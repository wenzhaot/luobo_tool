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
import android.os.Bundle;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityEditUserInfoBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.HomeRefreshEvent;
import com.feng.car.event.UserInfoEditEvent;
import com.feng.car.event.UserInfoRefreshEvent;
import com.feng.car.operation.OperationCallback;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.JsonUtil;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.SharedUtil;
import com.feng.library.utils.StringUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.stub.StubApp;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class EditUserInfoActivity extends BaseActivity<ActivityEditUserInfoBinding> {
    private static final int CROP_FROM_CAMERA = 22;
    public static final String EDIT_FROM = "FROM";
    public static final String EDIT_NICK = "EDIT_NICK";
    public static final int EDIT_NICK_REQUEST_CODE = 1;
    public static final String EDIT_RESULT = "RESULT";
    public static final String EDIT_SIGNATURE = "EDIT_SIGNATURE";
    public static final int EDIT_SIGNATURE_REQUEST_CODE = 2;
    private static final int PICK_FROM_CAMERA = 11;
    private static final int PICK_FROM_FILE = 33;
    private File mCurrentPhotoFile;
    private File mFile;
    private Uri mImageCaptureUri;
    private String mImgToken;
    private boolean mIsQiNiuInit = false;
    private Bitmap mPhoto = null;
    private UserInfo mUser;

    static {
        StubApp.interface11(2393);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.activity_edit_user_info;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.edit_information);
        if (FengApplication.getInstance().getUserInfo() == null) {
            startActivity(new Intent(this, LoginActivity.class));
            return;
        }
        getUserInfo();
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditHeadImg.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditNick.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditGender.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditProfiles.setOnClickListener(this);
    }

    private void getUserInfo() {
        Map<String, Object> map = new HashMap();
        map.put("userid", String.valueOf(FengApplication.getInstance().getUserInfo().id));
        FengApplication.getInstance().httpRequest("user/ywf/info/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    int code = jsonResult.getInt("code");
                    if (code == 1) {
                        JSONObject jsonUser = jsonResult.getJSONObject("body").getJSONObject("user");
                        EditUserInfoActivity.this.mUser = new UserInfo();
                        EditUserInfoActivity.this.mUser.parser(jsonUser);
                        EditUserInfoActivity.this.showUserInfo(EditUserInfoActivity.this.mUser);
                    } else if (code == -5) {
                        EditUserInfoActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                    } else {
                        FengApplication.getInstance().checkCode("user/ywf/info/", code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EditUserInfoActivity.this.showSecondTypeToast((int) R.string.getinfo_failed);
                    FengApplication.getInstance().upLoadTryCatchLog("user/ywf/info/", content, e);
                }
            }
        });
    }

    private void showUserInfo(UserInfo user) {
        FengApplication.getInstance().getUserInfo().getHeadImageInfo().url = user.getHeadImageInfo().url;
        FengApplication.getInstance().getUserInfo().getHeadImageInfo().width = user.getHeadImageInfo().width;
        FengApplication.getInstance().getUserInfo().getHeadImageInfo().height = user.getHeadImageInfo().height;
        FengApplication.getInstance().getUserInfo().getHeadImageInfo().mimetype = user.getHeadImageInfo().mimetype;
        FengApplication.getInstance().getUserInfo().signature.set(user.signature.get());
        FengApplication.getInstance().getUserInfo().name.set(user.name.get());
        FengApplication.getInstance().getUserInfo().sex.set(user.sex.get());
        FengApplication.getInstance().saveUserInfo();
        ((ActivityEditUserInfoBinding) this.mBaseBinding).afdEditHead.setHeadUrl(FengUtil.getHeadImageUrl(user.getHeadImageInfo()));
        ((ActivityEditUserInfoBinding) this.mBaseBinding).setUserInfo(user);
        if (user.sex.get() == 1) {
            ((ActivityEditUserInfoBinding) this.mBaseBinding).tvEditGender.setText("男");
        } else if (user.sex.get() == 2) {
            ((ActivityEditUserInfoBinding) this.mBaseBinding).tvEditGender.setText("女");
        }
    }

    private void selectGender() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("男", false));
        list.add(new DialogItemEntity("女", false));
        CommonDialog.showCommonDialog(this, "修改性别", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, final int which) {
                Map<String, Object> map = new HashMap();
                if (which == 0) {
                    map.put("sex", String.valueOf(1));
                } else if (which == 1) {
                    map.put("sex", String.valueOf(2));
                }
                String str = JsonUtil.toJson(map);
                map.clear();
                map.put("info", str);
                EditUserInfoActivity.this.mUser.updateInfoOperation(map, new OperationCallback() {
                    public void onNetworkError() {
                        EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }

                    public void onStart() {
                    }

                    public void onFinish() {
                    }

                    public void onFailure(int statusCode, String content, Throwable error) {
                        EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                    }

                    public void onSuccess(String content) {
                        try {
                            int code = new JSONObject(content).getInt("code");
                            if (code == 1) {
                                int gender = 1;
                                if (which == 0) {
                                    ((ActivityEditUserInfoBinding) EditUserInfoActivity.this.mBaseBinding).tvEditGender.setText("男");
                                    gender = 1;
                                } else if (which == 1) {
                                    ((ActivityEditUserInfoBinding) EditUserInfoActivity.this.mBaseBinding).tvEditGender.setText("女");
                                    gender = 2;
                                }
                                UserInfo userInfo = new UserInfo();
                                userInfo.id = EditUserInfoActivity.this.mUser.id;
                                userInfo.sex.set(gender);
                                UserInfoRefreshEvent event = new UserInfoRefreshEvent(userInfo);
                                event.type = 3;
                                EventBus.getDefault().post(event);
                                FengApplication.getInstance().getUserInfo().sex.set(gender);
                                SharedUtil.putInt(EditUserInfoActivity.this, "SEX_KEY", gender);
                            } else if (code == -5) {
                                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                            } else {
                                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.edit_failed);
                                FengApplication.getInstance().upLoadLog(true, "user/update/    " + code);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                            FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                        }
                    }
                });
            }
        });
    }

    public void onSingleClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_edit_head_img /*2131624345*/:
                showImageView();
                return;
            case R.id.rl_edit_nick /*2131624348*/:
                intent = new Intent(this, EditUserInfoNextActivity.class);
                intent.putExtra(EDIT_FROM, EDIT_NICK);
                startActivityForResult(intent, 1);
                return;
            case R.id.rl_edit_gender /*2131624351*/:
                selectGender();
                return;
            case R.id.rl_edit_profiles /*2131624354*/:
                intent = new Intent(this, EditUserInfoNextActivity.class);
                intent.putExtra(EDIT_FROM, EDIT_SIGNATURE);
                startActivityForResult(intent, 2);
                return;
            default:
                return;
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
            String str = "";
            if (data != null) {
                try {
                    if (data.hasExtra(EDIT_RESULT)) {
                        str = data.getStringExtra(EDIT_RESULT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            switch (requestCode) {
                case 1:
                    this.mUser.name.set(str);
                    ((ActivityEditUserInfoBinding) this.mBaseBinding).tvEditNick.setText(str);
                    break;
                case 2:
                    if (!str.equals("")) {
                        this.mUser.signature.set(str);
                        ((ActivityEditUserInfoBinding) this.mBaseBinding).tvSignature.setText(str);
                        break;
                    }
                    ((ActivityEditUserInfoBinding) this.mBaseBinding).tvSignature.setText("这家伙很懒，什么都没留下...");
                    break;
                case 11:
                    Intent intent = new Intent(this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", this.mFile.getAbsolutePath());
                    startActivityForResult(intent, 22);
                    break;
                case 22:
                    if (data != null) {
                        try {
                            Options options = new Options();
                            options.inPreferredConfig = Config.ARGB_8888;
                            options.inPurgeable = true;
                            options.inInputShareable = true;
                            this.mPhoto = BitmapFactory.decodeFile(data.getStringExtra("IMAGE_PATH_KEY"), options);
                            if (this.mPhoto != null) {
                                if (!this.mIsQiNiuInit) {
                                    initQiNiuToken();
                                    break;
                                } else {
                                    startUpLoadPohoto();
                                    break;
                                }
                            }
                            showSecondTypeToast((int) R.string.edit_head_image_faile);
                            break;
                        } catch (Exception e2) {
                            showSecondTypeToast((int) R.string.edit_head_image_faile);
                            break;
                        }
                    }
                    break;
                case 33:
                    if (data != null) {
                        this.mImageCaptureUri = data.getData();
                        if (VERSION.SDK_INT >= 19) {
                            this.mImageCaptureUri = Uri.parse("file://" + FengUtil.getPath(this, this.mImageCaptureUri));
                        }
                        doCrop();
                        break;
                    }
                    break;
            }
            if (this.mUser != null) {
                EventBus.getDefault().post(new UserInfoEditEvent(this.mUser));
            }
        }
    }

    private void startUpLoadPohoto() {
        this.mCurrentPhotoFile = FengUtil.saveBitmapToFile(this.mPhoto, FengConstant.IMAGE_FILE_PATH, System.currentTimeMillis() + ".jpg");
        showProgress("", "正在上传头像，请稍候...");
        uploadPhoto();
    }

    public void finish() {
        super.finish();
        if (!(this.mPhoto == null || this.mPhoto.isRecycled())) {
            this.mPhoto.recycle();
            this.mPhoto = null;
        }
        setResult(-1);
    }

    private void uploadPhoto() {
        if (this.mCurrentPhotoFile == null) {
            showSecondTypeToast((int) R.string.get_image_path_failed);
        } else {
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
                            EditUserInfoActivity.this.saveData(map2);
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    EditUserInfoActivity.this.showSecondTypeToast((int) R.string.upload_head_image_failed);
                }
            }, null);
        }
    }

    private void showImageView() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("相册", false));
        list.add(new DialogItemEntity("拍照", false));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    EditUserInfoActivity.this.checkPermission();
                } else if (position == 1) {
                    EditUserInfoActivity.this.checkPermissionCamera();
                }
            }
        });
    }

    public void onBackPressed() {
        finish();
    }

    private void takePhoto() {
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

    private void saveData(Map<String, Object> mapData) {
        Map<String, Object> map = new HashMap();
        map.put("info", mapData);
        this.mUser.updateInfoOperation(map, new OperationCallback() {
            public void onNetworkError() {
                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onStart() {
            }

            public void onFinish() {
                EditUserInfoActivity.this.hideProgress();
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
                EditUserInfoActivity.this.hideProgress();
            }

            public void onSuccess(String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject imageJson = jsonObject.getJSONObject("body").getJSONObject("image");
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
                        UserInfo userInfo = new UserInfo();
                        userInfo.id = FengApplication.getInstance().getUserInfo().id;
                        userInfo.setHeadImageInfo(image);
                        UserInfoRefreshEvent event = new UserInfoRefreshEvent(userInfo);
                        event.type = 1;
                        EventBus.getDefault().post(event);
                        EventBus.getDefault().post(new HomeRefreshEvent(4));
                        ((ActivityEditUserInfoBinding) EditUserInfoActivity.this.mBaseBinding).afdEditHead.setAutoImageURI(Uri.parse(FengApplication.getInstance().getUserInfo().getHeadImageInfo().url));
                        return;
                    }
                    FengApplication.getInstance().checkCode("user/update/", code);
                } catch (JSONException e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadTryCatchLog("user/update/", content, e);
                }
            }
        });
    }

    private void initQiNiuToken() {
        FengApplication.getInstance().httpRequest("home/qiniutoken/", new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                EditUserInfoActivity.this.showSecondTypeToast((int) R.string.net_abnormal);
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        EditUserInfoActivity.this.mImgToken = jsonObject.getJSONObject("body").getJSONObject("token").getString("image");
                        EditUserInfoActivity.this.mIsQiNiuInit = true;
                        EditUserInfoActivity.this.startUpLoadPohoto();
                    } else if (code == 0 || code == -1) {
                        EditUserInfoActivity.this.showSecondTypeToast((int) R.string.init_upload_fail);
                    } else if (code == -5) {
                        EditUserInfoActivity.this.showSecondTypeToast((int) R.string.account_is_banned);
                    } else {
                        FengApplication.getInstance().upLoadLog(true, "home/qiniutoken/    " + code);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    EditUserInfoActivity.this.showSecondTypeToast((int) R.string.init_upload_fail);
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

    public boolean getAllowShowAudioBtn() {
        return true;
    }

    public void loginSuccess() {
        super.loginSuccess();
        getUserInfo();
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditHeadImg.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditNick.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditGender.setOnClickListener(this);
        ((ActivityEditUserInfoBinding) this.mBaseBinding).rlEditProfiles.setOnClickListener(this);
    }
}
