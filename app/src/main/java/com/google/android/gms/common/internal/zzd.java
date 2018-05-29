package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.zzc;
import com.google.android.gms.common.zze;
import com.google.android.gms.location.DetectedActivity;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public abstract class zzd<T extends IInterface> {
    private static String[] zzaHe;
    private final Context mContext;
    final Handler mHandler;
    private final Object mLock;
    private final zze zzaCH;
    private int zzaGJ;
    private long zzaGK;
    private long zzaGL;
    private int zzaGM;
    private long zzaGN;
    private zzal zzaGO;
    private final zzaf zzaGP;
    private final Object zzaGQ;
    private zzay zzaGR;
    protected zzj zzaGS;
    private T zzaGT;
    private final ArrayList<zzi<?>> zzaGU;
    private zzl zzaGV;
    private int zzaGW;
    private final zzf zzaGX;
    private final zzg zzaGY;
    private final int zzaGZ;
    private final String zzaHa;
    private ConnectionResult zzaHb;
    private boolean zzaHc;
    protected AtomicInteger zzaHd;
    private final Looper zzrP;

    static {
        zzaHe = new String[]{"service_esmobile", "service_googleme"};
    }

    protected zzd(Context context, Looper looper, int i, zzf com_google_android_gms_common_internal_zzf, zzg com_google_android_gms_common_internal_zzg, String str) {
        this(context, looper, zzaf.zzaC(context), zze.zzoU(), i, (zzf) zzbr.zzu(com_google_android_gms_common_internal_zzf), (zzg) zzbr.zzu(com_google_android_gms_common_internal_zzg), null);
    }

    protected zzd(Context context, Looper looper, zzaf com_google_android_gms_common_internal_zzaf, zze com_google_android_gms_common_zze, int i, zzf com_google_android_gms_common_internal_zzf, zzg com_google_android_gms_common_internal_zzg, String str) {
        this.mLock = new Object();
        this.zzaGQ = new Object();
        this.zzaGU = new ArrayList();
        this.zzaGW = 1;
        this.zzaHb = null;
        this.zzaHc = false;
        this.zzaHd = new AtomicInteger(0);
        this.mContext = (Context) zzbr.zzb((Object) context, (Object) "Context must not be null");
        this.zzrP = (Looper) zzbr.zzb((Object) looper, (Object) "Looper must not be null");
        this.zzaGP = (zzaf) zzbr.zzb((Object) com_google_android_gms_common_internal_zzaf, (Object) "Supervisor must not be null");
        this.zzaCH = (zze) zzbr.zzb((Object) com_google_android_gms_common_zze, (Object) "API availability must not be null");
        this.mHandler = new zzh(this, looper);
        this.zzaGZ = i;
        this.zzaGX = com_google_android_gms_common_internal_zzf;
        this.zzaGY = com_google_android_gms_common_internal_zzg;
        this.zzaHa = str;
    }

    private final void zza(int i, T t) {
        boolean z = true;
        if (i == 4) {
            boolean z2 = true;
        } else {
            Object obj = null;
        }
        if (t != null) {
            boolean z3 = true;
        } else {
            Object obj2 = null;
        }
        if (z2 != z3) {
            z = false;
        }
        zzbr.zzaf(z);
        synchronized (this.mLock) {
            this.zzaGW = i;
            this.zzaGT = t;
            switch (i) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    if (this.zzaGV != null) {
                        this.zzaGP.zza(zzda(), zzqX(), this.zzaGV, zzqY());
                        this.zzaGV = null;
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                    String valueOf;
                    String valueOf2;
                    if (!(this.zzaGV == null || this.zzaGO == null)) {
                        valueOf = String.valueOf(this.zzaGO.zzrD());
                        valueOf2 = String.valueOf(this.zzaGO.getPackageName());
                        Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 70) + String.valueOf(valueOf2).length()).append("Calling connect() while still connected, missing disconnect() for ").append(valueOf).append(" on ").append(valueOf2).toString());
                        this.zzaGP.zza(this.zzaGO.zzrD(), this.zzaGO.getPackageName(), this.zzaGV, zzqY());
                        this.zzaHd.incrementAndGet();
                    }
                    this.zzaGV = new zzl(this, this.zzaHd.get());
                    this.zzaGO = new zzal(zzqX(), zzda(), false);
                    zzaf com_google_android_gms_common_internal_zzaf = this.zzaGP;
                    valueOf = this.zzaGO.zzrD();
                    valueOf2 = this.zzaGO.getPackageName();
                    if (!com_google_android_gms_common_internal_zzaf.zza(new zzag(valueOf, valueOf2), this.zzaGV, zzqY())) {
                        valueOf = String.valueOf(this.zzaGO.zzrD());
                        valueOf2 = String.valueOf(this.zzaGO.getPackageName());
                        Log.e("GmsClient", new StringBuilder((String.valueOf(valueOf).length() + 34) + String.valueOf(valueOf2).length()).append("unable to connect to service: ").append(valueOf).append(" on ").append(valueOf2).toString());
                        zza((int) ConnectionResult.API_UNAVAILABLE, null, this.zzaHd.get());
                    }
                    break;
                case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                    zza((IInterface) t);
                    break;
            }
        }
    }

    private final boolean zza(int i, int i2, T t) {
        boolean z;
        synchronized (this.mLock) {
            if (this.zzaGW != i) {
                z = false;
            } else {
                zza(i2, (IInterface) t);
                z = true;
            }
        }
        return z;
    }

    private final void zzaz(int i) {
        int i2;
        if (zzra()) {
            i2 = RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER;
            this.zzaHc = true;
        } else {
            i2 = RainSurfaceView.RAIN_LEVEL_RAINSTORM;
        }
        this.mHandler.sendMessage(this.mHandler.obtainMessage(i2, this.zzaHd.get(), ConnectionResult.API_UNAVAILABLE));
    }

    @Nullable
    private final String zzqY() {
        return this.zzaHa == null ? this.mContext.getClass().getName() : this.zzaHa;
    }

    private final boolean zzra() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaGW == 3;
        }
        return z;
    }

    private final boolean zzrg() {
        boolean z = false;
        if (this.zzaHc || TextUtils.isEmpty(zzdb()) || TextUtils.isEmpty(null)) {
            return false;
        }
        try {
            Class.forName(zzdb());
            z = true;
            return true;
        } catch (ClassNotFoundException e) {
            return z;
        }
    }

    public void disconnect() {
        this.zzaHd.incrementAndGet();
        synchronized (this.zzaGU) {
            int size = this.zzaGU.size();
            for (int i = 0; i < size; i++) {
                ((zzi) this.zzaGU.get(i)).removeListener();
            }
            this.zzaGU.clear();
        }
        synchronized (this.zzaGQ) {
            this.zzaGR = null;
        }
        zza(1, null);
    }

    public final void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (this.mLock) {
            int i = this.zzaGW;
            IInterface iInterface = this.zzaGT;
        }
        synchronized (this.zzaGQ) {
            zzay com_google_android_gms_common_internal_zzay = this.zzaGR;
        }
        printWriter.append(str).append("mConnectState=");
        switch (i) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                printWriter.print("DISCONNECTED");
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                printWriter.print("REMOTE_CONNECTING");
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                printWriter.print("LOCAL_CONNECTING");
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
                printWriter.print("CONNECTED");
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                printWriter.print("DISCONNECTING");
                break;
            default:
                printWriter.print("UNKNOWN");
                break;
        }
        printWriter.append(" mService=");
        if (iInterface == null) {
            printWriter.append("null");
        } else {
            printWriter.append(zzdb()).append("@").append(Integer.toHexString(System.identityHashCode(iInterface.asBinder())));
        }
        printWriter.append(" mServiceBroker=");
        if (com_google_android_gms_common_internal_zzay == null) {
            printWriter.println("null");
        } else {
            printWriter.append("IGmsServiceBroker@").println(Integer.toHexString(System.identityHashCode(com_google_android_gms_common_internal_zzay.asBinder())));
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.US);
        if (this.zzaGL > 0) {
            PrintWriter append = printWriter.append(str).append("lastConnectedTime=");
            long j = this.zzaGL;
            String valueOf = String.valueOf(simpleDateFormat.format(new Date(this.zzaGL)));
            append.println(new StringBuilder(String.valueOf(valueOf).length() + 21).append(j).append(" ").append(valueOf).toString());
        }
        if (this.zzaGK > 0) {
            printWriter.append(str).append("lastSuspendedCause=");
            switch (this.zzaGJ) {
                case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                    printWriter.append("CAUSE_SERVICE_DISCONNECTED");
                    break;
                case RainSurfaceView.RAIN_LEVEL_SHOWER:
                    printWriter.append("CAUSE_NETWORK_LOST");
                    break;
                default:
                    printWriter.append(String.valueOf(this.zzaGJ));
                    break;
            }
            append = printWriter.append(" lastSuspendedTime=");
            j = this.zzaGK;
            valueOf = String.valueOf(simpleDateFormat.format(new Date(this.zzaGK)));
            append.println(new StringBuilder(String.valueOf(valueOf).length() + 21).append(j).append(" ").append(valueOf).toString());
        }
        if (this.zzaGN > 0) {
            printWriter.append(str).append("lastFailedStatus=").append(CommonStatusCodes.getStatusCodeString(this.zzaGM));
            append = printWriter.append(" lastFailedTime=");
            j = this.zzaGN;
            String valueOf2 = String.valueOf(simpleDateFormat.format(new Date(this.zzaGN)));
            append.println(new StringBuilder(String.valueOf(valueOf2).length() + 21).append(j).append(" ").append(valueOf2).toString());
        }
    }

    public Account getAccount() {
        return null;
    }

    public final Context getContext() {
        return this.mContext;
    }

    public final Looper getLooper() {
        return this.zzrP;
    }

    public final boolean isConnected() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaGW == 4;
        }
        return z;
    }

    public final boolean isConnecting() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzaGW == 2 || this.zzaGW == 3;
        }
        return z;
    }

    @CallSuper
    protected void onConnectionFailed(ConnectionResult connectionResult) {
        this.zzaGM = connectionResult.getErrorCode();
        this.zzaGN = System.currentTimeMillis();
    }

    @CallSuper
    protected final void onConnectionSuspended(int i) {
        this.zzaGJ = i;
        this.zzaGK = System.currentTimeMillis();
    }

    protected final void zza(int i, @Nullable Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(DetectedActivity.WALKING, i2, -1, new zzo(this, i, null)));
    }

    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(1, i2, -1, new zzn(this, i, iBinder, bundle)));
    }

    @CallSuper
    protected void zza(@NonNull T t) {
        this.zzaGL = System.currentTimeMillis();
    }

    @WorkerThread
    public final void zza(zzam com_google_android_gms_common_internal_zzam, Set<Scope> set) {
        Bundle zzmm = zzmm();
        zzy com_google_android_gms_common_internal_zzy = new zzy(this.zzaGZ);
        com_google_android_gms_common_internal_zzy.zzaHy = this.mContext.getPackageName();
        com_google_android_gms_common_internal_zzy.zzaHB = zzmm;
        if (set != null) {
            com_google_android_gms_common_internal_zzy.zzaHA = (Scope[]) set.toArray(new Scope[set.size()]);
        }
        if (zzmt()) {
            com_google_android_gms_common_internal_zzy.zzaHC = getAccount() != null ? getAccount() : new Account("<<default account>>", "com.google");
            if (com_google_android_gms_common_internal_zzam != null) {
                com_google_android_gms_common_internal_zzy.zzaHz = com_google_android_gms_common_internal_zzam.asBinder();
            }
        } else if (zzre()) {
            com_google_android_gms_common_internal_zzy.zzaHC = getAccount();
        }
        com_google_android_gms_common_internal_zzy.zzaHD = zzrb();
        try {
            synchronized (this.zzaGQ) {
                if (this.zzaGR != null) {
                    this.zzaGR.zza(new zzk(this, this.zzaHd.get()), com_google_android_gms_common_internal_zzy);
                } else {
                    Log.w("GmsClient", "mServiceBroker is null, client disconnected");
                }
            }
        } catch (Throwable e) {
            Throwable e2;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zzay(1);
        } catch (SecurityException e3) {
            throw e3;
        } catch (RemoteException e4) {
            e2 = e4;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zza((int) DetectedActivity.RUNNING, null, null, this.zzaHd.get());
        } catch (RuntimeException e5) {
            e2 = e5;
            Log.w("GmsClient", "IGmsServiceBroker.getService failed", e2);
            zza((int) DetectedActivity.RUNNING, null, null, this.zzaHd.get());
        }
    }

    public void zza(@NonNull zzj com_google_android_gms_common_internal_zzj) {
        this.zzaGS = (zzj) zzbr.zzb((Object) com_google_android_gms_common_internal_zzj, (Object) "Connection progress callbacks cannot be null.");
        zza((int) RainSurfaceView.RAIN_LEVEL_SHOWER, null);
    }

    protected final void zza(@NonNull zzj com_google_android_gms_common_internal_zzj, int i, @Nullable PendingIntent pendingIntent) {
        this.zzaGS = (zzj) zzbr.zzb((Object) com_google_android_gms_common_internal_zzj, (Object) "Connection progress callbacks cannot be null.");
        this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_DOWNPOUR, this.zzaHd.get(), i, pendingIntent));
    }

    public final void zzay(int i) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(ConnectionResult.RESOLUTION_REQUIRED, this.zzaHd.get(), i));
    }

    @Nullable
    protected abstract T zzd(IBinder iBinder);

    @NonNull
    protected abstract String zzda();

    @NonNull
    protected abstract String zzdb();

    public boolean zzmE() {
        return false;
    }

    public Intent zzmF() {
        throw new UnsupportedOperationException("Not a sign in API");
    }

    protected Bundle zzmm() {
        return new Bundle();
    }

    public boolean zzmt() {
        return false;
    }

    public Bundle zzoA() {
        return null;
    }

    public boolean zzpc() {
        return true;
    }

    protected String zzqX() {
        return GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE;
    }

    public final void zzqZ() {
        int isGooglePlayServicesAvailable = this.zzaCH.isGooglePlayServicesAvailable(this.mContext);
        if (isGooglePlayServicesAvailable != 0) {
            zza(1, null);
            zza(new zzm(this), isGooglePlayServicesAvailable, null);
            return;
        }
        zza(new zzm(this));
    }

    public zzc[] zzrb() {
        return new zzc[0];
    }

    protected final void zzrc() {
        if (!isConnected()) {
            throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
        }
    }

    public final T zzrd() throws DeadObjectException {
        T t;
        synchronized (this.mLock) {
            if (this.zzaGW == 5) {
                throw new DeadObjectException();
            }
            zzrc();
            zzbr.zza(this.zzaGT != null, (Object) "Client is connected but service is null");
            t = this.zzaGT;
        }
        return t;
    }

    public boolean zzre() {
        return false;
    }

    protected Set<Scope> zzrf() {
        return Collections.EMPTY_SET;
    }
}
