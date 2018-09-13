package com.baidu.location.c.a;

import com.baidu.location.BDLocation;
import com.baidu.location.h.k;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.Vector;

public final class d {
    private static volatile d q = null;
    private HashMap<String, HashMap<Integer, e>> a;
    private HashMap<String, HashMap<Integer, Vector<Integer>>> b;
    private HashMap<String, ArrayList<ArrayList<Integer>>> c;
    private HashMap<String, ArrayList<ArrayList<Integer>>> d;
    private ArrayList<BDLocation> e;
    private ArrayList<BDLocation> f;
    private c g;
    private String h;
    private String i;
    private boolean j;
    private boolean k;
    private boolean l;
    private boolean m;
    private b n;
    private boolean o;
    private String p;

    private static class a {
        private final a a;
        private final int b;
        private final int c;

        private a(a aVar, int i, int i2) {
            this.a = aVar;
            this.b = i;
            this.c = i2;
        }
    }

    private static class b {
        private double a;
        private double b;
        private HashMap<String, g> c;
        private double d;
        private double e;
        private boolean f;
        private e g;
        private e h;
        private boolean i;

        private b(double d, double d2) {
            this.c = null;
            this.f = false;
            this.i = false;
            this.a = d;
            this.b = d2;
        }

        private HashMap<String, g> a() {
            return this.c;
        }

        private void a(HashMap<String, g> hashMap) {
            this.c = hashMap;
        }
    }

    private static class c {
        private final ArrayDeque<b> a;

        private c() {
            this.a = new ArrayDeque();
        }

        private void a() {
            if (this.a.size() > 0) {
                this.a.removeFirst();
            }
        }

        private void a(b bVar) {
            if (this.a.size() >= 3) {
                this.a.removeFirst();
            }
            this.a.addLast(bVar);
        }

        private void b() {
            this.a.clear();
        }

        private boolean c() {
            return this.a.size() == 3;
        }

        private int d() {
            return this.a.size();
        }
    }

