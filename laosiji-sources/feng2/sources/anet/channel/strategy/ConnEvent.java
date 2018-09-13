package anet.channel.strategy;

import com.facebook.common.time.Clock;

/* compiled from: Taobao */
public class ConnEvent {
    public long connTime = Clock.MAX_TIME;
    public boolean isSuccess = false;

    public String toString() {
        return this.isSuccess ? "ConnEvent#Success" : "ConnEvent#Fail";
    }
}
