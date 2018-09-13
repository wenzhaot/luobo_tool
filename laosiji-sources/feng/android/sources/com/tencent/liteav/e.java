package com.tencent.liteav;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.feng.car.video.shortvideo.FileUtils;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.umeng.message.util.HttpRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/* compiled from: TXCTimeShiftUtil */
public class e {
    private String a = "";
    private String b = "";
    private String c = "";
    private long d = 0;
    private long e = 0;
    private long f = 0;

    /* compiled from: TXCTimeShiftUtil */
    public interface a {
        void onLiveTime(long j);
    }

    public e(Context context) {
    }

    public long a() {
        this.f = System.currentTimeMillis() - this.d;
        return this.f;
    }

    public String a(long j) {
        String format = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(this.d + (1000 * j)));
        return String.format("http://playtimeshift.live.myqcloud.com/%s/%s/timeshift.m3u8?starttime=%s&appid=%s&txKbps=0", new Object[]{this.b, this.a, format, this.c});
    }

    public int a(final String str, final a aVar) {
        if (str == null || str.isEmpty()) {
            return -1;
        }
        AsyncTask.execute(new Runnable() {
            public void run() {
                e.this.e = 0;
                e.this.d = 0;
                e.this.f = 0;
                e.this.a = "";
                e.this.b = "";
                e.this.a = TXCCommonUtil.getStreamIDByStreamUrl(str);
                e.this.b = e.this.a("bizid", str);
                if (TextUtils.isEmpty(e.this.b)) {
                    e.this.b = str.substring(str.indexOf("//") + 2, str.indexOf(FileUtils.FILE_EXTENSION_SEPARATOR));
                }
                e.this.c = TXCCommonUtil.getAppID();
                try {
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(String.format("http://playtimeshift.live.myqcloud.com/%s/%s/timeshift.m3u8?delay=0&appid=%s&txKbps=0", new Object[]{e.this.b, e.this.a, e.this.c})).openConnection();
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setUseCaches(false);
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(5000);
                    httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                    httpURLConnection.setRequestProperty("Charsert", "UTF-8");
                    httpURLConnection.setRequestProperty("Content-Type", "text/plain;");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    String str = "";
                    str = "";
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            break;
                        }
                        str = str + readLine;
                    }
                    TXCLog.e("TXCTimeShiftUtil", "prepareSeekTime: receive response, strResponse = " + str);
                    str = e.this.a(str);
                    long parseLong = Long.parseLong(str);
                    long currentTimeMillis = System.currentTimeMillis();
                    TXCLog.i("TXCTimeShiftUtil", "time:" + str + ",currentTime:" + currentTimeMillis + ",diff:" + (currentTimeMillis - parseLong));
                    e.this.d = parseLong * 1000;
                    e.this.f = currentTimeMillis - e.this.d;
                    if (aVar != null) {
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            public void run() {
                                aVar.onLiveTime(e.this.f);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.this.d = 0;
                    e.printStackTrace();
                }
            }
        });
        return 0;
    }

    private String a(String str) {
        if (str.contains("#EXT-TX-TS-START-TIME")) {
            int indexOf = str.indexOf("#EXT-TX-TS-START-TIME:") + 22;
            if (indexOf > 0) {
                String substring = str.substring(indexOf);
                int indexOf2 = substring.indexOf("#");
                if (indexOf2 > 0) {
                    return substring.substring(0, indexOf2).replaceAll("\r\n", "");
                }
            }
        }
        return null;
    }

    private String a(String str, String str2) {
        if (str == null || str.length() == 0 || str2 == null || str2.length() == 0) {
            return null;
        }
        String toLowerCase = str.toLowerCase();
        for (String str3 : str2.split("[?&]")) {
            String str32;
            if (str32.indexOf("=") != -1) {
                String[] split = str32.split("[=]");
                if (split.length == 2) {
                    String str4 = split[0];
                    str32 = split[1];
                    if (str4 != null && str4.toLowerCase().equalsIgnoreCase(toLowerCase)) {
                        return str32;
                    }
                }
                continue;
            }
        }
        return "";
    }
}
