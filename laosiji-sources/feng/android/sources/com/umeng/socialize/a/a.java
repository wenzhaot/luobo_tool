package com.umeng.socialize.a;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.util.SparseArray;
import com.stub.StubApp;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.SocializeException;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.HandlerRequestCode;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.handler.UMMoreHandler;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMVideo;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMusic;
import com.umeng.socialize.net.analytics.SocialAnalytics;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.net.dplus.DplusApi;
import com.umeng.socialize.net.dplus.db.DBManager;
import com.umeng.socialize.net.utils.SocializeNetUtils;
import com.umeng.socialize.utils.ContextUtil;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.AUTH;
import com.umeng.socialize.utils.UmengText.CHECK;
import com.umeng.socialize.utils.UmengText.SHARE;
import com.umeng.socialize.utils.UrlUtil;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: SocialRouter */
public final class a {
    private static final String b = "umeng_share_platform";
    private static final String c = "share_action";
    private SHARE_MEDIA a;
    private String d = "6.9.2";
    private final Map<SHARE_MEDIA, UMSSOHandler> e = new HashMap();
    private final List<Pair<SHARE_MEDIA, String>> f = new ArrayList();
    private a g;
    private Context h;
    private SparseArray<UMAuthListener> i;
    private SparseArray<UMShareListener> j;
    private SparseArray<UMAuthListener> k;

    /* compiled from: SocialRouter */
    private static class a {
        private Map<SHARE_MEDIA, UMSSOHandler> a;

        public a(Map<SHARE_MEDIA, UMSSOHandler> map) {
            this.a = map;
        }

        public boolean a(Context context, SHARE_MEDIA share_media) {
            if (!a(context)) {
                return false;
            }
            if (!a(share_media)) {
                return false;
            }
            if (((UMSSOHandler) this.a.get(share_media)).isSupportAuth()) {
                return true;
            }
            SLog.E(share_media.toString() + AUTH.NOT_SUPPORT_PLATFROM);
            return false;
        }

        public boolean a(ShareAction shareAction) {
            SHARE_MEDIA platform = shareAction.getPlatform();
            if (platform == null) {
                return false;
            }
            if ((platform == SHARE_MEDIA.SINA || platform == SHARE_MEDIA.QQ || platform == SHARE_MEDIA.WEIXIN) && !((Platform) PlatformConfig.configs.get(platform)).isConfigured()) {
                SLog.E(CHECK.noKey(platform));
                return false;
            } else if (a(platform)) {
                return true;
            } else {
                return false;
            }
        }

        private boolean a(Context context) {
            if (context == null) {
                return false;
            }
            return true;
        }

        private boolean a(SHARE_MEDIA share_media) {
            Platform platform = (Platform) PlatformConfig.configs.get(share_media);
            if (((UMSSOHandler) this.a.get(share_media)) != null) {
                return true;
            }
            SLog.mutlE(CHECK.noJar(share_media), UrlUtil.ALL_NO_JAR);
            return false;
        }
    }

    public void a(Context context) {
        this.h = StubApp.getOrigApplicationContext(context.getApplicationContext());
    }

