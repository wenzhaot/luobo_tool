package com.umeng.socialize.sina.params;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.VoiceObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.sina.helper.MD5;
import com.umeng.socialize.sina.message.BaseRequest;
import com.umeng.socialize.sina.util.Utility;
import org.json.JSONException;
import org.json.JSONObject;

public class ShareRequestParam extends BrowserRequestParamBase {
    public static final String REQ_PARAM_AID = "aid";
    public static final String REQ_PARAM_KEY_HASH = "key_hash";
    public static final String REQ_PARAM_PACKAGENAME = "packagename";
    public static final String REQ_PARAM_PICINFO = "picinfo";
    public static final String REQ_PARAM_SOURCE = "source";
    public static final String REQ_PARAM_TITLE = "title";
    public static final String REQ_PARAM_TOKEN = "access_token";
    public static final String REQ_PARAM_VERSION = "version";
    public static final String REQ_UPLOAD_PIC_PARAM_IMG = "img";
    public static final String RESP_UPLOAD_PIC_PARAM_CODE = "code";
    public static final String RESP_UPLOAD_PIC_PARAM_DATA = "data";
    public static final int RESP_UPLOAD_PIC_SUCC_CODE = 1;
    private static final String SHARE_URL = "http://service.weibo.com/share/mobilesdk.php";
    public static final String UPLOAD_PIC_URL = "http://service.weibo.com/share/mobilesdk_uppic.php";
    private String mAppKey;
    private String mAppPackage;
    private String mAuthListenerKey;
    private byte[] mBase64ImgData;
    private BaseRequest mBaseRequest;
    private String mHashKey;
    private String mShareContent;
    private String mToken;
    private UMShareListener shareListener;

    public static class UploadPicResult {
        private int code = -2;
        private String picId;

        private UploadPicResult() {
        }

        public static UploadPicResult parse(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            UploadPicResult uploadPicResult = new UploadPicResult();
            try {
                JSONObject jSONObject = new JSONObject(str);
                uploadPicResult.code = jSONObject.optInt("code", -2);
                uploadPicResult.picId = jSONObject.optString("data", "");
                return uploadPicResult;
            } catch (JSONException e) {
                return uploadPicResult;
            }
        }

        public int getCode() {
            return this.code;
        }

        public String getPicId() {
            return this.picId;
        }
    }

