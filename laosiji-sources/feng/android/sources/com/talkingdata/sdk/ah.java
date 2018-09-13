package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;
import com.tencent.tauth.AuthActivity;

/* compiled from: td */
final class ah implements Runnable {
    final /* synthetic */ a val$features;

    ah(a aVar) {
        this.val$features = aVar;
    }

    public void run() {
        a aVar = new a();
        aVar.paraMap.put("apiType", Integer.valueOf(1));
        aVar.paraMap.put(AuthActivity.ACTION_KEY, "sendInit");
        aVar.paraMap.put("service", this.val$features);
        Message.obtain(zz.c(), 102, aVar).sendToTarget();
    }
}
