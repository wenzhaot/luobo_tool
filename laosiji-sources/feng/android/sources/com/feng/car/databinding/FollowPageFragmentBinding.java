package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FollowPageFragmentBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivMessage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final RelativeLayout tabLayout;
    public final RelativeLayout titleBar;
    public final TextView tvMessageCommentNum;
    public final ViewPager viewPage;

    static {
        sViewsWithIds.put(2131625034, 1);
        sViewsWithIds.put(2131625035, 2);
        sViewsWithIds.put(2131624232, 3);
        sViewsWithIds.put(2131624233, 4);
        sViewsWithIds.put(2131624550, 5);
    }

    public FollowPageFragmentBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.ivMessage = (ImageView) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tabLayout = (RelativeLayout) bindings[2];
        this.titleBar = (RelativeLayout) bindings[1];
        this.tvMessageCommentNum = (TextView) bindings[4];
        this.viewPage = (ViewPager) bindings[5];
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

    public static FollowPageFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FollowPageFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FollowPageFragmentBinding) DataBindingUtil.inflate(inflater, 2130903243, root, attachToRoot, bindingComponent);
    }

    public static FollowPageFragmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FollowPageFragmentBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903243, null, false), bindingComponent);
    }

    public static FollowPageFragmentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FollowPageFragmentBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/follow_page_fragment_0".equals(view.getTag())) {
            return new FollowPageFragmentBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
