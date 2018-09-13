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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class UploadProgressLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivCancel;
    private long mDirtyFlags = -1;
    private String mStr;
    public final ProgressBar progressbar;
    public final RelativeLayout rlProgress;
    public final TextView tvNum;
    public final TextView tvProgDescribe;

    static {
        sViewsWithIds.put(2131625524, 1);
        sViewsWithIds.put(2131625529, 2);
        sViewsWithIds.put(2131625358, 3);
        sViewsWithIds.put(2131625530, 4);
    }

    public UploadProgressLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.ivCancel = (ImageView) bindings[4];
        this.progressbar = (ProgressBar) bindings[1];
        this.rlProgress = (RelativeLayout) bindings[0];
        this.rlProgress.setTag(null);
        this.tvNum = (TextView) bindings[3];
        this.tvProgDescribe = (TextView) bindings[2];
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

    public static UploadProgressLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static UploadProgressLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (UploadProgressLayoutBinding) DataBindingUtil.inflate(inflater, 2130903418, root, attachToRoot, bindingComponent);
    }

    public static UploadProgressLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static UploadProgressLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903418, null, false), bindingComponent);
    }

    public static UploadProgressLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static UploadProgressLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/upload_progress_layout_0".equals(view.getTag())) {
            return new UploadProgressLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
