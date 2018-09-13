package com.tencent.liteav.beauty;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.graphics.Bitmap;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.tencent.liteav.basic.c.f;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.rtmp.TXLiveConstants;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* compiled from: TXCVideoPreprocessor */
public class d extends com.tencent.liteav.basic.module.a implements e {
    protected Context a;
    protected boolean b = true;
    protected boolean c = false;
    protected int d = 0;
    protected int e = 0;
    protected int f = 0;
    protected com.tencent.liteav.basic.c.a g = null;
    protected b h;
    protected b i = new b();
    protected c j = null;
    f k;
    private boolean l = false;
    private long m = 0;
    private long n = 0;
    private long o = 0;
    private a p = new a(this);

    /* compiled from: TXCVideoPreprocessor */
    protected static class a {
        WeakReference<d> a;
        private HashMap<String, String> b = new HashMap();

        public a(d dVar) {
            this.a = new WeakReference(dVar);
        }

        public void a(String str, int i) {
            this.b.put(str, String.valueOf(i));
            d dVar = (d) this.a.get();
            if (dVar != null) {
                String id = dVar.getID();
                if (id != null && id.length() > 0) {
                    dVar.setStatusValue(3001, a());
                }
            }
        }

        public String a() {
            String str = "";
            Iterator it = this.b.keySet().iterator();
            while (true) {
                String str2 = str;
                if (!it.hasNext()) {
                    return "{" + str2 + "}";
                }
                str = (String) it.next();
                str = str2 + str + ":" + ((String) this.b.get(str)) + " ";
            }
        }
    }

    /* compiled from: TXCVideoPreprocessor */
    static class b {
        int a;
        int b;
        int c;
        int d;
        int e;
        int f;
        int g;
        int h;
        boolean i;
        boolean j;
        public int k = 5;
        public int l = 0;
        com.tencent.liteav.basic.c.a m = null;

        b() {
        }
    }

    /* compiled from: TXCVideoPreprocessor */
    private static class c {
        public boolean a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public boolean g;
        public int h;
        public int i;
        public int j;
        public int k;
        public int l;
        public com.tencent.liteav.basic.c.a m;

        private c() {
            this.g = false;
            this.k = 5;
            this.l = 0;
            this.m = null;
        }
    }

    /* compiled from: TXCVideoPreprocessor */
    public static class d {
        public Bitmap a;
        public float b;
        public float c;
        public float d;
        public int e;
        public int f;
        public int g;
    }

    public int willAddWatermark(int i, int i2, int i3) {
        boolean z = false;
        if (this.k == null) {
            return 0;
        }
        com.tencent.liteav.basic.e.c cVar = new com.tencent.liteav.basic.e.c();
        cVar.d = i2;
        cVar.e = i3;
        cVar.i = this.j != null ? this.j.j : 0;
        if (this.j != null) {
            z = this.j.g;
        }
        cVar.h = z;
        cVar.a = i;
        return this.k.a(cVar);
    }

    public void didProcessFrame(int i, int i2, int i3, long j) {
        boolean z = false;
        b();
        if (this.k != null) {
            com.tencent.liteav.basic.e.c cVar = new com.tencent.liteav.basic.e.c();
            cVar.d = i2;
            cVar.e = i3;
            cVar.i = this.j != null ? this.j.j : 0;
            if (this.j != null) {
                z = this.j.g;
            }
            cVar.h = z;
            cVar.a = i;
            this.k.a(cVar, j);
        }
    }

    public void didProcessFrame(byte[] bArr, int i, int i2, int i3, long j) {
        if (this.k != null) {
            this.k.a(bArr, i, i2, i3, j);
        }
    }

