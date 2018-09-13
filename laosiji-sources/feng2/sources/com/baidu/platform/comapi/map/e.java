package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import com.baidu.mapapi.UIMsg.k_event;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapBaseIndoorMapInfo;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.model.LatLngBounds.Builder;
import com.baidu.mapapi.model.ParcelItem;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comjni.map.basemap.BaseMapCallback;
import com.baidu.platform.comjni.map.basemap.JNIBaseMap;
import com.baidu.platform.comjni.map.basemap.b;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint({"NewApi"})
public class e implements b {
    private static int N;
    private static int O;
    private static List<JNIBaseMap> aq;
    static long k = 0;
    private static final String o = j.class.getSimpleName();
    private Context A;
    private List<d> B;
    private aa C;
    private g D;
    private o E;
    private ah F;
    private ak G;
    private s H;
    private n I;
    private p J;
    private a K;
    private q L;
    private ai M;
    private int P;
    private int Q;
    private int R;
    private a S = new a();
    private VelocityTracker T;
    private long U;
    private long V;
    private long W;
    private long X;
    private int Y;
    private float Z;
    public float a = 22.0f;
    private float aa;
    private boolean ab;
    private long ac;
    private long ad;
    private boolean ae = false;
    private boolean af = false;
    private float ag;
    private float ah;
    private float ai;
    private float aj;
    private long ak = 0;
    private long al = 0;
    private f am;
    private String an;
    private b ao;
    private c ap;
    private boolean ar = false;
    private Queue<a> as = new LinkedList();
    public float b = 3.0f;
    public float c = 22.0f;
    boolean d = true;
    boolean e = true;
    List<l> f;
    com.baidu.platform.comjni.map.basemap.a g;
    long h;
    boolean i;
    public int j;
    boolean l;
    boolean m;
    boolean n;
    private boolean p;
    private boolean q;
    private boolean r = true;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v = true;
    private boolean w = true;
    private boolean x = false;
    private am y;
    private al z;

    public static class a {
        public long a;
        public int b;
        public int c;
        public int d;
        public Bundle e;

        public a(long j, int i, int i2, int i3) {
            this.a = j;
            this.b = i;
            this.c = i2;
            this.d = i3;
        }

        public a(Bundle bundle) {
            this.e = bundle;
        }
    }

    public e(Context context, String str) {
        this.A = context;
        this.f = new ArrayList();
        this.an = str;
    }

    private void P() {
        if (this.t || this.q || this.p || this.u) {
            if (this.a > 20.0f) {
                this.a = 20.0f;
            }
            if (E().a > 20.0f) {
                ae E = E();
                E.a = 20.0f;
                a(E);
                return;
            }
            return;
        }
        this.a = this.c;
    }

    private boolean Q() {
        if (this.g == null || !this.i) {
            return true;
        }
        this.af = false;
        if (!this.d) {
            return false;
        }
        long j = this.al - this.ak;
        float abs = (Math.abs(this.ai - this.ag) * 1000.0f) / ((float) j);
        float abs2 = (Math.abs(this.aj - this.ah) * 1000.0f) / ((float) j);
        abs2 = (float) Math.sqrt((double) ((abs2 * abs2) + (abs * abs)));
        if (abs2 <= 500.0f) {
            return false;
        }
        A();
        a(34, (int) (abs2 * 0.6f), (((int) this.aj) << 16) | ((int) this.ai));
        M();
        return true;
    }

    private Activity a(Context context) {
        return context == null ? null : context instanceof Activity ? (Activity) context : context instanceof ContextWrapper ? a(((ContextWrapper) context).getBaseContext()) : null;
    }

    private void a(String str, String str2, long j) {
        try {
            Class cls = Class.forName(str);
            Object newInstance = cls.newInstance();
            cls.getMethod(str2, new Class[]{Long.TYPE}).invoke(newInstance, new Object[]{Long.valueOf(j)});
        } catch (Exception e) {
        }
    }

    private boolean e(float f, float f2) {
        if (this.g == null || !this.i) {
            return true;
        }
        this.ae = false;
        GeoPoint b = b((int) f, (int) f2);
        if (b == null) {
            return false;
        }
        for (l b2 : this.f) {
            b2.b(b);
        }
        if (!this.e) {
            return false;
        }
        ae E = E();
        E.a += 1.0f;
        E.d = b.getLongitudeE6();
        E.e = b.getLatitudeE6();
        a(E, (int) GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION);
        k = System.currentTimeMillis();
        return true;
    }

    private boolean e(Bundle bundle) {
        return this.g == null ? false : this.g.e(bundle);
    }

    private boolean f(Bundle bundle) {
        boolean z = false;
        if (!(bundle == null || this.g == null)) {
            z = this.g.d(bundle);
            if (z) {
                d(z);
                this.g.b(this.y.a);
            }
        }
        return z;
    }

    private void g(Bundle bundle) {
        if (bundle.get("param") != null) {
            Bundle bundle2 = (Bundle) bundle.get("param");
            int i = bundle2.getInt("type");
            if (i == h.ground.ordinal()) {
                bundle2.putLong("layer_addr", this.E.a);
                return;
            } else if (i >= h.arc.ordinal()) {
                bundle2.putLong("layer_addr", this.I.a);
                return;
            } else if (i == h.popup.ordinal()) {
                bundle2.putLong("layer_addr", this.H.a);
                return;
            } else {
                bundle2.putLong("layer_addr", this.G.a);
                return;
            }
        }
        int i2 = bundle.getInt("type");
        if (i2 == h.ground.ordinal()) {
            bundle.putLong("layer_addr", this.E.a);
        } else if (i2 >= h.arc.ordinal()) {
            bundle.putLong("layer_addr", this.I.a);
        } else if (i2 == h.popup.ordinal()) {
            bundle.putLong("layer_addr", this.H.a);
        } else {
            bundle.putLong("layer_addr", this.G.a);
        }
    }

    public static void k(boolean z) {
        aq = com.baidu.platform.comjni.map.basemap.a.b();
        if (aq == null || aq.size() == 0) {
            com.baidu.platform.comjni.map.basemap.a.b(0, z);
            return;
        }
        com.baidu.platform.comjni.map.basemap.a.b(((JNIBaseMap) aq.get(0)).a, z);
        for (JNIBaseMap jNIBaseMap : aq) {
            jNIBaseMap.ClearLayer(jNIBaseMap.a, -1);
        }
    }

    void A() {
        if (!this.l && !this.m) {
            this.m = true;
            for (l a : this.f) {
                a.a(E());
            }
        }
    }

    void B() {
        this.m = false;
        this.l = false;
        for (l c : this.f) {
            c.c(E());
        }
    }

    public boolean C() {
        return this.g != null ? this.g.a(this.F.a) : false;
    }

    public boolean D() {
        return this.g != null ? this.g.a(this.ap.a) : false;
    }

    public ae E() {
        if (this.g == null) {
            return null;
        }
        Bundle h = this.g.h();
        ae aeVar = new ae();
        aeVar.a(h);
        return aeVar;
    }

