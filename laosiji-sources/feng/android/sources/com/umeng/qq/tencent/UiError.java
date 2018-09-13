package com.umeng.qq.tencent;

public class UiError {
    public int errorCode;
    public String errorDetail;
    public String errorMessage;

    public UiError(int var1, String var2, String var3) {
        this.errorMessage = var2;
        this.errorCode = var1;
        this.errorDetail = var3;
    }
}
