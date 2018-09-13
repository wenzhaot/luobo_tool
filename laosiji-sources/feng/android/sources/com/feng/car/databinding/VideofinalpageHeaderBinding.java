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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.ArticleRecommendView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.UserRelationView;
import com.feng.car.view.textview.AisenTextView;

public class VideofinalpageHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AisenTextView describe;
    public final View divider;
    public final RelativeLayout llExposureRead;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private final TextView mboundView3;
    public final TextView publishTime;
    public final ArticleRecommendView recommendView;
    public final LinearLayout rlHeaderParent;
    public final AisenTextView title;
    public final TextView tvExposureNum;
    public final TextView tvReadNum;
    public final TextView tvUserName;
    public final UserRelationView urvFianlpageTop;
    public final AutoFrescoDraweeView userImage;
    public final View vLine;
    public final TextView viewCount;

    static {
        sViewsWithIds.put(2131625550, 6);
        sViewsWithIds.put(2131624802, 7);
        sViewsWithIds.put(2131624803, 8);
        sViewsWithIds.put(2131624804, 9);
        sViewsWithIds.put(2131624805, 10);
        sViewsWithIds.put(2131624473, 11);
        sViewsWithIds.put(2131624806, 12);
        sViewsWithIds.put(2131624816, 13);
        sViewsWithIds.put(2131624240, 14);
    }

    public VideofinalpageHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.describe = (AisenTextView) bindings[5];
        this.describe.setTag(null);
        this.divider = (View) bindings[14];
        this.llExposureRead = (RelativeLayout) bindings[9];
        this.mboundView3 = (TextView) bindings[3];
        this.mboundView3.setTag(null);
        this.publishTime = (TextView) bindings[1];
        this.publishTime.setTag(null);
        this.recommendView = (ArticleRecommendView) bindings[13];
        this.rlHeaderParent = (LinearLayout) bindings[0];
        this.rlHeaderParent.setTag(null);
        this.title = (AisenTextView) bindings[4];
        this.title.setTag(null);
        this.tvExposureNum = (TextView) bindings[10];
        this.tvReadNum = (TextView) bindings[12];
        this.tvUserName = (TextView) bindings[2];
        this.tvUserName.setTag(null);
        this.urvFianlpageTop = (UserRelationView) bindings[8];
        this.userImage = (AutoFrescoDraweeView) bindings[7];
        this.vLine = (View) bindings[11];
        this.viewCount = (TextView) bindings[6];
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
                return onChangeMSnsInfoTitle((ObservableField) object, fieldId);
            case 2:
                return onChangeMSnsInfoUserName((ObservableField) object, fieldId);
            case 3:
                return onChangeMSnsInfoPublishtime((ObservableField) object, fieldId);
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

    private boolean onChangeMSnsInfoTitle(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMSnsInfoPublishtime(ObservableField<String> observableField, int fieldId) {
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
        ObservableField<String> mSnsInfoTitle = null;
        String mSnsInfoPublishtimeGet = null;
        UserInfo mSnsInfoUser = null;
        ObservableField<String> mSnsInfoUserName = null;
        ObservableField<String> mSnsInfoPublishtime = null;
        int mSnsInfoDescriptionLength = 0;
        String mSnsInfoTitleGet = null;
        String mSnsInfoDescriptionGet = null;
        Drawable mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        boolean mSnsInfoUserGetIsFirstAuthenticated = false;
        String mSnsInfoUserNameGet = null;
        int mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE = 0;
        int mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE = 0;
        String mSnsInfoUserGetIsFirstAuthenticatedInfo = null;
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
                        dirtyFlags |= 2048;
                    } else {
                        dirtyFlags |= 1024;
                    }
                }
                mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE = mSnsInfoDescriptionLengthInt0 ? 0 : 8;
            }
            if ((50 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoTitle = mSnsInfo.title;
                }
                updateRegistration(1, mSnsInfoTitle);
                if (mSnsInfoTitle != null) {
                    mSnsInfoTitleGet = (String) mSnsInfoTitle.get();
                }
                if (mSnsInfoTitleGet != null) {
                    mSnsInfoTitleLength = mSnsInfoTitleGet.length();
                }
                boolean mSnsInfoTitleLengthInt0 = mSnsInfoTitleLength > 0;
                if ((50 & dirtyFlags) != 0) {
                    if (mSnsInfoTitleLengthInt0) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE = mSnsInfoTitleLengthInt0 ? 0 : 8;
            }
            if ((52 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoUser = mSnsInfo.user;
                }
                if (mSnsInfoUser != null) {
                    mSnsInfoUserName = mSnsInfoUser.name;
                }
                updateRegistration(2, mSnsInfoUserName);
                if (mSnsInfoUserName != null) {
                    mSnsInfoUserNameGet = (String) mSnsInfoUserName.get();
                }
                if ((48 & dirtyFlags) != 0) {
                    if (mSnsInfoUser != null) {
                        mSnsInfoUserGetIsFirstAuthenticated = mSnsInfoUser.getIsFirstAuthenticated();
                        mSnsInfoUserGetIsFirstAuthenticatedInfo = mSnsInfoUser.getIsFirstAuthenticatedInfo();
                    }
                    if ((48 & dirtyFlags) != 0) {
                        if (mSnsInfoUserGetIsFirstAuthenticated) {
                            dirtyFlags |= 128;
                        } else {
                            dirtyFlags |= 64;
                        }
                    }
                    mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = mSnsInfoUserGetIsFirstAuthenticated ? getDrawableFromResource(this.tvUserName, 2130837629) : null;
                }
            }
            if ((56 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoPublishtime = mSnsInfo.publishtime;
                }
                updateRegistration(3, mSnsInfoPublishtime);
                if (mSnsInfoPublishtime != null) {
                    mSnsInfoPublishtimeGet = (String) mSnsInfoPublishtime.get();
                }
            }
        }
        if ((49 & dirtyFlags) != 0) {
            this.describe.setVisibility(mSnsInfoDescriptionLengthInt0ViewVISIBLEViewGONE);
        }
        if ((48 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView3, mSnsInfoUserGetIsFirstAuthenticatedInfo);
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, mSnsInfoUserGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((56 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.publishTime, mSnsInfoPublishtimeGet);
        }
        if ((50 & dirtyFlags) != 0) {
            this.title.setVisibility(mSnsInfoTitleLengthInt0ViewVISIBLEViewGONE);
        }
        if ((52 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, mSnsInfoUserNameGet);
        }
    }

    public static VideofinalpageHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static VideofinalpageHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (VideofinalpageHeaderBinding) DataBindingUtil.inflate(inflater, 2130903426, root, attachToRoot, bindingComponent);
    }

    public static VideofinalpageHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static VideofinalpageHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903426, null, false), bindingComponent);
    }

    public static VideofinalpageHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static VideofinalpageHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/videofinalpage_header_0".equals(view.getTag())) {
            return new VideofinalpageHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
