package com.umeng.message.inapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.proguard.h;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

class UImageLoadTask extends AsyncTask<String, Void, Bitmap[]> {
    private static final String a = UImageLoadTask.class.getName();
    private ImageLoaderCallback b;
    private String c;
    private Options d;

    interface ImageLoaderCallback {
        void onLoadImage(Bitmap[] bitmapArr);
    }

    public UImageLoadTask(Context context, UInAppMessage uInAppMessage) {
        this.c = h.d(context, uInAppMessage.msg_id);
        try {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
            int i = displayMetrics.widthPixels;
            int i2 = displayMetrics.heightPixels;
            this.d = new Options();
            this.d.inSampleSize = a(uInAppMessage, i, i2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: a */
    protected Bitmap[] doInBackground(String... strArr) {
        Bitmap[] bitmapArr = new Bitmap[strArr.length];
        int i = 0;
        while (i < strArr.length) {
            try {
                if (a(strArr[i])) {
                    bitmapArr[i] = b(strArr[i]);
                } else {
                    bitmapArr[i] = b(strArr[i]);
                    if (bitmapArr[i] == null) {
                        bitmapArr[i] = c(strArr[i]);
                        a(bitmapArr[i], strArr[i]);
                    }
                }
                i++;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmapArr;
    }

    /* renamed from: a */
    protected void onPostExecute(Bitmap[] bitmapArr) {
        super.onPostExecute(bitmapArr);
        int length = bitmapArr.length;
        int i = 0;
        while (i < length) {
            if (bitmapArr[i] != null) {
                i++;
            } else {
                return;
            }
        }
        if (this.b != null) {
            this.b.onLoadImage(bitmapArr);
        }
    }

    private boolean a(String str) {
        return new File(this.c, str.hashCode() + "").exists();
    }

    private Bitmap b(String str) {
        Bitmap decodeFile;
        Exception e;
        try {
            decodeFile = BitmapFactory.decodeFile(this.c + (str.hashCode() + ""));
            try {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 2, "load from local");
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return decodeFile;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            decodeFile = null;
            e = exception;
        }
        return decodeFile;
    }

    private Bitmap c(String str) throws IOException {
        Bitmap decodeStream;
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "Downloading image start");
        URLConnection openConnection = new URL(str).openConnection();
        openConnection.connect();
        InputStream inputStream = openConnection.getInputStream();
        if (this.d == null) {
            decodeStream = BitmapFactory.decodeStream(inputStream);
        } else {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "decode options");
            decodeStream = BitmapFactory.decodeStream(inputStream, null, this.d);
        }
        inputStream.close();
        UMLog uMLog2 = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "Downloading image finish");
        return decodeStream;
    }

    private void a(Bitmap bitmap, String str) {
        boolean compress;
        Exception e;
        UMLog uMLog;
        if (bitmap != null) {
            try {
                File file = new File(this.c);
                if (!file.exists()) {
                    file.mkdirs();
                }
                OutputStream fileOutputStream = new FileOutputStream(new File(this.c, str.hashCode() + ""));
                compress = bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
                try {
                    fileOutputStream.close();
                } catch (Exception e2) {
                    e = e2;
                    e.printStackTrace();
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(a, 2, "store bitmap" + compress);
                }
            } catch (Exception e3) {
                e = e3;
                compress = false;
                e.printStackTrace();
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(a, 2, "store bitmap" + compress);
            }
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "store bitmap" + compress);
        }
    }

    private static int a(UInAppMessage uInAppMessage, int i, int i2) {
        int i3 = uInAppMessage.height;
        int i4 = uInAppMessage.width;
        int i5 = 1;
        if (i3 > i2 || i4 > i) {
            i3 /= 2;
            i4 /= 2;
            while (i3 / i5 >= i2 && i4 / i5 >= i) {
                i5 *= 2;
            }
        }
        return i5;
    }

    public void a(ImageLoaderCallback imageLoaderCallback) {
        this.b = imageLoaderCallback;
    }
}
