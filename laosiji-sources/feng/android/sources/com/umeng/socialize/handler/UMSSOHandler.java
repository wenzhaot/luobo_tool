package com.umeng.socialize.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StringName;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CHECK;
import com.umeng.socialize.utils.UmengText.INTER;
import java.lang.ref.WeakReference;
import java.util.Map;

public abstract class UMSSOHandler {
    protected static final String ACCESSTOKEN = "accessToken";
    protected static final String ACCESS_SECRET = "access_secret";
    protected static final String ACCESS_TOKEN = "access_token";
    protected static final String CITY = "city";
    protected static final String COUNTRY = "country";
    protected static final String EMAIL = "email";
    protected static final String EXPIRATION = "expiration";
    protected static final String EXPIRES_IN = "expires_in";
    protected static final String FIRST_NAME = "first_name";
    protected static final String GENDER = "gender";
    protected static final String ICON = "iconurl";
    protected static final String ID = "id";
    protected static final String JSON = "json";
    protected static final String LAST_NAME = "last_name";
    protected static final String MIDDLE_NAME = "middle_name";
    protected static final String NAME = "name";
    protected static final String OPENID = "openid";
    @Deprecated
    protected static final String PROFILE_IMAGE_URL = "profile_image_url";
    protected static final String PROVINCE = "province";
    protected static final String REFRESHTOKEN = "refreshToken";
    protected static final String REFRESH_TOKEN = "refresh_token";
    protected static final String REGION = "region";
    @Deprecated
    protected static final String SCREEN_NAME = "screen_name";
    protected static final String UID = "uid";
    protected static final String UNIONID = "unionid";
    protected static final String USID = "usid";
    private static final UMShareConfig mDefaultShareConfig = new UMShareConfig();
    protected String VERSION = "";
    private boolean isInit = false;
    private Platform mConfig = null;
    private Context mContext = null;
    protected UMShareConfig mShareConfig;
    protected int mThumbLimit = 32768;
    protected WeakReference<Activity> mWeakAct;

    public abstract String getVersion();

    public abstract boolean share(ShareContent shareContent, UMShareListener uMShareListener);

    public void onCreate(Context context, Platform platform) {
        this.mContext = ContextUtil.getContext();
        this.mConfig = platform;
        if (context instanceof Activity) {
            this.mWeakAct = new WeakReference((Activity) context);
        }
        if (!this.isInit) {
            SLog.mutlI(INTER.PINFO, INTER.getVersion(platform.getName().getName()) + getVersion(), INTER.HANDLERID + toString());
            this.isInit = true;
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public Platform getConfig() {
        return this.mConfig;
    }

    public final void setShareConfig(UMShareConfig uMShareConfig) {
        this.mShareConfig = uMShareConfig;
    }

    protected final UMShareConfig getShareConfig() {
        if (this.mShareConfig == null) {
            return mDefaultShareConfig;
        }
        return this.mShareConfig;
    }

    public void authorize(UMAuthListener uMAuthListener) {
    }

    public void deleteAuth(UMAuthListener uMAuthListener) {
    }

    public void setAuthListener(UMAuthListener uMAuthListener) {
    }

    public boolean isHasAuthListener() {
        return true;
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onResume() {
    }

    public void getPlatformInfo(UMAuthListener uMAuthListener) {
    }

    public boolean isInstall() {
        SLog.E(CHECK.NO_SUPPORT_INSTALL);
        return true;
    }

    public boolean isSupport() {
        SLog.E(CHECK.NO_SUPPORT_SDKL);
        return true;
    }

    public boolean isAuthorize() {
        SLog.E(CHECK.NO_SUPPORT_AUTH);
        return true;
    }

    public String getSDKVersion() {
        return "";
    }

    public int getRequestCode() {
        return 0;
    }

    public boolean isSupportAuth() {
        return false;
    }

    public void release() {
    }

    public String getGender(Object obj) {
        String str = StringName.male;
        String str2 = StringName.female;
        if (obj == null) {
            return "";
        }
        if (obj instanceof String) {
            if (obj.equals("m") || obj.equals(PushConstants.PUSH_TYPE_THROUGH_MESSAGE) || obj.equals("男")) {
                return str;
            }
            if (obj.equals("f") || obj.equals(PushConstants.PUSH_TYPE_NOTIFY) || obj.equals("女")) {
                return str2;
            }
            return obj.toString();
        } else if (!(obj instanceof Integer)) {
            return obj.toString();
        } else {
            if (((Integer) obj).intValue() == 1) {
                return str;
            }
            if (((Integer) obj).intValue() == 0) {
                return str2;
            }
            return obj.toString();
        }
    }

    public UMShareListener getShareListener(UMShareListener uMShareListener) {
        return uMShareListener != null ? uMShareListener : new UMShareListener() {
            public void onStart(SHARE_MEDIA share_media) {
            }

            public void onResult(SHARE_MEDIA share_media) {
                SLog.E(CHECK.LISTENRNULL);
            }

            public void onError(SHARE_MEDIA share_media, Throwable th) {
                SLog.E(CHECK.LISTENRNULL);
            }

            public void onCancel(SHARE_MEDIA share_media) {
                SLog.E(CHECK.LISTENRNULL);
            }
        };
    }

    public UMAuthListener getAuthListener(UMAuthListener uMAuthListener) {
        return uMAuthListener != null ? uMAuthListener : new UMAuthListener() {
            public void onStart(SHARE_MEDIA share_media) {
            }

            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                SLog.E(CHECK.LISTENRNULL);
            }

            public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                SLog.E(CHECK.LISTENRNULL);
            }

            public void onCancel(SHARE_MEDIA share_media, int i) {
                SLog.E(CHECK.LISTENRNULL);
            }
        };
    }
}
