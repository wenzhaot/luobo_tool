package com.talkingdata.sdk;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.nio.channels.FileChannel;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.Inflater;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONObject;

/* compiled from: td */
public class bo {
    static final boolean a = false;
    public static boolean b = true;
    public static String c = ab.s;
    public static boolean d = false;
    public static boolean e = false;
    static final /* synthetic */ boolean f = (!bo.class.desiredAssertionStatus());
    private static final String g = "UTF-8";
    private static String h = "ge";
    private static String i = SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE;
    private static String j = "rop";
    private static final ExecutorService k = Executors.newSingleThreadExecutor();
    private static final byte l = (byte) 61;
    private static final String m = "US-ASCII";
    private static final byte[] n = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    private static byte[] o = new byte[]{(byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5, (byte) 6, (byte) 7, (byte) 8};
    private static final SecureRandom p = new SecureRandom();

    public static void execute(Runnable runnable) {
        if (k != null) {
            k.execute(runnable);
        }
    }

    public static String a(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new JSONObject(new String(bArr)).getString("td_channel_id");
        } catch (Throwable th) {
            return null;
        }
    }

    public static void a(Object obj, bk bkVar, String str, String str2) {
        try {
            Field declaredField = obj.getClass().getDeclaredField(str);
            declaredField.setAccessible(true);
            Object obj2 = declaredField.get(obj);
            Class cls = Class.forName(str2);
            InvocationHandler bpVar = new bp(bkVar, obj2);
            declaredField.set(obj, Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{cls}, bpVar));
        } catch (Throwable th) {
        }
    }

    public static void a(Class cls, bk bkVar, String str, String str2) {
        Field declaredField = cls.getDeclaredField(str);
        declaredField.setAccessible(true);
        Object obj = declaredField.get(null);
        Class cls2 = Class.forName(str2);
        InvocationHandler bqVar = new bq(bkVar, obj);
        declaredField.set(null, Proxy.newProxyInstance(cls.getClass().getClassLoader(), new Class[]{cls2}, bqVar));
    }

    public static final String a(String str) {
        if (str == null) {
            return null;
        }
        if (str.length() > AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS) {
            return str.substring(0, AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        }
        return str;
    }

    public static final boolean b(String str) {
        return str == null || "".equals(str.trim());
    }

    public static String c(String str) {
        try {
            return a(MessageDigest.getInstance("MD5").digest(str.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean b(Context context, String str) {
        try {
            if (a(23)) {
                return context.checkSelfPermission(str) == 0;
            } else {
                if (context.checkCallingOrSelfPermission(str) != 0) {
                    return false;
                }
                return true;
            }
        } catch (Throwable th) {
            return false;
        }
    }

    public static boolean a(int i) {
        return VERSION.SDK_INT >= i;
    }

    public static boolean b(int i) {
        return VERSION.SDK_INT < i;
    }

    public static String a(byte[] bArr) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : bArr) {
                int i = b & 255;
                if (i < 16) {
                    stringBuilder.append('0');
                }
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (Throwable th) {
            return null;
        }
    }

    public static byte[] a(int[] iArr, int[] iArr2) {
        try {
            String str = "kiG9w0BAQUFADCBqjELMAkGA0JFSUpJTkcxEDAOBgNVBAcMB0JFSUpJTkcxFjAUBgNVB";
            for (int i = 0; i < iArr.length; i++) {
                iArr[i] = ((iArr[i] * iArr2[(iArr2.length - 1) - i]) - (iArr[(iArr.length - 1) - i] * iArr2[i])) + str.charAt(i);
                iArr2[i] = ((iArr2[i] * iArr[(iArr.length - 1) - i]) + (iArr2[(iArr2.length - 1) - i] * iArr[i])) - str.charAt((str.length() - 1) - i);
            }
            return (Arrays.toString(iArr) + Arrays.hashCode(iArr2)).getBytes();
        } catch (Throwable th) {
            return null;
        }
    }

    private static byte[] a(byte[] bArr, int i, int i2, byte[] bArr2, int i3) {
        int i4 = 0;
        try {
            byte[] bArr3 = n;
            int i5 = (i2 > 1 ? (bArr[i + 1] << 24) >>> 16 : 0) | (i2 > 0 ? (bArr[i] << 24) >>> 8 : 0);
            if (i2 > 2) {
                i4 = (bArr[i + 2] << 24) >>> 24;
            }
            i4 |= i5;
            switch (i2) {
                case 1:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = l;
                    bArr2[i3 + 3] = l;
                    return bArr2;
                case 2:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                    bArr2[i3 + 3] = l;
                    return bArr2;
                case 3:
                    bArr2[i3] = bArr3[i4 >>> 18];
                    bArr2[i3 + 1] = bArr3[(i4 >>> 12) & 63];
                    bArr2[i3 + 2] = bArr3[(i4 >>> 6) & 63];
                    bArr2[i3 + 3] = bArr3[i4 & 63];
                    return bArr2;
                default:
                    return bArr2;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
        cs.postSDKError(th);
        return null;
    }

    public static String b(byte[] bArr) {
        String str = null;
        try {
            str = a(bArr, 0, bArr.length);
        } catch (Throwable th) {
            if (!f) {
                AssertionError assertionError = new AssertionError(th.getMessage());
            }
        }
        if (f || str != null) {
            return str;
        }
        throw new AssertionError();
    }

    public static String a(byte[] bArr, int i, int i2) {
        byte[] b = b(bArr, i, i2);
        try {
            return new String(b, "US-ASCII");
        } catch (Throwable th) {
            return new String(b);
        }
    }

    public static byte[] b(byte[] bArr, int i, int i2) {
        if (bArr == null) {
            throw new NullPointerException("Cannot serialize a null array.");
        } else if (i < 0) {
            throw new IllegalArgumentException("Cannot have negative offset: " + i);
        } else if (i2 < 0) {
            throw new IllegalArgumentException("Cannot have length offset: " + i2);
        } else if (i + i2 > bArr.length) {
            throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(bArr.length)}));
        } else {
            Object obj = new byte[((i2 % 3 > 0 ? 4 : 0) + ((i2 / 3) * 4))];
            int i3 = i2 - 2;
            int i4 = 0;
            int i5 = 0;
            while (i5 < i3) {
                a(bArr, i5 + i, 3, obj, i4);
                i5 += 3;
                i4 += 4;
            }
            if (i5 < i2) {
                a(bArr, i5 + i, i2 - i5, obj, i4);
                i4 += 4;
            }
            if (i4 > obj.length - 1) {
                return obj;
            }
            Object obj2 = new byte[i4];
            System.arraycopy(obj, 0, obj2, 0, i4);
            return obj2;
        }
    }

    public static boolean a(Context context) {
        return false;
    }

    public static String c(Context context, String str) {
        try {
            return a(context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData, str);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static String a(Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        try {
            for (String equalsIgnoreCase : bundle.keySet()) {
                if (equalsIgnoreCase.equalsIgnoreCase(str)) {
                    return String.valueOf(bundle.get(str));
                }
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static byte[] a(byte[] bArr, byte[] bArr2) {
        try {
            Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(1, generateSecret, new IvParameterSpec(o));
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static byte[] b(byte[] bArr, byte[] bArr2) {
        try {
            Key generateSecret = SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(bArr2));
            Cipher instance = Cipher.getInstance("DES/CBC/PKCS5Padding");
            instance.init(2, generateSecret, new IvParameterSpec(o));
            return instance.doFinal(bArr);
        } catch (Throwable th) {
            return null;
        }
    }

    public static String a() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(h + i + j).getInputStream(), "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    stringBuilder.append(readLine).append("\n");
                } else {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
            }
        } catch (Throwable th) {
            return null;
        }
    }

    public static String d(String str) {
        String str2 = null;
        if (str == null) {
            return str2;
        }
        try {
            return a(MessageDigest.getInstance("SHA-256").digest(str.getBytes("UTF-8")));
        } catch (Throwable th) {
            return str2;
        }
    }

    public static FileChannel d(Context context, String str) {
        FileChannel fileChannel = null;
        RandomAccessFile randomAccessFile;
        try {
            File file = new File(context.getFilesDir(), str + "td.lock");
            if (!file.exists()) {
                file.createNewFile();
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            try {
                return randomAccessFile.getChannel();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
            randomAccessFile = fileChannel;
            if (randomAccessFile == null) {
                return fileChannel;
            }
            try {
                randomAccessFile.close();
                return fileChannel;
            } catch (Throwable th3) {
                return fileChannel;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:13:0x002e A:{SYNTHETIC, Splitter: B:13:0x002e} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0037 A:{SYNTHETIC, Splitter: B:18:0x0037} */
    public static byte[] e(java.lang.String r6) {
        /*
        r2 = new java.io.ByteArrayOutputStream;
        r2.<init>();
        r1 = 0;
        r3 = new java.util.zip.Deflater;
        r0 = 9;
        r4 = 1;
        r3.<init>(r0, r4);
        r0 = new java.util.zip.DeflaterOutputStream;	 Catch:{ Throwable -> 0x002a, all -> 0x0034 }
        r0.<init>(r2, r3);	 Catch:{ Throwable -> 0x002a, all -> 0x0034 }
        r1 = "UTF-8";
        r1 = r6.getBytes(r1);	 Catch:{ Throwable -> 0x0044, all -> 0x003f }
        r0.write(r1);	 Catch:{ Throwable -> 0x0044, all -> 0x003f }
        if (r0 == 0) goto L_0x0022;
    L_0x001f:
        r0.close();	 Catch:{ Throwable -> 0x003b }
    L_0x0022:
        r3.end();
        r0 = r2.toByteArray();
        return r0;
    L_0x002a:
        r0 = move-exception;
        r0 = r1;
    L_0x002c:
        if (r0 == 0) goto L_0x0022;
    L_0x002e:
        r0.close();	 Catch:{ Throwable -> 0x0032 }
        goto L_0x0022;
    L_0x0032:
        r0 = move-exception;
        goto L_0x0022;
    L_0x0034:
        r0 = move-exception;
    L_0x0035:
        if (r1 == 0) goto L_0x003a;
    L_0x0037:
        r1.close();	 Catch:{ Throwable -> 0x003d }
    L_0x003a:
        throw r0;
    L_0x003b:
        r0 = move-exception;
        goto L_0x0022;
    L_0x003d:
        r1 = move-exception;
        goto L_0x003a;
    L_0x003f:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0035;
    L_0x0044:
        r1 = move-exception;
        goto L_0x002c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.bo.e(java.lang.String):byte[]");
    }

    public static byte[] c(byte[] bArr) {
        byte[] bArr2 = new byte[0];
        Inflater inflater = new Inflater();
        inflater.reset();
        inflater.setInput(bArr);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bArr.length);
        try {
            byte[] bArr3 = new byte[1024];
            while (!inflater.finished()) {
                byteArrayOutputStream.write(bArr3, 0, inflater.inflate(bArr3));
            }
            bArr = byteArrayOutputStream.toByteArray();
            try {
                byteArrayOutputStream.close();
            } catch (Throwable th) {
            }
        } catch (Throwable th2) {
        }
        inflater.end();
        return bArr;
    }

    public static SecureRandom b() {
        return p;
    }

    public static Long f(String str) {
        Long valueOf = Long.valueOf(-1);
        try {
            return Long.valueOf(Long.parseLong(str));
        } catch (Throwable th) {
            cs.postSDKError(th);
            return valueOf;
        }
    }

    public static Integer g(String str) {
        Integer valueOf = Integer.valueOf(-1);
        try {
            return Integer.valueOf(Integer.parseInt(str));
        } catch (Throwable th) {
            cs.postSDKError(th);
            return valueOf;
        }
    }

    public static Map a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            Iterator keys = jSONObject.keys();
            Map hashMap = new HashMap();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                hashMap.put(str, jSONObject.get(str));
            }
            return hashMap;
        } catch (Exception e) {
            return null;
        }
    }
}
