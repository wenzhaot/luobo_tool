package com.umeng.qq.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.qq.tencent.IUiListener;
import com.umeng.qq.tencent.Tencent;
import com.umeng.qq.tencent.UiError;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.utils.UmengText;

public class UmengQZoneHandler extends UmengQBaseHandler {
    private UmengQQPreferences qqPreferences;

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        this.qqPreferences = new UmengQQPreferences(context, SHARE_MEDIA.QQ.toString());
    }

    public boolean share(ShareContent content, final UMShareListener listener) {
        UmengQZoneShareContent shareContent = new UmengQZoneShareContent(content);
        if (this.mShareConfig != null) {
            shareContent.setCompressListener(this.mShareConfig.getCompressListener());
        }
        if (listener != null) {
            this.mShareListener = listener;
        }
        if (this.mTencent == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    UmengQZoneHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QZONE, new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.tencentEmpty(Config.isUmengQQ.booleanValue())));
                }
            });
        } else {
            if (!isInstall()) {
                if (Config.isJumptoAppStore) {
                    Intent intent = new Intent("android.intent.action.VIEW");
                    intent.setData(Uri.parse(SocializeConstants.DOWN_URL_QQ));
                    ((Activity) this.mWeakAct.get()).startActivity(intent);
                }
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        UmengQZoneHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QZONE, new Throwable(UmengErrorCode.NotInstall.getMessage()));
                    }
                });
            }
            Bundle bundle = shareContent.getBundle(getShareConfig().getAppName());
            final String error = bundle.getString("error");
            if (!TextUtils.isEmpty(error)) {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        UmengQZoneHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QZONE, new Throwable(UmengErrorCode.ShareFailed.getMessage() + error));
                    }
                });
            } else if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                this.mTencent.shareToQzone((Activity) this.mWeakAct.get(), bundle, getmShareListener(this.mShareListener));
            }
        }
        return false;
    }

    public boolean isInstall() {
        if (this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing() || this.mTencent.isSupportSSOLogin((Activity) this.mWeakAct.get())) {
            return true;
        }
        return false;
    }

    public boolean isSupportAuth() {
        return false;
    }

    public int getRequestCode() {
        return 10104;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10104) {
            Tencent.onActivityResultData(requestCode, resultCode, data, getmShareListener(this.mShareListener));
        }
    }

    public IUiListener getmShareListener(final UMShareListener mShareListener) {
        return new IUiListener() {
            public void onComplete(Object o) {
                UmengQZoneHandler.this.getShareListener(mShareListener).onResult(SHARE_MEDIA.QZONE);
            }

            public void onError(UiError uiError) {
                UmengQZoneHandler.this.getShareListener(mShareListener).onError(SHARE_MEDIA.QZONE, new Throwable(UmengErrorCode.ShareFailed.getMessage() + uiError.errorMessage));
            }

            public void onCancel() {
                UmengQZoneHandler.this.getShareListener(mShareListener).onCancel(SHARE_MEDIA.QZONE);
            }
        };
    }
}
