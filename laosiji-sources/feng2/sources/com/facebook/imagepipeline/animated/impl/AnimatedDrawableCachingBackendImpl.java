package com.facebook.imagepipeline.animated.impl;

import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.support.v4.util.SparseArrayCompat;
import bolts.Continuation;
import bolts.Task;
import com.facebook.common.executors.SerialExecutorService;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.logging.FLog;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.time.MonotonicClock;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableCachingBackend;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableFrameInfo.DisposalMethod;
import com.facebook.imagepipeline.animated.base.AnimatedDrawableOptions;
import com.facebook.imagepipeline.animated.base.DelegatingAnimatedDrawableBackend;
import com.facebook.imagepipeline.animated.impl.AnimatedImageCompositor.Callback;
import com.facebook.imagepipeline.animated.util.AnimatedDrawableUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.concurrent.GuardedBy;

public class AnimatedDrawableCachingBackendImpl extends DelegatingAnimatedDrawableBackend implements AnimatedDrawableCachingBackend {
    private static final int PREFETCH_FRAMES = 3;
    private static final Class<?> TAG = AnimatedDrawableCachingBackendImpl.class;
    private static final AtomicInteger sTotalBitmaps = new AtomicInteger();
    private final ActivityManager mActivityManager;
    private final AnimatedDrawableBackend mAnimatedDrawableBackend;
    private final AnimatedDrawableOptions mAnimatedDrawableOptions;
    private final AnimatedDrawableUtil mAnimatedDrawableUtil;
    private final AnimatedImageCompositor mAnimatedImageCompositor;
    private final double mApproxKiloBytesToHoldAllFrames;
    @GuardedBy("this")
    private final WhatToKeepCachedArray mBitmapsToKeepCached;
    @GuardedBy("this")
    private final SparseArrayCompat<CloseableReference<Bitmap>> mCachedBitmaps;
    @GuardedBy("ui-thread")
    private int mCurrentFrameIndex;
    @GuardedBy("this")
    private final SparseArrayCompat<Task<Object>> mDecodesInFlight;
    private final SerialExecutorService mExecutorService;
    @GuardedBy("this")
    private final List<Bitmap> mFreeBitmaps;
    private final double mMaximumKiloBytes;
    private final MonotonicClock mMonotonicClock;
    private final ResourceReleaser<Bitmap> mResourceReleaserForBitmaps;

    public AnimatedDrawableCachingBackendImpl(SerialExecutorService executorService, ActivityManager activityManager, AnimatedDrawableUtil animatedDrawableUtil, MonotonicClock monotonicClock, AnimatedDrawableBackend animatedDrawableBackend, AnimatedDrawableOptions options) {
        double d;
        super(animatedDrawableBackend);
        this.mExecutorService = executorService;
        this.mActivityManager = activityManager;
        this.mAnimatedDrawableUtil = animatedDrawableUtil;
        this.mMonotonicClock = monotonicClock;
        this.mAnimatedDrawableBackend = animatedDrawableBackend;
        this.mAnimatedDrawableOptions = options;
        if (options.maximumBytes >= 0) {
            d = (double) (options.maximumBytes / 1024);
        } else {
            d = (double) (getDefaultMaxBytes(activityManager) / 1024);
        }
        this.mMaximumKiloBytes = d;
        this.mAnimatedImageCompositor = new AnimatedImageCompositor(animatedDrawableBackend, new Callback() {
            public void onIntermediateResult(int frameNumber, Bitmap bitmap) {
                AnimatedDrawableCachingBackendImpl.this.maybeCacheBitmapDuringRender(frameNumber, bitmap);
            }

            public CloseableReference<Bitmap> getCachedBitmap(int frameNumber) {
                return AnimatedDrawableCachingBackendImpl.this.getCachedOrPredecodedFrame(frameNumber);
            }
        });
        this.mResourceReleaserForBitmaps = new ResourceReleaser<Bitmap>() {
            public void release(Bitmap value) {
                AnimatedDrawableCachingBackendImpl.this.releaseBitmapInternal(value);
            }
        };
        this.mFreeBitmaps = new ArrayList();
        this.mDecodesInFlight = new SparseArrayCompat(10);
        this.mCachedBitmaps = new SparseArrayCompat(10);
        this.mBitmapsToKeepCached = new WhatToKeepCachedArray(this.mAnimatedDrawableBackend.getFrameCount());
        this.mApproxKiloBytesToHoldAllFrames = (double) ((((this.mAnimatedDrawableBackend.getRenderedWidth() * this.mAnimatedDrawableBackend.getRenderedHeight()) / 1024) * this.mAnimatedDrawableBackend.getFrameCount()) * 4);
    }

