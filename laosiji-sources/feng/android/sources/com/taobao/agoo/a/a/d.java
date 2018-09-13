package com.taobao.agoo.a.a;

import android.text.TextUtils;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.e.a;

/* compiled from: Taobao */
public class d extends b {
    public static final String JSON_CMD_DISABLEPUSH = "disablePush";
    public static final String JSON_CMD_ENABLEPUSH = "enablePush";
    public String a;
    public String b;
    public String c;

    public byte[] a() {
        try {
            a aVar = new a();
            aVar.a(b.JSON_CMD, this.e).a(Constants.KEY_APP_KEY, this.a);
            if (TextUtils.isEmpty(this.b)) {
                aVar.a("utdid", this.c);
            } else {
                aVar.a("deviceId", this.b);
            }
            ALog.i("SwitchDO", "buildData", "data", aVar.a().toString());
            return aVar.a().toString().getBytes(com.qiniu.android.common.Constants.UTF_8);
        } catch (Throwable th) {
            ALog.e("SwitchDO", "buildData", th, new Object[0]);
            return null;
        }
    }

    public static byte[] a(String str, String str2, String str3, boolean z) {
        d dVar = new d();
        dVar.a = str;
        dVar.b = str2;
        dVar.c = str3;
        if (z) {
            dVar.e = JSON_CMD_ENABLEPUSH;
        } else {
            dVar.e = JSON_CMD_DISABLEPUSH;
        }
        return dVar.a();
    }
}
