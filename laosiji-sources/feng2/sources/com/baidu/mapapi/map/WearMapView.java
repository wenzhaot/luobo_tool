package com.baidu.mapapi.map;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnApplyWindowInsetsListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.mapapi.common.SysOSUtil;
import com.baidu.mapapi.map.MapViewLayoutParams.ELayoutMode;
import com.baidu.mapapi.model.CoordUtil;
import com.baidu.platform.comapi.map.an;
import com.baidu.platform.comapi.map.i;
import com.baidu.platform.comapi.map.j;
import com.baidu.platform.comapi.map.l;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

@TargetApi(20)
public class WearMapView extends ViewGroup implements OnApplyWindowInsetsListener {
    public static final int BT_INVIEW = 1;
    private static final String b = MapView.class.getSimpleName();
    private static String c;
    private static int q = 0;
    private static int r = 0;
    private static int s = 10;
    private static final SparseArray<Integer> v = new SparseArray();
    private int A;
    private int B;
    private int C;
    private int D;
    private int E;
    private int F;
    ScreenShape a = ScreenShape.ROUND;
    private j d;
    private BaiduMap e;
    private ImageView f;
    private Bitmap g;
    private an h;
    private boolean i = true;
    private Point j;
    private Point k;
    private RelativeLayout l;
    private SwipeDismissView m;
    public AnimationTask mTask;
    public Timer mTimer;
    public a mTimerHandler;
    private TextView n;
    private TextView o;
    private ImageView p;
    private boolean t = true;
    private Context u;
    private boolean w = true;
    private boolean x = true;
    private float y;
    private l z;

    public class AnimationTask extends TimerTask {
        public void run() {
            Message message = new Message();
            message.what = 1;
            WearMapView.this.mTimerHandler.sendMessage(message);
        }
    }

    public interface OnDismissCallback {
        void onDismiss();

        void onNotify();
    }

    public enum ScreenShape {
        ROUND,
        RECTANGLE,
        UNDETECTED
    }

    private class a extends Handler {
        private final WeakReference<Context> b;

        public a(Context context) {
            this.b = new WeakReference(context);
        }

        public void handleMessage(Message message) {
            if (((Context) this.b.get()) != null) {
                super.handleMessage(message);
                switch (message.what) {
                    case 1:
                        if (WearMapView.this.h != null) {
                            WearMapView.this.a(true);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    static {
        v.append(3, Integer.valueOf(2000000));
        v.append(4, Integer.valueOf(1000000));
        v.append(5, Integer.valueOf(500000));
        v.append(6, Integer.valueOf(200000));
        v.append(7, Integer.valueOf(100000));
        v.append(8, Integer.valueOf(50000));
        v.append(9, Integer.valueOf(25000));
        v.append(10, Integer.valueOf(20000));
        v.append(11, Integer.valueOf(10000));
        v.append(12, Integer.valueOf(m_AppUI.MSG_APP_GPS));
        v.append(13, Integer.valueOf(m_AppUI.MSG_APP_DATA_OK));
        v.append(14, Integer.valueOf(1000));
        v.append(15, Integer.valueOf(d_ResultType.SHORT_URL));
        v.append(16, Integer.valueOf(200));
        v.append(17, Integer.valueOf(100));
        v.append(18, Integer.valueOf(50));
        v.append(19, Integer.valueOf(20));
        v.append(20, Integer.valueOf(10));
        v.append(21, Integer.valueOf(5));
        v.append(22, Integer.valueOf(2));
    }

    public WearMapView(Context context) {
        super(context);
        a(context, null);
    }

    public WearMapView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, null);
    }

    public WearMapView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, null);
    }

    public WearMapView(Context context, BaiduMapOptions baiduMapOptions) {
        super(context);
        a(context, baiduMapOptions);
    }

    private int a(int i, int i2) {
        return i - ((int) Math.sqrt(Math.pow((double) i, 2.0d) - Math.pow((double) i2, 2.0d)));
    }

