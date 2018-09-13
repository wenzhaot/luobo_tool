package anet.channel.util;

import android.content.Context;
import anet.channel.GlobalAppRuntimeInfo;
import java.io.File;

/* compiled from: Taobao */
public class f {
    private static File a = null;

    public static File a(String str) {
        if (a == null) {
            Context context = GlobalAppRuntimeInfo.getContext();
            if (context != null) {
                a = context.getExternalCacheDir();
                if (a == null) {
                    a = context.getCacheDir();
                }
            }
        }
        return new File(a, str);
    }

    /* JADX WARNING: Removed duplicated region for block: B:40:0x00bc A:{SYNTHETIC, Splitter: B:40:0x00bc} */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0059 A:{SYNTHETIC, Splitter: B:20:0x0059} */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x00b8 A:{Splitter: B:10:0x001d, ExcHandler: all (th java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:27:0x0090, code:
            r0 = e;
     */
    /* JADX WARNING: Missing block: B:28:0x0091, code:
            r1 = null;
     */
    /* JADX WARNING: Missing block: B:37:0x00b8, code:
            r0 = th;
     */
    /* JADX WARNING: Missing block: B:38:0x00b9, code:
            r1 = null;
     */
    /* JADX WARNING: Missing block: B:41:?, code:
            r1.close();
     */
    public static synchronized void a(java.io.Serializable r13, java.io.File r14) {
        /*
        r0 = 1;
        r4 = 0;
        r2 = 0;
        r5 = anet.channel.util.f.class;
        monitor-enter(r5);
        if (r13 == 0) goto L_0x000a;
    L_0x0008:
        if (r14 != 0) goto L_0x0019;
    L_0x000a:
        r0 = "awcn.SerializeHelper";
        r1 = "persist fail. Invalid parameter";
        r2 = 0;
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x008d }
        anet.channel.util.ALog.e(r0, r1, r2, r3);	 Catch:{ all -> 0x008d }
    L_0x0017:
        monitor-exit(r5);
        return;
    L_0x0019:
        r6 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x008d }
        r1 = java.util.UUID.randomUUID();	 Catch:{ Exception -> 0x0090, all -> 0x00b8 }
        r1 = r1.toString();	 Catch:{ Exception -> 0x0090, all -> 0x00b8 }
        r3 = "-";
        r8 = "";
        r1 = r1.replace(r3, r8);	 Catch:{ Exception -> 0x0090, all -> 0x00b8 }
        r3 = a(r1);	 Catch:{ Exception -> 0x0090, all -> 0x00b8 }
        r3.createNewFile();	 Catch:{ Exception -> 0x00e8, all -> 0x00b8 }
        r1 = 1;
        r3.setReadable(r1);	 Catch:{ Exception -> 0x00e8, all -> 0x00b8 }
        r1 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00e8, all -> 0x00b8 }
        r1.<init>(r3);	 Catch:{ Exception -> 0x00e8, all -> 0x00b8 }
        r2 = new java.io.ObjectOutputStream;	 Catch:{ Exception -> 0x00ec }
        r8 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x00ec }
        r8.<init>(r1);	 Catch:{ Exception -> 0x00ec }
        r2.<init>(r8);	 Catch:{ Exception -> 0x00ec }
        r2.writeObject(r13);	 Catch:{ Exception -> 0x00ec }
        r2.flush();	 Catch:{ Exception -> 0x00ec }
        r2.close();	 Catch:{ Exception -> 0x00ec }
        if (r1 == 0) goto L_0x0057;
    L_0x0054:
        r1.close();	 Catch:{ IOException -> 0x00e1 }
    L_0x0057:
        if (r0 == 0) goto L_0x0017;
    L_0x0059:
        r0 = r3.renameTo(r14);	 Catch:{ all -> 0x008d }
        if (r0 == 0) goto L_0x00c0;
    L_0x005f:
        r0 = "awcn.SerializeHelper";
        r1 = "persist end.";
        r2 = 0;
        r3 = 4;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x008d }
        r4 = 0;
        r8 = "file";
        r3[r4] = r8;	 Catch:{ all -> 0x008d }
        r4 = 1;
        r8 = r14.getAbsoluteFile();	 Catch:{ all -> 0x008d }
        r3[r4] = r8;	 Catch:{ all -> 0x008d }
        r4 = 2;
        r8 = "cost";
        r3[r4] = r8;	 Catch:{ all -> 0x008d }
        r4 = 3;
        r8 = java.lang.System.currentTimeMillis();	 Catch:{ all -> 0x008d }
        r6 = r8 - r6;
        r6 = java.lang.Long.valueOf(r6);	 Catch:{ all -> 0x008d }
        r3[r4] = r6;	 Catch:{ all -> 0x008d }
        anet.channel.util.ALog.i(r0, r1, r2, r3);	 Catch:{ all -> 0x008d }
        goto L_0x0017;
    L_0x008d:
        r0 = move-exception;
        monitor-exit(r5);
        throw r0;
    L_0x0090:
        r0 = move-exception;
        r1 = r2;
    L_0x0092:
        r3 = "awcn.SerializeHelper";
        r8 = "persist fail. ";
        r9 = 0;
        r10 = 2;
        r10 = new java.lang.Object[r10];	 Catch:{ all -> 0x00e6 }
        r11 = 0;
        r12 = "file";
        r10[r11] = r12;	 Catch:{ all -> 0x00e6 }
        r11 = 1;
        r12 = r14.getName();	 Catch:{ all -> 0x00e6 }
        r10[r11] = r12;	 Catch:{ all -> 0x00e6 }
        anet.channel.util.ALog.e(r3, r8, r9, r0, r10);	 Catch:{ all -> 0x00e6 }
        if (r1 == 0) goto L_0x00ef;
    L_0x00ae:
        r1.close();	 Catch:{ IOException -> 0x00b4 }
        r0 = r4;
        r3 = r2;
        goto L_0x0057;
    L_0x00b4:
        r0 = move-exception;
        r0 = r4;
        r3 = r2;
        goto L_0x0057;
    L_0x00b8:
        r0 = move-exception;
        r1 = r2;
    L_0x00ba:
        if (r1 == 0) goto L_0x00bf;
    L_0x00bc:
        r1.close();	 Catch:{ IOException -> 0x00e4 }
    L_0x00bf:
        throw r0;	 Catch:{ all -> 0x008d }
    L_0x00c0:
        r0 = "awcn.SerializeHelper";
        r1 = "rename failed.";
        r2 = 0;
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x008d }
        anet.channel.util.ALog.e(r0, r1, r2, r3);	 Catch:{ all -> 0x008d }
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();	 Catch:{ all -> 0x008d }
        r1 = new anet.channel.statist.ExceptionStatistic;	 Catch:{ all -> 0x008d }
        r2 = -106; // 0xffffffffffffff96 float:NaN double:NaN;
        r3 = 0;
        r4 = "rt";
        r1.<init>(r2, r3, r4);	 Catch:{ all -> 0x008d }
        r0.commitStat(r1);	 Catch:{ all -> 0x008d }
        goto L_0x0017;
    L_0x00e1:
        r1 = move-exception;
        goto L_0x0057;
    L_0x00e4:
        r1 = move-exception;
        goto L_0x00bf;
    L_0x00e6:
        r0 = move-exception;
        goto L_0x00ba;
    L_0x00e8:
        r0 = move-exception;
        r1 = r2;
        r2 = r3;
        goto L_0x0092;
    L_0x00ec:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0092;
    L_0x00ef:
        r0 = r4;
        r3 = r2;
        goto L_0x0057;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.f.a(java.io.Serializable, java.io.File):void");
    }

    /* JADX WARNING: Removed duplicated region for block: B:36:0x0065 A:{Catch:{ all -> 0x008b }} */
    /* JADX WARNING: Removed duplicated region for block: B:38:0x0074 A:{SYNTHETIC, Splitter: B:38:0x0074} */
    /* JADX WARNING: Removed duplicated region for block: B:46:0x0082 A:{SYNTHETIC, Splitter: B:46:0x0082} */
    public static synchronized <T> T a(java.io.File r9) {
        /*
        r0 = 0;
        r3 = anet.channel.util.f.class;
        monitor-enter(r3);
        r1 = 0;
        r2 = r9.exists();	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        if (r2 != 0) goto L_0x003b;
    L_0x000b:
        r2 = 3;
        r2 = anet.channel.util.ALog.isPrintLog(r2);	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        if (r2 == 0) goto L_0x002c;
    L_0x0012:
        r2 = "awcn.SerializeHelper";
        r4 = "file not exist.";
        r5 = 0;
        r6 = 2;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        r7 = 0;
        r8 = "file";
        r6[r7] = r8;	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        r7 = 1;
        r8 = r9.getName();	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        r6[r7] = r8;	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        anet.channel.util.ALog.w(r2, r4, r5, r6);	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
    L_0x002c:
        if (r0 == 0) goto L_0x0031;
    L_0x002e:
        r1.close();	 Catch:{ IOException -> 0x0033 }
    L_0x0031:
        monitor-exit(r3);
        return r0;
    L_0x0033:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0038 }
        goto L_0x0031;
    L_0x0038:
        r0 = move-exception;
        monitor-exit(r3);
        throw r0;
    L_0x003b:
        r2 = new java.io.FileInputStream;	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        r2.<init>(r9);	 Catch:{ Throwable -> 0x005c, all -> 0x007d }
        r1 = new java.io.ObjectInputStream;	 Catch:{ Throwable -> 0x008d }
        r4 = new java.io.BufferedInputStream;	 Catch:{ Throwable -> 0x008d }
        r4.<init>(r2);	 Catch:{ Throwable -> 0x008d }
        r1.<init>(r4);	 Catch:{ Throwable -> 0x008d }
        r0 = r1.readObject();	 Catch:{ Throwable -> 0x008d }
        r1.close();	 Catch:{ Throwable -> 0x008d }
        if (r2 == 0) goto L_0x0031;
    L_0x0053:
        r2.close();	 Catch:{ IOException -> 0x0057 }
        goto L_0x0031;
    L_0x0057:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0038 }
        goto L_0x0031;
    L_0x005c:
        r1 = move-exception;
        r2 = r0;
    L_0x005e:
        r4 = 3;
        r4 = anet.channel.util.ALog.isPrintLog(r4);	 Catch:{ all -> 0x008b }
        if (r4 == 0) goto L_0x0072;
    L_0x0065:
        r4 = "awcn.SerializeHelper";
        r5 = "restore file fail.";
        r6 = 0;
        r7 = 0;
        r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x008b }
        anet.channel.util.ALog.w(r4, r5, r6, r1, r7);	 Catch:{ all -> 0x008b }
    L_0x0072:
        if (r2 == 0) goto L_0x0031;
    L_0x0074:
        r2.close();	 Catch:{ IOException -> 0x0078 }
        goto L_0x0031;
    L_0x0078:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0038 }
        goto L_0x0031;
    L_0x007d:
        r1 = move-exception;
        r2 = r0;
        r0 = r1;
    L_0x0080:
        if (r2 == 0) goto L_0x0085;
    L_0x0082:
        r2.close();	 Catch:{ IOException -> 0x0086 }
    L_0x0085:
        throw r0;	 Catch:{ all -> 0x0038 }
    L_0x0086:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0038 }
        goto L_0x0085;
    L_0x008b:
        r0 = move-exception;
        goto L_0x0080;
    L_0x008d:
        r1 = move-exception;
        goto L_0x005e;
        */
        throw new UnsupportedOperationException("Method not decompiled: anet.channel.util.f.a(java.io.File):T");
    }
}
