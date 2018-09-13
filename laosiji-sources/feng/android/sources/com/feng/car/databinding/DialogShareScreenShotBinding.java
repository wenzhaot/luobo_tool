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

public class DialogShareScreenShotBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivScreenShot;
    public final LinearLayout llFun;
    public final LinearLayout llLandscape;
    public final LinearLayout llPortrait;
    private long mDirtyFlags = -1;
    private final RelativeLayout mboundView0;
    public final TextView tvCancel;
    public final TextView tvMoments;
    public final TextView tvMomentsLandscape;
    public final TextView tvQZone;
    public final TextView tvQZoneLandscape;
    public final TextView tvQq;
    public final TextView tvQqLandscape;
    public final TextView tvWeChat;
    public final TextView tvWeChatLandscape;
    public final TextView tvWeibo;
    public final TextView tvWeiboLandscape;

    static {
        sViewsWithIds.put(2131624994, 1);
        sViewsWithIds.put(2131624995, 2);
        sViewsWithIds.put(2131624996, 3);
        sViewsWithIds.put(2131624997, 4);
        sViewsWithIds.put(2131624998, 5);
        sViewsWithIds.put(2131624999, 6);
        sViewsWithIds.put(2131625000, 7);
        sViewsWithIds.put(2131625001, 8);
        sViewsWithIds.put(2131625002, 9);
        sViewsWithIds.put(2131625003, 10);
        sViewsWithIds.put(2131625004, 11);
        sViewsWithIds.put(2131625005, 12);
        sViewsWithIds.put(2131625006, 13);
        sViewsWithIds.put(2131624576, 14);
        sViewsWithIds.put(2131625007, 15);
    }

    public DialogShareScreenShotBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 16, sIncludes, sViewsWithIds);
        this.ivScreenShot = (ImageView) bindings[15];
        this.llFun = (LinearLayout) bindings[1];
        this.llLandscape = (LinearLayout) bindings[8];
        this.llPortrait = (LinearLayout) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvCancel = (TextView) bindings[14];
        this.tvMoments = (TextView) bindings[3];
        this.tvMomentsLandscape = (TextView) bindings[9];
        this.tvQZone = (TextView) bindings[7];
        this.tvQZoneLandscape = (TextView) bindings[13];
        this.tvQq = (TextView) bindings[6];
        this.tvQqLandscape = (TextView) bindings[12];
        this.tvWeChat = (TextView) bindings[4];
        this.tvWeChatLandscape = (TextView) bindings[10];
        this.tvWeibo = (TextView) bindings[5];
        this.tvWeiboLandscape = (TextView) bindings[11];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 1;
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
        return false;
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

    public static DialogShareScreenShotBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static DialogShareScreenShotBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (DialogShareScreenShotBinding) DataBindingUtil.inflate(inflater, 2130903226, root, attachToRoot, bindingComponent);
    }

    public static DialogShareScreenShotBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static DialogShareScreenShotBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903226, null, false), bindingComponent);
    }

    public static DialogShareScreenShotBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static DialogShareScreenShotBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/dialog_share_screen_shot_0".equals(view.getTag())) {
            return new DialogShareScreenShotBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
