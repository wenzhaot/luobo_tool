package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;
import com.github.jdsjlzx.R;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class AtContentItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdCover;
    public final AisenTextView atDigestDescription;
    public final AutoFrescoDraweeView fdvSnsDetailImg;
    public final ImageView ivGood;
    public final LinearLayout llComment;
    public final LinearLayout llGood;
    public final LinearLayout llParent;
    private long mDirtyFlags = -1;
    private SnsInfo mSnsInfo;
    private final ImageView mboundView1;
    private final TextView mboundView2;
    private final TextView mboundView6;
    public final RelativeLayout rlAtMine;
    public final RelativeLayout rlUserInfo;
    public final TextView tvCommentNum;
    public final TextView tvGoodNum;
    public final TextView tvSnsContent;
    public final TextView tvSnsUsername;
    public final AutoFrescoDraweeView userImage;

    static {
        sViewsWithIds.put(2131624788, 7);
        sViewsWithIds.put(2131624269, 8);
        sViewsWithIds.put(2131624838, 9);
        sViewsWithIds.put(2131624839, 10);
        sViewsWithIds.put(2131624840, 11);
        sViewsWithIds.put(2131624841, 12);
        sViewsWithIds.put(2131624842, 13);
        sViewsWithIds.put(2131624843, 14);
        sViewsWithIds.put(2131624844, 15);
        sViewsWithIds.put(2131624846, 16);
    }

    public AtContentItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 5);
        Object[] bindings = mapBindings(bindingComponent, root, 17, sIncludes, sViewsWithIds);
        this.afdCover = (AutoFrescoDraweeView) bindings[10];
        this.atDigestDescription = (AisenTextView) bindings[9];
        this.fdvSnsDetailImg = (AutoFrescoDraweeView) bindings[12];
        this.ivGood = (ImageView) bindings[4];
        this.ivGood.setTag(null);
        this.llComment = (LinearLayout) bindings[15];
        this.llGood = (LinearLayout) bindings[16];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.mboundView1 = (ImageView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView2 = (TextView) bindings[2];
        this.mboundView2.setTag(null);
        this.mboundView6 = (TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.rlAtMine = (RelativeLayout) bindings[11];
        this.rlUserInfo = (RelativeLayout) bindings[7];
        this.tvCommentNum = (TextView) bindings[3];
        this.tvCommentNum.setTag(null);
        this.tvGoodNum = (TextView) bindings[5];
        this.tvGoodNum.setTag(null);
        this.tvSnsContent = (TextView) bindings[14];
        this.tvSnsUsername = (TextView) bindings[13];
        this.userImage = (AutoFrescoDraweeView) bindings[8];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 64;
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
            case 62:
                setSnsInfo((SnsInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSnsInfo(SnsInfo SnsInfo) {
        this.mSnsInfo = SnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    public SnsInfo getSnsInfo() {
        return this.mSnsInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeSnsInfoPublishtime((ObservableField) object, fieldId);
            case 1:
                return onChangeSnsInfoPraisecount((ObservableInt) object, fieldId);
            case 2:
                return onChangeSnsInfoUserName((ObservableField) object, fieldId);
            case 3:
                return onChangeSnsInfoIspraise((ObservableInt) object, fieldId);
            case 4:
                return onChangeSnsInfoCommentcount((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeSnsInfoPublishtime(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeSnsInfoPraisecount(ObservableInt SnsInfoPraisecount, int fieldId) {
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

    private boolean onChangeSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeSnsInfoIspraise(ObservableInt SnsInfoIspraise, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 8;
                }
                return true;
            default:
                return false;
        }
    }

    private boolean onChangeSnsInfoCommentcount(ObservableInt SnsInfoCommentcount, int fieldId) {
        switch (fieldId) {
            case 0:
                synchronized (this) {
                    this.mDirtyFlags |= 16;
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
        String snsInfoUserNameGet = null;
        int snsInfoUserIsFirstAuthenticatedViewVISIBLEViewGONE = 0;
        UserInfo snsInfoUser = null;
        ObservableField<String> snsInfoPublishtime = null;
        boolean snsInfoPraisecountInt0 = false;
        ObservableInt snsInfoPraisecount = null;
        int snsInfoPraisecountGet = 0;
        int snsInfoIspraiseGet = 0;
        int snsInfoCommentcountGet = 0;
        SnsInfo snsInfo = this.mSnsInfo;
        ObservableField<String> snsInfoUserName = null;
        String snsInfoPublishtimeGet = null;
        ObservableInt snsInfoIspraise = null;
        ObservableInt snsInfoCommentcount = null;
        String fengUtilNumberFormatSnsInfoCommentcount = null;
        Drawable snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = null;
        String fengUtilNumberFormatSnsInfoPraisecount = null;
        String snsInfoCommentcountInt0TvCommentNumAndroidStringMessageCommentTextFengUtilNumberFormatSnsInfoCommentcount = null;
        int snsInfoIspraiseInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = 0;
        boolean snsInfoCommentcountInt0 = false;
        String snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = null;
        boolean snsInfoUserIsFirstAuthenticated = false;
        if ((127 & dirtyFlags) != 0) {
            if ((100 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoUser = snsInfo.user;
                }
                if (snsInfoUser != null) {
                    snsInfoUserName = snsInfoUser.name;
                }
                updateRegistration(2, snsInfoUserName);
                if (snsInfoUserName != null) {
                    snsInfoUserNameGet = (String) snsInfoUserName.get();
                }
                if ((96 & dirtyFlags) != 0) {
                    if (snsInfoUser != null) {
                        snsInfoUserIsFirstAuthenticated = snsInfoUser.getIsFirstAuthenticated();
                    }
                    if ((96 & dirtyFlags) != 0) {
                        if (snsInfoUserIsFirstAuthenticated) {
                            dirtyFlags |= 256;
                        } else {
                            dirtyFlags |= 128;
                        }
                    }
                    snsInfoUserIsFirstAuthenticatedViewVISIBLEViewGONE = snsInfoUserIsFirstAuthenticated ? 0 : 8;
                }
            }
            if ((97 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoPublishtime = snsInfo.publishtime;
                }
                updateRegistration(0, snsInfoPublishtime);
                if (snsInfoPublishtime != null) {
                    snsInfoPublishtimeGet = (String) snsInfoPublishtime.get();
                }
            }
            if ((98 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoPraisecount = snsInfo.praisecount;
                }
                updateRegistration(1, snsInfoPraisecount);
                if (snsInfoPraisecount != null) {
                    snsInfoPraisecountGet = snsInfoPraisecount.get();
                }
                snsInfoPraisecountInt0 = snsInfoPraisecountGet <= 0;
                if ((98 & dirtyFlags) != 0) {
                    if (snsInfoPraisecountInt0) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_CENTER;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    }
                }
            }
            if ((104 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoIspraise = snsInfo.ispraise;
                }
                updateRegistration(3, snsInfoIspraise);
                if (snsInfoIspraise != null) {
                    snsInfoIspraiseGet = snsInfoIspraise.get();
                }
                boolean snsInfoIspraiseInt1 = snsInfoIspraiseGet == 1;
                if ((104 & dirtyFlags) != 0) {
                    if (snsInfoIspraiseInt1) {
                        dirtyFlags = (dirtyFlags | 1024) | IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    } else {
                        dirtyFlags = (dirtyFlags | 512) | IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    }
                }
                snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = snsInfoIspraiseInt1 ? getDrawableFromResource(this.ivGood, 2130838077) : getDrawableFromResource(this.ivGood, 2130838076);
                if (snsInfoIspraiseInt1) {
                    snsInfoIspraiseInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = getColorFromResource(this.tvGoodNum, 2131558538);
                } else {
                    snsInfoIspraiseInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = getColorFromResource(this.tvGoodNum, R.color.color_54_000000);
                }
            }
            if ((112 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoCommentcount = snsInfo.commentcount;
                }
                updateRegistration(4, snsInfoCommentcount);
                if (snsInfoCommentcount != null) {
                    snsInfoCommentcountGet = snsInfoCommentcount.get();
                }
                snsInfoCommentcountInt0 = snsInfoCommentcountGet <= 0;
                if ((112 & dirtyFlags) != 0) {
                    if (snsInfoCommentcountInt0) {
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
            }
        }
        if ((2048 & dirtyFlags) != 0) {
            fengUtilNumberFormatSnsInfoCommentcount = FengUtil.numberFormat(snsInfoCommentcountGet);
        }
        if ((IjkMediaMeta.AV_CH_TOP_BACK_LEFT & dirtyFlags) != 0) {
            fengUtilNumberFormatSnsInfoPraisecount = FengUtil.numberFormat(snsInfoPraisecountGet);
        }
        if ((112 & dirtyFlags) != 0) {
            snsInfoCommentcountInt0TvCommentNumAndroidStringMessageCommentTextFengUtilNumberFormatSnsInfoCommentcount = snsInfoCommentcountInt0 ? this.tvCommentNum.getResources().getString(2131231222) : fengUtilNumberFormatSnsInfoCommentcount;
        }
        if ((98 & dirtyFlags) != 0) {
            if (snsInfoPraisecountInt0) {
                snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = this.tvGoodNum.getResources().getString(2131230936);
            } else {
                snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = fengUtilNumberFormatSnsInfoPraisecount;
            }
        }
        if ((104 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivGood, snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood);
            this.tvGoodNum.setTextColor(snsInfoIspraiseInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000);
        }
        if ((96 & dirtyFlags) != 0) {
            this.mboundView1.setVisibility(snsInfoUserIsFirstAuthenticatedViewVISIBLEViewGONE);
        }
        if ((100 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView2, snsInfoUserNameGet);
        }
        if ((97 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView6, snsInfoPublishtimeGet);
        }
        if ((112 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCommentNum, snsInfoCommentcountInt0TvCommentNumAndroidStringMessageCommentTextFengUtilNumberFormatSnsInfoCommentcount);
        }
        if ((98 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvGoodNum, snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount);
        }
    }

    public static AtContentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static AtContentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (AtContentItemBinding) DataBindingUtil.inflate(inflater, 2130903163, root, attachToRoot, bindingComponent);
    }

    public static AtContentItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static AtContentItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903163, null, false), bindingComponent);
    }

    public static AtContentItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static AtContentItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/at_content_item_0".equals(view.getTag())) {
            return new AtContentItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
