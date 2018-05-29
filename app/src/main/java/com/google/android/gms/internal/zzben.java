package com.google.android.gms.internal;

import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.zzb;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import net.oneplus.weather.widget.WeatherCircleView;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class zzben implements Callback {
    public static final Status zzaEe;
    private static final Status zzaEf;
    private static zzben zzaEh;
    private static final Object zzuI;
    private final Context mContext;
    private final Handler mHandler;
    private final GoogleApiAvailability zzaBf;
    private final Map<zzbcf<?>, zzbep<?>> zzaCD;
    private long zzaDD;
    private long zzaDE;
    private long zzaEg;
    private int zzaEi;
    private final AtomicInteger zzaEj;
    private final AtomicInteger zzaEk;
    private zzbdi zzaEl;
    private final Set<zzbcf<?>> zzaEm;
    private final Set<zzbcf<?>> zzaEn;

    static {
        zzaEe = new Status(4, "Sign-out occurred while this API call was in progress.");
        zzaEf = new Status(4, "The user must be signed in to make this API call.");
        zzuI = new Object();
    }

    private zzben(Context context, Looper looper, GoogleApiAvailability googleApiAvailability) {
        this.zzaDE = 5000;
        this.zzaDD = 120000;
        this.zzaEg = 10000;
        this.zzaEi = -1;
        this.zzaEj = new AtomicInteger(1);
        this.zzaEk = new AtomicInteger(0);
        this.zzaCD = new ConcurrentHashMap(5, 0.75f, 1);
        this.zzaEl = null;
        this.zzaEm = new zzb();
        this.zzaEn = new zzb();
        this.mContext = context;
        this.mHandler = new Handler(looper, this);
        this.zzaBf = googleApiAvailability;
        this.mHandler.sendMessage(this.mHandler.obtainMessage(ConnectionResult.RESOLUTION_REQUIRED));
    }

    public static zzben zzay(Context context) {
        zzben com_google_android_gms_internal_zzben;
        synchronized (zzuI) {
            if (zzaEh == null) {
                HandlerThread handlerThread = new HandlerThread("GoogleApiHandler", 9);
                handlerThread.start();
                zzaEh = new zzben(context.getApplicationContext(), handlerThread.getLooper(), GoogleApiAvailability.getInstance());
            }
            com_google_android_gms_internal_zzben = zzaEh;
        }
        return com_google_android_gms_internal_zzben;
    }

    @WorkerThread
    private final void zzc(GoogleApi<?> googleApi) {
        zzbcf zzpf = googleApi.zzpf();
        zzbep com_google_android_gms_internal_zzbep = (zzbep) this.zzaCD.get(zzpf);
        if (com_google_android_gms_internal_zzbep == null) {
            com_google_android_gms_internal_zzbep = new zzbep(this, googleApi);
            this.zzaCD.put(zzpf, com_google_android_gms_internal_zzbep);
        }
        if (com_google_android_gms_internal_zzbep.zzmt()) {
            this.zzaEn.add(zzpf);
        }
        com_google_android_gms_internal_zzbep.connect();
    }

    public static zzben zzqi() {
        zzben com_google_android_gms_internal_zzben;
        synchronized (zzuI) {
            zzbr.zzb(zzaEh, (Object) "Must guarantee manager is non-null before using getInstance");
            com_google_android_gms_internal_zzben = zzaEh;
        }
        return com_google_android_gms_internal_zzben;
    }

    public static void zzqj() {
        synchronized (zzuI) {
            if (zzaEh != null) {
                zzben com_google_android_gms_internal_zzben = zzaEh;
                com_google_android_gms_internal_zzben.zzaEk.incrementAndGet();
                com_google_android_gms_internal_zzben.mHandler.sendMessageAtFrontOfQueue(com_google_android_gms_internal_zzben.mHandler.obtainMessage(ConnectionResult.DEVELOPER_ERROR));
            }
        }
    }

    @WorkerThread
    private final void zzql() {
        for (zzbcf com_google_android_gms_internal_zzbcf : this.zzaEn) {
            ((zzbep) this.zzaCD.remove(com_google_android_gms_internal_zzbcf)).signOut();
        }
        this.zzaEn.clear();
    }

    @WorkerThread
    public final boolean handleMessage(Message message) {
        zzbep com_google_android_gms_internal_zzbep;
        switch (message.what) {
            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                this.zzaEg = ((Boolean) message.obj).booleanValue() ? 10000 : 300000;
                this.mHandler.removeMessages(WeatherCircleView.ARC_DIN);
                for (zzbcf com_google_android_gms_internal_zzbcf : this.zzaCD.keySet()) {
                    this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(WeatherCircleView.ARC_DIN, com_google_android_gms_internal_zzbcf), this.zzaEg);
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_SHOWER:
                zzbch com_google_android_gms_internal_zzbch = (zzbch) message.obj;
                for (zzbcf com_google_android_gms_internal_zzbcf2 : com_google_android_gms_internal_zzbch.zzpr()) {
                    zzbep com_google_android_gms_internal_zzbep2 = (zzbep) this.zzaCD.get(com_google_android_gms_internal_zzbcf2);
                    if (com_google_android_gms_internal_zzbep2 == null) {
                        com_google_android_gms_internal_zzbch.zza(com_google_android_gms_internal_zzbcf2, new ConnectionResult(13));
                    } else if (com_google_android_gms_internal_zzbep2.isConnected()) {
                        com_google_android_gms_internal_zzbch.zza(com_google_android_gms_internal_zzbcf2, ConnectionResult.zzazZ);
                    } else if (com_google_android_gms_internal_zzbep2.zzqs() != null) {
                        com_google_android_gms_internal_zzbch.zza(com_google_android_gms_internal_zzbcf2, com_google_android_gms_internal_zzbep2.zzqs());
                    } else {
                        com_google_android_gms_internal_zzbep2.zza(com_google_android_gms_internal_zzbch);
                    }
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_DOWNPOUR:
                for (zzbep com_google_android_gms_internal_zzbep3 : this.zzaCD.values()) {
                    com_google_android_gms_internal_zzbep3.zzqr();
                    com_google_android_gms_internal_zzbep3.connect();
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_RAINSTORM:
            case DetectedActivity.RUNNING:
            case ConnectionResult.CANCELED:
                zzbfp com_google_android_gms_internal_zzbfp = (zzbfp) message.obj;
                com_google_android_gms_internal_zzbep = (zzbep) this.zzaCD.get(com_google_android_gms_internal_zzbfp.zzaEV.zzpf());
                if (com_google_android_gms_internal_zzbep == null) {
                    zzc(com_google_android_gms_internal_zzbfp.zzaEV);
                    com_google_android_gms_internal_zzbep = (zzbep) this.zzaCD.get(com_google_android_gms_internal_zzbfp.zzaEV.zzpf());
                }
                if (!com_google_android_gms_internal_zzbep.zzmt() || this.zzaEk.get() == com_google_android_gms_internal_zzbfp.zzaEU) {
                    com_google_android_gms_internal_zzbep.zza(com_google_android_gms_internal_zzbfp.zzaET);
                } else {
                    com_google_android_gms_internal_zzbfp.zzaET.zzp(zzaEe);
                    com_google_android_gms_internal_zzbep.signOut();
                }
                break;
            case RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER:
                String valueOf;
                String valueOf2;
                int i = message.arg1;
                ConnectionResult connectionResult = (ConnectionResult) message.obj;
                for (zzbep com_google_android_gms_internal_zzbep4 : this.zzaCD.values()) {
                    if (com_google_android_gms_internal_zzbep4.getInstanceId() == i) {
                        if (com_google_android_gms_internal_zzbep4 == null) {
                            valueOf = String.valueOf(this.zzaBf.getErrorString(connectionResult.getErrorCode()));
                            valueOf2 = String.valueOf(connectionResult.getErrorMessage());
                            com_google_android_gms_internal_zzbep4.zzt(new Status(17, new StringBuilder((String.valueOf(valueOf).length() + 69) + String.valueOf(valueOf2).length()).append("Error resolution was canceled by the user, original error message: ").append(valueOf).append(": ").append(valueOf2).toString()));
                        } else {
                            Log.wtf("GoogleApiManager", new StringBuilder(76).append("Could not find API instance ").append(i).append(" while trying to fail enqueued calls.").toString(), new Exception());
                        }
                    }
                }
                com_google_android_gms_internal_zzbep4 = null;
                if (com_google_android_gms_internal_zzbep4 == null) {
                    Log.wtf("GoogleApiManager", new StringBuilder(76).append("Could not find API instance ").append(i).append(" while trying to fail enqueued calls.").toString(), new Exception());
                } else {
                    valueOf = String.valueOf(this.zzaBf.getErrorString(connectionResult.getErrorCode()));
                    valueOf2 = String.valueOf(connectionResult.getErrorMessage());
                    com_google_android_gms_internal_zzbep4.zzt(new Status(17, new StringBuilder((String.valueOf(valueOf).length() + 69) + String.valueOf(valueOf2).length()).append("Error resolution was canceled by the user, original error message: ").append(valueOf).append(": ").append(valueOf2).toString()));
                }
                break;
            case ConnectionResult.RESOLUTION_REQUIRED:
                if (this.mContext.getApplicationContext() instanceof Application) {
                    zzbci.zza((Application) this.mContext.getApplicationContext());
                    zzbci.zzpt().zza(new zzbeo(this));
                    if (!zzbci.zzpt().zzab(true)) {
                        this.zzaEg = 300000;
                    }
                }
                break;
            case DetectedActivity.WALKING:
                zzc((GoogleApi) message.obj);
                break;
            case ConnectionResult.SERVICE_INVALID:
                if (this.zzaCD.containsKey(message.obj)) {
                    ((zzbep) this.zzaCD.get(message.obj)).resume();
                }
                break;
            case ConnectionResult.DEVELOPER_ERROR:
                zzql();
                break;
            case ConnectionResult.LICENSE_CHECK_FAILED:
                if (this.zzaCD.containsKey(message.obj)) {
                    ((zzbep) this.zzaCD.get(message.obj)).zzqb();
                }
                break;
            case WeatherCircleView.ARC_DIN:
                if (this.zzaCD.containsKey(message.obj)) {
                    ((zzbep) this.zzaCD.get(message.obj)).zzqv();
                }
                break;
            default:
                Log.w("GoogleApiManager", new StringBuilder(31).append("Unknown message id: ").append(message.what).toString());
                return false;
        }
        return true;
    }

    final PendingIntent zza(zzbcf<?> com_google_android_gms_internal_zzbcf_, int i) {
        zzbep com_google_android_gms_internal_zzbep = (zzbep) this.zzaCD.get(com_google_android_gms_internal_zzbcf_);
        if (com_google_android_gms_internal_zzbep == null) {
            return null;
        }
        zzcuw zzqw = com_google_android_gms_internal_zzbep.zzqw();
        return zzqw == null ? null : PendingIntent.getActivity(this.mContext, i, zzqw.zzmF(), 134217728);
    }

    public final <O extends ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbfk<?> com_google_android_gms_internal_zzbfk_) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(ConnectionResult.CANCELED, new zzbfp(new zzbcd(com_google_android_gms_internal_zzbfk_, taskCompletionSource), this.zzaEk.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final <O extends ApiOptions> Task<Void> zza(@NonNull GoogleApi<O> googleApi, @NonNull zzbfq<Api.zzb, ?> com_google_android_gms_internal_zzbfq_com_google_android_gms_common_api_Api_zzb__, @NonNull zzbgk<Api.zzb, ?> com_google_android_gms_internal_zzbgk_com_google_android_gms_common_api_Api_zzb__) {
        TaskCompletionSource taskCompletionSource = new TaskCompletionSource();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(DetectedActivity.RUNNING, new zzbfp(new zzbcb(new zzbfr(com_google_android_gms_internal_zzbfq_com_google_android_gms_common_api_Api_zzb__, com_google_android_gms_internal_zzbgk_com_google_android_gms_common_api_Api_zzb__), taskCompletionSource), this.zzaEk.get(), googleApi)));
        return taskCompletionSource.getTask();
    }

    public final Task<Void> zza(Iterable<? extends GoogleApi<?>> iterable) {
        zzbch com_google_android_gms_internal_zzbch = new zzbch(iterable);
        Iterator it = iterable.iterator();
        while (it.hasNext()) {
            zzbep com_google_android_gms_internal_zzbep = (zzbep) this.zzaCD.get(r0.zzpf());
            if (com_google_android_gms_internal_zzbep != null) {
                if (!com_google_android_gms_internal_zzbep.isConnected()) {
                }
            }
            this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_SHOWER, com_google_android_gms_internal_zzbch));
            return com_google_android_gms_internal_zzbch.getTask();
        }
        com_google_android_gms_internal_zzbch.zzps();
        return com_google_android_gms_internal_zzbch.getTask();
    }

    public final void zza(ConnectionResult connectionResult, int i) {
        if (!zzc(connectionResult, i)) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_THUNDERSHOWER, i, 0, connectionResult));
        }
    }

    public final <O extends ApiOptions> void zza(GoogleApi<O> googleApi, int i, zzbck<? extends Result, Api.zzb> com_google_android_gms_internal_zzbck__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_RAINSTORM, new zzbfp(new zzbca(i, com_google_android_gms_internal_zzbck__extends_com_google_android_gms_common_api_Result__com_google_android_gms_common_api_Api_zzb), this.zzaEk.get(), googleApi)));
    }

    public final <O extends ApiOptions, TResult> void zza(GoogleApi<O> googleApi, int i, zzbgc<Api.zzb, TResult> com_google_android_gms_internal_zzbgc_com_google_android_gms_common_api_Api_zzb__TResult, TaskCompletionSource<TResult> taskCompletionSource, zzbfy com_google_android_gms_internal_zzbfy) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_RAINSTORM, new zzbfp(new zzbcc(i, com_google_android_gms_internal_zzbgc_com_google_android_gms_common_api_Api_zzb__TResult, taskCompletionSource, com_google_android_gms_internal_zzbfy), this.zzaEk.get(), googleApi)));
    }

    public final void zza(@NonNull zzbdi com_google_android_gms_internal_zzbdi) {
        synchronized (zzuI) {
            if (this.zzaEl != com_google_android_gms_internal_zzbdi) {
                this.zzaEl = com_google_android_gms_internal_zzbdi;
                this.zzaEm.clear();
                this.zzaEm.addAll(com_google_android_gms_internal_zzbdi.zzpP());
            }
        }
    }

    public final void zzb(GoogleApi<?> googleApi) {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(DetectedActivity.WALKING, googleApi));
    }

    final void zzb(@NonNull zzbdi com_google_android_gms_internal_zzbdi) {
        synchronized (zzuI) {
            if (this.zzaEl == com_google_android_gms_internal_zzbdi) {
                this.zzaEl = null;
                this.zzaEm.clear();
            }
        }
    }

    final boolean zzc(ConnectionResult connectionResult, int i) {
        return this.zzaBf.zza(this.mContext, connectionResult, i);
    }

    final void zzpj() {
        this.zzaEk.incrementAndGet();
        this.mHandler.sendMessage(this.mHandler.obtainMessage(ConnectionResult.DEVELOPER_ERROR));
    }

    public final void zzpq() {
        this.mHandler.sendMessage(this.mHandler.obtainMessage(RainSurfaceView.RAIN_LEVEL_DOWNPOUR));
    }

    public final int zzqk() {
        return this.zzaEj.getAndIncrement();
    }
}
