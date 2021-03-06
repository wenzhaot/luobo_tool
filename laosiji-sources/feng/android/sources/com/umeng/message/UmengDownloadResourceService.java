package com.umeng.message;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.text.TextUtils;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.sina.params.ShareRequestParam;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.json.JSONObject;

public class UmengDownloadResourceService extends Service {
    public static final String TAG = UmengDownloadResourceService.class.getName();
    private static final String d = ".tmp";
    private static final String e = "RETRY_TIME";
    private static final String f = "OPERATIOIN";
    private static final int g = 1;
    private static final int h = 2;
    private static final long i = 1048576;
    private static final long j = 86400000;
    private static final int k = 300000;
    private static final int l = 3;
    private static Thread m;
    ScheduledThreadPoolExecutor a;
    Context b;
    ArrayList<String> c;

    public class DownloadResourceTask extends AsyncTask<Void, Void, Boolean> {
        UMessage a;
        ArrayList<String> b = new ArrayList();
        int c;

        public DownloadResourceTask(UMessage uMessage, int i) {
            this.a = uMessage;
            if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
                try {
                    this.b.add(new JSONObject(uMessage.custom).optString(ShareRequestParam.REQ_UPLOAD_PIC_PARAM_IMG));
                } catch (Exception e) {
                }
            }
            if (uMessage.isLargeIconFromInternet()) {
                this.b.add(uMessage.img);
            }
            if (uMessage.isSoundFromInternet()) {
                this.b.add(uMessage.sound);
            }
            if (!TextUtils.isEmpty(uMessage.bar_image)) {
                this.b.add(uMessage.bar_image);
            }
            if (!TextUtils.isEmpty(uMessage.expand_image)) {
                this.b.add(uMessage.expand_image);
            }
            this.c = i;
        }

        /* renamed from: a */
        protected Boolean doInBackground(Void... voidArr) {
            boolean z = true;
            Iterator it = this.b.iterator();
            while (true) {
                boolean z2 = z;
                if (!it.hasNext()) {
                    return Boolean.valueOf(z2);
                }
                z = download((String) it.next()) & z2;
            }
        }

        /* renamed from: a */
        protected void onPostExecute(Boolean bool) {
            super.onPostExecute(bool);
            UmengDownloadResourceService.this.c.remove(this.a.msg_id);
            if (bool.booleanValue() || this.c <= 0) {
                MessageSharedPrefs.getInstance(UmengDownloadResourceService.this.b).setMessageResourceDownloaded(this.a.msg_id);
                String jSONObject = this.a.getRaw().toString();
                Intent intent = new Intent(UmengDownloadResourceService.this.b, UmengDownloadResourceService.class);
                intent.putExtra("body", jSONObject);
                intent.putExtra("id", this.a.message_id);
                intent.putExtra("task_id", this.a.task_id);
                intent.putExtra(UmengDownloadResourceService.f, 1);
                intent.putExtra(UmengDownloadResourceService.e, this.c);
                UmengDownloadResourceService.this.startService(intent);
            } else if (UmengDownloadResourceService.this.c.size() == 0) {
                UmengDownloadResourceService.this.stopSelf();
            }
        }

        public boolean download(String str) {
            Closeable openStream;
            Closeable fileOutputStream;
            Exception e;
            Throwable th;
            Closeable closeable = null;
            if (TextUtils.isEmpty(str)) {
                return true;
            }
            try {
                String str2 = str.hashCode() + "";
                String messageResourceFolder = UmengDownloadResourceService.getMessageResourceFolder(UmengDownloadResourceService.this.b, this.a);
                File file = new File(messageResourceFolder, str2 + UmengDownloadResourceService.d);
                File file2 = new File(messageResourceFolder, str2);
                if (file2.exists()) {
                    UmengDownloadResourceService.this.close(null);
                    UmengDownloadResourceService.this.close(null);
                    return true;
                }
                File file3 = new File(messageResourceFolder);
                if (!file3.exists()) {
                    file3.mkdirs();
                }
                if (file.exists()) {
                    file.delete();
                }
                openStream = new URL(new URI(str).toASCIIString()).openStream();
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (Exception e2) {
                    e = e2;
                    fileOutputStream = openStream;
                    try {
                        e.printStackTrace();
                        UmengDownloadResourceService.this.close(fileOutputStream);
                        UmengDownloadResourceService.this.close(closeable);
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        openStream = fileOutputStream;
                        UmengDownloadResourceService.this.close(openStream);
                        UmengDownloadResourceService.this.close(closeable);
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    UmengDownloadResourceService.this.close(openStream);
                    UmengDownloadResourceService.this.close(closeable);
                    throw th;
                }
                try {
                    byte[] bArr = new byte[10240];
                    while (true) {
                        int read = openStream.read(bArr);
                        if (read > 0) {
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            file.renameTo(file2);
                            UmengDownloadResourceService.this.close(openStream);
                            UmengDownloadResourceService.this.close(fileOutputStream);
                            return true;
                        }
                    }
                } catch (Exception e3) {
                    e = e3;
                    closeable = fileOutputStream;
                    fileOutputStream = openStream;
                    e.printStackTrace();
                    UmengDownloadResourceService.this.close(fileOutputStream);
                    UmengDownloadResourceService.this.close(closeable);
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    closeable = fileOutputStream;
                    UmengDownloadResourceService.this.close(openStream);
                    UmengDownloadResourceService.this.close(closeable);
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                fileOutputStream = null;
            } catch (Throwable th5) {
                th = th5;
                openStream = null;
                UmengDownloadResourceService.this.close(openStream);
                UmengDownloadResourceService.this.close(closeable);
                throw th;
            }
        }
    }

    static {
        StubApp.interface11(8555);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        this.a = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 4);
        this.b = this;
        this.c = new ArrayList();
    }

