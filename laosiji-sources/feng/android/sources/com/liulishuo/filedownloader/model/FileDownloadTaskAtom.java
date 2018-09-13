package com.liulishuo.filedownloader.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class FileDownloadTaskAtom implements Parcelable {
    public static final Creator<FileDownloadTaskAtom> CREATOR = new Creator<FileDownloadTaskAtom>() {
        public FileDownloadTaskAtom createFromParcel(Parcel source) {
            return new FileDownloadTaskAtom(source);
        }

        public FileDownloadTaskAtom[] newArray(int size) {
            return new FileDownloadTaskAtom[size];
        }
    };
    private int id;
    private String path;
    private long totalBytes;
    private String url;

    public FileDownloadTaskAtom(String url, String path, long totalBytes) {
        setUrl(url);
        setPath(path);
        setTotalBytes(totalBytes);
    }

    public int getId() {
        if (this.id != 0) {
            return this.id;
        }
        int generateId = FileDownloadUtils.generateId(getUrl(), getPath());
        this.id = generateId;
        return generateId;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public long getTotalBytes() {
        return this.totalBytes;
    }

    public void setTotalBytes(long totalBytes) {
        this.totalBytes = totalBytes;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.url);
        dest.writeString(this.path);
        dest.writeLong(this.totalBytes);
    }

    protected FileDownloadTaskAtom(Parcel in) {
        this.url = in.readString();
        this.path = in.readString();
        this.totalBytes = in.readLong();
    }
}
