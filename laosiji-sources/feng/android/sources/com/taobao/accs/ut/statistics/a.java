package com.taobao.accs.ut.statistics;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class a implements UTInterface {
    public String a;
    public String b;
    public boolean c;
    public String d;
    private final String e = "BindApp";
    private boolean f = false;

    public void a(String str) {
        this.d = str;
    }

    public void a(int i) {
        switch (i) {
            case -4:
                a("msg too large");
                return;
            case -3:
                a("service not available");
                return;
            case -2:
                a("param error");
                return;
            case -1:
                a("network fail");
                return;
            case 200:
                return;
            case 300:
                a("app not bind");
                return;
            default:
                a(String.valueOf(i));
                return;
        }
    }

    public void commitUT() {
        b("BindApp");
    }

    private void b(String str) {
        Throwable th;
        if (!this.f) {
            this.f = true;
            Map hashMap = new HashMap();
            String str2;
            String valueOf;
            try {
                str2 = this.a;
                try {
                    valueOf = String.valueOf(Constants.SDK_VERSION_CODE);
                    try {
                        hashMap.put("device_id", this.a);
                        hashMap.put("bind_date", this.b);
                        hashMap.put("ret", this.c ? "y" : "n");
                        hashMap.put("fail_reasons", this.d);
                        hashMap.put("push_token", "");
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d("accs.BindAppStatistic", UTMini.getCommitInfo(66001, str2, null, valueOf, hashMap), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(66001, str, (Object) str2, null, (Object) valueOf, hashMap);
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    valueOf = null;
                }
            } catch (Throwable th4) {
                th = th4;
                valueOf = null;
                str2 = null;
                ALog.d("accs.BindAppStatistic", UTMini.getCommitInfo(66001, str2, null, valueOf, hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
