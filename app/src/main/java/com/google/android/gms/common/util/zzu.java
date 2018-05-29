package com.google.android.gms.common.util;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzbr;
import java.util.Set;

public final class zzu {
    public static String[] zzc(Set<Scope> set) {
        zzbr.zzb((Object) set, (Object) "scopes can't be null.");
        Object obj = (Scope[]) set.toArray(new Scope[set.size()]);
        zzbr.zzb(obj, (Object) "scopes can't be null.");
        String[] strArr = new String[obj.length];
        for (int i = 0; i < obj.length; i++) {
            strArr[i] = obj[i].zzpn();
        }
        return strArr;
    }
}