    public a(Context context) {
        List list = this.f;
        String str = "com.umeng.socialize.handler.";
        str = "com.umeng.weixin.handler.";
        str = "com.umeng.qq.handler.";
        list.add(new Pair(SHARE_MEDIA.LAIWANG, "com.umeng.socialize.handler.UMLWHandler"));
        list.add(new Pair(SHARE_MEDIA.LAIWANG_DYNAMIC, "com.umeng.socialize.handler.UMLWHandler"));
        list.add(new Pair(SHARE_MEDIA.SINA, "com.umeng.socialize.handler.SinaSimplyHandler"));
        list.add(new Pair(SHARE_MEDIA.PINTEREST, "com.umeng.socialize.handler.UMPinterestHandler"));
        list.add(new Pair(SHARE_MEDIA.QZONE, "com.umeng.qq.handler.UmengQZoneHandler"));
        list.add(new Pair(SHARE_MEDIA.QQ, "com.umeng.qq.handler.UmengQQHandler"));
        list.add(new Pair(SHARE_MEDIA.RENREN, "com.umeng.socialize.handler.RenrenSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.TENCENT, "com.umeng.socialize.handler.TencentWBSsoHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN, "com.umeng.weixin.handler.UmengWXHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN_CIRCLE, "com.umeng.weixin.handler.UmengWXHandler"));
        list.add(new Pair(SHARE_MEDIA.WEIXIN_FAVORITE, "com.umeng.weixin.handler.UmengWXHandler"));
        list.add(new Pair(SHARE_MEDIA.YIXIN, "com.umeng.socialize.handler.UMYXHandler"));
        list.add(new Pair(SHARE_MEDIA.YIXIN_CIRCLE, "com.umeng.socialize.handler.UMYXHandler"));
        list.add(new Pair(SHARE_MEDIA.EMAIL, "com.umeng.socialize.handler.EmailHandler"));
        list.add(new Pair(SHARE_MEDIA.EVERNOTE, "com.umeng.socialize.handler.UMEvernoteHandler"));
        list.add(new Pair(SHARE_MEDIA.FACEBOOK, "com.umeng.socialize.handler.UMFacebookHandler"));
        list.add(new Pair(SHARE_MEDIA.FACEBOOK_MESSAGER, "com.umeng.socialize.handler.UMFacebookHandler"));
        list.add(new Pair(SHARE_MEDIA.FLICKR, "com.umeng.socialize.handler.UMFlickrHandler"));
        list.add(new Pair(SHARE_MEDIA.FOURSQUARE, "com.umeng.socialize.handler.UMFourSquareHandler"));
        list.add(new Pair(SHARE_MEDIA.GOOGLEPLUS, "com.umeng.socialize.handler.UMGooglePlusHandler"));
        list.add(new Pair(SHARE_MEDIA.INSTAGRAM, "com.umeng.socialize.handler.UMInstagramHandler"));
        list.add(new Pair(SHARE_MEDIA.KAKAO, "com.umeng.socialize.handler.UMKakaoHandler"));
        list.add(new Pair(SHARE_MEDIA.LINE, "com.umeng.socialize.handler.UMLineHandler"));
        list.add(new Pair(SHARE_MEDIA.LINKEDIN, "com.umeng.socialize.handler.UMLinkedInHandler"));
        list.add(new Pair(SHARE_MEDIA.POCKET, "com.umeng.socialize.handler.UMPocketHandler"));
        list.add(new Pair(SHARE_MEDIA.WHATSAPP, "com.umeng.socialize.handler.UMWhatsAppHandler"));
        list.add(new Pair(SHARE_MEDIA.YNOTE, "com.umeng.socialize.handler.UMYNoteHandler"));
        list.add(new Pair(SHARE_MEDIA.SMS, "com.umeng.socialize.handler.SmsHandler"));
        list.add(new Pair(SHARE_MEDIA.DOUBAN, "com.umeng.socialize.handler.DoubanHandler"));
        list.add(new Pair(SHARE_MEDIA.TUMBLR, "com.umeng.socialize.handler.UMTumblrHandler"));
        list.add(new Pair(SHARE_MEDIA.TWITTER, "com.umeng.socialize.handler.TwitterHandler"));
        list.add(new Pair(SHARE_MEDIA.ALIPAY, "com.umeng.socialize.handler.AlipayHandler"));
        list.add(new Pair(SHARE_MEDIA.MORE, "com.umeng.socialize.handler.UMMoreHandler"));
        list.add(new Pair(SHARE_MEDIA.DINGTALK, "com.umeng.socialize.handler.UMDingSSoHandler"));
        list.add(new Pair(SHARE_MEDIA.VKONTAKTE, "com.umeng.socialize.handler.UMVKHandler"));
        list.add(new Pair(SHARE_MEDIA.DROPBOX, "com.umeng.socialize.handler.UMDropBoxHandler"));
        this.g = new a(this.e);
        this.h = null;
        this.i = new SparseArray();
        this.j = new SparseArray();
        this.k = new SparseArray();
        this.h = context;
        b();
    }

    private void b(Context context) {
        String appkey = SocializeUtils.getAppkey(context);
        if (TextUtils.isEmpty(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_NO_APPKEY));
        } else if (SocializeNetUtils.isConSpeCharacters(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_ERROR_APPKEY));
        } else if (SocializeNetUtils.isSelfAppkey(appkey)) {
            throw new SocializeException(UmengText.errorWithUrl(CHECK.APPKEY_NOT_FOUND, UrlUtil.ALL_ERROR_APPKEY));
        }
    }

