package com.feng.car.view.guideview;

import android.content.Context;

public class StoreUtils {
    private Context context;

    public StoreUtils(Context context) {
        this.context = context;
    }

    boolean hasShown(int id) {
        return this.context.getSharedPreferences("showtips", 0).getBoolean("id" + id, false);
    }

    void storeShownId(int id) {
        this.context.getSharedPreferences("showtips", 0).edit().putBoolean("id" + id, true).apply();
    }
}
