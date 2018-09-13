package com.github.jdsjlzx.progressindicator.indicator;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class LineSpinFadeLoaderIndicator extends BallSpinFadeLoaderIndicator {
    public void draw(Canvas canvas, Paint paint) {
        float radius = (float) (getWidth() / 10);
        for (int i = 0; i < 8; i++) {
            canvas.save();
            Point point = circleAt(getWidth(), getHeight(), (((float) getWidth()) / 2.5f) - radius, ((double) i) * 0.7853981633974483d);
            canvas.translate(point.x, point.y);
            canvas.scale(this.scaleFloats[i], this.scaleFloats[i]);
            canvas.rotate((float) (i * 45));
            paint.setAlpha(this.alphas[i]);
            canvas.drawRoundRect(new RectF(-radius, (-radius) / 1.5f, 1.5f * radius, radius / 1.5f), 5.0f, 5.0f, paint);
            canvas.restore();
        }
    }
}
