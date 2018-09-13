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
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityPrivateChatBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final LinearLayout bottom;
    public final TextView commentText;
    private long mDirtyFlags = -1;
    private MessageInfo mMessageInfo;
    private final RelativeLayout mboundView0;
    public final LRecyclerView rcviewPrivateChat;
    public final TextView tvPrivateDeleteBtn;

    static {
        sViewsWithIds.put(2131624098, 1);
        sViewsWithIds.put(2131624551, 2);
        sViewsWithIds.put(2131624552, 3);
        sViewsWithIds.put(2131624553, 4);
    }

    public ActivityPrivateChatBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.bottom = (LinearLayout) bindings[1];
        this.commentText = (TextView) bindings[2];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rcviewPrivateChat = (LRecyclerView) bindings[3];
        this.tvPrivateDeleteBtn = (TextView) bindings[4];
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

    public static ActivityPrivateChatBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateChatBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityPrivateChatBinding) DataBindingUtil.inflate(inflater, 2130903113, root, attachToRoot, bindingComponent);
    }

    public static ActivityPrivateChatBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateChatBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903113, null, false), bindingComponent);
    }

    public static ActivityPrivateChatBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityPrivateChatBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_private_chat_0".equals(view.getTag())) {
            return new ActivityPrivateChatBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
