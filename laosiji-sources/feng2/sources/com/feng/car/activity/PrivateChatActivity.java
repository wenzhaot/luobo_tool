package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.PrivateChatAdapter;
import com.feng.car.databinding.ActivityPrivateChatBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.privatemsg.MessageInfo;
import com.feng.car.entity.privatemsg.MessageInfo.MessageMyInfo;
import com.feng.car.event.PrivateChatEvent;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PrivateChatActivity extends BaseActivity<ActivityPrivateChatBinding> {
    private OnLoadMoreListener loadMoreListener;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<MessageInfo> mList = new ArrayList();
    private int mPage = 1;
    private PrivateChatAdapter mPrivateChatAdapter;
    private int mTotalPage = 0;
    private int mUserId = 0;
    private String mUserName = "";

    static {
        StubApp.interface11(2758);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_private_chat;
    }

    public void initView() {
        this.mUserId = getIntent().getIntExtra("userid", 0);
        if (this.mUserId == 0) {
            showSecondTypeToast((int) R.string.getinfo_failed);
            return;
        }
        this.mUserName = getIntent().getStringExtra("name");
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setPullRefreshEnabled(true);
        this.mPrivateChatAdapter = new PrivateChatAdapter(this, this.mList);
        processTitle();
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mPrivateChatAdapter);
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setRefreshProgressStyle(2);
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat, State.Normal);
                PrivateChatActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                PrivateChatActivity.this.getData(false, true);
            }
        });
        this.loadMoreListener = new OnLoadMoreListener() {
            public void onLoadMore() {
                if (PrivateChatActivity.this.mPage <= PrivateChatActivity.this.mTotalPage) {
                    RecyclerViewStateUtils.setFooterViewState(PrivateChatActivity.this, ((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat, 20, State.Loading, null);
                    PrivateChatActivity.this.mPage = 1;
                    PrivateChatActivity.this.getData(true, false);
                }
            }
        };
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setOnLoadMoreListener(this.loadMoreListener);
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setNoMore(true);
        int m10 = this.mResources.getDimensionPixelSize(R.dimen.default_10PX);
        ((ActivityPrivateChatBinding) this.mBaseBinding).commentText.setPadding(m10, m10, m10, m10);
        ((ActivityPrivateChatBinding) this.mBaseBinding).commentText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (FengApplication.getInstance().getSeverceState()) {
                    Intent intent = new Intent(PrivateChatActivity.this, SendPrivateLetterActivity.class);
                    intent.putExtra("userid", PrivateChatActivity.this.mUserId);
                    intent.putExtra("name", PrivateChatActivity.this.mUserName);
                    PrivateChatActivity.this.startActivity(intent);
                    return;
                }
                PrivateChatActivity.this.showSecondTypeToast((int) R.string.server_maintain);
            }
        });
        ((ActivityPrivateChatBinding) this.mBaseBinding).tvPrivateDeleteBtn.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (v.isSelected()) {
                    PrivateChatActivity.this.deleteSelectMsg();
                }
            }
        });
    }

    public void processTitle() {
        if (this.mPrivateChatAdapter.getShown()) {
            ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setNoMore(true);
            ((ActivityPrivateChatBinding) this.mBaseBinding).tvPrivateDeleteBtn.setVisibility(0);
            ((ActivityPrivateChatBinding) this.mBaseBinding).bottom.setVisibility(4);
            initNormalLeftTitleBar("取消", new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PrivateChatActivity.this.setSelectUnShown();
                }
            });
            return;
        }
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.setNoMore(false);
        ((ActivityPrivateChatBinding) this.mBaseBinding).tvPrivateDeleteBtn.setVisibility(8);
        ((ActivityPrivateChatBinding) this.mBaseBinding).bottom.setVisibility(0);
        if (TextUtils.isEmpty(this.mUserName)) {
            initNormalTitleBar("私信");
        } else {
            initNormalTitleBar(this.mUserName);
        }
    }

    private void setSelectUnShown() {
        this.mPrivateChatAdapter.closeShown();
        this.mPrivateChatAdapter.notifyDataSetChanged();
        processTitle();
    }

    public void onBackPressed() {
        if (this.mPrivateChatAdapter.getShown()) {
            setSelectUnShown();
        } else {
            super.onBackPressed();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PrivateChatEvent event) {
        if (event != null && event.info != null && event.userID == this.mUserId) {
            MessageInfo info = event.info;
            MessageInfo item = new MessageInfo();
            item.content.set(info.content.get());
            item.ismysend.set(1);
            item.time.set("刚刚");
            MessageInfo messageInfo = new MessageInfo();
            messageInfo.getClass();
            MessageMyInfo messageMyItem = new MessageMyInfo(messageInfo);
            messageMyItem.id = FengApplication.getInstance().getUserInfo().id;
            messageMyItem.name.set(FengApplication.getInstance().getUserInfo().name.get());
            messageMyItem.image = FengApplication.getInstance().getUserInfo().getHeadImageInfo();
            item.my = messageMyItem;
            if (!(info.image == null || TextUtils.isEmpty(info.image.url))) {
                item.image.url = info.image.url;
                item.image.mimetype = info.image.mimetype;
                item.image.width = info.image.width;
                item.image.height = info.image.height;
            }
            this.mList.add(item);
            this.mLRecyclerViewAdapter.notifyDataSetChanged();
            ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.scrollToPosition(this.mList.size());
        }
    }

    private void getData(final boolean needClear, boolean isLoadMoore) {
        if (!isLoadMoore || this.mPage <= this.mTotalPage) {
            Map<String, Object> map = new HashMap();
            map.put("userid", String.valueOf(this.mUserId));
            map.put("page", String.valueOf(this.mPage));
            FengApplication.getInstance().httpRequest("user/messagebyuserid/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat, State.Normal);
                    ((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat.refreshCompleteLetter();
                    PrivateChatActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat, State.TheEnd);
                    ((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat.refreshCompleteLetter();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PrivateChatActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject resultJson = new JSONObject(content);
                        int code = resultJson.getInt("code");
                        if (code == 1) {
                            JSONObject messageJson = resultJson.getJSONObject("body").getJSONObject("message");
                            BaseListModel<MessageInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(MessageInfo.class, messageJson);
                            PrivateChatActivity.this.mTotalPage = baseListModel.pagecount;
                            List<MessageInfo> list = baseListModel.list;
                            if (list.size() > 0) {
                                if (!PrivateChatActivity.this.mPrivateChatAdapter.getShown()) {
                                }
                                if (needClear) {
                                    PrivateChatActivity.this.mList.clear();
                                }
                                PrivateChatActivity.this.reverseList(list);
                                PrivateChatActivity.this.mPage = PrivateChatActivity.this.mPage + 1;
                                PrivateChatActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                                if (needClear) {
                                    ((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat.scrollToPosition(PrivateChatActivity.this.mList.size());
                                    return;
                                } else {
                                    ((ActivityPrivateChatBinding) PrivateChatActivity.this.mBaseBinding).rcviewPrivateChat.scrollToPosition(list.size());
                                    return;
                                }
                            }
                            return;
                        }
                        FengApplication.getInstance().checkCode("user/messagebyuserid/", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        PrivateChatActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                        FengApplication.getInstance().upLoadTryCatchLog("user/messagebyuserid/", content, e);
                    }
                }
            });
            return;
        }
        RecyclerViewStateUtils.setFooterViewState(((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat, State.Normal);
        ((ActivityPrivateChatBinding) this.mBaseBinding).rcviewPrivateChat.refreshCompleteLetter();
    }

    private void reverseList(List<MessageInfo> list) {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                this.mList.add(0, list.get(i));
            }
        }
    }

    public void deleteSelectMsg() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("确定", false));
        CommonDialog.showCommonDialog(this, "确定删除所选私信？", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    List<MessageInfo> msgList = PrivateChatActivity.this.mPrivateChatAdapter.getMsgList();
                    if (msgList != null && msgList.size() != 0) {
                        PrivateChatActivity.this.deleteMessage();
                    }
                }
            }
        });
    }

    private void deleteMessage() {
        if (FengApplication.getInstance().getSeverceState()) {
            Map<String, Object> map = new HashMap();
            Map<String, Object> map1 = new HashMap();
            List<Integer> list = new ArrayList();
            for (MessageInfo info : this.mPrivateChatAdapter.getMsgList()) {
                list.add(Integer.valueOf(info.id));
            }
            map1.put("messageidlist", list);
            map.put("messageidlist", map1);
            FengApplication.getInstance().httpRequest("user/delete/message/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                }

                public void onStart() {
                }

                public void onFinish() {
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    PrivateChatActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        int code = new JSONObject(content).getInt("code");
                        if (code == 1) {
                            PrivateChatActivity.this.mList.removeAll(PrivateChatActivity.this.mPrivateChatAdapter.getMsgList());
                            PrivateChatActivity.this.setSelectUnShown();
                            return;
                        }
                        FengApplication.getInstance().checkCode("user/messagebyuserid/", code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        PrivateChatActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                        FengApplication.getInstance().upLoadTryCatchLog("user/messagebyuserid/", content, e);
                    }
                }
            });
            return;
        }
        showSecondTypeToast((int) R.string.server_maintain);
    }
}
