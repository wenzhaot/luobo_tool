package com.liulishuo.filedownloader.exception;

import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class FileDownloadHttpException extends IOException {
    private final int mCode;
    private final Map<String, List<String>> mRequestHeaderMap;
    private final Map<String, List<String>> mResponseHeaderMap;

    public FileDownloadHttpException(int code, Map<String, List<String>> requestHeaderMap, Map<String, List<String>> responseHeaderMap) {
        super(FileDownloadUtils.formatString("response code error: %d, \n request headers: %s \n response headers: %s", Integer.valueOf(code), requestHeaderMap, responseHeaderMap));
        this.mCode = code;
        this.mRequestHeaderMap = cloneSerializableMap(requestHeaderMap);
        this.mResponseHeaderMap = cloneSerializableMap(requestHeaderMap);
    }

    public Map<String, List<String>> getRequestHeader() {
        return this.mRequestHeaderMap;
    }

    public Map<String, List<String>> getResponseHeader() {
        return this.mResponseHeaderMap;
    }

    public int getCode() {
        return this.mCode;
    }

    private static Map<String, List<String>> cloneSerializableMap(Map<String, List<String>> originMap) {
        Map<String, List<String>> serialMap = new HashMap();
        for (Entry<String, List<String>> entry : originMap.entrySet()) {
            serialMap.put((String) entry.getKey(), new ArrayList((Collection) entry.getValue()));
        }
        return serialMap;
    }
}
