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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivityEditUserInfoBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdEditHead;
    public final ImageView ivEditArrowProfiles;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlEditGender;
    public final RelativeLayout rlEditHeadImg;
    public final RelativeLayout rlEditNick;
    public final RelativeLayout rlEditProfiles;
    public final TextView tvEditGender;
    public final TextView tvEditGenderTip;
    public final TextView tvEditHeadTip;
    public final TextView tvEditNick;
    public final TextView tvEditNickTip;
    public final TextView tvEditProfilesTip;
    public final TextView tvSignature;

    static {
        sViewsWithIds.put(2131624345, 3);
        sViewsWithIds.put(2131624346, 4);
        sViewsWithIds.put(2131624347, 5);
        sViewsWithIds.put(2131624348, 6);
        sViewsWithIds.put(2131624349, 7);
        sViewsWithIds.put(2131624351, 8);
        sViewsWithIds.put(2131624352, 9);
        sViewsWithIds.put(2131624353, 10);
        sViewsWithIds.put(2131624354, 11);
        sViewsWithIds.put(2131624355, 12);
        sViewsWithIds.put(2131624356, 13);
    }

    public ActivityEditUserInfoBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.afdEditHead = (AutoFrescoDraweeView) bindings[5];
        this.ivEditArrowProfiles = (ImageView) bindings[13];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlEditGender = (RelativeLayout) bindings[8];
        this.rlEditHeadImg = (RelativeLayout) bindings[3];
        this.rlEditNick = (RelativeLayout) bindings[6];
        this.rlEditProfiles = (RelativeLayout) bindings[11];
        this.tvEditGender = (TextView) bindings[10];
        this.tvEditGenderTip = (TextView) bindings[9];
        this.tvEditHeadTip = (TextView) bindings[4];
        this.tvEditNick = (TextView) bindings[1];
        this.tvEditNick.setTag(null);
        this.tvEditNickTip = (TextView) bindings[7];
        this.tvEditProfilesTip = (TextView) bindings[12];
        this.tvSignature = (TextView) bindings[2];
        this.tvSignature.setTag(null);
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
                return onChangeUserInfoName((ObservableField) object, fieldId);
            case 1:
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

    private boolean onChangeUserInfoSignature(ObservableField<String> observableField, int fieldId) {
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

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        String userInfoSignatureGet = null;
        UserInfo userInfo = this.mUserInfo;
        ObservableField<String> userInfoSignature = null;
        if ((15 & dirtyFlags) != 0) {
            if ((13 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(0, userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((14 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSignature = userInfo.signature;
                }
                updateRegistration(1, userInfoSignature);
                if (userInfoSignature != null) {
                    userInfoSignatureGet = (String) userInfoSignature.get();
                }
            }
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvEditNick, userInfoNameGet);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvSignature, userInfoSignatureGet);
        }
    }

    public static ActivityEditUserInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityEditUserInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityEditUserInfoBinding) DataBindingUtil.inflate(inflater, 2130903089, root, attachToRoot, bindingComponent);
    }

    public static ActivityEditUserInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityEditUserInfoBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903089, null, false), bindingComponent);
    }

    public static ActivityEditUserInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityEditUserInfoBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_edit_user_info_0".equals(view.getTag())) {
            return new ActivityEditUserInfoBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
