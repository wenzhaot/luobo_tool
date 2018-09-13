package com.feng.car.utils;

import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.SearchCarResultActivity;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.library.utils.SharedUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.MsgConstant;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.android.agoo.common.AgooConstants;

public class SearchCarManager {
    public static int GUIDE_PRICE_TYPE = 0;
    public static int REAL_PRICE_TYPE = 1;
    private static SearchCarManager mManager;
    private final String PRICETYPE = "pricetype";
    private boolean hasSelection = false;
    private CarBrandInfo mBrandInfo = new CarBrandInfo();
    private int mGroupPosition = -1;
    private SearchCarGroup mLevelGroup;
    private SearchCarBean mLevelUnlimitedBean;
    private List<SearchCarGroup> mList = new ArrayList();
    private int mType = GUIDE_PRICE_TYPE;

    public void setHasSelection(boolean b) {
        this.hasSelection = b;
    }

    public boolean hasSelection() {
        return this.hasSelection;
    }

    public void setPriceType(int type) {
        this.mType = type;
        SharedUtil.putInt(FengApplication.getInstance(), "pricetype", this.mType);
    }

    public int getPriceType() {
        return this.mType;
    }

    public int getGroupPosition() {
        return this.mGroupPosition;
    }

    public void setGroupPosition(int position) {
        this.mGroupPosition = position;
    }

    public static SearchCarManager newInstance() {
        if (mManager == null) {
            mManager = new SearchCarManager();
        }
        return mManager;
    }

    public List<SearchCarGroup> getAllDataList() {
        return this.mList;
    }

    private SearchCarManager() {
        initData();
    }

    private void initData() {
        this.mType = SharedUtil.getInt(FengApplication.getInstance(), "pricetype", GUIDE_PRICE_TYPE);
        initPriceData();
        initLevel();
        initOtherCondition();
        for (int i = 0; i < this.mList.size(); i++) {
            SearchCarGroup group = (SearchCarGroup) this.mList.get(i);
            group.id = i + 1;
            for (int j = 0; j < group.beanList.size(); j++) {
                SearchCarBean bean = (SearchCarBean) group.beanList.get(j);
                bean.id = (group.id * 1000) + ((j + 1) * 10);
                if (bean.subList.size() > 0) {
                    for (int k = 0; k < bean.subList.size(); k++) {
                        ((SearchCarBean) bean.subList.get(k)).id = bean.id + (k + 1);
                    }
                }
            }
        }
    }

    public SearchCarBean getCurrentPriceValue() {
        for (int i = 0; i < ((SearchCarGroup) this.mList.get(0)).beanList.size(); i++) {
            SearchCarBean bean = (SearchCarBean) ((SearchCarGroup) this.mList.get(0)).beanList.get(i);
            if (bean.isChecked) {
                return bean;
            }
        }
        return (SearchCarBean) ((SearchCarGroup) this.mList.get(0)).beanList.get(0);
    }

    public void setPriceUnlimited() {
        for (int i = 0; i < ((SearchCarGroup) this.mList.get(0)).beanList.size(); i++) {
            SearchCarBean bean = (SearchCarBean) ((SearchCarGroup) this.mList.get(0)).beanList.get(i);
            if (bean.name.equals("不限")) {
                bean.isChecked = true;
            } else {
                bean.isChecked = false;
                if (bean.name.equals("自定义")) {
                    bean.value = "";
                    bean.min = 0;
                    bean.max = 0;
                }
            }
        }
    }

    public void setCustomPrice(int min, int max) {
        for (int i = 0; i < ((SearchCarGroup) this.mList.get(0)).beanList.size(); i++) {
            SearchCarBean bean = (SearchCarBean) ((SearchCarGroup) this.mList.get(0)).beanList.get(i);
            if (bean.name.equals("自定义")) {
                bean.isChecked = true;
                bean.min = min;
                bean.max = max;
                bean.value = min + "_" + max;
            } else {
                bean.isChecked = false;
            }
        }
    }

    public List<SearchCarBean> getPriceList() {
        return ((SearchCarGroup) this.mList.get(0)).beanList;
    }

    private void initPriceData() {
        SearchCarGroup mPriceGroup = new SearchCarGroup();
        mPriceGroup.id = 0;
        mPriceGroup.groupName = "价格区间";
        mPriceGroup.isSingleSelect = true;
        mPriceGroup.key = "PRICE";
        mPriceGroup.typeName = "基本参数";
        SearchCarBean bean = new SearchCarBean();
        bean.id = 0;
        bean.position = 0;
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        bean.min = 0;
        bean.max = 0;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 1;
        bean1.name = "5万以内";
        bean1.value = "0_5";
        bean1.min = 0;
        bean1.max = 5;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 2;
        bean2.name = "5-10万";
        bean2.value = "5_10";
        bean2.min = 5;
        bean2.max = 10;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 3;
        bean3.name = "10-15万";
        bean3.value = "10_15";
        bean3.min = 10;
        bean3.max = 15;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 4;
        bean4.name = "15-20万";
        bean4.value = "15_20";
        bean4.min = 15;
        bean4.max = 20;
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 5;
        bean5.name = "20-30万";
        bean5.value = "20_30";
        bean5.min = 20;
        bean5.max = 30;
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 6;
        bean6.name = "30-40万";
        bean6.value = "30_40";
        bean6.min = 30;
        bean6.max = 40;
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 7;
        bean7.name = "40-50万";
        bean7.value = "40_50";
        bean7.min = 40;
        bean7.max = 50;
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 8;
        bean8.name = "50-70万";
        bean8.value = "50_70";
        bean8.min = 50;
        bean8.max = 70;
        SearchCarBean bean9 = new SearchCarBean();
        bean9.id = 9;
        bean9.position = 9;
        bean9.name = "70-100万";
        bean9.value = "70_100";
        bean9.min = 70;
        bean9.max = 100;
        SearchCarBean bean10 = new SearchCarBean();
        bean10.id = 10;
        bean10.position = 10;
        bean10.name = "100万以上";
        bean10.value = "100_0";
        bean10.min = 100;
        bean10.max = 0;
        SearchCarBean bean11 = new SearchCarBean();
        bean11.id = 11;
        bean11.isClearOther = true;
        bean11.position = 11;
        bean11.name = "自定义";
        bean11.value = "";
        bean11.min = 0;
        bean11.max = 0;
        bean11.canClick = true;
        mPriceGroup.beanList.add(bean);
        mPriceGroup.beanList.add(bean1);
        mPriceGroup.beanList.add(bean2);
        mPriceGroup.beanList.add(bean3);
        mPriceGroup.beanList.add(bean4);
        mPriceGroup.beanList.add(bean5);
        mPriceGroup.beanList.add(bean6);
        mPriceGroup.beanList.add(bean7);
        mPriceGroup.beanList.add(bean8);
        mPriceGroup.beanList.add(bean9);
        mPriceGroup.beanList.add(bean10);
        mPriceGroup.beanList.add(bean11);
        this.mList.add(mPriceGroup);
    }

