package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class SelectCoverItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View footerView;
    public final View headerView;
    public final AutoFrescoDraweeView image;
    public final RelativeLayout imageLine;
    private long mDirtyFlags = -1;
    private Integer mIndext;
    private ObservableInt mSelpos;
    public final LinearLayout parentLine;
    public final TextView timeText;

    static {
        sViewsWithIds.put(2131625482, 2);
        sViewsWithIds.put(2131624155, 3);
        sViewsWithIds.put(2131624741, 4);
        sViewsWithIds.put(2131625483, 5);
    }

    public SelectCoverItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.footerView = (View) bindings[5];
        this.headerView = (View) bindings[2];
        this.image = (AutoFrescoDraweeView) bindings[3];
        this.imageLine = (RelativeLayout) bindings[1];
        this.imageLine.setTag(null);
        this.parentLine = (LinearLayout) bindings[0];
        this.parentLine.setTag(null);
        this.timeText = (TextView) bindings[4];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            case 27:
                setIndext((Integer) variable);
                return true;
            case 53:
                setSelpos((ObservableInt) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSelpos(ObservableInt Selpos) {
        updateRegistration(0, Selpos);
        this.mSelpos = Selpos;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(53);
        super.requestRebind();
    }

    public ObservableInt getSelpos() {
        return this.mSelpos;
    }

    public void setIndext(Integer Indext) {
        this.mIndext = Indext;
        synchronized (this) {
            this.mDirtyFlags |= 2;
        }
        notifyPropertyChanged(27);
        super.requestRebind();
    }

    public Integer getIndext() {
        return this.mIndext;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeSelpos((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeSelpos(ObservableInt Selpos, int fieldId) {
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

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        ObservableInt selpos = this.mSelpos;
        Integer indext = this.mIndext;
        Drawable indextSelposGetImageLineAndroidDrawableBgTransBorderFb6c064dpImageLineAndroidDrawableBgTransparent = null;
        int selposGet = 0;
        if ((dirtyFlags & 7) != 0) {
            if (selpos != null) {
                selposGet = selpos.get();
            }
            boolean indextSelposGet = DynamicUtil.safeUnbox(indext) == selposGet;
            if ((dirtyFlags & 7) != 0) {
                if (indextSelposGet) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            indextSelposGetImageLineAndroidDrawableBgTransBorderFb6c064dpImageLineAndroidDrawableBgTransparent = indextSelposGet ? getDrawableFromResource(this.imageLine, 2130837735) : getDrawableFromResource(this.imageLine, 2130837736);
        }
        if ((dirtyFlags & 7) != 0) {
            ViewBindingAdapter.setBackground(this.imageLine, indextSelposGetImageLineAndroidDrawableBgTransBorderFb6c064dpImageLineAndroidDrawableBgTransparent);
        }
    }

    public static SelectCoverItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCoverItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (SelectCoverItemBinding) DataBindingUtil.inflate(inflater, 2130903382, root, attachToRoot, bindingComponent);
    }

    public static SelectCoverItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCoverItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903382, null, false), bindingComponent);
    }

    public static SelectCoverItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static SelectCoverItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/select_cover_item_0".equals(view.getTag())) {
            return new SelectCoverItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
