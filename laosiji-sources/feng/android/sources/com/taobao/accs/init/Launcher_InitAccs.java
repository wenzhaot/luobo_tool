package com.taobao.accs.init;

import android.content.Context;
import com.taobao.accs.IAppReceiver;
import com.taobao.accs.ILoginInfo;
import com.taobao.accs.client.GlobalClientInfo;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public class Launcher_InitAccs implements Serializable {
    protected static final Map<String, String> a = new HashMap();
    public static String defaultAppkey = "21646297";
    public static IAppReceiver mAppReceiver = new d();
    public static String mAppkey;
    public static Context mContext;
    public static boolean mForceBindUser = false;
    public static boolean mIsInited = false;
    public static String mSid;
    public static String mTtid;
    public static String mUserId;

    /* compiled from: Taobao */
    static class a implements ILoginInfo {
        a() {
        }

        public String getSid() {
            return Launcher_InitAccs.mSid;
        }

        public String getUserId() {
            return Launcher_InitAccs.mUserId;
        }

        public String getNick() {
            return null;
        }

        public String getEcode() {
            return null;
        }

        public String getHeadPicLink() {
            return null;
        }

        public String getSsoToken() {
            return null;
        }

        public boolean getCommentUsed() {
            return false;
        }
    }

    static {
        a.put("im", "com.taobao.tao.amp.remote.AccsReceiverCallback");
        a.put("powermsg", "com.taobao.appfrmbundle.mkt.AccsReceiverService");
        a.put("pmmonitor", "com.taobao.appfrmbundle.mkt.AccsReceiverService");
        a.put("motu-remote", "com.taobao.tao.log.collect.AccsTlogService");
        a.put("cloudsync", "com.taobao.datasync.network.accs.AccsCloudSyncService");
        a.put("acds", "com.taobao.acds.compact.AccsACDSService");
        a.put(GlobalClientInfo.AGOO_SERVICE_ID, "org.android.agoo.accs.AgooService");
        a.put(AgooConstants.AGOO_SERVICE_AGOOACK, "org.android.agoo.accs.AgooService");
        a.put("agooTokenReport", "org.android.agoo.accs.AgooService");
        a.put("AliLive", "com.taobao.playbudyy.gameplugin.danmu.DanmuCallbackService");
        a.put("orange", "com.taobao.orange.accssupport.OrangeAccsService");
        a.put("tsla", "com.taobao.android.festival.accs.HomepageAccsMassService");
        a.put("taobaoWaimaiAccsService", "com.taobao.takeout.order.detail.service.TakeoutOrderDetailACCSService");
        a.put("login", "com.taobao.android.sso.v2.service.LoginAccsService");
        a.put("ranger_abtest", "com.taobao.ranger3.RangerACCSService");
        a.put("accs_poplayer", "com.taobao.tbpoplayer.AccsPopLayerService");
        a.put("dm_abtest", "com.tmall.wireless.ant.accs.AntAccsService");
    }

    /* JADX WARNING: Removed duplicated region for block: B:15:0x0071 A:{Catch:{ Throwable -> 0x0135 }} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0087 A:{Catch:{ Throwable -> 0x0135 }} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0071 A:{Catch:{ Throwable -> 0x0135 }} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0087 A:{Catch:{ Throwable -> 0x0135 }} */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0071 A:{Catch:{ Throwable -> 0x0135 }} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x0087 A:{Catch:{ Throwable -> 0x0135 }} */
    public void init(android.app.Application r10, java.util.HashMap<java.lang.String, java.lang.Object> r11) {
        /*
        r9 = this;
        r6 = 3;
        r4 = 2;
        r2 = 1;
        r3 = 0;
        r0 = "Launcher_InitAccs";
        r1 = "init";
        r5 = new java.lang.Object[r3];
        com.taobao.accs.utl.ALog.i(r0, r1, r5);
        r0 = r10.getApplicationContext();	 Catch:{ Throwable -> 0x0135 }
        r0 = com.stub.StubApp.getOrigApplicationContext(r0);	 Catch:{ Throwable -> 0x0135 }
        mContext = r0;	 Catch:{ Throwable -> 0x0135 }
        r1 = 0;
        r0 = "envIndex";
        r0 = r11.get(r0);	 Catch:{ Throwable -> 0x0124 }
        r0 = (java.lang.Integer) r0;	 Catch:{ Throwable -> 0x0124 }
        r5 = r0.intValue();	 Catch:{ Throwable -> 0x0124 }
        r0 = "onlineAppKey";
        r0 = r11.get(r0);	 Catch:{ Throwable -> 0x0124 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x0124 }
        mAppkey = r0;	 Catch:{ Throwable -> 0x0124 }
        if (r5 != r2) goto L_0x010a;
    L_0x0034:
        r0 = "preAppKey";
        r0 = r11.get(r0);	 Catch:{ Throwable -> 0x0124 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x0124 }
        mAppkey = r0;	 Catch:{ Throwable -> 0x0124 }
    L_0x003f:
        r0 = "process";
        r0 = r11.get(r0);	 Catch:{ Throwable -> 0x0142 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x0142 }
        r1 = "ttid";
        r1 = r11.get(r1);	 Catch:{ Throwable -> 0x0144 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0144 }
        mTtid = r1;	 Catch:{ Throwable -> 0x0144 }
        r1 = "userId";
        r1 = r11.get(r1);	 Catch:{ Throwable -> 0x0144 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0144 }
        mUserId = r1;	 Catch:{ Throwable -> 0x0144 }
        r1 = "sid";
        r1 = r11.get(r1);	 Catch:{ Throwable -> 0x0144 }
        r1 = (java.lang.String) r1;	 Catch:{ Throwable -> 0x0144 }
        mSid = r1;	 Catch:{ Throwable -> 0x0144 }
    L_0x0069:
        r1 = mAppkey;	 Catch:{ Throwable -> 0x0135 }
        r1 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x0135 }
        if (r1 == 0) goto L_0x0081;
    L_0x0071:
        r1 = "Launcher_InitAccs";
        r4 = "init get appkey null！！";
        r5 = 0;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.utl.ALog.e(r1, r4, r5);	 Catch:{ Throwable -> 0x0135 }
        r1 = defaultAppkey;	 Catch:{ Throwable -> 0x0135 }
        mAppkey = r1;	 Catch:{ Throwable -> 0x0135 }
    L_0x0081:
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x0135 }
        if (r1 == 0) goto L_0x009d;
    L_0x0087:
        r0 = "Launcher_InitAccs";
        r1 = "init get process null！！";
        r4 = 0;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.utl.ALog.e(r0, r1, r4);	 Catch:{ Throwable -> 0x0135 }
        r0 = mContext;	 Catch:{ Throwable -> 0x0135 }
        r1 = android.os.Process.myPid();	 Catch:{ Throwable -> 0x0135 }
        r0 = com.taobao.accs.utl.a.a(r0, r1);	 Catch:{ Throwable -> 0x0135 }
    L_0x009d:
        r1 = "Launcher_InitAccs";
        r4 = "init";
        r5 = 6;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x0135 }
        r6 = 0;
        r7 = "appkey";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0135 }
        r6 = 1;
        r7 = mAppkey;	 Catch:{ Throwable -> 0x0135 }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0135 }
        r6 = 2;
        r7 = "mode";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0135 }
        r6 = 3;
        r7 = java.lang.Integer.valueOf(r2);	 Catch:{ Throwable -> 0x0135 }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0135 }
        r6 = 4;
        r7 = "process";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x0135 }
        r6 = 5;
        r5[r6] = r0;	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.utl.ALog.i(r1, r4, r5);	 Catch:{ Throwable -> 0x0135 }
        r1 = mContext;	 Catch:{ Throwable -> 0x0135 }
        r4 = mAppkey;	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.ACCSManager.setAppkey(r1, r4, r2);	 Catch:{ Throwable -> 0x0135 }
        r1 = mContext;	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.ACCSManager.setMode(r1, r2);	 Catch:{ Throwable -> 0x0135 }
        r1 = mContext;	 Catch:{ Throwable -> 0x0135 }
        r2 = new com.taobao.accs.init.Launcher_InitAccs$a;	 Catch:{ Throwable -> 0x0135 }
        r2.<init>();	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.ACCSManager.setLoginInfoImpl(r1, r2);	 Catch:{ Throwable -> 0x0135 }
        r1 = android.text.TextUtils.isEmpty(r0);	 Catch:{ Throwable -> 0x0135 }
        if (r1 != 0) goto L_0x00fd;
    L_0x00e6:
        r1 = mContext;	 Catch:{ Throwable -> 0x0135 }
        r1 = r1.getPackageName();	 Catch:{ Throwable -> 0x0135 }
        r0 = r0.equals(r1);	 Catch:{ Throwable -> 0x0135 }
        if (r0 == 0) goto L_0x00fd;
    L_0x00f2:
        r0 = mContext;	 Catch:{ Throwable -> 0x0135 }
        r1 = mAppkey;	 Catch:{ Throwable -> 0x0135 }
        r2 = mTtid;	 Catch:{ Throwable -> 0x0135 }
        r4 = mAppReceiver;	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.ACCSManager.startInAppConnection(r0, r1, r2, r4);	 Catch:{ Throwable -> 0x0135 }
    L_0x00fd:
        r0 = new com.taobao.accs.init.c;	 Catch:{ Throwable -> 0x0135 }
        r0.<init>(r9);	 Catch:{ Throwable -> 0x0135 }
        r4 = 10;
        r1 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.common.ThreadPoolExecutorFactory.schedule(r0, r4, r1);	 Catch:{ Throwable -> 0x0135 }
    L_0x0109:
        return;
    L_0x010a:
        if (r5 != r4) goto L_0x0120;
    L_0x010c:
        r0 = r2;
    L_0x010d:
        if (r5 != r6) goto L_0x0122;
    L_0x010f:
        r0 = r0 | r2;
        if (r0 == 0) goto L_0x0149;
    L_0x0112:
        r0 = "dailyAppkey";
        r0 = r11.get(r0);	 Catch:{ Throwable -> 0x0124 }
        r0 = (java.lang.String) r0;	 Catch:{ Throwable -> 0x0124 }
        mAppkey = r0;	 Catch:{ Throwable -> 0x0124 }
        r2 = r4;
        goto L_0x003f;
    L_0x0120:
        r0 = r3;
        goto L_0x010d;
    L_0x0122:
        r2 = r3;
        goto L_0x010f;
    L_0x0124:
        r0 = move-exception;
        r2 = r3;
    L_0x0126:
        r4 = "Launcher_InitAccs";
        r5 = "init get param error";
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x0135 }
        com.taobao.accs.utl.ALog.e(r4, r5, r0, r6);	 Catch:{ Throwable -> 0x0135 }
        r0 = r1;
        goto L_0x0069;
    L_0x0135:
        r0 = move-exception;
        r1 = "Launcher_InitAccs";
        r2 = "init";
        r3 = new java.lang.Object[r3];
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);
        goto L_0x0109;
    L_0x0142:
        r0 = move-exception;
        goto L_0x0126;
    L_0x0144:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0126;
    L_0x0149:
        r2 = r3;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.init.Launcher_InitAccs.init(android.app.Application, java.util.HashMap):void");
    }
}