    private void a(int i) {
        if (this.d != null) {
            switch (i) {
                case 0:
                    this.d.onPause();
                    b();
                    return;
                case 1:
                    this.d.onResume();
                    c();
                    return;
                default:
                    return;
            }
        }
    }

    private static void a(Context context) {
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        q = point.x;
        r = point.y;
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions) {
        a(context);
        setOnApplyWindowInsetsListener(this);
        this.u = context;
        this.mTimerHandler = new a(context);
        this.mTimer = new Timer();
        if (!(this.mTimer == null || this.mTask == null)) {
            this.mTask.cancel();
        }
        this.mTask = new AnimationTask();
        this.mTimer.schedule(this.mTask, 5000);
        i.a();
        BMapManager.init();
        a(context, baiduMapOptions, c);
        this.e = new BaiduMap(this.d);
        this.d.a().q(false);
        this.d.a().p(false);
        c(context);
        d(context);
        b(context);
        if (!(baiduMapOptions == null || baiduMapOptions.h)) {
            this.h.setVisibility(4);
        }
        e(context);
        if (!(baiduMapOptions == null || baiduMapOptions.i)) {
            this.l.setVisibility(4);
        }
        if (!(baiduMapOptions == null || baiduMapOptions.l == null)) {
            this.k = baiduMapOptions.l;
        }
        if (baiduMapOptions != null && baiduMapOptions.k != null) {
            this.j = baiduMapOptions.k;
        }
    }

    private void a(Context context, BaiduMapOptions baiduMapOptions, String str) {
        if (baiduMapOptions == null) {
            this.d = new j(context, null, str);
        } else {
            this.d = new j(context, baiduMapOptions.a(), str);
        }
        addView(this.d);
        this.z = new u(this);
        this.d.a().a(this.z);
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

    private void a(View view, boolean z) {
        AnimatorSet animatorSet;
        ObjectAnimator ofFloat;
        ObjectAnimator ofFloat2;
        if (z) {
            animatorSet = new AnimatorSet();
            ofFloat = ObjectAnimator.ofFloat(view, "TranslationY", new float[]{0.0f, -50.0f});
            ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{1.0f, 0.0f});
            animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
            animatorSet.addListener(new x(this, view));
            animatorSet.setDuration(1200);
            animatorSet.start();
            return;
        }
        view.setVisibility(0);
        animatorSet = new AnimatorSet();
        ofFloat = ObjectAnimator.ofFloat(view, "TranslationY", new float[]{-50.0f, 0.0f});
        ofFloat2 = ObjectAnimator.ofFloat(view, "alpha", new float[]{0.0f, 1.0f});
        animatorSet.playTogether(new Animator[]{ofFloat, ofFloat2});
        animatorSet.setDuration(1200);
        animatorSet.start();
    }

    private void a(boolean z) {
        if (this.i) {
            a(this.h, z);
        }
    }

    private void b() {
        if (this.d != null && !this.t) {
            d();
            this.t = true;
        }
    }

    private void b(Context context) {
        this.m = new SwipeDismissView(context, this);
        LayoutParams layoutParams = new LayoutParams((int) ((context.getResources().getDisplayMetrics().density * 34.0f) + 0.5f), r);
        this.m.setBackgroundColor(Color.argb(0, 0, 0, 0));
        this.m.setLayoutParams(layoutParams);
        addView(this.m);
    }

    private void c() {
        if (this.d != null && this.t) {
            e();
            this.t = false;
        }
    }

    private void c(Context context) {
        String str = "logo_h.png";
        int densityDpi = SysOSUtil.getDensityDpi();
        if (densityDpi < 180) {
            str = "logo_l.png";
        }
        Bitmap a = com.baidu.platform.comapi.commonutils.a.a(str, context);
        Matrix matrix;
        if (densityDpi > 480) {
            matrix = new Matrix();
            matrix.postScale(2.0f, 2.0f);
            this.g = Bitmap.createBitmap(a, 0, 0, a.getWidth(), a.getHeight(), matrix, true);
        } else if (densityDpi <= 320 || densityDpi > 480) {
            this.g = a;
        } else {
            matrix = new Matrix();
            matrix.postScale(1.5f, 1.5f);
            this.g = Bitmap.createBitmap(a, 0, 0, a.getWidth(), a.getHeight(), matrix, true);
        }
        if (this.g != null) {
            this.f = new ImageView(context);
            this.f.setImageBitmap(this.g);
            addView(this.f);
        }
    }

