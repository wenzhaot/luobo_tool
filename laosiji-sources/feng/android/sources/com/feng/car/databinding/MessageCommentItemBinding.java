package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.DynamicUtil;
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
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;
import com.github.jdsjlzx.R;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class MessageCommentItemBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AisenTextView atvCommentDetail;
    public final AisenTextView atvReplyCommentDetail;
    public final View divider;
    public final AutoFrescoDraweeView fdvCommentImage;
    public final AutoFrescoDraweeView fdvCommentUserPortrait;
    public final AutoFrescoDraweeView ivCommentContentImg;
    public final ImageView ivGood;
    public final LinearLayout llCommentContainer;
    public final LinearLayout llCommentReply;
    public final LinearLayout llGood;
    public final LinearLayout llOperation;
    private CommentInfo mCommentInfo;
    private long mDirtyFlags = -1;
    private Integer mNType;
    private final ImageView mboundView1;
    private final TextView mboundView6;
    public final RelativeLayout rlOriginalReplyContainer;
    public final RelativeLayout rlThreadDetailContainer;
    public final RelativeLayout rlUser;
    public final RelativeLayout rlUserInfo;
    public final TextView tvCommentReply;
    public final TextView tvCommentTimestamp;
    public final TextView tvCommentUsername;
    public final TextView tvGoodNum;
    public final TextView tvThreadDetailContent;
    public final TextView tvThreadDetailUsername;

    static {
        sViewsWithIds.put(2131625259, 7);
        sViewsWithIds.put(2131624788, 8);
        sViewsWithIds.put(2131625260, 9);
        sViewsWithIds.put(2131625263, 10);
        sViewsWithIds.put(2131625264, 11);
        sViewsWithIds.put(2131625265, 12);
        sViewsWithIds.put(2131625266, 13);
        sViewsWithIds.put(2131625267, 14);
        sViewsWithIds.put(2131625268, 15);
        sViewsWithIds.put(2131625269, 16);
        sViewsWithIds.put(2131625270, 17);
        sViewsWithIds.put(2131624240, 18);
        sViewsWithIds.put(2131624970, 19);
        sViewsWithIds.put(2131625271, 20);
        sViewsWithIds.put(2131625272, 21);
        sViewsWithIds.put(2131624846, 22);
    }

    public MessageCommentItemBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 23, sIncludes, sViewsWithIds);
        this.atvCommentDetail = (AisenTextView) bindings[10];
        this.atvReplyCommentDetail = (AisenTextView) bindings[13];
        this.divider = (View) bindings[18];
        this.fdvCommentImage = (AutoFrescoDraweeView) bindings[11];
        this.fdvCommentUserPortrait = (AutoFrescoDraweeView) bindings[9];
        this.ivCommentContentImg = (AutoFrescoDraweeView) bindings[15];
        this.ivGood = (ImageView) bindings[4];
        this.ivGood.setTag(null);
        this.llCommentContainer = (LinearLayout) bindings[0];
        this.llCommentContainer.setTag(null);
        this.llCommentReply = (LinearLayout) bindings[20];
        this.llGood = (LinearLayout) bindings[22];
        this.llOperation = (LinearLayout) bindings[19];
        this.mboundView1 = (ImageView) bindings[1];
        this.mboundView1.setTag(null);
        this.mboundView6 = (TextView) bindings[6];
        this.mboundView6.setTag(null);
        this.rlOriginalReplyContainer = (RelativeLayout) bindings[12];
        this.rlThreadDetailContainer = (RelativeLayout) bindings[14];
        this.rlUser = (RelativeLayout) bindings[7];
        this.rlUserInfo = (RelativeLayout) bindings[8];
        this.tvCommentReply = (TextView) bindings[21];
        this.tvCommentTimestamp = (TextView) bindings[3];
        this.tvCommentTimestamp.setTag(null);
        this.tvCommentUsername = (TextView) bindings[2];
        this.tvCommentUsername.setTag(null);
        this.tvGoodNum = (TextView) bindings[5];
        this.tvGoodNum.setTag(null);
        this.tvThreadDetailContent = (TextView) bindings[17];
        this.tvThreadDetailUsername = (TextView) bindings[16];
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
            case 15:
                setCommentInfo((CommentInfo) variable);
                return true;
            case 43:
                setNType((Integer) variable);
                return true;
            default:
                return false;
        }
    }

    public void setNType(Integer NType) {
        this.mNType = NType;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(43);
        super.requestRebind();
    }

    public Integer getNType() {
        return this.mNType;
    }

    public void setCommentInfo(CommentInfo CommentInfo) {
        this.mCommentInfo = CommentInfo;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(15);
        super.requestRebind();
    }

    public CommentInfo getCommentInfo() {
        return this.mCommentInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeCommentInfoUserName((ObservableField) object, fieldId);
            case 1:
                return onChangeCommentInfoTime((ObservableField) object, fieldId);
            case 2:
                return onChangeCommentInfoIstop((ObservableInt) object, fieldId);
            case 3:
                return onChangeCommentInfoTop((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCommentInfoUserName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeCommentInfoTime(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeCommentInfoIstop(ObservableInt CommentInfoIstop, int fieldId) {
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

    private boolean onChangeCommentInfoTop(ObservableInt CommentInfoTop, int fieldId) {
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
        String commentInfoTopInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatCommentInfoTop = null;
        String commentInfoTimeGet = null;
        ObservableField<String> commentInfoUserName = null;
        Drawable commentInfoIstopInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = null;
        ObservableField<String> commentInfoTime = null;
        String commentInfoUserNameGet = null;
        String fengUtilNumberFormatCommentInfoTop = null;
        boolean commentInfoUserGetIsFirstAuthenticated = false;
        int nTypeInt1ViewVISIBLEViewGONE = 0;
        boolean commentInfoTopInt0 = false;
        int commentInfoIstopInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = 0;
        int commentInfoIstopGet = 0;
        String commentInfoUserIsMyInt1TvCommentUsernameAndroidStringMineCommentInfoUserName = null;
        Integer nType = this.mNType;
        boolean commentInfoUserIsMyInt1 = false;
        CommentInfo commentInfo = this.mCommentInfo;
        int commentInfoTopGet = 0;
        int commentInfoUserIsMy = 0;
        UserInfo commentInfoUser = null;
        int commentInfoUserGetIsFirstAuthenticatedViewVISIBLEViewGONE = 0;
        ObservableInt commentInfoIstop = null;
        ObservableInt commentInfoTop = null;
        if ((80 & dirtyFlags) != 0) {
            boolean nTypeInt1 = DynamicUtil.safeUnbox(nType) == 1;
            if ((80 & dirtyFlags) != 0) {
                if (nTypeInt1) {
                    dirtyFlags |= 4096;
                } else {
                    dirtyFlags |= 2048;
                }
            }
            nTypeInt1ViewVISIBLEViewGONE = nTypeInt1 ? 0 : 8;
        }
        if ((111 & dirtyFlags) != 0) {
            if ((98 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoTime = commentInfo.time;
                }
                updateRegistration(1, commentInfoTime);
                if (commentInfoTime != null) {
                    commentInfoTimeGet = (String) commentInfoTime.get();
                }
            }
            if ((97 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoUser = commentInfo.user;
                }
                if ((96 & dirtyFlags) != 0) {
                    if (commentInfoUser != null) {
                        commentInfoUserGetIsFirstAuthenticated = commentInfoUser.getIsFirstAuthenticated();
                    }
                    if ((96 & dirtyFlags) != 0) {
                        if (commentInfoUserGetIsFirstAuthenticated) {
                            dirtyFlags |= 262144;
                        } else {
                            dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_RIGHT;
                        }
                    }
                    commentInfoUserGetIsFirstAuthenticatedViewVISIBLEViewGONE = commentInfoUserGetIsFirstAuthenticated ? 0 : 8;
                }
                if (commentInfoUser != null) {
                    commentInfoUserIsMy = commentInfoUser.getIsMy();
                }
                commentInfoUserIsMyInt1 = commentInfoUserIsMy == 1;
                if ((97 & dirtyFlags) != 0) {
                    if (commentInfoUserIsMyInt1) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_CENTER;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    }
                }
            }
            if ((100 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoIstop = commentInfo.istop;
                }
                updateRegistration(2, commentInfoIstop);
                if (commentInfoIstop != null) {
                    commentInfoIstopGet = commentInfoIstop.get();
                }
                boolean commentInfoIstopInt1 = commentInfoIstopGet == 1;
                if ((100 & dirtyFlags) != 0) {
                    if (commentInfoIstopInt1) {
                        dirtyFlags = (dirtyFlags | 1024) | IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    } else {
                        dirtyFlags = (dirtyFlags | 512) | IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    }
                }
                commentInfoIstopInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood = commentInfoIstopInt1 ? getDrawableFromResource(this.ivGood, 2130838077) : getDrawableFromResource(this.ivGood, 2130838076);
                if (commentInfoIstopInt1) {
                    commentInfoIstopInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = getColorFromResource(this.tvGoodNum, 2131558538);
                } else {
                    commentInfoIstopInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000 = getColorFromResource(this.tvGoodNum, R.color.color_54_000000);
                }
            }
            if ((104 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoTop = commentInfo.top;
                }
                updateRegistration(3, commentInfoTop);
                if (commentInfoTop != null) {
                    commentInfoTopGet = commentInfoTop.get();
                }
                commentInfoTopInt0 = commentInfoTopGet <= 0;
                if ((104 & dirtyFlags) != 0) {
                    if (commentInfoTopInt0) {
                        dirtyFlags |= 256;
                    } else {
                        dirtyFlags |= 128;
                    }
                }
            }
        }
        if ((IjkMediaMeta.AV_CH_TOP_BACK_LEFT & dirtyFlags) != 0) {
            if (commentInfoUser != null) {
                commentInfoUserName = commentInfoUser.name;
            }
            updateRegistration(0, commentInfoUserName);
            if (commentInfoUserName != null) {
                commentInfoUserNameGet = (String) commentInfoUserName.get();
            }
        }
        if ((128 & dirtyFlags) != 0) {
            fengUtilNumberFormatCommentInfoTop = FengUtil.numberFormat(commentInfoTopGet);
        }
        if ((104 & dirtyFlags) != 0) {
            commentInfoTopInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatCommentInfoTop = commentInfoTopInt0 ? this.tvGoodNum.getResources().getString(2131230936) : fengUtilNumberFormatCommentInfoTop;
        }
        if ((97 & dirtyFlags) != 0) {
            if (commentInfoUserIsMyInt1) {
                commentInfoUserIsMyInt1TvCommentUsernameAndroidStringMineCommentInfoUserName = this.tvCommentUsername.getResources().getString(2131231253);
            } else {
                commentInfoUserIsMyInt1TvCommentUsernameAndroidStringMineCommentInfoUserName = commentInfoUserNameGet;
            }
        }
        if ((100 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivGood, commentInfoIstopInt1IvGoodAndroidDrawableIconGoodPresIvGoodAndroidDrawableIconGood);
            this.tvGoodNum.setTextColor(commentInfoIstopInt1TvGoodNumAndroidColorColorFc494cTvGoodNumAndroidColorColor54000000);
        }
        if ((96 & dirtyFlags) != 0) {
            this.mboundView1.setVisibility(commentInfoUserGetIsFirstAuthenticatedViewVISIBLEViewGONE);
        }
        if ((98 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.mboundView6, commentInfoTimeGet);
            TextViewBindingAdapter.setText(this.tvCommentTimestamp, commentInfoTimeGet);
        }
        if ((80 & dirtyFlags) != 0) {
            this.tvCommentTimestamp.setVisibility(nTypeInt1ViewVISIBLEViewGONE);
        }
        if ((97 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvCommentUsername, commentInfoUserIsMyInt1TvCommentUsernameAndroidStringMineCommentInfoUserName);
        }
        if ((104 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvGoodNum, commentInfoTopInt0TvGoodNumAndroidStringClickPraiseFengUtilNumberFormatCommentInfoTop);
        }
    }

    public static MessageCommentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static MessageCommentItemBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (MessageCommentItemBinding) DataBindingUtil.inflate(inflater, 2130903309, root, attachToRoot, bindingComponent);
    }

    public static MessageCommentItemBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static MessageCommentItemBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903309, null, false), bindingComponent);
    }

    public static MessageCommentItemBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static MessageCommentItemBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/message_comment_item_0".equals(view.getTag())) {
            return new MessageCommentItemBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
