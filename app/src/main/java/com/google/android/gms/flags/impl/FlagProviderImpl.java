package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.internal.zzcbp;

@DynamiteApi
public class FlagProviderImpl extends zzcbp {
    private SharedPreferences zzBW;
    private boolean zzuK;

    public FlagProviderImpl() {
        this.zzuK = false;
    }

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return !this.zzuK ? z : zzb.zza(this.zzBW, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return !this.zzuK ? i : zzd.zza(this.zzBW, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return !this.zzuK ? j : zzf.zza(this.zzBW, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return !this.zzuK ? str2 : zzh.zza(this.zzBW, str, str2);
    }

    public void init(IObjectWrapper iObjectWrapper) {
        Context context = (Context) zzn.zzE(iObjectWrapper);
        if (!this.zzuK) {
            try {
                this.zzBW = zzj.zzaW(context.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, 0));
                this.zzuK = true;
            } catch (NameNotFoundException e) {
            } catch (Exception e2) {
                String str = "FlagProviderImpl";
                String str2 = "Could not retrieve sdk flags, continuing with defaults: ";
                String valueOf = String.valueOf(e2.getMessage());
                Log.w(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            }
        }
    }
}
