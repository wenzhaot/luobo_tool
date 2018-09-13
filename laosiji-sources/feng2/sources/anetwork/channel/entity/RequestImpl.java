package anetwork.channel.entity;

import android.text.TextUtils;
import anet.channel.request.BodyEntry;
import anet.channel.util.ALog;
import anetwork.channel.Header;
import anetwork.channel.IBodyHandler;
import anetwork.channel.Param;
import anetwork.channel.Request;
import anetwork.channel.util.RequestConstant;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: Taobao */
public class RequestImpl implements Request {
    private static final String TAG = "anet.RequestImpl";
    private String bizId;
    private BodyEntry bodyEntry = null;
    private String charset = "utf-8";
    private int connectTimeout;
    private Map<String, String> extProperties;
    private List<Header> headers;
    private boolean isRedirect = true;
    private String method = "GET";
    private List<Param> params;
    private int readTimeout;
    private int retryTime = 2;
    private String seqNo;
    private URI uri;
    private URL url;

    @Deprecated
    public RequestImpl(URI uri) {
        this.uri = uri;
    }

    @Deprecated
    public RequestImpl(URL url) {
        this.url = url;
    }

    public RequestImpl(String str) {
        if (str != null) {
            try {
                if (str.startsWith("//")) {
                    str = "http:" + str;
                }
            } catch (MalformedURLException e) {
                ALog.w(TAG, "url MalformedURLException error:" + str, null, new Object[0]);
                return;
            }
        }
        this.url = new URL(str);
    }

    @Deprecated
    public URI getURI() {
        return this.uri;
    }

    @Deprecated
    public void setUri(URI uri) {
        this.uri = uri;
    }

    public boolean getFollowRedirects() {
        return this.isRedirect;
    }

    public void setFollowRedirects(boolean z) {
        this.isRedirect = z;
    }

    public List<Header> getHeaders() {
        return this.headers;
    }

    public void setHeaders(List<Header> list) {
        this.headers = list;
    }

    public void addHeader(String str, String str2) {
        if (str != null && str2 != null) {
            if (this.headers == null) {
                this.headers = new ArrayList();
            }
            this.headers.add(new BasicHeader(str, str2));
        }
    }

    public void removeHeader(Header header) {
        if (this.headers != null) {
            this.headers.remove(header);
        }
    }

    public void setHeader(Header header) {
        if (header != null) {
            if (this.headers == null) {
                this.headers = new ArrayList();
            }
            int size = this.headers.size();
            int i = 0;
            while (i < size) {
                if (header.getName().equalsIgnoreCase(((Header) this.headers.get(i)).getName())) {
                    this.headers.set(i, header);
                    break;
                }
                i++;
            }
            if (i < this.headers.size()) {
                this.headers.add(header);
            }
        }
    }

    public Header[] getHeaders(String str) {
        if (str == null) {
            return null;
        }
        Header[] headerArr;
        ArrayList arrayList = new ArrayList();
        if (this.headers != null) {
            int i = 0;
            while (true) {
                int i2 = i;
                if (i2 >= this.headers.size()) {
                    break;
                }
                if (!(this.headers.get(i2) == null || ((Header) this.headers.get(i2)).getName() == null || !((Header) this.headers.get(i2)).getName().equalsIgnoreCase(str))) {
                    arrayList.add(this.headers.get(i2));
                }
                i = i2 + 1;
            }
            if (arrayList.size() > 0) {
                headerArr = new Header[arrayList.size()];
                arrayList.toArray(headerArr);
                return headerArr;
            }
        }
        headerArr = null;
        return headerArr;
    }

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String str) {
        this.method = str;
    }

    public int getRetryTime() {
        return this.retryTime;
    }

    public void setRetryTime(int i) {
        this.retryTime = i;
    }

    public String getCharset() {
        return this.charset;
    }

    public void setCharset(String str) {
        this.charset = str;
    }

    public List<Param> getParams() {
        return this.params;
    }

    public void setParams(List<Param> list) {
        this.params = list;
    }

    public BodyEntry getBodyEntry() {
        return this.bodyEntry;
    }

    public void setBodyEntry(BodyEntry bodyEntry) {
        this.bodyEntry = bodyEntry;
    }

    @Deprecated
    public IBodyHandler getBodyHandler() {
        return null;
    }

    public void setBodyHandler(IBodyHandler iBodyHandler) {
        this.bodyEntry = new BodyHandlerEntry(iBodyHandler);
    }

    public URL getURL() {
        return this.url;
    }

    public void setUrL(URL url) {
        this.url = url;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public void setConnectTimeout(int i) {
        this.connectTimeout = i;
    }

    public void setReadTimeout(int i) {
        this.readTimeout = i;
    }

    @Deprecated
    public void setBizId(int i) {
        this.bizId = String.valueOf(i);
    }

    public void setBizId(String str) {
        this.bizId = str;
    }

    public String getBizId() {
        return this.bizId;
    }

    public void setSeqNo(String str) {
        this.seqNo = str;
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    @Deprecated
    public boolean isCookieEnabled() {
        return !RequestConstant.FALSE.equals(getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    @Deprecated
    public void setCookieEnabled(boolean z) {
        setExtProperty(RequestConstant.ENABLE_COOKIE, z ? RequestConstant.TURE : RequestConstant.FALSE);
    }

    @Deprecated
    public void setProtocolModifiable(boolean z) {
        setExtProperty(RequestConstant.ENABLE_SCHEME_REPLACE, z ? RequestConstant.TURE : RequestConstant.FALSE);
    }

    @Deprecated
    public boolean isProtocolModifiable() {
        return !RequestConstant.FALSE.equals(getExtProperty(RequestConstant.ENABLE_SCHEME_REPLACE));
    }

    public void setExtProperty(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (this.extProperties == null) {
                this.extProperties = new HashMap();
            }
            this.extProperties.put(str, str2);
        }
    }

    public String getExtProperty(String str) {
        if (this.extProperties == null) {
            return null;
        }
        return (String) this.extProperties.get(str);
    }

    public Map<String, String> getExtProperties() {
        return this.extProperties;
    }
}
