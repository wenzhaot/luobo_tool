package anet.channel.a;

import anet.channel.monitor.INetworkQualityChangeListener;
import anet.channel.monitor.NetworkSpeed;
import anet.channel.monitor.b;
import anet.channel.util.ALog;
import java.util.concurrent.ThreadPoolExecutor;

/* compiled from: Taobao */
final class e implements INetworkQualityChangeListener {
    e() {
    }

    public void onNetworkQualityChanged(NetworkSpeed networkSpeed) {
        int i = 3;
        ALog.i("awcn.ThreadPoolExecutorFactory", "", null, "Network", networkSpeed, "Speed", Integer.valueOf(((int) b.a().c()) * 1024));
        ThreadPoolExecutor a = d.a();
        if (networkSpeed != NetworkSpeed.Slow) {
            i = 2;
        }
        a.setCorePoolSize(i);
    }
}
