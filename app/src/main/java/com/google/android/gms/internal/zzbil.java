package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Process;
import com.google.android.gms.common.util.zzs;

public final class zzbil {
    private Context mContext;

    public zzbil(Context context) {
        this.mContext = context;
    }

    public final int checkCallingOrSelfPermission(String str) {
        return this.mContext.checkCallingOrSelfPermission(str);
    }

    public final int checkPermission(String str, String str2) {
        return this.mContext.getPackageManager().checkPermission(str, str2);
    }

    public final ApplicationInfo getApplicationInfo(String str, int i) throws NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationInfo(str, i);
    }

    public final PackageInfo getPackageInfo(String str, int i) throws NameNotFoundException {
        return this.mContext.getPackageManager().getPackageInfo(str, i);
    }

    public final String[] getPackagesForUid(int i) {
        return this.mContext.getPackageManager().getPackagesForUid(i);
    }

    public final CharSequence zzcN(String str) throws NameNotFoundException {
        return this.mContext.getPackageManager().getApplicationLabel(this.mContext.getPackageManager().getApplicationInfo(str, 0));
    }

    @TargetApi(19)
    public final boolean zzf(int i, String str) {
        boolean z = false;
        if (zzs.zzsb()) {
            try {
                ((AppOpsManager) this.mContext.getSystemService("appops")).checkPackage(i, str);
                z = true;
                return z;
            } catch (SecurityException e) {
                return z;
            }
        }
        String[] packagesForUid = this.mContext.getPackageManager().getPackagesForUid(i);
        if (str == null || packagesForUid == null) {
            return false;
        }
        for (int i2 = 0; i2 < packagesForUid.length; i2++) {
            if (str.equals(packagesForUid[i2])) {
                return true;
            }
        }
        return false;
    }

    public final boolean zzsk() {
        return Binder.getCallingUid() == Process.myUid() ? zzbik.zzaN(this.mContext) : false;
    }
}
