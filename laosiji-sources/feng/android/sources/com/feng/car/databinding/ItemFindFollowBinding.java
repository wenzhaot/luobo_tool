package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemFindFollowBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdHead;
    public final ImageView ivFindFollowSelect;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final TextView mboundView2;
    public final RelativeLayout rlParent;
    public final TextView tvUserName;

    static {
        sViewsWithIds.put(2131625138, 3);
        sViewsWithIds.put(2131625140, 4);
    }

    public ItemFindFollowBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.afdHead = (AutoFrescoDraweeView) bindings[3];
        this.ivFindFollowSelect = (ImageView) bindings[4];
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.tvUserName = (TextView) bindings[1];
        this.tvUserName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(75);
        super.requestRebind();
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeUserInfoName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoName(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String userInfoGetIsFirstAuthenticatedInfo = null;
        Drawable userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        UserInfo userInfo = this.mUserInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
        if ((7 & dirtyFlags) != 0) {
            if ((6 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticatedInfo = userInfo.getIsFirstAuthenticatedInfo();
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((6 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 16;
                    } else {
                        dirtyFlags |= 8;
                    }
                }
                userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUserName, 2130837629) : null;
            }
            if (userInfo != null) {
                userInfoName = userInfo.name;
            }
            updateRegistration(0, userInfoName);
            if (userInfoName != null) {
                userInfoNameGet = (String) userInfoName.get();
            }
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, userInfoGetIsFirstAuthenticatedInfo);
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, userInfoNameGet);
        }
    }

    public static ItemFindFollowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFindFollowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemFindFollowBinding) DataBindingUtil.inflate(inflater, 2130903270, root, attachToRoot, bindingComponent);
    }

    public static ItemFindFollowBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFindFollowBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903270, null, false), bindingComponent);
    }

    public static ItemFindFollowBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFindFollowBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_find_follow_0".equals(view.getTag())) {
            return new ItemFindFollowBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