    private d() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = null;
        this.i = null;
        this.j = false;
        this.k = true;
        this.l = false;
        this.m = false;
        this.n = null;
        this.o = false;
        this.p = null;
        this.a = new HashMap();
        this.b = new HashMap();
        this.c = new HashMap();
        this.d = new HashMap();
        this.g = new c();
    }

    private double a(BDLocation bDLocation, ArrayList<e> arrayList) {
        e eVar = (e) arrayList.get(arrayList.size() - 1);
        e eVar2 = new e();
        eVar2.a = bDLocation.getLatitude();
        eVar2.b = bDLocation.getLongitude();
        double a = eVar.a(eVar2);
        Iterator it = this.g.a.iterator();
        while (true) {
            double d = a;
            if (!it.hasNext()) {
                return d;
            }
            a = a((b) it.next(), (ArrayList) arrayList) + d;
        }
    }

    private double a(b bVar, ArrayList<e> arrayList) {
        int i = 0;
        e eVar = new e();
        eVar.a = bVar.b;
        eVar.b = bVar.a;
        if (arrayList.size() < 2) {
            return Double.MAX_VALUE;
        }
        e eVar2;
        e eVar3 = (e) arrayList.get(0);
        e eVar4 = (e) arrayList.get(arrayList.size() - 1);
        if (!(eVar3.d == Integer.MAX_VALUE || eVar3.e == Integer.MAX_VALUE)) {
            eVar2 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar3.d));
            eVar3 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar3.e));
            e c = c(eVar, eVar2, eVar3);
            if (d(c, eVar2, eVar3)) {
                return eVar.a(c);
            }
        }
        if (!(eVar4.d == Integer.MAX_VALUE || eVar4.e == Integer.MAX_VALUE)) {
            eVar3 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar4.d));
            eVar4 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar4.e));
            eVar2 = c(eVar, eVar3, eVar4);
            if (d(eVar2, eVar3, eVar4)) {
                return eVar.a(eVar2);
            }
        }
        if (bVar.i) {
            eVar3 = new e();
            eVar3.a = bVar.e;
            eVar3.b = bVar.d;
            return eVar.a(eVar3);
        }
        double d = Double.MAX_VALUE;
        while (i < arrayList.size() - 1) {
            double a;
            eVar3 = (e) arrayList.get(i);
            eVar4 = (e) arrayList.get(i + 1);
            e c2 = c(eVar, eVar3, eVar4);
            if (d(c2, eVar3, eVar4)) {
                a = eVar.a(c2);
                if (a < d) {
                    i++;
                    d = a;
                }
            }
            a = d;
            i++;
            d = a;
        }
        return d;
    }

    private double a(e eVar, e eVar2, ArrayList<e> arrayList) {
        int i = 0;
        double d = 0.0d;
        while (arrayList.size() >= 2 && i < arrayList.size() - 1) {
            d += ((e) arrayList.get(i)).a((e) arrayList.get(i + 1));
            i++;
        }
        return eVar.a((e) arrayList.get(arrayList.size() - 1)) + (d + eVar.a((e) arrayList.get(0)));
    }

    private double a(List<e> list) {
        if (list.size() < 2) {
            return Double.MAX_VALUE;
        }
        int i = 0;
        double d = 0.0d;
        while (true) {
            int i2 = i;
            if (i2 >= list.size() - 1) {
                return d;
            }
            d += ((e) list.get(i2)).a((e) list.get(i2 + 1));
            i = i2 + 1;
        }
    }

    public static d a() {
        if (q == null) {
            synchronized (d.class) {
                if (q == null) {
                    q = new d();
                }
            }
        }
        return q;
    }

    private e a(e eVar, e eVar2, e eVar3) {
        e eVar4 = new e();
        double d = eVar2.a;
        double d2 = eVar2.b;
        double d3 = eVar3.a;
        double d4 = eVar3.b;
        double d5 = eVar.a;
        double d6 = eVar.b;
        d6 = Math.sqrt(((d5 - d3) * (d5 - d3)) + ((d6 - d4) * (d6 - d4)));
        if (Math.abs((d2 - d4) / (d - d3)) > 10.0d) {
            d5 = d4 + d6;
            d6 = d4 - d6;
            if (((d3 - d3) * (d3 - d)) + ((d5 - d4) * (d4 - d2)) <= 0.0d) {
                d5 = d6;
            }
            d6 = d3;
        } else {
            double d7 = (d4 - d2) / (d3 - d);
            double d8 = ((d * d4) - (d3 * d2)) / (d - d3);
            double d9 = (d7 * d7) + 1.0d;
            double d10 = ((2.0d * d7) * (d8 - d4)) - (2.0d * d3);
            d6 = ((d3 * d3) + ((d8 - d4) * (d8 - d4))) - (d6 * d6);
            if ((d10 * d10) - ((4.0d * d9) * d6) < 0.0d) {
                eVar4.a = Double.MAX_VALUE;
                eVar4.b = Double.MAX_VALUE;
                return eVar4;
            }
            double sqrt = ((-1.0d * d10) + Math.sqrt((d10 * d10) - ((4.0d * d9) * d6))) / (2.0d * d9);
            d5 = (d7 * sqrt) + d8;
            d9 = ((-1.0d * d10) - Math.sqrt((d10 * d10) - (d6 * (4.0d * d9)))) / (d9 * 2.0d);
            d6 = (d7 * d9) + d8;
            if (((d3 - d) * (sqrt - d3)) + ((d5 - d4) * (d4 - d2)) > 0.0d) {
                d6 = sqrt;
            } else {
                d5 = d6;
                d6 = d9;
            }
        }
        eVar4.a = d6;
        eVar4.b = d5;
        return eVar4;
    }

    private ArrayList<e> a(g gVar, g gVar2) {
        gVar.c.d = gVar.a();
        gVar.c.e = gVar.b();
        gVar2.c.d = gVar2.a();
        gVar2.c.e = gVar2.b();
        double d = Double.MAX_VALUE;
        ArrayList<e> arrayList = new ArrayList();
        if (gVar.a() == gVar2.a() && gVar.b() == gVar2.b()) {
            arrayList.add(gVar.c);
            arrayList.add(gVar2.c);
            return arrayList;
        }
        Set hashSet = new HashSet();
        hashSet.add(Integer.valueOf(gVar.b()));
        hashSet.add(Integer.valueOf(gVar2.b()));
        List a = a(this.h, gVar.a(), gVar2.a(), gVar.c, gVar2.c, hashSet);
        double a2 = a(a);
        if (a2 < Double.MAX_VALUE) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(gVar.a()));
        hashSet.add(Integer.valueOf(gVar2.b()));
        a = a(this.h, gVar.b(), gVar2.a(), gVar.c, gVar2.c, hashSet);
        a2 = a(a);
        if (a2 < d) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(gVar.b()));
        hashSet.add(Integer.valueOf(gVar2.a()));
        a = a(this.h, gVar.a(), gVar2.b(), gVar.c, gVar2.c, hashSet);
        a2 = a(a);
        if (a2 < d) {
            arrayList = a;
            d = a2;
        }
        hashSet.clear();
        hashSet.add(Integer.valueOf(gVar.a()));
        hashSet.add(Integer.valueOf(gVar2.a()));
        a = a(this.h, gVar.b(), gVar2.b(), gVar.c, gVar2.c, hashSet);
        return a(a) < d ? a : arrayList;
    }

    private ArrayList<e> a(String str, int i, int i2, e eVar, e eVar2, Set<Integer> set) {
        HashMap hashMap = (HashMap) this.b.get(str);
        ArrayList arrayList = new ArrayList();
        Queue linkedList = new LinkedList();
        linkedList.add(new a(null, i, 0));
        while (!linkedList.isEmpty()) {
            a aVar = (a) linkedList.poll();
            if (!set.contains(Integer.valueOf(aVar.b)) && aVar.c <= 4) {
                if (aVar.b != i2) {
                    set.add(Integer.valueOf(aVar.b));
                    if (aVar.c < 4) {
                        Vector vector = (Vector) hashMap.get(Integer.valueOf(aVar.b));
                        int i3 = 0;
                        while (vector != null && i3 < vector.size()) {
                            linkedList.offer(new a(aVar, ((Integer) vector.get(i3)).intValue(), aVar.c + 1));
                            i3++;
                        }
                    }
                } else {
                    arrayList.add(aVar);
                }
            }
        }
        ArrayList<e> arrayList2 = new ArrayList();
        int i4 = 0;
        while (true) {
            int i5 = i4;
            if (i5 >= arrayList.size()) {
                return arrayList2;
            }
            ArrayList arrayList3 = new ArrayList();
            for (a aVar2 = (a) arrayList.get(i5); aVar2 != null; aVar2 = aVar2.a) {
                arrayList3.add((e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(aVar2.b)));
            }
            Collection arrayList4 = new ArrayList();
            for (i4 = arrayList3.size() - 1; i4 >= 0; i4--) {
                arrayList4.add(arrayList3.get(i4));
            }
            if (a(eVar, eVar2, arrayList3) < ((double) Float.MAX_VALUE)) {
                arrayList2.clear();
                arrayList2.add(eVar);
                arrayList2.addAll(arrayList4);
                arrayList2.add(eVar2);
            }
            i4 = i5 + 1;
        }
    }

    private boolean a(String str) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(str)));
            ArrayList arrayList = null;
            ArrayList arrayList2 = null;
            HashMap hashMap = null;
            HashMap hashMap2 = null;
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine == null) {
                    bufferedReader.close();
                    HashMap hashMap3 = (HashMap) this.a.get(this.h);
                    hashMap3 = (HashMap) this.b.get(this.h);
                    return true;
                } else if (readLine.contains("Floor")) {
                    HashMap hashMap4 = new HashMap();
                    HashMap hashMap5 = new HashMap();
                    ArrayList arrayList3 = new ArrayList();
                    ArrayList arrayList4 = new ArrayList();
                    Object obj = readLine.split(":")[1];
                    this.a.put(obj, hashMap4);
                    this.b.put(obj, hashMap5);
                    this.c.put(obj, arrayList3);
                    this.d.put(obj, arrayList4);
                    hashMap = hashMap5;
                    hashMap2 = hashMap4;
                    arrayList = arrayList4;
                    arrayList2 = arrayList3;
                } else {
                    int intValue;
                    String[] split;
                    String[] split2 = readLine.split(",");
                    if (split2[0].equals("0")) {
                        intValue = Integer.valueOf(split2[2]).intValue();
                        e eVar = new e();
                        eVar.a = Double.valueOf(split2[4]).doubleValue();
                        eVar.b = Double.valueOf(split2[3]).doubleValue();
                        eVar.c = intValue;
                        hashMap2.put(Integer.valueOf(intValue), eVar);
                    }
                    if (split2[0].equals("1")) {
                        for (int i = 1; i < split2.length; i++) {
                            Vector vector;
                            int intValue2;
                            String[] split3 = split2[i].split("-");
                            if (hashMap.keySet().contains(Integer.valueOf(split3[0]))) {
                                vector = (Vector) hashMap.get(Integer.valueOf(split3[0]));
                                if (!vector.contains(Integer.valueOf(split3[1]))) {
                                    vector.add(Integer.valueOf(split3[1]));
                                    Collections.sort(vector);
                                }
                            } else {
                                vector = new Vector();
                                intValue2 = Integer.valueOf(split3[0]).intValue();
                                vector.add(Integer.valueOf(split3[1]));
                                hashMap.put(Integer.valueOf(intValue2), vector);
                            }
                            if (hashMap.keySet().contains(Integer.valueOf(split3[1]))) {
                                vector = (Vector) hashMap.get(Integer.valueOf(split3[1]));
                                if (!vector.contains(Integer.valueOf(split3[0]))) {
                                    vector.add(Integer.valueOf(split3[0]));
                                    Collections.sort(vector);
                                }
                            } else {
                                vector = new Vector();
                                intValue2 = Integer.valueOf(split3[1]).intValue();
                                vector.add(Integer.valueOf(split3[0]));
                                hashMap.put(Integer.valueOf(intValue2), vector);
                            }
                        }
                    }
                    if (split2[0].equals("2")) {
                        split = split2[1].split("-");
                        ArrayList arrayList5 = new ArrayList();
                        for (String valueOf : split) {
                            arrayList5.add(Integer.valueOf(valueOf));
                        }
                        arrayList2.add(arrayList5);
                    }
                    if (split2[0].equals("3")) {
                        split = split2[1].split("-");
                        ArrayList arrayList6 = new ArrayList();
                        for (String valueOf2 : split) {
                            arrayList6.add(Integer.valueOf(valueOf2));
                        }
                        arrayList.add(arrayList6);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        return false;
    }

    private e b(e eVar, e eVar2, e eVar3) {
        e eVar4 = new e();
        double d = eVar2.a;
        double d2 = eVar2.b;
        double d3 = eVar3.a;
        double d4 = eVar3.b;
        double d5 = eVar.a;
        double d6 = eVar.b;
        d6 = Math.sqrt(((d5 - d) * (d5 - d)) + ((d6 - d2) * (d6 - d2)));
        if (Math.abs((d2 - d4) / (d - d3)) > 200.0d) {
            d5 = d2 + d6;
            d6 = d2 - d6;
            if (((d - d) * (d3 - d)) + ((d5 - d2) * (d4 - d2)) <= 0.0d) {
                d5 = d6;
            }
            d6 = d;
        } else {
            double d7 = (d4 - d2) / (d3 - d);
            double d8 = ((d * d4) - (d3 * d2)) / (d - d3);
            double d9 = (d7 * d7) + 1.0d;
            double d10 = ((2.0d * d7) * (d8 - d2)) - (2.0d * d);
            d6 = ((d * d) + ((d8 - d2) * (d8 - d2))) - (d6 * d6);
            if ((d10 * d10) - ((4.0d * d9) * d6) < 0.0d) {
                eVar4.a = Double.MAX_VALUE;
                eVar4.b = Double.MAX_VALUE;
                return eVar4;
            }
            double sqrt = ((-1.0d * d10) + Math.sqrt((d10 * d10) - ((4.0d * d9) * d6))) / (2.0d * d9);
            d5 = (d7 * sqrt) + d8;
            d9 = ((-1.0d * d10) - Math.sqrt((d10 * d10) - (d6 * (4.0d * d9)))) / (d9 * 2.0d);
            d6 = (d7 * d9) + d8;
            if (((d3 - d) * (sqrt - d)) + ((d4 - d2) * (d5 - d2)) > 0.0d) {
                d6 = sqrt;
            } else {
                d5 = d6;
                d6 = d9;
            }
        }
        eVar4.a = d6;
        eVar4.b = d5;
        return eVar4;
    }

    private boolean b(b bVar, ArrayList<e> arrayList) {
        boolean z = false;
        e eVar = new e();
        eVar.a = bVar.b;
        eVar.b = bVar.a;
        if (arrayList.size() < 2) {
            return false;
        }
        e eVar2 = (e) arrayList.get(0);
        e eVar3 = (e) arrayList.get(arrayList.size() - 1);
        if (!(eVar2.d == Integer.MAX_VALUE || eVar2.e == Integer.MAX_VALUE)) {
            e eVar4 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar2.d));
            eVar2 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar2.e));
            if (d(c(eVar, eVar4, eVar2), eVar4, eVar2)) {
                return true;
            }
        }
        if (!(eVar3.d == Integer.MAX_VALUE || eVar3.e == Integer.MAX_VALUE)) {
            eVar2 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar3.d));
            eVar3 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(eVar3.e));
            if (d(c(eVar, eVar2, eVar3), eVar2, eVar3)) {
                return true;
            }
        }
        for (int i = 0; i < arrayList.size() - 1; i++) {
            eVar2 = (e) arrayList.get(i);
            eVar3 = (e) arrayList.get(i + 1);
            if (d(c(eVar, eVar2, eVar3), eVar2, eVar3)) {
                z = true;
                break;
            }
        }
        return !bVar.i ? z : true;
    }

    private e c(e eVar, e eVar2, e eVar3) {
        e eVar4 = new e();
        double d = eVar.a;
        double d2 = eVar.b;
        double d3 = eVar2.a;
        double d4 = eVar2.b;
        double d5 = eVar3.a;
        double d6 = eVar3.b;
        if (Math.abs((d4 - d6) / (d3 - d5)) > 20000.0d) {
            eVar4.a = d3;
            eVar4.b = d2;
        } else {
            d5 = (d4 - d6) / (d3 - d5);
            eVar4.a = ((d / d5) + ((d2 - d4) + (d5 * d3))) * (d5 / ((d5 * d5) + 1.0d));
            eVar4.b = ((eVar4.a - d3) * d5) + d4;
        }
        return eVar4;
    }

    private boolean c(BDLocation bDLocation) {
        this.o = false;
        String buildingName = bDLocation.getBuildingName();
        if (!this.k) {
            if (!buildingName.equals(this.p)) {
                this.l = false;
                this.m = false;
                a.b().a(buildingName);
            }
            this.p = buildingName;
        }
        this.h = bDLocation.getFloor();
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            if (this.k) {
                this.p = buildingName;
                a.b().a(buildingName);
            }
            this.k = false;
        }
        if (!this.m) {
            if (!this.l) {
                return false;
            }
            if (a(k.h() + File.separator + "indoorinfo" + File.separator + buildingName + File.separator + buildingName + ".txt")) {
                this.m = true;
            } else {
                this.m = false;
                this.l = false;
                a.b().c();
                return false;
            }
        }
        if (this.a.get(this.h) == null) {
            return false;
        }
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            this.i = this.h;
            this.n = new b(bDLocation.getLongitude(), bDLocation.getLatitude());
            this.n.e = this.n.b;
            this.n.d = this.n.a;
            this.e = new ArrayList();
            this.f = new ArrayList();
            if (b(bDLocation)) {
                this.g.b();
                this.n = null;
                this.j = false;
                return true;
            } else if (this.h.equals(this.i)) {
                HashMap e = e(bDLocation);
                if (e.size() <= 0) {
                    return true;
                }
                if (this.g.c()) {
                    this.n.a(e);
                    if (!this.o) {
                        d(bDLocation);
                        this.j = true;
                        this.g.a(this.n);
                    }
                    return true;
                } else if (this.o) {
                    return true;
                } else {
                    double d = -1.0d;
                    g gVar = null;
                    for (Entry value : e.entrySet()) {
                        g gVar2 = (g) value.getValue();
                        if (gVar2.e > d) {
                            d = gVar2.e;
                        } else {
                            gVar2 = gVar;
                        }
                        gVar = gVar2;
                    }
                    if (gVar == null || gVar.e <= 0.5d) {
                        this.g.b();
                        this.j = false;
                        return true;
                    }
                    bDLocation.setLatitude(gVar.c.a);
                    bDLocation.setLongitude(gVar.c.b);
                    this.n.e = gVar.c.a;
                    this.n.d = gVar.c.b;
                    bDLocation.setNetworkLocationType("wf2");
                    this.j = true;
                    this.n.f = false;
                    this.n.g = new e();
                    this.n.g.a = gVar.a.a;
                    this.n.g.b = gVar.a.b;
                    this.n.h = new e();
                    this.n.h.a = gVar.b.a;
                    this.n.h.b = gVar.b.b;
                    if (!this.g.c()) {
                        this.n.a(e);
                        this.g.a(this.n);
                    }
                    return true;
                }
            } else {
                this.g.b();
                if (this.j) {
                    this.j = false;
                }
                this.i = this.h;
                return true;
            }
        } else if (!bDLocation.getNetworkLocationType().equals("dr")) {
            return true;
        } else {
            this.e.add(new BDLocation(bDLocation));
            if (this.j) {
                d(bDLocation);
                this.f.add(bDLocation);
            }
            return true;
        }
    }

    private void d(BDLocation bDLocation) {
        double f;
        e c;
        int i;
        e eVar;
        int i2;
        if (bDLocation.getNetworkLocationType().equals("dr")) {
            double e = this.n.d;
            f = this.n.e;
            double b = this.n.a;
            double a = this.n.b;
            bDLocation.setLongitude((e + bDLocation.getLongitude()) - b);
            bDLocation.setLatitude((bDLocation.getLatitude() + f) - a);
            e eVar2 = new e();
            eVar2.a = bDLocation.getLatitude();
            eVar2.b = bDLocation.getLongitude();
            if (!(this.n.g == null || this.n.h == null)) {
                if (!this.n.f) {
                    c = c(eVar2, this.n.g, this.n.h);
                } else if (this.n.i) {
                    c = b(eVar2, this.n.g, this.n.h);
                    if (c.a == Double.MAX_VALUE && c.b == Double.MAX_VALUE) {
                        c = c(eVar2, this.n.g, this.n.h);
                        bDLocation.setLongitude(c.b);
                        bDLocation.setLatitude(c.a);
                        bDLocation.setNetworkLocationType("dr2");
                        return;
                    }
                    bDLocation.setLongitude(c.b);
                    bDLocation.setLatitude(c.a);
                    bDLocation.setNetworkLocationType("dr2");
                    return;
                } else {
                    e a2 = a(eVar2, this.n.g, this.n.h);
                    if (a2.a == Double.MAX_VALUE && a2.b == Double.MAX_VALUE) {
                        c = c(eVar2, this.n.g, this.n.h);
                        bDLocation.setLongitude(c.b);
                        bDLocation.setLatitude(c.a);
                        bDLocation.setNetworkLocationType("dr2");
                        return;
                    }
                    if (this.n.h.d != Integer.MAX_VALUE) {
                        i = this.n.h.d;
                        c = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(i));
                        eVar = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(this.n.h.e));
                        if (!d(a2, c, eVar)) {
                            e eVar3;
                            e eVar4 = new e();
                            i2 = 0;
                            if (this.n.g.c == Integer.MAX_VALUE) {
                                eVar3 = new e();
                                eVar3.a = this.n.g.a;
                                eVar3.b = this.n.g.b;
                                e eVar5 = new e();
                                eVar5.a = this.n.h.a;
                                eVar5.b = this.n.h.b;
                                if (c.a(eVar3) > c.a(eVar5)) {
                                    eVar4.c = c.c;
                                    eVar4.a = c.a;
                                    eVar4.b = c.b;
                                    i2 = eVar.c;
                                }
                                if (c.a(eVar3) < c.a(eVar5)) {
                                    eVar4.c = eVar.c;
                                    eVar4.a = eVar.a;
                                    eVar4.b = eVar.b;
                                    i2 = c.c;
                                }
                                if (c.a(eVar3) == c.a(eVar5)) {
                                    bDLocation.setLongitude(a2.b);
                                    bDLocation.setLatitude(a2.a);
                                    bDLocation.setNetworkLocationType("dr2");
                                    return;
                                }
                            } else if (c.c == this.n.g.c) {
                                eVar4.a = eVar.a;
                                eVar4.b = eVar.b;
                                eVar4.c = eVar.c;
                                i2 = c.c;
                            } else {
                                eVar4.a = c.a;
                                eVar4.b = c.b;
                                eVar4.c = c.c;
                                i2 = eVar.c;
                            }
                            Vector vector = (Vector) ((HashMap) this.b.get(this.h)).get(Integer.valueOf(eVar4.c));
                            eVar3 = new e();
                            if (vector.size() == 2) {
                                if (((Integer) vector.get(0)).intValue() != i2) {
                                    eVar3.a = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(0))).a;
                                    eVar3.b = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(0))).b;
                                } else {
                                    eVar3.a = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(1))).a;
                                    eVar3.b = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(1))).b;
                                }
                                c = b(eVar2, eVar4, eVar3);
                                if (c.a == Double.MAX_VALUE && c.b == Double.MAX_VALUE) {
                                    c = c(eVar2, this.n.g, this.n.h);
                                    bDLocation.setLongitude(c.b);
                                    bDLocation.setLatitude(c.a);
                                    bDLocation.setNetworkLocationType("dr2");
                                    return;
                                }
                            }
                        }
                    }
                    c = a2;
                }
                bDLocation.setLongitude(c.b);
                bDLocation.setLatitude(c.a);
            }
            bDLocation.setNetworkLocationType("dr2");
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (bDLocation.getNetworkLocationType().equals("wf")) {
            b bVar = (b) this.g.a.getFirst();
            HashMap i3 = this.n.a();
            HashMap i4 = bVar.a();
            for (Entry value : i3.entrySet()) {
                g gVar = (g) value.getValue();
                for (Entry value2 : i4.entrySet()) {
                    ArrayList a3 = a((g) value2.getValue(), gVar);
                    if (a3 != null && a3.size() > 0) {
                        arrayList.add(a3);
                    }
                }
            }
            if (arrayList.size() == 0) {
                this.n.e = bDLocation.getLatitude();
                this.n.d = bDLocation.getLongitude();
                bDLocation.setNetworkLocationType("wf2");
                return;
            }
            ArrayList arrayList2;
            int i5;
            int i6 = 0;
            while (true) {
                i2 = i6;
                if (i2 >= arrayList.size()) {
                    break;
                }
                arrayList2 = (ArrayList) arrayList.get(i2);
                i = 0;
                Iterator it = this.g.a.iterator();
                i5 = 0;
                while (true) {
                    int i7 = i;
                    if (!it.hasNext()) {
                        break;
                    }
                    b bVar2 = (b) it.next();
                    if (i7 != 0) {
                        if (!b(bVar2, arrayList2)) {
                            break;
                        }
                        i = i5 + 1;
                    } else {
                        i = i7 + 1;
                    }
                }
                if (i5 < this.g.d() - 1) {
                    arrayList.remove(i2);
                }
                i6 = i2 + 1;
            }
            if (arrayList.size() == 0) {
                this.n.e = bDLocation.getLatitude();
                this.n.d = bDLocation.getLongitude();
                bDLocation.setNetworkLocationType("wf2");
                return;
            }
            c = new e();
            if (arrayList.size() == 1) {
                c = (e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1);
                bDLocation.setLatitude(c.a);
                bDLocation.setLongitude(c.b);
                this.n.e = c.a;
                this.n.d = c.b;
                bDLocation.setNetworkLocationType("wf2");
                this.n.f = true;
                this.n.g = new e();
                this.n.g.a = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).a;
                this.n.g.b = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).b;
                this.n.g.c = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 2)).c;
                this.n.h = new e();
                this.n.h.a = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).a;
                this.n.h.b = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).b;
                this.n.h.d = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).d;
                this.n.h.e = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).e;
                this.n.h.c = ((e) ((ArrayList) arrayList.get(0)).get(((ArrayList) arrayList.get(0)).size() - 1)).c;
                return;
            }
            i5 = 0;
            if (arrayList.size() >= 2) {
                ArrayList arrayList3 = new ArrayList();
                i6 = 0;
                while (true) {
                    i = i6;
                    if (i >= arrayList.size()) {
                        break;
                    }
                    arrayList3.add(Double.valueOf(a(bDLocation, (ArrayList) arrayList.get(i))));
                    i6 = i + 1;
                }
                f = Double.MAX_VALUE;
                i6 = 0;
                while (true) {
                    i = i6;
                    if (i < arrayList3.size()) {
                        if (((Double) arrayList3.get(i)).doubleValue() < f) {
                            f = ((Double) arrayList3.get(i)).doubleValue();
                            i5 = i;
                        }
                        i6 = i + 1;
                    } else {
                        arrayList2 = (ArrayList) arrayList.get(i5);
                        eVar = (e) arrayList2.get(arrayList2.size() - 1);
                        bDLocation.setLatitude(eVar.a);
                        bDLocation.setLongitude(eVar.b);
                        this.n.e = eVar.a;
                        this.n.d = eVar.b;
                        this.n.f = true;
                        this.n.g = new e();
                        this.n.g.a = ((e) arrayList2.get(arrayList2.size() - 2)).a;
                        this.n.g.b = ((e) arrayList2.get(arrayList2.size() - 2)).b;
                        this.n.g.c = ((e) arrayList2.get(arrayList2.size() - 2)).c;
                        this.n.h = new e();
                        this.n.h.a = ((e) arrayList2.get(arrayList2.size() - 1)).a;
                        this.n.h.b = ((e) arrayList2.get(arrayList2.size() - 1)).b;
                        this.n.h.d = ((e) arrayList2.get(arrayList2.size() - 1)).d;
                        this.n.h.e = ((e) arrayList2.get(arrayList2.size() - 1)).e;
                        this.n.h.c = ((e) arrayList2.get(arrayList2.size() - 1)).c;
                        bDLocation.setNetworkLocationType("wf2");
                        return;
                    }
                }
            }
        }
    }

    private boolean d(e eVar, e eVar2, e eVar3) {
        double d = eVar.a;
        double d2 = eVar.b;
        double d3 = eVar2.a;
        return ((eVar3.a - d) * (d3 - d)) + ((eVar3.b - d2) * (eVar2.b - d2)) <= 0.0d;
    }

    private double e(e eVar, e eVar2, e eVar3) {
        e eVar4 = new e();
        e eVar5 = new e();
        eVar4.a = eVar2.a - eVar.a;
        eVar4.b = eVar2.b - eVar.b;
        eVar5.a = eVar3.a - eVar.a;
        eVar5.b = eVar3.b - eVar.b;
        return Math.acos(((eVar4.a * eVar5.a) + (eVar4.b * eVar5.b)) / (eVar5.a() * eVar4.a()));
    }

    private HashMap<String, g> e(BDLocation bDLocation) {
        e eVar;
        Vector vector;
        int i;
        int intValue;
        e eVar2;
        Iterator it;
        int i2;
        int i3;
        HashMap<String, g> hashMap = new HashMap();
        double latitude = bDLocation.getLatitude();
        double longitude = bDLocation.getLongitude();
        e eVar3 = new e();
        eVar3.a = latitude;
        eVar3.b = longitude;
        HashMap hashMap2 = (HashMap) this.b.get(this.h);
        if (hashMap2 != null) {
            HashMap hashMap3 = new HashMap();
            for (Entry entry : hashMap2.entrySet()) {
                int intValue2 = ((Integer) entry.getKey()).intValue();
                eVar = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(intValue2));
                vector = (Vector) entry.getValue();
                i = 0;
                while (true) {
                    int i4 = i;
                    if (i4 < vector.size()) {
                        intValue = ((Integer) vector.get(i4)).intValue();
                        String str = String.valueOf(intValue2 > intValue ? intValue : intValue2) + "_" + String.valueOf(intValue2 < intValue ? intValue : intValue2);
                        if (!hashMap3.containsKey(str)) {
                            hashMap3.put(str, Integer.valueOf(1));
                            eVar2 = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(intValue));
                            e c = c(eVar3, eVar, eVar2);
                            if (d(c, eVar, eVar2)) {
                                g gVar = new g();
                                gVar.c = c;
                                gVar.d = eVar3.a(c);
                                gVar.a = eVar;
                                gVar.b = eVar2;
                                if (gVar.d < 2.0E-4d) {
                                    hashMap.put(str, gVar);
                                }
                            }
                        }
                        i = i4 + 1;
                    }
                }
            }
        }
        if (hashMap.size() > 0) {
            latitude = 0.0d;
            it = hashMap.entrySet().iterator();
            while (true) {
                longitude = latitude;
                if (!it.hasNext()) {
                    break;
                }
                latitude = (1.0d / (((g) ((Entry) it.next()).getValue()).d + 1.0E-6d)) + longitude;
            }
            ArrayList arrayList = new ArrayList();
            for (Entry entry2 : hashMap.entrySet()) {
                g gVar2 = (g) entry2.getValue();
                String str2 = (String) entry2.getKey();
                if (hashMap.size() == 1) {
                    gVar2.e = 1.0d;
                } else {
                    gVar2.e = (1.0d / (1.0E-6d + gVar2.d)) / longitude;
                }
                if (gVar2.e < 0.1d) {
                    arrayList.add(str2);
                }
            }
            i2 = 0;
            while (true) {
                i3 = i2;
                if (i3 >= arrayList.size()) {
                    break;
                }
                hashMap.remove((String) arrayList.get(i3));
                i2 = i3 + 1;
            }
        }
        if (hashMap.size() >= 0) {
            Object obj;
            e eVar4 = null;
            double d = 999999.0d;
            i = 0;
            for (Entry entry22 : ((HashMap) this.a.get(this.h)).entrySet()) {
                i3 = ((Integer) entry22.getKey()).intValue();
                e eVar5 = (e) entry22.getValue();
                if (Math.abs(eVar5.a - eVar3.a) <= 5.0E-4d && Math.abs(eVar5.b - eVar3.b) <= 5.0E-4d) {
                    double a = eVar5.a(eVar3);
                    if (d > a) {
                        longitude = a;
                        int i5 = i3;
                        eVar = eVar5;
                        i2 = i5;
                    } else {
                        i2 = i;
                        eVar = eVar4;
                        longitude = d;
                    }
                    d = longitude;
                    i = i2;
                    eVar4 = eVar;
                }
            }
            Object obj2 = 1;
            it = hashMap.entrySet().iterator();
            while (true) {
                obj = obj2;
                if (!it.hasNext()) {
                    break;
                }
                obj2 = ((g) ((Entry) it.next()).getValue()).d <= d ? null : obj;
            }
            if (obj == null) {
                return hashMap;
            }
            hashMap.clear();
            vector = (Vector) ((HashMap) this.b.get(this.h)).get(Integer.valueOf(i));
            if (vector == null) {
                return hashMap;
            }
            i3 = ((Integer) vector.get(0)).intValue();
            g gVar3 = new g();
            gVar3.c = eVar4;
            gVar3.d = 0.0d;
            gVar3.a = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(i));
            gVar3.b = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(i3));
            hashMap.put(String.valueOf(i) + "_" + String.valueOf(i3), gVar3);
            this.n.b = bDLocation.getLatitude();
            this.n.a = bDLocation.getLongitude();
            bDLocation.setLatitude(eVar4.a);
            bDLocation.setLongitude(eVar4.b);
            bDLocation.setNetworkLocationType("wf2");
            this.o = true;
            this.n.g = new e();
            this.n.g.a = gVar3.b.a;
            this.n.g.b = gVar3.b.b;
            this.n.h = new e();
            this.n.h.a = eVar4.a;
            this.n.h.b = eVar4.b;
            this.n.e = eVar4.a;
            this.n.d = eVar4.b;
            this.n.a((HashMap) hashMap);
            this.n.i = true;
            this.n.f = false;
            if (this.g.c()) {
                b bVar = (b) this.g.a.getLast();
                if (bVar.h != null && (bVar.h.d == i || bVar.h.e == i)) {
                    gVar3.a = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(i));
                    this.n.f = true;
                    intValue = bVar.h.e == i ? bVar.h.d : bVar.h.d == i ? bVar.h.e : 0;
                    gVar3.b = (e) ((HashMap) this.a.get(this.h)).get(Integer.valueOf(intValue));
                    vector = (Vector) ((HashMap) this.b.get(this.h)).get(Integer.valueOf(i));
                    eVar2 = new e();
                    if (vector.size() == 2) {
                        if (((Integer) vector.get(0)).intValue() != intValue) {
                            eVar2.a = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(0))).a;
                            eVar2.b = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(0))).b;
                        } else {
                            eVar2.a = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(1))).a;
                            eVar2.b = ((e) ((HashMap) this.a.get(this.h)).get(vector.get(1))).b;
                        }
                    }
                    this.n.h = eVar2;
                    this.n.g = eVar4;
                    this.n.h.d = Integer.MAX_VALUE;
                    this.n.i = true;
                }
                this.g.a();
                this.g.a(this.n);
            } else {
                this.g.a(this.n);
            }
        }
        return hashMap;
    }

    public boolean a(BDLocation bDLocation) {
        return false;
    }

    void b() {
        this.l = true;
    }

    boolean b(BDLocation bDLocation) {
        double d;
        boolean z = false;
        ArrayList arrayList = (ArrayList) this.c.get(this.h);
        ArrayList arrayList2 = (ArrayList) this.d.get(this.h);
        e eVar = new e();
        eVar.a = bDLocation.getLatitude();
        eVar.b = bDLocation.getLongitude();
        int i = 0;
        while (arrayList != null && i < arrayList.size()) {
            ArrayList arrayList3 = (ArrayList) arrayList.get(i);
            int i2 = 0;
            d = 0.0d;
            while (true) {
                int i3 = i2;
                if (i3 >= arrayList3.size() - 1) {
                    break;
                }
                d += e(eVar, (e) ((HashMap) this.a.get(this.h)).get(arrayList3.get(i3)), (e) ((HashMap) this.a.get(this.h)).get(arrayList3.get(i3 + 1)));
                i2 = i3 + 1;
            }
            if (Math.abs((e(eVar, (e) ((HashMap) this.a.get(this.h)).get(arrayList3.get(0)), (e) ((HashMap) this.a.get(this.h)).get(arrayList3.get(arrayList3.size() - 1))) + d) - 360.0d) < 0.1d) {
                z = true;
                break;
            }
            i++;
        }
        if (!z) {
            i = 0;
            while (arrayList2 != null && i < arrayList2.size()) {
                arrayList = (ArrayList) arrayList2.get(i);
                int i4 = 0;
                d = 0.0d;
                while (true) {
                    int i5 = i4;
                    if (i5 >= arrayList.size() - 1) {
                        break;
                    }
                    d += e(eVar, (e) ((HashMap) this.a.get(this.h)).get(arrayList.get(i5)), (e) ((HashMap) this.a.get(this.h)).get(arrayList.get(i5 + 1)));
                    i4 = i5 + 1;
                }
                if (Math.abs((e(eVar, (e) ((HashMap) this.a.get(this.h)).get(arrayList.get(0)), (e) ((HashMap) this.a.get(this.h)).get(arrayList.get(arrayList.size() - 1))) + d) - 360.0d) < 0.1d) {
                    return true;
                }
                i++;
            }
        }
        return z;
    }
}
