package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.ViewDataBinding.IncludedLayouts;
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
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;
import com.github.jdsjlzx.R;
import com.tencent.ijk.media.player.IjkMediaMeta;

public class ArticleCommentItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final RelativeLayout arrayLine;
    public final TextView arrayText;
    public final AisenTextView content;
    public final AutoFrescoDraweeView image;
    public final ImageView ivSafa;
    public final View line;
    public final LinearLayout llReplyCount;
    public final LinearLayout llReplyLine;
    private CommentInfo mCommentInfo;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private UserInfo mUserInfo;
    public final LinearLayout parentLine;
    public final TextView replyCount;
    public final AisenTextView replyText1;
    public final AisenTextView replyText2;
    public final AisenTextView replyText3;
    public final RelativeLayout rlUserInfo;
    public final TextView topText;
    public final TextView tvAnonymouspraise;
    public final TextView tvAuthorPraise;
    public final AutoFrescoDraweeView userImage;
    public final TextView userName;

    static {
        sViewsWithIds.put(2131624786, 4);
        sViewsWithIds.put(2131624787, 5);
        sViewsWithIds.put(2131624788, 6);
        sViewsWithIds.put(2131624269, 7);
        sViewsWithIds.put(2131624790, 8);
        sViewsWithIds.put(2131624792, 9);
        sViewsWithIds.put(2131624155, 10);
        sViewsWithIds.put(2131624794, 11);
        sViewsWithIds.put(2131624795, 12);
        sViewsWithIds.put(2131624796, 13);
        sViewsWithIds.put(2131624797, 14);
        sViewsWithIds.put(2131624798, 15);
        sViewsWithIds.put(2131624799, 16);
        sViewsWithIds.put(2131624800, 17);
        sViewsWithIds.put(2131624374, 18);
    }

    public ArticleCommentItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 19, sIncludes, sViewsWithIds);
        this.arrayLine = (RelativeLayout) bindings[4];
        this.arrayText = (TextView) bindings[5];
        this.content = (AisenTextView) bindings[9];
        this.image = (AutoFrescoDraweeView) bindings[10];
        this.ivSafa = (ImageView) bindings[8];
        this.line = (View) bindings[18];
        this.llReplyCount = (LinearLayout) bindings[15];
        this.llReplyLine = (LinearLayout) bindings[11];
        this.parentLine = (LinearLayout) bindings[0];
        this.parentLine.setTag(null);
        this.replyCount = (TextView) bindings[16];
        this.replyText1 = (AisenTextView) bindings[12];
        this.replyText2 = (AisenTextView) bindings[13];
        this.replyText3 = (AisenTextView) bindings[14];
        this.rlUserInfo = (RelativeLayout) bindings[6];
        this.topText = (TextView) bindings[2];
        this.topText.setTag(null);
        this.tvAnonymouspraise = (TextView) bindings[17];
        this.tvAuthorPraise = (TextView) bindings[3];
        this.tvAuthorPraise.setTag(null);
        this.userImage = (AutoFrescoDraweeView) bindings[7];
        this.userName = (TextView) bindings[1];
        this.userName.setTag(null);
        setRootTag(root);
        invalidateAll();
    }

    public void invalidateAll() {
        synchronized (this) {
            this.mDirtyFlags = 128;
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
            case 36:
                setMSnsInfo((SnsInfo) variable);
                return true;
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setMSnsInfo(SnsInfo MSnsInfo) {
        this.mMSnsInfo = MSnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 16;
        }
        notifyPropertyChanged(36);
        super.requestRebind();
    }

    public SnsInfo getMSnsInfo() {
        return this.mMSnsInfo;
    }

    public void setUserInfo(UserInfo UserInfo) {
        this.mUserInfo = UserInfo;
        synchronized (this) {
            this.mDirtyFlags |= 32;
        }
        notifyPropertyChanged(75);
        super.requestRebind();
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    public void setCommentInfo(CommentInfo CommentInfo) {
        this.mCommentInfo = CommentInfo;
        synchronized (this) {
            this.mDirtyFlags |= 64;
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
                return onChangeCommentInfoIstopbyauthor((ObservableInt) object, fieldId);
            case 1:
                return onChangeMSnsInfoUserName((ObservableField) object, fieldId);
            case 2:
                return onChangeCommentInfoIstop((ObservableInt) object, fieldId);
            case 3:
                return onChangeCommentInfoTop((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCommentInfoIstopbyauthor(ObservableInt CommentInfoIstopbyauthor, int fieldId) {
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

    private boolean onChangeMSnsInfoUserName(ObservableField<String> observableField, int fieldId) {
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
        SnsInfo mSnsInfo = this.mMSnsInfo;
        Drawable userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = null;
        String fengUtilNumberFormatCommentInfoTop = null;
        ObservableInt commentInfoIstopbyauthor = null;
        int commentInfoIstopGet = 0;
        UserInfo userInfo = this.mUserInfo;
        UserInfo mSnsInfoUser = null;
        CommentInfo commentInfo = this.mCommentInfo;
        ObservableField<String> mSnsInfoUserName = null;
        int commentInfoIstopInt1TopTextAndroidColorColorFc494cTopTextAndroidColorColor54000000 = 0;
        boolean userInfoGetIsFirstAuthenticated = false;
        int commentInfoTopGet = 0;
        Drawable commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = null;
        String mSnsInfoUserNameTvAuthorPraiseAndroidStringCommentAuthorPraise = null;
        int commentInfoIstopbyauthorGet = 0;
        ObservableInt commentInfoIstop = null;
        String mSnsInfoUserNameGet = null;
        ObservableInt commentInfoTop = null;
        int commentInfoIstopbyauthorInt1ViewVISIBLEViewGONE = 0;
        if ((146 & dirtyFlags) != 0) {
            if (mSnsInfo != null) {
                mSnsInfoUser = mSnsInfo.user;
            }
            if (mSnsInfoUser != null) {
                mSnsInfoUserName = mSnsInfoUser.name;
            }
            updateRegistration(1, mSnsInfoUserName);
            if (mSnsInfoUserName != null) {
                mSnsInfoUserNameGet = (String) mSnsInfoUserName.get();
            }
            mSnsInfoUserNameTvAuthorPraiseAndroidStringCommentAuthorPraise = mSnsInfoUserNameGet + this.tvAuthorPraise.getResources().getString(2131230951);
        }
        if ((160 & dirtyFlags) != 0) {
            if (userInfo != null) {
                userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
            }
            if ((160 & dirtyFlags) != 0) {
                if (userInfoGetIsFirstAuthenticated) {
                    dirtyFlags |= 512;
                } else {
                    dirtyFlags |= 256;
                }
            }
            userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.userName, 2130837632) : null;
        }
        if ((205 & dirtyFlags) != 0) {
            if ((193 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoIstopbyauthor = commentInfo.istopbyauthor;
                }
                updateRegistration(0, commentInfoIstopbyauthor);
                if (commentInfoIstopbyauthor != null) {
                    commentInfoIstopbyauthorGet = commentInfoIstopbyauthor.get();
                }
                boolean commentInfoIstopbyauthorInt1 = commentInfoIstopbyauthorGet == 1;
                if ((193 & dirtyFlags) != 0) {
                    if (commentInfoIstopbyauthorInt1) {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_BACK_LEFT;
                    } else {
                        dirtyFlags |= IjkMediaMeta.AV_CH_TOP_FRONT_RIGHT;
                    }
                }
                commentInfoIstopbyauthorInt1ViewVISIBLEViewGONE = commentInfoIstopbyauthorInt1 ? 0 : 8;
            }
            if ((196 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoIstop = commentInfo.istop;
                }
                updateRegistration(2, commentInfoIstop);
                if (commentInfoIstop != null) {
                    commentInfoIstopGet = commentInfoIstop.get();
                }
                boolean commentInfoIstopInt1 = commentInfoIstopGet == 1;
                if ((196 & dirtyFlags) != 0) {
                    if (commentInfoIstopInt1) {
                        dirtyFlags = (dirtyFlags | 2048) | IjkMediaMeta.AV_CH_TOP_FRONT_CENTER;
                    } else {
                        dirtyFlags = (dirtyFlags | 1024) | 4096;
                    }
                }
                commentInfoIstopInt1TopTextAndroidColorColorFc494cTopTextAndroidColorColor54000000 = commentInfoIstopInt1 ? getColorFromResource(this.topText, 2131558538) : getColorFromResource(this.topText, R.color.color_54_000000);
                commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = commentInfoIstopInt1 ? getDrawableFromResource(this.topText, 2130838077) : getDrawableFromResource(this.topText, 2130838076);
            }
            if ((200 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoTop = commentInfo.top;
                }
                updateRegistration(3, commentInfoTop);
                if (commentInfoTop != null) {
                    commentInfoTopGet = commentInfoTop.get();
                }
                fengUtilNumberFormatCommentInfoTop = FengUtil.numberFormat(commentInfoTopGet);
            }
        }
        if ((196 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableLeft(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
            TextViewBindingAdapter.setDrawableStart(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
            this.topText.setTextColor(commentInfoIstopInt1TopTextAndroidColorColorFc494cTopTextAndroidColorColor54000000);
        }
        if ((200 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.topText, fengUtilNumberFormatCommentInfoTop);
        }
        if ((146 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvAuthorPraise, mSnsInfoUserNameTvAuthorPraiseAndroidStringCommentAuthorPraise);
        }
        if ((193 & dirtyFlags) != 0) {
            this.tvAuthorPraise.setVisibility(commentInfoIstopbyauthorInt1ViewVISIBLEViewGONE);
        }
        if ((160 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.userName, userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.userName, userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull);
        }
    }

    public static ArticleCommentItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleCommentItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleCommentItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903157, root, attachToRoot, bindingComponent);
    }

    public static ArticleCommentItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleCommentItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903157, null, false), bindingComponent);
    }

    public static ArticleCommentItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleCommentItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_comment_item_layout_0".equals(view.getTag())) {
            return new ArticleCommentItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
