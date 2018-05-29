package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbcq;
import com.google.android.gms.internal.zzbfo;
import com.google.android.gms.internal.zzbfz;

public final class PendingResults {

    static final class zza<R extends Result> extends zzbcq<R> {
        private final R zzaBk;

        public zza(R r) {
            super(Looper.getMainLooper());
            this.zzaBk = r;
        }

        protected final R zzb(Status status) {
            if (status.getStatusCode() == this.zzaBk.getStatus().getStatusCode()) {
                return this.zzaBk;
            }
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    static final class zzb<R extends Result> extends zzbcq<R> {
        private final R zzaBl;

        public zzb(GoogleApiClient googleApiClient, R r) {
            super(googleApiClient);
            this.zzaBl = r;
        }

        protected final R zzb(Status status) {
            return this.zzaBl;
        }
    }

    static final class zzc<R extends Result> extends zzbcq<R> {
        public zzc(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        protected final R zzb(Status status) {
            throw new UnsupportedOperationException("Creating failed results is not supported");
        }
    }

    private PendingResults() {
    }

    public static PendingResult<Status> canceledPendingResult() {
        PendingResult com_google_android_gms_internal_zzbfz = new zzbfz(Looper.getMainLooper());
        com_google_android_gms_internal_zzbfz.cancel();
        return com_google_android_gms_internal_zzbfz;
    }

    public static <R extends Result> PendingResult<R> canceledPendingResult(R r) {
        zzbr.zzb((Object) r, (Object) "Result must not be null");
        zzbr.zzb(r.getStatus().getStatusCode() == 16, (Object) "Status code must be CommonStatusCodes.CANCELED");
        PendingResult com_google_android_gms_common_api_PendingResults_zza = new zza(r);
        com_google_android_gms_common_api_PendingResults_zza.cancel();
        return com_google_android_gms_common_api_PendingResults_zza;
    }

    public static <R extends Result> OptionalPendingResult<R> immediatePendingResult(R r) {
        zzbr.zzb((Object) r, (Object) "Result must not be null");
        PendingResult com_google_android_gms_common_api_PendingResults_zzc = new zzc(null);
        com_google_android_gms_common_api_PendingResults_zzc.setResult(r);
        return new zzbfo(com_google_android_gms_common_api_PendingResults_zzc);
    }

    public static PendingResult<Status> immediatePendingResult(Status status) {
        zzbr.zzb((Object) status, (Object) "Result must not be null");
        PendingResult com_google_android_gms_internal_zzbfz = new zzbfz(Looper.getMainLooper());
        com_google_android_gms_internal_zzbfz.setResult(status);
        return com_google_android_gms_internal_zzbfz;
    }

    public static <R extends Result> PendingResult<R> zza(R r, GoogleApiClient googleApiClient) {
        zzbr.zzb((Object) r, (Object) "Result must not be null");
        zzbr.zzb(!r.getStatus().isSuccess(), (Object) "Status code must not be SUCCESS");
        PendingResult com_google_android_gms_common_api_PendingResults_zzb = new zzb(googleApiClient, r);
        com_google_android_gms_common_api_PendingResults_zzb.setResult(r);
        return com_google_android_gms_common_api_PendingResults_zzb;
    }

    public static PendingResult<Status> zza(Status status, GoogleApiClient googleApiClient) {
        zzbr.zzb((Object) status, (Object) "Result must not be null");
        PendingResult com_google_android_gms_internal_zzbfz = new zzbfz(googleApiClient);
        com_google_android_gms_internal_zzbfz.setResult(status);
        return com_google_android_gms_internal_zzbfz;
    }

    public static <R extends Result> OptionalPendingResult<R> zzb(R r, GoogleApiClient googleApiClient) {
        zzbr.zzb((Object) r, (Object) "Result must not be null");
        PendingResult com_google_android_gms_common_api_PendingResults_zzc = new zzc(googleApiClient);
        com_google_android_gms_common_api_PendingResults_zzc.setResult(r);
        return new zzbfo(com_google_android_gms_common_api_PendingResults_zzc);
    }
}
