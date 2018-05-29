package com.oneplus.lib.util.loading;

import android.os.AsyncTask;
import com.oneplus.lib.util.loading.LoadingHelper.FinishShowCallback;

public abstract class LoadingAsyncTask<Param, Progress, Result> extends AsyncTask<Param, Progress, Result> {
    private LoadingHelper mProgressHelper;

    class AnonymousClass_2 implements FinishShowCallback {
        final /* synthetic */ Object val$result;

        AnonymousClass_2(Object obj) {
            this.val$result = obj;
        }

        public void finish(boolean shown) {
            if (!LoadingAsyncTask.this.isCancelled()) {
                LoadingAsyncTask.this.onPostExecuteExtend(this.val$result);
            }
        }
    }

    protected abstract void hideProgree(Object obj);

    protected abstract Object showProgree();

    public LoadingAsyncTask() {
        this.mProgressHelper = new LoadingHelper() {
            protected Object showProgree() {
                return LoadingAsyncTask.this.showProgree();
            }

            protected void hideProgree(Object progreeView) {
                LoadingAsyncTask.this.hideProgree(progreeView);
            }
        };
    }

    public LoadingAsyncTask<Param, Progress, Result> setWillShowProgreeTime(long willShowProgreeTime) {
        this.mProgressHelper.setWillShowProgreeTime(willShowProgreeTime);
        return this;
    }

    public LoadingAsyncTask<Param, Progress, Result> setProgreeMinShowTime(long progreeMinShowTime) {
        this.mProgressHelper.setProgreeMinShowTime(progreeMinShowTime);
        return this;
    }

    protected void onPreExecuteExtend() {
    }

    protected void onPostExecuteExtend(Result result) {
    }

    protected void onCancelledExtend(Result result) {
    }

    protected final void onPreExecute() {
        this.mProgressHelper.beginShowProgress();
        onPreExecuteExtend();
    }

    protected final void onPostExecute(Result result) {
        onFinish(result);
    }

    protected final void onCancelled(Result result) {
        onFinish(result);
        onCancelledExtend(result);
    }

    protected final void onCancelled() {
        super.onCancelled();
    }

    private void onFinish(Result result) {
        this.mProgressHelper.finishShowProgress(new AnonymousClass_2(result));
    }
}
