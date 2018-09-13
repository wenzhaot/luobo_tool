package com.tencent.bugly.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public final class y {
    public static boolean a = true;
    private static SimpleDateFormat b;
    private static int c = 5120;
    private static StringBuilder d;
    private static StringBuilder e;
    private static boolean f;
    private static a g;
    private static String h;
    private static String i;
    private static Context j;
    private static String k;
    private static boolean l;
    private static int m;
    private static final Object n = new Object();

    /* compiled from: BUGLY */
    public static class a {
        private boolean a;
        private File b;
        private String c;
        private long d;
        private long e = 30720;

        public a(String str) {
            if (str != null && !str.equals("")) {
                this.c = str;
                this.a = a();
            }
        }

        private boolean a() {
            try {
                this.b = new File(this.c);
                if (!this.b.exists() || this.b.delete()) {
                    if (!this.b.createNewFile()) {
                        this.a = false;
                        return false;
                    }
                    return true;
                }
                this.a = false;
                return false;
            } catch (Throwable th) {
                this.a = false;
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:34:? A:{SYNTHETIC, RETURN} */
        /* JADX WARNING: Removed duplicated region for block: B:16:0x0033 A:{SYNTHETIC, Splitter: B:16:0x0033} */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x003d A:{SYNTHETIC, Splitter: B:22:0x003d} */
        /* JADX WARNING: Removed duplicated region for block: B:22:0x003d A:{SYNTHETIC, Splitter: B:22:0x003d} */
        public final boolean a(java.lang.String r9) {
            /*
            r8 = this;
            r1 = 1;
            r0 = 0;
            r2 = r8.a;
            if (r2 != 0) goto L_0x0007;
        L_0x0006:
            return r0;
        L_0x0007:
            r3 = 0;
            r2 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x002c, all -> 0x0039 }
            r4 = r8.b;	 Catch:{ Throwable -> 0x002c, all -> 0x0039 }
            r5 = 1;
            r2.<init>(r4, r5);	 Catch:{ Throwable -> 0x002c, all -> 0x0039 }
            r3 = "UTF-8";
            r3 = r9.getBytes(r3);	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r2.write(r3);	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r2.flush();	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r2.close();	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r4 = r8.d;	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r3 = r3.length;	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r6 = (long) r3;	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r4 = r4 + r6;
            r8.d = r4;	 Catch:{ Throwable -> 0x004a, all -> 0x0045 }
            r2.close();	 Catch:{ IOException -> 0x0041 }
        L_0x002a:
            r0 = r1;
            goto L_0x0006;
        L_0x002c:
            r1 = move-exception;
            r1 = r3;
        L_0x002e:
            r2 = 0;
            r8.a = r2;	 Catch:{ all -> 0x0047 }
            if (r1 == 0) goto L_0x0006;
        L_0x0033:
            r1.close();	 Catch:{ IOException -> 0x0037 }
            goto L_0x0006;
        L_0x0037:
            r1 = move-exception;
            goto L_0x0006;
        L_0x0039:
            r0 = move-exception;
            r2 = r3;
        L_0x003b:
            if (r2 == 0) goto L_0x0040;
        L_0x003d:
            r2.close();	 Catch:{ IOException -> 0x0043 }
        L_0x0040:
            throw r0;
        L_0x0041:
            r0 = move-exception;
            goto L_0x002a;
        L_0x0043:
            r1 = move-exception;
            goto L_0x0040;
        L_0x0045:
            r0 = move-exception;
            goto L_0x003b;
        L_0x0047:
            r0 = move-exception;
            r2 = r1;
            goto L_0x003b;
        L_0x004a:
            r1 = move-exception;
            r1 = r2;
            goto L_0x002e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.proguard.y.a.a(java.lang.String):boolean");
        }
    }

    static {
        b = null;
        try {
            b = new SimpleDateFormat("MM-dd HH:mm:ss");
        } catch (Throwable th) {
        }
    }

    private static boolean b(String str, String str2, String str3) {
        try {
            com.tencent.bugly.crashreport.common.info.a b = com.tencent.bugly.crashreport.common.info.a.b();
            if (!(b == null || b.D == null)) {
                return b.D.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!x.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static synchronized void a(Context context) {
        synchronized (y.class) {
            if (!(l || context == null || !a)) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.crashreport.common.info.a a = com.tencent.bugly.crashreport.common.info.a.a(context);
                    h = a.d;
                    a.getClass();
                    i = "";
                    k = j.getFilesDir().getPath() + "/buglylog_" + h + "_" + i + ".txt";
                    m = Process.myPid();
                } catch (Throwable th) {
                }
                l = true;
            }
        }
    }

    public static void a(int i) {
        synchronized (n) {
            c = i;
            if (i < 0) {
                c = 0;
            } else if (i > 10240) {
                c = 10240;
            }
        }
    }

    public static void a(String str, String str2, Throwable th) {
        if (th != null) {
            String message = th.getMessage();
            if (message == null) {
                message = "";
            }
            a(str, str2, message + 10 + z.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (y.class) {
            if (l && a) {
                b(str, str2, str3);
                long myTid = (long) Process.myTid();
                d.setLength(0);
                if (str3.length() > 30720) {
                    str3 = str3.substring(str3.length() - 30720, str3.length() - 1);
                }
                Date date = new Date();
                d.append(b != null ? b.format(date) : date.toString()).append(" ").append(m).append(" ").append(myTid).append(" ").append(str).append(" ").append(str2).append(": ").append(str3).append("\u0001\r\n");
                final String stringBuilder = d.toString();
                synchronized (n) {
                    e.append(stringBuilder);
                    if (e.length() <= c) {
                    } else if (f) {
                    } else {
                        f = true;
                        w.a().a(new Runnable() {
                            public final void run() {
                                synchronized (y.n) {
                                    try {
                                        if (y.g == null) {
                                            y.g = new a(y.k);
                                        } else if (y.g.b == null || y.g.b.length() + ((long) y.e.length()) > y.g.e) {
                                            y.g.a();
                                        }
                                        if (y.g.a) {
                                            y.g.a(y.e.toString());
                                            y.e.setLength(0);
                                        } else {
                                            y.e.setLength(0);
                                            y.e.append(stringBuilder);
                                        }
                                        y.f = false;
                                    } catch (Throwable th) {
                                    }
                                }
                            }
                        });
                    }
                }
            }
        }
    }

    public static byte[] a() {
        byte[] bArr = null;
        if (a) {
            synchronized (n) {
                try {
                    File file;
                    if (g == null || !g.a) {
                        file = bArr;
                    } else {
                        file = g.b;
                    }
                    if (e.length() == 0 && file == null) {
                    } else {
                        bArr = z.a(file, e.toString(), "BuglyLog.txt");
                    }
                } catch (Throwable th) {
                }
            }
        }
        return bArr;
    }
}
