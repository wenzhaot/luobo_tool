package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.Process;
import android.text.TextUtils;
import anet.channel.appmonitor.AppMonitor;
import com.feng.car.utils.FengConstant;
import com.stub.StubApp;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.a.a.a;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.IBaseService;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.Constants.Operate;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.net.r;
import com.taobao.accs.ut.monitor.ElectionRateMonitor;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.l;
import com.tencent.tauth.AuthActivity;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.android.agoo.common.AgooConstants;

/* compiled from: Taobao */
public abstract class b implements IBaseService {
    public static final String ELECTION_KEY_BLACKLIST = "blacklist";
    public static final String ELECTION_KEY_HOST = "host";
    public static final String ELECTION_KEY_SDKVS = "sdkvs";
    public static final String ELECTION_KEY_VERSION = "elversion";
    public static final String ELECTION_SERVICE_ID = "accs_election";
    public static final int ELE_ERROR_EXCEPTION = -901;
    public static final int ELE_ERROR_SERVER = -900;
    public static final int ELE_LIST_NULL = -902;
    protected static ConcurrentHashMap<String, com.taobao.accs.net.b> a = new ConcurrentHashMap(2);
    private static int g = 0;
    private static boolean h = false;
    private Context b;
    private boolean c = false;
    private boolean d = true;
    private Map<String, Integer> e = null;
    private ScheduledThreadPoolExecutor f;
    private Service i = null;
    private ElectionRateMonitor j;
    private ElectionRateMonitor k;
    private ScheduledFuture<?> l;
    private ScheduledFuture<?> m;
    private AccsAbstractDataListener n = new c(this);
    private a o;
    private boolean p = false;
    private boolean q = false;
    private ScheduledFuture<?> r;

    public abstract int a(Intent intent);

    public abstract void a();

    public b(Service service) {
        this.i = service;
        this.b = StubApp.getOrigApplicationContext(service.getApplicationContext());
        this.e = new HashMap();
        this.f = ThreadPoolExecutorFactory.getScheduledExecutor();
        AppMonitor.getInstance().register(ElectionRateMonitor.class);
    }

