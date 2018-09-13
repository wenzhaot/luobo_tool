package com.uuzuche.lib_zxing;

import android.app.Application;
import android.util.DisplayMetrics;
import com.stub.StubApp;

public class ZApplication extends Application {
    public void onCreate() {
        super.onCreate();
        initDisplayOpinion();
    }

    private void initDisplayOpinion() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        DisplayUtil.density = dm.density;
        DisplayUtil.densityDPI = dm.densityDpi;
        DisplayUtil.screenWidthPx = dm.widthPixels;
        DisplayUtil.screenhightPx = dm.heightPixels;
        DisplayUtil.screenWidthDip = (float) DisplayUtil.px2dip(StubApp.getOrigApplicationContext(getApplicationContext()), (float) dm.widthPixels);
        DisplayUtil.screenHightDip = (float) DisplayUtil.px2dip(StubApp.getOrigApplicationContext(getApplicationContext()), (float) dm.heightPixels);
    }
}
