package com.baidu.location.a;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.baidu.location.Jni;
import com.baidu.location.f.b;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import com.facebook.common.util.UriUtil;
import java.io.File;
import java.util.HashMap;
import org.json.JSONObject;

public class g {
    private static Object c = new Object();
    private static g d = null;
    private static final String e = (k.h() + "/hst.db");
    a a = null;
    a b = null;
    private SQLiteDatabase f = null;
    private boolean g = false;

    class a extends f {
        private String b;
        private String c;
        private boolean d;
        private boolean e;

        a() {
            this.b = null;
            this.c = null;
            this.d = true;
            this.e = false;
            this.k = new HashMap();
        }

        public void a() {
            this.i = 1;
            this.h = k.c();
            String encodeTp4 = Jni.encodeTp4(this.c);
            this.c = null;
            this.k.put("bloc", encodeTp4);
        }

        public void a(String str, String str2) {
            if (!g.this.g) {
                g.this.g = true;
                this.b = str;
                this.c = str2;
                c(k.f);
            }
        }

        public void a(boolean z) {
            JSONObject jSONObject = null;
            if (z && this.j != null) {
                try {
                    String str = this.j;
                    if (this.d) {
                        JSONObject jSONObject2 = new JSONObject(str);
                        if (jSONObject2.has(UriUtil.LOCAL_CONTENT_SCHEME)) {
                            jSONObject = jSONObject2.getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                        }
                        if (jSONObject != null && jSONObject.has("imo")) {
                            Long valueOf = Long.valueOf(jSONObject.getJSONObject("imo").getString("mac"));
                            int i = jSONObject.getJSONObject("imo").getInt("mv");
                            if (Jni.encode3(this.b).longValue() == valueOf.longValue()) {
                                ContentValues contentValues = new ContentValues();
                                contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                                contentValues.put("hst", Integer.valueOf(i));
                                try {
                                    if (g.this.f.update("hstdata", contentValues, "id = \"" + valueOf + "\"", null) <= 0) {
                                        contentValues.put("id", valueOf);
                                        g.this.f.insert("hstdata", null, contentValues);
                                    }
                                } catch (Exception e) {
                                }
                                Bundle bundle = new Bundle();
                                bundle.putByteArray("mac", this.b.getBytes());
                                bundle.putInt("hotspot", i);
                                g.this.a(bundle);
                            }
                        }
                    }
                } catch (Exception e2) {
                }
            } else if (this.d) {
                g.this.f();
            }
            if (this.k != null) {
                this.k.clear();
            }
            g.this.g = false;
        }
    }

    public static g a() {
        g gVar;
        synchronized (c) {
            if (d == null) {
                d = new g();
            }
            gVar = d;
        }
        return gVar;
    }

    private String a(boolean z) {
        com.baidu.location.f.a f = b.a().f();
        com.baidu.location.f.f p = com.baidu.location.f.g.a().p();
        StringBuffer stringBuffer = new StringBuffer(1024);
        if (f != null && f.b()) {
            stringBuffer.append(f.h());
        }
        if (p != null && p.a() > 1) {
            stringBuffer.append(p.a(15));
        } else if (com.baidu.location.f.g.a().m() != null) {
            stringBuffer.append(com.baidu.location.f.g.a().m());
        }
        if (z) {
            stringBuffer.append("&imo=1");
        }
        stringBuffer.append(com.baidu.location.h.b.a().a(false));
        stringBuffer.append(a.a().e());
        return stringBuffer.toString();
    }

    private void a(Bundle bundle) {
        a.a().a(bundle, 406);
    }

    private void f() {
        Bundle bundle = new Bundle();
        bundle.putInt("hotspot", -1);
        a(bundle);
    }

