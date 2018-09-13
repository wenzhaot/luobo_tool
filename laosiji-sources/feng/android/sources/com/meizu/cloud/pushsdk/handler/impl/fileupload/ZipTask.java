package com.meizu.cloud.pushsdk.handler.impl.fileupload;

import android.os.Environment;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipTask {
    private static final int BUFF_SIZE = 1048576;
    private static final int M_SIZE = 1024;
    private static final String TAG = "ZipTask";
    private File zipFile;

    public ZipTask(String zipFilepath) {
        this.zipFile = new File(zipFilepath);
    }

    public boolean zip(String... resFile) {
        try {
            if (!this.zipFile.exists()) {
                this.zipFile.getParentFile().mkdirs();
            }
            List<File> resFileList = new ArrayList();
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            for (String filePath : resFile) {
                File file = new File(root + filePath);
                if (file.exists()) {
                    resFileList.add(file);
                }
            }
            zipFiles(resFileList, this.zipFile);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            DebugLogger.e(TAG, "zip file error " + e.getMessage());
            return false;
        }
    }

    public boolean zip(List<String> listfile) throws Exception {
        if (!this.zipFile.exists()) {
            this.zipFile.getParentFile().mkdirs();
        }
        List<File> resFileList = new ArrayList();
        String root = Environment.getExternalStorageDirectory().getAbsolutePath();
        for (String filePath : listfile) {
            File file = new File(root + filePath);
            if (file.exists()) {
                resFileList.add(file);
            }
        }
        zipFiles(resFileList, this.zipFile);
        return true;
    }

    private void zipFiles(Collection<File> resFileList, File zipFile) throws Exception {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1048576));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.close();
    }

    private void zipFiles(Collection<File> resFileList, File zipFile, String comment) throws Exception {
        ZipOutputStream zipout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFile), 1048576));
        for (File resFile : resFileList) {
            zipFile(resFile, zipout, "");
        }
        zipout.setComment(comment);
        zipout.close();
    }

    private void zipFile(File resFile, ZipOutputStream zipout, String rootpath) throws Exception {
        rootpath = rootpath + (rootpath.trim().length() == 0 ? "" : File.separator) + resFile.getName();
        if (resFile.isDirectory()) {
            for (File file : resFile.listFiles()) {
                zipFile(file, zipout, rootpath);
            }
            return;
        }
        DebugLogger.i(TAG, "current file " + rootpath + "/" + resFile.getName() + " size is " + (resFile.length() / 1024) + "KB");
        if (resFile.length() < 10485760) {
            byte[] buffer = new byte[1048576];
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(resFile), 1048576);
            zipout.putNextEntry(new ZipEntry(rootpath));
            while (true) {
                int realLength = in.read(buffer);
                if (realLength != -1) {
                    zipout.write(buffer, 0, realLength);
                } else {
                    in.close();
                    zipout.flush();
                    zipout.closeEntry();
                    return;
                }
            }
        }
    }
}
