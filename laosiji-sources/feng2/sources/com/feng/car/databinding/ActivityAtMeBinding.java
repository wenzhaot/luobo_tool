package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.feng.car.R;

public class ActivityAtMeBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = null;
    public final LinearLayout llAtFragmentContainer;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;

    public ActivityAtMeBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        this.llAtFragmentContainer = (LinearLayout) ViewDataBinding.mapBindings(bindingComponent, root, 1, sIncludes, sViewsWithIds)[0];
        this.llAtFragmentContainer.setTag(null);
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

    public static ActivityAtMeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAtMeBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAtMeBinding) DataBindingUtil.inflate(inflater, R.layout.activity_at_me, root, attachToRoot, bindingComponent);
    }

    public static ActivityAtMeBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAtMeBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_at_me, null, false), bindingComponent);
    }

    public static ActivityAtMeBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAtMeBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_at_me_0".equals(view.getTag())) {
            return new ActivityAtMeBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
