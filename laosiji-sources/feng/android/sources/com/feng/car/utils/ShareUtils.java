package com.feng.car.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.car.CarSeriesInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.library.utils.StringUtil;
import com.stub.StubApp;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import java.text.MessageFormat;

public class ShareUtils {
    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_VIEWPOINT = 4;

    public static void socialShare(Activity activity, SnsInfo snsInfo, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        Runtime.getRuntime().gc();
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if (media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231323);
                return;
            }
        } else if (media == SHARE_MEDIA.SINA && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.SINA)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231324);
            return;
        }
        judgeType(activity, snsInfo, media, umShareListener, type);
    }

    private static void judgeType(Activity activity, SnsInfo snsInfo, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        if (type == 4) {
            shareViewPoint(activity, snsInfo, media, umShareListener, type);
        } else {
            share(activity, snsInfo, media, umShareListener, type);
        }
    }

    private static void shareViewPoint(Activity activity, SnsInfo snsInfo, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        String url;
        String imageUrl;
        if (snsInfo.snstype == 9) {
            url = MessageFormat.format(HttpConstant.SHARE_P_DISCUSS, new Object[]{snsInfo.resourceid + ""});
        } else if (snsInfo.snstype == 10) {
            url = MessageFormat.format(HttpConstant.SHARE_U_DISCUSS, new Object[]{snsInfo.resourceid + ""});
        } else {
            url = "";
        }
        if (snsInfo.list != null && snsInfo.list.size() > 0) {
            imageUrl = shareImageUrl(((SnsPostResources) snsInfo.list.get(0)).image);
        } else if (StringUtil.isEmpty(snsInfo.discussinfo.image.url)) {
            imageUrl = HttpConstant.SHARE_DEFAULT_PIC;
        } else {
            imageUrl = shareImageUrl(snsInfo.discussinfo.image);
        }
        UMImage image = new UMImage((Context) activity, imageUrl);
        ShareAction action = new ShareAction(activity);
        action.setPlatform(media);
        if (umShareListener != null) {
            action.setCallback(umShareListener);
        }
        UMWeb umWeb = new UMWeb(url);
        umWeb.setThumb(image);
        StringBuffer sb = new StringBuffer();
        if (media == SHARE_MEDIA.SINA) {
            sb.append("听 ").append((String) snsInfo.user.name.get()).append(" 聊聊");
            sb.append(snsInfo.discussinfo.title);
            sb.append("，详细阅读点这里→");
            sb.append(url);
            action.withText(sb.toString());
            if (StringUtil.isEmpty(snsInfo.discussinfo.title)) {
                image.setTitle("分享来自老司机-有趣的汽车社区");
            } else {
                image.setTitle(snsInfo.discussinfo.title);
            }
            if (StringUtil.isEmpty(snsInfo.discussinfo.title)) {
                image.setDescription("分享来自老司机-有趣的汽车社区");
            } else {
                image.setDescription(snsInfo.discussinfo.title);
            }
        } else if (media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) {
            umWeb.setTitle("听 " + ((String) snsInfo.user.name.get()) + " 聊聊" + snsInfo.discussinfo.title);
            if (StringUtil.isEmpty(snsInfo.getQuoteDescription())) {
                action.withText("我发布了观点");
            } else {
                action.withText(snsInfo.getQuoteDescription());
            }
            if (StringUtil.isEmpty(snsInfo.getQuoteDescription())) {
                umWeb.setDescription("我发布了观点");
            } else {
                umWeb.setDescription(snsInfo.getQuoteDescription());
            }
        } else if (media == SHARE_MEDIA.WEIXIN) {
            umWeb.setTitle("听 " + ((String) snsInfo.user.name.get()) + " 聊聊" + snsInfo.discussinfo.title);
            if (StringUtil.isEmpty(snsInfo.getQuoteDescription())) {
                umWeb.setDescription("我发布了观点");
            } else {
                umWeb.setDescription(snsInfo.getQuoteDescription());
            }
            action.withText("分享自老司机-有趣的汽车社区");
        } else if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            umWeb.setTitle("听 " + ((String) snsInfo.user.name.get()) + " 聊聊" + snsInfo.discussinfo.title);
        }
        if (media == SHARE_MEDIA.SINA) {
            action.withMedia(image).share();
        } else {
            action.withMedia(umWeb).share();
        }
    }

    private static void share(Activity activity, SnsInfo mSnsInfo, SHARE_MEDIA media, UMShareListener umShareListener, int type) {
        String url;
        String imageUrl = "";
        if (type != 1) {
            url = "";
        } else if (FengApplication.getInstance().isLoginUser()) {
            url = MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{mSnsInfo.resourceid + ""}) + "?shareuid=" + FengApplication.getInstance().getUserInfo().id;
        } else {
            url = MessageFormat.format(HttpConstant.SHARE_POST, new Object[]{mSnsInfo.resourceid + ""});
        }
        if (mSnsInfo.snstype != 0 && mSnsInfo.snstype != 1) {
            imageUrl = HttpConstant.SHARE_DEFAULT_PIC;
        } else if (!TextUtils.isEmpty(mSnsInfo.image.url)) {
            imageUrl = shareImageUrl(mSnsInfo.image);
        } else if (TextUtils.isEmpty(mSnsInfo.user.getHeadImageInfo().url)) {
            imageUrl = HttpConstant.SHARE_DEFAULT_PIC;
        } else {
            imageUrl = mSnsInfo.user.getHeadImageInfo().url;
        }
        UMImage image = new UMImage((Context) activity, imageUrl);
        ShareAction action = new ShareAction(activity);
        action.setPlatform(media);
        if (umShareListener != null) {
            action.setCallback(umShareListener);
        }
        UMWeb umWeb = new UMWeb(url);
        umWeb.setThumb(image);
        StringBuffer sb = new StringBuffer();
        if (type == 1) {
            if (media == SHARE_MEDIA.SINA) {
                sb.append(mSnsInfo.getShareTitleOrDes());
                sb.append("，详情戳这里→");
                sb.append(url);
                action.withText(sb.toString());
                if (StringUtil.isEmpty((String) mSnsInfo.title.get())) {
                    image.setTitle("分享来自老司机-有趣的汽车社区");
                } else {
                    image.setTitle((String) mSnsInfo.title.get());
                }
                if (StringUtil.isEmpty(mSnsInfo.getQuoteDescription())) {
                    image.setDescription("分享来自老司机-有趣的汽车社区");
                } else {
                    image.setDescription(mSnsInfo.getQuoteDescription());
                }
            } else if (media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) {
                umWeb.setTitle(mSnsInfo.getShareTitleOrDes());
                action.withText("分享自老司机-有趣的汽车社区");
                umWeb.setDescription("分享自老司机-有趣的汽车社区");
            } else if (media == SHARE_MEDIA.WEIXIN) {
                umWeb.setTitle(mSnsInfo.getShareTitleOrDes());
                umWeb.setDescription("分享自老司机-有趣的汽车社区");
                action.withText("分享自老司机-有趣的汽车社区");
            } else if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {
                umWeb.setTitle(mSnsInfo.getShareTitleOrDes());
            }
        }
        if (media == SHARE_MEDIA.SINA) {
            action.withMedia(image).share();
        } else {
            action.withMedia(umWeb).share();
        }
    }

    public static void sharePopularProgramToWechat(Activity activity, String programName, int hotShowId, String imageUrl, UMShareListener umShareListener) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            new Dialog(activity).setTitle(2131230745);
            UMImage image = new UMImage((Context) activity, imageUrl);
            ShareAction action = new ShareAction(activity);
            action.setPlatform(SHARE_MEDIA.WEIXIN);
            if (umShareListener != null) {
                action.setCallback(umShareListener);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("【热门节目】").append(programName);
            String des = "分享自老司机-有趣的汽车社区";
            action.withText(des);
            UMWeb umWeb = new UMWeb(getHotShowShareUrl(hotShowId));
            umWeb.setThumb(image);
            umWeb.setTitle(sb.toString());
            umWeb.setDescription(des);
            action.withMedia(umWeb).share();
            return;
        }
        ((BaseActivity) activity).showThirdTypeToast(2131231325);
    }

    public static void sharePopularProgramToFriends(Activity activity, String programName, int hotShowId, String imageUrl, UMShareListener umShareListener) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
            new Dialog(activity).setTitle(2131230745);
            UMImage image = new UMImage((Context) activity, imageUrl);
            ShareAction action = new ShareAction(activity);
            action.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE);
            if (umShareListener != null) {
                action.setCallback(umShareListener);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("【热门节目】").append(programName);
            UMWeb umWeb = new UMWeb(getHotShowShareUrl(hotShowId));
            umWeb.setThumb(image);
            umWeb.setTitle(sb.toString());
            action.withMedia(umWeb).share();
            return;
        }
        ((BaseActivity) activity).showThirdTypeToast(2131231325);
    }

    public static void sharePopularProgramToWeibo(Activity activity, String programName, int hotShowId, String imageUrl, UMShareListener umShareListener) {
        new Dialog(activity).setTitle(2131230745);
        ShareAction action = new ShareAction(activity);
        action.setPlatform(SHARE_MEDIA.SINA);
        if (umShareListener != null) {
            action.setCallback(umShareListener);
        }
        UMImage image = new UMImage((Context) activity, imageUrl);
        StringBuffer sb = new StringBuffer();
        sb.append("【热门节目】").append(programName).append("，详细阅读点这里→").append(getHotShowShareUrl(hotShowId));
        action.withText(sb.toString());
        image.setTitle(sb.toString());
        image.setDescription(" ");
        action.withMedia(image).share();
    }

    public static void sharePopularProgramToQQ(Activity activity, String programName, int hotShowId, String imageUrl, UMShareListener umShareListener) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            new Dialog(activity).setTitle(2131230745);
            UMImage image = new UMImage((Context) activity, imageUrl);
            ShareAction action = new ShareAction(activity);
            action.setPlatform(SHARE_MEDIA.QQ);
            if (umShareListener != null) {
                action.setCallback(umShareListener);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("【热门节目】").append(programName);
            String des = "分享自老司机-有趣的汽车社区";
            action.withText(des);
            UMWeb umWeb = new UMWeb(getHotShowShareUrl(hotShowId));
            umWeb.setThumb(image);
            umWeb.setTitle(sb.toString());
            umWeb.setDescription(des);
            action.withMedia(umWeb).share();
            return;
        }
        ((BaseActivity) activity).showThirdTypeToast(2131231323);
    }

    public static void sharePopularProgramToQZone(Activity activity, String programName, int hotShowId, String imageUrl, UMShareListener umShareListener) {
        if (UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QZONE)) {
            new Dialog(activity).setTitle(2131230745);
            UMImage image = new UMImage((Context) activity, imageUrl);
            ShareAction action = new ShareAction(activity);
            action.setPlatform(SHARE_MEDIA.QZONE);
            if (umShareListener != null) {
                action.setCallback(umShareListener);
            }
            StringBuffer sb = new StringBuffer();
            sb.append("【热门节目】").append(programName);
            String des = "分享自老司机-有趣的汽车社区";
            action.withText(des);
            UMWeb umWeb = new UMWeb(getHotShowShareUrl(hotShowId));
            umWeb.setThumb(image);
            umWeb.setTitle(sb.toString());
            umWeb.setDescription(des);
            action.withMedia(umWeb).share();
            return;
        }
        ((BaseActivity) activity).showThirdTypeToast(2131231323);
    }

    public static void shareChooseCarFinal(Activity activity, SHARE_MEDIA media, int chooseCarId, String shareContent, String content, String imageUrl, UMShareListener umShareListener) {
        UMImage image = new UMImage((Context) activity, imageUrl);
        ShareAction action = new ShareAction(activity);
        action.setPlatform(media);
        if (umShareListener != null) {
            action.setCallback(umShareListener);
        }
        String shareTitle;
        if (media == SHARE_MEDIA.SINA) {
            shareTitle = shareContent + "，详细阅读点这里→" + getChooseCarShareUrl(chooseCarId);
            action.withText(shareTitle);
            image.setTitle(shareTitle);
            image.setDescription(" ");
            action.withMedia(image).share();
            return;
        }
        shareTitle = shareContent;
        UMWeb umWeb = new UMWeb(getChooseCarShareUrl(chooseCarId));
        umWeb.setThumb(image);
        umWeb.setTitle(shareTitle);
        umWeb.setDescription(content);
        action.withMedia(umWeb).share();
    }

    public static void ChooseCarFinalShare(Activity activity, SHARE_MEDIA media, int chooseCarId, String shareContent, String content, String imageUrl, UMShareListener umShareListener) {
        UMShareAPI mShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if ((media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231323);
            return;
        }
        shareChooseCarFinal(activity, media, chooseCarId, shareContent, content, imageUrl, umShareListener);
    }

    public static void shareCircleFinalPage(Activity activity, SHARE_MEDIA media, int circleType, int circleId, String circleName, int attendNum, int contentNum, String imageUrl, UMShareListener umShareListener) {
        UMImage image = new UMImage((Context) activity, imageUrl);
        ShareAction action = new ShareAction(activity);
        action.setPlatform(media);
        if (umShareListener != null) {
            action.setCallback(umShareListener);
        }
        String title = "快来看看关于「" + circleName + "」，大家都在聊些什么吧！";
        String content = attendNum + "人加入" + contentNum + "条内容";
        String shareTitle;
        if (media == SHARE_MEDIA.SINA) {
            shareTitle = title + "，" + content + "，详细阅读点这里→" + getCirclePageShareUrl(circleType, circleId);
            action.withText(shareTitle);
            image.setTitle(shareTitle);
            image.setDescription(" ");
            action.withMedia(image).share();
            return;
        }
        shareTitle = title;
        UMWeb umWeb = new UMWeb(getCirclePageShareUrl(circleType, circleId));
        umWeb.setThumb(image);
        umWeb.setTitle(shareTitle);
        umWeb.setDescription(content);
        action.withMedia(umWeb).share();
    }

    public static void CircleFinalPageShare(Activity activity, SHARE_MEDIA media, int circleType, int circleId, String circleName, int attendNum, int contentNum, String imageUrl, UMShareListener umShareListener) {
        UMShareAPI mShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if ((media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231323);
            return;
        }
        shareCircleFinalPage(activity, media, circleType, circleId, circleName, attendNum, contentNum, imageUrl, umShareListener);
    }

    public static String getCirclePageShareUrl(int circleType, int circleId) {
        return MessageFormat.format(HttpConstant.SHARE_CIRCLE, new Object[]{circleType + "", circleId + ""});
    }

    public static String getHotShowShareUrl(int hotShowId) {
        return MessageFormat.format(HttpConstant.SHARE_PROGRAM, new Object[]{hotShowId + ""});
    }

    public static String getChooseCarShareUrl(int chooseCarId) {
        return MessageFormat.format(HttpConstant.SHARE_CHOOSE_CAR, new Object[]{chooseCarId + ""});
    }

    public static void shareScreenshotBitmap(Activity activity, Bitmap bitmap, SHARE_MEDIA media, UMShareListener umShareListener) {
        UMShareAPI mShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if ((media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231323);
            return;
        }
        final Activity activity2;
        final Bitmap bitmap2;
        final SHARE_MEDIA share_media;
        final UMShareListener uMShareListener;
        if (media == SHARE_MEDIA.SINA) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.SINA)) {
                activity2 = activity;
                bitmap2 = bitmap;
                share_media = media;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.SINA, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareScreenshot(activity2, bitmap2, share_media, uMShareListener);
                    }

                    public void onAuthStart() {
                    }

                    public void onAuthCancel() {
                    }

                    public void onAuthError() {
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QQ) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.QQ)) {
                activity2 = activity;
                bitmap2 = bitmap;
                share_media = media;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareScreenshot(activity2, bitmap2, share_media, uMShareListener);
                    }

                    public void onAuthStart() {
                    }

                    public void onAuthCancel() {
                    }

                    public void onAuthError() {
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QZONE) {
            shareScreenshot(activity, bitmap, media, umShareListener);
            return;
        }
        shareScreenshot(activity, bitmap, media, umShareListener);
    }

    private static void shareScreenshot(Activity activity, Bitmap bitmap, SHARE_MEDIA media, UMShareListener umShareListener) {
        UMImage image = new UMImage((Context) activity, bitmap);
        if (media == SHARE_MEDIA.SINA) {
            new ShareAction(activity).setPlatform(media).withMedia(image).withText("（分享来自老司机-有趣的汽车社区）").setCallback(umShareListener).share();
        } else {
            new ShareAction(activity).setPlatform(media).withMedia(image).setCallback(umShareListener).share();
        }
    }

    private static String shareImageUrl(ImageInfo imageInfo) {
        if (TextUtils.isEmpty(imageInfo.url)) {
            return "";
        }
        if (imageInfo.mimetype == 4) {
            return imageInfo.url + "?imageView2/2/w" + 512 + FengConstant.GIF_SUFFIX;
        }
        return imageInfo.url + "?imageView2/2/w/" + 512;
    }

    public static void shareLSJMine(Activity activity, SHARE_MEDIA media, UMShareListener umShareListener) {
        UMShareAPI mShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if ((media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231323);
            return;
        }
        final Dialog mShareDialog = new Dialog(activity);
        mShareDialog.setTitle(2131230745);
        final Activity activity2;
        final SHARE_MEDIA share_media;
        final UMShareListener uMShareListener;
        if (media == SHARE_MEDIA.SINA) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.SINA)) {
                activity2 = activity;
                share_media = media;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.SINA, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareLSJ(activity2, share_media, uMShareListener);
                        mShareDialog.dismiss();
                    }

                    public void onAuthStart() {
                        mShareDialog.show();
                    }

                    public void onAuthCancel() {
                        mShareDialog.dismiss();
                    }

                    public void onAuthError() {
                        mShareDialog.dismiss();
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QQ) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.QQ)) {
                activity2 = activity;
                share_media = media;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareLSJ(activity2, share_media, uMShareListener);
                        mShareDialog.dismiss();
                    }

                    public void onAuthStart() {
                        mShareDialog.show();
                    }

                    public void onAuthCancel() {
                        mShareDialog.dismiss();
                    }

                    public void onAuthError() {
                        mShareDialog.dismiss();
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QZONE) {
            shareLSJ(activity, media, umShareListener);
            return;
        }
        shareLSJ(activity, media, umShareListener);
    }

    private static void shareLSJ(Activity activity, SHARE_MEDIA media, UMShareListener umShareListener) {
        UMImage image = new UMImage((Context) activity, 2130838469);
        if (media == SHARE_MEDIA.SINA) {
            new ShareAction(activity).setPlatform(media).withText("「看车·选车·买车·聊车」就上老司机App。点击下载→http://m.laosiji.com/html/app.html").withMedia(image).setCallback(umShareListener).share();
            return;
        }
        if (media == SHARE_MEDIA.WEIXIN) {
            image.setThumb(new UMImage((Context) activity, 2130838470));
        }
        new ShareAction(activity).setPlatform(media).withMedia(image).setCallback(umShareListener).share();
    }

    public static void shareCarSeries(Activity activity, SHARE_MEDIA media, CarSeriesInfo seriesInfo, String imageUrl, UMShareListener umShareListener) {
        UMShareAPI mShareAPI = UMShareAPI.get(StubApp.getOrigApplicationContext(activity.getApplicationContext()));
        if (media == SHARE_MEDIA.WEIXIN || media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            if (!UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.WEIXIN)) {
                ((BaseActivity) activity).showThirdTypeToast(2131231325);
                return;
            }
        } else if ((media == SHARE_MEDIA.QQ || media == SHARE_MEDIA.QZONE) && !UMShareAPI.get(activity).isInstall(activity, SHARE_MEDIA.QQ)) {
            ((BaseActivity) activity).showThirdTypeToast(2131231323);
            return;
        }
        final Dialog mShareDialog = new Dialog(activity);
        mShareDialog.setTitle(2131230745);
        final Activity activity2;
        final SHARE_MEDIA share_media;
        final CarSeriesInfo carSeriesInfo;
        final String str;
        final UMShareListener uMShareListener;
        if (media == SHARE_MEDIA.SINA) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.SINA)) {
                activity2 = activity;
                share_media = media;
                carSeriesInfo = seriesInfo;
                str = imageUrl;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.SINA, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareSeries(activity2, share_media, carSeriesInfo, str, uMShareListener);
                        mShareDialog.dismiss();
                    }

                    public void onAuthStart() {
                        mShareDialog.show();
                    }

                    public void onAuthCancel() {
                        mShareDialog.dismiss();
                    }

                    public void onAuthError() {
                        mShareDialog.dismiss();
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QQ) {
            if (!mShareAPI.isAuthorize(activity, SHARE_MEDIA.QQ)) {
                activity2 = activity;
                share_media = media;
                carSeriesInfo = seriesInfo;
                str = imageUrl;
                uMShareListener = umShareListener;
                mShareAPI.doOauthVerify(activity, SHARE_MEDIA.QQ, new MyUmengAuthImpl(activity) {
                    public void onAuthComplete() {
                        ShareUtils.shareSeries(activity2, share_media, carSeriesInfo, str, uMShareListener);
                        mShareDialog.dismiss();
                    }

                    public void onAuthStart() {
                        mShareDialog.show();
                    }

                    public void onAuthCancel() {
                        mShareDialog.dismiss();
                    }

                    public void onAuthError() {
                        mShareDialog.dismiss();
                    }
                });
                return;
            }
        } else if (media == SHARE_MEDIA.QZONE) {
            shareSeries(activity, media, seriesInfo, imageUrl, umShareListener);
            return;
        }
        shareSeries(activity, media, seriesInfo, imageUrl, umShareListener);
    }

    private static void shareSeries(Activity activity, SHARE_MEDIA media, CarSeriesInfo seriesInfo, String imageUrl, UMShareListener umShareListener) {
        String url = MessageFormat.format(HttpConstant.SHARE_SERIES, new Object[]{seriesInfo.id + ""});
        UMImage image = new UMImage((Context) activity, imageUrl);
        UMWeb umWeb;
        if (media == SHARE_MEDIA.SINA) {
            new ShareAction(activity).setPlatform(media).withText("老司机  - " + ((String) seriesInfo.name.get()) + "，详细阅读点这里→" + url).withMedia(image).setCallback(umShareListener).share();
        } else if (media == SHARE_MEDIA.WEIXIN_CIRCLE) {
            umWeb = new UMWeb(url);
            umWeb.setTitle("老司机  - " + ((String) seriesInfo.name.get()));
            umWeb.setThumb(image);
            new ShareAction(activity).setPlatform(media).withMedia(umWeb).setCallback(umShareListener).share();
        } else {
            umWeb = new UMWeb(url);
            umWeb.setThumb(image);
            umWeb.setTitle("老司机  - " + ((String) seriesInfo.name.get()));
            umWeb.setDescription("分享自老司机 - 有趣的汽车社区");
            new ShareAction(activity).setPlatform(media).withMedia(umWeb).setCallback(umShareListener).share();
        }
    }
}