    private void initLevel() {
        this.mLevelGroup = new SearchCarGroup();
        this.mLevelGroup.id = 1;
        this.mLevelGroup.groupName = "级别（可多选）";
        this.mLevelGroup.key = "LEVEL";
        this.mLevelGroup.typeName = "基本参数";
        this.mLevelUnlimitedBean = new SearchCarBean();
        this.mLevelUnlimitedBean.id = 0;
        this.mLevelUnlimitedBean.position = 0;
        this.mLevelUnlimitedBean.name = "不限";
        this.mLevelUnlimitedBean.value = PushConstants.PUSH_TYPE_NOTIFY;
        this.mLevelUnlimitedBean.isChecked = true;
        this.mLevelUnlimitedBean.isClearOther = true;
        this.mLevelGroup.beanList.add(this.mLevelUnlimitedBean);
        initCarLevel();
        initSuvLevel();
        SearchCarBean mpvBean = new SearchCarBean();
        mpvBean.id = 0;
        mpvBean.position = 3;
        mpvBean.name = "MPV";
        mpvBean.value = "3";
        this.mLevelGroup.beanList.add(mpvBean);
        SearchCarBean sportsBean = new SearchCarBean();
        sportsBean.id = 0;
        sportsBean.position = 4;
        sportsBean.name = "跑车";
        sportsBean.value = "4";
        this.mLevelGroup.beanList.add(sportsBean);
        SearchCarBean minivanBean = new SearchCarBean();
        minivanBean.id = 0;
        minivanBean.position = 5;
        minivanBean.name = "微面";
        minivanBean.value = "5";
        this.mLevelGroup.beanList.add(minivanBean);
        SearchCarBean lightBean = new SearchCarBean();
        lightBean.id = 0;
        lightBean.position = 6;
        lightBean.name = "轻客";
        lightBean.value = "6";
        this.mLevelGroup.beanList.add(lightBean);
        initTruck();
        this.mList.add(this.mLevelGroup);
    }

    private void initCarLevel() {
        SearchCarBean carBean = new SearchCarBean();
        carBean.id = 0;
        carBean.position = 1;
        carBean.name = "轿车";
        carBean.value = "";
        carBean.canClick = true;
        SearchCarBean carBean1 = new SearchCarBean();
        carBean1.id = 1;
        carBean1.parentid = 0;
        carBean1.position = 0;
        carBean1.name = "不限";
        carBean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        carBean1.isClearOther = true;
        SearchCarBean carBean2 = new SearchCarBean();
        carBean2.id = 2;
        carBean2.parentid = 0;
        carBean2.position = 1;
        carBean2.name = "微型车";
        carBean2.value = AgooConstants.ACK_BODY_NULL;
        SearchCarBean carBean3 = new SearchCarBean();
        carBean3.id = 3;
        carBean3.parentid = 0;
        carBean3.position = 2;
        carBean3.name = "小型车";
        carBean3.value = AgooConstants.ACK_PACK_NULL;
        SearchCarBean carBean4 = new SearchCarBean();
        carBean4.id = 4;
        carBean4.parentid = 0;
        carBean4.position = 3;
        carBean4.name = "紧凑型车";
        carBean4.value = AgooConstants.ACK_FLAG_NULL;
        SearchCarBean carBean5 = new SearchCarBean();
        carBean5.id = 5;
        carBean5.parentid = 0;
        carBean5.position = 4;
        carBean5.name = "中型车";
        carBean5.value = AgooConstants.ACK_PACK_NOBIND;
        SearchCarBean carBean6 = new SearchCarBean();
        carBean6.id = 6;
        carBean6.parentid = 0;
        carBean6.position = 5;
        carBean6.name = "中大型车";
        carBean6.value = AgooConstants.ACK_PACK_ERROR;
        SearchCarBean carBean7 = new SearchCarBean();
        carBean7.id = 7;
        carBean7.parentid = 0;
        carBean7.position = 6;
        carBean7.name = "大型车";
        carBean7.value = "16";
        carBean.subList.add(carBean1);
        carBean.subList.add(carBean2);
        carBean.subList.add(carBean3);
        carBean.subList.add(carBean4);
        carBean.subList.add(carBean5);
        carBean.subList.add(carBean6);
        carBean.subList.add(carBean7);
        this.mLevelGroup.beanList.add(carBean);
    }

