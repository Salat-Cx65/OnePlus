package com.google.android.gms.common.util;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

public final class zzo {
    private static final Pattern zzaJV;
    private static final Pattern zzaJW;
    private static final Pattern zzaJX;

    static {
        zzaJV = Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");
        zzaJW = Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");
        zzaJX = Pattern.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String decode(java.lang.String r2, java.lang.String r3) {
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.util.zzo.decode(java.lang.String, java.lang.String):java.lang.String");
        /*
        if (r3 == 0) goto L_0x0007;
    L_0x0002:
        r0 = java.net.URLDecoder.decode(r2, r3);	 Catch:{ UnsupportedEncodingException -> 0x000a }
        return r0;
    L_0x0007:
        r3 = "ISO-8859-1";
        goto L_0x0002;
    L_0x000a:
        r0 = move-exception;
        r1 = new java.lang.IllegalArgumentException;
        r1.<init>(r0);
        throw r1;
        */
    }

    public static Map<String, String> zza(URI uri, String str) {
        Map<String, String> emptyMap = Collections.emptyMap();
        String rawQuery = uri.getRawQuery();
        if (rawQuery == null || rawQuery.length() <= 0) {
            return emptyMap;
        }
        Map<String, String> hashMap = new HashMap();
        Scanner scanner = new Scanner(rawQuery);
        scanner.useDelimiter("&");
        while (scanner.hasNext()) {
            String[] split = scanner.next().split("=");
            if (split.length != 0 && split.length <= 2) {
                String decode = decode(split[0], str);
                Object obj = null;
                if (split.length == 2) {
                    obj = decode(split[1], str);
                }
                hashMap.put(decode, obj);
            }
            throw new IllegalArgumentException("bad parameter");
        }
        return hashMap;
    }
}
