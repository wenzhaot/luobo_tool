package com.feng.car.event;

import android.support.annotation.NonNull;
import com.feng.car.entity.sendpost.PostEdit;
import java.util.Map;

public class IDCardUploadFinishEvent {
    public Map<Integer, PostEdit> postEdits;

    public IDCardUploadFinishEvent(@NonNull Map<Integer, PostEdit> postEdits) {
        this.postEdits = postEdits;
    }
}
