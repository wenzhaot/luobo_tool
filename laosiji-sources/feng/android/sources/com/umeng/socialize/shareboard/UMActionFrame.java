package com.umeng.socialize.shareboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.umeng.socialize.shareboard.widgets.SocializeViewPager;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.SHAREBOARD;
import java.util.List;

class UMActionFrame extends LinearLayout {
    private ShareBoardConfig mConfig;
    private OnDismissListener mDismissListener;

    public UMActionFrame(Context context) {
        super(context);
    }

    public UMActionFrame(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @TargetApi(11)
    public UMActionFrame(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public UMActionFrame(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }

    public void setSnsPlatformData(List<SnsPlatform> list) {
        setSnsPlatformData(list, new ShareBoardConfig());
    }

    public void setSnsPlatformData(List<SnsPlatform> list, ShareBoardConfig shareBoardConfig) {
        if (shareBoardConfig == null) {
            this.mConfig = new ShareBoardConfig();
        } else {
            this.mConfig = shareBoardConfig;
        }
        init(list);
    }

    private void init(List<SnsPlatform> list) {
        setBackgroundColor(Color.argb(50, 0, 0, 0));
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(100);
        setAnimation(alphaAnimation);
        setOrientation(1);
        if (this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM) {
            setGravity(80);
        } else if (this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER) {
            setGravity(17);
            int dip2px = dip2px(36.0f);
            setPadding(dip2px, 0, dip2px, 0);
        }
        setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (UMActionFrame.this.mDismissListener != null) {
                    UMActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        View createShareboardLayout = createShareboardLayout(list);
        if (createShareboardLayout != null) {
            createShareboardLayout.setClickable(true);
            addView(createShareboardLayout);
        }
    }

    private View createShareboardLayout(List<SnsPlatform> list) {
        View view = null;
        View linearLayout = new LinearLayout(getContext());
        linearLayout.setBackgroundColor(this.mConfig.mShareboardBgColor);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        if (this.mConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_CENTER && this.mConfig.mTopMargin != 0) {
            layoutParams.topMargin = this.mConfig.mTopMargin;
        }
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(layoutParams);
        if (this.mConfig.mTitleVisibility) {
            linearLayout.addView(createShareTitle());
        }
        int calculateMenuHeightInDp = this.mConfig.calculateMenuHeightInDp(list.size());
        ViewPager createViewPagerInstance = createViewPagerInstance();
        if (createViewPagerInstance != null) {
            SocializeMenuPagerAdapter socializeMenuPagerAdapter = new SocializeMenuPagerAdapter(getContext(), this.mConfig);
            socializeMenuPagerAdapter.setData(list);
            settingMenuLayout(createViewPagerInstance, calculateMenuHeightInDp);
            linearLayout.addView(createViewPagerInstance);
            createViewPagerInstance.setAdapter(socializeMenuPagerAdapter);
            if (this.mConfig.mIndicatorVisibility) {
                view = createIndicatorView();
            }
            if (view != null) {
                view.setPageCount(socializeMenuPagerAdapter.getCount());
                linearLayout.addView(view);
            }
            AnonymousClass2 anonymousClass2 = new OnPageChangeListener() {
                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    if (view != null) {
                        view.setSelectedPosition(i);
                    }
                }

                public void onPageScrollStateChanged(int i) {
                }
            };
            if (verifyMethodExists()) {
                createViewPagerInstance.addOnPageChangeListener(anonymousClass2);
            } else {
                createViewPagerInstance.setOnPageChangeListener(anonymousClass2);
            }
        } else {
            SocializeViewPager createSocializeViewPagerInstance = createSocializeViewPagerInstance();
            if (createSocializeViewPagerInstance == null) {
                return null;
            }
            SocializeMenuAdapter socializeMenuAdapter = new SocializeMenuAdapter(getContext(), this.mConfig);
            socializeMenuAdapter.setData(list);
            settingMenuLayout(createSocializeViewPagerInstance, calculateMenuHeightInDp);
            linearLayout.addView(createSocializeViewPagerInstance);
            createSocializeViewPagerInstance.setAdapter(socializeMenuAdapter);
            if (this.mConfig.mIndicatorVisibility) {
                view = createIndicatorView();
            }
            if (view != null) {
                view.setPageCount(socializeMenuAdapter.getCount());
                linearLayout.addView(view);
            }
            createSocializeViewPagerInstance.addOnPageChangeListener(new SocializeViewPager.OnPageChangeListener() {
                public void onPageScrolled(int i, float f, int i2) {
                }

                public void onPageSelected(int i) {
                    if (view != null) {
                        view.setSelectedPosition(i);
                    }
                }

                public void onPageScrollStateChanged(int i) {
                }
            });
        }
        if (this.mConfig.mCancelBtnVisibility) {
            linearLayout.addView(createCancelBtn());
        }
        return linearLayout;
    }

    private View createShareTitle() {
        View textView = new TextView(getContext());
        textView.setText(this.mConfig.mTitleText);
        textView.setTextColor(this.mConfig.mTitleTextColor);
        textView.setTextSize(16.0f);
        textView.setGravity(17);
        textView.setMaxLines(1);
        textView.setEllipsize(TruncateAt.END);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = dip2px(20.0f);
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    private void settingMenuLayout(View view, int i) {
        int dip2px = dip2px(20.0f);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, dip2px((float) i));
        layoutParams.topMargin = dip2px;
        int dip2px2 = dip2px(10.0f);
        layoutParams.rightMargin = dip2px2;
        layoutParams.leftMargin = dip2px2;
        view.setLayoutParams(layoutParams);
        view.setPadding(0, 0, 0, dip2px);
    }

    public IndicatorView createIndicatorView() {
        int dip2px = dip2px(20.0f);
        IndicatorView indicatorView = new IndicatorView(getContext());
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.bottomMargin = dip2px;
        indicatorView.setLayoutParams(layoutParams);
        indicatorView.setIndicatorColor(this.mConfig.mIndicatorNormalColor, this.mConfig.mIndicatorSelectedColor);
        indicatorView.setIndicator(3, 5);
        return indicatorView;
    }

    public View createCancelBtn() {
        View textView = new TextView(getContext());
        textView.setText(this.mConfig.mCancelBtnText);
        textView.setTextColor(this.mConfig.mCancelBtnColor);
        textView.setClickable(true);
        textView.setTextSize(15.0f);
        textView.setGravity(17);
        if (this.mConfig.mCancelBtnBgPressedColor == 0) {
            textView.setBackgroundColor(this.mConfig.mCancelBtnBgColor);
        } else if (VERSION.SDK_INT >= 16) {
            textView.setBackground(getBtnBg());
        } else {
            textView.setBackgroundDrawable(getBtnBg());
        }
        textView.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                if (UMActionFrame.this.mDismissListener != null) {
                    UMActionFrame.this.mDismissListener.onDismiss();
                }
            }
        });
        textView.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(50.0f)));
        return textView;
    }

    private StateListDrawable getBtnBg() {
        Drawable colorDrawable = new ColorDrawable(this.mConfig.mCancelBtnBgColor);
        Drawable colorDrawable2 = new ColorDrawable(this.mConfig.mCancelBtnBgPressedColor);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, colorDrawable2);
        stateListDrawable.addState(new int[0], colorDrawable);
        return stateListDrawable;
    }

    private int dip2px(float f) {
        return (int) ((getContext().getResources().getDisplayMetrics().density * f) + 0.5f);
    }

    void setDismissListener(OnDismissListener onDismissListener) {
        this.mDismissListener = onDismissListener;
    }

    private ViewPager createViewPagerInstance() {
        try {
            return (ViewPager) Class.forName("android.support.v4.view.ViewPager").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
        } catch (Throwable e) {
            SLog.error(e);
            return null;
        }
    }

    private boolean verifyMethodExists() {
        try {
            if (Class.forName("android.support.v4.view.ViewPager").getMethod("addOnPageChangeListener", new Class[]{OnPageChangeListener.class}) != null) {
                return true;
            }
        } catch (Throwable e) {
            SLog.error(e);
        }
        return false;
    }

    private SocializeViewPager createSocializeViewPagerInstance() {
        try {
            return (SocializeViewPager) Class.forName("com.umeng.socialize.shareboard.widgets.SocializeViewPager").getConstructor(new Class[]{Context.class}).newInstance(new Object[]{getContext()});
        } catch (Throwable e) {
            SLog.error(SHAREBOARD.NULLJAR, e);
            return null;
        }
    }
}
