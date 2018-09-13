package com.taobao.accs.net;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import anet.channel.Config;
import anet.channel.SessionCenter;
import anet.channel.entity.ENV;
import anet.channel.strategy.ConnProtocol;
import anet.channel.strategy.StrategyTemplate;
import com.feng.car.utils.FengConstant;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import com.stub.StubApp;
import com.taobao.accs.ACCSManager;
import com.taobao.accs.AccsClientConfig;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.taobao.accs.common.ThreadPoolExecutorFactory;
import com.taobao.accs.data.Message;
import com.taobao.accs.data.a;
import com.taobao.accs.ut.statistics.c;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.ALog.Level;
import com.taobao.accs.utl.BaseMonitor;
import com.taobao.accs.utl.UtilityImpl;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* compiled from: Taobao */
public abstract class b {
    public static final int ACCS_RECEIVE_TIMEOUT = 40000;
    public static final int INAPP = 1;
    public static final int SERVICE = 0;
    public String a;
    public String b = "";
    protected int c;
    protected Context d;
    protected a e;
    protected int f = 0;
    protected volatile boolean g = false;
    public com.taobao.accs.client.b h;
    public AccsClientConfig i;
    protected String j;
    protected String k = null;
    protected LinkedHashMap<Integer, Message> l = new BaseConnection$1(this);
    public String m;
    private long n = 0;
    private Runnable o;
    private ScheduledFuture<?> p;

    public abstract void a();

    protected abstract void a(Message message, boolean z);

    protected abstract void a(String str, String str2);

    public abstract void a(boolean z, boolean z2);

    public abstract boolean a(String str);

    public abstract void b();

    public abstract c c();

    protected abstract String d();

