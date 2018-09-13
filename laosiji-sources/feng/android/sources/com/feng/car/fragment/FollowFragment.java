package com.feng.car.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.FengApplication;
import com.feng.car.activity.LoginActivity;
import com.feng.car.activity.MessageActivity;
import com.feng.car.databinding.FollowPageFragmentBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.VideoInFragmentChangeEvent;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener.DoubleClickCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LogConstans;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.video.player.JCVideoPlayer;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class FollowFragment extends BaseFragment<FollowPageFragmentBinding> {
    private FragmentPagerAdapter mAdapter;
    private TabCarFriendsCircleItemLayoutBinding mCircleBind;
    private CircleFragment mCircleFragment;
    private TabCarFriendsCircleItemLayoutBinding mFollowBind;
    private FollowedContentFragment mFollowedContentFragment;
    private List<BaseFragment> mFragments = new ArrayList();
    private int mIndex = 0;
    private TabCarFriendsCircleItemLayoutBinding mProgrameBind;
    private MyProgrameFragment mProgrameFragment;
    private List<TabCarFriendsCircleItemLayoutBinding> mTabList = new ArrayList();
    private String[] titles = new String[]{"用户", "话题", "节目"};

    protected int setLayoutId() {
        return 2130903243;
    }

    protected void initView() {
        initFragments();
        initTitleTab();
    }

    private void initFragments() {
        this.mFollowedContentFragment = new FollowedContentFragment();
        this.mCircleFragment = new CircleFragment();
        this.mProgrameFragment = new MyProgrameFragment();
        ((FollowPageFragmentBinding) this.mBind).titleBar.setOnTouchListener(new DoubleTouchListener(new DoubleClickCallBack() {
            public void callBack() {
                int pos = ((FollowPageFragmentBinding) FollowFragment.this.mBind).viewPage.getCurrentItem();
                if (pos == 0) {
                    if (FollowFragment.this.mFollowedContentFragment != null) {
                        FollowFragment.this.mFollowedContentFragment.backToTop();
                    }
                } else if (pos == 1) {
                    if (FollowFragment.this.mCircleFragment != null) {
                        FollowFragment.this.mCircleFragment.backToTop();
                    }
                } else if (pos == 2 && FollowFragment.this.mProgrameFragment != null) {
                    FollowFragment.this.mProgrameFragment.backToTop();
                }
            }
        }));
        this.mFragments.add(this.mFollowedContentFragment);
        this.mFragments.add(this.mCircleFragment);
        this.mFragments.add(this.mProgrameFragment);
        this.mAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            public int getCount() {
                return FollowFragment.this.mFragments.size();
            }

            public Fragment getItem(int position) {
                return (Fragment) FollowFragment.this.mFragments.get(position);
            }
        };
        ((FollowPageFragmentBinding) this.mBind).viewPage.setAdapter(this.mAdapter);
        ((FollowPageFragmentBinding) this.mBind).viewPage.setCurrentItem(0);
        ((FollowPageFragmentBinding) this.mBind).viewPage.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                ((BaseFragment) FollowFragment.this.mFragments.get(FollowFragment.this.mIndex)).getLogGatherInfo().addPageOutTime();
                FollowFragment.this.mIndex = position;
                FollowFragment.this.changeTabColor(position);
                JCVideoPlayer.releaseAllVideos();
                FollowFragment.this.uploadSlidePv();
                if (FollowFragment.this.isAdded() && FollowFragment.this.mBind != null) {
                    EventBus.getDefault().post(new VideoInFragmentChangeEvent(VideoInFragmentChangeEvent.FOLLOW_PAGE_SLIDE, FollowFragment.this.mIndex));
                }
            }

            public void onPageScrollStateChanged(int state) {
            }
        });
        ((FollowPageFragmentBinding) this.mBind).ivMessage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (FengApplication.getInstance().isLoginUser()) {
                    FollowFragment.this.startActivity(new Intent(FollowFragment.this.mActivity, MessageActivity.class));
                    return;
                }
                FollowFragment.this.mActivity.startActivity(new Intent(FollowFragment.this.mActivity, LoginActivity.class));
            }
        });
        uploadSlidePv();
    }

    public void uploadSlidePv() {
        if (this.mFragments.size() > this.mIndex) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    public void pageOut() {
        if (this.mFragments.size() > this.mIndex) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageOutTime();
        }
    }

    private String getLogCurrentPage() {
        if (this.mIndex == 0) {
            return LogConstans.APP_FOLLOW;
        }
        if (this.mIndex == 1) {
            return LogConstans.APP_FOLLOW_CIRCLE;
        }
        if (this.mIndex == 2) {
            return LogConstans.APP_FOLLOW_PROGRAM;
        }
        return LogConstans.APP_FOLLOW;
    }

    private void initTitleTab() {
        for (int i = 0; i < this.mFragments.size(); i++) {
            getAllCarTabView(i);
        }
        LayoutParams p = new LayoutParams(-2, -1);
        p.addRule(13);
        ((FollowPageFragmentBinding) this.mBind).tabLayout.addView(this.mCircleBind.getRoot(), p);
        int m30 = getResources().getDimensionPixelSize(2131296815);
        LayoutParams p1 = new LayoutParams(-2, -1);
        p1.addRule(0, this.mCircleBind.getRoot().getId());
        p1.setMargins(0, 0, m30, 0);
        ((FollowPageFragmentBinding) this.mBind).tabLayout.addView(this.mFollowBind.getRoot(), p1);
        LayoutParams p2 = new LayoutParams(-2, -1);
        p2.addRule(1, this.mCircleBind.getRoot().getId());
        p2.setMargins(m30, 0, 0, 0);
        ((FollowPageFragmentBinding) this.mBind).tabLayout.addView(this.mProgrameBind.getRoot(), p2);
    }

    private void changeTabColor(int position) {
        for (int i = 0; i < this.mTabList.size(); i++) {
            TabCarFriendsCircleItemLayoutBinding binding = (TabCarFriendsCircleItemLayoutBinding) this.mTabList.get(i);
            if (((Integer) binding.getRoot().getTag()).intValue() == position) {
                binding.tvTitle.setSelected(true);
                binding.tvTitle.setTypeface(Typeface.defaultFromStyle(1));
            } else {
                binding.tvTitle.setSelected(false);
                binding.tvTitle.setTypeface(Typeface.defaultFromStyle(0));
            }
        }
    }

    private View getAllCarTabView(final int position) {
        TabCarFriendsCircleItemLayoutBinding binding = TabCarFriendsCircleItemLayoutBinding.inflate(this.mInflater);
        binding.getRoot().setTag(Integer.valueOf(position));
        binding.tvTitle.setTextAppearance(getActivity(), 2131362231);
        binding.tvTitle.setTextColor(getResources().getColorStateList(2131558697));
        binding.tvTitle.setText(this.titles[position]);
        binding.tvTitle.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((FollowPageFragmentBinding) FollowFragment.this.mBind).viewPage.setCurrentItem(position);
            }
        });
        if (position == 0) {
            this.mFollowBind = binding;
            this.mFollowBind.getRoot().setId(2131623960);
            this.mFollowBind.tvTitle.setTypeface(Typeface.defaultFromStyle(1));
            this.mFollowBind.tvTitle.setSelected(true);
        } else if (position == 1) {
            this.mCircleBind = binding;
            this.mCircleBind.getRoot().setId(2131623952);
        } else if (position == 2) {
            this.mProgrameBind = binding;
            this.mProgrameBind.getRoot().setId(2131623993);
        }
        this.mTabList.add(binding);
        return binding.getRoot();
    }

    public void onResume() {
        super.onResume();
        changeDot();
    }

    private void changeDot() {
        if (MessageCountManager.getInstance().getSnsfollow() > 0) {
            this.mFollowBind.ivDot.setVisibility(0);
        } else {
            this.mFollowBind.ivDot.setVisibility(4);
        }
        if (MessageCountManager.getInstance().getCommunity() > 0) {
            this.mCircleBind.ivDot.setVisibility(0);
        } else {
            this.mCircleBind.ivDot.setVisibility(4);
        }
        if (MessageCountManager.getInstance().getNewHotShow() > 0) {
            this.mProgrameBind.ivDot.setVisibility(0);
        } else {
            this.mProgrameBind.ivDot.setVisibility(4);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (MessageCountManager.getInstance().getMessagePageTotalCount() <= 0 || !FengApplication.getInstance().isLoginUser()) {
            ((FollowPageFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(8);
        } else {
            ((FollowPageFragmentBinding) this.mBind).tvMessageCommentNum.setText(FengUtil.numberFormat99(MessageCountManager.getInstance().getMessagePageTotalCount()));
            ((FollowPageFragmentBinding) this.mBind).tvMessageCommentNum.setVisibility(0);
        }
        changeDot();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HomeRefreshEvent event) {
        if (event.mIndex != 3 || !isAdded()) {
            return;
        }
        if (this.mIndex == 0 && this.mFollowedContentFragment.isAdded()) {
            this.mFollowedContentFragment.refreshData();
        } else if (this.mIndex == 1 && this.mCircleFragment.isAdded()) {
            this.mCircleFragment.refreshData();
        } else if (this.mIndex == 2 && this.mProgrameFragment.isAdded()) {
            this.mProgrameFragment.refreshData();
        }
    }
}
