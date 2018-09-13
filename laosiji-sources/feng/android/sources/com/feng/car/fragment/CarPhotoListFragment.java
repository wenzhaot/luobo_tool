package com.feng.car.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.ShowCarImageNewActivity;
import com.feng.car.adapter.CarPhotoListAdapter;
import com.feng.car.databinding.FragmentCarPhotoListBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarImageInfo;
import com.feng.car.event.PhotoListEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.recyclerview.EmptyView;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.HeaderSpanSizeLookup;
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

public class CarPhotoListFragment extends BaseFragment<FragmentCarPhotoListBinding> {
    private static final String CAR_MODEL_ID = "CAR_MODEL_ID";
    private static final String CAR_SERIES_ID = "CAR_SERIES_ID";
    private static final String CAR_YEAR = "CAR_YEAR";
    private static final String FIRST_LOAD = "first_load";
    private String mAllImageCount = "";
    private int mCarModelID;
    private CarPhotoListAdapter mCarPhotoListAdapter;
    private int mCarPhotoType = 1;
    private int mCarSeriesID;
    private int mCurrentPage = 1;
    public EmptyView mEmptyView;
    private boolean mFirstLoad = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<CarImageInfo> mList = new ArrayList();
    private int mTotalPage;
    private String mYear = "";

    public void backToTop() {
        if (this.mBind != null && ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto != null) {
            ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.scrollToPosition(0);
        }
    }

    protected int setLayoutId() {
        return 2130903247;
    }

    public static CarPhotoListFragment newInstance(int type, int carSeriesID, int carModelID, String year, boolean firstLoad) {
        Bundle args = new Bundle();
        args.putInt(FengConstant.FENGTYPE, type);
        args.putInt(CAR_SERIES_ID, carSeriesID);
        args.putInt(CAR_MODEL_ID, carModelID);
        args.putString(CAR_YEAR, year);
        args.putBoolean(FIRST_LOAD, firstLoad);
        CarPhotoListFragment fragment = new CarPhotoListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCarPhotoType = getArguments().getInt(FengConstant.FENGTYPE, 1);
        this.mCarSeriesID = getArguments().getInt(CAR_SERIES_ID);
        this.mCarModelID = getArguments().getInt(CAR_MODEL_ID);
        this.mYear = getArguments().getString(CAR_YEAR);
        this.mFirstLoad = getArguments().getBoolean(FIRST_LOAD, true);
    }

    public void reload(int carSeriesID, int carModelID, String year) {
        if (isAdded() && this.mBind != null && (carSeriesID != this.mCarSeriesID || carModelID != this.mCarModelID || year != this.mYear)) {
            this.mCarSeriesID = carSeriesID;
            this.mCarModelID = carModelID;
            this.mYear = year;
            ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setPullRefreshEnabled(true);
            ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.forceToRefresh();
        } else if (isAdded() && this.mBind != null && this.mList.size() <= 0) {
            ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setPullRefreshEnabled(true);
            ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.forceToRefresh();
        }
    }

