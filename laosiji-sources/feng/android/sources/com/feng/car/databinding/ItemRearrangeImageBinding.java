package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.view.AutoFrescoDraweeView;

public class ItemRearrangeImageBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private PostEdit mInfo;
    private final RelativeLayout mboundView0;
    public final AutoFrescoDraweeView simpledraweeview;
    public final TextView tvTime;

    static {
        sViewsWithIds.put(2131624921, 2);
    }

    public ItemRearrangeImageBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 1);
        Object[] bindings = mapBindings(bindingComponent, root, 3, sIncludes, sViewsWithIds);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.simpledraweeview = (AutoFrescoDraweeView) bindings[1];
        this.simpledraweeview.setTag(null);
        this.tvTime = (TextView) bindings[2];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 4;
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
            case 28:
                setInfo((PostEdit) variable);
                return true;
            default:
                return false;
        }
    }

    public void setInfo(PostEdit Info) {
        updateRegistration(0, Info);
        this.mInfo = Info;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(28);
        super.requestRebind();
    }

    public PostEdit getInfo() {
        return this.mInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeInfo((PostEdit) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeInfo(PostEdit Info, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 1;
                }
                return true;
            case 69:
                synchronized (this) {
                    this.mDirtyFlags |= 2;
                }
                return true;
            default:
                return false;
        }
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        int infoTypeInt1ViewINVISIBLEViewVISIBLE = 0;
        int infoType = 0;
        PostEdit info = this.mInfo;
        if ((dirtyFlags & 7) != 0) {
            boolean infoTypeInt1;
            if (info != null) {
                infoType = info.getType();
            }
            if (infoType == -1) {
                infoTypeInt1 = true;
            } else {
                infoTypeInt1 = false;
            }
            if ((dirtyFlags & 7) != 0) {
                if (infoTypeInt1) {
                    dirtyFlags |= 16;
                } else {
                    dirtyFlags |= 8;
                }
            }
            if (infoTypeInt1) {
                infoTypeInt1ViewINVISIBLEViewVISIBLE = 4;
            } else {
                infoTypeInt1ViewINVISIBLEViewVISIBLE = 0;
            }
        }
        if ((dirtyFlags & 7) != 0) {
            this.simpledraweeview.setVisibility(infoTypeInt1ViewINVISIBLEViewVISIBLE);
        }
    }

    public static ItemRearrangeImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ItemRearrangeImageBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ItemRearrangeImageBinding) DataBindingUtil.inflate(inflater, 2130903278, root, attachToRoot, bindingComponent);
    }

    public static ItemRearrangeImageBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ItemRearrangeImageBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903278, null, false), bindingComponent);
    }

    public static ItemRearrangeImageBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ItemRearrangeImageBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/item_rearrange_image_0".equals(view.getTag())) {
            return new ItemRearrangeImageBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
