package com.meizu.cloud.pushsdk.platform.message;

public class StrategyMessage implements Comparable<StrategyMessage> {
    private String appId;
    private String appKey;
    long currentTime;
    private String packageName;
    private String params;
    private String pushId;
    private int retryCount = 0;
    private int strategyChildType;
    private int strategyType;

    public StrategyMessage(String appId, String appKey, String packageName, String pushId, int strategyType, int strategyChildType, String params) {
        this.appId = appId;
        this.appKey = appKey;
        this.packageName = packageName;
        this.pushId = pushId;
        this.strategyType = strategyType;
        this.strategyChildType = strategyChildType;
        this.params = params;
        this.currentTime = System.currentTimeMillis();
    }

    public String getAppId() {
        return this.appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return this.appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPushId() {
        return this.pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public int getStrategyType() {
        return this.strategyType;
    }

    public void setStrategyType(int strategyType) {
        this.strategyType = strategyType;
    }

    public int getStrategyChildType() {
        return this.strategyChildType;
    }

    public void setStrategyChildType(int strategyChildType) {
        this.strategyChildType = strategyChildType;
    }

    public String getParams() {
        return this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public void countDownRetryCount() {
        this.retryCount = 0;
    }

    public boolean isRetry() {
        int i = this.retryCount + 1;
        this.retryCount = i;
        if (i > 1) {
            return false;
        }
        return true;
    }

    public boolean needNetWork() {
        return this.strategyType != 64;
    }

    public int compareTo(StrategyMessage strategyMessage) {
        return (int) (this.currentTime - strategyMessage.currentTime);
    }

    public String toString() {
        return "StrategyMessage{strategyType=" + this.strategyType + ", packageName='" + this.packageName + '\'' + ", appKey='" + this.appKey + '\'' + ", appId='" + this.appId + '\'' + ", pushId='" + this.pushId + '\'' + ", strategyChildType=" + this.strategyChildType + ", params='" + this.params + '\'' + '}';
    }
}
