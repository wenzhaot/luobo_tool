package com.feng.car.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.request.ImageRequest;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityShowBigimageBinding;
import com.feng.car.databinding.ShowBigimageViewBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.model.LocationInfo;
import com.feng.car.event.ImageLocationEvent;
import com.feng.car.event.NineGridUnreigsterEvent;
import com.feng.car.event.RequestImageLocationEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.view.BigImageLoadProgressView;
import com.feng.car.view.BigimageLoadProgressDrawable;
import com.feng.car.view.BigimageLoadProgressDrawable$OnValueChangedListener;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.FixedViewPager$OnSwipeListener;
import com.feng.car.view.largeimage.LargeImageView;
import com.feng.car.view.largeimage.LargeImageView$LargeImageClickListener;
import com.feng.car.view.largeimage.LargeImageView$LargeImageOperationListener;
import com.feng.car.view.photoview.OnScaleChangeListener;
import com.feng.car.view.photoview.OnViewTapListener;
import com.feng.car.view.photoview.PhotoDraweeView;
import com.feng.library.emoticons.keyboard.SimpleCommonUtils;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.google.gson.reflect.TypeToken;
import com.umeng.analytics.MobclickAgent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShowBigImageActivity extends BaseActivity<ActivityShowBigimageBinding> {
    public static final String BIGIMAGE_KEY = "bigimage";
    public static final int ENTRANCE_TYPE_ARTICLE_IMAGE = 2003;
    public static final int ENTRANCE_TYPE_DEFAULT = 2001;
    public static final int ENTRANCE_TYPE_NINE_GRID_IMAGE = 2004;
    public static final int ENTRANCE_TYPE_SINGLE_IMAGE = 2002;
    public static final int IMAGE_TYPE_ARTICLE_FINAL = 1004;
    public static final int IMAGE_TYPE_COMMENT_EDIT = 1006;
    public static final int IMAGE_TYPE_COMMENT_FINAL = 1005;
    public static final int IMAGE_TYPE_DEFAULT = 1001;
    public static final int IMAGE_TYPE_HEAD_PORTRAIT_FINAL = 1007;
    public static final int IMAGE_TYPE_MICRO_EDIT = 1003;
    public static final int IMAGE_TYPE_MICRO_FINAL = 1002;
    public static final int IMAGE_TYPE_PRIVATE_CHAT_FINAL = 1008;
    public static final String LOCATION_INFO_JSON = "location_info_json";
    private MyAdapter adapter;
    private ImageInfo currentImageItem = null;
    private int describeCommentDefaultAlpha = 128;
    private int describeCommentHideAlpha = 64;
    private boolean isDescribeCommentBarHide = false;
    public ConnectionChangeReceiver mConnectionChangeReceiver;
    private int mCurrentIndex = 0;
    private int mEntranceType = -1;
    private int mImageQuality = -1;
    private float mImageScale = 1.0f;
    private boolean mIsAlreadyInitBackInfo = false;
    private boolean mIsPortrait = true;
    private boolean mIsWifiConnect = false;
    private List<ImageInfo> mList = new ArrayList();
    private LocationInfo mLocationInfo;
    private float mLongImageScale = 1.0f;
    int mOriginCenterX;
    int mOriginCenterY;
    int mOriginHeight;
    int mOriginLeft;
    int mOriginTop;
    int mOriginWidth;
    private boolean mReceiveLocation = false;
    private float mScaleX;
    private float mScaleY;
    private int mScreenHight;
    private int mScreenOrientation = -1;
    private int mScreenWidth;
    private float mTargetHeight;
    private View mTargetView;
    private float mTargetWidth;
    private float mTranslationX;
    private float mTranslationY;
    private int mType = 1001;
    private boolean needExitAnimate = true;
    private final int translateDescribeDuration = 350;

    public class ConnectionChangeReceiver extends BroadcastReceiver {
        public void onReceive(Context context, Intent intent) {
            if (FengUtil.isWifiConnectivity(ShowBigImageActivity.this)) {
                ShowBigImageActivity.this.mIsWifiConnect = true;
            } else {
                ShowBigImageActivity.this.mIsWifiConnect = false;
            }
        }
    }

    private class MyAdapter extends PagerAdapter {
        private boolean canScale = true;
        private Context mContext;
        private View mCurrentView;
        private LayoutInflater mInflater;
        private List<ImageInfo> pList;

        public void forbidScale() {
            this.canScale = false;
        }

        public void allowScale() {
            this.canScale = true;
        }

        MyAdapter(List<ImageInfo> list, Context con) {
            this.pList = list;
            this.mContext = con;
            this.mInflater = LayoutInflater.from(this.mContext);
        }

        List<ImageInfo> getPList() {
            return this.pList;
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            this.mCurrentView = (View) object;
        }

        public View getPrimaryItem() {
            return this.mCurrentView;
        }

        public int getCount() {
            return this.pList.size();
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            ShowBigimageViewBinding mBinding = ShowBigimageViewBinding.inflate(this.mInflater);
            ViewHolder holder = new ViewHolder(mBinding);
            if (this.canScale) {
                holder.mBinding.coverView.setVisibility(8);
                Log.e("===", "可以缩放");
            } else {
                holder.mBinding.coverView.setVisibility(0);
                Log.e("===", "禁止缩放");
            }
            ImageInfo image = (ImageInfo) this.pList.get(position);
            holder.bindTo(image);
            if (StringUtil.isEmpty(image.url)) {
                ShowBigImageActivity.this.mEntranceType = -1;
            }
            if (image.mimetype == 3) {
                String imageUrl;
                holder.mBinding.longImage.setVisibility(8);
                holder.mBinding.imageBig.setVisibility(8);
                holder.mBinding.imageBigGif.setVisibility(0);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImage(false);
                if (ShowBigImageActivity.this.isAddImageWaterMark()) {
                    imageUrl = FengUtil.getUniformScaleUrl(image, 640) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
                } else {
                    imageUrl = FengUtil.getUniformScaleUrl(image, 640);
                }
                holder.mBinding.imageBigGif.setAutoImageURI(Uri.parse(imageUrl));
                holder.mBinding.imageBigGif.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ShowBigImageActivity.this.finish();
                    }
                });
                holder.mBinding.imageBigGif.setOnLongClickListener(new OnLongClickListener() {
                    public boolean onLongClick(View v) {
                        ShowBigImageActivity.this.showMenuDialog();
                        return false;
                    }
                });
            } else {
                holder.mBinding.imageBigGif.setVisibility(8);
                if (FengUtil.isLongImage(image)) {
                    holder.mBinding.longImage.setVisibility(0);
                    holder.mBinding.imageBig.setVisibility(8);
                    holder.mBinding.longImage.setLargeImageClickListener(new LargeImageView$LargeImageClickListener() {
                        public void onClick() {
                            ShowBigImageActivity.this.finish();
                        }
                    });
                    holder.mBinding.longImage.setOnLongClickListener(new OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            ShowBigImageActivity.this.showMenuDialog();
                            return false;
                        }
                    });
                    if (position == ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.getCurrentItem()) {
                        ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImage(true);
                    }
                    holder.mBinding.longImage.setLargeImageOperationListener(new LargeImageView$LargeImageOperationListener() {
                        public void onScale(float scale) {
                            ShowBigImageActivity.this.mLongImageScale = scale;
                            if (scale > 1.1f) {
                                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setEnableScroll(false);
                            } else {
                                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setEnableScroll(true);
                            }
                        }

                        public void onScrollBottom() {
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToBottom(true);
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToTop(false);
                        }

                        public void onScrollTop() {
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToTop(true);
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToBottom(false);
                        }

                        public void onScroll() {
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToTop(false);
                            ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImageScrollToBottom(false);
                        }
                    });
                } else {
                    holder.mBinding.longImage.setVisibility(8);
                    holder.mBinding.imageBig.setVisibility(0);
                    if (position == ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.getCurrentItem()) {
                        ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImage(false);
                    }
                    holder.mBinding.imageBig.setOnScaleChangeListener(new OnScaleChangeListener() {
                        public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                            ShowBigImageActivity.this.mImageScale = scaleFactor;
                            if (scaleFactor < 1.1f) {
                                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setEnableScroll(true);
                            } else {
                                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setEnableScroll(false);
                            }
                        }
                    });
                    holder.mBinding.imageBig.setOnViewTapListener(new OnViewTapListener() {
                        public void onViewTap(View view, float x, float y) {
                            if (ShowBigImageActivity.this.mEntranceType == ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE || ShowBigImageActivity.this.mEntranceType == 2001) {
                                ShowBigImageActivity.this.finishWithAnimation();
                            } else if (ShowBigImageActivity.this.mEntranceType == 2004) {
                                ShowBigImageActivity.this.nineImgaeExit();
                            } else if (ShowBigImageActivity.this.mEntranceType == ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE) {
                                ShowBigImageActivity.this.finishWithDefaultAnimation();
                            } else {
                                ShowBigImageActivity.this.finish();
                            }
                        }
                    });
                    holder.mBinding.imageBig.setOnLongClickListener(new OnLongClickListener() {
                        public boolean onLongClick(View v) {
                            ShowBigImageActivity.this.showMenuDialog();
                            return false;
                        }
                    });
                }
                ShowBigImageActivity.this.showImage(holder.mBinding.imageBig, holder.mBinding.longImage, holder.mBinding.ivBigimageProgressView, image, this.mContext, false);
            }
            container.addView(holder.mBinding.getRoot());
            return mBinding.getRoot();
        }
    }

    public class ViewHolder {
        private ShowBigimageViewBinding mBinding;

        public ViewHolder(ShowBigimageViewBinding mAdapterBinding) {
            this.mBinding = mAdapterBinding;
        }

        void bindTo(ImageInfo imageInfo) {
            this.mBinding.setImageInfo(imageInfo);
            this.mBinding.executePendingBindings();
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_show_bigimage;
    }

    public void initView() {
        if (VERSION.SDK_INT >= 19) {
            getWindow().setFlags(67108864, 67108864);
        }
        this.mScreenWidth = FengUtil.getScreenWidth(this);
        this.mScreenHight = FengUtil.getScreenHeight(this);
        if (this.mScreenWidth > this.mScreenHight) {
            this.mIsPortrait = false;
        } else {
            this.mIsPortrait = true;
        }
        this.mImageQuality = SharedUtil.getInt(this, "image_quality", 0);
        this.mIsWifiConnect = FengUtil.isWifiConnectivity(this);
        this.mScreenOrientation = getResources().getConfiguration().orientation;
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setEnableScroll(true);
        getMyIntent();
        hideDefaultTitleBar();
        initListener();
        this.adapter = new MyAdapter(this.mList, this);
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setAdapter(this.adapter);
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.default_40PX));
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).tvBigimagePageText.setText((position + 1) + "/" + ShowBigImageActivity.this.mList.size());
                ShowBigImageActivity.this.mCurrentIndex = position;
                ShowBigImageActivity.this.currentImageItem = (ImageInfo) ShowBigImageActivity.this.mList.get(position);
                ShowBigImageActivity.this.setControllerBar();
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setEnableScroll(true);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.resetLongImageState();
                if (FengUtil.isLongImage((ImageInfo) ShowBigImageActivity.this.mList.get(position))) {
                    ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.setIsLongImage(true);
                    if (ShowBigImageActivity.this.mLongImageScale >= 1.1f) {
                        ((LargeImageView) ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.longImage)).setScale(1.0f);
                        ShowBigImageActivity.this.mLongImageScale = 1.0f;
                    }
                } else if (ShowBigImageActivity.this.mImageScale >= 1.1f) {
                    ((PhotoDraweeView) ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.image_big)).setScale(1.0f);
                    ShowBigImageActivity.this.mImageScale = 1.0f;
                }
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        if (this.mCurrentIndex <= 0) {
            this.mCurrentIndex = 0;
        }
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mCurrentIndex);
        this.currentImageItem = (ImageInfo) this.mList.get(this.mCurrentIndex);
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageArrowControllBar.getBackground().mutate().setAlpha(this.describeCommentDefaultAlpha);
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageArrowControllBar.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ShowBigImageActivity.this.translateUpDescribe();
            }
        });
        setControllerBar();
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                if (ShowBigImageActivity.this.mLocationInfo == null) {
                    String locationInfoJson = ShowBigImageActivity.this.getIntent().getStringExtra("location_info_json");
                    if (!TextUtils.isEmpty(locationInfoJson)) {
                        ShowBigImageActivity.this.mLocationInfo = (LocationInfo) JsonUtil.fromJson(locationInfoJson, LocationInfo.class);
                    }
                }
                ShowBigImageActivity.this.mOriginLeft = ShowBigImageActivity.this.mLocationInfo.left;
                ShowBigImageActivity.this.mOriginTop = ShowBigImageActivity.this.mLocationInfo.top;
                ShowBigImageActivity.this.mOriginWidth = ShowBigImageActivity.this.mLocationInfo.width;
                ShowBigImageActivity.this.mOriginHeight = ShowBigImageActivity.this.mLocationInfo.height;
                ShowBigImageActivity.this.mOriginCenterX = ShowBigImageActivity.this.mOriginLeft + (ShowBigImageActivity.this.mOriginWidth / 2);
                ShowBigImageActivity.this.mOriginCenterY = ShowBigImageActivity.this.mOriginTop + (ShowBigImageActivity.this.mOriginHeight / 2);
                int[] location = new int[2];
                if (ShowBigImageActivity.this.currentImageItem.mimetype == 3) {
                    ShowBigImageActivity.this.mTargetView = ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.image_big_gif);
                } else if (FengUtil.isLongImage(ShowBigImageActivity.this.currentImageItem)) {
                    ShowBigImageActivity.this.mTargetView = ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.longImage);
                } else {
                    ShowBigImageActivity.this.mTargetView = ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.image_big);
                }
                if (ShowBigImageActivity.this.mEntranceType == ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE || ShowBigImageActivity.this.mEntranceType == -1) {
                    ShowBigImageActivity.this.performDefaultEnterAnimation(ShowBigImageActivity.this.mTargetView);
                    return;
                }
                ShowBigImageActivity.this.mTargetView.getLocationOnScreen(location);
                ShowBigImageActivity.this.mTargetWidth = (float) ShowBigImageActivity.this.mTargetView.getWidth();
                ShowBigImageActivity.this.mTargetHeight = (float) ShowBigImageActivity.this.mTargetView.getHeight();
                float targetCenterY = ((float) location[1]) + (ShowBigImageActivity.this.mTargetHeight / 2.0f);
                ShowBigImageActivity.this.mTranslationX = ((float) ShowBigImageActivity.this.mOriginCenterX) - (((float) location[0]) + (ShowBigImageActivity.this.mTargetWidth / 2.0f));
                ShowBigImageActivity.this.mTranslationY = ((float) ShowBigImageActivity.this.mOriginCenterY) - targetCenterY;
                ShowBigImageActivity.this.mTargetView.setTranslationX(ShowBigImageActivity.this.mTranslationX);
                ShowBigImageActivity.this.mTargetView.setTranslationY(ShowBigImageActivity.this.mTranslationY);
                ShowBigImageActivity.this.mTargetHeight = ((float) ShowBigImageActivity.this.mOriginHeight) * ((((float) FengUtil.getScreenWidth(ShowBigImageActivity.this)) * 1.0f) / ((float) ShowBigImageActivity.this.mOriginWidth));
                ShowBigImageActivity.this.mScaleX = ((float) ShowBigImageActivity.this.mOriginWidth) / ShowBigImageActivity.this.mTargetWidth;
                ShowBigImageActivity.this.mScaleY = ((float) ShowBigImageActivity.this.mOriginHeight) / ShowBigImageActivity.this.mTargetHeight;
                ShowBigImageActivity.this.mTargetView.setScaleX(ShowBigImageActivity.this.mScaleX);
                ShowBigImageActivity.this.mTargetView.setScaleY(ShowBigImageActivity.this.mScaleY);
                ShowBigImageActivity.this.performEnterAnimation(ShowBigImageActivity.this.mTargetView);
            }
        });
        if (this.mScreenOrientation == 2) {
            hideAllFunctions();
        }
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setEnableScroll(true);
        ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setOnSwipeListener(new FixedViewPager$OnSwipeListener() {
            public void downSwipe() {
                ShowBigImageActivity.this.needExitAnimate = false;
                ShowBigImageActivity.this.finish();
            }

            public void overSwipe() {
            }

            public void onSwiping(float deltaY) {
                int alpha;
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).groupLine.setVisibility(8);
                ShowBigImageActivity.this.adapter.forbidScale();
                ShowBigImageActivity.this.adapter.notifyDataSetChanged();
                int height = ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).viewpager.getHeight() / 2;
                if (deltaY < ((float) height)) {
                    alpha = (int) ((1.0f - ((deltaY / ((float) height)) * 1.0f)) * 255.0f);
                } else {
                    alpha = 0;
                }
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).getRoot().getBackground().mutate().setAlpha(alpha);
            }

            public void onReduction() {
                ShowBigImageActivity.this.needExitAnimate = true;
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).groupLine.setVisibility(0);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).getRoot().getBackground().mutate().setAlpha(255);
                ShowBigImageActivity.this.adapter.allowScale();
                ShowBigImageActivity.this.adapter.notifyDataSetChanged();
            }
        });
    }

    private void getMyIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            this.mType = intent.getIntExtra("show_type", 1001);
            this.mEntranceType = intent.getIntExtra("entrance_type", 2001);
            String locationInfoJson = intent.getStringExtra("location_info_json");
            if (TextUtils.isEmpty(locationInfoJson)) {
                this.mLocationInfo = new LocationInfo();
            } else {
                this.mLocationInfo = (LocationInfo) JsonUtil.fromJson(locationInfoJson, LocationInfo.class);
            }
            String jsonList = intent.getStringExtra("mImageList");
            if (!StringUtil.isEmpty(jsonList)) {
                try {
                    this.mList.clear();
                    this.mList.addAll(JsonUtil.fromJson(jsonList, new TypeToken<List<ImageInfo>>() {
                    }));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            this.mCurrentIndex = intent.getIntExtra("position", 0);
            if (this.mCurrentIndex >= this.mList.size()) {
                this.mCurrentIndex = 0;
            }
            ((ActivityShowBigimageBinding) this.mBaseBinding).tvBigimagePageText.setText((this.mCurrentIndex + 1) + "/" + this.mList.size());
            if (this.mList.size() <= 0) {
                showSecondTypeToast((int) R.string.parameter_wrong);
            }
        }
    }

    private void initListener() {
        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        this.mConnectionChangeReceiver = new ConnectionChangeReceiver();
        registerReceiver(this.mConnectionChangeReceiver, filter);
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this.mConnectionChangeReceiver != null) {
            unregisterReceiver(this.mConnectionChangeReceiver);
        }
    }

    private void setControllerBar() {
        String original_url;
        final ImageInfo image = (ImageInfo) this.adapter.getPList().get(this.mCurrentIndex);
        ((ActivityShowBigimageBinding) this.mBaseBinding).setImageInfo(image);
        if (image.width <= 0 || ((float) (image.height * 640)) / (((float) image.width) * 1.0f) <= ((float) this.mScreenHight)) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setEnableScroll(true);
        } else {
            ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setEnableScroll(false);
        }
        if (isAddImageWaterMark()) {
            original_url = FengUtil.getUniformScaleUrl(image, 1600) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
        } else {
            original_url = FengUtil.getUniformScaleUrl(image, 1600);
        }
        if (FengApplication.getInstance().containsHd(original_url)) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setTag(Boolean.valueOf(true));
            ((ActivityShowBigimageBinding) this.mBaseBinding).rlBigimageHd.setEnabled(false);
        } else {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.bigimage_hd_button_selector);
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setTag(Boolean.valueOf(false));
            ((ActivityShowBigimageBinding) this.mBaseBinding).rlBigimageHd.setEnabled(true);
        }
        if (this.mImageQuality == 1) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setTag(Boolean.valueOf(true));
        } else if (this.mImageQuality != 2 && this.mImageQuality == 0 && this.mIsWifiConnect) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setTag(Boolean.valueOf(true));
        }
        if (this.mType == 1002) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).llBigimageDescribeContainer.setVisibility(8);
        } else if (this.mType == 1003) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageBottomShadow.setVisibility(8);
            ((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer.setVisibility(8);
        } else if (this.mType == 1004) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageBottomShadow.setVisibility(8);
        } else if (this.mType == 1005) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).tvBigimagePageText.setVisibility(8);
            ((ActivityShowBigimageBinding) this.mBaseBinding).llBigimageDescribeContainer.setVisibility(8);
        } else if (this.mType == 1006) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).tvBigimagePageText.setVisibility(8);
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageBottomShadow.setVisibility(8);
            ((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer.setVisibility(8);
        } else if (this.mType == 1007) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).tvBigimagePageText.setVisibility(8);
            ((ActivityShowBigimageBinding) this.mBaseBinding).llBigimageDescribeContainer.setVisibility(8);
        }
        if (image.mimetype == 3) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.setVisibility(8);
        } else if (image.width >= 640) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).rlBigimageHd.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    MobclickAgent.onEvent(ShowBigImageActivity.this, "FinalPage_pic_hdpic");
                    ShowBigImageActivity.this.showImage((PhotoDraweeView) ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.image_big), (LargeImageView) ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.longImage), (BigImageLoadProgressView) ShowBigImageActivity.this.adapter.getPrimaryItem().findViewById(R.id.iv_bigimage_progress_view), image, ShowBigImageActivity.this, true);
                    ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
                    ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageHd.setTag(Boolean.valueOf(true));
                    ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).rlBigimageHd.setEnabled(false);
                }
            });
        }
        if (StringUtil.isEmpty(image.description)) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.setVisibility(8);
        } else {
            ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.setVisibility(0);
            ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.scrollTo(0, 0);
            ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.setText(image.description);
            SimpleCommonUtils.spannableEmoticonFilter(((ActivityShowBigimageBinding) this.mBaseBinding).describeText, ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.getText().toString());
            ((ActivityShowBigimageBinding) this.mBaseBinding).llBigimageDescribeContainer.getBackground().mutate().setAlpha(this.describeCommentDefaultAlpha);
            ((ActivityShowBigimageBinding) this.mBaseBinding).describeText.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
        ((ActivityShowBigimageBinding) this.mBaseBinding).rlBigimageSave.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View view) {
                ShowBigImageActivity.this.checkBasePermission();
            }
        });
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageArrow.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (ShowBigImageActivity.this.isDescribeCommentBarHide) {
                    ShowBigImageActivity.this.translateUpDescribe();
                } else {
                    ShowBigImageActivity.this.translateDownDescribe();
                }
            }
        });
        if (this.mScreenOrientation == 2) {
            hideAllFunctions();
        }
    }

    private boolean isAddImageWaterMark() {
        if (this.mType == 1003 || this.mType == 1006 || this.mType == 1007 || this.mType == 1008) {
            return false;
        }
        return true;
    }

    private void showImage(final PhotoDraweeView photoDraweeView, LargeImageView longImage, final BigImageLoadProgressView progressView, final ImageInfo image, Context context, boolean isOriginalImage) {
        String imageUrl;
        String original_url;
        if (isAddImageWaterMark()) {
            imageUrl = FengUtil.getUniformScaleUrl(image, 640) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
            if (this.mType == 1004) {
                image.lowUrl = imageUrl;
            }
            original_url = FengUtil.getUniformScaleUrl(image, 1600) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
        } else {
            imageUrl = FengUtil.getUniformScaleUrl(image, 640);
            original_url = FengUtil.getUniformScaleUrl(image, 1600);
        }
        if (this.mImageQuality == 1) {
            isOriginalImage = true;
        } else if (this.mImageQuality != 2 && this.mImageQuality == 0 && this.mIsWifiConnect) {
            isOriginalImage = true;
        }
        if (FengUtil.isLongImage(image)) {
            progressView.setVisibility(8);
            if (isOriginalImage) {
                FengApplication.getInstance().addHdImage(original_url);
                FengUtil.downLoadImageToLargeImageView(this, original_url, longImage, new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
                    public void valueChanged(Rect bounds, int level) {
                        progressView.setValue(bounds, level);
                    }
                }));
                return;
            } else if (FengApplication.getInstance().containsHd(original_url)) {
                FengUtil.downLoadImageToLargeImageView(this, original_url, longImage, new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
                    public void valueChanged(Rect bounds, int level) {
                        progressView.setValue(bounds, level);
                    }
                }));
                return;
            } else {
                FengUtil.downLoadImageToLargeImageView(this, imageUrl, longImage, new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
                    public void valueChanged(Rect bounds, int level) {
                        progressView.setValue(bounds, level);
                    }
                }));
                return;
            }
        }
        progressView.setScreenOrientation(this.mScreenOrientation);
        if (this.mEntranceType != -1) {
            progressView.setVisibility(0);
        }
        GenericDraweeHierarchy hierarchy = (GenericDraweeHierarchy) photoDraweeView.getHierarchy();
        hierarchy.setProgressBarImage(new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
            public void valueChanged(Rect bounds, int level) {
                progressView.setValue(bounds, level);
            }
        }));
        photoDraweeView.setHierarchy(hierarchy);
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        if (this.mType == 1003 || this.mType == 1006) {
            controller.setLowResImageRequest(ImageRequest.fromUri(image.lowUrl));
            controller.setUri(Uri.parse(imageUrl));
        } else if (isOriginalImage) {
            controller.setLowResImageRequest(ImageRequest.fromUri(image.lowUrl));
            controller.setImageRequest(ImageRequest.fromUri(original_url));
            FengApplication.getInstance().addHdImage(original_url);
        } else if (FengApplication.getInstance().containsHd(original_url)) {
            controller.setLowResImageRequest(ImageRequest.fromUri(image.lowUrl));
            controller.setUri(Uri.parse(original_url));
        } else {
            controller.setLowResImageRequest(ImageRequest.fromUri(image.lowUrl));
            controller.setUri(Uri.parse(imageUrl));
        }
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<com.facebook.imagepipeline.image.ImageInfo>() {
            public void onFinalImageSet(String id, com.facebook.imagepipeline.image.ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo != null) {
                    float midScale;
                    float maxScale;
                    float scale;
                    if (((float) imageInfo.getWidth()) / (((float) imageInfo.getHeight()) * 1.0f) >= ((float) ShowBigImageActivity.this.mScreenWidth) / (((float) ShowBigImageActivity.this.mScreenHight) * 1.0f)) {
                        try {
                            float minScale;
                            float f;
                            if (imageInfo.getWidth() >= 640) {
                                minScale = 1.0f;
                                midScale = 2.0f;
                                maxScale = 3.0f;
                            } else {
                                scale = ((float) imageInfo.getWidth()) / (((float) ShowBigImageActivity.this.mScreenWidth) * 1.0f);
                                minScale = scale;
                                maxScale = 1.0f;
                                midScale = ((1.0f - scale) / 2.0f) + scale;
                            }
                            if (photoDraweeView.getMinimumScale() >= minScale) {
                                photoDraweeView.setMinimumScale(minScale);
                                if (photoDraweeView.getMediumScale() >= midScale) {
                                    photoDraweeView.setMediumScale(midScale);
                                    photoDraweeView.setMaximumScale(maxScale);
                                } else {
                                    photoDraweeView.setMaximumScale(maxScale);
                                    photoDraweeView.setMediumScale(midScale);
                                }
                            } else {
                                photoDraweeView.setMaximumScale(maxScale);
                                photoDraweeView.setMediumScale(midScale);
                                photoDraweeView.setMinimumScale(minScale);
                            }
                            PhotoDraweeView photoDraweeView = photoDraweeView;
                            if (ShowBigImageActivity.this.mIsPortrait) {
                                f = 1.0f;
                            } else {
                                f = minScale;
                            }
                            photoDraweeView.setScale(f, (float) (ShowBigImageActivity.this.mScreenWidth / 2), (float) (ShowBigImageActivity.this.mScreenHight / 2), true);
                        } catch (Exception e) {
                            photoDraweeView.setScale(1.0f, (float) (ShowBigImageActivity.this.mScreenWidth / 2), 0.0f, true);
                            return;
                        } finally {
                            progressView.setVisibility(8);
                            progressView.setValue(null, 0);
                            photoDraweeView.update(image.width, image.height);
                        }
                    } else {
                        scale = ShowBigImageActivity.this.getScale(image);
                        maxScale = scale + 1.0f;
                        midScale = ((maxScale - 1.0f) / 2.0f) + 1.0f;
                        if (photoDraweeView.getMinimumScale() >= 1.0f) {
                            photoDraweeView.setMinimumScale(1.0f);
                            if (photoDraweeView.getMediumScale() >= midScale) {
                                photoDraweeView.setMediumScale(midScale);
                                photoDraweeView.setMaximumScale(maxScale);
                            } else {
                                photoDraweeView.setMaximumScale(maxScale);
                                photoDraweeView.setMediumScale(midScale);
                            }
                        } else {
                            photoDraweeView.setMaximumScale(maxScale);
                            photoDraweeView.setMediumScale(midScale);
                            photoDraweeView.setMinimumScale(1.0f);
                        }
                        PhotoDraweeView photoDraweeView2 = photoDraweeView;
                        if (!ShowBigImageActivity.this.mIsPortrait) {
                            scale = 1.0f;
                        }
                        photoDraweeView2.setScale(scale, (float) (ShowBigImageActivity.this.mScreenWidth / 2), 0.0f, true);
                    }
                    progressView.setVisibility(8);
                    progressView.setValue(null, 0);
                    photoDraweeView.update(image.width, image.height);
                }
            }
        });
        photoDraweeView.setController(controller.build());
    }

    private float getScale(ImageInfo info) {
        if (info.width <= 0 || info.height <= 0) {
            return 1.0f;
        }
        return ((float) (this.mScreenWidth * info.height)) / (((float) (this.mScreenHight * info.width)) * 1.0f);
    }

    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(BIGIMAGE_KEY, this.currentImageItem);
        intent.putExtra("position", this.mCurrentIndex);
        setResult(-1, intent);
        super.finish();
        if (this.needExitAnimate) {
            overridePendingTransition(R.anim.in_righttoleft, R.anim.out_lefttoright);
        } else {
            overridePendingTransition(0, 0);
        }
        Fresco.getImagePipeline().clearMemoryCaches();
    }

    private void showMenuDialog() {
        DialogItemEntity watchOriginalItem = new DialogItemEntity(getString(R.string.watch_original_picture), false);
        DialogItemEntity saveItem = new DialogItemEntity(getString(R.string.save_picture), false);
        List<DialogItemEntity> list = new ArrayList();
        list.add(watchOriginalItem);
        list.add(saveItem);
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                switch (position) {
                    case 0:
                        ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).rlBigimageHd.callOnClick();
                        return;
                    case 1:
                        ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).rlBigimageSave.callOnClick();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    private void showAllFunctions() {
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageBottomShadow.setVisibility(0);
        if (this.isDescribeCommentBarHide) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageArrowControllBar.setVisibility(0);
        }
        ((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer.setVisibility(0);
        setControllerBar();
    }

    private void hideAllFunctions() {
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageBottomShadow.setVisibility(8);
        ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageArrowControllBar.setVisibility(8);
        ((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer.setVisibility(8);
    }

    private void savingImage(ImageInfo image, File file, String fileNameWithoutSuffix) {
        if (image == null || file == null) {
            showSecondTypeToast((int) R.string.get_image_failed);
            return;
        }
        try {
            FengUtil.saveImageToGallery(this, image, file, fileNameWithoutSuffix);
            showThirdTypeToast((int) R.string.save);
        } catch (Exception e) {
            e.printStackTrace();
            showSecondTypeToast((int) R.string.save_failed);
        }
    }

    private void translateDownDescribe() {
        float distance;
        if (TextUtils.isEmpty(((ActivityShowBigimageBinding) this.mBaseBinding).describeText.getText().toString())) {
            distance = (float) ((ActivityShowBigimageBinding) this.mBaseBinding).rlComment.getHeight();
        } else {
            distance = (float) (((ActivityShowBigimageBinding) this.mBaseBinding).describeText.getHeight() + ((ActivityShowBigimageBinding) this.mBaseBinding).rlComment.getHeight());
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer, "translationY", new float[]{0.0f, distance});
        animator.setDuration(350);
        animator.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageArrow.setImageResource(R.drawable.icon_bigimage_up_arrow);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).rlDescribeCommentContainer.setVisibility(8);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageArrowControllBar.setVisibility(0);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).llBigimageDescribeContainer.getBackground().mutate().setAlpha(ShowBigImageActivity.this.describeCommentHideAlpha);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
        this.isDescribeCommentBarHide = true;
    }

    private void translateUpDescribe() {
        float distance;
        if (TextUtils.isEmpty(((ActivityShowBigimageBinding) this.mBaseBinding).describeText.getText().toString())) {
            distance = (float) ((ActivityShowBigimageBinding) this.mBaseBinding).rlComment.getHeight();
        } else {
            distance = (float) (((ActivityShowBigimageBinding) this.mBaseBinding).describeText.getHeight() + ((ActivityShowBigimageBinding) this.mBaseBinding).rlComment.getHeight());
        }
        ObjectAnimator animator = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).rlDescribeCommentContainer, "translationY", new float[]{distance, 0.0f});
        animator.setDuration(350);
        animator.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).llBigimageDescribeContainer.getBackground().mutate().setAlpha(ShowBigImageActivity.this.describeCommentDefaultAlpha);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).rlDescribeCommentContainer.setVisibility(0);
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageArrowControllBar.setVisibility(8);
            }

            public void onAnimationEnd(Animator animation) {
                ((ActivityShowBigimageBinding) ShowBigImageActivity.this.mBaseBinding).ivBigimageArrow.setImageResource(R.drawable.icon_bigimage_down_arrow);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animator.start();
        this.isDescribeCommentBarHide = false;
    }

    private void performEnterAnimation(final View targetView) {
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator translateXAnimator = ValueAnimator.ofFloat(new float[]{targetView.getX(), 0.0f});
        translateXAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        translateXAnimator.setDuration(400);
        ValueAnimator translateYAnimator = ValueAnimator.ofFloat(new float[]{targetView.getY(), 0.0f});
        translateYAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        translateYAnimator.setDuration(400);
        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(new float[]{this.mScaleX, 1.0f});
        scaleXAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        scaleXAnimator.setDuration(400);
        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(new float[]{this.mScaleY, 1.0f});
        scaleYAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        scaleYAnimator.setDuration(400);
        animatorSet.play(translateXAnimator).with(translateYAnimator).with(scaleXAnimator).with(scaleYAnimator);
        animatorSet.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                targetView.setVisibility(0);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
        ObjectAnimator viewAnimatorAlpha = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).llParent, "alpha", new float[]{0.0f, 1.0f});
        viewAnimatorAlpha.setDuration(400);
        viewAnimatorAlpha.start();
    }

    private void performDefaultEnterAnimation(final View targetView) {
        AnimatorSet animatorSet = new AnimatorSet();
        ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(new float[]{0.5f, 1.0f});
        scaleXAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        scaleXAnimator.setDuration(400);
        ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(new float[]{0.5f, 1.0f});
        scaleYAnimator.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                targetView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        scaleYAnimator.setDuration(400);
        animatorSet.play(scaleXAnimator).with(scaleYAnimator);
        animatorSet.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                targetView.setVisibility(0);
            }

            public void onAnimationCancel(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }
        });
        animatorSet.start();
        ObjectAnimator viewAnimatorAlpha = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).llParent, "alpha", new float[]{0.0f, 1.0f});
        viewAnimatorAlpha.setDuration(400);
        viewAnimatorAlpha.start();
    }

    private void finishWithAnimation() {
        if (this.mScreenOrientation == 2) {
            finish();
            return;
        }
        EventBus.getDefault().post(new NineGridUnreigsterEvent());
        if (!(this.mLocationInfo == null || this.mIsAlreadyInitBackInfo)) {
            this.mOriginLeft = this.mLocationInfo.left;
            this.mOriginTop = this.mLocationInfo.top;
            this.mOriginWidth = this.mLocationInfo.width;
            this.mOriginHeight = this.mLocationInfo.height;
            int position = this.mLocationInfo.position;
            this.mOriginCenterX = this.mOriginLeft + (this.mOriginWidth / 2);
            this.mOriginCenterY = this.mOriginTop + (this.mOriginHeight / 2);
            if (this.currentImageItem.mimetype == 3) {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big_gif);
            } else if (FengUtil.isLongImage(this.currentImageItem)) {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.longImage);
            } else {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big);
            }
            int[] location = new int[2];
            this.mTargetView.getLocationOnScreen(location);
            this.mTargetWidth = (float) this.mTargetView.getWidth();
            this.mTargetHeight = (float) this.mTargetView.getHeight();
            float targetCenterY = ((float) location[1]) + (this.mTargetHeight / 2.0f);
            this.mTranslationX = ((float) this.mOriginCenterX) - (((float) location[0]) + (this.mTargetWidth / 2.0f));
            this.mTranslationY = ((float) this.mOriginCenterY) - targetCenterY;
            this.mTargetView.setTranslationX(this.mTranslationX);
            this.mTargetView.setTranslationY(this.mTranslationY);
            this.mTargetHeight = ((float) this.mOriginHeight) * ((((float) FengUtil.getScreenWidth(this)) * 1.0f) / ((float) this.mOriginWidth));
            this.mScaleX = ((float) this.mOriginWidth) / this.mTargetWidth;
            this.mScaleY = ((float) this.mOriginHeight) / this.mTargetHeight;
        }
        if (this.mTargetView != null) {
            final View targetView = this.mTargetView;
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator translateXAnimator = ValueAnimator.ofFloat(new float[]{0.0f, this.mTranslationX});
            translateXAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            translateXAnimator.setDuration(400);
            ValueAnimator translateYAnimator = ValueAnimator.ofFloat(new float[]{0.0f, this.mTranslationY});
            translateYAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            translateYAnimator.setDuration(400);
            ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(new float[]{1.0f, this.mScaleY});
            scaleYAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            scaleYAnimator.setDuration(400);
            ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(new float[]{1.0f, this.mScaleX});
            scaleXAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            scaleXAnimator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    animator.removeAllListeners();
                    ShowBigImageActivity.this.finish();
                    ShowBigImageActivity.this.overridePendingTransition(0, 0);
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            scaleXAnimator.setDuration(400);
            animatorSet.play(translateXAnimator).with(translateYAnimator).with(scaleXAnimator).with(scaleYAnimator);
            animatorSet.start();
            float[] fArr = new float[2];
            ObjectAnimator viewAnimatorAlpha = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).llParent, "alpha", new float[]{1.0f, 0.0f});
            viewAnimatorAlpha.setDuration(400);
            viewAnimatorAlpha.start();
            return;
        }
        finish();
    }

    private void finishWithDefaultAnimation() {
        if (this.mScreenOrientation == 2) {
            finish();
            return;
        }
        if (this.mLocationInfo != null) {
            int position = this.mLocationInfo.position;
            if (this.currentImageItem.mimetype == 3) {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big_gif);
            } else if (FengUtil.isLongImage(this.currentImageItem)) {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.longImage);
            } else {
                this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big);
            }
        }
        if (this.mTargetView != null) {
            final View targetView = this.mTargetView;
            AnimatorSet animatorSet = new AnimatorSet();
            ValueAnimator scaleYAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 0.7f});
            scaleYAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setScaleY(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            scaleYAnimator.setDuration(400);
            ValueAnimator scaleXAnimator = ValueAnimator.ofFloat(new float[]{1.0f, 0.7f});
            scaleXAnimator.addUpdateListener(new AnimatorUpdateListener() {
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    targetView.setScaleX(((Float) valueAnimator.getAnimatedValue()).floatValue());
                }
            });
            scaleXAnimator.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animator) {
                }

                public void onAnimationEnd(Animator animator) {
                    animator.removeAllListeners();
                    ShowBigImageActivity.this.finish();
                    ShowBigImageActivity.this.overridePendingTransition(0, 0);
                }

                public void onAnimationCancel(Animator animator) {
                }

                public void onAnimationRepeat(Animator animator) {
                }
            });
            scaleXAnimator.setDuration(400);
            animatorSet.play(scaleXAnimator).with(scaleYAnimator);
            animatorSet.start();
            ObjectAnimator viewAnimatorAlpha = ObjectAnimator.ofFloat(((ActivityShowBigimageBinding) this.mBaseBinding).llParent, "alpha", new float[]{1.0f, 0.0f});
            viewAnimatorAlpha.setDuration(400);
            viewAnimatorAlpha.start();
            return;
        }
        finish();
    }

    public void onBackPressed() {
        if (this.mEntranceType == ENTRANCE_TYPE_SINGLE_IMAGE || this.mEntranceType == 2001) {
            finishWithAnimation();
        } else if (this.mEntranceType == 2004) {
            nineImgaeExit();
        } else if (this.mEntranceType == ENTRANCE_TYPE_ARTICLE_IMAGE) {
            finishWithDefaultAnimation();
        } else {
            finish();
        }
    }

    private void nineImgaeExit() {
        this.mReceiveLocation = false;
        EventBus.getDefault().post(new RequestImageLocationEvent(this.mCurrentIndex, ((ImageInfo) this.mList.get(this.mCurrentIndex)).hash));
        if (!this.mReceiveLocation) {
            ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.postDelayed(new Runnable() {
                public void run() {
                    if (!ShowBigImageActivity.this.mReceiveLocation) {
                        ShowBigImageActivity.this.finish();
                    }
                }
            }, 300);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ImageLocationEvent event) {
        if (this.mList.size() <= event.info.position || !((ImageInfo) this.mList.get(event.info.position)).hash.equals(event.info.hash)) {
            finish();
            return;
        }
        this.mReceiveLocation = true;
        this.mLocationInfo = event.info;
        this.mOriginLeft = this.mLocationInfo.left;
        this.mOriginTop = this.mLocationInfo.top;
        this.mOriginWidth = this.mLocationInfo.width;
        this.mOriginHeight = this.mLocationInfo.height;
        int position = this.mLocationInfo.position;
        this.mOriginCenterX = this.mOriginLeft + (this.mOriginWidth / 2);
        this.mOriginCenterY = this.mOriginTop + (this.mOriginHeight / 2);
        if (this.currentImageItem.mimetype == 3) {
            this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big_gif);
        } else if (FengUtil.isLongImage(this.currentImageItem)) {
            this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.longImage);
        } else {
            this.mTargetView = this.adapter.getPrimaryItem().findViewById(R.id.image_big);
        }
        int[] location = new int[2];
        this.mTargetView.getLocationOnScreen(location);
        this.mTargetWidth = (float) this.mTargetView.getWidth();
        this.mTargetHeight = (float) this.mTargetView.getHeight();
        float targetCenterY = ((float) location[1]) + (this.mTargetHeight / 2.0f);
        this.mTranslationX = ((float) this.mOriginCenterX) - (((float) location[0]) + (this.mTargetWidth / 2.0f));
        this.mTranslationY = ((float) this.mOriginCenterY) - targetCenterY;
        this.mTargetView.setTranslationX(this.mTranslationX);
        this.mTargetView.setTranslationY(this.mTranslationY);
        this.mTargetHeight = ((float) this.mOriginHeight) * ((((float) FengUtil.getScreenWidth(this)) * 1.0f) / ((float) this.mOriginWidth));
        this.mScaleX = ((float) this.mOriginWidth) / this.mTargetWidth;
        this.mScaleY = ((float) this.mOriginHeight) / this.mTargetHeight;
        this.mIsAlreadyInitBackInfo = true;
        finishWithAnimation();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogGatherReadUtil.getInstance().setScreenOrientation(this);
        if (newConfig.orientation == 2) {
            this.mIsPortrait = false;
            this.mScreenWidth = FengUtil.getScreenWidth(this);
            this.mScreenHight = FengUtil.getScreenHeight(this);
            this.mScreenOrientation = 2;
            resetProgressDrawableOrientation();
            ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mCurrentIndex);
            this.adapter.notifyDataSetChanged();
            hideAllFunctions();
        } else if (newConfig.orientation == 1) {
            this.mIsPortrait = true;
            this.mScreenWidth = FengUtil.getScreenWidth(this);
            this.mScreenHight = FengUtil.getScreenHeight(this);
            this.mScreenOrientation = 1;
            resetProgressDrawableOrientation();
            showAllFunctions();
            ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mCurrentIndex);
            this.adapter.notifyDataSetChanged();
        }
    }

    private void resetProgressDrawableOrientation() {
        ((BigImageLoadProgressView) this.adapter.getPrimaryItem().findViewById(R.id.iv_bigimage_progress_view)).setScreenOrientation(this, this.mScreenOrientation);
        if (this.mCurrentIndex + 1 < this.adapter.getPList().size() && ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.getChildAt(2) != null) {
            ((BigImageLoadProgressView) ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.getChildAt(2).findViewById(R.id.iv_bigimage_progress_view)).setScreenOrientation(this, this.mScreenOrientation);
        }
        if (this.mCurrentIndex > 0 && ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.getChildAt(0) != null) {
            ((BigImageLoadProgressView) ((ActivityShowBigimageBinding) this.mBaseBinding).viewpager.getChildAt(0).findViewById(R.id.iv_bigimage_progress_view)).setScreenOrientation(this, this.mScreenOrientation);
        }
    }

    public void checkBasePermission() {
        try {
            if (ContextCompat.checkSelfPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
                ActivityCompat.requestPermissions(ActivityManager.getInstance().getCurrentActivity(), new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50000);
                return;
            }
            permissionSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void permissionSuccess() {
        ImageInfo image = ((ActivityShowBigimageBinding) this.mBaseBinding).getImageInfo();
        if (image != null) {
            String imageUrl;
            String original_url;
            FileBinaryResource resource;
            if (isAddImageWaterMark()) {
                imageUrl = FengUtil.getUniformScaleUrl(image, 640) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
                original_url = FengUtil.getUniformScaleUrl(image, 1600) + "|watermark/1/image/aHR0cDovLzd4c2N3NS5jb20wLnowLmdsYi5jbG91ZGRuLmNvbS9sYW9zam1rLnBuZw==/gravity/SouthEast";
            } else {
                imageUrl = FengUtil.getUniformScaleUrl(image, 640);
                original_url = FengUtil.getUniformScaleUrl(image, 1600);
            }
            if (image.mimetype == 3) {
                resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
            } else if (image.width < 640) {
                if (((Boolean) ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.getTag()).booleanValue()) {
                    resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(original_url));
                } else {
                    resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
                }
            } else if (((Boolean) ((ActivityShowBigimageBinding) this.mBaseBinding).ivBigimageHd.getTag()).booleanValue()) {
                resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(original_url));
            } else {
                resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
            }
            if (resource != null) {
                String fileName;
                File file = resource.getFile();
                String fileNameWithoutSuffix = FengUtil.getFileNameWithoutSuffix(file.getName());
                if (!FengUtil.getAppDir().exists()) {
                    FengUtil.getAppDir().mkdirs();
                }
                if (image.mimetype == 3) {
                    fileName = fileNameWithoutSuffix + ".gif";
                } else {
                    fileName = fileNameWithoutSuffix + ".jpg";
                }
                File checkFile = new File(FengUtil.getAppDir(), fileName);
                if (checkFile == null || checkFile.exists()) {
                    showFirstTypeToast((int) R.string.save_success);
                } else {
                    savingImage(image, file, fileNameWithoutSuffix);
                }
            }
        }
    }
}
