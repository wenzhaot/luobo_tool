package anet.channel.appmonitor;

import anet.channel.statist.AlarmObject;
import anet.channel.statist.CountObject;
import anet.channel.statist.StatObject;

/* compiled from: Taobao */
final class a implements IAppMonitor {
    final /* synthetic */ IAppMonitor a;

    a(IAppMonitor iAppMonitor) {
        this.a = iAppMonitor;
    }

    @Deprecated
    public void register() {
    }

    @Deprecated
    public void register(Class<?> cls) {
    }

    public void commitStat(StatObject statObject) {
        if (this.a != null) {
            this.a.commitStat(statObject);
        }
    }

    public void commitAlarm(AlarmObject alarmObject) {
        if (this.a != null) {
            this.a.commitAlarm(alarmObject);
        }
    }

    public void commitCount(CountObject countObject) {
        if (this.a != null) {
            this.a.commitCount(countObject);
        }
    }
}
