package com.talkingdata.sdk;

import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: td */
public class ed {
    private static do c = null;
    private static dr d = null;
    private static dv e = new dv();
    private static ds f = null;
    private static volatile ed g = null;
    public JSONObject a = null;
    public JSONObject b = null;

    public synchronized JSONObject a(dn dnVar, boolean z) {
        return a(dnVar, z, null);
    }

    public synchronized JSONObject a(dn dnVar, boolean z, a aVar) {
        return a(dnVar, z, aVar, null);
    }

    /* JADX WARNING: Removed duplicated region for block: B:64:0x01e9 A:{Splitter: B:25:0x0087, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01e9 A:{Splitter: B:25:0x0087, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01e9 A:{Splitter: B:25:0x0087, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x01e9 A:{Splitter: B:25:0x0087, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Missing block: B:66:?, code:
            com.talkingdata.sdk.bb.releaseFileLock(com.talkingdata.sdk.ck.a().b());
     */
    public synchronized org.json.JSONObject a(com.talkingdata.sdk.dn r5, boolean r6, com.talkingdata.sdk.a r7, android.util.Pair r8) {
        /*
        r4 = this;
        monitor-enter(r4);
        if (r5 == 0) goto L_0x0009;
    L_0x0003:
        r0 = r5.a_();	 Catch:{ all -> 0x01e6 }
        if (r0 != 0) goto L_0x000c;
    L_0x0009:
        r0 = 0;
    L_0x000a:
        monitor-exit(r4);
        return r0;
    L_0x000c:
        r2 = new org.json.JSONObject;	 Catch:{ all -> 0x01e6 }
        r2.<init>();	 Catch:{ all -> 0x01e6 }
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0 = r0.b();	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        com.talkingdata.sdk.bb.getFileLock(r0);	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r4.a = r0;	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r4.b = r0;	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0 = c;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        if (r0 != 0) goto L_0x0045;
    L_0x002e:
        r0 = com.talkingdata.sdk.do.a();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        c = r0;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = r0.getPackageName();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r1 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = com.talkingdata.sdk.ec.a(r1, r0);	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r1 = c;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r1.setUniqueId(r0);	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
    L_0x0045:
        r0 = c;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.setSubmitAppId(r7);	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = c;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.setSubmitChannelId(r7);	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = e;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        if (r0 != 0) goto L_0x01c6;
    L_0x0053:
        r0 = new com.talkingdata.sdk.dv;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        e = r0;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
    L_0x005a:
        r0 = e;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = com.talkingdata.sdk.dv.a;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.b();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = d;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        if (r0 != 0) goto L_0x006c;
    L_0x0065:
        r0 = new com.talkingdata.sdk.dr;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        d = r0;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
    L_0x006c:
        r0 = f;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        if (r0 != 0) goto L_0x0087;
    L_0x0070:
        r0 = new com.talkingdata.sdk.ds;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        f = r0;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = f;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.b();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = e;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = r0.c();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r1 = com.talkingdata.sdk.bn.a;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.setSlots(r1);	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
    L_0x0087:
        r0 = f;	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0.c();	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        r0 = "version";
        r1 = "2.0";
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "action";
        r1 = r5.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "device";
        r1 = e;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "app";
        r1 = c;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "sdk";
        r1 = d;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "appContext";
        r1 = com.talkingdata.sdk.dl.a();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = "user";
        r1 = f;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = java.lang.System.currentTimeMillis();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3 = "ts";
        r2.put(r3, r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3.<init>();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r3.append(r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = com.talkingdata.sdk.ec.a(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = e;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.b();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.b();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = e;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.b();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.c();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r0.append(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r0.toString();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = com.talkingdata.sdk.bo.c(r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = "fingerprint";
        r2.put(r1, r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        if (r6 == 0) goto L_0x018e;
    L_0x0126:
        r0 = new org.json.JSONArray;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0.<init>();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = new com.talkingdata.sdk.dy;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3 = com.talkingdata.sdk.eb.WIFI;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0.put(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = new com.talkingdata.sdk.dy;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3 = com.talkingdata.sdk.eb.CELLULAR;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0.put(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = com.talkingdata.sdk.ab.g;	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        r3 = "android.permission.BLUETOOTH";
        r1 = com.talkingdata.sdk.bo.b(r1, r3);	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        if (r1 == 0) goto L_0x0160;
    L_0x0152:
        r1 = new com.talkingdata.sdk.dy;	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        r3 = com.talkingdata.sdk.eb.BLUETOOTH;	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        r1.<init>(r3);	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
        r0.put(r1);	 Catch:{ Throwable -> 0x01f6, all -> 0x01e9 }
    L_0x0160:
        r1 = "networks";
        r2.put(r1, r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = "Location";
        r0 = r0.a(r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        if (r0 == 0) goto L_0x018e;
    L_0x0173:
        r1 = new com.talkingdata.sdk.dp;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1.<init>();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r3 = "locations";
        r1 = r1.a_();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r3, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r0.length();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        if (r1 <= 0) goto L_0x018e;
    L_0x0188:
        r1 = "Location";
        r4.a(r1, r0);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
    L_0x018e:
        if (r8 == 0) goto L_0x01a5;
    L_0x0190:
        r0 = r8.second;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = (org.json.JSONArray) r0;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = r0.length();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        if (r0 <= 0) goto L_0x01a5;
    L_0x019a:
        r0 = r8.first;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = r8.second;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r1 = (org.json.JSONArray) r1;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r4.a(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
    L_0x01a5:
        r0 = com.talkingdata.sdk.ab.q;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        if (r0 != 0) goto L_0x01b1;
    L_0x01a9:
        r0 = "cloudcontrol";
        r1 = r4.a;	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r2.put(r0, r1);	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
    L_0x01b1:
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
        r0.c();	 Catch:{ Throwable -> 0x01d4, all -> 0x01e9 }
    L_0x01b8:
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ all -> 0x01e6 }
        r0 = r0.b();	 Catch:{ all -> 0x01e6 }
        com.talkingdata.sdk.bb.releaseFileLock(r0);	 Catch:{ all -> 0x01e6 }
    L_0x01c3:
        r0 = r2;
        goto L_0x000a;
    L_0x01c6:
        r0 = e;	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0 = r0.c();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        r0.c();	 Catch:{ Throwable -> 0x01d1, all -> 0x01e9 }
        goto L_0x005a;
    L_0x01d1:
        r0 = move-exception;
        goto L_0x0087;
    L_0x01d4:
        r0 = move-exception;
        com.talkingdata.sdk.cs.postSDKError(r0);	 Catch:{ Throwable -> 0x01d9, all -> 0x01e9 }
        goto L_0x01b8;
    L_0x01d9:
        r0 = move-exception;
        r0 = com.talkingdata.sdk.ck.a();	 Catch:{ all -> 0x01e6 }
        r0 = r0.b();	 Catch:{ all -> 0x01e6 }
        com.talkingdata.sdk.bb.releaseFileLock(r0);	 Catch:{ all -> 0x01e6 }
        goto L_0x01c3;
    L_0x01e6:
        r0 = move-exception;
        monitor-exit(r4);
        throw r0;
    L_0x01e9:
        r0 = move-exception;
        r1 = com.talkingdata.sdk.ck.a();	 Catch:{ all -> 0x01e6 }
        r1 = r1.b();	 Catch:{ all -> 0x01e6 }
        com.talkingdata.sdk.bb.releaseFileLock(r1);	 Catch:{ all -> 0x01e6 }
        throw r0;	 Catch:{ all -> 0x01e6 }
    L_0x01f6:
        r1 = move-exception;
        goto L_0x0160;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.ed.a(com.talkingdata.sdk.dn, boolean, com.talkingdata.sdk.a, android.util.Pair):org.json.JSONObject");
    }

    public void a(String str, JSONArray jSONArray) {
        try {
            if (!ab.q && this.a != null && jSONArray.length() > 0) {
                this.a.put(str, jSONArray);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public static ed a() {
        if (g == null) {
            synchronized (ed.class) {
                if (g == null) {
                    g = new ed();
                }
            }
        }
        return g;
    }
}
