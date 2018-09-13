package anetwork.channel.config;

import anet.channel.a;
import anet.channel.util.ALog;
import anetwork.channel.statist.StatisticReqTimes;
import anetwork.channel.util.RequestConstant;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListenerV1;

/* compiled from: Taobao */
class OrangeConfigImpl implements IRemoteConfig {
    private static final String NETWORK_ACCS_SESSION_BG_SWITCH = "network_accs_session_bg_switch";
    private static final String NETWORK_EMPTY_SCHEME_HTTPS_SWITCH = "network_empty_scheme_https_switch";
    private static final String NETWORK_HTTPS_SNI_ENABLE_SWITCH = "network_https_sni_enable_switch";
    private static final String NETWORK_HTTP_CACHE_FLAG = "network_http_cache_flag";
    private static final String NETWORK_HTTP_CACHE_SWITCH = "network_http_cache_switch";
    private static final String NETWORK_MONITOR_WHITELIST_URL = "network_monitor_whitelist_url";
    private static final String NETWORK_SDK_GROUP = "networkSdk";
    private static final String NETWORK_SPDY_ENABLE_SWITCH = "network_spdy_enable_switch";
    private static final String TAG = "awcn.OrangeConfigImpl";
    private static boolean mOrangeValid;

    OrangeConfigImpl() {
    }

    static {
        mOrangeValid = false;
        try {
            Class.forName("com.taobao.orange.OrangeConfig");
            mOrangeValid = true;
        } catch (Exception e) {
            mOrangeValid = false;
        }
    }

    public void register() {
        if (mOrangeValid) {
            try {
                OrangeConfig.getInstance().registerListener(new String[]{NETWORK_SDK_GROUP}, new OrangeConfigListenerV1() {
                    public void onConfigUpdate(String str, boolean z) {
                        OrangeConfigImpl.this.onConfigUpdate(str);
                    }
                });
                getConfig(NETWORK_SDK_GROUP, NETWORK_EMPTY_SCHEME_HTTPS_SWITCH, RequestConstant.TURE);
                StatisticReqTimes.getIntance().updateWhiteReqUrls(getConfig(NETWORK_SDK_GROUP, NETWORK_MONITOR_WHITELIST_URL, null));
                return;
            } catch (Throwable e) {
                ALog.e(TAG, "register fail", null, e, new Object[0]);
                return;
            }
        }
        ALog.w(TAG, "no orange sdk", null, new Object[0]);
    }

    public void unRegister() {
        if (mOrangeValid) {
            OrangeConfig.getInstance().unregisterListener(new String[]{NETWORK_SDK_GROUP});
            return;
        }
        ALog.w(TAG, "no orange sdk", null, new Object[0]);
    }

    public String getConfig(String... strArr) {
        String str = null;
        if (mOrangeValid) {
            try {
                return OrangeConfig.getInstance().getConfig(strArr[0], strArr[1], strArr[2]);
            } catch (Throwable e) {
                ALog.e(TAG, "get config failed!", str, e, new Object[0]);
                return str;
            }
        }
        ALog.w(TAG, "no orange sdk", str, new Object[0]);
        return str;
    }

    public void onConfigUpdate(String str) {
        if (NETWORK_SDK_GROUP.equals(str)) {
            ALog.i(TAG, "onConfigUpdate", null, "namespace", str);
            try {
                a.a.a(Boolean.valueOf(getConfig(str, NETWORK_EMPTY_SCHEME_HTTPS_SWITCH, RequestConstant.TURE)).booleanValue());
            } catch (Exception e) {
            }
            try {
                NetworkConfigCenter.setSpdyEnabled(Boolean.valueOf(getConfig(str, NETWORK_SPDY_ENABLE_SWITCH, RequestConstant.TURE)).booleanValue());
            } catch (Exception e2) {
            }
            try {
                NetworkConfigCenter.setHttpCacheEnable(Boolean.valueOf(getConfig(str, NETWORK_HTTP_CACHE_SWITCH, RequestConstant.TURE)).booleanValue());
            } catch (Exception e3) {
            }
            try {
                String config = getConfig(str, NETWORK_HTTP_CACHE_FLAG, null);
                if (config != null) {
                    NetworkConfigCenter.setCacheFlag(Long.valueOf(config).longValue());
                }
            } catch (Exception e4) {
            }
            try {
                a.b(Boolean.valueOf(Boolean.valueOf(getConfig(str, NETWORK_HTTPS_SNI_ENABLE_SWITCH, RequestConstant.TURE)).booleanValue()).booleanValue());
            } catch (Exception e5) {
            }
            try {
                a.a(Boolean.valueOf(getConfig(str, NETWORK_ACCS_SESSION_BG_SWITCH, RequestConstant.TURE)).booleanValue());
            } catch (Exception e6) {
            }
            StatisticReqTimes.getIntance().updateWhiteReqUrls(getConfig(NETWORK_SDK_GROUP, NETWORK_MONITOR_WHITELIST_URL, null));
        }
    }
}
