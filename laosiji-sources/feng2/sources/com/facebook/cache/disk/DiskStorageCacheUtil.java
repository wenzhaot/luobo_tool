package com.facebook.cache.disk;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.facebook.common.logging.FLog;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import javax.annotation.Nullable;

public final class DiskStorageCacheUtil {
    private static final String TAG = DiskStorageCacheUtil.class.getSimpleName();

    private DiskStorageCacheUtil() {
    }

    protected static void addDiskCacheEntry(Integer hashKey, String resourceId, SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit();
        editor.putString(String.valueOf(hashKey), resourceId);
        editor.apply();
    }

    protected static void deleteDiskCacheEntry(Integer hashKey, SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit();
        editor.remove(String.valueOf(hashKey));
        editor.apply();
    }

    protected static synchronized Map<Integer, String> readStoredIndex(@Nullable SharedPreferences sharedPreferences, Set<String> resourceIndex) {
        Map<Integer, String> index;
        synchronized (DiskStorageCacheUtil.class) {
            index = new HashMap();
            if (sharedPreferences != null) {
                Map<String, ?> allEntries = sharedPreferences.getAll();
                Editor editor = sharedPreferences.edit();
                for (Entry<String, ?> entry : allEntries.entrySet()) {
                    if (entry.getValue() instanceof String) {
                        Integer key = Integer.valueOf(Integer.parseInt((String) entry.getKey()));
                        if (resourceIndex.contains(entry.getValue())) {
                            index.put(key, (String) entry.getValue());
                        } else {
                            editor.remove(String.valueOf(entry.getKey()));
                        }
                    } else {
                        FLog.e(TAG, "SharedPreference doesn't store right data type");
                    }
                }
                editor.apply();
            }
        }
        return index;
    }

    protected static void clearDiskEntries(SharedPreferences sharedPreferences) {
        Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
