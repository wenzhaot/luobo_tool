package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.ComparisonView;

public class SingleConfigureActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView carxName;
    public final ImageView ivBack;
    public final ImageView ivClassify;
    public final ImageView ivRightImageTwo;
    private long mDirtyFlags = -1;
    private String mStr;
    private final RelativeLayout mboundView0;
    public final RecyclerView recyclerview;
    public final ComparisonView rightCvPk;
    public final RelativeLayout rlTitleBar;
    public final RecyclerView rvConfigClass;
    public final TextView title;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624228, 2);
        sViewsWithIds.put(2131624015, 3);
        sViewsWithIds.put(2131624297, 4);
        sViewsWithIds.put(2131625320, 5);
        sViewsWithIds.put(2131624911, 6);
        sViewsWithIds.put(2131624280, 7);
        sViewsWithIds.put(2131624339, 8);
        sViewsWithIds.put(2131624340, 9);
    }

    public SingleConfigureActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.carxName = (TextView) bindings[6];
        this.ivBack = (ImageView) bindings[2];
        this.ivClassify = (ImageView) bindings[8];
        this.ivRightImageTwo = (ImageView) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (RecyclerView) bindings[7];
        this.rightCvPk = (ComparisonView) bindings[4];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.rvConfigClass = (RecyclerView) bindings[9];
        this.title = (TextView) bindings[3];
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

    public static SingleConfigureActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigureActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SingleConfigureActivityBinding) DataBindingUtil.inflate(inflater, 2130903392, root, attachToRoot, bindingComponent);
    }

    public static SingleConfigureActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigureActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903392, null, false), bindingComponent);
    }

    public static SingleConfigureActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SingleConfigureActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/single_configure_activity_0".equals(view.getTag())) {
            return new SingleConfigureActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
