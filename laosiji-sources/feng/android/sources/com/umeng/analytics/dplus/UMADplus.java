package com.umeng.analytics.dplus;

import android.content.Context;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.pro.h;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.statistics.common.MLog;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class UMADplus {
    public static void track(Context context, String str) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().a(context, str, null);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.S, 0, "\\|");
    }

    public static void track(Context context, String str, Map<String, Object> map) {
        UMLog uMLog;
        if (AnalyticsConfig.FLAG_DPLUS) {
            if (map == null || map.size() <= 0) {
                MLog.e("the map is null!");
                uMLog = UMConfigure.umDebugLog;
                UMLog.aq(h.ae, 0, "\\|");
            }
            MobclickAgent.getAgent().a(context, str, (Map) map);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.S, 0, "\\|");
    }

    public static void registerSuperProperty(Context context, String str, Object obj) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().a(context, str, obj);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.T, 0, "\\|");
    }

    public static void unregisterSuperProperty(Context context, String str) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().d(context, str);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.U, 0, "\\|");
    }

    public static Object getSuperProperty(Context context, String str) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            return MobclickAgent.getAgent().e(context, str);
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.V, 0, "\\|");
        return null;
    }

    public static String getSuperProperties(Context context) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            return MobclickAgent.getAgent().e(context);
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.W, 0, "\\|");
        return null;
    }

    public static void clearSuperProperties(Context context) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().f(context);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.X, 0, "\\|");
    }

    public static void setFirstLaunchEvent(Context context, List<String> list) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().a(context, (List) list);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.Y, 0, "\\|");
    }

    public static void registerPreProperties(Context context, JSONObject jSONObject) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().a(context, jSONObject);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.Z, 0, "\\|");
    }

    public static void unregisterPreProperty(Context context, String str) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().f(context, str);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.aa, 0, "\\|");
    }

    public static void clearPreProperties(Context context) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            MobclickAgent.getAgent().g(context);
            return;
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.ab, 0, "\\|");
    }

    public static JSONObject getPreProperties(Context context) {
        if (AnalyticsConfig.FLAG_DPLUS) {
            return MobclickAgent.getAgent().h(context);
        }
        MLog.e("UMADplus class is Dplus API, can't be use in no-Dplus scenario.");
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.aq(h.ac, 0, "\\|");
        return null;
    }
}
