package com.liulishuo.filedownloader.services;

import com.liulishuo.filedownloader.util.FileDownloadHelper.IdGenerator;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

public class DefaultIdGenerator implements IdGenerator {
    public int transOldId(int oldId, String url, String path, boolean pathAsDirectory) {
        return generateId(url, path, pathAsDirectory);
    }

    public int generateId(String url, String path, boolean pathAsDirectory) {
        if (pathAsDirectory) {
            return FileDownloadUtils.md5(FileDownloadUtils.formatString("%sp%s@dir", url, path)).hashCode();
        }
        return FileDownloadUtils.md5(FileDownloadUtils.formatString("%sp%s", url, path)).hashCode();
    }
}
