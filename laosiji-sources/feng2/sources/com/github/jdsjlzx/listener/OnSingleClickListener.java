package com.github.jdsjlzx.listener;

import android.view.View;
import android.view.View.OnClickListener;
import com.baidu.mapapi.UIMsg.d_ResultType;

public abstract class OnSingleClickListener implements OnClickListener {
    private final int INTERVAL_TIME = d_ResultType.SHORT_URL;
    private long mFirstTime = 0;

    public abstract void onSingleClick(View view);

    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - this.mFirstTime >= 500) {
            this.mFirstTime = nowTime;
            onSingleClick(v);
        }
    }
}
