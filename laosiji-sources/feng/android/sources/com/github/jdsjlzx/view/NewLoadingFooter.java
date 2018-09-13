package com.github.jdsjlzx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;

public class NewLoadingFooter extends RelativeLayout {
    private AVLoadingIndicatorView mLoadingProgress;
    private TextView mLoadingText;
    private View mLoadingView;
    protected com.github.jdsjlzx.view.LoadingFooter.State mState = com.github.jdsjlzx.view.LoadingFooter.State.Normal;

    public enum State {
        Normal,
        TheEnd,
        Loading,
        NetWorkError
    }

    public NewLoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public NewLoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NewLoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        this.mLoadingView = LayoutInflater.from(context).inflate(R.layout.layout_recyclerview_list_footer_loading, null);
        addView(this.mLoadingView);
        this.mLoadingProgress = (AVLoadingIndicatorView) this.mLoadingView.findViewById(R.id.loading_progress);
        setOnClickListener(null);
        setState(com.github.jdsjlzx.view.LoadingFooter.State.Normal, true);
    }

    public com.github.jdsjlzx.view.LoadingFooter.State getState() {
        return this.mState;
    }

    public void setState(com.github.jdsjlzx.view.LoadingFooter.State status) {
        setState(status, true);
    }

    public void setState(com.github.jdsjlzx.view.LoadingFooter.State status, boolean showView) {
        if (this.mState != status) {
            this.mState = status;
            switch (status) {
                case Normal:
                    setOnClickListener(null);
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                        return;
                    }
                    return;
                case Loading:
                    setOnClickListener(null);
                    this.mLoadingView.setVisibility(0);
                    return;
                case TheEnd:
                    setOnClickListener(null);
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                        return;
                    }
                    return;
                case NetWorkError:
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    public void setBottomMargin(int margin) {
        if (margin >= 0) {
            LayoutParams lp = (LayoutParams) this.mLoadingView.getLayoutParams();
            lp.bottomMargin = margin;
            this.mLoadingView.setLayoutParams(lp);
        }
    }

    public int getBottomMargin() {
        return ((LayoutParams) this.mLoadingView.getLayoutParams()).bottomMargin;
    }

    public void hide() {
        LayoutParams lp = (LayoutParams) this.mLoadingView.getLayoutParams();
        lp.height = 0;
        this.mLoadingView.setLayoutParams(lp);
    }

    public void show() {
        LayoutParams lp = (LayoutParams) this.mLoadingView.getLayoutParams();
        lp.height = -2;
        this.mLoadingView.setLayoutParams(lp);
    }

    public void onMove(float delta) {
        int height = getBottomMargin() + ((int) delta);
        if (delta > 0.0f) {
            setState(com.github.jdsjlzx.view.LoadingFooter.State.Loading);
            setBottomMargin(height);
        }
    }
}
