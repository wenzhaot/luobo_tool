package com.liulishuo.filedownloader.util;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.Process;
import android.os.StatFs;
import android.text.TextUtils;
import com.liulishuo.filedownloader.BuildConfig;
import com.liulishuo.filedownloader.connection.FileDownloadConnection;
import com.liulishuo.filedownloader.download.CustomComponentHolder;
import com.liulishuo.filedownloader.exception.FileDownloadGiveUpRetryException;
import com.liulishuo.filedownloader.exception.FileDownloadSecurityException;
import com.liulishuo.filedownloader.model.FileDownloadModel;
import com.liulishuo.filedownloader.stream.FileDownloadOutputStream;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.message.proguard.l;
import com.umeng.message.util.HttpRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileDownloadUtils {
    private static final Pattern CONTENT_DISPOSITION_NON_QUOTED_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(.*)");
    private static final Pattern CONTENT_DISPOSITION_QUOTED_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*\"([^\"]*)\"");
    private static final String FILEDOWNLOADER_PREFIX = "FileDownloader";
    private static final String INTERNAL_DOCUMENT_NAME = "filedownloader";
    private static final String OLD_FILE_CONVERTED_FILE_NAME = ".old_file_converted";
    private static String defaultSaveRootPath;
    private static Boolean filenameConverted = null;
    private static Boolean isDownloaderProcess;
    private static int minProgressStep = 65536;
    private static long minProgressTime = 2000;

    public static void setMinProgressStep(int minProgressStep) throws IllegalAccessException {
        if (isDownloaderProcess(FileDownloadHelper.getAppContext())) {
            minProgressStep = minProgressStep;
            return;
        }
        throw new IllegalAccessException("This value is used in the :filedownloader process, so set this value in your process is without effect. You can add 'process.non-separate=true' in 'filedownloader.properties' to share the main process to FileDownloadService. Or you can configure this value in 'filedownloader.properties' by 'download.min-progress-step'.");
    }

    public static void setMinProgressTime(long minProgressTime) throws IllegalAccessException {
        if (isDownloaderProcess(FileDownloadHelper.getAppContext())) {
            minProgressTime = minProgressTime;
            return;
        }
        throw new IllegalAccessException("This value is used in the :filedownloader process, so set this value in your process is without effect. You can add 'process.non-separate=true' in 'filedownloader.properties' to share the main process to FileDownloadService. Or you can configure this value in 'filedownloader.properties' by 'download.min-progress-time'.");
    }

    public static int getMinProgressStep() {
        return minProgressStep;
    }

    public static long getMinProgressTime() {
        return minProgressTime;
    }

    public static boolean isFilenameValid(String filename) {
        return true;
    }

    public static String getDefaultSaveRootPath() {
        if (!TextUtils.isEmpty(defaultSaveRootPath)) {
            return defaultSaveRootPath;
        }
        if (FileDownloadHelper.getAppContext().getExternalCacheDir() == null) {
            return Environment.getDownloadCacheDirectory().getAbsolutePath();
        }
        return FileDownloadHelper.getAppContext().getExternalCacheDir().getAbsolutePath();
    }

    public static String getDefaultSaveFilePath(String url) {
        return generateFilePath(getDefaultSaveRootPath(), generateFileName(url));
    }

    public static String generateFileName(String url) {
        return md5(url);
    }

    public static String generateFilePath(String directory, String filename) {
        if (filename == null) {
            throw new IllegalStateException("can't generate real path, the file name is null");
        } else if (directory == null) {
            throw new IllegalStateException("can't generate real path, the directory is null");
        } else {
            return formatString("%s%s%s", directory, File.separator, filename);
        }
    }

    public static void setDefaultSaveRootPath(String path) {
        defaultSaveRootPath = path;
    }

    public static String getTempPath(String targetPath) {
        return formatString("%s.temp", targetPath);
    }

    public static int generateId(String url, String path) {
        return CustomComponentHolder.getImpl().getIdGeneratorInstance().generateId(url, path, false);
    }

    public static int generateId(String url, String path, boolean pathAsDirectory) {
        return CustomComponentHolder.getImpl().getIdGeneratorInstance().generateId(url, path, pathAsDirectory);
    }

    public static String md5(String string) {
        try {
            byte[] hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder(hash.length * 2);
            for (byte b : hash) {
                if ((b & 255) < 16) {
                    hex.append(PushConstants.PUSH_TYPE_NOTIFY);
                }
                hex.append(Integer.toHexString(b & 255));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Huh, MD5 should be supported?", e);
        } catch (UnsupportedEncodingException e2) {
            throw new RuntimeException("Huh, UTF-8 should be supported?", e2);
        }
    }

    public static String getStack() {
        return getStack(true);
    }

    public static String getStack(boolean printLine) {
        return getStack(new Throwable().getStackTrace(), printLine);
    }

    public static String getStack(StackTraceElement[] stackTrace, boolean printLine) {
        if (stackTrace == null || stackTrace.length < 4) {
            return "";
        }
        StringBuilder t = new StringBuilder();
        for (int i = 3; i < stackTrace.length; i++) {
            if (stackTrace[i].getClassName().contains(BuildConfig.APPLICATION_ID)) {
                t.append("[");
                t.append(stackTrace[i].getClassName().substring(BuildConfig.APPLICATION_ID.length()));
                t.append(":");
                t.append(stackTrace[i].getMethodName());
                if (printLine) {
                    t.append(l.s).append(stackTrace[i].getLineNumber()).append(")]");
                } else {
                    t.append("]");
                }
            }
        }
        return t.toString();
    }

    public static boolean isDownloaderProcess(Context context) {
        if (isDownloaderProcess != null) {
            return isDownloaderProcess.booleanValue();
        }
        boolean result = false;
        if (!FileDownloadProperties.getImpl().processNonSeparate) {
            int pid = Process.myPid();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME);
            if (activityManager != null) {
                List<RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
                if (runningAppProcessInfoList != null && !runningAppProcessInfoList.isEmpty()) {
                    for (RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
                        if (processInfo.pid == pid) {
                            result = processInfo.processName.endsWith(":filedownloader");
                            break;
                        }
                    }
                }
                FileDownloadLog.w(FileDownloadUtils.class, "The running app process info list from ActivityManager is null or empty, maybe current App is not running.", new Object[0]);
                return false;
            }
            FileDownloadLog.w(FileDownloadUtils.class, "fail to get the activity manager!", new Object[0]);
            return false;
        }
        result = true;
        isDownloaderProcess = Boolean.valueOf(result);
        return isDownloaderProcess.booleanValue();
    }

    public static String[] convertHeaderString(String nameAndValuesString) {
        String[] lineString = nameAndValuesString.split("\n");
        String[] namesAndValues = new String[(lineString.length * 2)];
        for (int i = 0; i < lineString.length; i++) {
            String[] nameAndValue = lineString[i].split(": ");
            namesAndValues[i * 2] = nameAndValue[0];
            namesAndValues[(i * 2) + 1] = nameAndValue[1];
        }
        return namesAndValues;
    }

    public static long getFreeSpaceBytes(String path) {
        StatFs statFs = new StatFs(path);
        if (VERSION.SDK_INT >= 18) {
            return statFs.getAvailableBytes();
        }
        return ((long) statFs.getAvailableBlocks()) * ((long) statFs.getBlockSize());
    }

    public static String formatString(String msg, Object... args) {
        return String.format(Locale.ENGLISH, msg, args);
    }

    public static void markConverted(Context context) {
        File file = getConvertedMarkedFile(context);
        try {
            file.getParentFile().mkdirs();
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFilenameConverted(Context context) {
        if (filenameConverted == null) {
            filenameConverted = Boolean.valueOf(getConvertedMarkedFile(context).exists());
        }
        return filenameConverted.booleanValue();
    }

    public static File getConvertedMarkedFile(Context context) {
        return new File(context.getFilesDir().getAbsolutePath() + File.separator + "filedownloader", OLD_FILE_CONVERTED_FILE_NAME);
    }

    public static long parseContentRangeFoInstanceLength(String contentRange) {
        long j = -1;
        if (contentRange == null) {
            return j;
        }
        String[] session = contentRange.split("/");
        if (session.length < 2) {
            return j;
        }
        try {
            return Long.parseLong(session[1]);
        } catch (NumberFormatException e) {
            FileDownloadLog.w(FileDownloadUtils.class, "parse instance length failed with %s", contentRange);
            return j;
        }
    }

    public static String parseContentDisposition(String contentDisposition) {
        if (contentDisposition == null) {
            return null;
        }
        try {
            Matcher m = CONTENT_DISPOSITION_QUOTED_PATTERN.matcher(contentDisposition);
            if (m.find()) {
                return m.group(1);
            }
            m = CONTENT_DISPOSITION_NON_QUOTED_PATTERN.matcher(contentDisposition);
            if (m.find()) {
                return m.group(1);
            }
            return null;
        } catch (IllegalStateException e) {
            return null;
        }
    }

    public static String getTargetFilePath(String path, boolean pathAsDirectory, String filename) {
        if (path == null) {
            return null;
        }
        if (!pathAsDirectory) {
            return path;
        }
        if (filename == null) {
            return null;
        }
        return generateFilePath(path, filename);
    }

    public static String getParent(String path) {
        int length = path.length();
        int firstInPath = 0;
        if (File.separatorChar == '\\' && length > 2 && path.charAt(1) == ':') {
            firstInPath = 2;
        }
        int index = path.lastIndexOf(File.separatorChar);
        if (index == -1 && firstInPath > 0) {
            index = 2;
        }
        if (index == -1 || path.charAt(length - 1) == File.separatorChar) {
            return null;
        }
        if (path.indexOf(File.separatorChar) == index && path.charAt(firstInPath) == File.separatorChar) {
            return path.substring(0, index + 1);
        }
        return path.substring(0, index);
    }

    public static String getThreadPoolName(String name) {
        return "FileDownloader-" + name;
    }

    public static boolean isNetworkNotOnWifiType() {
        boolean z = false;
        ConnectivityManager manager = (ConnectivityManager) FileDownloadHelper.getAppContext().getSystemService("connectivity");
        if (manager == null) {
            FileDownloadLog.w(FileDownloadUtils.class, "failed to get connectivity manager!", new Object[0]);
            return true;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null || info.getType() != 1) {
            z = true;
        }
        return z;
    }

    public static boolean checkPermission(String permission) {
        return FileDownloadHelper.getAppContext().checkCallingOrSelfPermission(permission) == 0;
    }

    public static long convertContentLengthString(String s) {
        long j = -1;
        if (s == null) {
            return j;
        }
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
            return j;
        }
    }

    public static String findEtag(int id, FileDownloadConnection connection) {
        if (connection == null) {
            throw new RuntimeException("connection is null when findEtag");
        }
        String newEtag = connection.getResponseHeaderField("Etag");
        if (FileDownloadLog.NEED_LOG) {
            FileDownloadLog.d(FileDownloadUtils.class, "etag find %s for task(%d)", newEtag, Integer.valueOf(id));
        }
        return newEtag;
    }

    public static boolean isAcceptRange(int responseCode, FileDownloadConnection connection) {
        if (responseCode == 206 || responseCode == 1) {
            return true;
        }
        return "bytes".equals(connection.getResponseHeaderField("Accept-Ranges"));
    }

    public static long findInstanceLengthForTrial(FileDownloadConnection connection) {
        long length = findInstanceLengthFromContentRange(connection);
        if (length < 0) {
            length = -1;
            FileDownloadLog.w(FileDownloadUtils.class, "don't get instance length fromContent-Range header", new Object[0]);
        }
        if (length == 0 && FileDownloadProperties.getImpl().trialConnectionHeadMethod) {
            return -1;
        }
        return length;
    }

    public static long findInstanceLengthFromContentRange(FileDownloadConnection connection) {
        return parseContentRangeFoInstanceLength(getContentRangeHeader(connection));
    }

    private static String getContentRangeHeader(FileDownloadConnection connection) {
        return connection.getResponseHeaderField("Content-Range");
    }

    public static long findContentLength(int id, FileDownloadConnection connection) {
        long contentLength = convertContentLengthString(connection.getResponseHeaderField(HttpRequest.HEADER_CONTENT_LENGTH));
        String transferEncoding = connection.getResponseHeaderField("Transfer-Encoding");
        if (contentLength >= 0) {
            return contentLength;
        }
        boolean isEncodingChunked = transferEncoding != null && transferEncoding.equals("chunked");
        if (isEncodingChunked) {
            return -1;
        }
        if (!FileDownloadProperties.getImpl().httpLenient) {
            throw new FileDownloadGiveUpRetryException("can't know the size of the download file, and its Transfer-Encoding is not Chunked either.\nyou can ignore such exception by add http.lenient=true to the filedownloader.properties");
        } else if (!FileDownloadLog.NEED_LOG) {
            return -1;
        } else {
            FileDownloadLog.d(FileDownloadUtils.class, "%d response header is not legal but HTTP lenient is true, so handle as the case of transfer encoding chunk", Integer.valueOf(id));
            return -1;
        }
    }

    public static long findContentLengthFromContentRange(FileDownloadConnection connection) {
        long contentLength = parseContentLengthFromContentRange(getContentRangeHeader(connection));
        if (contentLength < 0) {
            return -1;
        }
        return contentLength;
    }

    public static long parseContentLengthFromContentRange(String contentRange) {
        if (contentRange == null || contentRange.length() == 0) {
            return -1;
        }
        String pattern = "bytes (\\d+)-(\\d+)/\\d+";
        try {
            Matcher m = Pattern.compile("bytes (\\d+)-(\\d+)/\\d+").matcher(contentRange);
            if (!m.find()) {
                return -1;
            }
            return (Long.parseLong(m.group(2)) - Long.parseLong(m.group(1))) + 1;
        } catch (Exception e) {
            FileDownloadLog.e(FileDownloadUtils.class, e, "parse content length from content range error", new Object[0]);
            return -1;
        }
    }

    public static String findFilename(FileDownloadConnection connection, String url) throws FileDownloadSecurityException {
        String filename = parseContentDisposition(connection.getResponseHeaderField("Content-Disposition"));
        if (TextUtils.isEmpty(filename)) {
            return generateFileName(url);
        }
        if (!filename.contains("../")) {
            return filename;
        }
        throw new FileDownloadSecurityException(formatString("The filename [%s] from the response is not allowable, because it contains '../', which can raise the directory traversal vulnerability", filename));
    }

    public static FileDownloadOutputStream createOutputStream(String path) throws IOException {
        if (TextUtils.isEmpty(path)) {
            throw new RuntimeException("found invalid internal destination path, empty");
        } else if (isFilenameValid(path)) {
            File file = new File(path);
            if (file.exists() && file.isDirectory()) {
                throw new RuntimeException(formatString("found invalid internal destination path[%s], & path is directory[%B]", path, Boolean.valueOf(file.isDirectory())));
            } else if (file.exists() || file.createNewFile()) {
                return CustomComponentHolder.getImpl().createOutputStream(file);
            } else {
                throw new IOException(formatString("create new file error  %s", file.getAbsolutePath()));
            }
        } else {
            throw new RuntimeException(formatString("found invalid internal destination filename %s", path));
        }
    }

    public static boolean isBreakpointAvailable(int id, FileDownloadModel model) {
        return isBreakpointAvailable(id, model, null);
    }

    public static boolean isBreakpointAvailable(int id, FileDownloadModel model, Boolean outputStreamSupportSeek) {
        if (model == null) {
            if (!FileDownloadLog.NEED_LOG) {
                return false;
            }
            FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d model == null", Integer.valueOf(id));
            return false;
        } else if (model.getTempFilePath() != null) {
            return isBreakpointAvailable(id, model, model.getTempFilePath(), outputStreamSupportSeek);
        } else {
            if (!FileDownloadLog.NEED_LOG) {
                return false;
            }
            FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d temp path == null", Integer.valueOf(id));
            return false;
        }
    }

    public static boolean isBreakpointAvailable(int id, FileDownloadModel model, String path, Boolean outputStreamSupportSeek) {
        if (path != null) {
            File file = new File(path);
            boolean isExists = file.exists();
            boolean isDirectory = file.isDirectory();
            if (isExists && !isDirectory) {
                long fileLength = file.length();
                long currentOffset = model.getSoFar();
                if (model.getConnectionCount() > 1 || currentOffset != 0) {
                    long totalLength = model.getTotal();
                    if (fileLength < currentOffset || (totalLength != -1 && (fileLength > totalLength || currentOffset >= totalLength))) {
                        if (!FileDownloadLog.NEED_LOG) {
                            return false;
                        }
                        FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d dirty data fileLength[%d] sofar[%d] total[%d]", Integer.valueOf(id), Long.valueOf(fileLength), Long.valueOf(currentOffset), Long.valueOf(totalLength));
                        return false;
                    } else if (outputStreamSupportSeek == null || outputStreamSupportSeek.booleanValue() || totalLength != fileLength) {
                        return true;
                    } else {
                        if (!FileDownloadLog.NEED_LOG) {
                            return false;
                        }
                        FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d, because of the output stream doesn't support seek, but the task has already pre-allocated, so we only can download it from the very beginning.", Integer.valueOf(id));
                        return false;
                    }
                } else if (!FileDownloadLog.NEED_LOG) {
                    return false;
                } else {
                    FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d the downloaded-record is zero.", Integer.valueOf(id));
                    return false;
                }
            } else if (!FileDownloadLog.NEED_LOG) {
                return false;
            } else {
                FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d file not suit, exists[%B], directory[%B]", Integer.valueOf(id), Boolean.valueOf(isExists), Boolean.valueOf(isDirectory));
                return false;
            }
        } else if (!FileDownloadLog.NEED_LOG) {
            return false;
        } else {
            FileDownloadLog.d(FileDownloadUtils.class, "can't continue %d path = null", Integer.valueOf(id));
            return false;
        }
    }

    public static void deleteTaskFiles(String targetFilepath, String tempFilePath) {
        deleteTempFile(tempFilePath);
        deleteTargetFile(targetFilepath);
    }

    public static void deleteTempFile(String tempFilePath) {
        if (tempFilePath != null) {
            File tempFile = new File(tempFilePath);
            if (tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    public static void deleteTargetFile(String targetFilePath) {
        if (targetFilePath != null) {
            File targetFile = new File(targetFilePath);
            if (targetFile.exists()) {
                targetFile.delete();
            }
        }
    }

    public static boolean isNeedSync(long bytesDelta, long timestampDelta) {
        return bytesDelta > ((long) getMinProgressStep()) && timestampDelta > getMinProgressTime();
    }

    public static String defaultUserAgent() {
        return formatString("FileDownloader/%s", BuildConfig.VERSION_NAME);
    }
}
