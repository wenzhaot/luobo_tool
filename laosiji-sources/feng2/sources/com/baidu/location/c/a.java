package com.baidu.location.c;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import anet.channel.request.Request;
import com.baidu.android.bbalbs.common.a.b;
import com.baidu.android.bbalbs.common.a.c;
import com.baidu.location.h.f;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class a extends f {
    private static HashMap<String, Long> a = new HashMap();
    private final String b = "http://loc.map.baidu.com/indoorlocbuildinginfo.php";
    private final SimpleDateFormat c = new SimpleDateFormat("yyyyMM");
    private Context d;
    private boolean e;
    private String f;
    private HashSet<String> p;
    private a q;
    private String r = null;
    private Handler s;
    private Runnable t;

    public interface a {
        void a(boolean z);
    }

    public a(Context context) {
        this.d = context;
        this.p = new HashSet();
        this.e = false;
        this.k = new HashMap();
        this.s = new Handler();
        this.t = new b(this);
    }

    private String a(Date date) {
        File file = new File(this.d.getCacheDir(), c.a((this.f + this.c.format(date)).getBytes(), false));
        if (!file.isFile()) {
            return null;
        }
        try {
            String str;
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            String str2 = "";
            while (true) {
                str = str2;
                str2 = bufferedReader.readLine();
                if (str2 == null) {
                    break;
                }
                str2 = str + str2 + "\n";
            }
            bufferedReader.close();
            return !str.equals("") ? new String(b.a(str.getBytes())) : null;
        } catch (Exception e) {
            return null;
        }
    }

    private void d(String str) {
        for (String toLowerCase : str.split(",")) {
            this.p.add(toLowerCase.toLowerCase());
        }
    }

    private void e(String str) {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.d.getCacheDir(), c.a((this.f + this.c.format(new Date())).getBytes(), false)));
            fileWriter.write(b.a(str.getBytes(), Request.DEFAULT_CHARSET));
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
        }
    }

    private Date f() {
        Calendar instance = Calendar.getInstance();
        instance.add(2, -1);
        return instance.getTime();
    }

    private void f(String str) {
        try {
            FileWriter fileWriter = new FileWriter(new File(this.d.getCacheDir(), "buildings"), true);
            fileWriter.write(str + "\n");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void g() {
        try {
            File file = new File(this.d.getCacheDir(), c.a((this.f + this.c.format(f())).getBytes(), false));
            if (file.isFile()) {
                file.delete();
            }
        } catch (Exception e) {
        }
    }

    public void a() {
        this.h = "http://loc.map.baidu.com/indoorlocbuildinginfo.php";
        this.k.clear();
        this.k.put("bid", "none");
        this.k.put("bldg", this.f);
        this.k.put("mb", Build.MODEL);
        this.k.put("msdk", "2.0");
        this.k.put("cuid", com.baidu.location.h.b.a().b);
        this.k.put("anchors", "v1");
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x0070  */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x006d  */
    /* JADX WARNING: Removed duplicated region for block: B:35:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:21:0x0065  */
    public void a(boolean r7) {
        /*
        r6 = this;
        r4 = 0;
        r1 = 1;
        r2 = 0;
        if (r7 == 0) goto L_0x0094;
    L_0x0005:
        r0 = r6.j;
        if (r0 == 0) goto L_0x0094;
    L_0x0009:
        r0 = r6.j;	 Catch:{ Exception -> 0x008e }
        r3 = new java.lang.String;	 Catch:{ Exception -> 0x008e }
        r0 = r0.toString();	 Catch:{ Exception -> 0x008e }
        r0 = r0.getBytes();	 Catch:{ Exception -> 0x008e }
        r0 = com.baidu.android.bbalbs.common.a.b.a(r0);	 Catch:{ Exception -> 0x008e }
        r3.<init>(r0);	 Catch:{ Exception -> 0x008e }
        r0 = new org.json.JSONObject;	 Catch:{ Exception -> 0x008e }
        r0.<init>(r3);	 Catch:{ Exception -> 0x008e }
        r3 = "anchorinfo";
        r3 = r0.has(r3);	 Catch:{ Exception -> 0x008e }
        if (r3 == 0) goto L_0x0094;
    L_0x002a:
        r3 = "anchorinfo";
        r0 = r0.optString(r3);	 Catch:{ Exception -> 0x008e }
        if (r0 == 0) goto L_0x0094;
    L_0x0033:
        r3 = "";
        r3 = r0.equals(r3);	 Catch:{ Exception -> 0x008e }
        if (r3 != 0) goto L_0x0094;
    L_0x003c:
        r3 = r6.p;	 Catch:{ Exception -> 0x008e }
        r3.clear();	 Catch:{ Exception -> 0x008e }
        r6.d(r0);	 Catch:{ Exception -> 0x008e }
        r6.e(r0);	 Catch:{ Exception -> 0x008e }
        r6.g();	 Catch:{ Exception -> 0x0091 }
        r0 = r1;
    L_0x004b:
        if (r0 != 0) goto L_0x006b;
    L_0x004d:
        r3 = r6.r;
        if (r3 != 0) goto L_0x006b;
    L_0x0051:
        r1 = r6.f;
        r6.r = r1;
        r1 = r6.s;
        r3 = r6.t;
        r4 = 60000; // 0xea60 float:8.4078E-41 double:2.9644E-319;
        r1.postDelayed(r3, r4);
    L_0x005f:
        r6.e = r2;
        r1 = r6.q;
        if (r1 == 0) goto L_0x006a;
    L_0x0065:
        r1 = r6.q;
        r1.a(r0);
    L_0x006a:
        return;
    L_0x006b:
        if (r0 == 0) goto L_0x0070;
    L_0x006d:
        r6.r = r4;
        goto L_0x005f;
    L_0x0070:
        r3 = r6.r;
        r6.f(r3);
        r6.r = r4;
        r3 = r6.f();
        r3 = r6.a(r3);
        if (r3 == 0) goto L_0x005f;
    L_0x0081:
        r6.d(r3);
        r3 = r6.q;
        if (r3 == 0) goto L_0x005f;
    L_0x0088:
        r3 = r6.q;
        r3.a(r1);
        goto L_0x005f;
    L_0x008e:
        r0 = move-exception;
        r0 = r2;
        goto L_0x004b;
    L_0x0091:
        r0 = move-exception;
        r0 = r1;
        goto L_0x004b;
    L_0x0094:
        r0 = r2;
        goto L_0x004b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.location.c.a.a(boolean):void");
    }

    public boolean a(String str) {
        return (this.f == null || !this.f.equalsIgnoreCase(str) || this.p.isEmpty()) ? false : true;
    }

    public boolean a(String str, a aVar) {
        if (!this.e) {
            this.q = aVar;
            this.e = true;
            this.f = str;
            try {
                String a = a(new Date());
                if (a == null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    if (a.get(str) == null || currentTimeMillis - ((Long) a.get(str)).longValue() > 86400000) {
                        a.put(str, Long.valueOf(currentTimeMillis));
                        e();
                    }
                } else {
                    d(a);
                    if (this.q != null) {
                        this.q.a(true);
                    }
                    this.e = false;
                }
            } catch (Exception e) {
                this.e = false;
            }
        }
        return false;
    }

    public boolean b() {
        return (this.p == null || this.p.isEmpty()) ? false : true;
    }

    public boolean b(String str) {
        return (this.f == null || this.p == null || this.p.isEmpty() || !this.p.contains(str)) ? false : true;
    }

    public void c() {
        this.f = null;
        this.p.clear();
    }
}
