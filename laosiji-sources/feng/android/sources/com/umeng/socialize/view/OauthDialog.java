package com.umeng.socialize.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.stub.StubApp;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.URLBuilder;
import com.umeng.socialize.utils.UmengText.NET;
import java.lang.ref.WeakReference;
import org.android.agoo.common.AgooConstants;

public class OauthDialog extends BaseDialog {
    private static final String BASE_URL = "https://log.umsns.com/";
    private static final String TAG = "OauthDialog";
    private static String mRedirectUri = "error";
    private a mListener;

    static class a {
        private UMAuthListener a = null;
        private SHARE_MEDIA b;
        private int c;

        public a(UMAuthListener uMAuthListener, SHARE_MEDIA share_media) {
            this.a = uMAuthListener;
            this.b = share_media;
        }

        public void a(Exception exception) {
            if (this.a != null) {
                this.a.onError(this.b, this.c, exception);
            }
        }

        public void a(Bundle bundle) {
            if (this.a != null) {
                this.a.onComplete(this.b, this.c, SocializeUtils.bundleTomap(bundle));
            }
        }

        public void onCancel() {
            if (this.a != null) {
                this.a.onCancel(this.b, this.c);
            }
        }
    }

    private static class b extends WebChromeClient {
        private WeakReference<OauthDialog> a;

