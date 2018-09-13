package anet.channel.flow;

import anet.channel.statist.RequestStatistic;

/* compiled from: Taobao */
public class b {
    public String a;
    public String b;
    public String c;
    public long d;
    public long e;

    public b(String str, RequestStatistic requestStatistic) {
        this.a = str;
        this.b = requestStatistic.protocolType;
        this.c = requestStatistic.url;
        this.d = requestStatistic.sendDataSize;
        this.e = requestStatistic.recDataSize;
    }

    public String toString() {
        return "FlowStat{refer='" + this.a + '\'' + ", protocoltype='" + this.b + '\'' + ", req_identifier='" + this.c + '\'' + ", upstream=" + this.d + ", downstream=" + this.e + '}';
    }
}
