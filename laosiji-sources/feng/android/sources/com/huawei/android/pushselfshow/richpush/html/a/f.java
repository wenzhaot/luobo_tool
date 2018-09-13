package com.huawei.android.pushselfshow.richpush.html.a;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Handler;
import com.huawei.android.pushagent.a.a.c;
import com.huawei.android.pushselfshow.richpush.html.api.NativeToJsMessageQueue;
import com.huawei.android.pushselfshow.richpush.html.api.b;
import com.umeng.message.proguard.l;
import org.json.JSONException;
import org.json.JSONObject;

public class f implements OnCompletionListener, OnErrorListener, OnPreparedListener, h {
    public String a = null;
    Handler b = new Handler();
    Runnable c = null;
    boolean d = true;
    private a e = a.MEDIA_NONE;
    private String f = null;
    private int g = 1000;
    private MediaPlayer h = null;
    private int i = 0;
    private NativeToJsMessageQueue j;

    public enum a {
        MEDIA_NONE,
        MEDIA_STARTING,
        MEDIA_RUNNING,
        MEDIA_PAUSED,
        MEDIA_STOPPED
    }

    public f(Context context) {
        c.e("PushSelfShowLog", "init AudioPlayer");
    }

    private void a(a aVar) {
        this.e = aVar;
    }

