package com.github.recyclerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class HeaderLayout extends RelativeLayout {
    public HeaderLayout(Context context, int resourcesId) {
        super(context);
        init(context, resourcesId);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int resourcesId) {
        super(context, attrs);
        init(context, resourcesId);
    }

    public HeaderLayout(Context context, AttributeSet attrs, int defStyleAttr, int resourcesId) {
        super(context, attrs, defStyleAttr);
        init(context, resourcesId);
    }

    public void init(Context context, int resourcesId) {
        inflate(context, resourcesId, this);
    }
}
