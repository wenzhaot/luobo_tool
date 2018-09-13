package com.feng.car.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommoditySelectAdapter;
import com.feng.car.databinding.ActivitySelectGoodsLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.event.AddGoodsEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.WXUtils;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

public class SelectGoodsActivity extends BaseActivity<ActivitySelectGoodsLayoutBinding> {
    private final int DEALER_MAX_NUM = 10;
    private TextView mHeadText;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private List<CommodityInfo> mList = new ArrayList();
    private List<CommodityInfo> mSelectList = new ArrayList();
    private String mServeListData = "";

    public int setBaseContentView() {
        return R.layout.activity_select_goods_layout;
    }

    private void initData() {
        String strData = getIntent().getStringExtra("DATA_JSON");
        if (!TextUtils.isEmpty(strData)) {
            List<CommodityInfo> listData = JsonUtil.fromJson(strData, new TypeToken<ArrayList<CommodityInfo>>() {
            });
            if (FengApplication.getInstance().getUserInfo().getLocalSaleType() != 1) {
                this.mServeListData = getIntent().getStringExtra("serve_item_data");
                if (TextUtils.isEmpty(this.mServeListData)) {
                    this.mServeListData = "";
                }
                if (listData != null && listData.size() > 0) {
                    CommodityInfo info = new CommodityInfo();
                    info.id = ((CommodityInfo) listData.get(0)).id;
                    this.mSelectList.add(info);
                }
            } else if (listData != null) {
                this.mSelectList.addAll(listData);
            }
        }
    }

