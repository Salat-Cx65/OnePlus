package com.loc;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Build.VERSION;

// compiled from: SPUtil.java
public final class dd {

    // compiled from: SPUtil.java
    static class AnonymousClass_1 extends AsyncTask<Void, Void, Void> {
        final /* synthetic */ Editor a;

        AnonymousClass_1(Editor editor) {
            this.a = editor;
        }

        private Void a() {
            try {
                if (this.a != null) {
                    this.a.commit();
                }
            } catch (Throwable th) {
                cw.a(th, "SPUtil", "commit");
            }
            return null;
        }

        protected final /* synthetic */ Object doInBackground(Object[] objArr) {
            return a();
        }
    }

    public static String a(Context context) {
        String str = "00:00:00:00:00:00";
        if (context == null) {
            return str;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("pref", 0);
        if (sharedPreferences == null) {
            return str;
        }
        String str2 = "smac";
        if (!sharedPreferences.contains(str2)) {
            return str;
        }
        try {
            String string = sharedPreferences.getString(str2, null);
            try {
                return new String(o.b(string), "UTF-8");
            } catch (Throwable th) {
                Throwable th2 = th;
                str = string;
                Throwable th3 = th2;
            }
        } catch (Throwable th4) {
            th3 = th4;
            cw.a(th3, "SPUtil", "getSmac");
            sharedPreferences.edit().remove(str2).commit();
            return str;
        }
    }

    public static String a(Context context, String str, String str2, String str3) {
        try {
            return context.getSharedPreferences(str, 0).getString(str2, str3);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "getPrefsInt");
            return str3;
        }
    }

    public static void a(Context context, String str, String str2, int i) {
        try {
            Editor edit = context.getSharedPreferences(str, 0).edit();
            edit.putInt(str2, i);
            a(edit);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "setPrefsInt");
        }
    }

    public static void a(Context context, String str, String str2, long j) {
        try {
            Editor edit = context.getSharedPreferences(str, 0).edit();
            edit.putLong(str2, j);
            a(edit);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "setPrefsLong");
        }
    }

    public static void a(Context context, String str, String str2, boolean z) {
        try {
            Editor edit = context.getSharedPreferences(str, 0).edit();
            edit.putBoolean(str2, z);
            a(edit);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "updatePrefsBoolean");
        }
    }

    @SuppressLint({"NewApi"})
    public static void a(Editor editor) {
        if (editor != null) {
            if (VERSION.SDK_INT >= 9) {
                editor.apply();
                return;
            }
            try {
                new AnonymousClass_1(editor).execute(new Void[]{null, null, null});
            } catch (Throwable th) {
                cw.a(th, "SPUtil", "commit1");
            }
        }
    }

    public static int b(Context context, String str, String str2, int i) {
        try {
            return context.getSharedPreferences(str, 0).getInt(str2, i);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "getPrefsInt");
            return i;
        }
    }

    public static long b(Context context, String str, String str2, long j) {
        try {
            return context.getSharedPreferences(str, 0).getLong(str2, j);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "getPrefsLong");
            return j;
        }
    }

    public static boolean b(Context context, String str, String str2, boolean z) {
        try {
            return context.getSharedPreferences(str, 0).getBoolean(str2, z);
        } catch (Throwable th) {
            cw.a(th, "SPUtil", "getPrefsBoolean");
            return z;
        }
    }
}
