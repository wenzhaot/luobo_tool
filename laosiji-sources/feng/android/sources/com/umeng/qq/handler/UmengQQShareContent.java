package com.umeng.qq.handler;

import android.os.Bundle;
import android.text.TextUtils;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.SimpleShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.IMAGE;
import com.umeng.socialize.utils.UmengText.QQ;
import com.umeng.socialize.utils.UmengText.SHARE;

public class UmengQQShareContent extends SimpleShareContent {
    public UmengQQShareContent(ShareContent shareContent) {
        super(shareContent);
    }

    public Bundle getBundle(boolean isHideQzone, String appName) {
        Bundle bundle;
        if (getmStyle() == 2 || getmStyle() == 3) {
            bundle = buildImageBundle();
        } else if (getmStyle() == 4) {
            bundle = buildMusicBundle();
        } else if (getmStyle() == 16) {
            bundle = buildWebBundle();
        } else if (getmStyle() == 8) {
            bundle = buildVideoBundle();
        } else {
            bundle = buildTextBundle();
            bundle.putString("error", UmengText.supportStyle(false, "text"));
        }
        if (isHideQzone) {
            bundle.putInt(QQConstant.SHARE_TO_QQ_EXT_INT, 2);
        } else {
            bundle.putInt(QQConstant.SHARE_TO_QQ_EXT_INT, 1);
        }
        if (!TextUtils.isEmpty(appName)) {
            bundle.putString(QQConstant.SHARE_TO_QQ_APP_NAME, appName);
        }
        return bundle;
    }

    private Bundle buildTextBundle() {
        Bundle bundle = new Bundle();
        bundle.putString("summary", getText());
        bundle.putInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 1);
        return bundle;
    }

    private Bundle buildVideoBundle() {
        String localpath = null;
        String error = null;
        UMVideo umVideo = getVideo();
        if (umVideo.getThumbImage() != null) {
            if (umVideo.getThumbImage().asFileImage() != null) {
                if (getUMImageScale(umVideo.getThumbImage()) <= 0) {
                    error = IMAGE.SHARECONTENT_THUMB_ERROR;
                }
                localpath = umVideo.getThumbImage().asFileImage().toString();
            } else {
                error = QQ.QQ_PERMISSION;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", subString(objectSetTitle(umVideo), 45));
        bundle.putString("summary", subString(objectSetDescription(umVideo), 60));
        bundle.putString("imageUrl", "");
        bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
        bundle.putInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 1);
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
                bundle.putString("imageUrl", t.toUrl());
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
            }
        }
        bundle.putString("title", subString(objectSetTitle(umWeb), 45));
        bundle.putString("summary", subString(objectSetDescription(umWeb), 60));
        bundle.putInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 1);
        bundle.putString(QQConstant.SHARE_TO_QQ_TARGET_URL, umWeb.toUrl());
        if (TextUtils.isEmpty(getUmWeb().toUrl())) {
            bundle.putString("error", SHARE.EMPTY_WEB_URL);
        }
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
                    error = IMAGE.SHARECONTENT_THUMB_ERROR;
                }
                localpath = mMusic.getThumbImage().asFileImage().toString();
            } else {
                error = QQ.QQ_PERMISSION;
            }
        }
        Bundle bundle = new Bundle();
        bundle.putString("title", subString(objectSetTitle(mMusic), 45));
        bundle.putString("summary", subString(objectSetDescription(mMusic), 60));
        bundle.putString("imageUrl", "");
        bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
        bundle.putInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 2);
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
        if (getImage() == null || getImage().asFileImage() == null) {
            error = QQ.QQ_PERMISSION;
        } else {
            if (getUMImageScale(getImage()) <= 0) {
                error = IMAGE.SHARECONTENT_IMAGE_ERROR;
            }
            localpath = getImage().asFileImage().toString();
        }
        bundle.putString("summary", getText());
        bundle.putString(QQConstant.SHARE_TO_QQ_IMAGE_LOCAL_URL, localpath);
        bundle.putInt(QQConstant.SHARE_TO_QQ_KEY_TYPE, 5);
        if (!TextUtils.isEmpty(error)) {
            bundle.putString("error", error);
        }
        return bundle;
    }
}
