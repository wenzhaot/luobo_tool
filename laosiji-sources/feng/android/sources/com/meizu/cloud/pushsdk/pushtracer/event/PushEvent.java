package com.meizu.cloud.pushsdk.pushtracer.event;

import com.meizu.cloud.pushsdk.pushtracer.constant.Parameters;
import com.meizu.cloud.pushsdk.pushtracer.dataload.TrackerDataload;

public class PushEvent extends Event {
    private String deviceId;
    private String eventCreateTime;
    private String eventName;
    private String messageSeq;
    private String packageName;
    private String pushsdkVersion;
    private String seqId;
    private String taskId;

    public static abstract class Builder<T extends Builder<T>> extends com.meizu.cloud.pushsdk.pushtracer.event.Event.Builder<T> {
        private String deviceId;
        private String eventCreateTime;
        private String eventName;
        private String messageSeq;
        private String packageName;
        private String pushsdkVersion;
        private String seqId;
        private String taskId;

        public T eventName(String eventName) {
            this.eventName = eventName;
            return (Builder) self();
        }

        public T taskId(String taskId) {
            this.taskId = taskId;
            return (Builder) self();
        }

        public T deviceId(String deviceId) {
            this.deviceId = deviceId;
            return (Builder) self();
        }

        public T pushsdkVersion(String pushsdkVersion) {
            this.pushsdkVersion = pushsdkVersion;
            return (Builder) self();
        }

        public T packageName(String packageName) {
            this.packageName = packageName;
            return (Builder) self();
        }

        public T seqId(String seqId) {
            this.seqId = seqId;
            return (Builder) self();
        }

        public T messageSeq(String messageSeq) {
            this.messageSeq = messageSeq;
            return (Builder) self();
        }

        public T eventCreateTime(String eventCreateTime) {
            this.eventCreateTime = eventCreateTime;
            return (Builder) self();
        }

        public PushEvent build() {
            return new PushEvent(this);
        }
    }

    private static class Builder2 extends Builder<Builder2> {
        private Builder2() {
        }

        protected Builder2 self() {
            return this;
        }
    }

    public static Builder<?> builder() {
        return new Builder2();
    }

    protected PushEvent(Builder<?> builder) {
        super(builder);
        this.taskId = builder.taskId;
        this.deviceId = builder.deviceId;
        this.eventName = builder.eventName;
        this.pushsdkVersion = builder.pushsdkVersion;
        this.packageName = builder.packageName;
        this.seqId = builder.seqId;
        this.messageSeq = builder.messageSeq;
        this.eventCreateTime = builder.eventCreateTime;
    }

    public TrackerDataload getDataLoad() {
        TrackerDataload dataLoad = new TrackerDataload();
        dataLoad.add("en", this.eventName);
        dataLoad.add(Parameters.TASK_ID, this.taskId);
        dataLoad.add("di", this.deviceId);
        dataLoad.add("pv", this.pushsdkVersion);
        dataLoad.add(Parameters.PACKAGE_NAME, this.packageName);
        dataLoad.add(Parameters.SEQ_ID, this.seqId);
        dataLoad.add(Parameters.MESSAGE_SEQ, this.messageSeq);
        dataLoad.add(Parameters.EVENT_CREATE_TIME, this.eventCreateTime);
        return putDefaultParams(dataLoad);
    }
}
