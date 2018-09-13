package com.github.jdsjlzx.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import com.github.jdsjlzx.interfaces.BaseRefreshHeader;

public class JellyView extends View implements BaseRefreshHeader {
    private int jellyHeight = 0;
    private int minimumHeight = 0;
    Paint paint;
    Path path;

    public JellyView(Context context) {
        super(context);
        init();
    }

    public JellyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JellyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public JellyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            this.path = new Path();
            this.paint = new Paint();
            this.paint.setColor(ContextCompat.getColor(getContext(), 17170459));
            this.paint.setAntiAlias(true);
        }
    }

    public void setJellyColor(int jellyColor) {
        this.paint.setColor(jellyColor);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.path.reset();
        this.path.lineTo(0.0f, (float) this.minimumHeight);
        this.path.quadTo((float) (getMeasuredWidth() / 2), (float) (this.minimumHeight + this.jellyHeight), (float) getMeasuredWidth(), (float) this.minimumHeight);
        this.path.lineTo((float) getMeasuredWidth(), 0.0f);
        canvas.drawPath(this.path, this.paint);
    }

    public void setMinimumHeight(int minimumHeight) {
        this.minimumHeight = minimumHeight;
    }

    public void setJellyHeight(int ribbonHeight) {
        this.jellyHeight = ribbonHeight;
    }

    public int getMinimumHeight() {
        return this.minimumHeight;
    }

    public int getJellyHeight() {
        return this.jellyHeight;
    }

    public void refreshComplete() {
    }

    public void onMove(float delta) {
        this.jellyHeight += (int) delta;
        invalidate();
    }

    public boolean releaseAction() {
        return false;
    }
}
