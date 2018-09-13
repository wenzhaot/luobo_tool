package com.feng.car.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Files;
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
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.PhotoAlbumAdapter;
import com.feng.car.adapter.SelectImageVideoAdapter;
import com.feng.car.adapter.SelectImageVideoAdapter.UpperLimitClickListener;
import com.feng.car.databinding.ActivitySelectPhotoBinding;
import com.feng.car.databinding.PhotoVideoFolderBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.model.ImageVideoBucket;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ClosePageEvent;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.event.ImageSizeChangeEvent;
import com.feng.car.event.MediaSelectChangeEvent;
import com.feng.car.event.SendPostEmptyCloseEvent;
import com.feng.car.event.TitleNextChangeEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.FengUtil;
import com.feng.car.utils.LocalMediaDataUtil;
import com.feng.car.utils.PostDataManager;
import com.feng.car.view.CommonDialog;
import com.feng.car.view.SwipeBackLayout$BackLayoutFinishCallBack;
import com.feng.library.utils.FileUtil;
import com.github.jdsjlzx.interfaces.OnItemClickListener;
import com.github.jdsjlzx.listener.OnSingleClickListener;
import com.stub.StubApp;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class SelectImageVideoActivity extends BaseActivity<ActivitySelectPhotoBinding> implements SwipeBackLayout$BackLayoutFinishCallBack, LoaderCallbacks<Cursor>, UpperLimitClickListener {
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            boolean z = true;
            switch (msg.what) {
                case 0:
                    SelectImageVideoActivity.this.showThirdTypeToast("最多只能选择30张图片。");
                    return;
                case 1:
                    int size = SelectImageVideoActivity.this.mAllDateList.size();
                    if (size > SelectImageVideoActivity.this.mSelectGroupID) {
                        SelectImageVideoActivity.this.mList.addAll(SelectImageVideoActivity.this.mAllDateList.get(SelectImageVideoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    }
                    if (size > 0) {
                        SelectImageVideoActivity.this.mMiddleTitleBinding.llParent.setVisibility(0);
                        SelectImageVideoActivity.this.mMiddleTitleBinding.tvTitle.setText(SelectImageVideoActivity.this.mAllDateList.get(SelectImageVideoActivity.this.mSelectGroupID).bucketName);
                    } else {
                        SelectImageVideoActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                        SelectImageVideoActivity.this.mMiddleTitleBinding.tvTitle.setVisibility(0);
                    }
                    SelectImageVideoActivity.this.initPupWindow();
                    SelectImageVideoActivity.this.mList.add(0, null);
                    ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).reSelectPhoto.setLayoutManager(new GridLayoutManager(SelectImageVideoActivity.this, 4));
                    SelectImageVideoActivity.this.mAdapter = new SelectImageVideoAdapter(SelectImageVideoActivity.this, SelectImageVideoActivity.this.mList, SelectImageVideoActivity.this.handler, SelectImageVideoActivity.this.mAllDateList);
                    ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).reSelectPhoto.setAdapter(SelectImageVideoActivity.this.mAdapter);
                    SelectImageVideoActivity.this.mAdapter.setUpperLimitClickListener(SelectImageVideoActivity.this);
                    return;
                case 2:
                    SelectImageVideoActivity.this.mSelectGroupID = msg.arg1;
                    ImageVideoBucket bucket = SelectImageVideoActivity.this.mAllDateList.get(SelectImageVideoActivity.this.mSelectGroupID);
                    SelectImageVideoActivity.this.mMiddleTitleBinding.tvTitle.setText(bucket.bucketName);
                    SelectImageVideoActivity.this.mAlbumAdapter.setSelectPost(SelectImageVideoActivity.this.mSelectGroupID);
                    SelectImageVideoActivity.this.hideWindow();
                    SelectImageVideoActivity.this.mList.clear();
                    SelectImageVideoActivity.this.mList.addAll(SelectImageVideoActivity.this.mAllDateList.get(SelectImageVideoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    SelectImageVideoActivity.this.mList.add(0, null);
                    SelectImageVideoAdapter access$400 = SelectImageVideoActivity.this.mAdapter;
                    if (bucket.bucketId.equals("all_video")) {
                        z = false;
                    }
                    access$400.setIsPhotograph(z);
                    SelectImageVideoActivity.this.mAdapter.notifyDataSetChanged();
                    return;
                case 3:
                    SelectImageVideoActivity.this.finish();
                    return;
                case 4:
                    if (FengConstant.UPLOAD_IMAGE_COUNT >= 30 && FengConstant.UPLOAD_VIDEO_COUNT >= 1) {
                        SelectImageVideoActivity.this.showTipsAnim();
                        return;
                    } else if (Environment.getExternalStorageState().equals("mounted")) {
                        SelectImageVideoActivity.this.checkPermissionCamera(true);
                        return;
                    } else {
                        SelectImageVideoActivity.this.showSecondTypeToast((int) R.string.not_find_storage_video_not_takephoto);
                        return;
                    }
                case 5:
                    if (FengConstant.UPLOAD_VIDEO_COUNT >= 1 && FengConstant.UPLOAD_IMAGE_COUNT >= 30) {
                        SelectImageVideoActivity.this.showTipsAnim();
                        return;
                    } else if (!Environment.getExternalStorageState().equals("mounted")) {
                        SelectImageVideoActivity.this.showSecondTypeToast((int) R.string.not_find_storage_video_not_recording);
                        return;
                    } else if (FileUtil.showFileAvailable() < 200.0d) {
                        SelectImageVideoActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough);
                        return;
                    } else {
                        SelectImageVideoActivity.this.checkPermissionCamera(false);
                        return;
                    }
                case 6:
                    EventBus.getDefault().post(new TitleNextChangeEvent(false));
                    return;
                case 7:
                    EventBus.getDefault().post(new TitleNextChangeEvent(true));
                    return;
                default:
                    return;
            }
        }
    };
    private SelectImageVideoAdapter mAdapter;
    private PhotoAlbumAdapter mAlbumAdapter;
    private ImageVideoBucketList mAllDateList = new ImageVideoBucketList();
    private boolean mIsAllowSendVideo = true;
    private boolean mIsClose = false;
    private boolean mIsPhotograph = true;
    private List<ImageVideoInfo> mList = new ArrayList();
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private PhotoVideoFolderBinding mPhotoVideoFolderBinding;
    private PopupWindow mPopupWindow;
    private int mSelectGroupID = 0;

    static {
        StubApp.interface11(2910);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        this.mIsAllowSendVideo = FengApplication.getInstance().isAllowSendVideo();
        FengConstant.UPLOAD_IMAGE_SIZE = 0;
        FengConstant.UPLOAD_IMAGE_ORIGINAL = false;
        ((ActivitySelectPhotoBinding) this.mBaseBinding).originalLine.setVisibility(0);
        this.mIsClose = getIntent().getBooleanExtra("is_auto_close", false);
        this.mRootBinding.titleLine.ivTitleLeft.setVisibility(0);
        this.mRootBinding.titleLine.tvTitleLeft.setVisibility(8);
        this.mRootBinding.titleLine.ivTitleLeft.setImageResource(R.drawable.icon_close);
        this.mRootBinding.titleLine.ivTitleLeft.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SelectImageVideoActivity.this.onHandleBack();
                SelectImageVideoActivity.this.finish();
            }
        });
        initTitleBarRightTextWithBg(R.string.next, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectImageVideoActivity.this.mAdapter.mIncludeVideo) {
                    PostDataManager.getInstance().addLocalSelMedias(SelectImageVideoActivity.this.mAdapter.mListSelect);
                    SelectImageVideoActivity.this.videoHandle();
                    return;
                }
                EventBus.getDefault().post(new ImageOrVideoPathEvent(SelectImageVideoActivity.this.mAdapter.mIncludeVideo, SelectImageVideoActivity.this.mAdapter.mListSelect));
                SelectImageVideoActivity.this.finish();
            }
        });
        this.mRootBinding.titleLine.tvRightText.setSelected(true);
        this.mRootBinding.titleLine.tvRightText.setBackgroundResource(R.drawable.color_313242_aeae49_round);
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mMiddleTitleBinding.tvTitle.setText("选择相片");
        this.mMiddleTitleBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectImageVideoActivity.this.mPopupWindow == null) {
                    SelectImageVideoActivity.this.showWindow(SelectImageVideoActivity.this.mRootBinding.titleLine.getRoot(), SelectImageVideoActivity.this.mPhotoVideoFolderBinding.getRoot());
                } else if (SelectImageVideoActivity.this.mPopupWindow.isShowing()) {
                    SelectImageVideoActivity.this.mPopupWindow.dismiss();
                    ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).viewShade.setVisibility(8);
                } else {
                    SelectImageVideoActivity.this.showWindow(SelectImageVideoActivity.this.mRootBinding.titleLine.getRoot(), SelectImageVideoActivity.this.mPhotoVideoFolderBinding.getRoot());
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
                    SelectImageVideoActivity.this.imageResume();
                } else if (!Fresco.getImagePipeline().isPaused()) {
                    SelectImageVideoActivity.this.imagePause();
                }
            }
        });
        ((ActivitySelectPhotoBinding) this.mBaseBinding).originalTag.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).imageSizeText.setVisibility(0);
                    FengConstant.UPLOAD_IMAGE_ORIGINAL = true;
                    return;
                }
                ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).imageSizeText.setVisibility(8);
                FengConstant.UPLOAD_IMAGE_ORIGINAL = false;
            }
        });
        ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                Intent intent;
                if (SelectImageVideoActivity.this.mAdapter.getSelectVideoInfo() != null) {
                    LocalMediaDataUtil.getInstance().initData(0, SelectImageVideoActivity.this.mAdapter.getSelectVideoInfo(), null, null, SelectImageVideoActivity.this.mAdapter.mListSelect);
                    intent = new Intent(SelectImageVideoActivity.this, WatchVideoActivity.class);
                    intent.putExtra("type", true);
                    SelectImageVideoActivity.this.startActivity(intent);
                } else if (SelectImageVideoActivity.this.mAdapter.mListSelect.size() > 0 && ((UploadQiNiuLocalPathInfo) SelectImageVideoActivity.this.mAdapter.mListSelect.get(0)).type == 2) {
                    intent = new Intent(SelectImageVideoActivity.this, ShowNativeImageActivity.class);
                    intent.putExtra("position", 0);
                    LocalMediaDataUtil.getInstance().initData(1, null, SelectImageVideoActivity.this.mAdapter.mSelectImageList, null, SelectImageVideoActivity.this.mAdapter.mListSelect);
                    SelectImageVideoActivity.this.startActivity(intent);
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
                if (SelectImageVideoActivity.this.mSelectGroupID == position) {
                    SelectImageVideoActivity.this.hideWindow();
                    return;
                }
                Message message = Message.obtain();
                message.what = 2;
                message.arg1 = position;
                SelectImageVideoActivity.this.handler.sendMessage(message);
            }
        });
    }

    private void videoHandle() {
        for (UploadQiNiuLocalPathInfo pathInfo : this.mAdapter.mListSelect) {
            if (pathInfo.type == 3) {
                Intent intent;
                if (pathInfo.videotime > ((long) (FengConstant.MAX_VIDEO_CROP * 1000))) {
                    intent = new Intent(this, ShortVideoCropActivity.class);
                    intent.putExtra("key_video_editer_path", pathInfo.path);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(this, ShortVideoSelectImageActivity.class);
                intent.putExtra("key_video_editer_path", pathInfo.path);
                startActivity(intent);
                return;
            }
        }
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
        this.mAlbumAdapter.notifyDataSetChanged();
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(1711276032));
        this.mPopupWindow.showAsDropDown(parent, 0, 0);
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                SelectImageVideoActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                ((ActivitySelectPhotoBinding) SelectImageVideoActivity.this.mBaseBinding).viewShade.setVisibility(8);
            }
        });
    }

    public void onBackPressed() {
        onHandleBack();
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
        if (this.mAdapter.mIncludeVideo) {
            FengConstant.UPLOAD_VIDEO_COUNT = 0;
        }
        FengConstant.UPLOAD_IMAGE_COUNT -= this.mAdapter.mIncludeVideo ? this.mAdapter.mListSelect.size() - 1 : this.mAdapter.mListSelect.size();
    }

    private void onHandleBack() {
        if (this.mAdapter.mIncludeVideo) {
            FengConstant.UPLOAD_VIDEO_COUNT = 0;
        }
        FengConstant.UPLOAD_IMAGE_COUNT -= this.mAdapter.mIncludeVideo ? this.mAdapter.mListSelect.size() - 1 : this.mAdapter.mListSelect.size();
        if (this.mIsClose) {
            EventBus.getDefault().post(new SendPostEmptyCloseEvent());
        }
    }

    private void checkPermission() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            getLoadAllMidea();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
    }

    private void checkPermissionCamera(boolean isPhotograph) {
        this.mIsPhotograph = isPhotograph;
        if (VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.CAMERA") != 0) {
                requestPermissions(new String[]{"android.permission.CAMERA"}, 50003);
            } else if (isPhotograph) {
                intentTakePhoto();
            } else {
                intentRecordVideo();
            }
        } else if (!cameraIsCanUse()) {
            showSecondTypeToast((int) R.string.notopen_camera_permissions);
        } else if (isPhotograph) {
            intentTakePhoto();
        } else {
            intentRecordVideo();
        }
    }

    public boolean cameraIsCanUse() {
        boolean isCanUse = true;
        Camera mCamera = null;
        try {
            mCamera = Camera.open();
            mCamera.setParameters(mCamera.getParameters());
        } catch (Exception e) {
            isCanUse = false;
        }
        if (mCamera != null) {
            try {
                mCamera.release();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return isCanUse;
    }

    private void intentTakePhoto() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraPreviewActivity.TYPE_KEY, 1);
        startActivity(intent);
    }

    private void intentRecordVideo() {
        Intent intent = new Intent(this, CameraActivity.class);
        intent.putExtra(CameraPreviewActivity.TYPE_KEY, 0);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                    getLoadAllMidea();
                } else {
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode != 50003) {
        } else {
            if (permissions[0].equals("android.permission.CAMERA") && grantResults[0] == 0) {
                if (this.mIsPhotograph) {
                    intentTakePhoto();
                } else {
                    intentRecordVideo();
                }
            } else if (grantResults[0] == 0) {
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                    showPermissionsDialog(false);
                } else {
                    showPermissionsDialog(true);
                }
            }
        }
    }

    private void showPermissionsDialog(final boolean notInquiry) {
        List<DialogItemEntity> list = new ArrayList();
        list.add(new DialogItemEntity(getString(R.string.go_open_permissions), false));
        CommonDialog.showCommonDialog(ActivityManager.getInstance().getCurrentActivity(), getString(R.string.no_open_camear_permissions), "", getString(R.string.repulse_permissions), list, new OnDialogItemClickListener() {
            public void onItemClick(DialogItemEntity dialogItemEntity, int position) {
                if (notInquiry) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", SelectImageVideoActivity.this.getPackageName(), null));
                    SelectImageVideoActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                SelectImageVideoActivity.this.checkPermissionCamera(SelectImageVideoActivity.this.mIsPhotograph);
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
                SelectImageVideoActivity.this.finish();
            }
        }, true);
    }

    private void getLoadAllMidea() {
        showLaoSiJiDialog();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = new String[]{"_id", "bucket_id", "bucket_display_name", "_data", "_size", "mime_type", "duration"};
        String selection = "";
        if (this.mIsAllowSendVideo) {
            selection = "(media_type=1 OR media_type=3) AND _size>0";
        } else {
            selection = "(media_type=1) AND _size>0";
        }
        return new CursorLoader(this, Files.getContentUri("external"), projection, selection, null, "datetaken DESC");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00a8  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:43:0x0210  */
    public void onLoadFinished(android.support.v4.content.Loader<android.database.Cursor> r33, android.database.Cursor r34) {
        /*
        r32 = this;
        r0 = r32;
        r0 = r0.mAllDateList;
        r25 = r0;
        r25.clear();
        r15 = new java.util.HashMap;
        r15.<init>();
        r5 = new com.feng.car.entity.model.ImageVideoBucket;
        r5.<init>();
        r25 = "all_media";
        r0 = r25;
        r5.bucketId = r0;
        r25 = "相机胶卷";
        r0 = r25;
        r5.bucketName = r0;
        r6 = new com.feng.car.entity.model.ImageVideoBucket;
        r6.<init>();
        r25 = "all_video";
        r0 = r25;
        r6.bucketId = r0;
        r25 = "视频相册";
        r0 = r25;
        r6.bucketName = r0;
        if (r34 == 0) goto L_0x00a6;
    L_0x0036:
        r25 = r34.moveToFirst();
        if (r25 == 0) goto L_0x00a6;
    L_0x003c:
        r25 = "_id";
        r0 = r34;
        r1 = r25;
        r20 = r0.getColumnIndex(r1);
        r25 = "bucket_id";
        r0 = r34;
        r1 = r25;
        r8 = r0.getColumnIndex(r1);
        r25 = "bucket_display_name";
        r0 = r34;
        r1 = r25;
        r11 = r0.getColumnIndex(r1);
        r25 = "_data";
        r0 = r34;
        r1 = r25;
        r21 = r0.getColumnIndex(r1);
        r25 = "_size";
        r0 = r34;
        r1 = r25;
        r22 = r0.getColumnIndex(r1);
        r25 = "mime_type";
        r0 = r34;
        r1 = r25;
        r17 = r0.getColumnIndex(r1);
    L_0x007e:
        r0 = r34;
        r1 = r20;
        r4 = r0.getString(r1);
        r0 = r34;
        r9 = r0.getString(r8);
        r0 = r34;
        r10 = r0.getString(r11);
        r0 = r34;
        r1 = r21;
        r19 = r0.getString(r1);
        r25 = android.text.TextUtils.isEmpty(r19);
        if (r25 == 0) goto L_0x00e9;
    L_0x00a0:
        r25 = r34.moveToNext();
        if (r25 != 0) goto L_0x007e;
    L_0x00a6:
        if (r34 == 0) goto L_0x00ab;
    L_0x00a8:
        r34.close();
    L_0x00ab:
        r15.keySet();
        r25 = r15.entrySet();
        r25 = r25.iterator();
    L_0x00b6:
        r28 = r25.hasNext();
        if (r28 == 0) goto L_0x0204;
    L_0x00bc:
        r14 = r25.next();
        r14 = (java.util.Map.Entry) r14;
        r24 = r14.getValue();
        r24 = (com.feng.car.entity.model.ImageVideoBucket) r24;
        r28 = "Camera";
        r0 = r24;
        r0 = r0.bucketName;
        r29 = r0;
        r28 = r28.equals(r29);
        if (r28 == 0) goto L_0x01f5;
    L_0x00d7:
        r0 = r32;
        r0 = r0.mAllDateList;
        r28 = r0;
        r29 = 0;
        r0 = r28;
        r1 = r29;
        r2 = r24;
        r0.add(r1, r2);
        goto L_0x00b6;
    L_0x00e9:
        r12 = new java.io.File;
        r0 = r19;
        r12.<init>(r0);
        r25 = r12.exists();
        if (r25 == 0) goto L_0x00a0;
    L_0x00f6:
        r0 = r34;
        r1 = r22;
        r23 = r0.getString(r1);
        r0 = r34;
        r1 = r17;
        r18 = r0.getString(r1);
        r25 = android.text.TextUtils.isEmpty(r18);
        if (r25 == 0) goto L_0x010f;
    L_0x010c:
        r18 = "";
    L_0x010f:
        r7 = r15.get(r9);
        r7 = (com.feng.car.entity.model.ImageVideoBucket) r7;
        if (r7 != 0) goto L_0x0123;
    L_0x0117:
        r7 = new com.feng.car.entity.model.ImageVideoBucket;
        r7.<init>();
        r7.bucketName = r10;
        r7.bucketId = r9;
        r15.put(r9, r7);
    L_0x0123:
        r16 = new com.feng.car.entity.model.ImageVideoInfo;
        r16.<init>();
        r25 = android.text.TextUtils.isEmpty(r4);
        if (r25 == 0) goto L_0x014e;
    L_0x012e:
        r25 = new java.lang.StringBuilder;
        r25.<init>();
        r0 = r5.count;
        r28 = r0;
        r0 = r25;
        r1 = r28;
        r25 = r0.append(r1);
        r28 = "";
        r0 = r25;
        r1 = r28;
        r25 = r0.append(r1);
        r4 = r25.toString();
    L_0x014e:
        r0 = r16;
        r0.id = r4;
        r0 = r16;
        r0.bucketId = r9;
        r0 = r19;
        r1 = r16;
        r1.url = r0;
        if (r23 == 0) goto L_0x01e3;
    L_0x015e:
        r0 = r23;
        r1 = r16;
        r1.size = r0;
        r0 = r18;
        r1 = r16;
        r1.mimeType = r0;
        r0 = r7.list;
        r25 = r0;
        r0 = r25;
        r1 = r16;
        r0.add(r1);
        r0 = r5.list;
        r25 = r0;
        r0 = r25;
        r1 = r16;
        r0.add(r1);
        r0 = r7.count;
        r25 = r0;
        r25 = r25 + 1;
        r0 = r25;
        r7.count = r0;
        r0 = r5.count;
        r25 = r0;
        r25 = r25 + 1;
        r0 = r25;
        r5.count = r0;
        r0 = r16;
        r0 = r0.mimeType;
        r25 = r0;
        r28 = "video";
        r0 = r25;
        r1 = r28;
        r25 = r0.indexOf(r1);
        if (r25 != 0) goto L_0x00a0;
    L_0x01a7:
        r25 = "duration";
        r0 = r34;
        r1 = r25;
        r25 = r0.getColumnIndex(r1);	 Catch:{ Exception -> 0x01e8 }
        r0 = r34;
        r1 = r25;
        r26 = r0.getLong(r1);	 Catch:{ Exception -> 0x01e8 }
        r0 = r26;
        r2 = r16;
        r2.time = r0;	 Catch:{ Exception -> 0x01e8 }
    L_0x01c0:
        r0 = r16;
        r0 = r0.time;
        r28 = r0;
        r30 = 0;
        r25 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1));
        if (r25 == 0) goto L_0x00a0;
    L_0x01cc:
        r0 = r6.list;
        r25 = r0;
        r0 = r25;
        r1 = r16;
        r0.add(r1);
        r0 = r6.count;
        r25 = r0;
        r25 = r25 + 1;
        r0 = r25;
        r6.count = r0;
        goto L_0x00a0;
    L_0x01e3:
        r23 = "0";
        goto L_0x015e;
    L_0x01e8:
        r13 = move-exception;
        r13.printStackTrace();
        r28 = 0;
        r0 = r28;
        r2 = r16;
        r2.time = r0;
        goto L_0x01c0;
    L_0x01f5:
        r0 = r32;
        r0 = r0.mAllDateList;
        r28 = r0;
        r0 = r28;
        r1 = r24;
        r0.add(r1);
        goto L_0x00b6;
    L_0x0204:
        r0 = r32;
        r0 = r0.mAllDateList;
        r25 = r0;
        r25 = r25.size();
        if (r25 <= 0) goto L_0x0238;
    L_0x0210:
        r0 = r6.list;
        r25 = r0;
        r25 = r25.size();
        if (r25 <= 0) goto L_0x0229;
    L_0x021a:
        r0 = r32;
        r0 = r0.mAllDateList;
        r25 = r0;
        r28 = 0;
        r0 = r25;
        r1 = r28;
        r0.add(r1, r6);
    L_0x0229:
        r0 = r32;
        r0 = r0.mAllDateList;
        r25 = r0;
        r28 = 0;
        r0 = r25;
        r1 = r28;
        r0.add(r1, r5);
    L_0x0238:
        r15.clear();
        r32.hideProgress();
        r0 = r32;
        r0 = r0.handler;
        r25 = r0;
        r28 = 1;
        r0 = r25;
        r1 = r28;
        r0.sendEmptyMessage(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.activity.SelectImageVideoActivity.onLoadFinished(android.support.v4.content.Loader, android.database.Cursor):void");
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ClosePageEvent event) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(MediaSelectChangeEvent event) {
        if (this.mAdapter == null) {
            return;
        }
        if (event.nextState == 0) {
            this.mAdapter.changeSelectEvent(event.info);
        } else if (event.nextState == 1) {
            this.mAdapter.changeSelectEvent(event.info);
            this.mRootBinding.titleLine.tvRightText.performClick();
        } else if (event.nextState == 2) {
            this.mRootBinding.titleLine.tvRightText.performClick();
        }
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
        if (this.mAdapter.mListSelect.size() > 0 || this.mAdapter.getSelectVideoInfo() != null) {
            ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setTextColor(this.mResources.getColor(R.color.color_87_000000));
        } else {
            ((ActivitySelectPhotoBinding) this.mBaseBinding).previewText.setTextColor(this.mResources.getColor(R.color.color_38_000000));
        }
    }

    public void onUpperLimitClick() {
        try {
            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast((int) R.string.select_imagevideo_tips);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showTipsAnim() {
        try {
            ActivityManager.getInstance().getCurrentActivity().showThirdTypeToast((int) R.string.select_imagevideo_tips);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
