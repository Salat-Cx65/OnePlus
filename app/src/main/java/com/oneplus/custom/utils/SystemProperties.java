package com.oneplus.custom.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class SystemProperties {
    private static Method sSystemPropertiesGetMethod;

    SystemProperties() {
    }

    static {
        sSystemPropertiesGetMethod = null;
    }

    public static String get(String name) {
        if (sSystemPropertiesGetMethod == null) {
            try {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                if (systemPropertiesClass != null) {
                    sSystemPropertiesGetMethod = systemPropertiesClass.getMethod("get", new Class[]{String.class});
                }
            } catch (ClassNotFoundException e) {
            } catch (NoSuchMethodException e2) {
            }
        }
        if (sSystemPropertiesGetMethod != null) {
            try {
                return (String) sSystemPropertiesGetMethod.invoke(null, new Object[]{name});
            } catch (IllegalArgumentException e3) {
            } catch (IllegalAccessException e4) {
            } catch (InvocationTargetException e5) {
            }
        }
        return null;
    }
}
