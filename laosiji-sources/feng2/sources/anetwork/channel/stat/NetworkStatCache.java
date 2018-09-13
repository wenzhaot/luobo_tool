package anetwork.channel.stat;

import anet.channel.util.StringUtils;
import anetwork.channel.statist.StatisticData;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: Taobao */
class NetworkStatCache implements INetworkStat {
    private static final int MAX_SIZE = 100;
    private static final String RESET_STAT = "{\"oneWayTime\" : 0, \"totalSize\" : 0}";
    private Map<String, String> lruCache;

    /* compiled from: Taobao */
    private static class holder {
        public static NetworkStatCache instance = new NetworkStatCache();

        private holder() {
        }
    }

    /* synthetic */ NetworkStatCache(AnonymousClass1 anonymousClass1) {
        this();
    }

    public static NetworkStatCache getInstance() {
        return holder.instance;
    }

    private NetworkStatCache() {
        this.lruCache = Collections.synchronizedMap(new LinkedHashMap<String, String>() {
            protected boolean removeEldestEntry(Entry<String, String> entry) {
                return size() > 100;
            }
        });
    }

    public void put(String str, StatisticData statisticData) {
        if (!StringUtils.isBlank(str)) {
            StringBuilder stringBuilder = new StringBuilder(48);
            stringBuilder.append("{\"oneWayTime\" : ").append(statisticData.oneWayTime_ANet).append(", \"totalSize\" : ").append(statisticData.totalSize).append("}");
            this.lruCache.put(str, stringBuilder.toString());
        }
    }

    public void reset(String str) {
        if (this.lruCache.containsKey(str)) {
            this.lruCache.put(str, RESET_STAT);
        }
    }

    public String get(String str) {
        return (String) this.lruCache.get(str);
    }
}
