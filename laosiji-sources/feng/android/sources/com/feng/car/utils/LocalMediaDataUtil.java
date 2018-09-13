package com.feng.car.utils;

import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.ImageVideoInfoList;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import java.util.List;

public class LocalMediaDataUtil {
    public static final int IMAGE_VIDEO_MIXTURE_TYPE = 1;
    public static final int IMAGE_VIDEO_SPLIT_TYPE = 0;
    private static LocalMediaDataUtil mInstance;
    private ImageVideoInfoList mMediaDatas = new ImageVideoInfoList();
    private List<UploadQiNiuLocalPathInfo> mSelectData;
    private int mType = 0;
    private ImageVideoInfo mVideoData;

    public static LocalMediaDataUtil getInstance() {
        if (mInstance == null) {
            mInstance = new LocalMediaDataUtil();
        }
        return mInstance;
    }

    public void initData(int type, ImageVideoInfo videoData, List<ImageVideoInfo> listImage, List<ImageVideoInfo> listAllVideo, List<UploadQiNiuLocalPathInfo> selectData) {
        this.mMediaDatas.clear();
        if (listImage != null) {
            if (type == 0) {
                this.mMediaDatas.addAll(listImage);
            } else {
                this.mMediaDatas.addAll(listImage);
                if (listAllVideo != null) {
                    this.mMediaDatas.removeAll(listAllVideo);
                }
            }
        }
        this.mVideoData = videoData;
        this.mSelectData = selectData;
        this.mType = type;
    }

    public List<ImageVideoInfo> getMediaDatas() {
        return this.mMediaDatas.getImageVideoInfoList();
    }

    public int getMediaPosition(String id) {
        return this.mMediaDatas.getPosition(id);
    }

    public ImageVideoInfo getVideoData() {
        return this.mVideoData;
    }

    public List<UploadQiNiuLocalPathInfo> getSelectData() {
        return this.mSelectData;
    }

    public void release() {
        this.mMediaDatas.clear();
        this.mVideoData = null;
        this.mSelectData = null;
    }
}
