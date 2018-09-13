package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.model.MenuItemInfo;
import com.feng.car.utils.FengUtil;

public class TabMenuItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private MenuItemInfo mInfo;
    private final RelativeLayout mboundView0;
    public final TextView tvCount;
    public final TextView tvTitle;

    static {
        sViewsWithIds.put(2131624296, 2);
    }

    public TabMenuItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCount = (TextView) bindings[1];
        this.tvCount.setTag(null);
        this.tvTitle = (TextView) bindings[2];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            case 28:
                setInfo((MenuItemInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(MenuItemInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public MenuItemInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoCountnum((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoCountnum(ObservableInt InfoCountnum, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        int infoCountnumInt0ViewVISIBLEViewGONE = 0;
        int infoCountnumGet = 0;
        MenuItemInfo info = this.mInfo;
        ObservableInt infoCountnum = null;
        String fengUtilNumberFormat99InfoCountnum = null;
        if ((7 & dirtyFlags) != 0) {
            if (info != null) {
                infoCountnum = info.countnum;
            }
            updateRegistration(0, infoCountnum);
            if (infoCountnum != null) {
                infoCountnumGet = infoCountnum.get();
            }
            boolean infoCountnumInt0 = infoCountnumGet > 0;
            fengUtilNumberFormat99InfoCountnum = FengUtil.numberFormat99(infoCountnumGet);
            if ((7 & dirtyFlags) != 0) {
                if (infoCountnumInt0) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            infoCountnumInt0ViewVISIBLEViewGONE = infoCountnumInt0 ? 0 : 8;
        }
        if ((7 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCount, fengUtilNumberFormat99InfoCountnum);
            this.tvCount.setVisibility(infoCountnumInt0ViewVISIBLEViewGONE);
        }
    }

    public static TabMenuItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static TabMenuItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (TabMenuItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903408, root, attachToRoot, bindingComponent);
    }

    public static TabMenuItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static TabMenuItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903408, null, false), bindingComponent);
    }

    public static TabMenuItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static TabMenuItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/tab_menu_item_layout_0".equals(view.getTag())) {
            return new TabMenuItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
