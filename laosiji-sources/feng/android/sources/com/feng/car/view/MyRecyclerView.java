package com.feng.car.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class MyRecyclerView extends RecyclerView {
    long downTime = 0;
    private BlankSpaceClickListener mBlankLSpaceistener;
    private Context mContext;
    long upTime = 0;

    public interface BlankSpaceClickListener {
        void onBlackClick();
    }

    public void setBlankLSpaceistener(BlankSpaceClickListener blankLSpaceistener) {
        this.mBlankLSpaceistener = blankLSpaceistener;
    }

    public MyRecyclerView(Context context) {
        super(context);
        this.mContext = context;
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mContext = context;
    }

    public boolean onTouchEvent(MotionEvent e) {
        switch (e.getAction()) {
            case 0:
                this.downTime = System.currentTimeMillis();
                break;
            case 1:
            case 3:
                this.upTime = System.currentTimeMillis();
                if (this.upTime - this.downTime < 500 && this.mBlankLSpaceistener != null) {
                    this.mBlankLSpaceistener.onBlackClick();
                    break;
                }
        }
        return super.onTouchEvent(e);
    }
}
