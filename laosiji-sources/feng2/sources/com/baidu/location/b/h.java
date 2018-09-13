package com.baidu.location.b;

import android.net.wifi.WifiConfiguration;
import android.os.Handler;
import anet.channel.request.Request;
import com.baidu.location.Jni;
import com.baidu.location.h.f;
import com.baidu.location.h.k;
import com.facebook.common.util.UriUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

public class h {
    private static Object a = new Object();
    private static h b = null;
    private Handler c = null;
    private String d = null;
    private int e = 24;
    private a f = null;
    private long g = 0;

    private class a extends f {
        private boolean b;
        private int c;
        private JSONArray d;
        private JSONArray e;

        a() {
            this.b = false;
            this.c = 0;
            this.d = null;
            this.e = null;
            this.k = new HashMap();
        }

        public void a() {
            this.h = k.e();
            this.k.clear();
            this.k.put("qt", "cltrw");
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(UriUtil.DATA_SCHEME, this.d);
                jSONObject.put("frt", this.c);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.k.put("cltr[0]", "" + Jni.encodeOfflineLocationUpdateRequest(jSONObject.toString()));
            this.k.put("cfg", Integer.valueOf(1));
            this.k.put("info", Jni.encode(com.baidu.location.h.b.a().c()));
            this.k.put("trtm", String.format(Locale.CHINA, "%d", new Object[]{Long.valueOf(System.currentTimeMillis())}));
        }

        public void a(boolean z) {
            if (z && this.j != null) {
                JSONObject jSONObject;
                boolean z2;
                try {
                    jSONObject = new JSONObject(this.j);
                    z2 = true;
                } catch (Exception e) {
                    jSONObject = null;
                    z2 = false;
                }
                if (z2 && jSONObject != null) {
                    try {
                        jSONObject.put("tt", System.currentTimeMillis());
                        jSONObject.put(UriUtil.DATA_SCHEME, this.e);
                        try {
                            File file = new File(h.this.d, "wcnf.dat");
                            if (!file.exists()) {
                                file.createNewFile();
                            }
                            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                            bufferedWriter.write(com.baidu.android.bbalbs.common.a.b.a(jSONObject.toString().getBytes(), Request.DEFAULT_CHARSET));
                            bufferedWriter.flush();
                            bufferedWriter.close();
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                    } catch (Exception e3) {
                    }
                }
            }
            this.b = false;
        }

        public void a(boolean z, JSONArray jSONArray, JSONArray jSONArray2) {
            if (!this.b) {
                this.b = true;
                if (z) {
                    this.c = 1;
                } else {
                    this.c = 0;
                }
                this.d = jSONArray;
                this.e = jSONArray2;
                c(k.e());
            }
        }
    }

    private class b {
        public String a = null;
        public int b = 0;

        b(String str, int i) {
            this.a = str;
            this.b = i;
        }
    }

    public static h a() {
        h hVar;
        synchronized (a) {
            if (b == null) {
                b = new h();
            }
            hVar = b;
        }
        return hVar;
    }

    private Object a(Object obj, String str) throws Exception {
        return obj.getClass().getField(str).get(obj);
    }

    private List<b> a(List<WifiConfiguration> list) {
        if (list == null || list.size() <= 0) {
            return null;
        }
        List<b> arrayList = new ArrayList();
        for (WifiConfiguration wifiConfiguration : list) {
            int intValue;
            String str = wifiConfiguration.SSID;
            try {
                intValue = ((Integer) a(wifiConfiguration, "numAssociation")).intValue();
            } catch (Exception e) {
                e.printStackTrace();
                intValue = 0;
            }
            if (intValue > 0 && str != null) {
                arrayList.add(new b(str, intValue));
            }
        }
        return arrayList;
    }

