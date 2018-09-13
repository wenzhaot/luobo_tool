package com.talkingdata.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.talkingdata.sdk.bd.a;

/* compiled from: td */
final class bg extends BroadcastReceiver {
    BroadcastReceiver mReceiver;
    final /* synthetic */ Context val$context;
    final /* synthetic */ Object val$lock;

    bg(Context context, Object obj) {
        this.val$context = context;
        this.val$lock = obj;
    }

    public void onReceive(Context context, Intent intent) {
        this.mReceiver = this;
        try {
            if (bd.i != null && bl.a.remove(bd.i)) {
                bd.i.unRegisterReceiver();
            }
            bd.i = new a(this.val$context, this.val$lock, this.mReceiver);
            bl.a.execute(bd.i);
        } catch (Throwable th) {
            if (this.mReceiver != null) {
                this.val$context.unregisterReceiver(this.mReceiver);
            }
        }
    }
}
