package com.baidu.mapapi.map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import anetwork.channel.util.RequestConstant;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.commonutils.a;
import com.baidu.platform.comapi.map.an;
import com.baidu.platform.comapi.map.e;
import com.baidu.platform.comapi.map.i;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comapi.map.l;
import java.io.File;

public final class MapView extends ViewGroup {
    private static final String a = MapView.class.getSimpleName();
    private static String b;
    private static final SparseArray<Integer> o = new SparseArray();
    private j c;
    private BaiduMap d;
    private ImageView e;
    private Bitmap f;
    private an g;
    private Point h;
    private Point i;
    private RelativeLayout j;
    private TextView k;
    private TextView l;
    private ImageView m;
    private Context n;
    private int p = LogoPosition.logoPostionleftBottom.ordinal();
    private boolean q = true;
    private boolean r = true;
    private float s;
    private l t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private int z;

    static {
        o.append(3, Integer.valueOf(2000000));
        o.append(4, Integer.valueOf(1000000));
        o.append(5, Integer.valueOf(500000));
        o.append(6, Integer.valueOf(200000));
        o.append(7, Integer.valueOf(100000));
        o.append(8, Integer.valueOf(50000));
        o.append(9, Integer.valueOf(25000));
        o.append(10, Integer.valueOf(20000));
        o.append(11, Integer.valueOf(10000));
        o.append(12, Integer.valueOf(m_AppUI.MSG_APP_GPS));
        o.append(13, Integer.valueOf(m_AppUI.MSG_APP_DATA_OK));
        o.append(14, Integer.valueOf(1000));
        o.append(15, Integer.valueOf(d_ResultType.SHORT_URL));
        o.append(16, Integer.valueOf(200));
        o.append(17, Integer.valueOf(100));
        o.append(18, Integer.valueOf(50));
        o.append(19, Integer.valueOf(20));
        o.append(20, Integer.valueOf(10));
        o.append(21, Integer.valueOf(5));
        o.append(22, Integer.valueOf(2));
    }

    public MapView(Context context) {
        super(context);
        a(context, null);
    }

