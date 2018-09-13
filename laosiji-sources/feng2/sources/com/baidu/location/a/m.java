package com.baidu.location.a;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import com.baidu.location.Jni;
import com.baidu.location.f.b;
import com.baidu.location.f.c;
import com.baidu.location.h.f;
import com.baidu.location.h.j;
import com.baidu.location.h.k;
import com.facebook.common.util.UriUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class m extends f {
    private static m p = null;
    String a;
    String b;
    String c;
    String d;
    int e;
    Handler f;

    private m() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = 1;
        this.f = null;
        this.f = new Handler();
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002e  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x002e  */
    public static void a(java.io.File r5, java.io.File r6) throws java.io.IOException {
        /*
        r2 = 0;
        r3 = new java.io.BufferedInputStream;	 Catch:{ all -> 0x0043 }
        r0 = new java.io.FileInputStream;	 Catch:{ all -> 0x0043 }
        r0.<init>(r5);	 Catch:{ all -> 0x0043 }
        r3.<init>(r0);	 Catch:{ all -> 0x0043 }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ all -> 0x0046 }
        r0 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0046 }
        r0.<init>(r6);	 Catch:{ all -> 0x0046 }
        r1.<init>(r0);	 Catch:{ all -> 0x0046 }
        r0 = 5120; // 0x1400 float:7.175E-42 double:2.5296E-320;
        r0 = new byte[r0];	 Catch:{ all -> 0x0025 }
    L_0x0019:
        r2 = r3.read(r0);	 Catch:{ all -> 0x0025 }
        r4 = -1;
        if (r2 == r4) goto L_0x0032;
    L_0x0020:
        r4 = 0;
        r1.write(r0, r4, r2);	 Catch:{ all -> 0x0025 }
        goto L_0x0019;
    L_0x0025:
        r0 = move-exception;
        r2 = r3;
    L_0x0027:
        if (r2 == 0) goto L_0x002c;
    L_0x0029:
        r2.close();
    L_0x002c:
        if (r1 == 0) goto L_0x0031;
    L_0x002e:
        r1.close();
    L_0x0031:
        throw r0;
    L_0x0032:
        r1.flush();	 Catch:{ all -> 0x0025 }
        r5.delete();	 Catch:{ all -> 0x0025 }
        if (r3 == 0) goto L_0x003d;
    L_0x003a:
        r3.close();
    L_0x003d:
        if (r1 == 0) goto L_0x0042;
    L_0x003f:
        r1.close();
    L_0x0042:
        return;
    L_0x0043:
        r0 = move-exception;
        r1 = r2;
        goto L_0x0027;
    L_0x0046:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.m.a(java.io.File, java.io.File):void");
    }

    private boolean a(Context context) {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.getType() == 0) {
                String a = c.a(b.a().e());
                if (a.equals("3G") || a.equals("4G")) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public static boolean a(String str, String str2) {
        File file = new File(k.h() + File.separator + "tmp");
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] bArr = new byte[4096];
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
            while (true) {
                int read = bufferedInputStream.read(bArr);
                if (read <= 0) {
                    break;
                }
                fileOutputStream.write(bArr, 0, read);
            }
            httpURLConnection.disconnect();
            fileOutputStream.close();
            bufferedInputStream.close();
            if (file.length() < 10240) {
                file.delete();
                return false;
            }
            file.renameTo(new File(k.h() + File.separator + str2));
            return true;
        } catch (Exception e) {
            file.delete();
            return false;
        }
    }

    public static m b() {
        if (p == null) {
            p = new m();
        }
        return p;
    }

    private Handler f() {
        return this.f;
    }

    private void g() {
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(k.h() + "/grtcfrsa.dat");
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(2);
                    randomAccessFile.writeInt(0);
                    randomAccessFile.seek(8);
                    byte[] bytes = "1980_01_01:0".getBytes();
                    randomAccessFile.writeInt(bytes.length);
                    randomAccessFile.write(bytes);
                    randomAccessFile.seek(200);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.seek(800);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(200);
            randomAccessFile.writeBoolean(true);
            if (this.e == 1) {
                randomAccessFile.writeBoolean(true);
            } else {
                randomAccessFile.writeBoolean(false);
            }
            if (this.d != null) {
                byte[] bytes2 = this.d.getBytes();
                randomAccessFile.writeInt(bytes2.length);
                randomAccessFile.write(bytes2);
            } else if (Math.abs(com.baidu.location.f.getFrameVersion() - 7.21f) < 1.0E-8f) {
                randomAccessFile.writeInt(0);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private void h() {
        if (this.a != null) {
            new q(this).start();
        }
    }

    private boolean i() {
        return (this.c == null || new File(k.h() + File.separator + this.c).exists()) ? true : a("http://" + this.a + "/" + this.c, this.c);
    }

    private void j() {
        if (this.b != null) {
            File file = new File(k.h() + File.separator + this.b);
            if (!file.exists() && a("http://" + this.a + "/" + this.b, this.b)) {
                String a = k.a(file, "SHA-256");
                if (this.d != null && a != null && k.b(a, this.d, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiP7BS5IjEOzrKGR9/Ww9oSDhdX1ir26VOsYjT1T6tk2XumRpkHRwZbrucDcNnvSB4QsqiEJnvTSRi7YMbh2H9sLMkcvHlMV5jAErNvnuskWfcvf7T2mq7EUZI/Hf4oVZhHV0hQJRFVdTcjWI6q2uaaKM3VMh+roDesiE7CR2biQIDAQAB")) {
                    File file2 = new File(k.h() + File.separator + com.baidu.location.f.replaceFileName);
                    if (file2.exists()) {
                        file2.delete();
                    }
                    try {
                        a(file, file2);
                    } catch (Exception e) {
                        file2.delete();
                    }
                }
            }
        }
    }

    @SuppressLint({"NewApi"})
    public void a() {
        int i = 0;
        StringBuffer stringBuffer = new StringBuffer(128);
        stringBuffer.append("&sdk=");
        stringBuffer.append(7.21f);
        stringBuffer.append("&fw=");
        stringBuffer.append(com.baidu.location.f.getFrameVersion());
        stringBuffer.append("&suit=");
        stringBuffer.append(2);
        if (com.baidu.location.h.b.a().b == null) {
            stringBuffer.append("&im=");
            stringBuffer.append(com.baidu.location.h.b.a().a);
        } else {
            stringBuffer.append("&cu=");
            stringBuffer.append(com.baidu.location.h.b.a().b);
        }
        stringBuffer.append("&mb=");
        stringBuffer.append(Build.MODEL);
        stringBuffer.append("&sv=");
        String str = VERSION.RELEASE;
        if (str != null && str.length() > 10) {
            str = str.substring(0, 10);
        }
        stringBuffer.append(str);
        try {
            if (VERSION.SDK_INT > 20) {
                String[] strArr = Build.SUPPORTED_ABIS;
                str = null;
                while (i < strArr.length) {
                    str = i == 0 ? strArr[i] + ";" : str + strArr[i] + ";";
                    i++;
                }
            } else {
                str = Build.CPU_ABI2;
            }
        } catch (Error e) {
            str = null;
        } catch (Exception e2) {
            str = null;
        }
        if (str != null) {
            stringBuffer.append("&cpuabi=");
            stringBuffer.append(str);
        }
        stringBuffer.append("&pack=");
        stringBuffer.append(com.baidu.location.h.b.d);
        this.h = k.d() + "?&it=" + Jni.en1(stringBuffer.toString());
    }

    public void a(boolean z) {
        if (z) {
            try {
                JSONObject jSONObject = new JSONObject(this.j);
                if ("up".equals(jSONObject.getString(UriUtil.LOCAL_RESOURCE_SCHEME))) {
                    this.a = jSONObject.getString("upath");
                    if (jSONObject.has("u1")) {
                        this.b = jSONObject.getString("u1");
                    }
                    if (jSONObject.has("u2")) {
                        this.c = jSONObject.getString("u2");
                    }
                    if (jSONObject.has("u1_rsa")) {
                        this.d = jSONObject.getString("u1_rsa");
                    }
                    f().post(new p(this));
                }
                if (jSONObject.has("ison")) {
                    this.e = jSONObject.getInt("ison");
                }
                g();
            } catch (Exception e) {
            }
        }
        com.baidu.location.h.c.a().a(System.currentTimeMillis());
    }

    public void c() {
        if (System.currentTimeMillis() - com.baidu.location.h.c.a().b() > 86400000) {
            f().postDelayed(new n(this), 10000);
            f().postDelayed(new o(this), 5000);
        }
    }
}
