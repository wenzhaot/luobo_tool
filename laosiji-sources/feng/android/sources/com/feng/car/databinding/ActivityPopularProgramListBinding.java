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
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.guideview.ShowTipsView;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityPopularProgramListBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView backImage;
    public final ImageView ivUpdateRemind;
    public final LinearLayout llTips;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final ImageView optionImage;
    public final ProgressBar progressBar;
    public final LRecyclerView rcvPopularProgramList;
    public final RelativeLayout rlParent;
    public final ShowTipsView showTipsView;
    public final TextView step0;
    public final View titleBottomLine;
    public final RelativeLayout titlebar;
    public final TextView tvTitle;

    static {
        sViewsWithIds.put(2131624525, 1);
        sViewsWithIds.put(2131624521, 2);
        sViewsWithIds.put(2131624418, 3);
        sViewsWithIds.put(2131624296, 4);
        sViewsWithIds.put(2131624526, 5);
        sViewsWithIds.put(2131624420, 6);
        sViewsWithIds.put(2131624421, 7);
        sViewsWithIds.put(2131624523, 8);
        sViewsWithIds.put(2131624527, 9);
        sViewsWithIds.put(2131624465, 10);
        sViewsWithIds.put(2131624528, 11);
    }

    public ActivityPopularProgramListBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.backImage = (ImageView) bindings[3];
        this.ivUpdateRemind = (ImageView) bindings[5];
        this.llTips = (LinearLayout) bindings[10];
        this.optionImage = (ImageView) bindings[6];
        this.progressBar = (ProgressBar) bindings[7];
        this.rcvPopularProgramList = (LRecyclerView) bindings[1];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.showTipsView = (ShowTipsView) bindings[9];
        this.step0 = (TextView) bindings[11];
        this.titleBottomLine = (View) bindings[8];
        this.titlebar = (RelativeLayout) bindings[2];
        this.tvTitle = (TextView) bindings[4];
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

    public static ActivityPopularProgramListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPopularProgramListBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPopularProgramListBinding) DataBindingUtil.inflate(inflater, 2130903108, root, attachToRoot, bindingComponent);
    }

    public static ActivityPopularProgramListBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPopularProgramListBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903108, null, false), bindingComponent);
    }

    public static ActivityPopularProgramListBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPopularProgramListBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_popular_program_list_0".equals(view.getTag())) {
            return new ActivityPopularProgramListBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
