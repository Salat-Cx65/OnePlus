package com.oneplus.lib.util.loading;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import com.oneplus.lib.util.loading.LoadingHelper.FinishShowCallback;

public abstract class LoadingHelper {
    private static final long PROMPT_MIN_SHOW_TIME_DEFAULT = 500;
    private static final long WILL_SHOW_PROMPT_TIME_DEFAULT = 300;
    private static Handler mHandler;
    private long mProgreeMinShowTime;
    private Object mProgreeView;
    private Runnable mShowProgreeRunnable;
    private long mShowProgreeTime;
    private long mWillShowProgreeTime;

    class AnonymousClass_2 implements Runnable {
        final /* synthetic */ FinishShowCallback val$callback;

        AnonymousClass_2(FinishShowCallback finishShowCallback) {
            this.val$callback = finishShowCallback;
        }

        public void run() {
            LoadingHelper.this.doFinish(this.val$callback, true);
        }
    }

    class AnonymousClass_3 implements Runnable {
        final /* synthetic */ FinishShowCallback val$callback;
        final /* synthetic */ boolean val$shown;

        AnonymousClass_3(boolean z, FinishShowCallback finishShowCallback) {
            this.val$shown = z;
            this.val$callback = finishShowCallback;
        }

        public void run() {
            if (this.val$shown) {
                LoadingHelper.this.hideProgree(LoadingHelper.this.mProgreeView);
            }
            if (this.val$callback != null) {
                this.val$callback.finish(true);
            }
        }
    }

    public static interface FinishShowCallback {
        void finish(boolean z);
    }

    protected abstract void hideProgree(Object obj);

    protected abstract Object showProgree();

    public LoadingHelper() {
        this.mWillShowProgreeTime = 300;
        this.mProgreeMinShowTime = 500;
    }

    static {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public LoadingHelper setWillShowProgreeTime(long willShowProgreeTime) {
        this.mWillShowProgreeTime = willShowProgreeTime;
        return this;
    }

    public LoadingHelper setProgreeMinShowTime(long progreeMinShowTime) {
        this.mProgreeMinShowTime = progreeMinShowTime;
        return this;
    }

    public void beginShowProgress() {
        this.mShowProgreeRunnable = new Runnable() {
            public void run() {
                LoadingHelper.this.mShowProgreeRunnable = null;
                LoadingHelper.this.mProgreeView = LoadingHelper.this.showProgree();
                LoadingHelper.this.mShowProgreeTime = SystemClock.elapsedRealtime();
            }
        };
        mHandler.postDelayed(this.mShowProgreeRunnable, this.mWillShowProgreeTime);
    }

    public void finishShowProgress(FinishShowCallback callback) {
        if (this.mShowProgreeRunnable != null) {
            mHandler.removeCallbacks(this.mShowProgreeRunnable);
            doFinish(callback, false);
            return;
        }
        long remainShowTime = this.mProgreeMinShowTime - (SystemClock.elapsedRealtime() - this.mShowProgreeTime);
        if (remainShowTime > 0) {
            mHandler.postDelayed(new AnonymousClass_2(callback), remainShowTime);
        } else {
            doFinish(callback, true);
        }
    }

    private void doFinish(FinishShowCallback callback, boolean shown) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            if (shown) {
                hideProgree(this.mProgreeView);
            }
            if (callback != null) {
                callback.finish(true);
                return;
            }
            return;
        }
        mHandler.post(new AnonymousClass_3(shown, callback));
    }
}
