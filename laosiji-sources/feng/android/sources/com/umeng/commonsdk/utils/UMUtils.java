package com.umeng.commonsdk.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.proguard.b;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.MLog;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.microedition.khronos.opengles.GL10;

public class UMUtils {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_APP_KEY = "appkey";
    private static final String KEY_CHANNEL = "channel";
    private static final String KEY_LAST_APP_KEY = "last_appkey";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_SHARED_PREFERENCES_NAME = "umeng_common_config";
    public static final String MOBILE_NETWORK = "2G/3G";
    private static final String SD_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";
    private static final String TAG = "UMUtils";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";
    private static final Pattern pattern = Pattern.compile("UTDID\">([^<]+)");

    public static void setLastAppkey(Context context, String str) {
        if (context != null && str != null) {
            try {
                SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putString(KEY_LAST_APP_KEY, str).commit();
                }
            } catch (Throwable e) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "set last app key e is " + e);
                }
                b.a(context, e);
            } catch (Throwable e2) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "set last app key e is " + e2);
                }
                b.a(context, e2);
            }
        }
    }

    public static String getLastAppkey(Context context) {
        if (context == null) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString(KEY_LAST_APP_KEY, null);
            }
            return null;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get last app key e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get last app key e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    public static synchronized void setAppkey(Context context, String str) {
        synchronized (UMUtils.class) {
            if (!(context == null || str == null)) {
                try {
                    SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
                    if (sharedPreferences != null) {
                        sharedPreferences.edit().putString("appkey", str).commit();
                    }
                } catch (Throwable e) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        Log.e(TAG, "set app key e is " + e);
                    }
                    b.a(context, e);
                } catch (Throwable e2) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        Log.e(TAG, "set app key e is " + e2);
                    }
                    b.a(context, e2);
                }
            }
        }
    }

    public static synchronized String getAppkey(Context context) {
        String str = null;
        synchronized (UMUtils.class) {
            if (context != null) {
                try {
                    SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
                    if (sharedPreferences != null) {
                        str = sharedPreferences.getString("appkey", null);
                    }
                } catch (Throwable e) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        Log.e(TAG, "get app key e is " + e);
                    }
                    b.a(context, e);
                } catch (Throwable e2) {
                    if (AnalyticsConstants.UM_DEBUG) {
                        Log.e(TAG, "get app key e is " + e2);
                    }
                    b.a(context, e2);
                }
            }
        }
        return str;
    }

    public static void setChannel(Context context, String str) {
        if (context != null && str != null) {
            try {
                SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
                if (sharedPreferences != null) {
                    sharedPreferences.edit().putString("channel", str).commit();
                }
            } catch (Throwable e) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "set channel e is " + e);
                }
                b.a(context, e);
            } catch (Throwable e2) {
                if (AnalyticsConstants.UM_DEBUG) {
                    Log.e(TAG, "set channel e is " + e2);
                }
                b.a(context, e2);
            }
        }
    }

    public static String getChannel(Context context) {
        if (context == null) {
            return null;
        }
        try {
            SharedPreferences sharedPreferences = StubApp.getOrigApplicationContext(context.getApplicationContext()).getSharedPreferences("umeng_common_config", 0);
            if (sharedPreferences != null) {
                return sharedPreferences.getString("channel", null);
            }
            return null;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get channel e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get channel e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x0058 A:{Splitter: B:3:0x0005, ExcHandler: all (r0_12 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:8:0x0037, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:10:0x003a, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x003c;
     */
    /* JADX WARNING: Missing block: B:11:0x003c, code:
            android.util.Log.e(TAG, "get utiid e is " + r0);
     */
    /* JADX WARNING: Missing block: B:13:0x0058, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:15:0x005b, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x005d;
     */
    /* JADX WARNING: Missing block: B:16:0x005d, code:
            android.util.Log.e(TAG, "get utiid e is " + r0);
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:20:?, code:
            return null;
     */
    public static java.lang.String getUTDID(android.content.Context r6) {
        /*
        r1 = 0;
        if (r6 != 0) goto L_0x0005;
    L_0x0003:
        r0 = r1;
    L_0x0004:
        return r0;
    L_0x0005:
        r0 = "com.ut.device.UTDevice";
        r0 = java.lang.Class.forName(r0);	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r2 = "getUtdid";
        r3 = 1;
        r3 = new java.lang.Class[r3];	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r4 = 0;
        r5 = android.content.Context.class;
        r3[r4] = r5;	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r0 = r0.getMethod(r2, r3);	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r2 = 0;
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r4 = 0;
        r5 = r6.getApplicationContext();	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r5 = com.stub.StubApp.getOrigApplicationContext(r5);	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r3[r4] = r5;	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r0 = r0.invoke(r2, r3);	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        r0 = (java.lang.String) r0;	 Catch:{ Exception -> 0x0031, Throwable -> 0x0058 }
        goto L_0x0004;
    L_0x0031:
        r0 = move-exception;
        r0 = readUTDId(r6);	 Catch:{ Exception -> 0x0037, Throwable -> 0x0058 }
        goto L_0x0004;
    L_0x0037:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0056;
    L_0x003c:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get utiid e is ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r2, r0);
    L_0x0056:
        r0 = r1;
        goto L_0x0004;
    L_0x0058:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0077;
    L_0x005d:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get utiid e is ";
        r3 = r3.append(r4);
        r0 = r3.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r2, r0);
    L_0x0077:
        r0 = r1;
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getUTDID(android.content.Context):java.lang.String");
    }

    private static String readUTDId(Context context) {
        if (context == null) {
            return null;
        }
        File file = getFile(context);
        if (file == null || !file.exists()) {
            return null;
        }
        InputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            String parseId = parseId(readStreamToString(fileInputStream));
            safeClose(fileInputStream);
            return parseId;
        } catch (Exception e) {
            return null;
        } catch (Throwable th) {
            safeClose(fileInputStream);
        }
    }

    private static void safeClose(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (Exception e) {
            }
        }
    }

    private static String parseId(String str) {
        if (str == null) {
            return null;
        }
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private static String readStreamToString(InputStream inputStream) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        char[] cArr = new char[1024];
        StringWriter stringWriter = new StringWriter();
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (-1 == read) {
                return stringWriter.toString();
            }
            stringWriter.write(cArr, 0, read);
        }
    }

    private static File getFile(Context context) {
        if (context == null || !checkPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") || !Environment.getExternalStorageState().equals("mounted")) {
            return null;
        }
        try {
            return new File(Environment.getExternalStorageDirectory().getCanonicalPath(), ".UTSystemConfig/Global/Alvin2.xml");
        } catch (Exception e) {
            return null;
        }
    }

    public static String[] getGPU(GL10 gl10) {
        try {
            String[] strArr = new String[2];
            String glGetString = gl10.glGetString(7936);
            String glGetString2 = gl10.glGetString(7937);
            strArr[0] = glGetString;
            strArr[1] = glGetString2;
            return strArr;
        } catch (Exception e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not read gpu infor, e is " + e);
            }
            return new String[0];
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not read gpu infor, e is " + th);
            }
            return new String[0];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0096 A:{SYNTHETIC, Splitter: B:29:0x0096} */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0058 A:{Catch:{ Exception -> 0x0073, Throwable -> 0x009a }} */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x001f  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0096 A:{SYNTHETIC, Splitter: B:29:0x0096} */
    public static java.lang.String getCPU() {
        /*
        r0 = 0;
        r1 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x00be }
        r2 = "/proc/cpuinfo";
        r1.<init>(r2);	 Catch:{ FileNotFoundException -> 0x00be }
        if (r1 == 0) goto L_0x001c;
    L_0x000b:
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0030 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r1, r3);	 Catch:{ IOException -> 0x0030 }
        r0 = r2.readLine();	 Catch:{ IOException -> 0x0030 }
        r2.close();	 Catch:{ FileNotFoundException -> 0x0050 }
        r1.close();	 Catch:{ FileNotFoundException -> 0x0050 }
    L_0x001c:
        r1 = r0;
    L_0x001d:
        if (r1 == 0) goto L_0x0096;
    L_0x001f:
        r0 = 58;
        r0 = r1.indexOf(r0);	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r0 = r0 + 1;
        r0 = r1.substring(r0);	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r0 = r0.trim();	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
    L_0x002f:
        return r0;
    L_0x0030:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{  }
        if (r2 == 0) goto L_0x001c;
    L_0x0035:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;	 Catch:{  }
        r3.<init>();	 Catch:{  }
        r4 = "Could not read from file /proc/cpuinfo, e is ";
        r3 = r3.append(r4);	 Catch:{  }
        r1 = r3.append(r1);	 Catch:{  }
        r1 = r1.toString();	 Catch:{  }
        android.util.Log.e(r2, r1);	 Catch:{  }
        goto L_0x001c;
    L_0x0050:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0054:
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        if (r2 == 0) goto L_0x001d;
    L_0x0058:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r3.<init>();	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r4 = "Could not read from file /proc/cpuinfo, e is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        android.util.Log.e(r2, r0);	 Catch:{ Exception -> 0x0073, Throwable -> 0x009a }
        goto L_0x001d;
    L_0x0073:
        r0 = move-exception;
        r1 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r1 == 0) goto L_0x0092;
    L_0x0078:
        r1 = "UMUtils";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "get cpu e is ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
    L_0x0092:
        r0 = "";
        goto L_0x002f;
    L_0x0096:
        r0 = "";
        goto L_0x002f;
    L_0x009a:
        r0 = move-exception;
        r1 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r1 == 0) goto L_0x00b9;
    L_0x009f:
        r1 = "UMUtils";
        r2 = new java.lang.StringBuilder;
        r2.<init>();
        r3 = "get cpu e is ";
        r2 = r2.append(r3);
        r0 = r2.append(r0);
        r0 = r0.toString();
        android.util.Log.e(r1, r0);
    L_0x00b9:
        r0 = "";
        goto L_0x002f;
    L_0x00be:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getCPU():java.lang.String");
    }

    public static String getImsi(Context context) {
        try {
            String subscriberId;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                subscriberId = telephonyManager.getSubscriberId();
            } else {
                subscriberId = null;
            }
            return subscriberId;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get imei e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get imei e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    public static String getRegisteredOperator(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String networkOperator;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                networkOperator = telephonyManager.getNetworkOperator();
            } else {
                networkOperator = null;
            }
            return networkOperator;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get registered operator e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get registered operator e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    public static String getNetworkOperatorName(Context context) {
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (!checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                return "";
            }
            if (telephonyManager == null) {
                return "";
            }
            return telephonyManager.getNetworkOperatorName();
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network operator e is " + e);
            }
            b.a(context, e);
            return "";
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network operator e is " + e2);
            }
            b.a(context, e2);
            return "";
        }
    }

    public static String getDisplayResolution(Context context) {
        if (context == null) {
            return "";
        }
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            return String.valueOf(displayMetrics.heightPixels) + "*" + String.valueOf(i);
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get display resolution e is " + e);
            }
            b.a(context, e);
            return "";
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get display resolution e is " + e2);
            }
            b.a(context, e2);
            return "";
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = new String[]{"", ""};
        if (context == null) {
            return strArr;
        }
        try {
            if (checkPermission(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager == null) {
                    strArr[0] = "";
                    return strArr;
                }
                NetworkInfo networkInfo = connectivityManager.getNetworkInfo(1);
                if (networkInfo == null || networkInfo.getState() != State.CONNECTED) {
                    NetworkInfo networkInfo2 = connectivityManager.getNetworkInfo(0);
                    if (networkInfo2 != null && networkInfo2.getState() == State.CONNECTED) {
                        strArr[0] = "2G/3G";
                        strArr[1] = networkInfo2.getSubtypeName();
                        return strArr;
                    }
                    return strArr;
                }
                strArr[0] = "Wi-Fi";
                return strArr;
            }
            strArr[0] = "";
            return strArr;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network access mode e is " + e);
            }
            b.a(context, e);
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get network access mode e is " + e2);
            }
            b.a(context, e2);
        }
    }

    public static boolean isSdCardWrittenable() {
        if (Environment.getExternalStorageState().equals("mounted")) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:7:0x0019 A:{SYNTHETIC, Splitter: B:7:0x0019} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0063 A:{Splitter: B:2:0x0004, ExcHandler: all (r0_10 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:15:0x0040, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:17:0x0043, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0045;
     */
    /* JADX WARNING: Missing block: B:18:0x0045, code:
            android.util.Log.e(TAG, "get locale e is " + r0);
     */
    /* JADX WARNING: Missing block: B:19:0x005f, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:20:0x0063, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:22:0x0066, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0068;
     */
    /* JADX WARNING: Missing block: B:23:0x0068, code:
            android.util.Log.e(TAG, "get locale e is " + r0);
     */
    /* JADX WARNING: Missing block: B:24:0x0082, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:27:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:28:?, code:
            return null;
     */
    public static java.util.Locale getLocale(android.content.Context r5) {
        /*
        r1 = 0;
        if (r5 != 0) goto L_0x0004;
    L_0x0003:
        return r1;
    L_0x0004:
        r0 = new android.content.res.Configuration;	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
        r0.<init>();	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
        r0.setToDefaults();	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
        r2 = r5.getContentResolver();	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
        android.provider.Settings.System.getConfiguration(r2, r0);	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
        if (r0 == 0) goto L_0x003e;
    L_0x0015:
        r0 = r0.locale;	 Catch:{ Exception -> 0x001f, Throwable -> 0x0063 }
    L_0x0017:
        if (r0 != 0) goto L_0x001d;
    L_0x0019:
        r0 = java.util.Locale.getDefault();	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
    L_0x001d:
        r1 = r0;
        goto L_0x0003;
    L_0x001f:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        if (r2 == 0) goto L_0x003e;
    L_0x0024:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        r3.<init>();	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        r4 = "fail to read user config locale, e is ";
        r3 = r3.append(r4);	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        r0 = r3.append(r0);	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        r0 = r0.toString();	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
        android.util.Log.e(r2, r0);	 Catch:{ Exception -> 0x0040, Throwable -> 0x0063 }
    L_0x003e:
        r0 = r1;
        goto L_0x0017;
    L_0x0040:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x005f;
    L_0x0045:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get locale e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x005f:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
    L_0x0063:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0082;
    L_0x0068:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get locale e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x0082:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getLocale(android.content.Context):java.util.Locale");
    }

    public static String getMac(Context context) {
        if (context == null) {
            return null;
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            }
            return "";
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get mac e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get mac e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    public static String getOperator(Context context) {
        if (context == null) {
            return "Unknown";
        }
        try {
            return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getNetworkOperatorName();
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get get operator e is " + e);
            }
            b.a(context, e);
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get get operator e is " + e2);
            }
            b.a(context, e2);
        }
        return "Unknown";
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x005b A:{Splitter: B:2:0x0004, ExcHandler: all (r0_9 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:19:0x0038, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:21:0x003b, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x003d;
     */
    /* JADX WARNING: Missing block: B:22:0x003d, code:
            android.util.Log.e(TAG, "get sub os name e is " + r0);
     */
    /* JADX WARNING: Missing block: B:23:0x0057, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:24:0x005b, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:26:0x005e, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0060;
     */
    /* JADX WARNING: Missing block: B:27:0x0060, code:
            android.util.Log.e(TAG, "get sub os name e is " + r0);
     */
    /* JADX WARNING: Missing block: B:28:0x007a, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:31:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:32:?, code:
            return null;
     */
    public static java.lang.String getSubOSName(android.content.Context r5) {
        /*
        r1 = 0;
        if (r5 != 0) goto L_0x0004;
    L_0x0003:
        return r1;
    L_0x0004:
        r2 = getBuildProp();	 Catch:{ Exception -> 0x0038, Throwable -> 0x005b }
        r0 = "ro.miui.ui.version.name";
        r0 = r2.getProperty(r0);	 Catch:{ Exception -> 0x0032, Throwable -> 0x005b }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x0032, Throwable -> 0x005b }
        if (r3 == 0) goto L_0x002e;
    L_0x0015:
        r3 = isFlyMe();	 Catch:{ Exception -> 0x0032, Throwable -> 0x005b }
        if (r3 == 0) goto L_0x0020;
    L_0x001b:
        r0 = "Flyme";
    L_0x001e:
        r1 = r0;
        goto L_0x0003;
    L_0x0020:
        r2 = getYunOSVersion(r2);	 Catch:{ Exception -> 0x0032, Throwable -> 0x005b }
        r2 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x0032, Throwable -> 0x005b }
        if (r2 != 0) goto L_0x001e;
    L_0x002a:
        r0 = "YunOS";
        goto L_0x001e;
    L_0x002e:
        r0 = "MIUI";
        goto L_0x001e;
    L_0x0032:
        r0 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r0);	 Catch:{ Exception -> 0x0038, Throwable -> 0x005b }
        r0 = r1;
        goto L_0x001e;
    L_0x0038:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0057;
    L_0x003d:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get sub os name e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x0057:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
    L_0x005b:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x007a;
    L_0x0060:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get sub os name e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x007a:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getSubOSName(android.content.Context):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f A:{Splitter: B:2:0x0004, ExcHandler: all (r0_10 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f A:{Splitter: B:2:0x0004, ExcHandler: all (r0_10 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x004f A:{Splitter: B:2:0x0004, ExcHandler: all (r0_10 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:14:0x0026, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:16:?, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:17:0x002a, code:
            r0 = null;
     */
    /* JADX WARNING: Missing block: B:18:0x002c, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:20:0x002f, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0031;
     */
    /* JADX WARNING: Missing block: B:21:0x0031, code:
            android.util.Log.e(TAG, "get sub os version e is " + r0);
     */
    /* JADX WARNING: Missing block: B:22:0x004b, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:23:0x004f, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:25:0x0052, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0054;
     */
    /* JADX WARNING: Missing block: B:26:0x0054, code:
            android.util.Log.e(TAG, "get sub os version e is " + r0);
     */
    /* JADX WARNING: Missing block: B:27:0x006e, code:
            com.umeng.commonsdk.proguard.b.a(r5, r0);
     */
    /* JADX WARNING: Missing block: B:32:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:33:?, code:
            return null;
     */
    public static java.lang.String getSubOSVersion(android.content.Context r5) {
        /*
        r1 = 0;
        if (r5 != 0) goto L_0x0004;
    L_0x0003:
        return r1;
    L_0x0004:
        r2 = getBuildProp();	 Catch:{ Exception -> 0x002c, Throwable -> 0x004f }
        r0 = "ro.miui.ui.version.name";
        r0 = r2.getProperty(r0);	 Catch:{ Exception -> 0x0026, Throwable -> 0x004f }
        r3 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Exception -> 0x0026, Throwable -> 0x004f }
        if (r3 == 0) goto L_0x001f;
    L_0x0015:
        r3 = isFlyMe();	 Catch:{ Exception -> 0x0026, Throwable -> 0x004f }
        if (r3 == 0) goto L_0x0021;
    L_0x001b:
        r0 = getFlymeVersion(r2);	 Catch:{ Exception -> 0x0072, Throwable -> 0x004f }
    L_0x001f:
        r1 = r0;
        goto L_0x0003;
    L_0x0021:
        r0 = getYunOSVersion(r2);	 Catch:{ Exception -> 0x0074, Throwable -> 0x004f }
        goto L_0x001f;
    L_0x0026:
        r0 = move-exception;
        com.umeng.commonsdk.proguard.b.a(r5, r0);	 Catch:{ Exception -> 0x002c, Throwable -> 0x004f }
        r0 = r1;
        goto L_0x001f;
    L_0x002c:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x004b;
    L_0x0031:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get sub os version e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x004b:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
    L_0x004f:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x006e;
    L_0x0054:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get sub os version e is ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        android.util.Log.e(r2, r3);
    L_0x006e:
        com.umeng.commonsdk.proguard.b.a(r5, r0);
        goto L_0x0003;
    L_0x0072:
        r1 = move-exception;
        goto L_0x001f;
    L_0x0074:
        r1 = move-exception;
        goto L_0x001f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getSubOSVersion(android.content.Context):java.lang.String");
    }

    private static String getYunOSVersion(Properties properties) {
        Object property = properties.getProperty("ro.yunos.version");
        return !TextUtils.isEmpty(property) ? property : null;
    }

    private static String getFlymeVersion(Properties properties) {
        try {
            String toLowerCase = properties.getProperty("ro.build.display.id").toLowerCase(Locale.getDefault());
            if (toLowerCase.contains("flyme os")) {
                return toLowerCase.split(" ")[2];
            }
        } catch (Exception e) {
        }
        return null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0024 A:{SYNTHETIC, Splitter: B:12:0x0024} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002d A:{SYNTHETIC, Splitter: B:17:0x002d} */
    public static java.util.Properties getBuildProp() {
        /*
        r2 = new java.util.Properties;
        r2.<init>();
        r1 = 0;
        r0 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0020, all -> 0x002a }
        r3 = new java.io.File;	 Catch:{ IOException -> 0x0020, all -> 0x002a }
        r4 = android.os.Environment.getRootDirectory();	 Catch:{ IOException -> 0x0020, all -> 0x002a }
        r5 = "build.prop";
        r3.<init>(r4, r5);	 Catch:{ IOException -> 0x0020, all -> 0x002a }
        r0.<init>(r3);	 Catch:{ IOException -> 0x0020, all -> 0x002a }
        r2.load(r0);	 Catch:{ IOException -> 0x003a, all -> 0x0035 }
        if (r0 == 0) goto L_0x001f;
    L_0x001c:
        r0.close();	 Catch:{ IOException -> 0x0031 }
    L_0x001f:
        return r2;
    L_0x0020:
        r0 = move-exception;
        r0 = r1;
    L_0x0022:
        if (r0 == 0) goto L_0x001f;
    L_0x0024:
        r0.close();	 Catch:{ IOException -> 0x0028 }
        goto L_0x001f;
    L_0x0028:
        r0 = move-exception;
        goto L_0x001f;
    L_0x002a:
        r0 = move-exception;
    L_0x002b:
        if (r1 == 0) goto L_0x0030;
    L_0x002d:
        r1.close();	 Catch:{ IOException -> 0x0033 }
    L_0x0030:
        throw r0;
    L_0x0031:
        r0 = move-exception;
        goto L_0x001f;
    L_0x0033:
        r1 = move-exception;
        goto L_0x0030;
    L_0x0035:
        r1 = move-exception;
        r6 = r1;
        r1 = r0;
        r0 = r6;
        goto L_0x002b;
    L_0x003a:
        r1 = move-exception;
        goto L_0x0022;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getBuildProp():java.util.Properties");
    }

    private static boolean isFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar", new Class[0]);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDeviceType(Context context) {
        try {
            String str = "Phone";
            if (context == null) {
                return str;
            }
            if (((context.getResources().getConfiguration().screenLayout & 15) >= 3 ? 1 : null) != null) {
                return "Tablet";
            }
            return "Phone";
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get device type e is " + e);
            }
            b.a(context, e);
            return null;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get device type e is " + e2);
            }
            b.a(context, e2);
            return null;
        }
    }

    public static String getAppVersionCode(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (Exception e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version code e is " + e);
            }
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version code e is " + th);
            }
            return "";
        }
    }

    public static String getAppVersinoCode(Context context, String str) {
        if (context == null || str == null) {
            return "";
        }
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(str, 0).versionCode);
        } catch (Exception e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version code e is " + e);
            }
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version code e is " + th);
            }
            return "";
        }
    }

    public static String getAppVersionName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version name e is " + e);
            }
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version name e is " + th);
            }
            return "";
        }
    }

    public static String getAppVersionName(Context context, String str) {
        if (context == null || str == null) {
            return "";
        }
        try {
            return context.getPackageManager().getPackageInfo(str, 0).versionName;
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version name e is " + e);
            }
            b.a(context, e);
            return "";
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app version name e is " + e2);
            }
            b.a(context, e2);
            return "";
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context == null) {
            return false;
        }
        if (VERSION.SDK_INT >= 23) {
            try {
                boolean z;
                if (((Integer) Class.forName("android.content.Context").getMethod("checkSelfPermission", new Class[]{String.class}).invoke(context, new Object[]{str})).intValue() == 0) {
                    z = true;
                } else {
                    z = false;
                }
                return z;
            } catch (Throwable e) {
                b.a(context, e);
                return false;
            }
        } else if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    private static String byte2HexFormatted(byte[] bArr) {
        StringBuilder stringBuilder = new StringBuilder(bArr.length * 2);
        for (int i = 0; i < bArr.length; i++) {
            String toHexString = Integer.toHexString(bArr[i]);
            int length = toHexString.length();
            if (length == 1) {
                toHexString = PushConstants.PUSH_TYPE_NOTIFY + toHexString;
            }
            if (length > 2) {
                toHexString = toHexString.substring(length - 2, length);
            }
            stringBuilder.append(toHexString.toUpperCase());
            if (i < bArr.length - 1) {
                stringBuilder.append(':');
            }
        }
        return stringBuilder.toString();
    }

    public static boolean isDebug(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if ((context.getApplicationInfo().flags & 2) != 0) {
                return true;
            }
            return false;
        } catch (Throwable e) {
            b.a(context, e);
            return false;
        }
    }

    public static String getAppName(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (Throwable e) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app name e is " + e);
            }
            b.a(context, e);
            return str;
        } catch (Throwable e2) {
            if (AnalyticsConstants.UM_DEBUG) {
                Log.e(TAG, "get app name e is " + e2);
            }
            b.a(context, e2);
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x006c A:{Splitter: B:2:0x0005, ExcHandler: all (r1_8 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:11:0x004c, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:13:0x004f, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0051;
     */
    /* JADX WARNING: Missing block: B:14:0x0051, code:
            android.util.Log.e(TAG, "MD5 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:15:0x006c, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:17:0x006f, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x0071;
     */
    /* JADX WARNING: Missing block: B:18:0x0071, code:
            android.util.Log.e(TAG, "MD5 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:21:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:22:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:24:?, code:
            return r0;
     */
    public static java.lang.String MD5(java.lang.String r8) {
        /*
        r1 = 0;
        r0 = 0;
        if (r8 != 0) goto L_0x0005;
    L_0x0004:
        return r0;
    L_0x0005:
        r2 = r8.getBytes();	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3 = "MD5";
        r3 = java.security.MessageDigest.getInstance(r3);	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3.reset();	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3.update(r2);	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r2 = r3.digest();	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3.<init>();	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
    L_0x001f:
        r4 = r2.length;	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        if (r1 >= r4) goto L_0x003b;
    L_0x0022:
        r4 = "%02X";
        r5 = 1;
        r5 = new java.lang.Object[r5];	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r6 = 0;
        r7 = r2[r1];	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r7 = java.lang.Byte.valueOf(r7);	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r5[r6] = r7;	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r4 = java.lang.String.format(r4, r5);	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r3.append(r4);	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        r1 = r1 + 1;
        goto L_0x001f;
    L_0x003b:
        r0 = r3.toString();	 Catch:{ Exception -> 0x0040, Throwable -> 0x006c }
        goto L_0x0004;
    L_0x0040:
        r1 = move-exception;
        r1 = "[^[a-z][A-Z][0-9][.][_]]";
        r2 = "";
        r0 = r8.replaceAll(r1, r2);	 Catch:{ Exception -> 0x004c, Throwable -> 0x006c }
        goto L_0x0004;
    L_0x004c:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0004;
    L_0x0051:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "MD5 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x0004;
    L_0x006c:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0004;
    L_0x0071:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "MD5 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x0004;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.MD5(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0067 A:{Splitter: B:1:0x0003, ExcHandler: all (r1_7 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:15:0x0047, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:17:0x004a, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x004c;
     */
    /* JADX WARNING: Missing block: B:18:0x004c, code:
            android.util.Log.e(TAG, "get file MD5 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:19:0x0067, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:21:0x006a, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x006c;
     */
    /* JADX WARNING: Missing block: B:22:0x006c, code:
            android.util.Log.e(TAG, "get file MD5 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:26:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:27:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:28:?, code:
            return r0;
     */
    public static java.lang.String getFileMD5(java.io.File r6) {
        /*
        r0 = 0;
        r1 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r1 = new byte[r1];	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r2 = r6.isFile();	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        if (r2 != 0) goto L_0x000f;
    L_0x000b:
        r0 = "";
    L_0x000e:
        return r0;
    L_0x000f:
        r2 = "MD5";
        r2 = java.security.MessageDigest.getInstance(r2);	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        r3 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
    L_0x001b:
        r4 = 0;
        r5 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r4 = r3.read(r1, r4, r5);	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        r5 = -1;
        if (r4 == r5) goto L_0x002c;
    L_0x0025:
        r5 = 0;
        r2.update(r1, r5, r4);	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        goto L_0x001b;
    L_0x002a:
        r1 = move-exception;
        goto L_0x000e;
    L_0x002c:
        r3.close();	 Catch:{ Exception -> 0x002a, Throwable -> 0x0067 }
        r1 = new java.math.BigInteger;	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r3 = 1;
        r2 = r2.digest();	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r1.<init>(r3, r2);	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r2 = "%1$032x";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r4 = 0;
        r3[r4] = r1;	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        r0 = java.lang.String.format(r2, r3);	 Catch:{ Exception -> 0x0047, Throwable -> 0x0067 }
        goto L_0x000e;
    L_0x0047:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x000e;
    L_0x004c:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get file MD5 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x000e;
    L_0x0067:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x000e;
    L_0x006c:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "get file MD5 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.getFileMD5(java.io.File):java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:9:0x0038 A:{Splitter: B:1:0x0001, ExcHandler: all (r1_6 'th' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:5:0x0018, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:7:0x001b, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x001d;
     */
    /* JADX WARNING: Missing block: B:8:0x001d, code:
            android.util.Log.e(TAG, "encrypt by SHA1 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:9:0x0038, code:
            r1 = move-exception;
     */
    /* JADX WARNING: Missing block: B:11:0x003b, code:
            if (com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG != false) goto L_0x003d;
     */
    /* JADX WARNING: Missing block: B:12:0x003d, code:
            android.util.Log.e(TAG, "encrypt by SHA1 e is " + r1);
     */
    /* JADX WARNING: Missing block: B:14:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:15:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:16:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:17:?, code:
            return r0;
     */
    public static java.lang.String encryptBySHA1(java.lang.String r5) {
        /*
        r0 = 0;
        r1 = r5.getBytes();	 Catch:{ Exception -> 0x0018, Throwable -> 0x0038 }
        r2 = "SHA1";
        r2 = java.security.MessageDigest.getInstance(r2);	 Catch:{ Exception -> 0x0058, Throwable -> 0x0038 }
        r2.update(r1);	 Catch:{ Exception -> 0x0058, Throwable -> 0x0038 }
        r1 = r2.digest();	 Catch:{ Exception -> 0x0058, Throwable -> 0x0038 }
        r0 = bytes2Hex(r1);	 Catch:{ Exception -> 0x0058, Throwable -> 0x0038 }
    L_0x0017:
        return r0;
    L_0x0018:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0017;
    L_0x001d:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "encrypt by SHA1 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x0017;
    L_0x0038:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0017;
    L_0x003d:
        r2 = "UMUtils";
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "encrypt by SHA1 e is ";
        r3 = r3.append(r4);
        r1 = r3.append(r1);
        r1 = r1.toString();
        android.util.Log.e(r2, r1);
        goto L_0x0017;
    L_0x0058:
        r1 = move-exception;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.utils.UMUtils.encryptBySHA1(java.lang.String):java.lang.String");
    }

    private static String bytes2Hex(byte[] bArr) {
        String str = "";
        int i = 0;
        while (i < bArr.length) {
            String toHexString = Integer.toHexString(bArr[i] & 255);
            if (toHexString.length() == 1) {
                str = str + PushConstants.PUSH_TYPE_NOTIFY;
            }
            i++;
            str = str + toHexString;
        }
        return str;
    }

    public static String getUMId(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        try {
            return UMEnvelopeBuild.imprintProperty(StubApp.getOrigApplicationContext(context.getApplicationContext()), "umid", null);
        } catch (Throwable e) {
            b.a(context, e);
            return str;
        }
    }

    public static String getDeviceToken(Context context) {
        if (context == null) {
            return null;
        }
        Context origApplicationContext = StubApp.getOrigApplicationContext(context.getApplicationContext());
        try {
            String str;
            Class cls = Class.forName("com.umeng.message.MessageSharedPrefs");
            if (cls != null) {
                Method method = cls.getMethod("getInstance", new Class[]{Context.class});
                if (method != null) {
                    Object invoke = method.invoke(cls, new Object[]{origApplicationContext});
                    if (invoke != null) {
                        Method method2 = cls.getMethod("getDeviceToken", new Class[0]);
                        if (method2 != null) {
                            invoke = method2.invoke(invoke, new Object[0]);
                            if (invoke != null && (invoke instanceof String)) {
                                str = (String) invoke;
                                return str;
                            }
                        }
                    }
                }
            }
            str = null;
            return str;
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getAppkeyByXML(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                String string = applicationInfo.metaData.getString("UMENG_APPKEY");
                if (string != null) {
                    return string.trim();
                }
                MLog.e(AnalyticsConstants.LOG_TAG, "getAppkey failed. the applicationinfo is null!");
            }
        } catch (Throwable th) {
            MLog.e(AnalyticsConstants.LOG_TAG, "Could not read UMENG_APPKEY meta-data from AndroidManifest.xml.", th);
        }
        return null;
    }

    public static String getChannelByXML(Context context) {
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                Object obj = applicationInfo.metaData.get("UMENG_CHANNEL");
                if (obj != null) {
                    String obj2 = obj.toString();
                    if (obj2 != null) {
                        return obj2.trim();
                    }
                    if (AnalyticsConstants.UM_DEBUG) {
                        MLog.i(AnalyticsConstants.LOG_TAG, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.");
                    }
                }
            }
        } catch (Throwable th) {
            MLog.e(AnalyticsConstants.LOG_TAG, "Could not read UMENG_CHANNEL meta-data from AndroidManifest.xml.", th);
        }
        return null;
    }

    public static boolean checkPath(String str) {
        try {
            if (Class.forName(str) == null) {
                return false;
            }
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean checkAndroidManifest(Context context, String str) {
        try {
            StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageManager().getActivityInfo(new ComponentName(StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName(), str), 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean checkIntentFilterData(Context context, String str) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addCategory("android.intent.category.BROWSABLE");
        intent.setData(Uri.parse(SocializeProtocolConstants.PROTOCOL_KEY_TENCENT + str + ":"));
        List<ResolveInfo> queryIntentActivities = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageManager().queryIntentActivities(intent, 64);
        if (queryIntentActivities.size() <= 0) {
            return false;
        }
        for (ResolveInfo resolveInfo : queryIntentActivities) {
            if (resolveInfo.activityInfo != null && resolveInfo.activityInfo.packageName.equals(StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName())) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkResource(Context context, String str, String str2) {
        if (StubApp.getOrigApplicationContext(context.getApplicationContext()).getResources().getIdentifier(str, str2, StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName()) <= 0) {
            return false;
        }
        return true;
    }

    public static boolean checkMetaData(Context context, String str) {
        try {
            ApplicationInfo applicationInfo = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageManager().getApplicationInfo(StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName(), 128);
            if (applicationInfo == null || applicationInfo.metaData.get(str) == null) {
                return false;
            }
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static String getAppMD5Signature(Context context) {
        String appMD5Signature = DeviceConfig.getAppMD5Signature(context);
        if (TextUtils.isEmpty(appMD5Signature)) {
            return appMD5Signature;
        }
        return appMD5Signature.replace(":", "").toLowerCase();
    }

    public static String getAppSHA1Key(Context context) {
        return DeviceConfig.getAppSHA1Key(context);
    }

    public static String getAppHashKey(Context context) {
        return DeviceConfig.getAppHashKey(context);
    }

    public static int getTargetSdkVersion(Context context) {
        return context.getApplicationInfo().targetSdkVersion;
    }

    public static boolean isMainProgress(Context context) {
        try {
            Object a = com.umeng.commonsdk.framework.b.a(context);
            CharSequence packageName = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName();
            if (TextUtils.isEmpty(a) || TextUtils.isEmpty(packageName) || !a.equals(packageName)) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isApplication(Context context) {
        try {
            Object name = context.getClass().getSuperclass().getName();
            if (TextUtils.isEmpty(name) || !name.equals("android.app.Application")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
