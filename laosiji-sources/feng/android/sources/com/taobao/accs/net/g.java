package com.taobao.accs.net;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import com.taobao.accs.internal.AccsJobService;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.i;

/* compiled from: Taobao */
public abstract class g {
    protected static volatile g b;
    private static final int[] c = new int[]{270, 360, 480};
    protected Context a;
    private int d;
    private long e;
    private boolean f = false;
    private int[] g = new int[]{0, 0, 0};
    private boolean h = true;

    protected abstract void a(int i);

    protected g(Context context) {
        try {
            this.a = context;
            this.d = 0;
            this.e = System.currentTimeMillis();
            this.h = i.b();
        } catch (Throwable th) {
            ALog.e("HeartbeatManager", "HeartbeatManager", th, new Object[0]);
        }
    }

    public static g a(Context context) {
        if (b == null) {
            synchronized (g.class) {
                if (b == null) {
                    if (VERSION.SDK_INT < 21 || !b(context)) {
                        ALog.i("HeartbeatManager", "hb use alarm", new Object[0]);
                        b = new a(context);
                    } else {
                        ALog.i("HeartbeatManager", "hb use job", new Object[0]);
                        b = new q(context);
                    }
                }
            }
        }
        return b;
    }

    private static boolean b(Context context) {
        boolean z = false;
        try {
            return context.getPackageManager().getServiceInfo(new ComponentName(context.getPackageName(), AccsJobService.class.getName()), 0).isEnabled();
        } catch (Throwable th) {
            ALog.e("HeartbeatManager", "isJobServiceExist", th, new Object[z]);
            return z;
        }
    }

    public synchronized void a() {
        try {
            if (this.e < 0) {
                this.e = System.currentTimeMillis();
            }
            int b = b();
            if (ALog.isPrintLog(Level.D)) {
                ALog.d("HeartbeatManager", "set " + b, new Object[0]);
            }
            a(b);
        } catch (Throwable th) {
            ALog.e("HeartbeatManager", "set", th, new Object[0]);
        }
        return;
    }

    public int b() {
        int i = 270;
        if (this.h) {
            i = c[this.d];
        }
        this.h = i.b();
        return i;
    }

    public void c() {
        int i;
        this.e = -1;
        if (this.f) {
            int[] iArr = this.g;
            int i2 = this.d;
            iArr[i2] = iArr[i2] + 1;
        }
        if (this.d > 0) {
            i = this.d - 1;
        } else {
            i = 0;
        }
        this.d = i;
        ALog.d("HeartbeatManager", "onNetworkTimeout", new Object[0]);
    }

    public void d() {
        this.e = -1;
        ALog.d("HeartbeatManager", "onNetworkFail", new Object[0]);
    }

    public void e() {
        ALog.d("HeartbeatManager", "onHeartbeatSucc", new Object[0]);
        if (System.currentTimeMillis() - this.e <= 7199000) {
            this.f = false;
            this.g[this.d] = 0;
        } else if (this.d < c.length - 1 && this.g[this.d] <= 2) {
            ALog.d("HeartbeatManager", "upgrade", new Object[0]);
            this.d++;
            this.f = true;
            this.e = System.currentTimeMillis();
        }
    }

    public void f() {
        this.d = 0;
        this.e = System.currentTimeMillis();
        ALog.d("HeartbeatManager", "resetLevel", new Object[0]);
    }
}
