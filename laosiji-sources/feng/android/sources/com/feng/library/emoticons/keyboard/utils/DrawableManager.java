package com.feng.library.emoticons.keyboard.utils;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Log;
import com.feng.car.video.shortvideo.FileUtils;
import com.feng.library.emoticons.keyboard.EmoticonsRule;
import java.util.LinkedHashMap;
import java.util.Map;

public class DrawableManager {
    private static Map<String, Drawable> drawableMap;
    private static DrawableManager instance;

    private DrawableManager() {
    }

    public static DrawableManager getInstance() {
        if (instance == null) {
            instance = new DrawableManager();
            drawableMap = new LinkedHashMap();
        }
        return instance;
    }

    public void clear() {
        if (drawableMap != null) {
            drawableMap.clear();
            drawableMap = null;
        }
        Log.d(getClass().getSimpleName(), "clear image hashmap");
    }

    public void initAllEmoticonsDrawable(Context context) {
        for (String emoticonName : EmoticonsRule.sXhsEmoticonHashMap.values()) {
            if (emoticonName.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR) >= 0) {
                String emoticonNameSub = emoticonName.substring(0, emoticonName.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR));
                int resID = context.getResources().getIdentifier(emoticonNameSub, "mipmap", context.getPackageName());
                if (resID <= 0) {
                    resID = context.getResources().getIdentifier(emoticonNameSub, "drawable", context.getPackageName());
                }
                try {
                    drawableMap.put(emoticonName, VERSION.SDK_INT >= 21 ? context.getResources().getDrawable(resID, (Theme) null) : context.getResources().getDrawable(resID));
                } catch (Exception var4) {
                    var4.printStackTrace();
                }
            }
        }
    }

    public Drawable getDrawable(String emoticonName) {
        if (drawableMap.containsKey(emoticonName)) {
            return (Drawable) drawableMap.get(emoticonName);
        }
        return null;
    }

    public void putDrawable(String emoticonName, Drawable drawable) {
        if (!drawableMap.containsKey(emoticonName)) {
            drawableMap.put(emoticonName, drawable);
        }
    }
}