    public LatLngBounds F() {
        if (this.g == null) {
            return null;
        }
        Bundle i = this.g.i();
        Builder builder = new Builder();
        int i2 = i.getInt("maxCoorx");
        int i3 = i.getInt("minCoorx");
        builder.include(CoordUtil.mc2ll(new GeoPoint((double) i.getInt("minCoory"), (double) i2))).include(CoordUtil.mc2ll(new GeoPoint((double) i.getInt("maxCoory"), (double) i3)));
        return builder.build();
    }

    public int G() {
        return this.P;
    }

    public int H() {
        return this.Q;
    }

    public ae I() {
        if (this.g == null) {
            return null;
        }
        Bundle j = this.g.j();
        ae aeVar = new ae();
        aeVar.a(j);
        return aeVar;
    }

    public double J() {
        return E().m;
    }

    void K() {
        if (!this.l) {
            this.l = true;
            this.m = false;
            for (l a : this.f) {
                a.a(E());
            }
        }
    }

    void L() {
        this.l = false;
        if (!this.m) {
            for (l c : this.f) {
                c.c(E());
            }
        }
    }

    void M() {
        this.R = 0;
        this.S.e = false;
        this.S.h = 0.0d;
    }

    public Queue<a> N() {
        return this.as;
    }

    public void O() {
        if (!this.as.isEmpty()) {
            a aVar = (a) this.as.poll();
            if (aVar.e == null) {
                com.baidu.platform.comjni.map.basemap.a.a(aVar.a, aVar.b, aVar.c, aVar.d);
            } else if (this.g != null) {
                A();
                this.g.a(aVar.e);
            }
        }
    }

