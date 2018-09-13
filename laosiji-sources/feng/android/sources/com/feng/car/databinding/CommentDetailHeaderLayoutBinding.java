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
import android.widget.TextView;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.textview.AisenTextView;

public class CommentDetailHeaderLayoutBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView adImage;
    public final LinearLayout arrayLine;
    public final TextView arrayText;
    public final AisenTextView content;
    public final LinearLayout llParent;
    private CommentInfo mCommentInfo;
    private long mDirtyFlags = -1;
    private UserInfo mUserInfo;
    private final ImageView mboundView2;
    public final TextView topText;
    public final TextView tvWatch;
    public final AutoFrescoDraweeView userImage;
    public final TextView userName;

    static {
        sViewsWithIds.put(2131624269, 4);
        sViewsWithIds.put(2131624792, 5);
        sViewsWithIds.put(2131624938, 6);
        sViewsWithIds.put(2131624939, 7);
        sViewsWithIds.put(2131624786, 8);
        sViewsWithIds.put(2131624787, 9);
    }

    public CommentDetailHeaderLayoutBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 4);
        Object[] bindings = mapBindings(bindingComponent, root, 10, sIncludes, sViewsWithIds);
        this.adImage = (AutoFrescoDraweeView) bindings[6];
        this.arrayLine = (LinearLayout) bindings[8];
        this.arrayText = (TextView) bindings[9];
        this.content = (AisenTextView) bindings[5];
        this.llParent = (LinearLayout) bindings[0];
        this.llParent.setTag(null);
        this.mboundView2 = (ImageView) bindings[2];
        this.mboundView2.setTag(null);
        this.topText = (TextView) bindings[3];
        this.topText.setTag(null);
        this.tvWatch = (TextView) bindings[7];
        this.userImage = (AutoFrescoDraweeView) bindings[4];
        this.userName = (TextView) bindings[1];
        this.userName.setTag(null);
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
            this.mDirtyFlags |= 16;
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
                return onChangeCommentInfoLevel((ObservableInt) object, fieldId);
            case 1:
                return onChangeUserInfoName((ObservableField) object, fieldId);
            case 2:
                return onChangeCommentInfoIstop((ObservableInt) object, fieldId);
            case 3:
                return onChangeCommentInfoTop((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeCommentInfoLevel(ObservableInt CommentInfoLevel, int fieldId) {
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

    private boolean onChangeUserInfoName(ObservableField<String> observableField, int fieldId) {
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
        Drawable userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = null;
        ObservableInt commentInfoLevel = null;
        int commentInfoLevelGet = 0;
        int commentInfoLevelInt1ViewVISIBLEViewGONE = 0;
        String fengUtilNumberFormatCommentInfoTop = null;
        String userInfoNameGet = null;
        int commentInfoIstopGet = 0;
        ObservableField<String> userInfoName = null;
        UserInfo userInfo = this.mUserInfo;
        CommentInfo commentInfo = this.mCommentInfo;
        boolean userInfoGetIsFirstAuthenticated = false;
        int commentInfoTopGet = 0;
        Drawable commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = null;
        ObservableInt commentInfoIstop = null;
        ObservableInt commentInfoTop = null;
        if ((82 & dirtyFlags) != 0) {
            if (userInfo != null) {
                userInfoName = userInfo.name;
            }
            updateRegistration(1, userInfoName);
            if (userInfoName != null) {
                userInfoNameGet = (String) userInfoName.get();
            }
            if ((80 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((80 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 256;
                    } else {
                        dirtyFlags |= 128;
                    }
                }
                userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? getDrawableFromResource(this.userName, 2130837632) : null;
            }
        }
        if ((109 & dirtyFlags) != 0) {
            if ((97 & dirtyFlags) != 0) {
                if (commentInfo != null) {
                    commentInfoLevel = commentInfo.level;
                }
                updateRegistration(0, commentInfoLevel);
                if (commentInfoLevel != null) {
                    commentInfoLevelGet = commentInfoLevel.get();
                }
                boolean commentInfoLevelInt1 = commentInfoLevelGet == 1;
                if ((97 & dirtyFlags) != 0) {
                    if (commentInfoLevelInt1) {
                        dirtyFlags |= 1024;
                    } else {
                        dirtyFlags |= 512;
                    }
                }
                commentInfoLevelInt1ViewVISIBLEViewGONE = commentInfoLevelInt1 ? 0 : 8;
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
                        dirtyFlags |= 4096;
                    } else {
                        dirtyFlags |= 2048;
                    }
                }
                commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood = commentInfoIstopInt1 ? getDrawableFromResource(this.topText, 2130838077) : getDrawableFromResource(this.topText, 2130838076);
            }
            if ((104 & dirtyFlags) != 0) {
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
        if ((97 & dirtyFlags) != 0) {
            this.mboundView2.setVisibility(commentInfoLevelInt1ViewVISIBLEViewGONE);
        }
        if ((100 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableLeft(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
            TextViewBindingAdapter.setDrawableStart(this.topText, commentInfoIstopInt1TopTextAndroidDrawableIconGoodPresTopTextAndroidDrawableIconGood);
        }
        if ((104 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.topText, fengUtilNumberFormatCommentInfoTop);
        }
        if ((80 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableEnd(this.userName, userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.userName, userInfoGetIsFirstAuthenticatedUserNameAndroidDrawableAuthoritiesSmallJavaLangObjectNull);
        }
        if ((82 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.userName, userInfoNameGet);
        }
    }

    public static CommentDetailHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailHeaderLayoutBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (CommentDetailHeaderLayoutBinding) DataBindingUtil.inflate(inflater, 2130903196, root, attachToRoot, bindingComponent);
    }

    public static CommentDetailHeaderLayoutBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailHeaderLayoutBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903196, null, false), bindingComponent);
    }

    public static CommentDetailHeaderLayoutBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static CommentDetailHeaderLayoutBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/comment_detail_header_layout_0".equals(view.getTag())) {
            return new CommentDetailHeaderLayoutBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