        private b(OauthDialog oauthDialog) {
            this.a = new WeakReference(oauthDialog);
        }

        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
            OauthDialog oauthDialog = this.a == null ? null : (OauthDialog) this.a.get();
            if (oauthDialog == null) {
                return;
            }
            if (i < 90) {
                oauthDialog.mProgressbar.setVisibility(0);
            } else {
                oauthDialog.mHandler.sendEmptyMessage(1);
            }
        }
    }

    private static class c extends WebViewClient {
        private WeakReference<OauthDialog> a;

        private c(OauthDialog oauthDialog) {
            this.a = new WeakReference(oauthDialog);
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            OauthDialog oauthDialog = this.a == null ? null : (OauthDialog) this.a.get();
            if (oauthDialog != null) {
                Context origApplicationContext = StubApp.getOrigApplicationContext(oauthDialog.mContext.getApplicationContext());
                if (DeviceConfig.isNetworkAvailable(origApplicationContext)) {
                    if (str.contains("?ud_get=")) {
                        str = oauthDialog.decrypt(str);
                    }
                    if (str.contains(oauthDialog.mWaitUrl)) {
                        a(str);
                    }
                } else {
                    Toast.makeText(origApplicationContext, NET.NET_INAVALIBLE, 0).show();
                    return true;
                }
            }
            return super.shouldOverrideUrlLoading(webView, str);
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            Dialog dialog;
            if (this.a == null) {
                dialog = null;
            } else {
                OauthDialog dialog2 = (OauthDialog) this.a.get();
            }
            if (dialog2 != null) {
                View view = dialog2.mProgressbar;
                if (view.getVisibility() == 0) {
                    view.setVisibility(8);
                }
            }
            super.onReceivedError(webView, i, str, str2);
            if (dialog2 != null) {
                SocializeUtils.safeCloseDialog(dialog2);
            }
        }

        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.cancel();
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            OauthDialog oauthDialog;
            if (this.a == null) {
                oauthDialog = null;
            } else {
                oauthDialog = (OauthDialog) this.a.get();
            }
            if (oauthDialog != null) {
                String str2 = "";
                if (str.contains("?ud_get=")) {
                    str2 = oauthDialog.decrypt(str);
                }
                if (str2.contains("access_key") && str2.contains("access_secret")) {
                    if (str.contains(oauthDialog.mWaitUrl)) {
                        a(str);
                        return;
                    }
                    return;
                } else if (str.startsWith(OauthDialog.mRedirectUri)) {
                    b(str);
                }
            }
            super.onPageStarted(webView, str, bitmap);
        }

        public void onPageFinished(WebView webView, String str) {
            OauthDialog oauthDialog = this.a == null ? null : (OauthDialog) this.a.get();
            if (oauthDialog != null) {
                oauthDialog.mHandler.sendEmptyMessage(1);
                super.onPageFinished(webView, str);
                if (oauthDialog.mFlag == 0 && str.contains(oauthDialog.mWaitUrl)) {
                    a(str);
                }
            }
        }

        private void a(String str) {
            Dialog dialog;
            if (this.a == null) {
                dialog = null;
            } else {
                OauthDialog dialog2 = (OauthDialog) this.a.get();
            }
            if (dialog2 != null) {
                dialog2.mFlag = 1;
                dialog2.mValues = SocializeUtils.parseUrl(str);
                if (dialog2.isShowing()) {
                    SocializeUtils.safeCloseDialog(dialog2);
                }
            }
        }

        private void b(String str) {
            Dialog dialog;
            if (this.a == null) {
                dialog = null;
            } else {
                OauthDialog dialog2 = (OauthDialog) this.a.get();
            }
            if (dialog2 != null) {
                dialog2.mFlag = 1;
                dialog2.mValues = SocializeNetUtils.parseUrl(str);
                if (dialog2.isShowing()) {
                    SocializeUtils.safeCloseDialog(dialog2);
                }
            }
        }
    }

    public void setmRedirectUri(String str) {
        mRedirectUri = str;
    }

    public OauthDialog(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        super(activity, share_media);
        this.mListener = new a(uMAuthListener, share_media);
        initViews();
    }

    private String getUrl(SHARE_MEDIA share_media) {
        URLBuilder uRLBuilder = new URLBuilder(this.mContext);
        uRLBuilder.setHost("https://log.umsns.com/").setPath("share/auth/").setAppkey(SocializeUtils.getAppkey(this.mContext)).setEntityKey(Config.EntityKey).withMedia(share_media).withOpId(AgooConstants.ACK_REMOVE_PACKAGE).withSessionId(Config.SessionId).withUID(UMUtils.getUMId(this.mContext));
        return uRLBuilder.toEncript();
    }

    public void setClient(WebView webView) {
        webView.setWebViewClient(getAdapterWebViewClient());
        this.mWebView.setWebChromeClient(new b());
    }

    private WebViewClient getAdapterWebViewClient() {
        return new c();
    }

    private String decrypt(String str) {
        try {
            String[] split = str.split("ud_get=");
            return split[0] + split[1];
        } catch (Throwable e) {
            SLog.error(e);
            return str;
        }
    }

    public void show() {
        super.show();
        this.mValues = null;
        if (this.mPlatform == SHARE_MEDIA.SINA) {
            this.mWebView.loadUrl(this.mWaitUrl);
            return;
        }
        this.mWebView.loadUrl(getUrl(this.mPlatform));
    }

    public void dismiss() {
        if (this.mValues != null) {
            CharSequence string = this.mValues.getString("uid");
            String string2 = this.mValues.getString("error_code");
            Object string3 = this.mValues.getString("error_description");
            if (this.mPlatform == SHARE_MEDIA.SINA && !TextUtils.isEmpty(string3)) {
                this.mListener.a(new SocializeException(UmengErrorCode.AuthorizeFailed.getMessage() + "errorcode:" + string2 + " message:" + string3));
            } else if (TextUtils.isEmpty(string)) {
                this.mListener.a(new SocializeException(UmengErrorCode.AuthorizeFailed.getMessage() + "unfetch usid..."));
            } else {
                this.mValues.putString("accessToken", this.mValues.getString("access_key"));
                this.mValues.putString("expiration", this.mValues.getString("expires_in"));
                this.mListener.a(this.mValues);
            }
        } else {
            this.mListener.onCancel();
        }
        super.dismiss();
        releaseWebView();
    }
}
