package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.utils.d;
import anet.channel.util.ALog;
import java.net.InetAddress;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/* compiled from: Taobao */
class b implements Runnable {
    final /* synthetic */ String a;
    final /* synthetic */ Object b;
    final /* synthetic */ a c;

    b(a aVar, String str, Object obj) {
        this.c = aVar;
        this.a = str;
        this.b = obj;
    }

    public void run() {
        int i = 80;
        Object obj = null;
        try {
            String hostAddress = InetAddress.getByName(this.a).getHostAddress();
            if (d.a(hostAddress)) {
                ConnProtocol connProtocol = StrategyTemplate.getInstance().getConnProtocol(this.a);
                List linkedList = new LinkedList();
                IPConnStrategy a;
                if (connProtocol != null) {
                    if (connProtocol.protocol.equalsIgnoreCase("https") || !TextUtils.isEmpty(connProtocol.publicKey)) {
                        obj = 1;
                    }
                    if (obj != null) {
                        i = 443;
                    }
                    a = IPConnStrategy.a(hostAddress, i, connProtocol, 0, 0, 1, 45000);
                    a.a = 2;
                    linkedList.add(a);
                    this.c.a.put(this.a, linkedList);
                } else {
                    IPConnStrategy a2 = IPConnStrategy.a(hostAddress, 80, ConnProtocol.HTTP, 0, 0, 0, 0);
                    a2.a = 2;
                    a = IPConnStrategy.a(hostAddress, 443, ConnProtocol.HTTPS, 0, 0, 0, 0);
                    a.a = 2;
                    linkedList.add(a2);
                    linkedList.add(a);
                    this.c.a.put(this.a, linkedList);
                }
                if (ALog.isPrintLog(1)) {
                    ALog.d("awcn.LocalDnsStrategyTable", "resolve ip by local dns", null, "host", this.a, "ip", hostAddress);
                }
            } else {
                this.c.a.put(this.a, Collections.EMPTY_LIST);
            }
            synchronized (this.c.b) {
                this.c.b.remove(this.a);
            }
            synchronized (this.b) {
                this.b.notifyAll();
            }
        } catch (Exception e) {
            if (ALog.isPrintLog(1)) {
                ALog.d("awcn.LocalDnsStrategyTable", "resolve ip by local dns failed", null, "host", this.a);
            }
            synchronized (this.c.b) {
                this.c.b.remove(this.a);
                synchronized (this.b) {
                    this.b.notifyAll();
                }
            }
        } catch (Throwable th) {
            synchronized (this.c.b) {
                this.c.b.remove(this.a);
                synchronized (this.b) {
                    this.b.notifyAll();
                }
            }
        }
    }
}
