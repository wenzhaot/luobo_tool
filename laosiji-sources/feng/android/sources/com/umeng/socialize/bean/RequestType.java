package com.umeng.socialize.bean;

import com.meizu.cloud.pushsdk.constants.PushConstants;

public enum RequestType {
    SOCIAL {
        public String toString() {
            return PushConstants.PUSH_TYPE_NOTIFY;
        }
    },
    ANALYTICS {
        public String toString() {
            return PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
        }
    },
    API {
        public String toString() {
            return PushConstants.PUSH_TYPE_UPLOAD_LOG;
        }
    }
}