    protected b(Context context, int i, String str) {
        this.c = i;
        this.d = StubApp.getOrigApplicationContext(context.getApplicationContext());
        AccsClientConfig configByTag = AccsClientConfig.getConfigByTag(str);
        if (configByTag == null) {
            ALog.e(d(), "BaseConnection config null!!", new Object[0]);
            try {
                configByTag = new Builder().setAppKey(ACCSManager.getDefaultAppkey(context)).setTag(str).build();
            } catch (Throwable e) {
                ALog.e(d(), "BaseConnection build config", e, new Object[0]);
            }
        }
        this.m = configByTag.getTag();
        this.b = configByTag.getAppKey();
        this.i = configByTag;
        this.e = new a(context, this);
        this.e.b = this.c;
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new c(this), 5000, TimeUnit.MILLISECONDS);
    }

    protected String a(int i) {
        switch (i) {
            case 1:
                return "CONNECTED";
            case 2:
                return "CONNECTING";
            case 3:
                return "DISCONNECTED";
            case 4:
                return "DISCONNECTING";
            default:
                return "DISCONNECTED";
        }
    }

    public void e() {
    }

    public void b(Message message, boolean z) {
        if (message.c || UtilityImpl.isNetworkConnected(this.d)) {
            long a;
            if (message.a() != 2) {
                a = this.e.d.a(message.F, message.T);
            } else {
                a = 0;
            }
            if (a == -1) {
                ALog.e(d(), "servier limit high. dataId:" + message.q, new Object[0]);
                this.e.a(message, (int) ErrorCode.SERVIER_HIGH_LIMIT);
                return;
            } else if (a == -1000) {
                ALog.e(d(), "servier limit high for brush. dataId:" + message.q, new Object[0]);
                this.e.a(message, (int) ErrorCode.SERVIER_HIGH_LIMIT_BRUSH);
                return;
            } else {
                if (a > 0) {
                    if (System.currentTimeMillis() > this.n) {
                        message.O = a;
                    } else {
                        message.O = (a + this.n) - System.currentTimeMillis();
                    }
                    this.n = System.currentTimeMillis() + message.O;
                    ALog.e(d(), "send message, " + com.taobao.accs.data.Message.b.b(message.a()) + " delay:" + message.O + " dataId:" + message.q, new Object[0]);
                } else if ("accs".equals(message.F)) {
                    ALog.e(d(), "send message, " + com.taobao.accs.data.Message.b.b(message.a()) + " delay:" + message.O + " dataId:" + message.q, new Object[0]);
                } else if (ALog.isPrintLog(Level.D)) {
                    ALog.d(d(), "send message, " + com.taobao.accs.data.Message.b.b(message.a()) + " delay:" + message.O + " dataId:" + message.q, new Object[0]);
                }
                try {
                    if (TextUtils.isEmpty(this.j)) {
                        this.j = UtilityImpl.getDeviceId(this.d);
                    }
                    if (message.g()) {
                        this.e.a(message, -9);
                        return;
                    } else {
                        a(message, z);
                        return;
                    }
                } catch (RejectedExecutionException e) {
                    this.e.a(message, (int) ErrorCode.MESSAGE_QUEUE_FULL);
                    ALog.e(d(), "msg queue full", FengConstant.SIZE, Integer.valueOf(ThreadPoolExecutorFactory.getSendScheduledExecutor().getQueue().size()));
                    return;
                }
            }
        }
        ALog.e(d(), "no network:" + message.q, new Object[0]);
        this.e.a(message, -13);
    }

    protected void a(String str, long j) {
        ThreadPoolExecutorFactory.getScheduledExecutor().schedule(new d(this, str), j, TimeUnit.MILLISECONDS);
    }

    protected boolean a(Message message, int i) {
        Throwable th;
        boolean z = true;
        try {
            if (message.P > 3) {
                return false;
            }
            message.P++;
            message.O = (long) i;
            ALog.e(d(), "reSend dataid:" + message.q + " retryTimes:" + message.P, new Object[0]);
            b(message, true);
            try {
                if (message.e() == null) {
                    return true;
                }
                message.e().take_date = 0;
                message.e().to_tnet_date = 0;
                message.e().retry_times = message.P;
                if (message.P != 1) {
                    return true;
                }
                com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, FileDownloadModel.TOTAL, 0.0d);
                return true;
            } catch (Throwable th2) {
                th = th2;
                this.e.a(message, -8);
                ALog.e(d(), "reSend error", th, new Object[0]);
                return z;
            }
        } catch (Throwable th3) {
            th = th3;
            z = false;
            this.e.a(message, -8);
            ALog.e(d(), "reSend error", th, new Object[0]);
            return z;
        }
    }

    protected void b(int i) {
        if (i < 0) {
            ALog.e(d(), "reSendAck", Constants.KEY_DATA_ID, Integer.valueOf(i));
            Message message = (Message) this.l.get(Integer.valueOf(i));
            if (message != null) {
                a(message, 5000);
                com.taobao.accs.utl.b.a("accs", BaseMonitor.COUNT_POINT_RESEND, BaseMonitor.COUNT_ACK, 0.0d);
            }
        }
    }

    protected void f() {
        if (this.o == null) {
            this.o = new e(this);
        }
        g();
        this.p = ThreadPoolExecutorFactory.getScheduledExecutor().schedule(this.o, 40000, TimeUnit.MILLISECONDS);
    }

    protected void g() {
        if (this.p != null) {
            this.p.cancel(true);
        }
    }

    public String b(String str) {
        String inappHost = this.i.getInappHost();
        String str2 = "https://" + (TextUtils.isEmpty(str) ? "" : str) + inappHost;
        try {
            StringBuilder stringBuilder = new StringBuilder();
            StringBuilder append = stringBuilder.append("https://");
            if (TextUtils.isEmpty(str)) {
                str = "";
            }
            append.append(str);
            stringBuilder.append(inappHost);
            return stringBuilder.toString();
        } catch (Throwable th) {
            ALog.e("InAppConnection", "getHost", th, new Object[0]);
            return str2;
        }
    }

    protected boolean h() {
        return true;
    }

    protected void a(Context context) {
        try {
            ENV env = ENV.ONLINE;
            if (AccsClientConfig.mEnv == 2) {
                env = ENV.TEST;
                SessionCenter.switchEnvironment(env);
            } else if (AccsClientConfig.mEnv == 1) {
                env = ENV.PREPARE;
                SessionCenter.switchEnvironment(env);
            }
            SessionCenter.init(context, new Config.Builder().setAppkey(this.b).setAppSecret(this.i.getAppSecret()).setAuthCode(this.i.getAuthCode()).setEnv(env).setTag(this.i.getAppKey()).build());
            String str = "acs";
            if (this.i.getInappPubKey() == 10 || this.i.getInappPubKey() == 11) {
                str = "open";
            }
            StrategyTemplate.getInstance().registerConnProtocol(this.i.getInappHost(), ConnProtocol.valueOf("http2", "0rtt", str, false));
        } catch (Throwable th) {
            ALog.e(d(), "initAwcn", th, new Object[0]);
        }
    }

    public void b(Message message, int i) {
        this.e.a(message, i);
    }

    public String i() {
        return this.b;
    }

    public com.taobao.accs.client.b j() {
        if (this.h == null) {
            this.h = new com.taobao.accs.client.b(this.d, this.m);
        }
        return this.h;
    }

    public void b(Context context) {
        try {
            ThreadPoolExecutorFactory.schedule(new f(this, context), 10000, TimeUnit.MILLISECONDS);
        } catch (Throwable th) {
            ALog.w(d(), "startChannelService", th, new Object[0]);
        }
    }

    protected String c(String str) {
        Object obj;
        String deviceId = UtilityImpl.getDeviceId(this.d);
        try {
            deviceId = URLEncoder.encode(deviceId);
        } catch (Throwable th) {
            ALog.e(d(), "encode", th, new Object[0]);
        }
        String appsign = UtilityImpl.getAppsign(this.d, i(), this.i.getAppSecret(), UtilityImpl.getDeviceId(this.d), this.m);
        StringBuilder stringBuilder = new StringBuilder(AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
        stringBuilder.append(str).append("auth?1=").append(deviceId).append("&2=").append(appsign).append("&3=").append(i());
        if (this.k != null) {
            stringBuilder.append("&4=").append(this.k);
        }
        StringBuilder append = stringBuilder.append("&5=").append(this.c).append("&6=").append(UtilityImpl.getNetworkType(this.d)).append("&7=").append(UtilityImpl.getOperator(this.d)).append("&8=");
        if (this.c == 1) {
            obj = "1.1.2";
        } else {
            obj = Integer.valueOf(Constants.SDK_VERSION_CODE);
        }
        append.append(obj).append("&9=").append(System.currentTimeMillis()).append("&10=").append(1).append("&11=").append(VERSION.SDK_INT).append("&12=").append(this.d.getPackageName()).append("&13=").append(UtilityImpl.getAppVersion(this.d)).append("&14=").append(this.a).append("&15=").append(Build.MODEL).append("&16=").append(Build.BRAND).append("&17=").append(Constants.SDK_VERSION_CODE);
        stringBuilder.append("&19=").append(k() ? 0 : 1);
        return stringBuilder.toString();
    }

    public boolean k() {
        return 2 == this.i.getSecurity();
    }
}
