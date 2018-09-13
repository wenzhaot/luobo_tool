package com.feng.library.utils;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

public class FileUtil {
    public static String SDPATH = (Environment.getExternalStorageDirectory() + "/formats/");

    public static void saveBitmap(Bitmap bm, String picName) {
        try {
            if (!isFileExist("")) {
                createSDDir("");
            }
            File f = new File(SDPATH, picName + ".JPEG");
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
    }

    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH + dirName);
        if (Environment.getExternalStorageState().equals("mounted")) {
            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH + fileName);
        file.isFile();
        return file.exists();
    }

    public static void delFile(String fileName) {
        File file = new File(SDPATH + fileName);
        if (file.isFile()) {
            file.delete();
        }
        file.exists();
    }

    public static void delAbsoluteFile(String fileName) {
        File file = new File(fileName);
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    public static void deleteDir() {
        File dir = new File(SDPATH);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDir();
                }
            }
            dir.delete();
        }
    }

    public static void deleteDir(String path) {
        File dir = new File(path);
        if (dir != null && dir.exists() && dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                } else if (file.isDirectory()) {
                    deleteDir();
                }
            }
            dir.delete();
        }
    }

    public static boolean fileIsExists(String path) {
        try {
            if (new File(path).exists()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getOutputMediaPath() {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "");
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            return null;
        }
        return mediaStorageDir.getPath() + File.separator + "IMG_" + DateUtil.getStringByFormat(new Date(), "yyyyMMddhhmmss") + ".jpg";
    }

    public static String getPicPath() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Spark" + File.separator;
        File pictureFile = new File(path);
        if (!pictureFile.exists()) {
            pictureFile.mkdirs();
        }
        return path + "IMG_" + DateUtil.getStringByFormat(new Date(), "yyyyMMddhhmmss") + ".jpg";
    }

    @SuppressLint({"NewApi"})
    public static String getPath(Context context, Uri uri) {
        boolean isKitKat;
        if (VERSION.SDK_INT >= 1) {
            isKitKat = true;
        } else {
            isKitKat = false;
        }
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            String[] split;
            if (isExternalStorageDocument(uri)) {
                split = DocumentsContract.getDocumentId(uri).split(":");
                if ("primary".equalsIgnoreCase(split[0])) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
                return null;
            } else if (isDownloadsDocument(uri)) {
                return getDataColumn(context, ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(DocumentsContract.getDocumentId(uri)).longValue()), null, null);
            } else if (!isMediaDocument(uri)) {
                return null;
            } else {
                String type = DocumentsContract.getDocumentId(uri).split(":")[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = "_id=?";
                return getDataColumn(context, contentUri, "_id=?", new String[]{split[1]});
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        } else {
            return null;
        }
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = "_data";
        try {
            cursor = context.getContentResolver().query(uri, new String[]{"_data"}, selection, selectionArgs, null);
            if (cursor == null || !cursor.moveToFirst()) {
                if (cursor != null) {
                    cursor.close();
                }
                return null;
            }
            String string = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
            return string;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static boolean isFileExists(String path) {
        if (new File(path).exists()) {
            return true;
        }
        return false;
    }

    public static double showFileAvailable() {
        if (!"mounted".equals(Environment.getExternalStorageState())) {
            return 0.0d;
        }
        StatFs sf = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((double) (((long) sf.getAvailableBlocks()) * ((long) sf.getBlockSize()))) / 1048576.0d;
    }
}