    protected void initView() {
        this.mEmptyView = ((FragmentCarPhotoListBinding) this.mBind).emptyView;
        this.mCarPhotoListAdapter = new CarPhotoListAdapter(getActivity(), this.mList, this.mCarPhotoType);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCarPhotoListAdapter);
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setAdapter(this.mLRecyclerViewAdapter);
        this.mCarPhotoListAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(CarPhotoListFragment.this.mActivity, ShowCarImageNewActivity.class);
                intent.putExtra(HttpConstant.CARS_ID, CarPhotoListFragment.this.mCarSeriesID);
                intent.putExtra(HttpConstant.CARX_ID, CarPhotoListFragment.this.mCarModelID);
                intent.putExtra("position", position + 1);
                intent.putExtra(FengConstant.CAR_PHOTO_TYPE, CarPhotoListFragment.this.mCarPhotoType);
                intent.putExtra(FengConstant.ALL_CAR_IMAGE_CUNT, CarPhotoListFragment.this.mAllImageCount);
                CarPhotoListFragment.this.startActivity(intent);
                CarPhotoListFragment.this.mActivity.overridePendingTransition(2130968586, 0);
            }
        });
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setRefreshProgressStyle(2);
        manager.setSpanSizeLookup(new HeaderSpanSizeLookup((LRecyclerViewAdapter) ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.getAdapter(), manager.getSpanCount()));
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setLayoutManager(manager);
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setPullRefreshEnabled(this.mFirstLoad);
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                CarPhotoListFragment.this.mCurrentPage = 1;
                CarPhotoListFragment.this.loadImageList();
            }
        });
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto) != State.Loading) {
                    if (CarPhotoListFragment.this.mCurrentPage <= CarPhotoListFragment.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CarPhotoListFragment.this.getActivity(), ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto, 20, State.Loading, null);
                        CarPhotoListFragment.this.loadImageList();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CarPhotoListFragment.this.getActivity(), ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto, 20, State.TheEnd, null);
                }
            }
        });
        ((FragmentCarPhotoListBinding) this.mBind).rvSelectCarPhoto.setRefreshing(true);
    }

    private void loadImageList() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CARS_ID, String.valueOf(this.mCarSeriesID));
        if (this.mCarModelID != 0) {
            map.put(HttpConstant.CARX_ID, String.valueOf(this.mCarModelID));
        }
        if (!TextUtils.isEmpty(this.mYear)) {
            map.put(HttpConstant.YEAR, this.mYear);
        }
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        map.put("type", String.valueOf(this.mCarPhotoType));
        map.put("pagesize", String.valueOf(30));
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_IMAGELIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarPhotoListFragment.this.mList.size() > 0) {
                    ((BaseActivity) CarPhotoListFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPhotoListFragment.this.showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto.forceToRefresh();
                        }
                    });
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarPhotoListFragment.this.mList.size() > 0) {
                    ((BaseActivity) CarPhotoListFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    CarPhotoListFragment.this.showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto.forceToRefresh();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("image");
                        if (jsonBrand.has("allcount")) {
                            CarPhotoListFragment.this.mAllImageCount = jsonBrand.getString("allcount");
                        }
                        BaseListModel<CarImageInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarImageInfo.class, jsonBrand);
                        List<CarImageInfo> list = baseListModel.list;
                        CarPhotoListFragment.this.hideEmptyView();
                        if (list != null) {
                            if (CarPhotoListFragment.this.mCurrentPage == 1) {
                                CarPhotoListFragment.this.mTotalPage = baseListModel.pagecount;
                                CarPhotoListFragment.this.mList.clear();
                            }
                            CarPhotoListFragment.this.mList.addAll(list);
                            CarPhotoListFragment.this.mCurrentPage = CarPhotoListFragment.this.mCurrentPage + 1;
                            CarPhotoListFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                            EventBus.getDefault().post(new PhotoListEvent(CarPhotoListFragment.this.mCarPhotoType));
                        }
                    } else if (CarPhotoListFragment.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode(HttpConstant.CAR_IMAGELIST, code);
                    } else {
                        CarPhotoListFragment.this.showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto.forceToRefresh();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (CarPhotoListFragment.this.mList.size() > 0) {
                        ((BaseActivity) CarPhotoListFragment.this.mActivity).showSecondTypeToast(2131231273);
                    } else {
                        CarPhotoListFragment.this.showEmptyView(2131231188, 2130838112, 2131231470, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((FragmentCarPhotoListBinding) CarPhotoListFragment.this.mBind).rvSelectCarPhoto.forceToRefresh();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.CAR_IMAGELIST, content, e);
                }
            }
        });
    }

    private void hideEmptyView() {
        if (this.mEmptyView != null) {
            this.mEmptyView.setVisibility(8);
        }
    }

    private void showEmptyView(int textID, int drawableID, int btnTextID, OnSingleClickListener listener) {
        if (this.mEmptyView != null) {
            this.mEmptyView.setEmptyImage(drawableID);
            this.mEmptyView.setEmptyText(textID);
            this.mEmptyView.setButtonListener(btnTextID, listener);
            this.mEmptyView.setVisibility(0);
        }
    }
}
