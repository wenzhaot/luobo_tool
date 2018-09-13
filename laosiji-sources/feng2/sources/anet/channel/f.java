package anet.channel;

import anet.channel.entity.ConnType.TypeLevel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* compiled from: Taobao */
class f {
    private final Map<SessionRequest, List<Session>> a = new HashMap();
    private final ReentrantReadWriteLock b = new ReentrantReadWriteLock();
    private final ReadLock c = this.b.readLock();
    private final WriteLock d = this.b.writeLock();

    f() {
    }

    public void a(SessionRequest sessionRequest, Session session) {
        if (sessionRequest != null && sessionRequest.a() != null && session != null) {
            this.d.lock();
            try {
                List list = (List) this.a.get(sessionRequest);
                if (list == null) {
                    list = new ArrayList();
                    this.a.put(sessionRequest, list);
                }
                if (list.indexOf(session) == -1) {
                    list.add(session);
                    Collections.sort(list);
                    this.d.unlock();
                }
            } finally {
                this.d.unlock();
            }
        }
    }

    public void b(SessionRequest sessionRequest, Session session) {
        this.d.lock();
        try {
            List list = (List) this.a.get(sessionRequest);
            if (list != null) {
                list.remove(session);
                if (list.size() == 0) {
                    this.a.remove(sessionRequest);
                }
                this.d.unlock();
            }
        } finally {
            this.d.unlock();
        }
    }

    public List<Session> a(SessionRequest sessionRequest) {
        this.c.lock();
        ArrayList arrayList;
        try {
            List list = (List) this.a.get(sessionRequest);
            if (list != null) {
                arrayList = new ArrayList(list);
                return arrayList;
            }
            List<Session> list2 = Collections.EMPTY_LIST;
            this.c.unlock();
            return list2;
        } finally {
            arrayList = this.c;
            arrayList.unlock();
        }
    }

    public Session a(SessionRequest sessionRequest, TypeLevel typeLevel) {
        this.c.lock();
        try {
            List<Session> list = (List) this.a.get(sessionRequest);
            if (list == null || list.isEmpty()) {
                this.c.unlock();
                return null;
            }
            for (Session session : list) {
                if (session != null && session.isAvailable()) {
                    if (typeLevel == null || session.mConnType.getTypeLevel() == typeLevel) {
                        break;
                    }
                }
            }
            Session session2 = null;
            this.c.unlock();
            return session2;
        } catch (Throwable th) {
            this.c.unlock();
        }
    }

    public List<SessionRequest> a() {
        List<SessionRequest> list = Collections.EMPTY_LIST;
        this.c.lock();
        try {
            if (!this.a.isEmpty()) {
                list = new ArrayList(this.a.keySet());
                this.c.unlock();
            }
            return list;
        } finally {
            this.c.unlock();
        }
    }

    public boolean c(SessionRequest sessionRequest, Session session) {
        boolean z = false;
        this.c.lock();
        try {
            List list = (List) this.a.get(sessionRequest);
            if (list == null) {
                return z;
            }
            boolean z2;
            if (list.indexOf(session) != -1) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.c.unlock();
            return z2;
        } finally {
            z = this.c;
            z.unlock();
        }
    }
}
