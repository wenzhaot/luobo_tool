package com.umeng.socialize.media;

import android.net.Uri;
import android.text.TextUtils;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.MultiImageObject;
import com.sina.weibo.sdk.api.MusicObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.VideoObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.net.LinkCardResponse;
import com.umeng.socialize.net.LinkcardRequest;
import com.umeng.socialize.net.RestAPI;
import com.umeng.socialize.sina.util.Utility;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.SINA;
import java.io.File;
import java.util.ArrayList;

public class SinaShareContent extends SimpleShareContent {
    private boolean a = false;

    public SinaShareContent(ShareContent shareContent) {
        super(shareContent);
    }

    private WeiboMultiMessage a(WeiboMultiMessage weiboMultiMessage) {
        if (TextUtils.isEmpty(getText())) {
            TextObject textObject = new TextObject();
            if (!(getBaseMediaObject() == null || TextUtils.isEmpty(getBaseMediaObject().getDescription()))) {
                textObject.text = getBaseMediaObject().getDescription();
            }
            weiboMultiMessage.textObject = textObject;
        } else {
            weiboMultiMessage.textObject = c();
        }
        return weiboMultiMessage;
    }

    private TextObject b() {
        TextObject textObject = new TextObject();
        textObject.text = "default text";
        SLog.E(SINA.SINA_MUL_IMAGE);
        return textObject;
    }

    private WeiboMultiMessage b(WeiboMultiMessage weiboMultiMessage) {
        if (!(getBaseMediaObject() == null || getBaseMediaObject().getThumbImage() == null)) {
            ImageObject imageObject = new ImageObject();
            if (canFileValid(getBaseMediaObject().getThumbImage())) {
                imageObject.imagePath = getBaseMediaObject().getThumbImage().asFileImage().toString();
            } else {
                imageObject.imageData = getImageData(getBaseMediaObject().getThumbImage());
            }
            weiboMultiMessage.imageObject = imageObject;
        }
        return weiboMultiMessage;
    }

    private TextObject c() {
        TextObject textObject = new TextObject();
        textObject.text = getText();
        return textObject;
    }

    private ImageObject d() {
        ImageObject imageObject = new ImageObject();
        if (canFileValid(getImage())) {
            imageObject.imagePath = getImage().asFileImage().toString();
        } else {
            imageObject.imageData = getImageData(getImage());
        }
        imageObject.thumbData = objectSetThumb(getImage());
        imageObject.description = getText();
        return imageObject;
    }

    private WebpageObject e() {
        LinkcardRequest linkcardRequest = new LinkcardRequest(ContextUtil.getContext());
        linkcardRequest.setMedia(getUmWeb());
        LinkCardResponse convertLinkCard = RestAPI.convertLinkCard(linkcardRequest);
        WebpageObject webpageObject = new WebpageObject();
        webpageObject.identify = Utility.generateGUID();
        webpageObject.title = objectSetTitle(getUmWeb());
        webpageObject.description = objectSetDescription(getUmWeb());
        if (getUmWeb().getThumbImage() != null) {
            webpageObject.thumbData = objectSetThumb(getUmWeb());
        } else {
            SLog.E(SINA.SINA_THUMB_ERROR);
        }
        if (convertLinkCard == null || TextUtils.isEmpty(convertLinkCard.url)) {
            webpageObject.actionUrl = getUmWeb().toUrl();
        } else {
            webpageObject.actionUrl = convertLinkCard.url;
        }
        webpageObject.defaultText = getText();
        return webpageObject;
    }

