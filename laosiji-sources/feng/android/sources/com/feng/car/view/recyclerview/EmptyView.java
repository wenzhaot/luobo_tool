package com.feng.car.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.feng.car.databinding.EmptyLayoutBinding;

public class EmptyView extends LinearLayout {
    private Context mContext;
    private EmptyLayoutBinding mEmptyLayoutBinding;

    public EmptyView(Context context) {
        super(context);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public EmptyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mEmptyLayoutBinding = EmptyLayoutBinding.inflate(LayoutInflater.from(this.mContext), this, true);
    }

    public void setEmptyImage(int drawable) {
        this.mEmptyLayoutBinding.emptyImage.setVisibility(0);
        this.mEmptyLayoutBinding.emptyImage.setBackgroundResource(drawable);
    }

    public void hideEmptyImage() {
        this.mEmptyLayoutBinding.emptyImage.setVisibility(8);
        LayoutParams p = (LayoutParams) this.mEmptyLayoutBinding.emptyText.getLayoutParams();
        p.setMargins(0, this.mContext.getResources().getDimensionPixelSize(2131296413), 0, 0);
        this.mEmptyLayoutBinding.emptyText.setLayoutParams(p);
    }

    public void setEmptyText(int text) {
        this.mEmptyLayoutBinding.emptyText.setVisibility(0);
        this.mEmptyLayoutBinding.emptyText.setText(text);
    }

    public void hideEmptyText() {
        this.mEmptyLayoutBinding.emptyText.setVisibility(8);
    }

    public void setButtonListener(int text, OnClickListener l) {
        this.mEmptyLayoutBinding.button.setVisibility(0);
        this.mEmptyLayoutBinding.button.setText(text);
        this.mEmptyLayoutBinding.button.setOnClickListener(l);
    }

    public void setButtonListener(OnClickListener l) {
        this.mEmptyLayoutBinding.btnShowsAll.setVisibility(0);
        this.mEmptyLayoutBinding.btnShowsAll.setOnClickListener(l);
    }

    public void hideEmptyButton() {
        this.mEmptyLayoutBinding.button.setVisibility(8);
    }
}
