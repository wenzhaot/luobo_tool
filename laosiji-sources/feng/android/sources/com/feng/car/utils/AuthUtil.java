package com.feng.car.utils;

import android.app.Activity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

public class AuthUtil {
    public static void doAuth(Activity activity, SHARE_MEDIA platform, UMAuthListener umAuthListener) {
        UMShareAPI.get(activity).doOauthVerify(activity, platform, umAuthListener);
    }

    public static void deleteAuth(Activity activity, SHARE_MEDIA platform, UMAuthListener umAuthListener) {
        UMShareAPI.get(activity).deleteOauth(activity, platform, umAuthListener);
    }

    public static boolean isAuth(Activity activity, SHARE_MEDIA platform) {
        return UMShareAPI.get(activity).isAuthorize(activity, platform);
    }

    public static void getInfo(Activity activity, SHARE_MEDIA platform, UMAuthListener umAuthListener) {
        UMShareAPI.get(activity).getPlatformInfo(activity, platform, umAuthListener);
    }
}
