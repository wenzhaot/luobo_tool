package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class NetCheckActivityBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final Button btnCopy;
    public final Button btnStart;
    public final LinearLayout container;
    public final EditText domainName;
    private long mDirtyFlags = -1;
    private String mStr;
    public final ProgressBar progress;
    public final RadioButton rbApi;
    public final RadioButton rbImage;
    public final RadioButton rbUpload;
    public final RadioButton rbVideo;
    public final RadioGroup rg;
    public final TextView text;

    static {
        sViewsWithIds.put(2131625293, 1);
        sViewsWithIds.put(2131625294, 2);
        sViewsWithIds.put(2131625295, 3);
        sViewsWithIds.put(2131625296, 4);
        sViewsWithIds.put(2131625297, 5);
        sViewsWithIds.put(2131625298, 6);
        sViewsWithIds.put(2131625299, 7);
        sViewsWithIds.put(2131623994, 8);
        sViewsWithIds.put(2131625300, 9);
        sViewsWithIds.put(2131624009, 10);
    }

    public NetCheckActivityBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.btnCopy = (Button) bindings[9];
        this.btnStart = (Button) bindings[7];
        this.container = (LinearLayout) bindings[0];
        this.container.setTag(null);
        this.domainName = (EditText) bindings[6];
        this.progress = (ProgressBar) bindings[8];
        this.rbApi = (RadioButton) bindings[2];
        this.rbImage = (RadioButton) bindings[3];
        this.rbUpload = (RadioButton) bindings[5];
        this.rbVideo = (RadioButton) bindings[4];
        this.rg = (RadioGroup) bindings[1];
        this.text = (TextView) bindings[10];
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

    public static NetCheckActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static NetCheckActivityBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (NetCheckActivityBinding) DataBindingUtil.inflate(inflater, 2130903315, root, attachToRoot, bindingComponent);
    }

    public static NetCheckActivityBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static NetCheckActivityBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903315, null, false), bindingComponent);
    }

    public static NetCheckActivityBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static NetCheckActivityBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/net_check_activity_0".equals(view.getTag())) {
            return new NetCheckActivityBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
