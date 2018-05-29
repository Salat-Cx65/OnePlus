package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbj extends zzcbg<Integer> {
    public zzcbj(int i, String str, Integer num) {
        super(str, num, null);
    }

    private final Integer zzc(zzcbo com_google_android_gms_internal_zzcbo) {
        try {
            return Integer.valueOf(com_google_android_gms_internal_zzcbo.getIntFlagValue(getKey(), ((Integer) zzdH()).intValue(), getSource()));
        } catch (RemoteException e) {
            return (Integer) zzdH();
        }
    }

    public final /* synthetic */ Object zza(zzcbo com_google_android_gms_internal_zzcbo) {
        return zzc(com_google_android_gms_internal_zzcbo);
    }
}
