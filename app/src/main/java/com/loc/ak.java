package com.loc;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.File;
import java.io.IOException;

// compiled from: DB.java
public final class ak extends SQLiteOpenHelper {
    private ae a;

    // compiled from: DB.java
    public static class a extends ContextWrapper {
        private String a;
        private Context b;

        public a(Context context, String str) {
            super(context);
            this.a = str;
            this.b = context;
        }

        public final File getDatabasePath(String str) {
            try {
                String str2 = this.a + "/" + str;
                File file = new File(this.a);
                if (!file.exists()) {
                    file.mkdirs();
                }
                boolean z = false;
                file = new File(str2);
                if (file.exists()) {
                    z = true;
                } else {
                    try {
                        z = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return z ? file : super.getDatabasePath(str);
            } catch (Throwable th) {
                th.printStackTrace();
                return super.getDatabasePath(str);
            }
        }

        public final SQLiteDatabase openOrCreateDatabase(String str, int i, CursorFactory cursorFactory) {
            if (getDatabasePath(str) != null) {
                SQLiteDatabase openOrCreateDatabase;
                try {
                    openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), null);
                } catch (Throwable th) {
                    th.printStackTrace();
                    openOrCreateDatabase = null;
                }
                if (openOrCreateDatabase != null) {
                    return openOrCreateDatabase;
                }
            }
            return SQLiteDatabase.openOrCreateDatabase(this.b.getApplicationContext().getDatabasePath(str), null);
        }

        public final SQLiteDatabase openOrCreateDatabase(String str, int i, CursorFactory cursorFactory, DatabaseErrorHandler databaseErrorHandler) {
            if (getDatabasePath(str) != null) {
                SQLiteDatabase openOrCreateDatabase;
                try {
                    openOrCreateDatabase = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(str), null);
                } catch (Throwable th) {
                    th.printStackTrace();
                    openOrCreateDatabase = null;
                }
                if (openOrCreateDatabase != null) {
                    return openOrCreateDatabase;
                }
            }
            return SQLiteDatabase.openOrCreateDatabase(this.b.getApplicationContext().getDatabasePath(str), null);
        }
    }

    public ak(Context context, String str, ae aeVar) {
        super(context, str, null, 1);
        this.a = aeVar;
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        this.a.a(sQLiteDatabase);
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        ae aeVar = this.a;
    }
}