    private void b() {
        if (this.m != 0) {
            setStatusValue(3002, Long.valueOf(System.currentTimeMillis() - this.m));
        }
        this.n++;
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > this.o + 2000) {
            setStatusValue(3003, Double.valueOf((((double) this.n) * 1000.0d) / ((double) (currentTimeMillis - this.o))));
            this.n = 0;
            this.o = currentTimeMillis;
        }
    }

    public d(Context context, boolean z) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        TXCLog.i("TXCVideoPreprocessor", "TXCVideoPreprocessor version: VideoPreprocessor-v1.1");
        ConfigurationInfo deviceConfigurationInfo = activityManager.getDeviceConfigurationInfo();
        if (deviceConfigurationInfo != null) {
            TXCLog.i("TXCVideoPreprocessor", "opengl es version " + deviceConfigurationInfo.reqGlEsVersion);
            TXCLog.i("TXCVideoPreprocessor", "set GLContext " + z);
            if (deviceConfigurationInfo.reqGlEsVersion > 131072) {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_3");
                f.a(3);
            } else {
                TXCLog.i("TXCVideoPreprocessor", "This devices is OpenGlUtils.OPENGL_ES_2");
                f.a(2);
            }
        } else {
            TXCLog.e("TXCVideoPreprocessor", "getDeviceConfigurationInfo opengl Info failed!");
        }
        this.a = context;
        this.b = z;
        this.h = new b(this.a, this.b);
        a.a().a(context);
    }

    public void a(float[] fArr) {
        if (this.h != null) {
            this.h.a(fArr);
        }
    }

    public synchronized int a(int i, int i2, int i3, int i4, int i5, int i6) {
        a(i2, i3, g(i4), i5, i6);
        this.h.b(this.i);
        return this.h.a(i, i5);
    }

    public synchronized void a(com.tencent.liteav.basic.c.a aVar) {
        this.g = aVar;
    }

    public synchronized void a(int i, int i2) {
        this.d = i;
        this.e = i2;
    }

    public synchronized void a(int i) {
        this.f = i;
    }

    public synchronized void a(Bitmap bitmap, float f, float f2, float f3) {
        if (f < 0.0f || f2 < 0.0f || ((double) f3) < 0.0d) {
            TXCLog.e("TXCVideoPreprocessor", "WaterMark param is Error!");
        } else if (this.h != null) {
            this.h.a(bitmap, f, f2, f3);
        }
    }

    public synchronized void a(List<d> list) {
        if (this.h != null) {
            a.a().e();
            this.h.a((List) list);
        }
    }

    public synchronized void a(boolean z) {
        this.c = z;
    }

    public synchronized void a(e eVar) {
        if (this.h == null) {
            TXCLog.e("TXCVideoPreprocessor", "setListener mDrawer is null!");
        } else {
            this.h.a(eVar);
        }
    }

    private int g(int i) {
        switch (i) {
            case 1:
                return 90;
            case 2:
                return TXLiveConstants.RENDER_ROTATION_180;
            case 3:
                return 270;
            default:
                return i;
        }
    }

    private boolean a(int i, int i2, int i3, int i4, int i5) {
        if (this.j == null) {
            this.j = new c();
            this.n = 0;
            this.o = System.currentTimeMillis();
        }
        if (i != this.j.b || i2 != this.j.c || i3 != this.j.f || ((this.d > 0 && this.d != this.j.h) || ((this.e > 0 && this.e != this.j.i) || ((this.f > 0 && this.f != this.j.j) || ((this.g != null && ((this.g.c > 0 && (this.j.m == null || this.g.c != this.j.m.c)) || ((this.g.d > 0 && (this.j.m == null || this.g.d != this.j.m.d)) || ((this.g.a >= 0 && (this.j.m == null || this.g.a != this.j.m.a)) || (this.g.b >= 0 && (this.j.m == null || this.g.b != this.j.m.b)))))) || this.c != this.j.g))))) {
            TXCLog.i("TXCVideoPreprocessor", "Init sdk");
            TXCLog.i("TXCVideoPreprocessor", "Input widht " + i + " height " + i2);
            this.j.b = i;
            this.j.c = i2;
            if (this.g != null && this.g.a >= 0 && this.g.b >= 0 && this.g.c > 0 && this.g.d > 0) {
                int i6;
                TXCLog.i("TXCVideoPreprocessor", "set Crop Rect; init ");
                int i7 = i - this.g.a > this.g.c ? this.g.c : i - this.g.a;
                if (i2 - this.g.b > this.g.d) {
                    i6 = this.g.d;
                } else {
                    i6 = i2 - this.g.b;
                }
                this.g.c = i7;
                this.g.d = i6;
                i = this.g.c;
                i2 = this.g.d;
                this.j.m = this.g;
            }
            this.j.f = i3;
            this.j.a = this.b;
            this.j.k = i4;
            this.j.l = i5;
            if (true == this.l) {
                this.j.h = this.d;
                this.j.i = this.e;
            } else {
                this.j.h = 0;
                this.j.i = 0;
            }
            this.j.j = this.f;
            if (this.j.j <= 0) {
                this.j.j = 0;
            }
            if (this.j.h <= 0 || this.j.i <= 0) {
                if (90 == this.j.j || 270 == this.j.j) {
                    this.j.h = i2;
                    this.j.i = i;
                } else {
                    this.j.h = i;
                    this.j.i = i2;
                }
            }
            if (90 == this.j.j || 270 == this.j.j) {
                this.j.d = this.j.i;
                this.j.e = this.j.h;
            } else {
                this.j.d = this.j.h;
                this.j.e = this.j.i;
            }
            if (true != this.l) {
                this.j.h = this.d;
                this.j.i = this.e;
                if (this.j.h <= 0 || this.j.i <= 0) {
                    if (90 == this.j.j || 270 == this.j.j) {
                        this.j.h = i2;
                        this.j.i = i;
                    } else {
                        this.j.h = i;
                        this.j.i = i2;
                    }
                }
            }
            this.j.g = this.c;
            if (!a(this.j)) {
                TXCLog.e("TXCVideoPreprocessor", "init failed!");
                return false;
            }
        } else if (!(i4 == this.j.k && i5 == this.j.l)) {
            this.j.k = i4;
            this.i.k = i4;
            this.j.l = i5;
            this.i.l = i5;
            this.h.a(i5);
        }
        return true;
    }

    private boolean a(c cVar) {
        this.i.d = cVar.b;
        this.i.e = cVar.c;
        this.i.m = cVar.m;
        this.i.g = cVar.d;
        this.i.f = cVar.e;
        this.i.h = (cVar.f + 360) % 360;
        this.i.b = cVar.h;
        this.i.c = cVar.i;
        this.i.a = cVar.j;
        this.i.j = cVar.a;
        this.i.i = cVar.g;
        this.i.k = cVar.k;
        this.i.l = cVar.l;
        if (this.h == null) {
            this.h = new b(this.a, cVar.a);
        }
        return this.h.a(this.i);
    }

    public synchronized void a() {
        if (this.h != null) {
            this.h.a();
        }
        this.j = null;
    }

    public synchronized void b(int i) {
        if (this.h != null) {
            this.h.c(i);
        }
        this.p.a("beautyStyle", i);
    }

    public synchronized void c(int i) {
        if (i > 9) {
            TXCLog.e("TXCVideoPreprocessor", "Beauty value too large! set max value 9");
            i = 9;
        } else if (i < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Beauty < 0; set 0");
            i = 0;
        }
        if (this.h != null) {
            this.h.b(i);
        }
        this.p.a("beautyLevel", i);
    }

    public synchronized void d(int i) {
        if (i > 9) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
            i = 9;
        } else if (i < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            i = 0;
        }
        if (this.h != null) {
            this.h.d(i);
        }
        this.p.a("whiteLevel", i);
    }

    public synchronized void e(int i) {
        if (i > 9) {
            TXCLog.e("TXCVideoPreprocessor", "Ruddy value too large! set max value 9");
            i = 9;
        } else if (i < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Ruddy < 0; set 0");
            i = 0;
        }
        if (this.h != null) {
            this.h.f(i);
        }
        this.p.a("ruddyLevel", i);
    }

    public void f(int i) {
        if (i > 9) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness value too large! set max value 9");
            i = 9;
        } else if (i < 0) {
            TXCLog.e("TXCVideoPreprocessor", "Brightness < 0; set 0");
            i = 0;
        }
        if (this.h != null) {
            this.h.e(i);
        }
    }

    public synchronized void a(String str) {
        if (this.h != null) {
            this.h.a(str);
        }
    }

    public synchronized void b(boolean z) {
        if (this.h != null) {
            this.h.a(z);
        }
    }

    public synchronized void a(float f) {
        if (this.h != null) {
            this.h.a(f);
        }
    }

    public void a(float f, Bitmap bitmap, float f2, Bitmap bitmap2, float f3) {
        if (this.h != null) {
            this.h.a(f, bitmap, f2, bitmap2, f3);
        }
    }

    public synchronized void b(float f) {
        if (this.h != null) {
            this.h.b(f);
        }
    }

    public void setID(String str) {
        super.setID(str);
        setStatusValue(3001, this.p.a());
    }
}
