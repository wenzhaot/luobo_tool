package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonPostAdapter;
import com.feng.car.databinding.ActivityPersonalSearchBinding;
import com.feng.car.databinding.HeaderPersonalSearchBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsInfoList;
import com.feng.car.event.SnsInfoModifyEvent;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.view.SpacesItemDecoration;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalSearchActivity extends BaseActivity<ActivityPersonalSearchBinding> {
    private CommonPostAdapter mCommonPostAdapter;
    private int mCurrentPage = 1;
    private HeaderPersonalSearchBinding mHeaderBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private String mLastSearchKey = "";
    private SnsInfoList mList = new SnsInfoList();
    private SearchKey mSearchKey = new SearchKey();
    private int mSearchResultCount;
    private int mTotalPage = 0;
    private int mUserID;
    private String mUserName;

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        /* synthetic */ EditChangedListener(PersonalSearchActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.length() == 0) {
                ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).rlParent.setBackgroundResource(R.color.color_70_000000);
                ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.setVisibility(8);
                PersonalSearchActivity.this.mList.clear();
                PersonalSearchActivity.this.mCommonPostAdapter.notifyDataSetChanged();
                PersonalSearchActivity.this.hideEmptyView();
            }
        }
    }

    private class EditorActionListener implements OnEditorActionListener {
        private EditorActionListener() {
        }

        /* synthetic */ EditorActionListener(PersonalSearchActivity x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            if (((String) PersonalSearchActivity.this.mSearchKey.searchKey.get()).trim().length() > 0) {
                FengUtil.closeSoftKeyboard(v);
                if (!PersonalSearchActivity.this.mLastSearchKey.equals(PersonalSearchActivity.this.mSearchKey.searchKey.get())) {
                    ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).rlParent.setBackgroundResource(R.color.color_f3f3f3);
                    ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.setVisibility(0);
                    PersonalSearchActivity.this.startSearch();
                    PersonalSearchActivity.this.mLastSearchKey = (String) PersonalSearchActivity.this.mSearchKey.searchKey.get();
                }
            }
            return true;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_personal_search;
    }

    public void getLocalIntentData() {
        Intent intent = getIntent();
        this.mUserID = intent.getIntExtra("userid", 0);
        this.mUserName = intent.getStringExtra("name");
    }

    public void initView() {
        closeSwip();
        if (FengApplication.getInstance().isLoginUser() && FengApplication.getInstance().getUserInfo() != null && FengApplication.getInstance().getUserInfo().id == this.mUserID) {
            initSearchTitleBar(this.mSearchKey, "搜索我的内容", new EditorActionListener(this, null), new EditChangedListener(this, null));
        } else {
            initSearchTitleBar(this.mSearchKey, "搜索@" + this.mUserName + "的内容", new EditorActionListener(this, null), new EditChangedListener(this, null));
        }
        this.mHeaderBinding = HeaderPersonalSearchBinding.inflate(this.mInflater, ((ActivityPersonalSearchBinding) this.mBaseBinding).rlParent, false);
        this.mCommonPostAdapter = new CommonPostAdapter(this, this.mList, 0, true, this.mLogGatherInfo);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mCommonPostAdapter);
        this.mLRecyclerViewAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, 1));
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.addItemDecoration(new SpacesItemDecoration(this));
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setRefreshProgressStyle(2);
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView, State.Normal);
                PersonalSearchActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                if (TextUtils.isEmpty((CharSequence) PersonalSearchActivity.this.mSearchKey.searchKey.get())) {
                    ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView, State.Normal);
                    return;
                }
                PersonalSearchActivity.this.mCurrentPage = 1;
                PersonalSearchActivity.this.getData(true);
            }
        });
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView) != State.Loading) {
                    if (PersonalSearchActivity.this.mCurrentPage > PersonalSearchActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(PersonalSearchActivity.this, ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView, 20, State.TheEnd, null);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(PersonalSearchActivity.this, ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView, 20, State.Loading, null);
                    PersonalSearchActivity.this.getData(false);
                }
            }
        });
        ((ActivityPersonalSearchBinding) this.mBaseBinding).rlParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.isShown()) {
                    PersonalSearchActivity.this.finish();
                }
            }
        });
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.setRefreshing(true);
    }

    private void getData(final boolean isRefresh) {
        String strKey = (String) this.mSearchKey.searchKey.get();
        if (TextUtils.isEmpty(strKey)) {
            RecyclerViewStateUtils.setFooterViewState(((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView, State.Normal);
            ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.refreshComplete();
            return;
        }
        Map<String, Object> map = new HashMap();
        Matcher emoticon = HttpConstant.PATTERN_EMOTICON_TEXT.matcher(strKey);
        while (emoticon.find()) {
            String key = emoticon.group();
            String value = (String) EmoticonsRule.sXhsEmoticonTextCodeHashMap.get(key);
            if (!TextUtils.isEmpty(value)) {
                strKey = strKey.replace(key, value);
            }
        }
        map.put("search", strKey);
        map.put("userid", String.valueOf(this.mUserID));
        FengApplication.getInstance().httpRequest("search/ywf/content/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PersonalSearchActivity.this.hideProgress();
                ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.refreshComplete();
                if (PersonalSearchActivity.this.mList.size() <= 0) {
                    PersonalSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.forceToRefresh();
                        }
                    });
                } else {
                    PersonalSearchActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView, State.Normal);
                ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.refreshComplete();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (PersonalSearchActivity.this.mList.size() <= 0) {
                    PersonalSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.forceToRefresh();
                        }
                    });
                } else {
                    PersonalSearchActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    if (jsonObject.getInt("code") == 1) {
                        JSONObject snsJson = jsonObject.getJSONObject("body").getJSONObject("search").getJSONObject("sns");
                        PersonalSearchActivity.this.mSearchResultCount = snsJson.getInt("count");
                        PersonalSearchActivity.this.mHeaderBinding.tvCount.setText(PersonalSearchActivity.this.getString(R.string.search_result_count, new Object[]{Integer.valueOf(PersonalSearchActivity.this.mSearchResultCount)}));
                        PersonalSearchActivity.this.hideEmptyView();
                        BaseListModel<SnsInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(SnsInfo.class, snsJson);
                        PersonalSearchActivity.this.mTotalPage = baseListModel.pagecount;
                        if (isRefresh) {
                            PersonalSearchActivity.this.mList.clear();
                        }
                        int oldSize = PersonalSearchActivity.this.mList.size();
                        List<SnsInfo> list = baseListModel.list;
                        if (list.size() > 0) {
                            PersonalSearchActivity.this.mList.addAll(list);
                            PersonalSearchActivity.this.mHeaderBinding.getRoot().setVisibility(0);
                            PersonalSearchActivity.this.mCurrentPage = PersonalSearchActivity.this.mCurrentPage + 1;
                            if (isRefresh) {
                                PersonalSearchActivity.this.mCommonPostAdapter.notifyDataSetChanged();
                            } else {
                                PersonalSearchActivity.this.mCommonPostAdapter.notifyItemRangeInserted(oldSize, PersonalSearchActivity.this.mList.size() - oldSize);
                            }
                        } else if (PersonalSearchActivity.this.mCurrentPage == 1) {
                            PersonalSearchActivity.this.mHeaderBinding.getRoot().setVisibility(8);
                            PersonalSearchActivity.this.showEmptyView(R.string.no_search_result);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("search/ywf/content/", content, e);
                    PersonalSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            ((ActivityPersonalSearchBinding) PersonalSearchActivity.this.mBaseBinding).recyclerView.forceToRefresh();
                        }
                    });
                }
            }
        });
    }

    private void startSearch() {
        this.mRootBinding.titleLine.etSearchKey.setFocusable(false);
        this.mRootBinding.titleLine.etSearchKey.setFocusableInTouchMode(false);
        this.mRootBinding.titleLine.etSearchKey.clearFocus();
        this.mRootBinding.titleLine.etSearchKey.setFocusable(true);
        this.mRootBinding.titleLine.etSearchKey.setFocusableInTouchMode(true);
        ((ActivityPersonalSearchBinding) this.mBaseBinding).recyclerView.forceToRefresh();
    }

    public String getLogCurrentPage() {
        return "app_profile_search?userid=" + this.mUserID + "&keyword=" + ((String) this.mSearchKey.searchKey.get());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoModifyEvent event) {
        if (event != null) {
            this.mCommonPostAdapter.modifySnsInfo(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null) {
            this.mCommonPostAdapter.refreshSnsInfo(event);
        }
    }
}
