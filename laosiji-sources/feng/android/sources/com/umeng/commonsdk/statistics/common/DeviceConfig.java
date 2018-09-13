package com.umeng.commonsdk.statistics.common;

import android.content.Context;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.feng.car.utils.HttpConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.meizu.cloud.pushsdk.notification.model.AdvanceSetting;
import com.taobao.accs.utl.UtilityImpl;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.statistics.AnalyticsConstants;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.message.MsgConstant;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import javax.microedition.khronos.opengles.GL10;

public class DeviceConfig {
    public static final int DEFAULT_TIMEZONE = 8;
    private static final String KEY_EMUI_VERSION_CODE = "ro.build.hw_emui_api_level";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    protected static final String LOG_TAG = DeviceConfig.class.getName();
    public static final String MOBILE_NETWORK = "2G/3G";
    public static final String UNKNOW = "";
    public static final String WIFI = "Wi-Fi";

    public static String getImei(Context context) {
        String deviceId;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
                if (telephonyManager != null && checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                    deviceId = telephonyManager.getDeviceId();
                    return deviceId;
                }
            } catch (Throwable e) {
                if (!AnalyticsConstants.UM_DEBUG) {
                    return null;
                }
                MLog.w("No IMEI.", e);
                return null;
            }
        }
        deviceId = null;
        return deviceId;
    }

    public static String getImeiNew(Context context) {
        Throwable th;
        String str;
        if (context != null) {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
                if (telephonyManager != null && checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                    if (VERSION.SDK_INT < 26) {
                        return telephonyManager.getDeviceId();
                    }
                    CharSequence charSequence;
                    try {
                        Method method = telephonyManager.getClass().getMethod("getImei", new Class[0]);
                        method.setAccessible(true);
                        charSequence = (String) method.invoke(telephonyManager, new Object[0]);
                    } catch (Exception e) {
                        charSequence = null;
                    }
                    try {
                        if (TextUtils.isEmpty(charSequence)) {
                            return telephonyManager.getDeviceId();
                        }
                        return charSequence;
                    } catch (Throwable e2) {
                        Throwable th2 = e2;
                        CharSequence str2 = charSequence;
                        th = th2;
                        if (!AnalyticsConstants.UM_DEBUG) {
                            return str2;
                        }
                        MLog.w("No IMEI.", th);
                        return str2;
                    }
                }
            } catch (Throwable e22) {
                th = e22;
                str2 = null;
            }
        }
        return null;
    }

    public static String getAndroidId(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        try {
            return Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
        } catch (Exception e) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return str;
            }
            MLog.w("can't read android id");
            return str;
        }
    }

    public static String getSerial() {
        if (VERSION.SDK_INT < 9) {
            return null;
        }
        if (VERSION.SDK_INT < 26) {
            return Build.SERIAL;
        }
        try {
            Class cls = Class.forName("android.os.Build");
            return (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getAppVersionCode(Context context) {
        return UMUtils.getAppVersionCode(context);
    }

    public static String getAppVersionName(Context context) {
        return UMUtils.getAppVersionName(context);
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
            } catch (Throwable th) {
                return false;
            }
        } else if (context.getPackageManager().checkPermission(str, context.getPackageName()) == 0) {
            return true;
        } else {
            return false;
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
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.e(LOG_TAG, "Could not read gpu infor:", th);
            }
            return new String[0];
        }
    }

    private static String getMacByJavaAPI() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
                if (!"wlan0".equals(networkInterface.getName())) {
                    if ("eth0".equals(networkInterface.getName())) {
                    }
                }
                byte[] hardwareAddress = networkInterface.getHardwareAddress();
                if (hardwareAddress == null || hardwareAddress.length == 0) {
                    return null;
                }
                StringBuilder stringBuilder = new StringBuilder();
                int length = hardwareAddress.length;
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(String.format("%02X:", new Object[]{Byte.valueOf(hardwareAddress[i])}));
                }
                if (stringBuilder.length() > 0) {
                    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
                }
                return stringBuilder.toString().toLowerCase(Locale.getDefault());
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private static String getMacShell() {
        int i = 0;
        try {
            String[] strArr = new String[]{"/sys/class/net/wlan0/address", "/sys/class/net/eth0/address", "/sys/devices/virtual/net/wlan0/address"};
            while (true) {
                int i2 = i;
                if (i2 >= strArr.length) {
                    break;
                }
                String reaMac = reaMac(strArr[i2]);
                if (reaMac != null) {
                    return reaMac;
                }
                i = i2 + 1;
            }
        } catch (Throwable th) {
        }
        return null;
    }

    private static String reaMac(String str) {
        Throwable th;
        String str2 = null;
        try {
            Reader fileReader = new FileReader(str);
            if (fileReader != null) {
                BufferedReader bufferedReader;
                try {
                    bufferedReader = new BufferedReader(fileReader, 1024);
                    try {
                        str2 = bufferedReader.readLine();
                        if (fileReader != null) {
                            try {
                                fileReader.close();
                            } catch (Throwable th2) {
                            }
                        }
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (Throwable th3) {
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                    }
                } catch (Throwable th5) {
                    th = th5;
                    bufferedReader = str2;
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (Throwable th6) {
                        }
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (Throwable th7) {
                        }
                    }
                    throw th;
                }
            }
        } catch (Throwable th8) {
        }
        return str2;
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e  */
    /* JADX WARNING: Removed duplicated region for block: B:9:0x001e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    public static java.lang.String getCPU() {
        /*
        r0 = 0;
        r1 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x004b }
        r2 = "/proc/cpuinfo";
        r1.<init>(r2);	 Catch:{ FileNotFoundException -> 0x004b }
        if (r1 == 0) goto L_0x001c;
    L_0x000b:
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x002f }
        r3 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;
        r2.<init>(r1, r3);	 Catch:{ Throwable -> 0x002f }
        r0 = r2.readLine();	 Catch:{ Throwable -> 0x002f }
        r2.close();	 Catch:{ FileNotFoundException -> 0x0039 }
        r1.close();	 Catch:{ FileNotFoundException -> 0x0039 }
    L_0x001c:
        if (r0 == 0) goto L_0x0047;
    L_0x001e:
        r1 = 58;
        r1 = r0.indexOf(r1);
        r1 = r1 + 1;
        r0 = r0.substring(r1);
        r0 = r0.trim();
    L_0x002e:
        return r0;
    L_0x002f:
        r1 = move-exception;
        r2 = LOG_TAG;	 Catch:{  }
        r3 = "Could not read from file /proc/cpuinfo";
        com.umeng.commonsdk.statistics.common.MLog.e(r2, r3, r1);	 Catch:{  }
        goto L_0x001c;
    L_0x0039:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
    L_0x003d:
        r2 = LOG_TAG;
        r3 = "Could not open file /proc/cpuinfo";
        com.umeng.commonsdk.statistics.common.MLog.e(r2, r3, r0);
        r0 = r1;
        goto L_0x001c;
    L_0x0047:
        r0 = "";
        goto L_0x002e;
    L_0x004b:
        r1 = move-exception;
        r4 = r1;
        r1 = r0;
        r0 = r4;
        goto L_0x003d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getCPU():java.lang.String");
    }

    public static String getDeviceId(Context context) {
        if (AnalyticsConstants.getDeviceType() == 2) {
            return getDeviceIdForBox(context);
        }
        return getDeviceIdForGeneral(context);
    }

    public static String getDeviceIdUmengMD5(Context context) {
        return HelperUtils.getUmengMD5(getDeviceId(context));
    }

    public static String getMCCMNC(Context context) {
        if (context == null || getImsi(context) == null) {
            return null;
        }
        int i = context.getResources().getConfiguration().mcc;
        int i2 = context.getResources().getConfiguration().mnc;
        if (i == 0) {
            return null;
        }
        String valueOf = String.valueOf(i2);
        if (i2 < 10) {
            valueOf = String.format("%02d", new Object[]{Integer.valueOf(i2)});
        }
        return new StringBuffer().append(String.valueOf(i)).append(valueOf).toString();
    }

    public static String getImsi(Context context) {
        if (context == null) {
            return null;
        }
        String subscriberId;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
            subscriberId = telephonyManager.getSubscriberId();
        } else {
            subscriberId = null;
        }
        return subscriberId;
    }

    public static String getRegisteredOperator(Context context) {
        if (context == null) {
            return null;
        }
        String networkOperator;
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
            networkOperator = telephonyManager.getNetworkOperator();
        } else {
            networkOperator = null;
        }
        return networkOperator;
    }

    public static String getNetworkOperatorName(Context context) {
        if (context == null) {
            return "";
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE) && telephonyManager != null) {
                return telephonyManager.getNetworkOperatorName();
            }
        } catch (Throwable th) {
        }
        return "";
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
        } catch (Throwable th) {
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
        } catch (Throwable th) {
        }
    }

    public static boolean isWiFiAvailable(Context context) {
        if (context == null) {
            return false;
        }
        return "Wi-Fi".equals(getNetworkAccessMode(context)[0]);
    }

    public static boolean isOnline(Context context) {
        if (context == null) {
            return false;
        }
        try {
            if (checkPermission(context, MsgConstant.PERMISSION_ACCESS_NETWORK_STATE)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
                if (connectivityManager != null) {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null) {
                        return activeNetworkInfo.isConnectedOrConnecting();
                    }
                }
            }
        } catch (Throwable th) {
        }
        return false;
    }

    public static int getTimeZone(Context context) {
        if (context == null) {
            return 8;
        }
        try {
            Calendar instance = Calendar.getInstance(getLocale(context));
            if (instance != null) {
                return instance.getTimeZone().getRawOffset() / 3600000;
            }
            return 8;
        } catch (Throwable th) {
            MLog.i(LOG_TAG, "error in getTimeZone", th);
            return 8;
        }
    }

    public static boolean isChineseAera(Context context) {
        if (context == null) {
            return false;
        }
        Object imprintProperty = UMEnvelopeBuild.imprintProperty(context, g.N, "");
        if (TextUtils.isEmpty(imprintProperty)) {
            if (getImsi(context) == null) {
                imprintProperty = getLocaleInfo(context)[0];
                if (TextUtils.isEmpty(imprintProperty) || !imprintProperty.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
                    return false;
                }
                return true;
            }
            int i = context.getResources().getConfiguration().mcc;
            if (i == 460 || i == 461) {
                return true;
            }
            if (i != 0) {
                return false;
            }
            imprintProperty = getLocaleInfo(context)[0];
            if (TextUtils.isEmpty(imprintProperty) || !imprintProperty.equalsIgnoreCase(AdvanceSetting.CLEAR_NOTIFICATION)) {
                return false;
            }
            return true;
        } else if (imprintProperty.equals(AdvanceSetting.CLEAR_NOTIFICATION)) {
            return true;
        } else {
            return false;
        }
    }

    public static String[] getLocaleInfo(Context context) {
        String[] strArr = new String[]{"Unknown", "Unknown"};
        if (context != null) {
            try {
                Locale locale = getLocale(context);
                if (locale != null) {
                    strArr[0] = locale.getCountry();
                    strArr[1] = locale.getLanguage();
                }
                if (TextUtils.isEmpty(strArr[0])) {
                    strArr[0] = "Unknown";
                }
                if (TextUtils.isEmpty(strArr[1])) {
                    strArr[1] = "Unknown";
                }
            } catch (Throwable th) {
                MLog.e(LOG_TAG, "error in getLocaleInfo", th);
            }
        }
        return strArr;
    }

    private static Locale getLocale(Context context) {
        Locale locale = null;
        if (context == null) {
            return Locale.getDefault();
        }
        try {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            System.getConfiguration(context.getContentResolver(), configuration);
            if (configuration != null) {
                locale = configuration.locale;
            }
        } catch (Throwable th) {
            MLog.e(LOG_TAG, "fail to read user config locale");
        }
        if (locale == null) {
            return Locale.getDefault();
        }
        return locale;
    }

    public static String getMac(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        if (VERSION.SDK_INT < 23) {
            return getMacBySystemInterface(context);
        }
        if (VERSION.SDK_INT == 23) {
            str = getMacByJavaAPI();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            if (AnalyticsConstants.CHECK_DEVICE) {
                return getMacShell();
            }
            return getMacBySystemInterface(context);
        }
        str = getMacByJavaAPI();
        if (TextUtils.isEmpty(str)) {
            return getMacBySystemInterface(context);
        }
        return str;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifiManager = (WifiManager) context.getSystemService(UtilityImpl.NET_TYPE_WIFI);
            if (checkPermission(context, MsgConstant.PERMISSION_ACCESS_WIFI_STATE)) {
                return wifiManager.getConnectionInfo().getMacAddress();
            }
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w(LOG_TAG, "Could not get mac address.[no permission android.permission.ACCESS_WIFI_STATE");
            }
            return "";
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w(LOG_TAG, "Could not get mac address." + th.toString());
            }
            return "";
        }
    }

    public static int[] getResolutionArray(Context context) {
        if (context == null) {
            return null;
        }
        try {
            int reflectMetrics;
            int reflectMetrics2;
            int i;
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            if ((context.getApplicationInfo().flags & 8192) == 0) {
                reflectMetrics = reflectMetrics(displayMetrics, "noncompatWidthPixels");
                reflectMetrics2 = reflectMetrics(displayMetrics, "noncompatHeightPixels");
            } else {
                reflectMetrics2 = -1;
                reflectMetrics = -1;
            }
            if (reflectMetrics == -1 || reflectMetrics2 == -1) {
                i = displayMetrics.widthPixels;
                reflectMetrics = displayMetrics.heightPixels;
            } else {
                i = reflectMetrics;
                reflectMetrics = reflectMetrics2;
            }
            int[] iArr = new int[2];
            if (i > reflectMetrics) {
                iArr[0] = reflectMetrics;
                iArr[1] = i;
                return iArr;
            }
            iArr[0] = i;
            iArr[1] = reflectMetrics;
            return iArr;
        } catch (Throwable th) {
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.e(LOG_TAG, "read resolution fail", th);
            }
            return null;
        }
    }

    private static int reflectMetrics(Object obj, String str) {
        try {
            Field declaredField = DisplayMetrics.class.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField.getInt(obj);
        } catch (Throwable th) {
            return -1;
        }
    }

    public static String getPackageName(Context context) {
        if (context == null) {
            return null;
        }
        return context.getPackageName();
    }

    public static String getAppSHA1Key(Context context) {
        try {
            return byte2HexFormatted(MessageDigest.getInstance("SHA1").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Exception e) {
            return null;
        }
    }

    public static String getAppHashKey(Context context) {
        try {
            Signature[] signatureArr = context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures;
            if (0 < signatureArr.length) {
                Signature signature = signatureArr[0];
                MessageDigest instance = MessageDigest.getInstance("SHA");
                instance.update(signature.toByteArray());
                return Base64.encodeToString(instance.digest(), 0).trim();
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static String getAppMD5Signature(Context context) {
        if (context == null) {
            return null;
        }
        String byte2HexFormatted;
        try {
            byte2HexFormatted = byte2HexFormatted(MessageDigest.getInstance("MD5").digest(((X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(context.getPackageManager().getPackageInfo(getPackageName(context), 64).signatures[0].toByteArray()))).getEncoded()));
        } catch (Throwable th) {
            byte2HexFormatted = null;
        }
        return byte2HexFormatted;
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
            stringBuilder.append(toHexString.toUpperCase(Locale.getDefault()));
            if (i < bArr.length - 1) {
                stringBuilder.append(':');
            }
        }
        return stringBuilder.toString();
    }

    public static String getApplicationLable(Context context) {
        if (context == null) {
            return "";
        }
        return context.getPackageManager().getApplicationLabel(context.getApplicationInfo()).toString();
    }

    public static String getAppName(Context context) {
        String str = null;
        if (context == null) {
            return str;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).applicationInfo.loadLabel(context.getPackageManager()).toString();
        } catch (Throwable th) {
            if (!AnalyticsConstants.UM_DEBUG) {
                return str;
            }
            MLog.i(LOG_TAG, th);
            return str;
        }
    }

    public static String getDeviceIdForGeneral(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        if (VERSION.SDK_INT < 23) {
            str = getIMEI(context);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.w(LOG_TAG, "No IMEI.");
            }
            str = getMacBySystemInterface(context);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + str);
            }
            if (TextUtils.isEmpty(str)) {
                return getSerialNo();
            }
            return str;
        } else if (VERSION.SDK_INT == 23) {
            str = getIMEI(context);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacByJavaAPI();
            if (TextUtils.isEmpty(str)) {
                if (AnalyticsConstants.CHECK_DEVICE) {
                    str = getMacShell();
                } else {
                    str = getMacBySystemInterface(context);
                }
            }
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, MAC: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + str);
            }
            if (TextUtils.isEmpty(str)) {
                return getSerialNo();
            }
            return str;
        } else {
            str = getIMEI(context);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getSerialNo();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacByJavaAPI();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacBySystemInterface(context);
            if (!AnalyticsConstants.UM_DEBUG) {
                return str;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + str);
            return str;
        }
    }

    public static String getDeviceIdForBox(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        if (VERSION.SDK_INT < 23) {
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacBySystemInterface(context);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, MAC: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getSerialNo();
            if (TextUtils.isEmpty(str)) {
                return getIMEI(context);
            }
            return str;
        } else if (VERSION.SDK_INT == 23) {
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, ANDROID_ID: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacByJavaAPI();
            if (TextUtils.isEmpty(str)) {
                if (AnalyticsConstants.CHECK_DEVICE) {
                    str = getMacShell();
                } else {
                    str = getMacBySystemInterface(context);
                }
            }
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId, MAC: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getSerialNo();
            if (TextUtils.isEmpty(str)) {
                return getIMEI(context);
            }
            return str;
        } else {
            str = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
            if (AnalyticsConstants.UM_DEBUG) {
                MLog.i(LOG_TAG, "getDeviceId: ANDROID_ID: " + str);
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getSerialNo();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getIMEI(context);
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacByJavaAPI();
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = getMacBySystemInterface(context);
            if (!AnalyticsConstants.UM_DEBUG) {
                return str;
            }
            MLog.i(LOG_TAG, "getDeviceId, MAC: " + str);
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x004b  */
    private static java.lang.String getIMEI(android.content.Context r7) {
        /*
        r1 = "";
        if (r7 != 0) goto L_0x0006;
    L_0x0005:
        return r1;
    L_0x0006:
        r0 = "phone";
        r0 = r7.getSystemService(r0);
        r0 = (android.telephony.TelephonyManager) r0;
        if (r0 == 0) goto L_0x0056;
    L_0x0011:
        r2 = "android.permission.READ_PHONE_STATE";
        r2 = checkPermission(r7, r2);	 Catch:{ Throwable -> 0x0043 }
        if (r2 == 0) goto L_0x0056;
    L_0x001a:
        r0 = r0.getDeviceId();	 Catch:{ Throwable -> 0x0043 }
        r1 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;	 Catch:{ Throwable -> 0x0054 }
        if (r1 == 0) goto L_0x0041;
    L_0x0022:
        r1 = LOG_TAG;	 Catch:{ Throwable -> 0x0054 }
        r2 = 1;
        r2 = new java.lang.Object[r2];	 Catch:{ Throwable -> 0x0054 }
        r3 = 0;
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0054 }
        r4.<init>();	 Catch:{ Throwable -> 0x0054 }
        r5 = "getDeviceId, IMEI: ";
        r4 = r4.append(r5);	 Catch:{ Throwable -> 0x0054 }
        r4 = r4.append(r0);	 Catch:{ Throwable -> 0x0054 }
        r4 = r4.toString();	 Catch:{ Throwable -> 0x0054 }
        r2[r3] = r4;	 Catch:{ Throwable -> 0x0054 }
        com.umeng.commonsdk.statistics.common.MLog.i(r1, r2);	 Catch:{ Throwable -> 0x0054 }
    L_0x0041:
        r1 = r0;
        goto L_0x0005;
    L_0x0043:
        r0 = move-exception;
        r6 = r0;
        r0 = r1;
        r1 = r6;
    L_0x0047:
        r2 = com.umeng.commonsdk.statistics.AnalyticsConstants.UM_DEBUG;
        if (r2 == 0) goto L_0x0041;
    L_0x004b:
        r2 = LOG_TAG;
        r3 = "No IMEI.";
        com.umeng.commonsdk.statistics.common.MLog.w(r2, r3, r1);
        goto L_0x0041;
    L_0x0054:
        r1 = move-exception;
        goto L_0x0047;
    L_0x0056:
        r0 = r1;
        goto L_0x0041;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getIMEI(android.content.Context):java.lang.String");
    }

    private static String getSerialNo() {
        String str;
        String str2 = "";
        if (VERSION.SDK_INT < 9) {
            str = str2;
        } else if (VERSION.SDK_INT >= 26) {
            try {
                Class cls = Class.forName("android.os.Build");
                str = (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
            } catch (Throwable th) {
                str = str2;
            }
        } else {
            str = Build.SERIAL;
        }
        if (AnalyticsConstants.UM_DEBUG) {
            MLog.i(LOG_TAG, "getDeviceId, serial no: " + str);
        }
        return str;
    }

    public static String getSubOSName(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            if (!TextUtils.isEmpty(property)) {
                return "MIUI";
            }
            if (isFlyMe()) {
                return "Flyme";
            }
            if (isEmui(buildProp)) {
                return "Emui";
            }
            if (TextUtils.isEmpty(getYunOSVersion(buildProp))) {
                return property;
            }
            return "YunOS";
        } catch (Throwable th) {
            return null;
        }
    }

    public static String getSubOSVersion(Context context) {
        Properties buildProp = getBuildProp();
        try {
            String property = buildProp.getProperty(KEY_MIUI_VERSION_NAME);
            if (!TextUtils.isEmpty(property)) {
                return property;
            }
            if (isFlyMe()) {
                try {
                    return getFlymeVersion(buildProp);
                } catch (Throwable th) {
                    return property;
                }
            } else if (isEmui(buildProp)) {
                try {
                    return getEmuiVersion(buildProp);
                } catch (Throwable th2) {
                    return property;
                }
            } else {
                try {
                    return getYunOSVersion(buildProp);
                } catch (Throwable th3) {
                    return property;
                }
            }
        } catch (Throwable th4) {
            return null;
        }
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
        } catch (Throwable th) {
        }
        return null;
    }

    private static String getEmuiVersion(Properties properties) {
        String str = null;
        try {
            return properties.getProperty(KEY_EMUI_VERSION_CODE, null);
        } catch (Exception e) {
            return str;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0024 A:{SYNTHETIC, Splitter: B:12:0x0024} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002d A:{SYNTHETIC, Splitter: B:17:0x002d} */
    private static java.util.Properties getBuildProp() {
        /*
        r2 = new java.util.Properties;
        r2.<init>();
        r1 = 0;
        r0 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x0020, all -> 0x002a }
        r3 = new java.io.File;	 Catch:{ Throwable -> 0x0020, all -> 0x002a }
        r4 = android.os.Environment.getRootDirectory();	 Catch:{ Throwable -> 0x0020, all -> 0x002a }
        r5 = "build.prop";
        r3.<init>(r4, r5);	 Catch:{ Throwable -> 0x0020, all -> 0x002a }
        r0.<init>(r3);	 Catch:{ Throwable -> 0x0020, all -> 0x002a }
        r2.load(r0);	 Catch:{ Throwable -> 0x003a, all -> 0x0035 }
        if (r0 == 0) goto L_0x001f;
    L_0x001c:
        r0.close();	 Catch:{ Throwable -> 0x0031 }
    L_0x001f:
        return r2;
    L_0x0020:
        r0 = move-exception;
        r0 = r1;
    L_0x0022:
        if (r0 == 0) goto L_0x001f;
    L_0x0024:
        r0.close();	 Catch:{ Throwable -> 0x0028 }
        goto L_0x001f;
    L_0x0028:
        r0 = move-exception;
        goto L_0x001f;
    L_0x002a:
        r0 = move-exception;
    L_0x002b:
        if (r1 == 0) goto L_0x0030;
    L_0x002d:
        r1.close();	 Catch:{ Throwable -> 0x0033 }
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
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.commonsdk.statistics.common.DeviceConfig.getBuildProp():java.util.Properties");
    }

    private static boolean isFlyMe() {
        try {
            Build.class.getMethod("hasSmartBar", new Class[0]);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    private static boolean isEmui(Properties properties) {
        try {
            if (properties.getProperty(KEY_EMUI_VERSION_CODE, null) != null) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getDeviceType(Context context) {
        String str = "Phone";
        if (context == null) {
            return str;
        }
        if (((context.getResources().getConfiguration().screenLayout & 15) >= 3 ? 1 : null) != null) {
            return "Tablet";
        }
        return "Phone";
    }

    public static String getDBencryptID(Context context) {
        if (context == null) {
            return null;
        }
        try {
            String str;
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            if (telephonyManager == null || !checkPermission(context, MsgConstant.PERMISSION_READ_PHONE_STATE)) {
                str = null;
            } else {
                str = telephonyManager.getDeviceId();
            }
            try {
                if (!TextUtils.isEmpty(str)) {
                    return str;
                }
                CharSequence string = Secure.getString(context.getContentResolver(), SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID);
                try {
                    if (!TextUtils.isEmpty(string) || VERSION.SDK_INT < 9) {
                        return string;
                    }
                    if (VERSION.SDK_INT < 26) {
                        return Build.SERIAL;
                    }
                    try {
                        Class cls = Class.forName("android.os.Build");
                        return (String) cls.getMethod("getSerial", new Class[0]).invoke(cls, new Object[0]);
                    } catch (Throwable th) {
                        return string;
                    }
                } catch (Throwable th2) {
                    return string;
                }
            } catch (Throwable th3) {
                return str;
            }
        } catch (Throwable th4) {
            return null;
        }
    }
}
