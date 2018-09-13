package com.tencent.liteav.basic.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.MediaFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.feng.car.utils.HttpConstant;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.ugc.TXRecordCommon;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.util.UUID;

/* compiled from: TXCSystemUtil */
public class a {
    public static long a = 0;
    private static float b = 0.0f;
    private static float c = 0.0f;
    private static float d = 0.0f;
    private static float e = 0.0f;
    private static float f = 0.0f;
    private static float g = 0.0f;
    private static boolean h = true;
    private static int[] i = new int[2];
    private static long j = 0;
    private static String k = "";
    private static final Object l = new Object();
    private static boolean m = false;
    private static int[] n = new int[]{96000, 88200, 64000, TXRecordCommon.AUDIO_SAMPLERATE_48000, TXRecordCommon.AUDIO_SAMPLERATE_44100, TXRecordCommon.AUDIO_SAMPLERATE_32000, 24000, 22050, TXRecordCommon.AUDIO_SAMPLERATE_16000, 12000, 11025, TXRecordCommon.AUDIO_SAMPLERATE_8000, 7350};

    private static long g() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("/proc/" + Process.myPid() + "/stat")), 1000);
            String readLine = bufferedReader.readLine();
            bufferedReader.close();
            String[] split = readLine.split(" ");
            if (split == null || TextUtils.isEmpty(split[13])) {
                return 0;
            }
            return Long.parseLong(split[16]) + ((Long.parseLong(split[13]) + Long.parseLong(split[14])) + Long.parseLong(split[15]));
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x007e  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x008c  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x007e  */
    private static void h() {
        /*
        r0 = 0;
        r2 = android.os.Build.VERSION.SDK_INT;
        r3 = 26;
        if (r2 >= r3) goto L_0x0095;
    L_0x0008:
        r2 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0085 }
        r3 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x0085 }
        r4 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0085 }
        r5 = "/proc/stat";
        r4.<init>(r5);	 Catch:{ Exception -> 0x0085 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0085 }
        r4 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r2.<init>(r3, r4);	 Catch:{ Exception -> 0x0085 }
        if (r2 == 0) goto L_0x0095;
    L_0x001e:
        r3 = r2.readLine();	 Catch:{ Exception -> 0x0085 }
        r2.close();	 Catch:{ Exception -> 0x0085 }
        r2 = " ";
        r4 = r3.split(r2);	 Catch:{ Exception -> 0x0085 }
        if (r4 == 0) goto L_0x0095;
    L_0x002e:
        r2 = r4.length;	 Catch:{ Exception -> 0x0085 }
        r3 = 9;
        if (r2 < r3) goto L_0x0095;
    L_0x0033:
        r2 = 2;
        r2 = r4[r2];	 Catch:{ Exception -> 0x0085 }
        r2 = java.lang.Long.parseLong(r2);	 Catch:{ Exception -> 0x0085 }
        r5 = 3;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 4;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 6;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 5;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 7;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 8;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0085 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0085 }
        r2 = r2 + r6;
        r5 = 5;
        r5 = r4[r5];	 Catch:{ Exception -> 0x0093 }
        r6 = java.lang.Long.parseLong(r5);	 Catch:{ Exception -> 0x0093 }
        r5 = 6;
        r4 = r4[r5];	 Catch:{ Exception -> 0x0093 }
        r0 = java.lang.Long.parseLong(r4);	 Catch:{ Exception -> 0x0093 }
        r0 = r0 + r6;
    L_0x007a:
        r4 = h;
        if (r4 == 0) goto L_0x008c;
    L_0x007e:
        r2 = (float) r2;
        b = r2;
        r0 = (float) r0;
        f = r0;
    L_0x0084:
        return;
    L_0x0085:
        r2 = move-exception;
        r4 = r2;
        r2 = r0;
    L_0x0088:
        r4.printStackTrace();
        goto L_0x007a;
    L_0x008c:
        r2 = (float) r2;
        c = r2;
        r0 = (float) r0;
        g = r0;
        goto L_0x0084;
    L_0x0093:
        r4 = move-exception;
        goto L_0x0088;
    L_0x0095:
        r2 = r0;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.a.h():void");
    }

    public static int[] a() {
        float f = 0.0f;
        if (j != 0 && TXCTimeUtil.getTimeTick() - j < 2000) {
            return i;
        }
        int[] iArr = new int[2];
        if (h) {
            d = (float) g();
            h();
            h = false;
            iArr[0] = 0;
            iArr[1] = 0;
            return iArr;
        }
        float f2;
        e = (float) g();
        h();
        if (c != b) {
            f2 = ((e - d) * 100.0f) / (c - b);
            f = (((c - b) - (g - f)) * 100.0f) / (c - b);
        } else {
            f2 = 0.0f;
        }
        b = c;
        d = e;
        f = g;
        iArr[0] = (int) (f2 * 10.0f);
        iArr[1] = (int) (f * 10.0f);
        i[0] = iArr[0];
        i[1] = iArr[1];
        j = TXCTimeUtil.getTimeTick();
        return iArr;
    }

    public static String a(Context context) {
        return TXCDRApi.getSimulateIDFA(context);
    }

    public static String b(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
        } catch (Exception e) {
            e.printStackTrace();
            return str;
        }
    }

    public static int c(Context context) {
        if (context == null) {
            return 255;
        }
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(HttpConstant.PHONE);
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return 255;
        }
        if (!activeNetworkInfo.isConnected()) {
            return 255;
        }
        if (activeNetworkInfo.getType() == 9) {
            return 5;
        }
        if (activeNetworkInfo.getType() == 1) {
            return 1;
        }
        if (activeNetworkInfo.getType() != 0) {
            return 255;
        }
        switch (telephonyManager.getNetworkType()) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
                return 4;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
                return 3;
            case 13:
                return 2;
            default:
                return 2;
        }
    }

    public static String b() {
        return Build.MODEL;
    }

    public static String c() {
        return UUID.randomUUID().toString();
    }

    public static String d(Context context) {
        return TXCDRApi.getDevUUID(context, TXCDRApi.getSimulateIDFA(context));
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.a> weakReference, int i, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("EVT_ID", i);
        bundle.putLong("EVT_TIME", TXCTimeUtil.getTimeTick());
        if (str != null) {
            bundle.putCharSequence(TXLiveConstants.EVT_DESCRIPTION, str);
        }
        a((WeakReference) weakReference, i, bundle);
    }

    public static void a(WeakReference<com.tencent.liteav.basic.b.a> weakReference, int i, Bundle bundle) {
        if (weakReference != null) {
            com.tencent.liteav.basic.b.a aVar = (com.tencent.liteav.basic.b.a) weakReference.get();
            if (aVar != null) {
                aVar.onNotifyEvent(i, bundle);
            }
        }
    }

    public static com.tencent.liteav.basic.c.a a(int i, int i2, int i3, int i4) {
        int i5;
        int i6;
        int i7;
        int i8 = 0;
        if (i * i4 >= i2 * i3) {
            i5 = i2;
            i6 = (i2 * i3) / i4;
        } else {
            i5 = (i * i4) / i3;
            i6 = i;
        }
        if (i > i6) {
            i7 = (i - i6) >> 1;
        } else {
            i7 = 0;
        }
        if (i2 > i5) {
            i8 = (i2 - i5) >> 1;
        }
        return new com.tencent.liteav.basic.c.a(i7, i8, i6, i5);
    }

    public static void d() {
        synchronized (l) {
            if (!m) {
                a("stlport_shared");
                a("saturn");
                a("txffmpeg");
                a("liteavsdk");
                m = true;
            }
        }
    }

    public static void a(String str) {
        try {
            System.loadLibrary(str);
        } catch (Error e) {
            Log.d("NativeLoad", "load library : " + e.toString());
            b(k, str);
        } catch (Exception e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
            b(k, str);
        }
    }

    private static void b(String str, String str2) {
        try {
            if (!TextUtils.isEmpty(str)) {
                System.load(str + "/lib" + str2 + ".so");
            }
        } catch (Error e) {
            Log.d("NativeLoad", "load library : " + e.toString());
        } catch (Exception e2) {
            Log.d("NativeLoad", "load library : " + e2.toString());
        }
    }

    public static void b(String str) {
        k = str;
    }

    public static String e() {
        return k;
    }

    public static int a(int i) {
        int i2 = 0;
        while (i2 < n.length && n[i2] != i) {
            i2++;
        }
        if (i2 >= n.length) {
            return -1;
        }
        return i2;
    }

    @TargetApi(16)
    public static MediaFormat a(int i, int i2, int i3) {
        int a = a(i);
        ByteBuffer allocate = ByteBuffer.allocate(2);
        allocate.put(0, (byte) ((i3 << 3) | (a >> 1)));
        allocate.put(1, (byte) (((a & 1) << 7) | (i2 << 3)));
        MediaFormat createAudioFormat = MediaFormat.createAudioFormat("audio/mp4a-latm", i, i2);
        createAudioFormat.setInteger("channel-count", i2);
        createAudioFormat.setInteger("sample-rate", i);
        createAudioFormat.setByteBuffer("csd-0", allocate);
        return createAudioFormat;
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x0085 A:{SYNTHETIC, Splitter: B:51:0x0085} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0085 A:{SYNTHETIC, Splitter: B:51:0x0085} */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x008a  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0073 A:{SYNTHETIC, Splitter: B:42:0x0073} */
    /* JADX WARNING: Removed duplicated region for block: B:69:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x0078  */
    public static boolean a(java.lang.String r6, java.lang.String r7) {
        /*
        r3 = 0;
        r0 = 0;
        r1 = 0;
        if (r6 == 0) goto L_0x0007;
    L_0x0005:
        if (r7 != 0) goto L_0x0017;
    L_0x0007:
        if (r3 == 0) goto L_0x000c;
    L_0x0009:
        r1.close();	 Catch:{ IOException -> 0x0012 }
    L_0x000c:
        if (r3 == 0) goto L_0x0011;
    L_0x000e:
        r3.release();
    L_0x0011:
        return r0;
    L_0x0012:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x000c;
    L_0x0017:
        r2 = new java.io.File;	 Catch:{ Exception -> 0x006c, all -> 0x0081 }
        r2.<init>(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0081 }
        r2 = r2.exists();	 Catch:{ Exception -> 0x006c, all -> 0x0081 }
        if (r2 != 0) goto L_0x0032;
    L_0x0022:
        if (r3 == 0) goto L_0x0027;
    L_0x0024:
        r1.close();	 Catch:{ IOException -> 0x002d }
    L_0x0027:
        if (r3 == 0) goto L_0x0011;
    L_0x0029:
        r3.release();
        goto L_0x0011;
    L_0x002d:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0027;
    L_0x0032:
        r2 = new android.media.MediaMetadataRetriever;	 Catch:{ Exception -> 0x006c, all -> 0x0081 }
        r2.<init>();	 Catch:{ Exception -> 0x006c, all -> 0x0081 }
        r2.setDataSource(r6);	 Catch:{ Exception -> 0x0098 }
        r1 = r2.getFrameAtTime();	 Catch:{ Exception -> 0x0098 }
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0098 }
        r5.<init>(r7);	 Catch:{ Exception -> 0x0098 }
        r4 = r5.exists();	 Catch:{ Exception -> 0x0098 }
        if (r4 == 0) goto L_0x004c;
    L_0x0049:
        r5.delete();	 Catch:{ Exception -> 0x0098 }
    L_0x004c:
        r4 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x0098 }
        r4.<init>(r5);	 Catch:{ Exception -> 0x0098 }
        r3 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x009a, all -> 0x0095 }
        r5 = 100;
        r1.compress(r3, r5, r4);	 Catch:{ Exception -> 0x009a, all -> 0x0095 }
        r4.flush();	 Catch:{ Exception -> 0x009a, all -> 0x0095 }
        if (r4 == 0) goto L_0x0060;
    L_0x005d:
        r4.close();	 Catch:{ IOException -> 0x0067 }
    L_0x0060:
        if (r2 == 0) goto L_0x0065;
    L_0x0062:
        r2.release();
    L_0x0065:
        r0 = 1;
        goto L_0x0011;
    L_0x0067:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0060;
    L_0x006c:
        r1 = move-exception;
        r2 = r3;
    L_0x006e:
        r1.printStackTrace();	 Catch:{ all -> 0x0093 }
        if (r3 == 0) goto L_0x0076;
    L_0x0073:
        r3.close();	 Catch:{ IOException -> 0x007c }
    L_0x0076:
        if (r2 == 0) goto L_0x0011;
    L_0x0078:
        r2.release();
        goto L_0x0011;
    L_0x007c:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0076;
    L_0x0081:
        r0 = move-exception;
        r2 = r3;
    L_0x0083:
        if (r3 == 0) goto L_0x0088;
    L_0x0085:
        r3.close();	 Catch:{ IOException -> 0x008e }
    L_0x0088:
        if (r2 == 0) goto L_0x008d;
    L_0x008a:
        r2.release();
    L_0x008d:
        throw r0;
    L_0x008e:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0088;
    L_0x0093:
        r0 = move-exception;
        goto L_0x0083;
    L_0x0095:
        r0 = move-exception;
        r3 = r4;
        goto L_0x0083;
    L_0x0098:
        r1 = move-exception;
        goto L_0x006e;
    L_0x009a:
        r1 = move-exception;
        r3 = r4;
        goto L_0x006e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.util.a.a(java.lang.String, java.lang.String):boolean");
    }

    public static boolean f() {
        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI") && Build.MODEL.equalsIgnoreCase("NEM-L22")) {
            return true;
        }
        return false;
    }

    private static void a(String str, MediaFormat mediaFormat, byte[] bArr, int i, int i2) {
        ByteBuffer allocate = ByteBuffer.allocate(i2 - i);
        allocate.put(bArr, i, i2 - i);
        allocate.position(0);
        mediaFormat.setByteBuffer(str, allocate);
    }

    @TargetApi(16)
    public static MediaFormat a(byte[] bArr, int i, int i2) {
        MediaFormat createVideoFormat = MediaFormat.createVideoFormat("video/avc", i, i2);
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        Object obj = null;
        Object obj2 = null;
        while (i3 + 3 < bArr.length) {
            int i6;
            if (bArr[i3] == (byte) 0 && bArr[i3 + 1] == (byte) 0 && bArr[i3 + 2] == (byte) 1) {
                i6 = 3;
            } else {
                i6 = 0;
            }
            if (bArr[i3] == (byte) 0 && bArr[i3 + 1] == (byte) 0 && bArr[i3 + 2] == (byte) 0 && bArr[i3 + 3] == (byte) 1) {
                i6 = 4;
            }
            if (i6 > 0) {
                if (i5 == 0) {
                    i3 += i6;
                    i5 = i6;
                } else {
                    i4 = bArr[i5] & 31;
                    if (i4 == 7) {
                        a("csd-0", createVideoFormat, bArr, i5, i3);
                        obj2 = 1;
                    } else if (i4 == 8) {
                        a("csd-1", createVideoFormat, bArr, i5, i3);
                        int obj3 = 1;
                    }
                    i4 = i3 + i6;
                    if (obj2 != null && obj3 != null) {
                        return createVideoFormat;
                    }
                    i5 = i4;
                    int i7 = i3;
                    i3 = i4;
                    i4 = i7;
                }
            }
            i3++;
        }
        i3 = bArr[i5] & 31;
        if (obj2 != null && i3 == 8) {
            a("csd-1", createVideoFormat, bArr, i5, i4);
            return createVideoFormat;
        } else if (obj3 == null || i3 != 7) {
            return null;
        } else {
            a("csd-0", createVideoFormat, bArr, i5, i4);
            return createVideoFormat;
        }
    }
}
