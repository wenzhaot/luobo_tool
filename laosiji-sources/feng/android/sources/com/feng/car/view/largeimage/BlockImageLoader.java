package com.feng.car.view.largeimage;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapRegionDecoder;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.feng.car.view.largeimage.factory.BitmapDecoderFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class BlockImageLoader {
    public static final int MESSAGE_BLOCK_1 = 1;
    public static final int MESSAGE_BLOCK_2 = 2;
    public static final int MESSAGE_LOAD = 666;
    public static final int MESSAGE_PIC = 665;
    private final int BASE_BLOCKSIZE;
    private Context context;
    private LoadHandler handler;
    private HandlerThread handlerThread;
    private volatile LoadData mLoadData;
    private Handler mainHandler;
    private OnImageLoadListener onImageLoadListener;
    private int preMessageWhat = 1;

    private static class CacheData {
        Map<Position, Bitmap> images;
        int scale;

        public CacheData(int scale, Map<Position, Bitmap> images) {
            this.scale = scale;
            this.images = images;
        }
    }

    public class DrawData {
        public Bitmap bitmap;
        public Rect imageRect;
        public Rect srcRect;

        public DrawData(Bitmap bitmap, Rect srcRect, Rect imageRect) {
            this.bitmap = bitmap;
            this.srcRect = srcRect;
            this.imageRect = imageRect;
        }
    }

    private static class LoadData {
        private List<CacheData> mCacheDatas = new LinkedList();
        private volatile Bitmap mCacheImageData;
        private volatile int mCacheImageScale;
        private volatile CacheData mCurrentCacheData;
        private volatile BitmapRegionDecoder mDecoder;
        private volatile BitmapDecoderFactory mFactory;
        private volatile int mImageHeight;
        private volatile int mImageWidth;

        public LoadData(BitmapDecoderFactory factory) {
            this.mFactory = factory;
        }
    }

    private class LoadHandler extends Handler {
        public LoadHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LoadData loadData = BlockImageLoader.this.mLoadData;
            Options decodingOptions;
            if (msg.what == 666) {
                if (loadData.mFactory != null) {
                    try {
                        loadData.mDecoder = loadData.mFactory.made();
                        loadData.mImageWidth = loadData.mDecoder.getWidth();
                        loadData.mImageHeight = loadData.mDecoder.getHeight();
                        final int imageWidth = loadData.mImageWidth;
                        final int imageHeight = loadData.mImageHeight;
                        BlockImageLoader.this.mainHandler.post(new Runnable() {
                            public void run() {
                                if (BlockImageLoader.this.onImageLoadListener != null) {
                                    BlockImageLoader.this.onImageLoadListener.onLoadImageSize(imageWidth, imageHeight);
                                }
                            }
                        });
                    } catch (final IOException e) {
                        e.printStackTrace();
                        BlockImageLoader.this.mainHandler.post(new Runnable() {
                            public void run() {
                                if (BlockImageLoader.this.onImageLoadListener != null) {
                                    BlockImageLoader.this.onImageLoadListener.onLoadFail(e);
                                }
                            }
                        });
                    }
                }
            } else if (msg.what == 665) {
                Integer scale = msg.obj;
                decodingOptions = new Options();
                decodingOptions.inSampleSize = scale.intValue();
                try {
                    loadData.mCacheImageData = loadData.mDecoder.decodeRegion(new Rect(0, 0, loadData.mImageWidth, loadData.mImageHeight), decodingOptions);
                    loadData.mCacheImageScale = scale.intValue();
                    BlockImageLoader.this.mainHandler.post(new Runnable() {
                        public void run() {
                            if (BlockImageLoader.this.onImageLoadListener != null) {
                                BlockImageLoader.this.onImageLoadListener.onBlockImageLoadFinished();
                            }
                        }
                    });
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            } else {
                MessageData data = msg.obj;
                CacheData cacheData = loadData.mCurrentCacheData;
                if (cacheData != null && cacheData.scale == data.scale) {
                    Position position = data.position;
                    if (((Bitmap) cacheData.images.get(position)) == null) {
                        int imageBlockSize = BlockImageLoader.this.BASE_BLOCKSIZE * data.scale;
                        int left = imageBlockSize * position.col;
                        int right = left + imageBlockSize;
                        int top = imageBlockSize * position.row;
                        int bottom = top + imageBlockSize;
                        if (right > loadData.mImageWidth) {
                            right = loadData.mImageWidth;
                        }
                        if (bottom > loadData.mImageHeight) {
                            bottom = loadData.mImageHeight;
                        }
                        Rect clipImageRect = new Rect(left, top, right, bottom);
                        decodingOptions = new Options();
                        decodingOptions.inSampleSize = data.scale;
                        try {
                            Bitmap imageData = loadData.mDecoder.decodeRegion(clipImageRect, decodingOptions);
                            if (imageData != null) {
                                cacheData.images.put(position, imageData);
                                BlockImageLoader.this.mainHandler.post(new Runnable() {
                                    public void run() {
                                        if (BlockImageLoader.this.onImageLoadListener != null) {
                                            BlockImageLoader.this.onImageLoadListener.onBlockImageLoadFinished();
                                        }
                                    }
                                });
                            }
                        } catch (Exception e22) {
                            e22.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static class MessageData {
        Position position;
        int scale;

        public MessageData(Position position, int scale) {
            this.position = position;
            this.scale = scale;
        }
    }

    private class NearComparator implements Comparator<CacheData> {
        private int scale;

        public NearComparator(int scale) {
            this.scale = scale;
        }

        public int compare(CacheData lhs, CacheData rhs) {
            int dScale = Math.abs(this.scale - lhs.scale) - Math.abs(this.scale - rhs.scale);
            if (dScale == 0) {
                if (lhs.scale > rhs.scale) {
                    return -1;
                }
                return 1;
            } else if (dScale >= 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    public interface OnImageLoadListener {
        void onBlockImageLoadFinished();

        void onLoadFail(Exception exception);

        void onLoadImageSize(int i, int i2);
    }

    private static class Position {
        int col;
        int row;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Position set(int row, int col) {
            this.row = row;
            this.col = col;
            return this;
        }

        public boolean equals(Object o) {
            if (!(o instanceof Position)) {
                return false;
            }
            Position position = (Position) o;
            if (this.row == position.row && this.col == position.col) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return ((this.row + 629) * 37) + this.col;
        }

        public String toString() {
            return "row:" + this.row + " col:" + this.col;
        }
    }

    public BlockImageLoader(Context context) {
        int i = 1;
        this.context = context;
        this.mainHandler = new Handler(Looper.getMainLooper());
        int size = (context.getResources().getDisplayMetrics().heightPixels / 2) + 1;
        if (size % 2 == 0) {
            i = 0;
        }
        this.BASE_BLOCKSIZE = i + size;
    }

    public void setOnImageLoadListener(OnImageLoadListener onImageLoadListener) {
        this.onImageLoadListener = onImageLoadListener;
    }

    public int getNearScale(float imageScale) {
        int scale = (int) imageScale;
        int startS = 1;
        if (scale > 2) {
            do {
                startS *= 2;
                scale /= 2;
            } while (scale > 2);
        }
        if (Math.abs(((float) startS) - imageScale) < Math.abs(((float) (startS * 2)) - imageScale)) {
            return startS;
        }
        return startS * 2;
    }

    public boolean hasLoad() {
        LoadData loadData = this.mLoadData;
        return (loadData == null || loadData.mDecoder == null) ? false : true;
    }

    private Rect madeRect(Bitmap bitmap, int row, int col, int scaleKey, float imageScale) {
        int size = scaleKey * this.BASE_BLOCKSIZE;
        Rect rect = new Rect();
        rect.left = col * size;
        rect.top = row * size;
        rect.right = rect.left + (bitmap.getWidth() * scaleKey);
        rect.bottom = rect.top + (bitmap.getHeight() * scaleKey);
        return rect;
    }

    public List<DrawData> getDrawData(float imageScale, Rect imageRect) {
        LoadData loadData = this.mLoadData;
        if (loadData == null || loadData.mDecoder == null) {
            return new ArrayList(0);
        }
        int imageWidth = loadData.mImageWidth;
        int imageHeight = loadData.mImageHeight;
        List<CacheData> cacheDatas = loadData.mCacheDatas;
        Bitmap cacheImageData = loadData.mCacheImageData;
        int cacheImageScale = loadData.mCacheImageScale;
        List<DrawData> drawDatas = new ArrayList();
        if (imageRect.left < 0) {
            imageRect.left = 0;
        }
        if (imageRect.top < 0) {
            imageRect.top = 0;
        }
        if (imageRect.right > loadData.mImageWidth) {
            imageRect.right = loadData.mImageWidth;
        }
        if (imageRect.bottom > loadData.mImageHeight) {
            imageRect.bottom = loadData.mImageHeight;
        }
        if (cacheImageData == null) {
            try {
                int s = (int) Math.sqrt((double) ((((1.0f * ((float) imageWidth)) * ((float) imageHeight)) / ((float) (this.context.getResources().getDisplayMetrics().widthPixels / 2))) / ((float) (this.context.getResources().getDisplayMetrics().heightPixels / 2))));
                cacheImageScale = getNearScale((float) s);
                if (cacheImageScale < s) {
                    cacheImageScale *= 2;
                }
                this.handler.sendMessage(this.handler.obtainMessage(MESSAGE_PIC, Integer.valueOf(cacheImageScale)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Rect rect = new Rect(imageRect);
            int cache = (int) (((float) dip2px(this.context, 100.0f)) * imageScale);
            rect.right += cache;
            rect.top -= cache;
            rect.left -= cache;
            rect.bottom += cache;
            if (rect.left < 0) {
                rect.left = 0;
            }
            if (rect.top < 0) {
                rect.top = 0;
            }
            if (rect.right > imageWidth) {
                rect.right = imageWidth;
            }
            if (rect.bottom > imageHeight) {
                rect.bottom = imageHeight;
            }
            Rect r = new Rect();
            r.left = (int) Math.abs((1.0f * ((float) rect.left)) / ((float) cacheImageScale));
            r.right = (int) Math.abs((1.0f * ((float) rect.right)) / ((float) cacheImageScale));
            r.top = (int) Math.abs((1.0f * ((float) rect.top)) / ((float) cacheImageScale));
            r.bottom = (int) Math.abs((1.0f * ((float) rect.bottom)) / ((float) cacheImageScale));
            drawDatas.add(new DrawData(cacheImageData, r, rect));
        }
        int scale = getNearScale(imageScale);
        if (cacheImageScale <= scale && cacheImageData != null) {
            return drawDatas;
        }
        Iterator<CacheData> iterator;
        CacheData cacheData;
        int row;
        int col;
        Bitmap bitmap;
        int blockSize = this.BASE_BLOCKSIZE * scale;
        int maxRow = (imageHeight / blockSize) + (imageHeight % blockSize == 0 ? 0 : 1);
        int maxCol = (imageWidth / blockSize) + (imageWidth % blockSize == 0 ? 0 : 1);
        int startRow = ((imageRect.top % blockSize == 0 ? 0 : 1) + (imageRect.top / blockSize)) - 1;
        int endRow = (imageRect.bottom / blockSize) + (imageRect.bottom % blockSize == 0 ? 0 : 1);
        int startCol = ((imageRect.left % blockSize == 0 ? 0 : 1) + (imageRect.left / blockSize)) - 1;
        int endCol = (imageRect.right / blockSize) + (imageRect.right % blockSize == 0 ? 0 : 1);
        if (startRow < 0) {
            startRow = 0;
        }
        if (startCol < 0) {
            startCol = 0;
        }
        if (endRow > maxRow) {
            endRow = maxRow;
        }
        if (endCol > maxCol) {
            endCol = maxCol;
        }
        int cacheStartRow = startRow - 1;
        int cacheEndRow = endRow + 1;
        int cacheStartCol = startCol - 1;
        int cacheEndCol = endCol + 1;
        if (cacheStartRow < 0) {
            cacheStartRow = 0;
        }
        if (cacheStartCol < 0) {
            cacheStartCol = 0;
        }
        if (cacheEndRow > maxRow) {
            cacheEndRow = maxRow;
        }
        if (cacheEndCol > maxCol) {
            cacheEndCol = maxCol;
        }
        Set<Position> needShowPositions = new HashSet();
        this.handler.removeMessages(this.preMessageWhat);
        int what = this.preMessageWhat == 1 ? 2 : 1;
        this.preMessageWhat = what;
        if (!(loadData.mCurrentCacheData == null || loadData.mCurrentCacheData.scale == scale)) {
            cacheDatas.add(new CacheData(loadData.mCurrentCacheData.scale, new HashMap(loadData.mCurrentCacheData.images)));
            loadData.mCurrentCacheData = null;
        }
        if (loadData.mCurrentCacheData == null) {
            iterator = cacheDatas.iterator();
            while (iterator.hasNext()) {
                cacheData = (CacheData) iterator.next();
                if (scale == cacheData.scale) {
                    loadData.mCurrentCacheData = new CacheData(scale, new ConcurrentHashMap(cacheData.images));
                    iterator.remove();
                }
            }
        }
        Position position;
        if (loadData.mCurrentCacheData == null) {
            loadData.mCurrentCacheData = new CacheData(scale, new ConcurrentHashMap());
            for (row = startRow; row < endRow; row++) {
                for (col = startCol; col < endCol; col++) {
                    position = new Position(row, col);
                    needShowPositions.add(position);
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                }
            }
            for (row = cacheStartRow; row < startRow; row++) {
                for (col = cacheStartCol; col < cacheEndCol; col++) {
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(new Position(row, col), scale)));
                }
            }
            for (row = endRow + 1; row < cacheEndRow; row++) {
                for (col = cacheStartCol; col < cacheEndCol; col++) {
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(new Position(row, col), scale)));
                }
            }
            for (row = startRow; row < endRow; row++) {
                for (col = cacheStartCol; col < startCol; col++) {
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(new Position(row, col), scale)));
                }
            }
            for (row = startRow; row < endRow; row++) {
                for (col = endRow + 1; col < cacheEndRow; col++) {
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(new Position(row, col), scale)));
                }
            }
        } else {
            Set<Position> usePositions = new HashSet();
            for (row = startRow; row < endRow; row++) {
                for (col = startCol; col < endCol; col++) {
                    position = new Position(row, col);
                    bitmap = (Bitmap) loadData.mCurrentCacheData.images.get(position);
                    if (bitmap == null) {
                        needShowPositions.add(position);
                        this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                    } else {
                        usePositions.add(position);
                        drawDatas.add(new DrawData(bitmap, null, madeRect(bitmap, row, col, scale, imageScale)));
                    }
                }
            }
            for (row = cacheStartRow; row < startRow; row++) {
                for (col = cacheStartCol; col < cacheEndCol; col++) {
                    position = new Position(row, col);
                    usePositions.add(position);
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                }
            }
            for (row = endRow + 1; row < cacheEndRow; row++) {
                for (col = cacheStartCol; col < cacheEndCol; col++) {
                    position = new Position(row, col);
                    usePositions.add(position);
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                }
            }
            for (row = startRow; row < endRow; row++) {
                for (col = cacheStartCol; col < startCol; col++) {
                    position = new Position(row, col);
                    usePositions.add(position);
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                }
            }
            for (row = startRow; row < endRow; row++) {
                for (col = endRow + 1; col < cacheEndRow; col++) {
                    position = new Position(row, col);
                    usePositions.add(position);
                    this.handler.sendMessage(this.handler.obtainMessage(what, new MessageData(position, scale)));
                }
            }
            loadData.mCurrentCacheData.images.keySet().retainAll(usePositions);
        }
        if (needShowPositions.isEmpty()) {
            return drawDatas;
        }
        Collections.sort(cacheDatas, new NearComparator(scale));
        iterator = cacheDatas.iterator();
        while (iterator.hasNext()) {
            cacheData = (CacheData) iterator.next();
            int scaleKey = cacheData.scale;
            int size;
            int startRowKey;
            int endRowKey;
            int startColKey;
            int endColKey;
            Iterator<Entry<Position, Bitmap>> imageiterator;
            Position position2;
            Entry<Position, Bitmap> entry;
            Rect rect2;
            if (scaleKey / scale == 2) {
                int ds = scaleKey / scale;
                size = scale * this.BASE_BLOCKSIZE;
                startRowKey = cacheStartRow / 2;
                endRowKey = cacheEndRow / 2;
                startColKey = cacheStartCol / 2;
                endColKey = cacheEndCol / 2;
                imageiterator = cacheData.images.entrySet().iterator();
                while (imageiterator.hasNext()) {
                    position2 = (Position) ((Entry) imageiterator.next()).getKey();
                    if (startRowKey > position2.row || position2.row > endRowKey || startColKey > position2.col || position2.col > endColKey) {
                        imageiterator.remove();
                    }
                }
                for (Entry<Position, Bitmap> entry2 : cacheData.images.entrySet()) {
                    position2 = (Position) entry2.getKey();
                    int startPositionRow = position2.row * ds;
                    int endPositionRow = startPositionRow + ds;
                    int startPositionCol = position2.col * ds;
                    int endPositionCol = startPositionCol + ds;
                    bitmap = (Bitmap) entry2.getValue();
                    int iW = bitmap.getWidth();
                    int iH = bitmap.getHeight();
                    int blockImageSize = (int) Math.ceil((double) ((1.0f * ((float) this.BASE_BLOCKSIZE)) / ((float) ds)));
                    row = startPositionRow;
                    int i = 0;
                    while (row <= endPositionRow) {
                        int top = i * blockImageSize;
                        if (top >= iH) {
                            break;
                        }
                        col = startPositionCol;
                        int j = 0;
                        while (col <= endPositionCol) {
                            int left = j * blockImageSize;
                            if (left >= iW) {
                                break;
                            }
                            if (needShowPositions.remove(new Position(row, col))) {
                                int right = left + blockImageSize;
                                int bottom = top + blockImageSize;
                                if (right > iW) {
                                    right = iW;
                                }
                                if (bottom > iH) {
                                    bottom = iH;
                                }
                                rect2 = new Rect();
                                rect2.left = col * size;
                                rect2.top = row * size;
                                rect2.right = rect2.left + ((right - left) * scaleKey);
                                rect2.bottom = rect2.top + ((bottom - top) * scaleKey);
                                drawDatas.add(new DrawData(bitmap, new Rect(left, top, right, bottom), rect2));
                            }
                            col++;
                            j++;
                        }
                        row++;
                        i++;
                    }
                }
            } else if (scale / scaleKey == 2) {
                size = scaleKey * this.BASE_BLOCKSIZE;
                startRowKey = ((imageRect.top % size == 0 ? 0 : 1) + (imageRect.top / size)) - 1;
                endRowKey = (imageRect.bottom / size) + (imageRect.bottom % size == 0 ? 0 : 1);
                startColKey = ((imageRect.left % size == 0 ? 0 : 1) + (imageRect.left / size)) - 1;
                endColKey = (imageRect.right / size) + (imageRect.right % size == 0 ? 0 : 1);
                Position tempPosition = new Position();
                imageiterator = cacheData.images.entrySet().iterator();
                while (imageiterator.hasNext()) {
                    entry2 = (Entry) imageiterator.next();
                    position2 = (Position) entry2.getKey();
                    if (startRowKey > position2.row || position2.row > endRowKey || startColKey > position2.col || position2.col > endColKey) {
                        imageiterator.remove();
                    } else {
                        int i2;
                        bitmap = (Bitmap) entry2.getValue();
                        int i3 = (position2.row / 2) + (position2.row % 2 == 0 ? 0 : 1);
                        int i4 = position2.col / 2;
                        if (position2.col % 2 == 0) {
                            i2 = 0;
                        } else {
                            i2 = 1;
                        }
                        tempPosition.set(i3, i2 + i4);
                        if (needShowPositions.contains(tempPosition)) {
                            rect2 = new Rect();
                            rect2.left = position2.col * size;
                            rect2.top = position2.row * size;
                            rect2.right = rect2.left + (bitmap.getWidth() * scaleKey);
                            rect2.bottom = rect2.top + (bitmap.getHeight() * scaleKey);
                            drawDatas.add(new DrawData(bitmap, null, rect2));
                        }
                    }
                }
            } else {
                iterator.remove();
            }
        }
        return drawDatas;
    }

    public static int dip2px(Context context, float dipValue) {
        return (int) ((dipValue * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @TargetApi(19)
    public static int getBitmapSize(Bitmap bitmap) {
        if (VERSION.SDK_INT >= 19) {
            return bitmap.getAllocationByteCount();
        }
        if (VERSION.SDK_INT >= 12) {
            return bitmap.getByteCount();
        }
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public void destroy() {
        if (this.handlerThread != null) {
            this.handlerThread.quit();
            this.handlerThread = null;
            this.handler = null;
        }
        this.mainHandler.removeCallbacksAndMessages(null);
        release(this.mLoadData);
    }

    public int getWidth() {
        return this.mLoadData == null ? 0 : this.mLoadData.mImageWidth;
    }

    public int getHeight() {
        return this.mLoadData == null ? 0 : this.mLoadData.mImageHeight;
    }

    public void load(BitmapDecoderFactory factory) {
        if (this.handlerThread == null) {
            this.handlerThread = new HandlerThread("111");
            this.handlerThread.start();
            this.handler = new LoadHandler(this.handlerThread.getLooper());
        }
        LoadData loadData = this.mLoadData;
        if (!(loadData == null || loadData.mFactory == null)) {
            release(loadData);
        }
        this.mLoadData = new LoadData(factory);
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler.sendEmptyMessage(MESSAGE_LOAD);
        }
    }

    private void release(LoadData loadData) {
        if (loadData != null && loadData.mDecoder != null) {
            try {
                loadData.mDecoder.recycle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            loadData.mDecoder = null;
        }
    }
}
