package com.feng.car.entity.ad;

import android.content.Context;
import com.feng.car.entity.BaseInfo;
import com.feng.car.operation.AdvertOperation;
import com.feng.car.utils.HttpConstant;
import com.umeng.commonsdk.proguard.g;
import org.json.JSONObject;

public class AdvertInfo extends BaseInfo {
    public transient int adid;
    public transient String adpos;
    public String adposlable;
    public String adpostemplatetype = "";
    private transient AdvertOperation advertOperation;
    public transient String backjson = "";
    public int bucketid;
    public int campaignid;
    public String carouselnum = "";
    public transient int carouselnumcode;
    public transient int contentid;
    public transient String cpd;
    public int creativeid;
    public int creativeshow = 4;
    public String creativesize;
    public String devicetoken = "";
    public transient String imagelasttime;
    public transient int imagenum;
    public transient String indexname;
    public int isinner;
    public String landingurl = "";
    public transient boolean local_add_feed = false;
    public String monitorlink = "";
    public int orderid;
    public int pageid;
    public transient int pageorder;
    public int publishid;
    public transient int resthype;
    public transient int seat;
    public String showid;
    public transient AdMaterialInfo tmpmap = new AdMaterialInfo();
    public String word;
    public String wordtype;

    public void resetData() {
        this.tmpmap.resetData();
        this.seat = 0;
        this.adid = 0;
        this.isinner = 0;
        this.bucketid = 0;
        this.campaignid = 0;
        this.orderid = 0;
        this.publishid = 0;
        this.creativeid = 0;
        this.creativesize = "";
        this.creativeshow = 0;
        this.carouselnum = "";
        this.adposlable = "";
        this.wordtype = "";
        this.word = "";
        this.pageid = 0;
        this.imagelasttime = "";
        this.imagenum = 0;
        this.pageorder = 0;
        this.cpd = "";
        this.adpos = "";
        this.carouselnumcode = 0;
        this.contentid = 0;
        this.indexname = "";
        this.resthype = 0;
        this.landingurl = "";
        this.adpostemplatetype = "";
        this.monitorlink = "";
        this.devicetoken = "";
        this.backjson = "";
    }

    public String getLocalkey() {
        return this.adid + "_2";
    }

