package com.feng.car.view.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.MotionEvent;

public class OnRecyclerItemClickListener implements OnItemTouchListener {
    private GestureDetectorCompat mGestureDetector;
    private RecyclerView recyclerView;

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        this.mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(), new ItemTouchHelperGestureListener(this, null));
    }

    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
        return false;
    }

    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
    }

    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public void onLongClick(ViewHolder vh, MotionEvent e) {
    }

    public void onItemClick(ViewHolder vh) {
    }
}
