package com.umeng.commonsdk.statistics.idtracking;

import android.content.Context;
import android.content.SharedPreferences;
import com.umeng.commonsdk.framework.UMEnvelopeBuild;
import com.umeng.commonsdk.proguard.h;
import com.umeng.commonsdk.proguard.l;
import com.umeng.commonsdk.proguard.u;
import com.umeng.commonsdk.statistics.common.DataHelper;
import com.umeng.commonsdk.statistics.common.DeviceConfig;
import com.umeng.commonsdk.statistics.common.HelperUtils;
import com.umeng.commonsdk.statistics.common.b;
import com.umeng.commonsdk.statistics.internal.PreferenceWrapper;
import com.umeng.commonsdk.utils.UMUtils;
import java.io.File;
import org.json.JSONObject;

public class Envelope {
    private final int CODEX_ENCRYPT = 1;
    private final int CODEX_NORMAL = 0;
    private final byte[] SEED = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
    private boolean encrypt = false;
    private byte[] identity = null;
    private String mAddress = null;
    private byte[] mChecksum = null;
    private byte[] mEntity = null;
    private byte[] mGuid = null;
    private int mLength = 0;
    private int mSerialNo = 0;
    private byte[] mSignature = null;
    private int mTimestamp = 0;
    private String mVersion = "1.0";

    private Envelope(byte[] bArr, String str, byte[] bArr2) throws Exception {
        if (bArr == null || bArr.length == 0) {
            throw new Exception("entity is null or empty");
        }
        this.mAddress = str;
        this.mLength = bArr.length;
        this.mEntity = b.a(bArr);
        this.mTimestamp = (int) (System.currentTimeMillis() / 1000);
        this.identity = bArr2;
    }

    public static String getSignature(Context context) {
        SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
        if (sharedPreferences == null) {
            return null;
        }
        return sharedPreferences.getString("signature", null);
    }

    public void setSignature(String str) {
        this.mSignature = DataHelper.reverseHexString(str);
    }

    public String getSignature() {
        return DataHelper.toHexString(this.mSignature);
    }

    public void setSerialNumber(int i) {
        this.mSerialNo = i;
    }

    public void setEncrypt(boolean z) {
        this.encrypt = z;
    }

    public static Envelope genEnvelope(Context context, String str, byte[] bArr) {
        try {
            String mac = DeviceConfig.getMac(context);
            String deviceId = DeviceConfig.getDeviceId(context);
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            String string = sharedPreferences.getString("signature", null);
            int i = sharedPreferences.getInt("serial", 1);
            Envelope envelope = new Envelope(bArr, str, (deviceId + mac).getBytes());
            envelope.setSignature(string);
            envelope.setSerialNumber(i);
            envelope.seal();
            sharedPreferences.edit().putInt("serial", i + 1).putString("signature", envelope.getSignature()).commit();
            envelope.export(context);
            return envelope;
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            return null;
        }
    }

    public static Envelope genEncryptEnvelope(Context context, String str, byte[] bArr) {
        try {
            String mac = DeviceConfig.getMac(context);
            String deviceId = DeviceConfig.getDeviceId(context);
            SharedPreferences sharedPreferences = PreferenceWrapper.getDefault(context);
            String string = sharedPreferences.getString("signature", null);
            int i = sharedPreferences.getInt("serial", 1);
            Envelope envelope = new Envelope(bArr, str, (deviceId + mac).getBytes());
            envelope.setEncrypt(true);
            envelope.setSignature(string);
            envelope.setSerialNumber(i);
            envelope.seal();
            sharedPreferences.edit().putInt("serial", i + 1).putString("signature", envelope.getSignature()).commit();
            envelope.export(context);
            return envelope;
        } catch (Throwable e) {
            com.umeng.commonsdk.proguard.b.a(context, e);
            return null;
        }
    }

    public void seal() {
        if (this.mSignature == null) {
            this.mSignature = genSignature();
        }
        if (this.encrypt) {
            Object obj = new byte[16];
            try {
                System.arraycopy(this.mSignature, 1, obj, 0, 16);
                this.mEntity = DataHelper.encrypt(this.mEntity, obj);
            } catch (Exception e) {
            }
        }
        this.mGuid = genGuid(this.mSignature, this.mTimestamp);
        this.mChecksum = genCheckSum();
    }

