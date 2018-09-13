package com.taobao.accs.utl;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.ChannelService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListenerV1;
import com.umeng.message.common.inter.ITagManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
public class i {
    public static final String NAMESPACE = "accs";
    private static boolean a;

    /* compiled from: Taobao */
    public static class a implements OrangeConfigListenerV1 {
        public void onConfigUpdate(String str, boolean z) {
            if (GlobalClientInfo.getContext() == null) {
                ALog.e("OrangeAdapter", "onConfigUpdate context null", new Object[0]);
                return;
            }
            try {
                ALog.i("OrangeAdapter", "onConfigUpdate", "namespace", str);
                if (str != null && "accs".equals(str)) {
                    i.f();
                    i.e();
                }
            } catch (Throwable th) {
                ALog.e("OrangeAdapter", "onConfigUpdate", th, new Object[0]);
            }
        }
    }

    static {
        a = false;
        try {
            Class.forName("com.taobao.orange.OrangeConfig");
            a = true;
        } catch (Exception e) {
            a = false;
        }
    }

    public static void a(String[] strArr, OrangeConfigListenerV1 orangeConfigListenerV1) {
        if (a) {
            OrangeConfig.getInstance().registerListener(strArr, orangeConfigListenerV1);
        } else {
            ALog.w("OrangeAdapter", "no orange sdk", new Object[0]);
        }
    }

    public static String a(String str, String str2, String str3) {
        if (a) {
            return OrangeConfig.getInstance().getConfig(str, str2, str3);
        }
        ALog.w("OrangeAdapter", "no orange sdk", new Object[0]);
        return str3;
    }

    public static boolean a() {
        boolean booleanValue;
        try {
            booleanValue = Boolean.valueOf(a("accs", "main_function_enable", ITagManager.STATUS_TRUE)).booleanValue();
        } catch (Throwable th) {
            ALog.e("OrangeAdapter", "isAccsEnabled", th, new Object[0]);
            booleanValue = true;
        }
        ALog.i("OrangeAdapter", "isAccsEnabled", "enable", Boolean.valueOf(booleanValue));
        return booleanValue;
    }

    public static boolean b() {
        boolean a;
        try {
            a = a(GlobalClientInfo.getContext(), Constants.SP_KEY_HB_SMART_ENABLE, true);
        } catch (Throwable th) {
            ALog.e("OrangeAdapter", "isSmartHb", th, new Object[0]);
            a = true;
        }
        ALog.d("OrangeAdapter", "isSmartHb", "result", Boolean.valueOf(a));
        return a;
    }

