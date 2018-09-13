package com.feng.car.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ImageView;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.listener.OnDownloadImageListener;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.feng.library.utils.StringUtil;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request.Builder;
import okhttp3.Response;

public class ImageUtil {
    public static final int CUTIMG = 0;
    public static final int MAX_HEIGHT = 2048;
    public static final int MAX_WIDTH = 2048;
    public static final int ORIGINALIMG = 2;
    public static final int SCALEIMG = 1;

    public static Bitmap getBitmap(String url, int type, int desiredWidth, int desiredHeight) {
        Bitmap bm = null;
        InputStream is = null;
        try {
            URLConnection con = new URL(url).openConnection();
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            Bitmap wholeBm = BitmapFactory.decodeStream(is, null, null);
            if (type == 0) {
                bm = getCutBitmap(wholeBm, desiredWidth, desiredHeight);
            } else if (type == 1) {
                bm = getScaleBitmap(wholeBm, desiredWidth, desiredHeight);
            } else {
                bm = wholeBm;
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            Log.d(String.valueOf(ImageUtil.class), "" + e2.getMessage());
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e22) {
                    e22.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e222) {
                    e222.printStackTrace();
                }
            }
        }
        return bm;
    }

    public static Bitmap getBitmapFromUrl(String url) {
        Bitmap bm = null;
        InputStream is = null;
        try {
            URLConnection con = new URL(url).openConnection();
            con.setDoInput(true);
            con.connect();
            is = con.getInputStream();
            bm = BitmapFactory.decodeStream(is, null, null);
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e32) {
                    e32.printStackTrace();
                }
            }
        }
        return bm;
    }

    public static void downLoadImage(String url, final OnDownloadImageListener listener) {
        try {
            OkHttpUtils.getInstance().getOkHttpClient().newCall(new Builder().url(url).build()).enqueue(new Callback() {
                public void onFailure(Call call, IOException e) {
                    listener.onDownloadFailed();
                }

                public void onResponse(Call call, Response response) throws IOException {
                    InputStream is = null;
                    try {
                        is = response.body().byteStream();
                        Bitmap bm = BitmapFactory.decodeStream(is, null, null);
                        if (bm != null) {
                            listener.onDownloadSuccess(bm);
                        } else {
                            listener.onDownloadFailed();
                        }
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e) {
                            }
                        }
                    } catch (Exception e2) {
                        listener.onDownloadFailed();
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e3) {
                            }
                        }
                    } catch (Throwable th) {
                        if (is != null) {
                            try {
                                is.close();
                            } catch (IOException e4) {
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            listener.onDownloadFailed();
        }
    }

    public static Bitmap getBitmap(File file) {
        Bitmap resizeBmp = null;
        try {
            return BitmapFactory.decodeFile(file.getPath());
        } catch (Exception e) {
            e.printStackTrace();
            return resizeBmp;
        }
    }

    public static Bitmap readBitMap(Context context, int resId) {
        Options opt = new Options();
        opt.inPreferredConfig = Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        return BitmapFactory.decodeStream(context.getResources().openRawResource(resId), null, opt);
    }

    public static Bitmap getScaleBitmap(File file, int desiredWidth, int desiredHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        float scale = getMinScale(srcWidth, srcHeight, size[0], size[1]);
        int destWidth = srcWidth;
        int destHeight = srcHeight;
        if (scale != 0.0f) {
            destWidth = (int) (((float) srcWidth) * scale);
            destHeight = (int) (((float) srcHeight) * scale);
        }
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        if (((double) scale) < 0.25d) {
            opts.inSampleSize = 2;
        } else if (((double) scale) < 0.125d) {
            opts.inSampleSize = 4;
        } else {
            opts.inSampleSize = 1;
        }
        opts.outWidth = destWidth;
        opts.outHeight = destHeight;
        opts.inJustDecodeBounds = false;
        opts.inDither = false;
        return getScaleBitmap(BitmapFactory.decodeFile(file.getPath(), opts), scale);
    }

    public static Bitmap getScaleBitmap(String path, int desiredWidth, int desiredHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, opts);
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        float scale = getMinScale(srcWidth, srcHeight, size[0], size[1]);
        int destWidth = srcWidth;
        int destHeight = srcHeight;
        if (scale != 0.0f) {
            destWidth = (int) (((float) srcWidth) * scale);
            destHeight = (int) (((float) srcHeight) * scale);
        }
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        if (((double) scale) < 0.25d) {
            opts.inSampleSize = 2;
        } else if (((double) scale) < 0.125d) {
            opts.inSampleSize = 4;
        } else {
            opts.inSampleSize = 1;
        }
        opts.outWidth = destWidth;
        opts.outHeight = destHeight;
        opts.inJustDecodeBounds = false;
        opts.inDither = false;
        return getScaleBitmap(BitmapFactory.decodeFile(path, opts), scale);
    }

    public static int calculateInSampleSize(int srcWidth, int srcHight, int reqWidth, int reqHeight) {
        if (srcHight <= reqHeight && srcWidth <= reqWidth) {
            return 1;
        }
        int heightRatio = Math.round(((float) srcHight) / ((float) reqHeight));
        int widthRatio = Math.round(((float) srcWidth) / ((float) reqWidth));
        if (heightRatio < widthRatio) {
            return heightRatio;
        }
        return widthRatio;
    }

    public static Bitmap getScaleBitmap(String path, int desiredWidth) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(new File(path)));
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(in, null, options);
            in.close();
            int i = 0;
            int srcWidth = options.outWidth;
            int srcHeight = options.outHeight;
            int desiredHeight = ((desiredWidth * srcHeight) / srcWidth) - 100;
            while (true) {
                if ((options.outWidth >> i) <= 1000 && (options.outHeight >> i) <= 1000) {
                    break;
                }
                i++;
            }
            in = new BufferedInputStream(new FileInputStream(new File(path)));
            options.inSampleSize = (int) Math.pow(2.0d, (double) i);
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Config.ARGB_8888;
            options.inPurgeable = true;
            options.inInputShareable = true;
            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options);
            if (srcWidth > desiredWidth) {
                return getScaleBitmap(bitmap, desiredWidth, desiredHeight);
            }
            if (i > 0) {
                return getScaleBitmap(bitmap, srcWidth, srcHeight);
            }
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getScaleBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
        if (!checkBitmap(bitmap)) {
            return null;
        }
        int srcWidth = bitmap.getWidth();
        int srcHeight = bitmap.getHeight();
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];
        Bitmap resizeBmp = getScaleBitmap(bitmap, getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight));
        if (resizeBmp.getWidth() > desiredWidth || resizeBmp.getHeight() > desiredHeight) {
            return getCutBitmap(resizeBmp, desiredWidth, desiredHeight);
        }
        return resizeBmp;
    }

    public static Bitmap getScaleBitmap(Bitmap bitmap, float scale) {
        if (!checkBitmap(bitmap)) {
            return null;
        }
        if (scale == 1.0f) {
            return bitmap;
        }
        Bitmap resizeBmp = null;
        try {
            int bmpW = bitmap.getWidth();
            int bmpH = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);
            resizeBmp = Bitmap.createBitmap(bitmap, 0, 0, bmpW, bmpH, matrix, true);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resizeBmp != bitmap) {
            }
        }
        return resizeBmp;
    }

    public static Bitmap getCutBitmap(File file, int desiredWidth, int desiredHeight) {
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        int srcWidth = opts.outWidth;
        int srcHeight = opts.outHeight;
        int[] size = resizeToMaxSize(srcWidth, srcHeight, desiredWidth, desiredHeight);
        desiredWidth = size[0];
        desiredHeight = size[1];
        float scale = getMinScale(srcWidth, srcHeight, desiredWidth, desiredHeight);
        int destWidth = srcWidth;
        int destHeight = srcHeight;
        if (scale != 1.0f) {
            destWidth = (int) (((float) srcWidth) * scale);
            destHeight = (int) (((float) srcHeight) * scale);
        }
        opts.inPreferredConfig = Config.RGB_565;
        opts.inPurgeable = true;
        opts.inInputShareable = true;
        if (((double) scale) < 0.25d) {
            opts.inSampleSize = 2;
        } else if (((double) scale) < 0.125d) {
            opts.inSampleSize = 4;
        } else {
            opts.inSampleSize = 1;
        }
        opts.outHeight = destHeight;
        opts.outWidth = destWidth;
        opts.inJustDecodeBounds = false;
        opts.inDither = false;
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(), opts);
        if (bitmap != null) {
            return getCutBitmap(bitmap, desiredWidth, desiredHeight);
        }
        return null;
    }

    public static Bitmap getCutBitmap(Bitmap bitmap, int desiredWidth, int desiredHeight) {
        Bitmap resizeBmp = null;
        if (checkBitmap(bitmap) && checkSize(desiredWidth, desiredHeight)) {
            resizeBmp = null;
            try {
                int width = bitmap.getWidth();
                int height = bitmap.getHeight();
                int offsetX = 0;
                int offsetY = 0;
                if (width > desiredWidth) {
                    offsetX = (width - desiredWidth) / 2;
                } else {
                    desiredWidth = width;
                }
                if (height > desiredHeight) {
                    offsetY = (height - desiredHeight) / 2;
                } else {
                    desiredHeight = height;
                }
                resizeBmp = Bitmap.createBitmap(bitmap, offsetX, offsetY, desiredWidth, desiredHeight);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (resizeBmp != bitmap) {
                }
            }
        }
        return resizeBmp;
    }

    public static float[] getBitmapSize(File file) {
        float[] size = new float[2];
        Options opts = new Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(file.getPath(), opts);
        size[0] = (float) opts.outWidth;
        size[1] = (float) opts.outHeight;
        return size;
    }

    private static float getMinScale(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        float scaleWidth = ((float) desiredWidth) / ((float) srcWidth);
        float scaleHeight = ((float) desiredHeight) / ((float) srcHeight);
        if (scaleWidth > scaleHeight) {
            return scaleWidth;
        }
        return scaleHeight;
    }

    private static int[] resizeToMaxSize(int srcWidth, int srcHeight, int desiredWidth, int desiredHeight) {
        int[] size = new int[2];
        if (desiredWidth <= 0) {
            desiredWidth = srcWidth;
        }
        if (desiredHeight <= 0) {
            desiredHeight = srcHeight;
        }
        if (desiredWidth > 2048) {
            desiredWidth = 2048;
            desiredHeight = (int) (((float) desiredHeight) * (((float) 2048) / ((float) srcWidth)));
        }
        if (desiredHeight > 2048) {
            desiredHeight = 2048;
            desiredWidth = (int) (((float) desiredWidth) * (((float) 2048) / ((float) srcHeight)));
        }
        size[0] = desiredWidth;
        size[1] = desiredHeight;
        return size;
    }

    private static boolean checkBitmap(Bitmap bitmap) {
        if (bitmap != null && bitmap.getWidth() > 0 && bitmap.getHeight() > 0) {
            return true;
        }
        return false;
    }

    private static boolean checkSize(int desiredWidth, int desiredHeight) {
        if (desiredWidth <= 0 || desiredHeight <= 0) {
            return false;
        }
        return true;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), drawable.getOpacity() != -1 ? Config.ARGB_8888 : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Drawable bitmapToDrawable(Bitmap bitmap) {
        Drawable mBitmapDrawable = null;
        if (bitmap == null) {
            return null;
        }
        try {
            mBitmapDrawable = new BitmapDrawable(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mBitmapDrawable;
    }

    public static byte[] bitmap2Bytes(Bitmap bitmap, int maxkb, boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, output);
        int options = 100;
        while (output.toByteArray().length > maxkb && options != 10) {
            try {
                output.reset();
                bitmap.compress(CompressFormat.JPEG, options, output);
                options -= 10;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (needRecycle) {
            bitmap.recycle();
        }
        return output.toByteArray();
    }

    /* JADX WARNING: Removed duplicated region for block: B:19:0x0028 A:{SYNTHETIC, Splitter: B:19:0x0028} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0034 A:{SYNTHETIC, Splitter: B:25:0x0034} */
    public static byte[] bitmap2Bytes(android.graphics.Bitmap r5, android.graphics.Bitmap.CompressFormat r6, boolean r7) {
        /*
        r3 = 0;
        r1 = 0;
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x0022 }
        r2.<init>();	 Catch:{ Exception -> 0x0022 }
        r4 = 10;
        r5.compress(r6, r4, r2);	 Catch:{ Exception -> 0x0040, all -> 0x003d }
        r3 = r2.toByteArray();	 Catch:{ Exception -> 0x0040, all -> 0x003d }
        if (r7 == 0) goto L_0x0015;
    L_0x0012:
        r5.recycle();	 Catch:{ Exception -> 0x0040, all -> 0x003d }
    L_0x0015:
        if (r2 == 0) goto L_0x0043;
    L_0x0017:
        r2.close();	 Catch:{ Exception -> 0x001c }
        r1 = r2;
    L_0x001b:
        return r3;
    L_0x001c:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = r2;
        goto L_0x001b;
    L_0x0022:
        r0 = move-exception;
    L_0x0023:
        r0.printStackTrace();	 Catch:{ all -> 0x0031 }
        if (r1 == 0) goto L_0x001b;
    L_0x0028:
        r1.close();	 Catch:{ Exception -> 0x002c }
        goto L_0x001b;
    L_0x002c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001b;
    L_0x0031:
        r4 = move-exception;
    L_0x0032:
        if (r1 == 0) goto L_0x0037;
    L_0x0034:
        r1.close();	 Catch:{ Exception -> 0x0038 }
    L_0x0037:
        throw r4;
    L_0x0038:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0037;
    L_0x003d:
        r4 = move-exception;
        r1 = r2;
        goto L_0x0032;
    L_0x0040:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0023;
    L_0x0043:
        r1 = r2;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.utils.ImageUtil.bitmap2Bytes(android.graphics.Bitmap, android.graphics.Bitmap$CompressFormat, boolean):byte[]");
    }

    /* JADX WARNING: Removed duplicated region for block: B:17:0x0024 A:{SYNTHETIC, Splitter: B:17:0x0024} */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0030 A:{SYNTHETIC, Splitter: B:23:0x0030} */
    public static int getByteCount(android.graphics.Bitmap r6, android.graphics.Bitmap.CompressFormat r7) {
        /*
        r4 = 0;
        r1 = 0;
        r2 = new java.io.ByteArrayOutputStream;	 Catch:{ Exception -> 0x001e }
        r2.<init>();	 Catch:{ Exception -> 0x001e }
        r5 = 100;
        r6.compress(r7, r5, r2);	 Catch:{ Exception -> 0x003c, all -> 0x0039 }
        r3 = r2.toByteArray();	 Catch:{ Exception -> 0x003c, all -> 0x0039 }
        r4 = r3.length;	 Catch:{ Exception -> 0x003c, all -> 0x0039 }
        if (r2 == 0) goto L_0x003f;
    L_0x0013:
        r2.close();	 Catch:{ Exception -> 0x0018 }
        r1 = r2;
    L_0x0017:
        return r4;
    L_0x0018:
        r0 = move-exception;
        r0.printStackTrace();
        r1 = r2;
        goto L_0x0017;
    L_0x001e:
        r0 = move-exception;
    L_0x001f:
        r0.printStackTrace();	 Catch:{ all -> 0x002d }
        if (r1 == 0) goto L_0x0017;
    L_0x0024:
        r1.close();	 Catch:{ Exception -> 0x0028 }
        goto L_0x0017;
    L_0x0028:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0017;
    L_0x002d:
        r5 = move-exception;
    L_0x002e:
        if (r1 == 0) goto L_0x0033;
    L_0x0030:
        r1.close();	 Catch:{ Exception -> 0x0034 }
    L_0x0033:
        throw r5;
    L_0x0034:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0033;
    L_0x0039:
        r5 = move-exception;
        r1 = r2;
        goto L_0x002e;
    L_0x003c:
        r0 = move-exception;
        r1 = r2;
        goto L_0x001f;
    L_0x003f:
        r1 = r2;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.utils.ImageUtil.getByteCount(android.graphics.Bitmap, android.graphics.Bitmap$CompressFormat):int");
    }

    public static Bitmap bytes2Bimap(byte[] b) {
        try {
            if (b.length != 0) {
                return BitmapFactory.decodeByteArray(b, 0, b.length);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap imageView2Bitmap(ImageView view) {
        Bitmap bitmap = null;
        try {
            bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static void saveArticleMixedPicture(Context context, String imageUrl, String description) {
        Bitmap bitmap = null;
        try {
            FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
            if (resource != null) {
                bitmap = BitmapFactory.decodeFile(resource.getFile().getPath());
            }
            if (bitmap != null) {
                Bitmap bgBitmap;
                int width_bg;
                if (StringUtil.isEmpty(description)) {
                    width_bg = bitmap.getWidth();
                    bgBitmap = Bitmap.createBitmap(width_bg, bitmap.getHeight(), Config.ARGB_8888);
                    new Canvas(bgBitmap).drawBitmap(bitmap, 0.0f, 0.0f, new Paint());
                } else {
                    TextPaint textPaint = new TextPaint();
                    textPaint.setColor(context.getResources().getColor(2131558559));
                    textPaint.setTextSize(25.0f);
                    int m10 = context.getResources().getDimensionPixelSize(2131296268);
                    width_bg = bitmap.getWidth();
                    String str = description;
                    int height_bg = (bitmap.getHeight() + (new StaticLayout(str, textPaint, new Rect(m10, bitmap.getHeight() + m10, width_bg - m10, (bitmap.getHeight() + m10) + 1).width(), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true).getLineCount() * 40)) + m10;
                    bgBitmap = Bitmap.createBitmap(width_bg, height_bg, Config.ARGB_8888);
                    Paint paint = new Paint();
                    Canvas canvas = new Canvas(bgBitmap);
                    canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
                    RectF rectF = new RectF(0.0f, (float) bitmap.getHeight(), (float) width_bg, (float) height_bg);
                    paint.setColor(context.getResources().getColor(2131558443));
                    canvas.drawRect(rectF, paint);
                    Rect rect = new Rect(m10, bitmap.getHeight() + m10, width_bg - m10, height_bg);
                    try {
                        Matcher m = HttpConstant.PATTERN_EMOTICON.matcher(description);
                        while (m.find()) {
                            Matcher matcher = m;
                            description = matcher.replaceAll((String) EmoticonsRule.sXhsEmoticonTextHashMap.get(m.group()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    StaticLayout layout = new StaticLayout(description, textPaint, rect.width(), Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true);
                    canvas.save();
                    textPaint.setTextAlign(Align.LEFT);
                    canvas.translate((float) rect.left, (float) rect.top);
                    layout.draw(canvas);
                    canvas.restore();
                }
                if (bgBitmap != null) {
                    FengUtil.saveBitmapToFile(bgBitmap, FengUtil.getAppDir().getAbsolutePath() + "/", System.currentTimeMillis() + ".jpg");
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static Drawable view2Drawable(View view) {
        try {
            Bitmap newbmp = view2Bitmap(view);
            if (newbmp != null) {
                return new BitmapDrawable(newbmp);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap view2Bitmap(View view) {
        Bitmap bitmap = null;
        if (view == null) {
            return bitmap;
        }
        try {
            view.setDrawingCacheEnabled(true);
            view.measure(MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
            view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
            view.buildDrawingCache();
            return view.getDrawingCache();
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static byte[] view2Bytes(View view, CompressFormat compressFormat) {
        byte[] b = null;
        try {
            return bitmap2Bytes(view2Bitmap(view), compressFormat, true);
        } catch (Exception e) {
            e.printStackTrace();
            return b;
        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, float degrees) {
        Bitmap mBitmap = null;
        try {
            Matrix m = new Matrix();
            m.setRotate(degrees % 360.0f);
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, false);
        } catch (Exception e) {
            e.printStackTrace();
            return mBitmap;
        }
    }

    public static Bitmap rotateBitmapTranslate(Bitmap bitmap, float degrees) {
        try {
            int width;
            int height;
            Matrix matrix = new Matrix();
            if ((degrees / 90.0f) % 2.0f != 0.0f) {
                width = bitmap.getWidth();
                height = bitmap.getHeight();
            } else {
                width = bitmap.getHeight();
                height = bitmap.getWidth();
            }
            int cx = width / 2;
            int cy = height / 2;
            matrix.preTranslate((float) (-cx), (float) (-cy));
            matrix.postRotate(degrees);
            matrix.postTranslate((float) cx, (float) cy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap toRoundBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        float roundPx;
        float top;
        float bottom;
        float left;
        float right;
        float dst_left;
        float dst_top;
        float dst_right;
        float dst_bottom;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width <= height) {
            roundPx = (float) (width / 2);
            top = 0.0f;
            bottom = (float) width;
            left = 0.0f;
            right = (float) width;
            height = width;
            dst_left = 0.0f;
            dst_top = 0.0f;
            dst_right = (float) width;
            dst_bottom = (float) width;
        } else {
            roundPx = (float) (height / 2);
            float clip = (float) ((width - height) / 2);
            left = clip;
            right = ((float) width) - clip;
            top = 0.0f;
            bottom = (float) height;
            width = height;
            dst_left = 0.0f;
            dst_top = 0.0f;
            dst_right = (float) height;
            dst_bottom = (float) height;
        }
        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        Rect rect = new Rect((int) left, (int) top, (int) right, (int) bottom);
        Rect dst = new Rect((int) dst_left, (int) dst_top, (int) dst_right, (int) dst_bottom);
        RectF rectF = new RectF(dst);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(-12434878);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, dst, paint);
        return output;
    }

    public static Bitmap toReflectionBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        try {
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2, width, height / 2, matrix, false);
            Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height / 2) + height, Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapWithReflection);
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, null);
            canvas.drawRect(0.0f, (float) height, (float) width, (float) (height + 1), new Paint());
            canvas.drawBitmap(reflectionImage, 0.0f, (float) (height + 1), null);
            Paint paint = new Paint();
            paint.setShader(new LinearGradient(0.0f, (float) bitmap.getHeight(), 0.0f, (float) (bitmapWithReflection.getHeight() + 1), 1895825407, 16777215, TileMode.CLAMP));
            paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
            canvas.drawRect(0.0f, (float) height, (float) width, (float) (bitmapWithReflection.getHeight() + 1), paint);
            return bitmapWithReflection;
        } catch (Exception e) {
            e.printStackTrace();
            return bitmap;
        }
    }

    public static void releaseBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    Log.d(String.valueOf(ImageUtil.class), "Bitmap释放" + bitmap.toString());
                    bitmap.recycle();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static int hammingDistance(String sourceHashCode, String hashCode) {
        int difference = 0;
        int len = sourceHashCode.length();
        for (int i = 0; i < len; i++) {
            if (sourceHashCode.charAt(i) != hashCode.charAt(i)) {
                difference++;
            }
        }
        return difference;
    }

    private static int rgbToGray(int pixels) {
        return (int) (((0.3d * ((double) ((pixels >> 16) & 255))) + (0.59d * ((double) ((pixels >> 8) & 255)))) + (0.11d * ((double) (pixels & 255))));
    }

    public static void main(String[] args) {
    }

    public static Bitmap doBlur(Bitmap sentBitmap, int radius, boolean canReuseInBitmap) {
        Bitmap bitmap;
        if (canReuseInBitmap) {
            bitmap = sentBitmap;
        } else {
            bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);
        }
        if (radius < 1) {
            return null;
        }
        int i;
        int y;
        int bsum;
        int gsum;
        int rsum;
        int boutsum;
        int goutsum;
        int routsum;
        int binsum;
        int ginsum;
        int rinsum;
        int p;
        int[] sir;
        int rbs;
        int stackpointer;
        int x;
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        int[] pix = new int[(w * h)];
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);
        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = (radius + radius) + 1;
        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int[] vmin = new int[Math.max(w, h)];
        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int[] dv = new int[(divsum * AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS)];
        for (i = 0; i < divsum * AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS; i++) {
            dv[i] = i / divsum;
        }
        int yi = 0;
        int yw = 0;
        int[][] stack = (int[][]) Array.newInstance(Integer.TYPE, new int[]{div, 3});
        int r1 = radius + 1;
        for (y = 0; y < h; y++) {
            bsum = 0;
            gsum = 0;
            rsum = 0;
            boutsum = 0;
            goutsum = 0;
            routsum = 0;
            binsum = 0;
            ginsum = 0;
            rinsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[Math.min(wm, Math.max(i, 0)) + yi];
                sir = stack[i + radius];
                sir[0] = (16711680 & p) >> 16;
                sir[1] = (65280 & p) >> 8;
                sir[2] = p & 255;
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;
            for (x = 0; x < w; x++) {
                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                sir = stack[((stackpointer - radius) + div) % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (y == 0) {
                    vmin[x] = Math.min((x + radius) + 1, wm);
                }
                p = pix[vmin[x] + yw];
                sir[0] = (16711680 & p) >> 16;
                sir[1] = (65280 & p) >> 8;
                sir[2] = p & 255;
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer % div];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            bsum = 0;
            gsum = 0;
            rsum = 0;
            boutsum = 0;
            goutsum = 0;
            routsum = 0;
            binsum = 0;
            ginsum = 0;
            rinsum = 0;
            int yp = (-radius) * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;
                sir = stack[i + radius];
                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];
                rbs = r1 - Math.abs(i);
                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                pix[yi] = (((-16777216 & pix[yi]) | (dv[rsum] << 16)) | (dv[gsum] << 8)) | dv[bsum];
                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;
                sir = stack[((stackpointer - radius) + div) % div];
                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];
                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];
                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];
                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];
                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;
                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];
                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];
                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];
                yi += w;
            }
        }
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);
        return bitmap;
    }

    public static Bitmap getZoomImage(Bitmap orgBitmap, double newWidth, double newHeight) {
        if (orgBitmap == null) {
            return null;
        }
        if (orgBitmap.isRecycled()) {
            return null;
        }
        if (newWidth <= 0.0d || newHeight <= 0.0d) {
            return null;
        }
        float width = (float) orgBitmap.getWidth();
        float height = (float) orgBitmap.getHeight();
        Matrix matrix = new Matrix();
        matrix.postScale(((float) newWidth) / width, ((float) newHeight) / height);
        return Bitmap.createBitmap(orgBitmap, 0, 0, (int) width, (int) height, matrix, true);
    }
}
