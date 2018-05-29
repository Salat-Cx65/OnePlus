package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;

final class zzbhf extends zzbgz {
    private final zzbcl<Status> zzaIB;

    public zzbhf(zzbcl<Status> com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status) {
        this.zzaIB = com_google_android_gms_internal_zzbcl_com_google_android_gms_common_api_Status;
    }

    public final void zzaC(int i) throws RemoteException {
        this.zzaIB.setResult(new Status(i));
    }
}
