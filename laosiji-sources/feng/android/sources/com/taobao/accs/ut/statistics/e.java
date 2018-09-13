package com.taobao.accs.ut.statistics;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class e implements UTInterface {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    private final String g = "sendAck";
    private boolean h = false;

    public void commitUT() {
        Throwable th;
        if (!this.h) {
            this.h = true;
            Map hashMap = new HashMap();
            String str;
            String valueOf;
            try {
                str = this.a;
                try {
                    valueOf = String.valueOf(Constants.SDK_VERSION_CODE);
                    try {
                        hashMap.put("device_id", this.a);
                        hashMap.put("session_id", this.b);
                        hashMap.put("data_id", this.c);
                        hashMap.put("ack_date", this.d);
                        hashMap.put("service_id", this.e);
                        hashMap.put("fail_reasons", this.f);
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str, null, valueOf, hashMap), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(66001, "sendAck", (Object) str, null, (Object) valueOf, hashMap);
                    } catch (Throwable th2) {
                        th = th2;
                        ALog.d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str, null, valueOf, hashMap) + " " + th.toString(), new Object[0]);
                    }
                } catch (Throwable th3) {
                    th = th3;
                    valueOf = null;
                }
            } catch (Throwable th4) {
                th = th4;
                valueOf = null;
                str = null;
                ALog.d("accs.SendAckStatistic", UTMini.getCommitInfo(66001, str, null, valueOf, hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
