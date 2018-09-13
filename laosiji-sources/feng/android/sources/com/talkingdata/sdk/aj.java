package com.talkingdata.sdk;

import android.app.Activity;
import android.os.Message;
import com.talkingdata.sdk.zz.a;

/* compiled from: td */
final class aj implements Runnable {
    final /* synthetic */ Activity val$act;
    final /* synthetic */ a val$features;

    aj(a aVar, Activity activity) {
        this.val$features = aVar;
        this.val$act = activity;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(11));
            aVar.paraMap.put("service", this.val$features);
            aVar.paraMap.put("pageName", this.val$act != null ? this.val$act.getLocalClassName() : "");
            aVar.paraMap.put("occurTime", String.valueOf(System.currentTimeMillis()));
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
