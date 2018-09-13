package com.feng.car.fragment;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.LevelConditionActivity;
import com.feng.car.activity.MessageActivity;
import com.feng.car.activity.MoreConditionActivity;
import com.feng.car.activity.PriceConditionActivity;
import com.feng.car.activity.SearchCarResultActivity;
import com.feng.car.activity.SearchNewActivity;
import com.feng.car.adapter.CarBrandItemAdapter;
import com.feng.car.adapter.PhotoTextListAdapter;
import com.feng.car.databinding.FindPageFragmentBinding;
import com.feng.car.databinding.FindPageFragmentHeaderBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.car.CarBrandInfo;
import com.feng.car.entity.car.CarBrandInfoList;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.utils.SearchCarManager;
import com.feng.car.view.selectcar.decoration.TitleWithLetterItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
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
import org.json.JSONException;
import org.json.JSONObject;

public class FindPageFragment extends BaseFragment<FindPageFragmentBinding> {
    private int m72;
    private CarBrandItemAdapter mAdapter;
    private CarBrandInfo mBrandInfo = new CarBrandInfo();
    private CarBrandInfoList mCarBrandEightList = new CarBrandInfoList();
    private CarBrandInfoList mCarBrandList = new CarBrandInfoList();
    private TitleWithLetterItemDecoration mDecoration;
    private boolean mFirstLoad = true;
    private FindPageFragmentHeaderBinding mHeaderViewBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LinearLayoutManager mManager;
    private PhotoTextListAdapter mPhotoTextListAdapter;

    protected int setLayoutId() {
        return 2130903241;
    }

