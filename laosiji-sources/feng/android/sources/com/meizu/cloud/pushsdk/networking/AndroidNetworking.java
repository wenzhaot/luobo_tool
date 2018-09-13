package com.meizu.cloud.pushsdk.networking;

import android.content.Context;
import com.meizu.cloud.pushsdk.networking.common.ANLog;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.DeleteRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.DownloadBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.GetRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.HeadRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.MultiPartBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.PatchRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.PostRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ANRequest.PutRequestBuilder;
import com.meizu.cloud.pushsdk.networking.common.ConnectionClassManager;
import com.meizu.cloud.pushsdk.networking.common.ConnectionQuality;
import com.meizu.cloud.pushsdk.networking.core.Core;
import com.meizu.cloud.pushsdk.networking.interfaces.ConnectionQualityChangeListener;
import com.meizu.cloud.pushsdk.networking.interfaces.Parser.Factory;
import com.meizu.cloud.pushsdk.networking.internal.ANImageLoader;
import com.meizu.cloud.pushsdk.networking.internal.ANImageLoader.ImageCache;
import com.meizu.cloud.pushsdk.networking.internal.ANRequestQueue;
import com.meizu.cloud.pushsdk.networking.internal.InternalNetworking;

public class AndroidNetworking {
    private AndroidNetworking() {
    }

    public static void initialize(Context context) {
        ANRequestQueue.initialize();
        ANImageLoader.initialize();
    }

    public static void setConnectionQualityChangeListener(ConnectionQualityChangeListener connectionChangeListener) {
        ConnectionClassManager.getInstance().setListener(connectionChangeListener);
    }

    public static void removeConnectionQualityChangeListener() {
        ConnectionClassManager.getInstance().removeListener();
    }

    public static GetRequestBuilder get(String url) {
        return new GetRequestBuilder(url);
    }

    public static HeadRequestBuilder head(String url) {
        return new HeadRequestBuilder(url);
    }

    public static PostRequestBuilder post(String url) {
        return new PostRequestBuilder(url);
    }

    public static PutRequestBuilder put(String url) {
        return new PutRequestBuilder(url);
    }

    public static DeleteRequestBuilder delete(String url) {
        return new DeleteRequestBuilder(url);
    }

    public static PatchRequestBuilder patch(String url) {
        return new PatchRequestBuilder(url);
    }

    public static DownloadBuilder download(String url, String dirPath, String fileName) {
        return new DownloadBuilder(url, dirPath, fileName);
    }

    public static MultiPartBuilder upload(String url) {
        return new MultiPartBuilder(url);
    }

    public static void cancel(Object tag) {
        ANRequestQueue.getInstance().cancelRequestWithGivenTag(tag, false);
    }

    public static void forceCancel(Object tag) {
        ANRequestQueue.getInstance().cancelRequestWithGivenTag(tag, true);
    }

    public static void cancelAll() {
        ANRequestQueue.getInstance().cancelAll(false);
    }

    public static void forceCancelAll() {
        ANRequestQueue.getInstance().cancelAll(true);
    }

    public static void enableLogging() {
        ANLog.enableLogging();
    }

    public static void enableLogging(String tag) {
        ANLog.enableLogging();
        ANLog.setTag(tag);
    }

    public static void disableLogging() {
        ANLog.disableLogging();
    }

    public static void evictBitmap(String key) {
        ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
        if (imageCache != null && key != null) {
            imageCache.evictBitmap(key);
        }
    }

    public static void evictAllBitmap() {
        ImageCache imageCache = ANImageLoader.getInstance().getImageCache();
        if (imageCache != null) {
            imageCache.evictAllBitmap();
        }
    }

    public static void setUserAgent(String userAgent) {
        InternalNetworking.setUserAgent(userAgent);
    }

    public static int getCurrentBandwidth() {
        return ConnectionClassManager.getInstance().getCurrentBandwidth();
    }

    public static ConnectionQuality getCurrentConnectionQuality() {
        return ConnectionClassManager.getInstance().getCurrentConnectionQuality();
    }

    public static void setParserFactory(Factory parserFactory) {
    }

    public static void shutDown() {
        Core.shutDown();
        evictAllBitmap();
        ConnectionClassManager.getInstance().removeListener();
        ConnectionClassManager.shutDown();
    }
}
