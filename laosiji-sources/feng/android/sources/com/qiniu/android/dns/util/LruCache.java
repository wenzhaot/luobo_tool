package com.qiniu.android.dns.util;

import com.meizu.cloud.pushsdk.handler.impl.AbstractMessageHandler;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

public final class LruCache<K, V> extends LinkedHashMap<K, V> {
    private int size;

    public LruCache() {
        this(AbstractMessageHandler.MESSAGE_TYPE_PUSH_SWITCH_STATUS);
    }

    public LruCache(int size) {
        super(size, 1.0f, true);
        this.size = size;
    }

    protected boolean removeEldestEntry(Entry<K, V> entry) {
        return size() > this.size;
    }
}
