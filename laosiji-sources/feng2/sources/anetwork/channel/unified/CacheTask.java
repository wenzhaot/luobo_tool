package anetwork.channel.unified;

import anet.channel.appmonitor.AppMonitor;
import anet.channel.bytes.ByteArray;
import anet.channel.statist.StatObject;
import anet.channel.util.ALog;
import anetwork.channel.aidl.DefaultFinishEvent;
import anetwork.channel.cache.Cache;
import anetwork.channel.cache.Cache.Entry;

/* compiled from: Taobao */
public class CacheTask implements IUnifiedTask {
    private static final String TAG = "anet.CacheTask";
    private Cache cache = null;
    private volatile boolean isCanceled = false;
    private RequestContext rc = null;

    public CacheTask(RequestContext requestContext, Cache cache) {
        this.rc = requestContext;
        this.cache = cache;
    }

    public void cancel() {
        this.isCanceled = true;
    }

    public void run() {
        if (!this.isCanceled) {
            StatObject statistic = this.rc.config.getStatistic();
            if (this.cache != null) {
                String urlString = this.rc.config.getUrlString();
                long currentTimeMillis = System.currentTimeMillis();
                Entry entry = this.cache.get(urlString);
                long currentTimeMillis2 = System.currentTimeMillis();
                statistic.cacheTime = currentTimeMillis2 - currentTimeMillis;
                if (ALog.isPrintLog(2)) {
                    int length;
                    String str = TAG;
                    String str2 = "read cache";
                    String str3 = this.rc.seqNum;
                    Object[] objArr = new Object[8];
                    objArr[0] = "hit";
                    objArr[1] = Boolean.valueOf(entry != null);
                    objArr[2] = "cost";
                    objArr[3] = Long.valueOf(statistic.cacheTime);
                    objArr[4] = "length";
                    if (entry != null) {
                        length = entry.data.length;
                    } else {
                        length = 0;
                    }
                    objArr[5] = Integer.valueOf(length);
                    objArr[6] = "key";
                    objArr[7] = urlString;
                    ALog.i(str, str2, str3, objArr);
                }
                if (entry == null || !entry.isFresh()) {
                    if (!this.isCanceled) {
                        Object networkTask = new NetworkTask(this.rc, this.cache, entry);
                        this.rc.runningTask = networkTask;
                        networkTask.run();
                    }
                } else if (this.rc.isDone.compareAndSet(false, true)) {
                    this.rc.cancelTimeoutTask();
                    statistic.ret = true;
                    statistic.statusCode = 200;
                    statistic.protocolType = "cache";
                    statistic.oneWayTime = currentTimeMillis2 - statistic.start;
                    this.rc.statisticData.filledBy(statistic);
                    if (ALog.isPrintLog(2)) {
                        ALog.i(TAG, "hit fresh cache", this.rc.seqNum, new Object[0]);
                        ALog.i(TAG, this.rc.statisticData.toString(), this.rc.seqNum, new Object[0]);
                    }
                    this.rc.callback.onResponseCode(200, entry.responseHeaders);
                    this.rc.callback.onDataReceiveSize(1, entry.data.length, ByteArray.wrap(entry.data));
                    this.rc.callback.onFinish(new DefaultFinishEvent(200, null, this.rc.statisticData));
                    AppMonitor.getInstance().commitStat(statistic);
                }
            }
        }
    }
}
