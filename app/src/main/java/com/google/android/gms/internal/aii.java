package com.google.android.gms.internal;

import java.util.Arrays;

final class aii {
    final int tag;
    final byte[] zzbww;

    aii(int i, byte[] bArr) {
        this.tag = i;
        this.zzbww = bArr;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof aii)) {
            return false;
        }
        aii com_google_android_gms_internal_aii = (aii) obj;
        return this.tag == com_google_android_gms_internal_aii.tag && Arrays.equals(this.zzbww, com_google_android_gms_internal_aii.zzbww);
    }

    public final int hashCode() {
        return ((this.tag + 527) * 31) + Arrays.hashCode(this.zzbww);
    }
}
