package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.stub.StubApp;
import com.taobao.accs.common.Constants;
import com.umeng.socialize.PlatformConfig.APPIDPlatform;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.AppInfo;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.WBShareCallBackActivity;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.h;
import com.umeng.socialize.net.i;
import com.umeng.socialize.sina.SinaAPI;
import com.umeng.socialize.sina.auth.AuthInfo;
import com.umeng.socialize.sina.helper.MD5;
import com.umeng.socialize.sina.message.BaseRequest;
import com.umeng.socialize.sina.message.BaseResponse;
import com.umeng.socialize.sina.message.SendMultiMessageToWeiboRequest;
import com.umeng.socialize.sina.params.ShareRequestParam;
import com.umeng.socialize.sina.params.WeiboParameters;
import com.umeng.socialize.sina.util.Utility;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.AUTH;
import com.umeng.socialize.utils.UmengText.SINA;
import com.umeng.socialize.utils.UrlUtil;
import com.umeng.socialize.view.OauthDialog;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SinaSimplyHandler extends UMSSOHandler {
    public static final String SCOPE = "email,direct_messages_read,direct_messages_write,friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
    private static final Uri a = Uri.parse("content://com.sina.weibo.sdkProvider/query/package");
    private static final Uri b = Uri.parse("content://com.sina.weibo.sdkProvider/query/package");
    private static final String c = "com.sina.weibo.action.sdkidentity";
    private static final String d = "weibo_for_sdk.json";
    private static final String e = "sina2/main?uid";
    private static String j = "";
    private static String k = "";
    public static String keyHash = "";
    private static final String q = "userName";
    private static final String r = "id";
    public final int ERR_CANCEL = 1;
    public final int ERR_FAIL = 2;
    public final int ERR_OK = 0;
    private Context f = null;
    private a g = null;
    private WeiboMultiMessage h;
    private String i = "6.9.2";
    private UMAuthListener l;
    private SHARE_MEDIA m = SHARE_MEDIA.SINA;
    private String n = "";
    private String o = "";
    private String p = "";
    private AuthInfo s;
    private AppInfo t;
    private SinaAPI u;
    private UMShareListener v;

    class a implements UMAuthListener {
        private UMAuthListener b = null;

        a(UMAuthListener uMAuthListener) {
            this.b = uMAuthListener;
        }

        public void onCancel(SHARE_MEDIA share_media, int i) {
            if (this.b != null) {
                this.b.onCancel(share_media, i);
            }
        }

        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
            if (SinaSimplyHandler.this.g != null) {
                SinaSimplyHandler.this.g.a((Map) map).g();
            }
            map.put("aid", SinaSimplyHandler.this.n);
            map.put("as", SinaSimplyHandler.this.o);
            if (this.b != null) {
                this.b.onComplete(share_media, i, map);
            }
        }

        public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
            if (this.b != null) {
                this.b.onError(share_media, i, th);
            }
        }

        public void onStart(SHARE_MEDIA share_media) {
        }
    }

    private AppInfo a(Context context) {
        Object obj = 1;
        AppInfo b = b(context);
        AppInfo c = c(context);
        Object obj2 = b != null ? 1 : null;
        if (c == null) {
            obj = null;
        }
        return (obj2 == null || obj == null) ? obj2 == null ? obj != null ? c : null : b : b.c() >= c.c() ? b : c;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x009f A:{SYNTHETIC, Splitter: B:38:0x009f} */
    private com.umeng.socialize.media.AppInfo a(java.lang.String r9) {
        /*
        r8 = this;
        r7 = -1;
        r0 = 0;
        r1 = android.text.TextUtils.isEmpty(r9);
        if (r1 == 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r1 = r8.f;	 Catch:{ Exception -> 0x00aa, all -> 0x009a }
        r2 = 2;
        r1 = r1.createPackageContext(r9, r2);	 Catch:{ Exception -> 0x00aa, all -> 0x009a }
        r2 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r3 = new byte[r2];	 Catch:{ Exception -> 0x00aa, all -> 0x009a }
        r1 = r1.getAssets();	 Catch:{ Exception -> 0x00aa, all -> 0x009a }
        r2 = "weibo_for_sdk.json";
        r2 = r1.open(r2);	 Catch:{ Exception -> 0x00aa, all -> 0x009a }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0037 }
        r1.<init>();	 Catch:{ Exception -> 0x0037 }
    L_0x0024:
        r4 = 0;
        r5 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r4 = r2.read(r3, r4, r5);	 Catch:{ Exception -> 0x0037 }
        if (r4 == r7) goto L_0x0046;
    L_0x002d:
        r5 = new java.lang.String;	 Catch:{ Exception -> 0x0037 }
        r6 = 0;
        r5.<init>(r3, r6, r4);	 Catch:{ Exception -> 0x0037 }
        r1.append(r5);	 Catch:{ Exception -> 0x0037 }
        goto L_0x0024;
    L_0x0037:
        r1 = move-exception;
    L_0x0038:
        com.umeng.socialize.utils.SLog.error(r1);	 Catch:{ all -> 0x00a8 }
        if (r2 == 0) goto L_0x0008;
    L_0x003d:
        r2.close();	 Catch:{ IOException -> 0x0041 }
        goto L_0x0008;
    L_0x0041:
        r1 = move-exception;
        com.umeng.socialize.utils.SLog.error(r1);
        goto L_0x0008;
    L_0x0046:
        r3 = r1.toString();	 Catch:{ Exception -> 0x0037 }
        r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ Exception -> 0x0037 }
        if (r3 != 0) goto L_0x008d;
    L_0x0050:
        r3 = r8.f;	 Catch:{ Exception -> 0x0037 }
        r3 = validateWeiboSign(r3, r9);	 Catch:{ Exception -> 0x0037 }
        if (r3 == 0) goto L_0x008d;
    L_0x0058:
        r3 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0037 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0037 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x0037 }
        r1 = "support_api";
        r4 = -1;
        r4 = r3.optInt(r1, r4);	 Catch:{ Exception -> 0x0037 }
        r1 = new com.umeng.socialize.media.AppInfo;	 Catch:{ Exception -> 0x0037 }
        r1.<init>();	 Catch:{ Exception -> 0x0037 }
        r1.a(r9);	 Catch:{ Exception -> 0x0037 }
        r1.a(r4);	 Catch:{ Exception -> 0x0037 }
        r4 = "authActivityName";
        r5 = "com.sina.weibo.SSOActivity";
        r3 = r3.optString(r4, r5);	 Catch:{ Exception -> 0x0037 }
        r1.b(r3);	 Catch:{ Exception -> 0x0037 }
        if (r2 == 0) goto L_0x0086;
    L_0x0083:
        r2.close();	 Catch:{ IOException -> 0x0088 }
    L_0x0086:
        r0 = r1;
        goto L_0x0008;
    L_0x0088:
        r0 = move-exception;
        com.umeng.socialize.utils.SLog.error(r0);
        goto L_0x0086;
    L_0x008d:
        if (r2 == 0) goto L_0x0008;
    L_0x008f:
        r2.close();	 Catch:{ IOException -> 0x0094 }
        goto L_0x0008;
    L_0x0094:
        r1 = move-exception;
        com.umeng.socialize.utils.SLog.error(r1);
        goto L_0x0008;
    L_0x009a:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x009d:
        if (r2 == 0) goto L_0x00a2;
    L_0x009f:
        r2.close();	 Catch:{ IOException -> 0x00a3 }
    L_0x00a2:
        throw r0;
    L_0x00a3:
        r1 = move-exception;
        com.umeng.socialize.utils.SLog.error(r1);
        goto L_0x00a2;
    L_0x00a8:
        r0 = move-exception;
        goto L_0x009d;
    L_0x00aa:
        r1 = move-exception;
        r2 = r0;
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.handler.SinaSimplyHandler.a(java.lang.String):com.umeng.socialize.media.AppInfo");
    }

    private void a(final UMAuthListener uMAuthListener) {
        if (!DeviceConfig.isOnline(this.f)) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    uMAuthListener.onError(SHARE_MEDIA.SINA, 0, new Throwable(UmengErrorCode.ShareFailed.getMessage() + "not online"));
                }
            });
        }
        final WeiboParameters weiboParameters = new WeiboParameters(this.n);
        weiboParameters.put("client_id", this.n);
        weiboParameters.put("redirect_uri", this.p);
        weiboParameters.put("scope", SCOPE);
        weiboParameters.put("response_type", "code");
        weiboParameters.put("version", "0031405000");
        weiboParameters.put("luicode", "10000360");
        weiboParameters.put("lfid", "OP_" + this.n);
        final String aid = Utility.getAid((Context) this.mWeakAct.get(), this.n);
        QueuedWork.runInMain(new Runnable() {
            public void run() {
                if (!TextUtils.isEmpty(aid)) {
                    weiboParameters.put("aid", aid);
                }
                weiboParameters.put(ShareRequestParam.REQ_PARAM_PACKAGENAME, ContextUtil.getPackageName());
                weiboParameters.put(ShareRequestParam.REQ_PARAM_KEY_HASH, SinaSimplyHandler.keyHash);
                String str = "https://open.weibo.cn/oauth2/authorize?" + weiboParameters.encodeUrl();
                if (SinaSimplyHandler.this.mWeakAct.get() != null && !((Activity) SinaSimplyHandler.this.mWeakAct.get()).isFinishing()) {
                    OauthDialog oauthDialog = new OauthDialog((Activity) SinaSimplyHandler.this.mWeakAct.get(), SHARE_MEDIA.SINA, new a(uMAuthListener));
                    oauthDialog.setWaitUrl(str);
                    oauthDialog.setmRedirectUri(SinaSimplyHandler.this.p);
                    oauthDialog.show();
                }
            }
        });
    }

    private boolean a() {
        try {
            AppInfo a = a(this.f);
            Intent intent = new Intent();
            intent.setClassName(a.a(), a.b());
            intent.putExtras(f());
            intent.putExtra("_weibo_command_type", 3);
            intent.putExtra("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
            intent.putExtra("aid", Utility.getAid((Context) this.mWeakAct.get(), this.s.getAppKey()));
            if (!a((Context) this.mWeakAct.get(), intent)) {
                return false;
            }
            boolean z;
            Object aid = Utility.getAid((Context) this.mWeakAct.get(), this.s.getAppKey());
            if (!TextUtils.isEmpty(aid)) {
                intent.putExtra("aid", aid);
            }
            try {
                ((Activity) this.mWeakAct.get()).startActivityForResult(intent, HandlerRequestCode.SINASSO_REQUEST_CODE);
                z = true;
            } catch (ActivityNotFoundException e) {
                z = false;
            }
            return z;
        } catch (Throwable e2) {
            SLog.error(e2);
            return false;
        }
    }

    private static boolean a(Context context, Intent intent) {
        boolean z = false;
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return z;
        }
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, z);
        if (resolveActivity == null) {
            return z;
        }
        try {
            return a(packageManager.getPackageInfo(resolveActivity.activityInfo.packageName, 64).signatures, "18da2bf10352443a00a5e046d9fca6bd");
        } catch (Throwable e) {
            SLog.error(e);
            return z;
        } catch (Throwable e2) {
            SLog.error(e2);
            return z;
        }
    }

    private static boolean a(Signature[] signatureArr, String str) {
        if (signatureArr == null || str == null) {
            return false;
        }
        for (Signature toByteArray : signatureArr) {
            if (str.equals(MD5.hexdigest(toByteArray.toByteArray()))) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x00b1  */
    private com.umeng.socialize.media.AppInfo b(android.content.Context r9) {
        /*
        r8 = this;
        r6 = 0;
        r0 = r9.getContentResolver();
        r1 = a;	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        r2 = 0;
        r2 = (java.lang.String[]) r2;	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        r3 = 0;
        r3 = (java.lang.String) r3;	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        r4 = 0;
        r4 = (java.lang.String[]) r4;	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        r5 = 0;
        r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        r7 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x00a1, all -> 0x00ad }
        if (r7 != 0) goto L_0x0036;
    L_0x0019:
        r1 = b;	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        r2 = 0;
        r2 = (java.lang.String[]) r2;	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        r3 = 0;
        r3 = (java.lang.String) r3;	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        r4 = 0;
        r4 = (java.lang.String[]) r4;	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        r5 = 0;
        r5 = (java.lang.String) r5;	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x00ba, all -> 0x00b5 }
        if (r1 != 0) goto L_0x0037;
    L_0x002d:
        r0 = 0;
        r0 = (com.umeng.socialize.media.AppInfo) r0;	 Catch:{ Exception -> 0x00bd }
        if (r1 == 0) goto L_0x0035;
    L_0x0032:
        r1.close();
    L_0x0035:
        return r0;
    L_0x0036:
        r1 = r7;
    L_0x0037:
        r0 = "support_api";
        r2 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd }
        r0 = "package";
        r4 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd }
        r0 = "sso_activity";
        r5 = r1.getColumnIndex(r0);	 Catch:{ Exception -> 0x00bd }
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x00bd }
        if (r0 != 0) goto L_0x0059;
    L_0x0052:
        if (r1 == 0) goto L_0x0057;
    L_0x0054:
        r1.close();
    L_0x0057:
        r0 = r6;
        goto L_0x0035;
    L_0x0059:
        r0 = -1;
        r2 = r1.getString(r2);	 Catch:{ Exception -> 0x00bd }
        r0 = java.lang.Integer.parseInt(r2);	 Catch:{ NumberFormatException -> 0x0081 }
        r3 = r0;
    L_0x0063:
        r4 = r1.getString(r4);	 Catch:{ Exception -> 0x00bd }
        if (r5 <= 0) goto L_0x00bf;
    L_0x0069:
        r0 = r1.getString(r5);	 Catch:{ Exception -> 0x00bd }
        r2 = r0;
    L_0x006e:
        r0 = android.text.TextUtils.isEmpty(r4);	 Catch:{ Exception -> 0x00bd }
        if (r0 != 0) goto L_0x007a;
    L_0x0074:
        r0 = validateWeiboSign(r9, r4);	 Catch:{ Exception -> 0x00bd }
        if (r0 != 0) goto L_0x0087;
    L_0x007a:
        if (r1 == 0) goto L_0x007f;
    L_0x007c:
        r1.close();
    L_0x007f:
        r0 = r6;
        goto L_0x0035;
    L_0x0081:
        r2 = move-exception;
        com.umeng.socialize.utils.SLog.error(r2);	 Catch:{ Exception -> 0x00bd }
        r3 = r0;
        goto L_0x0063;
    L_0x0087:
        r0 = new com.umeng.socialize.media.AppInfo;	 Catch:{ Exception -> 0x00bd }
        r0.<init>();	 Catch:{ Exception -> 0x00bd }
        r0.a(r4);	 Catch:{ Exception -> 0x00bd }
        r0.a(r3);	 Catch:{ Exception -> 0x00bd }
        r3 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x00bd }
        if (r3 != 0) goto L_0x009b;
    L_0x0098:
        r0.b(r2);	 Catch:{ Exception -> 0x00bd }
    L_0x009b:
        if (r1 == 0) goto L_0x0035;
    L_0x009d:
        r1.close();
        goto L_0x0035;
    L_0x00a1:
        r0 = move-exception;
        r1 = r6;
    L_0x00a3:
        com.umeng.socialize.utils.SLog.error(r0);	 Catch:{ all -> 0x00b8 }
        if (r1 == 0) goto L_0x00ab;
    L_0x00a8:
        r1.close();
    L_0x00ab:
        r0 = r6;
        goto L_0x0035;
    L_0x00ad:
        r0 = move-exception;
        r1 = r6;
    L_0x00af:
        if (r1 == 0) goto L_0x00b4;
    L_0x00b1:
        r1.close();
    L_0x00b4:
        throw r0;
    L_0x00b5:
        r0 = move-exception;
        r1 = r7;
        goto L_0x00af;
    L_0x00b8:
        r0 = move-exception;
        goto L_0x00af;
    L_0x00ba:
        r0 = move-exception;
        r1 = r7;
        goto L_0x00a3;
    L_0x00bd:
        r0 = move-exception;
        goto L_0x00a3;
    L_0x00bf:
        r2 = r6;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.handler.SinaSimplyHandler.b(android.content.Context):com.umeng.socialize.media.AppInfo");
    }

    private String b(String str) {
        try {
            return ((Activity) this.mWeakAct.get()).getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (Throwable e) {
            SLog.error(e);
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
    }

    private void b(final UMAuthListener uMAuthListener) {
        i iVar = (i) new SocializeClient().execute(new h(getUID(), e(), this.n, Utility.getAid((Context) this.mWeakAct.get(), this.n)));
        if (iVar != null) {
            final Map map = iVar.a;
            if (map != null && !map.containsKey("error")) {
                map.put("iconurl", map.get("profile_image_url"));
                map.put("name", map.get("screen_name"));
                map.put("gender", getGender(map.get("gender")));
                if (this.g != null) {
                    map.put("uid", getUID());
                    map.put("access_token", e());
                    map.put("refreshToken", d());
                    map.put("expires_in", String.valueOf(c()));
                    map.put("accessToken", e());
                    map.put("refreshToken", d());
                    map.put("expiration", String.valueOf(c()));
                    QueuedWork.runInMain(new Runnable() {
                        public void run() {
                            uMAuthListener.onComplete(SHARE_MEDIA.SINA, 2, map);
                        }
                    });
                    return;
                }
                return;
            } else if (map != null) {
                if (this.g != null) {
                    this.g.h();
                }
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        uMAuthListener.onError(SHARE_MEDIA.SINA, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed + ((String) map.get("error")).toString()));
                    }
                });
                return;
            } else {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        uMAuthListener.onError(SHARE_MEDIA.SINA, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed + AUTH.DATA_EMPTY));
                    }
                });
                return;
            }
        }
        QueuedWork.runInMain(new Runnable() {
            public void run() {
                uMAuthListener.onError(SHARE_MEDIA.SINA, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed + SINA.SINA_GET_ERROR));
            }
        });
    }

    private boolean b() {
        if (!isInstall()) {
            return false;
        }
        AppInfo wbAppInfo = getWbAppInfo();
        return wbAppInfo != null && wbAppInfo.c() >= 10772;
    }

    private long c() {
        return this.g != null ? this.g.c() : 0;
    }

    private AppInfo c(Context context) {
        AppInfo appInfo = null;
        Intent intent = new Intent(c);
        intent.addCategory("android.intent.category.DEFAULT");
        List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        if (!(queryIntentServices == null || queryIntentServices.isEmpty())) {
            for (ResolveInfo resolveInfo : queryIntentServices) {
                AppInfo a;
                if (!(resolveInfo.serviceInfo == null || resolveInfo.serviceInfo.applicationInfo == null || TextUtils.isEmpty(resolveInfo.serviceInfo.packageName))) {
                    a = a(resolveInfo.serviceInfo.packageName);
                    if (a != null) {
                        appInfo = a;
                    }
                }
                a = appInfo;
                appInfo = a;
            }
        }
        return appInfo;
    }

    private void c(final UMAuthListener uMAuthListener) {
        authorize(new UMAuthListener() {
            public void onCancel(SHARE_MEDIA share_media, int i) {
                uMAuthListener.onCancel(share_media, i);
            }

            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                QueuedWork.runInBack(new Runnable() {
                    public void run() {
                        SinaSimplyHandler.this.b(uMAuthListener);
                    }
                }, true);
            }

            public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                uMAuthListener.onError(share_media, i, th);
            }

            public void onStart(SHARE_MEDIA share_media) {
            }
        });
    }

    private String d() {
        return this.g != null ? this.g.b() : "";
    }

    private String e() {
        return this.g != null ? this.g.a() : "";
    }

    private Bundle f() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_APP_KEY, this.n);
        bundle.putString("redirectUri", this.p);
        bundle.putString("scope", SCOPE);
        bundle.putString(ShareRequestParam.REQ_PARAM_PACKAGENAME, ContextUtil.getPackageName());
        bundle.putString(ShareRequestParam.REQ_PARAM_KEY_HASH, Utility.getSign((Context) this.mWeakAct.get(), ContextUtil.getPackageName()));
        return bundle;
    }

    public static boolean validateWeiboSign(Context context, String str) {
        boolean z = false;
        if (TextUtils.isEmpty(str)) {
            return z;
        }
        try {
            return a(context.getPackageManager().getPackageInfo(str, 64).signatures, "18da2bf10352443a00a5e046d9fca6bd");
        } catch (NameNotFoundException e) {
            return z;
        }
    }

    public void authorize(final UMAuthListener uMAuthListener) {
        this.l = uMAuthListener;
        if (getShareConfig().isSinaAuthWithWebView()) {
            a(uMAuthListener);
        } else if (isInstall()) {
            QueuedWork.runInBack(new Runnable() {
                public void run() {
                    if (!SinaSimplyHandler.this.a()) {
                        SinaSimplyHandler.this.a(uMAuthListener);
                    }
                }
            }, true);
        } else {
            a(uMAuthListener);
        }
    }

    public void deleteAuth() {
        if (this.g != null) {
            this.g.h();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0042  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0033  */
    public void deleteAuth(final com.umeng.socialize.UMAuthListener r6) {
        /*
        r5 = this;
        r2 = new com.umeng.socialize.net.c;
        r1 = r5.n;
        r3 = r5.e();
        r0 = r5.mWeakAct;
        r0 = r0.get();
        r0 = (android.content.Context) r0;
        r4 = r5.n;
        r0 = com.umeng.socialize.sina.util.Utility.getAid(r0, r4);
        r2.<init>(r1, r3, r0);
        r0 = r5.g;
        if (r0 == 0) goto L_0x0022;
    L_0x001d:
        r0 = r5.g;
        r0.h();
    L_0x0022:
        r1 = 0;
        r0 = new com.umeng.socialize.net.base.SocializeClient;	 Catch:{ Throwable -> 0x003c }
        r0.<init>();	 Catch:{ Throwable -> 0x003c }
        r0 = r0.execute(r2);	 Catch:{ Throwable -> 0x003c }
        r0 = (com.umeng.socialize.net.d) r0;	 Catch:{ Throwable -> 0x003c }
        if (r0 == 0) goto L_0x0040;
    L_0x0030:
        r0 = 1;
    L_0x0031:
        if (r0 == 0) goto L_0x0042;
    L_0x0033:
        r0 = new com.umeng.socialize.handler.SinaSimplyHandler$5;
        r0.<init>(r6);
        com.umeng.socialize.common.QueuedWork.runInMain(r0);
    L_0x003b:
        return;
    L_0x003c:
        r0 = move-exception;
        com.umeng.socialize.utils.SLog.error(r0);
    L_0x0040:
        r0 = r1;
        goto L_0x0031;
    L_0x0042:
        r0 = new com.umeng.socialize.handler.SinaSimplyHandler$6;
        r0.<init>(r6);
        com.umeng.socialize.common.QueuedWork.runInMain(r0);
        goto L_0x003b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.handler.SinaSimplyHandler.deleteAuth(com.umeng.socialize.UMAuthListener):void");
    }

    public AppInfo getInfo() {
        return this.t;
    }

    public WeiboMultiMessage getMessage() {
        return this.h;
    }

    public void getPlatformInfo(UMAuthListener uMAuthListener) {
        if (getShareConfig().isNeedAuthOnGetUserInfo() || !this.g.f()) {
            c(uMAuthListener);
        } else {
            b(uMAuthListener);
        }
    }

    public int getRequestCode() {
        return HandlerRequestCode.SINA_REQUEST_CODE;
    }

    public String getUID() {
        return this.g != null ? this.g.d() : "";
    }

    public String getVersion() {
        return this.i;
    }

    public synchronized AppInfo getWbAppInfo() {
        this.t = a(this.f);
        return this.t;
    }

    public SinaAPI getmWeiboShareAPI() {
        return this.u;
    }

    public boolean isAuthorize() {
        return this.g == null ? false : this.g.f();
    }

    public boolean isHasAuthListener() {
        return this.l != null;
    }

    public boolean isInstall() {
        AppInfo wbAppInfo = getWbAppInfo();
        return wbAppInfo != null && wbAppInfo.d();
    }

    public boolean isSupportAuth() {
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        if (i != HandlerRequestCode.SINASSO_REQUEST_CODE) {
            super.onActivityResult(i, i2, intent);
        } else if (i2 == -1) {
            String stringExtra = intent.getStringExtra("error");
            if (stringExtra == null) {
                stringExtra = intent.getStringExtra("error_type");
            }
            if (stringExtra != null) {
                if (stringExtra.equals("access_denied") || stringExtra.equals("OAuthAccessDeniedException")) {
                    this.l.onCancel(SHARE_MEDIA.SINA, 0);
                    return;
                }
                String stringExtra2 = intent.getStringExtra("error_description");
                if (stringExtra2 != null) {
                    stringExtra = stringExtra + ":" + stringExtra2;
                }
                this.l.onError(SHARE_MEDIA.SINA, 0, new Throwable(UmengErrorCode.AuthorizeFailed + stringExtra));
            } else if (this.l != null) {
                Bundle extras = intent.getExtras();
                extras.keySet();
                Map hashMap = new HashMap();
                hashMap.put("name", extras.getString(q));
                hashMap.put("accessToken", extras.getString("access_token"));
                hashMap.put("refreshToken", extras.getString("refresh_token"));
                hashMap.put("expiration", extras.getString("expires_in"));
                hashMap.put("uid", extras.getString("uid"));
                hashMap.put("aid", this.n);
                hashMap.put("as", this.o);
                if (this.g != null) {
                    this.g.a(extras).g();
                }
                this.l.onComplete(SHARE_MEDIA.SINA, 0, hashMap);
            }
        } else if (i2 != 0) {
        } else {
            if (intent != null) {
                this.l.onCancel(SHARE_MEDIA.SINA, 0);
            } else if (this.l != null) {
                this.l.onCancel(SHARE_MEDIA.SINA, 0);
            }
        }
    }

    public void onCancel() {
        if (this.v != null) {
            this.v.onCancel(SHARE_MEDIA.SINA);
        }
    }

    public void onCreate(Context context, Platform platform) {
        super.onCreate(context, platform);
        this.f = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.n = ((APPIDPlatform) platform).appId;
        this.o = ((APPIDPlatform) platform).appkey;
        this.p = ((APPIDPlatform) platform).redirectUrl;
        this.s = new AuthInfo(context, ((APPIDPlatform) platform).appId, ((APPIDPlatform) getConfig()).redirectUrl, SCOPE);
        keyHash = Utility.getSign(context, ContextUtil.getPackageName());
        this.g = new a(context, SHARE_MEDIA.SINA.toString());
        this.u = new SinaAPI(StubApp.getOrigApplicationContext(context.getApplicationContext()), this.n, false);
        this.u.registerApp();
    }

    public void onError() {
        if (this.v != null) {
            this.v.onError(SHARE_MEDIA.SINA, new Throwable(UmengErrorCode.ShareFailed.getMessage()));
        }
    }

    public void onResponse(BaseResponse baseResponse) {
        switch (baseResponse.errCode) {
            case 0:
                if (this.v != null) {
                    this.v.onResult(SHARE_MEDIA.SINA);
                }
                baseResponse.toBundle(new Bundle());
                return;
            case 1:
                if (this.v != null) {
                    this.v.onCancel(SHARE_MEDIA.SINA);
                    return;
                }
                return;
            case 2:
                String str = baseResponse.errMsg;
                if (str.contains("auth faild")) {
                    str = UmengText.errorWithUrl(SINA.SINA_SIGN_ERROR, UrlUtil.SINA_ERROR_SIGN);
                }
                if (this.v != null) {
                    this.v.onError(SHARE_MEDIA.SINA, new Throwable(UmengErrorCode.ShareFailed.getMessage() + str));
                    return;
                }
                return;
            default:
                return;
        }
    }

    public void onSuccess() {
        if (this.v != null) {
            this.v.onResult(SHARE_MEDIA.SINA);
        }
    }

    public void release() {
        this.l = null;
    }

    public void setAuthListener(UMAuthListener uMAuthListener) {
        super.setAuthListener(uMAuthListener);
        this.l = uMAuthListener;
    }

    public boolean share(ShareContent shareContent, UMShareListener uMShareListener) {
        SinaShareContent sinaShareContent = new SinaShareContent(shareContent);
        if (this.mShareConfig != null) {
            sinaShareContent.setCompressListener(this.mShareConfig.getCompressListener());
        }
        sinaShareContent.a(b());
        BaseRequest sendMultiMessageToWeiboRequest = new SendMultiMessageToWeiboRequest();
        sendMultiMessageToWeiboRequest.transaction = String.valueOf(System.currentTimeMillis());
        sendMultiMessageToWeiboRequest.multiMessage = sinaShareContent.a();
        this.h = sendMultiMessageToWeiboRequest.multiMessage;
        AuthInfo authInfo = new AuthInfo(getContext(), this.n, ((APPIDPlatform) getConfig()).redirectUrl, SCOPE);
        String str = "";
        String e = e();
        this.v = uMShareListener;
        if (!isInstall()) {
            this.u.startShareWeiboActivity((Activity) this.mWeakAct.get(), e, sendMultiMessageToWeiboRequest, uMShareListener);
        } else if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
            ((Activity) this.mWeakAct.get()).startActivity(new Intent((Context) this.mWeakAct.get(), WBShareCallBackActivity.class));
        }
        return true;
    }
}
