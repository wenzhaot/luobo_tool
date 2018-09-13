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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.recyclerview.EmptyView;
import com.feng.library.utils.StringUtil;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class PersonalHomeHeaderLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView count;
    public final View divider;
    public final EmptyView emptyView;
    public final RelativeLayout fansLine;
    public final TextView fansText;
    public final TextView focusText;
    public final AutoFrescoDraweeView headerBg;
    public final RelativeLayout headerView;
    public final AutoFrescoDraweeView image;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlImageHead;
    public final ImageView sexIcon;
    public final TextView signature;
    public final TextView username;

    static {
        sViewsWithIds.put(2131625350, 7);
        sViewsWithIds.put(2131624514, 8);
        sViewsWithIds.put(2131625352, 9);
        sViewsWithIds.put(2131624155, 10);
        sViewsWithIds.put(2131625353, 11);
        sViewsWithIds.put(2131624240, 12);
        sViewsWithIds.put(2131624231, 13);
    }

    public PersonalHomeHeaderLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 6);
        Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.count = (TextView) bindings[1];
        this.count.setTag(null);
        this.divider = (View) bindings[12];
        this.emptyView = (EmptyView) bindings[13];
        this.fansLine = (RelativeLayout) bindings[11];
        this.fansText = (TextView) bindings[6];
        this.fansText.setTag(null);
        this.focusText = (TextView) bindings[5];
        this.focusText.setTag(null);
        this.headerBg = (AutoFrescoDraweeView) bindings[8];
        this.headerView = (RelativeLayout) bindings[7];
        this.image = (AutoFrescoDraweeView) bindings[10];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlImageHead = (RelativeLayout) bindings[9];
        this.sexIcon = (ImageView) bindings[2];
        this.sexIcon.setTag(null);
        this.signature = (TextView) bindings[4];
        this.signature.setTag(null);
        this.username = (TextView) bindings[3];
        this.username.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128;
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
            this.mDirtyFlags |= 64;
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
                return onChangeUserInfoFollowcount((ObservableInt) object, fieldId);
            case 1:
                return onChangeUserInfoSex((ObservableInt) object, fieldId);
            case 2:
                return onChangeUserInfoFanscount((ObservableInt) object, fieldId);
            case 3:
                return onChangeUserInfoName((ObservableField) object, fieldId);
            case 4:
                return onChangeUserInfoSnscount((ObservableInt) object, fieldId);
            case 5:
                return onChangeUserInfoSignature((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoFollowcount(ObservableInt UserInfoFollowcount, int fieldId) {
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

    private boolean onChangeUserInfoSex(ObservableInt UserInfoSex, int fieldId) {
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

    private boolean onChangeUserInfoFanscount(ObservableInt UserInfoFanscount, int fieldId) {
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

    private boolean onChangeUserInfoName(ObservableField<String> observableField, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 8;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoSnscount(ObservableInt UserInfoSnscount, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 16;
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
                    this.mDirtyFlags |= 32;
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
        String stringFormatCountAndroidStringPersonalhomeCountFengUtilNumberFormatUserInfoSnscount = null;
        String stringFormatFansTextAndroidStringFansTextFengUtilNumberFormatUserInfoFanscount = null;
        int userInfoSnscountGet = 0;
        ObservableInt userInfoFollowcount = null;
        boolean stringUtilIsEmptyUserInfoSignature = false;
        ObservableInt userInfoSex = null;
        String userInfoNameGet = null;
        ObservableInt userInfoFanscount = null;
        ObservableField<String> userInfoName = null;
        String userInfoSignatureGet = null;
        UserInfo userInfo = this.mUserInfo;
        int userInfoFanscountGet = 0;
        Drawable userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = null;
        int userInfoSexGet = 0;
        boolean userInfoGetIsFirstAuthenticated = false;
        String stringFormatFocusTextAndroidStringFocusTextFengUtilNumberFormatUserInfoFollowcount = null;
        String userInfoGetFirstAuthenticatedInfo = null;
        String stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = null;
        int userInfoFollowcountGet = 0;
        ObservableInt userInfoSnscount = null;
        Drawable userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = null;
        ObservableField<String> userInfoSignature = null;
        if ((255 & dirtyFlags) != 0) {
            if ((193 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFollowcount = userInfo.followcount;
                }
                updateRegistration(0, userInfoFollowcount);
                if (userInfoFollowcount != null) {
                    userInfoFollowcountGet = userInfoFollowcount.get();
                }
                String fengUtilNumberFormatUserInfoFollowcount = FengUtil.numberFormat(userInfoFollowcountGet);
                stringFormatFocusTextAndroidStringFocusTextFengUtilNumberFormatUserInfoFollowcount = String.format(this.focusText.getResources().getString(2131231055), new Object[]{fengUtilNumberFormatUserInfoFollowcount});
            }
            if ((194 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSex = userInfo.sex;
                }
                updateRegistration(1, userInfoSex);
                if (userInfoSex != null) {
                    userInfoSexGet = userInfoSex.get();
                }
                boolean userInfoSexInt1 = userInfoSexGet == 1;
                if ((194 & dirtyFlags) != 0) {
                    if (userInfoSexInt1) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    } else {
                        dirtyFlags |= 4096;
                    }
                }
                if (userInfoSexInt1) {
                    userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = getDrawableFromResource(this.sexIcon, 2130838460);
                } else {
                    userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = getDrawableFromResource(this.sexIcon, 2130838459);
                }
            }
            if ((196 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFanscount = userInfo.fanscount;
                }
                updateRegistration(2, userInfoFanscount);
                if (userInfoFanscount != null) {
                    userInfoFanscountGet = userInfoFanscount.get();
                }
                String fengUtilNumberFormatUserInfoFanscount = FengUtil.numberFormat(userInfoFanscountGet);
                stringFormatFansTextAndroidStringFansTextFengUtilNumberFormatUserInfoFanscount = String.format(this.fansText.getResources().getString(2131231034), new Object[]{fengUtilNumberFormatUserInfoFanscount});
            }
            if ((200 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(3, userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((192 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((192 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.username, 2130837631) : null;
            }
            if ((208 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSnscount = userInfo.snscount;
                }
                updateRegistration(4, userInfoSnscount);
                if (userInfoSnscount != null) {
                    userInfoSnscountGet = userInfoSnscount.get();
                }
                String fengUtilNumberFormatUserInfoSnscount = FengUtil.numberFormat(userInfoSnscountGet);
                stringFormatCountAndroidStringPersonalhomeCountFengUtilNumberFormatUserInfoSnscount = String.format(this.count.getResources().getString(2131231373), new Object[]{fengUtilNumberFormatUserInfoSnscount});
            }
            if ((224 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSignature = userInfo.signature;
                }
                updateRegistration(5, userInfoSignature);
                if (userInfoSignature != null) {
                    userInfoSignatureGet = (String) userInfoSignature.get();
                }
                stringUtilIsEmptyUserInfoSignature = StringUtil.isEmpty(userInfoSignatureGet);
                if ((224 & dirtyFlags) != 0) {
                    if (stringUtilIsEmptyUserInfoSignature) {
                        dirtyFlags |= 2048;
                    } else {
                        dirtyFlags |= 1024;
                    }
                }
            }
        }
        if (!((1024 & dirtyFlags) == 0 || userInfo == null)) {
            userInfoGetFirstAuthenticatedInfo = userInfo.getFirstAuthenticatedInfo();
        }
        if ((224 & dirtyFlags) != 0) {
            stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = stringUtilIsEmptyUserInfoSignature ? this.signature.getResources().getString(2131231301) : userInfoGetFirstAuthenticatedInfo;
        }
        if ((208 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.count, stringFormatCountAndroidStringPersonalhomeCountFengUtilNumberFormatUserInfoSnscount);
        }
        if ((196 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.fansText, stringFormatFansTextAndroidStringFansTextFengUtilNumberFormatUserInfoFanscount);
        }
        if ((193 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.focusText, stringFormatFocusTextAndroidStringFocusTextFengUtilNumberFormatUserInfoFollowcount);
        }
        if ((194 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.sexIcon, userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot);
        }
        if ((224 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.signature, stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo);
        }
        if ((192 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.username, userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.username, userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
        }
        if ((200 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.username, userInfoNameGet);
        }
    }

    public static PersonalHomeHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PersonalHomeHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PersonalHomeHeaderLayoutBinding) DataBindingUtil.inflate(inflater, 2130903338, root, attachToRoot, bindingComponent);
    }

    public static PersonalHomeHeaderLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PersonalHomeHeaderLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903338, null, false), bindingComponent);
    }

    public static PersonalHomeHeaderLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PersonalHomeHeaderLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/personal_home_header_layout_0".equals(view.getTag())) {
            return new PersonalHomeHeaderLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
