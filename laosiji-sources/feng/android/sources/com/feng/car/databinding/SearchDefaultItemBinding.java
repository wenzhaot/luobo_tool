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
import com.feng.car.entity.search.SearchItem;

public class SearchDefaultItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView clearHistory;
    public final RelativeLayout defaultLine;
    public final ImageView ivDel;
    public final ImageView ivType;
    private long mDirtyFlags = -1;
    private SearchItem mSearchItem;
    private final LinearLayout mboundView0;
    public final TextView tvContent;

    static {
        sViewsWithIds.put(2131624856, 1);
        sViewsWithIds.put(2131625465, 2);
        sViewsWithIds.put(2131624859, 3);
        sViewsWithIds.put(2131625396, 4);
        sViewsWithIds.put(2131625466, 5);
    }

    public SearchDefaultItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.clearHistory = (TextView) bindings[5];
        this.defaultLine = (RelativeLayout) bindings[1];
        this.ivDel = (ImageView) bindings[4];
        this.ivType = (ImageView) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvContent = (TextView) bindings[3];
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
            case 51:
                setSearchItem((SearchItem) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSearchItem(SearchItem SearchItem) {
        this.mSearchItem = SearchItem;
    }

    public SearchItem getSearchItem() {
        return this.mSearchItem;
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

    public static SearchDefaultItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SearchDefaultItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SearchDefaultItemBinding) DataBindingUtil.inflate(inflater, 2130903376, root, attachToRoot, bindingComponent);
    }

    public static SearchDefaultItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SearchDefaultItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903376, null, false), bindingComponent);
    }

    public static SearchDefaultItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SearchDefaultItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/search_default_item_0".equals(view.getTag())) {
            return new SearchDefaultItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
