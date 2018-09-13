package com.meizu.cloud.pushsdk.pushtracer.emitter;

import com.meizu.cloud.pushsdk.pushtracer.dataload.DataLoad;
import java.util.ArrayList;
import java.util.LinkedList;

public class EmittableEvents {
    private final LinkedList<Long> eventIds;
    private final ArrayList<DataLoad> events;

    public EmittableEvents(ArrayList<DataLoad> events, LinkedList<Long> eventIds) {
        this.events = events;
        this.eventIds = eventIds;
    }

    public ArrayList<DataLoad> getEvents() {
        return this.events;
    }

    public LinkedList<Long> getEventIds() {
        return this.eventIds;
    }
}
