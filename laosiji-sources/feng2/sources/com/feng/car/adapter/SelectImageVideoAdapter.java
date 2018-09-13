package com.feng.car.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.activity.ShowNativeImageActivity;
import com.feng.car.activity.WatchVideoActivity;
import com.feng.car.databinding.ItemImageSelectPhotoBinding;
import com.feng.car.entity.model.ImageVideoBucket;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.ImageVideoInfoList;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ImageSizeChangeEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.car.video.player.JCUtils;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SelectImageVideoAdapter extends MvvmBaseAdapter<ImageVideoInfo, ItemImageSelectPhotoBinding> {
    private ImageVideoInfoList mAllInfoList;
    private ImageVideoBucketList mBucketList;
    private Handler mHandler;
    private int mImageIndex = 1;
    public boolean mIncludeVideo = false;
    private boolean mIsPhotograph = true;
    private List<Integer> mListIndex = new ArrayList();
    public List<UploadQiNiuLocalPathInfo> mListSelect = new ArrayList();
    public List<ImageVideoInfo> mSelectImageList = new ArrayList();
    private ImageVideoInfo mSelectVideoInfo;
    private UpperLimitClickListener mUpperLimitClickListener;
    private int mViewWidth;

    public interface UpperLimitClickListener {
        void onUpperLimitClick();
    }

    public ImageVideoInfo getSelectVideoInfo() {
        return this.mSelectVideoInfo;
    }

    public void setIsPhotograph(boolean isPhotograph) {
        this.mIsPhotograph = isPhotograph;
    }

    public SelectImageVideoAdapter(Context context, List<ImageVideoInfo> list, Handler handler, ImageVideoBucketList bucketList) {
        super(context, list);
        this.mContext = context;
        this.mHandler = handler;
        this.mViewWidth = (this.mContext.getResources().getDisplayMetrics().widthPixels - this.mContext.getResources().getDimensionPixelSize(R.dimen.default_60PX)) / 4;
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

    public void dataBindingTo(ItemImageSelectPhotoBinding itemImageSelectPhotoBinding, ImageVideoInfo imageItem) {
        itemImageSelectPhotoBinding.setInfo(imageItem);
    }

    private boolean isMaxVideoCount() {
        return FengConstant.UPLOAD_VIDEO_COUNT >= 1;
    }

    private boolean isMaxImageCount() {
        return FengConstant.UPLOAD_IMAGE_COUNT >= 30;
    }

    public void onBaseBindViewHolder(MvvmViewHolder<ItemImageSelectPhotoBinding> holder, final int position) {
        final ImageVideoInfo item = (ImageVideoInfo) this.mList.get(position);
        LayoutParams params = (LayoutParams) ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.getLayoutParams();
        params.height = this.mViewWidth;
        ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setLayoutParams(params);
        if (position == 0) {
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(0);
            LayoutParams params2 = (LayoutParams) ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.getLayoutParams();
            params2.height = this.mViewWidth;
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setLayoutParams(params2);
            if (this.mIsPhotograph) {
                ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setImageResource(R.drawable.camera);
            } else {
                ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setImageResource(R.drawable.icon_video_record);
            }
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (SelectImageVideoAdapter.this.mIsPhotograph) {
                        SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(4);
                    } else {
                        SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(5);
                    }
                }
            });
        } else {
            if (TextUtils.isEmpty(item.url)) {
                item.url = "";
            }
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setDraweeImage("file://" + item.url, this.mViewWidth, this.mViewWidth);
            if (item.mimeType.indexOf("video") == 0) {
                ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setVisibility(0);
                ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setText(JCUtils.stringForTime((int) item.time));
                if (item.time > 300000) {
                    ((ItemImageSelectPhotoBinding) holder.binding).coverView.setVisibility(0);
                } else if (item.isSelected.get() || !isMaxVideoCount()) {
                    ((ItemImageSelectPhotoBinding) holder.binding).coverView.setVisibility(8);
                } else {
                    ((ItemImageSelectPhotoBinding) holder.binding).coverView.setVisibility(0);
                }
            } else {
                ((ItemImageSelectPhotoBinding) holder.binding).tvTime.setVisibility(8);
                if (item.isSelected.get() || !isMaxImageCount()) {
                    ((ItemImageSelectPhotoBinding) holder.binding).coverView.setVisibility(8);
                } else {
                    ((ItemImageSelectPhotoBinding) holder.binding).coverView.setVisibility(0);
                }
            }
        }
        ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent;
                int pos;
                LocalMediaDataUtil instance;
                List list;
                List imageVideoInfoList;
                int index;
                String str;
                if (item.isVideoType()) {
                    if (item.time > 300000) {
                        ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("仅可选择5分钟内的视频");
                        return;
                    } else if (SelectImageVideoAdapter.this.isMaxVideoCount()) {
                        if (item.isSelected.get()) {
                            LocalMediaDataUtil.getInstance().initData(0, item, null, null, SelectImageVideoAdapter.this.mListSelect);
                            intent = new Intent(SelectImageVideoAdapter.this.mContext, WatchVideoActivity.class);
                            intent.putExtra("type", true);
                            SelectImageVideoAdapter.this.mContext.startActivity(intent);
                            return;
                        } else if (SelectImageVideoAdapter.this.mUpperLimitClickListener != null) {
                            SelectImageVideoAdapter.this.mUpperLimitClickListener.onUpperLimitClick();
                            return;
                        } else {
                            return;
                        }
                    }
                } else if (SelectImageVideoAdapter.this.isMaxImageCount()) {
                    if (item.isSelected.get()) {
                        pos = SelectImageVideoAdapter.this.mBucketList.getPosition("all_video");
                        instance = LocalMediaDataUtil.getInstance();
                        list = SelectImageVideoAdapter.this.mList;
                        if (pos >= 0) {
                            imageVideoInfoList = SelectImageVideoAdapter.this.mBucketList.get(pos).list.getImageVideoInfoList();
                        } else {
                            imageVideoInfoList = null;
                        }
                        instance.initData(1, null, list, imageVideoInfoList, SelectImageVideoAdapter.this.mListSelect);
                        intent = new Intent(SelectImageVideoAdapter.this.mContext, ShowNativeImageActivity.class);
                        index = LocalMediaDataUtil.getInstance().getMediaPosition(item.id);
                        str = "position";
                        if (index < 0) {
                            index = 0;
                        }
                        intent.putExtra(str, index);
                        SelectImageVideoAdapter.this.mContext.startActivity(intent);
                        return;
                    } else if (SelectImageVideoAdapter.this.mUpperLimitClickListener != null) {
                        SelectImageVideoAdapter.this.mUpperLimitClickListener.onUpperLimitClick();
                        return;
                    } else {
                        return;
                    }
                }
                if (position != 0) {
                    if (item.mimeType.indexOf("video") == 0) {
                        LocalMediaDataUtil.getInstance().initData(0, item, null, null, SelectImageVideoAdapter.this.mListSelect);
                        intent = new Intent(SelectImageVideoAdapter.this.mContext, WatchVideoActivity.class);
                        intent.putExtra("type", true);
                    } else {
                        pos = SelectImageVideoAdapter.this.mBucketList.getPosition("all_video");
                        instance = LocalMediaDataUtil.getInstance();
                        list = SelectImageVideoAdapter.this.mList;
                        if (pos >= 0) {
                            imageVideoInfoList = SelectImageVideoAdapter.this.mBucketList.get(pos).list.getImageVideoInfoList();
                        } else {
                            imageVideoInfoList = null;
                        }
                        instance.initData(1, null, list, imageVideoInfoList, SelectImageVideoAdapter.this.mListSelect);
                        intent = new Intent(SelectImageVideoAdapter.this.mContext, ShowNativeImageActivity.class);
                        index = LocalMediaDataUtil.getInstance().getMediaPosition(item.id);
                        str = "position";
                        if (index < 0) {
                            index = 0;
                        }
                        intent.putExtra(str, index);
                    }
                    SelectImageVideoAdapter.this.mContext.startActivity(intent);
                } else if (SelectImageVideoAdapter.this.mIsPhotograph) {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(4);
                } else {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(5);
                }
            }
        });
        ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (position != 0) {
                    if (item.isVideoType()) {
                        if (item.time > 300000) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("仅可选择5分钟内的视频");
                            return;
                        } else if (!item.isSelected.get() && SelectImageVideoAdapter.this.isMaxVideoCount()) {
                            if (SelectImageVideoAdapter.this.mUpperLimitClickListener != null) {
                                SelectImageVideoAdapter.this.mUpperLimitClickListener.onUpperLimitClick();
                                return;
                            }
                            return;
                        }
                    } else if (!item.isSelected.get() && SelectImageVideoAdapter.this.isMaxImageCount()) {
                        if (SelectImageVideoAdapter.this.mUpperLimitClickListener != null) {
                            SelectImageVideoAdapter.this.mUpperLimitClickListener.onUpperLimitClick();
                            return;
                        }
                        return;
                    }
                    SelectImageVideoAdapter.this.selectHandleMedia(item);
                } else if (SelectImageVideoAdapter.this.mIsPhotograph) {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(4);
                } else {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(5);
                }
            }
        });
        ((ItemImageSelectPhotoBinding) holder.binding).selectExpansion.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (position != 0) {
                    SelectImageVideoAdapter.this.selectHandleMedia(item);
                } else if (SelectImageVideoAdapter.this.mIsPhotograph) {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(4);
                } else {
                    SelectImageVideoAdapter.this.mHandler.sendEmptyMessage(5);
                }
            }
        });
    }

    public void changeSelectEvent(ImageVideoInfo item) {
        int pos = this.mAllInfoList.getPosition(item.id);
        if (pos >= 0) {
            selectHandleMedia(this.mAllInfoList.get(pos));
        }
    }

    private void selectHandleMedia(ImageVideoInfo item) {
        if (item.isVideoType()) {
            ObservableBoolean observableBoolean;
            boolean z;
            int pos;
            ImageVideoBucket imageVideoBucket;
            if (FengConstant.UPLOAD_VIDEO_COUNT < 1) {
                item.isSelected.set(!item.isSelected.get());
                if (item.isSelected.get()) {
                    try {
                        if (Integer.parseInt(item.size) > 1073741824) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("只能上传1G以下的视频 ");
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            } else {
                                z = true;
                            }
                            observableBoolean.set(z);
                            return;
                        }
                        if (item.time > 300000) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("仅可选择5分钟内的视频");
                            item.isSelected.set(!item.isSelected.get());
                            return;
                        }
                        FengConstant.UPLOAD_VIDEO_COUNT++;
                        pos = this.mBucketList.getPosition(item.bucketId);
                        if (pos >= 0) {
                            imageVideoBucket = this.mBucketList.get(pos);
                            imageVideoBucket.selCount++;
                            imageVideoBucket = this.mBucketList.get(0);
                            imageVideoBucket.selCount++;
                            imageVideoBucket = this.mBucketList.get(1);
                            imageVideoBucket.selCount++;
                            pos = this.mAllInfoList.getPosition(item.id);
                            if (pos >= 0) {
                                this.mListIndex.add(Integer.valueOf(pos));
                            }
                        }
                        int i = this.mImageIndex;
                        this.mImageIndex = i + 1;
                        item.setSelectIndex(i);
                        if (this.mImageIndex == 2) {
                            Message.obtain(this.mHandler, 7).sendToTarget();
                        }
                        this.mListSelect.add(new UploadQiNiuLocalPathInfo(3, item.url, item.time));
                        this.mSelectImageList.add(item);
                        this.mIncludeVideo = true;
                        this.mSelectVideoInfo = item;
                        EventBus.getDefault().post(new ImageSizeChangeEvent());
                    } catch (Exception e) {
                        e.printStackTrace();
                        item.size = "0";
                    }
                } else if (!item.isSelected.get()) {
                    FengConstant.UPLOAD_VIDEO_COUNT--;
                    changeSelectIndex(item.getSelectIndex());
                    this.mImageIndex--;
                    if (this.mImageIndex == 1) {
                        Message.obtain(this.mHandler, 6).sendToTarget();
                    }
                    pos = this.mBucketList.getPosition(item.bucketId);
                    if (pos >= 0) {
                        imageVideoBucket = this.mBucketList.get(pos);
                        imageVideoBucket.selCount--;
                        imageVideoBucket = this.mBucketList.get(0);
                        imageVideoBucket.selCount--;
                        imageVideoBucket = this.mBucketList.get(1);
                        imageVideoBucket.selCount--;
                    }
                    this.mListSelect.remove(item.getSelectIndex() - 1);
                    this.mSelectImageList.remove(item);
                    item.setSelectIndex(0);
                    this.mIncludeVideo = false;
                    this.mSelectVideoInfo = null;
                    EventBus.getDefault().post(new ImageSizeChangeEvent());
                }
            } else if (FengConstant.UPLOAD_VIDEO_COUNT >= 1) {
                if (item.isSelected.get()) {
                    observableBoolean = item.isSelected;
                    if (item.isSelected.get()) {
                        z = false;
                    } else {
                        z = true;
                    }
                    observableBoolean.set(z);
                    changeSelectIndex(item.getSelectIndex());
                    FengConstant.UPLOAD_VIDEO_COUNT--;
                    this.mImageIndex--;
                    this.mIncludeVideo = false;
                    if (this.mImageIndex == 1) {
                        Message.obtain(this.mHandler, 6).sendToTarget();
                    }
                    pos = this.mBucketList.getPosition(item.bucketId);
                    if (pos >= 0) {
                        imageVideoBucket = this.mBucketList.get(pos);
                        imageVideoBucket.selCount--;
                        imageVideoBucket = this.mBucketList.get(0);
                        imageVideoBucket.selCount--;
                        imageVideoBucket = this.mBucketList.get(1);
                        imageVideoBucket.selCount--;
                    }
                    this.mListSelect.remove(item.getSelectIndex() - 1);
                    this.mSelectImageList.remove(item);
                    item.setSelectIndex(0);
                    this.mSelectVideoInfo = null;
                    EventBus.getDefault().post(new ImageSizeChangeEvent());
                } else {
                    ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast((int) R.string.select_one_video_only);
                }
            }
            notifyDataSetChanged();
            return;
        }
        selectHandleImage(item);
        if (FengConstant.UPLOAD_IMAGE_COUNT >= 29) {
            notifyDataSetChanged();
        }
    }

    private void selectHandleImage(ImageVideoInfo item) {
        boolean z = true;
        ObservableBoolean observableBoolean;
        int pos;
        ImageVideoBucket imageVideoBucket;
        if (FengConstant.UPLOAD_IMAGE_COUNT < 30) {
            item.isSelected.set(!item.isSelected.get());
            if (item.isSelected.get()) {
                if (item.size != null && item.size.length() > 0) {
                    try {
                        Options opts = new Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(item.url, opts);
                        String imgType = opts.outMimeType;
                        if (imgType == null || imgType.length() <= 0) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast((int) R.string.cannot_support_image_type);
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            }
                            observableBoolean.set(z);
                            return;
                        } else if (!imgType.equals("image/jpeg") && !imgType.equals("image/png") && !imgType.equals("image/gif") && !imgType.equals("image/bmp") && !imgType.equals("image/webp")) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast((int) R.string.upload_image_type_tips);
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            }
                            observableBoolean.set(z);
                            return;
                        } else if (Integer.parseInt(item.size) > 15728640) {
                            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast("只能上传15M以下的图片");
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
                }
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
                int i = this.mImageIndex;
                this.mImageIndex = i + 1;
                item.setSelectIndex(i);
                if (this.mImageIndex == 2) {
                    Message.obtain(this.mHandler, 7).sendToTarget();
                }
                FengConstant.UPLOAD_IMAGE_COUNT++;
                this.mListSelect.add(new UploadQiNiuLocalPathInfo(2, item.url));
                this.mSelectImageList.add(item);
                FengConstant.UPLOAD_IMAGE_SIZE += Long.parseLong(item.size);
                EventBus.getDefault().post(new ImageSizeChangeEvent());
                return;
            }
            changeSelectIndex(item.getSelectIndex());
            this.mImageIndex--;
            if (this.mImageIndex == 1) {
                Message.obtain(this.mHandler, 6).sendToTarget();
            }
            pos = this.mBucketList.getPosition(item.bucketId);
            if (pos >= 0) {
                imageVideoBucket = this.mBucketList.get(pos);
                imageVideoBucket.selCount--;
                imageVideoBucket = this.mBucketList.get(0);
                imageVideoBucket.selCount--;
            }
            FengConstant.UPLOAD_IMAGE_COUNT--;
            this.mListSelect.remove(item.getSelectIndex() - 1);
            this.mSelectImageList.remove(item);
            item.setSelectIndex(0);
            FengConstant.UPLOAD_IMAGE_SIZE -= Long.parseLong(item.size);
            if (FengConstant.UPLOAD_IMAGE_SIZE <= 0) {
                FengConstant.UPLOAD_IMAGE_SIZE = 0;
            }
            EventBus.getDefault().post(new ImageSizeChangeEvent());
        } else if (FengConstant.UPLOAD_IMAGE_COUNT < 30) {
        } else {
            if (item.isSelected.get()) {
                observableBoolean = item.isSelected;
                if (item.isSelected.get()) {
                    z = false;
                }
                observableBoolean.set(z);
                changeSelectIndex(item.getSelectIndex());
                this.mImageIndex--;
                pos = this.mBucketList.getPosition(item.bucketId);
                if (pos >= 0) {
                    imageVideoBucket = this.mBucketList.get(pos);
                    imageVideoBucket.selCount--;
                    imageVideoBucket = this.mBucketList.get(0);
                    imageVideoBucket.selCount--;
                }
                FengConstant.UPLOAD_IMAGE_COUNT--;
                this.mListSelect.remove(item.getSelectIndex() - 1);
                this.mSelectImageList.remove(item);
                item.setSelectIndex(0);
                FengConstant.UPLOAD_IMAGE_SIZE -= Long.parseLong(item.size);
                if (FengConstant.UPLOAD_IMAGE_SIZE <= 0) {
                    FengConstant.UPLOAD_IMAGE_SIZE = 0;
                }
                EventBus.getDefault().post(new ImageSizeChangeEvent());
                return;
            }
            Message.obtain(this.mHandler, 0).sendToTarget();
        }
    }

    private void changeSelectIndex(int index) {
        if (index <= this.mListSelect.size()) {
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

    public void setUpperLimitClickListener(UpperLimitClickListener l) {
        this.mUpperLimitClickListener = l;
    }
}
