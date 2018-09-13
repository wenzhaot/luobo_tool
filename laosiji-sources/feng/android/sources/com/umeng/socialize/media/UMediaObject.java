package com.umeng.socialize.media;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.util.Map;

public interface UMediaObject {

    public enum MediaType {
        IMAGE {
            public String toString() {
                return PushConstants.PUSH_TYPE_NOTIFY;
            }
        },
        VEDIO {
            public String toString() {
                return PushConstants.PUSH_TYPE_THROUGH_MESSAGE;
            }
        },
        MUSIC {
            public String toString() {
                return PushConstants.PUSH_TYPE_UPLOAD_LOG;
            }
        },
        TEXT {
            public String toString() {
                return "3";
            }
        },
        TEXT_IMAGE {
            public String toString() {
                return "4";
            }
        },
        WEBPAGE {
            public String toString() {
                return "5";
            }
        }
    }

    MediaType getMediaType();

    boolean isUrlMedia();

    byte[] toByte();

    String toUrl();

    Map<String, Object> toUrlExtraParams();
}
