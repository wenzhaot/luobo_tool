package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.feng.car.entity.ImageInfo;
import com.feng.car.view.BigImageLoadProgressView;
import com.feng.car.view.photoview.PhotoDraweeView;

public class ItemShowCarImageViewBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final PhotoDraweeView imageBig;
    public final BigImageLoadProgressView ivBigimageProgressView;
    private long mDirtyFlags = -1;
    private ImageInfo mImageInfo;
    private final RelativeLayout mboundView0;

    static {
        sViewsWithIds.put(2131625154, 1);
        sViewsWithIds.put(2131625155, 2);
    }

    public ItemShowCarImageViewBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.imageBig = (PhotoDraweeView) bindings[1];
        this.ivBigimageProgressView = (BigImageLoadProgressView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
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
            case 26:
                setImageInfo((ImageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setImageInfo(ImageInfo ImageInfo) {
        this.mImageInfo = ImageInfo;
    }

    public ImageInfo getImageInfo() {
        return this.mImageInfo;
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

    public static ItemShowCarImageViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemShowCarImageViewBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemShowCarImageViewBinding) DataBindingUtil.inflate(inflater, 2130903280, root, attachToRoot, bindingComponent);
    }

    public static ItemShowCarImageViewBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemShowCarImageViewBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903280, null, false), bindingComponent);
    }

    public static ItemShowCarImageViewBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemShowCarImageViewBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_show_car_image_view_0".equals(view.getTag())) {
            return new ItemShowCarImageViewBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
