package com.talkingdata.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: td */
class dg extends Handler {
    final /* synthetic */ de this$0;

    dg(de deVar, Looper looper) {
        this.this$0 = deVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            switch (message.what) {
                case 5:
                    if (message.obj != null && (message.obj instanceof a)) {
                        this.this$0.a((a) message.obj);
                        break;
                    }
            }
            this.this$0.d();
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }
}
