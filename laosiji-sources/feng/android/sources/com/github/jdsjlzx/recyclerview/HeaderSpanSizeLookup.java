package com.github.jdsjlzx.recyclerview;

import android.support.v7.widget.GridLayoutManager.SpanSizeLookup;

public class HeaderSpanSizeLookup extends SpanSizeLookup {
    private LRecyclerViewAdapter adapter;
    private int mSpanSize = 1;

    public HeaderSpanSizeLookup(LRecyclerViewAdapter adapter, int spanSize) {
        this.adapter = adapter;
        this.mSpanSize = spanSize;
    }

    public int getSpanSize(int position) {
        boolean isHeaderOrFooter = this.adapter.isRefreshHeader(position) || this.adapter.isHeader(position) || this.adapter.isFooter(position);
        if (isHeaderOrFooter) {
            return this.mSpanSize;
        }
        return 1;
    }
}
