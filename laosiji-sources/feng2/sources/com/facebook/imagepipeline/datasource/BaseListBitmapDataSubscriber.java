package com.facebook.imagepipeline.datasource;

import android.graphics.Bitmap;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.BaseDataSubscriber;
import com.facebook.datasource.DataSource;
import com.facebook.imagepipeline.image.CloseableBitmap;
import com.facebook.imagepipeline.image.CloseableImage;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseListBitmapDataSubscriber extends BaseDataSubscriber<List<CloseableReference<CloseableImage>>> {
    protected abstract void onNewResultListImpl(List<Bitmap> list);

    public void onNewResultImpl(DataSource<List<CloseableReference<CloseableImage>>> dataSource) {
        if (dataSource.isFinished()) {
            List<CloseableReference<CloseableImage>> imageRefList = (List) dataSource.getResult();
            if (imageRefList == null) {
                onNewResultListImpl(null);
                return;
            }
            try {
                List<Bitmap> bitmapList = new ArrayList(imageRefList.size());
                for (CloseableReference<CloseableImage> closeableImageRef : imageRefList) {
                    if (closeableImageRef == null || !(closeableImageRef.get() instanceof CloseableBitmap)) {
                        bitmapList.add(null);
                    } else {
                        bitmapList.add(((CloseableBitmap) closeableImageRef.get()).getUnderlyingBitmap());
                    }
                }
                onNewResultListImpl(bitmapList);
            } finally {
                for (CloseableReference closeableImageRef2 : imageRefList) {
                    CloseableReference.closeSafely(closeableImageRef2);
                }
            }
        }
    }
}
