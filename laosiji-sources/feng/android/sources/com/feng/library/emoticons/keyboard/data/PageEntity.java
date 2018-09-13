package com.feng.library.emoticons.keyboard.data;

import android.view.View;
import android.view.ViewGroup;
import com.feng.library.emoticons.keyboard.interfaces.PageViewInstantiateListener;

public class PageEntity<T extends PageEntity> implements PageViewInstantiateListener<T> {
    protected PageViewInstantiateListener mPageViewInstantiateListener;
    protected View mRootView;

    public void setIPageViewInstantiateItem(PageViewInstantiateListener pageViewInstantiateListener) {
        this.mPageViewInstantiateListener = pageViewInstantiateListener;
    }

    public View getRootView() {
        return this.mRootView;
    }

    public void setRootView(View rootView) {
        this.mRootView = rootView;
    }

    public PageEntity(View view) {
        this.mRootView = view;
    }

    public View instantiateItem(ViewGroup container, int position, T t) {
        if (this.mPageViewInstantiateListener != null) {
            return this.mPageViewInstantiateListener.instantiateItem(container, position, this);
        }
        return getRootView();
    }
}
