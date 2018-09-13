package com.feng.car.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipboardManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.StatFs;
import android.provider.DocumentsContract;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Video;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.DraweeHolder;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.feng.car.FengApplication;
import com.feng.car.activity.BaseActivity;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.PermissionSettingDialogBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.LocationInfo;
import com.feng.car.entity.sns.SnsInfo;
import com.feng.car.entity.sns.SnsPostResources;
import com.feng.car.entity.thread.CommentInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.listener.CommentItemListener;
import com.feng.car.video.shortvideo.FileUtils;
import com.feng.car.view.BigimageLoadProgressDrawable;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.largeimage.LargeImageView;
import com.feng.car.view.largeimage.factory.FileBitmapDecoderFactory;
import com.feng.library.okhttp.callback.FileCallBack;
import com.feng.library.okhttp.utils.OkHttpUtils;
import com.feng.library.utils.AppUtil;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.Md5Utils;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.feng.library.utils.ToastUtil;
import com.feng.library.utils.WifiUtil;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import com.taobao.accs.utl.UtilityImpl;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tencent.rtmp.TXLiveConstants;
import com.umeng.message.MsgConstant;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import okhttp3.Call;
import org.json.JSONException;
import org.json.JSONObject;

public class FengUtil {
    public static final int TYPE_FORWARD = 1;
    public static final int TYPE_PRAISE = 2;
    private static File appDir = null;

    public static File getAppDir() {
        if (Build.MANUFACTURER.equals("Xiaomi")) {
            if (appDir == null) {
                appDir = new File(Environment.getExternalStorageDirectory(), "/DCIM/Camera/");
            }
        } else if (appDir == null) {
            appDir = new File(Environment.getExternalStorageDirectory(), "/feng/image/photo/");
        }
        return appDir;
    }

