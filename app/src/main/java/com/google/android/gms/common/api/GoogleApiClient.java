package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Api.ApiOptions;
import com.google.android.gms.common.api.Api.ApiOptions.HasOptions;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.internal.zzq;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.internal.zzbcg;
import com.google.android.gms.internal.zzbck;
import com.google.android.gms.internal.zzbcu;
import com.google.android.gms.internal.zzbeb;
import com.google.android.gms.internal.zzbfd;
import com.google.android.gms.internal.zzbfi;
import com.google.android.gms.internal.zzbfu;
import com.google.android.gms.internal.zzbge;
import com.google.android.gms.internal.zzcus;
import com.google.android.gms.internal.zzcuw;
import com.google.android.gms.internal.zzcux;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public abstract class GoogleApiClient {
    public static final int SIGN_IN_MODE_OPTIONAL = 2;
    public static final int SIGN_IN_MODE_REQUIRED = 1;
    private static final Set<GoogleApiClient> zzaAU;

    public static final class Builder {
        private final Context mContext;
        private final Set<Scope> zzaAV;
        private final Set<Scope> zzaAW;
        private int zzaAX;
        private View zzaAY;
        private String zzaAZ;
        private final Map<Api<?>, zzr> zzaBa;
        private final Map<Api<?>, ApiOptions> zzaBb;
        private zzbfd zzaBc;
        private int zzaBd;
        private OnConnectionFailedListener zzaBe;
        private GoogleApiAvailability zzaBf;
        private zza<? extends zzcuw, zzcux> zzaBg;
        private final ArrayList<ConnectionCallbacks> zzaBh;
        private final ArrayList<OnConnectionFailedListener> zzaBi;
        private boolean zzaBj;
        private Account zzajd;
        private String zzakg;
        private Looper zzrP;

        public Builder(@NonNull Context context) {
            this.zzaAV = new HashSet();
            this.zzaAW = new HashSet();
            this.zzaBa = new ArrayMap();
            this.zzaBb = new ArrayMap();
            this.zzaBd = -1;
            this.zzaBf = GoogleApiAvailability.getInstance();
            this.zzaBg = zzcus.zzajU;
            this.zzaBh = new ArrayList();
            this.zzaBi = new ArrayList();
            this.zzaBj = false;
            this.mContext = context;
            this.zzrP = context.getMainLooper();
            this.zzakg = context.getPackageName();
            this.zzaAZ = context.getClass().getName();
        }

        public Builder(@NonNull Context context, @NonNull ConnectionCallbacks connectionCallbacks, @NonNull OnConnectionFailedListener onConnectionFailedListener) {
            this(context);
            zzbr.zzb((Object) connectionCallbacks, (Object) "Must provide a connected listener");
            this.zzaBh.add(connectionCallbacks);
            zzbr.zzb((Object) onConnectionFailedListener, (Object) "Must provide a connection failed listener");
            this.zzaBi.add(onConnectionFailedListener);
        }

        private final <O extends ApiOptions> void zza(Api<O> api, O o, Scope... scopeArr) {
            Set hashSet = new HashSet(api.zzoZ().zzn(o));
            for (Object obj : scopeArr) {
                hashSet.add(obj);
            }
            this.zzaBa.put(api, new zzr(hashSet));
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder addApi(@NonNull Api<? extends NotRequiredOptions> api) {
            zzbr.zzb((Object) api, (Object) "Api must not be null");
            this.zzaBb.put(api, null);
            Collection zzn = api.zzoZ().zzn(null);
            this.zzaAW.addAll(zzn);
            this.zzaAV.addAll(zzn);
            return this;
        }

        public final <O extends HasOptions> com.google.android.gms.common.api.GoogleApiClient.Builder addApi(@NonNull Api<O> api, @NonNull O o) {
            zzbr.zzb((Object) api, (Object) "Api must not be null");
            zzbr.zzb((Object) o, (Object) "Null options are not permitted for this Api");
            this.zzaBb.put(api, o);
            Collection zzn = api.zzoZ().zzn(o);
            this.zzaAW.addAll(zzn);
            this.zzaAV.addAll(zzn);
            return this;
        }

        public final <O extends HasOptions> com.google.android.gms.common.api.GoogleApiClient.Builder addApiIfAvailable(@NonNull Api<O> api, @NonNull O o, Scope... scopeArr) {
            zzbr.zzb((Object) api, (Object) "Api must not be null");
            zzbr.zzb((Object) o, (Object) "Null options are not permitted for this Api");
            this.zzaBb.put(api, o);
            zza(api, o, scopeArr);
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder addApiIfAvailable(@NonNull Api<? extends NotRequiredOptions> api, Scope... scopeArr) {
            zzbr.zzb((Object) api, (Object) "Api must not be null");
            this.zzaBb.put(api, null);
            zza(api, null, scopeArr);
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder addConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks) {
            zzbr.zzb((Object) connectionCallbacks, (Object) "Listener must not be null");
            this.zzaBh.add(connectionCallbacks);
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder addOnConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener) {
            zzbr.zzb((Object) onConnectionFailedListener, (Object) "Listener must not be null");
            this.zzaBi.add(onConnectionFailedListener);
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder addScope(@NonNull Scope scope) {
            zzbr.zzb((Object) scope, (Object) "Scope must not be null");
            this.zzaAV.add(scope);
            return this;
        }

        public final GoogleApiClient build() {
            zzbr.zzb(!this.zzaBb.isEmpty(), (Object) "must call addApi() to add at least one API");
            zzq zzpl = zzpl();
            Api api = null;
            Map zzrn = zzpl.zzrn();
            Map arrayMap = new ArrayMap();
            Map arrayMap2 = new ArrayMap();
            ArrayList arrayList = new ArrayList();
            Object obj = null;
            for (Api api2 : this.zzaBb.keySet()) {
                Api api22;
                Object obj2 = this.zzaBb.get(api22);
                boolean z = zzrn.get(api22) != null;
                arrayMap.put(api22, Boolean.valueOf(z));
                ConnectionCallbacks com_google_android_gms_internal_zzbcu = new zzbcu(api22, z);
                arrayList.add(com_google_android_gms_internal_zzbcu);
                zzd zzpa = api22.zzpa();
                zze zza = zzpa.zza(this.mContext, this.zzrP, zzpl, obj2, com_google_android_gms_internal_zzbcu, com_google_android_gms_internal_zzbcu);
                arrayMap2.put(api22.zzpb(), zza);
                Object obj3 = zzpa.getPriority() == 1 ? obj2 != null ? SIGN_IN_MODE_REQUIRED : null : obj;
                if (!zza.zzmE()) {
                    api22 = api;
                } else if (api != null) {
                    String valueOf = String.valueOf(api22.getName());
                    String valueOf2 = String.valueOf(api.getName());
                    throw new IllegalStateException(new StringBuilder((String.valueOf(valueOf).length() + 21) + String.valueOf(valueOf2).length()).append(valueOf).append(" cannot be used with ").append(valueOf2).toString());
                }
                obj = obj3;
                api = api22;
            }
            if (api != null) {
                if (obj != null) {
                    valueOf = String.valueOf(api.getName());
                    throw new IllegalStateException(new StringBuilder(String.valueOf(valueOf).length() + 82).append("With using ").append(valueOf).append(", GamesOptions can only be specified within GoogleSignInOptions.Builder").toString());
                }
                zzbr.zza(this.zzajd == null, "Must not set an account in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead", api.getName());
                zzbr.zza(this.zzaAV.equals(this.zzaAW), "Must not set scopes in GoogleApiClient.Builder when using %s. Set account in GoogleSignInOptions.Builder instead.", api.getName());
            }
            GoogleApiClient com_google_android_gms_internal_zzbeb = new zzbeb(this.mContext, new ReentrantLock(), this.zzrP, zzpl, this.zzaBf, this.zzaBg, arrayMap, this.zzaBh, this.zzaBi, arrayMap2, this.zzaBd, zzbeb.zza(arrayMap2.values(), true), arrayList, false);
            synchronized (zzaAU) {
                zzaAU.add(com_google_android_gms_internal_zzbeb);
            }
            if (this.zzaBd >= 0) {
                zzbcg.zza(this.zzaBc).zza(this.zzaBd, com_google_android_gms_internal_zzbeb, this.zzaBe);
            }
            return com_google_android_gms_internal_zzbeb;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, int i, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            zzbfd com_google_android_gms_internal_zzbfd = new zzbfd(fragmentActivity);
            zzbr.zzb(i >= 0, (Object) "clientId must be non-negative");
            this.zzaBd = i;
            this.zzaBe = onConnectionFailedListener;
            this.zzaBc = com_google_android_gms_internal_zzbfd;
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder enableAutoManage(@NonNull FragmentActivity fragmentActivity, @Nullable OnConnectionFailedListener onConnectionFailedListener) {
            return enableAutoManage(fragmentActivity, 0, onConnectionFailedListener);
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder setAccountName(String str) {
            this.zzajd = str == null ? null : new Account(str, "com.google");
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder setGravityForPopups(int i) {
            this.zzaAX = i;
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder setHandler(@NonNull Handler handler) {
            zzbr.zzb((Object) handler, (Object) "Handler must not be null");
            this.zzrP = handler.getLooper();
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder setViewForPopups(@NonNull View view) {
            zzbr.zzb((Object) view, (Object) "View must not be null");
            this.zzaAY = view;
            return this;
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder useDefaultAccount() {
            return setAccountName("<<default account>>");
        }

        public final com.google.android.gms.common.api.GoogleApiClient.Builder zze(Account account) {
            this.zzajd = account;
            return this;
        }

        public final zzq zzpl() {
            zzcux com_google_android_gms_internal_zzcux = zzcux.zzbCQ;
            if (this.zzaBb.containsKey(zzcus.API)) {
                com_google_android_gms_internal_zzcux = (zzcux) this.zzaBb.get(zzcus.API);
            }
            return new zzq(this.zzajd, this.zzaAV, this.zzaBa, this.zzaAX, this.zzaAY, this.zzakg, this.zzaAZ, com_google_android_gms_internal_zzcux);
        }
    }

    public static interface ConnectionCallbacks {
        public static final int CAUSE_NETWORK_LOST = 2;
        public static final int CAUSE_SERVICE_DISCONNECTED = 1;

        void onConnected(@Nullable Bundle bundle);

        void onConnectionSuspended(int i);
    }

    public static interface OnConnectionFailedListener {
        void onConnectionFailed(@NonNull ConnectionResult connectionResult);
    }

    static {
        zzaAU = Collections.newSetFromMap(new WeakHashMap());
    }

    public static void dumpAll(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        synchronized (zzaAU) {
            String concat = String.valueOf(str).concat("  ");
            int i = 0;
            for (GoogleApiClient googleApiClient : zzaAU) {
                int i2 = i + 1;
                printWriter.append(str).append("GoogleApiClient#").println(i);
                googleApiClient.dump(concat, fileDescriptor, printWriter, strArr);
                i = i2;
            }
        }
    }

    public static Set<GoogleApiClient> zzpi() {
        Set<GoogleApiClient> set;
        synchronized (zzaAU) {
            set = zzaAU;
        }
        return set;
    }

    public abstract ConnectionResult blockingConnect();

    public abstract ConnectionResult blockingConnect(long j, @NonNull TimeUnit timeUnit);

    public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

    public abstract void connect();

    public void connect(int i) {
        throw new UnsupportedOperationException();
    }

    public abstract void disconnect();

    public abstract void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    @NonNull
    public abstract ConnectionResult getConnectionResult(@NonNull Api<?> api);

    public Context getContext() {
        throw new UnsupportedOperationException();
    }

    public Looper getLooper() {
        throw new UnsupportedOperationException();
    }

    public abstract boolean hasConnectedApi(@NonNull Api<?> api);

    public abstract boolean isConnected();

    public abstract boolean isConnecting();

    public abstract boolean isConnectionCallbacksRegistered(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract boolean isConnectionFailedListenerRegistered(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void reconnect();

    public abstract void registerConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void registerConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    public abstract void stopAutoManage(@NonNull FragmentActivity fragmentActivity);

    public abstract void unregisterConnectionCallbacks(@NonNull ConnectionCallbacks connectionCallbacks);

    public abstract void unregisterConnectionFailedListener(@NonNull OnConnectionFailedListener onConnectionFailedListener);

    @NonNull
    public <C extends zze> C zza(@NonNull zzc<C> com_google_android_gms_common_api_Api_zzc_C) {
        throw new UnsupportedOperationException();
    }

    public void zza(zzbge com_google_android_gms_internal_zzbge) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(@NonNull Api<?> api) {
        throw new UnsupportedOperationException();
    }

    public boolean zza(zzbfu com_google_android_gms_internal_zzbfu) {
        throw new UnsupportedOperationException();
    }

    public void zzb(zzbge com_google_android_gms_internal_zzbge) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, R extends Result, T extends zzbck<R, A>> T zzd(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <A extends zzb, T extends zzbck<? extends Result, A>> T zze(@NonNull T t) {
        throw new UnsupportedOperationException();
    }

    public <L> zzbfi<L> zzp(@NonNull L l) {
        throw new UnsupportedOperationException();
    }

    public void zzpj() {
        throw new UnsupportedOperationException();
    }
}
