package anet.channel.request;

import android.text.TextUtils;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import anet.channel.util.c;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

/* compiled from: Taobao */
public class Request {
    public static final String DEFAULT_CHARSET = "UTF-8";
    private String bizId;
    private BodyEntry body;
    private String charset;
    private int connectTimeout;
    private c formattedUrl;
    private Map<String, String> headers;
    private HostnameVerifier hostnameVerifier;
    private boolean isRedirectEnable;
    private String method;
    private c originUrl;
    private Map<String, String> params;
    private int readTimeout;
    private int redirectTimes;
    public final RequestStatistic rs;
    private c sendUrl;
    private String seq;
    private SSLSocketFactory sslSocketFactory;
    private URL url;

    /* compiled from: Taobao */
    public static class Builder {
        private String bizId;
        private BodyEntry body;
        private String charset;
        private int connectTimeout = 0;
        private c formattedUrl;
        private Map<String, String> headers = new HashMap();
        private HostnameVerifier hostnameVerifier;
        private boolean isRedirectEnable = true;
        private String method = "GET";
        private c originUrl;
        private Map<String, String> params;
        private int readTimeout = 0;
        private int redirectTimes = 0;
        private RequestStatistic rs = null;
        private String seq;
        private SSLSocketFactory sslSocketFactory;

        public Builder setUrl(c cVar) {
            this.originUrl = cVar;
            this.formattedUrl = null;
            return this;
        }

        public Builder setUrl(String str) {
            this.originUrl = c.a(str);
            this.formattedUrl = null;
            if (this.originUrl != null) {
                return this;
            }
            throw new IllegalArgumentException("toURL is invalid! toURL = " + str);
        }

