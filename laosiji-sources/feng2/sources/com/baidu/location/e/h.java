package com.baidu.location.e;

import android.content.Context;
import android.content.pm.ProviderInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import com.baidu.location.BDLocation;
import com.baidu.location.f;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class h {
    public static final String a = com.baidu.location.h.a.a;
    static final String b = "http://ofloc.map.baidu.com/offline_loc";
    static final String c = "com.baidu.lbs.offlinelocationprovider";
    private static Context d;
    private static volatile h e;
    private final File f;
    private final k g;
    private final d h;
    private final l i;
    private final g j;

    public enum a {
        NEED_TO_LOG,
        NO_NEED_TO_LOG
    }

    public enum b {
        IS_MIX_MODE,
        IS_NOT_MIX_MODE
    }

    private enum c {
        NETWORK_UNKNOWN,
        NETWORK_WIFI,
        NETWORK_2G,
        NETWORK_3G,
        NETWORK_4G
    }

    private h() {
        File file;
        try {
            file = new File(d.getFilesDir(), "ofld");
            try {
                if (!file.exists()) {
                    file.mkdir();
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            file = null;
        }
        this.f = file;
        this.h = new d(this);
        this.g = new k(this.h.a());
        this.j = new g(this, this.h.a());
        this.i = new l(this, this.h.a(), this.j.n());
    }

    private BDLocation a(String[] strArr) {
        BDLocation bDLocation = new BDLocation();
        ExecutorService newSingleThreadExecutor = Executors.newSingleThreadExecutor();
        bDLocation = (FutureTask) newSingleThreadExecutor.submit(new i(this, strArr));
        try {
            bDLocation = (BDLocation) bDLocation.get(2000, TimeUnit.MILLISECONDS);
            return bDLocation;
        } catch (InterruptedException e) {
            bDLocation.cancel(true);
            return null;
        } catch (ExecutionException e2) {
            bDLocation.cancel(true);
            return null;
        } catch (TimeoutException e3) {
            bDLocation.cancel(true);
            return null;
        } finally {
            newSingleThreadExecutor.shutdown();
        }
    }

    public static h a() {
        if (e == null) {
            synchronized (h.class) {
                if (e == null) {
                    if (d == null) {
                        a(f.getServiceContext());
                    }
                    e = new h();
                }
            }
        }
        e.q();
        return e;
    }

    public static void a(Context context) {
        if (d == null) {
            d = context;
            com.baidu.location.h.b.a().a(d);
        }
    }

    private static final Uri c(String str) {
        return Uri.parse(String.format("content://%s/", new Object[]{str}));
    }

    private void q() {
        this.j.g();
    }

    private boolean r() {
        ProviderInfo resolveContentProvider;
        ProviderInfo providerInfo;
        String packageName = d.getPackageName();
        try {
            resolveContentProvider = d.getPackageManager().resolveContentProvider(c, 0);
        } catch (Exception e) {
            resolveContentProvider = null;
        }
        if (resolveContentProvider == null) {
            String[] o = this.j.o();
            providerInfo = resolveContentProvider;
            for (String resolveContentProvider2 : o) {
                try {
                    providerInfo = d.getPackageManager().resolveContentProvider(resolveContentProvider2, 0);
                } catch (Exception e2) {
                    providerInfo = null;
                }
                if (providerInfo != null) {
                    break;
                }
            }
        } else {
            providerInfo = resolveContentProvider;
        }
        return providerInfo == null ? true : packageName.equals(providerInfo.packageName);
    }

    public long a(String str) {
        return this.j.a(str);
    }

    public BDLocation a(com.baidu.location.f.a aVar, com.baidu.location.f.f fVar, BDLocation bDLocation, b bVar, a aVar2) {
        int a;
        String str;
        if (bVar == b.IS_MIX_MODE) {
            a = this.j.a();
            str = com.baidu.location.h.b.a().e() + "&mixMode=1";
        } else {
            str = com.baidu.location.h.b.a().e();
            a = 0;
        }
        String[] a2 = j.a(aVar, fVar, bDLocation, str, (aVar2 == a.NEED_TO_LOG ? Boolean.valueOf(true) : Boolean.valueOf(false)).booleanValue(), a);
        BDLocation bDLocation2 = null;
        if (a2.length > 0) {
            bDLocation2 = a(a2);
            if (bDLocation2 == null || bDLocation2.getLocType() != 67) {
            }
        }
        return bDLocation2;
    }

    public Context b() {
        return d;
    }

    File c() {
        return this.f;
    }

    public boolean d() {
        return this.j.h();
    }

    public boolean e() {
        return this.j.i();
    }

    public boolean f() {
        return this.j.j();
    }

    public boolean g() {
        return this.j.k();
    }

    public boolean h() {
        return this.j.m();
    }

    public void i() {
        this.g.a();
    }

    k j() {
        return this.g;
    }

    l k() {
        return this.i;
    }

    g l() {
        return this.j;
    }

    public void m() {
        if (r()) {
            this.h.b();
        }
    }

    public void n() {
    }

    public double o() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) d.getSystemService("connectivity")).getActiveNetworkInfo();
        c cVar = c.NETWORK_UNKNOWN;
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == 1) {
                cVar = c.NETWORK_WIFI;
            }
            if (activeNetworkInfo.getType() == 0) {
                int subtype = activeNetworkInfo.getSubtype();
                if (subtype == 1 || subtype == 2 || subtype == 4 || subtype == 7 || subtype == 11) {
                    cVar = c.NETWORK_2G;
                } else if (subtype == 3 || subtype == 5 || subtype == 6 || subtype == 8 || subtype == 9 || subtype == 10 || subtype == 12 || subtype == 14 || subtype == 15) {
                    cVar = c.NETWORK_3G;
                } else if (subtype == 13) {
                    cVar = c.NETWORK_4G;
                }
            }
        }
        return cVar == c.NETWORK_UNKNOWN ? this.j.b() : cVar == c.NETWORK_WIFI ? this.j.c() : cVar == c.NETWORK_2G ? this.j.d() : cVar == c.NETWORK_3G ? this.j.e() : cVar == c.NETWORK_4G ? this.j.f() : 0.0d;
    }
}
