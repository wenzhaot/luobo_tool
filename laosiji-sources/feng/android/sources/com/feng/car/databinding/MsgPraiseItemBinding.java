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
import com.feng.car.view.textview.AisenTextView;

public class MsgPraiseItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AisenTextView avCommentContent;
    public final AutoFrescoDraweeView fdvThreadDetailImg;
    public final AutoFrescoDraweeView fdvUserPraisePortrait;
    public final LinearLayout llDetailContainer;
    public final LinearLayout llPraiseContainer;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final ImageView mboundView1;
    public final RelativeLayout rlThreadDetailContainer;
    public final RelativeLayout rlUserInfo;
    public final TextView tvPraiseDetail;
    public final TextView tvThreadDetailContent;
    public final TextView tvThreadDetailUsername;
    public final TextView tvUserPraiseTimestamp;
    public final TextView tvUserPraiseUsername;

    static {
        sViewsWithIds.put(2131624788, 3);
        sViewsWithIds.put(2131625286, 4);
        sViewsWithIds.put(2131625288, 5);
        sViewsWithIds.put(2131625289, 6);
        sViewsWithIds.put(2131625290, 7);
        sViewsWithIds.put(2131625291, 8);
        sViewsWithIds.put(2131625267, 9);
        sViewsWithIds.put(2131625292, 10);
        sViewsWithIds.put(2131625269, 11);
        sViewsWithIds.put(2131625270, 12);
    }

    public MsgPraiseItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.avCommentContent = (AisenTextView) bindings[8];
        this.fdvThreadDetailImg = (AutoFrescoDraweeView) bindings[10];
        this.fdvUserPraisePortrait = (AutoFrescoDraweeView) bindings[4];
        this.llDetailContainer = (LinearLayout) bindings[7];
        this.llPraiseContainer = (LinearLayout) bindings[0];
        this.llPraiseContainer.setTag(null);
        this.mboundView1 = (ImageView) bindings[1];
        this.mboundView1.setTag(null);
        this.rlThreadDetailContainer = (RelativeLayout) bindings[9];
        this.rlUserInfo = (RelativeLayout) bindings[3];
        this.tvPraiseDetail = (TextView) bindings[6];
        this.tvThreadDetailContent = (TextView) bindings[12];
        this.tvThreadDetailUsername = (TextView) bindings[11];
        this.tvUserPraiseTimestamp = (TextView) bindings[5];
        this.tvUserPraiseUsername = (TextView) bindings[2];
        this.tvUserPraiseUsername.setTag(null);
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
        int userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE = 0;
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
                userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE = userInfoGetIsFirstAuthenticated ? 0 : 8;
            }
        }
        if ((6 & dirtyFlags) != 0) {
            this.mboundView1.setVisibility(userInfoGetIsFirstAuthenticatedViewVISIBLEViewGONE);
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserPraiseUsername, userInfoNameGet);
        }
    }

    public static MsgPraiseItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static MsgPraiseItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (MsgPraiseItemBinding) DataBindingUtil.inflate(inflater, 2130903313, root, attachToRoot, bindingComponent);
    }

    public static MsgPraiseItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static MsgPraiseItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903313, null, false), bindingComponent);
    }

    public static MsgPraiseItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static MsgPraiseItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/msg_praise_item_0".equals(view.getTag())) {
            return new MsgPraiseItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
