package com.github.jdsjlzx.recyclerview;

import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

public class ExStaggeredGridLayoutManager extends StaggeredGridLayoutManager {
    private final String TAG = getClass().getSimpleName();
    private LRecyclerViewAdapter mLRecyclerViewAdapter = null;
    SpanSizeLookup mSpanSizeLookup;

    public ExStaggeredGridLayoutManager(int spanCount, int orientation, LRecyclerViewAdapter adapter) {
        super(spanCount, orientation);
        this.mLRecyclerViewAdapter = adapter;
    }

    public SpanSizeLookup getSpanSizeLookup() {
        return this.mSpanSizeLookup;
    }

    public void setSpanSizeLookup(SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
    }

    public void onMeasure(Recycler recycler, State state, int widthSpec, int heightSpec) {
        int itemCount = this.mLRecyclerViewAdapter.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            Log.d(this.TAG, "lookup  i = " + i + " itemCount = " + itemCount);
            Log.e(this.TAG, "mSpanSizeLookup.getSpanSize(i) " + this.mSpanSizeLookup.getSpanSize(i));
        }
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
    }
}
