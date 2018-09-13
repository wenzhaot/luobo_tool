package com.feng.car.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import com.feng.car.FengApplication;
import com.feng.car.activity.AllProgramActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.HomeOhterFragmentBinding;
import com.feng.car.databinding.LavelHeadLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.AutoFrescoDraweeView;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeOhterLavelFragment extends BaseFragment<HomeOhterFragmentBinding> {
    private List<SnsInfo> mAdSnsList = new ArrayList();
    private CommonPostAdapter mAdater;
    private int mCategoryID = 0;
    private int mCurrentPage = 1;
    private LavelHeadLayoutBinding mHeadLayoutBinding;
    private List<HotShowInfo> mHotShowInfos = new ArrayList();
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private int mLavelID = 0;
    private SnsInfoList mList = new SnsInfoList();
    public int mPageTotal = 0;

    protected int setLayoutId() {
        return 2130903257;
    }

    public static HomeOhterLavelFragment newInstance(int id, int categoryID) {
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putInt(HttpConstant.CATEGORYID, categoryID);
        HomeOhterLavelFragment lavelFragment = new HomeOhterLavelFragment();
        lavelFragment.setArguments(args);
        return lavelFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mLavelID = getArguments().getInt("id");
        this.mCategoryID = getArguments().getInt(HttpConstant.CATEGORYID);
    }

    protected void initView() {
        this.mAdater = new CommonPostAdapter(this.mActivity, this.mList, 0, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setPullRefreshEnabled(false);
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
                HomeOhterLavelFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                HomeOhterLavelFragment.this.mCurrentPage = 1;
                if (HomeOhterLavelFragment.this.mLavelID != 0) {
                    HomeOhterLavelFragment.this.getAdData();
                    return;
                }
                ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
            }
        });
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview) != State.Loading) {
                    if (HomeOhterLavelFragment.this.mCurrentPage <= HomeOhterLavelFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(HomeOhterLavelFragment.this.getActivity(), ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        HomeOhterLavelFragment.this.getSnsData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(HomeOhterLavelFragment.this.getActivity(), ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        this.mHeadLayoutBinding = LavelHeadLayoutBinding.inflate(this.mInflater);
        this.mHeadLayoutBinding.llProgram.setVisibility(8);
        this.mHeadLayoutBinding.adProgram0.getLayoutParams().height = (FengUtil.getScreenWidth(this.mActivity) - getResources().getDimensionPixelSize(2131296258)) / 5;
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeadLayoutBinding.getRoot());
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setRefreshing(true);
    }

    protected void onFragmentFirstVisible() {
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.setPullRefreshEnabled(true);
        ((HomeOhterFragmentBinding) this.mBind).recyclerview.forceToRefresh();
    }

    private void getAdData() {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.PAGEID, String.valueOf(this.mLavelID));
        map.put("datatype", PushConstants.PUSH_TYPE_NOTIFY);
        map.put("pagecode", PushConstants.PUSH_TYPE_NOTIFY);
        FengApplication.getInstance().httpRequest(HttpConstant.ADVERT_ADSERVER, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                HomeOhterLavelFragment.this.getProgramData();
            }

            public void onStart() {
            }

            public void onFinish() {
                HomeOhterLavelFragment.this.getProgramData();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int i;
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (HomeOhterLavelFragment.this.mList.size() > 0) {
                            HomeOhterLavelFragment.this.mList.removeAllAd();
                        }
                        HomeOhterLavelFragment.this.mAdSnsList.clear();
                        JSONArray adData = jsonBody.getJSONArray("data");
                        JSONArray adSns = jsonBody.getJSONArray("snsinfolist");
                        int size = adData.length();
                        int snsSize = adSns.length();
                        for (i = 0; i < size; i++) {
                            SnsInfo snsInfo = new SnsInfo();
                            snsInfo.advertInfo = new AdvertInfo();
                            snsInfo.advertInfo.parser(adData.getJSONObject(i));
                            snsInfo.snstype = 1000;
                            HomeOhterLavelFragment.this.mAdSnsList.add(snsInfo);
                        }
                        if (snsSize > 0) {
                            for (SnsInfo snsInfo1 : HomeOhterLavelFragment.this.mAdSnsList) {
                                if (snsInfo1.advertInfo.isinner != 1) {
                                    snsInfo1.id = snsInfo1.advertInfo.adid;
                                    snsInfo1.resourceid = snsInfo1.advertInfo.adid;
                                    snsInfo1.isflag = 0;
                                } else {
                                    i = 0;
                                    while (i < snsSize) {
                                        if (snsInfo1.advertInfo.contentid == adSns.getJSONObject(i).getInt("resourceid") && snsInfo1.advertInfo.resthype == adSns.getJSONObject(i).getInt("snstype")) {
                                            snsInfo1.parser(adSns.getJSONObject(i));
                                            snsInfo1.snstype = 1000;
                                        }
                                        i++;
                                    }
                                }
                            }
                            return;
                        }
                        return;
                    }
                    HomeOhterLavelFragment.this.mAdSnsList.clear();
                    if (HomeOhterLavelFragment.this.mList.size() > 0) {
                        HomeOhterLavelFragment.this.mList.removeAllAd();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getProgramData() {
        if (this.mCategoryID <= 0) {
            getSnsData();
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.CATEGORYID, String.valueOf(this.mCategoryID));
        map.put("pagesize", String.valueOf(5));
        map.put(HttpConstant.COMMUNITY_SORT_TYPE, String.valueOf(0));
        FengApplication.getInstance().httpRequest(HttpConstant.SNSHOTSHOW_LISTBYCATEGORYID, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) HomeOhterLavelFragment.this.mActivity).showSecondTypeToast(2131231273);
                ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) HomeOhterLavelFragment.this.mActivity).showSecondTypeToast(2131231273);
                ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        HomeOhterLavelFragment.this.parserProgram(jsonResult.getJSONObject("body").getJSONObject(HttpConstant.HOTSHOW));
                        HomeOhterLavelFragment.this.getSnsData();
                        return;
                    }
                    HomeOhterLavelFragment.this.showErrorView();
                    FengApplication.getInstance().checkCode(HttpConstant.SNSHOTSHOW_LISTBYCATEGORYID, code);
                    ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
                } catch (JSONException e) {
                    e.printStackTrace();
                    HomeOhterLavelFragment.this.showErrorView();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SNSHOTSHOW_LISTBYCATEGORYID, content, e);
                }
            }
        });
    }

    private void getSnsData() {
        Map<String, Object> map = new HashMap();
        map.put("tabid", String.valueOf(this.mLavelID));
        map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
        FengApplication.getInstance().httpRequest(HttpConstant.HOME_TABLIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) HomeOhterLavelFragment.this.mActivity).showSecondTypeToast(2131231273);
                ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) HomeOhterLavelFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        HomeOhterLavelFragment.this.parserSns(jsonResult.getJSONObject("body").getJSONObject("snslist"));
                        return;
                    }
                    HomeOhterLavelFragment.this.showErrorView();
                    FengApplication.getInstance().checkCode(HttpConstant.HOME_TABLIST, code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    HomeOhterLavelFragment.this.showErrorView();
                    FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.HOME_TABLIST, content, e);
                }
            }
        });
    }

    private void parserProgram(JSONObject jsonObject) {
        this.mHotShowInfos.clear();
        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
        baseListModel.parser(HotShowInfo.class, jsonObject);
        this.mHotShowInfos.addAll(baseListModel.list);
        this.mHeadLayoutBinding.adProgram0.setVisibility(4);
        this.mHeadLayoutBinding.adProgram1.setVisibility(4);
        this.mHeadLayoutBinding.adProgram2.setVisibility(4);
        this.mHeadLayoutBinding.adProgram3.setVisibility(4);
        this.mHeadLayoutBinding.adProgram4.setVisibility(4);
        int size = this.mHotShowInfos.size();
        if (size > 0) {
            String imageUrl = "";
            AutoFrescoDraweeView autoFrescoDraweeView = null;
            int i = 0;
            while (i < size) {
                final HotShowInfo hotShowInfo = (HotShowInfo) this.mHotShowInfos.get(i);
                imageUrl = FengUtil.getUniformScaleUrl(hotShowInfo.image, FengConstant.IMAGEMIDDLEWIDTH_350, 1.0f);
                if (i == 0) {
                    autoFrescoDraweeView = this.mHeadLayoutBinding.adProgram0;
                } else if (i == 1) {
                    autoFrescoDraweeView = this.mHeadLayoutBinding.adProgram1;
                } else if (i == 2) {
                    autoFrescoDraweeView = this.mHeadLayoutBinding.adProgram2;
                } else if (i == 3) {
                    autoFrescoDraweeView = this.mHeadLayoutBinding.adProgram3;
                } else if (i == 4) {
                    autoFrescoDraweeView = this.mHeadLayoutBinding.adProgram4;
                }
                if (baseListModel.count > 5 && i == 4) {
                    autoFrescoDraweeView.setVisibility(0);
                    autoFrescoDraweeView.setAutoImageURI(Uri.parse("res://com.feng.car/2130838187"));
                    autoFrescoDraweeView.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            Intent intent = new Intent(HomeOhterLavelFragment.this.mActivity, AllProgramActivity.class);
                            intent.putExtra("id", HomeOhterLavelFragment.this.mCategoryID);
                            HomeOhterLavelFragment.this.startActivity(intent);
                        }
                    });
                } else if (i <= 4) {
                    autoFrescoDraweeView.setVisibility(0);
                    autoFrescoDraweeView.setAutoImageURI(Uri.parse(imageUrl));
                    autoFrescoDraweeView.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            Intent intent = new Intent(HomeOhterLavelFragment.this.mActivity, PopularProgramListActivity.class);
                            intent.putExtra(HttpConstant.HOT_SHOW_ID, hotShowInfo.id);
                            HomeOhterLavelFragment.this.startActivity(intent);
                        }
                    });
                }
                i++;
            }
        }
    }

    private void parserSns(JSONObject jsonObject) {
        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
        baseListModel.parser(SnsInfo.class, jsonObject);
        List<SnsInfo> list = baseListModel.list;
        if (this.mCurrentPage == 1) {
            this.mList.clear();
        }
        int oldSize = this.mList.size();
        this.mPageTotal = baseListModel.pagecount;
        if (list.size() > 0) {
            this.mList.addAll(list);
        }
        if (this.mCurrentPage == 1) {
            if (this.mHotShowInfos.size() <= 0) {
                this.mHeadLayoutBinding.llProgram.setVisibility(8);
                if (this.mList.size() == 0) {
                    showNoDataEmpty();
                } else {
                    hideEmpty();
                }
            } else {
                this.mHeadLayoutBinding.llProgram.setVisibility(0);
                hideEmpty();
            }
            initAd();
        } else {
            hideEmpty();
        }
        this.mCurrentPage++;
        if (this.mCurrentPage == 2) {
            this.mAdater.notifyDataSetChanged();
        } else {
            this.mAdater.notifyItemRangeInserted(oldSize, this.mList.size() - oldSize);
        }
    }

    private void initAd() {
        int size = this.mList.size();
        for (SnsInfo info : this.mAdSnsList) {
            if (info.advertInfo.isinner == 1 && this.mList.getPosition(info.getLocalkey()) > -1) {
                this.mList.remove(this.mList.getPosition(info.getLocalkey()));
            }
        }
        for (SnsInfo info2 : this.mAdSnsList) {
            if (info2.advertInfo.pageid == this.mLavelID && info2.advertInfo.pageorder - 1 <= size) {
                this.mList.add(info2.advertInfo.pageorder - 1, info2);
            }
        }
    }

    public void showErrorView() {
        if (((HomeOhterFragmentBinding) this.mBind).emptyView != null) {
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((HomeOhterFragmentBinding) HomeOhterLavelFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    public void showNoDataEmpty() {
        if (((HomeOhterFragmentBinding) this.mBind).emptyView != null) {
            ((HomeOhterFragmentBinding) this.mBind).emptyView.hideEmptyImage();
            ((HomeOhterFragmentBinding) this.mBind).emptyView.hideEmptyButton();
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setEmptyText(2131231300);
            ((HomeOhterFragmentBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((HomeOhterFragmentBinding) this.mBind).emptyView.setVisibility(8);
    }

    public void loginSuccess() {
        super.loginSuccess();
        if (isAdded()) {
            ((HomeOhterFragmentBinding) this.mBind).recyclerview.forceToRefresh();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((HomeOhterFragmentBinding) this.mBind).recyclerview.setIsScrollDown(true);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshUserStatus(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mAdater.refreshSnsInfo(event);
        }
    }
}
