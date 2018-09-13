package com.feng.car.databinding;

import android.databinding.DataBindingComponent;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ViewDataBinding;
import android.databinding.adapters.ImageViewBindingAdapter;
import android.databinding.adapters.TextViewBindingAdapter;
import android.graphics.drawable.Drawable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.view.AutoFrescoDraweeView;

public class ActivityAudioPlayDetailBinding extends ViewDataBinding {
    private static final IncludedLayouts sIncludes = null;
    private static final SparseIntArray sViewsWithIds = new SparseIntArray();
    public final AutoFrescoDraweeView afdAudioDetailCover;
    public final ImageView ivAudioDivider;
    public final ImageView ivAudioPlayOrPause;
    public final ImageView ivAudioPraise;
    public final ImageView ivAudioShare;
    public final ImageView ivFollow;
    public final LinearLayout llAudioControllerContainer;
    public final LinearLayout llAudioDownArrowContainer;
    private long mDirtyFlags = -1;
    private SnsInfo mSnsInfo;
    private UserInfo mUserInfo;
    private final RelativeLayout mboundView0;
    public final RelativeLayout rlAudioBottomAuthorContainer;
    public final RelativeLayout rlAudioHeadInfoContainer;
    public final RelativeLayout rlSeekContainer;
    public final SeekBar seekAudioDetailBar;
    public final TextView tvAudioCurrentTime;
    public final TextView tvAudioDetailQuitListening;
    public final TextView tvAudioDetailTitle;
    public final TextView tvAudioTotalTime;
    public final TextView tvFollow;
    public final TextView tvSignature;
    public final TextView tvUserName;
    public final AutoFrescoDraweeView userImage;

    static {
        sViewsWithIds.put(R.id.rl_audio_head_info_container, 6);
        sViewsWithIds.put(R.id.afd_audio_detail_cover, 7);
        sViewsWithIds.put(R.id.tv_audio_detail_title, 8);
        sViewsWithIds.put(R.id.ll_audio_down_arrow_container, 9);
        sViewsWithIds.put(R.id.tv_audio_detail_quit_listening, 10);
        sViewsWithIds.put(R.id.rl_seek_container, 11);
        sViewsWithIds.put(R.id.tv_audio_current_time, 12);
        sViewsWithIds.put(R.id.tv_audio_total_time, 13);
        sViewsWithIds.put(R.id.seek_audio_detail_bar, 14);
        sViewsWithIds.put(R.id.ll_audio_controller_container, 15);
        sViewsWithIds.put(R.id.iv_audio_share, 16);
        sViewsWithIds.put(R.id.iv_audio_play_or_pause, 17);
        sViewsWithIds.put(R.id.iv_audio_divider, 18);
        sViewsWithIds.put(R.id.rl_audio_bottom_author_container, 19);
        sViewsWithIds.put(R.id.user_image, 20);
    }

