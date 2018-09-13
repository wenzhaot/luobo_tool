package com.taobao.accs.ut.statistics;

import com.feng.library.okhttp.utils.OkHttpUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class c implements UTInterface {
    public int a;
    public int b;
    public boolean c = false;
    public int d = 0;
    public int e = 0;
    public String f;
    public String g;
    public long h;
    public boolean i;
    public boolean j;
    private long k = 0;

    public void commitUT() {
        Throwable th;
        long currentTimeMillis = System.currentTimeMillis();
        if (ALog.isPrintLog(Level.D)) {
            ALog.d("MonitorStatistic", "commitUT interval:" + (currentTimeMillis - this.k) + " interval1:" + (currentTimeMillis - this.h), new Object[0]);
        }
        if (currentTimeMillis - this.k > 1200000 && currentTimeMillis - this.h > OkHttpUtils.DEFAULT_MILLISECONDS) {
            Map hashMap = new HashMap();
            String valueOf;
            String valueOf2;
            try {
                valueOf = String.valueOf(this.d);
                try {
                    valueOf2 = String.valueOf(this.e);
                    try {
                        Object valueOf3 = String.valueOf(Constants.SDK_VERSION_CODE);
                        hashMap.put("connStatus", String.valueOf(this.a));
                        hashMap.put("connType", String.valueOf(this.b));
                        hashMap.put("tcpConnected", String.valueOf(this.c));
                        hashMap.put("proxy", String.valueOf(this.f));
                        hashMap.put("startServiceTime", String.valueOf(this.h));
                        hashMap.put("commitTime", String.valueOf(currentTimeMillis));
                        hashMap.put("networkAvailable", String.valueOf(this.i));
                        hashMap.put("threadIsalive", String.valueOf(this.j));
                        hashMap.put("url", this.g);
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, valueOf, valueOf2, (String) valueOf3, hashMap), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(66001, "MONITOR", (Object) valueOf, (Object) valueOf2, valueOf3, hashMap);
                        this.k = currentTimeMillis;
                    } catch (Throwable th2) {
                        th = th2;
                        ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, valueOf, valueOf2, null, hashMap) + " " + th.toString(), new Object[0]);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    valueOf2 = null;
                    ALog.d("MonitorStatistic", UTMini.getCommitInfo(66001, valueOf, valueOf2, null, hashMap) + " " + th.toString(), new Object[0]);
                }
            } catch (Throwable th4) {
                th = th4;
                valueOf2 = null;
                valueOf = null;
            }
        }
    }
}
