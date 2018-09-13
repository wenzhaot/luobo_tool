package anetwork.channel.aidl.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import anet.channel.util.ALog;
import anet.channel.util.Utils;
import anetwork.channel.aidl.IRemoteNetworkGetter;
import anetwork.channel.aidl.IRemoteNetworkGetter.Stub;
import anetwork.channel.aidl.NetworkService;
import com.stub.StubApp;
import java.util.concurrent.CountDownLatch;

/* compiled from: Taobao */
public class RemoteGetterHelper {
    private static final String TAG = "anet.RemoteGetter";
    private static volatile boolean bBindFailed = false;
    private static volatile boolean bBinding = false;
    private static ServiceConnection conn = new ServiceConnection() {
        public void onServiceDisconnected(ComponentName componentName) {
            if (ALog.isPrintLog(2)) {
                ALog.i(RemoteGetterHelper.TAG, "ANet_Service Disconnected", null, new Object[0]);
            }
            RemoteGetterHelper.mGetter = null;
            RemoteGetterHelper.bBinding = false;
            if (RemoteGetterHelper.mServiceBindLock != null) {
                RemoteGetterHelper.mServiceBindLock.countDown();
            }
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            if (ALog.isPrintLog(2)) {
                ALog.i(RemoteGetterHelper.TAG, "[onServiceConnected]ANet_Service start success. ANet run with service mode", null, new Object[0]);
            }
            synchronized (RemoteGetterHelper.class) {
                RemoteGetterHelper.mGetter = Stub.asInterface(iBinder);
                if (RemoteGetterHelper.mServiceBindLock != null) {
                    RemoteGetterHelper.mServiceBindLock.countDown();
                }
            }
            RemoteGetterHelper.bBindFailed = false;
            RemoteGetterHelper.bBinding = false;
        }
    };
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static volatile IRemoteNetworkGetter mGetter;
    private static volatile CountDownLatch mServiceBindLock = null;

    /* JADX WARNING: Missing block: B:26:?, code:
            anet.channel.util.ALog.i(TAG, "[initRemoteGetterAndWait]begin to wait 10s", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:27:0x0051, code:
            if (mServiceBindLock.await(10, java.util.concurrent.TimeUnit.SECONDS) == false) goto L_0x0061;
     */
    /* JADX WARNING: Missing block: B:28:0x0053, code:
            anet.channel.util.ALog.i(TAG, "mServiceBindLock count down to 0", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:29:0x0061, code:
            anet.channel.util.ALog.i(TAG, "mServiceBindLock wait timeout", null, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:35:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:37:?, code:
            return;
     */
    public static void initRemoteGetterAndWait(android.content.Context r6, boolean r7) {
        /*
        r5 = 0;
        r4 = 0;
        r0 = mGetter;
        if (r0 == 0) goto L_0x0007;
    L_0x0006:
        return;
    L_0x0007:
        r0 = bBindFailed;
        if (r0 != 0) goto L_0x0006;
    L_0x000b:
        asyncBindService(r6);
        r0 = bBindFailed;
        if (r0 != 0) goto L_0x0006;
    L_0x0012:
        if (r7 == 0) goto L_0x0006;
    L_0x0014:
        r1 = anetwork.channel.aidl.adapter.RemoteGetterHelper.class;
        monitor-enter(r1);	 Catch:{ InterruptedException -> 0x0020 }
        r0 = mGetter;	 Catch:{ all -> 0x001d }
        if (r0 == 0) goto L_0x002d;
    L_0x001b:
        monitor-exit(r1);	 Catch:{ all -> 0x001d }
        goto L_0x0006;
    L_0x001d:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ InterruptedException -> 0x0020 }
        throw r0;	 Catch:{ InterruptedException -> 0x0020 }
    L_0x0020:
        r0 = move-exception;
        r0 = "anet.RemoteGetter";
        r1 = "mServiceBindLock wait interrupt";
        r2 = new java.lang.Object[r4];
        anet.channel.util.ALog.e(r0, r1, r5, r2);
        goto L_0x0006;
    L_0x002d:
        r0 = mServiceBindLock;	 Catch:{ all -> 0x001d }
        if (r0 != 0) goto L_0x0039;
    L_0x0031:
        r0 = new java.util.concurrent.CountDownLatch;	 Catch:{ all -> 0x001d }
        r2 = 1;
        r0.<init>(r2);	 Catch:{ all -> 0x001d }
        mServiceBindLock = r0;	 Catch:{ all -> 0x001d }
    L_0x0039:
        monitor-exit(r1);	 Catch:{ all -> 0x001d }
        r0 = "anet.RemoteGetter";
        r1 = "[initRemoteGetterAndWait]begin to wait 10s";
        r2 = 0;
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ InterruptedException -> 0x0020 }
        anet.channel.util.ALog.i(r0, r1, r2, r3);	 Catch:{ InterruptedException -> 0x0020 }
        r0 = mServiceBindLock;	 Catch:{ InterruptedException -> 0x0020 }
        r2 = 10;
        r1 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ InterruptedException -> 0x0020 }
        r0 = r0.await(r2, r1);	 Catch:{ InterruptedException -> 0x0020 }
        if (r0 == 0) goto L_0x0061;
    L_0x0053:
        r0 = "anet.RemoteGetter";
        r1 = "mServiceBindLock count down to 0";
        r2 = 0;
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ InterruptedException -> 0x0020 }
        anet.channel.util.ALog.i(r0, r1, r2, r3);	 Catch:{ InterruptedException -> 0x0020 }
        goto L_0x0006;
    L_0x0061:
        r0 = "anet.RemoteGetter";
        r1 = "mServiceBindLock wait timeout";
        r2 = 0;
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ InterruptedException -> 0x0020 }
        anet.channel.util.ALog.i(r0, r1, r2, r3);	 Catch:{ InterruptedException -> 0x0020 }
        goto L_0x0006;
        */
        throw new UnsupportedOperationException("Method not decompiled: anetwork.channel.aidl.adapter.RemoteGetterHelper.initRemoteGetterAndWait(android.content.Context, boolean):void");
    }

