package com.meizu.cloud.pushsdk.pushtracer.dataload;

import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import java.util.HashMap;
import java.util.Map;

public class TrackerDataload implements DataLoad {
    private final String TAG = TrackerDataload.class.getSimpleName();
    private final HashMap<String, Object> dataload = new HashMap();

    public void add(String key, String value) {
        if (value == null || value.isEmpty()) {
            Logger.i(this.TAG, "The keys value is empty, returning without adding key: " + key, new Object[0]);
        } else {
            this.dataload.put(key, value);
        }
    }

    public void add(String key, Object value) {
        if (value == null) {
            Logger.i(this.TAG, "The keys value is empty, returning without adding key: " + key, new Object[0]);
        } else {
            this.dataload.put(key, value);
        }
    }

    public void addMap(Map<String, Object> map) {
        if (map == null) {
            Logger.i(this.TAG, "Map passed in is null, returning without adding map.", new Object[0]);
        } else {
            this.dataload.putAll(map);
        }
    }

    public void addMap(Map map, Boolean base64_encoded, String type_encoded, String type_no_encoded) {
        if (map == null) {
            Logger.i(this.TAG, "Map passed in is null, returning nothing.", new Object[0]);
            return;
        }
        String mapString = Util.mapToJSONObject(map).toString();
        Logger.i(this.TAG, "Adding new map: " + mapString, new Object[0]);
        if (base64_encoded.booleanValue()) {
            add(type_encoded, Util.base64Encode(mapString));
        } else {
            add(type_no_encoded, mapString);
        }
    }

    public Map getMap() {
        return this.dataload;
    }

    public String toString() {
        return Util.mapToJSONObject(this.dataload).toString();
    }

    public long getByteSize() {
        return Util.getUTF8Length(toString());
    }
}
