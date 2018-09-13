package com.alibaba.sdk.android.httpdns;

import com.alibaba.sdk.android.httpdns.a.b;
import com.alibaba.sdk.android.httpdns.a.e;
import com.alibaba.sdk.android.httpdns.a.g;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

class c {
    private String[] a;
    private long b;
    /* renamed from: b */
    private String f1b;
    private long c;

    c(e eVar) {
        int i = 0;
        this.b = eVar.j;
        this.c = com.alibaba.sdk.android.httpdns.a.c.a(eVar.l);
        if (eVar.a != null && eVar.a.size() > 0) {
            int size = eVar.a.size();
            if (size > 0) {
                this.b = com.alibaba.sdk.android.httpdns.a.c.a(((g) eVar.a.get(0)).n);
                this.a = new String[size];
                while (i < size) {
                    this.a[i] = ((g) eVar.a.get(i)).m;
                    i++;
                }
            }
        }
    }

    c(String str) {
        JSONObject jSONObject = new JSONObject(str);
        this.b = jSONObject.getString("host");
        JSONArray jSONArray = jSONObject.getJSONArray("ips");
        int length = jSONArray.length();
        this.a = new String[length];
        for (int i = 0; i < length; i++) {
            this.a[i] = jSONArray.getString(i);
        }
        this.b = jSONObject.getLong("ttl");
        this.c = System.currentTimeMillis() / 1000;
    }

    long a() {
        return this.b;
    }

    /* renamed from: a */
    e m0a() {
        e eVar = new e();
        eVar.j = this.b;
        eVar.l = String.valueOf(this.c);
        eVar.k = b.g();
        if (this.a != null && this.a.length > 0) {
            eVar.a = new ArrayList();
            for (String str : this.a) {
                g gVar = new g();
                gVar.m = str;
                gVar.n = String.valueOf(this.b);
                eVar.a.add(gVar);
            }
        }
        return eVar;
    }

    /* renamed from: a */
    String[] m1a() {
        return this.a;
    }

    long b() {
        return this.c;
    }

    /* renamed from: b */
    boolean m2b() {
        return b() + a() < System.currentTimeMillis() / 1000;
    }

    public String toString() {
        String str = "host: " + this.b + " ip cnt: " + this.a.length + " ttl: " + this.b;
        for (String str2 : this.a) {
            str = str + "\n ip: " + str2;
        }
        return str;
    }
}
