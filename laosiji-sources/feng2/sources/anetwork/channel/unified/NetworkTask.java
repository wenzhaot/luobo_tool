package anetwork.channel.unified;

import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.Config.Builder;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.RequestCb;
import anet.channel.Session;
import anet.channel.SessionCenter;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.entity.ConnType.TypeLevel;
import anet.channel.entity.ENV;
import anet.channel.entity.a;
import anet.channel.monitor.b;
import anet.channel.request.Cancelable;
import anet.channel.request.Request;
import anet.channel.session.d;
import anet.channel.statist.RequestStatistic;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anet.channel.util.StringUtils;
import anet.channel.util.c;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.Cache.Entry;
import anetwork.channel.cache.CacheHelper;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.cookie.CookieManager;
import anetwork.channel.http.NetworkSdkSetting;
import anetwork.channel.stat.NetworkStat;
import anetwork.channel.statist.StatisticReqTimes;
import anetwork.channel.util.RequestConstant;
import com.baidu.mapapi.UIMsg.m_AppUI;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
class NetworkTask implements IUnifiedTask {
    public static final String TAG = "anet.NetworkTask";
    Cache cache = null;
    ByteArrayOutputStream cacheBuffer = null;
    volatile Cancelable cancelable = null;
    int contentLength = 0;
    int dataChunkIndex = 0;
    Entry entry = null;
    String f_refer = DispatchConstants.OTHER;
    volatile boolean isCanceled = false;
    volatile AtomicBoolean isDone = null;
    RequestContext rc;
    int statusCode = 0;

    NetworkTask(RequestContext requestContext, Cache cache, Entry entry) {
        this.rc = requestContext;
        this.isDone = requestContext.isDone;
        this.cache = cache;
        this.entry = entry;
        this.f_refer = (String) requestContext.config.getHeaders().get(HttpConstant.F_REFER);
    }

    public void cancel() {
        this.isCanceled = true;
        if (this.cancelable != null) {
            this.cancelable.cancel();
        }
    }

