package com.feng.car.video.shortvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.feng.car.databinding.VideocropTimelineLayoutBinding;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.shortvideo.VideoCropSelcetView.CursorChangedListener;
import java.util.ArrayList;
import java.util.List;

public class VideoCropTimeline extends FrameLayout {
    private int m120Unit;
    private VideocropTimelineLayoutBinding mBinding;
    private Context mContext;
    private float mCursorX;
    private float mDistance;
    private ProgressChangedListener mProgressChangedListner;
    private float mRecylcerViewX;
    private ThumbnailAdapter mThumbnailAdapter;

    public VideoCropTimeline(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoCropTimeline(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoCropTimeline(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mBinding = VideocropTimelineLayoutBinding.inflate(LayoutInflater.from(context));
        addView(this.mBinding.getRoot());
        this.mThumbnailAdapter = new ThumbnailAdapter(0, new ArrayList());
        this.mBinding.recyclerView.setAdapter(this.mThumbnailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        this.mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        this.mBinding.recyclerView.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                VideoCropTimeline.this.mRecylcerViewX = VideoCropTimeline.this.mRecylcerViewX + ((float) dx);
                if (VideoCropTimeline.this.mProgressChangedListner != null) {
                    if (VideoCropTimeline.this.mDistance == 0.0f) {
                        VideoCropTimeline.this.mDistance = (float) (VideoCropTimeline.this.mBinding.videoCropSelcetView.getWidth() - (VideoCropTimeline.this.mBinding.videoCropSelcetView.getBitmapWidth() * 2));
                    }
                    VideoCropTimeline.this.mProgressChangedListner.onProgressChanged(VideoCropTimeline.this.mRecylcerViewX + VideoCropTimeline.this.mCursorX, VideoCropTimeline.this.mDistance);
                }
            }
        });
        this.mBinding.videoCropSelcetView.setCursorChangedListener(new CursorChangedListener() {
            public void onStartCursorChanged(float startx, float distance) {
                VideoCropTimeline.this.mCursorX = startx;
                VideoCropTimeline.this.mDistance = distance;
                if (VideoCropTimeline.this.mProgressChangedListner != null) {
                    VideoCropTimeline.this.mProgressChangedListner.onStartProgressChanged(VideoCropTimeline.this.mRecylcerViewX + VideoCropTimeline.this.mCursorX, VideoCropTimeline.this.mDistance);
                }
            }

            public void onEndCursorChanged(float startx, float distance) {
                VideoCropTimeline.this.mCursorX = startx;
                VideoCropTimeline.this.mDistance = distance;
                if (VideoCropTimeline.this.mProgressChangedListner != null) {
                    VideoCropTimeline.this.mProgressChangedListner.onEndProgressChanged(VideoCropTimeline.this.mRecylcerViewX + VideoCropTimeline.this.mCursorX, VideoCropTimeline.this.mDistance);
                }
            }
        });
    }

    public int getBitmapCount(Context context, long duration) {
        int count;
        int width = (FengUtil.getScreenWidth(context) - (context.getResources().getDimensionPixelSize(2131296334) * 2)) - (this.mBinding.videoCropSelcetView.getBitmapWidth() * 2);
        int itemWidth = context.getResources().getDimensionPixelSize(2131296275);
        this.m120Unit = width;
        int l = (int) (((long) (width / FengConstant.MAX_VIDEO_CROP)) * (duration / 1000));
        if (l % itemWidth == 0) {
            count = l / itemWidth;
        } else {
            count = (l / itemWidth) + 1;
        }
        this.mThumbnailAdapter.setTotalLength(l);
        this.mThumbnailAdapter.setCount(count);
        return count;
    }

    public void addBitmaps(List<Bitmap> bitmapList) {
        this.mThumbnailAdapter.addBitmaps(bitmapList);
    }

    public void addBitmap(Bitmap bitmap) {
        this.mThumbnailAdapter.addBitmap(bitmap);
    }

    public void recycleBitmaps() {
        this.mThumbnailAdapter.recycleBitmaps();
    }

    public float getSecondPerPx() {
        return ((float) FengConstant.MAX_VIDEO_CROP) / ((float) this.m120Unit);
    }

    public float getPxPerSecond() {
        return (float) (this.m120Unit / FengConstant.MAX_VIDEO_CROP);
    }

    public void updateProgressBar(float moveTime) {
        this.mBinding.videoCropSelcetView.updateProgressCenterX((getPxPerSecond() * moveTime) + ((13.0f / ((float) FengConstant.MAX_VIDEO_CROP)) * moveTime));
    }

    public void resetProgress() {
        this.mBinding.videoCropSelcetView.resetProgressCenterX();
    }

    public void setProgressChangedListener(ProgressChangedListener l) {
        this.mProgressChangedListner = l;
    }
}
