package com.liulishuo.filedownloader.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class FileDownloadModel implements Parcelable {
    public static final String CONNECTION_COUNT = "connectionCount";
    public static final Creator<FileDownloadModel> CREATOR = new Creator<FileDownloadModel>() {
        public FileDownloadModel createFromParcel(Parcel source) {
            return new FileDownloadModel(source);
        }

        public FileDownloadModel[] newArray(int size) {
            return new FileDownloadModel[size];
        }
    };
    public static final int DEFAULT_CALLBACK_PROGRESS_TIMES = 100;
    public static final String ERR_MSG = "errMsg";
    public static final String ETAG = "etag";
    public static final String FILENAME = "filename";
    public static final String ID = "_id";
    public static final String PATH = "path";
    public static final String PATH_AS_DIRECTORY = "pathAsDirectory";
    public static final String SOFAR = "sofar";
    public static final String STATUS = "status";
    public static final String TOTAL = "total";
    public static final int TOTAL_VALUE_IN_CHUNKED_RESOURCE = -1;
    public static final String URL = "url";
    private int connectionCount;
    private String eTag;
    private String errMsg;
    private String filename;
    private int id;
    private boolean isLargeFile;
    private String path;
    private boolean pathAsDirectory;
    private final AtomicLong soFar;
    private final AtomicInteger status;
    private long total;
    private String url;

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPath(String path, boolean pathAsDirectory) {
        this.path = path;
        this.pathAsDirectory = pathAsDirectory;
    }

    public void setStatus(byte status) {
        this.status.set(status);
    }

    public void setSoFar(long soFar) {
        this.soFar.set(soFar);
    }

    public void increaseSoFar(long increaseBytes) {
        this.soFar.addAndGet(increaseBytes);
    }

    public void setTotal(long total) {
        this.isLargeFile = total > 2147483647L;
        this.total = total;
    }

    public int getId() {
        return this.id;
    }

    public String getUrl() {
        return this.url;
    }

    public String getPath() {
        return this.path;
    }

    public String getTargetFilePath() {
        return FileDownloadUtils.getTargetFilePath(getPath(), isPathAsDirectory(), getFilename());
    }

    public String getTempFilePath() {
        if (getTargetFilePath() == null) {
            return null;
        }
        return FileDownloadUtils.getTempPath(getTargetFilePath());
    }

    public byte getStatus() {
        return (byte) this.status.get();
    }

    public long getSoFar() {
        return this.soFar.get();
    }

    public long getTotal() {
        return this.total;
    }

    public boolean isChunked() {
        return this.total == -1;
    }

    public String getETag() {
        return this.eTag;
    }

    public void setETag(String eTag) {
        this.eTag = eTag;
    }

    public String getErrMsg() {
        return this.errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public boolean isPathAsDirectory() {
        return this.pathAsDirectory;
    }

    public String getFilename() {
        return this.filename;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public int getConnectionCount() {
        return this.connectionCount;
    }

    public void resetConnectionCount() {
        this.connectionCount = 1;
    }

    public ContentValues toContentValues() {
        ContentValues cv = new ContentValues();
        cv.put("_id", Integer.valueOf(getId()));
        cv.put("url", getUrl());
        cv.put("path", getPath());
        cv.put("status", Byte.valueOf(getStatus()));
        cv.put(SOFAR, Long.valueOf(getSoFar()));
        cv.put(TOTAL, Long.valueOf(getTotal()));
        cv.put(ERR_MSG, getErrMsg());
        cv.put(ETAG, getETag());
        cv.put(CONNECTION_COUNT, Integer.valueOf(getConnectionCount()));
        cv.put(PATH_AS_DIRECTORY, Boolean.valueOf(isPathAsDirectory()));
        if (isPathAsDirectory() && getFilename() != null) {
            cv.put(FILENAME, getFilename());
        }
        return cv;
    }

    public boolean isLargeFile() {
        return this.isLargeFile;
    }

    public void deleteTaskFiles() {
        deleteTempFile();
        deleteTargetFile();
    }

    public void deleteTempFile() {
        String tempFilePath = getTempFilePath();
        if (tempFilePath != null) {
            File tempFile = new File(tempFilePath);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public void deleteTargetFile() {
        String targetFilePath = getTargetFilePath();
        if (targetFilePath != null) {
            File targetFile = new File(targetFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
        }
    }

    public String toString() {
        return FileDownloadUtils.formatString("id[%d], url[%s], path[%s], status[%d], sofar[%s], total[%d], etag[%s], %s", Integer.valueOf(this.id), this.url, this.path, Integer.valueOf(this.status.get()), this.soFar, Long.valueOf(this.total), this.eTag, super.toString());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        byte b = (byte) 1;
        dest.writeInt(this.id);
        dest.writeString(this.url);
        dest.writeString(this.path);
        dest.writeByte(this.pathAsDirectory ? (byte) 1 : (byte) 0);
        dest.writeString(this.filename);
        dest.writeByte((byte) this.status.get());
        dest.writeLong(this.soFar.get());
        dest.writeLong(this.total);
        dest.writeString(this.errMsg);
        dest.writeString(this.eTag);
        dest.writeInt(this.connectionCount);
        if (!this.isLargeFile) {
            b = (byte) 0;
        }
        dest.writeByte(b);
    }

    public FileDownloadModel() {
        this.soFar = new AtomicLong();
        this.status = new AtomicInteger();
    }

    protected FileDownloadModel(Parcel in) {
        boolean z = true;
        this.id = in.readInt();
        this.url = in.readString();
        this.path = in.readString();
        this.pathAsDirectory = in.readByte() != (byte) 0;
        this.filename = in.readString();
        this.status = new AtomicInteger(in.readByte());
        this.soFar = new AtomicLong(in.readLong());
        this.total = in.readLong();
        this.errMsg = in.readString();
        this.eTag = in.readString();
        this.connectionCount = in.readInt();
        if (in.readByte() == (byte) 0) {
            z = false;
        }
        this.isLargeFile = z;
    }
}
