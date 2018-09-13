package com.talkingdata.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.talkingdata.sdk.zz.a;

/* compiled from: td */
final class p extends Handler {
    p(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            a aVar = (a) message.obj;
            if (ab.n == 1) {
                a aVar2 = new a();
                aVar2.paraMap.put("apiType", Integer.valueOf(11));
                aVar2.paraMap.put("occurTime", String.valueOf(System.currentTimeMillis()));
                aVar2.paraMap.put("sessionEnd", Integer.valueOf(1));
                aVar2.paraMap.put("service", aVar);
                Message.obtain(zz.c(), 102, aVar2).sendToTarget();
                ab.J.set(true);
                ab.n = 2;
            }
        } catch (Throwable e) {
            cs.postSDKError(e);
        }
    }
}
