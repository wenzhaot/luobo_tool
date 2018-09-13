package com.feng.car.view;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.StaggeredGridLayoutManager.LayoutParams;
import android.text.TextUtils;
import android.view.View;

public class SpacesItemDecoration extends ItemDecoration {
    private int m10;
    private int m24;
    private String mItemTag = "";

    public SpacesItemDecoration(Context context) {
        this.m10 = context.getResources().getDimensionPixelSize(2131296268);
        this.m24 = context.getResources().getDimensionPixelSize(2131296423);
        this.mItemTag = context.getString(2131231036);
    }

    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        if (view.getTag(2131231036) == null || TextUtils.isEmpty(this.mItemTag) || !view.getTag(2131231036).toString().equals(this.mItemTag)) {
            outRect.top = 0;
            outRect.left = 0;
            outRect.right = 0;
            outRect.bottom = 0;
            return;
        }
        if (params.getSpanIndex() % 2 == 0) {
            outRect.left = this.m24;
            outRect.right = this.m10;
        } else {
            outRect.right = this.m24;
            outRect.left = this.m10;
        }
        outRect.bottom = this.m24;
    }
}
