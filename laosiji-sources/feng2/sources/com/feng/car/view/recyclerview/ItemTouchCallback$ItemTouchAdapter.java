package com.feng.car.view.recyclerview;

import android.support.v7.widget.RecyclerView.ViewHolder;

public interface ItemTouchCallback$ItemTouchAdapter {
    void clearView(ViewHolder viewHolder);

    void onMove(int i, int i2);

    void onSelectedChanged(ViewHolder viewHolder);

    void onSwiped(int i);
}
