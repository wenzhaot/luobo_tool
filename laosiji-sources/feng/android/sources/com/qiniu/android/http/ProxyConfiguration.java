package com.qiniu.android.http;

import com.umeng.message.util.HttpRequest;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public final class ProxyConfiguration {
    public final String hostAddress;
    public final String password;
    public final int port;
    public final Type type;
    public final String user;

    public ProxyConfiguration(String hostAddress, int port, String user, String password, Type type) {
        this.hostAddress = hostAddress;
        this.port = port;
        this.user = user;
        this.password = password;
        this.type = type;
    }

    public ProxyConfiguration(String hostAddress, int port) {
        this(hostAddress, port, null, null, Type.HTTP);
    }

    Proxy proxy() {
        return new Proxy(this.type, new InetSocketAddress(this.hostAddress, this.port));
    }

    Authenticator authenticator() {
        return new Authenticator() {
            public Request authenticate(Route route, Response response) throws IOException {
                return response.request().newBuilder().header(HttpRequest.HEADER_PROXY_AUTHORIZATION, Credentials.basic(ProxyConfiguration.this.user, ProxyConfiguration.this.password)).header("Proxy-Connection", "Keep-Alive").build();
            }
        };
    }
}
