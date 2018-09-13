package com.facebook.drawee.drawable;

import android.graphics.Matrix;
import android.graphics.Rect;
import javax.annotation.Nullable;

public class ScalingUtils {

    public interface ScaleType {
        public static final ScaleType CENTER = ScaleTypeCenter.INSTANCE;
        public static final ScaleType CENTER_CROP = ScaleTypeCenterCrop.INSTANCE;
        public static final ScaleType CENTER_INSIDE = ScaleTypeCenterInside.INSTANCE;
        public static final ScaleType FIT_CENTER = ScaleTypeFitCenter.INSTANCE;
        public static final ScaleType FIT_END = ScaleTypeFitEnd.INSTANCE;
        public static final ScaleType FIT_START = ScaleTypeFitStart.INSTANCE;
        public static final ScaleType FIT_XY = ScaleTypeFitXY.INSTANCE;
        public static final ScaleType FOCUS_CROP = ScaleTypeFocusCrop.INSTANCE;

        Matrix getTransform(Matrix matrix, Rect rect, int i, int i2, float f, float f2);
    }

    public static abstract class AbstractScaleType implements ScaleType {
        public abstract void getTransformImpl(Matrix matrix, Rect rect, int i, int i2, float f, float f2, float f3, float f4);

        public Matrix getTransform(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY) {
            getTransformImpl(outTransform, parentRect, childWidth, childHeight, focusX, focusY, ((float) parentRect.width()) / ((float) childWidth), ((float) parentRect.height()) / ((float) childHeight));
            return outTransform;
        }
    }

    public interface StatefulScaleType {
        Object getState();
    }

    public static class InterpolatingScaleType implements ScaleType, StatefulScaleType {
        @Nullable
        private final Rect mBoundsFrom;
        @Nullable
        private final Rect mBoundsTo;
        private float mInterpolatingValue;
        private final float[] mMatrixValuesFrom;
        private final float[] mMatrixValuesInterpolated;
        private final float[] mMatrixValuesTo;
        private final ScaleType mScaleTypeFrom;
        private final ScaleType mScaleTypeTo;

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo, @Nullable Rect boundsFrom, @Nullable Rect boundsTo) {
            this.mMatrixValuesFrom = new float[9];
            this.mMatrixValuesTo = new float[9];
            this.mMatrixValuesInterpolated = new float[9];
            this.mScaleTypeFrom = scaleTypeFrom;
            this.mScaleTypeTo = scaleTypeTo;
            this.mBoundsFrom = boundsFrom;
            this.mBoundsTo = boundsTo;
        }

        public InterpolatingScaleType(ScaleType scaleTypeFrom, ScaleType scaleTypeTo) {
            this(scaleTypeFrom, scaleTypeTo, null, null);
        }

        public ScaleType getScaleTypeFrom() {
            return this.mScaleTypeFrom;
        }

        public ScaleType getScaleTypeTo() {
            return this.mScaleTypeTo;
        }

        @Nullable
        public Rect getBoundsFrom() {
            return this.mBoundsFrom;
        }

        @Nullable
        public Rect getBoundsTo() {
            return this.mBoundsTo;
        }

        public void setValue(float value) {
            this.mInterpolatingValue = value;
        }

        public float getValue() {
            return this.mInterpolatingValue;
        }

        public Object getState() {
            return Float.valueOf(this.mInterpolatingValue);
        }