    protected void initView() {
        this.m72 = this.mActivity.getResources().getDimensionPixelSize(2131296837);
        initTitleBar();
        this.mAdapter = new CarBrandItemAdapter(this.mActivity, this.mCarBrandList.getCarBrandInfoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((FindPageFragmentBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        this.mManager = new LinearLayoutManager(this.mActivity);
        ((FindPageFragmentBinding) this.mBind).recyclerview.setLayoutManager(this.mManager);
        ((FindPageFragmentBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((FindPageFragmentBinding) this.mBind).recyclerview.setNoMore(true);
        this.mLRecyclerViewAdapter.removeFooterView(this.mLRecyclerViewAdapter.getFooterView());
        ((FindPageFragmentBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((FindPageFragmentBinding) FindPageFragment.this.mBind).recyclerview, State.Normal);
                FindPageFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                if (FindPageFragment.this.mCarBrandEightList.size() > 0) {
                    FindPageFragment.this.getAllBrandList();
                } else {
                    FindPageFragment.this.getEightBrandData(false);
                }
            }
        });
        this.mHeaderViewBinding = FindPageFragmentHeaderBinding.inflate(LayoutInflater.from(this.mActivity), (ViewGroup) this.mRootView.getRootView(), false);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderViewBinding.getRoot());
        this.mPhotoTextListAdapter = new PhotoTextListAdapter(this.mActivity, this.mCarBrandEightList.getCarBrandInfoList());
        this.mHeaderViewBinding.rvSelectCarPhoto.setAdapter(this.mPhotoTextListAdapter);
        this.mHeaderViewBinding.rvSelectCarPhoto.setLayoutManager(new GridLayoutManager(this.mActivity, 4));
        this.mPhotoTextListAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                FindPageFragment.this.mBrandInfo = FindPageFragment.this.mCarBrandEightList.get(position);
                SearchCarManager.newInstance().setCurrentBrand(FindPageFragment.this.mBrandInfo);
                FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, SearchCarResultActivity.class));
            }
        });
        ((FindPageFragmentBinding) this.mBind).recyclerview.setRefreshing(true);
        ((FindPageFragmentBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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
        this.mHeaderViewBinding.tvCarPrice.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, PriceConditionActivity.class));
                FindPageFragment.this.mActivity.overridePendingTransition(2130968586, 0);
            }
        });
        this.mHeaderViewBinding.tvCarRank.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, LevelConditionActivity.class));
                FindPageFragment.this.mActivity.overridePendingTransition(2130968586, 0);
            }
        });
        this.mHeaderViewBinding.tvMoreCondition.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, MoreConditionActivity.class));
                FindPageFragment.this.mActivity.overridePendingTransition(2130968586, 0);
            }
        });
    }

    private void initTitleBar() {
        ((FindPageFragmentBinding) this.mBind).tvSearch.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(FindPageFragment.this.mActivity, SearchNewActivity.class);
                intent.putExtra("type", 1);
                FindPageFragment.this.startActivity(intent);
                FindPageFragment.this.mActivity.overridePendingTransition(2130968592, 0);
            }
        });
        ((FindPageFragmentBinding) this.mBind).ivMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, MessageActivity.class));
            }
        });
    }

    public void onResume() {
        super.onResume();
        changeDot();
    }

    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible && isAdded() && !this.mFirstLoad && this.mCarBrandList.size() > 0) {
            getEightBrandData(true);
        }
    }

    private void initAdater() {
        if (this.mDecoration == null) {
            this.mDecoration = new TitleWithLetterItemDecoration(this.mActivity, 2, this.mCarBrandList.getCarBrandInfoList());
            ((FindPageFragmentBinding) this.mBind).recyclerview.addItemDecoration(this.mDecoration);
            ((FindPageFragmentBinding) this.mBind).indexBar.setmPressedShowTextView(((FindPageFragmentBinding) this.mBind).tvSideBarHint).setNeedRealIndex(false).setHeadNum(2).setmLayoutManager(this.mManager).setmSourceDatas(this.mCarBrandList.getCarBrandInfoList());
            this.mAdapter.setOnItemClickLister(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    FindPageFragment.this.mBrandInfo = FindPageFragment.this.mCarBrandList.get(position);
                    SearchCarManager.newInstance().setCurrentBrand(FindPageFragment.this.mBrandInfo);
                    FindPageFragment.this.startActivity(new Intent(FindPageFragment.this.mActivity, SearchCarResultActivity.class));
                }
            });
        }
        setLastFlagInCarBrand();
    }

    private void setLastFlagInCarBrand() {
        for (int i = 0; i < this.mCarBrandList.size(); i++) {
            if (i != this.mCarBrandList.size() - 1) {
                if (this.mCarBrandList.get(i).abc.equals(this.mCarBrandList.get(i + 1).abc)) {
                    this.mCarBrandList.get(i).posflag = 0;
                } else {
                    this.mCarBrandList.get(i).posflag = 1;
                }
            }
        }
        this.mCarBrandList.get(this.mCarBrandList.size() - 1).posflag = 0;
    }

    private void getAllBrandList() {
        FengApplication.getInstance().httpRequest(HttpConstant.CAR_BRANDLIST, new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                FindPageFragment.this.showNetErrorView();
            }

            public void onStart() {
            }

            public void onFinish() {
                ((FindPageFragmentBinding) FindPageFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((FindPageFragmentBinding) FindPageFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                FindPageFragment.this.showNetErrorView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        FindPageFragment.this.mFirstLoad = false;
                        FindPageFragment.this.hideEmpty();
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("brand");
                        BaseListModel<CarBrandInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarBrandInfo.class, jsonBrand);
                        List<CarBrandInfo> list = baseListModel.list;
                        if (list != null && list.size() > 0) {
                            FindPageFragment.this.mCarBrandList.clear();
                            FindPageFragment.this.mCarBrandList.addAll(list);
                            FindPageFragment.this.initAdater();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.CAR_BRANDLIST, code);
                    FindPageFragment.this.showNetErrorView();
                } catch (JSONException e) {
                    e.printStackTrace();
                    FindPageFragment.this.showNetErrorView();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.CAR_BRANDLIST, content, e);
                }
            }
        });
    }

    private void getEightBrandData(final boolean isRefreshCar) {
        Map<String, Object> map = new HashMap();
        String strIDs = SharedUtil.getString(this.mActivity, "see_history_cars");
        List<Integer> list = null;
        if (!TextUtils.isEmpty(strIDs)) {
            list = JsonUtil.fromJson(strIDs, new TypeToken<ArrayList<Integer>>() {
            });
        }
        if (list == null) {
            list = new ArrayList();
        }
        map.put("seriesids", list);
        FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_HOME_BRAND, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (FindPageFragment.this.mCarBrandList.size() > 0) {
                    ((BaseActivity) FindPageFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    FindPageFragment.this.showNetErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (FindPageFragment.this.mCarBrandList.size() > 0) {
                    ((BaseActivity) FindPageFragment.this.mActivity).showSecondTypeToast(2131231273);
                } else {
                    FindPageFragment.this.showNetErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("search").getJSONObject("brand");
                        BaseListModel<CarBrandInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CarBrandInfo.class, jsonBrand);
                        List<CarBrandInfo> list = baseListModel.list;
                        if (list != null) {
                            FindPageFragment.this.changeCarModule(true);
                            FindPageFragment.this.mCarBrandEightList.clear();
                            if (list.size() > 8) {
                                FindPageFragment.this.mCarBrandEightList.addAll(list.subList(0, 8));
                            } else {
                                FindPageFragment.this.mCarBrandEightList.addAll(list);
                            }
                            FindPageFragment.this.mPhotoTextListAdapter.notifyDataSetChanged();
                            if (!isRefreshCar) {
                                FindPageFragment.this.getAllBrandList();
                                return;
                            }
                            return;
                        }
                        FindPageFragment.this.changeCarModule(false);
                    } else if (FindPageFragment.this.mCarBrandList.size() > 0) {
                        FengApplication.getInstance().checkCode(HttpConstant.SEARCH_HOME_BRAND, code);
                    } else {
                        FindPageFragment.this.showNetErrorView();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (FindPageFragment.this.mCarBrandList.size() > 0) {
                        ((BaseActivity) FindPageFragment.this.mActivity).showSecondTypeToast(2131231190);
                    } else {
                        FindPageFragment.this.showNetErrorView();
                    }
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCH_HOME_BRAND, content, e);
                }
            }
        });
    }

    public void showNetErrorView() {
        if (((FindPageFragmentBinding) this.mBind).emptyView != null) {
            ((FindPageFragmentBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((FindPageFragmentBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((FindPageFragmentBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    FindPageFragment.this.getEightBrandData(false);
                }
            });
            ((FindPageFragmentBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((FindPageFragmentBinding) this.mBind).emptyView.setVisibility(8);
    }

    private void changeCarModule(boolean isShow) {
        int i;
        int i2 = 8;
        TextView textView = this.mHeaderViewBinding.tvSearchCar;
        if (isShow) {
            i = 0;
        } else {
            i = 8;
        }
        textView.setVisibility(i);
        RecyclerView recyclerView = this.mHeaderViewBinding.rvSelectCarPhoto;
        if (isShow) {
            i2 = 0;
        }
        recyclerView.setVisibility(i2);
        this.mHeaderViewBinding.llSearchCar.setVisibility(0);
    }

    private void changeDot() {
        if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
            ((FindPageFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(8);
            return;
        }
        ((FindPageFragmentBinding) this.mBind).tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
        ((FindPageFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeRefreshEvent event) {
        if (event.mIndex == 1) {
            ((FindPageFragmentBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((FindPageFragmentBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        changeDot();
    }
}
