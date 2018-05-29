package com.google.android.gms.dynamite;

import android.content.Context;
import android.database.Cursor;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzbr;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.common.zze;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule.zzc;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class DynamiteModule {
    private static Boolean zzaSJ;
    private static zzj zzaSK;
    private static zzl zzaSL;
    private static String zzaSM;
    private static final ThreadLocal<zza> zzaSN;
    private static final zzh zzaSO;
    public static final zzd zzaSP;
    private static zzd zzaSQ;
    public static final zzd zzaSR;
    public static final zzd zzaSS;
    public static final zzd zzaST;
    private final Context zzaSU;

    @DynamiteApi
    public static class DynamiteLoaderClassLoader {
        public static ClassLoader sClassLoader;
    }

    static class zza {
        public Cursor zzaSV;

        private zza() {
        }
    }

    public static class zzc extends Exception {
        private zzc(String str) {
            super(str);
        }

        private zzc(String str, Throwable th) {
            super(str, th);
        }
    }

    public static interface zzd {
        zzi zza(Context context, String str, zzh com_google_android_gms_dynamite_zzh) throws zzc;
    }

    static class zzb implements zzh {
        private final int zzaSW;
        private final int zzaSX;

        public zzb(int i, int i2) {
            this.zzaSW = i;
            this.zzaSX = 0;
        }

        public final int zzF(Context context, String str) {
            return this.zzaSW;
        }

        public final int zzb(Context context, String str, boolean z) {
            return 0;
        }
    }

    static {
        zzaSN = new ThreadLocal();
        zzaSO = new zza();
        zzaSP = new zzb();
        zzaSQ = new zzc();
        zzaSR = new zzd();
        zzaSS = new zze();
        zzaST = new zzf();
    }

    private DynamiteModule(Context context) {
        this.zzaSU = (Context) zzbr.zzu(context);
    }

    public static int zzF(Context context, String str) {
        try {
            ClassLoader classLoader = context.getApplicationContext().getClassLoader();
            String valueOf = String.valueOf("com.google.android.gms.dynamite.descriptors.");
            String valueOf2 = String.valueOf("ModuleDescriptor");
            Class loadClass = classLoader.loadClass(new StringBuilder(((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length()) + String.valueOf(valueOf2).length()).append(valueOf).append(str).append(".").append(valueOf2).toString());
            Field declaredField = loadClass.getDeclaredField("MODULE_ID");
            Field declaredField2 = loadClass.getDeclaredField("MODULE_VERSION");
            if (declaredField.get(null).equals(str)) {
                return declaredField2.getInt(null);
            }
            valueOf = String.valueOf(declaredField.get(null));
            Log.e("DynamiteModule", new StringBuilder((String.valueOf(valueOf).length() + 51) + String.valueOf(str).length()).append("Module descriptor id '").append(valueOf).append("' didn't match expected id '").append(str).append("'").toString());
            return 0;
        } catch (ClassNotFoundException e) {
            Log.w("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 45).append("Local module descriptor class for ").append(str).append(" not found.").toString());
            return 0;
        } catch (Exception e2) {
            valueOf = "DynamiteModule";
            valueOf2 = "Failed to load module descriptor class: ";
            String valueOf3 = String.valueOf(e2.getMessage());
            Log.e(valueOf, valueOf3.length() != 0 ? valueOf2.concat(valueOf3) : new String(valueOf2));
            return 0;
        }
    }

    public static int zzG(Context context, String str) {
        return zzb(context, str, false);
    }

    private static DynamiteModule zzH(Context context, String str) {
        String str2 = "DynamiteModule";
        String str3 = "Selected local version of ";
        String valueOf = String.valueOf(str);
        Log.i(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        return new DynamiteModule(context.getApplicationContext());
    }

    private static Context zza(Context context, String str, int i, Cursor cursor, zzl com_google_android_gms_dynamite_zzl) {
        try {
            return (Context) zzn.zzE(com_google_android_gms_dynamite_zzl.zza(zzn.zzw(context), str, i, zzn.zzw(cursor)));
        } catch (Exception e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load DynamiteLoader: ";
            String valueOf = String.valueOf(e.toString());
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
    }

    public static DynamiteModule zza(Context context, zzd com_google_android_gms_dynamite_DynamiteModule_zzd, String str) throws zzc {
        zza com_google_android_gms_dynamite_DynamiteModule_zza = (zza) zzaSN.get();
        zza com_google_android_gms_dynamite_DynamiteModule_zza2 = new zza();
        zzaSN.set(com_google_android_gms_dynamite_DynamiteModule_zza2);
        try {
            DynamiteModule zzH;
            zzi zza = com_google_android_gms_dynamite_DynamiteModule_zzd.zza(context, str, zzaSO);
            Log.i("DynamiteModule", new StringBuilder((String.valueOf(str).length() + 68) + String.valueOf(str).length()).append("Considering local module ").append(str).append(":").append(zza.zzaSY).append(" and remote module ").append(str).append(":").append(zza.zzaSZ).toString());
            if (zza.zzaTa != 0) {
                if (!((zza.zzaTa == -1 && zza.zzaSY == 0) || (zza.zzaTa == 1 && zza.zzaSZ == 0))) {
                    if (zza.zzaTa == -1) {
                        zzH = zzH(context, str);
                        if (com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV != null) {
                            com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV.close();
                        }
                        zzaSN.set(com_google_android_gms_dynamite_DynamiteModule_zza);
                        return zzH;
                    } else if (zza.zzaTa == 1) {
                        zzH = zza(context, str, zza.zzaSZ);
                        if (com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV != null) {
                            com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV.close();
                        }
                        zzaSN.set(com_google_android_gms_dynamite_DynamiteModule_zza);
                        return zzH;
                    } else {
                        throw new zzc(null);
                    }
                }
            }
            throw new zzc(null);
        } catch (Throwable e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to load remote module: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            if (zza.zzaSY == 0 || com_google_android_gms_dynamite_DynamiteModule_zzd.zza(context, str, new zzb(zza.zzaSY, 0)).zzaTa != -1) {
                throw new zzc(e, null);
            }
            zzH = zzH(context, str);
            if (com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV != null) {
                com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV.close();
            }
            zzaSN.set(com_google_android_gms_dynamite_DynamiteModule_zza);
            return zzH;
        } catch (Throwable th) {
            if (com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV != null) {
                com_google_android_gms_dynamite_DynamiteModule_zza2.zzaSV.close();
            }
        }
    }

    private static DynamiteModule zza(Context context, String str, int i) throws zzc {
        synchronized (DynamiteModule.class) {
            Boolean bool = zzaSJ;
        }
        if (bool != null) {
            return bool.booleanValue() ? zzc(context, str, i) : zzb(context, str, i);
        } else {
            throw new zzc(null);
        }
    }

    private static void zza(ClassLoader classLoader) throws zzc {
        try {
            zzl com_google_android_gms_dynamite_zzl;
            IBinder iBinder = (IBinder) classLoader.loadClass("com.google.android.gms.dynamiteloader.DynamiteLoaderV2").getConstructor(new Class[0]).newInstance(new Object[0]);
            if (iBinder == null) {
                com_google_android_gms_dynamite_zzl = null;
            } else {
                IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoaderV2");
                if (queryLocalInterface instanceof zzl) {
                    com_google_android_gms_dynamite_zzl = (zzl) queryLocalInterface;
                } else {
                    zzm com_google_android_gms_dynamite_zzm = new zzm(iBinder);
                }
            }
            zzaSL = com_google_android_gms_dynamite_zzl;
        } catch (ClassNotFoundException e) {
            Throwable e2 = e;
            throw new zzc(e2, null);
        } catch (IllegalAccessException e3) {
            e2 = e3;
            throw new zzc(e2, null);
        } catch (InstantiationException e4) {
            e2 = e4;
            throw new zzc(e2, null);
        } catch (InvocationTargetException e5) {
            e2 = e5;
            throw new zzc(e2, null);
        } catch (NoSuchMethodException e6) {
            e2 = e6;
            throw new zzc(e2, null);
        }
    }

    private static zzj zzaT(Context context) {
        synchronized (DynamiteModule.class) {
            zzj com_google_android_gms_dynamite_zzj;
            if (zzaSK != null) {
                com_google_android_gms_dynamite_zzj = zzaSK;
                return com_google_android_gms_dynamite_zzj;
            } else if (zze.zzoU().isGooglePlayServicesAvailable(context) != 0) {
                return null;
            } else {
                try {
                    IBinder iBinder = (IBinder) context.createPackageContext(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, RainSurfaceView.RAIN_LEVEL_DOWNPOUR).getClassLoader().loadClass("com.google.android.gms.chimera.container.DynamiteLoaderImpl").newInstance();
                    if (iBinder == null) {
                        com_google_android_gms_dynamite_zzj = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamite.IDynamiteLoader");
                        if (queryLocalInterface instanceof zzj) {
                            com_google_android_gms_dynamite_zzj = (zzj) queryLocalInterface;
                        } else {
                            zzk com_google_android_gms_dynamite_zzk = new zzk(iBinder);
                        }
                    }
                    if (com_google_android_gms_dynamite_zzj != null) {
                        zzaSK = com_google_android_gms_dynamite_zzj;
                        return com_google_android_gms_dynamite_zzj;
                    }
                } catch (Exception e) {
                    String str = "DynamiteModule";
                    String str2 = "Failed to load IDynamiteLoader from GmsCore: ";
                    String valueOf = String.valueOf(e.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                }
                return null;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static int zzb(android.content.Context r7, java.lang.String r8, boolean r9) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzb(android.content.Context, java.lang.String, boolean):int");
        /*
        r1 = com.google.android.gms.dynamite.DynamiteModule.class;
        monitor-enter(r1);
        r0 = zzaSJ;	 Catch:{ all -> 0x0074 }
        if (r0 != 0) goto L_0x0034;
    L_0x0007:
        r0 = r7.getApplicationContext();	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        r0 = r0.getClassLoader();	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        r2 = com.google.android.gms.dynamite.DynamiteModule.DynamiteLoaderClassLoader.class;
        r2 = r2.getName();	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        r2 = r0.loadClass(r2);	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        r0 = "sClassLoader";
        r3 = r2.getDeclaredField(r0);	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        monitor-enter(r2);	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
        r0 = 0;
        r0 = r3.get(r0);	 Catch:{ all -> 0x009c }
        r0 = (java.lang.ClassLoader) r0;	 Catch:{ all -> 0x009c }
        if (r0 == 0) goto L_0x0046;
    L_0x0029:
        r3 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x009c }
        if (r0 != r3) goto L_0x0040;
    L_0x002f:
        r0 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x009c }
    L_0x0031:
        monitor-exit(r2);	 Catch:{ all -> 0x009c }
    L_0x0032:
        zzaSJ = r0;	 Catch:{ all -> 0x0074 }
    L_0x0034:
        monitor-exit(r1);	 Catch:{ all -> 0x0074 }
        r0 = r0.booleanValue();
        if (r0 == 0) goto L_0x00ed;
    L_0x003b:
        r0 = zzd(r7, r8, r9);	 Catch:{ zzc -> 0x00ca }
    L_0x003f:
        return r0;
    L_0x0040:
        zza(r0);	 Catch:{ zzc -> 0x00f3 }
    L_0x0043:
        r0 = java.lang.Boolean.TRUE;	 Catch:{ all -> 0x009c }
        goto L_0x0031;
    L_0x0046:
        r0 = "com.google.android.gms";
        r4 = r7.getApplicationContext();	 Catch:{ all -> 0x009c }
        r4 = r4.getPackageName();	 Catch:{ all -> 0x009c }
        r0 = r0.equals(r4);	 Catch:{ all -> 0x009c }
        if (r0 == 0) goto L_0x0061;
    L_0x0056:
        r0 = 0;
        r4 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x009c }
        r3.set(r0, r4);	 Catch:{ all -> 0x009c }
        r0 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x009c }
        goto L_0x0031;
    L_0x0061:
        r0 = zzd(r7, r8, r9);	 Catch:{ zzc -> 0x0090 }
        r4 = zzaSM;	 Catch:{ zzc -> 0x0090 }
        if (r4 == 0) goto L_0x0071;
    L_0x0069:
        r4 = zzaSM;	 Catch:{ zzc -> 0x0090 }
        r4 = r4.isEmpty();	 Catch:{ zzc -> 0x0090 }
        if (r4 == 0) goto L_0x0077;
    L_0x0071:
        monitor-exit(r2);	 Catch:{ all -> 0x009c }
        monitor-exit(r1);	 Catch:{ all -> 0x0074 }
        goto L_0x003f;
    L_0x0074:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0074 }
        throw r0;
    L_0x0077:
        r4 = new com.google.android.gms.dynamite.zzg;	 Catch:{ zzc -> 0x0090 }
        r5 = zzaSM;	 Catch:{ zzc -> 0x0090 }
        r6 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ zzc -> 0x0090 }
        r4.<init>(r5, r6);	 Catch:{ zzc -> 0x0090 }
        zza(r4);	 Catch:{ zzc -> 0x0090 }
        r5 = 0;
        r3.set(r5, r4);	 Catch:{ zzc -> 0x0090 }
        r4 = java.lang.Boolean.TRUE;	 Catch:{ zzc -> 0x0090 }
        zzaSJ = r4;	 Catch:{ zzc -> 0x0090 }
        monitor-exit(r2);	 Catch:{ all -> 0x009c }
        monitor-exit(r1);	 Catch:{ all -> 0x0074 }
        goto L_0x003f;
    L_0x0090:
        r0 = move-exception;
        r0 = 0;
        r4 = java.lang.ClassLoader.getSystemClassLoader();	 Catch:{ all -> 0x009c }
        r3.set(r0, r4);	 Catch:{ all -> 0x009c }
        r0 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x009c }
        goto L_0x0031;
    L_0x009c:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x009c }
        throw r0;	 Catch:{ ClassNotFoundException -> 0x009f, IllegalAccessException -> 0x00f8, NoSuchFieldException -> 0x00f6 }
    L_0x009f:
        r0 = move-exception;
    L_0x00a0:
        r2 = "DynamiteModule";
        r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0074 }
        r3 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0074 }
        r3 = r3.length();	 Catch:{ all -> 0x0074 }
        r3 = r3 + 30;
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0074 }
        r4.<init>(r3);	 Catch:{ all -> 0x0074 }
        r3 = "Failed to load module via V2: ";
        r3 = r4.append(r3);	 Catch:{ all -> 0x0074 }
        r0 = r3.append(r0);	 Catch:{ all -> 0x0074 }
        r0 = r0.toString();	 Catch:{ all -> 0x0074 }
        android.util.Log.w(r2, r0);	 Catch:{ all -> 0x0074 }
        r0 = java.lang.Boolean.FALSE;	 Catch:{ all -> 0x0074 }
        goto L_0x0032;
    L_0x00ca:
        r0 = move-exception;
        r1 = "DynamiteModule";
        r2 = "Failed to retrieve remote module version: ";
        r0 = r0.getMessage();
        r0 = java.lang.String.valueOf(r0);
        r3 = r0.length();
        if (r3 == 0) goto L_0x00e7;
    L_0x00dd:
        r0 = r2.concat(r0);
    L_0x00e1:
        android.util.Log.w(r1, r0);
        r0 = 0;
        goto L_0x003f;
    L_0x00e7:
        r0 = new java.lang.String;
        r0.<init>(r2);
        goto L_0x00e1;
    L_0x00ed:
        r0 = zzc(r7, r8, r9);
        goto L_0x003f;
    L_0x00f3:
        r0 = move-exception;
        goto L_0x0043;
    L_0x00f6:
        r0 = move-exception;
        goto L_0x00a0;
    L_0x00f8:
        r0 = move-exception;
        goto L_0x00a0;
        */
    }

    private static DynamiteModule zzb(Context context, String str, int i) throws zzc {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        zzj zzaT = zzaT(context);
        if (zzaT == null) {
            throw new zzc(null);
        }
        try {
            IObjectWrapper zza = zzaT.zza(zzn.zzw(context), str, i);
            if (zzn.zzE(zza) != null) {
                return new DynamiteModule((Context) zzn.zzE(zza));
            }
            throw new zzc(null);
        } catch (Throwable e) {
            throw new zzc(e, null);
        }
    }

    private static int zzc(Context context, String str, boolean z) {
        zzj zzaT = zzaT(context);
        if (zzaT == null) {
            return 0;
        }
        try {
            return zzaT.zza(zzn.zzw(context), str, z);
        } catch (RemoteException e) {
            String str2 = "DynamiteModule";
            String str3 = "Failed to retrieve remote module version: ";
            String valueOf = String.valueOf(e.getMessage());
            Log.w(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return 0;
        }
    }

    private static DynamiteModule zzc(Context context, String str, int i) throws zzc {
        Log.i("DynamiteModule", new StringBuilder(String.valueOf(str).length() + 51).append("Selected remote version of ").append(str).append(", version >= ").append(i).toString());
        synchronized (DynamiteModule.class) {
            zzl com_google_android_gms_dynamite_zzl = zzaSL;
        }
        if (com_google_android_gms_dynamite_zzl == null) {
            throw new zzc(null);
        }
        zza com_google_android_gms_dynamite_DynamiteModule_zza = (zza) zzaSN.get();
        if (com_google_android_gms_dynamite_DynamiteModule_zza == null || com_google_android_gms_dynamite_DynamiteModule_zza.zzaSV == null) {
            throw new zzc(null);
        }
        Context zza = zza(context.getApplicationContext(), str, i, com_google_android_gms_dynamite_DynamiteModule_zza.zzaSV, com_google_android_gms_dynamite_zzl);
        if (zza != null) {
            return new DynamiteModule(zza);
        }
        throw new zzc(null);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int zzd(android.content.Context r7, java.lang.String r8, boolean r9) throws com.google.android.gms.dynamite.DynamiteModule.zzc {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.dynamite.DynamiteModule.zzd(android.content.Context, java.lang.String, boolean):int");
        /*
        r6 = 0;
        if (r9 == 0) goto L_0x0077;
    L_0x0003:
        r0 = "api_force_staging";
    L_0x0005:
        r1 = "content://com.google.android.gms.chimera/";
        r1 = java.lang.String.valueOf(r1);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = java.lang.String.valueOf(r1);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = r2.length();	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = r2 + 1;
        r3 = java.lang.String.valueOf(r0);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r3 = r3.length();	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = r2 + r3;
        r3 = java.lang.String.valueOf(r8);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r3 = r3.length();	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = r2 + r3;
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r3.<init>(r2);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r1 = r3.append(r1);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r0 = r1.append(r0);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r1 = "/";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r0 = r0.append(r8);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r0 = r0.toString();	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r1 = android.net.Uri.parse(r0);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r0 = r7.getContentResolver();	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r1 = r0.query(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x00b2, all -> 0x00af }
        if (r1 == 0) goto L_0x005a;
    L_0x0054:
        r0 = r1.moveToFirst();	 Catch:{ Exception -> 0x006a }
        if (r0 != 0) goto L_0x007a;
    L_0x005a:
        r0 = "DynamiteModule";
        r2 = "Failed to retrieve remote module version.";
        android.util.Log.w(r0, r2);	 Catch:{ Exception -> 0x006a }
        r0 = new com.google.android.gms.dynamite.DynamiteModule$zzc;	 Catch:{ Exception -> 0x006a }
        r2 = "Failed to connect to dynamite module ContentResolver.";
        r3 = 0;
        r0.<init>(r3);	 Catch:{ Exception -> 0x006a }
        throw r0;	 Catch:{ Exception -> 0x006a }
    L_0x006a:
        r0 = move-exception;
    L_0x006b:
        r2 = r0 instanceof com.google.android.gms.dynamite.DynamiteModule.zzc;	 Catch:{ all -> 0x0070 }
        if (r2 == 0) goto L_0x00a6;
    L_0x006f:
        throw r0;	 Catch:{ all -> 0x0070 }
    L_0x0070:
        r0 = move-exception;
    L_0x0071:
        if (r1 == 0) goto L_0x0076;
    L_0x0073:
        r1.close();
    L_0x0076:
        throw r0;
    L_0x0077:
        r0 = "api";
        goto L_0x0005;
    L_0x007a:
        r0 = 0;
        r2 = r1.getInt(r0);	 Catch:{ Exception -> 0x006a }
        if (r2 <= 0) goto L_0x009d;
    L_0x0081:
        r3 = com.google.android.gms.dynamite.DynamiteModule.class;
        monitor-enter(r3);	 Catch:{ Exception -> 0x006a }
        r0 = 2;
        r0 = r1.getString(r0);	 Catch:{ all -> 0x00a3 }
        zzaSM = r0;	 Catch:{ all -> 0x00a3 }
        monitor-exit(r3);	 Catch:{ all -> 0x00a3 }
        r0 = zzaSN;	 Catch:{ Exception -> 0x006a }
        r0 = r0.get();	 Catch:{ Exception -> 0x006a }
        r0 = (com.google.android.gms.dynamite.DynamiteModule.zza) r0;	 Catch:{ Exception -> 0x006a }
        if (r0 == 0) goto L_0x009d;
    L_0x0096:
        r3 = r0.zzaSV;	 Catch:{ Exception -> 0x006a }
        if (r3 != 0) goto L_0x009d;
    L_0x009a:
        r0.zzaSV = r1;	 Catch:{ Exception -> 0x006a }
        r1 = r6;
    L_0x009d:
        if (r1 == 0) goto L_0x00a2;
    L_0x009f:
        r1.close();
    L_0x00a2:
        return r2;
    L_0x00a3:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x00a3 }
        throw r0;	 Catch:{ Exception -> 0x006a }
    L_0x00a6:
        r2 = new com.google.android.gms.dynamite.DynamiteModule$zzc;	 Catch:{ all -> 0x0070 }
        r3 = "V2 version check failed";
        r4 = 0;
        r2.<init>(r0, r4);	 Catch:{ all -> 0x0070 }
        throw r2;	 Catch:{ all -> 0x0070 }
    L_0x00af:
        r0 = move-exception;
        r1 = r6;
        goto L_0x0071;
    L_0x00b2:
        r0 = move-exception;
        r1 = r6;
        goto L_0x006b;
        */
    }

    public final IBinder zzcW(String str) throws zzc {
        try {
            return (IBinder) this.zzaSU.getClassLoader().loadClass(str).newInstance();
        } catch (ClassNotFoundException e) {
            Throwable e2 = e;
            String str2 = "Failed to instantiate module class: ";
            String valueOf = String.valueOf(str);
            throw new zzc(e2, null);
        } catch (InstantiationException e3) {
            e2 = e3;
            String str22 = "Failed to instantiate module class: ";
            String valueOf2 = String.valueOf(str);
            if (valueOf2.length() != 0) {
            }
            throw new zzc(e2, null);
        } catch (IllegalAccessException e4) {
            e2 = e4;
            String str222 = "Failed to instantiate module class: ";
            String valueOf22 = String.valueOf(str);
            if (valueOf22.length() != 0) {
            }
            throw new zzc(e2, null);
        }
    }

    public final Context zztB() {
        return this.zzaSU;
    }
}
