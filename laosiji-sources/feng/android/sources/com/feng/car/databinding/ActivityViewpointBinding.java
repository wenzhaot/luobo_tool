package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.FengUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerView;

public class ActivityViewpointBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final RelativeLayout bottomLine;
    public final TextView commentText;
    public final View divider;
    public final ImageView ivForward;
    public final ImageView ivMessage;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private final RelativeLayout mboundView0;
    public final ImageView praiseLine;
    public final TextView praiseText;
    public final LRecyclerView recyclerView;
    public final RelativeLayout rlComment;
    public final RelativeLayout rlPraise;
    public final RelativeLayout topLine;
    public final TextView tvMessageNum;
    public final TextView tvPraiseNum;
    public final TextView tvSendMessage;

    static {
        sViewsWithIds.put(2131624249, 7);
        sViewsWithIds.put(2131624240, 8);
        sViewsWithIds.put(2131624241, 9);
        sViewsWithIds.put(2131624242, 10);
        sViewsWithIds.put(2131624245, 11);
        sViewsWithIds.put(2131624247, 12);
        sViewsWithIds.put(2131624248, 13);
        sViewsWithIds.put(2131624250, 14);
    }

    public ActivityViewpointBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 3);
        Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.bottomLine = (RelativeLayout) bindings[9];
        this.commentText = (TextView) bindings[5];
        this.commentText.setTag(null);
        this.divider = (View) bindings[8];
        this.ivForward = (ImageView) bindings[12];
        this.ivMessage = (ImageView) bindings[3];
        this.ivMessage.setTag(null);
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.praiseLine = (ImageView) bindings[1];
        this.praiseLine.setTag(null);
        this.praiseText = (TextView) bindings[6];
        this.praiseText.setTag(null);
        this.recyclerView = (LRecyclerView) bindings[7];
        this.rlComment = (RelativeLayout) bindings[11];
        this.rlPraise = (RelativeLayout) bindings[10];
        this.topLine = (RelativeLayout) bindings[14];
        this.tvMessageNum = (TextView) bindings[4];
        this.tvMessageNum.setTag(null);
        this.tvPraiseNum = (TextView) bindings[2];
        this.tvPraiseNum.setTag(null);
        this.tvSendMessage = (TextView) bindings[13];
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
            case 36:
                setMSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMSnsInfo(SnsInfo MSnsInfo) {
        this.mMSnsInfo = MSnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(36);
        super.requestRebind();
    }

    public SnsInfo getMSnsInfo() {
        return this.mMSnsInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMSnsInfoCommentcount((ObservableInt) object, fieldId);
            case 1:
                return onChangeMSnsInfoIspraise((ObservableInt) object, fieldId);
            case 2:
                return onChangeMSnsInfoPraisecount((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMSnsInfoCommentcount(ObservableInt MSnsInfoCommentcount, int fieldId) {
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

    private boolean onChangeMSnsInfoIspraise(ObservableInt MSnsInfoIspraise, int fieldId) {
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

    private boolean onChangeMSnsInfoPraisecount(ObservableInt MSnsInfoPraisecount, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 4;
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
        int mSnsInfoPraisecountGet = 0;
        SnsInfo mSnsInfo = this.mMSnsInfo;
        ObservableInt mSnsInfoCommentcount = null;
        Drawable mSnsInfoCommentcountInt0IvMessageAndroidDrawableIconPinglunAddIvMessageAndroidDrawableIconPinglunMsg = null;
        Drawable mSnsInfoIspraiseInt0PraiseLineAndroidDrawableIconPraiseNewBlPraiseLineAndroidDrawableIconPraiseNewYl = null;
        int mSnsInfoCommentcountGet = 0;
        ObservableInt mSnsInfoIspraise = null;
        String fengUtilNumberFormatPeopleMSnsInfoCommentcount = null;
        String fengUtilNumberFormatPeopleMSnsInfoPraisecount = null;
        int mSnsInfoIspraiseGet = 0;
        String stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount = null;
        ObservableInt mSnsInfoPraisecount = null;
        int mSnsInfoPraisecountInt0ViewVISIBLEViewGONE = 0;
        int mSnsInfoCommentcountInt0ViewVISIBLEViewGONE = 0;
        String stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount = null;
        if ((31 & dirtyFlags) != 0) {
            if ((25 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoCommentcount = mSnsInfo.commentcount;
                }
                updateRegistration(0, mSnsInfoCommentcount);
                if (mSnsInfoCommentcount != null) {
                    mSnsInfoCommentcountGet = mSnsInfoCommentcount.get();
                }
                fengUtilNumberFormatPeopleMSnsInfoCommentcount = FengUtil.numberFormatPeople(mSnsInfoCommentcountGet);
                String fengUtilNumberFormatMSnsInfoCommentcount = FengUtil.numberFormat(mSnsInfoCommentcountGet);
                boolean mSnsInfoCommentcountInt0 = mSnsInfoCommentcountGet == 0;
                boolean MSnsInfoCommentcountInt01 = mSnsInfoCommentcountGet > 0;
                if ((25 & dirtyFlags) != 0) {
                    if (mSnsInfoCommentcountInt0) {
                        dirtyFlags |= 64;
                    } else {
                        dirtyFlags |= 32;
                    }
                }
                if ((25 & dirtyFlags) != 0) {
                    if (MSnsInfoCommentcountInt01) {
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
                stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount = String.format(this.commentText.getResources().getString(2131230839), new Object[]{fengUtilNumberFormatMSnsInfoCommentcount});
                mSnsInfoCommentcountInt0IvMessageAndroidDrawableIconPinglunAddIvMessageAndroidDrawableIconPinglunMsg = mSnsInfoCommentcountInt0 ? getDrawableFromResource(this.ivMessage, 2130838171) : getDrawableFromResource(this.ivMessage, 2130838172);
                mSnsInfoCommentcountInt0ViewVISIBLEViewGONE = MSnsInfoCommentcountInt01 ? 0 : 8;
            }
            if ((26 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoIspraise = mSnsInfo.ispraise;
                }
                updateRegistration(1, mSnsInfoIspraise);
                if (mSnsInfoIspraise != null) {
                    mSnsInfoIspraiseGet = mSnsInfoIspraise.get();
                }
                boolean mSnsInfoIspraiseInt0 = mSnsInfoIspraiseGet == 0;
                if ((26 & dirtyFlags) != 0) {
                    if (mSnsInfoIspraiseInt0) {
                        dirtyFlags |= 256;
                    } else {
                        dirtyFlags |= 128;
                    }
                }
                if (mSnsInfoIspraiseInt0) {
                    mSnsInfoIspraiseInt0PraiseLineAndroidDrawableIconPraiseNewBlPraiseLineAndroidDrawableIconPraiseNewYl = getDrawableFromResource(this.praiseLine, 2130838179);
                } else {
                    mSnsInfoIspraiseInt0PraiseLineAndroidDrawableIconPraiseNewBlPraiseLineAndroidDrawableIconPraiseNewYl = getDrawableFromResource(this.praiseLine, 2130838180);
                }
            }
            if ((28 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoPraisecount = mSnsInfo.praisecount;
                }
                updateRegistration(2, mSnsInfoPraisecount);
                if (mSnsInfoPraisecount != null) {
                    mSnsInfoPraisecountGet = mSnsInfoPraisecount.get();
                }
                String fengUtilNumberFormatMSnsInfoPraisecount = FengUtil.numberFormat(mSnsInfoPraisecountGet);
                fengUtilNumberFormatPeopleMSnsInfoPraisecount = FengUtil.numberFormatPeople(mSnsInfoPraisecountGet);
                boolean mSnsInfoPraisecountInt0 = mSnsInfoPraisecountGet > 0;
                if ((28 & dirtyFlags) != 0) {
                    if (mSnsInfoPraisecountInt0) {
                        dirtyFlags |= 1024;
                    } else {
                        dirtyFlags |= 512;
                    }
                }
                stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount = String.format(this.praiseText.getResources().getString(2131230842), new Object[]{fengUtilNumberFormatMSnsInfoPraisecount});
                mSnsInfoPraisecountInt0ViewVISIBLEViewGONE = mSnsInfoPraisecountInt0 ? 0 : 8;
            }
        }
        if ((25 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.commentText, stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount);
            ImageViewBindingAdapter.setImageDrawable(this.ivMessage, mSnsInfoCommentcountInt0IvMessageAndroidDrawableIconPinglunAddIvMessageAndroidDrawableIconPinglunMsg);
            TextViewBindingAdapter.setText(this.tvMessageNum, fengUtilNumberFormatPeopleMSnsInfoCommentcount);
            this.tvMessageNum.setVisibility(mSnsInfoCommentcountInt0ViewVISIBLEViewGONE);
        }
        if ((26 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.praiseLine, mSnsInfoIspraiseInt0PraiseLineAndroidDrawableIconPraiseNewBlPraiseLineAndroidDrawableIconPraiseNewYl);
        }
        if ((28 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.praiseText, stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount);
            TextViewBindingAdapter.setText(this.tvPraiseNum, fengUtilNumberFormatPeopleMSnsInfoPraisecount);
            this.tvPraiseNum.setVisibility(mSnsInfoPraisecountInt0ViewVISIBLEViewGONE);
        }
    }

    public static ActivityViewpointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityViewpointBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityViewpointBinding) DataBindingUtil.inflate(inflater, 2130903149, root, attachToRoot, bindingComponent);
    }

    public static ActivityViewpointBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityViewpointBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903149, null, false), bindingComponent);
    }

    public static ActivityViewpointBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityViewpointBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_viewpoint_0".equals(view.getTag())) {
            return new ActivityViewpointBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
