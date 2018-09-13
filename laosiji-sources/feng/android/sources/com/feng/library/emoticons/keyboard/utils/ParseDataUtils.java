package com.feng.library.emoticons.keyboard.utils;

import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.feng.library.emoticons.keyboard.data.EmoticonEntity;
import com.feng.library.emoticons.keyboard.utils.imageloader.ImageBase.Scheme;
import com.xiaomi.mipush.sdk.MiPushClient;
import java.util.ArrayList;

public class ParseDataUtils {
    public static ArrayList<EmoticonEntity> ParseXhsData(String[] arry, Scheme scheme) {
        try {
            ArrayList<EmoticonEntity> arrayList = new ArrayList();
            for (int i = 0; i < arry.length; i++) {
                if (!TextUtils.isEmpty(arry[i])) {
                    String[] text = arry[i].trim().toString().split(MiPushClient.ACCEPT_TIME_SEPARATOR);
                    if (text != null && text.length == 2) {
                        String fileName;
                        if (scheme != Scheme.DRAWABLE) {
                            fileName = scheme.toUri(text[0]);
                        } else if (text[0].contains(FileUtils.FILE_EXTENSION_SEPARATOR)) {
                            fileName = scheme.toUri(text[0].substring(0, text[0].lastIndexOf(FileUtils.FILE_EXTENSION_SEPARATOR)));
                        } else {
                            fileName = scheme.toUri(text[0]);
                        }
                        arrayList.add(new EmoticonEntity(fileName, text[1]));
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
