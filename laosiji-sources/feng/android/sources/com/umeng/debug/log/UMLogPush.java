package com.umeng.debug.log;

public class UMLogPush {
    public static final String P_10000 = ("无法获取到device token|注册不成功，无法获取到device token，提示accs bind error，或者TNET_LOAD_SO_FAIL。详见问题链接 " + UMLogUtils.makeUrl("67080"));
    public static final String P_10001 = ("Toast提示注册有误|Toast提示mPushAgent.register should be called in both main process and channel process。详见问题链接 " + UMLogUtils.makeUrl("67140"));
    public static final String P_10006 = ("显示送达却没有弹出通知|后台显示送达，却没有弹出通知。详见问题链接 " + UMLogUtils.makeUrl("67146"));
}