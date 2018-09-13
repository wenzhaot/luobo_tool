package com.feng.car.receiver;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.content.FileProvider;
import com.feng.car.FengApplication;
import com.feng.car.utils.FengConstant;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CompleteReceiver extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            DownloadManager manager = (DownloadManager) context.getSystemService("download");
            if ("android.intent.action.DOWNLOAD_COMPLETE".equals(intent.getAction())) {
                Query query = new Query();
                query.setFilterById(new long[]{intent.getLongExtra("extra_download_id", 0)});
                Cursor c = null;
                String filePath = null;
                try {
                    c = manager.query(query);
                    while (c.moveToNext()) {
                        filePath = c.getString(c.getColumnIndex("local_uri"));
                    }
                    if (c != null) {
                        c.close();
                    }
                } catch (Throwable e) {
                    MobclickAgent.reportError(context, e);
                    if (c != null) {
                        c.close();
                    }
                } catch (Throwable th) {
                    if (c != null) {
                        c.close();
                    }
                }
                if (id == Long.parseLong(FengApplication.getInstance().getApkId()) && filePath != null) {
                    install(context, filePath);
                }
            }
        }
    }

    public void install(Context context, String filePath) {
        File file = new File(getFilePathFromUri(context, Uri.parse(filePath)));
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setFlags(CommonNetImpl.FLAG_AUTH);
        if (VERSION.SDK_INT >= 24) {
            Uri apkUri = FileProvider.getUriForFile(context, "com.feng.car.file_provider", file);
            intent.addFlags(1);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    public byte[] readSDCard(String fileName) throws IOException {
        FileInputStream fis = new FileInputStream(new File(fileName));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        while (true) {
            int len = fis.read(buffer);
            if (len != -1) {
                bos.write(buffer, 0, len);
            } else {
                byte[] data = bos.toByteArray();
                fis.close();
                bos.close();
                return data;
            }
        }
    }

    public void saveToSDCard(Context context, String fileName, byte[] b) throws IOException {
        File ff = new File(FengConstant.APK_FILE_PATH);
        if (!ff.exists()) {
            ff.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(new File(FengConstant.APK_FILE_PATH, fileName));
        fos.write(b);
        fos.close();
    }

    public static String getFilePathFromUri(Context c, Uri uri) {
        if ("content".equals(uri.getScheme())) {
            String[] filePathColumn = new String[]{"_data"};
            Cursor cursor = c.getContentResolver().query(uri, filePathColumn, null, null, null);
            cursor.moveToFirst();
            String filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            return filePath;
        } else if ("file".equals(uri.getScheme())) {
            return new File(uri.getPath()).getAbsolutePath();
        } else {
            return null;
        }
    }
}
