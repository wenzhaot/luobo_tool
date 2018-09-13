package com.feng.car.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.PopupWindow;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.ActivityNewSubjectBinding;
import com.feng.car.databinding.ChooseCarDeatailShareDialogBinding;
import com.feng.car.databinding.CircleSortListArraydialogBinding;
import com.feng.car.databinding.TabCarFriendsCircleItemLayoutBinding;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.circle.CircleSubclassInfo;
import com.feng.car.event.AddCircleEvent;
import com.feng.car.event.CircleFragmentRefreshFinishEvent;
import com.feng.car.event.RecyclerviewDirectionEvent;
import com.feng.car.event.SendDelArticleSuccess;
import com.feng.car.fragment.VehicleContentFragment;
import com.feng.car.listener.AppBarStateChangeListener;
import com.feng.car.listener.AppBarStateChangeListener.State;
import com.feng.car.listener.OnDownloadImageListener;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.ImageUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.MapUtil;
import com.feng.car.utils.ShareUtils;
import com.feng.car.video.player.JCVideoPlayer;
import com.feng.car.view.behavior.AppBarLayoutOverScrollViewBehavior;
import com.feng.car.view.behavior.AppBarLayoutOverScrollViewBehavior$onProgressChangeListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.stub.StubApp;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX.Req;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONObject;

public class NewSubjectActivity extends BaseActivity<ActivityNewSubjectBinding> {
    public static String CIRCLES_REQUEST_ID = "circles_request_id";
    private boolean IS_CAR_MODE;
    private int m140;
    private int m220;
    private int m250;
    private int m8;
    private AdvertInfo mAdvertInfo;
    private List<String> mAllCarTitles = new ArrayList();
    private boolean mAlreadyAd = false;
    private int mCarSeriesID = 0;
    private CircleInfo mCircleInfo = new CircleInfo();
    private CircleSortListArraydialogBinding mCircleSortDialogBinding;
    private int mCirclesRequestId = 0;
    private int mCityId;
    private int mContentSortType;
    private List<VehicleContentFragment> mFragments = new ArrayList();
    private int mIndex;
    private boolean mIsActive;
    private boolean mIsFirstLog = true;
    private IWXAPI mIwxapi;
    private CarSeriesInfo mSeriesInfo = new CarSeriesInfo();
    private Dialog mShareDialog;
    private ChooseCarDeatailShareDialogBinding mShareDialogBinding;
    private boolean mShowCircleEmpty = false;
    private PopupWindow mSortArrayWindow;
    private UMShareListener mUMShareListener = new UMShareListener() {
        public void onStart(SHARE_MEDIA share_media) {
        }

        public void onResult(SHARE_MEDIA share_media) {
            NewSubjectActivity.this.showFirstTypeToast((int) R.string.share_success);
        }

        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            NewSubjectActivity.this.showSecondTypeToast((int) R.string.share_failed);
        }

