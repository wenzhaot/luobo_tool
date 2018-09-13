package com.umeng.socialize.net.base;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.umeng.commonsdk.utils.UMUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.b.a.a;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.media.BaseMediaObject;
import com.umeng.socialize.media.UMediaObject;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.URequest;
import com.umeng.socialize.net.utils.URequest.FilePair;
import com.umeng.socialize.net.utils.URequest.RequestMethod;
import com.umeng.socialize.utils.DefaultClass;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.SocializeUtils;
import com.umeng.socialize.utils.UmengText.NET;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public abstract class SocializeRequest extends URequest {
    private static final String BASE_URL = "https://log.umsns.com/";
    public static final int REQUEST_ANALYTIC = 1;
    public static final int REQUEST_API = 2;
    public static final int REQUEST_SOCIAL = 0;
    private static final String TAG = "SocializeRequest";
    private Map<String, FilePair> mFileMap = new HashMap();
    public int mOpId;
    private int mReqType = 1;

    public enum FILE_TYPE {
        IMAGE,
        VEDIO
    }

    protected abstract String getPath();

    public SocializeRequest(Context context, String str, Class<? extends SocializeReseponse> cls, int i, RequestMethod requestMethod) {
        super("");
        this.mResponseClz = cls;
        this.mOpId = i;
        this.mContext = context;
        this.mMethod = requestMethod;
        setBaseUrl("https://log.umsns.com/");
    }

    public void setReqType(int i) {
        this.mReqType = i;
    }

    public void addFileParams(byte[] bArr, FILE_TYPE file_type, String str) {
        if (FILE_TYPE.IMAGE == file_type) {
            String c = a.c(bArr);
            if (TextUtils.isEmpty(c)) {
                c = "png";
            }
            this.mFileMap.put(SocializeProtocolConstants.PROTOCOL_KEY_IMAGE, new FilePair(SocializeUtils.md5(bArr) + FileUtils.FILE_EXTENSION_SEPARATOR + c, bArr));
        }
    }

    public void addMediaParams(UMediaObject uMediaObject) {
        if (uMediaObject != null) {
            if (uMediaObject instanceof BaseMediaObject) {
                addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_TITLE, ((BaseMediaObject) uMediaObject).getTitle());
            }
            if (uMediaObject.isUrlMedia()) {
                for (Entry entry : uMediaObject.toUrlExtraParams().entrySet()) {
                    addStringParams((String) entry.getKey(), entry.getValue().toString());
                }
                return;
            }
            byte[] toByte = uMediaObject.toByte();
            if (toByte != null) {
                addFileParams(toByte, FILE_TYPE.IMAGE, null);
            }
        }
    }

    public Map<String, Object> getBodyPair() {
        return buildParams();
    }

    public Map<String, FilePair> getFilePair() {
        return this.mFileMap;
    }

    public JSONObject toJson() {
        return null;
    }

    public String toGetUrl() {
        return generateGetURL(getBaseUrl(), buildParams());
    }

    public Map<String, Object> buildParams() {
        Map<String, Object> baseQuery = getBaseQuery(this.mContext);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        }
        if (!TextUtils.isEmpty(Config.SessionId)) {
            baseQuery.put("sid", Config.SessionId);
        }
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, Integer.valueOf(this.mReqType));
        baseQuery.put(SocializeProtocolConstants.PROTOCOL_KEY_OPID, Integer.valueOf(this.mOpId));
        baseQuery.put("uid", UMUtils.getUMId(this.mContext));
        baseQuery.putAll(this.mParams);
        return baseQuery;
    }

    public void setBaseUrl(String str) {
        String str2 = "";
        try {
            if (!TextUtils.isEmpty(getPath())) {
                str2 = new URL(new URL(str), getPath()).toString();
            }
        } catch (Throwable e) {
            SLog.error(NET.getURLERROR(str), e);
        }
        super.setBaseUrl(str2);
    }

    public void onPrepareRequest() {
        addStringParams("pcv", SocializeConstants.PROTOCOL_VERSON);
        addStringParams(SocializeConstants.USHARETYPE, Config.shareType);
        addStringParams("imei", DeviceConfig.getDeviceId(this.mContext));
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        addStringParams(SocializeProtocolConstants.PROTOCOL_KEY_MAC, DeviceConfig.getMac(this.mContext));
        addStringParams("os", "Android");
        addStringParams("en", DeviceConfig.getNetworkAccessMode(this.mContext)[0]);
        addStringParams("uid", null);
        addStringParams("sdkv", "6.9.2");
        addStringParams("dt", String.valueOf(System.currentTimeMillis()));
    }

    private String mapTostring(Map<String, Object> map) {
        String str = null;
        if (this.mParams.isEmpty()) {
            return str;
        }
        try {
            return new JSONObject(map).toString();
        } catch (Throwable e) {
            SLog.error(e);
            return str;
        }
    }

    protected String getHttpMethod() {
        switch (this.mMethod) {
            case POST:
                return POST;
            default:
                return GET;
        }
    }

    public String getEcryptString(String str) {
        return str;
    }

    public String getDecryptString(String str) {
        return str;
    }

    public static Map<String, Object> getBaseQuery(Context context) {
        Map<String, Object> hashMap = new HashMap();
        CharSequence deviceId = DeviceConfig.getDeviceId(context);
        if (!TextUtils.isEmpty(deviceId)) {
            hashMap.put("imei", deviceId);
        }
        Object mac = DeviceConfig.getMac(context);
        if (TextUtils.isEmpty(mac)) {
            mac = DefaultClass.getMac();
            SLog.I(NET.MACNULL);
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_MAC, mac);
        if (!TextUtils.isEmpty(SocializeConstants.UID)) {
            hashMap.put("uid", SocializeConstants.UID);
        }
        try {
            hashMap.put("en", DeviceConfig.getNetworkAccessMode(context)[0]);
        } catch (Exception e) {
            hashMap.put("en", "Unknown");
        }
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_DE, Build.MODEL);
        hashMap.put("sdkv", "6.9.2");
        hashMap.put("os", "Android");
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_ANDROID_ID, DeviceConfig.getAndroidID(context));
        hashMap.put("sn", DeviceConfig.getDeviceSN());
        hashMap.put("os_version", DeviceConfig.getOsVersion());
        hashMap.put("dt", Long.valueOf(System.currentTimeMillis()));
        hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_AK, SocializeUtils.getAppkey(context));
        hashMap.put(SocializeProtocolConstants.PROTOCOL_VERSION, SocializeConstants.PROTOCOL_VERSON);
        hashMap.put(SocializeConstants.USHARETYPE, Config.shareType);
        if (!TextUtils.isEmpty(Config.EntityKey)) {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_ENTITY_KEY, Config.EntityKey);
        }
        if (!TextUtils.isEmpty(Config.SessionId)) {
            hashMap.put("sid", Config.SessionId);
        }
        try {
            hashMap.put(SocializeProtocolConstants.PROTOCOL_KEY_REQUEST_TYPE, Integer.valueOf(0));
        } catch (Throwable e2) {
            SLog.error(e2);
        }
        return hashMap;
    }
}
