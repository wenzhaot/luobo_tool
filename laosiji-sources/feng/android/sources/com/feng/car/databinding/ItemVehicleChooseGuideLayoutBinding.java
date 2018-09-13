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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemVehicleChooseGuideLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdHead;
    public final ImageView ivChooseGuideDivider;
    public final LinearLayout llUserMsg;
    private long mDirtyFlags = -1;
    private SnsInfo mSnsInfo;
    private final LinearLayout mboundView0;
    private final TextView mboundView1;
    public final TextView tvChooseGuideCommentAmount;
    public final TextView tvChooseGuideName;

    static {
        sViewsWithIds.put(2131625109, 3);
        sViewsWithIds.put(2131625138, 4);
        sViewsWithIds.put(2131625167, 5);
        sViewsWithIds.put(2131625169, 6);
    }

    public ItemVehicleChooseGuideLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.afdHead = (AutoFrescoDraweeView) bindings[4];
        this.ivChooseGuideDivider = (ImageView) bindings[6];
        this.llUserMsg = (LinearLayout) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mboundView1 = (TextView) bindings[1];
        this.mboundView1.setTag(null);
        this.tvChooseGuideCommentAmount = (TextView) bindings[2];
        this.tvChooseGuideCommentAmount.setTag(null);
        this.tvChooseGuideName = (TextView) bindings[5];
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
            case 62:
                setSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSnsInfo(SnsInfo SnsInfo) {
        this.mSnsInfo = SnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    public SnsInfo getSnsInfo() {
        return this.mSnsInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeSnsInfoUserName((ObservableField) object, fieldId);
            case 1:
                return onChangeSnsInfoCommentcount((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeSnsInfoCommentcount(ObservableInt SnsInfoCommentcount, int fieldId) {
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
        String snsInfoUserNameGet = null;
        boolean snsInfoUserGetIsFirstAuthenticated = false;
        UserInfo snsInfoUser = null;
        Drawable snsInfoUserGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        int snsInfoCommentcountGet = 0;
        SnsInfo snsInfo = this.mSnsInfo;
        ObservableField<String> snsInfoUserName = null;
        ObservableInt snsInfoCommentcount = null;
        String stringValueOfSnsInfoCommentcount = null;
        if ((15 & dirtyFlags) != 0) {
            if ((13 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoUser = snsInfo.user;
                }
                if ((12 & dirtyFlags) != 0) {
                    if (snsInfoUser != null) {
                        snsInfoUserGetIsFirstAuthenticated = snsInfoUser.getIsFirstAuthenticated();
                    }
                    if ((12 & dirtyFlags) != 0) {
                        if (snsInfoUserGetIsFirstAuthenticated) {
                            dirtyFlags |= 32;
                        } else {
                            dirtyFlags |= 16;
                        }
                    }
                    snsInfoUserGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull = snsInfoUserGetIsFirstAuthenticated ? getDrawableFromResource(this.mboundView1, 2130837629) : null;
                }
                if (snsInfoUser != null) {
                    snsInfoUserName = snsInfoUser.name;
                }
                updateRegistration(0, snsInfoUserName);
                if (snsInfoUserName != null) {
                    snsInfoUserNameGet = (String) snsInfoUserName.get();
                }
            }
            if ((14 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoCommentcount = snsInfo.commentcount;
                }
                updateRegistration(1, snsInfoCommentcount);
                if (snsInfoCommentcount != null) {
                    snsInfoCommentcountGet = snsInfoCommentcount.get();
                }
                stringValueOfSnsInfoCommentcount = String.valueOf(snsInfoCommentcountGet);
            }
        }
        if ((12 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.mboundView1, snsInfoUserGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.mboundView1, snsInfoUserGetIsFirstAuthenticatedMboundView1AndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView1, snsInfoUserNameGet);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvChooseGuideCommentAmount, stringValueOfSnsInfoCommentcount);
        }
    }

    public static ItemVehicleChooseGuideLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleChooseGuideLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemVehicleChooseGuideLayoutBinding) DataBindingUtil.inflate(inflater, 2130903283, root, attachToRoot, bindingComponent);
    }

    public static ItemVehicleChooseGuideLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleChooseGuideLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903283, null, false), bindingComponent);
    }

    public static ItemVehicleChooseGuideLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemVehicleChooseGuideLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_vehicle_choose_guide_layout_0".equals(view.getTag())) {
            return new ItemVehicleChooseGuideLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
