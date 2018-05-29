package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzcux;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class zzq {
    private final Set<Scope> zzaAV;
    private final int zzaAX;
    private final View zzaAY;
    private final String zzaAZ;
    private final Set<Scope> zzaHm;
    private final Map<Api<?>, zzr> zzaHn;
    private final zzcux zzaHo;
    private Integer zzaHp;
    private final Account zzajd;
    private final String zzakg;

    public zzq(Account account, Set<Scope> set, Map<Api<?>, zzr> map, int i, View view, String str, String str2, zzcux com_google_android_gms_internal_zzcux) {
        Map map2;
        this.zzajd = account;
        this.zzaAV = set == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(set);
        if (map == null) {
            map2 = Collections.EMPTY_MAP;
        }
        this.zzaHn = map2;
        this.zzaAY = view;
        this.zzaAX = i;
        this.zzakg = str;
        this.zzaAZ = str2;
        this.zzaHo = com_google_android_gms_internal_zzcux;
        Set hashSet = new HashSet(this.zzaAV);
        for (zzr com_google_android_gms_common_internal_zzr : this.zzaHn.values()) {
            hashSet.addAll(com_google_android_gms_common_internal_zzr.zzamg);
        }
        this.zzaHm = Collections.unmodifiableSet(hashSet);
    }

    public static zzq zzaA(Context context) {
        return new Builder(context).zzpl();
    }

    public final Account getAccount() {
        return this.zzajd;
    }

    @Deprecated
    public final String getAccountName() {
        return this.zzajd != null ? this.zzajd.name : null;
    }

    public final Set<Scope> zzc(Api<?> api) {
        zzr com_google_android_gms_common_internal_zzr = (zzr) this.zzaHn.get(api);
        if (com_google_android_gms_common_internal_zzr == null || com_google_android_gms_common_internal_zzr.zzamg.isEmpty()) {
            return this.zzaAV;
        }
        Set<Scope> hashSet = new HashSet(this.zzaAV);
        hashSet.addAll(com_google_android_gms_common_internal_zzr.zzamg);
        return hashSet;
    }

    public final void zzc(Integer num) {
        this.zzaHp = num;
    }

    public final Account zzrj() {
        return this.zzajd != null ? this.zzajd : new Account("<<default account>>", "com.google");
    }

    public final int zzrk() {
        return this.zzaAX;
    }

    public final Set<Scope> zzrl() {
        return this.zzaAV;
    }

    public final Set<Scope> zzrm() {
        return this.zzaHm;
    }

    public final Map<Api<?>, zzr> zzrn() {
        return this.zzaHn;
    }

    public final String zzro() {
        return this.zzakg;
    }

    public final String zzrp() {
        return this.zzaAZ;
    }

    public final View zzrq() {
        return this.zzaAY;
    }

    public final zzcux zzrr() {
        return this.zzaHo;
    }

    public final Integer zzrs() {
        return this.zzaHp;
    }
}
