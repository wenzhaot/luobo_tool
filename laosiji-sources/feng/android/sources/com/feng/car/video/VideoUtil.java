package com.feng.car.video;

import android.content.Context;
import com.feng.car.utils.FengConstant;
import com.feng.library.utils.SharedUtil;
import com.stub.StubApp;
import java.io.File;

public class VideoUtil {
    public static final String DEFINITION_KEY = "DEFINITION_KEY";
    public static boolean IGNORE_AUDIO_LOSS = false;
    public static boolean IS_EXIT = false;
    public static boolean hasClick4GPlay = false;
    public static final long maxSurplusSize = 20971520;

    public static void saveDefinition(Context context, int definition) {
        SharedUtil.putInt(StubApp.getOrigApplicationContext(context.getApplicationContext()), DEFINITION_KEY, definition);
    }

    public static int getLocalDefinition(Context context) {
        return SharedUtil.getInt(StubApp.getOrigApplicationContext(context.getApplicationContext()), DEFINITION_KEY, 3);
    }

    public static String generateVideoPath() {
        File outputFolder = new File(FengConstant.VIDEO_EDIT_PATH);
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        return FengConstant.VIDEO_EDIT_PATH + String.format("video_%s.mp4", new Object[]{Long.valueOf(System.currentTimeMillis())});
    }
}
