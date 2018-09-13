package com.umeng.qq.tencent;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.ContextUtil;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URLDecoder;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthAgent extends BaseApi {
    private IUiListener c;
    private String d;
    private WeakReference<Activity> e;

    private class b implements IUiListener {
        IUiListener a;

        private abstract class a implements OnClickListener {
            Dialog d;

            a(Dialog var2) {
                this.d = var2;
            }
        }

        public b(IUiListener var2) {
            this.a = var2;
        }

        public void onComplete(Object var1) {
            if (var1 != null) {
                JSONObject var2 = (JSONObject) var1;
                if (var2 != null) {
                    boolean var3 = false;
                    String var4 = "";
                    try {
                        var3 = var2.getInt("sendinstall") == 1;
                        var4 = var2.getString("installwording");
                    } catch (JSONException e) {
                    }
                    var4 = URLDecoder.decode(var4);
                    if (var3 && !TextUtils.isEmpty(var4)) {
                        a(var4, this.a, var1);
                    } else if (this.a != null) {
                        this.a.onComplete(var1);
                    }
                }
            }
        }

        private void a(String var1, IUiListener var2, Object var3) {
            Activity var4 = (Activity) AuthAgent.this.e.get();
            if (var4 != null) {
                Dialog var5 = new Dialog(var4);
                var5.requestWindowFeature(1);
                PackageManager var6 = var4.getPackageManager();
                PackageInfo var7 = null;
                Drawable var8 = null;
                try {
                    var7 = var6.getPackageInfo(var4.getPackageName(), 0);
                } catch (NameNotFoundException var14) {
                    var14.printStackTrace();
                }
                if (var7 != null) {
                    var8 = var7.applicationInfo.loadIcon(var6);
                }
                final IUiListener iUiListener = var2;
                final Object obj = var3;
                a var9 = new a(var5) {
                    public void onClick(View var1) {
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                iUiListener = var2;
                obj = var3;
                a var10 = new a(var5) {
                    public void onClick(View var1) {
                        if (this.d != null && this.d.isShowing()) {
                            this.d.dismiss();
                        }
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                };
                ColorDrawable var11 = new ColorDrawable();
                var11.setAlpha(0);
                var5.getWindow().setBackgroundDrawable(var11);
                var5.setContentView(a(var4, var8, var1, var9, var10));
                iUiListener = var2;
                obj = var3;
                var5.setOnCancelListener(new OnCancelListener() {
                    public void onCancel(DialogInterface var1) {
                        if (iUiListener != null) {
                            iUiListener.onComplete(obj);
                        }
                    }
                });
                if (var4 != null && !var4.isFinishing()) {
                    var5.show();
                }
            }
        }

        private Drawable a(String var1, Context var2) {
            Drawable var4 = null;
            try {
                InputStream var5 = StubApp.getOrigApplicationContext(var2.getApplicationContext()).getAssets().open(var1);
                if (var5 == null) {
                    return null;
                }
                Object var42;
                if (var1.endsWith(".9.png")) {
                    Bitmap var6 = null;
                    try {
                        var6 = BitmapFactory.decodeStream(var5);
                    } catch (OutOfMemoryError var10) {
                        var10.printStackTrace();
                    }
                    if (var6 == null) {
                        return null;
                    }
                    byte[] var7 = var6.getNinePatchChunk();
                    boolean var8 = NinePatch.isNinePatchChunk(var7);
                    var42 = new NinePatchDrawable(var6, var7, new Rect(), (String) null);
                } else {
                    var4 = Drawable.createFromStream(var5, var1);
                    var5.close();
                }
                Object obj = var42;
                return (Drawable) obj;
            } catch (IOException var11) {
                var11.printStackTrace();
                Drawable obj2 = var4;
            }
        }

        private View a(Context var1, Drawable var2, String var3, OnClickListener var4, OnClickListener var5) {
            DisplayMetrics var6 = new DisplayMetrics();
            ((WindowManager) var1.getSystemService("window")).getDefaultDisplay().getMetrics(var6);
            float var8 = var6.density;
            RelativeLayout relativeLayout = new RelativeLayout(var1);
            ImageView var10 = new ImageView(var1);
            var10.setImageDrawable(var2);
            var10.setScaleType(ScaleType.FIT_XY);
            var10.setId(1);
            int var14 = (int) (18.0f * var8);
            int var15 = (int) (6.0f * var8);
            int var16 = (int) (18.0f * var8);
            LayoutParams var17 = new LayoutParams((int) (60.0f * var8), (int) (60.0f * var8));
            var17.addRule(9);
            var17.setMargins(0, var14, var15, var16);
            relativeLayout.addView(var10, var17);
            TextView var18 = new TextView(var1);
            var18.setText(var3);
            var18.setTextSize(14.0f);
            var18.setGravity(3);
            var18.setIncludeFontPadding(false);
            var18.setPadding(0, 0, 0, 0);
            var18.setLines(2);
            var18.setId(5);
            var18.setMinWidth((int) (185.0f * var8));
            LayoutParams var19 = new LayoutParams(-2, -2);
            var19.addRule(1, 1);
            var19.addRule(6, 1);
            var19.setMargins(0, 0, (int) (5.0f * var8), 0);
            relativeLayout.addView(var18, var19);
            View var20 = new View(var1);
            var20.setBackgroundColor(Color.rgb(214, 214, 214));
            var20.setId(3);
            LayoutParams var21 = new LayoutParams(-2, 2);
            var21.addRule(3, 1);
            var21.addRule(5, 1);
            var21.addRule(7, 5);
            var21.setMargins(0, 0, (byte) 0, (int) (12.0f * var8));
            relativeLayout.addView(var20, var21);
            LinearLayout var22 = new LinearLayout(var1);
            ViewGroup.LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(5, 1);
            layoutParams.addRule(7, 5);
            layoutParams.addRule(3, 3);
            View button = new Button(var1);
            button.setText("跳过");
            button.setBackgroundDrawable(a("buttonNegt.png", var1));
            button.setTextColor(Color.rgb(36, 97, 131));
            button.setTextSize(20.0f);
            button.setOnClickListener(var5);
            button.setId(4);
            layoutParams = new LinearLayout.LayoutParams(0, (int) (45.0f * var8));
            layoutParams.rightMargin = (int) (14.0f * var8);
            layoutParams.leftMargin = (int) (4.0f * var8);
            layoutParams.weight = 1.0f;
            var22.addView(button, layoutParams);
            button = new Button(var1);
            button.setText("确定");
            button.setTextSize(20.0f);
            button.setTextColor(Color.rgb(255, 255, 255));
            button.setBackgroundDrawable(a("buttonPost.png", var1));
            button.setOnClickListener(var4);
            layoutParams = new LinearLayout.LayoutParams(0, (int) (45.0f * var8));
            layoutParams.weight = 1.0f;
            layoutParams.rightMargin = (int) (4.0f * var8);
            var22.addView(button, layoutParams);
            relativeLayout.addView(var22, layoutParams);
            layoutParams = new FrameLayout.LayoutParams((int) (279.0f * var8), (int) (163.0f * var8));
            relativeLayout.setPadding((int) (14.0f * var8), 0, (int) (12.0f * var8), (int) (12.0f * var8));
            relativeLayout.setLayoutParams(layoutParams);
            relativeLayout.setBackgroundColor(Color.rgb(247, 251, 247));
            Drawable paintDrawable = new PaintDrawable(Color.rgb(247, 251, 247));
            paintDrawable.setCornerRadius(5.0f * var8);
            relativeLayout.setBackgroundDrawable(paintDrawable);
            return relativeLayout;
        }

        public void onError(UiError var1) {
            if (this.a != null) {
                this.a.onError(var1);
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel();
            }
        }
    }

    public AuthAgent(QQToken var1) {
        super(var1);
    }

    public int doLogin(Activity var1, String var2, IUiListener var3, boolean var4) {
        this.d = var2;
        this.e = new WeakReference(var1);
        this.c = var3;
        if (a(var1, var4)) {
            return 1;
        }
        this.c = new b(this.c);
        return a(var4, this.c);
    }

    private int a(boolean var1, IUiListener var2) {
        CookieSyncManager.createInstance(ContextUtil.getContext());
        return 2;
    }

    private boolean a(Activity var1, boolean var3) {
        Intent var4 = b("com.tencent.open.agent.AgentActivity");
        if (var4 != null) {
            Bundle var5 = a();
            if (var3) {
                var5.putString("isadd", PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
            }
            var5.putString("scope", this.d);
            var5.putString("client_id", this.b.getAppId());
            if (isOEM) {
                var5.putString(CommonNetImpl.PF, "desktop_m_qq-" + installChannel + "-" + "android" + "-" + registerChannel + "-" + businessId);
            } else {
                var5.putString(CommonNetImpl.PF, "openmobile_android");
            }
            var5.putString("need_pay", PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
            var5.putString("oauth_app_name", Wifig.a(ContextUtil.getContext()));
            var4.putExtra("key_action", "action_login");
            var4.putExtra("key_params", var5);
            if (a(var4)) {
                this.c = new b(this.c);
                UIListenerManager.getInstance().setListenerWithRequestcode(11101, this.c);
                a(var1, var4, 11101);
                return true;
            }
        }
        return false;
    }
}
