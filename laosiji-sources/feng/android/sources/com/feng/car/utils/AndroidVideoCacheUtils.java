package com.feng.car.utils;

import android.content.Context;
import java.io.File;
import java.io.IOException;

public class AndroidVideoCacheUtils {
    public static File getVideoCacheDir(Context context) {
        return new File(context.getExternalCacheDir(), "video-cache");
    }

    public static void cleanVideoCacheDir(Context context) throws IOException {
        cleanDirectory(getVideoCacheDir(context));
    }

    private static void cleanDirectory(File file) throws IOException {
        if (file.exists()) {
            File[] contentFiles = file.listFiles();
            if (contentFiles != null) {
                for (File contentFile : contentFiles) {
                    delete(contentFile);
                }
            }
        }
    }

    private static void delete(File file) throws IOException {
        if (file.isFile() && file.exists()) {
            deleteOrThrow(file);
            return;
        }
        cleanDirectory(file);
        deleteOrThrow(file);
    }

    private static void deleteOrThrow(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException(String.format("File %s can't be deleted", new Object[]{file.getAbsolutePath()}));
        }
    }
}
