package com.google.android.gms.common;

import android.support.v4.app.NotificationCompat.MessagingStyle;
import java.util.Arrays;

final class zzh extends zzg {
    private final byte[] zzaAj;

    zzh(byte[] bArr) {
        super(Arrays.copyOfRange(bArr, 0, MessagingStyle.MAXIMUM_RETAINED_MESSAGES));
        this.zzaAj = bArr;
    }

    final byte[] getBytes() {
        return this.zzaAj;
    }
}
