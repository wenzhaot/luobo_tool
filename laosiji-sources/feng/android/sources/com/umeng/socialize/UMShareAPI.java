package com.umeng.socialize;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Process;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork.DialogThread;
import com.umeng.socialize.common.QueuedWork.UMAsyncTask;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.net.ActionBarRequest;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.net.analytics.SocialAnalytics;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.uploadlog.UMLog;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeSpUtils;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.CHECK;
import com.umeng.socialize.utils.UrlUtil;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

public class UMShareAPI {
    private static UMShareAPI a = null;
    private com.umeng.socialize.a.a b;
    private UMShareConfig c = new UMShareConfig();

    private static class a extends UMAsyncTask<Void> {
        private Context a;
        private boolean b = false;
        private boolean c = false;

        public a(Context context) {
            this.a = context;
            this.b = SocializeUtils.isToday(SocializeSpUtils.getTime(context));
            this.c = SocializeUtils.isHasDplusCache();
        }

        /* renamed from: a */
        protected Void doInBackground() {
            boolean c = c();
            SLog.E(CHECK.SDKVERSION + "6.9.2");
            if (!this.b) {
                RestAPI.queryShareId(new ActionBarRequest(this.a, c));
            }
            if (!this.b) {
                SocializeSpUtils.putTime(this.a);
                DplusApi.uploadDAU(ContextUtil.getContext());
                SocialAnalytics.dauStats(this.a, true);
            } else if (this.c) {
                DplusApi.uploadDAU(ContextUtil.getContext());
                SocialAnalytics.dauStats(this.a, true);
            }
            return null;
        }

        private boolean c() {
            return this.a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).getBoolean("newinstall", false);
        }

