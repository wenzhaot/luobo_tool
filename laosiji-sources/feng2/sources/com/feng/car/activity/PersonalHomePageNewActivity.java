package com.feng.car.activity;

import android.content.Intent;
import android.databinding.ObservableInt;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.RelativeLayout.LayoutParams;
import com.baidu.mapapi.UIMsg.m_AppUI;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityPersonalHomeNewBinding;
import com.feng.car.databinding.TabVehicleContentItemLayoutBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.MessageCountInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.CircleFragmentRefreshFinishEvent;
import com.feng.car.event.MessageCountRefreshEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.event.SnsInfoRefreshEvent;
import com.feng.car.event.UserFirstFollowEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.fragment.BaseFragment;
import com.feng.car.fragment.PersonalContentFragment;
import com.feng.car.fragment.PersonalContentProgramFragment;
import com.feng.car.listener.DoubleTouchListener;
import com.feng.car.listener.DoubleTouchListener$DoubleClickCallBack;
import com.feng.car.utils.BlackUtil;
import com.feng.car.utils.BlackUtil$BlackExcuteCallBack;
import com.feng.car.utils.BlackUtil$BlackListRequestCallBack;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.ImageUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MessageCountManager;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.video.player.JCVideoPlayerManager;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.StickyNavLayout$OnActionMoveListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

public class PersonalHomePageNewActivity extends BaseActivity<ActivityPersonalHomeNewBinding> implements StickyNavLayout$OnActionMoveListener {
    public static final int TYPE_JUMP_LIKE = 2001;
    private final int MAX_DISTANCE = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
    private final int MIN_REFRESH_DISTANCE = 150;
    private int m160;
    private int m494;
    private FragmentPagerAdapter mAdapter;
    private ViewPager mContentViewPager;
    private int mCurrentHeaderheight = 0;
    private List<BaseFragment> mFragments = new ArrayList();
    private int mHeaderHeight = 0;
    private int mIndex = 0;
    private boolean mIsFirstLog = true;
    private boolean mIsLogin = false;
    private boolean mIsMine = false;
    private String mLastImageUrl;
    private LayoutParams mNormalParams;
    private TabLayout mTabLayout;
    private int mTitleBarAlpha = 0;
    private String[] mTitles = new String[]{"帖子", "喜欢", "节目"};
    private int mType = 2001;
    private int mUserId;
    private UserInfo mUserInfo = new UserInfo();
    private String mUserName;
    private boolean requestSuccess = false;

