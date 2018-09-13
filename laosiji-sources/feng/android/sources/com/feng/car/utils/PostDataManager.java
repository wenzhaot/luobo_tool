package com.feng.car.utils;

import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.entity.sendpost.PostEdit;
import java.util.ArrayList;
import java.util.List;

public class PostDataManager {
    private static PostDataManager instance;
    private List<PostEdit> mList;
    private List<UploadQiNiuLocalPathInfo> mLocalSelMedia = new ArrayList();

    public static PostDataManager getInstance() {
        if (instance == null) {
            instance = new PostDataManager();
        }
        return instance;
    }

    private PostDataManager() {
    }

    public List<UploadQiNiuLocalPathInfo> getLocalSelMedia() {
        if (this.mLocalSelMedia == null) {
            this.mLocalSelMedia = new ArrayList();
        }
        return this.mLocalSelMedia;
    }

    public void addLocalSelMedia(UploadQiNiuLocalPathInfo localSelMedia) {
        getLocalSelMedia().add(localSelMedia);
    }

    public void addLocalSelMedias(List<UploadQiNiuLocalPathInfo> localSelMedias) {
        getLocalSelMedia().clear();
        getLocalSelMedia().addAll(localSelMedias);
    }

    public List<PostEdit> getList() {
        return this.mList;
    }

    public void setList(List<PostEdit> list) {
        this.mList = list;
    }

    public void releaseData() {
        this.mList = null;
        this.mLocalSelMedia = null;
        instance = null;
    }

    public boolean hasSelectVideo() {
        for (UploadQiNiuLocalPathInfo info : getLocalSelMedia()) {
            if (info.type == 3) {
                return true;
            }
        }
        return false;
    }
}
