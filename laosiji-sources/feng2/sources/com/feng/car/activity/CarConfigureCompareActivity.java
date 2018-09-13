package com.feng.car.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.ConfigClassifyAdapter;
import com.feng.car.adapter.ConfigClassifyAdapter.ItemSelectListener;
import com.feng.car.adapter.VHTableAdapter;
import com.feng.car.databinding.ActivityConfigureCompareBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.car.carconfig.CarConfigureInfo;
import com.feng.car.entity.car.carconfig.CarConfigureParent;
import com.feng.car.entity.car.carconfig.ConfigItem;
import com.feng.car.event.RefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class CarConfigureCompareActivity extends BaseActivity<ActivityConfigureCompareBinding> implements ItemSelectListener {
    public static String COMRARE_IDS = "comrare_ids";
    public static int TYPE_CARS = 2;
    public static int TYPE_CARX = 1;
    private boolean mCanAdd = true;
    private ConfigClassifyAdapter mClassifyAdapter;
    private List<ConfigRowBean> mConfigRowBeanList = new ArrayList();
    private List<ConfigItem> mConfigureItemList = new ArrayList();
    private ArrayList<ConfigLineBean> mContentData = new ArrayList();
    private ArrayList<ConfigLineBean> mFirestRowContentData = new ArrayList();
    private String mIds = "";
    private List<String> mIntData = new ArrayList();
    private boolean mIsHideSameData = false;
    private boolean mIsOrientation = false;
    private List<CarConfigureParent> mList = new ArrayList();
    private List<String> mOwnerPriceList = new ArrayList();
    private List<String> mPriceList = new ArrayList();
    private List<String> mRedcolsList = new ArrayList();
    private int mScrollX;
    private VHTableAdapter mTableAdapter;
    private List<CarConfigureParent> mTitleData = new ArrayList();
    private int mType = TYPE_CARX;
    private int rowCount = 0;

    public class ConfigLineBean {
        public String groupName = "";
        public List<String> list = new ArrayList();

        public boolean isAllSame() {
            List<String> dataList = this.list.subList(1, this.list.size() - 1);
            if (dataList.size() <= 1) {
                return true;
            }
            for (int i = 1; i < dataList.size(); i++) {
                if (!((String) dataList.get(i)).equals(dataList.get(i - 1))) {
                    return false;
                }
            }
            return true;
        }
    }

    private class ConfigRowBean {
        List<CarConfigureInfo> list = new ArrayList();

        ConfigRowBean() {
        }
    }

    protected native void onCreate(Bundle bundle);

    static {
        StubApp.interface11(2253);
    }

    public void getLocalIntentData() {
        this.mType = getIntent().getIntExtra("feng_type", TYPE_CARX);
        String strIDs = getIntent().getStringExtra(COMRARE_IDS);
        if (TextUtils.isEmpty(strIDs)) {
            strIDs = "";
        }
        this.mIds = strIDs;
    }

    public int setBaseContentView() {
        return R.layout.activity_configure_compare;
    }

    public void initView() {
        closeSwip();
        initNormalTitleBar(this.mType == TYPE_CARX ? R.string.car_model_compare : R.string.config);
        initTitleBarRightText(R.string.hint_same_item, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (CarConfigureCompareActivity.this.mList.size() != 0) {
                    if (CarConfigureCompareActivity.this.mIsHideSameData) {
                        CarConfigureCompareActivity.this.mIsHideSameData = false;
                        CarConfigureCompareActivity.this.mRootBinding.titleLine.tvRightText.setText(R.string.hint_same_item);
                    } else {
                        CarConfigureCompareActivity.this.mIsHideSameData = true;
                        CarConfigureCompareActivity.this.mRootBinding.titleLine.tvRightText.setText(R.string.all_configure);
                    }
                    CarConfigureCompareActivity.this.assembleData();
                }
            }
        });
        ((ActivityConfigureCompareBinding) this.mBaseBinding).ivClassify.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (((ActivityConfigureCompareBinding) CarConfigureCompareActivity.this.mBaseBinding).rvConfigClass.isShown()) {
                    CarConfigureCompareActivity.this.hideConfigureSelectView();
                } else {
                    CarConfigureCompareActivity.this.showConfigureSelectView();
                }
            }
        });
        ((ActivityConfigureCompareBinding) this.mBaseBinding).viewShade.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                CarConfigureCompareActivity.this.hideConfigureSelectView();
            }
        });
        loadCarsConfigures(this.mIds);
    }

    private void loadCarsConfigures(final String ids) {
        String url;
        this.mIds = ids;
        Map<String, Object> map = new HashMap();
        if (this.mType == TYPE_CARX) {
            map.put("carxids", ids);
            url = "car/carxpk/";
        } else {
            map.put("carsid", ids);
            url = "car/carsconfig/";
        }
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        FengApplication.getInstance().httpRequest(url, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CarConfigureCompareActivity.this.mList.size() <= 0) {
                    CarConfigureCompareActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CarConfigureCompareActivity.this.loadCarsConfigures(ids);
                        }
                    });
                } else {
                    CarConfigureCompareActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
                CarConfigureCompareActivity.this.showLaoSiJiDialog();
            }

            public void onFinish() {
                CarConfigureCompareActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CarConfigureCompareActivity.this.mList.size() <= 0) {
                    CarConfigureCompareActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CarConfigureCompareActivity.this.loadCarsConfigures(ids);
                        }
                    });
                } else {
                    CarConfigureCompareActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
                CarConfigureCompareActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        CarConfigureCompareActivity.this.hideEmptyView();
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        JSONObject jsonConfigs = jsonBody.getJSONObject("configs");
                        JSONArray jsonRedcols = jsonBody.getJSONArray("redcols");
                        BaseListModel<CarConfigureParent> baseListModel = new BaseListModel();
                        baseListModel.parser(CarConfigureParent.class, jsonConfigs);
                        CarConfigureCompareActivity.this.mList.clear();
                        CarConfigureCompareActivity.this.mList.addAll(baseListModel.list);
                        int size = jsonRedcols.length();
                        if (CarConfigureCompareActivity.this.mList.size() <= 0) {
                            CarConfigureCompareActivity.this.showEmptyView((int) R.string.car_no_config, (int) R.drawable.icon_blank_peizhi);
                            ((ActivityConfigureCompareBinding) CarConfigureCompareActivity.this.mBaseBinding).ivClassify.setVisibility(8);
                            CarConfigureCompareActivity.this.mRootBinding.titleLine.tvRightText.setVisibility(8);
                            return;
                        }
                        CarConfigureCompareActivity.this.mRedcolsList.clear();
                        for (int i = 0; i < size; i++) {
                            CarConfigureCompareActivity.this.mRedcolsList.add(jsonRedcols.getString(i));
                        }
                        CarConfigureCompareActivity.this.assembleData();
                        return;
                    }
                    CarConfigureCompareActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } catch (Exception e) {
                    e.printStackTrace();
                    CarConfigureCompareActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }
        });
    }

    /* JADX WARNING: Removed duplicated region for block: B:69:0x025f  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x02c9  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x03ad  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0367  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x038c  */
    /* JADX WARNING: Removed duplicated region for block: B:54:0x01fb  */
    /* JADX WARNING: Removed duplicated region for block: B:58:0x0218  */
    /* JADX WARNING: Removed duplicated region for block: B:69:0x025f  */
    /* JADX WARNING: Removed duplicated region for block: B:72:0x026e  */
    /* JADX WARNING: Removed duplicated region for block: B:82:0x02b0  */
    /* JADX WARNING: Removed duplicated region for block: B:86:0x02c9  */
    /* JADX WARNING: Removed duplicated region for block: B:95:0x0347  */
    /* JADX WARNING: Removed duplicated region for block: B:100:0x0367  */
    /* JADX WARNING: Removed duplicated region for block: B:107:0x03ad  */
    /* JADX WARNING: Removed duplicated region for block: B:103:0x038c  */
    private void assembleData() {
        /*
        r22 = this;
        r0 = r22;
        r1 = r0.mTitleData;
        r1.clear();
        r11 = 0;
    L_0x0008:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x0024;
    L_0x0012:
        r0 = r22;
        r1 = r0.mTitleData;
        r0 = r22;
        r2 = r0.mList;
        r2 = r2.get(r11);
        r1.add(r2);
        r11 = r11 + 1;
        goto L_0x0008;
    L_0x0024:
        r19 = new com.feng.car.entity.car.carconfig.CarConfigureParent;
        r19.<init>();
        r0 = r22;
        r1 = r0.mTitleData;
        r2 = 0;
        r0 = r19;
        r1.add(r2, r0);
        r18 = new com.feng.car.entity.car.carconfig.CarConfigureParent;
        r18.<init>();
        r1 = -1;
        r0 = r18;
        r0.spec_id = r1;
        r0 = r22;
        r1 = r0.mTitleData;
        r0 = r18;
        r1.add(r0);
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r1 = r1.size();
        if (r1 == 0) goto L_0x0061;
    L_0x0050:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        if (r1 <= 0) goto L_0x0061;
    L_0x005a:
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r1.clear();
    L_0x0061:
        r11 = 0;
    L_0x0062:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x00b4;
    L_0x006c:
        r0 = r22;
        r1 = r0.mList;
        r9 = r1.get(r11);
        r9 = (com.feng.car.entity.car.carconfig.CarConfigureParent) r9;
        r7 = new com.feng.car.activity.CarConfigureCompareActivity$ConfigRowBean;
        r0 = r22;
        r7.<init>();
        r14 = 0;
    L_0x007e:
        r1 = r9.items;
        r1 = r1.size();
        if (r14 >= r1) goto L_0x00aa;
    L_0x0086:
        r1 = r9.items;
        r8 = r1.get(r14);
        r8 = (com.feng.car.entity.car.carconfig.CarConfigureItemInfo) r8;
        r15 = 0;
    L_0x008f:
        r1 = r8.confs;
        r1 = r1.size();
        if (r15 >= r1) goto L_0x00a7;
    L_0x0097:
        r1 = r8.confs;
        r12 = r1.get(r15);
        r12 = (com.feng.car.entity.car.carconfig.CarConfigureInfo) r12;
        r1 = r7.list;
        r1.add(r12);
        r15 = r15 + 1;
        goto L_0x008f;
    L_0x00a7:
        r14 = r14 + 1;
        goto L_0x007e;
    L_0x00aa:
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r1.add(r7);
        r11 = r11 + 1;
        goto L_0x0062;
    L_0x00b4:
        r0 = r22;
        r1 = r0.mContentData;
        r1.clear();
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r1 = r1.size();
        if (r1 == 0) goto L_0x00cf;
    L_0x00c5:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        if (r1 != 0) goto L_0x00f2;
    L_0x00cf:
        r11 = 0;
    L_0x00d0:
        r0 = r22;
        r1 = r0.rowCount;
        if (r11 >= r1) goto L_0x01a0;
    L_0x00d6:
        r0 = r22;
        r1 = r0.mFirestRowContentData;
        r7 = r1.get(r11);
        r7 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r7;
        r1 = r7.list;
        r2 = "";
        r1.add(r2);
        r0 = r22;
        r1 = r0.mContentData;
        r1.add(r7);
        r11 = r11 + 1;
        goto L_0x00d0;
    L_0x00f2:
        r0 = r22;
        r1 = r0.mFirestRowContentData;
        r1.clear();
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r2 = 0;
        r1 = r1.get(r2);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigRowBean) r1;
        r1 = r1.list;
        r1 = r1.size();
        r0 = r22;
        r0.rowCount = r1;
        r11 = 0;
    L_0x010f:
        r0 = r22;
        r1 = r0.rowCount;
        if (r11 >= r1) goto L_0x01a0;
    L_0x0115:
        r7 = new com.feng.car.activity.CarConfigureCompareActivity$ConfigLineBean;
        r0 = r22;
        r7.<init>();
        r10 = new com.feng.car.activity.CarConfigureCompareActivity$ConfigLineBean;
        r0 = r22;
        r10.<init>();
        r14 = 0;
    L_0x0124:
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r1 = r1.size();
        if (r14 >= r1) goto L_0x017e;
    L_0x012e:
        r0 = r22;
        r1 = r0.mConfigRowBeanList;
        r21 = r1.get(r14);
        r21 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigRowBean) r21;
        if (r14 != 0) goto L_0x015c;
    L_0x013a:
        r2 = r7.list;
        r0 = r21;
        r1 = r0.list;
        r1 = r1.get(r11);
        r1 = (com.feng.car.entity.car.carconfig.CarConfigureInfo) r1;
        r1 = r1.sub;
        r2.add(r1);
        r2 = r10.list;
        r0 = r21;
        r1 = r0.list;
        r1 = r1.get(r11);
        r1 = (com.feng.car.entity.car.carconfig.CarConfigureInfo) r1;
        r1 = r1.sub;
        r2.add(r1);
    L_0x015c:
        r2 = r7.list;
        r0 = r21;
        r1 = r0.list;
        r1 = r1.get(r11);
        r1 = (com.feng.car.entity.car.carconfig.CarConfigureInfo) r1;
        r1 = r1.value;
        r2.add(r1);
        r0 = r21;
        r1 = r0.list;
        r1 = r1.get(r11);
        r1 = (com.feng.car.entity.car.carconfig.CarConfigureInfo) r1;
        r1 = r1.item;
        r7.groupName = r1;
        r14 = r14 + 1;
        goto L_0x0124;
    L_0x017e:
        r1 = r10.list;
        r2 = "";
        r1.add(r2);
        r0 = r22;
        r1 = r0.mFirestRowContentData;
        r1.add(r10);
        r1 = r7.list;
        r2 = "";
        r1.add(r2);
        r0 = r22;
        r1 = r0.mContentData;
        r1.add(r7);
        r11 = r11 + 1;
        goto L_0x010f;
    L_0x01a0:
        r0 = r22;
        r1 = r0.mPriceList;
        r1.clear();
        r20 = -1;
        r11 = 0;
    L_0x01aa:
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x01f6;
    L_0x01b4:
        r0 = r22;
        r1 = r0.mContentData;
        r7 = r1.get(r11);
        r7 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r7;
        r1 = r7.list;
        r2 = 0;
        r17 = r1.get(r2);
        r17 = (java.lang.String) r17;
        r1 = com.feng.library.utils.StringUtil.isEmpty(r17);
        if (r1 != 0) goto L_0x01f3;
    L_0x01cd:
        r1 = "厂商指导价(元)";
        r0 = r17;
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x01f3;
    L_0x01d8:
        r20 = r11;
        r14 = 0;
    L_0x01db:
        r1 = r7.list;
        r1 = r1.size();
        if (r14 >= r1) goto L_0x01f6;
    L_0x01e3:
        r0 = r22;
        r1 = r0.mPriceList;
        r2 = r7.list;
        r2 = r2.get(r14);
        r1.add(r2);
        r14 = r14 + 1;
        goto L_0x01db;
    L_0x01f3:
        r11 = r11 + 1;
        goto L_0x01aa;
    L_0x01f6:
        r1 = -1;
        r0 = r20;
        if (r0 == r1) goto L_0x0204;
    L_0x01fb:
        r0 = r22;
        r1 = r0.mContentData;
        r0 = r20;
        r1.remove(r0);
    L_0x0204:
        r0 = r22;
        r1 = r0.mOwnerPriceList;
        r1.clear();
        r20 = -1;
        r11 = 0;
    L_0x020e:
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x025a;
    L_0x0218:
        r0 = r22;
        r1 = r0.mContentData;
        r7 = r1.get(r11);
        r7 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r7;
        r1 = r7.list;
        r2 = 0;
        r17 = r1.get(r2);
        r17 = (java.lang.String) r17;
        r1 = com.feng.library.utils.StringUtil.isEmpty(r17);
        if (r1 != 0) goto L_0x0257;
    L_0x0231:
        r1 = "车主价格(元)";
        r0 = r17;
        r1 = r0.equals(r1);
        if (r1 == 0) goto L_0x0257;
    L_0x023c:
        r20 = r11;
        r14 = 0;
    L_0x023f:
        r1 = r7.list;
        r1 = r1.size();
        if (r14 >= r1) goto L_0x025a;
    L_0x0247:
        r0 = r22;
        r1 = r0.mOwnerPriceList;
        r2 = r7.list;
        r2 = r2.get(r14);
        r1.add(r2);
        r14 = r14 + 1;
        goto L_0x023f;
    L_0x0257:
        r11 = r11 + 1;
        goto L_0x020e;
    L_0x025a:
        r1 = -1;
        r0 = r20;
        if (r0 == r1) goto L_0x0268;
    L_0x025f:
        r0 = r22;
        r1 = r0.mContentData;
        r0 = r20;
        r1.remove(r0);
    L_0x0268:
        r0 = r22;
        r1 = r0.mIsHideSameData;
        if (r1 == 0) goto L_0x02a6;
    L_0x026e:
        r16 = new java.util.ArrayList;
        r16.<init>();
        r11 = 0;
    L_0x0274:
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x0296;
    L_0x027e:
        r0 = r22;
        r1 = r0.mContentData;
        r7 = r1.get(r11);
        r7 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r7;
        r1 = r7.isAllSame();
        if (r1 != 0) goto L_0x0293;
    L_0x028e:
        r0 = r16;
        r0.add(r7);
    L_0x0293:
        r11 = r11 + 1;
        goto L_0x0274;
    L_0x0296:
        r0 = r22;
        r1 = r0.mContentData;
        r1.clear();
        r0 = r22;
        r1 = r0.mContentData;
        r0 = r16;
        r1.addAll(r0);
    L_0x02a6:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        if (r1 <= 0) goto L_0x02b7;
    L_0x02b0:
        r0 = r22;
        r1 = r0.mConfigureItemList;
        r1.clear();
    L_0x02b7:
        r0 = r22;
        r1 = r0.mIntData;
        r1.clear();
        r11 = 0;
    L_0x02bf:
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.size();
        if (r11 >= r1) goto L_0x033b;
    L_0x02c9:
        r0 = r22;
        r2 = r0.mIntData;
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.get(r11);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r1;
        r1 = r1.groupName;
        r2.add(r1);
        if (r11 <= 0) goto L_0x031d;
    L_0x02de:
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.get(r11);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r1;
        r2 = r1.groupName;
        r0 = r22;
        r1 = r0.mContentData;
        r3 = r11 + -1;
        r1 = r1.get(r3);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r1;
        r1 = r1.groupName;
        r1 = r2.equals(r1);
        if (r1 != 0) goto L_0x031a;
    L_0x02fe:
        r13 = new com.feng.car.entity.car.carconfig.ConfigItem;
        r13.<init>();
        r0 = r22;
        r1 = r0.mContentData;
        r1 = r1.get(r11);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r1;
        r1 = r1.groupName;
        r13.name = r1;
        r13.postion = r11;
        r0 = r22;
        r1 = r0.mConfigureItemList;
        r1.add(r13);
    L_0x031a:
        r11 = r11 + 1;
        goto L_0x02bf;
    L_0x031d:
        r13 = new com.feng.car.entity.car.carconfig.ConfigItem;
        r13.<init>();
        r0 = r22;
        r1 = r0.mContentData;
        r2 = 0;
        r1 = r1.get(r2);
        r1 = (com.feng.car.activity.CarConfigureCompareActivity.ConfigLineBean) r1;
        r1 = r1.groupName;
        r13.name = r1;
        r13.postion = r11;
        r0 = r22;
        r1 = r0.mConfigureItemList;
        r1.add(r13);
        goto L_0x031a;
    L_0x033b:
        r0 = r22;
        r1 = r0.mList;
        r1 = r1.size();
        r2 = 9;
        if (r1 >= r2) goto L_0x034d;
    L_0x0347:
        r0 = r22;
        r1 = r0.mIsOrientation;
        if (r1 == 0) goto L_0x03a7;
    L_0x034d:
        r1 = 0;
        r0 = r22;
        r0.mCanAdd = r1;
    L_0x0352:
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r0 = r22;
        r2 = r0.mCanAdd;
        r1.setCanAdd(r2);
        r0 = r22;
        r1 = r0.mTableAdapter;
        if (r1 == 0) goto L_0x03ad;
    L_0x0367:
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r1.notifyDataSetChanged();
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r2 = new com.feng.car.activity.CarConfigureCompareActivity$5;
        r0 = r22;
        r2.<init>();
        r1.post(r2);
    L_0x0384:
        r0 = r22;
        r1 = r0.mType;
        r2 = TYPE_CARS;
        if (r1 != r2) goto L_0x03a3;
    L_0x038c:
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r1.forbidAdd();
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r2 = 0;
        r1.setCanAdd(r2);
    L_0x03a3:
        r22.initClassifyView();
        return;
    L_0x03a7:
        r1 = 1;
        r0 = r22;
        r0.mCanAdd = r1;
        goto L_0x0352;
    L_0x03ad:
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r2 = new com.feng.car.activity.CarConfigureCompareActivity$6;
        r0 = r22;
        r2.<init>();
        r1.setOnScrollChangedListener(r2);
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r0 = r22;
        r2 = r0.mTitleData;
        r1.setTitleData(r2);
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r0 = r22;
        r2 = r0.mPriceList;
        r1.setPriceList(r2);
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r0 = r22;
        r2 = r0.mOwnerPriceList;
        r1.setOwnerPriceList(r2);
        r1 = new com.feng.car.adapter.VHTableAdapter;
        r0 = r22;
        r3 = r0.mTitleData;
        r0 = r22;
        r4 = r0.mContentData;
        r0 = r22;
        r5 = r0.mIntData;
        r0 = r22;
        r6 = r0.mRedcolsList;
        r2 = r22;
        r1.<init>(r2, r3, r4, r5, r6);
        r0 = r22;
        r0.mTableAdapter = r1;
        r0 = r22;
        r1 = r0.mTableAdapter;
        r0 = r22;
        r2 = r0.mIsHideSameData;
        r1.setIsHideSameData(r2);
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r0 = r22;
        r2 = r0.mTableAdapter;
        r1.setAdapter(r2);
        r0 = r22;
        r1 = r0.mBaseBinding;
        r1 = (com.feng.car.databinding.ActivityConfigureCompareBinding) r1;
        r1 = r1.vHTableView;
        r2 = new com.feng.car.activity.CarConfigureCompareActivity$7;
        r0 = r22;
        r2.<init>();
        r1.setOnAddDelteListener(r2);
        goto L_0x0384;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.activity.CarConfigureCompareActivity.assembleData():void");
    }

    public void onItemClick(int position) {
        ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.scrollToPosition(position);
        ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.post(new Runnable() {
            public void run() {
                ((ActivityConfigureCompareBinding) CarConfigureCompareActivity.this.mBaseBinding).vHTableView.scrollTo(CarConfigureCompareActivity.this.mScrollX, 0);
            }
        });
        hideConfigureSelectView();
    }

    private void initClassifyView() {
        if (this.mClassifyAdapter == null) {
            ((ActivityConfigureCompareBinding) this.mBaseBinding).rvConfigClass.setLayoutManager(new GridLayoutManager(this, 2));
            this.mClassifyAdapter = new ConfigClassifyAdapter(this, this.mConfigureItemList);
            this.mClassifyAdapter.setItemSelectListener(this);
            ((ActivityConfigureCompareBinding) this.mBaseBinding).rvConfigClass.setAdapter(this.mClassifyAdapter);
            ((ActivityConfigureCompareBinding) this.mBaseBinding).rvConfigClass.setOverScrollMode(2);
            return;
        }
        this.mClassifyAdapter.notifyDataSetChanged();
    }

    private void hideConfigureSelectView() {
        ((ActivityConfigureCompareBinding) this.mBaseBinding).viewShade.setVisibility(8);
        ((ActivityConfigureCompareBinding) this.mBaseBinding).rvConfigClass.setVisibility(8);
    }

    private void showConfigureSelectView() {
        ((ActivityConfigureCompareBinding) this.mBaseBinding).viewShade.setVisibility(0);
        ((ActivityConfigureCompareBinding) this.mBaseBinding).rvConfigClass.setVisibility(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RefreshEvent event) {
        if (event.type == 1) {
            FengConstant.CAR_MAX_CURRENT_COMPARISON_NUM++;
            List<CarModelInfo> list = JsonUtil.fromJson(SharedUtil.getString(this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY), new TypeToken<ArrayList<CarModelInfo>>() {
            });
            if (list == null) {
                list = new ArrayList();
            }
            if (!list.contains(event.carModelInfo)) {
                if (list.size() >= 9) {
                    list.remove(0);
                }
                list.add(event.carModelInfo);
            }
            SharedUtil.putString(this, CarModleComparisonActivity.USER_SEL_COMPARISON_KEY, JsonUtil.toJson(list));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < list.size(); i++) {
                sb.append(((CarModelInfo) list.get(i)).id);
                if (i != list.size() - 1) {
                    sb.append(",");
                }
            }
            loadCarsConfigures(sb.toString());
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogGatherReadUtil.getInstance().setScreenOrientation(this);
        boolean flag;
        if (this.mType == TYPE_CARX) {
            if (getResources().getConfiguration().orientation == 2) {
                flag = true;
            } else {
                flag = false;
            }
            if (flag) {
                this.mIsOrientation = true;
                this.mCanAdd = false;
                ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.forbidAdd();
                this.mRootBinding.titleLine.getRoot().setVisibility(8);
            } else {
                this.mIsOrientation = false;
                this.mCanAdd = true;
                ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.allowAdd();
                this.mRootBinding.titleLine.getRoot().setVisibility(0);
            }
            ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.setCanAdd(this.mCanAdd);
            ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.getTitleAdapter().notifyDataSetChanged();
            ((ActivityConfigureCompareBinding) this.mBaseBinding).vHTableView.getRecyclerView().getAdapter().notifyDataSetChanged();
            return;
        }
        if (getResources().getConfiguration().orientation == 2) {
            flag = true;
        } else {
            flag = false;
        }
        if (flag) {
            this.mRootBinding.titleLine.getRoot().setVisibility(8);
        } else {
            this.mRootBinding.titleLine.getRoot().setVisibility(0);
        }
    }

    public String getLogCurrentPage() {
        if (this.mType == TYPE_CARS) {
            return "app_car_configuration?carsid=" + this.mIds + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
        }
        return "app_car_pk_result?carxids=" + this.mIds + "&cityid=" + MapUtil.newInstance().getCurrentCityId();
    }
}
