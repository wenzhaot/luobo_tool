package com.feng.car.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.provider.MediaStore.Images.Media;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import com.facebook.common.util.UriUtil;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.databinding.ActivityUploadPriceImageBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.ImageInfo;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.HttpConstant;
import com.feng.car.utils.JsonUtil;
import com.feng.car.utils.UploadSingleImage;
import com.feng.car.utils.UploadSingleImage$OnUploadResult;
import com.feng.car.view.CommonDialog;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class InvoiceUploadActivity extends BaseActivity<ActivityUploadPriceImageBinding> {
    private static final int PICK_FROM_CAMERA = 11;
    private static final int PICK_FROM_FILE = 33;
    private boolean hasUploadSuccess = false;
    private int m10;
    private int m32;
    private String mBrandJson;
    private String mCarxName;
    private int mCarxid;
    private File mFile;
    private ImageInfo mImage;
    private Uri mImageCaptureUri;
    private String mPath = "";
    private int mSeriesid;
    private UploadSingleImage mUploadSingle;

    public int setBaseContentView() {
        return R.layout.activity_upload_price_image;
    }

    public void initView() {
        initNormalTitleBar((int) R.string.invoice_authentication);
        initNormalLeftTitleBar(getString(R.string.cancel));
        Intent intent = getIntent();
        this.mSeriesid = intent.getIntExtra("carsid", 0);
        this.mCarxid = intent.getIntExtra("carxid", 0);
        this.mCarxName = intent.getStringExtra("carx_name");
        if (this.mSeriesid != 0) {
            getBrandInfo();
        }
        this.m10 = this.mResources.getDimensionPixelSize(R.dimen.default_10PX);
        this.m32 = this.mResources.getDimensionPixelSize(R.dimen.default_32PX);
        int width = FengUtil.getScreenWidth(this) - (this.m32 * 2);
        LayoutParams p = new LayoutParams(width, (width * 720) / 1029);
        p.setMargins(0, 0, 0, this.m10);
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).samplImage.setLayoutParams(p);
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).nextText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (InvoiceUploadActivity.this.hasUploadSuccess) {
                    Intent intent = new Intent(InvoiceUploadActivity.this, WriteBuyCarInfoActivity.class);
                    intent.putExtra("brand", InvoiceUploadActivity.this.mBrandJson);
                    intent.putExtra("carxid", InvoiceUploadActivity.this.mCarxid);
                    intent.putExtra("carx_name", InvoiceUploadActivity.this.mCarxName);
                    intent.putExtra("image", JsonUtil.toJson(InvoiceUploadActivity.this.mImage));
                    InvoiceUploadActivity.this.startActivity(intent);
                }
            }
        });
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).invoiceImage.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                InvoiceUploadActivity.this.priviewImage();
            }
        });
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).uploadText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                InvoiceUploadActivity.this.showImageView();
            }
        });
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).tipsLine.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        });
        hideDefaultTitleBar();
        ((ActivityUploadPriceImageBinding) this.mBaseBinding).confirm.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                ((ActivityUploadPriceImageBinding) InvoiceUploadActivity.this.mBaseBinding).tipsLine.setVisibility(8);
                InvoiceUploadActivity.this.showDefaultTitleBar();
            }
        });
        this.mUploadSingle = new UploadSingleImage(this, new UploadSingleImage$OnUploadResult() {
            public void onStart() {
            }

            public void onResult(HashMap<String, String> imageMap) {
                if (InvoiceUploadActivity.this.mImage == null) {
                    InvoiceUploadActivity.this.mImage = new ImageInfo();
                }
                InvoiceUploadActivity.this.mImage.hash = (String) imageMap.get("hash");
                InvoiceUploadActivity.this.mImage.setMimetype((String) imageMap.get("mime"));
                InvoiceUploadActivity.this.mImage.width = Integer.parseInt((String) imageMap.get("width"));
                InvoiceUploadActivity.this.mImage.height = Integer.parseInt((String) imageMap.get("height"));
                InvoiceUploadActivity.this.mImage.size = (String) imageMap.get("size");
                InvoiceUploadActivity.this.mImage.url = HttpConstant.QINIUIMAGEBASEPATH + ((String) imageMap.get("hash"));
                ((ActivityUploadPriceImageBinding) InvoiceUploadActivity.this.mBaseBinding).invoiceImage.setAutoImageURI(Uri.parse(FengUtil.getSingleNineScaleUrl(InvoiceUploadActivity.this.mImage, 640)));
                InvoiceUploadActivity.this.hasUploadSuccess = true;
                ((ActivityUploadPriceImageBinding) InvoiceUploadActivity.this.mBaseBinding).uploadText.setText(R.string.reupload);
                ((ActivityUploadPriceImageBinding) InvoiceUploadActivity.this.mBaseBinding).nextText.setBackgroundResource(R.drawable.color_selector_404040_pressed_191919_4dp);
                ((ActivityUploadPriceImageBinding) InvoiceUploadActivity.this.mBaseBinding).nextText.setTextColor(InvoiceUploadActivity.this.mResources.getColor(R.color.color_ffffff));
            }

            public void onFailure() {
            }
        });
    }

    private void showImageView() {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity("从相册取", false));
        list.add(new DialogItemEntity("直接拍照", false));
        CommonDialog.showCommonDialog(this, "", list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (position == 0) {
                    InvoiceUploadActivity.this.checkPermission();
                } else if (position == 1) {
                    InvoiceUploadActivity.this.checkPermissionCamera();
                }
            }
        });
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

    private void checkPermission() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            takeGallery();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 11:
                    this.mPath = this.mFile.getAbsolutePath();
                    startUpLoadPohoto(this.mPath);
                    return;
                case 33:
                    if (data != null) {
                        this.mImageCaptureUri = data.getData();
                        if (VERSION.SDK_INT >= 19) {
                            this.mPath = FengUtil.getPath(this, this.mImageCaptureUri);
                            this.mImageCaptureUri = Uri.parse("file://" + this.mPath);
                        }
                        this.mPath = getFilePath(this.mImageCaptureUri);
                        startUpLoadPohoto(this.mPath);
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
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

    private void startUpLoadPohoto(String path) {
        this.hasUploadSuccess = false;
        this.mUploadSingle.startUpload(path);
    }

    private void priviewImage() {
        if (this.hasUploadSuccess) {
            Intent intent = new Intent(this, ShowBigImageActivity.class);
            intent.putExtra("show_type", 1006);
            intent.putExtra("position", 0);
            List<ImageInfo> infos = new ArrayList();
            infos.add(this.mImage);
            intent.putExtra("mImageList", JsonUtil.toJson(infos));
            intent.putExtra("location_info_json", JsonUtil.toJson(FengUtil.getImageLocationInfo(((ActivityUploadPriceImageBinding) this.mBaseBinding).invoiceImage, 0, this.mImage.hash)));
            intent.putExtra("entrance_type", ShowBigImageActivity.ENTRANCE_TYPE_SINGLE_IMAGE);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    private void getBrandInfo() {
        Map<String, Object> map = new HashMap();
        map.put("seriesid", Integer.valueOf(this.mSeriesid));
        FengApplication.getInstance().httpRequest("car/getseriesandbrand/", map, new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonResult = new JSONObject(content);
                    if (jsonResult.getInt("code") == 1) {
                        InvoiceUploadActivity.this.mBrandJson = jsonResult.getJSONObject("body").toString();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
