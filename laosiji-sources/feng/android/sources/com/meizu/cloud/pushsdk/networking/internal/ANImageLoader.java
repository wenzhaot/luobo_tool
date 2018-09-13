package com.meizu.cloud.pushsdk.networking.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.meizu.cloud.pushsdk.networking.AndroidNetworking;
import com.meizu.cloud.pushsdk.networking.cache.LruBitmapCache;
import com.meizu.cloud.pushsdk.networking.common.ANRequest;
import com.meizu.cloud.pushsdk.networking.error.ANError;
import com.meizu.cloud.pushsdk.networking.interfaces.BitmapRequestListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class ANImageLoader {
    private static final int cacheSize = (maxMemory / 8);
    private static final int maxMemory = ((int) (Runtime.getRuntime().maxMemory() / 1024));
    private static ANImageLoader sInstance;
    private int mBatchResponseDelayMs = 100;
    private final HashMap<String, BatchedImageRequest> mBatchedResponses = new HashMap();
    private final ImageCache mCache;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final HashMap<String, BatchedImageRequest> mInFlightRequests = new HashMap();
    private Runnable mRunnable;

    public interface ImageCache {
        void evictAllBitmap();

        void evictBitmap(String str);

        Bitmap getBitmap(String str);

        void putBitmap(String str, Bitmap bitmap);
    }

    public interface ImageListener {
        void onError(ANError aNError);

        void onResponse(ImageContainer imageContainer, boolean z);
    }

    private class BatchedImageRequest {
        private ANError mANError;
        private final LinkedList<ImageContainer> mContainers = new LinkedList();
        private final ANRequest mRequest;
        private Bitmap mResponseBitmap;

        public BatchedImageRequest(ANRequest request, ImageContainer container) {
            this.mRequest = request;
            this.mContainers.add(container);
        }

        public void setError(ANError anError) {
            this.mANError = anError;
        }

        public ANError getError() {
            return this.mANError;
        }

        public void addContainer(ImageContainer container) {
            this.mContainers.add(container);
        }

        public boolean removeContainerAndCancelIfNecessary(ImageContainer container) {
            this.mContainers.remove(container);
            if (this.mContainers.size() != 0) {
                return false;
            }
            this.mRequest.cancel(true);
            if (!this.mRequest.isCanceled()) {
                return true;
            }
            this.mRequest.destroy();
            ANRequestQueue.getInstance().finish(this.mRequest);
            return true;
        }
    }

    public class ImageContainer {
        private Bitmap mBitmap;
        private final String mCacheKey;
        private final ImageListener mListener;
        private final String mRequestUrl;

        public ImageContainer(Bitmap bitmap, String requestUrl, String cacheKey, ImageListener listener) {
            this.mBitmap = bitmap;
            this.mRequestUrl = requestUrl;
            this.mCacheKey = cacheKey;
            this.mListener = listener;
        }

        public void cancelRequest() {
            if (this.mListener != null) {
                BatchedImageRequest request = (BatchedImageRequest) ANImageLoader.this.mInFlightRequests.get(this.mCacheKey);
                if (request == null) {
                    request = (BatchedImageRequest) ANImageLoader.this.mBatchedResponses.get(this.mCacheKey);
                    if (request != null) {
                        request.removeContainerAndCancelIfNecessary(this);
                        if (request.mContainers.size() == 0) {
                            ANImageLoader.this.mBatchedResponses.remove(this.mCacheKey);
                        }
                    }
                } else if (request.removeContainerAndCancelIfNecessary(this)) {
                    ANImageLoader.this.mInFlightRequests.remove(this.mCacheKey);
                }
            }
        }

        public Bitmap getBitmap() {
            return this.mBitmap;
        }

        public String getRequestUrl() {
            return this.mRequestUrl;
        }
    }

    public static void initialize() {
        getInstance();
    }

    public static ANImageLoader getInstance() {
        if (sInstance == null) {
            synchronized (ANImageLoader.class) {
                if (sInstance == null) {
                    sInstance = new ANImageLoader(new LruBitmapCache(cacheSize));
                }
            }
        }
        return sInstance;
    }

    public ANImageLoader(ImageCache imageCache) {
        this.mCache = imageCache;
    }

    public ImageCache getImageCache() {
        return this.mCache;
    }

    public static ImageListener getImageListener(final ImageView view, final int defaultImageResId, final int errorImageResId) {
        return new ImageListener() {
            public void onResponse(ImageContainer response, boolean isImmediate) {
                if (response.getBitmap() != null) {
                    view.setImageBitmap(response.getBitmap());
                } else if (defaultImageResId != 0) {
                    view.setImageResource(defaultImageResId);
                }
            }

            public void onError(ANError anError) {
                if (errorImageResId != 0) {
                    view.setImageResource(errorImageResId);
                }
            }
        };
    }

    public boolean isCached(String requestUrl, int maxWidth, int maxHeight) {
        return isCached(requestUrl, maxWidth, maxHeight, ScaleType.CENTER_INSIDE);
    }

    public boolean isCached(String requestUrl, int maxWidth, int maxHeight, ScaleType scaleType) {
        throwIfNotOnMainThread();
        return this.mCache.getBitmap(getCacheKey(requestUrl, maxWidth, maxHeight, scaleType)) != null;
    }

    public ImageContainer get(String requestUrl, ImageListener listener) {
        return get(requestUrl, listener, 0, 0);
    }

    public ImageContainer get(String requestUrl, ImageListener imageListener, int maxWidth, int maxHeight) {
        return get(requestUrl, imageListener, maxWidth, maxHeight, ScaleType.CENTER_INSIDE);
    }

    public ImageContainer get(String requestUrl, ImageListener imageListener, int maxWidth, int maxHeight, ScaleType scaleType) {
        throwIfNotOnMainThread();
        String cacheKey = getCacheKey(requestUrl, maxWidth, maxHeight, scaleType);
        Bitmap cachedBitmap = this.mCache.getBitmap(cacheKey);
        if (cachedBitmap != null) {
            ImageContainer container = new ImageContainer(cachedBitmap, requestUrl, null, null);
            imageListener.onResponse(container, true);
            return container;
        }
        ImageContainer imageContainer = new ImageContainer(null, requestUrl, cacheKey, imageListener);
        imageListener.onResponse(imageContainer, true);
        BatchedImageRequest request = (BatchedImageRequest) this.mInFlightRequests.get(cacheKey);
        if (request != null) {
            request.addContainer(imageContainer);
            return imageContainer;
        }
        this.mInFlightRequests.put(cacheKey, new BatchedImageRequest(makeImageRequest(requestUrl, maxWidth, maxHeight, scaleType, cacheKey), imageContainer));
        return imageContainer;
    }

    protected ANRequest makeImageRequest(String requestUrl, int maxWidth, int maxHeight, ScaleType scaleType, final String cacheKey) {
        ANRequest ANRequest = AndroidNetworking.get(requestUrl).setTag((Object) "ImageRequestTag").setBitmapMaxHeight(maxHeight).setBitmapMaxWidth(maxWidth).setImageScaleType(scaleType).setBitmapConfig(Config.RGB_565).build();
        ANRequest.getAsBitmap(new BitmapRequestListener() {
            public void onResponse(Bitmap response) {
                ANImageLoader.this.onGetImageSuccess(cacheKey, response);
            }

            public void onError(ANError anError) {
                ANImageLoader.this.onGetImageError(cacheKey, anError);
            }
        });
        return ANRequest;
    }

    public void setBatchedResponseDelay(int newBatchedResponseDelayMs) {
        this.mBatchResponseDelayMs = newBatchedResponseDelayMs;
    }

    protected void onGetImageSuccess(String cacheKey, Bitmap response) {
        this.mCache.putBitmap(cacheKey, response);
        BatchedImageRequest request = (BatchedImageRequest) this.mInFlightRequests.remove(cacheKey);
        if (request != null) {
            request.mResponseBitmap = response;
            batchResponse(cacheKey, request);
        }
    }

    protected void onGetImageError(String cacheKey, ANError anError) {
        BatchedImageRequest request = (BatchedImageRequest) this.mInFlightRequests.remove(cacheKey);
        if (request != null) {
            request.setError(anError);
            batchResponse(cacheKey, request);
        }
    }

    private void batchResponse(String cacheKey, BatchedImageRequest request) {
        this.mBatchedResponses.put(cacheKey, request);
        if (this.mRunnable == null) {
            this.mRunnable = new Runnable() {
                public void run() {
                    for (BatchedImageRequest bir : ANImageLoader.this.mBatchedResponses.values()) {
                        Iterator it = bir.mContainers.iterator();
                        while (it.hasNext()) {
                            ImageContainer container = (ImageContainer) it.next();
                            if (container.mListener != null) {
                                if (bir.getError() == null) {
                                    container.mBitmap = bir.mResponseBitmap;
                                    container.mListener.onResponse(container, false);
                                } else {
                                    container.mListener.onError(bir.getError());
                                }
                            }
                        }
                    }
                    ANImageLoader.this.mBatchedResponses.clear();
                    ANImageLoader.this.mRunnable = null;
                }
            };
            this.mHandler.postDelayed(this.mRunnable, (long) this.mBatchResponseDelayMs);
        }
    }

    private void throwIfNotOnMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("ImageLoader must be invoked from the main thread.");
        }
    }

    private static String getCacheKey(String url, int maxWidth, int maxHeight, ScaleType scaleType) {
        return new StringBuilder(url.length() + 12).append("#W").append(maxWidth).append("#H").append(maxHeight).append("#S").append(scaleType.ordinal()).append(url).toString();
    }
}