    protected void onDestroy() {
        super.onDestroy();
        if (((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar != null) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(255);
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_personal_home_new;
    }

    public void getLocalIntentData() {
        this.mType = getIntent().getIntExtra("feng_type", 0);
    }

    public void initView() {
        showLaoSiJiDialog();
        hideDefaultTitleBar();
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.setOnTouchListener(new DoubleTouchListener(new DoubleTouchListener$DoubleClickCallBack() {
            public void callBack() {
                ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).navLayoutContainer.scrollTo(0, 0);
                ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).userName.setVisibility(8);
            }
        }));
        this.m494 = this.mResources.getDimensionPixelSize(R.dimen.default_494PX);
        this.m160 = this.mResources.getDimensionPixelSize(R.dimen.default_160PX);
        this.mUserId = getIntent().getIntExtra("userid", 0);
        this.mUserName = getIntent().getStringExtra("name");
        this.mIsLogin = FengApplication.getInstance().isLoginUser();
        if (this.mUserId == 0 && StringUtil.isEmpty(this.mUserName)) {
            initNormalTitleBar("");
            showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    PersonalHomePageNewActivity.this.loadUserInfoData();
                }
            });
        }
        this.mNormalParams = new LayoutParams(-1, this.m494);
        this.mHeaderHeight = this.mNormalParams.height;
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).rlPersonalPictureContainer.setLayoutParams(this.mNormalParams);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).navLayoutContainer.setActionMoveListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).backImage.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).ivSearch.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).llFollowParent.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).llFansParent.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).image.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationLine.setOnClickListener(this);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).letterLine.setOnClickListener(this);
        loadUserInfoData();
    }

    private View getTabView(int i) {
        TabVehicleContentItemLayoutBinding itemLayoutBinding = TabVehicleContentItemLayoutBinding.inflate(this.mInflater, this.mTabLayout, false);
        itemLayoutBinding.tvTitle.setText(this.mTitles[i]);
        return itemLayoutBinding.getRoot();
    }

    public void onLocationChange() {
        int[] xy = new int[2];
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).rlPersonalPictureContainer.getLocationOnScreen(xy);
        changeTitleColor(xy[1], this.m160);
    }

    public void onSingleClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case 2131624155:
                intent = new Intent(this, ShowBigImageActivity.class);
                List<ImageInfo> list = new ArrayList();
                ImageInfo image = new ImageInfo();
                image.url = this.mUserInfo.getHeadImageInfo().url;
                image.height = 640;
                image.width = 640;
                image.mimetype = this.mUserInfo.getHeadImageInfo().mimetype;
                list.add(image);
                intent.putExtra("mImageList", JsonUtil.toJson(list));
                intent.putExtra("position", 0);
                intent.putExtra("show_type", 1007);
                intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((ActivityPersonalHomeNewBinding) this.mBaseBinding).image, 0, image.hash)));
                intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
                startActivity(intent);
                overridePendingTransition(0, 0);
                return;
            case R.id.username /*2131624407*/:
            case R.id.signature /*2131624408*/:
                Intent intentTo = new Intent(this, WebActivity.class);
                intentTo.putExtra("url", HttpConstant.USER_CERTIFICATION_INSTRUCTIONS);
                intentTo.putExtra("title", "老司机认证");
                startActivity(intentTo);
                return;
            case R.id.backImage /*2131624418*/:
                finish();
                return;
            case R.id.option_image /*2131624420*/:
                if (!this.mIsMine) {
                    showOptionDialog();
                    return;
                } else if (FengApplication.getInstance().isLoginUser()) {
                    startActivity(new Intent(this, EditUserInfoActivity.class));
                    return;
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
            case R.id.relationLine /*2131624423*/:
                if (this.mUserInfo.isblack.get() == 1) {
                    BlackUtil.getInstance().addOrRemoveBlack(this, this.mUserInfo, new BlackUtil$BlackExcuteCallBack() {
                        public void onAddBlackSuccess() {
                            PersonalHomePageNewActivity.this.updateRelationUi();
                            PersonalHomePageNewActivity.this.showFirstTypeToast((int) R.string.add_black);
                        }

                        public void onRemoveBlackSuccess() {
                            PersonalHomePageNewActivity.this.updateRelationUi();
                            PersonalHomePageNewActivity.this.showFirstTypeToast((int) R.string.remove_black);
                        }
                    });
                    return;
                } else {
                    this.mUserInfo.followOperation(this, null);
                    return;
                }
            case R.id.letterLine /*2131624426*/:
                if (this.mIsLogin) {
                    intent = new Intent(this, PrivateChatActivity.class);
                    intent.putExtra("userid", this.mUserId);
                    intent.putExtra("name", (String) this.mUserInfo.name.get());
                } else {
                    intent = new Intent(this, LoginActivity.class);
                }
                startActivity(intent);
                return;
            case R.id.ll_follow_parent /*2131624519*/:
                intent = new Intent(this, FansFollowActivity.class);
                intent.putExtra("userid", this.mUserId);
                startActivity(intent);
                return;
            case R.id.ll_fans_parent /*2131624520*/:
                intent = new Intent(this, FansActivity.class);
                intent.putExtra("userid", this.mUserId);
                startActivity(intent);
                return;
            case R.id.iv_search /*2131624522*/:
                intent = new Intent(this, PersonalSearchActivity.class);
                intent.putExtra("userid", this.mUserId);
                intent.putExtra("name", (String) this.mUserInfo.name.get());
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, 0);
                return;
            default:
                return;
        }
    }

    private void downHeadImg() {
        ImageRequestBuilder imageRequestBuilder = ImageRequestBuilder.newBuilderWithSource(Uri.parse(FengUtil.getHeadImageUrl(this.mUserInfo.getHeadImageInfo())));
        imageRequestBuilder.setPostprocessor(new BasePostprocessor() {
            public void process(Bitmap bitmap) {
                ImageUtil.doBlur(bitmap, 8, true);
            }
        });
        ImageRequest imageRequest = imageRequestBuilder.build();
        PipelineDraweeControllerBuilder draweeControllerBuilder = Fresco.newDraweeControllerBuilder();
        draweeControllerBuilder.setOldController(((ActivityPersonalHomeNewBinding) this.mBaseBinding).headerBg.getController());
        draweeControllerBuilder.setImageRequest(imageRequest);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).headerBg.setController(draweeControllerBuilder.build());
    }

    private void loadUserInfoData() {
        Map<String, Object> map = new HashMap();
        if (this.mUserId != 0) {
            map.put("userid", String.valueOf(this.mUserId));
        } else {
            map.put("nickname", this.mUserName);
        }
        FengApplication.getInstance().httpRequest("user/ywf/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                PersonalHomePageNewActivity.this.hideProgress();
                ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).optionImage.setVisibility(0);
                ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).progressBar.setVisibility(8);
                if (PersonalHomePageNewActivity.this.requestSuccess) {
                    PersonalHomePageNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    return;
                }
                PersonalHomePageNewActivity.this.initNormalTitleBar("");
                PersonalHomePageNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        PersonalHomePageNewActivity.this.loadUserInfoData();
                    }
                });
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (PersonalHomePageNewActivity.this.requestSuccess) {
                    PersonalHomePageNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    PersonalHomePageNewActivity.this.initNormalTitleBar("");
                    PersonalHomePageNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            PersonalHomePageNewActivity.this.loadUserInfoData();
                        }
                    });
                }
                PersonalHomePageNewActivity.this.hideProgress();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject bodyJson = jsonObject.getJSONObject("body");
                        JSONObject userJson = bodyJson.getJSONObject("user");
                        PersonalHomePageNewActivity.this.hideProgress();
                        PersonalHomePageNewActivity.this.hideDefaultTitleBar();
                        PersonalHomePageNewActivity.this.hideEmptyView();
                        PersonalHomePageNewActivity.this.mUserInfo.parser(userJson);
                        PersonalHomePageNewActivity.this.showAuthenticatedTip();
                        int directhotshow = 0;
                        if (bodyJson.has("directhotshow") && !bodyJson.isNull("directhotshow")) {
                            directhotshow = bodyJson.getInt("directhotshow");
                        }
                        ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).setUserInfo(PersonalHomePageNewActivity.this.mUserInfo);
                        PersonalHomePageNewActivity.this.mUserId = PersonalHomePageNewActivity.this.mUserInfo.id;
                        PersonalHomePageNewActivity.this.mUserName = (String) PersonalHomePageNewActivity.this.mUserInfo.name.get();
                        PersonalHomePageNewActivity.this.mIsMine = PersonalHomePageNewActivity.this.mUserInfo.getIsMy() == 1;
                        PersonalHomePageNewActivity.this.initFragment(directhotshow > 0);
                        if (PersonalHomePageNewActivity.this.mIsMine) {
                            ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_alter_selector);
                        } else {
                            ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_wh_selector);
                            ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).bottomline.setVisibility(0);
                        }
                        ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).image.setHeadUrl(FengUtil.getHeadImageUrl(PersonalHomePageNewActivity.this.mUserInfo.getHeadImageInfo()));
                        PersonalHomePageNewActivity.this.mUserInfo.getHeadImageInfo().lowUrl = FengUtil.getHeadImageUrl(PersonalHomePageNewActivity.this.mUserInfo.getHeadImageInfo());
                        if (StringUtil.isEmpty(PersonalHomePageNewActivity.this.mLastImageUrl) || !PersonalHomePageNewActivity.this.mLastImageUrl.equals(PersonalHomePageNewActivity.this.mUserInfo.getHeadImageInfo().url)) {
                            PersonalHomePageNewActivity.this.mLastImageUrl = PersonalHomePageNewActivity.this.mUserInfo.getHeadImageInfo().url;
                            PersonalHomePageNewActivity.this.downHeadImg();
                        }
                        if (!PersonalHomePageNewActivity.this.mIsMine) {
                            if (PersonalHomePageNewActivity.this.mIsLogin) {
                                PersonalHomePageNewActivity.this.blackExecute();
                            } else {
                                ((ActivityPersonalHomeNewBinding) PersonalHomePageNewActivity.this.mBaseBinding).bottomline.setVisibility(0);
                                PersonalHomePageNewActivity.this.updateRelationUi();
                            }
                        }
                        PersonalHomePageNewActivity.this.requestSuccess = true;
                    } else if (code == -4) {
                        PersonalHomePageNewActivity.this.initNormalTitleBar("");
                        PersonalHomePageNewActivity.this.showEmptyView((int) R.string.user_notexist, (int) R.drawable.blank_user);
                        PersonalHomePageNewActivity.this.initNormalTitleBar((int) R.string.none);
                        PersonalHomePageNewActivity.this.hideProgress();
                    } else {
                        PersonalHomePageNewActivity.this.hideProgress();
                        FengApplication.getInstance().checkCode("user/ywf/info/", code);
                        if (!PersonalHomePageNewActivity.this.requestSuccess) {
                            PersonalHomePageNewActivity.this.initNormalTitleBar((int) R.string.none);
                            PersonalHomePageNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                                public void onSingleClick(View v) {
                                    PersonalHomePageNewActivity.this.loadUserInfoData();
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    if (!PersonalHomePageNewActivity.this.requestSuccess) {
                        PersonalHomePageNewActivity.this.initNormalTitleBar((int) R.string.none);
                        PersonalHomePageNewActivity.this.showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
                            public void onSingleClick(View v) {
                                PersonalHomePageNewActivity.this.loadUserInfoData();
                            }
                        });
                    }
                    PersonalHomePageNewActivity.this.hideProgress();
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/info/", content, e);
                }
            }
        });
    }

    private void refreshFragment() {
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).progressBar.setVisibility(0);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setVisibility(4);
        if (this.mIndex < 2) {
            ((PersonalContentFragment) this.mFragments.get(this.mIndex)).loadUserArticleData(true);
        } else {
            ((PersonalContentProgramFragment) this.mFragments.get(this.mIndex)).getData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CircleFragmentRefreshFinishEvent event) {
        if (event != null) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).progressBar.setVisibility(8);
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setVisibility(0);
        }
    }

    private void initFragment(boolean hasShow) {
        if (this.mFragments.size() <= 0) {
            this.mFragments.add(PersonalContentFragment.newInstance(this.mUserId, this.mIsMine, PersonalContentFragment.POST_TYPE));
            this.mFragments.add(PersonalContentFragment.newInstance(this.mUserId, this.mIsMine, PersonalContentFragment.LIKE_TYPE));
            if (hasShow) {
                this.mFragments.add(PersonalContentProgramFragment.newInstance(this.mUserId));
            }
            this.mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                public int getCount() {
                    return PersonalHomePageNewActivity.this.mFragments.size();
                }

                public Fragment getItem(int position) {
                    return (Fragment) PersonalHomePageNewActivity.this.mFragments.get(position);
                }
            };
            this.mContentViewPager = ((ActivityPersonalHomeNewBinding) this.mBaseBinding).idStickynavlayoutViewpager;
            this.mContentViewPager.setAdapter(this.mAdapter);
            this.mTabLayout = (TabLayout) findViewById(R.id.id_stickynavlayout_indicator);
            if (this.mType == 2001) {
                this.mIndex = 1;
                this.mContentViewPager.setCurrentItem(this.mIndex);
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).navLayoutContainer.scrollBy(0, getY(((ActivityPersonalHomeNewBinding) this.mBaseBinding).idStickynavlayoutIndicator));
                onLocationChange();
            } else {
                this.mContentViewPager.setCurrentItem(0);
            }
            uploadPv();
            this.mTabLayout.setupWithViewPager(this.mContentViewPager);
            for (int i = 0; i < this.mTabLayout.getTabCount(); i++) {
                this.mTabLayout.getTabAt(i).setCustomView(getTabView(i));
            }
            this.mContentViewPager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    ((BaseFragment) PersonalHomePageNewActivity.this.mFragments.get(PersonalHomePageNewActivity.this.mIndex)).getLogGatherInfo().addPageOutTime();
                    PersonalHomePageNewActivity.this.mIndex = position;
                    JCVideoPlayer.releaseAllVideos();
                    EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
                    PersonalHomePageNewActivity.this.uploadPv();
                }

                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    private void uploadPv() {
        if (this.mFragments.size() > this.mIndex) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    protected void onResume() {
        if (this.mIsFirstLog) {
            this.mIsFirstLog = false;
        } else {
            uploadPv();
        }
        super.onResume();
        MessageCountManager.getInstance().requestMessageCount();
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mIndex) {
            ((BaseFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
    }

    private void showAuthenticatedTip() {
        if (this.mUserInfo.getIsFirstAuthenticated()) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).tvUsername.setOnClickListener(this);
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).tvSignature.setOnClickListener(this);
            return;
        }
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).tvSignature.setOnClickListener(null);
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).tvSignature.setOnClickListener(null);
    }

    private void blackExecute() {
        if (BlackUtil.getInstance().isGetBlackListSuccess()) {
            if (BlackUtil.getInstance().isInBlackList(this.mUserInfo)) {
                this.mUserInfo.isblack.set(1);
            } else {
                this.mUserInfo.isblack.set(0);
            }
            updateRelationUi();
            return;
        }
        BlackUtil.getInstance().getBlackListData(this, new BlackUtil$BlackListRequestCallBack() {
            public void onBlackListSuccess() {
                if (BlackUtil.getInstance().isInBlackList(PersonalHomePageNewActivity.this.mUserInfo)) {
                    PersonalHomePageNewActivity.this.mUserInfo.isblack.set(1);
                } else {
                    PersonalHomePageNewActivity.this.mUserInfo.isblack.set(0);
                }
                PersonalHomePageNewActivity.this.updateRelationUi();
            }

            public void onBlackListFailure() {
                PersonalHomePageNewActivity.this.updateRelationUi();
            }
        });
    }

    private void updateRelationUi() {
        if (this.mUserInfo.isblack.get() == 1) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setText(R.string.remove_blacklist);
            try {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setTextColor(this.mResources.getColorStateList(R.color.selector_black54_pressed_black87));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setImageResource(R.drawable.icon_removeblacklist_selector);
            return;
        }
        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setVisibility(0);
        if (this.mUserInfo.isfollow.get() == 0) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setText(R.string.notfollow);
            try {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setTextColor(this.mResources.getColorStateList(R.color.selector_ffb90a_pressed_d39907));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (this.mUserInfo.isfans == 1) {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setImageResource(R.drawable.icon_followed_me_selector);
            } else {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setImageResource(R.drawable.icon_notfollow_selector);
            }
        } else if (this.mUserInfo.isfollow.get() == 1) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setText(R.string.followed);
            try {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setTextColor(this.mResources.getColorStateList(R.color.selector_black54_pressed_black87));
            } catch (Exception e22) {
                e22.printStackTrace();
            }
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setImageResource(R.drawable.icon_followed_selector);
        } else if (this.mUserInfo.isfollow.get() == 2) {
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setText(R.string.eachfollow);
            try {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationText.setTextColor(this.mResources.getColorStateList(R.color.selector_black54_pressed_black87));
            } catch (Exception e222) {
                e222.printStackTrace();
            }
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).relationIcon.setImageResource(R.drawable.icon_eachfollow_selector);
        }
    }

    private void changeTitleColor(int currentY, int maxY) {
        if (currentY != 0) {
            if (currentY > 0) {
                this.mTitleBarAlpha = 0;
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(0);
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).userName.setVisibility(8);
            } else {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.setBackgroundResource(R.drawable.bg_bottom_border);
                int value = Math.abs(currentY);
                if (value >= maxY) {
                    this.mTitleBarAlpha = 255;
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(this.mTitleBarAlpha);
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).userName.setVisibility(0);
                } else {
                    this.mTitleBarAlpha = (int) (((((double) Math.abs(value)) * 1.0d) / ((double) maxY)) * 255.0d);
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).titlebar.getBackground().mutate().setAlpha(this.mTitleBarAlpha);
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).userName.setVisibility(8);
                }
            }
            if (this.mTitleBarAlpha > 130) {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_selector);
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).ivSearch.setImageResource(R.drawable.icon_search_black);
                if (this.mIsMine) {
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_alter_bl_selector);
                    return;
                } else {
                    ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_bl_selector);
                    return;
                }
            }
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_wh_selector);
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).ivSearch.setImageResource(R.drawable.icon_search_wh);
            if (this.mIsMine) {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_alter_selector);
            } else {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_wh_selector);
            }
        }
    }

    public void loginSuccess() {
        super.loginSuccess();
        loadUserInfoData();
        this.mIsLogin = true;
    }

    public void loginOut() {
        super.loginOut();
        this.mIsLogin = false;
    }

    public void onActionMove(float mLastY, float mCurrentY) {
        if (mCurrentY > mLastY) {
            int changeHeight;
            if (((int) Math.abs(mCurrentY - mLastY)) >= GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION) {
                changeHeight = GenericDraweeHierarchyBuilder.DEFAULT_FADE_DURATION;
            } else {
                changeHeight = (int) Math.abs(mCurrentY - mLastY);
            }
            this.mCurrentHeaderheight = this.mHeaderHeight + changeHeight;
            this.mNormalParams.width = -1;
            this.mNormalParams.height = this.mCurrentHeaderheight;
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).rlPersonalPictureContainer.setLayoutParams(this.mNormalParams);
            if (changeHeight >= 150) {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).progressBar.setVisibility(0);
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setVisibility(4);
            }
        }
    }

    public void onActionHomeMove(float mLastY, float mCurrentY) {
    }

    public void onActionCancel() {
        if (this.mCurrentHeaderheight > this.mHeaderHeight) {
            if (this.mCurrentHeaderheight - this.mHeaderHeight > 150) {
                if (JCVideoPlayerManager.getCurrentJcvd() != null && JCVideoPlayerManager.getCurrentJcvd().mCurrentState == 2) {
                    JCVideoPlayer.releaseAllVideos();
                }
                loadUserInfoData();
                refreshFragment();
            } else {
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).optionImage.setVisibility(0);
                ((ActivityPersonalHomeNewBinding) this.mBaseBinding).progressBar.setVisibility(8);
            }
            this.mNormalParams.width = -1;
            this.mNormalParams.height = this.m494;
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).rlPersonalPictureContainer.setLayoutParams(this.mNormalParams);
            this.mCurrentHeaderheight = this.mHeaderHeight;
        }
    }

    private void showOptionDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("举报", false, 0));
        if (this.mUserInfo.isadministrator == 0) {
            list.add(new DialogItemEntity(this.mUserInfo.isblack.get() == 0 ? "加入黑名单" : "移出黑名单", false, 1));
        }
        list.add(new DialogItemEntity("返回首页", false, 2));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (dialogItemEntity.itemTag == 0) {
                    PersonalHomePageNewActivity.this.mUserInfo.reportOperation(PersonalHomePageNewActivity.this);
                } else if (dialogItemEntity.itemTag == 1) {
                    PersonalHomePageNewActivity.this.mUserInfo.blackOperation(PersonalHomePageNewActivity.this, new BlackUtil$BlackExcuteCallBack() {
                        public void onAddBlackSuccess() {
                            PersonalHomePageNewActivity.this.updateRelationUi();
                            PersonalHomePageNewActivity.this.showFirstTypeToast((int) R.string.add_black);
                        }

                        public void onRemoveBlackSuccess() {
                            PersonalHomePageNewActivity.this.updateRelationUi();
                            PersonalHomePageNewActivity.this.showFirstTypeToast((int) R.string.remove_black);
                        }
                    });
                } else if (dialogItemEntity.itemTag == 2) {
                    PersonalHomePageNewActivity.this.startActivity(new Intent(PersonalHomePageNewActivity.this, HomeActivity.class));
                    PersonalHomePageNewActivity.this.finish();
                }
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserInfoRefreshEvent event) {
        int i = 0;
        if (event.userInfo == null) {
            return;
        }
        ObservableInt observableInt;
        if (this.mIsMine) {
            if (event.type == 1) {
                if (this.mUserInfo.id == event.userInfo.id) {
                    if (!(StringUtil.isEmpty((String) event.userInfo.name.get()) || ((String) this.mUserInfo.name.get()).equals(event.userInfo.name.get()))) {
                        this.mUserInfo.name.set(event.userInfo.name.get());
                    }
                    if (!(StringUtil.isEmpty((String) event.userInfo.signature.get()) || ((String) this.mUserInfo.signature.get()).equals(event.userInfo.signature.get()))) {
                        this.mUserInfo.signature.set(event.userInfo.getFirstAuthenticatedInfo());
                    }
                    if (!StringUtil.isEmpty(event.userInfo.image.url) && !this.mUserInfo.getHeadImageInfo().url.equals(event.userInfo.getHeadImageInfo().url)) {
                        this.mUserInfo.getHeadImageInfo().width = event.userInfo.getHeadImageInfo().width;
                        this.mUserInfo.getHeadImageInfo().height = event.userInfo.getHeadImageInfo().height;
                        this.mUserInfo.getHeadImageInfo().url = event.userInfo.getHeadImageInfo().url;
                        this.mUserInfo.getHeadImageInfo().hash = event.userInfo.getHeadImageInfo().hash;
                        this.mUserInfo.getHeadImageInfo().mimetype = event.userInfo.getHeadImageInfo().mimetype;
                        ((ActivityPersonalHomeNewBinding) this.mBaseBinding).image.setImageURI(Uri.parse(FengUtil.getHeadImageUrl(this.mUserInfo.getHeadImageInfo())));
                        this.mLastImageUrl = this.mUserInfo.getHeadImageInfo().url;
                        downHeadImg();
                    }
                }
            } else if (event.type == 3) {
                this.mUserInfo.sex.set(event.userInfo.sex.get());
            } else if (event.userInfo.isfollow.get() == 0) {
                observableInt = this.mUserInfo.followcount;
                if (this.mUserInfo.followcount.get() - 1 > 0) {
                    i = this.mUserInfo.followcount.get() - 1;
                }
                observableInt.set(i);
            } else {
                this.mUserInfo.followcount.set(this.mUserInfo.followcount.get() + 1);
            }
        } else if (this.mUserInfo.id == event.userInfo.id && event.type == 2) {
            this.mUserInfo.isfollow.set(event.userInfo.isfollow.get());
            this.mUserInfo.fanscount.set(event.userInfo.fanscount.get());
            observableInt = this.mUserInfo.followcount;
            if (event.userInfo.followcount.get() > 0) {
                i = event.userInfo.followcount.get();
            }
            observableInt.set(i);
            updateRelationUi();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendDelArticleSuccess event) {
        if (event != null && this.mIsMine) {
            if (event.type == 1) {
                this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() + 1);
            } else if (event.type == 2) {
                this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() - 1);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SnsInfoRefreshEvent event) {
        if (event != null && event.refreshType == m_AppUI.MSG_APP_VERSION_FORCE && this.mIsMine && !event.deleterefresh) {
            this.mUserInfo.snscount.set(this.mUserInfo.snscount.get() - 1);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(UserFirstFollowEvent event) {
        if (event != null && FengUtil.isMine(this.mUserInfo.id) && !FengApplication.getInstance().isLoginUser()) {
            loadUserInfoData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MessageCountRefreshEvent event) {
        if (this.mIsMine) {
            MessageCountManager manager = MessageCountManager.getInstance();
            MessageCountInfo messageCountInfo = new MessageCountInfo();
            messageCountInfo.fansCount = manager.getFansCount();
            ((ActivityPersonalHomeNewBinding) this.mBaseBinding).setMessageCountInfo(messageCountInfo);
        }
    }

    private int getY(View view) {
        int[] rect = new int[2];
        view.getLocationOnScreen(rect);
        return rect[1];
    }

    public String getLogCurrentPage() {
        if (this.mIndex == 0) {
            return "app_profile_article?userid=" + this.mUserId;
        }
        if (this.mIndex == 1) {
            return "app_profile_like?userid=" + this.mUserId;
        }
        if (this.mIndex == 2) {
            return "app_profile_program?userid=" + this.mUserId;
        }
        return "-";
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
