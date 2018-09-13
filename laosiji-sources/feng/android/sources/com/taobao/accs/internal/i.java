package com.taobao.accs.internal;

import android.content.Intent;
import android.os.Process;
import com.taobao.accs.internal.ServiceImpl.AnonymousClass2;
import com.taobao.accs.utl.ALog;
import com.taobao.accs.utl.UTMini;
import com.taobao.accs.utl.UtilityImpl;
import com.taobao.accs.utl.a;

/* compiled from: Taobao */
class i implements Runnable {
    final /* synthetic */ AnonymousClass2 a;

    i(AnonymousClass2 anonymousClass2) {
        this.a = anonymousClass2;
    }

    public void run() {
        try {
            if (ServiceImpl.this.c == null || !UtilityImpl.getServiceEnabled(ServiceImpl.this.c)) {
                Process.killProcess(Process.myPid());
            } else {
                Intent intent = new Intent();
                intent.setAction("org.agoo.android.intent.action.PING_V4");
                intent.setClassName(ServiceImpl.this.c.getPackageName(), a.channelService);
                ServiceImpl.this.c.startService(intent);
                UTMini.getInstance().commitEvent(66001, "probeServiceEnabled", UtilityImpl.getDeviceId(ServiceImpl.this.c));
                ALog.d("ServiceImpl", "ReceiverImpl probeTaoBao........mContext.startService(intent) [probe][successfully]", new Object[0]);
            }
            ALog.d("ServiceImpl", "ReceiverImpl probeTaoBao........messageServiceBinder [probe][end]", new Object[0]);
        } catch (Throwable th) {
            ALog.d("ServiceImpl", "ReceiverImpl probeTaoBao error........e=" + th, new Object[0]);
        }
    }
}
