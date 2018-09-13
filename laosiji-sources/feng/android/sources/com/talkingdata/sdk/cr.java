package com.talkingdata.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* compiled from: td */
class cr extends Handler {
    final /* synthetic */ cq this$0;

    cr(cq cqVar, Looper looper) {
        this.this$0 = cqVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.this$0.b();
                return;
            case 2:
                this.this$0.c();
                return;
            case 3:
                this.this$0.d();
                return;
            case 4:
                this.this$0.d();
                this.this$0.b();
                this.this$0.a(2, 600000);
                return;
            default:
                return;
        }
    }
}
