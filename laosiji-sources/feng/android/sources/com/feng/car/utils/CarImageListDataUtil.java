package com.feng.car.utils;

import com.feng.car.entity.car.CarImageInfo;
import java.util.ArrayList;
import java.util.List;

public class CarImageListDataUtil {
    private static CarImageListDataUtil mInstance;
    private List<CarImageInfo> mSelectData;

    public static CarImageListDataUtil getInstance() {
        if (mInstance == null) {
            mInstance = new CarImageListDataUtil();
        }
        return mInstance;
    }

    public void initData(List<CarImageInfo> selectData) {
        this.mSelectData = selectData;
    }

    public List<CarImageInfo> getSelectData() {
        if (this.mSelectData == null) {
            this.mSelectData = new ArrayList();
        }
        return this.mSelectData;
    }

    public void release() {
        this.mSelectData = null;
    }
}
