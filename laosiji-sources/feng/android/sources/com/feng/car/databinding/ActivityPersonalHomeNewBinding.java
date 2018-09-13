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
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.StickyNavLayout;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class ActivityPersonalHomeNewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView backImage;
    public final LinearLayout bottomline;
    public final View divider;
    public final TextView fansMessage;
    public final AutoFrescoDraweeView headerBg;
    public final TabLayout idStickynavlayoutIndicator;
    public final RelativeLayout idStickynavlayoutTopview;
    public final ViewPager idStickynavlayoutViewpager;
    public final AutoFrescoDraweeView image;
    public final ImageView ivSearch;
    public final LinearLayout letterLine;
    public final TextView letterText;
    public final LinearLayout llFansParent;
    public final LinearLayout llFollowParent;
    public final LinearLayout llSnsParent;
    public final LinearLayout llUserInfo;
    private long mDirtyFlags = -1;
    private MessageCountInfo mMessageCountInfo;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    private final TextView mboundView4;
    private final TextView mboundView5;
    public final StickyNavLayout navLayoutContainer;
    public final ImageView optionImage;
    public final ProgressBar progressBar;
    public final ImageView relationIcon;
    public final LinearLayout relationLine;
    public final TextView relationText;
    public final RelativeLayout rlPersonalPictureContainer;
    public final LinearLayout rlTab;
    public final ImageView sexIcon;
    public final View titleBottomLine;
    public final RelativeLayout titlebar;
    public final TextView tvFansNum;
    public final TextView tvSignature;
    public final TextView tvUsername;
    public final TextView userName;
    public final View vTabLine;

    static {
        sViewsWithIds.put(2131624404, 9);
        sViewsWithIds.put(2131623969, 10);
        sViewsWithIds.put(2131624513, 11);
        sViewsWithIds.put(2131624514, 12);
        sViewsWithIds.put(2131624155, 13);
        sViewsWithIds.put(2131624515, 14);
        sViewsWithIds.put(2131624518, 15);
        sViewsWithIds.put(2131624519, 16);
        sViewsWithIds.put(2131624520, 17);
        sViewsWithIds.put(2131624240, 18);
        sViewsWithIds.put(2131624428, 19);
        sViewsWithIds.put(2131623967, 20);
        sViewsWithIds.put(2131624430, 21);
        sViewsWithIds.put(2131623970, 22);
        sViewsWithIds.put(2131624521, 23);
        sViewsWithIds.put(2131624418, 24);
        sViewsWithIds.put(2131624522, 25);
        sViewsWithIds.put(2131624420, 26);
        sViewsWithIds.put(2131624421, 27);
        sViewsWithIds.put(2131624523, 28);
        sViewsWithIds.put(2131624422, 29);
        sViewsWithIds.put(2131624423, 30);
        sViewsWithIds.put(2131624424, 31);
        sViewsWithIds.put(2131624425, 32);
        sViewsWithIds.put(2131624426, 33);
        sViewsWithIds.put(2131624427, 34);
    }

    public ActivityPersonalHomeNewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 6);
        Object[] bindings = mapBindings(bindingComponent, root, 35, sIncludes, sViewsWithIds);
        this.backImage = (ImageView) bindings[24];
        this.bottomline = (LinearLayout) bindings[29];
        this.divider = (View) bindings[18];
        this.fansMessage = (TextView) bindings[7];
        this.fansMessage.setTag(null);
        this.headerBg = (AutoFrescoDraweeView) bindings[12];
        this.idStickynavlayoutIndicator = (TabLayout) bindings[20];
        this.idStickynavlayoutTopview = (RelativeLayout) bindings[10];
        this.idStickynavlayoutViewpager = (ViewPager) bindings[22];
        this.image = (AutoFrescoDraweeView) bindings[13];
        this.ivSearch = (ImageView) bindings[25];
        this.letterLine = (LinearLayout) bindings[33];
        this.letterText = (TextView) bindings[34];
        this.llFansParent = (LinearLayout) bindings[17];
        this.llFollowParent = (LinearLayout) bindings[16];
        this.llSnsParent = (LinearLayout) bindings[15];
        this.llUserInfo = (LinearLayout) bindings[14];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView4 = (TextView) bindings[4];
        this.mboundView4.setTag(null);
        this.mboundView5 = (TextView) bindings[5];
        this.mboundView5.setTag(null);
        this.navLayoutContainer = (StickyNavLayout) bindings[9];
        this.optionImage = (ImageView) bindings[26];
        this.progressBar = (ProgressBar) bindings[27];
        this.relationIcon = (ImageView) bindings[31];
        this.relationLine = (LinearLayout) bindings[30];
        this.relationText = (TextView) bindings[32];
        this.rlPersonalPictureContainer = (RelativeLayout) bindings[11];
        this.rlTab = (LinearLayout) bindings[19];
        this.sexIcon = (ImageView) bindings[1];
        this.sexIcon.setTag(null);
        this.titleBottomLine = (View) bindings[28];
        this.titlebar = (RelativeLayout) bindings[23];
        this.tvFansNum = (TextView) bindings[6];
        this.tvFansNum.setTag(null);
        this.tvSignature = (TextView) bindings[3];
        this.tvSignature.setTag(null);
        this.tvUsername = (TextView) bindings[2];
        this.tvUsername.setTag(null);
        this.userName = (TextView) bindings[8];
        this.userName.setTag(null);
        this.vTabLine = (View) bindings[21];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 256;
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
            this.mDirtyFlags |= 64;
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
            this.mDirtyFlags |= 128;
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
        String fengUtilNumberFormat99MessageCountInfoFansCount = null;
        int userInfoSnscountGet = 0;
        String fengUtilNumberFormatUserInfoFollowcount = null;
        ObservableInt userInfoFollowcount = null;
        int messageCountInfoFansCount = 0;
        MessageCountInfo messageCountInfo = this.mMessageCountInfo;
        ObservableInt userInfoSex = null;
        boolean textUtilsIsEmptyUserInfoSignature = false;
        int messageCountInfoFansCountInt0ViewVISIBLEViewGONE = 0;
        String userInfoNameGet = null;
        ObservableInt userInfoFanscount = null;
        ObservableField<String> userInfoName = null;
        String userInfoSignatureGet = null;
        UserInfo userInfo = this.mUserInfo;
        String textUtilsIsEmptyUserInfoSignatureTvSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = null;
        String fengUtilNumberFormatUserInfoSnscount = null;
        int userInfoFanscountGet = 0;
        int userInfoSexGet = 0;
        boolean userInfoGetIsFirstAuthenticated = false;
        String userInfoGetFirstAuthenticatedInfo = null;
        int userInfoFollowcountGet = 0;
        ObservableInt userInfoSnscount = null;
        Drawable userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = null;
        Drawable userInfoGetIsFirstAuthenticatedTvUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = null;
        ObservableField<String> userInfoSignature = null;
        String fengUtilNumberFormatUserInfoFanscount = null;
        if ((320 & dirtyFlags) != 0) {
            if (messageCountInfo != null) {
                messageCountInfoFansCount = messageCountInfo.fansCount;
            }
            fengUtilNumberFormat99MessageCountInfoFansCount = FengUtil.numberFormat99(messageCountInfoFansCount);
            boolean messageCountInfoFansCountInt0 = messageCountInfoFansCount > 0;
            if ((320 & dirtyFlags) != 0) {
                if (messageCountInfoFansCountInt0) {
                    dirtyFlags |= 1024;
                } else {
                    dirtyFlags |= 512;
                }
            }
            messageCountInfoFansCountInt0ViewVISIBLEViewGONE = messageCountInfoFansCountInt0 ? 0 : 8;
        }
        if ((447 & dirtyFlags) != 0) {
            if ((385 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFollowcount = userInfo.followcount;
                }
                updateRegistration(0, userInfoFollowcount);
                if (userInfoFollowcount != null) {
                    userInfoFollowcountGet = userInfoFollowcount.get();
                }
                fengUtilNumberFormatUserInfoFollowcount = FengUtil.numberFormat(userInfoFollowcountGet);
            }
            if ((386 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSex = userInfo.sex;
                }
                updateRegistration(1, userInfoSex);
                if (userInfoSex != null) {
                    userInfoSexGet = userInfoSex.get();
                }
                boolean userInfoSexInt1 = userInfoSexGet == 1;
                if ((386 & dirtyFlags) != 0) {
                    if (userInfoSexInt1) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    }
                }
                if (userInfoSexInt1) {
                    userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = getDrawableFromResource(this.sexIcon, 2130838460);
                } else {
                    userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot = getDrawableFromResource(this.sexIcon, 2130838459);
                }
            }
            if ((388 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFanscount = userInfo.fanscount;
                }
                updateRegistration(2, userInfoFanscount);
                if (userInfoFanscount != null) {
                    userInfoFanscountGet = userInfoFanscount.get();
                }
                fengUtilNumberFormatUserInfoFanscount = FengUtil.numberFormat(userInfoFanscountGet);
            }
            if ((392 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(3, userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((384 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((384 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_CENTER;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    }
                }
                userInfoGetIsFirstAuthenticatedTvUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUsername, 2130837631) : null;
            }
            if ((400 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSnscount = userInfo.snscount;
                }
                updateRegistration(4, userInfoSnscount);
                if (userInfoSnscount != null) {
                    userInfoSnscountGet = userInfoSnscount.get();
                }
                fengUtilNumberFormatUserInfoSnscount = FengUtil.numberFormat(userInfoSnscountGet);
            }
            if ((416 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSignature = userInfo.signature;
                }
                updateRegistration(5, userInfoSignature);
                if (userInfoSignature != null) {
                    userInfoSignatureGet = (String) userInfoSignature.get();
                }
                textUtilsIsEmptyUserInfoSignature = TextUtils.isEmpty(userInfoSignatureGet);
                if ((416 & dirtyFlags) != 0) {
                    if (textUtilsIsEmptyUserInfoSignature) {
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
            }
        }
        if (!((2048 & dirtyFlags) == 0 || userInfo == null)) {
            userInfoGetFirstAuthenticatedInfo = userInfo.getFirstAuthenticatedInfo();
        }
        if ((416 & dirtyFlags) != 0) {
            textUtilsIsEmptyUserInfoSignatureTvSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = textUtilsIsEmptyUserInfoSignature ? this.tvSignature.getResources().getString(2131231301) : userInfoGetFirstAuthenticatedInfo;
        }
        if ((320 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.fansMessage, fengUtilNumberFormat99MessageCountInfoFansCount);
            this.fansMessage.setVisibility(messageCountInfoFansCountInt0ViewVISIBLEViewGONE);
        }
        if ((400 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView4, fengUtilNumberFormatUserInfoSnscount);
        }
        if ((385 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView5, fengUtilNumberFormatUserInfoFollowcount);
        }
        if ((386 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.sexIcon, userInfoSexInt1SexIconAndroidDrawableSexMaleDotSexIconAndroidDrawableSexFemaleDot);
        }
        if ((388 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvFansNum, fengUtilNumberFormatUserInfoFanscount);
        }
        if ((416 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvSignature, textUtilsIsEmptyUserInfoSignatureTvSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo);
        }
        if ((384 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvUsername, userInfoGetIsFirstAuthenticatedTvUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUsername, userInfoGetIsFirstAuthenticatedTvUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
        }
        if ((392 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUsername, userInfoNameGet);
            TextViewBindingAdapter.setText(this.userName, userInfoNameGet);
        }
    }

    public static ActivityPersonalHomeNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPersonalHomeNewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPersonalHomeNewBinding) DataBindingUtil.inflate(inflater, 2130903106, root, attachToRoot, bindingComponent);
    }

    public static ActivityPersonalHomeNewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPersonalHomeNewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903106, null, false), bindingComponent);
    }

    public static ActivityPersonalHomeNewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPersonalHomeNewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_personal_home_new_0".equals(view.getTag())) {
            return new ActivityPersonalHomeNewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
