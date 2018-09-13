package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ActivityCameraBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout bottomLine;
    public final ImageView buttonFlash;
    public final View cameraErrorView;
    public final RelativeLayout cameraPreview;
    public final TextView cancel;
    public final ImageView capture;
    public final Chronometer chrono;
    public final ImageView chronoRecordingImage;
    public final ImageView focusImage;
    public final Gallery gallery;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final View shutterView;
    public final ImageView switchCamera;
    public final RelativeLayout topLine;

    static {
        sViewsWithIds.put(2131624281, 1);
        sViewsWithIds.put(2131624282, 2);
        sViewsWithIds.put(2131624283, 3);
        sViewsWithIds.put(2131624284, 4);
        sViewsWithIds.put(2131624250, 5);
        sViewsWithIds.put(2131624285, 6);
        sViewsWithIds.put(2131624286, 7);
        sViewsWithIds.put(2131624287, 8);
        sViewsWithIds.put(2131624288, 9);
        sViewsWithIds.put(2131624289, 10);
        sViewsWithIds.put(2131624290, 11);
        sViewsWithIds.put(2131624291, 12);
        sViewsWithIds.put(2131624292, 13);
    }

    public ActivityCameraBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.bottomLine = (LinearLayout) bindings[9];
        this.buttonFlash = (ImageView) bindings[6];
        this.cameraErrorView = (View) bindings[2];
        this.cameraPreview = (RelativeLayout) bindings[1];
        this.cancel = (TextView) bindings[12];
        this.capture = (ImageView) bindings[11];
        this.chrono = (Chronometer) bindings[7];
        this.chronoRecordingImage = (ImageView) bindings[8];
        this.focusImage = (ImageView) bindings[3];
        this.gallery = (Gallery) bindings[10];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.shutterView = (View) bindings[4];
        this.switchCamera = (ImageView) bindings[13];
        this.topLine = (RelativeLayout) bindings[5];
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

    public static ActivityCameraBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCameraBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityCameraBinding) DataBindingUtil.inflate(inflater, 2130903077, root, attachToRoot, bindingComponent);
    }

    public static ActivityCameraBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCameraBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903077, null, false), bindingComponent);
    }

    public static ActivityCameraBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityCameraBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_camera_0".equals(view.getTag())) {
            return new ActivityCameraBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
