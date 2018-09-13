package com.facebook.webpsupport;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.v4.internal.view.SupportMenu;
import android.util.TypedValue;
import com.facebook.common.internal.DoNotStrip;
import com.facebook.common.soloader.SoLoaderShim;
import com.facebook.common.webp.WebpBitmapFactory;
import com.facebook.common.webp.WebpSupportStatus;
import java.io.BufferedInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.Nullable;

@DoNotStrip
public class WebpBitmapFactoryImpl implements WebpBitmapFactory {
    private static final int HEADER_SIZE = 20;
    public static final boolean IN_BITMAP_SUPPORTED = (VERSION.SDK_INT >= 11);
    private static final int IN_TEMP_BUFFER_SIZE = 8192;

    @DoNotStrip
    private static native Bitmap nativeDecodeByteArray(byte[] bArr, int i, int i2, Options options, float f, Bitmap bitmap, byte[] bArr2);

    @DoNotStrip
    private static native Bitmap nativeDecodeStream(InputStream inputStream, Options options, float f, Bitmap bitmap, byte[] bArr);

    @DoNotStrip
    private static native long nativeSeek(FileDescriptor fileDescriptor, long j, boolean z);

    static {
        SoLoaderShim.loadLibrary("static-webp");
    }

    private static InputStream wrapToMarkSupportedStream(InputStream inputStream) {
        if (inputStream.markSupported()) {
            return inputStream;
        }
        return new BufferedInputStream(inputStream, 20);
    }

    private static byte[] getWebpHeader(InputStream inputStream, Options opts) {
        byte[] header;
        inputStream.mark(20);
        if (opts == null || opts.inTempStorage == null || opts.inTempStorage.length < 20) {
            header = new byte[20];
        } else {
            header = opts.inTempStorage;
        }
        try {
            inputStream.read(header, 0, 20);
            inputStream.reset();
            return header;
        } catch (IOException e) {
            return null;
        }
    }

    private static void setDensityFromOptions(Bitmap outputBitmap, Options opts) {
        if (outputBitmap != null && opts != null) {
            int density = opts.inDensity;
            if (density != 0) {
                outputBitmap.setDensity(density);
                int targetDensity = opts.inTargetDensity;
                if (targetDensity != 0 && density != targetDensity && density != opts.inScreenDensity && opts.inScaled) {
                    outputBitmap.setDensity(targetDensity);
                }
            } else if (IN_BITMAP_SUPPORTED && opts.inBitmap != null) {
                outputBitmap.setDensity(160);
            }
        }
    }

