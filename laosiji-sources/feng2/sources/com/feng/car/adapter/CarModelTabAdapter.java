package com.feng.car.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.fragment.CarModelClassifyFragment;
import com.feng.car.utils.JsonUtil;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CarModelTabAdapter extends FragmentPagerAdapter {
    private int mCarModelID = 0;
    private Map<Integer, CarModelClassifyFragment> mFragmentMap;
    private List<String> mList;
    private Map<String, List<CarModelInfo>> mMap;

    public CarModelTabAdapter(FragmentManager fm, int carModelID, List<String> list, Map<String, List<CarModelInfo>> map) {
        super(fm);
        this.mMap = map;
        this.mList = list;
        this.mFragmentMap = new LinkedHashMap();
        this.mCarModelID = carModelID;
    }

    public int getCount() {
        return this.mMap.size();
    }

    public Fragment getItem(int position) {
        CarModelClassifyFragment fragment = CarModelClassifyFragment.newInstance(this.mCarModelID, (String) this.mList.get(position), JsonUtil.toJson((List) this.mMap.get(this.mList.get(position))));
        this.mFragmentMap.put(Integer.valueOf(position), fragment);
        return fragment;
    }

    public CarModelClassifyFragment getCurrentItem(int position) {
        if (this.mFragmentMap.containsKey(Integer.valueOf(position))) {
            return (CarModelClassifyFragment) this.mFragmentMap.get(Integer.valueOf(position));
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {
        return null;
    }
}
