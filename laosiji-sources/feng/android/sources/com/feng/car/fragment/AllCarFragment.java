package com.feng.car.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.feng.car.adapter.AllCarAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.car.RecommendCarxInfo;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class AllCarFragment extends BaseFragment<CommonRecyclerviewBinding> {
    private AllCarAdapter mAdapter;
    private int mCarSeriesID = 0;
    private String mCarSeriesName;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<RecommendCarxInfo> mList = new ArrayList();

    public static AllCarFragment newInstance(int carSeriesID, String carSeriesName, String jsonData) {
        Bundle args = new Bundle();
        args.putInt(HttpConstant.CARS_ID, carSeriesID);
        args.putString("name", carSeriesName);
        args.putString(FengConstant.DATA_JSON, jsonData);
        AllCarFragment allCarFragment = new AllCarFragment();
        allCarFragment.setArguments(args);
        return allCarFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mCarSeriesID = getArguments().getInt(HttpConstant.CARS_ID, 0);
        this.mCarSeriesName = getArguments().getString("name", "");
        try {
            List<RecommendCarxInfo> list = JsonUtil.fromJson(getArguments().getString(FengConstant.DATA_JSON, ""), new TypeToken<ArrayList<RecommendCarxInfo>>() {
            });
            if (list != null) {
                carsModelGroup(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResume() {
        super.onResume();
        if (this.mAdapter != null) {
            this.mAdapter.refreshLocal();
        }
    }

    public void backTop() {
        if (isAdded() && this.mBind != null) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.scrollToPosition(0);
        }
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser && this.mAdapter != null) {
            this.mAdapter.refreshLocal();
        }
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        this.mAdapter = new AllCarAdapter(this.mActivity, this.mCarSeriesID, this.mCarSeriesName, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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

    private void carsModelGroup(List<RecommendCarxInfo> list) {
        this.mList.clear();
        Map<String, List<RecommendCarxInfo>> map = new LinkedHashMap();
        Map<String, String> mapKey = new LinkedHashMap();
        for (RecommendCarxInfo info : list) {
            if (info.carx.state != 40 || !info.carx.getGuidePrice().equals("暂无")) {
                if (!(info.carx.state == 30 || info.carx.state == 40)) {
                    mapKey.put(info.carx.engine, "");
                }
                if (map.containsKey(info.carx.engine)) {
                    ((List) map.get(info.carx.engine)).add(info);
                } else {
                    List<RecommendCarxInfo> carxInfoList = new ArrayList();
                    carxInfoList.add(info);
                    map.put(info.carx.engine, carxInfoList);
                }
            }
        }
        List<RecommendCarxInfo> listStop = new ArrayList();
        for (String key : map.keySet()) {
            if (mapKey.containsKey(key)) {
                this.mList.addAll((Collection) map.get(key));
            } else {
                listStop.addAll((Collection) map.get(key));
            }
        }
        this.mList.addAll(listStop);
        setFlagsInCarModel();
        this.mAdapter.refreshLocal();
    }

    private void setFlagsInCarModel() {
        int i = 0;
        while (i < this.mList.size()) {
            if (!(i == this.mList.size() - 1 || ((RecommendCarxInfo) this.mList.get(i)).carx.engine.equals(((RecommendCarxInfo) this.mList.get(i + 1)).carx.engine))) {
                ((RecommendCarxInfo) this.mList.get(i)).carx.poslastflag = 1;
            }
            if (!(i == 0 || ((RecommendCarxInfo) this.mList.get(i)).carx.engine.equals(((RecommendCarxInfo) this.mList.get(i - 1)).carx.engine))) {
                ((RecommendCarxInfo) this.mList.get(i)).carx.posfirstflag = 1;
            }
            i++;
        }
        if (this.mList.size() > 0) {
            ((RecommendCarxInfo) this.mList.get(0)).carx.posfirstflag = 1;
            ((RecommendCarxInfo) this.mList.get(this.mList.size() - 1)).carx.poslastflag = 1;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }
}
