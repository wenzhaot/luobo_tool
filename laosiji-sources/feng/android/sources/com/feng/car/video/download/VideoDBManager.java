package com.feng.car.video.download;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.feng.car.db.SparkDBHelper;
import com.feng.car.entity.download.VideoDownloadInfo;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class VideoDBManager {
    private SparkDBHelper helper;

    public VideoDBManager(Context context) {
        this.helper = new SparkDBHelper(context);
    }

    public List<VideoDownloadInfo> getDownloadInfoList() {
        List<VideoDownloadInfo> list = new ArrayList();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.helper.getWritableDatabase();
            c = db.rawQuery("select * from video_cache", null);
            while (c.moveToNext()) {
                VideoDownloadInfo info = new VideoDownloadInfo();
                info._id = c.getInt(c.getColumnIndex("_id"));
                info.url = c.getString(c.getColumnIndex("url"));
                try {
                    info.parse(new JSONObject(c.getString(c.getColumnIndex("data"))));
                    list.add(info);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            return list;
        } finally {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return list;
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    public com.feng.car.entity.download.VideoDownloadInfo getVideoDownloadInfoByUrl(java.lang.String r8) {
        /*
        r7 = this;
        r4 = com.feng.library.utils.StringUtil.isEmpty(r8);
        if (r4 == 0) goto L_0x0008;
    L_0x0006:
        r3 = 0;
    L_0x0007:
        return r3;
    L_0x0008:
        r3 = new com.feng.car.entity.download.VideoDownloadInfo;
        r3.<init>();
        r1 = 0;
        r0 = 0;
        r4 = r7.helper;	 Catch:{ Exception -> 0x0065 }
        r1 = r4.getWritableDatabase();	 Catch:{ Exception -> 0x0065 }
        r4 = "select * from video_cache where url = ? ";
        r5 = 1;
        r5 = new java.lang.String[r5];	 Catch:{ Exception -> 0x0065 }
        r6 = 0;
        r5[r6] = r8;	 Catch:{ Exception -> 0x0065 }
        r0 = r1.rawQuery(r4, r5);	 Catch:{ Exception -> 0x0065 }
        r4 = r0.moveToNext();	 Catch:{ Exception -> 0x0065 }
        if (r4 == 0) goto L_0x0055;
    L_0x0028:
        r4 = "_id";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0065 }
        r4 = r0.getInt(r4);	 Catch:{ Exception -> 0x0065 }
        r3._id = r4;	 Catch:{ Exception -> 0x0065 }
        r4 = "url";
        r4 = r0.getColumnIndex(r4);	 Catch:{ Exception -> 0x0065 }
        r4 = r0.getString(r4);	 Catch:{ Exception -> 0x0065 }
        r3.url = r4;	 Catch:{ Exception -> 0x0065 }
        r4 = new org.json.JSONObject;	 Catch:{ JSONException -> 0x0060 }
        r5 = "data";
        r5 = r0.getColumnIndex(r5);	 Catch:{ JSONException -> 0x0060 }
        r5 = r0.getString(r5);	 Catch:{ JSONException -> 0x0060 }
        r4.<init>(r5);	 Catch:{ JSONException -> 0x0060 }
        r3.parse(r4);	 Catch:{ JSONException -> 0x0060 }
    L_0x0055:
        if (r0 == 0) goto L_0x005a;
    L_0x0057:
        r0.close();
    L_0x005a:
        if (r1 == 0) goto L_0x0007;
    L_0x005c:
        r1.close();
        goto L_0x0007;
    L_0x0060:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ Exception -> 0x0065 }
        goto L_0x0055;
    L_0x0065:
        r2 = move-exception;
        r2.printStackTrace();	 Catch:{ all -> 0x0074 }
        if (r0 == 0) goto L_0x006e;
    L_0x006b:
        r0.close();
    L_0x006e:
        if (r1 == 0) goto L_0x0007;
    L_0x0070:
        r1.close();
        goto L_0x0007;
    L_0x0074:
        r4 = move-exception;
        if (r0 == 0) goto L_0x007a;
    L_0x0077:
        r0.close();
    L_0x007a:
        if (r1 == 0) goto L_0x007f;
    L_0x007c:
        r1.close();
    L_0x007f:
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.download.VideoDBManager.getVideoDownloadInfoByUrl(java.lang.String):com.feng.car.entity.download.VideoDownloadInfo");
    }

    public VideoDownloadInfo getVideoDownloadInfoByMediaId(int mediaid) {
        VideoDownloadInfo info = new VideoDownloadInfo();
        SQLiteDatabase db = null;
        Cursor c = null;
        try {
            db = this.helper.getWritableDatabase();
            c = db.rawQuery("select * from video_cache where mediaid = ? ", new String[]{String.valueOf(mediaid)});
            if (c.moveToNext()) {
                info._id = c.getInt(c.getColumnIndex("_id"));
                info.url = c.getString(c.getColumnIndex("url"));
                try {
                    info.parse(new JSONObject(c.getString(c.getColumnIndex("data"))));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        } catch (Throwable th) {
            if (c != null) {
                c.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return info;
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    public void addDownloadInfo(com.feng.car.entity.download.VideoDownloadInfo r7) {
        /*
        r6 = this;
        r0 = 0;
        r2 = r6.helper;	 Catch:{ Exception -> 0x002b }
        r0 = r2.getWritableDatabase();	 Catch:{ Exception -> 0x002b }
        r2 = "insert into video_cache (url,mediaid,data) values(?,?,?)";
        r3 = 3;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x002b }
        r4 = 0;
        r5 = r7.url;	 Catch:{ Exception -> 0x002b }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002b }
        r4 = 1;
        r5 = r7.mediaid;	 Catch:{ Exception -> 0x002b }
        r5 = java.lang.Integer.valueOf(r5);	 Catch:{ Exception -> 0x002b }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002b }
        r4 = 2;
        r5 = r7.toJson();	 Catch:{ Exception -> 0x002b }
        r3[r4] = r5;	 Catch:{ Exception -> 0x002b }
        r0.execSQL(r2, r3);	 Catch:{ Exception -> 0x002b }
        if (r0 == 0) goto L_0x002a;
    L_0x0027:
        r0.close();
    L_0x002a:
        return;
    L_0x002b:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0035 }
        if (r0 == 0) goto L_0x002a;
    L_0x0031:
        r0.close();
        goto L_0x002a;
    L_0x0035:
        r2 = move-exception;
        if (r0 == 0) goto L_0x003b;
    L_0x0038:
        r0.close();
    L_0x003b:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.download.VideoDBManager.addDownloadInfo(com.feng.car.entity.download.VideoDownloadInfo):void");
    }

    /* JADX WARNING: Failed to extract finally block: empty outs */
    public void deleteDownloadInfo(com.feng.car.entity.download.VideoDownloadInfo r7) {
        /*
        r6 = this;
        r0 = 0;
        r2 = r6.helper;	 Catch:{ Exception -> 0x001b }
        r0 = r2.getWritableDatabase();	 Catch:{ Exception -> 0x001b }
        r2 = "delete from video_cache where url=?";
        r3 = 1;
        r3 = new java.lang.Object[r3];	 Catch:{ Exception -> 0x001b }
        r4 = 0;
        r5 = r7.url;	 Catch:{ Exception -> 0x001b }
        r3[r4] = r5;	 Catch:{ Exception -> 0x001b }
        r0.execSQL(r2, r3);	 Catch:{ Exception -> 0x001b }
        if (r0 == 0) goto L_0x001a;
    L_0x0017:
        r0.close();
    L_0x001a:
        return;
    L_0x001b:
        r1 = move-exception;
        r1.printStackTrace();	 Catch:{ all -> 0x0025 }
        if (r0 == 0) goto L_0x001a;
    L_0x0021:
        r0.close();
        goto L_0x001a;
    L_0x0025:
        r2 = move-exception;
        if (r0 == 0) goto L_0x002b;
    L_0x0028:
        r0.close();
    L_0x002b:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.video.download.VideoDBManager.deleteDownloadInfo(com.feng.car.entity.download.VideoDownloadInfo):void");
    }

    public boolean updateDownloadInfo(VideoDownloadInfo info) {
        int rownum = -1;
        SQLiteDatabase db = null;
        try {
            db = this.helper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("url", info.url);
            values.put("data", info.toJson());
            rownum = db.update(SparkDBHelper.VIDEO_CACHE, values, "url=?", new String[]{info.url});
            if (db != null) {
                db.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (db != null) {
                db.close();
            }
        } catch (Throwable th) {
            if (db != null) {
                db.close();
            }
        }
        if (rownum != -1) {
            return true;
        }
        return false;
    }
}
