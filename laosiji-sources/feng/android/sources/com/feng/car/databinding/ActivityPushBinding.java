package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.CompoundButtonBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.PushModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.SlidingButton;

public class ActivityPushBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivOpenFloat;
    private long mDirtyFlags = -1;
    private UserInfo mLoginUserInfo;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlOpenNetmonitor;
    public final RelativeLayout rlPushAtMe;
    public final RelativeLayout rlPushComment;
    public final RelativeLayout rlPushPrivateLetter;
    public final SlidingButton sliderBtnAttention;
    public final TextView textView;

    static {
        sViewsWithIds.put(2131624558, 2);
        sViewsWithIds.put(2131624559, 3);
        sViewsWithIds.put(2131624560, 4);
        sViewsWithIds.put(2131624561, 5);
        sViewsWithIds.put(2131624562, 6);
        sViewsWithIds.put(2131624563, 7);
    }

    public ActivityPushBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.ivOpenFloat = (ImageView) bindings[7];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlOpenNetmonitor = (RelativeLayout) bindings[6];
        this.rlPushAtMe = (RelativeLayout) bindings[4];
        this.rlPushComment = (RelativeLayout) bindings[3];
        this.rlPushPrivateLetter = (RelativeLayout) bindings[5];
        this.sliderBtnAttention = (SlidingButton) bindings[1];
        this.sliderBtnAttention.setTag(null);
        this.textView = (TextView) bindings[2];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
        }
        requestRebind();
    }

    public boolean hasPendingBindings() {
        synchronized (this) {
            if (this.mDirtyFlags != 0) {
                return true;
            }
            return false;
        }
    }

    public boolean setVariable(int variableId, Object variable) {
        switch (variableId) {
            case 32:
                setLoginUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setLoginUserInfo(UserInfo LoginUserInfo) {
        this.mLoginUserInfo = LoginUserInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(32);
        super.requestRebind();
    }

    public UserInfo getLoginUserInfo() {
        return this.mLoginUserInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        int loginUserInfoPushFollowpush = 0;
        boolean loginUserInfoPushFollowpushInt0BooleanFalseBooleanTrue = false;
        PushModel loginUserInfoPush = null;
        UserInfo loginUserInfo = this.mLoginUserInfo;
        if ((3 & dirtyFlags) != 0) {
            if (loginUserInfo != null) {
                loginUserInfoPush = loginUserInfo.push;
            }
            if (loginUserInfoPush != null) {
                loginUserInfoPushFollowpush = loginUserInfoPush.followpush;
            }
            boolean loginUserInfoPushFollowpushInt0 = loginUserInfoPushFollowpush == 0;
            if ((3 & dirtyFlags) != 0) {
                if (loginUserInfoPushFollowpushInt0) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            loginUserInfoPushFollowpushInt0BooleanFalseBooleanTrue = !loginUserInfoPushFollowpushInt0;
        }
        if ((3 & dirtyFlags) != 0) {
            CompoundButtonBindingAdapter.setChecked(this.sliderBtnAttention, loginUserInfoPushFollowpushInt0BooleanFalseBooleanTrue);
        }
    }

    public static ActivityPushBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPushBinding) DataBindingUtil.inflate(inflater, 2130903116, root, attachToRoot, bindingComponent);
    }

    public static ActivityPushBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903116, null, false), bindingComponent);
    }

    public static ActivityPushBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPushBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_push_0".equals(view.getTag())) {
            return new ActivityPushBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
