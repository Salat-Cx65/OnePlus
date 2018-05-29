package com.google.android.gms.internal;

import android.database.ContentObserver;
import android.os.Handler;

final class iv extends ContentObserver {
    iv(Handler handler) {
        super(null);
    }

    public final void onChange(boolean z) {
        iu.zzDZ().set(true);
    }
}