    public MapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, null);
    }

    public MapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, null);
    }

    public MapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        a(context, baiduMapOptions);
    }

    private void a(Context context) {
        String str = "logo_h.png";
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi < 180) {
            str = "logo_l.png";
        }
        Bitmap a = a.a(str, context);
        Matrix matrix;
        if (densityDpi > 480) {
            matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            this.f = Bitmap.createBitmap(a, 0, 0, a.getWidth(), a.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.f = a;
        } else {
            matrix = new Matrix();
            matrix.postScale(1.5f, 1.5f);
            this.f = Bitmap.createBitmap(a, 0, 0, a.getWidth(), a.getHeight(), matrix, true);
        }
        if (this.f != null) {
            this.e = new ImageView(context);
            this.e.setImageBitmap(this.f);
            addView(this.e);
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions) {
        this.n = context;
        i.a();
        BMapManager.init();
        a(context, baiduMapOptions, b);
        this.d = new BaiduMap(this.c);
        a(context);
        b(context);
        if (!(baiduMapOptions == null || baiduMapOptions.h)) {
            this.g.setVisibility(4);
        }
        c(context);
        if (!(baiduMapOptions == null || baiduMapOptions.i)) {
            this.j.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.j == null)) {
            this.p = baiduMapOptions.j.ordinal();
        }
        if (!(baiduMapOptions == null || baiduMapOptions.l == null)) {
            this.i = baiduMapOptions.l;
        }
        if (baiduMapOptions != null && baiduMapOptions.k != null) {
            this.h = baiduMapOptions.k;
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        if (baiduMapOptions == null) {
            this.c = new j(context, null, str);
        } else {
            this.c = new j(context, baiduMapOptions.a(), str);
        }
        addView(this.c);
        this.t = new i(this);
        this.c.a().a(this.t);
    }

    private void a(View view) {
        LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new LayoutParams(-2, -2);
        }
        int i = layoutParams.width;
        i = i > 0 ? MeasureSpec.makeMeasureSpec(i, 1073741824) : MeasureSpec.makeMeasureSpec(0, 0);
        int i2 = layoutParams.height;
        view.measure(i, i2 > 0 ? MeasureSpec.makeMeasureSpec(i2, 1073741824) : MeasureSpec.makeMeasureSpec(0, 0));
    }

    private void b() {
        boolean z = false;
        float f = this.c.a().E().a;
        if (this.g.a()) {
            this.g.b(f > this.c.a().b);
            an anVar = this.g;
            if (f < this.c.a().a) {
                z = true;
            }
            anVar.a(z);
        }
    }

    private void b(Context context) {
        this.g = new an(context, false);
        if (this.g.a()) {
            this.g.b(new j(this));
            this.g.a(new k(this));
            addView(this.g);
        }
    }

    private void c(Context context) {
        this.j = new RelativeLayout(context);
        this.j.setLayoutParams(new LayoutParams(-2, -2));
        this.k = new TextView(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.k.setTextColor(Color.parseColor("#FFFFFF"));
        this.k.setTextSize(2, 11.0f);
        this.k.setTypeface(this.k.getTypeface(), 1);
        this.k.setLayoutParams(layoutParams);
        this.k.setId(Integer.MAX_VALUE);
        this.j.addView(this.k);
        this.l = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        this.l.setTextColor(Color.parseColor("#000000"));
        this.l.setTextSize(2, 11.0f);
        this.l.setLayoutParams(layoutParams);
        this.j.addView(this.l);
        this.m = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        layoutParams.addRule(3, this.k.getId());
        this.m.setLayoutParams(layoutParams);
        Bitmap a = a.a("icon_scale.9.png", context);
        byte[] ninePatchChunk = a.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.m.setBackgroundDrawable(new NinePatchDrawable(a, ninePatchChunk, new Rect(), null));
        this.j.addView(this.m);
        addView(this.j);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (new File(str).exists()) {
            b = str;
        } else {
            throw new RuntimeException("please check whether the customMapStylePath file exits");
        }
    }

    public static void setMapCustomEnable(boolean z) {
        i.a(z);
    }

    public void addView(View view, LayoutParams layoutParams) {
        if (layoutParams instanceof MapViewLayoutParams) {
            super.addView(view, layoutParams);
        }
    }

    public void cancelRenderMap() {
        this.c.a().t(false);
        this.c.a().N().clear();
    }

    public final LogoPosition getLogoPosition() {
        switch (this.p) {
            case 1:
                return LogoPosition.logoPostionleftTop;
            case 2:
                return LogoPosition.logoPostionCenterBottom;
            case 3:
                return LogoPosition.logoPostionCenterTop;
            case 4:
                return LogoPosition.logoPostionRightBottom;
            case 5:
                return LogoPosition.logoPostionRightTop;
            default:
                return LogoPosition.logoPostionleftBottom;
        }
    }

    public final BaiduMap getMap() {
        this.d.a = this;
        return this.d;
    }

    public final int getMapLevel() {
        return ((Integer) o.get((int) this.c.a().E().a)).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.y;
    }

    public int getScaleControlViewWidth() {
        return this.z;
    }

    public boolean handleMultiTouch(float f, float f2, float f3, float f4) {
        return this.c != null && this.c.a(f, f2, f3, f4);
    }

    public void handleTouchDown(float f, float f2) {
        if (this.c != null) {
            this.c.a(f, f2);
        }
    }

    public boolean handleTouchMove(float f, float f2) {
        return this.c != null && this.c.c(f, f2);
    }

    public boolean handleTouchUp(float f, float f2) {
        return this.c == null ? false : this.c.b(f, f2);
    }

    public boolean inRangeOfView(float f, float f2) {
        return this.c != null && this.c.d(f, f2);
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            b = bundle.getString("customMapPath");
            if (bundle == null) {
                a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.h != null) {
                this.h = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.i != null) {
                this.i = (Point) bundle.getParcelable("zoomPosition");
            }
            this.q = bundle.getBoolean("mZoomControlEnabled");
            this.r = bundle.getBoolean("mScaleControlEnabled");
            this.p = bundle.getInt("logoPosition");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        if (this.n != null) {
            this.c.b(this.n.hashCode());
        }
        if (!(this.f == null || this.f.isRecycled())) {
            this.f.recycle();
            this.f = null;
        }
        if (b != null) {
            b = null;
        }
        this.g.b();
        BMapManager.destroy();
        i.b();
        this.n = null;
    }

    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int childCount = getChildCount();
        a(this.e);
        if (((getWidth() - this.u) - this.v) - this.e.getMeasuredWidth() <= 0 || ((getHeight() - this.w) - this.x) - this.e.getMeasuredHeight() <= 0) {
            this.u = 0;
            this.v = 0;
            this.x = 0;
            this.w = 0;
            f = 1.0f;
            f2 = 1.0f;
        } else {
            f = ((float) ((getWidth() - this.u) - this.v)) / ((float) getWidth());
            f2 = ((float) ((getHeight() - this.w) - this.x)) / ((float) getHeight());
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            if (childAt != null) {
                int i6;
                int i7;
                int i8;
                int i9;
                if (childAt == this.c) {
                    this.c.layout(0, 0, getWidth(), getHeight());
                } else if (childAt == this.e) {
                    i6 = (int) (((float) this.u) + (5.0f * f));
                    i7 = (int) (((float) this.v) + (5.0f * f));
                    i8 = (int) (((float) this.w) + (5.0f * f2));
                    i9 = (int) (((float) this.x) + (5.0f * f2));
                    switch (this.p) {
                        case 1:
                            i9 = i8 + this.e.getMeasuredHeight();
                            i7 = this.e.getMeasuredWidth() + i6;
                            break;
                        case 2:
                            i9 = getHeight() - i9;
                            i8 = i9 - this.e.getMeasuredHeight();
                            i6 = (((getWidth() - this.e.getMeasuredWidth()) + this.u) - this.v) / 2;
                            i7 = (((getWidth() + this.e.getMeasuredWidth()) + this.u) - this.v) / 2;
                            break;
                        case 3:
                            i9 = i8 + this.e.getMeasuredHeight();
                            i6 = (((getWidth() - this.e.getMeasuredWidth()) + this.u) - this.v) / 2;
                            i7 = (((getWidth() + this.e.getMeasuredWidth()) + this.u) - this.v) / 2;
                            break;
                        case 4:
                            i9 = getHeight() - i9;
                            i8 = i9 - this.e.getMeasuredHeight();
                            i7 = getWidth() - i7;
                            i6 = i7 - this.e.getMeasuredWidth();
                            break;
                        case 5:
                            i9 = i8 + this.e.getMeasuredHeight();
                            i7 = getWidth() - i7;
                            i6 = i7 - this.e.getMeasuredWidth();
                            break;
                        default:
                            i9 = getHeight() - i9;
                            i7 = this.e.getMeasuredWidth() + i6;
                            i8 = i9 - this.e.getMeasuredHeight();
                            break;
                    }
                    this.e.layout(i6, i8, i7, i9);
                } else if (childAt == this.g) {
                    if (this.g.a()) {
                        a(this.g);
                        if (this.i == null) {
                            i6 = (int) ((((float) (getHeight() - 15)) * f2) + ((float) this.w));
                            i8 = (int) ((((float) (getWidth() - 15)) * f) + ((float) this.u));
                            i9 = i8 - this.g.getMeasuredWidth();
                            i7 = i6 - this.g.getMeasuredHeight();
                            if (this.p == 4) {
                                i6 -= this.e.getMeasuredHeight();
                                i7 -= this.e.getMeasuredHeight();
                            }
                            this.g.layout(i9, i7, i8, i6);
                        } else {
                            this.g.layout(this.i.x, this.i.y, this.i.x + this.g.getMeasuredWidth(), this.i.y + this.g.getMeasuredHeight());
                        }
                    }
                } else if (childAt == this.j) {
                    a(this.j);
                    if (this.h == null) {
                        i7 = (int) ((((float) this.x) + (5.0f * f2)) + 56.0f);
                        this.z = this.j.getMeasuredWidth();
                        this.y = this.j.getMeasuredHeight();
                        i6 = (int) (((float) this.u) + (5.0f * f));
                        i7 = (getHeight() - i7) - this.e.getMeasuredHeight();
                        this.j.layout(i6, i7, this.z + i6, this.y + i7);
                    } else {
                        this.j.layout(this.h.x, this.h.y, this.h.x + this.j.getMeasuredWidth(), this.h.y + this.j.getMeasuredHeight());
                    }
                } else {
                    LayoutParams layoutParams = childAt.getLayoutParams();
                    if (layoutParams == null) {
                        Log.e(RequestConstant.ENV_TEST, "lp == null");
                    }
                    if (layoutParams instanceof MapViewLayoutParams) {
                        Point point;
                        MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                        if (mapViewLayoutParams.c == ELayoutMode.absoluteMode) {
                            point = mapViewLayoutParams.b;
                        } else {
                            point = this.c.a().a(CoordUtil.ll2mc(mapViewLayoutParams.a));
                        }
                        a(childAt);
                        i9 = childAt.getMeasuredWidth();
                        int measuredHeight = childAt.getMeasuredHeight();
                        float f3 = mapViewLayoutParams.d;
                        int i10 = (int) (((float) point.x) - (f3 * ((float) i9)));
                        i7 = mapViewLayoutParams.f + ((int) (((float) point.y) - (mapViewLayoutParams.e * ((float) measuredHeight))));
                        childAt.layout(i10, i7, i10 + i9, i7 + measuredHeight);
                    }
                }
            }
        }
    }

    public final void onPause() {
        this.c.onPause();
    }

    public final void onResume() {
        this.c.onResume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.d != null) {
            bundle.putParcelable("mapstatus", this.d.getMapStatus());
            if (this.h != null) {
                bundle.putParcelable("scalePosition", this.h);
            }
            if (this.i != null) {
                bundle.putParcelable("zoomPosition", this.i);
            }
            bundle.putBoolean("mZoomControlEnabled", this.q);
            bundle.putBoolean("mScaleControlEnabled", this.r);
            bundle.putInt("logoPosition", this.p);
            bundle.putInt("paddingLeft", this.u);
            bundle.putInt("paddingTop", this.w);
            bundle.putInt("paddingRight", this.v);
            bundle.putInt("paddingBottom", this.x);
            bundle.putString("customMapPath", b);
        }
    }

    public void removeView(View view) {
        if (view != this.e) {
            super.removeView(view);
        }
    }

    public void renderMap() {
        e a = this.c.a();
        a.t(true);
        a.O();
    }

    public final void setLogoPosition(LogoPosition logoPosition) {
        if (logoPosition == null) {
            this.p = LogoPosition.logoPostionleftBottom.ordinal();
        }
        this.p = logoPosition.ordinal();
        requestLayout();
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.u = i;
        this.w = i2;
        this.v = i3;
        this.x = i4;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.h = point;
            requestLayout();
        }
    }

    public void setUpViewEventToMapView(MotionEvent motionEvent) {
        this.c.onTouchEvent(motionEvent);
    }

    public final void setZOrderMediaOverlay(boolean z) {
        if (this.c != null) {
            this.c.setZOrderMediaOverlay(z);
        }
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.i = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z) {
        this.j.setVisibility(z ? 0 : 8);
        this.r = z;
    }

    public void showZoomControls(boolean z) {
        if (this.g.a()) {
            this.g.setVisibility(z ? 0 : 8);
            this.q = z;
        }
    }
}
