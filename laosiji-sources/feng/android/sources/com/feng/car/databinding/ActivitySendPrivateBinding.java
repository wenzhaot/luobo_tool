package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.library.emoticons.keyboard.XhsEmoticonsKeyBoard;
import com.feng.library.emoticons.keyboard.widget.UserDefEmoticonsEditText;

public class ActivitySendPrivateBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View closeKeyBoard;
    public final UserDefEmoticonsEditText etDefEmoticonsText;
    public final ImageButton ibDel;
    public final AutoFrescoDraweeView ivImage;
    public final XhsEmoticonsKeyBoard kbDefEmoticons;
    private long mDirtyFlags = -1;
    private int mUnWanted;

    static {
        sViewsWithIds.put(2131624438, 1);
        sViewsWithIds.put(2131624608, 2);
        sViewsWithIds.put(2131624609, 3);
        sViewsWithIds.put(2131624607, 4);
    }

    public ActivitySendPrivateBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.closeKeyBoard = (View) bindings[3];
        this.etDefEmoticonsText = (UserDefEmoticonsEditText) bindings[4];
        this.ibDel = (ImageButton) bindings[2];
        this.ivImage = (AutoFrescoDraweeView) bindings[1];
        this.kbDefEmoticons = (XhsEmoticonsKeyBoard) bindings[0];
        this.kbDefEmoticons.setTag(null);
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
            case 70:
                setUnWanted(((Integer) variable).intValue());
                return true;
            default:
                return false;
        }
    }

    public void setUnWanted(int UnWanted) {
        this.mUnWanted = UnWanted;
    }

    public int getUnWanted() {
        return this.mUnWanted;
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

    public static ActivitySendPrivateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendPrivateBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivitySendPrivateBinding) DataBindingUtil.inflate(inflater, 2130903133, root, attachToRoot, bindingComponent);
    }

    public static ActivitySendPrivateBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendPrivateBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903133, null, false), bindingComponent);
    }

    public static ActivitySendPrivateBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivitySendPrivateBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_send_private_0".equals(view.getTag())) {
            return new ActivitySendPrivateBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
