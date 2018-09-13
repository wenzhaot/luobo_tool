package com.alibaba.sdk.android.httpdns.a;

public class g {
    public long g;
    public long id;
    public String m;
    public String n;

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("[IpRecord] ");
        stringBuilder.append("id:");
        stringBuilder.append(this.id);
        stringBuilder.append("|");
        stringBuilder.append("host_id:");
        stringBuilder.append(this.g);
        stringBuilder.append("|");
        stringBuilder.append("ip:");
        stringBuilder.append(this.m);
        stringBuilder.append("|");
        stringBuilder.append("ttl:");
        stringBuilder.append(this.n);
        stringBuilder.append("|");
        return stringBuilder.toString();
    }
}
