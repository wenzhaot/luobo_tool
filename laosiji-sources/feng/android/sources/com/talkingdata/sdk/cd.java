package com.talkingdata.sdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
final class cd {
    private static int a = 2;
    private static int b = 6;
    private static int c = 6;
    private static int d = -40;
    private static int e = 4;
    private b f;

    /* compiled from: td */
    class a {
        private byte band;
        private String bssid;
        private byte channel;
        private byte rssi;
        private String ssid;

        a() {
            this.ssid = "";
            this.bssid = "00:00:00:00:00:00";
            this.rssi = (byte) -127;
            this.band = (byte) 1;
            this.channel = (byte) 1;
        }

        a(String str, String str2, byte b, byte b2, byte b3) {
            this.ssid = str;
            this.bssid = str2;
            this.rssi = b;
            this.band = b2;
            this.channel = b3;
        }

        String getSsid() {
            return this.ssid;
        }

        void setSsid(String str) {
            this.ssid = str;
        }

        String getBssid() {
            return this.bssid;
        }

        void setBssid(String str) {
            this.bssid = str;
        }

        byte getRssi() {
            return this.rssi;
        }

        void setRssi(byte b) {
            this.rssi = b;
        }

        byte getBand() {
            return this.band;
        }

        void setBand(byte b) {
            this.band = b;
        }

        byte getChannel() {
            return this.channel;
        }

        void setChannel(byte b) {
            this.channel = b;
        }

        a cloneBssEntry() {
            return new a(this.ssid, this.bssid, this.rssi, this.band, this.channel);
        }
    }

    /* compiled from: td */
    static class b {
        static final int DEFAULT_MAX_BSS_ENTRIES = 50;
        static final int DEFAULT_MAX_FINGERPRINTS = 10;
        static final int DEFAULT_MIN_FINGERPRINTS = 3;
        static final int DEFAULT_RSSI_THRESHOLD = -85;
        private int maxBssEntries = 50;
        private int maxFingerprints = 10;
        private int minFingerprints = 3;
        private int rssiThreshold = -85;

        b() {
        }

        int getMaxFingerprints() {
            return this.maxFingerprints;
        }

        void setMaxFingerprints(int i) {
            this.maxFingerprints = i;
        }

        int getMinFingerprints() {
            return this.minFingerprints;
        }

        void setMinFingerprints(int i) {
            this.minFingerprints = i;
        }

        int getMaxBssEntries() {
            return this.maxBssEntries;
        }

        void setMaxBssEntries(int i) {
            this.maxBssEntries = i;
        }

        int getRssiThreshold() {
            return this.rssiThreshold;
        }

        void setRssiThreshold(int i) {
            this.rssiThreshold = i;
        }
    }

    /* compiled from: td */
    class c {
        private List bsslist;
        private Map bssmap;
        private long poiId;
        private int timestamp;

        c() {
        }

        int getTimestamp() {
            return this.timestamp;
        }

        void setTimestamp(int i) {
            this.timestamp = i;
        }

        long getPoiId() {
            return this.poiId;
        }

        void setPoiId(long j) {
            this.poiId = j;
        }

        List getBsslist() {
            return this.bsslist;
        }

        void setBsslist(List list) {
            this.bsslist = list;
        }

        Map getBssmap(boolean z) {
            if (this.bssmap == null || z) {
                this.bssmap = new HashMap();
                for (a aVar : this.bsslist) {
                    this.bssmap.put(aVar.getBssid(), aVar);
                }
            }
            return this.bssmap;
        }

        c cloneFingerprint() {
            c cVar = new c();
            cVar.setTimestamp(this.timestamp);
            cVar.setPoiId(this.poiId);
            List linkedList = new LinkedList();
            for (a cloneBssEntry : this.bsslist) {
                linkedList.add(cloneBssEntry.cloneBssEntry());
            }
            cVar.setBsslist(linkedList);
            return cVar;
        }
    }

    /* compiled from: td */
    class d {
        c fp1;
        c fp2;
        double score;

        d(c cVar, c cVar2, double d) {
            this.fp1 = cVar;
            this.fp2 = cVar2;
            this.score = d;
        }
    }

    /* compiled from: td */
    class e {
        Object key;
        Object value;

        e(Object obj, Object obj2) {
            this.key = obj;
            this.value = obj2;
        }
    }

    cd() {
        this(new b());
    }

    cd(b bVar) {
        this.f = bVar;
    }

