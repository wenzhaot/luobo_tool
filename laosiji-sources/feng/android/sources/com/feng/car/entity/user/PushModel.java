package com.feng.car.entity.user;

import com.feng.car.entity.BaseInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class PushModel extends BaseInfo {
    public int atmine = 0;
    public int commentpush = 0;
    public int followpush = 0;
    public int messageswitch = 0;
    public int userdirectmessagepush = 0;

    public void parser(JSONObject object) {
        try {
            if (object.has(HttpConstant.FOLLOW_PUSH)) {
                this.followpush = object.getInt(HttpConstant.FOLLOW_PUSH);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.COMMENT_PUSH)) {
                this.commentpush = object.getInt(HttpConstant.COMMENT_PUSH);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.ATMINE_PUSH)) {
                this.atmine = object.getInt(HttpConstant.ATMINE_PUSH);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.REPLY_USERDIRECTMESSAGEPUSH_PUSH)) {
                this.userdirectmessagepush = object.getInt(HttpConstant.REPLY_USERDIRECTMESSAGEPUSH_PUSH);
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.MESSAGE_SWITCH)) {
                this.messageswitch = object.getInt(HttpConstant.MESSAGE_SWITCH);
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
    }
}
