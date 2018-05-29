package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.google.android.gms.internal.zzbff;

public abstract class zzt implements OnClickListener {
    public static zzt zza(Activity activity, Intent intent, int i) {
        return new zzu(intent, activity, i);
    }

    public static zzt zza(@NonNull Fragment fragment, Intent intent, int i) {
        return new zzv(intent, fragment, i);
    }

    public static zzt zza(@NonNull zzbff com_google_android_gms_internal_zzbff, Intent intent, int i) {
        return new zzw(intent, com_google_android_gms_internal_zzbff, 2);
    }

    public void onClick(DialogInterface dialogInterface, int i) {
        try {
            zzrt();
            dialogInterface.dismiss();
        } catch (Throwable e) {
            Log.e("DialogRedirect", "Failed to start resolution intent", e);
            dialogInterface.dismiss();
        }
    }

    protected abstract void zzrt();
}