    private MusicObject f() {
        MusicObject musicObject = new MusicObject();
        musicObject.identify = Utility.generateGUID();
        musicObject.title = objectSetTitle(getMusic());
        musicObject.description = objectSetDescription(getMusic());
        if (getMusic().getThumbImage() != null) {
            musicObject.thumbData = objectSetThumb(getMusic());
        } else {
            SLog.E(SINA.SINA_THUMB_ERROR);
        }
        musicObject.actionUrl = getMusic().getmTargetUrl();
        if (!TextUtils.isEmpty(getMusic().getLowBandDataUrl())) {
            musicObject.dataUrl = getMusic().getLowBandDataUrl();
        }
        if (!TextUtils.isEmpty(getMusic().getHighBandDataUrl())) {
            musicObject.dataHdUrl = getMusic().getHighBandDataUrl();
        }
        if (!TextUtils.isEmpty(getMusic().getH5Url())) {
            musicObject.h5Url = getMusic().getH5Url();
        }
        if (getMusic().getDuration() > 0) {
            musicObject.duration = getMusic().getDuration();
        } else {
            musicObject.duration = 10;
        }
        if (!TextUtils.isEmpty(getText())) {
            musicObject.defaultText = getText();
        }
        return musicObject;
    }

    private MultiImageObject g() {
        MultiImageObject multiImageObject = new MultiImageObject();
        UMImage[] uMImageArr = getmImages();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < uMImageArr.length; i++) {
            if (uMImageArr[i] != null) {
                File asFileImage = uMImageArr[i].asFileImage();
                if (asFileImage != null) {
                    arrayList.add(Uri.parse(asFileImage.getPath()));
                }
            }
        }
        multiImageObject.setImageList(arrayList);
        return multiImageObject;
    }

    private VideoObject h() {
        VideoObject videoObject = new VideoObject();
        videoObject.identify = Utility.generateGUID();
        videoObject.title = objectSetTitle(getVideo());
        videoObject.description = objectSetDescription(getVideo());
        if (getVideo().getThumbImage() != null) {
            videoObject.thumbData = objectSetThumb(getVideo());
        } else {
            SLog.E(SINA.SINA_THUMB_ERROR);
        }
        videoObject.actionUrl = getVideo().toUrl();
        if (!TextUtils.isEmpty(getVideo().getLowBandDataUrl())) {
            videoObject.dataUrl = getVideo().getLowBandDataUrl();
        }
        if (!TextUtils.isEmpty(getVideo().getHighBandDataUrl())) {
            videoObject.dataHdUrl = getVideo().getHighBandDataUrl();
        }
        if (!TextUtils.isEmpty(getVideo().getH5Url())) {
            videoObject.h5Url = getVideo().getH5Url();
        }
        if (getVideo().getDuration() > 0) {
            videoObject.duration = getVideo().getDuration();
        } else {
            videoObject.duration = 10;
        }
        if (!TextUtils.isEmpty(getVideo().getDescription())) {
            videoObject.description = getVideo().getDescription();
        }
        videoObject.defaultText = getText();
        return videoObject;
    }

    public WeiboMultiMessage a() {
        WeiboMultiMessage weiboMultiMessage = new WeiboMultiMessage();
        if (getmStyle() == 2 || getmStyle() == 3) {
            if (getmImages() == null || getmImages().length <= 0 || !this.a) {
                weiboMultiMessage.imageObject = d();
                if (!TextUtils.isEmpty(getText())) {
                    weiboMultiMessage.textObject = c();
                }
            } else {
                weiboMultiMessage.multiImageObject = g();
                if (TextUtils.isEmpty(getText())) {
                    weiboMultiMessage.textObject = b();
                } else {
                    weiboMultiMessage.textObject = c();
                }
            }
        } else if (getmStyle() == 16) {
            weiboMultiMessage.mediaObject = e();
            a(weiboMultiMessage);
        } else if (getmStyle() == 4) {
            weiboMultiMessage.mediaObject = f();
            a(weiboMultiMessage);
        } else if (getmStyle() == 8) {
            weiboMultiMessage.mediaObject = h();
            a(weiboMultiMessage);
        } else {
            weiboMultiMessage.textObject = c();
        }
        return weiboMultiMessage;
    }

    public void a(boolean z) {
        this.a = z;
    }
}
