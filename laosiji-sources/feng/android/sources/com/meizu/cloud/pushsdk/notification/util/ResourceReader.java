package com.meizu.cloud.pushsdk.notification.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import com.meizu.cloud.pushinternal.DebugLogger;
import java.io.IOException;

public class ResourceReader {
    private static final String TAG = "ResourceReader";
    private static ResourceReader mInstance;
    private AssetManager mAssetManager;
    private Context mContext;

    private ResourceReader(Context context) {
        this.mContext = context;
        init();
    }

    public static ResourceReader getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ResourceReader(context);
        }
        return mInstance;
    }

    private void init() {
        this.mAssetManager = this.mContext.getAssets();
    }

    public Drawable getDrawable(String var1) {
        Drawable drawable = null;
        try {
            return Drawable.createFromStream(this.mAssetManager.open(var1), null);
        } catch (IOException var3) {
            var3.printStackTrace();
            return drawable;
        }
    }

    public Bitmap getBitmap(String var1) {
        try {
            return BitmapFactory.decodeStream(this.mAssetManager.open(var1));
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public int getResId(String resName, String defType) {
        DebugLogger.i(TAG, "Get resource type " + defType + " " + resName);
        return this.mContext.getResources().getIdentifier(resName, defType, this.mContext.getApplicationInfo().packageName);
    }
}
