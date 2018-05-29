package net.oneplus.weather.receiver;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AlarmUpdateJob extends JobService {
    public static final int JOB_MESSAGE_WHAT = Integer.MIN_VALUE;
    private static final String TAG;
    private Handler mHandler;

    class AnonymousClass_1 extends Handler {
        final /* synthetic */ JobParameters val$params;

        AnonymousClass_1(JobParameters jobParameters) {
            this.val$params = jobParameters;
        }

        public void handleMessage(Message msg) {
            if (msg.what == Integer.MIN_VALUE) {
                AlarmUpdateJob.this.jobFinished(this.val$params, false);
                Log.d(TAG, "jobFinished");
            }
        }
    }

    static {
        TAG = AlarmUpdateJob.class.getSimpleName();
    }

    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        this.mHandler = new AnonymousClass_1(params);
        AlarmReceiver.getInstance().updateWarning(this, this.mHandler);
        return true;
    }

    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");
        this.mHandler.removeCallbacksAndMessages(null);
        return true;
    }
}
