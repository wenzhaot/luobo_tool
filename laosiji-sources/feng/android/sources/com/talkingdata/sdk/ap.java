package com.talkingdata.sdk;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import java.io.File;

/* compiled from: td */
public class ap {
    private static volatile ap a = null;
    private PackageInfo b = null;

    private ap() {
    }

    public static ap a() {
        if (a == null) {
            synchronized (ap.class) {
                if (a == null) {
                    a = new ap();
                }
            }
        }
        return a;
    }

    private synchronized boolean i(Context context) {
        boolean z;
        try {
            if (this.b == null) {
                this.b = context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            }
            z = true;
        } catch (Throwable th) {
            z = false;
        }
        return z;
    }

    public String a(Context context) {
        if (context == null) {
            return null;
        }
        try {
            i(context);
            return context.getPackageName();
        } catch (Throwable th) {
            return "";
        }
    }

    public int b(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            if (i(context)) {
                return this.b.versionCode;
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }

    public String c(Context context) {
        if (context == null) {
            return "unknown";
        }
        try {
            if (i(context)) {
                return this.b.versionName;
            }
            return "unknown";
        } catch (Throwable th) {
            return "unknown";
        }
    }

    public long d(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            if (i(context) && bo.a(9)) {
                return this.b.firstInstallTime;
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }

    public long e(Context context) {
        if (context == null) {
            return -1;
        }
        try {
            if (i(context) && bo.a(9)) {
                return this.b.lastUpdateTime;
            }
            return -1;
        } catch (Throwable th) {
            return -1;
        }
    }

    public long f(Context context) {
        long j = -1;
        if (context == null) {
            return j;
        }
        try {
            i(context);
            return new File(context.getApplicationInfo().sourceDir).length();
        } catch (Throwable th) {
            return j;
        }
    }

    public String g(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (!i(context)) {
                return null;
            }
            Signature[] signatureArr = this.b.signatures;
            if (signatureArr.length < 1) {
                return null;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(signatureArr[0].toCharsString());
            return stringBuffer.toString();
        } catch (Throwable th) {
            return null;
        }
    }

    public String h(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        try {
            i(context);
            return context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
        } catch (Throwable th) {
            return str;
        }
    }
}
