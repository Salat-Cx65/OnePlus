package com.oneplus.lib.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.Log;
import com.oneplus.lib.util.AppUtils;

@SuppressLint({"Registered"})
public class OneplusApplication extends Application {
    private static final String TAG;
    private static OneplusApplication instance;

    static {
        TAG = OneplusApplication.class.getSimpleName();
    }

    public static Context getContext() {
        return instance;
    }

    public void onCreate() {
        super.onCreate();
        instance = this;
        if (AppUtils.versionCodeChanged(this)) {
            onVersionChanged(AppUtils.getPrevVersion(this), AppUtils.getCurrentVersion(this));
            AppUtils.setCurrentVersion(this);
        }
    }

    @CallSuper
    protected void onVersionChanged(int previousCode, int currentCode) {
        Log.i(TAG, getPackageName() + " previousCode is: " + previousCode);
        Log.i(TAG, getPackageName() + " currentCode is: " + currentCode);
    }
}
