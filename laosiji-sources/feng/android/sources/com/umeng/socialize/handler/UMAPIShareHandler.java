package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.editorpage.IEditor;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.analytics.AnalyticsReqeust;
import com.umeng.socialize.net.base.SocializeReseponse;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.INTER;
import java.util.Map;
import java.util.Stack;

public abstract class UMAPIShareHandler extends UMSSOHandler implements IEditor {
    private Stack<StatHolder> mStatStack = new Stack();

    private static class StatHolder {
        public ShareContent Content;
        private UMShareListener Listener;

        private StatHolder() {
        }

        /* synthetic */ StatHolder(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public abstract void authorizeCallBack(int i, int i2, Intent intent);

    public abstract void deleteAuth();

    public abstract SHARE_MEDIA getPlatform();

    public abstract String getUID();

    public void onCreate(Context context, Platform platform) {
        super.onCreate(context, platform);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i == getRequestCode()) {
            StatHolder statHolder;
            if (i2 == 1000) {
                if (!this.mStatStack.isEmpty()) {
                    statHolder = (StatHolder) this.mStatStack.pop();
                    if (statHolder != null) {
                        statHolder.Listener.onCancel(getPlatform());
                    }
                }
            } else if (intent == null || !intent.hasExtra(SocializeConstants.KEY_TEXT)) {
                authorizeCallBack(i, i2, intent);
            } else if (!this.mStatStack.empty()) {
                statHolder = (StatHolder) this.mStatStack.pop();
                final Bundle extras = intent.getExtras();
                if (i2 == -1) {
                    QueuedWork.runInBack(new Runnable() {
                        public void run() {
                            UMAPIShareHandler.this.sendShareRequest(UMAPIShareHandler.this.getResult(statHolder.Content, extras), statHolder.Listener);
                        }
                    }, true);
                } else if (statHolder.Listener != null) {
                    statHolder.Listener.onCancel(getPlatform());
                }
            }
        }
    }

    public boolean share(final ShareContent shareContent, final UMShareListener uMShareListener) {
        if (isAuthorize()) {
            doShare(shareContent, uMShareListener);
        } else {
            authorize(new UMAuthListener() {
                public void onStart(SHARE_MEDIA share_media) {
                    uMShareListener.onStart(share_media);
                }

                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    QueuedWork.runInBack(new Runnable() {
                        public void run() {
                            UMAPIShareHandler.this.doShare(shareContent, uMShareListener);
                        }
                    }, true);
                }

                public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                    uMShareListener.onError(share_media, th);
                }

                public void onCancel(SHARE_MEDIA share_media, int i) {
                    uMShareListener.onCancel(share_media);
                }
            });
        }
        return false;
    }

    protected void doShare(ShareContent shareContent, UMShareListener uMShareListener) {
        if (getShareConfig().isOpenShareEditActivity()) {
            StatHolder statHolder = new StatHolder();
            statHolder.Content = shareContent;
            statHolder.Listener = uMShareListener;
            this.mStatStack.push(statHolder);
            if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
                try {
                    Intent intent = new Intent((Context) this.mWeakAct.get(), Class.forName("com.umeng.socialize.editorpage.ShareActivity"));
                    intent.putExtras(getEditable(shareContent));
                    ((Activity) this.mWeakAct.get()).startActivityForResult(intent, getRequestCode());
                    return;
                } catch (Throwable e) {
                    sendShareRequest(shareContent, uMShareListener);
                    SLog.error(INTER.NULLJAR, e);
                    e.printStackTrace();
                    return;
                }
            }
            return;
        }
        sendShareRequest(shareContent, uMShareListener);
    }

    public void sendShareRequest(ShareContent shareContent, final UMShareListener uMShareListener) {
        final SHARE_MEDIA platform = getPlatform();
        String toLowerCase = platform.toString().toLowerCase();
        String uid = getUID();
        AnalyticsReqeust analyticsReqeust = new AnalyticsReqeust(getContext(), toLowerCase, shareContent.mText);
        analyticsReqeust.setMedia(shareContent.mMedia);
        analyticsReqeust.setmUsid(uid);
        analyticsReqeust.setReqType(0);
        final SocializeReseponse doShareByRequest = RestAPI.doShareByRequest(analyticsReqeust);
        if (doShareByRequest == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    uMShareListener.onError(platform, new Throwable(UmengErrorCode.ShareFailed.getMessage() + "response is null"));
                }
            });
        } else if (doShareByRequest.isOk()) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    uMShareListener.onResult(platform);
                }
            });
        } else {
            final ShareContent shareContent2 = shareContent;
            final UMShareListener uMShareListener2 = uMShareListener;
            final SocializeReseponse socializeReseponse = doShareByRequest;
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    if (doShareByRequest.mStCode == 5027) {
                        UMAPIShareHandler.this.deleteAuth();
                        UMAPIShareHandler.this.share(shareContent2, uMShareListener2);
                        return;
                    }
                    uMShareListener2.onError(platform, new Throwable(UmengErrorCode.ShareFailed.getMessage() + socializeReseponse.mMsg));
                }
            });
        }
    }
}
