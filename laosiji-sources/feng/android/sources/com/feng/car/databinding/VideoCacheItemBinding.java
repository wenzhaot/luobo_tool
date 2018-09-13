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
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.SectorProgressView;

public class VideoCacheItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final RelativeLayout detailLine;
    public final AutoFrescoDraweeView image;
    public final TextView infoText;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    private final LinearLayout mboundView0;
    public final ImageView pointImage;
    public final SectorProgressView progress;
    public final ImageView selectImage;
    public final ImageView statusImage;
    public final LinearLayout statusLine;
    public final TextView statusText;
    public final TextView tipsText;
    public final TextView title;
    public final TextView userName;

    static {
        sViewsWithIds.put(2131625541, 1);
        sViewsWithIds.put(2131624155, 2);
        sViewsWithIds.put(2131625542, 3);
        sViewsWithIds.put(2131623994, 4);
        sViewsWithIds.put(2131625543, 5);
        sViewsWithIds.put(2131625544, 6);
        sViewsWithIds.put(2131625545, 7);
        sViewsWithIds.put(2131624015, 8);
        sViewsWithIds.put(2131625546, 9);
        sViewsWithIds.put(2131625547, 10);
        sViewsWithIds.put(2131624419, 11);
        sViewsWithIds.put(2131624820, 12);
    }

    public VideoCacheItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 13, sIncludes, sViewsWithIds);
        this.detailLine = (RelativeLayout) bindings[10];
        this.image = (AutoFrescoDraweeView) bindings[2];
        this.infoText = (TextView) bindings[12];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.pointImage = (ImageView) bindings[7];
        this.progress = (SectorProgressView) bindings[4];
        this.selectImage = (ImageView) bindings[1];
        this.statusImage = (ImageView) bindings[5];
        this.statusLine = (LinearLayout) bindings[3];
        this.statusText = (TextView) bindings[6];
        this.tipsText = (TextView) bindings[9];
        this.title = (TextView) bindings[8];
        this.userName = (TextView) bindings[11];
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

    public static VideoCacheItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static VideoCacheItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (VideoCacheItemBinding) DataBindingUtil.inflate(inflater, 2130903423, root, attachToRoot, bindingComponent);
    }

    public static VideoCacheItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static VideoCacheItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903423, null, false), bindingComponent);
    }

    public static VideoCacheItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static VideoCacheItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/video_cache_item_0".equals(view.getTag())) {
            return new VideoCacheItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
