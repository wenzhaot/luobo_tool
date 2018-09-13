package com.meizu.cloud.pushsdk.pushtracer.emitter;

import com.meizu.cloud.pushsdk.networking.http.Request;
import java.util.LinkedList;

public class ReadyRequest {
    private final LinkedList<Long> ids;
    private final boolean oversize;
    private final Request request;

    public ReadyRequest(boolean oversize, Request request, LinkedList<Long> ids) {
        this.oversize = oversize;
        this.request = request;
        this.ids = ids;
    }

    public Request getRequest() {
        return this.request;
    }

    public LinkedList<Long> getEventIds() {
        return this.ids;
    }

    public boolean isOversize() {
        return this.oversize;
    }
}
