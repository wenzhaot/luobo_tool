package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarBrandItemAdapter;
import com.feng.car.databinding.SelectCarByBrandActivityBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.UploadPriceSelectEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.selectcar.decoration.TitleWithLetterItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectCarByBrandActivity extends BaseActivity<SelectCarByBrandActivityBinding> {
    private int m72;
    private CarBrandItemAdapter mAdapter;
    private CarBrandInfo mBrandInfo = new CarBrandInfo();
    private List<CarBrandInfo> mDatas = new ArrayList();
    private TitleWithLetterItemDecoration mDecoration;
    private boolean mIsUploadPrice = false;
    private LinearLayoutManager mManager;
    private int mPos = -1;
    private int mType;

    static {
        StubApp.interface11(2883);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.select_car_by_brand_activity;
    }

    public void initView() {
        String json = "";
        if (getIntent().hasExtra("DATA_JSON")) {
            json = getIntent().getStringExtra("DATA_JSON");
        }
        if (json == null || json.equals("")) {
            this.mBrandInfo.id = 0;
        } else {
            try {
                this.mBrandInfo = (CarBrandInfo) JsonUtil.fromJson(json, CarBrandInfo.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.mType = getIntent().getIntExtra("feng_type", 1);
        this.mIsUploadPrice = getIntent().getBooleanExtra("type", false);
        if (this.mIsUploadPrice) {
            initNormalTitleBar(getString(R.string.select_brand), new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SelectCarByBrandActivity.this.finish();
                }
            });
        } else {
            initNormalTitleBar(this.mType == 0 ? R.string.find_cars : R.string.select_cars, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SelectCarByBrandActivity.this.finish();
                }
            });
        }
        this.mManager = new LinearLayoutManager(this);
        ((SelectCarByBrandActivityBinding) this.mBaseBinding).rvCarBrand.setLayoutManager(this.mManager);
        getBrandList();
    }

    private void startAllCarSeries() {
        Intent intent = new Intent(this, AllCarSeriesActivity.class);
        intent.putExtra("brandid", this.mBrandInfo.id);
        intent.putExtra("feng_type", this.mType);
        intent.putExtra("name", (String) this.mBrandInfo.name.get());
        intent.putExtra("url", FengUtil.getFixedSizeUrl(this.mBrandInfo.image, this.m72, this.m72));
        startActivity(intent);
    }

    private void initAdater() {
        this.mAdapter = new CarBrandItemAdapter(this, this.mDatas);
        ((SelectCarByBrandActivityBinding) this.mBaseBinding).rvCarBrand.setAdapter(this.mAdapter);
        this.mDecoration = new TitleWithLetterItemDecoration(this, this.mDatas);
        ((SelectCarByBrandActivityBinding) this.mBaseBinding).rvCarBrand.addItemDecoration(this.mDecoration);
        ((SelectCarByBrandActivityBinding) this.mBaseBinding).indexBar.setmPressedShowTextView(((SelectCarByBrandActivityBinding) this.mBaseBinding).tvSideBarHint).setNeedRealIndex(false).setmLayoutManager(this.mManager).setmSourceDatas(this.mDatas);
        setLastFlagInCarBrand();
        this.mAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                SelectCarByBrandActivity.this.mBrandInfo = (CarBrandInfo) SelectCarByBrandActivity.this.mDatas.get(position);
                if (SelectCarByBrandActivity.this.mIsUploadPrice) {
                    UploadPriceSelectEvent event = new UploadPriceSelectEvent(SelectCarByBrandActivity.this.mBrandInfo.id, (String) SelectCarByBrandActivity.this.mBrandInfo.name.get(), 1);
                    event.setImageUrl(SelectCarByBrandActivity.this.mBrandInfo.image.url);
                    EventBus.getDefault().post(event);
                    SelectCarByBrandActivity.this.finish();
                    return;
                }
                SelectCarByBrandActivity.this.startAllCarSeries();
            }
        });
    }

    private void setLastFlagInCarBrand() {
        int i = 0;
        while (i < this.mDatas.size()) {
            if (this.mBrandInfo.id > 0 && this.mPos < 0 && ((CarBrandInfo) this.mDatas.get(i)).id == this.mBrandInfo.id) {
                this.mPos = i;
            }
            if (i != this.mDatas.size() - 1) {
                if (((CarBrandInfo) this.mDatas.get(i)).abc.equals(((CarBrandInfo) this.mDatas.get(i + 1)).abc)) {
                    ((CarBrandInfo) this.mDatas.get(i)).posflag = 0;
                } else {
                    ((CarBrandInfo) this.mDatas.get(i)).posflag = 1;
                }
            }
            i++;
        }
        ((CarBrandInfo) this.mDatas.get(this.mDatas.size() - 1)).posflag = 0;
    }

    private void getBrandList() {
        FengApplication.getInstance().httpRequest("car/brandlist/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                SelectCarByBrandActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SelectCarByBrandActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
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
                            SelectCarByBrandActivity.this.mDatas.addAll(list);
                            SelectCarByBrandActivity.this.initAdater();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        if (this.mType != 0) {
            finish();
        }
    }
}
