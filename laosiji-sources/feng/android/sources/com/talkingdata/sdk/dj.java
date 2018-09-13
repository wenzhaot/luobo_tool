package com.talkingdata.sdk;

import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.CRC32;

/* compiled from: td */
public class dj {
    public static final String a = "__database_reborn_January_one__";
    private static final String c = "OperationManager";
    private static final int d = 6;
    private static dj e;
    Lock b = new ReentrantLock();
    private ExecutorService f;
    private di g;
    private HashMap h;
    private CRC32 i;
    private Map j;
    private Map k;

    /* compiled from: td */
    class a implements Runnable {
        private final String mFolderPath;
        private final String mNewFolder;
        private final TreeSet mQueue;
        private final a mService;

        public a(a aVar) {
            this.mFolderPath = ab.g.getFilesDir() + File.separator + "td_database" + aVar.index() + dr.c;
            this.mNewFolder = ab.g.getFilesDir() + File.separator + dj.a + File.separator + "td_database" + aVar.index() + dr.c;
            this.mQueue = (TreeSet) dj.this.h.get(Integer.valueOf(aVar.index()));
            this.mService = aVar;
        }

        public void run() {
            try {
                if (this.mQueue != null) {
                    if (this.mQueue.isEmpty()) {
                    }
                    while (!this.mQueue.isEmpty()) {
                        di diVar = (di) this.mQueue.pollFirst();
                        if (diVar != null) {
                            File file = new File(this.mFolderPath);
                            if (file.exists()) {
                                deleteFile(file.getAbsolutePath(), diVar);
                            }
                            file = new File(this.mNewFolder);
                            if (file.exists()) {
                                deleteFile(file.getAbsolutePath(), diVar);
                            }
                        }
                    }
                    this.mQueue.clear();
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }

        private void deleteFile(String str, di diVar) {
            File file = new File(str + File.separator + diVar.b());
            if (!(file.exists() && file.delete())) {
            }
        }
    }

    /* compiled from: td */
    class b implements Runnable {
        private final String mFolderPath;
        private final di mOperation;

        public b(di diVar, a aVar, String str) {
            String absolutePath = ab.g.getFilesDir().getAbsolutePath();
            if (!TextUtils.isEmpty(str)) {
                absolutePath = absolutePath + File.separator + str;
            }
            this.mFolderPath = absolutePath + File.separator + "td_database" + aVar.index() + dr.c;
            this.mOperation = diVar;
        }

        public void run() {
            try {
                File file = new File(this.mFolderPath + File.separator + this.mOperation.b());
                if (!(file.exists() && file.delete())) {
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    /* compiled from: td */
    class c implements Runnable {
        private final String mFolderPath;
        private dj mManger;

        public c(String str, dj djVar) {
            this.mFolderPath = str;
            this.mManger = djVar;
        }

        public void run() {
            try {
                File file = new File(this.mFolderPath);
                if (file.exists()) {
                    String[] list = file.list();
                    if (list != null && list.length > 0) {
                        int length = list.length;
                        int i = 0;
                        while (i < length) {
                            String str = list[i];
                            i = (str == null || str.length() <= 0) ? i + 1 : i + 1;
                        }
                        return;
                    }
                    return;
                }
                Log.i(dj.c, "folder path is not exists:" + this.mFolderPath);
            } catch (Throwable th) {
            }
        }
    }

    /* compiled from: td */
    class d implements Runnable {
        private String mFolderPath;
        private final di mOperation;

        public d(di diVar, dd ddVar) {
            File filesDir = ab.g.getFilesDir();
            this.mFolderPath = filesDir.toString() + File.separator + "td_database" + ddVar.a.index() + dr.c;
            this.mFolderPath = filesDir.toString() + File.separator + dj.a + File.separator + "td_database" + ddVar.a.index() + dr.c;
            this.mOperation = diVar;
        }

        public void run() {
            try {
                File file = new File(this.mFolderPath);
                if (!(file.exists() || file.isDirectory())) {
                    file.mkdirs();
                }
                dj.this.a(file);
                File file2 = new File(this.mFolderPath + File.separator + this.mOperation.b());
                if (!file2.exists()) {
                    file2.createNewFile();
                }
                RandomAccessFile randomAccessFile = new RandomAccessFile(file2, "rw");
                randomAccessFile.seek(1);
                randomAccessFile.writeInt(this.mOperation.d());
                randomAccessFile.writeInt(this.mOperation.e());
                randomAccessFile.write(this.mOperation.c());
                randomAccessFile.getFD().sync();
                randomAccessFile.close();
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public static dj a() {
        synchronized (dj.class) {
            if (e == null) {
                e = new dj();
            }
        }
        return e;
    }

    private dj() {
        c();
        this.g = null;
        this.h = new HashMap();
        for (a index : a.values()) {
            this.h.put(Integer.valueOf(index.index()), new TreeSet());
        }
        this.f = Executors.newSingleThreadExecutor();
        this.i = new CRC32();
    }

    private void c() {
        File file = new File(ab.g.getFilesDir(), a);
        this.j = new HashMap();
        this.k = new HashMap();
        try {
            for (a aVar : a.values()) {
                File file2 = new File(file, "td_database" + aVar.index() + dr.c);
                if (!file2.exists()) {
                    file2.mkdirs();
                }
                this.j.put(Integer.valueOf(aVar.index()), new RandomAccessFile(new File(file, "Lock" + aVar.index()), "rw"));
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void b() {
        int i = 0;
        File filesDir = ab.g.getFilesDir();
        try {
            for (a index : a.values()) {
                File file = new File(filesDir, "td_database" + index.index() + dr.c);
                if (file.exists()) {
                    for (File delete : d(file)) {
                        delete.delete();
                    }
                }
            }
            File file2 = new File(filesDir, a);
            a[] values = a.values();
            int length = values.length;
            while (i < length) {
                File file3 = new File(file2, "td_database" + values[i].index() + dr.c);
                if (file3.exists()) {
                    for (File delete2 : d(file3)) {
                        delete2.delete();
                    }
                }
                i++;
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    public void getFileLock(a aVar) {
        try {
            this.b.lock();
            this.k.put(Integer.valueOf(aVar.index()), ((RandomAccessFile) this.j.get(Integer.valueOf(aVar.index()))).getChannel().lock());
        } catch (Throwable th) {
        }
    }

    public void releaseFileLock(a aVar) {
        try {
            if (this.k.get(Integer.valueOf(aVar.index())) != null) {
                try {
                    ((FileLock) this.k.get(Integer.valueOf(aVar.index()))).release();
                    this.b.unlock();
                } catch (Throwable th) {
                }
            }
        } catch (Throwable th2) {
        }
    }

    private void a(File file) {
        try {
            if (c(file) > 6) {
                b(file);
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private void b(File file) {
        try {
            if (file.isDirectory()) {
                b((File) d(file).get(0));
            } else {
                file.delete();
            }
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    private int c(File file) {
        long j = 0;
        if (file == null) {
            return 0;
        }
        try {
            if (!file.isDirectory()) {
                return 0;
            }
            File[] listFiles = file.listFiles();
            if (listFiles == null || listFiles.length == 0) {
                return 0;
            }
            for (File file2 : listFiles) {
                if (file2.isFile()) {
                    j += file2.length();
                }
            }
            return (int) (j / 1048576);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return 0;
        }
    }

    private List d(File file) {
        List arrayList = new ArrayList();
        File[] listFiles = file.listFiles();
        if (listFiles == null) {
            return arrayList;
        }
        try {
            arrayList = Arrays.asList(listFiles);
            Collections.sort(arrayList, new dk(this));
            return arrayList;
        } catch (Throwable th) {
            return arrayList;
        }
    }

    public synchronized void a(di diVar, dd ddVar) {
        this.f.execute(new d(diVar, ddVar));
    }

    public synchronized void a(di diVar, a aVar) {
        if (!(aVar == null || diVar == null)) {
            try {
                ((TreeSet) this.h.get(Integer.valueOf(aVar.index()))).add(diVar);
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
        return;
    }

    /* JADX WARNING: Removed duplicated region for block: B:55:0x0118 A:{SYNTHETIC, Splitter: B:55:0x0118} */
    /* JADX WARNING: Removed duplicated region for block: B:93:0x00ac A:{SYNTHETIC} */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x011e A:{Catch:{ Throwable -> 0x014e }} */
    /* JADX WARNING: Removed duplicated region for block: B:77:0x0157 A:{Splitter: B:21:0x0079, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:56:?, code:
            r4.release();
            r4 = null;
     */
    /* JADX WARNING: Missing block: B:58:0x011e, code:
            r2.close();
     */
    /* JADX WARNING: Missing block: B:59:0x0121, code:
            r2 = null;
     */
    /* JADX WARNING: Missing block: B:77:0x0157, code:
            r2 = th;
     */
    /* JADX WARNING: Missing block: B:79:0x015a, code:
            r3 = r2;
            r2 = r5;
     */
    public synchronized java.util.List a(com.talkingdata.sdk.a r16, int r17, java.lang.String r18) {
        /*
        r15 = this;
        monitor-enter(r15);
        r7 = new java.util.LinkedList;	 Catch:{ all -> 0x0144 }
        r7.<init>();	 Catch:{ all -> 0x0144 }
        r2 = com.talkingdata.sdk.ab.g;	 Catch:{ all -> 0x0144 }
        r3 = r2.getFilesDir();	 Catch:{ all -> 0x0144 }
        if (r18 == 0) goto L_0x0160;
    L_0x000e:
        r2 = new java.io.File;	 Catch:{ all -> 0x0144 }
        r0 = r18;
        r2.<init>(r3, r0);	 Catch:{ all -> 0x0144 }
    L_0x0015:
        r8 = new java.io.File;	 Catch:{ all -> 0x0144 }
        r3 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0144 }
        r3.<init>();	 Catch:{ all -> 0x0144 }
        r4 = "td_database";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0144 }
        r4 = r16.index();	 Catch:{ all -> 0x0144 }
        r3 = r3.append(r4);	 Catch:{ all -> 0x0144 }
        r4 = "SaaS";
        r3 = r3.append(r4);	 Catch:{ all -> 0x0144 }
        r3 = r3.toString();	 Catch:{ all -> 0x0144 }
        r8.<init>(r2, r3);	 Catch:{ all -> 0x0144 }
        r5 = 0;
        r4 = 0;
        r3 = 0;
        r2 = r8.exists();	 Catch:{ all -> 0x0144 }
        if (r2 != 0) goto L_0x0061;
    L_0x0042:
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ all -> 0x0144 }
        r3 = 0;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0144 }
        r4.<init>();	 Catch:{ all -> 0x0144 }
        r5 = "operationFolder is not exists: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x0144 }
        r4 = r4.append(r8);	 Catch:{ all -> 0x0144 }
        r4 = r4.toString();	 Catch:{ all -> 0x0144 }
        r2[r3] = r4;	 Catch:{ all -> 0x0144 }
        com.talkingdata.sdk.aq.iForInternal(r2);	 Catch:{ all -> 0x0144 }
    L_0x005f:
        monitor-exit(r15);
        return r7;
    L_0x0061:
        r9 = r8.list();	 Catch:{ all -> 0x0144 }
        if (r9 == 0) goto L_0x005f;
    L_0x0067:
        r2 = r9.length;	 Catch:{ all -> 0x0144 }
        if (r2 <= 0) goto L_0x005f;
    L_0x006a:
        r2 = r9.length;	 Catch:{ all -> 0x0144 }
        r0 = r17;
        if (r2 >= r0) goto L_0x0072;
    L_0x006f:
        r0 = r9.length;	 Catch:{ all -> 0x0144 }
        r17 = r0;
    L_0x0072:
        r2 = 0;
        r6 = r2;
        r2 = r3;
    L_0x0075:
        r0 = r17;
        if (r6 >= r0) goto L_0x005f;
    L_0x0079:
        r10 = new java.io.File;	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r3 = r9[r6];	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r10.<init>(r8, r3);	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r3 = new com.talkingdata.sdk.di;	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r11 = r9[r6];	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r3.<init>(r11);	 Catch:{ Throwable -> 0x0159, all -> 0x0157 }
        r2 = new java.io.RandomAccessFile;	 Catch:{ Throwable -> 0x015d, all -> 0x0157 }
        r11 = "rw";
        r2.<init>(r10, r11);	 Catch:{ Throwable -> 0x015d, all -> 0x0157 }
        r5 = r2.getChannel();	 Catch:{ Throwable -> 0x0107 }
        r4 = r5.tryLock();	 Catch:{ Throwable -> 0x0107 }
        if (r4 != 0) goto L_0x00b8;
    L_0x0099:
        r2.close();	 Catch:{ Throwable -> 0x0107 }
        if (r4 == 0) goto L_0x00a2;
    L_0x009e:
        r4.release();	 Catch:{ Throwable -> 0x00b2 }
        r4 = 0;
    L_0x00a2:
        if (r2 == 0) goto L_0x00a8;
    L_0x00a4:
        r2.close();	 Catch:{ Throwable -> 0x00b2 }
        r2 = 0;
    L_0x00a8:
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
    L_0x00ac:
        r5 = r6 + 1;
        r6 = r5;
        r5 = r4;
        r4 = r3;
        goto L_0x0075;
    L_0x00b2:
        r5 = move-exception;
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
        goto L_0x00ac;
    L_0x00b8:
        r10 = 1;
        r2.seek(r10);	 Catch:{ Throwable -> 0x0107 }
        r5 = r2.readInt();	 Catch:{ Throwable -> 0x0107 }
        r10 = r2.readInt();	 Catch:{ Throwable -> 0x0107 }
        r10 = new byte[r10];	 Catch:{ Throwable -> 0x0107 }
        r2.readFully(r10);	 Catch:{ Throwable -> 0x0107 }
        r11 = r15.i;	 Catch:{ Throwable -> 0x0107 }
        r11.reset();	 Catch:{ Throwable -> 0x0107 }
        r11 = r15.i;	 Catch:{ Throwable -> 0x0107 }
        r11.update(r10);	 Catch:{ Throwable -> 0x0107 }
        r11 = r15.i;	 Catch:{ Throwable -> 0x0107 }
        r12 = r11.getValue();	 Catch:{ Throwable -> 0x0107 }
        r11 = (int) r12;	 Catch:{ Throwable -> 0x0107 }
        if (r5 != r11) goto L_0x0127;
    L_0x00dd:
        if (r10 == 0) goto L_0x00f8;
    L_0x00df:
        r7.add(r10);	 Catch:{ Throwable -> 0x0107 }
        r0 = r16;
        r15.a(r3, r0);	 Catch:{ Throwable -> 0x0107 }
    L_0x00e7:
        if (r4 == 0) goto L_0x00ed;
    L_0x00e9:
        r4.release();	 Catch:{ Throwable -> 0x0147 }
        r4 = 0;
    L_0x00ed:
        if (r2 == 0) goto L_0x00f3;
    L_0x00ef:
        r2.close();	 Catch:{ Throwable -> 0x0147 }
        r2 = 0;
    L_0x00f3:
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
        goto L_0x00ac;
    L_0x00f8:
        r5 = r15.f;	 Catch:{ Throwable -> 0x0107 }
        r10 = new com.talkingdata.sdk.dj$b;	 Catch:{ Throwable -> 0x0107 }
        r0 = r16;
        r1 = r18;
        r10.<init>(r3, r0, r1);	 Catch:{ Throwable -> 0x0107 }
        r5.execute(r10);	 Catch:{ Throwable -> 0x0107 }
        goto L_0x00e7;
    L_0x0107:
        r5 = move-exception;
    L_0x0108:
        r5 = r15.f;	 Catch:{ all -> 0x0136 }
        r10 = new com.talkingdata.sdk.dj$b;	 Catch:{ all -> 0x0136 }
        r0 = r16;
        r1 = r18;
        r10.<init>(r3, r0, r1);	 Catch:{ all -> 0x0136 }
        r5.execute(r10);	 Catch:{ all -> 0x0136 }
        if (r4 == 0) goto L_0x011c;
    L_0x0118:
        r4.release();	 Catch:{ Throwable -> 0x014e }
        r4 = 0;
    L_0x011c:
        if (r2 == 0) goto L_0x0122;
    L_0x011e:
        r2.close();	 Catch:{ Throwable -> 0x014e }
        r2 = 0;
    L_0x0122:
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
        goto L_0x00ac;
    L_0x0127:
        r5 = r15.f;	 Catch:{ Throwable -> 0x0107 }
        r10 = new com.talkingdata.sdk.dj$b;	 Catch:{ Throwable -> 0x0107 }
        r0 = r16;
        r1 = r18;
        r10.<init>(r3, r0, r1);	 Catch:{ Throwable -> 0x0107 }
        r5.execute(r10);	 Catch:{ Throwable -> 0x0107 }
        goto L_0x00e7;
    L_0x0136:
        r3 = move-exception;
        r5 = r2;
        r2 = r3;
    L_0x0139:
        if (r4 == 0) goto L_0x013e;
    L_0x013b:
        r4.release();	 Catch:{ Throwable -> 0x0155 }
    L_0x013e:
        if (r5 == 0) goto L_0x0143;
    L_0x0140:
        r5.close();	 Catch:{ Throwable -> 0x0155 }
    L_0x0143:
        throw r2;	 Catch:{ all -> 0x0144 }
    L_0x0144:
        r2 = move-exception;
        monitor-exit(r15);
        throw r2;
    L_0x0147:
        r5 = move-exception;
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
        goto L_0x00ac;
    L_0x014e:
        r5 = move-exception;
        r14 = r3;
        r3 = r4;
        r4 = r2;
        r2 = r14;
        goto L_0x00ac;
    L_0x0155:
        r3 = move-exception;
        goto L_0x0143;
    L_0x0157:
        r2 = move-exception;
        goto L_0x0139;
    L_0x0159:
        r3 = move-exception;
        r3 = r2;
        r2 = r5;
        goto L_0x0108;
    L_0x015d:
        r2 = move-exception;
        r2 = r5;
        goto L_0x0108;
    L_0x0160:
        r2 = r3;
        goto L_0x0015;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.talkingdata.sdk.dj.a(com.talkingdata.sdk.a, int, java.lang.String):java.util.List");
    }

    public void confirmRead(a aVar) {
        new a(aVar).run();
    }
}
