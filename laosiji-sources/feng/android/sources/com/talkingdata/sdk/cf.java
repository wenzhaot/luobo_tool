package com.talkingdata.sdk;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/* compiled from: td */
public class cf {
    private int a;
    private long b;
    private List c;
    private Map d;

    public int a() {
        return this.a;
    }

    public void setTimestamp(int i) {
        this.a = i;
    }

    public long b() {
        return this.b;
    }

    public void setPoiId(long j) {
        this.b = j;
    }

    public List c() {
        return this.c;
    }

    public void setBsslist(List list) {
        this.c = list;
    }

    public Map a(boolean z) {
        if (this.d == null || z) {
            this.d = new HashMap();
            for (cb cbVar : this.c) {
                this.d.put(cbVar.b(), cbVar);
            }
        }
        return this.d;
    }

    public cf d() {
        cf cfVar = new cf();
        cfVar.setTimestamp(this.a);
        cfVar.setPoiId(this.b);
        List linkedList = new LinkedList();
        for (cb f : this.c) {
            linkedList.add(f.f());
        }
        cfVar.setBsslist(linkedList);
        return cfVar;
    }
}
