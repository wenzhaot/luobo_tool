package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class PrivateLetterItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView content;
    public final RelativeLayout frontLetter;
    public final AutoFrescoDraweeView hpvPrivateLetter;
    public final LinearLayout linearLayout2;
    private long mDirtyFlags = -1;
    private MessageInfo mMessageInfo;
    private final LinearLayout mboundView0;
    public final TextView name;
    public final TextView time;
    public final TextView tvLetterNum;

    static {
        sViewsWithIds.put(2131625427, 1);
        sViewsWithIds.put(2131625428, 2);
        sViewsWithIds.put(2131625429, 3);
        sViewsWithIds.put(2131624750, 4);
        sViewsWithIds.put(2131624874, 5);
        sViewsWithIds.put(2131624792, 6);
        sViewsWithIds.put(2131625279, 7);
    }

    public PrivateLetterItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.content = (TextView) bindings[6];
        this.frontLetter = (RelativeLayout) bindings[1];
        this.hpvPrivateLetter = (AutoFrescoDraweeView) bindings[2];
        this.linearLayout2 = (LinearLayout) bindings[3];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.name = (TextView) bindings[4];
        this.time = (TextView) bindings[5];
        this.tvLetterNum = (TextView) bindings[7];
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
            case 39:
                setMessageInfo((MessageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMessageInfo(MessageInfo MessageInfo) {
        this.mMessageInfo = MessageInfo;
    }

    public MessageInfo getMessageInfo() {
        return this.mMessageInfo;
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

    public static PrivateLetterItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateLetterItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PrivateLetterItemBinding) DataBindingUtil.inflate(inflater, 2130903358, root, attachToRoot, bindingComponent);
    }

    public static PrivateLetterItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateLetterItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903358, null, false), bindingComponent);
    }

    public static PrivateLetterItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateLetterItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/private_letter_item_0".equals(view.getTag())) {
            return new PrivateLetterItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
