package com.liulishuo.filedownloader.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileDownloadProperties {
    private static final String FALSE_STRING = "false";
    private static final String KEY_BROADCAST_COMPLETED = "broadcast.completed";
    private static final String KEY_DOWNLOAD_MAX_NETWORK_THREAD_COUNT = "download.max-network-thread-count";
    private static final String KEY_DOWNLOAD_MIN_PROGRESS_STEP = "download.min-progress-step";
    private static final String KEY_DOWNLOAD_MIN_PROGRESS_TIME = "download.min-progress-time";
    private static final String KEY_FILE_NON_PRE_ALLOCATION = "file.non-pre-allocation";
    private static final String KEY_HTTP_LENIENT = "http.lenient";
    private static final String KEY_PROCESS_NON_SEPARATE = "process.non-separate";
    private static final String KEY_TRIAL_CONNECTION_HEAD_METHOD = "download.trial-connection-head-method";
    private static final String TRUE_STRING = "true";
    public final boolean broadcastCompleted;
    public final int downloadMaxNetworkThreadCount;
    public final int downloadMinProgressStep;
    public final long downloadMinProgressTime;
    public final boolean fileNonPreAllocation;
    public final boolean httpLenient;
    public final boolean processNonSeparate;
    public final boolean trialConnectionHeadMethod;

    public static class HolderClass {
        private static final FileDownloadProperties INSTANCE = new FileDownloadProperties();
    }

    public static FileDownloadProperties getImpl() {
        return HolderClass.INSTANCE;
    }

    private FileDownloadProperties() {
        if (FileDownloadHelper.getAppContext() == null) {
            throw new IllegalStateException("Please invoke the 'FileDownloader#setup' before using FileDownloader. If you want to register some components on FileDownloader please invoke the 'FileDownloader#setupOnApplicationOnCreate' on the 'Application#onCreate' first.");
        }
        long start = System.currentTimeMillis();
        String httpLenient = null;
        String processNonSeparate = null;
        String downloadMinProgressStep = null;
        String downloadMinProgressTime = null;
        String downloadMaxNetworkThreadCount = null;
        String fileNonPreAllocation = null;
        String broadcastCompleted = null;
        String downloadTrialConnectionHeadMethod = null;
        Properties p = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = FileDownloadHelper.getAppContext().getAssets().open("filedownloader.properties");
            if (inputStream != null) {
                p.load(inputStream);
                httpLenient = p.getProperty(KEY_HTTP_LENIENT);
                processNonSeparate = p.getProperty(KEY_PROCESS_NON_SEPARATE);
                downloadMinProgressStep = p.getProperty(KEY_DOWNLOAD_MIN_PROGRESS_STEP);
                downloadMinProgressTime = p.getProperty(KEY_DOWNLOAD_MIN_PROGRESS_TIME);
                downloadMaxNetworkThreadCount = p.getProperty(KEY_DOWNLOAD_MAX_NETWORK_THREAD_COUNT);
                fileNonPreAllocation = p.getProperty(KEY_FILE_NON_PRE_ALLOCATION);
                broadcastCompleted = p.getProperty(KEY_BROADCAST_COMPLETED);
                downloadTrialConnectionHeadMethod = p.getProperty(KEY_TRIAL_CONNECTION_HEAD_METHOD);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e2) {
            if (!(e2 instanceof FileNotFoundException)) {
                e2.printStackTrace();
            } else if (FileDownloadLog.NEED_LOG) {
                FileDownloadLog.d(FileDownloadProperties.class, "not found filedownloader.properties", new Object[0]);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e22) {
                    e22.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e222) {
                    e222.printStackTrace();
                }
            }
        }
        if (httpLenient == null) {
            this.httpLenient = false;
        } else if (httpLenient.equals("true") || httpLenient.equals("false")) {
            this.httpLenient = httpLenient.equals("true");
        } else {
            throw new IllegalStateException(FileDownloadUtils.formatString("the value of '%s' must be '%s' or '%s'", KEY_HTTP_LENIENT, "true", "false"));
        }
        if (processNonSeparate == null) {
            this.processNonSeparate = false;
        } else if (processNonSeparate.equals("true") || processNonSeparate.equals("false")) {
            this.processNonSeparate = processNonSeparate.equals("true");
        } else {
            throw new IllegalStateException(FileDownloadUtils.formatString("the value of '%s' must be '%s' or '%s'", KEY_PROCESS_NON_SEPARATE, "true", "false"));
        }
        if (downloadMinProgressStep != null) {
            this.downloadMinProgressStep = Math.max(0, Integer.valueOf(downloadMinProgressStep).intValue());
        } else {
            this.downloadMinProgressStep = 65536;
        }
        if (downloadMinProgressTime != null) {
            this.downloadMinProgressTime = Math.max(0, Long.valueOf(downloadMinProgressTime).longValue());
        } else {
            this.downloadMinProgressTime = 2000;
        }
        if (downloadMaxNetworkThreadCount != null) {
            this.downloadMaxNetworkThreadCount = getValidNetworkThreadCount(Integer.valueOf(downloadMaxNetworkThreadCount).intValue());
        } else {
            this.downloadMaxNetworkThreadCount = 3;
        }
        if (fileNonPreAllocation == null) {
            this.fileNonPreAllocation = false;
        } else if (fileNonPreAllocation.equals("true") || fileNonPreAllocation.equals("false")) {
            this.fileNonPreAllocation = fileNonPreAllocation.equals("true");
        } else {
            throw new IllegalStateException(FileDownloadUtils.formatString("the value of '%s' must be '%s' or '%s'", KEY_FILE_NON_PRE_ALLOCATION, "true", "false"));
        }
        if (broadcastCompleted == null) {
            this.broadcastCompleted = false;
        } else if (broadcastCompleted.equals("true") || broadcastCompleted.equals("false")) {
            this.broadcastCompleted = broadcastCompleted.equals("true");
        } else {
            throw new IllegalStateException(FileDownloadUtils.formatString("the value of '%s' must be '%s' or '%s'", KEY_BROADCAST_COMPLETED, "true", "false"));
        }
        if (downloadTrialConnectionHeadMethod == null) {
            this.trialConnectionHeadMethod = false;
        } else if (downloadTrialConnectionHeadMethod.equals("true") || downloadTrialConnectionHeadMethod.equals("false")) {
            this.trialConnectionHeadMethod = downloadTrialConnectionHeadMethod.equals("true");
        } else {
            throw new IllegalStateException(FileDownloadUtils.formatString("the value of '%s' must be '%s' or '%s'", KEY_TRIAL_CONNECTION_HEAD_METHOD, "true", "false"));
        }
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.i(FileDownloadProperties.class, "init properties %d\n load properties: %s=%B; %s=%B; %s=%d; %s=%d; %s=%d; %s=%B; %s=%B; %s=%B", Long.valueOf(System.currentTimeMillis() - start), KEY_HTTP_LENIENT, Boolean.valueOf(this.httpLenient), KEY_PROCESS_NON_SEPARATE, Boolean.valueOf(this.processNonSeparate), KEY_DOWNLOAD_MIN_PROGRESS_STEP, Integer.valueOf(this.downloadMinProgressStep), KEY_DOWNLOAD_MIN_PROGRESS_TIME, Long.valueOf(this.downloadMinProgressTime), KEY_DOWNLOAD_MAX_NETWORK_THREAD_COUNT, Integer.valueOf(this.downloadMaxNetworkThreadCount), KEY_FILE_NON_PRE_ALLOCATION, Boolean.valueOf(this.fileNonPreAllocation), KEY_BROADCAST_COMPLETED, Boolean.valueOf(this.broadcastCompleted), KEY_TRIAL_CONNECTION_HEAD_METHOD, Boolean.valueOf(this.trialConnectionHeadMethod));
        }
    }

    public static int getValidNetworkThreadCount(int requireCount) {
        if (requireCount > 12) {
            FileDownloadLog.w(FileDownloadProperties.class, "require the count of network thread  is %d, what is more than the max valid count(%d), so adjust to %d auto", Integer.valueOf(requireCount), Integer.valueOf(12), Integer.valueOf(12));
            return 12;
        } else if (requireCount >= 1) {
            return requireCount;
        } else {
            FileDownloadLog.w(FileDownloadProperties.class, "require the count of network thread  is %d, what is less than the min valid count(%d), so adjust to %d auto", Integer.valueOf(requireCount), Integer.valueOf(1), Integer.valueOf(1));
            return 1;
        }
    }
}
