package com.google.android.gms.common.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzbim;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import net.oneplus.weather.api.WeatherRequest.Type;

public final class zza {
    public static byte[] zzA(Context context, String str) throws NameNotFoundException {
        String str2 = "SHA1";
        PackageInfo packageInfo = zzbim.zzaP(context).getPackageInfo(str, Type.SUCCESS);
        if (packageInfo.signatures != null && packageInfo.signatures.length > 0) {
            MessageDigest zzbE = zzbE(str2);
            if (zzbE != null) {
                return zzbE.digest(packageInfo.signatures[0].toByteArray());
            }
        }
        return null;
    }

    private static MessageDigest zzbE(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }
}
