package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import com.feng.library.R;

public class WebActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btSendPost;
    public final LinearLayout llSendPost;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final ProgressBar myProgressBar;
    public final RelativeLayout rlParent;
    public final WebView webView;

    static {
        sViewsWithIds.put(2131625574, 1);
        sViewsWithIds.put(R.id.webView, 2);
        sViewsWithIds.put(2131625575, 3);
        sViewsWithIds.put(2131625576, 4);
    }

    public WebActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.btSendPost = (Button) bindings[4];
        this.llSendPost = (LinearLayout) bindings[3];
        this.myProgressBar = (ProgressBar) bindings[1];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.webView = (WebView) bindings[2];
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

    public static WebActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static WebActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (WebActivityBinding) DataBindingUtil.inflate(inflater, 2130903435, root, attachToRoot, bindingComponent);
    }

    public static WebActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static WebActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903435, null, false), bindingComponent);
    }

    public static WebActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static WebActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/web_activity_0".equals(view.getTag())) {
            return new WebActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
