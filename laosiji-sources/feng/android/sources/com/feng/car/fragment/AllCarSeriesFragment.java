package com.feng.car.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import com.feng.car.adapter.CarSeriesListAdapter;
import com.feng.car.databinding.CarSeriesPopWinBinding;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.car.CarSeriesInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengConstant;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class AllCarSeriesFragment extends BaseFragment<CommonRecyclerviewBinding> {
    public static final int CAR_IN_NOT_SALE_TYPE = 1;
    public static final int CAR_IN_SALE_TYPE = 0;
    private CarSeriesListAdapter mAdapter;
    private String mBrandImageUrl;
    private String mBrandName;
    private boolean mIsUploadPrice = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CarSeriesInfoList mList = new CarSeriesInfoList();
    private int mSelType;
    private int mType = 0;

    public void setIsUploadPrice(boolean flag) {
        this.mIsUploadPrice = flag;
    }

    public static AllCarSeriesFragment newInstance(int type, int selType, String brandName, String brandImageUrl, String jsonData) {
        Bundle args = new Bundle();
        args.putInt(FengConstant.FENGTYPE, type);
        args.putInt("sel_type", selType);
        args.putString("name", brandName);
        args.putString("url", brandImageUrl);
        args.putString(FengConstant.DATA_JSON, jsonData);
        AllCarSeriesFragment allCarFragment = new AllCarSeriesFragment();
        allCarFragment.setArguments(args);
        return allCarFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mType = getArguments().getInt(FengConstant.FENGTYPE, 0);
        this.mSelType = getArguments().getInt("sel_type", 0);
        this.mBrandName = getArguments().getString("name", "");
        this.mBrandImageUrl = getArguments().getString("url", "");
        String jsonData = getArguments().getString(FengConstant.DATA_JSON, "");
        try {
            BaseListModel<CarSeriesInfo> baseListModel = new BaseListModel();
            baseListModel.parser(CarSeriesInfo.class, new JSONObject(jsonData));
            List<CarSeriesInfo> list = new ArrayList();
            if (this.mType != 0) {
                list = baseListModel.list1;
            } else if (this.mIsUploadPrice) {
                for (CarSeriesInfo info : baseListModel.list) {
                    if (info.state == 20 || info.state == 30) {
                        list.add(info);
                    }
                }
            } else {
                list = baseListModel.list;
            }
            if (list != null) {
                this.mList.clear();
                this.mList.addAll(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        carsModelGroup();
    }

    protected int setLayoutId() {
        return 2130903204;
    }

    protected void initView() {
        this.mAdapter = new CarSeriesListAdapter(this.mActivity, this.mSelType, this.mList.getCarSeriesInfoList());
        this.mAdapter.setNeedBrand(true);
        if (this.mIsUploadPrice) {
            this.mAdapter.setIsUploadPrice(this.mIsUploadPrice);
        }
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new LinearLayoutManager(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        CarSeriesPopWinBinding carSeriesHeadBinding = CarSeriesPopWinBinding.inflate(this.mInflater, ((CommonRecyclerviewBinding) this.mBind).recyclerview, false);
        this.mLRecyclerViewAdapter.addHeaderView(carSeriesHeadBinding.getRoot());
        carSeriesHeadBinding.afdCarBrandImage.setAutoImageURI(Uri.parse(this.mBrandImageUrl));
        carSeriesHeadBinding.tvCarBrand.setText(this.mBrandName);
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

    private void carsModelGroup() {
        Collections.sort(this.mList.getCarSeriesInfoList(), new Comparator<CarSeriesInfo>() {
            public int compare(CarSeriesInfo lhs, CarSeriesInfo rhs) {
                return new Integer(lhs.factory.factoryPosition).compareTo(new Integer(rhs.factory.factoryPosition));
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }
}
