package com.alibaba.sdk.android.httpdns.a;

import java.util.ArrayList;
import java.util.Iterator;

public class e {
    public ArrayList<g> a;
    public long id;
    public String j;
    public String k;
    public String l;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("[HostRecord] ");
        stringBuilder.append("id:");
        stringBuilder.append(this.id);
        stringBuilder.append("|");
        stringBuilder.append("host:");
        stringBuilder.append(this.j);
        stringBuilder.append("|");
        stringBuilder.append("sp:");
        stringBuilder.append(this.k);
        stringBuilder.append("|");
        stringBuilder.append("time:");
        stringBuilder.append(this.l);
        stringBuilder.append("|");
        stringBuilder.append("ips:");
        if (this.a != null && this.a.size() > 0) {
            Iterator it = this.a.iterator();
            while (it.hasNext()) {
                stringBuilder.append((g) it.next());
            }
        }
        stringBuilder.append("|");
        return stringBuilder.toString();
    }
}
