package com.feng.car.view.vhtableview;

import android.view.View;
import android.view.ViewGroup;

public interface VHBaseAdapter {
    void OnClickContentRowItem(int i, View view);

    int getContentColumn();

    int getContentRows();

    Object getItem(int i);

    View getPriceView(int i);

    View getTableCellView(int i, int i2, View view, ViewGroup viewGroup);

    View getTitleView(int i, ViewGroup viewGroup);
}
