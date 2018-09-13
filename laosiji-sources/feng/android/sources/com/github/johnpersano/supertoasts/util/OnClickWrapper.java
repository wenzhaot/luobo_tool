package com.github.johnpersano.supertoasts.util;

import android.os.Parcelable;
import android.view.View;
import com.github.johnpersano.supertoasts.SuperToast.OnClickListener;

public class OnClickWrapper implements OnClickListener {
    private final OnClickListener mOnClickListener;
    private final String mTag;
    private Parcelable mToken;

    public OnClickWrapper(String tag, OnClickListener onClickListener) {
        this.mTag = tag;
        this.mOnClickListener = onClickListener;
    }

    public String getTag() {
        return this.mTag;
    }

    public void setToken(Parcelable token) {
        this.mToken = token;
    }

    public void onClick(View view, Parcelable token) {
        this.mOnClickListener.onClick(view, this.mToken);
    }
}
