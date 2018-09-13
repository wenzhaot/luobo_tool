package com.tencent.liteav.e;

import android.text.TextUtils;
import com.feng.car.utils.HttpConstant;
import com.tencent.liteav.c.e;
import com.tencent.liteav.c.g;
import com.tencent.liteav.i.a.b;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: AnimatedPasterFilterChain */
public class a extends c {
    private static a d;
    private List<b> e;
    private CopyOnWriteArrayList<com.tencent.liteav.c.a> f = new CopyOnWriteArrayList();

    public static a a() {
        if (d == null) {
            d = new a();
        }
        return d;
    }

    private a() {
    }

    public void a(List<b> list) {
        this.e = list;
        this.f.clear();
        if (this.c != null) {
            a(this.c);
        }
    }

    public List<com.tencent.liteav.c.a> b() {
        return this.f;
    }

    public void a(e eVar) {
        if (eVar != null && this.a != 0 && this.b != 0 && this.e != null && this.e.size() != 0) {
            g b = b(eVar);
            for (b bVar : this.e) {
                if (bVar != null) {
                    b a = a(bVar, a(bVar.b, b));
                    com.tencent.liteav.b.a a2 = a(a.a);
                    if (a2 != null && a2.c > 0) {
                        int i;
                        long j = a.c;
                        long j2 = a.d - j;
                        int i2 = a2.b / a2.c;
                        int i3 = (int) (j2 / ((long) a2.b));
                        if (j2 % ((long) a2.b) > 0) {
                            i = i3 + 1;
                        } else {
                            i = i3;
                        }
                        int i4 = 0;
                        while (i4 < i) {
                            i3 = 0;
                            j2 = j;
                            while (true) {
                                int i5 = i3;
                                if (i5 >= a2.c || ((long) i2) + j2 > a.d) {
                                    i4++;
                                    j = j2;
                                } else {
                                    com.tencent.liteav.b.a.a aVar = (com.tencent.liteav.b.a.a) a2.g.get(i5);
                                    com.tencent.liteav.c.a aVar2 = new com.tencent.liteav.c.a();
                                    aVar2.a = a.a + aVar.a + ".png";
                                    aVar2.b = a.b;
                                    aVar2.c = j2;
                                    aVar2.d = j2 + ((long) i2);
                                    aVar2.e = a.e;
                                    this.f.add(aVar2);
                                    j2 = aVar2.d;
                                    i3 = i5 + 1;
                                }
                            }
                            i4++;
                            j = j2;
                        }
                    }
                }
            }
        }
    }

    private com.tencent.liteav.b.a a(String str) {
        com.tencent.liteav.b.a aVar = null;
        Object b = b(str + "config.json");
        if (!TextUtils.isEmpty(b)) {
            try {
                JSONObject jSONObject = new JSONObject(b);
                aVar = new com.tencent.liteav.b.a();
                try {
                    aVar.a = jSONObject.getString("name");
                    aVar.c = jSONObject.getInt(HttpConstant.COUNT);
                    aVar.b = jSONObject.getInt("period");
                    aVar.d = jSONObject.getInt("width");
                    aVar.e = jSONObject.getInt("height");
                    aVar.f = jSONObject.getInt("keyframe");
                    JSONArray jSONArray = jSONObject.getJSONArray("frameArray");
                    for (int i = 0; i < aVar.c; i++) {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        com.tencent.liteav.b.a.a aVar2 = new com.tencent.liteav.b.a.a();
                        aVar2.a = jSONObject2.getString("picture");
                        aVar.g.add(aVar2);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e2) {
                e2.printStackTrace();
            }
        }
        return aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:20:0x0043 A:{SYNTHETIC, Splitter: B:20:0x0043} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0050 A:{SYNTHETIC, Splitter: B:27:0x0050} */
    private java.lang.String b(java.lang.String r6) {
        /*
        r5 = this;
        r3 = "";
        r1 = 0;
        r0 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r0.<init>(r6);	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r4 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r4.<init>(r0);	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r2.<init>(r4);	 Catch:{ IOException -> 0x003a, all -> 0x004c }
        r0 = r3;
    L_0x0014:
        r1 = r2.readLine();	 Catch:{ IOException -> 0x005b }
        if (r1 == 0) goto L_0x002c;
    L_0x001a:
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x005b }
        r3.<init>();	 Catch:{ IOException -> 0x005b }
        r3 = r3.append(r0);	 Catch:{ IOException -> 0x005b }
        r1 = r3.append(r1);	 Catch:{ IOException -> 0x005b }
        r0 = r1.toString();	 Catch:{ IOException -> 0x005b }
        goto L_0x0014;
    L_0x002c:
        r2.close();	 Catch:{ IOException -> 0x005b }
        if (r2 == 0) goto L_0x0034;
    L_0x0031:
        r2.close();	 Catch:{ IOException -> 0x0035 }
    L_0x0034:
        return r0;
    L_0x0035:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0034;
    L_0x003a:
        r0 = move-exception;
        r2 = r1;
        r1 = r0;
        r0 = r3;
    L_0x003e:
        r1.printStackTrace();	 Catch:{ all -> 0x0059 }
        if (r2 == 0) goto L_0x0034;
    L_0x0043:
        r2.close();	 Catch:{ IOException -> 0x0047 }
        goto L_0x0034;
    L_0x0047:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0034;
    L_0x004c:
        r0 = move-exception;
        r2 = r1;
    L_0x004e:
        if (r2 == 0) goto L_0x0053;
    L_0x0050:
        r2.close();	 Catch:{ IOException -> 0x0054 }
    L_0x0053:
        throw r0;
    L_0x0054:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x0053;
    L_0x0059:
        r0 = move-exception;
        goto L_0x004e;
    L_0x005b:
        r1 = move-exception;
        goto L_0x003e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.tencent.liteav.e.a.b(java.lang.String):java.lang.String");
    }

    private b a(b bVar, com.tencent.liteav.i.a.g gVar) {
        b bVar2 = new b();
        bVar2.b = gVar;
        bVar2.a = bVar.a;
        bVar2.c = bVar.c;
        bVar2.d = bVar.d;
        bVar2.e = bVar.e;
        return bVar2;
    }

    public void c() {
        this.f.clear();
        if (this.e != null) {
            this.e.clear();
        }
        this.e = null;
    }
}