    protected synchronized void finalize() throws Throwable {
        super.finalize();
        if (this.mCachedBitmaps.size() > 0) {
            FLog.d(TAG, "Finalizing with rendered bitmaps");
        }
        sTotalBitmaps.addAndGet(-this.mFreeBitmaps.size());
        this.mFreeBitmaps.clear();
    }

    private Bitmap createNewBitmap() {
        FLog.v(TAG, "Creating new bitmap");
        sTotalBitmaps.incrementAndGet();
        FLog.v(TAG, "Total bitmaps: %d", Integer.valueOf(sTotalBitmaps.get()));
        return Bitmap.createBitmap(this.mAnimatedDrawableBackend.getRenderedWidth(), this.mAnimatedDrawableBackend.getRenderedHeight(), Config.ARGB_8888);
    }

    public void renderFrame(int frameNumber, Canvas canvas) {
        throw new IllegalStateException();
    }

    public CloseableReference<Bitmap> getBitmapForFrame(int frameNumber) {
        this.mCurrentFrameIndex = frameNumber;
        CloseableReference<Bitmap> result = getBitmapForFrameInternal(frameNumber, false);
        schedulePrefetches();
        return result;
    }

    public CloseableReference<Bitmap> getPreviewBitmap() {
        return getAnimatedImageResult().getPreviewBitmap();
    }

    @VisibleForTesting
    CloseableReference<Bitmap> getBitmapForFrameBlocking(int frameNumber) {
        this.mCurrentFrameIndex = frameNumber;
        CloseableReference<Bitmap> result = getBitmapForFrameInternal(frameNumber, true);
        schedulePrefetches();
        return result;
    }

    public AnimatedDrawableCachingBackend forNewBounds(Rect bounds) {
        AnimatedDrawableBackend newBackend = this.mAnimatedDrawableBackend.forNewBounds(bounds);
        return newBackend == this.mAnimatedDrawableBackend ? this : new AnimatedDrawableCachingBackendImpl(this.mExecutorService, this.mActivityManager, this.mAnimatedDrawableUtil, this.mMonotonicClock, newBackend, this.mAnimatedDrawableOptions);
    }

    public synchronized void dropCaches() {
        this.mBitmapsToKeepCached.setAll(false);
        dropBitmapsThatShouldNotBeCached();
        for (Bitmap freeBitmap : this.mFreeBitmaps) {
            freeBitmap.recycle();
            sTotalBitmaps.decrementAndGet();
        }
        this.mFreeBitmaps.clear();
        this.mAnimatedDrawableBackend.dropCaches();
        FLog.v(TAG, "Total bitmaps: %d", Integer.valueOf(sTotalBitmaps.get()));
    }

    public int getMemoryUsage() {
        int bytes = 0;
        synchronized (this) {
            for (Bitmap bitmap : this.mFreeBitmaps) {
                bytes += this.mAnimatedDrawableUtil.getSizeOfBitmap(bitmap);
            }
            for (int i = 0; i < this.mCachedBitmaps.size(); i++) {
                bytes += this.mAnimatedDrawableUtil.getSizeOfBitmap((Bitmap) ((CloseableReference) this.mCachedBitmaps.valueAt(i)).get());
            }
        }
        return bytes + this.mAnimatedDrawableBackend.getMemoryUsage();
    }

