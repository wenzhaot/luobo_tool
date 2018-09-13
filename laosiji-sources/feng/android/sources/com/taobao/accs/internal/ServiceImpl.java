package com.taobao.accs.internal;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.taobao.accs.ACCSManager.AccsRequest;
import com.taobao.accs.b.a;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.common.Constants;
import com.taobao.accs.data.Message;
import com.taobao.accs.data.Message.ReqType;
import com.taobao.accs.data.d;
import com.taobao.accs.net.r;
import com.taobao.accs.ut.statistics.c;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.b;
import com.taobao.accs.utl.f;
import com.taobao.accs.utl.i;
import com.tencent.tauth.AuthActivity;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.android.agoo.common.Config;
import org.android.agoo.service.IMessageService.Stub;
import org.json.JSONArray;
import org.json.JSONObject;

/* compiled from: Taobao */
public class ServiceImpl extends b {
    private static a f;
    private Service b = null;
    private Context c;
    private long d;
    private long e;
    private String g = "unknown";
    private ScheduledThreadPoolExecutor h;
    private final Stub i = new Stub() {
        public boolean ping() throws RemoteException {
            return true;
        }

        public void probe() throws RemoteException {
            ALog.d("ServiceImpl", "ReceiverImpl probeTaoBao begin......messageServiceBinder [probe]", new Object[0]);
            ServiceImpl.this.h.execute(new i(this));
        }
    };

    static /* synthetic */ void d() {
    }

    public ServiceImpl(Service service) {
        super(service);
        this.b = service;
        this.c = StubApp.getOrigApplicationContext(service.getApplicationContext());
    }

    public void onCreate() {
        super.onCreate();
        GlobalClientInfo.getInstance(this.c);
        com.taobao.accs.client.a.g.incrementAndGet();
        this.d = System.currentTimeMillis();
        this.e = this.d;
        this.g = UtilityImpl.getNetworkTypeExt(this.c);
        this.h = new ScheduledThreadPoolExecutor(2);
        if (Config.f(this.c)) {
            a.c(this.c);
            f = a.a(this.c, 600, false);
            if (f != null) {
                f.a();
            }
        }
        try {
            this.h.execute(new h(this));
        } catch (Throwable th) {
            ALog.e("ServiceImpl", "serviceImpl init task fail:" + th.toString(), new Object[0]);
        }
        if (ALog.isPrintLog(Level.I)) {
            ALog.i("ServiceImpl", "ServiceImpl onCreate", "ClassLoader", ServiceImpl.class.getClassLoader().toString(), "sdkv", Integer.valueOf(Constants.SDK_VERSION_CODE), "procStart", Integer.valueOf(com.taobao.accs.client.a.g.intValue()));
        }
    }

