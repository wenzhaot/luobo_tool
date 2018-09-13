package com.liulishuo.filedownloader.download;

import android.text.TextUtils;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.connection.RedirectHandler;
import com.liulishuo.filedownloader.download.ConnectionProfile.ConnectionProfileBuild;
import com.liulishuo.filedownloader.model.FileDownloadHeader;
import com.liulishuo.filedownloader.util.FileDownloadLog;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class ConnectTask {
    final int downloadId;
    private String etag;
    final FileDownloadHeader header;
    private ConnectionProfile profile;
    private List<String> redirectedUrlList;
    private Map<String, List<String>> requestHeader;
    final String url;

    static class Builder {
        private ConnectionProfile connectionProfile;
        private Integer downloadId;
        private String etag;
        private FileDownloadHeader header;
        private String url;

        Builder() {
        }

        public Builder setDownloadId(int downloadId) {
            this.downloadId = Integer.valueOf(downloadId);
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setEtag(String etag) {
            this.etag = etag;
            return this;
        }

        public Builder setHeader(FileDownloadHeader header) {
            this.header = header;
            return this;
        }

        public Builder setConnectionProfile(ConnectionProfile model) {
            this.connectionProfile = model;
            return this;
        }

        ConnectTask build() {
            if (this.downloadId != null && this.connectionProfile != null && this.url != null) {
                return new ConnectTask(this.connectionProfile, this.downloadId.intValue(), this.url, this.etag, this.header);
            }
            throw new IllegalArgumentException();
        }
    }

    class Reconnect extends Throwable {
        Reconnect() {
        }
    }

    private ConnectTask(ConnectionProfile profile, int downloadId, String url, String etag, FileDownloadHeader header) {
        this.downloadId = downloadId;
        this.url = url;
        this.etag = etag;
        this.header = header;
        this.profile = profile;
    }

    void updateConnectionProfile(long downloadedOffset) {
        if (downloadedOffset == this.profile.currentOffset) {
            FileDownloadLog.w(this, "no data download, no need to update", new Object[0]);
            return;
        }
        long newContentLength = this.profile.contentLength - (downloadedOffset - this.profile.currentOffset);
        this.profile = ConnectionProfileBuild.buildConnectionProfile(this.profile.startOffset, downloadedOffset, this.profile.endOffset, newContentLength);
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.i(this, "after update profile:%s", this.profile);
        }
    }

    FileDownloadConnection connect() throws IOException, IllegalAccessException {
        FileDownloadConnection connection = CustomComponentHolder.getImpl().createConnection(this.url);
        addUserRequiredHeader(connection);
        addRangeHeader(connection);
        fixNeededHeader(connection);
        this.requestHeader = connection.getRequestHeaderFields();
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "<---- %s request header %s", Integer.valueOf(this.downloadId), this.requestHeader);
        }
        connection.execute();
        this.redirectedUrlList = new ArrayList();
        connection = RedirectHandler.process(this.requestHeader, connection, this.redirectedUrlList);
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(this, "----> %s response header %s", Integer.valueOf(this.downloadId), connection.getResponseHeaderFields());
        }
        return connection;
    }

    private void addUserRequiredHeader(FileDownloadConnection connection) {
        if (this.header != null) {
            HashMap<String, List<String>> additionHeaders = this.header.getHeaders();
            if (additionHeaders != null) {
                if (FileDownloadLog.NEED_LOG) {
                    FileDownloadLog.v(this, "%d add outside header: %s", Integer.valueOf(this.downloadId), additionHeaders);
                }
                for (Entry<String, List<String>> e : additionHeaders.entrySet()) {
                    String name = (String) e.getKey();
                    List<String> list = (List) e.getValue();
                    if (list != null) {
                        for (String value : list) {
                            connection.addHeader(name, value);
                        }
                    }
                }
            }
        }
    }

    private void addRangeHeader(FileDownloadConnection connection) throws ProtocolException {
        if (!connection.dispatchAddResumeOffset(this.etag, this.profile.startOffset)) {
            if (!TextUtils.isEmpty(this.etag)) {
                connection.addHeader("If-Match", this.etag);
            }
            this.profile.processProfile(connection);
        }
    }

    private void fixNeededHeader(FileDownloadConnection connection) {
        if (this.header == null || this.header.getHeaders().get("User-Agent") == null) {
            connection.addHeader("User-Agent", FileDownloadUtils.defaultUserAgent());
        }
    }

    boolean isRangeNotFromBeginning() {
        return this.profile.currentOffset > 0;
    }

    String getFinalRedirectedUrl() {
        if (this.redirectedUrlList == null || this.redirectedUrlList.isEmpty()) {
            return null;
        }
        return (String) this.redirectedUrlList.get(this.redirectedUrlList.size() - 1);
    }

    public Map<String, List<String>> getRequestHeader() {
        return this.requestHeader;
    }

    public ConnectionProfile getProfile() {
        return this.profile;
    }

    public void retryOnConnectedWithNewParam(ConnectionProfile profile, String etag) throws Reconnect {
        if (profile == null) {
            throw new IllegalArgumentException();
        }
        this.profile = profile;
        this.etag = etag;
        throw new Reconnect();
    }
}
