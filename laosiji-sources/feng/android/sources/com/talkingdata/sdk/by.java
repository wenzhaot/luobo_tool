package com.talkingdata.sdk;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* compiled from: td */
final class by {
    by() {
    }

    static int[][] a() {
        int i = 6;
        int i2 = 0;
        try {
            int i3;
            CharSequence c;
            String str = "Archimedes_p";
            List arrayList = new ArrayList();
            StringBuilder stringBuilder = new StringBuilder();
            int i4 = 0;
            for (i3 = 1; i3 < 4; i3++) {
                c = c("Archimedes_p" + i3);
                if (!TextUtils.isEmpty(c)) {
                    arrayList.add(c);
                    i4++;
                }
            }
            for (i3 = 4; i3 < 6; i3++) {
                c = a("Archimedes_p" + i3);
                if (!TextUtils.isEmpty(c)) {
                    arrayList.add(c);
                    i4++;
                }
            }
            if (i4 < 4) {
                while (i < 8) {
                    CharSequence b = b(ab.g.getPackageName() + i);
                    if (!TextUtils.isEmpty(b)) {
                        arrayList.add(b);
                        i4++;
                    }
                    i++;
                }
            }
            if (arrayList.size() < 4) {
                return (int[][]) null;
            }
            while (i2 < 4) {
                stringBuilder.append((String) arrayList.get(i2));
                i2++;
            }
            return a(stringBuilder.toString(), 4, 2);
        } catch (Throwable th) {
            return (int[][]) null;
        }
    }

    static int a(int[][] iArr) {
        if (iArr == null) {
            return 0;
        }
        try {
            if (iArr.length < 4) {
                return 0;
            }
            r1 = new double[4][];
            r1[0] = new double[]{(double) ((iArr[0][0] * iArr[0][0]) * iArr[0][0]), (double) (iArr[0][0] * iArr[0][0]), (double) iArr[0][0], 1.0d, (double) iArr[0][1]};
            r1[1] = new double[]{(double) ((iArr[1][0] * iArr[1][0]) * iArr[1][0]), (double) (iArr[1][0] * iArr[1][0]), (double) iArr[1][0], 1.0d, (double) iArr[1][1]};
            r1[2] = new double[]{(double) ((iArr[2][0] * iArr[2][0]) * iArr[2][0]), (double) (iArr[2][0] * iArr[2][0]), (double) iArr[2][0], 1.0d, (double) iArr[2][1]};
            r1[3] = new double[]{(double) ((iArr[3][0] * iArr[3][0]) * iArr[3][0]), (double) (iArr[3][0] * iArr[3][0]), (double) iArr[3][0], 1.0d, (double) iArr[3][1]};
            return a(r1);
        } catch (Throwable th) {
            return 0;
        }
    }

    private static int a(double[][] dArr) {
        r1 = new double[4][];
        r1[0] = new double[]{dArr[0][0], dArr[0][1], dArr[0][2], dArr[0][3]};
        r1[1] = new double[]{dArr[1][0], dArr[1][1], dArr[1][2], dArr[1][3]};
        r1[2] = new double[]{dArr[2][0], dArr[2][1], dArr[2][2], dArr[2][3]};
        r1[3] = new double[]{dArr[3][0], dArr[3][1], dArr[3][2], dArr[3][3]};
        r2 = new double[4][];
        r2[0] = new double[]{dArr[0][4], dArr[0][1], dArr[0][2], dArr[0][3]};
        r2[1] = new double[]{dArr[1][4], dArr[1][1], dArr[1][2], dArr[1][3]};
        r2[2] = new double[]{dArr[2][4], dArr[2][1], dArr[2][2], dArr[2][3]};
        r2[3] = new double[]{dArr[3][4], dArr[3][1], dArr[3][2], dArr[3][3]};
        r3 = new double[4][];
        r3[0] = new double[]{dArr[0][0], dArr[0][4], dArr[0][2], dArr[0][3]};
        r3[1] = new double[]{dArr[1][0], dArr[1][4], dArr[1][2], dArr[1][3]};
        r3[2] = new double[]{dArr[2][0], dArr[2][4], dArr[2][2], dArr[2][3]};
        r3[3] = new double[]{dArr[3][0], dArr[3][4], dArr[3][2], dArr[3][3]};
        r4 = new double[4][];
        r4[0] = new double[]{dArr[0][0], dArr[0][1], dArr[0][4], dArr[0][3]};
        r4[1] = new double[]{dArr[1][0], dArr[1][1], dArr[1][4], dArr[1][3]};
        r4[2] = new double[]{dArr[2][0], dArr[2][1], dArr[2][4], dArr[2][3]};
        r4[3] = new double[]{dArr[3][0], dArr[3][1], dArr[3][4], dArr[3][3]};
        r5 = new double[4][];
        r5[0] = new double[]{dArr[0][0], dArr[0][1], dArr[0][2], dArr[0][4]};
        r5[1] = new double[]{dArr[1][0], dArr[1][1], dArr[1][2], dArr[1][4]};
        r5[2] = new double[]{dArr[2][0], dArr[2][1], dArr[2][2], dArr[2][4]};
        r5[3] = new double[]{dArr[3][0], dArr[3][1], dArr[3][2], dArr[3][4]};
        double b = b(r1);
        if (b == 0.0d) {
            return 0;
        }
        double b2 = b(r2);
        double b3 = b(r3);
        b2 /= b;
        b2 = b3 / b;
        b2 = b(r4) / b;
        return a(b(r5) / b);
    }

