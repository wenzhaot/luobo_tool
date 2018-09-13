package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.dispatch.DispatchConstants;
import anet.channel.strategy.k.b;
import anet.channel.util.ALog;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/* compiled from: Taobao */
class StrategyCollection implements Serializable {
    String a;
    StrategyList b = null;
    volatile long c = 0;
    volatile String d = null;
    volatile String e = null;
    boolean f = false;
    private transient long g = 0;

    protected StrategyCollection(String str) {
        this.a = str;
        this.f = DispatchConstants.isAmdcServerDomain(str);
    }

    public void checkInit() {
        if (this.b != null) {
            this.b.checkInit();
        }
    }

    public synchronized List<IConnStrategy> queryStrategyList() {
        List<IConnStrategy> list;
        if (this.b == null) {
            list = Collections.EMPTY_LIST;
        } else {
            list = this.b.getStrategyList();
        }
        return list;
    }

    public synchronized void notifyConnEvent(IConnStrategy iConnStrategy, ConnEvent connEvent) {
        if (this.b != null) {
            this.b.notifyConnEvent(iConnStrategy, connEvent);
            if (!connEvent.isSuccess && this.b.shouldRefresh()) {
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - this.g > 60000) {
                    StrategyCenter.getInstance().forceRefreshStrategy(this.a);
                    this.g = currentTimeMillis;
                }
            }
        }
    }

    public String getHostWithEtag() {
        if (TextUtils.isEmpty(this.d)) {
            return this.a;
        }
        return new StringBuilder(this.a).append(':').append(this.d).toString();
    }

    public boolean isExpired() {
        return System.currentTimeMillis() > this.c;
    }

    public synchronized void update(b bVar) {
        this.c = System.currentTimeMillis() + (((long) bVar.b) * 1000);
        if (!bVar.a.equalsIgnoreCase(this.a)) {
            ALog.e("StrategyCollection", "update error!", null, "host", this.a, "dnsInfo.host", bVar.a);
        } else if (!bVar.j) {
            this.e = bVar.d;
            this.d = bVar.i;
            if (bVar.e == null || bVar.e.length == 0 || bVar.g == null || bVar.g.length == 0) {
                this.b = null;
            } else {
                if (this.b == null) {
                    this.b = new StrategyList();
                }
                this.b.update(bVar);
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append("\nStrategyList = ").append(this.c);
        if (this.b != null) {
            stringBuilder.append(this.b.toString());
        } else if (this.e != null) {
            stringBuilder.append('[').append(this.a).append("=>").append(this.e).append(']');
        } else {
            stringBuilder.append("[]");
        }
        return stringBuilder.toString();
    }
}
