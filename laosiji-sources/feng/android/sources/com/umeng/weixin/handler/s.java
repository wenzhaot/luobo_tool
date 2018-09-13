package com.umeng.weixin.handler;

import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage.Builder;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.SimpleShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.IMAGE;
import com.umeng.socialize.utils.UmengText.SHARE;
import com.umeng.socialize.utils.UmengText.WX;

public class s extends SimpleShareContent {
    public s(ShareContent shareContent) {
        super(shareContent);
    }

    private Bundle b() {
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 1);
        bundle.putString("_wxobject_description", objectSetText(getText()));
        bundle.putByteArray("_wxobject_thumbdata", null);
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", null);
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString("_wxtextobject_text", getText());
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXTextObject");
        if (TextUtils.isEmpty(getText())) {
            bundle.putString("error", SHARE.EMPTY_TEXT);
        }
        if (getText().length() > 10240) {
            bundle.putString("error", SHARE.LONG_TEXT);
        }
        return bundle;
    }

    private Bundle c() {
        BaseMediaObject umEmoji = getUmEmoji();
        String str = "";
        if (!(umEmoji == null || umEmoji.asFileImage() == null)) {
            str = umEmoji.asFileImage().toString();
        }
        Object obj = "";
        if (umEmoji != null && umEmoji.getThumbImage() == null) {
            SLog.E(WX.WX_NOTHUMB_EMOJ);
        }
        byte[] objectSetThumb = objectSetThumb(umEmoji);
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 8);
        bundle.putString("_wxobject_description", getText());
        bundle.putByteArray("_wxobject_thumbdata", objectSetThumb);
        bundle.putString("_wxemojiobject_emojiPath", str);
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", null);
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXEmojiObject");
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        return bundle;
    }

    private Bundle d() {
        BaseMediaObject music = getMusic();
        Object obj = "";
        String toUrl = TextUtils.isEmpty(music.getmTargetUrl()) ? music.toUrl() : music.getmTargetUrl();
        String toUrl2 = music.toUrl();
        String lowBandDataUrl = !TextUtils.isEmpty(music.getLowBandDataUrl()) ? music.getLowBandDataUrl() : null;
        String lowBandUrl = !TextUtils.isEmpty(music.getLowBandUrl()) ? music.getLowBandUrl() : null;
        String objectSetTitle = objectSetTitle(music);
        String objectSetDescription = objectSetDescription(music);
        byte[] objectSetThumb = objectSetThumb(music);
        if (objectSetThumb == null || objectSetThumb.length <= 0) {
            obj = IMAGE.SHARECONTENT_THUMB_ERROR;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 3);
        bundle.putString("_wxobject_description", objectSetDescription);
        bundle.putByteArray("_wxobject_thumbdata", objectSetThumb);
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", objectSetTitle);
        bundle.putString("_wxmusicobject_musicUrl", toUrl);
        bundle.putString("_wxmusicobject_musicLowBandUrl", lowBandUrl);
        bundle.putString("_wxmusicobject_musicDataUrl", toUrl2);
        bundle.putString("_wxmusicobject_musicLowBandDataUrl", lowBandDataUrl);
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString("_wxtextobject_text", objectSetDescription);
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXMusicObject");
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        return bundle;
    }

    private Bundle e() {
        BaseMediaObject video = getVideo();
        Object obj = "";
        String toUrl = video.toUrl();
        String lowBandUrl = !TextUtils.isEmpty(video.getLowBandUrl()) ? video.getLowBandUrl() : null;
        String objectSetTitle = objectSetTitle(video);
        String objectSetDescription = objectSetDescription(video);
        byte[] objectSetThumb = objectSetThumb(video);
        if (objectSetThumb == null || objectSetThumb.length <= 0) {
            obj = IMAGE.SHARECONTENT_THUMB_ERROR;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 4);
        bundle.putString("_wxobject_description", objectSetDescription);
        bundle.putByteArray("_wxobject_thumbdata", objectSetThumb);
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", objectSetTitle);
        bundle.putString("_wxvideoobject_videoUrl", toUrl);
        bundle.putString("_wxvideoobject_videoLowBandUrl", lowBandUrl);
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString("_wxtextobject_text", objectSetDescription);
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXVideoObject");
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        return bundle;
    }

    private Bundle f() {
        Object obj = "";
        UMImage image = getImage();
        byte[] asBinImage = image.asBinImage();
        String str = "";
        if (canFileValid(image)) {
            str = image.asFileImage().toString();
        } else {
            asBinImage = getStrictImageData(image);
        }
        byte[] imageThumb = getImageThumb(image);
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 2);
        bundle.putString("_wxobject_description", getText());
        bundle.putByteArray("_wxobject_thumbdata", imageThumb);
        if (TextUtils.isEmpty(str)) {
            bundle.putByteArray("_wximageobject_imageData", asBinImage);
            bundle.putString("_wximageobject_imagePath", str);
        } else {
            bundle.putString("_wximageobject_imagePath", str);
            bundle.putByteArray("_wximageobject_imageData", null);
        }
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", null);
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXImageObject");
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        return bundle;
    }

    private Bundle g() {
        Object obj = "";
        BaseMediaObject umWeb = getUmWeb();
        String objectSetTitle = objectSetTitle(umWeb);
        byte[] objectSetThumb = objectSetThumb(umWeb);
        if (objectSetThumb == null || objectSetThumb.length <= 0) {
            SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 5);
        bundle.putString("_wxobject_description", objectSetDescription(umWeb));
        bundle.putByteArray("_wxobject_thumbdata", objectSetThumb);
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxobject_title", objectSetTitle);
        bundle.putString("_wxwebpageobject_webpageUrl", umWeb.toUrl());
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString("_wxtextobject_text", objectSetDescription(umWeb));
        bundle.putString("_wxobject_description", objectSetDescription(umWeb));
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXWebpageObject");
        bundle.putString("_wxwebpageobject_extInfo", null);
        bundle.putString("_wxwebpageobject_canvaspagexml", null);
        if (TextUtils.isEmpty(umWeb.toUrl())) {
            bundle.putString("error", SHARE.EMPTY_WEB_URL);
        }
        if (umWeb.toUrl().length() > 10240) {
            bundle.putString("error", SHARE.LONG_URL);
        }
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        return bundle;
    }

    private Bundle h() {
        Object obj = "";
        BaseMediaObject umMin = getUmMin();
        String objectSetTitle = objectSetTitle(umMin);
        byte[] objectSetMInAppThumb = objectSetMInAppThumb(umMin);
        if (objectSetMInAppThumb == null || objectSetMInAppThumb.length <= 0) {
            SLog.E(IMAGE.SHARECONTENT_THUMB_ERROR);
        }
        Bundle bundle = new Bundle();
        bundle.putInt("_wxobject_sdkVer", 0);
        bundle.putInt("_wxapi_sendmessagetowx_req_media_type", 36);
        Object path = umMin.getPath();
        if (!TextUtils.isEmpty(path)) {
            String[] split = path.split("\\?");
            bundle.putString("_wxminiprogram_path", split.length > 1 ? split[0] + ".html?" + split[1] : split[0] + ".html");
        }
        bundle.putString("_wxobject_description", objectSetDescription(umMin));
        bundle.putByteArray("_wxobject_thumbdata", objectSetMInAppThumb);
        bundle.putInt("_wxminiprogram_type", Config.getMINITYPE());
        bundle.putInt("_wxapi_command_type", 2);
        bundle.putString("_wxminiprogram_username", umMin.getUserName() + "@app");
        bundle.putString("_wxobject_title", objectSetTitle);
        bundle.putString("_wxminiprogram_webpageurl", umMin.toUrl());
        bundle.putString("_wxapi_basereq_openid", null);
        bundle.putString(Builder.KEY_IDENTIFIER, "com.tencent.mm.sdk.openapi.WXMiniProgramObject");
        if (TextUtils.isEmpty(umMin.toUrl())) {
            bundle.putString("error", SHARE.EMPTY_WEB_URL);
        }
        if (umMin.toUrl().length() > 10240) {
            bundle.putString("error", SHARE.LONG_URL);
        }
        if (!TextUtils.isEmpty(obj)) {
            bundle.putString("error", obj);
        }
        if (TextUtils.isEmpty(umMin.getPath())) {
            bundle.putString("error", "UMMin path is null");
        }
        if (TextUtils.isEmpty(umMin.toUrl())) {
            bundle.putString("error", "UMMin url is null");
        }
        return bundle;
    }

    public Bundle a() {
        Bundle f = (getmStyle() == 2 || getmStyle() == 3) ? f() : getmStyle() == 16 ? g() : getmStyle() == 4 ? d() : getmStyle() == 8 ? e() : getmStyle() == 64 ? c() : getmStyle() == 128 ? h() : b();
        f.putString("_wxobject_message_action", null);
        f.putString("_wxobject_message_ext", null);
        f.putString("_wxobject_mediatagname", null);
        return f;
    }
}
