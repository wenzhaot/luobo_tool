package com.baidu.platform.comapi.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.storage.StorageManager;
import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class d {
    private static volatile d a = null;
    private boolean b = false;
    private boolean c = true;
    private final List<c> d = new ArrayList();
    private c e = null;
    private String f;

    private d() {
    }

    public static d a() {
        if (a == null) {
            synchronized (d.class) {
                if (a == null) {
                    a = new d();
                }
            }
        }
        return a;
    }

    private boolean a(String str) {
        boolean createNewFile;
        Exception e;
        try {
            File file = new File(str + "/test.0");
            if (file.exists()) {
                file.delete();
            }
            createNewFile = file.createNewFile();
            try {
                if (file.exists()) {
                    file.delete();
                }
            } catch (Exception e2) {
                e = e2;
                e.printStackTrace();
                return createNewFile;
            }
        } catch (Exception e3) {
            Exception exception = e3;
            createNewFile = false;
            e = exception;
            e.printStackTrace();
            return createNewFile;
        }
        return createNewFile;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(14)
    private void c(Context context) {
        try {
            StorageManager storageManager = (StorageManager) context.getSystemService("storage");
            Method method = storageManager.getClass().getMethod("getVolumeList", new Class[0]);
            Method method2 = storageManager.getClass().getMethod("getVolumeState", new Class[]{String.class});
            Class cls = Class.forName("android.os.storage.StorageVolume");
            Method method3 = cls.getMethod("isRemovable", new Class[0]);
            Method method4 = cls.getMethod("getPath", new Class[0]);
            Object[] objArr = (Object[]) method.invoke(storageManager, new Object[0]);
            if (objArr != null) {
                for (Object obj : objArr) {
                    String str = (String) method4.invoke(obj, new Object[0]);
                    if (str != null && str.length() > 0) {
                        if ("mounted".equals(method2.invoke(storageManager, new Object[]{str}))) {
                            Object obj2 = !((Boolean) method3.invoke(obj, new Object[0])).booleanValue() ? 1 : null;
                            if (VERSION.SDK_INT <= 19 && a(str)) {
                                this.d.add(new c(str, obj2 == null, obj2 != null ? "内置存储卡" : "外置存储卡", context));
                            } else if (VERSION.SDK_INT >= 19 && new File(str + File.separator + "BaiduMapSDKNew").exists() && str.equals(context.getSharedPreferences("map_pref", 0).getString("PREFFERED_SD_CARD", ""))) {
                                this.f = str + File.separator + "BaiduMapSDKNew";
                            }
                        }
                    }
                }
                if (VERSION.SDK_INT >= 19) {
                    File[] externalFilesDirs = context.getExternalFilesDirs(null);
                    Collection arrayList = new ArrayList();
                    arrayList.addAll(this.d);
                    int i = 0;
                    while (true) {
                        int i2 = i;
                        if (i2 >= externalFilesDirs.length || externalFilesDirs[i2] == null) {
                            this.d.clear();
                            this.d.addAll(arrayList);
                        } else {
                            Object obj3;
                            String absolutePath = externalFilesDirs[i2].getAbsolutePath();
                            for (c a : this.d) {
                                if (absolutePath.startsWith(a.a())) {
                                    obj3 = 1;
                                    break;
                                }
                            }
                            obj3 = null;
                            String str2 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).packageName;
                            if (!(str2 == null || obj3 != null || absolutePath.indexOf(str2) == -1)) {
                                arrayList.add(new c(absolutePath, true, "外置存储卡", context));
                            }
                            i = i2 + 1;
                        }
                    }
                    this.d.clear();
                    this.d.addAll(arrayList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:44:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:92:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0052  */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00b3  */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x004c A:{Splitter: B:5:0x001e, ExcHandler: java.lang.Exception (e java.lang.Exception), PHI: r1 } */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00b3  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:16:0x004c, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:69:0x011f, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:70:0x0120, code:
            r2 = r1;
     */
    private void d(android.content.Context r10) {
        /*
        r9 = this;
        r2 = 0;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = new java.util.ArrayList;
        r4.<init>();
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1 = "/proc/mounts";
        r0.<init>(r1);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1 = r0.exists();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r1 == 0) goto L_0x0059;
    L_0x0019:
        r1 = new java.util.Scanner;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1.<init>(r0);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
    L_0x001e:
        r0 = r1.hasNext();	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        if (r0 == 0) goto L_0x0056;
    L_0x0024:
        r0 = r1.nextLine();	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        r5 = "/dev/block/vold/";
        r5 = r0.startsWith(r5);	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        if (r5 == 0) goto L_0x001e;
    L_0x0031:
        r5 = 9;
        r6 = 32;
        r0 = r0.replace(r5, r6);	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        r5 = " ";
        r0 = r0.split(r5);	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        if (r0 == 0) goto L_0x001e;
    L_0x0042:
        r5 = r0.length;	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        if (r5 <= 0) goto L_0x001e;
    L_0x0045:
        r5 = 1;
        r0 = r0[r5];	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        r3.add(r0);	 Catch:{ Exception -> 0x004c, all -> 0x011f }
        goto L_0x001e;
    L_0x004c:
        r0 = move-exception;
    L_0x004d:
        r0.printStackTrace();	 Catch:{ all -> 0x0122 }
        if (r1 == 0) goto L_0x0055;
    L_0x0052:
        r1.close();
    L_0x0055:
        return;
    L_0x0056:
        r1.close();	 Catch:{ Exception -> 0x004c, all -> 0x011f }
    L_0x0059:
        r0 = new java.io.File;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1 = "/system/etc/vold.fstab";
        r0.<init>(r1);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1 = r0.exists();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r1 == 0) goto L_0x00ba;
    L_0x0067:
        r1 = new java.util.Scanner;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1.<init>(r0);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
    L_0x006c:
        r0 = r1.hasNext();	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        if (r0 == 0) goto L_0x00b7;
    L_0x0072:
        r0 = r1.nextLine();	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        r5 = "dev_mount";
        r5 = r0.startsWith(r5);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        if (r5 == 0) goto L_0x006c;
    L_0x007f:
        r5 = 9;
        r6 = 32;
        r0 = r0.replace(r5, r6);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        r5 = " ";
        r0 = r0.split(r5);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        if (r0 == 0) goto L_0x006c;
    L_0x0090:
        r5 = r0.length;	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        if (r5 <= 0) goto L_0x006c;
    L_0x0093:
        r5 = 2;
        r0 = r0[r5];	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        r5 = ":";
        r5 = r0.contains(r5);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        if (r5 == 0) goto L_0x00ab;
    L_0x009f:
        r5 = 0;
        r6 = ":";
        r6 = r0.indexOf(r6);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        r0 = r0.substring(r5, r6);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
    L_0x00ab:
        r4.add(r0);	 Catch:{ Exception -> 0x004c, all -> 0x00af }
        goto L_0x006c;
    L_0x00af:
        r0 = move-exception;
        r2 = r1;
    L_0x00b1:
        if (r2 == 0) goto L_0x00b6;
    L_0x00b3:
        r2.close();
    L_0x00b6:
        throw r0;
    L_0x00b7:
        r1.close();	 Catch:{ Exception -> 0x004c, all -> 0x00af }
    L_0x00ba:
        r0 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r1 = r0.getAbsolutePath();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r0 = r9.d;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r5 = new com.baidu.platform.comapi.util.c;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r6 = 0;
        r7 = "Auto";
        r5.<init>(r1, r6, r7, r10);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r0.add(r5);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r3 = r3.iterator();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
    L_0x00d4:
        r0 = r3.hasNext();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r0 == 0) goto L_0x0116;
    L_0x00da:
        r0 = r3.next();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r0 = (java.lang.String) r0;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r5 = r4.contains(r0);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r5 == 0) goto L_0x00d4;
    L_0x00e6:
        r5 = r0.equals(r1);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r5 != 0) goto L_0x00d4;
    L_0x00ec:
        r5 = new java.io.File;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r5.<init>(r0);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r6 = r5.exists();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r6 == 0) goto L_0x00d4;
    L_0x00f7:
        r6 = r5.isDirectory();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r6 == 0) goto L_0x00d4;
    L_0x00fd:
        r5 = r5.canWrite();	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        if (r5 == 0) goto L_0x00d4;
    L_0x0103:
        r5 = r9.d;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r6 = new com.baidu.platform.comapi.util.c;	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r7 = 0;
        r8 = "Auto";
        r6.<init>(r0, r7, r8, r10);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        r5.add(r6);	 Catch:{ Exception -> 0x0112, all -> 0x011d }
        goto L_0x00d4;
    L_0x0112:
        r0 = move-exception;
        r1 = r2;
        goto L_0x004d;
    L_0x0116:
        if (r2 == 0) goto L_0x0055;
    L_0x0118:
        r2.close();
        goto L_0x0055;
    L_0x011d:
        r0 = move-exception;
        goto L_0x00b1;
    L_0x011f:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00b1;
    L_0x0122:
        r0 = move-exception;
        r2 = r1;
        goto L_0x00b1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.baidu.platform.comapi.util.d.d(android.content.Context):void");
    }

    public void a(Context context) {
        int i = 0;
        if (!this.b) {
            this.b = true;
            try {
                if (VERSION.SDK_INT >= 14) {
                    c(context);
                } else {
                    d(context);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (this.d.size() > 0) {
                    c cVar = null;
                    for (c cVar2 : this.d) {
                        c cVar3;
                        int i2;
                        if (new File(cVar2.b()).exists()) {
                            int i3 = i + 1;
                            cVar3 = cVar2;
                            i2 = i3;
                        } else {
                            i2 = i;
                            cVar3 = cVar;
                        }
                        cVar = cVar3;
                        i = i2;
                    }
                    if (i == 0) {
                        this.e = b(context);
                        if (this.e == null) {
                            for (c cVar22 : this.d) {
                                if (a(context, cVar22)) {
                                    this.e = cVar22;
                                    break;
                                }
                            }
                        }
                    } else if (i != 1) {
                        this.e = b(context);
                    } else if (a(context, cVar)) {
                        this.e = cVar;
                    }
                    if (this.e == null) {
                        this.e = (c) this.d.get(0);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                if (this.e == null || !a(this.e.a())) {
                    this.c = false;
                    this.e = new c(context);
                    this.d.clear();
                    this.d.add(this.e);
                    return;
                }
                File file = new File(this.e.b());
                if (!file.exists()) {
                    file.mkdirs();
                }
                file = new File(this.e.c());
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(file, ".nomedia");
                if (!file2.exists()) {
                    file2.createNewFile();
                }
            } catch (Exception e22) {
                e22.printStackTrace();
            }
        }
    }

    public boolean a(Context context, c cVar) {
        String a = cVar.a();
        if (!a(a)) {
            return false;
        }
        Editor edit = context.getSharedPreferences("map_pref", 0).edit();
        edit.putString("PREFFERED_SD_CARD", a);
        return edit.commit();
    }

    public c b() {
        return this.e;
    }

    public c b(Context context) {
        String string = context.getSharedPreferences("map_pref", 0).getString("PREFFERED_SD_CARD", "");
        if (string != null && string.length() > 0) {
            for (c cVar : this.d) {
                if (cVar.a().equals(string)) {
                    return cVar;
                }
            }
        }
        return null;
    }
}
