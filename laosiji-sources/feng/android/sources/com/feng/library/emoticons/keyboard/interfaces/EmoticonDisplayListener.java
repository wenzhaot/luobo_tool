package com.feng.library.emoticons.keyboard.interfaces;

import android.view.ViewGroup;
import com.feng.library.emoticons.keyboard.adpater.EmoticonsAdapter.ViewHolder;

public interface EmoticonDisplayListener<T> {
    void onBindView(int i, ViewGroup viewGroup, ViewHolder viewHolder, T t, boolean z);
}
