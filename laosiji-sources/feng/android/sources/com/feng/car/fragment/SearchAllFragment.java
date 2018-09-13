package com.feng.car.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.adapter.CircleAccededAdapter;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.adapter.PhotoTextUserListAdapter;
import com.feng.car.adapter.ProgramListAdapter;
import com.feng.car.adapter.ProgramListAdapter.HotShowFollowListener;
import com.feng.car.databinding.CommonRecyclerviewBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.databinding.NewSearchAllHeaderBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.hotshow.HotShowInfoList;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.NewSearchSwitchEvent;
import com.feng.car.event.ProgramFollowEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.UmengConstans;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView.LScrollListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.common.a;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.ContextUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchAllFragment extends BaseFragment<CommonRecyclerviewBinding> implements HotShowFollowListener {
    private CommonPostAdapter mAdater;
    private Dialog mCommonDialog;
    private int mCurrentCityId = 131;
    private int mCurrentPage = 1;
    private NewSearchAllHeaderBinding mHeaderBinding;
    private int mHotShowid = 0;
    private boolean mIsEmpty = true;
    private boolean mIsSetNotification = false;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private SnsInfoList mList = new SnsInfoList();
    private DialogOpenNotifyBinding mOpenNotifyBinding;
    public int mPageTotal = 0;
    private PhotoTextUserListAdapter mPhotoTextUserListAdapter;
    private ProgramListAdapter mProgramAdapter;
    private HotShowInfoList mProgramList = new HotShowInfoList();
    private String mSearchKey = "";
    private CircleAccededAdapter mTopicAdapter;
    private CircleInfoList mTopicList = new CircleInfoList();
    private List<UserInfo> mUserInfoList = new ArrayList();

    protected int setLayoutId() {
        return 2130903204;
    }

    public static SearchAllFragment newInstance(String searchKey) {
        Bundle args = new Bundle();
        args.putString(HttpConstant.SEARCH_KEY, searchKey);
        SearchAllFragment allSearchFragment = new SearchAllFragment();
        allSearchFragment.setArguments(args);
        return allSearchFragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mSearchKey = getArguments().getString(FengConstant.FENGTYPE, "");
    }

    protected void initView() {
        this.mHeaderBinding = NewSearchAllHeaderBinding.inflate(this.mInflater);
        this.mAdater = new CommonPostAdapter(getActivity(), this.mList, 0, true, getLogGatherInfo());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdater);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.addItemDecoration(new SpacesItemDecoration(this.mActivity));
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshProgressStyle(2);
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, State.Normal);
                SearchAllFragment.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                SearchAllFragment.this.mCurrentPage = 1;
                if (TextUtils.isEmpty(SearchAllFragment.this.mSearchKey)) {
                    ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, State.Normal);
                    return;
                }
                SearchAllFragment.this.mIsEmpty = true;
                SearchAllFragment.this.loadSearchData();
            }
        });
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setLScrollListener(new LScrollListener() {
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
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview) != State.Loading) {
                    if (SearchAllFragment.this.mCurrentPage <= SearchAllFragment.this.mPageTotal) {
                        RecyclerViewStateUtils.setFooterViewState(SearchAllFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, 20, State.Loading, null);
                        SearchAllFragment.this.getSnsData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(SearchAllFragment.this.getActivity(), ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        ((CommonRecyclerviewBinding) this.mBind).recyclerview.setRefreshing(true);
    }

    public void onResume() {
        super.onResume();
        if (this.mIsSetNotification) {
            this.mIsSetNotification = false;
            openRemind();
        }
    }

    private void loadSearchData() {
        if (!TextUtils.isEmpty(this.mSearchKey)) {
            Map<String, Object> map = new HashMap();
            String strKey = this.mSearchKey;
            Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(strKey);
            while (emoticon.find()) {
                String key = emoticon.group();
                String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                if (!TextUtils.isEmpty(value)) {
                    strKey = strKey.replace(key, value);
                }
            }
            map.put("search", strKey);
            map.put(HttpConstant.CITYID, String.valueOf(this.mCurrentCityId));
            map.put("type", String.valueOf(1));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    SearchAllFragment.this.showErrorView();
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonObject = new JSONObject(content);
                        if (jsonObject.getInt("code") == 1) {
                            JSONObject searchJson = jsonObject.getJSONObject("body").getJSONObject("search");
                            SearchAllFragment.this.resetViewGone();
                            if (searchJson.has(HttpConstant.COMMUNITY) && !searchJson.isNull(HttpConstant.COMMUNITY)) {
                                SearchAllFragment.this.parserTopic(searchJson.getJSONObject(HttpConstant.COMMUNITY));
                            }
                            if (searchJson.has(HttpConstant.USER)) {
                                SearchAllFragment.this.parserUser(searchJson.getJSONObject(HttpConstant.USER));
                            }
                            if (searchJson.has(HttpConstant.HOTSHOW)) {
                                SearchAllFragment.this.parserHot(searchJson.getJSONObject(HttpConstant.HOTSHOW));
                            }
                            if (searchJson.has("sns")) {
                                SearchAllFragment.this.parserSns(searchJson.getJSONObject("sns"));
                            }
                            if (SearchAllFragment.this.mIsEmpty) {
                                SearchAllFragment.this.showNoDataEmpty();
                                return;
                            } else {
                                SearchAllFragment.this.hideEmpty();
                                return;
                            }
                        }
                        SearchAllFragment.this.showErrorView();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCH_YWF_INDEX_API, content, e);
                        SearchAllFragment.this.showErrorView();
                    }
                }
            });
        }
    }

    private void getSnsData() {
        if (!TextUtils.isEmpty(this.mSearchKey)) {
            Map<String, Object> map = new HashMap();
            String strKey = this.mSearchKey;
            Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(strKey);
            while (emoticon.find()) {
                String key = emoticon.group();
                String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
                if (!TextUtils.isEmpty(value)) {
                    strKey = strKey.replace(key, value);
                }
            }
            map.put("search", strKey);
            map.put(HttpConstant.PAGE, String.valueOf(this.mCurrentPage));
            map.put("type", String.valueOf(7));
            FengApplication.getInstance().httpRequest(HttpConstant.SEARCH_YWF_INDEX_API, map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    ((BaseActivity) SearchAllFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    ((BaseActivity) SearchAllFragment.this.mActivity).showSecondTypeToast(2131231273);
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            SearchAllFragment.this.parserSns(jsonResult.getJSONObject("body").getJSONObject("search").getJSONObject("sns"));
                            return;
                        }
                        FengApplication.getInstance().checkCode(HttpConstant.SEARCH_YWF_INDEX_API, code);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog(HttpConstant.SEARCH_YWF_INDEX_API, content, e);
                    }
                }
            });
        }
    }

    private void parserUser(JSONObject jsonObject) {
        BaseListModel<UserInfo> userList = new BaseListModel();
        userList.parser(UserInfo.class, jsonObject);
        List<UserInfo> list = userList.list;
        this.mUserInfoList.clear();
        if (this.mPhotoTextUserListAdapter == null) {
            this.mPhotoTextUserListAdapter = new PhotoTextUserListAdapter(getActivity(), this.mUserInfoList);
            this.mHeaderBinding.rvUserPhoto.setLayoutManager(new GridLayoutManager(getActivity(), 4));
            this.mHeaderBinding.rvUserPhoto.setAdapter(this.mPhotoTextUserListAdapter);
            this.mHeaderBinding.tvFindMoreUser.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    EventBus.getDefault().post(new NewSearchSwitchEvent(5));
                }
            });
            this.mPhotoTextUserListAdapter.setOnItemClickLister(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    ((UserInfo) SearchAllFragment.this.mUserInfoList.get(position)).intentToPersonalHome(SearchAllFragment.this.getActivity());
                }
            });
        }
        if (list.size() == 0) {
            this.mHeaderBinding.rlRelatedUser.setVisibility(8);
            return;
        }
        this.mIsEmpty = false;
        this.mHeaderBinding.rlRelatedUser.setVisibility(0);
        this.mHeaderBinding.vLineUser.setVisibility(0);
        if (list.size() > 4) {
            this.mUserInfoList.addAll(list.subList(0, 4));
        } else {
            this.mUserInfoList.addAll(list);
        }
        if (userList.count > 4) {
            this.mHeaderBinding.tvFindMoreUser.setVisibility(0);
        } else {
            this.mHeaderBinding.tvFindMoreUser.setVisibility(8);
        }
        this.mPhotoTextUserListAdapter.notifyDataSetChanged();
    }

    private void parserTopic(JSONObject jsonObject) throws JSONException {
        boolean showMore;
        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
        baseListModel.parser(CircleInfo.class, jsonObject);
        this.mTopicList.clear();
        if (baseListModel.list.size() > 3) {
            this.mTopicList.addAll(baseListModel.list.subList(0, 3));
            showMore = true;
        } else {
            this.mTopicList.addAll(baseListModel.list);
            showMore = false;
        }
        if (this.mTopicAdapter == null) {
            this.mTopicAdapter = new CircleAccededAdapter(this.mActivity, this.mTopicList.getCircleList(), CircleAccededAdapter.NORMAL_TYPE);
            this.mHeaderBinding.reTopic.setLayoutManager(new LinearLayoutManager(this.mActivity));
            this.mHeaderBinding.reTopic.setAdapter(this.mTopicAdapter);
            this.mTopicAdapter.setOnItemClickLister(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    SearchAllFragment.this.mTopicList.get(position).intentToCircleFinalPage(SearchAllFragment.this.getActivity());
                }
            });
            this.mHeaderBinding.tvSeeAll.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    EventBus.getDefault().post(new NewSearchSwitchEvent(2));
                }
            });
        } else {
            this.mTopicAdapter.notifyDataSetChanged();
        }
        if (this.mTopicList.size() > 0) {
            this.mIsEmpty = false;
            this.mHeaderBinding.llTopic.setVisibility(0);
            if (showMore) {
                this.mHeaderBinding.tvSeeAll.setVisibility(0);
                return;
            } else {
                this.mHeaderBinding.tvSeeAll.setVisibility(8);
                return;
            }
        }
        this.mHeaderBinding.llTopic.setVisibility(8);
    }

    private void parserHot(JSONObject jsonObject) {
        BaseListModel<HotShowInfo> baseListModel = new BaseListModel();
        baseListModel.parser(HotShowInfo.class, jsonObject);
        if (baseListModel.list.size() > 0) {
            this.mIsEmpty = false;
            this.mHeaderBinding.rlHotProgram.setVisibility(0);
            this.mProgramList.clear();
            this.mProgramList.addAll(baseListModel.list);
            if (this.mProgramAdapter == null) {
                this.mProgramAdapter = new ProgramListAdapter(this.mActivity, this.mProgramList.getHotShowInfoList());
                this.mHeaderBinding.rvProgram.setLayoutManager(new LinearLayoutManager(this.mActivity));
                this.mHeaderBinding.rvProgram.setAdapter(this.mProgramAdapter);
                this.mProgramAdapter.setOnItemClickLister(new OnItemClickListener() {
                    public void onItemClick(View view, int position) {
                        HotShowInfo info = SearchAllFragment.this.mProgramList.get(position);
                        Intent intent = new Intent(SearchAllFragment.this.mActivity, PopularProgramListActivity.class);
                        intent.putExtra(HttpConstant.HOT_SHOW_ID, info.id);
                        intent.putExtra(HttpConstant.HOT_SHOW_NAME, info.name);
                        SearchAllFragment.this.startActivity(intent);
                    }
                });
                return;
            }
            this.mProgramAdapter.notifyDataSetChanged();
            return;
        }
        this.mHeaderBinding.rlHotProgram.setVisibility(8);
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
            this.mIsEmpty = false;
            this.mList.addAll(list);
        }
        this.mCurrentPage++;
        if (this.mCurrentPage == 2) {
            this.mAdater.notifyDataSetChanged();
        } else {
            this.mAdater.notifyItemRangeInserted(oldSize, this.mList.size() - oldSize);
        }
    }

    private void resetViewGone() {
        this.mHeaderBinding.llTopic.setVisibility(8);
        this.mHeaderBinding.rlRelatedUser.setVisibility(8);
        this.mHeaderBinding.rlHotProgram.setVisibility(8);
    }

    public void showErrorView() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((CommonRecyclerviewBinding) SearchAllFragment.this.mBind).recyclerview.forceToRefresh();
                }
            });
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void showNoDataEmpty() {
        if (((CommonRecyclerviewBinding) this.mBind).emptyView != null) {
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyImage();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.hideEmptyButton();
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setEmptyText(2131231300);
            ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(0);
        }
    }

    private void hideEmpty() {
        ((CommonRecyclerviewBinding) this.mBind).emptyView.setVisibility(8);
    }

    public void checkSearchKey(String searchKey, int cityID) {
        if (isAdded() && !TextUtils.isEmpty(searchKey)) {
            if (!searchKey.equals(this.mSearchKey) || this.mCurrentCityId != cityID) {
                hideEmpty();
                this.mSearchKey = searchKey;
                this.mCurrentCityId = cityID;
                ((CommonRecyclerviewBinding) this.mBind).recyclerview.forceToRefresh();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (event != null && event.isHandOperation) {
            ((CommonRecyclerviewBinding) this.mBind).recyclerview.setIsScrollDown(true);
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ProgramFollowEvent event) {
        if (this.mProgramList.size() > 0) {
            int position = this.mProgramList.getPosition(event.id);
            if (position >= 0) {
                HotShowInfo info = this.mProgramList.get(position);
                this.mHotShowid = info.id;
                info.isfollow.set(event.isFollow);
                info.isremind.set(event.isRemind);
                this.mProgramAdapter.notifyDataSetChanged();
            }
        }
    }

    private void showRemindDialog() {
        if (this.mOpenNotifyBinding == null) {
            this.mOpenNotifyBinding = DialogOpenNotifyBinding.inflate(LayoutInflater.from(this.mActivity));
            this.mOpenNotifyBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SearchAllFragment.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.openText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SearchAllFragment.this.openRemind();
                    SearchAllFragment.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.cancelText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SearchAllFragment.this.mCommonDialog.dismiss();
                }
            });
        }
        if (this.mCommonDialog == null) {
            this.mCommonDialog = new Dialog(this.mActivity, 2131361986);
            this.mCommonDialog.setCanceledOnTouchOutside(true);
            this.mCommonDialog.setCancelable(true);
            Window window = this.mCommonDialog.getWindow();
            window.setGravity(17);
            window.setContentView(this.mOpenNotifyBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mCommonDialog.show();
    }

    private void openRemind() {
        if (FengUtil.isNotificationAuthorityEnabled(this.mActivity)) {
            setRemind(this.mHotShowid);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(2131231401), false));
        CommonDialog.showCommonDialog(this.mActivity, getString(2131231403), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(CommonNetImpl.FLAG_AUTH);
                    intent.setData(Uri.fromParts(a.c, ContextUtil.getPackageName(), null));
                    SearchAllFragment.this.startActivity(intent);
                    SearchAllFragment.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void setRemind(final int hotshowid) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.HOT_SHOW_ID, String.valueOf(hotshowid));
        map.put("type", String.valueOf(0));
        FengApplication.getInstance().httpRequest(HttpConstant.SET_REMIND, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) SearchAllFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) SearchAllFragment.this.mActivity).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        MobclickAgent.onEvent(SearchAllFragment.this.mActivity, UmengConstans.PROGRAM_REMIND);
                        ((BaseActivity) SearchAllFragment.this.mActivity).showFirstTypeToast(2131231402);
                        int position = SearchAllFragment.this.mProgramList.getPosition(hotshowid);
                        if (position >= 0) {
                            SearchAllFragment.this.mProgramList.get(position).isremind.set(1);
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.POPULAR_PROGRAM_DETAIL_LIST, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void onFollowChnage(int id, int isfollow, int isRemind) {
        if (isfollow != 1) {
            int position = this.mProgramList.getPosition(id);
            if (position >= 0) {
                this.mProgramList.get(position).isremind.set(0);
            }
        } else if (isRemind != 1) {
            this.mHotShowid = id;
            showRemindDialog();
        }
    }
}
