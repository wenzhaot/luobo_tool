package com.umeng.qq.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.proguard.l;
import com.umeng.qq.tencent.IUiListener;
import com.umeng.qq.tencent.Tencent;
import com.umeng.qq.tencent.UiError;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.QQ;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UmengQQHandler extends UmengQBaseHandler {
    private final String UNIONID_PARAM = "&unionid=1";
    private final String UNIONID_REQUEST_URL = "https://graph.qq.com/oauth2.0/me?access_token=";
    private IUiListener mShareListener;
    private UmengQQPreferences qqPreferences;

    public void onCreate(Context context, Platform p) {
        super.onCreate(context, p);
        if (context != null) {
            this.qqPreferences = new UmengQQPreferences(getContext(), SHARE_MEDIA.QQ.toString());
        }
    }

    public boolean share(ShareContent content, final UMShareListener listener) {
        UmengQQShareContent shareContent = new UmengQQShareContent(content);
        if (this.mShareConfig != null) {
            shareContent.setCompressListener(this.mShareConfig.getCompressListener());
        }
        if (this.mTencent == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    UmengQQHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QQ, new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.tencentEmpty(Config.isUmengQQ.booleanValue())));
                }
            });
            return false;
        }
        this.mShareListener = getQQSharelistener(listener);
        if (!isInstall()) {
            if (Config.isJumptoAppStore) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("http://log.umsns.com/link/qq/download/"));
                ((Activity) this.mWeakAct.get()).startActivity(intent);
            }
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    UmengQQHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QQ, new Throwable(UmengErrorCode.NotInstall.getMessage()));
                }
            });
        }
        Bundle bundle = shareContent.getBundle(getShareConfig().isHideQzoneOnQQFriendList(), getShareConfig().getAppName());
        final String error = bundle.getString("error");
        if (TextUtils.isEmpty(error)) {
            if (!(this.mWeakAct.get() == null || ((Activity) this.mWeakAct.get()).isFinishing())) {
                this.mTencent.shareToQQ((Activity) this.mWeakAct.get(), bundle, this.mShareListener);
            }
            return true;
        }
        QueuedWork.runInMain(new Runnable() {
            public void run() {
                UmengQQHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QQ, new Throwable(error));
            }
        });
        return false;
    }

    public boolean isAuthorize() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.isAuthValid();
        }
        return false;
    }

    private IUiListener getQQSharelistener(final UMShareListener listener) {
        return new IUiListener() {
            public void onError(final UiError e) {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        UmengQQHandler.this.getShareListener(listener).onError(SHARE_MEDIA.QQ, new Throwable(UmengErrorCode.ShareFailed.getMessage() + (e == null ? "" : e.errorMessage)));
                    }
                });
            }

            public void onCancel() {
                UmengQQHandler.this.getShareListener(listener).onCancel(SHARE_MEDIA.QQ);
            }

            public void onComplete(Object response) {
                UmengQQHandler.this.getShareListener(listener).onResult(SHARE_MEDIA.QQ);
            }
        };
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10103) {
            Tencent.onActivityResultData(requestCode, resultCode, data, this.mShareListener);
        }
        if (requestCode == 11101) {
            Tencent.onActivityResultData(requestCode, resultCode, data, getQQAuthlistener(this.mAuthListener));
        }
    }

    private IUiListener getQQAuthlistener(final UMAuthListener listener) {
        return new IUiListener() {
            public void onError(UiError e) {
                UmengQQHandler.this.getAuthListener(listener).onError(SHARE_MEDIA.QQ, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + "==> errorCode = " + e.errorCode + ", errorMsg = " + e.errorMessage + ", detail = " + e.errorDetail));
            }

            public void onCancel() {
                UmengQQHandler.this.getAuthListener(listener).onCancel(SHARE_MEDIA.QQ, 0);
            }

            public void onComplete(final Object response) {
                SocializeUtils.safeCloseDialog(UmengQQHandler.this.mProgressDialog);
                final Bundle values = UmengQQHandler.this.parseOauthData(response);
                if (UmengQQHandler.this.qqPreferences == null && UmengQQHandler.this.getContext() != null) {
                    UmengQQHandler.this.qqPreferences = new UmengQQPreferences(UmengQQHandler.this.getContext(), SHARE_MEDIA.QQ.toString());
                }
                if (UmengQQHandler.this.qqPreferences != null) {
                    UmengQQHandler.this.qqPreferences.setAuthData(values).commit();
                }
                QueuedWork.runInBack(new Runnable() {
                    public void run() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("https://graph.qq.com/oauth2.0/me?access_token=").append(UmengQQHandler.this.getmAccessToken()).append("&unionid=1");
                        String result = UmengQQHandler.this.getUnionIdRequest(stringBuilder.toString());
                        if (!TextUtils.isEmpty(result)) {
                            try {
                                JSONObject jsonObject = new JSONObject(result.replace("callback", "").replace(l.s, "").replace(l.t, ""));
                                String unionid = jsonObject.optString(CommonNetImpl.UNIONID);
                                String openid = jsonObject.optString("openid");
                                if (UmengQQHandler.this.qqPreferences != null) {
                                    UmengQQHandler.this.qqPreferences.setmOpenid(openid);
                                    UmengQQHandler.this.qqPreferences.setUnionid(unionid);
                                    UmengQQHandler.this.qqPreferences.commit();
                                }
                                String errormessage = jsonObject.optString("error_description");
                                if (!TextUtils.isEmpty(errormessage)) {
                                    SLog.E(QQ.ERRORINFO + errormessage);
                                }
                            } catch (JSONException e) {
                                SLog.error(e);
                            }
                        }
                        UmengQQHandler.this.initOpenidAndToken((JSONObject) response);
                        final Map<String, String> map = SocializeUtils.bundleTomap(values);
                        map.put(CommonNetImpl.UNIONID, UmengQQHandler.this.getUnionid());
                        QueuedWork.runInMain(new Runnable() {
                            public void run() {
                                UmengQQHandler.this.getAuthListener(listener).onComplete(SHARE_MEDIA.QQ, 0, map);
                            }
                        });
                        if (UmengQQHandler.this.config != null) {
                            map.put("aid", UmengQQHandler.this.config.appId);
                            map.put("as", UmengQQHandler.this.config.appkey);
                        }
                    }
                }, true);
            }
        };
    }

    private void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString("access_token");
            String expires = jsonObject.getString("expires_in");
            String openId = jsonObject.getString("openid");
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires) && !TextUtils.isEmpty(openId)) {
                this.mTencent.setAccessToken(token, expires);
                this.mTencent.setOpenId(openId);
            }
        } catch (Exception e) {
            SLog.error(QQ.OPENIDANDTOKEN, e);
        }
    }

    public void setAuthListener(UMAuthListener listener) {
        this.mAuthListener = listener;
    }

    public boolean isHasAuthListener() {
        return this.mAuthListener != null;
    }

    public void authorize(final UMAuthListener listener) {
        this.mAuthListener = listener;
        if (this.mTencent == null) {
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    UmengQQHandler.this.getAuthListener(listener).onError(SHARE_MEDIA.QQ, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + UmengText.tencentEmpty(Config.isUmengQQ.booleanValue())));
                }
            });
        }
        loginDeal();
    }

    public boolean isInstall() {
        if (this.mTencent.isSupportSSOLogin((Activity) this.mWeakAct.get())) {
            return true;
        }
        return false;
    }

    private void loginDeal() {
        if (!isInstall()) {
            if (Config.isJumptoAppStore) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(SocializeConstants.DOWN_URL_QQ));
                ((Activity) this.mWeakAct.get()).startActivity(intent);
            }
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    UmengQQHandler.this.getAuthListener(UmengQQHandler.this.mAuthListener).onError(SHARE_MEDIA.QQ, 0, new Throwable(UmengErrorCode.NotInstall.getMessage()));
                }
            });
        } else if (this.mWeakAct.get() != null && !((Activity) this.mWeakAct.get()).isFinishing()) {
            this.mTencent.login((Activity) this.mWeakAct.get(), "all", getQQAuthlistener(this.mAuthListener));
        }
    }

    private void fetchUserInfo(final UMAuthListener listener) {
        QueuedWork.runInBack(new Runnable() {
            public void run() {
                try {
                    JSONObject jsonObject = UmengQQHandler.this.request();
                    final Map<String, String> infos = new HashMap();
                    infos.put("screen_name", jsonObject.optString(HttpConstant.NICKNAME));
                    infos.put("name", jsonObject.optString(HttpConstant.NICKNAME));
                    infos.put("gender", UmengQQHandler.this.getGender(jsonObject.optString("gender")));
                    infos.put("profile_image_url", jsonObject.optString("figureurl_qq_2"));
                    infos.put("iconurl", jsonObject.optString("figureurl_qq_2"));
                    infos.put("is_yellow_year_vip", jsonObject.optString("is_yellow_year_vip"));
                    infos.put("yellow_vip_level", jsonObject.optString("yellow_vip_level"));
                    infos.put(SocializeProtocolConstants.PROTOCOL_KEY_MSG, jsonObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_MSG));
                    infos.put("city", jsonObject.optString("city"));
                    infos.put("vip", jsonObject.optString("vip"));
                    infos.put("level", jsonObject.optString("level"));
                    infos.put("ret", jsonObject.optString("ret"));
                    infos.put("province", jsonObject.optString("province"));
                    infos.put("is_yellow_vip", jsonObject.optString("is_yellow_vip"));
                    infos.put("openid", UmengQQHandler.this.getuid());
                    infos.put("uid", UmengQQHandler.this.getuid());
                    infos.put("access_token", UmengQQHandler.this.getmAccessToken());
                    infos.put("expires_in", UmengQQHandler.this.getMtl() + "");
                    infos.put("accessToken", UmengQQHandler.this.getmAccessToken());
                    infos.put("expiration", UmengQQHandler.this.getMtl() + "");
                    infos.put(CommonNetImpl.UNIONID, UmengQQHandler.this.getUnionid());
                    final String ret = (String) infos.get("ret");
                    if (TextUtils.isEmpty(ret) || !ret.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
                        QueuedWork.runInMain(new Runnable() {
                            public void run() {
                                if (TextUtils.isEmpty(ret) || !ret.equals("100030")) {
                                    UmengQQHandler.this.getAuthListener(listener).onError(SHARE_MEDIA.QQ, 2, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + ((String) infos.get(SocializeProtocolConstants.PROTOCOL_KEY_MSG))));
                                    return;
                                }
                                UmengQQHandler.this.qqPreferencesDelete();
                                UmengQQHandler.this.authAndFetchUserInfo(listener);
                            }
                        });
                    } else {
                        QueuedWork.runInMain(new Runnable() {
                            public void run() {
                                UmengQQHandler.this.getAuthListener(listener).onComplete(SHARE_MEDIA.QQ, 2, infos);
                            }
                        });
                    }
                } catch (final JSONException e) {
                    QueuedWork.runInMain(new Runnable() {
                        public void run() {
                            UmengQQHandler.this.getAuthListener(listener).onError(SHARE_MEDIA.QQ, 2, new Throwable(UmengErrorCode.RequestForUserProfileFailed.getMessage() + e.getMessage()));
                        }
                    });
                    SLog.error(e);
                }
            }
        }, false);
    }

    public void getPlatformInfo(UMAuthListener listener) {
        if (!this.qqPreferences.isAuthValid() || getShareConfig().isNeedAuthOnGetUserInfo()) {
            authAndFetchUserInfo(listener);
        } else {
            fetchUserInfo(listener);
        }
    }

    private void authAndFetchUserInfo(final UMAuthListener listener) {
        authorize(new UMAuthListener() {
            public void onStart(SHARE_MEDIA platform) {
            }

            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> map) {
                UmengQQHandler.this.fetchUserInfo(listener);
            }

            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                UmengQQHandler.this.getAuthListener(listener).onError(SHARE_MEDIA.QQ, 2, t);
            }

            public void onCancel(SHARE_MEDIA platform, int action) {
                UmengQQHandler.this.getAuthListener(listener).onCancel(SHARE_MEDIA.QQ, 2);
            }
        });
    }

    public boolean isSupportAuth() {
        return true;
    }

    public void deleteAuth(final UMAuthListener listener) {
        this.mTencent.logout();
        qqPreferencesDelete();
        QueuedWork.runInMain(new Runnable() {
            public void run() {
                UmengQQHandler.this.getAuthListener(listener).onComplete(SHARE_MEDIA.QQ, 1, null);
            }
        });
    }

    public int getRequestCode() {
        return 10103;
    }

    private String getmAccessToken() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.getmAccessToken();
        }
        return "";
    }

    private String getUnionid() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.getUnionid();
        }
        return "";
    }

    private long getMtl() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.getMtl();
        }
        return 0;
    }

    private String getuid() {
        if (this.qqPreferences != null) {
            return this.qqPreferences.getuid();
        }
        return "";
    }

    private void qqPreferencesDelete() {
        if (this.qqPreferences != null) {
            this.qqPreferences.delete();
        }
    }

    private JSONObject request() throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://openmobile.qq.com/user/get_simple_userinfo?status_os=" + VERSION.RELEASE).append("&").append("access_token=" + getmAccessToken()).append("&oauth_consumer_key=" + this.config.appId).append("&format=json&openid=" + getuid()).append("&status_version=" + VERSION.SDK).append("&status_machine=" + getDeviceName()).append("&pf=openmobile_android&sdkp=a&sdkv=3.1.0.lite");
        return new JSONObject(request(stringBuilder.toString()).replace("/n", ""));
    }

    private String getDeviceName() {
        try {
            return URLEncoder.encode(Build.MODEL.replace(" ", "+"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            SLog.error(e);
            return "sm801";
        }
    }

    private String request(String urlStr) {
        String emptyStr = "";
        try {
            URLConnection conn = new URL(urlStr).openConnection();
            if (conn == null) {
                return emptyStr;
            }
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            if (inputStream != null) {
                return convertStreamToString(inputStream);
            }
            return emptyStr;
        } catch (Exception e) {
            SLog.error(e);
            return emptyStr;
        }
    }

    private String getUnionIdRequest(String urlStr) {
        String emptyStr = "";
        try {
            URLConnection conn = new URL(urlStr).openConnection();
            if (conn == null) {
                return emptyStr;
            }
            conn.connect();
            InputStream inputStream = conn.getInputStream();
            if (inputStream != null) {
                return convertStreamToString(inputStream);
            }
            return emptyStr;
        } catch (Exception e) {
            SLog.error(e);
            return emptyStr;
        }
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                String line = reader.readLine();
                if (line != null) {
                    sb.append(line + "/n");
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                        SLog.error(e);
                    }
                }
            } catch (IOException e2) {
                SLog.error(e2);
                try {
                    is.close();
                } catch (IOException e22) {
                    SLog.error(e22);
                }
            } catch (Throwable th) {
                try {
                    is.close();
                } catch (IOException e222) {
                    SLog.error(e222);
                }
                throw th;
            }
        }
        is.close();
        return sb.toString();
    }

    public void release() {
        if (this.mTencent != null) {
            this.mTencent.release();
        }
        this.mTencent = null;
        this.mAuthListener = null;
    }
}
