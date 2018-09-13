package com.meizu.cloud.pushsdk.pushtracer.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.meizu.cloud.pushsdk.pushtracer.dataload.DataLoad;
import com.meizu.cloud.pushsdk.pushtracer.dataload.TrackerDataload;
import com.meizu.cloud.pushsdk.pushtracer.emitter.EmittableEvents;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EventStore implements Store {
    private String TAG = EventStore.class.getSimpleName();
    private String[] allColumns = new String[]{"id", "eventData", "dateCreated"};
    private SQLiteDatabase database;
    private EventStoreHelper dbHelper;
    private long lastInsertedRowId = -1;
    private int sendLimit;

    public EventStore(Context context, int sendLimit) {
        this.dbHelper = EventStoreHelper.getInstance(context, getDataBaseName(context));
        open();
        this.sendLimit = sendLimit;
    }

    private String getDataBaseName(Context context) {
        String processName = MzSystemUtils.getProcessName(context);
        if (TextUtils.isEmpty(processName)) {
            return EventStoreHelper.DATABASE_NAME;
        }
        return processName + "_" + EventStoreHelper.DATABASE_NAME;
    }

    public void add(DataLoad dataLoad) {
        insertEvent(dataLoad);
    }

    public boolean isOpen() {
        return isDatabaseOpen();
    }

    public void open() {
        if (!isDatabaseOpen()) {
            try {
                this.database = this.dbHelper.getWritableDatabase();
                this.database.enableWriteAheadLogging();
            } catch (Exception e) {
                Logger.e(this.TAG, " open database error " + e.getMessage(), new Object[0]);
            }
        }
    }

    public void close() {
        this.dbHelper.close();
    }

    public long insertEvent(DataLoad dataLoad) {
        if (isDatabaseOpen()) {
            byte[] bytes = serialize(dataLoad.getMap());
            ContentValues values = new ContentValues(2);
            values.put("eventData", bytes);
            this.lastInsertedRowId = this.database.insert("events", null, values);
        }
        Logger.d(this.TAG, "Added event to database: " + this.lastInsertedRowId, new Object[0]);
        return this.lastInsertedRowId;
    }

    public boolean removeEvent(long id) {
        int retval = -1;
        if (isDatabaseOpen()) {
            retval = this.database.delete("events", "id=" + id, null);
        }
        Logger.d(this.TAG, "Removed event from database: " + id, new Object[0]);
        if (retval == 1) {
            return true;
        }
        return false;
    }

    public boolean removeAllEvents() {
        int retval = -1;
        if (isDatabaseOpen()) {
            retval = this.database.delete("events", null, null);
        }
        Logger.d(this.TAG, "Removing all events from database.", new Object[0]);
        if (retval == 0) {
            return true;
        }
        return false;
    }

    public static byte[] serialize(Map<String, String> map) {
        try {
            ByteArrayOutputStream mem_out = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(mem_out);
            out.writeObject(map);
            out.close();
            mem_out.close();
            return mem_out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, String> deserializer(byte[] bytes) {
        Exception e;
        try {
            ByteArrayInputStream mem_in = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(mem_in);
            HashMap map = (HashMap) in.readObject();
            in.close();
            mem_in.close();
            return map;
        } catch (ClassNotFoundException e2) {
            e = e2;
        } catch (IOException e3) {
            e = e3;
        }
        e.printStackTrace();
        return null;
    }

    public List<Map<String, Object>> queryDatabase(String query, String orderBy) {
        List<Map<String, Object>> res = new ArrayList();
        if (isDatabaseOpen()) {
            Cursor cursor = this.database.query("events", this.allColumns, query, null, null, null, orderBy);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Map<String, Object> eventMetadata = new HashMap();
                eventMetadata.put("id", Long.valueOf(cursor.getLong(0)));
                eventMetadata.put("eventData", deserializer(cursor.getBlob(1)));
                eventMetadata.put("dateCreated", cursor.getString(2));
                cursor.moveToNext();
                res.add(eventMetadata);
            }
            cursor.close();
        }
        return res;
    }

    public long getSize() {
        if (isDatabaseOpen()) {
            return DatabaseUtils.queryNumEntries(this.database, "events");
        }
        return 0;
    }

    public long getLastInsertedRowId() {
        return this.lastInsertedRowId;
    }

    public EmittableEvents getEmittableEvents() {
        LinkedList<Long> eventIds = new LinkedList();
        ArrayList<DataLoad> events = new ArrayList();
        for (Map<String, Object> eventMetadata : getDescEventsInRange(this.sendLimit)) {
            TrackerDataload payload = new TrackerDataload();
            payload.addMap((Map) eventMetadata.get("eventData"));
            eventIds.add((Long) eventMetadata.get("id"));
            events.add(payload);
        }
        return new EmittableEvents(events, eventIds);
    }

    public Map<String, Object> getEvent(long id) {
        List<Map<String, Object>> res = queryDatabase("id=" + id, null);
        if (res.isEmpty()) {
            return null;
        }
        return (Map) res.get(0);
    }

    public List<Map<String, Object>> getAllEvents() {
        return queryDatabase(null, null);
    }

    public List<Map<String, Object>> getDescEventsInRange(int range) {
        return queryDatabase(null, "id ASC LIMIT " + range);
    }

    public boolean isDatabaseOpen() {
        return this.database != null && this.database.isOpen();
    }
}
