package com.feng.car.fragment;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.MvvmBaseAdapter;
import com.feng.car.entity.model.LogGatherInfo;
import com.feng.car.event.NetWorkConnectEvent;
import com.feng.car.event.UserLoginEvent;
import com.feng.library.utils.StringUtil;
import java.lang.reflect.Field;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public abstract class BaseFragment<VB extends ViewDataBinding> extends Fragment implements OnClickListener {
    final int INTERVAL_TIME = 1000;
    public FragmentActivity mActivity;
    public VB mBind;
    private long mFirstTime = 0;
    public LayoutInflater mInflater;
    private boolean mIsFirstVisible = true;
    private boolean mIsFragmentVisible;
    private boolean mIsVisibleToUser = false;
    private int mLastViewId = 0;
    private LogGatherInfo mLogGatherInfo = new LogGatherInfo();
    private boolean mLoginOut = false;
    private boolean mLoginSuccess = false;
    public View mRootView;

    protected abstract void initView();

    protected abstract int setLayoutId();

    public LogGatherInfo getLogGatherInfo() {
        return this.mLogGatherInfo;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable();
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.mRootView != null) {
            this.mIsVisibleToUser = isVisibleToUser;
            if (this.mIsFirstVisible && isVisibleToUser) {
                onFragmentFirstVisible();
                this.mIsFirstVisible = false;
            }
            if (isVisibleToUser) {
                onFragmentVisibleChange(true);
                this.mIsFragmentVisible = true;
            } else if (this.mIsFragmentVisible) {
                this.mIsFragmentVisible = false;
                onFragmentVisibleChange(false);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        if (this.mRootView == null) {
            this.mInflater = inflater;
            this.mBind = DataBindingUtil.inflate(inflater, setLayoutId(), container, false);
            this.mRootView = this.mBind.getRoot();
            initView();
        } else {
            ViewGroup parent = (ViewGroup) this.mRootView.getParent();
            if (parent != null) {
                parent.removeView(this.mRootView);
            }
        }
        return this.mRootView;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getUserVisibleHint()) {
            if (this.mIsFirstVisible) {
                onFragmentFirstVisible();
                this.mIsFirstVisible = false;
                this.mIsVisibleToUser = true;
            }
            if (!this.mIsFragmentVisible) {
                onFragmentVisibleChange(true);
                this.mIsFragmentVisible = true;
            }
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (FragmentActivity) context;
    }

    public void onResume() {
        super.onResume();
        if (this.mLoginSuccess) {
            loginSuccess();
            this.mLoginSuccess = false;
        }
        if (this.mLoginOut) {
            loginOut();
            this.mLoginOut = false;
        }
        if (this.mBind.getRoot().getBackground() != null) {
            this.mBind.getRoot().getBackground().mutate().setAlpha(255);
        }
        if (!this.mIsFragmentVisible && !this.mIsFirstVisible && this.mIsVisibleToUser) {
            onFragmentVisibleChange(true);
            this.mIsFragmentVisible = true;
        }
    }

    public void onPause() {
        super.onPause();
        this.mIsFragmentVisible = false;
    }

    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            if (this.mLoginSuccess) {
                loginSuccess();
                this.mLoginSuccess = false;
            }
            if (this.mLoginOut) {
                loginOut();
                this.mLoginOut = false;
            }
            onFragmentVisibleChange(true);
            this.mIsFragmentVisible = true;
        }
        if (this.mBind.getRoot() != null && this.mBind.getRoot().getBackground() != null) {
            this.mBind.getRoot().getBackground().mutate().setAlpha(255);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        initVariable();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserLoginEvent event) {
        if (event.mIsLogin) {
            this.mLoginSuccess = true;
            this.mLoginOut = false;
            return;
        }
        this.mLoginOut = true;
        this.mLoginSuccess = false;
    }

    public void loginSuccess() {
    }

    public void loginOut() {
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

    public void startActivity(Intent intent) {
        if (intent == null || intent.getComponent() == null) {
            super.startActivity(intent);
        } else if (!intent.getComponent().getClassName().equals("com.feng.car.activity.LoginActivity") || FengApplication.getInstance().isLoginUser()) {
            super.startActivity(intent);
        } else if (FengApplication.getInstance().getSeverceState()) {
            super.startActivity(intent);
        } else {
            ((BaseActivity) this.mActivity).showSecondTypeToast(2131231540);
        }
    }

    private void initVariable() {
        this.mIsFirstVisible = true;
        this.mIsFragmentVisible = false;
        this.mRootView = null;
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    protected void onFragmentFirstVisible() {
    }

    public void checkSearchKey(String searchKey, int cityID) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(NetWorkConnectEvent event) {
        if (event != null) {
            refreshAdapter();
            if (this.mIsFragmentVisible && isAdded()) {
                onFragmentVisibleChange(true);
            }
        }
    }

    public void refreshAdapter() {
        for (Field field : getClass().getDeclaredFields()) {
            String type = field.getGenericType().toString();
            if (!StringUtil.isEmpty(type) && type.equals("class com.feng.car.adapter.CommonPostAdapter")) {
                field.setAccessible(true);
                try {
                    ((MvvmBaseAdapter) field.get(this)).refreshData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