    private void initSuvLevel() {
        SearchCarBean suvBean = new SearchCarBean();
        suvBean.id = 1;
        suvBean.position = 2;
        suvBean.name = "SUV";
        suvBean.value = "";
        suvBean.canClick = true;
        SearchCarBean suvBean1 = new SearchCarBean();
        suvBean1.id = 1;
        suvBean1.parentid = 1;
        suvBean1.position = 0;
        suvBean1.name = "不限";
        suvBean1.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        suvBean1.isClearOther = true;
        SearchCarBean suvBean2 = new SearchCarBean();
        suvBean2.id = 2;
        suvBean2.parentid = 1;
        suvBean2.position = 1;
        suvBean2.name = "小型SUV";
        suvBean2.value = AgooConstants.REPORT_MESSAGE_NULL;
        SearchCarBean suvBean3 = new SearchCarBean();
        suvBean3.id = 3;
        suvBean3.parentid = 1;
        suvBean3.position = 2;
        suvBean3.name = "紧凑型SUV";
        suvBean3.value = AgooConstants.REPORT_ENCRYPT_FAIL;
        SearchCarBean suvBean4 = new SearchCarBean();
        suvBean4.id = 4;
        suvBean4.parentid = 1;
        suvBean4.parentid = 1;
        suvBean4.position = 3;
        suvBean4.name = "中型SUV";
        suvBean4.value = AgooConstants.REPORT_DUPLICATE_FAIL;
        SearchCarBean suvBean5 = new SearchCarBean();
        suvBean5.id = 5;
        suvBean5.parentid = 1;
        suvBean5.position = 4;
        suvBean5.name = "中大型SUV";
        suvBean5.value = AgooConstants.REPORT_NOT_ENCRYPT;
        SearchCarBean suvBean6 = new SearchCarBean();
        suvBean6.id = 6;
        suvBean6.parentid = 1;
        suvBean6.position = 5;
        suvBean6.name = "大型SUV";
        suvBean6.value = "25";
        suvBean.subList.add(suvBean1);
        suvBean.subList.add(suvBean2);
        suvBean.subList.add(suvBean3);
        suvBean.subList.add(suvBean4);
        suvBean.subList.add(suvBean5);
        suvBean.subList.add(suvBean6);
        this.mLevelGroup.beanList.add(suvBean);
    }

    private void initTruck() {
        SearchCarBean truckBean = new SearchCarBean();
        truckBean.id = 7;
        truckBean.position = 7;
        truckBean.name = "卡车";
        truckBean.value = "";
        truckBean.canClick = true;
        SearchCarBean truckBean1 = new SearchCarBean();
        truckBean1.id = 1;
        truckBean1.parentid = 7;
        truckBean1.position = 0;
        truckBean1.name = "不限";
        truckBean1.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        truckBean1.isClearOther = true;
        SearchCarBean truckBean2 = new SearchCarBean();
        truckBean2.id = 2;
        truckBean2.parentid = 7;
        truckBean2.position = 1;
        truckBean2.name = "微卡";
        truckBean2.value = "71";
        SearchCarBean truckBean3 = new SearchCarBean();
        truckBean3.id = 3;
        truckBean3.parentid = 7;
        truckBean3.position = 2;
        truckBean3.name = "皮卡";
        truckBean3.value = "72";
        truckBean.subList.add(truckBean1);
        truckBean.subList.add(truckBean2);
        truckBean.subList.add(truckBean3);
        this.mLevelGroup.beanList.add(truckBean);
    }

    public void setLevelUnlimited() {
        for (int i = 0; i < ((SearchCarGroup) this.mList.get(1)).beanList.size(); i++) {
            SearchCarBean bean = (SearchCarBean) ((SearchCarGroup) this.mList.get(1)).beanList.get(i);
            if (bean.name.equals("不限")) {
                bean.isChecked = true;
            } else {
                bean.isChecked = false;
                if (bean.subList.size() > 0) {
                    for (SearchCarBean b : bean.subList) {
                        b.isChecked = false;
                    }
                }
            }
        }
    }

    public List<SearchCarBean> getLeveList() {
        return ((SearchCarGroup) this.mList.get(1)).beanList;
    }

    public void recoveryLevelList(List<SearchCarBean> list) {
        ((SearchCarGroup) this.mList.get(1)).beanList.clear();
        ((SearchCarGroup) this.mList.get(1)).beanList.addAll(deepCopyBeanList(list));
    }

    public boolean hasLevelCondition() {
        List<SearchCarBean> beanList = ((SearchCarGroup) this.mList.get(1)).beanList.subList(1, ((SearchCarGroup) this.mList.get(1)).beanList.size());
        for (int i = 0; i < beanList.size(); i++) {
            if (((SearchCarBean) beanList.get(i)).hasChecked()) {
                return true;
            }
        }
        return false;
    }

    public void refreshUnlimited() {
        if (hasLevelCondition()) {
            ((SearchCarBean) ((SearchCarGroup) this.mList.get(1)).beanList.get(0)).isChecked = false;
        } else {
            ((SearchCarBean) ((SearchCarGroup) this.mList.get(1)).beanList.get(0)).isChecked = true;
        }
    }

    public List<SearchCarGroup> getOtherConditionList() {
        return this.mList.subList(2, this.mList.size());
    }

    private void initOtherCondition() {
        initCountries();
        initManufacturer();
        initCarStructure();
        initCarWheelbase();
        initCarSeat();
        initCarDoorNum();
        initCarDisplacement();
        initCarHoursePower();
        initCarEnergy();
        initCylinder();
        initIntakeMode();
        initCarOilSupplyWay();
        initCarPureElectricRange();
        initCarBatteryType();
        initCarTransmission();
        initCarGearNum();
        initDriveMode();
        initCarCentralDifferentiaStructure();
        initCarLimitedSlipDifferential();
        initCarSuspensionControl();
        initCarCarBodyStructure();
        initCarManipulationauxiliary();
        initCarPassiveSecuritySettings();
        initCarParkAssist();
        initCarDrivingAuxiliary();
        initCarSecurityConfiguration();
        initCarTirerims();
        initCarSKyLight();
        initCarOpenDoorWay();
        initCarCarBoot();
        initCarSteeringWheel();
        initCarMeter();
        initCarAirConditioningControl();
        initCarSeatMaterial();
        initCarFrontSeat();
        initCarRearSeat();
        initCarCarMonitor();
        initCarMobileChargingWay();
        initCarHeadLightFeatures();
        initCarLampsLightSource();
        initCarGlassConfiguration();
        initCarRearviewMirrorConfiguration();
    }

    public boolean hasTransmissionCondition() {
        SearchCarGroup group = (SearchCarGroup) this.mList.get(16);
        List<SearchCarBean> beanList = group.beanList.subList(1, group.beanList.size());
        for (int i = 0; i < beanList.size(); i++) {
            if (((SearchCarBean) beanList.get(i)).hasChecked()) {
                return true;
            }
        }
        return false;
    }

