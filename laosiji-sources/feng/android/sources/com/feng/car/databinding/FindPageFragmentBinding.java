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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.recyclerview.EmptyView;
import com.feng.car.view.selectcar.IndexBar.widget.IndexBarWithLetter;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class FindPageFragmentBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final EmptyView emptyView;
    public final IndexBarWithLetter indexBar;
    public final ImageView ivMessage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final RelativeLayout rlTitleBar;
    public final TextView tvMessageCommentNum;
    public final TextView tvSearch;
    public final TextView tvSideBarHint;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624545, 2);
        sViewsWithIds.put(2131624232, 3);
        sViewsWithIds.put(2131624233, 4);
        sViewsWithIds.put(2131624231, 5);
        sViewsWithIds.put(2131624280, 6);
        sViewsWithIds.put(2131624333, 7);
        sViewsWithIds.put(2131624334, 8);
    }

    public FindPageFragmentBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.emptyView = (EmptyView) bindings[5];
        this.indexBar = (IndexBarWithLetter) bindings[7];
        this.ivMessage = (ImageView) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[6];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.tvMessageCommentNum = (TextView) bindings[4];
        this.tvSearch = (TextView) bindings[2];
        this.tvSideBarHint = (TextView) bindings[8];
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

    public static FindPageFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FindPageFragmentBinding) DataBindingUtil.inflate(inflater, 2130903241, root, attachToRoot, bindingComponent);
    }

    public static FindPageFragmentBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903241, null, false), bindingComponent);
    }

    public static FindPageFragmentBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FindPageFragmentBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/find_page_fragment_0".equals(view.getTag())) {
            return new FindPageFragmentBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
