package com.liulishuo.filedownloader.connection;

import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import com.taobao.accs.ErrorCode;
import com.taobao.accs.common.Constants;
import com.umeng.message.util.HttpRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RedirectHandler {
    private static final int HTTP_PERMANENT_REDIRECT = 308;
    private static final int HTTP_TEMPORARY_REDIRECT = 307;
    private static final int MAX_REDIRECT_TIMES = 10;

    public static FileDownloadConnection process(Map<String, List<String>> requestHeaderFields, FileDownloadConnection connection, List<String> redirectedUrlList) throws IOException, IllegalAccessException {
        int code = connection.getResponseCode();
        String location = connection.getResponseHeaderField(HttpRequest.HEADER_LOCATION);
        List<String> redirectLocationList = new ArrayList();
        int redirectTimes = 0;
        FileDownloadConnection redirectConnection = connection;
        while (isRedirect(code)) {
            if (location == null) {
                throw new IllegalAccessException(FileDownloadUtils.formatString("receive %d (redirect) but the location is null with response [%s]", Integer.valueOf(code), redirectConnection.getResponseHeaderFields()));
            }
            if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(RedirectHandler.class, "redirect to %s with %d, %s", location, Integer.valueOf(code), redirectLocationList);
            }
            redirectConnection.ending();
            redirectConnection = buildRedirectConnection(requestHeaderFields, location);
            redirectLocationList.add(location);
            redirectConnection.execute();
            code = redirectConnection.getResponseCode();
            location = redirectConnection.getResponseHeaderField(HttpRequest.HEADER_LOCATION);
            redirectTimes++;
            if (redirectTimes >= 10) {
                throw new IllegalAccessException(FileDownloadUtils.formatString("redirect too many times! %s", redirectLocationList));
            }
        }
        if (redirectedUrlList != null) {
            redirectedUrlList.addAll(redirectLocationList);
        }
        return redirectConnection;
    }

    private static boolean isRedirect(int code) {
        return code == Constants.COMMAND_STOP_FOR_ELECTION || code == ErrorCode.DM_DEVICEID_INVALID || code == ErrorCode.DM_APPKEY_INVALID || code == 300 || code == HTTP_TEMPORARY_REDIRECT || code == HTTP_PERMANENT_REDIRECT;
    }

    private static FileDownloadConnection buildRedirectConnection(Map<String, List<String>> requestHeaderFields, String newUrl) throws IOException {
        FileDownloadConnection redirectConnection = CustomComponentHolder.getImpl().createConnection(newUrl);
        for (Entry<String, List<String>> e : requestHeaderFields.entrySet()) {
            String name = (String) e.getKey();
            List<String> list = (List) e.getValue();
            if (list != null) {
                for (String value : list) {
                    redirectConnection.addHeader(name, value);
                }
            }
        }
        return redirectConnection;
    }
}
