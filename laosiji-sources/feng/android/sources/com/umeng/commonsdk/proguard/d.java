package com.umeng.commonsdk.proguard;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build.VERSION;
import com.stub.StubApp;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.common.e;
import com.umeng.commonsdk.utils.UMUtils;

/* compiled from: UMSysLocation */
public class d {
    private static final String a = "UMSysLocation";
    private static final int c = 10000;
    private LocationManager b;
    private Context d;
    private f e;

    private d() {
    }

    public d(Context context) {
        if (context == null) {
            MLog.e("Context参数不能为null");
            return;
        }
        this.d = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.b = (LocationManager) StubApp.getOrigApplicationContext(context.getApplicationContext()).getSystemService("location");
    }

    public synchronized void a(f fVar) {
        Location location = null;
        boolean z = false;
        synchronized (this) {
            e.a(a, "getSystemLocation");
            if (!(fVar == null || this.d == null)) {
                this.e = fVar;
                boolean checkPermission = UMUtils.checkPermission(this.d, "android.permission.ACCESS_COARSE_LOCATION");
                boolean checkPermission2 = UMUtils.checkPermission(this.d, "android.permission.ACCESS_FINE_LOCATION");
                if (checkPermission || checkPermission2) {
                    try {
                        if (this.b != null) {
                            boolean isProviderEnabled;
                            if (VERSION.SDK_INT >= 21) {
                                isProviderEnabled = this.b.isProviderEnabled("gps");
                                z = this.b.isProviderEnabled("network");
                            } else {
                                if (checkPermission2) {
                                    isProviderEnabled = this.b.isProviderEnabled("gps");
                                } else {
                                    isProviderEnabled = false;
                                }
                                if (checkPermission) {
                                    z = this.b.isProviderEnabled("network");
                                }
                            }
                            if (isProviderEnabled || z) {
                                e.a(a, "getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)");
                                if (checkPermission2) {
                                    location = StubApp.mark(this.b, "passive");
                                } else if (checkPermission) {
                                    location = StubApp.mark(this.b, "network");
                                }
                            }
                            this.e.a(location);
                        }
                    } catch (Throwable th) {
                        b.a(this.d, th);
                    }
                } else if (this.e != null) {
                    this.e.a(null);
                }
            }
        }
        b.a(this.d, th);
    }

    public synchronized void a() {
        e.a(a, "destroy");
        try {
            if (this.b != null) {
                this.b = null;
            }
        } catch (Throwable th) {
            b.a(this.d, th);
        }
        return;
    }
}
