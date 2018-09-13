package com.ta.utdid2.c.a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import com.ta.utdid2.b.a.i;
import com.ta.utdid2.c.a.b.a;
import java.io.File;
import java.util.Map.Entry;

/* compiled from: PersistentConfiguration */
public class c {
    private Editor a = null;
    /* renamed from: a */
    private SharedPreferences f13a = null;
    /* renamed from: a */
    private a f14a = null;
    /* renamed from: a */
    private b f15a = null;
    /* renamed from: a */
    private d f16a = null;
    private String e = "";
    private String f = "";
    private boolean g = false;
    private boolean h = false;
    private boolean i = false;
    private boolean j = false;
    private Context mContext = null;

    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:28:0x00a7  */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00bb  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00cf  */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x00e2 A:{Catch:{ Exception -> 0x01ef }} */
    /* JADX WARNING: Removed duplicated region for block: B:108:? A:{SYNTHETIC, RETURN} */
    public c(android.content.Context r11, java.lang.String r12, java.lang.String r13, boolean r14, boolean r15) {
        /*
        r10 = this;
        r10.<init>();
        r0 = "";
        r10.e = r0;
        r0 = "";
        r10.f = r0;
        r0 = 0;
        r10.g = r0;
        r0 = 0;
        r10.h = r0;
        r0 = 0;
        r10.i = r0;
        r0 = 0;
        r10.a = r0;
        r0 = 0;
        r10.a = r0;
        r0 = 0;
        r10.a = r0;
        r0 = 0;
        r10.a = r0;
        r0 = 0;
        r10.mContext = r0;
        r0 = 0;
        r10.a = r0;
        r0 = 0;
        r10.j = r0;
        r10.g = r14;
        r10.j = r15;
        r10.e = r13;
        r10.f = r12;
        r10.mContext = r11;
        r0 = 0;
        r4 = 0;
        if (r11 == 0) goto L_0x004d;
    L_0x003b:
        r0 = 0;
        r0 = r11.getSharedPreferences(r13, r0);
        r10.a = r0;
        r0 = r10.a;
        r1 = "t";
        r2 = 0;
        r0 = r0.getLong(r1, r2);
    L_0x004d:
        r2 = 0;
        r2 = android.os.Environment.getExternalStorageState();	 Catch:{ Exception -> 0x00f2 }
    L_0x0052:
        r3 = com.ta.utdid2.b.a.i.a(r2);
        if (r3 == 0) goto L_0x00f8;
    L_0x0058:
        r2 = 0;
        r10.i = r2;
        r10.h = r2;
    L_0x005d:
        r2 = r10.h;
        if (r2 != 0) goto L_0x0065;
    L_0x0061:
        r2 = r10.i;
        if (r2 == 0) goto L_0x0203;
    L_0x0065:
        if (r11 == 0) goto L_0x0203;
    L_0x0067:
        r2 = com.ta.utdid2.b.a.i.a(r12);
        if (r2 != 0) goto L_0x0203;
    L_0x006d:
        r2 = r10.a(r12);
        r10.a = r2;
        r2 = r10.a;
        if (r2 == 0) goto L_0x0203;
    L_0x0077:
        r2 = r10.a;	 Catch:{ Exception -> 0x01f2 }
        r3 = 0;
        r2 = r2.a(r13, r3);	 Catch:{ Exception -> 0x01f2 }
        r10.a = r2;	 Catch:{ Exception -> 0x01f2 }
        r2 = r10.a;	 Catch:{ Exception -> 0x01f2 }
        r3 = "t";
        r6 = 0;
        r2 = r2.getLong(r3, r6);	 Catch:{ Exception -> 0x01f2 }
        if (r15 != 0) goto L_0x014e;
    L_0x008d:
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 <= 0) goto L_0x0120;
    L_0x0091:
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r10.a(r4, r5);	 Catch:{ Exception -> 0x01f6 }
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = 0;
        r4 = r4.a(r13, r5);	 Catch:{ Exception -> 0x01f6 }
        r10.a = r4;	 Catch:{ Exception -> 0x01f6 }
        r4 = r0;
        r0 = r2;
    L_0x00a3:
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 != 0) goto L_0x00b3;
    L_0x00a7:
        r2 = 0;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x00f1;
    L_0x00ad:
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x00f1;
    L_0x00b3:
        r2 = java.lang.System.currentTimeMillis();
        r6 = r10.j;
        if (r6 == 0) goto L_0x00cb;
    L_0x00bb:
        r6 = r10.j;
        if (r6 == 0) goto L_0x00f1;
    L_0x00bf:
        r6 = 0;
        r4 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1));
        if (r4 != 0) goto L_0x00f1;
    L_0x00c5:
        r4 = 0;
        r0 = (r0 > r4 ? 1 : (r0 == r4 ? 0 : -1));
        if (r0 != 0) goto L_0x00f1;
    L_0x00cb:
        r0 = r10.a;
        if (r0 == 0) goto L_0x00de;
    L_0x00cf:
        r0 = r10.a;
        r0 = r0.edit();
        r1 = "t2";
        r0.putLong(r1, r2);
        r0.commit();
    L_0x00de:
        r0 = r10.a;	 Catch:{ Exception -> 0x01ef }
        if (r0 == 0) goto L_0x00f1;
    L_0x00e2:
        r0 = r10.a;	 Catch:{ Exception -> 0x01ef }
        r0 = r0.a();	 Catch:{ Exception -> 0x01ef }
        r1 = "t2";
        r0.a(r1, r2);	 Catch:{ Exception -> 0x01ef }
        r0.commit();	 Catch:{ Exception -> 0x01ef }
    L_0x00f1:
        return;
    L_0x00f2:
        r3 = move-exception;
        r3.printStackTrace();
        goto L_0x0052;
    L_0x00f8:
        r3 = "mounted";
        r3 = r2.equals(r3);
        if (r3 == 0) goto L_0x0108;
    L_0x0101:
        r2 = 1;
        r10.i = r2;
        r10.h = r2;
        goto L_0x005d;
    L_0x0108:
        r3 = "mounted_ro";
        r2 = r2.equals(r3);
        if (r2 == 0) goto L_0x0119;
    L_0x0111:
        r2 = 1;
        r10.h = r2;
        r2 = 0;
        r10.i = r2;
        goto L_0x005d;
    L_0x0119:
        r2 = 0;
        r10.i = r2;
        r10.h = r2;
        goto L_0x005d;
    L_0x0120:
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 >= 0) goto L_0x0136;
    L_0x0124:
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r10.a(r4, r5);	 Catch:{ Exception -> 0x01f6 }
        r4 = 0;
        r4 = r11.getSharedPreferences(r13, r4);	 Catch:{ Exception -> 0x01f6 }
        r10.a = r4;	 Catch:{ Exception -> 0x01f6 }
        r4 = r0;
        r0 = r2;
        goto L_0x00a3;
    L_0x0136:
        r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r4 != 0) goto L_0x01ff;
    L_0x013a:
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r10.a(r4, r5);	 Catch:{ Exception -> 0x01f6 }
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = 0;
        r4 = r4.a(r13, r5);	 Catch:{ Exception -> 0x01f6 }
        r10.a = r4;	 Catch:{ Exception -> 0x01f6 }
        r4 = r0;
        r0 = r2;
        goto L_0x00a3;
    L_0x014e:
        r4 = r10.a;	 Catch:{ Exception -> 0x01f6 }
        r5 = "t2";
        r6 = 0;
        r4 = r4.getLong(r5, r6);	 Catch:{ Exception -> 0x01f6 }
        r0 = r10.a;	 Catch:{ Exception -> 0x01fb }
        r1 = "t2";
        r6 = 0;
        r0 = r0.getLong(r1, r6);	 Catch:{ Exception -> 0x01fb }
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 >= 0) goto L_0x0185;
    L_0x0168:
        r2 = 0;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x0185;
    L_0x016e:
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r10.a(r2, r3);	 Catch:{ Exception -> 0x0180 }
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = 0;
        r2 = r2.a(r13, r3);	 Catch:{ Exception -> 0x0180 }
        r10.a = r2;	 Catch:{ Exception -> 0x0180 }
        goto L_0x00a3;
    L_0x0180:
        r2 = move-exception;
        r2 = r4;
    L_0x0182:
        r4 = r2;
        goto L_0x00a3;
    L_0x0185:
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 <= 0) goto L_0x019f;
    L_0x0189:
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x019f;
    L_0x018f:
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r10.a(r2, r3);	 Catch:{ Exception -> 0x0180 }
        r2 = 0;
        r2 = r11.getSharedPreferences(r13, r2);	 Catch:{ Exception -> 0x0180 }
        r10.a = r2;	 Catch:{ Exception -> 0x0180 }
        goto L_0x00a3;
    L_0x019f:
        r2 = 0;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x01bb;
    L_0x01a5:
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x01bb;
    L_0x01ab:
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r10.a(r2, r3);	 Catch:{ Exception -> 0x0180 }
        r2 = 0;
        r2 = r11.getSharedPreferences(r13, r2);	 Catch:{ Exception -> 0x0180 }
        r10.a = r2;	 Catch:{ Exception -> 0x0180 }
        goto L_0x00a3;
    L_0x01bb:
        r2 = 0;
        r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
        if (r2 != 0) goto L_0x01d9;
    L_0x01c1:
        r2 = 0;
        r2 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r2 <= 0) goto L_0x01d9;
    L_0x01c7:
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r10.a(r2, r3);	 Catch:{ Exception -> 0x0180 }
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = 0;
        r2 = r2.a(r13, r3);	 Catch:{ Exception -> 0x0180 }
        r10.a = r2;	 Catch:{ Exception -> 0x0180 }
        goto L_0x00a3;
    L_0x01d9:
        r2 = (r4 > r0 ? 1 : (r4 == r0 ? 0 : -1));
        if (r2 != 0) goto L_0x00a3;
    L_0x01dd:
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r10.a(r2, r3);	 Catch:{ Exception -> 0x0180 }
        r2 = r10.a;	 Catch:{ Exception -> 0x0180 }
        r3 = 0;
        r2 = r2.a(r13, r3);	 Catch:{ Exception -> 0x0180 }
        r10.a = r2;	 Catch:{ Exception -> 0x0180 }
        goto L_0x00a3;
    L_0x01ef:
        r0 = move-exception;
        goto L_0x00f1;
    L_0x01f2:
        r2 = move-exception;
        r2 = r0;
        r0 = r4;
        goto L_0x0182;
    L_0x01f6:
        r4 = move-exception;
        r8 = r2;
        r2 = r0;
        r0 = r8;
        goto L_0x0182;
    L_0x01fb:
        r0 = move-exception;
        r0 = r2;
        r2 = r4;
        goto L_0x0182;
    L_0x01ff:
        r4 = r0;
        r0 = r2;
        goto L_0x00a3;
    L_0x0203:
        r8 = r4;
        r4 = r0;
        r0 = r8;
        goto L_0x00a3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.c.a.c.<init>(android.content.Context, java.lang.String, java.lang.String, boolean, boolean):void");
    }

    private d a(String str) {
        File a = a(str);
        if (a == null) {
            return null;
        }
        this.a = new d(a.getAbsolutePath());
        return this.a;
    }

    /* renamed from: a */
    private File m5a(String str) {
        if (Environment.getExternalStorageDirectory() == null) {
            return null;
        }
        File file = new File(String.format("%s%s%s", new Object[]{Environment.getExternalStorageDirectory().getAbsolutePath(), File.separator, str}));
        if (file == null || file.exists()) {
            return file;
        }
        file.mkdirs();
        return file;
    }

    private void a(SharedPreferences sharedPreferences, b bVar) {
        if (sharedPreferences != null && bVar != null) {
            a a = bVar.a();
            if (a != null) {
                a.b();
                for (Entry entry : sharedPreferences.getAll().entrySet()) {
                    String str = (String) entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        a.a(str, (String) value);
                    } else if (value instanceof Integer) {
                        a.a(str, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        a.a(str, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        a.a(str, ((Float) value).floatValue());
                    } else if (value instanceof Boolean) {
                        a.a(str, ((Boolean) value).booleanValue());
                    }
                }
                a.commit();
            }
        }
    }

    private void a(b bVar, SharedPreferences sharedPreferences) {
        if (bVar != null && sharedPreferences != null) {
            Editor edit = sharedPreferences.edit();
            if (edit != null) {
                edit.clear();
                for (Entry entry : bVar.getAll().entrySet()) {
                    String str = (String) entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        edit.putString(str, (String) value);
                    } else if (value instanceof Integer) {
                        edit.putInt(str, ((Integer) value).intValue());
                    } else if (value instanceof Long) {
                        edit.putLong(str, ((Long) value).longValue());
                    } else if (value instanceof Float) {
                        edit.putFloat(str, ((Float) value).floatValue());
                    } else if (value instanceof Boolean) {
                        edit.putBoolean(str, ((Boolean) value).booleanValue());
                    }
                }
                edit.commit();
            }
        }
    }

    private boolean b() {
        if (this.a == null) {
            return false;
        }
        boolean a = this.a.a();
        if (a) {
            return a;
        }
        commit();
        return a;
    }

    private void c() {
        if (this.a == null && this.a != null) {
            this.a = this.a.edit();
        }
        if (this.i && this.a == null && this.a != null) {
            this.a = this.a.a();
        }
        b();
    }

    public void putString(String key, String value) {
        if (!i.a(key) && !key.equals("t")) {
            c();
            if (this.a != null) {
                this.a.putString(key, value);
            }
            if (this.a != null) {
                this.a.a(key, value);
            }
        }
    }

    public void remove(String key) {
        if (!i.a(key) && !key.equals("t")) {
            c();
            if (this.a != null) {
                this.a.remove(key);
            }
            if (this.a != null) {
                this.a.a(key);
            }
        }
    }

    public boolean commit() {
        boolean z = true;
        long currentTimeMillis = System.currentTimeMillis();
        if (this.a != null) {
            if (!(this.j || this.a == null)) {
                this.a.putLong("t", currentTimeMillis);
            }
            if (!this.a.commit()) {
                z = false;
            }
        }
        if (!(this.a == null || this.mContext == null)) {
            this.a = this.mContext.getSharedPreferences(this.e, 0);
        }
        String str = null;
        try {
            str = Environment.getExternalStorageState();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!i.a(str)) {
            if (str.equals("mounted")) {
                if (this.a == null) {
                    d a = a(this.f);
                    if (a != null) {
                        this.a = a.a(this.e, 0);
                        if (this.j) {
                            a(this.a, this.a);
                        } else {
                            a(this.a, this.a);
                        }
                        this.a = this.a.a();
                    }
                } else if (!(this.a == null || this.a.commit())) {
                    z = false;
                }
            }
            if (str.equals("mounted") || (str.equals("mounted_ro") && this.a != null)) {
                try {
                    if (this.a != null) {
                        this.a = this.a.a(this.e, 0);
                    }
                } catch (Exception e2) {
                }
            }
        }
        return z;
    }

    public String getString(String key) {
        b();
        if (this.a != null) {
            String string = this.a.getString(key, "");
            if (!i.a(string)) {
                return string;
            }
        }
        if (this.a != null) {
            return this.a.getString(key, "");
        }
        return "";
    }
}
