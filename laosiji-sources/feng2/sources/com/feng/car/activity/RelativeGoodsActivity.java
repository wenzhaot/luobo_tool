package com.feng.car.activity;

import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.RelativeGoodsAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.WXUtils;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
import org.json.JSONException;
import org.json.JSONObject;

public class RelativeGoodsActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private RelativeGoodsAdapter mAdapter;
    private int mCarSeriesID = 0;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<CommodityInfo> mList = new ArrayList();
    private int mTotalPage;

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        this.mCarSeriesID = getIntent().getIntExtra("carsid", 0);
        String title = getIntent().getStringExtra("name") + "相关商品";
        int m16 = this.mResources.getDimensionPixelOffset(R.dimen.default_16PX);
        initNormalTitleBar(title);
        this.mAdapter = new RelativeGoodsAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setPadding(m16, 0, m16, 0);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview, State.Normal);
                RelativeGoodsActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                RelativeGoodsActivity.this.mCurrentPage = 1;
                RelativeGoodsActivity.this.getData(true);
            }
        });
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_COMMODITY_DETAILS_PATH, new Object[]{Integer.valueOf(((CommodityInfo) RelativeGoodsActivity.this.mList.get(position)).id), Integer.valueOf(((CommodityInfo) RelativeGoodsActivity.this.mList.get(position)).shopid)}));
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (RelativeGoodsActivity.this.mCurrentPage <= RelativeGoodsActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(RelativeGoodsActivity.this, ((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        RelativeGoodsActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(RelativeGoodsActivity.this, ((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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

    private void getData(final boolean refresh) {
        Map<String, Object> map = new HashMap();
        map.put("seriesid", String.valueOf(this.mCarSeriesID));
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest("shop/commodity/listcommoditybyseries/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (RelativeGoodsActivity.this.mList.size() <= 0) {
                    RelativeGoodsActivity.this.showNetErrorView();
                } else {
                    RelativeGoodsActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) RelativeGoodsActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (RelativeGoodsActivity.this.mList.size() <= 0) {
                    RelativeGoodsActivity.this.showNetErrorView();
                } else {
                    RelativeGoodsActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        BaseListModel<CommodityInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CommodityInfo.class, jsonBody);
                        RelativeGoodsActivity.this.mTotalPage = baseListModel.pagecount;
                        List<CommodityInfo> list = baseListModel.list;
                        RelativeGoodsActivity.this.hideEmptyView();
                        if (list == null || list.size() <= 0) {
                            RelativeGoodsActivity.this.showEmptyImage(R.drawable.relative_goods_empty);
                            return;
                        }
                        int oldSize = RelativeGoodsActivity.this.mList.size();
                        if (refresh) {
                            RelativeGoodsActivity.this.mList.clear();
                        }
                        RelativeGoodsActivity.this.mList.addAll(list);
                        RelativeGoodsActivity.this.mCurrentPage = RelativeGoodsActivity.this.mCurrentPage + 1;
                        if (refresh) {
                            RelativeGoodsActivity.this.mAdapter.notifyDataSetChanged();
                        } else {
                            RelativeGoodsActivity.this.mAdapter.notifyItemRangeInserted(oldSize, RelativeGoodsActivity.this.mList.size() - oldSize);
                        }
                    } else if (RelativeGoodsActivity.this.mList.size() <= 0) {
                        RelativeGoodsActivity.this.showNetErrorView();
                    } else {
                        FengApplication.getInstance().checkCode("sns/snsall/", code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (RelativeGoodsActivity.this.mList.size() <= 0) {
                        RelativeGoodsActivity.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("sns/snsall/", content, e);
                }
            }
        });
    }

    private void showNetErrorView() {
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                RelativeGoodsActivity.this.getData(true);
            }
        });
    }

    private void showEmptyImage(int drawableID) {
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.titleLine.ivRightImage.setVisibility(8);
            this.mRootBinding.emptyView.hideEmptyText();
            this.mRootBinding.emptyView.hideEmptyButton();
            this.mRootBinding.emptyView.setEmptyImage(drawableID);
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }
}
