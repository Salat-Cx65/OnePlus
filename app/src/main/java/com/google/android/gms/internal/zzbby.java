package com.google.android.gms.internal;

import android.os.Build.VERSION;
import android.os.DeadObjectException;
import android.os.RemoteException;
import android.os.TransactionTooLargeException;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.Status;

public abstract class zzbby {
    private int zzamt;

    public zzbby(int i) {
        this.zzamt = i;
    }

    private static Status zza(RemoteException remoteException) {
        StringBuilder stringBuilder = new StringBuilder();
        if (VERSION.SDK_INT >= 15 && (remoteException instanceof TransactionTooLargeException)) {
            stringBuilder.append("TransactionTooLargeException: ");
        }
        stringBuilder.append(remoteException.getLocalizedMessage());
        return new Status(8, stringBuilder.toString());
    }

    public abstract void zza(@NonNull zzbdf com_google_android_gms_internal_zzbdf, boolean z);

    public abstract void zza(zzbep<?> com_google_android_gms_internal_zzbep_) throws DeadObjectException;

    public abstract void zzp(@NonNull Status status);
}
