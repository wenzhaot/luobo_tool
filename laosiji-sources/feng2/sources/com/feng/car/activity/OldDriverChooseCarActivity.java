package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.OldDriverChooseCarAdapter;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class OldDriverChooseCarActivity extends BaseActivity<CommonRecyclerviewBinding> {
    private final int SEL_CAR_TYPE = 0;
    private final int SEL_RECOMMEND_TYPE = 1;
    private OldDriverChooseCarAdapter mAdapter;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();

    public int setBaseContentView() {
        return R.layout.common_recyclerview;
    }

    public void initView() {
        initCircleSearchTitleBar(R.string.search_subjectOfCar_tips, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(OldDriverChooseCarActivity.this, SearchNewActivity.class);
                intent.putExtra("type", 3);
                OldDriverChooseCarActivity.this.startActivity(intent);
                OldDriverChooseCarActivity.this.overridePendingTransition(R.anim.fade_in, 0);
            }
        });
        this.mAdapter = new OldDriverChooseCarAdapter(this, this.mList.getSnsList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setNoMore(true);
        ((CommonRecyclerviewBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview, State.Normal);
                OldDriverChooseCarActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                OldDriverChooseCarActivity.this.getData(0);
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

    private void getData(final int type) {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(type));
        if (type == 0) {
            map.put("pagesize", String.valueOf(3));
        } else {
            map.put("pagesize", String.valueOf(10));
        }
        FengApplication.getInstance().httpRequest("snsautodiscuss/newlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (OldDriverChooseCarActivity.this.mList.size() > 0) {
                    OldDriverChooseCarActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    OldDriverChooseCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                        }
                    });
                }
                ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                if (type == 1) {
                    ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview, State.Normal);
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (OldDriverChooseCarActivity.this.mList.size() > 0) {
                    OldDriverChooseCarActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    OldDriverChooseCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.forceToRefresh();
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
                        OldDriverChooseCarActivity.this.hideEmptyView();
                        if (list != null && list.size() > 0) {
                            if (type == 0) {
                                OldDriverChooseCarActivity.this.mList.clear();
                                ((SnsInfo) list.get(0)).discussinfo.localtype = 1;
                            } else {
                                ((SnsInfo) list.get(0)).discussinfo.localtype = 2;
                            }
                            OldDriverChooseCarActivity.this.mList.addAll(list);
                            OldDriverChooseCarActivity.this.mAdapter.notifyDataSetChanged();
                        } else if (type == 1 && OldDriverChooseCarActivity.this.mList.size() <= 0) {
                            OldDriverChooseCarActivity.this.showEmptyView(R.string.no_data);
                        }
                        if (type == 0) {
                            OldDriverChooseCarActivity.this.getData(1);
                        }
                    } else if (OldDriverChooseCarActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("snshotshow/getlist/", code);
                    } else {
                        OldDriverChooseCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (OldDriverChooseCarActivity.this.mList.size() <= 0) {
                        OldDriverChooseCarActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                ((CommonRecyclerviewBinding) OldDriverChooseCarActivity.this.mBaseBinding).recyclerview.forceToRefresh();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("snshotshow/getlist/", content, e);
                }
            }
        });
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
