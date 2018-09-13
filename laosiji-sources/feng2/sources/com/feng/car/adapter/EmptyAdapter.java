package com.feng.car.adapter;

import android.content.Context;
import android.view.ViewGroup;
import com.feng.car.databinding.ItemMineBinding;
import java.util.ArrayList;

public class EmptyAdapter extends MvvmBaseAdapter<Integer, ItemMineBinding> {
    public EmptyAdapter(Context context) {
        super(context, new ArrayList());
    }

    public ItemMineBinding getBinding(ViewGroup parent, int viewType) {
        return ItemMineBinding.inflate(this.mInflater);
    }

    public void dataBindingTo(ItemMineBinding itemMineBinding, Integer userInfo) {
    }
}
