package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
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

public class ItemBlackListBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdBlackHead;
    public final TextView blackText;
    public final LinearLayout llUserMsg;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final TextView mboundView1;
    private final TextView mboundView2;
    public final RelativeLayout rrBlackListItem;

    static {
        sViewsWithIds.put(2131625108, 4);
        sViewsWithIds.put(2131625109, 5);
    }

    public ItemBlackListBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 3);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.afdBlackHead = (AutoFrescoDraweeView) bindings[4];
        this.blackText = (TextView) bindings[3];
        this.blackText.setTag(null);
        this.llUserMsg = (LinearLayout) bindings[5];
        this.mboundView1 = (TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.rrBlackListItem = (RelativeLayout) bindings[0];
        this.rrBlackListItem.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16;
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
            this.mDirtyFlags |= 8;
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
            case 1:
                return onChangeUserInfoIsblack((ObservableInt) object, fieldId);
            case 2:
                return onChangeUserInfoSignature((ObservableField) object, fieldId);
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

    private boolean onChangeUserInfoIsblack(ObservableInt UserInfoIsblack, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoSignature(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
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
        int userInfoIsblackGet = 0;
        Drawable userInfoIsblackInt1BlackTextAndroidDrawableBlackRemoveSelectorBlackTextAndroidDrawableBlackAddSelector = null;
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        String userInfoSignatureGet = null;
        UserInfo userInfo = this.mUserInfo;
        ObservableInt userInfoIsblack = null;
        boolean userInfoGetIsFirstAuthenticated = false;
        Drawable userInfoGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        ObservableField<String> userInfoSignature = null;
        if ((31 & dirtyFlags) != 0) {
            if ((25 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(0, userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((26 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoIsblack = userInfo.isblack;
                }
                updateRegistration(1, userInfoIsblack);
                if (userInfoIsblack != null) {
                    userInfoIsblackGet = userInfoIsblack.get();
                }
                boolean userInfoIsblackInt1 = userInfoIsblackGet == 1;
                if ((26 & dirtyFlags) != 0) {
                    if (userInfoIsblackInt1) {
                        dirtyFlags |= 64;
                    } else {
                        dirtyFlags |= 32;
                    }
                }
                if (userInfoIsblackInt1) {
                    userInfoIsblackInt1BlackTextAndroidDrawableBlackRemoveSelectorBlackTextAndroidDrawableBlackAddSelector = getDrawableFromResource(this.blackText, 2130837755);
                } else {
                    userInfoIsblackInt1BlackTextAndroidDrawableBlackRemoveSelectorBlackTextAndroidDrawableBlackAddSelector = getDrawableFromResource(this.blackText, 2130837751);
                }
            }
            if ((24 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((24 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 256;
                    } else {
                        dirtyFlags |= 128;
                    }
                }
                userInfoGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.mboundView1, 2130837629) : null;
            }
            if ((28 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSignature = userInfo.signature;
                }
                updateRegistration(2, userInfoSignature);
                if (userInfoSignature != null) {
                    userInfoSignatureGet = (String) userInfoSignature.get();
                }
            }
        }
        if ((26 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.blackText, userInfoIsblackInt1BlackTextAndroidDrawableBlackRemoveSelectorBlackTextAndroidDrawableBlackAddSelector);
        }
        if ((25 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView1, userInfoNameGet);
        }
        if ((24 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.mboundView1, userInfoGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.mboundView1, userInfoGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((28 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, userInfoSignatureGet);
        }
    }

    public static ItemBlackListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemBlackListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemBlackListBinding) DataBindingUtil.inflate(inflater, 2130903263, root, attachToRoot, bindingComponent);
    }

    public static ItemBlackListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemBlackListBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903263, null, false), bindingComponent);
    }

    public static ItemBlackListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemBlackListBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_black_list_0".equals(view.getTag())) {
            return new ItemBlackListBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
