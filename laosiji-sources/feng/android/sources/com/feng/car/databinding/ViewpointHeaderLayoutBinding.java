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
import com.feng.car.entity.choosecar.ChooseCarInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.ArticleRecommendView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.UserRelationView;
import com.feng.car.view.VoiceBoxView;
import com.feng.car.view.textview.AisenTextView;

public class ViewpointHeaderLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView detail;
    public final RelativeLayout llExposureRead;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private final LinearLayout mboundView0;
    private final TextView mboundView6;
    public final ArticleRecommendView recommendView;
    public final AisenTextView text;
    public final TextView time;
    public final TextView title;
    public final LinearLayout titleLine;
    public final TextView tvExposureNum;
    public final TextView tvReadNum;
    public final UserRelationView urvFianlpageTop;
    public final AutoFrescoDraweeView userImage;
    public final TextView userName;
    public final View vLine;
    public final VoiceBoxView vbvViewPoint;

    static {
        sViewsWithIds.put(2131624275, 7);
        sViewsWithIds.put(2131624753, 8);
        sViewsWithIds.put(2131624802, 9);
        sViewsWithIds.put(2131624803, 10);
        sViewsWithIds.put(2131624804, 11);
        sViewsWithIds.put(2131624805, 12);
        sViewsWithIds.put(2131624009, 13);
        sViewsWithIds.put(2131625552, 14);
        sViewsWithIds.put(2131624816, 15);
    }

    public ViewpointHeaderLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds);
        this.detail = (TextView) bindings[8];
        this.llExposureRead = (RelativeLayout) bindings[11];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView6 = (TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.recommendView = (ArticleRecommendView) bindings[15];
        this.text = (AisenTextView) bindings[13];
        this.time = (TextView) bindings[3];
        this.time.setTag(null);
        this.title = (TextView) bindings[1];
        this.title.setTag(null);
        this.titleLine = (LinearLayout) bindings[7];
        this.tvExposureNum = (TextView) bindings[12];
        this.tvReadNum = (TextView) bindings[5];
        this.tvReadNum.setTag(null);
        this.urvFianlpageTop = (UserRelationView) bindings[10];
        this.userImage = (AutoFrescoDraweeView) bindings[9];
        this.userName = (TextView) bindings[2];
        this.userName.setTag(null);
        this.vLine = (View) bindings[4];
        this.vLine.setTag(null);
        this.vbvViewPoint = (VoiceBoxView) bindings[14];
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
            this.mDirtyFlags |= 4;
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
                return onChangeMSnsInfoUserName((ObservableField) object, fieldId);
            case 1:
                return onChangeMSnsInfoPublishtime((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeMSnsInfoPublishtime(ObservableField<String> observableField, int fieldId) {
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
        SnsInfo mSnsInfo = this.mMSnsInfo;
        int mSnsInfoSnstype = 0;
        String mSnsInfoPublishtimeGet = null;
        String mSnsInfoDiscussinfoTitle = null;
        Drawable mSnsInfoUserGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        UserInfo mSnsInfoUser = null;
        ObservableField<String> mSnsInfoUserName = null;
        ObservableField<String> mSnsInfoPublishtime = null;
        int mSnsInfoSnstypeInt9ViewVISIBLEViewGONE = 0;
        ChooseCarInfo mSnsInfoDiscussinfo = null;
        boolean mSnsInfoUserGetIsFirstAuthenticated = false;
        String mSnsInfoUserNameGet = null;
        if ((15 & dirtyFlags) != 0) {
            if ((12 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoSnstype = mSnsInfo.snstype;
                    mSnsInfoDiscussinfo = mSnsInfo.discussinfo;
                }
                boolean mSnsInfoSnstypeInt9 = mSnsInfoSnstype == 9;
                if ((12 & dirtyFlags) != 0) {
                    if (mSnsInfoSnstypeInt9) {
                        dirtyFlags |= 128;
                    } else {
                        dirtyFlags |= 64;
                    }
                }
                if (mSnsInfoDiscussinfo != null) {
                    mSnsInfoDiscussinfoTitle = mSnsInfoDiscussinfo.title;
                }
                mSnsInfoSnstypeInt9ViewVISIBLEViewGONE = mSnsInfoSnstypeInt9 ? 0 : 8;
            }
            if ((13 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoUser = mSnsInfo.user;
                }
                if (mSnsInfoUser != null) {
                    mSnsInfoUserName = mSnsInfoUser.name;
                }
                updateRegistration(0, mSnsInfoUserName);
                if (mSnsInfoUserName != null) {
                    mSnsInfoUserNameGet = (String) mSnsInfoUserName.get();
                }
                if ((12 & dirtyFlags) != 0) {
                    if (mSnsInfoUser != null) {
                        mSnsInfoUserGetIsFirstAuthenticated = mSnsInfoUser.getIsFirstAuthenticated();
                    }
                    if ((12 & dirtyFlags) != 0) {
                        if (mSnsInfoUserGetIsFirstAuthenticated) {
                            dirtyFlags |= 32;
                        } else {
                            dirtyFlags |= 16;
                        }
                    }
                    mSnsInfoUserGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = mSnsInfoUserGetIsFirstAuthenticated ? getDrawableFromResource(this.userName, 2130837629) : null;
                }
            }
            if ((14 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoPublishtime = mSnsInfo.publishtime;
                }
                updateRegistration(1, mSnsInfoPublishtime);
                if (mSnsInfoPublishtime != null) {
                    mSnsInfoPublishtimeGet = (String) mSnsInfoPublishtime.get();
                }
            }
        }
        if ((12 & dirtyFlags) != 0) {
            this.mboundView6.setVisibility(mSnsInfoSnstypeInt9ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.title, mSnsInfoDiscussinfoTitle);
            this.tvReadNum.setVisibility(mSnsInfoSnstypeInt9ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setDrawableEnd(this.userName, mSnsInfoUserGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.userName, mSnsInfoUserGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            this.vLine.setVisibility(mSnsInfoSnstypeInt9ViewVISIBLEViewGONE);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.time, mSnsInfoPublishtimeGet);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.userName, mSnsInfoUserNameGet);
        }
    }

    public static ViewpointHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ViewpointHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ViewpointHeaderLayoutBinding) DataBindingUtil.inflate(inflater, 2130903428, root, attachToRoot, bindingComponent);
    }

    public static ViewpointHeaderLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ViewpointHeaderLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903428, null, false), bindingComponent);
    }

    public static ViewpointHeaderLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ViewpointHeaderLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/viewpoint_header_layout_0".equals(view.getTag())) {
            return new ViewpointHeaderLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
