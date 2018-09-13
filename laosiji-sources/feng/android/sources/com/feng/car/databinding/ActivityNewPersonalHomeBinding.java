package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.StickyNavLayout;
import com.feng.library.utils.StringUtil;

public class ActivityNewPersonalHomeBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView avBlurBg;
    public final AutoFrescoDraweeView avHeadImage;
    public final ImageView backImage;
    public final LinearLayout bottomline;
    public final TextView fansMessage;
    public final TabLayout idStickynavlayoutIndicator;
    public final RelativeLayout idStickynavlayoutTopview;
    public final ViewPager idStickynavlayoutViewpager;
    public final LinearLayout letterLine;
    public final TextView letterText;
    public final LinearLayout llAttention;
    public final LinearLayout llContent;
    public final RelativeLayout llFans;
    public final LinearLayout llFansNum;
    public final LinearLayout llMenu;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    public final StickyNavLayout navLayoutContainer;
    public final ImageView optionImage;
    public final ProgressBar progressBar;
    public final ImageView relationIcon;
    public final LinearLayout relationLine;
    public final TextView relationText;
    public final RelativeLayout rlTitleBar;
    public final TextView signature;
    public final TextView tvArticleNum;
    public final TextView tvAttentionNum;
    public final TextView tvFansNum;
    public final TextView userName;
    public final TextView username;

    static {
        sViewsWithIds.put(2131624404, 6);
        sViewsWithIds.put(2131623969, 7);
        sViewsWithIds.put(2131624405, 8);
        sViewsWithIds.put(2131624406, 9);
        sViewsWithIds.put(2131624409, 10);
        sViewsWithIds.put(2131624410, 11);
        sViewsWithIds.put(2131624411, 12);
        sViewsWithIds.put(2131624412, 13);
        sViewsWithIds.put(2131624414, 14);
        sViewsWithIds.put(2131624415, 15);
        sViewsWithIds.put(2131624417, 16);
        sViewsWithIds.put(2131623967, 17);
        sViewsWithIds.put(2131623970, 18);
        sViewsWithIds.put(2131624215, 19);
        sViewsWithIds.put(2131624418, 20);
        sViewsWithIds.put(2131624420, 21);
        sViewsWithIds.put(2131624421, 22);
        sViewsWithIds.put(2131624422, 23);
        sViewsWithIds.put(2131624423, 24);
        sViewsWithIds.put(2131624424, 25);
        sViewsWithIds.put(2131624425, 26);
        sViewsWithIds.put(2131624426, 27);
        sViewsWithIds.put(2131624427, 28);
    }

    public ActivityNewPersonalHomeBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 29, sIncludes, sViewsWithIds);
        this.avBlurBg = (AutoFrescoDraweeView) bindings[8];
        this.avHeadImage = (AutoFrescoDraweeView) bindings[9];
        this.backImage = (ImageView) bindings[20];
        this.bottomline = (LinearLayout) bindings[23];
        this.fansMessage = (TextView) bindings[16];
        this.idStickynavlayoutIndicator = (TabLayout) bindings[17];
        this.idStickynavlayoutTopview = (RelativeLayout) bindings[7];
        this.idStickynavlayoutViewpager = (ViewPager) bindings[18];
        this.letterLine = (LinearLayout) bindings[27];
        this.letterText = (TextView) bindings[28];
        this.llAttention = (LinearLayout) bindings[13];
        this.llContent = (LinearLayout) bindings[11];
        this.llFans = (RelativeLayout) bindings[14];
        this.llFansNum = (LinearLayout) bindings[15];
        this.llMenu = (LinearLayout) bindings[10];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.navLayoutContainer = (StickyNavLayout) bindings[6];
        this.optionImage = (ImageView) bindings[21];
        this.progressBar = (ProgressBar) bindings[22];
        this.relationIcon = (ImageView) bindings[25];
        this.relationLine = (LinearLayout) bindings[24];
        this.relationText = (TextView) bindings[26];
        this.rlTitleBar = (RelativeLayout) bindings[19];
        this.signature = (TextView) bindings[2];
        this.signature.setTag(null);
        this.tvArticleNum = (TextView) bindings[12];
        this.tvAttentionNum = (TextView) bindings[3];
        this.tvAttentionNum.setTag(null);
        this.tvFansNum = (TextView) bindings[4];
        this.tvFansNum.setTag(null);
        this.userName = (TextView) bindings[5];
        this.userName.setTag(null);
        this.username = (TextView) bindings[1];
        this.username.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32;
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
            this.mDirtyFlags |= 16;
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
                return onChangeUserInfoFanscount((ObservableInt) object, fieldId);
            case 2:
                return onChangeUserInfoName((ObservableField) object, fieldId);
            case 3:
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

    private boolean onChangeUserInfoFanscount(ObservableInt UserInfoFanscount, int fieldId) {
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

    private boolean onChangeUserInfoName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeUserInfoSignature(ObservableField<String> observableField, int fieldId) {
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

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String fengUtilNumberFormatUserInfoFollowcount = null;
        ObservableInt userInfoFollowcount = null;
        boolean stringUtilIsEmptyUserInfoSignature = false;
        String userInfoNameGet = null;
        ObservableInt userInfoFanscount = null;
        ObservableField<String> userInfoName = null;
        String userInfoSignatureGet = null;
        UserInfo userInfo = this.mUserInfo;
        int userInfoFanscountGet = 0;
        Drawable userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = null;
        boolean userInfoGetIsFirstAuthenticated = false;
        String userInfoGetFirstAuthenticatedInfo = null;
        String stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = null;
        int userInfoFollowcountGet = 0;
        ObservableField<String> userInfoSignature = null;
        String fengUtilNumberFormatUserInfoFanscount = null;
        if ((63 & dirtyFlags) != 0) {
            if ((49 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFollowcount = userInfo.followcount;
                }
                updateRegistration(0, userInfoFollowcount);
                if (userInfoFollowcount != null) {
                    userInfoFollowcountGet = userInfoFollowcount.get();
                }
                fengUtilNumberFormatUserInfoFollowcount = FengUtil.numberFormat(userInfoFollowcountGet);
            }
            if ((50 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoFanscount = userInfo.fanscount;
                }
                updateRegistration(1, userInfoFanscount);
                if (userInfoFanscount != null) {
                    userInfoFanscountGet = userInfoFanscount.get();
                }
                fengUtilNumberFormatUserInfoFanscount = FengUtil.numberFormat(userInfoFanscountGet);
            }
            if ((52 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(2, userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((48 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((48 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 128;
                    } else {
                        dirtyFlags |= 64;
                    }
                }
                userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.username, 2130837631) : null;
            }
            if ((56 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoSignature = userInfo.signature;
                }
                updateRegistration(3, userInfoSignature);
                if (userInfoSignature != null) {
                    userInfoSignatureGet = (String) userInfoSignature.get();
                }
                stringUtilIsEmptyUserInfoSignature = StringUtil.isEmpty(userInfoSignatureGet);
                if ((56 & dirtyFlags) != 0) {
                    if (stringUtilIsEmptyUserInfoSignature) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
            }
        }
        if (!((256 & dirtyFlags) == 0 || userInfo == null)) {
            userInfoGetFirstAuthenticatedInfo = userInfo.getFirstAuthenticatedInfo();
        }
        if ((56 & dirtyFlags) != 0) {
            stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo = stringUtilIsEmptyUserInfoSignature ? this.signature.getResources().getString(2131231301) : userInfoGetFirstAuthenticatedInfo;
        }
        if ((56 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.signature, stringUtilIsEmptyUserInfoSignatureSignatureAndroidStringNoSignatureTipsUserInfoGetFirstAuthenticatedInfo);
        }
        if ((49 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvAttentionNum, fengUtilNumberFormatUserInfoFollowcount);
        }
        if ((50 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvFansNum, fengUtilNumberFormatUserInfoFanscount);
        }
        if ((52 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.userName, userInfoNameGet);
            TextViewBindingAdapter.setText(this.username, userInfoNameGet);
        }
        if ((48 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.username, userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.username, userInfoGetIsFirstAuthenticatedUsernameAndroidDrawableAuthoritiesBigSelectorJavaLangObjectNull);
        }
    }

    public static ActivityNewPersonalHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewPersonalHomeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityNewPersonalHomeBinding) DataBindingUtil.inflate(inflater, 2130903101, root, attachToRoot, bindingComponent);
    }

    public static ActivityNewPersonalHomeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewPersonalHomeBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903101, null, false), bindingComponent);
    }

    public static ActivityNewPersonalHomeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityNewPersonalHomeBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_new_personal_home_0".equals(view.getTag())) {
            return new ActivityNewPersonalHomeBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
