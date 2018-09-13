package com.feng.car.view;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.view.View;
import android.widget.PopupWindow;

public class Solve7PopupWindow extends PopupWindow {
    public Solve7PopupWindow(View mMenuView, int width, int height) {
        super(mMenuView, width, height);
    }

    public void showAsDropDown(View anchor) {
        if (VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            setHeight(anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom);
        }
        super.showAsDropDown(anchor);
    }
}
