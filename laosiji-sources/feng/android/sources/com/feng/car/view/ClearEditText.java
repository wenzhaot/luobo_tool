package com.feng.car.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class ClearEditText extends EditText implements TextWatcher {
    private Drawable mClearDrawable;
    private TextInputListener mTextInputListener;

    public interface TextInputListener {
        void onTextChanged(CharSequence charSequence, int i, int i2, int i3);
    }

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 16842862);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mClearDrawable = getCompoundDrawables()[2];
        if (this.mClearDrawable == null) {
            this.mClearDrawable = ContextCompat.getDrawable(context, 2130837777);
        }
        init();
    }

    private void init() {
        this.mClearDrawable.setBounds(0, 0, this.mClearDrawable.getIntrinsicWidth(), this.mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        addTextChangedListener(this);
    }

    public boolean onTouchEvent(MotionEvent event) {
        boolean touchable = true;
        if (event.getAction() == 1 && getCompoundDrawables()[2] != null) {
            if (event.getX() <= ((float) (getWidth() - getTotalPaddingRight())) || event.getX() >= ((float) (getWidth() - getPaddingRight()))) {
                touchable = false;
            }
            if (touchable) {
                setText("");
                event.setAction(3);
            }
        }
        return super.onTouchEvent(event);
    }

    protected void setClearIconVisible(boolean visible) {
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], visible ? this.mClearDrawable : null, getCompoundDrawables()[3]);
    }

    public void onTextChanged(CharSequence s, int start, int count, int after) {
        setClearIconVisible(s.length() > 0);
        if (this.mTextInputListener != null) {
            this.mTextInputListener.onTextChanged(s, start, count, after);
        }
    }

    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    public void afterTextChanged(Editable s) {
    }

    public void setTextInputListener(TextInputListener l) {
        this.mTextInputListener = l;
    }
}
