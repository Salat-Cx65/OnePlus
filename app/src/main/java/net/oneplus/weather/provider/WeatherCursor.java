package net.oneplus.weather.provider;

import android.database.AbstractCursor;
import android.database.CursorWindow;
import java.util.ArrayList;
import net.oneplus.weather.app.WeatherWarningActivity;

public class WeatherCursor extends AbstractCursor {
    private final String WEATHER_CITY;
    private final String WEATHER_INFO;
    private final String WEATHER_TEMP;
    private final String WEATHER_TEMP_HIGH;
    private final String WEATHER_TEMP_LOW;
    private final String WEATHER_TEMP_UNIT;
    private final String WEATHER_TIME;
    private final String WEATHER_TYPE;
    private ArrayList<ArrayList<String>> mAllDatas;
    private String[] mColumnNames;
    private int mCurrentPosition;
    private ArrayList<String> mOneLineData;

    public WeatherCursor() {
        this.WEATHER_TIME = "time";
        this.WEATHER_CITY = WeatherWarningActivity.INTENT_PARA_CITY;
        this.WEATHER_TYPE = "type";
        this.WEATHER_TEMP = "temp";
        this.WEATHER_TEMP_HIGH = "temp_high";
        this.WEATHER_TEMP_LOW = "temp_low";
        this.WEATHER_INFO = "info";
        this.WEATHER_TEMP_UNIT = "temp_unit";
        this.mColumnNames = new String[]{"time", WeatherWarningActivity.INTENT_PARA_CITY, "type", "temp", "temp_high", "temp_low", "info", "temp_unit"};
        this.mAllDatas = new ArrayList();
        this.mCurrentPosition = 0;
    }

    public void addData(ArrayList<String> data) {
        this.mAllDatas.add(data);
    }

    public String[] getColumnNames() {
        return this.mColumnNames;
    }

