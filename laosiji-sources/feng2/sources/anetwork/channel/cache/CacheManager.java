package anetwork.channel.cache;

import anet.channel.a.c;
import anet.channel.util.ALog;
import anet.channel.util.HttpConstant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/* compiled from: Taobao */
public class CacheManager {
    private static List<CacheItem> cacheList = new ArrayList();
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReadLock readLock = lock.readLock();
    private static final WriteLock writeLock = lock.writeLock();

    /* compiled from: Taobao */
    private static class CacheItem implements Comparable<CacheItem> {
        final Cache cache;
        final CachePrediction prediction;
        final int priority;

        CacheItem(Cache cache, CachePrediction cachePrediction, int i) {
            this.cache = cache;
            this.prediction = cachePrediction;
            this.priority = i;
        }

        public int compareTo(CacheItem cacheItem) {
            return this.priority - cacheItem.priority;
        }
    }

    static {
        c.a(new Runnable() {
            public void run() {
                try {
                    Cache aVFSCacheImpl = new AVFSCacheImpl();
                    aVFSCacheImpl.initialize();
                    CacheManager.addCache(aVFSCacheImpl, new CachePrediction() {
                        public boolean handleCache(String str, Map<String, String> map) {
                            return "weex".equals(map.get(HttpConstant.F_REFER));
                        }
                    }, 1);
                } catch (Exception e) {
                }
            }
        });
    }

    public static void addCache(Cache cache, CachePrediction cachePrediction, int i) {
        if (cache == null) {
            try {
                throw new IllegalArgumentException("cache is null");
            } catch (Throwable th) {
                writeLock.unlock();
            }
        } else if (cachePrediction == null) {
            throw new IllegalArgumentException("prediction is null");
        } else {
            writeLock.lock();
            cacheList.add(new CacheItem(cache, cachePrediction, i));
            Collections.sort(cacheList);
            writeLock.unlock();
        }
    }

    public static void removeCache(Cache cache) {
        try {
            writeLock.lock();
            ListIterator listIterator = cacheList.listIterator();
            while (listIterator.hasNext()) {
                if (((CacheItem) listIterator.next()).cache == cache) {
                    listIterator.remove();
                    break;
                }
            }
            writeLock.unlock();
        } catch (Throwable th) {
            writeLock.unlock();
        }
    }

    public static Cache getCache(String str, Map<String, String> map) {
        try {
            readLock.lock();
            for (CacheItem cacheItem : cacheList) {
                if (cacheItem.prediction.handleCache(str, map)) {
                    Cache cache = cacheItem.cache;
                    return cache;
                }
            }
            readLock.unlock();
            return null;
        } finally {
            readLock.unlock();
        }
    }

    public static void clearAllCache() {
        ALog.w("anet.CacheManager", "clearAllCache", null, new Object[0]);
        for (CacheItem cacheItem : cacheList) {
            try {
                cacheItem.cache.clear();
            } catch (Exception e) {
            }
        }
    }
}