    public float a(int i, int i2, int i3, int i4, int i5, int i6) {
        if (!this.i) {
            return 12.0f;
        }
        if (this.g == null) {
            return 0.0f;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("left", i);
        bundle.putInt("right", i3);
        bundle.putInt("bottom", i4);
        bundle.putInt("top", i2);
        bundle.putInt("hasHW", 1);
        bundle.putInt("width", i5);
        bundle.putInt("height", i6);
        return this.g.c(bundle);
    }

    int a(int i, int i2, int i3) {
        if (!this.ar) {
            return com.baidu.platform.comjni.map.basemap.a.a(this.h, i, i2, i3);
        }
        this.as.add(new a(this.h, i, i2, i3));
        return 0;
    }

    public int a(Bundle bundle, long j, int i, Bundle bundle2) {
        if (j == this.D.a) {
            bundle.putString("jsondata", this.D.a());
            bundle.putBundle("param", this.D.b());
            return this.D.g;
        } else if (j == this.C.a) {
            bundle.putString("jsondata", this.C.a());
            bundle.putBundle("param", this.C.b());
            return this.C.g;
        } else if (j == this.J.a) {
            bundle.putBundle("param", this.L.a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom")));
            return this.J.g;
        } else if (j != this.y.a) {
            return 0;
        } else {
            bundle.putBundle("param", this.z.a(bundle2.getInt("x"), bundle2.getInt("y"), bundle2.getInt("zoom"), this.A));
            return this.y.g;
        }
    }

    public Point a(GeoPoint geoPoint) {
        return this.M.a(geoPoint);
    }

    void a() {
        this.B = new ArrayList();
        this.am = new f();
        a(this.am);
        this.ao = new b();
        a(this.ao);
        this.E = new o();
        a(this.E);
        this.J = new p();
        a(this.J);
        this.K = new a();
        a(this.K);
        a(new r());
        this.F = new ah();
        a(this.F);
        this.ap = new c();
        a(this.ap);
        if (this.g != null) {
            this.g.e(false);
        }
        this.I = new n();
        a(this.I);
        this.G = new ak();
        a(this.G);
        this.D = new g();
        a(this.D);
        this.C = new aa();
        a(this.C);
        this.H = new s();
        a(this.H);
    }

    public void a(float f, float f2) {
        this.a = f;
        this.c = f;
        this.b = f2;
    }

    void a(int i) {
        this.g = new com.baidu.platform.comjni.map.basemap.a();
        this.g.a(i);
        this.h = this.g.a();
        a("com.baidu.platform.comapi.wnplatform.walkmap.WNaviBaiduMap", "setId", this.h);
        if (SysOSUtil.getDensityDpi() < 180) {
            this.j = 18;
        } else if (SysOSUtil.getDensityDpi() < 240) {
            this.j = 25;
        } else if (SysOSUtil.getDensityDpi() < 320) {
            this.j = 37;
        } else {
            this.j = 50;
        }
        String moduleFileName = SysOSUtil.getModuleFileName();
        String appSDCardPath = EnvironmentUtilities.getAppSDCardPath();
        String appCachePath = EnvironmentUtilities.getAppCachePath();
        String appSecondCachePath = EnvironmentUtilities.getAppSecondCachePath();
        int mapTmpStgMax = EnvironmentUtilities.getMapTmpStgMax();
        int domTmpStgMax = EnvironmentUtilities.getDomTmpStgMax();
        int itsTmpStgMax = EnvironmentUtilities.getItsTmpStgMax();
        String str = SysOSUtil.getDensityDpi() >= 180 ? "/h/" : "/l/";
        String str2 = moduleFileName + "/cfg";
        String str3 = appSDCardPath + "/vmp";
        moduleFileName = str2 + "/a/";
        String str4 = str2 + "/a/";
        String str5 = str2 + "/idrres/";
        appSDCardPath = str3 + str;
        str2 = str3 + str;
        appCachePath = appCachePath + "/tmp/";
        appSecondCachePath = appSecondCachePath + "/tmp/";
        Activity a = a(this.A);
        if (a != null) {
            Display defaultDisplay = a.getWindowManager().getDefaultDisplay();
            this.g.a(moduleFileName, appSDCardPath, appCachePath, appSecondCachePath, str2, str4, this.an, str5, defaultDisplay.getWidth(), defaultDisplay.getHeight(), SysOSUtil.getDensityDpi(), mapTmpStgMax, domTmpStgMax, itsTmpStgMax, 0);
            this.g.d();
            return;
        }
        throw new RuntimeException("Please give the right context.");
    }

    void a(int i, int i2) {
        this.P = i;
        this.Q = i2;
    }

    public void a(long j, long j2, long j3, long j4, boolean z) {
        this.g.a(j, j2, j3, j4, z);
    }

    public void a(Bitmap bitmap) {
        int i = 0;
        if (this.g != null) {
            Bundle bundle;
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject.put("type", 0);
                jSONObject2.put("x", N);
                jSONObject2.put("y", O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put(UriUtil.DATA_SCHEME, jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (bitmap == null) {
                bundle = null;
            } else {
                Bundle bundle2 = new Bundle();
                ArrayList arrayList = new ArrayList();
                ParcelItem parcelItem = new ParcelItem();
                Bundle bundle3 = new Bundle();
                Buffer allocate = ByteBuffer.allocate((bitmap.getWidth() * bitmap.getHeight()) * 4);
                bitmap.copyPixelsToBuffer(allocate);
                bundle3.putByteArray("imgdata", allocate.array());
                bundle3.putInt("imgindex", bitmap.hashCode());
                bundle3.putInt("imgH", bitmap.getHeight());
                bundle3.putInt("imgW", bitmap.getWidth());
                bundle3.putInt("hasIcon", 1);
                parcelItem.setBundle(bundle3);
                arrayList.add(parcelItem);
                if (arrayList.size() > 0) {
                    Parcelable[] parcelableArr = new ParcelItem[arrayList.size()];
                    while (true) {
                        int i2 = i;
                        if (i2 >= arrayList.size()) {
                            break;
                        }
                        parcelableArr[i2] = (ParcelItem) arrayList.get(i2);
                        i = i2 + 1;
                    }
                    bundle2.putParcelableArray("icondata", parcelableArr);
                }
                bundle = bundle2;
            }
            b(jSONObject.toString(), bundle);
            this.g.b(this.D.a);
        }
    }

    void a(Handler handler) {
        MessageCenter.registMessage(m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.registMessage(39, handler);
        MessageCenter.registMessage(41, handler);
        MessageCenter.registMessage(49, handler);
        MessageCenter.registMessage(m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.registMessage(50, handler);
        MessageCenter.registMessage(999, handler);
        BaseMapCallback.addLayerDataInterface(this.h, this);
    }

    public void a(LatLngBounds latLngBounds) {
        if (latLngBounds != null && this.g != null) {
            LatLng latLng = latLngBounds.northeast;
            LatLng latLng2 = latLngBounds.southwest;
            GeoPoint ll2mc = CoordUtil.ll2mc(latLng);
            GeoPoint ll2mc2 = CoordUtil.ll2mc(latLng2);
            int longitudeE6 = (int) ll2mc.getLongitudeE6();
            int latitudeE6 = (int) ll2mc2.getLatitudeE6();
            int longitudeE62 = (int) ll2mc2.getLongitudeE6();
            int latitudeE62 = (int) ll2mc.getLatitudeE6();
            Bundle bundle = new Bundle();
            bundle.putInt("maxCoorx", longitudeE6);
            bundle.putInt("minCoory", latitudeE6);
            bundle.putInt("minCoorx", longitudeE62);
            bundle.putInt("maxCoory", latitudeE62);
            this.g.b(bundle);
        }
    }

    void a(ac acVar) {
        ae aeVar = new ae();
        if (acVar == null) {
            acVar = new ac();
        }
        aeVar = acVar.a;
        this.v = acVar.f;
        this.w = acVar.d;
        this.d = acVar.e;
        this.e = acVar.g;
        this.g.a(aeVar.a(this));
        this.g.c(ab.DEFAULT.ordinal());
        this.r = acVar.b;
        if (acVar.b) {
            N = (int) (SysOSUtil.getDensity() * 40.0f);
            O = (int) (SysOSUtil.getDensity() * 40.0f);
            JSONObject jSONObject = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            try {
                jSONObject2.put("x", N);
                jSONObject2.put("y", O);
                jSONObject2.put("hidetime", 1000);
                jSONArray.put(jSONObject2);
                jSONObject.put(UriUtil.DATA_SCHEME, jSONArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            this.D.a(jSONObject.toString());
            this.g.a(this.D.a, true);
        } else {
            this.g.a(this.D.a, false);
        }
        int i = acVar.c;
        if (i == 2) {
            a(true);
        }
        if (i == 3) {
            this.g.a(this.am.a, false);
            this.g.a(this.ap.a, false);
            this.g.a(this.F.a, false);
            this.g.e(false);
        }
    }

    public void a(ae aeVar) {
        if (this.g != null && aeVar != null) {
            Bundle a = aeVar.a(this);
            a.putInt("animation", 0);
            a.putInt("animatime", 0);
            this.g.a(a);
        }
    }

    public void a(ae aeVar, int i) {
        if (this.g != null) {
            Bundle a = aeVar.a(this);
            a.putInt("animation", 1);
            a.putInt("animatime", i);
            if (this.ar) {
                this.as.add(new a(a));
                return;
            }
            A();
            this.g.a(a);
        }
    }

    public void a(al alVar) {
        this.z = alVar;
    }

    void a(d dVar) {
        if (this.g != null) {
            dVar.a = this.g.a(dVar.c, dVar.d, dVar.b);
            this.B.add(dVar);
        }
    }

    public void a(l lVar) {
        this.f.add(lVar);
    }

    public void a(q qVar) {
        this.L = qVar;
    }

    public void a(String str, Bundle bundle) {
        if (this.g != null) {
            this.C.a(str);
            this.C.a(bundle);
            this.g.b(this.C.a);
        }
    }

    public void a(List<Bundle> list) {
        if (this.g != null && list != null) {
            int size = list.size();
            Bundle[] bundleArr = new Bundle[list.size()];
            for (int i = 0; i < size; i++) {
                g((Bundle) list.get(i));
                bundleArr[i] = (Bundle) list.get(i);
            }
            this.g.a(bundleArr);
        }
    }

    public void a(boolean z) {
        if (this.g != null) {
            if (!this.g.a(this.am.a)) {
                this.g.a(this.am.a, true);
            }
            this.q = z;
            P();
            this.g.a(this.q);
        }
    }

    public boolean a(float f, float f2, float f3, float f4) {
        float f5 = ((float) this.Q) - f2;
        float f6 = ((float) this.Q) - f4;
        if (this.S.e) {
            double sqrt;
            int log;
            int atan2;
            if (this.R == 0) {
                if ((this.S.c - f5 <= 0.0f || this.S.d - f6 <= 0.0f) && (this.S.c - f5 >= 0.0f || this.S.d - f6 >= 0.0f)) {
                    this.R = 2;
                } else {
                    sqrt = Math.sqrt((double) (((f3 - f) * (f3 - f)) + ((f6 - f5) * (f6 - f5)))) / this.S.h;
                    log = (int) ((Math.log(sqrt) / Math.log(2.0d)) * 10000.0d);
                    atan2 = (int) (((Math.atan2((double) (f6 - f5), (double) (f3 - f)) - Math.atan2((double) (this.S.d - this.S.c), (double) (this.S.b - this.S.a))) * 180.0d) / 3.1416d);
                    if ((sqrt <= 0.0d || (log <= PathInterpolatorCompat.MAX_NUM_POINTS && log >= -3000)) && Math.abs(atan2) < 10) {
                        this.R = 1;
                    } else {
                        this.R = 2;
                    }
                }
                if (this.R == 0) {
                    return true;
                }
            }
            if (this.R == 1 && this.v) {
                if (this.S.c - f5 > 0.0f && this.S.d - f6 > 0.0f) {
                    K();
                    a(1, 83, 0);
                } else if (this.S.c - f5 < 0.0f && this.S.d - f6 < 0.0f) {
                    K();
                    a(1, 87, 0);
                }
            } else if (this.R == 2 || this.R == 4 || this.R == 3) {
                double atan22 = Math.atan2((double) (f6 - f5), (double) (f3 - f)) - Math.atan2((double) (this.S.d - this.S.c), (double) (this.S.b - this.S.a));
                sqrt = Math.sqrt((double) (((f3 - f) * (f3 - f)) + ((f6 - f5) * (f6 - f5)))) / this.S.h;
                log = (int) ((Math.log(sqrt) / Math.log(2.0d)) * 10000.0d);
                double atan23 = Math.atan2((double) (this.S.g - this.S.c), (double) (this.S.f - this.S.a));
                double sqrt2 = Math.sqrt((double) (((this.S.f - this.S.a) * (this.S.f - this.S.a)) + ((this.S.g - this.S.c) * (this.S.g - this.S.c))));
                float cos = (float) (((Math.cos(atan23 + atan22) * sqrt2) * sqrt) + ((double) f));
                float sin = (float) (((Math.sin(atan23 + atan22) * sqrt2) * sqrt) + ((double) f5));
                atan2 = (int) ((atan22 * 180.0d) / 3.1416d);
                if (sqrt > 0.0d && (3 == this.R || (Math.abs(log) > m_AppUI.MSG_APP_DATA_OK && 2 == this.R))) {
                    this.R = 3;
                    float f7 = E().a;
                    if (this.e) {
                        if (sqrt > 1.0d) {
                            if (f7 >= this.a) {
                                return false;
                            }
                            K();
                            a((int) k_event.V_WM_ROTATE, 3, log);
                        } else if (f7 <= this.b) {
                            return false;
                        } else {
                            K();
                            a((int) k_event.V_WM_ROTATE, 3, log);
                        }
                    }
                } else if (atan2 != 0 && (4 == this.R || (Math.abs(atan2) > 10 && 2 == this.R))) {
                    this.R = 4;
                    if (this.w) {
                        BaiduMap.mapStatusReason |= 1;
                        K();
                        a((int) k_event.V_WM_ROTATE, 1, atan2);
                    }
                }
                this.S.f = cos;
                this.S.g = sin;
            }
        }
        if (2 != this.R) {
            this.S.c = f5;
            this.S.d = f6;
            this.S.a = f;
            this.S.b = f3;
        }
        if (!this.S.e) {
            this.S.f = (float) (this.P / 2);
            this.S.g = (float) (this.Q / 2);
            this.S.e = true;
            if (0.0d == this.S.h) {
                this.S.h = Math.sqrt((double) (((this.S.b - this.S.a) * (this.S.b - this.S.a)) + ((this.S.d - this.S.c) * (this.S.d - this.S.c))));
            }
        }
        return true;
    }

    public boolean a(long j) {
        for (d dVar : this.B) {
            if (dVar.a == j) {
                return true;
            }
        }
        return false;
    }

    public boolean a(Point point) {
        if (point == null || this.g == null || point.x < 0 || point.y < 0) {
            return false;
        }
        N = point.x;
        O = point.y;
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        try {
            jSONObject2.put("x", N);
            jSONObject2.put("y", O);
            jSONObject2.put("hidetime", 1000);
            jSONArray.put(jSONObject2);
            jSONObject.put(UriUtil.DATA_SCHEME, jSONArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.D.a(jSONObject.toString());
        this.g.b(this.D.a);
        return true;
    }

    public boolean a(Bundle bundle) {
        if (this.g == null) {
            return false;
        }
        this.y = new am();
        long a = this.g.a(this.y.c, this.y.d, this.y.b);
        if (a == 0) {
            return false;
        }
        this.y.a = a;
        this.B.add(this.y);
        bundle.putLong("sdktileaddr", a);
        return e(bundle) && f(bundle);
    }

    /* JADX WARNING: Missing block: B:5:0x0036, code:
            if (c((int) r21.getX(1), (int) r21.getY(1)) == false) goto L_0x0038;
     */
    boolean a(android.view.MotionEvent r21) {
        /*
        r20 = this;
        r2 = 1;
        r3 = r21.getPointerCount();
        r4 = 2;
        if (r3 != r4) goto L_0x0039;
    L_0x0008:
        r4 = 0;
        r0 = r21;
        r4 = r0.getX(r4);
        r4 = (int) r4;
        r5 = 0;
        r0 = r21;
        r5 = r0.getY(r5);
        r5 = (int) r5;
        r0 = r20;
        r4 = r0.c(r4, r5);
        if (r4 == 0) goto L_0x0038;
    L_0x0020:
        r4 = 1;
        r0 = r21;
        r4 = r0.getX(r4);
        r4 = (int) r4;
        r5 = 1;
        r0 = r21;
        r5 = r0.getY(r5);
        r5 = (int) r5;
        r0 = r20;
        r4 = r0.c(r4, r5);
        if (r4 != 0) goto L_0x0039;
    L_0x0038:
        r3 = 1;
    L_0x0039:
        r4 = 2;
        if (r3 != r4) goto L_0x0506;
    L_0x003c:
        r0 = r20;
        r2 = r0.Q;
        r2 = (float) r2;
        r3 = 0;
        r0 = r21;
        r3 = r0.getY(r3);
        r4 = r2 - r3;
        r0 = r20;
        r2 = r0.Q;
        r2 = (float) r2;
        r3 = 1;
        r0 = r21;
        r3 = r0.getY(r3);
        r5 = r2 - r3;
        r2 = 0;
        r0 = r21;
        r6 = r0.getX(r2);
        r2 = 1;
        r0 = r21;
        r7 = r0.getX(r2);
        r2 = r21.getAction();
        switch(r2) {
            case 5: goto L_0x01a1;
            case 6: goto L_0x01c9;
            case 261: goto L_0x01b5;
            case 262: goto L_0x01dd;
            default: goto L_0x006d;
        };
    L_0x006d:
        r0 = r20;
        r2 = r0.T;
        if (r2 != 0) goto L_0x007b;
    L_0x0073:
        r2 = android.view.VelocityTracker.obtain();
        r0 = r20;
        r0.T = r2;
    L_0x007b:
        r0 = r20;
        r2 = r0.T;
        r0 = r21;
        r2.addMovement(r0);
        r2 = android.view.ViewConfiguration.getMinimumFlingVelocity();
        r3 = android.view.ViewConfiguration.getMaximumFlingVelocity();
        r0 = r20;
        r8 = r0.T;
        r9 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r3 = (float) r3;
        r8.computeCurrentVelocity(r9, r3);
        r0 = r20;
        r3 = r0.T;
        r8 = 1;
        r3 = r3.getXVelocity(r8);
        r0 = r20;
        r8 = r0.T;
        r9 = 1;
        r8 = r8.getYVelocity(r9);
        r0 = r20;
        r9 = r0.T;
        r10 = 2;
        r9 = r9.getXVelocity(r10);
        r0 = r20;
        r10 = r0.T;
        r11 = 2;
        r10 = r10.getYVelocity(r11);
        r3 = java.lang.Math.abs(r3);
        r11 = (float) r2;
        r3 = (r3 > r11 ? 1 : (r3 == r11 ? 0 : -1));
        if (r3 > 0) goto L_0x00de;
    L_0x00c3:
        r3 = java.lang.Math.abs(r8);
        r8 = (float) r2;
        r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1));
        if (r3 > 0) goto L_0x00de;
    L_0x00cc:
        r3 = java.lang.Math.abs(r9);
        r8 = (float) r2;
        r3 = (r3 > r8 ? 1 : (r3 == r8 ? 0 : -1));
        if (r3 > 0) goto L_0x00de;
    L_0x00d5:
        r3 = java.lang.Math.abs(r10);
        r2 = (float) r2;
        r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0497;
    L_0x00de:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.e;
        r3 = 1;
        if (r2 != r3) goto L_0x0234;
    L_0x00e7:
        r0 = r20;
        r2 = r0.R;
        if (r2 != 0) goto L_0x01fd;
    L_0x00ed:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.c;
        r2 = r2 - r4;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x0105;
    L_0x00f9:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.d;
        r2 = r2 - r5;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x011d;
    L_0x0105:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.c;
        r2 = r2 - r4;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 >= 0) goto L_0x01f7;
    L_0x0111:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.d;
        r2 = r2 - r5;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 >= 0) goto L_0x01f7;
    L_0x011d:
        r2 = r5 - r4;
        r2 = (double) r2;
        r8 = r7 - r6;
        r8 = (double) r8;
        r2 = java.lang.Math.atan2(r2, r8);
        r0 = r20;
        r8 = r0.S;
        r8 = r8.d;
        r0 = r20;
        r9 = r0.S;
        r9 = r9.c;
        r8 = r8 - r9;
        r8 = (double) r8;
        r0 = r20;
        r10 = r0.S;
        r10 = r10.b;
        r0 = r20;
        r11 = r0.S;
        r11 = r11.a;
        r10 = r10 - r11;
        r10 = (double) r10;
        r8 = java.lang.Math.atan2(r8, r10);
        r2 = r2 - r8;
        r8 = r7 - r6;
        r9 = r7 - r6;
        r8 = r8 * r9;
        r9 = r5 - r4;
        r10 = r5 - r4;
        r9 = r9 * r10;
        r8 = r8 + r9;
        r8 = (double) r8;
        r8 = java.lang.Math.sqrt(r8);
        r0 = r20;
        r10 = r0.S;
        r10 = r10.h;
        r8 = r8 / r10;
        r10 = java.lang.Math.log(r8);
        r12 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r12 = java.lang.Math.log(r12);
        r10 = r10 / r12;
        r12 = 4666723172467343360; // 0x40c3880000000000 float:0.0 double:10000.0;
        r10 = r10 * r12;
        r10 = (int) r10;
        r12 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r2 = r2 * r12;
        r12 = 4614256673094690983; // 0x400921ff2e48e8a7 float:4.5681372E-11 double:3.1416;
        r2 = r2 / r12;
        r2 = (int) r2;
        r12 = 0;
        r3 = (r8 > r12 ? 1 : (r8 == r12 ? 0 : -1));
        if (r3 <= 0) goto L_0x018c;
    L_0x0184:
        r3 = 3000; // 0xbb8 float:4.204E-42 double:1.482E-320;
        if (r10 > r3) goto L_0x0194;
    L_0x0188:
        r3 = -3000; // 0xfffffffffffff448 float:NaN double:NaN;
        if (r10 < r3) goto L_0x0194;
    L_0x018c:
        r2 = java.lang.Math.abs(r2);
        r3 = 10;
        if (r2 < r3) goto L_0x01f1;
    L_0x0194:
        r2 = 2;
        r0 = r20;
        r0.R = r2;
    L_0x0199:
        r0 = r20;
        r2 = r0.R;
        if (r2 != 0) goto L_0x01fd;
    L_0x019f:
        r2 = 1;
    L_0x01a0:
        return r2;
    L_0x01a1:
        r2 = r21.getEventTime();
        r0 = r20;
        r0.V = r2;
        r0 = r20;
        r2 = r0.Y;
        r2 = r2 + -1;
        r0 = r20;
        r0.Y = r2;
        goto L_0x006d;
    L_0x01b5:
        r2 = r21.getEventTime();
        r0 = r20;
        r0.U = r2;
        r0 = r20;
        r2 = r0.Y;
        r2 = r2 + -1;
        r0 = r20;
        r0.Y = r2;
        goto L_0x006d;
    L_0x01c9:
        r2 = r21.getEventTime();
        r0 = r20;
        r0.X = r2;
        r0 = r20;
        r2 = r0.Y;
        r2 = r2 + 1;
        r0 = r20;
        r0.Y = r2;
        goto L_0x006d;
    L_0x01dd:
        r2 = r21.getEventTime();
        r0 = r20;
        r0.W = r2;
        r0 = r20;
        r2 = r0.Y;
        r2 = r2 + 1;
        r0 = r20;
        r0.Y = r2;
        goto L_0x006d;
    L_0x01f1:
        r2 = 1;
        r0 = r20;
        r0.R = r2;
        goto L_0x0199;
    L_0x01f7:
        r2 = 2;
        r0 = r20;
        r0.R = r2;
        goto L_0x0199;
    L_0x01fd:
        r0 = r20;
        r2 = r0.R;
        r3 = 1;
        if (r2 != r3) goto L_0x02f9;
    L_0x0204:
        r0 = r20;
        r2 = r0.v;
        if (r2 == 0) goto L_0x02f9;
    L_0x020a:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.c;
        r2 = r2 - r4;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x02cd;
    L_0x0216:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.d;
        r2 = r2 - r5;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 <= 0) goto L_0x02cd;
    L_0x0222:
        r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason;
        r2 = r2 | 1;
        com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2;
        r20.K();
        r2 = 1;
        r3 = 83;
        r8 = 0;
        r0 = r20;
        r0.a(r2, r3, r8);
    L_0x0234:
        r2 = 2;
        r0 = r20;
        r3 = r0.R;
        if (r2 == r3) goto L_0x0253;
    L_0x023b:
        r0 = r20;
        r2 = r0.S;
        r2.c = r4;
        r0 = r20;
        r2 = r0.S;
        r2.d = r5;
        r0 = r20;
        r2 = r0.S;
        r2.a = r6;
        r0 = r20;
        r2 = r0.S;
        r2.b = r7;
    L_0x0253:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.e;
        if (r2 != 0) goto L_0x02ca;
    L_0x025b:
        r0 = r20;
        r2 = r0.S;
        r0 = r20;
        r3 = r0.P;
        r3 = r3 / 2;
        r3 = (float) r3;
        r2.f = r3;
        r0 = r20;
        r2 = r0.S;
        r0 = r20;
        r3 = r0.Q;
        r3 = r3 / 2;
        r3 = (float) r3;
        r2.g = r3;
        r0 = r20;
        r2 = r0.S;
        r3 = 1;
        r2.e = r3;
        r2 = 0;
        r0 = r20;
        r4 = r0.S;
        r4 = r4.h;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 != 0) goto L_0x02ca;
    L_0x0288:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.b;
        r0 = r20;
        r3 = r0.S;
        r3 = r3.a;
        r2 = r2 - r3;
        r0 = r20;
        r3 = r0.S;
        r3 = r3.b;
        r0 = r20;
        r4 = r0.S;
        r4 = r4.a;
        r3 = r3 - r4;
        r2 = r2 * r3;
        r0 = r20;
        r3 = r0.S;
        r3 = r3.d;
        r0 = r20;
        r4 = r0.S;
        r4 = r4.c;
        r3 = r3 - r4;
        r0 = r20;
        r4 = r0.S;
        r4 = r4.d;
        r0 = r20;
        r5 = r0.S;
        r5 = r5.c;
        r4 = r4 - r5;
        r3 = r3 * r4;
        r2 = r2 + r3;
        r2 = (double) r2;
        r2 = java.lang.Math.sqrt(r2);
        r0 = r20;
        r4 = r0.S;
        r4.h = r2;
    L_0x02ca:
        r2 = 1;
        goto L_0x01a0;
    L_0x02cd:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.c;
        r2 = r2 - r4;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 >= 0) goto L_0x0234;
    L_0x02d9:
        r0 = r20;
        r2 = r0.S;
        r2 = r2.d;
        r2 = r2 - r5;
        r3 = 0;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 >= 0) goto L_0x0234;
    L_0x02e5:
        r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason;
        r2 = r2 | 1;
        com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2;
        r20.K();
        r2 = 1;
        r3 = 87;
        r8 = 0;
        r0 = r20;
        r0.a(r2, r3, r8);
        goto L_0x0234;
    L_0x02f9:
        r0 = r20;
        r2 = r0.R;
        r3 = 2;
        if (r2 == r3) goto L_0x030e;
    L_0x0300:
        r0 = r20;
        r2 = r0.R;
        r3 = 4;
        if (r2 == r3) goto L_0x030e;
    L_0x0307:
        r0 = r20;
        r2 = r0.R;
        r3 = 3;
        if (r2 != r3) goto L_0x0234;
    L_0x030e:
        r2 = r5 - r4;
        r2 = (double) r2;
        r8 = r7 - r6;
        r8 = (double) r8;
        r2 = java.lang.Math.atan2(r2, r8);
        r0 = r20;
        r8 = r0.S;
        r8 = r8.d;
        r0 = r20;
        r9 = r0.S;
        r9 = r9.c;
        r8 = r8 - r9;
        r8 = (double) r8;
        r0 = r20;
        r10 = r0.S;
        r10 = r10.b;
        r0 = r20;
        r11 = r0.S;
        r11 = r11.a;
        r10 = r10 - r11;
        r10 = (double) r10;
        r8 = java.lang.Math.atan2(r8, r10);
        r2 = r2 - r8;
        r8 = r7 - r6;
        r9 = r7 - r6;
        r8 = r8 * r9;
        r9 = r5 - r4;
        r10 = r5 - r4;
        r9 = r9 * r10;
        r8 = r8 + r9;
        r8 = (double) r8;
        r8 = java.lang.Math.sqrt(r8);
        r0 = r20;
        r10 = r0.S;
        r10 = r10.h;
        r8 = r8 / r10;
        r10 = java.lang.Math.log(r8);
        r12 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r12 = java.lang.Math.log(r12);
        r10 = r10 / r12;
        r12 = 4666723172467343360; // 0x40c3880000000000 float:0.0 double:10000.0;
        r10 = r10 * r12;
        r10 = (int) r10;
        r0 = r20;
        r11 = r0.S;
        r11 = r11.g;
        r0 = r20;
        r12 = r0.S;
        r12 = r12.c;
        r11 = r11 - r12;
        r12 = (double) r11;
        r0 = r20;
        r11 = r0.S;
        r11 = r11.f;
        r0 = r20;
        r14 = r0.S;
        r14 = r14.a;
        r11 = r11 - r14;
        r14 = (double) r11;
        r12 = java.lang.Math.atan2(r12, r14);
        r0 = r20;
        r11 = r0.S;
        r11 = r11.f;
        r0 = r20;
        r14 = r0.S;
        r14 = r14.a;
        r11 = r11 - r14;
        r0 = r20;
        r14 = r0.S;
        r14 = r14.f;
        r0 = r20;
        r15 = r0.S;
        r15 = r15.a;
        r14 = r14 - r15;
        r11 = r11 * r14;
        r0 = r20;
        r14 = r0.S;
        r14 = r14.g;
        r0 = r20;
        r15 = r0.S;
        r15 = r15.c;
        r14 = r14 - r15;
        r0 = r20;
        r15 = r0.S;
        r15 = r15.g;
        r0 = r20;
        r0 = r0.S;
        r16 = r0;
        r0 = r16;
        r0 = r0.c;
        r16 = r0;
        r15 = r15 - r16;
        r14 = r14 * r15;
        r11 = r11 + r14;
        r14 = (double) r11;
        r14 = java.lang.Math.sqrt(r14);
        r16 = r12 + r2;
        r16 = java.lang.Math.cos(r16);
        r16 = r16 * r14;
        r16 = r16 * r8;
        r0 = (double) r6;
        r18 = r0;
        r16 = r16 + r18;
        r0 = r16;
        r11 = (float) r0;
        r12 = r12 + r2;
        r12 = java.lang.Math.sin(r12);
        r12 = r12 * r14;
        r12 = r12 * r8;
        r14 = (double) r4;
        r12 = r12 + r14;
        r12 = (float) r12;
        r14 = 4640537203540230144; // 0x4066800000000000 float:0.0 double:180.0;
        r2 = r2 * r14;
        r14 = 4614256673094690983; // 0x400921ff2e48e8a7 float:4.5681372E-11 double:3.1416;
        r2 = r2 / r14;
        r2 = (int) r2;
        r14 = 0;
        r3 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r3 <= 0) goto L_0x0468;
    L_0x03f4:
        r3 = 3;
        r0 = r20;
        r13 = r0.R;
        if (r3 == r13) goto L_0x040a;
    L_0x03fb:
        r3 = java.lang.Math.abs(r10);
        r13 = 2000; // 0x7d0 float:2.803E-42 double:9.88E-321;
        if (r3 <= r13) goto L_0x0468;
    L_0x0403:
        r3 = 2;
        r0 = r20;
        r13 = r0.R;
        if (r3 != r13) goto L_0x0468;
    L_0x040a:
        r2 = 3;
        r0 = r20;
        r0.R = r2;
        r2 = r20.E();
        r2 = r2.a;
        r0 = r20;
        r3 = r0.e;
        if (r3 == 0) goto L_0x043d;
    L_0x041b:
        r14 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r3 = (r8 > r14 ? 1 : (r8 == r14 ? 0 : -1));
        if (r3 <= 0) goto L_0x044b;
    L_0x0421:
        r0 = r20;
        r3 = r0.a;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 < 0) goto L_0x042c;
    L_0x0429:
        r2 = 0;
        goto L_0x01a0;
    L_0x042c:
        r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason;
        r2 = r2 | 1;
        com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2;
        r20.K();
        r2 = 8193; // 0x2001 float:1.1481E-41 double:4.048E-320;
        r3 = 3;
        r0 = r20;
        r0.a(r2, r3, r10);
    L_0x043d:
        r0 = r20;
        r2 = r0.S;
        r2.f = r11;
        r0 = r20;
        r2 = r0.S;
        r2.g = r12;
        goto L_0x0234;
    L_0x044b:
        r0 = r20;
        r3 = r0.b;
        r2 = (r2 > r3 ? 1 : (r2 == r3 ? 0 : -1));
        if (r2 > 0) goto L_0x0456;
    L_0x0453:
        r2 = 0;
        goto L_0x01a0;
    L_0x0456:
        r2 = com.baidu.mapapi.map.BaiduMap.mapStatusReason;
        r2 = r2 | 1;
        com.baidu.mapapi.map.BaiduMap.mapStatusReason = r2;
        r20.K();
        r2 = 8193; // 0x2001 float:1.1481E-41 double:4.048E-320;
        r3 = 3;
        r0 = r20;
        r0.a(r2, r3, r10);
        goto L_0x043d;
    L_0x0468:
        if (r2 == 0) goto L_0x043d;
    L_0x046a:
        r3 = 4;
        r0 = r20;
        r8 = r0.R;
        if (r3 == r8) goto L_0x0480;
    L_0x0471:
        r3 = java.lang.Math.abs(r2);
        r8 = 10;
        if (r3 <= r8) goto L_0x043d;
    L_0x0479:
        r3 = 2;
        r0 = r20;
        r8 = r0.R;
        if (r3 != r8) goto L_0x043d;
    L_0x0480:
        r3 = 4;
        r0 = r20;
        r0.R = r3;
        r0 = r20;
        r3 = r0.w;
        if (r3 == 0) goto L_0x043d;
    L_0x048b:
        r20.K();
        r3 = 8193; // 0x2001 float:1.1481E-41 double:4.048E-320;
        r8 = 1;
        r0 = r20;
        r0.a(r3, r8, r2);
        goto L_0x043d;
    L_0x0497:
        r0 = r20;
        r2 = r0.R;
        if (r2 != 0) goto L_0x0234;
    L_0x049d:
        r0 = r20;
        r2 = r0.Y;
        if (r2 != 0) goto L_0x0234;
    L_0x04a3:
        r0 = r20;
        r2 = r0.W;
        r0 = r20;
        r8 = r0.X;
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 <= 0) goto L_0x04fc;
    L_0x04af:
        r0 = r20;
        r2 = r0.W;
    L_0x04b3:
        r0 = r20;
        r0.W = r2;
        r0 = r20;
        r2 = r0.U;
        r0 = r20;
        r8 = r0.V;
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 >= 0) goto L_0x0501;
    L_0x04c3:
        r0 = r20;
        r2 = r0.V;
    L_0x04c7:
        r0 = r20;
        r0.U = r2;
        r0 = r20;
        r2 = r0.W;
        r0 = r20;
        r8 = r0.U;
        r2 = r2 - r8;
        r8 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r2 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r2 >= 0) goto L_0x0234;
    L_0x04da:
        r0 = r20;
        r2 = r0.e;
        if (r2 == 0) goto L_0x0234;
    L_0x04e0:
        r2 = r20.E();
        if (r2 == 0) goto L_0x0234;
    L_0x04e6:
        r3 = r2.a;
        r8 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r3 = r3 - r8;
        r2.a = r3;
        r3 = com.baidu.mapapi.map.BaiduMap.mapStatusReason;
        r3 = r3 | 1;
        com.baidu.mapapi.map.BaiduMap.mapStatusReason = r3;
        r3 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        r0 = r20;
        r0.a(r2, r3);
        goto L_0x0234;
    L_0x04fc:
        r0 = r20;
        r2 = r0.X;
        goto L_0x04b3;
    L_0x0501:
        r0 = r20;
        r2 = r0.U;
        goto L_0x04c7;
    L_0x0506:
        r3 = r21.getAction();
        switch(r3) {
            case 0: goto L_0x0510;
            case 1: goto L_0x0515;
            case 2: goto L_0x051b;
            default: goto L_0x050d;
        };
    L_0x050d:
        r2 = 0;
        goto L_0x01a0;
    L_0x0510:
        r20.b(r21);
        goto L_0x01a0;
    L_0x0515:
        r2 = r20.d(r21);
        goto L_0x01a0;
    L_0x051b:
        r20.c(r21);
        goto L_0x01a0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.e.a(android.view.MotionEvent):boolean");
    }

    public boolean a(String str, String str2) {
        return this.g.a(str, str2);
    }

    public GeoPoint b(int i, int i2) {
        return this.M.a(i, i2);
    }

    void b(float f, float f2) {
        if (!this.S.e) {
            this.ad = System.currentTimeMillis();
            if (this.ad - this.ac >= 400) {
                this.ac = this.ad;
            } else if (Math.abs(f - this.Z) >= 120.0f || Math.abs(f2 - this.aa) >= 120.0f) {
                this.ac = this.ad;
            } else {
                this.ac = 0;
                this.ae = true;
            }
            this.Z = f;
            this.aa = f2;
            a(4, 0, ((int) f) | (((int) f2) << 16));
            this.ab = true;
        }
    }

    void b(int i) {
        if (this.g != null) {
            this.g.b(i);
            this.g = null;
        }
    }

    public void b(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.f(bundle);
        }
    }

    void b(Handler handler) {
        MessageCenter.unregistMessage(m_AppUI.MSG_APP_SAVESCREEN, handler);
        MessageCenter.unregistMessage(41, handler);
        MessageCenter.unregistMessage(49, handler);
        MessageCenter.unregistMessage(39, handler);
        MessageCenter.unregistMessage(m_AppUI.V_WM_VDATAENGINE, handler);
        MessageCenter.unregistMessage(50, handler);
        MessageCenter.unregistMessage(999, handler);
        BaseMapCallback.removeLayerDataInterface(this.h);
    }

    void b(MotionEvent motionEvent) {
        if (!this.S.e) {
            this.ad = motionEvent.getDownTime();
            if (this.ad - this.ac >= 400) {
                this.ac = this.ad;
            } else if (Math.abs(motionEvent.getX() - this.Z) >= 120.0f || Math.abs(motionEvent.getY() - this.aa) >= 120.0f) {
                this.ac = this.ad;
            } else {
                this.ac = 0;
            }
            this.Z = motionEvent.getX();
            this.aa = motionEvent.getY();
            a(4, 0, ((int) motionEvent.getX()) | (((int) motionEvent.getY()) << 16));
            this.ab = true;
        }
    }

    public void b(String str, Bundle bundle) {
        if (this.g != null) {
            this.D.a(str);
            this.D.a(bundle);
            this.g.b(this.D.a);
        }
    }

    public void b(boolean z) {
        this.x = z;
    }

    public boolean b() {
        return this.x;
    }

    public void c() {
        if (this.g != null) {
            for (d dVar : this.B) {
                this.g.a(dVar.a, false);
            }
        }
    }

    public void c(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.g(bundle);
        }
    }

    public void c(boolean z) {
        if (this.g != null) {
            this.g.a(this.D.a, z);
        }
    }

    boolean c(float f, float f2) {
        if (this.S.e) {
            return true;
        }
        if (System.currentTimeMillis() - k < 300) {
            return true;
        }
        if (this.n) {
            for (l d : this.f) {
                d.d(b((int) f, (int) f2));
            }
            return true;
        }
        float abs = Math.abs(f - this.Z);
        float abs2 = Math.abs(f2 - this.aa);
        float density = (float) (((double) SysOSUtil.getDensity()) > 1.5d ? ((double) SysOSUtil.getDensity()) * 1.5d : (double) SysOSUtil.getDensity());
        if (this.ab && abs / density <= 3.0f && abs2 / density <= 3.0f) {
            return true;
        }
        this.ab = false;
        int i = (int) f;
        int i2 = (int) f2;
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        if (!this.d) {
            return false;
        }
        this.ag = this.ai;
        this.ah = this.aj;
        this.ai = f;
        this.aj = f2;
        this.ak = this.al;
        this.al = System.currentTimeMillis();
        this.af = true;
        K();
        a(3, 0, (i2 << 16) | i);
        return false;
    }

    boolean c(int i, int i2) {
        return i >= 0 && i <= this.P + 0 && i2 >= 0 && i2 <= this.Q + 0;
    }

    boolean c(MotionEvent motionEvent) {
        if (this.S.e) {
            return true;
        }
        if (System.currentTimeMillis() - k < 300) {
            return true;
        }
        if (this.n) {
            for (l d : this.f) {
                d.d(b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            return true;
        }
        float abs = Math.abs(motionEvent.getX() - this.Z);
        float abs2 = Math.abs(motionEvent.getY() - this.aa);
        float density = (float) (((double) SysOSUtil.getDensity()) > 1.5d ? ((double) SysOSUtil.getDensity()) * 1.5d : (double) SysOSUtil.getDensity());
        if (this.ab && abs / density <= 3.0f && abs2 / density <= 3.0f) {
            return true;
        }
        this.ab = false;
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (!this.d) {
            return false;
        }
        BaiduMap.mapStatusReason |= 1;
        K();
        a(3, 0, (y << 16) | x);
        return false;
    }

    public void d() {
        if (this.g != null) {
            for (d dVar : this.B) {
                if ((dVar instanceof aa) || (dVar instanceof a) || (dVar instanceof p)) {
                    this.g.a(dVar.a, false);
                } else {
                    this.g.a(dVar.a, true);
                }
            }
            this.g.c(false);
        }
    }

    public void d(Bundle bundle) {
        if (this.g != null) {
            g(bundle);
            this.g.h(bundle);
        }
    }

    public void d(boolean z) {
        if (this.g != null) {
            this.g.a(this.y.a, z);
        }
    }

    boolean d(float f, float f2) {
        if (this.n) {
            for (l e : this.f) {
                e.e(b((int) f, (int) f2));
            }
            this.n = false;
            return true;
        }
        if (!this.S.e) {
            if (this.ae) {
                return e(f, f2);
            }
            if (this.af) {
                return Q();
            }
            if (System.currentTimeMillis() - this.ad < 400 && Math.abs(f - this.Z) < 10.0f && Math.abs(f2 - this.aa) < 10.0f) {
                M();
                return true;
            }
        }
        M();
        int i = (int) f;
        int i2 = (int) f2;
        if (i < 0) {
            i = 0;
        }
        if (i2 < 0) {
            i2 = 0;
        }
        a(5, 0, (i2 << 16) | i);
        return true;
    }

    boolean d(MotionEvent motionEvent) {
        if (this.n) {
            for (l e : this.f) {
                e.e(b((int) motionEvent.getX(), (int) motionEvent.getY()));
            }
            this.n = false;
            return true;
        }
        boolean z = !this.S.e && motionEvent.getEventTime() - this.ad < 400 && Math.abs(motionEvent.getX() - this.Z) < 10.0f && Math.abs(motionEvent.getY() - this.aa) < 10.0f;
        M();
        int x = (int) motionEvent.getX();
        int y = (int) motionEvent.getY();
        if (z) {
            return false;
        }
        if (x < 0) {
            x = 0;
        }
        a(5, 0, ((y < 0 ? 0 : y) << 16) | x);
        return true;
    }

    public void e(boolean z) {
        if (this.g != null) {
            this.g.a(this.am.a, z);
        }
    }

    public boolean e() {
        return (this.y == null || this.g == null) ? false : this.g.c(this.y.a);
    }

    void f() {
        if (this.g != null) {
            this.M = new ai(this.g);
        }
    }

    public void f(boolean z) {
        if (this.g != null) {
            this.u = z;
            this.g.b(this.u);
        }
    }

    public void g(boolean z) {
        if (this.g != null) {
            this.p = z;
            this.g.c(this.p);
        }
    }

    public boolean g() {
        return this.p;
    }

    public String h() {
        return this.g == null ? null : this.g.e(this.D.a);
    }

    public void h(boolean z) {
        if (this.g != null) {
            this.g.d(z);
        }
    }

    public void i(boolean z) {
        if (this.g != null) {
            this.r = z;
            this.g.a(this.D.a, z);
        }
    }

    public boolean i() {
        return this.u;
    }

    public void j(boolean z) {
        this.g.e(z);
        this.g.d(this.ao.a);
        this.g.d(this.ap.a);
    }

    public boolean j() {
        return this.g == null ? false : this.g.k();
    }

    public boolean k() {
        return this.q;
    }

    public void l(boolean z) {
        if (this.g != null) {
            this.s = z;
            this.g.a(this.C.a, z);
        }
    }

    public boolean l() {
        return this.g.a(this.am.a);
    }

    public void m(boolean z) {
        if (this.g != null) {
            this.t = z;
            this.g.a(this.J.a, z);
        }
    }

    public boolean m() {
        return this.g == null ? false : this.g.o();
    }

    public void n() {
        if (this.g != null) {
            this.g.d(this.E.a);
            this.g.d(this.I.a);
            this.g.d(this.G.a);
            this.g.d(this.H.a);
        }
    }

    public void n(boolean z) {
        this.d = z;
    }

    public void o() {
        if (this.g != null) {
            this.g.p();
            this.g.b(this.J.a);
        }
    }

    public void o(boolean z) {
        this.e = z;
    }

    public MapBaseIndoorMapInfo p() {
        return this.g.q();
    }

    public void p(boolean z) {
        this.w = z;
    }

    public void q(boolean z) {
        this.v = z;
    }

    public boolean q() {
        return this.g.r();
    }

    public void r(boolean z) {
        if (this.g != null) {
            this.g.a(this.F.a, z);
        }
    }

    public boolean r() {
        return this.r;
    }

    public void s(boolean z) {
        if (this.g != null) {
            this.g.a(this.ap.a, z);
        }
    }

    public boolean s() {
        return this.s;
    }

    public void t() {
        if (this.g != null) {
            this.g.b(this.J.a);
        }
    }

    public void t(boolean z) {
        this.ar = z;
    }

    public void u() {
        if (this.g != null) {
            this.g.e();
        }
    }

    public void v() {
        if (this.g != null) {
            this.g.f();
        }
    }

    public boolean w() {
        return this.d;
    }

    public boolean x() {
        return this.e;
    }

    public boolean y() {
        return this.w;
    }

    public boolean z() {
        return this.v;
    }
}
