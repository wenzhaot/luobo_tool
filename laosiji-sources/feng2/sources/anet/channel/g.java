package anet.channel;

import anet.channel.entity.EventCb;
import anet.channel.entity.EventType;
import anet.channel.entity.d;
import anet.channel.util.ALog;

/* compiled from: Taobao */
class g implements EventCb {
    final /* synthetic */ IConnCb a;
    final /* synthetic */ long b;
    final /* synthetic */ SessionRequest c;

    g(SessionRequest sessionRequest, IConnCb iConnCb, long j) {
        this.c = sessionRequest;
        this.a = iConnCb;
        this.b = j;
    }

    public void onEvent(Session session, EventType eventType, d dVar) {
        if (session != null && eventType != null) {
            int i = dVar == null ? 0 : dVar.d;
            String str = dVar == null ? "" : dVar.e;
            String str2;
            String str3;
            switch (eventType) {
                case AUTH_SUCC:
                    str2 = "awcn.SessionRequest";
                    if (session != null) {
                        str = session.mSeq;
                    } else {
                        str = null;
                    }
                    ALog.d(str2, null, str, "Session", session, "EventType", eventType, "Event", dVar);
                    this.c.a(session, 0, null);
                    this.a.onSuccess(session, this.b);
                    return;
                case DISCONNECTED:
                    str3 = "awcn.SessionRequest";
                    if (session != null) {
                        str2 = session.mSeq;
                    } else {
                        str2 = null;
                    }
                    ALog.d(str3, null, str2, "Session", session, "EventType", eventType, "Event", dVar);
                    this.c.a(session, i, str);
                    if (this.c.c.c(this.c, session)) {
                        this.a.onDisConnect(session, this.b, eventType);
                        return;
                    } else {
                        this.a.onFailed(session, this.b, eventType, i);
                        return;
                    }
                case CONNECT_FAIL:
                    str3 = "awcn.SessionRequest";
                    if (session != null) {
                        str2 = session.mSeq;
                    } else {
                        str2 = null;
                    }
                    ALog.d(str3, null, str2, "Session", session, "EventType", eventType, "Event", dVar);
                    this.c.a(session, i, str);
                    this.a.onFailed(session, this.b, eventType, i);
                    return;
                default:
                    return;
            }
        }
    }
}
