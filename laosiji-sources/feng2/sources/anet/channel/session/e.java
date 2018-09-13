package anet.channel.session;

import anet.channel.Session.Status;
import anet.channel.entity.EventType;
import anet.channel.entity.b;
import anet.channel.entity.d;
import anet.channel.request.Request;
import anet.channel.session.b.a;

/* compiled from: Taobao */
class e implements Runnable {
    final /* synthetic */ Request a;
    final /* synthetic */ d b;

    e(d dVar, Request request) {
        this.b = dVar;
        this.a = request;
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        a a = b.a(this.a, null);
        if (a.a > 0) {
            d bVar = new b(EventType.CONNECTED);
            bVar.a = System.currentTimeMillis() - currentTimeMillis;
            this.b.a(Status.AUTH_SUCC, bVar);
            return;
        }
        this.b.b(EventType.CONNECT_FAIL, new d(EventType.CONNECT_FAIL, a.a, "Http connect fail"));
    }
}
