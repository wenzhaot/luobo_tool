package com.feng.car.view.slidebanner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.feng.car.R;
import com.feng.car.view.slidebanner.listener.OnBannerClickListener;
import com.feng.car.view.slidebanner.listener.OnBannerListener;
import com.feng.car.view.slidebanner.loader.ImageLoaderInterface;
import com.feng.car.view.slidebanner.view.BannerViewPager;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Banner extends FrameLayout implements OnPageChangeListener {
    private BannerPagerAdapter adapter;
    private OnBannerClickListener bannerListener;
    private int bannerStyle;
    private TextView bannerTitle;
    private Context context;
    private int count;
    private int currentItem;
    private int delayTime;
    private DisplayMetrics dm;
    private int gravity;
    private WeakHandler handler;
    private ImageLoaderInterface imageLoader;
    private List imageUrls;
    private List<View> imageViews;
    private LinearLayout indicator;
    private List<ImageView> indicatorImages;
    private LinearLayout indicatorInside;
    private int indicatorSize;
    private boolean isAutoPlay;
    private boolean isScroll;
    private int lastPosition;
    private OnBannerListener listener;
    private BannerViewPagerOnTouchListener mBannerViewPagerOnTouchListener;
    private int mIndicatorHeight;
    private int mIndicatorMargin;
    private int mIndicatorSelectedResId;
    private int mIndicatorUnselectedResId;
    private int mIndicatorWidth;
    private int mLayoutResId;
    private OnPageChangeListener mOnPageChangeListener;
    private BannerScroller mScroller;
    private TextView numIndicator;
    private TextView numIndicatorInside;
    private int scaleType;
    private int scrollTime;
    public String tag;
    private final Runnable task;
    private int titleBackground;
    private int titleHeight;
    private int titleTextColor;
    private int titleTextSize;
    private LinearLayout titleView;
    private List<String> titles;
    private BannerViewPager viewPager;

    class BannerPagerAdapter extends PagerAdapter {
        BannerPagerAdapter() {
        }

        public int getCount() {
            return Banner.this.imageViews.size();
        }

        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView((View) Banner.this.imageViews.get(position));
            View view = (View) Banner.this.imageViews.get(position);
            if (Banner.this.bannerListener != null) {
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Banner.this.bannerListener.OnBannerClick(position);
                    }
                });
            }
            if (Banner.this.listener != null) {
                view.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        Banner.this.listener.OnBannerClick(Banner.this.toRealPosition(position));
                    }
                });
            }
            return view;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface BannerViewPagerOnTouchListener {
        void onBannerTouchListener(MotionEvent motionEvent);
    }

    public Banner(Context context) {
        this(context, null);
    }

    public Banner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Banner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.tag = "banner";
        this.mIndicatorMargin = 10;
        this.bannerStyle = 1;
        this.delayTime = 2000;
        this.scrollTime = 800;
        this.isAutoPlay = true;
        this.isScroll = true;
        this.mIndicatorSelectedResId = 2130838491;
        this.mIndicatorUnselectedResId = 2130838492;
        this.mLayoutResId = 2130903167;
        this.count = 0;
        this.gravity = -1;
        this.lastPosition = 1;
        this.scaleType = 1;
        this.handler = new WeakHandler();
        this.task = new Runnable() {
            public void run() {
                if (Banner.this.count > 1 && Banner.this.isAutoPlay) {
                    Banner.this.currentItem = (Banner.this.currentItem % (Banner.this.count + 1)) + 1;
                    if (Banner.this.currentItem == 1) {
                        Banner.this.viewPager.setCurrentItem(Banner.this.currentItem, false);
                        Banner.this.handler.post(Banner.this.task);
                        return;
                    }
                    Banner.this.viewPager.setCurrentItem(Banner.this.currentItem);
                    Banner.this.handler.postDelayed(Banner.this.task, (long) Banner.this.delayTime);
                }
            }
        };
        this.context = context;
        this.titles = new ArrayList();
        this.imageUrls = new ArrayList();
        this.imageViews = new ArrayList();
        this.indicatorImages = new ArrayList();
        this.indicatorSize = context.getResources().getDimensionPixelSize(2131296290);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        this.imageViews.clear();
        handleTypedArray(context, attrs);
        View view = LayoutInflater.from(context).inflate(this.mLayoutResId, this, true);
        this.viewPager = (BannerViewPager) view.findViewById(2131623948);
        this.titleView = (LinearLayout) view.findViewById(2131624016);
        this.indicator = (LinearLayout) view.findViewById(2131623951);
        this.indicatorInside = (LinearLayout) view.findViewById(2131623977);
        this.bannerTitle = (TextView) view.findViewById(2131623947);
        this.numIndicator = (TextView) view.findViewById(2131623985);
        this.numIndicatorInside = (TextView) view.findViewById(2131623986);
        initViewPagerScroll();
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Banner);
            this.mIndicatorWidth = context.getResources().getDimensionPixelSize(2131296423);
            this.mIndicatorHeight = context.getResources().getDimensionPixelSize(2131296701);
            this.mIndicatorMargin = typedArray.getDimensionPixelSize(9, 10);
            this.mIndicatorSelectedResId = typedArray.getResourceId(10, 2130838491);
            this.mIndicatorUnselectedResId = typedArray.getResourceId(11, 2130838492);
            this.scaleType = typedArray.getInt(13, this.scaleType);
            this.delayTime = typedArray.getInt(0, 2000);
            this.scrollTime = typedArray.getInt(1, 800);
            this.isAutoPlay = typedArray.getBoolean(2, true);
            this.titleBackground = typedArray.getColor(3, -1);
            this.titleHeight = typedArray.getDimensionPixelSize(6, -1);
            this.titleTextColor = typedArray.getColor(4, -1);
            this.titleTextSize = typedArray.getDimensionPixelSize(5, -1);
            this.mLayoutResId = typedArray.getResourceId(12, this.mLayoutResId);
            typedArray.recycle();
        }
    }

    private void initViewPagerScroll() {
        try {
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            this.mScroller = new BannerScroller(this.viewPager.getContext());
            this.mScroller.setDuration(this.scrollTime);
            mField.set(this.viewPager, this.mScroller);
        } catch (Exception e) {
        }
    }

    public Banner isAutoPlay(boolean isAutoPlay) {
        this.isAutoPlay = isAutoPlay;
        return this;
    }

    public Banner setImageLoader(ImageLoaderInterface imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public Banner setDelayTime(int delayTime) {
        this.delayTime = delayTime;
        return this;
    }

    public Banner setIndicatorGravity(int type) {
        switch (type) {
            case 5:
                this.gravity = 19;
                break;
            case 6:
                this.gravity = 17;
                break;
            case 7:
                this.gravity = 21;
                break;
        }
        return this;
    }

    public Banner setBannerAnimation(Class<? extends PageTransformer> transformer) {
        try {
            setPageTransformer(true, (PageTransformer) transformer.newInstance());
        } catch (Exception e) {
        }
        return this;
    }

    public Banner setOffscreenPageLimit(int limit) {
        if (this.viewPager != null) {
            this.viewPager.setOffscreenPageLimit(limit);
        }
        return this;
    }

    public Banner setPageTransformer(boolean reverseDrawingOrder, PageTransformer transformer) {
        this.viewPager.setPageTransformer(reverseDrawingOrder, transformer);
        return this;
    }

    public Banner setBannerTitles(List<String> titles) {
        this.titles = titles;
        return this;
    }

    public Banner setBannerStyle(int bannerStyle) {
        this.bannerStyle = bannerStyle;
        return this;
    }

    public Banner setViewPagerIsScroll(boolean isScroll) {
        this.isScroll = isScroll;
        return this;
    }

    public Banner setImages(List<?> imageUrls) {
        this.imageUrls = imageUrls;
        this.count = imageUrls.size();
        return this;
    }

    public void update(List<?> imageUrls, List<String> titles) {
        this.imageUrls.clear();
        this.titles.clear();
        this.imageUrls.addAll(imageUrls);
        this.titles.addAll(titles);
        this.count = this.imageUrls.size();
        start();
    }

    public void update(List<?> imageUrls) {
        this.imageUrls.clear();
        this.imageUrls.addAll(imageUrls);
        this.count = this.imageUrls.size();
        start();
    }

    public void updateBannerStyle(int bannerStyle) {
        this.indicator.setVisibility(8);
        this.numIndicator.setVisibility(8);
        this.numIndicatorInside.setVisibility(8);
        this.indicatorInside.setVisibility(8);
        this.bannerTitle.setVisibility(8);
        this.titleView.setVisibility(8);
        this.bannerStyle = bannerStyle;
        start();
    }

    public Banner start() {
        setBannerStyleUI();
        setImageList(this.imageUrls);
        setData();
        return this;
    }

    private void setTitleStyleUI() {
        if (this.titles.size() != this.imageUrls.size()) {
            throw new RuntimeException("[Banner] --> The number of titles and images is different");
        }
        if (this.titleBackground != -1) {
            this.titleView.setBackgroundColor(this.titleBackground);
        }
        if (this.titleHeight != -1) {
            this.titleView.setLayoutParams(new LayoutParams(-1, this.titleHeight));
        }
        if (this.titleTextColor != -1) {
            this.bannerTitle.setTextColor(this.titleTextColor);
        }
        if (this.titleTextSize != -1) {
            this.bannerTitle.setTextSize(0, (float) this.titleTextSize);
        }
        if (this.titles != null && this.titles.size() > 0) {
            this.bannerTitle.setText((CharSequence) this.titles.get(0));
            this.bannerTitle.setVisibility(0);
            this.titleView.setVisibility(0);
        }
    }

    private void setBannerStyleUI() {
        int visibility;
        if (this.count > 1) {
            visibility = 0;
        } else {
            visibility = 8;
        }
        switch (this.bannerStyle) {
            case 1:
                this.indicator.setVisibility(visibility);
                return;
            case 2:
                this.numIndicator.setVisibility(visibility);
                return;
            case 3:
                this.numIndicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                return;
            case 4:
                this.indicator.setVisibility(visibility);
                setTitleStyleUI();
                return;
            case 5:
                this.indicatorInside.setVisibility(visibility);
                setTitleStyleUI();
                return;
            default:
                return;
        }
    }

    private void initImages() {
        this.imageViews.clear();
        if (this.bannerStyle == 1 || this.bannerStyle == 4 || this.bannerStyle == 5) {
            createIndicator();
        } else if (this.bannerStyle == 3) {
            this.numIndicatorInside.setText("1/" + this.count);
        } else if (this.bannerStyle == 2) {
            this.numIndicator.setText("1/" + this.count);
        }
    }

    private void setImageList(List<?> imagesUrl) {
        if (imagesUrl != null && imagesUrl.size() > 0) {
            initImages();
            for (int i = 0; i <= this.count + 1; i++) {
                Object url;
                View imageView = null;
                if (this.imageLoader != null) {
                    imageView = this.imageLoader.createImageView(this.context);
                }
                if (imageView == null) {
                    imageView = new ImageView(this.context);
                }
                setScaleType(imageView);
                if (i == 0) {
                    url = imagesUrl.get(this.count - 1);
                } else if (i == this.count + 1) {
                    url = imagesUrl.get(0);
                } else {
                    url = imagesUrl.get(i - 1);
                }
                this.imageViews.add(imageView);
                if (this.imageLoader != null) {
                    this.imageLoader.displayImage(this.context, url, imageView);
                }
            }
        }
    }

    private void setScaleType(View imageView) {
        if (imageView instanceof ImageView) {
            ImageView view = (ImageView) imageView;
            switch (this.scaleType) {
                case 0:
                    view.setScaleType(ScaleType.CENTER);
                    return;
                case 1:
                    view.setScaleType(ScaleType.CENTER_CROP);
                    return;
                case 2:
                    view.setScaleType(ScaleType.CENTER_INSIDE);
                    return;
                case 3:
                    view.setScaleType(ScaleType.FIT_CENTER);
                    return;
                case 4:
                    view.setScaleType(ScaleType.FIT_END);
                    return;
                case 5:
                    view.setScaleType(ScaleType.FIT_START);
                    return;
                case 6:
                    view.setScaleType(ScaleType.FIT_XY);
                    return;
                case 7:
                    view.setScaleType(ScaleType.MATRIX);
                    return;
                default:
                    return;
            }
        }
    }

    private void createIndicator() {
        this.indicatorImages.clear();
        this.indicator.removeAllViews();
        this.indicatorInside.removeAllViews();
        for (int i = 0; i < this.count; i++) {
            ImageView imageView = new ImageView(this.context);
            imageView.setScaleType(ScaleType.CENTER_CROP);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(this.mIndicatorWidth, this.mIndicatorHeight);
            params.leftMargin = this.mIndicatorMargin;
            params.rightMargin = this.mIndicatorMargin;
            if (i == 0) {
                imageView.setImageResource(this.mIndicatorSelectedResId);
            } else {
                imageView.setImageResource(this.mIndicatorUnselectedResId);
            }
            this.indicatorImages.add(imageView);
            if (this.bannerStyle == 1 || this.bannerStyle == 4) {
                this.indicator.addView(imageView, params);
            } else if (this.bannerStyle == 5) {
                this.indicatorInside.addView(imageView, params);
            }
        }
    }

    private void setData() {
        this.currentItem = 1;
        if (this.adapter == null) {
            this.adapter = new BannerPagerAdapter();
            this.viewPager.addOnPageChangeListener(this);
        }
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setFocusable(true);
        this.viewPager.setCurrentItem(1);
        if (this.gravity != -1) {
            this.indicator.setGravity(this.gravity);
        }
        if (!this.isScroll || this.count <= 1) {
            this.viewPager.setScrollable(false);
        } else {
            this.viewPager.setScrollable(true);
        }
        if (this.isAutoPlay) {
            startAutoPlay();
        }
    }

    public void startAutoPlay() {
        this.handler.removeCallbacks(this.task);
        this.handler.postDelayed(this.task, (long) this.delayTime);
    }

    public void stopAutoPlay() {
        this.handler.removeCallbacks(this.task);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.isAutoPlay) {
            int action = ev.getAction();
            if (action == 1 || action == 3 || action == 4) {
                startAutoPlay();
            } else if (action == 0) {
                stopAutoPlay();
            }
        }
        if (this.mBannerViewPagerOnTouchListener != null) {
            this.mBannerViewPagerOnTouchListener.onBannerTouchListener(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void setBannerViewPagerOnTouchListener(BannerViewPagerOnTouchListener l) {
        this.mBannerViewPagerOnTouchListener = l;
    }

    public int toRealPosition(int position) {
        int realPosition = (position - 1) % this.count;
        if (realPosition < 0) {
            return realPosition + this.count;
        }
        return realPosition;
    }

    public void onPageScrollStateChanged(int state) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        this.currentItem = this.viewPager.getCurrentItem();
        switch (state) {
            case 0:
                if (this.currentItem == 0) {
                    this.viewPager.setCurrentItem(this.count, false);
                    return;
                } else if (this.currentItem == this.count + 1) {
                    this.viewPager.setCurrentItem(1, false);
                    return;
                } else {
                    return;
                }
            case 1:
                if (this.currentItem == this.count + 1) {
                    this.viewPager.setCurrentItem(1, false);
                    return;
                } else if (this.currentItem == 0) {
                    this.viewPager.setCurrentItem(this.count, false);
                    return;
                } else {
                    return;
                }
            default:
                return;
        }
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.mOnPageChangeListener != null) {
            this.mOnPageChangeListener.onPageSelected(position);
        }
        if (this.bannerStyle == 1 || this.bannerStyle == 4 || this.bannerStyle == 5) {
            ((ImageView) this.indicatorImages.get(((this.lastPosition - 1) + this.count) % this.count)).setImageResource(this.mIndicatorUnselectedResId);
            ((ImageView) this.indicatorImages.get(((position - 1) + this.count) % this.count)).setImageResource(this.mIndicatorSelectedResId);
            this.lastPosition = position;
        }
        if (position == 0) {
            position = this.count;
        }
        if (position > this.count) {
            position = 1;
        }
        switch (this.bannerStyle) {
            case 2:
                this.numIndicator.setText(position + "/" + this.count);
                return;
            case 3:
                this.numIndicatorInside.setText(position + "/" + this.count);
                this.bannerTitle.setText((CharSequence) this.titles.get(position - 1));
                return;
            case 4:
                this.bannerTitle.setText((CharSequence) this.titles.get(position - 1));
                return;
            case 5:
                this.bannerTitle.setText((CharSequence) this.titles.get(position - 1));
                return;
            default:
                return;
        }
    }

    @Deprecated
    public Banner setOnBannerClickListener(OnBannerClickListener listener) {
        this.bannerListener = listener;
        return this;
    }

    public Banner setOnBannerListener(OnBannerListener listener) {
        this.listener = listener;
        return this;
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }

    public void releaseBanner() {
        this.handler.removeCallbacksAndMessages(null);
    }
}
