package com.lovejjfg.circle.widget;

/**
 * Created by Joe on 2016-06-16
 * Email: lovejjfg@gmail.com
 */

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class BezierEvaluator implements TypeEvaluator<PointF> {

    public PointF[] getPoints() {
        return points;
    }

    public void setPoints(PointF... points) {

        this.points = points;
    }

    private PointF points[];

    public BezierEvaluator(PointF... points) {
//        if (points.length != 4) {
//            throw new IllegalArgumentException("只演示三次方贝赛尔曲线");
//        }
        this.points = points;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        // B(t) = P0 * (1-t)^3 + 3 * P1 * t * (1-t)^2 + 3 * P2 * t^2 * (1-t) + P3 * t^3


        float one_t = 1.0f - fraction;

        PointF P0 = points[0];
        PointF P1 = points[1];
        PointF P2 = points[2];
//        PointF P3 = points[3];
//        二次
//        B(t) = (1 - t)^2 * P0 + 2t * (1 - t) * P1 + t^2 * P2, t ∈ [0,1]
        float x = (float) one_t * one_t * P0.x + 2 * fraction * one_t * P1.x + fraction * fraction * P2.x;
        float y = (float) one_t * one_t * P0.y + 2 * fraction * one_t * P1.y + fraction * fraction * P2.y;

//        float x = (float) (P0.x * Math.pow(one_t, 3) + 3 * P1.x * fraction * Math.pow(one_t, 2) + 3 * P2.x * Math.pow(fraction, 2) * one_t + P3.x * Math.pow(fraction, 3));
//        float y = (float) (P0.y * Math.pow(one_t, 3) + 3 * P1.y * fraction * Math.pow(one_t, 2) + 3 * P2.y * Math.pow(fraction, 2) * one_t + P3.y * Math.pow(fraction, 3));

        return new PointF(x, y);
    }

}