package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.zzo;

public final class zza extends zzan {
    private int zzaGI;

    public static Account zza(zzam com_google_android_gms_common_internal_zzam) {
        if (com_google_android_gms_common_internal_zzam == null) {
            return null;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Account account = com_google_android_gms_common_internal_zzam.getAccount();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return account;
        } catch (RemoteException e) {
            Log.w("AccountAccessor", "Remote account accessor probably died");
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return null;
        }
    }

    public final boolean equals(Object obj) {
        Account account = null;
        return this == obj ? true : !(obj instanceof zza) ? false : account.equals(account);
    }

    public final Account getAccount() {
        int callingUid = Binder.getCallingUid();
        if (callingUid != this.zzaGI) {
            if (zzo.zzf(null, callingUid)) {
                this.zzaGI = callingUid;
            } else {
                throw new SecurityException("Caller is not GooglePlayServices");
            }
        }
        return null;
    }
}