    private void d() {
        if (this.d != null) {
            this.d.b();
        }
    }

    private void d(Context context) {
        this.h = new an(context, true);
        if (this.h.a()) {
            this.h.b(new v(this));
            this.h.a(new w(this));
            addView(this.h);
        }
    }

    private void e() {
        if (this.d != null) {
            this.d.c();
        }
    }

    private void e(Context context) {
        this.l = new RelativeLayout(context);
        this.l.setLayoutParams(new LayoutParams(-2, -2));
        this.n = new TextView(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(14);
        this.n.setTextColor(Color.parseColor("#FFFFFF"));
        this.n.setTextSize(2, 11.0f);
        this.n.setTypeface(this.n.getTypeface(), 1);
        this.n.setLayoutParams(layoutParams);
        this.n.setId(Integer.MAX_VALUE);
        this.l.addView(this.n);
        this.o = new TextView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        this.o.setTextColor(Color.parseColor("#000000"));
        this.o.setTextSize(2, 11.0f);
        this.o.setLayoutParams(layoutParams);
        this.l.addView(this.o);
        this.p = new ImageView(context);
        layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.width = -2;
        layoutParams.height = -2;
        layoutParams.addRule(14);
        layoutParams.addRule(3, this.n.getId());
        this.p.setLayoutParams(layoutParams);
        Bitmap a = com.baidu.platform.comapi.commonutils.a.a("icon_scale.9.png", context);
        byte[] ninePatchChunk = a.getNinePatchChunk();
        NinePatch.isNinePatchChunk(ninePatchChunk);
        this.p.setBackgroundDrawable(new NinePatchDrawable(a, ninePatchChunk, new Rect(), null));
        this.l.addView(this.p);
        addView(this.l);
    }

    public static void setCustomMapStylePath(String str) {
        if (str == null || str.length() == 0) {
            throw new RuntimeException("customMapStylePath String is illegal");
        } else if (new File(str).exists()) {
            c = str;
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

    public final BaiduMap getMap() {
        this.e.c = this;
        return this.e;
    }

    public final int getMapLevel() {
        return ((Integer) v.get((int) this.d.a().E().a)).intValue();
    }

    public int getScaleControlViewHeight() {
        return this.E;
    }

    public int getScaleControlViewWidth() {
        return this.F;
    }

    public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
        if (windowInsets.isRound()) {
            this.a = ScreenShape.ROUND;
        } else {
            this.a = ScreenShape.RECTANGLE;
        }
        return windowInsets;
    }

    public void onCreate(Context context, Bundle bundle) {
        if (bundle != null) {
            c = bundle.getString("customMapPath");
            if (bundle == null) {
                a(context, new BaiduMapOptions());
                return;
            }
            MapStatus mapStatus = (MapStatus) bundle.getParcelable("mapstatus");
            if (this.j != null) {
                this.j = (Point) bundle.getParcelable("scalePosition");
            }
            if (this.k != null) {
                this.k = (Point) bundle.getParcelable("zoomPosition");
            }
            this.w = bundle.getBoolean("mZoomControlEnabled");
            this.x = bundle.getBoolean("mScaleControlEnabled");
            setPadding(bundle.getInt("paddingLeft"), bundle.getInt("paddingTop"), bundle.getInt("paddingRight"), bundle.getInt("paddingBottom"));
            a(context, new BaiduMapOptions().mapStatus(mapStatus));
        }
    }

    public final void onDestroy() {
        if (this.u != null) {
            this.d.b(this.u.hashCode());
        }
        if (!(this.g == null || this.g.isRecycled())) {
            this.g.recycle();
            this.g = null;
        }
        this.h.b();
        BMapManager.destroy();
        i.b();
        if (this.mTask != null) {
            this.mTask.cancel();
        }
        this.u = null;
    }

    public final void onDismiss() {
        removeAllViews();
    }

    public final void onEnterAmbient(Bundle bundle) {
        a(0);
    }

    public void onExitAmbient() {
        a(1);
    }

    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                if (this.h.getVisibility() != 0) {
                    if (this.h.getVisibility() == 4) {
                        if (this.mTimer != null) {
                            if (this.mTask != null) {
                                this.mTask.cancel();
                            }
                            this.mTimer.cancel();
                            this.mTask = null;
                            this.mTimer = null;
                        }
                        a(false);
                        break;
                    }
                } else if (this.mTimer != null) {
                    if (this.mTask != null) {
                        this.mTimer.cancel();
                        this.mTask.cancel();
                    }
                    this.mTimer = null;
                    this.mTask = null;
                    break;
                }
                break;
            case 1:
                this.mTimer = new Timer();
                if (!(this.mTimer == null || this.mTask == null)) {
                    this.mTask.cancel();
                }
                this.mTask = new AnimationTask();
                this.mTimer.schedule(this.mTask, 5000);
                break;
        }
        return super.onInterceptTouchEvent(motionEvent);
    }