    public static String numberFormat(int count) {
        if (count < 0) {
            count = 0;
        }
        if (count <= 99999) {
            return String.valueOf(count);
        }
        try {
            String strNum = (((double) count) / 10000.0d) + "";
            if (strNum.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR) + 2 < strNum.length()) {
                return new BigDecimal(((double) count) / 10000.0d).setScale(1, 5) + "万";
            }
            return String.format("%.1f", new Object[]{Double.valueOf(((double) count) / 10000.0d)}) + "万";
        } catch (Exception e) {
            return new BigDecimal(((double) count) / 10000.0d).setScale(1, 5) + "万";
        }
    }

    public static String numberFormatCar(double count) {
        if (count < 0.0d) {
            count = 0.0d;
        }
        if (count <= 9999.0d) {
            return String.valueOf(count);
        }
        try {
            String strNum = (count / 10000.0d) + "";
            if (strNum.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR) + 3 < strNum.length()) {
                return new BigDecimal((count / 10000.0d) - 0.005d).setScale(2, 4) + "万";
            }
            return String.format("%.2f", new Object[]{Double.valueOf(count / 10000.0d)}) + "万";
        } catch (Exception e) {
            return new BigDecimal(count / 10000.0d).setScale(2, 5) + "万";
        }
    }

    public static String numberFormatCarNew(double count) {
        if (count < 0.0d) {
            count = 0.0d;
        }
        if (count <= 9999.0d) {
            return String.valueOf(count);
        }
        try {
            String strNum = (count / 10000.0d) + "";
            if (strNum.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR) + 3 < strNum.length()) {
                return new BigDecimal((count / 10000.0d) - 0.005d).setScale(2, 4) + "";
            }
            return String.format("%.2f", new Object[]{Double.valueOf(count / 10000.0d)}) + "";
        } catch (Exception e) {
            return new BigDecimal(count / 10000.0d).setScale(2, 5) + "";
        }
    }

    public static String numberFormatPeople(int count) {
        if (count < 0) {
            count = 0;
        }
        if (count <= 99999) {
            return String.valueOf(count);
        }
        return String.format("%.2f", new Object[]{Double.valueOf((((double) count) / 10000.0d) - 0.005d)}) + "万";
    }

    public static String numberFormat99(int count) {
        if (count < 0) {
            count = 0;
        }
        if (count <= 99) {
            return String.valueOf(count);
        }
        return "99+";
    }

    public static void saveLoginUserInfo(Context context, UserInfo userInfo) {
        if (userInfo != null) {
            String strFlag = SharedUtil.getString(context, "new_user_flag");
            if (!(TextUtils.isEmpty(userInfo.createtime) || TextUtils.isEmpty(strFlag))) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateUtil.dateFormatYMD);
                    Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
                    if (strFlag.indexOf("1$" + simpleDateFormat.format(c.getTime())) < 0 || userInfo.createtime.indexOf(simpleDateFormat.format(c.getTime())) < 0) {
                        SharedUtil.putString(context, "new_user_flag", "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            SharedUtil.putInt(context, UserInfo.ID_KEY, userInfo.id);
            SharedUtil.putString(context, UserInfo.TOKEN_KEY, userInfo.token);
            SharedUtil.putString(context, UserInfo.MOBLIE_KEY, userInfo.phonenumber);
            SharedUtil.putString(context, UserInfo.USERNAME_KEY, (String) userInfo.name.get());
            SharedUtil.putString(context, "signature", (String) userInfo.signature.get());
            SharedUtil.putInt(context, UserInfo.SEX_KEY, userInfo.sex.get());
            SharedUtil.putInt(context, UserInfo.IS_ADMINISTRATOR, userInfo.isadministrator);
            SharedUtil.putInt(context, UserInfo.FOLLOW_KEY_COUNT, userInfo.followcount.get());
            SharedUtil.putInt(context, UserInfo.THIRD_WEIBO_STATE, userInfo.connect.weibo);
            SharedUtil.putInt(context, UserInfo.THIRD_WEIXIN_STATE, userInfo.connect.weixin);
            SharedUtil.putInt(context, UserInfo.THIRD_QQ_STATE, userInfo.connect.qq);
            SharedUtil.putInt(context, UserInfo.THIRD_LOGIN_5X, userInfo.connect.login_5x);
            SharedUtil.putInt(context, UserInfo.IS_COMPLETE_INFO, userInfo.iscomplete);
            userInfo.setIsMy(1);
            if (!TextUtils.isEmpty(userInfo.getHeadImageInfo().url)) {
                SharedUtil.putString(context, UserInfo.IMAGE_KEY_URL, userInfo.getHeadImageInfo().url);
                SharedUtil.putInt(context, UserInfo.IMAGE_KEY_WIDTH, userInfo.getHeadImageInfo().width);
                SharedUtil.putInt(context, UserInfo.IMAGE_KEY_HEIGHT, userInfo.getHeadImageInfo().height);
                SharedUtil.putInt(context, UserInfo.IMAGE_KEY_MIMETYPE, userInfo.getHeadImageInfo().mimetype);
            }
        }
    }

    public static UserInfo getUserInfo(Context context) {
        UserInfo user = new UserInfo();
        user.id = SharedUtil.getInt(context, UserInfo.ID_KEY, 0);
        user.token = SharedUtil.getString(context, UserInfo.TOKEN_KEY);
        user.phonenumber = SharedUtil.getString(context, UserInfo.MOBLIE_KEY);
        user.name.set(SharedUtil.getString(context, UserInfo.USERNAME_KEY));
        user.sex.set(SharedUtil.getInt(context, UserInfo.SEX_KEY, 1));
        user.signature.set(SharedUtil.getString(context, "signature"));
        user.isadministrator = SharedUtil.getInt(context, UserInfo.IS_ADMINISTRATOR, 0);
        user.connect.weibo = SharedUtil.getInt(context, UserInfo.THIRD_WEIBO_STATE, 0);
        user.connect.qq = SharedUtil.getInt(context, UserInfo.THIRD_QQ_STATE, 0);
        user.connect.weixin = SharedUtil.getInt(context, UserInfo.THIRD_WEIXIN_STATE, 0);
        user.connect.login_5x = SharedUtil.getInt(context, UserInfo.THIRD_LOGIN_5X, 0);
        user.followcount.set(SharedUtil.getInt(context, UserInfo.FOLLOW_KEY_COUNT, 0));
        user.iscomplete = SharedUtil.getInt(context, UserInfo.IS_COMPLETE_INFO, 0);
        user.iscomplete = SharedUtil.getInt(context, UserInfo.IS_COMPLETE_INFO, 0);
        user.setLocalOpenShopState(context, SharedUtil.getInt(context, UserInfo.LOCAL_OPEN_SHOP_STATE, -2));
        user.setLocalSaleType(context, SharedUtil.getInt(context, UserInfo.LOCAL_OPEN_SHOP_SALE_TYPE, -1));
        user.setIsMy(1);
        user.getHeadImageInfo().url = SharedUtil.getString(context, UserInfo.IMAGE_KEY_URL);
        user.getHeadImageInfo().width = SharedUtil.getInt(context, UserInfo.IMAGE_KEY_WIDTH, FengConstant.IMAGEMIDDLEWIDTH);
        user.getHeadImageInfo().height = SharedUtil.getInt(context, UserInfo.IMAGE_KEY_HEIGHT, FengConstant.IMAGEMIDDLEWIDTH);
        user.getHeadImageInfo().mimetype = SharedUtil.getInt(context, UserInfo.IMAGE_KEY_MIMETYPE, 0);
        if (user.iscomplete == -1 || StringUtil.isEmpty(user.token) || (StringUtil.isEmpty(user.phonenumber) && user.connect.weibo == 0 && user.connect.weixin == 0 && user.connect.qq == 0 && user.connect.login_5x == 0)) {
            return null;
        }
        return user;
    }

    public static String checkResponse(String str) throws JSONException {
        JSONObject response = new JSONObject(str);
        String strResult = response.getString("result");
        if (response.getString(HttpConstant.CHK).equals(Md5Utils.md5(strResult + HttpConstant.APPTOKEN))) {
            return strResult.replace("&lt;", "<").replace("&gt;", ">").replace("&quot;", "\\\"").replace("&acute;", "\\'").replace("&nbsp;", " ");
        }
        return null;
    }

    public static String getDeviceVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "1.0";
        }
    }

    public static String getCarImageSizeUrl(ImageInfo image, int type) {
        if (image == null || StringUtil.isEmpty(image.url)) {
            return "";
        }
        String str = image.url;
        Object[] objArr = new Object[1];
        String str2 = type == 2001 ? FengConstant.CARSMILESIZE : type == 2002 ? FengConstant.CARMIDDLESIZE : FengConstant.CARBIGSIZE;
        objArr[0] = str2;
        return MessageFormat.format(str, objArr);
    }

    public static String getFixedSizeUrl(ImageInfo image, int scaleWidth, int scaleHight) {
        if (image == null || StringUtil.isEmpty(image.url)) {
            return "";
        }
        String strUrl = image.url;
        if (image.width < scaleWidth || image.height < scaleHight) {
            return strUrl + "?imageView2/0/w/" + scaleWidth + "/h/" + scaleHight;
        }
        return strUrl + "?imageView2/3/w/" + scaleWidth + "/h/" + scaleHight;
    }

    public static String getFixedCircleUrl(ImageInfo image, int scaleWidth, int scaleHight) {
        if (image == null || StringUtil.isEmpty(image.url)) {
            return "";
        }
        return image.url + "?imageView2/1/w/" + scaleWidth + "/h/" + scaleHight;
    }

    public static String getHeadImageUrl(ImageInfo image) {
        if (image == null || StringUtil.isEmpty(image.url)) {
            return "";
        }
        String strUrl = getSingleNineScaleUrl(image, 200);
        if (image.mimetype == 3) {
            return strUrl + FengConstant.GIF_SUFFIX;
        }
        if (image.mimetype == 5) {
            return strUrl + FengConstant.GIF_SUFFIX_JPEG;
        }
        return strUrl;
    }

    public static String getUniformScaleUrl(ImageInfo image, int scaleWidth) {
        if (image == null) {
            return "";
        }
        String strUrl = image.url + "?imageView2/2/w/" + scaleWidth;
        if (image.mimetype == 4) {
            return strUrl + FengConstant.GIF_SUFFIX;
        }
        if (image.mimetype == 2) {
            return strUrl + FengConstant.GIF_SUFFIX_JPEG;
        }
        return strUrl;
    }

    public static String getFeedScaleUrl(ImageInfo image, int scaleWidth) {
        if (image == null) {
            return "";
        }
        if (image.width == 0 || image.height == 0) {
            return image.url;
        }
        String strUrl = image.url;
        if (image.width * 3 > image.height * 4) {
            strUrl = strUrl + "?imageView2/2/h/" + ((int) (((float) (scaleWidth * 3)) / 4.0f));
        } else {
            strUrl = strUrl + "?imageView2/2/w/" + scaleWidth;
        }
        if (image.mimetype == 4) {
            return strUrl + FengConstant.GIF_SUFFIX;
        }
        if (image.mimetype == 2) {
            return strUrl + FengConstant.GIF_SUFFIX_JPEG;
        }
        return strUrl;
    }

    public static String getUniformScaleUrl(ImageInfo image, int scaleWidth, float scale) {
        if (image == null) {
            return "";
        }
        int scaleHight = (int) (((float) scaleWidth) * scale);
        String strUrl = image.url + "?imageView2/4/w/" + scaleWidth + "/h/" + scaleHight;
        if (image.mimetype == 4) {
            return strUrl + FengConstant.GIF_SUFFIX;
        }
        if (image.mimetype == 2) {
            return strUrl + FengConstant.GIF_SUFFIX_JPEG;
        }
        return strUrl;
    }

    public static String getSingleNineScaleUrl(ImageInfo image, int scaleWidth) {
        if (image == null) {
            return "";
        }
        return image.url + "?imageView2/4/w/" + scaleWidth + "/h/" + scaleWidth;
    }

    public static int[] getFinalVideoShowWH(ImageInfo model, int maxWidth, int widthComparison, int maxHeight) {
        double scale;
        int[] xy = new int[2];
        if (model.width >= widthComparison) {
            xy[0] = maxWidth;
            xy[1] = (int) (((float) (model.height * maxWidth)) / (((float) model.width) * 1.0f));
        } else {
            scale = (double) (((float) maxWidth) / (((float) widthComparison) * 1.0f));
            xy[0] = (int) (((double) model.width) * scale);
            xy[1] = (int) (((double) model.height) * scale);
        }
        if (xy[1] > maxHeight) {
            scale = (double) (((float) maxHeight) / (((float) xy[1]) * 1.0f));
            xy[1] = (int) (((double) xy[1]) * scale);
            xy[0] = (int) (((double) xy[0]) * scale);
        }
        return xy;
    }

    public static int[] getRelativeWH(ImageInfo model, int screenWidth, int widthComparison) {
        int[] xy = new int[2];
        if (model.width >= widthComparison) {
            xy[0] = screenWidth;
            xy[1] = Math.round(((float) (model.height * screenWidth)) / (((float) model.width) * 1.0f));
        } else {
            float scale = ((float) screenWidth) / (((float) widthComparison) * 1.0f);
            xy[0] = Math.round(((float) model.width) * scale);
            xy[1] = Math.round(((float) model.height) * scale);
        }
        return xy;
    }

    public static int getViewHeight(ImageInfo model, int screenWidth) {
        return Math.round(((float) (model.height * screenWidth)) / (((float) model.width) * 1.0f));
    }

    public static boolean isLongImage(ImageInfo info) {
        return info.height >= info.width * 5 && info.height > 4000;
    }

    public static void clearImageCache(Context context) {
        try {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            imagePipeline.clearMemoryCaches();
            imagePipeline.clearDiskCaches();
        } catch (Exception e) {
            ((BaseActivity) context).showSecondTypeToast(2131230934);
        }
    }

    public static String getCacheSize() {
        long l = 0 + Fresco.getImagePipelineFactory().getMainDiskStorageCache().getSize();
        if (l <= 0) {
            l = 0;
        }
        String str = formatFileSize(l);
        if (str == null || !".00B".equals(str)) {
            return str;
        }
        return "0.00MB";
    }

    private static long getFileSize(File f) {
        long size = 0;
        for (File aFlist : f.listFiles()) {
            long fileSize;
            if (aFlist.isDirectory()) {
                fileSize = getFileSize(aFlist);
            } else {
                fileSize = aFlist.length();
            }
            size += fileSize;
        }
        return size;
    }

    private static String formatFileSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#.00");
        if (fileS < 1024) {
            return df.format((double) fileS) + "B";
        }
        if (fileS < 1048576) {
            return df.format(((double) fileS) / 1024.0d) + "K";
        }
        if (fileS < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return df.format(((double) fileS) / 1048576.0d) + "MB";
        }
        return df.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    public static String formatImageSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#0.00");
        if (fileS <= 0) {
            return "";
        }
        if (fileS < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return df.format(((double) fileS) / 1048576.0d) + "M";
        }
        return df.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    public static String formatVideoSize(long fileS) {
        DecimalFormat mDf = new DecimalFormat("#0");
        DecimalFormat gDf = new DecimalFormat("#0.0");
        if (fileS <= 0) {
            return "";
        }
        if (fileS < 1048576) {
            return "1M";
        }
        if (fileS < IjkMediaMeta.AV_CH_STEREO_RIGHT) {
            return mDf.format(((double) fileS) / 1048576.0d) + "M";
        }
        return gDf.format(((double) fileS) / 1.073741824E9d) + "G";
    }

    private static void deleteFile(File f) {
        for (File fi : f.listFiles()) {
            if (fi.isDirectory()) {
                deleteFile(fi);
            } else {
                fi.delete();
            }
        }
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    public static void changeSearchColorByHtml(TextView textView, String strKey, String strContent, int colorChang) {
        try {
            textView.setText(Html.fromHtml(strContent.replace(strKey, "<font color=" + colorChang + ">" + strKey + "</font>")));
        } catch (Exception e) {
            e.printStackTrace();
            textView.setText(strContent);
        }
    }

    @TargetApi(19)
    public static boolean isMiuiFloatWindowOpAllowed(Context context, boolean islocal) {
        if (!Build.MANUFACTURER.equals("Xiaomi")) {
            return true;
        }
        if (islocal && SharedUtil.getBoolean(context, "click_floating_window", false)) {
            return true;
        }
        if (VERSION.SDK_INT >= 19) {
            return checkOp(context, 24);
        }
        if ((context.getApplicationInfo().flags & 134217728) != 134217728) {
            return false;
        }
        return true;
    }

    @TargetApi(19)
    public static boolean checkOp(Context context, int op) {
        if (VERSION.SDK_INT >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService("appops");
            try {
                Class<?> spClazz = Class.forName(manager.getClass().getName());
                if (((Integer) manager.getClass().getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(manager, new Object[]{Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0) {
                    return true;
                }
                return false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static int getAPPVerionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
    }

    public static String getVersion(Context con) {
        PackageInfo pi = null;
        try {
            pi = con.getPackageManager().getPackageInfo(con.getPackageName(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (pi == null) {
            return "1.1";
        }
        return pi.versionName;
    }

    public static void showSettingPermissionDialog(final Context context, String title, String permission) {
        PermissionSettingDialogBinding binding = PermissionSettingDialogBinding.inflate(LayoutInflater.from(context));
        binding.title.setText(String.format(context.getString(2131231371), new Object[]{title}));
        binding.content.setText(String.format(context.getString(2131231370), new Object[]{permission}));
        final AlertDialog dialog = new Builder(context, 3).setView(binding.getRoot()).setCancelable(true).create();
        dialog.show();
        binding.btCancel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                dialog.dismiss();
            }
        });
        binding.btSetting.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                context.startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + context.getPackageName())));
                dialog.dismiss();
            }
        });
    }

    public static void checkUpdate(Context context, JSONObject json, boolean isAbout, boolean isUpdataHint) {
        try {
            int updateType;
            int forcedUpdate = json.getInt(HttpConstant.FORCED_UPDATE);
            final int code = json.getInt("code");
            final String url = json.getString("url");
            if (json.has("updatetype")) {
                updateType = json.getInt("updatetype");
            } else {
                updateType = 1;
            }
            String description = json.getString(HttpConstant.DESCRIPTION);
            if (isUpdataHint) {
                forcedUpdate = 1;
            }
            View view;
            TextView textView;
            TextView btCancel;
            TextView btUpdate;
            final AlertDialog dialog;
            if (forcedUpdate == 0) {
                if ((code != SharedUtil.getInt(context, HttpConstant.IGNORE_CODE, 0) || isAbout) && code > getAPPVerionCode(context)) {
                    view = LayoutInflater.from(context).inflate(2130903415, null);
                    textView = (TextView) view.findViewById(2131625523);
                    textView.setMovementMethod(ScrollingMovementMethod.getInstance());
                    textView.setText(description);
                    btCancel = (TextView) view.findViewById(2131625348);
                    btUpdate = (TextView) view.findViewById(2131625527);
                    dialog = new Builder(context, 2131362131).setView(view).setCancelable(false).create();
                    final Context context2 = context;
                    btCancel.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            SharedUtil.putInt(context2, HttpConstant.IGNORE_CODE, code);
                            dialog.dismiss();
                        }
                    });
                    context2 = context;
                    btUpdate.setOnClickListener(new OnSingleClickListener() {
                        public void onSingleClick(View v) {
                            if (VERSION.SDK_INT >= 23) {
                                if (context2.checkSelfPermission(MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE) != 0) {
                                    ((BaseActivity) context2).requestPermissions(new String[]{MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE}, PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE);
                                } else if (WifiUtil.isWifiConnectivity(context2)) {
                                    if (updateType == 0) {
                                        FengUtil.downloadApk(context2, url);
                                    } else {
                                        FengUtil.downloadSysApk(context2, url);
                                    }
                                    dialog.dismiss();
                                } else {
                                    dialog.dismiss();
                                    FengUtil.updateSelect(context2, updateType, url);
                                }
                            } else if (WifiUtil.isWifiConnectivity(context2)) {
                                if (updateType == 0) {
                                    FengUtil.downloadApk(context2, url);
                                } else {
                                    FengUtil.downloadSysApk(context2, url);
                                }
                                dialog.dismiss();
                            } else {
                                dialog.dismiss();
                                FengUtil.updateSelect(context2, updateType, url);
                            }
                        }
                    });
                    dialog.show();
                    FengApplication.getInstance().upLoadLog(true, json.toString() + " 连接 = " + url);
                }
            } else if (code > getAPPVerionCode(context)) {
                view = LayoutInflater.from(context).inflate(2130903415, null);
                textView = (TextView) view.findViewById(2131625523);
                textView.setText(description);
                btCancel = (TextView) view.findViewById(2131625348);
                btCancel.setText(2131231111);
                btUpdate = (TextView) view.findViewById(2131625527);
                textView.setText(2131231061);
                btUpdate.setText(2131231685);
                final ProgressBar progressBar = (ProgressBar) view.findViewById(2131625524);
                final TextView tv_progress_num = (TextView) view.findViewById(2131625525);
                dialog = new Builder(context, 2131362131).setView(view).setCancelable(false).create();
                final Context context3 = context;
                final boolean z = isUpdataHint;
                btUpdate.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        if (VERSION.SDK_INT >= 23) {
                            if (context3.checkSelfPermission(MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE) != 0) {
                                if (z) {
                                    dialog.dismiss();
                                }
                                ((BaseActivity) context3).requestPermissions(new String[]{MsgConstant.PERMISSION_WRITE_EXTERNAL_STORAGE}, PermissionsConstant.CODE_FOR_DOWN_WRITE_PERMISSION_BASE);
                            } else if (updateType == 0) {
                                view.findViewById(2131624473).setVisibility(8);
                                view.findViewById(2131625526).setVisibility(8);
                                progressBar.setVisibility(0);
                                tv_progress_num.setVisibility(0);
                                FengUtil.downloadForceApk(context3, url, progressBar, tv_progress_num, dialog);
                            } else {
                                FengUtil.downloadSysApk(context3, url);
                                ((BaseActivity) context3).showFirstTypeToast(2131231279);
                                dialog.dismiss();
                            }
                        } else if (updateType == 0) {
                            view.findViewById(2131624473).setVisibility(8);
                            view.findViewById(2131625526).setVisibility(8);
                            progressBar.setVisibility(0);
                            tv_progress_num.setVisibility(0);
                            FengUtil.downloadForceApk(context3, url, progressBar, tv_progress_num, dialog);
                        } else {
                            FengUtil.downloadSysApk(context3, url);
                            ((BaseActivity) context3).showFirstTypeToast(2131231279);
                            dialog.dismiss();
                        }
                    }
                });
                btCancel.setOnClickListener(new OnSingleClickListener() {
                    public void onSingleClick(View v) {
                        ActivityManager.getInstance().finishAllActivity();
                    }
                });
                dialog.show();
                if (isUpdataHint) {
                    btUpdate.performClick();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void downloadSysApk(Context context, String url) {
        try {
            DownloadManager downloadManager = (DownloadManager) context.getSystemService("download");
            Request request = new Request(Uri.parse(url));
            request.setTitle("老司机更新中");
            request.setAllowedNetworkTypes(3);
            request.setVisibleInDownloadsUi(false);
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "laosiji.apk");
            request.setMimeType("application/vnd.android.package-archive");
            FengApplication.getInstance().setApkId(downloadManager.enqueue(request) + "");
            ((BaseActivity) context).showFirstTypeToast(2131231279);
        } catch (Exception e) {
            e.printStackTrace();
            ((BaseActivity) context).showSecondTypeToast(2131230996);
        }
    }

    private static void downloadApk(Context context, String url) {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(2130838539);
        builder.setContentTitle("下载");
        builder.setContentText("正在下载");
        builder.setOngoing(true);
        final NotificationManager manager = (NotificationManager) context.getSystemService(UMessage.DISPLAY_TYPE_NOTIFICATION);
        manager.notify(10001, builder.build());
        builder.setProgress(100, 0, false);
        String fileName = "laosiji_" + System.currentTimeMillis() + ".apk";
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        final Context context2 = context;
        final String str = fileName;
        OkHttpUtils.get().url(url).build().execute(context, new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName) {
            int currentProgress = 0;

            public void onBefore(okhttp3.Request request) {
                super.onBefore(request);
                ((BaseActivity) context2).showFirstTypeToast(2131231279);
            }

            public void inProgress(float progress, long total) {
                int progressEnlarge = (int) (100.0f * progress);
                if (this.currentProgress != progressEnlarge) {
                    this.currentProgress = progressEnlarge;
                    builder.setProgress(100, progressEnlarge, false);
                    manager.notify(10001, builder.build());
                    builder.setContentText("下载" + progressEnlarge + "%");
                }
            }

            public void onError(Call call, Exception e) {
                ((BaseActivity) context2).showSecondTypeToast(2131230995);
            }

            public void onResponse(File file) {
            }

            public void onAfter() {
                super.onAfter();
                manager.cancel(10001);
                FengUtil.install(context2, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + str);
            }
        });
    }

    private static void downloadForceApk(Context context, String url, ProgressBar progressBar, TextView tv_progress_num, AlertDialog dialog) {
        String fileName = "laosiji_" + System.currentTimeMillis() + ".apk";
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).mkdir();
        final ProgressBar progressBar2 = progressBar;
        final TextView textView = tv_progress_num;
        final Context context2 = context;
        final AlertDialog alertDialog = dialog;
        final String str = fileName;
        OkHttpUtils.get().url(url).build().execute(context, new FileCallBack(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath(), fileName) {
            int currentProgress = 0;

            public void onBefore(okhttp3.Request request) {
                super.onBefore(request);
            }

            public void inProgress(float progress, long total) {
                int progressEnlarge = (int) (100.0f * progress);
                if (this.currentProgress != progressEnlarge) {
                    this.currentProgress = progressEnlarge;
                    if (progressBar2 != null) {
                        progressBar2.setProgress(this.currentProgress);
                        textView.setText("已下载..." + this.currentProgress + "%");
                    }
                }
            }

            public void onError(Call call, Exception e) {
                ((BaseActivity) context2).showSecondTypeToast(2131230995);
            }

            public void onResponse(File file) {
            }

            public void onAfter() {
                super.onAfter();
                if (alertDialog != null) {
                    alertDialog.dismiss();
                }
                FengUtil.install(context2, Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + "/" + str);
            }
        });
    }

    public static void updateSelect(final Context context, final int updateType, final String url) {
        View view = LayoutInflater.from(context).inflate(2130903415, null);
        ((TextView) view.findViewById(2131624296)).setText("提示");
        ((TextView) view.findViewById(2131625523)).setText("您目前非WIFI下，是否继续下载？");
        TextView btCancel = (TextView) view.findViewById(2131625348);
        btCancel.setText("取消");
        TextView btUpdate = (TextView) view.findViewById(2131625527);
        btUpdate.setText("继续");
        final AlertDialog dialog = new Builder(context, 2131362131).setView(view).setCancelable(false).create();
        btCancel.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                dialog.dismiss();
            }
        });
        btUpdate.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (updateType == 0) {
                    FengUtil.downloadApk(context, url);
                } else {
                    FengUtil.downloadSysApk(context, url);
                }
                ((BaseActivity) context).showFirstTypeToast(2131231279);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void copyText(Context context, String text, String tips) {
        ((ClipboardManager) context.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(null, filtrationText(text)));
        ToastUtil.showLong(context, tips);
    }

    public static String pasteText(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService("clipboard");
        if (clipboardManager.hasPrimaryClip()) {
            return clipboardManager.getPrimaryClip().getItemAt(0).coerceToText(context).toString();
        }
        return null;
    }

    public static void changeCopyText(Context context) {
        ClipboardManager cm = (ClipboardManager) context.getSystemService("clipboard");
        if (cm.hasPrimaryClip()) {
            Item item = cm.getPrimaryClip().getItemAt(0);
            if (!TextUtils.isEmpty(item.getText())) {
                String text = item.getText().toString();
                if (!filtrationText(text).equals(text)) {
                    cm.setPrimaryClip(ClipData.newPlainText(null, text));
                }
            }
        }
    }

    private static String filtrationText(String text) {
        String str;
        Matcher matcher = HttpConstant.PATTERN_TRANSFORM_IMAGE.matcher(text);
        while (matcher.find()) {
            str = matcher.group();
            text = matcher.replaceAll(str.substring(0, str.length() - 4));
        }
        Matcher matcherVideo = HttpConstant.PATTERN_TRANSFORM_VIDEO.matcher(text);
        while (matcherVideo.find()) {
            str = matcherVideo.group();
            text = matcherVideo.replaceAll(str.substring(0, str.length() - 4));
        }
        Matcher matcherUrl = HttpConstant.PATTERN_TRANSFORM_URL.matcher(text);
        while (matcherUrl.find()) {
            str = matcherUrl.group();
            text = matcherUrl.replaceAll(str.substring(0, str.length() - 4));
        }
        return text;
    }

    public static void saveImageToGallery(Context context, ImageInfo image, File file, String fileNameWithoutSuffix) throws Exception {
        String fileName;
        if (!getAppDir().exists()) {
            getAppDir().mkdirs();
        }
        if (image.mimetype == 3) {
            fileName = fileNameWithoutSuffix + ".gif";
        } else {
            fileName = fileNameWithoutSuffix + ".jpg";
        }
        File newFile = new File(getAppDir(), fileName);
        InputStream inStream = new FileInputStream(file.getAbsolutePath());
        FileOutputStream fs = new FileOutputStream(newFile.getAbsolutePath());
        byte[] buffer = new byte[1024];
        while (true) {
            int byteread = inStream.read(buffer);
            if (byteread != -1) {
                fs.write(buffer, 0, byteread);
            } else {
                inStream.close();
                fs.close();
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile(newFile));
                context.sendBroadcast(intent);
                return;
            }
        }
    }

    public static String getFileNameWithoutSuffix(String filename) {
        if (filename == null || filename.length() <= 0) {
            return filename;
        }
        int dot = filename.lastIndexOf(46);
        if (dot <= -1 || dot >= filename.length()) {
            return filename;
        }
        return filename.substring(0, dot);
    }

    public static File saveBitmapToFile(Bitmap bm, String path, String fileName) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        bm.compress(CompressFormat.JPEG, 100, bos);
        if (bos != null) {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myCaptureFile;
    }

    public static Bitmap getBitmapFromPath(String mPhotoPath) {
        Options options = new Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        return BitmapFactory.decodeFile(mPhotoPath, options);
    }

    public static Bitmap loadBitmap(String imgpath) {
        ExifInterface exif;
        Bitmap bm = getBitmapFromPath(imgpath);
        int digree = 0;
        try {
            exif = new ExifInterface(imgpath);
        } catch (IOException e) {
            e.printStackTrace();
            exif = null;
        }
        if (exif != null) {
            switch (exif.getAttributeInt("Orientation", 0)) {
                case 3:
                    digree = TXLiveConstants.RENDER_ROTATION_180;
                    break;
                case 6:
                    digree = 90;
                    break;
                case 8:
                    digree = 270;
                    break;
                default:
                    digree = 0;
                    break;
            }
        }
        if (digree == 0) {
            return bm;
        }
        Matrix m = new Matrix();
        m.postRotate((float) digree);
        return Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
    }

    public static void creatImageCache() {
        File parentFile = new File(FengConstant.IMAGE_FILE_PATH_TAKE_PHOTO);
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        File parentFile2 = new File(FengConstant.IMAGE_FILE_PATH);
        if (!parentFile2.exists()) {
            parentFile2.mkdirs();
        }
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

    public static void scaleBitmap(ImageVideoInfo imageItem) {
        Options newOpts = new Options();
        newOpts.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageItem.url, newOpts);
        newOpts.inJustDecodeBounds = false;
        int be = 1;
        if (newOpts.outWidth >= 1600) {
            float f = ((float) newOpts.outWidth) / 1600.0f;
            if (((double) f) < 1.75d || f > 2.0f) {
                be = (int) f;
            } else {
                be = 2;
            }
        }
        if (be > 1) {
            ExifInterface exif;
            newOpts.inSampleSize = be;
            int digree = 0;
            try {
                exif = new ExifInterface(imageItem.url);
            } catch (IOException e) {
                e.printStackTrace();
                exif = null;
            }
            if (exif != null) {
                switch (exif.getAttributeInt("Orientation", 0)) {
                    case 3:
                        digree = TXLiveConstants.RENDER_ROTATION_180;
                        break;
                    case 6:
                        digree = 90;
                        break;
                    case 8:
                        digree = 270;
                        break;
                    default:
                        digree = 0;
                        break;
                }
            }
            Bitmap bitmap = BitmapFactory.decodeFile(imageItem.url, newOpts);
            if (digree != 0) {
                Matrix m = new Matrix();
                m.postRotate((float) digree);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            }
            writeBitmapToSD(FengConstant.IMAGE_FILE_PATH + System.currentTimeMillis(), bitmap, imageItem);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:51:0x0089 A:{SYNTHETIC, Splitter: B:51:0x0089} */
    public static void writeBitmapToSD(java.lang.String r7, android.graphics.Bitmap r8, com.feng.car.entity.model.ImageVideoInfo r9) {
        /*
        r2 = 0;
        r1 = new java.io.File;	 Catch:{ Exception -> 0x00a7 }
        r1.<init>(r7);	 Catch:{ Exception -> 0x00a7 }
        r5 = isCanUseSD();	 Catch:{ Exception -> 0x00a7 }
        if (r5 != 0) goto L_0x001d;
    L_0x000c:
        if (r2 == 0) goto L_0x0017;
    L_0x000e:
        r2.close();	 Catch:{ Exception -> 0x0018 }
        if (r8 == 0) goto L_0x0017;
    L_0x0013:
        r8.recycle();	 Catch:{ Exception -> 0x0018 }
        r8 = 0;
    L_0x0017:
        return;
    L_0x0018:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0017;
    L_0x001d:
        r5 = r1.exists();	 Catch:{ Exception -> 0x00a7 }
        if (r5 != 0) goto L_0x0033;
    L_0x0023:
        r4 = r1.getParentFile();	 Catch:{ Exception -> 0x00a7 }
        r5 = r4.exists();	 Catch:{ Exception -> 0x00a7 }
        if (r5 != 0) goto L_0x0030;
    L_0x002d:
        r4.mkdirs();	 Catch:{ Exception -> 0x00a7 }
    L_0x0030:
        r1.createNewFile();	 Catch:{ Exception -> 0x00a7 }
    L_0x0033:
        r3 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x00a7 }
        r3.<init>(r7);	 Catch:{ Exception -> 0x00a7 }
        r5 = r9.mimeType;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        r6 = "image/jpeg";
        r5 = r5.equals(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        if (r5 == 0) goto L_0x0059;
    L_0x0043:
        r5 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        r6 = 60;
        r8.compress(r5, r6, r3);	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
    L_0x004a:
        r9.url = r7;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        if (r3 == 0) goto L_0x00a9;
    L_0x004e:
        r3.close();	 Catch:{ Exception -> 0x0093 }
        if (r8 == 0) goto L_0x0057;
    L_0x0053:
        r8.recycle();	 Catch:{ Exception -> 0x0093 }
        r8 = 0;
    L_0x0057:
        r2 = r3;
        goto L_0x0017;
    L_0x0059:
        r5 = r9.mimeType;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        r6 = "image/png";
        r5 = r5.equals(r6);	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        if (r5 == 0) goto L_0x007d;
    L_0x0064:
        r5 = android.graphics.Bitmap.CompressFormat.PNG;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        r6 = 60;
        r8.compress(r5, r6, r3);	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        goto L_0x004a;
    L_0x006c:
        r0 = move-exception;
        r2 = r3;
    L_0x006e:
        r0.printStackTrace();	 Catch:{ all -> 0x00a5 }
        if (r2 == 0) goto L_0x0017;
    L_0x0073:
        r2.close();	 Catch:{ Exception -> 0x009a }
        if (r8 == 0) goto L_0x0017;
    L_0x0078:
        r8.recycle();	 Catch:{ Exception -> 0x009a }
        r8 = 0;
        goto L_0x0017;
    L_0x007d:
        r5 = android.graphics.Bitmap.CompressFormat.JPEG;	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        r6 = 60;
        r8.compress(r5, r6, r3);	 Catch:{ Exception -> 0x006c, all -> 0x0085 }
        goto L_0x004a;
    L_0x0085:
        r5 = move-exception;
        r2 = r3;
    L_0x0087:
        if (r2 == 0) goto L_0x0092;
    L_0x0089:
        r2.close();	 Catch:{ Exception -> 0x00a0 }
        if (r8 == 0) goto L_0x0092;
    L_0x008e:
        r8.recycle();	 Catch:{ Exception -> 0x00a0 }
        r8 = 0;
    L_0x0092:
        throw r5;
    L_0x0093:
        r0 = move-exception;
        r0.printStackTrace();
        r2 = r3;
        goto L_0x0017;
    L_0x009a:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0017;
    L_0x00a0:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x0092;
    L_0x00a5:
        r5 = move-exception;
        goto L_0x0087;
    L_0x00a7:
        r0 = move-exception;
        goto L_0x006e;
    L_0x00a9:
        r2 = r3;
        goto L_0x0017;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.utils.FengUtil.writeBitmapToSD(java.lang.String, android.graphics.Bitmap, com.feng.car.entity.model.ImageVideoInfo):void");
    }

    public static boolean isCanUseSD() {
        try {
            return Environment.getExternalStorageState().equals("mounted");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showSoftInput(Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).toggleSoftInput(0, 2);
    }

    public static void closeSoftKeyboard(View view) {
        if (view != null && view.getWindowToken() != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService("input_method");
            if (imm.isActive()) {
                imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
            }
        }
    }

    public static boolean isShowPostInitTips(Context context) {
        try {
            if (SharedUtil.getInt(context, FengConstant.POST_INIT_TIPS_KEY, 0) == getAPPVerionCode(context)) {
                return false;
            }
            SharedUtil.putInt(context, FengConstant.POST_INIT_TIPS_KEY, getAPPVerionCode(context));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static String getReportUrl(Context context, int type, int tid, int tuid) {
        StringBuffer sb = new StringBuffer();
        sb.append("userid=" + FengApplication.getInstance().getUserInfo().id);
        sb.append("&typeid=" + type);
        sb.append("&tid=" + tid);
        sb.append("&tuid=" + tuid);
        sb.append("&systemtype=4");
        sb.append("&version=" + getVersion(context));
        sb.append("&device=" + getDeviceModel(context));
        sb.append("&clientid=1");
        sb.append("&chk=" + Md5Utils.md5(HttpConstant.REPORT_URL + "token=8ec84ca0ae4b940c5eccd358555643e&" + sb.toString()));
        return HttpConstant.REPORT_URL + sb.toString();
    }

    public static String getDeviceModel(Context context) {
        String model = Build.MODEL;
        String release = VERSION.RELEASE;
        String strWifi = "";
        if (AppUtil.isMobile(context)) {
            strWifi = UtilityImpl.NET_TYPE_3G;
        }
        if (WifiUtil.isWifiConnectivity(context)) {
            strWifi = UtilityImpl.NET_TYPE_WIFI;
        }
        return model + "[" + release + "][" + strWifi + "]";
    }

    public static void showCommentOperationDialog(Context context, CommentInfo commentInfo, SnsInfo snsInfo, boolean canDelete, int viewDy, String key, CommentItemListener listener) {
        List<DialogItemEntity> list = new ArrayList();
        boolean isMyComment = FengApplication.getInstance().isLoginUser() ? isMine(commentInfo.user.id) : false;
        boolean hasDelete = commentInfo.isdel != 0;
        boolean isMineSns = FengApplication.getInstance().isLoginUser() ? isMine(snsInfo.user.id) : false;
        Resources resources = context.getResources();
        if (isMyComment) {
            if (!hasDelete) {
                list.add(new DialogItemEntity(resources.getString(2131231481), false, 50001));
                list.add(new DialogItemEntity(resources.getString(2131230973), false, 50003));
                list.add(new DialogItemEntity(resources.getString(2131230982), true, 50005));
            }
        } else if (hasDelete) {
            list.add(new DialogItemEntity(resources.getString(2131231415), false, 50002));
        } else {
            list.add(new DialogItemEntity(resources.getString(2131231481), false, 50001));
            list.add(new DialogItemEntity(resources.getString(2131231415), false, 50002));
            list.add(new DialogItemEntity(resources.getString(2131230973), false, 50003));
            list.add(new DialogItemEntity(resources.getString(2131231482), false, 50004));
            if (isMineSns) {
                list.add(new DialogItemEntity(resources.getString(2131230982), true, 50005));
            }
        }
        if (!canDelete) {
            for (int i = 0; i < list.size(); i++) {
                if (((DialogItemEntity) list.get(i)).itemTag == 50005) {
                    list.remove(i);
                }
            }
        }
        final CommentInfo commentInfo2 = commentInfo;
        final Context context2 = context;
        final SnsInfo snsInfo2 = snsInfo;
        final int i2 = viewDy;
        final String str = key;
        final CommentItemListener commentItemListener = listener;
        CommonDialog.showCommonDialog(context, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                commentInfo2.processMenuClick(dialogItemEntity.itemTag, context2, snsInfo2, i2, str, commentItemListener);
            }
        });
    }

    public static void textViewCopy(TextView text, final Context context) {
        text.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                FengUtil.showCopyDialog(v, context);
                return false;
            }
        });
    }

    public static void nubiaMannager(TextView text, final Context context) {
        if (Build.MANUFACTURER.equals("nubia")) {
            text.setOnLongClickListener(new OnLongClickListener() {
                public boolean onLongClick(View v) {
                    FengUtil.showCopyDialog(v, context);
                    return false;
                }
            });
        }
    }

    public static void showCopyDialog(View text, Context context) {
        copyText(context, ((TextView) text).getText().toString(), "文字已复制到剪切板");
    }

    public static void setPostImageSize(SnsPostResources resources, Context context, View view) {
        LayoutParams imageParams;
        int mWidth = context.getResources().getDisplayMetrics().widthPixels;
        if (resources.image.width == 0 || resources.image.height == 0) {
            imageParams = new LayoutParams(mWidth, -2);
        } else {
            imageParams = new LayoutParams(mWidth, getRelativeWH(resources.image, mWidth, FengConstant.IMAGEMIDDLEWIDTH)[1]);
        }
        imageParams.setMargins(0, 0, 0, -1);
        view.setLayoutParams(imageParams);
    }

    public static void downLoadImageToLargeImageView(final Context context, final String imageUrl, final LargeImageView imageView) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl)).setProgressiveRenderingEnabled(true).build();
        Fresco.getImagePipeline().fetchDecodedImage(imageRequest, context).subscribe(new BaseBitmapDataSubscriber() {
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                final FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
                if (resource != null) {
                    ((Activity) context).runOnUiThread(new Runnable() {
                        public void run() {
                            if (resource.getFile() != null) {
                                imageView.setImage(new FileBitmapDecoderFactory(resource.getFile()));
                            }
                        }
                    });
                }
            }

            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
        ((PipelineDraweeController) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setOldController(DraweeHolder.create(new GenericDraweeHierarchyBuilder(context.getResources()).build(), context).getController())).setImageRequest(imageRequest)).build()).onClick();
    }

    public static void downLoadImageToLargeImageView(final Context context, final String imageUrl, final LargeImageView imageView, BigimageLoadProgressDrawable progressDrawable) {
        ImageRequest imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl)).setProgressiveRenderingEnabled(true).build();
        Fresco.getImagePipeline().fetchDecodedImage(imageRequest, context).subscribe(new BaseBitmapDataSubscriber() {
            public void onNewResultImpl(@Nullable Bitmap bitmap) {
                final FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(imageUrl));
                ((Activity) context).runOnUiThread(new Runnable() {
                    public void run() {
                        if (resource != null && resource.getFile() != null) {
                            imageView.setImage(new FileBitmapDecoderFactory(resource.getFile()));
                        }
                    }
                });
            }

            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
        ((PipelineDraweeController) ((PipelineDraweeControllerBuilder) ((PipelineDraweeControllerBuilder) Fresco.newDraweeControllerBuilder().setOldController(DraweeHolder.create(new GenericDraweeHierarchyBuilder(context.getResources()).build(), context).getController())).setImageRequest(imageRequest)).build()).onClick();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService("connectivity");
        if (connectivity == null) {
            return false;
        }
        NetworkInfo[] info = connectivity.getAllNetworkInfo();
        if (info == null) {
            return false;
        }
        for (NetworkInfo state : info) {
            if (state.getState() == State.CONNECTED) {
                return true;
            }
        }
        return false;
    }

    public static boolean isWifiConnectivity(Context context) {
        NetworkInfo activeNetInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetInfo == null || activeNetInfo.getType() != 1) {
            return false;
        }
        return true;
    }

    public static boolean isMine(int id) {
        if (FengApplication.getInstance().isLoginUser()) {
            if (id == FengApplication.getInstance().getUserInfo().id) {
                return true;
            }
        } else if (SharedUtil.getInt(FengApplication.getInstance(), "usertouristId", 0) > 0) {
            return true;
        }
        return false;
    }

    public static Spanned changeSearchKeyWord(String content, String keyWord) {
        if (TextUtils.isEmpty(keyWord)) {
            return new SpannedString(content);
        }
        if (content.indexOf(keyWord) < 0) {
            return new SpannedString(content);
        }
        return fromHtml(content.replace(keyWord, "<font color=#87000000>" + keyWord + "</font>"));
    }

    public static Spanned changeSearchKeyWord2Red(String content, String keyWord) {
        if (TextUtils.isEmpty(keyWord)) {
            return new SpannedString(content);
        }
        return fromHtml(content.replace(keyWord, "<font color=#c9272f>" + keyWord + "</font>"));
    }

    public static Spanned fromHtml(String source) {
        if (VERSION.SDK_INT >= 24) {
            return Html.fromHtml(source, 0);
        }
        return Html.fromHtml(source);
    }

    public static void saveLogToTxt(String str) {
        File rootfile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/feng/");
        if (!rootfile.exists()) {
            rootfile.mkdirs();
        }
        File outputFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/feng/" + String.valueOf(new Date().getTime()) + ".txt");
        if (!outputFile.exists()) {
            try {
                outputFile.createNewFile();
                RandomAccessFile raf = new RandomAccessFile(outputFile, "rwd");
                raf.seek(outputFile.length());
                raf.write(str.getBytes());
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LocationInfo getImageLocationInfo(View view, int position, String hash) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        LocationInfo info = new LocationInfo();
        info.left = location[0];
        info.top = location[1];
        info.width = view.getWidth();
        info.height = view.getHeight();
        info.position = position;
        info.hash = hash;
        return info;
    }

    public static void install(Context context, String filePath) {
        File file = new File(filePath);
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

    public static boolean isNotificationAuthorityEnabled(Context context) {
        if (VERSION.SDK_INT >= 19) {
            return NotificationManagerCompat.from(context).areNotificationsEnabled();
        }
        return true;
    }

    private static boolean isNotificationEnabled(Context context) {
        if (VERSION.SDK_INT < 19) {
            String CHECK_OP_NO_THROW = "checkOpNoThrow";
            String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";
            AppOpsManager mAppOps = (AppOpsManager) context.getSystemService("appops");
            ApplicationInfo appInfo = context.getApplicationInfo();
            String pkg = StubApp.getOrigApplicationContext(context.getApplicationContext()).getPackageName();
            int uid = appInfo.uid;
            try {
                Class appOpsClass = Class.forName(AppOpsManager.class.getName());
                Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, new Class[]{Integer.TYPE, Integer.TYPE, String.class});
                int value = ((Integer) appOpsClass.getDeclaredField(OP_POST_NOTIFICATION).get(Integer.class)).intValue();
                return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, new Object[]{Integer.valueOf(value), Integer.valueOf(uid), pkg})).intValue() == 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static String formateForwardAndPraiseText(int type, int count) {
        String str = "";
        if (count > 0) {
            return numberFormat(count);
        }
        if (type == 1) {
            return "转发";
        }
        return "赞";
    }

    public static String formatWxMinPath(String path, Object... arguments) {
        int length;
        if (FengApplication.getInstance().isLoginUser()) {
            length = arguments.length;
            if (length == 0) {
                return MessageFormat.format(path, new Object[]{FengApplication.getInstance().getUserInfo().token});
            } else if (length == 1) {
                return MessageFormat.format(path, new Object[]{FengApplication.getInstance().getUserInfo().token, arguments[0]});
            } else if (length == 2) {
                return MessageFormat.format(path, new Object[]{FengApplication.getInstance().getUserInfo().token, arguments[0], arguments[1]});
            } else {
                return MessageFormat.format(path, new Object[]{FengApplication.getInstance().getUserInfo().token});
            }
        }
        length = arguments.length;
        if (length == 0) {
            return MessageFormat.format(path, new Object[]{FengConstant.USERTOURISTTOKEN});
        } else if (length == 1) {
            return MessageFormat.format(path, new Object[]{FengConstant.USERTOURISTTOKEN, arguments[0]});
        } else if (length == 2) {
            return MessageFormat.format(path, new Object[]{FengConstant.USERTOURISTTOKEN, arguments[0], arguments[1]});
        } else {
            return MessageFormat.format(path, new Object[]{FengConstant.USERTOURISTTOKEN});
        }
    }

    public static long getAvailableExternalMemorySize() {
        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        return ((long) stat.getAvailableBlocks()) * ((long) stat.getBlockSize());
    }

    public static boolean externalMemoryAvailable() {
        return Environment.getExternalStorageState().equals("mounted");
    }
}
