package com.feng.car.entity.sns;

import com.feng.car.entity.BaseInfo;
import com.tencent.ijk.media.player.IjkMediaPlayer.OnNativeInvokeListener;
import org.json.JSONObject;

public class SnsReadRecord extends BaseInfo {
    public int firstVisiblePosition;
    public int id;
    public int offset;

    public void parser(JSONObject object) {
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("firstVisiblePosition")) {
                this.firstVisiblePosition = object.getInt("firstVisiblePosition");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(OnNativeInvokeListener.ARG_OFFSET)) {
                this.offset = object.getInt(OnNativeInvokeListener.ARG_OFFSET);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }

    public SnsReadRecord(int id, int firstVisiblePosition, int offset) {
        this.id = id;
        this.firstVisiblePosition = firstVisiblePosition;
        this.offset = offset;
    }

    public boolean equals(Object obj) {
        return this.id == ((SnsReadRecord) obj).id;
    }
}
