package com.feng.car.activity;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.PopupWindow;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommentReplyAdapter;
import com.feng.car.databinding.ActivityCommentDetailBinding;
import com.feng.car.databinding.ArticleCommentArraydialogBinding;
import com.feng.car.databinding.CommentDetailHeaderLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.comment.CommentInfoList;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.operation.SuccessFailCallback;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class CommentReplyListActivity extends BaseActivity<ActivityCommentDetailBinding> {
    public static final String COMMENT_SELECT_ID = "comment_select_id";
    public static final int TYPE_FROM_FINAL_POST = 0;
    public static final int TYPE_FROM_MINE_COMMENT = 1;
    private final int HOT_COMMENT_ARRAY = 1;
    private final int TIME_COMMENT_ARRAY = 2;
    private int m184;
    private int m220;
    private int m8;
    private CommentReplyAdapter mAdapter;
    private ArticleCommentArraydialogBinding mCommentArrayBinding;
    private PopupWindow mCommentArrayWindow;
    private int mCommentID;
    private int mCommentOrderid = 2;
    private CommentDetailHeaderLayoutBinding mHeaderBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CommentInfoList mList = new CommentInfoList(false);
    private int mPage = 1;
    private int mReplyCommentCount = 0;
    private int mSelectCommentID;
    private int mSnsID;
    private int mTotalPage = 0;
    private int mType = 0;

    public int setBaseContentView() {
        return R.layout.activity_comment_detail;
    }

    public void initView() {
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m184 = this.mResources.getDimensionPixelSize(R.dimen.default_184PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        Intent intent = getIntent();
        if (intent != null) {
            initNormalTitleBar("");
            this.mCommentID = intent.getIntExtra("commentid", 0);
            this.mSelectCommentID = intent.getIntExtra(COMMENT_SELECT_ID, 0);
            this.mType = intent.getIntExtra("feng_type", 0);
        }
        this.mHeaderBinding = CommentDetailHeaderLayoutBinding.inflate(LayoutInflater.from(this), ((ActivityCommentDetailBinding) this.mBaseBinding).llParent, false);
        this.mAdapter = new CommentReplyAdapter(this, this.mSelectCommentID, this.mList.getCommentList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setRefreshProgressStyle(2);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView, State.Normal);
                CommentReplyListActivity.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                CommentReplyListActivity.this.mPage = 1;
                CommentReplyListActivity.this.getData();
            }
        });
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView) != State.Loading) {
                    if (CommentReplyListActivity.this.mPage <= CommentReplyListActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CommentReplyListActivity.this, ((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                        CommentReplyListActivity.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CommentReplyListActivity.this, ((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setRefreshing(true);
        ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setLScrollListener(new LRecyclerView$LScrollListener() {
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
    }

    private void initHeadView(final CommentInfo info) {
        if (this.mAdapter != null) {
            this.mAdapter.setSnsInfo(info.sns);
        }
        this.mReplyCommentCount = info.reply.count;
        initNormalTitleBar(this.mReplyCommentCount + "条回复");
        ((ActivityCommentDetailBinding) this.mBaseBinding).replayText.setText("回复给@" + ((String) info.user.name.get()));
        this.mSnsID = info.sns.id;
        this.mHeaderBinding.userImage.setHeadUrl(FengUtil.getHeadImageUrl(info.user.getHeadImageInfo()));
        this.mHeaderBinding.userImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                info.user.intentToPersonalHome(CommentReplyListActivity.this);
            }
        });
        if (info.sns.snstype == 0 || info.sns.snstype == 1) {
            this.mHeaderBinding.tvWatch.setText(R.string.watch_original_article);
        } else if (info.sns.snstype == 2 || info.sns.snstype == 3) {
            this.mHeaderBinding.tvWatch.setText(R.string.watch_original_micro);
        } else if (info.sns.snstype == 9 || info.sns.snstype == 10) {
            this.mHeaderBinding.tvWatch.setText(R.string.watch_original_discuss);
        }
        this.mHeaderBinding.setUserInfo(info.user);
        this.mHeaderBinding.setCommentInfo(info);
        this.mHeaderBinding.tvWatch.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (CommentReplyListActivity.this.mType == 0) {
                    CommentReplyListActivity.this.finish();
                } else if (info.sns.snstype == 0 || info.sns.snstype == 1) {
                    info.sns.intentToArticle(CommentReplyListActivity.this, info.id);
                } else if (info.sns.snstype == 9 || info.sns.snstype == 10) {
                    info.sns.intentToViewPoint(CommentReplyListActivity.this, true);
                }
            }
        });
        this.mHeaderBinding.topText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CommentReplyListActivity.this.mHeaderBinding.topText.setEnabled(false);
                info.clickPraiseOperation(CommentReplyListActivity.this, new SuccessFailCallback() {
                    public void onSuccess() {
                        super.onSuccess();
                        CommentReplyListActivity.this.mHeaderBinding.topText.setEnabled(true);
                    }

                    public void onFail() {
                        super.onFail();
                        CommentReplyListActivity.this.mHeaderBinding.topText.setEnabled(true);
                    }
                });
            }
        });
        this.mHeaderBinding.arrayText.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CommentReplyListActivity.this.showArraryDialog(v);
            }
        });
        this.mHeaderBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FengUtil.showCommentOperationDialog(CommentReplyListActivity.this, info, info.sns, true, 0, "", null);
            }
        });
        ((ActivityCommentDetailBinding) this.mBaseBinding).bottomLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                info.intentToSendCommentActivity(CommentReplyListActivity.this);
            }
        });
        if (TextUtils.isEmpty(info.image.url)) {
            if (!TextUtils.isEmpty((CharSequence) info.content.get())) {
                this.mHeaderBinding.content.setContent((String) info.content.get(), true, info.imagelist, info.videolist, null);
            }
            this.mHeaderBinding.adImage.setVisibility(8);
            return;
        }
        this.mHeaderBinding.adImage.setVisibility(0);
        int index = ((String) info.content.get()).lastIndexOf(MessageFormat.format(" [http://15feng.cn/p/{0}] ", new Object[]{info.image.hash}).trim());
        if (index >= 0) {
            info.content.set(((String) info.content.get()).substring(0, index));
        }
        this.mHeaderBinding.content.setContent((String) info.content.get(), true, info.imagelist, info.videolist, null);
        String imageUrl2 = FengUtil.getSingleNineScaleUrl(info.image, 200);
        info.image.lowUrl = imageUrl2;
        this.mHeaderBinding.adImage.setImageURI(Uri.parse(imageUrl2));
        this.mHeaderBinding.adImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                Intent intent = new Intent(CommentReplyListActivity.this, ShowBigImageActivity.class);
                List<ImageInfo> list = new ArrayList();
                list.add(info.image);
                intent.putExtra("mImageList", JsonUtil.toJson(list));
                intent.putExtra("position", 0);
                intent.putExtra("show_type", 1005);
                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(CommentReplyListActivity.this.mHeaderBinding.adImage, 0, info.image.hash)));
                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                CommentReplyListActivity.this.startActivity(intent);
                CommentReplyListActivity.this.overridePendingTransition(0, 0);
            }
        });
    }

    private void showArraryDialog(View view) {
        if (this.mCommentArrayBinding == null) {
            this.mCommentArrayBinding = ArticleCommentArraydialogBinding.inflate(LayoutInflater.from(this));
            this.mCommentArrayBinding.arrayHot.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (CommentReplyListActivity.this.mCommentOrderid != 1) {
                        CommentReplyListActivity.this.mCommentOrderid = 1;
                        CommentReplyListActivity.this.mHeaderBinding.arrayText.setText(R.string.comment_hotarray);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setTextColor(CommentReplyListActivity.this.mResources.getColor(R.color.color_ffffff));
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setTextColor(CommentReplyListActivity.this.mResources.getColor(R.color.color_000000));
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setPadding(CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setPadding(CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8);
                        CommentReplyListActivity.this.mPage = 1;
                        CommentReplyListActivity.this.getData();
                    }
                    CommentReplyListActivity.this.hideCommentArrayWindow();
                }
            });
            this.mCommentArrayBinding.arrayTime.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (CommentReplyListActivity.this.mCommentOrderid != 2) {
                        CommentReplyListActivity.this.mCommentOrderid = 2;
                        CommentReplyListActivity.this.mHeaderBinding.arrayText.setText(R.string.comment_timearray);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setTextColor(CommentReplyListActivity.this.mResources.getColor(R.color.color_000000));
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setTextColor(CommentReplyListActivity.this.mResources.getColor(R.color.color_ffffff));
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayHot.setPadding(CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8);
                        CommentReplyListActivity.this.mCommentArrayBinding.arrayTime.setPadding(CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8, CommentReplyListActivity.this.m8);
                        CommentReplyListActivity.this.mPage = 1;
                        CommentReplyListActivity.this.getData();
                    }
                    CommentReplyListActivity.this.hideCommentArrayWindow();
                }
            });
        }
        if (this.mCommentOrderid == 1) {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(17170445);
        } else {
            this.mCommentArrayBinding.arrayHot.setBackgroundResource(17170445);
            this.mCommentArrayBinding.arrayTime.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCommentArrayBinding.arrayTime.setTextColor(this.mResources.getColor(R.color.color_ffffff));
            this.mCommentArrayBinding.arrayHot.setTextColor(this.mResources.getColor(R.color.color_000000));
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

    private void hideCommentArrayWindow() {
        if (this.mCommentArrayWindow != null && this.mCommentArrayWindow.isShowing()) {
            this.mCommentArrayWindow.dismiss();
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("orderid", String.valueOf(this.mCommentOrderid));
        map.put("commentid", String.valueOf(this.mCommentID));
        map.put("page", String.valueOf(this.mPage));
        FengApplication.getInstance().httpRequest("comment/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CommentReplyListActivity.this.mList.size() > 0) {
                    CommentReplyListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CommentReplyListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CommentReplyListActivity.this.getData();
                        }
                    });
                }
                ((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView, State.Normal);
                ((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                CommentReplyListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        CommentReplyListActivity.this.mRootBinding.titleLine.title.setVisibility(0);
                        JSONObject jsonComment = jsonResult.getJSONObject("body").getJSONObject("comment");
                        CommentInfo info = new CommentInfo();
                        info.parser(jsonComment);
                        CommentReplyListActivity.this.initHeadView(info);
                        CommentReplyListActivity.this.mTotalPage = info.reply.pagecount;
                        List<CommentInfo> list = info.reply.list;
                        CommentReplyListActivity.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (CommentReplyListActivity.this.mPage == 1) {
                                CommentReplyListActivity.this.mList.clear();
                            }
                            CommentReplyListActivity.this.mList.addAll(list);
                            CommentReplyListActivity.this.mPage = CommentReplyListActivity.this.mPage + 1;
                        }
                        CommentReplyListActivity.this.mAdapter.notifyDataSetChanged();
                    } else if (code == -66) {
                        CommentReplyListActivity.this.showEmptyView((int) R.string.comment_notexist, (int) R.drawable.blank_comment);
                    } else if (code == -75) {
                        CommentReplyListActivity.this.showEmptyView((int) R.string.choose_car_discuss_not_exist_tips, (int) R.drawable.icon_article_delete);
                    } else if (CommentReplyListActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("comment/info/", code);
                    } else {
                        CommentReplyListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                CommentReplyListActivity.this.getData();
                            }
                        });
                        CommentReplyListActivity.this.mRootBinding.titleLine.title.setVisibility(8);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("comment/info/", content, e);
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event.snsInfo != null && event.snsInfo.id == this.mSnsID) {
            switch (event.refreshType) {
                case ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE /*2003*/:
                    if (event.commentInfo.originparentid == this.mCommentID) {
                        this.mReplyCommentCount++;
                        initNormalTitleBar(this.mReplyCommentCount + "条回复");
                        this.mList.add(0, event.commentInfo);
                        this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    }
                    return;
                case m_AppUI.MSG_APP_VERSION_COMMEND /*2006*/:
                    if (event.commentInfo.originparentid == 0 && event.commentInfo.id == this.mCommentID) {
                        initNormalTitleBar("");
                        showEmptyView((int) R.string.comment_notexist, (int) R.drawable.blank_comment);
                        return;
                    } else if (event.commentInfo.originparentid > 0 && event.commentInfo.originparentid == this.mCommentID) {
                        this.mReplyCommentCount--;
                        if (this.mReplyCommentCount <= 0) {
                            this.mReplyCommentCount = 0;
                        }
                        initNormalTitleBar(this.mReplyCommentCount + "条回复");
                        int pos = this.mList.getPosition(event.commentInfo.id);
                        if (pos != -1) {
                            this.mList.remove(pos);
                        }
                        this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    } else {
                        return;
                    }
                default:
                    return;
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CommentPageRefreshEvent event) {
        if (event != null && event.isTop) {
            int position = this.mList.getPosition(event.commentId);
            if (position != -1) {
                CommentInfo commentInfo = this.mList.get(position);
                commentInfo.istop.set(1);
                commentInfo.top.set(event.topNum);
            }
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && !TextUtils.isEmpty(event.key) && this.mAdapter != null && this.mAdapter.getSoleKey().equals(event.key)) {
            if (LogGatherReadUtil.getInstance().getCurrentPage().equals("app_send_comment") && event.isFinish) {
                CommentInfo commentInfo = this.mList.get(this.mList.size() - 1);
                if (commentInfo.isLocalExtend) {
                    this.mList.getCommentList().remove(commentInfo);
                    this.mAdapter.notifyDataSetChanged();
                }
            }
            ((ActivityCommentDetailBinding) this.mBaseBinding).recyclerView.postDelayed(new Runnable() {
                public void run() {
                    ((ActivityCommentDetailBinding) CommentReplyListActivity.this.mBaseBinding).recyclerView.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }
}
