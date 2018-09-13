package com.feng.car.activity;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CircleAccededAdapter;
import com.feng.car.adapter.SearchRecordAdapter;
import com.feng.car.databinding.ActivitySearchResultBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SearchRecordEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView$LScrollListener;
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

public class CircleSearchActivity extends BaseActivity<ActivitySearchResultBinding> {
    public static final String POST_INIT_TYPE_SEL_CIRCLE = "post_init_type_sel_circle";
    private CircleAccededAdapter mAdapter;
    private int mCurrentPage = 1;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private CircleInfoList mList = new CircleInfoList();
    private SearchKey mSearchKey = new SearchKey();
    private List<SearchItem> mSearchRecord = new ArrayList();
    private SearchRecordAdapter mSearchRecordAdapter;
    private List<Integer> mSendSelCircle;
    public int mTotalPage = 0;

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        /* synthetic */ EditChangedListener(CircleSearchActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() <= 0) {
                CircleSearchActivity.this.hideEmptyView();
                CircleSearchActivity.this.showSearchRecord(false);
                return;
            }
            CircleSearchActivity.this.saveSearchRecords();
        }
    }

    private class EditorActionListener implements OnEditorActionListener {
        private EditorActionListener() {
        }

        /* synthetic */ EditorActionListener(CircleSearchActivity x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            if (((String) CircleSearchActivity.this.mSearchKey.searchKey.get()).trim().length() > 0) {
                FengUtil.closeSoftKeyboard(v);
                CircleSearchActivity.this.saveSearchRecords();
                CircleSearchActivity.this.mRootBinding.titleLine.etSearchKey.clearFocus();
                ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview.forceToRefresh();
            }
            return true;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_search_result;
    }

    public void initView() {
        initSearchTitleBar(this.mSearchKey, (int) R.string.search_circle, new EditorActionListener(this, null), new EditChangedListener(this, null));
        showSearchRecord(false);
        String selCircleData = getIntent().getStringExtra(POST_INIT_TYPE_SEL_CIRCLE);
        if (!TextUtils.isEmpty(selCircleData)) {
            this.mSendSelCircle = JsonUtil.fromJson(selCircleData, new TypeToken<List<Integer>>() {
            });
        }
        if (this.mSendSelCircle == null) {
            this.mSendSelCircle = new ArrayList();
        }
        this.mAdapter = new CircleAccededAdapter((Context) this, this.mList.getCircleList(), true);
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (FengConstant.ALREADY_SEL_CIRCLE_NUM >= FengConstant.MAXSELCIRCLENUM) {
                    CircleSearchActivity.this.showThirdTypeToast((int) R.string.max_share_circle_num);
                    return;
                }
                boolean isSelect = CircleSearchActivity.this.mList.get(position).islocalselect.get();
                CircleSearchActivity.this.mList.get(position).islocalselect.set(!isSelect);
                EventBus.getDefault().post(new AddCircleEvent(CircleSearchActivity.this.mList.get(position)));
                if (isSelect) {
                    CircleSearchActivity.this.mSendSelCircle.remove(Integer.valueOf(CircleSearchActivity.this.mList.get(position).id));
                } else {
                    CircleSearchActivity.this.finish();
                }
            }
        });
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview, State.Normal);
                CircleSearchActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                CircleSearchActivity.this.mCurrentPage = 1;
                if (((String) CircleSearchActivity.this.mSearchKey.searchKey.get()).trim().length() > 0) {
                    CircleSearchActivity.this.loadSearchData();
                } else {
                    ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview.refreshComplete();
                }
            }
        });
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (CircleSearchActivity.this.mCurrentPage <= CircleSearchActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(CircleSearchActivity.this, ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        CircleSearchActivity.this.loadSearchData();
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(CircleSearchActivity.this, ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.setLScrollListener(new LRecyclerView$LScrollListener() {
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
    }

    private void loadSearchData() {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mCurrentPage));
        map.put("search", ((String) this.mSearchKey.searchKey.get()).trim());
        FengApplication.getInstance().httpRequest("community/getmorelist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (CircleSearchActivity.this.mList.size() > 0) {
                    CircleSearchActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CircleSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CircleSearchActivity.this.loadSearchData();
                        }
                    });
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).recyclerview, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (CircleSearchActivity.this.mList.size() > 0) {
                    CircleSearchActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    CircleSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            CircleSearchActivity.this.loadSearchData();
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonSearch = jsonResult.getJSONObject("body").getJSONObject("community");
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonSearch);
                        CircleSearchActivity.this.mTotalPage = baseListModel.pagecount;
                        ((ActivitySearchResultBinding) CircleSearchActivity.this.mBaseBinding).rvSearchRecord.setVisibility(8);
                        CircleSearchActivity.this.hideEmptyView();
                        if (CircleSearchActivity.this.mCurrentPage == 1) {
                            CircleSearchActivity.this.mList.clear();
                        }
                        CircleSearchActivity.this.mList.addAll(baseListModel.list);
                        if (CircleSearchActivity.this.mList.size() <= 0) {
                            CircleSearchActivity.this.showEmptyView(R.string.no_search_result);
                        } else {
                            for (Integer intValue : CircleSearchActivity.this.mSendSelCircle) {
                                int pst = CircleSearchActivity.this.mList.getPosition(intValue.intValue());
                                if (pst >= 0) {
                                    CircleSearchActivity.this.mList.get(pst).islocalselect.set(true);
                                }
                            }
                        }
                        CircleSearchActivity.this.mCurrentPage = CircleSearchActivity.this.mCurrentPage + 1;
                        CircleSearchActivity.this.mAdapter.notifyDataSetChanged();
                    } else if (CircleSearchActivity.this.mList.size() > 0) {
                        CircleSearchActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        CircleSearchActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                CircleSearchActivity.this.loadSearchData();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("community/getmorelist/", content, e);
                }
            }
        });
    }

    private void showSearchRecord(boolean isAll) {
        List<SearchItem> list = FengApplication.getInstance().getSparkDB().getSearchReconds(SearchItem.SEARCH_CIRCLE_TYPE);
        this.mSearchRecord.clear();
        this.mSearchRecord.addAll(list);
        if (this.mSearchRecord.size() > 0) {
            ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setVisibility(0);
        } else {
            ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setVisibility(8);
        }
        if (this.mSearchRecordAdapter == null) {
            this.mSearchRecordAdapter = new SearchRecordAdapter(this, this.mSearchRecord);
            ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setAdapter(this.mSearchRecordAdapter);
            ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setLayoutManager(new LinearLayoutManager(this));
            return;
        }
        this.mSearchRecordAdapter.adapterNotifyDataSetChanged(isAll);
    }

    private void saveSearchRecords() {
        SearchItem item = new SearchItem();
        item.content = ((String) this.mSearchKey.searchKey.get()).trim();
        item.type = SearchItem.SEARCH_CIRCLE_TYPE;
        FengApplication.getInstance().getSparkDB().addSearchRecond(item);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchRecordEvent event) {
        if (event.type == 1) {
            showSearchRecord(true);
        } else if (event.type == 3) {
            SearchItem item = event.searchItem;
            if (item.type != SearchItem.SEARCH_SHOW_ALL_RECORD_TYPE && item.type != SearchItem.SEARCH_SHOW_DEL_RECORD_TYPE) {
                this.mCurrentPage = 1;
                this.mTotalPage = 0;
                this.mRootBinding.titleLine.etSearchKey.setText(item.content);
                this.mSearchKey.searchKey.set(item.content);
                FengUtil.closeSoftKeyboard(this.mRootBinding.titleLine.etSearchKey);
                ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setVisibility(8);
                ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.forceToRefresh();
                saveSearchRecords();
            }
        } else {
            FengApplication.getInstance().getSparkDB().deleteAllSerchRecord(SearchItem.SEARCH_CIRCLE_TYPE);
            ((ActivitySearchResultBinding) this.mBaseBinding).rvSearchRecord.setVisibility(8);
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        ((ActivitySearchResultBinding) this.mBaseBinding).recyclerview.forceToRefresh();
    }
}
