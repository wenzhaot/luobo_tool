package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class AtUserLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout llSearch;
    private long mDirtyFlags = -1;
    private String mStr;
    private final LinearLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final LRecyclerView rvSearchUser;
    public final TextView tvSearch;

    static {
        sViewsWithIds.put(2131624851, 1);
        sViewsWithIds.put(2131624545, 2);
        sViewsWithIds.put(2131624280, 3);
        sViewsWithIds.put(2131624852, 4);
    }

    public AtUserLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.llSearch = (LinearLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[3];
        this.rvSearchUser = (LRecyclerView) bindings[4];
        this.tvSearch = (TextView) bindings[2];
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
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
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

    public static AtUserLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static AtUserLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (AtUserLayoutBinding) DataBindingUtil.inflate(inflater, 2130903166, root, attachToRoot, bindingComponent);
    }

    public static AtUserLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static AtUserLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903166, null, false), bindingComponent);
    }

    public static AtUserLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static AtUserLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/at_user_layout_0".equals(view.getTag())) {
            return new AtUserLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