    public void parser(JSONObject object) {
        try {
            if (object.has("landingurl")) {
                this.landingurl = object.getString("landingurl");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (object.has("monitorlink")) {
                this.monitorlink = object.getString("monitorlink");
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        try {
            if (object.has(g.a)) {
                this.devicetoken = object.getString(g.a);
            }
        } catch (Exception e22) {
            e22.printStackTrace();
        }
        try {
            if (object.has("tmpmap")) {
                this.tmpmap.parser(object.getJSONObject("tmpmap"));
            }
        } catch (Exception e222) {
            e222.printStackTrace();
        }
        try {
            if (object.has("campaignid")) {
                this.campaignid = object.getInt("campaignid");
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
        }
        try {
            if (object.has("imagenum")) {
                this.imagenum = object.getInt("imagenum");
            }
        } catch (Exception e22222) {
            e22222.printStackTrace();
        }
        try {
            if (object.has("isinner")) {
                this.isinner = object.getInt("isinner");
            }
        } catch (Exception e222222) {
            e222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.PAGEID)) {
                this.pageid = object.getInt(HttpConstant.PAGEID);
            }
        } catch (Exception e2222222) {
            e2222222.printStackTrace();
        }
        try {
            if (object.has("pageorder")) {
                this.pageorder = object.getInt("pageorder");
            }
        } catch (Exception e22222222) {
            e22222222.printStackTrace();
        }
        try {
            if (object.has("adid")) {
                this.adid = object.getInt("adid");
            }
        } catch (Exception e222222222) {
            e222222222.printStackTrace();
        }
        try {
            if (object.has("adposlable")) {
                this.adposlable = object.getString("adposlable");
            }
        } catch (Exception e2222222222) {
            e2222222222.printStackTrace();
        }
        try {
            if (object.has("cpd")) {
                this.cpd = object.getString("cpd");
            }
        } catch (Exception e22222222222) {
            e22222222222.printStackTrace();
        }
        try {
            if (object.has("adpos")) {
                this.adpos = object.getString("adpos");
            }
        } catch (Exception e222222222222) {
            e222222222222.printStackTrace();
        }
        try {
            if (object.has("imagelasttime")) {
                this.imagelasttime = object.getString("imagelasttime");
            }
        } catch (Exception e2222222222222) {
            e2222222222222.printStackTrace();
        }
        try {
            if (object.has("carouselnumcode")) {
                this.carouselnumcode = object.getInt("carouselnumcode");
            }
        } catch (Exception e22222222222222) {
            e22222222222222.printStackTrace();
        }
        try {
            if (object.has(HttpConstant.ORDERID)) {
                this.orderid = object.getInt(HttpConstant.ORDERID);
            }
        } catch (Exception e222222222222222) {
            e222222222222222.printStackTrace();
        }
        try {
            if (object.has("creativeshow")) {
                this.creativeshow = object.getInt("creativeshow");
            }
        } catch (Exception e2222222222222222) {
            e2222222222222222.printStackTrace();
        }
        try {
            if (object.has("contentid")) {
                this.contentid = object.getInt("contentid");
            }
        } catch (Exception e22222222222222222) {
            e22222222222222222.printStackTrace();
        }
        try {
            if (object.has("bucketid")) {
                this.bucketid = object.getInt("bucketid");
            }
        } catch (Exception e222222222222222222) {
            e222222222222222222.printStackTrace();
        }
        try {
            if (object.has("creativeid")) {
                this.creativeid = object.getInt("creativeid");
            }
        } catch (Exception e2222222222222222222) {
            e2222222222222222222.printStackTrace();
        }
        try {
            if (object.has("publishid")) {
                this.publishid = object.getInt("publishid");
            }
        } catch (Exception e22222222222222222222) {
            e22222222222222222222.printStackTrace();
        }
        try {
            if (object.has("seat")) {
                this.seat = object.getInt("seat");
            }
        } catch (Exception e222222222222222222222) {
            e222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("showid")) {
                this.showid = object.getString("showid");
            }
        } catch (Exception e2222222222222222222222) {
            e2222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("indexname")) {
                this.indexname = object.getString("indexname");
            }
        } catch (Exception e22222222222222222222222) {
            e22222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("resthype")) {
                this.resthype = object.getInt("resthype");
            }
        } catch (Exception e222222222222222222222222) {
            e222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("creativesize")) {
                this.creativesize = object.getString("creativesize");
            }
        } catch (Exception e2222222222222222222222222) {
            e2222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("wordtype")) {
                this.wordtype = object.getString("wordtype");
            }
        } catch (Exception e22222222222222222222222222) {
            e22222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("word")) {
                this.word = object.getString("word");
            }
        } catch (Exception e222222222222222222222222222) {
            e222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("carouselnum")) {
                this.carouselnum = object.getString("carouselnum");
            }
        } catch (Exception e2222222222222222222222222222) {
            e2222222222222222222222222222.printStackTrace();
        }
        try {
            if (object.has("tmpmap")) {
                object.remove("tmpmap").toString();
                this.backjson = object.toString();
            }
        } catch (Exception e22222222222222222222222222222) {
            e22222222222222222222222222222.printStackTrace();
        }
    }

    public void adClickHandle(Context context) {
        if (this.advertOperation == null) {
            this.advertOperation = new AdvertOperation(this);
        }
        this.advertOperation.adClickHandle(context);
    }

    public void adPvHandle(Context context, boolean isStart) {
        if (this.advertOperation == null) {
            this.advertOperation = new AdvertOperation(this);
        }
        this.advertOperation.adPvHandle(context, isStart);
    }
}
