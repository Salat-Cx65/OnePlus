package com.oneplus.custom.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.oneplus.weather.widget.openglbase.RainSurfaceView;
import net.oneplus.weather.widget.shap.Stars;

public class OpCustomizeSettingsG1 extends OpCustomizeSettings {
    private static final String custom_fn = "/sys/module/param_read_write/parameters/cust_flag";

    protected CUSTOM_TYPE getCustomization() {
        Exception e;
        CUSTOM_TYPE result = CUSTOM_TYPE.NONE;
        File themeStateFile = new File(custom_fn);
        if (!themeStateFile.exists()) {
            return result;
        }
        BufferedReader br = null;
        try {
            BufferedReader br2 = new BufferedReader(new FileReader(themeStateFile));
            while (true) {
                try {
                    String line = br2.readLine();
                    if (line != null) {
                        Object obj = -1;
                        switch (line.hashCode()) {
                            case R.styleable.AppCompatTheme_colorBackgroundFloating:
                                if (line.equals("1")) {
                                    obj = null;
                                }
                                break;
                            case Stars.CIRCLE_COUNT:
                                if (line.equals("2")) {
                                    obj = 1;
                                }
                                break;
                        }
                        switch (obj) {
                            case RainSurfaceView.RAIN_LEVEL_DRIZZLE:
                                result = CUSTOM_TYPE.JCC;
                                break;
                            case RainSurfaceView.RAIN_LEVEL_NORMAL_RAIN:
                                result = CUSTOM_TYPE.SW;
                                break;
                            default:
                                break;
                        }
                    } else {
                        if (br2 != null) {
                            try {
                                br2.close();
                            } catch (Exception e2) {
                                Log.e("OpCustomizeSettings", e2.getMessage());
                            }
                        }
                        return result;
                    }
                } catch (Exception e3) {
                    e2 = e3;
                    br = br2;
                } catch (Throwable th) {
                    br = br2;
                }
            }
        } catch (Exception e4) {
            e2 = e4;
            try {
                Log.e("OpCustomizeSettings", e2.getMessage());
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e22) {
                        Log.e("OpCustomizeSettings", e22.getMessage());
                    }
                }
                return result;
            } catch (Throwable th2) {
                if (br != null) {
                    try {
                        br.close();
                    } catch (Exception e222) {
                        Log.e("OpCustomizeSettings", e222.getMessage());
                    }
                }
                return result;
            }
        }
    }
}