    private void a(boolean z, JSONArray jSONArray, JSONArray jSONArray2) {
        if (this.f == null) {
            this.f = new a();
        }
        this.f.a(z, jSONArray, jSONArray2);
    }

    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041 A:{Catch:{ Exception -> 0x00ac }} */
    /* JADX WARNING: Removed duplicated region for block: B:111:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0059 A:{Catch:{ Exception -> 0x00ac }} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041 A:{Catch:{ Exception -> 0x00ac }} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0059 A:{Catch:{ Exception -> 0x00ac }} */
    /* JADX WARNING: Removed duplicated region for block: B:111:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0041 A:{Catch:{ Exception -> 0x00ac }} */
    /* JADX WARNING: Removed duplicated region for block: B:111:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0059 A:{Catch:{ Exception -> 0x00ac }} */
    private void d() {
        /*
        r12 = this;
        r0 = r12.d;
        if (r0 == 0) goto L_0x00b0;
    L_0x0004:
        r2 = new java.io.File;	 Catch:{ Exception -> 0x00ac }
        r0 = r12.d;	 Catch:{ Exception -> 0x00ac }
        r1 = "wcnf.dat";
        r2.<init>(r0, r1);	 Catch:{ Exception -> 0x00ac }
        r4 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ac }
        r0 = 1;
        r1 = 0;
        r3 = r2.exists();	 Catch:{ Exception -> 0x00ac }
        if (r3 == 0) goto L_0x016a;
    L_0x001a:
        r3 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x0036 }
        r6 = new java.io.FileReader;	 Catch:{ Exception -> 0x0036 }
        r6.<init>(r2);	 Catch:{ Exception -> 0x0036 }
        r3.<init>(r6);	 Catch:{ Exception -> 0x0036 }
        r2 = "";
        r2 = new java.lang.StringBuffer;	 Catch:{ Exception -> 0x0036 }
        r2.<init>();	 Catch:{ Exception -> 0x0036 }
    L_0x002c:
        r6 = r3.readLine();	 Catch:{ Exception -> 0x0036 }
        if (r6 == 0) goto L_0x00b1;
    L_0x0032:
        r2.append(r6);	 Catch:{ Exception -> 0x0036 }
        goto L_0x002c;
    L_0x0036:
        r2 = move-exception;
        r11 = r2;
        r2 = r0;
        r0 = r11;
    L_0x003a:
        r0.printStackTrace();	 Catch:{ Exception -> 0x00ac }
        r6 = r1;
        r1 = r2;
    L_0x003f:
        if (r1 != 0) goto L_0x0047;
    L_0x0041:
        r0 = r12.e;	 Catch:{ Exception -> 0x00ac }
        r0 = r0 * 4;
        r12.e = r0;	 Catch:{ Exception -> 0x00ac }
    L_0x0047:
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x00ac }
        r0 = r0 - r4;
        r2 = r12.e;	 Catch:{ Exception -> 0x00ac }
        r2 = r2 * 60;
        r2 = r2 * 60;
        r2 = r2 * 1000;
        r2 = (long) r2;	 Catch:{ Exception -> 0x00ac }
        r0 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r0 <= 0) goto L_0x00b0;
    L_0x0059:
        r2 = 0;
        r0 = 0;
        r1 = com.baidu.location.f.g.a();	 Catch:{ Exception -> 0x00ac }
        r1 = r1.d();	 Catch:{ Exception -> 0x00ac }
        r7 = r12.a(r1);	 Catch:{ Exception -> 0x00ac }
        r3 = 0;
        r8 = 0;
        r1 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r1 != 0) goto L_0x017b;
    L_0x006e:
        if (r7 == 0) goto L_0x0209;
    L_0x0070:
        r1 = r7.size();	 Catch:{ Exception -> 0x00ac }
        if (r1 <= 0) goto L_0x0209;
    L_0x0076:
        r2 = new org.json.JSONArray;	 Catch:{ Exception -> 0x00ac }
        r2.<init>();	 Catch:{ Exception -> 0x00ac }
        r1 = new org.json.JSONArray;	 Catch:{ Exception -> 0x00ac }
        r1.<init>();	 Catch:{ Exception -> 0x00ac }
        r3 = r7.iterator();	 Catch:{ Exception -> 0x00ac }
    L_0x0084:
        r0 = r3.hasNext();	 Catch:{ Exception -> 0x00ac }
        if (r0 == 0) goto L_0x0171;
    L_0x008a:
        r0 = r3.next();	 Catch:{ Exception -> 0x00ac }
        r0 = (com.baidu.location.b.h.b) r0;	 Catch:{ Exception -> 0x00ac }
        r4 = new org.json.JSONObject;	 Catch:{ Exception -> 0x00ac }
        r4.<init>();	 Catch:{ Exception -> 0x00ac }
        r5 = "ssid";
        r6 = r0.a;	 Catch:{ Exception -> 0x00ac }
        r4.put(r5, r6);	 Catch:{ Exception -> 0x00ac }
        r5 = "num";
        r0 = r0.b;	 Catch:{ Exception -> 0x00ac }
        r4.put(r5, r0);	 Catch:{ Exception -> 0x00ac }
        r2.put(r4);	 Catch:{ Exception -> 0x00ac }
        r1.put(r4);	 Catch:{ Exception -> 0x00ac }
        goto L_0x0084;
    L_0x00ac:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x00b0:
        return;
    L_0x00b1:
        r3.close();	 Catch:{ Exception -> 0x0036 }
        r2 = r2.toString();	 Catch:{ Exception -> 0x0036 }
        if (r2 == 0) goto L_0x0215;
    L_0x00ba:
        r3 = new java.lang.String;	 Catch:{ Exception -> 0x0036 }
        r2 = r2.getBytes();	 Catch:{ Exception -> 0x0036 }
        r2 = com.baidu.android.bbalbs.common.a.b.a(r2);	 Catch:{ Exception -> 0x0036 }
        r3.<init>(r2);	 Catch:{ Exception -> 0x0036 }
        r6 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0036 }
        r6.<init>(r3);	 Catch:{ Exception -> 0x0036 }
        r2 = "ison";
        r2 = r6.has(r2);	 Catch:{ Exception -> 0x0036 }
        if (r2 == 0) goto L_0x0212;
    L_0x00d5:
        r2 = "ison";
        r2 = r6.getInt(r2);	 Catch:{ Exception -> 0x0036 }
        if (r2 != 0) goto L_0x0212;
    L_0x00de:
        r0 = 0;
        r2 = r0;
    L_0x00e0:
        r0 = "cfg";
        r0 = r6.has(r0);	 Catch:{ Exception -> 0x01f6 }
        if (r0 == 0) goto L_0x0102;
    L_0x00e9:
        r0 = "cfg";
        r0 = r6.getJSONObject(r0);	 Catch:{ Exception -> 0x01f6 }
        r3 = "frq";
        r3 = r0.has(r3);	 Catch:{ Exception -> 0x01f6 }
        if (r3 == 0) goto L_0x0102;
    L_0x00f9:
        r3 = "frq";
        r0 = r0.getInt(r3);	 Catch:{ Exception -> 0x01f6 }
        r12.e = r0;	 Catch:{ Exception -> 0x01f6 }
    L_0x0102:
        r0 = "tt";
        r0 = r6.has(r0);	 Catch:{ Exception -> 0x01f6 }
        if (r0 == 0) goto L_0x0112;
    L_0x010b:
        r0 = "tt";
        r4 = r6.getLong(r0);	 Catch:{ Exception -> 0x01f6 }
    L_0x0112:
        r0 = "data";
        r0 = r6.has(r0);	 Catch:{ Exception -> 0x01f6 }
        if (r0 == 0) goto L_0x020d;
    L_0x011b:
        r0 = "data";
        r3 = r6.getJSONArray(r0);	 Catch:{ Exception -> 0x01f6 }
        r0 = new java.util.HashMap;	 Catch:{ Exception -> 0x01f6 }
        r0.<init>();	 Catch:{ Exception -> 0x01f6 }
        r6 = r3.length();	 Catch:{ Exception -> 0x01f9 }
        r1 = 0;
    L_0x012c:
        if (r1 >= r6) goto L_0x0164;
    L_0x012e:
        r7 = r3.getJSONObject(r1);	 Catch:{ Exception -> 0x01f9 }
        r8 = "ssid";
        r8 = r7.has(r8);	 Catch:{ Exception -> 0x01f9 }
        if (r8 == 0) goto L_0x0161;
    L_0x013b:
        r8 = "num";
        r8 = r7.has(r8);	 Catch:{ Exception -> 0x01f9 }
        if (r8 == 0) goto L_0x0161;
    L_0x0144:
        r8 = new com.baidu.location.b.h$b;	 Catch:{ Exception -> 0x01f9 }
        r9 = "ssid";
        r9 = r7.getString(r9);	 Catch:{ Exception -> 0x01f9 }
        r10 = "num";
        r10 = r7.getInt(r10);	 Catch:{ Exception -> 0x01f9 }
        r8.<init>(r9, r10);	 Catch:{ Exception -> 0x01f9 }
        r9 = "ssid";
        r7 = r7.getString(r9);	 Catch:{ Exception -> 0x01f9 }
        r0.put(r7, r8);	 Catch:{ Exception -> 0x01f9 }
    L_0x0161:
        r1 = r1 + 1;
        goto L_0x012c;
    L_0x0164:
        r1 = r2;
        r2 = r4;
    L_0x0166:
        r6 = r0;
        r4 = r2;
        goto L_0x003f;
    L_0x016a:
        r2 = 0;
        r6 = r1;
        r4 = r2;
        r1 = r0;
        goto L_0x003f;
    L_0x0171:
        r0 = 1;
    L_0x0172:
        if (r2 == 0) goto L_0x00b0;
    L_0x0174:
        if (r1 == 0) goto L_0x00b0;
    L_0x0176:
        r12.a(r0, r2, r1);	 Catch:{ Exception -> 0x00ac }
        goto L_0x00b0;
    L_0x017b:
        if (r7 == 0) goto L_0x0209;
    L_0x017d:
        r1 = r7.size();	 Catch:{ Exception -> 0x00ac }
        if (r1 <= 0) goto L_0x0209;
    L_0x0183:
        r4 = new org.json.JSONArray;	 Catch:{ Exception -> 0x00ac }
        r4.<init>();	 Catch:{ Exception -> 0x00ac }
        if (r6 == 0) goto L_0x0205;
    L_0x018a:
        r0 = r6.size();	 Catch:{ Exception -> 0x00ac }
        if (r0 <= 0) goto L_0x0205;
    L_0x0190:
        r7 = r7.iterator();	 Catch:{ Exception -> 0x00ac }
    L_0x0194:
        r0 = r7.hasNext();	 Catch:{ Exception -> 0x00ac }
        if (r0 == 0) goto L_0x0205;
    L_0x019a:
        r0 = r7.next();	 Catch:{ Exception -> 0x00ac }
        r0 = (com.baidu.location.b.h.b) r0;	 Catch:{ Exception -> 0x00ac }
        r1 = new org.json.JSONObject;	 Catch:{ Exception -> 0x00ac }
        r1.<init>();	 Catch:{ Exception -> 0x00ac }
        r5 = "ssid";
        r8 = r0.a;	 Catch:{ Exception -> 0x00ac }
        r1.put(r5, r8);	 Catch:{ Exception -> 0x00ac }
        r5 = "num";
        r8 = r0.b;	 Catch:{ Exception -> 0x00ac }
        r1.put(r5, r8);	 Catch:{ Exception -> 0x00ac }
        r4.put(r1);	 Catch:{ Exception -> 0x00ac }
        r5 = 0;
        r1 = r0.a;	 Catch:{ Exception -> 0x00ac }
        r1 = r6.containsKey(r1);	 Catch:{ Exception -> 0x00ac }
        if (r1 == 0) goto L_0x01f4;
    L_0x01c1:
        r8 = r0.b;	 Catch:{ Exception -> 0x00ac }
        r1 = r0.a;	 Catch:{ Exception -> 0x00ac }
        r1 = r6.get(r1);	 Catch:{ Exception -> 0x00ac }
        r1 = (com.baidu.location.b.h.b) r1;	 Catch:{ Exception -> 0x00ac }
        r1 = r1.b;	 Catch:{ Exception -> 0x00ac }
        if (r8 == r1) goto L_0x0203;
    L_0x01cf:
        r1 = 1;
    L_0x01d0:
        if (r1 == 0) goto L_0x0201;
    L_0x01d2:
        if (r2 != 0) goto L_0x01ff;
    L_0x01d4:
        r1 = new org.json.JSONArray;	 Catch:{ Exception -> 0x00ac }
        r1.<init>();	 Catch:{ Exception -> 0x00ac }
    L_0x01d9:
        r2 = new org.json.JSONObject;	 Catch:{ Exception -> 0x00ac }
        r2.<init>();	 Catch:{ Exception -> 0x00ac }
        r5 = "ssid";
        r8 = r0.a;	 Catch:{ Exception -> 0x00ac }
        r2.put(r5, r8);	 Catch:{ Exception -> 0x00ac }
        r5 = "num";
        r0 = r0.b;	 Catch:{ Exception -> 0x00ac }
        r2.put(r5, r0);	 Catch:{ Exception -> 0x00ac }
        r1.put(r2);	 Catch:{ Exception -> 0x00ac }
        r0 = r1;
    L_0x01f2:
        r2 = r0;
        goto L_0x0194;
    L_0x01f4:
        r1 = 1;
        goto L_0x01d0;
    L_0x01f6:
        r0 = move-exception;
        goto L_0x003a;
    L_0x01f9:
        r1 = move-exception;
        r11 = r1;
        r1 = r0;
        r0 = r11;
        goto L_0x003a;
    L_0x01ff:
        r1 = r2;
        goto L_0x01d9;
    L_0x0201:
        r0 = r2;
        goto L_0x01f2;
    L_0x0203:
        r1 = r5;
        goto L_0x01d0;
    L_0x0205:
        r0 = r3;
        r1 = r4;
        goto L_0x0172;
    L_0x0209:
        r1 = r0;
        r0 = r3;
        goto L_0x0172;
    L_0x020d:
        r0 = r1;
        r1 = r2;
        r2 = r4;
        goto L_0x0166;
    L_0x0212:
        r2 = r0;
        goto L_0x00e0;
    L_0x0215:
        r2 = r4;
        r11 = r0;
        r0 = r1;
        r1 = r11;
        goto L_0x0166;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.b.h.d():void");
    }

    public void b() {
        if (this.c == null) {
            this.c = new i(this);
        }
        this.d = k.g();
    }

    public void c() {
        if (System.currentTimeMillis() - this.g > 3600000 && this.c != null) {
            this.c.sendEmptyMessage(1);
            this.g = System.currentTimeMillis();
        }
    }
}
