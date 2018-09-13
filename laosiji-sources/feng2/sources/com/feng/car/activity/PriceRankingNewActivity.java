package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CarsTagAdapter;
import com.feng.car.databinding.ActivityPriceNewRankingBinding;
import com.feng.car.databinding.PriceFromLayoutBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.event.SearchCityEvent;
import com.feng.car.fragment.TransactionPriceFragment;
import com.feng.car.listener.AppBarStateChangeListener;
import com.feng.car.listener.AppBarStateChangeListener.State;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.MapUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PriceRankingNewActivity extends BaseActivity<ActivityPriceNewRankingBinding> {
    private FragmentPagerAdapter mAdapter;
    private List<String> mAllTab = new ArrayList();
    private CarsTagAdapter mCarsTagAdapter;
    private int mCurrentIndex = 0;
    private List<TransactionPriceFragment> mFragments = new ArrayList();
    private Dialog mFromDialog;
    private boolean mIsFirst = true;
    private boolean mIsSlidingToFirst = false;
    private PriceFromLayoutBinding mPriceFromLayoutBinding;
    private List<SearchItem> mSeeCarsReconds = new ArrayList();

    public int setBaseContentView() {
        return R.layout.activity_price_new_ranking;
    }

    public void initView() {
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tvCityName.setText(MapUtil.newInstance().getCurrentCityName());
        uploadPv();
        hideDefaultTitleBar();
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).rlTitleBar.setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                if (PriceRankingNewActivity.this.mFragments.size() > 0) {
                    ((TransactionPriceFragment) PriceRankingNewActivity.this.mFragments.get(PriceRankingNewActivity.this.mCurrentIndex)).backTop();
                }
            }
        }));
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).backImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PriceRankingNewActivity.this.finish();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(0);
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.setLayoutManager(manager);
        this.mCarsTagAdapter = new CarsTagAdapter(this, this.mSeeCarsReconds);
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.setAdapter(this.mCarsTagAdapter);
        this.mCarsTagAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                SearchItem info = (SearchItem) PriceRankingNewActivity.this.mSeeCarsReconds.get(position);
                Intent intent = new Intent(PriceRankingNewActivity.this, PriceRankingCarsNewActivity.class);
                intent.putExtra("carsid", info.contentid);
                PriceRankingNewActivity.this.startActivity(intent);
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        PriceRankingNewActivity.this.mSwipeBackLayout.setCanScroll(false);
                        break;
                    case 1:
                        PriceRankingNewActivity.this.mSwipeBackLayout.setCanScroll(true);
                        break;
                }
                return false;
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (newState == 0) {
                    if (manager.findFirstCompletelyVisibleItemPosition() == 0 && PriceRankingNewActivity.this.mIsSlidingToFirst) {
                        PriceRankingNewActivity.this.mSwipeBackLayout.setCanScroll(true);
                    }
                } else if (newState == 1) {
                    PriceRankingNewActivity.this.mSwipeBackLayout.setCanScroll(false);
                }
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    PriceRankingNewActivity.this.mIsSlidingToFirst = false;
                } else {
                    PriceRankingNewActivity.this.mIsSlidingToFirst = true;
                }
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tvSearch.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent = new Intent(PriceRankingNewActivity.this, SearchNewActivity.class);
                intent.putExtra("type", 4);
                PriceRankingNewActivity.this.startActivity(intent);
                PriceRankingNewActivity.this.overridePendingTransition(R.anim.fade_in, 0);
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tvCityName.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PriceRankingNewActivity.this.startActivity(new Intent(PriceRankingNewActivity.this, CityListActivity.class));
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).ivPriceFrom.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                PriceRankingNewActivity.this.showPriceFromDialog();
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).ivAddCarPrice.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (!FengApplication.getInstance().isLoginUser()) {
                    PriceRankingNewActivity.this.startActivity(new Intent(PriceRankingNewActivity.this, LoginActivity.class));
                } else if (StringUtil.isEmpty(FengApplication.getInstance().getUserInfo().phonenumber)) {
                    Intent intent = new Intent(PriceRankingNewActivity.this, SettingAccountPhoneActivity.class);
                    intent.putExtra("feng_type", 0);
                    PriceRankingNewActivity.this.startActivity(intent);
                } else {
                    PriceRankingNewActivity.this.startActivity(new Intent(PriceRankingNewActivity.this, InvoiceUploadActivity.class));
                }
            }
        });
        initFragment();
        getHeaderData();
    }

    private void initFragment() {
        this.mAllTab.add("热门车系");
        this.mAllTab.add("成交量大");
        this.mAllTab.add("降价最多");
        this.mFragments.add(TransactionPriceFragment.newInstance(2));
        this.mFragments.add(TransactionPriceFragment.newInstance(1));
        this.mFragments.add(TransactionPriceFragment.newInstance(0));
        this.mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            public int getCount() {
                return PriceRankingNewActivity.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) PriceRankingNewActivity.this.mFragments.get(position);
            }
        };
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).viewPage.setAdapter(this.mAdapter);
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).tabLayout.setupWithViewPager(((ActivityPriceNewRankingBinding) this.mBaseBinding).viewPage);
        for (int i = 0; i < ((ActivityPriceNewRankingBinding) this.mBaseBinding).tabLayout.getTabCount(); i++) {
            ((ActivityPriceNewRankingBinding) this.mBaseBinding).tabLayout.getTabAt(i).setCustomView(getTabView(i));
        }
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).viewPage.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                ((TransactionPriceFragment) PriceRankingNewActivity.this.mFragments.get(PriceRankingNewActivity.this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
                PriceRankingNewActivity.this.mCurrentIndex = position;
                PriceRankingNewActivity.this.uploadPv();
                if (((TransactionPriceFragment) PriceRankingNewActivity.this.mFragments.get(PriceRankingNewActivity.this.mCurrentIndex)).isAdded()) {
                    ((TransactionPriceFragment) PriceRankingNewActivity.this.mFragments.get(PriceRankingNewActivity.this.mCurrentIndex)).checkCityRefresh();
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        ((ActivityPriceNewRankingBinding) this.mBaseBinding).appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state != State.EXPANDED && state == State.COLLAPSED) {
                }
            }

            public void onVerticalOffset(int verticalOffset, int barHeight) {
                if (barHeight != 0) {
                    ((ActivityPriceNewRankingBinding) PriceRankingNewActivity.this.mBaseBinding).rlTitleBar.getBackground().mutate().setAlpha((int) (((float) (Math.abs(verticalOffset) * 255)) / (((float) barHeight) * 1.0f)));
                    ((ActivityPriceNewRankingBinding) PriceRankingNewActivity.this.mBaseBinding).tvTitle.setAlpha(((float) Math.abs(verticalOffset)) / (((float) barHeight) * 1.0f));
                }
            }
        });
    }

    private void uploadPv() {
        if (this.mFragments.size() > this.mCurrentIndex) {
            ((TransactionPriceFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((TransactionPriceFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    public void onResume() {
        if (this.mIsFirst) {
            this.mIsFirst = false;
        } else {
            uploadPv();
        }
        super.onResume();
        changeSeeCarsData();
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mCurrentIndex) {
            ((TransactionPriceFragment) this.mFragments.get(this.mCurrentIndex)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    private View getTabView(int position) {
        TabCarFriendsCircleItemLayoutBinding binding = TabCarFriendsCircleItemLayoutBinding.inflate(this.mInflater, ((ActivityPriceNewRankingBinding) this.mBaseBinding).tabLayout, false);
        binding.tvTitle.setText((CharSequence) this.mAllTab.get(position));
        return binding.getRoot();
    }

    private void getHeaderData() {
        FengApplication.getInstance().httpRequest("carownerprice/statistics/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                PriceRankingNewActivity.this.initNormalTitleBar("");
                PriceRankingNewActivity.this.showEmptyView(R.string.load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        PriceRankingNewActivity.this.getHeaderData();
                    }
                });
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                PriceRankingNewActivity.this.initNormalTitleBar("");
                PriceRankingNewActivity.this.showEmptyView(R.string.load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        PriceRankingNewActivity.this.getHeaderData();
                    }
                });
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        PriceRankingNewActivity.this.hideDefaultTitleBar();
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        int ownerCount = jsonBody.getInt("ownersnumberofprice");
                        int yesterdayCount = jsonBody.getInt("yesterdayupdatepricenumber");
                        int totalCount = jsonBody.getInt("containsnumberofspec");
                        ((ActivityPriceNewRankingBinding) PriceRankingNewActivity.this.mBaseBinding).collectPriceCount.setText(String.valueOf(ownerCount));
                        ((ActivityPriceNewRankingBinding) PriceRankingNewActivity.this.mBaseBinding).updateCount.setText(String.valueOf(yesterdayCount));
                        ((ActivityPriceNewRankingBinding) PriceRankingNewActivity.this.mBaseBinding).coverCarxCount.setText("已覆盖" + totalCount + "车型");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    PriceRankingNewActivity.this.initNormalTitleBar("");
                    PriceRankingNewActivity.this.showEmptyView(R.string.load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            PriceRankingNewActivity.this.getHeaderData();
                        }
                    });
                }
            }
        });
    }

    private void showPriceFromDialog() {
        if (this.mPriceFromLayoutBinding == null) {
            this.mPriceFromLayoutBinding = PriceFromLayoutBinding.inflate(LayoutInflater.from(this));
            this.mPriceFromLayoutBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (PriceRankingNewActivity.this.mFromDialog != null) {
                        PriceRankingNewActivity.this.mFromDialog.dismiss();
                    }
                }
            });
        }
        if (this.mFromDialog == null) {
            this.mFromDialog = new Dialog(this, R.style.price_from_dialog);
            this.mFromDialog.setCanceledOnTouchOutside(true);
            this.mFromDialog.setCancelable(true);
            Window window = this.mFromDialog.getWindow();
            window.setGravity(17);
            window.setWindowAnimations(R.style.jc_popup_toast_anim);
            window.setContentView(this.mPriceFromLayoutBinding.getRoot());
            window.setLayout(-2, -2);
        }
        this.mFromDialog.show();
    }

    private void changeSeeCarsData() {
        if (this.mCarsTagAdapter != null) {
            List<SearchItem> list = FengApplication.getInstance().getSparkDB().getSearchReconds(SearchItem.SEARCH_OWNER_PRICE_TYPE);
            if (list.size() == 0) {
                ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.setVisibility(8);
            } else {
                ((ActivityPriceNewRankingBinding) this.mBaseBinding).tagRecyclerView.setVisibility(0);
                this.mSeeCarsReconds.clear();
                this.mSeeCarsReconds.addAll(list);
            }
            this.mCarsTagAdapter.notifyDataSetChanged();
        }
    }

    public String getLogCurrentPage() {
        if (this.mCurrentIndex == 0) {
            return "app_car_onwer_price_2?index=" + this.mCurrentIndex;
        }
        if (this.mCurrentIndex == 1) {
            return "app_car_onwer_price_1?index=" + this.mCurrentIndex;
        }
        return "app_car_onwer_price_0?index=" + this.mCurrentIndex;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SearchCityEvent searchCityEvent) {
        if (searchCityEvent != null && !TextUtils.isEmpty(searchCityEvent.cityInfo.name)) {
            ((ActivityPriceNewRankingBinding) this.mBaseBinding).tvCityName.setText(searchCityEvent.cityInfo.name);
            if (this.mCityId != searchCityEvent.cityInfo.id) {
                this.mCityId = searchCityEvent.cityInfo.id;
                if (this.mFragments.size() > 0) {
                    ((TransactionPriceFragment) this.mFragments.get(this.mCurrentIndex)).checkCityRefresh();
                }
            }
        }
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
