package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import com.google.android.gms.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzcb;

@Deprecated
public final class zzbey {
    private static zzbey zzaED;
    private static final Object zzuI;
    private final String mAppId;
    private final Status zzaEE;
    private final boolean zzaEF;
    private final boolean zzaEG;

    static {
        zzuI = new Object();
    }

    private zzbey(Context context) {
        boolean z = true;
        Resources resources = context.getResources();
        int identifier = resources.getIdentifier("google_app_measurement_enable", "integer", resources.getResourcePackageName(R.string.common_google_play_services_unknown_issue));
        if (identifier != 0) {
            boolean z2 = resources.getInteger(identifier) != 0;
            if (z2) {
                z = false;
            }
            this.zzaEG = z;
            z = z2;
        } else {
            this.zzaEG = false;
        }
        this.zzaEF = z;
        Object zzaD = zzbg.zzaD(context);
        if (zzaD == null) {
            zzaD = new zzcb(context).getString("google_app_id");
        }
        if (TextUtils.isEmpty(zzaD)) {
            this.zzaEE = new Status(10, "Missing google app id value from from string resources with name google_app_id.");
            this.mAppId = null;
            return;
        }
        this.mAppId = zzaD;
        this.zzaEE = Status.zzaBo;
    }

    public static Status zzaz(Context context) {
        Status status;
        zzbr.zzb((Object) context, (Object) "Context must not be null.");
        synchronized (zzuI) {
            if (zzaED == null) {
                zzaED = new zzbey(context);
            }
            status = zzaED.zzaEE;
        }
        return status;
    }

    private static zzbey zzcu(String str) {
        zzbey com_google_android_gms_internal_zzbey;
        synchronized (zzuI) {
            if (zzaED == null) {
                throw new IllegalStateException(new StringBuilder(String.valueOf(str).length() + 34).append("Initialize must be called before ").append(str).append(".").toString());
            }
            com_google_android_gms_internal_zzbey = zzaED;
        }
        return com_google_android_gms_internal_zzbey;
    }

    public static String zzqy() {
        return zzcu("getGoogleAppId").mAppId;
    }

    public static boolean zzqz() {
        return zzcu("isMeasurementExplicitlyDisabled").zzaEG;
    }
}
