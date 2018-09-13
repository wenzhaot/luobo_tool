package com.talkingdata.sdk;

import android.os.Message;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.qiniu.android.common.Constants;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;
import java.net.URLEncoder;
import java.util.Map;
import java.util.TreeMap;
import org.json.JSONObject;

/* compiled from: td */
class s implements Runnable {
    final /* synthetic */ zz this$0;
    final /* synthetic */ String val$link;
    final /* synthetic */ a val$service;

    s(zz zzVar, String str, a aVar) {
        this.this$0 = zzVar;
        this.val$link = str;
        this.val$service = aVar;
    }

    public void run() {
        try {
            dl.a().setDeepLink(URLEncoder.encode(this.val$link, Constants.UTF_8));
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(8));
            aVar.paraMap.put("service", this.val$service);
            aVar.paraMap.put("domain", PushConstants.EXTRA_APPLICATION_PENDING_INTENT);
            aVar.paraMap.put(AuthActivity.ACTION_KEY, "deeplink");
            Map treeMap = new TreeMap();
            treeMap.put("link", this.val$link);
            aVar.paraMap.put("data", new JSONObject(treeMap));
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
