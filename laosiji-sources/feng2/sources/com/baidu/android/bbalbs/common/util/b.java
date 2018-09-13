package com.baidu.android.bbalbs.common.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.os.SystemClock;
import android.provider.Settings.Secure;
import android.provider.Settings.System;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;
import com.baidu.android.bbalbs.common.a.d;
import com.stub.StubApp;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import javax.crypto.Cipher;
import org.json.JSONArray;
import org.json.JSONObject;

public final class b {
    private static final String a;
    private static b e;
    private final Context b;
    private int c = 0;
    private PublicKey d;

    private static class a {
        public ApplicationInfo a;
        public int b;
        public boolean c;
        public boolean d;

        private a() {
            this.b = 0;
            this.c = false;
            this.d = false;
        }

        /* synthetic */ a(c cVar) {
            this();
        }
    }

    private static class b {
        public String a;
        public String b;
        public int c;

        private b() {
            this.c = 2;
        }

        /* synthetic */ b(c cVar) {
            this();
        }

        public static b a(String str) {
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            try {
                JSONObject jSONObject = new JSONObject(str);
                Object string = jSONObject.getString("deviceid");
                String string2 = jSONObject.getString("imei");
                int i = jSONObject.getInt("ver");
                if (TextUtils.isEmpty(string) || string2 == null) {
                    return null;
                }
                b bVar = new b();
                bVar.a = string;
                bVar.b = string2;
                bVar.c = i;
                return bVar;
            } catch (Throwable e) {
                b.b(e);
                return null;
            }
        }

        public String a() {
            try {
                return new JSONObject().put("deviceid", this.a).put("imei", this.b).put("ver", this.c).toString();
            } catch (Throwable e) {
                b.b(e);
                return null;
            }
        }

        public String b() {
            String str = this.b;
            if (TextUtils.isEmpty(str)) {
                str = "0";
            }
            return this.a + "|" + new StringBuffer(str).reverse().toString();
        }
    }

    static class c {
        static boolean a(String str, int i) {
            try {
                Os.chmod(str, i);
                return true;
            } catch (Throwable e) {
                b.b(e);
                return false;
            }
        }
    }

    static {
        String str = new String(com.baidu.android.bbalbs.common.a.b.a(new byte[]{(byte) 77, (byte) 122, (byte) 65, (byte) 121, (byte) 77, (byte) 84, (byte) 73, (byte) 120, (byte) 77, (byte) 68, (byte) 73, (byte) 61}));
        a = str + new String(com.baidu.android.bbalbs.common.a.b.a(new byte[]{(byte) 90, (byte) 71, (byte) 108, (byte) 106, (byte) 100, (byte) 87, (byte) 82, (byte) 112, (byte) 89, (byte) 87, (byte) 73, (byte) 61}));
    }

    private b(Context context) {
        this.b = StubApp.getOrigApplicationContext(context.getApplicationContext());
        a();
    }