    double a(c cVar, c cVar2) {
        Map bssmap = cVar.getBssmap(false);
        Map bssmap2 = cVar2.getBssmap(false);
        Set hashSet = new HashSet();
        double d = 0.0d;
        double d2 = 0.0d;
        int i = 0;
        int i2 = 0;
        for (Entry entry : bssmap.entrySet()) {
            a aVar = (a) entry.getValue();
            a aVar2 = (a) bssmap2.get(entry.getKey());
            i += aVar.getRssi();
            if (aVar2 == null) {
                hashSet.add(aVar);
            } else {
                i2++;
                double b = b(aVar.getRssi(), (int) aVar2.getRssi());
                d2 += b;
                d += a(aVar.getRssi(), aVar2.getRssi()) * b;
            }
            d = d;
            d2 = d2;
        }
        if (i2 == 0) {
            return 0.0d;
        }
        for (Entry entry2 : bssmap2.entrySet()) {
            i += ((a) entry2.getValue()).getRssi();
            if (!bssmap.containsKey(entry2.getKey())) {
                hashSet.add(entry2.getValue());
            }
        }
        double d3 = 0.0d;
        byte max = Math.max(this.f.getRssiThreshold(), (int) (((double) (i / ((cVar.getBsslist().size() + cVar2.getBsslist().size()) - 0))) + 1.2d));
        Iterator it = hashSet.iterator();
        while (true) {
            double d4 = d3;
            if (!it.hasNext()) {
                return (1.0d - Math.pow(d4 / (((double) (i2 * 2)) + d4), (double) e)) * (d / d2);
            } else if (((a) it.next()).getRssi() > max) {
                d3 = 1.0d + d4;
            } else {
                d3 = d4;
            }
        }
    }

    double a(c cVar, List list) {
        double d = 0.0d;
        Iterator it = list.iterator();
        while (true) {
            double d2 = d;
            if (!it.hasNext()) {
                return d2;
            }
            d = Math.max(a((c) it.next(), cVar), d2);
        }
    }

    double a(List list, List list2) {
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
            d dVar = (d) it.next();
            if (!(dVar.fp1 == null || dVar.fp2 == null)) {
                d += dVar.score;
                i2++;
            }
            i = i2;
        }
    }

    c b(c cVar, c cVar2) {
        double d;
        Map bssmap = cVar.getBssmap(false);
        Map bssmap2 = cVar2.getBssmap(false);
        SortedMap treeMap = new TreeMap();
        c cVar3 = new c();
        cVar3.setPoiId(cVar2.getPoiId());
        cVar3.setTimestamp(cVar2.getTimestamp());
        List linkedList = new LinkedList();
        cVar3.setBsslist(linkedList);
        for (Entry entry : bssmap.entrySet()) {
            a aVar = (a) entry.getValue();
            a aVar2 = (a) bssmap2.get(entry.getKey());
            if (aVar2 == null) {
                d = (double) (-aVar.getRssi());
                while (treeMap.containsKey(Double.valueOf(d))) {
                    d += 1.0E-4d;
                }
                treeMap.put(Double.valueOf(d), aVar);
            } else {
                linkedList.add(new a(aVar2.getSsid(), aVar2.getBssid(), (byte) ((aVar.getRssi() + aVar2.getRssi()) / 2), aVar2.getBand(), aVar2.getChannel()));
            }
        }
        for (Entry entry2 : bssmap2.entrySet()) {
            if (!bssmap.containsKey(entry2.getKey())) {
                d = (double) (-((a) entry2.getValue()).getRssi());
                while (treeMap.containsKey(Double.valueOf(d))) {
                    d += 1.0E-4d;
                }
                treeMap.put(Double.valueOf(d), entry2.getValue());
            }
        }
        for (Entry entry22 : treeMap.entrySet()) {
            byte b = (byte) ((int) (-((Double) entry22.getKey()).doubleValue()));
            if (linkedList.size() >= this.f.getMaxBssEntries() || b < this.f.getRssiThreshold()) {
                break;
            }
            linkedList.add(entry22.getValue());
        }
        return cVar3;
    }

    double a(List list, List list2, List list3) {
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
            d dVar = (d) it.next();
            if (dVar.fp1 != null && dVar.fp2 != null) {
                d += dVar.score;
                i2++;
                list3.add(b(dVar.fp1, dVar.fp2));
            } else if (list3.size() < this.f.getMinFingerprints()) {
                list3.add(dVar.fp1 == null ? dVar.fp2.cloneFingerprint() : dVar.fp1.cloneFingerprint());
            }
            i = i2;
        }
    }

    double a(int i, int i2) {
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

    void b(List list, List list2, List list3) {
        Object arrayList = new ArrayList();
        Set<c> hashSet = new HashSet();
        Set<c> hashSet2 = new HashSet();
        for (c cVar : list) {
            for (c cVar2 : list2) {
                hashSet2.add(cVar2);
                arrayList.add(new d(cVar, cVar2, a(cVar, cVar2)));
            }
            hashSet.add(cVar);
        }
        Collections.sort(arrayList, new ce(this));
        list3.clear();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            d dVar = (d) it.next();
            if (hashSet.contains(dVar.fp1) && hashSet2.contains(dVar.fp2)) {
                hashSet.remove(dVar.fp1);
                hashSet2.remove(dVar.fp2);
                list3.add(dVar);
            }
        }
        for (c cVar3 : hashSet) {
            list3.add(new d(cVar3, null, 0.0d));
        }
        for (c cVar22 : hashSet2) {
            list3.add(new d(null, cVar22, 0.0d));
        }
    }

    double b(int i, int i2) {
        if (i >= 0 || i2 >= 0) {
            return 0.0d;
        }
        double max = (double) Math.max(i, i2);
        return max >= ((double) d) ? 1.0d : Math.pow((max + 128.0d) / ((double) (d + 128)), (double) c);
    }
}
