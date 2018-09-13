package com.liulishuo.filedownloader.model;

import android.content.ContentValues;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.util.List;

public class ConnectionModel {
    public static final String CURRENT_OFFSET = "currentOffset";
    public static final String END_OFFSET = "endOffset";
    public static final String ID = "id";
    public static final String INDEX = "connectionIndex";
    public static final String START_OFFSET = "startOffset";
    private long currentOffset;
    private long endOffset;
    private int id;
    private int index;
    private long startOffset;

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public long getStartOffset() {
        return this.startOffset;
    }

    public void setStartOffset(long startOffset) {
        this.startOffset = startOffset;
    }

    public long getCurrentOffset() {
        return this.currentOffset;
    }

    public void setCurrentOffset(long currentOffset) {
        this.currentOffset = currentOffset;
    }

    public long getEndOffset() {
        return this.endOffset;
    }

    public void setEndOffset(long endOffset) {
        this.endOffset = endOffset;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        values.put("id", Integer.valueOf(this.id));
        values.put(INDEX, Integer.valueOf(this.index));
        values.put(START_OFFSET, Long.valueOf(this.startOffset));
        values.put(CURRENT_OFFSET, Long.valueOf(this.currentOffset));
        values.put(END_OFFSET, Long.valueOf(this.endOffset));
        return values;
    }

    public static long getTotalOffset(List<ConnectionModel> modelList) {
        long totalOffset = 0;
        for (ConnectionModel model : modelList) {
            totalOffset += model.getCurrentOffset() - model.getStartOffset();
        }
        return totalOffset;
    }

    public String toString() {
        return FileDownloadUtils.formatString("id[%d] index[%d] range[%d, %d) current offset(%d)", Integer.valueOf(this.id), Integer.valueOf(this.index), Long.valueOf(this.startOffset), Long.valueOf(this.endOffset), Long.valueOf(this.currentOffset));
    }
}
