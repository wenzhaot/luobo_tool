package com.meizu.cloud.pushsdk.pushtracer.storage;

import com.meizu.cloud.pushsdk.pushtracer.dataload.DataLoad;
import com.meizu.cloud.pushsdk.pushtracer.dataload.TrackerDataload;
import com.meizu.cloud.pushsdk.pushtracer.emitter.EmittableEvents;
import com.meizu.cloud.pushsdk.pushtracer.utils.Logger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryStore implements Store {
    private static final String TAG = "MemoryStore";
    private AtomicLong atomicLong = new AtomicLong(0);
    private List<Long> dataKeyList = new CopyOnWriteArrayList();
    private int sendLimit;
    private Map<Long, byte[]> storeMap = new ConcurrentHashMap();

    public MemoryStore(int sendLimit) {
        this.sendLimit = sendLimit;
    }

    public void add(DataLoad dataLoad) {
        insertEvent(dataLoad);
    }

    public boolean isOpen() {
        return true;
    }

    public void close() {
        this.storeMap.clear();
        this.atomicLong.set(0);
        this.dataKeyList.clear();
    }

    public boolean removeEvent(long id) {
        return this.dataKeyList.remove(Long.valueOf(id)) && this.storeMap.remove(Long.valueOf(id)) != null;
    }

    public boolean removeAllEvents() {
        this.storeMap.clear();
        this.dataKeyList.clear();
        return true;
    }

    public long getSize() {
        return (long) this.dataKeyList.size();
    }

    public EmittableEvents getEmittableEvents() {
        int largeEmiteSize;
        LinkedList<Long> eventIds = new LinkedList();
        ArrayList<DataLoad> events = new ArrayList();
        int keySize = (int) getSize();
        if (keySize > this.sendLimit) {
            largeEmiteSize = this.sendLimit;
        } else {
            largeEmiteSize = keySize;
        }
        for (int i = 0; i < largeEmiteSize; i++) {
            Object key = this.dataKeyList.get(i);
            if (key != null) {
                TrackerDataload payload = new TrackerDataload();
                payload.addMap(EventStore.deserializer((byte[]) this.storeMap.get(key)));
                Logger.i(TAG, " current key " + key + " payload " + payload, new Object[0]);
                eventIds.add((Long) key);
                events.add(payload);
            }
        }
        return new EmittableEvents(events, eventIds);
    }

    public long insertEvent(DataLoad dataLoad) {
        byte[] bytes = EventStore.serialize(dataLoad.getMap());
        long key = this.atomicLong.getAndIncrement();
        this.dataKeyList.add(Long.valueOf(key));
        this.storeMap.put(Long.valueOf(key), bytes);
        return key;
    }

    public Map<String, Object> getEvent(long id) {
        byte[] eventByte = (byte[]) this.storeMap.get(Long.valueOf(id));
        if (eventByte != null) {
            return EventStore.deserializer(eventByte);
        }
        return null;
    }
}
