package com.google.android.gms.common.util;

import android.util.Base64;
import com.google.android.gms.common.ConnectionResult;

public final class zzd {
    public static String zzg(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, 0);
    }

    public static String zzh(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, ConnectionResult.DEVELOPER_ERROR);
    }

    public static String zzi(byte[] bArr) {
        return bArr == null ? null : Base64.encodeToString(bArr, ConnectionResult.LICENSE_CHECK_FAILED);
    }
}
