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
import android.view.ViewGroup;
import android.widget.RelativeLayout.LayoutParams;
import com.feng.car.R;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.ShowNativeImageActivity;
import com.feng.car.databinding.ItemImageSelectPhotoBinding;
import com.feng.car.entity.model.ImageVideoBucket;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.ImageVideoInfoList;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ImageSizeChangeEvent;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.LocalMediaDataUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

public class SelectPhotoAdapter extends MvvmBaseAdapter<ImageVideoInfo, ItemImageSelectPhotoBinding> {
    private ImageVideoInfoList mAllInfoList;
    private ImageVideoBucketList mBucketList;
    private Handler mHandler;
    private int mImageIndex = 1;
    private List<Integer> mListIndex = new ArrayList();
    public List<UploadQiNiuLocalPathInfo> mListSelect = new ArrayList();
    public List<ImageVideoInfo> mSelectImageList = new ArrayList();
    private int mType = 0;
    private int mViewWidth;

    public SelectPhotoAdapter(Context context, List<ImageVideoInfo> list, Handler handler, int type, ImageVideoBucketList bucketList) {
        super(context, list);
        this.mContext = context;
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

    public void dataBindingTo(ItemImageSelectPhotoBinding itemImageSelectPhotoBinding, ImageVideoInfo imageItem) {
        itemImageSelectPhotoBinding.setInfo(imageItem);
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
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setLayoutParams(params);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    SelectPhotoAdapter.this.mHandler.sendEmptyMessage(4);
                }
            });
        } else {
            if (TextUtils.isEmpty(item.url)) {
                item.url = "";
            }
            if (this.mType == 0) {
                ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(0);
            } else {
                ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setVisibility(8);
            }
            ((ItemImageSelectPhotoBinding) holder.binding).ivTakePhoto.setVisibility(8);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setVisibility(0);
            ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setDraweeImage("file://" + item.url, this.mViewWidth, this.mViewWidth);
        }
        ((ItemImageSelectPhotoBinding) holder.binding).simpledraweeview.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (position == 0) {
                    SelectPhotoAdapter.this.mHandler.sendEmptyMessage(4);
                } else if (SelectPhotoAdapter.this.mType == 0) {
                    Intent intent = new Intent(SelectPhotoAdapter.this.mContext, ShowNativeImageActivity.class);
                    intent.putExtra("position", position - 1);
                    LocalMediaDataUtil.getInstance().initData(0, null, SelectPhotoAdapter.this.mList, null, SelectPhotoAdapter.this.mListSelect);
                    SelectPhotoAdapter.this.mContext.startActivity(intent);
                } else {
                    SelectPhotoAdapter.this.selectHandle(item);
                }
            }
        });
        if (this.mType == 0) {
            ((ItemImageSelectPhotoBinding) holder.binding).selectExpansion.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (position == 0) {
                        SelectPhotoAdapter.this.mHandler.sendEmptyMessage(4);
                    } else {
                        SelectPhotoAdapter.this.selectHandle(item);
                    }
                }
            });
            ((ItemImageSelectPhotoBinding) holder.binding).tvSelectIndex.setOnClickListener(new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (position == 0) {
                        SelectPhotoAdapter.this.mHandler.sendEmptyMessage(4);
                    } else {
                        SelectPhotoAdapter.this.selectHandle(item);
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
        Options opts;
        String imgType;
        ObservableBoolean observableBoolean;
        int pos;
        ImageVideoBucket imageVideoBucket;
        if (this.mType != 0) {
            if (item.size != null && item.size.length() > 0) {
                try {
                    opts = new Options();
                    opts.inJustDecodeBounds = true;
                    BitmapFactory.decodeFile(item.url, opts);
                    imgType = opts.outMimeType;
                    if (imgType == null || imgType.length() <= 0) {
                        ((BaseActivity) this.mContext).showSecondTypeToast((int) R.string.cannot_support_image_type);
                    } else if (!imgType.equals("image/jpeg") && !imgType.equals("image/png") && !imgType.equals("image/gif") && !imgType.equals("image/bmp") && !imgType.equals("image/webp")) {
                        ((BaseActivity) this.mContext).showSecondTypeToast((int) R.string.cannot_support_image_type);
                    } else if (Integer.parseInt(item.size) > 15728640) {
                        ((BaseActivity) this.mContext).showThirdTypeToast((int) R.string.image_oversize);
                    } else {
                        Message message = Message.obtain();
                        message.obj = item.url;
                        if (this.mType == 1) {
                            message.what = 5;
                        } else if (this.mType == 2 || this.mType == 3 || this.mType == 4) {
                            message.what = 8;
                        }
                        this.mHandler.sendMessage(message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (FengConstant.UPLOAD_IMAGE_COUNT < 30) {
            boolean z2;
            ObservableBoolean observableBoolean2 = item.isSelected;
            if (item.isSelected.get()) {
                z2 = false;
            } else {
                z2 = true;
            }
            observableBoolean2.set(z2);
            if (item.isSelected.get()) {
                if (item.size != null && item.size.length() > 0) {
                    try {
                        opts = new Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(item.url, opts);
                        imgType = opts.outMimeType;
                        if (imgType == null || imgType.length() <= 0) {
                            ((BaseActivity) this.mContext).showSecondTypeToast((int) R.string.cannot_support_image_type);
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            }
                            observableBoolean.set(z);
                            return;
                        } else if (!imgType.equals("image/jpeg") && !imgType.equals("image/png") && !imgType.equals("image/gif") && !imgType.equals("image/bmp") && !imgType.equals("image/webp")) {
                            ((BaseActivity) this.mContext).showThirdTypeToast((int) R.string.upload_image_type_tips);
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            }
                            observableBoolean.set(z);
                            return;
                        } else if (Integer.parseInt(item.size) > 15728640) {
                            ((BaseActivity) this.mContext).showSecondTypeToast((int) R.string.image_oversize);
                            observableBoolean = item.isSelected;
                            if (item.isSelected.get()) {
                                z = false;
                            }
                            observableBoolean.set(z);
                            return;
                        }
                    } catch (Exception e2) {
                        e2.printStackTrace();
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
            item.setSelectIndex(0);
            this.mSelectImageList.remove(item);
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
                item.setSelectIndex(0);
                this.mSelectImageList.remove(item);
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
}
