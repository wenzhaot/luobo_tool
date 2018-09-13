package com.huawei.android.pushagent.a.a;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class a {
    private static final char[] a = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public static int a() {
        int intValue;
        Class[] clsArr = new Class[]{String.class, Integer.TYPE};
        Object[] objArr = new Object[]{"ro.build.hw_emui_api_level", Integer.valueOf(0)};
        try {
            Class cls = Class.forName("android.os.SystemProperties");
            intValue = ((Integer) cls.getDeclaredMethod("getInt", clsArr).invoke(cls, objArr)).intValue();
            try {
                c.a("PushLogSC2816", "getEmuiLevel:" + intValue);
            } catch (ClassNotFoundException e) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, ClassNotFoundException");
                return intValue;
            } catch (ExceptionInInitializerError e2) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, ExceptionInInitializerError");
                return intValue;
            } catch (LinkageError e3) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, LinkageError");
                return intValue;
            } catch (NoSuchMethodException e4) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, NoSuchMethodException");
                return intValue;
            } catch (NullPointerException e5) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, NullPointerException");
                return intValue;
            } catch (IllegalAccessException e6) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, IllegalAccessException");
                return intValue;
            } catch (IllegalArgumentException e7) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, IllegalArgumentException");
                return intValue;
            } catch (InvocationTargetException e8) {
                c.d("PushLogSC2816", " getEmuiLevel wrong, InvocationTargetException");
                return intValue;
            }
        } catch (ClassNotFoundException e9) {
            intValue = 0;
        } catch (ExceptionInInitializerError e10) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, ExceptionInInitializerError");
            return intValue;
        } catch (LinkageError e11) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, LinkageError");
            return intValue;
        } catch (NoSuchMethodException e12) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, NoSuchMethodException");
            return intValue;
        } catch (NullPointerException e13) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, NullPointerException");
            return intValue;
        } catch (IllegalAccessException e14) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, IllegalAccessException");
            return intValue;
        } catch (IllegalArgumentException e15) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, IllegalArgumentException");
            return intValue;
        } catch (InvocationTargetException e16) {
            intValue = 0;
            c.d("PushLogSC2816", " getEmuiLevel wrong, InvocationTargetException");
            return intValue;
        }
        return intValue;
    }

    public static int a(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivityManager == null) {
            return -1;
        }
        NetworkInfo[] allNetworkInfo = connectivityManager.getAllNetworkInfo();
        if (allNetworkInfo == null) {
            return -1;
        }
        for (int i = 0; i < allNetworkInfo.length; i++) {
            if (allNetworkInfo[i].getState() == State.CONNECTED) {
                return allNetworkInfo[i].getType();
            }
        }
        return -1;
    }

    public static String a(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[(bArr.length * 2)];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            cArr[i * 2] = a[(b & 240) >> 4];
            cArr[(i * 2) + 1] = a[b & 15];
        }
        return new String(cArr);
    }

    public static byte[] a(String str) {
        byte[] bArr = new byte[(str.length() / 2)];
        try {
            byte[] bytes = str.getBytes("UTF-8");
            for (int i = 0; i < bArr.length; i++) {
                bArr[i] = (byte) (((byte) (Byte.decode("0x" + new String(new byte[]{bytes[i * 2]}, "UTF-8")).byteValue() << 4)) ^ Byte.decode("0x" + new String(new byte[]{bytes[(i * 2) + 1]}, "UTF-8")).byteValue());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return bArr;
    }
}
