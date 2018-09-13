package com.baidu.location.b;

import android.os.Handler;
import android.os.Message;

class i extends Handler {
    final /* synthetic */ h a;

    i(h hVar) {
        this.a = hVar;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.a.d();
                return;
            default:
                return;
        }
    }
}
