package com.google.android.gms.dynamite;

import android.content.Context;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import com.google.android.gms.dynamite.DynamiteModule.zzd;

final class zzf implements zzd {
    zzf() {
    }

    public final zzi zza(Context context, String str, zzh com_google_android_gms_dynamite_zzh) throws zzc {
        zzi com_google_android_gms_dynamite_zzi = new zzi();
        com_google_android_gms_dynamite_zzi.zzaSY = com_google_android_gms_dynamite_zzh.zzF(context, str);
        if (com_google_android_gms_dynamite_zzi.zzaSY != 0) {
            com_google_android_gms_dynamite_zzi.zzaSZ = com_google_android_gms_dynamite_zzh.zzb(context, str, false);
        } else {
            com_google_android_gms_dynamite_zzi.zzaSZ = com_google_android_gms_dynamite_zzh.zzb(context, str, true);
        }
        if (com_google_android_gms_dynamite_zzi.zzaSY == 0 && com_google_android_gms_dynamite_zzi.zzaSZ == 0) {
            com_google_android_gms_dynamite_zzi.zzaTa = 0;
        } else if (com_google_android_gms_dynamite_zzi.zzaSZ >= com_google_android_gms_dynamite_zzi.zzaSY) {
            com_google_android_gms_dynamite_zzi.zzaTa = 1;
        } else {
            com_google_android_gms_dynamite_zzi.zzaTa = -1;
        }
        return com_google_android_gms_dynamite_zzi;
    }
}
