package com.feng.car.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.VoiceChooseCarAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.stub.StubApp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class OldDriverChooseCarNextActivity extends BaseActivity<CommonRecyclerviewBinding> {
    public static String CAR_SERIES_NAME = "CAR_SERIES_NAME";
    public static String RECOMMEND_TYPE = "RECOMMEND_TYPE";
    public static int RECOMMEND_TYPE_CHOOSE_CAR = 2001;
    public static int RECOMMEND_TYPE_RECOMMEND_CAR = ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE;
    public static int TIPS_TYPE_RECOMMEND_CAR = ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE;
    private final int SEL_CAR_TYPE = 0;
    private final int SEL_RECOMMEND_TYPE = 1;
    private VoiceChooseCarAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private int mPageTotal = 0;
    private int mSearchType = 0;
    private int mSeriesID;
    private int mType;

    static {
        StubApp.interface11(2586);
    }

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void getLocalIntentData() {
        this.mType = getIntent().getIntExtra(RECOMMEND_TYPE, RECOMMEND_TYPE_CHOOSE_CAR);
        this.mSeriesID = getIntent().getIntExtra("carsid", 0);
    }

    public void initView() {
        if (this.mType == RECOMMEND_TYPE_CHOOSE_CAR) {
            initNormalTitleBar((int) R.string.oldDriver_helpYou_chooseCar);
            this.mSearchType = 0;
        } else if (this.mType == RECOMMEND_TYPE_RECOMMEND_CAR) {
            initNormalTitleBar(getString(R.string.oldDriver_recommended_carModel));
            this.mSearchType = 1;
        } else {
            initNormalTitleBar(getIntent().getStringExtra(CAR_SERIES_NAME) + " 选车攻略");
        }
        this.mAdapter = new VoiceChooseCarAdapter(this, this.mList.getSnsList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview, State.Normal);
                OldDriverChooseCarNextActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                OldDriverChooseCarNextActivity.this.mCurrentPage = 1;
                OldDriverChooseCarNextActivity.this.getData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (OldDriverChooseCarNextActivity.this.mCurrentPage <= OldDriverChooseCarNextActivity.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(OldDriverChooseCarNextActivity.this, ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        OldDriverChooseCarNextActivity.this.getData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(OldDriverChooseCarNextActivity.this, ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
    }

    private void getData() {
        String api;
        Map<String, Object> map = new HashMap();
        if (this.mType == TIPS_TYPE_RECOMMEND_CAR) {
            map.put("carsid", Integer.valueOf(this.mSeriesID));
            api = "car/selection/strategy/";
        } else {
            map.put("type", String.valueOf(this.mSearchType));
            api = "snsautodiscuss/newlist/";
        }
        map.put("page", String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(api, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (OldDriverChooseCarNextActivity.this.mList.size() > 0) {
                    OldDriverChooseCarNextActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    OldDriverChooseCarNextActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
                ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (OldDriverChooseCarNextActivity.this.mList.size() > 0) {
                    OldDriverChooseCarNextActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    OldDriverChooseCarNextActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject object = new JSONObject(content);
                    int code = object.getInt("code");
                    if (code == 1) {
                        JSONObject snsObject = object.getJSONObject("body").getJSONObject("sns");
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, snsObject);
                        List<SnsInfo> list = baseListModel.list;
                        OldDriverChooseCarNextActivity.this.mPageTotal = baseListModel.pagecount;
                        OldDriverChooseCarNextActivity.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (OldDriverChooseCarNextActivity.this.mCurrentPage == 1) {
                                OldDriverChooseCarNextActivity.this.mList.clear();
                            }
                            OldDriverChooseCarNextActivity.this.mCurrentPage = OldDriverChooseCarNextActivity.this.mCurrentPage + 1;
                            OldDriverChooseCarNextActivity.this.mList.addAll(list);
                            OldDriverChooseCarNextActivity.this.mAdapter.notifyDataSetChanged();
                        } else if (OldDriverChooseCarNextActivity.this.mCurrentPage == 1) {
                            OldDriverChooseCarNextActivity.this.showEmptyView(R.string.no_data);
                        }
                    } else if (OldDriverChooseCarNextActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("snshotshow/getlist/", code);
                    } else {
                        OldDriverChooseCarNextActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (OldDriverChooseCarNextActivity.this.mList.size() <= 0) {
                        OldDriverChooseCarNextActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((CommonRecyclerviewBinding) OldDriverChooseCarNextActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/getlist/", content, e);
                }
            }
        });
    }

    public String getLogCurrentPage() {
        if (this.mType == RECOMMEND_TYPE_CHOOSE_CAR) {
            return "app_sel_car_driver";
        }
        if (this.mType == RECOMMEND_TYPE_RECOMMEND_CAR) {
            return "app_sel_car_recommend";
        }
        return "app_car_strategy";
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
