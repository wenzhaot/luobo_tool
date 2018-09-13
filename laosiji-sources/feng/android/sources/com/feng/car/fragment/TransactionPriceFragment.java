package com.feng.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PriceRankingCarsNewActivity;
import com.feng.car.adapter.CarsOwnerPriceAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.CarSeriesInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class TransactionPriceFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CarsOwnerPriceAdapter mAdapter;
    private int mCityID = 0;
    private int mCurrentPage = 1;
    LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CarSeriesInfoList mList = new CarSeriesInfoList();
    private int mTotalPage = 0;
    private int mType = 0;

    public static TransactionPriceFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        TransactionPriceFragment fragment = new TransactionPriceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mType = getArguments().getInt("type", 0);
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    public void backTop() {
        if (isAdded() && this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    protected void initView() {
        this.mAdapter = new CarsOwnerPriceAdapter(this.mActivity, this.mList.getCarSeriesInfoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsOwner(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview, State.Normal);
                TransactionPriceFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                TransactionPriceFragment.this.mCurrentPage = 1;
                TransactionPriceFragment.this.getCarsData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview) != State.Loading) {
                    if (TransactionPriceFragment.this.mCurrentPage <= TransactionPriceFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(TransactionPriceFragment.this.mActivity, ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        TransactionPriceFragment.this.getCarsData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(TransactionPriceFragment.this.mActivity, ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
            public void onScrollUp() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(true));
                ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview.setIsOwner(true);
            }

            public void onScrollDown() {
                EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
                ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview.setIsOwner(false);
            }

            public void onScrolled(int distanceX, int distanceY) {
            }

            public void onScrollStateChanged(int state) {
            }
        });
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                CarSeriesInfo info = TransactionPriceFragment.this.mList.get(position);
                Intent intent = new Intent(TransactionPriceFragment.this.mActivity, PriceRankingCarsNewActivity.class);
                intent.putExtra(HttpConstant.CARS_ID, info.id);
                intent.putExtra(HttpConstant.CITYID, TransactionPriceFragment.this.mCityID);
                TransactionPriceFragment.this.startActivity(intent);
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
    }

    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
    }

    public void onResume() {
        super.onResume();
    }

    private void getCarsData() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CITYID, String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        map.put("type", String.valueOf(this.mType));
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_FINALPRICE_LIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (TransactionPriceFragment.this.mList.size() > 0) {
                    ((BaseActivity) TransactionPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    TransactionPriceFragment.this.showErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview.setPullRefreshEnabled(false);
                ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (TransactionPriceFragment.this.mList.size() > 0) {
                    ((BaseActivity) TransactionPriceFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    TransactionPriceFragment.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        TransactionPriceFragment.this.hideEmpty();
                        JSONObject jsonObject = jsonResult.getJSONObject("body").getJSONObject(HttpConstant.CARS);
                        BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarSeriesInfo.class, jsonObject);
                        TransactionPriceFragment.this.mCityID = MapUtil.newInstance().getCurrentCityId();
                        List<CarSeriesInfo> list = baseListModel.list;
                        if (TransactionPriceFragment.this.mCurrentPage == 1) {
                            TransactionPriceFragment.this.mTotalPage = baseListModel.pagecount;
                            TransactionPriceFragment.this.mList.clear();
                        }
                        TransactionPriceFragment.this.mList.addAll(list);
                        TransactionPriceFragment.this.mCurrentPage = TransactionPriceFragment.this.mCurrentPage + 1;
                        TransactionPriceFragment.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.CAR_FINALPRICE_LIST, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.CAR_FINALPRICE_LIST, content, e);
                }
            }
        });
    }

    public void checkCityRefresh() {
        if (isAdded() && this.mBind != null && this.mCityID != 0 && this.mCityID != MapUtil.newInstance().getCurrentCityId()) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    private void showErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((CommonRecyclerviewBinding) TransactionPriceFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }
}
