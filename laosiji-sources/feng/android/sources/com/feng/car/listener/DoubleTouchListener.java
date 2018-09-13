package com.feng.car.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class DoubleTouchListener implements OnTouchListener {
    private long last_down = 0;
    private DoubleClickCallBack mCallBack;

    public DoubleTouchListener(DoubleClickCallBack callback) {
        this.mCallBack = callback;
    }

    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case 0:
                long current_down = System.currentTimeMillis();
                if (!(this.last_down == 0 || current_down - this.last_down >= 400 || this.mCallBack == null)) {
                    try {
                        this.mCallBack.callBack();
                    } catch (Exception e) {
                    }
                }
                this.last_down = current_down;
                break;
        }
        return true;
    }
}