    private byte[] genGuid(byte[] bArr, int i) {
        int i2;
        int i3 = 0;
        byte[] hash = DataHelper.hash(this.identity);
        byte[] hash2 = DataHelper.hash(this.mEntity);
        int length = hash.length;
        byte[] bArr2 = new byte[(length * 2)];
        for (i2 = 0; i2 < length; i2++) {
            bArr2[i2 * 2] = hash2[i2];
            bArr2[(i2 * 2) + 1] = hash[i2];
        }
        for (i2 = 0; i2 < 2; i2++) {
            bArr2[i2] = bArr[i2];
            bArr2[(bArr2.length - i2) - 1] = bArr[(bArr.length - i2) - 1];
        }
        byte[] bArr3 = new byte[]{(byte) (i & 255), (byte) ((i >> 8) & 255), (byte) ((i >> 16) & 255), (byte) (i >>> 24)};
        while (i3 < bArr2.length) {
            bArr2[i3] = (byte) (bArr2[i3] ^ bArr3[i3 % 4]);
            i3++;
        }
        return bArr2;
    }

    private byte[] genSignature() {
        return genGuid(this.SEED, (int) (System.currentTimeMillis() / 1000));
    }

    private byte[] genCheckSum() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DataHelper.toHexString(this.mSignature));
        stringBuilder.append(this.mSerialNo);
        stringBuilder.append(this.mTimestamp);
        stringBuilder.append(this.mLength);
        stringBuilder.append(DataHelper.toHexString(this.mGuid));
        return DataHelper.hash(stringBuilder.toString().getBytes());
    }

    public byte[] toBinary() {
        l hVar = new h();
        hVar.a(this.mVersion);
        hVar.b(this.mAddress);
        hVar.c(DataHelper.toHexString(this.mSignature));
        hVar.a(this.mSerialNo);
        hVar.b(this.mTimestamp);
        hVar.c(this.mLength);
        hVar.a(this.mEntity);
        hVar.d(this.encrypt ? 1 : 0);
        hVar.d(DataHelper.toHexString(this.mGuid));
        hVar.e(DataHelper.toHexString(this.mChecksum));
        try {
            return new u().a(hVar);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void export(Context context) {
        String str = this.mAddress;
        String imprintProperty = UMEnvelopeBuild.imprintProperty(context, "umid", null);
        String toHexString = DataHelper.toHexString(this.mSignature);
        Object obj = new byte[16];
        System.arraycopy(this.mSignature, 2, obj, 0, 16);
        String toHexString2 = DataHelper.toHexString(DataHelper.hash(obj));
        try {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("appkey", str);
            if (imprintProperty != null) {
                jSONObject.put("umid", imprintProperty);
            }
            jSONObject.put("signature", toHexString);
            jSONObject.put("checksum", toHexString2);
            File file = new File(context.getFilesDir(), ".umeng");
            if (!file.exists()) {
                file.mkdir();
            }
            HelperUtils.writeFile(new File(file, "exchangeIdentity.json"), jSONObject.toString());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        try {
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("appkey", str);
            jSONObject2.put("channel", UMUtils.getChannel(context));
            if (imprintProperty != null) {
                jSONObject2.put("umid", HelperUtils.getUmengMD5(imprintProperty));
            }
            HelperUtils.writeFile(new File(context.getFilesDir(), "exid.dat"), jSONObject2.toString());
        } catch (Throwable th2) {
            th2.printStackTrace();
        }
    }

    public String toString() {
        int i = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("version : %s\n", new Object[]{this.mVersion}));
        stringBuilder.append(String.format("address : %s\n", new Object[]{this.mAddress}));
        stringBuilder.append(String.format("signature : %s\n", new Object[]{DataHelper.toHexString(this.mSignature)}));
        stringBuilder.append(String.format("serial : %s\n", new Object[]{Integer.valueOf(this.mSerialNo)}));
        stringBuilder.append(String.format("timestamp : %d\n", new Object[]{Integer.valueOf(this.mTimestamp)}));
        stringBuilder.append(String.format("length : %d\n", new Object[]{Integer.valueOf(this.mLength)}));
        stringBuilder.append(String.format("guid : %s\n", new Object[]{DataHelper.toHexString(this.mGuid)}));
        stringBuilder.append(String.format("checksum : %s ", new Object[]{DataHelper.toHexString(this.mChecksum)}));
        String str = "codex : %d";
        Object[] objArr = new Object[1];
        if (!this.encrypt) {
            i = 0;
        }
        objArr[0] = Integer.valueOf(i);
        stringBuilder.append(String.format(str, objArr));
        return stringBuilder.toString();
    }
}
