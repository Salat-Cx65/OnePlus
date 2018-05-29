package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.internal.zzbim;

public final class zze {
    public static int zzB(Context context, String str) {
        PackageInfo zzC = zzC(context, str);
        if (zzC == null || zzC.applicationInfo == null) {
            return -1;
        }
        Bundle bundle = zzC.applicationInfo.metaData;
        return bundle != null ? bundle.getInt("com.google.android.gms.version", -1) : -1;
    }

    @Nullable
    private static PackageInfo zzC(Context context, String str) {
        try {
            return zzbim.zzaP(context).getPackageInfo(str, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
        } catch (NameNotFoundException e) {
            return null;
        }
    }

    public static boolean zzD(Context context, String str) {
        GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(str);
        try {
            return (zzbim.zzaP(context).getApplicationInfo(str, 0).flags & 2097152) != 0;
        } catch (NameNotFoundException e) {
            return false;
        }
    }
}
