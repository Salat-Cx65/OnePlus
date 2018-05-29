package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IInterface;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import java.util.Set;

public abstract class zzaa<T extends IInterface> extends zzd<T> implements zze, zzae {
    private final zzq zzaCC;
    private final Account zzajd;
    private final Set<Scope> zzamg;

    protected zzaa(Context context, Looper looper, int i, zzq com_google_android_gms_common_internal_zzq, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        this(context, looper, zzaf.zzaC(context), GoogleApiAvailability.getInstance(), i, com_google_android_gms_common_internal_zzq, (ConnectionCallbacks) zzbr.zzu(connectionCallbacks), (OnConnectionFailedListener) zzbr.zzu(onConnectionFailedListener));
    }

    private zzaa(Context context, Looper looper, zzaf com_google_android_gms_common_internal_zzaf, GoogleApiAvailability googleApiAvailability, int i, zzq com_google_android_gms_common_internal_zzq, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, com_google_android_gms_common_internal_zzaf, googleApiAvailability, i, connectionCallbacks == null ? null : new zzab(connectionCallbacks), onConnectionFailedListener == null ? null : new zzac(onConnectionFailedListener), com_google_android_gms_common_internal_zzq.zzrp());
        this.zzaCC = com_google_android_gms_common_internal_zzq;
        this.zzajd = com_google_android_gms_common_internal_zzq.getAccount();
        Set zzrm = com_google_android_gms_common_internal_zzq.zzrm();
        Set<Scope> zzb = zzb(zzrm);
        for (Scope scope : zzb) {
            if (!zzrm.contains(scope)) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        this.zzamg = zzb;
    }

    public final Account getAccount() {
        return this.zzajd;
    }

    @NonNull
    protected Set<Scope> zzb(@NonNull Set<Scope> set) {
        return set;
    }

    public zzc[] zzrb() {
        return new zzc[0];
    }

    protected final Set<Scope> zzrf() {
        return this.zzamg;
    }

    protected final zzq zzrx() {
        return this.zzaCC;
    }
}
