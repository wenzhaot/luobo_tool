package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class AllprogramItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView detail;
    public final View divier;
    public final ImageView followImage;
    public final AutoFrescoDraweeView image;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final TextView name;

    static {
        sViewsWithIds.put(2131624155, 1);
        sViewsWithIds.put(2131624752, 2);
        sViewsWithIds.put(2131624750, 3);
        sViewsWithIds.put(2131624753, 4);
        sViewsWithIds.put(2131624751, 5);
    }

    public AllprogramItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.detail = (TextView) bindings[4];
        this.divier = (View) bindings[5];
        this.followImage = (ImageView) bindings[2];
        this.image = (AutoFrescoDraweeView) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (TextView) bindings[3];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 2;
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
            case 72:
                setUnwanted((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnwanted(Integer Unwanted) {
        this.mUnwanted = Unwanted;
    }

    public Integer getUnwanted() {
        return this.mUnwanted;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
    }

    public static AllprogramItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static AllprogramItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (AllprogramItemBinding) DataBindingUtil.inflate(inflater, 2130903154, root, attachToRoot, bindingComponent);
    }

    public static AllprogramItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static AllprogramItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903154, null, false), bindingComponent);
    }

    public static AllprogramItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static AllprogramItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/allprogram_item_0".equals(view.getTag())) {
            return new AllprogramItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
