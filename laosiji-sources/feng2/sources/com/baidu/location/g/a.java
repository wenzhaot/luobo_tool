package com.baidu.location.g;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Process;
import android.util.Log;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.location.LLSInterface;
import com.baidu.location.a.c;
import com.baidu.location.a.g;
import com.baidu.location.a.i;
import com.baidu.location.a.j;
import com.baidu.location.a.m;
import com.baidu.location.a.s;
import com.baidu.location.c.e;
import com.baidu.location.e.h;
import com.baidu.location.f;
import com.baidu.location.f.b;
import com.baidu.location.f.d;

public class a extends Service implements LLSInterface {
    static a a = null;
    private static long d = 0;
    Messenger b = null;
    private boolean c = false;

    public class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            if (f.isServing) {
                switch (message.what) {
                    case 11:
                        a.this.a(message);
                        break;
                    case 12:
                        a.this.b(message);
                        break;
                    case 15:
                        a.this.c(message);
                        break;
                    case 22:
                        j.c().b(message);
                        break;
                    case 28:
                        j.c().a(true, true);
                        break;
                    case 41:
                        j.c().i();
                        break;
                    case 110:
                        e.a().c();
                        break;
                    case 111:
                        e.a().d();
                        break;
                    case 112:
                        e.a().b();
                        break;
                    case 302:
                        e.a().e();
                        break;
                    case 401:
                        try {
                            message.getData();
                            break;
                        } catch (Exception e) {
                            break;
                        }
                    case 405:
                        byte[] byteArray = message.getData().getByteArray("errorid");
                        if (byteArray != null && byteArray.length > 0) {
                            String str = new String(byteArray);
                            break;
                        }
                    case 406:
                        g.a().e();
                        break;
                }
            }
            if (message.what == 1) {
                a.this.d();
            }
            if (message.what == 0) {
                a.this.c();
            }
            super.handleMessage(message);
        }
    }

    public static Handler a() {
        return a;
    }

    private void a(Message message) {
        Log.d("baidu_location_service", "baidu location service register ...");
        com.baidu.location.a.a.a().a(message);
        h.a();
        com.baidu.location.b.e.a().d();
        m.b().c();
    }

    public static long b() {
        return d;
    }

    private void b(Message message) {
        com.baidu.location.a.a.a().b(message);
    }

    private void c() {
        i.a().a(f.getServiceContext());
        g.a().b();
        d.a().b();
        b.a().b();
        com.baidu.location.h.b.a();
        j.c().d();
        com.baidu.location.e.a.a().b();
        com.baidu.location.b.d.a().b();
        com.baidu.location.b.e.a().b();
        com.baidu.location.b.g.a().b();
        com.baidu.location.b.a.a().b();
        com.baidu.location.b.h.a().b();
        com.baidu.location.f.g.a().c();
    }

    private void c(Message message) {
        com.baidu.location.a.a.a().c(message);
    }

    private void d() {
        com.baidu.location.f.g.a().e();
        h.a().n();
        d.a().e();
        com.baidu.location.b.j.a().c();
        com.baidu.location.b.e.a().c();
        com.baidu.location.b.d.a().c();
        com.baidu.location.b.b.a().c();
        com.baidu.location.b.a.a().c();
        com.baidu.location.b.f.a().b();
        b.a().c();
        j.c().e();
        e.a().d();
        g.a().c();
        s.e();
        com.baidu.location.a.a.a().b();
        c.a().b();
        Log.d("baidu_location_service", "baidu location service has stoped ...");
        if (!this.c) {
            Process.killProcess(Process.myPid());
        }
    }

    public double getVersion() {
        return 7.210000038146973d;
    }

    public IBinder onBind(Intent intent) {
        Bundle extras = intent.getExtras();
        boolean z = false;
        if (extras != null) {
            com.baidu.location.h.b.g = extras.getString("key");
            com.baidu.location.h.b.f = extras.getString(DispatchConstants.SIGN);
            this.c = extras.getBoolean("kill_process");
            z = extras.getBoolean("cache_exception");
        }
        if (!z) {
            Thread.setDefaultUncaughtExceptionHandler(com.baidu.location.b.g.a());
        }
        return this.b.getBinder();
    }

    public void onCreate(Context context) {
        d = System.currentTimeMillis();
        a = new a(Looper.getMainLooper());
        this.b = new Messenger(a);
        a.sendEmptyMessage(0);
        Log.d("baidu_location_service", "baidu location service start1 ..." + Process.myPid());
    }

    public void onDestroy() {
        try {
            a.sendEmptyMessage(1);
        } catch (Exception e) {
            Log.d("baidu_location_service", "baidu location service stop exception...");
            d();
            Process.killProcess(Process.myPid());
        }
        Log.d("baidu_location_service", "baidu location service stop ...");
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        return 1;
    }

    public void onTaskRemoved(Intent intent) {
        Log.d("baidu_location_service", "baidu location service remove task...");
    }

    public boolean onUnBind(Intent intent) {
        return false;
    }
}