    /* JADX WARNING: Removed duplicated region for block: B:48:0x00e6 A:{SYNTHETIC, Splitter: B:48:0x00e6} */
    /* JADX WARNING: Removed duplicated region for block: B:67:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:26:0x0092 A:{SYNTHETIC, Splitter: B:26:0x0092} */
    /* JADX WARNING: Removed duplicated region for block: B:69:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:34:0x00b2 A:{SYNTHETIC, Splitter: B:34:0x00b2} */
    /* JADX WARNING: Removed duplicated region for block: B:71:? A:{SYNTHETIC, RETURN} */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x00d2 A:{SYNTHETIC, Splitter: B:42:0x00d2} */
    private boolean j() {
        /*
        r4 = this;
        r0 = 0;
        r1 = r4.e;
        r1 = r1.ordinal();
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE;
        r2 = r2.ordinal();
        if (r1 != r2) goto L_0x00f5;
    L_0x000f:
        r1 = r4.h;
        if (r1 != 0) goto L_0x0029;
    L_0x0013:
        r1 = new android.media.MediaPlayer;
        r1.<init>();
        r4.h = r1;
        r1 = r4.h;
        r1.setOnErrorListener(r4);
        r1 = r4.h;
        r1.setOnPreparedListener(r4);
        r1 = r4.h;
        r1.setOnCompletionListener(r4);
    L_0x0029:
        r1 = 0;
        r2 = r4.f;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = com.huawei.android.pushselfshow.richpush.html.api.b.a(r2);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        if (r2 == 0) goto L_0x004f;
    L_0x0032:
        r2 = r4.h;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r3 = r4.f;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2.setDataSource(r3);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = r4.h;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r3 = 3;
        r2.setAudioStreamType(r3);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r4.a(r2);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = r4.h;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2.prepareAsync();	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
    L_0x0049:
        if (r1 == 0) goto L_0x004e;
    L_0x004b:
        r1.close();	 Catch:{ Exception -> 0x0076 }
    L_0x004e:
        return r0;
    L_0x004f:
        r3 = new java.io.File;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = r4.f;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r3.<init>(r2);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2 = r3.exists();	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        if (r2 == 0) goto L_0x0049;
    L_0x005c:
        r2 = new java.io.FileInputStream;	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r2.<init>(r3);	 Catch:{ RuntimeException -> 0x0081, FileNotFoundException -> 0x00a1, IOException -> 0x00c1 }
        r1 = r4.h;	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r3 = r2.getFD();	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r1.setDataSource(r3);	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r1 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING;	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r4.a(r1);	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r1 = r4.h;	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r1.prepare();	 Catch:{ RuntimeException -> 0x0109, FileNotFoundException -> 0x0106, IOException -> 0x0103, all -> 0x0100 }
        r1 = r2;
        goto L_0x0049;
    L_0x0076:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "close fileInputStream error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);
        goto L_0x004e;
    L_0x0081:
        r2 = move-exception;
    L_0x0082:
        r2 = "PushSelfShowLog";
        r3 = "prepareAsync/prepare error";
        com.huawei.android.pushagent.a.a.c.e(r2, r3);	 Catch:{ all -> 0x00e3 }
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE;	 Catch:{ all -> 0x00e3 }
        r4.a(r2);	 Catch:{ all -> 0x00e3 }
        if (r1 == 0) goto L_0x004e;
    L_0x0092:
        r1.close();	 Catch:{ Exception -> 0x0096 }
        goto L_0x004e;
    L_0x0096:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "close fileInputStream error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);
        goto L_0x004e;
    L_0x00a1:
        r2 = move-exception;
    L_0x00a2:
        r2 = "PushSelfShowLog";
        r3 = "prepareAsync/prepare error";
        com.huawei.android.pushagent.a.a.c.e(r2, r3);	 Catch:{ all -> 0x00e3 }
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE;	 Catch:{ all -> 0x00e3 }
        r4.a(r2);	 Catch:{ all -> 0x00e3 }
        if (r1 == 0) goto L_0x004e;
    L_0x00b2:
        r1.close();	 Catch:{ Exception -> 0x00b6 }
        goto L_0x004e;
    L_0x00b6:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "close fileInputStream error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);
        goto L_0x004e;
    L_0x00c1:
        r2 = move-exception;
    L_0x00c2:
        r2 = "PushSelfShowLog";
        r3 = "prepareAsync/prepare error";
        com.huawei.android.pushagent.a.a.c.e(r2, r3);	 Catch:{ all -> 0x00e3 }
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_NONE;	 Catch:{ all -> 0x00e3 }
        r4.a(r2);	 Catch:{ all -> 0x00e3 }
        if (r1 == 0) goto L_0x004e;
    L_0x00d2:
        r1.close();	 Catch:{ Exception -> 0x00d7 }
        goto L_0x004e;
    L_0x00d7:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "close fileInputStream error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);
        goto L_0x004e;
    L_0x00e3:
        r0 = move-exception;
    L_0x00e4:
        if (r1 == 0) goto L_0x00e9;
    L_0x00e6:
        r1.close();	 Catch:{ Exception -> 0x00ea }
    L_0x00e9:
        throw r0;
    L_0x00ea:
        r1 = move-exception;
        r1 = "PushSelfShowLog";
        r2 = "close fileInputStream error";
        com.huawei.android.pushagent.a.a.c.e(r1, r2);
        goto L_0x00e9;
    L_0x00f5:
        r2 = com.huawei.android.pushselfshow.richpush.html.a.f.a.MEDIA_STARTING;
        r2 = r2.ordinal();
        if (r1 == r2) goto L_0x004e;
    L_0x00fd:
        r0 = 1;
        goto L_0x004e;
    L_0x0100:
        r0 = move-exception;
        r1 = r2;
        goto L_0x00e4;
    L_0x0103:
        r1 = move-exception;
        r1 = r2;
        goto L_0x00c2;
    L_0x0106:
        r1 = move-exception;
        r1 = r2;
        goto L_0x00a2;
    L_0x0109:
        r1 = move-exception;
        r1 = r2;
        goto L_0x0082;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.huawei.android.pushselfshow.richpush.html.a.f.j():boolean");
    }

    private float k() {
        try {
            return ((float) this.h.getDuration()) / 1000.0f;
        } catch (Exception e) {
            c.e("PushSelfShowLog", "getDurationInSeconds error ");
            return -1.0f;
        }
    }

    public String a(String str, JSONObject jSONObject) {
        return null;
    }

    public void a() {
        if (j() && this.h != null) {
            h();
        }
    }

    public void a(int i) {
        try {
            if (j()) {
                this.h.seekTo(i);
                c.a("PushSelfShowLog", "Send a onStatus update for the new seek");
                return;
            }
            this.i = i;
        } catch (IllegalStateException e) {
            c.a("PushSelfShowLog", "seekToPlaying failed");
        } catch (Exception e2) {
            c.a("PushSelfShowLog", "seekToPlaying failed");
        }
    }

    public void a(int i, int i2, Intent intent) {
    }

    public void a(NativeToJsMessageQueue nativeToJsMessageQueue, String str, String str2, JSONObject jSONObject) {
        if (nativeToJsMessageQueue == null) {
            c.a("PushSelfShowLog", "jsMessageQueue is null while run into Audio Player exec");
            return;
        }
        this.j = nativeToJsMessageQueue;
        if ("preparePlaying".equals(str)) {
            d();
            if (str2 != null) {
                this.a = str2;
                a(jSONObject);
                return;
            }
            c.a("PushSelfShowLog", "Audio exec callback is null ");
        } else if ("startPlaying".equals(str)) {
            a();
        } else if ("seekToPlaying".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("milliseconds")) {
                        a(jSONObject.getInt("milliseconds"));
                    }
                } catch (JSONException e) {
                    c.a("PushSelfShowLog", "seekto error");
                }
            }
        } else if ("pausePlaying".equals(str)) {
            e();
        } else if ("stopPlaying".equals(str)) {
            f();
        } else if ("getPlayingStatus".equals(str)) {
            if (jSONObject != null) {
                try {
                    if (jSONObject.has("frequently")) {
                        int i = jSONObject.getInt("frequently");
                        if (i > this.g) {
                            this.g = i;
                        }
                    }
                } catch (JSONException e2) {
                    c.a("PushSelfShowLog", "seekto error");
                }
            }
            c.e("PushSelfShowLog", "this.frequently is " + this.g);
            g();
        } else {
            nativeToJsMessageQueue.a(str2, com.huawei.android.pushselfshow.richpush.html.api.d.a.METHOD_NOT_FOUND_EXCEPTION, "error", null);
        }
    }

    public void a(JSONObject jSONObject) {
        c.e("PushSelfShowLog", " run into Audio player createAudio");
        if (jSONObject == null || !jSONObject.has("url")) {
            this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
        } else {
            try {
                String string = jSONObject.getString("url");
                String a = b.a(this.j.a(), string);
                if (a == null || a.length() <= 0) {
                    c.e("PushSelfShowLog", string + "File not exist");
                    this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_ONLY_SUPPORT_HTTP, "error", null);
                } else {
                    this.f = a;
                    this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.OK, "success", null);
                }
                if (jSONObject.has("pauseOnActivityPause")) {
                    this.d = jSONObject.getBoolean("pauseOnActivityPause");
                }
            } catch (Throwable e) {
                c.d("PushSelfShowLog", "startPlaying failed ", e);
                this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.JSON_EXCEPTION, "error", null);
            }
        }
        c.e("PushSelfShowLog", " this.audioFile = " + this.f);
    }

    public void b() {
        c.e("PushSelfShowLog", "Audio onResume");
    }

    public void c() {
        c.b("PushSelfShowLog", "Audio onPause and pauseOnActivityPause is %s  this.player is %s", Boolean.valueOf(this.d), this.h);
        d();
    }

    public void d() {
        c.e("PushSelfShowLog", "Audio reset/Destory");
        try {
            this.d = true;
            if (this.h != null) {
                if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
                    this.h.stop();
                }
                this.h.release();
                this.h = null;
            }
            this.f = null;
            a(a.MEDIA_NONE);
            this.g = 1000;
            this.i = 0;
            if (this.c != null) {
                this.b.removeCallbacks(this.c);
            }
            this.c = null;
        } catch (IllegalStateException e) {
            c.a("PushSelfShowLog", "reset music error");
        } catch (Exception e2) {
            c.a("PushSelfShowLog", "reset music error");
        }
    }

    public void e() {
        if (this.e != a.MEDIA_RUNNING || this.h == null) {
            c.a("PushSelfShowLog", "AudioPlayer Error: pausePlaying() called during invalid state: " + this.e.ordinal());
            return;
        }
        this.h.pause();
        a(a.MEDIA_PAUSED);
    }

    public void f() {
        if (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) {
            this.h.pause();
            this.h.seekTo(0);
            c.a("PushSelfShowLog", "stopPlaying is calling stopped");
            a(a.MEDIA_STOPPED);
            return;
        }
        c.a("PushSelfShowLog", "AudioPlayer Error: stopPlaying() called during invalid state: " + this.e.ordinal());
    }

    public void g() {
        c.e("PushSelfShowLog", "getPlayingStatusRb is " + this.c);
        if (this.c == null) {
            this.c = new g(this);
        } else {
            try {
                this.b.removeCallbacks(this.c);
            } catch (Exception e) {
                c.e("PushSelfShowLog", "getPlayingStatus error,handler.removeCallbacks");
            }
        }
        this.b.postDelayed(this.c, (long) this.g);
        c.e("PushSelfShowLog", "handler.postDelayed " + this.g);
    }

    public void h() {
        try {
            this.h.start();
            a(a.MEDIA_RUNNING);
            this.i = 0;
        } catch (Throwable e) {
            c.d("PushSelfShowLog", "play() error ", e);
        }
    }

    public long i() {
        return (this.e == a.MEDIA_RUNNING || this.e == a.MEDIA_PAUSED) ? (long) (this.h.getCurrentPosition() / 1000) : -1;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        c.a("PushSelfShowLog", "on completion is calling stopped");
        a(a.MEDIA_STOPPED);
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        c.a("PushSelfShowLog", "AudioPlayer.onError(" + i + ", " + i2 + l.t);
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("url", this.f);
            this.j.a(this.a, com.huawei.android.pushselfshow.richpush.html.api.d.a.AUDIO_PLAY_ERROR, "error", jSONObject);
        } catch (JSONException e) {
            c.e("PushSelfShowLog", "onError error");
        }
        d();
        return false;
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        a(this.i);
        h();
    }
}
