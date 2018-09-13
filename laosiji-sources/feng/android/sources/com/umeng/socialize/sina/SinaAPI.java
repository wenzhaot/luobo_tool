package com.umeng.socialize.sina;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareTransActivity;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.media.AppInfo;
import com.umeng.socialize.media.IWeiboHandler.Response;
import com.umeng.socialize.sina.auth.AuthInfo;
import com.umeng.socialize.sina.helper.MD5;
import com.umeng.socialize.sina.message.BaseRequest;
import com.umeng.socialize.sina.message.SendMessageToWeiboRequest;
import com.umeng.socialize.sina.message.SendMessageToWeiboResponse;
import com.umeng.socialize.sina.message.SendMultiMessageToWeiboRequest;
import com.umeng.socialize.sina.params.ShareRequestParam;
import com.umeng.socialize.sina.util.Utility;
import com.umeng.socialize.sina.webview.ShareDialog;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.SINA;

public class SinaAPI {
    private String mAppKey;
    private Context mContext;
    private String pkgName;

    public SinaAPI(Context context, String str, boolean z) {
        this.mContext = context;
        this.mAppKey = str;
    }

    private Bundle adapterMultiMessage2SingleMessage(WeiboMultiMessage weiboMultiMessage) {
        if (weiboMultiMessage == null) {
            return new Bundle();
        }
        Bundle bundle = new Bundle();
        weiboMultiMessage.toBundle(bundle);
        return bundle;
    }

    private void sendBroadcast(Context context, String str, String str2, String str3, Bundle bundle) {
        Intent intent = new Intent(str);
        String packageName = context.getPackageName();
        intent.putExtra("_weibo_sdkVersion", "0031205000");
        intent.putExtra("_weibo_appPackage", packageName);
        intent.putExtra("_weibo_appKey", str2);
        intent.putExtra("_weibo_flag", 538116905);
        intent.putExtra("_weibo_sign", MD5.hexdigest(Utility.getSign(context, packageName)));
        if (!TextUtils.isEmpty(str3)) {
            intent.setPackage(str3);
        }
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.sendBroadcast(intent, "com.sina.weibo.permission.WEIBO_SDK_PERMISSION");
    }

    public void doResultIntent(Intent intent, WbShareCallback wbShareCallback) {
        if (wbShareCallback != null) {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                switch (extras.getInt("_weibo_resp_errcode")) {
                    case 0:
                        wbShareCallback.onWbShareSuccess();
                        return;
                    case 1:
                        wbShareCallback.onWbShareCancel();
                        return;
                    case 2:
                        wbShareCallback.onWbShareFail();
                        return;
                    default:
                        return;
                }
            }
        }
    }

    public boolean handleWeiboResponse(Intent intent, Response response) {
        CharSequence stringExtra = intent.getStringExtra("_weibo_appPackage");
        CharSequence stringExtra2 = intent.getStringExtra("_weibo_transaction");
        if (TextUtils.isEmpty(stringExtra)) {
            return false;
        }
        if (!(response instanceof Activity)) {
            return false;
        }
        ((Activity) response).getCallingPackage();
        if (TextUtils.isEmpty(stringExtra2)) {
            return false;
        }
        response.a(new SendMessageToWeiboResponse(intent.getExtras()));
        return true;
    }

    public boolean launchWeiboActivity(Activity activity, String str, String str2, String str3, Bundle bundle) {
        if (activity == null || TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || TextUtils.isEmpty(str3)) {
            return false;
        }
        Intent intent = new Intent();
        intent.setPackage(str2);
        intent.setAction(str);
        String packageName = activity.getPackageName();
        intent.putExtra("_weibo_sdkVersion", "0031205000");
        intent.putExtra("_weibo_appPackage", packageName);
        intent.putExtra("_weibo_appKey", str3);
        intent.putExtra("_weibo_flag", 538116905);
        intent.putExtra("_weibo_sign", MD5.hexdigest(Utility.getSign(activity, packageName)));
        intent.putExtra("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        try {
            activity.startActivityForResult(intent, HandlerRequestCode.SINA_SHARE_REQUEST_CODE);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    public boolean registerApp() {
        sendBroadcast(this.mContext, "com.sina.weibo.sdk.Intent.ACTION_WEIBO_REGISTER", this.mAppKey, (String) null, (Bundle) null);
        return true;
    }

    public boolean sendRequest(Activity activity, BaseRequest baseRequest, AuthInfo authInfo, String str, UMShareListener uMShareListener, boolean z) {
        if (baseRequest == null) {
            return false;
        }
        if (!z) {
            return startShareWeiboActivity(activity, str, baseRequest, uMShareListener);
        }
        SendMultiMessageToWeiboRequest sendMultiMessageToWeiboRequest = (SendMultiMessageToWeiboRequest) baseRequest;
        SendMessageToWeiboRequest sendMessageToWeiboRequest = new SendMessageToWeiboRequest();
        sendMessageToWeiboRequest.packageName = sendMultiMessageToWeiboRequest.packageName;
        sendMessageToWeiboRequest.transaction = sendMultiMessageToWeiboRequest.transaction;
        sendMessageToWeiboRequest.message = adapterMultiMessage2SingleMessage(sendMultiMessageToWeiboRequest.multiMessage);
        Bundle bundle = new Bundle();
        sendMessageToWeiboRequest.toBundle(bundle);
        return launchWeiboActivity(activity, "com.sina.weibo.sdk.action.ACTION_WEIBO_ACTIVITY", this.pkgName, this.mAppKey, bundle);
    }

    public void setPkgName(String str) {
        this.pkgName = str;
    }

    public void startClientShare(AppInfo appInfo, Context context, WeiboMultiMessage weiboMultiMessage) {
        Bundle bundle = new Bundle();
        bundle.putInt("_weibo_command_type", 1);
        bundle.putString("_weibo_transaction", String.valueOf(System.currentTimeMillis()));
        bundle.putLong("callbackId", 0);
        bundle.putAll(weiboMultiMessage.toBundle(bundle));
        Intent intent = new Intent();
        intent.setClass(context, WbShareTransActivity.class);
        intent.putExtra("startPackage", appInfo.a());
        intent.putExtra("startAction", "com.sina.weibo.sdk.action.ACTION_WEIBO_ACTIVITY");
        intent.putExtra("startFlag", 0);
        intent.putExtra("startActivity", context.getClass().getName());
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        try {
            context.startActivity(intent);
        } catch (Throwable e) {
            SLog.error(SINA.SINASTARTERROR, e);
        }
    }

    public boolean startShareWeiboActivity(final Activity activity, String str, BaseRequest baseRequest, final UMShareListener uMShareListener) {
        try {
            String packageName = activity.getPackageName();
            ShareRequestParam shareRequestParam = new ShareRequestParam(activity);
            shareRequestParam.setToken(str);
            shareRequestParam.setAppKey(this.mAppKey);
            shareRequestParam.setAppPackage(packageName);
            shareRequestParam.setBaseRequest(baseRequest);
            shareRequestParam.setSpecifyTitle("微博分享");
            Bundle bundle = new Bundle();
            bundle = shareRequestParam.createRequestParamBundle();
            shareRequestParam = new ShareRequestParam(activity);
            shareRequestParam.setupRequestParam(bundle);
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    if (activity != null && !activity.isFinishing()) {
                        new ShareDialog(activity, SHARE_MEDIA.SINA, uMShareListener, shareRequestParam).show();
                    }
                }
            });
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }
}