    public int a(Intent intent) {
        Throwable th;
        int i = 2;
        if (UtilityImpl.getServiceEnabled(this.c)) {
            if (ALog.isPrintLog(Level.I)) {
                ALog.i("ServiceImpl", "onHostStartCommand:" + intent, new Object[0]);
            }
            int c;
            try {
                if (ALog.isPrintLog(Level.D) && intent != null) {
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        for (String str : extras.keySet()) {
                            ALog.d("ServiceImpl", "key", str, " value", extras.get(str));
                        }
                    }
                }
                c = f.c();
                if (c > 3) {
                    try {
                        ALog.e("ServiceImpl", "load SO fail 4 times, don't auto restart", new Object[0]);
                        b.a("accs", BaseMonitor.COUNT_POINT_SOFAIL, UtilityImpl.int2String(c), 0.0d);
                    } catch (Throwable th2) {
                        th = th2;
                        c = i;
                        try {
                            ALog.e("ServiceImpl", "onHostStartCommand", th, new Object[0]);
                            th.printStackTrace();
                            return c;
                        } finally {
                            com.taobao.accs.client.a.g.incrementAndGet();
                        }
                    }
                } else {
                    i = 1;
                }
                String str2 = intent == null ? null : intent.getAction();
                if (!TextUtils.isEmpty(str2) && "org.agoo.android.intent.action.PING_V4".equals(str2)) {
                    String stringExtra = intent.getStringExtra("source");
                    ALog.i("ServiceImpl", "org.agoo.android.intent.action.PING_V4,start channel by brothers", "serviceStart", Integer.valueOf(com.taobao.accs.client.a.g.intValue()), "source" + stringExtra);
                    b.a("accs", "startChannel", stringExtra, 0.0d);
                    if (com.taobao.accs.client.a.c()) {
                        b.a("accs", "createChannel", stringExtra, 0.0d);
                    }
                }
                if (TextUtils.isEmpty(str2)) {
                    e();
                    a(false, false);
                    com.taobao.accs.client.a.g.incrementAndGet();
                    return i;
                }
                e();
                if (!TextUtils.equals(str2, "android.intent.action.PACKAGE_REMOVED")) {
                    if (TextUtils.equals(str2, "android.net.conn.CONNECTIVITY_CHANGE")) {
                        String networkTypeExt = UtilityImpl.getNetworkTypeExt(this.c);
                        boolean isNetworkConnected = UtilityImpl.isNetworkConnected(this.c);
                        ALog.i("ServiceImpl", "network change:" + this.g + " to " + networkTypeExt, new Object[0]);
                        if (isNetworkConnected) {
                            this.g = networkTypeExt;
                            f();
                            a(true, false);
                            UTMini.getInstance().commitEvent(66001, "CONNECTIVITY_CHANGE", networkTypeExt, UtilityImpl.getProxy(), PushConstants.PUSH_TYPE_NOTIFY);
                        }
                        if (networkTypeExt.equals("unknown")) {
                            f();
                            this.g = networkTypeExt;
                        }
                    } else if (TextUtils.equals(str2, "android.intent.action.BOOT_COMPLETED")) {
                        a(true, false);
                    } else if (TextUtils.equals(str2, "android.intent.action.USER_PRESENT")) {
                        ALog.d("ServiceImpl", "action android.intent.action.USER_PRESENT", new Object[0]);
                        a(true, false);
                    } else if (str2.equals(Constants.ACTION_COMMAND)) {
                        b(intent);
                    } else if (str2.equals(Constants.ACTION_START_FROM_AGOO)) {
                        ALog.i("ServiceImpl", "ACTION_START_FROM_AGOO", new Object[0]);
                    }
                }
                com.taobao.accs.client.a.g.incrementAndGet();
                c = i;
                return c;
            } catch (Throwable th22) {
                Throwable th3 = th22;
                c = 1;
                th = th3;
            }
        } else {
            ALog.e("ServiceImpl", "service disabled!", new Object[0]);
            b(false);
            return 2;
        }
    }

    private void b(Intent intent) {
        int intExtra = intent.getIntExtra("command", -1);
        ALog.i("ServiceImpl", "command:" + intExtra, new Object[0]);
        String stringExtra = intent.getStringExtra(Constants.KEY_PACKAGE_NAME);
        String stringExtra2 = intent.getStringExtra(Constants.KEY_SERVICE_ID);
        String stringExtra3 = intent.getStringExtra(Constants.KEY_USER_ID);
        String stringExtra4 = intent.getStringExtra(Constants.KEY_APP_KEY);
        String stringExtra5 = intent.getStringExtra(Constants.KEY_CONFIG_TAG);
        String stringExtra6 = intent.getStringExtra(Constants.KEY_TTID);
        intent.getStringExtra("sid");
        intent.getStringExtra(Constants.KEY_ANTI_BRUSH_COOKIE);
        if (intExtra == Constants.COMMAND_PING) {
            a(Message.a(true, 0), true);
            g();
        }
        if (intExtra > 0 && !TextUtils.isEmpty(stringExtra)) {
            com.taobao.accs.net.b a = b.a(this.c, stringExtra5, true, intExtra);
            if (a != null) {
                a.a();
                Message message = null;
                Message a2;
                if (intExtra == 1) {
                    if (stringExtra.equals(this.c.getPackageName())) {
                        String stringExtra7 = intent.getStringExtra("app_sercet");
                        a2 = Message.a(this.c, stringExtra5, stringExtra4, stringExtra7, stringExtra, stringExtra6, intent.getStringExtra(Constants.KEY_APP_VERSION));
                        a.a = stringExtra6;
                        UtilityImpl.saveAppKey(this.c, stringExtra4, stringExtra7);
                        if (a.j().c(stringExtra)) {
                            if (!intent.getBooleanExtra(Constants.KEY_FOUCE_BIND, false)) {
                                ALog.i("ServiceImpl", stringExtra + " isAppBinded", new Object[0]);
                                a.b(a2, 200);
                                return;
                            }
                        }
                        message = a2;
                    } else {
                        ALog.e("ServiceImpl", "handleCommand bindapp pkg error", new Object[0]);
                        return;
                    }
                } else if (intExtra == 2) {
                    ALog.e("ServiceImpl", "onHostStartCommand COMMAND_UNBIND_APP", new Object[0]);
                    if (a.j().d(stringExtra)) {
                        a2 = Message.a(a, stringExtra);
                        ALog.i("ServiceImpl", stringExtra + " isAppUnbinded", new Object[0]);
                        a.b(a2, 200);
                        return;
                    }
                } else if (intExtra == 5) {
                    message = Message.a(stringExtra, stringExtra2);
                } else if (intExtra == 6) {
                    message = Message.b(stringExtra, stringExtra2);
                } else if (intExtra == 3) {
                    message = Message.c(stringExtra, stringExtra3);
                    if (a.j().b(stringExtra, stringExtra3)) {
                        if (!intent.getBooleanExtra(Constants.KEY_FOUCE_BIND, false)) {
                            ALog.i("ServiceImpl", stringExtra + "/" + stringExtra3 + " isUserBinded", new Object[0]);
                            if (message != null) {
                                a.b(message, 200);
                                return;
                            }
                            return;
                        }
                    }
                } else if (intExtra == 4) {
                    message = Message.a(stringExtra);
                } else if (intExtra == 100) {
                    ReqType reqType;
                    byte[] byteArrayExtra = intent.getByteArrayExtra("data");
                    String stringExtra8 = intent.getStringExtra(Constants.KEY_DATA_ID);
                    String stringExtra9 = intent.getStringExtra(Constants.KEY_TARGET);
                    String stringExtra10 = intent.getStringExtra(Constants.KEY_BUSINESSID);
                    String stringExtra11 = intent.getStringExtra(Constants.KEY_EXT_TAG);
                    try {
                        reqType = (ReqType) intent.getSerializableExtra(Constants.KEY_SEND_TYPE);
                    } catch (Exception e) {
                        reqType = null;
                    }
                    if (byteArrayExtra != null) {
                        URL url;
                        try {
                            url = new URL("https://" + ((r) a).m());
                        } catch (Exception e2) {
                            url = null;
                        }
                        AccsRequest accsRequest = new AccsRequest(stringExtra3, stringExtra2, byteArrayExtra, stringExtra8, stringExtra9, url, stringExtra10);
                        accsRequest.setTag(stringExtra11);
                        if (reqType == null) {
                            a2 = Message.a(a, this.c, stringExtra, accsRequest, false);
                        } else if (reqType == ReqType.REQ) {
                            a2 = Message.b(a, this.c, stringExtra, accsRequest, false);
                        }
                        message = a2;
                    }
                    a2 = null;
                    message = a2;
                } else if (intExtra == 105) {
                    message = Message.a(stringExtra, (Map) intent.getSerializableExtra(Constants.KEY_ELECTION_PACKS));
                } else if (intExtra == 106) {
                    intent.setAction(Constants.ACTION_RECEIVE);
                    intent.putExtra("command", -1);
                    d.a(this.c, intent);
                    return;
                }
                if (message != null) {
                    ALog.d("ServiceImpl", "try send message", new Object[0]);
                    if (message.e() != null) {
                        message.e().onSend();
                    }
                    a.b(message, true);
                    return;
                }
                ALog.e("ServiceImpl", "message is null", new Object[0]);
                a.b(Message.a(stringExtra, intExtra), -2);
                return;
            }
            ALog.e("ServiceImpl", "no connection", Constants.KEY_CONFIG_TAG, stringExtra5, "command", Integer.valueOf(intExtra));
        }
    }

    public IBinder onBind(Intent intent) {
        CharSequence action = intent.getAction();
        ALog.d("ServiceImpl", "accs probeTaoBao begin......action=" + action, new Object[0]);
        if (TextUtils.isEmpty(action) || !TextUtils.equals(action, "org.agoo.android.intent.action.PING_V4")) {
            return null;
        }
        UTMini.getInstance().commitEvent(66001, "probeChannelService", UtilityImpl.getDeviceId(this.c), intent.getStringExtra("source"));
        return this.i;
    }

    public boolean onUnbind(Intent intent) {
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        ALog.e("ServiceImpl", "Service onDestroy", new Object[0]);
        UtilityImpl.setServiceTime(this.c, Constants.SP_KEY_SERVICE_END, System.currentTimeMillis());
        this.b = null;
        this.c = null;
        h();
        Process.killProcess(Process.myPid());
    }

    private final void a(Context context) {
        try {
            Object d = i.d();
            if (!TextUtils.isEmpty(d)) {
                ALog.i("ServiceImpl", "start pull up", new Object[0]);
                JSONArray jSONArray = new JSONArray(d);
                for (int i = 0; i < jSONArray.length(); i++) {
                    boolean z;
                    JSONObject jSONObject = jSONArray.getJSONObject(i).getJSONObject(PushConstants.EXTRA_APPLICATION_PENDING_INTENT + i);
                    String string = jSONObject.getString(AuthActivity.ACTION_KEY);
                    String string2 = jSONObject.getString("pack");
                    String string3 = jSONObject.getString("service");
                    boolean z2 = jSONObject.getBoolean("enabled");
                    int i2 = jSONObject.getInt("probability");
                    if (!z2 || ((double) i2) < Math.random() * 100.0d) {
                        z = false;
                    } else {
                        z = true;
                    }
                    ALog.i("ServiceImpl", "pull up", AuthActivity.ACTION_KEY, string, "pack", string2, "service", string3, "need pull", Boolean.valueOf(z));
                    if (z && UtilityImpl.packageExist(context, string2)) {
                        Intent intent = new Intent();
                        intent.setAction(string);
                        intent.setClassName(string2, string3);
                        intent.putExtra(Constants.KEY_PACKAGE_NAME, context.getPackageName());
                        intent.setPackage(string2);
                        context.startService(intent);
                        UTMini.getInstance().commitEvent(66001, "pingApp", com.taobao.accs.utl.a.b(this.c), string2);
                    }
                }
            }
        } catch (Throwable th) {
            ALog.e("ServiceImpl", "onPingIpp", th, new Object[0]);
        }
    }

    private void b(boolean z) {
        ALog.e("ServiceImpl", "shouldStopSelf, kill:" + z, new Object[0]);
        if (this.b != null) {
            this.b.stopSelf();
        }
        if (z) {
            Process.killProcess(Process.myPid());
        }
    }

    private synchronized void e() {
        if (a == null || a.size() == 0) {
            ALog.w("ServiceImpl", "tryConnect no connections", new Object[0]);
        } else {
            for (Entry value : a.entrySet()) {
                com.taobao.accs.net.b bVar = (com.taobao.accs.net.b) value.getValue();
                if (bVar == null) {
                    ALog.e("ServiceImpl", "tryConnect connection null", "appkey", bVar.i());
                    break;
                }
                if (bVar.k() && TextUtils.isEmpty(bVar.i.getAppSecret())) {
                    ALog.e("ServiceImpl", "tryConnect secret is null", new Object[0]);
                } else {
                    bVar.a();
                }
                ALog.i("ServiceImpl", "tryConnect", "appkey", bVar.i(), Constants.KEY_CONFIG_TAG, value.getKey());
            }
        }
    }

    private void a(Message message, boolean z) {
        if (a != null && a.size() != 0) {
            for (Entry value : a.entrySet()) {
                ((com.taobao.accs.net.b) value.getValue()).b(message, z);
            }
        }
    }

    private void a(boolean z, boolean z2) {
        if (a != null && a.size() != 0) {
            for (Entry value : a.entrySet()) {
                ((com.taobao.accs.net.b) value.getValue()).a(z, z2);
                ALog.i("ServiceImpl", "ping connection", "appkey", r0.i());
            }
        }
    }

    private void f() {
        if (a != null && a.size() != 0) {
            for (Entry value : a.entrySet()) {
                ((com.taobao.accs.net.b) value.getValue()).b();
            }
        }
    }

    private void g() {
        if (a != null && a.size() != 0) {
            for (Entry value : a.entrySet()) {
                c c = ((com.taobao.accs.net.b) value.getValue()).c();
                if (c != null) {
                    c.h = this.d;
                    c.commitUT();
                }
            }
        }
    }

    private void h() {
        if (a != null && a.size() != 0) {
            for (Entry value : a.entrySet()) {
                ((com.taobao.accs.net.b) value.getValue()).e();
            }
        }
    }

    public void c() {
        ALog.i("ServiceImpl", "startConnect", new Object[0]);
        try {
            e();
            a(false, false);
        } catch (Throwable th) {
            ALog.e("ServiceImpl", "tryConnect is error,e=" + th, new Object[0]);
        }
    }

    public void a() {
        c();
    }

    public static boolean a(String str) {
        com.taobao.accs.net.b bVar = (com.taobao.accs.net.b) a.elements().nextElement();
        return bVar == null ? false : bVar.j().c(str);
    }
}
