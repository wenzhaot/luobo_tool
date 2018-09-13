package com.feng.car.entity.privatemsg;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.BaseModel;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class MessageInfo extends BaseInfo {
    public final ObservableField<String> content = new ObservableField("");
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public final ObservableBoolean isSelect = new ObservableBoolean(false);
    public final ObservableInt ismysend = new ObservableInt(0);
    public final ObservableInt isread = new ObservableInt(1);
    public MessageMyInfo my = new MessageMyInfo();
    public final ObservableField<String> time = new ObservableField("");
    public final ObservableField<String> title = new ObservableField("");
    public final ObservableInt unreadcount = new ObservableInt(0);
    public UserInfo user = new UserInfo();

    public class MessageMyInfo extends BaseModel {
        public ImageInfo image;

        public void parser(JSONObject object) {
            super.parser(object);
            try {
                if (object.has("image")) {
                    if (this.image == null) {
                        this.image = new ImageInfo();
                    }
                    this.image.parser(object.getJSONObject("image"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("content")) {
                this.content.set(object.getString("content"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("isread")) {
                this.isread.set(object.getInt("isread"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("unreadcount")) {
                this.unreadcount.set(object.getInt("unreadcount"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("time")) {
                this.time.set(object.getString("time"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title.set(object.getString("title"));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("my")) {
                this.my.parser(object.getJSONObject("my"));
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER)) {
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("ismysend")) {
                this.ismysend.set(object.getInt("ismysend"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
    }
}