    public void a(String str) {
        JSONObject jSONObject = null;
        if (!this.g) {
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                if (jSONObject2.has(UriUtil.LOCAL_CONTENT_SCHEME)) {
                    jSONObject = jSONObject2.getJSONObject(UriUtil.LOCAL_CONTENT_SCHEME);
                }
                if (jSONObject != null && jSONObject.has("imo")) {
                    Long valueOf = Long.valueOf(jSONObject.getJSONObject("imo").getString("mac"));
                    int i = jSONObject.getJSONObject("imo").getInt("mv");
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("tt", Integer.valueOf((int) (System.currentTimeMillis() / 1000)));
                    contentValues.put("hst", Integer.valueOf(i));
                    try {
                        if (this.f.update("hstdata", contentValues, "id = \"" + valueOf + "\"", null) <= 0) {
                            contentValues.put("id", valueOf);
                            this.f.insert("hstdata", null, contentValues);
                        }
                    } catch (Exception e) {
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public void b() {
        try {
            File file = new File(e);
            if (!file.exists()) {
                file.createNewFile();
            }
            if (file.exists()) {
                this.f = SQLiteDatabase.openOrCreateDatabase(file, null);
                this.f.execSQL("CREATE TABLE IF NOT EXISTS hstdata(id Long PRIMARY KEY,hst INT,tt INT);");
                this.f.setVersion(1);
            }
        } catch (Exception e) {
            this.f = null;
        }
    }

    public void c() {
        if (this.f != null) {
            try {
                this.f.close();
            } catch (Exception e) {
            } finally {
                this.f = null;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:21:0x0064 A:{SYNTHETIC, Splitter: B:21:0x0064} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x006c A:{Splitter: B:13:0x0033, ExcHandler: java.lang.Exception (e java.lang.Exception)} */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x007b A:{SYNTHETIC, Splitter: B:33:0x007b} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:26:0x006d, code:
            if (r1 != null) goto L_0x006f;
     */
    /* JADX WARNING: Missing block: B:28:?, code:
            r1.close();
     */
    /* JADX WARNING: Missing block: B:34:?, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:39:0x0083, code:
            r2 = move-exception;
     */
    /* JADX WARNING: Missing block: B:40:0x0084, code:
            r6 = r2;
            r2 = r1;
            r1 = r6;
     */
    public int d() {
        /*
        r7 = this;
        r1 = 0;
        r0 = -3;
        r2 = r7.g;
        if (r2 == 0) goto L_0x0007;
    L_0x0006:
        return r0;
    L_0x0007:
        r2 = com.baidu.location.f.g.j();	 Catch:{ Exception -> 0x007f }
        if (r2 == 0) goto L_0x0006;
    L_0x000d:
        r2 = r7.f;	 Catch:{ Exception -> 0x007f }
        if (r2 == 0) goto L_0x0006;
    L_0x0011:
        r2 = com.baidu.location.f.g.a();	 Catch:{ Exception -> 0x007f }
        r2 = r2.l();	 Catch:{ Exception -> 0x007f }
        if (r2 == 0) goto L_0x0006;
    L_0x001b:
        r3 = r2.getBSSID();	 Catch:{ Exception -> 0x007f }
        if (r3 == 0) goto L_0x0006;
    L_0x0021:
        r2 = r2.getBSSID();	 Catch:{ Exception -> 0x007f }
        r3 = ":";
        r4 = "";
        r2 = r2.replace(r3, r4);	 Catch:{ Exception -> 0x007f }
        r2 = com.baidu.location.Jni.encode3(r2);	 Catch:{ Exception -> 0x007f }
        r3 = r7.f;	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r4.<init>();	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r5 = "select * from hstdata where id = \"";
        r4 = r4.append(r5);	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r2 = r4.append(r2);	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r4 = "\";";
        r2 = r2.append(r4);	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        r4 = 0;
        r1 = r3.rawQuery(r2, r4);	 Catch:{ Exception -> 0x006c, all -> 0x0075 }
        if (r1 == 0) goto L_0x006a;
    L_0x0057:
        r2 = r1.moveToFirst();	 Catch:{ Exception -> 0x006c, all -> 0x0083 }
        if (r2 == 0) goto L_0x006a;
    L_0x005d:
        r2 = 1;
        r0 = r1.getInt(r2);	 Catch:{ Exception -> 0x006c, all -> 0x0083 }
    L_0x0062:
        if (r1 == 0) goto L_0x0006;
    L_0x0064:
        r1.close();	 Catch:{ Exception -> 0x0068 }
        goto L_0x0006;
    L_0x0068:
        r1 = move-exception;
        goto L_0x0006;
    L_0x006a:
        r0 = -2;
        goto L_0x0062;
    L_0x006c:
        r2 = move-exception;
        if (r1 == 0) goto L_0x0006;
    L_0x006f:
        r1.close();	 Catch:{ Exception -> 0x0073 }
        goto L_0x0006;
    L_0x0073:
        r1 = move-exception;
        goto L_0x0006;
    L_0x0075:
        r2 = move-exception;
        r6 = r2;
        r2 = r1;
        r1 = r6;
    L_0x0079:
        if (r2 == 0) goto L_0x007e;
    L_0x007b:
        r2.close();	 Catch:{ Exception -> 0x0081 }
    L_0x007e:
        throw r1;	 Catch:{ Exception -> 0x007f }
    L_0x007f:
        r1 = move-exception;
        goto L_0x0006;
    L_0x0081:
        r2 = move-exception;
        goto L_0x007e;
    L_0x0083:
        r2 = move-exception;
        r6 = r2;
        r2 = r1;
        r1 = r6;
        goto L_0x0079;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.g.d():int");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x007b A:{SYNTHETIC, Splitter: B:24:0x007b} */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0080 A:{SYNTHETIC, Splitter: B:27:0x0080} */
    /* JADX WARNING: Removed duplicated region for block: B:45:0x00c4 A:{Splitter: B:13:0x0034, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x0080 A:{SYNTHETIC, Splitter: B:27:0x0080} */
    /* JADX WARNING: Removed duplicated region for block: B:58:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Missing block: B:46:0x00c5, code:
            if (r2 != null) goto L_0x00c7;
     */
    /* JADX WARNING: Missing block: B:48:?, code:
            r2.close();
     */
    public void e() {
        /*
        r10 = this;
        r2 = 0;
        r0 = 1;
        r1 = r10.g;
        if (r1 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r1 = com.baidu.location.f.g.j();	 Catch:{ Exception -> 0x009b }
        if (r1 == 0) goto L_0x00d0;
    L_0x000d:
        r1 = r10.f;	 Catch:{ Exception -> 0x009b }
        if (r1 == 0) goto L_0x00d0;
    L_0x0011:
        r1 = com.baidu.location.f.g.a();	 Catch:{ Exception -> 0x009b }
        r1 = r1.l();	 Catch:{ Exception -> 0x009b }
        if (r1 == 0) goto L_0x00cb;
    L_0x001b:
        r3 = r1.getBSSID();	 Catch:{ Exception -> 0x009b }
        if (r3 == 0) goto L_0x00cb;
    L_0x0021:
        r1 = r1.getBSSID();	 Catch:{ Exception -> 0x009b }
        r3 = ":";
        r4 = "";
        r3 = r1.replace(r3, r4);	 Catch:{ Exception -> 0x009b }
        r4 = com.baidu.location.Jni.encode3(r3);	 Catch:{ Exception -> 0x009b }
        r1 = 0;
        r5 = r10.f;	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r6.<init>();	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r7 = "select * from hstdata where id = \"";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r4 = r6.append(r4);	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r6 = "\";";
        r4 = r4.append(r6);	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r4 = r4.toString();	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        r6 = 0;
        r2 = r5.rawQuery(r4, r6);	 Catch:{ Exception -> 0x00ba, all -> 0x00c4 }
        if (r2 == 0) goto L_0x00b8;
    L_0x0058:
        r4 = r2.moveToFirst();	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        if (r4 == 0) goto L_0x00b8;
    L_0x005e:
        r4 = 1;
        r4 = r2.getInt(r4);	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r5 = 2;
        r5 = r2.getInt(r5);	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r6 = r6 / r8;
        r8 = (long) r5;
        r6 = r6 - r8;
        r8 = 259200; // 0x3f480 float:3.63217E-40 double:1.28062E-318;
        r5 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r5 <= 0) goto L_0x009e;
    L_0x0078:
        r1 = r0;
    L_0x0079:
        if (r2 == 0) goto L_0x007e;
    L_0x007b:
        r2.close();	 Catch:{ Exception -> 0x00d5 }
    L_0x007e:
        if (r1 == 0) goto L_0x0006;
    L_0x0080:
        r0 = r10.a;	 Catch:{ Exception -> 0x009b }
        if (r0 != 0) goto L_0x008b;
    L_0x0084:
        r0 = new com.baidu.location.a.g$a;	 Catch:{ Exception -> 0x009b }
        r0.<init>();	 Catch:{ Exception -> 0x009b }
        r10.a = r0;	 Catch:{ Exception -> 0x009b }
    L_0x008b:
        r0 = r10.a;	 Catch:{ Exception -> 0x009b }
        if (r0 == 0) goto L_0x0006;
    L_0x008f:
        r0 = r10.a;	 Catch:{ Exception -> 0x009b }
        r1 = 1;
        r1 = r10.a(r1);	 Catch:{ Exception -> 0x009b }
        r0.a(r3, r1);	 Catch:{ Exception -> 0x009b }
        goto L_0x0006;
    L_0x009b:
        r0 = move-exception;
        goto L_0x0006;
    L_0x009e:
        r0 = new android.os.Bundle;	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r0.<init>();	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r5 = "mac";
        r6 = r3.getBytes();	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r0.putByteArray(r5, r6);	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r5 = "hotspot";
        r0.putInt(r5, r4);	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r10.a(r0);	 Catch:{ Exception -> 0x00d9, all -> 0x00c4 }
        r0 = r1;
        goto L_0x0078;
    L_0x00b8:
        r1 = r0;
        goto L_0x0079;
    L_0x00ba:
        r0 = move-exception;
        r0 = r2;
    L_0x00bc:
        if (r0 == 0) goto L_0x007e;
    L_0x00be:
        r0.close();	 Catch:{ Exception -> 0x00c2 }
        goto L_0x007e;
    L_0x00c2:
        r0 = move-exception;
        goto L_0x007e;
    L_0x00c4:
        r0 = move-exception;
        if (r2 == 0) goto L_0x00ca;
    L_0x00c7:
        r2.close();	 Catch:{ Exception -> 0x00d7 }
    L_0x00ca:
        throw r0;	 Catch:{ Exception -> 0x009b }
    L_0x00cb:
        r10.f();	 Catch:{ Exception -> 0x009b }
        goto L_0x0006;
    L_0x00d0:
        r10.f();	 Catch:{ Exception -> 0x009b }
        goto L_0x0006;
    L_0x00d5:
        r0 = move-exception;
        goto L_0x007e;
    L_0x00d7:
        r1 = move-exception;
        goto L_0x00ca;
    L_0x00d9:
        r0 = move-exception;
        r0 = r2;
        goto L_0x00bc;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.a.g.e():void");
    }
}
