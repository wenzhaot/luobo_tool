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

public class FollowedCircleItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView backImage;
    public final AutoFrescoDraweeView circleImage;
    public final RelativeLayout imageLine;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final ImageView markImage;
    private final RelativeLayout mboundView0;
    public final TextView name;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624473, 1);
        sViewsWithIds.put(2131625036, 2);
        sViewsWithIds.put(2131624418, 3);
        sViewsWithIds.put(2131625037, 4);
        sViewsWithIds.put(2131625038, 5);
        sViewsWithIds.put(2131624750, 6);
    }

    public FollowedCircleItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.backImage = (ImageView) bindings[3];
        this.circleImage = (AutoFrescoDraweeView) bindings[4];
        this.imageLine = (RelativeLayout) bindings[2];
        this.markImage = (ImageView) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (TextView) bindings[6];
        this.vLine = (View) bindings[1];
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

    public static FollowedCircleItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static FollowedCircleItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (FollowedCircleItemBinding) DataBindingUtil.inflate(inflater, 2130903244, root, attachToRoot, bindingComponent);
    }

    public static FollowedCircleItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static FollowedCircleItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903244, null, false), bindingComponent);
    }

    public static FollowedCircleItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static FollowedCircleItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/followed_circle_item_0".equals(view.getTag())) {
            return new FollowedCircleItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
