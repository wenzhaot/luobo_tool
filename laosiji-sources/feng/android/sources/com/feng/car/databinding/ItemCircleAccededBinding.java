package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
import android.databinding.ObservableBoolean;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemCircleAccededBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdCircle;
    public final ImageView ivCircleAccede;
    public final ImageView ivCircleSelect;
    private long mDirtyFlags = -1;
    private CircleInfo mInfo;
    private Boolean mMIsShowSel;
    private final RelativeLayout mboundView0;
    public final TextView tvCircleContentCount;
    public final TextView tvCircleName;
    public final TextView tvCirclePartnerCount;

    static {
        sViewsWithIds.put(2131625132, 5);
        sViewsWithIds.put(2131625134, 6);
    }

    public ItemCircleAccededBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.afdCircle = (AutoFrescoDraweeView) bindings[5];
        this.ivCircleAccede = (ImageView) bindings[3];
        this.ivCircleAccede.setTag(null);
        this.ivCircleSelect = (ImageView) bindings[4];
        this.ivCircleSelect.setTag(null);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCircleContentCount = (TextView) bindings[2];
        this.tvCircleContentCount.setTag(null);
        this.tvCircleName = (TextView) bindings[6];
        this.tvCirclePartnerCount = (TextView) bindings[1];
        this.tvCirclePartnerCount.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16;
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
            case 28:
                setInfo((CircleInfo) variable);
                return true;
            case 34:
                setMIsShowSel((Boolean) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CircleInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CircleInfo getInfo() {
        return this.mInfo;
    }

    public void setMIsShowSel(Boolean MIsShowSel) {
        this.mMIsShowSel = MIsShowSel;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(34);
        super.requestRebind();
    }

    public Boolean getMIsShowSel() {
        return this.mMIsShowSel;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoIsfans((ObservableInt) object, fieldId);
            case 1:
                return onChangeInfoIslocalselect((ObservableBoolean) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoIsfans(ObservableInt InfoIsfans, int fieldId) {
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

    private boolean onChangeInfoIslocalselect(ObservableBoolean InfoIslocalselect, int fieldId) {
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
        int infoIsfansGet = 0;
        String stringFormatTvCircleContentCountAndroidStringCircleContentCountFengUtilNumberFormatInfoSnscount = null;
        int mIsShowSelViewVISIBLEViewGONE = 0;
        boolean infoIslocalselectGet = false;
        String stringFormatTvCirclePartnerCountAndroidStringCirclePartnerCountFengUtilNumberFormatInfoFanscount = null;
        int mIsShowSelViewGONEViewVISIBLE = 0;
        CircleInfo info = this.mInfo;
        Drawable infoIsfansInt1IvCircleAccedeAndroidDrawableFollowCancelSelectorIvCircleAccedeAndroidDrawableFollowSelector = null;
        ObservableInt infoIsfans = null;
        ObservableBoolean infoIslocalselect = null;
        Boolean mIsShowSel = this.mMIsShowSel;
        int infoSnscount = 0;
        Drawable infoIslocalselectIvCircleSelectAndroidDrawablePrivateSelectIvCircleSelectAndroidDrawablePrivateSelectNone = null;
        int infoFanscount = 0;
        if ((23 & dirtyFlags) != 0) {
            if ((21 & dirtyFlags) != 0) {
                if (info != null) {
                    infoIsfans = info.isfans;
                }
                updateRegistration(0, infoIsfans);
                if (infoIsfans != null) {
                    infoIsfansGet = infoIsfans.get();
                }
                boolean infoIsfansInt1 = infoIsfansGet == 1;
                if ((21 & dirtyFlags) != 0) {
                    if (infoIsfansInt1) {
                        dirtyFlags |= 1024;
                    } else {
                        dirtyFlags |= 512;
                    }
                }
                if (infoIsfansInt1) {
                    infoIsfansInt1IvCircleAccedeAndroidDrawableFollowCancelSelectorIvCircleAccedeAndroidDrawableFollowSelector = getDrawableFromResource(this.ivCircleAccede, 2130837872);
                } else {
                    infoIsfansInt1IvCircleAccedeAndroidDrawableFollowCancelSelectorIvCircleAccedeAndroidDrawableFollowSelector = getDrawableFromResource(this.ivCircleAccede, 2130837877);
                }
            }
            if ((22 & dirtyFlags) != 0) {
                if (info != null) {
                    infoIslocalselect = info.islocalselect;
                }
                updateRegistration(1, infoIslocalselect);
                if (infoIslocalselect != null) {
                    infoIslocalselectGet = infoIslocalselect.get();
                }
                if ((22 & dirtyFlags) != 0) {
                    if (infoIslocalselectGet) {
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
                if (infoIslocalselectGet) {
                    infoIslocalselectIvCircleSelectAndroidDrawablePrivateSelectIvCircleSelectAndroidDrawablePrivateSelectNone = getDrawableFromResource(this.ivCircleSelect, 2130838402);
                } else {
                    infoIslocalselectIvCircleSelectAndroidDrawablePrivateSelectIvCircleSelectAndroidDrawablePrivateSelectNone = getDrawableFromResource(this.ivCircleSelect, 2130838403);
                }
            }
            if ((20 & dirtyFlags) != 0) {
                if (info != null) {
                    infoSnscount = info.snscount;
                    infoFanscount = info.fanscount;
                }
                String fengUtilNumberFormatInfoSnscount = FengUtil.numberFormat(infoSnscount);
                String fengUtilNumberFormatInfoFanscount = FengUtil.numberFormat(infoFanscount);
                stringFormatTvCircleContentCountAndroidStringCircleContentCountFengUtilNumberFormatInfoSnscount = String.format(this.tvCircleContentCount.getResources().getString(2131230921), new Object[]{fengUtilNumberFormatInfoSnscount});
                stringFormatTvCirclePartnerCountAndroidStringCirclePartnerCountFengUtilNumberFormatInfoFanscount = String.format(this.tvCirclePartnerCount.getResources().getString(2131230924), new Object[]{fengUtilNumberFormatInfoFanscount});
            }
        }
        if ((24 & dirtyFlags) != 0) {
            boolean androidDatabindingDynamicUtilSafeUnboxMIsShowSel = DynamicUtil.safeUnbox(mIsShowSel);
            if ((24 & dirtyFlags) != 0) {
                if (androidDatabindingDynamicUtilSafeUnboxMIsShowSel) {
                    dirtyFlags = (dirtyFlags | 64) | 256;
                } else {
                    dirtyFlags = (dirtyFlags | 32) | 128;
                }
            }
            mIsShowSelViewVISIBLEViewGONE = androidDatabindingDynamicUtilSafeUnboxMIsShowSel ? 0 : 8;
            mIsShowSelViewGONEViewVISIBLE = androidDatabindingDynamicUtilSafeUnboxMIsShowSel ? 8 : 0;
        }
        if ((21 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.ivCircleAccede, infoIsfansInt1IvCircleAccedeAndroidDrawableFollowCancelSelectorIvCircleAccedeAndroidDrawableFollowSelector);
        }
        if ((24 & dirtyFlags) != 0) {
            this.ivCircleAccede.setVisibility(mIsShowSelViewGONEViewVISIBLE);
            this.ivCircleSelect.setVisibility(mIsShowSelViewVISIBLEViewGONE);
        }
        if ((22 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.ivCircleSelect, infoIslocalselectIvCircleSelectAndroidDrawablePrivateSelectIvCircleSelectAndroidDrawablePrivateSelectNone);
        }
        if ((20 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCircleContentCount, stringFormatTvCircleContentCountAndroidStringCircleContentCountFengUtilNumberFormatInfoSnscount);
            TextViewBindingAdapter.setText(this.tvCirclePartnerCount, stringFormatTvCirclePartnerCountAndroidStringCirclePartnerCountFengUtilNumberFormatInfoFanscount);
        }
    }

    public static ItemCircleAccededBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCircleAccededBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemCircleAccededBinding) DataBindingUtil.inflate(inflater, 2130903268, root, attachToRoot, bindingComponent);
    }

    public static ItemCircleAccededBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCircleAccededBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903268, null, false), bindingComponent);
    }

    public static ItemCircleAccededBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemCircleAccededBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_circle_acceded_0".equals(view.getTag())) {
            return new ItemCircleAccededBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
