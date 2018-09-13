package com.umeng.commonsdk.internal.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.stub.StubApp;
import com.umeng.commonsdk.framework.UMWorkDispatch;
import com.umeng.commonsdk.internal.b;
import com.umeng.commonsdk.statistics.common.e;
import org.json.JSONObject;

/* compiled from: BatteryUtils */
public class c {
    private static final String a = "BatteryUtils";
    private static boolean b = false;
    private static Context c = null;
    private BroadcastReceiver d;

    /* compiled from: BatteryUtils */
    private static class a {
        private static final c a = new c();

        private a() {
        }
    }

    /* synthetic */ c(AnonymousClass1 anonymousClass1) {
        this();
    }

    private c() {
        this.d = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int i = -1;
                int i2 = 1;
                if (intent.getAction().equals("android.intent.action.BATTERY_CHANGED")) {
                    JSONObject jSONObject = new JSONObject();
                    try {
                        jSONObject.put("le", intent.getIntExtra("level", 0));
                    } catch (Exception e) {
                    }
                    try {
                        try {
                            jSONObject.put("vol", intent.getIntExtra("voltage", 0));
                        } catch (Exception e2) {
                        }
                        try {
                            jSONObject.put("temp", intent.getIntExtra("temperature", 0));
                            jSONObject.put("ts", System.currentTimeMillis());
                        } catch (Exception e3) {
                        }
                        switch (intent.getIntExtra("status", 0)) {
                            case 2:
                                i = 1;
                                break;
                            case 4:
                                i = 0;
                                break;
                            case 5:
                                i = 2;
                                break;
                        }
                        try {
                            jSONObject.put("st", i);
                        } catch (Exception e4) {
                        }
                        switch (intent.getIntExtra("plugged", 0)) {
                            case 1:
                                break;
                            case 2:
                                i2 = 2;
                                break;
                            default:
                                i2 = 0;
                                break;
                        }
                        try {
                            jSONObject.put("ct", i2);
                            jSONObject.put("ts", System.currentTimeMillis());
                        } catch (Exception e5) {
                        }
                        e.a(c.a, jSONObject.toString());
                        UMWorkDispatch.sendEvent(context, com.umeng.commonsdk.internal.a.g, b.a(c.c).a(), jSONObject.toString());
                        c.this.c();
                    } catch (Throwable th) {
                        com.umeng.commonsdk.proguard.b.a(c.c, th);
                    }
                }
            }
        };
    }

    public static c a(Context context) {
        if (c == null && context != null) {
            c = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
        return a.a;
    }

    public synchronized boolean a() {
        return b;
    }

    public synchronized void b() {
        try {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BATTERY_CHANGED");
            c.registerReceiver(this.d, intentFilter);
            b = true;
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(c, th);
        }
        return;
    }

    public synchronized void c() {
        try {
            c.unregisterReceiver(this.d);
            b = false;
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(c, th);
        }
        return;
    }
}