    @TargetApi(20)
    protected final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        float f;
        float f2;
        int childCount = getChildCount();
        a(this.f);
        if (((getWidth() - this.A) - this.B) - this.f.getMeasuredWidth() <= 0 || ((getHeight() - this.C) - this.D) - this.f.getMeasuredHeight() <= 0) {
            this.A = 0;
            this.B = 0;
            this.D = 0;
            this.C = 0;
            f = 1.0f;
            f2 = 1.0f;
        } else {
            f = ((float) ((getWidth() - this.A) - this.B)) / ((float) getWidth());
            f2 = ((float) ((getHeight() - this.C) - this.D)) / ((float) getHeight());
        }
        for (int i5 = 0; i5 < childCount; i5++) {
            View childAt = getChildAt(i5);
            int i6;
            int i7;
            int i8;
            int measuredWidth;
            if (childAt == this.d) {
                this.d.layout(0, 0, getWidth(), getHeight());
            } else if (childAt == this.f) {
                i6 = (int) (((float) this.D) + (12.0f * f2));
                i7 = 0;
                i8 = 0;
                if (this.a == ScreenShape.ROUND) {
                    a(this.h);
                    i7 = q / 2;
                    i8 = a(i7, this.h.getMeasuredWidth() / 2);
                    i7 = ((q / 2) - a(i7, i7 - i8)) + s;
                }
                i8 = (r - i8) - i6;
                i7 = q - i7;
                measuredWidth = i7 - this.f.getMeasuredWidth();
                this.f.layout(measuredWidth, i8 - this.f.getMeasuredHeight(), i7, i8);
            } else if (childAt == this.h) {
                if (this.h.a()) {
                    a(this.h);
                    if (this.k == null) {
                        i8 = 0;
                        if (this.a == ScreenShape.ROUND) {
                            i8 = a(r / 2, this.h.getMeasuredWidth() / 2);
                        }
                        i8 = (int) (((float) i8) + ((12.0f * f2) + ((float) this.C)));
                        i7 = (q - this.h.getMeasuredWidth()) / 2;
                        this.h.layout(i7, i8, this.h.getMeasuredWidth() + i7, this.h.getMeasuredHeight() + i8);
                    } else {
                        this.h.layout(this.k.x, this.k.y, this.k.x + this.h.getMeasuredWidth(), this.k.y + this.h.getMeasuredHeight());
                    }
                }
            } else if (childAt == this.l) {
                i7 = 0;
                i8 = 0;
                if (this.a == ScreenShape.ROUND) {
                    a(this.h);
                    i7 = q / 2;
                    i8 = a(i7, this.h.getMeasuredWidth() / 2);
                    i7 = ((q / 2) - a(i7, i7 - i8)) + s;
                }
                a(this.l);
                if (this.j == null) {
                    i6 = (int) (((float) this.D) + (12.0f * f2));
                    this.F = this.l.getMeasuredWidth();
                    this.E = this.l.getMeasuredHeight();
                    i7 = (int) (((float) i7) + (((float) this.A) + (5.0f * f)));
                    i8 = (r - i6) - i8;
                    measuredWidth = i8 - this.l.getMeasuredHeight();
                    this.l.layout(i7, measuredWidth, this.F + i7, i8);
                } else {
                    this.l.layout(this.j.x, this.j.y, this.j.x + this.l.getMeasuredWidth(), this.j.y + this.l.getMeasuredHeight());
                }
            } else if (childAt == this.m) {
                a(this.m);
                this.m.layout(0, 0, this.m.getMeasuredWidth(), r);
            } else {
                LayoutParams layoutParams = childAt.getLayoutParams();
                if (layoutParams instanceof MapViewLayoutParams) {
                    Point point;
                    MapViewLayoutParams mapViewLayoutParams = (MapViewLayoutParams) layoutParams;
                    if (mapViewLayoutParams.c == ELayoutMode.absoluteMode) {
                        point = mapViewLayoutParams.b;
                    } else {
                        point = this.d.a().a(CoordUtil.ll2mc(mapViewLayoutParams.a));
                    }
                    a(childAt);
                    measuredWidth = childAt.getMeasuredWidth();
                    int measuredHeight = childAt.getMeasuredHeight();
                    float f3 = mapViewLayoutParams.d;
                    int i9 = (int) (((float) point.x) - (f3 * ((float) measuredWidth)));
                    i8 = mapViewLayoutParams.f + ((int) (((float) point.y) - (mapViewLayoutParams.e * ((float) measuredHeight))));
                    childAt.layout(i9, i8, i9 + measuredWidth, i8 + measuredHeight);
                }
            }
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (bundle != null && this.e != null) {
            bundle.putParcelable("mapstatus", this.e.getMapStatus());
            if (this.j != null) {
                bundle.putParcelable("scalePosition", this.j);
            }
            if (this.k != null) {
                bundle.putParcelable("zoomPosition", this.k);
            }
            bundle.putBoolean("mZoomControlEnabled", this.w);
            bundle.putBoolean("mScaleControlEnabled", this.x);
            bundle.putInt("paddingLeft", this.A);
            bundle.putInt("paddingTop", this.C);
            bundle.putInt("paddingRight", this.B);
            bundle.putInt("paddingBottom", this.D);
            bundle.putString("customMapPath", c);
        }
    }

    public void removeView(View view) {
        if (view != this.f) {
            super.removeView(view);
        }
    }

    public void setOnDismissCallbackListener(OnDismissCallback onDismissCallback) {
        if (this.m != null) {
            this.m.setCallback(onDismissCallback);
        }
    }

    public void setPadding(int i, int i2, int i3, int i4) {
        this.A = i;
        this.C = i2;
        this.B = i3;
        this.D = i4;
    }

    public void setScaleControlPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.j = point;
            requestLayout();
        }
    }

    public void setShape(ScreenShape screenShape) {
        this.a = screenShape;
    }

    public void setViewAnimitionEnable(boolean z) {
        this.i = z;
    }

    public void setZoomControlsPosition(Point point) {
        if (point != null && point.x >= 0 && point.y >= 0 && point.x <= getWidth() && point.y <= getHeight()) {
            this.k = point;
            requestLayout();
        }
    }

    public void showScaleControl(boolean z) {
        this.l.setVisibility(z ? 0 : 8);
        this.x = z;
    }

    public void showZoomControls(boolean z) {
        if (this.h.a()) {
            this.h.setVisibility(z ? 0 : 8);
            this.w = z;
        }
    }
}
