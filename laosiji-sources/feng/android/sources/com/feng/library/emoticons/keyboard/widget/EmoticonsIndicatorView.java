package com.feng.library.emoticons.keyboard.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.feng.library.R;
import com.feng.library.emoticons.keyboard.data.PageSetEntity;
import com.feng.library.emoticons.keyboard.utils.EmoticonsKeyboardUtils;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import java.util.ArrayList;
import java.util.Iterator;

public class EmoticonsIndicatorView extends LinearLayout {
    private static final int MARGIN_LEFT = 4;
    protected Bitmap mBmpNomal = BitmapFactory.decodeResource(getResources(), R.drawable.emoji_indicator_point_nomal);
    protected Bitmap mBmpSelect = BitmapFactory.decodeResource(getResources(), R.drawable.emoji_indicator_point_select);
    protected Context mContext;
    protected ArrayList<ImageView> mImageViews;
    protected LayoutParams mLeftLayoutParams = new LayoutParams(-2, -2);
    protected AnimatorSet mPlayByInAnimatorSet;
    protected AnimatorSet mPlayByOutAnimatorSet;
    protected AnimatorSet mPlayToAnimatorSet;

    public EmoticonsIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setOrientation(0);
        this.mLeftLayoutParams.leftMargin = EmoticonsKeyboardUtils.dip2px(context, 4.0f);
    }

    public void playTo(int position, PageSetEntity pageSetEntity) {
        if (checkPageSetEntity(pageSetEntity)) {
            updateIndicatorCount(pageSetEntity.getPageCount());
            Iterator it = this.mImageViews.iterator();
            while (it.hasNext()) {
                ((ImageView) it.next()).setImageBitmap(this.mBmpNomal);
            }
            ((ImageView) this.mImageViews.get(position)).setImageBitmap(this.mBmpSelect);
            Object imageViewStrat = (ImageView) this.mImageViews.get(position);
            ObjectAnimator animIn1 = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 0.25f, 1.0f);
            ObjectAnimator animIn2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 0.25f, 1.0f);
            if (this.mPlayToAnimatorSet != null && this.mPlayToAnimatorSet.isRunning()) {
                this.mPlayToAnimatorSet.cancel();
                this.mPlayToAnimatorSet = null;
            }
            this.mPlayToAnimatorSet = new AnimatorSet();
            this.mPlayToAnimatorSet.play(animIn1).with(animIn2);
            this.mPlayToAnimatorSet.setDuration(100);
            this.mPlayToAnimatorSet.start();
        }
    }

    public void playBy(int startPosition, int nextPosition, PageSetEntity pageSetEntity) {
        if (checkPageSetEntity(pageSetEntity)) {
            updateIndicatorCount(pageSetEntity.getPageCount());
            boolean isShowInAnimOnly = false;
            if (startPosition < 0 || nextPosition < 0 || nextPosition == startPosition) {
                nextPosition = 0;
                startPosition = 0;
            }
            if (startPosition < 0) {
                isShowInAnimOnly = true;
                nextPosition = 0;
                startPosition = 0;
            }
            final Object imageViewStrat = (ImageView) this.mImageViews.get(startPosition);
            final Object imageViewNext = (ImageView) this.mImageViews.get(nextPosition);
            ObjectAnimator anim1 = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f, 0.25f);
            ObjectAnimator anim2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f, 0.25f);
            if (this.mPlayByOutAnimatorSet != null && this.mPlayByOutAnimatorSet.isRunning()) {
                this.mPlayByOutAnimatorSet.cancel();
                this.mPlayByOutAnimatorSet = null;
            }
            this.mPlayByOutAnimatorSet = new AnimatorSet();
            this.mPlayByOutAnimatorSet.play(anim1).with(anim2);
            this.mPlayByOutAnimatorSet.setDuration(100);
            ObjectAnimator animIn1 = ObjectAnimator.ofFloat(imageViewNext, "scaleX", 0.25f, 1.0f);
            ObjectAnimator animIn2 = ObjectAnimator.ofFloat(imageViewNext, "scaleY", 0.25f, 1.0f);
            if (this.mPlayByInAnimatorSet != null && this.mPlayByInAnimatorSet.isRunning()) {
                this.mPlayByInAnimatorSet.cancel();
                this.mPlayByInAnimatorSet = null;
            }
            this.mPlayByInAnimatorSet = new AnimatorSet();
            this.mPlayByInAnimatorSet.play(animIn1).with(animIn2);
            this.mPlayByInAnimatorSet.setDuration(100);
            if (isShowInAnimOnly) {
                this.mPlayByInAnimatorSet.start();
                return;
            }
            anim1.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                }

                public void onAnimationEnd(Animator animation) {
                    imageViewStrat.setImageBitmap(EmoticonsIndicatorView.this.mBmpNomal);
                    ObjectAnimator animFil1l = ObjectAnimator.ofFloat(imageViewStrat, "scaleX", 1.0f);
                    ObjectAnimator animFill2 = ObjectAnimator.ofFloat(imageViewStrat, "scaleY", 1.0f);
                    AnimatorSet mFillAnimatorSet = new AnimatorSet();
                    mFillAnimatorSet.play(animFil1l).with(animFill2);
                    mFillAnimatorSet.start();
                    imageViewNext.setImageBitmap(EmoticonsIndicatorView.this.mBmpSelect);
                    EmoticonsIndicatorView.this.mPlayByInAnimatorSet.start();
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            this.mPlayByOutAnimatorSet.start();
        }
    }

    protected boolean checkPageSetEntity(PageSetEntity pageSetEntity) {
        if (pageSetEntity == null || !pageSetEntity.isShowIndicator()) {
            setVisibility(8);
            return false;
        }
        setVisibility(0);
        return true;
    }

    protected void updateIndicatorCount(int count) {
        int i;
        if (this.mImageViews == null) {
            this.mImageViews = new ArrayList();
        }
        if (count > this.mImageViews.size()) {
            i = this.mImageViews.size();
            while (i < count) {
                ImageView imageView = new ImageView(this.mContext);
                imageView.setImageBitmap(i == 0 ? this.mBmpSelect : this.mBmpNomal);
                addView(imageView, this.mLeftLayoutParams);
                this.mImageViews.add(imageView);
                i++;
            }
        }
        for (i = 0; i < this.mImageViews.size(); i++) {
            if (i >= count) {
                ((ImageView) this.mImageViews.get(i)).setVisibility(8);
            } else {
                ((ImageView) this.mImageViews.get(i)).setVisibility(0);
            }
        }
    }
}