    @android.annotation.SuppressLint({"NewApi"})
    public int onStartCommand(android.content.Intent r8, int r9, int r10) {
        /*
        r7 = this;
        r1 = 2;
        if (r8 != 0) goto L_0x0008;
    L_0x0003:
        r0 = super.onStartCommand(r8, r9, r10);
    L_0x0007:
        return r0;
    L_0x0008:
        r0 = "OPERATIOIN";
        r0 = r8.getIntExtra(r0, r1);
        r1 = "RETRY_TIME";
        r2 = 3;
        r1 = r8.getIntExtra(r1, r2);
        r2 = "body";
        r2 = r8.getStringExtra(r2);
        r3 = new com.umeng.message.entity.UMessage;	 Catch:{ Exception -> 0x0075 }
        r4 = new org.json.JSONObject;	 Catch:{ Exception -> 0x0075 }
        r4.<init>(r2);	 Catch:{ Exception -> 0x0075 }
        r3.<init>(r4);	 Catch:{ Exception -> 0x0075 }
        r2 = "id";
        r2 = r8.getStringExtra(r2);	 Catch:{ Exception -> 0x0075 }
        r3.message_id = r2;	 Catch:{ Exception -> 0x0075 }
        r2 = "task_id";
        r2 = r8.getStringExtra(r2);	 Catch:{ Exception -> 0x0075 }
        r3.task_id = r2;	 Catch:{ Exception -> 0x0075 }
        r2 = r7.c;	 Catch:{ Exception -> 0x0075 }
        r4 = r3.msg_id;	 Catch:{ Exception -> 0x0075 }
        r2 = r2.contains(r4);	 Catch:{ Exception -> 0x0075 }
        if (r2 == 0) goto L_0x0049;
    L_0x0044:
        r0 = super.onStartCommand(r8, r9, r10);	 Catch:{ Exception -> 0x0075 }
        goto L_0x0007;
    L_0x0049:
        r2 = r7.c;	 Catch:{ Exception -> 0x0075 }
        r4 = r3.msg_id;	 Catch:{ Exception -> 0x0075 }
        r2.add(r4);	 Catch:{ Exception -> 0x0075 }
        switch(r0) {
            case 1: goto L_0x007a;
            case 2: goto L_0x0058;
            default: goto L_0x0053;
        };
    L_0x0053:
        r0 = super.onStartCommand(r8, r9, r10);
        goto L_0x0007;
    L_0x0058:
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x0075 }
        r0 = TAG;	 Catch:{ Exception -> 0x0075 }
        r2 = 2;
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x0075 }
        r5 = 0;
        r6 = "开始下载资源";
        r4[r5] = r6;	 Catch:{ Exception -> 0x0075 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r2, r4);	 Catch:{ Exception -> 0x0075 }
        r0 = r1 + -1;
        r7.setAlarm(r3, r0);	 Catch:{ Exception -> 0x0075 }
        r7.checkCache();	 Catch:{ Exception -> 0x0075 }
        r7.downloadResource(r3, r0);	 Catch:{ Exception -> 0x0075 }
        goto L_0x0053;
    L_0x0075:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0053;
    L_0x007a:
        r7.deleteAlarm(r3, r1);	 Catch:{ Exception -> 0x0075 }
        r0 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x0075 }
        r0 = TAG;	 Catch:{ Exception -> 0x0075 }
        r1 = 2;
        r2 = 1;
        r2 = new java.lang.String[r2];	 Catch:{ Exception -> 0x0075 }
        r4 = 0;
        r5 = "下载资源后显示通知";
        r2[r4] = r5;	 Catch:{ Exception -> 0x0075 }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r0, r1, r2);	 Catch:{ Exception -> 0x0075 }
        r7.notification(r3);	 Catch:{ Exception -> 0x0075 }
        r0 = r7.c;	 Catch:{ Exception -> 0x0075 }
        r1 = r3.msg_id;	 Catch:{ Exception -> 0x0075 }
        r0.remove(r1);	 Catch:{ Exception -> 0x0075 }
        r0 = r7.c;	 Catch:{ Exception -> 0x0075 }
        r0 = r0.size();	 Catch:{ Exception -> 0x0075 }
        if (r0 != 0) goto L_0x0053;
    L_0x00a0:
        r7.stopSelf();	 Catch:{ Exception -> 0x0075 }
        goto L_0x0053;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UmengDownloadResourceService.onStartCommand(android.content.Intent, int, int):int");
    }

    public void notification(UMessage uMessage) {
        UHandler adHandler;
        if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
            adHandler = PushAgent.getInstance(this).getAdHandler();
        } else {
            adHandler = PushAgent.getInstance(this).getMessageHandler();
        }
        if (adHandler == null) {
            return;
        }
        if (TextUtils.equals(UMessage.DISPLAY_TYPE_AUTOUPDATE, uMessage.display_type)) {
            UmengMessageHandler umengMessageHandler = (UmengMessageHandler) PushAgent.getInstance(this.b).getMessageHandler();
            if (umengMessageHandler != null) {
                umengMessageHandler.dealWithNotificationMessage(this.b, uMessage);
                return;
            }
            return;
        }
        adHandler.handleMessage(this, uMessage);
    }

    @SuppressLint({"NewApi"})
    public void downloadResource(UMessage uMessage, int i) {
        DownloadResourceTask downloadResourceTask = new DownloadResourceTask(uMessage, i);
        if (VERSION.SDK_INT >= 11) {
            downloadResourceTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            downloadResourceTask.execute(new Void[0]);
        }
    }

    public void setAlarm(UMessage uMessage, int i) {
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(TAG, 2, "setAlarm");
        ((AlarmManager) getSystemService("alarm")).set(1, System.currentTimeMillis() + 300000, a(uMessage, i));
    }

    public void deleteAlarm(UMessage uMessage, int i) {
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(TAG, 2, "deleteAlarm");
        ((AlarmManager) getSystemService("alarm")).cancel(a(uMessage, i));
    }

    private PendingIntent a(UMessage uMessage, int i) {
        String jSONObject = uMessage.getRaw().toString();
        int hashCode = uMessage.msg_id.hashCode();
        Intent intent = new Intent(this.b, UmengDownloadResourceService.class);
        intent.putExtra("body", jSONObject);
        intent.putExtra("id", uMessage.message_id);
        intent.putExtra("task_id", uMessage.task_id);
        intent.putExtra(f, 2);
        intent.putExtra(e, i);
        PendingIntent service = PendingIntent.getService(this.b, hashCode, intent, 134217728);
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(TAG, 2, "PendingIntent: msgId:" + uMessage.msg_id + ",requestCode:" + hashCode + ",retryTime:" + i);
        return service;
    }

    public void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkCache() {
        try {
            checkDir(new File(getMessageResourceFolder(this.b, null)), i, 86400000);
        } catch (Throwable th) {
        }
    }

    public static void checkDir(final File file, long j, final long j2) throws IOException {
        if (file.exists() && a(file.getCanonicalFile()) > j) {
            if (m == null) {
                m = new Thread(new Runnable() {
                    public void run() {
                        UmengDownloadResourceService.b(file, j2);
                        UmengDownloadResourceService.m = null;
                    }
                });
            }
            synchronized (m) {
                m.start();
            }
        }
    }

    private static long a(File file) {
        long j = 0;
        if (file == null || !file.exists() || !file.isDirectory()) {
            return 0;
        }
        Stack stack = new Stack();
        stack.clear();
        stack.push(file);
        while (true) {
            long j2 = j;
            if (stack.isEmpty()) {
                return j2;
            }
            j = j2;
            for (File file2 : ((File) stack.pop()).listFiles()) {
                if (!file2.isDirectory()) {
                    j += file2.length();
                }
            }
        }
    }

    private static void b(File file, long j) {
        if (file != null && file.exists() && file.canWrite() && file.isDirectory()) {
            for (File file2 : file.listFiles()) {
                if (!file2.isDirectory() && System.currentTimeMillis() - file2.lastModified() > j) {
                    file2.delete();
                }
            }
        }
    }

    public static String getMessageResourceFolder(Context context, UMessage uMessage) {
        String str = context.getCacheDir() + "/umeng_push/";
        if (uMessage == null || uMessage.msg_id == null) {
            return str;
        }
        return str + uMessage.msg_id + "/";
    }
}
