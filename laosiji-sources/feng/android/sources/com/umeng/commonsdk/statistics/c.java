package com.umeng.commonsdk.statistics;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import com.umeng.commonsdk.debug.UMRTLog;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.framework.b;
import com.umeng.commonsdk.proguard.ad;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.proguard.l;
import com.umeng.commonsdk.proguard.o;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.statistics.common.ReportPolicy.LatentPolicy;
import com.umeng.commonsdk.statistics.common.ReportPolicy.ReportStrategy;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler;
import com.umeng.commonsdk.statistics.idtracking.ImprintHandler.a;
import com.umeng.commonsdk.statistics.idtracking.e;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.statistics.internal.StatTracer;
import com.umeng.commonsdk.statistics.internal.d;
import com.umeng.commonsdk.statistics.noise.ABTest;
import com.umeng.commonsdk.statistics.noise.Defcon;
import com.umeng.commonsdk.statistics.noise.ImLatent;
import com.umeng.commonsdk.statistics.proto.Response;
import java.io.File;

/* compiled from: NetWorkManager */
public class c {
    private static final int b = 1;
    private static final int c = 2;
    private static final int d = 3;
    private static final String p = "thtstart";
    private static final String q = "gkvc";
    private static final String r = "ekvc";
    String a = null;
    private final int e = 1;
    private com.umeng.commonsdk.statistics.internal.c f;
    private ImprintHandler g;
    private e h;
    private a i = null;
    private ABTest j = null;
    private ImLatent k = null;
    private Defcon l = null;
    private long m = 0;
    private int n = 0;
    private int o = 0;
    private Context s;
    private ReportStrategy t = null;

    public c(Context context) {
        this.s = context;
        this.i = ImprintHandler.getImprintService(this.s).b();
        this.j = ABTest.getService(this.s);
        this.l = Defcon.getService(this.s);
        this.k = ImLatent.getService(this.s, StatTracer.getInstance(this.s));
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(this.s);
        this.m = sharedPreferences.getLong(p, 0);
        this.n = sharedPreferences.getInt(q, 0);
        this.o = sharedPreferences.getInt(r, 0);
        this.a = UMEnvelopeBuild.imprintProperty(this.s, "track_list", null);
        this.g = ImprintHandler.getImprintService(this.s);
        this.g.a(new d() {
            public void onImprintChanged(a aVar) {
                c.this.j.onImprintChanged(aVar);
                c.this.l.onImprintChanged(aVar);
                c.this.k.onImprintChanged(aVar);
                c.this.a = UMEnvelopeBuild.imprintProperty(c.this.s, "track_list", null);
                try {
                    if (!TextUtils.isEmpty(com.umeng.commonsdk.framework.a.a(c.this.s, g.e, null))) {
                        try {
                            Class cls = Class.forName("com.umeng.commonsdk.internal.utils.SDStorageAgent");
                            if (cls != null) {
                                cls.getMethod("updateUMTT", new Class[]{Context.class, String.class}).invoke(cls, new Object[]{c.this.s, r0});
                            }
                        } catch (ClassNotFoundException e) {
                        } catch (Throwable th) {
                        }
                    }
                } catch (Throwable th2) {
                }
            }
        });
        this.h = e.a(this.s);
        this.f = new com.umeng.commonsdk.statistics.internal.c(this.s);
        this.f.a(StatTracer.getInstance(this.s));
    }

    public boolean a(File file) {
        if (file == null) {
            return false;
        }
        try {
            byte[] a = b.a(file.getPath());
            if (a == null) {
                return false;
            }
            int i;
            com.umeng.commonsdk.statistics.internal.a.a(this.s).b(file.getName());
            a = this.f.a(a, com.umeng.commonsdk.statistics.internal.a.a(this.s).a(file.getName()));
            if (a == null) {
                i = 1;
            } else {
                i = a(a);
            }
            switch (i) {
                case 2:
                    this.h.d();
                    StatTracer.getInstance(this.s).saveSate();
                    break;
                case 3:
                    StatTracer.getInstance(this.s).saveSate();
                    break;
            }
            if (i == 2) {
                return true;
            }
            return false;
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(this.s, th);
            return false;
        }
    }

    private int a(byte[] bArr) {
        l response = new Response();
        try {
            new o(new ad.a()).a(response, bArr);
            if (response.resp_code == 1) {
                this.g.b(response.getImprint());
                this.g.c();
            }
            MLog.i("send log:" + response.getMsg());
            UMRTLog.i(UMRTLog.RTLOG_TAG, "send log: " + response.getMsg());
        } catch (Throwable th) {
            com.umeng.commonsdk.proguard.b.a(this.s, th);
        }
        if (response.resp_code == 1) {
            return 2;
        }
        return 3;
    }

    public boolean a() {
        if (!this.l.isOpen()) {
            boolean z = (this.t instanceof LatentPolicy) && this.t.isValid();
            if (!z && this.k.shouldStartLatency()) {
                this.t = new LatentPolicy((int) this.k.getDelayTime());
                return true;
            }
        }
        return false;
    }

    public int b() {
        int delayTime = (int) this.k.getDelayTime();
        return (int) (System.currentTimeMillis() - StatTracer.getInstance(this.s).getLastReqTime());
    }
}
