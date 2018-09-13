package com.umeng.qq.tencent;

public interface IUiListener {
    void onCancel();

    void onComplete(Object obj);

    void onError(UiError uiError);
}
