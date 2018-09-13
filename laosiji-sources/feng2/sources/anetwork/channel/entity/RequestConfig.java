package anetwork.channel.entity;

import anet.channel.request.Request;
import anet.channel.request.Request.Builder;
import anet.channel.statist.RequestStatistic;
import anet.channel.strategy.utils.d;
import anet.channel.util.HttpConstant;
import anet.channel.util.Utils;
import anet.channel.util.c;
import anetwork.channel.Header;
import anetwork.channel.Param;
import anetwork.channel.aidl.ParcelableRequest;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.util.RequestConstant;
import anetwork.channel.util.SeqGen;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class RequestConfig {
    private static final int DFT_CONNECT_TIMEOUT = 15000;
    private static final int DFT_READ_TIMEOUT = 15000;
    private static final int MAX_RETRY_TIMES = 3;
    private static final String TAG = "anet.RequestConfig";
    private Request awcnRequest = null;
    private int connectTimeout = 0;
    private int mCurrentRetryTimes = 0;
    private int mRedirectTimes = 0;
    private int maxRetryTime = 0;
    private int readTimeout = 0;
    private final ParcelableRequest request;
    private RequestStatistic rs = null;
    private final String seqNo;
    private final int type;

    public RequestConfig(ParcelableRequest parcelableRequest, int i) {
        if (parcelableRequest == null) {
            throw new IllegalArgumentException("request is null");
        }
        this.request = parcelableRequest;
        this.type = i;
        this.seqNo = SeqGen.createSeqNo(parcelableRequest.getSeqNo(), i == 0 ? "HTTP" : "DGRD");
        this.connectTimeout = parcelableRequest.getConnectTimeout();
        if (this.connectTimeout <= 0) {
            this.connectTimeout = (int) (Utils.getNetworkTimeFactor() * 15000.0f);
        }
        this.readTimeout = parcelableRequest.getReadTimeout();
        if (this.readTimeout <= 0) {
            this.readTimeout = (int) (Utils.getNetworkTimeFactor() * 15000.0f);
        }
        this.maxRetryTime = parcelableRequest.getRetryTime();
        if (this.maxRetryTime < 0 || this.maxRetryTime > 3) {
            this.maxRetryTime = 2;
        }
        c initHttpUrl = initHttpUrl();
        this.rs = new RequestStatistic(initHttpUrl.b(), String.valueOf(parcelableRequest.getBizId()));
        this.rs.url = initHttpUrl.e();
        this.awcnRequest = buildRequest(initHttpUrl);
    }

    public Request getAwcnRequest() {
        return this.awcnRequest;
    }

    public void setAwcnRequest(Request request) {
        this.awcnRequest = request;
    }

    private c initHttpUrl() {
        c a = c.a(this.request.getURL());
        if (a == null) {
            throw new IllegalArgumentException("url is invalid. url=" + this.request.getURL());
        }
        if (!NetworkConfigCenter.isSSLEnabled()) {
            a.g();
        } else if (RequestConstant.FALSE.equalsIgnoreCase(this.request.getExtProperty(RequestConstant.ENABLE_SCHEME_REPLACE))) {
            a.i();
        }
        return a;
    }

    private Request buildRequest(c cVar) {
        Builder requestStatistic = new Builder().setUrl(cVar).setMethod(this.request.getMethod()).setBody(this.request.getBodyEntry()).setReadTimeout(getReadTimeout()).setConnectTimeout(getConnectTimeout()).setRedirectEnable(this.request.getFollowRedirects()).setRedirectTimes(this.mRedirectTimes).setBizId(this.request.getBizId()).setSeq(getSeqNo()).setRequestStatistic(this.rs);
        if (this.request.getParams() != null) {
            for (Param param : this.request.getParams()) {
                requestStatistic.addParam(param.getKey(), param.getValue());
            }
        }
        if (this.request.getCharset() != null) {
            requestStatistic.setCharset(this.request.getCharset());
        }
        requestStatistic.setHeaders(initHeaders(cVar));
        return requestStatistic.build();
    }

    public RequestStatistic getStatistic() {
        return this.rs;
    }

    public int getCurrentRetryTimes() {
        return this.mCurrentRetryTimes;
    }

    public int getReadTimeout() {
        return this.readTimeout;
    }

    public int getConnectTimeout() {
        return this.connectTimeout;
    }

    public int getWaitTimeout() {
        return this.readTimeout * (this.maxRetryTime + 1);
    }

    public String getSeqNo() {
        return this.seqNo;
    }

    public int getRequestType() {
        return this.type;
    }

    public String getRequestProperty(String str) {
        return this.request.getExtProperty(str);
    }

    public boolean isAllowRetry() {
        return this.mCurrentRetryTimes < this.maxRetryTime;
    }

    public boolean isHttpSessionEnable() {
        return NetworkConfigCenter.isHttpSessionEnable() && !RequestConstant.FALSE.equalsIgnoreCase(this.request.getExtProperty(RequestConstant.ENABLE_HTTP_DNS)) && (NetworkConfigCenter.isAllowHttpIpRetry() || getCurrentRetryTimes() == 0);
    }

    public c getHttpUrl() {
        return this.awcnRequest.getHttpUrl();
    }

    public String getUrlString() {
        return this.awcnRequest.getUrlString();
    }

    public Map<String, String> getHeaders() {
        return this.awcnRequest.getHeaders();
    }

    private Map<String, String> initHeaders(c cVar) {
        Object obj;
        if (d.a(cVar.b())) {
            obj = null;
        } else {
            int obj2 = 1;
        }
        Map hashMap = new HashMap();
        if (this.request.getHeaders() != null) {
            for (Header header : this.request.getHeaders()) {
                String name = header.getName();
                if (!HttpConstant.HOST.equalsIgnoreCase(name) && !":host".equalsIgnoreCase(name)) {
                    hashMap.put(name, header.getValue());
                } else if (obj2 == null) {
                    hashMap.put(HttpConstant.HOST, header.getValue());
                }
            }
        }
        return hashMap;
    }

    public boolean isRequestCookieEnabled() {
        return !RequestConstant.FALSE.equalsIgnoreCase(this.request.getExtProperty(RequestConstant.ENABLE_COOKIE));
    }

    public void retryRequest() {
        this.mCurrentRetryTimes++;
        this.rs.retryTimes = this.mCurrentRetryTimes;
    }

    public void redirectToUrl(c cVar) {
        this.mRedirectTimes++;
        this.rs = new RequestStatistic(cVar.b(), String.valueOf(this.request.getBizId()));
        this.rs.url = cVar.e();
        this.awcnRequest = buildRequest(cVar);
    }
}
