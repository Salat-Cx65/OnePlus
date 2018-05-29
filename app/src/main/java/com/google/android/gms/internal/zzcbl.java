package com.google.android.gms.internal;

import android.os.RemoteException;

public final class zzcbl extends zzcbg<String> {
    public zzcbl(int i, String str, String str2) {
        super(str, str2, null);
    }

    private final String zze(zzcbo com_google_android_gms_internal_zzcbo) {
        try {
            return com_google_android_gms_internal_zzcbo.getStringFlagValue(getKey(), (String) zzdH(), getSource());
        } catch (RemoteException e) {
            return (String) zzdH();
        }
    }

    public final /* synthetic */ Object zza(zzcbo com_google_android_gms_internal_zzcbo) {
        return zze(com_google_android_gms_internal_zzcbo);
    }
}
