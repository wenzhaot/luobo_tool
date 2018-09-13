package com.liulishuo.filedownloader.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileDownloadHeader implements Parcelable {
    public static final Creator<FileDownloadHeader> CREATOR = new Creator<FileDownloadHeader>() {
        public FileDownloadHeader createFromParcel(Parcel source) {
            return new FileDownloadHeader(source);
        }

        public FileDownloadHeader[] newArray(int size) {
            return new FileDownloadHeader[size];
        }
    };
    private HashMap<String, List<String>> mHeaderMap;

    public void add(String name, String value) {
        if (name == null) {
            throw new NullPointerException("name == null");
        } else if (name.isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        } else if (value == null) {
            throw new NullPointerException("value == null");
        } else {
            if (this.mHeaderMap == null) {
                this.mHeaderMap = new HashMap();
            }
            List<String> values = (List) this.mHeaderMap.get(name);
            if (values == null) {
                values = new ArrayList();
                this.mHeaderMap.put(name, values);
            }
            if (!values.contains(value)) {
                values.add(value);
            }
        }
    }

    public void add(String line) {
        String[] parsed = line.split(":");
        add(parsed[0].trim(), parsed[1].trim());
    }

    public void removeAll(String name) {
        if (this.mHeaderMap != null) {
            this.mHeaderMap.remove(name);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeMap(this.mHeaderMap);
    }

    public HashMap<String, List<String>> getHeaders() {
        return this.mHeaderMap;
    }

    protected FileDownloadHeader(Parcel in) {
        this.mHeaderMap = in.readHashMap(String.class.getClassLoader());
    }

    public String toString() {
        return this.mHeaderMap.toString();
    }
}
