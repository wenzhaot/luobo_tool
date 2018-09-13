package com.feng.car.entity.sendpost;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.feng.car.listener.PostDescribeChangeListener;
import com.google.gson.annotations.Expose;

public class PostEdit extends BaseObservable {
    @Expose
    private String commonurl = "";
    @Expose
    private String content = "";
    @Expose
    private String coverimage = "";
    @Expose
    private String description = "";
    @Expose
    private String hash = "";
    @Expose
    private String height = "";
    @Expose
    private int id;
    public boolean isRequestFocus = false;
    public transient boolean localVideo = false;
    public int localVideoHight;
    public int localVideoWidth;
    public transient boolean localeditcover = false;
    protected transient PostDescribeChangeListener mTextChange = null;
    @Expose
    private String mime = "";
    @Expose
    private String outsideurl = "";
    public boolean showDescribe = false;
    public boolean showIcon = true;
    @Expose
    private String size = "";
    @Expose
    private int sort;
    public int srcHight;
    public long srcSize;
    public String srcUrl = "";
    public int srcWidth;
    public String tag = "";
    @Expose
    private int time;
    @Expose
    private String title = "";
    @Expose
    private int type;
    @Expose
    private String url = "";
    public PostEdit videoCoverImage;
    @Expose
    private int videodefinition;
    @Expose
    private String width = "";

    @Bindable
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(25);
    }

    @Bindable
    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
        notifyPropertyChanged(69);
    }

    @Bindable
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
        notifyPropertyChanged(17);
    }

    @Bindable
    public int getSort() {
        return this.sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
        notifyPropertyChanged(64);
    }

    @Bindable
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
        if (this.mTextChange != null) {
            this.mTextChange.onChangeDescribe(description);
        }
        notifyPropertyChanged(20);
    }

    @Bindable
    public String getHash() {
        return this.hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
        notifyPropertyChanged(22);
    }

    @Bindable
    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(73);
    }

    @Bindable
    public String getMime() {
        return this.mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
        notifyPropertyChanged(40);
    }

    @Bindable
    public String getSize() {
        return this.size;
    }

    public void setSize(String size) {
        this.size = size;
        notifyPropertyChanged(61);
    }

    @Bindable
    public String getWidth() {
        return this.width;
    }

    public void setWidth(String width) {
        this.width = width;
        notifyPropertyChanged(78);
    }

    @Bindable
    public String getHeight() {
        return this.height;
    }

    public void setHeight(String height) {
        this.height = height;
        notifyPropertyChanged(23);
    }

    @Bindable
    public int getTime() {
        return this.time;
    }

    public void setTime(int time) {
        this.time = time;
        notifyPropertyChanged(67);
    }

    @Bindable
    public int getVideodefinition() {
        return this.videodefinition;
    }

    public void setVideodefinition(int videodefinition) {
        this.videodefinition = videodefinition;
        notifyPropertyChanged(77);
    }

    @Bindable
    public String getCommonurl() {
        return this.commonurl;
    }

    public void setCommonurl(String commonurl) {
        this.commonurl = commonurl;
        notifyPropertyChanged(16);
    }

    @Bindable
    public String getOutsideurl() {
        return this.outsideurl;
    }

    public void setOutsideurl(String outsideurl) {
        this.outsideurl = outsideurl;
        notifyPropertyChanged(45);
    }

    @Bindable
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
        notifyPropertyChanged(68);
    }

    @Bindable
    public String getCoverimage() {
        return this.coverimage;
    }

    public void setCoverimage(String hash) {
        this.coverimage = hash;
        notifyPropertyChanged(18);
    }

    @Bindable
    public boolean isShowDescribe() {
        return this.showDescribe;
    }

    public void setShowDescribe(boolean showDescribe) {
        this.showDescribe = showDescribe;
        notifyPropertyChanged(59);
    }

    @Bindable
    public boolean isShowIcon() {
        return this.showIcon;
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
        notifyPropertyChanged(60);
    }

    public PostEdit(int type, String content) {
        setType(type);
        setContent(content);
        initCoverimage();
    }

    public PostEdit(int type, String description, String srcUrl) {
        this.type = type;
        this.description = description;
        this.srcUrl = srcUrl;
        initCoverimage();
    }

    public PostEdit(int type, int srcWidth, int srcHight, String description, String srcUrl) {
        setType(type);
        setDescription(description);
        this.srcUrl = srcUrl;
        this.srcWidth = srcWidth;
        this.srcHight = srcHight;
        initCoverimage();
    }

    public PostEdit(int type, String description, String srcUrl, int width, int hight) {
        setType(type);
        setDescription(description);
        this.srcUrl = srcUrl;
        this.localVideoWidth = width;
        this.localVideoHight = hight;
        initCoverimage();
    }

    private void initCoverimage() {
        if (this.type == 3) {
            this.videoCoverImage = new PostEdit(10, "");
        }
    }

    public void setOnTextChange(PostDescribeChangeListener textChange) {
        this.mTextChange = textChange;
    }
}
