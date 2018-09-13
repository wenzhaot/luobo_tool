package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.R;

public class ActivityAddSubjectHeaderBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageButton ibClear;
    private long mDirtyFlags = -1;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlHistorySubject;
    public final RecyclerView rvHistorySubject;
    public final TextView tvRecommendTopicTips;

    static {
        sViewsWithIds.put(R.id.rl_history_subject, 1);
        sViewsWithIds.put(R.id.ib_clear, 2);
        sViewsWithIds.put(R.id.rv_history_subject, 3);
        sViewsWithIds.put(R.id.tv_recommend_topic_tips, 4);
    }

    public ActivityAddSubjectHeaderBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.ibClear = (ImageButton) bindings[2];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlHistorySubject = (RelativeLayout) bindings[1];
        this.rvHistorySubject = (RecyclerView) bindings[3];
        this.tvRecommendTopicTips = (TextView) bindings[4];
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

    public static ActivityAddSubjectHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectHeaderBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAddSubjectHeaderBinding) DataBindingUtil.inflate(inflater, R.layout.activity_add_subject_header, root, attachToRoot, bindingComponent);
    }

    public static ActivityAddSubjectHeaderBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectHeaderBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_add_subject_header, null, false), bindingComponent);
    }

    public static ActivityAddSubjectHeaderBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectHeaderBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_add_subject_header_0".equals(view.getTag())) {
            return new ActivityAddSubjectHeaderBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
