package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.WatchVideoActivity;
import com.feng.car.databinding.ItemImageSelectPhotoBinding;
import com.feng.car.entity.model.ImageVideoBucket;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.ImageVideoInfoList;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.library.utils.DateUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;

public class SelectVideoAdapter extends MvvmBaseAdapter<ImageVideoInfo, ItemImageSelectPhotoBinding> {
    private ImageVideoInfoList mAllInfoList;
    private ImageVideoBucketList mBucketList;
    private Handler mHandler;
    private List<Integer> mListIndex = new ArrayList();
    private int mSelectCont = 1;
    public List<UploadQiNiuLocalPathInfo> mSelectVideo = new ArrayList();
    private int mType = 0;
    private int mViewWidth;

    public SelectVideoAdapter(Context context, List<ImageVideoInfo> list, Handler handler, int type, ImageVideoBucketList bucketList) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
        this.mHandler = handler;
        this.mViewWidth = (this.mContext.getResources().getDisplayMetrics().widthPixels - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_60PX)) / 4;
        this.mType = type;
        this.mBucketList = bucketList;
        if (this.mBucketList.size() > 0) {
            this.mAllInfoList = this.mBucketList.get(0).list;
        } else {
            this.mAllInfoList = new ImageVideoInfoList();
        }
    }

    public ItemImageSelectPhotoBinding getBinding(ViewGroup parent, int viewType) {
        return ItemImageSelectPhotoBinding.inflate(LayoutInflater.from(this.mContext));
    }

    public void dataBindingTo(ItemImageSelectPhotoBinding itemImageSelectPhotoBinding, ImageVideoInfo mediaModel) {
        itemImageSelectPhotoBinding.setInfo(mediaModel);
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemImageSelectPhotoBinding> holder, final int position) {
        final ImageVideoInfo item = (ImageVideoInfo) this.mList.get(position);
        LayoutParams params = (LayoutParams) ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.getLayoutParams();
        params.height = this.mViewWidth;
        ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setLayoutParams(params);
        if (position == 0) {
            LayoutParams params2 = (LayoutParams) ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.getLayoutParams();
            params2.height = this.mViewWidth;
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setLayoutParams(params2);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setImageResource(R.drawable.icon_video_record);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SelectVideoAdapter.this.mHandler.sendEmptyMessage(2);
                }
            });
        } else {
            if (TextUtils.isEmpty(item.url)) {
                item.url = "";
            }
            if (this.mType == 1) {
                ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(8);
            } else {
                ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(0);
            }
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setDraweeImage("file://" + item.url, this.mViewWidth, this.mViewWidth);
            ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setText(DateUtil.getStringByFormat(item.time, "mm:ss"));
        }
        ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (position == 0) {
                    SelectVideoAdapter.this.mHandler.sendEmptyMessage(2);
                } else if (SelectVideoAdapter.this.mType == 1) {
                    try {
                        if (Integer.parseInt(item.size) > 1073741824) {
                            ((BaseActivity) SelectVideoAdapter.this.mContext).showThirdTypeToast((int) R.string.video_oversize);
                            return;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        item.size = "0";
                    }
                    SelectVideoAdapter.this.mSelectVideo.add(new UploadQiNiuLocalPathInfo(3, item.url));
                    Message.obtain(SelectVideoAdapter.this.mHandler, 5).sendToTarget();
                } else {
                    LocalMediaDataUtil.getInstance().initData(0, item, null, null, SelectVideoAdapter.this.mSelectVideo);
                    Intent intent = new Intent(SelectVideoAdapter.this.mContext, WatchVideoActivity.class);
                    intent.putExtra("type", true);
                    SelectVideoAdapter.this.mContext.startActivity(intent);
                }
            }
        });
        if (this.mType == 0) {
            ((ItemImageSelectPhotoBinding) holder.binding).selectExpansion.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (position == 0) {
                        SelectVideoAdapter.this.mHandler.sendEmptyMessage(2);
                    } else {
                        SelectVideoAdapter.this.selectHandle(item);
                    }
                }
            });
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (position == 0) {
                        SelectVideoAdapter.this.mHandler.sendEmptyMessage(2);
                    } else {
                        SelectVideoAdapter.this.selectHandle(item);
                    }
                }
            });
        }
    }

    public void changeSelectEvent(ImageVideoInfo item) {
        int pos = this.mAllInfoList.getPosition(item.id);
        if (pos >= 0) {
            selectHandle(this.mAllInfoList.get(pos));
        }
    }

    private void selectHandle(ImageVideoInfo item) {
        boolean z = true;
        ObservableBoolean observableBoolean;
        int pos;
        ImageVideoBucket imageVideoBucket;
        if (FengConstant.UPLOAD_VIDEO_COUNT < 1) {
            item.isSelected.set(!item.isSelected.get());
            if (item.isSelected.get()) {
                try {
                    if (Integer.parseInt(item.size) > 1073741824) {
                        ((BaseActivity) this.mContext).showThirdTypeToast((int) R.string.video_oversize);
                        observableBoolean = item.isSelected;
                        if (item.isSelected.get()) {
                            z = false;
                        }
                        observableBoolean.set(z);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    item.size = "0";
                }
                FengConstant.UPLOAD_VIDEO_COUNT++;
                pos = this.mBucketList.getPosition(item.bucketId);
                if (pos >= 0) {
                    imageVideoBucket = this.mBucketList.get(pos);
                    imageVideoBucket.selCount++;
                    imageVideoBucket = this.mBucketList.get(0);
                    imageVideoBucket.selCount++;
                    pos = this.mAllInfoList.getPosition(item.id);
                    if (pos >= 0) {
                        this.mListIndex.add(Integer.valueOf(pos));
                    }
                }
                int i = this.mSelectCont;
                this.mSelectCont = i + 1;
                item.setSelectIndex(i);
                if (this.mSelectCont == 2) {
                    Message.obtain(this.mHandler, 4).sendToTarget();
                }
                this.mSelectVideo.add(new UploadQiNiuLocalPathInfo(3, item.url));
            } else if (!item.isSelected.get()) {
                FengConstant.UPLOAD_VIDEO_COUNT--;
                changeSelectIndex(item.getSelectIndex());
                this.mSelectCont--;
                if (this.mSelectCont == 1) {
                    Message.obtain(this.mHandler, 3).sendToTarget();
                }
                pos = this.mBucketList.getPosition(item.bucketId);
                if (pos >= 0) {
                    imageVideoBucket = this.mBucketList.get(pos);
                    imageVideoBucket.selCount--;
                    imageVideoBucket = this.mBucketList.get(0);
                    imageVideoBucket.selCount--;
                }
                this.mSelectVideo.remove(item.getSelectIndex() - 1);
                item.setSelectIndex(0);
            }
        } else if (FengConstant.UPLOAD_VIDEO_COUNT < 1) {
        } else {
            if (item.isSelected.get()) {
                observableBoolean = item.isSelected;
                if (item.isSelected.get()) {
                    z = false;
                }
                observableBoolean.set(z);
                changeSelectIndex(item.getSelectIndex());
                FengConstant.UPLOAD_VIDEO_COUNT--;
                this.mSelectCont--;
                if (this.mSelectCont == 0) {
                    Message.obtain(this.mHandler, 3).sendToTarget();
                }
                pos = this.mBucketList.getPosition(item.bucketId);
                if (pos >= 0) {
                    imageVideoBucket = this.mBucketList.get(pos);
                    imageVideoBucket.selCount--;
                    imageVideoBucket = this.mBucketList.get(0);
                    imageVideoBucket.selCount--;
                }
                this.mSelectVideo.remove(item.getSelectIndex() - 1);
                item.setSelectIndex(0);
                return;
            }
            ((BaseActivity) this.mContext).showThirdTypeToast((int) R.string.max_video_select);
        }
    }

    private void changeSelectIndex(int index) {
        if (index <= this.mSelectVideo.size()) {
            int size = this.mListIndex.size();
            for (int i = index; i < size; i++) {
                if (((Integer) this.mListIndex.get(i)).intValue() < this.mAllInfoList.size()) {
                    ImageVideoInfo item = this.mAllInfoList.get(((Integer) this.mListIndex.get(i)).intValue());
                    item.setSelectIndex(item.getSelectIndex() - 1);
                }
            }
            this.mListIndex.remove(index - 1);
        }
    }
}
