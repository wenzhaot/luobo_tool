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
import com.feng.car.adapter.AtUserAdater;
import com.feng.car.databinding.AtSearchUserHeadLayoutBinding;
import com.feng.car.databinding.AtUserLayoutBinding;
import com.feng.car.entity.BaseListModel;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.event.AtUserNameEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
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
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class AtUserActivity extends BaseActivity<AtUserLayoutBinding> {
    private AtUserAdater mAdapter;
    private int mCurrentPage = 1;
    private AtSearchUserHeadLayoutBinding mHeadBinding;
    private LRecyclerViewAdapter mLRecyclerViewAdapter;
    private LRecyclerViewAdapter mLSearchRecyclerViewAdapter;
    private UserInfoList mList = new UserInfoList();
    private AtUserAdater mSearchAdapter;
    private SearchKey mSearchKey = new SearchKey();
    private UserInfoList mSearchList = new UserInfoList();
    private int mTotalPage;
    private TextWatcher watcher = new TextWatcher() {
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            AtUserActivity.this.loadSearchUser();
        }
    };

    private class EditorActionListener implements OnEditorActionListener {
        private EditorActionListener() {
        }

        /* synthetic */ EditorActionListener(AtUserActivity x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            return false;
        }
    }

    public int setBaseContentView() {
        return R.layout.at_user_layout;
    }

    public void initView() {
        initNormalTitleBar("联系人");
        initNormalLeftTitleBar("取消");
        this.mAdapter = new AtUserAdater(this, this.mList.getUserInfoList());
        this.mLRecyclerViewAdapter = new LRecyclerViewAdapter(this.mAdapter);
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(this.mLRecyclerViewAdapter);
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setRefreshProgressStyle(2);
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setOnRefreshListener(new OnRefreshListener() {
            public void onRefresh() {
                RecyclerViewStateUtils.setFooterViewState(((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview, State.Normal);
                AtUserActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                AtUserActivity.this.mCurrentPage = 1;
                AtUserActivity.this.getData(true);
            }
        });
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setOnLoadMoreListener(new OnLoadMoreListener() {
            public void onLoadMore() {
                if (RecyclerViewStateUtils.getFooterViewState(((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview) != State.Loading) {
                    if (AtUserActivity.this.mCurrentPage <= AtUserActivity.this.mTotalPage) {
                        RecyclerViewStateUtils.setFooterViewState(AtUserActivity.this, ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview, 20, State.Loading, null);
                        AtUserActivity.this.getData(false);
                        return;
                    }
                    RecyclerViewStateUtils.setFooterViewState(AtUserActivity.this, ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview, 20, State.TheEnd, null);
                }
            }
        });
        ((AtUserLayoutBinding) this.mBaseBinding).recyclerview.setRefreshing(true);
        this.mLRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                EventBus.getDefault().post(new AtUserNameEvent((String) AtUserActivity.this.mList.get(position).name.get()));
                AtUserActivity.this.finish();
            }
        });
        initSearchBar();
    }

    private void initSearchBar() {
        ((AtUserLayoutBinding) this.mBaseBinding).tvSearch.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                AtUserActivity.this.mRootBinding.titleLine.tvTitleLeft.setVisibility(8);
                AtUserActivity.this.mRootBinding.titleLine.rlSearchBar.setVisibility(0);
                AtUserActivity.this.mRootBinding.titleLine.etSearchKey.setFocusableInTouchMode(true);
                AtUserActivity.this.mRootBinding.titleLine.etSearchKey.setFocusable(true);
                AtUserActivity.this.mRootBinding.titleLine.etSearchKey.setHint("搜索");
                AtUserActivity.this.mRootBinding.titleLine.etSearchKey.requestFocus();
                FengUtil.showSoftInput(AtUserActivity.this);
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).llSearch.setVisibility(8);
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview.setVisibility(8);
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).rvSearchUser.setVisibility(0);
            }
        });
        this.mRootBinding.titleLine.setKey(this.mSearchKey);
        this.mRootBinding.titleLine.etSearchKey.setImeOptions(6);
        this.mRootBinding.titleLine.etSearchKey.addTextChangedListener(this.watcher);
        this.mRootBinding.titleLine.etSearchKey.setOnEditorActionListener(new EditorActionListener(this, null));
        this.mRootBinding.titleLine.tvCancel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).llSearch.setVisibility(0);
                AtUserActivity.this.mRootBinding.titleLine.tvTitleLeft.setVisibility(0);
                AtUserActivity.this.mRootBinding.titleLine.rlSearchBar.setVisibility(8);
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview.setVisibility(0);
                ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).rvSearchUser.setVisibility(8);
            }
        });
    }

    private void loadSearchUser() {
        String strKey = ((String) this.mSearchKey.searchKey.get()).trim();
        this.mSearchList.clear();
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
            FengApplication.getInstance().httpRequest("search/ywf/follow/", map, new OkHttpResponseCallback() {
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
                        JSONObject jsonObject = new JSONObject(content);
                        if (jsonObject.getInt("code") == 1) {
                            JSONObject searchJson = jsonObject.getJSONObject("body").getJSONObject("follow").getJSONObject("user");
                            BaseListModel<UserInfo> baseListModel = new BaseListModel();
                            baseListModel.parser(UserInfo.class, searchJson);
                            AtUserActivity.this.mSearchList.clear();
                            AtUserActivity.this.mSearchList.addAll(baseListModel.list);
                            AtUserActivity.this.handerLocal();
                            return;
                        }
                        AtUserActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        FengApplication.getInstance().upLoadTryCatchLog("search/ywf/follow/", content, e);
                        AtUserActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }
                }
            });
        } else if (this.mLSearchRecyclerViewAdapter != null) {
            this.mLSearchRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    private void handerLocal() {
        if (this.mLSearchRecyclerViewAdapter == null) {
            this.mSearchAdapter = new AtUserAdater(this, this.mSearchList.getUserInfoList());
            this.mLSearchRecyclerViewAdapter = new LRecyclerViewAdapter(this.mSearchAdapter);
            ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser.setAdapter(this.mLSearchRecyclerViewAdapter);
            ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser.setLayoutManager(new LinearLayoutManager(this));
            ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser.setRefreshProgressStyle(2);
            ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser.setPullRefreshEnabled(false);
            ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser.setNoMore(true);
            this.mHeadBinding = AtSearchUserHeadLayoutBinding.inflate(this.mInflater, ((AtUserLayoutBinding) this.mBaseBinding).rvSearchUser, false);
            this.mLSearchRecyclerViewAdapter.addHeaderView(this.mHeadBinding.getRoot());
            this.mHeadBinding.tvAtName.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (!TextUtils.isEmpty(((String) AtUserActivity.this.mSearchKey.searchKey.get()).trim())) {
                        EventBus.getDefault().post(new AtUserNameEvent(((String) AtUserActivity.this.mSearchKey.searchKey.get()).trim()));
                        AtUserActivity.this.finish();
                    }
                }
            });
            this.mHeadBinding.setKey(this.mSearchKey);
            this.mLSearchRecyclerViewAdapter.setOnItemClickListener(new OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    EventBus.getDefault().post(new AtUserNameEvent((String) AtUserActivity.this.mSearchList.get(position).name.get()));
                    AtUserActivity.this.finish();
                }
            });
        }
        this.mLSearchRecyclerViewAdapter.notifyDataSetChanged();
    }

    private void getData(final boolean needClear) {
        Map<String, Object> map = new HashMap();
        map.put("page", String.valueOf(this.mCurrentPage));
        if (FengApplication.getInstance().isLoginUser()) {
            map.put("userid", String.valueOf(FengApplication.getInstance().getUserInfo().id));
        }
        FengApplication.getInstance().httpRequest("user/follow/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                AtUserActivity.this.hideProgress();
                if (AtUserActivity.this.mList.size() > 0) {
                    AtUserActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AtUserActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            AtUserActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                RecyclerViewStateUtils.setFooterViewState(((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview, State.Normal);
                if (needClear) {
                    ((AtUserLayoutBinding) AtUserActivity.this.mBaseBinding).recyclerview.refreshComplete();
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (AtUserActivity.this.mList.size() > 0) {
                    AtUserActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    AtUserActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            AtUserActivity.this.getData(true);
                        }
                    });
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonUsers = jsonResult.getJSONObject("body").getJSONObject("user");
                        BaseListModel<UserInfo> mPageInfo = new BaseListModel();
                        mPageInfo.parser(UserInfo.class, jsonUsers);
                        AtUserActivity.this.mTotalPage = mPageInfo.pagecount;
                        List<UserInfo> list = mPageInfo.list;
                        AtUserActivity.this.hideEmptyView();
                        if (list.size() > 0) {
                            if (needClear) {
                                AtUserActivity.this.mList.clear();
                            }
                            AtUserActivity.this.mList.addAll(list);
                            AtUserActivity.this.mCurrentPage = AtUserActivity.this.mCurrentPage + 1;
                        }
                        AtUserActivity.this.mLRecyclerViewAdapter.notifyDataSetChanged();
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/fins/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    AtUserActivity.this.showSecondTypeToast((int) R.string.retry_tips);
                    FengApplication.getInstance().upLoadTryCatchLog("user/fins/", content, e);
                }
            }
        });
    }
}
