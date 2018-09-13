package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.video.LocalVideoPlayer;

public class ActivityWatchvideoBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private ImageVideoInfo mInfo;
    private final RelativeLayout mboundView0;
    public final TextView tvSelectIndex;
    public final LocalVideoPlayer videoPlayer;

    static {
        sViewsWithIds.put(2131624726, 2);
    }

    public ActivityWatchvideoBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.tvSelectIndex = (TextView) bindings[1];
        this.tvSelectIndex.setTag(null);
        this.videoPlayer = (LocalVideoPlayer) bindings[2];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8;
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
            case 28:
                setInfo((ImageVideoInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(ImageVideoInfo Info) {
        updateRegistration(1, Info);
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public ImageVideoInfo getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfoIsSelected((ObservableBoolean) object, fieldId);
            case 1:
                return onChangeInfo((ImageVideoInfo) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfoIsSelected(ObservableBoolean InfoIsSelected, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeInfo(ImageVideoInfo Info, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            case 52:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        boolean infoIsSelectedGet = false;
        Drawable infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = null;
        ObservableBoolean infoIsSelected = null;
        ImageVideoInfo info = this.mInfo;
        String infoSelectIndexJavaLangString = null;
        int infoSelectIndex = 0;
        boolean infoSelectIndexInt0 = false;
        String infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString = null;
        if ((15 & dirtyFlags) != 0) {
            if ((11 & dirtyFlags) != 0) {
                if (info != null) {
                    infoIsSelected = info.isSelected;
                }
                updateRegistration(0, infoIsSelected);
                if (infoIsSelected != null) {
                    infoIsSelectedGet = infoIsSelected.get();
                }
                if ((11 & dirtyFlags) != 0) {
                    if (infoIsSelectedGet) {
                        dirtyFlags |= 32;
                    } else {
                        dirtyFlags |= 16;
                    }
                }
                if (infoIsSelectedGet) {
                    infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = getDrawableFromResource(this.tvSelectIndex, 2130838450);
                } else {
                    infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector = getDrawableFromResource(this.tvSelectIndex, 2130838451);
                }
            }
            if ((14 & dirtyFlags) != 0) {
                if (info != null) {
                    infoSelectIndex = info.getSelectIndex();
                }
                infoSelectIndexInt0 = infoSelectIndex == 0;
                if ((14 & dirtyFlags) != 0) {
                    if (infoSelectIndexInt0) {
                        dirtyFlags |= 128;
                    } else {
                        dirtyFlags |= 64;
                    }
                }
            }
        }
        if ((64 & dirtyFlags) != 0) {
            infoSelectIndexJavaLangString = infoSelectIndex + "";
        }
        if ((14 & dirtyFlags) != 0) {
            infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString = infoSelectIndexInt0 ? "" : infoSelectIndexJavaLangString;
        }
        if ((11 & dirtyFlags) != 0) {
            ViewBindingAdapter.setBackground(this.tvSelectIndex, infoIsSelectedTvSelectIndexAndroidDrawableSelectMediaSelectorTvSelectIndexAndroidDrawableSelectNotMediaSelector);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvSelectIndex, infoSelectIndexInt0JavaLangStringInfoSelectIndexJavaLangString);
        }
    }

    public static ActivityWatchvideoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWatchvideoBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityWatchvideoBinding) DataBindingUtil.inflate(inflater, 2130903151, root, attachToRoot, bindingComponent);
    }

    public static ActivityWatchvideoBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWatchvideoBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903151, null, false), bindingComponent);
    }

    public static ActivityWatchvideoBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityWatchvideoBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_watchvideo_0".equals(view.getTag())) {
            return new ActivityWatchvideoBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
