package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityPriceConditionBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView confirm;
    public final ImageView ivBack;
    public final ListView listView;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final EditText maxPrice;
    private final LinearLayout mboundView0;
    public final EditText minPrice;
    public final RadioButton rbGuidePrice;
    public final RadioButton rbOwnerPrice;
    public final RadioGroup rgPrice;
    public final RelativeLayout rlTitleBar;
    public final TextView titleText;
    public final TextView tvUnlimitedCondition;

    static {
        sViewsWithIds.put(2131624215, 1);
        sViewsWithIds.put(2131624530, 2);
        sViewsWithIds.put(2131624228, 3);
        sViewsWithIds.put(2131624531, 4);
        sViewsWithIds.put(2131624532, 5);
        sViewsWithIds.put(2131624533, 6);
        sViewsWithIds.put(2131624534, 7);
        sViewsWithIds.put(2131624535, 8);
        sViewsWithIds.put(2131624536, 9);
        sViewsWithIds.put(2131624294, 10);
        sViewsWithIds.put(2131624537, 11);
    }

    public ActivityPriceConditionBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.confirm = (TextView) bindings[10];
        this.ivBack = (ImageView) bindings[3];
        this.listView = (ListView) bindings[11];
        this.maxPrice = (EditText) bindings[9];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.minPrice = (EditText) bindings[8];
        this.rbGuidePrice = (RadioButton) bindings[6];
        this.rbOwnerPrice = (RadioButton) bindings[7];
        this.rgPrice = (RadioGroup) bindings[5];
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.titleText = (TextView) bindings[2];
        this.tvUnlimitedCondition = (TextView) bindings[4];
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

    public static ActivityPriceConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceConditionBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPriceConditionBinding) DataBindingUtil.inflate(inflater, 2130903111, root, attachToRoot, bindingComponent);
    }

    public static ActivityPriceConditionBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceConditionBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903111, null, false), bindingComponent);
    }

    public static ActivityPriceConditionBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPriceConditionBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_price_condition_0".equals(view.getTag())) {
            return new ActivityPriceConditionBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
