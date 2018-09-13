package com.qiniu.android.dns.local;

import com.qiniu.android.dns.Domain;
import com.qiniu.android.dns.NetworkInfo;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;

public final class Hosts {
    private final Hashtable<String, LinkedList<Value>> hosts = new Hashtable();

    public static class Value {
        public final String ip;
        public final int provider;

        public Value(String ip, int provider) {
            this.ip = ip;
            this.provider = provider;
        }

        public Value(String ip) {
            this(ip, 0);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof Value)) {
                return false;
            }
            Value another = (Value) o;
            if (this.ip.equals(another.ip) && this.provider == another.provider) {
                return true;
            }
            return false;
        }
    }

    public synchronized String[] query(Domain domain, NetworkInfo info) {
        String[] strArr;
        LinkedList<Value> values = (LinkedList) this.hosts.get(domain.domain);
        if (values == null || values.isEmpty()) {
            strArr = null;
        } else {
            if (values.size() > 1) {
                Value first = (Value) values.get(0);
                values.remove(0);
                values.add(first);
            }
            strArr = toIps(filter(values, info));
        }
        return strArr;
    }

    private LinkedList<Value> filter(LinkedList<Value> origin, NetworkInfo info) {
        LinkedList<Value> normal = new LinkedList();
        LinkedList<Value> special = new LinkedList();
        Iterator it = origin.iterator();
        while (it.hasNext()) {
            Value v = (Value) it.next();
            if (v.provider == 0) {
                normal.add(v);
            }
            if (info.provider != 0 && v.provider == info.provider) {
                special.add(v);
            }
        }
        return special.size() != 0 ? special : normal;
    }

    public synchronized String[] toIps(LinkedList<Value> vals) {
        String[] r;
        int size = vals.size();
        r = new String[size];
        for (int i = 0; i < size; i++) {
            r[i] = ((Value) vals.get(i)).ip;
        }
        return r;
    }

    public synchronized Hosts put(String domain, Value val) {
        LinkedList<Value> vals = (LinkedList) this.hosts.get(domain);
        if (vals == null) {
            vals = new LinkedList();
        }
        vals.add(val);
        this.hosts.put(domain, vals);
        return this;
    }

    public Hosts put(String domain, String val) {
        put(domain, new Value(val));
        return this;
    }
}
