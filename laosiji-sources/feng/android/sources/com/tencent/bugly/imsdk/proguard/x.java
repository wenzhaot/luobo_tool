package com.tencent.bugly.imsdk.proguard;

import android.content.Context;
import android.os.Process;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: BUGLY */
public final class x {
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
    private static Object n = new Object();

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

        private synchronized boolean a() {
            boolean z = false;
            synchronized (this) {
                try {
                    this.b = new File(this.c);
                    if (!this.b.exists() || this.b.delete()) {
                        if (!this.b.createNewFile()) {
                            this.a = false;
                        }
                        z = true;
                    } else {
                        this.a = false;
                    }
                } catch (Throwable th) {
                    this.a = false;
                }
            }
            return z;
        }

        /* JADX WARNING: Removed duplicated region for block: B:21:0x0035 A:{SYNTHETIC, Splitter: B:21:0x0035} */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x003f A:{SYNTHETIC, Splitter: B:27:0x003f} */
        /* JADX WARNING: Removed duplicated region for block: B:27:0x003f A:{SYNTHETIC, Splitter: B:27:0x003f} */
        public final synchronized boolean a(java.lang.String r9) {
            /*
            r8 = this;
            r1 = 1;
            r0 = 0;
            monitor-enter(r8);
            r2 = r8.a;	 Catch:{ all -> 0x0043 }
            if (r2 != 0) goto L_0x0009;
        L_0x0007:
            monitor-exit(r8);
            return r0;
        L_0x0009:
            r3 = 0;
            r2 = new java.io.FileOutputStream;	 Catch:{ Throwable -> 0x002e, all -> 0x003b }
            r4 = r8.b;	 Catch:{ Throwable -> 0x002e, all -> 0x003b }
            r5 = 1;
            r2.<init>(r4, r5);	 Catch:{ Throwable -> 0x002e, all -> 0x003b }
            r3 = "UTF-8";
            r3 = r9.getBytes(r3);	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r2.write(r3);	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r2.flush();	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r2.close();	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r4 = r8.d;	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r3 = r3.length;	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r6 = (long) r3;	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r4 = r4 + r6;
            r8.d = r4;	 Catch:{ Throwable -> 0x004f, all -> 0x004a }
            r2.close();	 Catch:{ IOException -> 0x0046 }
        L_0x002c:
            r0 = r1;
            goto L_0x0007;
        L_0x002e:
            r1 = move-exception;
            r1 = r3;
        L_0x0030:
            r2 = 0;
            r8.a = r2;	 Catch:{ all -> 0x004c }
            if (r1 == 0) goto L_0x0007;
        L_0x0035:
            r1.close();	 Catch:{ IOException -> 0x0039 }
            goto L_0x0007;
        L_0x0039:
            r1 = move-exception;
            goto L_0x0007;
        L_0x003b:
            r0 = move-exception;
            r2 = r3;
        L_0x003d:
            if (r2 == 0) goto L_0x0042;
        L_0x003f:
            r2.close();	 Catch:{ IOException -> 0x0048 }
        L_0x0042:
            throw r0;	 Catch:{ all -> 0x0043 }
        L_0x0043:
            r0 = move-exception;
            monitor-exit(r8);
            throw r0;
        L_0x0046:
            r0 = move-exception;
            goto L_0x002c;
        L_0x0048:
            r1 = move-exception;
            goto L_0x0042;
        L_0x004a:
            r0 = move-exception;
            goto L_0x003d;
        L_0x004c:
            r0 = move-exception;
            r2 = r1;
            goto L_0x003d;
        L_0x004f:
            r1 = move-exception;
            r1 = r2;
            goto L_0x0030;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.tencent.bugly.imsdk.proguard.x.a.a(java.lang.String):boolean");
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
            com.tencent.bugly.imsdk.crashreport.common.info.a b = com.tencent.bugly.imsdk.crashreport.common.info.a.b();
            if (!(b == null || b.C == null)) {
                return b.C.appendLogToNative(str, str2, str3);
            }
        } catch (Throwable th) {
            if (!w.a(th)) {
                th.printStackTrace();
            }
        }
        return false;
    }

    public static synchronized void a(Context context) {
        synchronized (x.class) {
            if (!(l || context == null || !a)) {
                try {
                    e = new StringBuilder(0);
                    d = new StringBuilder(0);
                    j = context;
                    com.tencent.bugly.imsdk.crashreport.common.info.a a = com.tencent.bugly.imsdk.crashreport.common.info.a.a(context);
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
            a(str, str2, message + 10 + y.b(th));
        }
    }

    public static synchronized void a(String str, String str2, String str3) {
        synchronized (x.class) {
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
                        v.a().a(new Runnable() {
                            public final void run() {
                                synchronized (x.n) {
                                    try {
                                        if (x.g == null) {
                                            x.g = new a(x.k);
                                        } else if (x.g.b == null || x.g.b.length() + ((long) x.e.length()) > x.g.e) {
                                            x.g.a();
                                        }
                                        if (x.g.a) {
                                            x.g.a(x.e.toString());
                                            x.e.setLength(0);
                                        } else {
                                            x.e.setLength(0);
                                            x.e.append(stringBuilder);
                                        }
                                        x.f = false;
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

    public static byte[] a(boolean z) {
        byte[] bArr = null;
        if (a) {
            synchronized (n) {
                File a;
                if (z) {
                    try {
                        if (g != null && g.a) {
                            a = g.b;
                            if (e.length() == 0 || a != null) {
                                bArr = y.a(a, e.toString());
                            }
                        }
                    } catch (Throwable th) {
                    }
                }
                a = bArr;
                if (e.length() == 0) {
                }
                bArr = y.a(a, e.toString());
            }
        }
        return bArr;
    }
}
