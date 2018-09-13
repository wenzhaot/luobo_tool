package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;

public class PostDragHeadBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final UserDefEmoticonsEditText etDigestText;
    public final LinearLayout line1;
    private long mDirtyFlags = -1;
    private String mStr;
    public final EditText postDragHeadEdit;

    static {
        sViewsWithIds.put(2131625366, 1);
        sViewsWithIds.put(2131625367, 2);
    }

    public PostDragHeadBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.etDigestText = (UserDefEmoticonsEditText) bindings[2];
        this.line1 = (LinearLayout) bindings[0];
        this.line1.setTag(null);
        this.postDragHeadEdit = (EditText) bindings[1];
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

    public static PostDragHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PostDragHeadBinding) DataBindingUtil.inflate(inflater, 2130903347, root, attachToRoot, bindingComponent);
    }

    public static PostDragHeadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragHeadBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903347, null, false), bindingComponent);
    }

    public static PostDragHeadBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PostDragHeadBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/post_drag_head_0".equals(view.getTag())) {
            return new PostDragHeadBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
