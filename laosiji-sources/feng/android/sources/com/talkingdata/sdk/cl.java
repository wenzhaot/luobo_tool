package com.talkingdata.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.HashMap;

/* compiled from: td */
class cl extends Handler {
    final /* synthetic */ ck this$0;

    cl(ck ckVar, Looper looper) {
        this.this$0 = ckVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            ck.t = false;
            HashMap hashMap = (HashMap) message.obj;
            this.this$0.b(String.valueOf(hashMap.get("appId")), String.valueOf(hashMap.get("channelId")), (a) hashMap.get("Features"));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
