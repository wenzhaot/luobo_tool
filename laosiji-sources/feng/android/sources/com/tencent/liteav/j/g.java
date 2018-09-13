package com.tencent.liteav.j;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Environment;
import android.text.TextUtils;
import com.feng.car.video.shortvideo.TCConstants;
import com.qiniu.android.common.Constants;
import com.tencent.liteav.basic.log.TXCLog;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: Util */
public class g {
    public static String a(int i) {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + TCConstants.DEFAULT_MEDIA_PACK_FOLDER);
        if (!file.exists()) {
            file.mkdirs();
        }
        String format = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date(Long.valueOf(String.valueOf(System.currentTimeMillis() / 1000) + "000").longValue()));
        String str = null;
        if (i == 0) {
            str = String.format("TXVideo_%s_reverse.mp4", new Object[]{format});
        } else if (i == 1) {
            str = String.format("TXVideo_%s_process.mp4", new Object[]{format});
        }
        return file + "/" + str;
    }

    public static Bitmap a(float f, Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.postRotate(f);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (!(createBitmap == bitmap || bitmap == null || bitmap.isRecycled())) {
            bitmap.recycle();
        }
        return createBitmap;
    }

    public static Bitmap a(Bitmap bitmap, int i) {
        Bitmap createBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint();
        paint.setAlpha(i);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        canvas.drawRect(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return createBitmap;
    }

    public static boolean a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(str);
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    /* JADX WARNING: Removed duplicated region for block: B:18:0x003d A:{SYNTHETIC, Splitter: B:18:0x003d} */
    public static java.lang.String b(java.lang.String r6) {
        /*
        r0 = 0;
        r1 = new java.io.File;
        r1.<init>(r6);
        r2 = new java.lang.StringBuilder;
        r3 = "";
        r2.<init>(r3);
        if (r1 == 0) goto L_0x0016;
    L_0x0010:
        r3 = r1.isFile();
        if (r3 != 0) goto L_0x0017;
    L_0x0016:
        return r0;
    L_0x0017:
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
        r4 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
        r4.<init>(r1);	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
        r3.<init>(r4);	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
        r1 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
        r1.<init>(r3);	 Catch:{ IOException -> 0x0067, all -> 0x0062 }
    L_0x0026:
        r0 = r1.readLine();	 Catch:{ IOException -> 0x0030 }
        if (r0 == 0) goto L_0x0041;
    L_0x002c:
        r2.append(r0);	 Catch:{ IOException -> 0x0030 }
        goto L_0x0026;
    L_0x0030:
        r0 = move-exception;
    L_0x0031:
        r2 = new java.lang.RuntimeException;	 Catch:{ all -> 0x003a }
        r3 = "IOException occurred. ";
        r2.<init>(r3, r0);	 Catch:{ all -> 0x003a }
        throw r2;	 Catch:{ all -> 0x003a }
    L_0x003a:
        r0 = move-exception;
    L_0x003b:
        if (r1 == 0) goto L_0x0040;
    L_0x003d:
        r1.close();	 Catch:{ IOException -> 0x0058 }
    L_0x0040:
        throw r0;
    L_0x0041:
        r1.close();	 Catch:{ IOException -> 0x0030 }
        r0 = r2.toString();	 Catch:{ IOException -> 0x0030 }
        if (r1 == 0) goto L_0x0016;
    L_0x004a:
        r1.close();	 Catch:{ IOException -> 0x004e }
        goto L_0x0016;
    L_0x004e:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "IOException occurred. ";
        r1.<init>(r2, r0);
        throw r1;
    L_0x0058:
        r0 = move-exception;
        r1 = new java.lang.RuntimeException;
        r2 = "IOException occurred. ";
        r1.<init>(r2, r0);
        throw r1;
    L_0x0062:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x003b;
    L_0x0067:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
        goto L_0x0031;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.j.g.b(java.lang.String):java.lang.String");
    }

    public static float a(int i, long j) {
        long b = b(i);
        long c = c(i);
        long j2 = b + c;
        j2 = j - (j2 * (j / j2));
        if (j2 < 0 || j2 > b) {
            float f = ((float) (j2 - b)) / ((float) c);
            TXCLog.i("Util", "setBitmapsAndDisplayRatio, in motion status, cropOffsetRatio = " + f + ", remainTimeMs = " + j2);
            return f;
        }
        TXCLog.i("Util", "setBitmapsAndDisplayRatio, in stay status, cropOffsetRatio = 0, remainTimeMs = " + j2);
        return 0.0f;
    }

    public static float b(int i, long j) {
        long b = b(i);
        long c = c(i);
        long j2 = b + c;
        long j3 = j - ((j / j2) * j2);
        switch (i) {
            case 3:
                if ((j3 < 0 || j3 > b) && j3 > b && j3 <= j2) {
                    return 1.0f - (((float) (j3 - b)) / ((float) c));
                }
                return 1.0f;
            case 4:
                if ((j3 < 0 || j3 > b) && j3 > b && j3 < j2) {
                    return 1.0f + ((((float) (j3 - b)) * 0.1f) / ((float) c));
                }
                return 1.0f;
            case 5:
                if (j3 >= 0 && j3 <= b) {
                    return 1.1f;
                }
                if (j3 <= b || j3 > j2) {
                    return 1.0f;
                }
                return 1.1f - ((((float) (j3 - b)) * 0.1f) / ((float) c));
            default:
                return 1.0f;
        }
    }

    public static float c(int i, long j) {
        long b = b(i);
        long c = c(i);
        long j2 = b + c;
        long j3 = j - ((j / j2) * j2);
        switch (i) {
            case 4:
            case 5:
                if (j3 >= 0 && ((double) j3) <= ((double) b) + (((double) c) * 0.8d)) {
                    return 1.0f;
                }
                if (((double) j3) <= ((double) b) + (((double) c) * 0.8d) || j3 > j2) {
                    return 1.0f;
                }
                return 1.0f - ((((float) (j3 - b)) - (((float) c) * 0.8f)) / (((float) c) * 0.2f));
            case 6:
                if (j3 >= 0 && j3 <= b) {
                    return 1.0f;
                }
                if (j3 <= b || j3 > j2) {
                    return 1.0f;
                }
                return 1.0f - (((float) (j3 - b)) / ((float) c));
            default:
                return 1.0f;
        }
    }

    public static int d(int i, long j) {
        long b = b(i);
        long c = c(i);
        long j2 = b + c;
        long j3 = j - ((j / j2) * j2);
        switch (i) {
            case 3:
                if ((j3 <= 0 || j3 > b) && j3 > b && j3 <= j2) {
                    return (int) ((((float) (j3 - b)) / ((float) c)) * 360.0f);
                }
                return 0;
            default:
                return 0;
        }
    }

    public static long b(int i) {
        switch (i) {
            case 3:
            case 6:
                return 1500;
            case 4:
            case 5:
                return 100;
            default:
                return 1000;
        }
    }

    public static long c(int i) {
        switch (i) {
            case 3:
                return 1000;
            case 4:
            case 5:
                return 2500;
            case 6:
                return 1500;
            default:
                return 500;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x002a A:{SYNTHETIC, Splitter: B:19:0x002a} */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x002f A:{Catch:{ Exception -> 0x0033 }} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x002a A:{SYNTHETIC, Splitter: B:19:0x002a} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x002f A:{Catch:{ Exception -> 0x0033 }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003c A:{SYNTHETIC, Splitter: B:28:0x003c} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0041 A:{Catch:{ Exception -> 0x0045 }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x003c A:{SYNTHETIC, Splitter: B:28:0x003c} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0041 A:{Catch:{ Exception -> 0x0045 }} */
    public static void a(java.lang.String r4, byte[] r5) {
        /*
        r2 = 0;
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0023, all -> 0x0038 }
        r0.<init>(r4);	 Catch:{ Exception -> 0x0023, all -> 0x0038 }
        r1 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x0023, all -> 0x0038 }
        r1.<init>(r0);	 Catch:{ Exception -> 0x0023, all -> 0x0038 }
        r3 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x004f }
        r3.<init>(r1);	 Catch:{ Exception -> 0x004f }
        r3.write(r5);	 Catch:{ Exception -> 0x0051, all -> 0x004c }
        if (r3 == 0) goto L_0x0018;
    L_0x0015:
        r3.close();	 Catch:{ Exception -> 0x001e }
    L_0x0018:
        if (r1 == 0) goto L_0x001d;
    L_0x001a:
        r1.close();	 Catch:{ Exception -> 0x001e }
    L_0x001d:
        return;
    L_0x001e:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001d;
    L_0x0023:
        r0 = move-exception;
        r1 = r2;
    L_0x0025:
        r0.printStackTrace();	 Catch:{ all -> 0x004a }
        if (r2 == 0) goto L_0x002d;
    L_0x002a:
        r2.close();	 Catch:{ Exception -> 0x0033 }
    L_0x002d:
        if (r1 == 0) goto L_0x001d;
    L_0x002f:
        r1.close();	 Catch:{ Exception -> 0x0033 }
        goto L_0x001d;
    L_0x0033:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001d;
    L_0x0038:
        r0 = move-exception;
        r1 = r2;
    L_0x003a:
        if (r2 == 0) goto L_0x003f;
    L_0x003c:
        r2.close();	 Catch:{ Exception -> 0x0045 }
    L_0x003f:
        if (r1 == 0) goto L_0x0044;
    L_0x0041:
        r1.close();	 Catch:{ Exception -> 0x0045 }
    L_0x0044:
        throw r0;
    L_0x0045:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0044;
    L_0x004a:
        r0 = move-exception;
        goto L_0x003a;
    L_0x004c:
        r0 = move-exception;
        r2 = r3;
        goto L_0x003a;
    L_0x004f:
        r0 = move-exception;
        goto L_0x0025;
    L_0x0051:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.j.g.a(java.lang.String, byte[]):void");
    }

    public static boolean a(Context context, String str) {
        try {
            String[] list = context.getAssets().list("");
            for (String equals : list) {
                if (equals.equals(str.trim())) {
                    TXCLog.i("isAssetFileExists", str + " exist");
                    return true;
                }
            }
            TXCLog.i("isAssetFileExists", str + " not exist");
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            TXCLog.i("isAssetFileExists", str + " not exist");
            return false;
        }
    }

    public static String b(Context context, String str) {
        try {
            InputStream open = context.getAssets().open(str);
            byte[] bArr = new byte[open.available()];
            open.read(bArr);
            open.close();
            return new String(bArr, Constants.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
