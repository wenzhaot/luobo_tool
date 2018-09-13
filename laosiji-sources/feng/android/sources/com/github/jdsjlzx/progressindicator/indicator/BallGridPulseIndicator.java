package com.github.jdsjlzx.progressindicator.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.feng.car.utils.FengConstant;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.rtmp.TXLiveConstants;
import java.util.ArrayList;
import java.util.List;

public class BallGridPulseIndicator extends BaseIndicatorController {
    public static final int ALPHA = 255;
    public static final float SCALE = 1.0f;
    int[] alphas = new int[]{255, 255, 255, 255, 255, 255, 255, 255, 255};
    float[] scaleFloats = new float[]{1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};

    public void draw(Canvas canvas, Paint paint) {
        float radius = (((float) getWidth()) - (4.0f * 4.0f)) / 6.0f;
        float x = ((float) (getWidth() / 2)) - ((2.0f * radius) + 4.0f);
        float y = ((float) (getWidth() / 2)) - ((2.0f * radius) + 4.0f);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                canvas.save();
                canvas.translate((((2.0f * radius) * ((float) j)) + x) + (((float) j) * 4.0f), (((2.0f * radius) * ((float) i)) + y) + (((float) i) * 4.0f));
                canvas.scale(this.scaleFloats[(i * 3) + j], this.scaleFloats[(i * 3) + j]);
                paint.setAlpha(this.alphas[(i * 3) + j]);
                canvas.drawCircle(0.0f, 0.0f, radius, paint);
                canvas.restore();
            }
        }
    }

    public List<Animator> createAnimation() {
        List<Animator> animators = new ArrayList();
        int[] durations = new int[]{720, TXLiveConstants.PUSH_EVT_ROOM_USERLIST, 1280, 1420, 1450, 1180, 870, 1450, 1060};
        int[] delays = new int[]{-60, FengConstant.IMAGE_SMALL_WIDTH, -170, 480, 310, 30, 460, 780, 450};
        for (int i = 0; i < 9; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(new float[]{1.0f, 0.5f, 1.0f});
            scaleAnim.setDuration((long) durations[i]);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay((long) delays[i]);
            scaleAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallGridPulseIndicator.this.scaleFloats[index] = ((Float) animation.getAnimatedValue()).floatValue();
                    BallGridPulseIndicator.this.postInvalidate();
                }
            });
            scaleAnim.start();
            ValueAnimator alphaAnim = ValueAnimator.ofInt(new int[]{255, 210, IjkMediaMeta.FF_PROFILE_H264_HIGH_422, 255});
            alphaAnim.setDuration((long) durations[i]);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay((long) delays[i]);
            alphaAnim.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator animation) {
                    BallGridPulseIndicator.this.alphas[index] = ((Integer) animation.getAnimatedValue()).intValue();
                    BallGridPulseIndicator.this.postInvalidate();
                }
            });
            alphaAnim.start();
            animators.add(scaleAnim);
            animators.add(alphaAnim);
        }
        return animators;
    }
}
