package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.video.shortvideo.VideoSelectImageTimeLine;

public class ActivityShortvideoSelectimageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView cancelButton;
    public final TextView cancelText;
    public final TextView completText;
    public final FrameLayout editerFlVideo;
    public final FrameLayout editerLayoutPlayer;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final RelativeLayout mboundView0;
    public final ImageButton playButton;
    public final FrameLayout progressLine;
    public final TextView progressText;
    public final RelativeLayout tipsLine;
    public final VideoSelectImageTimeLine videoSelectImageTimeLine;

    static {
        sViewsWithIds.put(2131624647, 1);
        sViewsWithIds.put(2131624568, 2);
        sViewsWithIds.put(2131624641, 3);
        sViewsWithIds.put(2131624642, 4);
        sViewsWithIds.put(2131624643, 5);
        sViewsWithIds.put(2131624644, 6);
        sViewsWithIds.put(2131624645, 7);
        sViewsWithIds.put(2131624646, 8);
        sViewsWithIds.put(2131624648, 9);
        sViewsWithIds.put(2131624649, 10);
    }

    public ActivityShortvideoSelectimageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.cancelButton = (TextView) bindings[10];
        this.cancelText = (TextView) bindings[3];
        this.completText = (TextView) bindings[4];
        this.editerFlVideo = (FrameLayout) bindings[6];
        this.editerLayoutPlayer = (FrameLayout) bindings[5];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.playButton = (ImageButton) bindings[7];
        this.progressLine = (FrameLayout) bindings[8];
        this.progressText = (TextView) bindings[9];
        this.tipsLine = (RelativeLayout) bindings[2];
        this.videoSelectImageTimeLine = (VideoSelectImageTimeLine) bindings[1];
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

    public static ActivityShortvideoSelectimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShortvideoSelectimageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityShortvideoSelectimageBinding) DataBindingUtil.inflate(inflater, 2130903138, root, attachToRoot, bindingComponent);
    }

    public static ActivityShortvideoSelectimageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShortvideoSelectimageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903138, null, false), bindingComponent);
    }

    public static ActivityShortvideoSelectimageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityShortvideoSelectimageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_shortvideo_selectimage_0".equals(view.getTag())) {
            return new ActivityShortvideoSelectimageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
