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
import com.feng.car.entity.ad.AdMaterialInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class CommonPostItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adCover;
    public final AutoFrescoDraweeView adUserImage;
    public final AisenTextView atDigestDescription;
    public final ImageView ivArrowDown;
    public final ImageView ivGood;
    public final ImageView ivSelectDel;
    public final ImageView ivTag;
    public final ImageView ivVideoTime;
    public final LinearLayout llOperation;
    public final LinearLayout llTag;
    private long mDirtyFlags = -1;
    private SnsInfo mSnsInfo;
    public final RelativeLayout rlParent;
    public final TextView tvGoodNum;
    public final TextView tvTagName;
    public final TextView tvUserName;
    public final View vLine;

    static {
        sViewsWithIds.put(2131624963, 4);
        sViewsWithIds.put(2131624964, 5);
        sViewsWithIds.put(2131624965, 6);
        sViewsWithIds.put(2131624966, 7);
        sViewsWithIds.put(2131624967, 8);
        sViewsWithIds.put(2131624473, 9);
        sViewsWithIds.put(2131624838, 10);
        sViewsWithIds.put(2131624968, 11);
        sViewsWithIds.put(2131624969, 12);
        sViewsWithIds.put(2131624970, 13);
        sViewsWithIds.put(2131624971, 14);
    }

    public CommonPostItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 15, sIncludes, sViewsWithIds);
        this.adCover = (AutoFrescoDraweeView) bindings[4];
        this.adUserImage = (AutoFrescoDraweeView) bindings[12];
        this.atDigestDescription = (AisenTextView) bindings[10];
        this.ivArrowDown = (ImageView) bindings[11];
        this.ivGood = (ImageView) bindings[1];
        this.ivGood.setTag(null);
        this.ivSelectDel = (ImageView) bindings[14];
        this.ivTag = (ImageView) bindings[7];
        this.ivVideoTime = (ImageView) bindings[5];
        this.llOperation = (LinearLayout) bindings[13];
        this.llTag = (LinearLayout) bindings[6];
        this.rlParent = (RelativeLayout) bindings[0];
        this.rlParent.setTag(null);
        this.tvGoodNum = (TextView) bindings[2];
        this.tvGoodNum.setTag(null);
        this.tvTagName = (TextView) bindings[8];
        this.tvUserName = (TextView) bindings[3];
        this.tvUserName.setTag(null);
        this.vLine = (View) bindings[9];
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 32;
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
            this.mDirtyFlags |= 16;
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
                return onChangeSnsInfoPraisecount((ObservableInt) object, fieldId);
            case 1:
                return onChangeSnsInfoUserName((ObservableField) object, fieldId);
            case 2:
                return onChangeSnsInfoIspraise((ObservableInt) object, fieldId);
            case 3:
                return onChangeSnsInfoAdvertInfoTmpmapUserName((ObservableField) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeSnsInfoPraisecount(ObservableInt SnsInfoPraisecount, int fieldId) {
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

    private boolean onChangeSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeSnsInfoIspraise(ObservableInt SnsInfoIspraise, int fieldId) {
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

    private boolean onChangeSnsInfoAdvertInfoTmpmapUserName(ObservableField<String> observableField, int fieldId) {
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

    protected void executeBindings() {
        long dirtyFlags;
        synchronized (this) {
            dirtyFlags = this.mDirtyFlags;
            this.mDirtyFlags = 0;
        }
        String snsInfoUserNameGet = null;
        UserInfo snsInfoUser = null;
        boolean snsInfoPraisecountInt0 = false;
        String snsInfoAdvertInfoTmpmapUserNameGet = null;
        ObservableInt snsInfoPraisecount = null;
        boolean snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalse = false;
        int snsInfoPraisecountGet = 0;
        int snsInfoIspraiseGet = 0;
        AdMaterialInfo snsInfoAdvertInfoTmpmap = null;
        int snsInfoSnstype = 0;
        SnsInfo snsInfo = this.mSnsInfo;
        UserInfo snsInfoAdvertInfoTmpmapUser = null;
        ObservableField<String> snsInfoUserName = null;
        ObservableInt snsInfoIspraise = null;
        String snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalseSnsInfoAdvertInfoTmpmapUserNameSnsInfoUserName = null;
        boolean snsInfoSnstypeInt1000 = false;
        AdvertInfo snsInfoAdvertInfo = null;
        boolean snsInfoAdvertInfoIsinnerInt0 = false;
        Drawable snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = null;
        String fengUtilNumberFormatSnsInfoPraisecount = null;
        ObservableField<String> snsInfoAdvertInfoTmpmapUserName = null;
        int snsInfoAdvertInfoIsinner = 0;
        int snsInfoIspraiseInt1TvGoodNumAndroidColorColorFb6c06TvGoodNumAndroidColorColor666666 = 0;
        String snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = null;
        if ((63 & dirtyFlags) != 0) {
            if ((49 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoPraisecount = snsInfo.praisecount;
                }
                updateRegistration(0, snsInfoPraisecount);
                if (snsInfoPraisecount != null) {
                    snsInfoPraisecountGet = snsInfoPraisecount.get();
                }
                snsInfoPraisecountInt0 = snsInfoPraisecountGet <= 0;
                if ((49 & dirtyFlags) != 0) {
                    dirtyFlags = snsInfoPraisecountInt0 ? dirtyFlags | IjkMediaMeta.AV_CH_TOP_BACK_LEFT : dirtyFlags | IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                }
            }
            if ((58 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoSnstype = snsInfo.snstype;
                }
                snsInfoSnstypeInt1000 = snsInfoSnstype == 1000;
                if ((58 & dirtyFlags) != 0) {
                    if (snsInfoSnstypeInt1000) {
                        dirtyFlags |= 128;
                    } else {
                        dirtyFlags |= 64;
                    }
                }
            }
            if ((52 & dirtyFlags) != 0) {
                if (snsInfo != null) {
                    snsInfoIspraise = snsInfo.ispraise;
                }
                updateRegistration(2, snsInfoIspraise);
                if (snsInfoIspraise != null) {
                    snsInfoIspraiseGet = snsInfoIspraise.get();
                }
                boolean snsInfoIspraiseInt1 = snsInfoIspraiseGet == 1;
                if ((52 & dirtyFlags) != 0) {
                    if (snsInfoIspraiseInt1) {
                        dirtyFlags = (dirtyFlags | 2048) | IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    } else {
                        dirtyFlags = (dirtyFlags | 1024) | 4096;
                    }
                }
                snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = snsInfoIspraiseInt1 ? getDrawableFromResource(this.ivGood, 2130838077) : getDrawableFromResource(this.ivGood, 2130838076);
                snsInfoIspraiseInt1TvGoodNumAndroidColorColorFb6c06TvGoodNumAndroidColorColor666666 = snsInfoIspraiseInt1 ? getColorFromResource(this.tvGoodNum, 2131558537) : getColorFromResource(this.tvGoodNum, 2131558469);
            }
        }
        if ((128 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoAdvertInfo = snsInfo.advertInfo;
            }
            if (snsInfoAdvertInfo != null) {
                snsInfoAdvertInfoIsinner = snsInfoAdvertInfo.isinner;
            }
            snsInfoAdvertInfoIsinnerInt0 = snsInfoAdvertInfoIsinner == 0;
        }
        if ((IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT & dirtyFlags) != 0) {
            fengUtilNumberFormatSnsInfoPraisecount = FengUtil.numberFormat(snsInfoPraisecountGet);
        }
        if ((58 & dirtyFlags) != 0) {
            snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalse = snsInfoSnstypeInt1000 ? snsInfoAdvertInfoIsinnerInt0 : false;
            if ((58 & dirtyFlags) != 0) {
                if (snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalse) {
                    dirtyFlags |= 512;
                } else {
                    dirtyFlags |= 256;
                }
            }
        }
        if ((49 & dirtyFlags) != 0) {
            if (snsInfoPraisecountInt0) {
                snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = this.tvGoodNum.getResources().getString(2131230936);
            } else {
                snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount = fengUtilNumberFormatSnsInfoPraisecount;
            }
        }
        if ((256 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoUser = snsInfo.user;
            }
            if (snsInfoUser != null) {
                snsInfoUserName = snsInfoUser.name;
            }
            updateRegistration(1, snsInfoUserName);
            if (snsInfoUserName != null) {
                snsInfoUserNameGet = (String) snsInfoUserName.get();
            }
        }
        if ((512 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoAdvertInfo = snsInfo.advertInfo;
            }
            if (snsInfoAdvertInfo != null) {
                snsInfoAdvertInfoTmpmap = snsInfoAdvertInfo.tmpmap;
            }
            if (snsInfoAdvertInfoTmpmap != null) {
                snsInfoAdvertInfoTmpmapUser = snsInfoAdvertInfoTmpmap.user;
            }
            if (snsInfoAdvertInfoTmpmapUser != null) {
                snsInfoAdvertInfoTmpmapUserName = snsInfoAdvertInfoTmpmapUser.name;
            }
            updateRegistration(3, snsInfoAdvertInfoTmpmapUserName);
            if (snsInfoAdvertInfoTmpmapUserName != null) {
                snsInfoAdvertInfoTmpmapUserNameGet = (String) snsInfoAdvertInfoTmpmapUserName.get();
            }
        }
        if ((58 & dirtyFlags) != 0) {
            snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalseSnsInfoAdvertInfoTmpmapUserNameSnsInfoUserName = snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalse ? snsInfoAdvertInfoTmpmapUserNameGet : snsInfoUserNameGet;
        }
        if ((52 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivGood, snsInfoIspraiseInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood);
            this.tvGoodNum.setTextColor(snsInfoIspraiseInt1TvGoodNumAndroidColorColorFb6c06TvGoodNumAndroidColorColor666666);
        }
        if ((49 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvGoodNum, snsInfoPraisecountInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatSnsInfoPraisecount);
        }
        if ((58 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, snsInfoSnstypeInt1000SnsInfoAdvertInfoIsinnerInt0BooleanFalseSnsInfoAdvertInfoTmpmapUserNameSnsInfoUserName);
        }
    }

    public static CommonPostItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommonPostItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommonPostItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903203, root, attachToRoot, bindingComponent);
    }

    public static CommonPostItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommonPostItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903203, null, false), bindingComponent);
    }

    public static CommonPostItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommonPostItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/common_post_item_layout_0".equals(view.getTag())) {
            return new CommonPostItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
