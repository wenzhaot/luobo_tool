package com.feng.car.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.view.LayoutInflater;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarSeriesListAdapter;
import com.feng.car.databinding.ActivityBlacklistBinding;
import com.feng.car.databinding.CarSeriesPopWinBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.CarSeriesInfoList;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.stub.StubApp;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class StopSellingCarActivity extends BaseActivity<ActivityBlacklistBinding> {
    private CarSeriesListAdapter mAdapter;
    private CarSeriesPopWinBinding mCarSeriesHeadBinding;
    private boolean mIsNeedHeader = true;
    private CarSeriesInfoList mList = new CarSeriesInfoList();

    static {
        StubApp.interface11(3137);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_blacklist;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.stopsale_cars);
        this.mAdapter = new CarSeriesListAdapter(this, 0, this.mList.getCarSeriesInfoList());
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setAdapter(lRecyclerViewAdapter);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((ActivityBlacklistBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        lRecyclerViewAdapter.removeFooterView(lRecyclerViewAdapter.getFooterView());
        boolean z = (SearchCarManager.newInstance().getCurrentBrandInfo().id == 0 || SearchCarManager.newInstance().getCurrentBrandInfo().image == null || StringUtil.isEmpty(SearchCarManager.newInstance().getCurrentBrandInfo().image.url)) ? false : true;
        this.mIsNeedHeader = z;
        if (this.mIsNeedHeader) {
            this.mCarSeriesHeadBinding = CarSeriesPopWinBinding.inflate(LayoutInflater.from(this));
            this.mCarSeriesHeadBinding.getRoot().setLayoutParams(new LayoutParams(-1, -2));
            this.mCarSeriesHeadBinding.getRoot().setVisibility(8);
            int m72 = getResources().getDimensionPixelSize(R.dimen.default_72PX);
            this.mCarSeriesHeadBinding.afdCarBrandImage.setAutoImageURI(Uri.parse(FengUtil.getFixedSizeUrl(SearchCarManager.newInstance().getCurrentBrandInfo().image, m72, m72)));
            this.mCarSeriesHeadBinding.tvCarBrand.setText((CharSequence) SearchCarManager.newInstance().getCurrentBrandInfo().name.get());
            lRecyclerViewAdapter.addHeaderView(this.mCarSeriesHeadBinding.getRoot());
        }
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        int brandid = SearchCarManager.newInstance().getBrandId();
        if (brandid != 0) {
            map.put("brandid", String.valueOf(brandid));
        }
        map.put("searchtype", String.valueOf(SearchCarManager.newInstance().getPriceType()));
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        map.put("searchterms", SearchCarManager.newInstance().getAllConditionData());
        if (!"0".equals(SearchCarManager.newInstance().getCurrentPriceValueString())) {
            map.put("pricerange", SearchCarManager.newInstance().getCurrentPriceValueString());
        }
        map.put("page", String.valueOf(1));
        FengApplication.getInstance().httpRequest("carsearch/series/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (StopSellingCarActivity.this.mList.size() == 0) {
                    StopSellingCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            StopSellingCarActivity.this.getData();
                        }
                    });
                } else {
                    StopSellingCarActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
                StopSellingCarActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                StopSellingCarActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (StopSellingCarActivity.this.mList.size() == 0) {
                    StopSellingCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            StopSellingCarActivity.this.getData();
                        }
                    });
                } else {
                    StopSellingCarActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
                StopSellingCarActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        StopSellingCarActivity.this.hideEmptyView();
                        JSONObject jsonObject = jsonResult.getJSONObject("body").getJSONObject("cars");
                        BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarSeriesInfo.class, jsonObject);
                        List<CarSeriesInfo> list1 = baseListModel.list1;
                        if (list1.size() > 0) {
                            StopSellingCarActivity.this.mList.clear();
                            StopSellingCarActivity.this.mList.addAll(list1);
                        } else if (StopSellingCarActivity.this.mList.size() == 0) {
                            StopSellingCarActivity.this.showEmptyView((int) R.string.unfind_cars, (int) R.drawable.icon_cars_empty);
                        } else {
                            StopSellingCarActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        }
                        if (StopSellingCarActivity.this.mIsNeedHeader && !StopSellingCarActivity.this.mCarSeriesHeadBinding.getRoot().isShown()) {
                            StopSellingCarActivity.this.mCarSeriesHeadBinding.getRoot().setVisibility(0);
                            StopSellingCarActivity.this.carsModelGroup();
                        }
                        StopSellingCarActivity.this.mAdapter.setNeedBrand(StopSellingCarActivity.this.mIsNeedHeader);
                        StopSellingCarActivity.this.mAdapter.notifyDataSetChanged();
                        return;
                    }
                    StopSellingCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            StopSellingCarActivity.this.getData();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("carsearch/series/", content, e);
                }
            }
        });
    }

    private void carsModelGroup() {
        Collections.sort(this.mList.getCarSeriesInfoList(), new Comparator<CarSeriesInfo>() {
            public int compare(CarSeriesInfo lhs, CarSeriesInfo rhs) {
                return Integer.valueOf(lhs.factory.factoryPosition).compareTo(Integer.valueOf(rhs.factory.factoryPosition));
            }
        });
        setFlagsInCarModel();
    }

    private void setFlagsInCarModel() {
        int i = 0;
        while (i < this.mList.size()) {
            if (!(i == this.mList.size() - 1 || this.mList.get(i).factory.name.equals(this.mList.get(i + 1).factory.name))) {
                this.mList.get(i).poslastflag = 1;
            }
            if (!(i == 0 || this.mList.get(i).factory.name.equals(this.mList.get(i - 1).factory.name))) {
                this.mList.get(i).posfirstflag = 1;
            }
            i++;
        }
        if (this.mList.size() > 0) {
            this.mList.get(0).posfirstflag = 1;
            this.mList.get(this.mList.size() - 1).poslastflag = 1;
        }
    }
}
