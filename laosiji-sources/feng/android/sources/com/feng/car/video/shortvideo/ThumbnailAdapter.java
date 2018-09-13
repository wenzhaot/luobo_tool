package com.feng.car.video.shortvideo;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.feng.car.FengApplication;
import java.util.ArrayList;
import java.util.List;

public class ThumbnailAdapter extends Adapter<ThumbnailViewHolder> {
    private static final int TYPE_FOOTER = 2;
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_THUMBNAIL = 3;
    private int m116;
    private int mCount;
    private List<Bitmap> mThumbnailList;
    private int mTotalLength;
    private int mViewWidth;
    private int padding;

    class ThumbnailViewHolder extends ViewHolder {
        ImageView ivThumbnail;
        FrameLayout parentLine;

        public ThumbnailViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    public void setTotalLength(int length) {
        this.mTotalLength = length;
    }

    public ThumbnailAdapter(int viewWidth, List<Bitmap> thumbnailList) {
        this.mViewWidth = viewWidth;
        if (thumbnailList == null) {
            this.mThumbnailList = new ArrayList();
        }
        this.mThumbnailList = thumbnailList;
        this.padding = FengApplication.getInstance().getResources().getDimensionPixelSize(2131296368);
        this.m116 = FengApplication.getInstance().getResources().getDimensionPixelSize(2131296275);
    }

    public ThumbnailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        ThumbnailViewHolder viewHolder;
        switch (viewType) {
            case 1:
            case 2:
                itemView = LayoutInflater.from(parent.getContext()).inflate(2130903285, null);
                LayoutParams layoutParams = new LayoutParams(-1, -1);
                if (this.mTotalLength != 0) {
                    layoutParams.width = this.mTotalLength % this.m116;
                }
                itemView.setLayoutParams(layoutParams);
                viewHolder = new ThumbnailViewHolder(itemView);
                viewHolder.ivThumbnail = (ImageView) itemView.findViewById(2131625173);
                viewHolder.parentLine = (FrameLayout) itemView.findViewById(2131624342);
                return viewHolder;
            case 3:
                itemView = LayoutInflater.from(parent.getContext()).inflate(2130903285, null);
                viewHolder = new ThumbnailViewHolder(itemView);
                viewHolder.ivThumbnail = (ImageView) itemView.findViewById(2131625173);
                viewHolder.parentLine = (FrameLayout) itemView.findViewById(2131624342);
                return viewHolder;
            default:
                return null;
        }
    }

    public void onBindViewHolder(ThumbnailViewHolder holder, int position) {
        if (position == 0) {
            holder.parentLine.setPadding(0, 0, 0, 0);
        } else {
            holder.parentLine.setPadding(this.padding, 0, 0, 0);
        }
        holder.ivThumbnail.setImageBitmap((Bitmap) this.mThumbnailList.get(position));
    }

    public int getItemCount() {
        return this.mThumbnailList.size();
    }

    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && this.mCount == getItemCount()) {
            return 2;
        }
        return 3;
    }

    public void onViewRecycled(ThumbnailViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder.ivThumbnail != null) {
            holder.ivThumbnail.setImageBitmap(null);
        }
    }

    public void addBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            this.mThumbnailList.add(bitmap);
        }
        notifyDataSetChanged();
    }

    public void addBitmaps(List<Bitmap> list) {
        this.mThumbnailList.clear();
        this.mThumbnailList.addAll(list);
        notifyDataSetChanged();
    }

    public void recycleBitmaps() {
        try {
            if (this.mThumbnailList != null && this.mThumbnailList.size() > 0) {
                for (Bitmap bitmap : this.mThumbnailList) {
                    if (bitmap != null) {
                        bitmap.recycle();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
