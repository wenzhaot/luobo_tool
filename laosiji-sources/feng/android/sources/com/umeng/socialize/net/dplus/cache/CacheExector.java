package com.umeng.socialize.net.dplus.cache;

import android.text.TextUtils;
import android.util.Log;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CACHE;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;

public class CacheExector {
    private static final String a = CacheExector.class.getSimpleName();
    private final int b = 32;
    private final int c = 5120;
    private final int d = 8;
    private String e = null;

    public CacheExector(String str) {
        this.e = str;
    }

    public double checkSize(String str) {
        double d = 0.0d;
        File a = a();
        if (a != null && a.isDirectory()) {
            File[] listFiles = a.listFiles();
            int i = 0;
            while (i < listFiles.length) {
                if (listFiles[i] != null && listFiles[i].getName().contains(str)) {
                    d += a(listFiles[i].length());
                }
                i++;
            }
        }
        return d;
    }

    public boolean save(String str, String str2) {
        boolean z;
        Throwable e;
        Throwable th;
        Closeable closeable = null;
        File b = b(a(), str2);
        if (b == null) {
            return false;
        }
        Closeable startWrite;
        AtomicFile atomicFile = new AtomicFile(b);
        try {
            startWrite = atomicFile.startWrite(true);
        } catch (Throwable e2) {
            SLog.error(CACHE.CACHEFILE, e2);
            deleteFile(b.getName());
            startWrite = null;
        }
        if (startWrite == null) {
            return false;
        }
        Closeable outputStreamWriter;
        try {
            Closeable closeable2;
            if (TextUtils.isEmpty(str)) {
                closeable2 = null;
                z = false;
            } else {
                outputStreamWriter = new OutputStreamWriter(startWrite);
                try {
                    closeable2 = new BufferedWriter(outputStreamWriter);
                } catch (IOException e3) {
                    e = e3;
                    try {
                        atomicFile.failWrite(startWrite);
                        SLog.error(CACHE.CACHEFILE, e);
                        a(closeable);
                        a(outputStreamWriter);
                        a(startWrite);
                        z = false;
                        return z;
                    } catch (Throwable th2) {
                        e = th2;
                        a(closeable);
                        a(outputStreamWriter);
                        a(startWrite);
                        throw e;
                    }
                }
                try {
                    closeable2.write(str);
                    closeable2.newLine();
                    closeable2.flush();
                    atomicFile.finishWrite(startWrite);
                    closeable = outputStreamWriter;
                    z = true;
                } catch (Throwable e4) {
                    th = e4;
                    closeable = closeable2;
                    e = th;
                    atomicFile.failWrite(startWrite);
                    SLog.error(CACHE.CACHEFILE, e);
                    a(closeable);
                    a(outputStreamWriter);
                    a(startWrite);
                    z = false;
                    return z;
                } catch (Throwable e42) {
                    th = e42;
                    closeable = closeable2;
                    e = th;
                    a(closeable);
                    a(outputStreamWriter);
                    a(startWrite);
                    throw e;
                }
            }
            a(closeable2);
            a(closeable);
            a(startWrite);
        } catch (IOException e5) {
            e = e5;
            outputStreamWriter = null;
            atomicFile.failWrite(startWrite);
            SLog.error(CACHE.CACHEFILE, e);
            a(closeable);
            a(outputStreamWriter);
            a(startWrite);
            z = false;
            return z;
        } catch (Throwable th3) {
            e = th3;
            outputStreamWriter = null;
            a(closeable);
            a(outputStreamWriter);
            a(startWrite);
            throw e;
        }
        return z;
    }

    private File a() {
        if (TextUtils.isEmpty(this.e)) {
            Log.d(a, "Couldn't create directory mDirPath is null");
            return null;
        }
        File file = new File(this.e);
        if (file.exists() || file.mkdirs()) {
            return file;
        }
        Log.d(a, "Couldn't create directory" + this.e);
        return null;
    }

