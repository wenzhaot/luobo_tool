package com.taobao.agoo.a;

import android.content.Context;
import android.text.TextUtils;
import com.stub.StubApp;
import com.taobao.accs.base.AccsAbstractDataListener;
import com.taobao.accs.base.TaoBaseService.ExtraInfo;
import com.taobao.accs.client.GlobalClientInfo;
import com.taobao.accs.utl.e;
import com.taobao.agoo.ICallback;
import com.taobao.agoo.a.a.a;
import java.util.HashMap;
import java.util.Map;
import org.android.agoo.common.Config;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: Taobao */
public class b extends AccsAbstractDataListener {
    public static a b;
    public Map<String, ICallback> a = new HashMap();

    public b(Context context) {
        if (b == null) {
            b = new a(StubApp.getOrigApplicationContext(context.getApplicationContext()));
        }
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    public void onResponse(java.lang.String r8, java.lang.String r9, int r10, byte[] r11, com.taobao.accs.base.TaoBaseService.ExtraInfo r12) {
        /*
        r7 = this;
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);	 Catch:{ Throwable -> 0x00d8 }
        if (r0 == 0) goto L_0x016e;
    L_0x0009:
        r0 = r7.a;	 Catch:{ Throwable -> 0x00d8 }
        r0 = r0.get(r9);	 Catch:{ Throwable -> 0x00d8 }
        r0 = (com.taobao.agoo.ICallback) r0;	 Catch:{ Throwable -> 0x00d8 }
        r1 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r10 != r1) goto L_0x0162;
    L_0x0015:
        r1 = new java.lang.String;	 Catch:{ Throwable -> 0x00d8 }
        r2 = "utf-8";
        r1.<init>(r11, r2);	 Catch:{ Throwable -> 0x00d8 }
        r2 = "RequestListener";
        r3 = "RequestListener onResponse";
        r4 = 6;
        r4 = new java.lang.Object[r4];	 Catch:{ Throwable -> 0x00d8 }
        r5 = 0;
        r6 = "dataId";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00d8 }
        r5 = 1;
        r4[r5] = r9;	 Catch:{ Throwable -> 0x00d8 }
        r5 = 2;
        r6 = "listener";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00d8 }
        r5 = 3;
        r4[r5] = r0;	 Catch:{ Throwable -> 0x00d8 }
        r5 = 4;
        r6 = "json";
        r4[r5] = r6;	 Catch:{ Throwable -> 0x00d8 }
        r5 = 5;
        r4[r5] = r1;	 Catch:{ Throwable -> 0x00d8 }
        com.taobao.accs.utl.ALog.i(r2, r3, r4);	 Catch:{ Throwable -> 0x00d8 }
        r2 = new org.json.JSONObject;	 Catch:{ Throwable -> 0x00d8 }
        r2.<init>(r1);	 Catch:{ Throwable -> 0x00d8 }
        r1 = "resultCode";
        r3 = 0;
        r1 = com.taobao.accs.utl.e.a(r2, r1, r3);	 Catch:{ Throwable -> 0x00d8 }
        r3 = "cmd";
        r4 = 0;
        r3 = com.taobao.accs.utl.e.a(r2, r3, r4);	 Catch:{ Throwable -> 0x00d8 }
        r4 = "success";
        r4 = r4.equals(r1);	 Catch:{ Throwable -> 0x00d8 }
        if (r4 != 0) goto L_0x007d;
    L_0x0062:
        if (r0 == 0) goto L_0x006e;
    L_0x0064:
        r1 = java.lang.String.valueOf(r1);	 Catch:{ Throwable -> 0x00d8 }
        r2 = "agoo server error";
        r0.onFailure(r1, r2);	 Catch:{ Throwable -> 0x00d8 }
    L_0x006e:
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x0077:
        r0 = r7.a;
        r0.remove(r9);
    L_0x007c:
        return;
    L_0x007d:
        r1 = "register";
        r1 = r1.equals(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r1 == 0) goto L_0x00f4;
    L_0x0086:
        r1 = "deviceId";
        r3 = 0;
        r1 = com.taobao.accs.utl.e.a(r2, r1, r3);	 Catch:{ Throwable -> 0x00d8 }
        r2 = android.text.TextUtils.isEmpty(r1);	 Catch:{ Throwable -> 0x00d8 }
        if (r2 == 0) goto L_0x00ae;
    L_0x0094:
        if (r0 == 0) goto L_0x009f;
    L_0x0096:
        r1 = "";
        r2 = "agoo server error deviceid null";
        r0.onFailure(r1, r2);	 Catch:{ Throwable -> 0x00d8 }
    L_0x009f:
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x00a8:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x00ae:
        r2 = com.taobao.accs.client.GlobalClientInfo.getContext();	 Catch:{ Throwable -> 0x00d8 }
        org.android.agoo.common.Config.a(r2, r1);	 Catch:{ Throwable -> 0x00d8 }
        r2 = b;	 Catch:{ Throwable -> 0x00d8 }
        r3 = com.taobao.accs.client.GlobalClientInfo.getContext();	 Catch:{ Throwable -> 0x00d8 }
        r3 = r3.getPackageName();	 Catch:{ Throwable -> 0x00d8 }
        r2.a(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r0 == 0) goto L_0x009f;
    L_0x00c4:
        r2 = r0 instanceof com.taobao.agoo.IRegister;	 Catch:{ Throwable -> 0x00d8 }
        if (r2 == 0) goto L_0x009f;
    L_0x00c8:
        r2 = "Agoo_AppStore";
        r3 = com.taobao.accs.client.GlobalClientInfo.getContext();	 Catch:{ Throwable -> 0x00d8 }
        com.taobao.accs.utl.UtilityImpl.saveUtdid(r2, r3);	 Catch:{ Throwable -> 0x00d8 }
        r0 = (com.taobao.agoo.IRegister) r0;	 Catch:{ Throwable -> 0x00d8 }
        r0.onSuccess(r1);	 Catch:{ Throwable -> 0x00d8 }
        goto L_0x009f;
    L_0x00d8:
        r0 = move-exception;
        r1 = "RequestListener";
        r2 = "onResponse";
        r3 = 0;
        r3 = new java.lang.Object[r3];	 Catch:{ all -> 0x017e }
        com.taobao.accs.utl.ALog.e(r1, r2, r0, r3);	 Catch:{ all -> 0x017e }
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x00ee:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x00f4:
        r1 = "setAlias";
        r1 = r1.equals(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r1 == 0) goto L_0x0110;
    L_0x00fd:
        r7.a(r2, r0);	 Catch:{ Throwable -> 0x00d8 }
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x0109:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x0110:
        r1 = "removeAlias";
        r1 = r1.equals(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r1 == 0) goto L_0x013b;
    L_0x0119:
        r1 = com.taobao.accs.client.GlobalClientInfo.getContext();	 Catch:{ Throwable -> 0x00d8 }
        r2 = 0;
        org.android.agoo.common.Config.b(r1, r2);	 Catch:{ Throwable -> 0x00d8 }
        if (r0 == 0) goto L_0x0126;
    L_0x0123:
        r0.onSuccess();	 Catch:{ Throwable -> 0x00d8 }
    L_0x0126:
        r0 = b;	 Catch:{ Throwable -> 0x00d8 }
        r0.a();	 Catch:{ Throwable -> 0x00d8 }
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x0134:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x013b:
        r1 = "enablePush";
        r1 = r1.equals(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r1 != 0) goto L_0x014d;
    L_0x0144:
        r1 = "disablePush";
        r1 = r1.equals(r3);	 Catch:{ Throwable -> 0x00d8 }
        if (r1 == 0) goto L_0x016e;
    L_0x014d:
        if (r0 == 0) goto L_0x0152;
    L_0x014f:
        r0.onSuccess();	 Catch:{ Throwable -> 0x00d8 }
    L_0x0152:
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x015b:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x0162:
        if (r0 == 0) goto L_0x016e;
    L_0x0164:
        r1 = java.lang.String.valueOf(r10);	 Catch:{ Throwable -> 0x00d8 }
        r2 = "accs channel error";
        r0.onFailure(r1, r2);	 Catch:{ Throwable -> 0x00d8 }
    L_0x016e:
        r0 = "AgooDeviceCmd";
        r0 = r0.equals(r8);
        if (r0 == 0) goto L_0x007c;
    L_0x0177:
        r0 = r7.a;
        r0.remove(r9);
        goto L_0x007c;
    L_0x017e:
        r0 = move-exception;
        r1 = "AgooDeviceCmd";
        r1 = r1.equals(r8);
        if (r1 == 0) goto L_0x018d;
    L_0x0188:
        r1 = r7.a;
        r1.remove(r9);
    L_0x018d:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.taobao.agoo.a.b.onResponse(java.lang.String, java.lang.String, int, byte[], com.taobao.accs.base.TaoBaseService$ExtraInfo):void");
    }

    public void onData(String str, String str2, String str3, byte[] bArr, ExtraInfo extraInfo) {
    }

    public void onBind(String str, int i, ExtraInfo extraInfo) {
    }

    public void onUnbind(String str, int i, ExtraInfo extraInfo) {
    }

    public void onSendData(String str, String str2, int i, ExtraInfo extraInfo) {
    }

    private void a(JSONObject jSONObject, ICallback iCallback) throws JSONException {
        Object a = e.a(jSONObject, a.JSON_PUSH_USER_TOKEN, null);
        if (!TextUtils.isEmpty(a)) {
            Config.b(GlobalClientInfo.getContext(), a);
            if (iCallback != null) {
                iCallback.onSuccess();
                b.c(iCallback.extra);
            }
        } else if (iCallback != null) {
            iCallback.onFailure("", "agoo server error-pushtoken null");
        }
    }
}
