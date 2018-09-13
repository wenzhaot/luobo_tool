package com.umeng.message.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.widget.Button;
import com.umeng.message.proguard.j;

public class UCloseView extends Button {
    private float cx;
    private float cy;
    private float lineWith;
    private Paint mPaint = new Paint();
    private float p;
    private float radius;
    private int remain;

    public UCloseView(Context context) {
        super(context);
        setBackgroundColor(0);
        this.remain = j.a(context, 1.0f);
        this.lineWith = (float) j.a(context, 2.0f);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.cx = (float) (getWidth() / 2);
        this.cy = (float) (getHeight() / 2);
        this.radius = (float) ((Math.min(getHeight(), getWidth()) / 2) - this.remain);
        this.p = this.radius / 1.4142f;
        this.mPaint.setAntiAlias(true);
        this.mPaint.setColor(-16777216);
        this.mPaint.setStyle(Style.FILL);
        canvas.drawCircle(this.cx, this.cy, this.radius, this.mPaint);
        this.mPaint.setColor(-1);
        this.mPaint.setStrokeWidth(this.lineWith);
        this.mPaint.setStyle(Style.STROKE);
        canvas.drawCircle(this.cx, this.cy, this.radius, this.mPaint);
        Canvas canvas2 = canvas;
        canvas2.drawLine(this.cx - this.p, this.cy - this.p, this.p + this.cx, this.p + this.cy, this.mPaint);
        canvas2 = canvas;
        canvas2.drawLine(this.p + this.cx, this.cy - this.p, this.cx - this.p, this.p + this.cy, this.mPaint);
    }
}
