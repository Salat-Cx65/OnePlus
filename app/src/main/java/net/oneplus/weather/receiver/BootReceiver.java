package net.oneplus.weather.receiver;

import android.app.job.JobInfo.Builder;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {
    private static final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";
    private static final String TAG = "BootReceiver";

    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (!TextUtils.isEmpty(action) && ACTION_BOOT.equals(action)) {
            Log.d(TAG, "boot complete");
            JobScheduler tm = (JobScheduler) context.getSystemService("jobscheduler");
            Builder builder = new Builder(1, new ComponentName(context, BootJobService.class));
            builder.setMinimumLatency(1000);
            builder.setOverrideDeadline(5000);
            builder.setRequiredNetworkType(1);
            tm.schedule(builder.build());
        }
    }
}
