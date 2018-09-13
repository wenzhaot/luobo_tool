package com.feng.car.activity;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.media.session.PlaybackStateCompat;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.feng.car.BuildConfig;
import com.feng.car.FengApplication;
import com.feng.car.R;
import com.feng.car.databinding.NetCheckActivityBinding;
import com.feng.car.utils.FengUtil;
import com.feng.library.okhttp.callback.OkHttpResponseCallback;
import com.netease.LDNetDiagnoService.LDNetDiagnoListener;
import com.netease.LDNetDiagnoService.LDNetDiagnoService;
import com.netease.LDNetDiagnoUtils.LDNetUtil;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.stub.StubApp;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class NetCheckActivity extends BaseActivity<NetCheckActivityBinding> implements LDNetDiagnoListener {
    private LDNetDiagnoService _netDiagnoService;
    private boolean isRunning = false;
    private String mDoMain = "";
    private UploadManager mUploadManager;
    private String showInfo = "";

    static {
        StubApp.interface11(2547);
    }

    protected native void onCreate(Bundle bundle);

    public int setBaseContentView() {
        return R.layout.net_check_activity;
    }

    public void initView() {
        initNormalTitleBar("网络检测");
        ((NetCheckActivityBinding) this.mBaseBinding).rg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_api /*2131625294*/:
                        NetCheckActivity.this.mDoMain = "api.laosiji.com";
                        return;
                    case R.id.rb_image /*2131625295*/:
                        NetCheckActivity.this.mDoMain = "imageqiniu.laosiji.com";
                        return;
                    case R.id.rb_video /*2131625296*/:
                        NetCheckActivity.this.mDoMain = "videoqiniu.laosiji.com";
                        return;
                    case R.id.rb_upload /*2131625297*/:
                        NetCheckActivity.this.mDoMain = "upload.qiniu.com";
                        return;
                    default:
                        return;
                }
            }
        });
        ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setOnClickListener(this);
        ((NetCheckActivityBinding) this.mBaseBinding).btnCopy.setOnClickListener(this);
    }

    public void onSingleClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start /*2131625299*/:
                boolean z;
                if (this.isRunning) {
                    ((NetCheckActivityBinding) this.mBaseBinding).progress.setVisibility(4);
                    ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setText("开始诊断");
                    this._netDiagnoService.cancel(true);
                    this._netDiagnoService = null;
                    ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setEnabled(true);
                    ((NetCheckActivityBinding) this.mBaseBinding).domainName.setInputType(1);
                } else {
                    this.showInfo = "";
                    if (((NetCheckActivityBinding) this.mBaseBinding).domainName.getText().toString().trim().length() > 0) {
                        this.mDoMain = ((NetCheckActivityBinding) this.mBaseBinding).domainName.getText().toString().trim();
                    }
                    LDNetUtil.OPEN_IP = this.mDoMain;
                    this._netDiagnoService = new LDNetDiagnoService(StubApp.getOrigApplicationContext(getApplicationContext()), BuildConfig.APPLICATION_ID, "老司机", FengUtil.getVersion(this), FengApplication.getInstance().isLoginUser() ? FengApplication.getInstance().getUserInfo().id + "" : "", "deviceID(option)", this.mDoMain, getSimOperatorInfo(), "ISOCountyCode", "MobilCountryCode", "MobileNetCode", this);
                    this._netDiagnoService.setIfUseJNICTrace(true);
                    this._netDiagnoService.execute(new String[0]);
                    ((NetCheckActivityBinding) this.mBaseBinding).progress.setVisibility(0);
                    ((NetCheckActivityBinding) this.mBaseBinding).text.setText("Traceroute with max 30 hops...");
                    ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setText("停止诊断");
                    ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setEnabled(false);
                    ((NetCheckActivityBinding) this.mBaseBinding).domainName.setInputType(0);
                }
                if (this.isRunning) {
                    z = false;
                } else {
                    z = true;
                }
                this.isRunning = z;
                return;
            case R.id.btn_copy /*2131625300*/:
                FengUtil.copyText(this, this.showInfo, "已复制");
                return;
            default:
                return;
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        if (this._netDiagnoService != null) {
            this._netDiagnoService.stopNetDialogsis();
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void OnNetDiagnoFinished(String log) {
        if (((NetCheckActivityBinding) this.mBaseBinding).rg.getCheckedRadioButtonId() == R.id.rb_upload) {
            ((NetCheckActivityBinding) this.mBaseBinding).text.setText(log + "\n开始上传...");
            initQiNiuToken();
            return;
        }
        ((NetCheckActivityBinding) this.mBaseBinding).text.setText(log);
        ((NetCheckActivityBinding) this.mBaseBinding).progress.setVisibility(4);
        ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setText("开始诊断");
        ((NetCheckActivityBinding) this.mBaseBinding).btnStart.setEnabled(true);
        ((NetCheckActivityBinding) this.mBaseBinding).domainName.setInputType(1);
        this.isRunning = false;
    }

    public void OnNetDiagnoUpdated(String log) {
        this.showInfo += log;
        ((NetCheckActivityBinding) this.mBaseBinding).text.setText(this.showInfo);
    }

    public String getSimOperatorInfo() {
        String operatorString = ((TelephonyManager) getSystemService("phone")).getSimOperator();
        if (operatorString == null) {
            return "未知";
        }
        if (operatorString.equals("46000") || operatorString.equals("46002")) {
            return "中国移动";
        }
        if (operatorString.equals("46001")) {
            return "中国联通";
        }
        if (operatorString.equals("46003")) {
            return "中国电信";
        }
        return "未知";
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
            }

            public void onSuccess(int statusCode, String content) {
                try {
                    JSONObject jsonObject = new JSONObject(content);
                    if (jsonObject.getInt("code") == 1) {
                        NetCheckActivity.this.upLoad(jsonObject.getJSONObject("body").getJSONObject("token").getString("testimage"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void upLoad(String strToken) {
        if (this.mUploadManager == null) {
            this.mUploadManager = new UploadManager();
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.login_5x_explain);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, 100, outputStream);
            final long startTime = System.currentTimeMillis();
            this.mUploadManager.put(outputStream.toByteArray(), UUID.randomUUID().toString(), strToken, new UpCompletionHandler() {
                public void complete(String key, ResponseInfo info, JSONObject response) {
                    if (info.statusCode == 200) {
                        try {
                            ((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).text.setText(((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).text.getText().toString() + "\n速度=" + (((float) (Integer.parseInt(response.getString("fsize")) * 1000)) / (((float) (PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID * (System.currentTimeMillis() - startTime))) * 1.0f)) + "kb/s");
                            return;
                        } catch (JSONException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                    ((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).progress.setVisibility(4);
                    ((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).btnStart.setText("开始诊断");
                    ((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).btnStart.setEnabled(true);
                    ((NetCheckActivityBinding) NetCheckActivity.this.mBaseBinding).domainName.setInputType(1);
                    NetCheckActivity.this.isRunning = false;
                }
            }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
