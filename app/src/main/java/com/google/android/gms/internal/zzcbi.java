package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbi extends zzcbg<Boolean> {
    public zzcbi(int i, String str, Boolean bool) {
        super(str, bool, null);
    }

    private final Boolean zzb(zzcbo com_google_android_gms_internal_zzcbo) {
        try {
            return Boolean.valueOf(com_google_android_gms_internal_zzcbo.getBooleanFlagValue(getKey(), ((Boolean) zzdH()).booleanValue(), getSource()));
        } catch (RemoteException e) {
            return (Boolean) zzdH();
        }
    }

    public final /* synthetic */ Object zza(zzcbo com_google_android_gms_internal_zzcbo) {
        return zzb(com_google_android_gms_internal_zzcbo);
    }
}
