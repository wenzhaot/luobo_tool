package com.feng.car.utils;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import com.feng.car.FengApplication;
import com.feng.car.db.SparkDB;
import com.feng.car.entity.model.ImageVideoInfo;
import com.feng.car.entity.model.UploadQiNiuLocalPathInfo;
import com.feng.car.entity.sendpost.PostEdit;
import com.feng.car.entity.user.UserInfo;
import com.feng.car.event.PostSortRefreshEvent;
import com.feng.car.listener.PostDescribeChangeListener;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.feng.library.utils.DateUtil;
import com.feng.library.utils.FileUtil;
import com.feng.library.utils.SharedUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.tencent.rtmp.TXLiveConstants;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

public class UploadQiNiu {
    private String mBaseQiNiuPath;
    private PostDescribeChangeListener mChangeListener;
    private long mCompleteSize = 0;
    private Context mContext;
    private PostEdit mCoverImage;
    private long mCurrentSize = 0;
    private String mDraftsId = "";
    private long mFirstTokenTime;
    private Handler mHandle;
    private String mImageToken;
    private boolean mIsBreakUpload = false;
    private boolean mIsOriginal = false;
    private double mLastPro;
    private List<PostEdit> mList;
    private List<UploadQiNiuLocalPathInfo> mListPath;
    private List<PostEdit> mListPostPath = new ArrayList();
    private int mTime = 0;
    private long mTotalSize = 0;
    private UploadManager mUploadManager;
    private int mUserID;
    private String mVideoToken;

    public void setBreakUpload(boolean isBreakUpload) {
        this.mIsBreakUpload = isBreakUpload;
    }

    public UploadQiNiu(Context context, int resource_id, int resource_type, String baseQiNiuPath, PostEdit coverImage, List<PostEdit> postList, List<UploadQiNiuLocalPathInfo> pathList, UploadManager uploadManager, Handler handler, PostDescribeChangeListener changeListener, boolean isOriginal) {
        this.mContext = context;
        this.mDraftsId = resource_id + "_" + resource_type;
        this.mBaseQiNiuPath = baseQiNiuPath;
        this.mUploadManager = uploadManager;
        this.mHandle = handler;
        this.mListPath = pathList;
        this.mCoverImage = coverImage;
        this.mList = postList;
        this.mUserID = SharedUtil.getInt(this.mContext, UserInfo.ID_KEY, 0);
        this.mIsOriginal = isOriginal;
        FengApplication.getInstance().upLoadLog(true, "");
        this.mChangeListener = changeListener;
        initModel();
    }

