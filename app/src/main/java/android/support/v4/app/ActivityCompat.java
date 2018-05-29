package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.IntentSender.SendIntentException;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.RestrictTo.Scope;
import android.support.v4.app.ActivityCompat.OnRequestPermissionsResultCallback;
import android.support.v4.app.SharedElementCallback.OnSharedElementsReadyListener;
import android.support.v4.content.ContextCompat;
import android.view.View;
import java.util.List;
import java.util.Map;

public class ActivityCompat extends ContextCompat {

    static class AnonymousClass_1 implements Runnable {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ String[] val$permissions;
        final /* synthetic */ int val$requestCode;

        AnonymousClass_1(String[] strArr, Activity activity, int i) {
            this.val$permissions = strArr;
            this.val$activity = activity;
            this.val$requestCode = i;
        }

        public void run() {
            int[] grantResults = new int[this.val$permissions.length];
            PackageManager packageManager = this.val$activity.getPackageManager();
            String packageName = this.val$activity.getPackageName();
            int permissionCount = this.val$permissions.length;
            for (int i = 0; i < permissionCount; i++) {
                grantResults[i] = packageManager.checkPermission(this.val$permissions[i], packageName);
            }
            ((OnRequestPermissionsResultCallback) this.val$activity).onRequestPermissionsResult(this.val$requestCode, this.val$permissions, grantResults);
        }
    }

    public static interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr);
    }

    @RestrictTo({Scope.LIBRARY_GROUP})
    public static interface RequestPermissionsRequestCodeValidator {
        void validateRequestPermissionsRequestCode(int i);
    }

    @RequiresApi(21)
    private static class SharedElementCallback21Impl extends SharedElementCallback {
        protected SharedElementCallback mCallback;

        public SharedElementCallback21Impl(SharedElementCallback callback) {
            this.mCallback = callback;
        }

        public void onSharedElementStart(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementStart(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onSharedElementEnd(List<String> sharedElementNames, List<View> sharedElements, List<View> sharedElementSnapshots) {
            this.mCallback.onSharedElementEnd(sharedElementNames, sharedElements, sharedElementSnapshots);
        }

        public void onRejectSharedElements(List<View> rejectedSharedElements) {
            this.mCallback.onRejectSharedElements(rejectedSharedElements);
        }

        public void onMapSharedElements(List<String> names, Map<String, View> sharedElements) {
            this.mCallback.onMapSharedElements(names, sharedElements);
        }

        public Parcelable onCaptureSharedElementSnapshot(View sharedElement, Matrix viewToGlobalMatrix, RectF screenBounds) {
            return this.mCallback.onCaptureSharedElementSnapshot(sharedElement, viewToGlobalMatrix, screenBounds);
        }

        public View onCreateSnapshotView(Context context, Parcelable snapshot) {
            return this.mCallback.onCreateSnapshotView(context, snapshot);
        }
    }

    @RequiresApi(23)
    private static class SharedElementCallback23Impl extends SharedElementCallback21Impl {

        class AnonymousClass_1 implements OnSharedElementsReadyListener {
            final /* synthetic */ SharedElementCallback.OnSharedElementsReadyListener val$listener;

            AnonymousClass_1(SharedElementCallback.OnSharedElementsReadyListener onSharedElementsReadyListener) {
                this.val$listener = onSharedElementsReadyListener;
            }

            public void onSharedElementsReady() {
                this.val$listener.onSharedElementsReady();
            }
        }

        public SharedElementCallback23Impl(SharedElementCallback callback) {
            super(callback);
        }

        public void onSharedElementsArrived(List<String> sharedElementNames, List<View> sharedElements, SharedElementCallback.OnSharedElementsReadyListener listener) {
            this.mCallback.onSharedElementsArrived(sharedElementNames, sharedElements, new AnonymousClass_1(listener));
        }
    }

    protected ActivityCompat() {
    }

    public static boolean invalidateOptionsMenu(Activity activity) {
        activity.invalidateOptionsMenu();
        return true;
    }

    public static void startActivityForResult(Activity activity, Intent intent, int requestCode, @Nullable Bundle options) {
        if (VERSION.SDK_INT >= 16) {
            activity.startActivityForResult(intent, requestCode, options);
        } else {
            activity.startActivityForResult(intent, requestCode);
        }
    }

    public static void startIntentSenderForResult(Activity activity, IntentSender intent, int requestCode, Intent fillInIntent, int flagsMask, int flagsValues, int extraFlags, @Nullable Bundle options) throws SendIntentException {
        if (VERSION.SDK_INT >= 16) {
            activity.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags, options);
        } else {
            activity.startIntentSenderForResult(intent, requestCode, fillInIntent, flagsMask, flagsValues, extraFlags);
        }
    }

    public static void finishAffinity(Activity activity) {
        if (VERSION.SDK_INT >= 16) {
            activity.finishAffinity();
        } else {
            activity.finish();
        }
    }

    public static void finishAfterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.finishAfterTransition();
        } else {
            activity.finish();
        }
    }

    @Nullable
    public static Uri getReferrer(Activity activity) {
        if (VERSION.SDK_INT >= 22) {
            return activity.getReferrer();
        }
        Intent intent = activity.getIntent();
        Uri referrer = (Uri) intent.getParcelableExtra("android.intent.extra.REFERRER");
        if (referrer != null) {
            return referrer;
        }
        String referrerName = intent.getStringExtra("android.intent.extra.REFERRER_NAME");
        return referrerName != null ? Uri.parse(referrerName) : null;
    }

    public static void setEnterSharedElementCallback(Activity activity, SharedElementCallback callback) {
        SharedElementCallback frameworkCallback = null;
        if (VERSION.SDK_INT >= 23) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback23Impl(callback);
            }
            activity.setEnterSharedElementCallback(frameworkCallback);
        } else if (VERSION.SDK_INT >= 21) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback21Impl(callback);
            }
            activity.setEnterSharedElementCallback(frameworkCallback);
        }
    }

    public static void setExitSharedElementCallback(Activity activity, SharedElementCallback callback) {
        SharedElementCallback frameworkCallback = null;
        if (VERSION.SDK_INT >= 23) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback23Impl(callback);
            }
            activity.setExitSharedElementCallback(frameworkCallback);
        } else if (VERSION.SDK_INT >= 21) {
            if (callback != null) {
                frameworkCallback = new SharedElementCallback21Impl(callback);
            }
            activity.setExitSharedElementCallback(frameworkCallback);
        }
    }

    public static void postponeEnterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.postponeEnterTransition();
        }
    }

    public static void startPostponedEnterTransition(Activity activity) {
        if (VERSION.SDK_INT >= 21) {
            activity.startPostponedEnterTransition();
        }
    }

    public static void requestPermissions(@NonNull Activity activity, @NonNull String[] permissions, @IntRange(from = 0) int requestCode) {
        if (VERSION.SDK_INT >= 23) {
            if (activity instanceof RequestPermissionsRequestCodeValidator) {
                ((RequestPermissionsRequestCodeValidator) activity).validateRequestPermissionsRequestCode(requestCode);
            }
            activity.requestPermissions(permissions, requestCode);
        } else if (activity instanceof OnRequestPermissionsResultCallback) {
            new Handler(Looper.getMainLooper()).post(new AnonymousClass_1(permissions, activity, requestCode));
        }
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity activity, @NonNull String permission) {
        return VERSION.SDK_INT >= 23 ? activity.shouldShowRequestPermissionRationale(permission) : false;
    }
}
