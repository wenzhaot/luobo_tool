package com.facebook.imagepipeline.request;

import android.net.Uri;
import com.facebook.common.internal.Objects;
import com.facebook.imagepipeline.common.ImageDecodeOptions;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.common.ResizeOptions;
import java.io.File;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

@Immutable
public class ImageRequest {
    private final boolean mAutoRotateEnabled;
    private final CacheChoice mCacheChoice;
    private final ImageDecodeOptions mImageDecodeOptions;
    private final boolean mIsDiskCacheEnabled;
    private final boolean mLocalThumbnailPreviewsEnabled;
    private final RequestLevel mLowestPermittedRequestLevel;
    private final Postprocessor mPostprocessor;
    private final boolean mProgressiveRenderingEnabled;
    private final Priority mRequestPriority;
    @Nullable
    ResizeOptions mResizeOptions = null;
    private File mSourceFile;
    private final Uri mSourceUri;

    public enum CacheChoice {
        SMALL,
        DEFAULT
    }

    public enum RequestLevel {
        FULL_FETCH(1),
        DISK_CACHE(2),
        ENCODED_MEMORY_CACHE(3),
        BITMAP_MEMORY_CACHE(4);
        
        private int mValue;

        private RequestLevel(int value) {
            this.mValue = value;
        }

        public int getValue() {
            return this.mValue;
        }

        public static RequestLevel getMax(RequestLevel requestLevel1, RequestLevel requestLevel2) {
            return requestLevel1.getValue() > requestLevel2.getValue() ? requestLevel1 : requestLevel2;
        }
    }

    public static ImageRequest fromUri(@Nullable Uri uri) {
        return uri == null ? null : ImageRequestBuilder.newBuilderWithSource(uri).build();
    }

    public static ImageRequest fromUri(@Nullable String uriString) {
        return (uriString == null || uriString.length() == 0) ? null : fromUri(Uri.parse(uriString));
    }

    protected ImageRequest(ImageRequestBuilder builder) {
        this.mCacheChoice = builder.getCacheChoice();
        this.mSourceUri = builder.getSourceUri();
        this.mProgressiveRenderingEnabled = builder.isProgressiveRenderingEnabled();
        this.mLocalThumbnailPreviewsEnabled = builder.isLocalThumbnailPreviewsEnabled();
        this.mImageDecodeOptions = builder.getImageDecodeOptions();
        this.mResizeOptions = builder.getResizeOptions();
        this.mAutoRotateEnabled = builder.isAutoRotateEnabled();
        this.mRequestPriority = builder.getRequestPriority();
        this.mLowestPermittedRequestLevel = builder.getLowestPermittedRequestLevel();
        this.mIsDiskCacheEnabled = builder.isDiskCacheEnabled();
        this.mPostprocessor = builder.getPostprocessor();
    }

    public CacheChoice getCacheChoice() {
        return this.mCacheChoice;
    }

    public Uri getSourceUri() {
        return this.mSourceUri;
    }

    public int getPreferredWidth() {
        return this.mResizeOptions != null ? this.mResizeOptions.width : 2048;
    }

    public int getPreferredHeight() {
        return this.mResizeOptions != null ? this.mResizeOptions.height : 2048;
    }

    @Nullable
    public ResizeOptions getResizeOptions() {
        return this.mResizeOptions;
    }

    public ImageDecodeOptions getImageDecodeOptions() {
        return this.mImageDecodeOptions;
    }

    public boolean getAutoRotateEnabled() {
        return this.mAutoRotateEnabled;
    }

    public boolean getProgressiveRenderingEnabled() {
        return this.mProgressiveRenderingEnabled;
    }

    public boolean getLocalThumbnailPreviewsEnabled() {
        return this.mLocalThumbnailPreviewsEnabled;
    }

    public Priority getPriority() {
        return this.mRequestPriority;
    }

    public RequestLevel getLowestPermittedRequestLevel() {
        return this.mLowestPermittedRequestLevel;
    }

    public boolean isDiskCacheEnabled() {
        return this.mIsDiskCacheEnabled;
    }

    public synchronized File getSourceFile() {
        if (this.mSourceFile == null) {
            this.mSourceFile = new File(this.mSourceUri.getPath());
        }
        return this.mSourceFile;
    }

    @Nullable
    public Postprocessor getPostprocessor() {
        return this.mPostprocessor;
    }

    public boolean equals(Object o) {
        if (!(o instanceof ImageRequest)) {
            return false;
        }
        ImageRequest request = (ImageRequest) o;
        if (Objects.equal(this.mSourceUri, request.mSourceUri) && Objects.equal(this.mCacheChoice, request.mCacheChoice) && Objects.equal(this.mSourceFile, request.mSourceFile)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return Objects.hashCode(this.mCacheChoice, this.mSourceUri, this.mSourceFile);
    }
}
