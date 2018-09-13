package com.umeng.message.util;

import com.feng.car.utils.FengConstant;
import com.feng.car.utils.PermissionsConstant;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

public class HttpRequest {
    private static final String BOUNDARY = "00content0boundary00";
    public static final String CHARSET_UTF8 = "UTF-8";
    private static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
    public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
    public static final String CONTENT_TYPE_JSON = "application/json";
    private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
    public static final String CONTENT_TYPE_THRIFT = "application/thrift";
    private static final String CRLF = "\r\n";
    private static final String[] EMPTY_STRINGS = new String[0];
    public static final String ENCODING_GZIP = "gzip";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
    public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_DATE = "Date";
    public static final String HEADER_ETAG = "ETag";
    public static final String HEADER_EXPIRES = "Expires";
    public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
    public static final String HEADER_LAST_MODIFIED = "Last-Modified";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
    public static final String HEADER_REFERER = "Referer";
    public static final String HEADER_SERVER = "Server";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String METHOD_DELETE = "DELETE";
    public static final String METHOD_GET = "GET";
    public static final String METHOD_HEAD = "HEAD";
    public static final String METHOD_OPTIONS = "OPTIONS";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_PUT = "PUT";
    public static final String METHOD_TRACE = "TRACE";
    public static final String PARAM_CHARSET = "charset";
    private static SSLSocketFactory TRUSTED_FACTORY;
    private static HostnameVerifier TRUSTED_VERIFIER;
    private static int connectTimeout = PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE;
    private static int readTimeOut = PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE;
    private int bufferSize = 8192;
    private HttpURLConnection connection = null;
    private boolean form;
    private String httpProxyHost;
    private int httpProxyPort;
    private boolean ignoreCloseExceptions = true;
    private boolean multipart;
    private e output;
    private final String requestMethod;
    private boolean uncompress = false;
    private final URL url;

    protected static abstract class d<V> implements Callable<V> {
        protected abstract V b() throws HttpRequestException, IOException;

        protected abstract void c() throws IOException;

        protected d() {
        }

        public V call() throws HttpRequestException {
            Throwable th;
            Object obj = 1;
            try {
                V b = b();
                try {
                    c();
                    return b;
                } catch (IOException e) {
                    throw new HttpRequestException(e);
                }
            } catch (HttpRequestException e2) {
                throw e2;
            } catch (IOException e3) {
                throw new HttpRequestException(e3);
            } catch (Throwable th2) {
                th = th2;
            }
            try {
                c();
            } catch (IOException e4) {
                if (obj == null) {
                    throw new HttpRequestException(e4);
                }
            }
            throw th;
        }
    }

    protected static abstract class b<V> extends d<V> {
        private final Closeable a;
        private final boolean b;

        protected b(Closeable closeable, boolean z) {
            this.a = closeable;
            this.b = z;
        }

        protected void c() throws IOException {
            if (this.a instanceof Flushable) {
                ((Flushable) this.a).flush();
            }
            if (this.b) {
                try {
                    this.a.close();
                    return;
                } catch (IOException e) {
                    return;
                }
            }
            this.a.close();
        }
    }

    protected static abstract class c<V> extends d<V> {
        private final Flushable a;

        protected c(Flushable flushable) {
            this.a = flushable;
        }

        protected void c() throws IOException {
            this.a.flush();
        }
    }

    public interface ConnectionFactory {
        public static final ConnectionFactory DEFAULT = new ConnectionFactory() {
            public HttpURLConnection create(URL url) throws IOException {
                return (HttpURLConnection) url.openConnection();
            }

            public HttpURLConnection create(URL url, Proxy proxy) throws IOException {
                return (HttpURLConnection) url.openConnection(proxy);
            }
        };

        HttpURLConnection create(URL url) throws IOException;

        HttpURLConnection create(URL url, Proxy proxy) throws IOException;
    }

    public static class HttpRequestException extends RuntimeException {
        private static final long serialVersionUID = -1170466989781746231L;

        protected HttpRequestException(IOException iOException) {
            super(iOException);
        }

        /* renamed from: a */
        public IOException getCause() {
            return (IOException) super.getCause();
        }
    }

    public static class a {
        private static final byte a = (byte) 61;
        private static final String b = "US-ASCII";
        private static final byte[] c = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};

        private a() {
        }

