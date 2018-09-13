package anetwork.channel.unified;

import anet.channel.a.c;
import anet.channel.appmonitor.AppMonitor;
import anet.channel.request.Request;
import anet.channel.statist.ExceptionStatistic;
import anet.channel.statist.StatObject;
import anet.channel.util.ALog;
import anet.channel.util.ErrorConstant;
import anet.channel.util.HttpConstant;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.config.NetworkConfigCenter;
import anetwork.channel.entity.Repeater;
import anetwork.channel.entity.RequestConfig;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.interceptor.Interceptor.Chain;
import anetwork.channel.interceptor.InterceptorManager;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
class UnifiedRequestTask {
    private static final String TAG = "anet.UnifiedRequestTask";
    private RequestContext rc;

    /* compiled from: Taobao */
    class UnifiedRequestChain implements Chain {
        private Callback callback = null;
        private int index = 0;
        private Request request = null;

        UnifiedRequestChain(int i, Request request, Callback callback) {
            this.index = i;
            this.request = request;
            this.callback = callback;
        }

        public Request request() {
            return this.request;
        }

        public Callback callback() {
            return this.callback;
        }

        public Future proceed(Request request, Callback callback) {
            if (UnifiedRequestTask.this.rc.isDone.get()) {
                ALog.i(UnifiedRequestTask.TAG, "request canneled or timeout in processing interceptor", request.getSeq(), new Object[0]);
                return null;
            } else if (this.index < InterceptorManager.getSize()) {
                return InterceptorManager.getInterceptor(this.index).intercept(new UnifiedRequestChain(this.index + 1, request, callback));
            } else {
                Cache cache;
                UnifiedRequestTask.this.rc.config.setAwcnRequest(request);
                UnifiedRequestTask.this.rc.callback = callback;
                if (!NetworkConfigCenter.isHttpCacheEnable() || "no-cache".equals(request.getHeaders().get(HttpConstant.CACHE_CONTROL))) {
                    cache = null;
                } else {
                    cache = CacheManager.getCache(UnifiedRequestTask.this.rc.config.getUrlString(), UnifiedRequestTask.this.rc.config.getHeaders());
                }
                UnifiedRequestTask.this.rc.runningTask = cache != null ? new CacheTask(UnifiedRequestTask.this.rc, cache) : new NetworkTask(UnifiedRequestTask.this.rc, null, null);
                c.a(UnifiedRequestTask.this.rc.runningTask, 0);
                UnifiedRequestTask.this.commitTimeoutTask();
                return null;
            }
        }
    }

    public UnifiedRequestTask(RequestConfig requestConfig, Repeater repeater) {
        repeater.setSeqNo(requestConfig.getSeqNo());
        this.rc = new RequestContext(requestConfig, repeater);
        requestConfig.getStatistic().start = System.currentTimeMillis();
    }

    public Future request() {
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "request", this.rc.seqNum, "Url", this.rc.config.getUrlString());
        }
        c.a(new Runnable() {
            public void run() {
                new UnifiedRequestChain(0, UnifiedRequestTask.this.rc.config.getAwcnRequest(), UnifiedRequestTask.this.rc.callback).proceed(UnifiedRequestTask.this.rc.config.getAwcnRequest(), UnifiedRequestTask.this.rc.callback);
            }
        }, 0);
        return new FutureResponse(this);
    }

    private void commitTimeoutTask() {
        this.rc.timeoutTask = c.a(new Runnable() {
            public void run() {
                if (UnifiedRequestTask.this.rc.isDone.compareAndSet(false, true)) {
                    ALog.e(UnifiedRequestTask.TAG, "task time out", UnifiedRequestTask.this.rc.seqNum, new Object[0]);
                    UnifiedRequestTask.this.rc.cancelRunningTask();
                    UnifiedRequestTask.this.rc.statisticData.resultCode = ErrorConstant.ERROR_REQUEST_TIME_OUT;
                    UnifiedRequestTask.this.rc.callback.onFinish(new DefaultFinishEvent(ErrorConstant.ERROR_REQUEST_TIME_OUT, null, UnifiedRequestTask.this.rc.statisticData));
                    StatObject statistic = UnifiedRequestTask.this.rc.config.getStatistic();
                    statistic.statusCode = ErrorConstant.ERROR_REQUEST_TIME_OUT;
                    statistic.msg = ErrorConstant.getErrMsg(ErrorConstant.ERROR_REQUEST_TIME_OUT);
                    AppMonitor.getInstance().commitStat(statistic);
                    AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_REQUEST_TIME_OUT, null, statistic, null));
                }
            }
        }, (long) this.rc.config.getWaitTimeout(), TimeUnit.MILLISECONDS);
    }

    void cancelTask() {
        if (this.rc.isDone.compareAndSet(false, true)) {
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "task cancelled", this.rc.seqNum, new Object[0]);
            }
            this.rc.cancelRunningTask();
            this.rc.cancelTimeoutTask();
            this.rc.statisticData.resultCode = ErrorConstant.ERROR_REQUEST_CANCEL;
            this.rc.callback.onFinish(new DefaultFinishEvent(ErrorConstant.ERROR_REQUEST_CANCEL, null, this.rc.statisticData));
            AppMonitor.getInstance().commitStat(new ExceptionStatistic(ErrorConstant.ERROR_REQUEST_CANCEL, null, this.rc.config.getStatistic(), null));
        }
    }
}
