package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
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
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;

public class ArticleShareDialogBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final TextView backHome;
    public final TextView cancel;
    public final TextView collectText;
    public final TextView copyLink;
    public final TextView delete;
    public final View divider;
    public final TextView download;
    public final TextView edit;
    public final TextView follow;
    public final TextView friendsShare;
    private long mDirtyFlags = -1;
    private SnsInfo mMSnsInfo;
    private UserInfo mUserInfo;
    private final LinearLayout mboundView0;
    public final TextView placeView;
    public final TextView placeView1;
    public final TextView placeView2;
    public final TextView qqShare;
    public final TextView qzoneShare;
    public final TextView report;
    public final LinearLayout shareLine;
    public final TextView weiboShare;
    public final TextView weixinShare;

    static {
        sViewsWithIds.put(2131624821, 3);
        sViewsWithIds.put(2131624822, 4);
        sViewsWithIds.put(2131624823, 5);
        sViewsWithIds.put(2131624824, 6);
        sViewsWithIds.put(2131624825, 7);
        sViewsWithIds.put(2131624826, 8);
        sViewsWithIds.put(2131624240, 9);
        sViewsWithIds.put(2131624828, 10);
        sViewsWithIds.put(2131624829, 11);
        sViewsWithIds.put(2131624830, 12);
        sViewsWithIds.put(2131624831, 13);
        sViewsWithIds.put(2131624833, 14);
        sViewsWithIds.put(2131624834, 15);
        sViewsWithIds.put(2131624835, 16);
        sViewsWithIds.put(2131624836, 17);
        sViewsWithIds.put(2131624837, 18);
        sViewsWithIds.put(2131624291, 19);
    }

    public ArticleShareDialogBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        Object[] bindings = mapBindings(bindingComponent, root, 20, sIncludes, sViewsWithIds);
        this.backHome = (TextView) bindings[15];
        this.cancel = (TextView) bindings[19];
        this.collectText = (TextView) bindings[1];
        this.collectText.setTag(null);
        this.copyLink = (TextView) bindings[10];
        this.delete = (TextView) bindings[12];
        this.divider = (View) bindings[9];
        this.download = (TextView) bindings[13];
        this.edit = (TextView) bindings[11];
        this.follow = (TextView) bindings[2];
        this.follow.setTag(null);
        this.friendsShare = (TextView) bindings[4];
        this.mboundView0 = (LinearLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.placeView = (TextView) bindings[16];
        this.placeView1 = (TextView) bindings[17];
        this.placeView2 = (TextView) bindings[18];
        this.qqShare = (TextView) bindings[7];
        this.qzoneShare = (TextView) bindings[8];
        this.report = (TextView) bindings[14];
        this.shareLine = (LinearLayout) bindings[3];
        this.weiboShare = (TextView) bindings[6];
        this.weixinShare = (TextView) bindings[5];
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
            this.mDirtyFlags |= 4;
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
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(75);
        super.requestRebind();
    }

    public UserInfo getUserInfo() {
        return this.mUserInfo;
    }

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeMSnsInfoIsfavorite((ObservableInt) object, fieldId);
            case 1:
                return onChangeUserInfoIsfollow((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeMSnsInfoIsfavorite(ObservableInt MSnsInfoIsfavorite, int fieldId) {
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

    private boolean onChangeUserInfoIsfollow(ObservableInt UserInfoIsfollow, int fieldId) {
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
        SnsInfo mSnsInfo = this.mMSnsInfo;
        Drawable mSnsInfoIsfavoriteInt0CollectTextAndroidDrawableCollectSelectorCollectTextAndroidDrawableUncollectSelector = null;
        int userInfoIsfollowGet = 0;
        String mSnsInfoIsfavoriteInt0CollectTextAndroidStringCollectCollectTextAndroidStringCancelCollect = null;
        int mSnsInfoIsfavoriteGet = 0;
        String userInfoIsfollowInt0FollowAndroidStringFollowFollowAndroidStringCancelAttention = null;
        UserInfo userInfo = this.mUserInfo;
        Drawable userInfoIsfollowInt0FollowAndroidDrawableArticleFollowSelectorFollowAndroidDrawableArticleFollowCancelSelector = null;
        ObservableInt mSnsInfoIsfavorite = null;
        ObservableInt userInfoIsfollow = null;
        if ((21 & dirtyFlags) != 0) {
            if (mSnsInfo != null) {
                mSnsInfoIsfavorite = mSnsInfo.isfavorite;
            }
            updateRegistration(0, mSnsInfoIsfavorite);
            if (mSnsInfoIsfavorite != null) {
                mSnsInfoIsfavoriteGet = mSnsInfoIsfavorite.get();
            }
            boolean mSnsInfoIsfavoriteInt0 = mSnsInfoIsfavoriteGet == 0;
            if ((21 & dirtyFlags) != 0) {
                if (mSnsInfoIsfavoriteInt0) {
                    dirtyFlags = (dirtyFlags | 64) | 256;
                } else {
                    dirtyFlags = (dirtyFlags | 32) | 128;
                }
            }
            mSnsInfoIsfavoriteInt0CollectTextAndroidDrawableCollectSelectorCollectTextAndroidDrawableUncollectSelector = mSnsInfoIsfavoriteInt0 ? getDrawableFromResource(this.collectText, 2130837795) : getDrawableFromResource(this.collectText, 2130838558);
            if (mSnsInfoIsfavoriteInt0) {
                mSnsInfoIsfavoriteInt0CollectTextAndroidStringCollectCollectTextAndroidStringCancelCollect = this.collectText.getResources().getString(2131230941);
            } else {
                mSnsInfoIsfavoriteInt0CollectTextAndroidStringCollectCollectTextAndroidStringCancelCollect = this.collectText.getResources().getString(2131230888);
            }
        }
        if ((26 & dirtyFlags) != 0) {
            if (userInfo != null) {
                userInfoIsfollow = userInfo.isfollow;
            }
            updateRegistration(1, userInfoIsfollow);
            if (userInfoIsfollow != null) {
                userInfoIsfollowGet = userInfoIsfollow.get();
            }
            boolean userInfoIsfollowInt0 = userInfoIsfollowGet == 0;
            if ((26 & dirtyFlags) != 0) {
                if (userInfoIsfollowInt0) {
                    dirtyFlags = (dirtyFlags | 1024) | 4096;
                } else {
                    dirtyFlags = (dirtyFlags | 512) | 2048;
                }
            }
            userInfoIsfollowInt0FollowAndroidStringFollowFollowAndroidStringCancelAttention = userInfoIsfollowInt0 ? this.follow.getResources().getString(2131231056) : this.follow.getResources().getString(2131230887);
            userInfoIsfollowInt0FollowAndroidDrawableArticleFollowSelectorFollowAndroidDrawableArticleFollowCancelSelector = userInfoIsfollowInt0 ? getDrawableFromResource(this.follow, 2130837613) : getDrawableFromResource(this.follow, 2130837611);
        }
        if ((21 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableTop(this.collectText, mSnsInfoIsfavoriteInt0CollectTextAndroidDrawableCollectSelectorCollectTextAndroidDrawableUncollectSelector);
            TextViewBindingAdapter.setText(this.collectText, mSnsInfoIsfavoriteInt0CollectTextAndroidStringCollectCollectTextAndroidStringCancelCollect);
        }
        if ((26 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setDrawableTop(this.follow, userInfoIsfollowInt0FollowAndroidDrawableArticleFollowSelectorFollowAndroidDrawableArticleFollowCancelSelector);
            TextViewBindingAdapter.setText(this.follow, userInfoIsfollowInt0FollowAndroidStringFollowFollowAndroidStringCancelAttention);
        }
    }

    public static ArticleShareDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleShareDialogBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ArticleShareDialogBinding) DataBindingUtil.inflate(inflater, 2130903162, root, attachToRoot, bindingComponent);
    }

    public static ArticleShareDialogBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleShareDialogBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(2130903162, null, false), bindingComponent);
    }

    public static ArticleShareDialogBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ArticleShareDialogBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/article_share_dialog_0".equals(view.getTag())) {
            return new ArticleShareDialogBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
