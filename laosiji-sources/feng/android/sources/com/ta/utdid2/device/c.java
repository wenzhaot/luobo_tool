package com.ta.utdid2.device;

import android.content.Context;
import android.provider.Settings.System;
import com.ta.utdid2.b.a.b;
import com.ta.utdid2.b.a.e;
import com.ta.utdid2.b.a.g;
import com.ta.utdid2.b.a.i;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Random;
import java.util.regex.Pattern;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: UTUtdid */
public class c {
    private static c a = null;
    private static final Object f = new Object();
    private static final String o = (".UTSystemConfig" + File.separator + "Global");
    /* renamed from: a */
    private com.ta.utdid2.c.a.c f20a = null;
    /* renamed from: a */
    private d f21a = null;
    /* renamed from: a */
    private Pattern f22a = Pattern.compile("[^0-9a-zA-Z=/+]+");
    private com.ta.utdid2.c.a.c b = null;
    private String l = null;
    private String m = "xx_utdid_key";
    private Context mContext = null;
    private String n = "xx_utdid_domain";

    public c(Context context) {
        this.mContext = context;
        this.b = new com.ta.utdid2.c.a.c(context, o, "Alvin2", false, true);
        this.a = new com.ta.utdid2.c.a.c(context, ".DataStorage", "ContextData", false, true);
        this.a = new d();
        this.m = String.format("K_%d", new Object[]{Integer.valueOf(i.a(this.m))});
        this.n = String.format("D_%d", new Object[]{Integer.valueOf(i.a(this.n))});
    }

    private void d() {
        Object obj = 1;
        if (this.b != null) {
            if (i.a(this.b.getString("UTDID2"))) {
                String string = this.b.getString("UTDID");
                if (!i.a(string)) {
                    f(string);
                }
            }
            Object obj2 = null;
            if (!i.a(this.b.getString("DID"))) {
                this.b.remove("DID");
                obj2 = 1;
            }
            if (!i.a(this.b.getString("EI"))) {
                this.b.remove("EI");
                obj2 = 1;
            }
            if (i.a(this.b.getString("SI"))) {
                obj = obj2;
            } else {
                this.b.remove("SI");
            }
            if (obj != null) {
                this.b.commit();
            }
        }
    }

    public static c a(Context context) {
        if (context != null && a == null) {
            synchronized (f) {
                if (a == null) {
                    a = new c(context);
                    a.d();
                }
            }
        }
        return a;
    }

    private void f(String str) {
        if (b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (str.length() == 24 && this.b != null) {
                this.b.putString("UTDID2", str);
                this.b.commit();
            }
        }
    }

    private void g(String str) {
        if (str != null && this.a != null && !str.equals(this.a.getString(this.m))) {
            this.a.putString(this.m, str);
            this.a.commit();
        }
    }

