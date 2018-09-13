package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityFindFollowBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final RecyclerView lrvFindFollow;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final TextView tvFindFollowAll;
    public final TextView tvFindFollowTip;

    static {
        sViewsWithIds.put(2131624363, 1);
        sViewsWithIds.put(2131624364, 2);
        sViewsWithIds.put(2131624365, 3);
    }

    public ActivityFindFollowBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.lrvFindFollow = (RecyclerView) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvFindFollowAll = (TextView) bindings[2];
        this.tvFindFollowTip = (TextView) bindings[1];
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
            case 74:
                setUseless(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUseless(int Useless) {
        this.mUseless = Useless;
    }

    public int getUseless() {
        return this.mUseless;
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

    public static ActivityFindFollowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFindFollowBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityFindFollowBinding) DataBindingUtil.inflate(inflater, 2130903093, root, attachToRoot, bindingComponent);
    }

    public static ActivityFindFollowBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFindFollowBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903093, null, false), bindingComponent);
    }

    public static ActivityFindFollowBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityFindFollowBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_find_follow_0".equals(view.getTag())) {
            return new ActivityFindFollowBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