    public Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts) {
        return hookDecodeFileDescriptor(fd, outPadding, opts);
    }

    public Bitmap decodeStream(InputStream inputStream, Rect outPadding, Options opts) {
        return hookDecodeStream(inputStream, outPadding, opts);
    }

    public Bitmap decodeFile(String pathName, Options opts) {
        return hookDecodeFile(pathName, opts);
    }

    public Bitmap decodeByteArray(byte[] array, int offset, int length, Options opts) {
        return hookDecodeByteArray(array, offset, length, opts);
    }

    @DoNotStrip
    public static Bitmap hookDecodeByteArray(byte[] array, int offset, int length, Options opts) {
        if (!WebpSupportStatus.isWebpHeader(array, offset, length) || WebpSupportStatus.isWebpPlatformSupported(array, offset, length)) {
            return originalDecodeByteArray(array, offset, length, opts);
        }
        Bitmap bitmap = nativeDecodeByteArray(array, offset, length, opts, getScaleFromOptions(opts), getInBitmapFromOptions(opts), getInTempStorageFromOptions(opts));
        setWebpBitmapOptions(bitmap, opts);
        return bitmap;
    }

    @DoNotStrip
    private static Bitmap originalDecodeByteArray(byte[] array, int offset, int length, Options opts) {
        return BitmapFactory.decodeByteArray(array, offset, length, opts);
    }

    @DoNotStrip
    public static Bitmap hookDecodeByteArray(byte[] array, int offset, int length) {
        return hookDecodeByteArray(array, offset, length, null);
    }

    @DoNotStrip
    private static Bitmap originalDecodeByteArray(byte[] array, int offset, int length) {
        return BitmapFactory.decodeByteArray(array, offset, length);
    }

    @DoNotStrip
    public static Bitmap hookDecodeStream(InputStream inputStream, Rect outPadding, Options opts) {
        inputStream = wrapToMarkSupportedStream(inputStream);
        byte[] header = getWebpHeader(inputStream, opts);
        if (!WebpSupportStatus.isWebpHeader(header, 0, 20) || WebpSupportStatus.isWebpPlatformSupported(header, 0, 20)) {
            return originalDecodeStream(inputStream, outPadding, opts);
        }
        Bitmap bitmap = nativeDecodeStream(inputStream, opts, getScaleFromOptions(opts), getInBitmapFromOptions(opts), getInTempStorageFromOptions(opts));
        setWebpBitmapOptions(bitmap, opts);
        setPaddingDefaultValues(outPadding);
        return bitmap;
    }

    @DoNotStrip
    private static Bitmap originalDecodeStream(InputStream inputStream, Rect outPadding, Options opts) {
        return BitmapFactory.decodeStream(inputStream, outPadding, opts);
    }

    @DoNotStrip
    public static Bitmap hookDecodeStream(InputStream inputStream) {
        return hookDecodeStream(inputStream, null, null);
    }

    @DoNotStrip
    private static Bitmap originalDecodeStream(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    /* JADX WARNING: Missing block: B:20:0x0023, code:
            if (r1 != null) goto L_0x0025;
     */
    /* JADX WARNING: Missing block: B:21:0x0025, code:
            if (r3 != null) goto L_0x0027;
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:28:0x0030, code:
            r1.close();
     */
    @com.facebook.common.internal.DoNotStrip
    public static android.graphics.Bitmap hookDecodeFile(java.lang.String r5, android.graphics.BitmapFactory.Options r6) {
        /*
        r3 = 0;
        r0 = 0;
        r1 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x001a }
        r1.<init>(r5);	 Catch:{ Exception -> 0x001a }
        r2 = 0;
        r4 = 0;
        r0 = hookDecodeStream(r1, r4, r6);	 Catch:{ Throwable -> 0x0020 }
        if (r1 == 0) goto L_0x0014;
    L_0x000f:
        if (r3 == 0) goto L_0x001c;
    L_0x0011:
        r1.close();	 Catch:{ Throwable -> 0x0015 }
    L_0x0014:
        return r0;
    L_0x0015:
        r3 = move-exception;
        r2.addSuppressed(r3);	 Catch:{ Exception -> 0x001a }
        goto L_0x0014;
    L_0x001a:
        r2 = move-exception;
        goto L_0x0014;
    L_0x001c:
        r1.close();	 Catch:{ Exception -> 0x001a }
        goto L_0x0014;
    L_0x0020:
        r3 = move-exception;
        throw r3;	 Catch:{ all -> 0x0022 }
    L_0x0022:
        r2 = move-exception;
        if (r1 == 0) goto L_0x002a;
    L_0x0025:
        if (r3 == 0) goto L_0x0030;
    L_0x0027:
        r1.close();	 Catch:{ Throwable -> 0x002b }
    L_0x002a:
        throw r2;	 Catch:{ Exception -> 0x001a }
    L_0x002b:
        r4 = move-exception;
        r3.addSuppressed(r4);	 Catch:{ Exception -> 0x001a }
        goto L_0x002a;
    L_0x0030:
        r1.close();	 Catch:{ Exception -> 0x001a }
        goto L_0x002a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.webpsupport.WebpBitmapFactoryImpl.hookDecodeFile(java.lang.String, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    @DoNotStrip
    public static Bitmap hookDecodeFile(String pathName) {
        return hookDecodeFile(pathName, null);
    }

    @DoNotStrip
    public static Bitmap hookDecodeResourceStream(Resources res, TypedValue value, InputStream is, Rect pad, Options opts) {
        if (opts == null) {
            opts = new Options();
        }
        if (opts.inDensity == 0 && value != null) {
            int density = value.density;
            if (density == 0) {
                opts.inDensity = 160;
            } else if (density != SupportMenu.USER_MASK) {
                opts.inDensity = density;
            }
        }
        if (opts.inTargetDensity == 0 && res != null) {
            opts.inTargetDensity = res.getDisplayMetrics().densityDpi;
        }
        return hookDecodeStream(is, pad, opts);
    }

    @DoNotStrip
    private static Bitmap originalDecodeResourceStream(Resources res, TypedValue value, InputStream is, Rect pad, Options opts) {
        return BitmapFactory.decodeResourceStream(res, value, is, pad, opts);
    }

    /* JADX WARNING: Missing block: B:27:0x003b, code:
            if (r1 != null) goto L_0x003d;
     */
    /* JADX WARNING: Missing block: B:28:0x003d, code:
            if (r4 != null) goto L_0x003f;
     */
    /* JADX WARNING: Missing block: B:30:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:35:0x0048, code:
            r1.close();
     */
    @com.facebook.common.internal.DoNotStrip
    public static android.graphics.Bitmap hookDecodeResource(android.content.res.Resources r6, int r7, android.graphics.BitmapFactory.Options r8) {
        /*
        r4 = 0;
        r0 = 0;
        r2 = new android.util.TypedValue;
        r2.<init>();
        r1 = r6.openRawResource(r7, r2);	 Catch:{ Exception -> 0x0032 }
        r3 = 0;
        r5 = 0;
        r0 = hookDecodeResourceStream(r6, r2, r1, r5, r8);	 Catch:{ Throwable -> 0x0038 }
        if (r1 == 0) goto L_0x0018;
    L_0x0013:
        if (r4 == 0) goto L_0x0034;
    L_0x0015:
        r1.close();	 Catch:{ Throwable -> 0x002d }
    L_0x0018:
        r3 = IN_BITMAP_SUPPORTED;
        if (r3 == 0) goto L_0x004c;
    L_0x001c:
        if (r0 != 0) goto L_0x004c;
    L_0x001e:
        if (r8 == 0) goto L_0x004c;
    L_0x0020:
        r3 = r8.inBitmap;
        if (r3 == 0) goto L_0x004c;
    L_0x0024:
        r3 = new java.lang.IllegalArgumentException;
        r4 = "Problem decoding into existing bitmap";
        r3.<init>(r4);
        throw r3;
    L_0x002d:
        r4 = move-exception;
        r3.addSuppressed(r4);	 Catch:{ Exception -> 0x0032 }
        goto L_0x0018;
    L_0x0032:
        r3 = move-exception;
        goto L_0x0018;
    L_0x0034:
        r1.close();	 Catch:{ Exception -> 0x0032 }
        goto L_0x0018;
    L_0x0038:
        r4 = move-exception;
        throw r4;	 Catch:{ all -> 0x003a }
    L_0x003a:
        r3 = move-exception;
        if (r1 == 0) goto L_0x0042;
    L_0x003d:
        if (r4 == 0) goto L_0x0048;
    L_0x003f:
        r1.close();	 Catch:{ Throwable -> 0x0043 }
    L_0x0042:
        throw r3;	 Catch:{ Exception -> 0x0032 }
    L_0x0043:
        r5 = move-exception;
        r4.addSuppressed(r5);	 Catch:{ Exception -> 0x0032 }
        goto L_0x0042;
    L_0x0048:
        r1.close();	 Catch:{ Exception -> 0x0032 }
        goto L_0x0042;
    L_0x004c:
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.webpsupport.WebpBitmapFactoryImpl.hookDecodeResource(android.content.res.Resources, int, android.graphics.BitmapFactory$Options):android.graphics.Bitmap");
    }

    @DoNotStrip
    private static Bitmap originalDecodeResource(Resources res, int id, Options opts) {
        return BitmapFactory.decodeResource(res, id, opts);
    }

    @DoNotStrip
    public static Bitmap hookDecodeResource(Resources res, int id) {
        return hookDecodeResource(res, id, null);
    }

    @DoNotStrip
    private static Bitmap originalDecodeResource(Resources res, int id) {
        return BitmapFactory.decodeResource(res, id);
    }

    @DoNotStrip
    private static boolean setOutDimensions(Options options, int imageWidth, int imageHeight) {
        if (options == null || !options.inJustDecodeBounds) {
            return false;
        }
        options.outWidth = imageWidth;
        options.outHeight = imageHeight;
        return true;
    }

    @DoNotStrip
    private static void setPaddingDefaultValues(@Nullable Rect padding) {
        if (padding != null) {
            padding.top = -1;
            padding.left = -1;
            padding.bottom = -1;
            padding.right = -1;
        }
    }

    @DoNotStrip
    private static void setBitmapSize(@Nullable Options options, int width, int height) {
        if (options != null) {
            options.outWidth = width;
            options.outHeight = height;
        }
    }

    @DoNotStrip
    private static Bitmap originalDecodeFile(String pathName, Options opts) {
        return BitmapFactory.decodeFile(pathName, opts);
    }

    @DoNotStrip
    private static Bitmap originalDecodeFile(String pathName) {
        return BitmapFactory.decodeFile(pathName);
    }

    @DoNotStrip
    public static Bitmap hookDecodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts) {
        long originalSeekPosition = nativeSeek(fd, 0, false);
        Bitmap bitmap;
        if (originalSeekPosition != -1) {
            InputStream inputStream = wrapToMarkSupportedStream(new FileInputStream(fd));
            try {
                byte[] header = getWebpHeader(inputStream, opts);
                if (!WebpSupportStatus.isWebpHeader(header, 0, 20) || WebpSupportStatus.isWebpPlatformSupported(header, 0, 20)) {
                    nativeSeek(fd, originalSeekPosition, true);
                    bitmap = originalDecodeFileDescriptor(fd, outPadding, opts);
                } else {
                    bitmap = nativeDecodeStream(inputStream, opts, getScaleFromOptions(opts), getInBitmapFromOptions(opts), getInTempStorageFromOptions(opts));
                    setPaddingDefaultValues(outPadding);
                    setWebpBitmapOptions(bitmap, opts);
                }
                try {
                    inputStream.close();
                    return bitmap;
                } catch (Throwable th) {
                    return bitmap;
                }
            } catch (Throwable th2) {
            }
        } else {
            bitmap = hookDecodeStream(new FileInputStream(fd), outPadding, opts);
            setPaddingDefaultValues(outPadding);
            return bitmap;
        }
    }

    @DoNotStrip
    private static Bitmap originalDecodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options opts) {
        return BitmapFactory.decodeFileDescriptor(fd, outPadding, opts);
    }

    @DoNotStrip
    public static Bitmap hookDecodeFileDescriptor(FileDescriptor fd) {
        return hookDecodeFileDescriptor(fd, null, null);
    }

    @DoNotStrip
    private static Bitmap originalDecodeFileDescriptor(FileDescriptor fd) {
        return BitmapFactory.decodeFileDescriptor(fd);
    }

    private static void setWebpBitmapOptions(Bitmap bitmap, Options opts) {
        setDensityFromOptions(bitmap, opts);
        if (opts != null) {
            opts.outMimeType = "image/webp";
        }
    }

    @SuppressLint({"NewApi"})
    @DoNotStrip
    private static boolean shouldPremultiply(Options options) {
        if (VERSION.SDK_INT < 19 || options == null) {
            return true;
        }
        return options.inPremultiplied;
    }

    @DoNotStrip
    private static Bitmap createBitmap(int width, int height, Options options) {
        return Bitmap.createBitmap(width, height, Config.ARGB_8888);
    }

    @DoNotStrip
    private static Bitmap getInBitmapFromOptions(Options options) {
        if (!IN_BITMAP_SUPPORTED || options == null) {
            return null;
        }
        return options.inBitmap;
    }

    @DoNotStrip
    private static byte[] getInTempStorageFromOptions(@Nullable Options options) {
        if (options == null || options.inTempStorage == null) {
            return new byte[8192];
        }
        return options.inTempStorage;
    }

    @DoNotStrip
    private static float getScaleFromOptions(Options options) {
        float scale = 1.0f;
        if (options == null) {
            return 1.0f;
        }
        int sampleSize = options.inSampleSize;
        if (sampleSize > 1) {
            scale = 1.0f / ((float) sampleSize);
        }
        if (!options.inScaled) {
            return scale;
        }
        int density = options.inDensity;
        int targetDensity = options.inTargetDensity;
        int screenDensity = options.inScreenDensity;
        if (density == 0 || targetDensity == 0 || density == screenDensity) {
            return scale;
        }
        return ((float) targetDensity) / ((float) density);
    }
}
