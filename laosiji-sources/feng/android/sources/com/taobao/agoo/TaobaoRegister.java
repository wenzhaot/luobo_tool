package com.taobao.agoo;

import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsClientConfig.ENV;
import com.taobao.accs.AccsException;
import com.taobao.accs.IACCSManager;
import com.taobao.accs.client.a;
import com.taobao.accs.common.Constants;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.agoo.a.a.d;
import com.taobao.agoo.a.b;
import org.android.agoo.common.CallBack;
import org.android.agoo.common.Config;
import org.android.agoo.control.NotifManager;

/* compiled from: Taobao */
public final class TaobaoRegister {
    private static final int EVENT_ID = 66001;
    static final String PREFERENCES = "Agoo_AppStore";
    static final String PROPERTY_APP_NOTIFICATION_CUSTOM_SOUND = "app_notification_custom_sound";
    static final String PROPERTY_APP_NOTIFICATION_ICON = "app_notification_icon";
    static final String PROPERTY_APP_NOTIFICATION_SOUND = "app_notification_sound";
    static final String PROPERTY_APP_NOTIFICATION_VIBRATE = "app_notification_vibrate";
    private static final String SERVICEID = "agooSend";
    protected static final String TAG = "TaobaoRegister";
    private static b mRequestListener;

    private TaobaoRegister() {
        throw new UnsupportedOperationException();
    }

