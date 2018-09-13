package com.talkingdata.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/* compiled from: td */
public class cg {
    private static int a = 2;
    private static int b = 6;
    private static int c = 6;
    private static int d = -40;
    private static int e = 4;
    private cc f;

    /* compiled from: td */
    class a {
        public cf fp1;
        public cf fp2;
        public double score;

        public a(cf cfVar, cf cfVar2, double d) {
            this.fp1 = cfVar;
            this.fp2 = cfVar2;
            this.score = d;
        }
    }

    /* compiled from: td */
    class b {
        public Object key;
        public Object value;

        public b(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }
    }

    public cg() {
        this(new cc());
    }

    public cg(cc ccVar) {
        this.f = ccVar;
    }

    public double a(cf cfVar, cf cfVar2) {
        Map a = cfVar.a(false);
        Map a2 = cfVar2.a(false);
        Set hashSet = new HashSet();
        double d = 0.0d;
        double d2 = 0.0d;
        int i = 0;
        int i2 = 0;
        for (Entry entry : a.entrySet()) {
            cb cbVar = (cb) entry.getValue();
            cb cbVar2 = (cb) a2.get(entry.getKey());
            i += cbVar.c();
            if (cbVar2 == null) {
                hashSet.add(cbVar);
            } else {
                i2++;
                double b = b(cbVar.c(), (int) cbVar2.c());
                d2 += b;
                d += a(cbVar.c(), cbVar2.c()) * b;
            }
            d = d;
            d2 = d2;
        }
        if (i2 == 0) {
            return 0.0d;
        }
        for (Entry entry2 : a2.entrySet()) {
            i += ((cb) entry2.getValue()).c();
            if (!a.containsKey(entry2.getKey())) {
                hashSet.add(entry2.getValue());
            }
        }
        double d3 = 0.0d;
        byte max = Math.max(this.f.d(), (int) (((double) (i / ((cfVar.c().size() + cfVar2.c().size()) - 0))) + 1.2d));
        Iterator it = hashSet.iterator();
        while (true) {
            double d4 = d3;
            if (!it.hasNext()) {
                return (1.0d - Math.pow(d4 / (((double) (i2 * 2)) + d4), (double) e)) * (d / d2);
            } else if (((cb) it.next()).c() > max) {
                d3 = 1.0d + d4;
            } else {
                d3 = d4;
            }
        }
    }

    public double a(cf cfVar, List list) {
        double d = 0.0d;
        Iterator it = list.iterator();
        while (true) {
            double d2 = d;
            if (!it.hasNext()) {
                return d2;
            }
            d = Math.max(a((cf) it.next(), cfVar), d2);
        }
    }

    public double a(List list, List list2) {
        double d = 0.0d;
        if (list.isEmpty() || list2.isEmpty()) {
            return 0.0d;
        }
        List linkedList = new LinkedList();
        b(list, list2, linkedList);
        int i = 0;
        Iterator it = linkedList.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return d / ((double) i2);
            }
            a aVar = (a) it.next();
            if (!(aVar.fp1 == null || aVar.fp2 == null)) {
                d += aVar.score;
                i2++;
            }
            i = i2;
        }
    }

    public cf b(cf cfVar, cf cfVar2) {
        Map a = cfVar.a(false);
        Map a2 = cfVar2.a(false);
        SortedMap treeMap = new TreeMap();
        cf cfVar3 = new cf();
        cfVar3.setPoiId(cfVar2.b());
        cfVar3.setTimestamp(cfVar2.a());
        List linkedList = new LinkedList();
        cfVar3.setBsslist(linkedList);
        for (Entry entry : a.entrySet()) {
            cb cbVar = (cb) entry.getValue();
            cb cbVar2 = (cb) a2.get(entry.getKey());
            if (cbVar2 == null) {
                double d = (double) (-cbVar.c());
                while (treeMap.containsKey(Double.valueOf(d))) {
                    d += 1.0E-4d;
                }
                treeMap.put(Double.valueOf(d), cbVar);
            } else {
                linkedList.add(new cb(cbVar2.a(), cbVar2.b(), (byte) ((cbVar.c() + cbVar2.c()) / 2), cbVar2.d(), cbVar2.e()));
            }
        }
        for (Entry entry2 : a2.entrySet()) {
            if (!a.containsKey(entry2.getKey())) {
                double d2 = (double) (-((cb) entry2.getValue()).c());
                while (treeMap.containsKey(Double.valueOf(d2))) {
                    d2 += 1.0E-4d;
                }
                treeMap.put(Double.valueOf(d2), entry2.getValue());
            }
        }
        for (Entry entry22 : treeMap.entrySet()) {
            byte b = (byte) ((int) (-((Double) entry22.getKey()).doubleValue()));
            if (linkedList.size() >= this.f.c() || b < this.f.d()) {
                break;
            }
            linkedList.add(entry22.getValue());
        }
        return cfVar3;
    }

    public double a(List list, List list2, List list3) {
        double d = 0.0d;
        if (list.isEmpty() || list2.isEmpty()) {
            list3.addAll(list);
            list3.addAll(list2);
            return 0.0d;
        }
        List linkedList = new LinkedList();
        b(list, list2, linkedList);
        int i = 0;
        Iterator it = linkedList.iterator();
        while (true) {
            int i2 = i;
            if (!it.hasNext()) {
                return d / ((double) i2);
            }
            a aVar = (a) it.next();
            if (aVar.fp1 != null && aVar.fp2 != null) {
                d += aVar.score;
                i2++;
                list3.add(b(aVar.fp1, aVar.fp2));
            } else if (list3.size() < this.f.b()) {
                list3.add(aVar.fp1 == null ? aVar.fp2.d() : aVar.fp1.d());
            }
            i = i2;
        }
    }

    public double a(int i, int i2) {
        double d = 0.0d;
        if (i >= 0 || i2 >= 0) {
            return 0.0d;
        }
        double d2 = (double) ((i + i2) / 2);
        double abs = Math.abs(((double) i) - d2);
        if (abs > ((double) a)) {
            d = abs - ((double) a);
        }
        return Math.pow((d + d2) / d2, (double) b);
    }

    public void b(List list, List list2, List list3) {
        Object arrayList = new ArrayList();
        Set<cf> hashSet = new HashSet();
        Set<cf> hashSet2 = new HashSet();
        for (cf cfVar : list) {
            for (cf cfVar2 : list2) {
                hashSet2.add(cfVar2);
                arrayList.add(new a(cfVar, cfVar2, a(cfVar, cfVar2)));
            }
            hashSet.add(cfVar);
        }
        Collections.sort(arrayList, new ch(this));
        list3.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            a aVar = (a) it.next();
            if (hashSet.contains(aVar.fp1) && hashSet2.contains(aVar.fp2)) {
                hashSet.remove(aVar.fp1);
                hashSet2.remove(aVar.fp2);
                list3.add(aVar);
            }
        }
        for (cf cfVar3 : hashSet) {
            list3.add(new a(cfVar3, null, 0.0d));
        }
        for (cf cfVar22 : hashSet2) {
            list3.add(new a(null, cfVar22, 0.0d));
        }
    }

    public double b(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            return 0.0d;
        }
        double max = (double) Math.max(i, i2);
        return max >= ((double) d) ? 1.0d : Math.pow((max + 128.0d) / ((double) (d + 128)), (double) c);
    }
}
