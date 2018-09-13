package com.umeng.message.entity;

import com.feng.car.utils.FengConstant;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.analytics.pro.b;
import com.umeng.message.MsgConstant;
import org.json.JSONException;
import org.json.JSONObject;

public class UInAppMessage {
    public static final int CARD_A = 2;
    public static final int CARD_B = 3;
    public static final String CLOSE = "go_app";
    public static final int CUSTOM_CARD = 4;
    public static final String GO_ACTIVITY = "go_activity";
    public static final String GO_URL = "go_url";
    public static final String NONE = "none";
    public static final int PLAIN_TAXT_A = 5;
    public static final int PLAIN_TAXT_B = 6;
    public static final int SPLASH_A = 0;
    public static final int SPLASH_B = 1;
    public static final int VIEW_BOTTOM_IMAGE = 17;
    public static final int VIEW_CUSTOM_BUTTON = 19;
    public static final int VIEW_IMAGE = 16;
    public static final int VIEW_PLAIN_TEXT = 18;
    private JSONObject a;
    public String action_activity;
    public String action_type;
    public String action_url;
    public String bottom_action_activity;
    public String bottom_action_type;
    public String bottom_action_url;
    public int bottom_height;
    public String bottom_image_url;
    public int bottom_width;
    public String button_text;
    public String check_num;
    public String content;
    public String customButtonActionType;
    public String customButtonActivity;
    public String customButtonUrl;
    public boolean display_button;
    public String display_name;
    public int display_time;
    public String expire_time;
    public int height;
    public String image_url;
    public String msg_id;
    public int msg_type;
    public int pduration;
    public String plainTextActionType;
    public String plainTextActivity;
    public String plainTextUrl;
    public int sduration;
    public int show_times;
    public int show_type;
    public String start_time;
    public String title;
    public int width;

    public UInAppMessage(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2;
        this.a = jSONObject;
        this.msg_id = jSONObject.getString("msg_id");
        this.msg_type = jSONObject.getInt(MsgConstant.INAPP_MSG_TYPE);
        JSONObject jSONObject3 = jSONObject.getJSONObject("msg_info");
        this.display_button = jSONObject3.optBoolean("display_button");
        this.display_name = jSONObject3.optString("display_name", "");
        this.display_time = jSONObject3.optInt("display_time", 5);
        if (this.msg_type == 5 || this.msg_type == 6) {
            jSONObject2 = jSONObject3.getJSONObject("plain_text");
            this.title = jSONObject2.getString("title");
            this.content = jSONObject2.getString("content");
            this.button_text = jSONObject2.getString("button_text");
            this.plainTextActionType = jSONObject2.getString(MsgConstant.KEY_ACTION_TYPE);
            this.plainTextActivity = jSONObject2.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.plainTextUrl = jSONObject2.optString("url", "");
        }
        if (jSONObject3.has("image")) {
            jSONObject2 = jSONObject3.getJSONObject("image");
            this.image_url = jSONObject2.getString("imageurl");
            this.width = jSONObject2.getInt("width");
            this.height = jSONObject2.getInt("height");
            this.action_type = jSONObject2.getString(MsgConstant.KEY_ACTION_TYPE);
            this.action_activity = jSONObject2.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.action_url = jSONObject2.optString("url", "");
        }
        if (jSONObject3.has("bottom_image")) {
            jSONObject2 = jSONObject3.getJSONObject("bottom_image");
            this.bottom_image_url = jSONObject2.getString("imageurl");
            this.bottom_width = jSONObject2.getInt("width");
            this.bottom_height = jSONObject2.getInt("height");
            this.bottom_action_type = jSONObject2.getString(MsgConstant.KEY_ACTION_TYPE);
            this.bottom_action_activity = jSONObject2.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.bottom_action_url = jSONObject2.optString("url", "");
        }
        if (jSONObject3.has("custom_button")) {
            jSONObject3 = jSONObject3.getJSONObject("custom_button");
            this.customButtonActionType = jSONObject3.getString(MsgConstant.KEY_ACTION_TYPE);
            this.customButtonActivity = jSONObject3.optString(PushConstants.INTENT_ACTIVITY_NAME, "");
            this.customButtonUrl = jSONObject3.optString("url", "");
        }
        jSONObject3 = jSONObject.getJSONObject("policy");
        this.show_type = jSONObject3.getInt(FengConstant.BIG_IMAGE_SHOW_TYPE);
        this.show_times = jSONObject3.getInt("show_times");
        this.start_time = jSONObject3.getString(b.p);
        this.expire_time = jSONObject3.getString("expire_time");
    }

    public JSONObject getRaw() {
        return this.a;
    }
}
