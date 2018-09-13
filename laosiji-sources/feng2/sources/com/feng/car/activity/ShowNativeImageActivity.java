package com.feng.car.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.feng.car.R;
import com.feng.car.databinding.ActivityShowNativeImageBinding;
import com.feng.car.databinding.ShowBigimageViewBinding;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.event.MediaSelectChangeEvent;
import com.feng.car.event.TitleNextChangeEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.car.view.BigimageLoadProgressDrawable;
import com.feng.car.view.BigimageLoadProgressDrawable$OnValueChangedListener;
import com.feng.car.view.largeimage.LargeImageView$LargeImageClickListener;
import com.feng.car.view.largeimage.factory.FileBitmapDecoderFactory;
import com.feng.car.view.photoview.OnViewTapListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShowNativeImageActivity extends BaseActivity<ActivityShowNativeImageBinding> {
    public static final String LOCATION_INFO_JSON = "location_info_json";
    private MyAdapter adapter;
    private int mCurrentIndex = 0;
    private List<ImageVideoInfo> mList = new ArrayList();
    private int mScreenHight;
    private int mScreenWidth;

    private class MyAdapter extends PagerAdapter {
        private Context mContext;
        private LayoutInflater mInflater = LayoutInflater.from(this.mContext);
        private List<ImageVideoInfo> pList;

        MyAdapter(List<ImageVideoInfo> list, Context con) {
            this.pList = list;
            this.mContext = con;
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
            mBinding.rlParent.setBackgroundResource(R.drawable.bg_white);
            mBinding.tvSelectIndex.setVisibility(0);
            ViewHolder holder = new ViewHolder(mBinding);
            holder.mBinding.coverView.setVisibility(8);
            final ImageVideoInfo image = (ImageVideoInfo) this.pList.get(position);
            Options opts = new Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(image.url, opts);
            String imgType = opts.outMimeType;
            if (TextUtils.isEmpty(imgType)) {
                imgType = "";
            }
            if (image.width <= 0) {
                image.width = opts.outWidth;
                image.hight = opts.outHeight;
            }
            holder.bindTo(image);
            holder.mBinding.tvSelectIndex.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    EventBus.getDefault().post(new MediaSelectChangeEvent(image));
                    ShowNativeImageActivity.this.refreshImageSize();
                }
            });
            if (imgType.equals("image/gif")) {
                holder.mBinding.imageBigGif.setVisibility(0);
                holder.mBinding.imageBig.setVisibility(8);
                holder.mBinding.longImage.setVisibility(8);
                holder.mBinding.imageBigGif.setAutoImageURI(Uri.parse("file://" + image.url));
                holder.mBinding.imageBigGif.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ShowNativeImageActivity.this.finish();
                    }
                });
            } else {
                if (ShowNativeImageActivity.this.isLongImage(opts)) {
                    holder.mBinding.longImage.setVisibility(0);
                    holder.mBinding.imageBig.setVisibility(8);
                    holder.mBinding.imageBigGif.setVisibility(8);
                    holder.mBinding.longImage.setLargeImageClickListener(new LargeImageView$LargeImageClickListener() {
                        public void onClick() {
                            ShowNativeImageActivity.this.finish();
                        }
                    });
                } else {
                    holder.mBinding.longImage.setVisibility(8);
                    holder.mBinding.imageBigGif.setVisibility(8);
                    holder.mBinding.imageBig.setVisibility(0);
                    GenericDraweeHierarchy hierarchy = (GenericDraweeHierarchy) holder.mBinding.imageBig.getHierarchy();
                    hierarchy.setProgressBarImage(new BigimageLoadProgressDrawable(new BigimageLoadProgressDrawable$OnValueChangedListener() {
                        public void valueChanged(Rect bounds, int level) {
                        }
                    }));
                    holder.mBinding.imageBig.setHierarchy(hierarchy);
                    holder.mBinding.imageBig.setOnViewTapListener(new OnViewTapListener() {
                        public void onViewTap(View view, float x, float y) {
                            ShowNativeImageActivity.this.onBackPressed();
                        }
                    });
                }
                ShowNativeImageActivity.this.showImage(holder, image, opts);
            }
            container.addView(holder.mBinding.getRoot());
            return holder.mBinding.getRoot();
        }
    }

    public class ViewHolder {
        private ShowBigimageViewBinding mBinding;

        public ViewHolder(ShowBigimageViewBinding mAdapterBinding) {
            this.mBinding = mAdapterBinding;
        }

        void bindTo(ImageVideoInfo imageInfo) {
            this.mBinding.setInfo(imageInfo);
        }
    }

    public int setBaseContentView() {
        return R.layout.activity_show_native_image;
    }

    public void initView() {
        ((ActivityShowNativeImageBinding) this.mBaseBinding).llParent.setBackgroundResource(R.drawable.bg_white);
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.setBackgroundResource(R.drawable.bg_white);
        this.mScreenWidth = FengUtil.getScreenWidth(this);
        this.mScreenHight = FengUtil.getScreenHeight(this);
        getMyIntent();
        this.adapter = new MyAdapter(this.mList, this);
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.setAdapter(this.adapter);
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.setPageMargin(getResources().getDimensionPixelOffset(R.dimen.default_40PX));
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.setOnPageChangeListener(new OnPageChangeListener() {
            public void onPageSelected(int position) {
                ShowNativeImageActivity.this.setTitleBarCenterText((position + 1) + "/" + ShowNativeImageActivity.this.mList.size());
                ShowNativeImageActivity.this.mCurrentIndex = position;
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageScrollStateChanged(int arg0) {
            }
        });
        if (this.mCurrentIndex <= 0) {
            this.mCurrentIndex = 0;
        }
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.setCurrentItem(this.mCurrentIndex);
        ((ActivityShowNativeImageBinding) this.mBaseBinding).viewpager.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ((ActivityShowNativeImageBinding) ShowNativeImageActivity.this.mBaseBinding).viewpager.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                ShowNativeImageActivity.this.mScreenHight = ((ActivityShowNativeImageBinding) ShowNativeImageActivity.this.mBaseBinding).viewpager.getHeight();
            }
        });
        ((ActivityShowNativeImageBinding) this.mBaseBinding).originalTag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    ((ActivityShowNativeImageBinding) ShowNativeImageActivity.this.mBaseBinding).imageSizeText.setVisibility(0);
                    FengConstant.UPLOAD_IMAGE_ORIGINAL = true;
                    return;
                }
                ((ActivityShowNativeImageBinding) ShowNativeImageActivity.this.mBaseBinding).imageSizeText.setVisibility(8);
                FengConstant.UPLOAD_IMAGE_ORIGINAL = false;
            }
        });
        ((ActivityShowNativeImageBinding) this.mBaseBinding).originalTag.setChecked(FengConstant.UPLOAD_IMAGE_ORIGINAL);
        refreshImageSize();
    }

    private void getMyIntent() {
        boolean z = false;
        Intent intent = getIntent();
        if (LocalMediaDataUtil.getInstance().getMediaDatas() != null) {
            this.mList.addAll(LocalMediaDataUtil.getInstance().getMediaDatas());
        }
        this.mCurrentIndex = intent.getIntExtra("position", 0);
        if (this.mCurrentIndex >= this.mList.size()) {
            this.mCurrentIndex = 0;
        }
        initNormalTitleBar((this.mCurrentIndex + 1) + "/" + this.mList.size());
        initTitleBarRightTextWithBg(R.string.next, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                try {
                    if (ShowNativeImageActivity.this.mRootBinding.titleLine.tvRightText.isSelected()) {
                        EventBus.getDefault().post(new MediaSelectChangeEvent(2, null));
                        ShowNativeImageActivity.this.finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        TextView textView = this.mRootBinding.titleLine.tvRightText;
        if (this.mList.size() > 0) {
            z = true;
        }
        textView.setSelected(z);
        if (this.mList.size() <= 0) {
            showSecondTypeToast((int) R.string.parameter_wrong);
        }
    }

    private void refreshImageSize() {
        ((ActivityShowNativeImageBinding) this.mBaseBinding).imageSizeText.setText(FengUtil.formatImageSize(FengConstant.UPLOAD_IMAGE_SIZE));
    }

    private ImageRequest getImageRequest(String uri, ImageVideoInfo info) {
        int width;
        int hight;
        if (info.width <= 0 || info.hight <= 0) {
            width = 640;
            hight = (int) (((float) (this.mScreenHight * 640)) / (((float) this.mScreenWidth) * 1.0f));
        } else if (info.width >= 640) {
            width = (int) (((float) (info.hight * 640)) / (((float) info.width) * 1.0f));
            hight = 640;
        } else {
            width = info.width;
            hight = info.hight;
        }
        return ImageRequestBuilder.newBuilderWithSource(Uri.parse("file://" + uri)).setResizeOptions(new ResizeOptions(width, hight)).build();
    }

    private void showImage(final ViewHolder fHolder, ImageVideoInfo image, final Options opts) {
        if (isLongImage(opts)) {
            fHolder.mBinding.longImage.setImage(new FileBitmapDecoderFactory(new File(image.url)));
            return;
        }
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setImageRequest(getImageRequest(image.url, image));
        controller.setOldController(fHolder.mBinding.imageBig.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo != null) {
                    float midScale;
                    float maxScale;
                    float scale;
                    if (((float) imageInfo.getWidth()) / (((float) imageInfo.getHeight()) * 1.0f) >= ((float) ShowNativeImageActivity.this.mScreenWidth) / (((float) ShowNativeImageActivity.this.mScreenHight) * 1.0f)) {
                        try {
                            float minScale;
                            if (imageInfo.getWidth() >= 640) {
                                minScale = 1.0f;
                                midScale = 2.0f;
                                maxScale = 3.0f;
                            } else {
                                scale = ((float) imageInfo.getWidth()) / (((float) ShowNativeImageActivity.this.mScreenWidth) * 1.0f);
                                minScale = scale;
                                maxScale = 1.0f;
                                midScale = ((1.0f - scale) / 2.0f) + scale;
                            }
                            if (fHolder.mBinding.imageBig.getMinimumScale() >= minScale) {
                                fHolder.mBinding.imageBig.setMinimumScale(minScale);
                                if (fHolder.mBinding.imageBig.getMediumScale() >= midScale) {
                                    fHolder.mBinding.imageBig.setMediumScale(midScale);
                                    fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                                } else {
                                    fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                                    fHolder.mBinding.imageBig.setMediumScale(midScale);
                                }
                            } else {
                                fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                                fHolder.mBinding.imageBig.setMediumScale(midScale);
                                fHolder.mBinding.imageBig.setMinimumScale(minScale);
                            }
                            fHolder.mBinding.imageBig.setScale(1.0f, (float) (ShowNativeImageActivity.this.mScreenWidth / 2), (float) (ShowNativeImageActivity.this.mScreenHight / 2), true);
                        } catch (Exception e) {
                            fHolder.mBinding.imageBig.setScale(1.0f, (float) (ShowNativeImageActivity.this.mScreenWidth / 2), 0.0f, true);
                        }
                    } else {
                        scale = ShowNativeImageActivity.this.getScale(opts);
                        maxScale = scale + 1.0f;
                        midScale = ((maxScale - 1.0f) / 2.0f) + 1.0f;
                        if (fHolder.mBinding.imageBig.getMinimumScale() >= 1.0f) {
                            fHolder.mBinding.imageBig.setMinimumScale(1.0f);
                            if (fHolder.mBinding.imageBig.getMediumScale() >= midScale) {
                                fHolder.mBinding.imageBig.setMediumScale(midScale);
                                fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                            } else {
                                fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                                fHolder.mBinding.imageBig.setMediumScale(midScale);
                            }
                        } else {
                            fHolder.mBinding.imageBig.setMaximumScale(maxScale);
                            fHolder.mBinding.imageBig.setMediumScale(midScale);
                            fHolder.mBinding.imageBig.setMinimumScale(1.0f);
                        }
                        fHolder.mBinding.imageBig.setScale(scale, (float) (ShowNativeImageActivity.this.mScreenWidth / 2), 0.0f, true);
                    }
                    fHolder.mBinding.imageBig.update(opts.outWidth, opts.outHeight);
                }
            }
        });
        fHolder.mBinding.imageBig.setController(controller.build());
    }

    private float getScale(Options opts) {
        if (opts.outWidth <= 0 || opts.outHeight <= 0) {
            return 1.0f;
        }
        return ((float) (this.mScreenWidth * opts.outHeight)) / (((float) (this.mScreenHight * opts.outWidth)) * 1.0f);
    }

    public boolean isLongImage(Options opts) {
        if (opts.outWidth <= 0) {
            return false;
        }
        int y;
        if (opts.outWidth >= 640) {
            y = (int) (((float) (opts.outHeight * 640)) / (((float) opts.outWidth) * 1.0f));
        } else {
            y = opts.outHeight;
        }
        if (y > 6000) {
            return true;
        }
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(TitleNextChangeEvent event) {
        this.mRootBinding.titleLine.tvRightText.setSelected(event.isSelected);
    }

    protected void onDestroy() {
        super.onDestroy();
        LocalMediaDataUtil.getInstance().release();
    }
}
