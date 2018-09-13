package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView.BufferType;
import com.feng.library.emoticons.keyboard.interfaces.EmoticonFilter;
import java.util.ArrayList;
import java.util.List;

public class EmoticonsEditText extends EditText {
    private List<EmoticonFilter> mFilterList;
    OnBackKeyClickListener onBackKeyClickListener;
    OnSizeChangedListener onSizeChangedListener;

    public interface OnBackKeyClickListener {
        void onBackKeyClick();
    }

    public interface OnSizeChangedListener {
        void onSizeChanged(int i, int i2, int i3, int i4);
    }

    public EmoticonsEditText(Context context) {
        this(context, null);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmoticonsEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (oldh > 0 && this.onSizeChangedListener != null) {
            this.onSizeChangedListener.onSizeChanged(w, h, oldw, oldh);
        }
    }

    public void setGravity(int gravity) {
        try {
            super.setGravity(gravity);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(getText().toString());
            super.setGravity(gravity);
        }
    }

    public void setText(CharSequence text, BufferType type) {
        try {
            super.setText(text, type);
        } catch (ArrayIndexOutOfBoundsException e) {
            setText(text.toString());
        }
    }

    protected final void onTextChanged(CharSequence arg0, int start, int lengthBefore, int after) {
        super.onTextChanged(arg0, start, lengthBefore, after);
        if (this.mFilterList != null) {
            for (EmoticonFilter emoticonFilter : this.mFilterList) {
                emoticonFilter.filter(this, arg0, start, lengthBefore, after);
            }
        }
    }

    public void addEmoticonFilter(EmoticonFilter emoticonFilter) {
        if (this.mFilterList == null) {
            this.mFilterList = new ArrayList();
        }
        this.mFilterList.add(emoticonFilter);
    }

    public void removedEmoticonFilter(EmoticonFilter emoticonFilter) {
        if (this.mFilterList != null && this.mFilterList.contains(emoticonFilter)) {
            this.mFilterList.remove(emoticonFilter);
        }
    }

    public boolean dispatchKeyEventPreIme(KeyEvent event) {
        if (this.onBackKeyClickListener != null) {
            this.onBackKeyClickListener.onBackKeyClick();
        }
        return super.dispatchKeyEventPreIme(event);
    }

    public void setOnBackKeyClickListener(OnBackKeyClickListener i) {
        this.onBackKeyClickListener = i;
    }

    public void setOnSizeChangedListener(OnSizeChangedListener i) {
        this.onSizeChangedListener = i;
    }
}