    private static double b(double[][] dArr) {
        r0 = new double[3][];
        r0[0] = new double[]{dArr[1][1], dArr[1][2], dArr[1][3]};
        r0[1] = new double[]{dArr[2][1], dArr[2][2], dArr[2][3]};
        r0[2] = new double[]{dArr[3][1], dArr[3][2], dArr[3][3]};
        r1 = new double[3][];
        r1[0] = new double[]{dArr[1][0], dArr[1][2], dArr[1][3]};
        r1[1] = new double[]{dArr[2][0], dArr[2][2], dArr[2][3]};
        r1[2] = new double[]{dArr[3][0], dArr[3][2], dArr[3][3]};
        r2 = new double[3][];
        r2[0] = new double[]{dArr[1][0], dArr[1][1], dArr[1][3]};
        r2[1] = new double[]{dArr[2][0], dArr[2][1], dArr[2][3]};
        r2[2] = new double[]{dArr[3][0], dArr[3][1], dArr[3][3]};
        r3 = new double[3][];
        r3[0] = new double[]{dArr[1][0], dArr[1][1], dArr[1][2]};
        r3[1] = new double[]{dArr[2][0], dArr[2][1], dArr[2][2]};
        r3[2] = new double[]{dArr[3][0], dArr[3][1], dArr[3][2]};
        return (((c(r1) * (Math.pow(-1.0d, 1.0d) * dArr[0][1])) + ((Math.pow(-1.0d, 0.0d) * dArr[0][0]) * c(r0))) + ((Math.pow(-1.0d, 2.0d) * dArr[0][2]) * c(r2))) + (c(r3) * (Math.pow(-1.0d, 3.0d) * dArr[0][3]));
    }

    private static double c(double[][] dArr) {
        return ((((((dArr[0][0] * dArr[1][1]) * dArr[2][2]) + ((dArr[0][1] * dArr[1][2]) * dArr[2][0])) + ((dArr[0][2] * dArr[1][0]) * dArr[2][1])) + (((-dArr[0][2]) * dArr[1][1]) * dArr[2][0])) - ((dArr[0][1] * dArr[1][0]) * dArr[2][2])) - ((dArr[0][0] * dArr[2][1]) * dArr[1][2]);
    }

    private static int a(double d) {
        try {
            return Integer.parseInt(new DecimalFormat("######0").format(d));
        } catch (Throwable th) {
            cs.postSDKError(th);
            return 0;
        }
    }

    private static String a(String str) {
        String str2;
        Throwable th;
        byte[] bArr = new byte[1024];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            FileInputStream openFileInput = ab.g.openFileInput(str);
            while (true) {
                int read = openFileInput.read(bArr);
                if (read == -1) {
                    break;
                }
                byteArrayOutputStream.write(bArr, 0, read);
            }
            str2 = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
            try {
                openFileInput.close();
                byteArrayOutputStream.close();
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Throwable th3) {
            Throwable th4 = th3;
            str2 = null;
            th = th4;
            cs.postSDKError(th);
            return str2;
        }
        return str2;
    }

    private static String b(String str) {
        return System.getProperties().getProperty(str);
    }

    private static String c(String str) {
        SharedPreferences sharedPreferences = ab.g.getSharedPreferences("Pythagoras_phase", 0);
        String string = sharedPreferences.getString(str, "");
        File file = new File(ab.g.getFilesDir() + File.separator + "_Ladder_Project");
        if (!(file.exists() || file.isDirectory())) {
            file.mkdirs();
        }
        File file2 = new File(file, str);
        if (file2.exists()) {
            FileInputStream fileInputStream = new FileInputStream(file2);
            try {
                byte[] bArr = new byte[((int) file2.length())];
                fileInputStream.read(bArr);
                string = new String(bArr);
                return string;
            } catch (Throwable th) {
                string = th;
                return "";
            } finally {
                fileInputStream.close();
            }
        } else {
            file2.createNewFile();
            if (TextUtils.isEmpty(string)) {
                return string;
            }
            ca.a(str, string);
            sharedPreferences.edit().putString(str, "").apply();
            return string;
        }
    }

    private static int[][] a(String str, int i, int i2) {
        int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i, i2});
        String[] split = str.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
        int i3 = 0;
        int i4 = 0;
        while (i3 < i) {
            int i5 = i4;
            for (i4 = 0; i4 < i2; i4++) {
                iArr[i3][i4] = Integer.parseInt(split[i5]);
                i5++;
            }
            i3++;
            i4 = i5;
        }
        return iArr;
    }
}
