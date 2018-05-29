package com.oneplus.lib.util;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.util.Map;

public class PreferenceUtils {
    private static final String MESSAGEDATA = "messagedata";
    private static final String SIMINFODATA = "siminfodata";

    public static void writeMessageData(Context context, long time, String title, String content, String url, String id) {
        Editor editor = context.getSharedPreferences(MESSAGEDATA, 0).edit();
        editor.putString(String.valueOf(time), title + "," + content + "," + url + "," + id);
        editor.apply();
    }

    public static Map<?, ?> getAllMessageData(Context context) {
        return context.getSharedPreferences(MESSAGEDATA, 0).getAll();
    }

    public static void deleteMessageData(Context context, long time) {
        Editor editor = context.getSharedPreferences(MESSAGEDATA, 0).edit();
        editor.remove(String.valueOf(time));
        editor.apply();
    }

    public static void writeSimInfoData(Context context, String country, String operator, int index) {
        Editor editor = context.getSharedPreferences(SIMINFODATA, 0).edit();
        editor.putString(SIMINFODATA + index, country + "," + operator + ",");
        editor.apply();
    }

    public static String readSimInfoData(Context context, int index) {
        return context.getSharedPreferences(SIMINFODATA, 0).getString(SIMINFODATA + index, null);
    }

    public static void removeSimInfoData(Context context, int index) {
        Editor editor = context.getSharedPreferences(MESSAGEDATA, 0).edit();
        editor.remove(SIMINFODATA);
        editor.apply();
    }

    public static void clearMesData(Context context) {
        Editor editor = context.getSharedPreferences(MESSAGEDATA, 0).edit();
        editor.clear();
        editor.apply();
    }

    public static void applyInt(Context context, String key, int value) {
        Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public static int getInt(Context context, String key, int defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
    }

    public static boolean contains(Context context, String key) {
        return PreferenceManager.getDefaultSharedPreferences(context).contains(key);
    }
}
