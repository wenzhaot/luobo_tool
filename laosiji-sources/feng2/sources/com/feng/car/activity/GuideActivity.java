package com.feng.car.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.text.TextUtils;
import android.view.View;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.SimpleCacheKey;
import com.facebook.common.util.UriUtil;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityGuideBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.OpenActivityEvent;
import com.feng.car.event.UserLoginEvent;
import com.feng.car.operation.OperationCallback;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class GuideActivity extends BaseActivity<ActivityGuideBinding> {
    private static final int CROP_FROM_CAMERA = 22;
    private static final int PICK_FROM_CAMERA = 11;
    private static final int PICK_FROM_FILE = 33;
    private final int LOCAL_HEAD_IMAGE = 2;
    private final int THIRD_LOGIN_HEAD_IMAGE = 1;
    private final int USER_HAS_HEAD_IMAGE = 0;
    private File mCurrentPhotoFile;
    private File mFile;
    private boolean mHasSelectHead = false;
    private Uri mImageCaptureUri;
    private String mImgToken;
    private boolean mIsQiNiuInit = false;
    private Pattern mNickNamePattern = Pattern.compile("^[\\w\\u4e00-\\u9fa5\\-]+$");
    private Bitmap mPhoto = null;
    private String mThirdHeadUrl;
    private int mType = 0;
    private UserInfo mUserInfo;

    public int setBaseContentView() {
        return R.layout.activity_guide;
    }

    public void initView() {
        initNormalTitleBar("完善资料");
        this.mRootBinding.titleLine.ivTitleLeft.setVisibility(8);
        closeSwip();
        this.mUserInfo = FengApplication.getInstance().getUserInfo();
        SharedUtil.putInt(this, "is_complete_info", -1);
        if (this.mUserInfo == null) {
            this.mUserInfo = new UserInfo();
        }
        this.mUserInfo.setIsMy(1);
        ((ActivityGuideBinding) this.mBaseBinding).afdGuideHead.setOnClickListener(this);
        ((ActivityGuideBinding) this.mBaseBinding).btGuideFinish.setOnClickListener(this);
        initData();
    }

    private void initData() {
        initUserInfo();
        if (!TextUtils.isEmpty(SharedUtil.getString(this, "login_third_info"))) {
            try {
                JSONObject json = new JSONObject(SharedUtil.getString(this, "login_third_info"));
                ((ActivityGuideBinding) this.mBaseBinding).etGuideName.setText(json.getString("name"));
                this.mThirdHeadUrl = json.getString("url");
                ((ActivityGuideBinding) this.mBaseBinding).afdGuideHead.setHeadUrl(this.mThirdHeadUrl);
                this.mType = 1;
                if (json.getString("sex").equals("1")) {
                    ((ActivityGuideBinding) this.mBaseBinding).rbGuideMale.setChecked(true);
                } else {
                    ((ActivityGuideBinding) this.mBaseBinding).rbGuideFemale.setChecked(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                this.mType = 2;
            }
        }
    }

    private void initUserInfo() {
        if (!StringUtil.isEmpty((String) this.mUserInfo.name.get())) {
            if (((String) this.mUserInfo.name.get()).indexOf("用户_") == 0) {
                ((ActivityGuideBinding) this.mBaseBinding).etGuideName.setText("");
            } else {
                ((ActivityGuideBinding) this.mBaseBinding).etGuideName.setText((CharSequence) this.mUserInfo.name.get());
            }
        }
        if (StringUtil.isEmpty(this.mUserInfo.getHeadImageInfo().url) || this.mUserInfo.getHeadImageInfo().url.equals("http://imageqiniu.laosiji.com/FqSLtpREo7dZPU4hBeja7D3RBf7N")) {
            this.mType = 2;
        } else {
            this.mType = 0;
            ((ActivityGuideBinding) this.mBaseBinding).afdGuideHead.setHeadUrl(FengUtil.getHeadImageUrl(this.mUserInfo.getHeadImageInfo()));
        }
        if (this.mUserInfo.sex.get() == 2) {
            ((ActivityGuideBinding) this.mBaseBinding).rbGuideFemale.setChecked(true);
        } else if (this.mUserInfo.sex.get() == 1) {
            ((ActivityGuideBinding) this.mBaseBinding).rbGuideMale.setChecked(true);
        }
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.bt_guide_finish /*2131624375*/:
                if (this.mType == 0) {
                    saveData(null);
                    return;
                } else {
                    startUpLoadPhoto();
                    return;
                }
            case R.id.afd_guide_head /*2131624376*/:
                showphotoDialog();
                return;
            default:
                return;
        }
    }

    private void showphotoDialog() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("相册", false));
        list.add(new DialogItemEntity("拍照", false));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    GuideActivity.this.checkPermission();
                } else if (position == 1) {
                    GuideActivity.this.checkPermissionCamera();
                }
            }
        });
    }

    private boolean checkName(String strName) {
        if (TextUtils.isEmpty(strName)) {
            showThirdTypeToast((int) R.string.nick_name_empty_hint);
            return false;
        }
        Matcher matcher = this.mNickNamePattern.matcher(strName);
        if (strName.length() > 0 && !matcher.find()) {
            showThirdTypeToast((int) R.string.nick_name_hint);
            return false;
        } else if (StringUtil.strLength(strName) >= 4 && StringUtil.strLength(strName) <= 16) {
            return true;
        } else {
            showThirdTypeToast((int) R.string.nickname_long_tips);
            return false;
        }
    }

    private void saveData(Map<String, Object> map) {
        final String strName = ((ActivityGuideBinding) this.mBaseBinding).etGuideName.getText().toString();
        if (checkName(strName)) {
            int gender;
            if (map == null) {
                showProgress("", "保存信息，请稍候...");
                map = new HashMap();
            }
            map.put("name", strName);
            if (((ActivityGuideBinding) this.mBaseBinding).rbGuideMale.isChecked()) {
                gender = 1;
            } else {
                gender = 2;
            }
            map.put("sex", String.valueOf(gender));
            Map<String, Object> map2 = new HashMap();
            map2.put("info", map);
            this.mUserInfo.updateInfoOperation(map2, new OperationCallback() {
                public void onNetworkError() {
                    GuideActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    GuideActivity.this.hideProgress();
                }

                public void onStart() {
                }

                public void onFinish() {
                    GuideActivity.this.hideProgress();
                }

                public void onFailure(int statusCode, String content, Throwable error) {
                    GuideActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    GuideActivity.this.hideProgress();
                }

                public void onSuccess(String content) {
                    try {
                        JSONObject jsonResult = new JSONObject(content);
                        int code = jsonResult.getInt("code");
                        if (code == 1) {
                            JSONObject bodyJson = jsonResult.getJSONObject("body");
                            if (bodyJson.has("image")) {
                                JSONObject imageJson = bodyJson.getJSONObject("image");
                                ImageInfo image = new ImageInfo();
                                image.parser(imageJson);
                                if (!StringUtil.isEmpty(image.url)) {
                                    if (FengApplication.getInstance().getUserInfo().getHeadImageInfo() == null) {
                                        FengApplication.getInstance().getUserInfo().setHeadImageInfo(new ImageInfo());
                                    }
                                    FengApplication.getInstance().getUserInfo().getHeadImageInfo().url = image.url;
                                    FengApplication.getInstance().getUserInfo().getHeadImageInfo().width = image.width;
                                    FengApplication.getInstance().getUserInfo().getHeadImageInfo().height = image.height;
                                    FengApplication.getInstance().getUserInfo().getHeadImageInfo().mimetype = image.mimetype;
                                    FengApplication.getInstance().saveUserInfo();
                                }
                            }
                            FengApplication.getInstance().getUserInfo().name.set(strName);
                            FengApplication.getInstance().getUserInfo().sex.set(gender);
                            FengApplication.getInstance().getUserInfo().iscomplete = 1;
                            FengApplication.getInstance().getUserInfo().local_new_user = true;
                            FengApplication.getInstance().saveUserInfo();
                            EventBus.getDefault().post(new UserLoginEvent(true));
                            if (FengApplication.getInstance().isAllowFollowShow()) {
                                GuideActivity.this.startActivity(new Intent(GuideActivity.this, FindFollowActivity.class));
                            } else {
                                EventBus.getDefault().post(new OpenActivityEvent());
                            }
                            GuideActivity.this.hideProgress();
                            GuideActivity.this.finish();
                        } else if (code == -21) {
                            GuideActivity.this.hideProgress();
                            GuideActivity.this.showThirdTypeToast((int) R.string.nick_name_exist);
                        } else if (code == -47) {
                            GuideActivity.this.hideProgress();
                            GuideActivity.this.showSecondTypeToast((int) R.string.nick_name_sensitive);
                        } else {
                            FengApplication.getInstance().checkCode("user/update/", code);
                            GuideActivity.this.hideProgress();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        GuideActivity.this.hideProgress();
                        FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                    }
                }
            });
        }
    }

    private void checkPermission() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            takeGallery();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
    }

    private void checkPermissionCamera() {
        if (VERSION.SDK_INT >= 23) {
            int hasCameraPermission = checkSelfPermission("android.permission.CAMERA");
            int hasWriteContactsPermission = checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
            if (hasCameraPermission != 0 && hasWriteContactsPermission != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.CAMERA"}, 50003);
                return;
            } else if (hasCameraPermission != 0 && hasWriteContactsPermission == 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 50003);
                return;
            } else if (hasCameraPermission == 0 && hasWriteContactsPermission != 0) {
                requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50003);
                return;
            }
        }
        takePhoto();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int i = 0;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                    takeGallery();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 50003) {
            int length = grantResults.length;
            while (i < length) {
                if (grantResults[i] == 0) {
                    i++;
                } else {
                    return;
                }
            }
            takePhoto();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 11:
                    Intent intent = new Intent(this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", this.mFile.getAbsolutePath());
                    startActivityForResult(intent, 22);
                    return;
                case 22:
                    if (data != null) {
                        try {
                            Options options = new Options();
                            options.inPreferredConfig = Config.ARGB_8888;
                            options.inPurgeable = true;
                            options.inInputShareable = true;
                            this.mPhoto = BitmapFactory.decodeFile(data.getStringExtra("IMAGE_PATH_KEY"), options);
                            if (this.mPhoto != null) {
                                showImage();
                                return;
                            } else {
                                showSecondTypeToast((int) R.string.edit_head_image_faile);
                                return;
                            }
                        } catch (Exception e) {
                            showSecondTypeToast((int) R.string.edit_head_image_faile);
                            return;
                        }
                    }
                    return;
                case 33:
                    if (data != null) {
                        this.mImageCaptureUri = data.getData();
                        if (VERSION.SDK_INT >= 19) {
                            this.mImageCaptureUri = Uri.parse("file://" + FengUtil.getPath(this, this.mImageCaptureUri));
                        }
                        doCrop();
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private void showImage() {
        this.mHasSelectHead = true;
        this.mCurrentPhotoFile = FengUtil.saveBitmapToFile(this.mPhoto, FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
        this.mType = 2;
        ((ActivityGuideBinding) this.mBaseBinding).afdGuideHead.setHeadUrl("file://" + this.mCurrentPhotoFile.getAbsolutePath());
    }

    private void startUpLoadPhoto() {
        if (!checkName(((ActivityGuideBinding) this.mBaseBinding).etGuideName.getText().toString())) {
            return;
        }
        if (this.mIsQiNiuInit) {
            uploadPhoto();
        } else {
            initQiNiuToken();
        }
    }

    public void finish() {
        super.finish();
        if (this.mPhoto != null) {
            this.mPhoto.recycle();
            this.mPhoto = null;
        }
        setResult(-1);
    }

    private void uploadPhoto() {
        if (this.mType != 0) {
            if (this.mType == 1) {
                if (TextUtils.isEmpty(this.mThirdHeadUrl)) {
                    showThirdTypeToast((int) R.string.info_incomplete_tip);
                    return;
                }
                FileBinaryResource resource = (FileBinaryResource) Fresco.getImagePipelineFactory().getMainDiskStorageCache().getResource(new SimpleCacheKey(this.mThirdHeadUrl));
                if (resource != null) {
                    this.mCurrentPhotoFile = resource.getFile();
                }
            }
            if (this.mCurrentPhotoFile != null) {
                showProgress("", "保存信息，请稍候...");
                new UploadManager().put(this.mCurrentPhotoFile, null, this.mImgToken, new UpCompletionHandler() {
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        if (info.statusCode == 200) {
                            try {
                                Map<String, Object> map = new HashMap();
                                map.put("hash", response.getString("hash"));
                                map.put("url", response.getString("key"));
                                map.put("mime", response.getString("mimeType"));
                                map.put("size", response.getString("fsize"));
                                map.put("width", response.getString("w"));
                                map.put("height", response.getString("h"));
                                Map<String, Object> map2 = new HashMap();
                                map2.put("logo", map);
                                GuideActivity.this.saveData(map2);
                                return;
                            } catch (JSONException e) {
                                e.printStackTrace();
                                GuideActivity.this.hideProgress();
                                return;
                            }
                        }
                        GuideActivity.this.hideProgress();
                        GuideActivity.this.showSecondTypeToast((int) R.string.edit_head_image_faile);
                    }
                }, null);
            } else if (this.mType != 2 || this.mHasSelectHead) {
                showSecondTypeToast((int) R.string.get_image_path_failed);
            } else {
                showThirdTypeToast((int) R.string.info_incomplete_tip);
            }
        }
    }

    public void onBackPressed() {
    }

    private void takePhoto() {
        try {
            FengUtil.creatImageCache();
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            this.mFile = new File(FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
            if (VERSION.SDK_INT < 24) {
                this.mImageCaptureUri = Uri.fromFile(this.mFile);
                intent.putExtra("output", this.mImageCaptureUri);
            } else {
                ContentValues contentValues = new ContentValues(1);
                contentValues.put("_data", this.mFile.getAbsolutePath());
                intent.putExtra("output", getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues));
            }
            startActivityForResult(intent, 11);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void takeGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.GET_CONTENT");
        startActivityForResult(Intent.createChooser(intent, "选择图片文件"), 33);
        FengUtil.creatImageCache();
        this.mFile = new File(FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
    }

    private void doCrop() {
        String path = getFilePath(this.mImageCaptureUri);
        if (StringUtil.isEmpty(path)) {
            showSecondTypeToast((int) R.string.get_image_path_failed);
            return;
        }
        Intent intent = new Intent(this, CropActivity.class);
        intent.putExtra("IMAGE_PATH_KEY", path);
        startActivityForResult(intent, 22);
    }

    private void initQiNiuToken() {
        FengApplication.getInstance().httpRequest("home/qiniutoken/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
                GuideActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                GuideActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        GuideActivity.this.mImgToken = jsonObject.getJSONObject("body").getJSONObject("token").getString("image");
                        GuideActivity.this.mIsQiNiuInit = true;
                        GuideActivity.this.uploadPhoto();
                    } else if (code == 0 || code == -1) {
                        GuideActivity.this.showSecondTypeToast((int) R.string.init_upload_fail);
                    } else if (code == -5) {
                        GuideActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                    } else {
                        FengApplication.getInstance().upLoadLog(true, "home/qiniutoken/    " + code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    GuideActivity.this.showSecondTypeToast((int) R.string.init_upload_fail);
                    FengApplication.getInstance().upLoadTryCatchLog("home/qiniutoken/", content, e);
                }
            }
        });
    }

    private String getFilePath(Uri mUri) {
        try {
            if (mUri.getScheme().equals(UriUtil.LOCAL_FILE_SCHEME)) {
                return mUri.getPath();
            }
            return getFilePathByUri(mUri);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e2) {
            return null;
        }
    }

    private String getFilePathByUri(Uri mUri) throws FileNotFoundException {
        Cursor cursor = getContentResolver().query(mUri, null, null, null, null);
        cursor.moveToFirst();
        String s = cursor.getString(1);
        cursor.close();
        return s;
    }
}
