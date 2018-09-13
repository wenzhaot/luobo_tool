package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.view.ClearEditText;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityAddSubjectBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView back;
    public final ClearEditText etSearchKey;
    public final LRecyclerView lrcAddSubject;
    public final LRecyclerView lrcAddSubjectSearch;
    private long mDirtyFlags = -1;
    public final LinearLayout parent;
    public final RelativeLayout rlTitleBar;
    public final RecyclerView rvSelectedSubject;
    public final TextView tvFinish;
    public final TextView tvTitleName;

    static {
        sViewsWithIds.put(R.id.rl_title_bar, 1);
        sViewsWithIds.put(R.id.back, 2);
        sViewsWithIds.put(R.id.tv_title_name, 3);
        sViewsWithIds.put(R.id.tv_finish, 4);
        sViewsWithIds.put(R.id.et_search_key, 5);
        sViewsWithIds.put(R.id.rv_selected_subject, 6);
        sViewsWithIds.put(R.id.lrc_add_subject, 7);
        sViewsWithIds.put(R.id.lrc_add_subject_search, 8);
    }

    public ActivityAddSubjectBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 9, sIncludes, sViewsWithIds);
        this.back = (TextView) bindings[2];
        this.etSearchKey = (ClearEditText) bindings[5];
        this.lrcAddSubject = (LRecyclerView) bindings[7];
        this.lrcAddSubjectSearch = (LRecyclerView) bindings[8];
        this.parent = (LinearLayout) bindings[0];
        this.parent.setTag(null);
        this.rlTitleBar = (RelativeLayout) bindings[1];
        this.rvSelectedSubject = (RecyclerView) bindings[6];
        this.tvFinish = (TextView) bindings[4];
        this.tvTitleName = (TextView) bindings[3];
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

    public static ActivityAddSubjectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAddSubjectBinding) DataBindingUtil.inflate(inflater, R.layout.activity_add_subject, root, attachToRoot, bindingComponent);
    }

    public static ActivityAddSubjectBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_add_subject, null, false), bindingComponent);
    }

    public static ActivityAddSubjectBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAddSubjectBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_add_subject_0".equals(view.getTag())) {
            return new ActivityAddSubjectBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
