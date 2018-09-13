package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.SearchCarAdapter;
import com.feng.car.adapter.SearchCarAdapter.ConditionChangeListener;
import com.feng.car.adapter.SearchCarTypeGroupAdapter;
import com.feng.car.databinding.ActivitySearchCarBinding;
import com.feng.car.databinding.LevelDialogLayoutBinding;
import com.feng.car.databinding.PriceDialogLayoutBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.car.entity.searchcar.SearchCarTypeGroup;
import com.feng.car.event.CloseMoreConditionEvent;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.event.SearchConditionEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.flowlayout.DLFlowLayout$FlowClickListener;
import com.feng.car.view.flowlayout.DLFlowLayout$OnSelectListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class MoreConditionActivity extends BaseActivity<ActivitySearchCarBinding> implements ConditionChangeListener, DLFlowLayout$FlowClickListener {
    private SearchCarAdapter mAdapter;
    private boolean mCancelRequest = false;
    private boolean mCouldClickPriceConfirm = false;
    private List<SearchCarGroup> mHistoryList = new ArrayList();
    private boolean mIsClickScroll = false;
    private LevelDialogLayoutBinding mLevelBinding;
    private Dialog mLevelDialog;
    private LinearLayoutManager mLinearLayoutManager;
    private List<SearchCarGroup> mList = new ArrayList();
    private Dialog mPriceDialog;
    private PriceDialogLayoutBinding mPriceLayoutBinding;
    private int mPriceType = SearchCarManager.GUIDE_PRICE_TYPE;
    private boolean mStartSearchCarResultAcitivity = false;
    private SearchCarTypeGroupAdapter mTypeGroupAdapter;
    private LinearLayoutManager mTypeGroupLinearLayoutManager;
    private List<SearchCarTypeGroup> mTypeGroupList;

    private void setConfirmClickable(boolean flag) {
        if (flag) {
            this.mPriceLayoutBinding.confirm.setTextColor(this.mResources.getColor(R.color.color_87_000000));
        } else {
            this.mPriceLayoutBinding.confirm.setTextColor(this.mResources.getColor(R.color.color_38_000000));
        }
    }

    private void showPriceDialog(SearchCarBean priceBean) {
        if (this.mPriceLayoutBinding == null) {
            this.mPriceLayoutBinding = PriceDialogLayoutBinding.inflate(LayoutInflater.from(this));
            this.mPriceLayoutBinding.minPrice.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(MoreConditionActivity.this.mPriceLayoutBinding.maxPrice.getText().toString())) {
                        MoreConditionActivity.this.mCouldClickPriceConfirm = false;
                    } else {
                        MoreConditionActivity.this.mCouldClickPriceConfirm = true;
                    }
                    MoreConditionActivity.this.setConfirmClickable(MoreConditionActivity.this.mCouldClickPriceConfirm);
                }

                public void afterTextChanged(Editable s) {
                }
            });
            this.mPriceLayoutBinding.maxPrice.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (StringUtil.isEmpty(s.toString()) && StringUtil.isEmpty(MoreConditionActivity.this.mPriceLayoutBinding.minPrice.getText().toString())) {
                        MoreConditionActivity.this.mCouldClickPriceConfirm = false;
                    } else {
                        MoreConditionActivity.this.mCouldClickPriceConfirm = true;
                    }
                    MoreConditionActivity.this.setConfirmClickable(MoreConditionActivity.this.mCouldClickPriceConfirm);
                }

                public void afterTextChanged(Editable s) {
                }
            });
            this.mPriceLayoutBinding.confirm.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (MoreConditionActivity.this.mCouldClickPriceConfirm) {
                        int min = 0;
                        int max = 0;
                        String minStr = MoreConditionActivity.this.mPriceLayoutBinding.minPrice.getText().toString();
                        String maxStr = MoreConditionActivity.this.mPriceLayoutBinding.maxPrice.getText().toString();
                        if (!StringUtil.isEmpty(minStr)) {
                            min = Integer.parseInt(MoreConditionActivity.this.mPriceLayoutBinding.minPrice.getText().toString());
                        }
                        if (!StringUtil.isEmpty(maxStr)) {
                            max = Integer.parseInt(MoreConditionActivity.this.mPriceLayoutBinding.maxPrice.getText().toString());
                        }
                        if (max == 0 || min < max) {
                            SearchCarManager.newInstance().setCustomPrice(min, max);
                            MoreConditionActivity.this.mAdapter.notifyDataSetChanged();
                            MoreConditionActivity.this.getData();
                            MoreConditionActivity.this.mPriceDialog.dismiss();
                            return;
                        }
                        MoreConditionActivity.this.showThirdTypeToast((int) R.string.maxprice_must_higher_minprice);
                    }
                }
            });
        }
        if (this.mPriceDialog == null) {
            this.mPriceDialog = CommonDialog.showViewDialog(this, this.mPriceLayoutBinding.getRoot());
        }
        SearchCarBean currentBean = SearchCarManager.newInstance().getCurrentPriceValue();
        if (currentBean.name.equals("自定义")) {
            int min = currentBean.min;
            int max = currentBean.max;
            if (min == 0) {
                this.mPriceLayoutBinding.minPrice.setText("");
            } else {
                this.mPriceLayoutBinding.minPrice.setText(String.valueOf(min));
            }
            if (max == 0) {
                this.mPriceLayoutBinding.maxPrice.setText("");
            } else {
                this.mPriceLayoutBinding.maxPrice.setText(String.valueOf(max));
            }
        } else {
            this.mPriceLayoutBinding.maxPrice.setText("");
            this.mPriceLayoutBinding.minPrice.setText("");
        }
        this.mPriceDialog.show();
    }

    private void showLevelDialog(final SearchCarBean bean) {
        if (this.mLevelBinding == null) {
            this.mLevelBinding = LevelDialogLayoutBinding.inflate(LayoutInflater.from(this));
            this.mLevelBinding.confirm.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    MoreConditionActivity.this.mLevelDialog.dismiss();
                }
            });
        }
        if (this.mLevelDialog == null) {
            this.mLevelDialog = CommonDialog.showViewDialog(this, this.mLevelBinding.getRoot());
        }
        this.mLevelBinding.groupName.setText("哪种" + bean.name);
        this.mLevelBinding.flowlayout.setMar(this.mResources.getDimensionPixelSize(R.dimen.default_25PX));
        if (bean.name.equals("自动")) {
            this.mLevelBinding.flowlayout.setCountPerLine(2);
        } else {
            this.mLevelBinding.flowlayout.setCountPerLine(4);
        }
        this.mLevelBinding.flowlayout.setType(this.mPriceType);
        this.mLevelBinding.flowlayout.setIsSingle(false);
        this.mLevelBinding.flowlayout.setCanCancelAll(true);
        this.mLevelBinding.flowlayout.setFlowData(bean.subList);
        this.mLevelBinding.flowlayout.setOnSelectListener(new DLFlowLayout$OnSelectListener() {
            public void onSelect(int position) {
                if (bean.name.equals("自动")) {
                    SearchCarManager.newInstance().refreshTransmissionCondition();
                } else {
                    SearchCarManager.newInstance().refreshUnlimited();
                }
                MoreConditionActivity.this.mAdapter.notifyDataSetChanged();
                MoreConditionActivity.this.getData();
            }

            public void onUnSelect(int position) {
                if (bean.name.equals("自动")) {
                    SearchCarManager.newInstance().refreshTransmissionCondition();
                } else {
                    SearchCarManager.newInstance().refreshUnlimited();
                }
                MoreConditionActivity.this.mAdapter.notifyDataSetChanged();
                MoreConditionActivity.this.getData();
            }

            public void onOutLimit() {
            }
        });
        this.mLevelDialog.show();
    }

    private void reset() {
        SearchCarManager.newInstance().clearAllConditionExceptBrand();
        getData();
        this.mAdapter.notifyDataSetChanged();
    }

    public int setBaseContentView() {
        return R.layout.activity_search_car;
    }

    public void initView() {
        closeSwip();
        hideDefaultTitleBar();
        ((ActivitySearchCarBinding) this.mBaseBinding).back.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().recoveryAllConditionList(MoreConditionActivity.this.mHistoryList);
                MoreConditionActivity.this.finish();
            }
        });
        ((ActivitySearchCarBinding) this.mBaseBinding).searchText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MoreConditionActivity.this.startActivity(new Intent(MoreConditionActivity.this, SearchConditionActivity.class));
            }
        });
        ((ActivitySearchCarBinding) this.mBaseBinding).clear.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                MoreConditionActivity.this.reset();
            }
        });
        this.mList = SearchCarManager.newInstance().getAllDataList();
        this.mPriceType = SearchCarManager.newInstance().getPriceType();
        this.mHistoryList = SearchCarManager.newInstance().deepCopyGroupList(SearchCarManager.newInstance().getAllDataList());
        this.mAdapter = new SearchCarAdapter(this, this.mList);
        this.mAdapter.setConditionChangeListener(this);
        initTypeGroupData();
        initPriceType();
        ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.setAdapter(new LRecyclerViewAdapter(this.mAdapter));
        this.mLinearLayoutManager = new LinearLayoutManager(this);
        ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.setLayoutManager(this.mLinearLayoutManager);
        ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.setPullRefreshEnabled(false);
        ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(null);
        ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!MoreConditionActivity.this.mIsClickScroll) {
                    int firstPos = MoreConditionActivity.this.mLinearLayoutManager.findFirstVisibleItemPosition();
                    int lastPos = MoreConditionActivity.this.mLinearLayoutManager.findLastVisibleItemPosition();
                    int position = ((lastPos + firstPos) / 2) - 1;
                    if (position >= 0) {
                        SearchCarGroup group = (SearchCarGroup) MoreConditionActivity.this.mList.get(position);
                        int i;
                        SearchCarTypeGroup typeGroup;
                        if (lastPos <= 4) {
                            for (i = 0; i < MoreConditionActivity.this.mTypeGroupList.size(); i++) {
                                typeGroup = (SearchCarTypeGroup) MoreConditionActivity.this.mTypeGroupList.get(i);
                                if (i == 0) {
                                    typeGroup.isSelect = true;
                                } else {
                                    typeGroup.isSelect = false;
                                }
                            }
                            MoreConditionActivity.this.mTypeGroupAdapter.notifyDataSetChanged();
                            ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).groupRecyclerView.scrollToPosition(0);
                        } else if (!((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).recyclerView.canScrollVertically(1)) {
                            for (i = 0; i < MoreConditionActivity.this.mTypeGroupList.size(); i++) {
                                typeGroup = (SearchCarTypeGroup) MoreConditionActivity.this.mTypeGroupList.get(i);
                                if (i == MoreConditionActivity.this.mTypeGroupList.size() - 1) {
                                    typeGroup.isSelect = true;
                                } else {
                                    typeGroup.isSelect = false;
                                }
                            }
                            MoreConditionActivity.this.mTypeGroupAdapter.notifyDataSetChanged();
                            ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).groupRecyclerView.scrollToPosition(MoreConditionActivity.this.mTypeGroupList.size() - 1);
                        } else if (group.isGroupFirst) {
                            for (i = 0; i < MoreConditionActivity.this.mTypeGroupList.size(); i++) {
                                typeGroup = (SearchCarTypeGroup) MoreConditionActivity.this.mTypeGroupList.get(i);
                                if (i == group.groupPosition) {
                                    typeGroup.isSelect = true;
                                } else {
                                    typeGroup.isSelect = false;
                                }
                            }
                            if (group.groupPosition < MoreConditionActivity.this.mTypeGroupLinearLayoutManager.findFirstVisibleItemPosition() + 2) {
                                MoreConditionActivity.this.mTypeGroupAdapter.notifyDataSetChanged();
                                ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).groupRecyclerView.scrollToPosition(group.groupPosition - 2);
                            } else {
                                MoreConditionActivity.this.mTypeGroupAdapter.notifyDataSetChanged();
                                ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).groupRecyclerView.scrollToPosition(group.groupPosition + 2);
                            }
                        }
                    } else {
                        return;
                    }
                }
                MoreConditionActivity.this.mIsClickScroll = false;
            }
        });
        ((ActivitySearchCarBinding) this.mBaseBinding).resultLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                    MoreConditionActivity.this.mStartSearchCarResultAcitivity = true;
                    MoreConditionActivity.this.startActivity(new Intent(MoreConditionActivity.this, SearchCarResultActivity.class));
                } else {
                    EventBus.getDefault().post(new SearchCarEvent(4));
                }
                MoreConditionActivity.this.finish();
            }
        });
        if (this.mPriceType != SearchCarManager.GUIDE_PRICE_TYPE) {
            realPriceSelect();
        }
        int pos = SearchCarManager.newInstance().getGroupPosition();
        if (pos != -1 && pos < this.mTypeGroupList.size()) {
            int scrollPosition = -1;
            for (int i = 0; i < this.mTypeGroupList.size(); i++) {
                SearchCarTypeGroup typeGroup = (SearchCarTypeGroup) this.mTypeGroupList.get(i);
                if (i == pos) {
                    typeGroup.isSelect = true;
                    scrollPosition = typeGroup.position;
                } else {
                    typeGroup.isSelect = false;
                }
            }
            this.mTypeGroupAdapter.notifyDataSetChanged();
            if (pos > 2) {
                ((ActivitySearchCarBinding) this.mBaseBinding).groupRecyclerView.scrollToPosition(pos - 2);
            } else {
                ((ActivitySearchCarBinding) this.mBaseBinding).groupRecyclerView.scrollToPosition(0);
            }
            if (scrollPosition != -1) {
                ((LinearLayoutManager) ((ActivitySearchCarBinding) this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(scrollPosition + 1, 0);
            }
        }
        getData();
    }

    private void initPriceType() {
        ((ActivitySearchCarBinding) this.mBaseBinding).guidepriceImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (MoreConditionActivity.this.mPriceType != SearchCarManager.GUIDE_PRICE_TYPE) {
                    MoreConditionActivity.this.guidePriceSelect();
                    MoreConditionActivity.this.getData();
                }
            }
        });
        ((ActivitySearchCarBinding) this.mBaseBinding).realpriceImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (MoreConditionActivity.this.mPriceType != SearchCarManager.REAL_PRICE_TYPE) {
                    MoreConditionActivity.this.realPriceSelect();
                    MoreConditionActivity.this.getData();
                }
            }
        });
    }

    private void guidePriceSelect() {
        this.mPriceType = SearchCarManager.GUIDE_PRICE_TYPE;
        ((ActivitySearchCarBinding) this.mBaseBinding).guidepriceImage.setBackgroundResource(R.drawable.guideprice_select);
        ((ActivitySearchCarBinding) this.mBaseBinding).realpriceImage.setBackgroundResource(R.drawable.realprice_unselect);
        ((ActivitySearchCarBinding) this.mBaseBinding).titlebar.setBackgroundResource(R.drawable.gradient_ff9718_ff890a);
        this.mAdapter.setType(this.mPriceType);
        SearchCarManager.newInstance().setPriceType(this.mPriceType);
    }

    private void realPriceSelect() {
        this.mPriceType = SearchCarManager.REAL_PRICE_TYPE;
        ((ActivitySearchCarBinding) this.mBaseBinding).guidepriceImage.setBackgroundResource(R.drawable.guideprice_unselect);
        ((ActivitySearchCarBinding) this.mBaseBinding).realpriceImage.setBackgroundResource(R.drawable.realprice_select);
        ((ActivitySearchCarBinding) this.mBaseBinding).titlebar.setBackgroundResource(R.drawable.gradient_62d1fc_33a4f7);
        this.mAdapter.setType(this.mPriceType);
        SearchCarManager.newInstance().setPriceType(this.mPriceType);
    }

    private void initTypeGroupData() {
        this.mTypeGroupList = new ArrayList();
        for (int i = 0; i < this.mList.size(); i++) {
            SearchCarGroup group = (SearchCarGroup) this.mList.get(i);
            SearchCarTypeGroup typeGroup;
            if (i == 0) {
                typeGroup = new SearchCarTypeGroup();
                typeGroup.position = i;
                typeGroup.isSelect = true;
                typeGroup.name = group.typeName;
                this.mTypeGroupList.add(typeGroup);
                group.isGroupFirst = true;
                group.groupPosition = 0;
            } else {
                SearchCarGroup group1 = (SearchCarGroup) this.mList.get(i - 1);
                if (!group1.typeName.equals(group.typeName)) {
                    typeGroup = new SearchCarTypeGroup();
                    typeGroup.position = i;
                    typeGroup.name = group.typeName;
                    this.mTypeGroupList.add(typeGroup);
                    group1.isGroupFirst = true;
                    group1.groupPosition = this.mTypeGroupList.size() - 2;
                    group.isGroupFirst = true;
                    group.groupPosition = this.mTypeGroupList.size() - 1;
                }
            }
        }
        this.mTypeGroupAdapter = new SearchCarTypeGroupAdapter(this, this.mTypeGroupList);
        ((ActivitySearchCarBinding) this.mBaseBinding).groupRecyclerView.setAdapter(this.mTypeGroupAdapter);
        this.mTypeGroupLinearLayoutManager = new LinearLayoutManager(this);
        ((ActivitySearchCarBinding) this.mBaseBinding).groupRecyclerView.setLayoutManager(this.mTypeGroupLinearLayoutManager);
        this.mTypeGroupAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                MoreConditionActivity.this.mIsClickScroll = true;
                int scrollPosition = -1;
                for (int i = 0; i < MoreConditionActivity.this.mTypeGroupList.size(); i++) {
                    SearchCarTypeGroup typeGroup = (SearchCarTypeGroup) MoreConditionActivity.this.mTypeGroupList.get(i);
                    if (i == position) {
                        typeGroup.isSelect = true;
                        scrollPosition = typeGroup.position;
                    } else {
                        typeGroup.isSelect = false;
                    }
                }
                MoreConditionActivity.this.mTypeGroupAdapter.notifyDataSetChanged();
                if (scrollPosition != -1) {
                    ((LinearLayoutManager) ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).recyclerView.getLayoutManager()).scrollToPositionWithOffset(scrollPosition + 1, 0);
                }
            }
        });
    }

    public void onChange() {
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("searchtype", Integer.valueOf(this.mPriceType));
        if (!"0".equals(SearchCarManager.newInstance().getCurrentPriceValueString())) {
            map.put("pricerange", SearchCarManager.newInstance().getCurrentPriceValueString());
        }
        map.put("searchterms", SearchCarManager.newInstance().getAllConditionData());
        map.put("cityid", String.valueOf(MapUtil.newInstance().getCurrentCityId()));
        int brandid = SearchCarManager.newInstance().getBrandId();
        if (brandid != 0) {
            map.put("brandid", String.valueOf(brandid));
        }
        map.put("containoption", SearchCarManager.newInstance().hasSelection() ? "1" : "0");
        if (((ActivitySearchCarBinding) this.mBaseBinding).progress.isShown()) {
            FengApplication.getInstance().cancelRequest("carsearch/seriescount/");
            this.mCancelRequest = true;
        } else {
            this.mCancelRequest = false;
        }
        FengApplication.getInstance().httpRequest("carsearch/seriescount/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
                ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).progress.setVisibility(0);
            }

            public void onFinish() {
                if (!MoreConditionActivity.this.mCancelRequest) {
                    ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).progress.setVisibility(8);
                }
                MoreConditionActivity.this.mCancelRequest = false;
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int count = jsonResult.getJSONObject("body").getJSONObject("cars").getInt("count");
                        if (count == 0) {
                            ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).resultText.setText(R.string.unfind_cars);
                        } else {
                            ((ActivitySearchCarBinding) MoreConditionActivity.this.mBaseBinding).resultText.setText("共" + count + "款车系符合条件");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onFLowClick(SearchCarBean bean, TextView textView) {
        if (bean.name.equals("自定义")) {
            showPriceDialog(bean);
        } else {
            showLevelDialog(bean);
        }
    }

    public void finish() {
        super.finish();
        boolean flag = ActivityManager.getInstance().hasContainsActivity(SearchCarResultActivity.class);
        if (!this.mStartSearchCarResultAcitivity && !flag) {
            SearchCarManager.newInstance().clearAllCondition();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchConditionEvent event) {
        this.mAdapter.notifyDataSetChanged();
        getData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CloseMoreConditionEvent event) {
        this.mStartSearchCarResultAcitivity = true;
        finish();
    }
}
