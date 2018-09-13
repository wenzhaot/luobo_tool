package com.feng.car.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout.LayoutParams;
import android.support.constraint.ConstraintSet;
import android.support.graphics.drawable.PathInterpolatorCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView.OnEditorActionListener;
import anet.channel.strategy.dispatch.DispatchConstants;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityBaseLayoutBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.model.LogGatherInfo;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.event.UserLoginEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.RecommendRecordUtil;
import com.feng.car.video.player.JCMediaManager;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SwipeBackLayout;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.socialize.UMShareAPI;
import java.util.ArrayList;
import java.util.List;
import me.leefeng.promptlibrary.PromptDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseActivity<VB extends ViewDataBinding> extends AppCompatActivity implements OnClickListener {
    final int INTERVAL_TIME = 1000;
    public boolean hasAttachSwip = false;
    public boolean isOpenSwipe = true;
    public String mAddress = "";
    public VB mBaseBinding;
    public String mCity = "";
    public int mCityId = 131;
    private long mFirstTime = 0;
    private boolean mHasInPageMap = false;
    public LayoutInflater mInflater;
    protected Dialog mLaoSiJiDialog;
    private int mLastViewId = 0;
    public LogGatherInfo mLogGatherInfo = new LogGatherInfo();
    private boolean mLoginOut = false;
    private boolean mLoginSucces = false;
    protected ProgressDialog mProgressDialog;
    public Resources mResources;
    public ActivityBaseLayoutBinding mRootBinding;
    public View mRootView;
    public SwipeBackLayout mSwipeBackLayout;
    private PromptDialog mToastDialog;

    public abstract void initView();

    public abstract int setBaseContentView();

    public int getTitleBarBottomY() {
        int[] location = new int[2];
        if (this.mRootBinding.titleLine == null) {
            return 0;
        }
        this.mRootBinding.titleLine.getRoot().getLocationOnScreen(location);
        return location[1] + this.mRootBinding.titleLine.getRoot().getHeight();
    }

    public void scrollWhenScreenOff() {
        if (this.mSwipeBackLayout != null) {
            this.mSwipeBackLayout.scrollRightOrOrigin();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);
        requestWindowFeature(10);
        super.onCreate(savedInstanceState);
        getLocalIntentData();
        if (LogGatherReadUtil.getInstance().getPageMap().containsKey(getLocalClassName())) {
            this.mHasInPageMap = true;
            this.mLogGatherInfo.setBasePage(getLogCurrentPage());
        }
        ActivityManager.getInstance().addActivity(this);
        EventBus.getDefault().register(this);
        this.mResources = getResources();
        if ((this instanceof VideoFinalPageActivity) || (this instanceof WatchVideoActivity)) {
            JCMediaManager.instance().setIsNeedVolume(true);
        } else {
            JCMediaManager.instance().setIsNeedVolume(false);
        }
        initRootView();
        initView();
        this.mSwipeBackLayout = (SwipeBackLayout) LayoutInflater.from(this).inflate(R.layout.swipe_base, null);
        if (!SharedUtil.getString(this, "TOKEN_KEY").trim().equals("")) {
            PushAgent.getInstance(this).onAppStart();
        }
    }

    public void getLocalIntentData() {
    }

    public void finish() {
        super.finish();
        hideToastDialog();
        if ((this instanceof SplashActivity) || (this instanceof HomeActivity) || (this instanceof LoginActivity)) {
            overridePendingTransition(0, 0);
        } else if ((this instanceof MoreConditionActivity) || (this instanceof BrandConditionActivity) || (this instanceof PriceConditionActivity) || (this instanceof LevelConditionActivity) || (this instanceof PricesSeriesChartActivity) || (this instanceof PricesModelChartActivity) || (this instanceof SelectImageVideoActivity)) {
            overridePendingTransition(R.anim.in_righttoleft, R.anim.bottom_out_anim);
        } else if ((this instanceof SearchNewActivity) || (this instanceof PersonalSearchActivity) || (this instanceof SendCommentActivity)) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else if (!(this instanceof DistributorMapActivity)) {
            overridePendingTransition(R.anim.in_righttoleft, R.anim.out_lefttoright);
        }
        ActivityManager.getInstance().removeActivity(this);
    }

    public void initRootView() {
        this.mInflater = LayoutInflater.from(this);
        this.mRootBinding = ActivityBaseLayoutBinding.inflate(this.mInflater);
        this.mRootView = this.mRootBinding.getRoot();
        setContentView(this.mRootBinding.getRoot());
        this.mBaseBinding = DataBindingUtil.inflate(this.mInflater, setBaseContentView(), null, false);
        LayoutParams contentParams = new LayoutParams(0, 0);
        contentParams.topToBottom = R.id.titleLine;
        this.mBaseBinding.getRoot().setLayoutParams(contentParams);
        this.mRootBinding.rootView.addView(this.mBaseBinding.getRoot());
        ConstraintSet set = new ConstraintSet();
        set.clone(this.mRootBinding.rootView);
        set.connect(this.mBaseBinding.getRoot().getId(), 1, R.id.rootView, 1, 0);
        set.connect(this.mBaseBinding.getRoot().getId(), 2, R.id.rootView, 2, 0);
        set.connect(this.mBaseBinding.getRoot().getId(), 3, R.id.titleLine, 4, 0);
        set.connect(this.mBaseBinding.getRoot().getId(), 4, R.id.rootView, 4, 0);
        set.applyTo(this.mRootBinding.rootView);
        this.mRootBinding.emptyView.bringToFront();
        this.mRootBinding.audioSuspensionView.bringToFront();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserLoginEvent event) {
        if (event.mIsLogin) {
            this.mLoginSucces = true;
            this.mLoginOut = false;
            return;
        }
        this.mLoginOut = true;
        this.mLoginSucces = false;
    }

    public void loginSuccess() {
    }

    public void loginOut() {
    }

    protected void onStart() {
        super.onStart();
        if (this instanceof WebActivity) {
            this.mRootBinding.getRoot().setFitsSystemWindows(true);
        }
    }

    public void closeSwip() {
        this.isOpenSwipe = false;
    }

    public void isOpenSwip() {
        if (this.isOpenSwipe && !this.hasAttachSwip) {
            this.mSwipeBackLayout.attachToActivity(this);
            this.hasAttachSwip = true;
        }
    }

    protected void onResume() {
        super.onResume();
        LogGatherReadUtil.getInstance().setScreenOrientation(this);
        if (this.mHasInPageMap) {
            this.mLogGatherInfo.changeRefererPage();
            this.mLogGatherInfo.addPageInTime();
        }
        MobclickAgent.onResume(this);
        isOpenSwip();
        if (this.mLoginSucces) {
            loginSuccess();
            this.mLoginSucces = false;
        }
        if (this.mLoginOut) {
            loginOut();
            this.mLoginOut = false;
        }
        if (getAllowShowAudioBtn()) {
            this.mRootBinding.audioSuspensionView.onResume();
        }
    }

    protected void onPause() {
        super.onPause();
        if (this.mHasInPageMap) {
            this.mLogGatherInfo.addPageOutTime();
        }
        MobclickAgent.onPause(this);
        RecommendRecordUtil.getInstance().addRecommendLog();
        if (getAllowShowAudioBtn()) {
            this.mRootBinding.audioSuspensionView.onPause();
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void hideEmptyView() {
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(0);
            this.mRootBinding.emptyView.setVisibility(8);
        }
    }

    public void showEmptyView(int textID) {
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.emptyView.hideEmptyImage();
            this.mRootBinding.emptyView.hideEmptyButton();
            this.mRootBinding.emptyView.setEmptyText(textID);
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void showEmptyView(int textID, int drawableID) {
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.emptyView.setEmptyImage(drawableID);
            this.mRootBinding.emptyView.setEmptyText(textID);
            this.mRootBinding.emptyView.hideEmptyButton();
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void showEmptyView(int textID, int btnTextID, OnSingleClickListener listener) {
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.emptyView.hideEmptyImage();
            this.mRootBinding.emptyView.setEmptyText(textID);
            this.mRootBinding.emptyView.setButtonListener(btnTextID, listener);
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void showEmptyView(int textID, int drawableID, int btnTextID, OnSingleClickListener listener) {
        if (this.mRootBinding.emptyView != null) {
            if (this.mRootBinding.titleLine != null) {
                this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            }
            this.mRootBinding.emptyView.setEmptyImage(drawableID);
            this.mRootBinding.emptyView.setEmptyText(textID);
            this.mRootBinding.emptyView.setButtonListener(btnTextID, listener);
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void showEmptyView(int drawableID, OnSingleClickListener listener) {
        if (this.mRootBinding.emptyView != null) {
            if (this.mRootBinding.titleLine != null) {
                this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            }
            this.mRootBinding.emptyView.hideEmptyText();
            this.mRootBinding.emptyView.setEmptyImage(drawableID);
            this.mRootBinding.emptyView.setButtonListener(listener);
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void initTitleBarRightText(int Stringid, OnClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.titleLine.tvRightTextTwo.setVisibility(8);
            this.mRootBinding.titleLine.tvRightText.setText(Stringid);
            this.mRootBinding.titleLine.tvRightText.setOnClickListener(l);
        }
    }

    public void initTitleBarRightTextGold(int Stringid, OnClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setTextColor(this.mResources.getColorStateList(R.color.selector_fb6c06_pressed_ffc296));
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.titleLine.tvRightTextTwo.setVisibility(8);
            this.mRootBinding.titleLine.tvRightText.setText(Stringid);
            this.mRootBinding.titleLine.tvRightText.setOnClickListener(l);
        }
    }

    public void initTitleBarRightTextWithBg(int StringId, OnClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            int btnWidth = this.mResources.getDimensionPixelSize(R.dimen.default_128PX);
            int btnHeight = this.mResources.getDimensionPixelSize(R.dimen.default_52PX);
            int margin = this.mResources.getDimensionPixelSize(R.dimen.default_32PX);
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) this.mRootBinding.titleLine.tvRightText.getLayoutParams();
            p.width = -2;
            p.height = btnHeight;
            p.setMargins(0, 0, margin, 0);
            this.mRootBinding.titleLine.tvRightText.setMinWidth(btnWidth);
            this.mRootBinding.titleLine.tvRightText.setPadding(margin / 4, 0, margin / 4, 0);
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.titleLine.tvRightText.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setLayoutParams(p);
            this.mRootBinding.titleLine.tvRightText.setText(StringId);
            this.mRootBinding.titleLine.tvRightText.setOnClickListener(l);
            this.mRootBinding.titleLine.tvRightText.setTextColor(this.mResources.getColorStateList(R.color.color_ffffff_unsel_white50));
            this.mRootBinding.titleLine.tvRightText.setBackgroundResource(R.drawable.color_selector_494a5d_pressed_313242_42px);
        }
    }

    public void initTitleBarRightPost(int res, OnSingleClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setVisibility(8);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setOnClickListener(l);
            this.mRootBinding.titleLine.ivRightImage.setImageResource(res);
        }
    }

    public void initTitleBarTitleWithRightImg(String title, int res, OnSingleClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.tvRightText.setSelected(true);
            initTitleBarRightTextWithBg(res, l);
        }
    }

    public void initTitleBarTitleWithRightImg2(String title, int res, OnSingleClickListener l) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setImageResource(res);
            this.mRootBinding.titleLine.ivRightImage.setOnClickListener(l);
        }
    }

    public void setTitleBarCenterText(String title) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.title.setText(title);
        }
    }

    public void initTitleBarRightText(int resFirst, int resSecond, OnClickListener listenerFirst, OnClickListener listenerSecond) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.titleLine.ivRightImageTwo.setVisibility(8);
            this.mRootBinding.titleLine.tvRightText.setVisibility(0);
            this.mRootBinding.titleLine.tvRightTextTwo.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setText(resFirst);
            this.mRootBinding.titleLine.tvRightText.setOnClickListener(listenerFirst);
            this.mRootBinding.titleLine.tvRightTextTwo.setText(resSecond);
            this.mRootBinding.titleLine.tvRightTextTwo.setOnClickListener(listenerSecond);
        }
    }

    public void initTitleBarRightImage(int resFirst, int resSecond, OnClickListener listenerFirst, OnClickListener listenerSecond) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImageTwo.setVisibility(0);
            this.mRootBinding.titleLine.tvRightText.setVisibility(8);
            this.mRootBinding.titleLine.tvRightTextTwo.setVisibility(8);
            this.mRootBinding.titleLine.ivRightImage.setImageResource(resFirst);
            this.mRootBinding.titleLine.ivRightImage.setOnClickListener(listenerFirst);
            this.mRootBinding.titleLine.ivRightImageTwo.setImageResource(resSecond);
            this.mRootBinding.titleLine.ivRightImageTwo.setOnClickListener(listenerSecond);
        }
    }

    public void initTitleBarRightPk() {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
            this.mRootBinding.titleLine.rightCvPk.setVisibility(0);
        }
    }

    public int[] getTitleBarPKLocation() {
        int[] location = new int[2];
        if (this.mRootBinding.titleLine.rightCvPk != null) {
            this.mRootBinding.titleLine.rightCvPk.findViewById(R.id.id_pk_tv_vs_num).getLocationOnScreen(location);
        }
        return location;
    }

    public void initTitleBarRightPkAdd(int twoRes, OnClickListener listenerSecond) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.rightCvPk.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(4);
            this.mRootBinding.titleLine.ivRightImageTwo.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImageTwo.setImageResource(twoRes);
            this.mRootBinding.titleLine.ivRightImage.setImageResource(twoRes);
            this.mRootBinding.titleLine.ivRightImageTwo.setOnClickListener(listenerSecond);
        }
    }

    public void hideDefaultTitleBar() {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.getRoot().setVisibility(8);
        }
    }

    public void showDefaultTitleBar() {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.getRoot().setVisibility(0);
        }
    }

    public void initNormalTitleBar(int title) {
        if (this.mRootBinding.titleLine != null) {
            showDefaultTitleBar();
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void initTitleBarMsgCount() {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivMessage.setVisibility(0);
            this.mRootBinding.titleLine.tvMessageCommentNum.setVisibility(8);
            this.mRootBinding.titleLine.ivMessage.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.startActivity(new Intent(BaseActivity.this, MessageActivity.class));
                }
            });
        }
    }

    public void changeLeftIcon(int res) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setImageResource(res);
        }
    }

    public void initNormalTitleBar(String title) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.getRoot().setVisibility(0);
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void initNormalTitleBar(int title, OnSingleClickListener leftListener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(leftListener);
        }
    }

    public void initWebTitleBar(String title, OnSingleClickListener leftListener, OnSingleClickListener leftCloseListener, OnSingleClickListener rightListener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.ivTitleLeftClose.setVisibility(0);
            this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setVisibility(0);
            this.mRootBinding.titleLine.ivRightImage.setImageResource(R.drawable.icon_more_bl_selector);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(leftListener);
            this.mRootBinding.titleLine.ivTitleLeftClose.setOnClickListener(leftCloseListener);
            this.mRootBinding.titleLine.ivRightImage.setOnClickListener(rightListener);
        }
    }

    public void initNormalTitleBar(String title, OnSingleClickListener leftListener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(leftListener);
        }
    }

    public void initNormalLeftTitleBar(String leftText) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.tvTitleLeft.setText(leftText);
            this.mRootBinding.titleLine.tvTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void initNormalLeftTitleBar(String leftText, OnSingleClickListener listener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.tvTitleLeft.setText(leftText);
            this.mRootBinding.titleLine.tvTitleLeft.setOnClickListener(listener);
        }
    }

    public void initNormalLeftTitleBar(int leftText, int title, OnSingleClickListener listener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.tvTitleLeft.setVisibility(0);
            this.mRootBinding.titleLine.title.setVisibility(0);
            this.mRootBinding.titleLine.title.setText(title);
            this.mRootBinding.titleLine.tvTitleLeft.setText(leftText);
            this.mRootBinding.titleLine.tvTitleLeft.setOnClickListener(listener);
        }
    }

    public void initCircleSearchTitleBar(int searchHint, OnSingleClickListener searchListener) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.tvSearch.setVisibility(0);
            this.mRootBinding.titleLine.tvSearch.setText(searchHint);
            this.mRootBinding.titleLine.tvSearch.setOnClickListener(searchListener);
            this.mRootBinding.titleLine.titlebarMiddleParent.setVisibility(8);
            this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void initSearchTitleBar(SearchKey searchKey, int searchHint, OnEditorActionListener actionListener, TextWatcher textWatcher) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.setKey(searchKey);
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.titlebarMiddleParent.setVisibility(8);
            this.mRootBinding.titleLine.rlSearchBar.setVisibility(0);
            this.mRootBinding.titleLine.etSearchKey.setHint(searchHint);
            this.mRootBinding.titleLine.etSearchKey.setOnEditorActionListener(actionListener);
            this.mRootBinding.titleLine.etSearchKey.addTextChangedListener(textWatcher);
            this.mRootBinding.titleLine.tvCancel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void initSearchTitleBar(SearchKey searchKey, int searchHint, OnSingleClickListener listener, OnEditorActionListener actionListener, TextWatcher textWatcher) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.setKey(searchKey);
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.titlebarMiddleParent.setVisibility(8);
            this.mRootBinding.titleLine.rlSearchBar.setVisibility(0);
            this.mRootBinding.titleLine.etSearchKey.setHint(searchHint);
            this.mRootBinding.titleLine.etSearchKey.setOnEditorActionListener(actionListener);
            this.mRootBinding.titleLine.etSearchKey.addTextChangedListener(textWatcher);
            this.mRootBinding.titleLine.tvCancel.setOnClickListener(listener);
        }
    }

    public void initSearchTitleBar(SearchKey searchKey, String searchHint, OnEditorActionListener actionListener, TextWatcher textWatcher) {
        if (this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.setKey(searchKey);
            this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
            this.mRootBinding.titleLine.titlebarMiddleParent.setVisibility(8);
            this.mRootBinding.titleLine.rlSearchBar.setVisibility(0);
            this.mRootBinding.titleLine.etSearchKey.setHint(searchHint);
            this.mRootBinding.titleLine.etSearchKey.setOnEditorActionListener(actionListener);
            this.mRootBinding.titleLine.etSearchKey.addTextChangedListener(textWatcher);
            this.mRootBinding.titleLine.tvCancel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    BaseActivity.this.finish();
                }
            });
        }
    }

    public void showLaoSiJiDialog() {
        if (this.mLaoSiJiDialog == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.progress_dialog_layout, null);
            ((AutoFrescoDraweeView) view.findViewById(R.id.ad_progress)).setAutoImageURI(Uri.parse("res://com.feng.car/2130838307"));
            this.mLaoSiJiDialog = new Dialog(this, R.style.laosiji_load_dialog);
            this.mLaoSiJiDialog.setCanceledOnTouchOutside(false);
            this.mLaoSiJiDialog.setCancelable(true);
            Window window = this.mLaoSiJiDialog.getWindow();
            window.setGravity(17);
            window.setContentView(view);
            window.setWindowAnimations(R.style.laosiji_load_anim);
            window.setLayout(-2, -2);
        }
        if (!this.mLaoSiJiDialog.isShowing()) {
            this.mLaoSiJiDialog.show();
        }
    }

    public ProgressDialog showProgress(String title, String message) {
        if (this.mProgressDialog == null) {
            this.mProgressDialog = new ProgressDialog(this, 3);
            this.mProgressDialog.setProgressStyle(0);
            this.mProgressDialog.requestWindowFeature(1);
            this.mProgressDialog.setCanceledOnTouchOutside(false);
            this.mProgressDialog.setIndeterminate(true);
        }
        if (!StringUtil.isEmpty(title)) {
            this.mProgressDialog.setTitle(title);
        }
        this.mProgressDialog.setMessage(message);
        if (this.mLaoSiJiDialog == null || !this.mLaoSiJiDialog.isShowing()) {
            this.mProgressDialog.show();
        }
        return this.mProgressDialog;
    }

    public void hideProgress() {
        try {
            if (this.mProgressDialog != null && this.mProgressDialog.isShowing()) {
                this.mProgressDialog.dismiss();
            }
            if (this.mLaoSiJiDialog != null && this.mLaoSiJiDialog.isShowing()) {
                this.mLaoSiJiDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (this.mLastViewId != v.getId() || nowTime - this.mFirstTime >= 1000) {
            this.mFirstTime = nowTime;
            this.mLastViewId = v.getId();
            onSingleClick(v);
        }
    }

    public void onSingleClick(View v) {
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", DispatchConstants.ANDROID);
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        if (result == 0) {
            return getResources().getDimensionPixelSize(R.dimen.default_48PX);
        }
        return result;
    }

    public void imagePause() {
        Fresco.getImagePipeline().pause();
    }

    public void imageResume() {
        Fresco.getImagePipeline().resume();
    }

    public void startActivity(Intent intent) {
        if (intent == null || intent.getComponent() == null) {
            super.startActivity(intent);
        } else if (!intent.getComponent().getClassName().equals("com.feng.car.activity.LoginActivity") || FengApplication.getInstance().isLoginUser()) {
            super.startActivity(intent);
        } else if (FengApplication.getInstance().getSeverceState()) {
            super.startActivity(intent);
        } else {
            showSecondTypeToast((int) R.string.server_maintain);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return false;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50005) {
            checkBasePermission();
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode != 50000) {
                return;
            }
            if (grantResults.length != 1 || permissions.length != 1) {
                int i = 0;
                while (i < grantResults.length) {
                    if (grantResults[i] == 0) {
                        i++;
                    } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])) {
                        showPermissionsDialog(false);
                        return;
                    } else {
                        showPermissionsDialog(true);
                        return;
                    }
                }
                permissionSuccess();
            } else if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                permissionSuccess();
            } else if (grantResults[0] == 0) {
                permissionSuccess();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                showPermissionsDialog(false);
            } else {
                showPermissionsDialog(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            permissionSuccess();
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", BaseActivity.this.getPackageName(), null));
                    BaseActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                BaseActivity.this.checkBasePermission();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        }, true);
    }

    public void checkBasePermission() {
        permissionSuccess();
    }

    public void permissionSuccess() {
    }

    public String getLogCurrentPage() {
        if (this.mHasInPageMap) {
            return (String) LogGatherReadUtil.getInstance().getPageMap().get(getLocalClassName());
        }
        return "-";
    }

    public void showFirstTypeToast(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_1, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showFirstTypeToast(final String string) {
        if (!TextUtils.isEmpty(string)) {
            runOnUiThread(new Runnable() {
                public void run() {
                    BaseActivity.this.showBaseToast(string, R.drawable.icon_toast_1, m_AppUI.MSG_APP_DATA_OK);
                }
            });
        }
    }

    public void showSecondTypeToast(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_2, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showSecondTypeToast(final String string) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(string, R.drawable.icon_toast_2, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showThirdTypeToast(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_3, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showThirdTypeToastLong(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_3, PathInterpolatorCompat.MAX_NUM_POINTS);
            }
        });
    }

    public void showThirdTypeToast(final String string) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(string, R.drawable.icon_toast_3, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showFourthTypeToast(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_4, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    public void showFifthTypeToast(final int resStrId) {
        runOnUiThread(new Runnable() {
            public void run() {
                BaseActivity.this.showBaseToast(BaseActivity.this.getString(resStrId), R.drawable.icon_toast_5, m_AppUI.MSG_APP_DATA_OK);
            }
        });
    }

    private void showBaseToast(String msg, int draId, int time) {
        if (this.mToastDialog == null) {
            this.mToastDialog = new PromptDialog(this);
            this.mToastDialog.getDefaultBuilder().touchAble(true).round(5.0f).loadingDuration((long) time);
        }
        this.mToastDialog.showCustom(draId, msg);
    }

    public void hideToastDialog() {
        if (this.mToastDialog != null) {
            this.mToastDialog.dismissImmediately();
        }
    }
}
