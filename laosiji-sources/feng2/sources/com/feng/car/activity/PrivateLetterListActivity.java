package com.feng.car.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.PrivateLetterAdapter;
import com.feng.car.databinding.ActivityPrivateLetterListBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnItemLongClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PrivateLetterListActivity extends BaseActivity<ActivityPrivateLetterListBinding> {
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<MessageInfo> mList;
    private int mPage = 1;
    private PrivateLetterAdapter mPrivateLetterAdapter;
    private int mTotalPage = 0;

    public int setBaseContentView() {
        return R.layout.activity_private_letter_list;
    }

    public void initView() {
        initNormalTitleBar("私信");
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
        this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
        this.mRootBinding.titleLine.tvRightText.setVisibility(0);
        this.mRootBinding.titleLine.tvRightText.setText(R.string.setting);
        this.mRootBinding.titleLine.tvRightText.setTextColor(ContextCompat.getColor(this, R.color.color_ffb90a));
        this.mRootBinding.titleLine.tvRightText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(PrivateLetterListActivity.this, PrivateSettingActivity.class);
                intent.putExtra("feng_type", PrivateSettingActivity.FROM_PRIVATE_LETTER_LIST);
                PrivateLetterListActivity.this.startActivity(intent);
            }
        });
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setPullRefreshEnabled(true);
        this.mRootBinding.titleLine.getRoot().setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter.scrollToPosition(0);
            }
        }));
        this.mList = new ArrayList();
        this.mPrivateLetterAdapter = new PrivateLetterAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mPrivateLetterAdapter);
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setRefreshProgressStyle(2);
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, State.Normal);
                PrivateLetterListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                PrivateLetterListActivity.this.mPage = 1;
                PrivateLetterListActivity.this.getData(true, false);
            }
        });
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter) != State.Loading) {
                    if (PrivateLetterListActivity.this.mPage <= PrivateLetterListActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(PrivateLetterListActivity.this, ((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, 20, State.Loading, null);
                        PrivateLetterListActivity.this.getData(false, true);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(PrivateLetterListActivity.this, ((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setRefreshing(true);
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                MessageInfo item = (MessageInfo) PrivateLetterListActivity.this.mList.get(position);
                if (item != null) {
                    item.unreadcount.set(0);
                    PrivateLetterListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(PrivateLetterListActivity.this, PrivateChatActivity.class);
                    intent.putExtra("userid", item.user.id);
                    intent.putExtra("name", (String) item.user.name.get());
                    PrivateLetterListActivity.this.startActivity(intent);
                }
            }
        });
        this.mLRecyclerViewAdapter.setOnItemLongClickListener(new OnItemLongClickListener() {
            public void onItemLongClick(View view, int position) {
                final MessageInfo item = (MessageInfo) PrivateLetterListActivity.this.mList.get(position);
                List<DialogItemEntity> list = new ArrayList();
                list.add(new DialogItemEntity("删除私信", true));
                CommonDialog.showCommonDialog(PrivateLetterListActivity.this, "", list, new OnDialogItemClickListener() {
                    public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                        PrivateLetterListActivity.this.deltePrivateLetter(item.user.id);
                    }
                });
            }
        });
        ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    private void getData(final boolean needClear, final boolean isLoadMoore) {
        if (!isLoadMoore || this.mPage <= this.mTotalPage) {
            Map<String, Object> map = new HashMap();
            map.put("page", String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest("user/message/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (PrivateLetterListActivity.this.mList.size() > 0) {
                        PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        PrivateLetterListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                PrivateLetterListActivity.this.getData(needClear, isLoadMoore);
                            }
                        });
                    }
                    ((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, State.Normal);
                    ((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter.refreshComplete();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject resultJson = new JSONObject(content);
                        int code = resultJson.getInt("code");
                        if (code == 1) {
                            JSONObject messageJson = resultJson.getJSONObject("body").getJSONObject("message");
                            BaseListModel<MessageInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(MessageInfo.class, messageJson);
                            PrivateLetterListActivity.this.mTotalPage = baseListModel.pagecount;
                            List<MessageInfo> list = baseListModel.list;
                            PrivateLetterListActivity.this.hideEmptyView();
                            if (list.size() > 0) {
                                if (needClear) {
                                    PrivateLetterListActivity.this.mList.clear();
                                }
                                PrivateLetterListActivity.this.mList.addAll(list);
                                PrivateLetterListActivity.this.mPage = PrivateLetterListActivity.this.mPage + 1;
                            } else if (PrivateLetterListActivity.this.mPage == 1) {
                                PrivateLetterListActivity.this.showEmptyView(R.string.message_page_letter_empty_tips);
                            }
                            RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateLetterListBinding) PrivateLetterListActivity.this.mBaseBinding).rcviewPrivateLetter, State.Normal);
                            PrivateLetterListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            return;
                        }
                        PrivateLetterListActivity.this.showSecondTypeToast("获得私信列表失败，请稍后重试");
                        if (PrivateLetterListActivity.this.mList.size() > 0) {
                            FengApplication.getInstance().upLoadLog(true, "user/message/  " + code);
                        } else {
                            PrivateLetterListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    PrivateLetterListActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        if (PrivateLetterListActivity.this.mList.size() > 0) {
                            PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.retry_tips);
                        } else {
                            PrivateLetterListActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    PrivateLetterListActivity.this.getData(needClear, isLoadMoore);
                                }
                            });
                        }
                        FengApplication.getInstance().upLoadTryCatchLog("user/message/", content, e);
                    }
                }
            });
            return;
        }
        showThirdTypeToast((int) R.string.load_more_tips);
    }

    private void deltePrivateLetter(final int userid) {
        if (!FengApplication.getInstance().getSeverceState()) {
            showSecondTypeToast((int) R.string.server_maintain);
        } else if (userid == 0) {
            showSecondTypeToast((int) R.string.getinfo_failed);
        } else {
            Map<String, Object> map = new HashMap();
            map.put("messageuserid", String.valueOf(userid));
            FengApplication.getInstance().httpRequest("user/delete/message/", map, new OkHttpResponseCallback() {
                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            for (int i = 0; i < PrivateLetterListActivity.this.mList.size(); i++) {
                                MessageInfo info = (MessageInfo) PrivateLetterListActivity.this.mList.get(i);
                                if (info != null && info.user.id == userid) {
                                    PrivateLetterListActivity.this.mList.remove(i);
                                    PrivateLetterListActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                    break;
                                }
                            }
                            PrivateLetterListActivity.this.showFirstTypeToast((int) R.string.delete_private_message_success);
                            if (PrivateLetterListActivity.this.mList != null && PrivateLetterListActivity.this.mList.isEmpty()) {
                                PrivateLetterListActivity.this.showEmptyView(R.string.message_page_letter_empty_tips);
                                return;
                            }
                            return;
                        }
                        FengApplication.getInstance().checkCode("user/delete/message/", code);
                    } catch (JSONException e) {
                        PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.delete_private_message_failed);
                        FengApplication.getInstance().upLoadTryCatchLog("user/delete/message/", content, e);
                    }
                }

                public void onNetworkError() {
                    PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onStart() {
                    PrivateLetterListActivity.this.showProgress("", "正在删除私信，请稍候...");
                }

                public void onFinish() {
                    PrivateLetterListActivity.this.hideProgress();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PrivateLetterListActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    PrivateLetterListActivity.this.hideProgress();
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((ActivityPrivateLetterListBinding) this.mBaseBinding).rcviewPrivateLetter.setIsScrollDown(true);
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
