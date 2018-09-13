package okhttp3.internal.platform;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.annotation.Nullable;
import javax.net.ssl.SSLSocket;
import okhttp3.Protocol;
import okhttp3.internal.Util;

class JdkWithJettyBootPlatform extends Platform {
    private final Class<?> clientProviderClass;
    private final Method getMethod;
    private final Method putMethod;
    private final Method removeMethod;
    private final Class<?> serverProviderClass;

    JdkWithJettyBootPlatform(Method putMethod, Method getMethod, Method removeMethod, Class<?> clientProviderClass, Class<?> serverProviderClass) {
        this.putMethod = putMethod;
        this.getMethod = getMethod;
        this.removeMethod = removeMethod;
        this.clientProviderClass = clientProviderClass;
        this.serverProviderClass = serverProviderClass;
    }

    public void configureTlsExtensions(SSLSocket sslSocket, String hostname, List<Protocol> protocols) {
        ReflectiveOperationException e;
        List<String> names = Platform.alpnProtocolNames(protocols);
        try {
            Object provider = Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(names));
            this.putMethod.invoke(null, new Object[]{sslSocket, provider});
        } catch (InvocationTargetException e2) {
            e = e2;
            throw Util.assertionError("unable to set alpn", e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw Util.assertionError("unable to set alpn", e);
        }
    }

    public void afterHandshake(SSLSocket sslSocket) {
        ReflectiveOperationException e;
        try {
            this.removeMethod.invoke(null, new Object[]{sslSocket});
        } catch (IllegalAccessException e2) {
            e = e2;
            throw Util.assertionError("unable to remove alpn", e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw Util.assertionError("unable to remove alpn", e);
        }
    }

    @Nullable
    public String getSelectedProtocol(SSLSocket socket) {
        ReflectiveOperationException e;
        try {
            JettyNegoProvider provider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, new Object[]{socket}));
            if (!provider.unsupported && provider.selected == null) {
                Platform.get().log(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
                return null;
            } else if (provider.unsupported) {
                return null;
            } else {
                return provider.selected;
            }
        } catch (InvocationTargetException e2) {
            e = e2;
            throw Util.assertionError("unable to get selected protocol", e);
        } catch (IllegalAccessException e3) {
            e = e3;
            throw Util.assertionError("unable to get selected protocol", e);
        }
    }

    public static Platform buildIfSupported() {
        try {
            String negoClassName = "org.eclipse.jetty.alpn.ALPN";
            Class<?> negoClass = Class.forName(negoClassName);
            Class<?> providerClass = Class.forName(negoClassName + "$Provider");
            Class<?> clientProviderClass = Class.forName(negoClassName + "$ClientProvider");
            Class<?> serverProviderClass = Class.forName(negoClassName + "$ServerProvider");
            return new JdkWithJettyBootPlatform(negoClass.getMethod("put", new Class[]{SSLSocket.class, providerClass}), negoClass.getMethod("get", new Class[]{SSLSocket.class}), negoClass.getMethod("remove", new Class[]{SSLSocket.class}), clientProviderClass, serverProviderClass);
        } catch (ClassNotFoundException e) {
        } catch (NoSuchMethodException e2) {
        }
        return null;
    }
}