    public void initView() {
        hideDefaultTitleBar();
        initData();
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).back.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SelectGoodsActivity.this.finish();
            }
        });
        if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 0) {
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setText(R.string.next);
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).tvFinish.isClickable() && SelectGoodsActivity.this.mSelectList.size() > 0) {
                        Intent intent = new Intent(SelectGoodsActivity.this, WriteGoodsServeActivity.class);
                        intent.putExtra("id", ((CommodityInfo) SelectGoodsActivity.this.mSelectList.get(0)).id);
                        intent.putExtra("name", ((CommodityInfo) SelectGoodsActivity.this.mSelectList.get(0)).title);
                        intent.putExtra("DATA_JSON", SelectGoodsActivity.this.mServeListData);
                        SelectGoodsActivity.this.startActivity(intent);
                    }
                }
            });
        } else {
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).tvFinish.isClickable()) {
                        EventBus.getDefault().post(new AddGoodsEvent(SelectGoodsActivity.this.mSelectList));
                        SelectGoodsActivity.this.finish();
                    }
                }
            });
        }
        CommoditySelectAdapter mAdapter = new CommoditySelectAdapter(this, this.mList);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(mAdapter);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        mAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                CommodityInfo commodityInfo = (CommodityInfo) SelectGoodsActivity.this.mList.get(position);
                if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1) {
                    if (commodityInfo.local_select.get()) {
                        commodityInfo.local_select.set(false);
                        SelectGoodsActivity.this.mSelectList.remove(commodityInfo);
                        SelectGoodsActivity.this.changRightText();
                    } else if (commodityInfo.status == 15 && SelectGoodsActivity.this.mSelectList.size() < 10) {
                        commodityInfo.local_select.set(true);
                        SelectGoodsActivity.this.mSelectList.add(commodityInfo);
                        SelectGoodsActivity.this.changRightText();
                    }
                } else if (commodityInfo.local_select.get()) {
                    commodityInfo.local_select.set(false);
                    SelectGoodsActivity.this.mSelectList.clear();
                    SelectGoodsActivity.this.changRightText();
                } else if (commodityInfo.status == 15) {
                    if (SelectGoodsActivity.this.mSelectList.size() > 0) {
                        ((CommodityInfo) SelectGoodsActivity.this.mSelectList.get(0)).local_select.set(false);
                    }
                    commodityInfo.local_select.set(true);
                    SelectGoodsActivity.this.mSelectList.clear();
                    SelectGoodsActivity.this.mSelectList.add(commodityInfo);
                    SelectGoodsActivity.this.changRightText();
                }
            }
        });
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvCreatGoods.setSelected(true);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvCreatGoods.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_ADD_COMMODITY_PATH, new Object[0]));
            }
        });
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).recyclerview, State.Normal);
                SelectGoodsActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SelectGoodsActivity.this.getData();
            }
        });
        int m16 = getResources().getDimensionPixelSize(R.dimen.default_16PX);
        this.mHeadText = new TextView(this);
        this.mHeadText.setText("");
        this.mHeadText.setTextColor(ContextCompat.getColor(this, R.color.color_87_000000));
        this.mHeadText.setPadding(m16, m16 * 2, 0, 0);
        this.mHeadText.setTextAppearance(this, R.style.textsize_14);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeadText);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
    }

    private void changRightText() {
        int size = this.mSelectList.size();
        if (FengApplication.getInstance().getUserInfo().getLocalSaleType() == 1) {
            if (size > 0) {
                ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setClickable(true);
            } else {
                ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setClickable(false);
            }
            changeText("完成(" + size + "/10)");
        } else if (size > 0) {
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setClickable(true);
        } else {
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setClickable(false);
        }
    }

    private void changeText(String text) {
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).tvFinish.setText(text);
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(1));
        map.put("size", String.valueOf(100));
        map.put("type", String.valueOf(1));
        FengApplication.getInstance().httpRequest("shop/commodity/list/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (SelectGoodsActivity.this.mList.size() > 0) {
                    SelectGoodsActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    SelectGoodsActivity.this.hideTitleBar();
                    SelectGoodsActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SelectGoodsActivity.this.getData();
                        }
                    });
                }
                ((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivitySelectGoodsLayoutBinding) SelectGoodsActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (SelectGoodsActivity.this.mList.size() > 0) {
                    SelectGoodsActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    return;
                }
                SelectGoodsActivity.this.hideTitleBar();
                SelectGoodsActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        SelectGoodsActivity.this.getData();
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject resultJson = new JSONObject(content);
                    int code = resultJson.getInt("code");
                    if (code == 1) {
                        JSONObject bodyJson = resultJson.getJSONObject("body");
                        BaseListModel<CommodityInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CommodityInfo.class, bodyJson);
                        List<CommodityInfo> list = baseListModel.list;
                        SelectGoodsActivity.this.hideEmptyView();
                        SelectGoodsActivity.this.hintGoodsEmpty();
                        SelectGoodsActivity.this.mList.clear();
                        SelectGoodsActivity.this.checkData(list);
                        if (SelectGoodsActivity.this.mList.size() == 0) {
                            SelectGoodsActivity.this.showGoodsEmptyView(R.string.not_creat_goods, R.drawable.icon_cars_empty);
                        } else if (SelectGoodsActivity.this.mHeadText != null) {
                            SelectGoodsActivity.this.mHeadText.setText(R.string.select_googs_tip);
                        }
                        SelectGoodsActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                    } else if (SelectGoodsActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().upLoadLog(true, "shop/commodity/list/  " + code);
                    } else {
                        SelectGoodsActivity.this.hideTitleBar();
                        SelectGoodsActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                SelectGoodsActivity.this.getData();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (SelectGoodsActivity.this.mList.size() > 0) {
                        SelectGoodsActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        return;
                    }
                    SelectGoodsActivity.this.hideTitleBar();
                    SelectGoodsActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SelectGoodsActivity.this.getData();
                        }
                    });
                }
            }
        });
    }

    private void hideTitleBar() {
        initNormalTitleBar("");
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).rlTitleBar.setVisibility(8);
    }

    private void showGoodsEmptyView(int textID, int drawableID) {
        if (((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).goodsEmpty != null) {
            hideTitleBar();
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).goodsEmpty.setEmptyImage(drawableID);
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).goodsEmpty.setEmptyText(textID);
            ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).goodsEmpty.setVisibility(0);
        }
    }

    private void hintGoodsEmpty() {
        hideDefaultTitleBar();
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).rlTitleBar.setVisibility(0);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).goodsEmpty.setVisibility(8);
        ((ActivitySelectGoodsLayoutBinding) this.mBaseBinding).llGoods.setVisibility(0);
    }

    private void checkData(List<CommodityInfo> list) {
        if (this.mSelectList.size() > 0) {
            for (CommodityInfo info : list) {
                if (info.status != -1) {
                    int index = this.mSelectList.indexOf(info);
                    if (index >= 0) {
                        if (info.status == 15) {
                            this.mSelectList.remove(index);
                            this.mSelectList.add(index, info);
                            info.local_select.set(true);
                        } else {
                            this.mSelectList.remove(info);
                        }
                    }
                    this.mList.add(info);
                }
            }
            int size = this.mSelectList.size();
            int i = 0;
            while (i < size) {
                if (TextUtils.isEmpty(((CommodityInfo) this.mSelectList.get(i)).title)) {
                    this.mSelectList.remove(i);
                    i--;
                    size--;
                }
                i++;
            }
        } else {
            this.mList.addAll(list);
        }
        changRightText();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddGoodsEvent event) {
        if (event.listText != null) {
            finish();
        }
    }

    public String getLogCurrentPage() {
        if (FengApplication.getInstance().isLoginUser()) {
            return "app_dealer_select_goods_" + FengApplication.getInstance().getUserInfo().getLocalSaleType();
        }
        return "-";
    }
}
