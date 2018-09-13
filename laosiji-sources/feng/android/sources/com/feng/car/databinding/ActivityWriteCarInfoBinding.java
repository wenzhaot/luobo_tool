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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ActivityWriteCarInfoBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView brandText;
    public final TextView carsText;
    public final TextView carxText;
    public final TextView cityText;
    public final TextView fullText;
    public final LinearLayout infoLine;
    public final EditText landingPriceText;
    public final TextView loanText;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final EditText mobilText;
    public final EditText nakedPriceText;
    public final TextView provinceText;
    public final EditText remarksText;
    public final ScrollView scrollView;
    public final ImageView submit;
    public final LinearLayout successLine;
    public final ImageView successText;
    public final TextView timeText;

    static {
        sViewsWithIds.put(2131624164, 1);
        sViewsWithIds.put(2131624734, 2);
        sViewsWithIds.put(2131624735, 3);
        sViewsWithIds.put(2131624736, 4);
        sViewsWithIds.put(2131624737, 5);
        sViewsWithIds.put(2131624738, 6);
        sViewsWithIds.put(2131624739, 7);
        sViewsWithIds.put(2131624740, 8);
        sViewsWithIds.put(2131624741, 9);
        sViewsWithIds.put(2131624742, 10);
        sViewsWithIds.put(2131624743, 11);
        sViewsWithIds.put(2131624744, 12);
        sViewsWithIds.put(2131624745, 13);
        sViewsWithIds.put(2131624746, 14);
        sViewsWithIds.put(2131624747, 15);
        sViewsWithIds.put(2131624748, 16);
        sViewsWithIds.put(2131624749, 17);
    }

    public ActivityWriteCarInfoBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 18, sIncludes, sViewsWithIds);
        this.brandText = (TextView) bindings[3];
        this.carsText = (TextView) bindings[4];
        this.carxText = (TextView) bindings[5];
        this.cityText = (TextView) bindings[8];
        this.fullText = (TextView) bindings[12];
        this.infoLine = (LinearLayout) bindings[2];
        this.landingPriceText = (EditText) bindings[11];
        this.loanText = (TextView) bindings[13];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.mobilText = (EditText) bindings[6];
        this.nakedPriceText = (EditText) bindings[10];
        this.provinceText = (TextView) bindings[7];
        this.remarksText = (EditText) bindings[14];
        this.scrollView = (ScrollView) bindings[1];
        this.submit = (ImageView) bindings[15];
        this.successLine = (LinearLayout) bindings[16];
        this.successText = (ImageView) bindings[17];
        this.timeText = (TextView) bindings[9];
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

    public static ActivityWriteCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWriteCarInfoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityWriteCarInfoBinding) DataBindingUtil.inflate(inflater, 2130903152, root, attachToRoot, bindingComponent);
    }

    public static ActivityWriteCarInfoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWriteCarInfoBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903152, null, false), bindingComponent);
    }

    public static ActivityWriteCarInfoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWriteCarInfoBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_write_car_info_0".equals(view.getTag())) {
            return new ActivityWriteCarInfoBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
