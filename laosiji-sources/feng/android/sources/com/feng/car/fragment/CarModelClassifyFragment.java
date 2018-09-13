package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.feng.car.activity.CarPhotoListActivity;
import com.feng.car.activity.VehicleClassDetailActivity;
import com.feng.car.adapter.CarModelListAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.CarModelInfoList;
import com.feng.car.event.CarModelChangeImageEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CarModelClassifyFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private CarModelListAdapter mAdapter;
    private int mCarModelId = 0;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CarModelInfoList mList = new CarModelInfoList();
    private String mYear = "";

    public static CarModelClassifyFragment newInstance(int carModelId, String year, String jsonData) {
        Bundle args = new Bundle();
        args.putInt(HttpConstant.CARX_ID, carModelId);
        args.putString(HttpConstant.YEAR, year);
        args.putString(FengConstant.DATA_JSON, jsonData);
        CarModelClassifyFragment classifyFragment = new CarModelClassifyFragment();
        classifyFragment.setArguments(args);
        return classifyFragment;
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mYear = getArguments().getString(HttpConstant.YEAR, "");
        this.mCarModelId = getArguments().getInt(HttpConstant.CARX_ID, 0);
        List<CarModelInfo> list = JsonUtil.fromJson(getArguments().getString(FengConstant.DATA_JSON, ""), new TypeToken<ArrayList<CarModelInfo>>() {
        });
        if (list == null) {
            list = new ArrayList();
        }
        carsModelGroup(list);
    }

    private void carsModelGroup(List<CarModelInfo> list) {
        Map<String, List<CarModelInfo>> map = new LinkedHashMap();
        for (CarModelInfo info : list) {
            if (map.containsKey(info.engine)) {
                ((List) map.get(info.engine)).add(info);
            } else {
                List<CarModelInfo> carModelInfos = new ArrayList();
                carModelInfos.add(info);
                map.put(info.engine, carModelInfos);
            }
        }
        for (String key : map.keySet()) {
            this.mList.addAll((List) map.get(key));
        }
        setFlagsInCarModel();
    }

    private void setFlagsInCarModel() {
        int i = 0;
        while (i < this.mList.size()) {
            if (!(i == this.mList.size() - 1 || this.mList.get(i).engine.equals(this.mList.get(i + 1).engine))) {
                this.mList.get(i).poslastflag = 1;
            }
            if (!(i == 0 || this.mList.get(i).engine.equals(this.mList.get(i - 1).engine))) {
                this.mList.get(i).posfirstflag = 1;
            }
            i++;
        }
        if (this.mList.size() > 0) {
            this.mList.get(0).posfirstflag = 1;
            this.mList.get(this.mList.size() - 1).poslastflag = 1;
        }
    }

    protected void initView() {
        this.mAdapter = new CarModelListAdapter(this.mActivity, this.mCarModelId, this.mList.getCarModelInfoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        int pos = this.mList.getPosition(this.mCarModelId);
        if (pos >= 0) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(pos);
        }
    }

    public void checkCarModelId(int carModelId) {
        if (this.mAdapter != null && this.mList.size() > 0 && this.mCarModelId != carModelId) {
            this.mAdapter.setCarModelID(carModelId);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CarModelChangeImageEvent event) {
        if (((!(this.mActivity instanceof CarPhotoListActivity) || event.type != CarModelChangeImageEvent.TYPE_CAR_IMAGE_LSIT) && (!(this.mActivity instanceof VehicleClassDetailActivity) || event.type != CarModelChangeImageEvent.TYPE_CAR_DEALER)) || !isAdded() || this.mAdapter == null) {
            return;
        }
        if (this.mYear.equals(event.info.year)) {
            if (this.mCarModelId != event.info.id) {
                this.mAdapter.setCarModelID(event.info.id);
                this.mCarModelId = event.info.id;
                this.mAdapter.notifyDataSetChanged();
            }
        } else if (this.mCarModelId != 0) {
            this.mAdapter.setCarModelID(0);
            this.mCarModelId = 0;
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
