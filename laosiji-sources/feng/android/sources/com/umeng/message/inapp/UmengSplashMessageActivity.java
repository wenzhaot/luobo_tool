package com.umeng.message.inapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.qiniu.android.storage.Configuration;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.proguard.j;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.Calendar;

public class UmengSplashMessageActivity extends Activity {
    private static final String a = UmengSplashMessageActivity.class.getName();
    private static int s = 2000;
    private static int t = 1000;
    private Activity b;
    private UImageLoadTask c;
    private ImageView d;
    private ImageView e;
    private TextView f;
    private boolean g = true;
    private boolean h = true;
    private a i;
    private a j;
    private UInAppMessage k;
    private d l;
    private boolean m = false;
    private boolean n = false;
    private boolean o = false;
    private boolean p = false;
    private long q;
    private long r;
    private ImageLoaderCallback u = new ImageLoaderCallback() {
        public void onLoadImage(Bitmap[] bitmapArr) {
            if (!UmengSplashMessageActivity.this.e()) {
                if (UmengSplashMessageActivity.this.i != null) {
                    UmengSplashMessageActivity.this.i.a();
                    UmengSplashMessageActivity.this.i = null;
                }
                try {
                    if (bitmapArr.length == 1) {
                        UmengSplashMessageActivity.this.d.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                UmengSplashMessageActivity.this.m = true;
                                if (!TextUtils.equals(UInAppMessage.NONE, UmengSplashMessageActivity.this.k.action_type)) {
                                    UmengSplashMessageActivity.this.r = UmengSplashMessageActivity.this.r + (SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.q);
                                    e.a(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, UmengSplashMessageActivity.this.k.msg_type, 1, 1, 0, 0, 0, (int) UmengSplashMessageActivity.this.r, 0);
                                    UmengSplashMessageActivity.this.f();
                                    UmengSplashMessageActivity.this.l.handleInAppMessage(UmengSplashMessageActivity.this.b, UmengSplashMessageActivity.this.k, 16);
                                    UmengSplashMessageActivity.this.finish();
                                }
                            }
                        });
                        UmengSplashMessageActivity.this.e.setVisibility(8);
                        UmengSplashMessageActivity.this.d.setImageBitmap(bitmapArr[0]);
                        UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.d);
                    }
                    if (bitmapArr.length == 2) {
                        UmengSplashMessageActivity.this.d.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                UmengSplashMessageActivity.this.n = true;
                                if (!TextUtils.equals(UInAppMessage.NONE, UmengSplashMessageActivity.this.k.action_type)) {
                                    UmengSplashMessageActivity.this.r = UmengSplashMessageActivity.this.r + (SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.q);
                                    e.a(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, UmengSplashMessageActivity.this.k.msg_type, 1, 0, 1, UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.o), 0, (int) UmengSplashMessageActivity.this.r, 0);
                                    UmengSplashMessageActivity.this.f();
                                    UmengSplashMessageActivity.this.l.handleInAppMessage(UmengSplashMessageActivity.this.b, UmengSplashMessageActivity.this.k, 16);
                                    UmengSplashMessageActivity.this.finish();
                                }
                            }
                        });
                        UmengSplashMessageActivity.this.e.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                UmengSplashMessageActivity.this.o = true;
                                if (!TextUtils.equals(UInAppMessage.NONE, UmengSplashMessageActivity.this.k.bottom_action_type)) {
                                    UmengSplashMessageActivity.this.r = UmengSplashMessageActivity.this.r + (SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.q);
                                    e.a(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, UmengSplashMessageActivity.this.k.msg_type, 1, 0, UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.n), 1, 0, (int) UmengSplashMessageActivity.this.r, 0);
                                    UmengSplashMessageActivity.this.f();
                                    UmengSplashMessageActivity.this.l.handleInAppMessage(UmengSplashMessageActivity.this.b, UmengSplashMessageActivity.this.k, 17);
                                    UmengSplashMessageActivity.this.finish();
                                }
                            }
                        });
                        UmengSplashMessageActivity.this.d.setImageBitmap(bitmapArr[0]);
                        UmengSplashMessageActivity.this.e.setImageBitmap(bitmapArr[1]);
                        UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.d);
                        UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.e);
                    }
                    UmengSplashMessageActivity.this.q = SystemClock.elapsedRealtime();
                    if (UmengSplashMessageActivity.this.k.display_button) {
                        UmengSplashMessageActivity.this.f.setVisibility(0);
                        UmengSplashMessageActivity.this.f.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) {
                                UmengSplashMessageActivity.this.r = UmengSplashMessageActivity.this.r + (SystemClock.elapsedRealtime() - UmengSplashMessageActivity.this.q);
                                e.a(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, UmengSplashMessageActivity.this.k.msg_type, 1, UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.m), UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.n), UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.o), 1, (int) UmengSplashMessageActivity.this.r, 0);
                                UmengSplashMessageActivity.this.f();
                                UmengSplashMessageActivity.this.finish();
                            }
                        });
                    } else {
                        UmengSplashMessageActivity.this.f.setVisibility(8);
                    }
                    InAppMessageManager.getInstance(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k);
                    InAppMessageManager.getInstance(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, 1);
                    InAppMessageManager.getInstance(UmengSplashMessageActivity.this.b).h();
                    UmengSplashMessageActivity.this.g = false;
                    UmengSplashMessageActivity.this.j = new a((long) (UmengSplashMessageActivity.this.k.display_time * 1000), (long) UmengSplashMessageActivity.t);
                    UmengSplashMessageActivity.this.j.b();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private IUmengInAppMessageCallback v = new IUmengInAppMessageCallback() {
        /* JADX WARNING: Removed duplicated region for block: B:22:0x00ae  */
        /* JADX WARNING: Removed duplicated region for block: B:5:0x0024 A:{SKIP} */
        public void onSplashMessage(com.umeng.message.entity.UInAppMessage r8) {
            /*
            r7 = this;
            r6 = 2;
            r5 = 0;
            r4 = 1;
            r1 = 0;
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.b;
            r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
            r2 = r0.e();
            r0 = android.text.TextUtils.isEmpty(r2);
            if (r0 != 0) goto L_0x00ab;
        L_0x0018:
            r0 = new com.umeng.message.entity.UInAppMessage;	 Catch:{ JSONException -> 0x00a7 }
            r3 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x00a7 }
            r3.<init>(r2);	 Catch:{ JSONException -> 0x00a7 }
            r0.<init>(r3);	 Catch:{ JSONException -> 0x00a7 }
        L_0x0022:
            if (r8 == 0) goto L_0x00ae;
        L_0x0024:
            if (r0 == 0) goto L_0x004e;
        L_0x0026:
            r1 = r8.msg_id;
            r2 = r0.msg_id;
            r1 = r1.equals(r2);
            if (r1 != 0) goto L_0x004e;
        L_0x0030:
            r1 = new java.io.File;
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.b;
            r0 = r0.msg_id;
            r0 = com.umeng.message.proguard.h.d(r2, r0);
            r1.<init>(r0);
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.b;
            r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
            r0.a(r1);
        L_0x004e:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0.k = r8;
        L_0x0053:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.k;
            r0 = r0.show_type;
            if (r0 != r4) goto L_0x007a;
        L_0x005d:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.g();
            if (r0 != 0) goto L_0x007a;
        L_0x0065:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.b;
            r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = r1.k;
            r1 = r1.msg_id;
            r0.a(r1, r5);
        L_0x007a:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.b;
            r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = r1.k;
            r0 = r0.b(r1);
            if (r0 == 0) goto L_0x00a6;
        L_0x0090:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.b;
            r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = r1.k;
            r0 = r0.c(r1);
            if (r0 != 0) goto L_0x00b6;
        L_0x00a6:
            return;
        L_0x00a7:
            r0 = move-exception;
            r0.printStackTrace();
        L_0x00ab:
            r0 = r1;
            goto L_0x0022;
        L_0x00ae:
            if (r0 == 0) goto L_0x00a6;
        L_0x00b0:
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1.k = r0;
            goto L_0x0053;
        L_0x00b6:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.k;
            r0 = r0.msg_type;
            if (r0 != 0) goto L_0x010a;
        L_0x00c0:
            r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.a;
            r1 = new java.lang.String[r4];
            r2 = "SPLASH_A";
            r1[r5] = r2;
            com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r6, r1);
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = new com.umeng.message.inapp.UImageLoadTask;
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.b;
            r3 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r3 = r3.k;
            r1.<init>(r2, r3);
            r0.c = r1;
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.c;
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = r1.u;
            r0.a(r1);
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.c;
            r1 = new java.lang.String[r4];
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.k;
            r2 = r2.image_url;
            r1[r5] = r2;
            r0.execute(r1);
        L_0x010a:
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.k;
            r0 = r0.msg_type;
            if (r0 != r4) goto L_0x00a6;
        L_0x0114:
            r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.a;
            r1 = new java.lang.String[r4];
            r2 = "SPLASH_B";
            r1[r5] = r2;
            com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r6, r1);
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = new com.umeng.message.inapp.UImageLoadTask;
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.b;
            r3 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r3 = r3.k;
            r1.<init>(r2, r3);
            r0.c = r1;
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.c;
            r1 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r1 = r1.u;
            r0.a(r1);
            r0 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r0 = r0.c;
            r1 = new java.lang.String[r6];
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.k;
            r2 = r2.image_url;
            r1[r5] = r2;
            r2 = com.umeng.message.inapp.UmengSplashMessageActivity.this;
            r2 = r2.k;
            r2 = r2.bottom_image_url;
            r1[r4] = r2;
            r0.execute(r1);
            goto L_0x00a6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.UmengSplashMessageActivity.2.onSplashMessage(com.umeng.message.entity.UInAppMessage):void");
        }

        public void onCardMessage(UInAppMessage uInAppMessage) {
        }
    };

    class a extends c {
        a(long j, long j2) {
            super(j, j2);
        }

        public void a(long j) {
            if (!UmengSplashMessageActivity.this.g) {
                UmengSplashMessageActivity.this.f.setVisibility(0);
                UmengSplashMessageActivity.this.f.setText(((int) Math.ceil((((double) j) * 1.0d) / ((double) UmengSplashMessageActivity.t))) + " " + UmengSplashMessageActivity.this.k.display_name);
            }
        }

        public void e() {
            if (!UmengSplashMessageActivity.this.e() || !UmengSplashMessageActivity.this.g) {
                if (!UmengSplashMessageActivity.this.g) {
                    e.a(UmengSplashMessageActivity.this.b).a(UmengSplashMessageActivity.this.k.msg_id, UmengSplashMessageActivity.this.k.msg_type, 1, UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.m), UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.n), UmengSplashMessageActivity.this.a(UmengSplashMessageActivity.this.o), 0, UmengSplashMessageActivity.this.k.display_time * 1000, 0);
                }
                UmengSplashMessageActivity.this.f();
                UmengSplashMessageActivity.this.finish();
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.b = this;
        if ((getIntent().getFlags() & Configuration.BLOCK_SIZE) > 0) {
            finish();
        } else if (!onCustomPretreatment()) {
            setRequestedOrientation(1);
            setContentView(c());
            d();
            this.l = new d();
            this.i = new a((long) s, (long) t);
            this.i.b();
        }
    }

    public final void onCreate(Bundle bundle, PersistableBundle persistableBundle) {
        super.onCreate(bundle, persistableBundle);
    }

    private View c() {
        View frameLayout = new FrameLayout(this.b);
        frameLayout.setLayoutParams(new LayoutParams(-1, -1));
        View linearLayout = new LinearLayout(this.b);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout.setOrientation(1);
        ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 13.0f);
        this.d = new ImageView(this.b);
        this.d.setLayoutParams(layoutParams);
        this.d.setScaleType(ScaleType.FIT_XY);
        linearLayout.addView(this.d);
        layoutParams = new LinearLayout.LayoutParams(-1, 0, 3.0f);
        this.e = new ImageView(this.b);
        this.e.setLayoutParams(layoutParams);
        this.e.setScaleType(ScaleType.FIT_XY);
        linearLayout.addView(this.e);
        frameLayout.addView(linearLayout);
        ViewGroup.LayoutParams layoutParams2 = new LayoutParams(-2, -2);
        layoutParams2.gravity = 5;
        layoutParams2.rightMargin = j.a(this.b, 30.0f);
        layoutParams2.topMargin = j.a(this.b, 20.0f);
        this.f = new TextView(this.b);
        this.f.setLayoutParams(layoutParams2);
        int a = j.a(this.b, 6.0f);
        int i = a / 3;
        this.f.setPadding(a, i, a, i);
        this.f.setTextSize(14.0f);
        this.f.setBackgroundColor(Color.parseColor("#80000000"));
        this.f.setTextColor(-1);
        this.f.setVisibility(8);
        frameLayout.addView(this.f);
        return frameLayout;
    }

    private void d() {
        if (InAppMessageManager.a) {
            e.a((Context) this).a(this.v);
        } else if (System.currentTimeMillis() - InAppMessageManager.getInstance(this.b).d() > ((long) InAppMessageManager.b)) {
            e.a((Context) this).a(this.v);
        } else {
            this.v.onSplashMessage(null);
        }
    }

    protected final void onStart() {
        super.onStart();
    }

    protected void onResume() {
        super.onResume();
        if (this.i != null) {
            this.i.d();
        }
        if (this.j != null) {
            this.q = SystemClock.elapsedRealtime();
            this.j.d();
        }
    }

    protected final void onPause() {
        super.onPause();
        if (this.i != null) {
            this.i.c();
        }
        if (this.j != null) {
            this.r += SystemClock.elapsedRealtime() - this.q;
            this.j.c();
        }
    }

    public final void onBackPressed() {
    }

    private synchronized boolean e() {
        boolean z;
        z = this.p;
        this.p = true;
        return z;
    }

    private synchronized void f() {
        if (this.h) {
            this.h = false;
            Intent intent = new Intent();
            intent.setClassName(this.b, InAppMessageManager.getInstance(this).a());
            intent.setFlags(CommonNetImpl.FLAG_SHARE);
            this.b.startActivity(intent);
        }
    }

    private boolean g() {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(InAppMessageManager.getInstance(this.b).i());
        Calendar instance2 = Calendar.getInstance();
        if (instance.get(6) == instance2.get(6) && instance.get(1) == instance2.get(1)) {
            return true;
        }
        return false;
    }

    public boolean onCustomPretreatment() {
        return false;
    }

    private void a(View view) {
        Animation alphaAnimation = new AlphaAnimation(0.3f, 1.0f);
        alphaAnimation.setDuration(500);
        view.startAnimation(alphaAnimation);
    }

    protected final void onDestroy() {
        if (this.i != null) {
            this.i.a();
        }
        if (this.j != null) {
            this.j.a();
        }
        if (this.c != null) {
            this.c.a(null);
        }
        this.p = false;
        this.m = false;
        this.n = false;
        this.o = false;
        super.onDestroy();
    }

    private int a(boolean z) {
        return z ? 1 : 0;
    }
}