    private File a(File file, String str) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        return new File(file, a(str));
    }

    private String a(String str) {
        return String.valueOf(System.currentTimeMillis()) + str;
    }

    private File b(File file, String str) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        String[] list = file.list();
        if (list == null || list.length <= 0) {
            return a(file, str);
        }
        File c = c(file, str);
        if (c == null) {
            return a(file, str);
        }
        return c;
    }

    private File c(File file, String str) {
        File[] a = a(file);
        if (a == null || a.length <= 0 || 0 >= a.length) {
            return null;
        }
        File file2 = a[0];
        if (a(file2.length()) <= 32.0d) {
            return file2;
        }
        return null;
    }

    private File[] a(File file) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        File[] listFiles = file.listFiles();
        Arrays.sort(listFiles, b());
        return listFiles;
    }

    private Comparator<File> b() {
        return new Comparator<File>() {
            public int compare(File file, File file2) {
                return Long.valueOf(file.length()).compareTo(Long.valueOf(file2.length()));
            }
        };
    }

    public boolean deleteFile(String str) {
        int i = 0;
        File[] listFiles = a().listFiles();
        boolean z = false;
        while (i < listFiles.length) {
            if (listFiles[i] != null && listFiles[i].getName().contains(str)) {
                z = listFiles[i].delete();
            }
            i++;
        }
        return z;
    }

    private double a(long j) {
        return j <= 0 ? 0.0d : ((double) j) / 1024.0d;
    }

    private void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable e) {
                SLog.error(CACHE.CACHEFILE, e);
            }
        }
    }

    /* JADX WARNING: Missing block: B:31:0x0085, code:
            if (r1 == null) goto L_0x008e;
     */
    /* JADX WARNING: Missing block: B:33:?, code:
            r1.create(r6.toString());
     */
    /* JADX WARNING: Missing block: B:34:0x008e, code:
            a(r5);
            a(r3);
            a(r2);
     */
    /* JADX WARNING: Missing block: B:55:?, code:
            return r1;
     */
    public <T extends com.umeng.socialize.net.dplus.cache.IReader> T readFile(java.lang.String r12, java.lang.Class<T> r13) {
        /*
        r11 = this;
        r0 = 0;
        r1 = r11.a();
        r2 = r11.d(r1, r12);
        if (r2 != 0) goto L_0x000c;
    L_0x000b:
        return r0;
    L_0x000c:
        r1 = new com.umeng.socialize.net.dplus.cache.AtomicFile;
        r1.<init>(r2);
        r1 = r1.openRead();	 Catch:{ IOException -> 0x0076 }
        r5 = r1;
    L_0x0016:
        if (r5 == 0) goto L_0x000b;
    L_0x0018:
        r1 = r2.getName();	 Catch:{ IOException -> 0x00ae, all -> 0x009a }
        r1 = r11.a(r1, r13);	 Catch:{ IOException -> 0x00ae, all -> 0x009a }
        r3 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x00ae, all -> 0x009a }
        r3.<init>(r5);	 Catch:{ IOException -> 0x00ae, all -> 0x009a }
        r2 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x00b2, all -> 0x00a8 }
        r2.<init>(r3);	 Catch:{ IOException -> 0x00b2, all -> 0x00a8 }
        r4 = 0;
        r6 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0066 }
        r6.<init>();	 Catch:{ IOException -> 0x0066 }
    L_0x0030:
        r7 = r2.readLine();	 Catch:{ IOException -> 0x0066 }
        if (r7 == 0) goto L_0x0085;
    L_0x0036:
        r4 = r4 + 1;
        r8 = a;	 Catch:{ IOException -> 0x0066 }
        r9 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0066 }
        r9.<init>();	 Catch:{ IOException -> 0x0066 }
        r10 = "read file:";
        r9 = r9.append(r10);	 Catch:{ IOException -> 0x0066 }
        r9 = r9.append(r4);	 Catch:{ IOException -> 0x0066 }
        r9 = r9.append(r7);	 Catch:{ IOException -> 0x0066 }
        r9 = r9.toString();	 Catch:{ IOException -> 0x0066 }
        android.util.Log.d(r8, r9);	 Catch:{ IOException -> 0x0066 }
        r8 = android.text.TextUtils.isEmpty(r7);	 Catch:{ IOException -> 0x0066 }
        if (r8 != 0) goto L_0x0030;
    L_0x005b:
        r6.append(r7);	 Catch:{ Exception -> 0x005f }
        goto L_0x0030;
    L_0x005f:
        r7 = move-exception;
        r8 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;	 Catch:{ IOException -> 0x0066 }
        com.umeng.socialize.utils.SLog.error(r8, r7);	 Catch:{ IOException -> 0x0066 }
        goto L_0x0030;
    L_0x0066:
        r1 = move-exception;
    L_0x0067:
        r4 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;	 Catch:{ all -> 0x00ac }
        com.umeng.socialize.utils.SLog.error(r4, r1);	 Catch:{ all -> 0x00ac }
        r11.a(r5);
        r11.a(r3);
        r11.a(r2);
        goto L_0x000b;
    L_0x0076:
        r1 = move-exception;
        r3 = com.umeng.socialize.utils.UmengText.CACHE.CACHEFILE;
        com.umeng.socialize.utils.SLog.error(r3, r1);
        r1 = r2.getName();
        r11.deleteFile(r1);
        r5 = r0;
        goto L_0x0016;
    L_0x0085:
        if (r1 == 0) goto L_0x008e;
    L_0x0087:
        r4 = r6.toString();	 Catch:{ IOException -> 0x0066 }
        r1.create(r4);	 Catch:{ IOException -> 0x0066 }
    L_0x008e:
        r11.a(r5);
        r11.a(r3);
        r11.a(r2);
        r0 = r1;
        goto L_0x000b;
    L_0x009a:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        r0 = r1;
    L_0x009e:
        r11.a(r5);
        r11.a(r3);
        r11.a(r2);
        throw r0;
    L_0x00a8:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
        goto L_0x009e;
    L_0x00ac:
        r0 = move-exception;
        goto L_0x009e;
    L_0x00ae:
        r1 = move-exception;
        r2 = r0;
        r3 = r0;
        goto L_0x0067;
    L_0x00b2:
        r1 = move-exception;
        r2 = r0;
        goto L_0x0067;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.dplus.cache.CacheExector.readFile(java.lang.String, java.lang.Class):T");
    }

    private <T extends IReader> T a(String str, Class<T> cls) {
        try {
            return (IReader) cls.getConstructor(new Class[]{String.class}).newInstance(new Object[]{str});
        } catch (Throwable th) {
            SLog.error(CACHE.CACHEFILE, th);
            return null;
        }
    }

    private File d(File file, String str) {
        if (file == null || !file.isDirectory()) {
            return null;
        }
        String[] list = file.list();
        if (list == null || list.length <= 0) {
            return null;
        }
        return e(file, str);
    }

    private File e(File file, String str) {
        File[] a = a(file);
        if (a == null || a.length <= 0) {
            return null;
        }
        for (File file2 : a) {
            if (a(file2.length()) <= ((double) 40) && file2.getName().endsWith(str)) {
                return file2;
            }
            Log.e(a, "getReadableFileFromFiles:file length don't legal" + file2.length());
            deleteFile(file2.getName());
        }
        return null;
    }
}