    private void b() {
        for (Pair pair : this.f) {
            Object obj;
            UMSSOHandler obj2;
            if (pair.first == SHARE_MEDIA.WEIXIN_CIRCLE || pair.first == SHARE_MEDIA.WEIXIN_FAVORITE) {
                obj2 = (UMSSOHandler) this.e.get(SHARE_MEDIA.WEIXIN);
            } else if (pair.first == SHARE_MEDIA.FACEBOOK_MESSAGER) {
                obj2 = (UMSSOHandler) this.e.get(SHARE_MEDIA.FACEBOOK);
            } else if (pair.first == SHARE_MEDIA.YIXIN_CIRCLE) {
                obj2 = (UMSSOHandler) this.e.get(SHARE_MEDIA.YIXIN);
            } else if (pair.first == SHARE_MEDIA.LAIWANG_DYNAMIC) {
                obj2 = (UMSSOHandler) this.e.get(SHARE_MEDIA.LAIWANG);
            } else if (pair.first == SHARE_MEDIA.TENCENT) {
                obj2 = a((String) pair.second);
            } else if (pair.first == SHARE_MEDIA.MORE) {
                obj2 = new UMMoreHandler();
            } else if (pair.first == SHARE_MEDIA.SINA) {
                if (Config.isUmengSina.booleanValue()) {
                    obj2 = a((String) pair.second);
                } else {
                    obj2 = a("com.umeng.socialize.handler.SinaSsoHandler");
                }
            } else if (pair.first == SHARE_MEDIA.WEIXIN) {
                if (Config.isUmengWx.booleanValue()) {
                    obj2 = a((String) pair.second);
                } else {
                    obj2 = a("com.umeng.socialize.handler.UMWXHandler");
                }
            } else if (pair.first == SHARE_MEDIA.QQ) {
                if (Config.isUmengQQ.booleanValue()) {
                    obj2 = a((String) pair.second);
                } else {
                    obj2 = a("com.umeng.socialize.handler.UMQQSsoHandler");
                }
            } else if (pair.first != SHARE_MEDIA.QZONE) {
                obj2 = a((String) pair.second);
            } else if (Config.isUmengQQ.booleanValue()) {
                obj2 = a((String) pair.second);
            } else {
                obj2 = a("com.umeng.socialize.handler.QZoneSsoHandler");
            }
            this.e.put(pair.first, obj2);
        }
    }

    private UMSSOHandler a(String str) {
        UMSSOHandler uMSSOHandler;
        try {
            uMSSOHandler = (UMSSOHandler) Class.forName(str).newInstance();
        } catch (Exception e) {
            uMSSOHandler = null;
        }
        if (uMSSOHandler != null) {
            return uMSSOHandler;
        }
        if (str.contains("SinaSimplyHandler")) {
            Config.isUmengSina = Boolean.valueOf(false);
            return a("com.umeng.socialize.handler.SinaSsoHandler");
        } else if (str.contains("UmengQQHandler")) {
            Config.isUmengQQ = Boolean.valueOf(false);
            return a("com.umeng.socialize.handler.UMQQSsoHandler");
        } else if (str.contains("UmengQZoneHandler")) {
            Config.isUmengQQ = Boolean.valueOf(false);
            return a("com.umeng.socialize.handler.QZoneSsoHandler");
        } else if (!str.contains("UmengWXHandler")) {
            return uMSSOHandler;
        } else {
            Config.isUmengWx = Boolean.valueOf(false);
            return a("com.umeng.socialize.handler.UMWXHandler");
        }
    }

    public UMSSOHandler a(SHARE_MEDIA share_media) {
        UMSSOHandler uMSSOHandler = (UMSSOHandler) this.e.get(share_media);
        if (uMSSOHandler != null) {
            uMSSOHandler.onCreate(this.h, PlatformConfig.getPlatform(share_media));
        }
        return uMSSOHandler;
    }

