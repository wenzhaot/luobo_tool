package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.sysmsg.SystemMessageInfo;
import com.feng.car.view.textview.AisenTextView;

public class SystemMsgItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private SystemMessageInfo mSystemInfo;
    private final LinearLayout mboundView0;
    public final AisenTextView tvSystemInfoDetail;
    public final TextView tvSystemInfoTime;

    static {
        sViewsWithIds.put(2131625499, 2);
    }

    public SystemMsgItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvSystemInfoDetail = (AisenTextView) bindings[2];
        this.tvSystemInfoTime = (TextView) bindings[1];
        this.tvSystemInfoTime.setTag(null);
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
            case 66:
                setSystemInfo((SystemMessageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSystemInfo(SystemMessageInfo SystemInfo) {
        this.mSystemInfo = SystemInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(66);
        super.requestRebind();
    }

    public SystemMessageInfo getSystemInfo() {
        return this.mSystemInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String systemInfoTime = null;
        SystemMessageInfo systemInfo = this.mSystemInfo;
        if (!((dirtyFlags & 3) == 0 || systemInfo == null)) {
            systemInfoTime = systemInfo.time;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvSystemInfoTime, systemInfoTime);
        }
    }

    public static SystemMsgItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SystemMsgItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SystemMsgItemBinding) DataBindingUtil.inflate(inflater, 2130903405, root, attachToRoot, bindingComponent);
    }

    public static SystemMsgItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SystemMsgItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903405, null, false), bindingComponent);
    }

    public static SystemMsgItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SystemMsgItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/system_msg_item_0".equals(view.getTag())) {
            return new SystemMsgItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
