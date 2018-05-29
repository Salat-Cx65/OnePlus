package com.google.android.gms.common;

import android.support.v4.app.NotificationCompat.MessagingStyle;
import android.util.Log;
import com.google.android.gms.common.internal.zzas;
import com.google.android.gms.common.internal.zzat;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzn;
import com.google.android.gms.dynamic.IObjectWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

abstract class zzg extends zzat {
    private int zzaAi;

    protected zzg(byte[] bArr) {
        boolean z = false;
        if (bArr.length != 25) {
            int length = bArr.length;
            String valueOf = String.valueOf(zzn.zza(bArr, 0, bArr.length, false));
            Log.wtf("GoogleCertificates", new StringBuilder(String.valueOf(valueOf).length() + 51).append("Cert hash data has incorrect length (").append(length).append("):\n").append(valueOf).toString(), new Exception());
            bArr = Arrays.copyOfRange(bArr, 0, MessagingStyle.MAXIMUM_RETAINED_MESSAGES);
            if (bArr.length == 25) {
                z = true;
            }
            zzbr.zzb(z, new StringBuilder(55).append("cert hash data has incorrect length. length=").append(bArr.length).toString());
        }
        this.zzaAi = Arrays.hashCode(bArr);
    }

    protected static byte[] zzcs(String str) {
        try {
            return str.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzas)) {
            return false;
        }
        try {
            zzas com_google_android_gms_common_internal_zzas = (zzas) obj;
            if (com_google_android_gms_common_internal_zzas.zzoX() != hashCode()) {
                return false;
            }
            IObjectWrapper zzoW = com_google_android_gms_common_internal_zzas.zzoW();
            if (zzoW == null) {
                return false;
            }
            return Arrays.equals(getBytes(), (byte[]) com.google.android.gms.dynamic.zzn.zzE(zzoW));
        } catch (Throwable e) {
            Log.e("GoogleCertificates", "Failed to get Google certificates from remote", e);
            return false;
        }
    }

    abstract byte[] getBytes();

    public int hashCode() {
        return this.zzaAi;
    }

    public final IObjectWrapper zzoW() {
        return com.google.android.gms.dynamic.zzn.zzw(getBytes());
    }

    public final int zzoX() {
        return hashCode();
    }
}
