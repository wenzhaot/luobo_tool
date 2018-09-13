package com.feng.car.entity.thread;

import android.databinding.ObservableField;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class GratuityRecordInfo extends BaseInfo {
    public CommentInfo comment = new CommentInfo();
    public int id = 0;
    public int local_praise_num = 0;
    public int resourcetype = 0;
    public SnsInfo sns = new SnsInfo();
    public final ObservableField<String> time = new ObservableField("");
    public UserInfo user = new UserInfo();

    public void parser(JSONObject object) {
        try {
            if (object.has("time")) {
                this.time.set(object.getString("time"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER)) {
                this.user.parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.RESOURCETYPE)) {
                this.resourcetype = object.getInt(HttpConstant.RESOURCETYPE);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("sns")) {
                this.sns.parser(object.getJSONObject("sns"));
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.COMMENT)) {
                this.comment.parser(object.getJSONObject(HttpConstant.COMMENT));
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
    }
}