    public ShareRequestParam(Context context) {
        super(context);
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0041 A:{SYNTHETIC, Splitter: B:21:0x0041} */
    /* JADX WARNING: Removed duplicated region for block: B:42:? A:{SYNTHETIC, RETURN, SKIP} */
    /* JADX WARNING: Removed duplicated region for block: B:24:0x0046  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0053 A:{SYNTHETIC, Splitter: B:29:0x0053} */
    private void handleMblogPic(java.lang.String r8, byte[] r9) {
        /*
        r7 = this;
        r0 = android.text.TextUtils.isEmpty(r8);	 Catch:{ SecurityException -> 0x0057 }
        if (r0 != 0) goto L_0x0044;
    L_0x0006:
        r2 = new java.io.File;	 Catch:{ SecurityException -> 0x0057 }
        r2.<init>(r8);	 Catch:{ SecurityException -> 0x0057 }
        r0 = r2.exists();	 Catch:{ SecurityException -> 0x0057 }
        if (r0 == 0) goto L_0x0044;
    L_0x0011:
        r0 = r2.canRead();	 Catch:{ SecurityException -> 0x0057 }
        if (r0 == 0) goto L_0x0044;
    L_0x0017:
        r0 = r2.length();	 Catch:{ SecurityException -> 0x0057 }
        r4 = 0;
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 <= 0) goto L_0x0044;
    L_0x0021:
        r0 = r2.length();	 Catch:{ SecurityException -> 0x0057 }
        r0 = (int) r0;	 Catch:{ SecurityException -> 0x0057 }
        r3 = new byte[r0];	 Catch:{ SecurityException -> 0x0057 }
        r1 = 0;
        r0 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x003d, all -> 0x0050 }
        r0.<init>(r2);	 Catch:{ IOException -> 0x003d, all -> 0x0050 }
        r0.read(r3);	 Catch:{ IOException -> 0x0064, all -> 0x005f }
        r1 = com.umeng.socialize.sina.helper.Base64.encodebyte(r3);	 Catch:{ IOException -> 0x0064, all -> 0x005f }
        r7.mBase64ImgData = r1;	 Catch:{ IOException -> 0x0064, all -> 0x005f }
        if (r0 == 0) goto L_0x003c;
    L_0x0039:
        r0.close();	 Catch:{ Exception -> 0x0059 }
    L_0x003c:
        return;
    L_0x003d:
        r0 = move-exception;
        r0 = r1;
    L_0x003f:
        if (r0 == 0) goto L_0x0044;
    L_0x0041:
        r0.close();	 Catch:{ Exception -> 0x005b }
    L_0x0044:
        if (r9 == 0) goto L_0x003c;
    L_0x0046:
        r0 = r9.length;
        if (r0 <= 0) goto L_0x003c;
    L_0x0049:
        r0 = com.umeng.socialize.sina.helper.Base64.encodebyte(r9);
        r7.mBase64ImgData = r0;
        goto L_0x003c;
    L_0x0050:
        r0 = move-exception;
    L_0x0051:
        if (r1 == 0) goto L_0x0056;
    L_0x0053:
        r1.close();	 Catch:{ Exception -> 0x005d }
    L_0x0056:
        throw r0;	 Catch:{ SecurityException -> 0x0057 }
    L_0x0057:
        r0 = move-exception;
        goto L_0x0044;
    L_0x0059:
        r0 = move-exception;
        goto L_0x003c;
    L_0x005b:
        r0 = move-exception;
        goto L_0x0044;
    L_0x005d:
        r1 = move-exception;
        goto L_0x0056;
    L_0x005f:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x0051;
    L_0x0064:
        r1 = move-exception;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.sina.params.ShareRequestParam.handleMblogPic(java.lang.String, byte[]):void");
    }

    private void handleSharedMessage(Bundle bundle) {
        ImageObject imageObject;
        WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
        weiboMultiMessage.toObject(bundle);
        StringBuilder stringBuilder = new StringBuilder();
        if (weiboMultiMessage.textObject instanceof TextObject) {
            stringBuilder.append(weiboMultiMessage.textObject.text);
        }
        if (weiboMultiMessage.imageObject instanceof ImageObject) {
            imageObject = weiboMultiMessage.imageObject;
            handleMblogPic(imageObject.imagePath, imageObject.imageData);
        }
        if (weiboMultiMessage.mediaObject instanceof TextObject) {
            stringBuilder.append(((TextObject) weiboMultiMessage.mediaObject).text);
        }
        if (weiboMultiMessage.mediaObject instanceof ImageObject) {
            imageObject = (ImageObject) weiboMultiMessage.mediaObject;
            handleMblogPic(imageObject.imagePath, imageObject.imageData);
        }
        if (weiboMultiMessage.mediaObject instanceof WebpageObject) {
            stringBuilder.append(" ").append(((WebpageObject) weiboMultiMessage.mediaObject).actionUrl);
        }
        if (weiboMultiMessage.mediaObject instanceof MusicObject) {
            stringBuilder.append(" ").append(((MusicObject) weiboMultiMessage.mediaObject).actionUrl);
        }
        if (weiboMultiMessage.mediaObject instanceof VideoObject) {
            stringBuilder.append(" ").append(((VideoObject) weiboMultiMessage.mediaObject).actionUrl);
        }
        if (weiboMultiMessage.mediaObject instanceof VoiceObject) {
            stringBuilder.append(" ").append(((VoiceObject) weiboMultiMessage.mediaObject).actionUrl);
        }
        this.mShareContent = stringBuilder.toString();
    }

    private void sendSdkResponse(Activity activity, int i, String str) {
        Bundle extras = activity.getIntent().getExtras();
        if (extras != null) {
            Intent intent = new Intent("com.sina.weibo.sdk.action.ACTION_SDK_REQ_ACTIVITY");
            intent.setFlags(131072);
            intent.setPackage(extras.getString("_weibo_appPackage"));
            intent.putExtras(extras);
            intent.putExtra("_weibo_appPackage", activity.getPackageName());
            intent.putExtra("_weibo_resp_errcode", i);
            intent.putExtra("_weibo_resp_errstr", str);
            try {
                activity.startActivityForResult(intent, HandlerRequestCode.SINA_SHARE_REQUEST_CODE);
            } catch (ActivityNotFoundException e) {
            }
        }
    }

    public WeiboParameters buildUploadPicParam(WeiboParameters weiboParameters) {
        if (hasImage()) {
            weiboParameters.put(REQ_UPLOAD_PIC_PARAM_IMG, new String(this.mBase64ImgData));
        }
        return weiboParameters;
    }

    public String buildUrl(String str) {
        Builder buildUpon = Uri.parse(SHARE_URL).buildUpon();
        buildUpon.appendQueryParameter("title", this.mShareContent);
        buildUpon.appendQueryParameter("version", "0031205000");
        if (!TextUtils.isEmpty(this.mAppKey)) {
            buildUpon.appendQueryParameter("source", this.mAppKey);
        }
        if (!TextUtils.isEmpty(this.mToken)) {
            buildUpon.appendQueryParameter("access_token", this.mToken);
        }
        Object aid = Utility.getAid(this.mContext, this.mAppKey);
        if (!TextUtils.isEmpty(aid)) {
            buildUpon.appendQueryParameter("aid", aid);
        }
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            buildUpon.appendQueryParameter(REQ_PARAM_PACKAGENAME, this.mAppPackage);
        }
        if (!TextUtils.isEmpty(this.mHashKey)) {
            buildUpon.appendQueryParameter(REQ_PARAM_KEY_HASH, this.mHashKey);
        }
        if (!TextUtils.isEmpty(str)) {
            buildUpon.appendQueryParameter(REQ_PARAM_PICINFO, str);
        }
        return buildUpon.build().toString();
    }

    public void execRequest(Activity activity, int i) {
        if (i == 3) {
            sendSdkCancleResponse(activity);
        }
    }

    public String getAppKey() {
        return this.mAppKey;
    }

    public String getAppPackage() {
        return this.mAppPackage;
    }

    public String getAuthListenerKey() {
        return this.mAuthListenerKey;
    }

    public byte[] getBase64ImgData() {
        return this.mBase64ImgData;
    }

    public String getHashKey() {
        return this.mHashKey;
    }

    public String getShareContent() {
        return this.mShareContent;
    }

    public String getToken() {
        return this.mToken;
    }

    public boolean hasImage() {
        return this.mBase64ImgData != null && this.mBase64ImgData.length > 0;
    }

    public void onCreateRequestParamBundle(Bundle bundle) {
        if (this.mBaseRequest != null) {
            this.mBaseRequest.toBundle(bundle);
        }
        if (!TextUtils.isEmpty(this.mAppPackage)) {
            this.mHashKey = MD5.hexdigest(Utility.getSign(this.mContext, this.mAppPackage));
        }
        bundle.putString("access_token", this.mToken);
        bundle.putString("source", this.mAppKey);
        bundle.putString(REQ_PARAM_PACKAGENAME, this.mAppPackage);
        bundle.putString(REQ_PARAM_KEY_HASH, this.mHashKey);
        bundle.putString("_weibo_appPackage", this.mAppPackage);
        bundle.putString("_weibo_appKey", this.mAppKey);
        bundle.putInt("_weibo_flag", 538116905);
        bundle.putString("_weibo_sign", this.mHashKey);
    }

    protected void onSetupRequestParam(Bundle bundle) {
        this.mAppKey = bundle.getString("source");
        this.mAppPackage = bundle.getString(REQ_PARAM_PACKAGENAME);
        this.mHashKey = bundle.getString(REQ_PARAM_KEY_HASH);
        this.mToken = bundle.getString("access_token");
        this.mAuthListenerKey = bundle.getString("key_listener");
        handleSharedMessage(bundle);
        this.mUrl = buildUrl("");
    }

    public void sendSdkCancleResponse(Activity activity) {
        sendSdkResponse(activity, 1, "send cancel!!!");
    }

    public void sendSdkErrorResponse(Activity activity, String str) {
        sendSdkResponse(activity, 2, str);
    }

    public void sendSdkOkResponse(Activity activity) {
        sendSdkResponse(activity, 0, "send ok!!!");
    }

    public void setAppKey(String str) {
        this.mAppKey = str;
    }

    public void setAppPackage(String str) {
        this.mAppPackage = str;
    }

    public void setBaseRequest(BaseRequest baseRequest) {
        this.mBaseRequest = baseRequest;
    }

    public void setToken(String str) {
        this.mToken = str;
    }
}
