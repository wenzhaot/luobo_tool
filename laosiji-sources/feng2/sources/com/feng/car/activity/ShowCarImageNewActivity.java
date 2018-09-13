package com.feng.car.activity;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityShowCarimageBinding;
import com.feng.car.databinding.ItemShowCarImageViewBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.car.CarImageInfo;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LogGatherReadUtil;
import com.feng.car.view.BigImageLoadProgressView;
import com.feng.car.view.BigimageLoadProgressDrawable;
import com.feng.car.view.BigimageLoadProgressDrawable$OnValueChangedListener;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.FixedViewPager;
import com.feng.car.view.photoview.OnViewTapListener;
import com.feng.car.view.photoview.PhotoDraweeView;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ShowCarImageNewActivity extends BaseActivity<ActivityShowCarimageBinding> {
    private final int REQUEST_COUNT_NUM = 20;
    private final int REQUEST_OFFSET = 10;
    private MyAdapter mAdapter;
    private Map<Integer, CarImageInfo> mAllImageMap = new HashMap();
    private int mBottomHeight = 0;
    private int mCarModelID;
    private int mCarSeriesID;
    private int mFirstCount = 0;
    private int mFourCount = 0;
    private int mImageQuality = -1;
    private boolean mIsAnimIng = false;
    private boolean mIsFirst = true;
    private boolean mIsLoading = false;
    private boolean mIsOpen = true;
    private boolean mIsWifiConnect = false;
    private int mLocalPosition;
    private int mScreenOrientation = -1;
    private int mSecondCount = 0;
    private int mThirdCount = 0;
    private int mTopBarHeight = 0;
    private int mType;

    private class MyAdapter extends PagerAdapter {
        private View currentView;

        private MyAdapter() {
        }

        /* synthetic */ MyAdapter(ShowCarImageNewActivity x0, AnonymousClass1 x1) {
            this();
        }

        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            this.currentView = (View) object;
        }

        View getPrimaryItem() {
            return this.currentView;
        }

        public Object instantiateItem(ViewGroup container, int position) {
            ItemShowCarImageViewBinding mBinding = ItemShowCarImageViewBinding.inflate(ShowCarImageNewActivity.this.mInflater);
            ViewHolder holder = new ViewHolder(mBinding);
            CarImageInfo info = (CarImageInfo) ShowCarImageNewActivity.this.mAllImageMap.get(Integer.valueOf(position + 1));
            if (info != null) {
                holder.bindTo(info);
                ShowCarImageNewActivity.this.showImage(holder.mBinding.imageBig, holder.mBinding.ivBigimageProgressView, info, false);
            }
            container.addView(holder.mBinding.getRoot());
            return mBinding.getRoot();
        }

        public int getItemPosition(Object object) {
            return -2;
        }

        public int getCount() {
            return ((ShowCarImageNewActivity.this.mFirstCount + ShowCarImageNewActivity.this.mSecondCount) + ShowCarImageNewActivity.this.mThirdCount) + ShowCarImageNewActivity.this.mFourCount;
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public class ViewHolder {
        private ItemShowCarImageViewBinding mBinding;

        public ViewHolder(ItemShowCarImageViewBinding mAdapterBinding) {
            this.mBinding = mAdapterBinding;
        }

        void bindTo(CarImageInfo carImageInfo) {
            this.mBinding.setImageInfo(carImageInfo.image);
            this.mBinding.executePendingBindings();
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_show_carimage;
    }

    public void initView() {
        hideDefaultTitleBar();
        closeSwip();
        this.mImageQuality = SharedUtil.getInt(this, "image_quality", 0);
        this.mIsWifiConnect = FengUtil.isWifiConnectivity(this);
        this.mScreenOrientation = this.mResources.getConfiguration().orientation;
        Intent intent = getIntent();
        this.mLocalPosition = intent.getIntExtra("position", 0);
        this.mType = intent.getIntExtra("car_photo_type", 1);
        this.mCarSeriesID = intent.getIntExtra("carsid", 0);
        this.mCarModelID = intent.getIntExtra("carxid", 0);
        String strAllCount = intent.getStringExtra("ALL_CAR_IMAGE_CUNT");
        if (TextUtils.isEmpty(strAllCount)) {
            showSecondTypeToast((int) R.string.net_abnormal);
            finish();
        } else {
            String[] strCounts = strAllCount.split(",");
            if (strCounts.length == 4) {
                this.mFirstCount = Integer.parseInt(strCounts[0]);
                this.mSecondCount = Integer.parseInt(strCounts[1]);
                this.mThirdCount = Integer.parseInt(strCounts[2]);
                this.mFourCount = Integer.parseInt(strCounts[3]);
            } else {
                showSecondTypeToast((int) R.string.net_abnormal);
                finish();
            }
        }
        changeView(false);
        showLaoSiJiDialog();
        initListener();
        getData(this.mLocalPosition);
    }

    private void changeView(boolean isShow) {
        int i;
        int i2 = 0;
        FixedViewPager fixedViewPager = ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager;
        if (isShow) {
            i = 0;
        } else {
            i = 8;
        }
        fixedViewPager.setVisibility(i);
        LinearLayout linearLayout = ((ActivityShowCarimageBinding) this.mBaseBinding).llTab;
        if (isShow) {
            i = 0;
        } else {
            i = 8;
        }
        linearLayout.setVisibility(i);
        RelativeLayout relativeLayout = ((ActivityShowCarimageBinding) this.mBaseBinding).llBottom;
        if (!isShow) {
            i2 = 8;
        }
        relativeLayout.setVisibility(i2);
    }

    private void initAdapter() {
        if (this.mAdapter == null) {
            this.mAdapter = new MyAdapter(this, null);
            ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setPageMargin(getResources().getDimensionPixelSize(R.dimen.default_40PX));
            ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setAdapter(this.mAdapter);
            ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setCurrentItem(getRealPosition(this.mLocalPosition - 1));
            ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.addOnPageChangeListener(new OnPageChangeListener() {
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                public void onPageSelected(int position) {
                    position++;
                    if (position <= ShowCarImageNewActivity.this.mFirstCount) {
                        ShowCarImageNewActivity.this.mType = 1;
                        ShowCarImageNewActivity.this.mLocalPosition = position;
                    } else if (position <= ShowCarImageNewActivity.this.mFirstCount + ShowCarImageNewActivity.this.mSecondCount) {
                        ShowCarImageNewActivity.this.mType = 2;
                        ShowCarImageNewActivity.this.mLocalPosition = position - ShowCarImageNewActivity.this.mFirstCount;
                    } else if (position <= (ShowCarImageNewActivity.this.mFirstCount + ShowCarImageNewActivity.this.mSecondCount) + ShowCarImageNewActivity.this.mThirdCount) {
                        ShowCarImageNewActivity.this.mType = 3;
                        ShowCarImageNewActivity.this.mLocalPosition = (position - ShowCarImageNewActivity.this.mFirstCount) - ShowCarImageNewActivity.this.mSecondCount;
                    } else if (position <= ((ShowCarImageNewActivity.this.mFirstCount + ShowCarImageNewActivity.this.mSecondCount) + ShowCarImageNewActivity.this.mThirdCount) + ShowCarImageNewActivity.this.mFourCount) {
                        ShowCarImageNewActivity.this.mType = 4;
                        ShowCarImageNewActivity.this.mLocalPosition = ((position - ShowCarImageNewActivity.this.mFirstCount) - ShowCarImageNewActivity.this.mSecondCount) - ShowCarImageNewActivity.this.mThirdCount;
                    }
                    ShowCarImageNewActivity.this.requestData();
                    ShowCarImageNewActivity.this.changeTob();
                    ShowCarImageNewActivity.this.setControllerBar(ShowCarImageNewActivity.this.mLocalPosition);
                }

                public void onPageScrollStateChanged(int state) {
                }
            });
            changeTob();
            setControllerBar(this.mLocalPosition);
            if (this.mScreenOrientation == 2) {
                changeLandscape();
                return;
            }
            return;
        }
        setControllerBar(this.mLocalPosition);
        this.mAdapter.notifyDataSetChanged();
    }

    private void requestData() {
        if (!this.mIsFirst && !this.mIsLoading) {
            if (this.mAllImageMap.containsKey(Integer.valueOf(getRealPosition(this.mLocalPosition)))) {
                int i = 1;
                while (i <= 10) {
                    if (getRealPosition(this.mLocalPosition + i) > ((this.mFourCount + this.mFirstCount) + this.mThirdCount) + this.mSecondCount) {
                        return;
                    }
                    if (this.mAllImageMap.containsKey(Integer.valueOf(getRealPosition(this.mLocalPosition + i)))) {
                        i++;
                    } else {
                        getData(this.mLocalPosition + i);
                        return;
                    }
                }
                i = 1;
                while (i <= 10 && getRealPosition(this.mLocalPosition - i) != 0) {
                    if (this.mAllImageMap.containsKey(Integer.valueOf(getRealPosition(this.mLocalPosition - i)))) {
                        i++;
                    } else {
                        getData(this.mLocalPosition - i);
                        return;
                    }
                }
                return;
            }
            getData(this.mLocalPosition);
        }
    }

    private void changeTob() {
        int i;
        int i2 = 0;
        ImageView imageView = ((ActivityShowCarimageBinding) this.mBaseBinding).ivFirstBottomLine;
        if (this.mType == 1) {
            i = 0;
        } else {
            i = 4;
        }
        imageView.setVisibility(i);
        imageView = ((ActivityShowCarimageBinding) this.mBaseBinding).ivSecondBottomLine;
        if (this.mType == 2) {
            i = 0;
        } else {
            i = 4;
        }
        imageView.setVisibility(i);
        imageView = ((ActivityShowCarimageBinding) this.mBaseBinding).ivThirdBottomLine;
        if (this.mType == 3) {
            i = 0;
        } else {
            i = 4;
        }
        imageView.setVisibility(i);
        ImageView imageView2 = ((ActivityShowCarimageBinding) this.mBaseBinding).ivFourBottomLine;
        if (this.mType != 4) {
            i2 = 4;
        }
        imageView2.setVisibility(i2);
    }

    private void getData(final int position) {
        Map<String, Object> map = new HashMap();
        map.put("position", String.valueOf(position));
        map.put("count", String.valueOf(20));
        map.put("carxid", String.valueOf(this.mCarModelID));
        map.put("carsid", String.valueOf(this.mCarSeriesID));
        map.put("type", String.valueOf(this.mType));
        this.mIsLoading = true;
        FengApplication.getInstance().httpRequest("car/imagemoreltandgtbyposition/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                if (ShowCarImageNewActivity.this.mIsFirst) {
                    ShowCarImageNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    ShowCarImageNewActivity.this.hideProgress();
                }
                ShowCarImageNewActivity.this.mIsLoading = false;
            }

            public void onStart() {
            }

            public void onFinish() {
                ShowCarImageNewActivity.this.mIsLoading = false;
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                if (ShowCarImageNewActivity.this.mIsFirst) {
                    ShowCarImageNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    ShowCarImageNewActivity.this.hideProgress();
                }
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        JSONArray jsonArray;
                        int size;
                        int i;
                        int pos;
                        CarImageInfo carImageInfo;
                        JSONObject jsonBrand = jsonResult.getJSONObject("body").getJSONObject("image");
                        int pos2 = position - 1;
                        if (jsonBrand.has("ltlist")) {
                            jsonArray = jsonBrand.getJSONArray("ltlist");
                            size = jsonArray.length();
                            i = 0;
                            pos = pos2;
                            while (i < size) {
                                carImageInfo = new CarImageInfo();
                                carImageInfo.parser(jsonArray.getJSONObject(i));
                                carImageInfo.position.set(pos);
                                pos2 = pos - 1;
                                ShowCarImageNewActivity.this.mAllImageMap.put(Integer.valueOf(ShowCarImageNewActivity.this.getRealPosition(pos)), carImageInfo);
                                i++;
                                pos = pos2;
                            }
                            pos2 = pos;
                        }
                        if (jsonBrand.has("gtlist")) {
                            jsonArray = jsonBrand.getJSONArray("gtlist");
                            pos2 = position + 1;
                            size = jsonArray.length();
                            i = 0;
                            pos = pos2;
                            while (i < size) {
                                carImageInfo = new CarImageInfo();
                                carImageInfo.parser(jsonArray.getJSONObject(i));
                                carImageInfo.position.set(pos);
                                pos2 = pos + 1;
                                ShowCarImageNewActivity.this.mAllImageMap.put(Integer.valueOf(ShowCarImageNewActivity.this.getRealPosition(pos)), carImageInfo);
                                i++;
                                pos = pos2;
                            }
                            pos2 = pos;
                        }
                        if (jsonBrand.has("thislist")) {
                            jsonArray = jsonBrand.getJSONArray("thislist");
                            if (jsonArray.length() == 1) {
                                carImageInfo = new CarImageInfo();
                                carImageInfo.parser(jsonArray.getJSONObject(0));
                                carImageInfo.position.set(position);
                                ShowCarImageNewActivity.this.mAllImageMap.put(Integer.valueOf(ShowCarImageNewActivity.this.getRealPosition(position)), carImageInfo);
                            }
                        }
                        ShowCarImageNewActivity.this.initAdapter();
                        if (ShowCarImageNewActivity.this.mIsFirst) {
                            ShowCarImageNewActivity.this.hideProgress();
                            ShowCarImageNewActivity.this.changeView(true);
                        }
                        ShowCarImageNewActivity.this.mIsFirst = false;
                    } else if (ShowCarImageNewActivity.this.mIsFirst) {
                        ShowCarImageNewActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                        ShowCarImageNewActivity.this.hideProgress();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initListener() {
        ((ActivityShowCarimageBinding) this.mBaseBinding).ibClose.setOnClickListener(this);
        ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setOnClickListener(this);
        if (this.mFirstCount > 0) {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeFirst.setOnClickListener(this);
        } else {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeFirst.setEnabled(false);
            ((ActivityShowCarimageBinding) this.mBaseBinding).tvFirstTab.setTextColor(ContextCompat.getColor(this, R.color.color_50_ffffff));
        }
        if (this.mSecondCount > 0) {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeSecond.setOnClickListener(this);
        } else {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeSecond.setEnabled(false);
            ((ActivityShowCarimageBinding) this.mBaseBinding).tvSecondTab.setTextColor(ContextCompat.getColor(this, R.color.color_50_ffffff));
        }
        if (this.mThirdCount > 0) {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeThird.setOnClickListener(this);
        } else {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeThird.setEnabled(false);
            ((ActivityShowCarimageBinding) this.mBaseBinding).tvThirdTab.setTextColor(ContextCompat.getColor(this, R.color.color_50_ffffff));
        }
        if (this.mFourCount > 0) {
            ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeFour.setOnClickListener(this);
            return;
        }
        ((ActivityShowCarimageBinding) this.mBaseBinding).llTypeFour.setEnabled(false);
        ((ActivityShowCarimageBinding) this.mBaseBinding).tvFourTab.setTextColor(ContextCompat.getColor(this, R.color.color_50_ffffff));
    }

    private int getRealPosition(int position) {
        if (this.mType == 1) {
            return position;
        }
        if (this.mType == 2) {
            return position + this.mFirstCount;
        }
        if (this.mType == 3) {
            return (this.mFirstCount + position) + this.mSecondCount;
        }
        return ((this.mFirstCount + position) + this.mSecondCount) + this.mThirdCount;
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.ib_close /*2131624664*/:
                finish();
                return;
            case R.id.ll_type_first /*2131624666*/:
                if (this.mType != 1) {
                    this.mType = 1;
                    if (this.mAdapter != null) {
                        ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setCurrentItem(0);
                        return;
                    }
                    return;
                }
                return;
            case R.id.ll_type_second /*2131624669*/:
                if (this.mType != 2) {
                    this.mType = 2;
                    if (this.mAdapter != null) {
                        ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mFirstCount);
                        return;
                    }
                    return;
                }
                return;
            case R.id.ll_type_third /*2131624672*/:
                if (this.mType != 3) {
                    this.mType = 3;
                    if (this.mAdapter != null) {
                        ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mFirstCount + this.mSecondCount);
                        return;
                    }
                    return;
                }
                return;
            case R.id.ll_type_four /*2131624675*/:
                if (this.mType != 4) {
                    this.mType = 4;
                    if (this.mAdapter != null) {
                        ((ActivityShowCarimageBinding) this.mBaseBinding).viewpager.setCurrentItem((this.mFirstCount + this.mSecondCount) + this.mThirdCount);
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    private void setControllerBar(int currentIndex) {
        if (this.mAllImageMap.size() != 0) {
            if (this.mType == 1) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvBigimagePageText.setText(currentIndex + "/" + this.mFirstCount);
            } else if (this.mType == 2) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvBigimagePageText.setText(currentIndex + "/" + this.mSecondCount);
            } else if (this.mType == 3) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvBigimagePageText.setText(currentIndex + "/" + this.mThirdCount);
            } else if (this.mType == 4) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvBigimagePageText.setText(currentIndex + "/" + this.mFourCount);
            }
            final CarImageInfo carImageInfo = (CarImageInfo) this.mAllImageMap.get(Integer.valueOf(getRealPosition(currentIndex)));
            if (carImageInfo == null) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setVisibility(4);
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setVisibility(4);
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageSave.setVisibility(4);
                return;
            }
            String imageUrl;
            String originalUrl;
            ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setVisibility(0);
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setVisibility(0);
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageSave.setVisibility(0);
            ((ActivityShowCarimageBinding) this.mBaseBinding).setCarImageInfo(carImageInfo);
            if (carImageInfo.imagelist.p640.width > 0) {
                imageUrl = carImageInfo.imagelist.p640.url;
            } else {
                imageUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
            }
            if (carImageInfo.imagelist.p1920.width > 0) {
                originalUrl = carImageInfo.imagelist.p1920.url;
            } else {
                originalUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE);
            }
            if (FengApplication.getInstance().containsHd(originalUrl)) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setEnabled(false);
            } else {
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.bigimage_hd_button_selector);
                ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setEnabled(true);
            }
            if (TextUtils.isEmpty(carImageInfo.dealer4sname)) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).llVehicleImage4sInfoContainer.setVisibility(8);
                if (TextUtils.isEmpty(carImageInfo.fromdescn)) {
                    ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImageSourceText.setVisibility(8);
                } else {
                    ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImageSourceText.setVisibility(0);
                    ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImageSourceText.setText(carImageInfo.fromdescn);
                }
            } else {
                ((ActivityShowCarimageBinding) this.mBaseBinding).llVehicleImage4sInfoContainer.setVisibility(0);
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImageSourceText.setVisibility(8);
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImage4sName.setText(this.mResources.getString(R.string.vehicle_4s_name_tips, new Object[]{carImageInfo.dealer4sname}));
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImage4sTel.setText(this.mResources.getString(R.string.vehicle_4s_tel_tips, new Object[]{carImageInfo.dealer4stel}));
            }
            if (TextUtils.isEmpty(carImageInfo.carxname)) {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvCarModelName.setVisibility(8);
            } else {
                ((ActivityShowCarimageBinding) this.mBaseBinding).tvCarModelName.setVisibility(0);
            }
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ShowCarImageNewActivity.this.showImage((PhotoDraweeView) ShowCarImageNewActivity.this.mAdapter.getPrimaryItem().findViewById(R.id.image_big), (BigImageLoadProgressView) ShowCarImageNewActivity.this.mAdapter.getPrimaryItem().findViewById(R.id.iv_bigimage_progress_view), carImageInfo, true);
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).ivBigimageHd.setEnabled(false);
                }
            });
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageSave.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View view) {
                    if (VERSION.SDK_INT < 23 || ShowCarImageNewActivity.this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
                        boolean isHdShowTag;
                        FileBinaryResource resource;
                        if (FengApplication.getInstance().containsHd(originalUrl)) {
                            isHdShowTag = true;
                        } else {
                            isHdShowTag = false;
                        }
                        if (ShowCarImageNewActivity.this.mImageQuality == 1) {
                            isHdShowTag = true;
                        } else if (ShowCarImageNewActivity.this.mImageQuality != 2 && ShowCarImageNewActivity.this.mImageQuality == 0 && ShowCarImageNewActivity.this.mIsWifiConnect) {
                            isHdShowTag = true;
                        }
                        if (isHdShowTag) {
                            resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(originalUrl));
                        } else {
                            resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
                        }
                        if (resource != null) {
                            File file = resource.getFile();
                            String fileNameWithoutSuffix = FengUtil.getFileNameWithoutSuffix(file.getName());
                            if (!FengUtil.getAppDir().exists()) {
                                FengUtil.getAppDir().mkdirs();
                            }
                            if (new File(FengUtil.getAppDir(), fileNameWithoutSuffix + ".jpg").exists()) {
                                ShowCarImageNewActivity.this.showFirstTypeToast((int) R.string.save_success);
                                return;
                            } else if (isHdShowTag) {
                                if (carImageInfo.imagelist.p1920.width > 0) {
                                    ShowCarImageNewActivity.this.savingImage(carImageInfo.imagelist.p1920, file, fileNameWithoutSuffix);
                                    return;
                                } else {
                                    ShowCarImageNewActivity.this.savingImage(carImageInfo.image, file, fileNameWithoutSuffix);
                                    return;
                                }
                            } else if (carImageInfo.imagelist.p640.width > 0) {
                                ShowCarImageNewActivity.this.savingImage(carImageInfo.imagelist.p640, file, fileNameWithoutSuffix);
                                return;
                            } else {
                                ShowCarImageNewActivity.this.savingImage(carImageInfo.image, file, fileNameWithoutSuffix);
                                return;
                            }
                        }
                        return;
                    }
                    ShowCarImageNewActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
                }
            });
            ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImageSourceText.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    FengApplication.getInstance().handlerUrlSkip(ShowCarImageNewActivity.this, carImageInfo.fromurl, "", false, null);
                }
            });
            ((ActivityShowCarimageBinding) this.mBaseBinding).tvVehicleImage4sTel.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    ShowCarImageNewActivity.this.checkCallTelPermission(carImageInfo.dealer4stel);
                }
            });
        }
    }

    private void showImage(final PhotoDraweeView photoDraweeView, final BigImageLoadProgressView progressView, CarImageInfo carImageInfo, boolean isOriginalImage) {
        String imageLowUrl;
        String imageUrl;
        String originalUrl;
        GenericDraweeHierarchy hierarchy = (GenericDraweeHierarchy) photoDraweeView.getHierarchy();
        hierarchy.setProgressBarImage(new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
            public void valueChanged(Rect bounds, int level) {
                progressView.setValue(bounds, level);
            }
        }));
        progressView.setScreenOrientation(this.mScreenOrientation);
        progressView.setVisibility(0);
        photoDraweeView.setHierarchy(hierarchy);
        if (carImageInfo.imagelist.p240.width > 0) {
            imageLowUrl = carImageInfo.imagelist.p240.url;
        } else {
            imageLowUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, 2001);
        }
        if (carImageInfo.imagelist.p640.width > 0) {
            imageUrl = carImageInfo.imagelist.p640.url;
        } else {
            imageUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
        }
        if (carImageInfo.imagelist.p1920.width > 0) {
            originalUrl = carImageInfo.imagelist.p1920.url;
        } else {
            originalUrl = FengUtil.getCarImageSizeUrl(carImageInfo.image, ShowBigImageActivity.ENTRANCE_TYPE_ARTICLE_IMAGE);
        }
        if (this.mImageQuality == 1) {
            isOriginalImage = true;
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
        } else if (this.mImageQuality != 2 && this.mImageQuality == 0 && this.mIsWifiConnect) {
            isOriginalImage = true;
            ((ActivityShowCarimageBinding) this.mBaseBinding).ivBigimageHd.setImageResource(R.drawable.icon_bigimage_hd_select);
        }
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        if (isOriginalImage) {
            controller.setLowResImageRequest(ImageRequest.fromUri(imageLowUrl));
            controller.setImageRequest(ImageRequest.fromUri(originalUrl));
            FengApplication.getInstance().addHdImage(originalUrl);
        } else if (FengApplication.getInstance().containsHd(originalUrl)) {
            controller.setLowResImageRequest(ImageRequest.fromUri(imageLowUrl));
            controller.setUri(Uri.parse(originalUrl));
        } else {
            controller.setLowResImageRequest(ImageRequest.fromUri(imageLowUrl));
            controller.setUri(Uri.parse(imageUrl));
        }
        photoDraweeView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                ShowCarImageNewActivity.this.showMenuDialog();
                return false;
            }
        });
        controller.setOldController(photoDraweeView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            public void onFinalImageSet(String id, final ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo != null) {
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                Thread.sleep(200);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            ShowCarImageNewActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    float scale = (((float) FengUtil.getScreenWidth(ShowCarImageNewActivity.this)) * 1.0f) / ((float) imageInfo.getWidth());
                                    photoDraweeView.update((int) (((float) imageInfo.getWidth()) * scale), (int) (((float) imageInfo.getHeight()) * scale));
                                    photoDraweeView.setMaximumScale(3.0f);
                                    progressView.setVisibility(8);
                                    progressView.setValue(null, 0);
                                }
                            });
                        }
                    }).start();
                }
            }

            public void onFailure(String id, Throwable throwable) {
                super.onFailure(id, throwable);
                progressView.setVisibility(8);
                progressView.setValue(null, 0);
            }
        });
        photoDraweeView.setController(controller.build());
        photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
            public void onViewTap(View view, float x, float y) {
                ShowCarImageNewActivity.this.changBarState();
            }
        });
    }

    private void savingImage(com.feng.car.entity.ImageInfo image, File file, String fileNameWithoutSuffix) {
        if (image == null || file == null) {
            showSecondTypeToast((int) R.string.get_image_failed);
            return;
        }
        try {
            FengUtil.saveImageToGallery(this, image, file, fileNameWithoutSuffix);
            showFirstTypeToast((int) R.string.save_success);
        } catch (Exception e) {
            e.printStackTrace();
            showSecondTypeToast((int) R.string.save_failed);
        }
    }

    private void showMenuDialog() {
        DialogItemEntity saveItem = new DialogItemEntity(getString(R.string.save_picture), false);
        List<DialogItemEntity> list = new ArrayList();
        list.add(saveItem);
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                switch (position) {
                    case 0:
                        ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).ivBigimageSave.callOnClick();
                        return;
                    default:
                        return;
                }
            }
        });
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (!permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") || grantResults[0] != 0) {
                    showSecondTypeToast((int) R.string.authorization_failed_cannot_save_image);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode != 50002) {
        } else {
            if (permissions[0].equals("android.permission.CALL_PHONE") && grantResults[0] == 0) {
                Intent intentTo = new Intent("android.intent.action.CALL", Uri.parse("tel:" + ((CarImageInfo) this.mAllImageMap.get(Integer.valueOf(getRealPosition(this.mLocalPosition)))).dealer4stel));
                if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
                    showSecondTypeToast((int) R.string.authorization_failed_cannot_make_phonecall);
                    return;
                } else {
                    startActivity(intentTo);
                    return;
                }
            }
            showSecondTypeToast((int) R.string.authorization_failed_cannot_make_phonecall);
        }
    }

    private void checkCallTelPermission(String dealer4stel) {
        if (VERSION.SDK_INT < 23) {
            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + dealer4stel)));
        } else if (ContextCompat.checkSelfPermission(this, "android.permission.CALL_PHONE") != 0) {
            requestPermissions(new String[]{"android.permission.CALL_PHONE"}, 50002);
        } else {
            startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:" + dealer4stel)));
        }
    }

    private void changBarState() {
        if (!this.mIsAnimIng) {
            if (this.mIsOpen) {
                hideTopBottomView();
            } else {
                showTopBottomView();
            }
        }
    }

    private void showTopBottomView() {
        if (!this.mIsAnimIng) {
            if (this.mTopBarHeight == 0 || this.mBottomHeight == 0) {
                this.mTopBarHeight = ((ActivityShowCarimageBinding) this.mBaseBinding).rlTopBar.getMeasuredHeight();
                this.mBottomHeight = ((ActivityShowCarimageBinding) this.mBaseBinding).llBottom.getMeasuredHeight();
            }
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(((ActivityShowCarimageBinding) this.mBaseBinding).rlTopBar, "translationY", new float[]{(float) (-this.mTopBarHeight), 0.0f});
            animator1.setDuration(200);
            animator1.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                    ShowCarImageNewActivity.this.mIsAnimIng = true;
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).rlTopBar.setVisibility(0);
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).llBottom.setVisibility(0);
                }

                public void onAnimationEnd(Animator animation) {
                    ShowCarImageNewActivity.this.mIsAnimIng = false;
                    ShowCarImageNewActivity.this.mIsOpen = true;
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator1.start();
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(((ActivityShowCarimageBinding) this.mBaseBinding).llBottom, "translationY", new float[]{(float) this.mBottomHeight, 0.0f});
            animator2.setDuration(200);
            animator2.start();
        }
    }

    private void hideTopBottomView() {
        if (!this.mIsAnimIng) {
            if (this.mTopBarHeight == 0 || this.mBottomHeight == 0) {
                this.mTopBarHeight = ((ActivityShowCarimageBinding) this.mBaseBinding).rlTopBar.getMeasuredHeight();
                this.mBottomHeight = ((ActivityShowCarimageBinding) this.mBaseBinding).llBottom.getMeasuredHeight();
            }
            ObjectAnimator animator1 = ObjectAnimator.ofFloat(((ActivityShowCarimageBinding) this.mBaseBinding).rlTopBar, "translationY", new float[]{0.0f, (float) (-this.mTopBarHeight)});
            animator1.setDuration(200);
            animator1.addListener(new AnimatorListener() {
                public void onAnimationStart(Animator animation) {
                    ShowCarImageNewActivity.this.mIsAnimIng = true;
                }

                public void onAnimationEnd(Animator animation) {
                    ShowCarImageNewActivity.this.mIsAnimIng = false;
                    ShowCarImageNewActivity.this.mIsOpen = false;
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).rlTopBar.setVisibility(8);
                    ((ActivityShowCarimageBinding) ShowCarImageNewActivity.this.mBaseBinding).llBottom.setVisibility(8);
                }

                public void onAnimationCancel(Animator animation) {
                }

                public void onAnimationRepeat(Animator animation) {
                }
            });
            animator1.start();
            ObjectAnimator animator2 = ObjectAnimator.ofFloat(((ActivityShowCarimageBinding) this.mBaseBinding).llBottom, "translationY", new float[]{0.0f, (float) this.mBottomHeight});
            animator2.setDuration(200);
            animator2.start();
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogGatherReadUtil.getInstance().setScreenOrientation(this);
        if (newConfig.orientation == 2) {
            this.mScreenOrientation = 2;
            changeLandscape();
        } else if (newConfig.orientation == 1) {
            this.mScreenOrientation = 1;
            changePortrait();
        }
    }

    private void changeLandscape() {
        LayoutParams paramsContent = (LayoutParams) ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.getLayoutParams();
        paramsContent.width = getResources().getDimensionPixelSize(R.dimen.default_600PX);
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setPadding(0, 0, 0, getResources().getDimensionPixelSize(R.dimen.default_20PX));
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.default_108PX));
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setLayoutParams(paramsContent);
        LayoutParams params = (LayoutParams) ((ActivityShowCarimageBinding) this.mBaseBinding).rlControllerContainer.getLayoutParams();
        params.addRule(3, 0);
        params.addRule(11);
        params.addRule(1, R.id.ll_content);
        params.addRule(8, R.id.ll_content);
        ((ActivityShowCarimageBinding) this.mBaseBinding).rlControllerContainer.setLayoutParams(params);
    }

    private void changePortrait() {
        LayoutParams paramsContent = (LayoutParams) ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.getLayoutParams();
        paramsContent.width = -1;
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setPadding(0, 0, 0, 0);
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setMinimumHeight(0);
        ((ActivityShowCarimageBinding) this.mBaseBinding).llContent.setLayoutParams(paramsContent);
        LayoutParams params = (LayoutParams) ((ActivityShowCarimageBinding) this.mBaseBinding).rlControllerContainer.getLayoutParams();
        params.width = -1;
        params.addRule(3, R.id.ll_content);
        params.addRule(8, 0);
        params.addRule(1, 0);
        ((ActivityShowCarimageBinding) this.mBaseBinding).rlControllerContainer.setLayoutParams(params);
    }

    public void finish() {
        super.finish();
        Fresco.getImagePipeline().clearMemoryCaches();
    }
}
