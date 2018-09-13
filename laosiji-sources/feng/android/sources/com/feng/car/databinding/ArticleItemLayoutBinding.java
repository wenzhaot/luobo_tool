package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
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
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.FengVideoPlayer;
import com.feng.car.view.ArticleRecommendView;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.largeimage.LargeImageView;
import com.feng.car.view.textview.AisenTextView;

public class ArticleItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final RelativeLayout choiceLine;
    public final TextView commentText;
    public final AisenTextView describe;
    public final TextView emptyText;
    public final LargeImageView longImage;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private final LinearLayout mboundView0;
    public final AutoFrescoDraweeView outLinkImage;
    public final RelativeLayout outLinkLine;
    public final AutoFrescoDraweeView postImage;
    public final AisenTextView postText;
    public final TextView praiseText;
    public final ArticleRecommendView recommendView;
    public final TextView tvOutLinkTitle;
    public final FengVideoPlayer videoPlayer;

    static {
        sViewsWithIds.put(2131624810, 3);
        sViewsWithIds.put(2131624811, 4);
        sViewsWithIds.put(2131624812, 5);
        sViewsWithIds.put(2131624726, 6);
        sViewsWithIds.put(2131624813, 7);
        sViewsWithIds.put(2131624814, 8);
        sViewsWithIds.put(2131624815, 9);
        sViewsWithIds.put(2131624807, 10);
        sViewsWithIds.put(2131624816, 11);
        sViewsWithIds.put(2131624817, 12);
        sViewsWithIds.put(2131624809, 13);
    }

    public ArticleItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 14, sIncludes, sViewsWithIds);
        this.choiceLine = (RelativeLayout) bindings[12];
        this.commentText = (TextView) bindings[1];
        this.commentText.setTag(null);
        this.describe = (AisenTextView) bindings[10];
        this.emptyText = (TextView) bindings[13];
        this.longImage = (LargeImageView) bindings[5];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.outLinkImage = (AutoFrescoDraweeView) bindings[8];
        this.outLinkLine = (RelativeLayout) bindings[7];
        this.postImage = (AutoFrescoDraweeView) bindings[4];
        this.postText = (AisenTextView) bindings[3];
        this.praiseText = (TextView) bindings[2];
        this.praiseText.setTag(null);
        this.recommendView = (ArticleRecommendView) bindings[11];
        this.tvOutLinkTitle = (TextView) bindings[9];
        this.videoPlayer = (FengVideoPlayer) bindings[6];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 8;
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
            this.mDirtyFlags |= 4;
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

    private boolean onChangeMSnsInfoPraisecount(ObservableInt MSnsInfoPraisecount, int fieldId) {
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
        int mSnsInfoPraisecountGet = 0;
        SnsInfo mSnsInfo = this.mMSnsInfo;
        ObservableInt mSnsInfoCommentcount = null;
        int mSnsInfoCommentcountGet = 0;
        String stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount = null;
        ObservableInt mSnsInfoPraisecount = null;
        String stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount = null;
        if ((15 & dirtyFlags) != 0) {
            if ((13 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoCommentcount = mSnsInfo.commentcount;
                }
                updateRegistration(0, mSnsInfoCommentcount);
                if (mSnsInfoCommentcount != null) {
                    mSnsInfoCommentcountGet = mSnsInfoCommentcount.get();
                }
                String fengUtilNumberFormatMSnsInfoCommentcount = FengUtil.numberFormat(mSnsInfoCommentcountGet);
                stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount = String.format(this.commentText.getResources().getString(2131230839), new Object[]{fengUtilNumberFormatMSnsInfoCommentcount});
            }
            if ((14 & dirtyFlags) != 0) {
                if (mSnsInfo != null) {
                    mSnsInfoPraisecount = mSnsInfo.praisecount;
                }
                updateRegistration(1, mSnsInfoPraisecount);
                if (mSnsInfoPraisecount != null) {
                    mSnsInfoPraisecountGet = mSnsInfoPraisecount.get();
                }
                String fengUtilNumberFormatMSnsInfoPraisecount = FengUtil.numberFormat(mSnsInfoPraisecountGet);
                stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount = String.format(this.praiseText.getResources().getString(2131230842), new Object[]{fengUtilNumberFormatMSnsInfoPraisecount});
            }
        }
        if ((13 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.commentText, stringFormatCommentTextAndroidStringArticleCommentTextFormatFengUtilNumberFormatMSnsInfoCommentcount);
        }
        if ((14 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.praiseText, stringFormatPraiseTextAndroidStringArticlePraiseTextFormatFengUtilNumberFormatMSnsInfoPraisecount);
        }
    }

    public static ArticleItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903160, root, attachToRoot, bindingComponent);
    }

    public static ArticleItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903160, null, false), bindingComponent);
    }

    public static ArticleItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_item_layout_0".equals(view.getTag())) {
            return new ArticleItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
