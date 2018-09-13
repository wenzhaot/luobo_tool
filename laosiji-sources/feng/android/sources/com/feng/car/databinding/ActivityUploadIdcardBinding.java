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
import android.widget.RelativeLayout;
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivityUploadIdcardBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btFinish;
    public final AutoFrescoDraweeView image1;
    public final AutoFrescoDraweeView image2;
    public final AutoFrescoDraweeView image3;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;

    static {
        sViewsWithIds.put(2131624693, 1);
        sViewsWithIds.put(2131624694, 2);
        sViewsWithIds.put(2131624695, 3);
        sViewsWithIds.put(2131624692, 4);
    }

    public ActivityUploadIdcardBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.btFinish = (Button) bindings[4];
        this.image1 = (AutoFrescoDraweeView) bindings[1];
        this.image2 = (AutoFrescoDraweeView) bindings[2];
        this.image3 = (AutoFrescoDraweeView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
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
        return false;
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

    public static ActivityUploadIdcardBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadIdcardBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityUploadIdcardBinding) DataBindingUtil.inflate(inflater, 2130903144, root, attachToRoot, bindingComponent);
    }

    public static ActivityUploadIdcardBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadIdcardBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903144, null, false), bindingComponent);
    }

    public static ActivityUploadIdcardBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityUploadIdcardBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_upload_idcard_0".equals(view.getTag())) {
            return new ActivityUploadIdcardBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
