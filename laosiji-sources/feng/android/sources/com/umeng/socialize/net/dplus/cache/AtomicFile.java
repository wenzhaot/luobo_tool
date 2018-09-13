package com.umeng.socialize.net.dplus.cache;

import android.util.Log;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.CACHE;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AtomicFile {
    private final File a;
    private final File b;

    public AtomicFile(File file) {
        this.a = file;
        this.b = new File(file.getPath() + ".bak");
    }

    public File getBaseFile() {
        return this.a;
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
    }

    public FileOutputStream startWrite(boolean z) throws IOException {
        if (this.a.exists()) {
            if (this.b.exists()) {
                this.a.delete();
            } else if (this.a.renameTo(this.b)) {
                a(this.b, this.a);
            } else {
                Log.w("AtomicFile", "Couldn't rename file " + this.a + " to backup file " + this.b);
            }
        }
        try {
            return new FileOutputStream(this.a, z);
        } catch (Throwable e) {
            Throwable th = e;
            if (!this.a.getParentFile().mkdirs()) {
                SLog.error(CACHE.CACHEFILE, th);
            }
            try {
                return new FileOutputStream(this.a, z);
            } catch (FileNotFoundException e2) {
                SLog.error(CACHE.CACHEFILE, th);
                return null;
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x0041  */
    private static void a(java.io.File r8, java.io.File r9) throws java.io.IOException {
        /*
        r2 = 0;
        r4 = java.lang.System.currentTimeMillis();
        r3 = new java.io.FileInputStream;	 Catch:{ all -> 0x006f }
        r3.<init>(r8);	 Catch:{ all -> 0x006f }
        r1 = new java.io.FileOutputStream;	 Catch:{ all -> 0x0072 }
        r1.<init>(r9);	 Catch:{ all -> 0x0072 }
        r0 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;
        r0 = new byte[r0];	 Catch:{ all -> 0x0038 }
    L_0x0013:
        r2 = r3.read(r0);	 Catch:{ all -> 0x0038 }
        if (r2 <= 0) goto L_0x0045;
    L_0x0019:
        r6 = 0;
        r1.write(r0, r6, r2);	 Catch:{ all -> 0x0038 }
        r6 = "AtomicFile";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0038 }
        r7.<init>();	 Catch:{ all -> 0x0038 }
        r2 = r7.append(r2);	 Catch:{ all -> 0x0038 }
        r7 = "";
        r2 = r2.append(r7);	 Catch:{ all -> 0x0038 }
        r2 = r2.toString();	 Catch:{ all -> 0x0038 }
        android.util.Log.d(r6, r2);	 Catch:{ all -> 0x0038 }
        goto L_0x0013;
    L_0x0038:
        r0 = move-exception;
        r2 = r3;
    L_0x003a:
        if (r2 == 0) goto L_0x003f;
    L_0x003c:
        r2.close();
    L_0x003f:
        if (r1 == 0) goto L_0x0044;
    L_0x0041:
        r1.close();
    L_0x0044:
        throw r0;
    L_0x0045:
        if (r3 == 0) goto L_0x004a;
    L_0x0047:
        r3.close();
    L_0x004a:
        if (r1 == 0) goto L_0x004f;
    L_0x004c:
        r1.close();
    L_0x004f:
        r0 = "AtomicFile";
        r1 = new java.lang.StringBuilder;
        r1.<init>();
        r2 = "comsum time:";
        r1 = r1.append(r2);
        r2 = java.lang.System.currentTimeMillis();
        r2 = r2 - r4;
        r1 = r1.append(r2);
        r1 = r1.toString();
        android.util.Log.d(r0, r1);
        return;
    L_0x006f:
        r0 = move-exception;
        r1 = r2;
        goto L_0x003a;
    L_0x0072:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.socialize.net.dplus.cache.AtomicFile.a(java.io.File, java.io.File):void");
    }

    public void finishWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.b.delete();
            } catch (Throwable e) {
                SLog.error(CACHE.CACHEFILE, e);
            }
        }
    }

    public void failWrite(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            a(fileOutputStream);
            try {
                fileOutputStream.close();
                this.a.delete();
                this.b.renameTo(this.a);
            } catch (Throwable e) {
                SLog.error(CACHE.CACHEFILE, e);
            }
        }
    }

    public FileInputStream openRead() throws FileNotFoundException {
        if (this.b.exists()) {
            this.a.delete();
            this.b.renameTo(this.a);
        }
        return new FileInputStream(this.a);
    }

    public byte[] readFully() throws IOException {
        int i = 0;
        FileInputStream openRead = openRead();
        try {
            Object obj = new byte[openRead.available()];
            while (true) {
                int read = openRead.read(obj, i, obj.length - i);
                if (read <= 0) {
                    break;
                }
                Object obj2;
                read += i;
                i = openRead.available();
                if (i > obj.length - read) {
                    obj2 = new byte[(i + read)];
                    System.arraycopy(obj, 0, obj2, 0, read);
                } else {
                    obj2 = obj;
                }
                obj = obj2;
                i = read;
            }
            return obj;
        } finally {
            openRead.close();
        }
    }

    static boolean a(FileOutputStream fileOutputStream) {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            } catch (Throwable e) {
                SLog.error(CACHE.CACHEFILE, e);
                return false;
            }
        }
        return true;
    }
}
