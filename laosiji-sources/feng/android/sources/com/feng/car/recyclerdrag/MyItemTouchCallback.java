package com.feng.car.recyclerdrag;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

public class MyItemTouchCallback extends Callback {
    private ItemTouchAdapter itemTouchAdapter;
    private OnDragListener onDragListener;

    public MyItemTouchCallback(ItemTouchAdapter itemTouchAdapter) {
        this.itemTouchAdapter = itemTouchAdapter;
    }

    public boolean isLongPressDragEnabled() {
        return false;
    }

    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            return makeMovementFlags(15, 0);
        }
        return makeMovementFlags(3, 0);
    }

    public boolean onMove(RecyclerView recyclerView, ViewHolder viewHolder, ViewHolder target) {
        this.itemTouchAdapter.onMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    public void onSwiped(ViewHolder viewHolder, int direction) {
        this.itemTouchAdapter.onSwiped(viewHolder.getAdapterPosition());
    }

    public void onChildDraw(Canvas c, RecyclerView recyclerView, ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == 1) {
            viewHolder.itemView.setAlpha(1.0f - (Math.abs(dX) / ((float) viewHolder.itemView.getWidth())));
            viewHolder.itemView.setTranslationX(dX);
            return;
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    public void onSelectedChanged(ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if (actionState == 0) {
            this.itemTouchAdapter.onChange();
        }
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        if (this.onDragListener != null) {
            this.onDragListener.onFinishDrag();
        }
    }

    public MyItemTouchCallback setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
        return this;
    }
}
