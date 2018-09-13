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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.recyclerview.EmptyView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivitySearchcarResultBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final CheckBox cbOptional;
    public final EmptyView emptyView;
    public final ImageView ivBack;
    public final ImageView ivShade;
    public final LinearLayout llBrand;
    public final LinearLayout llLevel;
    public final LinearLayout llMore;
    public final LinearLayout llPrice;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final LinearLayout mboundView0;
    public final LRecyclerView recyclerview;
    public final RelativeLayout rlTitleBar;
    public final RecyclerView rvCondition;
    public final TextView tvBrandText;
    public final TextView tvCityName;
    public final TextView tvLevelText;
    public final TextView tvMoreText;
    public final TextView tvPriceText;

    static {
        sViewsWithIds.put(2131624231, 1);
        sViewsWithIds.put(2131624215, 2);
        sViewsWithIds.put(2131624228, 3);
        sViewsWithIds.put(2131624547, 4);
        sViewsWithIds.put(2131624581, 5);
        sViewsWithIds.put(2131624582, 6);
        sViewsWithIds.put(2131624452, 7);
        sViewsWithIds.put(2131624583, 8);
        sViewsWithIds.put(2131624584, 9);
        sViewsWithIds.put(2131624585, 10);
        sViewsWithIds.put(2131624586, 11);
        sViewsWithIds.put(2131624587, 12);
        sViewsWithIds.put(2131624588, 13);
        sViewsWithIds.put(2131624590, 14);
        sViewsWithIds.put(2131624589, 15);
        sViewsWithIds.put(2131624280, 16);
    }

    public ActivitySearchcarResultBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds);
        this.cbOptional = (CheckBox) bindings[15];
        this.emptyView = (EmptyView) bindings[1];
        this.ivBack = (ImageView) bindings[3];
        this.ivShade = (ImageView) bindings[14];
        this.llBrand = (LinearLayout) bindings[5];
        this.llLevel = (LinearLayout) bindings[9];
        this.llMore = (LinearLayout) bindings[11];
        this.llPrice = (LinearLayout) bindings[7];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.recyclerview = (LRecyclerView) bindings[16];
        this.rlTitleBar = (RelativeLayout) bindings[2];
        this.rvCondition = (RecyclerView) bindings[13];
        this.tvBrandText = (TextView) bindings[6];
        this.tvCityName = (TextView) bindings[4];
        this.tvLevelText = (TextView) bindings[10];
        this.tvMoreText = (TextView) bindings[12];
        this.tvPriceText = (TextView) bindings[8];
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
                setUseless(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUseless(int Useless) {
        this.mUseless = Useless;
    }

    public int getUseless() {
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

    public static ActivitySearchcarResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchcarResultBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySearchcarResultBinding) DataBindingUtil.inflate(inflater, 2130903128, root, attachToRoot, bindingComponent);
    }

    public static ActivitySearchcarResultBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchcarResultBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903128, null, false), bindingComponent);
    }

    public static ActivitySearchcarResultBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySearchcarResultBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_searchcar_result_0".equals(view.getTag())) {
            return new ActivitySearchcarResultBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
