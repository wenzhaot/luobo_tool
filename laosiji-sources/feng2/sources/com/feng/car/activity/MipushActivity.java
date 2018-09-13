package com.feng.car.activity;

import android.content.Intent;
import android.os.Bundle;
import com.stub.StubApp;
import com.umeng.message.UmengNotifyClickActivity;
import org.json.JSONObject;

public class MipushActivity extends UmengNotifyClickActivity {
    static {
        StubApp.interface11(2526);
    }

    protected native void onCreate(Bundle bundle);

    public void onMessage(Intent intent) {
        super.onMessage(intent);
        JSONObject jsonObject = new JSONObject(intent.getStringExtra("body"));
        Intent intent1 = new Intent(this, TransparentActivity.class);
        if (jsonObject.has("extra") && jsonObject.getJSONObject("extra").has("url")) {
            intent1.putExtra("url", jsonObject.getJSONObject("extra").getString("url"));
            intent1.putExtra("umeng_push", 1);
        } else {
            try {
                if (jsonObject.has("body") && jsonObject.getJSONObject("body").has("url")) {
                    intent1.putExtra("url", jsonObject.getJSONObject("body").getString("url"));
                    intent1.putExtra("umeng_push", 1);
                } else {
                    intent1.putExtra("url", "");
                    intent1.putExtra("umeng_push", 1);
                }
            } catch (Exception e) {
                e.printStackTrace();
                startActivity(new Intent(this, SplashActivity.class));
            }
        }
        intent1.addFlags(268435456);
        startActivity(intent1);
        finish();
    }
}
