package com.feng.library.emoticons.keyboard.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.RelativeLayout;
import java.util.ArrayList;
import java.util.List;

public class SoftKeyboardSizeWatchLayout extends RelativeLayout {
    private Context mContext;
    private boolean mIsSoftKeyboardPop = false;
    private List<OnResizeListener> mListenerList;
    private int mNowh = -1;
    private int mOldh = -1;
    private int mScreenHeight = 0;

    public interface OnResizeListener {
        void OnSoftClose();

        void OnSoftPop(int i);
    }

    public SoftKeyboardSizeWatchLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                Rect r = new Rect();
                ((Activity) SoftKeyboardSizeWatchLayout.this.mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                if (SoftKeyboardSizeWatchLayout.this.mScreenHeight == 0) {
                    SoftKeyboardSizeWatchLayout.this.mScreenHeight = r.bottom;
                }
                SoftKeyboardSizeWatchLayout.this.mNowh = SoftKeyboardSizeWatchLayout.this.mScreenHeight - r.bottom;
                if (!(SoftKeyboardSizeWatchLayout.this.mOldh == -1 || SoftKeyboardSizeWatchLayout.this.mNowh == SoftKeyboardSizeWatchLayout.this.mOldh)) {
                    if (SoftKeyboardSizeWatchLayout.this.mNowh <= 0) {
                        SoftKeyboardSizeWatchLayout.this.mIsSoftKeyboardPop = false;
                        if (SoftKeyboardSizeWatchLayout.this.mListenerList != null) {
                            for (OnResizeListener l : SoftKeyboardSizeWatchLayout.this.mListenerList) {
                                l.OnSoftClose();
                            }
                        }
                    } else if (SoftKeyboardSizeWatchLayout.this.mNowh >= 150) {
                        SoftKeyboardSizeWatchLayout.this.mIsSoftKeyboardPop = true;
                        if (SoftKeyboardSizeWatchLayout.this.mListenerList != null) {
                            for (OnResizeListener l2 : SoftKeyboardSizeWatchLayout.this.mListenerList) {
                                l2.OnSoftPop(SoftKeyboardSizeWatchLayout.this.mNowh);
                            }
                        }
                    } else {
                        return;
                    }
                }
                SoftKeyboardSizeWatchLayout.this.mOldh = SoftKeyboardSizeWatchLayout.this.mNowh;
            }
        });
    }

    public boolean isSoftKeyboardPop() {
        return this.mIsSoftKeyboardPop;
    }

    public void addOnResizeListener(OnResizeListener l) {
        if (this.mListenerList == null) {
            this.mListenerList = new ArrayList();
        }
        this.mListenerList.add(l);
    }
}
