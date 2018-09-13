package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class MessageLetterItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView fdvLetterUserPortrait;
    public final View ivMessageLetterItemDivider;
    public final LinearLayout llLetterStatus;
    public final LinearLayout llUserInfo;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    public final TextView tvLetterDetail;
    public final TextView tvLetterNum;
    public final TextView tvLetterTimestamp;
    public final TextView tvLetterUsername;

    static {
        sViewsWithIds.put(2131625273, 2);
        sViewsWithIds.put(2131625274, 3);
        sViewsWithIds.put(2131625277, 4);
        sViewsWithIds.put(2131625275, 5);
        sViewsWithIds.put(2131625278, 6);
        sViewsWithIds.put(2131625279, 7);
        sViewsWithIds.put(2131625280, 8);
    }

    public MessageLetterItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.fdvLetterUserPortrait = (AutoFrescoDraweeView) bindings[2];
        this.ivMessageLetterItemDivider = (View) bindings[8];
        this.llLetterStatus = (LinearLayout) bindings[5];
        this.llUserInfo = (LinearLayout) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvLetterDetail = (TextView) bindings[4];
        this.tvLetterNum = (TextView) bindings[7];
        this.tvLetterTimestamp = (TextView) bindings[6];
        this.tvLetterUsername = (TextView) bindings[1];
        this.tvLetterUsername.setTag(null);
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
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUserInfo(UserInfo UserInfo) {
        this.mUserInfo = UserInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(75);
        super.requestRebind();
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
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
        Drawable userInfoGetIsFirstAuthenticatedTvLetterUsernameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        UserInfo userInfo = this.mUserInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
        if ((dirtyFlags & 3) != 0) {
            if (userInfo != null) {
                userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
            }
            if ((dirtyFlags & 3) != 0) {
                if (userInfoGetIsFirstAuthenticated) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            userInfoGetIsFirstAuthenticatedTvLetterUsernameAndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.tvLetterUsername, 2130837629) : null;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvLetterUsername, userInfoGetIsFirstAuthenticatedTvLetterUsernameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvLetterUsername, userInfoGetIsFirstAuthenticatedTvLetterUsernameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
    }

    public static MessageLetterItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static MessageLetterItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (MessageLetterItemBinding) DataBindingUtil.inflate(inflater, 2130903310, root, attachToRoot, bindingComponent);
    }

    public static MessageLetterItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static MessageLetterItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903310, null, false), bindingComponent);
    }

    public static MessageLetterItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static MessageLetterItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/message_letter_item_0".equals(view.getTag())) {
            return new MessageLetterItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
