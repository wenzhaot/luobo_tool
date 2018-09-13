package com.meizu.cloud.pushsdk.platform.message;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SubTagsStatus extends BasicPushStatus {
    private String pushId;
    private List<Tag> tagList;

    public class Tag implements Serializable {
        private int tagId;
        private String tagName;

        public String toString() {
            return "Tag{tagId=" + this.tagId + ", tagName='" + this.tagName + '\'' + '}';
        }

        public int getTagId() {
            return this.tagId;
        }

        public void setTagId(int tagId) {
            this.tagId = tagId;
        }

        public String getTagName() {
            return this.tagName;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }

    public SubTagsStatus(String json) {
        super(json);
    }

    public void parseValueData(JSONObject jsonObject) throws JSONException {
        if (!jsonObject.isNull(PushConstants.KEY_PUSH_ID)) {
            setPushId(jsonObject.getString(PushConstants.KEY_PUSH_ID));
        }
        if (!jsonObject.isNull("tags")) {
            JSONArray tagsArray = jsonObject.getJSONArray("tags");
            List<Tag> tags = new ArrayList();
            for (int i = 0; i < tagsArray.length(); i++) {
                JSONObject tagObj = tagsArray.getJSONObject(i);
                Tag tag = new Tag();
                if (!tagObj.isNull("tagId")) {
                    tag.tagId = tagObj.getInt("tagId");
                }
                if (!tagObj.isNull("tagName")) {
                    tag.tagName = tagObj.getString("tagName");
                }
                tags.add(tag);
            }
            DebugLogger.e(BasicPushStatus.TAG, "tags " + tags);
            setTagList(tags);
        }
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public List<Tag> getTagList() {
        return this.tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public String toString() {
        return super.toString() + " SubTagsStatus{pushId='" + this.pushId + '\'' + ", tagList=" + this.tagList + '}';
    }
}
