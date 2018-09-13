package com.taobao.agoo.a.a;

import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;

/* compiled from: Taobao */
public class a extends b {
    public static final String JSON_CMD_REMOVEALIAS = "removeAlias";
    public static final String JSON_CMD_SETALIAS = "setAlias";
    public static final String JSON_PUSH_USER_TOKEN = "pushAliasToken";
    public String a;
    public String b;
    public String c;
    public String d;

    public byte[] a() {
        byte[] bArr = null;
        try {
            ALog.i("AliasDO", "buildData", "data", new com.taobao.accs.utl.e.a().a(b.JSON_CMD, this.e).a(Constants.KEY_APP_KEY, this.a).a("deviceId", this.b).a("alias", this.c).a(JSON_PUSH_USER_TOKEN, this.d).a().toString());
            return new com.taobao.accs.utl.e.a().a(b.JSON_CMD, this.e).a(Constants.KEY_APP_KEY, this.a).a("deviceId", this.b).a("alias", this.c).a(JSON_PUSH_USER_TOKEN, this.d).a().toString().getBytes(com.qiniu.android.common.Constants.UTF_8);
        } catch (Throwable th) {
            ALog.e("AliasDO", "buildData", th, new Object[0]);
            return bArr;
        }
    }

    public static byte[] a(String str, String str2, String str3) {
        a aVar = new a();
        aVar.a = str;
        aVar.b = str2;
        aVar.c = str3;
        aVar.e = "setAlias";
        return aVar.a();
    }

    public static byte[] b(String str, String str2, String str3) {
        a aVar = new a();
        aVar.a = str;
        aVar.b = str2;
        aVar.d = str3;
        aVar.e = JSON_CMD_REMOVEALIAS;
        return aVar.a();
    }
}
