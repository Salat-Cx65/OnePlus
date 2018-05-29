package com.loc;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import com.autonavi.aps.amapapi.model.AMapLocationServer;
import java.util.Hashtable;
import java.util.Map.Entry;
import net.oneplus.weather.util.GlobalConfig;
import net.oneplus.weather.util.StringUtils;
import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import org.json.JSONObject;

// compiled from: HeatMap.java
public final class cm {
    Hashtable<String, JSONObject> a;
    Hashtable<String, JSONObject> b;
    boolean c;
    private Context d;
    private String e;

    public cm(Context context) {
        this.a = new Hashtable();
        this.b = new Hashtable();
        this.c = false;
        this.d = null;
        this.e = "2.0.201501131131".replace(".", StringUtils.EMPTY_STRING);
        if (context != null) {
            this.d = context.getApplicationContext();
        }
    }

    private void a(Context context, String str, String str2, int i, long j, boolean z) {
        Throwable th;
        if (bw.a && context != null && !TextUtils.isEmpty(str)) {
            int optInt;
            JSONObject jSONObject = (JSONObject) this.a.get(str);
            JSONObject jSONObject2 = jSONObject == null ? new JSONObject() : jSONObject;
            try {
                jSONObject2.put("x", str2);
                jSONObject2.put("time", j);
                optInt = jSONObject2.optInt("num", 0) + i;
                try {
                    jSONObject2.put("num", optInt);
                } catch (Throwable th2) {
                    th = th2;
                    cw.a(th, "HeatMap", "update1");
                    this.a.put(str, jSONObject2);
                    cw.j = true;
                    dd.a(context, "pref", "ddex", true);
                    if (!z) {
                        this.b.put(str, jSONObject2);
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                optInt = i;
                cw.a(th, "HeatMap", "update1");
                this.a.put(str, jSONObject2);
                cw.j = true;
                dd.a(context, "pref", "ddex", true);
                if (!z) {
                    this.b.put(str, jSONObject2);
                }
            }
            this.a.put(str, jSONObject2);
            if (!(optInt < 120 || cw.j || dd.b(context, "pref", "ddex", false))) {
                cw.j = true;
                dd.a(context, "pref", "ddex", true);
            }
            if (!z) {
                try {
                    this.b.put(str, jSONObject2);
                } catch (Throwable th32) {
                    cw.a(th32, "HeatMap", "update");
                }
            }
        }
    }

    public final void a() {
        Throwable th;
        SQLiteDatabase sQLiteDatabase = null;
        if (!this.a.isEmpty()) {
            this.a.clear();
        }
        if (this.d != null) {
            SQLiteDatabase openOrCreateDatabase;
            try {
                StringBuilder stringBuilder = new StringBuilder();
                openOrCreateDatabase = this.d.openOrCreateDatabase("hmdb", 0, null);
                try {
                    stringBuilder.append("CREATE TABLE IF NOT EXISTS hm");
                    stringBuilder.append(this.e);
                    stringBuilder.append(" (hash VARCHAR PRIMARY KEY, num INTEGER, extra VARCHAR, time VARCHAR);");
                    openOrCreateDatabase.execSQL(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                    openOrCreateDatabase.beginTransaction();
                    for (Entry entry : this.b.entrySet()) {
                        String str = (String) entry.getKey();
                        String optString = ((JSONObject) entry.getValue()).optString("x");
                        if (!TextUtils.isEmpty(optString)) {
                            str = de.c(str);
                            optString = de.c(optString);
                            stringBuilder.append("REPLACE INTO ");
                            stringBuilder.append("hm").append(this.e);
                            stringBuilder.append(" VALUES (?, ?, ?, ?);");
                            openOrCreateDatabase.execSQL(stringBuilder.toString(), new Object[]{str, Integer.valueOf(r0.optInt("num", 1)), optString, Long.valueOf(r0.optLong("time", de.a()))});
                            stringBuilder.delete(0, stringBuilder.length());
                        }
                    }
                    if (openOrCreateDatabase != null) {
                        openOrCreateDatabase.setTransactionSuccessful();
                        openOrCreateDatabase.endTransaction();
                        if (openOrCreateDatabase.isOpen()) {
                            openOrCreateDatabase.close();
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    if (openOrCreateDatabase != null) {
                        openOrCreateDatabase.setTransactionSuccessful();
                        openOrCreateDatabase.endTransaction();
                        if (openOrCreateDatabase.isOpen()) {
                            openOrCreateDatabase.close();
                        }
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                openOrCreateDatabase = null;
                th = th3;
                if (openOrCreateDatabase != null) {
                    openOrCreateDatabase.setTransactionSuccessful();
                    openOrCreateDatabase.endTransaction();
                    if (openOrCreateDatabase.isOpen()) {
                        openOrCreateDatabase.close();
                    }
                }
                throw th;
            }
        }
        if (this.b.isEmpty()) {
            this.b.clear();
        }
        this.c = false;
    }

    public final void a(Context context) {
        Throwable th;
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor = null;
        if (bw.a && !this.c) {
            try {
                if (bw.a && context != null) {
                    SQLiteDatabase openOrCreateDatabase;
                    try {
                        openOrCreateDatabase = context.openOrCreateDatabase("hmdb", 0, null);
                        try {
                        } catch (Throwable th2) {
                            th = th2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            openOrCreateDatabase.close();
                            throw th;
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        openOrCreateDatabase = null;
                        if (cursor != null) {
                            cursor.close();
                        }
                        openOrCreateDatabase.close();
                        throw th;
                    }
                    if (de.a(openOrCreateDatabase, "hm")) {
                        Cursor cursor2;
                        long a = de.a() - 1209600000;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("SELECT hash, num, extra, time FROM ");
                        stringBuilder.append("hm").append(this.e);
                        stringBuilder.append(" WHERE time > ").append(a);
                        stringBuilder.append(" ORDER BY num DESC LIMIT 0,");
                        stringBuilder.append(GlobalConfig.MESSAGE_GET_CURRENT_WEATHER).append(";");
                        try {
                            cursor = openOrCreateDatabase.rawQuery(stringBuilder.toString(), null);
                            cursor2 = cursor;
                        } catch (Throwable th22) {
                            th = th22;
                            if (cursor != null) {
                                cursor.close();
                            }
                            openOrCreateDatabase.close();
                            throw th;
                        }
                        try {
                            stringBuilder.delete(0, stringBuilder.length());
                            if (cursor2 != null && cursor2.moveToFirst()) {
                                do {
                                    String string = cursor2.getString(0);
                                    int i = cursor2.getInt(1);
                                    String string2 = cursor2.getString(RainSurfaceView.RAIN_LEVEL_SHOWER);
                                    long j = cursor2.getLong(RainSurfaceView.RAIN_LEVEL_DOWNPOUR);
                                    if (!string2.startsWith("{")) {
                                        string = de.d(string);
                                        string2 = de.d(string2);
                                    }
                                    a(context, string, string2, i, j, false);
                                } while (cursor2.moveToNext());
                            }
                            if (cursor2 != null) {
                                cursor2.close();
                            }
                            if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                                openOrCreateDatabase.close();
                            }
                        } catch (Throwable th4) {
                            th = th4;
                            cursor = cursor2;
                            if (cursor != null) {
                                cursor.close();
                            }
                            openOrCreateDatabase.close();
                            throw th;
                        }
                    } else if (openOrCreateDatabase != null && openOrCreateDatabase.isOpen()) {
                        openOrCreateDatabase.close();
                    }
                }
            } catch (Throwable th5) {
                cw.a(th5, "HeatMap", "loadDB");
            }
            this.c = true;
        }
    }

    public final void a(Context context, String str, AMapLocationServer aMapLocationServer) {
        String str2 = null;
        if (de.a(aMapLocationServer) && context != null && bw.a) {
            if (this.a.size() > 500) {
                str2 = bz.a(aMapLocationServer.getLatitude(), aMapLocationServer.getLongitude());
                if (!this.a.containsKey(str2)) {
                    return;
                }
            }
            if (str2 == null) {
                str2 = bz.a(aMapLocationServer.getLatitude(), aMapLocationServer.getLongitude());
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("key", str);
                jSONObject.put("lat", aMapLocationServer.getLatitude());
                jSONObject.put("lon", aMapLocationServer.getLongitude());
                a(context, str2, jSONObject.toString(), 1, de.a(), true);
            } catch (Throwable th) {
                cw.a(th, "HeatMap", "update");
            }
        }
    }
}