    public static boolean a(boolean z) {
        boolean a;
        Throwable th;
        try {
            String str = AccsClientConfig.DEFAULT_CONFIGTAG;
            if (z) {
                str = a("accs", Constants.SP_KEY_TNET_LOG_OFF, AccsClientConfig.DEFAULT_CONFIGTAG);
            }
            if (str.equals(AccsClientConfig.DEFAULT_CONFIGTAG)) {
                a = a(GlobalClientInfo.getContext(), Constants.SP_KEY_TNET_LOG_OFF, true);
            } else {
                a = Boolean.valueOf(str).booleanValue();
                try {
                    b(GlobalClientInfo.getContext(), Constants.SP_KEY_TNET_LOG_OFF, a);
                } catch (Throwable th2) {
                    th = th2;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            a = true;
        }
        ALog.i("OrangeAdapter", "isTnetLogOff", "result", Boolean.valueOf(a));
        return a;
        ALog.e("OrangeAdapter", "isTnetLogOff", th, new Object[0]);
        ALog.i("OrangeAdapter", "isTnetLogOff", "result", Boolean.valueOf(a));
        return a;
    }

    public static boolean c() {
        boolean a;
        boolean z = GlobalClientInfo.d;
        try {
            a = a(GlobalClientInfo.getContext(), Constants.SP_KEY_ELECTION_ENABLE, GlobalClientInfo.d);
        } catch (Throwable th) {
            ALog.e("OrangeAdapter", "isElectionEnable", th, new Object[0]);
            a = z;
        }
        if (!a) {
            ALog.i("OrangeAdapter", "isElectionEnable", "result", Boolean.valueOf(a));
        }
        return a;
    }

    private static boolean a(Context context, String str, boolean z) {
        try {
            return context.getSharedPreferences(Constants.SP_FILE_NAME, 0).getBoolean(str, z);
        } catch (Throwable e) {
            ALog.e("OrangeAdapter", "getConfigFromSP fail:", e, "key", str);
            return z;
        }
    }

    private static void b(Context context, String str, boolean z) {
        if (context == null) {
            try {
                ALog.e("OrangeAdapter", "saveTLogOffToSP context null", new Object[0]);
            } catch (Throwable e) {
                ALog.e("OrangeAdapter", "saveConfigToSP fail:", e, "key", str, "value", Boolean.valueOf(z));
            }
        } else {
            Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putBoolean(str, z);
            edit.apply();
            ALog.i("OrangeAdapter", "saveConfigToSP", "key", str, "value", Boolean.valueOf(z));
        }
    }

    public static void a(Context context, String str, int i) {
        if (context == null) {
            try {
                ALog.e("OrangeAdapter", "saveTLogOffToSP context null", new Object[0]);
            } catch (Throwable e) {
                ALog.e("OrangeAdapter", "saveConfigToSP fail:", e, "key", str, "value", Integer.valueOf(i));
            }
        } else {
            Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
            edit.putInt(str, i);
            edit.apply();
            ALog.i("OrangeAdapter", "saveConfigToSP", "key", str, "value", Integer.valueOf(i));
        }
    }

    private static void a(Context context, Map<String, Boolean> map) {
        if (map != null) {
            try {
                if (map.size() != 0) {
                    Editor edit = context.getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                    for (Entry entry : map.entrySet()) {
                        edit.putBoolean((String) entry.getKey(), ((Boolean) entry.getValue()).booleanValue());
                    }
                    edit.apply();
                    ALog.i("OrangeAdapter", "saveConfigsToSP", HttpConstant.CONFIGS, map.toString());
                }
            } catch (Throwable e) {
                ALog.e("OrangeAdapter", "saveConfigsToSP fail:", e, HttpConstant.CONFIGS, map.toString());
            }
        }
    }

    private static void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                Editor edit = GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_FILE_NAME, 0).edit();
                edit.putString("pullup", str);
                edit.apply();
            } catch (Throwable th) {
                ALog.e("OrangeAdapter", "savePullupInfo fail:", th, "pullup", str);
            }
            ALog.i("OrangeAdapter", "savePullupInfo", "pullup", str);
        }
    }

    public static String d() {
        String str = null;
        try {
            return GlobalClientInfo.getContext().getSharedPreferences(Constants.SP_FILE_NAME, 0).getString("pullup", str);
        } catch (Throwable th) {
            ALog.e("OrangeAdapter", "getPullupInfo fail:", th, new Object[0]);
            return str;
        }
    }

    public static void e() {
        Map hashMap = new HashMap();
        hashMap.put(Constants.SP_KEY_TNET_LOG_OFF, Boolean.valueOf(a("accs", Constants.SP_KEY_TNET_LOG_OFF, "false")));
        hashMap.put(Constants.SP_KEY_ELECTION_ENABLE, Boolean.valueOf(a("accs", Constants.SP_KEY_ELECTION_ENABLE, String.valueOf(GlobalClientInfo.d))));
        hashMap.put(Constants.SP_KEY_HB_SMART_ENABLE, Boolean.valueOf(a("accs", "heartbeat_smart_enable", ITagManager.STATUS_TRUE)));
        a(GlobalClientInfo.getContext(), hashMap);
        a(GlobalClientInfo.getContext(), ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, UtilityImpl.String2Int(a("accs", ChannelService.SUPPORT_FOREGROUND_VERSION_KEY, String.valueOf(24))));
        a(a("accs", "pullup", null));
    }

    public static void f() {
        if (!a()) {
            ALog.e("OrangeAdapter", "force disable service", new Object[0]);
            ACCSManager.forceDisableService(GlobalClientInfo.getContext());
        } else if (UtilityImpl.getFocusDisableStatus(GlobalClientInfo.getContext())) {
            ALog.i("OrangeAdapter", "force enable service", new Object[0]);
            ACCSManager.forceEnableService(GlobalClientInfo.getContext());
        }
    }
}
