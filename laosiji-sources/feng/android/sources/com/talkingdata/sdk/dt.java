package com.talkingdata.sdk;

import com.umeng.commonsdk.proguard.g;
import org.json.JSONArray;

/* compiled from: td */
public class dt extends dq {
    public dt() {
        int i = 0;
        a("manufacture", (Object) aw.c());
        a("brand", (Object) aw.d());
        a("model", (Object) aw.e());
        JSONArray jSONArray = new JSONArray();
        String[] m = aw.m();
        for (Object put : m) {
            jSONArray.put(put);
        }
        a("cpuInfo", (Object) jSONArray);
        jSONArray = new JSONArray();
        int[] r = aw.r();
        for (int put2 : r) {
            jSONArray.put(put2);
        }
        a("memoryInfo", (Object) jSONArray);
        JSONArray jSONArray2 = new JSONArray();
        int[] q = aw.q();
        while (i < q.length) {
            jSONArray2.put(q[i]);
            i++;
        }
        a("sdCardInfo", (Object) jSONArray2);
        aw.a(ab.g, this.b);
        aw.b(ab.g, this.b);
        a("totalDiskSpace", (Object) Integer.valueOf(b()));
        a("support", (Object) aw.d(ab.g));
        a(g.v, (Object) aw.n());
        a("nfcHce", (Object) aw.a(ab.g));
    }

    public static int b() {
        int i = 0;
        try {
            int[] s = aw.s();
            return s[2] + s[0];
        } catch (Throwable th) {
            return i;
        }
    }

    public void setSlots(int i) {
        a("slots", (Object) Integer.valueOf(i));
    }

    public void c() {
        try {
            a("support", (Object) aw.d(ab.g));
        } catch (Throwable th) {
        }
    }
}