        public Matrix getTransform(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY) {
            Rect boundsFrom;
            Rect boundsTo;
            if (this.mBoundsFrom != null) {
                boundsFrom = this.mBoundsFrom;
            } else {
                boundsFrom = parentBounds;
            }
            if (this.mBoundsTo != null) {
                boundsTo = this.mBoundsTo;
            } else {
                boundsTo = parentBounds;
            }
            this.mScaleTypeFrom.getTransform(transform, boundsFrom, childWidth, childHeight, focusX, focusY);
            transform.getValues(this.mMatrixValuesFrom);
            this.mScaleTypeTo.getTransform(transform, boundsTo, childWidth, childHeight, focusX, focusY);
            transform.getValues(this.mMatrixValuesTo);
            for (int i = 0; i < 9; i++) {
                this.mMatrixValuesInterpolated[i] = (this.mMatrixValuesFrom[i] * (1.0f - this.mInterpolatingValue)) + (this.mMatrixValuesTo[i] * this.mInterpolatingValue);
            }
            transform.setValues(this.mMatrixValuesInterpolated);
            return transform;
        }
    }

    private static class ScaleTypeCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenter();

        private ScaleTypeCenter() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            outTransform.setTranslate((float) ((int) ((((float) parentRect.left) + (((float) (parentRect.width() - childWidth)) * 0.5f)) + 0.5f)), (float) ((int) ((((float) parentRect.top) + (((float) (parentRect.height() - childHeight)) * 0.5f)) + 0.5f)));
        }
    }

    private static class ScaleTypeCenterCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterCrop();

        private ScaleTypeCenterCrop() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale;
            float dx;
            float dy;
            if (scaleY > scaleX) {
                scale = scaleY;
                dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
                dy = (float) parentRect.top;
            } else {
                scale = scaleX;
                dx = (float) parentRect.left;
                dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
        }
    }

    private static class ScaleTypeCenterInside extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeCenterInside();

        private ScaleTypeCenterInside() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(Math.min(scaleX, scaleY), 1.0f);
            float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
            float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
        }
    }

    private static class ScaleTypeFitCenter extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitCenter();

        private ScaleTypeFitCenter() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = ((float) parentRect.left) + ((((float) parentRect.width()) - (((float) childWidth) * scale)) * 0.5f);
            float dy = ((float) parentRect.top) + ((((float) parentRect.height()) - (((float) childHeight) * scale)) * 0.5f);
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
        }
    }

    private static class ScaleTypeFitEnd extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitEnd();

        private ScaleTypeFitEnd() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = ((float) parentRect.left) + (((float) parentRect.width()) - (((float) childWidth) * scale));
            float dy = ((float) parentRect.top) + (((float) parentRect.height()) - (((float) childHeight) * scale));
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (dy + 0.5f)));
        }
    }

    private static class ScaleTypeFitStart extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitStart();

        private ScaleTypeFitStart() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale = Math.min(scaleX, scaleY);
            float dx = (float) parentRect.left;
            float dy = (float) parentRect.top;
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }
    }

    private static class ScaleTypeFitXY extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFitXY();

        private ScaleTypeFitXY() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float dx = (float) parentRect.left;
            float dy = (float) parentRect.top;
            outTransform.setScale(scaleX, scaleY);
            outTransform.postTranslate((float) ((int) (dx + 0.5f)), (float) ((int) (0.5f + dy)));
        }
    }

    private static class ScaleTypeFocusCrop extends AbstractScaleType {
        public static final ScaleType INSTANCE = new ScaleTypeFocusCrop();

        private ScaleTypeFocusCrop() {
        }

        public void getTransformImpl(Matrix outTransform, Rect parentRect, int childWidth, int childHeight, float focusX, float focusY, float scaleX, float scaleY) {
            float scale;
            float dx;
            float dy;
            if (scaleY > scaleX) {
                scale = scaleY;
                dx = ((float) parentRect.left) + Math.max(Math.min((((float) parentRect.width()) * 0.5f) - ((((float) childWidth) * scale) * focusX), 0.0f), ((float) parentRect.width()) - (((float) childWidth) * scale));
                dy = (float) parentRect.top;
            } else {
                scale = scaleX;
                dx = (float) parentRect.left;
                dy = ((float) parentRect.top) + Math.max(Math.min((((float) parentRect.height()) * 0.5f) - ((((float) childHeight) * scale) * focusY), 0.0f), ((float) parentRect.height()) - (((float) childHeight) * scale));
            }
            outTransform.setScale(scale, scale);
            outTransform.postTranslate((float) ((int) (0.5f + dx)), (float) ((int) (0.5f + dy)));
        }
    }

    @Deprecated
    public static Matrix getTransform(Matrix transform, Rect parentBounds, int childWidth, int childHeight, float focusX, float focusY, ScaleType scaleType) {
        return scaleType.getTransform(transform, parentBounds, childWidth, childHeight, focusX, focusY);
    }
}
