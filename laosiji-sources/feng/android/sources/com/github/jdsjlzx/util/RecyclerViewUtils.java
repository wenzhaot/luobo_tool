package com.github.jdsjlzx.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

public class RecyclerViewUtils {
    @Deprecated
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
            ((LRecyclerViewAdapter) outerAdapter).addHeaderView(view);
        }
    }

    @Deprecated
    public static void setFooterView(RecyclerView recyclerView, View view) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
            LRecyclerViewAdapter lRecyclerViewAdapter = (LRecyclerViewAdapter) outerAdapter;
            if (lRecyclerViewAdapter.getFooterViewsCount() > 0) {
                lRecyclerViewAdapter.removeFooterView(lRecyclerViewAdapter.getFooterView());
            }
            lRecyclerViewAdapter.addFooterView(view);
        }
    }

    public static void removeFooterView(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter) && ((LRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
            ((LRecyclerViewAdapter) outerAdapter).removeFooterView(((LRecyclerViewAdapter) outerAdapter).getFooterView());
        }
    }

    public static void removeHeaderView(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter) && ((LRecyclerViewAdapter) outerAdapter).getHeaderViewsCount() > 0) {
            ((LRecyclerViewAdapter) outerAdapter).removeHeaderView(((LRecyclerViewAdapter) outerAdapter).getHeaderView());
        }
    }

    public static int getLayoutPosition(RecyclerView recyclerView, ViewHolder holder) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
            int headerViewCounter = ((LRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - (headerViewCounter + 1);
            }
        }
        return holder.getLayoutPosition();
    }

    public static int getAdapterPosition(RecyclerView recyclerView, ViewHolder holder) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LRecyclerViewAdapter)) {
            int headerViewCounter = ((LRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - (headerViewCounter + 1);
            }
        }
        return holder.getAdapterPosition();
    }
}