    public static IRemoteNetworkGetter getRemoteGetter() {
        return mGetter;
    }

    private static void asyncBindService(Context context) {
        boolean z = true;
        if (ALog.isPrintLog(2)) {
            ALog.i(TAG, "[asyncBindService] mContext:" + context + " bBindFailed:" + bBindFailed + " bBinding:" + bBinding, null, new Object[0]);
        }
        if (context != null && !bBindFailed && !bBinding) {
            bBinding = true;
            try {
                boolean z2;
                if (Boolean.valueOf(Utils.invokeStaticMethodThrowException("com.taobao.android.service.Services", "bind", new Class[]{Context.class, Class.class, ServiceConnection.class}, StubApp.getOrigApplicationContext(context.getApplicationContext()), IRemoteNetworkGetter.class, conn) + "").booleanValue()) {
                    z2 = false;
                } else {
                    z2 = true;
                }
                bBindFailed = z2;
            } catch (Throwable e) {
                ALog.w(TAG, "[asyncBindService]use taobao framwork start service error", null, e, new Object[0]);
                bBindFailed = true;
                if ((e instanceof ClassNotFoundException) || (e instanceof NoSuchMethodException)) {
                    ALog.i(TAG, "[asyncBindService]Cannot found Service Farmwork,User System intent start Anet service", null, new Object[0]);
                    Intent intent = new Intent(context, NetworkService.class);
                    intent.setAction(IRemoteNetworkGetter.class.getName());
                    intent.addCategory("android.intent.category.DEFAULT");
                    if (context.bindService(intent, conn, 1)) {
                        z = false;
                    }
                    bBindFailed = z;
                }
            }
            if (bBindFailed) {
                bBinding = false;
                ALog.w(TAG, "[asyncBindService]ANet_Service start not success.ANet run with local mode!", null, new Object[0]);
            }
            handler.postDelayed(new Runnable() {
                public void run() {
                    if (RemoteGetterHelper.bBinding) {
                        RemoteGetterHelper.bBinding = false;
                        ALog.w(RemoteGetterHelper.TAG, "binding service timeout. reset status!", null, new Object[0]);
                    }
                }
            }, 15000);
            if (ALog.isPrintLog(2)) {
                ALog.i(TAG, "[asyncBindService] end", null, new Object[0]);
            }
        }
    }
}
