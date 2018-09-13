package com.feng.car.activity;

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
import com.feng.car.adapter.HorizonTopicAdapter;
import com.feng.car.adapter.RecommendedTopicAdapter;
import com.feng.car.databinding.ActivityAddSubjectBinding;
import com.feng.car.databinding.ActivityAddSubjectHeaderBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleInfoList;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.util.RecyclerViewStateUtils;
import com.github.jdsjlzx.view.LoadingFooter.State;
import com.google.gson.reflect.TypeToken;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class AddSubjectActivity extends BaseActivity<ActivityAddSubjectBinding> {
    public static final String DATA_CONTEXT = "data_context";
    public static String SEL_CIRCLE_DATA = "sel_circle_data";
    private boolean SEARCH_MODE;
    private LRecyclerViewAdapter mAdapter;
    private String mContextData;
    private int mCurrentPageSearch = 1;
    private ActivityAddSubjectHeaderBinding mHeaderBinding;
    private CircleInfoList mHistoryList = new CircleInfoList();
    private CircleInfoList mList = new CircleInfoList();
    private LRecyclerViewAdapter mSearchAdapter;
    private List<CircleInfo> mSearchList = new ArrayList();
    private List<CircleInfo> mSelectedList;
    private HorizonTopicAdapter mTopicHistoryAdapter;
    private HorizonTopicAdapter mTopicSelectedAdapter;
    private int mTotalPageSearch;

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        /* synthetic */ EditChangedListener(AddSubjectActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() <= 0) {
                AddSubjectActivity.this.mSearchList.clear();
                AddSubjectActivity.this.hideSearchMode();
                return;
            }
            AddSubjectActivity.this.mCurrentPageSearch = 1;
            AddSubjectActivity.this.searchRequest();
        }
    }

    static {
        StubApp.interface11(2072);
    }

    public int setBaseContentView() {
        return R.layout.activity_add_subject;
    }

    public void initView() {
        hideDefaultTitleBar();
        closeSwip();
        String strData = getIntent().getStringExtra(SEL_CIRCLE_DATA);
        this.mContextData = getIntent().getStringExtra(DATA_CONTEXT);
        if (!TextUtils.isEmpty(strData)) {
            this.mSelectedList = JsonUtil.fromJson(strData, new TypeToken<List<CircleInfo>>() {
            });
        }
        if (this.mSelectedList == null) {
            this.mSelectedList = new ArrayList();
        }
        initHead();
        FengConstant.ALREADY_SEL_CIRCLE_NUM = this.mSelectedList.size();
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(0);
        this.mTopicSelectedAdapter = new HorizonTopicAdapter(this, this.mSelectedList, HorizonTopicAdapter.TYPE_SELECTED);
        ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setLayoutManager(manager);
        ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setAdapter(this.mTopicSelectedAdapter);
        this.mTopicSelectedAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                AddSubjectActivity.this.processSelect((CircleInfo) AddSubjectActivity.this.mSelectedList.get(position));
            }
        });
        RecommendedTopicAdapter topicAdapter = new RecommendedTopicAdapter(this, this.mList.getCircleList(), false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.mAdapter = new LRecyclerViewAdapter(topicAdapter);
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubject.setLayoutManager(layoutManager);
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubject.setAdapter(this.mAdapter);
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubject.setPullRefreshEnabled(false);
        this.mAdapter.addHeaderView(this.mHeaderBinding.getRoot());
        this.mAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                AddSubjectActivity.this.processSelect(AddSubjectActivity.this.mList.get(position));
            }
        });
        ((ActivityAddSubjectBinding) this.mBaseBinding).back.setOnClickListener(this);
        ((ActivityAddSubjectBinding) this.mBaseBinding).tvFinish.setOnClickListener(this);
        initSearchMode();
        getData();
        getHistoryData();
    }

    private void getHistoryData() {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(3));
        FengApplication.getInstance().httpRequest("community/getlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonTag = jsonResult.getJSONObject("body").getJSONObject("community");
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonTag);
                        AddSubjectActivity.this.hideEmpty();
                        AddSubjectActivity.this.mHistoryList.addAll(baseListModel.list);
                        if (AddSubjectActivity.this.mSelectedList.size() > 0) {
                            for (CircleInfo circleInfo : AddSubjectActivity.this.mSelectedList) {
                                int position = AddSubjectActivity.this.mHistoryList.getPosition(circleInfo.id);
                                if (position != -1) {
                                    AddSubjectActivity.this.mHistoryList.get(position).islocalselect.set(true);
                                }
                            }
                        }
                        if (AddSubjectActivity.this.mHistoryList.size() > 0) {
                            AddSubjectActivity.this.mHeaderBinding.rlHistorySubject.setVisibility(0);
                            AddSubjectActivity.this.mHeaderBinding.rvHistorySubject.setVisibility(0);
                        }
                        AddSubjectActivity.this.mTopicHistoryAdapter.notifyDataSetChanged();
                    } else if (AddSubjectActivity.this.mHistoryList.size() > 0) {
                        FengApplication.getInstance().checkCode("community/getlist/", code);
                    } else {
                        AddSubjectActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                AddSubjectActivity.this.getData();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (AddSubjectActivity.this.mHistoryList.size() > 0) {
                        AddSubjectActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    } else {
                        AddSubjectActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                AddSubjectActivity.this.getData();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("community/getlist/", content, e);
                }
            }
        });
    }

    private void clearHistory() {
        FengApplication.getInstance().httpRequest("theme/clearselfhistory/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") == 1) {
                        AddSubjectActivity.this.mHistoryList.clear();
                        AddSubjectActivity.this.mHeaderBinding.rlHistorySubject.setVisibility(8);
                        AddSubjectActivity.this.mHeaderBinding.rvHistorySubject.setVisibility(8);
                        AddSubjectActivity.this.mTopicHistoryAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    AddSubjectActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    FengApplication.getInstance().upLoadTryCatchLog("theme/clearselfhistory/", content, e);
                }
            }
        });
    }

    private void processSelect(CircleInfo info) {
        if (this.mSelectedList.contains(info)) {
            this.mSelectedList.remove(info);
            if (this.mHistoryList.getPosition(info.id) != -1) {
                this.mHistoryList.get(this.mHistoryList.getPosition(info.id)).islocalselect.set(false);
            }
            if (this.mList.getPosition(info.id) != -1) {
                this.mList.get(this.mList.getPosition(info.id)).islocalselect.set(false);
            }
        } else if (this.mSelectedList.size() > 9) {
            showSecondTypeToast((int) R.string.most_ten_topic);
            return;
        } else {
            this.mSelectedList.add(info);
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.scrollToPosition(this.mSelectedList.size() - 1);
            if (this.mHistoryList.getPosition(info.id) != -1) {
                this.mHistoryList.get(this.mHistoryList.getPosition(info.id)).islocalselect.set(true);
            }
            if (this.mList.getPosition(info.id) != -1) {
                this.mList.get(this.mList.getPosition(info.id)).islocalselect.set(true);
            }
        }
        if (this.mSelectedList.size() > 0) {
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(0);
        } else {
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(8);
        }
        this.mTopicSelectedAdapter.notifyDataSetChanged();
        this.mTopicHistoryAdapter.notifyDataSetChanged();
        ((ActivityAddSubjectBinding) this.mBaseBinding).tvFinish.setText("完成(" + this.mSelectedList.size() + "/10)");
    }

    private void getData() {
        Map<String, Object> map = new HashMap();
        map.put("type", String.valueOf(4));
        map.put("context", this.mContextData);
        FengApplication.getInstance().httpRequest("community/getlist/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (AddSubjectActivity.this.mList.size() > 0) {
                    AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddSubjectActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            AddSubjectActivity.this.getData();
                        }
                    });
                }
                ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubject.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubject, State.Normal);
            }

            public void onStart() {
            }

            public void onFinish() {
                ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubject.refreshComplete();
                RecyclerViewStateUtils.setFooterViewState(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubject, State.Normal);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (AddSubjectActivity.this.mList.size() > 0) {
                    AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AddSubjectActivity.this.showErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonTag = jsonResult.getJSONObject("body").getJSONObject("community");
                        BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                        baseListModel.parser(CircleInfo.class, jsonTag);
                        AddSubjectActivity.this.hideEmpty();
                        AddSubjectActivity.this.mList.addAll(baseListModel.list);
                        if (AddSubjectActivity.this.mSelectedList.size() > 0) {
                            for (CircleInfo circleInfo : AddSubjectActivity.this.mSelectedList) {
                                int position = AddSubjectActivity.this.mList.getPosition(circleInfo.id);
                                if (position != -1) {
                                    AddSubjectActivity.this.mList.get(position).islocalselect.set(true);
                                }
                            }
                        }
                        if (AddSubjectActivity.this.mList.size() > 0) {
                            AddSubjectActivity.this.mHeaderBinding.tvRecommendTopicTips.setVisibility(0);
                        } else {
                            AddSubjectActivity.this.mHeaderBinding.tvRecommendTopicTips.setVisibility(8);
                        }
                        AddSubjectActivity.this.mAdapter.notifyDataSetChanged();
                    } else if (AddSubjectActivity.this.mList.size() > 0) {
                        FengApplication.getInstance().checkCode("community/getlist/", code);
                    } else {
                        AddSubjectActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                AddSubjectActivity.this.getData();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (AddSubjectActivity.this.mList.size() > 0) {
                        AddSubjectActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                    } else {
                        AddSubjectActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                AddSubjectActivity.this.getData();
                            }
                        });
                    }
                    FengApplication.getInstance().upLoadTryCatchLog("community/getlist/", content, e);
                }
            }
        });
    }

    private void initHead() {
        this.mHeaderBinding = ActivityAddSubjectHeaderBinding.inflate(this.mInflater, ((ActivityAddSubjectBinding) this.mBaseBinding).parent, false);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        manager2.setOrientation(0);
        this.mTopicHistoryAdapter = new HorizonTopicAdapter(this, this.mHistoryList.getCircleList(), HorizonTopicAdapter.TYPE_HISTORY);
        this.mTopicHistoryAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                AddSubjectActivity.this.processSelect(AddSubjectActivity.this.mHistoryList.get(position));
            }
        });
        this.mHeaderBinding.rvHistorySubject.setLayoutManager(manager2);
        this.mHeaderBinding.rvHistorySubject.setAdapter(this.mTopicHistoryAdapter);
        this.mHeaderBinding.ibClear.setOnClickListener(this);
        if (this.mSelectedList.size() > 0) {
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(0);
            ((ActivityAddSubjectBinding) this.mBaseBinding).tvFinish.setText("完成(" + this.mSelectedList.size() + "/10)");
            return;
        }
        ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(8);
    }

    public void onSingleClick(View v) {
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.back /*2131624216*/:
                FengUtil.closeSoftKeyboard(((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey);
                finish();
                return;
            case R.id.tv_finish /*2131624218*/:
                if (this.mSelectedList.size() > 0) {
                    FengUtil.closeSoftKeyboard(((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey);
                    EventBus.getDefault().post(new AddCircleEvent(this.mSelectedList));
                    finish();
                    return;
                }
                return;
            case R.id.ib_clear /*2131624224*/:
                clearHistory();
                return;
            default:
                return;
        }
    }

    private void initSearchMode() {
        if (this.mSearchAdapter == null) {
            this.mSearchAdapter = new LRecyclerViewAdapter(new RecommendedTopicAdapter(this, this.mSearchList, true));
            this.mSearchAdapter.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    AddSubjectActivity.this.processSelect((CircleInfo) AddSubjectActivity.this.mSearchList.get(position));
                    ((CircleInfo) AddSubjectActivity.this.mSearchList.get(position)).islocalselect.set(!((CircleInfo) AddSubjectActivity.this.mSearchList.get(position)).islocalselect.get());
                    if (((CircleInfo) AddSubjectActivity.this.mSearchList.get(position)).islocalcreate.get()) {
                        ((CircleInfo) AddSubjectActivity.this.mSearchList.get(position)).islocalcreate.set(false);
                    }
                    ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).etSearchKey.setText("");
                    FengUtil.closeSoftKeyboard(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).etSearchKey);
                }
            });
            ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setAdapter(this.mSearchAdapter);
            ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setLayoutManager(new LinearLayoutManager(this));
            ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setPullRefreshEnabled(false);
            ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setOnLoadMoreListener(new OnLoadMoreListener() {
                public void onLoadMore() {
                    if (RecyclerViewStateUtils.getFooterViewState(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch) != State.Loading) {
                        if (AddSubjectActivity.this.mCurrentPageSearch <= AddSubjectActivity.this.mTotalPageSearch) {
                            RecyclerViewStateUtils.setFooterViewState(AddSubjectActivity.this, ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch, 20, State.Loading, null);
                            AddSubjectActivity.this.searchRequest();
                            return;
                        }
                        RecyclerViewStateUtils.setFooterViewState(AddSubjectActivity.this, ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch, 20, State.TheEnd, null);
                    }
                }
            });
            ((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey.setOnEditorActionListener(new OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId != 3) {
                        return false;
                    }
                    if (((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).etSearchKey.getText().toString().trim().length() > 0) {
                        FengUtil.closeSoftKeyboard(v);
                        AddSubjectActivity.this.mRootBinding.titleLine.etSearchKey.clearFocus();
                    }
                    return true;
                }
            });
            ((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey.addTextChangedListener(new EditChangedListener(this, null));
        }
    }

    private void showSearchMode() {
        this.SEARCH_MODE = true;
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubject.setVisibility(8);
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setVisibility(0);
        ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(8);
    }

    private void hideSearchMode() {
        FengUtil.closeSoftKeyboard(((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey);
        this.SEARCH_MODE = false;
        hideEmpty();
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubject.setVisibility(0);
        ((ActivityAddSubjectBinding) this.mBaseBinding).lrcAddSubjectSearch.setVisibility(8);
        if (this.mSelectedList.size() > 0) {
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(0);
        } else {
            ((ActivityAddSubjectBinding) this.mBaseBinding).rvSelectedSubject.setVisibility(8);
        }
    }

    private void searchRequest() {
        String strKey = ((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey.getText().toString();
        if (!TextUtils.isEmpty(strKey)) {
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
            map.put("page", String.valueOf(this.mCurrentPageSearch));
            map.put("type", String.valueOf(5));
            final String finalStrKey = strKey;
            FengApplication.getInstance().httpRequest("search/ywf/indexapi/", map, new OkHttpResponseCallback() {
                public void onNetworkError() {
                    if (AddSubjectActivity.this.mSearchList.size() > 0) {
                        AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        AddSubjectActivity.this.showErrorView();
                    }
                    ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch, State.Normal);
                }

                public void onStart() {
                }

                public void onFinish() {
                    ((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch.refreshComplete();
                    RecyclerViewStateUtils.setFooterViewState(((ActivityAddSubjectBinding) AddSubjectActivity.this.mBaseBinding).lrcAddSubjectSearch, State.Normal);
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    if (AddSubjectActivity.this.mSearchList.size() > 0) {
                        AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        AddSubjectActivity.this.showErrorView();
                    }
                }

                public void onSuccess(int statusCode, String content) {
                    try {
                        JSONObject object = new JSONObject(content);
                        if (object.getInt("code") == 1) {
                            AddSubjectActivity.this.hideEmpty();
                            JSONObject jsonSearch = object.getJSONObject("body").getJSONObject("search");
                            if (jsonSearch.has("community") && !jsonSearch.isNull("community")) {
                                BaseListModel<CircleInfo> baseListModel = new BaseListModel();
                                baseListModel.parser(CircleInfo.class, jsonSearch.getJSONObject("community"));
                                AddSubjectActivity.this.mTotalPageSearch = baseListModel.pagecount;
                                if (AddSubjectActivity.this.mCurrentPageSearch == 1) {
                                    AddSubjectActivity.this.mSearchList.clear();
                                    boolean tmp = false;
                                    for (CircleInfo info : baseListModel.list) {
                                        if (info.name.equals(finalStrKey)) {
                                            tmp = true;
                                            break;
                                        }
                                    }
                                    if (!tmp) {
                                        AddSubjectActivity.this.createLocalTopic();
                                    }
                                }
                                AddSubjectActivity.this.mSearchList.addAll(baseListModel.list);
                                for (CircleInfo circleInfo : AddSubjectActivity.this.mSearchList) {
                                    if (AddSubjectActivity.this.mSelectedList.contains(circleInfo)) {
                                        circleInfo.islocalselect.set(true);
                                    }
                                }
                                AddSubjectActivity.this.mCurrentPageSearch = AddSubjectActivity.this.mCurrentPageSearch + 1;
                                AddSubjectActivity.this.mSearchAdapter.notifyDataSetChanged();
                                AddSubjectActivity.this.showSearchMode();
                            } else if (AddSubjectActivity.this.mCurrentPageSearch == 1) {
                                AddSubjectActivity.this.mSearchList.clear();
                                AddSubjectActivity.this.showNoDataEmpty();
                            }
                        } else if (AddSubjectActivity.this.mSearchList.size() > 0) {
                            AddSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        } else {
                            AddSubjectActivity.this.showErrorView();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (AddSubjectActivity.this.mSearchList.size() > 0) {
                            AddSubjectActivity.this.showSecondTypeToast((int) R.string.analysis_data_failed);
                        } else {
                            AddSubjectActivity.this.showErrorView();
                        }
                        FengApplication.getInstance().upLoadTryCatchLog("search/ywf/indexapi/", content, e);
                    }
                }
            });
        }
    }

    private void createLocalTopic() {
        this.mSearchList.clear();
        CircleInfo tmp = new CircleInfo();
        tmp.name = ((ActivityAddSubjectBinding) this.mBaseBinding).etSearchKey.getText().toString();
        tmp.islocalcreate.set(true);
        this.mSearchList.add(0, tmp);
    }

    private void showDefaultTitle() {
        initNormalTitleBar("");
        ((ActivityAddSubjectBinding) this.mBaseBinding).rlTitleBar.setVisibility(8);
    }

    private void hideDefaultTitle() {
        hideDefaultTitleBar();
        ((ActivityAddSubjectBinding) this.mBaseBinding).rlTitleBar.setVisibility(0);
    }

    private void showNoDataEmpty() {
        showDefaultTitle();
        showEmptyView(R.string.no_search_result);
    }

    private void showErrorView() {
        showDefaultTitle();
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (AddSubjectActivity.this.SEARCH_MODE) {
                    AddSubjectActivity.this.searchRequest();
                } else {
                    AddSubjectActivity.this.getData();
                }
            }
        });
    }

    private void hideEmpty() {
        hideDefaultTitle();
        hideEmptyView();
    }
}
