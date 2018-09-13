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
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivityUploadPriceImageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout bottomLine;
    public final TextView confirm;
    public final AutoFrescoDraweeView invoiceImage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final TextView nextText;
    public final ImageView samplImage;
    public final TextView tips;
    public final RelativeLayout tipsLine;
    public final TextView uploadText;

    static {
        sViewsWithIds.put(2131624696, 1);
        sViewsWithIds.put(2131624288, 2);
        sViewsWithIds.put(2131624697, 3);
        sViewsWithIds.put(2131624698, 4);
        sViewsWithIds.put(2131624699, 5);
        sViewsWithIds.put(2131624700, 6);
        sViewsWithIds.put(2131624568, 7);
        sViewsWithIds.put(2131624294, 8);
    }

    public ActivityUploadPriceImageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.bottomLine = (LinearLayout) bindings[2];
        this.confirm = (TextView) bindings[8];
        this.invoiceImage = (AutoFrescoDraweeView) bindings[6];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nextText = (TextView) bindings[4];
        this.samplImage = (ImageView) bindings[5];
        this.tips = (TextView) bindings[1];
        this.tipsLine = (RelativeLayout) bindings[7];
        this.uploadText = (TextView) bindings[3];
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

    public static ActivityUploadPriceImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadPriceImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityUploadPriceImageBinding) DataBindingUtil.inflate(inflater, 2130903145, root, attachToRoot, bindingComponent);
    }

    public static ActivityUploadPriceImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadPriceImageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903145, null, false), bindingComponent);
    }

    public static ActivityUploadPriceImageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadPriceImageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_upload_price_image_0".equals(view.getTag())) {
            return new ActivityUploadPriceImageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
