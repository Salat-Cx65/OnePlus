package com.google.android.gms.internal;

import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzca;

public final class zzbdm implements zzbei {
    private final zzbej zzaDb;
    private boolean zzaDc;

    public zzbdm(zzbej com_google_android_gms_internal_zzbej) {
        this.zzaDc = false;
        this.zzaDb = com_google_android_gms_internal_zzbej;
    }

    public final void begin() {
    }

    public final void connect() {
        if (this.zzaDc) {
            this.zzaDc = false;
            this.zzaDb.zza(new zzbdo(this, this));
        }
    }

    public final boolean disconnect() {
        if (this.zzaDc) {
            return false;
        }
        if (this.zzaDb.zzaCn.zzqd()) {
            this.zzaDc = true;
            for (zzbge com_google_android_gms_internal_zzbge : this.zzaDb.zzaCn.zzaDM) {
                com_google_android_gms_internal_zzbge.zzqI();
            }
            return false;
        }
        this.zzaDb.zzg(null);
        return true;
    }

    public final void onConnected(Bundle bundle) {
    }

    public final void onConnectionSuspended(int i) {
        this.zzaDb.zzg(null);
        this.zzaDb.zzaEa.zze(i, this.zzaDc);
    }

    public final void zza(ConnectionResult connectionResult, Api<?> api, boolean z) {
    }

    public final <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(T t) {
        return zze(t);
    }

    public final <A extends zzb, T extends zzbck<? extends Result, A>> T zze(T t) {
        try {
            this.zzaDb.zzaCn.zzaDN.zzb(t);
            zzbeb com_google_android_gms_internal_zzbeb = this.zzaDb.zzaCn;
            Object obj = (zze) com_google_android_gms_internal_zzbeb.zzaDH.get(t.zzpb());
            zzbr.zzb(obj, (Object) "Appropriate Api was not requested.");
            if (obj.isConnected() || !this.zzaDb.zzaDW.containsKey(t.zzpb())) {
                zzb com_google_android_gms_common_api_Api_zzb;
                if (obj instanceof zzca) {
                    zzca com_google_android_gms_common_internal_zzca = (zzca) obj;
                    com_google_android_gms_common_api_Api_zzb = null;
                }
                t.zzb(com_google_android_gms_common_api_Api_zzb);
                return t;
            }
            t.zzr(new Status(17));
            return t;
        } catch (DeadObjectException e) {
            this.zzaDb.zza(new zzbdn(this, this));
        }
    }

    final void zzpS() {
        if (this.zzaDc) {
            this.zzaDc = false;
            this.zzaDb.zzaCn.zzaDN.release();
            disconnect();
        }
    }
}
