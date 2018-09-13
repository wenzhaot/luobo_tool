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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ChoicenessHeaderLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adNotice;
    private long mDirtyFlags = -1;
    private SnsInfo mSnsInfo;
    private final RelativeLayout mboundView0;
    public final RecyclerView rvHomeMenu;
    public final RecyclerView rvSeeCars;
    public final TextView tvAdLabel;
    public final View vAdLine;

    static {
        sViewsWithIds.put(2131624917, 1);
        sViewsWithIds.put(2131624918, 2);
        sViewsWithIds.put(2131624474, 3);
        sViewsWithIds.put(2131624476, 4);
        sViewsWithIds.put(2131624475, 5);
    }

    public ChoicenessHeaderLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.adNotice = (AutoFrescoDraweeView) bindings[3];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rvHomeMenu = (RecyclerView) bindings[1];
        this.rvSeeCars = (RecyclerView) bindings[2];
        this.tvAdLabel = (TextView) bindings[5];
        this.vAdLine = (View) bindings[4];
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
            case 62:
                setSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSnsInfo(SnsInfo SnsInfo) {
        this.mSnsInfo = SnsInfo;
    }

    public SnsInfo getSnsInfo() {
        return this.mSnsInfo;
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

    public static ChoicenessHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ChoicenessHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ChoicenessHeaderLayoutBinding) DataBindingUtil.inflate(inflater, 2130903183, root, attachToRoot, bindingComponent);
    }

    public static ChoicenessHeaderLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ChoicenessHeaderLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903183, null, false), bindingComponent);
    }

    public static ChoicenessHeaderLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ChoicenessHeaderLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/choiceness_header_layout_0".equals(view.getTag())) {
            return new ChoicenessHeaderLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
