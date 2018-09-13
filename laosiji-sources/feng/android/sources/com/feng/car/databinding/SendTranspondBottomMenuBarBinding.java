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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SendTranspondBottomMenuBarBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivAddAt;
    public final ImageView ivAddEmoji;
    public final ImageView ivAddImage;
    public final ImageView ivComplete;
    public final LinearLayout llTextNum;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final RelativeLayout rlBottomMenu;
    public final TextView tvCount;
    public final TextView tvCurrentWordsCount;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624944, 1);
        sViewsWithIds.put(2131624945, 2);
        sViewsWithIds.put(2131624946, 3);
        sViewsWithIds.put(2131625487, 4);
        sViewsWithIds.put(2131625488, 5);
        sViewsWithIds.put(2131624948, 6);
        sViewsWithIds.put(2131625406, 7);
        sViewsWithIds.put(2131624473, 8);
    }

    public SendTranspondBottomMenuBarBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.ivAddAt = (ImageView) bindings[2];
        this.ivAddEmoji = (ImageView) bindings[3];
        this.ivAddImage = (ImageView) bindings[1];
        this.ivComplete = (ImageView) bindings[7];
        this.llTextNum = (LinearLayout) bindings[4];
        this.rlBottomMenu = (RelativeLayout) bindings[0];
        this.rlBottomMenu.setTag(null);
        this.tvCount = (TextView) bindings[6];
        this.tvCurrentWordsCount = (TextView) bindings[5];
        this.vLine = (View) bindings[8];
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

    public static SendTranspondBottomMenuBarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SendTranspondBottomMenuBarBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SendTranspondBottomMenuBarBinding) DataBindingUtil.inflate(inflater, 2130903388, root, attachToRoot, bindingComponent);
    }

    public static SendTranspondBottomMenuBarBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SendTranspondBottomMenuBarBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903388, null, false), bindingComponent);
    }

    public static SendTranspondBottomMenuBarBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SendTranspondBottomMenuBarBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/send_transpond_bottom_menu_bar_0".equals(view.getTag())) {
            return new SendTranspondBottomMenuBarBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
