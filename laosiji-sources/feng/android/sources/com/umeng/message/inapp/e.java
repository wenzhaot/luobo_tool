package com.umeng.message.inapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MsgConstant;
import com.umeng.message.UTrack;
import com.umeng.message.common.d;
import com.umeng.message.common.impl.json.JUtrack;
import com.umeng.message.common.inter.ITagManager;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.message.proguard.h;
import java.io.File;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UmengInAppMessageTracker */
class e {
    private static final String a = e.class.getName();
    private static boolean c = false;
    @SuppressLint({"StaticFieldLeak"})
    private static e d;
    private Context b;

    private e(Context context) {
        this.b = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public static e a(Context context) {
        if (d == null) {
            synchronized (e.class) {
                if (d == null) {
                    d = new e(StubApp.getOrigApplicationContext(context.getApplicationContext()));
                }
            }
        }
        return d;
    }

    void a(final IUmengInAppMessageCallback iUmengInAppMessageCallback) {
        c();
        d.a(new Runnable() {
            public void run() {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(e.a, 2, "get splash message begin");
                try {
                    JSONObject sendRequest = JUtrack.sendRequest(e.this.b(), MsgConstant.SPLASH_MSG_ENDPOINT);
                    if (sendRequest != null && TextUtils.equals(sendRequest.getString("success"), ITagManager.SUCCESS)) {
                        UMLog uMLog2 = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(e.a, 2, "get splash message success" + sendRequest);
                        sendRequest = sendRequest.getJSONObject("data");
                        InAppMessageManager.b = sendRequest.getInt("pduration") * 1000;
                        InAppMessageManager.c = sendRequest.getInt("sduration") * 1000;
                        iUmengInAppMessageCallback.onSplashMessage(new UInAppMessage(sendRequest.getJSONObject("launch")));
                        InAppMessageManager.getInstance(e.this.b).c();
                    } else if (sendRequest != null && TextUtils.equals(sendRequest.getString("success"), "fail") && TextUtils.equals(sendRequest.getString("error"), "no message")) {
                        Object e = InAppMessageManager.getInstance(e.this.b).e();
                        if (!TextUtils.isEmpty(e)) {
                            UInAppMessage uInAppMessage;
                            try {
                                uInAppMessage = new UInAppMessage(new JSONObject(e));
                            } catch (JSONException e2) {
                                e2.printStackTrace();
                                uInAppMessage = null;
                            }
                            if (uInAppMessage != null) {
                                InAppMessageManager.getInstance(e.this.b).a(new File(h.d(e.this.b, uInAppMessage.msg_id)));
                                InAppMessageManager.getInstance(e.this.b).a(null);
                            }
                        }
                    } else {
                        iUmengInAppMessageCallback.onSplashMessage(null);
                    }
                } catch (Exception e3) {
                    iUmengInAppMessageCallback.onSplashMessage(null);
                    e3.printStackTrace();
                }
            }
        });
    }

    void a(final String str, final IUmengInAppMessageCallback iUmengInAppMessageCallback) {
        c();
        d.a(new Runnable() {
            public void run() {
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(e.a, 2, "get card message begin");
                try {
                    JSONObject a = e.this.b();
                    a.put(MsgConstant.INAPP_LABEL, str);
                    JSONObject sendRequest = JUtrack.sendRequest(a, MsgConstant.CARD_MSG_ENDPOINT);
                    if (sendRequest != null && TextUtils.equals(sendRequest.getString("success"), ITagManager.SUCCESS)) {
                        UMLog uMLog2 = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(e.a, 2, "get card message success" + sendRequest);
                        sendRequest = sendRequest.getJSONObject("data");
                        InAppMessageManager.b = sendRequest.getInt("pduration") * 1000;
                        InAppMessageManager.c = sendRequest.getInt("sduration") * 1000;
                        iUmengInAppMessageCallback.onCardMessage(new UInAppMessage(sendRequest.getJSONObject("card")));
                        InAppMessageManager.getInstance(e.this.b).a(a.optString(MsgConstant.INAPP_LABEL, ""));
                    } else if (sendRequest != null && TextUtils.equals(sendRequest.getString("success"), "fail") && TextUtils.equals(sendRequest.getString("error"), "no message")) {
                        Object c = InAppMessageManager.getInstance(e.this.b).c(str);
                        if (!TextUtils.isEmpty(c)) {
                            UInAppMessage uInAppMessage;
                            try {
                                uInAppMessage = new UInAppMessage(new JSONObject(c));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                uInAppMessage = null;
                            }
                            if (uInAppMessage != null) {
                                InAppMessageManager.getInstance(e.this.b).a(new File(h.d(e.this.b, uInAppMessage.msg_id)));
                                InAppMessageManager.getInstance(e.this.b).a(null, str);
                            }
                        }
                    } else {
                        iUmengInAppMessageCallback.onCardMessage(null);
                    }
                } catch (Exception e2) {
                    iUmengInAppMessageCallback.onCardMessage(null);
                    e2.printStackTrace();
                }
            }
        });
    }

    void a(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        final String str2 = str;
        final int i9 = i;
        final int i10 = i2;
        final int i11 = i3;
        final int i12 = i4;
        final int i13 = i5;
        final int i14 = i6;
        final int i15 = i7;
        final int i16 = i8;
        d.a(new Runnable() {
            public void run() {
                try {
                    UMLog uMLog = UMConfigure.umDebugLog;
                    UMLog.mutlInfo(e.a, 2, "track in app msg begin");
                    JSONObject a = e.this.b(str2, i9, i10, i11, i12, i13, i14, i15, i16);
                    if (a != null && TextUtils.equals(a.getString("success"), ITagManager.SUCCESS)) {
                        uMLog = UMConfigure.umDebugLog;
                        UMLog.mutlInfo(e.a, 2, "track in app msg success");
                    }
                } catch (Exception e) {
                    Exception exception = e;
                    InAppMessageManager.getInstance(e.this.b).a(str2, i9, i10, i11, i12, i13, i14, i15, i16);
                    exception.printStackTrace();
                }
            }
        });
    }

    private JSONObject b() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", UTrack.getInstance(this.b).getHeader());
        if (InAppMessageManager.a) {
            jSONObject.put(MsgConstant.KEY_INAPP_PMODE, PushConstants.PUSH_TYPE_NOTIFY);
        } else {
            jSONObject.put(MsgConstant.KEY_INAPP_PMODE, PushConstants.PUSH_TYPE_THROUGH_MESSAGE);
        }
        return jSONObject;
    }

