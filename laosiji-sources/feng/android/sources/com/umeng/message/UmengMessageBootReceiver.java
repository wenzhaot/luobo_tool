package com.umeng.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.commonsdk.debug.UMLog;
import com.umeng.message.entity.UMessage;
import com.umeng.message.proguard.m;
import com.umeng.message.proguard.m.c;
import com.umeng.message.proguard.m.d;
import java.util.Iterator;

public class UmengMessageBootReceiver extends BroadcastReceiver {
    private static final String b = UmengMessageBootReceiver.class.getName();
    private static final String c = "android.intent.action.BOOT_COMPLETED";
    Runnable a = new Runnable() {
        public void run() {
            try {
                Iterator it = m.a(UmengMessageBootReceiver.this.d).b().iterator();
                while (it.hasNext()) {
                    c cVar = (c) it.next();
                    if (m.a(UmengMessageBootReceiver.this.d).a(cVar.a) == null && cVar.b.equals(UMessage.DISPLAY_TYPE_NOTIFICATION)) {
                        m.a(UmengMessageBootReceiver.this.d).a(cVar.a, 2, System.currentTimeMillis(), "");
                    }
                }
                Iterator it2 = m.a(UmengMessageBootReceiver.this.d).d().iterator();
                while (it2.hasNext()) {
                    d dVar = (d) it2.next();
                    if (m.a(UmengMessageBootReceiver.this.d).c(dVar.a) == null && dVar.c.equals(UMessage.DISPLAY_TYPE_NOTIFICATION)) {
                        m.a(UmengMessageBootReceiver.this.d).a(dVar.a, dVar.b, "9", System.currentTimeMillis());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                UMLog uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(UmengMessageBootReceiver.b, 2, e.toString());
            }
        }
    };
    private Context d;

    public void onReceive(Context context, Intent intent) {
        try {
            UMLog uMLog = UMConfigure.umDebugLog;
            UMLog.mutlInfo(b, 2, "Boot this system , UmengMessageBootReceiver onReceive()");
            String action = intent.getAction();
            if (action != null && !action.equals("")) {
                uMLog = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 2, "action=" + intent.getAction());
                if (TextUtils.equals(intent.getAction(), c)) {
                    this.d = context;
                    com.umeng.message.common.d.a(this.a);
                }
            }
        } catch (Exception e) {
            if (e != null) {
                UMLog uMLog2 = UMConfigure.umDebugLog;
                UMLog.mutlInfo(b, 0, e.toString());
            }
        }
    }
}
