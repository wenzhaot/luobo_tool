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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class PhotoVideoItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView ivCover;
    public final ImageView ivSelect;
    private long mDirtyFlags = -1;
    private String mUnuse;
    public final RelativeLayout rlParent;
    public final TextView tvName;
    public final TextView tvNum;

    static {
        sViewsWithIds.put(2131625357, 1);
        sViewsWithIds.put(2131624860, 2);
        sViewsWithIds.put(2131624369, 3);
        sViewsWithIds.put(2131625358, 4);
    }

    public PhotoVideoItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.ivCover = (AutoFrescoDraweeView) bindings[1];
        this.ivSelect = (ImageView) bindings[2];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.tvName = (TextView) bindings[3];
        this.tvNum = (TextView) bindings[4];
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
            case 71:
                setUnuse((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUnuse(String Unuse) {
        this.mUnuse = Unuse;
    }

    public String getUnuse() {
        return this.mUnuse;
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

    public static PhotoVideoItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoVideoItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PhotoVideoItemBinding) DataBindingUtil.inflate(inflater, 2130903342, root, attachToRoot, bindingComponent);
    }

    public static PhotoVideoItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoVideoItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903342, null, false), bindingComponent);
    }

    public static PhotoVideoItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PhotoVideoItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/photo_video_item_0".equals(view.getTag())) {
            return new PhotoVideoItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