        private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
            int i4 = 0;
            byte[] bArr3 = c;
            int i5 = (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0);
            if (i2 > 2) {
                i4 = (bArr[i + 2] << 24) >>> 24;
            }
            i4 |= i5;
            switch (i2) {
                case 1:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = a;
                    bArr2[i3 + 3] = a;
                    break;
                case 2:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                    bArr2[i3 + 3] = a;
                    break;
                case 3:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                    bArr2[i3 + 3] = bArr3[i4 & 63];
                    break;
            }
            return bArr2;
        }

        public static String a(String str) {
            byte[] bytes;
            try {
                bytes = str.getBytes("US-ASCII");
            } catch (UnsupportedEncodingException e) {
                bytes = str.getBytes();
            }
            return a(bytes);
        }

        public static String a(byte[] bArr) {
            return a(bArr, 0, bArr.length);
        }

        public static String a(byte[] bArr, int i, int i2) {
            byte[] b = b(bArr, i, i2);
            try {
                return new String(b, "US-ASCII");
            } catch (UnsupportedEncodingException e) {
                return new String(b);
            }
        }

        public static byte[] b(byte[] bArr, int i, int i2) {
            if (bArr == null) {
                throw new NullPointerException("Cannot serialize a null array.");
            } else if (i < 0) {
                throw new IllegalArgumentException("Cannot have negative offset: " + i);
            } else if (i2 < 0) {
                throw new IllegalArgumentException("Cannot have length offset: " + i2);
            } else if (i + i2 > bArr.length) {
                throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)}));
            } else {
                Object obj = new byte[((i2 % 3 > 0 ? 4 : 0) + ((i2 / 3) * 4))];
                int i3 = i2 - 2;
                int i4 = 0;
                int i5 = 0;
                while (i5 < i3) {
                    a(bArr, i5 + i, 3, obj, i4);
                    i5 += 3;
                    i4 += 4;
                }
                if (i5 < i2) {
                    a(bArr, i5 + i, i2 - i5, obj, i4);
                    i4 += 4;
                }
                if (i4 > obj.length - 1) {
                    return obj;
                }
                Object obj2 = new byte[i4];
                System.arraycopy(obj, 0, obj2, 0, i4);
                return obj2;
            }
        }
    }

    public static class e extends BufferedOutputStream {
        private final CharsetEncoder a;

        public e(OutputStream outputStream, String str, int i) {
            super(outputStream, i);
            this.a = Charset.forName(HttpRequest.getValidCharset(str)).newEncoder();
        }

        public e a(String str) throws IOException {
            ByteBuffer encode = this.a.encode(CharBuffer.wrap(str));
            super.write(encode.array(), 0, encode.limit());
            return this;
        }
    }

    private static String getValidCharset(String str) {
        return (str == null || str.length() <= 0) ? "UTF-8" : str;
    }

    private static HostnameVerifier getTrustedVerifier() {
        if (TRUSTED_VERIFIER == null) {
            TRUSTED_VERIFIER = new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    return true;
                }
            };
        }
        return TRUSTED_VERIFIER;
    }

    private static HostnameVerifier getTrustedVerifier(final HttpsURLConnection httpsURLConnection) {
        if (TRUSTED_VERIFIER == null) {
            TRUSTED_VERIFIER = new HostnameVerifier() {
                public boolean verify(String str, SSLSession sSLSession) {
                    String requestProperty = httpsURLConnection.getRequestProperty("Host");
                    if (requestProperty == null) {
                        requestProperty = httpsURLConnection.getURL().getHost();
                    }
                    return HttpsURLConnection.getDefaultHostnameVerifier().verify(requestProperty, sSLSession);
                }
            };
        }
        return TRUSTED_VERIFIER;
    }

    private static StringBuilder addPathSeparator(String str, StringBuilder stringBuilder) {
        if (str.indexOf(58) + 2 == str.lastIndexOf(47)) {
            stringBuilder.append('/');
        }
        return stringBuilder;
    }

    private static StringBuilder addParamPrefix(String str, StringBuilder stringBuilder) {
        int indexOf = str.indexOf(63);
        int length = stringBuilder.length() - 1;
        if (indexOf == -1) {
            stringBuilder.append('?');
        } else if (indexOf < length && str.charAt(length) != '&') {
            stringBuilder.append('&');
        }
        return stringBuilder;
    }

    public static void setConnectionFactory(ConnectionFactory connectionFactory) {
        if (connectionFactory == null) {
            CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
        } else {
            CONNECTION_FACTORY = connectionFactory;
        }
    }

    public static String encode(CharSequence charSequence) throws HttpRequestException {
        try {
            URL url = new URL(charSequence.toString());
            String host = url.getHost();
            int port = url.getPort();
            if (port != -1) {
                host = host + ':' + Integer.toString(port);
            }
            try {
                String toASCIIString = new URI(url.getProtocol(), host, url.getPath(), url.getQuery(), null).toASCIIString();
                int indexOf = toASCIIString.indexOf(63);
                if (indexOf <= 0 || indexOf + 1 >= toASCIIString.length()) {
                    return toASCIIString;
                }
                return toASCIIString.substring(0, indexOf + 1) + toASCIIString.substring(indexOf + 1).replace("+", "%2B");
            } catch (Throwable e) {
                IOException iOException = new IOException("Parsing URI failed");
                iOException.initCause(e);
                throw new HttpRequestException(iOException);
            }
        } catch (IOException e2) {
            throw new HttpRequestException(e2);
        }
    }

    public static String append(CharSequence charSequence, Map<?, ?> map) {
        String charSequence2 = charSequence.toString();
        if (map == null || map.isEmpty()) {
            return charSequence2;
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence2);
        addPathSeparator(charSequence2, stringBuilder);
        addParamPrefix(charSequence2, stringBuilder);
        Iterator it = map.entrySet().iterator();
        Entry entry = (Entry) it.next();
        stringBuilder.append(entry.getKey().toString());
        stringBuilder.append('=');
        Object value = entry.getValue();
        if (value != null) {
            stringBuilder.append(value);
        }
        while (it.hasNext()) {
            stringBuilder.append('&');
            entry = (Entry) it.next();
            stringBuilder.append(entry.getKey().toString());
            stringBuilder.append('=');
            value = entry.getValue();
            if (value != null) {
                stringBuilder.append(value);
            }
        }
        return stringBuilder.toString();
    }

    public static String append(CharSequence charSequence, Object... objArr) {
        String charSequence2 = charSequence.toString();
        if (objArr == null || objArr.length == 0) {
            return charSequence2;
        }
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("Must specify an even number of parameter names/values");
        }
        StringBuilder stringBuilder = new StringBuilder(charSequence2);
        addPathSeparator(charSequence2, stringBuilder);
        addParamPrefix(charSequence2, stringBuilder);
        stringBuilder.append(objArr[0]);
        stringBuilder.append('=');
        Object obj = objArr[1];
        if (obj != null) {
            stringBuilder.append(obj);
        }
        for (int i = 2; i < objArr.length; i += 2) {
            stringBuilder.append('&');
            stringBuilder.append(objArr[i]);
            stringBuilder.append('=');
            Object obj2 = objArr[i + 1];
            if (obj2 != null) {
                stringBuilder.append(obj2);
            }
        }
        return stringBuilder.toString();
    }

    public static HttpRequest get(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_GET);
    }

    public static HttpRequest get(URL url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_GET);
    }

    public static HttpRequest get(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence append = append(charSequence, (Map) map);
        if (z) {
            append = encode(append);
        }
        return get(append);
    }

    public static HttpRequest get(CharSequence charSequence, boolean z, Object... objArr) {
        CharSequence append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return get(append);
    }

    public static HttpRequest post(CharSequence charSequence) throws HttpRequestException {
        HttpRequest httpRequest = new HttpRequest(charSequence, METHOD_POST);
        httpRequest.getConnection().setConnectTimeout(connectTimeout);
        httpRequest.getConnection().setReadTimeout(readTimeOut);
        return httpRequest;
    }

    public static HttpRequest post(URL url) throws HttpRequestException {
        HttpRequest httpRequest = new HttpRequest(url, METHOD_POST);
        httpRequest.getConnection().setConnectTimeout(connectTimeout);
        httpRequest.getConnection().setReadTimeout(readTimeOut);
        return httpRequest;
    }

    public static HttpRequest post(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence append = append(charSequence, (Map) map);
        if (z) {
            append = encode(append);
        }
        return post(append);
    }

    public static HttpRequest post(CharSequence charSequence, boolean z, Object... objArr) {
        CharSequence append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return post(append);
    }

    public static HttpRequest put(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "PUT");
    }

    public static HttpRequest put(URL url) throws HttpRequestException {
        return new HttpRequest(url, "PUT");
    }

    public static HttpRequest put(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence append = append(charSequence, (Map) map);
        if (z) {
            append = encode(append);
        }
        return put(append);
    }

    public static HttpRequest put(CharSequence charSequence, boolean z, Object... objArr) {
        CharSequence append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return put(append);
    }

    public static HttpRequest delete(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "DELETE");
    }

    public static HttpRequest delete(URL url) throws HttpRequestException {
        return new HttpRequest(url, "DELETE");
    }

    public static HttpRequest delete(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence append = append(charSequence, (Map) map);
        if (z) {
            append = encode(append);
        }
        return delete(append);
    }

    public static HttpRequest delete(CharSequence charSequence, boolean z, Object... objArr) {
        CharSequence append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return delete(append);
    }

    public static HttpRequest head(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, "HEAD");
    }

    public static HttpRequest head(URL url) throws HttpRequestException {
        return new HttpRequest(url, "HEAD");
    }

    public static HttpRequest head(CharSequence charSequence, Map<?, ?> map, boolean z) {
        CharSequence append = append(charSequence, (Map) map);
        if (z) {
            append = encode(append);
        }
        return head(append);
    }

    public static HttpRequest head(CharSequence charSequence, boolean z, Object... objArr) {
        CharSequence append = append(charSequence, objArr);
        if (z) {
            append = encode(append);
        }
        return head(append);
    }

    public static HttpRequest options(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_OPTIONS);
    }

    public static HttpRequest options(URL url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_OPTIONS);
    }

    public static HttpRequest trace(CharSequence charSequence) throws HttpRequestException {
        return new HttpRequest(charSequence, METHOD_TRACE);
    }

    public static HttpRequest trace(URL url) throws HttpRequestException {
        return new HttpRequest(url, METHOD_TRACE);
    }

    public static void keepAlive(boolean z) {
        setProperty("http.keepAlive", Boolean.toString(z));
    }

    public static void proxyHost(String str) {
        setProperty("http.proxyHost", str);
        setProperty("https.proxyHost", str);
    }

    public static void proxyPort(int i) {
        String num = Integer.toString(i);
        setProperty("http.proxyPort", num);
        setProperty("https.proxyPort", num);
    }

    public static void nonProxyHosts(String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            setProperty("http.nonProxyHosts", null);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int length = strArr.length - 1;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(strArr[i]).append('|');
        }
        stringBuilder.append(strArr[length]);
        setProperty("http.nonProxyHosts", stringBuilder.toString());
    }

    private static String setProperty(final String str, final String str2) {
        PrivilegedAction anonymousClass4;
        if (str2 != null) {
            anonymousClass4 = new PrivilegedAction<String>() {
                /* renamed from: a */
                public String run() {
                    return System.setProperty(str, str2);
                }
            };
        } else {
            anonymousClass4 = new PrivilegedAction<String>() {
                /* renamed from: a */
                public String run() {
                    return System.clearProperty(str);
                }
            };
        }
        return (String) AccessController.doPrivileged(anonymousClass4);
    }

    public HttpRequest(CharSequence charSequence, String str) throws HttpRequestException {
        try {
            this.url = new URL(charSequence.toString());
            this.requestMethod = str;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest(URL url, String str) throws HttpRequestException {
        this.url = url;
        this.requestMethod = str;
    }

    private Proxy createProxy() {
        return new Proxy(Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
    }

    private HttpURLConnection createConnection() {
        try {
            HttpURLConnection create;
            if (this.httpProxyHost != null) {
                create = CONNECTION_FACTORY.create(this.url, createProxy());
            } else {
                create = CONNECTION_FACTORY.create(this.url);
            }
            create.setRequestMethod(this.requestMethod);
            return create;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public String toString() {
        return method() + ' ' + url();
    }

    public HttpURLConnection getConnection() {
        if (this.connection == null) {
            this.connection = createConnection();
        }
        return this.connection;
    }

    public HttpRequest ignoreCloseExceptions(boolean z) {
        this.ignoreCloseExceptions = z;
        return this;
    }

    public boolean ignoreCloseExceptions() {
        return this.ignoreCloseExceptions;
    }

    public int code() throws HttpRequestException {
        try {
            closeOutput();
            return getConnection().getResponseCode();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest code(AtomicInteger atomicInteger) throws HttpRequestException {
        atomicInteger.set(code());
        return this;
    }

    public boolean ok() throws HttpRequestException {
        return 200 == code();
    }

    public boolean created() throws HttpRequestException {
        return Constants.COMMAND_PING == code();
    }

    public boolean serverError() throws HttpRequestException {
        return 500 == code();
    }

    public boolean badRequest() throws HttpRequestException {
        return FengConstant.SINGLE_IMAGE_MAX_WIDTH == code();
    }

    public boolean notFound() throws HttpRequestException {
        return 404 == code();
    }

    public boolean notModified() throws HttpRequestException {
        return ErrorCode.DM_PACKAGENAME_INVALID == code();
    }

    public String message() throws HttpRequestException {
        try {
            closeOutput();
            return getConnection().getResponseMessage();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest disconnect() {
        getConnection().disconnect();
        return this;
    }

    public HttpRequest chunk(int i) {
        getConnection().setChunkedStreamingMode(i);
        return this;
    }

    public HttpRequest bufferSize(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("Size must be greater than zero");
        }
        this.bufferSize = i;
        return this;
    }

    public int bufferSize() {
        return this.bufferSize;
    }

    public HttpRequest uncompress(boolean z) {
        this.uncompress = z;
        return this;
    }

    protected ByteArrayOutputStream byteStream() {
        int contentLength = contentLength();
        if (contentLength > 0) {
            return new ByteArrayOutputStream(contentLength);
        }
        return new ByteArrayOutputStream();
    }

    public String body(String str) throws HttpRequestException {
        OutputStream byteStream = byteStream();
        try {
            copy(buffer(), byteStream);
            return byteStream.toString(getValidCharset(str));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public String body() throws HttpRequestException {
        return body(charset());
    }

    public HttpRequest body(AtomicReference<String> atomicReference) throws HttpRequestException {
        atomicReference.set(body());
        return this;
    }

    public HttpRequest body(AtomicReference<String> atomicReference, String str) throws HttpRequestException {
        atomicReference.set(body(str));
        return this;
    }

    public boolean isBodyEmpty() throws HttpRequestException {
        return contentLength() == 0;
    }

    public byte[] bytes() throws HttpRequestException {
        OutputStream byteStream = byteStream();
        try {
            copy(buffer(), byteStream);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public BufferedInputStream buffer() throws HttpRequestException {
        return new BufferedInputStream(stream(), this.bufferSize);
    }

    public InputStream stream() throws HttpRequestException {
        InputStream inputStream;
        if (code() < FengConstant.SINGLE_IMAGE_MAX_WIDTH) {
            try {
                inputStream = getConnection().getInputStream();
            } catch (IOException e) {
                throw new HttpRequestException(e);
            }
        }
        inputStream = getConnection().getErrorStream();
        if (inputStream == null) {
            try {
                inputStream = getConnection().getInputStream();
            } catch (IOException e2) {
                throw new HttpRequestException(e2);
            }
        }
        if (!this.uncompress || !ENCODING_GZIP.equals(contentEncoding())) {
            return inputStream;
        }
        try {
            return new GZIPInputStream(inputStream);
        } catch (IOException e22) {
            throw new HttpRequestException(e22);
        }
    }

    public InputStreamReader reader(String str) throws HttpRequestException {
        try {
            return new InputStreamReader(stream(), getValidCharset(str));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public InputStreamReader reader() throws HttpRequestException {
        return reader(charset());
    }

    public BufferedReader bufferedReader(String str) throws HttpRequestException {
        return new BufferedReader(reader(str), this.bufferSize);
    }

    public BufferedReader bufferedReader() throws HttpRequestException {
        return bufferedReader(charset());
    }

    public HttpRequest receive(File file) throws HttpRequestException {
        try {
            final Object bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file), this.bufferSize);
            return (HttpRequest) new b<HttpRequest>(this.ignoreCloseExceptions, bufferedOutputStream) {
                /* renamed from: a */
                protected HttpRequest b() throws HttpRequestException, IOException {
                    return HttpRequest.this.receive(bufferedOutputStream);
                }
            }.call();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest receive(OutputStream outputStream) throws HttpRequestException {
        try {
            return copy(buffer(), outputStream);
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest receive(PrintStream printStream) throws HttpRequestException {
        return receive((OutputStream) printStream);
    }

    public HttpRequest receive(Appendable appendable) throws HttpRequestException {
        Closeable bufferedReader = bufferedReader();
        final Closeable closeable = bufferedReader;
        final Appendable appendable2 = appendable;
        return (HttpRequest) new b<HttpRequest>(bufferedReader, this.ignoreCloseExceptions) {
            /* renamed from: a */
            public HttpRequest b() throws IOException {
                Object allocate = CharBuffer.allocate(HttpRequest.this.bufferSize);
                while (true) {
                    int read = closeable.read(allocate);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    allocate.rewind();
                    appendable2.append(allocate, 0, read);
                    allocate.rewind();
                }
            }
        }.call();
    }

    public HttpRequest receive(Writer writer) throws HttpRequestException {
        Closeable bufferedReader = bufferedReader();
        final Closeable closeable = bufferedReader;
        final Writer writer2 = writer;
        return (HttpRequest) new b<HttpRequest>(bufferedReader, this.ignoreCloseExceptions) {
            /* renamed from: a */
            public HttpRequest b() throws IOException {
                return HttpRequest.this.copy(closeable, writer2);
            }
        }.call();
    }

    public HttpRequest readTimeout(int i) {
        getConnection().setReadTimeout(i);
        return this;
    }

    public HttpRequest connectTimeout(int i) {
        getConnection().setConnectTimeout(i);
        return this;
    }

    public HttpRequest header(String str, String str2) {
        getConnection().setRequestProperty(str, str2);
        return this;
    }

    public HttpRequest header(String str, Number number) {
        return header(str, number != null ? number.toString() : null);
    }

    public HttpRequest headers(Map<String, String> map) {
        if (!map.isEmpty()) {
            for (Entry header : map.entrySet()) {
                header(header);
            }
        }
        return this;
    }

    public HttpRequest header(Entry<String, String> entry) {
        return header((String) entry.getKey(), (String) entry.getValue());
    }

    public String header(String str) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderField(str);
    }

    public Map<String, List<String>> headers() throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFields();
    }

    public long dateHeader(String str) throws HttpRequestException {
        return dateHeader(str, -1);
    }

    public long dateHeader(String str, long j) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFieldDate(str, j);
    }

    public int intHeader(String str) throws HttpRequestException {
        return intHeader(str, -1);
    }

    public int intHeader(String str, int i) throws HttpRequestException {
        closeOutputQuietly();
        return getConnection().getHeaderFieldInt(str, i);
    }

    public String[] headers(String str) {
        Map headers = headers();
        if (headers == null || headers.isEmpty()) {
            return EMPTY_STRINGS;
        }
        List list = (List) headers.get(str);
        if (list == null || list.isEmpty()) {
            return EMPTY_STRINGS;
        }
        return (String[]) list.toArray(new String[list.size()]);
    }

    public String parameter(String str, String str2) {
        return getParam(header(str), str2);
    }

    public Map<String, String> parameters(String str) {
        return getParams(header(str));
    }

    protected Map<String, String> getParams(String str) {
        if (str == null || str.length() == 0) {
            return Collections.emptyMap();
        }
        int length = str.length();
        int indexOf = str.indexOf(59) + 1;
        if (indexOf == 0 || indexOf == length) {
            return Collections.emptyMap();
        }
        int indexOf2 = str.indexOf(59, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = length;
        }
        Map<String, String> linkedHashMap = new LinkedHashMap();
        while (indexOf < indexOf2) {
            int indexOf3 = str.indexOf(61, indexOf);
            if (indexOf3 != -1 && indexOf3 < indexOf2) {
                String trim = str.substring(indexOf, indexOf3).trim();
                if (trim.length() > 0) {
                    String trim2 = str.substring(indexOf3 + 1, indexOf2).trim();
                    int length2 = trim2.length();
                    if (length2 != 0) {
                        if (length2 > 2 && '\"' == trim2.charAt(0) && '\"' == trim2.charAt(length2 - 1)) {
                            linkedHashMap.put(trim, trim2.substring(1, length2 - 1));
                        } else {
                            linkedHashMap.put(trim, trim2);
                        }
                    }
                }
            }
            indexOf = indexOf2 + 1;
            indexOf2 = str.indexOf(59, indexOf);
            if (indexOf2 == -1) {
                indexOf2 = length;
            }
        }
        return linkedHashMap;
    }

    protected String getParam(String str, String str2) {
        if (str == null || str.length() == 0) {
            return null;
        }
        int length = str.length();
        int indexOf = str.indexOf(59) + 1;
        if (indexOf == 0 || indexOf == length) {
            return null;
        }
        int i;
        int indexOf2 = str.indexOf(59, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = indexOf;
            indexOf = length;
        } else {
            i = indexOf2;
            indexOf2 = indexOf;
            indexOf = i;
        }
        while (indexOf2 < indexOf) {
            int indexOf3 = str.indexOf(61, indexOf2);
            if (indexOf3 != -1 && indexOf3 < indexOf && str2.equals(str.substring(indexOf2, indexOf3).trim())) {
                String trim = str.substring(indexOf3 + 1, indexOf).trim();
                indexOf3 = trim.length();
                if (indexOf3 != 0) {
                    if (indexOf3 > 2 && '\"' == trim.charAt(0) && '\"' == trim.charAt(indexOf3 - 1)) {
                        return trim.substring(1, indexOf3 - 1);
                    }
                    return trim;
                }
            }
            indexOf++;
            indexOf2 = str.indexOf(59, indexOf);
            if (indexOf2 == -1) {
                indexOf2 = length;
            }
            i = indexOf2;
            indexOf2 = indexOf;
            indexOf = i;
        }
        return null;
    }

    public String charset() {
        return parameter("Content-Type", PARAM_CHARSET);
    }

    public HttpRequest userAgent(String str) {
        return header("User-Agent", str);
    }

    public HttpRequest referer(String str) {
        return header(HEADER_REFERER, str);
    }

    public HttpRequest useCaches(boolean z) {
        getConnection().setUseCaches(z);
        return this;
    }

    public HttpRequest acceptEncoding(String str) {
        return header(HEADER_ACCEPT_ENCODING, str);
    }

    public HttpRequest acceptGzipEncoding() {
        return acceptEncoding(ENCODING_GZIP);
    }

    public HttpRequest acceptCharset(String str) {
        return header(HEADER_ACCEPT_CHARSET, str);
    }

    public String contentEncoding() {
        return header(HEADER_CONTENT_ENCODING);
    }

    public String server() {
        return header(HEADER_SERVER);
    }

    public long date() {
        return dateHeader(HEADER_DATE);
    }

    public String cacheControl() {
        return header(HEADER_CACHE_CONTROL);
    }

    public String eTag() {
        return header(HEADER_ETAG);
    }

    public long expires() {
        return dateHeader(HEADER_EXPIRES);
    }

    public long lastModified() {
        return dateHeader(HEADER_LAST_MODIFIED);
    }

    public String location() {
        return header(HEADER_LOCATION);
    }

    public HttpRequest authorization(String str) {
        return header(HEADER_AUTHORIZATION, str);
    }

    public HttpRequest proxyAuthorization(String str) {
        return header(HEADER_PROXY_AUTHORIZATION, str);
    }

    public HttpRequest basic(String str, String str2) {
        return authorization("Basic " + a.a(str + ':' + str2));
    }

    public HttpRequest proxyBasic(String str, String str2) {
        return proxyAuthorization("Basic " + a.a(str + ':' + str2));
    }

    public HttpRequest ifModifiedSince(long j) {
        getConnection().setIfModifiedSince(j);
        return this;
    }

    public HttpRequest ifNoneMatch(String str) {
        return header(HEADER_IF_NONE_MATCH, str);
    }

    public HttpRequest contentType(String str) {
        return contentType(str, null);
    }

    public HttpRequest contentType(String str, String str2) {
        if (str2 == null || str2.length() <= 0) {
            return header("Content-Type", str);
        }
        String str3 = "; charset=";
        return header("Content-Type", str + "; charset=" + str2);
    }

    public String contentType() {
        return header("Content-Type");
    }

    public int contentLength() {
        return intHeader(HEADER_CONTENT_LENGTH);
    }

    public HttpRequest contentLength(String str) {
        return contentLength(Integer.parseInt(str));
    }

    public HttpRequest contentLength(int i) {
        getConnection().setFixedLengthStreamingMode(i);
        return this;
    }

    public HttpRequest accept(String str) {
        return header(HEADER_ACCEPT, str);
    }

    public HttpRequest acceptJson() {
        return accept("application/json");
    }

    protected HttpRequest copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        final InputStream inputStream2 = inputStream;
        final OutputStream outputStream2 = outputStream;
        return (HttpRequest) new b<HttpRequest>(inputStream, this.ignoreCloseExceptions) {
            /* renamed from: a */
            public HttpRequest b() throws IOException {
                byte[] bArr = new byte[HttpRequest.this.bufferSize];
                while (true) {
                    int read = inputStream2.read(bArr);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    outputStream2.write(bArr, 0, read);
                }
            }
        }.call();
    }

    protected HttpRequest copy(Reader reader, Writer writer) throws IOException {
        final Reader reader2 = reader;
        final Writer writer2 = writer;
        return (HttpRequest) new b<HttpRequest>(reader, this.ignoreCloseExceptions) {
            /* renamed from: a */
            public HttpRequest b() throws IOException {
                char[] cArr = new char[HttpRequest.this.bufferSize];
                while (true) {
                    int read = reader2.read(cArr);
                    if (read == -1) {
                        return HttpRequest.this;
                    }
                    writer2.write(cArr, 0, read);
                }
            }
        }.call();
    }

    protected HttpRequest closeOutput() throws IOException {
        if (this.output != null) {
            if (this.multipart) {
                this.output.a("\r\n--00content0boundary00--\r\n");
            }
            if (this.ignoreCloseExceptions) {
                try {
                    this.output.close();
                } catch (IOException e) {
                }
            } else {
                this.output.close();
            }
            this.output = null;
        }
        return this;
    }

    protected HttpRequest closeOutputQuietly() throws HttpRequestException {
        try {
            return closeOutput();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    protected HttpRequest openOutput() throws IOException {
        if (this.output == null) {
            getConnection().setDoOutput(true);
            this.output = new e(getConnection().getOutputStream(), getParam(getConnection().getRequestProperty("Content-Type"), PARAM_CHARSET), this.bufferSize);
        }
        return this;
    }

    protected HttpRequest startPart() throws IOException {
        if (this.multipart) {
            this.output.a("\r\n--00content0boundary00\r\n");
        } else {
            this.multipart = true;
            contentType(CONTENT_TYPE_MULTIPART).openOutput();
            this.output.a("--00content0boundary00\r\n");
        }
        return this;
    }

    protected HttpRequest writePartHeader(String str, String str2) throws IOException {
        return writePartHeader(str, str2, null);
    }

    protected HttpRequest writePartHeader(String str, String str2, String str3) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("form-data; name=\"").append(str);
        if (str2 != null) {
            stringBuilder.append("\"; filename=\"").append(str2);
        }
        stringBuilder.append('\"');
        partHeader("Content-Disposition", stringBuilder.toString());
        if (str3 != null) {
            partHeader("Content-Type", str3);
        }
        return send(CRLF);
    }

    public HttpRequest part(String str, String str2) {
        return part(str, null, str2);
    }

    public HttpRequest part(String str, String str2, String str3) throws HttpRequestException {
        return part(str, str2, null, str3);
    }

    public HttpRequest part(String str, String str2, String str3, String str4) throws HttpRequestException {
        try {
            startPart();
            writePartHeader(str, str2, str3);
            this.output.a(str4);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest part(String str, Number number) throws HttpRequestException {
        return part(str, null, number);
    }

    public HttpRequest part(String str, String str2, Number number) throws HttpRequestException {
        return part(str, str2, number != null ? number.toString() : null);
    }

    public HttpRequest part(String str, File file) throws HttpRequestException {
        return part(str, null, file);
    }

    public HttpRequest part(String str, String str2, File file) throws HttpRequestException {
        return part(str, str2, null, file);
    }

    public HttpRequest part(String str, String str2, String str3, File file) throws HttpRequestException {
        try {
            return part(str, str2, str3, new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest part(String str, InputStream inputStream) throws HttpRequestException {
        return part(str, null, null, inputStream);
    }

    public HttpRequest part(String str, String str2, String str3, InputStream inputStream) throws HttpRequestException {
        try {
            startPart();
            writePartHeader(str, str2, str3);
            copy(inputStream, this.output);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest partHeader(String str, String str2) throws HttpRequestException {
        return send((CharSequence) str).send((CharSequence) ": ").send((CharSequence) str2).send(CRLF);
    }

    public HttpRequest send(File file) throws HttpRequestException {
        try {
            return send(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(byte[] bArr) throws HttpRequestException {
        return send(new ByteArrayInputStream(bArr));
    }

    public HttpRequest send(InputStream inputStream) throws HttpRequestException {
        try {
            openOutput();
            copy(inputStream, this.output);
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(final Reader reader) throws HttpRequestException {
        try {
            openOutput();
            final Object outputStreamWriter = new OutputStreamWriter(this.output, this.output.a.charset());
            return (HttpRequest) new c<HttpRequest>(outputStreamWriter) {
                /* renamed from: a */
                protected HttpRequest b() throws IOException {
                    return HttpRequest.this.copy(reader, outputStreamWriter);
                }
            }.call();
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest send(CharSequence charSequence) throws HttpRequestException {
        try {
            openOutput();
            this.output.a(charSequence.toString());
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public OutputStreamWriter writer() throws HttpRequestException {
        try {
            openOutput();
            return new OutputStreamWriter(this.output, this.output.a.charset());
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest form(Map<?, ?> map) throws HttpRequestException {
        return form((Map) map, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry) throws HttpRequestException {
        return form((Entry) entry, "UTF-8");
    }

    public HttpRequest form(Entry<?, ?> entry, String str) throws HttpRequestException {
        return form(entry.getKey(), entry.getValue(), str);
    }

    public HttpRequest form(Object obj, Object obj2) throws HttpRequestException {
        return form(obj, obj2, "UTF-8");
    }

    public HttpRequest form(Object obj, Object obj2, String str) throws HttpRequestException {
        boolean z = !this.form;
        if (z) {
            contentType("application/x-www-form-urlencoded", str);
            this.form = true;
        }
        String validCharset = getValidCharset(str);
        try {
            openOutput();
            if (!z) {
                this.output.write(38);
            }
            this.output.a(URLEncoder.encode(obj.toString(), validCharset));
            this.output.write(61);
            if (obj2 != null) {
                this.output.a(URLEncoder.encode(obj2.toString(), validCharset));
            }
            return this;
        } catch (IOException e) {
            throw new HttpRequestException(e);
        }
    }

    public HttpRequest form(Map<?, ?> map, String str) throws HttpRequestException {
        if (!map.isEmpty()) {
            for (Entry form : map.entrySet()) {
                form(form, str);
            }
        }
        return this;
    }

    public HttpRequest trustAllHosts() {
        HttpURLConnection connection = getConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(getTrustedVerifier());
        }
        return this;
    }

    public HttpRequest trustHosts() {
        HttpURLConnection connection = getConnection();
        if (connection instanceof HttpsURLConnection) {
            ((HttpsURLConnection) connection).setHostnameVerifier(getTrustedVerifier((HttpsURLConnection) connection));
        }
        return this;
    }

    public URL url() {
        return getConnection().getURL();
    }

    public String method() {
        return getConnection().getRequestMethod();
    }

    public HttpRequest useProxy(String str, int i) {
        if (this.connection != null) {
            throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
        }
        this.httpProxyHost = str;
        this.httpProxyPort = i;
        return this;
    }

    public HttpRequest followRedirects(boolean z) {
        getConnection();
        HttpURLConnection.setFollowRedirects(z);
        return this;
    }
}
