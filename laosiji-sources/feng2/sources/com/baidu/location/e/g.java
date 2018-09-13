package com.baidu.location.e;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.os.EnvironmentCompat;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.location.Jni;
import com.baidu.location.h.b;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;
import org.json.JSONObject;

final class g {
    private final h a;
    private final SQLiteDatabase b;
    private final a c;
    private boolean d;
    private boolean e;
    private boolean f;
    private boolean g;
    private boolean h;
    private String[] i;
    private boolean j;
    private boolean k;
    private int l;
    private int m;
    private int n;
    private double o;
    private double p;
    private double q;
    private double r;
    private double s;
    private int t;
    private boolean u = true;
    private long v = 8000;
    private long w = 5000;
    private long x = 5000;
    private long y = 5000;
    private long z = 5000;

    private final class a extends f {
        private int b;
        private long c;
        private long d;
        private boolean e;
        private final String f;

        private a() {
            this.b = 0;
            this.e = false;
            this.c = -1;
            this.d = -1;
            this.k = new HashMap();
            this.f = Jni.encodeOfflineLocationUpdateRequest(String.format(Locale.US, "&ver=%s&cuid=%s&prod=%s:%s&sdk=%.2f", new Object[]{"1", b.a().b, b.e, b.d, Float.valueOf(7.21f)}));
        }

        private void b() {
            if (!this.e) {
                boolean z = false;
                try {
                    File file = new File(g.this.a.c(), "ofl.config");
                    if (this.d == -1 && file.exists()) {
                        JSONObject jSONObject;
                        Scanner scanner = new Scanner(file);
                        String next = scanner.next();
                        scanner.close();
                        JSONObject jSONObject2 = new JSONObject(next);
                        g.this.d = jSONObject2.getBoolean("ol");
                        g.this.e = jSONObject2.getBoolean("fl");
                        g.this.f = jSONObject2.getBoolean("on");
                        g.this.g = jSONObject2.getBoolean("wn");
                        g.this.h = jSONObject2.getBoolean("oc");
                        this.d = jSONObject2.getLong(DispatchConstants.TIMESTAMP);
                        if (jSONObject2.has("ol")) {
                            g.this.k = jSONObject2.getBoolean("olv2");
                        }
                        if (jSONObject2.has("cplist")) {
                            g.this.i = jSONObject2.getString("cplist").split(";");
                        }
                        if (jSONObject2.has("rgcgp")) {
                            g.this.l = jSONObject2.getInt("rgcgp");
                        }
                        if (jSONObject2.has("rgcon")) {
                            g.this.j = jSONObject2.getBoolean("rgcon");
                        }
                        if (jSONObject2.has("addrup")) {
                            g.this.n = jSONObject2.getInt("addrup");
                        }
                        if (jSONObject2.has("poiup")) {
                            g.this.m = jSONObject2.getInt("poiup");
                        }
                        if (jSONObject2.has("oflp")) {
                            jSONObject = jSONObject2.getJSONObject("oflp");
                            if (jSONObject.has("0")) {
                                g.this.o = jSONObject.getDouble("0");
                            }
                            if (jSONObject.has("1")) {
                                g.this.p = jSONObject.getDouble("1");
                            }
                            if (jSONObject.has("2")) {
                                g.this.q = jSONObject.getDouble("2");
                            }
                            if (jSONObject.has("3")) {
                                g.this.r = jSONObject.getDouble("3");
                            }
                            if (jSONObject.has("4")) {
                                g.this.s = jSONObject.getDouble("4");
                            }
                        }
                        if (jSONObject2.has("onlt")) {
                            jSONObject = jSONObject2.getJSONObject("onlt");
                            if (jSONObject.has("0")) {
                                g.this.z = jSONObject.getLong("0");
                            }
                            if (jSONObject.has("1")) {
                                g.this.y = jSONObject.getLong("1");
                            }
                            if (jSONObject.has("2")) {
                                g.this.v = jSONObject.getLong("2");
                            }
                            if (jSONObject.has("3")) {
                                g.this.w = jSONObject.getLong("3");
                            }
                            if (jSONObject.has("4")) {
                                g.this.x = jSONObject.getLong("4");
                            }
                        }
                        if (jSONObject2.has("minapn")) {
                            g.this.t = jSONObject2.getInt("minapn");
                        }
                    }
                    if (!(this.d == -1 && file.exists())) {
                    }
                    if (this.d != -1 && this.d + 86400000 <= System.currentTimeMillis()) {
                        z = true;
                    }
                } catch (Exception e) {
                }
                if ((this.d == -1 || z) && c() && k.a(g.this.a.b())) {
                    this.e = true;
                    c("https://ofloc.map.baidu.com/offline_loc");
                }
            }
        }

