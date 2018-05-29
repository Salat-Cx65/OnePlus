package com.google.android.gms.common.util;

import java.util.HashMap;

public final class zzr {
    public static void zza(StringBuilder stringBuilder, HashMap<String, String> hashMap) {
        stringBuilder.append("{");
        int i = 1;
        for (String str : hashMap.keySet()) {
            Object obj;
            Object obj2;
            if (obj2 == null) {
                stringBuilder.append(",");
                obj = obj2;
            } else {
                obj = null;
            }
            String str2 = (String) hashMap.get(str);
            stringBuilder.append("\"").append(str).append("\":");
            if (str2 == null) {
                stringBuilder.append("null");
                obj2 = obj;
            } else {
                stringBuilder.append("\"").append(str2).append("\"");
                obj2 = obj;
            }
        }
        stringBuilder.append("}");
    }
}
