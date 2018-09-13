package com.baidu.location.h;

import android.content.Context;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.text.TextUtils;
import com.baidu.location.BDLocation;
import com.baidu.location.Jni;
import com.baidu.location.f.a;
import com.baidu.location.f.b;
import com.baidu.location.f.d;
import com.baidu.location.f.f;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.baidu.platform.comapi.location.CoordinateType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.stub.StubApp;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Locale;

public class k {
    public static float A = 2.3f;
    public static float B = 3.8f;
    public static int C = 3;
    public static int D = 10;
    public static int E = 2;
    public static int F = 7;
    public static int G = 20;
    public static int H = 70;
    public static int I = 120;
    public static float J = 2.0f;
    public static float K = 10.0f;
    public static float L = 50.0f;
    public static float M = 200.0f;
    public static int N = 16;
    public static float O = 0.9f;
    public static int P = 10000;
    public static float Q = 0.5f;
    public static float R = 0.0f;
    public static float S = 0.1f;
    public static int T = 30;
    public static int U = 100;
    public static int V = 0;
    public static int W = 0;
    public static int X = 0;
    public static int Y = 420000;
    public static boolean Z = true;
    public static boolean a = false;
    private static String aA = "http://loc.map.baidu.com/iofd.php";
    private static String aB = "http://loc.map.baidu.com/wloc";
    public static boolean aa = true;
    public static int ab = 20;
    public static int ac = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
    public static int ad = 1000;
    public static int ae = Integer.MAX_VALUE;
    public static long af = 900000;
    public static long ag = 420000;
    public static long ah = 180000;
    public static long ai = 0;
    public static long aj = 15;
    public static long ak = 300000;
    public static int al = 1000;
    public static int am = 0;
    public static int an = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static int ao = m_AppUI.MSG_RADAR_SEARCH_RETURN_RESULT;
    public static float ap = 10.0f;
    public static float aq = 6.0f;
    public static float ar = 10.0f;
    public static int as = 60;
    public static int at = 70;
    public static int au = 6;
    private static String av = "http://loc.map.baidu.com/sdk.php";
    private static String aw = "http://loc.map.baidu.com/user_err.php";
    private static String ax = "http://loc.map.baidu.com/oqur.php";
    private static String ay = "http://loc.map.baidu.com/tcu.php";
    private static String az = "http://loc.map.baidu.com/rtbu.php";
    public static boolean b = false;
    public static boolean c = false;
    public static int d = 0;
    public static String e = "http://loc.map.baidu.com/sdk_ep.php";
    public static String f = "https://loc.map.baidu.com/sdk.php";
    public static String g = "no";
    public static boolean h = false;
    public static boolean i = false;
    public static boolean j = false;
    public static boolean k = false;
    public static boolean l = false;
    public static String m = CoordinateType.GCJ02;
    public static String n = "";
    public static boolean o = true;
    public static int p = 3;
    public static double q = 0.0d;
    public static double r = 0.0d;
    public static double s = 0.0d;
    public static double t = 0.0d;
    public static int u = 0;
    public static byte[] v = null;
    public static boolean w = false;
    public static int x = 0;
    public static float y = 1.1f;
    public static float z = 2.2f;

    public static int a(String str, String str2, String str3) {
        int i = Integer.MIN_VALUE;
        if (str == null || str.equals("")) {
            return i;
        }
        int indexOf = str.indexOf(str2);
        if (indexOf == -1) {
            return i;
        }
        indexOf += str2.length();
        int indexOf2 = str.indexOf(str3, indexOf);
        if (indexOf2 == -1) {
            return i;
        }
        String substring = str.substring(indexOf, indexOf2);
        if (substring == null || substring.equals("")) {
            return i;
        }
        try {
            return Integer.parseInt(substring);
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static Object a(Context context, String str) {
        Object obj = null;
        if (context == null) {
            return obj;
        }
        try {
            return StubApp.getOrigApplicationContext(context.getApplicationContext()).getSystemService(str);
        } catch (Throwable th) {
            return obj;
        }
    }

    public static Object a(Object obj, String str, Object... objArr) throws Exception {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return declaredMethod.invoke(obj, objArr);
    }

    public static String a() {
        Calendar instance = Calendar.getInstance();
        int i = instance.get(5);
        int i2 = instance.get(1);
        int i3 = instance.get(2) + 1;
        int i4 = instance.get(11);
        int i5 = instance.get(12);
        int i6 = instance.get(13);
        return String.format(Locale.CHINA, "%d-%02d-%02d %02d:%02d:%02d", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i), Integer.valueOf(i4), Integer.valueOf(i5), Integer.valueOf(i6)});
    }

    public static String a(a aVar, f fVar, Location location, String str, int i) {
        return a(aVar, fVar, location, str, i, false);
    }

