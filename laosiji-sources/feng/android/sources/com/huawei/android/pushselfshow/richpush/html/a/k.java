package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.d.a;
import org.json.JSONObject;

public class k implements h {
    private NativeToJsMessageQueue a;
    private String b;
    private Context c;
    private String d = null;

    public k(Context context) {
        c.e("PushSelfShowLog", "init VideoPlayer");
        this.c = context;
    }

    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ed A:{Splitter: B:6:0x001a, ExcHandler: java.lang.Exception (r0_24 'e' java.lang.Throwable)} */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x00ed A:{Splitter: B:6:0x001a, ExcHandler: java.lang.Exception (r0_24 'e' java.lang.Throwable)} */
    /* JADX WARNING: Failed to process nested try/catch */
    /* JADX WARNING: Missing block: B:26:0x00ca, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:27:0x00cb, code:
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "startPlaying failed ", r0);
            r6.a.a(r6.b, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
     */
    /* JADX WARNING: Missing block: B:31:0x00ed, code:
            r0 = move-exception;
     */
    /* JADX WARNING: Missing block: B:32:0x00ee, code:
            com.huawei.android.pushagent.a.a.c.d("PushSelfShowLog", "startPlaying failed ", r0);
            r6.a.a(r6.b, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
     */
    /* JADX WARNING: Missing block: B:42:?, code:
            return;
     */
    /* JADX WARNING: Missing block: B:43:?, code:
            return;
     */
    private void a(org.json.JSONObject r7) {
        /*
        r6 = this;
        r5 = 0;
        r0 = r6.a;
        if (r0 != 0) goto L_0x000f;
    L_0x0005:
        r0 = "PushSelfShowLog";
        r1 = "jsMessageQueue is null while run into Video Player exec";
        com.huawei.android.pushagent.a.a.c.a(r0, r1);
    L_0x000e:
        return;
    L_0x000f:
        if (r7 == 0) goto L_0x0145;
    L_0x0011:
        r0 = "url";
        r0 = r7.has(r0);
        if (r0 == 0) goto L_0x0145;
    L_0x001a:
        r0 = "url";
        r0 = r7.getString(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.a;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r1.a();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r1, r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r1 == 0) goto L_0x011c;
    L_0x002d:
        r2 = r1.length();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r2 <= 0) goto L_0x011c;
    L_0x0033:
        r6.d = r1;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = "video/*";
        r0 = "mime-type";
        r0 = r7.has(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r0 == 0) goto L_0x006c;
    L_0x0041:
        r0 = "mime-type";
        r0 = r7.getString(r0);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3.<init>();	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r4 = "the custom mimetype is ";
        r3 = r3.append(r4);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3 = r3.append(r0);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        com.huawei.android.pushagent.a.a.c.e(r2, r3);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        r2 = "video/";
        r2 = r0.startsWith(r2);	 Catch:{ JSONException -> 0x00e2, Exception -> 0x0105 }
        if (r2 == 0) goto L_0x0153;
    L_0x006b:
        r1 = r0;
    L_0x006c:
        r0 = new android.content.Intent;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = "android.intent.action.VIEW";
        r0.<init>(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = r6.d;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = android.net.Uri.parse(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0.setDataAndType(r2, r1);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = "package-name";
        r1 = r7.has(r1);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        if (r1 == 0) goto L_0x00b6;
    L_0x0086:
        r1 = "package-name";
        r1 = r7.getString(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = "PushSelfShowLog";
        r3 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3.<init>();	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r4 = "the custom packageName is ";
        r3 = r3.append(r4);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3 = r3.append(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r3 = r3.toString();	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        com.huawei.android.pushagent.a.a.c.e(r2, r3);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = r6.c;	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r2, r0);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        r2 = r2.contains(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
        if (r2 == 0) goto L_0x00b6;
    L_0x00b3:
        r0.setPackage(r1);	 Catch:{ JSONException -> 0x0111, Exception -> 0x00ed }
    L_0x00b6:
        r1 = r6.c;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1.startActivity(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r6.a;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.b;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.OK;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r3 = "success";
        r4 = 0;
        r0.a(r1, r2, r3, r4);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x000e;
    L_0x00ca:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "startPlaying failed ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        r0 = r6.a;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION;
        r3 = "error";
        r0.a(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x00e2:
        r0 = move-exception;
        r0 = "PushSelfShowLog";
        r2 = "get mime-type error";
        com.huawei.android.pushagent.a.a.c.e(r0, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x006c;
    L_0x00ed:
        r0 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "startPlaying failed ";
        com.huawei.android.pushagent.a.a.c.d(r1, r2, r0);
        r0 = r6.a;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION;
        r3 = "error";
        r0.a(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x0105:
        r0 = move-exception;
        r0 = "PushSelfShowLog";
        r2 = "get mime-type error";
        com.huawei.android.pushagent.a.a.c.e(r0, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x006c;
    L_0x0111:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "get packageName error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x00b6;
    L_0x011c:
        r1 = "PushSelfShowLog";
        r2 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2.<init>();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r2.append(r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = "File not exist";
        r0 = r0.append(r2);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r0.toString();	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        com.huawei.android.pushagent.a.a.c.e(r1, r0);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r0 = r6.a;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r1 = r6.b;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_ONLY_SUPPORT_HTTP;	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        r3 = "error";
        r4 = 0;
        r0.a(r1, r2, r3, r4);	 Catch:{ JSONException -> 0x00ca, Exception -> 0x00ed }
        goto L_0x000e;
    L_0x0145:
        r0 = r6.a;
        r1 = r6.b;
        r2 = com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION;
        r3 = "error";
        r0.a(r1, r2, r3, r5);
        goto L_0x000e;
    L_0x0153:
        r0 = r1;
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a.k.a(org.json.JSONObject):void");
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into Video Player exec");
            return;
        }
        this.a = nativeToJsMessageQueue;
        if ("playVideo".equals(str)) {
            d();
            if (str2 != null) {
                this.b = str2;
                a(jSONObject);
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
            return;
        }
        nativeToJsMessageQueue.a(str2, a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
    }

    public void b() {
    }

    public void c() {
        d();
    }

    public void d() {
        this.b = null;
        this.d = null;
    }
}
