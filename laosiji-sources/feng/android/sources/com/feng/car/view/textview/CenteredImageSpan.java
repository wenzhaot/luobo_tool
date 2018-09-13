package com.feng.car.view.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

public class CenteredImageSpan extends ImageSpan {
    public CenteredImageSpan(Context context, int drawableRes) {
        super(context, drawableRes);
    }

    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        Drawable drawable = getDrawable();
        canvas.save();
        FontMetricsInt fm = paint.getFontMetricsInt();
        canvas.translate(x, (float) ((((fm.descent + y) + (fm.ascent + y)) / 2) - (drawable.getBounds().bottom / 2)));
        drawable.draw(canvas);
        canvas.restore();
    }

    public int getSize(Paint paint, CharSequence text, int start, int end, FontMetricsInt fm) {
        Rect rect = getDrawable().getBounds();
        if (fm != null) {
            FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;
            int top = (drHeight / 2) - (fontHeight / 4);
            int bottom = (drHeight / 2) + (fontHeight / 4);
            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    }
}
