package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.adapter.PopularShowsHostAdapter;
import com.feng.car.databinding.ActivityPopularProgramListBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.databinding.LayoutPopularProgramListHeaderBinding;
import com.feng.car.databinding.PopularProgramListArraydialogBinding;
import com.feng.car.databinding.PopularProgramShareDialogBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.ShareUtils;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LRecyclerViewOnToucheListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PopularProgramListActivity extends BaseActivity<ActivityPopularProgramListBinding> implements LRecyclerView$LRecyclerViewOnToucheListener {
    public static final int SORT_COMMENT_COUNT = 3;
    public static final int SORT_PUBLISH_TIME = 1;
    public static final int SORT_READ_COUNT = 2;
    private final int MAX_DISTANCE = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
    private final int MIN_REFRESH_DISTANCE = 150;
    private int m220;
    private int m250;
    private int m8;
    private int m88;
    private Dialog mCommonDialog;
    private int mCurrentHeaderheight = 0;
    private int mCurrentHotShowSort = 1;
    private int mCurrentPage = 1;
    private LayoutPopularProgramListHeaderBinding mHeaderBinding;
    private int mHeaderHeight = 0;
    private PopularShowsHostAdapter mHostAdapter;
    private HotShowInfo mHotShoInfo = new HotShowInfo();
    private int mHotShowId = -1;
    private String mHotShowImageUrl = "";
    private String mHotShowName = "";
    private boolean mIsSetNotification = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LayoutParams mNormalParams;
    private DialogOpenNotifyBinding mOpenNotifyBinding;
    private CommonPostAdapter mPopularProgramListAdapter;
    private PopularProgramShareDialogBinding mPopularProgramShareDialogBinding;
    private Dialog mShareDialog;
    private SnsInfoList mSnsInfoList = new SnsInfoList();
    private PopularProgramListArraydialogBinding mSortArrayDialogBinding;
    private PopupWindow mSortArrayWindow;
    private int mTitleBarAlpha = 0;
    public int mTotalPage = 0;
    private UMShareListener mUMShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA share_media) {
            PopularProgramListActivity.this.showFirstTypeToast((int) R.string.share_success);
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            PopularProgramListActivity.this.showSecondTypeToast((int) R.string.share_failed);
        }

        public void onCancel(SHARE_MEDIA share_media) {
            PopularProgramListActivity.this.showSecondTypeToast((int) R.string.share_canceled);
        }
    };
    private UserInfoList mUserInfoList = new UserInfoList();

    static {
        StubApp.interface11(2636);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_popular_program_list;
    }

    public void getLocalIntentData() {
        this.mHotShowId = getIntent().getIntExtra("hotshowid", 0);
        this.mHotShowName = getIntent().getStringExtra("hot_show_name");
    }

    public void initView() {
        initNormalTitleBar("");
        hideDefaultTitleBar();
        ((ActivityPopularProgramListBinding) this.mBaseBinding).titlebar.setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList.scrollToPosition(0);
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList.setScrolledXDistance(0);
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList.setScrolledYDistance(0);
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).tvTitle.setVisibility(8);
            }
        }));
        ((ActivityPopularProgramListBinding) this.mBaseBinding).backImage.setOnClickListener(this);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setOnClickListener(this);
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.m250 = this.mResources.getDimensionPixelSize(R.dimen.default_250PX);
        this.m88 = this.mResources.getDimensionPixelSize(R.dimen.default_88PX);
        this.mPopularProgramListAdapter = new CommonPostAdapter(this, this.mSnsInfoList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mPopularProgramListAdapter);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.addItemDecoration(new SpacesItemDecoration(this));
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setRefreshProgressStyle(2);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setLRecyclerViewOnToucheListener(this);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList) != State.Loading) {
                    if (PopularProgramListActivity.this.mCurrentPage <= PopularProgramListActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(PopularProgramListActivity.this, ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList, 20, State.Loading, null);
                        PopularProgramListActivity.this.getDetailListInfo(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(PopularProgramListActivity.this, ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList, 20, State.TheEnd, null);
                }
            }
        });
        initHeader();
        ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (PopularProgramListActivity.this.mHotShoInfo.isremind.get() == 1) {
                    PopularProgramListActivity.this.showCloseRemindDialog();
                } else if (FengApplication.getInstance().isLoginUser()) {
                    PopularProgramListActivity.this.showOpenRemindDialog();
                } else {
                    PopularProgramListActivity.this.startActivity(new Intent(PopularProgramListActivity.this, LoginActivity.class));
                }
            }
        });
        ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setLScrollListener(new LRecyclerView$LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
                PopularProgramListActivity.this.changeTitleColor(distanceY, PopularProgramListActivity.this.mHeaderHeight - PopularProgramListActivity.this.m88);
            }

            public void onScrollStateChanged(int state) {
            }
        });
    }

    private void showRemindTips() {
        SharedUtil.putBoolean(this, "remind_tips", true);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setBackgroundResource(R.drawable.remind_tips_icon);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).showTipsView.setTarget(((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).showTipsView.show(this);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).llTips.setVisibility(0);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).step0.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).llTips.setVisibility(8);
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).showTipsView.hide();
                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).ivUpdateRemind.setBackgroundResource(R.drawable.icon_remind_selector);
            }
        });
    }

    private void initHeader() {
        this.mHeaderBinding = LayoutPopularProgramListHeaderBinding.inflate(this.mInflater, ((ActivityPopularProgramListBinding) this.mBaseBinding).rlParent, false);
        this.mHeaderHeight = (int) (((float) FengUtil.getScreenWidth(this)) * 0.5625f);
        this.mNormalParams = new LayoutParams(-1, this.mHeaderHeight);
        this.mHeaderBinding.headerBg.setLayoutParams(this.mNormalParams);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        LinearLayoutManager horizonLinearLayoutManager = new LinearLayoutManager(this);
        horizonLinearLayoutManager.setOrientation(0);
        this.mHeaderBinding.tvSortType.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PopularProgramListActivity.this.showArraryDialog(v);
            }
        });
        this.mHostAdapter = new PopularShowsHostAdapter(this, this.mUserInfoList.getUserInfoList());
        this.mHeaderBinding.lrvShowsHosts.setAdapter(this.mHostAdapter);
        this.mHeaderBinding.lrvShowsHosts.setLayoutManager(horizonLinearLayoutManager);
        this.mHeaderBinding.ivShowFollow.setOnClickListener(this);
        this.mHostAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                PopularProgramListActivity.this.mUserInfoList.get(position).intentToPersonalHome(PopularProgramListActivity.this);
            }
        });
    }

    private void showMoreDialog() {
        if (this.mPopularProgramShareDialogBinding == null) {
            this.mPopularProgramShareDialogBinding = PopularProgramShareDialogBinding.inflate(this.mInflater);
            this.mPopularProgramShareDialogBinding.friendsShare.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.weixinShare.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.weiboShare.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.qqShare.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.qzoneShare.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.copyLink.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.backHome.setOnClickListener(this);
            this.mPopularProgramShareDialogBinding.cancel.setOnClickListener(this);
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mShareDialog.setCanceledOnTouchOutside(true);
            this.mShareDialog.setCancelable(true);
            Window window = this.mShareDialog.getWindow();
            window.setGravity(80);
            window.setContentView(this.mPopularProgramShareDialogBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mShareDialog.show();
    }

    private void getDetailListInfo(final boolean needClear, boolean isLoadMore) {
        if (!isLoadMore || this.mCurrentPage <= this.mTotalPage) {
            Map<String, Object> map = new HashMap();
            map.put("hotshowid", String.valueOf(this.mHotShowId));
            map.put("sort", String.valueOf(this.mCurrentHotShowSort));
            map.put("page", String.valueOf(this.mCurrentPage));
            FengApplication.getInstance().httpRequest("snshotshow/getlistbyid/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    PopularProgramListActivity.this.hideProgress();
                    if (PopularProgramListActivity.this.mSnsInfoList.size() > 0) {
                        PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        PopularProgramListActivity.this.showNetErrorView();
                    }
                }

                public void onStart() {
                }

                public void onFinish() {
                    PopularProgramListActivity.this.hideProgress();
                    ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).optionImage.setVisibility(0);
                    ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).ivUpdateRemind.setVisibility(0);
                    ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).progressBar.setVisibility(8);
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PopularProgramListActivity.this.hideProgress();
                    if (PopularProgramListActivity.this.mSnsInfoList.size() > 0) {
                        PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        PopularProgramListActivity.this.showNetErrorView();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            PopularProgramListActivity.this.hideDefaultTitleBar();
                            JSONObject jsonHotShow = jsonResult.getJSONObject("body").getJSONObject("hotshow");
                            JSONObject jsonSns = jsonHotShow.getJSONObject("sns");
                            PopularProgramListActivity.this.mHotShoInfo.parser(jsonHotShow);
                            PopularProgramListActivity.this.mHotShowName = PopularProgramListActivity.this.mHotShoInfo.name;
                            PopularProgramListActivity.this.mHeaderBinding.setHotShowInfo(PopularProgramListActivity.this.mHotShoInfo);
                            ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).tvTitle.setText(PopularProgramListActivity.this.mHotShoInfo.name);
                            PopularProgramListActivity.this.mHeaderBinding.headerBg.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(PopularProgramListActivity.this.mHotShoInfo.image, 640, 0.56f)));
                            if (PopularProgramListActivity.this.mCurrentPage == 1) {
                                ((ActivityPopularProgramListBinding) PopularProgramListActivity.this.mBaseBinding).rcvPopularProgramList.setNoMore(false);
                                if (TextUtils.isEmpty(PopularProgramListActivity.this.mHotShoInfo.image.url)) {
                                    PopularProgramListActivity.this.mHotShowImageUrl = "http://imageqiniu.laosiji.com/FppDPbWgS3nZ1pvyoRx93cTuNCrp";
                                } else {
                                    PopularProgramListActivity.this.mHotShowImageUrl = PopularProgramListActivity.this.mHotShoInfo.image.url + "?imageView2/4/w/" + 512 + "/h/" + 512;
                                }
                            }
                            PopularProgramListActivity.this.changeRemind();
                            BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(SnsInfo.class, jsonSns);
                            PopularProgramListActivity.this.mTotalPage = baseListModel.pagecount;
                            List<SnsInfo> list = baseListModel.list;
                            int oldSize = PopularProgramListActivity.this.mSnsInfoList.size();
                            if (list != null && list.size() > 0) {
                                if (needClear) {
                                    PopularProgramListActivity.this.mSnsInfoList.clear();
                                    PopularProgramListActivity.this.mUserInfoList.clear();
                                }
                                PopularProgramListActivity.this.mUserInfoList.addAll(PopularProgramListActivity.this.mHotShoInfo.userlist.getUserInfoList());
                                PopularProgramListActivity.this.mSnsInfoList.addAll(list);
                                PopularProgramListActivity.this.mCurrentPage = PopularProgramListActivity.this.mCurrentPage + 1;
                            }
                            if (PopularProgramListActivity.this.mUserInfoList.size() > 0) {
                                PopularProgramListActivity.this.mHeaderBinding.llHostParent.setVisibility(0);
                                PopularProgramListActivity.this.mHostAdapter.notifyDataSetChanged();
                            } else {
                                PopularProgramListActivity.this.mHeaderBinding.llHostParent.setVisibility(8);
                            }
                            if (needClear) {
                                PopularProgramListActivity.this.mPopularProgramListAdapter.notifyDataSetChanged();
                            } else {
                                PopularProgramListActivity.this.mPopularProgramListAdapter.notifyItemRangeInserted(oldSize, PopularProgramListActivity.this.mSnsInfoList.size() - oldSize);
                            }
                            PopularProgramListActivity.this.mHeaderBinding.llPopProgramListHeaderContainer.setVisibility(0);
                            if (!SharedUtil.getBoolean(PopularProgramListActivity.this, "remind_tips", false)) {
                                PopularProgramListActivity.this.showRemindTips();
                                return;
                            }
                            return;
                        }
                        FengApplication.getInstance().checkCode("snshotshow/getlistbyid/", code);
                        if (PopularProgramListActivity.this.mHotShowId > 0) {
                            PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        } else {
                            PopularProgramListActivity.this.showNetErrorView();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog("snshotshow/getlistbyid/", content, e);
                        if (PopularProgramListActivity.this.mHotShowId > 0) {
                            PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        } else {
                            PopularProgramListActivity.this.showNetErrorView();
                        }
                    }
                }
            });
            return;
        }
        showThirdTypeToast((int) R.string.load_more_tips);
    }

    private void changeRemind() {
        if (this.mHotShoInfo.isremind.get() == 1) {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setBackgroundResource(R.drawable.icon_reminded_selector);
        } else {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setBackgroundResource(R.drawable.icon_remind_selector);
        }
    }

    public void onSingleClick(View view) {
        switch (view.getId()) {
            case R.id.cancel /*2131624291*/:
                this.mShareDialog.dismiss();
                return;
            case R.id.backImage /*2131624418*/:
                finish();
                return;
            case R.id.option_image /*2131624420*/:
                showMoreDialog();
                return;
            case R.id.friendsShare /*2131624822*/:
                ShareUtils.sharePopularProgramToFriends(this, this.mHotShowName, this.mHotShowId, this.mHotShowImageUrl, this.mUMShareListener);
                return;
            case R.id.weixinShare /*2131624823*/:
                ShareUtils.sharePopularProgramToWechat(this, this.mHotShowName, this.mHotShowId, this.mHotShowImageUrl, this.mUMShareListener);
                return;
            case R.id.weiboShare /*2131624824*/:
                ShareUtils.sharePopularProgramToWeibo(this, this.mHotShowName, this.mHotShowId, this.mHotShowImageUrl, this.mUMShareListener);
                return;
            case R.id.qqShare /*2131624825*/:
                ShareUtils.sharePopularProgramToQQ(this, this.mHotShowName, this.mHotShowId, this.mHotShowImageUrl, this.mUMShareListener);
                return;
            case R.id.qzoneShare /*2131624826*/:
                ShareUtils.sharePopularProgramToQZone(this, this.mHotShowName, this.mHotShowId, this.mHotShowImageUrl, this.mUMShareListener);
                return;
            case R.id.copyLink /*2131624828*/:
                FengUtil.copyText(this, MessageFormat.format(HttpConstant.SHARE_PROGRAM, new Object[]{this.mHotShowId + ""}), "已复制");
                this.mShareDialog.dismiss();
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return;
            case R.id.iv_show_follow /*2131625229*/:
                if (this.mHotShoInfo.isfollow.get() == 1) {
                    showCancelAttentionDialog();
                    return;
                } else {
                    followShows();
                    return;
                }
            default:
                return;
        }
    }

    private void showCancelAttentionDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.make_sure), false));
        list.add(new DialogItemEntity(getString(R.string.cancel), true));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_cancel_follow_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    PopularProgramListActivity.this.followShows();
                }
            }
        }, false);
    }

    private void followShows() {
        int isFollow;
        Map<String, Object> map = new HashMap();
        if (this.mHotShoInfo.isfollow.get() == 0) {
            isFollow = 1;
        } else {
            isFollow = 0;
        }
        map.put("isfollow", String.valueOf(isFollow));
        map.put("hotshowid", String.valueOf(this.mHotShoInfo.id));
        FengApplication.getInstance().httpRequest("snshotshow/follow/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        PopularProgramListActivity.this.mHotShoInfo.isfollow.set(isFollow);
                        if (PopularProgramListActivity.this.mHotShoInfo.isfollow.get() != 1) {
                            PopularProgramListActivity.this.mHotShoInfo.isremind.set(0);
                            PopularProgramListActivity.this.changeRemind();
                        } else if (PopularProgramListActivity.this.mHotShoInfo.isremind.get() != 1) {
                            PopularProgramListActivity.this.showRemindDialog();
                        }
                        EventBus.getDefault().post(new ProgramFollowEvent(PopularProgramListActivity.this.mHotShoInfo.id, PopularProgramListActivity.this.mHotShoInfo.isfollow.get(), PopularProgramListActivity.this.mHotShoInfo.isremind.get()));
                        return;
                    }
                    FengApplication.getInstance().checkCode("snshotshow/follow/", code);
                } catch (JSONException e) {
                    PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/follow/", content, e);
                    e.printStackTrace();
                }
            }
        });
    }

    private void showNetErrorView() {
        this.mRootBinding.titleLine.getRoot().setVisibility(0);
        initNormalTitleBar("");
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PopularProgramListActivity.this.mCurrentPage = 1;
                PopularProgramListActivity.this.getDetailListInfo(true, false);
            }
        });
    }

    private void showArraryDialog(View view) {
        if (this.mSortArrayDialogBinding == null) {
            this.mSortArrayDialogBinding = PopularProgramListArraydialogBinding.inflate(this.mInflater);
            this.mSortArrayDialogBinding.arrayTime.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (PopularProgramListActivity.this.mCurrentHotShowSort != 1) {
                        PopularProgramListActivity.this.mCurrentHotShowSort = 1;
                        PopularProgramListActivity.this.mHeaderBinding.tvSortType.setText(R.string.comment_timearray);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_ffffff));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mCurrentPage = 1;
                        PopularProgramListActivity.this.getDetailListInfo(true, false);
                        PopularProgramListActivity.this.mLogGatherInfo.setCurrentPage(PopularProgramListActivity.this.getLogCurrentPage());
                    }
                    PopularProgramListActivity.this.hideCommentArrayWindow();
                }
            });
            this.mSortArrayDialogBinding.arrayCommentCount.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (PopularProgramListActivity.this.mCurrentHotShowSort != 3) {
                        PopularProgramListActivity.this.mCurrentHotShowSort = 3;
                        PopularProgramListActivity.this.mHeaderBinding.tvSortType.setText(R.string.sort_comment_count_array);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_ffffff));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mCurrentPage = 1;
                        PopularProgramListActivity.this.getDetailListInfo(true, false);
                        PopularProgramListActivity.this.mLogGatherInfo.setCurrentPage(PopularProgramListActivity.this.getLogCurrentPage());
                    }
                    PopularProgramListActivity.this.hideCommentArrayWindow();
                }
            });
            this.mSortArrayDialogBinding.arrayReadCount.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (PopularProgramListActivity.this.mCurrentHotShowSort != 2) {
                        PopularProgramListActivity.this.mCurrentHotShowSort = 2;
                        PopularProgramListActivity.this.mHeaderBinding.tvSortType.setText(R.string.sort_read_count_array);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_000000));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setTextColor(PopularProgramListActivity.this.mResources.getColor(R.color.color_ffffff));
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayTime.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayCommentCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mSortArrayDialogBinding.arrayReadCount.setPadding(PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8, PopularProgramListActivity.this.m8);
                        PopularProgramListActivity.this.mCurrentPage = 1;
                        PopularProgramListActivity.this.getDetailListInfo(true, false);
                        PopularProgramListActivity.this.mLogGatherInfo.setCurrentPage(PopularProgramListActivity.this.getLogCurrentPage());
                    }
                    PopularProgramListActivity.this.hideCommentArrayWindow();
                }
            });
        }
        if (this.mCurrentHotShowSort == 1) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_000000));
        } else if (this.mCurrentHotShowSort == 3) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_000000));
        } else if (this.mCurrentHotShowSort == 2) {
            this.mSortArrayDialogBinding.arrayTime.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayCommentCount.setBackgroundResource(17170445);
            this.mSortArrayDialogBinding.arrayCommentCount.setTextColor(this.mResources.getColor(R.color.color_000000));
            this.mSortArrayDialogBinding.arrayReadCount.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mSortArrayDialogBinding.arrayReadCount.setTextColor(this.mResources.getColor(R.color.color_ffffff));
        }
        this.mSortArrayDialogBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mSortArrayDialogBinding.arrayCommentCount.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mSortArrayDialogBinding.arrayReadCount.setPadding(this.m8, this.m8, this.m8, this.m8);
        if (this.mSortArrayWindow == null) {
            this.mSortArrayWindow = new PopupWindow(this.mSortArrayDialogBinding.getRoot(), this.m220, this.m250, true);
        }
        this.mSortArrayWindow.setFocusable(true);
        this.mSortArrayWindow.setOutsideTouchable(true);
        this.mSortArrayWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        int[] loc_int = new int[2];
        view.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.right = loc_int[0] + view.getWidth();
        location.bottom = loc_int[1] + view.getHeight();
        this.mSortArrayWindow.showAtLocation(view, 51, location.left, location.bottom - 100);
    }

    private void hideCommentArrayWindow() {
        if (this.mSortArrayWindow != null && this.mSortArrayWindow.isShowing()) {
            this.mSortArrayWindow.dismiss();
        }
    }

    protected void onResume() {
        super.onResume();
        if (this.mIsSetNotification) {
            if (FengUtil.isNotificationAuthorityEnabled(this)) {
                setRemind(0);
            }
            this.mIsSetNotification = false;
        }
    }

    private void showOpenRemindDialog() {
        if (FengUtil.isNotificationAuthorityEnabled(this)) {
            setRemind(0);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.pop_program_open_remind_confirm), false));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_open_remind_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(268435456);
                    intent.setData(Uri.fromParts("package", PopularProgramListActivity.this.getPackageName(), null));
                    PopularProgramListActivity.this.startActivity(intent);
                    PopularProgramListActivity.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void showCloseRemindDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.pop_program_close_remind_cancel), false));
        list.add(new DialogItemEntity(getString(R.string.pop_program_close_remind_confirm), false));
        CommonDialog.showCommonDialog(this, getString(R.string.pop_program_cancel_remind_tips), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 1) {
                    PopularProgramListActivity.this.setRemind(1);
                }
            }
        }, false);
    }

    private void setRemind(final int type) {
        Map<String, Object> map = new HashMap();
        map.put("hotshowid", String.valueOf(this.mHotShowId));
        map.put("type", String.valueOf(type));
        FengApplication.getInstance().httpRequest("snshotshow/setremind/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PopularProgramListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        if (type == 0) {
                            PopularProgramListActivity.this.mHotShoInfo.isremind.set(1);
                            MobclickAgent.onEvent(PopularProgramListActivity.this, "program_remind");
                            PopularProgramListActivity.this.showFirstTypeToast((int) R.string.pop_program_open_remind_succeed_tips);
                        } else {
                            PopularProgramListActivity.this.mHotShoInfo.isremind.set(0);
                            MobclickAgent.onEvent(PopularProgramListActivity.this, "program_remind_cancel");
                        }
                        PopularProgramListActivity.this.changeRemind();
                        EventBus.getDefault().post(new ProgramFollowEvent(PopularProgramListActivity.this.mHotShoInfo.id, PopularProgramListActivity.this.mHotShoInfo.isfollow.get(), PopularProgramListActivity.this.mHotShoInfo.isremind.get()));
                        return;
                    }
                    FengApplication.getInstance().checkCode("snshotshow/getlistbyid/", code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onActionMove(float mLastY, float mCurrentY) {
        if (mCurrentY > mLastY) {
            int changeHeight;
            if (((int) Math.abs(mCurrentY - mLastY)) >= GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION) {
                changeHeight = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
            } else {
                changeHeight = (int) Math.abs(mCurrentY - mLastY);
            }
            this.mCurrentHeaderheight = this.mHeaderHeight + changeHeight;
            this.mNormalParams.width = -1;
            this.mNormalParams.height = this.mCurrentHeaderheight;
            this.mHeaderBinding.headerBg.setLayoutParams(this.mNormalParams);
            if (changeHeight >= 150) {
                ((ActivityPopularProgramListBinding) this.mBaseBinding).progressBar.setVisibility(0);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setVisibility(8);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setVisibility(8);
                return;
            }
            ((ActivityPopularProgramListBinding) this.mBaseBinding).progressBar.setVisibility(8);
            ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setVisibility(0);
            ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setVisibility(0);
        }
    }

    public void onActionCancel() {
        if (this.mCurrentHeaderheight > this.mHeaderHeight) {
            if (this.mCurrentHeaderheight - this.mHeaderHeight > 150) {
                this.mCurrentPage = 1;
                getDetailListInfo(true, false);
            } else {
                ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setVisibility(0);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).progressBar.setVisibility(8);
            }
            this.mNormalParams.width = -1;
            this.mNormalParams.height = this.mHeaderHeight;
            this.mHeaderBinding.headerBg.setLayoutParams(this.mNormalParams);
            this.mCurrentHeaderheight = this.mHeaderHeight;
        }
    }

    private void changeTitleColor(int currentY, int maxY) {
        if (currentY <= 0) {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(0);
            ((ActivityPopularProgramListBinding) this.mBaseBinding).titleBottomLine.setVisibility(8);
        } else {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).titlebar.setBackgroundResource(R.color.color_ffffff);
            if (currentY >= maxY) {
                this.mTitleBarAlpha = 255;
                ((ActivityPopularProgramListBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(this.mTitleBarAlpha);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).tvTitle.setVisibility(0);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).titleBottomLine.setVisibility(0);
            } else {
                this.mTitleBarAlpha = (int) (((((double) currentY) * 1.0d) / ((double) maxY)) * 255.0d);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(this.mTitleBarAlpha);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).tvTitle.setVisibility(8);
                ((ActivityPopularProgramListBinding) this.mBaseBinding).titleBottomLine.setVisibility(8);
            }
        }
        if (currentY > 130) {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_selector);
            ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_bl_selector);
            ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setSelected(false);
            return;
        }
        ((ActivityPopularProgramListBinding) this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_wh_selector);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_wh_selector);
        ((ActivityPopularProgramListBinding) this.mBaseBinding).ivUpdateRemind.setSelected(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityPopularProgramListBinding) this.mBaseBinding).rcvPopularProgramList.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public void loginSuccess() {
        super.loginSuccess();
        this.mCurrentPage = 1;
        getDetailListInfo(true, false);
    }

    private void showRemindDialog() {
        if (this.mOpenNotifyBinding == null) {
            this.mOpenNotifyBinding = DialogOpenNotifyBinding.inflate(LayoutInflater.from(this));
            this.mOpenNotifyBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PopularProgramListActivity.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.openText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PopularProgramListActivity.this.showOpenRemindDialog();
                    PopularProgramListActivity.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.cancelText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PopularProgramListActivity.this.mCommonDialog.dismiss();
                }
            });
        }
        if (this.mCommonDialog == null) {
            this.mCommonDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mCommonDialog.setCanceledOnTouchOutside(true);
            this.mCommonDialog.setCancelable(true);
            Window window = this.mCommonDialog.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mOpenNotifyBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mCommonDialog.show();
    }

    public String getLogCurrentPage() {
        return "app_program?programid=" + this.mHotShowId + "&sortid=" + this.mCurrentHotShowSort;
    }
}