    private void h(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && b(str)) {
            if (str.endsWith("\n")) {
                str = str.substring(0, str.length() - 1);
            }
            if (24 == str.length()) {
                String str2 = null;
                try {
                    str2 = System.getString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk");
                } catch (Exception e) {
                }
                if (!b(str2)) {
                    try {
                        System.putString(this.mContext.getContentResolver(), "mqBRboGZkQPcAkyk", str);
                    } catch (Exception e2) {
                    }
                }
            }
        }
    }

    private void i(String str) {
        Object obj = null;
        try {
            obj = System.getString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp");
        } catch (Exception e) {
        }
        if (!str.equals(obj)) {
            try {
                System.putString(this.mContext.getContentResolver(), "dxCRMxhQkdGePGnp", str);
            } catch (Exception e2) {
            }
        }
    }

    private void j(String str) {
        if (this.mContext.checkCallingOrSelfPermission("android.permission.WRITE_SETTINGS") == 0 && str != null) {
            i(str);
        }
    }

    private String g() {
        if (this.b != null) {
            String string = this.b.getString("UTDID2");
            if (!(i.a(string) || this.a.a(string) == null)) {
                return string;
            }
        }
        return null;
    }

    private boolean b(String str) {
        if (str == null) {
            return false;
        }
        CharSequence str2;
        if (str2.endsWith("\n")) {
            str2 = str2.substring(0, str2.length() - 1);
        }
        if (24 != str2.length() || this.a.matcher(str2).find()) {
            return false;
        }
        return true;
    }

    public synchronized String getValue() {
        String str;
        if (this.l != null) {
            str = this.l;
        } else {
            str = h();
        }
        return str;
    }

    /* JADX WARNING: Removed duplicated region for block: B:38:0x0079 A:{Catch:{ Exception -> 0x0089 }} */
    public synchronized java.lang.String h() {
        /*
        r6 = this;
        r1 = 0;
        monitor-enter(r6);
        r0 = "";
        r2 = r6.mContext;	 Catch:{ Exception -> 0x011a }
        r2 = r2.getContentResolver();	 Catch:{ Exception -> 0x011a }
        r3 = "mqBRboGZkQPcAkyk";
        r0 = android.provider.Settings.System.getString(r2, r3);	 Catch:{ Exception -> 0x011a }
    L_0x0012:
        r2 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r2 == 0) goto L_0x001a;
    L_0x0018:
        monitor-exit(r6);
        return r0;
    L_0x001a:
        r4 = new com.ta.utdid2.device.e;	 Catch:{ all -> 0x0041 }
        r4.<init>();	 Catch:{ all -> 0x0041 }
        r2 = 0;
        r0 = r6.mContext;	 Catch:{ Exception -> 0x0044 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0044 }
        r3 = "dxCRMxhQkdGePGnp";
        r3 = android.provider.Settings.System.getString(r0, r3);	 Catch:{ Exception -> 0x0044 }
    L_0x002d:
        r0 = com.ta.utdid2.b.a.i.a(r3);	 Catch:{ all -> 0x0041 }
        if (r0 != 0) goto L_0x008c;
    L_0x0033:
        r0 = r4.c(r3);	 Catch:{ all -> 0x0041 }
        r5 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r5 == 0) goto L_0x0047;
    L_0x003d:
        r6.h(r0);	 Catch:{ all -> 0x0041 }
        goto L_0x0018;
    L_0x0041:
        r0 = move-exception;
        monitor-exit(r6);
        throw r0;
    L_0x0044:
        r0 = move-exception;
        r3 = r1;
        goto L_0x002d;
    L_0x0047:
        r0 = r4.b(r3);	 Catch:{ all -> 0x0041 }
        r5 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r5 == 0) goto L_0x011d;
    L_0x0051:
        r5 = r6.a;	 Catch:{ all -> 0x0041 }
        r0 = r5.a(r0);	 Catch:{ all -> 0x0041 }
        r5 = com.ta.utdid2.b.a.i.a(r0);	 Catch:{ all -> 0x0041 }
        if (r5 != 0) goto L_0x011d;
    L_0x005d:
        r6.j(r0);	 Catch:{ all -> 0x0041 }
        r0 = r6.mContext;	 Catch:{ Exception -> 0x0089 }
        r0 = r0.getContentResolver();	 Catch:{ Exception -> 0x0089 }
        r5 = "dxCRMxhQkdGePGnp";
        r0 = android.provider.Settings.System.getString(r0, r5);	 Catch:{ Exception -> 0x0089 }
    L_0x006d:
        r3 = r6.a;	 Catch:{ all -> 0x0041 }
        r3 = r3.b(r0);	 Catch:{ all -> 0x0041 }
        r5 = r6.b(r3);	 Catch:{ all -> 0x0041 }
        if (r5 == 0) goto L_0x008e;
    L_0x0079:
        r6.l = r3;	 Catch:{ all -> 0x0041 }
        r6.f(r3);	 Catch:{ all -> 0x0041 }
        r6.g(r0);	 Catch:{ all -> 0x0041 }
        r0 = r6.l;	 Catch:{ all -> 0x0041 }
        r6.h(r0);	 Catch:{ all -> 0x0041 }
        r0 = r6.l;	 Catch:{ all -> 0x0041 }
        goto L_0x0018;
    L_0x0089:
        r0 = move-exception;
        r0 = r3;
        goto L_0x006d;
    L_0x008c:
        r0 = 1;
        r2 = r0;
    L_0x008e:
        r0 = r6.g();	 Catch:{ all -> 0x0041 }
        r3 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r3 == 0) goto L_0x00ad;
    L_0x0098:
        r1 = r6.a;	 Catch:{ all -> 0x0041 }
        r1 = r1.a(r0);	 Catch:{ all -> 0x0041 }
        if (r2 == 0) goto L_0x00a3;
    L_0x00a0:
        r6.j(r1);	 Catch:{ all -> 0x0041 }
    L_0x00a3:
        r6.h(r0);	 Catch:{ all -> 0x0041 }
        r6.g(r1);	 Catch:{ all -> 0x0041 }
        r6.l = r0;	 Catch:{ all -> 0x0041 }
        goto L_0x0018;
    L_0x00ad:
        r0 = r6.a;	 Catch:{ all -> 0x0041 }
        r3 = r6.m;	 Catch:{ all -> 0x0041 }
        r3 = r0.getString(r3);	 Catch:{ all -> 0x0041 }
        r0 = com.ta.utdid2.b.a.i.a(r3);	 Catch:{ all -> 0x0041 }
        if (r0 != 0) goto L_0x00ed;
    L_0x00bb:
        r0 = r4.b(r3);	 Catch:{ all -> 0x0041 }
        r4 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r4 != 0) goto L_0x00cb;
    L_0x00c5:
        r0 = r6.a;	 Catch:{ all -> 0x0041 }
        r0 = r0.b(r3);	 Catch:{ all -> 0x0041 }
    L_0x00cb:
        r3 = r6.b(r0);	 Catch:{ all -> 0x0041 }
        if (r3 == 0) goto L_0x00ed;
    L_0x00d1:
        r3 = r6.a;	 Catch:{ all -> 0x0041 }
        r3 = r3.a(r0);	 Catch:{ all -> 0x0041 }
        r4 = com.ta.utdid2.b.a.i.a(r0);	 Catch:{ all -> 0x0041 }
        if (r4 != 0) goto L_0x00ed;
    L_0x00dd:
        r6.l = r0;	 Catch:{ all -> 0x0041 }
        if (r2 == 0) goto L_0x00e4;
    L_0x00e1:
        r6.j(r3);	 Catch:{ all -> 0x0041 }
    L_0x00e4:
        r0 = r6.l;	 Catch:{ all -> 0x0041 }
        r6.f(r0);	 Catch:{ all -> 0x0041 }
        r0 = r6.l;	 Catch:{ all -> 0x0041 }
        goto L_0x0018;
    L_0x00ed:
        r0 = r6.a();	 Catch:{ Exception -> 0x0113 }
        if (r0 == 0) goto L_0x0117;
    L_0x00f3:
        r3 = 2;
        r3 = com.ta.utdid2.b.a.b.encodeToString(r0, r3);	 Catch:{ Exception -> 0x0113 }
        r6.l = r3;	 Catch:{ Exception -> 0x0113 }
        r3 = r6.l;	 Catch:{ Exception -> 0x0113 }
        r6.f(r3);	 Catch:{ Exception -> 0x0113 }
        r3 = r6.a;	 Catch:{ Exception -> 0x0113 }
        r0 = r3.c(r0);	 Catch:{ Exception -> 0x0113 }
        if (r0 == 0) goto L_0x010f;
    L_0x0107:
        if (r2 == 0) goto L_0x010c;
    L_0x0109:
        r6.j(r0);	 Catch:{ Exception -> 0x0113 }
    L_0x010c:
        r6.g(r0);	 Catch:{ Exception -> 0x0113 }
    L_0x010f:
        r0 = r6.l;	 Catch:{ Exception -> 0x0113 }
        goto L_0x0018;
    L_0x0113:
        r0 = move-exception;
        r0.printStackTrace();	 Catch:{ all -> 0x0041 }
    L_0x0117:
        r0 = r1;
        goto L_0x0018;
    L_0x011a:
        r2 = move-exception;
        goto L_0x0012;
    L_0x011d:
        r0 = r3;
        goto L_0x006d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.device.c.h():java.lang.String");
    }

    private final byte[] a() throws Exception {
        String a;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        int nextInt = new Random().nextInt();
        byte[] bytes = e.getBytes(currentTimeMillis);
        byte[] bytes2 = e.getBytes(nextInt);
        byteArrayOutputStream.write(bytes, 0, 4);
        byteArrayOutputStream.write(bytes2, 0, 4);
        byteArrayOutputStream.write(3);
        byteArrayOutputStream.write(0);
        try {
            a = g.a(this.mContext);
        } catch (Exception e) {
            a = new Random().nextInt();
        }
        byteArrayOutputStream.write(e.getBytes(i.a(a)), 0, 4);
        a = "";
        byteArrayOutputStream.write(e.getBytes(i.a(b(byteArrayOutputStream.toByteArray()))));
        return byteArrayOutputStream.toByteArray();
    }

    private static String b(byte[] bArr) throws Exception {
        Mac instance = Mac.getInstance("HmacSHA1");
        instance.init(new SecretKeySpec("d6fc3a4a06adbde89223bvefedc24fecde188aaa9161".getBytes(), instance.getAlgorithm()));
        return b.encodeToString(instance.doFinal(bArr), 2);
    }
}
