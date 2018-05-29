package com.oneplus.lib.util.loading;

import android.view.View;
import com.google.android.gms.location.DetectedActivity;

public class ViewLoadingHelper extends LoadingHelper {
    View mProgressView;

    public ViewLoadingHelper(View progressView) {
        this.mProgressView = progressView;
    }

    protected Object showProgree() {
        if (this.mProgressView != null) {
            this.mProgressView.setVisibility(0);
        }
        return this.mProgressView;
    }

    protected void hideProgree(Object progreeView) {
        if (this.mProgressView != null) {
            this.mProgressView.setVisibility(DetectedActivity.RUNNING);
        }
    }
}
