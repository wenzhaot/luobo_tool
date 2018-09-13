package com.huawei.android.pushselfshow;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.huawei.android.pushagent.PushReceiver.ACTION;
import com.huawei.android.pushagent.PushReceiver.KEY_TYPE;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.c.d;
import com.huawei.android.pushselfshow.permission.RequestPermissionsActivity;
import com.huawei.android.pushselfshow.utils.b.b;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.File;
import java.util.ArrayList;
import org.json.JSONArray;

public class SelfShowReceiver {

    static class a extends Thread {
        Context a;
        String b;

        public a(Context context, String str) {
            this.a = context;
            this.b = str;
        }

        public void run() {
            ArrayList a = com.huawei.android.pushselfshow.utils.a.a.a(this.a, this.b);
            int size = a.size();
            c.e("PushSelfShowLog", "receive package add ,arrSize " + size);
            for (int i = 0; i < size; i++) {
                com.huawei.android.pushselfshow.utils.a.a(this.a, "16", (String) a.get(i), PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
            }
            if (size > 0) {
                com.huawei.android.pushselfshow.utils.a.a.b(this.a, this.b);
            }
            com.huawei.android.pushselfshow.utils.a.b(new File(b.a(this.a)));
        }
    }

    public void a(Context context, Intent intent, com.huawei.android.pushselfshow.b.a aVar) {
        c.a("PushSelfShowLog", "receive a selfshow message ,the type is" + aVar.p);
        if (com.huawei.android.pushselfshow.a.a.a(aVar.p)) {
            long b = com.huawei.android.pushselfshow.utils.a.b(aVar.l);
            if (b == 0) {
                new d(context, aVar).start();
                return;
            }
            c.a("PushSelfShowLog", "waiting ……");
            intent.setPackage(context.getPackageName());
            com.huawei.android.pushselfshow.utils.a.a(context, intent, b);
            return;
        }
        com.huawei.android.pushselfshow.utils.a.a(context, "3", aVar);
    }

    public void a(Context context, Intent intent, String str, com.huawei.android.pushselfshow.b.a aVar, int i) {
        c.a("PushSelfShowLog", "receive a selfshow userhandle message");
        if ("-1".equals(str)) {
            com.huawei.android.pushselfshow.c.b.a(context, i);
        } else {
            com.huawei.android.pushselfshow.c.b.a(context, intent);
        }
        if (PushConstants.PUSH_TYPE_THROUGH_MESSAGE.equals(str)) {
            new com.huawei.android.pushselfshow.a.a(context, aVar).a();
            if (aVar.o != null) {
                try {
                    JSONArray jSONArray = new JSONArray(aVar.o);
                    Intent intent2 = new Intent(ACTION.ACTION_NOTIFICATION_MSG_CLICK);
                    intent2.putExtra(KEY_TYPE.PUSH_KEY_CLICK, jSONArray.toString()).setPackage(aVar.n).setFlags(32);
                    context.sendBroadcast(intent2);
                } catch (Exception e) {
                    c.d("PushSelfShowLog", "message.extras is not a json format,err info " + e.toString());
                }
            }
        }
        if (!TextUtils.isEmpty(aVar.a)) {
            String str2 = aVar.n + aVar.a;
            c.a("PushSelfShowLog", "groupMap key is " + str2);
            if (com.huawei.android.pushselfshow.c.b.a.containsKey(str2)) {
                com.huawei.android.pushselfshow.c.b.a.remove(str2);
                c.a("PushSelfShowLog", "after remove, groupMap.size is:" + com.huawei.android.pushselfshow.c.b.a.get(str2));
            }
        }
        com.huawei.android.pushselfshow.utils.a.a(context, str, aVar);
    }

    public void onReceive(Context context, Intent intent) {
        int i = 0;
        if (context == null || intent == null) {
            try {
                c.a("PushSelfShowLog", "enter SelfShowReceiver receiver, context or intent is null");
                return;
            } catch (Throwable e) {
                c.a("PushSelfShowLog", e.toString(), e);
                return;
            }
        }
        c.a(context);
        String action = intent.getAction();
        if ("android.intent.action.PACKAGE_ADDED".equals(action)) {
            Uri data = intent.getData();
            if (data != null) {
                Object schemeSpecificPart = data.getSchemeSpecificPart();
                c.e("PushSelfShowLog", "receive package add ,the pkgName is " + schemeSpecificPart);
                if (!TextUtils.isEmpty(schemeSpecificPart)) {
                    new a(context, schemeSpecificPart).start();
                }
            }
        } else if (!"com.huawei.intent.action.PUSH".equals(action)) {
        } else {
            if (RequestPermissionsActivity.a(context)) {
                c.b("PushSelfShowLog", "needStartPermissionActivity");
                RequestPermissionsActivity.a(context, intent);
                return;
            }
            String str = null;
            if (intent.hasExtra("selfshow_info")) {
                byte[] byteArrayExtra = intent.getByteArrayExtra("selfshow_info");
                if (intent.hasExtra("selfshow_token")) {
                    byte[] byteArrayExtra2 = intent.getByteArrayExtra("selfshow_token");
                    if (intent.hasExtra("selfshow_event_id")) {
                        str = intent.getStringExtra("selfshow_event_id");
                    }
                    if (intent.hasExtra("selfshow_notify_id")) {
                        i = intent.getIntExtra("selfshow_notify_id", 0);
                        c.b("PushSelfShowLog", "get notifyId:" + i);
                    }
                    com.huawei.android.pushselfshow.b.a aVar = new com.huawei.android.pushselfshow.b.a(byteArrayExtra, byteArrayExtra2);
                    if (aVar.b()) {
                        c.a("PushSelfShowLog", " onReceive the msg id = " + aVar.m + ",and cmd is" + aVar.p + ",and the eventId is " + str);
                        if (str == null) {
                            a(context, intent, aVar);
                        } else {
                            a(context, intent, str, aVar, i);
                        }
                        com.huawei.android.pushselfshow.utils.a.b(new File(b.a(context)));
                        return;
                    }
                    c.a("PushSelfShowLog", "parseMessage failed");
                }
            }
        }
    }
}