    public void a(int i, int i2, Intent intent) {
        UMSSOHandler a = a(i);
        if (a != null) {
            a.onActivityResult(i, i2, intent);
        }
    }

    @Deprecated
    public void a(Activity activity, int i, UMAuthListener uMAuthListener) {
        UMSSOHandler a = a(i);
        if (a == null) {
            return;
        }
        if (i == 10103 || i == 11101) {
            a.onCreate(activity, PlatformConfig.getPlatform(b(i)));
            a(SHARE_MEDIA.QQ, uMAuthListener, a, String.valueOf(System.currentTimeMillis()));
        }
    }

    private UMSSOHandler a(int i) {
        int i2 = 10103;
        if (!(i == 10103 || i == 11101)) {
            i2 = i;
        }
        if (i == HandlerRequestCode.FACEBOOK_REQUEST_SHARE_CODE || i == HandlerRequestCode.FACEBOOK_REQUEST_AUTH_CODE || i == HandlerRequestCode.FACEBOOK_REQUEST_SHARE_MESSAGE_CODE) {
            i2 = 64206;
        }
        if (i == HandlerRequestCode.SINA_AUTH_REQUEST_CODE || i == HandlerRequestCode.SINA_SHARE_REQUEST_CODE) {
            i2 = 5659;
        }
        if (i == HandlerRequestCode.SINASSO_REQUEST_CODE) {
            i2 = 5659;
        }
        for (UMSSOHandler uMSSOHandler : this.e.values()) {
            if (uMSSOHandler != null && r1 == uMSSOHandler.getRequestCode()) {
                return uMSSOHandler;
            }
        }
        return null;
    }

    private SHARE_MEDIA b(int i) {
        if (i == 10103 || i == 11101) {
            return SHARE_MEDIA.QQ;
        }
        if (i == HandlerRequestCode.SINA_AUTH_REQUEST_CODE || i == HandlerRequestCode.SINA_SHARE_REQUEST_CODE) {
            return SHARE_MEDIA.SINA;
        }
        return SHARE_MEDIA.QQ;
    }

