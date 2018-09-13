package anet.channel.strategy;

import android.text.TextUtils;
import anet.channel.strategy.k.a;
import java.io.Serializable;

/* compiled from: Taobao */
class IPConnStrategy implements IConnStrategy, Serializable {
    volatile int a = 1;
    transient boolean b;
    public volatile int cto;
    public volatile int heartbeat;
    public final String ip;
    public final int port;
    public final ConnProtocol protocol;
    public volatile int retry;
    public volatile int rto;

    /* compiled from: Taobao */
    interface IpType {
        public static final int LOCAL_DNS = 2;
        public static final int NORMAL = 1;
        public static final int STATIC_BANDWITDH = 0;
    }

    static IPConnStrategy a(String str, a aVar) {
        ConnProtocol valueOf = ConnProtocol.valueOf(aVar);
        if (valueOf == null) {
            return null;
        }
        return a(str, aVar.a, valueOf, aVar.c, aVar.d, aVar.e, aVar.f);
    }

    static IPConnStrategy a(String str, int i, ConnProtocol connProtocol, int i2, int i3, int i4, int i5) {
        if (TextUtils.isEmpty(str) || connProtocol == null || i <= 0) {
            return null;
        }
        return new IPConnStrategy(str, i, connProtocol, i2, i3, i4, i5);
    }

    private IPConnStrategy(String str, int i, ConnProtocol connProtocol, int i2, int i3, int i4, int i5) {
        this.ip = str;
        this.port = i;
        this.protocol = connProtocol;
        this.cto = i2;
        this.rto = i3;
        this.retry = i4;
        this.heartbeat = i5;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append('{').append(this.ip);
        if (this.a == 0) {
            stringBuilder.append("(*)");
        }
        stringBuilder.append(' ').append(this.port).append(' ').append(this.protocol).append('}');
        return stringBuilder.toString();
    }

    public String getIp() {
        return this.ip;
    }

    public int getIpType() {
        return this.a;
    }

    public int getPort() {
        return this.port;
    }

    public ConnProtocol getProtocol() {
        return this.protocol;
    }

    public int getConnectionTimeout() {
        return this.cto;
    }

    public int getReadTimeout() {
        return this.rto;
    }

    public int getRetryTimes() {
        return this.retry;
    }

    public int getHeartbeat() {
        return this.heartbeat;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof IPConnStrategy)) {
            return false;
        }
        IPConnStrategy iPConnStrategy = (IPConnStrategy) obj;
        if (this.port == iPConnStrategy.port && this.ip.equals(iPConnStrategy.ip) && this.protocol.equals(iPConnStrategy.protocol)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((this.ip.hashCode() + 527) * 31) + this.port) * 31) + this.protocol.hashCode();
    }

    public int getUniqueId() {
        return hashCode();
    }
}
