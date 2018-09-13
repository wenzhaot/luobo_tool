package com.feng.car.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.BlackListActivity;
import com.feng.car.activity.CollectionActivity;
import com.feng.car.activity.DraftActivity;
import com.feng.car.activity.EditUserInfoActivity;
import com.feng.car.activity.FansActivity;
import com.feng.car.activity.FansFollowActivity;
import com.feng.car.activity.HistoryActivity;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.MessageActivity;
import com.feng.car.activity.QRCodeActivity;
import com.feng.car.activity.RegisterStoreActivity;
import com.feng.car.activity.RegisterStoreNoWechatActivity;
import com.feng.car.activity.SettingActivity;
import com.feng.car.activity.SnsAllActivity;
import com.feng.car.activity.VideoCacheActivity;
import com.feng.car.activity.WalletActivity;
import com.feng.car.activity.WeChatBindActivity;
import com.feng.car.adapter.EmptyAdapter;
import com.feng.car.databinding.FragmentMineBinding;
import com.feng.car.databinding.HeadMineBinding;
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.entity.user.PushModel;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.ChangeIntentToShopReStateEvent;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.RefreshEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserFirstFollowEvent;
import com.feng.car.event.UserInfoEditEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.utils.ShareUtils;
import com.feng.car.utils.WXUtils;
import com.feng.car.video.download.VideoDownloadManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.HashMap;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class MineFragment extends BaseFragment<FragmentMineBinding> {
    private HeadMineBinding mHeadBinding;
    private boolean mIntentToShopRegister = false;
    private boolean mIsLogin = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private UserInfo mUserInfo = new UserInfo();
    private UMShareListener umShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA share_media) {
            ((BaseActivity) MineFragment.this.mActivity).showFirstTypeToast(2131231555);
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231552);
        }

        public void onCancel(SHARE_MEDIA share_media) {
            ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231550);
        }
    };

    protected int setLayoutId() {
        return 2130903249;
    }

    protected void initView() {
        boolean flag;
        int i;
        this.mIsLogin = FengApplication.getInstance().isLoginUser();
        this.mHeadBinding = HeadMineBinding.inflate(this.mInflater);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(new EmptyAdapter(this.mActivity));
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeadBinding.getRoot());
        ((FragmentMineBinding) this.mBind).lrvMine.setAdapter(this.mLRecyclerViewAdapter);
        ((FragmentMineBinding) this.mBind).lrvMine.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((FragmentMineBinding) this.mBind).lrvMine.setRefreshProgressStyle(2);
        ((FragmentMineBinding) this.mBind).lrvMine.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((FragmentMineBinding) MineFragment.this.mBind).lrvMine, State.Normal);
                MineFragment.this.dataCheck();
            }
        });
        this.mHeadBinding.rlMineLoginTips.setOnClickListener(this);
        this.mHeadBinding.rlMineUserInfo.setOnClickListener(this);
        this.mHeadBinding.tvMineFollow.setOnClickListener(this);
        this.mHeadBinding.rlMineFans.setOnClickListener(this);
        this.mHeadBinding.tvMineArticles.setOnClickListener(this);
        this.mHeadBinding.mineDraft.setOnClickListener(this);
        this.mHeadBinding.mineLike.setOnClickListener(this);
        this.mHeadBinding.mineArticleHistory.setOnClickListener(this);
        this.mHeadBinding.mineVideoBuffer.setOnClickListener(this);
        this.mHeadBinding.mineCollection.setOnClickListener(this);
        this.mHeadBinding.mineScanQr.setOnClickListener(this);
        this.mHeadBinding.rlMineAllSns.setOnClickListener(this);
        this.mHeadBinding.rlMineBlackList.setOnClickListener(this);
        this.mHeadBinding.rlMineOpenStore.setOnClickListener(this);
        this.mHeadBinding.rlMineEdit.setOnClickListener(this);
        this.mHeadBinding.rlMineWallet.setOnClickListener(this);
        this.mHeadBinding.rlMineSetting.setOnClickListener(this);
        this.mHeadBinding.ivMessage.setOnClickListener(this);
        this.mHeadBinding.ivShareMoment.setOnClickListener(this);
        this.mHeadBinding.ivShareWechat.setOnClickListener(this);
        this.mHeadBinding.ivShareWeibo.setOnClickListener(this);
        this.mHeadBinding.ivShareQq.setOnClickListener(this);
        this.mHeadBinding.ivShareQzone.setOnClickListener(this);
        changeUI();
        if (SharedUtil.getInt(this.mActivity, HttpConstant.UPDATE_CODE, 0) > FengUtil.getAPPVerionCode(this.mActivity)) {
            flag = true;
        } else {
            flag = false;
        }
        if (flag) {
            this.mHeadBinding.dot.setVisibility(0);
        }
        View view = this.mHeadBinding.videoRemindDot;
        if (VideoDownloadManager.newInstance().isCacheHasRedPoint()) {
            i = 0;
        } else {
            i = 8;
        }
        view.setVisibility(i);
        ((FragmentMineBinding) this.mBind).lrvMine.setLScrollListener(new LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
        ((FragmentMineBinding) this.mBind).lrvMine.setRefreshing(true);
        String wxAction = FengApplication.getInstance().getNewUserActivity();
        if (TextUtils.isEmpty(wxAction)) {
            this.mHeadBinding.rlMineNewUser.setVisibility(8);
        } else if (wxAction.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
            this.mHeadBinding.rlMineNewUser.setVisibility(8);
        } else if (wxAction.indexOf("|||") > 0) {
            final String[] strSp = wxAction.split("\\|\\|\\|");
            if (strSp.length == 2) {
                this.mHeadBinding.rlMineNewUser.setVisibility(0);
                this.mHeadBinding.tvNewUser.setText(strSp[0]);
                this.mHeadBinding.rlMineNewUser.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        Intent intent = new Intent(MineFragment.this.mActivity, WeChatBindActivity.class);
                        if (!MineFragment.this.mIsLogin) {
                            intent.putExtra("type", WeChatBindActivity.LOGIN_TYPE);
                        } else if (MineFragment.this.mUserInfo.connect.weixin == 1) {
                            intent.putExtra("type", WeChatBindActivity.BIND_FINISH);
                        } else {
                            intent.putExtra("type", WeChatBindActivity.BIND_TYPE);
                        }
                        intent.putExtra("url", strSp[1]);
                        MineFragment.this.mActivity.startActivity(intent);
                    }
                });
                return;
            }
            this.mHeadBinding.rlMineNewUser.setVisibility(8);
        }
    }

    public void onResume() {
        super.onResume();
        this.mHeadBinding.rlMineAllSns.setVisibility(SharedUtil.getBoolean(this.mActivity, "sns_all_list", false) ? 0 : 8);
        if (this.mIntentToShopRegister && FengApplication.getInstance().isLoginUser()) {
            this.mIntentToShopRegister = false;
            if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 2) {
                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_ADD_SERVICE_PATH, new Object[0]));
            } else if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 3 || FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 4) {
                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_SHOP_PATH, Integer.valueOf(0)));
            } else {
                startActivity(new Intent(this.mActivity, RegisterStoreActivity.class));
            }
        }
        changeShopView();
    }

    public void onPause() {
        super.onPause();
    }

    public void loginOut() {
        this.mIsLogin = false;
        ((FragmentMineBinding) this.mBind).lrvMine.forceToRefresh();
        changeUI();
    }

    private void changeUI() {
        if (this.mIsLogin) {
            this.mHeadBinding.llMineLoginUser.setVisibility(0);
            this.mHeadBinding.ivMineHeadArrow.setVisibility(0);
            this.mHeadBinding.rlMineLoginTips.setVisibility(8);
            return;
        }
        this.mHeadBinding.llMineLoginUser.setVisibility(8);
        this.mHeadBinding.ivMineHeadArrow.setVisibility(8);
        this.mHeadBinding.rlMineLoginTips.setVisibility(0);
    }

    public void loginSuccess() {
        this.mIsLogin = true;
        ((FragmentMineBinding) this.mBind).lrvMine.forceToRefresh();
        changeUI();
    }

    public void onSingleClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case 2131624232:
                if (this.mIsLogin) {
                    startActivity(new Intent(this.mActivity, MessageActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625055:
            case 2131625062:
                this.mUserInfo.intentToPersonalHome(this.mActivity);
                return;
            case 2131625061:
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625063:
                intent = new Intent(this.mActivity, FansFollowActivity.class);
                intent.putExtra("userid", this.mUserInfo.id);
                this.mActivity.startActivity(intent);
                return;
            case 2131625064:
                intent = new Intent(this.mActivity, FansActivity.class);
                intent.putExtra("userid", this.mUserInfo.id);
                this.mActivity.startActivity(intent);
                this.mHeadBinding.fansMessage.setVisibility(8);
                return;
            case 2131625066:
                this.mActivity.startActivity(new Intent(this.mActivity, HistoryActivity.class));
                return;
            case 2131625067:
                this.mUserInfo.intentToPersonalHome(this.mActivity, 2001);
                return;
            case 2131625068:
                if (this.mIsLogin) {
                    this.mActivity.startActivity(new Intent(this.mActivity, QRCodeActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625069:
                this.mActivity.startActivity(new Intent(this.mActivity, CollectionActivity.class));
                return;
            case 2131625070:
                if (this.mIsLogin) {
                    this.mActivity.startActivity(new Intent(this.mActivity, DraftActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625071:
                this.mActivity.startActivity(new Intent(this.mActivity, VideoCacheActivity.class));
                this.mHeadBinding.videoRemindDot.setVisibility(8);
                VideoDownloadManager.newInstance().hideCacheRedPoint();
                VideoDownloadManager.newInstance().hideMineFragmentRedPoint();
                return;
            case 2131625074:
                this.mActivity.startActivity(new Intent(this.mActivity, SnsAllActivity.class));
                return;
            case 2131625077:
                if (this.mIsLogin) {
                    this.mActivity.startActivity(new Intent(this.mActivity, WalletActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625078:
                if (this.mIsLogin) {
                    this.mActivity.startActivity(new Intent(this.mActivity, EditUserInfoActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625079:
                if (this.mIsLogin) {
                    this.mActivity.startActivity(new Intent(this.mActivity, BlackListActivity.class));
                    return;
                }
                this.mActivity.startActivity(new Intent(this.mActivity, LoginActivity.class));
                return;
            case 2131625080:
                if (this.mIsLogin) {
                    FengApplication.getInstance().getUserInfo().checkShopState(this.mActivity, new SuccessFailCallback() {
                        public void onSuccess() {
                            super.onSuccess();
                            if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 2) {
                                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_ADD_SERVICE_PATH, new Object[0]));
                            } else if (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 3 || FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 4) {
                                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_SHOP_PATH, Integer.valueOf(0)));
                            } else {
                                MineFragment.this.startActivity(new Intent(MineFragment.this.mActivity, RegisterStoreNoWechatActivity.class));
                            }
                        }
                    });
                    return;
                } else {
                    startActivity(new Intent(this.mActivity, RegisterStoreNoWechatActivity.class));
                    return;
                }
            case 2131625082:
                this.mActivity.startActivity(new Intent(this.mActivity, SettingActivity.class));
                return;
            case 2131625084:
                ShareUtils.shareLSJMine(this.mActivity, SHARE_MEDIA.WEIXIN_CIRCLE, this.umShareListener);
                return;
            case 2131625085:
                ShareUtils.shareLSJMine(this.mActivity, SHARE_MEDIA.WEIXIN, this.umShareListener);
                return;
            case 2131625086:
                ShareUtils.shareLSJMine(this.mActivity, SHARE_MEDIA.SINA, this.umShareListener);
                return;
            case 2131625087:
                ShareUtils.shareLSJMine(this.mActivity, SHARE_MEDIA.QQ, this.umShareListener);
                return;
            case 2131625088:
                ShareUtils.shareLSJMine(this.mActivity, SHARE_MEDIA.QZONE, this.umShareListener);
                return;
            default:
                return;
        }
    }

    private void dataCheck() {
        if (FengApplication.getInstance().isLoginUser()) {
            FengApplication.getInstance().getUserInfo().checkShopState(this.mActivity, new SuccessFailCallback() {
                public void onSuccess() {
                    super.onSuccess();
                    MineFragment.this.getData();
                }
            });
        } else {
            getData();
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        if (this.mIsLogin) {
            map.put("userid", String.valueOf(FengApplication.getInstance().getUserInfo().id));
        } else {
            map.put("userid", String.valueOf(SharedUtil.getInt(this.mActivity, "usertouristId", 0)));
        }
        FengApplication.getInstance().httpRequest(HttpConstant.USERINFO, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((FragmentMineBinding) MineFragment.this.mBind).lrvMine, State.Normal);
                ((FragmentMineBinding) MineFragment.this.mBind).lrvMine.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        MineFragment.this.mUserInfo.parser(jsonResult.getJSONObject("body").getJSONObject(HttpConstant.USER));
                        MineFragment.this.bindData();
                        if (FengApplication.getInstance().isLoginUser()) {
                            FengApplication.getInstance().getUserInfo().getHeadImageInfo().url = MineFragment.this.mUserInfo.getHeadImageInfo().url;
                            FengApplication.getInstance().getUserInfo().push = new PushModel();
                            FengApplication.getInstance().getUserInfo().push.commentpush = MineFragment.this.mUserInfo.push.commentpush;
                            FengApplication.getInstance().getUserInfo().push.followpush = MineFragment.this.mUserInfo.push.followpush;
                            FengApplication.getInstance().getUserInfo().push.atmine = MineFragment.this.mUserInfo.push.atmine;
                            FengApplication.getInstance().getUserInfo().push.userdirectmessagepush = MineFragment.this.mUserInfo.push.userdirectmessagepush;
                            FengApplication.getInstance().getUserInfo().push.messageswitch = MineFragment.this.mUserInfo.push.messageswitch;
                            if (FengApplication.getInstance().getUserInfo().connect != null) {
                                FengApplication.getInstance().getUserInfo().connect.qq = MineFragment.this.mUserInfo.connect.qq;
                                FengApplication.getInstance().getUserInfo().connect.weixin = MineFragment.this.mUserInfo.connect.weixin;
                                FengApplication.getInstance().getUserInfo().connect.weibo = MineFragment.this.mUserInfo.connect.weibo;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadLog(true, "user/base/  " + code);
                } catch (Exception e) {
                    e.printStackTrace();
                    ((BaseActivity) MineFragment.this.mActivity).showSecondTypeToast(2131231273);
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.USERINFO, content, e);
                }
            }
        });
    }

    public void bindData() {
        this.mHeadBinding.setUserInfo(this.mUserInfo);
        this.mHeadBinding.tvMineArticles.setText(String.format(getString(2131230844), new Object[]{FengUtil.numberFormat(this.mUserInfo.snscount.get())}));
        this.mHeadBinding.tvMineFollow.setText(String.format(getString(2131231057), new Object[]{FengUtil.numberFormat(this.mUserInfo.followcount.get())}));
        this.mHeadBinding.tvMineFans.setText(String.format(getString(2131231033), new Object[]{FengUtil.numberFormat(this.mUserInfo.fanscount.get())}));
        this.mHeadBinding.afdMineHead.setHeadUrl(FengUtil.getHeadImageUrl(this.mUserInfo.getHeadImageInfo()));
        changeShopView();
    }

    private void changeShopView() {
        if (this.mIsLogin && (FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 4 || FengApplication.getInstance().getUserInfo().getLocalOpenShopState() == 3)) {
            this.mHeadBinding.tvMyShore.setText(2131231268);
        } else {
            this.mHeadBinding.tvMyShore.setText(2131231346);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoEditEvent event) {
        this.mHeadBinding.tvMineName.setText((CharSequence) event.mInfo.name.get());
        this.mHeadBinding.tvMineSignature.setText(event.mInfo.getIsFirstAuthenticatedInfo());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ChangeIntentToShopReStateEvent event) {
        this.mIntentToShopRegister = true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeRefreshEvent event) {
        if (event.mIndex == 4) {
            ((FragmentMineBinding) this.mBind).lrvMine.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (event != null && event.type == 2) {
            ((FragmentMineBinding) this.mBind).lrvMine.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        int i = 0;
        if (isAdded()) {
            MessageCountManager manager = MessageCountManager.getInstance();
            MessageCountInfo messageCountInfo = new MessageCountInfo();
            messageCountInfo.fansCount = manager.getFansCount();
            this.mHeadBinding.setMessageCountInfo(messageCountInfo);
            if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
                this.mHeadBinding.tvMessageCommentNum.setVisibility(8);
            } else {
                this.mHeadBinding.tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
                this.mHeadBinding.tvMessageCommentNum.setVisibility(0);
            }
            View view = this.mHeadBinding.videoRemindDot;
            if (!VideoDownloadManager.newInstance().isCacheHasRedPoint()) {
                i = 8;
            }
            view.setVisibility(i);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendDelArticleSuccess event) {
        if (event == null) {
            return;
        }
        if (event.type == 1) {
            this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() + 1);
            this.mHeadBinding.tvMineArticles.setText(String.format(getString(2131230844), new Object[]{FengUtil.numberFormat(this.mUserInfo.snscount.get())}));
        } else if (event.type == 2) {
            this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() - 1);
            this.mHeadBinding.tvMineArticles.setText(String.format(getString(2131230844), new Object[]{FengUtil.numberFormat(this.mUserInfo.snscount.get())}));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null && event.refreshType == 2005 && !event.deleterefresh) {
            this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() - 1);
            this.mHeadBinding.tvMineArticles.setText(String.format(getString(2131230844), new Object[]{FengUtil.numberFormat(this.mUserInfo.snscount.get())}));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event.type == 2 && FengUtil.isMine(this.mUserInfo.id)) {
            this.mUserInfo.isfollow.set(event.userInfo.isfollow.get());
            if (event.userInfo.isfollow.get() == 0) {
                this.mUserInfo.followcount.set(this.mUserInfo.followcount.get() - 1);
            } else {
                this.mUserInfo.followcount.set(this.mUserInfo.followcount.get() + 1);
            }
            this.mHeadBinding.tvMineFollow.setText(String.format(getString(2131231057), new Object[]{FengUtil.numberFormat(this.mUserInfo.followcount.get())}));
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserFirstFollowEvent event) {
        if (event != null && FengUtil.isMine(this.mUserInfo.id)) {
            ((FragmentMineBinding) this.mBind).lrvMine.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((FragmentMineBinding) this.mBind).lrvMine.setIsScrollDown(true);
        }
    }
}
