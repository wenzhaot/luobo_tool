package anetwork.channel.download;

import android.content.Context;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.SparseArray;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.Header;
import anetwork.channel.aidl.Connection;
import anetwork.channel.http.NetworkSdkSetting;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/* compiled from: Taobao */
public class DownloadManager {
    private static final String DOWNLOAD_FOLDER = "downloads";
    public static final int ERROR_DOWNLOAD_CANCELLED = -105;
    public static final int ERROR_EXCEPTION_HAPPEN = -104;
    public static final int ERROR_FILE_FOLDER_INVALID = -101;
    public static final int ERROR_IO_EXCEPTION = -103;
    public static final int ERROR_REQUEST_FAIL = -102;
    public static final int ERROR_URL_INVALID = -100;
    public static final String TAG = "anet.DownloadManager";
    private Context context;
    private ThreadPoolExecutor executor;
    private AtomicInteger taskIdGen;
    private SparseArray<DownloadTask> taskMap;

    /* compiled from: Taobao */
    private static class ClassHolder {
        static DownloadManager instance = new DownloadManager();

        private ClassHolder() {
        }
    }

    /* compiled from: Taobao */
    public interface DownloadListener {
        void onFail(int i, int i2, String str);

        void onProgress(int i, long j, long j2);

        void onSuccess(int i, String str);
    }

    /* compiled from: Taobao */
    class DownloadTask implements Runnable {
        private volatile Connection conn = null;
        private final String filePath;
        private final AtomicBoolean isCancelled = new AtomicBoolean(false);
        private final AtomicBoolean isFinish = new AtomicBoolean(false);
        private final CopyOnWriteArrayList<DownloadListener> listenerList;
        final int taskId;
        final URL url;

        DownloadTask(URL url, String str, String str2, DownloadListener downloadListener) {
            this.taskId = DownloadManager.this.taskIdGen.getAndIncrement();
            this.url = url;
            if (TextUtils.isEmpty(str2)) {
                str2 = parseFileNameForURL(url);
            }
            if (TextUtils.isEmpty(str)) {
                this.filePath = DownloadManager.this.getDownloadFilePath(str2);
            } else if (str.endsWith("/")) {
                this.filePath = str + str2;
            } else {
                this.filePath = str + '/' + str2;
            }
            this.listenerList = new CopyOnWriteArrayList();
            this.listenerList.add(downloadListener);
        }

        public boolean attachListener(DownloadListener downloadListener) {
            if (this.isFinish.get()) {
                return false;
            }
            this.listenerList.add(downloadListener);
            return true;
        }

