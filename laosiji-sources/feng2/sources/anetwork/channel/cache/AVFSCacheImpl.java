package anetwork.channel.cache;

import android.support.v4.media.session.PlaybackStateCompat;
import anet.channel.util.ALog;
import anet.channel.util.StringUtils;
import anetwork.channel.cache.Cache.Entry;
import com.taobao.alivfssdk.cache.AVFSCache;
import com.taobao.alivfssdk.cache.AVFSCacheConfig;
import com.taobao.alivfssdk.cache.AVFSCacheManager;
import com.taobao.alivfssdk.cache.IAVFSCache;
import com.taobao.alivfssdk.cache.IAVFSCache.OnAllObjectRemoveCallback;
import com.taobao.alivfssdk.cache.IAVFSCache.OnObjectSetCallback;

/* compiled from: Taobao */
class AVFSCacheImpl implements Cache {
    private static final String MODULE_NAME = "networksdk.httpcache";
    private static final String TAG = "anet.AVFSCacheImpl";
    private static boolean isAvfsCacheExist;
    private static Object nullAllObjectRemoveCallback;
    private static Object nullObjectSetCallback;

    AVFSCacheImpl() {
    }

    static {
        isAvfsCacheExist = true;
        nullObjectSetCallback = null;
        nullAllObjectRemoveCallback = null;
        try {
            Class.forName("com.taobao.alivfssdk.cache.AVFSCacheManager");
            nullObjectSetCallback = new OnObjectSetCallback() {
                public void onObjectSetCallback(String str, boolean z) {
                }
            };
            nullAllObjectRemoveCallback = new OnAllObjectRemoveCallback() {
                public void onAllObjectRemoveCallback(boolean z) {
                }
            };
        } catch (ClassNotFoundException e) {
            isAvfsCacheExist = false;
            ALog.w(TAG, "no alivfs sdk!", null, new Object[0]);
        }
    }

    public void initialize() {
        if (isAvfsCacheExist) {
            AVFSCache cacheForModule = AVFSCacheManager.getInstance().cacheForModule(MODULE_NAME);
            if (cacheForModule != null) {
                AVFSCacheConfig aVFSCacheConfig = new AVFSCacheConfig();
                aVFSCacheConfig.limitSize = Long.valueOf(5242880);
                aVFSCacheConfig.fileMemMaxSize = PlaybackStateCompat.ACTION_SET_CAPTIONING_ENABLED;
                cacheForModule.moduleConfig(aVFSCacheConfig);
            }
        }
    }

    public Entry get(String str) {
        if (!isAvfsCacheExist) {
            return null;
        }
        try {
            IAVFSCache fileCache = getFileCache();
            if (fileCache != null) {
                return (Entry) fileCache.objectForKey(StringUtils.md5ToHex(str));
            }
        } catch (Throwable e) {
            ALog.e(TAG, "get cache failed", null, e, new Object[0]);
        }
        return null;
    }

    public void put(String str, Entry entry) {
        if (isAvfsCacheExist) {
            try {
                IAVFSCache fileCache = getFileCache();
                if (fileCache != null) {
                    fileCache.setObjectForKey(StringUtils.md5ToHex(str), entry, (OnObjectSetCallback) nullObjectSetCallback);
                }
            } catch (Throwable e) {
                ALog.e(TAG, "put cache failed", null, e, new Object[0]);
            }
        }
    }

    public void clear() {
        if (isAvfsCacheExist) {
            try {
                IAVFSCache fileCache = getFileCache();
                if (fileCache != null) {
                    fileCache.removeAllObject((OnAllObjectRemoveCallback) nullAllObjectRemoveCallback);
                }
            } catch (Throwable e) {
                ALog.e(TAG, "clear cache failed", null, e, new Object[0]);
            }
        }
    }

    private IAVFSCache getFileCache() {
        AVFSCache cacheForModule = AVFSCacheManager.getInstance().cacheForModule(MODULE_NAME);
        if (cacheForModule != null) {
            return cacheForModule.getFileCache();
        }
        return null;
    }
}
