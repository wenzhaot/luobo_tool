package com.umeng.socialize.b.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import com.umeng.socialize.b.b.b;
import com.umeng.socialize.b.b.d;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMImage.CompressStyle;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.IMAGE;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

/* compiled from: ImageImpl */
public class a {
    static {
        com.umeng.socialize.b.b.a.a();
    }

    /* JADX WARNING: Removed duplicated region for block: B:23:0x004c A:{SYNTHETIC, Splitter: B:23:0x004c} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x0061 A:{SYNTHETIC, Splitter: B:31:0x0061} */
    private static byte[] b(android.graphics.Bitmap r6, android.graphics.Bitmap.CompressFormat r7) {
        /*
        r0 = 0;
        if (r6 == 0) goto L_0x0009;
    L_0x0003:
        r1 = r6.isRecycled();
        if (r1 == 0) goto L_0x000a;
    L_0x0009:
        return r0;
    L_0x000a:
        r1 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x0041, all -> 0x005b }
        r1.<init>();	 Catch:{ Exception -> 0x0041, all -> 0x005b }
        r0 = r6.getRowBytes();	 Catch:{ Exception -> 0x006e }
        r2 = r6.getHeight();	 Catch:{ Exception -> 0x006e }
        r0 = r0 * r2;
        r2 = r0 / 1024;
        r0 = 100;
        r3 = (float) r2;	 Catch:{ Exception -> 0x006e }
        r4 = com.umeng.socialize.b.b.c.g;	 Catch:{ Exception -> 0x006e }
        r3 = (r3 > r4 ? 1 : (r3 == r4 ? 0 : -1));
        if (r3 <= 0) goto L_0x002b;
    L_0x0023:
        r3 = com.umeng.socialize.b.b.c.g;	 Catch:{ Exception -> 0x006e }
        r2 = (float) r2;	 Catch:{ Exception -> 0x006e }
        r2 = r3 / r2;
        r0 = (float) r0;	 Catch:{ Exception -> 0x006e }
        r0 = r0 * r2;
        r0 = (int) r0;	 Catch:{ Exception -> 0x006e }
    L_0x002b:
        if (r6 == 0) goto L_0x0030;
    L_0x002d:
        r6.compress(r7, r0, r1);	 Catch:{ Exception -> 0x006e }
    L_0x0030:
        r0 = r1.toByteArray();	 Catch:{ Exception -> 0x006e }
        if (r1 == 0) goto L_0x0009;
    L_0x0036:
        r1.close();	 Catch:{ IOException -> 0x003a }
        goto L_0x0009;
    L_0x003a:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0009;
    L_0x0041:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x0045:
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.BITMAOTOBINARY;	 Catch:{ all -> 0x006c }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x006c }
        if (r1 == 0) goto L_0x004f;
    L_0x004c:
        r1.close();	 Catch:{ IOException -> 0x0054 }
    L_0x004f:
        r0 = com.umeng.socialize.utils.DefaultClass.getBytes();
        goto L_0x0009;
    L_0x0054:
        r0 = move-exception;
        r1 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r1, r0);
        goto L_0x004f;
    L_0x005b:
        r1 = move-exception;
        r5 = r1;
        r1 = r0;
        r0 = r5;
    L_0x005f:
        if (r1 == 0) goto L_0x0064;
    L_0x0061:
        r1.close();	 Catch:{ IOException -> 0x0065 }
    L_0x0064:
        throw r0;
    L_0x0065:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0064;
    L_0x006c:
        r0 = move-exception;
        goto L_0x005f;
    L_0x006e:
        r0 = move-exception;
        goto L_0x0045;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.b.a.a.b(android.graphics.Bitmap, android.graphics.Bitmap$CompressFormat):byte[]");
    }

    private static Options d(byte[] bArr) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
        int ceil = (int) Math.ceil((double) (options.outWidth / UMImage.MAX_WIDTH));
        int ceil2 = (int) Math.ceil((double) (options.outHeight / UMImage.MAX_HEIGHT));
        if (ceil2 <= 1 || ceil <= 1) {
            if (ceil2 > 2) {
                options.inSampleSize = ceil2;
            } else if (ceil > 2) {
                options.inSampleSize = ceil;
            }
        } else if (ceil2 > ceil) {
            options.inSampleSize = ceil2;
        } else {
            options.inSampleSize = ceil;
        }
        options.inJustDecodeBounds = false;
        return options;
    }

    public static byte[] a(UMImage uMImage, int i) {
        if (uMImage == null) {
            return DefaultClass.getBytes();
        }
        if (uMImage.asBinImage() == null || a(uMImage) < i) {
            return uMImage.asBinImage();
        }
        if (uMImage.compressStyle == CompressStyle.QUALITY) {
            return a(uMImage.asBinImage(), i, uMImage.compressFormat);
        }
        try {
            byte[] asBinImage = uMImage.asBinImage();
            if (asBinImage == null) {
                return new byte[1];
            }
            if (asBinImage.length <= 0) {
                return uMImage.asBinImage();
            }
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(asBinImage, 0, asBinImage.length);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(asBinImage, 0, asBinImage.length);
            Bitmap bitmap = decodeByteArray;
            byte[] bArr = asBinImage;
            Bitmap bitmap2 = bitmap;
            while (byteArrayOutputStream.toByteArray().length > i) {
                double sqrt = Math.sqrt((1.0d * ((double) bArr.length)) / ((double) i));
                bitmap2 = Bitmap.createScaledBitmap(bitmap2, (int) (((double) bitmap2.getWidth()) / sqrt), (int) (((double) bitmap2.getHeight()) / sqrt), true);
                byteArrayOutputStream.reset();
                if (bitmap2 != null) {
                    bitmap2.compress(uMImage.compressFormat, 100, byteArrayOutputStream);
                    bArr = byteArrayOutputStream.toByteArray();
                }
            }
            if (byteArrayOutputStream.toByteArray().length > i) {
                return null;
            }
            return bArr;
        } catch (Throwable th) {
            SLog.error(th);
            return DefaultClass.getBytes();
        }
    }

    public static byte[] a(String str) {
        return SocializeNetUtils.getNetData(str);
    }

    public static Bitmap a(byte[] bArr) {
        if (bArr != null) {
            return BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
        }
        return null;
    }

    public static File b(byte[] bArr) {
        try {
            return a(bArr, b.a().b());
        } catch (Throwable e) {
            SLog.error(IMAGE.BINARYTOFILE, e);
            return null;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0024 A:{SYNTHETIC, Splitter: B:16:0x0024} */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x0032 A:{SYNTHETIC, Splitter: B:22:0x0032} */
    private static java.io.File a(byte[] r3, java.io.File r4) {
        /*
        r2 = 0;
        r0 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x001b, all -> 0x002f }
        r0.<init>(r4);	 Catch:{ Exception -> 0x001b, all -> 0x002f }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x001b, all -> 0x002f }
        r1.<init>(r0);	 Catch:{ Exception -> 0x001b, all -> 0x002f }
        r1.write(r3);	 Catch:{ Exception -> 0x0040 }
        if (r1 == 0) goto L_0x0013;
    L_0x0010:
        r1.close();	 Catch:{ IOException -> 0x0014 }
    L_0x0013:
        return r4;
    L_0x0014:
        r0 = move-exception;
        r1 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r1, r0);
        goto L_0x0013;
    L_0x001b:
        r0 = move-exception;
        r1 = r2;
    L_0x001d:
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.GET_FILE_FROM_BINARY;	 Catch:{ all -> 0x003d }
        com.umeng.socialize.utils.SLog.error(r2, r0);	 Catch:{ all -> 0x003d }
        if (r1 == 0) goto L_0x0013;
    L_0x0024:
        r1.close();	 Catch:{ IOException -> 0x0028 }
        goto L_0x0013;
    L_0x0028:
        r0 = move-exception;
        r1 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r1, r0);
        goto L_0x0013;
    L_0x002f:
        r0 = move-exception;
    L_0x0030:
        if (r2 == 0) goto L_0x0035;
    L_0x0032:
        r2.close();	 Catch:{ IOException -> 0x0036 }
    L_0x0035:
        throw r0;
    L_0x0036:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0035;
    L_0x003d:
        r0 = move-exception;
        r2 = r1;
        goto L_0x0030;
    L_0x0040:
        r0 = move-exception;
        goto L_0x001d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.b.a.a.a(byte[], java.io.File):java.io.File");
    }

    public static byte[] a(Bitmap bitmap, CompressFormat compressFormat) {
        return b(bitmap, compressFormat);
    }

    private static Bitmap a(Drawable drawable) {
        int intrinsicWidth = drawable.getIntrinsicWidth();
        int intrinsicHeight = drawable.getIntrinsicHeight();
        Bitmap createBitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, intrinsicWidth, intrinsicHeight);
        drawable.draw(canvas);
        return createBitmap;
    }

    public static byte[] a(Context context, int i, boolean z, CompressFormat compressFormat) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (z) {
            byte[] bArr = new byte[0];
            try {
                Options options = new Options();
                options.inPreferredConfig = Config.RGB_565;
                Bitmap decodeStream = BitmapFactory.decodeStream(context.getResources().openRawResource(i), null, options);
                if (decodeStream != null) {
                    decodeStream.compress(compressFormat, 100, byteArrayOutputStream);
                }
                return byteArrayOutputStream.toByteArray();
            } catch (Throwable e) {
                SLog.error(IMAGE.TOOBIG, e);
                return bArr;
            }
        }
        Drawable drawable;
        Resources resources = context.getResources();
        if (VERSION.SDK_INT >= 21) {
            drawable = resources.getDrawable(i, null);
        } else {
            drawable = resources.getDrawable(i);
        }
        Bitmap a = a(drawable);
        if (a != null) {
            a.compress(compressFormat, 100, byteArrayOutputStream);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static byte[] a(File file, CompressFormat compressFormat) {
        return b(file, compressFormat);
    }

    public static String c(byte[] bArr) {
        return d.a(bArr);
    }

    public static int a(UMImage uMImage) {
        if (uMImage.getImageStyle() == UMImage.FILE_IMAGE) {
            return a(uMImage.asFileImage());
        }
        return e(uMImage.asBinImage());
    }

    private static byte[] b(File file, CompressFormat compressFormat) {
        if (file == null || !file.getAbsoluteFile().exists()) {
            return null;
        }
        byte[] a = b.a().a(file);
        if (!SocializeUtils.assertBinaryInvalid(a)) {
            return null;
        }
        if (d.m[1].equals(d.a(a))) {
            return a;
        }
        return a(a, compressFormat);
    }

    /* JADX WARNING: Removed duplicated region for block: B:26:0x0047 A:{SYNTHETIC, Splitter: B:26:0x0047} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0037 A:{SYNTHETIC, Splitter: B:19:0x0037} */
    private static byte[] a(byte[] r4, android.graphics.Bitmap.CompressFormat r5) {
        /*
        r0 = 0;
        r1 = d(r4);	 Catch:{ Exception -> 0x002e, all -> 0x0042 }
        r2 = 0;
        r3 = r4.length;	 Catch:{ Exception -> 0x002e, all -> 0x0042 }
        r1 = android.graphics.BitmapFactory.decodeByteArray(r4, r2, r3, r1);	 Catch:{ Exception -> 0x002e, all -> 0x0042 }
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x002e, all -> 0x0042 }
        r2.<init>();	 Catch:{ Exception -> 0x002e, all -> 0x0042 }
        if (r1 == 0) goto L_0x001d;
    L_0x0012:
        r3 = 100;
        r1.compress(r5, r3, r2);	 Catch:{ Exception -> 0x0054 }
        r1.recycle();	 Catch:{ Exception -> 0x0054 }
        java.lang.System.gc();	 Catch:{ Exception -> 0x0054 }
    L_0x001d:
        r0 = r2.toByteArray();	 Catch:{ Exception -> 0x0054 }
        if (r2 == 0) goto L_0x0026;
    L_0x0023:
        r2.close();	 Catch:{ IOException -> 0x0027 }
    L_0x0026:
        return r0;
    L_0x0027:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x002e:
        r1 = move-exception;
        r2 = r0;
    L_0x0030:
        r3 = com.umeng.socialize.utils.UmengText.IMAGE.FILE_TO_BINARY_ERROR;	 Catch:{ all -> 0x0052 }
        com.umeng.socialize.utils.SLog.error(r3, r1);	 Catch:{ all -> 0x0052 }
        if (r2 == 0) goto L_0x0026;
    L_0x0037:
        r2.close();	 Catch:{ IOException -> 0x003b }
        goto L_0x0026;
    L_0x003b:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x0026;
    L_0x0042:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0045:
        if (r2 == 0) goto L_0x004a;
    L_0x0047:
        r2.close();	 Catch:{ IOException -> 0x004b }
    L_0x004a:
        throw r0;
    L_0x004b:
        r1 = move-exception;
        r2 = com.umeng.socialize.utils.UmengText.IMAGE.CLOSE;
        com.umeng.socialize.utils.SLog.error(r2, r1);
        goto L_0x004a;
    L_0x0052:
        r0 = move-exception;
        goto L_0x0045;
    L_0x0054:
        r1 = move-exception;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.b.a.a.a(byte[], android.graphics.Bitmap$CompressFormat):byte[]");
    }

    public static byte[] a(byte[] bArr, int i, CompressFormat compressFormat) {
        int i2 = 0;
        if (bArr != null && bArr.length >= i) {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(bArr, 0, bArr.length);
            int i3 = 1;
            while (i2 == 0 && i3 <= 10) {
                int pow = (int) (Math.pow(0.8d, (double) i3) * 100.0d);
                if (decodeByteArray != null) {
                    decodeByteArray.compress(compressFormat, pow, byteArrayOutputStream);
                }
                if (byteArrayOutputStream == null || byteArrayOutputStream.size() >= i) {
                    byteArrayOutputStream.reset();
                    i3++;
                } else {
                    i2 = 1;
                }
            }
            if (byteArrayOutputStream != null) {
                bArr = byteArrayOutputStream.toByteArray();
                if (!decodeByteArray.isRecycled()) {
                    decodeByteArray.recycle();
                }
                if (bArr != null && bArr.length <= 0) {
                    SLog.E(IMAGE.THUMB_ERROR);
                }
            }
        }
        return bArr;
    }

    private static int e(byte[] bArr) {
        if (bArr != null) {
            return bArr.length;
        }
        return 0;
    }

    private static int a(File file) {
        if (file != null) {
            try {
                return new FileInputStream(file).available();
            } catch (Throwable th) {
                SLog.error(IMAGE.GET_IMAGE_SCALE_ERROR, th);
            }
        }
        return 0;
    }
}
