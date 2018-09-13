package anet.channel.heartbeat;

import anet.channel.Session;

/* compiled from: Taobao */
final class b implements IHeartbeatFactory {
    b() {
    }

    public IHeartbeat createHeartbeat(Session session) {
        if (session == null || session.getConnStrategy() == null || session.getConnStrategy().getHeartbeat() <= 0) {
            return null;
        }
        return new a(session);
    }
}
