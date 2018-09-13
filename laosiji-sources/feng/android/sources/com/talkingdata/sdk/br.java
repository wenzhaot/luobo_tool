package com.talkingdata.sdk;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArraySet;

/* compiled from: td */
public final class br {
    private static volatile br a = null;
    private final ConcurrentMap b = new ConcurrentHashMap();
    private final ThreadLocal c = new bs(this);
    private final ThreadLocal d = new bt(this);
    private final Map e = new HashMap();

    /* compiled from: td */
    static class a {
        final Object event;
        final bv handler;

        public a(Object obj, bv bvVar) {
            this.event = obj;
            this.handler = bvVar;
        }
    }

    public static br a() {
        if (a == null) {
            synchronized (br.class) {
                if (a == null) {
                    a = new br();
                }
            }
        }
        return a;
    }

    private br() {
    }

    public void register(Object obj) {
        if (obj != null) {
            try {
                Map a = bu.a(obj);
                for (Class cls : a.keySet()) {
                    Set set = (Set) this.b.get(cls);
                    if (set == null) {
                        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
                        set = (Set) this.b.putIfAbsent(cls, copyOnWriteArraySet);
                        if (set == null) {
                            set = copyOnWriteArraySet;
                        }
                    }
                    if (!set.addAll((Set) a.get(cls))) {
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public void unregister(Object obj) {
        if (obj != null) {
            try {
                for (Entry entry : bu.a(obj).entrySet()) {
                    Set<bv> a = a((Class) entry.getKey());
                    Collection collection = (Collection) entry.getValue();
                    if (a != null && a.containsAll(collection)) {
                        for (bv bvVar : a) {
                            if (collection.contains(bvVar)) {
                                bvVar.b();
                            }
                        }
                        a.removeAll(collection);
                    } else {
                        return;
                    }
                }
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    public void post(Object obj) {
        if (obj != null) {
            try {
                Object obj2 = null;
                for (Class a : b(obj.getClass())) {
                    Set<bv> a2 = a(a);
                    if (!(a2 == null || a2.isEmpty())) {
                        obj2 = 1;
                        for (bv a3 : a2) {
                            a(obj, a3);
                        }
                    }
                    obj2 = obj2;
                }
                if (obj2 == null) {
                    if (!(obj instanceof bw)) {
                        post(new bw(this, obj));
                    }
                }
                b();
            } catch (Throwable th) {
                cs.postSDKError(th);
            }
        }
    }

    protected void a(Object obj, bv bvVar) {
        try {
            ((ConcurrentLinkedQueue) this.c.get()).offer(new a(obj, bvVar));
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    protected void b() {
        try {
            if (!((Boolean) this.d.get()).booleanValue()) {
                this.d.set(Boolean.valueOf(true));
                while (true) {
                    a aVar = (a) ((ConcurrentLinkedQueue) this.c.get()).poll();
                    if (aVar == null) {
                        this.d.set(Boolean.valueOf(false));
                        return;
                    } else if (aVar.handler.a()) {
                        b(aVar.event, aVar.handler);
                    }
                }
            }
        } finally {
            this.d.set(Boolean.valueOf(false));
        }
    }

    protected void b(Object obj, bv bvVar) {
        try {
            bvVar.handleEvent(obj);
        } catch (Throwable th) {
            cs.postSDKError(th);
        }
    }

    Set a(Class cls) {
        try {
            return (Set) this.b.get(cls);
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    Set b(Class cls) {
        try {
            Set set = (Set) this.e.get(cls);
            if (set != null) {
                return set;
            }
            set = c(cls);
            this.e.put(cls, set);
            return set;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }

    private Set c(Class cls) {
        try {
            List linkedList = new LinkedList();
            Set hashSet = new HashSet();
            linkedList.add(cls);
            while (!linkedList.isEmpty()) {
                Class cls2 = (Class) linkedList.remove(0);
                hashSet.add(cls2);
                cls2 = cls2.getSuperclass();
                if (cls2 != null) {
                    linkedList.add(cls2);
                }
            }
            return hashSet;
        } catch (Throwable th) {
            cs.postSDKError(th);
            return null;
        }
    }
}
