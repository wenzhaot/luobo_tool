package anetwork.channel.config;

import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import anet.channel.util.ALog;
import anet.channel.util.b;
import anetwork.channel.cache.CacheManager;
import anetwork.channel.http.NetworkSdkSetting;

/* compiled from: Taobao */
public class NetworkConfigCenter {
    private static final String CACHE_FLAG = "Cache.Flag";
    private static volatile long cacheFlag = 0;
    private static volatile IRemoteConfig iRemoteConfig;
    private static volatile boolean isAllowHttpIpRetry = false;
    private static volatile boolean isHttpCacheEnable = true;
    private static volatile boolean isHttpSessionEnable = true;
    private static volatile boolean isRemoteNetworkServiceEnable = true;
    private static volatile boolean isSSLEnabled = true;
    private static volatile boolean isSpdyEnabled = true;

    public static void init() {
        iRemoteConfig = new OrangeConfigImpl();
        iRemoteConfig.register();
        cacheFlag = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).getLong(CACHE_FLAG, 0);
    }

    public static void setSSLEnabled(boolean z) {
        isSSLEnabled = z;
    }

    public static boolean isSSLEnabled() {
        return isSSLEnabled;
    }

    public static void setSpdyEnabled(boolean z) {
        isSpdyEnabled = z;
    }

    public static boolean isSpdyEnabled() {
        return isSpdyEnabled;
    }

    public static void setHttpsValidationEnabled(boolean z) {
        if (z) {
            b.a(null);
            b.a(null);
            return;
        }
        b.a(b.ALLOW_ALL_HOSTNAME_VERIFIER);
        b.a(b.TRUST_ALL_SSL_SOCKET_FACTORY);
    }

    public static void setRemoteNetworkServiceEnable(boolean z) {
        isRemoteNetworkServiceEnable = z;
    }

    public static boolean isRemoteNetworkServiceEnable() {
        return isRemoteNetworkServiceEnable;
    }

    public static void setRemoteConfig(IRemoteConfig iRemoteConfig) {
        if (iRemoteConfig != null) {
            iRemoteConfig.unRegister();
        }
        if (iRemoteConfig != null) {
            iRemoteConfig.register();
        }
        iRemoteConfig = iRemoteConfig;
    }

    public static boolean isHttpSessionEnable() {
        return isHttpSessionEnable;
    }

    public static void setHttpSessionEnable(boolean z) {
        isHttpSessionEnable = z;
    }

    public static boolean isAllowHttpIpRetry() {
        return isHttpSessionEnable && isAllowHttpIpRetry;
    }

    public static void setAllowHttpIpRetry(boolean z) {
        isAllowHttpIpRetry = z;
    }

    public static boolean isHttpCacheEnable() {
        return isHttpCacheEnable;
    }

    public static void setHttpCacheEnable(boolean z) {
        isHttpCacheEnable = z;
    }

    public static void setCacheFlag(long j) {
        if (j != cacheFlag) {
            ALog.i("anet.NetworkConfigCenter", "set cache flag", null, "old", Long.valueOf(cacheFlag), "new", Long.valueOf(j));
            cacheFlag = j;
            Editor edit = PreferenceManager.getDefaultSharedPreferences(NetworkSdkSetting.getContext()).edit();
            edit.putLong(CACHE_FLAG, cacheFlag);
            edit.apply();
            CacheManager.clearAllCache();
        }
    }
}
