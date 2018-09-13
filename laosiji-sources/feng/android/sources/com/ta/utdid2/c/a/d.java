package com.ta.utdid2.c.a;

import com.ta.utdid2.c.a.b.b;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

/* compiled from: TransactionXMLFile */
public class d {
    private static final Object c = new Object();
    private File a;
    /* renamed from: a */
    private HashMap<File, a> f17a = new HashMap();
    private final Object b = new Object();

    /* compiled from: TransactionXMLFile */
    private static final class a implements b {
        private static final Object d = new Object();
        private WeakHashMap<b, Object> a;
        private final File b;
        private final int c;
        /* renamed from: c */
        private final File f18c;
        /* renamed from: c */
        private Map f19c;
        private boolean k = false;

        /* compiled from: TransactionXMLFile */
        public final class a implements com.ta.utdid2.c.a.b.a {
            private final Map<String, Object> d = new HashMap();
            private boolean l = false;

            public com.ta.utdid2.c.a.b.a a(String str, String str2) {
                synchronized (this) {
                    this.d.put(str, str2);
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a a(String str, int i) {
                synchronized (this) {
                    this.d.put(str, Integer.valueOf(i));
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a a(String str, long j) {
                synchronized (this) {
                    this.d.put(str, Long.valueOf(j));
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a a(String str, float f) {
                synchronized (this) {
                    this.d.put(str, Float.valueOf(f));
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a a(String str, boolean z) {
                synchronized (this) {
                    this.d.put(str, Boolean.valueOf(z));
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a a(String str) {
                synchronized (this) {
                    this.d.put(str, this);
                }
                return this;
            }

            public com.ta.utdid2.c.a.b.a b() {
                synchronized (this) {
                    this.l = true;
                }
                return this;
            }

            public boolean commit() {
                List list;
                String str;
                boolean a;
                Object obj = null;
                synchronized (d.a()) {
                    if (a.this.c.size() > 0) {
                        obj = 1;
                    }
                    Set hashSet;
                    if (obj != null) {
                        ArrayList arrayList = new ArrayList();
                        hashSet = new HashSet(a.this.c.keySet());
                        list = arrayList;
                    } else {
                        hashSet = null;
                        list = null;
                    }
                    synchronized (this) {
                        if (this.l) {
                            a.this.c.clear();
                            this.l = false;
                        }
                        for (Entry entry : this.d.entrySet()) {
                            str = (String) entry.getKey();
                            a value = entry.getValue();
                            if (value == this) {
                                a.this.c.remove(str);
                            } else {
                                a.this.c.put(str, value);
                            }
                            if (obj != null) {
                                list.add(str);
                            }
                        }
                        this.d.clear();
                    }
                    a = a.this.c;
                    if (a) {
                        a.this.a(true);
                    }
                }
                if (obj != null) {
                    for (int size = list.size() - 1; size >= 0; size--) {
                        str = (String) list.get(size);
                        for (b bVar : hashSet) {
                            if (bVar != null) {
                                bVar.a(a.this, str);
                            }
                        }
                    }
                }
                return a;
            }
        }

        a(File file, int i, Map map) {
            this.b = file;
            this.c = d.a(file);
            this.c = i;
            if (map == null) {
                map = new HashMap();
            }
            this.c = map;
            this.a = new WeakHashMap();
        }

        /* renamed from: a */
        public boolean m6a() {
            if (this.b == null || !new File(this.b.getAbsolutePath()).exists()) {
                return false;
            }
            return true;
        }

        public void a(boolean z) {
            synchronized (this) {
                this.k = z;
            }
        }

        public boolean c() {
            boolean z;
            synchronized (this) {
                z = this.k;
            }
            return z;
        }

        public void a(Map map) {
            if (map != null) {
                synchronized (this) {
                    this.c = map;
                }
            }
        }

        public Map<String, ?> getAll() {
            Map hashMap;
            synchronized (this) {
                hashMap = new HashMap(this.c);
            }
            return hashMap;
        }

        public String getString(String key, String defValue) {
            String str;
            synchronized (this) {
                str = (String) this.c.get(key);
                if (str == null) {
                    str = defValue;
                }
            }
            return str;
        }

        public long getLong(String key, long defValue) {
            synchronized (this) {
                Long l = (Long) this.c.get(key);
                if (l != null) {
                    defValue = l.longValue();
                }
            }
            return defValue;
        }

        public com.ta.utdid2.c.a.b.a a() {
            return new a();
        }

        private FileOutputStream a(File file) {
            try {
                return new FileOutputStream(file);
            } catch (FileNotFoundException e) {
                if (!file.getParentFile().mkdir()) {
                    return null;
                }
                try {
                    return new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    return null;
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:20:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:15:0x0041  */
        private boolean d() {
            /*
            r3 = this;
            r0 = 0;
            r1 = r3.b;
            r1 = r1.exists();
            if (r1 == 0) goto L_0x0021;
        L_0x0009:
            r1 = r3.c;
            r1 = r1.exists();
            if (r1 != 0) goto L_0x001c;
        L_0x0011:
            r1 = r3.b;
            r2 = r3.c;
            r1 = r1.renameTo(r2);
            if (r1 != 0) goto L_0x0021;
        L_0x001b:
            return r0;
        L_0x001c:
            r1 = r3.b;
            r1.delete();
        L_0x0021:
            r1 = r3.b;	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            r1 = r3.a(r1);	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            if (r1 == 0) goto L_0x001b;
        L_0x0029:
            r2 = r3.c;	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            com.ta.utdid2.c.a.e.a(r2, r1);	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            r1.close();	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            r1 = r3.c;	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            r1.delete();	 Catch:{ XmlPullParserException -> 0x0047, IOException -> 0x0038 }
            r0 = 1;
            goto L_0x001b;
        L_0x0038:
            r1 = move-exception;
        L_0x0039:
            r1 = r3.b;
            r1 = r1.exists();
            if (r1 == 0) goto L_0x001b;
        L_0x0041:
            r1 = r3.b;
            r1.delete();
            goto L_0x001b;
        L_0x0047:
            r1 = move-exception;
            goto L_0x0039;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.c.a.d.a.d():boolean");
        }
    }

    public d(String str) {
        if (str == null || str.length() <= 0) {
            throw new RuntimeException("Directory can not be empty");
        }
        this.a = new File(str);
    }

    private File a(File file, String str) {
        if (str.indexOf(File.separatorChar) < 0) {
            return new File(file, str);
        }
        throw new IllegalArgumentException("File " + str + " contains a path separator");
    }

    private File a() {
        File file;
        synchronized (this.b) {
            file = this.a;
        }
        return file;
    }

    private File b(String str) {
        return a(a(), new StringBuilder(String.valueOf(str)).append(".xml").toString());
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0082 A:{SYNTHETIC, Splitter: B:50:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0091 A:{SYNTHETIC, Splitter: B:58:0x0091} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0082 A:{SYNTHETIC, Splitter: B:50:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:66:0x00a0 A:{SYNTHETIC, Splitter: B:66:0x00a0} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0082 A:{SYNTHETIC, Splitter: B:50:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0082 A:{SYNTHETIC, Splitter: B:50:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x007d A:{SYNTHETIC, Splitter: B:47:0x007d} */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x0082 A:{SYNTHETIC, Splitter: B:50:0x0082} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x00bc A:{SYNTHETIC, Splitter: B:86:0x00bc} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:94:0x00c9 A:{SYNTHETIC, Splitter: B:94:0x00c9} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:102:0x00d6 A:{SYNTHETIC, Splitter: B:102:0x00d6} */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0054  */
    /* JADX WARNING: Removed duplicated region for block: B:78:0x00b1 A:{SYNTHETIC, Splitter: B:78:0x00b1} */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x00aa A:{SYNTHETIC, Splitter: B:72:0x00aa} */
    public com.ta.utdid2.c.a.b a(java.lang.String r11, int r12) {
        /*
        r10 = this;
        r1 = 0;
        r4 = r10.b(r11);
        r2 = c;
        monitor-enter(r2);
        r0 = r10.a;	 Catch:{ all -> 0x005e }
        r0 = r0.get(r4);	 Catch:{ all -> 0x005e }
        r0 = (com.ta.utdid2.c.a.d.a) r0;	 Catch:{ all -> 0x005e }
        if (r0 == 0) goto L_0x001a;
    L_0x0012:
        r3 = r0.c();	 Catch:{ all -> 0x005e }
        if (r3 != 0) goto L_0x001a;
    L_0x0018:
        monitor-exit(r2);	 Catch:{ all -> 0x005e }
    L_0x0019:
        return r0;
    L_0x001a:
        monitor-exit(r2);	 Catch:{ all -> 0x005e }
        r2 = a(r4);
        r3 = r2.exists();
        if (r3 == 0) goto L_0x002b;
    L_0x0025:
        r4.delete();
        r2.renameTo(r4);
    L_0x002b:
        r2 = r4.exists();
        if (r2 == 0) goto L_0x0034;
    L_0x0031:
        r4.canRead();
    L_0x0034:
        r2 = r4.exists();
        if (r2 == 0) goto L_0x0051;
    L_0x003a:
        r2 = r4.canRead();
        if (r2 == 0) goto L_0x0051;
    L_0x0040:
        r2 = new java.io.FileInputStream;	 Catch:{ XmlPullParserException -> 0x0061, FileNotFoundException -> 0x00b5, IOException -> 0x00c2, Exception -> 0x00cf, all -> 0x00fd }
        r2.<init>(r4);	 Catch:{ XmlPullParserException -> 0x0061, FileNotFoundException -> 0x00b5, IOException -> 0x00c2, Exception -> 0x00cf, all -> 0x00fd }
        r1 = com.ta.utdid2.c.a.e.a(r2);	 Catch:{ XmlPullParserException -> 0x0119, FileNotFoundException -> 0x010d, IOException -> 0x0108, Exception -> 0x0103, all -> 0x0100 }
        r2.close();	 Catch:{ XmlPullParserException -> 0x0119, FileNotFoundException -> 0x010d, IOException -> 0x0108, Exception -> 0x0103, all -> 0x0100 }
        if (r2 == 0) goto L_0x0051;
    L_0x004e:
        r2.close();	 Catch:{ Throwable -> 0x00fa }
    L_0x0051:
        r2 = c;
        monitor-enter(r2);
        if (r0 == 0) goto L_0x00de;
    L_0x0056:
        r0.a(r1);	 Catch:{ all -> 0x005b }
    L_0x0059:
        monitor-exit(r2);	 Catch:{ all -> 0x005b }
        goto L_0x0019;
    L_0x005b:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x005b }
        throw r0;
    L_0x005e:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x005e }
        throw r0;
    L_0x0061:
        r2 = move-exception;
        r2 = r1;
    L_0x0063:
        r3 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0088, IOException -> 0x0097, all -> 0x00a6 }
        r3.<init>(r4);	 Catch:{ FileNotFoundException -> 0x0088, IOException -> 0x0097, all -> 0x00a6 }
        r2 = r3.available();	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        r2 = new byte[r2];	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        r3.read(r2);	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        r5 = new java.lang.String;	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        r6 = 0;
        r7 = r2.length;	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        r8 = "UTF-8";
        r5.<init>(r2, r6, r7, r8);	 Catch:{ FileNotFoundException -> 0x0116, IOException -> 0x0114 }
        if (r3 == 0) goto L_0x0080;
    L_0x007d:
        r3.close();	 Catch:{ Throwable -> 0x00f6 }
    L_0x0080:
        if (r3 == 0) goto L_0x0051;
    L_0x0082:
        r3.close();	 Catch:{ Throwable -> 0x0086 }
        goto L_0x0051;
    L_0x0086:
        r2 = move-exception;
        goto L_0x0051;
    L_0x0088:
        r3 = move-exception;
        r9 = r3;
        r3 = r2;
        r2 = r9;
    L_0x008c:
        r2.printStackTrace();	 Catch:{ all -> 0x0112 }
        if (r3 == 0) goto L_0x0080;
    L_0x0091:
        r3.close();	 Catch:{ Throwable -> 0x0095 }
        goto L_0x0080;
    L_0x0095:
        r2 = move-exception;
        goto L_0x0080;
    L_0x0097:
        r3 = move-exception;
        r9 = r3;
        r3 = r2;
        r2 = r9;
    L_0x009b:
        r2.printStackTrace();	 Catch:{ all -> 0x0112 }
        if (r3 == 0) goto L_0x0080;
    L_0x00a0:
        r3.close();	 Catch:{ Throwable -> 0x00a4 }
        goto L_0x0080;
    L_0x00a4:
        r2 = move-exception;
        goto L_0x0080;
    L_0x00a6:
        r0 = move-exception;
        r3 = r2;
    L_0x00a8:
        if (r3 == 0) goto L_0x00ad;
    L_0x00aa:
        r3.close();	 Catch:{ Throwable -> 0x00f4 }
    L_0x00ad:
        throw r0;	 Catch:{ all -> 0x00ae }
    L_0x00ae:
        r0 = move-exception;
    L_0x00af:
        if (r3 == 0) goto L_0x00b4;
    L_0x00b1:
        r3.close();	 Catch:{ Throwable -> 0x00f8 }
    L_0x00b4:
        throw r0;
    L_0x00b5:
        r2 = move-exception;
        r3 = r1;
    L_0x00b7:
        r2.printStackTrace();	 Catch:{ all -> 0x00ae }
        if (r3 == 0) goto L_0x0051;
    L_0x00bc:
        r3.close();	 Catch:{ Throwable -> 0x00c0 }
        goto L_0x0051;
    L_0x00c0:
        r2 = move-exception;
        goto L_0x0051;
    L_0x00c2:
        r2 = move-exception;
        r3 = r1;
    L_0x00c4:
        r2.printStackTrace();	 Catch:{ all -> 0x00ae }
        if (r3 == 0) goto L_0x0051;
    L_0x00c9:
        r3.close();	 Catch:{ Throwable -> 0x00cd }
        goto L_0x0051;
    L_0x00cd:
        r2 = move-exception;
        goto L_0x0051;
    L_0x00cf:
        r2 = move-exception;
        r3 = r1;
    L_0x00d1:
        r2.printStackTrace();	 Catch:{ all -> 0x00ae }
        if (r3 == 0) goto L_0x0051;
    L_0x00d6:
        r3.close();	 Catch:{ Throwable -> 0x00db }
        goto L_0x0051;
    L_0x00db:
        r2 = move-exception;
        goto L_0x0051;
    L_0x00de:
        r0 = r10.a;	 Catch:{ all -> 0x005b }
        r0 = r0.get(r4);	 Catch:{ all -> 0x005b }
        r0 = (com.ta.utdid2.c.a.d.a) r0;	 Catch:{ all -> 0x005b }
        if (r0 != 0) goto L_0x0059;
    L_0x00e8:
        r0 = new com.ta.utdid2.c.a.d$a;	 Catch:{ all -> 0x005b }
        r0.<init>(r4, r12, r1);	 Catch:{ all -> 0x005b }
        r1 = r10.a;	 Catch:{ all -> 0x005b }
        r1.put(r4, r0);	 Catch:{ all -> 0x005b }
        goto L_0x0059;
    L_0x00f4:
        r1 = move-exception;
        goto L_0x00ad;
    L_0x00f6:
        r2 = move-exception;
        goto L_0x0080;
    L_0x00f8:
        r1 = move-exception;
        goto L_0x00b4;
    L_0x00fa:
        r2 = move-exception;
        goto L_0x0051;
    L_0x00fd:
        r0 = move-exception;
        r3 = r1;
        goto L_0x00af;
    L_0x0100:
        r0 = move-exception;
        r3 = r2;
        goto L_0x00af;
    L_0x0103:
        r3 = move-exception;
        r9 = r3;
        r3 = r2;
        r2 = r9;
        goto L_0x00d1;
    L_0x0108:
        r3 = move-exception;
        r9 = r3;
        r3 = r2;
        r2 = r9;
        goto L_0x00c4;
    L_0x010d:
        r3 = move-exception;
        r9 = r3;
        r3 = r2;
        r2 = r9;
        goto L_0x00b7;
    L_0x0112:
        r0 = move-exception;
        goto L_0x00a8;
    L_0x0114:
        r2 = move-exception;
        goto L_0x009b;
    L_0x0116:
        r2 = move-exception;
        goto L_0x008c;
    L_0x0119:
        r3 = move-exception;
        goto L_0x0063;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ta.utdid2.c.a.d.a(java.lang.String, int):com.ta.utdid2.c.a.b");
    }

    private static File a(File file) {
        return new File(file.getPath() + ".bak");
    }
}
