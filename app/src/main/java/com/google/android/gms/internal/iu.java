package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Pattern;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public class iu {
    private static Uri CONTENT_URI;
    private static Uri zzbUc;
    private static Pattern zzbUd;
    private static Pattern zzbUe;
    private static final AtomicBoolean zzbUf;
    private static HashMap<String, String> zzbUg;
    private static HashMap<String, Boolean> zzbUh;
    private static HashMap<String, Integer> zzbUi;
    private static HashMap<String, Long> zzbUj;
    private static HashMap<String, Float> zzbUk;
    private static Object zzbUl;
    private static boolean zzbUm;
    private static String[] zzbUn;

    static {
        CONTENT_URI = Uri.parse("content://com.google.android.gsf.gservices");
        zzbUc = Uri.parse("content://com.google.android.gsf.gservices/prefix");
        zzbUd = Pattern.compile("^(1|true|t|on|yes|y)$", RainSurfaceView.RAIN_LEVEL_SHOWER);
        zzbUe = Pattern.compile("^(0|false|f|off|no|n)$", RainSurfaceView.RAIN_LEVEL_SHOWER);
        zzbUf = new AtomicBoolean();
        zzbUh = new HashMap();
        zzbUi = new HashMap();
        zzbUj = new HashMap();
        zzbUk = new HashMap();
        zzbUn = new String[0];
    }

    public static long getLong(ContentResolver contentResolver, String str, long j) {
        long j2 = 0;
        Object zzb = zzb(contentResolver);
        Long l = (Long) zza(zzbUj, str, Long.valueOf(0));
        if (l != null) {
            return l.longValue();
        }
        long j3;
        String zza = zza(contentResolver, str, null);
        Long l2;
        if (zza == null) {
            l2 = l;
            j3 = 0;
        } else {
            try {
                long parseLong = Long.parseLong(zza);
                l2 = Long.valueOf(parseLong);
                j3 = parseLong;
            } catch (NumberFormatException e) {
                long j4 = j2;
                l2 = l;
                j3 = j4;
            }
        }
        HashMap hashMap = zzbUj;
        synchronized (iu.class) {
            if (zzb == zzbUl) {
                hashMap.put(str, r2);
                zzbUg.remove(str);
            }
        }
        return j3;
    }

    private static <T> T zza(HashMap<String, T> hashMap, String str, T t) {
        synchronized (iu.class) {
            if (hashMap.containsKey(str)) {
                T t2 = hashMap.get(str);
                if (t2 == null) {
                    t2 = t;
                }
                return t2;
            }
            return null;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String zza(android.content.ContentResolver r9, java.lang.String r10, java.lang.String r11) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.iu.zza(android.content.ContentResolver, java.lang.String, java.lang.String):java.lang.String");
        /*
        r8 = 1;
        r3 = 0;
        r2 = 0;
        r1 = com.google.android.gms.internal.iu.class;
        monitor-enter(r1);
        zza(r9);	 Catch:{ all -> 0x0054 }
        r6 = zzbUl;	 Catch:{ all -> 0x0054 }
        r0 = zzbUg;	 Catch:{ all -> 0x0054 }
        r0 = r0.containsKey(r10);	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x0020;
    L_0x0013:
        r0 = zzbUg;	 Catch:{ all -> 0x0054 }
        r0 = r0.get(r10);	 Catch:{ all -> 0x0054 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x001e;
    L_0x001d:
        r2 = r0;
    L_0x001e:
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
    L_0x001f:
        return r2;
    L_0x0020:
        r4 = zzbUn;	 Catch:{ all -> 0x0054 }
        r5 = r4.length;	 Catch:{ all -> 0x0054 }
        r0 = r3;
    L_0x0024:
        if (r0 >= r5) goto L_0x005c;
    L_0x0026:
        r7 = r4[r0];	 Catch:{ all -> 0x0054 }
        r7 = r10.startsWith(r7);	 Catch:{ all -> 0x0054 }
        if (r7 == 0) goto L_0x0059;
    L_0x002e:
        r0 = zzbUm;	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x003a;
    L_0x0032:
        r0 = zzbUg;	 Catch:{ all -> 0x0054 }
        r0 = r0.isEmpty();	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x0057;
    L_0x003a:
        r0 = zzbUn;	 Catch:{ all -> 0x0054 }
        zzc(r9, r0);	 Catch:{ all -> 0x0054 }
        r0 = zzbUg;	 Catch:{ all -> 0x0054 }
        r0 = r0.containsKey(r10);	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x0057;
    L_0x0047:
        r0 = zzbUg;	 Catch:{ all -> 0x0054 }
        r0 = r0.get(r10);	 Catch:{ all -> 0x0054 }
        r0 = (java.lang.String) r0;	 Catch:{ all -> 0x0054 }
        if (r0 == 0) goto L_0x0052;
    L_0x0051:
        r2 = r0;
    L_0x0052:
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        goto L_0x001f;
    L_0x0054:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        throw r0;
    L_0x0057:
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        goto L_0x001f;
    L_0x0059:
        r0 = r0 + 1;
        goto L_0x0024;
    L_0x005c:
        monitor-exit(r1);	 Catch:{ all -> 0x0054 }
        r1 = CONTENT_URI;
        r4 = new java.lang.String[r8];
        r4[r3] = r10;
        r0 = r9;
        r3 = r2;
        r5 = r2;
        r1 = r0.query(r1, r2, r3, r4, r5);
        if (r1 == 0) goto L_0x0072;
    L_0x006c:
        r0 = r1.moveToFirst();	 Catch:{ all -> 0x0097 }
        if (r0 != 0) goto L_0x007c;
    L_0x0072:
        r0 = 0;
        zza(r6, r10, r0);	 Catch:{ all -> 0x0097 }
        if (r1 == 0) goto L_0x001f;
    L_0x0078:
        r1.close();
        goto L_0x001f;
    L_0x007c:
        r0 = 1;
        r0 = r1.getString(r0);	 Catch:{ all -> 0x0097 }
        if (r0 == 0) goto L_0x008b;
    L_0x0083:
        r3 = 0;
        r3 = r0.equals(r3);	 Catch:{ all -> 0x0097 }
        if (r3 == 0) goto L_0x008b;
    L_0x008a:
        r0 = r2;
    L_0x008b:
        zza(r6, r10, r0);	 Catch:{ all -> 0x0097 }
        if (r0 == 0) goto L_0x0091;
    L_0x0090:
        r2 = r0;
    L_0x0091:
        if (r1 == 0) goto L_0x001f;
    L_0x0093:
        r1.close();
        goto L_0x001f;
    L_0x0097:
        r0 = move-exception;
        if (r1 == 0) goto L_0x009d;
    L_0x009a:
        r1.close();
    L_0x009d:
        throw r0;
        */
    }

    private static Map<String, String> zza(ContentResolver contentResolver, String... strArr) {
        Cursor query = contentResolver.query(zzbUc, null, null, strArr, null);
        Object treeMap = new TreeMap();
        if (query != null) {
            while (query.moveToNext()) {
                treeMap.put(query.getString(0), query.getString(1));
            }
            query.close();
        }
        return treeMap;
    }

    private static void zza(ContentResolver contentResolver) {
        if (zzbUg == null) {
            zzbUf.set(false);
            zzbUg = new HashMap();
            zzbUl = new Object();
            zzbUm = false;
            contentResolver.registerContentObserver(CONTENT_URI, true, new iv(null));
        } else if (zzbUf.getAndSet(false)) {
            zzbUg.clear();
            zzbUh.clear();
            zzbUi.clear();
            zzbUj.clear();
            zzbUk.clear();
            zzbUl = new Object();
            zzbUm = false;
        }
    }

    private static void zza(Object obj, String str, String str2) {
        synchronized (iu.class) {
            if (obj == zzbUl) {
                zzbUg.put(str, str2);
            }
        }
    }

    private static Object zzb(ContentResolver contentResolver) {
        Object obj;
        synchronized (iu.class) {
            zza(contentResolver);
            obj = zzbUl;
        }
        return obj;
    }

    public static void zzb(ContentResolver contentResolver, String... strArr) {
        if (strArr.length != 0) {
            synchronized (iu.class) {
                String[] strArr2;
                zza(contentResolver);
                HashSet hashSet = new HashSet((((zzbUn.length + strArr.length) << 2) / 3) + 1);
                hashSet.addAll(Arrays.asList(zzbUn));
                ArrayList arrayList = new ArrayList();
                for (Object obj : strArr) {
                    if (hashSet.add(obj)) {
                        arrayList.add(obj);
                    }
                }
                if (arrayList.isEmpty()) {
                    strArr2 = new String[0];
                } else {
                    zzbUn = (String[]) hashSet.toArray(new String[hashSet.size()]);
                    strArr2 = (String[]) arrayList.toArray(new String[arrayList.size()]);
                }
                if (!zzbUm || zzbUg.isEmpty()) {
                    zzc(contentResolver, zzbUn);
                } else if (strArr2.length != 0) {
                    zzc(contentResolver, strArr2);
                }
            }
        }
    }

    private static void zzc(ContentResolver contentResolver, String[] strArr) {
        zzbUg.putAll(zza(contentResolver, strArr));
        zzbUm = true;
    }
}
