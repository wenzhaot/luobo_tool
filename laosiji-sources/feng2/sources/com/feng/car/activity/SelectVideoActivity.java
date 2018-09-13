package com.feng.car.activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore.Video.Media;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.feng.car.R;
import com.feng.car.adapter.CommonBottomDialogAdapter.OnDialogItemClickListener;
import com.feng.car.adapter.SelectVideoAdapter;
import com.feng.car.adapter.VideoAlbumAdapter;
import com.feng.car.databinding.ActivitySelectPhotoBinding;
import com.feng.car.databinding.PhotoVideoFolderBinding;
import com.feng.car.databinding.SelectMiddleTitleBinding;
import com.feng.car.entity.DialogItemEntity;
import com.feng.car.entity.model.ImageVideoBucketList;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.event.ImageOrVideoPathEvent;
import com.feng.car.event.MediaSelectChangeEvent;
import com.feng.car.event.TitleNextChangeEvent;
import com.feng.car.utils.ActivityManager;
import com.feng.car.utils.FengConstant;
import com.feng.car.utils.LocalMediaDataUtil;
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

public class SelectVideoActivity extends BaseActivity<ActivitySelectPhotoBinding> implements SwipeBackLayout$BackLayoutFinishCallBack, LoaderCallbacks<Cursor> {
    public static final int SEL_VIDEO_MORE_TYPE = 0;
    public static final int SEL_VIDEO_ONE_TYPE = 1;
    private int RECORD_VIDEO = 1000;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int size = SelectVideoActivity.this.mAllDateList.size();
                    if (size > SelectVideoActivity.this.mSelectGroupID) {
                        SelectVideoActivity.this.mList.addAll(SelectVideoActivity.this.mAllDateList.get(SelectVideoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    }
                    if (size > 0) {
                        SelectVideoActivity.this.mMiddleTitleBinding.llParent.setVisibility(0);
                        SelectVideoActivity.this.mMiddleTitleBinding.tvTitle.setText(SelectVideoActivity.this.mAllDateList.get(SelectVideoActivity.this.mSelectGroupID).bucketName);
                    } else {
                        SelectVideoActivity.this.mMiddleTitleBinding.llParent.setVisibility(8);
                        SelectVideoActivity.this.mMiddleTitleBinding.tvTitle.setVisibility(0);
                    }
                    SelectVideoActivity.this.initPupWindow();
                    SelectVideoActivity.this.mList.add(0, null);
                    ((ActivitySelectPhotoBinding) SelectVideoActivity.this.mBaseBinding).reSelectPhoto.setLayoutManager(new GridLayoutManager(SelectVideoActivity.this, 4));
                    SelectVideoActivity.this.mAdapter = new SelectVideoAdapter(SelectVideoActivity.this, SelectVideoActivity.this.mList, SelectVideoActivity.this.handler, SelectVideoActivity.this.mType, SelectVideoActivity.this.mAllDateList);
                    ((ActivitySelectPhotoBinding) SelectVideoActivity.this.mBaseBinding).reSelectPhoto.setAdapter(SelectVideoActivity.this.mAdapter);
                    return;
                case 1:
                    SelectVideoActivity.this.mSelectGroupID = msg.arg1;
                    SelectVideoActivity.this.mMiddleTitleBinding.tvTitle.setText(SelectVideoActivity.this.mAllDateList.get(SelectVideoActivity.this.mSelectGroupID).bucketName);
                    SelectVideoActivity.this.mVideoAlbumAdapter.setSelectPost(SelectVideoActivity.this.mSelectGroupID);
                    SelectVideoActivity.this.hideWindow();
                    SelectVideoActivity.this.mList.clear();
                    SelectVideoActivity.this.mList.addAll(SelectVideoActivity.this.mAllDateList.get(SelectVideoActivity.this.mSelectGroupID).list.getImageVideoInfoList());
                    SelectVideoActivity.this.mList.add(0, null);
                    SelectVideoActivity.this.mAdapter.notifyDataSetChanged();
                    return;
                case 2:
                    if (FengConstant.UPLOAD_VIDEO_COUNT >= 1) {
                        SelectVideoActivity.this.showThirdTypeToast((int) R.string.max_video_number);
                        return;
                    } else if (!Environment.getExternalStorageState().equals("mounted")) {
                        SelectVideoActivity.this.showSecondTypeToast((int) R.string.not_find_storage_video_not_recording);
                        return;
                    } else if (FileUtil.showFileAvailable() < 200.0d) {
                        SelectVideoActivity.this.showSecondTypeToast((int) R.string.sdcard_not_enough);
                        return;
                    } else {
                        SelectVideoActivity.this.checkPermissionCamera();
                        return;
                    }
                case 3:
                    SelectVideoActivity.this.mRootBinding.titleLine.tvRightText.setSelected(false);
                    EventBus.getDefault().post(new TitleNextChangeEvent(false));
                    return;
                case 4:
                    SelectVideoActivity.this.mRootBinding.titleLine.tvRightText.setSelected(true);
                    EventBus.getDefault().post(new TitleNextChangeEvent(true));
                    return;
                case 5:
                    EventBus.getDefault().post(new ImageOrVideoPathEvent(3, SelectVideoActivity.this.mAdapter.mSelectVideo));
                    return;
                default:
                    return;
            }
        }
    };
    private SelectVideoAdapter mAdapter;
    private ImageVideoBucketList mAllDateList = new ImageVideoBucketList();
    private List<ImageVideoInfo> mList = new ArrayList();
    private SelectMiddleTitleBinding mMiddleTitleBinding;
    private PhotoVideoFolderBinding mPhotoVideoFolderBinding;
    private PopupWindow mPopupWindow;
    private int mSelectGroupID = 0;
    private int mType = 0;
    private VideoAlbumAdapter mVideoAlbumAdapter;

    static {
        StubApp.interface11(2932);
    }

    protected native void onCreate(Bundle bundle);

    public void initView() {
        Intent intent = getIntent();
        if (intent.hasExtra("feng_type")) {
            this.mType = intent.getIntExtra("feng_type", 0);
        }
        initNormalLeftTitleBar(getString(R.string.cancel), new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectVideoActivity.this.mType != 1) {
                    FengConstant.UPLOAD_VIDEO_COUNT -= SelectVideoActivity.this.mAdapter.mSelectVideo.size();
                }
                SelectVideoActivity.this.mAdapter.mSelectVideo.clear();
                SelectVideoActivity.this.finish();
            }
        });
        if (this.mType != 1) {
            initTitleBarRightTextWithBg(R.string.next, new OnSingleClickListener() {
                public void onSingleClick(View v) {
                    if (!SelectVideoActivity.this.mRootBinding.titleLine.tvRightText.isSelected()) {
                        return;
                    }
                    if (SelectVideoActivity.this.mAdapter.mSelectVideo.size() <= 0) {
                        SelectVideoActivity.this.showSecondTypeToast((int) R.string.have_not_select_video);
                        return;
                    }
                    EventBus.getDefault().post(new ImageOrVideoPathEvent(3, SelectVideoActivity.this.mAdapter.mSelectVideo));
                    SelectVideoActivity.this.finish();
                }
            });
        }
        this.mMiddleTitleBinding = SelectMiddleTitleBinding.inflate(this.mInflater);
        this.mMiddleTitleBinding.tvTitle.setText("选择相片");
        this.mMiddleTitleBinding.llParent.setOnClickListener(new OnSingleClickListener() {
            public void onSingleClick(View v) {
                if (SelectVideoActivity.this.mPopupWindow == null) {
                    SelectVideoActivity.this.showWindow(SelectVideoActivity.this.mRootBinding.titleLine.getRoot(), SelectVideoActivity.this.mPhotoVideoFolderBinding.getRoot());
                } else if (SelectVideoActivity.this.mPopupWindow.isShowing()) {
                    SelectVideoActivity.this.mPopupWindow.dismiss();
                } else {
                    SelectVideoActivity.this.showWindow(SelectVideoActivity.this.mRootBinding.titleLine.getRoot(), SelectVideoActivity.this.mPhotoVideoFolderBinding.getRoot());
                }
            }
        });
        this.mRootBinding.titleLine.titlebarMiddleParent.removeAllViews();
        this.mRootBinding.titleLine.titlebarMiddleParent.addView(this.mMiddleTitleBinding.getRoot());
        ((ActivitySelectPhotoBinding) this.mBaseBinding).reSelectPhoto.setVisibility(0);
        ((ActivitySelectPhotoBinding) this.mBaseBinding).reSelectPhoto.addOnScrollListener(new OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == 0 && Fresco.getImagePipeline().isPaused()) {
                    SelectVideoActivity.this.imageResume();
                } else if (!Fresco.getImagePipeline().isPaused()) {
                    SelectVideoActivity.this.imagePause();
                }
            }
        });
        ((ActivitySelectPhotoBinding) this.mBaseBinding).originalLine.setVisibility(8);
    }

    public void initPupWindow() {
        this.mPhotoVideoFolderBinding = PhotoVideoFolderBinding.inflate(this.mInflater);
        this.mPhotoVideoFolderBinding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        this.mVideoAlbumAdapter = new VideoAlbumAdapter(this, this.mAllDateList.getBucketList());
        this.mPhotoVideoFolderBinding.recyclerView.setAdapter(this.mVideoAlbumAdapter);
        this.mVideoAlbumAdapter.setOnItemClickLister(new OnItemClickListener() {
            public void onItemClick(View view, int position) {
                if (SelectVideoActivity.this.mSelectGroupID == position) {
                    SelectVideoActivity.this.hideWindow();
                    return;
                }
                Message message = Message.obtain();
                message.what = 1;
                message.arg1 = position;
                SelectVideoActivity.this.handler.sendMessage(message);
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
        this.mPopupWindow = new PopupWindow(view, -1, -2, true);
        this.mPopupWindow.setFocusable(true);
        this.mPopupWindow.setOutsideTouchable(true);
        this.mPopupWindow.setBackgroundDrawable(new ColorDrawable(1711276032));
        this.mPopupWindow.showAsDropDown(parent, 0, 0);
        this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_up_yellow);
        this.mPopupWindow.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                SelectVideoActivity.this.mMiddleTitleBinding.ivRankDownUp.setImageResource(R.drawable.icon_down_yellow);
                ((ActivitySelectPhotoBinding) SelectVideoActivity.this.mBaseBinding).viewShade.setVisibility(8);
            }
        });
        this.mVideoAlbumAdapter.notifyDataSetChanged();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            try {
                if (requestCode == this.RECORD_VIDEO) {
                    if (data != null) {
                        Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                        if (cursor != null && cursor.moveToNext()) {
                            String filePath = cursor.getString(cursor.getColumnIndex("_data"));
                            cursor.close();
                            if (filePath != null && filePath.length() > 0) {
                                List<UploadQiNiuLocalPathInfo> list = new ArrayList();
                                list.add(new UploadQiNiuLocalPathInfo(3, filePath));
                                if (this.mType != 1) {
                                    FengConstant.UPLOAD_VIDEO_COUNT++;
                                }
                                EventBus.getDefault().post(new ImageOrVideoPathEvent(3, list));
                            }
                        }
                    }
                    this.mAdapter.mSelectVideo.clear();
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
                finish();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 50001) {
            try {
                if (permissions[0].equals("android.permission.WRITE_EXTERNAL_STORAGE") && grantResults[0] == 0) {
                    getVideos();
                } else {
                    finish();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode != 50003) {
        } else {
            if (permissions[0].equals("android.permission.CAMERA") && grantResults[0] == 0) {
                intentRecordVideo();
            } else if (grantResults[0] == 0) {
                intentRecordVideo();
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
                    intent.setData(Uri.fromParts("package", SelectVideoActivity.this.getPackageName(), null));
                    SelectVideoActivity.this.startActivityForResult(intent, 50005);
                    return;
                }
                SelectVideoActivity.this.checkPermissionCamera();
            }
        }, new OnSingleClickListener() {
            public void onSingleClick(View v) {
            }
        }, true);
    }

    private void checkPermission() {
        if (VERSION.SDK_INT < 23 || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            getVideos();
            return;
        }
        requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 50001);
    }

    private void checkPermissionCamera() {
        if (VERSION.SDK_INT >= 23) {
            int hasCameraPermission = checkSelfPermission("android.permission.CAMERA");
            int has = checkSelfPermission("android.permission.RECORD_AUDIO");
            if (!(hasCameraPermission == 0 && has == 0)) {
                requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.RECORD_AUDIO"}, 50003);
                return;
            }
        }
        intentRecordVideo();
    }

    private void intentRecordVideo() {
        Intent intent = new Intent("android.media.action.VIDEO_CAPTURE");
        intent.putExtra("android.intent.extra.videoQuality", 1);
        intent.putExtra("android.intent.extra.sizeLimit", 1048576000);
        intent.putExtra("android.intent.extra.durationLimit", 15);
        startActivityForResult(intent, this.RECORD_VIDEO);
    }

    protected void onResume() {
        super.onResume();
        this.mSwipeBackLayout.setmCallBack(this);
    }

    public int setBaseContentView() {
        return R.layout.activity_select_photo;
    }

    public void onBackPressed() {
        if (this.mType != 1) {
            FengConstant.UPLOAD_VIDEO_COUNT -= this.mAdapter.mSelectVideo.size();
        }
        this.mAdapter.mSelectVideo.clear();
        finish();
    }

    public void callBack() {
        if (this.mType != 1) {
            FengConstant.UPLOAD_VIDEO_COUNT -= this.mAdapter.mSelectVideo.size();
        }
        this.mAdapter.mSelectVideo.clear();
    }

    private void getVideos() {
        showLaoSiJiDialog();
        getSupportLoaderManager().initLoader(0, null, this);
    }

    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] columns = new String[]{"_id", "bucket_id", "bucket_display_name", "_size", "width", "height", "duration", "_data", "mime_type", "date_modified"};
        return new CursorLoader(this, Media.EXTERNAL_CONTENT_URI, columns, null, null, "date_modified desc");
    }

    /* JADX WARNING: Removed duplicated region for block: B:10:0x00ca  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x00de  */
    /* JADX WARNING: Removed duplicated region for block: B:29:0x0192  */
    public void onLoadFinished(android.support.v4.content.Loader<android.database.Cursor> r36, android.database.Cursor r37) {
        /*
        r35 = this;
        r0 = r35;
        r7 = r0.mAllDateList;
        r7.clear();
        r21 = new java.util.HashMap;
        r21.<init>();
        r11 = new com.feng.car.entity.model.ImageVideoBucket;
        r11.<init>();
        r7 = "相机相册";
        r11.bucketName = r7;
        if (r37 == 0) goto L_0x00c8;
    L_0x0018:
        r7 = r37.moveToFirst();
        if (r7 == 0) goto L_0x00c8;
    L_0x001e:
        r7 = "_id";
        r0 = r37;
        r23 = r0.getColumnIndexOrThrow(r7);
        r7 = "bucket_id";
        r0 = r37;
        r12 = r0.getColumnIndexOrThrow(r7);
        r7 = "bucket_display_name";
        r0 = r37;
        r15 = r0.getColumnIndexOrThrow(r7);
        r7 = "_data";
        r0 = r37;
        r29 = r0.getColumnIndexOrThrow(r7);
        r7 = "_size";
        r0 = r37;
        r30 = r0.getColumnIndex(r7);
        r7 = "width";
        r0 = r37;
        r32 = r0.getColumnIndex(r7);
        r7 = "height";
        r0 = r37;
        r28 = r0.getColumnIndex(r7);
        r7 = "duration";
        r0 = r37;
        r31 = r0.getColumnIndex(r7);
        r7 = "mime_type";
        r0 = r37;
        r24 = r0.getColumnIndex(r7);
        r7 = "date_modified";
        r0 = r37;
        r18 = r0.getColumnIndex(r7);
    L_0x0078:
        r0 = r37;
        r1 = r23;
        r22 = r0.getString(r1);
        r0 = r37;
        r1 = r29;
        r5 = r0.getString(r1);
        r0 = r37;
        r1 = r30;
        r6 = r0.getString(r1);
        r0 = r37;
        r13 = r0.getString(r12);
        r0 = r37;
        r14 = r0.getString(r15);
        r0 = r37;
        r1 = r32;
        r8 = r0.getInt(r1);
        r0 = r37;
        r1 = r28;
        r9 = r0.getInt(r1);
        r0 = r37;
        r1 = r31;
        r10 = r0.getInt(r1);
        r0 = r37;
        r1 = r24;
        r25 = r0.getString(r1);
        r7 = android.text.TextUtils.isEmpty(r5);
        if (r7 == 0) goto L_0x010b;
    L_0x00c2:
        r7 = r37.moveToNext();
        if (r7 != 0) goto L_0x0078;
    L_0x00c8:
        if (r37 == 0) goto L_0x00cd;
    L_0x00ca:
        r37.close();
    L_0x00cd:
        r21.keySet();
        r7 = r21.entrySet();
        r7 = r7.iterator();
    L_0x00d8:
        r33 = r7.hasNext();
        if (r33 == 0) goto L_0x0188;
    L_0x00de:
        r20 = r7.next();
        r20 = (java.util.Map.Entry) r20;
        r26 = r20.getValue();
        r26 = (com.feng.car.entity.model.ImageVideoBucket) r26;
        r33 = "Camera";
        r0 = r26;
        r0 = r0.bucketName;
        r34 = r0;
        r33 = r33.equals(r34);
        if (r33 == 0) goto L_0x0179;
    L_0x00f9:
        r0 = r35;
        r0 = r0.mAllDateList;
        r33 = r0;
        r34 = 0;
        r0 = r33;
        r1 = r34;
        r2 = r26;
        r0.add(r1, r2);
        goto L_0x00d8;
    L_0x010b:
        r19 = new java.io.File;
        r0 = r19;
        r0.<init>(r5);
        r7 = r19.exists();
        if (r7 == 0) goto L_0x00c2;
    L_0x0118:
        r7 = android.text.TextUtils.isEmpty(r25);
        if (r7 == 0) goto L_0x0121;
    L_0x011e:
        r25 = "";
    L_0x0121:
        r0 = r37;
        r1 = r18;
        r16 = r0.getLong(r1);
        r0 = r21;
        r27 = r0.get(r13);
        r27 = (com.feng.car.entity.model.ImageVideoBucket) r27;
        if (r27 != 0) goto L_0x0147;
    L_0x0133:
        r27 = new com.feng.car.entity.model.ImageVideoBucket;
        r27.<init>();
        r0 = r27;
        r0.bucketId = r13;
        r0 = r27;
        r0.bucketName = r14;
        r0 = r21;
        r1 = r27;
        r0.put(r13, r1);
    L_0x0147:
        r0 = r27;
        r7 = r0.count;
        r7 = r7 + 1;
        r0 = r27;
        r0.count = r7;
        r4 = new com.feng.car.entity.model.ImageVideoInfo;
        r7 = 0;
        r4.<init>(r5, r6, r7, r8, r9, r10);
        r0 = r22;
        r4.id = r0;
        r4.bucketId = r13;
        r0 = r25;
        r4.mimeType = r0;
        r0 = r16;
        r4.date_modified = r0;
        r0 = r27;
        r7 = r0.list;
        r7.add(r4);
        r7 = r11.list;
        r7.add(r4);
        r7 = r11.count;
        r7 = r7 + 1;
        r11.count = r7;
        goto L_0x00c2;
    L_0x0179:
        r0 = r35;
        r0 = r0.mAllDateList;
        r33 = r0;
        r0 = r33;
        r1 = r26;
        r0.add(r1);
        goto L_0x00d8;
    L_0x0188:
        r0 = r35;
        r7 = r0.mAllDateList;
        r7 = r7.size();
        if (r7 <= 0) goto L_0x019d;
    L_0x0192:
        r0 = r35;
        r7 = r0.mAllDateList;
        r33 = 0;
        r0 = r33;
        r7.add(r0, r11);
    L_0x019d:
        r21.clear();
        r35.hideProgress();
        r0 = r35;
        r7 = r0.handler;
        r33 = 0;
        r0 = r33;
        r7.sendEmptyMessage(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.activity.SelectVideoActivity.onLoadFinished(android.support.v4.content.Loader, android.database.Cursor):void");
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
            EventBus.getDefault().post(new ImageOrVideoPathEvent(3, this.mAdapter.mSelectVideo));
            finish();
        } else if (event.nextState == 2) {
            EventBus.getDefault().post(new ImageOrVideoPathEvent(3, this.mAdapter.mSelectVideo));
            finish();
        } else {
            this.mAdapter.changeSelectEvent(event.info);
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        LocalMediaDataUtil.getInstance().release();
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
        }
    }
}
