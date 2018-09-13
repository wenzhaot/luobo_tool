package com.umeng.weixin.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.tencent.mm.opensdk.constants.Build;
import com.umeng.commonsdk.proguard.g;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig.APPIDPlatform;
import com.umeng.socialize.PlatformConfig.Platform;
import com.umeng.socialize.ShareContent;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.StringName;
import com.umeng.socialize.bean.UmengErrorCode;
import com.umeng.socialize.common.QueuedWork;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.handler.UMSSOHandler;
import com.umeng.socialize.media.UMMin;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText;
import com.umeng.socialize.utils.UmengText.AUTH;
import com.umeng.socialize.utils.UmengText.SHARE;
import com.umeng.socialize.utils.UrlUtil;
import com.umeng.weixin.umengwx.WeChat;
import com.umeng.weixin.umengwx.a;
import com.umeng.weixin.umengwx.e;
import com.umeng.weixin.umengwx.h;
import com.umeng.weixin.umengwx.i;
import com.umeng.weixin.umengwx.k;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public class UmengWXHandler extends UMSSOHandler {
    private static final int b = 0;
    private static final int c = 1;
    private static final int d = 2;
    private static final int k = 604800;
    private static final int l = 1;
    private static final int m = 2;
    private static String o = "snsapi_userinfo,snsapi_friend,snsapi_message";
    private static final String p = "refresh_token_expires";
    private static final String q = "nickname";
    private static final String r = "language";
    private static final String s = "headimgurl";
    private static final String t = "sex";
    private static final String u = "privilege";
    private static final String v = "errcode";
    private static final String w = "errmsg";
    private static final String x = "40001";
    private static final String y = "40030";
    private static final String z = "42002";
    private e A = new l(this);
    private q a;
    private WeChat e;
    private String f = "6.9.2";
    private s g;
    private APPIDPlatform h;
    private SHARE_MEDIA i = SHARE_MEDIA.WEIXIN;
    private UMAuthListener j;
    private UMShareListener n;

    private int a() {
        int i = 0;
        if (!isInstall()) {
            return i;
        }
        try {
            return getContext().getPackageManager().getApplicationInfo("com.tencent.mm", 128).metaData.getInt("com.tencent.mm.BuildInfo.OPEN_SDK_VERSION", 0);
        } catch (Exception e) {
            return i;
        }
    }

    private ShareContent a(ShareContent shareContent) {
        if (shareContent.getShareType() == 128 && a() < Build.MINIPROGRAM_SUPPORTED_SDK_INT) {
            UMMin uMMin = (UMMin) shareContent.mMedia;
            UMediaObject uMWeb = new UMWeb(uMMin.toUrl());
            uMWeb.setThumb(uMMin.getThumbImage());
            uMWeb.setDescription(uMMin.getDescription());
            uMWeb.setTitle(uMMin.getTitle());
            shareContent.mMedia = uMWeb;
        }
        return shareContent;
    }

    private void a(Bundle bundle) {
        if (this.a != null) {
            this.a.a(bundle).k();
        }
    }

    private void a(UMAuthListener uMAuthListener) {
        String g = g();
        String i = i();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.weixin.qq.com/sns/userinfo?access_token=");
        stringBuilder.append(i).append("&openid=").append(g);
        stringBuilder.append("&lang=zh_CN");
        g = r.a(stringBuilder.toString());
        if (TextUtils.isEmpty(g) || g.startsWith("##")) {
            QueuedWork.runInMain(new b(this, uMAuthListener, g));
            return;
        }
        Map e = e(g);
        if (e == null) {
            QueuedWork.runInMain(new c(this, uMAuthListener, g));
        } else if (!e.containsKey(v)) {
            QueuedWork.runInMain(new e(this, uMAuthListener, e));
        } else if (((String) e.get(v)).equals(x)) {
            d();
            authorize(uMAuthListener);
        } else {
            QueuedWork.runInMain(new d(this, uMAuthListener, e));
        }
    }

    private void a(i iVar) {
        if (iVar.a == 0) {
            a(iVar.e, this.j);
        } else if (iVar.a == -2) {
            getAuthListener(this.j).onCancel(SHARE_MEDIA.WEIXIN, 0);
        } else if (iVar.a == -6) {
            getAuthListener(this.j).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + UmengText.errorWithUrl(AUTH.AUTH_DENIED, UrlUtil.WX_ERROR_SIGN)));
        } else {
            getAuthListener(this.j).onError(SHARE_MEDIA.WEIXIN, 0, new Throwable(UmengErrorCode.AuthorizeFailed.getMessage() + TextUtils.concat(new CharSequence[]{"weixin auth error (", String.valueOf(iVar.a), "):", iVar.b}).toString()));
        }
    }

    private void a(k kVar) {
        switch (kVar.a) {
            case -6:
                getShareListener(this.n).onError(this.i, new Throwable(UmengErrorCode.ShareFailed.getMessage() + UmengText.errorWithUrl(AUTH.AUTH_DENIED, UrlUtil.WX_ERROR_SIGN)));
                return;
            case -5:
                getShareListener(this.n).onError(this.i, new Throwable(UmengErrorCode.ShareFailed.getMessage() + SHARE.VERSION_NOT_SUPPORT));
                return;
            case -3:
            case -1:
                getShareListener(this.n).onError(this.i, new Throwable(UmengErrorCode.ShareFailed.getMessage() + kVar.b));
                return;
            case -2:
                getShareListener(this.n).onCancel(this.i);
                return;
            case 0:
                getShareListener(this.n).onResult(this.i);
                return;
            default:
                getShareListener(this.n).onError(this.i, new Throwable(UmengErrorCode.ShareFailed.getMessage() + kVar.b));
                return;
        }
    }

    private void a(String str) {
        a(d(r.a(str)));
    }

    private void a(String str, UMAuthListener uMAuthListener) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
        stringBuilder.append("appid=").append(this.h.appId);
        stringBuilder.append("&secret=").append(this.h.appkey);
        stringBuilder.append("&code=").append(str);
        stringBuilder.append("&grant_type=authorization_code");
        QueuedWork.runInBack(new m(this, stringBuilder, uMAuthListener), true);
    }

    private boolean a(s sVar) {
        Bundle a = sVar.a();
        a.putString("_wxapi_basereq_transaction", c(this.g.getStrStyle()));
        if (TextUtils.isEmpty(a.getString("error"))) {
            switch (f.a[this.i.ordinal()]) {
                case 1:
                    a.putInt("_wxapi_sendmessagetowx_req_scene", 0);
                    break;
                case 2:
                    a.putInt("_wxapi_sendmessagetowx_req_scene", 1);
                    break;
                case 3:
                    a.putInt("_wxapi_sendmessagetowx_req_scene", 2);
                    break;
                default:
                    a.putInt("_wxapi_sendmessagetowx_req_scene", 2);
                    break;
            }
            this.e.pushare(a);
            return true;
        }
        QueuedWork.runInMain(new h(this, a));
        return false;
    }

    private Map b(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://api.weixin.qq.com/sns/oauth2/refresh_token?");
        stringBuilder.append("appid=").append(this.h.appId);
        stringBuilder.append("&grant_type=refresh_token");
        stringBuilder.append("&refresh_token=").append(str);
        String a = r.a(stringBuilder.toString());
        Map map = null;
        try {
            map = SocializeUtils.jsonToMap(a);
            map.put(CommonNetImpl.UNIONID, h());
            return map;
        } catch (Exception e) {
            return map;
        }
    }

    private boolean b() {
        return this.a != null ? this.a.h() : false;
    }

    private String c(String str) {
        return str == null ? String.valueOf(System.currentTimeMillis()) : str + System.currentTimeMillis();
    }

    private boolean c() {
        return this.a != null ? this.a.e() : false;
    }

    private Bundle d(String str) {
        Bundle bundle = new Bundle();
        if (TextUtils.isEmpty(str)) {
            return bundle;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            String str2 = "";
            while (keys.hasNext()) {
                str2 = (String) keys.next();
                bundle.putString(str2, jSONObject.optString(str2));
            }
            bundle.putLong(p, 604800);
            bundle.putString("accessToken", bundle.getString("access_token"));
            bundle.putString("expiration", bundle.getString("expires_in"));
            bundle.putString("refreshToken", bundle.getString("refresh_token"));
            bundle.putString("uid", bundle.getString(CommonNetImpl.UNIONID));
        } catch (Throwable e) {
            SLog.error(e);
        }
        return bundle;
    }

    private void d() {
        if (this.a != null) {
            this.a.j();
        }
    }

    private String e() {
        return this.a != null ? this.a.c() : "";
    }

    private Map e(String str) {
        Map hashMap = new HashMap();
        try {
            JSONObject jSONObject = new JSONObject(str);
            if (jSONObject.has(v)) {
                hashMap.put(v, jSONObject.getString(v));
                hashMap.put(w, jSONObject.getString(w));
                return hashMap;
            }
            hashMap.put("openid", jSONObject.optString("openid"));
            hashMap.put("screen_name", jSONObject.optString("nickname"));
            hashMap.put("name", jSONObject.optString("nickname"));
            hashMap.put("language", jSONObject.optString("language"));
            hashMap.put("city", jSONObject.optString("city"));
            hashMap.put("province", jSONObject.optString("province"));
            hashMap.put(g.N, jSONObject.optString(g.N));
            hashMap.put("profile_image_url", jSONObject.optString(s));
            hashMap.put("iconurl", jSONObject.optString(s));
            hashMap.put(CommonNetImpl.UNIONID, jSONObject.optString(CommonNetImpl.UNIONID));
            hashMap.put("uid", jSONObject.optString(CommonNetImpl.UNIONID));
            hashMap.put("gender", getGender(jSONObject.optString("sex")));
            JSONArray jSONArray = jSONObject.getJSONArray(u);
            int length = jSONArray.length();
            if (length > 0) {
                Object obj = new String[length];
                for (int i = 0; i < length; i++) {
                    obj[i] = jSONArray.get(i).toString();
                }
                hashMap.put(u, obj.toString());
            }
            hashMap.put("access_token", i());
            hashMap.put("refreshToken", e());
            hashMap.put("expires_in", String.valueOf(j()));
            hashMap.put("accessToken", i());
            hashMap.put("refreshToken", e());
            hashMap.put("expiration", String.valueOf(j()));
            return hashMap;
        } catch (Throwable e) {
            SLog.error(e);
            return Collections.emptyMap();
        }
    }

    private Map f() {
        return this.a != null ? this.a.d() : null;
    }

    private String g() {
        return this.a != null ? this.a.b() : "";
    }

    private String h() {
        return this.a != null ? this.a.a() : "";
    }

    private String i() {
        return this.a != null ? this.a.f() : "";
    }

    private long j() {
        return this.a != null ? this.a.g() : 0;
    }

    public void authorize(UMAuthListener uMAuthListener) {
        this.j = uMAuthListener;
        this.i = this.h.getName();
        if (!isInstall()) {
            if (Config.isJumptoAppStore) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(SocializeConstants.DOWN_URL_WX));
                ((Activity) this.mWeakAct.get()).startActivity(intent);
            }
            QueuedWork.runInMain(new j(this, uMAuthListener));
        } else if (b()) {
            if (!c()) {
                a("https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + this.h.appId + "&grant_type=refresh_token&refresh_token=" + e());
            }
            Map b = b(e());
            if (b.containsKey(v) && (((String) b.get(v)).equals(y) || ((String) b.get(v)).equals(z))) {
                d();
                authorize(uMAuthListener);
                return;
            }
            QueuedWork.runInMain(new k(this, b));
        } else {
            a hVar = new h();
            hVar.c = o;
            hVar.d = "123";
            this.e.sendReq(hVar);
        }
    }

    public void deleteAuth(UMAuthListener uMAuthListener) {
        d();
        QueuedWork.runInMain(new i(this, uMAuthListener));
    }

    public String getGender(Object obj) {
        String str = StringName.male;
        String str2 = StringName.female;
        return obj == null ? "" : obj instanceof String ? (obj.equals("m") || obj.equals(PushConstants.PUSH_TYPE_THROUGH_MESSAGE) || obj.equals(UmengText.MAN)) ? str : (obj.equals("f") || obj.equals(PushConstants.PUSH_TYPE_UPLOAD_LOG) || obj.equals(UmengText.WOMAN)) ? str2 : obj.toString() : obj instanceof Integer ? ((Integer) obj).intValue() == 1 ? str : ((Integer) obj).intValue() == 2 ? str2 : obj.toString() : obj.toString();
    }

    public void getPlatformInfo(UMAuthListener uMAuthListener) {
        if (getShareConfig().isNeedAuthOnGetUserInfo()) {
            d();
        }
        authorize(new o(this, uMAuthListener));
    }

    public String getVersion() {
        return this.f;
    }

    public WeChat getWXApi() {
        return this.e;
    }

    public e getWXEventHandler() {
        return this.A;
    }

    public boolean isAuthorize() {
        return this.a != null ? this.a.i() : false;
    }

    public boolean isInstall() {
        return this.e.isWXAppInstalled();
    }

    public boolean isSupportAuth() {
        return true;
    }

    public void onCreate(Context context, Platform platform) {
        super.onCreate(context, platform);
        this.a = new q(StubApp.getOrigApplicationContext(context.getApplicationContext()), FengConstant.LOGIN_PLATFORM_WEIXIN);
        this.h = (APPIDPlatform) platform;
        this.e = new WeChat(StubApp.getOrigApplicationContext(context.getApplicationContext()), this.h.appId);
        this.e.registerApp(this.h.appId);
    }

    public void release() {
        super.release();
        this.j = null;
    }

    public void setAuthListener(UMAuthListener uMAuthListener) {
        super.setAuthListener(uMAuthListener);
        this.j = uMAuthListener;
    }

    public boolean share(ShareContent shareContent, UMShareListener uMShareListener) {
        this.i = this.h.getName();
        if (isInstall()) {
            this.g = new s(a(shareContent));
            if (this.mShareConfig != null) {
                this.g.setCompressListener(this.mShareConfig.getCompressListener());
            }
            if (this.g.getmStyle() == 64 && (this.i == SHARE_MEDIA.WEIXIN_CIRCLE || this.i == SHARE_MEDIA.WEIXIN_FAVORITE)) {
                QueuedWork.runInMain(new g(this, uMShareListener));
                return false;
            }
            this.n = uMShareListener;
            return a(this.g);
        }
        if (Config.isJumptoAppStore) {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse(SocializeConstants.DOWN_URL_WX));
            ((Activity) this.mWeakAct.get()).startActivity(intent);
        }
        QueuedWork.runInMain(new a(this, uMShareListener));
        return false;
    }
}
