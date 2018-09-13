package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivitySelectPhotoBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView imageSizeText;
    private long mDirtyFlags = -1;
    private String mStr;
    private final RelativeLayout mboundView0;
    public final TextView nodataTips;
    public final LinearLayout originalLine;
    public final CheckBox originalTag;
    public final TextView previewText;
    public final RecyclerView reSelectPhoto;
    public final View viewShade;

    static {
        sViewsWithIds.put(2131624598, 1);
        sViewsWithIds.put(2131624599, 2);
        sViewsWithIds.put(2131624600, 3);
        sViewsWithIds.put(2131624601, 4);
        sViewsWithIds.put(2131624602, 5);
        sViewsWithIds.put(2131624303, 6);
        sViewsWithIds.put(2131624304, 7);
    }

    public ActivitySelectPhotoBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.imageSizeText = (TextView) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.nodataTips = (TextView) bindings[6];
        this.originalLine = (LinearLayout) bindings[2];
        this.originalTag = (CheckBox) bindings[4];
        this.previewText = (TextView) bindings[3];
        this.reSelectPhoto = (RecyclerView) bindings[1];
        this.viewShade = (View) bindings[7];
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

    public static ActivitySelectPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectPhotoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySelectPhotoBinding) DataBindingUtil.inflate(inflater, 2130903131, root, attachToRoot, bindingComponent);
    }

    public static ActivitySelectPhotoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectPhotoBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903131, null, false), bindingComponent);
    }

    public static ActivitySelectPhotoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectPhotoBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_select_photo_0".equals(view.getTag())) {
            return new ActivitySelectPhotoBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
