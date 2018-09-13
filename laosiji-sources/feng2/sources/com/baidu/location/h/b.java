package com.baidu.location.h;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import com.baidu.android.bbalbs.common.util.CommonParam;
import com.baidu.location.a.k;
import com.baidu.location.f;
import java.util.Locale;

public class b {
    public static String d = null;
    public static String e = null;
    public static String f = null;
    public static String g = null;
    private static b h = null;
    public String a = null;
    public String b = null;
    public String c = null;
    private boolean i = false;

    private b() {
        if (f.getServiceContext() != null) {
            a(f.getServiceContext());
        }
    }

    public static b a() {
        if (h == null) {
            h = new b();
        }
        return h;
    }

    public String a(boolean z) {
        return a(z, null);
    }

    public String a(boolean z, String str) {
        StringBuffer stringBuffer = new StringBuffer(256);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.21f);
        if (z) {
            if (k.g.equals("all")) {
                stringBuffer.append("&addr=allj");
            }
            if (k.h || k.j || k.k || k.i) {
                stringBuffer.append("&sema=");
                if (k.h) {
                    stringBuffer.append("aptag|");
                }
                if (k.i) {
                    stringBuffer.append("aptagd|");
                }
                if (k.j) {
                    stringBuffer.append("poiregion|");
                }
                if (k.k) {
                    stringBuffer.append("regular");
                }
            }
        }
        if (z) {
            if (str == null) {
                stringBuffer.append("&coor=gcj02");
            } else {
                stringBuffer.append("&coor=");
                stringBuffer.append(str);
            }
        }
        if (this.b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
            if (!(this.a == null || this.a.equals("NULL") || this.b.contains(new StringBuffer(this.a).reverse().toString()))) {
                stringBuffer.append("&Aim=");
                stringBuffer.append(this.a);
            }
        }
        if (this.c != null) {
            stringBuffer.append("&Aid=");
            stringBuffer.append(this.c);
        }
        stringBuffer.append("&fw=");
        stringBuffer.append(f.getFrameVersion());
        stringBuffer.append("&lt=1");
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        String b = k.b();
        if (b != null) {
            stringBuffer.append("&laip=");
            stringBuffer.append(b);
        }
        if (k.a().e() != 0.0f) {
            stringBuffer.append("&altv=");
            stringBuffer.append(String.format(Locale.US, "%.2f", new Object[]{Float.valueOf(r0)}));
        }
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        if (z) {
            stringBuffer.append("&sv=");
            b = VERSION.RELEASE;
            if (b != null && b.length() > 6) {
                b = b.substring(0, 6);
            }
            stringBuffer.append(b);
        }
        return stringBuffer.toString();
    }

    public void a(Context context) {
        if (context != null && !this.i) {
            try {
                this.a = ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
            } catch (Exception e) {
                this.a = "NULL";
            }
            try {
                this.b = CommonParam.a(context);
            } catch (Exception e2) {
                this.b = null;
            }
            try {
                this.c = com.baidu.android.bbalbs.common.util.b.b(context);
            } catch (Exception e3) {
                this.c = null;
            }
            try {
                d = context.getPackageName();
            } catch (Exception e4) {
                d = null;
            }
            k.n = "" + this.b;
            this.i = true;
        }
    }

    public void a(String str, String str2) {
        e = str;
        d = str2;
    }

    public String b() {
        return this.b != null ? "v7.21|" + this.b + "|" + Build.MODEL : "v7.21|" + this.a + "|" + Build.MODEL;
    }

    public String c() {
        StringBuffer stringBuffer = new StringBuffer(200);
        if (this.b != null) {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
        } else {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        }
        try {
            stringBuffer.append("&mb=");
            stringBuffer.append(Build.MODEL);
        } catch (Exception e) {
        }
        stringBuffer.append("&pack=");
        try {
            stringBuffer.append(d);
        } catch (Exception e2) {
        }
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.21f);
        return stringBuffer.toString();
    }

    public String d() {
        return d != null ? b() + "|" + d : b();
    }

    public String e() {
        StringBuffer stringBuffer = new StringBuffer();
        if (this.b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(this.a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(this.b);
        }
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.21f);
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&os=A");
        stringBuffer.append(VERSION.SDK);
        stringBuffer.append("&prod=");
        stringBuffer.append(e + ":" + d);
        stringBuffer.append(k.e(f.getServiceContext()));
        stringBuffer.append("&resid=");
        stringBuffer.append("12");
        return stringBuffer.toString();
    }
}
