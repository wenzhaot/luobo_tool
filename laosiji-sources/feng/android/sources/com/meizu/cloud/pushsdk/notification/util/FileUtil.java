package com.meizu.cloud.pushsdk.notification.util;

import com.meizu.cloud.pushinternal.DebugLogger;
import com.tencent.ijk.media.player.IjkMediaMeta;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DecimalFormat;

public class FileUtil {
    public static final String TAG = "FileUtil";

    public static void copyFile(String oldPath, String newPath) {
        int bytesum = 0;
        try {
            if (!new File(oldPath).exists()) {
                InputStream inStream = new FileInputStream(oldPath);
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                while (true) {
                    int byteread = inStream.read(buffer);
                    if (byteread != -1) {
                        bytesum += byteread;
                        fs.write(buffer, 0, byteread);
                    } else {
                        inStream.close();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyFolder(String oldPath, String newPath) {
        try {
            new File(newPath).mkdirs();
            String[] file = new File(oldPath).list();
            for (int i = 0; i < file.length; i++) {
                File temp;
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + temp.getName().toString());
                    byte[] b = new byte[5120];
                    while (true) {
                        int len = input.read(b);
                        if (len == -1) {
                            break;
                        }
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                } else if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static File[] listFile(String dir, final String filter) {
        File directory = new File(dir);
        File[] files = new File[0];
        return (directory == null || !directory.isDirectory()) ? files : directory.listFiles(new FileFilter() {
            public boolean accept(File file) {
                try {
                    if (Long.valueOf(filter).longValue() > Long.valueOf(file.getName().split("-")[1]).longValue()) {
                        return true;
                    }
                    return false;
                } catch (Exception e) {
                    DebugLogger.e(FileUtil.TAG, "filters file error " + e.getMessage());
                    return true;
                }
            }
        });
    }

    public static boolean deleteDirectory(String filePath) {
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        File dirFile = new File(filePath);
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (!files[i].isFile()) {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            } else {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }
        if (flag) {
            return dirFile.delete();
        }
        return false;
    }

    public static long getDirSize(File dir) {
        long dirSize = 0;
        if (dir != null && dir.isDirectory()) {
            dirSize = 0;
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    dirSize += file.length();
                } else if (file.isDirectory()) {
                    dirSize = (dirSize + file.length()) + getDirSize(file);
                }
            }
        }
        return dirSize;
    }

    public static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        }
        if (fileS < 1048576) {
            return df.format(((double) fileS) / 1024.0d) + "KB";
        }
        if (fileS < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return df.format(((double) fileS) / 1048576.0d) + "MB";
        }
        return df.format(((double) fileS) / 1.073741824E9d) + "G";
    }
}
