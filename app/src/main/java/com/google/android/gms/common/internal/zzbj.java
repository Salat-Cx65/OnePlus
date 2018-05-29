package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.List;

public final class zzbj {
    private final List<String> zzaIj;
    private final Object zzaay;

    private zzbj(Object obj) {
        this.zzaay = zzbr.zzu(obj);
        this.zzaIj = new ArrayList();
    }

    public final String toString() {
        StringBuilder append = new StringBuilder(100).append(this.zzaay.getClass().getSimpleName()).append('{');
        int size = this.zzaIj.size();
        for (int i = 0; i < size; i++) {
            append.append((String) this.zzaIj.get(i));
            if (i < size - 1) {
                append.append(", ");
            }
        }
        return append.append('}').toString();
    }

    public final zzbj zzg(String str, Object obj) {
        List list = this.zzaIj;
        String str2 = (String) zzbr.zzu(str);
        String valueOf = String.valueOf(String.valueOf(obj));
        list.add(new StringBuilder((String.valueOf(str2).length() + 1) + String.valueOf(valueOf).length()).append(str2).append("=").append(valueOf).toString());
        return this;
    }
}