        public Builder setMethod(String str) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("method is null or empty");
            }
            if ("GET".equalsIgnoreCase(str)) {
                this.method = "GET";
            } else if ("POST".equalsIgnoreCase(str)) {
                this.method = "POST";
            } else if (Method.OPTION.equalsIgnoreCase(str)) {
                this.method = Method.OPTION;
            } else if (Method.HEAD.equalsIgnoreCase(str)) {
                this.method = Method.HEAD;
            } else if (Method.PUT.equalsIgnoreCase(str)) {
                this.method = Method.PUT;
            } else if (Method.DELETE.equalsIgnoreCase(str)) {
                this.method = Method.DELETE;
            } else {
                this.method = "GET";
            }
            return this;
        }

        public Builder setHeaders(Map<String, String> map) {
            this.headers.clear();
            if (map != null) {
                this.headers.putAll(map);
            }
            return this;
        }

        public Builder addHeader(String str, String str2) {
            this.headers.put(str, str2);
            return this;
        }

        public Builder setParams(Map<String, String> map) {
            this.params = map;
            this.formattedUrl = null;
            return this;
        }

        public Builder addParam(String str, String str2) {
            if (this.params == null) {
                this.params = new HashMap();
            }
            this.params.put(str, str2);
            this.formattedUrl = null;
            return this;
        }

        public Builder setCharset(String str) {
            this.charset = str;
            this.formattedUrl = null;
            return this;
        }

        public Builder setBody(BodyEntry bodyEntry) {
            this.body = bodyEntry;
            return this;
        }

        public Builder setRedirectEnable(boolean z) {
            this.isRedirectEnable = z;
            return this;
        }

        public Builder setRedirectTimes(int i) {
            this.redirectTimes = i;
            return this;
        }

        public Builder setHostnameVerifier(HostnameVerifier hostnameVerifier) {
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder setSslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            this.sslSocketFactory = sSLSocketFactory;
            return this;
        }

        public Builder setBizId(String str) {
            this.bizId = str;
            return this;
        }

        public Builder setSeq(String str) {
            this.seq = str;
            return this;
        }

        public Builder setReadTimeout(int i) {
            this.readTimeout = i;
            return this;
        }

        public Builder setConnectTimeout(int i) {
            this.connectTimeout = i;
            return this;
        }

        public Builder setRequestStatistic(RequestStatistic requestStatistic) {
            this.rs = requestStatistic;
            return this;
        }

        public Request build() {
            if (this.body == null && this.params == null && Method.requiresRequestBody(this.method)) {
                ALog.e("awcn.Request", "method " + this.method + " must have a request body", null, new Object[0]);
            }
            if (!(this.body == null || Method.permitsRequestBody(this.method))) {
                ALog.e("awcn.Request", "method " + this.method + " should not have a request body", null, new Object[0]);
                this.body = null;
            }
            if (!(this.body == null || this.body.getContentType() == null)) {
                addHeader(HttpConstant.CONTENT_TYPE, this.body.getContentType());
            }
            return new Request(this);
        }
    }

    /* compiled from: Taobao */
    public static final class Method {
        public static final String DELETE = "DELETE";
        public static final String GET = "GET";
        public static final String HEAD = "HEAD";
        public static final String OPTION = "OPTIONS";
        public static final String POST = "POST";
        public static final String PUT = "PUT";

        static boolean requiresRequestBody(String str) {
            return str.equals("POST") || str.equals(PUT);
        }

        static boolean permitsRequestBody(String str) {
            return requiresRequestBody(str) || str.equals(DELETE) || str.equals(OPTION);
        }
    }

    private Request(Builder builder) {
        this.method = "GET";
        this.isRedirectEnable = true;
        this.redirectTimes = 0;
        this.connectTimeout = 10000;
        this.readTimeout = 10000;
        this.method = builder.method;
        this.headers = builder.headers;
        this.params = builder.params;
        this.body = builder.body;
        this.charset = builder.charset;
        this.isRedirectEnable = builder.isRedirectEnable;
        this.redirectTimes = builder.redirectTimes;
        this.hostnameVerifier = builder.hostnameVerifier;
        this.sslSocketFactory = builder.sslSocketFactory;
        this.bizId = builder.bizId;
        this.seq = builder.seq;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.originUrl = builder.originUrl;
        this.formattedUrl = builder.formattedUrl;
        if (this.formattedUrl == null) {
            formatUrl();
        }
        this.rs = builder.rs != null ? builder.rs : new RequestStatistic(getHost(), this.bizId);
    }

    public Builder newBuilder() {
        Builder builder = new Builder();
        builder.method = this.method;
        builder.headers = this.headers;
        builder.params = this.params;
        builder.body = this.body;
        builder.charset = this.charset;
        builder.isRedirectEnable = this.isRedirectEnable;
        builder.redirectTimes = this.redirectTimes;
        builder.hostnameVerifier = this.hostnameVerifier;
        builder.sslSocketFactory = this.sslSocketFactory;
        builder.originUrl = this.originUrl;
        builder.formattedUrl = this.formattedUrl;
        builder.bizId = this.bizId;
        builder.seq = this.seq;
        builder.connectTimeout = this.connectTimeout;
        builder.readTimeout = this.readTimeout;
        builder.rs = this.rs;
        return builder;
    }

    public c getHttpUrl() {
        return this.formattedUrl;
    }

    public String getUrlString() {
        return this.formattedUrl.e();
    }

    public URL getUrl() {
        if (this.url == null) {
            this.url = this.sendUrl != null ? this.sendUrl.f() : this.formattedUrl.f();
        }
        return this.url;
    }

    public void setDnsOptimize(String str, int i) {
        if (str != null && i != 0) {
            if (this.sendUrl == null) {
                this.sendUrl = new c(this.formattedUrl);
            }
            this.sendUrl.a(str, i);
            this.rs.setIPAndPort(str, i);
            this.url = null;
        }
    }

    public void setUrlScheme(boolean z) {
        if (this.sendUrl == null) {
            this.sendUrl = new c(this.formattedUrl);
        }
        this.sendUrl.b(z ? "https" : "http");
        this.url = null;
    }

    public int getRedirectTimes() {
        return this.redirectTimes;
    }

    public String getHost() {
        return this.formattedUrl.b();
    }

    public String getMethod() {
        return this.method;
    }

    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(this.headers);
    }

    public String getContentEncoding() {
        return this.charset != null ? this.charset : DEFAULT_CHARSET;
    }

    public boolean isRedirectEnable() {
        return this.isRedirectEnable;
    }

    public HostnameVerifier getHostnameVerifier() {
        return this.hostnameVerifier;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return this.sslSocketFactory;
    }

    public int postBody(OutputStream outputStream) throws IOException {
        if (this.body != null) {
            return this.body.writeTo(outputStream);
        }
        return 0;
    }

    public byte[] getBodyBytes() {
        if (this.body == null) {
            return null;
        }
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        try {
            postBody(byteArrayOutputStream);
        } catch (IOException e) {
        }
        return byteArrayOutputStream.toByteArray();
    }

    public boolean containsBody() {
        return this.body != null;
    }

    public String getBizId() {
        return this.bizId;
    }

    public String getSeq() {
        return this.seq;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    private void formatUrl() {
        String b = d.b(this.params, getContentEncoding());
        if (!TextUtils.isEmpty(b)) {
            if (Method.requiresRequestBody(this.method) && this.body == null) {
                try {
                    this.body = new ByteArrayEntry(b.getBytes(getContentEncoding()));
                    this.headers.put(HttpConstant.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + getContentEncoding());
                } catch (UnsupportedEncodingException e) {
                }
            } else {
                String e2 = this.originUrl.e();
                StringBuilder stringBuilder = new StringBuilder(e2);
                if (stringBuilder.indexOf("?") == -1) {
                    stringBuilder.append('?');
                } else if (e2.charAt(e2.length() - 1) != '&') {
                    stringBuilder.append('&');
                }
                stringBuilder.append(b);
                c a = c.a(stringBuilder.toString());
                if (a != null) {
                    this.formattedUrl = a;
                }
            }
        }
        if (this.formattedUrl == null) {
            this.formattedUrl = this.originUrl;
        }
    }
}
