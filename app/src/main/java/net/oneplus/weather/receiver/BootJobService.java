package net.oneplus.weather.receiver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class BootJobService extends JobService {
    private static final String TAG;

    class AnonymousClass_1 extends Thread {
        final /* synthetic */ JobParameters val$jobParameters;

        AnonymousClass_1(JobParameters jobParameters) {
            this.val$jobParameters = jobParameters;
        }

        public void run() {
            AlarmReceiver.setAlarmClock(BootJobService.this);
            BootJobService.this.jobFinished(this.val$jobParameters, false);
            Log.d(TAG, "set alarm clock");
        }
    }

    static {
        TAG = BootJobService.class.getSimpleName();
    }

    public boolean onStartJob(JobParameters jobParameters) {
        new AnonymousClass_1(jobParameters).start();
        return true;
    }

    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
