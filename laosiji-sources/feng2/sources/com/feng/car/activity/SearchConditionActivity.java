package com.feng.car.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.SearchCarsConditionAdapter;
import com.feng.car.databinding.ActivitySearchConditionLayoutBinding;
import com.feng.car.entity.search.SearchKey;
import com.feng.car.entity.searchcar.SearchCarGroup;
import com.feng.car.event.CloseMoreConditionEvent;
import com.feng.car.event.SearchCarEvent;
import com.feng.car.event.SearchConditionEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.SearchCarManager;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
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

public class SearchConditionActivity extends BaseActivity<ActivitySearchConditionLayoutBinding> {
    private SearchCarsConditionAdapter mAdapter;
    private boolean mCancelRequest = false;
    private List<SearchCarGroup> mList = new ArrayList();
    private SearchKey mSearchKey = new SearchKey();

    private class EditChangedListener implements TextWatcher {
        private EditChangedListener() {
        }

        /* synthetic */ EditChangedListener(SearchConditionActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.toString().trim().length() <= 0) {
                SearchConditionActivity.this.mList.clear();
                SearchConditionActivity.this.mAdapter.notifyDataSetChanged();
                return;
            }
            SearchConditionActivity.this.getKeyData();
        }
    }

    private class EditorActionListener implements OnEditorActionListener {
        private EditorActionListener() {
        }

        /* synthetic */ EditorActionListener(SearchConditionActivity x0, AnonymousClass1 x1) {
            this();
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            if (((String) SearchConditionActivity.this.mSearchKey.searchKey.get()).trim().length() > 0) {
                FengUtil.closeSoftKeyboard(v);
                SearchConditionActivity.this.mRootBinding.titleLine.etSearchKey.clearFocus();
                SearchConditionActivity.this.getKeyData();
            }
            return true;
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_search_condition_layout;
    }

    public void initView() {
        initSearchTitleBar(this.mSearchKey, R.string.search_condition_text, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SearchConditionActivity.this.finish();
            }
        }, new EditorActionListener(this, null), new EditChangedListener(this, null));
        this.mRootBinding.titleLine.etSearchKey.setFilters(new InputFilter[]{new LengthFilter(100)});
        this.mAdapter = new SearchCarsConditionAdapter(this, this.mList);
        ((ActivitySearchConditionLayoutBinding) this.mBaseBinding).recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ((ActivitySearchConditionLayoutBinding) this.mBaseBinding).recyclerview.setAdapter(this.mAdapter);
        ((ActivitySearchConditionLayoutBinding) this.mBaseBinding).recyclerview.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == 1) {
                    FengUtil.closeSoftKeyboard(SearchConditionActivity.this.mRootBinding.titleLine.etSearchKey);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        ((ActivitySearchConditionLayoutBinding) this.mBaseBinding).resultLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SearchCarManager.newInstance().isNeedOpenResultActivity()) {
                    SearchConditionActivity.this.startActivity(new Intent(SearchConditionActivity.this, SearchCarResultActivity.class));
                } else {
                    EventBus.getDefault().post(new SearchCarEvent(4));
                }
                EventBus.getDefault().post(new CloseMoreConditionEvent());
                SearchConditionActivity.this.finish();
            }
        });
        getData();
    }

    private void startSearch(List<String> keyList) {
        this.mList.clear();
        this.mList.addAll(SearchCarManager.newInstance().getSearchGroupList((String) this.mSearchKey.searchKey.get(), keyList));
        this.mAdapter.notifyDataSetChanged();
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
        if (((ActivitySearchConditionLayoutBinding) this.mBaseBinding).progress.isShown()) {
            FengApplication.getInstance().cancelRequest("carsearch/seriescount/");
            this.mCancelRequest = true;
        } else {
            this.mCancelRequest = false;
        }
        FengApplication.getInstance().httpRequest("carsearch/seriescount/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
                ((ActivitySearchConditionLayoutBinding) SearchConditionActivity.this.mBaseBinding).progress.setVisibility(0);
            }

            public void onFinish() {
                if (!SearchConditionActivity.this.mCancelRequest) {
                    ((ActivitySearchConditionLayoutBinding) SearchConditionActivity.this.mBaseBinding).progress.setVisibility(8);
                }
                SearchConditionActivity.this.mCancelRequest = false;
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        int count = jsonResult.getJSONObject("body").getJSONObject("cars").getInt("count");
                        if (count == 0) {
                            ((ActivitySearchConditionLayoutBinding) SearchConditionActivity.this.mBaseBinding).resultText.setText(R.string.unfind_cars);
                        } else {
                            ((ActivitySearchConditionLayoutBinding) SearchConditionActivity.this.mBaseBinding).resultText.setText("共" + count + "款车系符合条件");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getKeyData() {
        Map<String, Object> map = new HashMap();
        map.put("keyword", this.mSearchKey.searchKey.get());
        FengApplication.getInstance().cancelRequest("carsearch/case/");
        FengApplication.getInstance().httpRequest("carsearch/case/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                SearchConditionActivity.this.startSearch(new ArrayList());
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                SearchConditionActivity.this.startSearch(new ArrayList());
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray jsonArray = jsonResult.getJSONObject("body").getJSONObject("case").getJSONArray("list");
                        List<String> list = new ArrayList();
                        int size = jsonArray.length();
                        for (int i = 0; i < size; i++) {
                            list.add(jsonArray.getString(i));
                        }
                        SearchConditionActivity.this.startSearch(list);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    SearchConditionActivity.this.startSearch(new ArrayList());
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchConditionEvent event) {
        getData();
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
