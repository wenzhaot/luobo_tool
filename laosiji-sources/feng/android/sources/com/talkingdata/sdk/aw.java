package com.talkingdata.sdk;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.media.AudioManager;
import android.nfc.NfcAdapter;
import android.nfc.NfcManager;
import android.nfc.cardemulation.CardEmulation;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.feng.car.utils.HttpConstant;
import com.taobao.accs.utl.UtilityImpl;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class aw {
    public static final String a = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq";
    public static final String b = "/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_min_freq";
    public static final String c = "/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq";
    private static final int d = 3600000;
    private static final Pattern e = Pattern.compile("([0-9]+)");
    private static final Pattern f = Pattern.compile("\\s*([0-9]+)");
    private static final FileFilter g = new ax();
    private static BroadcastReceiver h = new ay();

    /* compiled from: td */
    public static class a {
        static final int HCE_ENABLED = 3;
        static final int NFC_ENABLED = 2;
        static final int NOT_ENALBED = 1;
        static final int UNKNOWN = 0;
    }

    public static String a() {
        return ab.j + VERSION.RELEASE;
    }

    public static String b() {
        try {
            return Build.ID;
        } catch (Throwable th) {
            return "";
        }
    }

    public static String c() {
        return Build.MANUFACTURER.trim();
    }

    public static String d() {
        return Build.BRAND.trim();
    }

    public static String e() {
        return Build.MODEL.trim();
    }

    public static int f() {
        return TimeZone.getDefault().getRawOffset() / d;
    }

    public static String g() {
        try {
            String trim = Build.MODEL.trim();
            String a = a(Build.MANUFACTURER.trim(), trim);
            if (TextUtils.isEmpty(a)) {
                a = a(Build.BRAND.trim(), trim);
            }
            if (a == null) {
                a = "";
            }
            return ":" + trim;
        } catch (Throwable th) {
            return "";
        }
    }

    public static String h() {
        String str = "unknown";
        try {
            if (bo.a(14)) {
                return Build.getRadioVersion();
            }
            return str;
        } catch (Throwable th) {
            return str;
        }
    }

    public static JSONObject a(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("nfcStatus", b(context));
            jSONObject.put("appsRegistedHCE", q(context));
            jSONObject.put("ssMode", r(context));
            return jSONObject;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    public static int b(Context context) {
        if (context == null) {
            return 0;
        }
        int i;
        try {
            if (bo.a(10)) {
                NfcAdapter defaultAdapter = ((NfcManager) context.getSystemService("nfc")).getDefaultAdapter();
                if (defaultAdapter != null) {
                    if (!defaultAdapter.isEnabled()) {
                        i = 1;
                    } else if (bo.a(19) && context.getPackageManager().hasSystemFeature("android.hardware.nfc.hce")) {
                        i = 3;
                    } else {
                        i = 2;
                    }
                    return i;
                }
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        i = 0;
        return i;
    }

    private static JSONArray q(Context context) {
        if (!bo.a(19)) {
            return null;
        }
        try {
            JSONArray jSONArray = new JSONArray();
            List<PackageInfo> a = a(context, 4);
            if (a != null) {
                for (PackageInfo packageInfo : a) {
                    if (packageInfo != null) {
                        ServiceInfo[] serviceInfoArr = packageInfo.services;
                        if (serviceInfoArr != null) {
                            for (ServiceInfo serviceInfo : serviceInfoArr) {
                                try {
                                    Bundle bundle = context.getPackageManager().getServiceInfo(new ComponentName(serviceInfo.packageName, serviceInfo.name), 128).metaData;
                                    if (bundle != null && bundle.containsKey("android.nfc.cardemulation.host_apdu_service")) {
                                        jSONArray.put(packageInfo.packageName);
                                        break;
                                    }
                                } catch (Throwable th) {
                                }
                            }
                        }
                    }
                }
            }
            return jSONArray;
        } catch (Throwable th2) {
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:? A:{SYNTHETIC, RETURN, ORIG_RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0049 A:{SYNTHETIC, Splitter: B:16:0x0049} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x005d A:{SYNTHETIC, Splitter: B:27:0x005d} */
    private static java.util.List a(android.content.Context r7, int r8) {
        /*
        r1 = r7.getPackageManager();
        r0 = r1.getInstalledPackages(r8);	 Catch:{ Throwable -> 0x0009 }
    L_0x0008:
        return r0;
    L_0x0009:
        r0 = move-exception;
        r0 = new java.util.ArrayList;
        r0.<init>();
        r3 = 0;
        r2 = java.lang.Runtime.getRuntime();	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r4 = "pm list packages";
        r4 = r2.exec(r4);	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r2 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r5 = new java.io.InputStreamReader;	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r6 = r4.getInputStream();	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r5.<init>(r6);	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
        r2.<init>(r5);	 Catch:{ Throwable -> 0x0066, all -> 0x005a }
    L_0x0029:
        r3 = r2.readLine();	 Catch:{ Throwable -> 0x0043 }
        if (r3 == 0) goto L_0x004f;
    L_0x002f:
        r5 = 58;
        r5 = r3.indexOf(r5);	 Catch:{ Throwable -> 0x0043 }
        r5 = r5 + 1;
        r3 = r3.substring(r5);	 Catch:{ Throwable -> 0x0043 }
        r3 = r1.getPackageInfo(r3, r8);	 Catch:{ Throwable -> 0x0043 }
        r0.add(r3);	 Catch:{ Throwable -> 0x0043 }
        goto L_0x0029;
    L_0x0043:
        r1 = move-exception;
    L_0x0044:
        com.talkingdata.sdk.cs.postSDKError(r1);	 Catch:{ all -> 0x0063 }
        if (r2 == 0) goto L_0x0008;
    L_0x0049:
        r2.close();	 Catch:{ Throwable -> 0x004d }
        goto L_0x0008;
    L_0x004d:
        r1 = move-exception;
        goto L_0x0008;
    L_0x004f:
        r4.waitFor();	 Catch:{ Throwable -> 0x0043 }
        if (r2 == 0) goto L_0x0008;
    L_0x0054:
        r2.close();	 Catch:{ Throwable -> 0x0058 }
        goto L_0x0008;
    L_0x0058:
        r1 = move-exception;
        goto L_0x0008;
    L_0x005a:
        r0 = move-exception;
    L_0x005b:
        if (r3 == 0) goto L_0x0060;
    L_0x005d:
        r3.close();	 Catch:{ Throwable -> 0x0061 }
    L_0x0060:
        throw r0;
    L_0x0061:
        r1 = move-exception;
        goto L_0x0060;
    L_0x0063:
        r0 = move-exception;
        r3 = r2;
        goto L_0x005b;
    L_0x0066:
        r1 = move-exception;
        r2 = r3;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.aw.a(android.content.Context, int):java.util.List");
    }

    private static int r(Context context) {
        try {
            if (bo.a(19)) {
                return CardEmulation.getInstance(((NfcManager) context.getSystemService("nfc")).getDefaultAdapter()).getSelectionModeForCategory("payment");
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return -1;
    }

    private static String a(String str, String str2) {
        try {
            CharSequence toLowerCase = str.toLowerCase();
            return (toLowerCase.startsWith("unknown") || toLowerCase.startsWith("alps") || toLowerCase.startsWith("android") || toLowerCase.startsWith("sprd") || toLowerCase.startsWith("spreadtrum") || toLowerCase.startsWith("rockchip") || toLowerCase.startsWith("wondermedia") || toLowerCase.startsWith("mtk") || toLowerCase.startsWith("mt65") || toLowerCase.startsWith("nvidia") || toLowerCase.startsWith("brcm") || toLowerCase.startsWith("marvell") || str2.toLowerCase().contains(toLowerCase)) ? null : str;
        } catch (Throwable th) {
            return null;
        }
    }

    public static int i() {
        return VERSION.SDK_INT;
    }

    public static String j() {
        return VERSION.RELEASE;
    }

    public static String k() {
        return Locale.getDefault().getLanguage();
    }

    public static String l() {
        return Locale.getDefault().getCountry();
    }

    public static JSONObject a(Context context, JSONObject jSONObject) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                int i = displayMetrics.widthPixels;
                int i2 = displayMetrics.heightPixels;
                jSONObject.put("pixel", Math.min(i, i2) + "*" + Math.max(i, i2) + "*" + displayMetrics.densityDpi);
                jSONObject.put("densityDpi", displayMetrics.densityDpi);
            }
        } catch (Throwable th) {
        }
        return jSONObject;
    }

    public static JSONObject b(Context context, JSONObject jSONObject) {
        try {
            jSONObject.put("brightness", p(context));
        } catch (Throwable th) {
        }
        return jSONObject;
    }

    public static String c(Context context) {
        try {
            DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
            if (displayMetrics != null) {
                int i = displayMetrics.widthPixels;
                int i2 = displayMetrics.heightPixels;
                return Math.min(i, i2) + "*" + Math.max(i, i2) + "*" + displayMetrics.densityDpi;
            }
        } catch (Throwable th) {
        }
        return "";
    }

    public static String[] m() {
        int i;
        Object obj = 1;
        String[] strArr = new String[4];
        for (i = 0; i < 4; i++) {
            strArr[i] = "";
        }
        List arrayList = new ArrayList();
        Reader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/cpuinfo");
            bufferedReader = new BufferedReader(fileReader, 1024);
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    arrayList.add(readLine);
                } else {
                    try {
                        break;
                    } catch (IOException e) {
                    }
                }
            }
            bufferedReader.close();
            fileReader.close();
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        String[] strArr2 = new String[]{"Processor\\s*:\\s*(.*)", "CPU\\s*variant\\s*:\\s*0x(.*)", "Hardware\\s*:\\s*(.*)"};
        if (obj != null) {
            int size = arrayList.size();
            for (int i2 = 0; i2 < 3; i2++) {
                Pattern compile = Pattern.compile(strArr2[i2]);
                for (i = 0; i < size; i++) {
                    Matcher matcher = compile.matcher((String) arrayList.get(i));
                    if (matcher.find()) {
                        strArr[i2] = matcher.toMatchResult().group(1);
                    }
                }
            }
        }
        strArr[3] = c(a);
        return strArr;
    }

    public static JSONObject n() {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("name", m()[2]);
            jSONObject.put("coreNum", o());
            jSONObject.put("maxFreq", a(a));
            jSONObject.put("minFreq", a(b));
            jSONObject.put("curFreq", a(c));
            return jSONObject;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0030 A:{SYNTHETIC, Splitter: B:19:0x0030} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0035 A:{SYNTHETIC, Splitter: B:22:0x0035} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003f A:{SYNTHETIC, Splitter: B:28:0x003f} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0044 A:{SYNTHETIC, Splitter: B:31:0x0044} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003f A:{SYNTHETIC, Splitter: B:28:0x003f} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0044 A:{SYNTHETIC, Splitter: B:31:0x0044} */
    public static int a(java.lang.String r4) {
        /*
        r2 = 0;
        r0 = -1;
        r1 = android.text.TextUtils.isEmpty(r4);
        if (r1 == 0) goto L_0x0009;
    L_0x0008:
        return r0;
    L_0x0009:
        r3 = new java.io.FileReader;	 Catch:{ Throwable -> 0x002c, all -> 0x003b }
        r3.<init>(r4);	 Catch:{ Throwable -> 0x002c, all -> 0x003b }
        r1 = new java.io.BufferedReader;	 Catch:{ Throwable -> 0x0055, all -> 0x0050 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x0055, all -> 0x0050 }
        r2 = r1.readLine();	 Catch:{ Throwable -> 0x0059, all -> 0x0052 }
        r2 = r2.trim();	 Catch:{ Throwable -> 0x0059, all -> 0x0052 }
        r0 = java.lang.Integer.parseInt(r2);	 Catch:{ Throwable -> 0x0059, all -> 0x0052 }
        if (r3 == 0) goto L_0x0024;
    L_0x0021:
        r3.close();	 Catch:{ Throwable -> 0x0048 }
    L_0x0024:
        if (r1 == 0) goto L_0x0008;
    L_0x0026:
        r1.close();	 Catch:{ Throwable -> 0x002a }
        goto L_0x0008;
    L_0x002a:
        r1 = move-exception;
        goto L_0x0008;
    L_0x002c:
        r1 = move-exception;
        r1 = r2;
    L_0x002e:
        if (r2 == 0) goto L_0x0033;
    L_0x0030:
        r2.close();	 Catch:{ Throwable -> 0x004a }
    L_0x0033:
        if (r1 == 0) goto L_0x0008;
    L_0x0035:
        r1.close();	 Catch:{ Throwable -> 0x0039 }
        goto L_0x0008;
    L_0x0039:
        r1 = move-exception;
        goto L_0x0008;
    L_0x003b:
        r0 = move-exception;
        r3 = r2;
    L_0x003d:
        if (r3 == 0) goto L_0x0042;
    L_0x003f:
        r3.close();	 Catch:{ Throwable -> 0x004c }
    L_0x0042:
        if (r2 == 0) goto L_0x0047;
    L_0x0044:
        r2.close();	 Catch:{ Throwable -> 0x004e }
    L_0x0047:
        throw r0;
    L_0x0048:
        r2 = move-exception;
        goto L_0x0024;
    L_0x004a:
        r2 = move-exception;
        goto L_0x0033;
    L_0x004c:
        r1 = move-exception;
        goto L_0x0042;
    L_0x004e:
        r1 = move-exception;
        goto L_0x0047;
    L_0x0050:
        r0 = move-exception;
        goto L_0x003d;
    L_0x0052:
        r0 = move-exception;
        r2 = r1;
        goto L_0x003d;
    L_0x0055:
        r1 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x002e;
    L_0x0059:
        r2 = move-exception;
        r2 = r3;
        goto L_0x002e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.aw.a(java.lang.String):int");
    }

    public static int o() {
        try {
            File[] listFiles = new File("/sys/devices/system/cpu/").listFiles(g);
            if (listFiles != null) {
                return listFiles.length;
            }
            return 1;
        } catch (Throwable th) {
            return 1;
        }
    }

    public static String[] p() {
        return null;
    }

    public static int[] q() {
        int[] iArr = new int[]{0, 0};
        try {
            if ("mounted".equals(Environment.getExternalStorageState())) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                int blockSize = statFs.getBlockSize();
                int blockCount = statFs.getBlockCount();
                int availableBlocks = statFs.getAvailableBlocks();
                iArr[0] = (blockCount * (blockSize / 512)) / 2;
                iArr[1] = ((blockSize / 512) * availableBlocks) / 2;
            }
        } catch (Throwable th) {
        }
        return iArr;
    }

    public static int[] r() {
        int i = 0;
        int[] iArr = new int[]{0, 0};
        int[] iArr2 = new int[4];
        for (int i2 = 0; i2 < 4; i2++) {
            iArr2[i2] = 0;
        }
        Reader fileReader;
        BufferedReader bufferedReader;
        try {
            fileReader = new FileReader("/proc/meminfo");
            bufferedReader = new BufferedReader(fileReader, 1024);
            while (i < 4) {
                iArr2[i] = b(bufferedReader.readLine());
                i++;
            }
            iArr[0] = iArr2[0];
            iArr[1] = iArr2[3] + (iArr2[1] + iArr2[2]);
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
            }
        } catch (IOException e2) {
            try {
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e3) {
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
        return iArr;
    }

    public static int[] s() {
        try {
            r0 = new int[4];
            StatFs statFs = new StatFs(Environment.getDataDirectory().getAbsolutePath());
            r0[0] = (statFs.getBlockCount() * (statFs.getBlockSize() / 512)) / 2;
            r0[1] = ((statFs.getBlockSize() / 512) * statFs.getAvailableBlocks()) / 2;
            statFs = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            r0[2] = (statFs.getBlockCount() * (statFs.getBlockSize() / 512)) / 2;
            r0[3] = ((statFs.getBlockSize() / 512) * statFs.getAvailableBlocks()) / 2;
            return r0;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    private static int b(String str) {
        try {
            String str2 = "";
            Matcher matcher = e.matcher(str);
            if (matcher.find()) {
                str2 = matcher.toMatchResult().group(0);
            }
            return Integer.parseInt(str2);
        } catch (Throwable e) {
            cs.postSDKError(e);
            return 0;
        }
    }

    public static int t() {
        try {
            Matcher matcher = f.matcher(c("/sys/class/power_supply/battery/full_bat"));
            if (matcher.find()) {
                return Integer.parseInt(matcher.toMatchResult().group(0));
            }
            return 0;
        } catch (Exception e) {
            return 0;
        }
    }

    private static String c(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            Reader fileReader = new FileReader(str);
            try {
                char[] cArr = new char[1024];
                BufferedReader bufferedReader = new BufferedReader(fileReader, 1024);
                while (true) {
                    int read = bufferedReader.read(cArr, 0, 1024);
                    if (-1 == read) {
                        break;
                    }
                    stringBuffer.append(new String(cArr, 0, read));
                }
                bufferedReader.close();
                fileReader.close();
            } catch (IOException e) {
            }
        } catch (Throwable th) {
        }
        return stringBuffer.toString();
    }

    public static JSONObject d(Context context) {
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("mobile", l(context));
            jSONObject.put(UtilityImpl.NET_TYPE_WIFI, g(context));
            jSONObject.put("gps", f(context));
            jSONObject.put("telephone", k(context));
            jSONObject.put("nfc", i(context));
            jSONObject.put("bluetooth", h(context));
            jSONObject.put("otg", e(context));
            jSONObject.put("insertEarphones", j(context));
            return jSONObject;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    public static boolean e(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || !packageManager.hasSystemFeature("android.hardware.usb.host")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean f(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || !packageManager.hasSystemFeature("android.hardware.location.gps")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean g(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || !packageManager.hasSystemFeature("android.hardware.wifi")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean h(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || !packageManager.hasSystemFeature("android.hardware.bluetooth")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean i(Context context) {
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null || !packageManager.hasSystemFeature("android.hardware.nfc")) {
                return false;
            }
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean j(Context context) {
        boolean isWiredHeadsetOn;
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            AudioManager audioManager = (AudioManager) context.getSystemService("audio");
            isWiredHeadsetOn = audioManager != null ? audioManager.isWiredHeadsetOn() : false;
        } catch (Throwable th) {
            isWiredHeadsetOn = false;
        }
        return isWiredHeadsetOn;
    }

    public static boolean k(Context context) {
        boolean z;
        if (context == null) {
            if (ab.g == null) {
                return false;
            }
            context = ab.g;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
            z = telephonyManager != null ? telephonyManager.getPhoneType() != 0 : false;
        } catch (Throwable th) {
            z = false;
        }
        return z;
    }

    public static boolean l(Context context) {
        boolean z = false;
        if (context == null) {
            if (ab.g == null) {
                return z;
            }
            context = ab.g;
        }
        try {
            return context.getPackageManager().hasSystemFeature("android.hardware.telephony");
        } catch (Throwable th) {
            return z;
        }
    }

    public static int m(Context context) {
        if (context == null) {
            try {
                if (ab.g == null) {
                    return -1;
                }
                context = ab.g;
            } catch (Throwable th) {
                return -1;
            }
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.widthPixels;
        }
        return -1;
    }

    public static int n(Context context) {
        if (context == null) {
            try {
                if (ab.g == null) {
                    return -1;
                }
                context = ab.g;
            } catch (Throwable th) {
                return -1;
            }
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.heightPixels;
        }
        return -1;
    }

    public static int o(Context context) {
        if (context == null) {
            try {
                if (ab.g == null) {
                    return -1;
                }
                context = ab.g;
            } catch (Throwable th) {
                return -1;
            }
        }
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        if (displayMetrics != null) {
            return displayMetrics.densityDpi;
        }
        return -1;
    }

    public static int p(Context context) {
        int i = -1;
        if (context == null) {
            if (ab.g == null) {
                return i;
            }
            context = ab.g;
        }
        try {
            return System.getInt(context.getContentResolver(), "screen_brightness");
        } catch (Throwable th) {
            return i;
        }
    }
}
