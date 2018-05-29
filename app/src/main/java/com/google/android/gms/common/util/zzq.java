package com.google.android.gms.common.util;

import android.text.TextUtils;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.DetectedActivity;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.oneplus.weather.R;
import net.oneplus.weather.widget.WeatherCircleView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class zzq {
    private static final Pattern zzaJY;
    private static final Pattern zzaJZ;

    static {
        zzaJY = Pattern.compile("\\\\.");
        zzaJZ = Pattern.compile("[\\\\\"/\b\f\n\r\t]");
    }

    public static boolean zzc(Object obj, Object obj2) {
        if (obj == null && obj2 == null) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        if ((obj instanceof JSONObject) && (obj2 instanceof JSONObject)) {
            JSONObject jSONObject = (JSONObject) obj;
            JSONObject jSONObject2 = (JSONObject) obj2;
            if (jSONObject.length() != jSONObject2.length()) {
                return false;
            }
            Iterator keys = jSONObject.keys();
            while (keys.hasNext()) {
                String str = (String) keys.next();
                if (!jSONObject2.has(str)) {
                    return false;
                }
                try {
                    if (!zzc(jSONObject.get(str), jSONObject2.get(str))) {
                        return false;
                    }
                } catch (JSONException e) {
                    return false;
                }
            }
            return true;
        } else if (!(obj instanceof JSONArray) || !(obj2 instanceof JSONArray)) {
            return obj.equals(obj2);
        } else {
            JSONArray jSONArray = (JSONArray) obj;
            JSONArray jSONArray2 = (JSONArray) obj2;
            if (jSONArray.length() != jSONArray2.length()) {
                return false;
            }
            int i = 0;
            while (i < jSONArray.length()) {
                try {
                    if (!zzc(jSONArray.get(i), jSONArray2.get(i))) {
                        return false;
                    }
                    i++;
                } catch (JSONException e2) {
                    return false;
                }
            }
            return true;
        }
    }

    public static String zzcK(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        Matcher matcher = zzaJZ.matcher(str);
        StringBuffer stringBuffer = null;
        while (matcher.find()) {
            if (stringBuffer == null) {
                stringBuffer = new StringBuffer();
            }
            switch (matcher.group().charAt(0)) {
                case DetectedActivity.RUNNING:
                    matcher.appendReplacement(stringBuffer, "\\\\b");
                    break;
                case ConnectionResult.SERVICE_INVALID:
                    matcher.appendReplacement(stringBuffer, "\\\\t");
                    break;
                case ConnectionResult.DEVELOPER_ERROR:
                    matcher.appendReplacement(stringBuffer, "\\\\n");
                    break;
                case WeatherCircleView.ARC_DIN:
                    matcher.appendReplacement(stringBuffer, "\\\\f");
                    break;
                case ConnectionResult.CANCELED:
                    matcher.appendReplacement(stringBuffer, "\\\\r");
                    break;
                case R.styleable.OneplusTheme_op_buttonPanelSideLayout:
                    matcher.appendReplacement(stringBuffer, "\\\\\\\"");
                    break;
                case R.styleable.AppCompatTheme_checkedTextViewStyle:
                    matcher.appendReplacement(stringBuffer, "\\\\/");
                    break;
                case R.styleable.AppCompatTheme_spinnerDropDownItemStyle:
                    matcher.appendReplacement(stringBuffer, "\\\\\\\\");
                    break;
                default:
                    break;
            }
        }
        if (stringBuffer == null) {
            return str;
        }
        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }
}
