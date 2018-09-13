package com.feng.car.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.PhotoAlbumAdapter;
import com.feng.car.adapter.SelectPhotoAdapter;
import com.feng.car.databinding.ActivitySelectPhotoBinding;
import com.feng.car.databinding.PhotoVideoFolderBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.event.ImageSizeChangeEvent;
import com.feng.car.event.MediaSelectChangeEvent;
import com.feng.car.event.TitleNextChangeEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SwipeBackLayout$BackLayoutFinishCallBack;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SelectPhotoActivity extends BaseActivity<ActivitySelectPhotoBinding> implements SwipeBackLayout$BackLayoutFinishCallBack, LoaderCallbacks<Cursor> {
    public static final int SEL_PHOTO_MORE_TYPE = 0;
    public static final int SEL_PHOTO_ONE_CROP_DEALERS_ID_CARD_TYPE = 4;
    public static final int SEL_PHOTO_ONE_CROP_DEALERS_TYPE = 3;
    public static final int SEL_PHOTO_ONE_CROP_TYPE = 2;
    public static final int SEL_PHOTO_ONE_TYPE = 1;
    private final int PICK_FROM_CAMERA = 111;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            String strPath;
            Intent intent;
            switch (msg.what) {
                case 0:
                    SelectPhotoActivity.this.showThirdTypeToast("最多只能选择30张图片。");
                    return;
                case 1:
                    int size = SelectPhotoActivity.this.mAllDateList.size();
                    if (size > SelectPhotoActivity.this.mSelectGroupID) {
                        SelectPhotoActivity.this.mList.addAll(SelectPhotoActivity.this.mAllDateList.get(SelectPhotoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    }
                    if (size > 0) {
                        SelectPhotoActivity.this.mMiddleTitleBinding.llParent.setVisibility(0);
                        SelectPhotoActivity.this.mMiddleTitleBinding.tvTitle.setText(SelectPhotoActivity.this.mAllDateList.get(SelectPhotoActivity.this.mSelectGroupID).bucketName);
                    } else {
                        SelectPhotoActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                        SelectPhotoActivity.this.mMiddleTitleBinding.tvTitle.setVisibility(0);
                    }
                    SelectPhotoActivity.this.initPupWindow();
                    SelectPhotoActivity.this.mList.add(0, null);
                    ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).reSelectPhoto.setLayoutManager(new GridLayoutManager(SelectPhotoActivity.this, 4));
                    SelectPhotoActivity.this.mAdapter = new SelectPhotoAdapter(SelectPhotoActivity.this, SelectPhotoActivity.this.mList, SelectPhotoActivity.this.handler, SelectPhotoActivity.this.mType, SelectPhotoActivity.this.mAllDateList);
                    ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).reSelectPhoto.setAdapter(SelectPhotoActivity.this.mAdapter);
                    SelectPhotoActivity.this.hideProgress();
                    return;
                case 2:
                    SelectPhotoActivity.this.mSelectGroupID = msg.arg1;
                    SelectPhotoActivity.this.mMiddleTitleBinding.tvTitle.setText(SelectPhotoActivity.this.mAllDateList.get(SelectPhotoActivity.this.mSelectGroupID).bucketName);
                    SelectPhotoActivity.this.mAlbumAdapter.setSelectPost(SelectPhotoActivity.this.mSelectGroupID);
                    SelectPhotoActivity.this.hideWindow();
                    SelectPhotoActivity.this.mList.clear();
                    SelectPhotoActivity.this.mList.addAll(SelectPhotoActivity.this.mAllDateList.get(SelectPhotoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    SelectPhotoActivity.this.mList.add(0, null);
                    SelectPhotoActivity.this.mAdapter.notifyDataSetChanged();
                    return;
                case 3:
                    SelectPhotoActivity.this.finish();
                    return;
                case 4:
                    if (FengConstant.UPLOAD_IMAGE_COUNT >= 30) {
                        SelectPhotoActivity.this.showThirdTypeToast("帖子最多只能添加30张图片");
                        return;
                    } else if (Environment.getExternalStorageState().equals("mounted")) {
                        SelectPhotoActivity.this.checkPermissionCamera();
                        return;
                    } else {
                        SelectPhotoActivity.this.showSecondTypeToast((int) R.string.not_find_storage_video_not_takephoto);
                        return;
                    }
                case 5:
                    strPath = msg.obj.toString();
                    ImageVideoInfo imageItem = new ImageVideoInfo();
                    imageItem.url = strPath;
                    imageItem.mimeType = "image/jpeg";
                    FengUtil.scaleBitmap(imageItem);
                    intent = new Intent();
                    intent.putExtra("imagepath", imageItem.url);
                    SelectPhotoActivity.this.setResult(20, intent);
                    SelectPhotoActivity.this.finish();
                    return;
                case 6:
                    SelectPhotoActivity.this.mRootBinding.titleLine.tvRightText.setSelected(false);
                    EventBus.getDefault().post(new TitleNextChangeEvent(false));
                    return;
                case 7:
                    SelectPhotoActivity.this.mRootBinding.titleLine.tvRightText.setSelected(true);
                    EventBus.getDefault().post(new TitleNextChangeEvent(true));
                    return;
                case 8:
                    strPath = msg.obj.toString();
                    intent = new Intent(SelectPhotoActivity.this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", strPath);
                    if (SelectPhotoActivity.this.mType == 3) {
                        intent.putExtra(CropActivity.TYPE_KEY, 3);
                    } else if (SelectPhotoActivity.this.mType == 4) {
                        intent.putExtra(CropActivity.TYPE_KEY, 4);
                    } else {
                        intent.putExtra(CropActivity.TYPE_KEY, 1);
                    }
                    SelectPhotoActivity.this.startActivity(intent);
                    return;
                default:
                    return;
            }
        }
    };
    private SelectPhotoAdapter mAdapter;
    private PhotoAlbumAdapter mAlbumAdapter;
    private ImageVideoBucketList mAllDateList = new ImageVideoBucketList();
    private List<ImageVideoInfo> mList = new ArrayList();
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private File mPhotoFile;
    private PhotoVideoFolderBinding mPhotoVideoFolderBinding;
    private PopupWindow mPopupWindow;
    private int mSelectGroupID = 0;
    private int mType = 0;

    static {
        StubApp.interface11(2922);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        FengConstant.UPLOAD_IMAGE_SIZE = 0;
        FengConstant.UPLOAD_IMAGE_ORIGINAL = false;
        Intent intent = getIntent();
        if (intent.getBooleanExtra("type", false)) {
            ((ActivitySelectPhotoBinding) this.mBaseBinding).originalLine.setVisibility(0);
        }
        if (intent.hasExtra("feng_type")) {
            this.mType = intent.getIntExtra("feng_type", 0);
        }
        initNormalLeftTitleBar(getString(R.string.cancel), new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectPhotoActivity.this.mType == 0) {
                    FengConstant.UPLOAD_IMAGE_COUNT -= SelectPhotoActivity.this.mAdapter.mListSelect.size();
                }
                SelectPhotoActivity.this.finish();
            }
        });
        if (this.mType == 0) {
            initTitleBarRightTextWithBg(R.string.next, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (!SelectPhotoActivity.this.mRootBinding.titleLine.tvRightText.isSelected()) {
                        return;
                    }
                    if (SelectPhotoActivity.this.mAdapter.mListSelect.size() <= 0) {
                        SelectPhotoActivity.this.showSecondTypeToast((int) R.string.have_not_select_image);
                        return;
                    }
                    EventBus.getDefault().post(new ImageOrVideoPathEvent(2, SelectPhotoActivity.this.mAdapter.mListSelect));
                    SelectPhotoActivity.this.finish();
                }
            });
        }
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mMiddleTitleBinding.tvTitle.setText("选择相片");
        this.mMiddleTitleBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectPhotoActivity.this.mPopupWindow == null) {
                    SelectPhotoActivity.this.showWindow(SelectPhotoActivity.this.mRootBinding.titleLine.getRoot(), SelectPhotoActivity.this.mPhotoVideoFolderBinding.getRoot());
                } else if (SelectPhotoActivity.this.mPopupWindow.isShowing()) {
                    SelectPhotoActivity.this.mPopupWindow.dismiss();
                    ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).viewShade.setVisibility(8);
                } else {
                    SelectPhotoActivity.this.showWindow(SelectPhotoActivity.this.mRootBinding.titleLine.getRoot(), SelectPhotoActivity.this.mPhotoVideoFolderBinding.getRoot());
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        ((ActivitySelectPhotoBinding) this.mBaseBinding).reSelectPhoto.setVisibility(0);
        checkPermission();
        ((ActivitySelectPhotoBinding) this.mBaseBinding).reSelectPhoto.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0 && Fresco.getImagePipeline().isPaused()) {
                    SelectPhotoActivity.this.imageResume();
                } else if (!Fresco.getImagePipeline().isPaused()) {
                    SelectPhotoActivity.this.imagePause();
                }
            }
        });
        ((ActivitySelectPhotoBinding) this.mBaseBinding).originalTag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).imageSizeText.setVisibility(0);
                    FengConstant.UPLOAD_IMAGE_ORIGINAL = true;
                    return;
                }
                ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).imageSizeText.setVisibility(8);
                FengConstant.UPLOAD_IMAGE_ORIGINAL = false;
            }
        });
        ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectPhotoActivity.this.mAdapter.mListSelect.size() > 0 && ((UploadQiNiuLocalPathInfo) SelectPhotoActivity.this.mAdapter.mListSelect.get(0)).type == 2) {
                    Intent intent = new Intent(SelectPhotoActivity.this, ShowNativeImageActivity.class);
                    intent.putExtra("position", 0);
                    LocalMediaDataUtil.getInstance().initData(1, null, SelectPhotoActivity.this.mAdapter.mSelectImageList, null, SelectPhotoActivity.this.mAdapter.mListSelect);
                    SelectPhotoActivity.this.startActivity(intent);
                }
            }
        });
    }

    public void initPupWindow() {
        this.mPhotoVideoFolderBinding = PhotoVideoFolderBinding.inflate(this.mInflater);
        this.mPhotoVideoFolderBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mAlbumAdapter = new PhotoAlbumAdapter(this, this.mAllDateList.getBucketList());
        this.mPhotoVideoFolderBinding.recyclerView.setAdapter(this.mAlbumAdapter);
        this.mAlbumAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (SelectPhotoActivity.this.mSelectGroupID == position) {
                    SelectPhotoActivity.this.hideWindow();
                    return;
                }
                Message message = Message.obtain();
                message.what = 2;
                message.arg1 = position;
                SelectPhotoActivity.this.handler.sendMessage(message);
            }
        });
    }

    private void hideWindow() {
        if (this.mPopupWindow != null) {
            this.mPopupWindow.dismiss();
            ((ActivitySelectPhotoBinding) this.mBaseBinding).viewShade.setVisibility(8);
        }
    }

    private void showWindow(View parent, View view) {
        ((ActivitySelectPhotoBinding) this.mBaseBinding).viewShade.setVisibility(0);
        if (this.mAllDateList.size() > 5) {
            this.mPopupWindow = new PopupWindow(view, -1, this.mResources.getDimensionPixelSize(R.dimen.default_600PX), true);
        } else {
            this.mPopupWindow = new PopupWindow(view, -1, -2, true);
        }
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(1711276032));
        this.mPopupWindow.showAsDropDown(parent, 0, 0);
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                SelectPhotoActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                ((ActivitySelectPhotoBinding) SelectPhotoActivity.this.mBaseBinding).viewShade.setVisibility(8);
            }
        });
        this.mAlbumAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if (resultCode != -1) {
                return;
            }
            if (this.mPhotoFile == null) {
                showSecondTypeToast((int) R.string.get_image_path_failed);
                return;
            }
            try {
                FengConstant.UPLOAD_IMAGE_COUNT++;
                Intent intent;
                if (this.mType == 0) {
                    List<UploadQiNiuLocalPathInfo> list = new ArrayList();
                    list.add(new UploadQiNiuLocalPathInfo(2, this.mPhotoFile.getAbsolutePath()));
                    EventBus.getDefault().post(new ImageOrVideoPathEvent(2, list));
                } else if (this.mType == 1) {
                    ImageVideoInfo imageItem = new ImageVideoInfo();
                    imageItem.url = this.mPhotoFile.getAbsolutePath();
                    imageItem.mimeType = "image/jpeg";
                    FengUtil.scaleBitmap(imageItem);
                    intent = new Intent();
                    intent.putExtra("imagepath", imageItem.url);
                    setResult(20, intent);
                } else if (this.mType == 2) {
                    intent = new Intent(this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", this.mPhotoFile.getAbsolutePath());
                    intent.putExtra(CropActivity.TYPE_KEY, 1);
                    startActivity(intent);
                } else if (this.mType == 3) {
                    intent = new Intent(this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", this.mPhotoFile.getAbsolutePath());
                    intent.putExtra(CropActivity.TYPE_KEY, 3);
                    startActivity(intent);
                } else if (this.mType == 4) {
                    intent = new Intent(this, CropActivity.class);
                    intent.putExtra("IMAGE_PATH_KEY", this.mPhotoFile.getAbsolutePath());
                    intent.putExtra(CropActivity.TYPE_KEY, 4);
                    startActivity(intent);
                }
                finish();
            } catch (Exception e) {
                finish();
            }
        } else if (requestCode == 10 && resultCode == -1) {
            setResult(11);
            finish();
        }
    }

    public void onBackPressed() {
        if (this.mType == 0) {
            FengConstant.UPLOAD_IMAGE_COUNT -= this.mAdapter.mListSelect.size();
        }
        finish();
    }

    protected void onResume() {
        super.onResume();
        this.mSwipeBackLayout.setmCallBack(this);
        ((ActivitySelectPhotoBinding) this.mBaseBinding).originalTag.setChecked(FengConstant.UPLOAD_IMAGE_ORIGINAL);
    }

    public int setBaseContentView() {
        return R.layout.activity_select_photo;
    }

    public void callBack() {
        if (this.mType == 0) {
            FengConstant.UPLOAD_IMAGE_COUNT -= this.mAdapter.mListSelect.size();
        }
    }

    private void checkPermission() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            getLoadImages();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
    }

    private void checkPermissionCamera() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.CAMERA") == 0) {
            takePhoto();
            return;
        }
        requestPermissions(new String[]{"android.permission.CAMERA"}, 50003);
    }

    private void takePhoto() {
        FengUtil.creatImageCache();
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.mPhotoFile = new File(FengConstant.IMAGE_FILE_PATH_TAKE_PHOTO + System.currentTimeMillis() + "");
        if (VERSION.SDK_INT < 24) {
            intent.putExtra("output", Uri.fromFile(this.mPhotoFile));
            startActivityForResult(intent, 111);
            return;
        }
        ContentValues contentValues = new ContentValues(1);
        contentValues.put("_data", this.mPhotoFile.getAbsolutePath());
        intent.putExtra("output", getContentResolver().insert(Media.EXTERNAL_CONTENT_URI, contentValues));
        startActivityForResult(intent, 111);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                    getLoadImages();
                } else {
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode != 50003) {
        } else {
            if (permissions[0].equals("android.permission.CAMERA") && grantResults[0] == 0) {
                takePhoto();
            } else if (grantResults[0] == 0) {
                takePhoto();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                showPermissionsDialog(false);
            } else {
                showPermissionsDialog(true);
            }
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", SelectPhotoActivity.this.getPackageName(), null));
                    SelectPhotoActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                SelectPhotoActivity.this.checkPermissionCamera();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        }, true);
    }

    private void getLoadImages() {
        showLaoSiJiDialog();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "_size", "mime_type", "date_modified"};
        return new CursorLoader(this, Media.EXTERNAL_CONTENT_URI, columns, null, null, "date_modified desc");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00b0  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00c4  */
    /* JADX WARNING: Removed duplicated region for block: B:35:0x01ac  */
    public void onLoadFinished(android.support.v4.content.Loader<android.database.Cursor> r30, android.database.Cursor r31) {
        /*
        r29 = this;
        r0 = r29;
        r0 = r0.mAllDateList;
        r26 = r0;
        r26.clear();
        r16 = new java.util.HashMap;
        r16.<init>();
        r5 = new com.feng.car.entity.model.ImageVideoBucket;
        r5.<init>();
        r26 = "相机相册";
        r0 = r26;
        r5.bucketName = r0;
        if (r31 == 0) goto L_0x00ae;
    L_0x001c:
        r26 = r31.moveToFirst();
        if (r26 == 0) goto L_0x00ae;
    L_0x0022:
        r26 = "_id";
        r0 = r31;
        r1 = r26;
        r21 = r0.getColumnIndexOrThrow(r1);
        r26 = "bucket_id";
        r0 = r31;
        r1 = r26;
        r7 = r0.getColumnIndexOrThrow(r1);
        r26 = "bucket_display_name";
        r0 = r31;
        r1 = r26;
        r10 = r0.getColumnIndexOrThrow(r1);
        r26 = "_data";
        r0 = r31;
        r1 = r26;
        r22 = r0.getColumnIndexOrThrow(r1);
        r26 = "_size";
        r0 = r31;
        r1 = r26;
        r23 = r0.getColumnIndexOrThrow(r1);
        r26 = "mime_type";
        r0 = r31;
        r1 = r26;
        r18 = r0.getColumnIndexOrThrow(r1);
        r26 = "date_modified";
        r0 = r31;
        r1 = r26;
        r11 = r0.getColumnIndexOrThrow(r1);
    L_0x006f:
        r0 = r31;
        r1 = r21;
        r4 = r0.getString(r1);
        r0 = r31;
        r8 = r0.getString(r7);
        r0 = r31;
        r9 = r0.getString(r10);
        r0 = r31;
        r1 = r22;
        r20 = r0.getString(r1);
        r0 = r31;
        r1 = r23;
        r24 = r0.getString(r1);
        r0 = r31;
        r1 = r18;
        r19 = r0.getString(r1);
        r14 = new java.io.File;
        r0 = r20;
        r14.<init>(r0);
        r26 = r14.exists();
        if (r26 != 0) goto L_0x00f1;
    L_0x00a8:
        r26 = r31.moveToNext();
        if (r26 != 0) goto L_0x006f;
    L_0x00ae:
        if (r31 == 0) goto L_0x00b3;
    L_0x00b0:
        r31.close();
    L_0x00b3:
        r16.keySet();
        r26 = r16.entrySet();
        r26 = r26.iterator();
    L_0x00be:
        r27 = r26.hasNext();
        if (r27 == 0) goto L_0x01a0;
    L_0x00c4:
        r15 = r26.next();
        r15 = (java.util.Map.Entry) r15;
        r25 = r15.getValue();
        r25 = (com.feng.car.entity.model.ImageVideoBucket) r25;
        r27 = "Camera";
        r0 = r25;
        r0 = r0.bucketName;
        r28 = r0;
        r27 = r27.equals(r28);
        if (r27 == 0) goto L_0x0191;
    L_0x00df:
        r0 = r29;
        r0 = r0.mAllDateList;
        r27 = r0;
        r28 = 0;
        r0 = r27;
        r1 = r28;
        r2 = r25;
        r0.add(r1, r2);
        goto L_0x00be;
    L_0x00f1:
        r26 = android.text.TextUtils.isEmpty(r19);
        if (r26 == 0) goto L_0x00fa;
    L_0x00f7:
        r19 = "";
    L_0x00fa:
        r26 = android.text.TextUtils.isEmpty(r20);
        if (r26 != 0) goto L_0x00a8;
    L_0x0100:
        r0 = r31;
        r12 = r0.getLong(r11);
        r0 = r16;
        r6 = r0.get(r8);
        r6 = (com.feng.car.entity.model.ImageVideoBucket) r6;
        if (r6 != 0) goto L_0x011e;
    L_0x0110:
        r6 = new com.feng.car.entity.model.ImageVideoBucket;
        r6.<init>();
        r6.bucketName = r9;
        r6.bucketId = r8;
        r0 = r16;
        r0.put(r8, r6);
    L_0x011e:
        r0 = r6.count;
        r26 = r0;
        r26 = r26 + 1;
        r0 = r26;
        r6.count = r0;
        r17 = new com.feng.car.entity.model.ImageVideoInfo;
        r17.<init>();
        r26 = android.text.TextUtils.isEmpty(r4);
        if (r26 == 0) goto L_0x014b;
    L_0x0133:
        r26 = new java.lang.StringBuilder;
        r26.<init>();
        r0 = r5.count;
        r27 = r0;
        r26 = r26.append(r27);
        r27 = "";
        r26 = r26.append(r27);
        r4 = r26.toString();
    L_0x014b:
        r0 = r17;
        r0.id = r4;
        r0 = r17;
        r0.bucketId = r8;
        r0 = r20;
        r1 = r17;
        r1.url = r0;
        if (r24 == 0) goto L_0x018d;
    L_0x015b:
        r0 = r24;
        r1 = r17;
        r1.size = r0;
        r0 = r19;
        r1 = r17;
        r1.mimeType = r0;
        r0 = r17;
        r0.date_modified = r12;
        r0 = r6.list;
        r26 = r0;
        r0 = r26;
        r1 = r17;
        r0.add(r1);
        r0 = r5.list;
        r26 = r0;
        r0 = r26;
        r1 = r17;
        r0.add(r1);
        r0 = r5.count;
        r26 = r0;
        r26 = r26 + 1;
        r0 = r26;
        r5.count = r0;
        goto L_0x00a8;
    L_0x018d:
        r24 = "0";
        goto L_0x015b;
    L_0x0191:
        r0 = r29;
        r0 = r0.mAllDateList;
        r27 = r0;
        r0 = r27;
        r1 = r25;
        r0.add(r1);
        goto L_0x00be;
    L_0x01a0:
        r0 = r29;
        r0 = r0.mAllDateList;
        r26 = r0;
        r26 = r26.size();
        if (r26 <= 0) goto L_0x01bb;
    L_0x01ac:
        r0 = r29;
        r0 = r0.mAllDateList;
        r26 = r0;
        r27 = 0;
        r0 = r26;
        r1 = r27;
        r0.add(r1, r5);
    L_0x01bb:
        r16.clear();
        r0 = r29;
        r0 = r0.handler;
        r26 = r0;
        r27 = 1;
        r26.sendEmptyMessage(r27);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.activity.SelectPhotoActivity.onLoadFinished(android.support.v4.content.Loader, android.database.Cursor):void");
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MediaSelectChangeEvent event) {
        if (this.mAdapter == null) {
            return;
        }
        if (event.nextState == 1) {
            this.mAdapter.changeSelectEvent(event.info);
            EventBus.getDefault().post(new ImageOrVideoPathEvent(2, this.mAdapter.mListSelect));
            finish();
        } else if (event.nextState == 2) {
            EventBus.getDefault().post(new ImageOrVideoPathEvent(2, this.mAdapter.mListSelect));
            finish();
        } else {
            this.mAdapter.changeSelectEvent(event.info);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }

    protected void onDestroy() {
        super.onDestroy();
        LocalMediaDataUtil.getInstance().release();
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ImageSizeChangeEvent event) {
        ((ActivitySelectPhotoBinding) this.mBaseBinding).imageSizeText.setText(FengUtil.formatImageSize(FengConstant.UPLOAD_IMAGE_SIZE));
        if (this.mAdapter.mListSelect.size() > 0) {
            ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setTextColor(this.mResources.getColor(R.color.color_87_000000));
        } else {
            ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setTextColor(this.mResources.getColor(R.color.color_38_000000));
        }
    }
}
