package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Pair;
import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.imageformat.ImageFormat;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.BitmapUtil;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;

public class LocalExifThumbnailProducer implements ThumbnailProducer<EncodedImage> {
    private static final int COMMON_EXIF_THUMBNAIL_MAX_DIMENSION = 512;
    @VisibleForTesting
    static final String CREATED_THUMBNAIL = "createdThumbnail";
    @VisibleForTesting
    static final String PRODUCER_NAME = "LocalExifThumbnailProducer";
    private final ContentResolver mContentResolver;
    private final Executor mExecutor;
    private final PooledByteBufferFactory mPooledByteBufferFactory;

    public LocalExifThumbnailProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver) {
        this.mExecutor = executor;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mContentResolver = contentResolver;
    }

    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(512, 512, resizeOptions);
    }

    public void produceResults(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        ProducerListener listener = producerContext.getListener();
        String requestId = producerContext.getId();
        final ImageRequest imageRequest = producerContext.getImageRequest();
        final StatefulProducerRunnable cancellableProducerRunnable = new StatefulProducerRunnable<EncodedImage>(consumer, listener, PRODUCER_NAME, requestId) {
            protected EncodedImage getResult() throws Exception {
                ExifInterface exifInterface = LocalExifThumbnailProducer.this.getExifInterface(imageRequest.getSourceUri());
                if (exifInterface == null || !exifInterface.hasThumbnail()) {
                    return null;
                }
                return LocalExifThumbnailProducer.this.buildEncodedImage(LocalExifThumbnailProducer.this.mPooledByteBufferFactory.newByteBuffer(exifInterface.getThumbnail()), exifInterface);
            }

            protected void disposeResult(EncodedImage result) {
                EncodedImage.closeSafely(result);
            }

            protected Map<String, String> getExtraMapOnSuccess(EncodedImage result) {
                return ImmutableMap.of(LocalExifThumbnailProducer.CREATED_THUMBNAIL, Boolean.toString(result != null));
            }
        };
        producerContext.addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                cancellableProducerRunnable.cancel();
            }
        });
        this.mExecutor.execute(cancellableProducerRunnable);
    }

    @VisibleForTesting
    ExifInterface getExifInterface(Uri uri) throws IOException {
        String realPath = UriUtil.getRealPathFromUri(this.mContentResolver, uri);
        if (canReadAsFile(realPath)) {
            return new ExifInterface(realPath);
        }
        return null;
    }

    private EncodedImage buildEncodedImage(PooledByteBuffer imageBytes, ExifInterface exifInterface) {
        int width;
        int height = -1;
        Pair<Integer, Integer> dimensions = BitmapUtil.decodeDimensions(new PooledByteBufferInputStream(imageBytes));
        int rotationAngle = getRotationAngle(exifInterface);
        if (dimensions != null) {
            width = ((Integer) dimensions.first).intValue();
        } else {
            width = -1;
        }
        if (dimensions != null) {
            height = ((Integer) dimensions.second).intValue();
        }
        CloseableReference closeableByteBuffer = CloseableReference.of(imageBytes);
        try {
            EncodedImage encodedImage = new EncodedImage(closeableByteBuffer);
            encodedImage.setImageFormat(ImageFormat.JPEG);
            encodedImage.setRotationAngle(rotationAngle);
            encodedImage.setWidth(width);
            encodedImage.setHeight(height);
            return encodedImage;
        } finally {
            CloseableReference.closeSafely(closeableByteBuffer);
        }
    }

    private int getRotationAngle(ExifInterface exifInterface) {
        return JfifUtil.getAutoRotateAngleFromOrientation(Integer.parseInt(exifInterface.getAttribute("Orientation")));
    }

    @VisibleForTesting
    boolean canReadAsFile(String realPath) throws IOException {
        if (realPath == null) {
            return false;
        }
        File file = new File(realPath);
        if (file.exists() && file.canRead()) {
            return true;
        }
        return false;
    }
}
