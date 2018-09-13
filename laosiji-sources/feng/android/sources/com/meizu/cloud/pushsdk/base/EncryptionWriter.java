package com.meizu.cloud.pushsdk.base;

import android.util.Log;
import com.feng.library.utils.DateUtil;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

class EncryptionWriter implements ILogWriter {
    private String TAG = "EncryptionWriter";
    private SimpleDateFormat mDateFormat = new SimpleDateFormat(DateUtil.dateFormatYMD);
    private EncryptBase64 mEncryptBase64 = new EncryptBase64("lo");
    private int mFileCount = 7;
    private String mFileSuffixName = ".log.txt";
    private BufferedWriter mWriter;

    class ComparatorByLastModified implements Comparator<File> {
        ComparatorByLastModified() {
        }

        public int compare(File f1, File f2) {
            long diff = f1.lastModified() - f2.lastModified();
            if (diff > 0) {
                return -1;
            }
            if (diff == 0) {
                return 0;
            }
            return 1;
        }
    }

    void checkFileCount(File dir) {
        File[] files = dir.listFiles(new FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().endsWith(EncryptionWriter.this.mFileSuffixName);
            }
        });
        if (files != null || files.length > this.mFileCount) {
            Arrays.sort(files, new ComparatorByLastModified());
            for (int i = this.mFileCount; i < files.length; i++) {
                files[i].delete();
            }
        }
    }

    public void open(String path) throws IOException {
        File dir = new File(path);
        if (dir.exists() || dir.mkdirs()) {
            String name = this.mDateFormat.format(new Date());
            File file = new File(path, name + this.mFileSuffixName);
            if (!file.exists()) {
                if (file.createNewFile()) {
                    checkFileCount(dir);
                } else {
                    Log.e(this.TAG, "create new file " + name + " failed !!!");
                }
            }
            this.mWriter = new BufferedWriter(new FileWriter(file, true));
            return;
        }
        throw new IOException("create " + path + " dir failed!!!");
    }

    public void write(String header, String tag, String msg) throws IOException {
        if (this.mWriter != null) {
            StringBuffer buffer = new StringBuffer(header);
            buffer.append(tag);
            buffer.append(" ");
            buffer.append(msg);
            this.mWriter.write(this.mEncryptBase64.encode(buffer.toString().getBytes()));
            this.mWriter.write("\r\n");
        }
    }

    public void close() throws IOException {
        if (this.mWriter != null) {
            this.mWriter.flush();
            this.mWriter.close();
            this.mWriter = null;
        }
    }
}
