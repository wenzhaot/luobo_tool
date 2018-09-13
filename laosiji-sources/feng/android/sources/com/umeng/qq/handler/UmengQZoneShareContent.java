package com.umeng.qq.handler;

import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.SimpleShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.UmengText.IMAGE;
import com.umeng.socialize.utils.UmengText.QQ;
import java.io.File;
import java.util.ArrayList;

public class UmengQZoneShareContent extends SimpleShareContent {
    public ArrayList imagelist = new ArrayList();

    public UmengQZoneShareContent(ShareContent shareContent) {
        super(shareContent);
    }

    public Bundle getBundle(String appName) {
        Bundle bundle;
        if (getmStyle() == 2 || getmStyle() == 3) {
            bundle = buildImageBundle();
            bundle.putString(QQConstant.SHARE_UMENG_TYPE, QQConstant.SHARE_SHUO);
        } else if (getmStyle() == 4) {
            bundle = buildMusicBundle();
            bundle.putString(QQConstant.SHARE_UMENG_TYPE, QQConstant.SHARE_QZONE);
        } else if (getmStyle() == 16) {
            bundle = buildWebBundle();
            bundle.putString(QQConstant.SHARE_UMENG_TYPE, QQConstant.SHARE_QZONE);
        } else if (getmStyle() == 8) {
            bundle = buildVideoBundle();
            bundle.putString(QQConstant.SHARE_UMENG_TYPE, QQConstant.SHARE_QZONE);
        } else {
            bundle = buildTextBundle();
            bundle.putString(QQConstant.SHARE_UMENG_TYPE, QQConstant.SHARE_SHUO);
        }
        if (!TextUtils.isEmpty(appName)) {
            bundle.putString(QQConstant.SHARE_TO_QQ_APP_NAME, appName);
        }
        return bundle;
    }

    private Bundle buildTextBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("summary", getText());
        return bundle;
    }

    private Bundle buildVideoBundle() {
        String localpath = null;
        String error = null;
        UMVideo umVideo = getVideo();
        if (umVideo.getThumbImage() != null) {
            if (umVideo.getThumbImage().asFileImage() != null) {
                if (getUMImageScale(umVideo.getThumbImage()) <= 0) {
                    error = IMAGE.SHARECONTENT_IMAGE_ERROR;
                }
                localpath = umVideo.getThumbImage().asFileImage().toString();
            } else {
                error = QQ.QQ_PERMISSION;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", subString(objectSetTitle(umVideo), 200));
        bundle.putString("summary", subString(objectSetDescription(umVideo), 600));
        bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
        if (!TextUtils.isEmpty(localpath)) {
            this.imagelist.clear();
            this.imagelist.add(localpath);
        }
        bundle.putStringArrayList("imageUrl", this.imagelist);
        bundle.putString(QQConstant.SHARE_TO_QQ_TARGET_URL, umVideo.toUrl());
        if (!TextUtils.isEmpty(error)) {
            bundle.putString("error", error);
        }
        return bundle;
    }

    private Bundle buildWebBundle() {
        String localpath = null;
        String error = null;
        UMWeb umWeb = getUmWeb();
        Bundle bundle = new Bundle();
        if (umWeb.getThumbImage() != null) {
            UMImage t = umWeb.getThumbImage();
            if (t.isUrlMedia()) {
                ArrayList<String> list = new ArrayList();
                list.add(t.toUrl());
                bundle.putStringArrayList("imageUrl", list);
            } else {
                if (umWeb.getThumbImage().asFileImage() != null) {
                    if (getUMImageScale(umWeb.getThumbImage()) <= 0) {
                        error = IMAGE.SHARECONTENT_THUMB_ERROR;
                    }
                    localpath = umWeb.getThumbImage().asFileImage().toString();
                } else {
                    error = QQ.QQ_PERMISSION;
                }
                bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
                if (!TextUtils.isEmpty(localpath)) {
                    this.imagelist.clear();
                    this.imagelist.add(localpath);
                }
                bundle.putStringArrayList("imageUrl", this.imagelist);
            }
        }
        bundle.putString("title", subString(objectSetTitle(umWeb), 200));
        bundle.putString("summary", subString(objectSetDescription(umWeb), 600));
        bundle.putString(QQConstant.SHARE_TO_QQ_TARGET_URL, umWeb.toUrl());
        if (!TextUtils.isEmpty(error)) {
            bundle.putString("error", error);
        }
        return bundle;
    }

    private Bundle buildMusicBundle() {
        String localpath = null;
        String error = null;
        UMusic mMusic = getMusic();
        if (mMusic.getThumbImage() != null) {
            if (mMusic.getThumbImage().asFileImage() != null) {
                if (getUMImageScale(mMusic.getThumbImage()) <= 0) {
                    error = IMAGE.SHARECONTENT_IMAGE_ERROR;
                }
                localpath = mMusic.getThumbImage().asFileImage().toString();
            } else {
                error = QQ.QQ_PERMISSION;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", subString(objectSetTitle(mMusic), 200));
        bundle.putString("summary", subString(objectSetDescription(mMusic), 600));
        bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
        if (!TextUtils.isEmpty(localpath)) {
            this.imagelist.clear();
            this.imagelist.add(localpath);
        }
        bundle.putStringArrayList("imageUrl", this.imagelist);
        bundle.putString(QQConstant.SHARE_TO_QQ_TARGET_URL, mMusic.getmTargetUrl());
        bundle.putString(QQConstant.SHARE_TO_QQ_AUDIO_URL, mMusic.toUrl());
        if (!TextUtils.isEmpty(error)) {
            bundle.putString("error", error);
        }
        return bundle;
    }

    private Bundle buildImageBundle() {
        String localpath = null;
        String error = null;
        Bundle bundle = new Bundle();
        if (getmImages() == null || getmImages().length <= 0) {
            if (getImage().asFileImage() != null) {
                if (getUMImageScale(getImage()) <= 0) {
                    error = IMAGE.SHARECONTENT_IMAGE_ERROR;
                }
                localpath = getImage().asFileImage().toString();
            } else {
                error = QQ.QQ_PERMISSION;
            }
            bundle.putString("summary", getText());
            bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
            if (!TextUtils.isEmpty(localpath)) {
                this.imagelist.clear();
                this.imagelist.add(localpath);
            }
            bundle.putStringArrayList("imageUrl", this.imagelist);
        } else {
            ArrayList<String> paths = new ArrayList();
            for (UMImage temp : getmImages()) {
                File f = temp.asFileImage();
                if (f != null) {
                    paths.add(f.toString());
                }
            }
            bundle.putStringArrayList("imageUrl", paths);
        }
        if (!TextUtils.isEmpty(error)) {
            bundle.putString("error", error);
        }
        return bundle;
    }
}
