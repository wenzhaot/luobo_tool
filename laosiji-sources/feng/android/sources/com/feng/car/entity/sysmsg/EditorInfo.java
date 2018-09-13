package com.feng.car.entity.sysmsg;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.utils.HttpConstant;
import org.json.JSONObject;

public class EditorInfo extends BaseInfo {
    public static final int APPLY_FAIL = -1;
    public static final int APPLY_PASS = 1;
    public final ObservableField<String> description = new ObservableField("");
    public final ObservableInt result = new ObservableInt(-1);
    public final ObservableField<UserInfo> user = new ObservableField(new UserInfo());

    public void parser(JSONObject object) {
        try {
            if (object.has("result")) {
                this.result.set(object.getInt("result"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.DESCRIPTION)) {
                this.description.set(object.getString(HttpConstant.DESCRIPTION));
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.USER)) {
                ((UserInfo) this.user.get()).parser(object.getJSONObject(HttpConstant.USER));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
    }
}
