package com.google.android.gms.internal;

import android.os.SystemClock;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class zzag implements zzb {
    private final Map<String, zzai> zzav;
    private long zzaw;
    private final File zzax;
    private final int zzay;

    public zzag(File file) {
        this(file, 5242880);
    }

    private zzag(File file, int i) {
        this.zzav = new LinkedHashMap(16, 0.75f, true);
        this.zzaw = 0;
        this.zzax = file;
        this.zzay = 5242880;
    }

    private final synchronized void remove(String str) {
        boolean delete = zze(str).delete();
        zzai com_google_android_gms_internal_zzai = (zzai) this.zzav.get(str);
        if (com_google_android_gms_internal_zzai != null) {
            this.zzaw -= com_google_android_gms_internal_zzai.zzaz;
            this.zzav.remove(str);
        }
        if (!delete) {
            zzab.zzb("Could not delete cache entry for key=%s, filename=%s", str, zzd(str));
        }
    }

    private static int zza(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return read;
        }
        throw new EOFException();
    }

    static void zza(OutputStream outputStream, int i) throws IOException {
        outputStream.write(i & 255);
        outputStream.write((i >> 8) & 255);
        outputStream.write((i >> 16) & 255);
        outputStream.write(i >>> 24);
    }

    static void zza(OutputStream outputStream, long j) throws IOException {
        outputStream.write((byte) ((int) j));
        outputStream.write((byte) ((int) (j >>> 8)));
        outputStream.write((byte) ((int) (j >>> 16)));
        outputStream.write((byte) ((int) (j >>> 24)));
        outputStream.write((byte) ((int) (j >>> 32)));
        outputStream.write((byte) ((int) (j >>> 40)));
        outputStream.write((byte) ((int) (j >>> 48)));
        outputStream.write((byte) ((int) (j >>> 56)));
    }

    static void zza(OutputStream outputStream, String str) throws IOException {
        byte[] bytes = str.getBytes("UTF-8");
        zza(outputStream, (long) bytes.length);
        outputStream.write(bytes, 0, bytes.length);
    }

    private final void zza(String str, zzai com_google_android_gms_internal_zzai) {
        if (this.zzav.containsKey(str)) {
            zzai com_google_android_gms_internal_zzai2 = (zzai) this.zzav.get(str);
            this.zzaw = (com_google_android_gms_internal_zzai.zzaz - com_google_android_gms_internal_zzai2.zzaz) + this.zzaw;
        } else {
            this.zzaw += com_google_android_gms_internal_zzai.zzaz;
        }
        this.zzav.put(str, com_google_android_gms_internal_zzai);
    }

    private static byte[] zza(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        int i2 = 0;
        while (i2 < i) {
            int read = inputStream.read(bArr, i2, i - i2);
            if (read == -1) {
                break;
            }
            i2 += read;
        }
        if (i2 == i) {
            return bArr;
        }
        throw new IOException(new StringBuilder(50).append("Expected ").append(i).append(" bytes, read ").append(i2).append(" bytes").toString());
    }

    static int zzb(InputStream inputStream) throws IOException {
        return (((zza(inputStream) | 0) | (zza(inputStream) << 8)) | (zza(inputStream) << 16)) | (zza(inputStream) << 24);
    }

    static long zzc(InputStream inputStream) throws IOException {
        return (((((((0 | (((long) zza(inputStream)) & 255)) | ((((long) zza(inputStream)) & 255) << 8)) | ((((long) zza(inputStream)) & 255) << 16)) | ((((long) zza(inputStream)) & 255) << 24)) | ((((long) zza(inputStream)) & 255) << 32)) | ((((long) zza(inputStream)) & 255) << 40)) | ((((long) zza(inputStream)) & 255) << 48)) | ((((long) zza(inputStream)) & 255) << 56);
    }

    static String zzd(InputStream inputStream) throws IOException {
        return new String(zza(inputStream, (int) zzc(inputStream)), "UTF-8");
    }

    private static String zzd(String str) {
        int length = str.length() / 2;
        String valueOf = String.valueOf(String.valueOf(str.substring(0, length).hashCode()));
        String valueOf2 = String.valueOf(String.valueOf(str.substring(length).hashCode()));
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private final File zze(String str) {
        return new File(this.zzax, zzd(str));
    }

    static Map<String, String> zze(InputStream inputStream) throws IOException {
        int zzb = zzb(inputStream);
        Map<String, String> emptyMap = zzb == 0 ? Collections.emptyMap() : new HashMap(zzb);
        for (int i = 0; i < zzb; i++) {
            emptyMap.put(zzd(inputStream).intern(), zzd(inputStream).intern());
        }
        return emptyMap;
    }

    public final synchronized void initialize() {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzag.initialize():void");
        /* JADX: method processing error */
/*
        Error: jadx.core.utils.exceptions.JadxRuntimeException: Try/catch wrap count limit reached in com.google.android.gms.internal.zzag.initialize():void
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:54)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:40)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:27)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:16)
	at jadx.core.ProcessClass.process(ProcessClass.java:22)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:209)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:133)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(Unknown Source)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(Unknown Source)
	at java.lang.Thread.run(Unknown Source)
*/
        /*
        this = this;
        r0 = 0;
        monitor-enter(r9);
        r1 = r9.zzax;	 Catch:{ all -> 0x006a }
        r1 = r1.exists();	 Catch:{ all -> 0x006a }
        if (r1 != 0) goto L_0x0025;
    L_0x000a:
        r0 = r9.zzax;	 Catch:{ all -> 0x006a }
        r0 = r0.mkdirs();	 Catch:{ all -> 0x006a }
        if (r0 != 0) goto L_0x0023;
    L_0x0012:
        r0 = "Unable to create cache dir %s";
        r1 = 1;
        r1 = new java.lang.Object[r1];	 Catch:{ all -> 0x006a }
        r2 = 0;
        r3 = r9.zzax;	 Catch:{ all -> 0x006a }
        r3 = r3.getAbsolutePath();	 Catch:{ all -> 0x006a }
        r1[r2] = r3;	 Catch:{ all -> 0x006a }
        com.google.android.gms.internal.zzab.zzc(r0, r1);	 Catch:{ all -> 0x006a }
    L_0x0023:
        monitor-exit(r9);
        return;
    L_0x0025:
        r1 = r9.zzax;	 Catch:{ all -> 0x006a }
        r3 = r1.listFiles();	 Catch:{ all -> 0x006a }
        if (r3 == 0) goto L_0x0023;
    L_0x002d:
        r4 = r3.length;	 Catch:{ all -> 0x006a }
        r2 = r0;
    L_0x002f:
        if (r2 >= r4) goto L_0x0023;
    L_0x0031:
        r5 = r3[r2];	 Catch:{ all -> 0x006a }
        r1 = 0;
        r0 = new java.io.BufferedInputStream;	 Catch:{ IOException -> 0x0054, all -> 0x0063 }
        r6 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x0054, all -> 0x0063 }
        r6.<init>(r5);	 Catch:{ IOException -> 0x0054, all -> 0x0063 }
        r0.<init>(r6);	 Catch:{ IOException -> 0x0054, all -> 0x0063 }
        r1 = com.google.android.gms.internal.zzai.zzf(r0);	 Catch:{ IOException -> 0x0076 }
        r6 = r5.length();	 Catch:{ IOException -> 0x0076 }
        r1.zzaz = r6;	 Catch:{ IOException -> 0x0076 }
        r6 = r1.key;	 Catch:{ IOException -> 0x0076 }
        r9.zza(r6, r1);	 Catch:{ IOException -> 0x0076 }
        r0.close();	 Catch:{ IOException -> 0x006d }
    L_0x0050:
        r0 = r2 + 1;
        r2 = r0;
        goto L_0x002f;
    L_0x0054:
        r0 = move-exception;
        r0 = r1;
    L_0x0056:
        if (r5 == 0) goto L_0x005b;
    L_0x0058:
        r5.delete();	 Catch:{ all -> 0x0071 }
    L_0x005b:
        if (r0 == 0) goto L_0x0050;
    L_0x005d:
        r0.close();	 Catch:{ IOException -> 0x0061 }
        goto L_0x0050;
    L_0x0061:
        r0 = move-exception;
        goto L_0x0050;
    L_0x0063:
        r0 = move-exception;
    L_0x0064:
        if (r1 == 0) goto L_0x0069;
    L_0x0066:
        r1.close();	 Catch:{ IOException -> 0x006f }
    L_0x0069:
        throw r0;	 Catch:{ all -> 0x006a }
    L_0x006a:
        r0 = move-exception;
        monitor-exit(r9);
        throw r0;
    L_0x006d:
        r0 = move-exception;
        goto L_0x0050;
    L_0x006f:
        r1 = move-exception;
        goto L_0x0069;
    L_0x0071:
        r1 = move-exception;
        r8 = r1;
        r1 = r0;
        r0 = r8;
        goto L_0x0064;
    L_0x0076:
        r1 = move-exception;
        goto L_0x0056;
        */
    }

    public final synchronized zzc zza(String str) {
        zzc com_google_android_gms_internal_zzc;
        IOException e;
        zzaj com_google_android_gms_internal_zzaj;
        NegativeArraySizeException e2;
        zzaj com_google_android_gms_internal_zzaj2;
        Throwable th;
        zzai com_google_android_gms_internal_zzai = (zzai) this.zzav.get(str);
        if (com_google_android_gms_internal_zzai == null) {
            com_google_android_gms_internal_zzc = null;
        } else {
            File zze = zze(str);
            try {
                InputStream com_google_android_gms_internal_zzaj3 = new zzaj(null);
                try {
                    zzai.zzf(com_google_android_gms_internal_zzaj3);
                    byte[] zza = zza(com_google_android_gms_internal_zzaj3, (int) (zze.length() - ((long) com_google_android_gms_internal_zzaj3.zzaA)));
                    zzc com_google_android_gms_internal_zzc2 = new zzc();
                    com_google_android_gms_internal_zzc2.data = zza;
                    com_google_android_gms_internal_zzc2.zza = com_google_android_gms_internal_zzai.zza;
                    com_google_android_gms_internal_zzc2.zzb = com_google_android_gms_internal_zzai.zzb;
                    com_google_android_gms_internal_zzc2.zzc = com_google_android_gms_internal_zzai.zzc;
                    com_google_android_gms_internal_zzc2.zzd = com_google_android_gms_internal_zzai.zzd;
                    com_google_android_gms_internal_zzc2.zze = com_google_android_gms_internal_zzai.zze;
                    com_google_android_gms_internal_zzc2.zzf = com_google_android_gms_internal_zzai.zzf;
                    try {
                        com_google_android_gms_internal_zzaj3.close();
                        com_google_android_gms_internal_zzc = com_google_android_gms_internal_zzc2;
                    } catch (IOException e3) {
                        com_google_android_gms_internal_zzc = null;
                    }
                } catch (IOException e4) {
                    e = e4;
                    InputStream inputStream = com_google_android_gms_internal_zzaj3;
                    zzab.zzb("%s: %s", zze.getAbsolutePath(), e.toString());
                    remove(str);
                    if (com_google_android_gms_internal_zzaj != null) {
                        com_google_android_gms_internal_zzaj.close();
                    }
                    com_google_android_gms_internal_zzc = null;
                    return com_google_android_gms_internal_zzc;
                } catch (NegativeArraySizeException e5) {
                    e2 = e5;
                    zzab.zzb("%s: %s", zze.getAbsolutePath(), e2.toString());
                    remove(str);
                    if (com_google_android_gms_internal_zzaj2 != null) {
                        com_google_android_gms_internal_zzaj2.close();
                    }
                    com_google_android_gms_internal_zzc = null;
                    return com_google_android_gms_internal_zzc;
                }
            } catch (IOException e6) {
                e = e6;
                com_google_android_gms_internal_zzaj = null;
                try {
                    zzab.zzb("%s: %s", zze.getAbsolutePath(), e.toString());
                    remove(str);
                    if (com_google_android_gms_internal_zzaj != null) {
                        try {
                            com_google_android_gms_internal_zzaj.close();
                        } catch (IOException e7) {
                            com_google_android_gms_internal_zzc = null;
                        }
                    }
                    com_google_android_gms_internal_zzc = null;
                } catch (Throwable th2) {
                    th = th2;
                    com_google_android_gms_internal_zzaj2 = com_google_android_gms_internal_zzaj;
                    if (com_google_android_gms_internal_zzaj2 != null) {
                        com_google_android_gms_internal_zzaj2.close();
                    }
                    throw th;
                }
                return com_google_android_gms_internal_zzc;
            } catch (NegativeArraySizeException e8) {
                e2 = e8;
                com_google_android_gms_internal_zzaj2 = null;
                try {
                    zzab.zzb("%s: %s", zze.getAbsolutePath(), e2.toString());
                    remove(str);
                    if (com_google_android_gms_internal_zzaj2 != null) {
                        try {
                            com_google_android_gms_internal_zzaj2.close();
                        } catch (IOException e9) {
                            com_google_android_gms_internal_zzc = null;
                        }
                    }
                    com_google_android_gms_internal_zzc = null;
                } catch (Throwable th3) {
                    th = th3;
                    if (com_google_android_gms_internal_zzaj2 != null) {
                        com_google_android_gms_internal_zzaj2.close();
                    }
                    throw th;
                }
                return com_google_android_gms_internal_zzc;
            } catch (Throwable th4) {
                th = th4;
                com_google_android_gms_internal_zzaj2 = null;
                if (com_google_android_gms_internal_zzaj2 != null) {
                    try {
                        com_google_android_gms_internal_zzaj2.close();
                    } catch (IOException e10) {
                        com_google_android_gms_internal_zzc = null;
                    }
                }
                throw th;
            }
        }
        return com_google_android_gms_internal_zzc;
    }

    public final synchronized void zza(String str, zzc com_google_android_gms_internal_zzc) {
        int i = 0;
        synchronized (this) {
            int length = com_google_android_gms_internal_zzc.data.length;
            if (this.zzaw + ((long) length) >= ((long) this.zzay)) {
                int i2;
                if (zzab.DEBUG) {
                    zzab.zza("Pruning old cache entries.", new Object[0]);
                }
                long j = this.zzaw;
                long elapsedRealtime = SystemClock.elapsedRealtime();
                Iterator it = this.zzav.entrySet().iterator();
                while (it.hasNext()) {
                    zzai com_google_android_gms_internal_zzai = (zzai) ((Entry) it.next()).getValue();
                    if (zze(com_google_android_gms_internal_zzai.key).delete()) {
                        this.zzaw -= com_google_android_gms_internal_zzai.zzaz;
                    } else {
                        zzab.zzb("Could not delete cache entry for key=%s, filename=%s", com_google_android_gms_internal_zzai.key, zzd(com_google_android_gms_internal_zzai.key));
                    }
                    it.remove();
                    i2 = i + 1;
                    if (((float) (this.zzaw + ((long) length))) < ((float) this.zzay) * 0.9f) {
                        break;
                    }
                    i = i2;
                }
                i2 = i;
                if (zzab.DEBUG) {
                    zzab.zza("pruned %d files, %d bytes, %d ms", Integer.valueOf(i2), Long.valueOf(this.zzaw - j), Long.valueOf(SystemClock.elapsedRealtime() - elapsedRealtime));
                }
            }
            File zze = zze(str);
            try {
                OutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(zze));
                zzai com_google_android_gms_internal_zzai2 = new zzai(str, com_google_android_gms_internal_zzc);
                if (com_google_android_gms_internal_zzai2.zza(bufferedOutputStream)) {
                    bufferedOutputStream.write(com_google_android_gms_internal_zzc.data);
                    bufferedOutputStream.close();
                    zza(str, com_google_android_gms_internal_zzai2);
                } else {
                    bufferedOutputStream.close();
                    zzab.zzb("Failed to write header for %s", zze.getAbsolutePath());
                    throw new IOException();
                }
            } catch (IOException e) {
                if (!zze.delete()) {
                    zzab.zzb("Could not clean up file %s", zze.getAbsolutePath());
                }
            }
        }
    }
}
