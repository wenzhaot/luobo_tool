package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.TextViewBindingAdapter;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.utils.FengUtil;

public class LayoutMessageHeadBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    private long mDirtyFlags = -1;
    private MessageCountInfo mMessageCountInfo;
    private final LinearLayout mboundView0;
    public final RelativeLayout rlMessageAiteContainer;
    public final RelativeLayout rlMessageCommentContainer;
    public final RelativeLayout rlMessageLetterContainer;
    public final RelativeLayout rlMessagePraiseContainer;
    public final RelativeLayout rlMessageSystemInfoContainer;
    public final TextView tvMessageAiteNum;
    public final TextView tvMessageCommentNum;
    public final TextView tvMessageLetterNum;
    public final TextView tvMessagePraiseNum;
    public final TextView tvMessageSystemInfoNum;

    static {
        sViewsWithIds.put(2131625218, 6);
        sViewsWithIds.put(2131625219, 7);
        sViewsWithIds.put(2131625221, 8);
        sViewsWithIds.put(2131625223, 9);
        sViewsWithIds.put(2131625225, 10);
    }

    public LayoutMessageHeadBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        Object[] bindings = mapBindings(bindingComponent, root, 11, sIncludes, sViewsWithIds);
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlMessageAiteContainer = (RelativeLayout) bindings[7];
        this.rlMessageCommentContainer = (RelativeLayout) bindings[6];
        this.rlMessageLetterContainer = (RelativeLayout) bindings[9];
        this.rlMessagePraiseContainer = (RelativeLayout) bindings[8];
        this.rlMessageSystemInfoContainer = (RelativeLayout) bindings[10];
        this.tvMessageAiteNum = (TextView) bindings[2];
        this.tvMessageAiteNum.setTag(null);
        this.tvMessageCommentNum = (TextView) bindings[1];
        this.tvMessageCommentNum.setTag(null);
        this.tvMessageLetterNum = (TextView) bindings[4];
        this.tvMessageLetterNum.setTag(null);
        this.tvMessagePraiseNum = (TextView) bindings[3];
        this.tvMessagePraiseNum.setTag(null);
        this.tvMessageSystemInfoNum = (TextView) bindings[5];
        this.tvMessageSystemInfoNum.setTag(null);
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
            case 38:
                setMessageCountInfo((MessageCountInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMessageCountInfo(MessageCountInfo MessageCountInfo) {
        this.mMessageCountInfo = MessageCountInfo;
        synchronized (this) {
            this.mDirtyFlags |= 1;
        }
        notifyPropertyChanged(38);
        super.requestRebind();
    }

    public MessageCountInfo getMessageCountInfo() {
        return this.mMessageCountInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        return false;
    }

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        int messageCountInfoPraiseCount = 0;
        String fengUtilNumberFormat99MessageCountInfoPraiseCount = null;
        int messageCountInfoCommentCount = 0;
        String fengUtilNumberFormat99MessageCountInfoCommentCount = null;
        int messageCountInfoAtCountInt0ViewVISIBLEViewGONE = 0;
        MessageCountInfo messageCountInfo = this.mMessageCountInfo;
        String fengUtilNumberFormat99MessageCountInfoReceivedCount = null;
        int messageCountInfoReceivedCount = 0;
        int messageCountInfoSysCountInt0ViewVISIBLEViewGONE = 0;
        int messageCountInfoAtCount = 0;
        int messageCountInfoReceivedCountInt0ViewVISIBLEViewGONE = 0;
        String fengUtilNumberFormat99MessageCountInfoAtCount = null;
        int messageCountInfoPraiseCountInt0ViewVISIBLEViewGONE = 0;
        String fengUtilNumberFormat99MessageCountInfoSysCount = null;
        int messageCountInfoSysCount = 0;
        int messageCountInfoCommentCountInt0ViewVISIBLEViewGONE = 0;
        if ((3 & dirtyFlags) != 0) {
            if (messageCountInfo != null) {
                messageCountInfoPraiseCount = messageCountInfo.praiseCount;
                messageCountInfoCommentCount = messageCountInfo.commentCount;
                messageCountInfoReceivedCount = messageCountInfo.receivedCount;
                messageCountInfoAtCount = messageCountInfo.atCount;
                messageCountInfoSysCount = messageCountInfo.sysCount;
            }
            fengUtilNumberFormat99MessageCountInfoPraiseCount = FengUtil.numberFormat99(messageCountInfoPraiseCount);
            boolean messageCountInfoPraiseCountInt0 = messageCountInfoPraiseCount > 0;
            fengUtilNumberFormat99MessageCountInfoCommentCount = FengUtil.numberFormat99(messageCountInfoCommentCount);
            boolean messageCountInfoCommentCountInt0 = messageCountInfoCommentCount > 0;
            fengUtilNumberFormat99MessageCountInfoReceivedCount = FengUtil.numberFormat99(messageCountInfoReceivedCount);
            boolean messageCountInfoReceivedCountInt0 = messageCountInfoReceivedCount > 0;
            boolean messageCountInfoAtCountInt0 = messageCountInfoAtCount > 0;
            fengUtilNumberFormat99MessageCountInfoAtCount = FengUtil.numberFormat99(messageCountInfoAtCount);
            boolean messageCountInfoSysCountInt0 = messageCountInfoSysCount > 0;
            fengUtilNumberFormat99MessageCountInfoSysCount = FengUtil.numberFormat99(messageCountInfoSysCount);
            if ((3 & dirtyFlags) != 0) {
                if (messageCountInfoPraiseCountInt0) {
                    dirtyFlags |= 512;
                } else {
                    dirtyFlags |= 256;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (messageCountInfoCommentCountInt0) {
                    dirtyFlags |= 2048;
                } else {
                    dirtyFlags |= 1024;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (messageCountInfoReceivedCountInt0) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (messageCountInfoAtCountInt0) {
                    dirtyFlags |= 8;
                } else {
                    dirtyFlags |= 4;
                }
            }
            if ((3 & dirtyFlags) != 0) {
                if (messageCountInfoSysCountInt0) {
                    dirtyFlags |= 32;
                } else {
                    dirtyFlags |= 16;
                }
            }
            messageCountInfoPraiseCountInt0ViewVISIBLEViewGONE = messageCountInfoPraiseCountInt0 ? 0 : 8;
            messageCountInfoCommentCountInt0ViewVISIBLEViewGONE = messageCountInfoCommentCountInt0 ? 0 : 8;
            messageCountInfoReceivedCountInt0ViewVISIBLEViewGONE = messageCountInfoReceivedCountInt0 ? 0 : 8;
            messageCountInfoAtCountInt0ViewVISIBLEViewGONE = messageCountInfoAtCountInt0 ? 0 : 8;
            messageCountInfoSysCountInt0ViewVISIBLEViewGONE = messageCountInfoSysCountInt0 ? 0 : 8;
        }
        if ((3 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvMessageAiteNum, fengUtilNumberFormat99MessageCountInfoAtCount);
            this.tvMessageAiteNum.setVisibility(messageCountInfoAtCountInt0ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvMessageCommentNum, fengUtilNumberFormat99MessageCountInfoCommentCount);
            this.tvMessageCommentNum.setVisibility(messageCountInfoCommentCountInt0ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvMessageLetterNum, fengUtilNumberFormat99MessageCountInfoReceivedCount);
            this.tvMessageLetterNum.setVisibility(messageCountInfoReceivedCountInt0ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvMessagePraiseNum, fengUtilNumberFormat99MessageCountInfoPraiseCount);
            this.tvMessagePraiseNum.setVisibility(messageCountInfoPraiseCountInt0ViewVISIBLEViewGONE);
            TextViewBindingAdapter.setText(this.tvMessageSystemInfoNum, fengUtilNumberFormat99MessageCountInfoSysCount);
            this.tvMessageSystemInfoNum.setVisibility(messageCountInfoSysCountInt0ViewVISIBLEViewGONE);
        }
    }

    public static LayoutMessageHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutMessageHeadBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (LayoutMessageHeadBinding) DataBindingUtil.inflate(inflater, 2130903290, root, attachToRoot, bindingComponent);
    }

    public static LayoutMessageHeadBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutMessageHeadBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903290, null, false), bindingComponent);
    }

    public static LayoutMessageHeadBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static LayoutMessageHeadBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/layout_message_head_0".equals(view.getTag())) {
            return new LayoutMessageHeadBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
