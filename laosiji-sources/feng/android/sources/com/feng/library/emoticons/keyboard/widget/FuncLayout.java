package com.feng.library.emoticons.keyboard.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import java.util.ArrayList;
import java.util.List;

public class FuncLayout extends LinearLayout {
    public final int DEF_KEY = Integer.MIN_VALUE;
    private int mCurrentFuncKey = Integer.MIN_VALUE;
    private final SparseArray<View> mFuncViewArrayMap = new SparseArray();
    protected int mHeight = 0;
    private List<OnFuncKeyBoardListener> mListenerList;
    private OnFuncChangeListener onFuncChangeListener;

    public interface OnFuncChangeListener {
        void onFuncChange(int i);
    }

    public FuncLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(1);
    }

    public void addFuncView(int key, View view) {
        if (this.mFuncViewArrayMap.get(key) == null) {
            this.mFuncViewArrayMap.put(key, view);
            addView(view, new LayoutParams(-1, -1));
            view.setVisibility(8);
        }
    }

    public void hideAllFuncView() {
        for (int i = 0; i < this.mFuncViewArrayMap.size(); i++) {
            ((View) this.mFuncViewArrayMap.get(this.mFuncViewArrayMap.keyAt(i))).setVisibility(8);
        }
        this.mCurrentFuncKey = Integer.MIN_VALUE;
        setVisibility(false);
    }

    public void toggleFuncView(int key, boolean isSoftKeyboardPop, EditText editText) {
        if (getCurrentFuncKey() != key) {
            if (isSoftKeyboardPop) {
                if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
                    EmoticonsKeyboardUtils.closeSoftKeyboard((View) editText);
                } else {
                    EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
                }
            }
            showFuncView(key);
        } else if (!isSoftKeyboardPop) {
            EmoticonsKeyboardUtils.openSoftKeyboard(getContext(), editText);
        } else if (EmoticonsKeyboardUtils.isFullScreen((Activity) getContext())) {
            EmoticonsKeyboardUtils.closeSoftKeyboard((View) editText);
        } else {
            EmoticonsKeyboardUtils.closeSoftKeyboard(getContext());
        }
    }

    public void showFuncView(int key) {
        if (this.mFuncViewArrayMap.get(key) != null) {
            for (int i = 0; i < this.mFuncViewArrayMap.size(); i++) {
                int keyTemp = this.mFuncViewArrayMap.keyAt(i);
                if (keyTemp == key) {
                    ((View) this.mFuncViewArrayMap.get(keyTemp)).setVisibility(0);
                } else {
                    ((View) this.mFuncViewArrayMap.get(keyTemp)).setVisibility(8);
                }
            }
            this.mCurrentFuncKey = key;
            setVisibility(true);
            if (this.onFuncChangeListener != null) {
                this.onFuncChangeListener.onFuncChange(this.mCurrentFuncKey);
            }
        }
    }

    public int getCurrentFuncKey() {
        return this.mCurrentFuncKey;
    }

    public void updateHeight(int height) {
        this.mHeight = height;
    }

    public void setVisibility(boolean b) {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
        if (b) {
            setVisibility(0);
            params.height = this.mHeight;
            if (this.mListenerList != null) {
                for (OnFuncKeyBoardListener l : this.mListenerList) {
                    l.OnFuncPop(this.mHeight);
                }
            }
        } else {
            setVisibility(8);
            params.height = 0;
            if (this.mListenerList != null) {
                for (OnFuncKeyBoardListener l2 : this.mListenerList) {
                    l2.OnFuncClose();
                }
            }
        }
        setLayoutParams(params);
    }

    public boolean isOnlyShowSoftKeyboard() {
        return this.mCurrentFuncKey == Integer.MIN_VALUE;
    }

    public void addOnKeyBoardListener(OnFuncKeyBoardListener l) {
        if (this.mListenerList == null) {
            this.mListenerList = new ArrayList();
        }
        this.mListenerList.add(l);
    }

    public void setOnFuncChangeListener(OnFuncChangeListener listener) {
        this.onFuncChangeListener = listener;
    }
}