    private void initModel() {
        if (this.mListPath != null && this.mListPath.size() > 0) {
            for (UploadQiNiuLocalPathInfo pathInfo : this.mListPath) {
                long size;
                Options opts;
                PostEdit postEdit;
                if (pathInfo.type == 2) {
                    if (FileUtil.isFileExists(pathInfo.path)) {
                        size = getFileSize(pathInfo.path);
                        opts = new Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(pathInfo.path, opts);
                        postEdit = new PostEdit(pathInfo.type, opts.outWidth, opts.outHeight, "", pathInfo.path);
                        postEdit.setOnTextChange(this.mChangeListener);
                        postEdit.srcSize = size;
                        postEdit.setMime(opts.outMimeType);
                        this.mListPostPath.add(postEdit);
                        this.mTotalSize += size;
                    } else {
                        FengApplication.getInstance().upLoadLog(false, "检查图片路径不存在:" + pathInfo.path);
                        failHandle();
                        return;
                    }
                } else if (pathInfo.type == 3) {
                    if (FileUtil.isFileExists(pathInfo.path) && FileUtil.isFileExists(pathInfo.videocoverpath)) {
                        size = getFileSize(pathInfo.videocoverpath);
                        opts = new Options();
                        opts.inJustDecodeBounds = true;
                        BitmapFactory.decodeFile(pathInfo.videocoverpath, opts);
                        postEdit = new PostEdit(pathInfo.type, "", pathInfo.path);
                        postEdit.setOnTextChange(this.mChangeListener);
                        postEdit.videoCoverImage.srcWidth = opts.outWidth;
                        postEdit.videoCoverImage.srcHight = opts.outHeight;
                        postEdit.videoCoverImage.srcUrl = pathInfo.videocoverpath;
                        postEdit.videoCoverImage.srcSize = size;
                        postEdit.videoCoverImage.setMime(opts.outMimeType);
                        this.mTotalSize += size;
                        size = getFileSize(pathInfo.path);
                        postEdit.srcSize = size;
                        this.mListPostPath.add(postEdit);
                        this.mTotalSize += size;
                    } else {
                        FengApplication.getInstance().upLoadLog(false, "检查视频路径不存在:" + pathInfo.path);
                        failHandle();
                        return;
                    }
                } else if (pathInfo.type != 10) {
                    continue;
                } else if (FileUtil.isFileExists(pathInfo.videocoverpath)) {
                    size = getFileSize(pathInfo.videocoverpath);
                    for (PostEdit postEdit2 : this.mList) {
                        if (postEdit2.getType() == 3 && postEdit2.srcUrl.equals(pathInfo.path)) {
                            postEdit2.videoCoverImage.srcUrl = pathInfo.videocoverpath;
                            opts = new Options();
                            opts.inJustDecodeBounds = true;
                            BitmapFactory.decodeFile(pathInfo.path, opts);
                            postEdit2.videoCoverImage.srcWidth = opts.outWidth;
                            postEdit2.videoCoverImage.srcHight = opts.outHeight;
                            postEdit2.videoCoverImage.srcUrl = pathInfo.videocoverpath;
                            postEdit2.videoCoverImage.srcSize = size;
                            postEdit2.videoCoverImage.localeditcover = true;
                            postEdit2.videoCoverImage.setMime(opts.outMimeType);
                            this.mListPostPath.add(postEdit2.videoCoverImage);
                            this.mTotalSize += size;
                            break;
                        }
                    }
                    if (this.mListPostPath.size() == 0) {
                        FengApplication.getInstance().upLoadLog(false, "检查修改视频图片路径不存在:" + pathInfo.path);
                        failHandle();
                        return;
                    }
                } else {
                    FengApplication.getInstance().upLoadLog(false, "检查图片路径不存在:" + pathInfo.path);
                    failHandle();
                    return;
                }
            }
            initQiNiuToken(true);
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:25:0x0038 A:{SYNTHETIC, Splitter: B:25:0x0038} */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x002b A:{SYNTHETIC, Splitter: B:18:0x002b} */
    public long getFileSize(java.lang.String r10) {
        /*
        r9 = this;
        r1 = new java.io.File;
        r1.<init>(r10);
        r4 = 0;
        r2 = 0;
        r8 = r1.exists();	 Catch:{ Exception -> 0x0025 }
        if (r8 == 0) goto L_0x0019;
    L_0x000e:
        r3 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x0025 }
        r3.<init>(r1);	 Catch:{ Exception -> 0x0025 }
        r8 = r3.available();	 Catch:{ Exception -> 0x0044, all -> 0x0041 }
        r4 = (long) r8;
        r2 = r3;
    L_0x0019:
        if (r2 == 0) goto L_0x001e;
    L_0x001b:
        r2.close();	 Catch:{ Exception -> 0x0020 }
    L_0x001e:
        r6 = r4;
    L_0x001f:
        return r6;
    L_0x0020:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x001e;
    L_0x0025:
        r0 = move-exception;
    L_0x0026:
        r0.printStackTrace();	 Catch:{ all -> 0x0035 }
        if (r2 == 0) goto L_0x002e;
    L_0x002b:
        r2.close();	 Catch:{ Exception -> 0x0030 }
    L_0x002e:
        r6 = r4;
        goto L_0x001f;
    L_0x0030:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x002e;
    L_0x0035:
        r8 = move-exception;
    L_0x0036:
        if (r2 == 0) goto L_0x003b;
    L_0x0038:
        r2.close();	 Catch:{ Exception -> 0x003c }
    L_0x003b:
        throw r8;
    L_0x003c:
        r0 = move-exception;
        r0.printStackTrace();
        goto L_0x003b;
    L_0x0041:
        r8 = move-exception;
        r2 = r3;
        goto L_0x0036;
    L_0x0044:
        r0 = move-exception;
        r2 = r3;
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.feng.car.utils.UploadQiNiu.getFileSize(java.lang.String):long");
    }

    public void upLoad(final PostEdit edit, final String strToken) {
        this.mLastPro = 0.0d;
        this.mCurrentSize = 0;
        try {
            this.mUploadManager.put(edit.srcUrl, null, strToken, (UpCompletionHandler) new UpCompletionHandler() {
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.statusCode == 200) {
                        try {
                            edit.setSize(response.getString("fsize"));
                            edit.setHash(response.getString("hash"));
                            edit.setMime(response.getString("mimeType"));
                            edit.setUrl(response.getString("key"));
                            String orient;
                            if (edit.getType() == 3) {
                                edit.localVideo = true;
                                orient = "";
                                if (response.has("orient")) {
                                    orient = response.getString("orient");
                                }
                                if (TextUtils.isEmpty(orient) || !(orient.equalsIgnoreCase("90") || orient.equalsIgnoreCase("270"))) {
                                    edit.setWidth(response.getString("w"));
                                    edit.setHeight(response.getString("h"));
                                } else {
                                    edit.setWidth(response.getString("h"));
                                    edit.setHeight(response.getString("w"));
                                }
                                edit.setTime((int) Math.rint(response.getDouble("duration") * 1000.0d));
                                edit.setCoverimage(edit.videoCoverImage.getHash());
                                FengConstant.UPLOAD_VIDEO_COUNT++;
                            } else if (edit.getType() == 2 || edit.getType() == 10) {
                                orient = "";
                                if (response.has("orient")) {
                                    orient = response.getString("orient");
                                }
                                if (TextUtils.isEmpty(orient) || !(orient.equalsIgnoreCase("left-bottom") || orient.equalsIgnoreCase("right-top"))) {
                                    edit.setWidth(response.getString("w"));
                                    edit.setHeight(response.getString("h"));
                                } else {
                                    edit.setWidth(response.getString("h"));
                                    edit.setHeight(response.getString("w"));
                                }
                                if (edit.getType() == 2) {
                                    FengConstant.UPLOAD_IMAGE_COUNT++;
                                }
                            }
                            UploadQiNiu.this.mTime = 0;
                            if (edit.getType() == 2 || edit.getType() == 10) {
                                edit.srcUrl = UploadQiNiu.this.mBaseQiNiuPath + edit.getHash();
                                UploadQiNiu.this.saveDrafts();
                            }
                            if (edit.getType() != 10) {
                                UploadQiNiu.this.mList.add(edit);
                                UploadQiNiu.this.mHandle.sendEmptyMessage(1002);
                            } else if (edit.getType() == 10 && edit.localeditcover) {
                                EventBus.getDefault().post(new PostSortRefreshEvent());
                            }
                            UploadQiNiu.this.uploadToQiNiu();
                            FengApplication.getInstance().upLoadLog(false, "上传成功Type=" + edit.getType() + "  上传的路径" + edit.srcUrl + "  文件类型" + edit.getMime());
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (!UploadQiNiu.this.mIsBreakUpload) {
                                FengApplication.getInstance().upLoadLog(false, "上传成功Type=" + edit.getType() + ":" + e.toString());
                                UploadQiNiu.this.failHandle();
                                return;
                            }
                            return;
                        }
                    }
                    UploadQiNiu.this.mTime = UploadQiNiu.this.mTime + 1;
                    if (UploadQiNiu.this.mTime <= 3) {
                        UploadQiNiu.this.upLoad(edit, strToken);
                    } else if (!UploadQiNiu.this.mIsBreakUpload) {
                        FengApplication.getInstance().upLoadLog(false, "上传七牛重试次数" + UploadQiNiu.this.mTime + "失败type=" + edit.getType() + " 错误信息:" + info.error);
                        UploadQiNiu.this.failHandle();
                    }
                }
            }, new UploadOptions(null, null, false, new UpProgressHandler() {
                public void progress(String arg0, double arg1) {
                    if (!UploadQiNiu.this.mIsBreakUpload) {
                        if (arg1 - UploadQiNiu.this.mLastPro > 1.0E-8d) {
                            UploadQiNiu.this.mCurrentSize = UploadQiNiu.this.mCompleteSize + ((long) (((double) edit.srcSize) * arg1));
                            float pro = ((((float) UploadQiNiu.this.mCurrentSize) * 100.0f) / ((float) UploadQiNiu.this.mTotalSize)) * 1.0f;
                            if (pro != 100.0f) {
                                Message message = Message.obtain();
                                message.what = 1000;
                                message.obj = Float.valueOf(pro);
                                UploadQiNiu.this.mHandle.sendMessage(message);
                            }
                        }
                        if (arg1 == 1.0d) {
                            UploadQiNiu.this.mCompleteSize = UploadQiNiu.this.mCompleteSize + edit.srcSize;
                        }
                        UploadQiNiu.this.mLastPro = arg1;
                    }
                }
            }, new UpCancellationSignal() {
                public boolean isCancelled() {
                    if (UploadQiNiu.this.mIsBreakUpload) {
                        UploadQiNiu.this.mHandle.sendEmptyMessage(1001);
                    }
                    return UploadQiNiu.this.mIsBreakUpload;
                }
            }));
        } catch (Exception e) {
            if (!this.mIsBreakUpload) {
                FengApplication.getInstance().upLoadLog(false, "上传七牛异常:" + e.toString());
                failHandle();
            }
        }
    }

    private void initQiNiuToken(final boolean isFrist) {
        FengApplication.getInstance().httpRequest(HttpConstant.QINIUTOKEN, new HashMap(), new OkHttpResponseCallback() {
            public void onNetworkError() {
            }

            public void onStart() {
            }

            public void onFinish() {
            }

            public void onFailure(int statusCode, String content, Throwable error) {
                FengApplication.getInstance().upLoadLog(false, "获得七牛token失败onFailure");
                UploadQiNiu.this.failHandle();
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    int code = jsonObject.getInt("code");
                    if (code == 1) {
                        JSONObject tokenJson = jsonObject.getJSONObject("body").getJSONObject(HttpConstant.TOKEN);
                        UploadQiNiu.this.mImageToken = tokenJson.getString("image");
                        UploadQiNiu.this.mVideoToken = tokenJson.getString("video");
                        FengApplication.getInstance().upLoadLog(false, "获得七牛token成功");
                        UploadQiNiu.this.mFirstTokenTime = System.currentTimeMillis();
                        if (isFrist) {
                            UploadQiNiu.this.uploadToQiNiu();
                            return;
                        }
                        return;
                    }
                    FengApplication.getInstance().upLoadLog(false, "获得七牛token失败code=" + code);
                    if (isFrist) {
                        UploadQiNiu.this.failHandle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    FengApplication.getInstance().upLoadLog(false, "获得七牛token失败:" + e.toString());
                    if (isFrist) {
                        UploadQiNiu.this.failHandle();
                    }
                }
            }
        });
    }

    private void uploadToQiNiu() {
        if (DateUtil.getOffectMinutes(System.currentTimeMillis(), this.mFirstTokenTime) >= 10) {
            initQiNiuToken(false);
        }
        Iterator it = this.mListPostPath.iterator();
        if (it.hasNext()) {
            PostEdit edit = (PostEdit) it.next();
            if (edit.getType() == 2) {
                ImageVideoInfo imageItem = new ImageVideoInfo();
                imageItem.url = edit.srcUrl;
                imageItem.mimeType = edit.getMime();
                if (!edit.getMime().equals(FengConstant.GIF) && edit.getType() == 2) {
                    if (!this.mIsOriginal) {
                        FengUtil.scaleBitmap(imageItem);
                    }
                    edit.srcUrl = imageItem.url;
                }
                upLoad(edit, this.mImageToken);
            } else if (edit.getType() == 3) {
                if (TextUtils.isEmpty(edit.videoCoverImage.getHash())) {
                    upLoad(edit.videoCoverImage, this.mImageToken);
                    return;
                }
                upLoad(edit, this.mVideoToken);
            } else if (edit.getType() == 10) {
                upLoad(edit, this.mImageToken);
            }
            this.mListPostPath.remove(edit);
            return;
        }
        successUploadHandle();
    }

    private void successUploadHandle() {
        saveDrafts();
        this.mHandle.sendEmptyMessage(TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC);
    }

    private void failHandle() {
        saveDrafts();
        this.mHandle.sendEmptyMessage(1004);
    }

    private void saveDrafts() {
        SparkDB sparkDB = FengApplication.getInstance().getSparkDB();
        String str = this.mDraftsId;
        String toJson = (this.mCoverImage != null || TextUtils.isEmpty(this.mCoverImage.getHash())) ? "" : JsonUtil.toJson(this.mCoverImage);
        if (!sparkDB.updateDraftsPostJson(str, toJson, JsonUtil.toJson(this.mList), this.mUserID, 1)) {
            this.mHandle.sendEmptyMessage(TXLiveConstants.PUSH_EVT_CHANGE_BITRATE);
        }
    }
}
