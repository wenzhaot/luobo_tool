package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.drawee.view.SimpleDraweeView;

public class ActivitySplashBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final SimpleDraweeView indexBg;
    public final ImageView ivFirstPublish;
    public final ImageView ivLogo;
    private long mDirtyFlags = -1;
    private int mUseless;
    public final RelativeLayout rlParent;
    public final RelativeLayout rlSkip;
    public final TextView tvAd;
    public final TextView tvSkip;
    public final TextView tvSkipTime;

    static {
        sViewsWithIds.put(2131624685, 1);
        sViewsWithIds.put(2131624687, 2);
        sViewsWithIds.put(2131624686, 3);
        sViewsWithIds.put(2131624688, 4);
        sViewsWithIds.put(2131624689, 5);
        sViewsWithIds.put(2131624690, 6);
        sViewsWithIds.put(2131624691, 7);
    }

    public ActivitySplashBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.indexBg = (SimpleDraweeView) bindings[1];
        this.ivFirstPublish = (ImageView) bindings[7];
        this.ivLogo = (ImageView) bindings[3];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.rlSkip = (RelativeLayout) bindings[4];
        this.tvAd = (TextView) bindings[2];
        this.tvSkip = (TextView) bindings[5];
        this.tvSkipTime = (TextView) bindings[6];
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

    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySplashBinding) DataBindingUtil.inflate(inflater, 2130903142, root, attachToRoot, bindingComponent);
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySplashBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903142, null, false), bindingComponent);
    }

    public static ActivitySplashBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySplashBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_splash_0".equals(view.getTag())) {
            return new ActivitySplashBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
