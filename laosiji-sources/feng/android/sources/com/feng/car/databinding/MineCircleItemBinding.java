package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class MineCircleItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adCircle;
    public final ImageView ivRedcircle;
    private long mDirtyFlags = -1;
    private CircleInfo mInfo;
    public final RelativeLayout parent;
    public final TextView tvCircleName;

    static {
        sViewsWithIds.put(2131625283, 3);
    }

    public MineCircleItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.adCircle = (AutoFrescoDraweeView) bindings[3];
        this.ivRedcircle = (ImageView) bindings[1];
        this.ivRedcircle.setTag(null);
        this.parent = (RelativeLayout) bindings[0];
        this.parent.setTag(null);
        this.tvCircleName = (TextView) bindings[2];
        this.tvCircleName.setTag(null);
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
            case 28:
                setInfo((CircleInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CircleInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CircleInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoRedpoint((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoRedpoint(ObservableInt InfoRedpoint, int fieldId) {
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
        int infoRedpointGet = 0;
        String infoName = null;
        int infoRedpointInt0ViewGONEViewVISIBLE = 0;
        ObservableInt infoRedpoint = null;
        CircleInfo info = this.mInfo;
        if ((7 & dirtyFlags) != 0) {
            if (!((6 & dirtyFlags) == 0 || info == null)) {
                infoName = info.name;
            }
            if (info != null) {
                infoRedpoint = info.redpoint;
            }
            updateRegistration(0, infoRedpoint);
            if (infoRedpoint != null) {
                infoRedpointGet = infoRedpoint.get();
            }
            boolean infoRedpointInt0 = infoRedpointGet == 0;
            if ((7 & dirtyFlags) != 0) {
                if (infoRedpointInt0) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            infoRedpointInt0ViewGONEViewVISIBLE = infoRedpointInt0 ? 8 : 0;
        }
        if ((7 & dirtyFlags) != 0) {
            this.ivRedcircle.setVisibility(infoRedpointInt0ViewGONEViewVISIBLE);
        }
        if ((6 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCircleName, infoName);
        }
    }

    public static MineCircleItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static MineCircleItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (MineCircleItemBinding) DataBindingUtil.inflate(inflater, 2130903312, root, attachToRoot, bindingComponent);
    }

    public static MineCircleItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static MineCircleItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903312, null, false), bindingComponent);
    }

    public static MineCircleItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static MineCircleItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/mine_circle_item_0".equals(view.getTag())) {
            return new MineCircleItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
