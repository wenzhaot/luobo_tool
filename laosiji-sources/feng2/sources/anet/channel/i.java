package anet.channel;

import anet.channel.util.g;

/* compiled from: Taobao */
class i implements Runnable {
    final /* synthetic */ Session a;
    final /* synthetic */ a b;

    i(a aVar, Session session) {
        this.b = aVar;
        this.a = session;
    }

    public void run() {
        try {
            SessionRequest.this.a(this.b.c, this.a.getConnType().getTypeLevel(), g.a(SessionRequest.this.b.seqNum));
        } catch (Exception e) {
        }
    }
}
