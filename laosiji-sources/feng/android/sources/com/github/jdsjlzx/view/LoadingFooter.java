package com.github.jdsjlzx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewStub;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;

public class LoadingFooter extends RelativeLayout {
    private FooterCallBack callBack;
    private AVLoadingIndicatorView mLoadingProgress;
    private TextView mLoadingText;
    private View mLoadingView;
    private View mNetworkErrorView;
    protected State mState = State.Normal;
    private View mTheEndView;

    public enum State {
        Normal,
        TheEnd,
        Loading,
        NetWorkError,
        TheEndRefesh,
        TheEndHint,
        TheEndRefeshHint,
        TheEndShowBtn
    }

    public LoadingFooter(Context context) {
        super(context);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingFooter(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        inflate(context, R.layout.layout_recyclerview_list_footer, this);
        setOnClickListener(null);
        setState(State.Normal, true);
    }

    public State getState() {
        return this.mState;
    }

    public void setState(State status) {
        setState(status, true);
    }

    public void setState(State status, boolean showView) {
        int i = 0;
        if (this.mState != status) {
            this.mState = status;
            View view;
            switch (status) {
                case Normal:
                    setOnClickListener(null);
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                        return;
                    }
                    return;
                case Loading:
                    int i2;
                    setOnClickListener(null);
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                    }
                    if (this.mLoadingView == null) {
                        this.mLoadingView = ((ViewStub) findViewById(R.id.loading_viewstub)).inflate();
                        this.mLoadingProgress = (AVLoadingIndicatorView) this.mLoadingView.findViewById(R.id.loading_progress);
                        this.mLoadingText = (TextView) this.mLoadingView.findViewById(R.id.loading_text);
                    } else {
                        this.mLoadingView.setVisibility(0);
                    }
                    View view2 = this.mLoadingView;
                    if (showView) {
                        i2 = 0;
                    } else {
                        i2 = 8;
                    }
                    view2.setVisibility(i2);
                    this.mLoadingProgress.setVisibility(0);
                    this.mLoadingText.setText(R.string.list_footer_loading);
                    return;
                case TheEnd:
                    setOnClickListener(null);
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                    }
                    if (this.mTheEndView == null) {
                        this.mTheEndView = ((ViewStub) findViewById(R.id.end_viewstub)).inflate();
                    } else {
                        this.mTheEndView.setVisibility(8);
                    }
                    view = this.mTheEndView;
                    if (!showView) {
                        i = 8;
                    }
                    view.setVisibility(i);
                    return;
                case NetWorkError:
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView == null) {
                        this.mNetworkErrorView = ((ViewStub) findViewById(R.id.network_error_viewstub)).inflate();
                    } else {
                        this.mNetworkErrorView.setVisibility(0);
                    }
                    view = this.mNetworkErrorView;
                    if (!showView) {
                        i = 8;
                    }
                    view.setVisibility(i);
                    return;
                case TheEndRefeshHint:
                case TheEndRefesh:
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                    }
                    view = findViewById(R.id.iv_nodata_refresh);
                    if (status != State.TheEndRefesh) {
                        i = 8;
                    }
                    view.setVisibility(i);
                    return;
                case TheEndHint:
                    if (!isShown()) {
                        setVisibility(0);
                    }
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                    }
                    findViewById(R.id.tv_end_hint).setVisibility(0);
                    return;
                case TheEndShowBtn:
                    if (!isShown()) {
                        setVisibility(0);
                    }
                    if (this.mLoadingView != null) {
                        this.mLoadingView.setVisibility(8);
                    }
                    if (this.mTheEndView != null) {
                        this.mTheEndView.setVisibility(8);
                    }
                    if (this.mNetworkErrorView != null) {
                        this.mNetworkErrorView.setVisibility(8);
                    }
                    findViewById(R.id.btn_shows_all).setVisibility(0);
                    findViewById(R.id.btn_shows_all).setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            if (LoadingFooter.this.callBack != null) {
                                LoadingFooter.this.callBack.onClick();
                            }
                        }
                    });
                    return;
                default:
                    return;
            }
        }
    }

    public void setCallBack(FooterCallBack callBack) {
        this.callBack = callBack;
    }
}
