package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.EmptyAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.databinding.LayoutMessageHeadBinding;
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MessageCountManager;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MessageActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private LayoutMessageHeadBinding layoutMessageHeadBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private EmptyAdapter mMessageAdapter;

    public void initView() {
        initNormalTitleBar((int) R.string.message);
        this.layoutMessageHeadBinding = LayoutMessageHeadBinding.inflate(this.mInflater);
        this.layoutMessageHeadBinding.rlMessageCommentContainer.setOnClickListener(this);
        this.layoutMessageHeadBinding.rlMessageAiteContainer.setOnClickListener(this);
        this.layoutMessageHeadBinding.rlMessagePraiseContainer.setOnClickListener(this);
        this.layoutMessageHeadBinding.rlMessageLetterContainer.setOnClickListener(this);
        this.layoutMessageHeadBinding.rlMessageSystemInfoContainer.setOnClickListener(this);
        this.mMessageAdapter = new EmptyAdapter(this);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mMessageAdapter);
        this.mLRecyclerViewAdapter.addHeaderView(this.layoutMessageHeadBinding.getRoot());
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                if (FengUtil.isNetworkAvailable(MessageActivity.this)) {
                    ((CommonRecyclerviewBinding) MessageActivity.this.mBaseBinding).recyclerview.setVisibility(0);
                    MessageActivity.this.layoutMessageHeadBinding.getRoot().setVisibility(0);
                    MessageActivity.this.hideEmptyView();
                    MessageCountManager.getInstance().requestMessageCount();
                    return;
                }
                MessageActivity.this.showNetErrorView();
            }
        });
        if (FengApplication.getInstance().isLoginUser()) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        } else {
            loginOut();
        }
    }

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    protected void onResume() {
        super.onResume();
        MessageCountManager.getInstance().requestMessageCount();
    }

    public void onSingleClick(View view) {
        switch (view.getId()) {
            case R.id.rl_message_comment_container /*2131625218*/:
                startActivity(new Intent(this, CommentActivity.class));
                return;
            case R.id.rl_message_aite_container /*2131625219*/:
                Intent intentTo = new Intent(this, AtMeActivity.class);
                if (MessageCountManager.getInstance().getAtWenZhangCount() > 0) {
                    intentTo.putExtra("feng_type", 0);
                } else if (MessageCountManager.getInstance().getAtCommentCount() > 0) {
                    intentTo.putExtra("feng_type", 1);
                } else {
                    intentTo.putExtra("feng_type", 0);
                }
                startActivity(intentTo);
                return;
            case R.id.rl_message_praise_container /*2131625221*/:
                startActivity(new Intent(this, PraiseActivity.class));
                return;
            case R.id.rl_message_letter_container /*2131625223*/:
                startActivity(new Intent(this, PrivateLetterListActivity.class));
                return;
            case R.id.rl_message_system_info_container /*2131625225*/:
                startActivity(new Intent(this, SystemNoticeActivity.class));
                return;
            default:
                return;
        }
    }

    public void loginOut() {
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setVisibility(8);
        showEmptyView(R.string.tourist_msg_tip, R.drawable.blank_message, R.string.login, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MessageActivity.this.startActivity(new Intent(MessageActivity.this, LoginActivity.class));
            }
        });
    }

    public void loginSuccess() {
        hideEmptyView();
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setVisibility(0);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.forceToRefresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeRefreshEvent event) {
        if (event.mIndex == 3) {
            ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        MessageCountManager manager = MessageCountManager.getInstance();
        MessageCountInfo messageCountInfo = new MessageCountInfo();
        messageCountInfo.commentCount = manager.getCommentCount();
        messageCountInfo.atCount = manager.getAtCount();
        messageCountInfo.praiseCount = manager.getPraiseCount();
        messageCountInfo.receivedCount = manager.getReceivedCount();
        messageCountInfo.sysCount = manager.getSysCount();
        this.layoutMessageHeadBinding.setMessageCountInfo(messageCountInfo);
        RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview, State.Normal);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.refreshComplete();
    }

    public void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((CommonRecyclerviewBinding) MessageActivity.this.mBaseBinding).recyclerview.forceToRefresh();
            }
        });
        RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview, State.Normal);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.refreshComplete();
    }
}