    public static String a(Context context) {
        return c(context).b();
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x003d A:{SYNTHETIC, Splitter: B:29:0x003d} */
    private static java.lang.String a(java.io.File r6) {
        /*
        r0 = 0;
        r2 = new java.io.FileReader;	 Catch:{ Exception -> 0x0048, all -> 0x0038 }
        r2.<init>(r6);	 Catch:{ Exception -> 0x0048, all -> 0x0038 }
        r1 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r1 = new char[r1];	 Catch:{ Exception -> 0x001a }
        r3 = new java.io.CharArrayWriter;	 Catch:{ Exception -> 0x001a }
        r3.<init>();	 Catch:{ Exception -> 0x001a }
    L_0x000f:
        r4 = r2.read(r1);	 Catch:{ Exception -> 0x001a }
        if (r4 <= 0) goto L_0x0024;
    L_0x0015:
        r5 = 0;
        r3.write(r1, r5, r4);	 Catch:{ Exception -> 0x001a }
        goto L_0x000f;
    L_0x001a:
        r1 = move-exception;
    L_0x001b:
        b(r1);	 Catch:{ all -> 0x0046 }
        if (r2 == 0) goto L_0x0023;
    L_0x0020:
        r2.close();	 Catch:{ Exception -> 0x0033 }
    L_0x0023:
        return r0;
    L_0x0024:
        r0 = r3.toString();	 Catch:{ Exception -> 0x001a }
        if (r2 == 0) goto L_0x0023;
    L_0x002a:
        r2.close();	 Catch:{ Exception -> 0x002e }
        goto L_0x0023;
    L_0x002e:
        r1 = move-exception;
        b(r1);
        goto L_0x0023;
    L_0x0033:
        r1 = move-exception;
        b(r1);
        goto L_0x0023;
    L_0x0038:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x003b:
        if (r2 == 0) goto L_0x0040;
    L_0x003d:
        r2.close();	 Catch:{ Exception -> 0x0041 }
    L_0x0040:
        throw r0;
    L_0x0041:
        r1 = move-exception;
        b(r1);
        goto L_0x0040;
    L_0x0046:
        r0 = move-exception;
        goto L_0x003b;
    L_0x0048:
        r1 = move-exception;
        r2 = r0;
        goto L_0x001b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.android.bbalbs.common.util.b.a(java.io.File):java.lang.String");
    }

    private static String a(byte[] bArr) {
        if (bArr == null) {
            throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
        }
        String str = "";
        str = "";
        for (byte b : bArr) {
            String toHexString = Integer.toHexString(b & 255);
            str = toHexString.length() == 1 ? str + "0" + toHexString : str + toHexString;
        }
        return str.toLowerCase();
    }

    private List<a> a(Intent intent, boolean z) {
        List<a> arrayList = new ArrayList();
        PackageManager packageManager = this.b.getPackageManager();
        List<ResolveInfo> queryBroadcastReceivers = packageManager.queryBroadcastReceivers(intent, 0);
        if (queryBroadcastReceivers != null) {
            for (ResolveInfo resolveInfo : queryBroadcastReceivers) {
                if (!(resolveInfo.activityInfo == null || resolveInfo.activityInfo.applicationInfo == null)) {
                    try {
                        Bundle bundle = packageManager.getReceiverInfo(new ComponentName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name), 128).metaData;
                        if (bundle != null) {
                            Object string = bundle.getString("galaxy_data");
                            if (!TextUtils.isEmpty(string)) {
                                byte[] a = com.baidu.android.bbalbs.common.a.b.a(string.getBytes("utf-8"));
                                JSONObject jSONObject = new JSONObject(new String(a));
                                a aVar = new a();
                                aVar.b = jSONObject.getInt("priority");
                                aVar.a = resolveInfo.activityInfo.applicationInfo;
                                if (this.b.getPackageName().equals(resolveInfo.activityInfo.applicationInfo.packageName)) {
                                    aVar.d = true;
                                }
                                if (z) {
                                    Object string2 = bundle.getString("galaxy_sf");
                                    if (!TextUtils.isEmpty(string2)) {
                                        int i;
                                        PackageInfo packageInfo = packageManager.getPackageInfo(resolveInfo.activityInfo.applicationInfo.packageName, 64);
                                        JSONArray jSONArray = jSONObject.getJSONArray("sigs");
                                        String[] strArr = new String[jSONArray.length()];
                                        for (i = 0; i < strArr.length; i++) {
                                            strArr[i] = jSONArray.getString(i);
                                        }
                                        if (a(strArr, a(packageInfo.signatures))) {
                                            byte[] a2 = a(com.baidu.android.bbalbs.common.a.b.a(string2.getBytes()), this.d);
                                            i = (a2 == null || !Arrays.equals(a2, d.a(a))) ? 0 : 1;
                                            if (i != 0) {
                                                aVar.c = true;
                                            }
                                        }
                                    }
                                }
                                arrayList.add(aVar);
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }
        }
        Collections.sort(arrayList, new c(this));
        return arrayList;
    }

    /* JADX WARNING: Removed duplicated region for block: B:30:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:13:0x002a A:{SYNTHETIC, Splitter: B:13:0x002a} */
    /* JADX WARNING: Removed duplicated region for block: B:19:0x0036 A:{SYNTHETIC, Splitter: B:19:0x0036} */
    private void a() {
        /*
        r4 = this;
        r1 = 0;
        r0 = new java.io.ByteArrayInputStream;	 Catch:{ Exception -> 0x0026, all -> 0x0033 }
        r2 = com.baidu.android.bbalbs.common.util.a.a();	 Catch:{ Exception -> 0x0026, all -> 0x0033 }
        r0.<init>(r2);	 Catch:{ Exception -> 0x0026, all -> 0x0033 }
        r1 = "X.509";
        r1 = java.security.cert.CertificateFactory.getInstance(r1);	 Catch:{ Exception -> 0x0044, all -> 0x003f }
        r1 = r1.generateCertificate(r0);	 Catch:{ Exception -> 0x0044, all -> 0x003f }
        r1 = r1.getPublicKey();	 Catch:{ Exception -> 0x0044, all -> 0x003f }
        r4.d = r1;	 Catch:{ Exception -> 0x0044, all -> 0x003f }
        if (r0 == 0) goto L_0x0020;
    L_0x001d:
        r0.close();	 Catch:{ Exception -> 0x0021 }
    L_0x0020:
        return;
    L_0x0021:
        r0 = move-exception;
        b(r0);
        goto L_0x0020;
    L_0x0026:
        r0 = move-exception;
        r0 = r1;
    L_0x0028:
        if (r0 == 0) goto L_0x0020;
    L_0x002a:
        r0.close();	 Catch:{ Exception -> 0x002e }
        goto L_0x0020;
    L_0x002e:
        r0 = move-exception;
        b(r0);
        goto L_0x0020;
    L_0x0033:
        r0 = move-exception;
    L_0x0034:
        if (r1 == 0) goto L_0x0039;
    L_0x0036:
        r1.close();	 Catch:{ Exception -> 0x003a }
    L_0x0039:
        throw r0;
    L_0x003a:
        r1 = move-exception;
        b(r1);
        goto L_0x0039;
    L_0x003f:
        r1 = move-exception;
        r3 = r1;
        r1 = r0;
        r0 = r3;
        goto L_0x0034;
    L_0x0044:
        r1 = move-exception;
        goto L_0x0028;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.android.bbalbs.common.util.b.a():void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:27:0x0058 A:{SYNTHETIC, Splitter: B:27:0x0058} */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0055 A:{Splitter: B:3:0x000a, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:15:0x0044, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:16:0x0045, code:
            r2 = r3;
     */
    /* JADX WARNING: Missing block: B:25:0x0055, code:
            r0 = th;
     */
    @android.annotation.SuppressLint({"NewApi"})
    private boolean a(java.lang.String r7) {
        /*
        r6 = this;
        r2 = 1;
        r1 = 0;
        r3 = 0;
        r0 = android.os.Build.VERSION.SDK_INT;
        r4 = 24;
        if (r0 < r4) goto L_0x003d;
    L_0x0009:
        r0 = r1;
    L_0x000a:
        r4 = r6.b;	 Catch:{ Exception -> 0x0044, all -> 0x0055 }
        r5 = "libcuid.so";
        r3 = r4.openFileOutput(r5, r0);	 Catch:{ Exception -> 0x0044, all -> 0x0055 }
        r4 = r7.getBytes();	 Catch:{ Exception -> 0x0064, all -> 0x0055 }
        r3.write(r4);	 Catch:{ Exception -> 0x0064, all -> 0x0055 }
        r3.flush();	 Catch:{ Exception -> 0x0064, all -> 0x0055 }
        if (r3 == 0) goto L_0x0022;
    L_0x001f:
        r3.close();	 Catch:{ Exception -> 0x003f }
    L_0x0022:
        if (r0 != 0) goto L_0x003c;
    L_0x0024:
        r0 = 436; // 0x1b4 float:6.11E-43 double:2.154E-321;
        r1 = new java.io.File;
        r2 = r6.b;
        r2 = r2.getFilesDir();
        r3 = "libcuid.so";
        r1.<init>(r2, r3);
        r1 = r1.getAbsolutePath();
        r2 = com.baidu.android.bbalbs.common.util.b.c.a(r1, r0);
    L_0x003c:
        return r2;
    L_0x003d:
        r0 = r2;
        goto L_0x000a;
    L_0x003f:
        r1 = move-exception;
        b(r1);
        goto L_0x0022;
    L_0x0044:
        r0 = move-exception;
        r2 = r3;
    L_0x0046:
        b(r0);	 Catch:{ all -> 0x0061 }
        if (r2 == 0) goto L_0x004e;
    L_0x004b:
        r2.close();	 Catch:{ Exception -> 0x0050 }
    L_0x004e:
        r2 = r1;
        goto L_0x003c;
    L_0x0050:
        r0 = move-exception;
        b(r0);
        goto L_0x004e;
    L_0x0055:
        r0 = move-exception;
    L_0x0056:
        if (r3 == 0) goto L_0x005b;
    L_0x0058:
        r3.close();	 Catch:{ Exception -> 0x005c }
    L_0x005b:
        throw r0;
    L_0x005c:
        r1 = move-exception;
        b(r1);
        goto L_0x005b;
    L_0x0061:
        r0 = move-exception;
        r3 = r2;
        goto L_0x0056;
    L_0x0064:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0046;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.android.bbalbs.common.util.b.a(java.lang.String):boolean");
    }

    private boolean a(String str, String str2) {
        try {
            return System.putString(this.b.getContentResolver(), str, str2);
        } catch (Throwable e) {
            b(e);
            return false;
        }
    }

    private boolean a(String[] strArr, String[] strArr2) {
        int i = 0;
        if (strArr == null || strArr2 == null || strArr.length != strArr2.length) {
            return false;
        }
        HashSet hashSet = new HashSet();
        for (Object add : strArr) {
            hashSet.add(add);
        }
        HashSet hashSet2 = new HashSet();
        int length = strArr2.length;
        while (i < length) {
            hashSet2.add(strArr2[i]);
            i++;
        }
        return hashSet.equals(hashSet2);
    }

    private static byte[] a(byte[] bArr, PublicKey publicKey) throws Exception {
        Cipher instance = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        instance.init(2, publicKey);
        return instance.doFinal(bArr);
    }

    private String[] a(Signature[] signatureArr) {
        String[] strArr = new String[signatureArr.length];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = a(d.a(signatureArr[i].toByteArray()));
        }
        return strArr;
    }

    private b b() {
        boolean z;
        a aVar;
        boolean z2;
        String str;
        String str2;
        b a;
        b e;
        String str3 = null;
        int i = 0;
        List a2 = a(new Intent("com.baidu.intent.action.GALAXY").setPackage(this.b.getPackageName()), true);
        int i2;
        if (a2 == null || a2.size() == 0) {
            for (i2 = 0; i2 < 3; i2++) {
                Log.w("DeviceId", "galaxy lib host missing meta-data,make sure you know the right way to integrate galaxy");
            }
            z = false;
        } else {
            aVar = (a) a2.get(0);
            z2 = aVar.c;
            if (!aVar.c) {
                for (i2 = 0; i2 < 3; i2++) {
                    Log.w("DeviceId", "galaxy config err, In the release version of the signature should be matched");
                }
            }
            z = z2;
        }
        File file = new File(this.b.getFilesDir(), "libcuid.so");
        b a3 = file.exists() ? b.a(f(a(file))) : null;
        if (a3 == null) {
            this.c |= 16;
            List<a> a4 = a(new Intent("com.baidu.intent.action.GALAXY"), z);
            if (a4 != null) {
                str = "files";
                file = this.b.getFilesDir();
                if (str.equals(file.getName())) {
                    str2 = str;
                } else {
                    Log.e("DeviceId", "fetal error:: app files dir name is unexpectedly :: " + file.getAbsolutePath());
                    str2 = file.getName();
                }
                for (a aVar2 : a4) {
                    if (!aVar2.d) {
                        File file2 = new File(new File(aVar2.a.dataDir, str2), "libcuid.so");
                        if (file2.exists()) {
                            a = b.a(f(a(file2)));
                            if (a != null) {
                                break;
                            }
                        }
                        a = a3;
                        a3 = a;
                    }
                }
            }
        }
        a = a3;
        if (a == null) {
            a = b.a(f(b("com.baidu.deviceid.v2")));
        }
        boolean c = c("android.permission.READ_EXTERNAL_STORAGE");
        if (a == null && c) {
            this.c |= 2;
            e = e();
        } else {
            e = a;
        }
        if (e == null) {
            this.c |= 8;
            e = d();
        }
        if (e == null && c) {
            this.c |= 1;
            str = h("");
            e = d(str);
            i = 1;
        } else {
            str = null;
        }
        if (e == null) {
            this.c |= 4;
            if (i == 0) {
                str = h("");
            }
            b bVar = new b();
            str2 = b(this.b);
            bVar.a = com.baidu.android.bbalbs.common.a.c.a((VERSION.SDK_INT < 23 ? str + str2 + UUID.randomUUID().toString() : "com.baidu" + str2).getBytes(), true);
            bVar.b = str;
            a = bVar;
        } else {
            a = e;
        }
        file = new File(this.b.getFilesDir(), "libcuid.so");
        if (!((this.c & 16) == 0 && file.exists())) {
            str2 = TextUtils.isEmpty(null) ? e(a.a()) : null;
            a(str2);
            str3 = str2;
        }
        z2 = c();
        if (z2 && ((this.c & 2) != 0 || TextUtils.isEmpty(b("com.baidu.deviceid.v2")))) {
            if (TextUtils.isEmpty(str3)) {
                str3 = e(a.a());
            }
            a("com.baidu.deviceid.v2", str3);
        }
        if (c("android.permission.WRITE_EXTERNAL_STORAGE")) {
            File file3 = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
            if (!((this.c & 8) == 0 && file3.exists())) {
                if (TextUtils.isEmpty(str3)) {
                    str3 = e(a.a());
                }
                g(str3);
            }
        }
        if (z2 && ((this.c & 1) != 0 || TextUtils.isEmpty(b("com.baidu.deviceid")))) {
            a("com.baidu.deviceid", a.a);
            a("bd_setting_i", a.b);
        }
        if (z2 && !TextUtils.isEmpty(a.b)) {
            file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid");
            if (!((this.c & 2) == 0 && file.exists())) {
                b(a.b, a.a);
            }
        }
        return a;
    }

    public static String b(Context context) {
        String str = "";
        Object string = Secure.getString(context.getContentResolver(), "android_id");
        return TextUtils.isEmpty(string) ? "" : string;
    }

    private String b(String str) {
        try {
            return System.getString(this.b.getContentResolver(), str);
        } catch (Throwable e) {
            b(e);
            return null;
        }
    }

    private static void b(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(str);
            stringBuilder.append("=");
            stringBuilder.append(str2);
            File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
            File file2 = new File(file, ".cuid");
            try {
                if (file.exists() && !file.isDirectory()) {
                    File file3;
                    Random random = new Random();
                    File parentFile = file.getParentFile();
                    String name = file.getName();
                    do {
                        file3 = new File(parentFile, name + random.nextInt() + ".tmp");
                    } while (file3.exists());
                    file.renameTo(file3);
                    file3.delete();
                }
                file.mkdirs();
                FileWriter fileWriter = new FileWriter(file2, false);
                fileWriter.write(com.baidu.android.bbalbs.common.a.b.a(com.baidu.android.bbalbs.common.a.a.a(a, a, stringBuilder.toString().getBytes()), "utf-8"));
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
            } catch (Exception e2) {
            }
        }
    }

    private static void b(Throwable th) {
    }

    private static b c(Context context) {
        if (e == null) {
            synchronized (b.class) {
                if (e == null) {
                    SystemClock.uptimeMillis();
                    e = new b(context).b();
                    SystemClock.uptimeMillis();
                }
            }
        }
        return e;
    }

    private boolean c() {
        return c("android.permission.WRITE_SETTINGS");
    }

    private boolean c(String str) {
        return this.b.checkPermission(str, Process.myPid(), Process.myUid()) == 0;
    }

    private b d() {
        Object b = b("com.baidu.deviceid");
        String b2 = b("bd_setting_i");
        if (TextUtils.isEmpty(b2)) {
            b2 = h("");
            if (!TextUtils.isEmpty(b2)) {
                a("bd_setting_i", b2);
            }
        }
        if (TextUtils.isEmpty(b)) {
            b = b(com.baidu.android.bbalbs.common.a.c.a(("com.baidu" + b2 + b(this.b)).getBytes(), true));
        }
        if (TextUtils.isEmpty(b)) {
            return null;
        }
        b bVar = new b();
        bVar.a = b;
        bVar.b = b2;
        return bVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a5 A:{SYNTHETIC, Splitter: B:33:0x00a5} */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a5 A:{SYNTHETIC, Splitter: B:33:0x00a5} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x00bb  */
    private com.baidu.android.bbalbs.common.util.b.b d(java.lang.String r9) {
        /*
        r8 = this;
        r2 = 0;
        r3 = 0;
        r4 = 1;
        r0 = android.os.Build.VERSION.SDK_INT;
        r1 = 23;
        if (r0 >= r1) goto L_0x0014;
    L_0x0009:
        r5 = r4;
    L_0x000a:
        if (r5 == 0) goto L_0x0016;
    L_0x000c:
        r0 = android.text.TextUtils.isEmpty(r9);
        if (r0 == 0) goto L_0x0016;
    L_0x0012:
        r0 = r2;
    L_0x0013:
        return r0;
    L_0x0014:
        r5 = r3;
        goto L_0x000a;
    L_0x0016:
        r0 = "";
        r1 = new java.io.File;
        r6 = android.os.Environment.getExternalStorageDirectory();
        r7 = "baidu/.cuid";
        r1.<init>(r6, r7);
        r6 = r1.exists();
        if (r6 == 0) goto L_0x005d;
    L_0x002b:
        r4 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4.<init>(r1);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1.<init>(r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4.<init>();	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
    L_0x003a:
        r6 = r1.readLine();	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        if (r6 == 0) goto L_0x006b;
    L_0x0040:
        r4.append(r6);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r6 = "\r\n";
        r4.append(r6);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        goto L_0x003a;
    L_0x004a:
        r1 = move-exception;
        r1 = r9;
    L_0x004c:
        r3 = android.text.TextUtils.isEmpty(r0);
        if (r3 != 0) goto L_0x00bb;
    L_0x0052:
        r3 = new com.baidu.android.bbalbs.common.util.b$b;
        r3.<init>(r2);
        r3.a = r0;
        r3.b = r1;
        r0 = r3;
        goto L_0x0013;
    L_0x005d:
        r1 = new java.io.File;
        r3 = android.os.Environment.getExternalStorageDirectory();
        r6 = "backups/.SystemConfig/.cuid";
        r1.<init>(r3, r6);
        r3 = r4;
        goto L_0x002b;
    L_0x006b:
        r1.close();	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1 = new java.lang.String;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r6 = a;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r7 = a;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = r4.toString();	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = r4.getBytes();	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = com.baidu.android.bbalbs.common.a.b.a(r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = com.baidu.android.bbalbs.common.a.a.b(r6, r7, r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1.<init>(r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = "=";
        r1 = r1.split(r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        if (r1 == 0) goto L_0x00c8;
    L_0x0090:
        r4 = r1.length;	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r6 = 2;
        if (r4 != r6) goto L_0x00c8;
    L_0x0094:
        if (r5 == 0) goto L_0x00ab;
    L_0x0096:
        r4 = 0;
        r4 = r1[r4];	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r4 = r9.equals(r4);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        if (r4 == 0) goto L_0x00ab;
    L_0x009f:
        r4 = 1;
        r0 = r1[r4];	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1 = r9;
    L_0x00a3:
        if (r3 != 0) goto L_0x004c;
    L_0x00a5:
        b(r1, r0);	 Catch:{ FileNotFoundException -> 0x00a9, IOException -> 0x00c6, Exception -> 0x00c1 }
        goto L_0x004c;
    L_0x00a9:
        r3 = move-exception;
        goto L_0x004c;
    L_0x00ab:
        if (r5 != 0) goto L_0x00c8;
    L_0x00ad:
        r4 = android.text.TextUtils.isEmpty(r9);	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        if (r4 == 0) goto L_0x00b6;
    L_0x00b3:
        r4 = 1;
        r9 = r1[r4];	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
    L_0x00b6:
        r4 = 1;
        r0 = r1[r4];	 Catch:{ FileNotFoundException -> 0x004a, IOException -> 0x00c3, Exception -> 0x00be }
        r1 = r9;
        goto L_0x00a3;
    L_0x00bb:
        r0 = r2;
        goto L_0x0013;
    L_0x00be:
        r1 = move-exception;
        r1 = r9;
        goto L_0x004c;
    L_0x00c1:
        r3 = move-exception;
        goto L_0x004c;
    L_0x00c3:
        r1 = move-exception;
        r1 = r9;
        goto L_0x004c;
    L_0x00c6:
        r3 = move-exception;
        goto L_0x004c;
    L_0x00c8:
        r1 = r9;
        goto L_0x00a3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.android.bbalbs.common.util.b.d(java.lang.String):com.baidu.android.bbalbs.common.util.b$b");
    }

    private b e() {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig/.cuid2");
        if (file.exists()) {
            Object a = a(file);
            if (!TextUtils.isEmpty(a)) {
                try {
                    return b.a(new String(com.baidu.android.bbalbs.common.a.a.b(a, a, com.baidu.android.bbalbs.common.a.b.a(a.getBytes()))));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static String e(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return com.baidu.android.bbalbs.common.a.b.a(com.baidu.android.bbalbs.common.a.a.a(a, a, str.getBytes()), "utf-8");
        } catch (Throwable e) {
            b(e);
        } catch (Throwable e2) {
            b(e2);
        }
        return "";
    }

    private static String f(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new String(com.baidu.android.bbalbs.common.a.a.b(a, a, com.baidu.android.bbalbs.common.a.b.a(str.getBytes())));
        } catch (Throwable e) {
            b(e);
            return "";
        }
    }

    private static void g(String str) {
        File file = new File(Environment.getExternalStorageDirectory(), "backups/.SystemConfig");
        File file2 = new File(file, ".cuid2");
        try {
            if (file.exists() && !file.isDirectory()) {
                File file3;
                Random random = new Random();
                File parentFile = file.getParentFile();
                String name = file.getName();
                do {
                    file3 = new File(parentFile, name + random.nextInt() + ".tmp");
                } while (file3.exists());
                file.renameTo(file3);
                file3.delete();
            }
            file.mkdirs();
            FileWriter fileWriter = new FileWriter(file2, false);
            fileWriter.write(str);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
        } catch (Exception e2) {
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x0029  */
    /* JADX WARNING: Removed duplicated region for block: B:11:? A:{SYNTHETIC, RETURN, ORIG_RETURN} */
    private java.lang.String h(java.lang.String r5) {
        /*
        r4 = this;
        r1 = 0;
        r0 = r4.b;	 Catch:{ Exception -> 0x001d }
        r2 = "phone";
        r0 = r0.getSystemService(r2);	 Catch:{ Exception -> 0x001d }
        r0 = (android.telephony.TelephonyManager) r0;	 Catch:{ Exception -> 0x001d }
        if (r0 == 0) goto L_0x0027;
    L_0x000e:
        r0 = r0.getDeviceId();	 Catch:{ Exception -> 0x001d }
    L_0x0012:
        r0 = i(r0);
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x0029;
    L_0x001c:
        return r5;
    L_0x001d:
        r0 = move-exception;
        r2 = "DeviceId";
        r3 = "Read IMEI failed";
        android.util.Log.e(r2, r3, r0);
    L_0x0027:
        r0 = r1;
        goto L_0x0012;
    L_0x0029:
        r5 = r0;
        goto L_0x001c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.android.bbalbs.common.util.b.h(java.lang.String):java.lang.String");
    }

    private static String i(String str) {
        return (str == null || !str.contains(":")) ? str : "";
    }
}
