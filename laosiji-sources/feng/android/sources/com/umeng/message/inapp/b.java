package com.umeng.message.inapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;
import com.stub.StubApp;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.entity.UInAppMessage;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Calendar;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: UmengCardMessageBuilder */
final class b implements IUmengInAppMessageCallback, ImageLoaderCallback {
    private static final String a = b.class.getName();
    private static final int f = 10;
    private Context b;
    private String c;
    private boolean d;
    private UInAppMessage e;
    private IUmengInAppMsgCloseCallback g;

    public b(Activity activity, String str, IUmengInAppMsgCloseCallback iUmengInAppMsgCloseCallback) {
        this.b = activity;
        this.c = str;
        this.g = iUmengInAppMsgCloseCallback;
    }

    public b(Context context, String str) {
        this.b = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.c = str;
        this.d = true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0066  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x004a  */
    private boolean a(java.lang.String r5) {
        /*
        r4 = this;
        r0 = 1;
        r1 = r4.b;
        r1 = com.umeng.message.common.UmengMessageDeviceConfig.getAppVersionCode(r1);
        r2 = r4.b;
        r2 = com.umeng.message.inapp.InAppMessageManager.getInstance(r2);
        r2 = r2.g();
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0023;
    L_0x0017:
        r1 = r4.b;
        r1 = com.umeng.message.inapp.InAppMessageManager.getInstance(r1);
        r2 = "";
        r1.d(r2);
    L_0x0023:
        r1 = r4.b;
        r1 = com.umeng.message.inapp.InAppMessageManager.getInstance(r1);
        r2 = r4.b;
        r2 = com.umeng.message.common.UmengMessageDeviceConfig.getAppVersionCode(r2);
        r1.e(r2);
        r1 = r4.b;
        r1 = com.umeng.message.inapp.InAppMessageManager.getInstance(r1);
        r3 = r1.f();
        r2 = 0;
        r1 = android.text.TextUtils.isEmpty(r3);
        if (r1 != 0) goto L_0x0064;
    L_0x0043:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0060 }
        r1.<init>(r3);	 Catch:{ JSONException -> 0x0060 }
    L_0x0048:
        if (r1 != 0) goto L_0x0066;
    L_0x004a:
        r1 = new org.json.JSONArray;
        r1.<init>();
        r1.put(r5);
        r2 = r4.b;
        r2 = com.umeng.message.inapp.InAppMessageManager.getInstance(r2);
        r1 = r1.toString();
        r2.d(r1);
    L_0x005f:
        return r0;
    L_0x0060:
        r1 = move-exception;
        r1.printStackTrace();
    L_0x0064:
        r1 = r2;
        goto L_0x0048;
    L_0x0066:
        r2 = r4.a(r1, r5);
        if (r2 != 0) goto L_0x005f;
    L_0x006c:
        r2 = r1.length();
        r3 = 10;
        if (r2 >= r3) goto L_0x0085;
    L_0x0074:
        r1.put(r5);
        r2 = r4.b;
        r2 = com.umeng.message.inapp.InAppMessageManager.getInstance(r2);
        r1 = r1.toString();
        r2.d(r1);
        goto L_0x005f;
    L_0x0085:
        r0 = 0;
        goto L_0x005f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.b.a(java.lang.String):boolean");
    }

    private boolean a(JSONArray jSONArray, String str) {
        int i = 0;
        while (i < jSONArray.length()) {
            try {
                if (jSONArray.getString(i).equals(str)) {
                    return true;
                }
                i++;
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    void a() {
        UMLog uMLog;
        if (TextUtils.isEmpty(this.c.trim())) {
            if (PushAgent.DEBUG) {
                Toast.makeText(this.b, "插屏消息的标签不能为空", 1).show();
            }
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "插屏消息的标签不能为空");
        } else if (!a(this.c)) {
            uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(a, 0, "插屏消息的最大标签数为 10");
        } else if (InAppMessageManager.a) {
            e.a(this.b).a(this.c, this);
        } else if (System.currentTimeMillis() - InAppMessageManager.getInstance(this.b).b(this.c) > ((long) InAppMessageManager.b)) {
            e.a(this.b).a(this.c, this);
        } else {
            onCardMessage(null);
        }
    }

    private void a(Bitmap bitmap) {
        if (bitmap != null) {
            try {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] toByteArray = byteArrayOutputStream.toByteArray();
                UmengCardMessage umengCardMessage = new UmengCardMessage();
                umengCardMessage.a(this.g);
                Bundle bundle = new Bundle();
                bundle.putString(MsgConstant.INAPP_LABEL, this.c);
                bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_MSG, this.e.getRaw().toString());
                bundle.putByteArray("bitmapByte", toByteArray);
                umengCardMessage.setArguments(bundle);
                umengCardMessage.show(((Activity) this.b).getFragmentManager(), this.c);
                InAppMessageManager.getInstance(this.b).a(this.e.msg_id, 1);
                InAppMessageManager.getInstance(this.b).f(this.c);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void b() {
        try {
            UmengCardMessage umengCardMessage = new UmengCardMessage();
            umengCardMessage.a(this.g);
            Bundle bundle = new Bundle();
            bundle.putString(MsgConstant.INAPP_LABEL, this.c);
            bundle.putString(SocializeProtocolConstants.PROTOCOL_KEY_MSG, this.e.getRaw().toString());
            umengCardMessage.setArguments(bundle);
            umengCardMessage.show(((Activity) this.b).getFragmentManager(), this.c);
            InAppMessageManager.getInstance(this.b).a(this.e.msg_id, 1);
            InAppMessageManager.getInstance(this.b).f(this.c);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSplashMessage(UInAppMessage uInAppMessage) {
    }

    /* JADX WARNING: Removed duplicated region for block: B:22:0x0083  */
    /* JADX WARNING: Removed duplicated region for block: B:5:0x0021 A:{SKIP} */
    public void onCardMessage(com.umeng.message.entity.UInAppMessage r7) {
        /*
        r6 = this;
        r5 = 1;
        r4 = 0;
        r1 = 0;
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r2 = r6.c;
        r2 = r0.c(r2);
        r0 = android.text.TextUtils.isEmpty(r2);
        if (r0 != 0) goto L_0x0081;
    L_0x0015:
        r0 = new com.umeng.message.entity.UInAppMessage;	 Catch:{ JSONException -> 0x007d }
        r3 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x007d }
        r3.<init>(r2);	 Catch:{ JSONException -> 0x007d }
        r0.<init>(r3);	 Catch:{ JSONException -> 0x007d }
    L_0x001f:
        if (r7 == 0) goto L_0x0083;
    L_0x0021:
        if (r0 == 0) goto L_0x0043;
    L_0x0023:
        r1 = r7.msg_id;
        r2 = r0.msg_id;
        r1 = r1.equals(r2);
        if (r1 != 0) goto L_0x0043;
    L_0x002d:
        r1 = new java.io.File;
        r2 = r6.b;
        r0 = r0.msg_id;
        r0 = com.umeng.message.proguard.h.d(r2, r0);
        r1.<init>(r0);
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r0.a(r1);
    L_0x0043:
        r6.e = r7;
    L_0x0045:
        r0 = r6.e;
        r0 = r0.show_type;
        if (r0 != r5) goto L_0x0060;
    L_0x004b:
        r0 = r6.c;
        r0 = r6.b(r0);
        if (r0 != 0) goto L_0x0060;
    L_0x0053:
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r1 = r6.e;
        r1 = r1.msg_id;
        r0.a(r1, r4);
    L_0x0060:
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r1 = r6.e;
        r0 = r0.b(r1);
        if (r0 == 0) goto L_0x007c;
    L_0x006e:
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r1 = r6.e;
        r0 = r0.c(r1);
        if (r0 != 0) goto L_0x0088;
    L_0x007c:
        return;
    L_0x007d:
        r0 = move-exception;
        r0.printStackTrace();
    L_0x0081:
        r0 = r1;
        goto L_0x001f;
    L_0x0083:
        if (r0 == 0) goto L_0x007c;
    L_0x0085:
        r6.e = r0;
        goto L_0x0045;
    L_0x0088:
        r0 = r6.e;
        r0 = r0.msg_type;
        r1 = 5;
        if (r0 == r1) goto L_0x0096;
    L_0x008f:
        r0 = r6.e;
        r0 = r0.msg_type;
        r1 = 6;
        if (r0 != r1) goto L_0x00a7;
    L_0x0096:
        r0 = r6.b;
        r0 = com.umeng.message.inapp.InAppMessageManager.getInstance(r0);
        r1 = r6.e;
        r2 = r6.c;
        r0.a(r1, r2);
        r6.b();
        goto L_0x007c;
    L_0x00a7:
        r0 = new com.umeng.message.inapp.UImageLoadTask;
        r1 = r6.b;
        r2 = r6.e;
        r0.<init>(r1, r2);
        r0.a(r6);
        r1 = new java.lang.String[r5];
        r2 = r6.e;
        r2 = r2.image_url;
        r1[r4] = r2;
        r0.execute(r1);
        goto L_0x007c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.umeng.message.inapp.b.onCardMessage(com.umeng.message.entity.UInAppMessage):void");
    }

    public void onLoadImage(Bitmap[] bitmapArr) {
        if (!this.d) {
            a(bitmapArr[0]);
        }
        InAppMessageManager.getInstance(this.b).a(this.e, this.c);
    }

    private boolean b(String str) {
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(InAppMessageManager.getInstance(this.b).g(str));
        Calendar instance2 = Calendar.getInstance();
        if (instance.get(6) == instance2.get(6) && instance.get(1) == instance2.get(1)) {
            return true;
        }
        return false;
    }
}
