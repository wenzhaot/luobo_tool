package com.feng.car.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.R;
import com.feng.car.databinding.ActivityAtMeBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.fragment.AtCommentFragment;
import com.feng.car.fragment.AtMicroFragment;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.github.jdsjlzx.listener.OnSingleClickListener;

public class AtMeActivity extends BaseActivity<ActivityAtMeBinding> {
    public static final int AT_COMMENT_SEL_TYPE = 1;
    public static final int AT_MICRO_SEL_TYPE = 0;
    private AtCommentFragment mCommentFragment;
    private int mIndex = 0;
    private int mMarg14;
    private AtMicroFragment mMicroFragment;
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private PopupWindow mPopupWindow;
    private int mType = 0;
    private View mViewPup;

    public int setBaseContentView() {
        return R.layout.activity_at_me;
    }

    public void initView() {
        this.mMicroFragment = new AtMicroFragment();
        this.mCommentFragment = new AtCommentFragment();
        this.mType = getIntent().getIntExtra("feng_type", 0);
        initNormalTitleBar("");
        this.mMarg14 = this.mResources.getDimensionPixelSize(R.dimen.default_14PX);
        final int windowWidth = this.mResources.getDisplayMetrics().widthPixels;
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mMiddleTitleBinding.tvTitle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (AtMeActivity.this.mPopupWindow == null) {
                    AtMeActivity.this.showWindow(AtMeActivity.this.mRootBinding.titleLine.getRoot(), AtMeActivity.this.mViewPup, windowWidth, (windowWidth * 5) / 12, 0, AtMeActivity.this.mMarg14, true);
                } else if (AtMeActivity.this.mPopupWindow.isShowing()) {
                    AtMeActivity.this.mPopupWindow.dismiss();
                } else {
                    AtMeActivity.this.showWindow(AtMeActivity.this.mRootBinding.titleLine.getRoot(), AtMeActivity.this.mViewPup, windowWidth, (windowWidth * 5) / 12, 0, AtMeActivity.this.mMarg14, true);
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                if (AtMeActivity.this.mIndex == 0) {
                    AtMeActivity.this.mMicroFragment.backToTop();
                } else if (AtMeActivity.this.mIndex == 1) {
                    AtMeActivity.this.mCommentFragment.backToTop();
                }
            }
        }));
        if (this.mType == 0) {
            this.mIndex = 0;
            this.mMiddleTitleBinding.tvTitle.setText(R.string.at_micro);
            getSupportFragmentManager().beginTransaction().add((int) R.id.ll_at_fragment_container, this.mMicroFragment).show(this.mMicroFragment).commitAllowingStateLoss();
        } else if (this.mType == 1) {
            this.mIndex = 1;
            this.mMiddleTitleBinding.tvTitle.setText(R.string.at_comment);
            getSupportFragmentManager().beginTransaction().add((int) R.id.ll_at_fragment_container, this.mCommentFragment).show(this.mCommentFragment).commitAllowingStateLoss();
        }
        initPup();
    }

    private void initPup() {
        this.mViewPup = LayoutInflater.from(this).inflate(R.layout.message_menu, null);
        RadioGroup radioGroup = (RadioGroup) this.mViewPup.findViewById(R.id.rg_comment);
        for (int i = 0; i < 2; i++) {
            RadioButton radioButton = new RadioButton(this);
            if (i == 0) {
                radioButton.setText(R.string.at_micro);
            } else if (i == 1) {
                radioButton.setText(R.string.at_comment);
            }
            radioButton.setGravity(17);
            radioButton.setTextColor(this.mResources.getColorStateList(R.color.selector_black87_checked_ffffff));
            radioButton.setTextAppearance(this, R.style.textsize_14);
            radioButton.setButtonDrawable(ContextCompat.getDrawable(this, 17170445));
            radioButton.setBackgroundResource(R.drawable.radio_menu_selector);
            radioButton.setLayoutParams(new LayoutParams(-1, -2));
            radioButton.setPadding(0, this.mMarg14, 0, this.mMarg14);
            radioButton.setId(i);
            if (i == 0 && this.mType == 0) {
                radioButton.setChecked(true);
            } else if (i == 1 && this.mType == 1) {
                radioButton.setChecked(true);
            }
            radioButton.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    AtMeActivity.this.hideWindow();
                }
            });
            radioGroup.addView(radioButton);
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int nResTitle = R.string.at_micro;
                    FragmentTransaction trx = AtMeActivity.this.getSupportFragmentManager().beginTransaction();
                    switch (checkedId) {
                        case 0:
                            if (AtMeActivity.this.mIndex != 0) {
                                AtMeActivity.this.mIndex = 0;
                                nResTitle = R.string.at_micro;
                                if (!AtMeActivity.this.mMicroFragment.isAdded()) {
                                    trx.add((int) R.id.ll_at_fragment_container, AtMeActivity.this.mMicroFragment);
                                }
                                if (AtMeActivity.this.mCommentFragment.isAdded()) {
                                    trx.hide(AtMeActivity.this.mCommentFragment);
                                }
                                trx.show(AtMeActivity.this.mMicroFragment).commitAllowingStateLoss();
                                break;
                            }
                            return;
                        case 1:
                            if (AtMeActivity.this.mIndex != 1) {
                                AtMeActivity.this.mIndex = 1;
                                nResTitle = R.string.at_comment;
                                if (!AtMeActivity.this.mCommentFragment.isAdded()) {
                                    trx.add((int) R.id.ll_at_fragment_container, AtMeActivity.this.mCommentFragment);
                                }
                                if (AtMeActivity.this.mMicroFragment.isAdded()) {
                                    trx.hide(AtMeActivity.this.mMicroFragment);
                                }
                                trx.show(AtMeActivity.this.mCommentFragment).commitAllowingStateLoss();
                                break;
                            }
                            return;
                    }
                    AtMeActivity.this.mMiddleTitleBinding.tvTitle.setText(nResTitle);
                    AtMeActivity.this.hideWindow();
                }
            });
        }
    }

    private void showWindow(View parent, View view, int windowWidth, int parentWidth, int offX, int offY, boolean offsetMode) {
        if (offsetMode) {
            this.mPopupWindow = new PopupWindow(view, windowWidth, -2, true);
            this.mPopupWindow.setWidth(parentWidth);
        } else {
            this.mPopupWindow = new PopupWindow(view, -1, -2, true);
        }
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        this.mPopupWindow.showAsDropDown(parent, ((windowWidth - parentWidth) / 2) - offX, -offY);
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                AtMeActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
            }
        });
    }

    private void hideWindow() {
        if (this.mPopupWindow != null) {
            this.mPopupWindow.dismiss();
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
