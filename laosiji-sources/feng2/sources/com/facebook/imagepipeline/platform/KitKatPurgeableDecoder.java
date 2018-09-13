package com.facebook.imagepipeline.platform;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.memory.FlexByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import javax.annotation.concurrent.ThreadSafe;

@TargetApi(19)
@ThreadSafe
public class KitKatPurgeableDecoder extends DalvikPurgeableDecoder {
    private final FlexByteArrayPool mFlexByteArrayPool;

    public KitKatPurgeableDecoder(FlexByteArrayPool flexByteArrayPool) {
        this.mFlexByteArrayPool = flexByteArrayPool;
    }

    protected Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> bytesRef, Options options) {
        PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) bytesRef.get();
        int length = pooledByteBuffer.size();
        CloseableReference encodedBytesArrayRef = this.mFlexByteArrayPool.get(length);
        try {
            byte[] encodedBytesArray = (byte[]) encodedBytesArrayRef.get();
            pooledByteBuffer.read(0, encodedBytesArray, 0, length);
            Bitmap bitmap = (Bitmap) Preconditions.checkNotNull(BitmapFactory.decodeByteArray(encodedBytesArray, 0, length, options), "BitmapFactory returned null");
            return bitmap;
        } finally {
            CloseableReference.closeSafely(encodedBytesArrayRef);
        }
    }

    protected Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> bytesRef, int length, Options options) {
        boolean z = false;
        byte[] suffix = DalvikPurgeableDecoder.endsWithEOI(bytesRef, length) ? null : EOI;
        PooledByteBuffer pooledByteBuffer = (PooledByteBuffer) bytesRef.get();
        if (length <= pooledByteBuffer.size()) {
            z = true;
        }
        Preconditions.checkArgument(z);
        CloseableReference encodedBytesArrayRef = this.mFlexByteArrayPool.get(length + 2);
        try {
            byte[] encodedBytesArray = (byte[]) encodedBytesArrayRef.get();
            pooledByteBuffer.read(0, encodedBytesArray, 0, length);
            if (suffix != null) {
                putEOI(encodedBytesArray, length);
                length += 2;
            }
            Bitmap bitmap = (Bitmap) Preconditions.checkNotNull(BitmapFactory.decodeByteArray(encodedBytesArray, 0, length, options), "BitmapFactory returned null");
            return bitmap;
        } finally {
            CloseableReference.closeSafely(encodedBytesArrayRef);
        }
    }

    private static void putEOI(byte[] imageBytes, int offset) {
        imageBytes[offset] = (byte) -1;
        imageBytes[offset + 1] = (byte) -39;
    }
}
