package com.github.jdsjlzx.util;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.view.LoadingFooter;
import com.github.jdsjlzx.view.LoadingFooter.State;

public class RecyclerViewStateUtils {
    public static void setFooterViewState(Activity instance, RecyclerView recyclerView, int pageSize, State state, OnSingleClickListener errorListener) {
        if (instance != null && !instance.isFinishing()) {
            Adapter outerAdapter = recyclerView.getAdapter();
            if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
                LRecyclerViewAdapter lRecyclerViewAdapter = (LRecyclerViewAdapter) outerAdapter;
                if (lRecyclerViewAdapter.getFooterViewsCount() > 0) {
                    LoadingFooter footerView = (LoadingFooter) lRecyclerViewAdapter.getFooterView();
                    footerView.setState(state);
                    footerView.setVisibility(0);
                    if (state == State.NetWorkError) {
                        footerView.setOnClickListener(errorListener);
                    } else if (state == State.Normal) {
                        footerView.setVisibility(8);
                    } else if (state == State.TheEnd) {
                        ((LRecyclerView) recyclerView).setNoMore(true);
                    }
                }
            }
        }
    }

    public static void setFooterViewState2(Activity instance, RecyclerView recyclerView, int pageSize, State state, OnSingleClickListener errorListener) {
        if (instance != null && !instance.isFinishing()) {
            Adapter outerAdapter = recyclerView.getAdapter();
            if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
                LRecyclerViewAdapter lRecyclerViewAdapter = (LRecyclerViewAdapter) outerAdapter;
                LoadingFooter footerView;
                if (lRecyclerViewAdapter.getFooterViewsCount() > 0) {
                    footerView = (LoadingFooter) lRecyclerViewAdapter.getFooterView();
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
                lRecyclerViewAdapter.addFooterView(footerView);
                recyclerView.scrollToPosition(0);
            }
        }
    }

    public static State getFooterViewState(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter == null || !(outerAdapter instanceof LRecyclerViewAdapter) || ((LRecyclerViewAdapter) outerAdapter).getFooterViewsCount() <= 0) {
            return State.Normal;
        }
        return ((LoadingFooter) ((LRecyclerViewAdapter) outerAdapter).getFooterView()).getState();
    }

    public static boolean isRecylerViewLoading(RecyclerView recyclerView) {
        if (getFooterViewState(recyclerView) == State.Loading) {
            return true;
        }
        return false;
    }

    public static void setFooterViewState(RecyclerView recyclerView, State state) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter) && ((LRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
            ((LoadingFooter) ((LRecyclerViewAdapter) outerAdapter).getFooterView()).setState(state);
        }
    }
}
