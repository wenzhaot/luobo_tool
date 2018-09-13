package anet.channel.strategy.dispatch;

import android.os.Build.VERSION;
import android.text.TextUtils;
import anet.channel.GlobalAppRuntimeInfo;
import anet.channel.status.NetworkStatusHelper;
import anet.channel.status.NetworkStatusHelper.NetworkStatus;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import java.util.Map;
import java.util.Set;

/* compiled from: Taobao */
class e {
    public static final String TAG = "amdc.DispatchParamBuilder";

    e() {
    }

    public static Map a(Map<String, Object> map) {
        IAmdcSign b = a.b();
        if (b == null || TextUtils.isEmpty(b.getAppkey())) {
            return null;
        }
        map.put(DispatchConstants.APPKEY, b.getAppkey());
        map.put(DispatchConstants.VERSION, DispatchConstants.VER_CODE);
        map.put(DispatchConstants.PLATFORM, DispatchConstants.ANDROID);
        map.put(DispatchConstants.PLATFORM_VERSION, VERSION.RELEASE);
        if (!TextUtils.isEmpty(GlobalAppRuntimeInfo.getUserId())) {
            map.put(DispatchConstants.SID, GlobalAppRuntimeInfo.getUserId());
        }
        if (!TextUtils.isEmpty(GlobalAppRuntimeInfo.getUtdid())) {
            map.put(DispatchConstants.DEVICEID, GlobalAppRuntimeInfo.getUtdid());
        }
        NetworkStatus a = NetworkStatusHelper.a();
        map.put(DispatchConstants.NET_TYPE, a.toString());
        if (a.isWifi()) {
            map.put(DispatchConstants.BSSID, NetworkStatusHelper.f());
        }
        map.put(DispatchConstants.CARRIER, NetworkStatusHelper.d());
        map.put(DispatchConstants.MNC, NetworkStatusHelper.e());
        map.put(DispatchConstants.LATITUDE, String.valueOf(a.a));
        map.put(DispatchConstants.LONGTITUDE, String.valueOf(a.b));
        c(map);
        map.put(DispatchConstants.DOMAIN, b(map));
        map.put(DispatchConstants.SIGNTYPE, b.useSecurityGuard() ? "sec" : "noSec");
        map.put(DispatchConstants.TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        CharSequence a2 = a(b, map);
        if (TextUtils.isEmpty(a2)) {
            return null;
        }
        map.put(DispatchConstants.SIGN, a2);
        return map;
    }

    private static String b(Map map) {
        Set<String> set = (Set) map.remove(DispatchConstants.HOSTS);
        StringBuilder stringBuilder = new StringBuilder();
        for (String append : set) {
            stringBuilder.append(append).append(' ');
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    private static void c(Map<String, Object> map) {
        try {
            String ttid = GlobalAppRuntimeInfo.getTtid();
            if (!TextUtils.isEmpty(ttid)) {
                int indexOf = ttid.indexOf("@");
                if (indexOf != -1) {
                    map.put(DispatchConstants.CHANNEL, ttid.substring(0, indexOf));
                }
                ttid = ttid.substring(indexOf + 1);
                indexOf = ttid.lastIndexOf("_");
                if (indexOf != -1) {
                    map.put(DispatchConstants.APP_NAME, ttid.substring(0, indexOf));
                    map.put(DispatchConstants.APP_VERSION, ttid.substring(indexOf + 1));
                    return;
                }
                map.put(DispatchConstants.APP_NAME, ttid);
            }
        } catch (Exception e) {
        }
    }

    static String a(IAmdcSign iAmdcSign, Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append(d.c((String) map.get(DispatchConstants.APPKEY))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.DOMAIN))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.APP_NAME))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.APP_VERSION))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.BSSID))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.CHANNEL))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.DEVICEID))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.LATITUDE))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.LONGTITUDE))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.MACHINE))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.NET_TYPE))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.OTHER))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.PLATFORM))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.PLATFORM_VERSION))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.PRE_IP))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.SID))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.TIMESTAMP))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.VERSION))).append(DispatchConstants.SIGN_SPLIT_SYMBOL).append(d.c((String) map.get(DispatchConstants.SIGNTYPE)));
        try {
            return iAmdcSign.sign(stringBuilder.toString());
        } catch (Throwable e) {
            ALog.e(TAG, "get sign failed", null, e, new Object[0]);
            return null;
        }
    }
}
