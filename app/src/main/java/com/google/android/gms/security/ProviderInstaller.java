package com.google.android.gms.security;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.zze;
import com.google.android.gms.common.zzo;
import java.lang.reflect.Method;

public class ProviderInstaller {
    public static final String PROVIDER_NAME = "GmsCore_OpenSSL";
    private static final zze zzbCK;
    private static Method zzbCL;
    private static final Object zzuI;

    public static interface ProviderInstallListener {
        void onProviderInstallFailed(int i, Intent intent);

        void onProviderInstalled();
    }

    static {
        zzbCK = zze.zzoU();
        zzuI = new Object();
        zzbCL = null;
    }

    public static void installIfNeeded(Context context) throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException {
        zzbr.zzb((Object) context, (Object) "Context must not be null");
        zze.zzas(context);
        Context remoteContext = zzo.getRemoteContext(context);
        if (remoteContext == null) {
            Log.e("ProviderInstaller", "Failed to get remote context");
            throw new GooglePlayServicesNotAvailableException(8);
        }
        synchronized (zzuI) {
            try {
                if (zzbCL == null) {
                    zzbCL = remoteContext.getClassLoader().loadClass("com.google.android.gms.common.security.ProviderInstallerImpl").getMethod("insertProvider", new Class[]{Context.class});
                }
                zzbCL.invoke(null, new Object[]{remoteContext});
            } catch (Exception e) {
                String str = "ProviderInstaller";
                String str2 = "Failed to install provider: ";
                String valueOf = String.valueOf(e.getMessage());
                Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                throw new GooglePlayServicesNotAvailableException(8);
            } catch (Throwable th) {
            }
        }
    }

    public static void installIfNeededAsync(Context context, ProviderInstallListener providerInstallListener) {
        zzbr.zzb((Object) context, (Object) "Context must not be null");
        zzbr.zzb((Object) providerInstallListener, (Object) "Listener must not be null");
        zzbr.zzcz("Must be called on the UI thread");
        new zza(context, providerInstallListener).execute(new Void[0]);
    }
}
