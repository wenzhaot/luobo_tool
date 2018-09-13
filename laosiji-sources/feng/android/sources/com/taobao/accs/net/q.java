package com.taobao.accs.net;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import com.taobao.accs.internal.AccsJobService;

/* compiled from: Taobao */
public class q extends g {
    public static final int DEAMON_JOB_ID = 2051;
    public static final int HB_JOB_ID = 2050;

    protected q(Context context) {
        super(context);
    }

    protected void a(int i) {
        ((JobScheduler) this.a.getSystemService("jobscheduler")).schedule(new Builder(2050, new ComponentName(this.a.getPackageName(), AccsJobService.class.getName())).setMinimumLatency((long) (i * 1000)).setOverrideDeadline((long) (i * 1000)).setRequiredNetworkType(1).build());
    }
}
