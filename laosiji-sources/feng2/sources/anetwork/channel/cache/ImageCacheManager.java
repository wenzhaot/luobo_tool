package anetwork.channel.cache;

import anet.channel.util.HttpConstant;
import anet.channel.util.a;
import anetwork.channel.cache.Cache.Entry;
import com.facebook.common.time.Clock;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Deprecated
/* compiled from: Taobao */
public class ImageCacheManager {
    private static Set<String> imgExtSet = new HashSet();

    static {
        imgExtSet.add("jpg");
        imgExtSet.add("gif");
        imgExtSet.add("png");
        imgExtSet.add("webp");
    }

    public static void setCache(final ImageCache imageCache) {
        if (imageCache != null) {
            CacheManager.addCache(new Cache() {
                public Entry get(String str) {
                    byte[] bArr = imageCache.get(str);
                    if (bArr == null) {
                        return null;
                    }
                    Entry entry = new Entry();
                    entry.ttl = Clock.MAX_TIME;
                    entry.data = bArr;
                    entry.responseHeaders = new HashMap();
                    entry.responseHeaders.put(null, Arrays.asList(new String[]{"HTTP/1.1 200 OK"}));
                    entry.responseHeaders.put(HttpConstant.CACHE_CONTROL, Arrays.asList(new String[]{"no-store"}));
                    return entry;
                }

                public void put(String str, Entry entry) {
                    imageCache.put(str, entry.data);
                }

                public void clear() {
                }
            }, new CachePrediction() {
                public boolean handleCache(String str, Map<String, String> map) {
                    if ("wv_h5".equals(map.get(HttpConstant.F_REFER))) {
                        try {
                            String a = a.a(new URL(str));
                            if (a != null && ImageCacheManager.imgExtSet.contains(a)) {
                                return true;
                            }
                            a = (String) map.get(HttpConstant.ACCEPT);
                            if (a != null && a.contains("image/")) {
                                return true;
                            }
                        } catch (Exception e) {
                            return false;
                        }
                    }
                    return false;
                }
            }, -1);
        }
    }
}
