package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.gms.R;

public final class zzcb {
    private final Resources zzaIy;
    private final String zzaIz;

    public zzcb(Context context) {
        zzbr.zzu(context);
        this.zzaIy = context.getResources();
        this.zzaIz = this.zzaIy.getResourcePackageName(R.string.common_google_play_services_unknown_issue);
    }

    public final String getString(String str) {
        int identifier = this.zzaIy.getIdentifier(str, "string", this.zzaIz);
        return identifier == 0 ? null : this.zzaIy.getString(identifier);
    }
}
