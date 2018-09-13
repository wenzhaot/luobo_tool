package com.umeng.socialize;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.tencent.mm.opensdk.channel.MMessageActV2;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.PlatformConfig.APPIDPlatform;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CHECK;
import com.umeng.socialize.utils.UmengText.FACEBOOK;
import com.umeng.socialize.utils.UmengText.QQ;
import com.umeng.socialize.utils.UmengText.SINA;
import com.umeng.socialize.utils.UmengText.WX;

public class UmengTool {
    public static void getSignature(Context context) {
        showDialog(context, "包名：" + ContextUtil.getPackageName() + "\n" + "签名:" + UMUtils.getAppMD5Signature(context) + "\n" + "facebook keyhash:" + UMUtils.getAppHashKey(context));
    }

    public static void showDialog(Context context, String str) {
        new Builder(context).setTitle("友盟Debug模式自检").setMessage(str).setPositiveButton("关闭", null).show();
    }

    public static String getStrRedicrectUrl() {
        return ((APPIDPlatform) PlatformConfig.configs.get(SHARE_MEDIA.SINA)).redirectUrl;
    }

    public static String checkWxBySelf(Context context) {
        String packageName = context.getPackageName();
        String str = packageName + MMessageActV2.DEFAULT_ENTRY_CLASS_NAME;
        if (!UMUtils.checkPath(str)) {
            return WX.WX_ERRORACTIVITY;
        }
        String appMD5Signature = UMUtils.getAppMD5Signature(context);
        if (UMUtils.checkAndroidManifest(context, str)) {
            return CHECK.checkSuccess(appMD5Signature.toLowerCase(), packageName);
        }
        return WX.WX_ERRORMANIFEST;
    }

    public static void checkWx(Context context) {
        showDialog(context, checkWxBySelf(context));
    }

    public static String checkSinaBySelf(Context context) {
        String packageName = context.getPackageName();
        String appMD5Signature = UMUtils.getAppMD5Signature(context);
        String str = "com.sina.weibo.sdk.web.WeiboSdkWebActivity";
        String str2 = "com.sina.weibo.sdk.share.WbShareTransActivity";
        if (!UMUtils.checkAndroidManifest(context, "com.umeng.socialize.media.WBShareCallBackActivity")) {
            return SINA.SINA_CALLBACKACTIVITY;
        }
        if (!UMUtils.checkAndroidManifest(context, str)) {
            return SINA.SINA_WEBACTIVITY;
        }
        if (UMUtils.checkAndroidManifest(context, str2)) {
            return CHECK.checkSuccess(appMD5Signature.toLowerCase(), packageName);
        }
        return SINA.SINA_TRANSACTIVITY;
    }

    public static void checkSina(Context context) {
        showDialog(context, checkSinaBySelf(context));
    }

    public static void checkAlipay(Context context) {
        String packageName = context.getPackageName();
        packageName + ".apshare.ShareEntryActivity";
        if (UMUtils.checkPath(packageName + ".apshare.ShareEntryActivity")) {
            SLog.E(CHECK.ALIPAYSUCCESS);
        } else {
            SLog.E(CHECK.ALIPAYERROR);
        }
    }

    @TargetApi(9)
    public static String checkFBByself(Context context) {
        String str = "";
        if (!UMUtils.checkAndroidManifest(context, "com.umeng.facebook.FacebookActivity")) {
            return FACEBOOK.NOFACEBOOKACTIVITY;
        }
        if (!UMUtils.checkMetaData(context, "com.facebook.sdk.ApplicationId")) {
            return FACEBOOK.NOMETA;
        }
        if (UMUtils.checkResource(context, "facebook_app_id", "string")) {
            return CHECK.checkSuccess(UMUtils.getAppHashKey(context), ContextUtil.getPackageName());
        }
        return FACEBOOK.ERRORMETA;
    }

    public static String checkQQByself(Context context) {
        String str = "com.tencent.tauth.AuthActivity";
        String str2 = "com.tencent.connect.common.AssistActivity";
        if (!UMUtils.checkAndroidManifest(context, str)) {
            return QQ.getError(str);
        }
        if (!UMUtils.checkAndroidManifest(context, str2)) {
            return QQ.getError(str2);
        }
        if (!UMUtils.checkPermission(context, MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE)) {
            return QQ.ADDPERMISSION;
        }
        if (UMUtils.checkIntentFilterData(context, ((APPIDPlatform) PlatformConfig.getPlatform(SHARE_MEDIA.QQ)).appId)) {
            return "qq配置正确";
        }
        return QQ.ERRORDATA;
    }

    public static String checkVKByself(Context context) {
        context.getPackageName();
        return "你使用的签名：" + UMUtils.getAppSHA1Key(context).replace(":", "");
    }

    public static String checkLinkin(Context context) {
        CharSequence packageName = context.getPackageName();
        context.getPackageManager();
        if (TextUtils.isEmpty(packageName)) {
            return "包名为空";
        }
        try {
            context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            return "领英 配置正确，请检查领英后台签名:" + UMUtils.getAppHashKey(context);
        } catch (NameNotFoundException e) {
            return "签名获取失败";
        }
    }

    public static String checkKakao(Context context) {
        CharSequence packageName = context.getPackageName();
        context.getPackageManager();
        if (TextUtils.isEmpty(packageName)) {
            return "包名为空";
        }
        try {
            context.getPackageManager().getPackageInfo(context.getPackageName(), 64);
            return "kakao 配置正确，请检查kakao后台签名:" + UMUtils.getAppHashKey(context);
        } catch (NameNotFoundException e) {
            return "签名获取失败";
        }
    }

    public static void checkQQ(Context context) {
        showDialog(context, checkQQByself(context));
    }

    public static void checkFacebook(Context context) {
        showDialog(context, checkFBByself(context));
    }

    public static void checkVK(Context context) {
        showDialog(context, checkVKByself(context));
    }
}
