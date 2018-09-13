package com.umeng.socialize.media;

import android.graphics.Bitmap.CompressFormat;
import android.text.TextUtils;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.b.a.a;
import com.umeng.socialize.interfaces.CompressListener;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.IMAGE;
import java.io.File;

public class SimpleShareContent {
    public final String DEFAULT_DESCRIPTION = "这里是描述";
    public final String DEFAULT_TITLE = "这里是标题";
    public final int IMAGE_LIMIT = 491520;
    public int THUMB_LIMIT = 24576;
    public int WX_MIN_LIMIT = 131072;
    public int WX_THUMB_LIMIT = 18432;
    private UMImage a;
    private UMImage[] b;
    private String c;
    private UMVideo d;
    private UMEmoji e;
    private UMusic f;
    private UMMin g;
    private UMWeb h;
    private File i;
    private BaseMediaObject j;
    private int k;
    private String l;
    private String m;
    private CompressListener n;

    public SimpleShareContent(ShareContent shareContent) {
        this.c = shareContent.mText;
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMImage)) {
            this.a = (UMImage) shareContent.mMedia;
            this.j = this.a;
            if (shareContent.mMedias != null && shareContent.mMedias.length > 0) {
                this.b = shareContent.mMedias;
            }
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMusic)) {
            this.f = (UMusic) shareContent.mMedia;
            this.j = this.f;
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMVideo)) {
            this.d = (UMVideo) shareContent.mMedia;
            this.j = this.d;
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMEmoji)) {
            this.e = (UMEmoji) shareContent.mMedia;
            this.j = this.e;
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMWeb)) {
            this.h = (UMWeb) shareContent.mMedia;
            this.j = this.h;
        }
        if (shareContent.mMedia != null && (shareContent.mMedia instanceof UMMin)) {
            this.g = (UMMin) shareContent.mMedia;
            this.j = this.h;
        }
        if (shareContent.file != null) {
            this.i = shareContent.file;
        }
        this.m = shareContent.subject;
        this.k = shareContent.getShareType();
        this.l = a();
    }

    public void setCompressListener(CompressListener compressListener) {
        this.n = compressListener;
    }

    private String a() {
        switch (this.k) {
            case 1:
                return "text";
            case 2:
                return "image";
            case 3:
                return "textandimage";
            case 4:
                return "music";
            case 8:
                return "video";
            case 16:
                return "web";
            case 32:
                return "file";
            case 64:
                return "emoji";
            case 128:
                return "minapp";
            default:
                return "error";
        }
    }

    public File getFile() {
        return this.i;
    }

    public UMEmoji getUmEmoji() {
        return this.e;
    }

    public BaseMediaObject getBaseMediaObject() {
        return this.j;
    }

    public String getSubject() {
        return this.m;
    }

    public String getAssertSubject() {
        if (TextUtils.isEmpty(this.m)) {
            return "umengshare";
        }
        return this.m;
    }

    public String getStrStyle() {
        return this.l;
    }

    public int getmStyle() {
        return this.k;
    }

    public UMWeb getUmWeb() {
        return this.h;
    }

    public UMMin getUmMin() {
        return this.g;
    }

    public void setText(String str) {
        this.c = str;
    }

    public String getText() {
        return this.c;
    }

    public void setImage(UMImage uMImage) {
        this.a = uMImage;
    }

    public UMImage getImage() {
        return this.a;
    }

    public UMImage[] getmImages() {
        return this.b;
    }

    public void setMusic(UMusic uMusic) {
        this.f = uMusic;
    }

    public UMusic getMusic() {
        return this.f;
    }

    public void setVideo(UMVideo uMVideo) {
        this.d = uMVideo;
    }

    public UMVideo getVideo() {
        return this.d;
    }

    public String objectSetTitle(BaseMediaObject baseMediaObject) {
        if (TextUtils.isEmpty(baseMediaObject.getTitle())) {
            return "这里是标题";
        }
        String title = baseMediaObject.getTitle();
        if (title.length() > 512) {
            return title.substring(0, 512);
        }
        return title;
    }

    public String objectSetDescription(BaseMediaObject baseMediaObject) {
        if (TextUtils.isEmpty(baseMediaObject.getDescription())) {
            return "这里是描述";
        }
        String description = baseMediaObject.getDescription();
        if (description.length() > 1024) {
            return description.substring(0, 1024);
        }
        return description;
    }

    public String objectSetText(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return "这里是描述";
        }
        if (str.length() > i) {
            return str.substring(0, i);
        }
        return str;
    }

    public String objectSetText(String str) {
        return objectSetText(str, 10240);
    }

    public byte[] objectSetThumb(BaseMediaObject baseMediaObject) {
        if (baseMediaObject.getThumbImage() == null) {
            return b();
        }
        byte[] asBinImage;
        if (this.n != null) {
            UMImage thumbImage = baseMediaObject.getThumbImage();
            if (thumbImage == null) {
                return DefaultClass.getBytes();
            }
            asBinImage = thumbImage.asBinImage();
            if (asBinImage == null || a.a(thumbImage) > this.THUMB_LIMIT) {
                return this.n.compressThumb(asBinImage);
            }
            return asBinImage;
        }
        asBinImage = a.a(baseMediaObject.getThumbImage(), this.THUMB_LIMIT);
        if (asBinImage != null && asBinImage.length > 0) {
            return asBinImage;
        }
        SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        return b();
    }

    public byte[] objectSetMInAppThumb(BaseMediaObject baseMediaObject) {
        if (baseMediaObject.getThumbImage() == null) {
            return DefaultClass.getBytes();
        }
        byte[] asBinImage;
        if (this.n != null) {
            UMImage thumbImage = baseMediaObject.getThumbImage();
            if (thumbImage == null) {
                return DefaultClass.getBytes();
            }
            asBinImage = thumbImage.asBinImage();
            if (asBinImage == null || a.a(thumbImage) > this.WX_MIN_LIMIT) {
                return this.n.compressThumb(asBinImage);
            }
            return asBinImage;
        }
        asBinImage = a.a(baseMediaObject.getThumbImage().asBinImage(), this.WX_MIN_LIMIT, CompressFormat.JPEG);
        if (asBinImage != null && asBinImage.length > 0) {
            return asBinImage;
        }
        SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        return asBinImage;
    }

    public String getMusicTargetUrl(UMusic uMusic) {
        if (TextUtils.isEmpty(uMusic.getmTargetUrl())) {
            return uMusic.toUrl();
        }
        return uMusic.getmTargetUrl();
    }

    public byte[] getImageThumb(UMImage uMImage) {
        if (uMImage.getThumbImage() == null) {
            return b();
        }
        byte[] a = a.a(uMImage.getThumbImage(), this.WX_THUMB_LIMIT);
        if (a != null && a.length > 0) {
            return a;
        }
        SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        return b();
    }

    private byte[] b() {
        byte[] bytes = DefaultClass.getBytes();
        if (ContextUtil.getIcon() != 0) {
            bytes = a.a(new UMImage(ContextUtil.getContext(), ContextUtil.getIcon()), this.WX_THUMB_LIMIT);
            if (bytes == null || bytes.length <= 0) {
                SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
            }
        }
        return bytes;
    }

    public byte[] getImageData(UMImage uMImage) {
        return uMImage.asBinImage();
    }

    public byte[] getStrictImageData(UMImage uMImage) {
        if (getUMImageScale(uMImage) <= 491520) {
            return getImageData(uMImage);
        }
        byte[] a = a.a(getImage(), 491520);
        if (a != null && a.length > 0) {
            return a;
        }
        SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        return null;
    }

    public int getUMImageScale(UMImage uMImage) {
        return a.a(uMImage);
    }

    public String subString(String str, int i) {
        if (!TextUtils.isEmpty(str) || str.length() <= i) {
            return str;
        }
        return str.substring(0, i);
    }

    public boolean canFileValid(UMImage uMImage) {
        if (uMImage.asFileImage() != null) {
            return true;
        }
        return false;
    }
}
