package com.meizu.cloud.pushsdk.pushtracer.dataload;

import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.pushtracer.utils.Preconditions;
import com.meizu.cloud.pushsdk.pushtracer.utils.Util;
import java.util.HashMap;
import java.util.Map;

public class SelfDescribingJson implements DataLoad {
    private final String TAG;
    private final HashMap<String, Object> payload;

    public SelfDescribingJson(String schema) {
        this(schema, new HashMap());
    }

    public SelfDescribingJson(String schema, TrackerDataload data) {
        this.TAG = SelfDescribingJson.class.getSimpleName();
        this.payload = new HashMap();
        setSchema(schema);
        setData(data);
    }

    public SelfDescribingJson(String schema, SelfDescribingJson data) {
        this.TAG = SelfDescribingJson.class.getSimpleName();
        this.payload = new HashMap();
        setSchema(schema);
        setData(data);
    }

    public SelfDescribingJson(String schema, Object data) {
        this.TAG = SelfDescribingJson.class.getSimpleName();
        this.payload = new HashMap();
        setSchema(schema);
        setData(data);
    }

    public SelfDescribingJson setSchema(String schema) {
        Preconditions.checkNotNull(schema, "schema cannot be null");
        Preconditions.checkArgument(!schema.isEmpty(), "schema cannot be empty.");
        this.payload.put(Parameters.SCHEMA, schema);
        return this;
    }

    public SelfDescribingJson setData(TrackerDataload trackerDataload) {
        if (trackerDataload != null) {
            this.payload.put("dt", trackerDataload.getMap());
        }
        return this;
    }

    public SelfDescribingJson setData(Object data) {
        if (data != null) {
            this.payload.put("dt", data);
        }
        return this;
    }

    public SelfDescribingJson setData(SelfDescribingJson selfDescribingJson) {
        if (this.payload != null) {
            this.payload.put("dt", selfDescribingJson.getMap());
        }
        return this;
    }

    @Deprecated
    public void add(String key, String value) {
        Logger.i(this.TAG, "Payload: add(String, String) method called - Doing nothing.", new Object[0]);
    }

    @Deprecated
    public void add(String key, Object value) {
        Logger.i(this.TAG, "Payload: add(String, Object) method called - Doing nothing.", new Object[0]);
    }

    @Deprecated
    public void addMap(Map<String, Object> map) {
        Logger.i(this.TAG, "Payload: addMap(Map<String, Object>) method called - Doing nothing.", new Object[0]);
    }

    @Deprecated
    public void addMap(Map map, Boolean base64_encoded, String type_encoded, String type_no_encoded) {
        Logger.i(this.TAG, "Payload: addMap(Map, Boolean, String, String) method called - Doing nothing.", new Object[0]);
    }

    public Map<String, Object> getMap() {
        return this.payload;
    }

    public String toString() {
        return Util.mapToJSONObject(this.payload).toString();
    }

    public long getByteSize() {
        return Util.getUTF8Length(toString());
    }
}
