package com.baidu.platform.comapi.map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import com.baidu.mapapi.common.EnvironmentUtilities;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.baidu.platform.comapi.map.m.a;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressLint({"NewApi"})
public class af extends TextureView implements OnDoubleTapListener, OnGestureListener, SurfaceTextureListener, a {
    public static int a;
    public static int b;
    private GestureDetector c;
    private Handler d;
    private m e = null;
    private e f;

    public af(Context context, ac acVar, String str) {
        super(context);
        a(context, acVar, str);
    }

    private void a(Context context, ac acVar, String str) {
        setSurfaceTextureListener(this);
        if (context == null) {
            throw new RuntimeException("when you create an mapview, the context can not be null");
        }
        this.c = new GestureDetector(context, this);
        EnvironmentUtilities.initAppDirectory(context);
        if (this.f == null) {
            this.f = new e(context, str);
        }
        this.f.a(context.hashCode());
        this.f.a();
        this.f.a(acVar);
        e();
        this.f.a(this.d);
        this.f.f();
    }

    private void e() {
        this.d = new ag(this);
    }

    public int a() {
        return this.f == null ? 0 : MapRenderer.nativeRender(this.f.h);
    }

    public void a(int i) {
        synchronized (this.f) {
            for (l f : this.f.f) {
                f.f();
            }
            if (this.f != null) {
                this.f.b(this.d);
                this.f.b(i);
                this.f = null;
            }
            this.d.removeCallbacksAndMessages(null);
        }
    }

