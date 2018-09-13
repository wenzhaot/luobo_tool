package com.baidu.location.b;

import android.content.SharedPreferences.Editor;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import com.baidu.location.Jni;
import com.baidu.location.h.b;
import com.baidu.location.h.c;
import com.baidu.location.h.f;
import com.baidu.location.h.j;
import com.baidu.location.h.k;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Locale;
import org.json.JSONObject;

public class e {
    private static e i = null;
    private static final String k = (j.a + "/conlts.dat");
    private static int l = -1;
    private static int m = -1;
    private static int n = 0;
    public boolean a = true;
    public boolean b = true;
    public boolean c = false;
    public boolean d = true;
    public boolean e = true;
    public boolean f = true;
    public boolean g = true;
    public boolean h = false;
    private a j = null;

    class a extends f {
        String a;
        boolean b;
        boolean c;

        public a() {
            this.a = null;
            this.b = false;
            this.c = false;
            this.k = new HashMap();
        }

        public void a() {
            this.h = k.c();
            this.i = 2;
            String encode = Jni.encode(this.a);
            this.a = null;
            if (this.b) {
                this.k.put("qt", "grid");
            } else {
                this.k.put("qt", "conf");
            }
            this.k.put("req", encode);
        }

        public void a(String str, boolean z) {
            if (!this.c) {
                this.c = true;
                this.a = str;
                this.b = z;
                if (z) {
                    a(true, "loc.map.baidu.com");
                } else {
                    c(k.f);
                }
            }
        }

        public void a(boolean z) {
            if (!z || this.j == null) {
                e.this.c(null);
            } else if (this.b) {
                e.this.a(this.m);
            } else {
                e.this.c(this.j);
            }
            if (this.k != null) {
                this.k.clear();
            }
            this.c = false;
        }
    }

    private e() {
    }

    public static e a() {
        if (i == null) {
            i = new e();
        }
        return i;
    }

    private void a(int i) {
        boolean z = true;
        this.a = (i & 1) == 1;
        this.b = (i & 2) == 2;
        this.c = (i & 4) == 4;
        this.d = (i & 8) == 8;
        this.f = (i & 65536) == 65536;
        if ((i & 131072) != 131072) {
            z = false;
        }
        this.g = z;
        if ((i & 16) == 16) {
            this.e = false;
        }
    }

    private void a(JSONObject jSONObject) {
        boolean z = false;
        if (jSONObject != null) {
            int i = 14400000;
            int i2 = 10;
            try {
                if (!(jSONObject.has("ipen") && jSONObject.getInt("ipen") == 0)) {
                    z = true;
                }
                if (jSONObject.has("ipvt")) {
                    i = jSONObject.getInt("ipvt");
                }
                if (jSONObject.has("ipvn")) {
                    i2 = jSONObject.getInt("ipvn");
                }
                Editor edit = com.baidu.location.f.getServiceContext().getSharedPreferences("MapCoreServicePre", 0).edit();
                edit.putBoolean("ipLocInfoUpload", z);
                edit.putInt("ipValidTime", i);
                edit.putInt("ipLocInfoUploadTimesPerDay", i2);
                edit.commit();
            } catch (Exception e) {
            }
        }
    }

    private void a(byte[] bArr) {
        int i = 0;
        if (bArr != null) {
            if (bArr.length < 640) {
                k.w = false;
                k.t = k.r + 0.025d;
                k.s = k.q - 0.025d;
                i = 1;
            } else {
                k.w = true;
                k.s = Double.longBitsToDouble(((((((((((long) bArr[7]) & 255) << 56) | ((((long) bArr[6]) & 255) << 48)) | ((((long) bArr[5]) & 255) << 40)) | ((((long) bArr[4]) & 255) << 32)) | ((((long) bArr[3]) & 255) << 24)) | ((((long) bArr[2]) & 255) << 16)) | ((((long) bArr[1]) & 255) << 8)) | (((long) bArr[0]) & 255));
                k.t = Double.longBitsToDouble(((((((((((long) bArr[15]) & 255) << 56) | ((((long) bArr[14]) & 255) << 48)) | ((((long) bArr[13]) & 255) << 40)) | ((((long) bArr[12]) & 255) << 32)) | ((((long) bArr[11]) & 255) << 24)) | ((((long) bArr[10]) & 255) << 16)) | ((((long) bArr[9]) & 255) << 8)) | (((long) bArr[8]) & 255));
                k.v = new byte[625];
                while (i < 625) {
                    k.v[i] = bArr[i + 16];
                    i++;
                }
                i = 1;
            }
        }
        if (i != 0) {
            try {
                g();
            } catch (Exception e) {
            }
        }
    }

