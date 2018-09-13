package com.umeng.commonsdk.stateless;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;
import android.util.Base64;
import com.stub.StubApp;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.message.MsgConstant;
import java.io.File;

/* compiled from: UMSLNetWorkSender */
public class d {
    public static final int a = 273;
    private static Context b = null;
    private static HandlerThread c = null;
    private static Handler d = null;
    private static Object e = new Object();
    private static final int f = 512;
    private static IntentFilter g;
    private static boolean h = false;
    private static BroadcastReceiver i = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            if (context != null && intent != null) {
                try {
                    if (intent.getAction() != null && intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                        d.b = StubApp.getOrigApplicationContext(context.getApplicationContext());
                        if (d.b != null) {
                            ConnectivityManager connectivityManager = (ConnectivityManager) d.b.getSystemService("connectivity");
                            if (connectivityManager != null) {
                                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                                if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable()) {
                                    e.a("walle", "[stateless] net reveiver disconnected --->>>");
                                    d.h = false;
                                    return;
                                }
                                d.h = true;
                                e.a("walle", "[stateless] net reveiver ok --->>>");
                                d.b(d.a);
                            }
                        }
                    }
                } catch (Throwable th) {
                    b.a(context, th);
                }
            }
        }
    };

    public d(Context context) {
        synchronized (e) {
            if (context != null) {
                try {
                    b = StubApp.getOrigApplicationContext(context.getApplicationContext());
                    if (b != null && c == null) {
                        c = new HandlerThread("SL-NetWorkSender");
                        c.start();
                        if (d == null) {
                            d = new Handler(c.getLooper()) {
                                public void handleMessage(Message message) {
                                    switch (message.what) {
                                        case d.a /*273*/:
                                            d.e();
                                            return;
                                        case 512:
                                            d.f();
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            };
                        }
                        if (DeviceConfig.checkPermission(b, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                            e.a("walle", "[stateless] begin register receiver");
                            if (g == null) {
                                g = new IntentFilter();
                                g.addAction("android.net.conn.CONNECTIVITY_CHANGE");
                                if (i != null) {
                                    e.a("walle", "[stateless] register receiver ok");
                                    b.registerReceiver(i, g);
                                }
                            }
                        }
                    }
                } catch (Throwable th) {
                    b.a(context, th);
                }
            }
        }
    }

    public static void a(int i) {
        if (h && d != null) {
            Message obtainMessage = d.obtainMessage();
            obtainMessage.what = i;
            d.sendMessage(obtainMessage);
        }
    }

    public static void b(int i) {
        try {
            if (h && d != null && !d.hasMessages(i)) {
                e.a("walle", "[stateless] sendMsgOnce !!!!");
                Message obtainMessage = d.obtainMessage();
                obtainMessage.what = i;
                d.sendMessage(obtainMessage);
            }
        } catch (Throwable th) {
            b.a(b, th);
        }
    }

    private static void e() {
        if (h && b != null) {
            try {
                File a = f.a(b);
                if (a != null && a.getParentFile() != null && !TextUtils.isEmpty(a.getParentFile().getName())) {
                    e eVar = new e(b);
                    if (eVar != null) {
                        e.a("walle", "[stateless] handleProcessNext, pathUrl is " + new String(Base64.decode(a.getParentFile().getName(), 0)));
                        byte[] bArr = null;
                        try {
                            bArr = f.a(a.getAbsolutePath());
                        } catch (Exception e) {
                        }
                        if (eVar.a(bArr, r3)) {
                            e.a("walle", "[stateless] Send envelope file success, delete it.");
                            File file = new File(a.getAbsolutePath());
                            if (!file.delete()) {
                                e.a("walle", "[stateless] Failed to delete already processed file. We try again after delete failed.");
                                file.delete();
                            }
                        } else {
                            e.a("walle", "[stateless] Send envelope file failed, abandon and wait next trigger!");
                            return;
                        }
                    }
                    b(a);
                }
            } catch (Throwable th) {
                b.a(b, th);
            }
        }
    }

    public static void a() {
        b(512);
    }

    private static void f() {
        if (g != null) {
            if (i != null) {
                if (b != null) {
                    b.unregisterReceiver(i);
                }
                i = null;
            }
            g = null;
        }
        if (c != null) {
            c.quit();
            if (c != null) {
                c = null;
            }
            if (d != null) {
                d = null;
            }
        }
    }
}
