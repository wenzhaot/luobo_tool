package com.feng.car.entity.circle;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;
import com.feng.car.entity.BaseInfo;
import com.feng.car.entity.ImageInfo;
import com.feng.car.operation.CircleOperation;
import com.feng.car.operation.OperationCallback;
import com.google.gson.annotations.Expose;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class CircleInfo extends BaseInfo {
    public int autoseriesid = 0;
    private transient CircleOperation circleOperation = new CircleOperation(this);
    public String createtime = "";
    public int fanscount;
    @Expose
    public int id = 0;
    public ImageInfo image = new ImageInfo();
    public ObservableInt isfans = new ObservableInt(0);
    public transient ObservableBoolean islocalcreate = new ObservableBoolean(false);
    public transient ObservableBoolean islocalselect = new ObservableBoolean(false);
    @Expose
    public String name = "";
    public ObservableInt redpoint = new ObservableInt(0);
    public int snscount;
    public List<CircleSubclassInfo> sublist = new ArrayList();
    public int type = 0;
    public List<ImageInfo> userlogolist = new ArrayList();

    public String getCircleName() {
        if (this.type == 0) {
            return this.name;
        }
        return this.name;
    }

    public void parser(JSONObject object) {
        JSONArray jsonArray;
        int size;
        int i;
        try {
            if (object.has("id")) {
                this.id = object.getInt("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("name")) {
                this.name = object.getString("name");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has("image") && !object.isNull("image")) {
                this.image.parser(object.getJSONObject("image"));
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("autoseriesid")) {
                this.autoseriesid = object.getInt("autoseriesid");
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("type")) {
                this.type = object.getInt("type");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("fanscount")) {
                this.fanscount = object.getInt("fanscount");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("snscount")) {
                this.snscount = object.getInt("snscount");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has("isfans")) {
                this.isfans.set(object.getInt("isfans"));
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("redpoint")) {
                this.redpoint.set(object.getInt("redpoint"));
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
            this.redpoint.set(0);
        }
        try {
            if (object.has("createtime") && !object.isNull("createtime")) {
                this.createtime = object.getString("createtime");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("sublist") && !object.isNull("sublist")) {
                jsonArray = object.getJSONArray("sublist");
                size = jsonArray.length();
                this.sublist.clear();
                for (i = 0; i < size; i++) {
                    CircleSubclassInfo info = new CircleSubclassInfo();
                    info.parser(jsonArray.getJSONObject(i));
                    this.sublist.add(info);
                }
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("userlogolist") && !object.isNull("userlogolist")) {
                jsonArray = object.getJSONArray("userlogolist");
                size = jsonArray.length();
                this.userlogolist.clear();
                for (i = 0; i < size; i++) {
                    ImageInfo info2 = new ImageInfo();
                    info2.parser(jsonArray.getJSONObject(i));
                    this.userlogolist.add(info2);
                }
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
    }

    public boolean equals(Object obj) {
        return this.id == ((CircleInfo) obj).id && this.name.equals(((CircleInfo) obj).name);
    }

    public void accedeSingleOperation(Context context, OperationCallback callback) {
        if (this.circleOperation == null) {
            this.circleOperation = new CircleOperation(this);
        }
        this.circleOperation.accedeSingleOperation(context, callback);
    }

    public void intentToCircleFinalPage(Context context) {
        if (this.circleOperation == null) {
            this.circleOperation = new CircleOperation(this);
        }
        this.circleOperation.intentToCircleFinalPage(context);
    }

    public void intentToCarSeriesFinalPage(Context context) {
        if (this.circleOperation == null) {
            this.circleOperation = new CircleOperation(this);
        }
        this.circleOperation.intentToCarSeriesFinalPage(context);
    }

    public void accedeBatchOperation(Context context, int type, String strIDs, OperationCallback callback) {
        if (this.circleOperation == null) {
            this.circleOperation = new CircleOperation(this);
        }
        this.circleOperation.accedeBatchOperation(context, type, strIDs, callback);
    }
}
