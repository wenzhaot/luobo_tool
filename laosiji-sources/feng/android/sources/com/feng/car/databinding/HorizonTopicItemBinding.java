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
import com.feng.car.entity.circle.CircleInfo;

public class HorizonTopicItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private CircleInfo mInfo;
    private final LinearLayout mboundView0;
    public final TextView tvTopicName;
    public final View vLeft;

    static {
        sViewsWithIds.put(2131624949, 2);
    }

    public HorizonTopicItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvTopicName = (TextView) bindings[1];
        this.tvTopicName.setTag(null);
        this.vLeft = (View) bindings[2];
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
            case 28:
                setInfo((CircleInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(CircleInfo Info) {
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public CircleInfo getInfo() {
        return this.mInfo;
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
        String infoName = null;
        CircleInfo info = this.mInfo;
        if (!((dirtyFlags & 3) == 0 || info == null)) {
            infoName = info.name;
        }
        if ((dirtyFlags & 3) != 0) {
            TextViewBindingAdapter.setText(this.tvTopicName, infoName);
        }
    }

    public static HorizonTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static HorizonTopicItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (HorizonTopicItemBinding) DataBindingUtil.inflate(inflater, 2130903259, root, attachToRoot, bindingComponent);
    }

    public static HorizonTopicItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static HorizonTopicItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903259, null, false), bindingComponent);
    }

    public static HorizonTopicItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static HorizonTopicItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/horizon_topic_item_0".equals(view.getTag())) {
            return new HorizonTopicItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
