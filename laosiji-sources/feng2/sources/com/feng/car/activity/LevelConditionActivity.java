package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.LevelConditionAdapter;
import com.feng.car.databinding.ActivityLevelConditionBinding;
import com.feng.car.databinding.LevelDialogLayoutBinding;
import com.feng.car.entity.searchcar.SearchCarBean;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.flowlayout.DLFlowLayout$OnSelectListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class LevelConditionActivity extends BaseActivity<ActivityLevelConditionBinding> {
    private boolean mCancelRequest = false;
    private List<SearchCarBean> mHistoryList = new ArrayList();
    private LevelConditionAdapter mLevelAdapter;
    private LevelDialogLayoutBinding mLevelBinding;
    private Dialog mLevelDialog;
    private List<SearchCarBean> mList = new ArrayList();
    private boolean mStartSearchCarResultAcitivity = false;

    private void showLevelDialog(SearchCarBean bean) {
        if (this.mLevelBinding == null) {
            this.mLevelBinding = LevelDialogLayoutBinding.inflate(LayoutInflater.from(this));
            this.mLevelBinding.confirm.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    LevelConditionActivity.this.mLevelDialog.dismiss();
                }
            });
        }
        if (this.mLevelDialog == null) {
            this.mLevelDialog = CommonDialog.showViewDialog(this, this.mLevelBinding.getRoot());
        }
        this.mLevelBinding.groupName.setText("哪种" + bean.name);
        this.mLevelBinding.flowlayout.setMar(this.mResources.getDimensionPixelSize(R.dimen.default_25PX));
        this.mLevelBinding.flowlayout.setCountPerLine(4);
        this.mLevelBinding.flowlayout.setIsSingle(false);
        this.mLevelBinding.flowlayout.setCanCancelAll(true);
        this.mLevelBinding.flowlayout.setFlowData(bean.subList);
        this.mLevelBinding.flowlayout.setType(SearchCarManager.newInstance().getPriceType());
        this.mLevelBinding.flowlayout.setOnSelectListener(new DLFlowLayout$OnSelectListener() {
            public void onSelect(int position) {
                SearchCarManager.newInstance().refreshUnlimited();
                LevelConditionActivity.this.mLevelAdapter.notifyDataSetChanged();
                LevelConditionActivity.this.getData();
            }

            public void onUnSelect(int position) {
                SearchCarManager.newInstance().refreshUnlimited();
                LevelConditionActivity.this.mLevelAdapter.notifyDataSetChanged();
                LevelConditionActivity.this.getData();
            }

            public void onOutLimit() {
            }
        });
        this.mLevelDialog.show();
    }

    public int setBaseContentView() {
        return R.layout.activity_level_condition;
    }

    public void initView() {
        closeSwip();
        initNormalTitleBar((int) R.string.car_rank_class, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().recoveryLevelList(LevelConditionActivity.this.mHistoryList);
                LevelConditionActivity.this.finish();
            }
        });
        changeLeftIcon(R.drawable.icon_close);
        this.mRootBinding.titleLine.titlebarRightParent.setVisibility(0);
        this.mRootBinding.titleLine.tvRightText.setVisibility(0);
        this.mRootBinding.titleLine.tvRightText.setText(R.string.reset);
        this.mRootBinding.titleLine.tvRightText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().setLevelUnlimited();
                LevelConditionActivity.this.mLevelAdapter.notifyDataSetChanged();
                LevelConditionActivity.this.getData();
            }
        });
        ((ActivityLevelConditionBinding) this.mBaseBinding).resultLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchCarManager.newInstance().refreshUnlimited();
                if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                    LevelConditionActivity.this.mStartSearchCarResultAcitivity = true;
                    LevelConditionActivity.this.startActivity(new Intent(LevelConditionActivity.this, SearchCarResultActivity.class));
                } else {
                    EventBus.getDefault().post(new SearchCarEvent(3));
                }
                LevelConditionActivity.this.finish();
            }
        });
        this.mHistoryList = SearchCarManager.newInstance().deepCopyBeanList(SearchCarManager.newInstance().getLeveList());
        List<SearchCarBean> list = SearchCarManager.newInstance().getLeveList();
        this.mList = list.subList(1, list.size());
        this.mLevelAdapter = new LevelConditionAdapter(this, this.mList);
        ((ActivityLevelConditionBinding) this.mBaseBinding).recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        ((ActivityLevelConditionBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLevelAdapter);
        this.mLevelAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                SearchCarBean bean = (SearchCarBean) LevelConditionActivity.this.mList.get(position);
                if (bean.hasChecked()) {
                    if (bean.canClick) {
                        LevelConditionActivity.this.showLevelDialog(bean);
                        return;
                    }
                    bean.isChecked = false;
                    LevelConditionActivity.this.mLevelAdapter.notifyDataSetChanged();
                    LevelConditionActivity.this.getData();
                } else if (bean.canClick) {
                    LevelConditionActivity.this.showLevelDialog(bean);
                } else {
                    bean.isChecked = true;
                    LevelConditionActivity.this.mLevelAdapter.notifyDataSetChanged();
                    LevelConditionActivity.this.getData();
                }
            }
        });
        getData();
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("searchtype", Integer.valueOf(SearchCarManager.newInstance().getPriceType()));
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
        if (((ActivityLevelConditionBinding) this.mBaseBinding).progress.isShown()) {
            FengApplication.getInstance().cancelRequest("carsearch/seriescount/");
            this.mCancelRequest = true;
        } else {
            this.mCancelRequest = false;
        }
        FengApplication.getInstance().httpRequest("carsearch/seriescount/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
                ((ActivityLevelConditionBinding) LevelConditionActivity.this.mBaseBinding).progress.setVisibility(0);
            }

            public void onFinish() {
                if (!LevelConditionActivity.this.mCancelRequest) {
                    ((ActivityLevelConditionBinding) LevelConditionActivity.this.mBaseBinding).progress.setVisibility(8);
                }
                LevelConditionActivity.this.mCancelRequest = false;
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int count = jsonResult.getJSONObject("body").getJSONObject("cars").getInt("count");
                        if (count == 0) {
                            ((ActivityLevelConditionBinding) LevelConditionActivity.this.mBaseBinding).resultText.setText(R.string.unfind_cars);
                        } else {
                            ((ActivityLevelConditionBinding) LevelConditionActivity.this.mBaseBinding).resultText.setText("共" + count + "款车系符合条件");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void finish() {
        super.finish();
        boolean flag = ActivityManager.getInstance().hasContainsActivity(SearchCarResultActivity.class);
        if (!this.mStartSearchCarResultAcitivity && !flag) {
            SearchCarManager.newInstance().clearAllCondition();
        }
    }
}
