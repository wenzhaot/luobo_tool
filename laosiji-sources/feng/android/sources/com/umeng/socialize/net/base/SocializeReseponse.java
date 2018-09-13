package com.umeng.socialize.net.base;

import android.text.TextUtils;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.common.SocializeConstants;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import com.umeng.socialize.net.utils.UResponse;
import com.umeng.socialize.utils.SLog;
import com.umeng.socialize.utils.UmengText.NET;
import java.util.Iterator;
import org.json.JSONObject;

public class SocializeReseponse extends UResponse {
    protected static final String TAG = "SocializeReseponse";
    private int mHttpCode;
    protected JSONObject mJsonData;
    public String mMsg;
    public int mStCode;

    public SocializeReseponse(JSONObject jSONObject) {
        super(jSONObject);
        this.mStCode = StatusCode.ST_CODE_SDK_NORESPONSE;
        this.mJsonData = parseStatus(jSONObject);
        parseJsonObject();
    }

    public SocializeReseponse(Integer num, JSONObject jSONObject) {
        this(jSONObject);
        this.mHttpCode = num == null ? -1 : num.intValue();
    }

    public boolean isHttpOK() {
        return this.mHttpCode == 200;
    }

    public boolean isOk() {
        return this.mStCode == 200;
    }

    public JSONObject getJsonData() {
        return this.mJsonData;
    }

    public JSONObject parseStatus(JSONObject jSONObject) {
        if (jSONObject == null) {
            return null;
        }
        try {
            this.mStCode = jSONObject.optInt("st", SocializeConstants.SERVER_RETURN_PARAMS_ILLEGAL);
            if (this.mStCode == 0) {
                return null;
            }
            this.mMsg = jSONObject.optString(SocializeProtocolConstants.PROTOCOL_KEY_MSG, "");
            String optString = jSONObject.optString("data", null);
            if (TextUtils.isEmpty(optString)) {
                return null;
            }
            if (this.mStCode != 200) {
                parseErrorMsg(optString);
            }
            return new JSONObject(optString);
        } catch (Throwable e) {
            SLog.error(NET.PARSEERROR, e);
            return null;
        }
    }

    public void parseJsonObject() {
    }

    private void parseErrorMsg(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                JSONObject jSONObject2 = jSONObject.getJSONObject((String) keys.next());
                if (TextUtils.isEmpty(jSONObject2.getString(SocializeProtocolConstants.PROTOCOL_KEY_MSG))) {
                    jSONObject2.getJSONObject("data").getString(SocializeProtocolConstants.PROTOCOL_KEY_PLATFORM_ERROR);
                }
            }
        } catch (Throwable e) {
            SLog.error(e);
        }
    }
}
