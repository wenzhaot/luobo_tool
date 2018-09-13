package com.github.johnpersano.supertoasts.util;

import android.view.View;
import com.github.johnpersano.supertoasts.SuperToast.OnDismissListener;

public class OnDismissWrapper implements OnDismissListener {
    private final OnDismissListener mOnDismissListener;
    private final String mTag;

    public OnDismissWrapper(String tag, OnDismissListener onDismissListener) {
        this.mTag = tag;
        this.mOnDismissListener = onDismissListener;
    }

    public String getTag() {
        return this.mTag;
    }

    public void onDismiss(View view) {
        this.mOnDismissListener.onDismiss(view);
    }
}
