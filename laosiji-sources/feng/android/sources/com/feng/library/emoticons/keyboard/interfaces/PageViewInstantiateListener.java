package com.feng.library.emoticons.keyboard.interfaces;

import android.view.View;
import android.view.ViewGroup;
import com.feng.library.emoticons.keyboard.data.PageEntity;

public interface PageViewInstantiateListener<T extends PageEntity> {
    View instantiateItem(ViewGroup viewGroup, int i, T t);
}
