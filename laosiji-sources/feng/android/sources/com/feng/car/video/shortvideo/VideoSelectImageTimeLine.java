package com.feng.car.video.shortvideo;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.feng.car.databinding.VideoSelectimageTimelineLayoutBinding;
import com.feng.car.utils.FengUtil;
import com.feng.car.video.shortvideo.VideoSelectImageView.BoxChangedListener;
import java.util.ArrayList;
import java.util.List;

public class VideoSelectImageTimeLine extends FrameLayout {
    private VideoSelectimageTimelineLayoutBinding mBinding;
    private Context mContext;
    private ProgressChangedListner mProgressChangedListner;
    private ThumbnailAdapter mThumbnailAdapter;

    public VideoSelectImageTimeLine(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoSelectImageTimeLine(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoSelectImageTimeLine(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        this.mBinding = VideoSelectimageTimelineLayoutBinding.inflate(LayoutInflater.from(context));
        addView(this.mBinding.getRoot());
        this.mThumbnailAdapter = new ThumbnailAdapter(0, new ArrayList());
        this.mBinding.recyclerView.setAdapter(this.mThumbnailAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.mContext);
        linearLayoutManager.setOrientation(0);
        this.mBinding.recyclerView.setLayoutManager(linearLayoutManager);
        this.mBinding.videoSelectImageView.setBoxChangedListener(new BoxChangedListener() {
            public void onBoxChanged(float distanceX, int totalDistance) {
                if (VideoSelectImageTimeLine.this.mProgressChangedListner != null) {
                    VideoSelectImageTimeLine.this.mProgressChangedListner.onProgressChangeListener(distanceX, totalDistance);
                }
            }
        });
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

    public int getBitmapCount(Context context) {
        int count;
        int width = FengUtil.getScreenWidth(context) - (context.getResources().getDimensionPixelSize(2131296334) * 2);
        int itemWidth = context.getResources().getDimensionPixelSize(2131296275);
        if (width % itemWidth == 0) {
            count = width / itemWidth;
        } else {
            count = (width / itemWidth) + 1;
        }
        this.mThumbnailAdapter.setTotalLength(width);
        return count;
    }

    public void setProgressChangedListner(ProgressChangedListner l) {
        this.mProgressChangedListner = l;
    }
}
