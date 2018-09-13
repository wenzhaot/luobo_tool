package com.tencent.liteav.g;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.feng.car.video.shortvideo.TCConstants;
import com.feng.library.utils.DateUtil;
import com.meizu.cloud.pushsdk.constants.PushConstants;
import com.stub.StubApp;
import com.taobao.accs.common.Constants;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.a;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tencent.liteav.j.b;
import com.tencent.liteav.j.g;
import com.umeng.socialize.sina.params.ShareRequestParam;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: UGCLicenceNewCheck */
public class f {
    private static f k;
    private Context a;
    private String b;
    private String c;
    private String d = TCConstants.UGC_LICENCE_NAME;
    private String e = "tmp.licence";
    private String f = "YTFaceSDK.licence";
    private String g = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4teqkW/TUruU89ElNVd\nKrpSL+HCITruyb6BS9mW6M4mqmxDhazDmQgMKNfsA0d2kxFucCsXTyesFNajaisk\nrAzVJpNGO75bQFap4jYzJYskIuas6fgIS7zSmGXgRcp6i0ZBH3pkVCXcgfLfsVCO\n+sN01jFhFgOC0LY2f1pJ+3jqktAlMIxy8Q9t7XwwL5/n8/Sledp7TwuRdnl2OPl3\nycCTRkXtOIoRNB9vgd9XooTKiEdCXC7W9ryvtwCiAB82vEfHWXXgzhsPC13URuFy\n1JqbWJtTCCcfsCVxuBplhVJAQ7JsF5SMntdJDkp7rJLhprgsaim2CRjcVseNmw97\nbwIDAQAB";
    private String h;
    private String i;
    private String j;
    private boolean l = false;
    private int m = 3;

    public static f a() {
        if (k == null) {
            k = new f();
        }
        return k;
    }

    private f() {
    }

    public void a(Context context, String str, String str2) {
        this.a = StubApp.getOrigApplicationContext(context.getApplicationContext());
        this.b = str;
        this.c = str2;
    }

    public void b() {
        if (TextUtils.isEmpty(this.b)) {
            TXCLog.e("UGCLicenceNewCheck", "downloadLicense, mUrl is empty, ignore !");
            return;
        }
        b anonymousClass1 = new b() {
            public void a(File file) {
                TXCLog.i("UGCLicenceNewCheck", "downloadLicense, onSaveSuccess");
                String a = f.this.f();
                if (TextUtils.isEmpty(a)) {
                    TXCLog.e("UGCLicenceNewCheck", "downloadLicense, readDownloadTempLicence is empty!");
                } else if (f.this.b(a, null) == 0) {
                    f.this.d();
                }
            }

            public void a(File file, Exception exception) {
                TXCLog.i("UGCLicenceNewCheck", "downloadLicense, onSaveFailed");
            }

            public void a(int i) {
                TXCLog.i("UGCLicenceNewCheck", "downloadLicense, onProgressUpdate");
            }

            public void a() {
                TXCLog.i("UGCLicenceNewCheck", "downloadLicense, onProcessEnd");
            }
        };
        this.h = this.a.getExternalFilesDir(null).getAbsolutePath();
        new Thread(new c(this.a, this.b, this.h, this.e, anonymousClass1, false)).start();
    }

    public int a(e eVar, Context context) {
        if (this.l) {
            TXCLog.i("UGCLicenceNewCheck", "check, sdkType = 3, mLicenceVersionType = " + this.m);
            if (3 <= this.m) {
                return 0;
            }
            return -9;
        }
        if (this.a == null) {
            this.a = context;
        }
        if (b(eVar) == 0) {
            this.l = true;
            return 0;
        }
        int a = a(eVar);
        if (a == 0) {
            this.l = true;
            return 0;
        }
        b();
        return a;
    }

    private int a(e eVar) {
        String str = this.a.getExternalFilesDir(null).getAbsolutePath() + File.separator + this.d;
        if (!b(str)) {
            return -7;
        }
        str = g.b(str);
        if (!TextUtils.isEmpty(str)) {
            return a(str, eVar);
        }
        TXCLog.e("UGCLicenceNewCheck", "checkSdcardLicence, licenceSdcardStr is empty");
        return -8;
    }

    private int b(e eVar) {
        if (!c()) {
            return -6;
        }
        String b = g.b(this.a, this.d);
        if (!TextUtils.isEmpty(b)) {
            return a(b, eVar);
        }
        TXCLog.e("UGCLicenceNewCheck", "checkAssetLicence, licenceSdcardStr is empty");
        return -8;
    }

