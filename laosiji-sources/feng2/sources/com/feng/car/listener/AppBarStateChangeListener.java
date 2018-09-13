package com.feng.car.listener;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.AppBarLayout.OnOffsetChangedListener;

public abstract class AppBarStateChangeListener implements OnOffsetChangedListener {
    private State mCurrentState = State.IDLE;

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state);

    public abstract void onVerticalOffset(int i, int i2);

    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (verticalOffset == 0) {
            if (this.mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED);
            }
            this.mCurrentState = State.EXPANDED;
        } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
            if (this.mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED);
            }
            this.mCurrentState = State.COLLAPSED;
        } else {
            if (this.mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE);
            }
            this.mCurrentState = State.IDLE;
        }
        onVerticalOffset(verticalOffset, appBarLayout.getTotalScrollRange());
    }
}
