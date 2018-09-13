package com.tencent.liteav.basic.d;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.taobao.accs.ErrorCode;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.umeng.message.util.HttpRequest;
import com.umeng.socialize.b.b.c;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: TXCConfigCenter */
public class b {
    protected static final String a = (Environment.getExternalStorageDirectory().getAbsolutePath() + "/txrtmp/");
    protected static b f;
    private static Context g = null;
    SharedPreferences b;
    Editor c;
    String d = "";
    long e = 0;
    private boolean h = false;
    private boolean i = false;
    private a j = new a();

    /* compiled from: TXCConfigCenter */
    private static class a {
        private int a;
        private String b;
        private int c;
        private int d;
        private int e;
        private int[] f;
        private int g;
        private String h;
        private int i;
        private int j;
        private int k;
        private int l;
        private int m;
        private int n;
        private JSONObject o;

        private a() {
            this.a = 2;
            this.b = "";
            this.c = 0;
            this.d = 0;
            this.e = 0;
            this.f = null;
            this.g = 0;
            this.h = "";
            this.i = 60;
            this.j = 70;
            this.k = 80;
            this.l = 50;
            this.m = 10;
            this.n = 0;
            this.o = new JSONObject();
        }

        /* synthetic */ a(AnonymousClass1 anonymousClass1) {
            this();
        }
    }

    public static b a() {
        if (f == null) {
            synchronized (b.class) {
                if (f == null) {
                    f = new b();
                }
            }
        }
        return f;
    }

    public void a(Context context) {
        if (g == null) {
            g = StubApp.getOrigApplicationContext(context.getApplicationContext());
        }
    }

    public int b() {
        h();
        return this.j.a;
    }

    public int c() {
        h();
        return this.j.n;
    }

    public int d() {
        h();
        return this.j.m;
    }

    public boolean a(float f, float f2, float f3) {
        h();
        if (f2 >= ((float) this.j.k) || f3 <= ((float) this.j.l) || (f >= ((float) this.j.i) && f3 <= ((float) this.j.j))) {
            return true;
        }
        return false;
    }

    public int e() {
        int i = 0;
        h();
        if (this.j.f != null) {
            int i2 = this.j.f[0];
            int[] m = this.j.f;
            int length = m.length;
            int i3 = 0;
            i = i2;
            while (i3 < length) {
                i2 = m[i3];
                if (i <= i2) {
                    i2 = i;
                }
                i3++;
                i = i2;
            }
        }
        return i;
    }

    public int f() {
        int i = 0;
        h();
        if (this.j.f != null) {
            int i2 = this.j.f[0];
            int[] m = this.j.f;
            int length = m.length;
            int i3 = 0;
            i = i2;
            while (i3 < length) {
                i2 = m[i3];
                if (i >= i2) {
                    i2 = i;
                }
                i3++;
                i = i2;
            }
        }
        return i;
    }

    public long a(String str, String str2) {
        h();
        long a = a(TXCCommonUtil.getAppID(), str, str2);
        if (a == -1) {
            return a("Global", str, str2);
        }
        return a;
    }

    public float b(String str, String str2) {
        h();
        float b = b(TXCCommonUtil.getAppID(), str, str2);
        if (((double) b) == -1.0d) {
            return b("Global", str, str2);
        }
        return b;
    }

