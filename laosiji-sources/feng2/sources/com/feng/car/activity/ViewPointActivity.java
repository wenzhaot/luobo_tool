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
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.widget.PopupWindow;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.ArticleDetailAdapter;
import com.feng.car.adapter.ArticleDetailAdapter.ArticleContentHolder;
import com.feng.car.adapter.ArticleDetailAdapter.InteractionChangeListener;
import com.feng.car.databinding.ActivityViewpointBinding;
import com.feng.car.databinding.ArticleCommentArraydialogBinding;
import com.feng.car.databinding.ArticleShareDialogBinding;
import com.feng.car.databinding.ViewpointHeaderLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.ImageInfo;
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
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.event.UserPointRefreshEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.listener.FengUMShareListener;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.view.VoiceBoxView;
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
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class ViewPointActivity extends VideoBaseActivity<ActivityViewpointBinding> implements InteractionChangeListener {
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
    private ArticleDetailAdapter mArticleDetailAdapter;
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
    private ViewpointHeaderLayoutBinding mHeaderBinding;
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
    private ArticleInfo tabArticleInfo;
    private FengUMShareListener umShareListener;

    static {
        StubApp.interface11(3249);
    }

    protected native void onCreate(Bundle bundle);

    public int getSnsId() {
        return this.mSnsId;
    }

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

    public void getLocalIntentData() {
        this.mSnsId = getIntent().getIntExtra("snsid", 0);
        this.mResourceId = getIntent().getIntExtra("resourceid", 0);
        this.mResourceType = getIntent().getIntExtra("resourcetype", 0);
        this.mFirstLocation = getIntent().getIntExtra("location_type", -1);
        this.mCommentId = getIntent().getIntExtra("commentid", -1);
    }

    public int setBaseContentView() {
        return R.layout.activity_viewpoint;
    }

    public void initView() {
        initNormalTitleBar("");
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m184 = this.mResources.getDimensionPixelSize(R.dimen.default_184PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.mRootBinding.titleLine.ivRightImage.setBackgroundResource(R.drawable.icon_more_bl_selector);
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
        this.mRootBinding.titleLine.ivRightImage.setOnClickListener(this);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.scrollToPosition(0);
            }
        }));
        this.mHeaderBinding = ViewpointHeaderLayoutBinding.inflate(LayoutInflater.from(this));
        this.mArticleDetailAdapter = new ArticleDetailAdapter(this, this.mList);
        this.mArticleDetailAdapter.setInteractionChangeListener(this);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mArticleDetailAdapter);
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setRefreshProgressStyle(2);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        this.mHeaderBinding.getRoot().setLayoutParams(new LayoutParams(-1, -2));
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, State.Normal);
                ViewPointActivity.this.resetCurrentPage();
                ViewPointActivity.this.mLastOffset = 0;
                ViewPointActivity.this.updateViewPosition(-1, 0);
                ViewPointActivity.this.loadData();
            }
        });
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView) && !ViewPointActivity.this.mIsRefresh) {
                    if (ViewPointActivity.this.getCurrentPage() > ViewPointActivity.this.getTotalPage()) {
                        RecyclerViewStateUtils.setFooterViewState(ViewPointActivity.this, ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(ViewPointActivity.this, ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                    ViewPointActivity.this.loadBottomData(0, false);
                }
            }
        });
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setLScrollListener(new LRecyclerView$LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
            }

            public void onScrolled(int distanceX, int distanceY) {
                if (ViewPointActivity.this.mValidRecyclerViewHight == 0) {
                    ViewPointActivity.this.mValidRecyclerViewHight = ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).bottomLine.getTop();
                }
                LinearLayoutManager lm = (LinearLayoutManager) ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                int firstVisiblePosition = lm.findFirstVisibleItemPosition();
                int lastVisiblePosition = lm.findLastVisibleItemPosition();
                ViewPointActivity.this.mFirstVisiblePosition = firstVisiblePosition;
                ViewPointActivity.this.mLastVisiblePosition = lastVisiblePosition;
                View view = lm.getChildAt(0);
                if (view != null) {
                    int[] locations = new int[2];
                    view.getLocationInWindow(locations);
                    ViewPointActivity.this.mOffsetYOfVisibleOne = locations[1];
                    ArticleContentHolder holder = ViewPointActivity.this.mArticleDetailAdapter.getTabHolder();
                    if (holder != null) {
                        int[] location = new int[2];
                        holder.mBinding.choiceLine.getLocationOnScreen(location);
                        int position = ViewPointActivity.this.mArticleDetailAdapter.getTabPosition();
                        if (location[1] >= ViewPointActivity.this.getTitleBarBottomY() && position >= firstVisiblePosition - 1) {
                            ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).topLine.setVisibility(8);
                            ViewPointActivity.this.mIsShowTopBar = false;
                        } else if (position >= firstVisiblePosition - 1) {
                            ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).topLine.setVisibility(8);
                            ViewPointActivity.this.mIsShowTopBar = false;
                        } else {
                            ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).topLine.setVisibility(0);
                            ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).topLine.getBackground().mutate().setAlpha(255);
                            ViewPointActivity.this.mIsShowTopBar = true;
                        }
                    }
                    ViewPointActivity.this.setEmptyHight(lm);
                }
            }

            public void onScrollStateChanged(int state) {
                if (state == 0) {
                    if (ViewPointActivity.this.mArticleDetailAdapter.getTabPosition() != 0) {
                        ArticleContentHolder holder = ViewPointActivity.this.mArticleDetailAdapter.getTabHolder();
                        if (holder != null) {
                            ViewPointActivity.this.updateViewPosition(ViewPointActivity.this.mArticleDetailAdapter.getTabPosition() + 2, holder.mBinding.getRoot().getTop());
                        }
                    }
                    if (ViewPointActivity.this.mLastVisiblePosition <= ViewPointActivity.this.mContentList.size() + 2 && ViewPointActivity.this.mLastVisiblePosition > 0) {
                        ViewPointActivity.this.mLogGatherInfo.addPercentageLog(ViewPointActivity.this.mSnsId, ViewPointActivity.this.mResourceId, ViewPointActivity.this.mResourceType, 0, ViewPointActivity.this.calculateReadPercent(), "");
                    }
                }
            }
        });
        this.mHeaderBinding.userImage.setOnClickListener(this);
        this.mHeaderBinding.titleLine.setOnClickListener(this);
        ((ActivityViewpointBinding) this.mBaseBinding).praiseLine.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).praiseLine.setEnabled(false);
                ViewPointActivity.this.mSnsInfo.praiseOperation(ViewPointActivity.this, false, new SuccessFailCallback() {
                    public void onSuccess() {
                        ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }

                    public void onFail() {
                        ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).praiseLine.setEnabled(true);
                    }
                });
            }
        });
        ((ActivityViewpointBinding) this.mBaseBinding).tvSendMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ViewPointActivity.this.mSnsInfo.intentToSendComment(ViewPointActivity.this);
            }
        });
        ((ActivityViewpointBinding) this.mBaseBinding).commentText.setOnClickListener(this);
        ((ActivityViewpointBinding) this.mBaseBinding).praiseText.setOnClickListener(this);
        ((ActivityViewpointBinding) this.mBaseBinding).ivForward.setOnClickListener(this);
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
        if (this.mSnsInfo.snstype == 9) {
            this.mSnsInfo.publishtime.set(this.mSnsInfo.discussinfo.publishtime);
        }
        this.mHeaderBinding.setMSnsInfo(this.mSnsInfo);
        this.mHeaderBinding.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
        if (this.mSnsInfo.user.getIsMy() == 1) {
            this.mHeaderBinding.urvFianlpageTop.setVisibility(8);
            this.mHeaderBinding.llExposureRead.setVisibility(0);
            this.mHeaderBinding.tvExposureNum.setText(FengUtil.numberFormat(this.mSnsInfo.exposurecount.get()));
            this.mHeaderBinding.tvReadNum.setText(FengUtil.numberFormat(this.mSnsInfo.cilckcount.get()));
        } else {
            this.mHeaderBinding.urvFianlpageTop.setUserInfo(this.mSnsInfo.user);
            this.mHeaderBinding.llExposureRead.setVisibility(8);
        }
        initNormalTitleBar(this.mSnsInfo.discussinfo.title);
        this.mHeaderBinding.title.setText(this.mSnsInfo.discussinfo.title);
        this.mHeaderBinding.detail.setText(this.mSnsInfo.discussinfo.publishtime + "    讨论" + FengUtil.numberFormat(this.mSnsInfo.commentcount.get()));
        ((ActivityViewpointBinding) this.mBaseBinding).setMSnsInfo(this.mSnsInfo);
        this.mArticleDetailAdapter.setmSnsInfo(this.mSnsInfo);
        this.mArticleDetailAdapter.setmCommentId(this.mCommentId);
        ((ActivityViewpointBinding) this.mBaseBinding).rlComment.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ViewPointActivity.this.mSnsInfo.commentcount.get() > 0) {
                    LinearLayoutManager lm = (LinearLayoutManager) ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.getLayoutManager();
                    int lastPosition = lm.findFirstVisibleItemPosition();
                    int[] locations = new int[2];
                    View view = lm.getChildAt(0);
                    if (view != null) {
                        view.getLocationInWindow(locations);
                        if (ViewPointActivity.this.mLastOffset != 0) {
                            if (lastPosition < ViewPointActivity.this.mContentList.size() + 1 && ViewPointActivity.this.mLastPosition < ViewPointActivity.this.mContentList.size() + 1) {
                                ViewPointActivity.this.mLastPosition = ViewPointActivity.this.mCurrentPosition;
                                ViewPointActivity.this.mLastOffset = ViewPointActivity.this.mCurrentOffset;
                            }
                            lm.scrollToPositionWithOffset(ViewPointActivity.this.mLastPosition, ViewPointActivity.this.mLastOffset - (ViewPointActivity.this.getResources().getDimensionPixelSize(R.dimen.default_88PX) + FengApplication.getInstance().getStatusBarHeight()));
                            ViewPointActivity.this.mCurrentPosition = ViewPointActivity.this.mLastPosition;
                            ViewPointActivity.this.mCurrentOffset = ViewPointActivity.this.mLastOffset;
                        } else if (ViewPointActivity.this.mIsShowTopBar) {
                            lm.scrollToPositionWithOffset(0, 0);
                        } else {
                            lm.scrollToPositionWithOffset(ViewPointActivity.this.mContentList.size() + 1, 0);
                        }
                        ViewPointActivity.this.mLastOffset = locations[1];
                        ViewPointActivity.this.mLastPosition = lastPosition;
                        return;
                    }
                    return;
                }
                ViewPointActivity.this.mSnsInfo.intentToSendComment(ViewPointActivity.this);
            }
        });
        if (this.mResourceType == 9) {
            List<SnsPostResources> mThreadInfoList = new ArrayList();
            mThreadInfoList.addAll(this.mSnsInfo.list);
            if (mThreadInfoList.size() > 0) {
                ArticleInfo articleInfo;
                this.mList.clear();
                resetEmptyParameter();
                this.mContentList.clear();
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
                this.mTabBarPos = this.mContentList.size();
                articleInfo = new ArticleInfo();
                articleInfo.type = 5;
                this.mContentList.add(articleInfo);
                this.mList.addAll(this.mContentList);
            }
            this.mHeaderBinding.text.setVisibility(8);
            this.mHeaderBinding.vbvViewPoint.setVisibility(0);
            this.mHeaderBinding.vbvViewPoint.initVoiceBox(this.mSnsInfo, VoiceBoxView.VBV_SHOW_AUTHOR);
            this.mLRecyclerViewAdapter.notifyDataSetChanged();
            this.mArticleDetailAdapter.setmImageList(this.mImageList);
            this.mHeaderBinding.recommendView.setVisibility(8);
        }
    }

    private void parseThreadComment(List<CommentInfo> list, int locationType) {
        if (locationType == 1) {
            this.mList.clear();
            if (this.mResourceType == 10) {
                this.mList.add(this.tabArticleInfo);
            }
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
        LinearLayoutManager lm;
        if (this.mFirstLocation != -1) {
            lm = (LinearLayoutManager) ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            if (this.mResourceType == 9) {
                lm.scrollToPositionWithOffset(this.mContentList.size() + 1, 0);
            } else {
                lm.scrollToPositionWithOffset(2, 0);
            }
        } else if (locationType == 1) {
            lm = (LinearLayoutManager) ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            if (((ActivityViewpointBinding) this.mBaseBinding).topLine.isShown()) {
                lm.scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
            } else {
                lm.scrollToPositionWithOffset(this.mPosition, this.mOffset);
            }
        }
        if (!((ActivityViewpointBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setVisibility(0);
            if (this.mResourceType == 9) {
                initScroll();
            } else {
                this.mFirstLocation = -1;
            }
        }
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
                ViewPointActivity.this.hideProgress();
                if (ViewPointActivity.this.mList.size() > 0) {
                    ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    ViewPointActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ViewPointActivity.this.loadData();
                        }
                    });
                }
            }

            public void onStart() {
                ViewPointActivity.this.mIsRefresh = true;
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, State.Normal);
                ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.refreshComplete();
                if (ViewPointActivity.this.loadCommentSuccess || ViewPointActivity.this.loadPraiseSuccess) {
                    ViewPointActivity.this.hideProgress();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ViewPointActivity.this.mList.size() > 0) {
                    ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    return;
                }
                ViewPointActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ViewPointActivity.this.loadData();
                    }
                });
                ViewPointActivity.this.mRootBinding.titleLine.titlebarRightParent.setVisibility(8);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        ViewPointActivity.this.mSnsInfo.parser(jsonObject.getJSONObject("body").getJSONObject("sns"));
                        if (ViewPointActivity.this.mSnsId == 0) {
                            ViewPointActivity.this.mSnsId = ViewPointActivity.this.mSnsInfo.id;
                        }
                        if (ViewPointActivity.this.mSnsInfo.isflag == 0) {
                            ViewPointActivity.this.hideEmptyView();
                            if (ViewPointActivity.this.mSnsInfo.discussinfo.id <= 0) {
                                ViewPointActivity.this.showEmptyView((int) R.string.choose_car_discuss_not_exist_tips, (int) R.drawable.icon_article_delete);
                                return;
                            }
                            ViewPointActivity.this.parseThread();
                            if (ViewPointActivity.this.mFirstLocation != -1) {
                                if (ViewPointActivity.this.mFirstLocation == 1) {
                                    ViewPointActivity.this.mType = 1;
                                } else if (ViewPointActivity.this.mFirstLocation == 3) {
                                    ViewPointActivity.this.mType = 3;
                                }
                                ViewPointActivity.this.changeTabColor(ViewPointActivity.this.mType);
                                ViewPointActivity.this.loadBottomData(1, false);
                                ViewPointActivity.this.mArticleDetailAdapter.setType(ViewPointActivity.this.mType);
                                return;
                            }
                            ViewPointActivity.this.changeTabColor(ViewPointActivity.this.mType);
                            ViewPointActivity.this.loadBottomData(0, false);
                        } else if (ViewPointActivity.this.mSnsInfo.isflag == 1) {
                            ViewPointActivity.this.showEmptyView((int) R.string.viewpoint_delete, (int) R.drawable.icon_article_delete);
                        } else if (ViewPointActivity.this.mSnsInfo.isflag == -1 || ViewPointActivity.this.mSnsInfo.isflag == -2 || ViewPointActivity.this.mSnsInfo.isflag == -3) {
                            ViewPointActivity.this.showEmptyView((int) R.string.viewpoint_edit, (int) R.drawable.icon_article_modify);
                        } else {
                            ViewPointActivity.this.showEmptyView((int) R.string.viewpoint_delete, (int) R.drawable.icon_article_delete);
                        }
                    } else if (code == -48 || code == -50 || code == -75) {
                        ViewPointActivity.this.showEmptyView((int) R.string.viewpoint_edit, (int) R.drawable.icon_article_delete);
                    } else if (code == -49 || code == -51 || code == -52) {
                        ViewPointActivity.this.showEmptyView((int) R.string.viewpoint_edit, (int) R.drawable.icon_article_delete);
                    } else {
                        if (ViewPointActivity.this.mList.size() > 0) {
                            ViewPointActivity.this.showThirdTypeToast((int) R.string.check_network);
                        } else {
                            ViewPointActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    ViewPointActivity.this.loadData();
                                }
                            });
                        }
                        FengApplication.getInstance().upLoadLog(true, "sns/info/-----错误码 = " + code);
                    }
                } catch (Exception e) {
                    if (ViewPointActivity.this.mList.size() > 0) {
                        ViewPointActivity.this.showThirdTypeToast((int) R.string.check_network);
                    } else {
                        ViewPointActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ViewPointActivity.this.loadData();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("sns/info/", content, e);
                }
            }
        });
    }

    private void initScroll() {
        if (this.mResourceType == 9) {
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() {
                    ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (ViewPointActivity.this.mFirstLocation == -1) {
                        SnsReadRecord snsReadRecord = ViewPointActivity.this.mSnsReadRecordList.getReadRecord(ViewPointActivity.this.mSnsId);
                        if (snsReadRecord != null && snsReadRecord.firstVisiblePosition > 0) {
                            ((LinearLayoutManager) ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(snsReadRecord.firstVisiblePosition, snsReadRecord.offset - (ViewPointActivity.this.getResources().getDimensionPixelSize(R.dimen.default_88PX) + FengApplication.getInstance().getStatusBarHeight()));
                            ViewPointActivity.this.mFirstLocation = -1;
                        }
                    }
                }
            });
        }
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
                ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                ViewPointActivity.this.hideProgress();
            }

            public void onStart() {
                if (!ViewPointActivity.this.loadCommentSuccess) {
                    ViewPointActivity.this.showProgress("", ViewPointActivity.this.getString(R.string.loading));
                }
            }

            public void onFinish() {
                ViewPointActivity.this.mIsRefresh = false;
                RecyclerViewStateUtils.setFooterViewState(((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, State.Normal);
                ViewPointActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ViewPointActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ViewPointActivity.this.getCurrentPage() == 1) {
                    ViewPointActivity.this.mIsCommentEmpty = true;
                    ViewPointActivity.this.parseThreadComment(null, locationType);
                }
                ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!ViewPointActivity.this.loadCommentSuccess) {
                            ViewPointActivity.this.loadCommentSuccess = true;
                        }
                        JSONObject commentJson = resultJson.getJSONObject("body").getJSONObject("comment");
                        BaseListModel<CommentInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CommentInfo.class, commentJson);
                        ViewPointActivity.this.setTotalPage(baseListModel.pagecount);
                        if (ViewPointActivity.this.mResourceType == 10 && ViewPointActivity.this.getCurrentPage() == 1) {
                            ViewPointActivity.this.mList.clear();
                            ViewPointActivity.this.mList.add(ViewPointActivity.this.tabArticleInfo);
                        }
                        List<CommentInfo> list = baseListModel.list;
                        if (list.size() > 0) {
                            ViewPointActivity.this.mIsCommentEmpty = false;
                            if (ViewPointActivity.this.mCommentOrderid == 1) {
                                if (ViewPointActivity.this.getCurrentPage() == 1) {
                                    ViewPointActivity.this.mHotCommentList.clear();
                                }
                                ViewPointActivity.this.mHotCommentList.addAll(list);
                            } else if (ViewPointActivity.this.mCommentOrderid == 2) {
                                if (ViewPointActivity.this.getCurrentPage() == 1) {
                                    ViewPointActivity.this.mTimeCommentList.clear();
                                }
                                ViewPointActivity.this.mTimeCommentList.addAll(list);
                            }
                            ViewPointActivity.this.updateCurrentPage();
                            ViewPointActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else if (ViewPointActivity.this.getCurrentPage() == 1) {
                            ViewPointActivity.this.mIsCommentEmpty = true;
                            ViewPointActivity.this.parseThreadComment(list, locationType);
                            return;
                        } else {
                            return;
                        }
                    }
                    FengApplication.getInstance().checkCode("comment/getlist/", code);
                } catch (Exception e) {
                    ViewPointActivity.this.showSecondTypeToast((int) R.string.analysis_commend_fail);
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
                ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                ViewPointActivity.this.hideProgress();
            }

            public void onStart() {
                if (!ViewPointActivity.this.loadPraiseSuccess) {
                    ViewPointActivity.this.showProgress("", ViewPointActivity.this.getString(R.string.loading));
                }
            }

            public void onFinish() {
                ViewPointActivity.this.mIsRefresh = false;
                RecyclerViewStateUtils.setFooterViewState(((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView, State.Normal);
                ViewPointActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                ViewPointActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ViewPointActivity.this.getCurrentPage() == 1) {
                    ViewPointActivity.this.mIsPraiseEmpty = true;
                    ViewPointActivity.this.parseThreadPraise(null, locationType);
                }
                ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        if (!ViewPointActivity.this.loadPraiseSuccess) {
                            ViewPointActivity.this.loadPraiseSuccess = true;
                        }
                        JSONObject userJson = resultJson.getJSONObject("body").getJSONObject("praise");
                        BaseListModel<GratuityRecordInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(GratuityRecordInfo.class, userJson);
                        ViewPointActivity.this.setTotalPage(baseListModel.pagecount);
                        if (ViewPointActivity.this.mResourceType == 10 && ViewPointActivity.this.getCurrentPage() == 1) {
                            ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.setNoMore(false);
                            ViewPointActivity.this.mList.clear();
                            ViewPointActivity.this.mList.add(ViewPointActivity.this.tabArticleInfo);
                        }
                        List<GratuityRecordInfo> list = baseListModel.list;
                        GratuityRecordInfo gratuityInfo;
                        if (list.size() > 0) {
                            ViewPointActivity.this.mIsPraiseEmpty = false;
                            if (ViewPointActivity.this.getCurrentPage() == 1) {
                                ViewPointActivity.this.mPraiseList.clear();
                            }
                            ViewPointActivity.this.mPraiseList.addAll(list);
                            if (ViewPointActivity.this.getCurrentPage() == ViewPointActivity.this.getTotalPage() && ViewPointActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                                gratuityInfo = new GratuityRecordInfo();
                                gratuityInfo.local_praise_num = ViewPointActivity.this.mSnsInfo.anonymouspraisenum;
                                ViewPointActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                                list.add(gratuityInfo);
                            }
                            ViewPointActivity.this.updateCurrentPage();
                            ViewPointActivity.this.parseThreadPraise(list, locationType);
                            return;
                        }
                        if (ViewPointActivity.this.getCurrentPage() == ViewPointActivity.this.getTotalPage() && ViewPointActivity.this.mSnsInfo.anonymouspraisenum > 0) {
                            gratuityInfo = new GratuityRecordInfo();
                            gratuityInfo.local_praise_num = ViewPointActivity.this.mSnsInfo.anonymouspraisenum;
                            ViewPointActivity.this.mPraiseList.getPraiseList().add(gratuityInfo);
                            list.add(gratuityInfo);
                        }
                        if (list.size() > 0) {
                            ViewPointActivity.this.parseThreadPraise(list, locationType);
                            return;
                        } else if (ViewPointActivity.this.getCurrentPage() == 1) {
                            ViewPointActivity.this.mIsPraiseEmpty = true;
                            ViewPointActivity.this.parseThreadPraise(list, locationType);
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
            if (this.mResourceType == 10) {
                this.mList.add(this.tabArticleInfo);
            }
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
        if (locationType == 1) {
            LinearLayoutManager lm = (LinearLayoutManager) ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            if (((ActivityViewpointBinding) this.mBaseBinding).topLine.isShown()) {
                lm.scrollToPositionWithOffset(this.mPosition, isEmpty ? 0 : this.mOffset);
            } else {
                lm.scrollToPositionWithOffset(this.mPosition, this.mOffset);
            }
        }
        if (!((ActivityViewpointBinding) this.mBaseBinding).recyclerView.isShown()) {
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setVisibility(0);
            if (this.mResourceType == 9) {
                initScroll();
            } else {
                this.mFirstLocation = -1;
            }
        }
    }

    private void changeTabColor(int type) {
        if (type == 1) {
            ((ActivityViewpointBinding) this.mBaseBinding).commentText.setSelected(true);
            ((ActivityViewpointBinding) this.mBaseBinding).praiseText.setSelected(false);
        } else if (type == 3) {
            ((ActivityViewpointBinding) this.mBaseBinding).commentText.setSelected(false);
            ((ActivityViewpointBinding) this.mBaseBinding).praiseText.setSelected(true);
        }
    }

    private void changeTab(int type) {
        this.mType = type;
        if (getCurrentPage() <= getTotalPage()) {
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setNoMore(false);
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

    private void loadBottomData(final int locationType, boolean isShowLoad) {
        if (isShowLoad) {
            showProgress("", getString(R.string.loading));
        }
        if (this.mType == 1) {
            if (getCurrentPage() == 1) {
                this.mSnsInfo.checkSnsState(this, true, new SuccessFailCallback() {
                    public void onSuccess() {
                        super.onSuccess();
                        ViewPointActivity.this.loadCommentData(locationType);
                    }

                    public void onFail() {
                        super.onFail();
                        ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                });
            } else {
                loadCommentData(locationType);
            }
        } else if (this.mType != 3) {
        } else {
            if (this.mPraiseList.size() <= 0) {
                this.mSnsInfo.checkSnsState(this, true, new SuccessFailCallback() {
                    public void onSuccess() {
                        super.onSuccess();
                        ViewPointActivity.this.loadPraiseData(locationType);
                    }

                    public void onFail() {
                        super.onFail();
                        ViewPointActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                });
            } else {
                loadPraiseData(locationType);
            }
        }
    }

    private void loadBottomData(int locationType) {
        loadBottomData(locationType, false);
    }

    public void onSingleClick(View view) {
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
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.WEIXIN_CIRCLE, this.umShareListener, 4);
                this.mShareDialog.dismiss();
                return;
            case R.id.weixinShare /*2131624823*/:
                this.mSnsInfo.shareToWeiXin(this, this.mIwxapi);
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.WEIXIN, this.umShareListener, 4);
                this.mShareDialog.dismiss();
                return;
            case R.id.weiboShare /*2131624824*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.SINA, this.umShareListener, 4);
                this.mShareDialog.dismiss();
                return;
            case R.id.qqShare /*2131624825*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.QQ, this.umShareListener, 4);
                this.mShareDialog.dismiss();
                return;
            case R.id.qzoneShare /*2131624826*/:
                this.mSnsInfo.socialShare(this, SHARE_MEDIA.QZONE, this.umShareListener, 4);
                this.mShareDialog.dismiss();
                return;
            case R.id.collectText /*2131624827*/:
                this.mSnsInfo.collectOperation(this, null);
                this.mShareDialog.dismiss();
                return;
            case R.id.copyLink /*2131624828*/:
                this.mSnsInfo.copySnsUrl(this, 1);
                this.mShareDialog.dismiss();
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
                return;
            case R.id.report /*2131624833*/:
                this.mSnsInfo.reportOperation(this);
                this.mShareDialog.dismiss();
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
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
            this.mArticleShareBinding.copyLink.setOnClickListener(this);
            this.mArticleShareBinding.edit.setOnClickListener(this);
            this.mArticleShareBinding.delete.setOnClickListener(this);
            this.mArticleShareBinding.follow.setOnClickListener(this);
            this.mArticleShareBinding.report.setOnClickListener(this);
            this.mArticleShareBinding.backHome.setOnClickListener(this);
            this.mArticleShareBinding.cancel.setOnClickListener(this);
            this.mArticleShareBinding.collectText.setVisibility(8);
            this.mArticleShareBinding.edit.setVisibility(8);
        }
        if (isForward) {
            this.mArticleShareBinding.placeView.setVisibility(0);
            this.mArticleShareBinding.placeView1.setVisibility(0);
            this.mArticleShareBinding.placeView2.setVisibility(0);
            this.mArticleShareBinding.delete.setVisibility(4);
            this.mArticleShareBinding.follow.setVisibility(8);
            this.mArticleShareBinding.report.setVisibility(8);
            this.mArticleShareBinding.backHome.setVisibility(8);
        } else {
            this.mArticleShareBinding.placeView.setVisibility(8);
            this.mArticleShareBinding.placeView1.setVisibility(8);
            this.mArticleShareBinding.placeView2.setVisibility(8);
            this.mArticleShareBinding.backHome.setVisibility(0);
            if (this.mResourceType == 9) {
                if (this.mSnsInfo.user.getIsMy() == 1) {
                    this.mArticleShareBinding.follow.setVisibility(8);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(8);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                    this.mArticleShareBinding.placeView1.setVisibility(0);
                    this.mArticleShareBinding.placeView2.setVisibility(0);
                } else {
                    this.mArticleShareBinding.follow.setVisibility(0);
                    this.mArticleShareBinding.delete.setVisibility(8);
                    this.mArticleShareBinding.report.setVisibility(8);
                    this.mArticleShareBinding.placeView.setVisibility(0);
                    this.mArticleShareBinding.placeView1.setVisibility(0);
                    if (this.mSnsInfo.user.isfollow.get() == 0) {
                        this.mArticleShareBinding.follow.setText(R.string.follow);
                    } else {
                        this.mArticleShareBinding.follow.setText(R.string.cancel_attention);
                    }
                }
            } else if (this.mSnsInfo.user.getIsMy() == 1) {
                this.mArticleShareBinding.follow.setVisibility(8);
                this.mArticleShareBinding.delete.setVisibility(0);
                this.mArticleShareBinding.report.setVisibility(8);
                this.mArticleShareBinding.placeView.setVisibility(0);
                this.mArticleShareBinding.placeView1.setVisibility(0);
            } else {
                this.mArticleShareBinding.follow.setVisibility(0);
                this.mArticleShareBinding.delete.setVisibility(8);
                this.mArticleShareBinding.report.setVisibility(0);
                this.mArticleShareBinding.placeView.setVisibility(0);
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
                    ViewPointActivity.this.videoPlaying();
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
                        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.post(new Runnable() {
                            public void run() {
                                ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.scrollToPosition(index);
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
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.forceToRefresh();
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
                            if (this.mResourceType == 9) {
                                this.mList.add(pos, articleInfo);
                            } else if (this.mResourceType == 10) {
                                this.mList.add(1, articleInfo);
                            }
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
                            if (this.mResourceType == 9) {
                                this.mList.add(this.mContentList.size(), articleInfo);
                                this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            } else if (this.mResourceType == 10) {
                                this.mList.add(1, articleInfo);
                                this.mPraiseList.add(0, articleInfo.gratuityInfo);
                            }
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
                        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.forceToRefresh();
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
                    this.mHeaderBinding.urvFianlpageTop.changeStatus(1);
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
                    this.mHeaderBinding.userImage.setHeadUrl(FengUtil.getHeadImageUrl(this.mSnsInfo.user.getHeadImageInfo()));
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserPointRefreshEvent event) {
        if (event.snsInfo != null && event.snsInfo.discussinfo.id == this.mSnsInfo.discussinfo.id && event.snsInfo.snstype == 10) {
            finish();
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.forceToRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation && this.mResourceType == 9) {
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.setIsScrollDown(true);
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

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    private String calculateReadPercent() {
        double percent = ((double) (((float) this.mLastVisiblePosition) / (((float) this.mContentList.size()) * 1.0f))) * 100.0d;
        return new DecimalFormat("0.0").format(percent) + "%";
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && !TextUtils.isEmpty(event.key) && this.mArticleDetailAdapter != null && this.mArticleDetailAdapter.getSoleKey().equals(event.key)) {
            LinearLayoutManager manager = (LinearLayoutManager) ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.getLayoutManager();
            int lastItemPosition = manager.findLastVisibleItemPosition();
            if (lastItemPosition >= manager.getItemCount() - 3) {
                int firstItemPosition = manager.findFirstVisibleItemPosition();
                int childSize = (lastItemPosition - firstItemPosition) + 1;
                if (lastItemPosition == firstItemPosition && lastItemPosition == 1 && manager.getChildCount() >= 2) {
                    childSize = 2;
                }
                View view = manager.getChildAt(childSize - 1);
                if (view != null) {
                    if (!LogGatherReadUtil.getInstance().getCurrentPage().equals("app_send_comment")) {
                        view.setPadding(0, 0, 0, 1920);
                    } else if (event.isFinish) {
                        view.setPadding(0, 0, 0, 0);
                    } else {
                        view.setPadding(0, 0, 0, 1920);
                    }
                }
            }
            ((ActivityViewpointBinding) this.mBaseBinding).recyclerView.postDelayed(new Runnable() {
                public void run() {
                    ((ActivityViewpointBinding) ViewPointActivity.this.mBaseBinding).recyclerView.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }

    public String getLogCurrentPage() {
        if (this.mResourceType == 9) {
            return "app_olddirverview?snsid=" + this.mSnsId + "&resourceid=" + this.mResourceId + "&resourcetype=" + this.mResourceType + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
        }
        return "app_userview?snsid=" + this.mSnsId + "&resourceid=" + this.mResourceId + "&resourcetype=" + this.mResourceType + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
    }
}
