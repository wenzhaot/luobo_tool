package com.facebook.imagepipeline.animated.impl;

import android.net.Uri;
import android.support.v4.view.PointerIconCompat;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.cache.CountingMemoryCache;
import com.facebook.imagepipeline.cache.CountingMemoryCache.EntryStateObserver;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedFrameCache {
    private final CountingMemoryCache<CacheKey, CloseableImage> mBackingCache;
    private final EntryStateObserver<CacheKey> mEntryStateObserver = new EntryStateObserver<CacheKey>() {
        public void onExclusivityChanged(CacheKey key, boolean isExclusive) {
            AnimatedFrameCache.this.onReusabilityChange(key, isExclusive);
        }
    };
    @GuardedBy("this")
    private final LinkedHashSet<CacheKey> mFreeItemsPool = new LinkedHashSet();
    private final CacheKey mImageCacheKey;

    @VisibleForTesting
    static class FrameKey implements CacheKey {
        private final int mFrameIndex;
        private final CacheKey mImageCacheKey;

        public FrameKey(CacheKey imageCacheKey, int frameIndex) {
            this.mImageCacheKey = imageCacheKey;
            this.mFrameIndex = frameIndex;
        }

        public String toString() {
            return Objects.toStringHelper((Object) this).add("imageCacheKey", this.mImageCacheKey).add("frameIndex", this.mFrameIndex).toString();
        }

        public boolean equals(Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof FrameKey)) {
                return false;
            }
            FrameKey that = (FrameKey) o;
            if (this.mImageCacheKey == that.mImageCacheKey && this.mFrameIndex == that.mFrameIndex) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (this.mImageCacheKey.hashCode() * PointerIconCompat.TYPE_ALL_SCROLL) + this.mFrameIndex;
        }

        public boolean containsUri(Uri uri) {
            return this.mImageCacheKey.containsUri(uri);
        }
    }

    public AnimatedFrameCache(CacheKey imageCacheKey, CountingMemoryCache<CacheKey, CloseableImage> backingCache) {
        this.mImageCacheKey = imageCacheKey;
        this.mBackingCache = backingCache;
    }

    public synchronized void onReusabilityChange(CacheKey key, boolean isReusable) {
        if (isReusable) {
            this.mFreeItemsPool.add(key);
        } else {
            this.mFreeItemsPool.remove(key);
        }
    }

    @Nullable
    public CloseableReference<CloseableImage> cache(int frameIndex, CloseableReference<CloseableImage> imageRef) {
        return this.mBackingCache.cache(keyFor(frameIndex), imageRef, this.mEntryStateObserver);
    }

    @Nullable
    public CloseableReference<CloseableImage> get(int frameIndex) {
        return this.mBackingCache.get(keyFor(frameIndex));
    }

    @Nullable
    public CloseableReference<CloseableImage> getForReuse() {
        CloseableReference<CloseableImage> imageRef;
        do {
            CacheKey key = popFirstFreeItemKey();
            if (key == null) {
                return null;
            }
            imageRef = this.mBackingCache.reuse(key);
        } while (imageRef == null);
        return imageRef;
    }

    @Nullable
    private synchronized CacheKey popFirstFreeItemKey() {
        CacheKey cacheKey;
        cacheKey = null;
        Iterator<CacheKey> iterator = this.mFreeItemsPool.iterator();
        if (iterator.hasNext()) {
            cacheKey = (CacheKey) iterator.next();
            iterator.remove();
        }
        return cacheKey;
    }

    private FrameKey keyFor(int frameIndex) {
        return new FrameKey(this.mImageCacheKey, frameIndex);
    }
}