        public void b() {
            Editor edit = this.a.getSharedPreferences(SocializeConstants.SOCIAL_PREFERENCE_NAME, 0).edit();
            edit.putBoolean("newinstall", true);
            edit.commit();
        }
    }

    private UMShareAPI(Context context) {
        ContextUtil.setContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        this.b = new com.umeng.socialize.a.a(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        if (a(context) != null && a(context).equals(ContextUtil.getPackageName())) {
            new a(StubApp.getOrigApplicationContext(context.getApplicationContext())).execute();
        }
    }

    private String a(Context context) {
        int myPid = Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
        if (!(activityManager == null || activityManager.getRunningAppProcesses() == null)) {
            for (RunningAppProcessInfo runningAppProcessInfo : activityManager.getRunningAppProcesses()) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.processName;
                }
            }
        }
        return null;
    }

    public static UMShareAPI get(Context context) {
        if (a == null || a.b == null) {
            a = new UMShareAPI(context);
            SLog.welcome();
        }
        a.b.a(context);
        return a;
    }

    public static void init(Context context, String str) {
        SocializeConstants.APPKEY = str;
        get(context);
    }

    @Deprecated
    public void doOauthVerify(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        UMLog.putAuth();
        if (UMConfigure.getInitStatus()) {
            a.b.a((Context) activity);
            if (SLog.isDebug() && !a(activity, share_media)) {
                return;
            }
            if (activity != null) {
                final Activity activity2 = activity;
                final SHARE_MEDIA share_media2 = share_media;
                final UMAuthListener uMAuthListener2 = uMAuthListener;
                new DialogThread<Void>(activity) {
                    /* renamed from: a */
                    protected Void doInBackground() {
                        if (UMShareAPI.this.b == null) {
                            UMShareAPI.this.b = new com.umeng.socialize.a.a(activity2);
                        }
                        UMShareAPI.this.b.c(activity2, share_media2, uMAuthListener2);
                        return null;
                    }
                }.execute();
                return;
            }
            SLog.E(CHECK.ACTIVITYNULL);
            return;
        }
        SLog.selfLog(CHECK.NOINT);
    }

    public void deleteOauth(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (activity != null) {
            a.b.a((Context) activity);
            final Activity activity2 = activity;
            final SHARE_MEDIA share_media2 = share_media;
            final UMAuthListener uMAuthListener2 = uMAuthListener;
            new DialogThread<Void>(activity) {
                protected Object doInBackground() {
                    if (UMShareAPI.this.b != null) {
                        UMShareAPI.this.b.a(activity2, share_media2, uMAuthListener2);
                    }
                    return null;
                }
            }.execute();
            return;
        }
        SLog.E(CHECK.ACTIVITYNULL);
    }

    public void getPlatformInfo(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (activity == null) {
            SLog.E(CHECK.ACTIVITYNULL);
        } else if (UMConfigure.getInitStatus()) {
            UMLog.putAuth();
            if (SLog.isDebug()) {
                if (a(activity, share_media)) {
                    UrlUtil.getInfoPrint(share_media);
                } else {
                    return;
                }
            }
            a.b.a((Context) activity);
            final Activity activity2 = activity;
            final SHARE_MEDIA share_media2 = share_media;
            final UMAuthListener uMAuthListener2 = uMAuthListener;
            new DialogThread<Void>(activity) {
                protected Object doInBackground() {
                    if (UMShareAPI.this.b != null) {
                        UMShareAPI.this.b.b(activity2, share_media2, uMAuthListener2);
                    }
                    return null;
                }
            }.execute();
        } else {
            SLog.selfLog(CHECK.NOINT);
        }
    }

    public boolean isInstall(Activity activity, SHARE_MEDIA share_media) {
        if (this.b != null) {
            return this.b.a(activity, share_media);
        }
        this.b = new com.umeng.socialize.a.a(activity);
        return this.b.a(activity, share_media);
    }

    public boolean isAuthorize(Activity activity, SHARE_MEDIA share_media) {
        if (this.b != null) {
            return this.b.d(activity, share_media);
        }
        this.b = new com.umeng.socialize.a.a(activity);
        return this.b.d(activity, share_media);
    }

    public boolean isSupport(Activity activity, SHARE_MEDIA share_media) {
        if (this.b != null) {
            return this.b.b(activity, share_media);
        }
        this.b = new com.umeng.socialize.a.a(activity);
        return this.b.b(activity, share_media);
    }

    public String getversion(Activity activity, SHARE_MEDIA share_media) {
        if (this.b != null) {
            return this.b.c(activity, share_media);
        }
        this.b = new com.umeng.socialize.a.a(activity);
        return this.b.c(activity, share_media);
    }

    public void doShare(Activity activity, ShareAction shareAction, UMShareListener uMShareListener) {
        UMLog.putShare();
        if (UMConfigure.getInitStatus()) {
            final WeakReference weakReference = new WeakReference(activity);
            if (SLog.isDebug()) {
                if (a(activity, shareAction.getPlatform())) {
                    UrlUtil.sharePrint(shareAction.getPlatform());
                } else {
                    return;
                }
            }
            if (weakReference.get() == null || ((Activity) weakReference.get()).isFinishing()) {
                SLog.E(CHECK.ACTIVITYNULL);
                return;
            }
            a.b.a((Context) activity);
            final ShareAction shareAction2 = shareAction;
            final UMShareListener uMShareListener2 = uMShareListener;
            new DialogThread<Void>((Context) weakReference.get()) {
                /* renamed from: a */
                protected Void doInBackground() {
                    if (!(weakReference.get() == null || ((Activity) weakReference.get()).isFinishing())) {
                        if (UMShareAPI.this.b != null) {
                            UMShareAPI.this.b.a((Activity) weakReference.get(), shareAction2, uMShareListener2);
                        } else {
                            UMShareAPI.this.b = new com.umeng.socialize.a.a((Context) weakReference.get());
                            UMShareAPI.this.b.a((Activity) weakReference.get(), shareAction2, uMShareListener2);
                        }
                    }
                    return null;
                }
            }.execute();
            return;
        }
        SLog.selfLog(CHECK.NOINT);
    }

    private boolean a(Activity activity, SHARE_MEDIA share_media) {
        int i = 0;
        for (Method name : activity.getClass().getDeclaredMethods()) {
            if (name.getName().equals("onActivityResult")) {
                i = true;
            }
        }
        if (i == 0) {
            SLog.mutlE(CHECK.ALL_NO_ONACTIVITY, UrlUtil.ALL_NO_ONACTIVITY);
        }
        if (share_media == SHARE_MEDIA.QQ) {
            SLog.E(UmengTool.checkQQByself(activity));
        } else if (share_media == SHARE_MEDIA.WEIXIN) {
            SLog.E(UmengTool.checkWxBySelf(activity));
        } else if (share_media == SHARE_MEDIA.SINA) {
            SLog.E(UmengTool.checkSinaBySelf(activity));
        } else if (share_media == SHARE_MEDIA.FACEBOOK) {
            SLog.E(UmengTool.checkFBByself(activity));
        } else {
            if (share_media == SHARE_MEDIA.VKONTAKTE) {
                SLog.E(UmengTool.checkVKByself(activity));
            }
            if (share_media == SHARE_MEDIA.LINKEDIN) {
                SLog.E(UmengTool.checkLinkin(activity));
            }
            if (share_media == SHARE_MEDIA.KAKAO) {
                SLog.E(UmengTool.checkKakao(activity));
            }
        }
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (this.b != null) {
            this.b.a(i, i2, intent);
        } else {
            SLog.E(CHECK.ROUTERNULL);
        }
        SLog.I(CHECK.getActivityResult(i, i2));
    }

    public UMSSOHandler getHandler(SHARE_MEDIA share_media) {
        if (this.b != null) {
            return this.b.a(share_media);
        }
        return null;
    }

    public void release() {
        this.b.a();
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.b.a(bundle);
    }

    public void fetchAuthResultWithBundle(Activity activity, Bundle bundle, UMAuthListener uMAuthListener) {
        this.b.a(activity, bundle, uMAuthListener);
    }

    public void setShareConfig(UMShareConfig uMShareConfig) {
        this.b.a(uMShareConfig);
    }
}
