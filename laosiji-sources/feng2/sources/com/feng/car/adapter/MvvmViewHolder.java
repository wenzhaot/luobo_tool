package com.feng.car.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView.ViewHolder;

public class MvvmViewHolder<V extends ViewDataBinding> extends ViewHolder {
    public V binding;

    public MvvmViewHolder(V binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}
