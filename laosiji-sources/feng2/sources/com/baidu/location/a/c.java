package com.baidu.location.a;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import anet.channel.util.HttpConstant;
import com.baidu.location.Jni;
import com.baidu.location.b.d;
import com.baidu.location.h.b;
import com.baidu.location.h.f;
import com.baidu.location.h.j;
import com.baidu.mapapi.UIMsg.d_ResultType;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class c {
    public static String f = "0";
    private static c j = null;
    private a A;
    private boolean B;
    private boolean C;
    private int D;
    private float E;
    private float F;
    private long G;
    private int H;
    private Handler I;
    private byte[] J;
    private byte[] K;
    private int L;
    private List<Byte> M;
    private boolean N;
    long a;
    Location b;
    Location c;
    StringBuilder d;
    long e;
    int g;
    double h;
    double i;
    private int k;
    private double l;
    private String m;
    private int n;
    private int o;
    private int p;
    private int q;
    private double r;
    private double s;
    private double t;
    private int u;
    private int v;
    private int w;
    private int x;
    private int y;
    private long z;

    class a extends f {
        String a;

        public a() {
            this.a = null;
            this.k = new HashMap();
        }

        public void a() {
            this.h = "http://loc.map.baidu.com/cc.php";
            String encode = Jni.encode(this.a);
            this.a = null;
            this.k.put("q", encode);
        }

        public void a(String str) {
            this.a = str;
            e();
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    jSONObject.put("prod", b.d);
                    jSONObject.put("uptime", System.currentTimeMillis());
                    c.this.e(jSONObject.toString());
                } catch (Exception e) {
                }
            }
            if (this.k != null) {
                this.k.clear();
            }
        }
    }

    private c() {
        this.k = 1;
        this.l = 0.699999988079071d;
        this.m = "3G|4G";
        this.n = 1;
        this.o = 307200;
        this.p = 15;
        this.q = 1;
        this.r = 3.5d;
        this.s = 3.0d;
        this.t = 0.5d;
        this.u = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
        this.v = 60;
        this.w = 0;
        this.x = 60;
        this.y = 0;
        this.z = 0;
        this.A = null;
        this.B = false;
        this.C = false;
        this.D = 0;
        this.E = 0.0f;
        this.F = 0.0f;
        this.G = 0;
        this.H = d_ResultType.SHORT_URL;
        this.a = 0;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = 0;
        this.I = null;
        this.J = new byte[4];
        this.K = null;
        this.L = 0;
        this.M = null;
        this.N = false;
        this.g = 0;
        this.h = 116.22345545d;
        this.i = 40.245667323d;
        this.I = new Handler();
    }

    public static c a() {
        if (j == null) {
            j = new c();
        }
        return j;
    }

    private String a(File file, String str) {
        String uuid = UUID.randomUUID().toString();
        String str2 = "--";
        String str3 = "\r\n";
        String str4 = "multipart/form-data";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Charset", "utf-8");
            httpURLConnection.setRequestProperty("connection", "close");
            httpURLConnection.setRequestProperty(HttpConstant.CONTENT_TYPE, str4 + ";boundary=" + uuid);
            if (file != null && file.exists()) {
                OutputStream outputStream = httpURLConnection.getOutputStream();
                DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append(str2);
                stringBuffer.append(uuid);
                stringBuffer.append(str3);
                stringBuffer.append("Content-Disposition: form-data; name=\"location_dat\"; filename=\"" + file.getName() + "\"" + str3);
                stringBuffer.append("Content-Type: application/octet-stream; charset=utf-8" + str3);
                stringBuffer.append(str3);
                dataOutputStream.write(stringBuffer.toString().getBytes());
                InputStream fileInputStream = new FileInputStream(file);
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read == -1) {
                        break;
                    }
                    dataOutputStream.write(bArr, 0, read);
                }
                fileInputStream.close();
                dataOutputStream.write(str3.getBytes());
                dataOutputStream.write((str2 + uuid + str2 + str3).getBytes());
                dataOutputStream.flush();
                dataOutputStream.close();
                int responseCode = httpURLConnection.getResponseCode();
                outputStream.close();
                httpURLConnection.disconnect();
                this.y += 400;
                c(this.y);
                if (responseCode == 200) {
                    return "1";
                }
            }
        } catch (MalformedURLException e) {
        } catch (IOException e2) {
        }
        return "0";
    }

    private boolean a(String str, Context context) {
        boolean z = false;
        try {
            List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
            if (runningAppProcesses != null) {
                for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                    boolean z2;
                    if (runningAppProcessInfo.processName.equals(str)) {
                        int i = runningAppProcessInfo.importance;
                        if (i == 200 || i == 100) {
                            z2 = true;
                            z = z2;
                        }
                    }
                    z2 = z;
                    z = z2;
                }
            }
        } catch (Exception e) {
        }
        return z;
    }

    private byte[] a(int i) {
        return new byte[]{(byte) (i & 255), (byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >> 8), (byte) ((16711680 & i) >> 16), (byte) ((ViewCompat.MEASURED_STATE_MASK & i) >> 24)};
    }

    private byte[] a(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        byte[] bytes = str.getBytes();
        byte nextInt = (byte) new Random().nextInt(255);
        byte nextInt2 = (byte) new Random().nextInt(255);
        byte[] bArr = new byte[(bytes.length + 2)];
        int length = bytes.length;
        int i2 = 0;
        while (i < length) {
            int i3 = i2 + 1;
            bArr[i2] = (byte) (bytes[i] ^ nextInt);
            i++;
            i2 = i3;
        }
        i = i2 + 1;
        bArr[i2] = nextInt;
        i2 = i + 1;
        bArr[i] = nextInt2;
        return bArr;
    }

    private String b(String str) {
        Calendar instance = Calendar.getInstance();
        return String.format(str, new Object[]{Integer.valueOf(instance.get(1)), Integer.valueOf(instance.get(2) + 1), Integer.valueOf(instance.get(5))});
    }

    private void b(int i) {
        byte[] a = a(i);
        for (int i2 = 0; i2 < 4; i2++) {
            this.M.add(Byte.valueOf(a[i2]));
        }
    }

    private void b(Location location) {
        c(location);
        h();
    }

    private void c() {
        if (!this.N) {
            this.N = true;
            d(b.d);
            j();
            d();
        }
    }

    private void c(int i) {
        if (i != 0) {
            try {
                RandomAccessFile randomAccessFile;
                File file = new File(j.a + "/grtcf.dat");
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
                randomAccessFile.seek(8);
                byte[] bytes2 = (b("%d_%02d_%02d") + ":" + i).getBytes();
                randomAccessFile.writeInt(bytes2.length);
                randomAccessFile.write(bytes2);
                randomAccessFile.close();
            } catch (Exception e) {
            }
        }
    }

    private void c(Location location) {
        if (System.currentTimeMillis() - this.a >= ((long) this.H) && location != null) {
            if (location != null && location.hasSpeed() && location.getSpeed() > this.E) {
                this.E = location.getSpeed();
            }
            try {
                if (this.M == null) {
                    this.M = new ArrayList();
                    i();
                    d(location);
                } else {
                    e(location);
                }
            } catch (Exception e) {
            }
            this.L++;
        }
    }

    private void c(String str) {
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("on")) {
                    this.k = jSONObject.getInt("on");
                }
                if (jSONObject.has("bash")) {
                    this.l = jSONObject.getDouble("bash");
                }
                if (jSONObject.has("net")) {
                    this.m = jSONObject.getString("net");
                }
                if (jSONObject.has("tcon")) {
                    this.n = jSONObject.getInt("tcon");
                }
                if (jSONObject.has("tcsh")) {
                    this.o = jSONObject.getInt("tcsh");
                }
                if (jSONObject.has("per")) {
                    this.p = jSONObject.getInt("per");
                }
                if (jSONObject.has("chdron")) {
                    this.q = jSONObject.getInt("chdron");
                }
                if (jSONObject.has("spsh")) {
                    this.r = jSONObject.getDouble("spsh");
                }
                if (jSONObject.has("acsh")) {
                    this.s = jSONObject.getDouble("acsh");
                }
                if (jSONObject.has("stspsh")) {
                    this.t = jSONObject.getDouble("stspsh");
                }
                if (jSONObject.has("drstsh")) {
                    this.u = jSONObject.getInt("drstsh");
                }
                if (jSONObject.has("stper")) {
                    this.v = jSONObject.getInt("stper");
                }
                if (jSONObject.has("nondron")) {
                    this.w = jSONObject.getInt("nondron");
                }
                if (jSONObject.has("nondrper")) {
                    this.x = jSONObject.getInt("nondrper");
                }
                if (jSONObject.has("uptime")) {
                    this.z = jSONObject.getLong("uptime");
                }
                k();
            } catch (JSONException e) {
            }
        }
    }

    private void d() {
        String str = null;
        if (null == null) {
            str = "7.2.1";
        }
        String[] split = str.split("\\.");
        int length = split.length;
        this.J[0] = (byte) 0;
        this.J[1] = (byte) 0;
        this.J[2] = (byte) 0;
        this.J[3] = (byte) 0;
        if (length >= 4) {
            length = 4;
        }
        int i = 0;
        while (i < length) {
            try {
                this.J[i] = (byte) (Integer.valueOf(split[i]).intValue() & 255);
                i++;
            } catch (Exception e) {
            }
        }
        this.K = a(b.d + ":" + b.a().b);
    }

    private void d(Location location) {
        Object obj;
        Object obj2 = null;
        this.e = System.currentTimeMillis();
        b((int) (location.getTime() / 1000));
        b((int) (location.getLongitude() * 1000000.0d));
        b((int) (location.getLatitude() * 1000000.0d));
        if (location.hasBearing()) {
            obj = null;
        } else {
            int obj3 = 1;
        }
        if (!location.hasSpeed()) {
            int obj22 = 1;
        }
        if (obj3 > null) {
            this.M.add(Byte.valueOf((byte) 32));
        } else {
            this.M.add(Byte.valueOf((byte) (((byte) (((int) (location.getBearing() / 15.0f)) & 255)) & -33)));
        }
        if (obj22 > null) {
            this.M.add(Byte.valueOf(Byte.MIN_VALUE));
        } else {
            this.M.add(Byte.valueOf((byte) (((byte) (((int) ((((double) location.getSpeed()) * 3.6d) / 4.0d)) & 255)) & 127)));
        }
        this.b = location;
    }

    private void d(String str) {
        int i = 1;
        try {
            File file = new File(j.a + "/grtcf.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(2);
                int readInt = randomAccessFile.readInt();
                randomAccessFile.seek(8);
                int readInt2 = randomAccessFile.readInt();
                byte[] bArr = new byte[readInt2];
                randomAccessFile.read(bArr, 0, readInt2);
                String str2 = new String(bArr);
                if (str2.contains(b("%d_%02d_%02d")) && str2.contains(":")) {
                    try {
                        String[] split = str2.split(":");
                        if (split.length > 1) {
                            this.y = Integer.valueOf(split[1]).intValue();
                        }
                    } catch (Exception e) {
                    }
                }
                while (i <= readInt) {
                    randomAccessFile.seek((long) (i * 2048));
                    readInt2 = randomAccessFile.readInt();
                    bArr = new byte[readInt2];
                    randomAccessFile.read(bArr, 0, readInt2);
                    str2 = new String(bArr);
                    if (str != null && str2.contains(str)) {
                        c(str2);
                        break;
                    }
                    i++;
                }
                randomAccessFile.close();
            }
        } catch (Exception e2) {
        }
    }

    private void e(Location location) {
        if (location != null) {
            Object obj;
            Object obj2;
            Object obj3;
            Object obj4;
            int longitude = (int) ((location.getLongitude() - this.b.getLongitude()) * 1000000.0d);
            int latitude = (int) ((location.getLatitude() - this.b.getLatitude()) * 1000000.0d);
            if (location.hasBearing()) {
                obj = null;
            } else {
                int obj5 = 1;
            }
            if (location.hasSpeed()) {
                obj2 = null;
            } else {
                int obj22 = 1;
            }
            if (longitude > 0) {
                obj3 = null;
            } else {
                int obj32 = 1;
            }
            int abs = Math.abs(longitude);
            if (latitude > 0) {
                obj4 = null;
            } else {
                int obj42 = 1;
            }
            int abs2 = Math.abs(latitude);
            if (this.L > 1) {
                this.c = null;
                this.c = this.b;
            }
            this.b = location;
            if (this.b != null && this.c != null && this.b.getTime() > this.c.getTime() && this.b.getTime() - this.c.getTime() < 5000) {
                long time = this.b.getTime() - this.c.getTime();
                float[] fArr = new float[2];
                Location.distanceBetween(this.b.getAltitude(), this.b.getLongitude(), this.c.getLatitude(), this.c.getLongitude(), fArr);
                double speed = (double) ((2.0f * (fArr[0] - (this.c.getSpeed() * ((float) time)))) / ((float) (time * time)));
                if (speed > ((double) this.F)) {
                    this.F = (float) speed;
                }
            }
            this.M.add(Byte.valueOf((byte) (abs & 255)));
            this.M.add(Byte.valueOf((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & abs) >> 8)));
            this.M.add(Byte.valueOf((byte) (abs2 & 255)));
            this.M.add(Byte.valueOf((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & abs2) >> 8)));
            byte b;
            if (obj5 > null) {
                b = (byte) 32;
                if (obj42 > null) {
                    b = (byte) 96;
                }
                if (obj32 > null) {
                    b = (byte) (b | -128);
                }
                this.M.add(Byte.valueOf(b));
            } else {
                b = (byte) (((byte) (((int) (location.getBearing() / 15.0f)) & 255)) & 31);
                if (obj42 > null) {
                    b = (byte) (b | 64);
                }
                if (obj32 > null) {
                    b = (byte) (b | -128);
                }
                this.M.add(Byte.valueOf(b));
            }
            if (obj22 > null) {
                this.M.add(Byte.valueOf(Byte.MIN_VALUE));
                return;
            }
            this.M.add(Byte.valueOf((byte) (((byte) (((int) ((((double) location.getSpeed()) * 3.6d) / 4.0d)) & 255)) & 127)));
        }
    }

    private void e(String str) {
        try {
            File file = new File(j.a + "/grtcf.dat");
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
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
            RandomAccessFile randomAccessFile2 = new RandomAccessFile(file, "rw");
            randomAccessFile2.seek(2);
            int readInt = randomAccessFile2.readInt();
            int i = 1;
            while (i <= readInt) {
                randomAccessFile2.seek((long) (i * 2048));
                int readInt2 = randomAccessFile2.readInt();
                byte[] bArr = new byte[readInt2];
                randomAccessFile2.read(bArr, 0, readInt2);
                if (new String(bArr).contains(b.d)) {
                    break;
                }
                i++;
            }
            if (i >= readInt) {
                randomAccessFile2.seek(2);
                randomAccessFile2.writeInt(i);
            }
            randomAccessFile2.seek((long) (i * 2048));
            byte[] bytes2 = str.getBytes();
            randomAccessFile2.writeInt(bytes2.length);
            randomAccessFile2.write(bytes2);
            randomAccessFile2.close();
        } catch (Exception e) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:34:0x006e A:{SYNTHETIC, Splitter: B:34:0x006e} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0073 A:{Catch:{ Exception -> 0x007c }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A:{Catch:{ Exception -> 0x007c }} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x006e A:{SYNTHETIC, Splitter: B:34:0x006e} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0073 A:{Catch:{ Exception -> 0x007c }} */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x0078 A:{Catch:{ Exception -> 0x007c }} */
    private boolean e() {
        /*
        r7 = this;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r0 = 0;
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1.<init>();	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r6 = com.baidu.location.h.k.g();	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r6 = java.io.File.separator;	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r6 = "gflk.dat";
        r1 = r1.append(r6);	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r5.<init>(r1);	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r1 = r5.exists();	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        if (r1 != 0) goto L_0x0030;
    L_0x002d:
        r5.createNewFile();	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
    L_0x0030:
        if (r2 != 0) goto L_0x0087;
    L_0x0032:
        r1 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r6 = "rw";
        r1.<init>(r5, r6);	 Catch:{ Exception -> 0x0055, all -> 0x0069 }
        r3 = r1.getChannel();	 Catch:{ Exception -> 0x0083, all -> 0x007e }
        r2 = r3.tryLock();	 Catch:{ Exception -> 0x0052, all -> 0x0081 }
    L_0x0042:
        if (r2 == 0) goto L_0x0047;
    L_0x0044:
        r2.release();	 Catch:{ Exception -> 0x0085 }
    L_0x0047:
        if (r3 == 0) goto L_0x004c;
    L_0x0049:
        r3.close();	 Catch:{ Exception -> 0x0085 }
    L_0x004c:
        if (r1 == 0) goto L_0x0051;
    L_0x004e:
        r1.close();	 Catch:{ Exception -> 0x0085 }
    L_0x0051:
        return r0;
    L_0x0052:
        r0 = move-exception;
        r0 = 1;
        goto L_0x0042;
    L_0x0055:
        r1 = move-exception;
        r1 = r2;
    L_0x0057:
        if (r2 == 0) goto L_0x005c;
    L_0x0059:
        r4.release();	 Catch:{ Exception -> 0x0067 }
    L_0x005c:
        if (r2 == 0) goto L_0x0061;
    L_0x005e:
        r3.close();	 Catch:{ Exception -> 0x0067 }
    L_0x0061:
        if (r1 == 0) goto L_0x0051;
    L_0x0063:
        r1.close();	 Catch:{ Exception -> 0x0067 }
        goto L_0x0051;
    L_0x0067:
        r1 = move-exception;
        goto L_0x0051;
    L_0x0069:
        r0 = move-exception;
        r1 = r2;
        r3 = r2;
    L_0x006c:
        if (r2 == 0) goto L_0x0071;
    L_0x006e:
        r4.release();	 Catch:{ Exception -> 0x007c }
    L_0x0071:
        if (r3 == 0) goto L_0x0076;
    L_0x0073:
        r3.close();	 Catch:{ Exception -> 0x007c }
    L_0x0076:
        if (r1 == 0) goto L_0x007b;
    L_0x0078:
        r1.close();	 Catch:{ Exception -> 0x007c }
    L_0x007b:
        throw r0;
    L_0x007c:
        r1 = move-exception;
        goto L_0x007b;
    L_0x007e:
        r0 = move-exception;
        r3 = r2;
        goto L_0x006c;
    L_0x0081:
        r0 = move-exception;
        goto L_0x006c;
    L_0x0083:
        r5 = move-exception;
        goto L_0x0057;
    L_0x0085:
        r1 = move-exception;
        goto L_0x0051;
    L_0x0087:
        r1 = r2;
        r3 = r2;
        goto L_0x0042;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.c.e():boolean");
    }

    private boolean f() {
        if (this.B) {
            if (this.C) {
                if (((double) this.E) < this.t) {
                    this.D += this.p;
                    if (this.D <= this.u || System.currentTimeMillis() - this.G > ((long) (this.v * 1000))) {
                        return true;
                    }
                }
                this.D = 0;
                this.C = false;
                return true;
            } else if (((double) this.E) >= this.t) {
                return true;
            } else {
                this.C = true;
                this.D = 0;
                this.D += this.p;
                return true;
            }
        } else if (((double) this.E) >= this.r || ((double) this.F) >= this.s) {
            this.B = true;
            return true;
        } else if (this.w == 1 && System.currentTimeMillis() - this.G > ((long) (this.x * 1000))) {
            return true;
        }
        return false;
    }

    private void g() {
        this.M = null;
        this.e = 0;
        this.L = 0;
        this.b = null;
        this.c = null;
        this.E = 0.0f;
        this.F = 0.0f;
    }

    private void h() {
        if (this.e != 0 && System.currentTimeMillis() - this.e >= ((long) (this.p * 1000))) {
            if (com.baidu.location.f.getServiceContext().getSharedPreferences("loc_navi_mode", 4).getBoolean("is_navi_on", false)) {
                g();
            } else if (this.n != 1 || f()) {
                if (b.d.equals("com.ubercab.driver")) {
                    if (e()) {
                        g();
                        return;
                    }
                } else if (!a(b.d, com.baidu.location.f.getServiceContext())) {
                    g();
                    return;
                }
                if (this.M != null) {
                    int size = this.M.size();
                    this.M.set(0, Byte.valueOf((byte) (size & 255)));
                    this.M.set(1, Byte.valueOf((byte) ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & size) >> 8)));
                    this.M.set(3, Byte.valueOf((byte) (this.L & 255)));
                    byte[] bArr = new byte[size];
                    for (int i = 0; i < size; i++) {
                        bArr[i] = ((Byte) this.M.get(i)).byteValue();
                    }
                    if (Environment.getExternalStorageState().equals("mounted")) {
                        File file = new File(Environment.getExternalStorageDirectory(), "baidu/tempdata");
                        if (!file.exists()) {
                            file.mkdirs();
                        }
                        if (file.exists()) {
                            File file2 = new File(file, "intime.dat");
                            if (file2.exists()) {
                                file2.delete();
                            }
                            try {
                                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file2));
                                bufferedOutputStream.write(bArr);
                                bufferedOutputStream.flush();
                                bufferedOutputStream.close();
                                new e(this).start();
                            } catch (Exception e) {
                            }
                        }
                    }
                    g();
                    this.G = System.currentTimeMillis();
                }
            } else {
                g();
            }
        }
    }

    private void i() {
        int i = 0;
        this.M.add(Byte.valueOf((byte) 0));
        this.M.add(Byte.valueOf((byte) 0));
        if (f.equals("0")) {
            this.M.add(Byte.valueOf((byte) -82));
        } else {
            this.M.add(Byte.valueOf((byte) -66));
        }
        this.M.add(Byte.valueOf((byte) 0));
        this.M.add(Byte.valueOf(this.J[0]));
        this.M.add(Byte.valueOf(this.J[1]));
        this.M.add(Byte.valueOf(this.J[2]));
        this.M.add(Byte.valueOf(this.J[3]));
        int length = this.K.length;
        this.M.add(Byte.valueOf((byte) ((length + 1) & 255)));
        while (i < length) {
            this.M.add(Byte.valueOf(this.K[i]));
            i++;
        }
    }

    private void j() {
        if (System.currentTimeMillis() - this.z > 86400000) {
            if (this.A == null) {
                this.A = new a();
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(b.a().a(false));
            stringBuffer.append(a.a().e());
            this.A.a(stringBuffer.toString());
        }
        k();
    }

    private void k() {
    }

    public void a(Location location) {
        if (!this.N) {
            c();
        }
        Object obj = ((double) d.a().f()) < this.l * 100.0d ? 1 : null;
        if (this.k != 1 || obj == null || !this.m.contains(com.baidu.location.f.c.a(com.baidu.location.f.b.a().e()))) {
            return;
        }
        if (this.n != 1 || this.y <= this.o) {
            this.I.post(new d(this, location));
        }
    }

    public void b() {
        if (this.N) {
            this.N = false;
            g();
        }
    }
}
