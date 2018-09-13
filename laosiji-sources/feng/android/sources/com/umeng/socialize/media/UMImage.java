package com.umeng.socialize.media;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import com.stub.StubApp;
import com.umeng.social.tool.UMImageMark;
import com.umeng.socialize.b.a.a;
import com.umeng.socialize.media.UMediaObject.MediaType;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.IMAGE;
import java.io.Closeable;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class UMImage extends BaseMediaObject {
    public static int BINARY_IMAGE = 5;
    public static int BITMAP_IMAGE = 4;
    public static int FILE_IMAGE = 1;
    public static int MAX_HEIGHT = 1024;
    public static int MAX_WIDTH = 768;
    public static int RES_IMAGE = 3;
    public static int URL_IMAGE = 2;
    public CompressFormat compressFormat = CompressFormat.JPEG;
    public CompressStyle compressStyle = CompressStyle.SCALE;
    private ConfiguredConvertor f = null;
    private UMImage g;
    private UMImageMark h;
    private int i = 0;
    public boolean isLoadImgByCompress = true;
    private boolean j;

    interface IImageConvertor {
        byte[] asBinary();

        Bitmap asBitmap();

        File asFile();

        String asUrl();
    }

    static abstract class ConfiguredConvertor implements IImageConvertor {
        ConfiguredConvertor() {
        }
    }

    class BinaryConvertor extends ConfiguredConvertor {
        private byte[] b;

        public BinaryConvertor(byte[] bArr) {
            this.b = bArr;
        }

        public File asFile() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.b(asBinary());
            }
            return null;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return this.b;
        }

        public Bitmap asBitmap() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.a(asBinary());
            }
            return null;
        }
    }

    class BitmapConvertor extends ConfiguredConvertor {
        private Bitmap b;

        public BitmapConvertor(Bitmap bitmap) {
            this.b = bitmap;
        }

        public File asFile() {
            byte[] a = a.a(this.b, UMImage.this.compressFormat);
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.b(a);
            }
            return null;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return a.a(this.b, UMImage.this.compressFormat);
        }

        public Bitmap asBitmap() {
            return this.b;
        }
    }

    public enum CompressStyle {
        SCALE,
        QUALITY
    }

    class FileConvertor extends ConfiguredConvertor {
        private File b;

        public FileConvertor(File file) {
            this.b = file;
        }

        public File asFile() {
            return this.b;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return a.a(this.b, UMImage.this.compressFormat);
        }

        public Bitmap asBitmap() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.a(UMImage.this.asBinImage());
            }
            return null;
        }
    }

    class ResConvertor extends ConfiguredConvertor {
        private Context b;
        private int c = 0;

        public ResConvertor(Context context, int i) {
            this.b = context;
            this.c = i;
        }

        public File asFile() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.b(asBinary());
            }
            return null;
        }

        public String asUrl() {
            return null;
        }

        public byte[] asBinary() {
            return a.a(this.b, this.c, UMImage.this.isLoadImgByCompress, UMImage.this.compressFormat);
        }

        public Bitmap asBitmap() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.a(asBinary());
            }
            return null;
        }
    }

    class UrlConvertor extends ConfiguredConvertor {
        private String b = null;

        public UrlConvertor(String str) {
            this.b = str;
        }

        public File asFile() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.b(asBinary());
            }
            return null;
        }

        public String asUrl() {
            return this.b;
        }

        public byte[] asBinary() {
            return a.a(this.b);
        }

        public Bitmap asBitmap() {
            if (SocializeUtils.assertBinaryInvalid(asBinary())) {
                return a.a(asBinary());
            }
            return null;
        }
    }

    public UMImage(Context context, File file) {
        a(context, (Object) file);
    }

    public UMImage(Context context, String str) {
        super(str);
        a((Context) new WeakReference(context).get(), (Object) str);
    }

    public UMImage(Context context, int i) {
        a(context, Integer.valueOf(i));
    }

    public UMImage(Context context, byte[] bArr) {
        a(context, (Object) bArr);
    }

    public UMImage(Context context, Bitmap bitmap) {
        a(context, (Object) bitmap);
    }

    public UMImage(Context context, Bitmap bitmap, UMImageMark uMImageMark) {
        a(context, bitmap, uMImageMark);
    }

    public UMImage(Context context, int i, UMImageMark uMImageMark) {
        a(context, Integer.valueOf(i), uMImageMark);
    }

    public UMImage(Context context, byte[] bArr, UMImageMark uMImageMark) {
        a(context, bArr, uMImageMark);
    }

    private void a(Context context, Object obj) {
        a(context, obj, null);
    }

    private void a(Context context, Object obj, UMImageMark uMImageMark) {
        Bitmap bitmap = null;
        if (uMImageMark != null) {
            this.j = true;
            this.h = uMImageMark;
            this.h.setContext(context);
        }
        if (ContextUtil.getContext() == null) {
            ContextUtil.setContext(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        }
        if (obj instanceof File) {
            this.i = FILE_IMAGE;
            this.f = new FileConvertor((File) obj);
        } else if (obj instanceof String) {
            this.i = URL_IMAGE;
            this.f = new UrlConvertor((String) obj);
        } else if (obj instanceof Integer) {
            this.i = RES_IMAGE;
            if (isHasWaterMark()) {
                bitmap = a(context, ((Integer) obj).intValue());
            }
            if (bitmap != null) {
                this.f = new BitmapConvertor(bitmap);
            } else {
                this.f = new ResConvertor(StubApp.getOrigApplicationContext(context.getApplicationContext()), ((Integer) obj).intValue());
            }
        } else if (obj instanceof byte[]) {
            this.i = BINARY_IMAGE;
            if (isHasWaterMark()) {
                bitmap = a((byte[]) obj);
            }
            if (bitmap != null) {
                this.f = new BitmapConvertor(bitmap);
            } else {
                this.f = new BinaryConvertor((byte[]) obj);
            }
        } else if (obj instanceof Bitmap) {
            Bitmap bitmap2;
            this.i = BITMAP_IMAGE;
            if (isHasWaterMark()) {
                bitmap = a((Bitmap) obj, true);
            }
            if (bitmap == null) {
                bitmap2 = (Bitmap) obj;
            } else {
                bitmap2 = bitmap;
            }
            this.f = new BitmapConvertor(bitmap2);
        } else if (obj != null) {
            SLog.E(IMAGE.UNKNOW_UMIMAGE + obj.getClass().getSimpleName());
        } else {
            SLog.E(IMAGE.UNKNOW_UMIMAGE + "null");
        }
    }

    public byte[] toByte() {
        return asBinImage();
    }

    public void setThumb(UMImage uMImage) {
        this.g = uMImage;
    }

    public UMImage getThumbImage() {
        return this.g;
    }

    public final Map<String, Object> toUrlExtraParams() {
        Map<String, Object> hashMap = new HashMap();
        if (isUrlMedia()) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FURL, this.a);
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_FTYPE, getMediaType());
        }
        return hashMap;
    }

    public MediaType getMediaType() {
        return MediaType.IMAGE;
    }

    public int getImageStyle() {
        return this.i;
    }

    public File asFileImage() {
        return this.f == null ? null : this.f.asFile();
    }

    public String asUrlImage() {
        return this.f == null ? null : this.f.asUrl();
    }

    public byte[] asBinImage() {
        return this.f == null ? null : this.f.asBinary();
    }

    public Bitmap asBitmap() {
        return this.f == null ? null : this.f.asBitmap();
    }

    private Bitmap a(Bitmap bitmap, boolean z) {
        if (this.h == null) {
            return bitmap;
        }
        if (bitmap == null) {
            return null;
        }
        if (z) {
            try {
                bitmap = a(bitmap);
            } catch (Throwable e) {
                SLog.error(e);
                return null;
            }
        }
        return this.h.compound(bitmap);
    }

    private Bitmap a(Context context, int i) {
        Throwable e;
        Throwable th;
        Bitmap bitmap = null;
        if (!(i == 0 || context == null || this.h == null)) {
            Closeable openRawResource;
            try {
                Options options = new Options();
                options.inJustDecodeBounds = true;
                openRawResource = context.getResources().openRawResource(i);
                try {
                    BitmapFactory.decodeStream(openRawResource, null, options);
                    a(openRawResource);
                    int a = (int) a((float) options.outWidth, (float) options.outHeight, (float) MAX_WIDTH, (float) MAX_HEIGHT);
                    if (a > 0) {
                        options.inSampleSize = a;
                    }
                    options.inJustDecodeBounds = false;
                    openRawResource = context.getResources().openRawResource(i);
                    bitmap = a(BitmapFactory.decodeStream(openRawResource, null, options), false);
                    a(openRawResource);
                } catch (Exception e2) {
                    e = e2;
                    try {
                        SLog.error(e);
                        a(openRawResource);
                        return bitmap;
                    } catch (Throwable th2) {
                        th = th2;
                        a(openRawResource);
                        throw th;
                    }
                }
            } catch (Exception e3) {
                e = e3;
                openRawResource = null;
                SLog.error(e);
                a(openRawResource);
                return bitmap;
            } catch (Throwable e4) {
                openRawResource = null;
                th = e4;
                a(openRawResource);
                throw th;
            }
        }
        return bitmap;
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
    }

    private Bitmap a(byte[] bArr) {
        if (bArr == null || this.h == null) {
            return null;
        }
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options);
            int a = (int) a((float) options.outWidth, (float) options.outHeight, (float) MAX_WIDTH, (float) MAX_HEIGHT);
            if (a > 0) {
                options.inSampleSize = a;
            }
            options.inJustDecodeBounds = false;
            return a(BitmapFactory.decodeByteArray(bArr, 0, bArr.length, options), false);
        } catch (Throwable e) {
            SLog.error(e);
            return null;
        }
    }

    private Bitmap a(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float a = a((float) width, (float) height, (float) MAX_WIDTH, (float) MAX_HEIGHT);
        if (a < 0.0f) {
            return bitmap;
        }
        a = 1.0f / a;
        Matrix matrix = new Matrix();
        matrix.postScale(a, a);
        Bitmap createBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, false);
        b(bitmap);
        return createBitmap;
    }

    private float a(float f, float f2, float f3, float f4) {
        if (f <= f4 && f2 <= f4) {
            return -1.0f;
        }
        float f5 = f / f3;
        float f6 = f2 / f4;
        return f5 <= f6 ? f6 : f5;
    }

    private void b(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                if (!bitmap.isRecycled()) {
                    bitmap.recycle();
                }
            } catch (Throwable e) {
                SLog.error(e);
            }
        }
    }

    public boolean isHasWaterMark() {
        return this.j;
    }
}
