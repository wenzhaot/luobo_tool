package com.feng.car.entity.launch;

import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

public class StartPageInfo extends BaseInfo {
    public static final String SPLASH_END_TIME = "SPLASH_END_TIME";
    public static final String SPLASH_HTML_URL = "SPLASH_HTML_URL";
    public static final String SPLASH_PIC_AUTHOR = "SPLASH_PIC_AUTHOR";
    public static final String SPLASH_PIC_ID = "SPLASH_PIC_ID";
    public static final String SPLASH_PIC_URL = "SPLASH_PIC_URL";
    public String author = "";
    public String endtime = "";
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public String url = "";

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("url")) {
                this.url = object.getString("url");
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("endtime")) {
                this.endtime = object.getString("endtime");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has(SocializeProtocolConstants.AUTHOR)) {
                this.author = object.getString(SocializeProtocolConstants.AUTHOR);
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
