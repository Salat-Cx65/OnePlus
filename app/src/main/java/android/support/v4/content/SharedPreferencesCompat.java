package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

public final class SharedPreferencesCompat {

    public static final class EditorCompat {
        private static android.support.v4.content.SharedPreferencesCompat.EditorCompat sInstance;
        private final Helper mHelper;

        private static class Helper {
            Helper() {
            }

            public void apply(@NonNull Editor editor) {
                try {
                    editor.apply();
                } catch (AbstractMethodError e) {
                    editor.commit();
                }
            }
        }

        private EditorCompat() {
            this.mHelper = new Helper();
        }

        public static android.support.v4.content.SharedPreferencesCompat.EditorCompat getInstance() {
            if (sInstance == null) {
                sInstance = new android.support.v4.content.SharedPreferencesCompat.EditorCompat();
            }
            return sInstance;
        }

        public void apply(@NonNull Editor editor) {
            this.mHelper.apply(editor);
        }
    }

    private SharedPreferencesCompat() {
    }
}