        private boolean c() {
            boolean z = true;
            if (this.b >= 2) {
                if (this.c + 86400000 < System.currentTimeMillis()) {
                    this.b = 0;
                    this.c = -1;
                } else {
                    z = false;
                }
            }
            if (!z) {
            }
            return z;
        }

        public void a() {
            this.k.clear();
            this.k.put("qt", "conf");
            this.k.put("req", this.f);
            this.h = h.b;
        }

        public void a(boolean z) {
            if (!z || this.j == null) {
                this.b++;
                this.c = System.currentTimeMillis();
            } else {
                try {
                    JSONObject jSONObject = new JSONObject(this.j);
                    Object obj = "1";
                    long j = 0;
                    if (jSONObject.has("ofl")) {
                        j = jSONObject.getLong("ofl");
                    }
                    if (jSONObject.has("ver")) {
                        obj = jSONObject.getString("ver");
                    }
                    if ((j & 1) == 1) {
                        g.this.d = true;
                    }
                    if ((j & 2) == 2) {
                        g.this.e = true;
                    }
                    if ((j & 4) == 4) {
                        g.this.f = true;
                    }
                    if ((j & 8) == 8) {
                        g.this.g = true;
                    }
                    if ((16 & j) == 16) {
                        g.this.h = true;
                    }
                    if ((32 & j) == 32) {
                        g.this.j = true;
                    }
                    if ((j & 64) == 64) {
                        g.this.k = true;
                    }
                    JSONObject jSONObject2 = new JSONObject();
                    if (jSONObject.has("cplist")) {
                        g.this.i = jSONObject.getString("cplist").split(";");
                        jSONObject2.put("cplist", jSONObject.getString("cplist"));
                    }
                    if (jSONObject.has("bklist")) {
                        g.this.a(jSONObject.getString("bklist").split(";"));
                    }
                    if (jSONObject.has("para")) {
                        JSONObject jSONObject3;
                        jSONObject = jSONObject.getJSONObject("para");
                        if (jSONObject.has("rgcgp")) {
                            g.this.l = jSONObject.getInt("rgcgp");
                        }
                        if (jSONObject.has("addrup")) {
                            g.this.n = jSONObject.getInt("addrup");
                        }
                        if (jSONObject.has("poiup")) {
                            g.this.m = jSONObject.getInt("poiup");
                        }
                        if (jSONObject.has("oflp")) {
                            jSONObject3 = jSONObject.getJSONObject("oflp");
                            if (jSONObject3.has("0")) {
                                g.this.o = jSONObject3.getDouble("0");
                            }
                            if (jSONObject3.has("1")) {
                                g.this.p = jSONObject3.getDouble("1");
                            }
                            if (jSONObject3.has("2")) {
                                g.this.q = jSONObject3.getDouble("2");
                            }
                            if (jSONObject3.has("3")) {
                                g.this.r = jSONObject3.getDouble("3");
                            }
                            if (jSONObject3.has("4")) {
                                g.this.s = jSONObject3.getDouble("4");
                            }
                        }
                        if (jSONObject.has("onlt")) {
                            jSONObject3 = jSONObject.getJSONObject("onlt");
                            if (jSONObject3.has("0")) {
                                g.this.z = jSONObject3.getLong("0");
                            }
                            if (jSONObject3.has("1")) {
                                g.this.y = jSONObject3.getLong("1");
                            }
                            if (jSONObject3.has("2")) {
                                g.this.v = jSONObject3.getLong("2");
                            }
                            if (jSONObject3.has("3")) {
                                g.this.w = jSONObject3.getLong("3");
                            }
                            if (jSONObject3.has("4")) {
                                g.this.x = jSONObject3.getLong("4");
                            }
                        }
                        if (jSONObject.has("minapn")) {
                            g.this.t = jSONObject.getInt("minapn");
                        }
                    }
                    jSONObject2.put("ol", g.this.d);
                    jSONObject2.put("olv2", g.this.k);
                    jSONObject2.put("fl", g.this.e);
                    jSONObject2.put("on", g.this.f);
                    jSONObject2.put("wn", g.this.g);
                    jSONObject2.put("oc", g.this.h);
                    this.d = System.currentTimeMillis();
                    jSONObject2.put(DispatchConstants.TIMESTAMP, this.d);
                    jSONObject2.put("ver", obj);
                    jSONObject2.put("rgcon", g.this.j);
                    jSONObject2.put("rgcgp", g.this.l);
                    JSONObject jSONObject4 = new JSONObject();
                    jSONObject4.put("0", g.this.o);
                    jSONObject4.put("1", g.this.p);
                    jSONObject4.put("2", g.this.q);
                    jSONObject4.put("3", g.this.r);
                    jSONObject4.put("4", g.this.s);
                    jSONObject2.put("oflp", jSONObject4);
                    jSONObject4 = new JSONObject();
                    jSONObject4.put("0", g.this.z);
                    jSONObject4.put("1", g.this.y);
                    jSONObject4.put("2", g.this.v);
                    jSONObject4.put("3", g.this.w);
                    jSONObject4.put("4", g.this.x);
                    jSONObject2.put("onlt", jSONObject4);
                    jSONObject2.put("addrup", g.this.n);
                    jSONObject2.put("poiup", g.this.m);
                    jSONObject2.put("minapn", g.this.t);
                    File file = new File(g.this.a.c(), "ofl.config");
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(file);
                    fileWriter.write(jSONObject2.toString());
                    fileWriter.close();
                } catch (Exception e) {
                    this.b++;
                    this.c = System.currentTimeMillis();
                }
            }
            this.e = false;
        }
    }

