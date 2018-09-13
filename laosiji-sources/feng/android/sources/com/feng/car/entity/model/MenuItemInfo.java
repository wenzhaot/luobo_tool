package com.feng.car.entity.model;

import android.databinding.ObservableInt;

public class MenuItemInfo {
    public static final int DRAFT_COMMENT_TYPE = 4;
    public static final int DRAFT_MICRO_TYPE = 2;
    public static final int DRAFT_POST_TYPE = 1;
    public static final int DRAFT_TRANSPOND_TYPE = 3;
    public final ObservableInt countnum = new ObservableInt(0);
    public String title = "";
    public int type = 0;

    public MenuItemInfo(int type, String title, int count) {
        this.type = type;
        this.title = title;
        this.countnum.set(count);
    }
}
