package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableBoolean;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.entity.privatemsg.MessageMarkInfo;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.MyLinearLayout;
import com.feng.car.view.textview.AisenTextView;

public class PrivateChatItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final View bottomView;
    public final AutoFrescoDraweeView hpvMine;
    public final AutoFrescoDraweeView hpvOther;
    public final ImageView ivPrivateMySelect;
    public final ImageView ivPrivateOtherSelect;
    public final LinearLayout llHpv;
    public final MyLinearLayout llPrivate;
    private long mDirtyFlags = -1;
    private MessageMarkInfo mMarkInfo;
    private MessageInfo mMsgInfo;
    public final AutoFrescoDraweeView myImage;
    public final RelativeLayout myLine;
    public final AisenTextView mySummary;
    public final AutoFrescoDraweeView otherImage;
    public final RelativeLayout otherLine;
    public final AisenTextView otherSummary;
    public final TextView tvMyTime;
    public final TextView tvOtherTime;

    static {
        sViewsWithIds.put(2131625413, 3);
        sViewsWithIds.put(2131625414, 4);
        sViewsWithIds.put(2131625416, 5);
        sViewsWithIds.put(2131625417, 6);
        sViewsWithIds.put(2131625415, 7);
        sViewsWithIds.put(2131625419, 8);
        sViewsWithIds.put(2131625420, 9);
        sViewsWithIds.put(2131625421, 10);
        sViewsWithIds.put(2131625423, 11);
        sViewsWithIds.put(2131625424, 12);
        sViewsWithIds.put(2131625425, 13);
        sViewsWithIds.put(2131625426, 14);
    }

    public PrivateChatItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.bottomView = (View) bindings[14];
        this.hpvMine = (AutoFrescoDraweeView) bindings[11];
        this.hpvOther = (AutoFrescoDraweeView) bindings[8];
        this.ivPrivateMySelect = (ImageView) bindings[2];
        this.ivPrivateMySelect.setTag(null);
        this.ivPrivateOtherSelect = (ImageView) bindings[1];
        this.ivPrivateOtherSelect.setTag(null);
        this.llHpv = (LinearLayout) bindings[7];
        this.llPrivate = (MyLinearLayout) bindings[0];
        this.llPrivate.setTag(null);
        this.myImage = (AutoFrescoDraweeView) bindings[13];
        this.myLine = (RelativeLayout) bindings[9];
        this.mySummary = (AisenTextView) bindings[12];
        this.otherImage = (AutoFrescoDraweeView) bindings[6];
        this.otherLine = (RelativeLayout) bindings[3];
        this.otherSummary = (AisenTextView) bindings[5];
        this.tvMyTime = (TextView) bindings[10];
        this.tvOtherTime = (TextView) bindings[4];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 16;
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
            case 37:
                setMarkInfo((MessageMarkInfo) variable);
                return true;
            case 42:
                setMsgInfo((MessageInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMarkInfo(MessageMarkInfo MarkInfo) {
        this.mMarkInfo = MarkInfo;
        synchronized (this) {
            this.mDirtyFlags |= 4;
        }
        notifyPropertyChanged(37);
        super.requestRebind();
    }

    public MessageMarkInfo getMarkInfo() {
        return this.mMarkInfo;
    }

    public void setMsgInfo(MessageInfo MsgInfo) {
        this.mMsgInfo = MsgInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(42);
        super.requestRebind();
    }

    public MessageInfo getMsgInfo() {
        return this.mMsgInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMsgInfoIsSelect((ObservableBoolean) object, fieldId);
            case 1:
                return onChangeMarkInfoIsShow((ObservableBoolean) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMsgInfoIsSelect(ObservableBoolean MsgInfoIsSelect, int fieldId) {
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

    private boolean onChangeMarkInfoIsShow(ObservableBoolean MarkInfoIsShow, int fieldId) {
        switch (fieldId) {
            case 0:
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
        MessageMarkInfo markInfo = this.mMarkInfo;
        MessageInfo msgInfo = this.mMsgInfo;
        ObservableBoolean msgInfoIsSelect = null;
        int markInfoIsShowViewVISIBLEViewGONE = 0;
        boolean msgInfoIsSelectGet = false;
        Drawable msgInfoIsSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectNone = null;
        ObservableBoolean markInfoIsShow = null;
        boolean markInfoIsShowGet = false;
        Drawable msgInfoIsSelectIvPrivateMySelectAndroidDrawablePrivateSelectIvPrivateMySelectAndroidDrawablePrivateSelectNone = null;
        if ((22 & dirtyFlags) != 0) {
            if (markInfo != null) {
                markInfoIsShow = markInfo.isShow;
            }
            updateRegistration(1, markInfoIsShow);
            if (markInfoIsShow != null) {
                markInfoIsShowGet = markInfoIsShow.get();
            }
            if ((22 & dirtyFlags) != 0) {
                if (markInfoIsShowGet) {
                    dirtyFlags |= 64;
                } else {
                    dirtyFlags |= 32;
                }
            }
            markInfoIsShowViewVISIBLEViewGONE = markInfoIsShowGet ? 0 : 8;
        }
        if ((25 & dirtyFlags) != 0) {
            if (msgInfo != null) {
                msgInfoIsSelect = msgInfo.isSelect;
            }
            updateRegistration(0, msgInfoIsSelect);
            if (msgInfoIsSelect != null) {
                msgInfoIsSelectGet = msgInfoIsSelect.get();
            }
            if ((25 & dirtyFlags) != 0) {
                if (msgInfoIsSelectGet) {
                    dirtyFlags = (dirtyFlags | 256) | 1024;
                } else {
                    dirtyFlags = (dirtyFlags | 128) | 512;
                }
            }
            msgInfoIsSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectNone = msgInfoIsSelectGet ? getDrawableFromResource(this.ivPrivateOtherSelect, 2130838402) : getDrawableFromResource(this.ivPrivateOtherSelect, 2130838403);
            msgInfoIsSelectIvPrivateMySelectAndroidDrawablePrivateSelectIvPrivateMySelectAndroidDrawablePrivateSelectNone = msgInfoIsSelectGet ? getDrawableFromResource(this.ivPrivateMySelect, 2130838402) : getDrawableFromResource(this.ivPrivateMySelect, 2130838403);
        }
        if ((25 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivPrivateMySelect, msgInfoIsSelectIvPrivateMySelectAndroidDrawablePrivateSelectIvPrivateMySelectAndroidDrawablePrivateSelectNone);
            ImageViewBindingAdapter.setImageDrawable(this.ivPrivateOtherSelect, msgInfoIsSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectIvPrivateOtherSelectAndroidDrawablePrivateSelectNone);
        }
        if ((22 & dirtyFlags) != 0) {
            this.ivPrivateMySelect.setVisibility(markInfoIsShowViewVISIBLEViewGONE);
            this.ivPrivateOtherSelect.setVisibility(markInfoIsShowViewVISIBLEViewGONE);
            MyLinearLayout.setBlock(this.llPrivate, markInfoIsShowGet);
        }
    }

    public static PrivateChatItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateChatItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (PrivateChatItemBinding) DataBindingUtil.inflate(inflater, 2130903357, root, attachToRoot, bindingComponent);
    }

    public static PrivateChatItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateChatItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903357, null, false), bindingComponent);
    }

    public static PrivateChatItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static PrivateChatItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/private_chat_item_0".equals(view.getTag())) {
            return new PrivateChatItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
