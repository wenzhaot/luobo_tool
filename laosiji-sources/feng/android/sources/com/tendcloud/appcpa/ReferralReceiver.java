package com.tendcloud.appcpa;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.talkingdata.sdk.ab;
import com.talkingdata.sdk.zz;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.net.URLDecoder;
import java.util.Map;
import java.util.TreeMap;

/* compiled from: td */
public class ReferralReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                extras.containsKey(null);
            }
            if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
                String stringExtra = intent.getStringExtra("referrer");
                if (stringExtra != null && stringExtra.length() != 0) {
                    Log.d("install_referer", stringExtra);
                    try {
                        stringExtra = URLDecoder.decode(stringExtra, "UTF-8");
                        try {
                            if (ab.g == null) {
                                ab.g = StubApp.getOrigApplicationContext(context.getApplicationContext());
                            }
                            a aVar = new a();
                            aVar.paraMap.put("domain", PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
                            aVar.paraMap.put("apiType", Integer.valueOf(1));
                            aVar.paraMap.put(AuthActivity.ACTION_KEY, "install");
                            aVar.paraMap.put("service", com.talkingdata.sdk.a.TRACKING);
                            Map treeMap = new TreeMap();
                            treeMap.put("referer", stringExtra);
                            aVar.paraMap.put("data", treeMap);
                            zz.c().obtainMessage(102, aVar).sendToTarget();
                        } catch (Throwable th) {
                        }
                    } catch (Throwable th2) {
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
