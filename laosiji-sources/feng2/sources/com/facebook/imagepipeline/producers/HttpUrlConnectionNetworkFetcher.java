package com.facebook.imagepipeline.producers;

import android.net.Uri;
import anet.channel.util.HttpConstant;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.NetworkFetcher.Callback;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HttpUrlConnectionNetworkFetcher extends BaseNetworkFetcher<FetchState> {
    public static final int HTTP_PERMANENT_REDIRECT = 308;
    public static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final int MAX_REDIRECTS = 5;
    private static final int NUM_NETWORK_THREADS = 3;
    private final ExecutorService mExecutorService;

    public HttpUrlConnectionNetworkFetcher() {
        this(Executors.newFixedThreadPool(3));
    }

    @VisibleForTesting
    HttpUrlConnectionNetworkFetcher(ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    public FetchState createFetchState(Consumer<EncodedImage> consumer, ProducerContext context) {
        return new FetchState(consumer, context);
    }

    public void fetch(final FetchState fetchState, final Callback callback) {
        final Future<?> future = this.mExecutorService.submit(new Runnable() {
            public void run() {
                HttpUrlConnectionNetworkFetcher.this.fetchSync(fetchState, callback);
            }
        });
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                if (future.cancel(false)) {
                    callback.onCancellation();
                }
            }
        });
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    @com.facebook.common.internal.VisibleForTesting
    void fetchSync(com.facebook.imagepipeline.producers.FetchState r5, com.facebook.imagepipeline.producers.NetworkFetcher.Callback r6) {
        /*
        r4 = this;
        r0 = 0;
        r2 = r5.getUri();	 Catch:{ IOException -> 0x001a }
        r3 = 5;
        r0 = r4.downloadFrom(r2, r3);	 Catch:{ IOException -> 0x001a }
        if (r0 == 0) goto L_0x0014;
    L_0x000c:
        r2 = r0.getInputStream();	 Catch:{ IOException -> 0x001a }
        r3 = -1;
        r6.onResponse(r2, r3);	 Catch:{ IOException -> 0x001a }
    L_0x0014:
        if (r0 == 0) goto L_0x0019;
    L_0x0016:
        r0.disconnect();
    L_0x0019:
        return;
    L_0x001a:
        r1 = move-exception;
        r6.onFailure(r1);	 Catch:{ all -> 0x0024 }
        if (r0 == 0) goto L_0x0019;
    L_0x0020:
        r0.disconnect();
        goto L_0x0019;
    L_0x0024:
        r2 = move-exception;
        if (r0 == 0) goto L_0x002a;
    L_0x0027:
        r0.disconnect();
    L_0x002a:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher.fetchSync(com.facebook.imagepipeline.producers.FetchState, com.facebook.imagepipeline.producers.NetworkFetcher$Callback):void");
    }

    private HttpURLConnection downloadFrom(Uri uri, int maxRedirects) throws IOException {
        HttpURLConnection connection = openConnectionTo(uri);
        int responseCode = connection.getResponseCode();
        if (isHttpSuccess(responseCode)) {
            return connection;
        }
        if (isHttpRedirect(responseCode)) {
            String nextUriString = connection.getHeaderField(HttpConstant.LOCATION);
            connection.disconnect();
            Uri nextUri = nextUriString == null ? null : Uri.parse(nextUriString);
            String originalScheme = uri.getScheme();
            if (maxRedirects > 0 && nextUri != null && !nextUri.getScheme().equals(originalScheme)) {
                return downloadFrom(nextUri, maxRedirects - 1);
            }
            String message;
            if (maxRedirects == 0) {
                message = error("URL %s follows too many redirects", uri.toString());
            } else {
                message = error("URL %s returned %d without a valid redirect", uri.toString(), Integer.valueOf(responseCode));
            }
            throw new IOException(message);
        }
        connection.disconnect();
        throw new IOException(String.format("Image URL %s returned HTTP code %d", new Object[]{uri.toString(), Integer.valueOf(responseCode)}));
    }

    @VisibleForTesting
    static HttpURLConnection openConnectionTo(Uri uri) throws IOException {
        return (HttpURLConnection) new URL(uri.toString()).openConnection();
    }

    private static boolean isHttpSuccess(int responseCode) {
        return responseCode >= 200 && responseCode < GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
    }

    private static boolean isHttpRedirect(int responseCode) {
        switch (responseCode) {
            case GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION /*300*/:
            case 301:
            case 302:
            case 303:
            case HTTP_TEMPORARY_REDIRECT /*307*/:
            case HTTP_PERMANENT_REDIRECT /*308*/:
                return true;
            default:
                return false;
        }
    }

    private static String error(String format, Object... args) {
        return String.format(Locale.getDefault(), format, args);
    }
}
