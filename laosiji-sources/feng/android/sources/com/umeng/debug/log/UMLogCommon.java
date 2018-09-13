package com.umeng.debug.log;

public class UMLogCommon {
    public static final String SC_10001 = ("不能在非主进程进行初始化|目前只能在主进程进行初始化，如何正确初始化请详见地址：" + UMLogUtils.makeUrl("67292"));
    public static final String SC_10002 = ("不能在非Application的onCreate方法中进行初始化|目前只能在Application的onCreate方法中进行初始化，如何正确初始化请详见地址：" + UMLogUtils.makeUrl("67292"));
    public static final String SC_10003 = "---->>>> init analytics is OK ~~";
    public static final String SC_10004 = "---->>>> init Push app key is OK ~~";
    public static final String SC_10005 = "---->>>> init Push channel is OK ~~";
    public static final String SC_10006 = "---->>>> init share appkey is OK ~~";
    public static final String SC_10007 = ("app key不能为空|您必须正确设置app key，如何正确初始化请详见地址：" + UMLogUtils.makeUrl("67292"));
    public static final String SC_10008 = "appkey is change !!!";
    public static final String SC_10009 = "---->>>> init push secret is OK ~~";
    public static final String SC_10010 = "---->>>> init um e is ok ~~";
    public static final String SC_10011 = "请注意：您init接口中设置的appkey是@，manifest中设置的appkey是#，init接口设置的appkey会覆盖manifest中设置的appkey";
}