    public void onCreate() {
        ALog.i("ElectionServiceImpl", "onCreate,", "sdkv", Integer.valueOf(Constants.SDK_VERSION_CODE));
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (intent == null) {
            return 2;
        }
        CharSequence schemeSpecificPart;
        CharSequence action = intent.getAction();
        CharSequence packageName = this.b.getPackageName();
        ALog.i("ElectionServiceImpl", "onStartCommand begin", AuthActivity.ACTION_KEY, action);
        if (com.taobao.accs.a.a.b()) {
            try {
                if (TextUtils.equals(action, "android.intent.action.PACKAGE_REMOVED")) {
                    schemeSpecificPart = intent.getData().getSchemeSpecificPart();
                    boolean booleanExtra = intent.getBooleanExtra("android.intent.extra.REPLACING", false);
                    a a = com.taobao.accs.a.a.a(this.b);
                    CharSequence charSequence = a.a;
                    ALog.w("ElectionServiceImpl", "onstartcommand PACKAGE_REMOVED", Constants.KEY_ELECTION_PKG, schemeSpecificPart, "host", charSequence, "replaced", Boolean.valueOf(booleanExtra));
                    if (TextUtils.isEmpty(charSequence) || !TextUtils.equals(charSequence, schemeSpecificPart)) {
                        ALog.i("ElectionServiceImpl", "onstartcommand PACKAGE_REMOVED no need election", new Object[0]);
                    } else if (this.b.getPackageName().equals(com.taobao.accs.a.a.e(this.b))) {
                        a.b = 0;
                        com.taobao.accs.a.a.a(this.b, a);
                        a(this.b, "host removed");
                    } else {
                        ALog.i("ElectionServiceImpl", "onstartcommand PACKAGE_REMOVED no need election", new Object[0]);
                    }
                } else if (TextUtils.equals(action, com.taobao.accs.a.a.c())) {
                    ALog.i("ElectionServiceImpl", "operate is receive", "operate", (Operate) intent.getSerializableExtra("operate"));
                    Intent intent2;
                    switch ((Operate) intent.getSerializableExtra("operate")) {
                        case TRY_ELECTION:
                            d();
                            break;
                        case START_ELECTION:
                            a(this.b, intent.getStringExtra("reason"));
                            break;
                        case ASK_VERSION:
                            String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
                            int intExtra = intent.getIntExtra(ELECTION_KEY_VERSION, 0);
                            intent2 = new Intent(com.taobao.accs.a.a.c());
                            intent2.putExtra("operate", Operate.REPORT_VERSION);
                            intent2.putExtra(Constants.KEY_PACKAGE_NAME, this.b.getPackageName());
                            intent2.setPackage(stringExtra);
                            intent2.setClassName(stringExtra, com.taobao.accs.utl.a.channelService);
                            int i3 = Constants.SDK_VERSION_CODE;
                            if (com.taobao.accs.a.a.a(this.b, this.b.getPackageName(), intExtra)) {
                                intent2.putExtra(Constants.KEY_SDK_VERSION, Constants.SDK_VERSION_CODE);
                            } else {
                                i3 = 0;
                            }
                            this.b.startService(intent2);
                            ALog.i("ElectionServiceImpl", AgooConstants.MESSAGE_REPORT, "sdkv", Integer.valueOf(i3), "from pkg", this.b.getPackageName(), "to pkg", stringExtra);
                            break;
                        case REPORT_VERSION:
                            if (!this.c) {
                                ALog.e("ElectionServiceImpl", "not electioning, but receive report", new Object[0]);
                                break;
                            }
                            String stringExtra2 = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
                            int intExtra2 = intent.getIntExtra(Constants.KEY_SDK_VERSION, 0);
                            g--;
                            if (intExtra2 != 0) {
                                this.e.put(stringExtra2, Integer.valueOf(intExtra2));
                            }
                            ALog.i("ElectionServiceImpl", "collect info", "sdkv", Integer.valueOf(intExtra2), "election pkg", stringExtra2, "electionPackCount", Integer.valueOf(g));
                            if (g == 0) {
                                e();
                                break;
                            }
                            break;
                        case RESULT_ELECTION:
                            this.d = true;
                            if (this.m != null) {
                                this.m.cancel(true);
                                this.m = null;
                            }
                            schemeSpecificPart = intent.getStringExtra("sudoPack");
                            ALog.i("ElectionServiceImpl", "election result", "host", schemeSpecificPart, "curr pkg", packageName);
                            com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_ELECTION_PKG_TIMES, schemeSpecificPart, 0.0d);
                            com.taobao.accs.a.a.b(this.b);
                            if (!TextUtils.isEmpty(schemeSpecificPart)) {
                                if (!TextUtils.equals(schemeSpecificPart, packageName)) {
                                    a(true);
                                    break;
                                }
                                a();
                                break;
                            }
                            break;
                        case PING_ELECTION:
                            schemeSpecificPart = com.taobao.accs.a.a.a(this.b).a;
                            CharSequence stringExtra3 = intent.getStringExtra("pingPack");
                            if (!(TextUtils.isEmpty(schemeSpecificPart) || TextUtils.isEmpty(stringExtra3) || !TextUtils.equals(schemeSpecificPart, packageName))) {
                                ALog.i("ElectionServiceImpl", "host receive ping, and report ping", "to pkg", stringExtra3, "host", schemeSpecificPart);
                                intent2 = new Intent(com.taobao.accs.a.a.c());
                                intent2.setPackage(stringExtra3);
                                intent2.setClassName(stringExtra3, com.taobao.accs.utl.a.channelService);
                                intent2.putExtra("operate", Operate.PING_ELECTION);
                                intent2.putExtra("isPing", true);
                                intent2.putExtra("pingPack", stringExtra3);
                                intent2.putExtra(Constants.KEY_SDK_VERSION, Constants.SDK_VERSION_CODE);
                                this.b.startService(intent2);
                                a();
                            }
                            if (TextUtils.equals(stringExtra3, packageName)) {
                                ALog.i("ElectionServiceImpl", "receive host's ping back", "host", schemeSpecificPart);
                                h = intent.getBooleanExtra("isPing", false);
                                break;
                            }
                            break;
                    }
                    return 2;
                }
            } catch (Throwable th) {
                ALog.e("ElectionServiceImpl", "onStartCommand", th, new Object[0]);
            }
        } else if (TextUtils.equals(action, com.taobao.accs.a.a.c())) {
            ALog.e("ElectionServiceImpl", "election disabled", new Object[0]);
            return 2;
        }
        if (TextUtils.equals(action, Constants.ACTION_START_SERVICE)) {
            try {
                Object stringExtra4 = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
                Object stringExtra5 = intent.getStringExtra(Constants.KEY_APP_KEY);
                String stringExtra6 = intent.getStringExtra(Constants.KEY_TTID);
                String stringExtra7 = intent.getStringExtra("app_sercet");
                String stringExtra8 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
                int intExtra3 = intent.getIntExtra(Constants.KEY_MODE, 0);
                ALog.i("ElectionServiceImpl", "try to saveAppKey", Constants.KEY_CONFIG_TAG, stringExtra8, "appkey", stringExtra5, "appSecret", stringExtra7, Constants.KEY_TTID, stringExtra6, Constants.KEY_ELECTION_PKG, stringExtra4);
                if (!(TextUtils.isEmpty(stringExtra4) || TextUtils.isEmpty(stringExtra5) || !stringExtra4.equals(this.b.getPackageName()))) {
                    l.a(this.b, intExtra3);
                    com.taobao.accs.net.b a2 = a(this.b, stringExtra8, false, -1);
                    if (a2 != null) {
                        a2.a = stringExtra6;
                    } else {
                        ALog.e("ElectionServiceImpl", "start action, no connection", Constants.KEY_CONFIG_TAG, stringExtra8);
                    }
                    UtilityImpl.saveAppKey(this.b, stringExtra5, stringExtra7);
                }
            } catch (Throwable th2) {
                ALog.e("ElectionServiceImpl", "start action", th2, new Object[0]);
            }
            if (com.taobao.accs.a.a.b()) {
                return 2;
            }
        }
        schemeSpecificPart = com.taobao.accs.a.a.a(this.b).a;
        if (TextUtils.isEmpty(schemeSpecificPart) || TextUtils.equals(schemeSpecificPart, this.b.getPackageName()) || !com.taobao.accs.a.a.b()) {
            ALog.i("ElectionServiceImpl", "deliver to channelservice", "host pkg", schemeSpecificPart);
            return a(intent);
        }
        if (!(this.c || TextUtils.equals(action, "android.intent.action.PACKAGE_REMOVED"))) {
            ALog.i("ElectionServiceImpl", "not electioning and not host, stop", new Object[0]);
            a(true);
        }
        return 2;
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void onDestroy() {
        ALog.e("ElectionServiceImpl", "Service onDestroy", new Object[0]);
        this.b = null;
        this.i = null;
    }

