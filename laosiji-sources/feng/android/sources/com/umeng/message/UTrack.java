package com.umeng.message;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.stub.StubApp;
import com.talkingdata.sdk.ab;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.analytics.pro.b;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.commonsdk.proguard.g;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.message.common.UmengMessageDeviceConfig;
import com.umeng.message.common.d;
import com.umeng.message.common.impl.json.JUtrack;
import com.umeng.message.common.inter.IUtrack;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.c;
import com.umeng.message.proguard.h;
import com.umeng.message.proguard.m;
import com.umeng.message.proguard.m.a;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UTrack {
    private static final String a = UTrack.class.getName();
    private static UTrack d;
    private static IUtrack e;
    private static boolean i = false;
    private static boolean j = false;
    private static boolean k = false;
    private JSONObject b;
    private JSONObject c;
    private Context f;
    private boolean g;
    private final String h = "appkey";

    public interface ICallBack {
        void onMessage(boolean z, String str);
    }

    enum SuccessState {
        SUCCESS_CACHE,
        SUCCESS,
        FAIL_REQUEST,
        FAIL_PARAM
    }

    public void setClearPrevMessage(boolean z) {
        this.g = z;
    }

    private UTrack(Context context) {
        this.f = StubApp.getOrigApplicationContext(context.getApplicationContext());
        e();
    }

    public static synchronized UTrack getInstance(Context context) {
        UTrack uTrack;
        synchronized (UTrack.class) {
            if (d == null) {
                d = new UTrack(context);
                e = new JUtrack(context);
            }
            uTrack = d;
        }
        return uTrack;
    }

    public void trackMsgArrival(UMessage uMessage) {
        if (uMessage != null && uMessage.msg_id != null) {
            b(uMessage.msg_id, 0, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
        }
    }

    public void trackMsgClick(UMessage uMessage) {
        if (!(uMessage == null || uMessage.msg_id == null)) {
            b(uMessage.msg_id, 1, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
        }
        if (!(uMessage == null || uMessage.message_id == null)) {
            a(uMessage.message_id, uMessage.task_id, "8");
        }
        if (!this.g) {
            return;
        }
        if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
            ((UmengAdHandler) PushAgent.getInstance(this.f).getAdHandler()).setPrevMessage(null);
        } else {
            ((UmengMessageHandler) PushAgent.getInstance(this.f).getMessageHandler()).setPrevMessage(null);
        }
    }

    public void trackMiPushMsgClick(UMessage uMessage) {
        if (!(uMessage == null || uMessage.msg_id == null)) {
            b(uMessage.msg_id, 21, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
        }
        if (!this.g) {
            return;
        }
        if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
            ((UmengAdHandler) PushAgent.getInstance(this.f).getAdHandler()).setPrevMessage(null);
        } else {
            ((UmengMessageHandler) PushAgent.getInstance(this.f).getMessageHandler()).setPrevMessage(null);
        }
    }

    public void trackMsgDismissed(UMessage uMessage) {
        if (!(uMessage == null || uMessage.msg_id == null)) {
            b(uMessage.msg_id, 2, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
        }
        if (!(uMessage == null || uMessage.message_id == null)) {
            a(uMessage.message_id, uMessage.task_id, "9");
        }
        if (UMessage.DISPLAY_TYPE_NOTIFICATIONPULLAPP.equals(uMessage.display_type)) {
            ((UmengAdHandler) PushAgent.getInstance(this.f).getAdHandler()).setPrevMessage(null);
        } else {
            ((UmengMessageHandler) PushAgent.getInstance(this.f).getMessageHandler()).setPrevMessage(null);
        }
    }

    void a(UMessage uMessage) {
        b(uMessage.recall, 4, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
    }

    void b(UMessage uMessage) {
        b(uMessage.recall, 5, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
    }

    public void trackMsgPulled(UMessage uMessage, int i) {
        if (uMessage != null && uMessage.msg_id != null) {
            b(uMessage.msg_id, i, OkHttpUtils.DEFAULT_MILLISECONDS * uMessage.random_min, uMessage.pulledWho);
        }
    }

    private void a(String str, int i, long j, String str2) {
        UMLog uMLog;
        if (!f()) {
            return;
        }
        if (TextUtils.isEmpty(str)) {
            UMLog uMLog2 = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "trackMsgLog: msgId为空");
            return;
        }
        final long currentTimeMillis = System.currentTimeMillis();
        try {
            m.a(this.f).a(str, i, currentTimeMillis, str2);
        } catch (Exception e) {
            e.printStackTrace();
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "trackMsgLog: ", e.toString());
        }
        final String str3 = str;
        final int i2 = i;
        final String str4 = str2;
        Runnable anonymousClass1 = new Runnable() {
            public void run() {
                UTrack.this.c(str3, i2, currentTimeMillis, str4);
            }
        };
        long j2 = 0;
        if (!(j <= 0 || i == 1 || i == 21)) {
            j2 = Math.abs(new Random().nextLong() % j);
        }
        uMLog = UMConfigure.umDebugLog;
        String str5 = a;
        String[] strArr = new String[1];
        strArr[0] = String.format("trackMsgLog(msgId=%s, actionType=%d, random=%d, delay=%d)", new Object[]{str, Integer.valueOf(i), Long.valueOf(j), Long.valueOf(j2)});
        UMLog.mutlInfo(str5, 2, strArr);
        d.a(anonymousClass1, j2, TimeUnit.MILLISECONDS);
    }

    private void b(String str, int i, long j, String str2) {
        a(str, i, j, str2);
    }

    private void a(final String str, final String str2, final String str3) {
        d.a(new Runnable() {
            public void run() {
                UTrack.this.sendMsgLogForAgoo(str, str2, str3);
            }
        });
    }

    private synchronized void c(String str, int i, long j, String str2) {
        Object str22;
        if (str22 == null) {
            str22 = "";
        }
        try {
            JSONObject i2 = i();
            i2.put("msg_id", str);
            i2.put(MsgConstant.KEY_ACTION_TYPE, i);
            i2.put("ts", j);
            i2.put("pa", str22);
            e.sendMsgLog(i2, str, i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public synchronized void sendMsgLogForAgoo(String str, String str2, String str3) {
        UMLog uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "sendMsgLogForAgoo-->msgId:" + str + ",taskId:" + str2);
        if (str3.equalsIgnoreCase("8")) {
            TaobaoRegister.clickMessage(this.f, str, str2);
        } else {
            TaobaoRegister.dismissMessage(this.f, str, str2);
        }
        m.a(this.f).b(str, str3);
        if (!str3.equals(MsgConstant.MESSAGE_NOTIFY_ARRIVAL)) {
            m.a(this.f).d(str);
        }
    }

    private void a(long j) {
        if (!f()) {
            return;
        }
        UMLog uMLog;
        if (i) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "appCachedPushlog已经在队列里, 忽略这次请求");
            return;
        }
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "appCachedPushlog开始, 设置appLaunchSending标志位");
        i = true;
        if (h.d(this.f)) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(30000);
                        UTrack.i = false;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        d.a(new Runnable() {
            public void run() {
                try {
                    JSONArray jSONArray = new JSONArray();
                    ArrayList a = m.a(UTrack.this.f).a();
                    if (a != null && a.size() > 0) {
                        int i = 0;
                        while (true) {
                            int i2 = i;
                            if (i2 >= a.size()) {
                                break;
                            }
                            a aVar = (a) a.get(i2);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("ts", aVar.b);
                            jSONObject.put("pa", aVar.d);
                            jSONObject.put("device_token", MessageSharedPrefs.getInstance(UTrack.this.f).getDeviceToken());
                            jSONObject.put("msg_id", aVar.a);
                            jSONObject.put(MsgConstant.KEY_ACTION_TYPE, aVar.c);
                            jSONArray.put(jSONObject);
                            i = i2 + 1;
                        }
                        UMSLEnvelopeBuild.mContext = UTrack.this.f;
                        UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
                        JSONObject buildSLBaseHeader = uMSLEnvelopeBuild.buildSLBaseHeader(UTrack.this.f);
                        JSONObject jSONObject2 = (JSONObject) buildSLBaseHeader.opt("header");
                        jSONObject2.put("din", UmengMessageDeviceConfig.getDINAes(UTrack.this.f));
                        jSONObject2.put(g.as, MsgConstant.SDK_VERSION);
                        jSONObject2.put("umid", UmengMessageDeviceConfig.getUmid(UTrack.this.f));
                        buildSLBaseHeader.put("header", jSONObject2);
                        jSONObject2 = new JSONObject();
                        jSONObject2.put("push", jSONArray);
                        if (h.d(UTrack.this.f)) {
                            jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(UTrack.this.f, buildSLBaseHeader, jSONObject2, MsgConstant.UNPX_PUSH_LOGS);
                            if (jSONObject2 != null && !jSONObject2.has(b.ao)) {
                                UTrack.this.a(jSONArray);
                                return;
                            }
                            return;
                        }
                        JSONObject jSONObject3 = new JSONObject();
                        jSONObject3.put("jsonHeader", buildSLBaseHeader);
                        jSONObject3.put("jsonBody", jSONObject2);
                        Intent intent = new Intent();
                        intent.setPackage(UTrack.this.f.getPackageName());
                        intent.setAction(MsgConstant.MESSAGE_MESSAGE_SEND_ACTION);
                        intent.putExtra(MsgConstant.KEY_UMPX_PATH, MsgConstant.UNPX_PUSH_LOGS);
                        intent.putExtra(MsgConstant.KEY_SENDMESSAGE, jSONObject3.toString());
                        UTrack.this.f.startService(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        d.a(new Runnable() {
            public void run() {
                int i = 0;
                try {
                    ArrayList c = m.a(UTrack.this.f).c();
                    while (true) {
                        int i2 = i;
                        if (i2 < c.size()) {
                            m.b bVar = (m.b) c.get(i2);
                            UTrack.this.sendMsgLogForAgoo(bVar.a, bVar.b, bVar.c);
                            i = i2 + 1;
                        } else {
                            return;
                        }
                    }
                } catch (Throwable th) {
                    th.printStackTrace();
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 2, th.toString());
                }
            }
        });
    }

    private void a(JSONArray jSONArray) {
        int i = 0;
        if (jSONArray != null) {
            try {
                ArrayList arrayList = new ArrayList();
                if (jSONArray.length() > 0) {
                    while (true) {
                        int i2 = i;
                        if (i2 >= jSONArray.length()) {
                            break;
                        }
                        JSONObject jSONObject = (JSONObject) jSONArray.get(i2);
                        String optString = jSONObject.optString("msg_id");
                        String[] strArr = new String[]{optString, jSONObject.optInt(MsgConstant.KEY_ACTION_TYPE) + ""};
                        com.umeng.message.provider.a.a(this.f);
                        arrayList.add(ContentProviderOperation.newDelete(com.umeng.message.provider.a.f).withSelection("MsgId=? And ActionType=?", strArr).build());
                        if (jSONObject.optInt(MsgConstant.KEY_ACTION_TYPE) != 0) {
                            String[] strArr2 = new String[]{optString};
                            com.umeng.message.provider.a.a(this.f);
                            arrayList.add(ContentProviderOperation.newDelete(com.umeng.message.provider.a.g).withSelection("MsgId=?", strArr2).build());
                        }
                        i = i2 + 1;
                    }
                }
                ContentResolver contentResolver = this.f.getContentResolver();
                com.umeng.message.provider.a.a(this.f);
                contentResolver.applyBatch(com.umeng.message.provider.a.a, arrayList);
            } catch (Exception e) {
            }
        }
    }

    public void sendCachedMsgLog(long j) {
        a(j);
    }

    public void trackAppLaunch(long j) {
        if (!f()) {
            return;
        }
        if (MessageSharedPrefs.getInstance(this.f).getAppLaunchLogSendPolicy() == 1) {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "launch_policy=1, 跳过发送应用程序启动信息");
        } else if (!MessageSharedPrefs.getInstance(this.f).hasAppLaunchLogSentToday()) {
            b(j);
        }
    }

    private void b(long j) {
        UMLog uMLog;
        if (j) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "trackAppLaunch已经在队列里, 忽略这次请求");
            return;
        }
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "trackAppLaunch开始, 设置appLaunchSending标志位");
        j = true;
        Runnable anonymousClass9 = new Runnable() {
            public void run() {
                try {
                    JSONObject b = UTrack.this.h();
                    JSONArray c = UTrack.this.d();
                    if (c != null) {
                        b.put(MsgConstant.KEY_UCODE, c.a(c.toString()));
                    }
                    UTrack.e.trackAppLaunch(b);
                } catch (Exception e) {
                } finally {
                    UTrack.j = false;
                }
            }
        };
        UMLog uMLog2 = UMConfigure.umDebugLog;
        String str = a;
        String[] strArr = new String[1];
        strArr[0] = String.format("trackAppLaunch(delay=%d)", new Object[]{Long.valueOf(j)});
        UMLog.mutlInfo(str, 2, strArr);
        d.a(anonymousClass9, j, TimeUnit.MILLISECONDS);
    }

    /* JADX WARNING: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A:{SYNTHETIC, RETURN, ORIG_RETURN} */
    private org.json.JSONArray d() {
        /*
        r5 = this;
        r2 = 0;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r0 = "[";
        r4.append(r0);
        r0 = r5.f;
        r0 = com.umeng.message.MessageSharedPrefs.getInstance(r0);
        r0 = r0.getUcode();
        if (r0 == 0) goto L_0x002d;
    L_0x0018:
        r1 = "";
        r1 = r0.equals(r1);
        if (r1 != 0) goto L_0x002d;
    L_0x0021:
        r0 = com.umeng.message.proguard.h.g(r0);	 Catch:{ Exception -> 0x0029 }
        r3 = r0;
    L_0x0026:
        if (r3 != 0) goto L_0x002f;
    L_0x0028:
        return r2;
    L_0x0029:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x002d:
        r3 = r2;
        goto L_0x0026;
    L_0x002f:
        r0 = 0;
        r1 = r0;
    L_0x0031:
        r0 = r3.size();
        if (r1 >= r0) goto L_0x0089;
    L_0x0037:
        r0 = "{";
        r4.append(r0);
        r0 = "\"p\":";
        r4.append(r0);
        r0 = "\"";
        r4.append(r0);
        r0 = r3.get(r1);
        r0 = (com.umeng.message.entity.Ucode) r0;
        r0 = r0.p;
        r4.append(r0);
        r0 = "\"";
        r4.append(r0);
        r0 = ",";
        r4.append(r0);
        r0 = "\"t\":";
        r4.append(r0);
        r0 = r3.get(r1);
        r0 = (com.umeng.message.entity.Ucode) r0;
        r0 = r0.b;
        r4.append(r0);
        r0 = "}";
        r4.append(r0);
        r0 = r3.size();
        r0 = r0 + -1;
        if (r1 == r0) goto L_0x0085;
    L_0x007f:
        r0 = ",";
        r4.append(r0);
    L_0x0085:
        r0 = r1 + 1;
        r1 = r0;
        goto L_0x0031;
    L_0x0089:
        r0 = "]";
        r4.append(r0);
        r0 = new org.json.JSONArray;	 Catch:{ Exception -> 0x009a }
        r1 = r4.toString();	 Catch:{ Exception -> 0x009a }
        r0.<init>(r1);	 Catch:{ Exception -> 0x009a }
    L_0x0098:
        r2 = r0;
        goto L_0x0028;
    L_0x009a:
        r0 = move-exception;
        r0.printStackTrace();
        r0 = r2;
        goto L_0x0098;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UTrack.d():org.json.JSONArray");
    }

    void a() {
        if (!f() || MessageSharedPrefs.getInstance(this.f).getHasRegister()) {
            return;
        }
        UMLog uMLog;
        if (k) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "sendRegisterLog已经在队列里，忽略这次请求");
            return;
        }
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "trackRegisterLog开始, 设置registerSending标志位");
        k = true;
        Runnable anonymousClass10 = new Runnable() {
            public void run() {
                try {
                    JSONObject b = UTrack.this.h();
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 2, "trackRegister-->request:" + b.toString());
                    String d = UTrack.this.g();
                    if (!h.d(d)) {
                        UMLog uMLog2 = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(UTrack.a, 2, "TestDevice sign =" + d);
                        b.put(ab.r, d);
                    }
                    UTrack.e.trackRegister(b);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    UTrack.k = false;
                }
            }
        };
        UMLog uMLog2 = UMConfigure.umDebugLog;
        String str = a;
        String[] strArr = new String[1];
        strArr[0] = String.format("trackRegister(delay=%d)", new Object[]{Integer.valueOf(0)});
        UMLog.mutlInfo(str, 2, strArr);
        d.a(anonymousClass10, 0, TimeUnit.MILLISECONDS);
    }

    private void e() {
        com.umeng.message.common.b bVar;
        if (this.b == null) {
            bVar = new com.umeng.message.common.b();
            bVar.b(this.f, new String[0]);
            bVar.a(this.f, PushAgent.getInstance(this.f).getMessageAppkey(), PushAgent.getInstance(this.f).getMessageChannel());
            this.b = new JSONObject();
            try {
                bVar.b(this.b);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (this.c == null) {
            bVar = new com.umeng.message.common.b();
            bVar.c(this.f, new String[0]);
            bVar.a(this.f, PushAgent.getInstance(this.f).getMessageAppkey(), PushAgent.getInstance(this.f).getMessageChannel());
            this.c = new JSONObject();
            try {
                bVar.c(this.c);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public JSONObject getHeader() {
        return this.b;
    }

    public void sendAliasFailLog() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    UTrack.e.sendAliasFailLog(UTrack.this.h());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void sendRegisterLog(final String str) {
        new Thread(new Runnable() {
            public void run() {
                if (MessageSharedPrefs.getInstance(UTrack.this.f).getDaRegisterSendPolicy() == 1) {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 2, "da_register_policy=1, skip sending da_register info");
                    return;
                }
                try {
                    JSONObject b = UTrack.this.h();
                    b.put("registerLog", str);
                    UTrack.e.sendRegisterLog(b);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public boolean addAlias(final String str, final String str2, final ICallBack iCallBack) {
        new Thread(new Runnable() {
            public void run() {
                UMLog uMLog;
                SuccessState successState = null;
                try {
                    JSONObject jSONObject;
                    String str = MessageSharedPrefs.getInstance(UTrack.this.f).get_addAliasInterval();
                    if (str == null || str.length() <= 0) {
                        jSONObject = null;
                    } else {
                        jSONObject = new JSONObject(str);
                    }
                    String a = UTrack.this.a(str, str2, jSONObject);
                    if (a != null && a.length() > 0) {
                        iCallBack.onMessage(false, a);
                        return;
                    }
                } catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
                Object obj = "";
                Object obj2 = "" + "utdid:" + UmengMessageDeviceConfig.getUtdid(UTrack.this.f) + ",deviceToken:" + MessageSharedPrefs.getInstance(UTrack.this.f).getDeviceToken() + ";";
                if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "addAlias: type或alias为空");
                    obj2 = obj2 + "addAlias: empty type or alias;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(UmengMessageDeviceConfig.getUtdid(UTrack.this.f))) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "UTDID为空");
                    obj2 = obj2 + "UTDID is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(MessageSharedPrefs.getInstance(UTrack.this.f).getDeviceToken())) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "Device token为空");
                    obj2 = obj2 + "RegistrationId is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (MessageSharedPrefs.getInstance(UTrack.this.f).isAliasSet(0, str, str2)) {
                    uMLog = UMConfigure.umDebugLog;
                    String b = UTrack.a;
                    String[] strArr = new String[1];
                    strArr[0] = String.format("addAlias: <%s, %s> 已经同步至服务器，忽略该请求", new Object[]{str, str2});
                    UMLog.mutlInfo(b, 2, strArr);
                    obj = obj + String.format("addAlias: <%s, %s> has been synced to the server before. Ingore this request;", new Object[]{str, str2});
                    successState = SuccessState.SUCCESS_CACHE;
                }
                try {
                    JSONObject b2 = UTrack.this.h();
                    if (successState == null) {
                        b2.put("alias", str);
                        b2.put("type", str2);
                        b2.put(MsgConstant.KEY_LAST_ALIAS, MessageSharedPrefs.getInstance(UTrack.this.f).getLastAlias(0, str2));
                        b2.put("ts", System.currentTimeMillis());
                    } else if (successState == SuccessState.FAIL_PARAM) {
                        b2.put("fail", obj2);
                    } else if (successState == SuccessState.SUCCESS_CACHE) {
                        b2.put("success", obj);
                    }
                    UTrack.e.addAlias(str, str2, b2, iCallBack);
                } catch (Exception e2) {
                    Exception exception = e2;
                    if (exception == null || exception.getMessage() == null) {
                        iCallBack.onMessage(false, "alias:" + str + "添加失败");
                        MessageSharedPrefs.getInstance(UTrack.this.f).addAlias(str, str2, 0, 1, "添加失败");
                        return;
                    }
                    iCallBack.onMessage(false, "alias:" + str + "添加失败:" + exception.getMessage());
                    MessageSharedPrefs.getInstance(UTrack.this.f).addAlias(str, str2, 0, 1, exception.getMessage());
                }
            }
        }).start();
        return false;
    }

    private String a(String str, String str2, JSONObject jSONObject) {
        Object obj = 1;
        try {
            byte[] bytes = str.getBytes("UTF-8");
            byte[] bytes2 = str2.getBytes("UTF-8");
            Object obj2 = (bytes.length > 128 || bytes.length < 0) ? null : 1;
            if (bytes2.length > 64 || bytes2.length < 0) {
                obj = null;
            }
            if (obj2 == null || obj == null) {
                return "alias长度不在0~128之间或aliasType长度不在0~64之间";
            }
            if (jSONObject == null) {
                return null;
            }
            long optLong = jSONObject.optLong("interval", 0);
            long optLong2 = jSONObject.optLong("last_requestTime", 0);
            long currentTimeMillis = System.currentTimeMillis();
            if (optLong == 0 || (currentTimeMillis - optLong2) / 1000 >= optLong) {
                return null;
            }
            return "interval限制";
        } catch (Exception e) {
            if (e == null) {
                return null;
            }
            e.printStackTrace();
            return null;
        }
    }

    public void setAlias(final String str, final String str2, final ICallBack iCallBack) {
        new Thread(new Runnable() {
            public void run() {
                UMLog uMLog;
                SuccessState successState = null;
                try {
                    JSONObject jSONObject;
                    String str = MessageSharedPrefs.getInstance(UTrack.this.f).get_setAliasInterval();
                    if (str == null || str.length() <= 0) {
                        jSONObject = null;
                    } else {
                        jSONObject = new JSONObject(str);
                    }
                    String a = UTrack.this.a(str, str2, jSONObject);
                    if (a != null && a.length() > 0) {
                        iCallBack.onMessage(false, a);
                        return;
                    }
                } catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
                Object obj = "";
                Object obj2 = "";
                if (TextUtils.isEmpty(str2)) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "addExclusiveAlias: type为空");
                    obj = obj + "addExclusiveAlias: empty type";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(UmengMessageDeviceConfig.getUtdid(UTrack.this.f))) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "UTDID为空");
                    obj = obj + "UTDID is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(MessageSharedPrefs.getInstance(UTrack.this.f).getDeviceToken())) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "Device token为空");
                    obj = obj + "RegistrationId is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (MessageSharedPrefs.getInstance(UTrack.this.f).isAliasSet(1, str, str2)) {
                    uMLog = UMConfigure.umDebugLog;
                    String b = UTrack.a;
                    String[] strArr = new String[1];
                    strArr[0] = String.format("addExclusiveAlias: <%s, %s> 已经同步至服务器，忽略该请求", new Object[]{str, str2});
                    UMLog.mutlInfo(b, 2, strArr);
                    obj2 = obj2 + String.format("addExclusiveAlias: <%s, %s> has been synced to the server before. Ingore this request.", new Object[]{str, str2});
                    successState = SuccessState.SUCCESS_CACHE;
                }
                try {
                    JSONObject b2 = UTrack.this.h();
                    if (successState == null) {
                        b2.put("alias", str);
                        b2.put("type", str2);
                        b2.put(MsgConstant.KEY_LAST_ALIAS, MessageSharedPrefs.getInstance(UTrack.this.f).getLastAlias(1, str2));
                        b2.put("ts", System.currentTimeMillis());
                    } else if (successState == SuccessState.FAIL_PARAM) {
                        b2.put("fail", obj);
                    } else if (successState == SuccessState.SUCCESS_CACHE) {
                        b2.put("success", obj2);
                    }
                    UTrack.e.setAlias(str, str2, b2, iCallBack);
                } catch (Exception e2) {
                    Exception exception = e2;
                    if (exception == null || exception.getMessage() == null) {
                        iCallBack.onMessage(false, "alias:" + str + "添加失败");
                        MessageSharedPrefs.getInstance(UTrack.this.f).addAlias(str, str2, 1, 1, "添加失败");
                        return;
                    }
                    iCallBack.onMessage(false, "alias:" + str + "添加失败:" + exception.getMessage());
                    MessageSharedPrefs.getInstance(UTrack.this.f).addAlias(str, str2, 1, 1, exception.getMessage());
                }
            }
        }).start();
    }

    public void deleteAlias(final String str, final String str2, final ICallBack iCallBack) {
        new Thread(new Runnable() {
            public void run() {
                String str;
                UMLog uMLog;
                SuccessState successState = null;
                try {
                    JSONObject jSONObject;
                    str = MessageSharedPrefs.getInstance(UTrack.this.f).get_deleteALiasInterval();
                    if (str == null || str.length() <= 0) {
                        jSONObject = null;
                    } else {
                        jSONObject = new JSONObject(str);
                    }
                    String a = UTrack.this.a(str, str2, jSONObject);
                    if (a != null && a.length() > 0) {
                        iCallBack.onMessage(false, a);
                        return;
                    }
                } catch (Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                }
                Object obj = "";
                str = "";
                if (TextUtils.isEmpty(str2)) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "removeAlias: type为空");
                    obj = obj + "removeAlias: empty type";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(UmengMessageDeviceConfig.getUtdid(UTrack.this.f))) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "UTDID为空");
                    obj = obj + "UTDID is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                if (TextUtils.isEmpty(MessageSharedPrefs.getInstance(UTrack.this.f).getDeviceToken())) {
                    uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(UTrack.a, 0, "Device token为空");
                    obj = obj + "RegistrationId is empty;";
                    successState = SuccessState.FAIL_PARAM;
                }
                try {
                    JSONObject b = UTrack.this.h();
                    if (successState == null) {
                        b.put("alias", str);
                        b.put("type", str2);
                        b.put("ts", System.currentTimeMillis());
                    } else if (successState == SuccessState.FAIL_PARAM) {
                        b.put("fail", obj);
                    } else if (successState == SuccessState.SUCCESS_CACHE) {
                        b.put("success", str);
                    }
                    UTrack.e.deleteAlias(str, str2, b, iCallBack);
                } catch (Exception e2) {
                    if (e2 == null || e2.getMessage() == null) {
                        iCallBack.onMessage(false, "alias:" + str + "移除失败");
                    } else {
                        iCallBack.onMessage(false, "alias:" + str + "移除失败:" + e2.getMessage());
                    }
                }
            }
        }).start();
    }

    private boolean f() {
        UMLog uMLog;
        if (TextUtils.isEmpty(UmengMessageDeviceConfig.getUtdid(this.f))) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "UTDID为空");
            return false;
        } else if (!TextUtils.isEmpty(MessageSharedPrefs.getInstance(this.f).getDeviceToken())) {
            return true;
        } else {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "Device token为空");
            return false;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:68:? A:{SYNTHETIC, RETURN, Catch:{ Exception -> 0x009d }} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b7 A:{SYNTHETIC, Splitter: B:34:0x00b7} */
    /* JADX WARNING: Removed duplicated region for block: B:69:? A:{SYNTHETIC, RETURN, Catch:{ Exception -> 0x009d }} */
    /* JADX WARNING: Removed duplicated region for block: B:44:0x00c9 A:{SYNTHETIC, Splitter: B:44:0x00c9} */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00d8 A:{SYNTHETIC, Splitter: B:52:0x00d8} */
    @android.annotation.SuppressLint({"NewApi"})
    private java.lang.String g() {
        /*
        r8 = this;
        r0 = 0;
        r1 = android.os.Environment.getExternalStorageState();	 Catch:{ Exception -> 0x009d }
        r2 = "mounted";
        r1 = r1.equals(r2);	 Catch:{ Exception -> 0x009d }
        if (r1 != 0) goto L_0x000f;
    L_0x000e:
        return r0;
    L_0x000f:
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009d }
        r1.<init>();	 Catch:{ Exception -> 0x009d }
        r2 = android.os.Environment.getExternalStorageDirectory();	 Catch:{ Exception -> 0x009d }
        r2 = r2.getPath();	 Catch:{ Exception -> 0x009d }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x009d }
        r2 = "/data/";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x009d }
        r2 = r8.f;	 Catch:{ Exception -> 0x009d }
        r2 = r2.getPackageName();	 Catch:{ Exception -> 0x009d }
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x009d }
        r2 = "/";
        r1 = r1.append(r2);	 Catch:{ Exception -> 0x009d }
        r1 = r1.toString();	 Catch:{ Exception -> 0x009d }
        r2 = com.umeng.commonsdk.UMConfigure.umDebugLog;	 Catch:{ Exception -> 0x009d }
        r2 = a;	 Catch:{ Exception -> 0x009d }
        r3 = 2;
        r4 = 1;
        r4 = new java.lang.String[r4];	 Catch:{ Exception -> 0x009d }
        r5 = 0;
        r6 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x009d }
        r6.<init>();	 Catch:{ Exception -> 0x009d }
        r7 = "path=";
        r6 = r6.append(r7);	 Catch:{ Exception -> 0x009d }
        r6 = r6.append(r1);	 Catch:{ Exception -> 0x009d }
        r6 = r6.toString();	 Catch:{ Exception -> 0x009d }
        r4[r5] = r6;	 Catch:{ Exception -> 0x009d }
        com.umeng.commonsdk.debug.UMLog.mutlInfo(r2, r3, r4);	 Catch:{ Exception -> 0x009d }
        r3 = new java.io.File;	 Catch:{ Exception -> 0x009d }
        r2 = "umeng-message.config";
        r3.<init>(r1, r2);	 Catch:{ Exception -> 0x009d }
        r1 = r3.exists();	 Catch:{ Exception -> 0x009d }
        if (r1 == 0) goto L_0x000e;
    L_0x006c:
        r2 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
        r1 = new java.io.FileReader;	 Catch:{ FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
        r1.<init>(r3);	 Catch:{ FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x00b0, IOException -> 0x00c2, all -> 0x00d4 }
    L_0x0076:
        r1 = r2.readLine();	 Catch:{ FileNotFoundException -> 0x00e5, IOException -> 0x00e3 }
        if (r1 == 0) goto L_0x00a3;
    L_0x007c:
        r3 = "sign=";
        r3 = r1.startsWith(r3);	 Catch:{ FileNotFoundException -> 0x00e5, IOException -> 0x00e3 }
        if (r3 == 0) goto L_0x0076;
    L_0x0085:
        r3 = "sign=";
        r3 = r3.length();	 Catch:{ FileNotFoundException -> 0x00e5, IOException -> 0x00e3 }
        r1 = r1.substring(r3);	 Catch:{ FileNotFoundException -> 0x00e5, IOException -> 0x00e3 }
        if (r2 == 0) goto L_0x0095;
    L_0x0092:
        r2.close();	 Catch:{ IOException -> 0x0098 }
    L_0x0095:
        r0 = r1;
        goto L_0x000e;
    L_0x0098:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ Exception -> 0x009d }
        goto L_0x0095;
    L_0x009d:
        r1 = move-exception;
        r1.printStackTrace();
        goto L_0x000e;
    L_0x00a3:
        if (r2 == 0) goto L_0x000e;
    L_0x00a5:
        r2.close();	 Catch:{ IOException -> 0x00aa }
        goto L_0x000e;
    L_0x00aa:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x009d }
        goto L_0x000e;
    L_0x00b0:
        r1 = move-exception;
        r2 = r0;
    L_0x00b2:
        r1.printStackTrace();	 Catch:{ all -> 0x00e1 }
        if (r2 == 0) goto L_0x000e;
    L_0x00b7:
        r2.close();	 Catch:{ IOException -> 0x00bc }
        goto L_0x000e;
    L_0x00bc:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x009d }
        goto L_0x000e;
    L_0x00c2:
        r1 = move-exception;
        r2 = r0;
    L_0x00c4:
        r1.printStackTrace();	 Catch:{ all -> 0x00e1 }
        if (r2 == 0) goto L_0x000e;
    L_0x00c9:
        r2.close();	 Catch:{ IOException -> 0x00ce }
        goto L_0x000e;
    L_0x00ce:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ Exception -> 0x009d }
        goto L_0x000e;
    L_0x00d4:
        r1 = move-exception;
        r2 = r0;
    L_0x00d6:
        if (r2 == 0) goto L_0x00db;
    L_0x00d8:
        r2.close();	 Catch:{ IOException -> 0x00dc }
    L_0x00db:
        throw r1;	 Catch:{ Exception -> 0x009d }
    L_0x00dc:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ Exception -> 0x009d }
        goto L_0x00db;
    L_0x00e1:
        r1 = move-exception;
        goto L_0x00d6;
    L_0x00e3:
        r1 = move-exception;
        goto L_0x00c4;
    L_0x00e5:
        r1 = move-exception;
        goto L_0x00b2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.UTrack.g():java.lang.String");
    }

    private JSONObject h() throws JSONException {
        String deviceToken = MessageSharedPrefs.getInstance(this.f).getDeviceToken();
        String utdid = UmengMessageDeviceConfig.getUtdid(this.f);
        JSONObject jSONObject = new JSONObject();
        this.b.put("umid", UmengMessageDeviceConfig.getUmid(this.f));
        jSONObject.put("header", this.b);
        jSONObject.put("utdid", utdid);
        jSONObject.put("device_token", deviceToken);
        return jSONObject;
    }

    private JSONObject i() throws JSONException {
        String deviceToken = MessageSharedPrefs.getInstance(this.f).getDeviceToken();
        String utdid = UmengMessageDeviceConfig.getUtdid(this.f);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = this.b;
        jSONObject2.put("umid", UmengMessageDeviceConfig.getUmid(this.f));
        jSONObject.put("header", jSONObject2);
        jSONObject.put("utdid", utdid);
        jSONObject.put("device_token", deviceToken);
        return jSONObject;
    }

    private JSONObject j() throws JSONException {
        String deviceToken = MessageSharedPrefs.getInstance(this.f).getDeviceToken();
        String utdid = UmengMessageDeviceConfig.getUtdid(this.f);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("appkey", PushAgent.getInstance(this.f).getMessageAppkey());
        jSONObject.put("utdid", utdid);
        jSONObject.put("device_token", deviceToken);
        return jSONObject;
    }

    public void updateHeader() {
        com.umeng.message.common.b bVar = new com.umeng.message.common.b();
        bVar.b(this.f, new String[0]);
        bVar.a(this.f, PushAgent.getInstance(this.f).getMessageAppkey(), PushAgent.getInstance(this.f).getMessageChannel());
        this.b = new JSONObject();
        try {
            bVar.b(this.b);
        } catch (Exception e) {
            e.printStackTrace();
        }
        bVar = new com.umeng.message.common.b();
        bVar.c(this.f, new String[0]);
        bVar.a(this.f, PushAgent.getInstance(this.f).getMessageAppkey(), PushAgent.getInstance(this.f).getMessageChannel());
        this.c = new JSONObject();
        try {
            bVar.c(this.c);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }
}
