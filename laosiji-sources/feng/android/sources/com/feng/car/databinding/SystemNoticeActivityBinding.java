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
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class SystemNoticeActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private String mStr;
    private final LinearLayout mboundView0;
    public final LRecyclerView rcviewNotice;

    static {
        sViewsWithIds.put(2131625501, 1);
    }

    public SystemNoticeActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 2, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rcviewNotice = (LRecyclerView) bindings[1];
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

    public static SystemNoticeActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SystemNoticeActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SystemNoticeActivityBinding) DataBindingUtil.inflate(inflater, 2130903406, root, attachToRoot, bindingComponent);
    }

    public static SystemNoticeActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SystemNoticeActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903406, null, false), bindingComponent);
    }

    public static SystemNoticeActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SystemNoticeActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/system_notice_activity_0".equals(view.getTag())) {
            return new SystemNoticeActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
