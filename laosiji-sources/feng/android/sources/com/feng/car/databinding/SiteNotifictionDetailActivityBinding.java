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
import com.feng.car.view.AutoFrescoDraweeView;

public class SiteNotifictionDetailActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View divider1;
    public final View divider2;
    public final RelativeLayout line1;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final TextView summary;
    public final TextView time;
    public final TextView title;
    public final TextView tvUserName;
    public final AutoFrescoDraweeView userImage;

    static {
        sViewsWithIds.put(2131623983, 1);
        sViewsWithIds.put(2131624269, 2);
        sViewsWithIds.put(2131624271, 3);
        sViewsWithIds.put(2131624874, 4);
        sViewsWithIds.put(2131625452, 5);
        sViewsWithIds.put(2131624015, 6);
        sViewsWithIds.put(2131625493, 7);
        sViewsWithIds.put(2131625494, 8);
    }

    public SiteNotifictionDetailActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.divider1 = (View) bindings[5];
        this.divider2 = (View) bindings[7];
        this.line1 = (RelativeLayout) bindings[1];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.summary = (TextView) bindings[8];
        this.time = (TextView) bindings[4];
        this.title = (TextView) bindings[6];
        this.tvUserName = (TextView) bindings[3];
        this.userImage = (AutoFrescoDraweeView) bindings[2];
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

    public static SiteNotifictionDetailActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SiteNotifictionDetailActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SiteNotifictionDetailActivityBinding) DataBindingUtil.inflate(inflater, 2130903393, root, attachToRoot, bindingComponent);
    }

    public static SiteNotifictionDetailActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SiteNotifictionDetailActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903393, null, false), bindingComponent);
    }

    public static SiteNotifictionDetailActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SiteNotifictionDetailActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/site_notifiction_detail_activity_0".equals(view.getTag())) {
            return new SiteNotifictionDetailActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
