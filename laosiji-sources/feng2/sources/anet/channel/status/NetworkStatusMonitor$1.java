package anet.channel.status;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import anet.channel.a.c;
import anet.channel.util.ALog;

/* compiled from: Taobao */
final class NetworkStatusMonitor$1 extends BroadcastReceiver {
    NetworkStatusMonitor$1() {
    }

    public void onReceive(Context context, Intent intent) {
        if (ALog.isPrintLog(1)) {
            ALog.d("awcn.NetworkStatusMonitor", "receiver:" + intent.getAction(), null, new Object[0]);
        }
        c.a(new c(this, context));
    }
}
