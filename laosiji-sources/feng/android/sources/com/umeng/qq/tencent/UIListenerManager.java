package com.umeng.qq.tencent;

import android.content.Intent;
import com.umeng.socialize.net.dplus.CommonNetImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class UIListenerManager {
    private static final String TAG = "openSDK_LOG.UIListenerManager";
    private static UIListenerManager mInstance = null;
    private Map<String, ApiTask> mListenerMap = Collections.synchronizedMap(new HashMap());

    public class ApiTask {
        public IUiListener mListener;
        public int mRequestCode;

        public ApiTask(int var2, IUiListener var3) {
            this.mRequestCode = var2;
            this.mListener = var3;
        }
    }

    public static UIListenerManager getInstance() {
        if (mInstance == null) {
            mInstance = new UIListenerManager();
        }
        return mInstance;
    }

    private UIListenerManager() {
        if (this.mListenerMap == null) {
            this.mListenerMap = Collections.synchronizedMap(new HashMap());
        }
    }

    public Object setListenerWithRequestcode(int var1, IUiListener var2) {
        String var3 = Wifig.a(var1);
        if (var3 == null) {
            return null;
        }
        ApiTask var4;
        Map var5 = this.mListenerMap;
        synchronized (this.mListenerMap) {
            var4 = (ApiTask) this.mListenerMap.put(var3, new ApiTask(var1, var2));
        }
        return var4 == null ? null : var4.mListener;
    }

    public Object setListnerWithAction(String var1, IUiListener var2) {
        int var3 = Wifig.a(var1);
        if (var3 == -1) {
            return null;
        }
        ApiTask var4;
        Map var5 = this.mListenerMap;
        synchronized (this.mListenerMap) {
            var4 = (ApiTask) this.mListenerMap.put(var1, new ApiTask(var3, var2));
        }
        return var4 == null ? null : var4.mListener;
    }

    public IUiListener getListnerWithRequestCode(int var1) {
        String var2 = Wifig.a(var1);
        if (var2 == null) {
            return null;
        }
        return getListnerWithAction(var2);
    }

    public IUiListener getListnerWithAction(String var1) {
        if (var1 == null) {
            return null;
        }
        ApiTask var2;
        Map var3 = this.mListenerMap;
        synchronized (this.mListenerMap) {
            var2 = (ApiTask) this.mListenerMap.get(var1);
            this.mListenerMap.remove(var1);
        }
        return var2 == null ? null : var2.mListener;
    }

    public void handleDataToListener(Intent var1, IUiListener var2) {
        if (var1 == null) {
            var2.onCancel();
            return;
        }
        String var3 = var1.getStringExtra("key_action");
        String var5;
        if ("action_login".equals(var3)) {
            int var4 = var1.getIntExtra("key_error_code", 0);
            if (var4 == 0) {
                var5 = var1.getStringExtra("key_response");
                if (var5 != null) {
                    try {
                        var2.onComplete(JsonUtil.d(var5));
                        return;
                    } catch (JSONException e) {
                        var2.onError(new UiError(-4, "服务器返回数据格式有误!", var5));
                        return;
                    }
                }
                var2.onComplete(new JSONObject());
                return;
            }
            var2.onError(new UiError(var4, var1.getStringExtra("key_error_msg"), var1.getStringExtra("key_error_detail")));
        } else if ("action_share".equals(var3)) {
            String var9 = var1.getStringExtra("result");
            var5 = var1.getStringExtra("response");
            if (CommonNetImpl.CANCEL.equals(var9)) {
                var2.onCancel();
            } else if ("error".equals(var9)) {
                var2.onError(new UiError(-6, "unknown error", var5 + ""));
            } else if ("complete".equals(var9)) {
                try {
                    String str;
                    if (var5 == null) {
                        str = "{\"ret\": 0}";
                    } else {
                        str = var5;
                    }
                    var2.onComplete(new JSONObject(str));
                } catch (JSONException var7) {
                    var7.printStackTrace();
                    var2.onError(new UiError(-4, "json error", var5 + ""));
                }
            }
        }
    }

    private IUiListener buildListener(int var1, IUiListener var2) {
        return var2;
    }

    public boolean onActivityResult(int var1, int var2, Intent var3, IUiListener var4) {
        IUiListener var5 = getListnerWithRequestCode(var1);
        if (var5 == null) {
            if (var4 == null) {
                return false;
            }
            var5 = buildListener(var1, var4);
        }
        if (var2 != -1) {
            var5.onCancel();
        } else if (var3 == null) {
            var5.onError(new UiError(-6, "onActivityResult intent data is null.", "onActivityResult intent data is null."));
            return true;
        } else {
            String var6 = var3.getStringExtra("key_action");
            int var7;
            String var8;
            if ("action_login".equals(var6)) {
                var7 = var3.getIntExtra("key_error_code", 0);
                if (var7 == 0) {
                    var8 = var3.getStringExtra("key_response");
                    if (var8 != null) {
                        try {
                            var5.onComplete(JsonUtil.d(var8));
                        } catch (JSONException e) {
                            var5.onError(new UiError(-4, "服务器返回数据格式有误!", var8));
                        }
                    } else {
                        var5.onComplete(new JSONObject());
                    }
                } else {
                    var5.onError(new UiError(var7, var3.getStringExtra("key_error_msg"), var3.getStringExtra("key_error_detail")));
                }
            } else if ("action_share".equals(var6)) {
                String var13 = var3.getStringExtra("result");
                var8 = var3.getStringExtra("response");
                if (CommonNetImpl.CANCEL.equals(var13)) {
                    var5.onCancel();
                } else if ("error".equals(var13)) {
                    var5.onError(new UiError(-6, "unknown error", var8 + ""));
                } else if ("complete".equals(var13)) {
                    try {
                        String str;
                        if (var8 == null) {
                            str = "{\"ret\": 0}";
                        } else {
                            str = var8;
                        }
                        var5.onComplete(new JSONObject(str));
                    } catch (JSONException var11) {
                        var11.printStackTrace();
                        var5.onError(new UiError(-4, "json error", var8 + ""));
                    }
                }
            } else {
                var7 = var3.getIntExtra("key_error_code", 0);
                if (var7 == 0) {
                    var8 = var3.getStringExtra("key_response");
                    if (var8 != null) {
                        try {
                            var5.onComplete(JsonUtil.d(var8));
                        } catch (JSONException e2) {
                            var5.onError(new UiError(-4, "服务器返回数据格式有误!", var8));
                        }
                    } else {
                        var5.onComplete(new JSONObject());
                    }
                } else {
                    var5.onError(new UiError(var7, var3.getStringExtra("key_error_msg"), var3.getStringExtra("key_error_detail")));
                }
            }
        }
        return true;
    }
}
