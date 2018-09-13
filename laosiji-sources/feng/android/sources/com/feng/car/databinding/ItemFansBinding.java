package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
import android.databinding.ObservableInt;
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
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.UserRelationView;

public class ItemFansBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdHead;
    public final LinearLayout llUserMsg;
    private long mDirtyFlags = -1;
    private Integer mNType;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    private final TextView mboundView2;
    private final TextView mboundView3;
    public final TextView tvUserName;
    public final UserRelationView urvFans;

    static {
        sViewsWithIds.put(2131625138, 4);
        sViewsWithIds.put(2131625109, 5);
        sViewsWithIds.put(2131625139, 6);
    }

    public ItemFansBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.afdHead = (AutoFrescoDraweeView) bindings[4];
        this.llUserMsg = (LinearLayout) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView3 = (TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.tvUserName = (TextView) bindings[1];
        this.tvUserName.setTag(null);
        this.urvFans = (UserRelationView) bindings[6];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8;
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
            case 43:
                setNType((Integer) variable);
                return true;
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setNType(Integer NType) {
        this.mNType = NType;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    public Integer getNType() {
        return this.mNType;
    }

    public void setUserInfo(UserInfo UserInfo) {
        this.mUserInfo = UserInfo;
        synchronized (this) {
            this.mDirtyFlags |= 4;
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
                return onChangeUserInfoFanscount((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoFanscount(ObservableInt UserInfoFanscount, int fieldId) {
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
        int nTypeInt1ViewVISIBLEViewGONE = 0;
        String userInfoGetIsFirstAuthenticatedInfo = null;
        int nTypeInt0ViewVISIBLEViewGONE = 0;
        Drawable userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        ObservableInt userInfoFanscount = null;
        Integer nType = this.mNType;
        UserInfo userInfo = this.mUserInfo;
        int userInfoFanscountGet = 0;
        boolean userInfoGetIsFirstAuthenticated = false;
        String stringFormatMboundView3AndroidStringFansTextFengUtilNumberFormatUserInfoFanscount = null;
        if ((10 & dirtyFlags) != 0) {
            int androidDatabindingDynamicUtilSafeUnboxNType = DynamicUtil.safeUnbox(nType);
            boolean nTypeInt0 = androidDatabindingDynamicUtilSafeUnboxNType == 0;
            boolean nTypeInt1 = androidDatabindingDynamicUtilSafeUnboxNType == 1;
            if ((10 & dirtyFlags) != 0) {
                if (nTypeInt0) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            if ((10 & dirtyFlags) != 0) {
                if (nTypeInt1) {
                    dirtyFlags |= 32;
                } else {
                    dirtyFlags |= 16;
                }
            }
            nTypeInt0ViewVISIBLEViewGONE = nTypeInt0 ? 0 : 8;
            nTypeInt1ViewVISIBLEViewGONE = nTypeInt1 ? 0 : 8;
        }
        if ((13 & dirtyFlags) != 0) {
            if ((12 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticatedInfo = userInfo.getIsFirstAuthenticatedInfo();
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((12 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUserName, 2130837629) : null;
            }
            if (userInfo != null) {
                userInfoFanscount = userInfo.fanscount;
            }
            updateRegistration(0, userInfoFanscount);
            if (userInfoFanscount != null) {
                userInfoFanscountGet = userInfoFanscount.get();
            }
            String fengUtilNumberFormatUserInfoFanscount = FengUtil.numberFormat(userInfoFanscountGet);
            stringFormatMboundView3AndroidStringFansTextFengUtilNumberFormatUserInfoFanscount = String.format(this.mboundView3.getResources().getString(2131231034), new Object[]{fengUtilNumberFormatUserInfoFanscount});
        }
        if ((12 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, userInfoGetIsFirstAuthenticatedInfo);
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((10 & dirtyFlags) != 0) {
            this.mboundView2.setVisibility(nTypeInt0ViewVISIBLEViewGONE);
            this.mboundView3.setVisibility(nTypeInt1ViewVISIBLEViewGONE);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView3, stringFormatMboundView3AndroidStringFansTextFengUtilNumberFormatUserInfoFanscount);
        }
    }

    public static ItemFansBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFansBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemFansBinding) DataBindingUtil.inflate(inflater, 2130903269, root, attachToRoot, bindingComponent);
    }

    public static ItemFansBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFansBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903269, null, false), bindingComponent);
    }

    public static ItemFansBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemFansBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_fans_0".equals(view.getTag())) {
            return new ItemFansBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
