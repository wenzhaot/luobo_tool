package anet.channel.statist;

/* compiled from: Taobao */
public class AlarmObject {
    public String arg;
    public String errorCode;
    public String errorMsg;
    public boolean isSuccess;
    public String module;
    public String modulePoint;

    public String toString() {
        return "[module:" + this.module + " modulePoint:" + this.modulePoint + " arg:" + this.arg + " isSuccess:" + this.isSuccess + " errorCode:" + this.errorCode + "]";
    }
}
