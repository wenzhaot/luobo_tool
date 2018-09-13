package anetwork.channel.statist;

import android.text.TextUtils;
import anet.channel.util.ALog;
import anet.channel.util.c;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.json.JSONObject;

/* compiled from: Taobao */
public class StatisticReqTimes {
    private static final String TAG = "awcn.StatisticReqTimes";
    private static StatisticReqTimes instance;
    private Set<String> currentReqUrls;
    private long finalResult;
    private boolean isStarting;
    private long startPoint;
    private Set<String> whiteReqUrls;

    private StatisticReqTimes() {
        initAttrs();
    }

    public static StatisticReqTimes getIntance() {
        if (instance == null) {
            synchronized (StatisticReqTimes.class) {
                if (instance == null) {
                    instance = new StatisticReqTimes();
                }
            }
        }
        return instance;
    }

    private void initAttrs() {
        this.isStarting = false;
        this.startPoint = 0;
        this.finalResult = 0;
        if (this.currentReqUrls == null) {
            this.currentReqUrls = new HashSet();
        } else {
            this.currentReqUrls.clear();
        }
        if (this.whiteReqUrls == null) {
            this.whiteReqUrls = new HashSet();
        }
    }

    public void updateWhiteReqUrls(String str) {
        if (this.whiteReqUrls == null) {
            this.whiteReqUrls = new HashSet();
        } else {
            this.whiteReqUrls.clear();
        }
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "urlsFromOrange: " + str, null, new Object[0]);
        }
        if (!TextUtils.isEmpty(str)) {
            try {
                Iterator keys = new JSONObject(str).keys();
                while (keys.hasNext()) {
                    this.whiteReqUrls.add((String) keys.next());
                }
            } catch (Exception e) {
                ALog.e(TAG, "whiteReqUrls from orange isnot json format", null, new Object[0]);
            }
        }
    }

    public void start() {
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "start statistic req times", null, new Object[0]);
        }
        initAttrs();
        this.isStarting = true;
    }

    public void putReq(c cVar) {
        if (this.isStarting && cVar != null) {
            String c = cVar.c();
            if (this.whiteReqUrls.contains(c)) {
                if (this.currentReqUrls.isEmpty()) {
                    this.startPoint = System.currentTimeMillis();
                }
                this.currentReqUrls.add(c);
            }
        }
    }

    public void updateReqTimes(c cVar, long j) {
        if (this.isStarting && j > 0 && cVar != null) {
            if (this.currentReqUrls.remove(cVar.c()) && this.currentReqUrls.isEmpty()) {
                long currentTimeMillis = System.currentTimeMillis() - this.startPoint;
                ALog.i(TAG, "this req spend times: " + currentTimeMillis, null, new Object[0]);
                this.finalResult = currentTimeMillis + this.finalResult;
            }
        }
    }

    public long end() {
        long j = 0;
        if (this.isStarting) {
            j = this.finalResult;
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "finalResult:" + this.finalResult, null, new Object[0]);
            }
        }
        initAttrs();
        return j;
    }
}
