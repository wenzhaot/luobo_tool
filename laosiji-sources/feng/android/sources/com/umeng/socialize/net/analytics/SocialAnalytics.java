package com.umeng.socialize.net.analytics;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.base.SocializeClient;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.uploadlog.UMLog;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeSpUtils;
import com.umeng.socialize.utils.UmengText.NET;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SocialAnalytics {
    private static SocializeClient a = new SocializeClient();
    private static ExecutorService b = Executors.newCachedThreadPool();

    public static void log(final Context context, final String str, final String str2, final UMediaObject uMediaObject) {
        a(new Runnable() {
            public void run() {
                URequest analyticsReqeust = new AnalyticsReqeust(context, str, str2);
                analyticsReqeust.setMedia(uMediaObject);
                analyticsReqeust.setReqType(1);
                AnalyticsResponse analyticsResponse = (AnalyticsResponse) SocialAnalytics.a.execute(analyticsReqeust);
                if (analyticsResponse == null || !analyticsResponse.isOk()) {
                    SLog.debug(NET.SHARESELFFAIL);
                } else {
                    SLog.debug(NET.SHARESELFOK);
                }
            }
        });
    }

    public static void authstart(Context context, SHARE_MEDIA share_media, String str, boolean z, String str2) {
        DplusApi.uploadAuthStart(context, z, share_media, str2);
    }

    public static void authendt(Context context, SHARE_MEDIA share_media, String str, boolean z, String str2, String str3, Map<String, String> map) {
        DplusApi.uploadAuthend(context, share_media, str3, str, str2);
        if (map != null) {
            DplusApi.uploadAuth(context, map, z, share_media, str3);
        }
    }

    public static void shareend(Context context, SHARE_MEDIA share_media, String str, String str2, String str3) {
        DplusApi.uploadStatsShareEnd(context, share_media, str3, str, str2);
    }

    public static void getInfostart(Context context, SHARE_MEDIA share_media, String str) {
        DplusApi.uploadInfoStart(context, share_media, str);
    }

    public static void getInfoendt(Context context, SHARE_MEDIA share_media, String str, String str2, String str3, Map<String, String> map) {
        DplusApi.uploadInfoend(context, share_media, str3, str, str2);
        if (map != null) {
            DplusApi.uploadUserInfo(context, map, share_media, str3);
        }
    }

    public static void dauStats(Context context, boolean z) {
        int i;
        int i2;
        int i3;
        Bundle shareAndAuth = UMLog.getShareAndAuth();
        int i4 = shareAndAuth.getBoolean("share") ? CommonNetImpl.FLAG_SHARE : 0;
        if (shareAndAuth.getBoolean("auth")) {
            i = CommonNetImpl.FLAG_AUTH;
        } else {
            i = 0;
        }
        if (UMLog.isOpenShareEdit()) {
            i2 = CommonNetImpl.FLAG_SHARE_EDIT;
        } else {
            i2 = 0;
        }
        if (shareAndAuth.getBoolean("isjump")) {
            i3 = CommonNetImpl.FLAG_SHARE_JUMP;
        } else {
            i3 = 0;
        }
        i4 = ((i4 | i) | i3) | i2;
        Object shareBoardConfig = SocializeSpUtils.getShareBoardConfig(context);
        Map hashMap = new HashMap();
        if (TextUtils.isEmpty(shareBoardConfig)) {
            DplusApi.uploadStatsDAU(context, null, i4);
            return;
        }
        String[] split = shareBoardConfig.split(";");
        if (split.length == 2) {
            CharSequence charSequence = split[0];
            CharSequence charSequence2 = split[1];
            if (!TextUtils.isEmpty(charSequence) && !TextUtils.isEmpty(charSequence2)) {
                hashMap.put("position", split[1]);
                hashMap.put(CommonNetImpl.MENUBG, split[0]);
                DplusApi.uploadStatsDAU(context, hashMap, i4);
            }
        }
    }

    private static void a(Runnable runnable) {
        if (b != null && runnable != null) {
            b.execute(runnable);
        }
    }
}
