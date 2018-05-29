package net.oneplus.weather.util;

import com.google.android.gms.common.ConnectionResult;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceUtils {
    private static ExecutorService mPool;

    static {
        mPool = null;
    }

    private ExecutorServiceUtils() {
    }

    public static synchronized ExecutorService getExecutorService() {
        ExecutorService executorService;
        synchronized (ExecutorServiceUtils.class) {
            if (mPool == null) {
                mPool = Executors.newFixedThreadPool(ConnectionResult.RESTRICTED_PROFILE);
            }
            executorService = mPool;
        }
        return executorService;
    }

    public static void execute(Runnable run) {
        getExecutorService();
        mPool.execute(run);
    }
}
