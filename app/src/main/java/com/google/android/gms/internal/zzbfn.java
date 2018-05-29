package com.google.android.gms.internal;

import android.app.Activity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.concurrent.CancellationException;

public class zzbfn extends zzbcm {
    private TaskCompletionSource<Void> zzalG;

    private zzbfn(zzbff com_google_android_gms_internal_zzbff) {
        super(com_google_android_gms_internal_zzbff);
        this.zzalG = new TaskCompletionSource();
        this.zzaEI.zza("GmsAvailabilityHelper", (zzbfe) this);
    }

    public static zzbfn zzp(Activity activity) {
        zzbff zzn = zzn(activity);
        zzbfn com_google_android_gms_internal_zzbfn = (zzbfn) zzn.zza("GmsAvailabilityHelper", zzbfn.class);
        if (com_google_android_gms_internal_zzbfn == null) {
            return new zzbfn(zzn);
        }
        if (!com_google_android_gms_internal_zzbfn.zzalG.getTask().isComplete()) {
            return com_google_android_gms_internal_zzbfn;
        }
        com_google_android_gms_internal_zzbfn.zzalG = new TaskCompletionSource();
        return com_google_android_gms_internal_zzbfn;
    }

    public final Task<Void> getTask() {
        return this.zzalG.getTask();
    }

    public final void onDestroy() {
        super.onDestroy();
        this.zzalG.setException(new CancellationException("Host activity was destroyed before Google Play services could be made available."));
    }

    protected final void zza(ConnectionResult connectionResult, int i) {
        this.zzalG.setException(zzb.zzx(new Status(connectionResult.getErrorCode(), connectionResult.getErrorMessage(), connectionResult.getResolution())));
    }

    protected final void zzpq() {
        int isGooglePlayServicesAvailable = this.zzaBf.isGooglePlayServicesAvailable(this.zzaEI.zzqD());
        if (isGooglePlayServicesAvailable == 0) {
            this.zzalG.setResult(null);
        } else if (!this.zzalG.getTask().isComplete()) {
            zzb(new ConnectionResult(isGooglePlayServicesAvailable, null), 0);
        }
    }
}
