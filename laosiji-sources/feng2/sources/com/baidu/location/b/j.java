package com.baidu.location.b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.os.Handler;
import com.baidu.location.a.g;
import com.baidu.location.e.h;
import com.baidu.location.f;
import com.baidu.location.h.k;

public class j {
    private static j b = null;
    final Handler a = new Handler();
    private a c = null;
    private boolean d = false;
    private boolean e = false;
    private boolean f = false;
    private boolean g = true;
    private boolean h = false;
    private b i = new b();

    private class a extends BroadcastReceiver {
        private a() {
        }

        public void onReceive(Context context, Intent intent) {
            if (context != null && j.this.a != null) {
                j.this.f();
            }
        }
    }

    private class b implements Runnable {
        private b() {
        }

        public void run() {
            int d = g.a().d();
            if (j.this.d && d.a().e() && h.a().d() && d != 1) {
                j.this.g();
            }
            if (j.this.d && d.a().e()) {
                h.a().c();
            }
            if (j.this.d && j.this.g) {
                j.this.a.postDelayed(this, (long) k.P);
                j.this.f = true;
                return;
            }
            j.this.f = false;
        }
    }

    private j() {
    }

    public static synchronized j a() {
        j jVar;
        synchronized (j.class) {
            if (b == null) {
                b = new j();
            }
            jVar = b;
        }
        return jVar;
    }

    private void f() {
        State state;
        State state2 = State.UNKNOWN;
        try {
            state = ((ConnectivityManager) f.getServiceContext().getSystemService("connectivity")).getNetworkInfo(1).getState();
        } catch (Exception e) {
            state = state2;
        }
        if (State.CONNECTED != state) {
            this.d = false;
        } else if (!this.d) {
            this.d = true;
            this.a.postDelayed(this.i, (long) k.P);
            this.f = true;
        }
    }

    private void g() {
        h.a().m();
        h.a().i();
    }

    public synchronized void b() {
        if (f.isServing) {
            if (!this.h) {
                try {
                    this.c = new a();
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    f.getServiceContext().registerReceiver(this.c, intentFilter);
                    this.e = true;
                    f();
                } catch (Exception e) {
                }
                this.g = true;
                this.h = true;
            }
        }
    }

    public synchronized void c() {
        if (this.h) {
            try {
                f.getServiceContext().unregisterReceiver(this.c);
            } catch (Exception e) {
            }
            this.g = false;
            this.h = false;
            this.f = false;
            this.c = null;
        }
    }

    public void d() {
        if (this.h) {
            this.g = true;
            if (!this.f && this.g) {
                this.a.postDelayed(this.i, (long) k.P);
                this.f = true;
            }
        }
    }

    public void e() {
        this.g = false;
    }
}
