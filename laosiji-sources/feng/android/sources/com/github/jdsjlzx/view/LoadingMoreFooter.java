package com.github.jdsjlzx.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.jdsjlzx.R;
import com.github.jdsjlzx.progressindicator.AVLoadingIndicatorView;

public class LoadingMoreFooter extends LinearLayout {
    public static final int STATE_COMPLETE = 1;
    public static final int STATE_LOADING = 0;
    public static final int STATE_NOMORE = 2;
    private TextView mText;
    private SimpleViewSwitcher progressCon;

    public LoadingMoreFooter(Context context) {
        super(context);
        initView();
    }

    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public void initView() {
        setGravity(17);
        setLayoutParams(new LayoutParams(-1, -2));
        this.progressCon = new SimpleViewSwitcher(getContext());
        this.progressCon.setLayoutParams(new LayoutParams(-2, -2));
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(-4868683);
        progressView.setIndicatorId(22);
        this.progressCon.setView(progressView);
        addView(this.progressCon);
        this.mText = new TextView(getContext());
        this.mText.setText("正在加载...");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.setMargins((int) getResources().getDimension(R.dimen.textandiconmargin), 0, 0, 0);
        this.mText.setLayoutParams(layoutParams);
        addView(this.mText);
    }

    public void setProgressStyle(int style) {
        if (style == -1) {
            this.progressCon.setView(new ProgressBar(getContext(), null, 16842871));
            return;
        }
        AVLoadingIndicatorView progressView = new AVLoadingIndicatorView(getContext());
        progressView.setIndicatorColor(-4868683);
        progressView.setIndicatorId(style);
        this.progressCon.setView(progressView);
    }

    public void setState(int state) {
        switch (state) {
            case 0:
                this.progressCon.setVisibility(0);
                this.mText.setText(getContext().getText(R.string.listview_loading));
                setVisibility(0);
                return;
            case 1:
                this.mText.setText(getContext().getText(R.string.listview_loading));
                setVisibility(8);
                return;
            case 2:
                this.mText.setText(getContext().getText(R.string.nomore_loading));
                this.progressCon.setVisibility(8);
                setVisibility(0);
                return;
            default:
                return;
        }
    }
}
