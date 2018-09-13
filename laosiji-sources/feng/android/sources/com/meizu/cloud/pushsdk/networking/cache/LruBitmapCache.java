package com.meizu.cloud.pushsdk.networking.cache;

import android.graphics.Bitmap;
import com.meizu.cloud.pushsdk.networking.internal.ANImageLoader.ImageCache;

public class LruBitmapCache extends LruCache<String, Bitmap> implements ImageCache {
    public LruBitmapCache(int maxSize) {
        super(maxSize);
    }

    protected int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight();
    }

    public Bitmap getBitmap(String key) {
        return (Bitmap) get(key);
    }

    public void evictBitmap(String key) {
        remove(key);
    }

    public void evictAllBitmap() {
        evictAll();
    }

    public void putBitmap(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
