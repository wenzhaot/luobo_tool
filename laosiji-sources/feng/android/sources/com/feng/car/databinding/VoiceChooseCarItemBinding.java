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
import com.feng.car.view.VoiceBoxView;

public class VoiceChooseCarItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View footDivider;
    private long mDirtyFlags = -1;
    private Integer mUseless;
    private final LinearLayout mboundView0;
    public final VoiceBoxView vbvChooseCarItem;

    static {
        sViewsWithIds.put(2131625345, 1);
        sViewsWithIds.put(2131625346, 2);
    }

    public VoiceChooseCarItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.footDivider = (View) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.vbvChooseCarItem = (VoiceBoxView) bindings[1];
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

    public static VoiceChooseCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static VoiceChooseCarItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (VoiceChooseCarItemBinding) DataBindingUtil.inflate(inflater, 2130903429, root, attachToRoot, bindingComponent);
    }

    public static VoiceChooseCarItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static VoiceChooseCarItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903429, null, false), bindingComponent);
    }

    public static VoiceChooseCarItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static VoiceChooseCarItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/voice_choose_car_item_0".equals(view.getTag())) {
            return new VoiceChooseCarItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
