package com.umeng.socialize.net.dplus.cache;

import com.umeng.socialize.utils.SLog;
import org.json.JSONObject;

public class JsonReader extends IReader<JSONObject> {
    public JsonReader(String str) {
        super(str);
    }

    public void create(String str) {
        try {
            this.result = new JSONObject(str);
        } catch (Throwable e) {
            SLog.error(e);
        }
    }
}