        public void cancel() {
            this.isCancelled.set(true);
            notifyFail(-105, "download canceled.");
            if (this.conn != null) {
                try {
                    this.conn.cancel();
                } catch (RemoteException e) {
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:203:0x0299 A:{SYNTHETIC, Splitter: B:203:0x0299} */
        /* JADX WARNING: Removed duplicated region for block: B:206:0x029e A:{SYNTHETIC, Splitter: B:206:0x029e} */
        /* JADX WARNING: Removed duplicated region for block: B:209:0x02a3 A:{SYNTHETIC, Splitter: B:209:0x02a3} */
        /* JADX WARNING: Removed duplicated region for block: B:213:0x02ad A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:203:0x0299 A:{SYNTHETIC, Splitter: B:203:0x0299} */
        /* JADX WARNING: Removed duplicated region for block: B:206:0x029e A:{SYNTHETIC, Splitter: B:206:0x029e} */
        /* JADX WARNING: Removed duplicated region for block: B:209:0x02a3 A:{SYNTHETIC, Splitter: B:209:0x02a3} */
        /* JADX WARNING: Removed duplicated region for block: B:213:0x02ad A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:176:0x024f A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:191:0x027b A:{SYNTHETIC, Splitter: B:191:0x027b} */
        /* JADX WARNING: Removed duplicated region for block: B:195:0x0285 A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:195:0x0285 A:{SYNTHETIC} */
        /* JADX WARNING: Removed duplicated region for block: B:172:0x0245 A:{SYNTHETIC, Splitter: B:172:0x0245} */
        /* JADX WARNING: Removed duplicated region for block: B:176:0x024f A:{SYNTHETIC} */
        /* JADX WARNING: Missing block: B:163:?, code:
            r3.flush();
     */
        /* JADX WARNING: Missing block: B:164:0x0237, code:
            if (r12.isCancelled.get() == false) goto L_0x0260;
     */
        /* JADX WARNING: Missing block: B:165:0x0239, code:
            if (r3 == null) goto L_0x023e;
     */
        /* JADX WARNING: Missing block: B:167:?, code:
            r3.close();
     */
        /* JADX WARNING: Missing block: B:183:?, code:
            r8.renameTo(new java.io.File(r12.filePath));
            notifySuccess(r12.filePath);
     */
        /* JADX WARNING: Missing block: B:184:0x026f, code:
            if (r3 == null) goto L_0x0274;
     */
        /* JADX WARNING: Missing block: B:186:?, code:
            r3.close();
     */
        public void run() {
            /*
            r12 = this;
            r0 = r12.isCancelled;
            r0 = r0.get();
            if (r0 == 0) goto L_0x0009;
        L_0x0008:
            return;
        L_0x0009:
            r4 = 0;
            r2 = 0;
            r1 = 0;
            r8 = new java.io.File;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = r12.url;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = r3.toString();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0 = r0.getTempFile(r3);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r8.<init>(r0);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0 = r8.exists();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = new anetwork.channel.entity.RequestImpl;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r5 = r12.url;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3.<init>(r5);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r5 = 0;
            r3.setRetryTime(r5);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r5 = 1;
            r3.setFollowRedirects(r5);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            if (r0 == 0) goto L_0x0057;
        L_0x0032:
            r5 = "Range";
            r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6.<init>();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r7 = "bytes=";
            r6 = r6.append(r7);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r10 = r8.length();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r6.append(r10);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r7 = "-";
            r6 = r6.append(r7);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r6.toString();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3.addHeader(r5, r6);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
        L_0x0057:
            r5 = new anetwork.channel.degrade.DegradableNetwork;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = anetwork.channel.download.DownloadManager.this;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r6.context;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r5.<init>(r6);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = 0;
            r6 = r5.getConnection(r3, r6);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r12.conn = r6;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r12.conn;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r9 = r6.getStatusCode();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            if (r9 <= 0) goto L_0x007d;
        L_0x0071:
            r6 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r9 == r6) goto L_0x00bd;
        L_0x0075:
            r6 = 206; // 0xce float:2.89E-43 double:1.02E-321;
            if (r9 == r6) goto L_0x00bd;
        L_0x0079:
            r6 = 416; // 0x1a0 float:5.83E-43 double:2.055E-321;
            if (r9 == r6) goto L_0x00bd;
        L_0x007d:
            r0 = -102; // 0xffffffffffffff9a float:NaN double:NaN;
            r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3.<init>();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r5 = "ResponseCode:";
            r3 = r3.append(r5);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = r3.append(r9);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = r3.toString();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r12.notifyFail(r0, r3);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            if (r4 == 0) goto L_0x009b;
        L_0x0098:
            r4.close();	 Catch:{ Exception -> 0x02bd }
        L_0x009b:
            if (r2 == 0) goto L_0x00a0;
        L_0x009d:
            r2.close();	 Catch:{ Exception -> 0x02c0 }
        L_0x00a0:
            if (r1 == 0) goto L_0x00a5;
        L_0x00a2:
            r1.close();	 Catch:{ Exception -> 0x02c3 }
        L_0x00a5:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x00ba }
            r0 = r0.taskMap;	 Catch:{ all -> 0x00ba }
            r2 = r12.taskId;	 Catch:{ all -> 0x00ba }
            r0.remove(r2);	 Catch:{ all -> 0x00ba }
            monitor-exit(r1);	 Catch:{ all -> 0x00ba }
            goto L_0x0008;
        L_0x00ba:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x00bd:
            if (r0 == 0) goto L_0x0106;
        L_0x00bf:
            r6 = 416; // 0x1a0 float:5.83E-43 double:2.055E-321;
            if (r9 != r6) goto L_0x0101;
        L_0x00c3:
            r0 = 0;
            r6 = r3.getHeaders();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r12.removeRangeHeader(r6);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r12.isCancelled;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r6.get();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            if (r6 == 0) goto L_0x00fa;
        L_0x00d3:
            if (r4 == 0) goto L_0x00d8;
        L_0x00d5:
            r4.close();	 Catch:{ Exception -> 0x02c6 }
        L_0x00d8:
            if (r2 == 0) goto L_0x00dd;
        L_0x00da:
            r2.close();	 Catch:{ Exception -> 0x02c9 }
        L_0x00dd:
            if (r1 == 0) goto L_0x00e2;
        L_0x00df:
            r1.close();	 Catch:{ Exception -> 0x02cc }
        L_0x00e2:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x00f7 }
            r0 = r0.taskMap;	 Catch:{ all -> 0x00f7 }
            r2 = r12.taskId;	 Catch:{ all -> 0x00f7 }
            r0.remove(r2);	 Catch:{ all -> 0x00f7 }
            monitor-exit(r1);	 Catch:{ all -> 0x00f7 }
            goto L_0x0008;
        L_0x00f7:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x00fa:
            r6 = 0;
            r3 = r5.getConnection(r3, r6);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r12.conn = r3;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
        L_0x0101:
            r3 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r9 != r3) goto L_0x0106;
        L_0x0105:
            r0 = 0;
        L_0x0106:
            r3 = r12.isCancelled;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3 = r3.get();	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            if (r3 == 0) goto L_0x0135;
        L_0x010e:
            if (r4 == 0) goto L_0x0113;
        L_0x0110:
            r4.close();	 Catch:{ Exception -> 0x02cf }
        L_0x0113:
            if (r2 == 0) goto L_0x0118;
        L_0x0115:
            r2.close();	 Catch:{ Exception -> 0x02d2 }
        L_0x0118:
            if (r1 == 0) goto L_0x011d;
        L_0x011a:
            r1.close();	 Catch:{ Exception -> 0x02d5 }
        L_0x011d:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x0132 }
            r0 = r0.taskMap;	 Catch:{ all -> 0x0132 }
            r2 = r12.taskId;	 Catch:{ all -> 0x0132 }
            r0.remove(r2);	 Catch:{ all -> 0x0132 }
            monitor-exit(r1);	 Catch:{ all -> 0x0132 }
            goto L_0x0008;
        L_0x0132:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x0135:
            r6 = 0;
            if (r0 != 0) goto L_0x0185;
        L_0x0139:
            r3 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0.<init>(r8);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r3.<init>(r0);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r4 = r6;
        L_0x0144:
            r0 = r12.conn;	 Catch:{ Exception -> 0x01f0 }
            r0 = r0.getConnHeadFields();	 Catch:{ Exception -> 0x01f0 }
            r6 = r12.parseContentLength(r9, r0, r4);	 Catch:{ Exception -> 0x01f0 }
            r0 = r12.conn;	 Catch:{ Exception -> 0x01f0 }
            r1 = r0.getInputStream();	 Catch:{ Exception -> 0x01f0 }
            if (r1 != 0) goto L_0x01a5;
        L_0x0156:
            r0 = -103; // 0xffffffffffffff99 float:NaN double:NaN;
            r4 = "input stream is null.";
            r12.notifyFail(r0, r4);	 Catch:{ Exception -> 0x01f0 }
            if (r3 == 0) goto L_0x0163;
        L_0x0160:
            r3.close();	 Catch:{ Exception -> 0x02d8 }
        L_0x0163:
            if (r2 == 0) goto L_0x0168;
        L_0x0165:
            r2.close();	 Catch:{ Exception -> 0x02db }
        L_0x0168:
            if (r1 == 0) goto L_0x016d;
        L_0x016a:
            r1.close();	 Catch:{ Exception -> 0x02de }
        L_0x016d:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x0182 }
            r0 = r0.taskMap;	 Catch:{ all -> 0x0182 }
            r2 = r12.taskId;	 Catch:{ all -> 0x0182 }
            r0.remove(r2);	 Catch:{ all -> 0x0182 }
            monitor-exit(r1);	 Catch:{ all -> 0x0182 }
            goto L_0x0008;
        L_0x0182:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x0185:
            r3 = new java.io.RandomAccessFile;	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r0 = "rw";
            r3.<init>(r8, r0);	 Catch:{ Exception -> 0x030e, all -> 0x0296 }
            r6 = r3.length();	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r3.seek(r6);	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r0 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r2 = r3.getChannel();	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r2 = java.nio.channels.Channels.newOutputStream(r2);	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r0.<init>(r2);	 Catch:{ Exception -> 0x0312, all -> 0x0308 }
            r4 = r6;
            r2 = r3;
            r3 = r0;
            goto L_0x0144;
        L_0x01a5:
            r0 = 0;
            r9 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
            r9 = new byte[r9];	 Catch:{ Exception -> 0x01f0 }
        L_0x01aa:
            r10 = r1.read(r9);	 Catch:{ Exception -> 0x01f0 }
            r11 = -1;
            if (r10 == r11) goto L_0x022e;
        L_0x01b1:
            r11 = r12.isCancelled;	 Catch:{ Exception -> 0x01f0 }
            r11 = r11.get();	 Catch:{ Exception -> 0x01f0 }
            if (r11 == 0) goto L_0x01e5;
        L_0x01b9:
            r0 = r12.conn;	 Catch:{ Exception -> 0x01f0 }
            r0.cancel();	 Catch:{ Exception -> 0x01f0 }
            if (r3 == 0) goto L_0x01c3;
        L_0x01c0:
            r3.close();	 Catch:{ Exception -> 0x02e1 }
        L_0x01c3:
            if (r2 == 0) goto L_0x01c8;
        L_0x01c5:
            r2.close();	 Catch:{ Exception -> 0x02e4 }
        L_0x01c8:
            if (r1 == 0) goto L_0x01cd;
        L_0x01ca:
            r1.close();	 Catch:{ Exception -> 0x02e7 }
        L_0x01cd:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x01e2 }
            r0 = r0.taskMap;	 Catch:{ all -> 0x01e2 }
            r2 = r12.taskId;	 Catch:{ all -> 0x01e2 }
            r0.remove(r2);	 Catch:{ all -> 0x01e2 }
            monitor-exit(r1);	 Catch:{ all -> 0x01e2 }
            goto L_0x0008;
        L_0x01e2:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x01e5:
            r0 = r0 + r10;
            r11 = 0;
            r3.write(r9, r11, r10);	 Catch:{ Exception -> 0x01f0 }
            r10 = (long) r0;	 Catch:{ Exception -> 0x01f0 }
            r10 = r10 + r4;
            r12.notifyProgress(r10, r6);	 Catch:{ Exception -> 0x01f0 }
            goto L_0x01aa;
        L_0x01f0:
            r0 = move-exception;
        L_0x01f1:
            r4 = "anet.DownloadManager";
            r5 = "file download failed!";
            r6 = 0;
            r7 = 0;
            r7 = new java.lang.Object[r7];	 Catch:{ all -> 0x030b }
            anet.channel.util.ALog.e(r4, r5, r6, r0, r7);	 Catch:{ all -> 0x030b }
            r4 = -104; // 0xffffffffffffff98 float:NaN double:NaN;
            r0 = r0.toString();	 Catch:{ all -> 0x030b }
            r12.notifyFail(r4, r0);	 Catch:{ all -> 0x030b }
            if (r3 == 0) goto L_0x020c;
        L_0x0209:
            r3.close();	 Catch:{ Exception -> 0x02f9 }
        L_0x020c:
            if (r2 == 0) goto L_0x0211;
        L_0x020e:
            r2.close();	 Catch:{ Exception -> 0x02fc }
        L_0x0211:
            if (r1 == 0) goto L_0x0216;
        L_0x0213:
            r1.close();	 Catch:{ Exception -> 0x02ff }
        L_0x0216:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x022b }
            r0 = r0.taskMap;	 Catch:{ all -> 0x022b }
            r2 = r12.taskId;	 Catch:{ all -> 0x022b }
            r0.remove(r2);	 Catch:{ all -> 0x022b }
            monitor-exit(r1);	 Catch:{ all -> 0x022b }
            goto L_0x0008;
        L_0x022b:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x022e:
            r3.flush();	 Catch:{ Exception -> 0x01f0 }
            r0 = r12.isCancelled;	 Catch:{ Exception -> 0x01f0 }
            r0 = r0.get();	 Catch:{ Exception -> 0x01f0 }
            if (r0 == 0) goto L_0x0260;
        L_0x0239:
            if (r3 == 0) goto L_0x023e;
        L_0x023b:
            r3.close();	 Catch:{ Exception -> 0x02ea }
        L_0x023e:
            if (r2 == 0) goto L_0x0243;
        L_0x0240:
            r2.close();	 Catch:{ Exception -> 0x02ed }
        L_0x0243:
            if (r1 == 0) goto L_0x0248;
        L_0x0245:
            r1.close();	 Catch:{ Exception -> 0x02f0 }
        L_0x0248:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x025d }
            r0 = r0.taskMap;	 Catch:{ all -> 0x025d }
            r2 = r12.taskId;	 Catch:{ all -> 0x025d }
            r0.remove(r2);	 Catch:{ all -> 0x025d }
            monitor-exit(r1);	 Catch:{ all -> 0x025d }
            goto L_0x0008;
        L_0x025d:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x0260:
            r0 = new java.io.File;	 Catch:{ Exception -> 0x01f0 }
            r4 = r12.filePath;	 Catch:{ Exception -> 0x01f0 }
            r0.<init>(r4);	 Catch:{ Exception -> 0x01f0 }
            r8.renameTo(r0);	 Catch:{ Exception -> 0x01f0 }
            r0 = r12.filePath;	 Catch:{ Exception -> 0x01f0 }
            r12.notifySuccess(r0);	 Catch:{ Exception -> 0x01f0 }
            if (r3 == 0) goto L_0x0274;
        L_0x0271:
            r3.close();	 Catch:{ Exception -> 0x02f3 }
        L_0x0274:
            if (r2 == 0) goto L_0x0279;
        L_0x0276:
            r2.close();	 Catch:{ Exception -> 0x02f5 }
        L_0x0279:
            if (r1 == 0) goto L_0x027e;
        L_0x027b:
            r1.close();	 Catch:{ Exception -> 0x02f7 }
        L_0x027e:
            r0 = anetwork.channel.download.DownloadManager.this;
            r1 = r0.taskMap;
            monitor-enter(r1);
            r0 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x0293 }
            r0 = r0.taskMap;	 Catch:{ all -> 0x0293 }
            r2 = r12.taskId;	 Catch:{ all -> 0x0293 }
            r0.remove(r2);	 Catch:{ all -> 0x0293 }
            monitor-exit(r1);	 Catch:{ all -> 0x0293 }
            goto L_0x0008;
        L_0x0293:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x0296:
            r0 = move-exception;
        L_0x0297:
            if (r4 == 0) goto L_0x029c;
        L_0x0299:
            r4.close();	 Catch:{ Exception -> 0x0302 }
        L_0x029c:
            if (r2 == 0) goto L_0x02a1;
        L_0x029e:
            r2.close();	 Catch:{ Exception -> 0x0304 }
        L_0x02a1:
            if (r1 == 0) goto L_0x02a6;
        L_0x02a3:
            r1.close();	 Catch:{ Exception -> 0x0306 }
        L_0x02a6:
            r1 = anetwork.channel.download.DownloadManager.this;
            r1 = r1.taskMap;
            monitor-enter(r1);
            r2 = anetwork.channel.download.DownloadManager.this;	 Catch:{ all -> 0x02ba }
            r2 = r2.taskMap;	 Catch:{ all -> 0x02ba }
            r3 = r12.taskId;	 Catch:{ all -> 0x02ba }
            r2.remove(r3);	 Catch:{ all -> 0x02ba }
            monitor-exit(r1);	 Catch:{ all -> 0x02ba }
            throw r0;
        L_0x02ba:
            r0 = move-exception;
            monitor-exit(r1);
            throw r0;
        L_0x02bd:
            r0 = move-exception;
            goto L_0x009b;
        L_0x02c0:
            r0 = move-exception;
            goto L_0x00a0;
        L_0x02c3:
            r0 = move-exception;
            goto L_0x00a5;
        L_0x02c6:
            r0 = move-exception;
            goto L_0x00d8;
        L_0x02c9:
            r0 = move-exception;
            goto L_0x00dd;
        L_0x02cc:
            r0 = move-exception;
            goto L_0x00e2;
        L_0x02cf:
            r0 = move-exception;
            goto L_0x0113;
        L_0x02d2:
            r0 = move-exception;
            goto L_0x0118;
        L_0x02d5:
            r0 = move-exception;
            goto L_0x011d;
        L_0x02d8:
            r0 = move-exception;
            goto L_0x0163;
        L_0x02db:
            r0 = move-exception;
            goto L_0x0168;
        L_0x02de:
            r0 = move-exception;
            goto L_0x016d;
        L_0x02e1:
            r0 = move-exception;
            goto L_0x01c3;
        L_0x02e4:
            r0 = move-exception;
            goto L_0x01c8;
        L_0x02e7:
            r0 = move-exception;
            goto L_0x01cd;
        L_0x02ea:
            r0 = move-exception;
            goto L_0x023e;
        L_0x02ed:
            r0 = move-exception;
            goto L_0x0243;
        L_0x02f0:
            r0 = move-exception;
            goto L_0x0248;
        L_0x02f3:
            r0 = move-exception;
            goto L_0x0274;
        L_0x02f5:
            r0 = move-exception;
            goto L_0x0279;
        L_0x02f7:
            r0 = move-exception;
            goto L_0x027e;
        L_0x02f9:
            r0 = move-exception;
            goto L_0x020c;
        L_0x02fc:
            r0 = move-exception;
            goto L_0x0211;
        L_0x02ff:
            r0 = move-exception;
            goto L_0x0216;
        L_0x0302:
            r3 = move-exception;
            goto L_0x029c;
        L_0x0304:
            r2 = move-exception;
            goto L_0x02a1;
        L_0x0306:
            r1 = move-exception;
            goto L_0x02a6;
        L_0x0308:
            r0 = move-exception;
            r2 = r3;
            goto L_0x0297;
        L_0x030b:
            r0 = move-exception;
            r4 = r3;
            goto L_0x0297;
        L_0x030e:
            r0 = move-exception;
            r3 = r4;
            goto L_0x01f1;
        L_0x0312:
            r0 = move-exception;
            r2 = r3;
            r3 = r4;
            goto L_0x01f1;
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.download.DownloadManager.DownloadTask.run():void");
        }

        private void notifySuccess(String str) {
            if (this.isFinish.compareAndSet(false, true)) {
                Iterator it = this.listenerList.iterator();
                while (it.hasNext()) {
                    ((DownloadListener) it.next()).onSuccess(this.taskId, str);
                }
            }
        }

        private void notifyFail(int i, String str) {
            if (this.isFinish.compareAndSet(false, true)) {
                Iterator it = this.listenerList.iterator();
                while (it.hasNext()) {
                    ((DownloadListener) it.next()).onFail(this.taskId, i, str);
                }
            }
        }

        private void notifyProgress(long j, long j2) {
            if (!this.isFinish.get()) {
                Iterator it = this.listenerList.iterator();
                while (it.hasNext()) {
                    ((DownloadListener) it.next()).onProgress(this.taskId, j, j2);
                }
            }
        }

        /* JADX WARNING: Removed duplicated region for block: B:22:? A:{SYNTHETIC, RETURN, Catch:{ Exception -> 0x0043 }} */
        /* JADX WARNING: Removed duplicated region for block: B:13:0x0036 A:{SYNTHETIC, Splitter: B:13:0x0036} */
        private long parseContentLength(int r6, java.util.Map<java.lang.String, java.util.List<java.lang.String>> r7, long r8) {
            /*
            r5 = this;
            r2 = 0;
            r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
            if (r6 != r0) goto L_0x0012;
        L_0x0006:
            r0 = "Content-Length";
            r0 = anet.channel.util.a.b(r7, r0);	 Catch:{ Exception -> 0x0043 }
            r0 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0043 }
        L_0x0011:
            return r0;
        L_0x0012:
            r0 = 206; // 0xce float:2.89E-43 double:1.02E-321;
            if (r6 != r0) goto L_0x004a;
        L_0x0016:
            r0 = "Content-Range";
            r0 = anet.channel.util.a.b(r7, r0);	 Catch:{ Exception -> 0x0043 }
            if (r0 == 0) goto L_0x0048;
        L_0x001f:
            r1 = 47;
            r1 = r0.lastIndexOf(r1);	 Catch:{ Exception -> 0x0043 }
            r4 = -1;
            if (r1 == r4) goto L_0x0048;
        L_0x0028:
            r1 = r1 + 1;
            r0 = r0.substring(r1);	 Catch:{ Exception -> 0x0043 }
            r0 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0043 }
        L_0x0032:
            r2 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1));
            if (r2 != 0) goto L_0x0011;
        L_0x0036:
            r2 = "Content-Length";
            r2 = anet.channel.util.a.b(r7, r2);	 Catch:{ Exception -> 0x0046 }
            r0 = java.lang.Long.parseLong(r2);	 Catch:{ Exception -> 0x0046 }
            r0 = r0 + r8;
            goto L_0x0011;
        L_0x0043:
            r0 = move-exception;
            r0 = r2;
            goto L_0x0011;
        L_0x0046:
            r2 = move-exception;
            goto L_0x0011;
        L_0x0048:
            r0 = r2;
            goto L_0x0032;
        L_0x004a:
            r0 = r2;
            goto L_0x0011;
            */
            throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.download.DownloadManager.DownloadTask.parseContentLength(int, java.util.Map, long):long");
        }

        private void removeRangeHeader(List<Header> list) {
            if (list != null) {
                ListIterator listIterator = list.listIterator();
                while (listIterator.hasNext()) {
                    if ("Range".equalsIgnoreCase(((Header) listIterator.next()).getName())) {
                        listIterator.remove();
                        return;
                    }
                }
            }
        }

        private String parseFileNameForURL(URL url) {
            String path = url.getPath();
            int lastIndexOf = path.lastIndexOf(47);
            String str = null;
            if (lastIndexOf != -1) {
                str = path.substring(lastIndexOf + 1, path.length());
            }
            if (!TextUtils.isEmpty(str)) {
                return str;
            }
            str = StringUtils.md5ToHex(url.toString());
            if (str == null) {
                return url.getFile();
            }
            return str;
        }
    }

    public static DownloadManager getInstance() {
        return ClassHolder.instance;
    }

    private DownloadManager() {
        this.taskMap = new SparseArray(6);
        this.taskIdGen = new AtomicInteger(0);
        this.executor = new ThreadPoolExecutor(2, 2, 30, TimeUnit.SECONDS, new LinkedBlockingDeque());
        this.context = null;
        this.context = NetworkSdkSetting.getContext();
        this.executor.allowCoreThreadTimeOut(true);
        prepareDownloadFolder();
    }

    public int enqueue(String str, String str2, DownloadListener downloadListener) {
        return enqueue(str, null, str2, downloadListener);
    }

    public int enqueue(String str, String str2, String str3, DownloadListener downloadListener) {
        int i = -1;
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "enqueue", null, "folder", str2, "filename", str3, "url", str);
        }
        if (this.context == null) {
            ALog.e(TAG, "network not initial.", null, new Object[0]);
        } else {
            try {
                URL url = new URL(str);
                if (TextUtils.isEmpty(str2) || prepareFolder(str2)) {
                    synchronized (this.taskMap) {
                        int size = this.taskMap.size();
                        int i2 = 0;
                        while (i2 < size) {
                            DownloadTask downloadTask = (DownloadTask) this.taskMap.valueAt(i2);
                            if (!url.equals(downloadTask.url)) {
                                i2++;
                            } else if (downloadTask.attachListener(downloadListener)) {
                                i = downloadTask.taskId;
                            }
                        }
                        Object downloadTask2 = new DownloadTask(url, str2, str3, downloadListener);
                        this.taskMap.put(downloadTask2.taskId, downloadTask2);
                        this.executor.submit(downloadTask2);
                        i = downloadTask2.taskId;
                    }
                } else {
                    ALog.e(TAG, "file folder invalid.", null, new Object[0]);
                    if (downloadListener != null) {
                        downloadListener.onFail(-1, -101, "file folder path invalid");
                    }
                }
            } catch (Throwable e) {
                ALog.e(TAG, "url invalid.", null, e, new Object[0]);
                if (downloadListener != null) {
                    downloadListener.onFail(-1, -100, "url invalid");
                }
            }
        }
        return i;
    }

    public void cancel(int i) {
        synchronized (this.taskMap) {
            DownloadTask downloadTask = (DownloadTask) this.taskMap.get(i);
            if (downloadTask != null) {
                if (ALog.isPrintLog(2)) {
                    ALog.i(TAG, "try cancel task" + i + " url=" + downloadTask.url.toString(), null, new Object[0]);
                }
                this.taskMap.remove(i);
                downloadTask.cancel();
            }
        }
    }

    private void prepareDownloadFolder() {
        if (this.context != null) {
            File file = new File(this.context.getExternalFilesDir(null), DOWNLOAD_FOLDER);
            if (!file.exists()) {
                file.mkdir();
            }
        }
    }

    private boolean prepareFolder(String str) {
        if (this.context != null) {
            try {
                File file = new File(str);
                if (file.exists()) {
                    return true;
                }
                return file.mkdir();
            } catch (Exception e) {
                ALog.e(TAG, "create folder failed", null, "folder", str);
            }
        }
        return false;
    }

    private String getDownloadFilePath(String str) {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append(this.context.getExternalFilesDir(null)).append("/").append(DOWNLOAD_FOLDER).append("/").append(str);
        return stringBuilder.toString();
    }

    private String getTempFile(String str) {
        String md5ToHex = StringUtils.md5ToHex(str);
        if (md5ToHex != null) {
            str = md5ToHex;
        }
        return this.context.getExternalCacheDir() + "/" + str;
    }
}
