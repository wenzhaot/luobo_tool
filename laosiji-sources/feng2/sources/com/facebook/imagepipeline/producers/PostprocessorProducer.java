package com.facebook.imagepipeline.producers;

import com.facebook.common.internal.ImmutableMap;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.VisibleForTesting;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.CloseableStaticBitmap;
import com.facebook.imagepipeline.request.Postprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessor;
import com.facebook.imagepipeline.request.RepeatedPostprocessorRunner;
import java.util.Map;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;

public class PostprocessorProducer implements Producer<CloseableReference<CloseableImage>> {
    @VisibleForTesting
    static final String NAME = "PostprocessorProducer";
    @VisibleForTesting
    static final String POSTPROCESSOR = "Postprocessor";
    private final PlatformBitmapFactory mBitmapFactory;
    private final Executor mExecutor;
    private final Producer<CloseableReference<CloseableImage>> mInputProducer;

    private class PostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsDirty = false;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsLast = false;
        @GuardedBy("PostprocessorConsumer.this")
        private boolean mIsPostProcessingRunning = false;
        private final ProducerListener mListener;
        private final Postprocessor mPostprocessor;
        private final String mRequestId;
        @GuardedBy("PostprocessorConsumer.this")
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef = null;

        public PostprocessorConsumer(Consumer<CloseableReference<CloseableImage>> consumer, ProducerListener listener, String requestId, Postprocessor postprocessor, ProducerContext producerContext) {
            super(consumer);
            this.mListener = listener;
            this.mRequestId = requestId;
            this.mPostprocessor = postprocessor;
            producerContext.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    PostprocessorConsumer.this.maybeNotifyOnCancellation();
                }
            });
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (CloseableReference.isValid(newResult)) {
                updateSourceImageRef(newResult, isLast);
            } else if (isLast) {
                maybeNotifyOnNewResult(null, true);
            }
        }

        protected void onFailureImpl(Throwable t) {
            maybeNotifyOnFailure(t);
        }

        protected void onCancellationImpl() {
            maybeNotifyOnCancellation();
        }

        /* JADX WARNING: Missing block: B:7:0x0019, code:
            com.facebook.common.references.CloseableReference.closeSafely(r0);
     */
        /* JADX WARNING: Missing block: B:8:0x001c, code:
            if (r1 == false) goto L_?;
     */
        /* JADX WARNING: Missing block: B:9:0x001e, code:
            submitPostprocessing();
     */
        /* JADX WARNING: Missing block: B:18:?, code:
            return;
     */
        /* JADX WARNING: Missing block: B:19:?, code:
            return;
     */
        private void updateSourceImageRef(@javax.annotation.Nullable com.facebook.common.references.CloseableReference<com.facebook.imagepipeline.image.CloseableImage> r4, boolean r5) {
            /*
            r3 = this;
            monitor-enter(r3);
            r2 = r3.mIsClosed;	 Catch:{ all -> 0x0022 }
            if (r2 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r3);	 Catch:{ all -> 0x0022 }
        L_0x0006:
            return;
        L_0x0007:
            r0 = r3.mSourceImageRef;	 Catch:{ all -> 0x0022 }
            r2 = com.facebook.common.references.CloseableReference.cloneOrNull(r4);	 Catch:{ all -> 0x0022 }
            r3.mSourceImageRef = r2;	 Catch:{ all -> 0x0022 }
            r3.mIsLast = r5;	 Catch:{ all -> 0x0022 }
            r2 = 1;
            r3.mIsDirty = r2;	 Catch:{ all -> 0x0022 }
            r1 = r3.setRunningIfDirtyAndNotRunning();	 Catch:{ all -> 0x0022 }
            monitor-exit(r3);	 Catch:{ all -> 0x0022 }
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            if (r1 == 0) goto L_0x0006;
        L_0x001e:
            r3.submitPostprocessing();
            goto L_0x0006;
        L_0x0022:
            r2 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x0022 }
            throw r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.PostprocessorConsumer.updateSourceImageRef(com.facebook.common.references.CloseableReference, boolean):void");
        }

        private void submitPostprocessing() {
            PostprocessorProducer.this.mExecutor.execute(new Runnable() {
                public void run() {
                    CloseableReference closeableImageRef;
                    boolean isLast;
                    synchronized (PostprocessorConsumer.this) {
                        closeableImageRef = PostprocessorConsumer.this.mSourceImageRef;
                        isLast = PostprocessorConsumer.this.mIsLast;
                        PostprocessorConsumer.this.mSourceImageRef = null;
                        PostprocessorConsumer.this.mIsDirty = false;
                    }
                    if (CloseableReference.isValid(closeableImageRef)) {
                        try {
                            PostprocessorConsumer.this.doPostprocessing(closeableImageRef, isLast);
                        } finally {
                            CloseableReference.closeSafely(closeableImageRef);
                        }
                    }
                    PostprocessorConsumer.this.clearRunningAndStartIfDirty();
                }
            });
        }

        private void clearRunningAndStartIfDirty() {
            boolean shouldExecuteAgain;
            synchronized (this) {
                this.mIsPostProcessingRunning = false;
                shouldExecuteAgain = setRunningIfDirtyAndNotRunning();
            }
            if (shouldExecuteAgain) {
                submitPostprocessing();
            }
        }

        private synchronized boolean setRunningIfDirtyAndNotRunning() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed || !this.mIsDirty || this.mIsPostProcessingRunning || !CloseableReference.isValid(this.mSourceImageRef)) {
                    z = false;
                } else {
                    this.mIsPostProcessingRunning = true;
                }
            }
            return z;
        }

        private void doPostprocessing(CloseableReference<CloseableImage> sourceImageRef, boolean isLast) {
            Preconditions.checkArgument(CloseableReference.isValid(sourceImageRef));
            if (shouldPostprocess((CloseableImage) sourceImageRef.get())) {
                this.mListener.onProducerStart(this.mRequestId, PostprocessorProducer.NAME);
                CloseableReference destImageRef = null;
                try {
                    destImageRef = postprocessInternal((CloseableImage) sourceImageRef.get());
                    this.mListener.onProducerFinishWithSuccess(this.mRequestId, PostprocessorProducer.NAME, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                    maybeNotifyOnNewResult(destImageRef, isLast);
                } catch (Exception e) {
                    this.mListener.onProducerFinishWithFailure(this.mRequestId, PostprocessorProducer.NAME, e, getExtraMap(this.mListener, this.mRequestId, this.mPostprocessor));
                    maybeNotifyOnFailure(e);
                } finally {
                    CloseableReference.closeSafely(destImageRef);
                }
            } else {
                maybeNotifyOnNewResult(sourceImageRef, isLast);
            }
        }

        private Map<String, String> getExtraMap(ProducerListener listener, String requestId, Postprocessor postprocessor) {
            if (listener.requiresExtraMap(requestId)) {
                return ImmutableMap.of(PostprocessorProducer.POSTPROCESSOR, postprocessor.getName());
            }
            return null;
        }

        private boolean shouldPostprocess(CloseableImage sourceImage) {
            return sourceImage instanceof CloseableStaticBitmap;
        }

        private CloseableReference<CloseableImage> postprocessInternal(CloseableImage sourceImage) {
            CloseableStaticBitmap staticBitmap = (CloseableStaticBitmap) sourceImage;
            CloseableReference bitmapRef = this.mPostprocessor.process(staticBitmap.getUnderlyingBitmap(), PostprocessorProducer.this.mBitmapFactory);
            try {
                CloseableReference<CloseableImage> of = CloseableReference.of(new CloseableStaticBitmap(bitmapRef, sourceImage.getQualityInfo(), staticBitmap.getRotationAngle()));
                return of;
            } finally {
                CloseableReference.closeSafely(bitmapRef);
            }
        }

        private void maybeNotifyOnNewResult(CloseableReference<CloseableImage> newRef, boolean isLast) {
            if ((!isLast && !isClosed()) || (isLast && close())) {
                getConsumer().onNewResult(newRef, isLast);
            }
        }

        private void maybeNotifyOnFailure(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        private void maybeNotifyOnCancellation() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        private synchronized boolean isClosed() {
            return this.mIsClosed;
        }

        private boolean close() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed) {
                    z = false;
                } else {
                    CloseableReference oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = null;
                    this.mIsClosed = true;
                    CloseableReference.closeSafely(oldSourceImageRef);
                }
            }
            return z;
        }
    }

    class RepeatedPostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> implements RepeatedPostprocessorRunner {
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        private boolean mIsClosed;
        @GuardedBy("RepeatedPostprocessorConsumer.this")
        @Nullable
        private CloseableReference<CloseableImage> mSourceImageRef;

        private RepeatedPostprocessorConsumer(PostprocessorConsumer postprocessorConsumer, RepeatedPostprocessor repeatedPostprocessor, ProducerContext context) {
            super(postprocessorConsumer);
            this.mIsClosed = false;
            this.mSourceImageRef = null;
            repeatedPostprocessor.setCallback(this);
            context.addCallbacks(new BaseProducerContextCallbacks(PostprocessorProducer.this) {
                public void onCancellationRequested() {
                    if (RepeatedPostprocessorConsumer.this.close()) {
                        RepeatedPostprocessorConsumer.this.getConsumer().onCancellation();
                    }
                }
            });
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (isLast) {
                setSourceImageRef(newResult);
                updateInternal();
            }
        }

        protected void onFailureImpl(Throwable throwable) {
            if (close()) {
                getConsumer().onFailure(throwable);
            }
        }

        protected void onCancellationImpl() {
            if (close()) {
                getConsumer().onCancellation();
            }
        }

        public synchronized void update() {
            updateInternal();
        }

        /* JADX WARNING: Missing block: B:8:?, code:
            getConsumer().onNewResult(r0, false);
     */
        /* JADX WARNING: Missing block: B:14:0x001e, code:
            com.facebook.common.references.CloseableReference.closeSafely(r0);
     */
        private void updateInternal() {
            /*
            r3 = this;
            monitor-enter(r3);
            r1 = r3.mIsClosed;	 Catch:{ all -> 0x001a }
            if (r1 == 0) goto L_0x0007;
        L_0x0005:
            monitor-exit(r3);	 Catch:{ all -> 0x001a }
        L_0x0006:
            return;
        L_0x0007:
            r1 = r3.mSourceImageRef;	 Catch:{ all -> 0x001a }
            r0 = com.facebook.common.references.CloseableReference.cloneOrNull(r1);	 Catch:{ all -> 0x001a }
            monitor-exit(r3);	 Catch:{ all -> 0x001a }
            r1 = r3.getConsumer();	 Catch:{ all -> 0x001d }
            r2 = 0;
            r1.onNewResult(r0, r2);	 Catch:{ all -> 0x001d }
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            goto L_0x0006;
        L_0x001a:
            r1 = move-exception;
            monitor-exit(r3);	 Catch:{ all -> 0x001a }
            throw r1;
        L_0x001d:
            r1 = move-exception;
            com.facebook.common.references.CloseableReference.closeSafely(r0);
            throw r1;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.PostprocessorProducer.RepeatedPostprocessorConsumer.updateInternal():void");
        }

        private void setSourceImageRef(CloseableReference<CloseableImage> sourceImageRef) {
            synchronized (this) {
                if (this.mIsClosed) {
                    return;
                }
                CloseableReference oldSourceImageRef = this.mSourceImageRef;
                this.mSourceImageRef = CloseableReference.cloneOrNull((CloseableReference) sourceImageRef);
                CloseableReference.closeSafely(oldSourceImageRef);
            }
        }

        private boolean close() {
            boolean z = true;
            synchronized (this) {
                if (this.mIsClosed) {
                    z = false;
                } else {
                    CloseableReference oldSourceImageRef = this.mSourceImageRef;
                    this.mSourceImageRef = null;
                    this.mIsClosed = true;
                    CloseableReference.closeSafely(oldSourceImageRef);
                }
            }
            return z;
        }
    }

    class SingleUsePostprocessorConsumer extends DelegatingConsumer<CloseableReference<CloseableImage>, CloseableReference<CloseableImage>> {
        private SingleUsePostprocessorConsumer(PostprocessorConsumer postprocessorConsumer) {
            super(postprocessorConsumer);
        }

        protected void onNewResultImpl(CloseableReference<CloseableImage> newResult, boolean isLast) {
            if (isLast) {
                getConsumer().onNewResult(newResult, isLast);
            }
        }
    }

    public PostprocessorProducer(Producer<CloseableReference<CloseableImage>> inputProducer, PlatformBitmapFactory platformBitmapFactory, Executor executor) {
        this.mInputProducer = (Producer) Preconditions.checkNotNull(inputProducer);
        this.mBitmapFactory = platformBitmapFactory;
        this.mExecutor = (Executor) Preconditions.checkNotNull(executor);
    }

    public void produceResults(Consumer<CloseableReference<CloseableImage>> consumer, ProducerContext context) {
        Consumer<CloseableReference<CloseableImage>> postprocessorConsumer;
        ProducerListener listener = context.getListener();
        Postprocessor postprocessor = context.getImageRequest().getPostprocessor();
        PostprocessorConsumer basePostprocessorConsumer = new PostprocessorConsumer(consumer, listener, context.getId(), postprocessor, context);
        if (postprocessor instanceof RepeatedPostprocessor) {
            postprocessorConsumer = new RepeatedPostprocessorConsumer(basePostprocessorConsumer, (RepeatedPostprocessor) postprocessor, context);
        } else {
            postprocessorConsumer = new SingleUsePostprocessorConsumer(basePostprocessorConsumer);
        }
        this.mInputProducer.produceResults(postprocessorConsumer, context);
    }
}
