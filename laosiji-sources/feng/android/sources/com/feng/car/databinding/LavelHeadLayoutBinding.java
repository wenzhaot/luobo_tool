package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.feng.car.view.AutoFrescoDraweeView;

public class LavelHeadLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adProgram0;
    public final AutoFrescoDraweeView adProgram1;
    public final AutoFrescoDraweeView adProgram2;
    public final AutoFrescoDraweeView adProgram3;
    public final AutoFrescoDraweeView adProgram4;
    public final LinearLayout llProgram;
    private long mDirtyFlags = -1;
    private String mStr;
    private final LinearLayout mboundView0;

    static {
        sViewsWithIds.put(2131625210, 1);
        sViewsWithIds.put(2131625211, 2);
        sViewsWithIds.put(2131625212, 3);
        sViewsWithIds.put(2131625213, 4);
        sViewsWithIds.put(2131625214, 5);
        sViewsWithIds.put(2131625215, 6);
    }

    public LavelHeadLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.adProgram0 = (AutoFrescoDraweeView) bindings[2];
        this.adProgram1 = (AutoFrescoDraweeView) bindings[3];
        this.adProgram2 = (AutoFrescoDraweeView) bindings[4];
        this.adProgram3 = (AutoFrescoDraweeView) bindings[5];
        this.adProgram4 = (AutoFrescoDraweeView) bindings[6];
        this.llProgram = (LinearLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
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

    public static LavelHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static LavelHeadLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (LavelHeadLayoutBinding) DataBindingUtil.inflate(inflater, 2130903288, root, attachToRoot, bindingComponent);
    }

    public static LavelHeadLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static LavelHeadLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903288, null, false), bindingComponent);
    }

    public static LavelHeadLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LavelHeadLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/lavel_head_layout_0".equals(view.getTag())) {
            return new LavelHeadLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
