package com.feng.car.entity.hotshow;

import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.entity.user.UserInfoList;
import com.feng.car.utils.HttpConstant;
import org.json.JSONArray;
import org.json.JSONObject;

public class HotShowInfo extends BaseInfo {
    public ObservableInt clickcount = new ObservableInt(0);
    public int hotshowcount = 0;
    public int id;
    public ImageInfo image = new ImageInfo();
    public ObservableInt isfollow = new ObservableInt(0);
    public ObservableInt isremind = new ObservableInt(0);
    public String name = "";
    public ObservableInt redpoint = new ObservableInt(0);
    public String title = "";
    public UserInfoList userlist = new UserInfoList();

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("clickcount")) {
                this.clickcount.set(object.getInt("clickcount"));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            this.clickcount.set(0);
        }
        try {
            if (object.has("isremind")) {
                if (!(object.get("isremind") instanceof Boolean)) {
                    this.isremind.set(object.getInt("isremind"));
                } else if (object.getBoolean("isremind")) {
                    this.isremind.set(1);
                } else {
                    this.isremind.set(0);
                }
            }
        } catch (Exception e22) {
            e22.printStackTrace();
            this.isremind.set(0);
        }
        try {
            if (object.has("redpoint")) {
                this.redpoint.set(object.getInt("redpoint"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
            this.redpoint.set(0);
        }
        try {
            if (object.has("hotshowcount")) {
                this.hotshowcount = object.getInt("hotshowcount");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("title")) {
                this.title = object.getString("title");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.ISFOLLOW)) {
                this.isfollow.set(object.getInt(HttpConstant.ISFOLLOW));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
            this.isfollow.set(0);
        }
        try {
            if (object.has("userlist")) {
                JSONArray jsonArray = object.getJSONArray("userlist");
                int size = jsonArray.length();
                this.userlist.clear();
                for (int i = 0; i < size; i++) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.parser(jsonArray.getJSONObject(i));
                    this.userlist.add(userInfo);
                }
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
    }
}
