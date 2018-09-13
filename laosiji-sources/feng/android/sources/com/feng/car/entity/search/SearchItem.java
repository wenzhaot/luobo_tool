package com.feng.car.entity.search;

import com.feng.car.entity.BaseInfo;
import org.json.JSONObject;

public class SearchItem extends BaseInfo {
    public static int SEARCH_BROWSE_CARSERIES_TYPE = 7;
    public static int SEARCH_CAR_USER_CONTENT_TYPE = 1;
    public static int SEARCH_CIRCLE_TYPE = 5;
    public static int SEARCH_CITY_TYPE = 6;
    public static int SEARCH_OWNER_PRICE_RECORD_TYPE = 9;
    public static int SEARCH_OWNER_PRICE_TYPE = 8;
    public static int SEARCH_SHOW_ALL_RECORD_TYPE = 3;
    public static int SEARCH_SHOW_DEL_RECORD_TYPE = 4;
    public static int SEARCH_TOPIC_TYPE = 2;
    public static int SEARCH_VOICE_TOPIC_TYPE = 10;
    public int _id = 0;
    public String content = "";
    public int contentid = 0;
    public int type = 1;

    public void parser(JSONObject object) {
    }
}
