package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class PhotoTextUserListItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView brandImage;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    private final ImageView mboundView1;
    public final TextView tvUserName;

    static {
        sViewsWithIds.put(2131625356, 2);
        sViewsWithIds.put(2131624271, 3);
    }

    public PhotoTextUserListItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.brandImage = (AutoFrescoDraweeView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (ImageView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvUserName = (TextView) bindings[3];
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
        int userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE = 0;
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
            userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE = userInfoGetIsFirstAuthenticated ? 0 : 8;
        }
        if ((dirtyFlags & 3) != 0) {
            this.mboundView1.setVisibility(userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE);
        }
    }

    public static PhotoTextUserListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextUserListItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PhotoTextUserListItemBinding) DataBindingUtil.inflate(inflater, 2130903340, root, attachToRoot, bindingComponent);
    }

    public static PhotoTextUserListItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextUserListItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903340, null, false), bindingComponent);
    }

    public static PhotoTextUserListItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoTextUserListItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/photo_text_user_list_item_0".equals(view.getTag())) {
            return new PhotoTextUserListItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