    public void a(String str, Rect rect) {
        if (this.f != null && this.f.g != null) {
            if (rect != null) {
                int i = rect.left;
                int i2 = b < rect.bottom ? 0 : b - rect.bottom;
                int width = rect.width();
                int height = rect.height();
                if (i >= 0 && i2 >= 0 && width > 0 && height > 0) {
                    if (width > a) {
                        width = Math.abs(rect.width()) - (rect.right - a);
                    }
                    if (height > b) {
                        height = Math.abs(rect.height()) - (rect.bottom - b);
                    }
                    if (i > SysOSUtil.getScreenSizeX() || i2 > SysOSUtil.getScreenSizeY()) {
                        this.f.g.a(str, null);
                        if (this.e != null) {
                            this.e.a();
                            return;
                        }
                        return;
                    }
                    a = width;
                    b = height;
                    Bundle bundle = new Bundle();
                    bundle.putInt("x", i);
                    bundle.putInt("y", i2);
                    bundle.putInt("width", width);
                    bundle.putInt("height", height);
                    this.f.g.a(str, bundle);
                    if (this.e != null) {
                        this.e.a();
                        return;
                    }
                    return;
                }
                return;
            }
            this.f.g.a(str, null);
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    public e b() {
        return this.f;
    }

    public void c() {
        if (this.f != null && this.f.g != null) {
            for (l d : this.f.f) {
                d.d();
            }
            this.f.g.g();
            this.f.g.d();
            this.f.g.n();
            if (this.e != null) {
                this.e.a();
            }
        }
    }

    public void d() {
        if (this.f != null && this.f.g != null) {
            this.f.g.c();
            synchronized (this.f) {
                this.f.g.c();
                if (this.e != null) {
                    this.e.b();
                }
            }
        }
    }

    public boolean onDoubleTap(MotionEvent motionEvent) {
        if (this.f == null || this.f.g == null || !this.f.i) {
            return true;
        }
        GeoPoint b = this.f.b((int) motionEvent.getX(), (int) motionEvent.getY());
        if (b == null) {
            return false;
        }
        for (l b2 : this.f.f) {
            b2.b(b);
        }
        if (!this.f.e) {
            return false;
        }
        ae E = this.f.E();
        E.a += 1.0f;
        E.d = b.getLongitudeE6();
        E.e = b.getLatitudeE6();
        BaiduMap.mapStatusReason |= 1;
        this.f.a(E, (int) GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION);
        e eVar = this.f;
        e.k = System.currentTimeMillis();
        return true;
    }

    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        if (this.f == null || this.f.g == null || !this.f.i) {
            return true;
        }
        if (!this.f.d) {
            return false;
        }
        float sqrt = (float) Math.sqrt((double) ((f * f) + (f2 * f2)));
        if (sqrt <= 500.0f) {
            return false;
        }
        BaiduMap.mapStatusReason |= 1;
        this.f.A();
        this.f.a(34, (int) (sqrt * 0.6f), (((int) motionEvent2.getY()) << 16) | ((int) motionEvent2.getX()));
        this.f.M();
        return true;
    }

    public void onLongPress(MotionEvent motionEvent) {
        if (this.f != null && this.f.g != null && this.f.i) {
            String a = this.f.g.a(-1, (int) motionEvent.getX(), (int) motionEvent.getY(), this.f.j);
            if (a == null || a.equals("")) {
                for (l c : this.f.f) {
                    c.c(this.f.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
                return;
            }
            for (l c2 : this.f.f) {
                if (c2.b(a)) {
                    this.f.n = true;
                } else {
                    c2.c(this.f.b((int) motionEvent.getX(), (int) motionEvent.getY()));
                }
            }
        }
    }

    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
        return false;
    }

    public void onShowPress(MotionEvent motionEvent) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x005f  */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x005f  */
    public boolean onSingleTapConfirmed(android.view.MotionEvent r8) {
        /*
        r7 = this;
        r5 = 1;
        r0 = r7.f;
        if (r0 == 0) goto L_0x0011;
    L_0x0005:
        r0 = r7.f;
        r0 = r0.g;
        if (r0 == 0) goto L_0x0011;
    L_0x000b:
        r0 = r7.f;
        r0 = r0.i;
        if (r0 != 0) goto L_0x0012;
    L_0x0011:
        return r5;
    L_0x0012:
        r0 = r7.f;
        r0 = r0.g;
        r1 = -1;
        r2 = r8.getX();
        r2 = (int) r2;
        r3 = r8.getY();
        r3 = (int) r3;
        r4 = r7.f;
        r4 = r4.j;
        r2 = r0.a(r1, r2, r3, r4);
        r1 = 0;
        if (r2 == 0) goto L_0x0078;
    L_0x002c:
        r0 = "";
        r0 = r2.equals(r0);
        if (r0 != 0) goto L_0x0078;
    L_0x0035:
        r0 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x006f }
        r0.<init>(r2);	 Catch:{ JSONException -> 0x006f }
        r1 = "px";
        r2 = r8.getX();	 Catch:{ JSONException -> 0x00a0 }
        r2 = (int) r2;	 Catch:{ JSONException -> 0x00a0 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00a0 }
        r1 = "py";
        r2 = r8.getY();	 Catch:{ JSONException -> 0x00a0 }
        r2 = (int) r2;	 Catch:{ JSONException -> 0x00a0 }
        r0.put(r1, r2);	 Catch:{ JSONException -> 0x00a0 }
        r1 = r0;
    L_0x0051:
        r0 = r7.f;
        r0 = r0.f;
        r2 = r0.iterator();
    L_0x0059:
        r0 = r2.hasNext();
        if (r0 == 0) goto L_0x0011;
    L_0x005f:
        r0 = r2.next();
        r0 = (com.baidu.platform.comapi.map.l) r0;
        if (r1 == 0) goto L_0x0059;
    L_0x0067:
        r3 = r1.toString();
        r0.a(r3);
        goto L_0x0059;
    L_0x006f:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x0073:
        r1.printStackTrace();
        r1 = r0;
        goto L_0x0051;
    L_0x0078:
        r0 = r7.f;
        r0 = r0.f;
        r1 = r0.iterator();
    L_0x0080:
        r0 = r1.hasNext();
        if (r0 == 0) goto L_0x0011;
    L_0x0086:
        r0 = r1.next();
        r0 = (com.baidu.platform.comapi.map.l) r0;
        r2 = r7.f;
        r3 = r8.getX();
        r3 = (int) r3;
        r4 = r8.getY();
        r4 = (int) r4;
        r2 = r2.b(r3, r4);
        r0.a(r2);
        goto L_0x0080;
    L_0x00a0:
        r1 = move-exception;
        goto L_0x0073;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.map.af.onSingleTapConfirmed(android.view.MotionEvent):boolean");
    }

    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f != null) {
            this.e = new m(surfaceTexture, this, new AtomicBoolean(true), this);
            this.e.start();
            a = i;
            b = i2;
            ae E = this.f.E();
            if (E != null) {
                if (E.f == 0 || E.f == -1 || E.f == (E.j.left - E.j.right) / 2) {
                    E.f = -1;
                }
                if (E.g == 0 || E.g == -1 || E.g == (E.j.bottom - E.j.top) / 2) {
                    E.g = -1;
                }
                E.j.left = 0;
                E.j.top = 0;
                E.j.bottom = i2;
                E.j.right = i;
                this.f.a(E);
                this.f.a(a, b);
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.e != null) {
            this.e.c();
            this.e = null;
        }
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f != null) {
            a = i;
            b = i2;
            this.f.a(a, b);
            MapRenderer.nativeResize(this.f.h, i, i2);
        }
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.f == null || this.f.g == null) {
            return true;
        }
        super.onTouchEvent(motionEvent);
        for (l a : this.f.f) {
            a.a(motionEvent);
        }
        return this.c.onTouchEvent(motionEvent) ? true : this.f.a(motionEvent);
    }
}
