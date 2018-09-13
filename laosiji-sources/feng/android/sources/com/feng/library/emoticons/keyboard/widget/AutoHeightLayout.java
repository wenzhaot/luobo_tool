package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.feng.library.emoticons.keyboard.widget.SoftKeyboardSizeWatchLayout.OnResizeListener;

public abstract class AutoHeightLayout extends SoftKeyboardSizeWatchLayout implements OnResizeListener {
    private static final int ID_CHILD = R.id.id_autolayout;
    protected Context mContext;
    protected int mMaxParentHeight;
    protected int mSoftKeyboardHeight = EmoticonsKeyboardUtils.getDefKeyboardHeight(this.mContext);
    private OnMaxParentHeightChangeListener maxParentHeightChangeListener;

    public interface OnMaxParentHeightChangeListener {
        void onMaxParentHeightChange(int i);
    }

    public abstract void onSoftKeyboardHeightChanged(int i);

    public AutoHeightLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        addOnResizeListener(this);
    }

    public void addView(View child, int index, LayoutParams params) {
        int childSum = getChildCount();
        if (childSum > 1) {
            throw new IllegalStateException("can host only one direct child");
        }
        super.addView(child, index, params);
        RelativeLayout.LayoutParams paramsChild;
        if (childSum == 0) {
            if (child.getId() < 0) {
                child.setId(ID_CHILD);
            }
            paramsChild = (RelativeLayout.LayoutParams) child.getLayoutParams();
            paramsChild.addRule(12);
            child.setLayoutParams(paramsChild);
        } else if (childSum == 1) {
            paramsChild = (RelativeLayout.LayoutParams) child.getLayoutParams();
            paramsChild.addRule(2, ID_CHILD);
            child.setLayoutParams(paramsChild);
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        onSoftKeyboardHeightChanged(this.mSoftKeyboardHeight);
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (this.mMaxParentHeight == 0) {
            this.mMaxParentHeight = h;
        }
    }

    public void updateMaxParentHeight(int maxParentHeight) {
        this.mMaxParentHeight = maxParentHeight;
        if (this.maxParentHeightChangeListener != null) {
            this.maxParentHeightChangeListener.onMaxParentHeightChange(maxParentHeight);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (this.mMaxParentHeight != 0) {
            super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(this.mMaxParentHeight, MeasureSpec.getMode(heightMeasureSpec)));
            return;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void OnSoftPop(int height) {
        if (this.mSoftKeyboardHeight != height) {
            this.mSoftKeyboardHeight = height;
            EmoticonsKeyboardUtils.setDefKeyboardHeight(this.mContext, this.mSoftKeyboardHeight);
            onSoftKeyboardHeightChanged(this.mSoftKeyboardHeight);
        }
    }

    public void OnSoftClose() {
    }

    public void setOnMaxParentHeightChangeListener(OnMaxParentHeightChangeListener listener) {
        this.maxParentHeightChangeListener = listener;
    }
}
