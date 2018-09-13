package com.github.jdsjlzx.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import com.github.jdsjlzx.recyclerview.LuRecyclerViewAdapter;

public class LuRecyclerViewUtils {
    @Deprecated
    public static void setHeaderView(RecyclerView recyclerView, View view) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
            ((LuRecyclerViewAdapter) outerAdapter).addHeaderView(view);
        }
    }

    @Deprecated
    public static void setFooterView(RecyclerView recyclerView, View view) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
            LuRecyclerViewAdapter luRecyclerViewAdapter = (LuRecyclerViewAdapter) outerAdapter;
            if (luRecyclerViewAdapter.getFooterViewsCount() > 0) {
                luRecyclerViewAdapter.removeFooterView(luRecyclerViewAdapter.getFooterView());
            }
            luRecyclerViewAdapter.addFooterView(view);
        }
    }

    public static void removeFooterView(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter) && ((LuRecyclerViewAdapter) outerAdapter).getFooterViewsCount() > 0) {
            ((LuRecyclerViewAdapter) outerAdapter).removeFooterView(((LuRecyclerViewAdapter) outerAdapter).getFooterView());
        }
    }

    public static void removeHeaderView(RecyclerView recyclerView) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter) && ((LuRecyclerViewAdapter) outerAdapter).getHeaderViewsCount() > 0) {
            ((LuRecyclerViewAdapter) outerAdapter).removeHeaderView(((LuRecyclerViewAdapter) outerAdapter).getHeaderView());
        }
    }

    public static int getLayoutPosition(RecyclerView recyclerView, ViewHolder holder) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
            int headerViewCounter = ((LuRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getLayoutPosition() - headerViewCounter;
            }
        }
        return holder.getLayoutPosition();
    }

    public static int getAdapterPosition(RecyclerView recyclerView, ViewHolder holder) {
        Adapter outerAdapter = recyclerView.getAdapter();
        if (outerAdapter != null && (outerAdapter instanceof LuRecyclerViewAdapter)) {
            int headerViewCounter = ((LuRecyclerViewAdapter) outerAdapter).getHeaderViewsCount();
            if (headerViewCounter > 0) {
                return holder.getAdapterPosition() - headerViewCounter;
            }
        }
        return holder.getAdapterPosition();
    }
}
