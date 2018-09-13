package com.feng.car.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommentAdapter;
import com.feng.car.databinding.ActivityCommentBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.comment.CommentInfoList;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.event.CommentEvent;
import com.feng.car.event.CommentPageRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendCommentStartSlideEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class CommentActivity extends BaseActivity<ActivityCommentBinding> {
    private CommentAdapter mCommentAdapter;
    private CommentInfoList mCommentInfoList;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mMarg14;
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private int mPage = 1;
    private PopupWindow mPopupWindow;
    private int mTotalPage = 0;
    private View mViewPup;
    private int type = 2;

    public int setBaseContentView() {
        return R.layout.activity_comment;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.none);
        final int windowWidth = this.mResources.getDisplayMetrics().widthPixels;
        this.mMarg14 = this.mResources.getDimensionPixelSize(R.dimen.default_14PX);
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mMiddleTitleBinding.tvTitle.setText("我收到的评论");
        this.mMiddleTitleBinding.tvTitle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CommentActivity.this.mPopupWindow == null) {
                    CommentActivity.this.showWindow(CommentActivity.this.mRootBinding.titleLine.getRoot(), CommentActivity.this.mViewPup, windowWidth, (windowWidth * 5) / 12, 0, CommentActivity.this.mMarg14, true);
                } else if (CommentActivity.this.mPopupWindow.isShowing()) {
                    CommentActivity.this.mPopupWindow.dismiss();
                } else {
                    CommentActivity.this.showWindow(CommentActivity.this.mRootBinding.titleLine.getRoot(), CommentActivity.this.mViewPup, windowWidth, (windowWidth * 5) / 12, 0, CommentActivity.this.mMarg14, true);
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.scrollToPosition(0);
            }
        }));
        initPup();
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setPullRefreshEnabled(true);
        this.mCommentInfoList = new CommentInfoList();
        this.mCommentAdapter = new CommentAdapter(this, this.mCommentInfoList.getCommentList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCommentAdapter);
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setRefreshProgressStyle(2);
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, State.Normal);
                CommentActivity.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                CommentActivity.this.mPage = 1;
                CommentActivity.this.getData(true, false);
            }
        });
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment) != State.Loading) {
                    if (CommentActivity.this.mPage <= CommentActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CommentActivity.this, ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, 20, State.Loading, null);
                        CommentActivity.this.getData(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CommentActivity.this, ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setRefreshing(true);
        ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    private void initPup() {
        this.mViewPup = LayoutInflater.from(this).inflate(R.layout.message_menu, null);
        RadioGroup radioGroup = (RadioGroup) this.mViewPup.findViewById(R.id.rg_comment);
        int i = 0;
        while (i < 2) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(i == 0 ? R.string.mine_receive_comment : R.string.mine_send_comment);
            radioButton.setGravity(17);
            radioButton.setTextColor(this.mResources.getColorStateList(R.color.selector_black87_checked_ffffff));
            radioButton.setTextAppearance(this, R.style.textsize_14);
            radioButton.setButtonDrawable(ContextCompat.getDrawable(this, 17170445));
            radioButton.setBackgroundResource(R.drawable.radio_menu_selector);
            radioButton.setLayoutParams(new LayoutParams(-1, -2));
            radioButton.setPadding(0, this.mMarg14, 0, this.mMarg14);
            radioButton.setId(i);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radioButton.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    CommentActivity.this.hideWindow();
                }
            });
            radioGroup.addView(radioButton);
            radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int nResTitle = R.string.mine_receive_comment;
                    CommentActivity.this.mCommentInfoList.clear();
                    switch (checkedId) {
                        case 0:
                            nResTitle = R.string.mine_receive_comment;
                            CommentActivity.this.type = 2;
                            break;
                        case 1:
                            nResTitle = R.string.mine_send_comment;
                            CommentActivity.this.type = 1;
                            break;
                    }
                    CommentActivity.this.mPage = 1;
                    CommentActivity.this.mMiddleTitleBinding.tvTitle.setText(nResTitle);
                    ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.forceToRefresh();
                    CommentActivity.this.hideWindow();
                }
            });
            i++;
        }
    }

    private void showWindow(View parent, View view, int windowWidth, int parentWidth, int offX, int offY, boolean offsetMode) {
        if (offsetMode) {
            this.mPopupWindow = new PopupWindow(view, windowWidth, -2, true);
            this.mPopupWindow.setWidth(parentWidth);
        } else {
            this.mPopupWindow = new PopupWindow(view, -1, -2, true);
        }
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        this.mPopupWindow.showAsDropDown(parent, ((windowWidth - parentWidth) / 2) - offX, -offY);
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                CommentActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
            }
        });
    }

    private void getData(final boolean needClear, final boolean isLoadMoore) {
        if (!isLoadMoore || this.mPage <= this.mTotalPage) {
            Map<String, Object> map = new HashMap();
            map.put("type", String.valueOf(this.type));
            map.put("page", String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest("user/ywf/comment/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (CommentActivity.this.mCommentInfoList.size() > 0) {
                        CommentActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        CommentActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                CommentActivity.this.getData(needClear, isLoadMoore);
                            }
                        });
                        CommentActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                    }
                    ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, State.Normal);
                    ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    CommentActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject jsonComment = jsonResult.getJSONObject("body").getJSONObject("comment");
                            BaseListModel<CommentInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(CommentInfo.class, jsonComment);
                            CommentActivity.this.mTotalPage = baseListModel.pagecount;
                            List<CommentInfo> list = baseListModel.list;
                            CommentActivity.this.hideEmptyView();
                            if (list != null && list.size() > 0) {
                                if (needClear) {
                                    CommentActivity.this.mCommentInfoList.clear();
                                }
                                CommentActivity.this.mCommentInfoList.addAll(list);
                                CommentActivity.this.mPage = CommentActivity.this.mPage + 1;
                            } else if (CommentActivity.this.mPage == 1) {
                                if (CommentActivity.this.type == 2) {
                                    CommentActivity.this.showEmptyView(R.string.message_page_receive_comment_empty_tips);
                                } else if (CommentActivity.this.type == 1) {
                                    CommentActivity.this.showEmptyView(R.string.message_page_send_comment_empty_tips);
                                }
                            }
                            CommentActivity.this.mCommentAdapter.setCommentType(CommentActivity.this.type);
                            RecyclerViewStateUtils.setFooterViewState(((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment, State.Normal);
                            CommentActivity.this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
                            if (needClear) {
                                ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.smoothScrollToPosition(0);
                            }
                            CommentActivity.this.mMiddleTitleBinding.llParent.setVisibility(0);
                        } else if (CommentActivity.this.mCommentInfoList.size() > 0) {
                            FengApplication.getInstance().checkCode("user/ywf/comment/", code);
                        } else {
                            CommentActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    CommentActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                            CommentActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (CommentActivity.this.mCommentInfoList.size() > 0) {
                            CommentActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                        } else {
                            CommentActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    CommentActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                            CommentActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                        }
                        FengApplication.getInstance().upLoadTryCatchLog("user/ywf/comment/", content, e);
                    }
                }
            });
            return;
        }
        showThirdTypeToast((int) R.string.load_more_tips);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CommentEvent event) {
        if (event != null && !StringUtil.isEmpty(event.commentJson) && this.type == 1) {
            try {
                JSONObject jsonObject = new JSONObject(event.commentJson);
                CommentInfo info = new CommentInfo();
                info.parser(jsonObject);
                this.mCommentInfoList.add(0, info);
                this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CommentPageRefreshEvent event) {
        if (event == null) {
            return;
        }
        if (event.isTop) {
            int position = this.mCommentInfoList.getPosition(event.commentId);
            if (position != -1) {
                CommentInfo commentInfo = this.mCommentInfoList.get(position);
                commentInfo.istop.set(1);
                commentInfo.top.set(event.topNum);
                return;
            }
            return;
        }
        this.mCommentInfoList.get(this.mCommentInfoList.getPosition(event.commentId)).isreply = 1;
        this.mLRecyclerViewAdapter.getInnerAdapter().notifyDataSetChanged();
    }

    private void hideWindow() {
        if (this.mPopupWindow != null) {
            this.mPopupWindow.dismiss();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(final SendCommentStartSlideEvent event) {
        if (event != null && !TextUtils.isEmpty(event.key) && this.mCommentAdapter != null && this.mCommentAdapter.getSoleKey().equals(event.key)) {
            LinearLayoutManager manager = (LinearLayoutManager) ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.getLayoutManager();
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
            ((ActivityCommentBinding) this.mBaseBinding).rcviewComment.postDelayed(new Runnable() {
                public void run() {
                    ((ActivityCommentBinding) CommentActivity.this.mBaseBinding).rcviewComment.smoothScrollBy(0, event.dy);
                }
            }, 100);
        }
    }
}
