package com.github.jdsjlzx.view;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.interfaces.BaseRefreshHeader;

public class ArrowRefreshHeader extends LinearLayout implements BaseRefreshHeader {
    private SimpleDraweeView mArrowImageView;
    private LinearLayout mContainer;
    private Context mContext;
    public int mMeasuredHeight;
    private int mState = 0;
    private TextView mStatusTextView;

    public ArrowRefreshHeader(Context context) {
        super(context);
        initView();
    }

    public ArrowRefreshHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        this.mContext = getContext();
        this.mContainer = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.listview_header, null);
        LayoutParams lp = new LayoutParams(-1, -2);
        lp.setMargins(0, 0, 0, 0);
        setLayoutParams(lp);
        setPadding(0, 0, 0, 0);
        addView(this.mContainer, new LayoutParams(-1, 0));
        setGravity(80);
        this.mArrowImageView = (SimpleDraweeView) findViewById(R.id.listview_header_arrow);
        this.mStatusTextView = (TextView) findViewById(R.id.refresh_status_textview);
        measure(-2, -2);
        this.mMeasuredHeight = getMeasuredHeight();
        setAutoImageURI();
    }

    private void setAutoImageURI() {
        this.mArrowImageView.setController(((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setUri(Uri.parse("res://com.github.jdsjlzx/" + R.drawable.laosiji)).setAutoPlayAnimations(true)).setOldController(this.mArrowImageView.getController())).build());
    }

    public void setProgressStyle(int style) {
    }

    public void setArrowImageView(int resid) {
    }

    public void setState(int state) {
        if (state != this.mState) {
            this.mArrowImageView.setVisibility(0);
            if (state != 2 && state == 3) {
            }
            switch (state) {
                case 0:
                    this.mStatusTextView.setText(R.string.listview_header_hint_release);
                    break;
                case 1:
                    this.mStatusTextView.setText(R.string.listview_header_hint_normal);
                    break;
                case 2:
                    this.mStatusTextView.setText(R.string.refreshing);
                    break;
                case 3:
                    this.mStatusTextView.setText(R.string.refreshing);
                    break;
            }
            this.mState = state;
        }
    }

    public int getState() {
        return this.mState;
    }

    public void refreshComplete() {
        setState(3);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ArrowRefreshHeader.this.reset();
            }
        }, 200);
    }

    public void setVisibleHeight(int height) {
        if (height < 0) {
            height = 0;
        }
        LayoutParams lp = (LayoutParams) this.mContainer.getLayoutParams();
        lp.height = height;
        this.mContainer.setLayoutParams(lp);
    }

    public int getVisibleHeight() {
        return ((LayoutParams) this.mContainer.getLayoutParams()).height;
    }

    public void onMove(float delta) {
        if (getVisibleHeight() > 0 || delta > 0.0f) {
            setVisibleHeight(((int) delta) + getVisibleHeight());
            if (this.mState > 1) {
                return;
            }
            if (getVisibleHeight() > this.mMeasuredHeight) {
                setState(1);
            } else {
                setState(0);
            }
        }
    }

    public boolean releaseAction() {
        boolean isOnRefresh = false;
        int height = getVisibleHeight();
        if (height == 0) {
            isOnRefresh = false;
        }
        if (getVisibleHeight() > this.mMeasuredHeight && this.mState < 2) {
            setState(2);
            isOnRefresh = true;
        }
        if (this.mState != 2 || height <= this.mMeasuredHeight) {
        }
        int destHeight = 0;
        if (this.mState == 2) {
            destHeight = this.mMeasuredHeight;
        }
        smoothScrollTo(destHeight);
        return isOnRefresh;
    }

    public void reset() {
        smoothScrollTo(0);
        new Handler().postDelayed(new Runnable() {
            public void run() {
                ArrowRefreshHeader.this.setState(0);
            }
        }, 500);
    }

    private void smoothScrollTo(int destHeight) {
        ValueAnimator animator = ValueAnimator.ofInt(new int[]{getVisibleHeight(), destHeight});
        animator.setDuration(300).start();
        animator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                ArrowRefreshHeader.this.setVisibleHeight(((Integer) animation.getAnimatedValue()).intValue());
            }
        });
        animator.start();
    }
}
