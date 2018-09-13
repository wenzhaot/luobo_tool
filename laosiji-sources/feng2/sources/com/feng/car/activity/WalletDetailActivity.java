package com.feng.car.activity;

import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.WalletDetailAdapter;
import com.feng.car.databinding.WalletDetailActivityBinding;
import com.feng.car.databinding.WalletDetailTopPopBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.wallet.WalletDetailInfo;
import com.feng.car.view.Solve7PopupWindow;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.ToastUtil;
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
import org.json.JSONException;
import org.json.JSONObject;

public class WalletDetailActivity extends BaseActivity<WalletDetailActivityBinding> {
    private static int ALL_DETAIL = -1;
    private static int ANSWER_SUCCESS = 0;
    private static int PICK_UP = 60;
    private static int SHARE_PAY = 10;
    private static int SYS_RED_PACKET = 20;
    private WalletDetailAdapter mAdapter;
    private int mCurrentFilterType = ALL_DETAIL;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<WalletDetailInfo> mList = new ArrayList();
    private WalletDetailTopPopBinding mPopBinding;
    private Solve7PopupWindow mPopupWindow;
    private int mTotalPage = 0;

    static {
        StubApp.interface11(3273);
    }

    public int setBaseContentView() {
        return R.layout.wallet_detail_activity;
    }

