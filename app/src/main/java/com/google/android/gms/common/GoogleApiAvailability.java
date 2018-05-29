package com.google.android.gms.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;
import com.google.android.gms.R;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzs;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.util.zzk;
import com.google.android.gms.internal.zzbew;
import com.google.android.gms.internal.zzbex;
import com.google.android.gms.internal.zzbff;
import com.google.android.gms.internal.zzbfn;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class GoogleApiAvailability extends zze {
    public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
    public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE;
    private static final GoogleApiAvailability zzaAc;

    @SuppressLint({"HandlerLeak"})
    class zza extends Handler {
        private final Context mApplicationContext;

        public zza(Context context) {
            super(Looper.myLooper() == null ? Looper.getMainLooper() : Looper.myLooper());
            this.mApplicationContext = context.getApplicationContext();
        }

        public final void handleMessage(Message message) {
            switch (message.what) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    int isGooglePlayServicesAvailable = GoogleApiAvailability.this.isGooglePlayServicesAvailable(this.mApplicationContext);
                    if (GoogleApiAvailability.this.isUserResolvableError(isGooglePlayServicesAvailable)) {
                        GoogleApiAvailability.this.showErrorNotification(this.mApplicationContext, isGooglePlayServicesAvailable);
                    }
                default:
                    Log.w("GoogleApiAvailability", new StringBuilder(50).append("Don't know how to handle this message: ").append(message.what).toString());
            }
        }
    }

    static {
        zzaAc = new GoogleApiAvailability();
        GOOGLE_PLAY_SERVICES_VERSION_CODE = zze.GOOGLE_PLAY_SERVICES_VERSION_CODE;
    }

    GoogleApiAvailability() {
    }

    public static GoogleApiAvailability getInstance() {
        return zzaAc;
    }

    public static Dialog zza(Activity activity, OnCancelListener onCancelListener) {
        View progressBar = new ProgressBar(activity, null, 16842874);
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(0);
        Builder builder = new Builder(activity);
        builder.setView(progressBar);
        builder.setMessage(zzs.zzi(activity, ConnectionResult.SERVICE_UPDATING));
        builder.setPositiveButton(StringUtils.EMPTY_STRING, null);
        Dialog create = builder.create();
        zza(activity, create, "GooglePlayServicesUpdatingDialog", onCancelListener);
        return create;
    }

    static Dialog zza(Context context, int i, zzt com_google_android_gms_common_internal_zzt, OnCancelListener onCancelListener) {
        Builder builder = null;
        if (i == 0) {
            return null;
        }
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(16843529, typedValue, true);
        if ("Theme.Dialog.Alert".equals(context.getResources().getResourceEntryName(typedValue.resourceId))) {
            builder = new Builder(context, 5);
        }
        if (builder == null) {
            builder = new Builder(context);
        }
        builder.setMessage(zzs.zzi(context, i));
        if (onCancelListener != null) {
            builder.setOnCancelListener(onCancelListener);
        }
        CharSequence zzk = zzs.zzk(context, i);
        if (zzk != null) {
            builder.setPositiveButton(zzk, com_google_android_gms_common_internal_zzt);
        }
        zzk = zzs.zzg(context, i);
        if (zzk != null) {
            builder.setTitle(zzk);
        }
        return builder.create();
    }

    @Nullable
    public static zzbew zza(Context context, zzbex com_google_android_gms_internal_zzbex) {
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PACKAGE_ADDED");
        intentFilter.addDataScheme("package");
        BroadcastReceiver com_google_android_gms_internal_zzbew = new zzbew(com_google_android_gms_internal_zzbex);
        context.registerReceiver(com_google_android_gms_internal_zzbew, intentFilter);
        com_google_android_gms_internal_zzbew.setContext(context);
        if (zzo.zzy(context, GOOGLE_PLAY_SERVICES_PACKAGE)) {
            return com_google_android_gms_internal_zzbew;
        }
        com_google_android_gms_internal_zzbex.zzpy();
        com_google_android_gms_internal_zzbew.unregister();
        return null;
    }

    static void zza(Activity activity, Dialog dialog, String str, OnCancelListener onCancelListener) {
        if (activity instanceof FragmentActivity) {
            SupportErrorDialogFragment.newInstance(dialog, onCancelListener).show(((FragmentActivity) activity).getSupportFragmentManager(), str);
            return;
        }
        ErrorDialogFragment.newInstance(dialog, onCancelListener).show(activity.getFragmentManager(), str);
    }

    @TargetApi(20)
    private final void zza(Context context, int i, String str, PendingIntent pendingIntent) {
        if (i == 18) {
            zzar(context);
        } else if (pendingIntent != null) {
            Notification build;
            int i2;
            CharSequence zzh = zzs.zzh(context, i);
            CharSequence zzj = zzs.zzj(context, i);
            Resources resources = context.getResources();
            if (zzk.zzaH(context)) {
                zzbr.zzae(com.google.android.gms.common.util.zzs.zzsc());
                build = new Notification.Builder(context).setSmallIcon(context.getApplicationInfo().icon).setPriority(RainSurfaceView.RAIN_LEVEL_SHOWER).setAutoCancel(true).setContentTitle(zzh).setStyle(new BigTextStyle().bigText(zzj)).addAction(R.drawable.common_full_open_on_phone, resources.getString(R.string.common_open_on_phone), pendingIntent).build();
            } else {
                build = new NotificationCompat.Builder(context).setSmallIcon(17301642).setTicker(resources.getString(R.string.common_google_play_services_notification_ticker)).setWhen(System.currentTimeMillis()).setAutoCancel(true).setContentIntent(pendingIntent).setContentTitle(zzh).setContentText(zzj).setLocalOnly(true).setStyle(new NotificationCompat.BigTextStyle().bigText(zzj)).build();
            }
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    zzo.zzaAu.set(false);
                    i2 = 10436;
                    break;
                default:
                    i2 = 39789;
                    break;
            }
            ((NotificationManager) context.getSystemService("notification")).notify(i2, build);
        } else if (i == 6) {
            Log.w("GoogleApiAvailability", "Missing resolution for ConnectionResult.RESOLUTION_REQUIRED. Call GoogleApiAvailability#showErrorNotification(Context, ConnectionResult) instead.");
        }
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2) {
        return getErrorDialog(activity, i, i2, null);
    }

    public Dialog getErrorDialog(Activity activity, int i, int i2, OnCancelListener onCancelListener) {
        return zza((Context) activity, i, zzt.zza(activity, zze.zza(activity, i, "d"), i2), onCancelListener);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, int i, int i2) {
        return super.getErrorResolutionPendingIntent(context, i, i2);
    }

    @Nullable
    public PendingIntent getErrorResolutionPendingIntent(Context context, ConnectionResult connectionResult) {
        return connectionResult.hasResolution() ? connectionResult.getResolution() : getErrorResolutionPendingIntent(context, connectionResult.getErrorCode(), 0);
    }

    public final String getErrorString(int i) {
        return super.getErrorString(i);
    }

    @Nullable
    public String getOpenSourceSoftwareLicenseInfo(Context context) {
        return super.getOpenSourceSoftwareLicenseInfo(context);
    }

    public int isGooglePlayServicesAvailable(Context context) {
        return super.isGooglePlayServicesAvailable(context);
    }

    public final boolean isUserResolvableError(int i) {
        return super.isUserResolvableError(i);
    }

    @MainThread
    public Task<Void> makeGooglePlayServicesAvailable(Activity activity) {
        zzbr.zzcz("makeGooglePlayServicesAvailable must be called from the main thread");
        int isGooglePlayServicesAvailable = isGooglePlayServicesAvailable(activity);
        if (isGooglePlayServicesAvailable == 0) {
            return Tasks.forResult(null);
        }
        zzbfn zzp = zzbfn.zzp(activity);
        zzp.zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        return zzp.getTask();
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2) {
        return showErrorDialogFragment(activity, i, i2, null);
    }

    public boolean showErrorDialogFragment(Activity activity, int i, int i2, OnCancelListener onCancelListener) {
        Dialog errorDialog = getErrorDialog(activity, i, i2, onCancelListener);
        if (errorDialog == null) {
            return false;
        }
        zza(activity, errorDialog, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public void showErrorNotification(Context context, int i) {
        zza(context, i, null, zza(context, i, 0, "n"));
    }

    public void showErrorNotification(Context context, ConnectionResult connectionResult) {
        zza(context, connectionResult.getErrorCode(), null, getErrorResolutionPendingIntent(context, connectionResult));
    }

    public final boolean zza(Activity activity, @NonNull zzbff com_google_android_gms_internal_zzbff, int i, int i2, OnCancelListener onCancelListener) {
        Dialog zza = zza((Context) activity, i, zzt.zza(com_google_android_gms_internal_zzbff, zze.zza(activity, i, "d"), (int) RainSurfaceView.RAIN_LEVEL_SHOWER), onCancelListener);
        if (zza == null) {
            return false;
        }
        zza(activity, zza, GooglePlayServicesUtil.GMS_ERROR_DIALOG, onCancelListener);
        return true;
    }

    public final boolean zza(Context context, ConnectionResult connectionResult, int i) {
        PendingIntent errorResolutionPendingIntent = getErrorResolutionPendingIntent(context, connectionResult);
        if (errorResolutionPendingIntent == null) {
            return false;
        }
        zza(context, connectionResult.getErrorCode(), null, GoogleApiActivity.zza(context, errorResolutionPendingIntent, i));
        return true;
    }

    final void zzar(Context context) {
        new zza(context).sendEmptyMessageDelayed(1, 120000);
    }
}
