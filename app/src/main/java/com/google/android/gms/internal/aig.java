package com.google.android.gms.internal;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;

public final class aig {
    private static void zza(String str, Object obj, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (obj == null) {
            return;
        }
        if (obj instanceof aif) {
            int modifiers;
            String name;
            int length = stringBuffer.length();
            if (str != null) {
                stringBuffer2.append(stringBuffer).append(zziq(str)).append(" <\n");
                stringBuffer.append("  ");
            }
            Class cls = obj.getClass();
            Field[] fields = cls.getFields();
            int length2 = fields.length;
            for (int i = 0; i < length2; i++) {
                Field field = fields[i];
                modifiers = field.getModifiers();
                name = field.getName();
                if (!"cachedSize".equals(name) && (modifiers & 1) == 1 && (modifiers & 8) != 8 && !name.startsWith("_") && !name.endsWith("_")) {
                    Class type = field.getType();
                    Object obj2 = field.get(obj);
                    if (!type.isArray() || type.getComponentType() == Byte.TYPE) {
                        zza(name, obj2, stringBuffer, stringBuffer2);
                    } else {
                        int length3 = obj2 == null ? 0 : Array.getLength(obj2);
                        for (modifiers = 0; modifiers < length3; modifiers++) {
                            zza(name, Array.get(obj2, modifiers), stringBuffer, stringBuffer2);
                        }
                    }
                }
            }
            Method[] methods = cls.getMethods();
            int length4 = methods.length;
            for (modifiers = 0; modifiers < length4; modifiers++) {
                String name2 = methods[modifiers].getName();
                if (name2.startsWith("set")) {
                    String substring = name2.substring(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                    try {
                        name = "has";
                        name2 = String.valueOf(substring);
                        if (((Boolean) cls.getMethod(name2.length() != 0 ? name.concat(name2) : new String(name), new Class[0]).invoke(obj, new Object[0])).booleanValue()) {
                            try {
                                name = "get";
                                name2 = String.valueOf(substring);
                                zza(substring, cls.getMethod(name2.length() != 0 ? name.concat(name2) : new String(name), new Class[0]).invoke(obj, new Object[0]), stringBuffer, stringBuffer2);
                            } catch (NoSuchMethodException e) {
                            }
                        }
                    } catch (NoSuchMethodException e2) {
                    }
                }
            }
            if (str != null) {
                stringBuffer.setLength(length);
                stringBuffer2.append(stringBuffer).append(">\n");
                return;
            }
            return;
        }
        stringBuffer2.append(stringBuffer).append(zziq(str)).append(": ");
        if (obj instanceof String) {
            String str2 = (String) obj;
            if (!str2.startsWith("http") && str2.length() > 200) {
                str2 = String.valueOf(str2.substring(0, GlobalConfig.MESSAGE_ACCU_GET_LOCATION_SUCC)).concat("[...]");
            }
            stringBuffer2.append("\"").append(zzcK(str2)).append("\"");
        } else if (obj instanceof byte[]) {
            zza((byte[]) obj, stringBuffer2);
        } else {
            stringBuffer2.append(obj);
        }
        stringBuffer2.append("\n");
    }

    private static void zza(byte[] bArr, StringBuffer stringBuffer) {
        if (bArr == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i] & 255;
            if (i2 == 92 || i2 == 34) {
                stringBuffer.append('\\').append((char) i2);
            } else if (i2 < 32 || i2 >= 127) {
                stringBuffer.append(String.format("\\%03o", new Object[]{Integer.valueOf(i2)}));
            } else {
                stringBuffer.append((char) i2);
            }
        }
        stringBuffer.append('\"');
    }

    private static String zzcK(String str) {
        int length = str.length();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < ' ' || charAt > '~' || charAt == '\"' || charAt == '\'') {
                stringBuilder.append(String.format("\\u%04x", new Object[]{Integer.valueOf(charAt)}));
            } else {
                stringBuilder.append(charAt);
            }
        }
        return stringBuilder.toString();
    }

    public static <T extends aif> String zze(T t) {
        if (t == null) {
            return StringUtils.EMPTY_STRING;
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        } catch (IllegalAccessException e) {
            String str = "Error printing proto: ";
            String valueOf = String.valueOf(e.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        } catch (InvocationTargetException e2) {
            str = "Error printing proto: ";
            valueOf = String.valueOf(e2.getMessage());
            return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
        }
    }

    private static String zziq(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (i == 0) {
                stringBuffer.append(Character.toLowerCase(charAt));
            } else if (Character.isUpperCase(charAt)) {
                stringBuffer.append('_').append(Character.toLowerCase(charAt));
            } else {
                stringBuffer.append(charAt);
            }
        }
        return stringBuffer.toString();
    }
}
