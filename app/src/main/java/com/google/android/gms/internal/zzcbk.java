package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbk extends zzcbg<Long> {
    public zzcbk(int i, String str, Long l) {
        super(str, l, null);
    }

    private final Long zzd(zzcbo com_google_android_gms_internal_zzcbo) {
        try {
            return Long.valueOf(com_google_android_gms_internal_zzcbo.getLongFlagValue(getKey(), ((Long) zzdH()).longValue(), getSource()));
        } catch (RemoteException e) {
            return (Long) zzdH();
        }
    }

    public final /* synthetic */ Object zza(zzcbo com_google_android_gms_internal_zzcbo) {
        return zzd(com_google_android_gms_internal_zzcbo);
    }
}
