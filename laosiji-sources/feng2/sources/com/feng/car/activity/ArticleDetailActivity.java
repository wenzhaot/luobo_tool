package com.feng.car.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.PopupWindow;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.ArticleDetailAdapter;
import com.feng.car.adapter.ArticleDetailAdapter.ArticleContentHolder;
import com.feng.car.adapter.ArticleDetailAdapter.InteractionChangeListener;
import com.feng.car.databinding.ActivityArticleDetailBinding;
import com.feng.car.databinding.ArticleCommentArraydialogBinding;
import com.feng.car.databinding.ArticleHeaderViewBinding;
import com.feng.car.databinding.ArticleShareDialogBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.comment.CommentInfoList;
import com.feng.car.entity.praise.PraiseInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.sns.SnsReadRecord;
import com.feng.car.entity.sns.SnsReadRecordList;
import com.feng.car.entity.thread.ArticleInfo;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.thread.GratuityRecordInfo;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.listener.FengUMShareListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleDetailActivity extends VideoBaseActivity<ActivityArticleDetailBinding> implements InteractionChangeListener {
    private final int HOT_COMMENT_ARRAY = 1;
    private final int NEED_LOCATION = 1;
    private final int NO_LOCATION = 0;
    private final int REQUEST_COMMENT = 1;
    private final int REQUEST_PRAISE = 3;
    private final int TIME_COMMENT_ARRAY = 2;
    private Drawable cancelFollowDrawable;
    private Drawable followDrawable;
    private boolean loadCommentSuccess = false;
    private boolean loadPraiseSuccess = false;
    private int m184;
    private int m220;
    private int m8;
    private AdvertInfo mAdvertInfo = new AdvertInfo();
    private boolean mAlreadyExposure = false;
    private ArticleDetailAdapter mArticleDetailAdapter;
    private ArticleHeaderViewBinding mArticleHeaderViewBinding;
    private ArticleShareDialogBinding mArticleShareBinding;
    private ArticleCommentArraydialogBinding mCommentArrayBinding;
    private PopupWindow mCommentArrayWindow;
    private int mCommentEmptyHight = 0;
    private int mCommentId = -1;
    private int mCommentOrderid = 1;
    private List<ArticleInfo> mContentList = new ArrayList();
    private int mCurrentOffset = 0;
    private int mCurrentPosition = 0;
    private int mFirstLocation = -1;
    private int mFirstVisiblePosition = -1;
    private CommentInfoList mHotCommentList = new CommentInfoList();
    private int mHotCommentPage = 1;
    private int mHotCommentTotalPage = 0;
    private List<ImageInfo> mImageList = new ArrayList();
    private boolean mIsCommentChangeEmptyHight = false;
    private boolean mIsCommentEmpty = false;
    private boolean mIsCommentNeedEmpty = false;
    private boolean mIsPraiseChangeEmptyHight = false;
    private boolean mIsPraiseEmpty = false;
    public boolean mIsPraiseNeedEmpty = false;
    private boolean mIsRefresh = false;
    private boolean mIsShowTopBar = false;
    private IWXAPI mIwxapi;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mLastOffset;
    private int mLastPosition;
    private int mLastVisiblePosition = -1;
    private List<ArticleInfo> mList = new ArrayList();
    private int mOffset = -1;
    private int mOffsetYOfVisibleOne = -1;
    private int mPosition = -1;
    private int mPraiseEmptyHight = 0;
    private PraiseInfoList mPraiseList = new PraiseInfoList();
    private int mPraisePage = 1;
    private int mPraiseTotalPage = 0;
    private int mResourceId;
    private int mResourceType;
    private Dialog mShareDialog;
    private int mSnsId;
    private SnsInfo mSnsInfo = new SnsInfo();
    private SnsReadRecordList mSnsReadRecordList;
    private int mTabBarPos;
    private CommentInfoList mTimeCommentList = new CommentInfoList();
    private int mTimeCommentPage = 1;
    private int mTimeCommentTotalPage = 0;
    private int mType = 1;
    private int mValidRecyclerViewHight;
    private FengUMShareListener umShareListener;

    static {
        StubApp.interface11(2133);
    }

    protected native void onCreate(Bundle bundle);

    private void resetCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentPage = 1;
            } else {
                this.mTimeCommentPage = 1;
            }
        } else if (this.mType == 3) {
            this.mPraisePage = 1;
        }
    }

    private void updateCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentPage++;
            } else {
                this.mTimeCommentPage++;
            }
        } else if (this.mType == 3) {
            this.mPraisePage++;
        }
    }

    private int getCurrentPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                return this.mHotCommentPage;
            }
            return this.mTimeCommentPage;
        } else if (this.mType == 3) {
            return this.mPraisePage;
        } else {
            return 1;
        }
    }

    private int getTotalPage() {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                return this.mHotCommentTotalPage;
            }
            return this.mTimeCommentTotalPage;
        } else if (this.mType == 3) {
            return this.mPraiseTotalPage;
        } else {
            return 0;
        }
    }

    private void setTotalPage(int totalPage) {
        if (this.mType == 1) {
            if (this.mCommentOrderid == 1) {
                this.mHotCommentTotalPage = totalPage;
            } else {
                this.mTimeCommentTotalPage = totalPage;
            }
        } else if (this.mType == 3) {
            this.mPraiseTotalPage = totalPage;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_article_detail;
    }

    public void getLocalIntentData() {
        this.mSnsId = getIntent().getIntExtra("snsid", 0);
        this.mResourceId = getIntent().getIntExtra("resourceid", 0);
        this.mResourceType = getIntent().getIntExtra("resourcetype", 0);
        this.mCommentId = getIntent().getIntExtra("commentid", -1);
        this.mFirstLocation = getIntent().getIntExtra("location_type", -1);
    }

    public void initView() {
        initNormalTitleBar((int) R.string.articles);
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m184 = this.mResources.getDimensionPixelSize(R.dimen.default_184PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.mRootBinding.titleLine.ivRightImage.setBackgroundResource(R.drawable.icon_more_bl_selector);
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
        this.mRootBinding.titleLine.ivRightImage.setOnClickListener(this);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.scrollToPosition(0);
            }
        }));
        this.mArticleHeaderViewBinding = ArticleHeaderViewBinding.inflate(LayoutInflater.from(this), (ViewGroup) ((ActivityArticleDetailBinding) this.mBaseBinding).getRoot(), false);
        this.mArticleDetailAdapter = new ArticleDetailAdapter(this, this.mList);
        this.mArticleDetailAdapter.setInteractionChangeListener(this);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mArticleDetailAdapter);
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setRefreshProgressStyle(2);
        this.mLRecyclerViewAdapter.addHeaderView(this.mArticleHeaderViewBinding.getRoot());
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, State.Normal);
                ArticleDetailActivity.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                ArticleDetailActivity.this.resetCurrentPage();
                ArticleDetailActivity.this.mLastOffset = 0;
                ArticleDetailActivity.this.updateViewPosition(-1, 0);
                ArticleDetailActivity.this.getAdData();
            }
        });
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView) && !ArticleDetailActivity.this.mIsRefresh) {
                    if (ArticleDetailActivity.this.getCurrentPage() > ArticleDetailActivity.this.getTotalPage()) {
                        RecyclerViewStateUtils.setFooterViewState(ArticleDetailActivity.this, ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(ArticleDetailActivity.this, ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                    ArticleDetailActivity.this.loadBottomData(0, false);
                }
            }
        });
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setLScrollListener(new LRecyclerView$LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
                if (ArticleDetailActivity.this.mValidRecyclerViewHight == 0) {
                    ArticleDetailActivity.this.mValidRecyclerViewHight = ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).bottomLine.getTop();
                }
                LinearLayoutManager lm = (LinearLayoutManager) ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                int firstVisiblePosition = lm.findFirstVisibleItemPosition();
                int lastVisiblePosition = lm.findLastVisibleItemPosition();
                ArticleDetailActivity.this.mFirstVisiblePosition = firstVisiblePosition;
                ArticleDetailActivity.this.mLastVisiblePosition = lastVisiblePosition;
                int[] locations = new int[2];
                View view = lm.getChildAt(0);
                if (view != null) {
                    view.getLocationInWindow(locations);
                    ArticleDetailActivity.this.mOffsetYOfVisibleOne = locations[1];
                    ArticleContentHolder holder = ArticleDetailActivity.this.mArticleDetailAdapter.getTabHolder();
                    if (holder != null) {
                        int[] location = new int[2];
                        holder.mBinding.choiceLine.getLocationOnScreen(location);
                        int position = ArticleDetailActivity.this.mArticleDetailAdapter.getTabPosition();
                        if (location[1] >= ArticleDetailActivity.this.getTitleBarBottomY() && position >= firstVisiblePosition - 1) {
                            ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).topLine.setVisibility(8);
                            ArticleDetailActivity.this.mIsShowTopBar = false;
                            ArticleDetailActivity.this.adShowOrGone(true);
                        } else if (position >= firstVisiblePosition - 1) {
                            ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).topLine.setVisibility(8);
                            ArticleDetailActivity.this.mIsShowTopBar = false;
                            ArticleDetailActivity.this.adShowOrGone(true);
                        } else {
                            ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).topLine.setVisibility(0);
                            ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(255);
                            ArticleDetailActivity.this.mIsShowTopBar = true;
                            ArticleDetailActivity.this.adShowOrGone(false);
                        }
                    }
                    ArticleDetailActivity.this.setEmptyHight(lm);
                }
            }

            public void onScrollStateChanged(int state) {
                if (state == 0) {
                    if (ArticleDetailActivity.this.mArticleDetailAdapter.getTabPosition() != 0) {
                        ArticleContentHolder holder = ArticleDetailActivity.this.mArticleDetailAdapter.getTabHolder();
                        if (holder != null) {
                            LinearLayoutManager lm = (LinearLayoutManager) ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                            ArticleDetailActivity.this.updateViewPosition(ArticleDetailActivity.this.mArticleDetailAdapter.getTabPosition() + 2, holder.mBinding.getRoot().getTop());
                        }
                    }
                    if (ArticleDetailActivity.this.mLastVisiblePosition <= ArticleDetailActivity.this.mContentList.size() + 2 && ArticleDetailActivity.this.mLastVisiblePosition > 0) {
                        ArticleDetailActivity.this.mLogGatherInfo.addPercentageLog(ArticleDetailActivity.this.mSnsId, ArticleDetailActivity.this.mResourceId, ArticleDetailActivity.this.mResourceType, 0, ArticleDetailActivity.this.calculateReadPercent(), "");
                    }
                }
            }
        });
        this.mArticleHeaderViewBinding.userImage.setOnClickListener(this);
        ((ActivityArticleDetailBinding) this.mBaseBinding).ivForward.setOnClickListener(this);
        ((ActivityArticleDetailBinding) this.mBaseBinding).praiseLine.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).praiseLine.setEnabled(false);
                ArticleDetailActivity.this.mSnsInfo.praiseOperation(ArticleDetailActivity.this, false, new SuccessFailCallback() {
                    public void onSuccess() {
                        ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }

                    public void onFail() {
                        ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }
                });
            }
        });
        ((ActivityArticleDetailBinding) this.mBaseBinding).tvSendMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ArticleDetailActivity.this.mSnsInfo.intentToSendComment(ArticleDetailActivity.this);
            }
        });
        ((ActivityArticleDetailBinding) this.mBaseBinding).commentText.setOnClickListener(this);
        ((ActivityArticleDetailBinding) this.mBaseBinding).praiseText.setOnClickListener(this);
        this.umShareListener = new FengUMShareListener(this, this.mSnsInfo);
        regToWX();
    }

    private void regToWX() {
        this.mIwxapi = WXAPIFactory.createWXAPI(this, HttpConstant.BASE_WX_APP_ID, false);
        this.mIwxapi.registerApp(HttpConstant.BASE_WX_APP_ID);
    }

    private void setEmptyHight(LinearLayoutManager lm) {
        int dataSize = 0;
        try {
            if (this.mType == 1) {
                if (this.mCommentOrderid == 1) {
                    dataSize = this.mHotCommentList.size();
                } else {
                    dataSize = this.mTimeCommentList.size();
                }
            } else if (this.mType == 3) {
                dataSize = this.mPraiseList.size();
            }
            if (dataSize > 0) {
                if (this.mType == 3 && !this.mIsPraiseChangeEmptyHight && this.mIsPraiseNeedEmpty) {
                    calculateEmptyHight(lm, dataSize);
                } else if (this.mType == 1 && !this.mIsCommentChangeEmptyHight && this.mIsCommentNeedEmpty) {
                    calculateEmptyHight(lm, dataSize);
                }
            } else if (this.mType == 3 && this.mIsPraiseEmpty && !this.mIsPraiseChangeEmptyHight) {
                showHintEmpty();
            } else if (this.mType == 1 && this.mIsCommentEmpty && !this.mIsCommentChangeEmptyHight) {
                showHintEmpty();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calculateEmptyHight(LinearLayoutManager lm, int dataSize) {
        int firstVisiblePosition = lm.findFirstVisibleItemPosition();
        int lastVisiblePosition = lm.findLastVisibleItemPosition();
        View view;
        int hight;
        int tabBarTop;
        int i;
        View childView;
        ArticleInfo info;
        if (this.mIsShowTopBar) {
            if (firstVisiblePosition >= this.mTabBarPos + 2 && lastVisiblePosition <= (this.mContentList.size() + dataSize) + 2) {
                view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
                hight = 1;
                if (view.getTag() != null && view.getTag().toString().equals("empty_view")) {
                    tabBarTop = 0;
                    for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                        childView = lm.getChildAt(i);
                        if (childView.getTag() != null && childView.getTag().toString().equals("tab_bar")) {
                            tabBarTop = childView.getTop();
                            break;
                        }
                    }
                    hight = this.mValidRecyclerViewHight - (view.getTop() - tabBarTop);
                }
                int size;
                if (this.mType == 1 && this.mIsCommentNeedEmpty && !this.mIsCommentChangeEmptyHight) {
                    size = this.mList.size();
                    ((ArticleInfo) this.mList.get(size - 1)).viewhight = hight >= 0 ? hight : 1;
                    if (hight < 0) {
                        hight = 1;
                    }
                    this.mCommentEmptyHight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(size - 1);
                    this.mIsCommentChangeEmptyHight = true;
                } else if (this.mType == 3 && this.mIsPraiseNeedEmpty && !this.mIsPraiseChangeEmptyHight) {
                    size = this.mList.size();
                    ((ArticleInfo) this.mList.get(size - 1)).viewhight = hight >= 0 ? hight : 1;
                    if (hight < 0) {
                        hight = 1;
                    }
                    this.mPraiseEmptyHight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(size - 1);
                    this.mIsPraiseChangeEmptyHight = true;
                }
            }
        } else if (lastVisiblePosition == (this.mContentList.size() + dataSize) + 1) {
            tabBarTop = 0;
            for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                view = lm.getChildAt(i);
                if (view.getTag() != null && view.getTag().toString().equals("tab_bar")) {
                    tabBarTop = view.getTop();
                    break;
                }
            }
            view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
            if (lastVisiblePosition - 1 < this.mList.size()) {
                info = (ArticleInfo) this.mList.get(lastVisiblePosition - 1);
                hight = this.mValidRecyclerViewHight - (view.getBottom() - tabBarTop);
                if (this.mType == 1) {
                    this.mCommentEmptyHight = hight >= 0 ? hight : 0;
                    this.mIsCommentChangeEmptyHight = true;
                } else if (this.mType == 3) {
                    this.mIsPraiseChangeEmptyHight = true;
                    this.mPraiseEmptyHight = hight >= 0 ? hight : 0;
                }
                if (hight < 0) {
                    hight = 0;
                }
                info.viewhight = hight;
                this.mArticleDetailAdapter.notifyItemChanged(lastVisiblePosition - 1);
            }
        } else if (lastVisiblePosition != (this.mContentList.size() + dataSize) + 2) {
        } else {
            if ((this.mType == 3 && !this.mIsPraiseChangeEmptyHight) || (this.mType == 1 && !this.mIsCommentChangeEmptyHight)) {
                view = lm.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (view.getTag() != null && view.getTag().toString().equals("empty_view")) {
                    tabBarTop = 0;
                    for (i = 0; i < lastVisiblePosition - firstVisiblePosition; i++) {
                        childView = lm.getChildAt(i);
                        if (childView.getTag() != null && childView.getTag().toString().equals("tab_bar")) {
                            tabBarTop = childView.getTop();
                            break;
                        }
                    }
                    info = (ArticleInfo) this.mList.get(lastVisiblePosition - 2);
                    hight = this.mValidRecyclerViewHight - (view.getTop() - tabBarTop);
                    if (this.mType == 1) {
                        this.mCommentEmptyHight = hight >= 0 ? hight : 0;
                        this.mIsCommentChangeEmptyHight = true;
                    } else if (this.mType == 3) {
                        this.mPraiseEmptyHight = hight >= 0 ? hight : 0;
                        this.mIsPraiseChangeEmptyHight = true;
                    }
                    if (hight < 0) {
                        hight = 0;
                    }
                    info.viewhight = hight;
                    this.mArticleDetailAdapter.notifyItemChanged(lastVisiblePosition - 2);
                }
            }
        }
    }

    private void showHintEmpty() {
        int size = this.mList.size();
        if (size > 0) {
            ArticleInfo info = (ArticleInfo) this.mList.get(size - 1);
            if (this.mType == 3) {
                if (this.mPraiseEmptyHight == 0) {
                    this.mPraiseEmptyHight = this.mValidRecyclerViewHight - this.mResources.getDimensionPixelSize(R.dimen.default_98PX);
                }
                if (info.type == 6) {
                    info.viewhight = this.mPraiseEmptyHight;
                }
                this.mIsPraiseChangeEmptyHight = true;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            } else if (this.mType == 1) {
                if (this.mCommentEmptyHight == 0) {
                    this.mCommentEmptyHight = this.mValidRecyclerViewHight - this.mResources.getDimensionPixelSize(R.dimen.default_98PX);
                }
                if (info.type == 6) {
                    info.viewhight = this.mCommentEmptyHight;
                }
                this.mIsCommentChangeEmptyHight = true;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            }
        }
    }

    private void resetEmptyParameter() {
        this.mIsPraiseChangeEmptyHight = false;
        this.mPraiseEmptyHight = 0;
        this.mIsPraiseEmpty = false;
        this.mIsPraiseNeedEmpty = false;
        this.mIsCommentChangeEmptyHight = false;
        this.mCommentEmptyHight = 0;
        this.mIsCommentEmpty = false;
        this.mIsCommentNeedEmpty = false;
    }

    private void changeTabInitEmpty() {
        int size;
        if (this.mType == 3 && this.mPraiseEmptyHight != 0 && ((this.mIsPraiseEmpty || this.mIsPraiseNeedEmpty) && this.mIsPraiseChangeEmptyHight)) {
            size = this.mList.size();
            ((ArticleInfo) this.mList.get(size - 1)).viewhight = this.mPraiseEmptyHight;
            this.mArticleDetailAdapter.notifyItemChanged(size - 1);
        } else if (this.mType == 1 && this.mCommentEmptyHight != 0) {
            if ((this.mIsCommentEmpty || this.mIsCommentNeedEmpty) && this.mIsCommentChangeEmptyHight) {
                size = this.mList.size();
                ((ArticleInfo) this.mList.get(size - 1)).viewhight = this.mCommentEmptyHight;
                this.mArticleDetailAdapter.notifyItemChanged(size - 1);
            }
        }
    }

    private void updateViewPosition(int position, int offset) {
        this.mPosition = position;
        this.mOffset = offset;
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        SnsReadRecord snsReadRecord;
        if (this.mLastVisiblePosition < this.mArticleDetailAdapter.getTabPosition() + 2 || this.mArticleDetailAdapter.getTabPosition() == 0) {
            snsReadRecord = SnsReadRecordList.getInstance(this).getReadRecord(this.mSnsId);
            if (snsReadRecord != null) {
                snsReadRecord.firstVisiblePosition = this.mFirstVisiblePosition;
                snsReadRecord.offset = this.mOffsetYOfVisibleOne;
            } else {
                this.mSnsReadRecordList.addReadRecord(new SnsReadRecord(this.mSnsId, this.mFirstVisiblePosition, this.mOffsetYOfVisibleOne));
            }
        } else {
            snsReadRecord = SnsReadRecordList.getInstance(this).getReadRecord(this.mSnsId);
            if (snsReadRecord != null) {
                snsReadRecord.firstVisiblePosition = 0;
                snsReadRecord.offset = 0;
                this.mSnsReadRecordList.remove(this.mSnsReadRecordList.getPosition(this.mSnsId));
            }
        }
        this.mSnsReadRecordList.writeRecords();
        if (this.mLastVisiblePosition > 0) {
            this.mLogGatherInfo.addPercentageLog(this.mSnsId, this.mResourceId, this.mResourceType, 0, calculateReadPercent(), "");
        }
        super.onPause();
    }

    private void parseThread() {
        ArticleInfo articleInfo;
        if (this.mSnsInfo.list.size() == 0) {
            SnsPostResources snsPostResources = new SnsPostResources();
            snsPostResources.type = 1;
            snsPostResources.description = "";
            this.mSnsInfo.list.add(snsPostResources);
        }
        if (this.mSnsInfo.isoldthread == 1) {
            this.mSnsInfo.description.set("");
        }
        this.mArticleHeaderViewBinding.setMSnsInfo(this.mSnsInfo);
        this.mArticleHeaderViewBinding.describe.setContent((String) this.mSnsInfo.description.get(), true);
        if (this.mSnsInfo.user.getIsMy() == 1) {
            this.mArticleHeaderViewBinding.urvFianlpageTop.setVisibility(8);
            this.mArticleHeaderViewBinding.llExposureRead.setVisibility(0);
            this.mArticleHeaderViewBinding.tvExposureNum.setText(FengUtil.numberFormat(this.mSnsInfo.exposurecount.get()));
            this.mArticleHeaderViewBinding.tvReadNum.setText(FengUtil.numberFormat(this.mSnsInfo.cilckcount.get()));
        } else {
            this.mArticleHeaderViewBinding.urvFianlpageTop.setUserInfo(this.mSnsInfo.user);
            this.mArticleHeaderViewBinding.llExposureRead.setVisibility(8);
        }
        this.mArticleHeaderViewBinding.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
        initNormalTitleBar((String) this.mSnsInfo.title.get());
        ((ActivityArticleDetailBinding) this.mBaseBinding).setMSnsInfo(this.mSnsInfo);
        this.mArticleDetailAdapter.setmSnsInfo(this.mSnsInfo, this.mSwipeBackLayout);
        this.mArticleDetailAdapter.setAdvertInfo(this.mAdvertInfo);
        this.mArticleDetailAdapter.setmCommentId(this.mCommentId);
        this.mArticleDetailAdapter.setCircleList(this.mSnsInfo.communitylist);
        ((ActivityArticleDetailBinding) this.mBaseBinding).rlComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ArticleDetailActivity.this.mSnsInfo.commentcount.get() > 0) {
                    LinearLayoutManager lm = (LinearLayoutManager) ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                    int lastPosition = lm.findFirstVisibleItemPosition();
                    int[] locations = new int[2];
                    View view = lm.getChildAt(0);
                    if (view != null) {
                        view.getLocationInWindow(locations);
                        if (ArticleDetailActivity.this.mLastOffset != 0) {
                            if (lastPosition < ArticleDetailActivity.this.mContentList.size() + 1 && ArticleDetailActivity.this.mLastPosition < ArticleDetailActivity.this.mContentList.size() + 1) {
                                ArticleDetailActivity.this.mLastPosition = ArticleDetailActivity.this.mCurrentPosition;
                                ArticleDetailActivity.this.mLastOffset = ArticleDetailActivity.this.mCurrentOffset;
                            }
                            lm.scrollToPositionWithOffset(ArticleDetailActivity.this.mLastPosition, ArticleDetailActivity.this.mLastOffset - (ArticleDetailActivity.this.getResources().getDimensionPixelSize(R.dimen.default_88PX) + FengApplication.getInstance().getStatusBarHeight()));
                            ArticleDetailActivity.this.mCurrentPosition = ArticleDetailActivity.this.mLastPosition;
                            ArticleDetailActivity.this.mCurrentOffset = ArticleDetailActivity.this.mLastOffset;
                        } else if (ArticleDetailActivity.this.mIsShowTopBar) {
                            lm.scrollToPositionWithOffset(0, 0);
                        } else {
                            lm.scrollToPositionWithOffset(ArticleDetailActivity.this.mContentList.size() + 1, 0);
                        }
                        ArticleDetailActivity.this.mLastOffset = locations[1];
                        ArticleDetailActivity.this.mLastPosition = lastPosition;
                        return;
                    }
                    return;
                }
                ArticleDetailActivity.this.mSnsInfo.intentToSendComment(ArticleDetailActivity.this);
            }
        });
        List<SnsPostResources> mThreadInfoList = new ArrayList();
        mThreadInfoList.addAll(this.mSnsInfo.list);
        this.mList.clear();
        this.mContentList.clear();
        this.mImageList.clear();
        if (mThreadInfoList.size() > 0) {
            resetEmptyParameter();
            for (SnsPostResources postResources : mThreadInfoList) {
                if (postResources != null) {
                    articleInfo = new ArticleInfo();
                    articleInfo.resources = postResources;
                    articleInfo.type = 1;
                    this.mContentList.add(articleInfo);
                    if (articleInfo.resources.type == 2) {
                        ImageInfo imageInfo = articleInfo.resources.image;
                        if (!StringUtil.isEmpty(articleInfo.resources.description)) {
                            imageInfo.description = articleInfo.resources.description;
                        }
                        this.mImageList.add(imageInfo);
                    }
                }
            }
        }
        this.mTabBarPos = this.mContentList.size();
        articleInfo = new ArticleInfo();
        articleInfo.type = 5;
        this.mContentList.add(articleInfo);
        this.mList.addAll(this.mContentList);
        this.mLRecyclerViewAdapter.notifyDataSetChanged();
        this.mArticleDetailAdapter.setmImageList(this.mImageList);
    }

    private void parseThreadComment(List<CommentInfo> list, int locationType) {
        if (locationType == 1) {
            this.mList.clear();
            this.mList.addAll(this.mContentList);
        }
        if (list != null && list.size() > 0) {
            for (CommentInfo info : list) {
                if (info != null) {
                    ArticleInfo articleInfo = new ArticleInfo();
                    articleInfo.commentInfo = info;
                    articleInfo.type = 2;
                    this.mList.add(articleInfo);
                }
            }
        }
        int size = this.mCommentOrderid == 1 ? this.mHotCommentList.size() : this.mTimeCommentList.size();
        boolean isEmpty = false;
        if (size < 15) {
            this.mIsCommentNeedEmpty = true;
            ArticleInfo info2 = new ArticleInfo();
            if (size == 0) {
                info2.type = 6;
                isEmpty = true;
            } else {
                info2.type = 7;
                isEmpty = false;
            }
            info2.viewhight = this.mCommentEmptyHight > 0 ? this.mCommentEmptyHight : 1920;
            this.mList.add(info2);
        } else {
            this.mIsCommentNeedEmpty = false;
        }
        this.mLRecyclerViewAdapter.notifyDataSetChanged();
        if (this.mFirstLocation != -1) {
            ((LinearLayoutManager) ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(this.mContentList.size() + 1, 0);
        } else if (locationType == 1) {
            LinearLayoutManager lm = (LinearLayoutManager) ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            if (((ActivityArticleDetailBinding) this.mBaseBinding).topLine.isShown()) {
                lm.scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
            } else {
                lm.scrollToPositionWithOffset(this.mPosition, this.mOffset);
            }
        }
        if (!((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setVisibility(0);
            initScroll();
        }
    }

    private void getAdData() {
        Map<String, Object> map = new HashMap();
        map.put("pageid", String.valueOf(998));
        map.put("datatype", "0");
        map.put("pagecode", "0");
        FengApplication.getInstance().httpRequest("advert/adserver/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ArticleDetailActivity.this.loadData();
            }

            public void onStart() {
            }

            public void onFinish() {
                ArticleDetailActivity.this.loadData();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray adData = jsonResult.getJSONObject("body").getJSONArray(UriUtil.DATA_SCHEME);
                        if (adData.length() > 0) {
                            ArticleDetailActivity.this.mAdvertInfo.resetData();
                            ArticleDetailActivity.this.mAdvertInfo.parser(adData.getJSONObject(0));
                            return;
                        }
                        ArticleDetailActivity.this.mAdvertInfo.resetData();
                        return;
                    }
                    ArticleDetailActivity.this.mAdvertInfo.resetData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadData() {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("showtype", String.valueOf(1));
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        FengApplication.getInstance().httpRequest("sns/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ArticleDetailActivity.this.hideProgress();
                if (ArticleDetailActivity.this.mList.size() > 0) {
                    ArticleDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    ArticleDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ArticleDetailActivity.this.getAdData();
                        }
                    });
                }
            }

            public void onStart() {
                ArticleDetailActivity.this.mIsRefresh = true;
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, State.Normal);
                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.refreshComplete();
                if (ArticleDetailActivity.this.loadCommentSuccess || ArticleDetailActivity.this.loadPraiseSuccess) {
                    ArticleDetailActivity.this.hideProgress();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ArticleDetailActivity.this.mList.size() > 0) {
                    ArticleDetailActivity.this.showThirdTypeToast((int) R.string.check_network);
                    return;
                }
                ArticleDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ArticleDetailActivity.this.getAdData();
                    }
                });
                ArticleDetailActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        ArticleDetailActivity.this.mSnsInfo.parser(jsonObject.getJSONObject("body").getJSONObject("sns"));
                        if (ArticleDetailActivity.this.mSnsId == 0) {
                            ArticleDetailActivity.this.mSnsId = ArticleDetailActivity.this.mSnsInfo.id;
                        }
                        if (ArticleDetailActivity.this.mSnsInfo.isflag == 0) {
                            ArticleDetailActivity.this.hideEmptyView();
                            ArticleDetailActivity.this.parseThread();
                            if (ArticleDetailActivity.this.mFirstLocation != -1) {
                                if (ArticleDetailActivity.this.mFirstLocation == 1) {
                                    ArticleDetailActivity.this.mType = 1;
                                } else if (ArticleDetailActivity.this.mFirstLocation == 3) {
                                    ArticleDetailActivity.this.mType = 3;
                                }
                                ArticleDetailActivity.this.changeTabColor(ArticleDetailActivity.this.mType);
                                ArticleDetailActivity.this.loadBottomData(1, false);
                                ArticleDetailActivity.this.mArticleDetailAdapter.setType(ArticleDetailActivity.this.mType);
                                return;
                            }
                            ArticleDetailActivity.this.changeTabColor(ArticleDetailActivity.this.mType);
                            ArticleDetailActivity.this.loadBottomData(0, false);
                        } else if (ArticleDetailActivity.this.mSnsInfo.isflag == 1) {
                            ArticleDetailActivity.this.showEmptyView((int) R.string.fengwen_delete, (int) R.drawable.icon_article_delete);
                        } else if (ArticleDetailActivity.this.mSnsInfo.isflag == -1 || ArticleDetailActivity.this.mSnsInfo.isflag == -2 || ArticleDetailActivity.this.mSnsInfo.isflag == -3) {
                            ArticleDetailActivity.this.showEmptyView((int) R.string.fengwen_edit, (int) R.drawable.icon_article_modify);
                        } else {
                            ArticleDetailActivity.this.showEmptyView((int) R.string.fengwen_delete, (int) R.drawable.icon_article_delete);
                        }
                    } else if (code == -48 || code == -50) {
                        ArticleDetailActivity.this.showEmptyView((int) R.string.fengwen_delete, (int) R.drawable.icon_article_delete);
                    } else if (code == -49 || code == -51 || code == -52) {
                        ArticleDetailActivity.this.showEmptyView((int) R.string.fengwen_edit, (int) R.drawable.icon_article_delete);
                    } else {
                        if (ArticleDetailActivity.this.mList.size() > 0) {
                            ArticleDetailActivity.this.showThirdTypeToast((int) R.string.check_network);
                        } else {
                            ArticleDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    ArticleDetailActivity.this.getAdData();
                                }
                            });
                        }
                        FengApplication.getInstance().checkCode("sns/info/", code);
                    }
                } catch (Exception e) {
                    if (ArticleDetailActivity.this.mList.size() > 0) {
                        ArticleDetailActivity.this.showThirdTypeToast((int) R.string.check_network);
                    } else {
                        ArticleDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ArticleDetailActivity.this.getAdData();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("sns/info/", content, e);
                }
            }
        });
    }

    private void initScroll() {
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (ArticleDetailActivity.this.mFirstLocation == -1) {
                    SnsReadRecord snsReadRecord = ArticleDetailActivity.this.mSnsReadRecordList.getReadRecord(ArticleDetailActivity.this.mSnsId);
                    if (snsReadRecord != null && snsReadRecord.firstVisiblePosition > 0) {
                        ((LinearLayoutManager) ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(snsReadRecord.firstVisiblePosition, snsReadRecord.offset - (ArticleDetailActivity.this.getResources().getDimensionPixelSize(R.dimen.default_88PX) + FengApplication.getInstance().getStatusBarHeight()));
                        return;
                    }
                    return;
                }
                ArticleDetailActivity.this.mFirstLocation = -1;
            }
        });
    }

    private void loadCommentData(final int locationType) {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("page", String.valueOf(getCurrentPage()));
        map.put("orderid", String.valueOf(this.mCommentOrderid));
        FengApplication.getInstance().httpRequest("comment/getlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ArticleDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                ArticleDetailActivity.this.hideProgress();
            }

            public void onStart() {
                if (!ArticleDetailActivity.this.loadCommentSuccess) {
                    ArticleDetailActivity.this.showLaoSiJiDialog();
                }
            }

            public void onFinish() {
                ArticleDetailActivity.this.mIsRefresh = false;
                RecyclerViewStateUtils.setFooterViewState(((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, State.Normal);
                ArticleDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ArticleDetailActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                    ArticleDetailActivity.this.mIsCommentEmpty = true;
                    ArticleDetailActivity.this.parseThreadComment(null, locationType);
                }
                ArticleDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!ArticleDetailActivity.this.loadCommentSuccess) {
                            ArticleDetailActivity.this.loadCommentSuccess = true;
                        }
                        JSONObject commentJson = resultJson.getJSONObject("body").getJSONObject("comment");
                        BaseListModel<CommentInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CommentInfo.class, commentJson);
                        ArticleDetailActivity.this.setTotalPage(baseListModel.pagecount);
                        List<CommentInfo> list = baseListModel.list;
                        if (list.size() > 0) {
                            ArticleDetailActivity.this.mIsCommentEmpty = false;
                            if (ArticleDetailActivity.this.mCommentOrderid == 1) {
                                if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                                    ArticleDetailActivity.this.mHotCommentList.clear();
                                }
                                ArticleDetailActivity.this.mHotCommentList.addAll(list);
                            } else if (ArticleDetailActivity.this.mCommentOrderid == 2) {
                                if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                                    ArticleDetailActivity.this.mTimeCommentList.clear();
                                }
                                ArticleDetailActivity.this.mTimeCommentList.addAll(list);
                            }
                            ArticleDetailActivity.this.updateCurrentPage();
                            ArticleDetailActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                            ArticleDetailActivity.this.mIsCommentEmpty = true;
                            ArticleDetailActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("comment/getlist/", code);
                } catch (JSONException e) {
                    ArticleDetailActivity.this.showSecondTypeToast((int) R.string.analysis_commend_fail);
                    FengApplication.getInstance().upLoadTryCatchLog("comment/getlist/", content, e);
                }
            }
        });
    }

    private void loadPraiseData(final int locationType) {
        Map<String, Object> map = new HashMap();
        map.put("snsid", String.valueOf(this.mSnsId));
        map.put("resourceid", String.valueOf(this.mResourceId));
        map.put("resourcetype", String.valueOf(this.mResourceType));
        map.put("page", String.valueOf(getCurrentPage()));
        FengApplication.getInstance().httpRequest("sns/praise/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ArticleDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                ArticleDetailActivity.this.hideProgress();
            }

            public void onStart() {
                if (!ArticleDetailActivity.this.loadPraiseSuccess) {
                    ArticleDetailActivity.this.showLaoSiJiDialog();
                }
            }

            public void onFinish() {
                ArticleDetailActivity.this.mIsRefresh = false;
                RecyclerViewStateUtils.setFooterViewState(((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView, State.Normal);
                ArticleDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ArticleDetailActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                    ArticleDetailActivity.this.mIsPraiseEmpty = true;
                    ArticleDetailActivity.this.parseThreadPraise(null, locationType);
                }
                ArticleDetailActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!ArticleDetailActivity.this.loadPraiseSuccess) {
                            ArticleDetailActivity.this.loadPraiseSuccess = true;
                        }
                        JSONObject userJson = resultJson.getJSONObject("body").getJSONObject("praise");
                        BaseListModel<GratuityRecordInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(GratuityRecordInfo.class, userJson);
                        ArticleDetailActivity.this.setTotalPage(baseListModel.pagecount);
                        List<GratuityRecordInfo> list = baseListModel.list;
                        GratuityRecordInfo gratuityInfo;
                        if (list.size() > 0) {
                            ArticleDetailActivity.this.mIsPraiseEmpty = false;
                            if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.setNoMore(false);
                                ArticleDetailActivity.this.mPraiseList.clear();
                            }
                            ArticleDetailActivity.this.mPraiseList.addAll(list);
                            if (ArticleDetailActivity.this.getCurrentPage() == ArticleDetailActivity.this.getTotalPage() && ArticleDetailActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                                gratuityInfo = new GratuityRecordInfo();
                                gratuityInfo.local_praise_num = ArticleDetailActivity.this.mSnsInfo.anonymouspraisenum;
                                ArticleDetailActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                                list.add(gratuityInfo);
                            }
                            ArticleDetailActivity.this.updateCurrentPage();
                            ArticleDetailActivity.this.parseThreadPraise(list, locationType);
                            return;
                        }
                        if (ArticleDetailActivity.this.getCurrentPage() == ArticleDetailActivity.this.getTotalPage() && ArticleDetailActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                            gratuityInfo = new GratuityRecordInfo();
                            gratuityInfo.local_praise_num = ArticleDetailActivity.this.mSnsInfo.anonymouspraisenum;
                            ArticleDetailActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                            list.add(gratuityInfo);
                        }
                        if (list.size() > 0) {
                            ArticleDetailActivity.this.parseThreadPraise(list, locationType);
                            return;
                        } else if (ArticleDetailActivity.this.getCurrentPage() == 1) {
                            ArticleDetailActivity.this.mIsPraiseEmpty = true;
                            ArticleDetailActivity.this.parseThreadPraise(list, locationType);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("sns/praise/", code);
                } catch (Exception e) {
                    FengApplication.getInstance().upLoadTryCatchLog("sns/praise/", content, e);
                }
            }
        });
    }

    private void parseThreadPraise(List<GratuityRecordInfo> list, int locationType) {
        ArticleInfo info;
        if (locationType == 1) {
            this.mList.clear();
            this.mList.addAll(this.mContentList);
        }
        if (list != null && list.size() > 0) {
            for (GratuityRecordInfo gratuityInfo : list) {
                info = new ArticleInfo();
                info.gratuityInfo = gratuityInfo;
                info.type = 3;
                this.mList.add(info);
            }
        }
        boolean isEmpty = false;
        if (this.mPraiseList.size() < 15) {
            this.mIsPraiseNeedEmpty = true;
            info = new ArticleInfo();
            if (this.mPraiseList.size() == 0) {
                info.type = 6;
                isEmpty = true;
            } else {
                info.type = 7;
                isEmpty = false;
            }
            info.viewhight = 1920;
            this.mList.add(info);
        } else {
            this.mIsPraiseNeedEmpty = false;
        }
        this.mLRecyclerViewAdapter.notifyDataSetChanged();
        if (this.mFirstLocation == 3) {
            ((LinearLayoutManager) ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(this.mContentList.size() + 1, 0);
        } else if (locationType == 1) {
            LinearLayoutManager lm = (LinearLayoutManager) ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            if (((ActivityArticleDetailBinding) this.mBaseBinding).topLine.isShown()) {
                lm.scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
            } else {
                lm.scrollToPositionWithOffset(this.mPosition, this.mOffset);
            }
        }
        if (!((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setVisibility(0);
            initScroll();
        }
    }

    private void changeTabColor(int type) {
        if (type == 1) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).commentText.setSelected(true);
            ((ActivityArticleDetailBinding) this.mBaseBinding).praiseText.setSelected(false);
        } else if (type == 3) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).commentText.setSelected(false);
            ((ActivityArticleDetailBinding) this.mBaseBinding).praiseText.setSelected(true);
        }
    }

    private void changeTab(int type) {
        this.mType = type;
        if (getCurrentPage() <= getTotalPage()) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setNoMore(false);
        }
        if (this.mType == 1) {
            changeTabColor(1);
            if (this.mCommentOrderid == 1) {
                if (this.mHotCommentList.size() > 0) {
                    parseThreadComment(this.mHotCommentList.getCommentList(), 1);
                } else {
                    loadBottomData(1);
                }
            } else if (this.mTimeCommentList.size() > 0) {
                parseThreadComment(this.mTimeCommentList.getCommentList(), 1);
            } else {
                loadBottomData(1);
            }
        } else if (this.mType == 3) {
            changeTabColor(3);
            if (this.mPraiseList.size() > 0) {
                parseThreadPraise(this.mPraiseList.getPraiseList(), 1);
            } else {
                loadBottomData(1);
            }
        }
        changeTabInitEmpty();
    }

    private void loadBottomData(int locationType, boolean isShowLoad) {
        if (isShowLoad) {
            showLaoSiJiDialog();
        }
        if (this.mType == 1) {
            loadCommentData(locationType);
        } else if (this.mType == 3) {
            loadPraiseData(locationType);
        }
    }

    private void loadBottomData(int locationType) {
        loadBottomData(locationType, true);
    }

    public void onSingleClick(View view) {
        Map<String, String> umengMap;
        switch (view.getId()) {
            case R.id.iv_forward /*2131624247*/:
                showShareDialog(true);
                return;
            case R.id.commentText /*2131624251*/:
                if (this.mType != 1) {
                    changeTab(1);
                    this.mArticleDetailAdapter.changeTab(this.mArticleDetailAdapter.getTabHolder(), 1);
                    return;
                }
                return;
            case R.id.praiseText /*2131624252*/:
                if (this.mType != 3) {
                    changeTab(3);
                    this.mArticleDetailAdapter.changeTab(this.mArticleDetailAdapter.getTabHolder(), 3);
                    return;
                }
                return;
            case R.id.cancel /*2131624291*/:
                this.mShareDialog.dismiss();
                return;
            case R.id.arrayHot /*2131624784*/:
                if (this.mCommentOrderid != 1) {
                    this.mCommentOrderid = 1;
                    this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                    this.mCommentArrayBinding.arrayHot.setTextColor(this.mResources.getColor(R.color.color_ffffff));
                    this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
                    this.mCommentArrayBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_000000));
                    this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
                    this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
                    if (this.mArticleDetailAdapter.getArrayHolder() != null) {
                        this.mArticleDetailAdapter.getArrayHolder().mBinding.arrayText.setText(R.string.comment_hotarray);
                        this.mArticleDetailAdapter.setmCommentOrderid(this.mCommentOrderid);
                    }
                    changeTab(1);
                }
                hideCommentArrayWindow();
                return;
            case R.id.arrayTime /*2131624785*/:
                if (this.mCommentOrderid != 2) {
                    this.mCommentOrderid = 2;
                    this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
                    this.mCommentArrayBinding.arrayHot.setTextColor(this.mResources.getColor(R.color.color_000000));
                    this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                    this.mCommentArrayBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_ffffff));
                    this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
                    this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
                    if (this.mArticleDetailAdapter.getArrayHolder() != null) {
                        this.mArticleDetailAdapter.getArrayHolder().mBinding.arrayText.setText(R.string.comment_timearray);
                        this.mArticleDetailAdapter.setmCommentOrderid(this.mCommentOrderid);
                    }
                    changeTab(1);
                }
                hideCommentArrayWindow();
                return;
            case R.id.array_text /*2131624787*/:
                showArraryDialog(view);
                return;
            case R.id.userImage /*2131624802*/:
                this.mSnsInfo.user.intentToPersonalHome(this);
                return;
            case R.id.friendsShare /*2131624822*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.WEIXIN_CIRCLE, this.umShareListener, 1);
                this.mShareDialog.dismiss();
                return;
            case R.id.weixinShare /*2131624823*/:
                this.mSnsInfo.shareToWeiXin(this, this.mIwxapi);
                this.mShareDialog.dismiss();
                return;
            case R.id.weiboShare /*2131624824*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.SINA, this.umShareListener, 1);
                this.mShareDialog.dismiss();
                return;
            case R.id.qqShare /*2131624825*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.QQ, this.umShareListener, 1);
                this.mShareDialog.dismiss();
                return;
            case R.id.qzoneShare /*2131624826*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.QZONE, this.umShareListener, 1);
                this.mShareDialog.dismiss();
                return;
            case R.id.collectText /*2131624827*/:
                this.mSnsInfo.collectOperation(this, null);
                this.mShareDialog.dismiss();
                return;
            case R.id.copyLink /*2131624828*/:
                this.mSnsInfo.copySnsUrl(this, 1);
                this.mShareDialog.dismiss();
                umengMap = new HashMap();
                umengMap.put("from", getString(R.string.articles));
                MobclickAgent.onEvent(this, "FinalPage_link", umengMap);
                return;
            case R.id.edit /*2131624829*/:
                this.mSnsInfo.editOperation(this);
                this.mShareDialog.dismiss();
                return;
            case R.id.delete /*2131624830*/:
                this.mSnsInfo.deleteOperation(this, null);
                this.mShareDialog.dismiss();
                return;
            case R.id.follow /*2131624832*/:
                this.mSnsInfo.user.followOperation(this, null);
                this.mShareDialog.dismiss();
                umengMap = new HashMap();
                umengMap.put("from", getString(R.string.articles));
                MobclickAgent.onEvent(this, "FinalPage_more_follow", umengMap);
                return;
            case R.id.report /*2131624833*/:
                this.mSnsInfo.reportOperation(this);
                this.mShareDialog.dismiss();
                umengMap = new HashMap();
                umengMap.put("from", getString(R.string.articles));
                MobclickAgent.onEvent(this, "FinalPage_report", umengMap);
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
                umengMap = new HashMap();
                umengMap.put("from", getString(R.string.articles));
                MobclickAgent.onEvent(this, "FinalPage_back", umengMap);
                return;
            case R.id.iv_right_image /*2131625319*/:
                if (this.mSnsInfo.id != 0 && this.mSnsInfo.user.id != 0) {
                    showShareDialog(false);
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void showShareDialog(boolean isForward) {
        if (this.mArticleShareBinding == null) {
            this.mArticleShareBinding = ArticleShareDialogBinding.inflate(LayoutInflater.from(this));
            this.mArticleShareBinding.setUserInfo(this.mSnsInfo.user);
            this.mArticleShareBinding.setMSnsInfo(this.mSnsInfo);
            this.mArticleShareBinding.friendsShare.setOnClickListener(this);
            this.mArticleShareBinding.weixinShare.setOnClickListener(this);
            this.mArticleShareBinding.weiboShare.setOnClickListener(this);
            this.mArticleShareBinding.qqShare.setOnClickListener(this);
            this.mArticleShareBinding.qzoneShare.setOnClickListener(this);
            this.mArticleShareBinding.collectText.setOnClickListener(this);
            this.mArticleShareBinding.copyLink.setOnClickListener(this);
            this.mArticleShareBinding.edit.setOnClickListener(this);
            this.mArticleShareBinding.delete.setOnClickListener(this);
            this.mArticleShareBinding.follow.setOnClickListener(this);
            this.mArticleShareBinding.report.setOnClickListener(this);
            this.mArticleShareBinding.backHome.setOnClickListener(this);
            this.mArticleShareBinding.cancel.setOnClickListener(this);
        }
        if (isForward) {
            this.mArticleShareBinding.placeView.setVisibility(0);
            this.mArticleShareBinding.placeView1.setVisibility(0);
            this.mArticleShareBinding.placeView2.setVisibility(0);
            this.mArticleShareBinding.collectText.setVisibility(8);
            this.mArticleShareBinding.edit.setVisibility(8);
            this.mArticleShareBinding.delete.setVisibility(8);
            this.mArticleShareBinding.follow.setVisibility(8);
            this.mArticleShareBinding.report.setVisibility(8);
            this.mArticleShareBinding.backHome.setVisibility(8);
        } else {
            this.mArticleShareBinding.placeView.setVisibility(8);
            this.mArticleShareBinding.placeView1.setVisibility(8);
            this.mArticleShareBinding.placeView2.setVisibility(8);
            this.mArticleShareBinding.collectText.setVisibility(0);
            this.mArticleShareBinding.backHome.setVisibility(0);
            if (this.mSnsInfo.user.getIsMy() == 1) {
                this.mArticleShareBinding.edit.setVisibility(0);
                if (this.mSnsInfo.isoldthread == 1) {
                    AlphaAnimation alpha = new AlphaAnimation(1.0f, 0.3f);
                    alpha.setDuration(0);
                    alpha.setFillAfter(true);
                    this.mArticleShareBinding.edit.startAnimation(alpha);
                    this.mArticleShareBinding.edit.setClickable(false);
                }
                this.mArticleShareBinding.delete.setVisibility(0);
                this.mArticleShareBinding.follow.setVisibility(8);
                this.mArticleShareBinding.report.setVisibility(8);
            } else {
                this.mArticleShareBinding.edit.setVisibility(8);
                this.mArticleShareBinding.delete.setVisibility(8);
                this.mArticleShareBinding.follow.setVisibility(0);
                this.mArticleShareBinding.report.setVisibility(0);
                if (this.mSnsInfo.user.isfollow.get() == 0) {
                    this.mArticleShareBinding.follow.setText(R.string.follow);
                } else {
                    this.mArticleShareBinding.follow.setText(R.string.cancel_attention);
                }
            }
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mShareDialog.setCanceledOnTouchOutside(true);
            this.mShareDialog.setCancelable(true);
            Window window = this.mShareDialog.getWindow();
            window.setGravity(80);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mArticleShareBinding.getRoot());
            window.setLayout(-1, -2);
            this.followDrawable = getResources().getDrawable(R.drawable.article_follow_selector);
            this.cancelFollowDrawable = getResources().getDrawable(R.drawable.article_follow_cancel_selector);
            this.followDrawable.setBounds(0, 0, this.followDrawable.getMinimumWidth(), this.followDrawable.getMinimumHeight());
            this.cancelFollowDrawable.setBounds(0, 0, this.cancelFollowDrawable.getMinimumWidth(), this.cancelFollowDrawable.getMinimumHeight());
            this.mShareDialog.setOnDismissListener(new OnDismissListener() {
                public void onDismiss(DialogInterface dialog) {
                    ArticleDetailActivity.this.videoPlaying();
                }
            });
        }
        this.mShareDialog.show();
        videoPause();
    }

    public void finish() {
        if (this.mShareDialog != null && this.mShareDialog.isShowing()) {
            this.mShareDialog.dismiss();
            this.mShareDialog = null;
        }
        super.finish();
    }

    private void hideCommentArrayWindow() {
        if (this.mCommentArrayWindow != null && this.mCommentArrayWindow.isShowing()) {
            this.mCommentArrayWindow.dismiss();
        }
    }

    private void showArraryDialog(View view) {
        if (this.mCommentArrayBinding == null) {
            this.mCommentArrayBinding = ArticleCommentArraydialogBinding.inflate(LayoutInflater.from(this));
            this.mCommentArrayBinding.arrayHot.setOnClickListener(this);
            this.mCommentArrayBinding.arrayTime.setOnClickListener(this);
        }
        if (this.mCommentOrderid == 1) {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
        } else {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
        }
        this.mCommentArrayBinding.arrayHot.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mCommentArrayBinding.arrayTime.setPadding(this.m8, this.m8, this.m8, this.m8);
        if (this.mCommentArrayWindow == null) {
            this.mCommentArrayWindow = new PopupWindow(this.mCommentArrayBinding.getRoot(), this.m220, this.m184, true);
        }
        this.mCommentArrayWindow.setFocusable(true);
        this.mCommentArrayWindow.setOutsideTouchable(true);
        this.mCommentArrayWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        int[] loc_int = new int[2];
        view.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.right = loc_int[0] + view.getWidth();
        location.bottom = loc_int[1] + view.getHeight();
        this.mCommentArrayWindow.showAtLocation(view, 53, location.left, location.bottom - 10);
    }

    public void onArrayChanged(View view) {
        showArraryDialog(view);
    }

    public void onCommentSelected() {
        changeTab(1);
    }

    public void onPraiseSelected() {
        changeTab(3);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1111 && resultCode == -1 && data != null) {
            ImageInfo image = (ImageInfo) data.getExtras().get(ShowBigImageActivity.BIGIMAGE_KEY);
            int position = data.getIntExtra("position", -1);
            if (image != null) {
                for (int i = position; i < this.mList.size(); i++) {
                    ArticleInfo articleInfo = (ArticleInfo) this.mList.get(i);
                    if (articleInfo.type == 1 && articleInfo.resources.type == 2 && articleInfo.resources.image.url.equals(image.url)) {
                        final int index = i + 2;
                        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.post(new Runnable() {
                            public void run() {
                                ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.scrollToPosition(index);
                            }
                        });
                        return;
                    }
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoModifyEvent event) {
        if (event.snsInfo != null && event.snsInfo.id == this.mSnsInfo.id) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event.snsInfo != null && event.snsInfo.id == this.mSnsInfo.id) {
            ArticleInfo articleInfo;
            switch (event.refreshType) {
                case 2001:
                    this.mSnsInfo.isfavorite.set(event.snsInfo.isfavorite.get());
                    return;
                case ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE /*2003*/:
                    this.mSnsInfo.commentcount.set(event.snsInfo.commentcount.get());
                    int commentPos;
                    BaseListModel baseListModel;
                    if (this.mType == 1) {
                        if (event.commentInfo.originparentid == 0) {
                            int pos = this.mContentList.size();
                            articleInfo = new ArticleInfo();
                            articleInfo.commentInfo = event.commentInfo;
                            articleInfo.type = 2;
                            this.mList.add(pos, articleInfo);
                            if (this.mCommentOrderid == 1) {
                                this.mHotCommentList.add(0, event.commentInfo);
                            } else {
                                this.mTimeCommentList.add(0, event.commentInfo);
                            }
                        } else if (this.mCommentOrderid == 1) {
                            commentPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mHotCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mHotCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                            }
                        } else {
                            commentPos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mTimeCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mTimeCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                            }
                        }
                        if (this.mIsCommentNeedEmpty || this.mIsCommentEmpty) {
                            ((ArticleInfo) this.mList.get(this.mList.size() - 1)).type = 7;
                            this.mIsCommentChangeEmptyHight = false;
                            this.mCommentEmptyHight = 0;
                            this.mIsCommentEmpty = false;
                            this.mIsCommentNeedEmpty = true;
                        }
                        this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    } else if (!this.loadCommentSuccess) {
                        return;
                    } else {
                        if (event.commentInfo.originparentid == 0) {
                            if (this.mCommentOrderid == 1) {
                                this.mHotCommentList.add(0, event.commentInfo);
                                return;
                            } else {
                                this.mTimeCommentList.add(0, event.commentInfo);
                                return;
                            }
                        } else if (this.mCommentOrderid == 1) {
                            commentPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mHotCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mHotCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                                return;
                            }
                            return;
                        } else {
                            commentPos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (commentPos != -1) {
                                baseListModel = this.mTimeCommentList.get(commentPos).reply;
                                baseListModel.count++;
                                this.mTimeCommentList.get(commentPos).reply.list.add(0, event.commentInfo);
                                return;
                            }
                            return;
                        }
                    }
                case 2004:
                    this.mSnsInfo.praisecount.set(event.snsInfo.praisecount.get());
                    this.mSnsInfo.ispraise.set(event.snsInfo.ispraise.get());
                    if (event.snsInfo.ispraise.get() == 1) {
                        articleInfo = new ArticleInfo();
                        articleInfo.gratuityInfo = event.gratuityRecordInfo;
                        articleInfo.type = 3;
                        if (this.mType == 3) {
                            this.mList.add(this.mContentList.size(), articleInfo);
                            this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            if (this.mIsPraiseNeedEmpty || this.mIsPraiseEmpty) {
                                ((ArticleInfo) this.mList.get(this.mList.size() - 1)).type = 7;
                                this.mIsPraiseChangeEmptyHight = false;
                                this.mPraiseEmptyHight = 0;
                                this.mIsPraiseEmpty = false;
                                this.mIsPraiseNeedEmpty = true;
                            }
                            this.mArticleDetailAdapter.notifyDataSetChanged();
                            return;
                        } else if (this.loadPraiseSuccess) {
                            this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            return;
                        } else {
                            return;
                        }
                    }
                    int praisePos = this.mPraiseList.getPosition(event.gratuityRecordInfo.id);
                    if (praisePos != -1) {
                        this.mPraiseList.remove(praisePos);
                        if (this.mType == 3) {
                            resetEmptyParameter();
                            parseThreadPraise(this.mPraiseList.getPraiseList(), 1);
                            return;
                        }
                        return;
                    }
                    return;
                case m_AppUI.MSG_APP_VERSION_FORCE /*2005*/:
                    if (event.deleterefresh) {
                        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.forceToRefresh();
                        return;
                    } else {
                        finish();
                        return;
                    }
                case m_AppUI.MSG_APP_VERSION_COMMEND /*2006*/:
                    this.mSnsInfo.commentcount.set(event.snsInfo.commentcount.get());
                    int hotPos;
                    int timePos;
                    int index;
                    if (this.mType == 1) {
                        if (event.commentInfo.originparentid == 0) {
                            if (this.mCommentOrderid == 1) {
                                hotPos = this.mHotCommentList.getPosition(event.commentInfo.id);
                                if (hotPos != -1) {
                                    this.mHotCommentList.remove(hotPos);
                                    resetEmptyParameter();
                                    parseThreadComment(this.mHotCommentList.getCommentList(), 1);
                                    return;
                                }
                                return;
                            } else if (this.mCommentOrderid == 2) {
                                timePos = this.mTimeCommentList.getPosition(event.commentInfo.id);
                                if (timePos != -1) {
                                    this.mTimeCommentList.remove(timePos);
                                    resetEmptyParameter();
                                    parseThreadComment(this.mTimeCommentList.getCommentList(), 1);
                                    return;
                                }
                                return;
                            } else {
                                return;
                            }
                        } else if (this.mCommentOrderid == 1) {
                            hotPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                            if (hotPos != -1) {
                                index = this.mHotCommentList.get(hotPos).reply.list.indexOf(event.commentInfo);
                                if (index > -1) {
                                    ((CommentInfo) this.mHotCommentList.get(hotPos).reply.list.get(index)).isdel = 1;
                                    this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                    return;
                                }
                                return;
                            }
                            return;
                        } else if (this.mCommentOrderid == 2) {
                            timePos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                            if (timePos != -1) {
                                index = this.mTimeCommentList.get(timePos).reply.list.indexOf(event.commentInfo);
                                if (index > -1) {
                                    ((CommentInfo) this.mTimeCommentList.get(timePos).reply.list.get(index)).isdel = 1;
                                    this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                    return;
                                }
                                return;
                            }
                            return;
                        } else {
                            return;
                        }
                    } else if (event.commentInfo.originparentid == 0) {
                        hotPos = this.mHotCommentList.getPosition(event.commentid);
                        if (hotPos != -1) {
                            this.mHotCommentList.remove(hotPos);
                        }
                        timePos = this.mTimeCommentList.getPosition(event.commentid);
                        if (timePos != -1) {
                            this.mTimeCommentList.remove(timePos);
                            return;
                        }
                        return;
                    } else {
                        hotPos = this.mHotCommentList.getPosition(event.commentInfo.originparentid);
                        if (hotPos != -1) {
                            index = this.mHotCommentList.get(hotPos).reply.list.indexOf(event.commentInfo);
                            if (index > -1) {
                                ((CommentInfo) this.mHotCommentList.get(hotPos).reply.list.get(index)).isdel = 1;
                            }
                        }
                        timePos = this.mTimeCommentList.getPosition(event.commentInfo.originparentid);
                        if (timePos != -1) {
                            index = this.mTimeCommentList.get(timePos).reply.list.indexOf(event.commentInfo);
                            if (index > -1) {
                                ((CommentInfo) this.mTimeCommentList.get(timePos).reply.list.get(index)).isdel = 1;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                default:
                    return;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event.userInfo != null && event.userInfo.id == this.mSnsInfo.user.id) {
            if (event.type == 2) {
                if (this.mSnsInfo.user.getIsMy() != 1) {
                    this.mSnsInfo.user.isfollow.set(event.userInfo.isfollow.get());
                    this.mArticleHeaderViewBinding.urvFianlpageTop.changeStatus(1);
                    if (event.userInfo.isfollow.get() == 0) {
                        this.mArticleShareBinding.follow.setCompoundDrawables(null, this.followDrawable, null, null);
                        this.mArticleShareBinding.follow.setText(R.string.follow);
                        return;
                    }
                    this.mArticleShareBinding.follow.setCompoundDrawables(null, this.cancelFollowDrawable, null, null);
                    this.mArticleShareBinding.follow.setText(R.string.cancel_attention);
                }
            } else if (event.type == 1) {
                if (!(StringUtil.isEmpty((String) event.userInfo.name.get()) || ((String) this.mSnsInfo.user.name.get()).equals(event.userInfo.name.get()))) {
                    this.mSnsInfo.user.name.set(event.userInfo.name.get());
                }
                if (!StringUtil.isEmpty(event.userInfo.getHeadImageInfo().url) && !this.mSnsInfo.user.getHeadImageInfo().url.equals(event.userInfo.getHeadImageInfo().url)) {
                    this.mSnsInfo.user.getHeadImageInfo().width = event.userInfo.getHeadImageInfo().width;
                    this.mSnsInfo.user.getHeadImageInfo().height = event.userInfo.getHeadImageInfo().height;
                    this.mSnsInfo.user.getHeadImageInfo().url = event.userInfo.getHeadImageInfo().url;
                    this.mSnsInfo.user.getHeadImageInfo().hash = event.userInfo.getHeadImageInfo().hash;
                    this.mSnsInfo.user.getHeadImageInfo().mimetype = event.userInfo.getHeadImageInfo().mimetype;
                    this.mArticleHeaderViewBinding.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
                }
            }
        }
    }

    private void adShowOrGone(boolean isShow) {
        if (!TextUtils.isEmpty(this.mAdvertInfo.showid)) {
            if (isShow) {
                if (!this.mAlreadyExposure) {
                    this.mAlreadyExposure = true;
                    this.mAdvertInfo.adPvHandle(this, true);
                }
            } else if (this.mAlreadyExposure) {
                this.mAlreadyExposure = false;
                this.mAdvertInfo.adPvHandle(this, false);
            }
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.forceToRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    private String calculateReadPercent() {
        double percent = ((double) (((float) this.mLastVisiblePosition) / (((float) this.mContentList.size()) * 1.0f))) * 100.0d;
        if (percent > 100.0d) {
            percent = 100.0d;
        }
        return new DecimalFormat("0.0").format(percent) + "%";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        if (event != null && this.mSnsInfo.hotshow != null && event.id == this.mSnsInfo.hotshow.id) {
            this.mSnsInfo.hotshow.isfollow.set(event.isFollow);
            this.mSnsInfo.hotshow.isremind.set(event.isRemind);
            this.mArticleDetailAdapter.getRecommendView().setSnsInfo(this.mSnsInfo, this.mSwipeBackLayout);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CommentPageRefreshEvent event) {
        if (event != null && event.isTop) {
            int position = this.mHotCommentList.getPosition(event.commentId);
            if (position != -1) {
                CommentInfo commentInfo = this.mHotCommentList.get(position);
                commentInfo.istop.set(1);
                commentInfo.top.set(event.topNum);
                position = this.mTimeCommentList.getPosition(event.commentId);
                if (position != -1) {
                    commentInfo = this.mTimeCommentList.get(position);
                    commentInfo.istop.set(1);
                    commentInfo.top.set(event.topNum);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && !TextUtils.isEmpty(event.key) && this.mArticleDetailAdapter != null && this.mArticleDetailAdapter.getSoleKey().equals(event.key)) {
            if (LogGatherReadUtil.getInstance().getCurrentPage().equals("app_send_comment") && event.isFinish) {
                ArticleInfo articleInfo = (ArticleInfo) this.mList.get(this.mList.size() - 1);
                if (articleInfo.isextend) {
                    this.mList.remove(articleInfo);
                    this.mArticleDetailAdapter.notifyDataSetChanged();
                }
            }
            ((ActivityArticleDetailBinding) this.mBaseBinding).recyclerView.postDelayed(new Runnable() {
                public void run() {
                    ((ActivityArticleDetailBinding) ArticleDetailActivity.this.mBaseBinding).recyclerView.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }

    public String getLogCurrentPage() {
        return "app_thread?snsid=" + this.mSnsId + "&resourceid=" + this.mResourceId + "&resourcetype=" + this.mResourceType + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
    }
}
