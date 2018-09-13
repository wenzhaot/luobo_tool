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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class ChooseCarItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView ivImage;
    private long mDirtyFlags = -1;
    private String mStr;
    private final RelativeLayout mboundView0;
    public final RecyclerView rvCar;
    public final TextView tvCommentNum;
    public final TextView tvTime;
    public final TextView tvTopicName;

    static {
        sViewsWithIds.put(2131624438, 1);
        sViewsWithIds.put(2131624920, 2);
        sViewsWithIds.put(2131624921, 3);
        sViewsWithIds.put(2131624845, 4);
        sViewsWithIds.put(2131624922, 5);
    }

    public ChooseCarItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.ivImage = (AutoFrescoDraweeView) bindings[1];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvCar = (RecyclerView) bindings[5];
        this.tvCommentNum = (TextView) bindings[4];
        this.tvTime = (TextView) bindings[3];
        this.tvTopicName = (TextView) bindings[2];
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

    public static ChooseCarItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ChooseCarItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903185, root, attachToRoot, bindingComponent);
    }

    public static ChooseCarItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903185, null, false), bindingComponent);
    }

    public static ChooseCarItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ChooseCarItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/choose_car_item_layout_0".equals(view.getTag())) {
            return new ChooseCarItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
