package com.github.jdsjlzx.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;
import com.github.jdsjlzx.view.LoadingFooter;
import com.github.jdsjlzx.view.LoadingFooter.State;

public class LuRecyclerViewStateUtils {
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, int pageSize, State state, OnSingleClickListener errorListener) {
        if (instance != null && !instance.isFinishing()) {
            Adapter outerAdapter = recyclerView.getAdapter();
            if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
                LuRecyclerViewAdapter LuRecyclerViewAdapter = (LuRecyclerViewAdapter) outerAdapter;
                if (LuRecyclerViewAdapter.getInnerAdapter().getItemCount() >= pageSize && LuRecyclerViewAdapter.getFooterViewsCount() > 0) {
                    LoadingFooter footerView = (LoadingFooter) LuRecyclerViewAdapter.getFooterView();
                    footerView.setState(state);
                    footerView.setVisibility(0);
                    if (state == State.NetWorkError) {
                        footerView.setOnClickListener(errorListener);
                    }
                }
            }
        }
    }

    public static void setFooterViewState2(Activity instance, RecyclerView recyclerView, int pageSize, State state, OnSingleClickListener errorListener) {
        if (instance != null && !instance.isFinishing()) {
            Adapter outerAdapter = recyclerView.getAdapter();
            if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
                LuRecyclerViewAdapter LuRecyclerViewAdapter = (LuRecyclerViewAdapter) outerAdapter;
                LoadingFooter footerView;
                if (LuRecyclerViewAdapter.getFooterViewsCount() > 0) {
                    footerView = (LoadingFooter) LuRecyclerViewAdapter.getFooterView();
                    footerView.setState(state);
                    if (state == State.NetWorkError) {
                        footerView.setOnClickListener(errorListener);
                    }
                    recyclerView.scrollToPosition(0);
                    return;
                }
                footerView = new LoadingFooter(instance);
                footerView.setState(state);
                if (state == State.NetWorkError) {
                    footerView.setOnClickListener(errorListener);
                }
                LuRecyclerViewAdapter.addFooterView(footerView);
                recyclerView.scrollToPosition(0);
            }
        }
    }

    public static State getFooterViewState(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter == null || !(outerAdapter instanceof LuRecyclerViewAdapter) || ((LuRecyclerViewAdapter) outerAdapter).getFooterViewsCount() <= 0) {
            return State.Normal;
        }
        return ((LoadingFooter) ((LuRecyclerViewAdapter) outerAdapter).getFooterView()).getState();
    }

    public static void setFooterViewState(RecyclerView recyclerView, State state) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter) && ((LuRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
            ((LoadingFooter) ((LuRecyclerViewAdapter) outerAdapter).getFooterView()).setState(state);
        }
    }
}
