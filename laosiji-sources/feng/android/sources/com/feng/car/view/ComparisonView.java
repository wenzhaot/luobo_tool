package com.feng.car.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.activity.CarModleComparisonActivity;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.UmengConstans;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.MobclickAgent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ComparisonView extends RelativeLayout {
    private boolean isScaleAnimationRunning = false;
    private int m10;
    private int m32;
    private Context mContext;
    private ImageView mIvVs;
    private TextView mTvPkNum;

    public ComparisonView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public ComparisonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
    }

    public ComparisonView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init() {
        this.m10 = this.mContext.getResources().getDimensionPixelSize(2131296268);
        this.m32 = this.mContext.getResources().getDimensionPixelSize(2131296512);
        this.mIvVs = new ImageView(this.mContext);
        this.mIvVs.setImageResource(2130837781);
        this.mIvVs.setId(2131623965);
        LayoutParams ivParams = new LayoutParams(-2, -2);
        ivParams.addRule(15);
        this.mIvVs.setPadding(this.m32, this.m10, this.m32, this.m10);
        this.mIvVs.setScaleType(ScaleType.CENTER);
        addView(this.mIvVs, ivParams);
        this.mTvPkNum = new TextView(this.mContext);
        this.mTvPkNum.setId(2131623966);
        this.mTvPkNum.setGravity(17);
        this.mTvPkNum.setTextColor(ContextCompat.getColor(this.mContext, 2131558558));
        this.mTvPkNum.setBackgroundResource(2130838433);
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(6, 2131623965);
        layoutParams.addRule(7, 2131623965);
        layoutParams.setMargins(0, 0, this.m10, 0);
        this.mTvPkNum.setTextAppearance(this.mContext, 2131362224);
        addView(this.mTvPkNum, layoutParams);
        int num = FengApplication.getInstance().getSparkDB().getCarComparisonIDList().size();
        if (num == 0) {
            this.mTvPkNum.setVisibility(4);
        } else {
            this.mTvPkNum.setVisibility(0);
            this.mTvPkNum.setText(num + "");
        }
        setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ComparisonView.this.mContext.startActivity(new Intent(ComparisonView.this.mContext, CarModleComparisonActivity.class));
                MobclickAgent.onEvent(ComparisonView.this.mContext, UmengConstans.CAR_VS);
            }
        });
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        refreshComparisomnNum();
    }

    private void refreshComparisomnNum() {
        int mLastNum = 0;
        if (!StringUtil.isEmpty(this.mTvPkNum.getText().toString())) {
            mLastNum = Integer.parseInt(this.mTvPkNum.getText().toString());
        }
        int num = FengApplication.getInstance().getSparkDB().getCarComparisonIDList().size();
        if (num == 0) {
            this.mTvPkNum.setVisibility(8);
            this.mTvPkNum.setText(PushConstants.PUSH_TYPE_NOTIFY);
            return;
        }
        this.mTvPkNum.setVisibility(0);
        this.mTvPkNum.setText(num + "");
        if (num > mLastNum) {
            scaleAnimationStart();
        }
    }

    public void setVsImageResource(int resourceID) {
        this.mIvVs.setImageResource(resourceID);
    }

    private void scaleAnimationStart() {
        if (!this.isScaleAnimationRunning) {
            this.isScaleAnimationRunning = true;
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f);
            scaleAnimation.setDuration(75);
            scaleAnimation.setFillAfter(true);
            scaleAnimation.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    ComparisonView.this.mTvPkNum.clearAnimation();
                    ComparisonView.this.scaleAnimationEnd();
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            this.mTvPkNum.startAnimation(scaleAnimation);
        }
    }

    private void scaleAnimationEnd() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.3f, 1.0f, 1.3f, 1.0f);
        scaleAnimation.setDuration(75);
        scaleAnimation.setFillAfter(true);
        scaleAnimation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ComparisonView.this.isScaleAnimationRunning = false;
                ComparisonView.this.mTvPkNum.clearAnimation();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        });
        this.mTvPkNum.startAnimation(scaleAnimation);
    }
}
