package com.taobao.accs.ut.statistics;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.common.SocializeConstants;
import java.util.HashMap;
import java.util.Map;

/* compiled from: Taobao */
public class d implements UTInterface {
    public String a;
    public String b;
    public String c;
    public String d;
    public String e;
    public String f;
    public String g;
    public boolean h = false;
    public String i;
    private final String j = "receiveMessage";
    private boolean k = false;

    public void commitUT() {
        Throwable th;
        if (!this.k) {
            this.k = true;
            Map hashMap = new HashMap();
            String str;
            String valueOf;
            try {
                str = this.a;
                try {
                    valueOf = String.valueOf(Constants.SDK_VERSION_CODE);
                    try {
                        hashMap.put("device_id", this.a);
                        hashMap.put("data_id", this.b);
                        hashMap.put("receive_date", this.c);
                        hashMap.put("to_bz_date", this.d);
                        hashMap.put("service_id", this.e);
                        hashMap.put("data_length", this.f);
                        hashMap.put(MsgConstant.INAPP_MSG_TYPE, this.g);
                        hashMap.put("repeat", this.h ? "y" : "n");
                        hashMap.put(SocializeConstants.TENCENT_UID, this.i);
                        if (ALog.isPrintLog(Level.D)) {
                            ALog.d("accs.ReceiveMessage", UTMini.getCommitInfo(66001, str, null, valueOf, hashMap), new Object[0]);
                        }
                        UTMini.getInstance().commitEvent(66001, "receiveMessage", (Object) str, null, (Object) valueOf, hashMap);
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
                str = null;
                ALog.d("accs.ReceiveMessage", UTMini.getCommitInfo(66001, str, null, valueOf, hashMap) + " " + th.toString(), new Object[0]);
            }
        }
    }
}
