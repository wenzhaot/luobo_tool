package com.uuzuche.lib_zxing.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.stub.StubApp;
import com.uuzuche.lib_zxing.activity.CodeUtils.AnalyzeCallback;

public class CaptureActivity extends AppCompatActivity {
    AnalyzeCallback analyzeCallback = new AnalyzeCallback() {
        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, 1);
            bundle.putString(CodeUtils.RESULT_STRING, result);
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(-1, resultIntent);
            CaptureActivity.this.finish();
        }

        public void onAnalyzeFailed() {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putInt(CodeUtils.RESULT_TYPE, 2);
            bundle.putString(CodeUtils.RESULT_STRING, "");
            resultIntent.putExtras(bundle);
            CaptureActivity.this.setResult(-1, resultIntent);
            CaptureActivity.this.finish();
        }
    };

    static {
        StubApp.interface11(9080);
    }

    protected native void onCreate(Bundle bundle);
}
