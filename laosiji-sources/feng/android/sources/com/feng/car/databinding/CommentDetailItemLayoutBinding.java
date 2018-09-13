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
import android.widget.LinearLayout;
import android.widget.TextView;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;

public class CommentDetailItemLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AisenTextView content;
    private CommentInfo mCommentInfo;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    public final LinearLayout parentLine;
    public final TextView topText;
    public final AutoFrescoDraweeView userImage;
    public final TextView userName;
    public final View vExtend;

    static {
        sViewsWithIds.put(2131624940, 3);
        sViewsWithIds.put(2131624269, 4);
        sViewsWithIds.put(2131624792, 5);
    }

    public CommentDetailItemLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 3);
        Object[] bindings = mapBindings(bindingComponent, root, 6, sIncludes, sViewsWithIds);
        this.content = (AisenTextView) bindings[5];
        this.parentLine = (LinearLayout) bindings[0];
        this.parentLine.setTag(null);
        this.topText = (TextView) bindings[2];
        this.topText.setTag(null);
        this.userImage = (AutoFrescoDraweeView) bindings[4];
        this.userName = (TextView) bindings[1];
        this.userName.setTag(null);
        this.vExtend = (View) bindings[3];
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
            case 15:
                setCommentInfo((CommentInfo) variable);
                return true;
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setUserInfo(UserInfo UserInfo) {
        this.mUserInfo = UserInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
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
            this.mDirtyFlags |= 16;
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
                return onChangeCommentInfoIstop((ObservableInt) object, fieldId);
            case 2:
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

    private boolean onChangeCommentInfoIstop(ObservableInt CommentInfoIstop, int fieldId) {
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

    private boolean onChangeCommentInfoTop(ObservableInt CommentInfoTop, int fieldId) {
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
        ObservableField<String> commentInfoUserName = null;
        Drawable userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = null;
        String commentInfoUserNameGet = null;
        String fengUtilNumberFormatCommentInfoTop = null;
        int commentInfoIstopGet = 0;
        UserInfo userInfo = this.mUserInfo;
        CommentInfo commentInfo = this.mCommentInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
        int commentInfoTopGet = 0;
        Drawable commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = null;
        UserInfo commentInfoUser = null;
        ObservableInt commentInfoIstop = null;
        ObservableInt commentInfoTop = null;
        if ((40 & dirtyFlags) != 0) {
            if (userInfo != null) {
                userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
            }
            if ((40 & dirtyFlags) != 0) {
                if (userInfoGetIsFirstAuthenticated) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.userName, 2130837632) : null;
        }
        if ((55 & dirtyFlags) != 0) {
            if ((49 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoUser = commentInfo.user;
                }
                if (commentInfoUser != null) {
                    commentInfoUserName = commentInfoUser.name;
                }
                updateRegistration(0, commentInfoUserName);
                if (commentInfoUserName != null) {
                    commentInfoUserNameGet = (String) commentInfoUserName.get();
                }
            }
            if ((50 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoIstop = commentInfo.istop;
                }
                updateRegistration(1, commentInfoIstop);
                if (commentInfoIstop != null) {
                    commentInfoIstopGet = commentInfoIstop.get();
                }
                boolean commentInfoIstopInt1 = commentInfoIstopGet == 1;
                if ((50 & dirtyFlags) != 0) {
                    if (commentInfoIstopInt1) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = commentInfoIstopInt1 ? getDrawableFromResource(this.topText, 2130838077) : getDrawableFromResource(this.topText, 2130838076);
            }
            if ((52 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoTop = commentInfo.top;
                }
                updateRegistration(2, commentInfoTop);
                if (commentInfoTop != null) {
                    commentInfoTopGet = commentInfoTop.get();
                }
                fengUtilNumberFormatCommentInfoTop = FengUtil.numberFormat(commentInfoTopGet);
            }
        }
        if ((50 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableLeft(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
            TextViewBindingAdapter.setDrawableStart(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
        }
        if ((52 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.topText, fengUtilNumberFormatCommentInfoTop);
        }
        if ((40 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableRight(this.userName, userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull);
        }
        if ((49 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.userName, commentInfoUserNameGet);
        }
    }

    public static CommentDetailItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailItemLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommentDetailItemLayoutBinding) DataBindingUtil.inflate(inflater, 2130903197, root, attachToRoot, bindingComponent);
    }

    public static CommentDetailItemLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailItemLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903197, null, false), bindingComponent);
    }

    public static CommentDetailItemLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailItemLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/comment_detail_item_layout_0".equals(view.getTag())) {
            return new CommentDetailItemLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
