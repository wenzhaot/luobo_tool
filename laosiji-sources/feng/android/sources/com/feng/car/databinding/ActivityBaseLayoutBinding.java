package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.support.constraint.ConstraintLayout;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.feng.car.view.AudioSuspensionView;
import com.feng.car.view.recyclerview.EmptyView;

public class ActivityBaseLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = new IncludedLayouts(4);
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AudioSuspensionView audioSuspensionView;
    public final EmptyView emptyView;
    private long mDirtyFlags = -1;
    private Integer mUnwanted;
    public final ConstraintLayout rootView;
    public final NewTitleBarLayoutBinding titleLine;

    static {
        sIncludes.setIncludes(0, new String[]{"new_title_bar_layout"}, new int[]{1}, new int[]{2130903319});
        sViewsWithIds.put(2131624276, 2);
        sViewsWithIds.put(2131624231, 3);
    }

    public ActivityBaseLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.audioSuspensionView = (AudioSuspensionView) bindings[2];
        this.emptyView = (EmptyView) bindings[3];
        this.rootView = (ConstraintLayout) bindings[0];
        this.rootView.setTag(null);
        this.titleLine = (NewTitleBarLayoutBinding) bindings[1];
        setContainedBinding(this.titleLine);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
        }
        this.titleLine.invalidateAll();
        requestRebind();
    }

    /* JADX WARNING: Missing block: B:8:0x0013, code:
            if (r6.titleLine.hasPendingBindings() != false) goto L_?;
     */
    /* JADX WARNING: Missing block: B:18:?, code:
            return true;
     */
    /* JADX WARNING: Missing block: B:19:?, code:
            return false;
     */
    public boolean hasPendingBindings() {
        /*
        r6 = this;
        r0 = 1;
        monitor-enter(r6);
        r2 = r6.mDirtyFlags;	 Catch:{ all -> 0x0017 }
        r4 = 0;
        r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r1 == 0) goto L_0x000c;
    L_0x000a:
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
    L_0x000b:
        return r0;
    L_0x000c:
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        r1 = r6.titleLine;
        r1 = r1.hasPendingBindings();
        if (r1 != 0) goto L_0x000b;
    L_0x0015:
        r0 = 0;
        goto L_0x000b;
    L_0x0017:
        r0 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0017 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.databinding.ActivityBaseLayoutBinding.hasPendingBindings():boolean");
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
        switch (localFieldId) {
            case 0:
                return onChangeTitleLine((NewTitleBarLayoutBinding) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeTitleLine(NewTitleBarLayoutBinding TitleLine, int fieldId) {
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
        synchronized (this) {
            long dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        executeBindingsOn(this.titleLine);
    }

    public static ActivityBaseLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityBaseLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityBaseLayoutBinding) DataBindingUtil.inflate(inflater, 2130903075, root, attachToRoot, bindingComponent);
    }

    public static ActivityBaseLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityBaseLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903075, null, false), bindingComponent);
    }

    public static ActivityBaseLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityBaseLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_base_layout_0".equals(view.getTag())) {
            return new ActivityBaseLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
