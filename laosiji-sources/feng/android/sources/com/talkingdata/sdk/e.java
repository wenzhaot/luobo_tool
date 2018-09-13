package com.talkingdata.sdk;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.talkingdata.sdk.zz.a;

/* compiled from: td */
final class e extends Handler {
    e(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        dh.b();
        de.a();
        if (message.obj != null && (message.obj instanceof a)) {
            try {
                br.a().post((a) message.obj);
            } catch (Throwable th) {
            }
        }
    }
}
