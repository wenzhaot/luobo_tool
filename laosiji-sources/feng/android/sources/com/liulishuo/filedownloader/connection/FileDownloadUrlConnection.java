package com.liulishuo.filedownloader.connection;

import com.liulishuo.filedownloader.util.FileDownloadHelper.ConnectionCreator;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class FileDownloadUrlConnection implements FileDownloadConnection {
    protected URLConnection mConnection;

    public static class Configuration {
        private Integer connectTimeout;
        private Proxy proxy;
        private Integer readTimeout;

        public Configuration proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Configuration readTimeout(int readTimeout) {
            this.readTimeout = Integer.valueOf(readTimeout);
            return this;
        }

        public Configuration connectTimeout(int connectTimeout) {
            this.connectTimeout = Integer.valueOf(connectTimeout);
            return this;
        }
    }

    public static class Creator implements ConnectionCreator {
        private final Configuration mConfiguration;

        public Creator() {
            this(null);
        }

        public Creator(Configuration configuration) {
            this.mConfiguration = configuration;
        }

        FileDownloadConnection create(URL url) throws IOException {
            return new FileDownloadUrlConnection(url, this.mConfiguration);
        }

        public FileDownloadConnection create(String originUrl) throws IOException {
            return new FileDownloadUrlConnection(originUrl, this.mConfiguration);
        }
    }

    public FileDownloadUrlConnection(String originUrl, Configuration configuration) throws IOException {
        this(new URL(originUrl), configuration);
    }

    public FileDownloadUrlConnection(URL url, Configuration configuration) throws IOException {
        if (configuration == null || configuration.proxy == null) {
            this.mConnection = url.openConnection();
        } else {
            this.mConnection = url.openConnection(configuration.proxy);
        }
        if (configuration != null) {
            if (configuration.readTimeout != null) {
                this.mConnection.setReadTimeout(configuration.readTimeout.intValue());
            }
            if (configuration.connectTimeout != null) {
                this.mConnection.setConnectTimeout(configuration.connectTimeout.intValue());
            }
        }
    }

    public FileDownloadUrlConnection(String originUrl) throws IOException {
        this(originUrl, null);
    }

    public void addHeader(String name, String value) {
        this.mConnection.addRequestProperty(name, value);
    }

    public boolean dispatchAddResumeOffset(String etag, long offset) {
        return false;
    }

    public InputStream getInputStream() throws IOException {
        return this.mConnection.getInputStream();
    }

    public Map<String, List<String>> getRequestHeaderFields() {
        return this.mConnection.getRequestProperties();
    }

    public Map<String, List<String>> getResponseHeaderFields() {
        return this.mConnection.getHeaderFields();
    }

    public String getResponseHeaderField(String name) {
        return this.mConnection.getHeaderField(name);
    }

    public boolean setRequestMethod(String method) throws ProtocolException {
        if (!(this.mConnection instanceof HttpURLConnection)) {
            return false;
        }
        ((HttpURLConnection) this.mConnection).setRequestMethod(method);
        return true;
    }

    public void execute() throws IOException {
        this.mConnection.connect();
    }

    public int getResponseCode() throws IOException {
        if (this.mConnection instanceof HttpURLConnection) {
            return ((HttpURLConnection) this.mConnection).getResponseCode();
        }
        return 0;
    }

    public void ending() {
        try {
            this.mConnection.getInputStream().close();
        } catch (IOException e) {
        }
    }
}