    public int getCount() {
        return this.mAllDatas.size();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public double getDouble(int r11_column) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.provider.WeatherCursor.getDouble(int):double");
        /*
        this = this;
        r6 = 0;
        r4 = r10.getString(r11);
        if (r4 == 0) goto L_0x0019;
    L_0x0008:
        r0 = r4;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x001b }
        r5 = r0;
        r8 = r5.doubleValue();	 Catch:{ ClassCastException -> 0x001b }
        r5 = java.lang.Double.valueOf(r8);	 Catch:{ ClassCastException -> 0x001b }
    L_0x0014:
        r6 = r5.doubleValue();	 Catch:{ ClassCastException -> 0x001b }
    L_0x0018:
        return r6;
    L_0x0019:
        r5 = 0;
        goto L_0x0014;
    L_0x001b:
        r2 = move-exception;
        r5 = r4 instanceof java.lang.CharSequence;
        if (r5 == 0) goto L_0x0018;
    L_0x0020:
        r5 = r4.toString();	 Catch:{ NumberFormatException -> 0x002d }
        r5 = java.lang.Double.valueOf(r5);	 Catch:{ NumberFormatException -> 0x002d }
        r6 = r5.doubleValue();	 Catch:{ NumberFormatException -> 0x002d }
        goto L_0x0018;
    L_0x002d:
        r3 = move-exception;
        goto L_0x0018;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float getFloat(int r7_column) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.provider.WeatherCursor.getFloat(int):float");
        /*
        this = this;
        r5 = 0;
        r3 = r6.getString(r7);
        if (r3 == 0) goto L_0x0018;
    L_0x0007:
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x001a }
        r4 = r0;
        r4 = r4.floatValue();	 Catch:{ ClassCastException -> 0x001a }
        r4 = java.lang.Float.valueOf(r4);	 Catch:{ ClassCastException -> 0x001a }
    L_0x0013:
        r4 = r4.floatValue();	 Catch:{ ClassCastException -> 0x001a }
    L_0x0017:
        return r4;
    L_0x0018:
        r4 = 0;
        goto L_0x0013;
    L_0x001a:
        r1 = move-exception;
        r4 = r3 instanceof java.lang.CharSequence;
        if (r4 == 0) goto L_0x002f;
    L_0x001f:
        r4 = r3.toString();	 Catch:{ NumberFormatException -> 0x002c }
        r4 = java.lang.Float.valueOf(r4);	 Catch:{ NumberFormatException -> 0x002c }
        r4 = r4.floatValue();	 Catch:{ NumberFormatException -> 0x002c }
        goto L_0x0017;
    L_0x002c:
        r2 = move-exception;
        r4 = r5;
        goto L_0x0017;
    L_0x002f:
        r4 = r5;
        goto L_0x0017;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int getInt(int r7_column) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.provider.WeatherCursor.getInt(int):int");
        /*
        this = this;
        r5 = 0;
        r3 = r6.getString(r7);
        if (r3 == 0) goto L_0x0018;
    L_0x0007:
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x001a }
        r4 = r0;
        r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x001a }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ ClassCastException -> 0x001a }
    L_0x0013:
        r4 = r4.intValue();	 Catch:{ ClassCastException -> 0x001a }
    L_0x0017:
        return r4;
    L_0x0018:
        r4 = 0;
        goto L_0x0013;
    L_0x001a:
        r1 = move-exception;
        r4 = r3 instanceof java.lang.CharSequence;
        if (r4 == 0) goto L_0x002f;
    L_0x001f:
        r4 = r3.toString();	 Catch:{ NumberFormatException -> 0x002c }
        r4 = java.lang.Integer.valueOf(r4);	 Catch:{ NumberFormatException -> 0x002c }
        r4 = r4.intValue();	 Catch:{ NumberFormatException -> 0x002c }
        goto L_0x0017;
    L_0x002c:
        r2 = move-exception;
        r4 = r5;
        goto L_0x0017;
    L_0x002f:
        r4 = r5;
        goto L_0x0017;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long getLong(int r11_column) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.provider.WeatherCursor.getLong(int):long");
        /*
        this = this;
        r6 = 0;
        r4 = r10.getString(r11);
        if (r4 == 0) goto L_0x0019;
    L_0x0008:
        r0 = r4;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x001b }
        r5 = r0;
        r8 = r5.longValue();	 Catch:{ ClassCastException -> 0x001b }
        r5 = java.lang.Long.valueOf(r8);	 Catch:{ ClassCastException -> 0x001b }
    L_0x0014:
        r6 = r5.longValue();	 Catch:{ ClassCastException -> 0x001b }
    L_0x0018:
        return r6;
    L_0x0019:
        r5 = 0;
        goto L_0x0014;
    L_0x001b:
        r2 = move-exception;
        r5 = r4 instanceof java.lang.CharSequence;
        if (r5 == 0) goto L_0x0018;
    L_0x0020:
        r5 = r4.toString();	 Catch:{ NumberFormatException -> 0x002d }
        r5 = java.lang.Long.valueOf(r5);	 Catch:{ NumberFormatException -> 0x002d }
        r6 = r5.longValue();	 Catch:{ NumberFormatException -> 0x002d }
        goto L_0x0018;
    L_0x002d:
        r3 = move-exception;
        goto L_0x0018;
        */
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public short getShort(int r7_column) {
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.weather.provider.WeatherCursor.getShort(int):short");
        /*
        this = this;
        r5 = 0;
        r3 = r6.getString(r7);
        if (r3 == 0) goto L_0x0018;
    L_0x0007:
        r0 = r3;
        r0 = (java.lang.Number) r0;	 Catch:{ ClassCastException -> 0x001a }
        r4 = r0;
        r4 = r4.shortValue();	 Catch:{ ClassCastException -> 0x001a }
        r4 = java.lang.Short.valueOf(r4);	 Catch:{ ClassCastException -> 0x001a }
    L_0x0013:
        r4 = r4.shortValue();	 Catch:{ ClassCastException -> 0x001a }
    L_0x0017:
        return r4;
    L_0x0018:
        r4 = 0;
        goto L_0x0013;
    L_0x001a:
        r1 = move-exception;
        r4 = r3 instanceof java.lang.CharSequence;
        if (r4 == 0) goto L_0x002f;
    L_0x001f:
        r4 = r3.toString();	 Catch:{ NumberFormatException -> 0x002c }
        r4 = java.lang.Short.valueOf(r4);	 Catch:{ NumberFormatException -> 0x002c }
        r4 = r4.shortValue();	 Catch:{ NumberFormatException -> 0x002c }
        goto L_0x0017;
    L_0x002c:
        r2 = move-exception;
        r4 = r5;
        goto L_0x0017;
    L_0x002f:
        r4 = r5;
        goto L_0x0017;
        */
    }

    public String getString(int column) {
        return this.mOneLineData == null ? null : (String) this.mOneLineData.get(column);
    }

    public boolean isNull(int column) {
        return column < 0 || column >= this.mOneLineData.size();
    }

    public void fillWindow(int position, CursorWindow window) {
        if (position > 0) {
            position--;
        }
        this.mCurrentPosition = position;
        super.fillWindow(position, window);
    }

    public boolean onMove(int oldPosition, int newPosition) {
        if (newPosition < 0 || newPosition >= getCount()) {
            this.mOneLineData = null;
            return false;
        }
        int index = newPosition - this.mCurrentPosition;
        if (index < 0 || index >= this.mAllDatas.size()) {
            return false;
        }
        this.mOneLineData = (ArrayList) this.mAllDatas.get(index);
        return super.onMove(oldPosition, newPosition);
    }
}
