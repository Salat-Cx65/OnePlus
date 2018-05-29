package com.oneplus.lib.util.loading;

import android.app.Dialog;

public abstract class DialogLoadingAsyncTask<Param, Progress, Result> extends LoadingAsyncTask<Param, Progress, Result> {
    private Dialog mDialog;

    public DialogLoadingAsyncTask(Dialog dialog) {
        this.mDialog = dialog;
    }

    protected Object showProgree() {
        if (this.mDialog != null) {
            try {
                this.mDialog.show();
            } catch (Throwable th) {
            }
        }
        return this.mDialog;
    }

    protected void hideProgree(Object progreeView) {
        if (this.mDialog != null) {
            try {
                this.mDialog.dismiss();
            } catch (Throwable th) {
            }
        }
    }
}
