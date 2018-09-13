package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
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
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.UserRelationView;
import com.feng.car.view.textview.AisenTextView;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class ArticleHeaderViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AisenTextView describe;
    public final RelativeLayout llExposureRead;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private final TextView mboundView2;
    public final LinearLayout rlHeaderParent;
    public final TextView title;
    public final TextView tvExposureNum;
    public final TextView tvReadNum;
    public final TextView tvUserName;
    public final UserRelationView urvFianlpageTop;
    public final AutoFrescoDraweeView userImage;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624802, 5);
        sViewsWithIds.put(2131624803, 6);
        sViewsWithIds.put(2131624804, 7);
        sViewsWithIds.put(2131624805, 8);
        sViewsWithIds.put(2131624473, 9);
        sViewsWithIds.put(2131624806, 10);
    }

    public ArticleHeaderViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.describe = (AisenTextView) bindings[4];
        this.describe.setTag(null);
        this.llExposureRead = (RelativeLayout) bindings[7];
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.rlHeaderParent = (LinearLayout) bindings[0];
        this.rlHeaderParent.setTag(null);
        this.title = (TextView) bindings[3];
        this.title.setTag(null);
        this.tvExposureNum = (TextView) bindings[8];
        this.tvReadNum = (TextView) bindings[10];
        this.tvUserName = (TextView) bindings[1];
        this.tvUserName.setTag(null);
        this.urvFianlpageTop = (UserRelationView) bindings[6];
        this.userImage = (AutoFrescoDraweeView) bindings[5];
        this.vLine = (View) bindings[9];
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
            case 36:
                setMSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMSnsInfo(SnsInfo MSnsInfo) {
        this.mMSnsInfo = MSnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(36);
        super.requestRebind();
    }

    public SnsInfo getMSnsInfo() {
        return this.mMSnsInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMSnsInfoDescription((ObservableField) object, fieldId);
            case 1:
                return onChangeMSnsInfoUserIsfollow((ObservableInt) object, fieldId);
            case 2:
                return onChangeMSnsInfoTitle((ObservableField) object, fieldId);
            case 3:
                return onChangeMSnsInfoUserName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMSnsInfoDescription(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMSnsInfoUserIsfollow(ObservableInt MSnsInfoUserIsfollow, int fieldId) {
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

    private boolean onChangeMSnsInfoTitle(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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
        SnsInfo mSnsInfo = this.mMSnsInfo;
        int mSnsInfoTitleLength = 0;
        ObservableField<String> mSnsInfoDescription = null;
        ObservableInt mSnsInfoUserIsfollow = null;
        int mSnsInfoUserIsfollowGet = 0;
        ObservableField<String> mSnsInfoTitle = null;
        boolean mSnsInfoUserGetIsMyInt1 = false;
        boolean mSnsInfoUserIsfollowInt0 = false;
        String mSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo = null;
        UserInfo mSnsInfoUser = null;
        int mSnsInfoUserGetIsMy = 0;
        ObservableField<String> mSnsInfoUserName = null;
        int mSnsInfoDescriptionLength = 0;
        String mSnsInfoTitleGet = null;
        String mSnsInfoDescriptionGet = null;
        Drawable mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        boolean mSnsInfoUserGetIsFirstAuthenticated = false;
        String mSnsInfoUserNameGet = null;
        int mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE = 0;
        String mSnsInfoUserGetIsMyInt1MSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo = null;
        int mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE = 0;
        String mSnsInfoUserGetIsFirstAuthenticatedInfo = null;
        String mboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfo = null;
        if ((63 & dirtyFlags) != 0) {
            if ((49 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoDescription = mSnsInfo.description;
                }
                updateRegistration(0, mSnsInfoDescription);
                if (mSnsInfoDescription != null) {
                    mSnsInfoDescriptionGet = (String) mSnsInfoDescription.get();
                }
                if (mSnsInfoDescriptionGet != null) {
                    mSnsInfoDescriptionLength = mSnsInfoDescriptionGet.length();
                }
                boolean mSnsInfoDescriptionLengthInt0 = mSnsInfoDescriptionLength > 0;
                if ((49 & dirtyFlags) != 0) {
                    if (mSnsInfoDescriptionLengthInt0) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    }
                }
                mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE = mSnsInfoDescriptionLengthInt0 ? 0 : 8;
            }
            if ((52 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoTitle = mSnsInfo.title;
                }
                updateRegistration(2, mSnsInfoTitle);
                if (mSnsInfoTitle != null) {
                    mSnsInfoTitleGet = (String) mSnsInfoTitle.get();
                }
                if (mSnsInfoTitleGet != null) {
                    mSnsInfoTitleLength = mSnsInfoTitleGet.length();
                }
                boolean mSnsInfoTitleLengthInt0 = mSnsInfoTitleLength > 0;
                if ((52 & dirtyFlags) != 0) {
                    if (mSnsInfoTitleLengthInt0) {
                        dirtyFlags |= 2048;
                    } else {
                        dirtyFlags |= 1024;
                    }
                }
                mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE = mSnsInfoTitleLengthInt0 ? 0 : 8;
            }
            if ((58 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoUser = mSnsInfo.user;
                }
                if ((50 & dirtyFlags) != 0) {
                    if (mSnsInfoUser != null) {
                        mSnsInfoUserGetIsMy = mSnsInfoUser.getIsMy();
                    }
                    mSnsInfoUserGetIsMyInt1 = mSnsInfoUserGetIsMy == 1;
                    if ((50 & dirtyFlags) != 0) {
                        if (mSnsInfoUserGetIsMyInt1) {
                            dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                        } else {
                            dirtyFlags |= 4096;
                        }
                    }
                }
                if ((56 & dirtyFlags) != 0) {
                    if (mSnsInfoUser != null) {
                        mSnsInfoUserName = mSnsInfoUser.name;
                    }
                    updateRegistration(3, mSnsInfoUserName);
                    if (mSnsInfoUserName != null) {
                        mSnsInfoUserNameGet = (String) mSnsInfoUserName.get();
                    }
                }
                if ((48 & dirtyFlags) != 0) {
                    if (mSnsInfoUser != null) {
                        mSnsInfoUserGetIsFirstAuthenticated = mSnsInfoUser.getIsFirstAuthenticated();
                    }
                    if ((48 & dirtyFlags) != 0) {
                        if (mSnsInfoUserGetIsFirstAuthenticated) {
                            dirtyFlags |= 512;
                        } else {
                            dirtyFlags |= 256;
                        }
                    }
                    mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = mSnsInfoUserGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUserName, 2130837629) : null;
                }
            }
        }
        if ((12288 & dirtyFlags) != 0) {
            if (mSnsInfo != null) {
                mSnsInfoUser = mSnsInfo.user;
            }
            if ((4096 & dirtyFlags) != 0) {
                if (mSnsInfoUser != null) {
                    mSnsInfoUserIsfollow = mSnsInfoUser.isfollow;
                }
                updateRegistration(1, mSnsInfoUserIsfollow);
                if (mSnsInfoUserIsfollow != null) {
                    mSnsInfoUserIsfollowGet = mSnsInfoUserIsfollow.get();
                }
                mSnsInfoUserIsfollowInt0 = mSnsInfoUserIsfollowGet != 0;
                if ((4096 & dirtyFlags) != 0) {
                    if (mSnsInfoUserIsfollowInt0) {
                        dirtyFlags |= 128;
                    } else {
                        dirtyFlags |= 64;
                    }
                }
            }
            if (!((IjkMediaMeta.AV_CH_TOP_FRONT_CENTER & dirtyFlags) == 0 || mSnsInfoUser == null)) {
                mSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUser.getIsFirstAuthenticatedInfo();
            }
        }
        if ((192 & dirtyFlags) != 0) {
            if (mSnsInfoUser != null) {
                mSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUser.getIsFirstAuthenticatedInfo();
            }
            if ((128 & dirtyFlags) != 0) {
                mboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfo = this.mboundView2.getResources().getString(2131231542) + mSnsInfoUserGetIsFirstAuthenticatedInfo;
            }
        }
        if ((4096 & dirtyFlags) != 0) {
            mSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUserIsfollowInt0 ? mboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfo : mSnsInfoUserGetIsFirstAuthenticatedInfo;
        }
        if ((50 & dirtyFlags) != 0) {
            if (mSnsInfoUserGetIsMyInt1) {
                mSnsInfoUserGetIsMyInt1MSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUserGetIsFirstAuthenticatedInfo;
            } else {
                mSnsInfoUserGetIsMyInt1MSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo;
            }
        }
        if ((49 & dirtyFlags) != 0) {
            this.describe.setVisibility(mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE);
        }
        if ((50 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, mSnsInfoUserGetIsMyInt1MSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserIsfollowInt0MboundView2AndroidStringSetFocusMSnsInfoUserGetIsFirstAuthenticatedInfoMSnsInfoUserGetIsFirstAuthenticatedInfo);
        }
        if ((52 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.title, mSnsInfoTitleGet);
            this.title.setVisibility(mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE);
        }
        if ((48 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((56 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, mSnsInfoUserNameGet);
        }
    }

    public static ArticleHeaderViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleHeaderViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleHeaderViewBinding) DataBindingUtil.inflate(inflater, 2130903158, root, attachToRoot, bindingComponent);
    }

    public static ArticleHeaderViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleHeaderViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903158, null, false), bindingComponent);
    }

    public static ArticleHeaderViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleHeaderViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_header_view_0".equals(view.getTag())) {
            return new ArticleHeaderViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
