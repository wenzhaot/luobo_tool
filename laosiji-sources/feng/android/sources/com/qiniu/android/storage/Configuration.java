package com.qiniu.android.storage;

import com.qiniu.android.common.AutoZone;
import com.qiniu.android.common.Zone;
import com.qiniu.android.dns.DnsManager;
import com.qiniu.android.dns.Domain;
import com.qiniu.android.dns.IResolver;
import com.qiniu.android.dns.NetworkInfo;
import com.qiniu.android.dns.local.AndroidDnsServer;
import com.qiniu.android.dns.local.Resolver;
import com.qiniu.android.http.Dns;
import com.qiniu.android.http.ProxyConfiguration;
import com.qiniu.android.http.UrlConverter;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Configuration {
    public static final int BLOCK_SIZE = 4194304;
    public final int chunkSize;
    public final int connectTimeout;
    public Dns dns;
    public final KeyGenerator keyGen;
    public final ProxyConfiguration proxy;
    public final int putThreshold;
    public final Recorder recorder;
    public final int responseTimeout;
    public final int retryMax;
    public UrlConverter urlConverter;
    public boolean useHttps;
    public Zone zone;

    public static class Builder {
        private int chunkSize = 2097152;
        private int connectTimeout = 10;
        private Dns dns = null;
        private KeyGenerator keyGen = null;
        private ProxyConfiguration proxy = null;
        private int putThreshold = Configuration.BLOCK_SIZE;
        private Recorder recorder = null;
        private int responseTimeout = 60;
        private int retryMax = 3;
        private UrlConverter urlConverter = null;
        private boolean useHttps = false;
        private Zone zone = null;

        public Builder() {
            buildDefaultDns();
        }

        private void buildDefaultDns() {
            IResolver r1 = AndroidDnsServer.defaultResolver();
            IResolver r2 = null;
            try {
                r2 = new Resolver(InetAddress.getByName("119.29.29.29"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            final DnsManager happlyDns = new DnsManager(NetworkInfo.normal, new IResolver[]{r1, r2});
            this.dns = new Dns() {
                public List<InetAddress> lookup(String hostname) throws UnknownHostException {
                    try {
                        InetAddress[] ips = happlyDns.queryInetAdress(new Domain(hostname));
                        if (ips == null) {
                            throw new UnknownHostException(hostname + " resolve failed.");
                        }
                        List<InetAddress> l = new ArrayList();
                        Collections.addAll(l, ips);
                        return l;
                    } catch (IOException e) {
                        e.printStackTrace();
                        throw new UnknownHostException(e.getMessage());
                    }
                }
            };
        }

        public Builder zone(Zone zone) {
            this.zone = zone;
            return this;
        }

        public Builder recorder(Recorder recorder) {
            this.recorder = recorder;
            return this;
        }

        public Builder recorder(Recorder recorder, KeyGenerator keyGen) {
            this.recorder = recorder;
            this.keyGen = keyGen;
            return this;
        }

        public Builder proxy(ProxyConfiguration proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder chunkSize(int size) {
            this.chunkSize = size;
            return this;
        }

        public Builder putThreshhold(int size) {
            this.putThreshold = size;
            return this;
        }

        public Builder connectTimeout(int timeout) {
            this.connectTimeout = timeout;
            return this;
        }

        public Builder responseTimeout(int timeout) {
            this.responseTimeout = timeout;
            return this;
        }

        public Builder retryMax(int times) {
            this.retryMax = times;
            return this;
        }

        public Builder urlConverter(UrlConverter converter) {
            this.urlConverter = converter;
            return this;
        }

        public Builder dns(Dns dns) {
            this.dns = dns;
            return this;
        }

        public Builder useHttps(boolean useHttps) {
            this.useHttps = useHttps;
            return this;
        }

        public Configuration build() {
            return new Configuration(this, null);
        }
    }

    /* synthetic */ Configuration(Builder x0, AnonymousClass1 x1) {
        this(x0);
    }

    private Configuration(Builder builder) {
        this.useHttps = builder.useHttps;
        this.chunkSize = builder.chunkSize;
        this.putThreshold = builder.putThreshold;
        this.connectTimeout = builder.connectTimeout;
        this.responseTimeout = builder.responseTimeout;
        this.recorder = builder.recorder;
        this.keyGen = getKeyGen(builder.keyGen);
        this.retryMax = builder.retryMax;
        this.proxy = builder.proxy;
        this.urlConverter = builder.urlConverter;
        this.zone = builder.zone == null ? AutoZone.autoZone : builder.zone;
        this.dns = builder.dns;
    }

    private KeyGenerator getKeyGen(KeyGenerator keyGen) {
        if (keyGen == null) {
            return new KeyGenerator() {
                public String gen(String key, File file) {
                    return key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
                }
            };
        }
        return keyGen;
    }
}