    private void b(int i) {
        File file = new File(k);
        if (!file.exists()) {
            i();
        }
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(4);
            int readInt = randomAccessFile.readInt();
            int readInt2 = randomAccessFile.readInt();
            randomAccessFile.seek((long) ((readInt * n) + 128));
            byte[] bytes = (b.d + 0).getBytes();
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes, 0, bytes.length);
            randomAccessFile.writeInt(i);
            if (readInt2 == n) {
                randomAccessFile.seek(8);
                randomAccessFile.writeInt(readInt2 + 1);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private boolean b(String str) {
        boolean z = true;
        if (str != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ipconf")) {
                    try {
                        a(jSONObject.getJSONObject("ipconf"));
                    } catch (Exception e) {
                    }
                }
                int parseInt = Integer.parseInt(jSONObject.getString("ver"));
                if (parseInt > k.x) {
                    String[] split;
                    k.x = parseInt;
                    if (jSONObject.has("gps")) {
                        split = jSONObject.getString("gps").split("\\|");
                        if (split.length > 10) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.y = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.z = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.A = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.B = Float.parseFloat(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                k.C = Integer.parseInt(split[4]);
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                k.D = Integer.parseInt(split[5]);
                            }
                            if (!(split[6] == null || split[6].equals(""))) {
                                k.E = Integer.parseInt(split[6]);
                            }
                            if (!(split[7] == null || split[7].equals(""))) {
                                k.F = Integer.parseInt(split[7]);
                            }
                            if (!(split[8] == null || split[8].equals(""))) {
                                k.G = Integer.parseInt(split[8]);
                            }
                            if (!(split[9] == null || split[9].equals(""))) {
                                k.H = Integer.parseInt(split[9]);
                            }
                            if (!(split[10] == null || split[10].equals(""))) {
                                k.I = Integer.parseInt(split[10]);
                            }
                        }
                    }
                    if (jSONObject.has("up")) {
                        split = jSONObject.getString("up").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.J = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.K = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.L = Float.parseFloat(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.M = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("wf")) {
                        split = jSONObject.getString("wf").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.N = Integer.parseInt(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.O = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.P = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.Q = Float.parseFloat(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("ab")) {
                        split = jSONObject.getString("ab").split("\\|");
                        if (split.length > 3) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.R = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.S = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.T = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.U = Integer.parseInt(split[3]);
                            }
                        }
                    }
                    if (jSONObject.has("zxd")) {
                        split = jSONObject.getString("zxd").split("\\|");
                        if (split.length > 4) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.aq = Float.parseFloat(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.ar = Float.parseFloat(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.as = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.at = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                k.au = Integer.parseInt(split[4]);
                            }
                        }
                    }
                    if (jSONObject.has("gpc")) {
                        split = jSONObject.getString("gpc").split("\\|");
                        if (split.length > 5) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                if (Integer.parseInt(split[0]) > 0) {
                                    k.Z = true;
                                } else {
                                    k.Z = false;
                                }
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                if (Integer.parseInt(split[1]) > 0) {
                                    k.aa = true;
                                } else {
                                    k.aa = false;
                                }
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.ab = Integer.parseInt(split[2]);
                            }
                            if (!(split[3] == null || split[3].equals(""))) {
                                k.ad = Integer.parseInt(split[3]);
                            }
                            if (!(split[4] == null || split[4].equals(""))) {
                                int parseInt2 = Integer.parseInt(split[4]);
                                if (parseInt2 > 0) {
                                    k.aj = (long) parseInt2;
                                    k.af = (k.aj * 1000) * 60;
                                    k.ak = k.af >> 2;
                                } else {
                                    k.o = false;
                                }
                            }
                            if (!(split[5] == null || split[5].equals(""))) {
                                k.am = Integer.parseInt(split[5]);
                            }
                        }
                    }
                    if (jSONObject.has("shak")) {
                        split = jSONObject.getString("shak").split("\\|");
                        if (split.length > 2) {
                            if (!(split[0] == null || split[0].equals(""))) {
                                k.an = Integer.parseInt(split[0]);
                            }
                            if (!(split[1] == null || split[1].equals(""))) {
                                k.ao = Integer.parseInt(split[1]);
                            }
                            if (!(split[2] == null || split[2].equals(""))) {
                                k.ap = Float.parseFloat(split[2]);
                            }
                        }
                    }
                    if (jSONObject.has("dmx")) {
                        k.al = jSONObject.getInt("dmx");
                    }
                    return z;
                }
            } catch (Exception e2) {
                return false;
            }
        }
        z = false;
        return z;
    }

    private void c(String str) {
        m = -1;
        if (str != null) {
            try {
                if (b(str)) {
                    f();
                }
            } catch (Exception e) {
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("ctr")) {
                    m = Integer.parseInt(jSONObject.getString("ctr"));
                }
            } catch (Exception e2) {
            }
            try {
                int i;
                j();
                if (m != -1) {
                    i = m;
                    b(m);
                } else {
                    i = l != -1 ? l : -1;
                }
                if (i != -1) {
                    a(i);
                }
            } catch (Exception e3) {
            }
        }
    }

    private void e() {
        String str = "&ver=" + k.x + "&usr=" + b.a().b() + "&app=" + b.d + "&prod=" + b.e;
        if (this.j == null) {
            this.j = new a();
        }
        this.j.a(str, false);
    }

    private void f() {
        String str = j.a + "/config.dat";
        int i = k.Z ? 1 : 0;
        int i2 = k.aa ? 1 : 0;
        byte[] bytes = String.format(Locale.CHINA, "{\"ver\":\"%d\",\"gps\":\"%.1f|%.1f|%.1f|%.1f|%d|%d|%d|%d|%d|%d|%d\",\"up\":\"%.1f|%.1f|%.1f|%.1f\",\"wf\":\"%d|%.1f|%d|%.1f\",\"ab\":\"%.2f|%.2f|%d|%d\",\"gpc\":\"%d|%d|%d|%d|%d|%d\",\"zxd\":\"%.1f|%.1f|%d|%d|%d\",\"shak\":\"%d|%d|%.1f\",\"dmx\":%d}", new Object[]{Integer.valueOf(k.x), Float.valueOf(k.y), Float.valueOf(k.z), Float.valueOf(k.A), Float.valueOf(k.B), Integer.valueOf(k.C), Integer.valueOf(k.D), Integer.valueOf(k.E), Integer.valueOf(k.F), Integer.valueOf(k.G), Integer.valueOf(k.H), Integer.valueOf(k.I), Float.valueOf(k.J), Float.valueOf(k.K), Float.valueOf(k.L), Float.valueOf(k.M), Integer.valueOf(k.N), Float.valueOf(k.O), Integer.valueOf(k.P), Float.valueOf(k.Q), Float.valueOf(k.R), Float.valueOf(k.S), Integer.valueOf(k.T), Integer.valueOf(k.U), Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(k.ab), Integer.valueOf(k.ad), Long.valueOf(k.aj), Integer.valueOf(k.am), Float.valueOf(k.aq), Float.valueOf(k.ar), Integer.valueOf(k.as), Integer.valueOf(k.at), Integer.valueOf(k.au), Integer.valueOf(k.an), Integer.valueOf(k.ao), Float.valueOf(k.ap), Integer.valueOf(k.al)}).getBytes();
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(str);
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(0);
            randomAccessFile.writeBoolean(true);
            randomAccessFile.seek(2);
            randomAccessFile.writeInt(bytes.length);
            randomAccessFile.write(bytes);
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private void g() {
        try {
            RandomAccessFile randomAccessFile;
            File file = new File(j.a + "/config.dat");
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (file.createNewFile()) {
                    randomAccessFile = new RandomAccessFile(file, "rw");
                    randomAccessFile.seek(0);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.writeBoolean(false);
                    randomAccessFile.close();
                } else {
                    return;
                }
            }
            randomAccessFile = new RandomAccessFile(file, "rw");
            randomAccessFile.seek(1);
            randomAccessFile.writeBoolean(true);
            randomAccessFile.seek(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
            randomAccessFile.writeDouble(k.s);
            randomAccessFile.writeDouble(k.t);
            randomAccessFile.writeBoolean(k.w);
            if (k.w && k.v != null) {
                randomAccessFile.write(k.v);
            }
            randomAccessFile.close();
        } catch (Exception e) {
        }
    }

    private void h() {
        try {
            File file = new File(j.a + "/config.dat");
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(2);
                    int readInt = randomAccessFile.readInt();
                    byte[] bArr = new byte[readInt];
                    randomAccessFile.read(bArr, 0, readInt);
                    b(new String(bArr));
                }
                randomAccessFile.seek(1);
                if (randomAccessFile.readBoolean()) {
                    randomAccessFile.seek(PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID);
                    k.s = randomAccessFile.readDouble();
                    k.t = randomAccessFile.readDouble();
                    k.w = randomAccessFile.readBoolean();
                    if (k.w) {
                        k.v = new byte[625];
                        randomAccessFile.read(k.v, 0, 625);
                    }
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
        if (!k.o || com.baidu.location.f.isServing) {
        }
    }

    private void i() {
        try {
            File file = new File(k);
            if (!file.exists()) {
                File file2 = new File(j.a);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                if (!file.createNewFile()) {
                    file = null;
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(0);
                randomAccessFile.writeInt(0);
                randomAccessFile.writeInt(128);
                randomAccessFile.writeInt(0);
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    private void j() {
        int i = 0;
        try {
            File file = new File(k);
            if (file.exists()) {
                RandomAccessFile randomAccessFile = new RandomAccessFile(file, "rw");
                randomAccessFile.seek(4);
                int readInt = randomAccessFile.readInt();
                if (readInt > PathInterpolatorCompat.MAX_NUM_POINTS) {
                    randomAccessFile.close();
                    n = 0;
                    i();
                    return;
                }
                int readInt2 = randomAccessFile.readInt();
                randomAccessFile.seek(128);
                byte[] bArr = new byte[readInt];
                while (i < readInt2) {
                    randomAccessFile.seek((long) ((readInt * i) + 128));
                    int readInt3 = randomAccessFile.readInt();
                    if (readInt3 > 0 && readInt3 < readInt) {
                        randomAccessFile.read(bArr, 0, readInt3);
                        if (bArr[readInt3 - 1] == (byte) 0) {
                            String str = new String(bArr, 0, readInt3 - 1);
                            b.a();
                            if (str.equals(b.d)) {
                                l = randomAccessFile.readInt();
                                n = i;
                                break;
                            }
                        } else {
                            continue;
                        }
                    }
                    i++;
                }
                if (i == readInt2) {
                    n = readInt2;
                }
                randomAccessFile.close();
            }
        } catch (Exception e) {
        }
    }

    public void a(String str) {
        if (this.j == null) {
            this.j = new a();
        }
        this.j.a(str, true);
    }

    public void b() {
        h();
    }

    public void c() {
    }

    public void d() {
        if (System.currentTimeMillis() - c.a().d() > 86400000) {
            c.a().c(System.currentTimeMillis());
            e();
        }
    }
}
