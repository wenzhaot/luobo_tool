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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.VoiceBoxView;

public class OldDriverChooseCarItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider;
    public final View footDivider;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlMoreParent;
    public final TextView tvDividerTitle;
    public final TextView tvMore;
    public final VoiceBoxView vbvChooseCarItem;

    static {
        sViewsWithIds.put(2131624240, 1);
        sViewsWithIds.put(2131625342, 2);
        sViewsWithIds.put(2131625343, 3);
        sViewsWithIds.put(2131625344, 4);
        sViewsWithIds.put(2131625345, 5);
        sViewsWithIds.put(2131625346, 6);
    }

    public OldDriverChooseCarItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 7, sIncludes, sViewsWithIds);
        this.divider = (View) bindings[1];
        this.footDivider = (View) bindings[6];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlMoreParent = (RelativeLayout) bindings[2];
        this.tvDividerTitle = (TextView) bindings[3];
        this.tvMore = (TextView) bindings[4];
        this.vbvChooseCarItem = (VoiceBoxView) bindings[5];
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
                setUseless((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUseless(Integer Useless) {
        this.mUseless = Useless;
    }

    public Integer getUseless() {
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

    public static OldDriverChooseCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static OldDriverChooseCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (OldDriverChooseCarItemBinding) DataBindingUtil.inflate(inflater, 2130903335, root, attachToRoot, bindingComponent);
    }

    public static OldDriverChooseCarItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static OldDriverChooseCarItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903335, null, false), bindingComponent);
    }

    public static OldDriverChooseCarItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static OldDriverChooseCarItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/old_driver_choose_car_item_0".equals(view.getTag())) {
            return new OldDriverChooseCarItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