    public void run() {
        if (!this.isCanceled) {
            if (NetworkStatusHelper.g()) {
                if (ALog.isPrintLog(2)) {
                    ALog.i(TAG, "exec request", this.rc.seqNum, "retryTimes", Integer.valueOf(this.rc.config.getCurrentRetryTimes()));
                }
                try {
                    sendRequest(tryGetSession(), this.rc.config.getAwcnRequest());
                    return;
                } catch (Throwable e) {
                    ALog.e(TAG, "send request failed.", this.rc.seqNum, e, new Object[0]);
                    return;
                }
            }
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "network unavailable", this.rc.seqNum, "NetworkStatus", NetworkStatusHelper.a());
            }
            this.rc.callback.onFinish(new DefaultFinishEvent(ErrorConstant.ERROR_NO_NETWORK));
        }
    }

    private c checkCName(c cVar) {
        String str = (String) this.rc.config.getHeaders().get(HttpConstant.X_HOST_CNAME);
        if (TextUtils.isEmpty(str)) {
            return cVar;
        }
        c a = c.a(cVar.e().replace(cVar.b(), str));
        if (a != null) {
            return a;
        }
        return cVar;
    }

    private SessionCenter getSessionCenter() {
        String requestProperty = this.rc.config.getRequestProperty(RequestConstant.APPKEY);
        if (TextUtils.isEmpty(requestProperty)) {
            return SessionCenter.getInstance();
        }
        Config build;
        ENV env = ENV.ONLINE;
        String requestProperty2 = this.rc.config.getRequestProperty(RequestConstant.ENVIRONMENT);
        if (RequestConstant.ENV_PRE.equalsIgnoreCase(requestProperty2)) {
            env = ENV.PREPARE;
        } else if (RequestConstant.ENV_TEST.equalsIgnoreCase(requestProperty2)) {
            env = ENV.TEST;
        }
        if (env != NetworkSdkSetting.CURRENT_ENV) {
            NetworkSdkSetting.CURRENT_ENV = env;
            SessionCenter.switchEnvironment(env);
        }
        Config config = Config.getConfig(requestProperty, env);
        if (config == null) {
            build = new Builder().setAppkey(requestProperty).setEnv(env).setAuthCode(this.rc.config.getRequestProperty(RequestConstant.AUTH_CODE)).build();
        } else {
            build = config;
        }
        return SessionCenter.getInstance(build);
    }

    private Session tryGetSession() {
        Session session;
        SessionCenter sessionCenter = getSessionCenter();
        c httpUrl = this.rc.config.getHttpUrl();
        if (this.rc.config.getRequestType() == 1 && NetworkConfigCenter.isSpdyEnabled() && this.rc.config.getCurrentRetryTimes() == 0) {
            session = sessionCenter.get(checkCName(httpUrl), TypeLevel.SPDY, 5000);
        } else {
            session = null;
        }
        if (session == null && this.rc.config.isHttpSessionEnable() && !NetworkStatusHelper.h()) {
            session = sessionCenter.get(httpUrl, TypeLevel.HTTP, 0);
        }
        if (session == null) {
            ALog.i(TAG, "create HttpSession with local DNS", this.rc.seqNum, new Object[0]);
            session = new d(GlobalAppRuntimeInfo.getContext(), new a(httpUrl.d(), this.rc.seqNum, null));
        }
        this.rc.statisticData.connectionType = session.getConnType().toString();
        this.rc.statisticData.isSSL = session.getConnType().isSSL();
        ALog.i(TAG, "tryGetSession", this.rc.seqNum, "Session", session);
        return session;
    }

    private Request processRequest(Request request) {
        Request.Builder builder = null;
        if (this.rc.config.isRequestCookieEnabled()) {
            String cookie = CookieManager.getCookie(this.rc.config.getUrlString());
            if (!TextUtils.isEmpty(cookie)) {
                Request.Builder newBuilder = request.newBuilder();
                String str = (String) request.getHeaders().get(HttpConstant.COOKIE);
                if (str != null) {
                    str = StringUtils.concatString(str, "; ", cookie);
                } else {
                    str = cookie;
                }
                newBuilder.addHeader(HttpConstant.COOKIE, str);
                builder = newBuilder;
            }
        }
        if (this.entry != null) {
            if (builder == null) {
                builder = request.newBuilder();
            }
            if (this.entry.etag != null) {
                builder.addHeader("If-None-Match", this.entry.etag);
            }
            if (this.entry.lastModified > 0) {
                builder.addHeader("If-Modified-Since", CacheHelper.toGMTDate(this.entry.lastModified));
            }
        }
        if (builder == null) {
            return request;
        }
        return builder.build();
    }

    private void sendRequest(Session session, Request request) {
        if (session != null && !this.isCanceled) {
            final Request processRequest = processRequest(request);
            StatisticReqTimes.getIntance().putReq(processRequest.getHttpUrl());
            this.cancelable = session.request(processRequest, new RequestCb() {
                public void onResponseCode(int i, Map<String, List<String>> map) {
                    if (!NetworkTask.this.isDone.get()) {
                        if (ALog.isPrintLog(2)) {
                            StringBuilder stringBuilder = new StringBuilder("[onResponseCode]");
                            stringBuilder.append("responseCode:").append(i);
                            if (map != null) {
                                stringBuilder.append(", header:").append(map.toString());
                            }
                            ALog.i(NetworkTask.TAG, stringBuilder.toString(), NetworkTask.this.rc.seqNum, new Object[0]);
                        }
                        if (anet.channel.util.a.a(processRequest, i)) {
                            String b = anet.channel.util.a.b(map, HttpConstant.LOCATION);
                            if (b != null) {
                                c a = c.a(b);
                                if (a == null) {
                                    ALog.e(NetworkTask.TAG, "redirect url is invalid!", processRequest.getSeq(), "redirect url", b);
                                } else if (NetworkTask.this.isDone.compareAndSet(false, true)) {
                                    a.i();
                                    NetworkTask.this.rc.config.redirectToUrl(a);
                                    NetworkTask.this.rc.statisticData.host = NetworkTask.this.rc.config.getHttpUrl().b();
                                    NetworkTask.this.rc.isDone = new AtomicBoolean();
                                    NetworkTask.this.rc.runningTask = new NetworkTask(NetworkTask.this.rc, null, null);
                                    anet.channel.a.c.a(NetworkTask.this.rc.runningTask, 0);
                                    return;
                                } else {
                                    return;
                                }
                            }
                        }
                        try {
                            NetworkTask.this.rc.cancelTimeoutTask();
                            NetworkTask.this.statusCode = i;
                            CookieManager.setCookie(NetworkTask.this.rc.config.getUrlString(), (Map) map);
                            NetworkTask.this.contentLength = anet.channel.util.a.c(map);
                            if (i != HttpConstant.SC_NOT_MODIFIED || NetworkTask.this.entry == null) {
                                if (NetworkTask.this.cache != null && "GET".equals(processRequest.getMethod())) {
                                    NetworkTask.this.entry = CacheHelper.parseCacheHeaders(map);
                                    if (NetworkTask.this.entry != null) {
                                        anet.channel.util.a.c(map, HttpConstant.CACHE_CONTROL);
                                        map.put(HttpConstant.CACHE_CONTROL, Arrays.asList(new String[]{"no-store"}));
                                        NetworkTask.this.cacheBuffer = new ByteArrayOutputStream(NetworkTask.this.contentLength != 0 ? NetworkTask.this.contentLength : 5120);
                                    }
                                }
                                NetworkTask.this.rc.callback.onResponseCode(i, map);
                                return;
                            }
                            NetworkTask.this.entry.responseHeaders.putAll(map);
                            NetworkTask.this.rc.callback.onResponseCode(200, NetworkTask.this.entry.responseHeaders);
                            NetworkTask.this.rc.callback.onDataReceiveSize(1, NetworkTask.this.entry.data.length, ByteArray.wrap(NetworkTask.this.entry.data));
                        } catch (Throwable e) {
                            ALog.w(NetworkTask.TAG, "[onResponseCode] error.", NetworkTask.this.rc.seqNum, e, new Object[0]);
                        }
                    }
                }

                public void onDataReceive(ByteArray byteArray, boolean z) {
                    if (!NetworkTask.this.isDone.get()) {
                        if (NetworkTask.this.dataChunkIndex == 0) {
                            ALog.i(NetworkTask.TAG, "[onDataReceive] receive first data chunk!", NetworkTask.this.rc.seqNum, new Object[0]);
                        }
                        if (z) {
                            ALog.i(NetworkTask.TAG, "[onDataReceive] receive last data chunk!", NetworkTask.this.rc.seqNum, new Object[0]);
                        }
                        try {
                            NetworkTask networkTask = NetworkTask.this;
                            networkTask.dataChunkIndex++;
                            NetworkTask.this.rc.callback.onDataReceiveSize(NetworkTask.this.dataChunkIndex, NetworkTask.this.contentLength, byteArray);
                            if (NetworkTask.this.cacheBuffer != null) {
                                NetworkTask.this.cacheBuffer.write(byteArray.getBuffer(), 0, byteArray.getDataLength());
                                if (z) {
                                    String urlString = NetworkTask.this.rc.config.getUrlString();
                                    NetworkTask.this.entry.data = NetworkTask.this.cacheBuffer.toByteArray();
                                    long currentTimeMillis = System.currentTimeMillis();
                                    NetworkTask.this.cache.put(urlString, NetworkTask.this.entry);
                                    ALog.i(NetworkTask.TAG, "write cache", NetworkTask.this.rc.seqNum, "cost", Long.valueOf(System.currentTimeMillis() - currentTimeMillis), "size", Integer.valueOf(NetworkTask.this.entry.data.length), "key", urlString);
                                }
                            }
                        } catch (Throwable e) {
                            ALog.w(NetworkTask.TAG, "[onDataReceive] error.", NetworkTask.this.rc.seqNum, e, new Object[0]);
                        }
                    }
                }

                public void onFinish(int i, String str, RequestStatistic requestStatistic) {
                    if (!NetworkTask.this.isDone.getAndSet(true)) {
                        DefaultFinishEvent defaultFinishEvent;
                        NetworkTask.this.rc.cancelTimeoutTask();
                        if (ALog.isPrintLog(2)) {
                            ALog.i(NetworkTask.TAG, "[onFinish]", NetworkTask.this.rc.seqNum, "code", Integer.valueOf(i), NotificationCompat.CATEGORY_MESSAGE, str);
                        }
                        if (i < 0) {
                            try {
                                if (NetworkTask.this.rc.config.isAllowRetry()) {
                                    NetworkTask.this.rc.config.retryRequest();
                                    NetworkTask.this.rc.isDone = new AtomicBoolean();
                                    NetworkTask.this.rc.runningTask = new NetworkTask(NetworkTask.this.rc, NetworkTask.this.cache, NetworkTask.this.entry);
                                    anet.channel.a.c.a(new Runnable() {
                                        public void run() {
                                            anet.channel.a.c.b(NetworkTask.this.rc.runningTask);
                                        }
                                    }, (long) (NetworkTask.this.rc.config.getCurrentRetryTimes() * m_AppUI.MSG_APP_DATA_OK), TimeUnit.MILLISECONDS);
                                    return;
                                }
                            } catch (Exception e) {
                                return;
                            }
                        }
                        if (NetworkTask.this.statusCode == 0) {
                            NetworkTask.this.statusCode = i;
                        }
                        requestStatistic.statusCode = NetworkTask.this.statusCode;
                        requestStatistic.msg = str;
                        NetworkTask.this.rc.statisticData.filledBy(requestStatistic);
                        if (NetworkTask.this.statusCode != HttpConstant.SC_NOT_MODIFIED || NetworkTask.this.entry == null) {
                            defaultFinishEvent = new DefaultFinishEvent(NetworkTask.this.statusCode, str, NetworkTask.this.rc.statisticData);
                        } else {
                            requestStatistic.protocolType = "cache";
                            defaultFinishEvent = new DefaultFinishEvent(200, str, NetworkTask.this.rc.statisticData);
                        }
                        NetworkTask.this.rc.callback.onFinish(defaultFinishEvent);
                        if (ALog.isPrintLog(2)) {
                            ALog.i(NetworkTask.TAG, NetworkTask.this.rc.statisticData.toString(), NetworkTask.this.rc.seqNum, new Object[0]);
                        }
                        if (i != ErrorConstant.ERROR_NO_NETWORK) {
                            AppMonitor.getInstance().commitStat(requestStatistic);
                        }
                        if (i >= 0) {
                            b.a().a(requestStatistic.start, requestStatistic.start + requestStatistic.oneWayTime, requestStatistic.recDataSize);
                        }
                        anet.channel.flow.c.a().commitFlow(new anet.channel.flow.b(NetworkTask.this.f_refer, requestStatistic));
                        NetworkStat.getNetworkStat().put(NetworkTask.this.rc.config.getUrlString(), NetworkTask.this.rc.statisticData);
                        StatisticReqTimes.getIntance().updateReqTimes(processRequest.getHttpUrl(), System.currentTimeMillis());
                    }
                }
            });
        }
    }
}
