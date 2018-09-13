package com.qiniu.android.http;

import com.qiniu.android.collect.Config;
import com.qiniu.android.collect.UploadInfoCollector;
import com.qiniu.android.collect.UploadInfoCollector.RecordMsg;
import com.qiniu.android.common.Constants;
import com.qiniu.android.storage.UpToken;
import com.qiniu.android.utils.StringUtils;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.Locale;
import org.json.JSONObject;

public final class ResponseInfo {
    public static final int Cancelled = -2;
    public static final int CannotConnectToHost = -1004;
    public static final int InvalidArgument = -4;
    public static final int InvalidFile = -3;
    public static final int InvalidToken = -5;
    public static final int NetworkConnectionLost = -1005;
    public static final int NetworkError = -1;
    public static final int TimedOut = -1001;
    public static final int UnknownError = 0;
    public static final int UnknownHost = -1003;
    public static final int ZeroSizeFile = -6;
    public final long duration;
    public final String error;
    public final String host;
    public final String id = UserAgent.instance().id;
    public final String ip;
    public final String path;
    public final int port;
    public final String reqId;
    public final JSONObject response;
    public final long sent;
    public final int statusCode;
    public final long timeStamp = (System.currentTimeMillis() / 1000);
    public final long totalSize;
    public final UpToken upToken;
    public final String xlog;
    public final String xvia;

    private ResponseInfo(JSONObject json, int statusCode, String reqId, String xlog, String xvia, String host, String path, String ip, int port, long duration, long sent, String error, UpToken upToken, long totalSize) {
        this.response = json;
        this.statusCode = statusCode;
        this.reqId = reqId;
        this.xlog = xlog;
        this.xvia = xvia;
        this.host = host;
        this.path = path;
        this.duration = duration;
        this.error = error;
        this.ip = ip;
        this.port = port;
        this.sent = sent;
        this.upToken = upToken;
        this.totalSize = totalSize;
    }

    public static ResponseInfo create(JSONObject json, int statusCode, String reqId, String xlog, String xvia, String host, String path, String oIp, int port, long duration, long sent, String error, UpToken upToken, long totalSize) {
        String _ip = (oIp + "").split(":")[0];
        String str = _ip;
        final String ip = str.substring(Math.max(0, _ip.indexOf("/") + 1));
        ResponseInfo res = new ResponseInfo(json, statusCode, reqId, xlog, xvia, host, path, ip, port, duration, sent, error, upToken, totalSize);
        if (Config.isRecord || upToken != null) {
            final String _timeStamp = res.timeStamp + "";
            final int i = statusCode;
            final String str2 = reqId;
            final String str3 = host;
            final int i2 = port;
            final long j = duration;
            final long j2 = sent;
            final String str4 = path;
            final long j3 = totalSize;
            UploadInfoCollector.handleHttp(upToken, new RecordMsg() {
                public String toRecordMsg() {
                    return StringUtils.join(new String[]{i + "", str2, str3, ip, i2 + "", j + "", _timeStamp, j2 + "", ResponseInfo.getUpType(str4), j3 + ""}, MiPushClient.ACCEPT_TIME_SEPARATOR);
                }
            });
        }
        return res;
    }

    private static String getUpType(String path) {
        if (path == null || !path.startsWith("/")) {
            return "";
        }
        if ("/".equals(path)) {
            return "form";
        }
        int l = path.indexOf(47, 1);
        if (l < 1) {
            return "";
        }
        String m = path.substring(1, l);
        int i = -1;
        switch (m.hashCode()) {
            case -1072430054:
                if (m.equals("mkfile")) {
                    i = 2;
                    break;
                }
                break;
            case 111375:
                if (m.equals("put")) {
                    i = 3;
                    break;
                }
                break;
            case 3030893:
                if (m.equals("bput")) {
                    i = 1;
                    break;
                }
                break;
            case 103949059:
                if (m.equals("mkblk")) {
                    i = 0;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                return "mkblk";
            case 1:
                return "bput";
            case 2:
                return "mkfile";
            case 3:
                return "put";
            default:
                return "";
        }
    }

    public static ResponseInfo zeroSize(UpToken upToken) {
        return create(null, -6, "", "", "", "", "", "", 80, 0, 0, "file or data size is zero", upToken, 0);
    }

    public static ResponseInfo cancelled(UpToken upToken) {
        return create(null, -2, "", "", "", "", "", "", 80, -1, -1, "cancelled by user", upToken, 0);
    }

    public static ResponseInfo invalidArgument(String message, UpToken upToken) {
        return create(null, -4, "", "", "", "", "", "", 80, 0, 0, message, upToken, 0);
    }

    public static ResponseInfo invalidToken(String message) {
        return create(null, -5, "", "", "", "", "", "", 80, 0, 0, message, null, 0);
    }

    public static ResponseInfo fileError(Exception e, UpToken upToken) {
        return create(null, -3, "", "", "", "", "", "", 80, 0, 0, e.getMessage(), upToken, 0);
    }

    public static ResponseInfo networkError(int code, UpToken upToken) {
        return create(null, code, "", "", "", "", "", "", 80, 0, 0, "Network error during preQuery", upToken, 0);
    }

    public static boolean isStatusCodeForBrokenNetwork(int code) {
        return code == -1 || code == -1003 || code == -1004 || code == -1001 || code == NetworkConnectionLost;
    }

    public boolean isCancelled() {
        return this.statusCode == -2;
    }

    public boolean isOK() {
        return this.statusCode == 200 && this.error == null && (hasReqId() || this.response != null);
    }

    public boolean isNetworkBroken() {
        return this.statusCode == -1 || this.statusCode == -1003 || this.statusCode == -1004 || this.statusCode == -1001 || this.statusCode == NetworkConnectionLost;
    }

    public boolean isServerError() {
        return (this.statusCode >= 500 && this.statusCode < 600 && this.statusCode != 579) || this.statusCode == 996;
    }

    public boolean needSwitchServer() {
        return isNetworkBroken() || isServerError();
    }

    public boolean needRetry() {
        return !isCancelled() && (needSwitchServer() || this.statusCode == 406 || ((this.statusCode == 200 && this.error != null) || (isNotQiniu() && !this.upToken.hasReturnUrl())));
    }

    public boolean isNotQiniu() {
        return this.statusCode < 500 && this.statusCode >= 200 && !hasReqId() && this.response == null;
    }

    public String toString() {
        return String.format(Locale.ENGLISH, "{ver:%s,ResponseInfo:%s,status:%d, reqId:%s, xlog:%s, xvia:%s, host:%s, path:%s, ip:%s, port:%d, duration:%d s, time:%d, sent:%d,error:%s}", new Object[]{Constants.VERSION, this.id, Integer.valueOf(this.statusCode), this.reqId, this.xlog, this.xvia, this.host, this.path, this.ip, Integer.valueOf(this.port), Long.valueOf(this.duration), Long.valueOf(this.timeStamp), Long.valueOf(this.sent), this.error});
    }

    public boolean hasReqId() {
        return this.reqId != null;
    }
}