        public void onCancel(SHARE_MEDIA share_media) {
            NewSubjectActivity.this.showSecondTypeToast((int) R.string.share_canceled);
        }
    };

    static {
        StubApp.interface11(2568);
    }

    public int setBaseContentView() {
        return R.layout.activity_new_subject;
    }

    public void initView() {
        hideDefaultTitleBar();
        this.m8 = this.mResources.getDimensionPixelSize(R.dimen.default_8PX);
        this.m140 = this.mResources.getDimensionPixelSize(R.dimen.default_140PX);
        this.m220 = this.mResources.getDimensionPixelSize(R.dimen.default_220PX);
        this.m250 = this.mResources.getDimensionPixelSize(R.dimen.default_250PX);
        showLaoSiJiDialog();
        ((ActivityNewSubjectBinding) this.mBaseBinding).appBar.setVisibility(4);
        ((ActivityNewSubjectBinding) this.mBaseBinding).llParent1.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ibPublish.setVisibility(8);
        initData();
        ((ActivityNewSubjectBinding) this.mBaseBinding).appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    if (NewSubjectActivity.this.mBaseBinding != null && ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).adNotice.isShown() && NewSubjectActivity.this.mAdvertInfo != null && NewSubjectActivity.this.mAdvertInfo.adid > 0) {
                        NewSubjectActivity.this.mAdvertInfo.adPvHandle(NewSubjectActivity.this, true);
                    }
                } else if (state == State.COLLAPSED && NewSubjectActivity.this.mBaseBinding != null && ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).adNotice.isShown() && NewSubjectActivity.this.mAdvertInfo != null && NewSubjectActivity.this.mAdvertInfo.adid > 0) {
                    NewSubjectActivity.this.mAdvertInfo.adPvHandle(NewSubjectActivity.this, false);
                }
            }

            public void onVerticalOffset(int verticalOffset, int barHeight) {
                if (barHeight != 0) {
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).rlTitleBar.getBackground().mutate().setAlpha((int) (((float) (Math.abs(verticalOffset) * 255)) / (((float) barHeight) * 1.0f)));
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).tvTitleSubjectName.setAlpha(((float) Math.abs(verticalOffset)) / (((float) barHeight) * 1.0f));
                    if (Math.abs(verticalOffset) > NewSubjectActivity.this.m220) {
                        ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_selector);
                        ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_bl_selector);
                        if (NewSubjectActivity.this.IS_CAR_MODE) {
                            ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).rightCvPk.setVsImageResource(R.drawable.car_compare_selector);
                            return;
                        }
                        return;
                    }
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).backImage.setImageResource(R.drawable.icon_back_wh_selector);
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).optionImage.setImageResource(R.drawable.icon_more_wh_selector);
                    if (NewSubjectActivity.this.IS_CAR_MODE) {
                        ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).rightCvPk.setVsImageResource(R.drawable.car_compare_white_selector);
                    }
                }
            }
        });
        regToWX();
    }

    private void initData() {
        this.mContentSortType = SharedUtil.getInt(this, "circle_sort_type", -1);
        this.mCityId = MapUtil.newInstance().getCurrentCityId();
        this.mCarSeriesID = getIntent().getIntExtra("carsid", 0);
        this.mCirclesRequestId = getIntent().getIntExtra(CIRCLES_REQUEST_ID, 0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).rightCvPk.setVsImageResource(R.drawable.car_compare_white_selector);
        ((ActivityNewSubjectBinding) this.mBaseBinding).rlTitleBar.getBackground().mutate().setAlpha(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).backImage.setOnClickListener(this);
        ((ActivityNewSubjectBinding) this.mBaseBinding).optionImage.setOnClickListener(this);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ibFollow.setOnClickListener(this);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvSort.setOnClickListener(this);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ibPublish.setOnClickListener(this);
        getCircleDetailInfo();
    }

    private void getCircleDetailInfo() {
        Map<String, Object> map = new HashMap();
        map.put("communityid", String.valueOf(this.mCirclesRequestId));
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        map.put("cityid", String.valueOf(this.mCityId));
        FengApplication.getInstance().httpRequest("theme/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                NewSubjectActivity.this.hideProgress();
                if (NewSubjectActivity.this.mCircleInfo.id > 0) {
                    NewSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    NewSubjectActivity.this.showNetErrorView();
                }
            }

            public void onStart() {
            }

            public void onFinish() {
                NewSubjectActivity.this.hideProgress();
                ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).appBar.setVisibility(0);
                ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).llParent1.setVisibility(0);
                ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).ibPublish.setVisibility(0);
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (NewSubjectActivity.this.mCircleInfo.id > 0) {
                    NewSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                } else {
                    NewSubjectActivity.this.showNetErrorView();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonBody = jsonResult.getJSONObject("body");
                        NewSubjectActivity.this.mCircleInfo.parser(jsonBody.getJSONObject("community"));
                        NewSubjectActivity.this.mCirclesRequestId = NewSubjectActivity.this.mCircleInfo.id;
                        if (jsonBody.has("cars")) {
                            NewSubjectActivity.this.mSeriesInfo.parser(jsonBody.getJSONObject("cars"));
                            ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).setInfo(NewSubjectActivity.this.mSeriesInfo);
                            if (NewSubjectActivity.this.mSeriesInfo.id > 0) {
                                NewSubjectActivity.this.initCarMode();
                            } else {
                                NewSubjectActivity.this.initNormalMode();
                            }
                        } else {
                            NewSubjectActivity.this.initNormalMode();
                        }
                        if (!NewSubjectActivity.this.mAlreadyAd) {
                            NewSubjectActivity.this.mAlreadyAd = true;
                            NewSubjectActivity.this.getAdData();
                        }
                        NewSubjectActivity.this.hideEmptyView();
                        NewSubjectActivity.this.parserHeadData();
                        NewSubjectActivity.this.parserTabInfo();
                        String strIDs = SharedUtil.getString(NewSubjectActivity.this, "see_history_cars");
                        List<Integer> list = null;
                        if (!TextUtils.isEmpty(strIDs)) {
                            list = JsonUtil.fromJson(strIDs, new TypeToken<ArrayList<Integer>>() {
                            });
                        }
                        if (list == null) {
                            list = new ArrayList();
                        }
                        if (list.contains(Integer.valueOf(NewSubjectActivity.this.mCarSeriesID))) {
                            list.remove(Integer.valueOf(NewSubjectActivity.this.mCarSeriesID));
                        }
                        if (list.size() > 10) {
                            list.remove(list.size() - 1);
                        }
                        list.add(0, Integer.valueOf(NewSubjectActivity.this.mCarSeriesID));
                        SharedUtil.putString(NewSubjectActivity.this, "see_history_cars", JsonUtil.toJson(list));
                    } else if (code == -110 || code == -206) {
                        NewSubjectActivity.this.showNotExist();
                    } else {
                        FengApplication.getInstance().checkCode("community/getinfobyid/", code);
                        if (NewSubjectActivity.this.mCircleInfo.id > 0) {
                            NewSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        } else {
                            NewSubjectActivity.this.showNetErrorView();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("community/getinfobyid/", content, e);
                    if (NewSubjectActivity.this.mCircleInfo.id > 0) {
                        NewSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    } else {
                        NewSubjectActivity.this.showNetErrorView();
                    }
                }
            }
        });
    }

    private void initNormalMode() {
        this.IS_CAR_MODE = false;
        ((ActivityNewSubjectBinding) this.mBaseBinding).line1.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).line2.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvPicNum.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).rightCvPk.setVisibility(8);
    }

    private void initBahavior() {
        final AppBarLayoutOverScrollViewBehavior behavior = (AppBarLayoutOverScrollViewBehavior) ((LayoutParams) ((ActivityNewSubjectBinding) this.mBaseBinding).appBar.getLayoutParams()).getBehavior();
        ((ActivityNewSubjectBinding) this.mBaseBinding).appBar.postDelayed(new Runnable() {
            public void run() {
                if (((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).appBar != null) {
                    behavior.setParentHeight(((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).appBar.getHeight());
                }
            }
        }, 1000);
        behavior.setOnProgressChangeListener(new AppBarLayoutOverScrollViewBehavior$onProgressChangeListener() {
            public void onProgressChange(float progress, boolean isRelease) {
                if (progress == 1.0f && isRelease && !((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).progressBar.isShown()) {
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).progressBar.setVisibility(0);
                    ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).optionImage.setVisibility(4);
                } else if (progress == 0.0f && isRelease && ((ActivityNewSubjectBinding) NewSubjectActivity.this.mBaseBinding).progressBar.isShown() && NewSubjectActivity.this.mFragments.size() > NewSubjectActivity.this.mIndex) {
                    ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(NewSubjectActivity.this.mIndex)).loadData(false);
                }
            }
        });
    }

    private void initCarMode() {
        this.IS_CAR_MODE = true;
        ((ActivityNewSubjectBinding) this.mBaseBinding).ibConfig.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ibConfig.setOnClickListener(this);
        if (this.mSeriesInfo.speccount > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llModel.setOnClickListener(this);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectModel.setImageResource(R.drawable.subject_model_gray);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectModelNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPriceRange.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.minprice == 0 && this.mSeriesInfo.maxprice == 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPriceRange.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.pricecount > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llPrice.setOnClickListener(this);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectPrice.setImageResource(R.drawable.subject_price_gray);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPriceNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPreferential.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.preferential == 0.0d) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPreferential.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.autodiscusscount > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llTips.setOnClickListener(this);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectTips.setImageResource(R.drawable.subject_tips_gray);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectTips.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectTipsNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.imagecount.get() > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llPictures.setOnClickListener(this);
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivImagePlace.setOnClickListener(this);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectPic.setImageResource(R.drawable.subject_pic_gray);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPic.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectPicNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.getDealernum() > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llCommand.setOnClickListener(this);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectDealer.setImageResource(R.drawable.subject_dealer_gray);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectDealer.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectDealerNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        }
        if (this.mSeriesInfo.commoditycount > 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).llRelativeGoods.setOnClickListener(this);
            return;
        }
        ((ActivityNewSubjectBinding) this.mBaseBinding).ivSubjectGoods.setImageResource(R.drawable.subject_goods_gray);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectGoodsNum.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectGoods.setTextColor(ContextCompat.getColor(this, R.color.color_38_000000));
    }

    private void parserHeadData() {
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvTitleSubjectName.setText(this.mCircleInfo.getCircleName());
        ((ActivityNewSubjectBinding) this.mBaseBinding).ivImage.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(this.mCircleInfo.image, 640, 0.56f)));
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvSubjectName.setText(this.mCircleInfo.getCircleName());
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvFollowerNum.setText(FengUtil.numberFormat(this.mCircleInfo.fanscount) + "人关注");
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvContentNum.setText(FengUtil.numberFormat(this.mCircleInfo.snscount) + "条内容");
        if (this.mCircleInfo.isfans.get() == 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ibFollow.setImageResource(R.drawable.follow_white_selector);
        } else if (this.mCircleInfo.isfans.get() == 1) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ibFollow.setImageResource(R.drawable.follow_white_cancel_selector);
        }
    }

    private void parserTabInfo() {
        if (this.mCircleInfo.sublist.size() == 0) {
            showCircleEmptyView();
        } else if (this.mCircleInfo.sublist.size() > 0 && this.mFragments.size() > 0) {
        } else {
            if (this.mCircleInfo.sublist == null || this.mCircleInfo.sublist.size() <= 0) {
                showCircleEmptyView();
                return;
            }
            int i;
            hideCircleEmpty();
            for (i = 0; i < this.mCircleInfo.sublist.size(); i++) {
                this.mAllCarTitles.add(((CircleSubclassInfo) this.mCircleInfo.sublist.get(i)).name);
                this.mFragments.add(VehicleContentFragment.newInstance(this.mCircleInfo.id, ((CircleSubclassInfo) this.mCircleInfo.sublist.get(i)).id, ((CircleSubclassInfo) this.mCircleInfo.sublist.get(i)).name, i));
            }
            ((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                public int getCount() {
                    return NewSubjectActivity.this.mFragments.size();
                }

                public Fragment getItem(int position) {
                    return (Fragment) NewSubjectActivity.this.mFragments.get(position);
                }
            });
            ((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject.setCurrentItem(0);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject.setupWithViewPager(((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject);
            for (i = 0; i < ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject.getTabCount(); i++) {
                ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject.getTabAt(i).setCustomView(getAllCarTabView(i));
            }
            ((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(NewSubjectActivity.this.mIndex)).getLogGatherInfo().addPageOutTime();
                    JCVideoPlayer.releaseAllVideos();
                    EventBus.getDefault().post(new RecyclerviewDirectionEvent(false));
                    if (!(NewSubjectActivity.this.mFragments == null || NewSubjectActivity.this.mFragments.get(position) == null || !((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(position)).isAdded())) {
                        ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(position)).checkPageAndSortType();
                    }
                    NewSubjectActivity.this.mIndex = position;
                    NewSubjectActivity.this.uploadPv();
                }

                public void onPageScrollStateChanged(int state) {
                }
            });
            uploadPv();
        }
    }

    private View getAllCarTabView(int position) {
        TabCarFriendsCircleItemLayoutBinding binding = TabCarFriendsCircleItemLayoutBinding.inflate(this.mInflater, ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject, false);
        binding.tvTitle.setText((CharSequence) this.mAllCarTitles.get(position));
        return binding.getRoot();
    }

    private void getAdData() {
        Map<String, Object> map = new HashMap();
        map.put("pageid", String.valueOf(992));
        map.put("datatype", "1");
        map.put("pagecode", this.mCirclesRequestId + "");
        FengApplication.getInstance().httpRequest("advert/adserver/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                NewSubjectActivity.this.initBahavior();
            }

            public void onStart() {
            }

            public void onFinish() {
                NewSubjectActivity.this.initBahavior();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray adData = jsonResult.getJSONObject("body").getJSONArray(UriUtil.DATA_SCHEME);
                        if (NewSubjectActivity.this.mAdvertInfo == null) {
                            NewSubjectActivity.this.mAdvertInfo = new AdvertInfo();
                        }
                        if (adData.length() > 0) {
                            NewSubjectActivity.this.mAdvertInfo.resetData();
                            NewSubjectActivity.this.mAdvertInfo.parser(adData.getJSONObject(0));
                        } else {
                            NewSubjectActivity.this.mAdvertInfo.resetData();
                        }
                        NewSubjectActivity.this.showAdView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showAdView() {
        if (this.mAdvertInfo == null || this.mAdvertInfo.adid == 0) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).adNotice.setVisibility(8);
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvAdLabel.setVisibility(8);
            ((ActivityNewSubjectBinding) this.mBaseBinding).vAdLine.setVisibility(8);
            return;
        }
        ((ActivityNewSubjectBinding) this.mBaseBinding).adNotice.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvAdLabel.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).vAdLine.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).adNotice.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(this.mAdvertInfo.tmpmap.image.size() > 0 ? (ImageInfo) this.mAdvertInfo.tmpmap.image.get(0) : new ImageInfo(), 640, 0.17f)));
        ((ActivityNewSubjectBinding) this.mBaseBinding).tvAdLabel.setText(this.mAdvertInfo.tmpmap.label);
        ((ActivityNewSubjectBinding) this.mBaseBinding).vAdLine.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).adNotice.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                NewSubjectActivity.this.mAdvertInfo.adClickHandle(NewSubjectActivity.this);
            }
        });
        this.mAdvertInfo.adPvHandle(this, true);
    }

    public void onSingleClick(View v) {
        Intent intent;
        super.onSingleClick(v);
        switch (v.getId()) {
            case R.id.cancel /*2131624291*/:
                this.mShareDialog.dismiss();
                return;
            case R.id.backImage /*2131624418*/:
                finish();
                return;
            case R.id.iv_image_place /*2131624440*/:
                if (!this.IS_CAR_MODE) {
                    return;
                }
                break;
            case R.id.ib_follow /*2131624446*/:
                if (this.mCircleInfo.isfans.get() == 0) {
                    MobclickAgent.onEvent(this, "forum_final_joinbtn");
                } else {
                    MobclickAgent.onEvent(this, "forum_final_quitbtn");
                }
                this.mCircleInfo.accedeSingleOperation(this, null);
                return;
            case R.id.ib_config /*2131624447*/:
                intent = new Intent(this, CarConfigureCompareActivity.class);
                intent.putExtra("feng_type", CarConfigureCompareActivity.TYPE_CARS);
                intent.putExtra(CarConfigureCompareActivity.COMRARE_IDS, this.mSeriesInfo.id + "");
                startActivity(intent);
                return;
            case R.id.ll_model /*2131624448*/:
                intent = new Intent(this, CarModelListActivity.class);
                intent.putExtra("name", (String) this.mSeriesInfo.name.get());
                intent.putExtra("carsid", this.mSeriesInfo.id);
                startActivity(intent);
                return;
            case R.id.ll_price /*2131624452*/:
                intent = new Intent(this, PriceRankingCarsNewActivity.class);
                intent.putExtra("carsid", this.mSeriesInfo.id);
                startActivity(intent);
                return;
            case R.id.ll_relative_goods /*2131624456*/:
                intent = new Intent(this, RelativeGoodsActivity.class);
                intent.putExtra("name", (String) this.mSeriesInfo.name.get());
                intent.putExtra("carsid", this.mSeriesInfo.id);
                startActivity(intent);
                return;
            case R.id.ll_pictures /*2131624461*/:
                break;
            case R.id.ll_tips /*2131624465*/:
                intent = new Intent(this, OldDriverChooseCarNextActivity.class);
                intent.putExtra(OldDriverChooseCarNextActivity.CAR_SERIES_NAME, this.mCircleInfo.name);
                intent.putExtra(OldDriverChooseCarNextActivity.RECOMMEND_TYPE, OldDriverChooseCarNextActivity.TIPS_TYPE_RECOMMEND_CAR);
                intent.putExtra("carsid", this.mSeriesInfo.id);
                startActivity(intent);
                return;
            case R.id.ll_command /*2131624469*/:
                if (this.mSeriesInfo.getDealernum() > 0) {
                    intent = new Intent(this, VehicleClassDetailActivity.class);
                    intent.putExtra("id", this.mSeriesInfo.id);
                    intent.putExtra("name", (String) this.mSeriesInfo.name.get());
                    intent.putExtra("type", this.mSeriesInfo.getCarPriceText2());
                    intent.putExtra("price", this.mSeriesInfo.getCarPrice());
                    startActivity(intent);
                    MobclickAgent.onEvent(this, "car_dealer_btn");
                    return;
                }
                return;
            case R.id.optionImage /*2131624478*/:
                showMoreDialog();
                return;
            case R.id.tv_sort /*2131624481*/:
                showSortArrayDialog(v);
                return;
            case R.id.ib_publish /*2131624484*/:
                if (FengApplication.getInstance().isLoginUser()) {
                    FengApplication.getInstance().getUserInfo().intentToSendPost(this, this.mCircleInfo);
                    return;
                } else {
                    startActivity(new Intent(this, LoginActivity.class));
                    return;
                }
            case R.id.friendsShare /*2131624822*/:
                ShareUtils.CircleFinalPageShare(this, SHARE_MEDIA.WEIXIN_CIRCLE, this.mCircleInfo.type, this.mCircleInfo.id, this.mCircleInfo.getCircleName(), this.mCircleInfo.fanscount, this.mCircleInfo.snscount, this.mCircleInfo.image.url, this.mUMShareListener);
                this.mShareDialog.dismiss();
                return;
            case R.id.weixinShare /*2131624823*/:
                initIconThumb(true);
                return;
            case R.id.weiboShare /*2131624824*/:
                ShareUtils.CircleFinalPageShare(this, SHARE_MEDIA.SINA, this.mCircleInfo.type, this.mCircleInfo.id, this.mCircleInfo.getCircleName(), this.mCircleInfo.fanscount, this.mCircleInfo.snscount, this.mCircleInfo.image.url, this.mUMShareListener);
                this.mShareDialog.dismiss();
                return;
            case R.id.qqShare /*2131624825*/:
                ShareUtils.CircleFinalPageShare(this, SHARE_MEDIA.QQ, this.mCircleInfo.type, this.mCircleInfo.id, this.mCircleInfo.getCircleName(), this.mCircleInfo.fanscount, this.mCircleInfo.snscount, this.mCircleInfo.image.url, this.mUMShareListener);
                this.mShareDialog.dismiss();
                return;
            case R.id.qzoneShare /*2131624826*/:
                ShareUtils.CircleFinalPageShare(this, SHARE_MEDIA.QZONE, this.mCircleInfo.type, this.mCircleInfo.id, this.mCircleInfo.getCircleName(), this.mCircleInfo.fanscount, this.mCircleInfo.snscount, this.mCircleInfo.image.url, this.mUMShareListener);
                this.mShareDialog.dismiss();
                return;
            case R.id.copyLink /*2131624828*/:
                FengUtil.copyText(this, ShareUtils.getCirclePageShareUrl(this.mCircleInfo.type, this.mCircleInfo.id), "已复制");
                this.mShareDialog.dismiss();
                return;
            case R.id.backHome /*2131624834*/:
                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return;
            case R.id.tv_add_desktop /*2131624919*/:
                initIconThumb(false);
                this.mShareDialog.dismiss();
                return;
            default:
                return;
        }
        intent = new Intent(this, CarPhotoListActivity.class);
        intent.putExtra("id", this.mSeriesInfo.id);
        intent.putExtra("name", (String) this.mSeriesInfo.name.get());
        intent.putExtra("size", this.mSeriesInfo.imagecount.get());
        startActivity(intent);
    }

    private void createShortCut(Bitmap bitmap) {
        try {
            Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
            shortcut.putExtra("android.intent.extra.shortcut.NAME", this.mCircleInfo.name);
            shortcut.putExtra("duplicate", false);
            Intent shortcutIntent = new Intent("android.intent.action.MAIN");
            shortcutIntent.putExtra("url", HttpConstant.FENGHTML5M + "community/1/" + this.mCirclesRequestId + ".html");
            shortcutIntent.setClass(this, TransparentActivity.class);
            shortcutIntent.setFlags(268435456);
            shortcut.putExtra("android.intent.extra.shortcut.INTENT", shortcutIntent);
            shortcut.putExtra("android.intent.extra.shortcut.ICON", bitmap);
            sendBroadcast(shortcut);
            showFirstTypeToast((int) R.string.add_desktop_hint);
        } catch (Exception e) {
            e.printStackTrace();
            showFirstTypeToast((int) R.string.add_desktop_hint);
        }
    }

    private String buildTransaction(String type) {
        return type == null ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void showMoreDialog() {
        if (this.mShareDialogBinding == null) {
            this.mShareDialogBinding = ChooseCarDeatailShareDialogBinding.inflate(LayoutInflater.from(this));
            this.mShareDialogBinding.friendsShare.setOnClickListener(this);
            this.mShareDialogBinding.weixinShare.setOnClickListener(this);
            this.mShareDialogBinding.weiboShare.setOnClickListener(this);
            this.mShareDialogBinding.qqShare.setOnClickListener(this);
            this.mShareDialogBinding.qzoneShare.setOnClickListener(this);
            this.mShareDialogBinding.copyLink.setOnClickListener(this);
            this.mShareDialogBinding.tvAddDesktop.setOnClickListener(this);
            this.mShareDialogBinding.backHome.setOnClickListener(this);
            this.mShareDialogBinding.cancel.setOnClickListener(this);
        }
        if (this.mShareDialog == null) {
            this.mShareDialog = new Dialog(this, R.style.ArticleShareDialog);
            this.mShareDialog.setCanceledOnTouchOutside(true);
            this.mShareDialog.setCancelable(true);
            Window window = this.mShareDialog.getWindow();
            window.setGravity(80);
            window.setWindowAnimations(R.style.shareAnimation);
            window.setContentView(this.mShareDialogBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mShareDialog.show();
    }

    private void hideCircleEmpty() {
        this.mShowCircleEmpty = false;
        ((ActivityNewSubjectBinding) this.mBaseBinding).rlTab.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject.setVisibility(0);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ivEmpty.setVisibility(8);
    }

    private void showCircleEmptyView() {
        this.mShowCircleEmpty = true;
        ((ActivityNewSubjectBinding) this.mBaseBinding).rlTab.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).tabSubject.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).vpSubject.setVisibility(8);
        ((ActivityNewSubjectBinding) this.mBaseBinding).ivEmpty.setVisibility(0);
    }

    private void showSortArrayDialog(View view) {
        if (this.mCircleSortDialogBinding == null) {
            this.mCircleSortDialogBinding = CircleSortListArraydialogBinding.inflate(LayoutInflater.from(this));
            this.mCircleSortDialogBinding.tvCircleSortReply.setText(R.string.default_sort);
            this.mCircleSortDialogBinding.tvCircleSortPublish.setText(R.string.sort_new);
            this.mCircleSortDialogBinding.tvCircleSortPopular.setText(R.string.sort_hot);
            this.mCircleSortDialogBinding.tvCircleSortReply.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (NewSubjectActivity.this.mContentSortType != 2) {
                        SharedUtil.putInt(NewSubjectActivity.this, "circle_sort_type", 2);
                        NewSubjectActivity.this.mContentSortType = 2;
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_ffffff));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(NewSubjectActivity.this.mIndex)).checkPageAndSortType();
                    }
                    NewSubjectActivity.this.hideSortArrayWindow();
                }
            });
            this.mCircleSortDialogBinding.tvCircleSortPublish.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (NewSubjectActivity.this.mContentSortType != 0) {
                        SharedUtil.putInt(NewSubjectActivity.this, "circle_sort_type", 0);
                        NewSubjectActivity.this.mContentSortType = 0;
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_ffffff));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(NewSubjectActivity.this.mIndex)).checkPageAndSortType();
                    }
                    NewSubjectActivity.this.hideSortArrayWindow();
                }
            });
            this.mCircleSortDialogBinding.tvCircleSortPopular.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (NewSubjectActivity.this.mContentSortType != 1) {
                        SharedUtil.putInt(NewSubjectActivity.this, "circle_sort_type", 1);
                        NewSubjectActivity.this.mContentSortType = 1;
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(17170445);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_000000));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(NewSubjectActivity.this, R.color.color_ffffff));
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortReply.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPublish.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        NewSubjectActivity.this.mCircleSortDialogBinding.tvCircleSortPopular.setPadding(NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8, NewSubjectActivity.this.m8);
                        ((VehicleContentFragment) NewSubjectActivity.this.mFragments.get(NewSubjectActivity.this.mIndex)).checkPageAndSortType();
                    }
                    NewSubjectActivity.this.hideSortArrayWindow();
                }
            });
        }
        if (this.mContentSortType == 2) {
            this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
            this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
            this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
        } else if (this.mContentSortType == 0) {
            this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
            this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
            this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
        } else if (this.mContentSortType == 1) {
            this.mCircleSortDialogBinding.tvCircleSortReply.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortReply.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
            this.mCircleSortDialogBinding.tvCircleSortPublish.setBackgroundResource(17170445);
            this.mCircleSortDialogBinding.tvCircleSortPublish.setTextColor(ContextCompat.getColor(this, R.color.color_000000));
            this.mCircleSortDialogBinding.tvCircleSortPopular.setBackgroundResource(R.drawable.login_bg_round_404040_4dp);
            this.mCircleSortDialogBinding.tvCircleSortPopular.setTextColor(ContextCompat.getColor(this, R.color.color_ffffff));
        }
        this.mCircleSortDialogBinding.tvCircleSortReply.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mCircleSortDialogBinding.tvCircleSortPublish.setPadding(this.m8, this.m8, this.m8, this.m8);
        this.mCircleSortDialogBinding.tvCircleSortPopular.setPadding(this.m8, this.m8, this.m8, this.m8);
        if (this.mSortArrayWindow == null) {
            this.mSortArrayWindow = new PopupWindow(this.mCircleSortDialogBinding.getRoot(), this.m220, this.m250, true);
        }
        this.mSortArrayWindow.setFocusable(true);
        this.mSortArrayWindow.setOutsideTouchable(true);
        this.mSortArrayWindow.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, 17170445)));
        int[] loc_int = new int[2];
        view.getLocationOnScreen(loc_int);
        Rect location = new Rect();
        location.right = loc_int[0] + view.getWidth();
        location.bottom = loc_int[1] + view.getHeight();
        this.mSortArrayWindow.showAtLocation(view, 51, location.right, location.bottom);
    }

    private void hideSortArrayWindow() {
        if (this.mSortArrayWindow != null && this.mSortArrayWindow.isShowing()) {
            this.mSortArrayWindow.dismiss();
        }
    }

    public void showNetErrorView() {
        initNormalTitleBar("");
        showEmptyView(R.string.load_faile, R.drawable.icon_load_faile, R.string.reload, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                NewSubjectActivity.this.getCircleDetailInfo();
            }
        });
    }

    private void showNotExist() {
        initNormalTitleBar("");
        if (this.mRootBinding.emptyView != null && this.mRootBinding.titleLine != null) {
            this.mRootBinding.emptyView.setEmptyImage(R.drawable.icon_car_friends_empty);
            this.mRootBinding.emptyView.hideEmptyButton();
            this.mRootBinding.emptyView.setVisibility(0);
            hideProgress();
        }
    }

    public void hideEmptyView() {
        hideDefaultTitleBar();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(RecyclerviewDirectionEvent event) {
        if (this.mIsActive && event.directionUp) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ibPublish.setVisibility(8);
        } else {
            ((ActivityNewSubjectBinding) this.mBaseBinding).ibPublish.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CircleFragmentRefreshFinishEvent event) {
        if (event != null) {
            ((ActivityNewSubjectBinding) this.mBaseBinding).progressBar.setVisibility(4);
            ((ActivityNewSubjectBinding) this.mBaseBinding).optionImage.setVisibility(0);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(SendDelArticleSuccess event) {
        if (event != null && this.mShowCircleEmpty && event.snsInfo.communitylist.getPosition(this.mCircleInfo.id) >= 0) {
            getCircleDetailInfo();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(AddCircleEvent event) {
        if (event != null && event.type == AddCircleEvent.ATTENTION_CANCEL_CIRCLE_TYPE && event.info != null && this.mCircleInfo.id == event.info.id) {
            this.mCircleInfo.isfans.set(event.info.isfans.get());
            if (this.mCircleInfo.isfans.get() == 0) {
                ((ActivityNewSubjectBinding) this.mBaseBinding).ibFollow.setImageResource(R.drawable.follow_white_selector);
                this.mCircleInfo.fanscount--;
            } else if (this.mCircleInfo.isfans.get() == 1) {
                ((ActivityNewSubjectBinding) this.mBaseBinding).ibFollow.setImageResource(R.drawable.follow_white_cancel_selector);
                this.mCircleInfo.fanscount++;
            }
            ((ActivityNewSubjectBinding) this.mBaseBinding).tvFollowerNum.setText(FengUtil.numberFormat(this.mCircleInfo.fanscount) + "人关注");
        }
    }

    private void uploadPv() {
        if (this.mFragments.size() > this.mIndex) {
            ((VehicleContentFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().setBasePage(getLogCurrentPage());
            ((VehicleContentFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageInTime();
        }
    }

    protected void onPause() {
        if (this.mFragments.size() > this.mIndex) {
            ((VehicleContentFragment) this.mFragments.get(this.mIndex)).getLogGatherInfo().addPageOutTime();
        }
        super.onPause();
        this.mIsActive = false;
    }

    protected void onResume() {
        if (this.mIsFirstLog) {
            this.mIsFirstLog = false;
        } else {
            uploadPv();
        }
        super.onResume();
        this.mIsActive = true;
    }

    private void regToWX() {
        this.mIwxapi = WXAPIFactory.createWXAPI(this, HttpConstant.BASE_WX_APP_ID, false);
        this.mIwxapi.registerApp(HttpConstant.BASE_WX_APP_ID);
    }

    private void initIconThumb(final boolean isShare) {
        String strUrl = "";
        if (isShare) {
            strUrl = FengUtil.getUniformScaleUrl(this.mCircleInfo.image, 640, 0.56f);
        } else {
            strUrl = FengUtil.getFixedCircleUrl(this.mCircleInfo.image, this.m140, this.m140);
        }
        FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(strUrl));
        File file = null;
        if (resource != null) {
            file = resource.getFile();
        }
        Bitmap bitmap = null;
        if (file != null && file.exists()) {
            bitmap = ImageUtil.getBitmap(file);
        }
        if (bitmap != null) {
            if (isShare) {
                shareMiniProgram(bitmap);
            } else {
                createShortCut(bitmap);
            }
        } else if (!TextUtils.isEmpty(this.mCircleInfo.image.url)) {
            ImageUtil.downLoadImage(strUrl, new OnDownloadImageListener() {
                public void onDownloadSuccess(final Bitmap bitmap2) {
                    NewSubjectActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (isShare) {
                                NewSubjectActivity.this.shareMiniProgram(bitmap2);
                            } else {
                                NewSubjectActivity.this.createShortCut(bitmap2);
                            }
                        }
                    });
                }

                public void onDownloadFailed() {
                    NewSubjectActivity.this.runOnUiThread(new Runnable() {
                        public void run() {
                            if (isShare) {
                                NewSubjectActivity.this.shareMiniProgram(null);
                            } else {
                                NewSubjectActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                            }
                        }
                    });
                }
            });
        } else if (isShare) {
            shareMiniProgram(null);
        } else {
            showSecondTypeToast((int) R.string.net_abnormal);
        }
    }

    private void shareMiniProgram(Bitmap bitmap) {
        try {
            WXMiniProgramObject miniProgram = new WXMiniProgramObject();
            if (FengApplication.getInstance().getIsOpenTest()) {
                miniProgram.miniprogramType = 2;
            } else {
                miniProgram.miniprogramType = 0;
            }
            miniProgram.webpageUrl = MessageFormat.format(HttpConstant.SHARE_SERIES, new Object[]{Integer.valueOf(this.mSeriesInfo.id)});
            miniProgram.userName = "gh_f26575d49d44";
            String strTitle = "快来看看关于「" + this.mCircleInfo.name + "」，大家都在聊些什么吧！";
            if (this.IS_CAR_MODE) {
                miniProgram.path = "pages/cars/index?type=0&id=" + this.mSeriesInfo.id;
            } else {
                miniProgram.path = "pages/cars/index?type=1&id=" + this.mCircleInfo.id;
            }
            WXMediaMessage msg = new WXMediaMessage(miniProgram);
            msg.title = strTitle;
            msg.mediaObject = miniProgram;
            if (bitmap == null) {
                msg.thumbData = ImageUtil.bitmap2Bytes(BitmapFactory.decodeResource(getResources(), R.drawable.default_post_32), 32768, true);
            } else {
                msg.thumbData = ImageUtil.bitmap2Bytes(bitmap, 32768, true);
            }
            Req rep = new Req();
            rep.transaction = buildTransaction("miniProgram");
            rep.message = msg;
            rep.scene = 0;
            this.mIwxapi.sendReq(rep);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getLogCurrentPage() {
        if (this.mCircleInfo.sublist.size() > this.mIndex) {
            return "app_community_" + ((CircleSubclassInfo) this.mCircleInfo.sublist.get(this.mIndex)).id + "?communityid=" + this.mCirclesRequestId + "&sortid=" + this.mContentSortType + (this.mSeriesInfo.id > 0 ? "&carsid=" + this.mSeriesInfo.id : "");
        }
        return "app_community_0?communityid=" + this.mCirclesRequestId + "&sortid=" + this.mContentSortType + (this.mSeriesInfo.id > 0 ? "&carsid=" + this.mSeriesInfo.id : "");
    }

    public boolean getAllowShowAudioBtn() {
        return true;
    }
}
