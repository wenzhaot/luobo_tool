package com.umeng.commonsdk.framework;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.FileObserver;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.internal.b;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.noise.ImLatent;
import com.umeng.message.MsgConstant;
import java.io.File;

/* compiled from: UMNetWorkSender */
class c {
    private static HandlerThread a = null;
    private static Handler b = null;
    private static Handler c = null;
    private static final int d = 273;
    private static final int e = 512;
    private static final int f = 769;
    private static a g;
    private static ConnectivityManager h;
    private static NetworkInfo i;
    private static IntentFilter j = null;
    private static StatTracer k = null;
    private static ImLatent l = null;
    private static boolean m = false;
    private static BroadcastReceiver n = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                Context appContext = UMModuleRegister.getAppContext();
                c.h = (ConnectivityManager) appContext.getSystemService("connectivity");
                try {
                    c.i = c.h.getActiveNetworkInfo();
                    if (c.i == null || !c.i.isAvailable()) {
                        e.c("--->>> network disconnected.");
                        c.m = false;
                        return;
                    }
                    c.m = true;
                    c.c(273);
                    if (c.i.getType() == 1 && context != null) {
                        try {
                            if (!UMWorkDispatch.eventHasExist(com.umeng.commonsdk.internal.a.j)) {
                                UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.j, b.a(context).a(), null);
                            }
                        } catch (Throwable th) {
                        }
                    }
                } catch (Throwable th2) {
                    com.umeng.commonsdk.proguard.b.a(appContext, th2);
                }
            }
        }
    };

    /* compiled from: UMNetWorkSender */
    static class a extends FileObserver {
        public a(String str) {
            super(str);
        }

        public void onEvent(int i, String str) {
            switch (i & 8) {
                case 8:
                    e.b("--->>> envelope file created >>> " + str);
                    UMRTLog.i(UMRTLog.RTLOG_TAG, "File: " + str + " created.");
                    c.c(273);
                    return;
                default:
                    return;
            }
        }
    }

    public c(Context context, Handler handler) {
        c = handler;
        try {
            if (a == null) {
                a = new HandlerThread("NetWorkSender");
                a.start();
                if (g == null) {
                    g = new a(b.h(context));
                    g.startWatching();
                    e.b("--->>> FileMonitor has already started!");
                }
                Context appContext = UMModuleRegister.getAppContext();
                if (DeviceConfig.checkPermission(appContext, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE) && j == null) {
                    j = new IntentFilter();
                    j.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                    if (n != null) {
                        appContext.registerReceiver(n, j);
                    }
                }
                if (k == null) {
                    k = StatTracer.getInstance(context);
                    l = ImLatent.getService(context, k);
                }
                if (b == null) {
                    b = new Handler(a.getLooper()) {
                        public void handleMessage(Message message) {
                            switch (message.what) {
                                case 273:
                                    e.b("--->>> handleMessage: recv MSG_PROCESS_NEXT msg.");
                                    c.j();
                                    return;
                                case 512:
                                    c.i();
                                    return;
                                default:
                                    return;
                            }
                        }
                    };
                }
            }
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(context, th);
        }
    }

    private static void h() {
        if (a != null) {
            a = null;
        }
        if (b != null) {
            b = null;
        }
        if (c != null) {
            c = null;
        }
        if (l != null) {
            l = null;
        }
        if (k != null) {
            k = null;
        }
    }

    private static void i() {
        if (g != null) {
            g.stopWatching();
            g = null;
        }
        if (j != null) {
            if (n != null) {
                UMModuleRegister.getAppContext().unregisterReceiver(n);
                n = null;
            }
            j = null;
        }
        e.b("--->>> handleQuit: Quit sender thread.");
        if (a != null) {
            a.quit();
            h();
        }
    }

    public static void a() {
        c(512);
    }

    private static void b(int i) {
        if (m && b != null && !b.hasMessages(i)) {
            Message obtainMessage = b.obtainMessage();
            obtainMessage.what = i;
            b.sendMessage(obtainMessage);
        }
    }

    private static void c(int i) {
        if (m && b != null) {
            Message obtainMessage = b.obtainMessage();
            obtainMessage.what = i;
            b.sendMessage(obtainMessage);
        }
    }

    private static void a(int i, long j) {
        if (m && b != null) {
            Message obtainMessage = b.obtainMessage();
            obtainMessage.what = i;
            b.sendMessageDelayed(obtainMessage, j);
        }
    }

    public static void b() {
        b(273);
    }

    private static void a(int i, int i2) {
        if (m && c != null) {
            c.removeMessages(i);
            Message obtainMessage = c.obtainMessage();
            obtainMessage.what = i;
            c.sendMessageDelayed(obtainMessage, (long) i2);
        }
    }

    public static void c() {
        a((int) f, 3000);
    }

    private static void j() {
        e.b("--->>> handleProcessNext: Enter...");
        if (m) {
            Context appContext = UMModuleRegister.getAppContext();
            try {
                if (b.c(appContext) > 0) {
                    e.b("--->>> The envelope file exists.");
                    if (b.c(appContext) > 100) {
                        e.b("--->>> Number of envelope files is greater than 100, remove old files first.");
                        b.d(appContext);
                    }
                    File e = b.e(appContext);
                    if (e != null) {
                        e.b("--->>> Ready to send envelope file [" + e.getPath() + "].");
                        com.umeng.commonsdk.statistics.c cVar = new com.umeng.commonsdk.statistics.c(appContext);
                        if (l != null && l.isLatentActivite()) {
                            l.latentDeactivite();
                            long delayTime = l.getDelayTime();
                            if (delayTime > 0) {
                                e.c("start lacency policy, wait [" + delayTime + "] milliseconds .");
                                Thread.sleep(delayTime * 1000);
                            }
                        }
                        if (cVar.a(e)) {
                            e.b("--->>> Send envelope file success, delete it.");
                            if (!b.a(e)) {
                                e.b("--->>> Failed to delete already processed file. We try again after delete failed.");
                                b.a(e);
                            }
                            c(273);
                            return;
                        }
                        e.b("--->>> Send envelope file failed, abandon and wait next trigger!");
                        return;
                    }
                }
                e.b("--->>> The envelope file not exists, start auto process for module cache data.");
                c();
            } catch (Throwable th) {
                com.umeng.commonsdk.proguard.b.a(appContext, th);
            }
        }
    }
}
