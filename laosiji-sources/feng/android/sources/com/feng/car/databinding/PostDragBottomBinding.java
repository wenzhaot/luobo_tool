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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.library.R;

public class PostDragBottomBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final ImageView ivIcon;
    private long mDirtyFlags = -1;
    private String mStr;
    public final RelativeLayout rlBottomParent;
    public final TextView tvBottomGoods;
    public final TextView tvBottomGoodsNum;

    static {
        sViewsWithIds.put(R.id.iv_icon, 1);
        sViewsWithIds.put(2131625364, 2);
        sViewsWithIds.put(2131625365, 3);
    }

    public PostDragBottomBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 4, sIncludes, sViewsWithIds);
        this.ivIcon = (ImageView) bindings[1];
        this.rlBottomParent = (RelativeLayout) bindings[0];
        this.rlBottomParent.setTag(null);
        this.tvBottomGoods = (TextView) bindings[2];
        this.tvBottomGoodsNum = (TextView) bindings[3];
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
            case 65:
                setStr((String) variable);
                return true;
            default:
                return false;
        }
    }

    public void setStr(String Str) {
        this.mStr = Str;
    }

    public String getStr() {
        return this.mStr;
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

    public static PostDragBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragBottomBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PostDragBottomBinding) DataBindingUtil.inflate(inflater, 2130903346, root, attachToRoot, bindingComponent);
    }

    public static PostDragBottomBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragBottomBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903346, null, false), bindingComponent);
    }

    public static PostDragBottomBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragBottomBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/post_drag_bottom_0".equals(view.getTag())) {
            return new PostDragBottomBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
