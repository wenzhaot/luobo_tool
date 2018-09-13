package com.umeng.socialize.net.dplus;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.media.UMEmoji;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONException;
import org.json.JSONObject;

public class DplusApi {
    private static final String FULL = "false";
    private static final String SIMPLE = "true";
    private static ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    public static void uploadDAU(final Context context) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context, SocializeConstants.DAU_EVENT, DplusApi.constructDauContent());
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadStatsDAU(final Context context, final Map<String, String> map, final int i) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context, SocializeConstants.SEND_DAU_STATS_EVENT, DplusApi.constructStatsDauContent(map, i));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadShare(Context context, ShareContent shareContent, boolean z, SHARE_MEDIA share_media, String str, boolean z2) {
        final ShareContent shareContent2 = shareContent;
        final boolean z3 = z;
        final SHARE_MEDIA share_media2 = share_media;
        final String str2 = str;
        final Context context2 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context2, SocializeConstants.SHARE_EVENT, DplusApi.constructShareContent(shareContent2, z3, share_media2, str2));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
        shareContent2 = shareContent;
        z3 = z;
        share_media2 = share_media;
        final boolean z4 = z2;
        final String str3 = str;
        final Context context3 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context3, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsShareContent(shareContent2, z3, share_media2, z4, str3));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadAuth(Context context, Map<String, String> map, boolean z, SHARE_MEDIA share_media, String str) {
        final Map<String, String> map2 = map;
        final boolean z2 = z;
        final SHARE_MEDIA share_media2 = share_media;
        final String str2 = str;
        final Context context2 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context2, SocializeConstants.AUTH_EVENT, DplusApi.constructAuthContent(map2, z2, share_media2, str2));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadAuthStart(final Context context, final boolean z, final SHARE_MEDIA share_media, final String str) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsAuthStartContent(z, share_media, str));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadAuthend(Context context, SHARE_MEDIA share_media, String str, String str2, String str3) {
        final SHARE_MEDIA share_media2 = share_media;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Context context2 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context2, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsAuthEndContent(share_media2, str4, str5, str6));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadStatsShareEnd(Context context, SHARE_MEDIA share_media, String str, String str2, String str3) {
        final SHARE_MEDIA share_media2 = share_media;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Context context2 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context2, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsShareEndContent(share_media2, str4, str5, str6));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadInfoStart(final Context context, final SHARE_MEDIA share_media, final String str) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsInfoStartContent(share_media, str));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadInfoend(Context context, SHARE_MEDIA share_media, String str, String str2, String str3) {
        final SHARE_MEDIA share_media2 = share_media;
        final String str4 = str;
        final String str5 = str2;
        final String str6 = str3;
        final Context context2 = context;
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context2, SocializeConstants.SAVE_STATS_EVENT, DplusApi.constructStatsInfoEndContent(share_media2, str4, str5, str6));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    public static void uploadUserInfo(final Context context, final Map<String, String> map, final SHARE_MEDIA share_media, final String str) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    UMWorkDispatch.sendEvent(context, SocializeConstants.GET_EVENT, DplusApi.constructUserInfoContent(map, share_media, str));
                } catch (Throwable e) {
                    SLog.error(e);
                }
            }
        });
    }

    private static JSONObject constructStatsDauContent(Map<String, String> map, int i) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("name", CommonNetImpl.S_DAU);
        jSONObject.put(CommonNetImpl.A_B, i);
        if (map != null) {
            JSONObject jSONObject2 = new JSONObject();
            if (!(TextUtils.isEmpty((CharSequence) map.get("position")) || TextUtils.isEmpty((CharSequence) map.get(CommonNetImpl.MENUBG)))) {
                jSONObject2.put("position", map.get("position"));
                jSONObject2.put(CommonNetImpl.MENUBG, map.get(CommonNetImpl.MENUBG));
                jSONObject.put(CommonNetImpl.S_I, jSONObject2);
            }
        }
        return jSONObject;
    }

    private static JSONObject constructDauContent() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("ts", System.currentTimeMillis());
        jSONObject.put(CommonNetImpl.SHARETYPE, Config.shareType);
        return jSONObject;
    }

    private static JSONObject constructStatsInfoStartContent(SHARE_MEDIA share_media, String str) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_I_S);
        return constructBaseContent;
    }

    private static JSONObject constructStatsInfoEndContent(SHARE_MEDIA share_media, String str, String str2, String str3) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_I_E);
        constructBaseContent.put("result", str2);
        if (!TextUtils.isEmpty(str3)) {
            constructBaseContent.put(CommonNetImpl.E_M, str3);
        }
        return constructBaseContent;
    }

    private static JSONObject constructStatsShareEndContent(SHARE_MEDIA share_media, String str, String str2, String str3) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_S_E);
        constructBaseContent.put("result", str2);
        if (!TextUtils.isEmpty(str3)) {
            constructBaseContent.put(CommonNetImpl.E_M, str3);
        }
        return constructBaseContent;
    }

    private static JSONObject constructStatsAuthStartContent(boolean z, SHARE_MEDIA share_media, String str) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_A_S);
        constructBaseContent.put(CommonNetImpl.AM, share_media.getsharestyle(z));
        return constructBaseContent;
    }

    private static JSONObject constructStatsAuthEndContent(SHARE_MEDIA share_media, String str, String str2, String str3) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_A_E);
        constructBaseContent.put("result", str2);
        if (!TextUtils.isEmpty(str3)) {
            constructBaseContent.put(CommonNetImpl.E_M, str3);
        }
        return constructBaseContent;
    }

    private static JSONObject constructAuthContent(Map<String, String> map, boolean z, SHARE_MEDIA share_media, String str) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        if (map != null) {
            constructBaseContent.put(CommonNetImpl.AM, share_media.getsharestyle(z));
            if (share_media.toString().equals("WEIXIN")) {
                constructBaseContent.put("uid", map.get("openid"));
            } else {
                constructBaseContent.put("uid", map.get("uid"));
            }
            constructBaseContent.put(CommonNetImpl.UNIONID, map.get(CommonNetImpl.UNIONID));
            constructBaseContent.put("aid", map.get("aid"));
            constructBaseContent.put("as", map.get("as"));
            if (TextUtils.isEmpty((CharSequence) map.get("access_token"))) {
                constructBaseContent.put("at", map.get("accessToken"));
            } else {
                constructBaseContent.put("at", map.get("access_token"));
            }
        }
        return constructBaseContent;
    }

    private static JSONObject constructUserInfoContent(Map<String, String> map, SHARE_MEDIA share_media, String str) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        if (map != null) {
            constructBaseContent.put(CommonNetImpl.UN, map.get("name"));
            constructBaseContent.put(CommonNetImpl.UP, map.get("iconurl"));
            constructBaseContent.put("sex", map.get("gender"));
            if (TextUtils.isEmpty((CharSequence) map.get("location"))) {
                constructBaseContent.put(CommonNetImpl.REGION, map.get("city"));
            } else {
                constructBaseContent.put(CommonNetImpl.REGION, map.get("location"));
            }
            if (share_media.toString().equals("WEIXIN")) {
                constructBaseContent.put("uid", map.get("openid"));
            } else {
                constructBaseContent.put("uid", map.get("uid"));
            }
            constructBaseContent.put(CommonNetImpl.UNIONID, map.get(CommonNetImpl.UNIONID));
            constructBaseContent.put("ts", System.currentTimeMillis());
        }
        return constructBaseContent;
    }

    private static JSONObject constructStatsShareContent(ShareContent shareContent, boolean z, SHARE_MEDIA share_media, boolean z2, String str) throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        constructBaseContent.put("name", CommonNetImpl.S_S_S);
        constructBaseContent.put(CommonNetImpl.U_C, z2 + "");
        constructBaseContent.put(CommonNetImpl.SM, share_media.getsharestyle(z));
        constructBaseContent.put(CommonNetImpl.STYPE, shareContent.getShareType());
        return constructBaseContent;
    }

    public static JSONObject getFakeData() throws JSONException {
        JSONObject constructBaseContent = constructBaseContent(SHARE_MEDIA.SINA, "test");
        constructBaseContent.put("name", "testetstttttttttttttttttttttttttttttttt");
        constructBaseContent.put(CommonNetImpl.U_C, true);
        constructBaseContent.put(CommonNetImpl.SM, "sso");
        constructBaseContent.put(CommonNetImpl.STYPE, 0);
        return constructBaseContent;
    }

    private static JSONObject constructBaseContent(SHARE_MEDIA share_media, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        String share_media2 = share_media.toString();
        jSONObject.put(CommonNetImpl.PF, share_media.getName());
        if ((share_media2.equals(SHARE_MEDIA.QQ.toString()) || share_media2.equals(SHARE_MEDIA.QZONE.toString())) && Config.isUmengQQ.booleanValue()) {
            jSONObject.put(CommonNetImpl.SDKT, "true");
        } else if ((share_media2.equals(SHARE_MEDIA.WEIXIN.toString()) || share_media2.equals(SHARE_MEDIA.WEIXIN_CIRCLE.toString()) || share_media2.equals(SHARE_MEDIA.WEIXIN_FAVORITE.toString())) && Config.isUmengWx.booleanValue()) {
            jSONObject.put(CommonNetImpl.SDKT, "true");
        } else if (share_media2.equals(SHARE_MEDIA.SINA.toString()) && Config.isUmengSina.booleanValue()) {
            jSONObject.put(CommonNetImpl.SDKT, "true");
        } else {
            jSONObject.put(CommonNetImpl.SDKT, "false");
        }
        jSONObject.put("ts", System.currentTimeMillis());
        jSONObject.put(CommonNetImpl.TAG, str);
        return jSONObject;
    }

    private static JSONObject constructShareContent(ShareContent shareContent, boolean z, SHARE_MEDIA share_media, String str) throws JSONException {
        Object substring;
        JSONObject constructBaseContent = constructBaseContent(share_media, str);
        String str2 = shareContent.mText;
        constructBaseContent.put(CommonNetImpl.STYPE, shareContent.getShareType());
        constructBaseContent.put(CommonNetImpl.SM, share_media.getsharestyle(z));
        if (TextUtils.isEmpty(str2) || str2.length() <= 10240) {
            String substring2 = str2;
        } else {
            substring2 = str2.substring(0, 10240);
        }
        if (shareContent.getShareType() == 2 || shareContent.getShareType() == 3) {
            UMImage uMImage = (UMImage) shareContent.mMedia;
            if (uMImage != null) {
                if (uMImage.isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMImage.asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.hexdigest(SocializeUtils.md5(uMImage.toByte())));
                }
            }
            constructBaseContent.put("ct", substring2);
        } else if (shareContent.getShareType() == 1) {
            constructBaseContent.put("ct", substring2);
        } else if (shareContent.getShareType() == 8) {
            UMVideo uMVideo = (UMVideo) shareContent.mMedia;
            if (uMVideo.getThumbImage() != null) {
                if (uMVideo.getThumbImage().isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMVideo.getThumbImage().asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.hexdigest(SocializeUtils.md5(uMVideo.getThumbImage().toByte())));
                }
            }
            if (share_media == SHARE_MEDIA.SINA) {
                constructBaseContent.put("ct", substring2);
            } else {
                constructBaseContent.put("ct", uMVideo.getDescription());
            }
            constructBaseContent.put("title", uMVideo.getTitle());
            constructBaseContent.put("url", uMVideo.toUrl());
        } else if (shareContent.getShareType() == 4) {
            UMusic uMusic = (UMusic) shareContent.mMedia;
            if (uMusic.getThumbImage() != null) {
                if (uMusic.getThumbImage().isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMusic.getThumbImage().asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.hexdigest(SocializeUtils.md5(uMusic.getThumbImage().toByte())));
                }
            }
            if (share_media == SHARE_MEDIA.SINA) {
                constructBaseContent.put("ct", substring2);
            } else {
                constructBaseContent.put("ct", uMusic.getDescription());
            }
            constructBaseContent.put("title", uMusic.getTitle());
            constructBaseContent.put(CommonNetImpl.DURL, uMusic.toUrl());
            constructBaseContent.put("url", uMusic.getmTargetUrl());
        } else if (shareContent.getShareType() == 32) {
            constructBaseContent.put("ct", substring2);
        } else if (shareContent.getShareType() == 64) {
            UMEmoji uMEmoji = (UMEmoji) shareContent.mMedia;
            if (uMEmoji != null) {
                if (uMEmoji.isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMEmoji.asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.md5(uMEmoji.toByte()));
                }
            }
            constructBaseContent.put("ct", substring2);
        } else if (shareContent.getShareType() == 128) {
            UMMin uMMin = (UMMin) shareContent.mMedia;
            if (uMMin.getThumbImage() != null) {
                if (uMMin.getThumbImage().isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMMin.getThumbImage().asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.hexdigest(SocializeUtils.md5(uMMin.getThumbImage().toByte())));
                }
            }
            constructBaseContent.put("ct", uMMin.getDescription());
            constructBaseContent.put("title", uMMin.getTitle());
            constructBaseContent.put("url", uMMin.toUrl());
            constructBaseContent.put(CommonNetImpl.M_P, uMMin.getPath());
            constructBaseContent.put(CommonNetImpl.M_U, uMMin.getUserName());
        } else if (shareContent.getShareType() == 16) {
            UMWeb uMWeb = (UMWeb) shareContent.mMedia;
            if (uMWeb.getThumbImage() != null) {
                if (uMWeb.getThumbImage().isUrlMedia()) {
                    constructBaseContent.put(CommonNetImpl.PICURL, uMWeb.getThumbImage().asUrlImage());
                } else {
                    constructBaseContent.put("pic", SocializeUtils.hexdigest(SocializeUtils.md5(uMWeb.getThumbImage().toByte())));
                }
            }
            if (share_media == SHARE_MEDIA.SINA) {
                constructBaseContent.put("ct", substring2);
            } else {
                constructBaseContent.put("ct", uMWeb.getDescription());
            }
            constructBaseContent.put("title", uMWeb.getTitle());
            constructBaseContent.put("url", uMWeb.toUrl());
        }
        return constructBaseContent;
    }
}
