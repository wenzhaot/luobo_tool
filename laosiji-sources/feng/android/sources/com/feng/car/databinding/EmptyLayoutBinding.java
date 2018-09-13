package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.jdsjlzx.R;

public class EmptyLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btnShowsAll;
    public final TextView button;
    public final ImageView emptyImage;
    public final TextView emptyText;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LinearLayout parentLine;

    static {
        sViewsWithIds.put(2131625023, 1);
        sViewsWithIds.put(2131624809, 2);
        sViewsWithIds.put(2131624928, 3);
        sViewsWithIds.put(R.id.btn_shows_all, 4);
    }

    public EmptyLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.btnShowsAll = (Button) bindings[4];
        this.button = (TextView) bindings[3];
        this.emptyImage = (ImageView) bindings[1];
        this.emptyText = (TextView) bindings[2];
        this.parentLine = (LinearLayout) bindings[0];
        this.parentLine.setTag(null);
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

    public static EmptyLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static EmptyLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (EmptyLayoutBinding) DataBindingUtil.inflate(inflater, 2130903239, root, attachToRoot, bindingComponent);
    }

    public static EmptyLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static EmptyLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903239, null, false), bindingComponent);
    }

    public static EmptyLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static EmptyLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/empty_layout_0".equals(view.getTag())) {
            return new EmptyLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