    public void refreshTransmissionCondition() {
        SearchCarGroup group = (SearchCarGroup) this.mList.get(16);
        if (hasTransmissionCondition()) {
            ((SearchCarBean) group.beanList.get(0)).isChecked = false;
        } else {
            ((SearchCarBean) group.beanList.get(0)).isChecked = true;
        }
    }

    private void initCarRearviewMirrorConfiguration() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "后视镜配置（可多选）";
        group.key = "REARVIEWMIRRORCONFIGURATION";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "玻璃及后视镜配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "后视镜加热";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "电动折叠";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "内自动防眩目";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "外自动防眩目";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarGlassConfiguration() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "玻璃配置（可多选）";
        group.key = "GLASSCONFIGURATION";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "玻璃及后视镜配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "感应雨刷";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "全车玻璃一键升降";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "后排侧遮阳帘";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "后风挡遮阳帘";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarLampsLightSource() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "灯光光源（可多选）";
        group.key = "LAMPSLIGHTSOURCE";
        group.countPerLine = 2;
        group.typeName = "灯光配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "氙气大灯";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "LED大灯";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "激光大灯";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "LED日行灯";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarHeadLightFeatures() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "大灯功能（可多选）";
        group.key = "HEADLIGHTFEATURES";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "灯光配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "自动头灯";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "转向头灯";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "自适应远近光";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "大灯高度调节";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarMobileChargingWay() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "手机连接及充电方式（可多选）";
        group.key = "MOBILECHARGINGWAY";
        group.countPerLine = 2;
        group.typeName = "多媒体配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "蓝牙/车载电话";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "手机互联/映射";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "220V/230V电源";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "手机无线充电";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarCarMonitor() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "车载屏幕及功能（可多选）";
        group.key = "CARMONITOR";
        group.countPerLine = 2;
        group.typeName = "多媒体配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "GPS导航";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "中控彩色大屏";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "车载电视";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "后排液晶屏";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarRearSeat() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "后排座椅（可多选）";
        group.key = "REARSEAT";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "座椅配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "后排座椅加热";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "后排座椅通风";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "靠背调节";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "前后移动";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "后排座椅按摩";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "电动调节";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "比例放倒";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarFrontSeat() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "前排座椅（可多选）";
        group.key = "FRONTSEAT";
        group.countPerLine = 2;
        group.typeName = "座椅配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "驾驶位电动调节";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "副驾驶位电动调节";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "前排座椅加热";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "前排座椅通风";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "座椅记忆";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "前排座椅按摩";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "腰部支撑调节";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarSeatMaterial() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "座椅材质（可多选）";
        group.key = "SEATMATERIAL";
        group.countPerLine = 2;
        group.typeName = "座椅配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "织物座椅";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "皮质座椅";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarAirConditioningControl() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "空调控制（可多选）";
        group.key = "AIRCONDITIONINGCONTROL";
        group.countPerLine = 2;
        group.typeName = "内部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "自动空调";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "温度分区控制";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "后座出风口";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 4;
        bean5.position = 4;
        bean5.name = "后排独立空调";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarMeter() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "仪表（可多选）";
        group.key = "METER";
        group.countPerLine = 2;
        group.typeName = "内部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "全液晶仪表";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 2;
        bean3.position = 2;
        bean3.name = "抬头显示";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarSteeringWheel() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "方向盘（可多选）";
        group.key = "STEERINGWHEEL";
        group.countPerLine = 2;
        group.typeName = "内部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "多功能方向盘";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "方向盘加热";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "方向盘换挡";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "方向盘电动调节";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarCarBoot() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "后备厢（可多选）";
        group.key = "CARBOOT";
        group.countPerLine = 2;
        group.typeName = "外部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "电动后备厢";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "感应后备厢";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarOpenDoorWay() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "车门开闭方式（可多选）";
        group.key = "OPENDOORWAY";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "外部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "手动侧滑门";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "电动侧滑门";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "电动吸合门";
        bean4.value = "3";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        this.mList.add(group);
    }

    private void initCarSKyLight() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "天窗配置（可多选）";
        group.key = "SKYLIGHT";
        group.countPerLine = 2;
        group.typeName = "外部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "电动天窗";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "全景天窗";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarTirerims() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "轮胎/轮圈（可多选）";
        group.key = "TIRERIMS";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "外部配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "胎压监测";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "铝合金轮圈";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "全尺寸备胎";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "缺气保用胎";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarSecurityConfiguration() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "防盗配置（可多选）";
        group.key = "SECURITYCONFIGURATION";
        group.countPerLine = 2;
        group.typeName = "安全配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "无钥匙进入";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "无钥匙启动";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "远程启动";
        bean4.value = "3";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        this.mList.add(group);
    }

    private void initCarDrivingAuxiliary() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "行车辅助（可多选）";
        group.key = "DRIVINGAUXILIARY";
        group.countPerLine = 2;
        group.typeName = "安全配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "定速巡航";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "自适应巡航";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "发动机启停";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "并线辅助";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "车道偏离预警";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "夜视系统";
        bean7.value = "6";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        this.mList.add(group);
    }

    private void initCarParkAssist() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "停车辅助（可多选）";
        group.key = "PARKASSIST";
        group.countPerLine = 2;
        group.typeName = "安全配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "前雷达";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "倒车雷达";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "倒车影像";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "全景摄像头";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "电子手刹";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "自动驻车";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "自动泊车";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarPassiveSecuritySettings() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "被动安全配置（可多选）";
        group.key = "PASSIVESECURITYSETTINGS";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "安全配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "前排侧气囊";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "后排侧气囊";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 5;
        bean4.position = 4;
        bean4.name = "前排头部气囊";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 6;
        bean5.position = 5;
        bean5.name = "后排头部气囊";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 7;
        bean6.position = 6;
        bean6.name = "ISOFIX";
        bean6.value = "5";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        this.mList.add(group);
    }

    private void initCarManipulationauxiliary() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "操控辅助（可多选）";
        group.key = "MANIPULATIONAUXILIARY";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "安全配置";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "ESP稳定系统";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "牵引力控制";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "上坡辅助";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "陡坡缓降";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "可变转向比";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "主动刹车";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "整体主动转向";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarCarBodyStructure() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "车体结构（可多选）";
        group.key = "CARBODYSTRUCTURE";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "底盘制动";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "承载式车身";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "非承载式车身";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarSuspensionControl() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "悬架调节（可多选）";
        group.key = "SUSPENSIONCONTROL";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "底盘制动";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "高低";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 2;
        bean3.position = 1;
        bean3.name = "软硬";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCarLimitedSlipDifferential() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "限滑差速器/锁（可多选）";
        group.key = "LIMITEDSLIPDIFFERENTIAL";
        group.countPerLine = 2;
        group.typeName = "底盘制动";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "前桥";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 2;
        bean3.position = 1;
        bean3.name = "后桥";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 2;
        bean4.position = 1;
        bean4.name = "中央差速器锁止";
        bean4.value = "3";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        this.mList.add(group);
    }

    private void initCarCentralDifferentiaStructure() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "中央差速器结构（可多选）";
        group.key = "CENTRALDIFFERENTIASTRUCTURE";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "底盘制动";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "托森";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "多片离合器";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "冠状齿轮";
        bean4.value = "3";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        this.mList.add(group);
    }

    private void initCarGearNum() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "挡位数（可多选）";
        group.key = "GEARNUM";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "变速箱";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "4挡";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "5挡";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "6挡";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "7挡";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "8挡";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "9挡及以上";
        bean7.value = "6";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        this.mList.add(group);
    }

    private void initCarBatteryType() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "动力电池类型（可多选）";
        group.key = "BATTERYTYPE";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "动力参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "三元锂电";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "锂离子";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "磷酸铁锂";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "镍氢";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarPureElectricRange() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "纯电续航里程（可多选）";
        group.key = "PUREELECTRICRANGE";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "动力参数";
        group.countPerLine = 2;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "200公里及以下";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "201-300公里";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "301-400公里";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "400公里以上";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarOilSupplyWay() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "供油方式（可多选）";
        group.key = "OILSUPPLYWAY";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "动力参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "直喷";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "多点电喷";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "混合喷射";
        bean4.value = "3";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        this.mList.add(group);
    }

    private void initCarHoursePower() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "马力（可多选）";
        group.key = "HOURSEPOWER";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "动力参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "100及以下";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 2;
        bean3.position = 2;
        bean3.name = "101-150";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 3;
        bean4.position = 3;
        bean4.name = "151-200";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 4;
        bean5.position = 4;
        bean5.name = "201-250";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 5;
        bean6.position = 5;
        bean6.name = "251-300";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 6;
        bean7.position = 6;
        bean7.name = "301-400";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 7;
        bean8.position = 7;
        bean8.name = "400以上";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarDoorNum() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "车门数（可多选）";
        group.key = "DOORNUM";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.typeName = "车身参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "2门";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 2;
        bean3.position = 2;
        bean3.name = "3门";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 3;
        bean4.position = 3;
        bean4.name = "4门";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 4;
        bean5.position = 4;
        bean5.name = "5门及以上";
        bean5.value = "4";
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCarWheelbase() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "轴距（可多选）";
        group.key = "WHEELBASE";
        group.hasExpendTr = true;
        group.isTakeUp = true;
        group.countPerLine = 2;
        group.typeName = "车身参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "2500mm及以下";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "2501-2600mm";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "2601-2700mm";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "2701-2800mm";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "2801-2900mm";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "2901-3000mm";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "3000mm以上";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarStructure() {
        SearchCarGroup group = new SearchCarGroup();
        group.id = 0;
        group.groupName = "车身结构（可多选）";
        group.key = "CARSTRUCT";
        group.typeName = "车身参数";
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 1;
        bean1.position = 0;
        bean1.name = "不限";
        bean1.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean1.isChecked = true;
        bean1.isClearOther = true;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 2;
        bean2.position = 1;
        bean2.name = "两厢";
        bean2.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.id = 3;
        bean3.position = 2;
        bean3.name = "三厢";
        bean3.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean4 = new SearchCarBean();
        bean4.id = 4;
        bean4.position = 3;
        bean4.name = "掀背";
        bean4.value = "3";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.id = 5;
        bean5.position = 4;
        bean5.name = "旅行车";
        bean5.value = "4";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.id = 6;
        bean6.position = 5;
        bean6.name = "硬顶敞篷";
        bean6.value = "5";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.id = 7;
        bean7.position = 6;
        bean7.name = "软顶敞篷";
        bean7.value = "6";
        SearchCarBean bean8 = new SearchCarBean();
        bean8.id = 8;
        bean8.position = 7;
        bean8.name = "硬顶跑车";
        bean8.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initCarTransmission() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "变速箱类型（可多选）";
        group.key = "GEARBOX";
        group.typeName = "变速箱";
        SearchCarBean bean = new SearchCarBean();
        bean.id = 0;
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.id = 0;
        bean1.name = "手动";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.id = 0;
        bean2.name = "自动";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        bean2.canClick = true;
        bean2.countPerLine = 2;
        SearchCarBean autoBean1 = new SearchCarBean();
        autoBean1.id = 1;
        autoBean1.position = 0;
        autoBean1.name = "不限";
        autoBean1.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        autoBean1.isClearOther = true;
        SearchCarBean autoBean2 = new SearchCarBean();
        autoBean2.id = 2;
        autoBean2.position = 1;
        autoBean2.name = "自动（AT）";
        autoBean2.value = "3";
        SearchCarBean autoBean3 = new SearchCarBean();
        autoBean3.id = 3;
        autoBean3.position = 2;
        autoBean3.name = "手自一体";
        autoBean3.value = "4";
        SearchCarBean autoBean4 = new SearchCarBean();
        autoBean4.id = 4;
        autoBean4.position = 3;
        autoBean4.name = "无级变速";
        autoBean4.value = "5";
        SearchCarBean autoBean5 = new SearchCarBean();
        autoBean5.id = 5;
        autoBean5.position = 4;
        autoBean5.name = "电子无级变速";
        autoBean5.value = "6";
        SearchCarBean autoBean6 = new SearchCarBean();
        autoBean6.id = 6;
        autoBean6.position = 5;
        autoBean6.name = "双离合";
        autoBean6.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        SearchCarBean autoBean7 = new SearchCarBean();
        autoBean7.id = 7;
        autoBean7.position = 6;
        autoBean7.name = "机械式自动";
        autoBean7.value = "8";
        SearchCarBean autoBean8 = new SearchCarBean();
        autoBean8.id = 8;
        autoBean8.position = 7;
        autoBean8.name = "固定齿比";
        autoBean8.value = "9";
        SearchCarBean autoBean9 = new SearchCarBean();
        autoBean9.id = 9;
        autoBean9.position = 8;
        autoBean9.name = "ISR变速箱";
        autoBean9.value = AgooConstants.ACK_REMOVE_PACKAGE;
        bean2.subList.add(autoBean1);
        bean2.subList.add(autoBean2);
        bean2.subList.add(autoBean3);
        bean2.subList.add(autoBean4);
        bean2.subList.add(autoBean5);
        bean2.subList.add(autoBean6);
        bean2.subList.add(autoBean7);
        bean2.subList.add(autoBean8);
        bean2.subList.add(autoBean9);
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        this.mList.add(group);
    }

    private void initCarEnergy() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "能源（可多选）";
        group.key = "ENERGY";
        group.typeName = "动力参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "汽油";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "柴油";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "纯电动";
        bean3.value = "3";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "油电混合";
        bean4.value = "4";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "插电式混动";
        bean5.value = "5";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.name = "增程式";
        bean6.value = "6";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        this.mList.add(group);
    }

    private void initCarDisplacement() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "排量（可多选）";
        group.key = "DISPLACEMENT";
        group.typeName = "动力参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "1.0L及以下";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "1.1L-1.6L";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "1.7L-2.0L";
        bean3.value = "3";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "2.1L-2.5L";
        bean4.value = "4";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "2.6L-3.0L";
        bean5.value = "5";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.name = "3.1L-4.0L";
        bean6.value = "6";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.name = "4.0L以上";
        bean7.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        this.mList.add(group);
    }

    private void initCarSeat() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "座位数（可多选）";
        group.typeName = "车身参数";
        group.key = "SEATING";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "2座";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "4座";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "5座";
        bean3.value = "3";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "6座";
        bean4.value = "4";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "7座";
        bean5.value = "5";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.name = "7座以上";
        bean6.value = "6";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        this.mList.add(group);
    }

    private void initManufacturer() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "生产厂商（可多选）";
        group.key = "FIRM";
        group.typeName = "基本参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "自主";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "合资";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "进口";
        bean3.value = "3";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    private void initCountries() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "国别（可多选）";
        group.key = "COUNTRY";
        group.typeName = "基本参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "中国";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "德国";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "日本";
        bean3.value = "3";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "美国";
        bean4.value = "4";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "韩国";
        bean5.value = "5";
        SearchCarBean bean6 = new SearchCarBean();
        bean6.name = "法国";
        bean6.value = "6";
        SearchCarBean bean7 = new SearchCarBean();
        bean7.name = "英国";
        bean7.value = MsgConstant.MESSAGE_NOTIFY_ARRIVAL;
        SearchCarBean bean8 = new SearchCarBean();
        bean8.name = "其他";
        bean8.value = "8";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        group.beanList.add(bean6);
        group.beanList.add(bean7);
        group.beanList.add(bean8);
        this.mList.add(group);
    }

    private void initDriveMode() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "驱动方式（可多选）";
        group.key = "DRIVE";
        group.typeName = "底盘制动";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "前驱";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "后驱";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "全时四驱";
        bean3.value = "4";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "适时四驱";
        bean4.value = "5";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "分时四驱";
        bean5.value = "6";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initCylinder() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "气缸数（可多选）";
        group.key = "CYLINDER";
        group.typeName = "动力参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "3缸";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "4缸";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "5缸";
        bean3.value = "3";
        SearchCarBean bean4 = new SearchCarBean();
        bean4.name = "6缸";
        bean4.value = "4";
        SearchCarBean bean5 = new SearchCarBean();
        bean5.name = "8缸及以上";
        bean5.value = "8";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        group.beanList.add(bean4);
        group.beanList.add(bean5);
        this.mList.add(group);
    }

    private void initIntakeMode() {
        SearchCarGroup group = new SearchCarGroup();
        group.groupName = "进气方式（可多选）";
        group.key = "AIR";
        group.typeName = "动力参数";
        SearchCarBean bean = new SearchCarBean();
        bean.name = "不限";
        bean.value = PushConstants.PUSH_TYPE_NOTIFY;
        bean.isChecked = true;
        bean.isClearOther = true;
        SearchCarBean bean1 = new SearchCarBean();
        bean1.name = "自然吸气";
        bean1.value = PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        SearchCarBean bean2 = new SearchCarBean();
        bean2.name = "涡轮增压";
        bean2.value = PushConstants.PUSH_TYPE_UPLOAD_LOG;
        SearchCarBean bean3 = new SearchCarBean();
        bean3.name = "机械增压";
        bean3.value = "3";
        group.beanList.add(bean);
        group.beanList.add(bean1);
        group.beanList.add(bean2);
        group.beanList.add(bean3);
        this.mList.add(group);
    }

    public void setOtherUnlimited() {
        List<SearchCarGroup> list = this.mList.subList(2, this.mList.size());
        for (int i = 0; i < list.size(); i++) {
            SearchCarGroup group = (SearchCarGroup) list.get(i);
            for (int j = 0; j < group.beanList.size(); j++) {
                SearchCarBean bean = (SearchCarBean) group.beanList.get(j);
                if (bean.name.equals("不限")) {
                    bean.isChecked = true;
                } else {
                    bean.isChecked = false;
                }
            }
        }
    }

    public boolean hasOtherConditionSelect() {
        List<SearchCarGroup> list = this.mList.subList(2, this.mList.size());
        for (int i = 0; i < list.size(); i++) {
            SearchCarGroup group = (SearchCarGroup) list.get(i);
            for (int j = 1; j < group.beanList.size(); j++) {
                if (((SearchCarBean) group.beanList.get(j)).isChecked) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<SearchCarBean> deepCopyBeanList(List<SearchCarBean> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            List<SearchCarBean> dest = (List) in.readObject();
            out.close();
            byteOut.close();
            in.close();
            byteIn.close();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public List<SearchCarGroup> deepCopyGroupList(List<SearchCarGroup> src) {
        try {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(src);
            ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
            ObjectInputStream in = new ObjectInputStream(byteIn);
            List<SearchCarGroup> dest = (List) in.readObject();
            out.close();
            byteOut.close();
            byteIn.close();
            in.close();
            return dest;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList();
        }
    }

    public int getBrandId() {
        return this.mBrandInfo.id;
    }

    public void setCurrentBrand(CarBrandInfo info) {
        this.mBrandInfo.id = info.id;
        this.mBrandInfo.name.set(info.name.get());
        this.mBrandInfo.image.width = info.image.width;
        this.mBrandInfo.image.height = info.image.height;
        this.mBrandInfo.image.url = info.image.url;
    }

    public CarBrandInfo getCurrentBrandInfo() {
        return this.mBrandInfo;
    }

    public void clearBrandData() {
        this.mBrandInfo.id = 0;
        this.mBrandInfo.name.set("");
    }

    public void clearAllCondition() {
        this.mGroupPosition = -1;
        this.mList.clear();
        this.hasSelection = false;
        initData();
        clearBrandData();
    }

    public void clearAllConditionExceptBrand() {
        this.mList.clear();
        this.hasSelection = false;
        initData();
    }

    public String getCurrentPriceValueString() {
        StringBuilder sb = new StringBuilder();
        SearchCarBean priceBean = getCurrentPriceValue();
        if (priceBean.name.equals("自定义")) {
            sb.append(priceBean.min);
            sb.append("_");
            sb.append(priceBean.max);
        } else {
            sb.append(priceBean.value);
        }
        return sb.toString();
    }

    public String getAllConditionData() {
        int i;
        SearchCarBean bean;
        SearchCarBean b;
        Object map = new HashMap();
        StringBuilder priceSb = new StringBuilder();
        SearchCarBean priceBean = getCurrentPriceValue();
        if (priceBean.name.equals("自定义")) {
            priceSb.append(priceBean.min);
            priceSb.append("_");
            priceSb.append(priceBean.max);
        } else {
            priceSb.append(priceBean.value);
        }
        if (this.mType == GUIDE_PRICE_TYPE && !PushConstants.PUSH_TYPE_NOTIFY.equals(priceSb.toString())) {
            map.put("PRICE", priceSb.toString());
        }
        StringBuilder levelSb = new StringBuilder();
        int size = ((SearchCarGroup) this.mList.get(1)).beanList.size();
        List<SearchCarBean> levelList = ((SearchCarGroup) this.mList.get(1)).beanList;
        for (i = 0; i < size; i++) {
            bean = (SearchCarBean) levelList.get(i);
            if (bean.subList.size() > 0) {
                for (SearchCarBean b2 : bean.subList) {
                    if (b2.isChecked) {
                        levelSb.append(b2.value);
                        levelSb.append("_");
                    }
                }
            } else if (bean.isChecked) {
                levelSb.append(bean.value);
                levelSb.append("_");
            }
            if (i == size - 1 && levelSb.length() > 0) {
                levelSb.replace(levelSb.length() - 1, levelSb.length(), "");
            }
        }
        if (!PushConstants.PUSH_TYPE_NOTIFY.equals(levelSb.toString())) {
            map.put("LEVEL", levelSb.toString());
        }
        List<SearchCarGroup> list = this.mList.subList(2, this.mList.size());
        for (i = 0; i < list.size(); i++) {
            SearchCarGroup group = (SearchCarGroup) list.get(i);
            StringBuilder sb = new StringBuilder();
            List<SearchCarBean> beanList = group.beanList;
            for (int j = 0; j < beanList.size(); j++) {
                bean = (SearchCarBean) beanList.get(j);
                if (bean.isChecked) {
                    sb.append(bean.value);
                    sb.append("_");
                } else if (bean.subList.size() > 0) {
                    for (int k = 0; k < bean.subList.size(); k++) {
                        b2 = (SearchCarBean) bean.subList.get(k);
                        if (b2.isChecked) {
                            sb.append(b2.value);
                            sb.append("_");
                        }
                    }
                }
                if (j == beanList.size() - 1 && sb.length() > 0) {
                    sb.replace(sb.length() - 1, sb.length(), "");
                }
            }
            if (!PushConstants.PUSH_TYPE_NOTIFY.equals(sb.toString())) {
                map.put(group.key, sb.toString());
            }
        }
        return JsonUtil.toJson(map);
    }

    public void recoveryAllConditionList(List<SearchCarGroup> list) {
        this.mList.clear();
        this.mList.addAll(deepCopyGroupList(list));
    }

    public boolean isNeedOpenResultActivity() {
        List<BaseActivity> list = ActivityManager.getInstance().getActitysList();
        for (int i = list.size() - 1; i > 0; i--) {
            if (list.get(i) instanceof SearchCarResultActivity) {
                return false;
            }
        }
        return true;
    }

    public boolean hasPriceCondition() {
        if (getCurrentPriceValue().value.equals(PushConstants.PUSH_TYPE_NOTIFY)) {
            return false;
        }
        return true;
    }

    public boolean hasOtherCondition() {
        List<SearchCarGroup> list = this.mList.subList(2, this.mList.size());
        for (int i = 0; i < list.size(); i++) {
            List<SearchCarBean> beanList = ((SearchCarGroup) list.get(i)).beanList;
            for (int j = 0; j < beanList.size(); j++) {
                SearchCarBean bean = (SearchCarBean) beanList.get(j);
                if (bean.isChecked && !bean.name.equals("不限")) {
                    return true;
                }
                if (bean.subList.size() > 0) {
                    for (int k = 0; k < bean.subList.size(); k++) {
                        SearchCarBean b = (SearchCarBean) bean.subList.get(k);
                        if (b.isChecked && !b.name.equals("不限")) {
                            return true;
                        }
                    }
                    continue;
                }
            }
        }
        return false;
    }

    public List<SearchCarBean> getAllSelectConditionList() {
        SearchCarBean bean;
        int i;
        SearchCarBean b;
        List<SearchCarBean> list = new ArrayList();
        if (!(this.mBrandInfo == null || this.mBrandInfo.id == 0)) {
            bean = new SearchCarBean();
            bean.id = this.mBrandInfo.id;
            bean.name = (String) this.mBrandInfo.name.get();
            bean.value = "-1";
            list.add(bean);
        }
        SearchCarBean priceBean = getCurrentPriceValue();
        if (!PushConstants.PUSH_TYPE_NOTIFY.equals(priceBean.value)) {
            list.add(priceBean);
        }
        if (hasLevelCondition()) {
            List<SearchCarBean> levelList = ((SearchCarGroup) this.mList.get(1)).beanList;
            for (i = 0; i < levelList.size(); i++) {
                bean = (SearchCarBean) levelList.get(i);
                if (bean.subList.size() > 0) {
                    for (SearchCarBean b2 : bean.subList) {
                        if (b2.isChecked) {
                            if (b2.name.equals("不限")) {
                                list.add(bean);
                            } else {
                                list.add(b2);
                            }
                        }
                    }
                } else if (bean.isChecked) {
                    list.add(bean);
                }
            }
        }
        List<SearchCarGroup> otherList = this.mList.subList(2, this.mList.size());
        for (i = 0; i < otherList.size(); i++) {
            List<SearchCarBean> beanList = ((SearchCarGroup) otherList.get(i)).beanList;
            for (int j = 0; j < beanList.size(); j++) {
                bean = (SearchCarBean) beanList.get(j);
                if (bean.isChecked) {
                    if (!PushConstants.PUSH_TYPE_NOTIFY.equals(bean.value)) {
                        list.add(bean);
                    }
                } else if (bean.subList.size() > 0) {
                    for (int k = 0; k < bean.subList.size(); k++) {
                        b2 = (SearchCarBean) bean.subList.get(k);
                        if (b2.isChecked) {
                            if ("不限".equals(b2.name)) {
                                list.add(bean);
                            } else {
                                list.add(b2);
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    public void clearSelectCondition(SearchCarBean searchCarBean) {
        if (searchCarBean.value.equals("-1")) {
            clearBrandData();
            return;
        }
        for (SearchCarGroup group : this.mList) {
            List<SearchCarBean> list = group.beanList;
            int i = 0;
            while (i < list.size()) {
                SearchCarBean bean = (SearchCarBean) list.get(i);
                int j;
                if (bean.id == searchCarBean.id) {
                    if (bean.subList.size() > 0) {
                        for (j = 0; j < bean.subList.size(); j++) {
                            ((SearchCarBean) bean.subList.get(j)).isChecked = false;
                        }
                    } else {
                        bean.isChecked = false;
                    }
                    reverseSelection(list);
                } else {
                    if (bean.subList.size() > 0) {
                        for (j = 0; j < bean.subList.size(); j++) {
                            SearchCarBean b = (SearchCarBean) bean.subList.get(j);
                            if (b.id == searchCarBean.id) {
                                b.isChecked = false;
                                reverseSelection(list);
                                break;
                            }
                        }
                    }
                    i++;
                }
            }
        }
    }

    private void reverseSelection(List<SearchCarBean> list) {
        boolean flag = true;
        for (int i = 0; i < list.size(); i++) {
            SearchCarBean bean = (SearchCarBean) list.get(i);
            if (bean.subList.size() > 0) {
                for (int j = 0; j < bean.subList.size(); j++) {
                    if (((SearchCarBean) bean.subList.get(j)).isChecked) {
                        flag = false;
                        break;
                    }
                }
            } else if (bean.isChecked) {
                flag = false;
                break;
            }
        }
        if (flag) {
            ((SearchCarBean) list.get(0)).isChecked = true;
        }
    }

    public List<SearchCarGroup> getSearchGroupList(String key, List<String> keyList) {
        List<SearchCarGroup> groupList = new ArrayList();
        String lowerKey = key.toLowerCase();
        String upperKey = key.toUpperCase();
        for (SearchCarGroup group : this.mList) {
            SearchCarGroup newGroup = group.creatGroupWithoutList();
            if (newGroup.groupName.contains(key) || keyList.contains(newGroup.groupName) || newGroup.groupName.contains(lowerKey) || newGroup.groupName.contains(upperKey)) {
                newGroup.searchBeanList.addAll(group.beanList);
                groupList.add(newGroup);
            } else {
                for (int i = 0; i < group.beanList.size(); i++) {
                    SearchCarBean bean = (SearchCarBean) group.beanList.get(i);
                    if (bean.name.contains(key) || keyList.contains(bean.name) || bean.name.contains(lowerKey) || bean.name.contains(upperKey)) {
                        newGroup.searchBeanList.add(bean);
                    } else if (bean.subList.size() > 0) {
                        SearchCarBean newBean = bean.creatNewCarBean();
                        for (int j = 0; j < bean.subList.size(); j++) {
                            SearchCarBean b = (SearchCarBean) bean.subList.get(j);
                            if (b.name.contains(key) || keyList.contains(b.name) || bean.name.contains(lowerKey) || bean.name.contains(upperKey)) {
                                newBean.subList.add(b);
                            }
                        }
                        if (newBean.subList.size() > 0) {
                            newGroup.searchBeanList.add(newBean);
                        }
                    }
                }
                if (newGroup.searchBeanList.size() > 0) {
                    groupList.add(newGroup);
                }
            }
        }
        return groupList;
    }
}
