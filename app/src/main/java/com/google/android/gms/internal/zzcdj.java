package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzq;

public class zzcdj extends zzaa<zzcel> {
    private final String zzbiE;
    protected final zzcff<zzcel> zzbiF;

    public zzcdj(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzq com_google_android_gms_common_internal_zzq) {
        super(context, looper, 23, com_google_android_gms_common_internal_zzq, connectionCallbacks, onConnectionFailedListener);
        this.zzbiF = new zzcdk(this);
        this.zzbiE = str;
    }

    protected final /* synthetic */ IInterface zzd(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.internal.IGoogleLocationManagerService");
        return queryLocalInterface instanceof zzcel ? (zzcel) queryLocalInterface : new zzcem(iBinder);
    }

    protected final String zzda() {
        return "com.google.android.location.internal.GoogleLocationManagerService.START";
    }

    protected final String zzdb() {
        return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
    }

    protected final Bundle zzmm() {
        Bundle bundle = new Bundle();
        bundle.putString("client_name", this.zzbiE);
        return bundle;
    }
}