    private long a(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || this.j.o == null) {
            return -1;
        }
        JSONObject optJSONObject = this.j.o.optJSONObject(str);
        if (optJSONObject == null) {
            return -1;
        }
        optJSONObject = optJSONObject.optJSONObject(str2);
        if (optJSONObject != null) {
            return optJSONObject.optLong(str3, -1);
        }
        return -1;
    }

    private float b(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str) || this.j.o == null) {
            return -1.0f;
        }
        JSONObject optJSONObject = this.j.o.optJSONObject(str);
        if (optJSONObject == null) {
            return -1.0f;
        }
        optJSONObject = optJSONObject.optJSONObject(str2);
        if (optJSONObject != null) {
            return (float) optJSONObject.optDouble(str3, -1.0d);
        }
        return -1.0f;
    }

    private void h() {
        i();
        g();
    }

    private synchronized void i() {
        if (k()) {
            b(true);
            if (!m()) {
                n();
            }
            TXCLog.i("CloudConfig", "load config(system aec):" + this.j.d + MiPushClient.ACCEPT_TIME_SEPARATOR + this.j.e + MiPushClient.ACCEPT_TIME_SEPARATOR + this.j.g + MiPushClient.ACCEPT_TIME_SEPARATOR + this.j.h + ", model = " + Build.MODEL + ", manufacturer = " + Build.MANUFACTURER + "， board = " + Build.BOARD);
        }
    }

    private void a(a aVar) {
        if (this.c == null && g != null) {
            SharedPreferences sharedPreferences = g.getSharedPreferences("cloud_config", 0);
            if (sharedPreferences != null) {
                this.c = sharedPreferences.edit();
            }
        }
        if (this.c != null) {
            this.c.putLong("expired_time", this.e);
            this.c.putInt("hw_config", aVar.a);
            this.c.putInt("ExposureCompensation", aVar.c);
            this.c.putInt("UGCSWMuxerConfig", aVar.n);
            this.c.putInt("CPU", aVar.i);
            this.c.putInt("FPS", aVar.j);
            this.c.putInt("CPU_MAX", aVar.k);
            this.c.putInt("FPS_MIN", aVar.l);
            this.c.putInt("CheckCount", aVar.m);
            this.c.putString("trae_config", aVar.b);
            if (aVar.o != null) {
                this.c.putString("AppIDConfig", aVar.o.toString());
            }
            String str = PushConstants.PUSH_TYPE_NOTIFY;
            if (aVar.f != null) {
                str = "" + aVar.f[0];
                for (int i = 1; i < aVar.f.length; i++) {
                    str = (str + "|") + aVar.f[i];
                }
            }
            if (aVar.h == null) {
                aVar.h = "";
            }
            this.c.putString("system_aec_config", aVar.d + MiPushClient.ACCEPT_TIME_SEPARATOR + aVar.e + MiPushClient.ACCEPT_TIME_SEPARATOR + str + MiPushClient.ACCEPT_TIME_SEPARATOR + aVar.g + MiPushClient.ACCEPT_TIME_SEPARATOR + aVar.h);
            this.c.commit();
        }
    }

    public void g() {
        if (j()) {
            a(true);
            new Thread() {
                public void run() {
                    b.this.l();
                }
            }.start();
        }
    }

    private synchronized boolean j() {
        boolean z;
        if (this.h || this.e > System.currentTimeMillis()) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private synchronized boolean k() {
        boolean z;
        if (this.i) {
            z = false;
        } else {
            z = true;
        }
        return z;
    }

    private synchronized void a(boolean z) {
        this.h = z;
    }

    private synchronized void b(boolean z) {
        this.i = z;
    }

    private synchronized void a(int i) {
        if (i < 1) {
            i = 1;
        }
        this.e = System.currentTimeMillis() + ((long) ((((i * 24) * 60) * 60) * 1000));
    }

    private void l() {
        try {
            TXCLog.w("CloudConfig", "update server config ");
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL("http://dldir1.qq.com/hudongzhibo/liteavsvrcfg/serverconfig_en.zip").openConnection();
            if (!this.d.isEmpty()) {
                httpURLConnection.addRequestProperty("If-Modified-Since", this.d);
            }
            String headerField = httpURLConnection.getHeaderField(HttpRequest.HEADER_LAST_MODIFIED);
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == 200) {
                if (!(headerField == null || headerField.isEmpty())) {
                    this.d = headerField;
                    if (this.c != null) {
                        this.c.putString("last_modify", this.d);
                    }
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bArr = new byte[1024];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                while (true) {
                    int read = inputStream.read(bArr);
                    if (read != -1) {
                        byteArrayOutputStream.write(bArr, 0, read);
                    } else {
                        File file;
                        byte[] a = a.a(byteArrayOutputStream.toByteArray(), a.a(Base64.decode("MIICeQIBADANBgkqhkiG9w0BAQEFAASCAmMwggJfAgEAAoGBAOnmX5h7KCQsoIQ+2ot1dIayWsmA3LU7p0kl1t5T2cbosedcsSGT+YM5bFiVBeAYbAM10WSvzZ2+oexMW7B2RcYZ1qulSR4eNXk74biDy2DmQqXK3qt1ZP4DnpiR+UXVKt6rqdtpDqRk4VGUw33/w3mMOyzkSjueewYB32n/l2JPAgMBAAECgYEA5rzfcyGTQNRRaQREPa0ZzcLmcr/Pem2lojBU3jBjtqhYz/8Nsi0yyHP+YQhpql8NNsGBlk0jjsi/HcdZ8CNMwbRfPYoe9mICe/iKMJ5P3+DtcH7AtE0ckHg01rY8pbqV9EAICijU1BwgbZh9M715HLSCeKwSWBWmpq1aQ/8l7PkCQQD5GFqrmGtMJOfTxaqS5hCHg+VsYpPsb566DEZQIJBWMP7eE58H1rphWMMSQ36c1V/iZuauYO0gYC1UlMfYHsRVAkEA8GIwlFXPG+LnkPENHo2pKORCnY7wo63hjyeQRipHhY7yUJjaPA50wDI7XCGOrJryBCVTOVszEUz4ocHQ0mOQEwJBAOnCPySVTuwQHjaQYzikCpMB5gVGpUbWoQA7kKiVRp58MFG73BwBGLtODxJOoL0RSIAwzP6MGzusxh1/2eMpTFkCQQCk5tboi0z+llPArHwRf6CRurSwHUSbJEddywg/+fUCfCNigtkC5e/VgSATfbnAUrK/gVNsP1HzBlhxruGv0jkdAkEAjNSVhjcoLg1JodbhBmD16vsAUzJpDR6EZIeiXj4pN+hKiDq9+aHEtMxtjFXiqbdKkrUjrzfZKrzQm0wy950BUw==".getBytes("UTF-8"), 2)));
                        synchronized (b.class) {
                            file = new File(a, "serverconfig_dec.zip");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            fileOutputStream.write(a);
                            fileOutputStream.close();
                        }
                        a(a(file));
                        return;
                    }
                }
            } else if (responseCode == ErrorCode.DM_PACKAGENAME_INVALID) {
                a(false);
                TXCLog.d("CloudConfig", new StringBuilder().append("fetch config Not-Modified-Since ").append(this.d).toString() == null ? "" : this.d);
            }
        } catch (Exception e) {
            TXCLog.e("CloudConfig", "fetch config catch exception " + e);
            a(false);
        }
    }

    private static String a(File file) throws IOException {
        synchronized (b.class) {
            ZipFile zipFile = new ZipFile(file);
            Enumeration entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                if (!zipEntry.isDirectory()) {
                    InputStream inputStream = zipFile.getInputStream(zipEntry);
                    byte[] bArr = new byte[c.a];
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read > 0) {
                            byteArrayOutputStream.write(bArr, 0, read);
                        } else {
                            inputStream.close();
                            String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                            return byteArrayOutputStream2;
                        }
                    }
                }
            }
            return "";
        }
    }

    private void a(String str) {
        a(false);
        try {
            JSONObject jSONObject = new JSONObject(str);
            int i = 1;
            if (jSONObject.has("UpdateFrequency")) {
                i = jSONObject.getInt("UpdateFrequency");
            }
            a(i);
            a a = a(jSONObject);
            if (a != null) {
                this.j = a;
                a(a);
            }
        } catch (JSONException e) {
            TXCLog.w("CloudConfig", "parseRespon catch ecxeption" + e);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:65:0x0126 A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0114  */
    private void a(org.json.JSONObject r14, com.tencent.liteav.basic.d.b.a r15) throws org.json.JSONException {
        /*
        r13 = this;
        r5 = 1;
        r3 = 0;
        r2 = 0;
        r15.d = r2;
        r15.e = r2;
        r15.f = r3;
        r15.g = r2;
        r0 = "";
        r15.h = r0;
        r0 = "SystemAECConfig";
        r0 = r14.has(r0);
        if (r0 == 0) goto L_0x014f;
    L_0x001e:
        r0 = "SystemAECConfig";
        r0 = r14.getJSONObject(r0);
        r1 = "InfoList";
        r1 = r0.has(r1);
        if (r1 == 0) goto L_0x014f;
    L_0x002e:
        r1 = "InfoList";
        r7 = r0.getJSONArray(r1);
        r1 = r2;
        r0 = r3;
    L_0x0037:
        r4 = r7.length();
        if (r1 >= r4) goto L_0x014e;
    L_0x003d:
        r8 = r7.getJSONObject(r1);
        if (r8 == 0) goto L_0x0126;
    L_0x0043:
        r4 = "Manufacture";
        r4 = r8.getString(r4);
        r6 = android.os.Build.MANUFACTURER;
        r4 = r4.equalsIgnoreCase(r6);
        if (r4 == 0) goto L_0x0126;
    L_0x0052:
        r4 = "WhiteList";
        r6 = r8.optJSONArray(r4);
        if (r6 == 0) goto L_0x01d6;
    L_0x005b:
        r4 = r2;
    L_0x005c:
        r9 = r6.length();
        if (r4 >= r9) goto L_0x01d6;
    L_0x0062:
        r9 = r6.optJSONObject(r4);
        if (r9 == 0) goto L_0x00f2;
    L_0x0068:
        r10 = "Model";
        r9 = r9.optString(r10);
        if (r9 == 0) goto L_0x00f2;
    L_0x0071:
        r10 = android.os.Build.MODEL;
        r9 = r10.equals(r9);
        if (r9 == 0) goto L_0x00f2;
    L_0x0079:
        r4 = r5;
    L_0x007a:
        r6 = "BlackList";
        r9 = r8.optJSONArray(r6);
        if (r9 == 0) goto L_0x01d3;
    L_0x0083:
        r6 = r2;
    L_0x0084:
        r10 = r9.length();
        if (r6 >= r10) goto L_0x01d3;
    L_0x008a:
        r10 = r9.optJSONObject(r6);
        if (r10 == 0) goto L_0x00f6;
    L_0x0090:
        r11 = "Model";
        r10 = r10.optString(r11);
        if (r10 == 0) goto L_0x00f6;
    L_0x0099:
        r11 = android.os.Build.MODEL;
        r10 = r11.equals(r10);
        if (r10 == 0) goto L_0x00f6;
    L_0x00a1:
        r6 = r5;
    L_0x00a2:
        if (r4 == 0) goto L_0x0112;
    L_0x00a4:
        r4 = "SystemAEC";
        r9 = 0;
        r4 = r8.optInt(r4, r9);	 Catch:{ Exception -> 0x01cd }
        r15.d = r4;	 Catch:{ Exception -> 0x01cd }
        r4 = "AGC";
        r9 = 0;
        r4 = r8.optInt(r4, r9);	 Catch:{ Exception -> 0x01cd }
        r15.e = r4;	 Catch:{ Exception -> 0x01cd }
        r4 = "SampleRate";
        r9 = "";
        r4 = r8.optString(r4, r9);	 Catch:{ Exception -> 0x01cd }
        r0 = r4.isEmpty();	 Catch:{ Exception -> 0x012a }
        if (r0 != 0) goto L_0x00f9;
    L_0x00ca:
        r0 = "\\|";
        r9 = r4.split(r0);	 Catch:{ Exception -> 0x012a }
        r0 = r9.length;	 Catch:{ Exception -> 0x012a }
        r0 = new int[r0];	 Catch:{ Exception -> 0x012a }
        r15.f = r0;	 Catch:{ Exception -> 0x012a }
        r0 = r2;
    L_0x00d8:
        r10 = r9.length;	 Catch:{ Exception -> 0x012a }
        if (r0 >= r10) goto L_0x00f9;
    L_0x00db:
        r10 = r15.f;	 Catch:{ Exception -> 0x012a }
        r11 = r9[r0];	 Catch:{ Exception -> 0x012a }
        r11 = r11.trim();	 Catch:{ Exception -> 0x012a }
        r11 = java.lang.Integer.valueOf(r11);	 Catch:{ Exception -> 0x012a }
        r11 = r11.intValue();	 Catch:{ Exception -> 0x012a }
        r10[r0] = r11;	 Catch:{ Exception -> 0x012a }
        r0 = r0 + 1;
        goto L_0x00d8;
    L_0x00f2:
        r4 = r4 + 1;
        goto L_0x005c;
    L_0x00f6:
        r6 = r6 + 1;
        goto L_0x0084;
    L_0x00f9:
        r0 = "HWAACCodec";
        r9 = 0;
        r0 = r8.optInt(r0, r9);	 Catch:{ Exception -> 0x012a }
        r15.g = r0;	 Catch:{ Exception -> 0x012a }
        r0 = "SceneType";
        r9 = "";
        r0 = r8.optString(r0, r9);	 Catch:{ Exception -> 0x012a }
        r15.h = r0;	 Catch:{ Exception -> 0x012a }
        r0 = r4;
    L_0x0112:
        if (r6 == 0) goto L_0x0126;
    L_0x0114:
        r15.d = r2;
        r15.e = r2;
        r15.f = r3;
        r15.g = r2;
        r4 = "";
        r15.h = r4;
    L_0x0126:
        r1 = r1 + 1;
        goto L_0x0037;
    L_0x012a:
        r0 = move-exception;
    L_0x012b:
        r0.printStackTrace();
        r8 = "CloudConfig";
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "parseSysAECConfig Exception: ";
        r9 = r9.append(r10);
        r0 = r0.getMessage();
        r0 = r9.append(r0);
        r0 = r0.toString();
        com.tencent.liteav.basic.log.TXCLog.e(r8, r0);
        r0 = r4;
        goto L_0x0112;
    L_0x014e:
        r3 = r0;
    L_0x014f:
        r0 = "CloudConfig";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "system aec config1:";
        r1 = r1.append(r2);
        r2 = r15.d;
        r1 = r1.append(r2);
        r2 = ",";
        r1 = r1.append(r2);
        r2 = r15.e;
        r1 = r1.append(r2);
        r2 = ",";
        r1 = r1.append(r2);
        r1 = r1.append(r3);
        r2 = ",";
        r1 = r1.append(r2);
        r2 = r15.g;
        r1 = r1.append(r2);
        r2 = ",";
        r1 = r1.append(r2);
        r2 = r15.h;
        r1 = r1.append(r2);
        r2 = ", model = ";
        r1 = r1.append(r2);
        r2 = android.os.Build.MODEL;
        r1 = r1.append(r2);
        r2 = ", manufacturer = ";
        r1 = r1.append(r2);
        r2 = android.os.Build.MANUFACTURER;
        r1 = r1.append(r2);
        r2 = "， board = ";
        r1 = r1.append(r2);
        r2 = android.os.Build.BOARD;
        r1 = r1.append(r2);
        r1 = r1.toString();
        com.tencent.liteav.basic.log.TXCLog.i(r0, r1);
        return;
    L_0x01cd:
        r4 = move-exception;
        r12 = r4;
        r4 = r0;
        r0 = r12;
        goto L_0x012b;
    L_0x01d3:
        r6 = r2;
        goto L_0x00a2;
    L_0x01d6:
        r4 = r2;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.basic.d.b.a(org.json.JSONObject, com.tencent.liteav.basic.d.b$a):void");
    }

    private void b(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("TraeConfig")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("TraeConfig");
            if (jSONObject2.has("InfoList")) {
                JSONArray jSONArray = jSONObject2.getJSONArray("InfoList");
                int i = 0;
                while (i < jSONArray.length()) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    if (jSONObject3 == null || !jSONObject3.getString("MachineType").equals(Build.MODEL)) {
                        i++;
                    } else {
                        aVar.b = jSONObject3.getString("ConfigValue");
                        TXCLog.d("CloudConfig", "parseTRAEConfig get TRAE config: " + aVar.b);
                        return;
                    }
                }
            }
        }
    }

    private void c(JSONObject jSONObject, a aVar) throws JSONException {
        aVar.i = 60;
        aVar.j = 70;
        aVar.k = 80;
        aVar.l = 50;
        aVar.m = 10;
        if (jSONObject.has("HWWhiteList")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("HWWhiteList");
            if (jSONObject2.has("SWToHWThreshold")) {
                jSONObject2 = jSONObject2.getJSONObject("SWToHWThreshold");
                if (jSONObject2.has("CPU")) {
                    aVar.i = jSONObject2.getInt("CPU");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + aVar.i);
                }
                if (jSONObject2.has("FPS")) {
                    aVar.j = jSONObject2.getInt("FPS");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + aVar.j);
                }
                if (jSONObject2.has("CPU_MAX")) {
                    aVar.k = jSONObject2.getInt("CPU_MAX");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CPU:" + aVar.k);
                }
                if (jSONObject2.has("FPS_MIN")) {
                    aVar.l = jSONObject2.getInt("FPS_MIN");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.FPS:" + aVar.l);
                }
                if (jSONObject2.has("CheckCount")) {
                    aVar.m = jSONObject2.getInt("CheckCount");
                    TXCLog.d("CloudConfig", "parseAutoSWHWConfig get SWToHWThreshold.CheckCount:" + aVar.m);
                }
            }
        }
    }

    private void d(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("ExposureWhiteConfig")) {
            JSONArray jSONArray = jSONObject.getJSONObject("ExposureWhiteConfig").getJSONArray("InfoList");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                if (jSONObject2.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) && jSONObject2.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                    aVar.c = jSONObject2.getInt("ExposureCompensation");
                    TXCLog.d("CloudConfig", "parseExposureConfig get exposure config: " + aVar.c);
                    break;
                }
            }
            if (g != null) {
                g.sendBroadcast(new Intent("com.tencent.liteav.basic.serverconfig.get"));
            }
        }
    }

    private void e(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("HWBlackConfig")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("HWBlackConfig");
            int i = 16;
            if (jSONObject2.has("HWMiniSupportAPI")) {
                i = jSONObject2.getInt("HWMiniSupportAPI");
            }
            if (VERSION.SDK_INT < i) {
                aVar.a = 0;
                return;
            }
            JSONArray jSONArray = jSONObject2.getJSONArray("InfoList");
            i = 0;
            while (i < jSONArray.length()) {
                JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                if (!jSONObject3.getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER) || !jSONObject3.getString("Model").equalsIgnoreCase(Build.MODEL)) {
                    i++;
                } else if (jSONObject3.has("profile")) {
                    aVar.a = 1;
                    TXCLog.d("CloudConfig", "parseHWBlackConfig get HWBlack config: " + aVar.a);
                    return;
                } else {
                    aVar.a = 0;
                    TXCLog.d("CloudConfig", "parseHWBlackConfig get HWBlack config: " + aVar.a);
                    return;
                }
            }
        }
    }

    private void f(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("UGCSWMuxerConfig")) {
            JSONArray jSONArray = jSONObject.getJSONObject("UGCSWMuxerConfig").getJSONArray("InfoList");
            for (int i = 0; i < jSONArray.length(); i++) {
                if (jSONArray.getJSONObject(i).getString("Manufacture").equalsIgnoreCase(Build.MANUFACTURER)) {
                    aVar.n = 1;
                    return;
                }
            }
        }
    }

    private void g(JSONObject jSONObject, a aVar) throws JSONException {
        if (jSONObject.has("AppIDConfig")) {
            JSONObject optJSONObject = jSONObject.optJSONObject("AppIDConfig");
            if (optJSONObject != null) {
                aVar.o = optJSONObject;
                TXCLog.w("CloudConfig", "parse global config : " + aVar.o);
            }
        }
    }

    private boolean m() {
        if (g == null) {
            return false;
        }
        this.b = g.getSharedPreferences("cloud_config", 0);
        if (this.b == null || !this.b.contains("expired_time")) {
            return false;
        }
        TXCLog.w("CloudConfig", "loadLocalFileConfig ");
        this.c = this.b.edit();
        try {
            this.d = this.b.getString("last_modify", "");
            this.e = this.b.getLong("expired_time", System.currentTimeMillis());
            this.j.a = this.b.getInt("hw_config", 2);
            this.j.c = this.b.getInt("ExposureCompensation", 0);
            this.j.n = this.b.getInt("UGCSWMuxerConfig", 0);
            this.j.i = this.b.getInt("CPU", 60);
            this.j.j = this.b.getInt("FPS", 70);
            this.j.k = this.b.getInt("CPU_MAX", 80);
            this.j.l = this.b.getInt("FPS_MIN", 50);
            this.j.m = this.b.getInt("CheckCount", 10);
            this.j.b = this.b.getString("trae_config", "");
            String string = this.b.getString("system_aec_config", "");
            Object string2 = this.b.getString("AppIDConfig", "");
            if (!TextUtils.isEmpty(string2)) {
                this.j.o = new JSONObject(string2);
            }
            TXCLog.i("CloudConfig", "system aec config:" + string);
            if (!string.isEmpty()) {
                String[] split = string.split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                if (split != null && split.length >= 5) {
                    this.j.d = Integer.valueOf(split[0]).intValue();
                    this.j.e = Integer.valueOf(split[1]).intValue();
                    String[] split2 = split[2].split("\\|");
                    if (split2 != null) {
                        this.j.f = new int[split2.length];
                        for (int i = 0; i < split2.length; i++) {
                            this.j.f[i] = Integer.valueOf(split2[i].trim()).intValue();
                        }
                    }
                    this.j.g = Integer.valueOf(split[3]).intValue();
                    this.j.h = split[4];
                }
            }
            return true;
        } catch (Exception e) {
            this.c.clear();
            this.c.commit();
            TXCLog.d("CloudConfig", "loadLocalFileConfig catch exception " + e);
            return false;
        }
    }

    private void n() {
        try {
            TXCLog.w("CloudConfig", "loadDefaultConfig ");
            a a = a(new JSONObject("{\n    \"version\": 3,\n    \"UpdateFrequency\": 1,\n    \"AppIDConfig\": {\n        \"Global\": {\n           \"DataReport\": {\n               \"UserPortrait\": 0\n           },\n           \"Audio\": {\n               \"RTC_JitterCycle\" : 240,\n               \"LIVE_JitterCycle\": 500,\n               \"LoadingThreshold\": 800,\n               \"SmoothModeAdjust\": 300,\n               \"SmoothSpeed\": 1.2\n           },\n           \"Network\": {\n               \"EnableRouteOptimize\": 0,\n               \"RouteSamplingMaxCount\": 0,\n               \"QualityDataCacheCount\": 0,\n               \"AccRetryCountWithoutSecret\": 0\n           }\n       }\n    },\n    \"PlayModeConfig\": {\n        \"SmoothModeOffset\": 0\n    },\n    \"UGCSWMuxerConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\"\n            }\n        ]\n    },\n    \"HWBlackConfig\": {\n        \"HWMiniSupportAPI\": 17,\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"P6-U06\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"SCH-I939(S3)\"\n            },\n            {\n                \"Manufacture\": \"VIVO\",\n                \"Model\": \"vivo X5Pro D\"\n            },\n            {\n                \"Manufacture\": \"金立\",\n                \"Model\": \"GN9006\"\n            },\n            {\n                \"Manufacture\": \"Samsung\",\n                \"Model\": \"A7000\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI NXT-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"HUAWEI MHA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL00\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"EVA-AL10\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L29\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-L00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"Model\": \"MHA-TL00\",\n\t\t\t\t\"Profile\": \"baseline\"\n            },\n             {\n                 \"Manufacture\": \"HUAWEI\",\n                 \"Model\": \"HUAWEI GRA-UL00\"\n             }\n        ]\n    },\n    \"ExposureWhiteConfig\": {\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"Meizu\",\n                \"Model\": \"MX4 Pro\",\n                \"ExposureCompensation\": 1\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3\",\n                \"ExposureCompensation\": 30\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"Model\": \"MI 3C\",\n                \"ExposureCompensation\": 30\n            }\n        ]\n    },\n    \"SystemAECConfig\": {\n        \"__comment__\":\"SceneType(开启系统aec的场景，1为连麦，2为通话)\",\n        \"InfoList\": [\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9\"},{\"Model\":\"vivo Y67A\"}],\n                \"BlackList\": [],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"vivo\",\n                \"WhiteList\": [{\"Model\":\"vivo X9Plus\"},{\"Model\":\"vivo X7Plus\"},{\"Model\":\"vivo X7\"}],\n                \"BlackList\": [{\"Model\":\"vivo Y51A\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"HUAWEI\",\n                \"WhiteList\": [{\"Model\":\"VTR-TL00\"},{\"Model\":\"HUAWEI GRA-UL00\"},{\"Model\":\"HUAWEI NXT-AL10\"},{\"Model\":\"PLK-AL10\"},{\"Model\":\"PLK-UL00\"},{\"Model\":\"EVA-AL10\"},{\"Model\":\"HUAWEI MT7-TL10\"}],\n                \"BlackList\": [{\"Model\":\"MHA-AL00\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 2\"},{\"Model\":\"Redmi Note 4\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"Redmi Note 3\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Xiaomi\",\n                \"WhiteList\": [{\"Model\":\"MI 4\"},{\"Model\":\"MI 3C\"},{\"Model\":\"Mi-4c\"}],\n                \"BlackList\": [{\"Model\":\"MI 6\"},{\"Model\":\"Redmi 4A\"},{\"Model\":\"MI 5X\"},{\"Model\":\"MI 5\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"Google\",\n                \"WhiteList\": [{\"Model\":\"Pixel XL\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                \"Manufacture\": \"samsung\",\n                \"WhiteList\": [{\"Model\":\"SM-G9350\"},{\"Model\":\"SM-G9500\"},{\"Model\":\"SM-G950U\"}],\n                \"SystemAEC\": 1,\n                \"AGC\": 0,\n                \"SampleRate\": \"48000|16000\",\n                \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n            {\n                 \"Manufacture\": \"LeMobile\",\n                 \"WhiteList\": [{\"Model\":\"X620\"}],\n                 \"SystemAEC\": 1,\n                 \"AGC\": 0,\n                 \"SampleRate\": \"48000|16000\",\n                 \"HWAACCodec\": 1,\n                \"SceneType\": \"1|2\"\n            },\n             {\n                  \"Manufacture\": \"asus\",\n                  \"WhiteList\": [{\"Model\":\"ASUS_Z00ADB\"}],\n                  \"SystemAEC\": 1,\n                  \"AGC\": 0,\n                  \"SampleRate\": \"48000|16000\",\n                  \"HWAACCodec\": 1,\n                 \"SceneType\": \"1|2\"\n             }\n        ]\n    },\n    \"HWWhiteList\": {\n        \"SWToHWThreshold\": {\n            \"CPU_MAX\": 80,\n            \"FPS_MIN\": 50,\n            \"CPU\": 20,\n            \"FPS\": 70,\n            \"CheckCount\": 10,\n            \"__comment__\": \"软编根据性能切硬编的阈值，avgTotalCPU >= CPU_MAX || avgFPS <= FPS_MIN || (avgAppCPU >= CPU && avgFPS <= FPS) 性能指标满足上述条件则切硬编。CheckCount表示需要做X次有效性能数据采集。\"\n\t\t}\n    },\n    \"TraeConfig\": {\n        \"InfoList\": [\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-AL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-CL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-DL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"EVA-TL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-CL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-TL00\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI MT7-TL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"HUAWEI NXT-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"HUAWEI\",\n            \"MachineType\": \"PLK-AL10\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"JTY\",\n            \"MachineType\": \"KT096H\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LENOVO\",\n            \"MachineType\": \"Lenovo K900\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"MachineType\": \"Nexus 5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"LGE\",\n            \"MachineType\": \"Nexus 5X\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"M5\",\n            \"MachineType\": \"X5 R1\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Meizu\",\n            \"MachineType\": \"MX4 Pro\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"A31\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO A33\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nagc {\\r\\nswitch y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO A59s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R7\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9km\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9m\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\naec {\\r\\nUseHQAEC y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"OPPO\",\n            \"MachineType\": \"OPPO R9tm\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 0\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-G9350\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\nvolume 2.0\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nMkechoRatio 1\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-N9108V\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 12.0\\r\\npreAGCvvolmax 20.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"samsung\",\n            \"MachineType\": \"SM-N9200\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 2\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 21\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"TCL\",\n            \"MachineType\": \"TCL P501M\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\nprep {\\r\\ndrop_mic_ms 300\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 4LTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 4W\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\nvolume 0.25\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5s\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI 5s Plus\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nloopback y\\r\\nloop {\\r\\ngap 4\\r\\nbufnum 2\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"MI NOTE LTE\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\ndev {\\r\\nforcevoip y\\r\\n}\\r\\n}\\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 17\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"Xiaomi\",\n            \"MachineType\": \"Redmi Note 3\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\nhwcodec_new {\\r\\navc_decoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.2\\r\\n}\\r\\n}\\r\\navc_encoder {\\r\\nwhite_list {\\r\\nmin_sdk 19\\r\\nmin_version 1.8.1\\r\\n}\\r\\n}\\r\\n}\\r\\ntrae {\\r\\naec {\\r\\nUseHQAEC n\\r\\n}\\r\\n}\\r\\ntrae {\\r\\ndev {\\r\\ncap {\\r\\nhw_ch_191 2\\r\\nStereoCapLorR 1\\r\\n}\\r\\ncomponent 1\\r\\n}\\r\\n}\\r\\n}\"\n          },\n          {\n            \"Factory\": \"ZTE\",\n            \"MachineType\": \"ZTE N928Dt\",\n            \"ConfigValue\": \"sharp{\\r\\nos android \\r\\ntrae {\\r\\npreAGC {\\r\\npreAGCSwitch y\\r\\npreAGCdy 0\\r\\npreVADkind 1\\r\\npreAGCvvolmin 0.0\\r\\npreAGCvvolfst 15.0\\r\\npreAGCvvolmax 23.0\\r\\n}\\r\\n}\\r\\n}\"\n          }\n        ]\n    }\n}"));
            if (a != null) {
                this.j = a;
            }
        } catch (JSONException e) {
            TXCLog.w("CloudConfig", "loadDefaultConfig catch exception " + e);
            this.j = new a();
        }
    }

    private a a(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        a aVar = new a();
        try {
            e(jSONObject, aVar);
            d(jSONObject, aVar);
            c(jSONObject, aVar);
            a(jSONObject, aVar);
            b(jSONObject, aVar);
            f(jSONObject, aVar);
            g(jSONObject, aVar);
        } catch (JSONException e) {
            TXCLog.w("CloudConfig", "parse config catch exception " + e.toString());
            aVar = null;
        }
        return aVar;
    }
}
