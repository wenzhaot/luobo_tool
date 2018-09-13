package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.ComparisonView;

public class ActivityCarModelListBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivTitleLeft;
    private long mDirtyFlags = -1;
    private String mUseless;
    private final LinearLayout mboundView0;
    public final ComparisonView rightCvPk;
    public final RelativeLayout rlTitleBar;
    public final TabLayout tabCarModelList;
    public final TextView tvLocationCity;
    public final TextView tvTitle;
    public final ViewPager vpCarModelList;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624295, 2);
        sViewsWithIds.put(2131624296, 3);
        sViewsWithIds.put(2131624297, 4);
        sViewsWithIds.put(2131624298, 5);
        sViewsWithIds.put(2131624299, 6);
        sViewsWithIds.put(2131624300, 7);
    }

    public ActivityCarModelListBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.ivTitleLeft = (ImageView) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rightCvPk = (ComparisonView) bindings[4];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.tabCarModelList = (TabLayout) bindings[6];
        this.tvLocationCity = (TextView) bindings[5];
        this.tvTitle = (TextView) bindings[3];
        this.vpCarModelList = (ViewPager) bindings[7];
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
            case 74:
                setUseless((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUseless(String Useless) {
        this.mUseless = Useless;
    }

    public String getUseless() {
        return this.mUseless;
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

    public static ActivityCarModelListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarModelListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityCarModelListBinding) DataBindingUtil.inflate(inflater, 2130903079, root, attachToRoot, bindingComponent);
    }

    public static ActivityCarModelListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarModelListBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903079, null, false), bindingComponent);
    }

    public static ActivityCarModelListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCarModelListBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_car_model_list_0".equals(view.getTag())) {
            return new ActivityCarModelListBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
