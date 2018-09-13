package com.taobao.tlog.adapter;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import com.taobao.orange.OrangeConfig;
import com.taobao.orange.OrangeConfigListener;
import com.taobao.tao.log.ITLogController;
import com.taobao.tao.log.TLogInitializer;
import com.taobao.tao.log.TLogUtils;
import com.taobao.tao.log.TaskManager;
import com.umeng.message.common.inter.ITagManager;
import java.util.Map;

public class TLogConfigSwitchReceiver {
    private static final String TAG = "TLogConfigSwitchReceiver";

    public static void init(final Context context) {
        OrangeConfig.getInstance().registerListener(new String[]{"remote_debuger_android"}, new OrangeConfigListener() {
            public void onConfigUpdate(String str) {
                Map configs = OrangeConfig.getInstance().getConfigs(str);
                if (configs != null) {
                    Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
                    String str2 = (String) configs.get("tlog_destroy");
                    String str3 = (String) configs.get("tlog_switch");
                    String str4 = (String) configs.get("tlog_level");
                    String str5 = (String) configs.get("tlog_module");
                    String str6 = (String) configs.get("tlog_endtime");
                    String str7 = (String) configs.get("tlog_pull");
                    ITLogController tLogControler = TLogInitializer.getTLogControler();
                    if (tLogControler != null) {
                        Log.i(TLogConfigSwitchReceiver.TAG, "The tlogDestroy is : " + str2 + "  tlogSwitch is : " + str3 + "  tlogLevel is : " + str4 + "  tlogModule is : " + str5);
                        if (TextUtils.isEmpty(str2)) {
                            Log.i(TLogConfigSwitchReceiver.TAG, "The tlogDestroy is null");
                            return;
                        } else if (ITagManager.STATUS_TRUE.equalsIgnoreCase(str2)) {
                            TLogInitializer.delete();
                            tLogControler.openLog(false);
                            tLogControler.destroyLog(true);
                            edit.putBoolean("tlog_switch", false);
                            return;
                        } else {
                            tLogControler.destroyLog(false);
                            if (TextUtils.isEmpty(str3)) {
                                Log.i(TLogConfigSwitchReceiver.TAG, "The tlogSwitch is null");
                                return;
                            }
                            if (ITagManager.STATUS_TRUE.equalsIgnoreCase(str3)) {
                                tLogControler.openLog(true);
                                edit.putBoolean("tlog_switch", true);
                            } else if ("false".equalsIgnoreCase(str3)) {
                                tLogControler.openLog(false);
                                edit.putBoolean("tlog_switch", false);
                            }
                            if (TextUtils.isEmpty(str4)) {
                                Log.i(TLogConfigSwitchReceiver.TAG, "The tlogLevel is null");
                                return;
                            }
                            tLogControler.setLogLevel(str4);
                            edit.putString("tlog_level", str4);
                            if (TextUtils.isEmpty(str5)) {
                                Log.i(TLogConfigSwitchReceiver.TAG, "The tlogModule is null");
                                return;
                            }
                            tLogControler.setModuleFilter(TLogUtils.makeModule(str5));
                            edit.putString("tlog_module", str5);
                            if (TextUtils.isEmpty(str6)) {
                                tLogControler.setEndTime(System.currentTimeMillis());
                                edit.putLong("tlog_endtime", System.currentTimeMillis());
                            } else {
                                long parseInt;
                                try {
                                    parseInt = ((long) (Integer.parseInt(str6) * 1000)) + System.currentTimeMillis();
                                } catch (NumberFormatException e) {
                                    parseInt = System.currentTimeMillis();
                                }
                                long currentTimeMillis = System.currentTimeMillis() + 86400000;
                                if (parseInt > System.currentTimeMillis() && parseInt < currentTimeMillis) {
                                    tLogControler.setEndTime(parseInt);
                                    edit.putLong("tlog_endtime", parseInt);
                                } else if (parseInt >= currentTimeMillis) {
                                    tLogControler.setEndTime(currentTimeMillis);
                                    edit.putLong("tlog_endtime", currentTimeMillis);
                                } else {
                                    tLogControler.setEndTime(System.currentTimeMillis());
                                    edit.putLong("tlog_endtime", System.currentTimeMillis());
                                }
                            }
                            if (!TextUtils.isEmpty(str7)) {
                                if (str7.equals(ITagManager.STATUS_TRUE)) {
                                    TaskManager.getInstance().queryTraceStatus(context);
                                }
                                edit.putString("tlog_pull", str7);
                            }
                            edit.putString("tlog_version", TLogUtils.getAppBuildVersion(context));
                            edit.apply();
                            return;
                        }
                    }
                    return;
                }
                Log.i(TLogConfigSwitchReceiver.TAG, "TLogConfigSwitchReceiver --> the config is null!");
            }
        });
    }
}
