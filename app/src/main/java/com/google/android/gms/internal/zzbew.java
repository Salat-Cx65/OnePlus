package com.google.android.gms.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.common.GooglePlayServicesUtil;

public final class zzbew extends BroadcastReceiver {
    private Context mContext;
    private final zzbex zzaEC;

    public zzbew(zzbex com_google_android_gms_internal_zzbex) {
        this.zzaEC = com_google_android_gms_internal_zzbex;
    }

    public final void onReceive(Context context, Intent intent) {
        Uri data = intent.getData();
        Object obj = null;
        if (data != null) {
            obj = data.getSchemeSpecificPart();
        }
        if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(obj)) {
            this.zzaEC.zzpy();
            unregister();
        }
    }

    public final void setContext(Context context) {
        this.mContext = context;
    }

    public final synchronized void unregister() {
        if (this.mContext != null) {
            this.mContext.unregisterReceiver(this);
        }
        this.mContext = null;
    }
}