    public ActivityAudioPlayDetailBinding(DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 3);
        Object[] bindings = ViewDataBinding.mapBindings(bindingComponent, root, 21, sIncludes, sViewsWithIds);
        this.afdAudioDetailCover = (AutoFrescoDraweeView) bindings[7];
        this.ivAudioDivider = (ImageView) bindings[18];
        this.ivAudioPlayOrPause = (ImageView) bindings[17];
        this.ivAudioPraise = (ImageView) bindings[1];
        this.ivAudioPraise.setTag(null);
        this.ivAudioShare = (ImageView) bindings[16];
        this.ivFollow = (ImageView) bindings[5];
        this.ivFollow.setTag(null);
        this.llAudioControllerContainer = (LinearLayout) bindings[15];
        this.llAudioDownArrowContainer = (LinearLayout) bindings[9];
        this.mboundView0 = (RelativeLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.rlAudioBottomAuthorContainer = (RelativeLayout) bindings[19];
        this.rlAudioHeadInfoContainer = (RelativeLayout) bindings[6];
        this.rlSeekContainer = (RelativeLayout) bindings[11];
        this.seekAudioDetailBar = (SeekBar) bindings[14];
        this.tvAudioCurrentTime = (TextView) bindings[12];
        this.tvAudioDetailQuitListening = (TextView) bindings[10];
        this.tvAudioDetailTitle = (TextView) bindings[8];
        this.tvAudioTotalTime = (TextView) bindings[13];
        this.tvFollow = (TextView) bindings[3];
        this.tvFollow.setTag(null);
        this.tvSignature = (TextView) bindings[4];
        this.tvSignature.setTag(null);
        this.tvUserName = (TextView) bindings[2];
        this.tvUserName.setTag(null);
        this.userImage = (AutoFrescoDraweeView) bindings[20];
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
            case 75:
                setUserInfo((UserInfo) variable);
                return true;
            default:
                return false;
        }
    }

    public void setSnsInfo(SnsInfo SnsInfo) {
        this.mSnsInfo = SnsInfo;
        synchronized (this) {
            this.mDirtyFlags |= 8;
        }
        notifyPropertyChanged(62);
        super.requestRebind();
    }

    public SnsInfo getSnsInfo() {
        return this.mSnsInfo;
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

    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0:
                return onChangeUserInfoName((ObservableField) object, fieldId);
            case 1:
                return onChangeSnsInfoIspraise((ObservableInt) object, fieldId);
            case 2:
                return onChangeUserInfoIsfollow((ObservableInt) object, fieldId);
            default:
                return false;
        }
    }

    private boolean onChangeUserInfoName(ObservableField<String> observableField, int fieldId) {
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

    private boolean onChangeSnsInfoIspraise(ObservableInt SnsInfoIspraise, int fieldId) {
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

    private boolean onChangeUserInfoIsfollow(ObservableInt UserInfoIsfollow, int fieldId) {
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
        Drawable snsInfoIspraiseInt0IvAudioPraiseAndroidDrawableAudioDetailPraiseBtnSelectorIvAudioPraiseAndroidDrawableAudioDetailPraiseAlreadyBtnSelector = null;
        String userInfoGetIsFirstAuthenticatedInfo = null;
        int userInfoIsfollowGet = 0;
        Drawable userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = null;
        int snsInfoIspraiseGet = 0;
        String userInfoNameGet = null;
        SnsInfo snsInfo = this.mSnsInfo;
        ObservableField<String> userInfoName = null;
        int userInfoIsfollowInt0ViewVISIBLEViewGONE = 0;
        UserInfo userInfo = this.mUserInfo;
        ObservableInt snsInfoIspraise = null;
        boolean userInfoGetIsFirstAuthenticated = false;
        int UserInfoIsfollowInt0ViewVISIBLEViewGONE1 = 0;
        ObservableInt userInfoIsfollow = null;
        if ((42 & dirtyFlags) != 0) {
            if (snsInfo != null) {
                snsInfoIspraise = snsInfo.ispraise;
            }
            updateRegistration(1, (Observable) snsInfoIspraise);
            if (snsInfoIspraise != null) {
                snsInfoIspraiseGet = snsInfoIspraise.get();
            }
            boolean snsInfoIspraiseInt0 = snsInfoIspraiseGet == 0;
            if ((42 & dirtyFlags) != 0) {
                if (snsInfoIspraiseInt0) {
                    dirtyFlags |= 128;
                } else {
                    dirtyFlags |= 64;
                }
            }
            if (snsInfoIspraiseInt0) {
                snsInfoIspraiseInt0IvAudioPraiseAndroidDrawableAudioDetailPraiseBtnSelectorIvAudioPraiseAndroidDrawableAudioDetailPraiseAlreadyBtnSelector = ViewDataBinding.getDrawableFromResource(this.ivAudioPraise, R.drawable.audio_detail_praise_btn_selector);
            } else {
                snsInfoIspraiseInt0IvAudioPraiseAndroidDrawableAudioDetailPraiseBtnSelectorIvAudioPraiseAndroidDrawableAudioDetailPraiseAlreadyBtnSelector = ViewDataBinding.getDrawableFromResource(this.ivAudioPraise, R.drawable.audio_detail_praise_already_btn_selector);
            }
        }
        if ((53 & dirtyFlags) != 0) {
            if ((48 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoGetIsFirstAuthenticatedInfo = userInfo.getIsFirstAuthenticatedInfo();
                    userInfoGetIsFirstAuthenticated = userInfo.getIsFirstAuthenticated();
                }
                if ((48 & dirtyFlags) != 0) {
                    if (userInfoGetIsFirstAuthenticated) {
                        dirtyFlags |= 512;
                    } else {
                        dirtyFlags |= 256;
                    }
                }
                userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull = userInfoGetIsFirstAuthenticated ? ViewDataBinding.getDrawableFromResource(this.tvUserName, R.drawable.authorities_big) : null;
            }
            if ((49 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoName = userInfo.name;
                }
                updateRegistration(0, (Observable) userInfoName);
                if (userInfoName != null) {
                    userInfoNameGet = (String) userInfoName.get();
                }
            }
            if ((52 & dirtyFlags) != 0) {
                if (userInfo != null) {
                    userInfoIsfollow = userInfo.isfollow;
                }
                updateRegistration(2, (Observable) userInfoIsfollow);
                if (userInfoIsfollow != null) {
                    userInfoIsfollowGet = userInfoIsfollow.get();
                }
                boolean userInfoIsfollowInt0 = userInfoIsfollowGet == 0;
                boolean UserInfoIsfollowInt01 = userInfoIsfollowGet != 0;
                if ((52 & dirtyFlags) != 0) {
                    if (userInfoIsfollowInt0) {
                        dirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
                    } else {
                        dirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
                    }
                }
                if ((52 & dirtyFlags) != 0) {
                    if (UserInfoIsfollowInt01) {
                        dirtyFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                    } else {
                        dirtyFlags |= PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
                    }
                }
                userInfoIsfollowInt0ViewVISIBLEViewGONE = userInfoIsfollowInt0 ? 0 : 8;
                UserInfoIsfollowInt0ViewVISIBLEViewGONE1 = UserInfoIsfollowInt01 ? 0 : 8;
            }
        }
        if ((42 & dirtyFlags) != 0) {
            ImageViewBindingAdapter.setImageDrawable(this.ivAudioPraise, snsInfoIspraiseInt0IvAudioPraiseAndroidDrawableAudioDetailPraiseBtnSelectorIvAudioPraiseAndroidDrawableAudioDetailPraiseAlreadyBtnSelector);
        }
        if ((52 & dirtyFlags) != 0) {
            this.ivFollow.setVisibility(userInfoIsfollowInt0ViewVISIBLEViewGONE);
            this.tvFollow.setVisibility(UserInfoIsfollowInt0ViewVISIBLEViewGONE1);
        }
        if ((48 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvSignature, userInfoGetIsFirstAuthenticatedInfo);
            TextViewBindingAdapter.setDrawableEnd(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
            TextViewBindingAdapter.setDrawableRight(this.tvUserName, userInfoGetIsFirstAuthenticatedTvUserNameAndroidDrawableAuthoritiesBigJavaLangObjectNull);
        }
        if ((49 & dirtyFlags) != 0) {
            TextViewBindingAdapter.setText(this.tvUserName, userInfoNameGet);
        }
    }

    public static ActivityAudioPlayDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAudioPlayDetailBinding inflate(LayoutInflater inflater, ViewGroup root, boolean attachToRoot, DataBindingComponent bindingComponent) {
        return (ActivityAudioPlayDetailBinding) DataBindingUtil.inflate(inflater, R.layout.activity_audio_play_detail, root, attachToRoot, bindingComponent);
    }

    public static ActivityAudioPlayDetailBinding inflate(LayoutInflater inflater) {
        return inflate(inflater, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAudioPlayDetailBinding inflate(LayoutInflater inflater, DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(R.layout.activity_audio_play_detail, null, false), bindingComponent);
    }

    public static ActivityAudioPlayDetailBinding bind(View view) {
        return bind(view, DataBindingUtil.getDefaultComponent());
    }

    public static ActivityAudioPlayDetailBinding bind(View view, DataBindingComponent bindingComponent) {
        if ("layout/activity_audio_play_detail_0".equals(view.getTag())) {
            return new ActivityAudioPlayDetailBinding(bindingComponent, view);
        }
        throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
    }
}
