package com.feng.car.view;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.feng.car.FengApplication;
import com.feng.car.activity.ArticleDetailActivity;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.NewSubjectActivity;
import com.feng.car.activity.PopularProgramListActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.adapter.CommodityAdapter;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.GoodsServeItemAdater;
import com.feng.car.adapter.RecommendAdapter;
import com.feng.car.databinding.ArticleBottomInfolineLayoutBinding;
import com.feng.car.databinding.DialogOpenNotifyBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.ad.AdvertInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.circle.CircleInfo;
import com.feng.car.entity.dealer.CommodityInfo;
import com.feng.car.entity.hotshow.HotShowInfo;
import com.feng.car.entity.sns.RecommandCarsHolder;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.UmengConstans;
import com.feng.car.utils.WXUtils;
import com.feng.car.view.convenientbanner.ConvenientBanner.PageIndicatorAlign;
import com.feng.car.view.convenientbanner.holder.CBViewHolderCreator;
import com.feng.car.view.convenientbanner.listener.OnItemClickListener;
import com.feng.car.view.convenientbanner.view.CBLoopViewPager.ViewPageTouchedListener;
import com.feng.car.view.tagview.TagCloudView.OnTagClickListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.common.a;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class ArticleRecommendView extends LinearLayout {
    private AdvertInfo mAdvertInfo;
    private ArticleBottomInfolineLayoutBinding mBinding;
    private Dialog mCommonDialog;
    private Context mContext;
    private boolean mIsSetNotification;
    private boolean mIsSlidingToFirst = false;
    private DialogOpenNotifyBinding mOpenNotifyBinding;
    private SnsInfo mSnsInfo;
    private SwipeBackLayout mSwipeBackLayout;
    private int mVideoPlayCount = 0;

    public void setVideoPlayCount(int videoPlayCount) {
        this.mVideoPlayCount = videoPlayCount;
    }

    public ArticleRecommendView(Context context) {
        super(context);
        init(context);
    }

    public ArticleRecommendView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ArticleRecommendView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mBinding = ArticleBottomInfolineLayoutBinding.inflate(LayoutInflater.from(this.mContext));
        addView(this.mBinding.getRoot());
    }

    public void setSnsInfo(SnsInfo snsInfo, SwipeBackLayout swipeBackLayout) {
        this.mSnsInfo = snsInfo;
        this.mSwipeBackLayout = swipeBackLayout;
        refreshData();
    }

    public void setAdInfo(AdvertInfo adInfo) {
        if (adInfo != null) {
            this.mAdvertInfo = adInfo;
            if (this.mAdvertInfo.adid > 0) {
                LayoutParams params = (LayoutParams) this.mBinding.adNotice.getLayoutParams();
                params.height = FengUtil.getScreenWidth(this.mContext) / 6;
                this.mBinding.adNotice.setLayoutParams(params);
                this.mBinding.rlAd.setVisibility(0);
                this.mBinding.adNotice.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(this.mAdvertInfo.tmpmap.image.size() > 0 ? (ImageInfo) this.mAdvertInfo.tmpmap.image.get(0) : new ImageInfo(), FengConstant.IMAGEMIDDLEWIDTH, 0.17f)));
                this.mBinding.tvAdLabel.setText(this.mAdvertInfo.tmpmap.label);
                this.mBinding.adNotice.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ArticleRecommendView.this.mAdvertInfo.adClickHandle(ArticleRecommendView.this.mContext);
                    }
                });
                return;
            }
            this.mBinding.rlAd.setVisibility(8);
        }
    }

    private void refreshData() {
        if (this.mSnsInfo == null || this.mSnsInfo.id == 0) {
            this.mBinding.banner.setVisibility(8);
            this.mBinding.bannerDivider.setVisibility(8);
            this.mBinding.recommandLine.setVisibility(8);
            this.mBinding.listView.setVisibility(8);
            this.mBinding.bottomDivider.setVisibility(8);
            return;
        }
        if ((this.mContext instanceof ArticleDetailActivity) || (this.mContext instanceof VideoFinalPageActivity)) {
            this.mBinding.publishTime.setVisibility(0);
            this.mBinding.publishTime.setText((CharSequence) this.mSnsInfo.publishtime.get());
            if (this.mContext instanceof VideoFinalPageActivity) {
                this.mBinding.tvPlayNum.setVisibility(0);
                this.mBinding.tvPlayNum.setText(FengUtil.numberFormat(this.mVideoPlayCount) + "次播放");
            } else {
                this.mBinding.tvPlayNum.setVisibility(8);
            }
        } else {
            this.mBinding.publishTime.setVisibility(8);
        }
        if (this.mSnsInfo.communitylist.size() > 0) {
            this.mBinding.tgView.setVisibility(0);
            this.mBinding.tgView.setTags(this.mSnsInfo.communitylist.getCircleList());
            this.mBinding.tgView.setOnTagClickListener(new OnTagClickListener() {
                public void onTagClick(TextView tagView, CircleInfo circleInfo, int position) {
                    circleInfo.intentToCircleFinalPage(ArticleRecommendView.this.mContext);
                }
            });
        } else {
            this.mBinding.tgView.setVisibility(8);
        }
        if (this.mSnsInfo.ishotshow == 1) {
            if (this.mSnsInfo.carsList == null || this.mSnsInfo.carsList.size() <= 0) {
                this.mBinding.banner.setVisibility(8);
                this.mBinding.bannerDivider.setVisibility(8);
            } else {
                this.mBinding.banner.setPages(new CBViewHolderCreator<RecommandCarsHolder>() {
                    public RecommandCarsHolder createHolder() {
                        return new RecommandCarsHolder();
                    }
                }, this.mSnsInfo.carsList).setPageIndicator(new int[]{2130837977, 2130837976}).setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(int position) {
                        if (position >= 0 && position < ArticleRecommendView.this.mSnsInfo.carsList.size()) {
                            Intent intent = new Intent(ArticleRecommendView.this.mContext, NewSubjectActivity.class);
                            intent.putExtra(HttpConstant.CARS_ID, ((CarSeriesInfo) ArticleRecommendView.this.mSnsInfo.carsList.get(position)).id);
                            intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, ((CarSeriesInfo) ArticleRecommendView.this.mSnsInfo.carsList.get(position)).communityinfo.id);
                            ArticleRecommendView.this.mContext.startActivity(intent);
                        }
                    }
                }).setPageIndicatorAlign(PageIndicatorAlign.CENTER_HORIZONTAL);
                this.mBinding.banner.setVisibility(0);
                if (this.mSnsInfo.carsList.size() == 1) {
                    this.mBinding.bannerDivider.setVisibility(8);
                } else {
                    this.mBinding.bannerDivider.setVisibility(0);
                }
                this.mBinding.banner.setViewPageTouchListener(new ViewPageTouchedListener() {
                    public void onTouched(MotionEvent ev) {
                        switch (ev.getAction()) {
                            case 0:
                                ((BaseActivity) ArticleRecommendView.this.mContext).mSwipeBackLayout.setCanScroll(false);
                                return;
                            case 1:
                                ((BaseActivity) ArticleRecommendView.this.mContext).mSwipeBackLayout.setCanScroll(true);
                                return;
                            case 3:
                                ((BaseActivity) ArticleRecommendView.this.mContext).mSwipeBackLayout.setCanScroll(true);
                                return;
                            default:
                                return;
                        }
                    }
                });
                if (this.mSnsInfo.carsList.size() == 1) {
                    this.mBinding.banner.setCanLoop(false);
                    this.mBinding.banner.setPointViewVisible(false);
                } else {
                    this.mBinding.banner.setCanLoop(true);
                    this.mBinding.banner.setPointViewVisible(true);
                }
            }
            if (this.mSnsInfo.recommendList == null || this.mSnsInfo.recommendList.size() <= 0) {
                this.mBinding.listView.setVisibility(8);
                this.mBinding.recommandLine.setVisibility(8);
            } else {
                RecommendAdapter recommendAdapter = new RecommendAdapter(this.mContext, this.mSnsInfo.recommendList);
                recommendAdapter.setSnsInfo(this.mSnsInfo);
                this.mBinding.listView.setLayoutManager(new LinearLayoutManager(this.mContext));
                this.mBinding.listView.setAdapter(recommendAdapter);
                this.mBinding.listView.setVisibility(0);
                this.mBinding.recommandLine.setVisibility(0);
                this.mBinding.recommandText.setText(this.mSnsInfo.hotshow.name);
                this.mBinding.moreText.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        Intent intent = new Intent(ArticleRecommendView.this.mContext, PopularProgramListActivity.class);
                        intent.putExtra(HttpConstant.HOT_SHOW_ID, ArticleRecommendView.this.mSnsInfo.hotshow.id);
                        intent.putExtra(HttpConstant.HOT_SHOW_NAME, ArticleRecommendView.this.mSnsInfo.hotshow.name);
                        ArticleRecommendView.this.mContext.startActivity(intent);
                    }
                });
            }
            if (this.mSnsInfo.hotshow.isfollow.get() == 0) {
                this.mBinding.followImage.setVisibility(0);
                this.mBinding.followImage.setImageResource(2130837877);
                this.mBinding.followImage.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ArticleRecommendView.this.followHotShow(ArticleRecommendView.this.mSnsInfo.hotshow);
                    }
                });
            } else {
                this.mBinding.followImage.setImageResource(2130837872);
                this.mBinding.followImage.setVisibility(8);
            }
            this.mBinding.bottomDivider.setVisibility(0);
        } else {
            if (this.mSnsInfo.carsList == null || this.mSnsInfo.carsList.size() <= 0) {
                this.mBinding.banner.setVisibility(8);
                this.mBinding.bannerDivider.setVisibility(8);
            } else {
                this.mBinding.banner.setPages(new CBViewHolderCreator<RecommandCarsHolder>() {
                    public RecommandCarsHolder createHolder() {
                        return new RecommandCarsHolder();
                    }
                }, this.mSnsInfo.carsList).setPageIndicator(new int[]{2130837977, 2130837976}).setOnItemClickListener(new OnItemClickListener() {
                    public void onItemClick(int position) {
                        if (position >= 0 && position < ArticleRecommendView.this.mSnsInfo.carsList.size()) {
                            Intent intent = new Intent(ArticleRecommendView.this.mContext, NewSubjectActivity.class);
                            intent.putExtra(HttpConstant.CARS_ID, ((CarSeriesInfo) ArticleRecommendView.this.mSnsInfo.carsList.get(position)).id);
                            intent.putExtra(NewSubjectActivity.CIRCLES_REQUEST_ID, ((CarSeriesInfo) ArticleRecommendView.this.mSnsInfo.carsList.get(position)).communityinfo.id);
                            ArticleRecommendView.this.mContext.startActivity(intent);
                        }
                    }
                }).setPageIndicatorAlign(PageIndicatorAlign.CENTER_HORIZONTAL);
                this.mBinding.banner.setVisibility(0);
                if (this.mSnsInfo.carsList.size() == 1) {
                    this.mBinding.bannerDivider.setVisibility(8);
                } else {
                    this.mBinding.bannerDivider.setVisibility(0);
                }
                if (this.mSnsInfo.carsList.size() == 1) {
                    this.mBinding.banner.setCanLoop(false);
                    this.mBinding.banner.setPointViewVisible(false);
                } else {
                    this.mBinding.banner.setCanLoop(true);
                    this.mBinding.banner.setPointViewVisible(true);
                }
            }
            this.mBinding.recommandLine.setVisibility(8);
            this.mBinding.listView.setVisibility(8);
            this.mBinding.bottomDivider.setVisibility(8);
        }
        if (this.mSnsInfo.shopcommodityvos.size() <= 0 || this.mSnsInfo.shopvo.id <= 0) {
            this.mBinding.llCommodity.setVisibility(8);
            this.mBinding.vGoodsLine.setVisibility(8);
            return;
        }
        this.mBinding.llCommodity.setVisibility(0);
        this.mBinding.vGoodsLine.setVisibility(0);
        this.mBinding.tvStoreUserName.setText(this.mSnsInfo.shopvo.realname);
        this.mBinding.tvUserInfo.setText(this.mSnsInfo.shopvo.shopname + " " + this.mSnsInfo.shopvo.position);
        this.mBinding.tvOnSaleNum.setText(this.mSnsInfo.shopvo.salecount + "");
        this.mBinding.tvSaleNum.setText(this.mSnsInfo.shopvo.dealcount + "");
        this.mBinding.tvViewedNum.setText(this.mSnsInfo.shopvo.viewcount + "");
        this.mBinding.tvInShop.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_SHOP_PATH, Integer.valueOf(ArticleRecommendView.this.mSnsInfo.shopvo.id)));
            }
        });
        if (this.mSnsInfo.shopvo.saletype == 1) {
            this.mBinding.vLine.setVisibility(0);
            this.mBinding.llServeList.setVisibility(8);
            this.mBinding.rvCommodity.setVisibility(0);
            CommodityAdapter adapter = new CommodityAdapter(this.mContext, this.mSnsInfo.shopcommodityvos);
            LinearLayoutManager manager = new LinearLayoutManager(this.mContext);
            manager.setOrientation(0);
            this.mBinding.rvCommodity.setLayoutManager(manager);
            this.mBinding.rvCommodity.setAdapter(adapter);
            adapter.setOnItemClickLister(new com.github.jdsjlzx.interfaces.OnItemClickListener() {
                public void onItemClick(View view, int position) {
                    CommodityInfo commodityInfo = (CommodityInfo) ArticleRecommendView.this.mSnsInfo.shopcommodityvos.get(position);
                    WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_COMMODITY_DETAILS_PATH, Integer.valueOf(commodityInfo.id), Integer.valueOf(commodityInfo.shopid)));
                }
            });
            if (this.mSnsInfo.shopcommodityvos.size() > 2 && this.mSwipeBackLayout != null) {
                this.mBinding.rvCommodity.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        switch (event.getAction()) {
                            case 0:
                                ArticleRecommendView.this.mSwipeBackLayout.setCanScroll(false);
                                break;
                            case 1:
                                ArticleRecommendView.this.mSwipeBackLayout.setCanScroll(true);
                                break;
                        }
                        return false;
                    }
                });
                this.mBinding.rvCommodity.addOnScrollListener(new OnScrollListener() {
                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                        super.onScrollStateChanged(recyclerView, newState);
                        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                        if (newState == 0) {
                            if (manager.findFirstCompletelyVisibleItemPosition() == 0 && ArticleRecommendView.this.mIsSlidingToFirst) {
                                ArticleRecommendView.this.mSwipeBackLayout.setCanScroll(true);
                            }
                        } else if (newState == 1) {
                            ArticleRecommendView.this.mSwipeBackLayout.setCanScroll(false);
                        }
                    }

                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        if (dx > 0) {
                            ArticleRecommendView.this.mIsSlidingToFirst = false;
                        } else {
                            ArticleRecommendView.this.mIsSlidingToFirst = true;
                        }
                    }
                });
            }
        } else {
            this.mBinding.vLine.setVisibility(8);
            this.mBinding.rvCommodity.setVisibility(8);
            this.mBinding.llServeList.setVisibility(0);
            GoodsServeItemAdater adater = new GoodsServeItemAdater(this.mContext, this.mSnsInfo.scsl.contents);
            this.mBinding.rvServeList.setLayoutManager(new LinearLayoutManager(this.mContext));
            this.mBinding.rvServeList.setAdapter(adater);
            this.mBinding.tvPrice.setText("总价：" + this.mSnsInfo.scsl.price + "元");
            final CommodityInfo commodityInfo = (CommodityInfo) this.mSnsInfo.shopcommodityvos.get(0);
            this.mBinding.adGoods.setAutoImageURI(Uri.parse(FengUtil.getUniformScaleUrl(commodityInfo.imageinfo, 200, 1.0f)));
            this.mBinding.tvGoodsName.setText(commodityInfo.title);
            this.mBinding.tvViewedGoods.setText(FengUtil.numberFormat(commodityInfo.viewcount));
            this.mBinding.rlGoodsInfo.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_COMMODITY_DETAILS_PATH, Integer.valueOf(commodityInfo.id), Integer.valueOf(commodityInfo.shopid)));
                }
            });
        }
        if (this.mSnsInfo.shopvo.id > 0) {
            this.mBinding.rlInfo.setVisibility(0);
            this.mBinding.llNum.setVisibility(0);
            this.mBinding.vLine.setVisibility(0);
            this.mBinding.tvStoreUserName.setText(this.mSnsInfo.shopvo.realname);
            this.mBinding.tvUserInfo.setText(this.mSnsInfo.shopvo.shopname + " " + this.mSnsInfo.shopvo.position);
            this.mBinding.tvOnSaleNum.setText(this.mSnsInfo.shopvo.salecount + "");
            this.mBinding.tvSaleNum.setText(this.mSnsInfo.shopvo.dealcount + "");
            this.mBinding.tvViewedNum.setText(this.mSnsInfo.shopvo.viewcount + "");
            this.mBinding.tvInShop.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    WXUtils.getInstance().lunchMiniProgram(FengUtil.formatWxMinPath(HttpConstant.WX_MIN_SHOP_PATH, Integer.valueOf(ArticleRecommendView.this.mSnsInfo.shopvo.id)));
                }
            });
            return;
        }
        this.mBinding.rlInfo.setVisibility(8);
        this.mBinding.llNum.setVisibility(8);
        this.mBinding.vLine.setVisibility(8);
    }

    private void followHotShow(final HotShowInfo info) {
        Map<String, Object> map = new HashMap();
        if (info.isfollow.get() == 1) {
            map.put(HttpConstant.ISFOLLOW, String.valueOf(0));
        } else {
            map.put(HttpConstant.ISFOLLOW, String.valueOf(1));
        }
        map.put(HttpConstant.HOT_SHOW_ID, String.valueOf(info.id));
        FengApplication.getInstance().httpRequest(HttpConstant.HOTSHOW_FOLLOW, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) ArticleRecommendView.this.mContext).showThirdTypeToast(2131230912);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) ArticleRecommendView.this.mContext).showSecondTypeToast(2131231028);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    if (new JSONObject(content).getInt("code") != 1) {
                        ((BaseActivity) ArticleRecommendView.this.mContext).showSecondTypeToast(2131231028);
                    } else if (info.isfollow.get() == 1) {
                        info.isfollow.set(0);
                        ArticleRecommendView.this.mBinding.followImage.setImageResource(2130837877);
                    } else {
                        info.isfollow.set(1);
                        ArticleRecommendView.this.mBinding.followImage.setImageResource(2130837872);
                        if (info.isremind.get() == 0) {
                            ArticleRecommendView.this.showRemindDialog();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    ((BaseActivity) ArticleRecommendView.this.mContext).showSecondTypeToast(2131231028);
                }
            }
        });
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if (hasWindowFocus && this.mIsSetNotification) {
            this.mIsSetNotification = false;
            openRemind();
        }
    }

    private void showRemindDialog() {
        if (this.mOpenNotifyBinding == null) {
            this.mOpenNotifyBinding = DialogOpenNotifyBinding.inflate(LayoutInflater.from(this.mContext));
            this.mOpenNotifyBinding.ivClose.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ArticleRecommendView.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.openText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ArticleRecommendView.this.openRemind();
                    ArticleRecommendView.this.mCommonDialog.dismiss();
                }
            });
            this.mOpenNotifyBinding.cancelText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ArticleRecommendView.this.mCommonDialog.dismiss();
                }
            });
        }
        if (this.mCommonDialog == null) {
            this.mCommonDialog = new Dialog(this.mContext, 2131361986);
            this.mCommonDialog.setCanceledOnTouchOutside(true);
            this.mCommonDialog.setCancelable(true);
            Window window = this.mCommonDialog.getWindow();
            window.setGravity(17);
            window.setContentView(this.mOpenNotifyBinding.getRoot());
            window.setLayout(-1, -2);
        }
        this.mCommonDialog.show();
    }

    private void openRemind() {
        if (FengUtil.isNotificationAuthorityEnabled(this.mContext)) {
            setRemind(this.mSnsInfo.hotshow.id);
            return;
        }
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(this.mContext.getString(2131231401), false));
        CommonDialog.showCommonDialog(this.mContext, this.mContext.getString(2131231403), "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.addFlags(CommonNetImpl.FLAG_AUTH);
                    intent.setData(Uri.fromParts(a.c, ArticleRecommendView.this.mContext.getPackageName(), null));
                    ArticleRecommendView.this.mContext.startActivity(intent);
                    ArticleRecommendView.this.mIsSetNotification = true;
                }
            }
        }, true);
    }

    private void setRemind(int hotshowid) {
        Map<String, Object> map = new HashMap();
        map.put(HttpConstant.HOT_SHOW_ID, String.valueOf(hotshowid));
        map.put("type", String.valueOf(0));
        FengApplication.getInstance().httpRequest(HttpConstant.SET_REMIND, map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                ((BaseActivity) ArticleRecommendView.this.mContext).showSecondTypeToast(2131231273);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                ((BaseActivity) ArticleRecommendView.this.mContext).showSecondTypeToast(2131231273);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    int code = new JSONObject(content).getInt("code");
                    if (code == 1) {
                        MobclickAgent.onEvent(ArticleRecommendView.this.mContext, UmengConstans.PROGRAM_REMIND);
                        ((BaseActivity) ArticleRecommendView.this.mContext).showFirstTypeToast(2131231402);
                        ArticleRecommendView.this.mSnsInfo.hotshow.isremind.set(1);
                        return;
                    }
                    FengApplication.getInstance().checkCode(HttpConstant.POPULAR_PROGRAM_DETAIL_LIST, code);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
