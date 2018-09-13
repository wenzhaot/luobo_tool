package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarBrandItemAdapter;
import com.feng.car.databinding.ActivitySearchcarBrandBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.entity.car.CarBrandInfoList;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.selectcar.decoration.TitleWithLetterItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class BrandConditionActivity extends BaseActivity<ActivitySearchcarBrandBinding> {
    private CarBrandItemAdapter mAdapter;
    private CarBrandInfoList mCarBrandList = new CarBrandInfoList();
    private int mCarId = 0;
    private TitleWithLetterItemDecoration mDecoration;
    private boolean mNeedOpenResult = false;

    public int setBaseContentView() {
        return R.layout.activity_searchcar_brand;
    }

    public void initView() {
        closeSwip();
        this.mCarId = SearchCarManager.newInstance().getBrandId();
        this.mNeedOpenResult = getIntent().getBooleanExtra("cars", false);
        initNormalTitleBar((int) R.string.brand);
        changeLeftIcon(R.drawable.icon_close);
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
        this.mRootBinding.titleLine.tvRightText.setVisibility(0);
        this.mRootBinding.titleLine.tvRightText.setText(R.string.unlimited_condition);
        this.mRootBinding.titleLine.tvRightText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().clearBrandData();
                if (SearchCarManager.newInstance().isNeedOpenResultActivity() || BrandConditionActivity.this.mNeedOpenResult) {
                    BrandConditionActivity.this.startActivity(new Intent(BrandConditionActivity.this, SearchCarResultActivity.class));
                } else if (BrandConditionActivity.this.mCarId != SearchCarManager.newInstance().getBrandId()) {
                    EventBus.getDefault().post(new SearchCarEvent(1));
                }
                BrandConditionActivity.this.finish();
            }
        });
        this.mAdapter = new CarBrandItemAdapter(this, this.mCarBrandList.getCarBrandInfoList());
        this.mAdapter.setmBrandId(SearchCarManager.newInstance().getBrandId());
        LRecyclerViewAdapter mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.setAdapter(mLRecyclerViewAdapter);
        ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.setPullRefreshEnabled(false);
        mLRecyclerViewAdapter.removeFooterView(mLRecyclerViewAdapter.getFooterView());
        getAllBrandList();
    }

    private void getAllBrandList() {
        FengApplication.getInstance().httpRequest("car/brandlist/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivitySearchcarBrandBinding) BrandConditionActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivitySearchcarBrandBinding) BrandConditionActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("brand");
                        BaseListModel<CarBrandInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarBrandInfo.class, jsonBrand);
                        List<CarBrandInfo> list = baseListModel.list;
                        if (list != null && list.size() > 0) {
                            BrandConditionActivity.this.mCarBrandList.clear();
                            BrandConditionActivity.this.mCarBrandList.addAll(list);
                            BrandConditionActivity.this.initAdapter();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode("car/brandlist/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("car/brandlist/", content, e);
                }
            }
        });
    }

    private void initAdapter() {
        if (this.mDecoration == null) {
            this.mDecoration = new TitleWithLetterItemDecoration(this, 1, this.mCarBrandList.getCarBrandInfoList());
            ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.addItemDecoration(this.mDecoration);
            ((ActivitySearchcarBrandBinding) this.mBaseBinding).indexBar.setmPressedShowTextView(((ActivitySearchcarBrandBinding) this.mBaseBinding).tvSideBarHint).setNeedRealIndex(false).setHeadNum(1).setmLayoutManager((LinearLayoutManager) ((ActivitySearchcarBrandBinding) this.mBaseBinding).recyclerview.getLayoutManager()).setmSourceDatas(this.mCarBrandList.getCarBrandInfoList());
            this.mAdapter.setOnItemClickLister(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    SearchCarManager.newInstance().setCurrentBrand(BrandConditionActivity.this.mCarBrandList.get(position));
                    if (BrandConditionActivity.this.mCarId != SearchCarManager.newInstance().getBrandId()) {
                        EventBus.getDefault().post(new SearchCarEvent(1));
                    }
                    if (BrandConditionActivity.this.mNeedOpenResult) {
                        BrandConditionActivity.this.startActivity(new Intent(BrandConditionActivity.this, SearchCarResultActivity.class));
                    }
                    BrandConditionActivity.this.finish();
                }
            });
        }
        setLastFlagInCarBrand();
    }

    private void setLastFlagInCarBrand() {
        for (int i = 0; i < this.mCarBrandList.size(); i++) {
            if (i != this.mCarBrandList.size() - 1) {
                if (this.mCarBrandList.get(i).abc.equals(this.mCarBrandList.get(i + 1).abc)) {
                    this.mCarBrandList.get(i).posflag = 0;
                } else {
                    this.mCarBrandList.get(i).posflag = 1;
                }
            }
        }
        this.mCarBrandList.get(this.mCarBrandList.size() - 1).posflag = 0;
    }
}