    public static synchronized void setAccsConfigTag(Context context, String str) {
        synchronized (TaobaoRegister.class) {
            Config.a = str;
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null) {
                throw new RuntimeException("accs config not exist!! please set accs config first!!");
            }
            ALog.i(TAG, "setAccsConfigTag", "config", configByTag.toString());
            a.c = configByTag.getAuthCode();
            Config.setAgooAppKey(context, configByTag.getAppKey());
            com.taobao.accs.utl.a.b = configByTag.getAppSecret();
            if (!TextUtils.isEmpty(com.taobao.accs.utl.a.b)) {
                a.a = 2;
            }
        }
    }

    @Deprecated
    public static synchronized void register(Context context, String str, String str2, String str3, IRegister iRegister) throws AccsException {
        synchronized (TaobaoRegister.class) {
            register(context, str, str, str2, str3, iRegister);
        }
    }

    public static synchronized void register(Context context, String str, String str2, String str3, String str4, IRegister iRegister) throws AccsException {
        synchronized (TaobaoRegister.class) {
            if (context != null) {
                if (!(TextUtils.isEmpty(str2) || TextUtils.isEmpty(str))) {
                    ALog.i(TAG, "register", Constants.KEY_APP_KEY, str2, Constants.KEY_CONFIG_TAG, str);
                    Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
                    Config.a = str;
                    Config.setAgooAppKey(context, str2);
                    com.taobao.accs.utl.a.b = str3;
                    if (!TextUtils.isEmpty(str3)) {
                        a.a = 2;
                    }
                    AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
                    if (configByTag == null) {
                        new Builder().setAppKey(str2).setAppSecret(str3).setTag(str).build();
                    } else {
                        a.c = configByTag.getAuthCode();
                        ALog.i(TAG, "config exist", "config", configByTag.toString());
                    }
                    IACCSManager accsInstance = ACCSManager.getAccsInstance(context, str2, str);
                    accsInstance.bindApp(origApplicationContext, str2, str3, str4, new f(origApplicationContext, accsInstance, iRegister, str2, str4));
                }
            }
            ALog.e(TAG, "register params null", "appkey", str2, Constants.KEY_CONFIG_TAG, str, com.umeng.analytics.pro.b.M, context);
        }
    }

    public static synchronized void setAlias(Context context, String str, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.i(TAG, "setAlias", "alias", str);
            Object g = Config.g(context);
            String a = Config.a(context);
            if (TextUtils.isEmpty(a) || TextUtils.isEmpty(g) || context == null || TextUtils.isEmpty(str)) {
                if (iCallback != null) {
                    iCallback.onFailure(TaobaoConstants.ALIAS_ERROR, "input params null!!");
                }
                ALog.e(TAG, "setAlias param null", "appkey", a, "deviceId", g, "alias", str, com.umeng.analytics.pro.b.M, context);
            } else {
                try {
                    if (mRequestListener == null) {
                        mRequestListener = new b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                    }
                    if (b.b.d(str)) {
                        ALog.i(TAG, "Alias already set", "alias", str);
                        if (iCallback != null) {
                            iCallback.onSuccess();
                        }
                    } else {
                        IACCSManager accsInstance = ACCSManager.getAccsInstance(context, a, Config.b(context));
                        if (b.b.b(context.getPackageName())) {
                            accsInstance.registerDataListener(context, TaobaoConstants.SERVICE_ID_DEVICECMD, mRequestListener);
                            CharSequence sendRequest = accsInstance.sendRequest(context, new AccsRequest(null, TaobaoConstants.SERVICE_ID_DEVICECMD, com.taobao.agoo.a.a.a.a(a, g, str), null));
                            if (TextUtils.isEmpty(sendRequest)) {
                                if (iCallback != null) {
                                    iCallback.onFailure(TaobaoConstants.ALIAS_ERROR, "accs channel disabled!");
                                }
                            } else if (iCallback != null) {
                                iCallback.extra = str;
                                mRequestListener.a.put(sendRequest, iCallback);
                            }
                        } else if (iCallback != null) {
                            iCallback.onFailure(TaobaoConstants.ALIAS_ERROR, "bindApp first!!");
                        }
                    }
                } catch (Throwable th) {
                    ALog.e(TAG, "setAlias", th, new Object[0]);
                }
            }
        }
        return;
    }

    public static synchronized void removeAlias(Context context, ICallback iCallback) {
        synchronized (TaobaoRegister.class) {
            ALog.i(TAG, com.taobao.agoo.a.a.a.JSON_CMD_REMOVEALIAS, new Object[0]);
            try {
                Object g = Config.g(context);
                Object h = Config.h(context);
                String a = Config.a(context);
                if (TextUtils.isEmpty(a) || TextUtils.isEmpty(g) || context == null || TextUtils.isEmpty(h)) {
                    if (iCallback != null) {
                        iCallback.onFailure(TaobaoConstants.ALIAS_ERROR, "input params null!!");
                    }
                    ALog.e(TAG, "setAlias param null", "appkey", a, "deviceId", g, com.taobao.agoo.a.a.a.JSON_PUSH_USER_TOKEN, h, com.umeng.analytics.pro.b.M, context);
                } else {
                    IACCSManager accsInstance = ACCSManager.getAccsInstance(context, a, Config.b(context));
                    if (mRequestListener == null) {
                        mRequestListener = new b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                    }
                    accsInstance.registerDataListener(context, TaobaoConstants.SERVICE_ID_DEVICECMD, mRequestListener);
                    CharSequence sendRequest = accsInstance.sendRequest(context, new AccsRequest(null, TaobaoConstants.SERVICE_ID_DEVICECMD, com.taobao.agoo.a.a.a.b(a, g, h), null));
                    if (TextUtils.isEmpty(sendRequest)) {
                        if (iCallback != null) {
                            iCallback.onFailure(TaobaoConstants.ALIAS_ERROR, "accs channel disabled!");
                        }
                    } else if (iCallback != null) {
                        mRequestListener.a.put(sendRequest, iCallback);
                    }
                }
            } catch (Throwable th) {
                ALog.e(TAG, com.taobao.agoo.a.a.a.JSON_CMD_REMOVEALIAS, th, new Object[0]);
            }
        }
        return;
    }

    @Deprecated
    public static void bindAgoo(Context context, String str, String str2, CallBack callBack) {
        bindAgoo(context, null);
    }

    @Deprecated
    public static void unBindAgoo(Context context, String str, String str2, CallBack callBack) {
        unbindAgoo(context, null);
    }

    private static synchronized void sendSwitch(Context context, ICallback iCallback, boolean z) {
        synchronized (TaobaoRegister.class) {
            try {
                Object g = Config.g(context);
                String a = Config.a(context);
                Object deviceId = UtilityImpl.getDeviceId(context);
                if (TextUtils.isEmpty(a) || context == null || (TextUtils.isEmpty(g) && TextUtils.isEmpty(deviceId))) {
                    if (iCallback != null) {
                        iCallback.onFailure(TaobaoConstants.UNBINDAGOO_ERROR, "input params null!!");
                    }
                    ALog.e(TAG, "sendSwitch param null", "appkey", a, "deviceId", g, com.umeng.analytics.pro.b.M, context, d.JSON_CMD_ENABLEPUSH, Boolean.valueOf(z));
                } else {
                    IACCSManager accsInstance = ACCSManager.getAccsInstance(context, a, Config.b(context));
                    if (mRequestListener == null) {
                        mRequestListener = new b(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                    }
                    accsInstance.registerDataListener(context, TaobaoConstants.SERVICE_ID_DEVICECMD, mRequestListener);
                    CharSequence sendRequest = accsInstance.sendRequest(context, new AccsRequest(null, TaobaoConstants.SERVICE_ID_DEVICECMD, d.a(a, g, deviceId, z), null));
                    if (TextUtils.isEmpty(sendRequest)) {
                        if (iCallback != null) {
                            iCallback.onFailure(TaobaoConstants.BINDAGOO_ERROR, "accs channel disabled!");
                        }
                    } else if (iCallback != null) {
                        mRequestListener.a.put(sendRequest, iCallback);
                    }
                }
            } catch (Throwable th) {
                ALog.e(TAG, "sendSwitch", th, new Object[0]);
            }
        }
        return;
    }

    public static void bindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, true);
        UTMini.getInstance().commitEvent(66001, "bindAgoo", UtilityImpl.getDeviceId(context));
    }

    public static void unbindAgoo(Context context, ICallback iCallback) {
        sendSwitch(context, iCallback, false);
        UTMini.getInstance().commitEvent(66001, "unregister", UtilityImpl.getDeviceId(context));
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008f  */
    public static void clickMessage(android.content.Context r7, java.lang.String r8, java.lang.String r9) {
        /*
        r1 = 0;
        r3 = new org.android.agoo.control.NotifManager;
        r3.<init>();
        r0 = com.taobao.accs.utl.ALog.Level.I;	 Catch:{ Throwable -> 0x006f }
        r0 = com.taobao.accs.utl.ALog.isPrintLog(r0);	 Catch:{ Throwable -> 0x006f }
        if (r0 == 0) goto L_0x002c;
    L_0x000e:
        r0 = "TaobaoRegister";
        r2 = "clickMessage";
        r4 = 4;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x006f }
        r5 = 0;
        r6 = "msgid";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x006f }
        r5 = 1;
        r4[r5] = r8;	 Catch:{ Throwable -> 0x006f }
        r5 = 2;
        r6 = "extData";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x006f }
        r5 = 3;
        r4[r5] = r9;	 Catch:{ Throwable -> 0x006f }
        com.taobao.accs.utl.ALog.i(r0, r2, r4);	 Catch:{ Throwable -> 0x006f }
    L_0x002c:
        r0 = "accs";
        r4 = "8";
        r2 = android.text.TextUtils.isEmpty(r8);	 Catch:{ Throwable -> 0x006f }
        if (r2 == 0) goto L_0x004a;
    L_0x0038:
        r0 = "TaobaoRegister";
        r2 = "messageId == null";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x006f }
        com.taobao.accs.utl.ALog.d(r0, r2, r4);	 Catch:{ Throwable -> 0x006f }
        if (r1 == 0) goto L_0x0049;
    L_0x0046:
        r3.reportNotifyMessage(r1);
    L_0x0049:
        return;
    L_0x004a:
        r3.init(r7);	 Catch:{ Throwable -> 0x006f }
        r2 = new org.android.agoo.common.MsgDO;	 Catch:{ Throwable -> 0x006f }
        r2.<init>();	 Catch:{ Throwable -> 0x006f }
        r2.msgIds = r8;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.extData = r9;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.messageSource = r0;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.msgStatus = r4;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r0 = new org.android.agoo.control.AgooFactory;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r0.<init>();	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r1 = 0;
        r0.init(r7, r3, r1);	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r1 = "8";
        r0.updateMsgStatus(r8, r1);	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        if (r2 == 0) goto L_0x0049;
    L_0x006b:
        r3.reportNotifyMessage(r2);
        goto L_0x0049;
    L_0x006f:
        r0 = move-exception;
    L_0x0070:
        r2 = "TaobaoRegister";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0093 }
        r4.<init>();	 Catch:{ all -> 0x0093 }
        r5 = "clickMessage,error=";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0093 }
        r0 = r4.append(r0);	 Catch:{ all -> 0x0093 }
        r0 = r0.toString();	 Catch:{ all -> 0x0093 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0093 }
        com.taobao.accs.utl.ALog.e(r2, r0, r4);	 Catch:{ all -> 0x0093 }
        if (r1 == 0) goto L_0x0049;
    L_0x008f:
        r3.reportNotifyMessage(r1);
        goto L_0x0049;
    L_0x0093:
        r0 = move-exception;
    L_0x0094:
        if (r1 == 0) goto L_0x0099;
    L_0x0096:
        r3.reportNotifyMessage(r1);
    L_0x0099:
        throw r0;
    L_0x009a:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0094;
    L_0x009d:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.clickMessage(android.content.Context, java.lang.String, java.lang.String):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x0096  */
    /* JADX WARNING: Removed duplicated region for block: B:33:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008f  */
    public static void dismissMessage(android.content.Context r7, java.lang.String r8, java.lang.String r9) {
        /*
        r1 = 0;
        r3 = new org.android.agoo.control.NotifManager;
        r3.<init>();
        r0 = com.taobao.accs.utl.ALog.Level.I;	 Catch:{ Throwable -> 0x006f }
        r0 = com.taobao.accs.utl.ALog.isPrintLog(r0);	 Catch:{ Throwable -> 0x006f }
        if (r0 == 0) goto L_0x002c;
    L_0x000e:
        r0 = "TaobaoRegister";
        r2 = "dismissMessage";
        r4 = 4;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x006f }
        r5 = 0;
        r6 = "msgid";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x006f }
        r5 = 1;
        r4[r5] = r8;	 Catch:{ Throwable -> 0x006f }
        r5 = 2;
        r6 = "extData";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x006f }
        r5 = 3;
        r4[r5] = r9;	 Catch:{ Throwable -> 0x006f }
        com.taobao.accs.utl.ALog.i(r0, r2, r4);	 Catch:{ Throwable -> 0x006f }
    L_0x002c:
        r0 = "accs";
        r4 = "9";
        r2 = android.text.TextUtils.isEmpty(r8);	 Catch:{ Throwable -> 0x006f }
        if (r2 == 0) goto L_0x004a;
    L_0x0038:
        r0 = "TaobaoRegister";
        r2 = "messageId == null";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x006f }
        com.taobao.accs.utl.ALog.d(r0, r2, r4);	 Catch:{ Throwable -> 0x006f }
        if (r1 == 0) goto L_0x0049;
    L_0x0046:
        r3.reportNotifyMessage(r1);
    L_0x0049:
        return;
    L_0x004a:
        r3.init(r7);	 Catch:{ Throwable -> 0x006f }
        r2 = new org.android.agoo.common.MsgDO;	 Catch:{ Throwable -> 0x006f }
        r2.<init>();	 Catch:{ Throwable -> 0x006f }
        r2.msgIds = r8;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.extData = r9;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.messageSource = r0;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r2.msgStatus = r4;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r0 = new org.android.agoo.control.AgooFactory;	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r0.<init>();	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r1 = 0;
        r0.init(r7, r3, r1);	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        r1 = "9";
        r0.updateMsgStatus(r8, r1);	 Catch:{ Throwable -> 0x009d, all -> 0x009a }
        if (r2 == 0) goto L_0x0049;
    L_0x006b:
        r3.reportNotifyMessage(r2);
        goto L_0x0049;
    L_0x006f:
        r0 = move-exception;
    L_0x0070:
        r2 = "TaobaoRegister";
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0093 }
        r4.<init>();	 Catch:{ all -> 0x0093 }
        r5 = "clickMessage,error=";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0093 }
        r0 = r4.append(r0);	 Catch:{ all -> 0x0093 }
        r0 = r0.toString();	 Catch:{ all -> 0x0093 }
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ all -> 0x0093 }
        com.taobao.accs.utl.ALog.e(r2, r0, r4);	 Catch:{ all -> 0x0093 }
        if (r1 == 0) goto L_0x0049;
    L_0x008f:
        r3.reportNotifyMessage(r1);
        goto L_0x0049;
    L_0x0093:
        r0 = move-exception;
    L_0x0094:
        if (r1 == 0) goto L_0x0099;
    L_0x0096:
        r3.reportNotifyMessage(r1);
    L_0x0099:
        throw r0;
    L_0x009a:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0094;
    L_0x009d:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0070;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.TaobaoRegister.dismissMessage(android.content.Context, java.lang.String, java.lang.String):void");
    }

    public static void pingApp(Context context, String str, String str2, String str3, int i) {
        NotifManager notifManager = new NotifManager();
        notifManager.init(context);
        notifManager.pingApp(str, str2, str3, i);
    }

    public static void isEnableDaemonServer(Context context, boolean z) {
        if (ALog.isPrintLog(Level.I)) {
            ALog.i(TAG, "isEnableDaemonServer begin,enable=" + z, new Object[0]);
        }
        Config.a(context, z);
    }

    public static void setAgooMsgReceiveService(String str) {
        a.b = str;
    }

    public static void setEnv(Context context, @ENV int i) {
        ACCSClient.setEnvironment(context, i);
    }

    @Deprecated
    public static void setNotificationIcon(Context context, int i) {
    }

    @Deprecated
    public static void setNotificationSound(Context context, boolean z) {
    }

    @Deprecated
    public static void setBuilderSound(Context context, String str) {
    }

    @Deprecated
    public static void setNotificationVibrate(Context context, boolean z) {
    }

    @Deprecated
    public static void unregister(Context context, CallBack callBack) {
        unbindAgoo(context, null);
    }
}
