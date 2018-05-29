package com.google.android.gms.common.api;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.internal.zzbcf;
import java.util.ArrayList;

public final class zza extends Exception {
    private final ArrayMap<zzbcf<?>, ConnectionResult> zzaAD;

    public zza(ArrayMap<zzbcf<?>, ConnectionResult> arrayMap) {
        this.zzaAD = arrayMap;
    }

    public final String getMessage() {
        Iterable arrayList = new ArrayList();
        int i = 1;
        for (zzbcf com_google_android_gms_internal_zzbcf : this.zzaAD.keySet()) {
            ConnectionResult connectionResult = (ConnectionResult) this.zzaAD.get(com_google_android_gms_internal_zzbcf);
            if (connectionResult.isSuccess()) {
                Object obj = null;
            }
            String valueOf = String.valueOf(com_google_android_gms_internal_zzbcf.zzpp());
            String valueOf2 = String.valueOf(connectionResult);
            arrayList.add(new StringBuilder((String.valueOf(valueOf).length() + 2) + String.valueOf(valueOf2).length()).append(valueOf).append(": ").append(valueOf2).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (obj != null) {
            stringBuilder.append("None of the queried APIs are available. ");
        } else {
            stringBuilder.append("Some of the queried APIs are unavailable. ");
        }
        stringBuilder.append(TextUtils.join("; ", arrayList));
        return stringBuilder.toString();
    }

    public final ConnectionResult zza(GoogleApi<? extends ApiOptions> googleApi) {
        zzbcf zzpf = googleApi.zzpf();
        zzbr.zzb(this.zzaAD.get(zzpf) != null, (Object) "The given API was not part of the availability request.");
        return (ConnectionResult) this.zzaAD.get(zzpf);
    }

    public final ArrayMap<zzbcf<?>, ConnectionResult> zzpd() {
        return this.zzaAD;
    }
}
