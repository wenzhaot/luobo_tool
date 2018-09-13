package com.umeng.message;

import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.stub.StubApp;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig.Builder;
import com.taobao.accs.AccsException;
import com.taobao.accs.client.GlobalConfig;
import com.taobao.accs.utl.ALog;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.IRegister;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.UTrack.ICallBack;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.d;
import com.umeng.message.proguard.h;
import com.umeng.message.proguard.k;
import com.umeng.message.tag.TagManager;
import com.umeng.message.util.b;
import java.util.Random;
import org.android.spdy.SpdyAgent;

public class PushAgent {
    public static boolean DEBUG = false;
    private static PushAgent a;
    private static boolean d = false;
    private static final String e = PushAgent.class.getName();
    private TagManager b;
    private Context c;
    private UHandler f;
    private UHandler g;
    private UHandler h;
    private boolean i = false;
    private boolean j = true;
    private Handler k;
    private IUmengRegisterCallback l;
    private IUmengCallback m;

    private PushAgent(Context context) {
        try {
            this.c = context;
            this.b = TagManager.getInstance(context);
            this.f = new UmengMessageHandler();
            this.g = new UmengAdHandler();
            this.h = new UmengNotificationClickHandler();
            b.a(context);
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "PushAgent初始化失败", e.getMessage());
        }
        this.k = new Handler(context.getMainLooper()) {
            public void handleMessage(Message message) {
                super.handleMessage(message);
            }
        };
    }

    private PushAgent() {
    }

    public static synchronized PushAgent getInstance(Context context) {
        PushAgent pushAgent;
        synchronized (PushAgent.class) {
            if (a == null) {
                a = new PushAgent(StubApp.getOrigApplicationContext(context.getApplicationContext()));
            }
            pushAgent = a;
        }
        return pushAgent;
    }

    private void b() {
        UMLog uMLog;
        try {
            if (VERSION.SDK_INT < 14) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(e, 0, "U-Push最低支持的系统版本为Android 4.0");
            } else if (h.a(this.c, this.k)) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(e, 2, "AndroidManifest配置正确");
                if (TextUtils.isEmpty(getMessageAppkey()) || TextUtils.isEmpty(getMessageSecret())) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(e, 0, "Appkey和Secret key均不能为空");
                    return;
                }
                h.a(this.c, UmengMessageCallbackHandlerService.class);
                if (DEBUG) {
                    h.b(this.c, this.k);
                }
                ALog.setUseTlog(false);
                anet.channel.util.ALog.setUseTlog(false);
                ACCSClient.setEnvironment(this.c, 0);
                ACCSClient.init(this.c, new Builder().setAppKey("umeng:" + getMessageAppkey()).setAppSecret(getMessageSecret()).setInappHost("umengacs.m.taobao.com").setInappPubKey(11).setChannelHost("umengjmacs.m.taobao.com").setChannelPubKey(11).setKeepAlive(e()).setAutoUnit(false).build());
                DispatchConstants.setAmdcServerDomain(new String[]{"amdcopen.m.taobao.com", "amdc.wapa.taobao.com", "amdc.taobao.net"});
                r0 = new String[3][];
                r0[0] = new String[]{"106.11.61.135", "106.11.61.137"};
                r0[1] = null;
                r0[2] = null;
                DispatchConstants.setAmdcServerFixIp(r0);
                if (UmengMessageDeviceConfig.isMiui8()) {
                    TaobaoRegister.setAgooMsgReceiveService("com.umeng.message.XiaomiIntentService");
                } else {
                    TaobaoRegister.setAgooMsgReceiveService("com.umeng.message.UmengIntentService");
                }
                d.a(new Runnable() {
                    public void run() {
                        String str = "umeng:" + PushAgent.this.getMessageAppkey();
                        String messageSecret = PushAgent.this.getMessageSecret();
                        UMLog uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(PushAgent.e, 2, "appkey:" + str + ",secret:" + messageSecret);
                        try {
                            TaobaoRegister.register(PushAgent.this.c, str, messageSecret, "android@umeng", new IRegister() {
                                public void onSuccess(String str) {
                                    UMLog uMLog = UMConfigure.umDebugLog;
                                    UMLog.mutlInfo(PushAgent.e, 2, "注册成功:" + str);
                                    PushAgent.this.a(str);
                                }

                                public void onFailure(String str, String str2) {
                                    UMLog uMLog = UMConfigure.umDebugLog;
                                    UMLog.mutlInfo(PushAgent.e, 0, "注册失败-->s:" + str + ",s1:" + str2);
                                    PushAgent.this.a(str, str2);
                                    uMLog = UMConfigure.umDebugLog;
                                    UMLog.aq(k.a, 0, "\\|");
                                }
                            });
                        } catch (AccsException e) {
                            UMLog uMLog2 = UMConfigure.umDebugLog;
                            UMLog.mutlInfo(PushAgent.e, 0, "注册失败");
                        }
                    }
                });
            } else {
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(e, 0, "AndroidManifest权限或组件配置错误");
            }
        } catch (Exception e) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "注册失败");
        }
    }

    private void a(String str) {
        Intent intent = new Intent();
        intent.setPackage(this.c.getPackageName());
        intent.setAction(MsgConstant.MESSAGE_REGISTER_CALLBACK_ACTION);
        intent.putExtra("registration_id", str);
        intent.putExtra("status", true);
        this.c.startService(intent);
    }

    private void a(String str, String str2) {
        Intent intent = new Intent();
        intent.setPackage(this.c.getPackageName());
        intent.setAction(MsgConstant.MESSAGE_REGISTER_CALLBACK_ACTION);
        intent.putExtra("status", false);
        intent.putExtra("s", str);
        intent.putExtra("s1", str2);
        this.c.startService(intent);
    }

    private void c() {
        try {
            TaobaoRegister.bindAgoo(this.c, new ICallback() {
                public void onSuccess() {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(PushAgent.e, 2, "开启推送成功");
                    Intent intent = new Intent();
                    intent.setPackage(PushAgent.this.c.getPackageName());
                    intent.setAction(MsgConstant.MESSAGE_ENABLE_CALLBACK_ACTION);
                    intent.putExtra("status", true);
                    PushAgent.this.c.startService(intent);
                }

                public void onFailure(String str, String str2) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(PushAgent.e, 2, "开启推送失败-->s:" + str + ",s1:" + str2);
                    Intent intent = new Intent();
                    intent.setPackage(PushAgent.this.c.getPackageName());
                    intent.setAction(MsgConstant.MESSAGE_ENABLE_CALLBACK_ACTION);
                    intent.putExtra("status", false);
                    intent.putExtra("s", str);
                    intent.putExtra("s1", str2);
                    PushAgent.this.c.startService(intent);
                }
            });
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "开启推送失败");
        }
    }

    private void d() {
        try {
            TaobaoRegister.unbindAgoo(this.c, new ICallback() {
                public void onSuccess() {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(PushAgent.e, 2, "关闭推送成功");
                    Intent intent = new Intent();
                    intent.setPackage(PushAgent.this.c.getPackageName());
                    intent.setAction(MsgConstant.MESSAGE_DISABLE_CALLBACK_ACTION);
                    intent.putExtra("status", true);
                    PushAgent.this.c.startService(intent);
                }

                public void onFailure(String str, String str2) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(PushAgent.e, 0, "关闭推送失败-->s:" + str + ",s1:" + str2);
                    Intent intent = new Intent();
                    intent.setPackage(PushAgent.this.c.getPackageName());
                    intent.setAction(MsgConstant.MESSAGE_DISABLE_CALLBACK_ACTION);
                    intent.putExtra("status", false);
                    intent.putExtra("s", str);
                    intent.putExtra("s1", str2);
                    PushAgent.this.c.startService(intent);
                }
            });
        } catch (Exception e) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "关闭推送失败");
        }
    }

    public void register(IUmengRegisterCallback iUmengRegisterCallback) {
        setRegisterCallback(iUmengRegisterCallback);
        b();
    }

    public void enable(IUmengCallback iUmengCallback) {
        setCallback(iUmengCallback);
        c();
    }

    public void disable(IUmengCallback iUmengCallback) {
        setCallback(iUmengCallback);
        d();
    }

    public void setMessageHandler(UHandler uHandler) {
        this.f = uHandler;
    }

    public UHandler getMessageHandler() {
        return this.f;
    }

    public UHandler getAdHandler() {
        return this.g;
    }

    public void setNotificationClickHandler(UHandler uHandler) {
        this.h = uHandler;
    }

    public UHandler getNotificationClickHandler() {
        return this.h;
    }

    public TagManager getTagManager() {
        return this.b;
    }

    public void addAlias(String str, String str2, ICallBack iCallBack) {
        UTrack.getInstance(this.c).addAlias(str, str2, iCallBack);
    }

    public void setAlias(String str, String str2, ICallBack iCallBack) {
        UTrack.getInstance(this.c).setAlias(str, str2, iCallBack);
    }

    public void deleteAlias(String str, String str2, ICallBack iCallBack) {
        UTrack.getInstance(this.c).deleteAlias(str, str2, iCallBack);
    }

    public String getMessageSecret() {
        String messageAppSecret = MessageSharedPrefs.getInstance(this.c).getMessageAppSecret();
        if (TextUtils.isEmpty(messageAppSecret)) {
            return UmengMessageDeviceConfig.getMetaData(this.c, "UMENG_MESSAGE_SECRET");
        }
        return messageAppSecret;
    }

    public String getMessageAppkey() {
        String messageAppKey = MessageSharedPrefs.getInstance(this.c).getMessageAppKey();
        if (TextUtils.isEmpty(messageAppKey)) {
            return UmengMessageDeviceConfig.getAppkey(this.c);
        }
        return messageAppKey;
    }

    public String getMessageChannel() {
        String messageChannel = MessageSharedPrefs.getInstance(this.c).getMessageChannel();
        if (TextUtils.isEmpty(messageChannel)) {
            return UmengMessageDeviceConfig.getChannel(this.c);
        }
        return messageChannel;
    }

    public void onAppStart() {
        UTrack.getInstance(this.c).trackAppLaunch(10000);
        long j = 0;
        if (isAppLaunchByMessage()) {
            j = Math.abs(new Random().nextLong() % MsgConstant.b);
        }
        UTrack.getInstance(this.c).sendCachedMsgLog(j);
    }

    public <U extends UmengMessageService> void setPushIntentServiceClass(Class<U> cls) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setPushIntentServiceClass(cls);
        }
    }

    public String getPushIntentServiceClass() {
        return MessageSharedPrefs.getInstance(this.c).getPushIntentServiceClass();
    }

    private void setDebugMode(boolean z) {
        DEBUG = z;
        ALog.setPrintLog(z);
        anet.channel.util.ALog.setPrintLog(z);
        SpdyAgent.enableDebug = z;
    }

    public void setNoDisturbMode(int i, int i2, int i3, int i4) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).a(i, i2, i3, i4);
        }
    }

    public int getNoDisturbStartHour() {
        return MessageSharedPrefs.getInstance(this.c).a();
    }

    public int getNoDisturbStartMinute() {
        return MessageSharedPrefs.getInstance(this.c).b();
    }

    public int getNoDisturbEndHour() {
        return MessageSharedPrefs.getInstance(this.c).c();
    }

    public int getNoDisturbEndMinute() {
        return MessageSharedPrefs.getInstance(this.c).d();
    }

    public static void setAppLaunchByMessage() {
        d = true;
    }

    public static boolean isAppLaunchByMessage() {
        return d;
    }

    public String getRegistrationId() {
        return MessageSharedPrefs.getInstance(this.c).getDeviceToken();
    }

    public void setDisplayNotificationNumber(int i) {
        if (h.d(this.c) && i >= 0 && i <= 10) {
            MessageSharedPrefs.getInstance(this.c).setDisplayNotificationNumber(i);
        }
    }

    public int getDisplayNotificationNumber() {
        return MessageSharedPrefs.getInstance(this.c).getDisplayNotificationNumber();
    }

    @Deprecated
    private void setAppkeyAndSecret(String str, String str2) {
        if (h.d(this.c)) {
            String messageAppKey = MessageSharedPrefs.getInstance(this.c).getMessageAppKey();
            String messageAppSecret = MessageSharedPrefs.getInstance(this.c).getMessageAppSecret();
            if (!(messageAppKey.equals(str) || messageAppSecret.equals(str2))) {
                MessageSharedPrefs.getInstance(this.c).removeMessageAppKey();
                MessageSharedPrefs.getInstance(this.c).removeMessageAppSecret();
            }
            MessageSharedPrefs.getInstance(this.c).setMessageAppKey(str);
            MessageSharedPrefs.getInstance(this.c).setMessageAppSecret(str2);
            UTrack.getInstance(this.c).updateHeader();
        }
    }

    @Deprecated
    private void setAppkey(String str) {
        if (!h.d(this.c)) {
            return;
        }
        if (str == null || str.equals("")) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "appkey不能为null");
            return;
        }
        MessageSharedPrefs.getInstance(this.c).setMessageAppKey(str);
    }

    @Deprecated
    private void setSecret(String str) {
        if (!h.d(this.c)) {
            return;
        }
        if (str == null || str.equals("")) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(e, 0, "appSecret不能为null");
            return;
        }
        MessageSharedPrefs.getInstance(this.c).setMessageAppSecret(str);
    }

    @Deprecated
    private void setMessageChannel(String str) {
        System.currentTimeMillis();
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setMessageChannel(str);
            d.a(new Runnable() {
                public void run() {
                    UTrack.getInstance(PushAgent.this.c).updateHeader();
                }
            });
        }
    }

    public void setRegisterCallback(IUmengRegisterCallback iUmengRegisterCallback) {
        this.l = iUmengRegisterCallback;
    }

    public IUmengRegisterCallback getRegisterCallback() {
        return this.l;
    }

    public void setCallback(IUmengCallback iUmengCallback) {
        this.m = iUmengCallback;
    }

    public IUmengCallback getCallback() {
        return this.m;
    }

    public void setMuteDurationSeconds(int i) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setMuteDuration(i);
        }
    }

    public int getMuteDurationSeconds() {
        return MessageSharedPrefs.getInstance(this.c).getMuteDuration();
    }

    public boolean isIncludesUmengUpdateSDK() {
        Class cls;
        try {
            cls = Class.forName("com.umeng.update.UmengUpdateAgent");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            cls = null;
        }
        if (cls != null) {
            return true;
        }
        return false;
    }

    public boolean getNotificationOnForeground() {
        return MessageSharedPrefs.getInstance(this.c).getNotificaitonOnForeground();
    }

    public void setNotificaitonOnForeground(boolean z) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setNotificaitonOnForeground(z);
        }
    }

    public String getResourcePackageName() {
        return MessageSharedPrefs.getInstance(this.c).getResourcePackageName();
    }

    public void setResourcePackageName(String str) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setResourcePackageName(str);
        }
    }

    public boolean isPushCheck() {
        return this.i;
    }

    public void setPushCheck(boolean z) {
        this.i = z;
    }

    public int getNotificationPlayVibrate() {
        return MessageSharedPrefs.getInstance(this.c).getNotificationPlayVibrate();
    }

    public void setNotificationPlayVibrate(int i) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setNotificationPlayVibrate(i);
        }
    }

    public int getNotificationPlayLights() {
        return MessageSharedPrefs.getInstance(this.c).getNotificationPlayLights();
    }

    public void setNotificationPlayLights(int i) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setNotificationPlayLights(i);
        }
    }

    public int getNotificationPlaySound() {
        return MessageSharedPrefs.getInstance(this.c).getNotificationPlaySound();
    }

    public void setNotificationPlaySound(int i) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).setNotificationPlaySound(i);
        }
    }

    public void keepLowPowerMode(boolean z) {
        this.j = !z;
    }

    private boolean e() {
        return this.j;
    }

    public void setEnableForground(Context context, boolean z) {
        GlobalConfig.setEnableForground(context, z);
    }

    public void setNotificationChannelName(String str) {
        if (h.d(this.c)) {
            MessageSharedPrefs.getInstance(this.c).b(str);
        }
    }

    public String getNotificationChannelName() {
        return MessageSharedPrefs.getInstance(this.c).h();
    }
}
