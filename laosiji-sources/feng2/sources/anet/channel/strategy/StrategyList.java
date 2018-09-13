package anet.channel.strategy;

import anet.channel.strategy.k.a;
import anet.channel.strategy.k.b;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
class StrategyList implements Serializable {
    private List<IPConnStrategy> a = new ArrayList();
    private Map<Integer, ConnHistoryItem> b = new HashMap();
    private boolean c = false;
    private transient Comparator<IPConnStrategy> d = null;

    /* compiled from: Taobao */
    private interface Predicate<T> {
        boolean apply(T t);
    }

    StrategyList(List<IPConnStrategy> list) {
        this.a = list;
    }

    public void checkInit() {
        if (this.a == null) {
            this.a = new ArrayList();
        }
        if (this.b == null) {
            this.b = new HashMap();
        }
        Iterator it = this.b.entrySet().iterator();
        while (it.hasNext()) {
            if (((ConnHistoryItem) ((Entry) it.next()).getValue()).d()) {
                it.remove();
            }
        }
        for (IPConnStrategy iPConnStrategy : this.a) {
            if (!this.b.containsKey(Integer.valueOf(iPConnStrategy.getUniqueId()))) {
                this.b.put(Integer.valueOf(iPConnStrategy.getUniqueId()), new ConnHistoryItem());
            }
        }
        Collections.sort(this.a, a());
    }

    public String toString() {
        return this.a.toString();
    }

    public List<IConnStrategy> getStrategyList() {
        if (this.a.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<IConnStrategy> list = null;
        for (IPConnStrategy iPConnStrategy : this.a) {
            if (((ConnHistoryItem) this.b.get(Integer.valueOf(iPConnStrategy.getUniqueId()))).c()) {
                ALog.i("awcn.StrategyList", "strategy ban!", null, "strategy", iPConnStrategy);
            } else {
                List<IConnStrategy> arrayList;
                if (list == null) {
                    arrayList = new ArrayList();
                } else {
                    arrayList = list;
                }
                arrayList.add(iPConnStrategy);
                list = arrayList;
            }
        }
        return list == null ? Collections.EMPTY_LIST : list;
    }

    public void update(b bVar) {
        for (IPConnStrategy iPConnStrategy : this.a) {
            iPConnStrategy.b = true;
        }
        for (int i = 0; i < bVar.g.length; i++) {
            for (String a : bVar.e) {
                a(a, 1, bVar.g[i]);
            }
            if (bVar.f != null) {
                this.c = true;
                for (String a2 : bVar.f) {
                    a(a2, 0, bVar.g[i]);
                }
            } else {
                this.c = false;
            }
        }
        ListIterator listIterator = this.a.listIterator();
        while (listIterator.hasNext()) {
            if (((IPConnStrategy) listIterator.next()).b) {
                listIterator.remove();
            }
        }
        Collections.sort(this.a, a());
    }

    private void a(String str, int i, a aVar) {
        int a = a(this.a, new i(this, aVar, str, ConnProtocol.valueOf(aVar)));
        IPConnStrategy iPConnStrategy;
        if (a != -1) {
            iPConnStrategy = (IPConnStrategy) this.a.get(a);
            iPConnStrategy.cto = aVar.c;
            iPConnStrategy.rto = aVar.d;
            iPConnStrategy.heartbeat = aVar.f;
            iPConnStrategy.a = i;
            iPConnStrategy.b = false;
            return;
        }
        iPConnStrategy = IPConnStrategy.a(str, aVar);
        if (iPConnStrategy != null) {
            iPConnStrategy.a = i;
            if (!this.b.containsKey(Integer.valueOf(iPConnStrategy.getUniqueId()))) {
                this.b.put(Integer.valueOf(iPConnStrategy.getUniqueId()), new ConnHistoryItem());
            }
            this.a.add(iPConnStrategy);
        }
    }

    public boolean shouldRefresh() {
        for (IPConnStrategy iPConnStrategy : this.a) {
            if ((!this.c || iPConnStrategy.a == 0) && !((ConnHistoryItem) this.b.get(Integer.valueOf(iPConnStrategy.getUniqueId()))).b()) {
                return false;
            }
        }
        return true;
    }

    public void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if ((iConnStrategy instanceof IPConnStrategy) && this.a.indexOf(iConnStrategy) != -1) {
            ((ConnHistoryItem) this.b.get(Integer.valueOf(((IPConnStrategy) iConnStrategy).getUniqueId()))).a(connEvent.isSuccess);
            Collections.sort(this.a, this.d);
        }
    }

    private Comparator a() {
        if (this.d == null) {
            this.d = new j(this);
        }
        return this.d;
    }

    private static <T> int a(Collection<T> collection, Predicate<T> predicate) {
        if (collection == null) {
            return -1;
        }
        int i = 0;
        Iterator it = collection.iterator();
        while (it.hasNext() && !predicate.apply(it.next())) {
            i++;
        }
        if (i == collection.size()) {
            i = -1;
        }
        return i;
    }
}