    g(h hVar, SQLiteDatabase sQLiteDatabase) {
        this.a = hVar;
        this.d = false;
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = false;
        this.j = false;
        this.k = false;
        this.l = 6;
        this.m = 30;
        this.n = 30;
        this.o = 0.0d;
        this.p = 0.0d;
        this.q = 0.0d;
        this.r = 0.0d;
        this.s = 0.0d;
        this.t = 8;
        this.i = new String[0];
        this.b = sQLiteDatabase;
        this.c = new a();
        if (this.b != null && this.b.isOpen()) {
            try {
                this.b.execSQL("CREATE TABLE IF NOT EXISTS BLACK (name VARCHAR(100) PRIMARY KEY);");
            } catch (Exception e) {
            }
        }
        g();
    }

    int a() {
        return this.t;
    }

    long a(String str) {
        return str.equals("2G") ? this.v : str.equals("3G") ? this.w : str.equals("4G") ? this.x : str.equals("WIFI") ? this.y : str.equals(EnvironmentCompat.MEDIA_UNKNOWN) ? this.z : 5000;
    }

    void a(String[] strArr) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < strArr.length; i++) {
            if (i > 0) {
                stringBuffer.append(",");
            }
            stringBuffer.append("(\"");
            stringBuffer.append(strArr[i]);
            stringBuffer.append("\")");
        }
        if (this.b != null && this.b.isOpen() && stringBuffer.length() > 0) {
            try {
                this.b.execSQL(String.format(Locale.US, "INSERT OR IGNORE INTO BLACK VALUES %s;", new Object[]{stringBuffer.toString()}));
            } catch (Exception e) {
            }
        }
    }

    double b() {
        return this.o;
    }

    double c() {
        return this.p;
    }

    double d() {
        return this.q;
    }

    double e() {
        return this.r;
    }

    double f() {
        return this.s;
    }

    void g() {
        this.c.b();
    }

    boolean h() {
        return this.d;
    }

    boolean i() {
        return this.f;
    }

    boolean j() {
        return this.g;
    }

    boolean k() {
        return this.e;
    }

    boolean l() {
        return this.j;
    }

    boolean m() {
        return this.u;
    }

    int n() {
        return this.l;
    }

    String[] o() {
        return this.i;
    }

    int p() {
        return this.n;
    }

    int q() {
        return this.m;
    }
}
