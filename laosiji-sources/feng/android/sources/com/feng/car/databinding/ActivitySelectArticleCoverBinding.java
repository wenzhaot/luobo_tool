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
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivitySelectArticleCoverBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final FrameLayout bottomLine;
    public final AutoFrescoDraweeView headImage;
    public final AutoFrescoDraweeView image;
    public final ImageView ivCloseArrow;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final ImageView publishButton;
    public final RecyclerView recyclerView;
    public final RelativeLayout rlCoverParent;
    public final RelativeLayout rlTitleBar;
    public final TextView title;
    public final TextView tvPraiseNum;
    public final TextView tvTitle;
    public final TextView userName;

    static {
        sViewsWithIds.put(2131624288, 1);
        sViewsWithIds.put(2131624592, 2);
        sViewsWithIds.put(2131624249, 3);
        sViewsWithIds.put(2131624215, 4);
        sViewsWithIds.put(2131624593, 5);
        sViewsWithIds.put(2131624296, 6);
        sViewsWithIds.put(2131624155, 7);
        sViewsWithIds.put(2131624015, 8);
        sViewsWithIds.put(2131624594, 9);
        sViewsWithIds.put(2131624419, 10);
        sViewsWithIds.put(2131624244, 11);
    }

    public ActivitySelectArticleCoverBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 12, sIncludes, sViewsWithIds);
        this.bottomLine = (FrameLayout) bindings[1];
        this.headImage = (AutoFrescoDraweeView) bindings[9];
        this.image = (AutoFrescoDraweeView) bindings[7];
        this.ivCloseArrow = (ImageView) bindings[5];
        this.publishButton = (ImageView) bindings[2];
        this.recyclerView = (RecyclerView) bindings[3];
        this.rlCoverParent = (RelativeLayout) bindings[0];
        this.rlCoverParent.setTag(null);
        this.rlTitleBar = (RelativeLayout) bindings[4];
        this.title = (TextView) bindings[8];
        this.tvPraiseNum = (TextView) bindings[11];
        this.tvTitle = (TextView) bindings[6];
        this.userName = (TextView) bindings[10];
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

    public static ActivitySelectArticleCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectArticleCoverBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySelectArticleCoverBinding) DataBindingUtil.inflate(inflater, 2130903129, root, attachToRoot, bindingComponent);
    }

    public static ActivitySelectArticleCoverBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectArticleCoverBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903129, null, false), bindingComponent);
    }

    public static ActivitySelectArticleCoverBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySelectArticleCoverBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_select_article_cover_0".equals(view.getTag())) {
            return new ActivitySelectArticleCoverBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
