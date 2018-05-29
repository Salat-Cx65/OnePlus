package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.WorkSource;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzv;
import com.google.android.gms.common.util.zzz;

public final class zzcvl {
    private static boolean DEBUG;
    private static String TAG;
    private static String zzbDa;
    private final Context mContext;
    private final String zzaJr;
    private final String zzaJt;
    private final WakeLock zzbDb;
    private WorkSource zzbDc;
    private final int zzbDd;
    private final String zzbDe;
    private boolean zzbDf;
    private int zzbDg;
    private int zzbDh;

    static {
        TAG = "WakeLock";
        zzbDa = "*gcore*:";
        DEBUG = false;
    }

    public zzcvl(Context context, int i, String str) {
        this(context, 1, str, null, context == null ? null : context.getPackageName());
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcvl(Context context, int i, String str, String str2, String str3) {
        this(context, 1, str, null, str3, null);
    }

    @SuppressLint({"UnwrappedWakeLock"})
    private zzcvl(Context context, int i, String str, String str2, String str3, String str4) {
        this.zzbDf = true;
        zzbr.zzh(str, "Wake lock name can NOT be empty");
        this.zzbDd = i;
        this.zzbDe = null;
        this.zzaJt = null;
        this.mContext = context.getApplicationContext();
        if (GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE.equals(context.getPackageName())) {
            this.zzaJr = str;
        } else {
            String valueOf = String.valueOf(zzbDa);
            String valueOf2 = String.valueOf(str);
            this.zzaJr = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        }
        this.zzbDb = ((PowerManager) context.getSystemService("power")).newWakeLock(i, str);
        if (zzz.zzaM(this.mContext)) {
            if (zzv.zzcM(str3)) {
                str3 = context.getPackageName();
            }
            this.zzbDc = zzz.zzE(context, str3);
            WorkSource workSource = this.zzbDc;
            if (workSource != null && zzz.zzaM(this.mContext)) {
                if (this.zzbDc != null) {
                    this.zzbDc.add(workSource);
                } else {
                    this.zzbDc = workSource;
                }
                try {
                    this.zzbDb.setWorkSource(this.zzbDc);
                } catch (IllegalArgumentException e) {
                    Log.wtf(TAG, e.toString());
                }
            }
        }
    }

    private final boolean zzeW(String str) {
        Object obj = null;
        return (TextUtils.isEmpty(obj) || obj.equals(this.zzbDe)) ? false : true;
    }

    private final String zzi(String str, boolean z) {
        return this.zzbDf ? z ? null : this.zzbDe : this.zzbDe;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void acquire(long r13) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvl.acquire(long):void");
        /*
        this = this;
        r10 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r1 = 0;
        r0 = r12.zzeW(r1);
        r4 = r12.zzi(r1, r0);
        monitor-enter(r12);
        r1 = r12.zzbDf;	 Catch:{ all -> 0x004b }
        if (r1 == 0) goto L_0x001a;
    L_0x0010:
        r1 = r12.zzbDg;	 Catch:{ all -> 0x004b }
        r2 = r1 + 1;
        r12.zzbDg = r2;	 Catch:{ all -> 0x004b }
        if (r1 == 0) goto L_0x0022;
    L_0x0018:
        if (r0 != 0) goto L_0x0022;
    L_0x001a:
        r0 = r12.zzbDf;	 Catch:{ all -> 0x004b }
        if (r0 != 0) goto L_0x0044;
    L_0x001e:
        r0 = r12.zzbDh;	 Catch:{ all -> 0x004b }
        if (r0 != 0) goto L_0x0044;
    L_0x0022:
        com.google.android.gms.common.stats.zze.zzrW();	 Catch:{ all -> 0x004b }
        r0 = r12.mContext;	 Catch:{ all -> 0x004b }
        r1 = r12.zzbDb;	 Catch:{ all -> 0x004b }
        r1 = com.google.android.gms.common.stats.zzc.zza(r1, r4);	 Catch:{ all -> 0x004b }
        r2 = 7;
        r3 = r12.zzaJr;	 Catch:{ all -> 0x004b }
        r5 = 0;
        r6 = r12.zzbDd;	 Catch:{ all -> 0x004b }
        r7 = r12.zzbDc;	 Catch:{ all -> 0x004b }
        r7 = com.google.android.gms.common.util.zzz.zzb(r7);	 Catch:{ all -> 0x004b }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7, r8);	 Catch:{ all -> 0x004b }
        r0 = r12.zzbDh;	 Catch:{ all -> 0x004b }
        r0 = r0 + 1;
        r12.zzbDh = r0;	 Catch:{ all -> 0x004b }
    L_0x0044:
        monitor-exit(r12);	 Catch:{ all -> 0x004b }
        r0 = r12.zzbDb;
        r0.acquire(r10);
        return;
    L_0x004b:
        r0 = move-exception;
        monitor-exit(r12);	 Catch:{ all -> 0x004b }
        throw r0;
        */
    }

    public final boolean isHeld() {
        return this.zzbDb.isHeld();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void release() {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzcvl.release():void");
        /*
        this = this;
        r1 = 0;
        r0 = r8.zzeW(r1);
        r4 = r8.zzi(r1, r0);
        monitor-enter(r8);
        r1 = r8.zzbDf;	 Catch:{ all -> 0x0049 }
        if (r1 == 0) goto L_0x0018;
    L_0x000e:
        r1 = r8.zzbDg;	 Catch:{ all -> 0x0049 }
        r1 = r1 + -1;
        r8.zzbDg = r1;	 Catch:{ all -> 0x0049 }
        if (r1 == 0) goto L_0x0021;
    L_0x0016:
        if (r0 != 0) goto L_0x0021;
    L_0x0018:
        r0 = r8.zzbDf;	 Catch:{ all -> 0x0049 }
        if (r0 != 0) goto L_0x0042;
    L_0x001c:
        r0 = r8.zzbDh;	 Catch:{ all -> 0x0049 }
        r1 = 1;
        if (r0 != r1) goto L_0x0042;
    L_0x0021:
        com.google.android.gms.common.stats.zze.zzrW();	 Catch:{ all -> 0x0049 }
        r0 = r8.mContext;	 Catch:{ all -> 0x0049 }
        r1 = r8.zzbDb;	 Catch:{ all -> 0x0049 }
        r1 = com.google.android.gms.common.stats.zzc.zza(r1, r4);	 Catch:{ all -> 0x0049 }
        r2 = 8;
        r3 = r8.zzaJr;	 Catch:{ all -> 0x0049 }
        r5 = 0;
        r6 = r8.zzbDd;	 Catch:{ all -> 0x0049 }
        r7 = r8.zzbDc;	 Catch:{ all -> 0x0049 }
        r7 = com.google.android.gms.common.util.zzz.zzb(r7);	 Catch:{ all -> 0x0049 }
        com.google.android.gms.common.stats.zze.zza(r0, r1, r2, r3, r4, r5, r6, r7);	 Catch:{ all -> 0x0049 }
        r0 = r8.zzbDh;	 Catch:{ all -> 0x0049 }
        r0 = r0 + -1;
        r8.zzbDh = r0;	 Catch:{ all -> 0x0049 }
    L_0x0042:
        monitor-exit(r8);	 Catch:{ all -> 0x0049 }
        r0 = r8.zzbDb;
        r0.release();
        return;
    L_0x0049:
        r0 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0049 }
        throw r0;
        */
    }

    public final void setReferenceCounted(boolean z) {
        this.zzbDb.setReferenceCounted(false);
        this.zzbDf = false;
    }
}