    public void a(Activity activity, SHARE_MEDIA share_media, UMAuthListener uMAuthListener) {
        if (this.g.a(activity, share_media)) {
            if (uMAuthListener == null) {
                uMAuthListener = new UMAuthListener() {
                    public void onStart(SHARE_MEDIA share_media) {
                    }

                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    }

                    public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                    }

                    public void onCancel(SHARE_MEDIA share_media, int i) {
                    }
                };
            }
            ((UMSSOHandler) this.e.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
            ((UMSSOHandler) this.e.get(share_media)).deleteAuth(uMAuthListener);
        }
    }

    public void b(Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (this.g.a(activity, share_media)) {
            UMSSOHandler uMSSOHandler = (UMSSOHandler) this.e.get(share_media);
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(share_media));
            final String valueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.getInfostart(ContextUtil.getContext(), share_media, valueOf);
            }
            final int ordinal = share_media.ordinal();
            b(ordinal, uMAuthListener);
            UMAuthListener anonymousClass2 = new UMAuthListener() {
                public void onStart(SHARE_MEDIA share_media) {
                    UMAuthListener a = a.this.d(ordinal);
                    if (a != null) {
                        a.onStart(share_media);
                    }
                }

                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                    UMAuthListener a = a.this.d(ordinal);
                    if (a != null) {
                        a.onComplete(share_media, i, map);
                    }
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media, "success", "", valueOf, map);
                    }
                }

                public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                    UMAuthListener a = a.this.d(ordinal);
                    if (a != null) {
                        a.onError(share_media, i, th);
                    }
                    if (th != null) {
                        SLog.E(th.getMessage());
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_AUTHFAIL);
                        SLog.runtimePrint(th.getMessage());
                    } else {
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_AUTHFAIL);
                    }
                    if (ContextUtil.getContext() != null && th != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media, "fail", th.getMessage(), valueOf, null);
                    }
                }

                public void onCancel(SHARE_MEDIA share_media, int i) {
                    UMAuthListener a = a.this.d(ordinal);
                    if (a != null) {
                        a.onCancel(share_media, i);
                    }
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.getInfoendt(ContextUtil.getContext(), share_media, CommonNetImpl.CANCEL, "", valueOf, null);
                    }
                }
            };
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    uMAuthListener.onStart(share_media);
                }
            });
            uMSSOHandler.getPlatformInfo(anonymousClass2);
        }
    }

    public boolean a(Activity activity, SHARE_MEDIA share_media) {
        ((UMSSOHandler) this.e.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.e.get(share_media)).isInstall();
    }

    public boolean b(Activity activity, SHARE_MEDIA share_media) {
        if (!this.g.a(activity, share_media)) {
            return false;
        }
        ((UMSSOHandler) this.e.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.e.get(share_media)).isSupport();
    }

    public String c(Activity activity, SHARE_MEDIA share_media) {
        if (!this.g.a(activity, share_media)) {
            return "";
        }
        ((UMSSOHandler) this.e.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.e.get(share_media)).getSDKVersion();
    }

    public boolean d(Activity activity, SHARE_MEDIA share_media) {
        if (!this.g.a(activity, share_media)) {
            return false;
        }
        ((UMSSOHandler) this.e.get(share_media)).onCreate(activity, PlatformConfig.getPlatform(share_media));
        return ((UMSSOHandler) this.e.get(share_media)).isAuthorize();
    }

    public void c(Activity activity, final SHARE_MEDIA share_media, final UMAuthListener uMAuthListener) {
        if (this.g.a(activity, share_media)) {
            UMSSOHandler uMSSOHandler = (UMSSOHandler) this.e.get(share_media);
            uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(share_media));
            String valueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                SocialAnalytics.authstart(ContextUtil.getContext(), share_media, uMSSOHandler.getSDKVersion(), uMSSOHandler.isInstall(), valueOf);
            }
            int ordinal = share_media.ordinal();
            a(ordinal, uMAuthListener);
            UMAuthListener a = a(ordinal, valueOf, uMSSOHandler.isInstall());
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    uMAuthListener.onStart(share_media);
                }
            });
            uMSSOHandler.authorize(a);
            this.a = share_media;
        }
    }

    private UMAuthListener a(final int i, final String str, final boolean z) {
        return new UMAuthListener() {
            public void onStart(SHARE_MEDIA share_media) {
                UMAuthListener b = a.this.c(i);
                if (b != null) {
                    b.onStart(share_media);
                }
            }

            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                UMAuthListener b = a.this.c(i);
                if (b != null) {
                    b.onComplete(share_media, i, map);
                }
                if (ContextUtil.getContext() != null) {
                    SHARE_MEDIA share_media2 = share_media;
                    SocialAnalytics.authendt(ContextUtil.getContext(), share_media2, "success", z, "", str, a.this.a(share_media, (Map) map));
                }
            }

            public void onError(SHARE_MEDIA share_media, int i, Throwable th) {
                UMAuthListener b = a.this.c(i);
                if (b != null) {
                    b.onError(share_media, i, th);
                }
                if (th != null) {
                    SLog.E(th.getMessage());
                    SLog.runtimePrint(th.getMessage());
                } else {
                    SLog.E("null");
                    SLog.runtimePrint("null");
                }
                if (ContextUtil.getContext() != null && th != null) {
                    SocialAnalytics.authendt(ContextUtil.getContext(), share_media, "fail", z, th.getMessage(), str, null);
                }
            }

            public void onCancel(SHARE_MEDIA share_media, int i) {
                UMAuthListener b = a.this.c(i);
                if (b != null) {
                    b.onCancel(share_media, i);
                }
                if (ContextUtil.getContext() != null) {
                    SocialAnalytics.authendt(ContextUtil.getContext(), share_media, CommonNetImpl.CANCEL, z, "", str, null);
                }
            }
        };
    }

    private Map<String, String> a(SHARE_MEDIA share_media, Map<String, String> map) {
        Object obj = "";
        Object obj2 = "";
        if (PlatformConfig.getPlatform(share_media) != null) {
            obj = PlatformConfig.getPlatform(share_media).getAppid();
            obj2 = PlatformConfig.getPlatform(share_media).getAppSecret();
        }
        map.put("aid", obj);
        map.put("as", obj2);
        return map;
    }

    private void a(ShareAction shareAction) {
        ShareContent shareContent = shareAction.getShareContent();
        ArrayList arrayList = new ArrayList();
        arrayList.add(SHARE.INFO);
        arrayList.add(SHARE.SHAREPLAT + shareAction.getPlatform().toString());
        arrayList.add(SHARE.SHARESTYLE + shareAction.getShareContent().getShareType());
        arrayList.add(SHARE.SHARETEXT + shareContent.mText);
        if (shareContent.mMedia != null) {
            if (shareContent.mMedia instanceof UMImage) {
                UMImage uMImage = (UMImage) shareContent.mMedia;
                if (uMImage.isUrlMedia()) {
                    arrayList.add(SHARE.URLIMAGE + uMImage.asUrlImage());
                } else {
                    byte[] asBinImage = uMImage.asBinImage();
                    arrayList.add(SHARE.LOCALIMAGE + (asBinImage == null ? 0 : asBinImage.length));
                }
                if (uMImage.getThumbImage() != null) {
                    uMImage = uMImage.getThumbImage();
                    if (uMImage.isUrlMedia()) {
                        arrayList.add(SHARE.URLTHUMB + uMImage.asUrlImage());
                    } else {
                        arrayList.add(SHARE.LOCALTHUMB + uMImage.asBinImage().length);
                    }
                }
            }
            if (shareContent.mMedia instanceof UMVideo) {
                UMVideo uMVideo = (UMVideo) shareContent.mMedia;
                arrayList.add(SHARE.VIDEOURL + uMVideo.toUrl());
                arrayList.add(SHARE.VIDEOTITLE + uMVideo.getTitle());
                arrayList.add(SHARE.VIDEODES + uMVideo.getDescription());
                if (uMVideo.getThumbImage() != null) {
                    if (uMVideo.getThumbImage().isUrlMedia()) {
                        arrayList.add(SHARE.URLTHUMB + uMVideo.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(SHARE.LOCALTHUMB + uMVideo.getThumbImage().asBinImage().length);
                    }
                }
            }
            if (shareContent.mMedia instanceof UMusic) {
                UMusic uMusic = (UMusic) shareContent.mMedia;
                arrayList.add(SHARE.MUSICURL + uMusic.toUrl() + "   " + uMusic.getmTargetUrl());
                arrayList.add(SHARE.MUSICTITLE + uMusic.getTitle());
                arrayList.add(SHARE.MUSICDES + uMusic.getDescription());
                if (uMusic.getThumbImage() != null) {
                    if (uMusic.getThumbImage().isUrlMedia()) {
                        arrayList.add(SHARE.URLTHUMB + uMusic.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(SHARE.LOCALTHUMB + uMusic.getThumbImage().asBinImage().length);
                    }
                }
            }
            if (shareContent.mMedia instanceof UMWeb) {
                UMWeb uMWeb = (UMWeb) shareContent.mMedia;
                arrayList.add(SHARE.URLURL + uMWeb.toUrl());
                arrayList.add(SHARE.URLTITLE + uMWeb.getTitle());
                arrayList.add(SHARE.URLDES + uMWeb.getDescription());
                if (uMWeb.getThumbImage() != null) {
                    if (uMWeb.getThumbImage().isUrlMedia()) {
                        arrayList.add(SHARE.URLTHUMB + uMWeb.getThumbImage().asUrlImage());
                    } else {
                        arrayList.add(SHARE.LOCALTHUMB + uMWeb.getThumbImage().asBinImage().length);
                    }
                }
            }
        }
        if (shareContent.file != null) {
            arrayList.add(SHARE.FILENAME + shareContent.file.getName());
        }
        SLog.mutlI((String[]) arrayList.toArray(new String[1]));
    }

    public void a(Activity activity, final ShareAction shareAction, final UMShareListener uMShareListener) {
        b((Context) activity);
        WeakReference weakReference = new WeakReference(activity);
        if (this.g.a(shareAction)) {
            if (SLog.isDebug()) {
                SLog.E(SHARE.VERSION + this.d);
                a(shareAction);
            }
            SHARE_MEDIA platform = shareAction.getPlatform();
            UMSSOHandler uMSSOHandler = (UMSSOHandler) this.e.get(platform);
            uMSSOHandler.onCreate((Context) weakReference.get(), PlatformConfig.getPlatform(platform));
            if (!(platform.toString().equals("TENCENT") || platform.toString().equals("RENREN") || platform.toString().equals("DOUBAN"))) {
                if (platform.toString().equals("WEIXIN")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxsession", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_CIRCLE")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxtimeline", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else if (platform.toString().equals("WEIXIN_FAVORITE")) {
                    SocialAnalytics.log((Context) weakReference.get(), "wxfavorite", shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                } else {
                    SocialAnalytics.log((Context) weakReference.get(), platform.toString().toLowerCase(), shareAction.getShareContent().mText, shareAction.getShareContent().mMedia);
                }
            }
            final String valueOf = String.valueOf(System.currentTimeMillis());
            if (ContextUtil.getContext() != null) {
                boolean z = false;
                if (shareAction.getShareContent().mMedia instanceof UMImage) {
                    z = ((UMImage) shareAction.getShareContent().mMedia).isHasWaterMark();
                }
                DplusApi.uploadShare(ContextUtil.getContext(), shareAction.getShareContent(), uMSSOHandler.isInstall(), platform, valueOf, z);
            }
            final int ordinal = platform.ordinal();
            a(ordinal, uMShareListener);
            final UMShareListener anonymousClass6 = new UMShareListener() {
                public void onStart(SHARE_MEDIA share_media) {
                    UMShareListener c = a.this.e(ordinal);
                    if (c != null) {
                        c.onStart(share_media);
                    }
                }

                public void onResult(SHARE_MEDIA share_media) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, "success", "", valueOf);
                    }
                    UMShareListener c = a.this.e(ordinal);
                    if (c != null) {
                        c.onResult(share_media);
                    }
                }

                public void onError(SHARE_MEDIA share_media, Throwable th) {
                    if (!(ContextUtil.getContext() == null || th == null)) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, "fail", th.getMessage(), valueOf);
                    }
                    UMShareListener c = a.this.e(ordinal);
                    if (c != null) {
                        c.onError(share_media, th);
                    }
                    if (th != null) {
                        SLog.E(th.getMessage());
                        SLog.E(UmengText.SOLVE + UrlUtil.ALL_SHAREFAIL);
                        SLog.runtimePrint(th.getMessage());
                        return;
                    }
                    SLog.E("null");
                    SLog.E(UmengText.SOLVE + UrlUtil.ALL_SHAREFAIL);
                    SLog.runtimePrint("null");
                }

                public void onCancel(SHARE_MEDIA share_media) {
                    if (ContextUtil.getContext() != null) {
                        SocialAnalytics.shareend(ContextUtil.getContext(), share_media, CommonNetImpl.CANCEL, "", valueOf);
                    }
                    UMShareListener c = a.this.e(ordinal);
                    if (c != null) {
                        c.onCancel(share_media);
                    }
                }
            };
            if (shareAction.getUrlValid()) {
                QueuedWork.runInMain(new Runnable() {
                    public void run() {
                        if (uMShareListener != null) {
                            uMShareListener.onStart(shareAction.getPlatform());
                        }
                    }
                });
                try {
                    uMSSOHandler.share(shareAction.getShareContent(), anonymousClass6);
                    return;
                } catch (Throwable th) {
                    SLog.error(th);
                    return;
                }
            }
            QueuedWork.runInMain(new Runnable() {
                public void run() {
                    anonymousClass6.onError(shareAction.getPlatform(), new Throwable(UmengErrorCode.ShareFailed.getMessage() + SHARE.WEB_HTTP));
                }
            });
        }
    }

    private synchronized void a(int i, UMAuthListener uMAuthListener) {
        this.i.put(i, uMAuthListener);
    }

    private synchronized UMAuthListener c(int i) {
        UMAuthListener uMAuthListener;
        this.a = null;
        uMAuthListener = (UMAuthListener) this.i.get(i, null);
        if (uMAuthListener != null) {
            this.i.remove(i);
        }
        return uMAuthListener;
    }

    private synchronized void b(int i, UMAuthListener uMAuthListener) {
        this.k.put(i, uMAuthListener);
    }

    private synchronized UMAuthListener d(int i) {
        UMAuthListener uMAuthListener;
        uMAuthListener = (UMAuthListener) this.k.get(i, null);
        if (uMAuthListener != null) {
            this.k.remove(i);
        }
        return uMAuthListener;
    }

    private synchronized void a(int i, UMShareListener uMShareListener) {
        this.j.put(i, uMShareListener);
    }

    private synchronized UMShareListener e(int i) {
        UMShareListener uMShareListener;
        uMShareListener = (UMShareListener) this.j.get(i, null);
        if (uMShareListener != null) {
            this.j.remove(i);
        }
        return uMShareListener;
    }

    private synchronized void c() {
        this.i.clear();
        this.j.clear();
        this.k.clear();
    }

    private void a(SHARE_MEDIA share_media, UMAuthListener uMAuthListener, UMSSOHandler uMSSOHandler, String str) {
        if (!uMSSOHandler.isHasAuthListener()) {
            int ordinal = share_media.ordinal();
            a(ordinal, uMAuthListener);
            uMSSOHandler.setAuthListener(a(ordinal, str, uMSSOHandler.isInstall()));
        }
    }

    public void a() {
        c();
        com.umeng.socialize.b.b.a.b();
        UMSSOHandler uMSSOHandler = (UMSSOHandler) this.e.get(SHARE_MEDIA.SINA);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        uMSSOHandler = (UMSSOHandler) this.e.get(SHARE_MEDIA.MORE);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        uMSSOHandler = (UMSSOHandler) this.e.get(SHARE_MEDIA.DINGTALK);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        uMSSOHandler = (UMSSOHandler) this.e.get(SHARE_MEDIA.WEIXIN);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        uMSSOHandler = (UMSSOHandler) this.e.get(SHARE_MEDIA.QQ);
        if (uMSSOHandler != null) {
            uMSSOHandler.release();
        }
        this.a = null;
        DBManager.get(ContextUtil.getContext()).closeDatabase();
    }

    public void a(Bundle bundle) {
        String str = "";
        int i = -1;
        if (this.a != null && (this.a == SHARE_MEDIA.WEIXIN || this.a == SHARE_MEDIA.QQ || this.a == SHARE_MEDIA.SINA)) {
            str = this.a.toString();
            i = 0;
        }
        bundle.putString(b, str);
        bundle.putInt(c, i);
        this.a = null;
    }

    public void a(Activity activity, Bundle bundle, UMAuthListener uMAuthListener) {
        if (bundle != null && uMAuthListener != null) {
            Object string = bundle.getString(b, null);
            if (bundle.getInt(c, -1) == 0 && !TextUtils.isEmpty(string)) {
                SHARE_MEDIA convertToEmun = SHARE_MEDIA.convertToEmun(string);
                if (convertToEmun != null) {
                    UMSSOHandler uMSSOHandler;
                    if (convertToEmun == SHARE_MEDIA.QQ) {
                        uMSSOHandler = (UMSSOHandler) this.e.get(convertToEmun);
                        uMSSOHandler.onCreate(activity, PlatformConfig.getPlatform(convertToEmun));
                    } else {
                        uMSSOHandler = a(convertToEmun);
                    }
                    if (uMSSOHandler != null) {
                        a(convertToEmun, uMAuthListener, uMSSOHandler, String.valueOf(System.currentTimeMillis()));
                    }
                }
            }
        }
    }

    public void a(UMShareConfig uMShareConfig) {
        if (this.e != null && !this.e.isEmpty()) {
            for (Entry value : this.e.entrySet()) {
                UMSSOHandler uMSSOHandler = (UMSSOHandler) value.getValue();
                if (uMSSOHandler != null) {
                    uMSSOHandler.setShareConfig(uMShareConfig);
                }
            }
        }
    }
}