    public void initView() {
        hideDefaultTitleBar();
        ((WalletDetailActivityBinding) this.mBaseBinding).ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletDetailActivity.this.finish();
            }
        });
        ((WalletDetailActivityBinding) this.mBaseBinding).rlTopMenu.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (WalletDetailActivity.this.mPopupWindow == null) {
                    WalletDetailActivity.this.showPopWindow();
                } else if (WalletDetailActivity.this.mPopupWindow.isShowing()) {
                    WalletDetailActivity.this.mPopupWindow.dismiss();
                } else {
                    WalletDetailActivity.this.showPopWindow();
                }
            }
        });
        ((WalletDetailActivityBinding) this.mBaseBinding).lRc.setLayoutManager(new LinearLayoutManager(this));
        this.mAdapter = new WalletDetailAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((WalletDetailActivityBinding) this.mBaseBinding).lRc.setAdapter(this.mLRecyclerViewAdapter);
        ((WalletDetailActivityBinding) this.mBaseBinding).lRc.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc, State.Normal);
                WalletDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                WalletDetailActivity.this.mCurrentPage = 1;
                WalletDetailActivity.this.getData();
            }
        });
        ((WalletDetailActivityBinding) this.mBaseBinding).lRc.setRefreshing(true);
        ((WalletDetailActivityBinding) this.mBaseBinding).lRc.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (!RecyclerViewStateUtils.isRecylerViewLoading(((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc)) {
                    if (WalletDetailActivity.this.mCurrentPage > WalletDetailActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(WalletDetailActivity.this, ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(WalletDetailActivity.this, ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc, 20, State.Loading, null);
                    WalletDetailActivity.this.getData();
                }
            }
        });
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("type", Integer.valueOf(this.mCurrentFilterType));
        map.put("page", Integer.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest("hq/award/history/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (WalletDetailActivity.this.mList.size() > 0) {
                    ToastUtil.showToast(WalletDetailActivity.this, R.string.net_abnormal);
                } else {
                    WalletDetailActivity.this.initNormalTitleBar((int) R.string.reward_detail);
                    WalletDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            WalletDetailActivity.this.getData();
                        }
                    });
                }
                RecyclerViewStateUtils.setFooterViewState(((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc, State.Normal);
                ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc.refreshComplete();
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc, State.Normal);
                ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (WalletDetailActivity.this.mList.size() > 0) {
                    ToastUtil.showToast(WalletDetailActivity.this, R.string.net_abnormal);
                    return;
                }
                WalletDetailActivity.this.initNormalTitleBar((int) R.string.reward_detail);
                WalletDetailActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        WalletDetailActivity.this.getData();
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonDetail = jsonResult.getJSONObject("body").getJSONObject("details");
                        BaseListModel<WalletDetailInfo> mPageInfo = new BaseListModel();
                        mPageInfo.parser(WalletDetailInfo.class, jsonDetail);
                        WalletDetailActivity.this.mTotalPage = mPageInfo.pagecount;
                        List<WalletDetailInfo> list = mPageInfo.list;
                        WalletDetailActivity.this.hideEmptyView();
                        WalletDetailActivity.this.hideDefaultTitleBar();
                        if (WalletDetailActivity.this.mCurrentPage == 1) {
                            WalletDetailActivity.this.mList.clear();
                        }
                        WalletDetailActivity.this.mList.addAll(list);
                        WalletDetailActivity.this.mCurrentPage = WalletDetailActivity.this.mCurrentPage + 1;
                        WalletDetailActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    }
                    FengApplication.getInstance().checkCode("hq/award/history/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtil.showToast(WalletDetailActivity.this, R.string.net_abnormal);
                }
            }
        });
    }

    private void showPopWindow() {
        if (this.mPopupWindow == null) {
            initPopWindow();
        }
        if (this.mCurrentFilterType == ALL_DETAIL) {
            this.mPopBinding.rbAllDetail.setChecked(true);
        } else if (this.mCurrentFilterType == ANSWER_SUCCESS) {
            this.mPopBinding.rbAnswerSuccessGot.setChecked(true);
        } else if (this.mCurrentFilterType == SHARE_PAY) {
            this.mPopBinding.rbSharePay.setChecked(true);
        } else if (this.mCurrentFilterType == SYS_RED_PACKET) {
            this.mPopBinding.rbSysRedPacket.setChecked(true);
        } else if (this.mCurrentFilterType == PICK_UP) {
            this.mPopBinding.rbPickUp.setChecked(true);
        }
        this.mPopupWindow.showAsDropDown(((WalletDetailActivityBinding) this.mBaseBinding).divider);
        ((WalletDetailActivityBinding) this.mBaseBinding).ibPopIndicate.setImageResource(R.drawable.live_money_detail_arrow_up);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).ibPopIndicate.setImageResource(R.drawable.live_money_detail_arrow_down);
            }
        });
    }

    private void initPopWindow() {
        this.mPopBinding = WalletDetailTopPopBinding.inflate(this.mInflater);
        this.mPopupWindow = new Solve7PopupWindow(this.mPopBinding.getRoot(), -1, -2);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        this.mPopBinding.background.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WalletDetailActivity.this.mPopupWindow.dismiss();
            }
        });
        this.mPopBinding.rgChange.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all_detail /*2131625560*/:
                        ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).tvDetailFilter.setText(R.string.all_detail);
                        WalletDetailActivity.this.mCurrentFilterType = WalletDetailActivity.ALL_DETAIL;
                        break;
                    case R.id.rb_answer_success_got /*2131625561*/:
                        ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).tvDetailFilter.setText(R.string.answer_success_got);
                        WalletDetailActivity.this.mCurrentFilterType = WalletDetailActivity.ANSWER_SUCCESS;
                        break;
                    case R.id.rb_sharePay /*2131625562*/:
                        ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).tvDetailFilter.setText(R.string.share_pay);
                        WalletDetailActivity.this.mCurrentFilterType = WalletDetailActivity.SHARE_PAY;
                        break;
                    case R.id.rb_sys_redPacket /*2131625563*/:
                        ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).tvDetailFilter.setText(R.string.sys_redPacket);
                        WalletDetailActivity.this.mCurrentFilterType = WalletDetailActivity.SYS_RED_PACKET;
                        break;
                    case R.id.rb_pickUp /*2131625564*/:
                        ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).tvDetailFilter.setText(R.string.pick_up);
                        WalletDetailActivity.this.mCurrentFilterType = WalletDetailActivity.PICK_UP;
                        break;
                }
                if (WalletDetailActivity.this.mPopupWindow != null) {
                    WalletDetailActivity.this.mPopupWindow.dismiss();
                }
                ((WalletDetailActivityBinding) WalletDetailActivity.this.mBaseBinding).lRc.forceToRefresh();
            }
        });
    }
}
