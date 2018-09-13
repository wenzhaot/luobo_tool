package anetwork.channel.unified;

import anetwork.channel.entity.RequestConfig;
import anetwork.channel.interceptor.Callback;
import anetwork.channel.statist.StatisticData;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

/* compiled from: Taobao */
class RequestContext {
    public Callback callback;
    public final RequestConfig config;
    public volatile AtomicBoolean isDone = new AtomicBoolean();
    public volatile IUnifiedTask runningTask = null;
    public final String seqNum;
    public volatile StatisticData statisticData = new StatisticData();
    public volatile Future timeoutTask = null;

    public RequestContext(RequestConfig requestConfig, Callback callback) {
        this.config = requestConfig;
        this.seqNum = requestConfig.getSeqNo();
        this.callback = callback;
        this.statisticData.host = requestConfig.getHttpUrl().b();
    }

    public void cancelTimeoutTask() {
        Future future = this.timeoutTask;
        if (future != null) {
            future.cancel(true);
            this.timeoutTask = null;
        }
    }

    public void cancelRunningTask() {
        if (this.runningTask != null) {
            this.runningTask.cancel();
            this.runningTask = null;
        }
    }
}
