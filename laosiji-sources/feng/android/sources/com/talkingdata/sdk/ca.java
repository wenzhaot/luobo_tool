package com.talkingdata.sdk;

import com.talkingdata.sdk.bb.b;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Stack;

/* compiled from: td */
public final class ca {
    static final String a = "Archimedes_p";
    static final String b = "Pythagoras_phase";
    static final String c = "_Ladder_Project";
    private static int d = 0;
    private static final int e = 1000000000;

    static int a() {
        if (d != 0) {
            return d;
        }
        int i;
        try {
            bb.getFileLock(b.AES_DATA_LOCK.toString());
            d = by.a(by.a());
            if (d == 0) {
                d = bo.b().nextInt(e);
                a(d);
            }
            i = d;
        } catch (Throwable th) {
            return 0;
        } finally {
            bb.releaseFileLock(b.AES_DATA_LOCK.toString());
            return 0;
        }
        return i;
    }

    static void a(int i) {
        int i2;
        int i3;
        Stack stack = new Stack();
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{7, 2});
        int[] iArr2 = new int[3];
        int[] iArr3 = new int[7];
        int[] iArr4 = new int[7];
        SecureRandom secureRandom = new SecureRandom();
        for (i2 = 0; i2 < iArr2.length; i2++) {
            iArr2[i2] = secureRandom.nextInt(1000);
        }
        for (i2 = 0; i2 < iArr3.length; i2++) {
            iArr3[i2] = secureRandom.nextInt(100);
        }
        for (i2 = 0; i2 < iArr4.length; i2++) {
            iArr4[i2] = (((((iArr2[0] * iArr3[i2]) * iArr3[i2]) * iArr3[i2]) + ((iArr2[1] * iArr3[i2]) * iArr3[i2])) + (iArr2[2] * iArr3[i2])) + i;
            stack.push(iArr3[i2] + MiPushClient.ACCEPT_TIME_SEPARATOR + iArr4[i2] + MiPushClient.ACCEPT_TIME_SEPARATOR);
        }
        for (i3 = 1; i3 < 4; i3++) {
            a(a + i3, (String) stack.pop());
        }
        for (i3 = 4; i3 < 6; i3++) {
            b(a + i3, (String) stack.pop());
        }
        i2 = 6;
        while (true) {
            i3 = i2;
            if (i3 < 8) {
                c(ab.g.getPackageName() + i3, (String) stack.pop());
                i2 = i3 + 1;
            } else {
                return;
            }
        }
    }

    private static void b(String str, String str2) {
        d(str, str2);
    }

    static void a(String str, String str2) {
        File file = new File(ab.g.getFilesDir() + File.separator + c);
        if (!(file.exists() || file.isDirectory())) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        if (!file2.exists()) {
            file2.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file2);
        try {
            fileOutputStream.write(str2.getBytes());
        } catch (Throwable th) {
        } finally {
            fileOutputStream.close();
        }
    }

    private static void c(String str, String str2) {
        System.getProperties().setProperty(str, str2);
    }

    private static void d(String str, String str2) {
        try {
            FileOutputStream openFileOutput = ab.g.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes("UTF-8"));
            openFileOutput.close();
        } catch (Throwable e) {
            cs.postSDKError(e);
        }
    }
}
