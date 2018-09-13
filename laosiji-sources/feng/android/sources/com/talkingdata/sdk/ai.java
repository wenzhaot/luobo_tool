package com.talkingdata.sdk;

import android.os.Message;
import com.talkingdata.sdk.zz.a;

/* compiled from: td */
final class ai implements Runnable {
    final /* synthetic */ a val$features;

    ai(a aVar) {
        this.val$features = aVar;
    }

    public void run() {
        try {
            a aVar = new a();
            aVar.paraMap.put("apiType", Integer.valueOf(10));
            aVar.paraMap.put("occurTime", Long.valueOf(System.currentTimeMillis()));
            aVar.paraMap.put("service", this.val$features);
            Message.obtain(zz.c(), 102, aVar).sendToTarget();
        } catch (Throwable th) {
        }
    }
}
