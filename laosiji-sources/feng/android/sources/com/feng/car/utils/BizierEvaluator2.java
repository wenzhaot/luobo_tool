package com.feng.car.utils;

import android.animation.TypeEvaluator;
import android.graphics.Point;

public class BizierEvaluator2 implements TypeEvaluator<Point> {
    private Point controllPoint;

    public BizierEvaluator2(Point controllPoint) {
        this.controllPoint = controllPoint;
    }

    public Point evaluate(float t, Point startValue, Point endValue) {
        return new Point((int) (((((1.0f - t) * (1.0f - t)) * ((float) startValue.x)) + (((2.0f * t) * (1.0f - t)) * ((float) this.controllPoint.x))) + ((t * t) * ((float) endValue.x))), (int) (((((1.0f - t) * (1.0f - t)) * ((float) startValue.y)) + (((2.0f * t) * (1.0f - t)) * ((float) this.controllPoint.y))) + ((t * t) * ((float) endValue.y))));
    }
}