    public static String a(a aVar, f fVar, Location location, String str, int i, boolean z) {
        String b;
        StringBuffer stringBuffer = new StringBuffer(1024);
        if (aVar != null) {
            b = b.a().b(aVar);
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        if (fVar != null) {
            b = i == 0 ? z ? fVar.b() : fVar.c() : fVar.d();
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        if (location != null) {
            b = (d == 0 || i == 0) ? d.b(location) : d.c(location);
            if (b != null) {
                stringBuffer.append(b);
            }
        }
        boolean z2 = false;
        if (i == 0) {
            z2 = true;
        }
        b = b.a().a(z2);
        if (b != null) {
            stringBuffer.append(b);
        }
        if (str != null) {
            stringBuffer.append(str);
        }
        Object d = com.baidu.location.b.d.a().d();
        if (!TextUtils.isEmpty(d)) {
            stringBuffer.append("&bc=").append(d);
        }
        if (i == 0) {
        }
        if (aVar != null) {
            b = b.a().a(aVar);
            if (b != null && b.length() + stringBuffer.length() < 750) {
                stringBuffer.append(b);
            }
        }
        b = stringBuffer.toString();
        if (location == null || fVar == null) {
            p = 3;
        } else {
            try {
                float speed = location.getSpeed();
                int i2 = d;
                int g = fVar.g();
                int a = fVar.a();
                boolean h = fVar.h();
                if (speed < aq && ((i2 == 1 || i2 == 0) && (g < as || h))) {
                    p = 1;
                } else if (speed >= ar || (!(i2 == 1 || i2 == 0 || i2 == 3) || (g >= at && a <= au))) {
                    p = 3;
                } else {
                    p = 2;
                }
            } catch (Exception e) {
                p = 3;
            }
        }
        return b;
    }

    public static String a(File file, String str) {
        if (!file.isFile()) {
            return null;
        }
        byte[] bArr = new byte[1024];
        try {
            MessageDigest instance = MessageDigest.getInstance(str);
            FileInputStream fileInputStream = new FileInputStream(file);
            while (true) {
                int read = fileInputStream.read(bArr, 0, 1024);
                if (read != -1) {
                    instance.update(bArr, 0, read);
                } else {
                    fileInputStream.close();
                    return new BigInteger(1, instance.digest()).toString(16);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String a(String str) {
        return Jni.en1(n + ";" + str);
    }

    public static boolean a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return false;
        }
        for (NetworkInfo state : allNetworkInfo) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean a(BDLocation bDLocation) {
        int locType = bDLocation.getLocType();
        return (locType > 100 && locType < 200) || locType == 62;
    }

    public static int b(Context context) {
        try {
            return System.getInt(context.getContentResolver(), "airplane_mode_on", 0);
        } catch (Exception e) {
            return 2;
        }
    }

    private static int b(Context context, String str) {
        int i;
        try {
            i = context.checkPermission(str, Process.myPid(), Process.myUid()) == 0 ? 1 : 0;
        } catch (Exception e) {
            i = 1;
        }
        return i == 0 ? 0 : 1;
    }

    public static int b(Object obj, String str, Object... objArr) throws Exception {
        Class cls = obj.getClass();
        Class[] clsArr = new Class[objArr.length];
        int length = objArr.length;
        for (int i = 0; i < length; i++) {
            clsArr[i] = objArr[i].getClass();
            if (clsArr[i] == Integer.class) {
                clsArr[i] = Integer.TYPE;
            }
        }
        Method declaredMethod = cls.getDeclaredMethod(str, clsArr);
        if (!declaredMethod.isAccessible()) {
            declaredMethod.setAccessible(true);
        }
        return ((Integer) declaredMethod.invoke(obj, objArr)).intValue();
    }

    public static String b() {
        try {
            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration inetAddresses = ((NetworkInterface) networkInterfaces.nextElement()).getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && (inetAddress instanceof Inet4Address)) {
                        byte[] address = inetAddress.getAddress();
                        String str = "";
                        int i = 0;
                        while (true) {
                            int i2 = i;
                            String str2 = str;
                            if (i2 >= address.length) {
                                return str2;
                            }
                            str = Integer.toHexString(address[i2] & 255);
                            if (str.length() == 1) {
                                str = '0' + str;
                            }
                            str = str2 + str;
                            i = i2 + 1;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean b(String str, String str2, String str3) {
        try {
            PublicKey generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.baidu.android.bbalbs.common.a.b.a(str3.getBytes())));
            Signature instance = Signature.getInstance("SHA1WithRSA");
            instance.initVerify(generatePublic);
            instance.update(str.getBytes());
            return instance.verify(com.baidu.android.bbalbs.common.a.b.a(str2.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int c(Context context) {
        int i = -1;
        if (VERSION.SDK_INT < 19) {
            return -2;
        }
        try {
            return Secure.getInt(context.getContentResolver(), "location_mode", -1);
        } catch (Exception e) {
            return i;
        }
    }

    public static String c() {
        return av;
    }

    public static String d() {
        return ay;
    }

    public static String d(Context context) {
        int b = b(context, "android.permission.ACCESS_COARSE_LOCATION");
        int b2 = b(context, "android.permission.ACCESS_FINE_LOCATION");
        return "&per=" + b + "|" + b2 + "|" + b(context, "android.permission.READ_PHONE_STATE");
    }

    public static String e() {
        return "https://daup.map.baidu.com/cltr/rcvr";
    }

    public static String e(Context context) {
        int type;
        int i = -1;
        if (context != null) {
            try {
                NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
                    type = activeNetworkInfo.getType();
                    i = type;
                    return "&netc=" + i;
                }
            } catch (Exception e) {
            }
        }
        type = -1;
        i = type;
        return "&netc=" + i;
    }

    public static String f() {
        try {
            if (!Environment.getExternalStorageState().equals("mounted")) {
                return null;
            }
            String path = Environment.getExternalStorageDirectory().getPath();
            File file = new File(path + "/baidu/tempdata");
            if (file.exists()) {
                return path;
            }
            file.mkdirs();
            return path;
        } catch (Exception e) {
            return null;
        }
    }

    public static String g() {
        String f = f();
        return f == null ? null : f + "/baidu/tempdata";
    }

    public static String h() {
        try {
            File file = new File(com.baidu.location.f.getServiceContext().getFilesDir() + File.separator + "lldt");
            if (!file.exists()) {
                file.mkdirs();
            }
            return file.getAbsolutePath();
        } catch (Exception e) {
            return null;
        }
    }
}
