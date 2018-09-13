package com.umeng.commonsdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.meizu.cloud.pushsdk.notification.model.NotifyType;
import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.stub.StubApp;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.SdkVersion;
import com.umeng.commonsdk.statistics.a;
import com.umeng.commonsdk.statistics.b;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.commonsdk.utils.UMUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UMConfigure {
    public static final int DEVICE_TYPE_BOX = 2;
    public static final int DEVICE_TYPE_PHONE = 1;
    private static final String KEY_FILE_NAME_APPKEY = "APPKEY";
    private static final String KEY_FILE_NAME_LOG = "LOG";
    private static final String KEY_METHOD_NAME_PUSH_SETCHANNEL = "setMessageChannel";
    private static final String KEY_METHOD_NAME_PUSH_SET_SECRET = "setSecret";
    private static final String KEY_METHOD_NAME_SETAPPKEY = "setAppkey";
    private static final String KEY_METHOD_NAME_SETCHANNEL = "setChannel";
    private static final String KEY_METHOD_NAME_SETDEBUGMODE = "setDebugMode";
    private static final String TAG = "UMConfigure";
    private static final String WRAPER_TYPE_COCOS2DX_X = "Cocos2d-x";
    private static final String WRAPER_TYPE_COCOS2DX_XLUA = "Cocos2d-x_lua";
    private static final String WRAPER_TYPE_FLUTTER = "flutter";
    private static final String WRAPER_TYPE_HYBRID = "hybrid";
    private static final String WRAPER_TYPE_NATIVE = "native";
    private static final String WRAPER_TYPE_PHONEGAP = "phonegap";
    private static final String WRAPER_TYPE_REACTNATIVE = "react-native";
    private static final String WRAPER_TYPE_UNITY = "Unity";
    private static final String WRAPER_TYPE_WEEX = "weex";
    private static boolean debugLog = false;
    private static boolean isFinish = false;
    private static boolean isUMDebug;
    private static Object lockObject = new Object();
    public static UMLog umDebugLog = new UMLog();

    private static Class<?> getClass(String str) {
        Class<?> cls = null;
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            return cls;
        }
    }

    private Object getInstanceObject(Class<?> cls) {
        Object obj = null;
        if (cls == null) {
            return obj;
        }
        try {
            return cls.newInstance();
        } catch (InstantiationException e) {
            return obj;
        } catch (IllegalAccessException e2) {
            return obj;
        }
    }

    private static Object getDecInstanceObject(Class<?> cls) {
        Constructor constructor = null;
        if (cls == null) {
            return constructor;
        }
        Constructor declaredConstructor;
        try {
            declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
        } catch (NoSuchMethodException e) {
            declaredConstructor = constructor;
        }
        if (declaredConstructor == null) {
            return constructor;
        }
        declaredConstructor.setAccessible(true);
        try {
            return declaredConstructor.newInstance(new Object[0]);
        } catch (IllegalArgumentException e2) {
            return constructor;
        } catch (InstantiationException e3) {
            return constructor;
        } catch (IllegalAccessException e4) {
            return constructor;
        } catch (InvocationTargetException e5) {
            return constructor;
        }
    }

    private static Method getDecMethod(Class<?> cls, String str, Class<?>[] clsArr) {
        Method method = null;
        if (cls != null) {
            try {
                method = cls.getDeclaredMethod(str, clsArr);
            } catch (NoSuchMethodException e) {
            }
            if (method != null) {
                method.setAccessible(true);
            }
        }
        return method;
    }

    private static void invoke(Method method, Object obj, Object[] objArr) {
        if (method != null && obj != null) {
            try {
                method.invoke(obj, objArr);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
    }

    private static void invoke(Method method, Object[] objArr) {
        if (method != null) {
            try {
                method.invoke(null, objArr);
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e2) {
            } catch (InvocationTargetException e3) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, String str2) {
        if (cls != null) {
            try {
                cls.getField(str).set(str, str2);
            } catch (Exception e) {
            }
        }
    }

    private static void setFile(Class<?> cls, String str, boolean z) {
        if (cls != null) {
            try {
                cls.getField(str).set(str, Boolean.valueOf(z));
            } catch (Exception e) {
            }
        }
    }

    private static void saveSDKComponent() {
        StringBuffer stringBuffer = new StringBuffer();
        if (getClass("com.umeng.analytics.vismode.V") != null) {
            stringBuffer.append(NotifyType.VIBRATE);
        } else if (getClass("com.umeng.analytics.MobclickAgent") != null) {
            stringBuffer.append(g.al);
        }
        if (getClass("com.umeng.visual.UMVisualAgent") != null) {
            stringBuffer.append("x");
        }
        if (getClass("com.umeng.message.PushAgent") != null) {
            stringBuffer.append(g.ao);
        }
        if (getClass("com.umeng.socialize.UMShareAPI") != null) {
            stringBuffer.append("s");
        }
        if (getClass("com.umeng.error.UMError") != null) {
            stringBuffer.append(Parameters.EVENT);
        }
        stringBuffer.append(g.aq);
        if (!(SdkVersion.SDK_TYPE == 1 || getClass("com.umeng.commonsdk.internal.UMOplus") == null)) {
            stringBuffer.append("o");
        }
        if (!TextUtils.isEmpty(stringBuffer)) {
            b.a = stringBuffer.toString();
            UMSLEnvelopeBuild.module = stringBuffer.toString();
        }
    }

    public static boolean getInitStatus() {
        boolean z;
        synchronized (lockObject) {
            z = isFinish;
        }
        return z;
    }

    public static void init(Context context, int i, String str) {
        init(context, null, null, i, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:131:0x031b A:{Splitter: B:39:0x00f8, ExcHandler: all (r0_70 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01fc A:{Splitter: B:63:0x01a0, ExcHandler: java.lang.Exception (r0_67 'e' java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01fc A:{Splitter: B:63:0x01a0, ExcHandler: java.lang.Exception (r0_67 'e' java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01fc A:{Splitter: B:63:0x01a0, ExcHandler: java.lang.Exception (r0_67 'e' java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x031b A:{Splitter: B:39:0x00f8, ExcHandler: all (r0_70 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x031b A:{Splitter: B:39:0x00f8, ExcHandler: all (r0_70 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01fc A:{Splitter: B:63:0x01a0, ExcHandler: java.lang.Exception (r0_67 'e' java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x031b A:{Splitter: B:39:0x00f8, ExcHandler: all (r0_70 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x01fc A:{Splitter: B:63:0x01a0, ExcHandler: java.lang.Exception (r0_67 'e' java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:131:0x031b A:{Splitter: B:39:0x00f8, ExcHandler: all (r0_70 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:77:0x01fc, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:79:0x01ff, code:
            if (debugLog != false) goto L_0x0201;
     */
    /* JADX WARNING: Missing block: B:80:0x0201, code:
            android.util.Log.e(TAG, "init e is " + r0);
     */
    /* JADX WARNING: Missing block: B:131:0x031b, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:133:0x031e, code:
            if (debugLog != false) goto L_0x0320;
     */
    /* JADX WARNING: Missing block: B:134:0x0320, code:
            android.util.Log.e(TAG, "init e is " + r0);
     */
    /* JADX WARNING: Missing block: B:160:?, code:
            java.lang.Class.forName("com.umeng.analytics.vismode.event.VisualHelper").getMethod("init", new java.lang.Class[]{android.content.Context.class}).invoke(null, new java.lang.Object[]{r9});
     */
    /* JADX WARNING: Missing block: B:173:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:174:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:175:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:176:?, code:
            return;
     */
    public static void init(android.content.Context r9, java.lang.String r10, java.lang.String r11, int r12, java.lang.String r13) {
        /*
        r8 = 1;
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x002a;
    L_0x0005:
        r0 = "UMConfigure";
        r1 = "common version is 1.5.3";
        android.util.Log.i(r0, r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = "UMConfigure";
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r1.<init>();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = "common type is ";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = com.umeng.commonsdk.statistics.SdkVersion.SDK_TYPE;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r1 = r1.toString();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        android.util.Log.i(r0, r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x002a:
        if (r9 != 0) goto L_0x003a;
    L_0x002c:
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x0039;
    L_0x0030:
        r0 = "UMConfigure";
        r1 = "context is null !!!";
        android.util.Log.e(r0, r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0039:
        return;
    L_0x003a:
        r1 = r9.getApplicationContext();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r1 = com.stub.StubApp.getOrigApplicationContext(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x0080;
    L_0x0046:
        r0 = com.umeng.commonsdk.utils.UMUtils.getAppkeyByXML(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = android.text.TextUtils.isEmpty(r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0080;
    L_0x0050:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0080;
    L_0x0056:
        r2 = r10.equals(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0080;
    L_0x005c:
        r2 = 2;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = 0;
        r4 = "@";
        r2[r3] = r4;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = 1;
        r4 = "#";
        r2[r3] = r4;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = 2;
        r3 = new java.lang.String[r3];	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r4 = 0;
        r3[r4] = r10;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r4 = 1;
        r3[r4] = r0;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = umDebugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = "请注意：您init接口中设置的AppKey是@，manifest中设置的AppKey是#，init接口设置的AppKey会覆盖manifest中设置的AppKey";
        r4 = 3;
        r5 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r4, r5, r2, r3);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0080:
        r0 = android.text.TextUtils.isEmpty(r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x008a;
    L_0x0086:
        r10 = com.umeng.commonsdk.utils.UMUtils.getAppkeyByXML(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x008a:
        r0 = android.text.TextUtils.isEmpty(r11);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x0403;
    L_0x0090:
        r0 = com.umeng.commonsdk.utils.UMUtils.getChannelByXML(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0094:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x009d;
    L_0x009a:
        r0 = "Unknown";
    L_0x009d:
        com.umeng.commonsdk.utils.UMUtils.setChannel(r1, r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x00be;
    L_0x00a4:
        r2 = "UMConfigure";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3.<init>();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r4 = "channel is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = r3.append(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = r3.toString();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        android.util.Log.i(r2, r3);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x00be:
        saveSDKComponent();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = "com.umeng.analytics.MobclickAgent";
        r2 = java.lang.Class.forName(r2);	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x00f8;
    L_0x00ca:
        r3 = "init";
        r4 = 1;
        r4 = new java.lang.Class[r4];	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r5 = 0;
        r6 = android.content.Context.class;
        r4[r5] = r6;	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r3 = r2.getDeclaredMethod(r3, r4);	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        if (r3 == 0) goto L_0x00f8;
    L_0x00db:
        r4 = 1;
        r3.setAccessible(r4);	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r5 = 0;
        r4[r5] = r1;	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r3.invoke(r2, r4);	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r2 = debugLog;	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x00f8;
    L_0x00ec:
        r2 = umDebugLog;	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
        r2 = "统计SDK初始化成功";
        r3 = 2;
        r4 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{ Exception -> 0x0400, Throwable -> 0x031b }
    L_0x00f8:
        r2 = "com.umeng.message.PushAgent";
        r2 = java.lang.Class.forName(r2);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x014c;
    L_0x0101:
        r3 = "getInstance";
        r4 = 1;
        r4 = new java.lang.Class[r4];	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r5 = 0;
        r6 = android.content.Context.class;
        r4[r5] = r6;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r3 = r2.getDeclaredMethod(r3, r4);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        if (r3 == 0) goto L_0x014c;
    L_0x0112:
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r5 = 0;
        r4[r5] = r1;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r3 = r3.invoke(r2, r4);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        if (r3 == 0) goto L_0x014c;
    L_0x011e:
        r4 = "setAppkey";
        r5 = 1;
        r5 = new java.lang.Class[r5];	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r6 = 0;
        r7 = java.lang.String.class;
        r5[r6] = r7;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r2 = r2.getDeclaredMethod(r4, r5);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x014c;
    L_0x012f:
        r4 = 1;
        r2.setAccessible(r4);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r5 = 0;
        r4[r5] = r10;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r2.invoke(r3, r4);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r2 = debugLog;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x014c;
    L_0x0140:
        r2 = umDebugLog;	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
        r2 = "PUSH AppKey设置成功";
        r3 = 2;
        r4 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{ Exception -> 0x03fd, Throwable -> 0x031b }
    L_0x014c:
        r2 = "com.umeng.message.PushAgent";
        r2 = java.lang.Class.forName(r2);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x01a0;
    L_0x0155:
        r3 = "getInstance";
        r4 = 1;
        r4 = new java.lang.Class[r4];	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r5 = 0;
        r6 = android.content.Context.class;
        r4[r5] = r6;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r3 = r2.getDeclaredMethod(r3, r4);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        if (r3 == 0) goto L_0x01a0;
    L_0x0166:
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r5 = 0;
        r4[r5] = r1;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r3 = r3.invoke(r2, r4);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        if (r3 == 0) goto L_0x01a0;
    L_0x0172:
        r4 = "setMessageChannel";
        r5 = 1;
        r5 = new java.lang.Class[r5];	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r6 = 0;
        r7 = java.lang.String.class;
        r5[r6] = r7;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r2 = r2.getDeclaredMethod(r4, r5);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x01a0;
    L_0x0183:
        r4 = 1;
        r2.setAccessible(r4);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r5 = 0;
        r4[r5] = r0;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r2.invoke(r3, r4);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r0 = debugLog;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x01a0;
    L_0x0194:
        r0 = umDebugLog;	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
        r0 = "PUSH Channel设置成功";
        r2 = 2;
        r3 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r2, r3);	 Catch:{ Exception -> 0x03fa, Throwable -> 0x031b }
    L_0x01a0:
        r0 = "com.umeng.socialize.UMShareAPI";
        r0 = getClass(r0);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r2 = "APPKEY";
        setFile(r0, r2, r10);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x01e5;
    L_0x01af:
        r2 = "init";
        r3 = 2;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r4 = 0;
        r5 = android.content.Context.class;
        r3[r4] = r5;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r4 = 1;
        r5 = java.lang.String.class;
        r3[r4] = r5;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r2 = r0.getDeclaredMethod(r2, r3);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        if (r2 == 0) goto L_0x01e5;
    L_0x01c5:
        r3 = 1;
        r2.setAccessible(r3);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r3 = 2;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r4 = 0;
        r3[r4] = r1;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r4 = 1;
        r3[r4] = r10;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r2.invoke(r0, r3);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r0 = debugLog;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x01e5;
    L_0x01d9:
        r0 = umDebugLog;	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
        r0 = "Share AppKey设置成功";
        r2 = 2;
        r3 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r2, r3);	 Catch:{ Throwable -> 0x03f7, Exception -> 0x01fc }
    L_0x01e5:
        r0 = android.text.TextUtils.isEmpty(r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x021d;
    L_0x01eb:
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x0039;
    L_0x01ef:
        r0 = umDebugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = com.umeng.commonsdk.debug.UMLogCommon.SC_10007;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r1 = 0;
        r2 = "\\|";
        com.umeng.commonsdk.debug.UMLog.aq(r0, r1, r2);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        goto L_0x0039;
    L_0x01fc:
        r0 = move-exception;
        r1 = debugLog;
        if (r1 == 0) goto L_0x0039;
    L_0x0201:
        r1 = "UMConfigure";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "init e is ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        goto L_0x0039;
    L_0x021d:
        com.umeng.commonsdk.utils.UMUtils.setAppkey(r1, r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = com.umeng.commonsdk.utils.UMUtils.getLastAppkey(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = android.text.TextUtils.isEmpty(r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0249;
    L_0x022a:
        r2 = r10.equals(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0249;
    L_0x0230:
        r2 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 != 0) goto L_0x0246;
    L_0x0236:
        r2 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x0246;
    L_0x023a:
        r2 = umDebugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = "AppKey改变!!!";
        r3 = 2;
        r4 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0246:
        com.umeng.commonsdk.utils.UMUtils.setLastAppkey(r1, r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0249:
        r2 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r2 == 0) goto L_0x0272;
    L_0x024d:
        r2 = "UMConfigure";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3.<init>();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r4 = "current appkey is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = r3.append(r10);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r4 = ", last appkey is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = r0.toString();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        android.util.Log.i(r2, r0);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x0272:
        com.umeng.commonsdk.statistics.AnalyticsConstants.setDeviceType(r12);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = android.text.TextUtils.isEmpty(r13);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x033c;
    L_0x027b:
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x027f;
    L_0x027f:
        r0 = "com.umeng.error.UMError";
        r0 = java.lang.Class.forName(r0);	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x02b6;
    L_0x0288:
        r2 = "init";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r4 = 0;
        r5 = android.content.Context.class;
        r3[r4] = r5;	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r2 = r0.getDeclaredMethod(r2, r3);	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        if (r2 == 0) goto L_0x02b6;
    L_0x0299:
        r3 = 1;
        r2.setAccessible(r3);	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r4 = 0;
        r3[r4] = r1;	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r2.invoke(r0, r3);	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r0 = debugLog;	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x02b6;
    L_0x02aa:
        r0 = umDebugLog;	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
        r0 = "错误分析SDK初始化成功";
        r2 = 2;
        r3 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r2, r3);	 Catch:{ Throwable -> 0x03f4, Exception -> 0x01fc }
    L_0x02b6:
        r0 = com.umeng.commonsdk.statistics.SdkVersion.SDK_TYPE;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == r8) goto L_0x03b7;
    L_0x02ba:
        r0 = "com.umeng.commonsdk.UMConfigureImpl";
        r0 = java.lang.Class.forName(r0);	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x02e1;
    L_0x02c3:
        r2 = "init";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        r4 = 0;
        r5 = android.content.Context.class;
        r3[r4] = r5;	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        r2 = r0.getDeclaredMethod(r2, r3);	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        if (r2 == 0) goto L_0x02e1;
    L_0x02d4:
        r3 = 1;
        r2.setAccessible(r3);	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        r4 = 0;
        r3[r4] = r1;	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
        r2.invoke(r0, r3);	 Catch:{ Throwable -> 0x03f1, Exception -> 0x01fc }
    L_0x02e1:
        r0 = "com.umeng.visual.UMVisualAgent";
        r0 = java.lang.Class.forName(r0);	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r1 = "init";
        r2 = 2;
        r2 = new java.lang.Class[r2];	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r3 = 0;
        r4 = android.content.Context.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r3 = 1;
        r4 = java.lang.String.class;
        r2[r3] = r4;	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r0 = r0.getMethod(r1, r2);	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r1 = android.text.TextUtils.isEmpty(r10);	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        if (r1 != 0) goto L_0x03bc;
    L_0x0302:
        r1 = 0;
        r2 = 2;
        r2 = new java.lang.Object[r2];	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r3 = 0;
        r2[r3] = r9;	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r3 = 1;
        r2[r3] = r10;	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        r0.invoke(r1, r2);	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
    L_0x030f:
        r1 = lockObject;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        monitor-enter(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r0 = 1;
        isFinish = r0;	 Catch:{ all -> 0x0318 }
        monitor-exit(r1);	 Catch:{ all -> 0x0318 }
        goto L_0x0039;
    L_0x0318:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0318 }
        throw r0;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x031b:
        r0 = move-exception;
        r1 = debugLog;
        if (r1 == 0) goto L_0x0039;
    L_0x0320:
        r1 = "UMConfigure";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "init e is ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
        goto L_0x0039;
    L_0x033c:
        r0 = debugLog;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        if (r0 == 0) goto L_0x035a;
    L_0x0340:
        r0 = "UMConfigure";
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2.<init>();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r3 = "push secret is ";
        r2 = r2.append(r3);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = r2.append(r13);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        r2 = r2.toString();	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        android.util.Log.i(r0, r2);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
    L_0x035a:
        r0 = "com.umeng.message.PushAgent";
        r0 = java.lang.Class.forName(r0);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x027f;
    L_0x0363:
        r2 = "getInstance";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r4 = 0;
        r5 = android.content.Context.class;
        r3[r4] = r5;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r2 = r0.getDeclaredMethod(r2, r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        if (r2 == 0) goto L_0x027f;
    L_0x0374:
        r3 = 1;
        r2.setAccessible(r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r4 = 0;
        r3[r4] = r1;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r2 = r2.invoke(r0, r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        if (r2 == 0) goto L_0x027f;
    L_0x0384:
        r3 = "setSecret";
        r4 = 1;
        r4 = new java.lang.Class[r4];	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r5 = 0;
        r6 = java.lang.String.class;
        r4[r5] = r6;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r0 = r0.getDeclaredMethod(r3, r4);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x027f;
    L_0x0395:
        r3 = 1;
        r0.setAccessible(r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r4 = 0;
        r3[r4] = r13;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r0.invoke(r2, r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r0 = debugLog;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x027f;
    L_0x03a6:
        r0 = umDebugLog;	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        r0 = "PUSH Secret设置成功";
        r2 = 2;
        r3 = "";
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r2, r3);	 Catch:{ Throwable -> 0x03b4, Exception -> 0x01fc }
        goto L_0x027f;
    L_0x03b4:
        r0 = move-exception;
        goto L_0x027f;
    L_0x03b7:
        com.umeng.commonsdk.a.a(r1);	 Catch:{ Exception -> 0x01fc, Throwable -> 0x031b }
        goto L_0x02e1;
    L_0x03bc:
        r0 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        if (r0 == 0) goto L_0x030f;
    L_0x03c0:
        r0 = "initDebugSDK appkey  is null";
        com.umeng.commonsdk.statistics.common.MLog.e(r0);	 Catch:{ ClassNotFoundException -> 0x03c8, Throwable -> 0x03ee, Exception -> 0x01fc }
        goto L_0x030f;
    L_0x03c8:
        r0 = move-exception;
        r0 = "com.umeng.analytics.vismode.event.VisualHelper";
        r0 = java.lang.Class.forName(r0);	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r1 = "init";
        r2 = 1;
        r2 = new java.lang.Class[r2];	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r3 = 0;
        r4 = android.content.Context.class;
        r2[r3] = r4;	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r0 = r0.getMethod(r1, r2);	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r1 = 0;
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r3 = 0;
        r2[r3] = r9;	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        r0.invoke(r1, r2);	 Catch:{ Exception -> 0x03eb, Throwable -> 0x031b }
        goto L_0x030f;
    L_0x03eb:
        r0 = move-exception;
        goto L_0x030f;
    L_0x03ee:
        r0 = move-exception;
        goto L_0x030f;
    L_0x03f1:
        r0 = move-exception;
        goto L_0x02e1;
    L_0x03f4:
        r0 = move-exception;
        goto L_0x02b6;
    L_0x03f7:
        r0 = move-exception;
        goto L_0x01e5;
    L_0x03fa:
        r0 = move-exception;
        goto L_0x01a0;
    L_0x03fd:
        r2 = move-exception;
        goto L_0x014c;
    L_0x0400:
        r2 = move-exception;
        goto L_0x00f8;
    L_0x0403:
        r0 = r11;
        goto L_0x0094;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.UMConfigure.init(android.content.Context, java.lang.String, java.lang.String, int, java.lang.String):void");
    }

    public static boolean isDebugLog() {
        return debugLog;
    }

    public static void setLogEnabled(boolean z) {
        try {
            debugLog = z;
            MLog.DEBUG = z;
            Class cls = getClass("com.umeng.message.PushAgent");
            Object decInstanceObject = getDecInstanceObject(cls);
            invoke(getDecMethod(cls, KEY_METHOD_NAME_SETDEBUGMODE, new Class[]{Boolean.TYPE}), decInstanceObject, new Object[]{Boolean.valueOf(z)});
            setFile(getClass("com.umeng.socialize.Config"), "DEBUG", z);
        } catch (Exception e) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + e);
            }
        } catch (Throwable th) {
            if (debugLog) {
                Log.e(TAG, "set log enabled e is " + th);
            }
        }
    }

    public static void setEncryptEnabled(boolean z) {
        b.a(z);
        UMSLEnvelopeBuild.setEncryptEnabled(z);
    }

    public static String getUMIDString(Context context) {
        if (context != null) {
            return UMUtils.getUMId(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        }
        return null;
    }

    private static void setLatencyWindow(long j) {
        a.c = ((int) j) * 1000;
    }

    private static void setCheckDevice(boolean z) {
        AnalyticsConstants.CHECK_DEVICE = z;
    }

    private static void setWraperType(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            if (str.equals(WRAPER_TYPE_NATIVE)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_NATIVE;
                a.a = WRAPER_TYPE_NATIVE;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_X)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_COCOS2DX_X;
                a.a = WRAPER_TYPE_COCOS2DX_X;
            } else if (str.equals(WRAPER_TYPE_COCOS2DX_XLUA)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_COCOS2DX_XLUA;
                a.a = WRAPER_TYPE_COCOS2DX_XLUA;
            } else if (str.equals(WRAPER_TYPE_UNITY)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_UNITY;
                a.a = WRAPER_TYPE_UNITY;
            } else if (str.equals(WRAPER_TYPE_REACTNATIVE)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_REACTNATIVE;
                a.a = WRAPER_TYPE_REACTNATIVE;
            } else if (str.equals(WRAPER_TYPE_PHONEGAP)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_PHONEGAP;
                a.a = WRAPER_TYPE_PHONEGAP;
            } else if (str.equals(WRAPER_TYPE_WEEX)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_WEEX;
                a.a = WRAPER_TYPE_WEEX;
            } else if (str.equals(WRAPER_TYPE_HYBRID)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_HYBRID;
                a.a = WRAPER_TYPE_HYBRID;
            } else if (str.equals(WRAPER_TYPE_FLUTTER)) {
                com.umeng.commonsdk.stateless.a.a = WRAPER_TYPE_FLUTTER;
                a.a = WRAPER_TYPE_FLUTTER;
            }
        }
        if (!TextUtils.isEmpty(str2)) {
            com.umeng.commonsdk.stateless.a.b = str2;
            a.b = str2;
        }
    }

    public static String[] getTestDeviceInfo(Context context) {
        String[] strArr = new String[2];
        if (context != null) {
            try {
                strArr[0] = DeviceConfig.getDeviceIdForGeneral(context);
                strArr[1] = DeviceConfig.getMac(context);
            } catch (Exception e) {
            }
        }
        return strArr;
    }
}
