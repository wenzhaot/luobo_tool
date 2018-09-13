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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.library.R;

public class NewAssociatedSearchItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdHead;
    public final ImageView ivIcon;
    public final LinearLayout llUserInfo;
    public final LinearLayout llUserMsg;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlCarNormal;
    public final TextView tvContent;
    public final TextView tvFansNum;
    public final TextView tvGoTo;
    public final TextView tvUserName;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624473, 2);
        sViewsWithIds.put(2131625274, 3);
        sViewsWithIds.put(2131625138, 4);
        sViewsWithIds.put(2131625109, 5);
        sViewsWithIds.put(2131624416, 6);
        sViewsWithIds.put(2131625302, 7);
        sViewsWithIds.put(R.id.iv_icon, 8);
        sViewsWithIds.put(2131624859, 9);
        sViewsWithIds.put(2131625303, 10);
    }

    public NewAssociatedSearchItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.afdHead = (AutoFrescoDraweeView) bindings[4];
        this.ivIcon = (ImageView) bindings[8];
        this.llUserInfo = (LinearLayout) bindings[3];
        this.llUserMsg = (LinearLayout) bindings[5];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlCarNormal = (RelativeLayout) bindings[7];
        this.tvContent = (TextView) bindings[9];
        this.tvFansNum = (TextView) bindings[6];
        this.tvGoTo = (TextView) bindings[10];
        this.tvUserName = (TextView) bindings[1];
        this.tvUserName.setTag(null);
        this.vLine = (View) bindings[2];
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
        Drawable userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        UserInfo userInfo = this.mUserInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
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
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, userInfoNameGet);
        }
    }

    public static NewAssociatedSearchItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static NewAssociatedSearchItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (NewAssociatedSearchItemBinding) DataBindingUtil.inflate(inflater, 2130903317, root, attachToRoot, bindingComponent);
    }

    public static NewAssociatedSearchItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static NewAssociatedSearchItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903317, null, false), bindingComponent);
    }

    public static NewAssociatedSearchItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static NewAssociatedSearchItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/new_associated_search_item_0".equals(view.getTag())) {
            return new NewAssociatedSearchItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
