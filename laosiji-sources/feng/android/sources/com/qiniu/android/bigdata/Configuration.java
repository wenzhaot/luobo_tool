package com.qiniu.android.bigdata;

import com.qiniu.android.http.ProxyConfiguration;

public final class Configuration implements Cloneable {
    public int connectTimeout = 3;
    public String pipelineHost = "https://pipeline.qiniu.com";
    public ProxyConfiguration proxy;
    public int responseTimeout = 10;

    public static Configuration copy(Configuration config) {
        if (config == null) {
            return new Configuration();
        }
        try {
            return config.clone();
        } catch (CloneNotSupportedException e) {
            return new Configuration();
        }
    }

    public Configuration clone() throws CloneNotSupportedException {
        return (Configuration) super.clone();
    }
}