    public int a(String str, e eVar) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            return b(str, eVar);
        } catch (JSONException e) {
            return c(str, eVar);
        }
    }

    private boolean c() {
        return g.a(this.a, this.d);
    }

    private boolean b(String str) {
        return g.a(str);
    }

    private void d() {
        File file = new File(this.a.getExternalFilesDir(null).getAbsolutePath() + File.separator + this.d);
        if (file.exists()) {
            TXCLog.i("UGCLicenceNewCheck", "delete dst file:" + file.delete());
        }
        File file2 = new File(this.h + File.separator + this.e);
        if (file2.exists()) {
            TXCLog.i("UGCLicenceNewCheck", "rename file:" + file2.renameTo(file));
        }
        this.l = true;
    }

    private static long c(String str) {
        try {
            return new SimpleDateFormat(DateUtil.dateFormatYMD).parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public PublicKey a(String str) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
    }

    private String e() {
        if (TextUtils.isEmpty(this.c)) {
            TXCLog.e("UGCLicenceNewCheck", "decodeLicence, mKey is empty!!!");
            return "";
        }
        byte[] bytes = this.c.getBytes();
        Key secretKeySpec = new SecretKeySpec(bytes, "AES");
        byte[] bArr = new byte[16];
        int i = 0;
        while (i < bytes.length && i < bArr.length) {
            bArr[i] = bytes[i];
            i++;
        }
        AlgorithmParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        byte[] decode = Base64.decode(this.i, 0);
        try {
            Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
            instance.init(2, secretKeySpec, ivParameterSpec);
            String str = new String(instance.doFinal(decode), "UTF-8");
            TXCLog.i("UGCLicenceNewCheck", "decodeLicence : " + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private int b(String str, e eVar) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt("appId");
            this.i = jSONObject.getString("encryptedLicense");
            this.j = jSONObject.getString("signature");
            TXCLog.i("UGCLicenceNewCheck", "appid:" + optInt);
            TXCLog.i("UGCLicenceNewCheck", "encryptedLicense:" + this.i);
            TXCLog.i("UGCLicenceNewCheck", "signature:" + this.j);
            return c(eVar);
        } catch (JSONException e) {
            this.i = null;
            this.j = null;
            e.printStackTrace();
            a(-1);
            return -1;
        }
    }

    private int c(e eVar) {
        boolean a;
        try {
            a = a(Base64.decode(this.i, 0), Base64.decode(this.j, 0), a(this.g));
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.e("UGCLicenceNewCheck", "verifyLicence, exception is : " + e);
            a = false;
        }
        if (a) {
            String e2 = e();
            if (TextUtils.isEmpty(e2)) {
                a(-3);
                TXCLog.e("UGCLicenceNewCheck", "verifyLicence, decodeValue is empty!");
                return -3;
            }
            if (eVar != null) {
                eVar.a = e2;
            }
            try {
                JSONObject jSONObject = new JSONObject(e2);
                String string = jSONObject.getString("pituLicense");
                JSONArray optJSONArray = jSONObject.optJSONArray("appData");
                if (optJSONArray == null) {
                    TXCLog.e("UGCLicenceNewCheck", "verifyLicence, appDataArray is null!");
                    a(-1);
                    return -1;
                }
                int i;
                int i2 = 0;
                for (i = 0; i < optJSONArray.length(); i++) {
                    JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                    String optString = jSONObject2.optString(Constants.KEY_PACKAGE_NAME);
                    TXCLog.i("UGCLicenceNewCheck", "verifyLicence, packageName:" + optString);
                    if (optString.equals(this.a.getPackageName())) {
                        i2 = 1;
                        if (!d(jSONObject2.optString("endDate"))) {
                            a(jSONObject2, string);
                            i = 1;
                            i2 = 1;
                            break;
                        }
                    } else {
                        TXCLog.e("UGCLicenceNewCheck", "verifyLicence, packageName not match!");
                        i2 = 0;
                    }
                }
                i = i2;
                i2 = 0;
                if (i == 0) {
                    a(-4);
                    return -4;
                } else if (i2 == 0) {
                    a(-5);
                    return -5;
                } else {
                    if (!TextUtils.isEmpty(string)) {
                        byte[] decode = Base64.decode(string, 0);
                        File file = new File(this.a.getExternalFilesDir(null).getAbsolutePath() + File.separator + this.f);
                        g.a(file.getAbsolutePath(), decode);
                        TXCCommonUtil.setPituLicencePath(file.getAbsolutePath());
                    }
                    TXCDRApi.txReportDAU(this.a, a.aI);
                    return 0;
                }
            } catch (JSONException e3) {
                e3.printStackTrace();
                TXCLog.e("UGCLicenceNewCheck", "verifyLicence, json format error !");
                a(-1);
                return -1;
            }
        }
        a(-2);
        TXCLog.e("UGCLicenceNewCheck", "verifyLicence, signature not pass!");
        return -2;
    }

    private void a(JSONObject jSONObject, String str) {
        int optInt = jSONObject.optInt("feature");
        if (optInt <= 1) {
            if (TextUtils.isEmpty(str)) {
                this.m = 3;
            } else {
                this.m = 5;
            }
            TXCLog.i("UGCLicenceNewCheck", "parseVersionType, licence is old, mLicenceVersionType = " + this.m);
            return;
        }
        this.m = optInt & 15;
        if (this.m >= 5) {
            this.m = 5;
        }
        TXCLog.i("UGCLicenceNewCheck", "parseVersionType, mLicenceVersionType = " + this.m);
    }

    private void a(int i) {
        TXCDRApi.txReportDAU(this.a, a.aJ, i, "");
    }

    private boolean d(String str) {
        long c = c(str);
        if (c < 0) {
            TXCLog.e("UGCLicenceNewCheck", "checkEndDate, end date millis < 0!");
            return true;
        } else if (c >= System.currentTimeMillis()) {
            return false;
        } else {
            TXCLog.e("UGCLicenceNewCheck", "checkEndDate, end date expire!");
            return true;
        }
    }

    private int c(String str, e eVar) {
        String e = e(str);
        if (TextUtils.isEmpty(e)) {
            TXCLog.e("UGCLicenceNewCheck", "verifyOldLicence, decryptStr is empty");
            return -3;
        }
        if (eVar != null) {
            eVar.a = e;
        }
        try {
            JSONObject jSONObject = new JSONObject(e);
            if (!jSONObject.getString(ShareRequestParam.REQ_PARAM_PACKAGENAME).equals(a(this.a))) {
                TXCLog.e("UGCLicenceNewCheck", "packagename not match!");
                a(-4);
                return -4;
            } else if (d(jSONObject.getString("enddate"))) {
                return -5;
            } else {
                this.m = 5;
                TXCDRApi.txReportDAU(this.a, a.aI);
                return 0;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            TXCLog.e("UGCLicenceNewCheck", "verifyOldLicence, json format error !");
            a(-1);
            return -1;
        }
    }

    private static String a(Context context) {
        int myPid = Process.myPid();
        for (RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService(PushConstants.INTENT_ACTIVITY_NAME)).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    private String e(String str) {
        try {
            return new String(b.b(Base64.decode(str, 0), Base64.decode("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKfMXaF6wx9lev2U\nIzkk6ydI2sdaSQAD2ZvDBLq+5Fm6nGwSSWawl03D4vHcWIUa3wnz6f19/y8wzrj4\nnTfcEnT94SPdB6GhGsqPwbwRp9MHAqd/2gWZxSb005il2yiOZafk6X4NGKCn2tGd\nyNaCF+m9rLykuLdZHB0Z53ivgseNAgMBAAECgYAvXI2pAH+Goxwd6uwuOu9svTGT\nRzaHnI6VWmxBUZQeh3+TOW4iYAG03291GN6bY0RFCOWouSGH7lzK9NFbbPCAQ/hx\ncO48PqioHoq7K8sqzd3XaYBv39HrRnM8JvZsqv0PLJwX/LGm2y/MRaKAC6bcHtse\npgh+NNmUxXNRcTMRAQJBANezmenBcR8HTcY5YaEk3SQRzOo+QhIXuuD4T/FESpVJ\nmVQGxJjLsEBua1j38WG2QuepE5JiVbkQ0jQSvhUiZK0CQQDHJa+vWu6l72lQAvIx\nwmRISorvLb/tnu5bH0Ele42oX+w4p/tm03awdVjhVANnpDjYS2H6EzrF/pfis7k9\nV2phAkB4E4gz47bYYhV+qsTZkw70HGCpab0YG1OyFylRkwW983nCl/3rXUChrZZe\nsbATCAZYtfuqOsmju2R5DpH4a+wFAkBmHlcWbmSNxlSUaM5U4b+WqlLQDv+qE6Na\nKo63b8HWI0n4S3tI4QqttZ7b/L66OKXFk/Ir0AyFVuX/o/VLFTZBAkAdSTEkGwE5\nGQmhxu95sKxmdlUY6Q0Gwwpi06C1BPBrj2VkGXpBP0twhPVAq/3xVjjb+2KXVTUW\nIpRLc06M4vhv", 0)));
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.e("UGCLicenceNewCheck", "decryptLicenceStr, exception is : " + e);
            return null;
        }
    }

    private String f() {
        return g.b(new File(this.h + File.separator + this.e).getAbsolutePath());
    }

    public static boolean a(byte[] bArr, byte[] bArr2, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature instance = Signature.getInstance("SHA256WithRSA");
        instance.initVerify(publicKey);
        instance.update(bArr);
        return instance.verify(bArr2);
    }
}
