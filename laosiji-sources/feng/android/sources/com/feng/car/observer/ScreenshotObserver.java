package com.feng.car.observer;

import android.app.Activity;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import com.feng.car.activity.BaseActivity;
import com.feng.car.activity.CarConfigureCompareActivity;
import com.feng.car.activity.ShowBigImageActivity;
import com.feng.car.activity.ShowCarImageNewActivity;
import com.feng.car.activity.VideoFinalPageActivity;
import com.feng.car.databinding.DialogShareScreenShotBinding;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.ShareUtils;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import java.util.List;

public class ScreenshotObserver extends ContentObserver {
    private static final String[] KEYWORDS = new String[]{"screenshot", "screen_shot", "screen-shot", "screen shot", "screencapture", "screen_capture", "screen-capture", "screen capture", "screencap", "screen_cap", "screen-cap", "screen cap"};
    private long lastId;
    private final int m40;
    private Context mContext;
    private ScreenshotObserver mExternalObserver;
    private String mFilePath;
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (ActivityManager.getInstance().getCurrentActivity() != null) {
                ScreenshotObserver.this.showScreenshotDialog(ActivityManager.getInstance().getCurrentActivity());
            }
        }
    };
    private ScreenshotObserver mInternalObserver;
    private final Resources mResources;
    private Bitmap mScreenshotBitmap;
    private Dialog mShareDialog;

    private class ShareShotListener implements OnClickListener {
        private ShareShotListener() {
        }

        /* synthetic */ ShareShotListener(ScreenshotObserver x0, AnonymousClass1 x1) {
            this();
        }

        public void onClick(View v) {
            switch (v.getId()) {
                case 2131624576:
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                case 2131624996:
                case 2131625002:
                    ScreenshotObserver.this.shareShot(SHARE_MEDIA.WEIXIN_CIRCLE);
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                case 2131624997:
                case 2131625003:
                    ScreenshotObserver.this.shareShot(SHARE_MEDIA.WEIXIN);
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                case 2131624998:
                case 2131625004:
                    ScreenshotObserver.this.shareShot(SHARE_MEDIA.SINA);
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                case 2131624999:
                case 2131625005:
                    ScreenshotObserver.this.shareShot(SHARE_MEDIA.QQ);
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                case 2131625000:
                case 2131625006:
                    ScreenshotObserver.this.shareShot(SHARE_MEDIA.QZONE);
                    ScreenshotObserver.this.mShareDialog.dismiss();
                    restoreOrientation();
                    return;
                default:
                    return;
            }
        }

        private void restoreOrientation() {
            Activity activity = ActivityManager.getInstance().getCurrentActivity();
            if (activity != null && ((activity instanceof ShowBigImageActivity) || (activity instanceof ShowCarImageNewActivity) || (activity instanceof CarConfigureCompareActivity))) {
                activity.setRequestedOrientation(2);
            } else if (activity != null && (activity instanceof VideoFinalPageActivity)) {
                ((VideoFinalPageActivity) activity).recoveryGravityScreen();
            }
        }
    }

    public ScreenshotObserver(Context context) {
        super(null);
        this.mContext = context;
        this.mResources = this.mContext.getResources();
        this.m40 = this.mResources.getDimensionPixelSize(2131296601);
    }

    public void startObserve(Context context) {
        if (this.mExternalObserver == null) {
            this.mExternalObserver = new ScreenshotObserver(context);
        }
        if (this.mInternalObserver == null) {
            this.mInternalObserver = new ScreenshotObserver(context);
        }
        context.getContentResolver().registerContentObserver(Media.EXTERNAL_CONTENT_URI, false, this.mExternalObserver);
        context.getContentResolver().registerContentObserver(Media.INTERNAL_CONTENT_URI, false, this.mExternalObserver);
    }

    public void stopObserve() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mInternalObserver);
        this.mContext.getContentResolver().unregisterContentObserver(this.mExternalObserver);
    }

    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        Cursor cursor = null;
        try {
            cursor = this.mContext.getContentResolver().query(Media.EXTERNAL_CONTENT_URI, new String[]{"date_added", "_data", "_id"}, null, null, "date_added desc");
            if (cursor == null) {
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (cursor.moveToFirst()) {
                this.mFilePath = cursor.getString(cursor.getColumnIndex("_data"));
                long addTime = cursor.getLong(cursor.getColumnIndex("date_added"));
                long id = cursor.getLong(cursor.getColumnIndex("_id"));
                if (checkTime(addTime) && checkPath(this.mFilePath) && checkSize(this.mFilePath) && !checkId(id)) {
                    this.lastId = id;
                    if (this.mShareDialog == null || !this.mShareDialog.isShowing()) {
                        if (!isApplicationInBackground(this.mContext)) {
                            this.mHandler.sendEmptyMessage(0);
                        }
                    } else if (cursor != null) {
                        try {
                            cursor.close();
                            return;
                        } catch (Exception e2) {
                            e2.printStackTrace();
                            return;
                        }
                    } else {
                        return;
                    }
                }
                if (cursor != null) {
                    try {
                        cursor.close();
                    } catch (Exception e22) {
                        e22.printStackTrace();
                    }
                }
            } else if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e222) {
                    e222.printStackTrace();
                }
            }
        } catch (Exception e2222) {
            e2222.printStackTrace();
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e22222) {
                    e22222.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (cursor != null) {
                try {
                    cursor.close();
                } catch (Exception e222222) {
                    e222222.printStackTrace();
                }
            }
        }
    }

    public boolean deliverSelfNotifications() {
        return super.deliverSelfNotifications();
    }

    private boolean checkTime(long addTime) {
        return System.currentTimeMillis() - (1000 * addTime) <= 1500;
    }

    private boolean checkId(long id) {
        return this.lastId == id;
    }

    private boolean checkSize(String filePath) {
        DisplayMetrics metrics = getHasVirtualKey();
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (metrics.widthPixels < options.outWidth || metrics.heightPixels < options.outHeight) {
            return false;
        }
        return true;
    }

    private boolean checkPath(String filePath) {
        filePath = filePath.toLowerCase();
        for (String keyWork : KEYWORDS) {
            if (filePath.contains(keyWork)) {
                return true;
            }
        }
        return false;
    }

    private DisplayMetrics getHasVirtualKey() {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager mWindowManager = (WindowManager) this.mContext.getSystemService("window");
        Display display = mWindowManager.getDefaultDisplay();
        mWindowManager.getDefaultDisplay().getMetrics(metrics);
        try {
            Class.forName("android.view.Display").getMethod("getRealMetrics", new Class[]{DisplayMetrics.class}).invoke(display, new Object[]{metrics});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return metrics;
    }

    private void showScreenshotDialog(Context context) {
        ShareShotListener mShareShotListener = new ShareShotListener(this, null);
        DialogShareScreenShotBinding mShotBind = DialogShareScreenShotBinding.inflate(LayoutInflater.from(context));
        mShotBind.tvCancel.setOnClickListener(mShareShotListener);
        mShotBind.tvMoments.setOnClickListener(mShareShotListener);
        mShotBind.tvWeChat.setOnClickListener(mShareShotListener);
        mShotBind.tvWeibo.setOnClickListener(mShareShotListener);
        mShotBind.tvQq.setOnClickListener(mShareShotListener);
        mShotBind.tvQZone.setOnClickListener(mShareShotListener);
        mShotBind.tvMomentsLandscape.setOnClickListener(mShareShotListener);
        mShotBind.tvWeChatLandscape.setOnClickListener(mShareShotListener);
        mShotBind.tvWeiboLandscape.setOnClickListener(mShareShotListener);
        mShotBind.tvQqLandscape.setOnClickListener(mShareShotListener);
        mShotBind.tvQZoneLandscape.setOnClickListener(mShareShotListener);
        Activity activity = (Activity) context;
        if ((activity instanceof ShowBigImageActivity) || (activity instanceof ShowCarImageNewActivity) || (activity instanceof VideoFinalPageActivity)) {
            int orientation = context.getResources().getConfiguration().orientation;
            if (orientation == 2) {
                mShotBind.llLandscape.setVisibility(0);
                mShotBind.llPortrait.setVisibility(8);
                if (activity instanceof VideoFinalPageActivity) {
                    ((VideoFinalPageActivity) activity).keepLandscapeScreen();
                } else {
                    activity.setRequestedOrientation(6);
                }
            } else if (orientation == 1) {
                mShotBind.llLandscape.setVisibility(8);
                mShotBind.llPortrait.setVisibility(0);
                if (activity instanceof VideoFinalPageActivity) {
                    ((VideoFinalPageActivity) activity).keepPortraitScreen();
                } else {
                    activity.setRequestedOrientation(1);
                }
            }
        }
        if (this.mFilePath != null) {
            do {
                this.mScreenshotBitmap = BitmapFactory.decodeFile(this.mFilePath);
            } while (this.mScreenshotBitmap == null);
            mShotBind.ivScreenShot.setImageBitmap(this.mScreenshotBitmap);
        }
        this.mShareDialog = new Dialog(context, 2131361986);
        this.mShareDialog.setCanceledOnTouchOutside(true);
        this.mShareDialog.setCancelable(true);
        Window window = this.mShareDialog.getWindow();
        window.setGravity(80);
        window.setWindowAnimations(2131362223);
        window.setContentView(mShotBind.getRoot());
        window.setLayout(-1, -2);
        this.mShareDialog.show();
    }

    private void shareShot(SHARE_MEDIA media) {
        Bitmap qrCodeBitmap = BitmapFactory.decodeResource(this.mResources, 2130838477);
        this.mScreenshotBitmap = mergeBitmap((this.mScreenshotBitmap.getHeight() + qrCodeBitmap.getHeight()) + (this.m40 * 2), this.mScreenshotBitmap.getWidth(), qrCodeBitmap, (float) ((this.mScreenshotBitmap.getWidth() - qrCodeBitmap.getWidth()) / 2), (float) (this.mScreenshotBitmap.getHeight() + this.m40), this.mScreenshotBitmap, 0.0f, 0.0f);
        ShareUtils.shareScreenshotBitmap((Activity) ActivityManager.getInstance().getCurrentActivity(), this.mScreenshotBitmap, media, new UMShareListener() {
            public void onStart(SHARE_MEDIA share_media) {
            }

            public void onResult(SHARE_MEDIA share_media) {
                ((BaseActivity) ScreenshotObserver.this.mContext).showFirstTypeToast(2131231555);
            }

            public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                ((BaseActivity) ScreenshotObserver.this.mContext).showSecondTypeToast(2131231552);
            }

            public void onCancel(SHARE_MEDIA share_media) {
                ((BaseActivity) ScreenshotObserver.this.mContext).showSecondTypeToast(2131231550);
            }
        });
    }

    private Bitmap mergeBitmap(int newImageH, int newImageW, Bitmap newBitmap, float newX, float newY, Bitmap oldBitmap, float oldX, float oldY) {
        if (newBitmap == null || oldBitmap == null) {
            return null;
        }
        Bitmap newbmp = Bitmap.createBitmap(newImageW, newImageH, Config.RGB_565);
        Canvas cv = new Canvas(newbmp);
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this.mContext, 2131558512));
        cv.drawColor(ContextCompat.getColor(this.mContext, 2131558558));
        cv.drawBitmap(oldBitmap, oldX, oldY, null);
        cv.drawLine(0.0f, newY - ((float) (this.m40 / 2)), (float) newImageW, (newY - ((float) (this.m40 / 2))) - 2.0f, paint);
        cv.drawBitmap(newBitmap, newX, newY, null);
        cv.save(31);
        cv.restore();
        return newbmp;
    }

    private static boolean isApplicationInBackground(Context context) {
        List<RunningTaskInfo> taskList = ((android.app.ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningTasks(1);
        if (!(taskList == null || taskList.isEmpty())) {
            ComponentName topActivity = ((RunningTaskInfo) taskList.get(0)).topActivity;
            if (!(topActivity == null || topActivity.getPackageName().equals(context.getPackageName()))) {
                return true;
            }
        }
        return false;
    }
}
