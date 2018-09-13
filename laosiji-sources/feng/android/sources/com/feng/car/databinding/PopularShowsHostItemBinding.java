package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class PopularShowsHostItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdHeadImage;
    public final ImageView ivAuthored;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    private final TextView mboundView2;

    static {
        sViewsWithIds.put(2131625361, 3);
    }

    public PopularShowsHostItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.afdHeadImage = (AutoFrescoDraweeView) bindings[3];
        this.ivAuthored = (ImageView) bindings[1];
        this.ivAuthored.setTag(null);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
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
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        UserInfo userInfo = this.mUserInfo;
        int userInfoIsFirstAuthenticatedViewVISIBLEViewGONE = 0;
        boolean userInfoIsFirstAuthenticated = false;
        if ((7 & dirtyFlags) != 0) {
            if (userInfo != null) {
                userInfoName = userInfo.name;
            }
            updateRegistration(0, userInfoName);
            if (userInfoName != null) {
                userInfoNameGet = (String) userInfoName.get();
            }
            if ((6 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((6 & dirtyFlags) != 0) {
                    if (userInfoIsFirstAuthenticated) {
                        dirtyFlags |= 16;
                    } else {
                        dirtyFlags |= 8;
                    }
                }
                userInfoIsFirstAuthenticatedViewVISIBLEViewGONE = userInfoIsFirstAuthenticated ? 0 : 8;
            }
        }
        if ((6 & dirtyFlags) != 0) {
            this.ivAuthored.setVisibility(userInfoIsFirstAuthenticatedViewVISIBLEViewGONE);
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, userInfoNameGet);
        }
    }

    public static PopularShowsHostItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PopularShowsHostItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PopularShowsHostItemBinding) DataBindingUtil.inflate(inflater, 2130903345, root, attachToRoot, bindingComponent);
    }

    public static PopularShowsHostItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PopularShowsHostItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903345, null, false), bindingComponent);
    }

    public static PopularShowsHostItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PopularShowsHostItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/popular_shows_host_item_0".equals(view.getTag())) {
            return new PopularShowsHostItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
