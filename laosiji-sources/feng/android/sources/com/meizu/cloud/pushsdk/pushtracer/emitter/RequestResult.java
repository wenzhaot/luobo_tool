package com.meizu.cloud.pushsdk.pushtracer.emitter;

import java.util.LinkedList;

public class RequestResult {
    private final LinkedList<Long> eventIds;
    private final boolean success;

    public RequestResult(boolean success, LinkedList<Long> eventIds) {
        this.success = success;
        this.eventIds = eventIds;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public LinkedList<Long> getEventIds() {
        return this.eventIds;
    }
}