    public void appendDebugOptionString(StringBuilder sb) {
        if (this.mAnimatedDrawableOptions.forceKeepAllFramesInMemory) {
            sb.append("Pinned To Memory");
        } else {
            if (this.mApproxKiloBytesToHoldAllFrames < this.mMaximumKiloBytes) {
                sb.append("within ");
            } else {
                sb.append("exceeds ");
            }
            this.mAnimatedDrawableUtil.appendMemoryString(sb, (int) this.mMaximumKiloBytes);
        }
        if (shouldKeepAllFramesInMemory() && this.mAnimatedDrawableOptions.allowPrefetching) {
            sb.append(" MT");
        }
    }

    /* JADX WARNING: Missing block: B:7:0x0016, code:
            r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Missing block: B:8:0x0022, code:
            if (r4 <= 10) goto L_?;
     */
    /* JADX WARNING: Missing block: B:9:0x0024, code:
            r1 = "";
     */
    /* JADX WARNING: Missing block: B:10:0x0027, code:
            if (null == null) goto L_0x003d;
     */
    /* JADX WARNING: Missing block: B:11:0x0029, code:
            r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Missing block: B:12:0x002c, code:
            com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", java.lang.Integer.valueOf(r14), java.lang.Long.valueOf(r4), r1);
     */
    /* JADX WARNING: Missing block: B:13:0x003d, code:
            if (null == null) goto L_0x0043;
     */
    /* JADX WARNING: Missing block: B:14:0x003f, code:
            r1 = "deferred";
     */
    /* JADX WARNING: Missing block: B:15:0x0043, code:
            r1 = "ok";
     */
    /* JADX WARNING: Missing block: B:18:0x0048, code:
            if (r15 == false) goto L_0x00c6;
     */
    /* JADX WARNING: Missing block: B:19:0x004a, code:
            r3 = true;
     */
    /* JADX WARNING: Missing block: B:21:?, code:
            r0 = obtainBitmapInternal();
     */
    /* JADX WARNING: Missing block: B:23:?, code:
            r13.mAnimatedImageCompositor.renderFrame(r14, (android.graphics.Bitmap) r0.get());
            maybeCacheRenderedBitmap(r14, r0);
            r8 = r0.clone();
     */
    /* JADX WARNING: Missing block: B:25:?, code:
            r0.close();
     */
    /* JADX WARNING: Missing block: B:26:0x0064, code:
            r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Missing block: B:27:0x0070, code:
            if (r4 <= 10) goto L_0x008a;
     */
    /* JADX WARNING: Missing block: B:28:0x0072, code:
            r1 = "";
     */
    /* JADX WARNING: Missing block: B:29:0x0075, code:
            if (1 == null) goto L_0x00b7;
     */
    /* JADX WARNING: Missing block: B:30:0x0077, code:
            r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Missing block: B:31:0x007a, code:
            com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", java.lang.Integer.valueOf(r14), java.lang.Long.valueOf(r4), r1);
     */
    /* JADX WARNING: Missing block: B:46:0x00b7, code:
            if (null == null) goto L_0x00bd;
     */
    /* JADX WARNING: Missing block: B:47:0x00b9, code:
            r1 = "deferred";
     */
    /* JADX WARNING: Missing block: B:48:0x00bd, code:
            r1 = "ok";
     */
    /* JADX WARNING: Missing block: B:53:0x00c6, code:
            r4 = r13.mMonotonicClock.now() - r6;
     */
    /* JADX WARNING: Missing block: B:54:0x00d4, code:
            if (r4 <= 10) goto L_?;
     */
    /* JADX WARNING: Missing block: B:55:0x00d6, code:
            r1 = "";
     */
    /* JADX WARNING: Missing block: B:56:0x00d9, code:
            if (null == null) goto L_0x00f0;
     */
    /* JADX WARNING: Missing block: B:57:0x00db, code:
            r1 = "renderedOnCallingThread";
     */
    /* JADX WARNING: Missing block: B:58:0x00de, code:
            com.facebook.common.logging.FLog.v(TAG, "obtainBitmap for frame %d took %d ms (%s)", java.lang.Integer.valueOf(r14), java.lang.Long.valueOf(r4), r1);
     */
    /* JADX WARNING: Missing block: B:59:0x00f0, code:
            if (true == false) goto L_0x00f6;
     */
    /* JADX WARNING: Missing block: B:60:0x00f2, code:
            r1 = "deferred";
     */
    /* JADX WARNING: Missing block: B:61:0x00f6, code:
            r1 = "ok";
     */
    /* JADX WARNING: Missing block: B:67:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:68:?, code:
            return r0;
     */
    /* JADX WARNING: Missing block: B:69:?, code:
            return r8;
     */
    /* JADX WARNING: Missing block: B:70:?, code:
            return null;
     */
    /* JADX WARNING: Missing block: B:71:?, code:
            return null;
     */
    private com.facebook.common.references.CloseableReference<android.graphics.Bitmap> getBitmapForFrameInternal(int r14, boolean r15) {
        /*
        r13 = this;
        r3 = 0;
        r2 = 0;
        r8 = r13.mMonotonicClock;
        r6 = r8.now();
        monitor-enter(r13);	 Catch:{ all -> 0x008f }
        r8 = r13.mBitmapsToKeepCached;	 Catch:{ all -> 0x008c }
        r9 = 1;
        r8.set(r14, r9);	 Catch:{ all -> 0x008c }
        r0 = r13.getCachedOrPredecodedFrame(r14);	 Catch:{ all -> 0x008c }
        if (r0 == 0) goto L_0x0047;
    L_0x0015:
        monitor-exit(r13);	 Catch:{ all -> 0x008c }
        r8 = r13.mMonotonicClock;
        r8 = r8.now();
        r4 = r8 - r6;
        r8 = 10;
        r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r8 <= 0) goto L_0x003c;
    L_0x0024:
        r1 = "";
        if (r3 == 0) goto L_0x003d;
    L_0x0029:
        r1 = "renderedOnCallingThread";
    L_0x002c:
        r8 = TAG;
        r9 = "obtainBitmap for frame %d took %d ms (%s)";
        r10 = java.lang.Integer.valueOf(r14);
        r11 = java.lang.Long.valueOf(r4);
        com.facebook.common.logging.FLog.v(r8, r9, r10, r11, r1);
    L_0x003c:
        return r0;
    L_0x003d:
        if (r2 == 0) goto L_0x0043;
    L_0x003f:
        r1 = "deferred";
        goto L_0x002c;
    L_0x0043:
        r1 = "ok";
        goto L_0x002c;
    L_0x0047:
        monitor-exit(r13);	 Catch:{ all -> 0x008c }
        if (r15 == 0) goto L_0x00c6;
    L_0x004a:
        r3 = 1;
        r0 = r13.obtainBitmapInternal();	 Catch:{ all -> 0x008f }
        r9 = r13.mAnimatedImageCompositor;	 Catch:{ all -> 0x00c1 }
        r8 = r0.get();	 Catch:{ all -> 0x00c1 }
        r8 = (android.graphics.Bitmap) r8;	 Catch:{ all -> 0x00c1 }
        r9.renderFrame(r14, r8);	 Catch:{ all -> 0x00c1 }
        r13.maybeCacheRenderedBitmap(r14, r0);	 Catch:{ all -> 0x00c1 }
        r8 = r0.clone();	 Catch:{ all -> 0x00c1 }
        r0.close();	 Catch:{ all -> 0x008f }
        r9 = r13.mMonotonicClock;
        r10 = r9.now();
        r4 = r10 - r6;
        r10 = 10;
        r9 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x008a;
    L_0x0072:
        r1 = "";
        if (r3 == 0) goto L_0x00b7;
    L_0x0077:
        r1 = "renderedOnCallingThread";
    L_0x007a:
        r9 = TAG;
        r10 = "obtainBitmap for frame %d took %d ms (%s)";
        r11 = java.lang.Integer.valueOf(r14);
        r12 = java.lang.Long.valueOf(r4);
        com.facebook.common.logging.FLog.v(r9, r10, r11, r12, r1);
    L_0x008a:
        r0 = r8;
        goto L_0x003c;
    L_0x008c:
        r8 = move-exception;
        monitor-exit(r13);	 Catch:{ all -> 0x008c }
        throw r8;	 Catch:{ all -> 0x008f }
    L_0x008f:
        r8 = move-exception;
        r9 = r13.mMonotonicClock;
        r10 = r9.now();
        r4 = r10 - r6;
        r10 = 10;
        r9 = (r4 > r10 ? 1 : (r4 == r10 ? 0 : -1));
        if (r9 <= 0) goto L_0x00b6;
    L_0x009e:
        r1 = "";
        if (r3 == 0) goto L_0x00fa;
    L_0x00a3:
        r1 = "renderedOnCallingThread";
    L_0x00a6:
        r9 = TAG;
        r10 = "obtainBitmap for frame %d took %d ms (%s)";
        r11 = java.lang.Integer.valueOf(r14);
        r12 = java.lang.Long.valueOf(r4);
        com.facebook.common.logging.FLog.v(r9, r10, r11, r12, r1);
    L_0x00b6:
        throw r8;
    L_0x00b7:
        if (r2 == 0) goto L_0x00bd;
    L_0x00b9:
        r1 = "deferred";
        goto L_0x007a;
    L_0x00bd:
        r1 = "ok";
        goto L_0x007a;
    L_0x00c1:
        r8 = move-exception;
        r0.close();	 Catch:{ all -> 0x008f }
        throw r8;	 Catch:{ all -> 0x008f }
    L_0x00c6:
        r2 = 1;
        r0 = 0;
        r8 = r13.mMonotonicClock;
        r8 = r8.now();
        r4 = r8 - r6;
        r8 = 10;
        r8 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r8 <= 0) goto L_0x003c;
    L_0x00d6:
        r1 = "";
        if (r3 == 0) goto L_0x00f0;
    L_0x00db:
        r1 = "renderedOnCallingThread";
    L_0x00de:
        r8 = TAG;
        r9 = "obtainBitmap for frame %d took %d ms (%s)";
        r10 = java.lang.Integer.valueOf(r14);
        r11 = java.lang.Long.valueOf(r4);
        com.facebook.common.logging.FLog.v(r8, r9, r10, r11, r1);
        goto L_0x003c;
    L_0x00f0:
        if (r2 == 0) goto L_0x00f6;
    L_0x00f2:
        r1 = "deferred";
        goto L_0x00de;
    L_0x00f6:
        r1 = "ok";
        goto L_0x00de;
    L_0x00fa:
        if (r2 == 0) goto L_0x0100;
    L_0x00fc:
        r1 = "deferred";
        goto L_0x00a6;
    L_0x0100:
        r1 = "ok";
        goto L_0x00a6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl.getBitmapForFrameInternal(int, boolean):com.facebook.common.references.CloseableReference<android.graphics.Bitmap>");
    }

    private void maybeCacheBitmapDuringRender(int frameNumber, Bitmap bitmap) {
        boolean cacheBitmap = false;
        synchronized (this) {
            if (this.mBitmapsToKeepCached.get(frameNumber)) {
                cacheBitmap = this.mCachedBitmaps.get(frameNumber) == null;
            }
        }
        if (cacheBitmap) {
            copyAndCacheBitmapDuringRendering(frameNumber, bitmap);
        }
    }

    private void copyAndCacheBitmapDuringRendering(int frameNumber, Bitmap sourceBitmap) {
        CloseableReference<Bitmap> destBitmapReference = obtainBitmapInternal();
        try {
            Canvas copyCanvas = new Canvas((Bitmap) destBitmapReference.get());
            copyCanvas.drawColor(0, Mode.SRC);
            copyCanvas.drawBitmap(sourceBitmap, 0.0f, 0.0f, null);
            maybeCacheRenderedBitmap(frameNumber, destBitmapReference);
        } finally {
            destBitmapReference.close();
        }
    }

    private CloseableReference<Bitmap> obtainBitmapInternal() {
        Bitmap bitmap;
        synchronized (this) {
            long nowNanos = System.nanoTime();
            long waitUntilNanos = nowNanos + TimeUnit.NANOSECONDS.convert(20, TimeUnit.MILLISECONDS);
            while (this.mFreeBitmaps.isEmpty() && nowNanos < waitUntilNanos) {
                try {
                    TimeUnit.NANOSECONDS.timedWait(this, waitUntilNanos - nowNanos);
                    nowNanos = System.nanoTime();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
            if (this.mFreeBitmaps.isEmpty()) {
                bitmap = createNewBitmap();
            } else {
                bitmap = (Bitmap) this.mFreeBitmaps.remove(this.mFreeBitmaps.size() - 1);
            }
        }
        return CloseableReference.of(bitmap, this.mResourceReleaserForBitmaps);
    }

    synchronized void releaseBitmapInternal(Bitmap bitmap) {
        this.mFreeBitmaps.add(bitmap);
    }

    private synchronized void schedulePrefetches() {
        int i = 1;
        synchronized (this) {
            boolean keepOnePreceding;
            int i2;
            int numToPrefetch;
            if (this.mAnimatedDrawableBackend.getFrameInfo(this.mCurrentFrameIndex).disposalMethod == DisposalMethod.DISPOSE_TO_PREVIOUS) {
                keepOnePreceding = true;
            } else {
                keepOnePreceding = false;
            }
            int i3 = this.mCurrentFrameIndex;
            if (keepOnePreceding) {
                i2 = 1;
            } else {
                i2 = 0;
            }
            int startFrame = Math.max(0, i3 - i2);
            if (this.mAnimatedDrawableOptions.allowPrefetching) {
                numToPrefetch = 3;
            } else {
                numToPrefetch = 0;
            }
            if (!keepOnePreceding) {
                i = 0;
            }
            numToPrefetch = Math.max(numToPrefetch, i);
            int endFrame = (startFrame + numToPrefetch) % this.mAnimatedDrawableBackend.getFrameCount();
            cancelFuturesOutsideOfRange(startFrame, endFrame);
            if (!shouldKeepAllFramesInMemory()) {
                this.mBitmapsToKeepCached.setAll(true);
                this.mBitmapsToKeepCached.removeOutsideRange(startFrame, endFrame);
                for (int frameNumber = startFrame; frameNumber >= 0; frameNumber--) {
                    if (this.mCachedBitmaps.get(frameNumber) != null) {
                        this.mBitmapsToKeepCached.set(frameNumber, true);
                        break;
                    }
                }
                dropBitmapsThatShouldNotBeCached();
            }
            if (this.mAnimatedDrawableOptions.allowPrefetching) {
                doPrefetch(startFrame, numToPrefetch);
            } else {
                cancelFuturesOutsideOfRange(this.mCurrentFrameIndex, this.mCurrentFrameIndex);
            }
        }
    }

    private static int getDefaultMaxBytes(ActivityManager activityManager) {
        if (activityManager.getMemoryClass() > 32) {
            return 5242880;
        }
        return 3145728;
    }

    private boolean shouldKeepAllFramesInMemory() {
        if (!this.mAnimatedDrawableOptions.forceKeepAllFramesInMemory && this.mApproxKiloBytesToHoldAllFrames >= this.mMaximumKiloBytes) {
            return false;
        }
        return true;
    }

    private synchronized void doPrefetch(int startFrame, int count) {
        for (int i = 0; i < count; i++) {
            final int frameNumber = (startFrame + i) % this.mAnimatedDrawableBackend.getFrameCount();
            Task<Object> future = (Task) this.mDecodesInFlight.get(frameNumber);
            if (!hasCachedOrPredecodedFrame(frameNumber) && future == null) {
                final Task<Object> newFuture = Task.call(new Callable<Object>() {
                    public Object call() {
                        AnimatedDrawableCachingBackendImpl.this.runPrefetch(frameNumber);
                        return null;
                    }
                }, this.mExecutorService);
                this.mDecodesInFlight.put(frameNumber, newFuture);
                newFuture.continueWith(new Continuation<Object, Object>() {
                    public Object then(Task<Object> task) throws Exception {
                        AnimatedDrawableCachingBackendImpl.this.onFutureFinished(newFuture, frameNumber);
                        return null;
                    }
                });
            }
        }
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl.runPrefetch(int):void, dom blocks: [B:15:0x001f, B:20:0x002a]
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:89)
        	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:63)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:58)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
        	at jadx.core.dex.visitors.DepthTraversal.lambda$visit$1(DepthTraversal.java:14)
        	at java.base/java.util.ArrayList.forEach(ArrayList.java:1378)
        	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
        	at jadx.core.ProcessClass.process(ProcessClass.java:32)
        	at jadx.core.ProcessClass.lambda$processDependencies$0(ProcessClass.java:51)
        	at java.base/java.lang.Iterable.forEach(Iterable.java:75)
        	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:51)
        	at jadx.core.ProcessClass.process(ProcessClass.java:37)
        	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:292)
        	at jadx.api.JavaClass.decompile(JavaClass.java:62)
        	at jadx.api.JadxDecompiler.lambda$appendSourcesSave$0(JadxDecompiler.java:200)
        */
    private void runPrefetch(int r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r2 = r5.mBitmapsToKeepCached;	 Catch:{ all -> 0x0013 }
        r2 = r2.get(r6);	 Catch:{ all -> 0x0013 }
        if (r2 != 0) goto L_0x000b;	 Catch:{ all -> 0x0013 }
    L_0x0009:
        monitor-exit(r5);	 Catch:{ all -> 0x0013 }
    L_0x000a:
        return;	 Catch:{ all -> 0x0013 }
    L_0x000b:
        r2 = r5.hasCachedOrPredecodedFrame(r6);	 Catch:{ all -> 0x0013 }
        if (r2 == 0) goto L_0x0016;	 Catch:{ all -> 0x0013 }
    L_0x0011:
        monitor-exit(r5);	 Catch:{ all -> 0x0013 }
        goto L_0x000a;	 Catch:{ all -> 0x0013 }
    L_0x0013:
        r2 = move-exception;	 Catch:{ all -> 0x0013 }
        monitor-exit(r5);	 Catch:{ all -> 0x0013 }
        throw r2;
    L_0x0016:
        monitor-exit(r5);	 Catch:{ all -> 0x0013 }
        r2 = r5.mAnimatedDrawableBackend;
        r1 = r2.getPreDecodedFrame(r6);
        if (r1 == 0) goto L_0x0026;
    L_0x001f:
        r5.maybeCacheRenderedBitmap(r6, r1);	 Catch:{ all -> 0x0048 }
    L_0x0022:
        com.facebook.common.references.CloseableReference.closeSafely(r1);
        goto L_0x000a;
    L_0x0026:
        r0 = r5.obtainBitmapInternal();	 Catch:{ all -> 0x0048 }
        r3 = r5.mAnimatedImageCompositor;	 Catch:{ all -> 0x004d }
        r2 = r0.get();	 Catch:{ all -> 0x004d }
        r2 = (android.graphics.Bitmap) r2;	 Catch:{ all -> 0x004d }
        r3.renderFrame(r6, r2);	 Catch:{ all -> 0x004d }
        r5.maybeCacheRenderedBitmap(r6, r0);	 Catch:{ all -> 0x004d }
        r2 = TAG;	 Catch:{ all -> 0x004d }
        r3 = "Prefetch rendered frame %d";	 Catch:{ all -> 0x004d }
        r4 = java.lang.Integer.valueOf(r6);	 Catch:{ all -> 0x004d }
        com.facebook.common.logging.FLog.v(r2, r3, r4);	 Catch:{ all -> 0x004d }
        r0.close();	 Catch:{ all -> 0x0048 }
        goto L_0x0022;
    L_0x0048:
        r2 = move-exception;
        com.facebook.common.references.CloseableReference.closeSafely(r1);
        throw r2;
    L_0x004d:
        r2 = move-exception;
        r0.close();	 Catch:{ all -> 0x0048 }
        throw r2;	 Catch:{ all -> 0x0048 }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.animated.impl.AnimatedDrawableCachingBackendImpl.runPrefetch(int):void");
    }

    private synchronized void onFutureFinished(Task<?> future, int frameNumber) {
        int index = this.mDecodesInFlight.indexOfKey(frameNumber);
        if (index >= 0 && ((Task) this.mDecodesInFlight.valueAt(index)) == future) {
            this.mDecodesInFlight.removeAt(index);
            if (future.getError() != null) {
                FLog.v(TAG, future.getError(), "Failed to render frame %d", Integer.valueOf(frameNumber));
            }
        }
    }

    private synchronized void cancelFuturesOutsideOfRange(int startFrame, int endFrame) {
        int index = 0;
        while (index < this.mDecodesInFlight.size()) {
            if (AnimatedDrawableUtil.isOutsideRange(startFrame, endFrame, this.mDecodesInFlight.keyAt(index))) {
                Task<?> future = (Task) this.mDecodesInFlight.valueAt(index);
                this.mDecodesInFlight.removeAt(index);
            } else {
                index++;
            }
        }
    }

    private synchronized void dropBitmapsThatShouldNotBeCached() {
        int index = 0;
        while (index < this.mCachedBitmaps.size()) {
            if (this.mBitmapsToKeepCached.get(this.mCachedBitmaps.keyAt(index))) {
                index++;
            } else {
                CloseableReference<Bitmap> bitmapReference = (CloseableReference) this.mCachedBitmaps.valueAt(index);
                this.mCachedBitmaps.removeAt(index);
                bitmapReference.close();
            }
        }
    }

    private synchronized void maybeCacheRenderedBitmap(int frameNumber, CloseableReference<Bitmap> bitmapReference) {
        if (this.mBitmapsToKeepCached.get(frameNumber)) {
            int existingIndex = this.mCachedBitmaps.indexOfKey(frameNumber);
            if (existingIndex >= 0) {
                ((CloseableReference) this.mCachedBitmaps.valueAt(existingIndex)).close();
                this.mCachedBitmaps.removeAt(existingIndex);
            }
            this.mCachedBitmaps.put(frameNumber, bitmapReference.clone());
        }
    }

    private synchronized CloseableReference<Bitmap> getCachedOrPredecodedFrame(int frameNumber) {
        CloseableReference<Bitmap> ret;
        ret = CloseableReference.cloneOrNull((CloseableReference) this.mCachedBitmaps.get(frameNumber));
        if (ret == null) {
            ret = this.mAnimatedDrawableBackend.getPreDecodedFrame(frameNumber);
        }
        return ret;
    }

    private synchronized boolean hasCachedOrPredecodedFrame(int frameNumber) {
        boolean z;
        z = this.mCachedBitmaps.get(frameNumber) != null || this.mAnimatedDrawableBackend.hasPreDecodedFrame(frameNumber);
        return z;
    }

    @VisibleForTesting
    synchronized Map<Integer, Task<?>> getDecodesInFlight() {
        Map<Integer, Task<?>> map;
        map = new HashMap();
        for (int i = 0; i < this.mDecodesInFlight.size(); i++) {
            map.put(Integer.valueOf(this.mDecodesInFlight.keyAt(i)), this.mDecodesInFlight.valueAt(i));
        }
        return map;
    }

    @VisibleForTesting
    synchronized Set<Integer> getFramesCached() {
        Set<Integer> set;
        set = new HashSet();
        for (int i = 0; i < this.mCachedBitmaps.size(); i++) {
            set.add(Integer.valueOf(this.mCachedBitmaps.keyAt(i)));
        }
        return set;
    }
}
