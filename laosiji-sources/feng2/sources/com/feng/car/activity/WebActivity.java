package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.baidu.mapapi.UIMsg.l_ErrorNo;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.WebActivityBinding;
import com.feng.car.databinding.WebMoreDialogBinding;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.umeng.socialize.UMShareAPI;

public class WebActivity extends BaseActivity<WebActivityBinding> {
    public static final int WEB_NORMAL_TYPE = 1001;
    public static final int WEB_SHOW_SEND_BTN_TYPE = 1002;
    private boolean mIsError = false;
    private boolean mIsStopLoad = false;
    private Dialog mMoreDialog;
    private WebMoreDialogBinding mMoreDialogBinding;
    private UMShareAPI mShareAPI;
    private String mStrJson = "";
    private String mTitle = "";
    private int mType = 0;
    private String mUrl = "";

    static {
        StubApp.interface11(3291);
    }

    protected native void onCreate(Bundle bundle);

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    public int setBaseContentView() {
        return R.layout.web_activity;
    }

    public void initView() {
        String str;
        Intent intent = getIntent();
        if (intent != null) {
            this.mUrl = intent.getStringExtra("url");
            if (intent.hasExtra("title")) {
                this.mTitle = intent.getStringExtra("title");
            }
            this.mType = intent.getIntExtra("type", 1001);
            this.mStrJson = intent.getStringExtra("DATA_JSON");
        }
        if (TextUtils.isEmpty(this.mTitle)) {
            this.mTitle = "";
        }
        if (this.mTitle != null) {
            str = this.mTitle;
        } else {
            str = "";
        }
        initWebTitleBar(str, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (((WebActivityBinding) WebActivity.this.mBaseBinding).webView.canGoBack()) {
                    ((WebActivityBinding) WebActivity.this.mBaseBinding).webView.goBack();
                } else {
                    WebActivity.this.finish();
                }
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WebActivity.this.finish();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WebActivity.this.showMorwDialog();
            }
        });
        if (this.mUrl != null && !"".equals(this.mUrl)) {
            errorLog(this.mUrl);
            WebSettings settings = ((WebActivityBinding) this.mBaseBinding).webView.getSettings();
            settings.setUserAgentString(settings.getUserAgentString() + "[SPARK LEWDDRIVER +" + FengUtil.getAPPVerionCode(this) + "]");
            settings.setDefaultTextEncodingName("utf-8");
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);
            settings.setDisplayZoomControls(false);
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            settings.setJavaScriptEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setUseWideViewPort(true);
            settings.setLoadWithOverviewMode(true);
            settings.setCacheMode(-1);
            setWebViewClient();
            ((WebActivityBinding) this.mBaseBinding).webView.loadUrl(this.mUrl);
            ((WebActivityBinding) this.mBaseBinding).webView.setDownloadListener(new DownloadListener() {
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(Uri.parse(url));
                    WebActivity.this.startActivity(intent);
                    if (url.equals(WebActivity.this.mUrl)) {
                        WebActivity.this.finish();
                    }
                }
            });
            if (this.mType == 1002) {
                ((WebActivityBinding) this.mBaseBinding).llSendPost.setVisibility(0);
                ((WebActivityBinding) this.mBaseBinding).btSendPost.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (FengApplication.getInstance().isLoginUser()) {
                            Intent intent = new Intent(WebActivity.this, PostInitActivity.class);
                            if (!TextUtils.isEmpty(WebActivity.this.mStrJson)) {
                                intent.putExtra("send_post_form_circle", WebActivity.this.mStrJson);
                            }
                            WebActivity.this.startActivity(intent);
                            FengApplication.getInstance().upLoadLog(true, "新用户发帖时间 = " + LogGatherReadUtil.getInstance().getBeijingTime());
                            WebActivity.this.finish();
                            return;
                        }
                        WebActivity.this.startActivity(new Intent(WebActivity.this, LoginActivity.class));
                    }
                });
            }
        }
    }

    private void setWebViewClient() {
        ((WebActivityBinding) this.mBaseBinding).webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!TextUtils.isEmpty(url)) {
                    WebActivity.this.errorLog(url);
                    if (url.equals("lewddriver://back")) {
                        WebActivity.this.finish();
                    } else {
                        WebActivity.this.mIsStopLoad = true;
                        if (url.indexOf("http://") < 0 && url.indexOf("https://") < 0) {
                            return true;
                        }
                        view.stopLoading();
                        if (!(FengApplication.getInstance().handlerWeb302Skip(WebActivity.this, url) || url.indexOf("taobaos://") == 0 || url.indexOf("taobao://") == 0)) {
                            WebActivity.this.mIsStopLoad = false;
                            view.loadUrl(url);
                        }
                    }
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                if (errorCode == l_ErrorNo.NETWORK_ERROR_404 || errorCode == -6) {
                    WebActivity.this.mIsError = true;
                    if (!WebActivity.this.mIsStopLoad) {
                    }
                }
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
            }

            public void onPageFinished(WebView view, String url) {
            }

            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });
        ((WebActivityBinding) this.mBaseBinding).webView.setWebChromeClient(new WebChromeClient() {
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (title != null && WebActivity.this.mTitle.length() <= 0) {
                    if (WebActivity.this.mIsError) {
                        WebActivity.this.mRootBinding.titleLine.title.setText("");
                    } else if (WebActivity.this.mIsStopLoad || !(title.equals("404 Not Found") || title.equals("网页无法打开"))) {
                        WebActivity.this.mRootBinding.titleLine.title.setText(title);
                    } else {
                        WebActivity.this.mIsError = true;
                    }
                }
            }

            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    ((WebActivityBinding) WebActivity.this.mBaseBinding).myProgressBar.setVisibility(8);
                } else {
                    if (8 == ((WebActivityBinding) WebActivity.this.mBaseBinding).myProgressBar.getVisibility()) {
                        ((WebActivityBinding) WebActivity.this.mBaseBinding).myProgressBar.setVisibility(0);
                    }
                    ((WebActivityBinding) WebActivity.this.mBaseBinding).myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }
        });
    }

    public void onPause() {
        super.onPause();
        ((WebActivityBinding) this.mBaseBinding).webView.onPause();
    }

    public void onResume() {
        super.onResume();
        ((WebActivityBinding) this.mBaseBinding).webView.onResume();
    }

    protected void onDestroy() {
        super.onDestroy();
        ((WebActivityBinding) this.mBaseBinding).webView.destroy();
    }

    private void errorLog(String strUrl) {
    }

    private void showMorwDialog() {
        if (this.mMoreDialogBinding == null) {
            this.mMoreDialogBinding = WebMoreDialogBinding.inflate(LayoutInflater.from(this));
            this.mMoreDialogBinding.copyLink.setOnClickListener(this);
            this.mMoreDialogBinding.tvRefresh.setOnClickListener(this);
            this.mMoreDialogBinding.tvWebOpen.setOnClickListener(this);
            this.mMoreDialogBinding.backHome.setOnClickListener(this);
            this.mMoreDialogBinding.cancel.setOnClickListener(this);
        }
        if (this.mMoreDialog == null) {
            this.mMoreDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mMoreDialog.setCanceledOnTouchOutside(true);
            this.mMoreDialog.setCancelable(true);
            Window window = this.mMoreDialog.getWindow();
            window.setGravity(80);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mMoreDialogBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mMoreDialog.show();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4 || !((WebActivityBinding) this.mBaseBinding).webView.canGoBack()) {
            return super.onKeyDown(keyCode, event);
        }
        ((WebActivityBinding) this.mBaseBinding).webView.goBack();
        return true;
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.cancel /*2131624291*/:
                this.mMoreDialog.dismiss();
                return;
            case R.id.copyLink /*2131624828*/:
                FengUtil.copyText(this, ((WebActivityBinding) this.mBaseBinding).webView.getUrl(), "已复制");
                this.mMoreDialog.dismiss();
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
                return;
            case R.id.tv_refresh /*2131625577*/:
                ((WebActivityBinding) this.mBaseBinding).webView.reload();
                this.mMoreDialog.dismiss();
                return;
            case R.id.tv_web_open /*2131625578*/:
                this.mMoreDialog.dismiss();
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(Uri.parse(this.mUrl));
                startActivity(intent);
                return;
            default:
                return;
        }
    }
}
