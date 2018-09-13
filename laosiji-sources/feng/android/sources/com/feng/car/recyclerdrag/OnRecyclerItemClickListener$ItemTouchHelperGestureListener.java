package com.feng.car.recyclerdrag;

import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

class OnRecyclerItemClickListener$ItemTouchHelperGestureListener extends SimpleOnGestureListener {
    final /* synthetic */ OnRecyclerItemClickListener this$0;

    private OnRecyclerItemClickListener$ItemTouchHelperGestureListener(OnRecyclerItemClickListener onRecyclerItemClickListener) {
        this.this$0 = onRecyclerItemClickListener;
    }

    public boolean onSingleTapUp(MotionEvent e) {
        View child = OnRecyclerItemClickListener.access$100(this.this$0).findChildViewUnder(e.getX(), e.getY());
        if (child != null) {
            this.this$0.onItemClick(OnRecyclerItemClickListener.access$100(this.this$0).getChildViewHolder(child));
        }
        return true;
    }

    public void onLongPress(MotionEvent e) {
        View child = OnRecyclerItemClickListener.access$100(this.this$0).findChildViewUnder(e.getX(), e.getY());
        if (child != null) {
            this.this$0.onLongClick(OnRecyclerItemClickListener.access$100(this.this$0).getChildViewHolder(child));
        }
    }
}