    /* JADX WARNING: Removed duplicated region for block: B:29:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x007c  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:40:0x00a8  */
    private java.lang.String c() {
        /*
        r11 = this;
        r3 = 0;
        r1 = 1;
        r0 = 0;
        r2 = r11.e;	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        if (r2 == 0) goto L_0x0055;
    L_0x0007:
        r2 = r11.e;	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        r2 = r2.size();	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        if (r2 <= 0) goto L_0x0055;
    L_0x000f:
        r2 = r11.b;	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        r4 = r11.e;	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        r2 = com.taobao.accs.a.a.a(r2, r4);	 Catch:{ Throwable -> 0x0060, all -> 0x00a1 }
        r4 = r3;
        r5 = r0;
    L_0x0019:
        r3 = "ElectionServiceImpl";
        r6 = "localElection";
        r7 = 2;
        r7 = new java.lang.Object[r7];	 Catch:{ Throwable -> 0x00dc }
        r8 = 0;
        r9 = "host";
        r7[r8] = r9;	 Catch:{ Throwable -> 0x00dc }
        r8 = 1;
        r7[r8] = r2;	 Catch:{ Throwable -> 0x00dc }
        com.taobao.accs.utl.ALog.i(r3, r6, r7);	 Catch:{ Throwable -> 0x00dc }
        r3 = r11.k;
        if (r3 == 0) goto L_0x00de;
    L_0x0032:
        r3 = android.text.TextUtils.isEmpty(r4);
        if (r3 != 0) goto L_0x0040;
    L_0x0038:
        r3 = r11.k;
        r3.errorCode = r5;
        r3 = r11.k;
        r3.errorMsg = r4;
    L_0x0040:
        r3 = r11.k;
        r4 = android.text.TextUtils.isEmpty(r2);
        if (r4 == 0) goto L_0x005e;
    L_0x0048:
        r3.ret = r0;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r11.k;
        r0.commitStat(r1);
        r0 = r2;
    L_0x0054:
        return r0;
    L_0x0055:
        r4 = -902; // 0xfffffffffffffc7a float:NaN double:NaN;
        r2 = "apps is null";
        r5 = r4;
        r4 = r2;
        r2 = r3;
        goto L_0x0019;
    L_0x005e:
        r0 = r1;
        goto L_0x0048;
    L_0x0060:
        r2 = move-exception;
        r4 = r3;
        r5 = r0;
        r10 = r2;
        r2 = r3;
        r3 = r10;
    L_0x0066:
        r6 = "ElectionServiceImpl";
        r7 = "localElection error";
        r8 = 0;
        r8 = new java.lang.Object[r8];	 Catch:{ all -> 0x00d0 }
        com.taobao.accs.utl.ALog.e(r6, r7, r3, r8);	 Catch:{ all -> 0x00d0 }
        r5 = -901; // 0xfffffffffffffc7b float:NaN double:NaN;
        r3 = r3.toString();	 Catch:{ all -> 0x00d0 }
        r4 = r11.k;
        if (r4 == 0) goto L_0x00de;
    L_0x007c:
        r4 = android.text.TextUtils.isEmpty(r3);
        if (r4 != 0) goto L_0x008a;
    L_0x0082:
        r4 = r11.k;
        r4.errorCode = r5;
        r4 = r11.k;
        r4.errorMsg = r3;
    L_0x008a:
        r3 = r11.k;
        r4 = android.text.TextUtils.isEmpty(r2);
        if (r4 == 0) goto L_0x009f;
    L_0x0092:
        r3.ret = r0;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r11.k;
        r0.commitStat(r1);
        r0 = r2;
        goto L_0x0054;
    L_0x009f:
        r0 = r1;
        goto L_0x0092;
    L_0x00a1:
        r2 = move-exception;
        r4 = r3;
        r5 = r0;
    L_0x00a4:
        r6 = r11.k;
        if (r6 == 0) goto L_0x00c9;
    L_0x00a8:
        r6 = android.text.TextUtils.isEmpty(r4);
        if (r6 != 0) goto L_0x00b6;
    L_0x00ae:
        r6 = r11.k;
        r6.errorCode = r5;
        r5 = r11.k;
        r5.errorMsg = r4;
    L_0x00b6:
        r4 = r11.k;
        r3 = android.text.TextUtils.isEmpty(r3);
        if (r3 == 0) goto L_0x00ca;
    L_0x00be:
        r4.ret = r0;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r11.k;
        r0.commitStat(r1);
    L_0x00c9:
        throw r2;
    L_0x00ca:
        r0 = r1;
        goto L_0x00be;
    L_0x00cc:
        r2 = move-exception;
        r5 = r4;
        r4 = r3;
        goto L_0x00a4;
    L_0x00d0:
        r3 = move-exception;
        r10 = r3;
        r3 = r2;
        r2 = r10;
        goto L_0x00a4;
    L_0x00d5:
        r2 = move-exception;
        r5 = r4;
        r4 = r3;
        r10 = r2;
        r2 = r3;
        r3 = r10;
        goto L_0x0066;
    L_0x00dc:
        r3 = move-exception;
        goto L_0x0066;
    L_0x00de:
        r0 = r2;
        goto L_0x0054;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.b.c():java.lang.String");
    }

    private void a(Map<String, Integer> map) {
        ALog.i("ElectionServiceImpl", "serverElection start", Constants.KEY_ELECTION_PACKS, map.toString());
        this.j = new ElectionRateMonitor();
        try {
            this.j.type = "server";
            if (this.k != null) {
                this.j.reason = this.k.reason;
            }
            GlobalClientInfo.getInstance(this.b).registerListener(ELECTION_SERVICE_ID, this.n);
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION_COMMAND);
            intent.putExtra(Constants.KEY_PACKAGE_NAME, this.b.getPackageName());
            intent.putExtra("command", 105);
            intent.putExtra(Constants.KEY_ELECTION_PACKS, (HashMap) map);
            a(intent);
        } catch (Throwable th) {
            this.j.errorCode = ELE_ERROR_EXCEPTION;
            this.j.errorMsg = th.toString();
        }
    }

    protected static com.taobao.accs.net.b a(Context context, String str, boolean z, int i) {
        Throwable th;
        Throwable th2;
        com.taobao.accs.net.b bVar;
        try {
            if (TextUtils.isEmpty(str)) {
                ALog.i("ElectionServiceImpl", "getConnection configTag null or env invalid", "command", Integer.valueOf(i), "conns:", Integer.valueOf(a.size()));
                if (a.size() > 0) {
                    return (com.taobao.accs.net.b) a.elements().nextElement();
                }
                return null;
            }
            ALog.i("ElectionServiceImpl", "getConnection", Constants.KEY_CONFIG_TAG, str);
            AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
            if (configByTag == null || !configByTag.getDisableChannel()) {
                int a = l.a(context);
                String str2 = "|" + a;
                bVar = (com.taobao.accs.net.b) a.get(str2);
                if (bVar != null) {
                    return bVar;
                }
                try {
                    AccsClientConfig.mEnv = a;
                    com.taobao.accs.net.b rVar = new r(context, 0, str);
                    if (z) {
                        try {
                            rVar.a();
                        } catch (Throwable th3) {
                            th = th3;
                            bVar = rVar;
                            th2 = th;
                            ALog.e("ElectionServiceImpl", "getConnection", th2, new Object[0]);
                            return bVar;
                        }
                    }
                    if (a.size() < 10) {
                        a.put(str2, rVar);
                        return rVar;
                    }
                    ALog.e("ElectionServiceImpl", "to many conns!!!", new Object[0]);
                    return rVar;
                } catch (Throwable th4) {
                    th2 = th4;
                    ALog.e("ElectionServiceImpl", "getConnection", th2, new Object[0]);
                    return bVar;
                }
            }
            ALog.e("ElectionServiceImpl", "channel disabled!", Constants.KEY_CONFIG_TAG, str);
            return null;
        } catch (Throwable th32) {
            th = th32;
            bVar = null;
            th2 = th;
            ALog.e("ElectionServiceImpl", "getConnection", th2, new Object[0]);
            return bVar;
        }
    }

    private void d() {
        String str;
        try {
            if (UtilityImpl.isFirstStart(this.b)) {
                b(this.b, "first start");
                UtilityImpl.setSdkStart(this.b);
                return;
            }
            str = com.taobao.accs.a.a.a(this.b).a;
            Object packageName = this.b.getPackageName();
            ALog.i("ElectionServiceImpl", "tryElection begin", "isFirstStart", Boolean.valueOf(UtilityImpl.isFirstStart(this.b)), "currentPack", packageName, "currentElectionPack", str);
            if (TextUtils.isEmpty(str)) {
                ALog.i("ElectionServiceImpl", "host is empty, try selectAppToElection", new Object[0]);
                b(this.b, "host null");
            } else if (TextUtils.equals(str, packageName)) {
                ALog.i("ElectionServiceImpl", "curr is host, no need election", new Object[0]);
                a();
            } else {
                Intent intent = new Intent(com.taobao.accs.a.a.c());
                intent.setPackage(str);
                intent.putExtra("operate", Operate.PING_ELECTION);
                intent.setClassName(str, com.taobao.accs.utl.a.channelService);
                intent.putExtra("pingPack", packageName);
                this.b.startService(intent);
                ALog.i("ElectionServiceImpl", "tryElection send PING_ELECTION", "to pkg", str);
                this.f.schedule(new d(this, str), 5, TimeUnit.SECONDS);
            }
        } catch (Throwable th) {
            ALog.e("ElectionServiceImpl", "tryElection error", th, new Object[0]);
        }
    }

    private void b(Context context, String str) {
        String e = com.taobao.accs.a.a.e(context);
        ALog.i("ElectionServiceImpl", "selectAppToElection", Constants.KEY_ELECTION_PKG, e);
        com.taobao.accs.a.a.b = false;
        this.d = false;
        Intent intent = new Intent(com.taobao.accs.a.a.c());
        if (TextUtils.isEmpty(e)) {
            intent.putExtra("operate", Operate.START_ELECTION);
            intent.putExtra("reason", str);
            intent.setPackage(context.getPackageName());
            intent.setClassName(context.getPackageName(), com.taobao.accs.utl.a.channelService);
        } else {
            intent.putExtra("operate", Operate.START_ELECTION);
            intent.putExtra("reason", str);
            intent.setPackage(e);
            intent.setClassName(e, com.taobao.accs.utl.a.channelService);
        }
        if (this.m != null) {
            this.m.cancel(true);
            this.m = null;
        }
        this.m = this.f.schedule(new e(this, context), 30, TimeUnit.SECONDS);
        context.startService(intent);
    }

    public void a(Context context, String str) {
        try {
            if (this.c) {
                ALog.w("ElectionServiceImpl", "isElectioning return", new Object[0]);
                return;
            }
            this.o = com.taobao.accs.a.a.a(context);
            if (this.o.b > 20) {
                ALog.w("ElectionServiceImpl", "startElection too many times, return", "times", Integer.valueOf(this.o.b));
                com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_ELECTION_OVER_MAX, str + UtilityImpl.getDeviceId(context), 0.0d);
                return;
            }
            this.k = new ElectionRateMonitor();
            this.k.type = AgooConstants.MESSAGE_LOCAL;
            this.k.reason = str;
            com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_ELECTION_START_TIMES, str, 0.0d);
            List<ResolveInfo> queryIntentServices = context.getPackageManager().queryIntentServices(new Intent(com.taobao.accs.a.a.c()), 32);
            this.c = true;
            if (queryIntentServices == null || queryIntentServices.size() < 2) {
                String str2 = "ElectionServiceImpl";
                String str3 = "startElection apps < 2";
                Object[] objArr = new Object[2];
                objArr[0] = "services";
                objArr[1] = queryIntentServices == null ? "null" : queryIntentServices.toString();
                ALog.i(str2, str3, objArr);
                e();
                return;
            }
            g = 0;
            ALog.i("ElectionServiceImpl", "startElection begin", "locallist", queryIntentServices.toString(), FengConstant.SIZE, Integer.valueOf(queryIntentServices.size()));
            for (ResolveInfo resolveInfo : queryIntentServices) {
                if (!(resolveInfo == null || resolveInfo.serviceInfo == null)) {
                    String str4 = resolveInfo.serviceInfo.packageName;
                    if (TextUtils.isEmpty(str4)) {
                        ALog.i("ElectionServiceImpl", "startElection unvailable app", Constants.KEY_ELECTION_PKG, str4);
                    } else {
                        Intent intent = new Intent(com.taobao.accs.a.a.c());
                        intent.putExtra("operate", Operate.ASK_VERSION);
                        intent.setPackage(str4);
                        intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
                        intent.putExtra(ELECTION_KEY_VERSION, 1);
                        intent.setClassName(str4, com.taobao.accs.utl.a.channelService);
                        ALog.i("ElectionServiceImpl", "startElection askversion", "receive pkg", str4);
                        context.startService(intent);
                        g++;
                    }
                }
            }
            this.p = false;
            this.l = this.f.schedule(new f(this), 3, TimeUnit.SECONDS);
        } catch (Throwable th) {
            ALog.e("ElectionServiceImpl", "startElection error", th, new Object[0]);
            this.c = false;
            if (this.k != null) {
                this.k.errorCode = ELE_ERROR_EXCEPTION;
                this.k.errorMsg = th.toString();
            }
        }
    }

    private void e() {
        try {
            if (this.l != null) {
                this.l.cancel(true);
                this.l = null;
            }
            if (this.p) {
                ALog.i("ElectionServiceImpl", "reportcompleted, return", new Object[0]);
                return;
            }
            this.p = true;
            ALog.i("ElectionServiceImpl", "onReportComplete", new Object[0]);
            if (this.e == null) {
                this.e = new HashMap();
            }
            this.e.put(this.b.getPackageName(), Integer.valueOf(Constants.SDK_VERSION_CODE));
            if (this.e.size() == 1) {
                String str = ((String[]) this.e.keySet().toArray(new String[0]))[0];
                if (this.k != null) {
                    this.k.ret = TextUtils.isEmpty(str) ? 0 : 1;
                    AppMonitor.getInstance().commitStat(this.k);
                }
                a(str);
            } else {
                a(this.e);
                this.q = false;
                this.r = this.f.schedule(new g(this), 20, TimeUnit.SECONDS);
            }
            this.c = false;
        } catch (Throwable th) {
            if (this.k != null) {
                this.k.errorCode = ELE_ERROR_EXCEPTION;
                this.k.errorMsg = th.toString();
            }
            ALog.e("ElectionServiceImpl", "onReportComplete", th, new Object[0]);
        } finally {
            this.c = false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:39:0x00b3 A:{Splitter: B:6:0x001b, ExcHandler: all (r0_36 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:67:0x0126 A:{Splitter: B:26:0x008d, ExcHandler: all (r0_17 'th' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x00f6  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x0101  */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:39:0x00b3, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:40:0x00b4, code:
            r9 = r0;
            r0 = r3;
            r3 = r9;
     */
    /* JADX WARNING: Missing block: B:42:?, code:
            com.taobao.accs.utl.ALog.e("ElectionServiceImpl", "handleServerElectionResult", r3, new java.lang.Object[0]);
     */
    /* JADX WARNING: Missing block: B:43:0x00c5, code:
            if (r10.j == null) goto L_0x00d5;
     */
    /* JADX WARNING: Missing block: B:44:0x00c7, code:
            r10.j.errorCode = ELE_ERROR_EXCEPTION;
            r10.j.errorMsg = r3.toString();
     */
    /* JADX WARNING: Missing block: B:45:0x00d5, code:
            a(c());
     */
    /* JADX WARNING: Missing block: B:46:0x00de, code:
            if (r10.j == null) goto L_?;
     */
    /* JADX WARNING: Missing block: B:47:0x00e0, code:
            r10.j.ret = 0;
            anet.channel.appmonitor.AppMonitor.getInstance().commitStat(r10.j);
     */
    /* JADX WARNING: Missing block: B:48:0x00ef, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:49:0x00f0, code:
            r4 = false;
            r9 = r0;
            r0 = r3;
            r3 = r9;
     */
    /* JADX WARNING: Missing block: B:51:0x00f6, code:
            r0 = c();
     */
    /* JADX WARNING: Missing block: B:54:0x0101, code:
            r0 = r10.j;
     */
    /* JADX WARNING: Missing block: B:55:0x0103, code:
            if (r4 == false) goto L_0x0111;
     */
    /* JADX WARNING: Missing block: B:56:0x0105, code:
            r0.ret = r1;
            anet.channel.appmonitor.AppMonitor.getInstance().commitStat(r10.j);
     */
    /* JADX WARNING: Missing block: B:58:0x0111, code:
            r1 = 1;
     */
    /* JADX WARNING: Missing block: B:61:0x0118, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:62:0x0119, code:
            r4 = false;
            r9 = r0;
            r0 = r3;
            r3 = r9;
     */
    /* JADX WARNING: Missing block: B:65:0x0123, code:
            r3 = th;
     */
    /* JADX WARNING: Missing block: B:66:0x0124, code:
            r4 = true;
     */
    /* JADX WARNING: Missing block: B:67:0x0126, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:68:0x0127, code:
            r9 = r0;
            r0 = r3;
            r3 = r9;
     */
    /* JADX WARNING: Missing block: B:75:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:76:?, code:
            return;
     */
    private void a(byte[] r11, int r12) {
        /*
        r10 = this;
        r1 = 0;
        r2 = 1;
        r0 = r10.q;
        if (r0 == 0) goto L_0x0012;
    L_0x0006:
        r0 = "ElectionServiceImpl";
        r2 = "server election handled, return";
        r1 = new java.lang.Object[r1];
        com.taobao.accs.utl.ALog.i(r0, r2, r1);
    L_0x0011:
        return;
    L_0x0012:
        r10.q = r2;
        r3 = 0;
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r12 != r0) goto L_0x001b;
    L_0x0019:
        if (r11 != 0) goto L_0x0067;
    L_0x001b:
        r0 = "ElectionServiceImpl";
        r4 = "handleServerElectionResult fail, start local election";
        r5 = 2;
        r5 = new java.lang.Object[r5];	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r6 = 0;
        r7 = "error";
        r5[r6] = r7;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r6 = 1;
        r7 = java.lang.Integer.valueOf(r12);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r5[r6] = r7;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        com.taobao.accs.utl.ALog.e(r0, r4, r5);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r0 = 201; // 0xc9 float:2.82E-43 double:9.93E-322;
        if (r12 == r0) goto L_0x0131;
    L_0x0038:
        r4 = r2;
    L_0x0039:
        r0 = r10.j;	 Catch:{ Throwable -> 0x00b3, all -> 0x0113 }
        if (r0 == 0) goto L_0x012e;
    L_0x003d:
        r0 = r10.j;	 Catch:{ Throwable -> 0x00b3, all -> 0x0113 }
        r0.errorCode = r12;	 Catch:{ Throwable -> 0x00b3, all -> 0x0113 }
        r0 = r10.j;	 Catch:{ Throwable -> 0x00b3, all -> 0x0113 }
        r5 = "server status error";
        r0.errorMsg = r5;	 Catch:{ Throwable -> 0x00b3, all -> 0x0113 }
        r0 = r3;
    L_0x0049:
        if (r4 == 0) goto L_0x004f;
    L_0x004b:
        r0 = r10.c();
    L_0x004f:
        r10.a(r0);
        r0 = r10.j;
        if (r0 == 0) goto L_0x0011;
    L_0x0056:
        r3 = r10.j;
        if (r4 == 0) goto L_0x00b1;
    L_0x005a:
        r0 = r1;
    L_0x005b:
        r3.ret = r0;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r10.j;
        r0.commitStat(r1);
        goto L_0x0011;
    L_0x0067:
        r0 = new java.lang.String;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r0.<init>(r11);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r4 = "ElectionServiceImpl";
        r5 = "handleServerElectionResult";
        r6 = 2;
        r6 = new java.lang.Object[r6];	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r7 = 0;
        r8 = "json";
        r6[r7] = r8;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r7 = 1;
        r6[r7] = r0;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        com.taobao.accs.utl.ALog.i(r4, r5, r6);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r5 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r5.<init>(r0);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r0 = "host";
        r3 = r5.getString(r0);	 Catch:{ Throwable -> 0x00b3, all -> 0x00ef }
        r0 = android.text.TextUtils.isEmpty(r3);	 Catch:{ Throwable -> 0x0126, all -> 0x0118 }
        if (r0 == 0) goto L_0x012b;
    L_0x0093:
        r4 = r2;
    L_0x0094:
        r0 = "blacklist";
        r0 = r5.getJSONArray(r0);	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        if (r0 == 0) goto L_0x00aa;
    L_0x009d:
        r0 = r0.length();	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        if (r0 <= 0) goto L_0x00aa;
    L_0x00a3:
        r0 = r10.b;	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        com.taobao.accs.a.a.a(r0, r11);	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        r0 = r3;
        goto L_0x0049;
    L_0x00aa:
        r0 = r10.b;	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        com.taobao.accs.a.a.c(r0);	 Catch:{ Throwable -> 0x0126, all -> 0x011e }
        r0 = r3;
        goto L_0x0049;
    L_0x00b1:
        r0 = r2;
        goto L_0x005b;
    L_0x00b3:
        r0 = move-exception;
        r9 = r0;
        r0 = r3;
        r3 = r9;
    L_0x00b7:
        r4 = "ElectionServiceImpl";
        r5 = "handleServerElectionResult";
        r6 = 0;
        r6 = new java.lang.Object[r6];	 Catch:{ all -> 0x0123 }
        com.taobao.accs.utl.ALog.e(r4, r5, r3, r6);	 Catch:{ all -> 0x0123 }
        r4 = r10.j;	 Catch:{ all -> 0x0123 }
        if (r4 == 0) goto L_0x00d5;
    L_0x00c7:
        r4 = r10.j;	 Catch:{ all -> 0x0123 }
        r5 = -901; // 0xfffffffffffffc7b float:NaN double:NaN;
        r4.errorCode = r5;	 Catch:{ all -> 0x0123 }
        r4 = r10.j;	 Catch:{ all -> 0x0123 }
        r3 = r3.toString();	 Catch:{ all -> 0x0123 }
        r4.errorMsg = r3;	 Catch:{ all -> 0x0123 }
    L_0x00d5:
        r0 = r10.c();
        r10.a(r0);
        r0 = r10.j;
        if (r0 == 0) goto L_0x0011;
    L_0x00e0:
        r0 = r10.j;
        r0.ret = r1;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r10.j;
        r0.commitStat(r1);
        goto L_0x0011;
    L_0x00ef:
        r0 = move-exception;
        r4 = r1;
        r9 = r0;
        r0 = r3;
        r3 = r9;
    L_0x00f4:
        if (r4 == 0) goto L_0x00fa;
    L_0x00f6:
        r0 = r10.c();
    L_0x00fa:
        r10.a(r0);
        r0 = r10.j;
        if (r0 == 0) goto L_0x0110;
    L_0x0101:
        r0 = r10.j;
        if (r4 == 0) goto L_0x0111;
    L_0x0105:
        r0.ret = r1;
        r0 = anet.channel.appmonitor.AppMonitor.getInstance();
        r1 = r10.j;
        r0.commitStat(r1);
    L_0x0110:
        throw r3;
    L_0x0111:
        r1 = r2;
        goto L_0x0105;
    L_0x0113:
        r0 = move-exception;
        r9 = r0;
        r0 = r3;
        r3 = r9;
        goto L_0x00f4;
    L_0x0118:
        r0 = move-exception;
        r4 = r1;
        r9 = r0;
        r0 = r3;
        r3 = r9;
        goto L_0x00f4;
    L_0x011e:
        r0 = move-exception;
        r9 = r0;
        r0 = r3;
        r3 = r9;
        goto L_0x00f4;
    L_0x0123:
        r3 = move-exception;
        r4 = r2;
        goto L_0x00f4;
    L_0x0126:
        r0 = move-exception;
        r9 = r0;
        r0 = r3;
        r3 = r9;
        goto L_0x00b7;
    L_0x012b:
        r4 = r1;
        goto L_0x0094;
    L_0x012e:
        r0 = r3;
        goto L_0x0049;
    L_0x0131:
        r4 = r1;
        goto L_0x0039;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.accs.internal.b.a(byte[], int):void");
    }

    private void a(String str) {
        try {
            if (this.o == null) {
                this.o = com.taobao.accs.a.a.a(this.b);
            }
            this.o.a = str;
            a aVar = this.o;
            aVar.b++;
            com.taobao.accs.a.a.a(this.b, this.o);
            ALog.i("ElectionServiceImpl", "handleResult notify result", "host", str, "packMap", this.e);
            for (String str2 : this.e.keySet()) {
                if (!TextUtils.isEmpty(str2)) {
                    Intent intent = new Intent(com.taobao.accs.a.a.c());
                    intent.setPackage(str2);
                    intent.putExtra("operate", Operate.RESULT_ELECTION);
                    intent.putExtra("sudoPack", str);
                    intent.setClassName(str2, com.taobao.accs.utl.a.channelService);
                    this.b.startService(intent);
                }
            }
        } catch (Throwable th) {
            ALog.e("ElectionServiceImpl", "handleResult", th, new Object[0]);
        }
    }

    public void a(boolean z) {
        ALog.e("ElectionServiceImpl", "shouldStopSelf, kill:" + z, new Object[0]);
        if (this.i != null) {
            this.i.stopSelf();
        }
        if (z) {
            Process.killProcess(Process.myPid());
        }
    }
}
