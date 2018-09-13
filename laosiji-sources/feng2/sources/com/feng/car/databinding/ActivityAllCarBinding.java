package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.feng.car.R;
import com.feng.car.view.ComparisonView;
import com.feng.car.view.recyclerview.EmptyView;

public class ActivityAllCarBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ComparisonView cvPk;
    public final EmptyView emptyView;
    public final ImageView ivBack;
    private long mDirtyFlags = -1;
    private int mUseless;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlTitleBar;
    public final TabLayout tabAllCar;
    public final ViewPager vpAllCar;

    static {
        sViewsWithIds.put(R.id.rl_title_bar, 1);
        sViewsWithIds.put(R.id.tab_allCar, 2);
        sViewsWithIds.put(R.id.iv_back, 3);
        sViewsWithIds.put(R.id.cv_pk, 4);
        sViewsWithIds.put(R.id.vp_allCar, 5);
        sViewsWithIds.put(R.id.emptyView, 6);
    }

    public ActivityAllCarBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.cvPk = (ComparisonView) bindings[4];
        this.emptyView = (EmptyView) bindings[6];
        this.ivBack = (ImageView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.tabAllCar = (TabLayout) bindings[2];
        this.vpAllCar = (ViewPager) bindings[5];
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

    public static ActivityAllCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllCarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAllCarBinding) DataBindingUtil.inflate(inflater, R.layout.activity_all_car, root, attachToRoot, bindingComponent);
    }

    public static ActivityAllCarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllCarBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_all_car, null, false), bindingComponent);
    }

    public static ActivityAllCarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllCarBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_all_car_0".equals(view.getTag())) {
            return new ActivityAllCarBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
