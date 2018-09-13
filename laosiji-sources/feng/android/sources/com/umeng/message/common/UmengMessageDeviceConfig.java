package com.umeng.message.common;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.feng.car.utils.HttpConstant;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.qiniu.android.common.Constants;
import com.stub.StubApp;
import com.ta.utdid2.device.UTDevice;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.proguard.c;
import com.umeng.message.proguard.h;
import com.umeng.message.util.a;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.microedition.khronos.opengles.GL10;

public class UmengMessageDeviceConfig {
    public static final int DEFAULT_TIMEZONE = 8;
    protected static final String a = "Unknown";
    private static final String b = UmengMessageDeviceConfig.class.getSimpleName();
    private static final String c = "2G/3G";
    private static final String d = "Wi-Fi";
    private static boolean e = false;

    public static boolean isAppInstalled(String str, Context context) {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isChinese(Context context) {
        return context.getResources().getConfiguration().locale.toString().equals(Locale.CHINA.toString());
    }

    public static boolean isScreenPortrait(Context context) {
        if (context.getResources().getConfiguration().orientation == 1) {
            return true;
        }
        return false;
    }

    public static String getAppVersionCode(Context context) {
        try {
            return String.valueOf(context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode);
        } catch (NameNotFoundException e) {
            return a;
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (NameNotFoundException e) {
            return a;
        }
    }

    public static boolean checkPermission(Context context, String str) {
        if (context.getPackageManager().checkPermission(str, context.getPackageName()) != 0) {
            return false;
        }
        return true;
    }

    public static String getAppLabel(Context context) {
        ApplicationInfo applicationInfo;
        PackageManager packageManager = context.getPackageManager();
        try {
            applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
        } catch (NameNotFoundException e) {
            applicationInfo = null;
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : "");
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
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, "Could not read gpu infor:", e.getMessage());
            return new String[0];
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x0021  */
    /* JADX WARNING: Removed duplicated region for block: B:19:? A:{SYNTHETIC, RETURN} */
    public static java.lang.String getCPU() {
        /*
        r9 = 2;
        r8 = 1;
        r7 = 0;
        r0 = 0;
        r1 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x0066 }
        r2 = "/proc/cpuinfo";
        r1.<init>(r2);	 Catch:{ FileNotFoundException -> 0x0066 }
        if (r1 == 0) goto L_0x001f;
    L_0x000e:
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0032 }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r1, r3);	 Catch:{ IOException -> 0x0032 }
        r0 = r2.readLine();	 Catch:{ IOException -> 0x0032 }
        r2.close();	 Catch:{ FileNotFoundException -> 0x004c }
        r1.close();	 Catch:{ FileNotFoundException -> 0x004c }
    L_0x001f:
        if (r0 == 0) goto L_0x0031;
    L_0x0021:
        r1 = 58;
        r1 = r0.indexOf(r1);
        r1 = r1 + 1;
        r0 = r0.substring(r1);
        r0 = r0.trim();
    L_0x0031:
        return r0;
    L_0x0032:
        r1 = move-exception;
        r2 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{  }
        r2 = b;	 Catch:{  }
        r3 = 0;
        r4 = 2;
        r4 = new java.lang.String[r4];	 Catch:{  }
        r5 = 0;
        r6 = "Could not read from file /proc/cpuinfo";
        r4[r5] = r6;	 Catch:{  }
        r5 = 1;
        r1 = r1.getMessage();	 Catch:{  }
        r4[r5] = r1;	 Catch:{  }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{  }
        goto L_0x001f;
    L_0x004c:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
    L_0x0050:
        r2 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r2 = b;
        r3 = new java.lang.String[r9];
        r4 = "Could not open file /proc/cpuinfo";
        r3[r7] = r4;
        r0 = r0.getMessage();
        r3[r8] = r0;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r7, r3);
        r0 = r1;
        goto L_0x001f;
    L_0x0066:
        r1 = move-exception;
        r10 = r1;
        r1 = r0;
        r0 = r10;
        goto L_0x0050;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.common.UmengMessageDeviceConfig.getCPU():java.lang.String");
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:10:0x0032  */
    public static java.lang.String getDeviceId(android.content.Context r8) {
        /*
        r7 = 2;
        r6 = 0;
        r5 = 1;
        r0 = "phone";
        r0 = r8.getSystemService(r0);
        r0 = (android.telephony.TelephonyManager) r0;
        if (r0 != 0) goto L_0x001c;
    L_0x000e:
        r1 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r1 = b;
        r2 = new java.lang.String[r5];
        r3 = "No IMEI.";
        r2[r6] = r3;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r1, r5, r2);
    L_0x001c:
        r1 = "";
        r2 = "android.permission.READ_PHONE_STATE";
        r2 = checkPermission(r8, r2);	 Catch:{ Exception -> 0x00ba }
        if (r2 == 0) goto L_0x00cf;
    L_0x0028:
        r0 = r0.getDeviceId();	 Catch:{ Exception -> 0x00ba }
    L_0x002c:
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x00b9;
    L_0x0032:
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r0 = b;
        r1 = new java.lang.String[r5];
        r2 = "No IMEI.";
        r1[r6] = r2;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r5, r1);
        r0 = getMac(r8);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x00b9;
    L_0x004a:
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r0 = b;
        r1 = new java.lang.String[r5];
        r2 = "Failed to take mac as IMEI. Try to use Secure.ANDROID_ID instead.";
        r1[r6] = r2;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r5, r1);
        r0 = r8.getContentResolver();
        r1 = "android_id";
        r0 = android.provider.Settings.Secure.getString(r0, r1);
        r1 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r1 = b;
        r2 = new java.lang.String[r5];
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "getDeviceId: Secure.ANDROID_ID: ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        r2[r6] = r3;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r1, r7, r2);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x00b9;
    L_0x0088:
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r0 = b;
        r1 = new java.lang.String[r5];
        r2 = "Failed to take Secure.ANDROID_ID as IMEI. Try to use Serial_number instead.";
        r1[r6] = r2;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r5, r1);
        r0 = getSerial_number();
        r1 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r1 = b;
        r2 = new java.lang.String[r5];
        r3 = new java.lang.StringBuilder;
        r3.<init>();
        r4 = "getDeviceId: Serial_number: ";
        r3 = r3.append(r4);
        r3 = r3.append(r0);
        r3 = r3.toString();
        r2[r6] = r3;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r1, r7, r2);
    L_0x00b9:
        return r0;
    L_0x00ba:
        r0 = move-exception;
        r2 = com.umeng.commonsdk.UMConfigure.umDebugLog;
        r2 = b;
        r3 = new java.lang.String[r7];
        r4 = "No IMEI.";
        r3[r6] = r4;
        r0 = r0.getMessage();
        r3[r5] = r0;
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r5, r3);
    L_0x00cf:
        r0 = r1;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.common.UmengMessageDeviceConfig.getDeviceId(android.content.Context):java.lang.String");
    }

    public static String getDIN(Context context) {
        UMLog uMLog;
        String str = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (telephonyManager == null) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 1, "No IMEI.");
        }
        try {
            if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                Object deviceId = telephonyManager.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)) {
                    return deviceId;
                }
            }
        } catch (Exception e) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 1, "No IMEI.", e.getMessage());
        }
        return "";
    }

    public static String getDINAes(Context context) {
        String str = "";
        String messageAppkey;
        try {
            str = getDIN(context);
            messageAppkey = PushAgent.getInstance(context).getMessageAppkey();
            if (messageAppkey == null || 24 != messageAppkey.length()) {
                return c.a(str, Constants.UTF_8);
            }
            return c.a(str, Constants.UTF_8, messageAppkey.substring(0, 16));
        } catch (Exception e) {
            Exception exception = e;
            messageAppkey = str;
            if (exception == null) {
                return messageAppkey;
            }
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 1, "getDINAes:" + r1.getMessage());
            return messageAppkey;
        }
    }

    public static String getAndroidId(Context context) {
        String string = System.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        if (string == null) {
            return "";
        }
        return string;
    }

    public static String getSerial_number() {
        String str = "";
        if (VERSION.SDK_INT <= 25) {
            str = Build.SERIAL;
        } else {
            try {
                Class cls = Class.forName("android.os.Build");
                str = (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
            } catch (Throwable th) {
                str = "";
            }
        }
        if (str == null) {
            return "";
        }
        return str;
    }

    public static String getDeviceIdUmengMD5(Context context) {
        return h.b(getDeviceId(context));
    }

    public static String getDeviceIdMD5(Context context) {
        return h.a(getDeviceId(context));
    }

    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (telephonyManager == null) {
                return a;
            }
            return telephonyManager.getNetworkOperatorName();
        } catch (Exception e) {
            e.printStackTrace();
            return a;
        }
    }

    public static String getDisplayResolution(Context context) {
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            return String.valueOf(displayMetrics.heightPixels) + "*" + String.valueOf(i);
        } catch (Exception e) {
            e.printStackTrace();
            return a;
        }
    }

    public static String[] getNetworkAccessMode(Context context) {
        String[] strArr = new String[]{a, a};
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            if (connectivityManager == null) {
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
        } catch (Exception e) {
        }
    }

    public static boolean isWiFiAvailable(Context context) {
        return "Wi-Fi".equals(getNetworkAccessMode(context)[0]);
    }

    public static Location getLocation(Context context) {
        UMLog uMLog;
        try {
            UMLog uMLog2;
            LocationManager locationManager = (LocationManager) context.getSystemService("location");
            if (checkPermission(context, "android.permission.ACCESS_FINE_LOCATION")) {
                Location mark = StubApp.mark(locationManager, "gps");
                if (mark != null) {
                    uMLog2 = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(b, 2, "get location from gps:" + mark.getLatitude() + MiPushClient.ACCEPT_TIME_SEPARATOR + mark.getLongitude());
                    return mark;
                }
            }
            if (checkPermission(context, "android.permission.ACCESS_COARSE_LOCATION")) {
                Location mark2 = StubApp.mark(locationManager, "network");
                if (mark2 != null) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(b, 2, "get location from network:" + mark2.getLatitude() + MiPushClient.ACCEPT_TIME_SEPARATOR + mark2.getLongitude());
                    return mark2;
                }
            }
            uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "Could not get location from GPS or Cell-id, lack ACCESS_COARSE_LOCATION or ACCESS_COARSE_LOCATION permission?");
            return null;
        } catch (Exception e) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, e.getMessage());
            return null;
        }
    }

    public static boolean isOnline(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null ? activeNetworkInfo.isConnectedOrConnecting() : false;
        } catch (Exception e) {
            return true;
        }
    }

    public static boolean isSdCardWrittenable() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static int getTimeZone(Context context) {
        try {
            Calendar instance = Calendar.getInstance(a(context));
            if (instance != null) {
                return instance.getTimeZone().getRawOffset() / 3600000;
            }
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "error in getTimeZone", e.getMessage());
        }
        return 8;
    }

    public static String[] getLocaleInfo(Context context) {
        String[] strArr = new String[2];
        try {
            Locale a = a(context);
            if (a != null) {
                strArr[0] = a.getCountry();
                strArr[1] = a.getLanguage();
            }
            if (TextUtils.isEmpty(strArr[0])) {
                strArr[0] = a;
            }
            if (TextUtils.isEmpty(strArr[1])) {
                strArr[1] = a;
            }
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, "error in getLocaleInfo", e.getMessage());
        }
        return strArr;
    }

    private static Locale a(Context context) {
        Locale locale = null;
        try {
            Configuration configuration = new Configuration();
            System.getConfiguration(context.getContentResolver(), configuration);
            if (configuration != null) {
                locale = configuration.locale;
            }
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, "fail to read user config locale");
        }
        if (locale == null) {
            return Locale.getDefault();
        }
        return locale;
    }

    public static String getAppkey(Context context) {
        return getMetaData(context, "UMENG_APPKEY");
    }

    public static String getMetaData(Context context, String str) {
        String string;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (applicationInfo != null) {
                string = applicationInfo.metaData.getString(str);
                if (string != null) {
                    return string.trim();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        UMLog uMLog = UMConfigure.umDebugLog;
        string = b;
        String[] strArr = new String[1];
        strArr[0] = String.format("Could not read meta-data %s from AndroidManifest.xml.", new Object[]{str});
        UMLog.mutlInfo(string, 0, strArr);
        return null;
    }

    public static String getMac(Context context) {
        if (VERSION.SDK_INT <= 22) {
            try {
                WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
                if (checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                    return wifiManager.getConnectionInfo().getMacAddress();
                }
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 1, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            } catch (Exception e) {
                UMLog uMLog2 = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 1, "Could not get mac address." + e.toString());
            }
        } else {
            try {
                for (NetworkInterface networkInterface : Collections.list(NetworkInterface.getNetworkInterfaces())) {
                    if (networkInterface.getName().equalsIgnoreCase("wlan0")) {
                        byte[] hardwareAddress = networkInterface.getHardwareAddress();
                        if (hardwareAddress == null) {
                            return "";
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        int length = hardwareAddress.length;
                        for (int i = 0; i < length; i++) {
                            stringBuilder.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                        }
                        if (stringBuilder.length() > 0) {
                            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                        }
                        return stringBuilder.toString();
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return "";
    }

    public static String getUmid(Context context) {
        String b = b(context);
        if (b == null) {
            return "";
        }
        return b;
    }

    private static String b(Context context) {
        return UMUtils.getUMId(context);
    }

    public static String getResolution(Context context) {
        try {
            int a;
            int a2;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            if ((context.getApplicationInfo().flags & 8192) == 0) {
                a = a(displayMetrics, "noncompatWidthPixels");
                a2 = a(displayMetrics, "noncompatHeightPixels");
            } else {
                a2 = -1;
                a = -1;
            }
            if (a == -1 || r0 == -1) {
                a = displayMetrics.widthPixels;
                a2 = displayMetrics.heightPixels;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(a);
            stringBuffer.append("*");
            stringBuffer.append(a2);
            return stringBuffer.toString();
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, "read resolution fail", e.getMessage());
            return a;
        }
    }

    private static int a(Object obj, String str) {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            if (declaredField != null) {
                declaredField.setAccessible(true);
                return declaredField.getInt(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static String getOperator(Context context) {
        try {
            return ((TelephonyManager) context.getSystemService(HttpConstant.PHONE)).getNetworkOperatorName();
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "read carrier fail", e.getMessage());
            return a;
        }
    }

    public static String getTimeString(Date date) {
        return new SimpleDateFormat(DateUtil.dateFormatYMDHMS, Locale.US).format(date);
    }

    public static String getToday() {
        return new SimpleDateFormat(DateUtil.dateFormatYMD, Locale.US).format(new Date());
    }

    public static Date toTime(String str) {
        try {
            return new SimpleDateFormat(DateUtil.dateFormatYMDHMS).parse(str);
        } catch (Exception e) {
            return null;
        }
    }

    public static int getIntervalSeconds(Date date, Date date2) {
        if (!date.after(date2)) {
            Date date3 = date2;
            date2 = date;
            date = date3;
        }
        return (int) ((date.getTime() - date2.getTime()) / 1000);
    }

    public static String getChannel(Context context) {
        String str = a;
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128);
            if (!(applicationInfo == null || applicationInfo.metaData == null)) {
                Object obj = applicationInfo.metaData.get("UMENG_CHANNEL");
                if (obj != null) {
                    String obj2 = obj.toString();
                    if (obj2 != null) {
                        return obj2;
                    }
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(b, 2, "在AndroidManifest.xml中读取不到UMENG_CHANNEL meta-data");
                }
            }
        } catch (Exception e) {
            UMLog uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "在AndroidManifest.xml中读取不到UMENG_CHANNEL meta-data");
            e.printStackTrace();
        }
        return str;
    }

    public static String getPackageName(Context context) {
        return context.getPackageName();
    }

    public static String getApplicationLable(Context context) {
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static boolean isDebug(Context context) {
        try {
            return (context.getApplicationInfo().flags & 2) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getUtdid(Context context) {
        try {
            return UTDevice.getUtdid(context);
        } catch (Throwable th) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 0, "获取utdid失败. " + th.getMessage());
            return "";
        }
    }

    public static boolean isServiceWork(Context context, String str, String str2) {
        List runningServices = ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningServices(Integer.MAX_VALUE);
        if (runningServices.size() <= 0) {
            return false;
        }
        for (int i = 0; i < runningServices.size(); i++) {
            String str3 = ((RunningServiceInfo) runningServices.get(i)).service.getClassName().toString();
            String str4 = ((RunningServiceInfo) runningServices.get(i)).service.getPackageName().toString();
            if (str3.equals(str) && str4.equals(str2)) {
                return true;
            }
        }
        return false;
    }

    public static String getServiceName(Context context, String str, String str2) {
        PackageManager packageManager = context.getPackageManager();
        Intent intent = new Intent();
        intent.setAction(str);
        List<ResolveInfo> queryIntentServices = packageManager.queryIntentServices(intent, 0);
        List arrayList = new ArrayList();
        if (queryIntentServices != null && queryIntentServices.size() > 0) {
            for (ResolveInfo resolveInfo : queryIntentServices) {
                if (resolveInfo.serviceInfo.packageName.equals(str2)) {
                    arrayList.add(resolveInfo);
                }
            }
        }
        if (arrayList == null || arrayList.size() <= 0) {
            return null;
        }
        return ((ResolveInfo) arrayList.get(0)).serviceInfo.name;
    }

    public static boolean isIntentExist(Context context, String str, String str2) {
        List queryIntentActivities = context.getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(str)), 0);
        if (queryIntentActivities.isEmpty()) {
            return false;
        }
        for (int i = 0; i < queryIntentActivities.size(); i++) {
            if (((ResolveInfo) queryIntentActivities.get(i)).activityInfo.packageName.equalsIgnoreCase(str2)) {
                return true;
            }
        }
        return false;
    }

    public static boolean getDataData(String str) {
        boolean exists = new File("/data/app/" + str + "-1.apk").exists();
        if (!exists) {
            exists = new File("/data/app/" + str + "-2.apk").exists();
        }
        if (!exists) {
            exists = new File("/data/app/" + str + ".apk").exists();
        }
        if (!exists) {
            exists = new File("/data/app/" + str + "-1").exists();
        }
        if (exists) {
            return exists;
        }
        return new File("/data/app/" + str + "-2").exists();
    }

    public static boolean isMIUI() {
        String str = "ro.miui.ui.version.code";
        str = "ro.miui.ui.version.name";
        str = "ro.miui.internal.storage";
        try {
            a g = a.g();
            if (g.a("ro.miui.ui.version.code", null) == null && g.a("ro.miui.ui.version.name", null) == null && g.a("ro.miui.internal.storage", null) == null) {
                return false;
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static boolean isMiui8() {
        try {
            String a = a.g().a("ro.miui.ui.version.name");
            if (a == null || !a.contains("8")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String isNotificationEnabled(Context context) {
        String str = "unknown";
        if (VERSION.SDK_INT >= 19) {
            try {
                AppOpsManager appOpsManager = (AppOpsManager) context.getSystemService("appops");
                ApplicationInfo applicationInfo = context.getApplicationInfo();
                String packageName = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName();
                int i = applicationInfo.uid;
                return String.valueOf(((Integer) Class.forName(AppOpsManager.class.getName()).getMethod("checkOpNoThrow", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(appOpsManager, new Object[]{Integer.valueOf(((Integer) Class.forName(AppOpsManager.class.getName()).getDeclaredField("OP_POST_NOTIFICATION").get(appOpsManager)).intValue()), Integer.valueOf(i), packageName})).intValue() == 0);
            } catch (Exception e) {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 0, "通知开关是否打开异常");
            }
        }
        return str;
    }
}
