package com.umeng.socialize.sina.webview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.handler.a;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.f;
import com.umeng.socialize.net.g;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.sina.params.ShareRequestParam;
import com.umeng.socialize.sina.params.WeiboParameters;
import com.umeng.socialize.sina.util.Utility;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.SINA;
import com.umeng.socialize.view.BaseDialog;

public class ShareDialog extends BaseDialog {
    private boolean isAutoClose = false;
    private UMShareListener listener;
    private ShareRequestParam param;
    private a sinaPreferences = null;

    class ShareWeiboWebViewClient extends WebViewClient {
        private Activity mAct;
        private ShareRequestParam mShareRequestParam;

        public ShareWeiboWebViewClient(Activity activity, ShareRequestParam shareRequestParam) {
            this.mAct = activity;
            this.mShareRequestParam = shareRequestParam;
        }

        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            super.onReceivedError(webView, i, str, str2);
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (!str.startsWith("sinaweibo://browser/close")) {
                return super.shouldOverrideUrlLoading(webView, str);
            }
            Bundle parseUri = SocializeNetUtils.parseUri(str);
            CharSequence string = parseUri.getString("code");
            final String string2 = parseUri.getString(SocializeProtocolConstants.PROTOCOL_KEY_MSG);
            if (ShareDialog.this.sinaPreferences != null) {
                ShareDialog.this.sinaPreferences.a(parseUri).g();
            }
            ShareDialog.this.isAutoClose = true;
            if (TextUtils.isEmpty(string)) {
                ShareDialog.this.listener.onCancel(ShareDialog.this.mPlatform);
                return true;
            } else if (PushConstants.PUSH_TYPE_NOTIFY.equals(string)) {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        ShareDialog.this.listener.onResult(ShareDialog.this.mPlatform);
                        SocializeUtils.safeCloseDialog(ShareDialog.this);
                    }
                });
                return true;
            } else {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        ShareDialog.this.listener.onError(ShareDialog.this.mPlatform, new Throwable(UmengErrorCode.ShareFailed.getMessage() + string2));
                    }
                });
                return true;
            }
        }
    }

    public ShareDialog(Activity activity, SHARE_MEDIA share_media, UMShareListener uMShareListener, ShareRequestParam shareRequestParam) {
        super(activity, share_media);
        this.param = shareRequestParam;
        this.listener = uMShareListener;
        initViews();
        this.titleMidTv.setText(shareRequestParam.getSpecifyTitle());
        this.mProgressbar.setVisibility(8);
        this.sinaPreferences = new a(activity, SHARE_MEDIA.SINA.toString());
    }

    private void startShare() {
        final ShareRequestParam shareRequestParam = this.param;
        if (shareRequestParam.hasImage()) {
            QueuedWork.runInBack(new Runnable() {
                public void run() {
                    WeiboParameters weiboParameters = new WeiboParameters(shareRequestParam.getAppKey());
                    String appKey = shareRequestParam.getAppKey();
                    String aid = Utility.getAid(ShareDialog.this.mActivity, appKey);
                    String token = shareRequestParam.getToken();
                    WeiboParameters buildUploadPicParam = shareRequestParam.buildUploadPicParam(weiboParameters);
                    URequest fVar = new f(aid, token, appKey);
                    for (String str : buildUploadPicParam.keySet()) {
                        fVar.addStringParams(str, buildUploadPicParam.get(str).toString());
                    }
                    g gVar = (g) new SocializeClient().execute(fVar);
                    if (gVar != null) {
                        appKey = shareRequestParam.buildUrl(gVar.b);
                        if (gVar == null || gVar.a != 1 || TextUtils.isEmpty(gVar.b)) {
                            QueuedWork.runInMain(new Runnable() {
                                public void run() {
                                    ShareDialog.this.listener.onError(ShareDialog.this.mPlatform, new Throwable(UmengErrorCode.ShareFailed.getMessage() + SINA.SINA_UPLOAD_ERROR));
                                    SocializeUtils.safeCloseDialog(ShareDialog.this);
                                }
                            });
                            return;
                        } else {
                            QueuedWork.runInMain(new Runnable() {
                                public void run() {
                                    if (ShareDialog.this.mWebView != null && appKey != null) {
                                        ShareDialog.this.mWebView.loadUrl(appKey);
                                    }
                                }
                            });
                            return;
                        }
                    }
                    QueuedWork.runInMain(new Runnable() {
                        public void run() {
                            ShareDialog.this.listener.onError(ShareDialog.this.mPlatform, new Throwable(UmengErrorCode.ShareFailed.getMessage() + SINA.SINA_UPLOAD_ERROR));
                            SocializeUtils.safeCloseDialog(ShareDialog.this);
                        }
                    });
                }
            }, true);
        } else if (this.mWebView != null) {
            this.mWebView.loadUrl(this.param.getUrl());
        }
    }

    public void dismiss() {
        super.dismiss();
        if (!this.isAutoClose) {
            this.listener.onCancel(this.mPlatform);
        }
        releaseWebView();
    }

    public void removeJavascriptInterface(WebView webView) {
        if (VERSION.SDK_INT < 11) {
            try {
                webView.getClass().getDeclaredMethod("removeJavascriptInterface", new Class[0]).invoke("searchBoxJavaBridge_", new Object[0]);
            } catch (Exception e) {
            }
        }
    }

    public void setClient(WebView webView) {
        webView.setWebViewClient(new ShareWeiboWebViewClient(this.mActivity, this.param));
        webView.setWebChromeClient(new WebChromeClient());
    }

    public boolean setUpWebView() {
        boolean upWebView = super.setUpWebView();
        if (VERSION.SDK_INT >= 11) {
            this.mWebView.removeJavascriptInterface("searchBoxJavaBridge_");
        } else {
            removeJavascriptInterface(this.mWebView);
        }
        this.mWebView.getSettings().setUserAgentString(Utility.generateUA(this.mActivity));
        return upWebView;
    }

    public void show() {
        super.show();
        startShare();
    }
}
