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
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;

public class HeadMineBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdMineHead;
    public final ImageView dot;
    public final TextView fansMessage;
    public final ImageView ivMessage;
    public final ImageView ivMineHeadArrow;
    public final ImageView ivShareMoment;
    public final ImageView ivShareQq;
    public final ImageView ivShareQzone;
    public final ImageView ivShareWechat;
    public final ImageView ivShareWeibo;
    public final ImageView ivVideoBuffer;
    public final LinearLayout llMineLoginUser;
    private long mDirtyFlags = -1;
    private MessageCountInfo mMessageCountInfo;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final ImageView mineArticleHistory;
    public final ImageView mineCollection;
    public final ImageView mineDraft;
    public final ImageView mineLike;
    public final ImageView mineScanQr;
    public final RelativeLayout mineVideoBuffer;
    public final RelativeLayout rlMineAllSns;
    public final RelativeLayout rlMineBlackList;
    public final RelativeLayout rlMineEdit;
    public final RelativeLayout rlMineFans;
    public final RelativeLayout rlMineLoginTips;
    public final RelativeLayout rlMineNewUser;
    public final RelativeLayout rlMineOpenStore;
    public final RelativeLayout rlMineSetting;
    public final RelativeLayout rlMineUserInfo;
    public final RelativeLayout rlMineWallet;
    public final RelativeLayout rlTitleBar;
    public final TextView tvLogin;
    public final TextView tvMessageCommentNum;
    public final TextView tvMineArticles;
    public final TextView tvMineFans;
    public final TextView tvMineFollow;
    public final TextView tvMineName;
    public final TextView tvMineSignature;
    public final TextView tvMyShore;
    public final TextView tvNewUser;
    public final View videoRemindDot;

    static {
        sViewsWithIds.put(2131624215, 4);
        sViewsWithIds.put(2131624232, 5);
        sViewsWithIds.put(2131624233, 6);
        sViewsWithIds.put(2131625055, 7);
        sViewsWithIds.put(2131625056, 8);
        sViewsWithIds.put(2131625057, 9);
        sViewsWithIds.put(2131625058, 10);
        sViewsWithIds.put(2131625061, 11);
        sViewsWithIds.put(2131624388, 12);
        sViewsWithIds.put(2131625062, 13);
        sViewsWithIds.put(2131625063, 14);
        sViewsWithIds.put(2131625064, 15);
        sViewsWithIds.put(2131625065, 16);
        sViewsWithIds.put(2131625066, 17);
        sViewsWithIds.put(2131625067, 18);
        sViewsWithIds.put(2131625068, 19);
        sViewsWithIds.put(2131625069, 20);
        sViewsWithIds.put(2131625070, 21);
        sViewsWithIds.put(2131625071, 22);
        sViewsWithIds.put(2131625072, 23);
        sViewsWithIds.put(2131625073, 24);
        sViewsWithIds.put(2131625074, 25);
        sViewsWithIds.put(2131625075, 26);
        sViewsWithIds.put(2131625076, 27);
        sViewsWithIds.put(2131625077, 28);
        sViewsWithIds.put(2131625078, 29);
        sViewsWithIds.put(2131625079, 30);
        sViewsWithIds.put(2131625080, 31);
        sViewsWithIds.put(2131625081, 32);
        sViewsWithIds.put(2131625082, 33);
        sViewsWithIds.put(2131625083, 34);
        sViewsWithIds.put(2131625084, 35);
        sViewsWithIds.put(2131625085, 36);
        sViewsWithIds.put(2131625086, 37);
        sViewsWithIds.put(2131625087, 38);
        sViewsWithIds.put(2131625088, 39);
    }

    public HeadMineBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 40, sIncludes, sViewsWithIds);
        this.afdMineHead = (AutoFrescoDraweeView) bindings[8];
        this.dot = (ImageView) bindings[34];
        this.fansMessage = (TextView) bindings[3];
        this.fansMessage.setTag(null);
        this.ivMessage = (ImageView) bindings[5];
        this.ivMineHeadArrow = (ImageView) bindings[10];
        this.ivShareMoment = (ImageView) bindings[35];
        this.ivShareQq = (ImageView) bindings[38];
        this.ivShareQzone = (ImageView) bindings[39];
        this.ivShareWechat = (ImageView) bindings[36];
        this.ivShareWeibo = (ImageView) bindings[37];
        this.ivVideoBuffer = (ImageView) bindings[23];
        this.llMineLoginUser = (LinearLayout) bindings[9];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mineArticleHistory = (ImageView) bindings[17];
        this.mineCollection = (ImageView) bindings[20];
        this.mineDraft = (ImageView) bindings[21];
        this.mineLike = (ImageView) bindings[18];
        this.mineScanQr = (ImageView) bindings[19];
        this.mineVideoBuffer = (RelativeLayout) bindings[22];
        this.rlMineAllSns = (RelativeLayout) bindings[25];
        this.rlMineBlackList = (RelativeLayout) bindings[30];
        this.rlMineEdit = (RelativeLayout) bindings[29];
        this.rlMineFans = (RelativeLayout) bindings[15];
        this.rlMineLoginTips = (RelativeLayout) bindings[11];
        this.rlMineNewUser = (RelativeLayout) bindings[26];
        this.rlMineOpenStore = (RelativeLayout) bindings[31];
        this.rlMineSetting = (RelativeLayout) bindings[33];
        this.rlMineUserInfo = (RelativeLayout) bindings[7];
        this.rlMineWallet = (RelativeLayout) bindings[28];
        this.rlTitleBar = (RelativeLayout) bindings[4];
        this.tvLogin = (TextView) bindings[12];
        this.tvMessageCommentNum = (TextView) bindings[6];
        this.tvMineArticles = (TextView) bindings[13];
        this.tvMineFans = (TextView) bindings[16];
        this.tvMineFollow = (TextView) bindings[14];
        this.tvMineName = (TextView) bindings[1];
        this.tvMineName.setTag(null);
        this.tvMineSignature = (TextView) bindings[2];
        this.tvMineSignature.setTag(null);
        this.tvMyShore = (TextView) bindings[32];
        this.tvNewUser = (TextView) bindings[27];
        this.videoRemindDot = (View) bindings[24];
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
            case 38:
                setMessageCountInfo((MessageCountInfo) variable);
                return true;
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMessageCountInfo(MessageCountInfo MessageCountInfo) {
        this.mMessageCountInfo = MessageCountInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(38);
        super.requestRebind();
    }

    public MessageCountInfo getMessageCountInfo() {
        return this.mMessageCountInfo;
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
        String fengUtilNumberFormat99MessageCountInfoFansCount = null;
        String userInfoGetIsFirstAuthenticatedInfo = null;
        int messageCountInfoFansCount = 0;
        MessageCountInfo messageCountInfo = this.mMessageCountInfo;
        Drawable userInfoGetIsFirstAuthenticatedTvMineNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        int messageCountInfoFansCountInt0ViewVISIBLEViewGONE = 0;
        String userInfoNameGet = null;
        ObservableField<String> userInfoName = null;
        UserInfo userInfo = this.mUserInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
        if ((10 & dirtyFlags) != 0) {
            if (messageCountInfo != null) {
                messageCountInfoFansCount = messageCountInfo.fansCount;
            }
            fengUtilNumberFormat99MessageCountInfoFansCount = FengUtil.numberFormat99(messageCountInfoFansCount);
            boolean messageCountInfoFansCountInt0 = messageCountInfoFansCount > 0;
            if ((10 & dirtyFlags) != 0) {
                if (messageCountInfoFansCountInt0) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            messageCountInfoFansCountInt0ViewVISIBLEViewGONE = messageCountInfoFansCountInt0 ? 0 : 8;
        }
        if ((13 & dirtyFlags) != 0) {
            if ((12 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticatedInfo = userInfo.getIsFirstAuthenticatedInfo();
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((12 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 32;
                    } else {
                        dirtyFlags |= 16;
                    }
                }
                userInfoGetIsFirstAuthenticatedTvMineNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.tvMineName, 2130837629) : null;
            }
            if (userInfo != null) {
                userInfoName = userInfo.name;
            }
            updateRegistration(0, userInfoName);
            if (userInfoName != null) {
                userInfoNameGet = (String) userInfoName.get();
            }
        }
        if ((10 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.fansMessage, fengUtilNumberFormat99MessageCountInfoFansCount);
            this.fansMessage.setVisibility(messageCountInfoFansCountInt0ViewVISIBLEViewGONE);
        }
        if ((12 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvMineName, userInfoGetIsFirstAuthenticatedTvMineNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvMineName, userInfoGetIsFirstAuthenticatedTvMineNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setText(this.tvMineSignature, userInfoGetIsFirstAuthenticatedInfo);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvMineName, userInfoNameGet);
        }
    }

    public static HeadMineBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HeadMineBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HeadMineBinding) DataBindingUtil.inflate(inflater, 2130903253, root, attachToRoot, bindingComponent);
    }

    public static HeadMineBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HeadMineBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903253, null, false), bindingComponent);
    }

    public static HeadMineBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HeadMineBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/head_mine_0".equals(view.getTag())) {
            return new HeadMineBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
