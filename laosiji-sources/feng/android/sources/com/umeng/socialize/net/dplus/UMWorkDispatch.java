package com.umeng.socialize.net.dplus;

import android.content.Context;
import android.text.TextUtils;
import com.umeng.analytics.pro.b;
import com.umeng.commonsdk.stateless.UMSLEnvelopeBuild;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.dplus.cache.DplusCacheApi;
import com.umeng.socialize.net.dplus.cache.DplusCacheListener;
import com.umeng.socialize.utils.DeviceConfig;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.NET;
import org.json.JSONObject;

public class UMWorkDispatch {
    public static String URL = "umpx_share";

    public static void sendEvent(final Context context, final int i, Object obj) {
        UMSLEnvelopeBuild.mContext = context;
        if (i == SocializeConstants.SAVE_STATS_EVENT) {
            DplusCacheApi.getInstance().saveFile(context, (JSONObject) obj, i, new DplusCacheListener() {
                public void onResult(JSONObject jSONObject) {
                }
            });
        } else {
            DplusCacheApi.getInstance().saveFile(context, (JSONObject) obj, i, new DplusCacheListener() {
                public void onResult(JSONObject jSONObject) {
                    UMSLEnvelopeBuild uMSLEnvelopeBuild = new UMSLEnvelopeBuild();
                    JSONObject access$000 = UMWorkDispatch.constructHeader(context, uMSLEnvelopeBuild.buildSLBaseHeader(context));
                    JSONObject readFileAsnc = DplusCacheApi.getInstance().readFileAsnc(context, i);
                    JSONObject jSONObject2 = null;
                    if (!(readFileAsnc == null || TextUtils.isEmpty(readFileAsnc.toString()))) {
                        jSONObject2 = uMSLEnvelopeBuild.buildSLEnvelope(context, access$000, readFileAsnc, UMWorkDispatch.URL);
                    }
                    if (jSONObject2 == null) {
                        SLog.E(NET.BODYNULL);
                    } else if (!jSONObject2.has(b.ao)) {
                        DplusCacheApi.getInstance().deleteFileAsnc(context);
                    } else if (jSONObject2.optInt(b.ao) != 101) {
                        DplusCacheApi.getInstance().deleteFileAsnc(context);
                    }
                }
            });
        }
    }

    private static JSONObject constructHeader(Context context, JSONObject jSONObject) {
        try {
            JSONObject optJSONObject = jSONObject.optJSONObject("header");
            if (optJSONObject != null) {
                optJSONObject.put("s_sdk_v", "6.9.2");
                optJSONObject.put(CommonNetImpl.PCV, SocializeConstants.PROTOCOL_VERSON);
                optJSONObject.put("imei", DeviceConfig.getDeviceId(context));
            }
            jSONObject.put("header", optJSONObject);
        } catch (Throwable e) {
            SLog.error(e);
        }
        return jSONObject;
    }
}
