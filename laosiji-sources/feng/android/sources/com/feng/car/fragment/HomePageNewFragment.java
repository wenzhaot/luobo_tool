package com.feng.car.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout.OnTabSelectedListener;
import android.support.design.widget.TabLayout.Tab;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.MessageActivity;
import com.feng.car.activity.SearchNewActivity;
import com.feng.car.databinding.HomePageNewFragmentBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.entity.home.HomeLabelInfo;
import com.feng.car.event.HomeLabelRefreshEvent;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
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

public class HomePageNewFragment extends BaseFragment<HomePageNewFragmentBinding> {
    private FragmentPagerAdapter mAdapter;
    private List<BaseFragment> mFragments = new ArrayList();
    private int mIndex = 0;
    private List<HomeLabelInfo> mLabelInfos = new ArrayList();
    private boolean mLoadFirst = true;

    protected int setLayoutId() {
        return 2130903258;
    }

    protected void initView() {
        initTitleBar();
        getHeadData();
    }

    private void initTitleBar() {
        ((HomePageNewFragmentBinding) this.mBind).tvSearch.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                HomePageNewFragment.this.startActivity(new Intent(HomePageNewFragment.this.mActivity, SearchNewActivity.class));
                HomePageNewFragment.this.mActivity.overridePendingTransition(2130968592, 0);
            }
        });
        ((HomePageNewFragmentBinding) this.mBind).ivMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                HomePageNewFragment.this.startActivity(new Intent(HomePageNewFragment.this.mActivity, MessageActivity.class));
            }
        });
        ((HomePageNewFragmentBinding) this.mBind).rlTab.setVisibility(4);
        ((HomePageNewFragmentBinding) this.mBind).tvUpdataApp.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                try {
                    FengUtil.checkUpdate(HomePageNewFragment.this.mActivity, new JSONObject(SharedUtil.getString(HomePageNewFragment.this.mActivity, HttpConstant.UPDATEVERSIONJSON)), false, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void showUpdataHint() {
        if (SharedUtil.getInt(this.mActivity, HttpConstant.UPDATE_CODE, 0) < 70 || FengUtil.getAPPVerionCode(FengApplication.getInstance()) >= 70) {
            ((HomePageNewFragmentBinding) this.mBind).tvUpdataApp.setVisibility(8);
        } else {
            ((HomePageNewFragmentBinding) this.mBind).tvUpdataApp.setVisibility(0);
        }
    }

    private void initTab() {
        int i;
        this.mFragments.add(new ChoicenessFragment());
        for (i = 1; i < this.mLabelInfos.size(); i++) {
            this.mFragments.add(HomeOhterLavelFragment.newInstance(((HomeLabelInfo) this.mLabelInfos.get(i)).id, ((HomeLabelInfo) this.mLabelInfos.get(i)).categoryid));
        }
        this.mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            public int getCount() {
                return HomePageNewFragment.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) HomePageNewFragment.this.mFragments.get(position);
            }
        };
        ((HomePageNewFragmentBinding) this.mBind).viewPage.setAdapter(this.mAdapter);
        ((HomePageNewFragmentBinding) this.mBind).viewPage.setCurrentItem(0);
        ((HomePageNewFragmentBinding) this.mBind).tabLayout.setupWithViewPager(((HomePageNewFragmentBinding) this.mBind).viewPage);
        for (i = 0; i < ((HomePageNewFragmentBinding) this.mBind).tabLayout.getTabCount(); i++) {
            ((HomePageNewFragmentBinding) this.mBind).tabLayout.getTabAt(i).setCustomView(getAllCarTabView(i));
        }
        handlerResume();
        ((HomePageNewFragmentBinding) this.mBind).tabLayout.addOnTabSelectedListener(new OnTabSelectedListener() {
            public void onTabSelected(Tab tab) {
                HomePageNewFragment.this.updateTabTextView(tab, true);
            }

            public void onTabUnselected(Tab tab) {
                HomePageNewFragment.this.updateTabTextView(tab, false);
            }

            public void onTabReselected(Tab tab) {
            }
        });
        ((HomePageNewFragmentBinding) this.mBind).viewPage.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                ((BaseFragment) HomePageNewFragment.this.mFragments.get(HomePageNewFragment.this.mIndex)).getLogGatherInfo().addPageOutTime();
                HomePageNewFragment.this.mIndex = position;
                JCVideoPlayer.releaseAllVideos();
                HomePageNewFragment.this.handlerResume();
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public void handlerResume() {
        if (this.mBind != null && this.mLabelInfos.size() != 0 && this.mFragments.size() != 0) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    public void pageOut() {
        if (this.mBind != null && this.mLabelInfos.size() > this.mIndex) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageOutTime();
        }
    }

    private View getAllCarTabView(int position) {
        TabCarFriendsCircleItemLayoutBinding binding = TabCarFriendsCircleItemLayoutBinding.inflate(this.mInflater, ((HomePageNewFragmentBinding) this.mBind).tabLayout, false);
        binding.tvTitle.setTextAppearance(getActivity(), 2131362231);
        HomeLabelInfo info = (HomeLabelInfo) this.mLabelInfos.get(position);
        if (position == 0) {
            binding.tvTitle.setTypeface(Typeface.defaultFromStyle(1));
        }
        if (info.ishighlight == 1) {
            binding.tvTitle.setTextColor(this.mActivity.getResources().getColorStateList(2131558695));
        } else {
            binding.tvTitle.setTextColor(this.mActivity.getResources().getColorStateList(2131558697));
        }
        binding.tvTitle.setText(info.name);
        return binding.getRoot();
    }

    private void updateTabTextView(Tab tab, boolean isSelect) {
        if (tab != null && tab.getCustomView() != null) {
            if (isSelect) {
                ((TextView) tab.getCustomView().findViewById(2131624296)).setTypeface(Typeface.defaultFromStyle(1));
            } else {
                ((TextView) tab.getCustomView().findViewById(2131624296)).setTypeface(Typeface.defaultFromStyle(0));
            }
        }
    }

    private void getHeadData() {
        Map<String, Object> map = new HashMap();
        map.put("seriesids", new ArrayList());
        FengApplication.getInstance().httpRequest(HttpConstant.HOT_SERIES_LIST, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                HomePageNewFragment.this.showEmptyView();
            }

            public void onStart() {
                ((BaseActivity) HomePageNewFragment.this.mActivity).showLaoSiJiDialog();
            }

            public void onFinish() {
                if (HomePageNewFragment.this.mLoadFirst) {
                    HomePageNewFragment.this.mLoadFirst = false;
                }
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) HomePageNewFragment.this.mActivity).hideProgress();
                HomePageNewFragment.this.showEmptyView();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        if (jsonBody.has("hometab") && !jsonBody.isNull("hometab")) {
                            ((HomePageNewFragmentBinding) HomePageNewFragment.this.mBind).rlTab.setVisibility(0);
                            HomePageNewFragment.this.parserLabelsInfo(jsonBody.getJSONArray("hometab"));
                            ((BaseActivity) HomePageNewFragment.this.mActivity).hideProgress();
                        }
                    } else if (HomePageNewFragment.this.mLoadFirst) {
                        HomePageNewFragment.this.mLoadFirst = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parserLabelsInfo(JSONArray jsonArray) throws JSONException {
        if (this.mLabelInfos.size() <= 0 || this.mFragments.size() <= 0) {
            this.mLabelInfos.clear();
            int size = jsonArray.length();
            for (int i = 0; i < size; i++) {
                HomeLabelInfo labelInfo = new HomeLabelInfo();
                labelInfo.parser(jsonArray.getJSONObject(i));
                this.mLabelInfos.add(labelInfo);
            }
            initTab();
        }
    }

    private void showEmptyView() {
        if (((HomePageNewFragmentBinding) this.mBind).emptyView != null) {
            ((HomePageNewFragmentBinding) this.mBind).emptyView.setVisibility(0);
            ((HomePageNewFragmentBinding) this.mBind).emptyView.setEmptyImage(2130838112);
            ((HomePageNewFragmentBinding) this.mBind).emptyView.setEmptyText(2131231188);
            ((HomePageNewFragmentBinding) this.mBind).emptyView.setButtonListener(2131231470, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ((HomePageNewFragmentBinding) HomePageNewFragment.this.mBind).emptyView.setVisibility(8);
                }
            });
        }
    }

    private String getLogCurrentPage() {
        if (this.mIndex == 0) {
            return "app_selected_0?index=" + this.mIndex;
        }
        if (this.mBind == null || this.mLabelInfos.size() <= this.mIndex) {
            return "-";
        }
        return "app_selected_" + ((HomeLabelInfo) this.mLabelInfos.get(this.mIndex)).id + "?index=" + this.mIndex;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
            ((HomePageNewFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(8);
            return;
        }
        ((HomePageNewFragmentBinding) this.mBind).tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
        ((HomePageNewFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeRefreshEvent event) {
        if (event.mIndex == 0 && isAdded() && this.mFragments.size() >= this.mIndex && this.mFragments.get(this.mIndex) != null) {
            EventBus.getDefault().post(new HomeLabelRefreshEvent(this.mIndex == 0 ? 0 : ((HomeLabelInfo) this.mLabelInfos.get(this.mIndex)).id));
        }
    }
}
