package com.meizu.cloud.pushsdk.handler.impl.model;

import android.text.TextUtils;
import com.meizu.cloud.pushsdk.pushtracer.dataload.TrackerDataload;

public class PlatformMessage {
    public static final String PLATFORM_DEVICE_ID = "device_id";
    public static final String PLATFORM_PUSH_TIMESTAMP = "push_timestamp";
    public static final String PLATFORM_SEQ_ID = "seq_id";
    public static final String PLATFORM_TASK_ID = "task_id";
    String deviceId;
    String pushTimesTamp;
    String seqId;
    String taskId;

    public static class Builder {
        private String deviceId;
        private String pushTimesTamp;
        private String seqId;
        private String taskId;

        public Builder taskId(String taskId) {
            this.taskId = taskId;
            return this;
        }

        public Builder seqId(String seqId) {
            this.seqId = seqId;
            return this;
        }

        public Builder pushTimesTamp(String pushTimesTamp) {
            this.pushTimesTamp = pushTimesTamp;
            return this;
        }

        public Builder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public PlatformMessage build() {
            return new PlatformMessage(this);
        }
    }

    public PlatformMessage(Builder builder) {
        this.taskId = !TextUtils.isEmpty(builder.taskId) ? builder.taskId : "";
        this.seqId = !TextUtils.isEmpty(builder.seqId) ? builder.seqId : "";
        this.pushTimesTamp = !TextUtils.isEmpty(builder.pushTimesTamp) ? builder.pushTimesTamp : "";
        this.deviceId = !TextUtils.isEmpty(builder.deviceId) ? builder.deviceId : "";
    }

    public static Builder builder() {
        return new Builder();
    }

    public String toJson() {
        TrackerDataload trackerDataload = new TrackerDataload();
        trackerDataload.add("task_id", this.taskId);
        trackerDataload.add(PLATFORM_SEQ_ID, this.seqId);
        trackerDataload.add(PLATFORM_PUSH_TIMESTAMP, this.pushTimesTamp);
        trackerDataload.add("device_id", this.deviceId);
        return trackerDataload.toString();
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSeqId() {
        return this.seqId;
    }

    public void setSeqId(String seqId) {
        this.seqId = seqId;
    }

    public String getPushTimesTamp() {
        return this.pushTimesTamp;
    }

    public void setPushTimesTamp(String pushTimesTamp) {
        this.pushTimesTamp = pushTimesTamp;
    }

    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
