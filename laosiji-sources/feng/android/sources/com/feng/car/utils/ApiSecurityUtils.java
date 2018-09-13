package com.feng.car.utils;

import android.util.Base64;
import com.feng.car.entity.model.ApiSecurityInfo;
import com.feng.library.utils.Md5Utils;
import com.feng.library.utils.StringUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class ApiSecurityUtils {
    private static final int _afterPwd = 2;
    private static final int _beforePwd = 3;
    private static final List<ApiSecurityInfo> _listApiPwdSecurity = new ArrayList();
    private static Random random = new Random();

    private static int getRand(int intMin, int intMax) {
        return (random.nextInt(intMax) % ((intMax - intMin) + 1)) + intMin;
    }

    private static void initData() {
        if (_listApiPwdSecurity.size() != 2) {
            _listApiPwdSecurity.add(new ApiSecurityInfo(2, 1));
            _listApiPwdSecurity.add(new ApiSecurityInfo(3, 2));
        }
    }

    public static String encryptUserPwd(String appPwd) {
        initData();
        return encrypt(appPwd, _listApiPwdSecurity, 3, 2);
    }

    private static String encrypt(String strOriginal, List<ApiSecurityInfo> list, int intBefore, int intAfter) {
        String strResult = "";
        if (StringUtil.isEmpty(strOriginal)) {
            return strResult;
        }
        String strToken = Md5Utils.md5(UUID.randomUUID().toString());
        return encrypt(intBefore, intAfter, String.format("%s_%s", new Object[]{strOriginal, strToken}), (List) list);
    }

    private static String encrypt(int intBefore, int intAfter, String strOriginal, List<ApiSecurityInfo> list) {
        String strResult = "";
        if (StringUtil.isEmpty(strOriginal)) {
            return strResult;
        }
        strResult = strOriginal;
        for (ApiSecurityInfo info : list) {
            strResult = encrypt(strResult, info.interval, info.digit, intBefore, intAfter);
        }
        return strResult;
    }

    private static String encrypt(String strOriginal, int intInterval, int intInsertDigit, int intBefore, int intAfter) {
        int i;
        String strResult = "";
        for (i = 0; i < strOriginal.length(); i++) {
            strResult = strResult + strOriginal.charAt(i);
            if ((i + 1) % intInterval == 0) {
                for (int j = 0; j < intInsertDigit; j++) {
                    strResult = strResult + strOriginal.charAt(getRand(0, strOriginal.length()));
                }
            }
        }
        for (i = 0; i < intBefore; i++) {
            strResult = strResult.charAt(getRand(0, strResult.length())) + strResult;
        }
        for (i = 0; i < intAfter; i++) {
            strResult = strResult + strResult.charAt(getRand(0, strResult.length()));
        }
        return Base64.encodeToString(strResult.getBytes(), 0);
    }
}
