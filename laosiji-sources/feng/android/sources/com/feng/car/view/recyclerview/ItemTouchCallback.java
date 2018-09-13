package com.feng.car.view.recyclerview;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;

public class ItemTouchCallback extends Callback {
    private Drawable background = null;
    private int bkcolor = -1;
    private ItemTouchAdapter itemTouchAdapter;
    private OnDragListener onDragListener;

    public interface OnDragListener {
        void onFinishDrag();
    }

    public ItemTouchCallback(ItemTouchAdapter itemTouchAdapter) {
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
        if (actionState != 0) {
            this.itemTouchAdapter.onSelectedChanged(viewHolder);
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    public void clearView(RecyclerView recyclerView, ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        this.itemTouchAdapter.clearView(viewHolder);
        viewHolder.itemView.setAlpha(1.0f);
        if (this.onDragListener != null) {
            this.onDragListener.onFinishDrag();
        }
    }

    public ItemTouchCallback setOnDragListener(OnDragListener onDragListener) {
        this.onDragListener = onDragListener;
        return this;
    }
}
