package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.R;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityAllprogramBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView back;
    public final LinearLayout groupEmptyLine;
    public final RecyclerView groupRecyclerView;
    public final TextView groupReloadButton;
    public final ImageView ivMessage;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final LinearLayout parent;
    public final LinearLayout programEmptyLine;
    public final LRecyclerView programRecyclerView;
    public final TextView programReloadButton;
    public final TextView title;
    public final TextView tvMessageCommentNum;

    static {
        sViewsWithIds.put(R.id.back, 1);
        sViewsWithIds.put(2131624015, 2);
        sViewsWithIds.put(R.id.iv_message, 3);
        sViewsWithIds.put(R.id.tv_message_comment_num, 4);
        sViewsWithIds.put(R.id.groupEmptyLine, 5);
        sViewsWithIds.put(R.id.groupReloadButton, 6);
        sViewsWithIds.put(R.id.groupRecyclerView, 7);
        sViewsWithIds.put(R.id.programEmptyLine, 8);
        sViewsWithIds.put(R.id.programReloadButton, 9);
        sViewsWithIds.put(R.id.programRecyclerView, 10);
    }

    public ActivityAllprogramBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.back = (ImageView) bindings[1];
        this.groupEmptyLine = (LinearLayout) bindings[5];
        this.groupRecyclerView = (RecyclerView) bindings[7];
        this.groupReloadButton = (TextView) bindings[6];
        this.ivMessage = (ImageView) bindings[3];
        this.parent = (LinearLayout) bindings[0];
        this.parent.setTag(null);
        this.programEmptyLine = (LinearLayout) bindings[8];
        this.programRecyclerView = (LRecyclerView) bindings[10];
        this.programReloadButton = (TextView) bindings[9];
        this.title = (TextView) bindings[2];
        this.tvMessageCommentNum = (TextView) bindings[4];
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

    public static ActivityAllprogramBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllprogramBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAllprogramBinding) DataBindingUtil.inflate(inflater, R.layout.activity_allprogram, root, attachToRoot, bindingComponent);
    }

    public static ActivityAllprogramBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllprogramBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_allprogram, null, false), bindingComponent);
    }

    public static ActivityAllprogramBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAllprogramBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_allprogram_0".equals(view.getTag())) {
            return new ActivityAllprogramBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
