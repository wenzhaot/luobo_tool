package com.meizu.cloud.pushsdk.networking.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Build.VERSION;
import android.os.NetworkOnMainThreadException;
import android.widget.ImageView.ScaleType;
import com.meizu.cloud.pushsdk.networking.common.ANConstants;
import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.common.ANResponse;
import com.meizu.cloud.pushsdk.networking.core.Core;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.http.Response;
import com.meizu.cloud.pushsdk.networking.interfaces.AnalyticsListener;
import com.meizu.cloud.pushsdk.networking.okio.Okio;
import java.io.File;
import java.io.IOException;
import java.net.URLConnection;

public class Utils {
    public static File getDiskCacheDir(Context context, String uniqueName) {
        return new File(context.getCacheDir(), uniqueName);
    }

    public static String getMimeType(String path) {
        String contentTypeFor = URLConnection.getFileNameMap().getContentTypeFor(path);
        if (contentTypeFor == null) {
            return "application/octet-stream";
        }
        return contentTypeFor;
    }

    public static ANResponse<Bitmap> decodeBitmap(Response response, int maxWidth, int maxHeight, Config decodeConfig, ScaleType scaleType) {
        Bitmap bitmap;
        byte[] data = new byte[0];
        try {
            data = Okio.buffer(response.body().source()).readByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Options decodeOptions = new Options();
        if (maxWidth == 0 && maxHeight == 0) {
            decodeOptions.inPreferredConfig = decodeConfig;
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
        } else {
            decodeOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            int actualWidth = decodeOptions.outWidth;
            int actualHeight = decodeOptions.outHeight;
            int desiredWidth = getResizedDimension(maxWidth, maxHeight, actualWidth, actualHeight, scaleType);
            int desiredHeight = getResizedDimension(maxHeight, maxWidth, actualHeight, actualWidth, scaleType);
            decodeOptions.inJustDecodeBounds = false;
            decodeOptions.inSampleSize = findBestSampleSize(actualWidth, actualHeight, desiredWidth, desiredHeight);
            Bitmap tempBitmap = BitmapFactory.decodeByteArray(data, 0, data.length, decodeOptions);
            if (tempBitmap == null || (tempBitmap.getWidth() <= desiredWidth && tempBitmap.getHeight() <= desiredHeight)) {
                bitmap = tempBitmap;
            } else {
                bitmap = Bitmap.createScaledBitmap(tempBitmap, desiredWidth, desiredHeight, true);
                tempBitmap.recycle();
            }
        }
        if (bitmap == null) {
            return ANResponse.failed(getErrorForParse(new ANError(response)));
        }
        return ANResponse.success(bitmap);
    }

    private static int getResizedDimension(int maxPrimary, int maxSecondary, int actualPrimary, int actualSecondary, ScaleType scaleType) {
        if (maxPrimary == 0 && maxSecondary == 0) {
            return actualPrimary;
        }
        if (scaleType == ScaleType.FIT_XY) {
            if (maxPrimary != 0) {
                return maxPrimary;
            }
            return actualPrimary;
        } else if (maxPrimary == 0) {
            return (int) (((double) actualPrimary) * (((double) maxSecondary) / ((double) actualSecondary)));
        } else if (maxSecondary == 0) {
            return maxPrimary;
        } else {
            double ratio = ((double) actualSecondary) / ((double) actualPrimary);
            int resized = maxPrimary;
            if (scaleType == ScaleType.CENTER_CROP) {
                if (((double) resized) * ratio < ((double) maxSecondary)) {
                    resized = (int) (((double) maxSecondary) / ratio);
                }
                return resized;
            }
            if (((double) resized) * ratio > ((double) maxSecondary)) {
                resized = (int) (((double) maxSecondary) / ratio);
            }
            return resized;
        }
    }

    public static int findBestSampleSize(int actualWidth, int actualHeight, int desiredWidth, int desiredHeight) {
        float n = 1.0f;
        while (((double) (2.0f * n)) <= Math.min(((double) actualWidth) / ((double) desiredWidth), ((double) actualHeight) / ((double) desiredHeight))) {
            n *= 2.0f;
        }
        return (int) n;
    }

    /* JADX WARNING: Removed duplicated region for block: B:14:0x0036 A:{SYNTHETIC, Splitter: B:14:0x0036} */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x003b A:{SYNTHETIC, Splitter: B:17:0x003b} */
    public static void saveFile(com.meizu.cloud.pushsdk.networking.http.Response r9, java.lang.String r10, java.lang.String r11) throws java.io.IOException {
        /*
        r6 = 0;
        r8 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r0 = new byte[r8];
        r4 = 0;
        r8 = r9.body();	 Catch:{ all -> 0x0061 }
        r6 = r8.byteStream();	 Catch:{ all -> 0x0061 }
        r1 = new java.io.File;	 Catch:{ all -> 0x0061 }
        r1.<init>(r10);	 Catch:{ all -> 0x0061 }
        r8 = r1.exists();	 Catch:{ all -> 0x0061 }
        if (r8 != 0) goto L_0x001c;
    L_0x0019:
        r1.mkdirs();	 Catch:{ all -> 0x0061 }
    L_0x001c:
        r3 = new java.io.File;	 Catch:{ all -> 0x0061 }
        r3.<init>(r1, r11);	 Catch:{ all -> 0x0061 }
        r5 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0061 }
        r5.<init>(r3);	 Catch:{ all -> 0x0061 }
    L_0x0026:
        r7 = r6.read(r0);	 Catch:{ all -> 0x0032 }
        r8 = -1;
        if (r7 == r8) goto L_0x003f;
    L_0x002d:
        r8 = 0;
        r5.write(r0, r8, r7);	 Catch:{ all -> 0x0032 }
        goto L_0x0026;
    L_0x0032:
        r8 = move-exception;
        r4 = r5;
    L_0x0034:
        if (r6 == 0) goto L_0x0039;
    L_0x0036:
        r6.close();	 Catch:{ IOException -> 0x0057 }
    L_0x0039:
        if (r4 == 0) goto L_0x003e;
    L_0x003b:
        r4.close();	 Catch:{ IOException -> 0x005c }
    L_0x003e:
        throw r8;
    L_0x003f:
        r5.flush();	 Catch:{ all -> 0x0032 }
        if (r6 == 0) goto L_0x0047;
    L_0x0044:
        r6.close();	 Catch:{ IOException -> 0x004d }
    L_0x0047:
        if (r5 == 0) goto L_0x004c;
    L_0x0049:
        r5.close();	 Catch:{ IOException -> 0x0052 }
    L_0x004c:
        return;
    L_0x004d:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0047;
    L_0x0052:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x004c;
    L_0x0057:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x0039;
    L_0x005c:
        r2 = move-exception;
        r2.printStackTrace();
        goto L_0x003e;
    L_0x0061:
        r8 = move-exception;
        goto L_0x0034;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.meizu.cloud.pushsdk.networking.utils.Utils.saveFile(com.meizu.cloud.pushsdk.networking.http.Response, java.lang.String, java.lang.String):void");
    }

    public static void sendAnalytics(AnalyticsListener analyticsListener, long timeTakenInMillis, long bytesSent, long bytesReceived, boolean isFromCache) {
        final AnalyticsListener analyticsListener2 = analyticsListener;
        final long j = timeTakenInMillis;
        final long j2 = bytesSent;
        final long j3 = bytesReceived;
        final boolean z = isFromCache;
        Core.getInstance().getExecutorSupplier().forMainThreadTasks().execute(new Runnable() {
            public void run() {
                if (analyticsListener2 != null) {
                    analyticsListener2.onReceived(j, j2, j3, z);
                }
            }
        });
    }

    public static ANError getErrorForConnection(ANError error) {
        error.setErrorDetail(ANConstants.CONNECTION_ERROR);
        error.setErrorCode(0);
        error.setErrorBody(error.getMessage());
        return error;
    }

    public static ANError getErrorForServerResponse(ANError error, ANRequest request, int code) {
        error = request.parseNetworkError(error);
        error.setErrorCode(code);
        error.setErrorDetail(ANConstants.RESPONSE_FROM_SERVER_ERROR);
        return error;
    }

    public static ANError getErrorForParse(ANError error) {
        error.setErrorCode(0);
        error.setErrorDetail(ANConstants.PARSE_ERROR);
        error.setErrorBody(error.getMessage());
        return error;
    }

    public static ANError getErrorForNetworkOnMainThreadOrConnection(Exception e) {
        ANError error = new ANError((Throwable) e);
        if (VERSION.SDK_INT < 11 || !(e instanceof NetworkOnMainThreadException)) {
            error.setErrorDetail(ANConstants.CONNECTION_ERROR);
        } else {
            error.setErrorDetail(ANConstants.NETWORK_ON_MAIN_THREAD_ERROR);
        }
        error.setErrorCode(0);
        return error;
    }
}
