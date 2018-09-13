package anet.channel.strategy;

import anet.channel.strategy.dispatch.HttpDispatcher;
import java.util.ArrayList;
import java.util.List;

/* compiled from: Taobao */
public class HttpDnsAdapter {

    /* compiled from: Taobao */
    public static final class HttpDnsOrigin {
        private final IConnStrategy connStrategy;

        HttpDnsOrigin(IConnStrategy iConnStrategy) {
            this.connStrategy = iConnStrategy;
        }

        public String getOriginIP() {
            return this.connStrategy.getIp();
        }

        public int getOriginPort() {
            return this.connStrategy.getPort();
        }

        public boolean canWithSPDY() {
            String str = this.connStrategy.getProtocol().protocol;
            return (str.equalsIgnoreCase("http") || str.equalsIgnoreCase("https")) ? false : true;
        }

        public String toString() {
            return this.connStrategy.toString();
        }
    }

    public static void setHosts(ArrayList<String> arrayList) {
        HttpDispatcher.getInstance().addHosts(arrayList);
    }

    public static HttpDnsOrigin getOriginByHttpDns(String str) {
        List connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str);
        if (connStrategyListByHost.isEmpty()) {
            return null;
        }
        return new HttpDnsOrigin((IConnStrategy) connStrategyListByHost.get(0));
    }

    public static ArrayList<HttpDnsOrigin> getOriginsByHttpDns(String str) {
        List<IConnStrategy> connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str);
        if (connStrategyListByHost.isEmpty()) {
            return null;
        }
        ArrayList<HttpDnsOrigin> arrayList = new ArrayList(connStrategyListByHost.size());
        for (IConnStrategy httpDnsOrigin : connStrategyListByHost) {
            arrayList.add(new HttpDnsOrigin(httpDnsOrigin));
        }
        return arrayList;
    }

    public static String getIpByHttpDns(String str) {
        List connStrategyListByHost = StrategyCenter.getInstance().getConnStrategyListByHost(str);
        if (connStrategyListByHost.isEmpty()) {
            return null;
        }
        return ((IConnStrategy) connStrategyListByHost.get(0)).getIp();
    }
}
