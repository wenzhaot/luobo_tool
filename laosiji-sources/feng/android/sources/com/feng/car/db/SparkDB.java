package com.feng.car.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.entity.car.CarModelInfo;
import com.feng.car.entity.drafts.DraftsModel;
import com.feng.car.entity.search.SearchItem;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.library.utils.SharedUtil;
import com.umeng.message.proguard.l;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SparkDB {
    private SparkDBHelper helper;

    public SparkDB(Context context) {
        this.helper = new SparkDBHelper(context);
    }

    public Map<String, Integer> getDrafts(int user_id, int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Map<String, Integer> map = new HashMap();
        Cursor c = db.rawQuery("select * from post_drafts where user_id=" + user_id + " and resources_id is not null and type = " + type + " ORDER BY date DESC", null);
        while (c.moveToNext()) {
            DraftsModel draftsModel = new DraftsModel();
            draftsModel._id = c.getInt(c.getColumnIndex("_id"));
            draftsModel.resources_id = c.getString(c.getColumnIndex("resources_id"));
            map.put(draftsModel.resources_id, Integer.valueOf(0));
        }
        c.close();
        db.close();
        return map;
    }

    public DraftsModel getDraftsById(String resources_id, int userID, int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        DraftsModel draftsModel = null;
        Cursor c = db.rawQuery("select * from post_drafts where resources_id = ? and user_id=? and type = ? ", new String[]{resources_id, userID + "", type + ""});
        if (c.moveToNext()) {
            draftsModel = new DraftsModel();
            draftsModel._id = c.getInt(c.getColumnIndex("_id"));
            draftsModel.resources_id = c.getString(c.getColumnIndex("resources_id"));
            draftsModel.title = c.getString(c.getColumnIndex("title"));
            draftsModel.description = c.getString(c.getColumnIndex(HttpConstant.DESCRIPTION));
            draftsModel.coverJson = c.getString(c.getColumnIndex("coverjson"));
            draftsModel.postJson = c.getString(c.getColumnIndex("postjson"));
            draftsModel.goodsJson = c.getString(c.getColumnIndex("goodsjson"));
            draftsModel.servelistJson = c.getString(c.getColumnIndex("servelistjson"));
            draftsModel.topiclistJson = c.getString(c.getColumnIndex("topiclistjson"));
            draftsModel.type = c.getInt(c.getColumnIndex("type"));
            if (TextUtils.isEmpty(draftsModel.title)) {
                draftsModel.title = "";
            }
            if (TextUtils.isEmpty(draftsModel.description)) {
                draftsModel.description = "";
            }
            if (TextUtils.isEmpty(draftsModel.postJson)) {
                draftsModel.postJson = "";
            }
            if (TextUtils.isEmpty(draftsModel.coverJson)) {
                draftsModel.coverJson = "";
            }
            if (TextUtils.isEmpty(draftsModel.goodsJson)) {
                draftsModel.goodsJson = "";
            }
            if (TextUtils.isEmpty(draftsModel.servelistJson)) {
                draftsModel.servelistJson = "";
            }
            if (TextUtils.isEmpty(draftsModel.topiclistJson)) {
                draftsModel.topiclistJson = "";
            }
        }
        c.close();
        db.close();
        return draftsModel;
    }

    public boolean updateDraftsPostJson(String resources_id, String coverJson, String postJson, int userid, int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from post_drafts where resources_id = ? ", new String[]{resources_id + ""});
        if (c.moveToNext()) {
            db.execSQL("update post_drafts set coverjson=?, postjson=?,date=datetime('now','localtime')  where resources_id=? and user_id = ? and type = ? ", new Object[]{coverJson, postJson, resources_id, Integer.valueOf(userid), Integer.valueOf(type)});
            c.close();
            db.close();
            return true;
        }
        c.close();
        return false;
    }

    public void addDrafts(DraftsModel draftsModel) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from post_drafts where resources_id = ? and user_id = ? and type = ? ", new String[]{draftsModel.resources_id + "", draftsModel.user_id + "", draftsModel.type + ""});
        if (c.moveToNext()) {
            db.execSQL("update post_drafts set title=?,description =?,coverjson=?,postjson=?,goodsjson=?,servelistjson=?,topiclistjson=?,date=datetime('now','localtime') where resources_id=? and user_id = ? and type = ? ", new Object[]{draftsModel.title, draftsModel.description, draftsModel.coverJson, draftsModel.postJson, draftsModel.goodsJson, draftsModel.servelistJson, draftsModel.topiclistJson, draftsModel.resources_id, draftsModel.user_id + "", draftsModel.type + ""});
        } else {
            db.execSQL("insert into post_drafts (title,description,coverjson,postjson,goodsjson,servelistjson,topiclistjson,resources_id,user_id,type) values(?,?,?,?,?,?,?,?,?,?)", new Object[]{draftsModel.title, draftsModel.description, draftsModel.coverJson, draftsModel.postJson, draftsModel.goodsJson, draftsModel.servelistJson, draftsModel.topiclistJson, draftsModel.resources_id, Integer.valueOf(draftsModel.user_id), Integer.valueOf(draftsModel.type)});
        }
        c.close();
        db.close();
    }

    public void delDrafts(String resources_id, int userID, int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        try {
            db.execSQL("delete from post_drafts where resources_id=? and user_id = ? and type = ? ", new Object[]{resources_id, Integer.valueOf(userID), Integer.valueOf(type)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public List<SearchItem> getSearchReconds(int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        List<SearchItem> list = new ArrayList();
        Cursor c = db.rawQuery("select * from search_records where type = ? and content is not null ORDER BY _id DESC limit 0," + (type == 2 ? 5 : 10), new String[]{type + ""});
        while (c.moveToNext()) {
            SearchItem searchItem = new SearchItem();
            searchItem._id = c.getInt(c.getColumnIndex("_id"));
            searchItem.content = c.getString(c.getColumnIndex("content"));
            searchItem.type = c.getInt(c.getColumnIndex("type"));
            searchItem.contentid = c.getInt(c.getColumnIndex("content_id"));
            list.add(searchItem);
        }
        c.close();
        db.close();
        return list;
    }

    public void addSearchRecond(SearchItem item) {
        if (!TextUtils.isEmpty(item.content)) {
            SQLiteDatabase db = this.helper.getWritableDatabase();
            Cursor c = db.rawQuery("select * from search_records where content = ? and type = ? ", new String[]{item.content, item.type + ""});
            if (c.moveToNext()) {
                db.execSQL("delete from search_records where _id=?", new Object[]{Integer.valueOf(c.getInt(c.getColumnIndex("_id")))});
            }
            db.execSQL("insert into search_records (content,content_id,type) values(?,?,?)", new Object[]{item.content, Integer.valueOf(item.contentid), Integer.valueOf(item.type)});
            c.close();
            db.close();
        }
    }

    public void delSearchRecond(int _id) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.execSQL("delete from search_records where _id=?", new Object[]{Integer.valueOf(_id)});
        db.close();
    }

    public void deleteAllSerchRecord(int type) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(SparkDBHelper.SEARCH_RECORDS, "type = ?", new String[]{String.valueOf(type)});
        db.close();
    }

    public List<CarModelInfo> getCarComparisonRecord() {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        List<CarModelInfo> list = new ArrayList();
        Cursor c = db.rawQuery("select * from car_comparison ORDER BY _id ASC limit 0,5050", null);
        while (c.moveToNext()) {
            CarModelInfo info = new CarModelInfo();
            info.id = c.getInt(c.getColumnIndex("id"));
            info.name = c.getString(c.getColumnIndex("name"));
            list.add(info);
        }
        c.close();
        db.close();
        return list;
    }

    public List<Integer> getCarComparisonIDList() {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        List<Integer> list = new ArrayList();
        Cursor c = db.rawQuery("select * from car_comparison ORDER BY _id DESC limit 0,50", null);
        while (c.moveToNext()) {
            list.add(Integer.valueOf(c.getInt(c.getColumnIndex("id"))));
        }
        c.close();
        db.close();
        return list;
    }

    public void addCarComparisonRecond(String carXName, CarModelInfo info) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from car_comparison where id = ? ", new String[]{info.id + ""});
        if (c.moveToNext()) {
            db.execSQL("delete from car_comparison where id=?", new Object[]{Integer.valueOf(info.id)});
        }
        String str = "insert into car_comparison (id,name) values(?,?)";
        Object[] objArr = new Object[2];
        objArr[0] = Integer.valueOf(info.id);
        objArr[1] = TextUtils.isEmpty(carXName) ? info.name : carXName + " " + info.name;
        db.execSQL(str, objArr);
        c.close();
        db.close();
    }

    public void addCarComparisonRecond(CarModelInfo info) {
        addCarComparisonRecond("", info);
    }

    public void delCarComparisonRecond(String idGroup) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.execSQL("delete from car_comparison where id in (" + idGroup + l.t);
        db.close();
    }

    public void addRecommendHistory(String jsonData) {
        if (!TextUtils.isEmpty(jsonData)) {
            try {
                int userId;
                SQLiteDatabase db = this.helper.getWritableDatabase();
                if (FengApplication.getInstance().isLoginUser()) {
                    userId = FengApplication.getInstance().getUserInfo().id;
                } else {
                    userId = SharedUtil.getInt(FengApplication.getInstance(), "usertouristId", 0);
                }
                Cursor c = db.rawQuery("select * from recommend_history where user_id = ? ", new String[]{userId + ""});
                if (c.moveToNext()) {
                    db.execSQL("update recommend_history set json_data=? where user_id=?", new Object[]{jsonData, Integer.valueOf(userId)});
                } else {
                    db.execSQL("insert into recommend_history (user_id,json_data) values(?,?)", new Object[]{Integer.valueOf(userId), jsonData});
                }
                c.close();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public LinkedHashMap<Integer, Integer> getRecommendHistory() {
        int userId;
        SQLiteDatabase db = this.helper.getWritableDatabase();
        LinkedHashMap<Integer, Integer> linkedHashMap = null;
        if (FengApplication.getInstance().isLoginUser()) {
            userId = FengApplication.getInstance().getUserInfo().id;
        } else {
            userId = SharedUtil.getInt(FengApplication.getInstance(), "usertouristId", 0);
        }
        Cursor c = db.rawQuery("select * from recommend_history where user_id = ? ", new String[]{userId + ""});
        if (c.moveToNext()) {
            linkedHashMap = JsonUtil.RecommendHistory(c.getString(c.getColumnIndex("json_data")));
        }
        if (linkedHashMap == null) {
            linkedHashMap = new LinkedHashMap();
        }
        c.close();
        db.close();
        return linkedHashMap;
    }
}