    private void c() {
        UMLog uMLog;
        if (c) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 2, "sendInAppCacheLog已经在队列里，忽略该请求");
            return;
        }
        c = true;
        uMLog = UMConfigure.umDebugLog;
        UMLog.mutlInfo(a, 2, "sendInAppCacheLog开始");
        d.a(new Runnable() {
            public void run() {
                try {
                    Iterator it = InAppMessageManager.getInstance(e.this.b).j().iterator();
                    while (it.hasNext()) {
                        a aVar = (a) it.next();
                        JSONObject a = e.this.b(aVar.b, aVar.c, aVar.d, aVar.e, aVar.f, aVar.g, aVar.h, aVar.i, aVar.j);
                        if (a != null && TextUtils.equals(a.getString("success"), ITagManager.SUCCESS)) {
                            InAppMessageManager.getInstance(e.this.b).h(aVar.b);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    e.c = false;
                }
            }
        });
    }

    private JSONObject b(String str, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) throws Exception {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("header", UTrack.getInstance(this.b).getHeader());
        jSONObject.put("msg_id", str);
        jSONObject.put(MsgConstant.INAPP_MSG_TYPE, i);
        jSONObject.put(MsgConstant.INAPP_NUM_DISPLAY, i2);
        jSONObject.put(MsgConstant.INAPP_NUM_OPEN_FULL, i3);
        jSONObject.put(MsgConstant.INAPP_NUM_OPEN_TOP, i4);
        jSONObject.put(MsgConstant.INAPP_NUM_OPEN_BUTTOM, i5);
        jSONObject.put(MsgConstant.INAPP_NUM_CLOSE, i6);
        jSONObject.put(MsgConstant.INAPP_NUM_DURATION, i7);
        jSONObject.put(MsgConstant.INAPP_NUM_CUSTOM, i8);
        return JUtrack.sendRequest(jSONObject, MsgConstant.STATS_ENDPOINT);
    }
}
