package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.FixedViewPager;

public class ActivityShowNativeImageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView imageSizeText;
    public final RelativeLayout llParent;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LinearLayout originalLine;
    public final CheckBox originalTag;
    public final FixedViewPager viewpager;

    static {
        sViewsWithIds.put(2131624650, 1);
        sViewsWithIds.put(2131624599, 2);
        sViewsWithIds.put(2131624601, 3);
        sViewsWithIds.put(2131624602, 4);
    }

    public ActivityShowNativeImageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.imageSizeText = (TextView) bindings[4];
        this.llParent = (RelativeLayout) bindings[0];
        this.llParent.setTag(null);
        this.originalLine = (LinearLayout) bindings[2];
        this.originalTag = (CheckBox) bindings[3];
        this.viewpager = (FixedViewPager) bindings[1];
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

    public static ActivityShowNativeImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowNativeImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityShowNativeImageBinding) DataBindingUtil.inflate(inflater, 2130903141, root, attachToRoot, bindingComponent);
    }

    public static ActivityShowNativeImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowNativeImageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903141, null, false), bindingComponent);
    }

    public static ActivityShowNativeImageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShowNativeImageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_show_native_image_0".equals(view.getTag())) {
            return new ActivityShowNativeImageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
