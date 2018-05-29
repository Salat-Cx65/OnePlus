package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import java.util.concurrent.atomic.AtomicReference;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzbcm extends zzbfe implements OnCancelListener {
    protected volatile boolean mStarted;
    protected final AtomicReference<zzbcn> zzaBP;
    private final Handler zzaBQ;
    protected final GoogleApiAvailability zzaBf;

    protected zzbcm(zzbff com_google_android_gms_internal_zzbff) {
        this(com_google_android_gms_internal_zzbff, GoogleApiAvailability.getInstance());
    }

    private zzbcm(zzbff com_google_android_gms_internal_zzbff, GoogleApiAvailability googleApiAvailability) {
        super(com_google_android_gms_internal_zzbff);
        this.zzaBP = new AtomicReference(null);
        this.zzaBQ = new Handler(Looper.getMainLooper());
        this.zzaBf = googleApiAvailability;
    }

    private static int zza(@Nullable zzbcn com_google_android_gms_internal_zzbcn) {
        return com_google_android_gms_internal_zzbcn == null ? -1 : com_google_android_gms_internal_zzbcn.zzpw();
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        Object obj;
        int i3 = ConnectionResult.CANCELED;
        zzbcn com_google_android_gms_internal_zzbcn = (zzbcn) this.zzaBP.get();
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                if (i2 == -1) {
                    i3 = 1;
                } else {
                    if (i2 == 0) {
                        if (intent != null) {
                            i3 = intent.getIntExtra("<<ResolutionFailureErrorDetail>>", ConnectionResult.CANCELED);
                        }
                        zzbcn com_google_android_gms_internal_zzbcn2 = new zzbcn(new ConnectionResult(i3, null), zza(com_google_android_gms_internal_zzbcn));
                        this.zzaBP.set(com_google_android_gms_internal_zzbcn2);
                        com_google_android_gms_internal_zzbcn = com_google_android_gms_internal_zzbcn2;
                        obj = null;
                    }
                    obj = null;
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                int isGooglePlayServicesAvailable = this.zzaBf.isGooglePlayServicesAvailable(getActivity());
                if (isGooglePlayServicesAvailable == 0) {
                    i3 = 1;
                } else {
                    obj = null;
                }
                if (com_google_android_gms_internal_zzbcn == null) {
                    return;
                }
                if (com_google_android_gms_internal_zzbcn.zzpx().getErrorCode() == 18 && isGooglePlayServicesAvailable == 18) {
                    return;
                }
            default:
                obj = null;
                break;
        }
        if (obj != null) {
            zzpv();
        } else if (com_google_android_gms_internal_zzbcn != null) {
            zza(com_google_android_gms_internal_zzbcn.zzpx(), com_google_android_gms_internal_zzbcn.zzpw());
        }
    }

    public void onCancel(DialogInterface dialogInterface) {
        zza(new ConnectionResult(13, null), zza((zzbcn) this.zzaBP.get()));
        zzpv();
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.zzaBP.set(bundle.getBoolean("resolving_error", false) ? new zzbcn(new ConnectionResult(bundle.getInt("failed_status"), (PendingIntent) bundle.getParcelable("failed_resolution")), bundle.getInt("failed_client_id", -1)) : null);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        zzbcn com_google_android_gms_internal_zzbcn = (zzbcn) this.zzaBP.get();
        if (com_google_android_gms_internal_zzbcn != null) {
            bundle.putBoolean("resolving_error", true);
            bundle.putInt("failed_client_id", com_google_android_gms_internal_zzbcn.zzpw());
            bundle.putInt("failed_status", com_google_android_gms_internal_zzbcn.zzpx().getErrorCode());
            bundle.putParcelable("failed_resolution", com_google_android_gms_internal_zzbcn.zzpx().getResolution());
        }
    }

    public void onStart() {
        super.onStart();
        this.mStarted = true;
    }

    public void onStop() {
        super.onStop();
        this.mStarted = false;
    }

    protected abstract void zza(ConnectionResult connectionResult, int i);

    public final void zzb(ConnectionResult connectionResult, int i) {
        zzbcn com_google_android_gms_internal_zzbcn = new zzbcn(connectionResult, i);
        if (this.zzaBP.compareAndSet(null, com_google_android_gms_internal_zzbcn)) {
            this.zzaBQ.post(new zzbco(this, com_google_android_gms_internal_zzbcn));
        }
    }

    protected abstract void zzpq();

    protected final void zzpv() {
        this.zzaBP.set(null);
        zzpq();
    }
}
