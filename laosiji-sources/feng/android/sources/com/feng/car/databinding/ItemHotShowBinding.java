package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemHotShowBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdImage;
    public final RelativeLayout llParent;
    private long mDirtyFlags = -1;
    private HotShowInfo mMHotShowInfo;
    public final TextView tvHotName;
    public final TextView tvHotTitle;
    public final TextView tvProgramNum;

    static {
        sViewsWithIds.put(2131625141, 3);
        sViewsWithIds.put(2131625144, 4);
    }

    public ItemHotShowBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.afdImage = (AutoFrescoDraweeView) bindings[3];
        this.llParent = (RelativeLayout) bindings[0];
        this.llParent.setTag(null);
        this.tvHotName = (TextView) bindings[1];
        this.tvHotName.setTag(null);
        this.tvHotTitle = (TextView) bindings[2];
        this.tvHotTitle.setTag(null);
        this.tvProgramNum = (TextView) bindings[4];
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
            case 33:
                setMHotShowInfo((HotShowInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMHotShowInfo(HotShowInfo MHotShowInfo) {
        this.mMHotShowInfo = MHotShowInfo;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(33);
        super.requestRebind();
    }

    public HotShowInfo getMHotShowInfo() {
        return this.mMHotShowInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMHotShowInfoRedpoint((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMHotShowInfoRedpoint(ObservableInt MHotShowInfoRedpoint, int fieldId) {
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
        int mHotShowInfoRedpointGet = 0;
        HotShowInfo mHotShowInfo = this.mMHotShowInfo;
        String mHotShowInfoName = null;
        ObservableInt mHotShowInfoRedpoint = null;
        String mHotShowInfoTitle = null;
        Drawable mHotShowInfoRedpointInt1TvHotNameAndroidDrawableIconHomeMenuDotJavaLangObjectNull = null;
        if ((7 & dirtyFlags) != 0) {
            if (!((6 & dirtyFlags) == 0 || mHotShowInfo == null)) {
                mHotShowInfoName = mHotShowInfo.name;
                mHotShowInfoTitle = mHotShowInfo.title;
            }
            if (mHotShowInfo != null) {
                mHotShowInfoRedpoint = mHotShowInfo.redpoint;
            }
            updateRegistration(0, mHotShowInfoRedpoint);
            if (mHotShowInfoRedpoint != null) {
                mHotShowInfoRedpointGet = mHotShowInfoRedpoint.get();
            }
            boolean mHotShowInfoRedpointInt1 = mHotShowInfoRedpointGet == 1;
            if ((7 & dirtyFlags) != 0) {
                if (mHotShowInfoRedpointInt1) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            mHotShowInfoRedpointInt1TvHotNameAndroidDrawableIconHomeMenuDotJavaLangObjectNull = mHotShowInfoRedpointInt1 ? getDrawableFromResource(this.tvHotName, 2130838082) : null;
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableRight(this.tvHotName, mHotShowInfoRedpointInt1TvHotNameAndroidDrawableIconHomeMenuDotJavaLangObjectNull);
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvHotName, mHotShowInfoName);
            TextViewBindingAdapter.setText(this.tvHotTitle, mHotShowInfoTitle);
        }
    }

    public static ItemHotShowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemHotShowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemHotShowBinding) DataBindingUtil.inflate(inflater, 2130903272, root, attachToRoot, bindingComponent);
    }

    public static ItemHotShowBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemHotShowBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903272, null, false), bindingComponent);
    }

    public static ItemHotShowBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemHotShowBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_hot_show_0".equals(view.getTag())) {
            return new ItemHotShowBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
