package com.umeng.socialize.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager.LayoutParams;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.stub.StubApp;
import com.umeng.socialize.bean.PlatformName;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.ResContainer;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import java.lang.reflect.Method;

public abstract class BaseDialog extends Dialog {
    public final ResContainer R;
    public Activity mActivity;
    public View mContent;
    public Context mContext;
    public int mFlag = 0;
    public Handler mHandler = new Handler() {
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1 && BaseDialog.this.mProgressbar != null) {
                BaseDialog.this.mProgressbar.setVisibility(8);
            }
            if (message.what == 2) {
            }
        }
    };
    public SHARE_MEDIA mPlatform;
    public View mProgressbar;
    public Bundle mValues;
    public String mWaitUrl = "error";
    public WebView mWebView;
    public TextView titleMidTv;

    public abstract void setClient(WebView webView);

    public BaseDialog(Activity activity, SHARE_MEDIA share_media) {
        super(activity, ResContainer.get(activity).style("umeng_socialize_popup_dialog"));
        this.mContext = StubApp.getOrigApplicationContext(activity.getApplicationContext());
        this.R = ResContainer.get(this.mContext);
        this.mActivity = activity;
        this.mPlatform = share_media;
    }

    public void setWaitUrl(String str) {
        this.mWaitUrl = str;
    }

    public void initViews() {
        String str;
        setOwnerActivity(this.mActivity);
        LayoutInflater layoutInflater = (LayoutInflater) this.mActivity.getSystemService("layout_inflater");
        int layout = this.R.layout("umeng_socialize_oauth_dialog");
        int id = this.R.id("umeng_socialize_follow");
        this.mContent = layoutInflater.inflate(layout, null);
        final View findViewById = this.mContent.findViewById(id);
        findViewById.setVisibility(8);
        int id2 = this.R.id("progress_bar_parent");
        layout = this.R.id("umeng_back");
        int id3 = this.R.id("umeng_share_btn");
        int id4 = this.R.id("umeng_title");
        int id5 = this.R.id("umeng_socialize_titlebar");
        this.mProgressbar = this.mContent.findViewById(id2);
        this.mProgressbar.setVisibility(0);
        ((RelativeLayout) this.mContent.findViewById(layout)).setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                BaseDialog.this.dismiss();
            }
        });
        this.mContent.findViewById(id3).setVisibility(8);
        this.titleMidTv = (TextView) this.mContent.findViewById(id4);
        if (this.mPlatform.toString().equals("SINA")) {
            str = PlatformName.SINA;
        } else if (this.mPlatform.toString().equals("RENREN")) {
            str = PlatformName.RENREN;
        } else if (this.mPlatform.toString().equals("DOUBAN")) {
            str = PlatformName.DOUBAN;
        } else if (this.mPlatform.toString().equals("TENCENT")) {
            str = PlatformName.TENCENT;
        } else {
            str = null;
        }
        this.titleMidTv.setText("授权" + str);
        setUpWebView();
        final View findViewById2 = this.mContent.findViewById(id5);
        id4 = SocializeUtils.dip2Px(this.mContext, 200.0f);
        View anonymousClass3 = new FrameLayout(this.mContext) {
            protected void onSizeChanged(int i, int i2, int i3, int i4) {
                super.onSizeChanged(i, i2, i3, i4);
                if (!SocializeUtils.isFloatWindowStyle(BaseDialog.this.mContext)) {
                    a(findViewById, findViewById2, id4, i2);
                }
            }

            private void a(final View view, final View view2, int i, int i2) {
                if (view2.getVisibility() == 0 && i2 < i) {
                    BaseDialog.this.mHandler.post(new Runnable() {
                        public void run() {
                            view2.setVisibility(8);
                            if (view.getVisibility() == 0) {
                                view.setVisibility(8);
                            }
                            AnonymousClass3.this.requestLayout();
                        }
                    });
                } else if (view2.getVisibility() != 0 && i2 >= i) {
                    BaseDialog.this.mHandler.post(new Runnable() {
                        public void run() {
                            view2.setVisibility(0);
                            AnonymousClass3.this.requestLayout();
                        }
                    });
                }
            }
        };
        anonymousClass3.addView(this.mContent, -1, -1);
        setContentView(anonymousClass3);
        LayoutParams attributes = getWindow().getAttributes();
        if (SocializeUtils.isFloatWindowStyle(this.mContext)) {
            int[] floatWindowSize = SocializeUtils.getFloatWindowSize(this.mContext);
            attributes.width = floatWindowSize[0];
            attributes.height = floatWindowSize[1];
        } else {
            attributes.height = -1;
            attributes.width = -1;
        }
        attributes.gravity = 17;
    }

    public boolean setUpWebView() {
        this.mWebView = (WebView) this.mContent.findViewById(this.R.id("webView"));
        setClient(this.mWebView);
        this.mWebView.requestFocusFromTouch();
        this.mWebView.setVerticalScrollBarEnabled(false);
        this.mWebView.setHorizontalScrollBarEnabled(false);
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.getSettings().setCacheMode(2);
        this.mWebView.setBackgroundColor(-1);
        WebSettings settings = this.mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setPluginState(PluginState.ON);
        }
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setAllowFileAccess(true);
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        settings.setUseWideViewPort(true);
        if (VERSION.SDK_INT >= 8) {
            settings.setLoadWithOverviewMode(true);
            settings.setDatabaseEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setGeolocationEnabled(true);
            settings.setAppCacheEnabled(true);
        }
        if (VERSION.SDK_INT >= 11) {
            try {
                Method declaredMethod = WebSettings.class.getDeclaredMethod("setDisplayZoomControls", new Class[]{Boolean.TYPE});
                declaredMethod.setAccessible(true);
                declaredMethod.invoke(settings, new Object[]{Boolean.valueOf(false)});
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
        try {
            if (this.mPlatform == SHARE_MEDIA.RENREN) {
                CookieSyncManager.createInstance(this.mContext);
                CookieManager.getInstance().removeAllCookie();
            }
        } catch (Exception e2) {
        }
        if (VERSION.SDK_INT >= 11) {
            this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        } else {
            removeJavascriptInterface(this.mWebView);
        }
        return true;
    }

    public void removeJavascriptInterface(WebView webView) {
        if (VERSION.SDK_INT < 11) {
            try {
                webView.getClass().getDeclaredMethod("removeJavascriptInterface", new Class[0]).invoke("searchBoxJavaBridge_", new Object[0]);
            } catch (Exception e) {
            }
        }
    }

    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        return super.onKeyDown(i, keyEvent);
    }

    public void releaseWebView() {
        try {
            ((ViewGroup) this.mWebView.getParent()).removeView(this.mWebView);
        } catch (Exception e) {
        }
        try {
            this.mWebView.removeAllViews();
        } catch (Exception e2) {
        }
        this.mWebView = null;
    }
}
